// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model;

import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_AttackPacketNpc;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.model.poison.L1ParalysisPoison;
import com.lineage.server.model.poison.L1SilencePoison;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.gametime.L1GameTimeClock;
import java.util.Iterator;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.william.MapNpcDmg;
import com.lineage.data.event.NpcMapDmg;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.types.Point;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.datatables.SprTable;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;

public class L1AttackNpc extends L1AttackMode {
	private static final Log _log = LogFactory.getLog(L1AttackNpc.class);
	L1ItemSpecialAttributeChar item_attr_char = null;
	public L1AttackNpc(L1NpcInstance attacker, L1Character target) {
		if (attacker == null) {
			return;
		}
		if (target == null) {
			return;
		}
		if (target.isDead()) {
			return;
		}
		if (target.getCurrentHp() <= 0) {
			return;
		}
		_npc = attacker;
		if ((target instanceof L1PcInstance)) {
			_targetPc = ((L1PcInstance) target);
			_calcType = NPC_PC;
		} else if ((target instanceof L1NpcInstance)) {
			_targetNpc = ((L1NpcInstance) target);
			_calcType = NPC_NPC;
		}
		_target = target;
		_targetId = target.getId();
		_targetX = target.getX();
		_targetY = target.getY();
	}
    
    @Override
    public boolean calcHit() {
		if (_target == null) {
			_isHit = false;
			return _isHit;
		}
		switch (_calcType) {
		case NPC_PC:
			_isHit = calcPcHit();
			break;
		case NPC_NPC:
			_isHit = calcNpcHit();
		}

		return _isHit;
	}
    
    private boolean calcPcHit() {
		if ((((_npc instanceof L1PetInstance)) || ((_npc instanceof L1SummonInstance)))
				&& (_targetPc.getZoneType() == 1)) {
			return false;
		}

		if (dmg0(_targetPc)) {
			return false;
		}

		if (calcEvasion()) {
			return false;
		}
		if (this._targetPc.getattr_物理格檔() > 0 && L1AttackNpc._random.nextInt(100) < this._targetPc.getattr_物理格檔()) {
            return false;
        }
		_hitRate += _npc.getLevel() + 5;

		if ((_npc instanceof L1PetInstance)) {
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 3;
		
		attackerDice += attackerDice(_targetPc);
		
        final int tgChaDodgeDown = this._targetPc.get_dodge() * 10;
        if (tgChaDodgeDown > 0) {
            attackerDice -= tgChaDodgeDown;
        }
        int defenderDice = 0;

		int defenderValue = _targetPc.getAc() * -1;

		if (_targetPc.getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAc();
		} else if (_targetPc.getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}
		int fumble = _hitRate;

		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else if (attackerDice > defenderDice) {
			_hitRate = 100;
		} else if (attackerDice <= defenderDice) {
			_hitRate = 0;
		}
        if (this._npc.getNpcTemplate().is_boss()) {
            this._hitRate += Config_Pc_Damage.BossMobHit_chance;
        }
        if (!this._npc.getNpcTemplate().is_boss()) {
            this._hitRate += Config_Pc_Damage.AllMobHit_chance;
        }
        if (this._targetPc.isCrown()) {
            this._hitRate += Config_Pc_Damage.Crown_ADD_HIT;
        }
        else if (this._targetPc.isKnight()) {
            this._hitRate += Config_Pc_Damage.Knight_ADD_HIT;
        }
        else if (this._targetPc.isElf()) {
            this._hitRate += Config_Pc_Damage.Elf_ADD_HIT;
        }
        else if (this._targetPc.isDarkelf()) {
            this._hitRate += Config_Pc_Damage.Darkelf_ADD_HIT;
        }
        else if (this._targetPc.isWizard()) {
            this._hitRate += Config_Pc_Damage.Wizard_ADD_HIT;
        }
        else if (this._targetPc.isDragonKnight()) {
            this._hitRate += Config_Pc_Damage.DragonKnight_ADD_HIT;
        }
        else if (this._targetPc.isIllusionist()) {
            this._hitRate += Config_Pc_Damage.Illusionist_ADD_HIT;
        }
        final int rnd = L1AttackNpc._random.nextInt(100) + 1;
        if (this._npc.get_ranged() >= 10 && this._hitRate > rnd && this._npc.getLocation().getTileLineDistance(new Point(this._targetX, this._targetY)) >= 2) {
            return this.calcErEvasion();
        }
        return this._hitRate >= rnd;
    }
    
	private boolean calcNpcHit() {
		if (dmg0(_targetNpc)) {
			return false;
		}

		_hitRate += _npc.getLevel() + 3;

		if ((_npc instanceof L1PetInstance)) {
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 3;

		attackerDice += attackerDice(_targetNpc);

		if (_npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {// BOSS增加命中機率
			attackerDice += 30;
		}

		int defenderDice = 0;

		int defenderValue = _targetNpc.getAc() * -1;

		if (_targetNpc.getAc() >= 0) {
			defenderDice = 10 - _targetNpc.getAc();
		} else if (_targetNpc.getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}

		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else if (attackerDice > defenderDice) {
			_hitRate = 100;
		} else if (attackerDice <= defenderDice) {
			_hitRate = 0;
		}

		int rnd = _random.nextInt(100) + 1;
		return _hitRate >= rnd;
	}
    
    @Override
    public int calcDamage() {
		switch (_calcType) {
		case 3:
			_damage = calcPcDamage();
			break;
		case 4:
			_damage = calcNpcDamage();
		}

		return _damage;
	}
    
    /**
	 * 其他傷害加成計算
	 * 
	 * @param dmg
	 * @return
	 */
	private double npcDmgMode(double dmg) {
		if (_npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {// BOSS傷害加倍
			dmg *= 2;
		}

		if (_random.nextInt(100) < 15) {// 15%機率爆擊
			dmg *= 2.0D;
		}

		dmg += _npc.getDmgup();

		if (isUndeadDamage()) {// 不死系夜間增加攻擊力
			dmg *= 1.2D;
		}

		dmg = (int) (dmg * (getLeverage() / 10.0D));

		if (_npc.isWeaponBreaked()) {
			dmg /= 2.0D;
		}

		return dmg;
	}
    
    private int calcPcDamage() {
        if (this._targetPc == null) {
            return 0;
        }
        if (L1AttackMode.dmg0(this._targetPc)) {
            this._isHit = false;
            return 0;
        }
        if (!this._isHit) {
            return 0;
        }
        if (this._targetPc.hasSkillEffect(91)) {
            final L1MagicMode magic = new L1MagicPc(this._targetPc, this._npc);
            final boolean isProbability = magic.calcProbabilityMagic(91);
            final boolean isShortDistance = this.isShortDistance();
            if (isProbability && isShortDistance) {
                this.commitCounterBarrier();
                this._npc.broadcastPacketAll(new S_SkillSound(this._targetPc.getId(), 10710));
                return 0;
            }
        }
        final int lvl = this._npc.getLevel();
        double dmg = 0.0;
        final Integer dmgStr = L1AttackList.STRD.get((int)this._npc.getStr());
        if (this._targetPc.getMapId() == 1936) {
            dmg = 30.0 + this._npc.getStr() * 0.8 + dmgStr;
        }
        else {
            dmg = L1AttackNpc._random.nextInt(lvl) + this._npc.getStr() * 0.8 + dmgStr;
        }
        if (this._npc instanceof L1PetInstance) {
            dmg += lvl / 7;
            dmg += ((L1PetInstance)this._npc).getDamageByWeapon();
        }
        dmg = this.npcDmgMode(dmg);
        dmg -= this.calcPcDefense();
        dmg -= this._targetPc.getDamageReductionByArmor() + this._targetPc.getother_ReductionDmg() + this._targetPc.getClan_ReductionDmg() + this._targetPc.get_reduction_dmg() + this._targetPc.getdolldamageReductionByArmor();
        dmg -= this._targetPc.dmgDowe();
        if (this._targetPc.getClanid() != 0) {
            dmg -= L1AttackMode.getDamageReductionByClan(this._targetPc);
        }
        if (this._targetPc.hasSkillEffect(8867)) {
            dmg = 0.0;
        }
        if (NpcMapDmg.START) {
            final double dmg2 = MapNpcDmg.getStrDmgSkill(this._npc, this._npc.getMapId());
            if (dmg2 != 0.0) {
                dmg *= dmg2 / 100.0;
            }
        }
        if (this._targetPc.isIllusionist() && this._targetPc.getlogpcpower_SkillFor4() != 0) {
            int hp = this._targetPc.getMaxHp() / this._targetPc.getCurrentHp();
            if (hp > 1) {
                if (hp > 10) {
                    hp = 10;
                }
                if (RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor4()) {
                    dmg -= hp * 40;
                    this._targetPc.sendPackets(new S_SystemMessage("\u89f8\u767c \u75db\u82e6\u5316\u8eab \u6e1b\u514d\u4e86" + hp * 40 + "\u6ef4\u50b7\u5bb3\u3002"));
                    this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 5377));
                }
            }
        }
        if (this._targetPc.hasSkillEffect(168) && this._targetPc.isElf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && this._targetPc.getElfAttr() == 1 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
            dmg *= 0.3;
            this._targetPc.sendPackets(new S_SkillSound(this._targetPc.getId(), 5377));
            this._targetPc.sendPackets(new S_SystemMessage("\u89f8\u767c \u7cbe\u901a\u80fd\u91cf \u6e1b\u514d\u50b7\u5bb3"));
        }
        if (this._targetPc.isDragonKnight() && this._targetPc.getlogpcpower_SkillFor3() != 0 && RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
            dmg -= (int)dmg * (this._targetPc.getlogpcpower_SkillFor3() * 0.01);
            this._targetPc.sendPackets(new S_SystemMessage("\u89f8\u767c \u5f37\u4e4b\u8b77\u93a7"));
        }
        final int ran = L1AttackNpc._random.nextInt(100) + 1;
        if (this._targetPc.getInventory().checkSkillType(113) && ran <= ConfigOther.armor_type1) {
            dmg *= 0.98;
        }
        if (this._targetPc.getInventory().checkSkillType(114) && ran <= ConfigOther.armor_type2) {
            dmg *= 0.95;
        }
        if (this._targetPc.getInventory().checkSkillType(115) && ran <= ConfigOther.armor_type3) {
            dmg *= 0.9;
        }
        if (this._targetPc.getInventory().checkSkillType(116) && ran <= ConfigOther.armor_type4) {
            dmg *= 0.85;
        }
        if (this._targetPc.getInventory().checkSkillType(117) && ran <= ConfigOther.armor_type5) {
            dmg *= 0.8;
        }
        if (this._targetPc.getInventory().checkSkillType(128) && ran <= ConfigOther.armor_type16) {
            dmg = 0.0;
        }
        if (this._targetPc.getInventory().checkSkillType(129) && ran <= ConfigOther.armor_type17) {
            dmg = 0.0;
        }
        if (this._targetPc.getInventory().checkSkillType(130) && ran <= ConfigOther.armor_type18) {
            dmg = 0.0;
        }
        if (this._targetPc.getInventory().checkSkillType(131) && ran <= ConfigOther.armor_type19) {
            dmg = 0.0;
        }
        if (this._targetPc.getInventory().checkSkillType(132) && ran <= ConfigOther.armor_type20) {
            dmg = 0.0;
        }
        /** [原碼] 反叛者的盾牌 機率減免傷害 */
		for (L1ItemInstance item : _targetPc.getInventory().getItems()) {
			if (item.getItemId() == 400041 && item.isEquipped()) {
				Random random = new Random();
				int r = random.nextInt(100) + 1;
				if ((item.getEnchantLevel() * 2) >= r) {
					dmg -= 50;
					_targetPc.sendPacketsAll(new S_SkillSound(_targetPc.getId(), 6320));
				}
			}
		}
        if (this._targetPc.getDmgReductionChance() > 0 && this._targetPc.getDmgReductionDmg() > 0 && ran <= this._targetPc.getDmgReductionChance()) {
            dmg -= this._targetPc.getDmgReductionDmg();
        }
        if (this._targetPc.hasSkillEffect(68)) {
            dmg /= ConfigOther.IMMUNE_TO_HARM_NPC;
        }
        boolean isNowWar = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(this._npc);
        if (castleId > 0) {
            isNowWar = ServerWarExecutor.get().isNowWar(castleId);
        }
        if (!isNowWar) {
            if (this._npc instanceof L1PetInstance) {
                dmg *= ConfigOther.petdmgotherpc;
            }
            if (this._npc instanceof L1SummonInstance) {
                final L1SummonInstance summon = (L1SummonInstance)this._npc;
                if (summon.isExsistMaster()) {
                    dmg *= ConfigOther.summondmgotherpc;
                }
            }
        }
        else if (isNowWar) {
            if (this._npc instanceof L1PetInstance) {
                dmg *= ConfigOther.petdmgotherpc_war;
            }
            if (this._npc instanceof L1SummonInstance) {
                final L1SummonInstance summon = (L1SummonInstance)this._npc;
                if (summon.isExsistMaster()) {
                    dmg *= ConfigOther.summondmgotherpc_war;
                }
            }
        }
        if (dmg <= 0.0) {
            this._isHit = false;
        }
        this.addNpcPoisonAttack(this._targetPc);
        if (!this._isHit) {
            dmg = 0.0;
        }
        return (int)dmg;
    }
    
    private int calcNpcDamage() {
        if (this._targetNpc == null) {
            return 0;
        }
        if (L1AttackMode.dmg0(this._targetNpc)) {
            this._isHit = false;
            return 0;
        }
        final int lvl = this._npc.getLevel();
        double dmg = 0.0;
        if (this._npc instanceof L1PetInstance) {
            dmg = L1AttackNpc._random.nextInt(this._npc.getNpcTemplate().get_level()) + this._npc.getStr() / 2 + 1;
            dmg += lvl / 14;
            dmg += ((L1PetInstance)this._npc).getDamageByWeapon();
            if (ConfigOther.petdmgother > 0.0) {
                dmg *= ConfigOther.petdmgother;
            }
        }
        else if (this._npc instanceof L1SummonInstance) {
            final Integer dmgStr = L1AttackList.STRD.get((int)this._npc.getStr());
            dmg = L1AttackNpc._random.nextInt(lvl) + this._npc.getStr() / 2 + dmgStr;
            if (ConfigOther.summondmgother > 0.0) {
                dmg *= ConfigOther.summondmgother;
            }
        }
        else {
            final Integer dmgStr = L1AttackList.STRD.get((int)this._npc.getStr());
            dmg = L1AttackNpc._random.nextInt(lvl) + this._npc.getStr() / 2 + dmgStr;
        }
        dmg = this.npcDmgMode(dmg);
        dmg -= this.calcNpcDamageReduction();
        this.addNpcPoisonAttack(this._targetNpc);
        if (this._targetNpc.hasSkillEffect(68)) {
            dmg /= 2.0;
        }
        if (dmg <= 0.0) {
            this._isHit = false;
        }
        if (!this._isHit) {
            dmg = 0.0;
        }
        return (int)dmg;
    }
    
    /**
	 * 夜間攻擊力增加
	 * 
	 * @return
	 */
	private boolean isUndeadDamage() {
		boolean flag = false;
		int undead = _npc.getNpcTemplate().get_undead();
		boolean isNight = L1GameTimeClock.getInstance().currentTime().isNight();
		if (isNight) {
			switch (undead) {
			case 1:// 不死系
			case 3:// 殭屍系
			case 4:// 不死系(治療系無傷害/無法使用起死回生)
				flag = true;
				break;
			}
		}
		return flag;
	}
    
	/**
	 * 毒素附加攻擊
	 * 
	 * @param target
	 */
	private void addNpcPoisonAttack(L1Character target) {
		switch (_npc.getNpcTemplate().get_poisonatk()) {
		case 1:// 出血毒
			if (15 >= _random.nextInt(100) + 1) {
				L1DamagePoison.doInfection(_npc, target, 3000, 20);
			}
			break;
		case 2:// 沉默毒
			if (15 >= _random.nextInt(100) + 1) {
				L1SilencePoison.doInfection(target);
			}
			break;
		case 4:// 麻痺毒
			if (15 >= _random.nextInt(100) + 1) {
				L1ParalysisPoison.doInfection(target, 20000, 16000);
			}
			break;
		}
	}
    
	/**
	 * 攻擊動作送出
	 */
	public void action() {
		try {
			if (_npc == null) {
				return;
			}
			if (_npc.isDead()) {
				return;
			}

			_npc.setHeading(_npc.targetDirection(_targetX, _targetY));

			boolean isLongRange = _npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1;
			int bowActId = _npc.getBowActId();

			if ((isLongRange) && (bowActId > 0)) {
				actionX1();
			} else {
				actionX2();
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 遠距離攻擊
	 */
	private void actionX1() {
		try {
			int bowActId = _npc.getBowActId();

			int actId = 1;// 預設攻擊動作
			if (getActId() > 1) {// 有指定攻擊動作編號
				actId = getActId();
			}

			if (_isHit) {// 命中
				_npc.broadcastPacketAll(
						new S_UseArrowSkill(_npc, _targetId, bowActId, _targetX, _targetY, _damage));
			} else {// 未命中
				_npc.broadcastPacketAll(new S_UseArrowSkill(_npc, bowActId, _targetX, _targetY, actId));
			}

		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 近距離攻擊
	 */
	private void actionX2() {
		try {
			int actId = 1;// 預設攻擊動作
			if (getActId() > 1) {// 有指定攻擊動作編號
				actId = getActId();
			}

			/** 搜尋可用的預設攻擊動作 */
			if (actId == 1) {
				int sprid = _npc.getNpcTemplate().get_gfxid();
				final int attack1 = SprTable.get().getAttackSpeed(sprid, 1);
				if (attack1 == 0) {// 動作1速度資料為0
					final int attack5 = SprTable.get().getAttackSpeed(sprid, 5);
					if (attack5 != 0) {
						actId = 5;
					} else {// 動作5速度資料為0
						final int attack12 = SprTable.get().getAttackSpeed(sprid, 12);
						if (attack12 != 0) {
							actId = 12;
						} else {// 動作12速度資料為0
							final int attack30 = SprTable.get().getDirSpellSpeed30(sprid);
							if (attack30 != 0) {
								actId = 30;
							} else {// 動作30速度資料為0
								final int attack18 = SprTable.get().getDirSpellSpeed(sprid);
								if (attack18 != 0) {
									actId = 18;
								} else {// 動作18速度資料為0
									// System.out.println("沒有預設攻擊動作資料 SPRID =" +
									// sprid);
									actId = 3;// 閒置動作
								}
							}
						}
					}
				}
			}
			/** 搜尋可用的預設攻擊動作 END */

			// 特定外型改變攻擊動作
			switch (_npc.getTempCharGfx()) {
			case 1780:// 烈炎獸
			case 7430:
			case 13076:
				actId = 30;
				break;
			case 2757:// 吸血鬼
			case 4104:
			case 13096:
				actId = 18;
				break;
			}

			if (_isHit) {// 命中
				if (getGfxId() > 0) {// 有動畫編號
					_npc.broadcastPacketAll(new S_UseAttackSkill(_npc, _target.getId(), getGfxId(), _targetX, _targetY,
							actId, _damage));
				} else {// 沒有動畫編號
					gfx7049();

					_npc.broadcastPacketAll(new S_AttackPacketNpc(_npc, _target, actId, _damage));
				}
			} else if (getGfxId() > 0) {// 未命中 有動畫編號
				_npc.broadcastPacketAll(
						new S_UseAttackSkill(_target, _npc.getId(), getGfxId(), _targetX, _targetY, actId, 0));
			} else {// 未命中 沒動畫編號
				_npc.broadcastPacketAll(new S_AttackPacketNpc(_npc, _target, actId));
			}

		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 幻術師外型 使用古奇獸
	 */
	private void gfx7049() {
		if (_npc.getStatus() != 58) {
			return;
		}
		boolean is = false;
		if (_npc.getTempCharGfx() == 6671) {
			is = true;
		}
		if (_npc.getTempCharGfx() == 6650) {
			is = true;
		}
		if (is) {// 幻術師外型 使用古奇獸
			_npc.broadcastPacketAll(new S_SkillSound(_npc.getId(), 7049));
		}
	}
    
	/**
	 * 傷害資訊送出
	 */
	public void commit() {
		if (_isHit) {// 命中
			switch (_calcType) {
			case NPC_PC:
				commitPc();
				break;
			case NPC_NPC:
				commitNpc();
			}
		} /*else {// 未命中
			if (_calcType == NPC_PC) {
				_targetPc.sendPacketsAll(new S_SkillSound(_targetPc.getId(), 13418));// MISS特效編號
			}
		}*/

		if (!ConfigAlt.ALT_ATKMSG) {
			return;
		}

		if (_calcType == NPC_NPC) {
			return;
		}
		if (!_targetPc.isGm()) {
			return;
		}
		String srcatk = _npc.getName();
		String tgatk = _targetPc.getName();
		String dmginfo = _isHit ? "傷害:" + _damage : "失敗";
		String hitinfo = " 命中:" + _hitRate + "% 剩餘hp:" + _targetPc.getCurrentHp();
		String x = srcatk + ">" + tgatk + " " + dmginfo + hitinfo;

		_targetPc.sendPackets(new S_ServerMessage(166, "受到NPC攻擊: " + x));
	}
    
	private void commitPc() {
		_targetPc.receiveDamage(_npc, _damage, false, false);
	}

	private void commitNpc() {
		_targetNpc.receiveDamage(_npc, _damage);
	}
    
	/**
	 * 是否為近距離攻擊
	 */
	public boolean isShortDistance() {
		boolean isShortDistance = true;
		boolean isLongRange = _npc.getLocation().getTileLineDistance(new Point(_targetX, _targetY)) > 1;
		int bowActId = _npc.getBowActId();

		if ((isLongRange) && (bowActId > 0)) {
			isShortDistance = false;
		}
		return isShortDistance;
	}
    
	/**
	 * 受到反擊屏障傷害的處理
	 */
	public void commitCounterBarrier() {
		int damage = calcCounterBarrierDamage();
		if (damage == 0) {
			return;
		}

		if (_npc.hasSkillEffect(68)) {// 聖界減傷
			damage /= 2;
		}

		/*
		 * if (damage >= _npc.getCurrentHp()) {//如果傷害大於等於目前HP damage =
		 * _npc.getCurrentHp() - 1;//變更傷害為目前HP-1(避免使用反屏掛機) }
		 */

		_npc.receiveDamage(_target, damage);
		_npc.broadcastPacketAll(new S_DoActionGFX(_npc.getId(), 2));
	}
    
    @Override
    public void calcStaffOfMana() {
    }
}
