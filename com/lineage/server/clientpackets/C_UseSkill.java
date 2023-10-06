package com.lineage.server.clientpackets;

import static com.lineage.server.model.skill.L1SkillId.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.ActionCodes;
import com.lineage.server.datatables.PolyTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.world.World;
import com.lineage.server.datatables.sql.SpeedTable;

/**
 * 要求使用技能
 *
 * @author daien
 *
 */
public class C_UseSkill extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_UseSkill.class);

	// 隱身狀態下可用的魔法
	private static final int[] _cast_with_invis = {
		HEAL, LIGHT, SHIELD, TELEPORT, HOLY_WEAPON, CURE_POISON, ENCHANT_WEAPON, DETECTION, DECREASE_WEIGHT, 
		EXTRA_HEAL, BLESSED_ARMOR, PHYSICAL_ENCHANT_DEX, COUNTER_MAGIC, MEDITATION, GREATER_HEAL, REMOVE_CURSE, 
		PHYSICAL_ENCHANT_STR, HASTE, CANCELLATION, BLESS_WEAPON, HEAL_ALL, HOLY_WALK, GREATER_HASTE, BERSERKERS, 
		FULL_HEAL, INVISIBILITY, RESURRECTION, LIFE_STREAM, SHAPE_CHANGE, IMMUNE_TO_HARM, MASS_TELEPORT, COUNTER_DETECTION, 
		CREATE_MAGICAL_WEAPON, GREATER_RESURRECTION, ABSOLUTE_BARRIER, ADVANCE_SPIRIT, REDUCTION_ARMOR, BOUNCE_ATTACK, 
		SOLID_CARRIAGE, COUNTER_BARRIER, BLIND_HIDING, ENCHANT_VENOM, SHADOW_ARMOR, BRING_STONE, MOVING_ACCELERATION, 
		BURNING_SPIRIT, VENOM_RESIST, DOUBLE_BREAK, UNCANNY_DODGE, SHADOW_FANG, DRESS_MIGHTY, DRESS_DEXTERITY, DRESS_EVASION, 
		TRUE_TARGET, GLOWING_AURA, SHINING_AURA, CALL_CLAN, BRAVE_AURA, RUN_CLAN, RESIST_MAGIC, BODY_TO_MIND, TELEPORT_TO_MATHER, 
		ELEMENTAL_FALL_DOWN, COUNTER_MIRROR, CLEAR_MIND, RESIST_ELEMENTAL, BLOODY_SOUL, ELEMENTAL_PROTECTION, FIRE_WEAPON, 
		WIND_SHOT, WIND_WALK, EARTH_SKIN, FIRE_BLESS, STORM_EYE, NATURES_TOUCH, EARTH_BLESS, BURNING_WEAPON, NATURES_BLESSING, 
		CALL_OF_NATURE, STORM_SHOT, IRON_SKIN, EXOTIC_VITALIZE, WATER_LIFE, ELEMENTAL_FIRE, SOUL_OF_FLAME, ADDITIONAL_FIRE, 
		DRAGON_SKIN, AWAKEN_ANTHARAS, AWAKEN_FAFURION, AWAKEN_VALAKAS, MIRROR_IMAGE, ILLUSION_OGRE, ILLUSION_LICH, PATIENCE, 
		ILLUSION_DIA_GOLEM, INSIGHT, ILLUSION_AVATAR };

	// 五段加速狀態不可用的魔法
	private static final int[] _cast_with_STATUS = { MOVING_ACCELERATION,GREATER_HASTE,HASTE,HOLY_WALK,WIND_WALK };
	
	// 魔封狀態下可用的魔法
	private static final int[] _cast_with_silence = { SHOCK_STUN, REDUCTION_ARMOR, BOUNCE_ATTACK, SOLID_CARRIAGE,
			COUNTER_BARRIER, FOE_SLAYER };

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);

			final L1PcInstance pc = client.getActiveChar();

			/*
			 * final long noetime = System.currentTimeMillis(); if (noetime -
			 * pc.get_spr_skill_time() <=
			 * SprTable.get().spr_skill_speed(pc.getTempCharGfx())) { if
			 * (!pc.isGm()) { pc.getNetConnection().kick(); return; } }
			 * pc.set_spr_skill_time(noetime);
			 */
			if(pc==null){
				return;
			}

			if (pc.isDead()) { // 死亡
				return;
			}

			if (pc.isTeleport()) { // 傳送中
				return;
			}

			if (pc.isPrivateShop()) {// 商店村模式
				return;
			}

			if (pc.getInventory().getWeight240() >= 197) { // 重量過重
				// 316 \f1你攜帶太多物品，因此無法使用法術。
				pc.sendPackets(new S_ServerMessage(316));
				return;
			}
			
			if (!pc.getMap().isUsableSkill()) {
				
				// 563 \f1你無法在這個地方使用。
				pc.sendPackets(new S_ServerMessage(563));
				return;
			}
			// 技能延遲狀態
			if (pc.isSkillDelay()) {
				return;
			}

			// 加載封包內容
			final int row = this.readC();
			final int column = this.readC();

			// 計算使用的技能編號(>> 1: 除) (<< 1: 乘) 3=*8
			final int skillId = (row << 3) + column + 1;

			if (skillId > 231) {
				return;
			}
			if (skillId < 0) {
				return;
			}
			
			// 裝備劍類武器才能使用舞躍之火
			if (skillId == FIRE_BLESS) {
				L1ItemInstance weapon = pc.getWeapon(); 
			    if (weapon != null) {//武器不為空
				    if (weapon.getItem().getType() != 1) {//不是單手劍
				    	//使用魔法: 失敗(未成功), 需要裝備劍武器
				    	pc.sendPackets(new S_ServerMessage(3435));
				    	return;
				    }
			    } else {//沒有武器
			    	//使用魔法: 失敗(未成功), 需要裝備劍武器
			    	pc.sendPackets(new S_ServerMessage(3435));
			    	return;
			    }
			}
			
			boolean isError = false;
			final boolean isError2 = false;
            boolean isError3 = false;
			// 變身限制
			final int polyId = pc.getTempCharGfx();
			final L1PolyMorph poly = PolyTable.get().getTemplate(polyId);
			// 該變身無法使用魔法
			if ((poly != null) && !poly.canUseSkill()) {
				isError = true;
			}

			// 麻痺・凍結狀態
			if (pc.isParalyzed() && !isError) {
				isError = true;
			}
            
			// 下列狀態無法使用魔法(魔法封印)
			if (pc.hasSkillEffect(SILENCE) && !isError) {
				if (!this.isSilenceUsableSkill(skillId)) {
					isError = true;
				}
			}
			// 下列狀態無法使用魔法(封印禁地)
			if (pc.hasSkillEffect(AREA_OF_SILENCE) && !isError) {
				if (!this.isSilenceUsableSkill(skillId)) {
					isError = true;
				}
			}

			// 下列狀態無法使用魔法(沈黙毒素效果)
			if (pc.hasSkillEffect(STATUS_POISON_SILENCE) && !isError) {
				if (!this.isSilenceUsableSkill(skillId)) {
					isError = true;
				}
			}

			// 無法攻擊/使用道具/技能/回城的狀態
			if (pc.isParalyzedX() && !isError) {
				isError = true;
			}
			
			if(pc.getCurrentWeapon()!=50 && skillId == 87){
				isError = true;
			}

			if (isError) {
				// 285 \f1在此狀態下無法使用魔法。
				pc.sendPackets(new S_ServerMessage(285));
				return;
			}
			if (isError3) {
                pc.sendPackets(new S_Paralysis(7, false));
                pc.sendPackets(new S_ServerMessage(316));
                return;
            }
            if (isError2) {
                pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法使用魔法。"));
                return;
            }
			
            final int[] MAP_IDSKILL = ConfigOther.MAP_IDSKILL;
            final int[] MAP_SKILL = ConfigOther.MAP_SKILL;
            int[] array;
            
            for (int length = (array = MAP_IDSKILL).length, i = 0; i < length; ++i) {
                final int mapid = array[i];
                int[] array2;
                for (int length2 = (array2 = MAP_SKILL).length, j = 0; j < length2; ++j) {
                    final int mapskill = array2[j];
                    if (pc.getMapId() == mapid && skillId == mapskill) {
                    	pc.sendPackets((ServerBasePacket)new S_ServerMessage("此地圖無法使用此魔法"));
                        pc.sendPackets(new S_Paralysis(7, false));
                        return;
                    }
                }
            }
            final int[] bow_GFX_Arrow = ConfigGuaji.Guaji_map_stopskill;
            int[] array3;
            for (int length3 = (array3 = bow_GFX_Arrow).length, k = 0; k < length3; ++k) {
                final int gfx = array3[k];
                if (skillId == gfx && pc.isActived()) {
                	pc.sendPackets((ServerBasePacket)new S_ServerMessage("內掛中禁止施放此魔法"));
                    return;
                }
            }
            final boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId());
            final int[] bow_GFX_Arrow2 = ConfigOther.WAR_DISABLE_SKILLS;
            int[] array4;
            for (int length4 = (array4 = bow_GFX_Arrow2).length, l = 0; l < length4; ++l) {
                final int gfx2 = array4[l];
                if (skillId == gfx2 && castle_area && pc.castleWarResult()) {
                    pc.sendPackets(new S_ServerMessage("戰爭旗幟內禁止使用此魔法"));
                    return;
                }
            }
            if (skillId == 11 && pc.getMap().isSafetyZone(pc.getLocation())) {
                pc.sendPackets(new S_ServerMessage("安全區無法施放此魔法"));
                return;
            }

			// 隱身狀態可用魔法限制
			if (pc.isInvisble() || pc.isInvisDelay()) {
				if (!this.isInvisUsableSkill(skillId)) {
					// 1003：透明狀態無法使用的魔法。
					pc.sendPackets(new S_ServerMessage(1003));
					return;
				}
			}

			// 技能合法判斷
			if (!pc.isSkillMastery(skillId)) {
				// _log.info(pc.getAccountName() + ":" + pc.getName() + "(" +
				// pc.getType() + ") 非法技能:" + skillId);
				return;
			}
		
			final long now_time = System.currentTimeMillis();
            final L1Skills _skill = SkillsTable.get().getTemplate(skillId);
            if (_skill.getdetect_no_delay() != 0 && now_time < pc.getskill_timeDelay() + _skill.getReuseDelay()) {
                return;
            }
			// 檢查地圖使用權
			CheckUtil.isUserMap(pc);

			String charName = new String();
			// String message = null;

			int targetId = 0;
			int targetX = 0;
			int targetY = 0;

			// 檢查使用魔法的間隔
			int result;
			// FIXME 判斷有向及無向的魔法
			boolean isSpeed = SpeedTable.get().isSpeed(pc.getAccountName());
			if (!isSpeed) {
				if (SkillsTable.get().getTemplate(skillId).getActionId() == ActionCodes.ACTION_SkillAttack) {
					result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_DIR);
				} else {
					result = pc.getAcceleratorChecker().checkInterval(AcceleratorChecker.ACT_TYPE.SPELL_NODIR);
				}
					if (result == AcceleratorChecker.R_DISCONNECTED) {
						C_UseSkill._log.error((Object)("要求角色技能攻擊:速度異常(" + pc.getName() + ")"));
						return;
					}
			}
			
			if (skillId == 18 && pc.hasSkillEffect(6931)) {
                pc.killSkillEffectTimer(6931);
                pc.setSkillEffect(6932, 2000);
            }
			if (decrypt.length > 4) {
				try {
					if ((skillId == CALL_CLAN) || (skillId == RUN_CLAN)) { // コールクラン、ランクラン
						String tempName[] = readS().split("\\[");
						charName = tempName[0];
					} else if (skillId == TRUE_TARGET) { // トゥルーターゲット
						targetId = readD();
						targetX = readH();
						targetY = readH();
						pc.setText(readS());
					} else if ((skillId == TELEPORT) || (skillId == MASS_TELEPORT)) { // テレポート、マステレポート
						readH(); // MapID
						targetId = readD(); // Bookmark ID
					} else if ((skillId == FIRE_WALL) || (skillId == LIFE_STREAM)) { // ファイアーウォール、ライフストリーム
						targetX = readH();
						targetY = readH();
					} else if (skillId == SUMMON_MONSTER) { // 法師魔法 (召喚術)
						if (pc.getInventory().checkEquipped(20284) || pc.getInventory().checkEquipped(120284)) { // 有裝備召喚戒指
							int summonId = readD();
							pc.setSummonId(summonId);
						} else {
							targetId = readD();
						}
					} else {
						targetId = readD();
						targetX = readH();
						targetY = readH();
					}
				} catch (Exception ex) {
					// _log.log(Level.SEVERE, "", e);
				}
			}

			// 絕對屏障解除
			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
				pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
				pc.startHpRegeneration();
				pc.startMpRegeneration();
			}

			// 冥想術解除
			pc.killSkillEffectTimer(MEDITATION);

			try {
				if ((skillId == CALL_CLAN) || (skillId == RUN_CLAN)) { // コールクラン、ランクラン
					if (charName.isEmpty()) {
						// 名前が空の場合クライアントで弾かれるはず
						return;
					}

					L1PcInstance target = World.get().getPlayer(charName);

					if (target == null) {
						// メッセージが正確であるか未調査
						pc.sendPackets(new S_ServerMessage(73, charName)); // \f1%0はゲームをしていません。
						return;
					}
					if (pc.getClanid() != target.getClanid()) {
						pc.sendPackets(new S_ServerMessage(414)); // 同じ血盟員ではありません。
						return;
					}
					targetId = target.getId();
					if (skillId == CALL_CLAN) {
						// 移動せずに連続して同じクラン員にコールクランした場合、向きは前回の向きになる
						int callClanId = pc.getCallClanId();
						if ((callClanId == 0) || (callClanId != targetId)) {
							pc.setCallClanId(targetId);
							pc.setCallClanHeading(pc.getHeading());
						}
					}
				}
				
				if (pc.isWizard() && pc.getlogpcpower_SkillFor5() != 0 && _skill.getTarget().equals("attack")) {
				    if (pc.hasSkillEffect(5198)) {
				        if (pc.getEsotericSkill() == skillId) {
				            if (pc.getEsotericCount() < 4) {
				                pc.setEsotericCount(pc.getEsotericCount() + 1);
				                if (pc.getEsotericCount() == 2) {
				                    pc.sendPackets(new S_SystemMessage("魔擊累積 階段二已發動！"));
				                } else if (pc.getEsotericCount() == 3) {
				                    pc.sendPackets(new S_SystemMessage("魔擊累積 階段三已發動！"));
				                } else if (pc.getEsotericCount() == 4) {
				                    pc.sendPackets(new S_SystemMessage("魔擊累積 階段四已發動！"));
				                }
				            } else {
				                pc.sendPackets(new S_SystemMessage("魔擊累積維持"));
				            }
				        } else {
				            pc.setEsotericSkill(skillId);
				            pc.setEsotericCount(1);
				            pc.sendPackets(new S_SystemMessage("魔擊累積 階段一已發動！"));
				        }
				        pc.setSkillEffect(5198, 2000);
				    } else {
				        pc.setEsotericSkill(skillId);
				        pc.setEsotericCount(1);
				        pc.setSkillEffect(5198, 2000);
				        pc.sendPackets(new S_SystemMessage("魔擊累積 階段一 已發動！"));
				    }
				}

				
				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(pc, skillId, targetId, targetX, targetY, 0, L1SkillUse.TYPE_NORMAL);

			} catch (final Exception ex2) {
				/*
				 * OutErrorMsg.put(this.getClass().getSimpleName(),
				 * "檢查 C_UseSkill 程式執行位置(核心管理者參考) SkillId: " + skillId, e);
				 */
			}

		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			this.over();
		}
		this.over();
	}
	/**
	 * 該技能可否在5段加速狀態使用
	 * 
	 * @param useSkillid
	 *            技能編號
	 * @return true:可 false:不可
	 */
	public boolean isSTATUS(int useSkillid){
	  for (final int skillId : _cast_with_STATUS) {
		  if (skillId == useSkillid) {
			return false;
			}
		}
		return true;
	}
	/**
	 * 該技能可否在隱身狀態使用
	 * 
	 * @param useSkillid
	 *            技能編號
	 * @return true:可 false:不可
	 */
	private boolean isInvisUsableSkill(int useSkillid) {
		for (final int skillId : _cast_with_invis) {
			if (skillId == useSkillid) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 該技能可否在魔封狀態使用
	 * 
	 * @param useSkillid
	 *            技能編號
	 * @return true:可 false:不可
	 */
	private boolean isSilenceUsableSkill(int useSkillid) {
		for (final int skillId : _cast_with_silence) {
			if (skillId == useSkillid) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
