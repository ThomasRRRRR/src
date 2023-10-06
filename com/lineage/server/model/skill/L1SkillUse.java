package com.lineage.server.model.skill;

import static com.lineage.server.model.skill.L1SkillId.*;
import com.lineage.server.model.L1Location;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;

import java.util.Random;

import com.lineage.server.ActionCodes;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.model.L1Magic;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.serverpackets.S_RangeSkill;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.S_Sound;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_PacketBoxWindShackle;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.L1Clan;

import java.util.List;

import com.lineage.server.types.Point;
import com.lineage.server.model.Instance.L1TowerInstance;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.Instance.L1TeleporterInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.Instance.L1HousekeeperInstance;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1CrownInstance;
import com.lineage.server.model.Instance.L1GuardInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_Paralysis;

import java.util.Iterator;

import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Object;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import com.lineage.server.datatables.SkillsTable;

import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.templates.L1Skills;
import com.lineage.william.L1WilliamSystemMessage;

import org.apache.commons.logging.Log;

public class L1SkillUse {
	private static final Log _log = LogFactory.getLog(L1SkillUse.class);
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOGIN = 1;
    public static final int TYPE_SPELLSC = 2;
    public static final int TYPE_NPCBUFF = 3;
    public static final int TYPE_GMBUFF = 4;
    private L1Skills _skill;
    private int _skillId;
    private int _getBuffDuration;
    private int _shockStunDuration;
    private int _getBuffIconDuration;
    private int _targetID;
    private int _mpConsume;
    private int _hpConsume;
    private int _targetX;
    private int _targetY;
    private int _dmg;
    private int _skillTime;
    private int _type;
    private int _actid;
    private int _gfxid;
    private int _heal;
    private boolean _isPK;
    private int _bookmarkId;
    private int _itemobjid;
    private boolean _checkedUseSkill;
    private int _leverage;
    private boolean _isFreeze;
    private boolean _isCounterMagic;
    private boolean _isGlanceCheckFail;
    private L1Character _user;
    private L1PcInstance _player;
    private L1NpcInstance _npc;
    private L1Character _target;
    private L1NpcInstance _targetNpc;
    private int _calcType;
    private static final int PC_PC = 1;
    private static final int PC_NPC = 2;
    private static final int NPC_PC = 3;
    private static final int NPC_NPC = 4;
    private ArrayList<TargetStatus> _targetList;
    
	// 設定魔法屏障不可抵擋的魔法
	private static final int[] EXCEPT_COUNTER_MAGIC = { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42,
			43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, SHOCK_STUN, REDUCTION_ARMOR,
			BOUNCE_ATTACK, SOLID_CARRIAGE, COUNTER_BARRIER, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110,
			111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 132, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155,
			156, 158, 159, 161, 163, 164, 165, 166, 168, 169, 170, 171, 194, 213, SOUL_OF_FLAME, ADDITIONAL_FIRE,
			DRAGON_SKIN, AWAKEN_ANTHARAS, AWAKEN_FAFURION, AWAKEN_VALAKAS, MIRROR_IMAGE, ILLUSION_OGRE, ILLUSION_LICH,
			PATIENCE, ILLUSION_DIA_GOLEM, INSIGHT, ILLUSION_AVATAR, POWERGRIP, DESPERADO, 10026, 10027, 10028, 10029,
			41472 };
    
    public L1SkillUse() {
        this._mpConsume = 0;
        this._hpConsume = 0;
        this._targetX = 0;
        this._targetY = 0;
        this._dmg = 0;
        this._skillTime = 0;
        this._type = 0;
        this._actid = 0;
        this._gfxid = 0;
        this._isPK = false;
        this._bookmarkId = 0;
        this._itemobjid = 0;
        this._checkedUseSkill = false;
        this._leverage = 10;
        this._isFreeze = false;
        this._isCounterMagic = true;
        this._isGlanceCheckFail = false;
        this._user = null;
        this._player = null;
        this._npc = null;
        this._target = null;
        this._targetNpc = null;
    }
    
    /**
	 * 攻擊倍率(1/10)
	 */
	public void setLeverage(final int i) {
		this._leverage = i;
	}

	/**
	 * 攻擊倍率(1/10)
	 * 
	 * @return
	 */
	public int getLeverage() {
		return this._leverage;
	}

	/**
	 * 是否已檢查過技能設定
	 * 
	 * @return
	 */
	private boolean isCheckedUseSkill() {
		return this._checkedUseSkill;
	}

	/**
	 * 設定是否已檢查過技能設定
	 * 
	 * @param flg
	 */
	private void setCheckedUseSkill(final boolean flg) {
		this._checkedUseSkill = flg;
	}

	/**
	 * 檢查技能相關設定
	 * 
	 * @param player
	 *            攻擊者為PC
	 * @param skillid
	 *            技能編號
	 * @param target_id
	 *            目標OBJID
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param time
	 *            時間
	 * @param type
	 *            類型
	 * @param attacker
	 *            攻擊者為NPC
	 * @return
	 */
	public boolean checkUseSkill(final L1PcInstance player, final int skillid, final int target_id, final int x,
			final int y, final int time, final int type, final L1Character attacker) {
		return checkUseSkill(player, skillid, target_id, x, y, time, type, attacker, 0, 0);
	}
	
	
    
	/**
	 * 是否通過技能相關設定的檢查
	 * 
	 * @param player
	 *            攻擊者為PC
	 * @param skillid
	 * @param target_id
	 * @param x
	 * @param y
	 * @param time
	 * @param type
	 * @param attacker
	 *            攻擊者為NPC
	 * @param actid
	 * @param gfxid
	 * @return true:檢查通過 ;false:檢查未通過
	 */
	public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, int time, int type,
			L1Character attacker, int actid, int gfxid) {
		setCheckedUseSkill(true);
		_targetList = new ArrayList<TargetStatus>(); // ターゲットリストの初期化

		_skill = SkillsTable.get().getTemplate(skillid);
		if (_skill == null) {
			return false;
		}
		_skillId = skillid;
    	_targetX = x;
    	_targetY = y;
    	_skillTime = time;
    	_type = type;
    	_actid = actid;
    	_gfxid = gfxid;
        boolean checkedResult = true;
        
        if (attacker == null) {// NPC攻擊者欄位為空
			// pc
			_player = player;
			_user = _player;

		} else {// 有設定NPC攻擊者欄位
			// npc
			_npc = (L1NpcInstance) attacker;
			_user = _npc;
		}

		if (_skill.getTarget().equals("none")) {
			_targetID = _user.getId();
			_targetX = _user.getX();
			_targetY = _user.getY();

		} else {
			_targetID = target_id;
		}
		switch (type) {
		case TYPE_NORMAL: // 通常魔法使用時
			checkedResult = this.isNormalSkillUsable();
			break;

		case TYPE_SPELLSC: // 魔法卷軸使用時
		case TYPE_NPCBUFF:
			checkedResult = true;
			break;
		}
		if (!checkedResult) {
			return false;
		}
		
		// ファイアーウォール、ライフストリームは詠唱対象が座標
		// キューブは詠唱者の座標に配置されるため例外
		// id58火牢 id63治愈能量风暴
		if ((_skillId == FIRE_WALL) || (_skillId == LIFE_STREAM)) {
			return true;
		}
				
		final L1Object object = World.get().findObject(_targetID);
		if (object instanceof L1ItemInstance) {
			return false;
		}
		if (_user instanceof L1PcInstance) {
			if (object instanceof L1PcInstance) {
				_calcType = PC_PC;

			} else {
				_calcType = PC_NPC;
				_targetNpc = (L1NpcInstance) object;
			}

		} else if (_user instanceof L1NpcInstance) {
			if (object instanceof L1PcInstance) {
				_calcType = NPC_PC;

			} else {
				_calcType = NPC_NPC;
				_targetNpc = (L1NpcInstance) object;
			}
		}
        switch (this._skillId) {
        	// 可使用傳送戒指技能
     		case TELEPORT:
     		case MASS_TELEPORT:
    			_bookmarkId = target_id;
    			break;
    		// 技能對象為道具
    		case CREATE_MAGICAL_WEAPON:
    		case BRING_STONE:
    		case BLESSED_ARMOR:
    		case ENCHANT_WEAPON:
    		case SHADOW_FANG:
    			_itemobjid = target_id;
    			break;
    		}
        
        this._target = (L1Character)object;
        
        if (!(_target instanceof L1MonsterInstance) && _skill.getTarget().equals("attack")
				&& (_user.getId() != target_id)) {
			_isPK = true; // ターゲットがモンスター以外で攻撃系スキルで、自分以外の場合PKモードとする。
		}
        
        // 初期設定ここまで

     	// 事前チェック
     	if (!(object instanceof L1Character)) { // ターゲットがキャラクター以外の場合何もしない。
     		checkedResult = false;
     	}
     	
     	// 技能發動 目標清單判定
     	makeTargetList();
     	
        if (this._user instanceof L1PcInstance) {
            final L1PcInstance pc = (L1PcInstance)this._user;
            if (!ConfigAlt.NO_CD.equals("null")) {
                boolean nocd = false;
                final String[] nocds = ConfigAlt.NO_CD.split(",");
                for (int i = 0; i < nocds.length; ++i) {
                    if (Integer.valueOf(nocds[i]) == this._skillId) {
                        nocd = true;
                        break;
                    }
                }
                if (nocd && this._type != 4) {
                    if (pc.hasSkillEffect(40000)) {
                        return false;
                    }
                    if (!pc.hasSkillEffect(40000) && this._skill.getReuseDelay() > 0) {
                        pc.setSkillEffect(40000, this._skill.getReuseDelay());
                    }
                }
            }
        }
        if ((_targetList.size() == 0) && (_user instanceof L1NpcInstance)) {
			checkedResult = false;
		}

		// 事前チェックここまで
		return checkedResult;
    }
	/**
	 * 通常のスキル使用時に使用者の状態からスキルが使用可能であるか判断する
	 *
	 * @return false スキルが使用不可能な状態である場合 在以下情况下不可使用技能
	 */
	private boolean isNormalSkillUsable() {
		// スキル使用者がPCの場合のチェック
		if (this._user instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) this._user;

			if (!this.isAttrAgrees()) { // 精霊魔法で、属性が一致しなければ何もしない。
				return false;
			}
			
			if ((this._skillId == ELEMENTAL_PROTECTION) && (pc.getElfAttr() == 0)) {
				pc.sendPackets(new S_ServerMessage(280)); // \f1魔法が失敗しました。
				return false;
			}
			
            if (this._skillId == SHINING_AURA) {
                if (pc.getWeapon() == null) {
                    pc.sendPackets(new S_SystemMessage("需要裝備匕首或長劍才能施放"));
                    return false;
                }
                
                if (pc.getWeapon().getItem().getType() != 1 && pc.getWeapon().getItem().getType() != 2) {
                    pc.sendPackets(new S_SystemMessage("需要裝備匕首或長劍才能施放"));
                    return false;
                }
            }
            
            final boolean castle_area = L1CastleLocation.checkInAllWarArea(this._player.getX(), this._player.getY(), this._player.getMapId());
            
            final int[] bow_GFX_Arrow = ConfigOther.WAR_DISABLE_SKILLS;
            
            int[] array;
            for (int length = (array = bow_GFX_Arrow).length, i = 0; i < length; ++i) {
                final int gfx = array[i];
                if (this._skillId == gfx && castle_area) {
                    this._player.sendPackets(new S_ServerMessage("戰鬥旗幟內禁止使用此魔法"));
                    return false;
                }
            }
            
            /*
			 * // 正義屬性才可使用究極光裂術 if ((this._skillId == DISINTEGRATE) &&
			 * (pc.getLawful() < 500)) { // このメッセージであってるか未確認 pc.sendPackets(new
			 * S_ServerMessage(352, "$967")); // この魔法を利用するには性向値が%0でなければなりません。
			 * return false; }
			 */

			// 同じキューブは効果範囲外であれば配置可能
			if ((this._skillId == CUBE_IGNITION) || (this._skillId == CUBE_QUAKE) || (this._skillId == CUBE_SHOCK)
					|| (this._skillId == CUBE_BALANCE)) {
				boolean isNearSameCube = false;
				int gfxId = 0;
				for (final L1Object obj : World.get().getVisibleObjects(pc, 3)) {
					if (obj instanceof L1EffectInstance) {
						final L1EffectInstance effect = (L1EffectInstance) obj;
						gfxId = effect.getGfxId();
						if (((this._skillId == CUBE_IGNITION) && (gfxId == 6706))
								|| ((this._skillId == CUBE_QUAKE) && (gfxId == 6712))
								|| ((this._skillId == CUBE_SHOCK) && (gfxId == 6718))
								|| ((this._skillId == CUBE_BALANCE) && (gfxId == 6724))) {
							isNearSameCube = true;
							break;
						}
					}
				}
				if (isNearSameCube) {
					pc.sendPackets(new S_ServerMessage(1412)); // 已在地板上召喚了魔法立方塊。
					return false;
				}
			}
			
			if ((this.isItemConsume() == false) && (!this._player.isGm())) { // 消費アイテムはあるか
				this._player.sendPackets(new S_ServerMessage(299)); // \f1施放魔法所需材料不足。
				return false;
			}
        }
        else if ((this._user instanceof L1NpcInstance && this._user.hasSkillEffect(64)) || (this._user instanceof L1NpcInstance && this._user.hasSkillEffect(8912))) {
            this._user.removeSkillEffect(64);
            this._user.removeSkillEffect(8912);
            return false;
        }
		// PC、NPC共通のチェック
		if (!this.isHPMPConsume()) { // 消費HP、MPはあるか
			return false;
		}
		return true;
    }
	
	/**
	 * pc 用技能施放判斷
	 * 
	 * @param player
	 * @param skillId
	 * @param targetId
	 * @param x
	 * @param y
	 * @param timeSecs
	 *            秒
	 * @param type
	 */
	public void handleCommands(final L1PcInstance player, final int skillId, final int targetId, final int x,
			final int y, final int timeSecs, final int type) {
		this.handleCommands(player, skillId, targetId, x, y, timeSecs, type, null);
	}
    
    public void handleCommands1(final L1PcInstance player, final int skillId, final int targetId, final int x, final int y, final int timeSecs, final int type) {
        this.handleCommands1(player, skillId, targetId, x, y, timeSecs, type, null);
    }
    
    public void handleCommands(final L1PcInstance player, final int skillId, final int targetId, final int x, final int y, final int timeSecs, final int type, final L1Character attacker) {
        try {
        	// 事前チェックをしているか？
        	if (!isCheckedUseSkill()) {
        		final boolean isUseSkill = checkUseSkill(player, skillId, targetId, x, y, timeSecs, type, attacker);

        		if (!isUseSkill) {
        			failSkill();
        			return;
        		}
        	}
            switch (type) {
            case TYPE_NORMAL: // 魔法詠唱時
				if (!_isGlanceCheckFail || (_skill.getArea() > 0) || _skill.getTarget().equals("none")) {
					runSkill();
					useConsume();// HPMP材料消耗
					sendGrfx(true);// 施法動作及特效
					sendFailMessageHandle();// 失敗訊息
					setDelay();// 技能延遲
				}
				break;

    			case TYPE_LOGIN: // 登入時（HPMP材料無消耗、無施法動作、 技能無延遲、無魔法特效）
    				runSkill();
    				break;

    			case TYPE_SPELLSC: // 魔法卷軸使用時（HPMP材料無消耗）
    				runSkill();
    				sendGrfx(true);
    				setDelay();
    				break;

    			case TYPE_NPCBUFF: // NPCBUFF使用時（HPMP材料無消耗、 技能無延遲）
    				runSkill();
    				sendGrfx(true);
    				break;

    			case TYPE_GMBUFF: // GMBUFF使用時（HPMP材料無消耗、無施法動作、 技能無延遲）
    				runSkill();
    				sendGrfx(false);
    				break;       
    				
    			case 5:
                    this.runSkill();
                    this.sendGrfx(false);
                    break;
            }
            setCheckedUseSkill(false);// 將檢查狀態初始化
        } catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
    }
    
    public void handleCommands1(final L1PcInstance player, final int skillId, final int targetId, final int x, final int y, final int timeSecs, final int type, final L1Character attacker) {
        try {
            if (!this.isCheckedUseSkill()) {
                final boolean isUseSkill = this.checkUseSkill(player, skillId, targetId, x, y, timeSecs, type, attacker);
                if (!isUseSkill) {
                    this.failSkill();
                    return;
                }
            }
            switch (type) {
            case TYPE_NORMAL: // 魔法詠唱時
				if (!_isGlanceCheckFail || (_skill.getArea() > 0) || _skill.getTarget().equals("none")) {
					runSkill();
					useConsume();// HPMP材料消耗
					sendGrfx(false);// 施法動作及特效
					sendFailMessageHandle();// 失敗訊息
					setDelay();// 技能延遲
				}
				break;
                    
            case TYPE_LOGIN: // 登入時（HPMP材料無消耗、無施法動作、 技能無延遲、無魔法特效）
				runSkill();
				break;

			case TYPE_SPELLSC: // 魔法卷軸使用時（HPMP材料無消耗）
				runSkill();
				sendGrfx(false);
				setDelay();
				break;

			case TYPE_NPCBUFF: // NPCBUFF使用時（HPMP材料無消耗、 技能無延遲）
				runSkill();
				sendGrfx(false);
				break;
				
            case 5: 
                this.runSkill();
                this.sendGrfx(false);
                break; 
            }
            setCheckedUseSkill(false);// 將檢查狀態初始化
        } catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
    }
    
    /**
	 * 法術施展失敗的處理
	 */
	private void failSkill() {
		setCheckedUseSkill(false);
		switch (_skillId) {
		case TELEPORT:
		case MASS_TELEPORT:
		case TELEPORT_TO_MATHER:
			// 解除傳送鎖定
			_player.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
			break;
		}
	}
    
    private boolean isTarget(final L1Character cha) throws Exception {
        if (cha == null) {
            return false;
        }
        
        // 副本ID不相等
     	if (_user.get_showId() != cha.get_showId()) {
     		return false;
     	}
        
     	if (_npc != null) {
			// 在目標清單中
			if (_npc.isHate(cha)) {
				return true;
			}
			// 施展者是寵物 XXX
			if (_npc instanceof L1PetInstance) {
				if (cha instanceof L1MonsterInstance) {
					return true;
				}
			}
			// 施展者是召喚獸
			if (_npc instanceof L1SummonInstance) {
				if (cha instanceof L1MonsterInstance) {
					return true;
				}
			}
		}
     	
     	// 該物件是否允許被攻擊
     	if (!CheckUtil.checkAttackSkill(cha)) {
     		return false;
     	}
     	
        boolean flg = false;
        
        // 目標是門
     	if (cha instanceof L1DoorInstance) {
     		// 目標不可破壞設置
     		if ((cha.getMaxHp() == 0) || (cha.getMaxHp() == 1)) {
     			return false;
     		}
     	}
     		
		// 目標是魔法娃娃 拒絕所有技能
		if (cha instanceof L1DollInstance) {
			return false;
		}
		
		// 目標是祭師 拒絕所有技能
        if (cha instanceof L1HierarchInstance) {
            return false;
        }
        
        
		// 目標是人物
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			// 鬼魂模式 以及 GM隱身
			if (pc.isGhost() || pc.isGmInvis()) {
				return false;
			}
		}
		
		// NPC 對 PC
		if (_calcType == NPC_PC) {
			if ((cha instanceof L1PcInstance) || (cha instanceof L1PetInstance) || (cha instanceof L1SummonInstance)) {
				flg = true;
			}
            if (cha instanceof L1DeInstance && cha.isSafetyZone()) {
                return false;
            }
        }
		// PC 對 NPC
		if (_calcType == PC_NPC) {
			// 判斷目標為人物
			if (cha instanceof L1PcInstance) {
				// 位於安全區域中
				if (cha.isSafetyZone()) {
					return false;
				}
			}
		}
		
		// 元のターゲットがPet、Summon以外のNPCの場合、PC、Pet、Summonは対象外
		if ((_calcType == PC_NPC)
				// 目標是NPC
				&& (_target instanceof L1NpcInstance)
				// 不能是寵物
				&& !(_target instanceof L1PetInstance)
				// 不能是召喚獸
				&& !(_target instanceof L1SummonInstance) && ((cha instanceof L1PetInstance)
						|| (cha instanceof L1SummonInstance) || (cha instanceof L1PcInstance))) {
			return false;
		}

		// 元のターゲットがガード以外のNPCの場合、ガードは対象外
		if ((_calcType == PC_NPC) && (_target instanceof L1NpcInstance) && !(_target instanceof L1GuardInstance)
				&& (cha instanceof L1GuardInstance)) {
			return false;
		}
		// NPC対PCでターゲットがモンスターの場合ターゲットではない。
		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK)) && (_calcType == NPC_PC)
				&& !(cha instanceof L1PetInstance) && !(cha instanceof L1SummonInstance)
				&& !(cha instanceof L1PcInstance)) {
			return false;
		}

		// NPC対NPCで使用者がMOBで、ターゲットがMOBの場合ターゲットではない。
		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK))
				&& (_calcType == NPC_NPC) && (_user instanceof L1MonsterInstance)
				&& (cha instanceof L1MonsterInstance)) {
			return false;
		}

		// 無方向範囲攻撃魔法で攻撃できないNPCは対象外
		if (_skill.getTarget().equals("none") && (_skill.getType() == L1Skills.TYPE_ATTACK)
				&& ((cha instanceof L1CrownInstance) || (cha instanceof L1DwarfInstance)
						|| (cha instanceof L1EffectInstance) || (cha instanceof L1FieldObjectInstance)
						|| (cha instanceof L1FurnitureInstance) || (cha instanceof L1HousekeeperInstance)
						|| (cha instanceof L1MerchantInstance) || (cha instanceof L1TeleporterInstance))) {
			return false;
		}
		// 攻撃系スキルで対象が自分は対象外
		if ((_skill.getType() == L1Skills.TYPE_ATTACK) && (cha.getId() == _user.getId())) {
			return false;
		}

		// ターゲットが自分でH-Aの場合効果無し
		if ((cha.getId() == _user.getId()) && (_skillId == HEAL_ALL)) {
			return false;
		}
		
		if ((((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC)
				|| ((_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN)
				|| ((_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY))
				&& (cha.getId() == _user.getId()) && (_skillId != HEAL_ALL)) {
			return true; // ターゲットがパーティーかクラン員のものは自分に効果がある。（ただし、ヒールオールは除外）
		}
		
		// 攻擊者是PC
		if ((_user instanceof L1PcInstance)
				&& (_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK))
				&& (_isPK == false)) {

			// 目標是召喚獸
			if (cha instanceof L1SummonInstance) {
				final L1SummonInstance summon = (L1SummonInstance) cha;
				// 自己的召喚獸
				if (_player.getId() == summon.getMaster().getId()) {
					return false;
				}
				// 目標是寵物
			} else if (cha instanceof L1PetInstance) {
				final L1PetInstance pet = (L1PetInstance) cha;
				// 自己的寵物
				if (_player.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
		}
				
		if ((_skill.getTarget().equals("attack") || (_skill.getType() == L1Skills.TYPE_ATTACK))
				// 目標不是怪物
				&& !(cha instanceof L1MonsterInstance)
				// 不是PK狀態
				&& (_isPK == false)
				// 目標是人物
				&& (_target instanceof L1PcInstance)) {

			L1PcInstance enemy = null;

			try {
				enemy = (L1PcInstance) cha;

			} catch (final Exception e) {
				return false;
			}

			// カウンター無所遁形術
			if ((_skillId == COUNTER_DETECTION) && (enemy.getZoneType() != 1)
					&& (cha.hasSkillEffect(INVISIBILITY) || cha.hasSkillEffect(BLIND_HIDING))) {
				return true; // インビジかブラインドハイディング中
			}
			if ((_player.getClanid() != 0) && (enemy.getClanid() != 0)) { // クラン所属中
				// 取回全部戰爭清單
				for (final L1War war : WorldWar.get().getWarList()) {
					if (war.checkClanInWar(_player.getClanname())) { // 自己的血盟在戰爭中
						if ((war.checkClanInSameWar(_player.getClanname(), enemy.getClanname())) // 雙方參加同一場戰爭

								|| (L1CastleLocation.checkInAllWarArea(enemy.getX(), enemy.getY(), enemy.getMapId()))) {// 對方在攻城旗內
							return true;
						}
					}
				}
			}
			return false; // 攻撃スキルでPKモードじゃない場合
		}
		
		if ((_user.glanceCheck(cha.getX(), cha.getY()) == false) && (_skill.isThrough() == false)) {
			// 變化類型、復活類型除外的技能 有障礙物阻擋
			if (!((_skill.getType() == L1Skills.TYPE_CHANGE) || (_skill.getType() == L1Skills.TYPE_RESTORE))) {
				_isGlanceCheckFail = true;
				return false; // 直線上に障害物がある
			}
		}
    		if (cha.hasSkillEffect(ICE_LANCE) && ((_skillId == ICE_LANCE))) {
    			return false; // アイスランス中にアイスランス、フリージングブリザード、フリージングブレス
    		}

    		if (cha.hasSkillEffect(EARTH_BIND) && (_skillId == EARTH_BIND)) {
    			return false; // アース バインド中にアース バインド
    		}
    		if (!(cha instanceof L1MonsterInstance) && ((_skillId == TAMING_MONSTER) || (_skillId == CREATE_ZOMBIE))) {
    			return false; // ターゲットがモンスターじゃない（テイミングモンスター）
    		}
    		if (cha.isDead() && ((_skillId != CREATE_ZOMBIE) && (_skillId != RESURRECTION)
    				&& (_skillId != GREATER_RESURRECTION) && (_skillId != CALL_OF_NATURE))) {
    			return false; // ターゲットが死亡している
    		}

    		if ((cha.isDead() == false) && ((_skillId == CREATE_ZOMBIE) || (_skillId == RESURRECTION)
    				|| (_skillId == GREATER_RESURRECTION) || (_skillId == CALL_OF_NATURE))) {
    			return false; // ターゲットが死亡していない
    		}

    		if (((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) && ((_skillId == CREATE_ZOMBIE)
    				|| (_skillId == RESURRECTION) || (_skillId == GREATER_RESURRECTION) || (_skillId == CALL_OF_NATURE))) {
    			return false; // ターゲットがガーディアンタワー、ドア
    		}
    		
    		if (cha instanceof L1PcInstance) {
    			final L1PcInstance pc = (L1PcInstance) cha;
    			// 目標在絕對屏障狀態下仍然有效的魔法
    			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
    				switch (_skillId) {
    				case CURSE_BLIND:
    				case WEAPON_BREAK:
    				case DARKNESS:
    				case WEAKNESS:
    				case DISEASE:
    				case FOG_OF_SLEEPING:
    				case MASS_SLOW:
    				case SLOW:
    				case CANCELLATION:
    				case SILENCE:
    				case DECAY_POTION:
    				case MASS_TELEPORT:
    				case DETECTION:
    				case COUNTER_DETECTION:
    				case ERASE_MAGIC:
    				case ENTANGLE:
    				case PHYSICAL_ENCHANT_DEX:
    				case PHYSICAL_ENCHANT_STR:
    				case BLESS_WEAPON:
    				case EARTH_SKIN:
    				case IMMUNE_TO_HARM:
    				case REMOVE_CURSE:
    				case DARK_BLIND:
    				case PHANTASM:
    					return true;
    				default:
    					return false;
    				}
    			}
    		}
    		
    		// 目標在隱身狀態(地下)
    		if (cha instanceof L1NpcInstance) {
    			final int hiddenStatus = ((L1NpcInstance) cha).getHiddenStatus();
    			switch (hiddenStatus) {
    			case L1NpcInstance.HIDDEN_STATUS_SINK:
    				switch (_skillId) {
    				case DETECTION:
    				case COUNTER_DETECTION:
    				case FREEZING_BREATH:// 暴龍之眼
    				case ARM_BREAKER:// 隱身破壞者
    					return true;
    				}
    				return false;

    			case L1NpcInstance.HIDDEN_STATUS_FLY:
    				return false;
    			}
    		}
    		
    		if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC // ターゲットがPC
    				) && (cha instanceof L1PcInstance)) {
    					flg = true;

    				} else if (((_skill.getTargetTo() & L1Skills.TARGET_TO_NPC) == L1Skills.TARGET_TO_NPC // ターゲットがNPC
    				)

    						&& ((cha instanceof L1MonsterInstance) || (cha instanceof L1NpcInstance)
    								|| (cha instanceof L1SummonInstance) || (cha instanceof L1PetInstance))) {
    					flg = true;

    				} else if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PET) == L1Skills.TARGET_TO_PET)
    						&& (_user instanceof L1PcInstance)) { // ターゲットがSummon,Pet
    					if (cha instanceof L1SummonInstance) {
    						final L1SummonInstance summon = (L1SummonInstance) cha;
    						if (summon.getMaster() != null) {
    							if (_player.getId() == summon.getMaster().getId()) {
    								flg = true;
    							}
    						}
    					}

    					if (cha instanceof L1PetInstance) {
    						final L1PetInstance pet = (L1PetInstance) cha;
    						if (pet.getMaster() != null) {
    							if (_player.getId() == pet.getMaster().getId()) {
    								flg = true;
    							}
    						}
    					}
    				}
    		
            if ((_calcType == PC_PC) && (cha instanceof L1PcInstance)) {

    			final L1PcInstance xpc = (L1PcInstance) cha;
    			if (((_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN)
    					&& (((_player.getClanid() != 0 // ターゲットがクラン員
    					) && (_player.getClanid() == xpc.getClanid())) || _player.isGm())) {
    				return true;
    			}

    			if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY)
    					&& (_player.getParty().isMember(xpc) || _player.isGm())) {
    				return true;
    			}
    		}
            return flg;
        }
    
    /**
	 * 是否為同組
	 * 
	 * @param npc
	 * @param cha
	 * @return
	 */
	private boolean isParty(final L1NpcInstance npc, final L1Character cha) {
		if (npc.getMaster() == null) {
			return false;
		}
		// 在目標清單中
		if (npc.isHate(cha)) {
			return false;
		}

		final int masterId = npc.getMaster().getId();
		
		// 目標是人物
		if (cha instanceof L1PcInstance) {
			if (cha.getId() == masterId) {
				return true;
			}
			return false;
		}

		// 目標是寵物
		if (cha instanceof L1PetInstance) {
			final L1PetInstance tgPet = (L1PetInstance) cha;
			if (tgPet.getMaster() != null && tgPet.getMaster().getId() == masterId) {
				return true;
			}
			return false;
		}
		
		// 目標是召喚獸
		if (cha instanceof L1SummonInstance) {
			final L1SummonInstance tgSu = (L1SummonInstance) cha;
			if (tgSu.getMaster() != null && tgSu.getMaster().getId() == masterId) {
				return true;
			}
			return false;
		}
        return false;
    }
	
	/**
	 * 技能發動 目標清單判定
	 */
	private void makeTargetList() {
		try {
			if (this._type == TYPE_LOGIN) { // ログイン時(死亡時、お化け屋敷のキャンセレーション含む)は使用者のみ
				this._targetList.add(new TargetStatus(this._user));
				return;
			}

			if ((this._skill.getTargetTo() == L1Skills.TARGET_TO_ME)
					&& ((this._skill.getType() & L1Skills.TYPE_ATTACK) != L1Skills.TYPE_ATTACK)) {
				this._targetList.add(new TargetStatus(this._user)); // ターゲットは使用者のみ
				return;
			}

			// 具有攻擊距離設置
			if (this._skill.getRanged() != -1) {
				if (this._user.getLocation().getTileLineDistance(this._target.getLocation()) > this._skill
						.getRanged()) {
					return; // 射程範囲外
				}

			} else {
				// 距離不可見
				if (!this._user.getLocation().isInScreen(this._target.getLocation())) {
					return; // 射程範囲外
				}
			}

			if ((this.isTarget(this._target) == false) && !(this._skill.getTarget().equals("none"))) {
				// 対象が違うのでスキルが発動しない。
				return;
			}
			
			// 直線上目標列舉
			switch (_skillId) {
				case LIGHTNING:
                    final ArrayList<L1Object> al1object = World.get().getVisibleLineObjects(this._user, this._target);
    				for (final L1Object tgobj : al1object) {
    					if (tgobj == null) {
    						continue;
    					}

    					if (!(tgobj instanceof L1Character)) { // ターゲットがキャラクター以外の場合何もしない。
    						continue;
    					}

    					final L1Character cha = (L1Character) tgobj;
    					if (this.isTarget(cha) == false) {
    						continue;
    					}
    					// 技能發動 目標清單判定:直線上目標列舉
    					this._targetList.add(new TargetStatus(cha));
    				}
                    al1object.clear();
                    return;
                }
             // 單一目標攻擊
    			if (this._skill.getArea() == 0) {
    				if (!this._user.glanceCheck(this._target.getX(), this._target.getY())) { // 直線上に障害物があるか
    					if (((this._skill.getType() & L1Skills.TYPE_ATTACK) == L1Skills.TYPE_ATTACK)
    							&& (this._skillId != 10026) && (this._skillId != 10027) && (this._skillId != 10028)
    							&& (this._skillId != 10029)) { // 安息攻撃以外の攻撃スキル
    						// ダメージも発生しないし、ダメージモーションも発生しないが、スキルは発動
    						this._targetList.add(new TargetStatus(this._target, false));
    						return;
    					}
    				}
    				this._targetList.add(new TargetStatus(this._target));

    				// 範圍攻擊
    			} else {
    				if (!this._skill.getTarget().equals("none")) {
    					this._targetList.add(new TargetStatus(this._target));
    				}

    				if ((this._skillId != HEAL_ALL) && !(this._skill.getTarget().equals("attack")
    						|| (this._skill.getType() == L1Skills.TYPE_ATTACK))) {
    					// 攻撃系以外のスキルとH-A以外はターゲット自身を含める
    					this._targetList.add(new TargetStatus(this._user));
    				}

    				List<L1Object> objects;
    				// 全畫面物件
    				if (this._skill.getArea() == -1) {
    					objects = World.get().getVisibleObjects(this._user);

    					// 指定範圍物件
    				} else {
    					objects = World.get().getVisibleObjects(this._target, this._skill.getArea());
    				}
    				// System.out.println("攻擊範圍物件數量:"+objects.size());
    				for (final L1Object tgobj : objects) {
    					if (tgobj == null) {
    						continue;
    					}

    					if (!(tgobj instanceof L1Character)) {
    						continue;
    					}

    					if (tgobj instanceof L1MonsterInstance) {
    						L1MonsterInstance mob = (L1MonsterInstance) tgobj;
    						if (mob.getNpcId() == 45166) {// 膽小的南瓜怪
    							continue;
    						}
    						if (mob.getNpcId() == 45167) {// 殘暴的南瓜怪
    							continue;
    						}
    					}
//                            else if (tgobj instanceof L1PcInstance) {
//                                final L1PcInstance tgpc = (L1PcInstance)tgobj;
//                                if ((this._skillId == 114 || this._skillId == 115 || this._skillId == 117) && this._user instanceof L1PcInstance) {
//                                    final L1PcInstance pc = (L1PcInstance)this._user;
//                                    if (pc.getClanid() != tgpc.getClanid()) {
//                                        continue;
//                                    }
//                                    final L1Clan clan = pc.getClan();
//                                    final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
//                                    L1PcInstance[] array;
//                                    for (int length = (array = clanMembers).length, i = 0; i < length; ++i) {
//                                        final L1PcInstance clanMember1 = array[i];
//                                        if (pc.getLocation().isInScreen(pc.getLocation())) {
//                                            final int castgfx = this._skill.getCastGfx();
//                                            final L1SkillUse skillUse = new L1SkillUse();
//                                            skillUse.handleCommands(clanMember1, castgfx, clanMember1.getId(), clanMember1.getX(), clanMember1.getY(), 1800, 4);
//                                        }
//                                    }
//                                }
//                            }
                            final L1Character cha = (L1Character)tgobj;
                            if (!this.isTarget(cha)) {
                                continue;
                            }
                            // 技能發動 目標清單判定:加入目標清單 - 迴圈
        					this._targetList.add(new TargetStatus(cha));
                        }
                        return;
                    }
        } catch (Exception ex) {
			// _log.error("SkillId:" + this._skillId + " UserName:" +
			// this._player.getName());
        }
    }
    
	/**
	 * 訊息發送
	 * 
	 * @param pc
	 */
	private void sendHappenMessage(final L1PcInstance pc) {
		final int msgID = this._skill.getSysmsgIdHappen();
		if (msgID > 0) {
			pc.sendPackets(new S_ServerMessage(msgID));
		}
	}

	// 失敗メッセージ表示のハンドル
	private void sendFailMessageHandle() {
		// 攻撃スキル以外で対象を指定するスキルが失敗した場合は失敗したメッセージをクライアントに送信
		// ※攻撃スキルは障害物があっても成功時と同じアクションであるべき。
		if ((_skill.getType() != L1Skills.TYPE_ATTACK) && !_skill.getTarget().equals("none")
				&& (_targetList.size() == 0)) {
			sendFailMessage();
		}
	}

	// メッセージの表示（失敗したとき）
	private void sendFailMessage() {
		final int msgID = _skill.getSysmsgIdFail();
		if ((msgID > 0) && (_user instanceof L1PcInstance)) {
			_player.sendPackets(new S_ServerMessage(msgID));
		}
	}

	// 精霊魔法の属性と使用者の属性は一致するか？（とりあえずの対処なので、対応できたら消去して下さい)
	private boolean isAttrAgrees() {
		final int magicattr = _skill.getAttr();
		if (_user instanceof L1NpcInstance) { // NPCが使った場合なんでもOK
			return true;
		}

		if ((_skill.getSkillLevel() >= 17) && (_skill.getSkillLevel() <= 22) && (magicattr != 0) // 精霊魔法で、無属性魔法ではなく、
				&& (magicattr != _player.getElfAttr()) // 使用者と魔法の属性が一致しない。
				&& !_player.isGm()) { // ただしGMは例外
			return false;
		}
		return true;
	}
    
	/**
	 * 判断技能使用需要消耗的HP/MP是否足夠
	 * 
	 * @return
	 */
	private boolean isHPMPConsume() {
		this._mpConsume = _skill.getMpConsume();
		this._hpConsume = _skill.getHpConsume();
		int currentMp = 0;
		int currentHp = 0;

		if (this._user instanceof L1NpcInstance) {// 使用者是NPC
			currentMp = _npc.getCurrentMp();
			currentHp = _npc.getCurrentHp();

		} else {// 使用者是PC

			currentMp = _player.getCurrentMp();
			currentHp = _player.getCurrentHp();
			
			// 7.6智力減少MP消耗量
			Integer minusmpconsume = L1AttackList.INTMPCONSUME.get(Integer.valueOf(_player.getInt()));
			if (minusmpconsume != null) {
				this._mpConsume -= Math.round(_mpConsume * minusmpconsume / 100.0D);// 四捨五入
			}

			
			// 裝備道具 MP消耗減少
						if ((this._skillId == PHYSICAL_ENCHANT_DEX) && this._player.getInventory().checkEquipped(20013)) { // 迅速ヘルム装備中にPE:DEX
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == HASTE) && this._player.getInventory().checkEquipped(20013)) { // 迅速ヘルム装備中にヘイスト
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == HEAL) && this._player.getInventory().checkEquipped(20014)) { // 治癒ヘルム装備中にヒール
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == EXTRA_HEAL) && this._player.getInventory().checkEquipped(20014)) { // 治癒ヘルム装備中にエキストラヒール
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == ENCHANT_WEAPON) && this._player.getInventory().checkEquipped(20015)) { // 力ヘルム装備中にエンチャントウエポン
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == DETECTION) && this._player.getInventory().checkEquipped(20015)) { // 力ヘルム装備中に無所遁形術
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == PHYSICAL_ENCHANT_STR) && this._player.getInventory().checkEquipped(20015)) { // 力ヘルム装備中にPE:STR
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == HASTE) && this._player.getInventory().checkEquipped(20008)) { // マイナーウィンドヘルム装備中にヘイスト
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						if ((this._skillId == GREATER_HASTE) && this._player.getInventory().checkEquipped(20023)) { // ウィンドヘルム装備中にグレーターヘイスト
							// this._mpConsume /= 2;
							this._mpConsume = this._mpConsume >> 1;
						}
						
			            if (this._player.getInt() > 12 && this._skillId > 8 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 13 && this._skillId > 16 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 14 && this._skillId > 23 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 15 && this._skillId > 32 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 16 && this._skillId > 40 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 17 && this._skillId > 48 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 18 && this._skillId > 56 && this._skillId <= 80) {
		                --this._mpConsume;
		            }
		            if (this._player.getInt() > 12 && this._skillId >= 87 && this._skillId <= 91) {
		                this._mpConsume -= this._player.getInt() - 12;
		            }

						if (0 < this._skill.getMpConsume()) { // 需要耗魔的魔法
							this._mpConsume = Math.max(this._mpConsume, 1); // 確保最低耗魔=1
						}

//            if (this._skill.getMpConsume() == 0) {
//                this._mpConsume = 0;
//            }
//            else if (this._mpConsume <= 0) {
//                this._mpConsume = 1;
//            }
						
			if (this._player.isGm()) {
				this._mpConsume = 0;
				}
        	}
		
		if (currentHp < this._hpConsume + 1) {
			if (this._user instanceof L1PcInstance) {
				// 279 \f1因體力不足而無法使用魔法。
				this._player.sendPackets(new S_ServerMessage(279));
				if (this._player.isGm()) {
					this._player.setCurrentHp(this._player.getMaxHp());
				}
			}
			return false;

		} else if ((this._skillId == FINAL_BURN) && (currentHp <= 100)) {
			if (this._user instanceof L1PcInstance) {
				// 279 \f1因體力不足而無法使用魔法。
				this._player.sendPackets(new S_ServerMessage(279));
			}
			return false;

		} else if (currentMp < this._mpConsume) {
			if (this._user instanceof L1PcInstance) {
				// 278 \f1因魔力不足而無法使用魔法。
				this._player.sendPackets(new S_ServerMessage(278));
				if (this._player.isGm()) {
					this._player.setCurrentMp(this._player.getMaxMp());
				}
			}
			return false;
		}

		return true;
	}
    
	// 必要材料があるか？
		// 判斷材料是否足夠
		private boolean isItemConsume() {

			final int itemConsume = this._skill.getItemConsumeId();
			final int itemConsumeCount = this._skill.getItemConsumeCount();

			if ((itemConsume == 0) || (_player.isGm())) {
				return true; // 材料を必要としない魔法

			} else if (!this._player.getInventory().checkItem(itemConsume, itemConsumeCount)) {
				return false; // 必要材料が足りなかった。
			}

			return true;
		}
    
		/**
		 * 使用技能后，相应的HP和MP、Lawful、材料的减少
		 */
		private void useConsume() {
			if (this._user instanceof L1NpcInstance) {
				// NPCの場合、HP、MPのみマイナス
				final int current_hp = this._npc.getCurrentHp() - this._hpConsume;
				this._npc.setCurrentHp(current_hp);

				final int current_mp = this._npc.getCurrentMp() - this._mpConsume;
				this._npc.setCurrentMp(current_mp);
				return;
			}
			
        if (this.isHPMPConsume()) {
            if (this._skillId == FINAL_BURN) {// ファイナル バーン
                if (this._player.getlogpcpower_SkillFor4() != 0) {
                    final int hp = (int)(this._player.getCurrentHp() * (0.05 * this._player.getlogpcpower_SkillFor4()));
                    this._player.setCurrentHp(hp);
                    this._player.setCurrentMp(1);
                }
                else {
                	_hpConsume = this._player.getCurrentHp() - 100;
    				_mpConsume = this._player.getCurrentMp() - 1;
                }
            }
			final int current_hp = this._player.getCurrentHp() - this._hpConsume;
			this._player.setCurrentHp(current_hp);

			final int current_mp = this._player.getCurrentMp() - this._mpConsume;
			this._player.setCurrentMp(current_mp);
        }
        
     // Lawfulをマイナス
     		int lawful = this._player.getLawful() + this._skill.getLawful();
     		if (lawful > 32767) {
     			lawful = 32767;
     		}
     		if (lawful < -32767) {
     			lawful = -32767;
     		}
     		this._player.setLawful(lawful);

     		final int itemConsume = this._skill.getItemConsumeId();
     		final int itemConsumeCount = this._skill.getItemConsumeCount();

     		if ((itemConsume == 0) || (_player.isGm())) {
     			return; // 材料を必要としない魔法
     		}

     		// 使用材料をマイナス
     		this._player.getInventory().consumeItem(itemConsume, itemConsumeCount);

     	}
    
		/**
		 * 更新右上角狀態圖示及效果時間
		 * 
		 * @param cha
		 * @param repetition
		 *            true重新發送狀態圖示 false 不發送狀態圖示
		 */
		private void addMagicList(final L1Character cha, final boolean repetition) {
			/// System.out.println("111111111111");
			if (_skillTime == 0) {
				_getBuffDuration = _skill.getBuffDuration() * 1000; // 効果時間
				if (_skill.getBuffDuration() == 0) {
					if (_skillId == INVISIBILITY) { // インビジビリティ
						cha.setSkillEffect(INVISIBILITY, 0);
					}
					return;
				}
			} else {
				_getBuffDuration = _skillTime * 1000; // パラメータのtimeが0以外なら、効果時間として設定する
			}

			if (_skillId == SHOCK_STUN) {
				_getBuffDuration = _shockStunDuration;
			}

			if (_skillId == CURSE_POISON) { // カーズポイズンの効果処理はL1Poisonに移譲。
				return;
			}
			if ((_skillId == CURSE_PARALYZE) || (_skillId == CURSE_PARALYZE2)) { // カーズパラライズの効果処理はL1CurseParalysisに移譲。
				return;
			}
			if (_skillId == SHAPE_CHANGE) { // シェイプチェンジの効果処理はL1PolyMorphに移譲。
				return;
			}
			if ((_skillId == BLESSED_ARMOR) || (_skillId == HOLY_WEAPON) // 武器・防具に効果がある処理はL1ItemInstanceに移譲。
					|| (_skillId == ENCHANT_WEAPON) || (_skillId == BLESS_WEAPON) || (_skillId == SHADOW_FANG)) {
				return;
			}
			if ((_skillId == ICE_LANCE) && !_isFreeze) { // 凍結失敗
				return;
			}
			final SkillMode mode = L1SkillMode.get().getSkill(this._skillId);
			if (mode == null) {// 沒有class
				cha.setSkillEffect(_skillId, _getBuffDuration);// 給予技能效果時間
			}
			// 判斷是否重新發送技能圖示
			if ((cha instanceof L1PcInstance) && repetition) { // 対象がPCで既にスキルが重複している場合
				final L1PcInstance pc = (L1PcInstance) cha;
				sendIcon(pc);
			}
		}
    
		/**
		 * 發送技能圖示
		 * 
		 * @param pc
		 */
		private void sendIcon(final L1PcInstance pc) {
			if (this._skillTime == 0) {
				this._getBuffIconDuration = this._skill.getBuffDuration(); // 効果時間

			} else {
				this._getBuffIconDuration = this._skillTime; // パラメータのtimeが0以外なら、効果時間として設定する
			}

			// System.out.println("發送技能圖示");
			switch (this._skillId) {
			case SHIELD: // 保護罩
				pc.sendPackets(new S_SkillIconShield(5, this._getBuffIconDuration));
				break;
				
			case SHADOW_ARMOR:
				pc.sendPackets(new S_SkillIconShield(3, this._getBuffIconDuration));
				break;
				
			case DRESS_DEXTERITY: // 敏捷提升
				pc.sendPackets(new S_Dexup(pc, 3, this._getBuffIconDuration));
				break;

			case DRESS_MIGHTY: // 力量提升
				pc.sendPackets(new S_Strup(pc, 3, this._getBuffIconDuration));
				break;

			case GLOWING_AURA: // 灼熱武器
				pc.sendPackets(new S_PacketBoxIconAura(113, this._getBuffIconDuration));
				break;

			case SHINING_AURA: // 閃亮之盾
				pc.sendPackets(new S_PacketBoxIconAura(114, this._getBuffIconDuration));
				break;

			case BRAVE_AURA: // 勇猛意志
				pc.sendPackets(new S_PacketBoxIconAura(116, this._getBuffIconDuration));
				break;

			case FIRE_WEAPON: // ファイアー ウェポン
				pc.sendPackets(new S_PacketBoxIconAura(147, this._getBuffIconDuration));
				break;

			case WIND_SHOT: // ウィンド ショット
				pc.sendPackets(new S_PacketBoxIconAura(148, this._getBuffIconDuration));
				break;
				
            case FIRE_BLESS: {
                pc.sendPackets(new S_PacketBoxIconAura(154, this._getBuffIconDuration));
                break;
            }
            case STORM_EYE: // ストーム アイ
    			pc.sendPackets(new S_PacketBoxIconAura(155, this._getBuffIconDuration));
    			break;

    		case EARTH_BLESS: // アース ブレス
    			pc.sendPackets(new S_SkillIconShield(7, this._getBuffIconDuration));
    			break;

    		case BURNING_WEAPON: // バーニング ウェポン
    			pc.sendPackets(new S_PacketBoxIconAura(162, this._getBuffIconDuration));
    			break;

    		case STORM_SHOT: // ストーム ショット
    			pc.sendPackets(new S_PacketBoxIconAura(165, this._getBuffIconDuration));
    			break;

    		case IRON_SKIN: // アイアン スキン
    			pc.sendPackets(new S_SkillIconShield(10, this._getBuffIconDuration));
    			break;

    		case EARTH_SKIN: // アース スキン
    			pc.sendPackets(new S_SkillIconShield(6, this._getBuffIconDuration));
    			break;

    		case PHYSICAL_ENCHANT_STR: // フィジカル エンチャント：STR
    			pc.sendPackets(new S_Strup(pc, 5, this._getBuffIconDuration));
    			break;

    		case PHYSICAL_ENCHANT_DEX: // フィジカル エンチャント：DEX
    			pc.sendPackets(new S_Dexup(pc, 5, this._getBuffIconDuration));
    			break;
    			
    		case HASTE:
    		case GREATER_HASTE: // グレーターヘイスト
    			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, this._getBuffIconDuration));
    			pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));
    			break;
    			
    		case HOLY_WALK:
    		case MOVING_ACCELERATION:
    		case WIND_WALK: // ホーリーウォーク、ムービングアクセレーション、ウィンドウォーク
    			pc.sendPackets(new S_SkillBrave(pc.getId(), 4, this._getBuffIconDuration));
    			pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 4, 0));
    			break;
    			
            case BLOODLUST: {
                pc.sendPackets(new S_SkillBrave(pc.getId(), 6, this._getBuffIconDuration));
                pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 6, 0));
                break;
            }
            case SLOW:
    		case MASS_SLOW:
    		case ENTANGLE: // スロー、エンタングル、マススロー
    			pc.sendPackets(new S_SkillHaste(pc.getId(), 2, this._getBuffIconDuration));
    			pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 2, 0));
    			break;

    		case IMMUNE_TO_HARM:
    			pc.sendPackets(new S_PacketBox(S_PacketBox.ICON_I2H, this._getBuffIconDuration));
    			break;

    		case WIND_SHACKLE:
    			pc.sendPackets(new S_PacketBoxWindShackle(pc.getId(), _getBuffIconDuration));
    			break;
        }
			pc.sendPackets(new S_SPMR(pc));// 魔攻魔防更新
			pc.sendPackets(new S_OwnCharStatus(pc));// 角色資訊更新
			pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));// 迴避率更新
    }
    
		/**
		 * 發送施法動作及魔法特效
		 * 
		 * @param isSkillAction
		 *            true 有動作 false 沒動作
		 */
		private void sendGrfx(final boolean isSkillAction) {
			if (_actid == 0) {
				_actid = _skill.getActionId();
			}
			if (_gfxid == 0) {
				_gfxid = _skill.getCastGfx();
				if (_gfxid == 0) {
					return; // 表示するグラフィックが無い
				}
			}
			
			// TODO 施展者為PC
			if (_user instanceof L1PcInstance) {
				if (_player.isMagicCritical()) {// 爆擊則動畫改用CastGfx2
					int gfxid2 = _skill.getCastGfx2();
					if (gfxid2 != 0) {
						_gfxid = gfxid2;
					}
				}
				if ((_skillId == FIRE_WALL) || (_skillId == LIFE_STREAM)) {
					final L1PcInstance pc = (L1PcInstance) _user;
					if (_skillId == FIRE_WALL) {
						pc.setHeading(pc.targetDirection(_targetX, _targetY));
						pc.sendPacketsAll(new S_ChangeHeading(pc));
					}
					pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), _actid));
					return;
				}
				
				final int targetid = this._target.getId();
				
            if (this._skillId == SHOCK_STUN || this._skillId == BONE_BREAK) {// 失敗
                if (this._targetList.size() == 0) {
                    return;
                } else {
					if (_target instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) _target;
						pc.sendPacketsAll(new S_SkillSound(pc.getId(), 4434));

					} else if (_target instanceof L1NpcInstance) {
						_target.broadcastPacketAll(new S_SkillSound(_target.getId(), 4434));
					}
					return;
				}
			}
            	if (_skillId == LIGHT) {
                    final L1PcInstance pc = (L1PcInstance)this._target;
                    pc.sendPackets(new S_Sound(145));
                    if (pc.isKnight() && pc.getlogpcpower_SkillFor3() != 0 && pc.getMeteLevel() > 0) {
                        if (pc.isEsoteric()) {
                            pc.setEsoteric(false);
                            pc.sendPackets(new S_SystemMessage("\\fU關閉轉身技能(聖法之盾)"));
                        }
                        else {
                            pc.setEsoteric(true);
                            pc.sendPackets(new S_SystemMessage("\\fU開啟轉身技能(聖法之盾)"));
                        }
                    }
                    else if (pc.isDarkelf() && pc.getlogpcpower_SkillFor5() != 0 && pc.getMeteLevel() > 0) {
                        if (pc.isEsoteric()) {
                            pc.setEsoteric(false);
                            pc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(刀劍之煙)"));
                        }
                        else {
                            pc.setEsoteric(true);
                            pc.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(刀劍之煙)"));
                        }
                    }
                    else if (pc.isWizard() && pc.getlogpcpower_SkillFor4() != 0 && pc.getMeteLevel() > 0) {
                        if (pc.isEsoteric()) {
                            pc.setEsoteric(false);
                            pc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(粉碎爆發)"));
                        }
                        else {
                            pc.setEsoteric(true);
                            int addMp = 0;
                            if (pc.getlogpcpower_SkillFor4() >= 1) {
                                addMp += pc.getlogpcpower_SkillFor4() * 10;
                            }
                            pc.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(粉碎爆發)，每秒消耗 " + addMp + " MP"));
                        }
                    }
                    else if (pc.isCrown() && pc.getlogpcpower_SkillFor2() != 0 && pc.getMeteLevel() > 0) {
                        if (pc.isEsoteric()) {
                            pc.setEsoteric(false);
                            pc.sendPackets(new S_SystemMessage("\\fU關閉轉生技能(狂暴之氣)"));
                        }
                        else {
                            pc.setEsoteric(true);
                            pc.sendPackets(new S_SystemMessage("\\fU開啟轉生技能(狂暴之氣)"));
                        }
                    }
                }
            	if ((_targetList.size() == 0) && !(_skill.getTarget().equals("none"))) {
    				// ターゲット数が０で対象を指定するスキルの場合、魔法使用エフェクトだけ表示して終了
    				final int tempchargfx = _player.getTempCharGfx();
    				switch (tempchargfx) {
    				case 5727:
    				case 5730: // シャドウ系変身のモーション対応
    					_actid = ActionCodes.ACTION_SkillBuff;
    					break;

    				case 5733:
    				case 5736:
    					_actid = ActionCodes.ACTION_Attack;
    					break;
    				}
    				if (isSkillAction) {// 是否執行施法動作
    					_player.sendPacketsAll(new S_DoActionGFX(_player.getId(), _actid));
    				}
    				return;
    			}
            	
            	// 攻擊魔法
    			if (_skill.getTarget().equals("attack") && (_skillId != TURN_UNDEAD)) {
    				// 目標對象 是否為寵物 召喚獸 虛擬人物
    				if (isPcSummonPet(_target)) {
    					if (_player.isSafetyZone() || // 自己位於安全區
    							_target.isSafetyZone() || // 目標位於安全區
    							_player.checkNonPvP(_player, _target)) { // 檢查是否可以攻擊

    						// 封包:物件攻擊(NPC / PC 技能使用)
    						_player.sendPacketsAll(
    								new S_UseAttackSkill(_player, 0, _gfxid, _targetX, _targetY, _actid, _dmg));
    						return;
    					}
    				}
    				
    				// 單體攻擊魔法
    				if (_skill.getArea() == 0) {
    					// 封包:物件攻擊(NPC / PC 技能使用)
    					_player.sendPacketsAll(
    							new S_UseAttackSkill(_player, targetid, _gfxid, _targetX, _targetY, _actid, _dmg));
    					// 有方向範圍魔法
    				} else {
    					// 封包:範圍魔法
    					_player.sendPacketsAll(
    							new S_RangeSkill(_player, _targetList, _gfxid, _actid, S_RangeSkill.TYPE_DIR));
                    }
                } else if (_skill.getTarget().equals("none") && (_skill.getType() == L1Skills.TYPE_ATTACK)) { // 無方向範圍攻擊魔法
                	// System.out.println("無方向範圍攻擊魔法 目標物件數量:" + _targetList.size());
    				_player.sendPacketsAll(new S_RangeSkill(_player, _targetList, _gfxid, _actid, S_RangeSkill.TYPE_NODIR));
                } else { // 補助魔法或詛咒魔法
    				// テレポート、マステレ、テレポートトゥマザー以外
    				if ((_skillId != TELEPORT) && (_skillId != MASS_TELEPORT) && (_skillId != TELEPORT_TO_MATHER)) {
    					// 是否執行施法動作
    					if (isSkillAction) {
    						_player.sendPacketsAll(new S_DoActionGFX(_player.getId(), _actid));
    					}

    					if ((_skillId == COUNTER_MAGIC) || // 魔法屏障
    							(_skillId == COUNTER_BARRIER) || // 反擊屏障
    							(_skillId == ARMOR_BREAK) || // 破壞盔甲
    							(_skillId == COUNTER_MIRROR)) {// 鏡反射
    						_player.sendPackets(new S_SkillSound(targetid, _gfxid));// 只發送給自己
    						_player.broadcastPacketAll(new S_SkillSound(targetid, _gfxid));// 發送給所有玩家

    					} else if (_skillId == TRUE_TARGET) { // 精準目標
    						return;
    					} else if ((_skillId == HEAL) || // 初治
    							(_skillId == EXTRA_HEAL) || // 中治
    							(_skillId == GREATER_HEAL) || // 高治
    							(_skillId == FULL_HEAL)) { // 全治
    						// 使用者隱身狀態下仍可見魔法特效
    						_player.sendPacketsAllUnderInvis(new S_SkillSound(targetid, _gfxid));

    					} else if (this._skillId == IMMUNE_TO_HARM) {
                                this._player.sendPacketsAllUnderInvis(new S_SkillSound(targetid, this._gfxid));
                                if (this._calcType == 1 && this._player.getId() != this._target.getId() && this._target.hasSkillEffect(5122)) {
                                    L1PinkName.runPinkNameTimer(this._player);
                                }
                        } else if ((_skillId == AWAKEN_ANTHARAS) || // 覺醒：安塔瑞斯
    							(_skillId == AWAKEN_FAFURION) || // 覺醒：法利昂
    							(_skillId == AWAKEN_VALAKAS)) { // 覺醒：巴拉卡斯
    						if (_skillId == _player.getAwakeSkillId()) { // 再詠唱なら解除でエフェクトなし
    							_player.sendPacketsAll(new S_SkillSound(targetid, _gfxid));

    						} else {
    							return;
    						}

    					} else if (_skillId == UNCANNY_DODGE && _player.getAc() <= -150) {// 暗影閃避 // 防禦大於-150
                                _gfxid = _skill.getCastGfx2();// 使用CastGfx2
                                _player.sendPacketsAll(new S_SkillSound(targetid, _gfxid));
                            } else {
                            	_player.sendPacketsAll(new S_SkillSound(targetid, _gfxid));
                            }
                        }
    				
    				// スキルのエフェクト表示はターゲット全員だが、あまり必要性がないので、ステータスのみ送信
    				for (final TargetStatus ts : _targetList) {
    					final L1Character cha = ts.getTarget();
    					if (cha instanceof L1PcInstance) {
    						final L1PcInstance pc = (L1PcInstance) cha;
    						pc.sendPackets(new S_SPMR(pc));// 魔攻魔防更新
    						pc.sendPackets(new S_OwnCharStatus(pc));// 角色資訊更新
    						pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));// 迴避率更新
    					}
    				}
    			}
    			_player.setMagicCritical(false);// 取消魔法爆擊狀態
    			
    			// TODO 施展者是NPC
			} else if (this._user instanceof L1NpcInstance) { // NPCがスキルを使った場合
				final int targetid = this._target.getId();

				if (this._user instanceof L1MerchantInstance) {
					this._user.broadcastPacketAll(new S_SkillSound(targetid, _gfxid));
					return;
				}

				if ((this._targetList.size() == 0) && !(this._skill.getTarget().equals("none"))) {
					// ターゲット数が０で対象を指定するスキルの場合、魔法使用エフェクトだけ表示して終了
					this._user.broadcastPacketAll(new S_DoActionGFX(this._user.getId(), _actid));
					return;
				}

				if (this._skill.getTarget().equals("attack") && (this._skillId != TURN_UNDEAD)) {
					if (this._skill.getArea() == 0) { // 単体攻撃魔法
						this._user.broadcastPacketAll(new S_UseAttackSkill(this._user, targetid, _gfxid, this._targetX,
								this._targetY, _actid, this._dmg));

					} else { // 有方向範圍攻撃魔法
						this._user.broadcastPacketAll(
								new S_RangeSkill(this._user, this._targetList, _gfxid, _actid, S_RangeSkill.TYPE_DIR));
					}

				} else if (this._skill.getTarget().equals("none") && (this._skill.getType() == L1Skills.TYPE_ATTACK)) { // 無方向範圍魔法
					// System.out.println("無方向範圍魔法");
					this._user.broadcastPacketAll(
							new S_RangeSkill(this._user, this._targetList, _gfxid, _actid, S_RangeSkill.TYPE_NODIR));

				} else { // 補助魔法或詛咒魔法
					if ((_skillId != TELEPORT) && (_skillId != MASS_TELEPORT) && (_skillId != TELEPORT_TO_MATHER)) {
						// 魔法を使う動作のエフェクトは使用者だけ
						this._user.broadcastPacketAll(new S_DoActionGFX(this._user.getId(), _actid));
						this._user.broadcastPacketAll(new S_SkillSound(targetid, _gfxid));
					}
				}
			}
    }
    
	/** 不允許重複的技能組 */
	private static final int[][] REPEATEDSKILLS = { { FIRE_WEAPON, WIND_SHOT, STORM_EYE, BURNING_WEAPON, STORM_SHOT },

			{ EARTH_SKIN, IRON_SKIN },

			{ HOLY_WALK, MOVING_ACCELERATION, WIND_WALK, STATUS_BRAVE, STATUS_ELFBRAVE, BLOODLUST, FIRE_BLESS },

			{ HASTE, GREATER_HASTE, STATUS_HASTE },

			{ PHYSICAL_ENCHANT_DEX, DRESS_DEXTERITY },

			{ PHYSICAL_ENCHANT_STR, DRESS_MIGHTY },

			{ MIRROR_IMAGE, UNCANNY_DODGE },

			{ AWAKEN_ANTHARAS, AWAKEN_FAFURION, AWAKEN_VALAKAS },

			{ DECREASE_WEIGHT, JOY_OF_PAIN },

			{ GIGANTIC, ADVANCE_SPIRIT }
	};
	
	/**
	 * 删除不能重复/同时使用的技能，图标更改为刚使用时的图标
	 * 
	 * @param cha
	 */
	private void deleteRepeatedSkills(final L1Character cha) {
		for (final int[] skills : REPEATEDSKILLS) {
			for (final int id : skills) {
				if (id == _skillId) {
					stopSkillList(cha, skills);
				}
			}
		}
	}
    
	/**
	 * 删除全部重复的正在使用的技能
	 * 
	 * @param cha
	 * @param repeat_skill
	 */
	private void stopSkillList(final L1Character cha, final int[] repeat_skill) {
		for (final int skillId : repeat_skill) {
			if (skillId != _skillId) {
				cha.removeSkillEffect(skillId);
			}
		}
	}
    
	// 技能使用延遲的處理
	private void setDelay() {
		if (this._skill.getReuseDelay() > 0) {
			L1SkillDelay.onSkillUse(_user, _skill.getReuseDelay());
		}
	}
    
	/**
	 * 發動技能效果
	 */
	private void runSkill() {
		switch (_skillId) {
		case LIFE_STREAM:// 法師技能(治癒能量風暴)
			L1SpawnUtil.spawnEffect(81169, _skill.getBuffDuration(), _targetX, _targetY, _user.getMapId(), _user, 0);
			return;

		case CUBE_IGNITION:// 幻術師技能(立方：燃燒)
			L1SpawnUtil.spawnEffect(80149, _skill.getBuffDuration(), _targetX, _targetY, _user.getMapId(), _user,
					_skillId);
			return;

		case CUBE_QUAKE:// 幻術師技能(立方：地裂)
			L1SpawnUtil.spawnEffect(80150, _skill.getBuffDuration(), _targetX, _targetY, _user.getMapId(), _user,
					_skillId);
			return;

		case CUBE_SHOCK:// 幻術師技能(立方：衝擊)
			L1SpawnUtil.spawnEffect(80151, _skill.getBuffDuration(), _targetX, _targetY, _user.getMapId(), _user,
					_skillId);
			return;

		case CUBE_BALANCE:// 幻術師技能(立方：和諧)
			L1SpawnUtil.spawnEffect(80152, _skill.getBuffDuration(), _targetX, _targetY, _user.getMapId(), _user,
					_skillId);
			return;

		case FIRE_WALL:// 法師技能(火牢)
			// System.out.println("法師技能(火牢):"+_targetX+"/"+_targetY);
			L1SpawnUtil.doSpawnFireWall(_user, _targetX, _targetY);
			return;
		}
		
		// 魔法屏障不可抵擋的魔法
		for (final int skillId : EXCEPT_COUNTER_MAGIC) {
			if (_skillId == skillId) {
				_isCounterMagic = false; // 魔法屏障無效
				break;
			}
		}
		
		// NPCにショックスタンを使用させるとonActionでNullPointerExceptionが発生するため
				// とりあえずPCが使用した時のみ
				if ((_skillId == SHOCK_STUN) && (_user instanceof L1PcInstance)) {
					_target.onAction(_player);
				}
				
                if (_skillId == BONE_BREAK && _user instanceof L1PcInstance) {
                    _target.onAction(_player);
                }
        		if (!this.isTargetCalc(_target)) {
        			return;
        		}
        		
                try {
        			TargetStatus ts = null;
        			L1Character cha = null;
        			int drainMana = 0;
        			boolean isSuccess = false;
        			int undeadType = 0;
        			int heal = 0;
        			
        			for (final Iterator<TargetStatus> iter = _targetList.iterator(); iter.hasNext();) {
        				ts = null;
        				cha = null;
        				isSuccess = false;
        				undeadType = 0;
        				ts = iter.next();
        				cha = ts.getTarget();
        				
        				// System.out.println("發動技能效果");
        				if (_npc != null) {
        					// 施展者是寵物 XXX
        					if (_npc instanceof L1PetInstance) {
        						if (isParty(_npc, cha)) {
        							ts.isCalc(false);
        							_dmg = 0;
        							continue;
        						}
        					}
        					// 施展者是召喚獸
        					if (_npc instanceof L1SummonInstance) {
        						if (isParty(_npc, cha)) {
        							ts.isCalc(false);
        							_dmg = 0;
        							continue;
        						}
        					}
        				}
        				
        				if (!ts.isCalc() || !this.isTargetCalc(cha)) {
        					ts.isCalc(false);
        					continue; // 計算する必要がない。不需要计算
        				}
        				
        				
        				final L1Magic magic = new L1Magic(_user, cha);
        				magic.setLeverage(getLeverage());
        				
        				if (cha instanceof L1MonsterInstance) { // アンデットの判定
        					undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
        				}
        				
        			
        				// 確率系スキルで失敗が確定している場合
        				// 概率系技能失败的确定
        				if (((_skill.getType() == L1Skills.TYPE_CURSE) || (_skill.getType() == L1Skills.TYPE_PROBABILITY))
        						&& isTargetFailure(cha)) {
        					iter.remove();
        					continue;
        				}
        				
        				if (cha instanceof L1PcInstance || cha instanceof L1NpcInstance) {
        					// 目標為PC或NPC
        					if (_skillTime == 0) {
        						_getBuffIconDuration = _skill.getBuffDuration(); // 効果時間

        					} else {
        						_getBuffIconDuration = _skillTime;
        					}
        				}
        				
        				deleteRepeatedSkills(cha); // 删除重复的技能
        				
        				// System.out.println("NPC對PC傷害計算 XXX:"+this._skill.getType());
        				switch (_skill.getType()) {
        				case L1Skills.TYPE_ATTACK:// 攻撃系スキル＆ターゲットが使用者以外であること。
        					if (_user.getId() != cha.getId()) {
        						// 攻击系技能和使用者除外
        						if (isUseCounterMagic(cha)) { // 魔法屏障的處理
        							iter.remove();
        							continue;
        						}
        						_dmg = magic.calcMagicDamage(_skillId);
        						if (cha.hasSkillEffect(ERASE_MAGIC)) {
        							cha.removeSkillEffect(ERASE_MAGIC); // 魔法消除
        						}
        					}
        					break;

        				case L1Skills.TYPE_CURSE:
        				case L1Skills.TYPE_PROBABILITY: // 確率系スキル
        					isSuccess = magic.calcProbabilityMagic(this._skillId);
        					if (_type == TYPE_GMBUFF) {
        						isSuccess = true;
        					}

        					if (cha.hasSkillEffect(ERASE_MAGIC) && (this._skillId != ERASE_MAGIC)) {
        						cha.removeSkillEffect(ERASE_MAGIC); // 魔法消除
        					}
                    					
                    					if (this._skillId == JOY_OF_PAIN) {
                    						if (!_player.hasSkillEffect(JOY_OF_PAIN)) {
                    							_player.setSkillEffect(JOY_OF_PAIN, 16*1000);
                    						} else {
                    							_player.sendPackets(new S_SystemMessage("已擁有此狀態"));
                    						}
                    					}

                    					if (this._skillId != FOG_OF_SLEEPING || _skillId != 212 || _skillId != 103) {
                    						cha.removeSkillEffect(FOG_OF_SLEEPING); // 沉睡之霧
                    						cha.removeSkillEffect(212);
                    						cha.removeSkillEffect(103);
                    					}

                    					if (isSuccess) { // 成功したがカウンターマジックが発動した場合、リストから削除
                    						if (this.isUseCounterMagic(cha)) { // 魔法屏障的處理
                    							iter.remove();
                    							continue;
                    						}
                    					} else { // 失敗した場合、リストから削除
                    						if ((this._skillId == FOG_OF_SLEEPING || _skillId == 212 || _skillId == 103)
                    								&& (cha instanceof L1PcInstance)) {
                    							final L1PcInstance pc = (L1PcInstance) cha;
                    							// 297 你感覺些微地暈眩。
                    							pc.sendPackets(new S_ServerMessage(297));
                    						}
                    						iter.remove();
                    						continue;
                    					}
                    					break;
                                    
        				case L1Skills.TYPE_HEAL: // 回復系スキル
        					// 回復量はマイナスダメージで表現
        					_heal = magic.calcHealing(this._skillId);
        					if (cha.hasSkillEffect(WATER_LIFE)) { // 水之元氣(回復量2倍)
        						// this._dmg *= 2;
        						// (>> 1: 除) (<< 1: 乘)
        						_heal = (_heal << 1);
        					}

        					if (cha.hasSkillEffect(POLLUTE_WATER)) { // 污濁之水(回復量1/2倍)
        						// this._dmg /= 2;
        						// (>> 1: 除) (<< 1: 乘)
        						_heal = (_heal >> 1);
        					}

        					if (cha.hasSkillEffect(ADLV80_2_2)) {// 污濁的水流(水龍副本 回復量1/2倍)
        						_heal = (_heal >> 1);
        					}

        					if (cha.hasSkillEffect(ADLV80_2_3)) {// 治癒侵蝕術
        						_heal *= -1;
        					}
        						
                                        if (this._skillId == NATURES_BLESSING && this._player.getlogpcpower_SkillFor3() != 0 && this._player.getlogpcpower_SkillFor3() >= 1 && RandomArrayList.getInc(100, 1) <= this._player.getlogpcpower_SkillFor3()) {
                                            this._heal *= 2;
                                            this._player.sendPackets(new S_SkillSound(this._player.getId(), 5377));
                                        }
                                        
                                        if (cha.isPinkName()) {
                                            L1PinkName.runPinkNameTimer(this._player);
                                        }
                                        
                                        double con1 = 1.0;
                                        if (this._calcType == 1) {
                                            for (int Int = 1; Int < this._player.getInt() / 15; ++Int) {
                                                con1 += 0.1;
                                            }
                                        }
                                        
                                        this._heal *= (int)con1;
                                        if (this._calcType == 1 && this._player.isGm()) {
                                            this._player.sendPackets(new S_SystemMessage("治癒:" + this._heal));
                                            break;
                                        }
                                        break;
                                }
        					
        					// TODO SKILL移轉
        					final SkillMode mode = L1SkillMode.get().getSkill(this._skillId);
        					if (mode != null) {// 具有skillmode
        						// 施展者是PC
        						if (this._user instanceof L1PcInstance) {
        							switch (this._skillId) {
        							case TELEPORT:// 指定傳送5
        							case MASS_TELEPORT:// 集體傳送術69
        								this._dmg = mode.start(this._player, cha, magic, this._bookmarkId);
        								break;
        								
        							case CALL_CLAN:// 呼喚盟友
        							case RUN_CLAN:// 援護盟友118
        								this._dmg = mode.start(this._player, cha, magic, this._targetID);
        								break;

        							default:
        								this._dmg = mode.start(this._player, cha, magic, this._getBuffIconDuration);
        								break;
        							}
        						}
        						
        						// 施展者是NPC
        						if (this._user instanceof L1NpcInstance) {
        							this._dmg = mode.start(this._npc, cha, magic, this._getBuffIconDuration);
        						}
        						
                                } else {// 沒有skillmode
                					// ■■■■ 個別処理のあるスキルのみ書いてください。 ■■■■
                					// 需要个别处理的技能（无法简单以技能的属系做判断）
                					// すでにスキルを使用済みの場合なにもしない 重复使用无效的技能
                					// ただしショックスタンは重ねがけ出来るため例外 冲击之晕例外
                					if (cha.hasSkillEffect(this._skillId)) {// 目標身上已有此技能效果
                						this.addMagicList(cha, true); // 更新右上角狀態圖示及效果時間
                						continue;// 略過以下處理
                					}
                				}
        					
        					if ((this._skillId == DETECTION) || (_skillId == 194) || (_skillId == 213)) { // 無所遁形術
        						if (cha instanceof L1NpcInstance) {
        							final L1NpcInstance npc = (L1NpcInstance) cha;
        							final int hiddenStatus = npc.getHiddenStatus();
        							if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
        								npc.appearOnGround(this._player);
        							}
        						}

        					}  else if (this._skillId == COUNTER_DETECTION) { // 強力無所遁形術
        						if (cha instanceof L1PcInstance) {
        							this._dmg = magic.calcMagicDamage(this._skillId);

        						} else if (cha instanceof L1NpcInstance) {
        							final L1NpcInstance npc = (L1NpcInstance) cha;
        							final int hiddenStatus = npc.getHiddenStatus();
        							if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
        								npc.appearOnGround(this._player);
        							} else {
        								this._dmg = 0;
        							}

        						} else {
        							this._dmg = 0;
        						}
        						
        						// ★★★ 回復系スキル ★★★ 恢复系技能
        					} else if ((this._skillId == HEAL) || (this._skillId == EXTRA_HEAL) || (this._skillId == GREATER_HEAL)
        							|| (this._skillId == FULL_HEAL) || (this._skillId == HEAL_ALL)
        							|| (this._skillId == NATURES_TOUCH) || (this._skillId == NATURES_BLESSING)) {
        						if (this._user instanceof L1PcInstance) {
        							cha.removeSkillEffect(WATER_LIFE); // 水之元氣
        						}
        						
        						// ★★★ 攻撃系スキル ★★★ 攻击系技能
        						// チルタッチ、バンパイアリックタッチ
        					} else if ((this._skillId == CHILL_TOUCH) || (this._skillId == VAMPIRIC_TOUCH)) {
        						heal = this._dmg;

        					} else if ((this._skillId == 10026) || (this._skillId == 10027) || (this._skillId == 10028)
        							|| (this._skillId == 10029)) { // 安息攻撃
        						if (this._user instanceof L1NpcInstance) {
        							this._user.broadcastPacketAll(new S_NpcChat(_npc, (L1PcInstance) _target, "$3717")); // さあ、おまえに安息を与えよう。

        						} else {
        							this._player.broadcastPacketAll(new S_Chat(this._player, "$3717")); // さあ、おまえに安息を与えよう。
        						}

        					} else if (this._skillId == 10057) { // 召喚傳送術
        						L1Teleport.teleportToTargetFront(cha, this._user, 1);
        						
        						// ★★★ 確率系スキル ★★★ 确率系技能
        						/*
        						 * } else if (_skillId == 20011) { // 毒霧-目標區域及周圍隨機生成5次
        						 * L1Location baseloc = _target.getLocation(); //對象的座標
        						 * SpawnPoisonArea(baseloc); //目標區域
        						 * 
        						 * for (int i = 0; i < 5; i++) {//周圍隨機生成5次 L1Location newloc
        						 * = baseloc.randomLocation(5, false);//對象周圍坐標 if (newloc !=
        						 * baseloc) { SpawnPoisonArea(newloc); } }
        						 */
        						
                                } else if (this._skillId == 10058) {
                                    if (this._calcType == 3) {
                                        final List<L1Object> objects = World.get().getVisibleObjects(this._user, 12);
                                        for (final L1Object tgobj : objects) {
                                            if (tgobj == null) {
                                                continue;
                                            }
                                            if (tgobj == this._user) {
                                                continue;
                                            }
                                            if (!(tgobj instanceof L1PcInstance)) {
                                                continue;
                                            }
                                            final L1PcInstance cha2 = (L1PcInstance)tgobj;
                                            L1Teleport.teleportToTargetFront(cha2, this._user, 1);
                                        }
                                    }
                                } else if ((this._skillId == SLOW) || (this._skillId == MASS_SLOW) || (this._skillId == ENTANGLE)) { // スロー、マス
                					// スロー、エンタングル
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						if (pc.getHasteItemEquipped() > 0) {
                							continue;
                						}
                					}
                					if (cha.getBraveSpeed() == 5) {// 具有強化勇水狀態
                						continue;
                					}
                					switch (cha.getMoveSpeed()) {
                					case 0:
                						if (cha instanceof L1PcInstance) {
                							final L1PcInstance pc = (L1PcInstance) cha;
                							pc.sendPackets(new S_SkillHaste(pc.getId(), 2, this._getBuffIconDuration));
                						}
                						cha.broadcastPacketAll(new S_SkillHaste(cha.getId(), 2, this._getBuffIconDuration));
                						cha.setMoveSpeed(2);
                						break;

                					case 1:
                						int skillNum = 0;
                						if (cha.hasSkillEffect(HASTE)) {
                							skillNum = HASTE;

                						} else if (cha.hasSkillEffect(GREATER_HASTE)) {
                							skillNum = GREATER_HASTE;

                						} else if (cha.hasSkillEffect(STATUS_HASTE)) {
                							skillNum = STATUS_HASTE;
                						}

                						if (skillNum != 0) {
                							cha.removeSkillEffect(skillNum);
                							cha.removeSkillEffect(this._skillId);
                							cha.setMoveSpeed(0);
                							continue;
                						}
                						break;
                					}
                                } else if (this._skillId == CURSE_POISON) {
                					L1DamagePoison.doInfection(this._user, cha, 3000, 5);

                				} else if (this._skillId == WEAKNESS) { // ウィークネス
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDmgup(-5);
                						pc.addHitup(-1);
                					}

                				} else if (this._skillId == DISEASE) { // ディジーズ
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addHitup(-6);
                						pc.addAc(12);
                					}

                				} else if (this._skillId == 8909) {
                                    if (cha instanceof L1PcInstance) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.addHitup(-6);
                                        pc.addAc(12);
//                                      this._player.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY觸發疾病術:對方防禦力與命中率降低3秒"));
//                                		pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY防禦力與命中率降低3秒"));
                                		this._player.sendPackets((ServerBasePacket)new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(12)));
                                		pc.sendPackets((ServerBasePacket)new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(13)));
                                    }
                                }
                                else if (this._skillId == 8910) {
                                    if (cha instanceof L1PcInstance) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.addHitup(-6);
                                        pc.addAc(12);
//                                      this._player.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY觸發疾病術:對方防禦力與命中率降低6秒"));
//                                		pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY防禦力與命中率降低6秒"));
                                		this._player.sendPackets((ServerBasePacket)new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(14)));
                                		pc.sendPackets((ServerBasePacket)new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(15)));
                                    }
                                }
                                else if (this._skillId == EARTH_BIND) {
                                    if (cha instanceof L1PcInstance) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        final boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId());
                                        if (castle_area) {
                                            return;
                                        }
                                        pc.sendPacketsAll(new S_Poison(pc.getId(), 2));
                                        pc.sendPackets(new S_Paralysis(4, true));
                                        if (pc.hasSkillEffect(1010) || pc.hasSkillEffect(1011)) {
                                            pc.cureParalaysis();
                                        }
                                    }
                                    else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
                                        final L1NpcInstance npc = (L1NpcInstance)cha;
                                        npc.broadcastPacketAll(new S_Poison(npc.getId(), 2));
                                        npc.setParalyzed(true);
                                    }
                                }
                                else if (this._skillId == ICE_LANCE) { // アイスランス
                					// 計算攻擊是否成功
                					this._isFreeze = magic.calcProbabilityMagic(this._skillId);
                					if (this._isFreeze) {// 凍結成功
                						final int time = this._skill.getBuffDuration() + 1;
                						if (cha instanceof L1PcInstance) {
                							final L1PcInstance pc = (L1PcInstance) cha;
                							// 法師技能(冰矛圍籬)
                							L1SpawnUtil.spawnEffect(81168, time, pc.getX(), pc.getY(), _user.getMapId(), _user, 0);

                							pc.sendPacketsAll(new S_Poison(pc.getId(), 2));
                							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true));

                						} else if ((cha instanceof L1MonsterInstance) || (cha instanceof L1SummonInstance)
                								|| (cha instanceof L1PetInstance)) {
                							final L1NpcInstance npc = (L1NpcInstance) cha;
                							// 法師技能(冰矛圍籬)
                							L1SpawnUtil.spawnEffect(81168, time, npc.getX(), npc.getY(), _user.getMapId(), _user, 0);

                							npc.broadcastPacketAll(new S_Poison(npc.getId(), 2));
                							npc.setParalyzed(true);
                						}
                					}

                				}
                                else if (this._skillId == FREEZING_BLIZZARD) {
                                    this._isFreeze = magic.calcProbabilityMagic(this._skillId);
                                    if (this._isFreeze) {// 凍結成功
                                        final int time = this._skill.getBuffDuration() + 1;
                                        if (cha instanceof L1PcInstance) {
                                            final L1PcInstance pc = (L1PcInstance)cha;
                                         // 法師技能(冰矛圍籬)
                							L1SpawnUtil.spawnEffect(81168, time, pc.getX(), pc.getY(), _user.getMapId(), _user, 0);

                							pc.sendPacketsAll(new S_Poison(pc.getId(), 2));
                							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true));
                                        } else if ((cha instanceof L1MonsterInstance) || (cha instanceof L1SummonInstance)
                								|| (cha instanceof L1PetInstance)) {
                                            final L1NpcInstance npc = (L1NpcInstance)cha;
                                         // 法師技能(冰矛圍籬)
                							L1SpawnUtil.spawnEffect(81168, time, npc.getX(), npc.getY(), _user.getMapId(), _user, 0);

                							npc.broadcastPacketAll(new S_Poison(npc.getId(), 2));
                							npc.setParalyzed(true);
                                        }
                                    }
                                }
                                else if (this._skillId == TURN_UNDEAD) {
                					if ((undeadType == 1) || (undeadType == 3)) {// 不死系或殭屍系
                						// ダメージを対象のHPとする。
                						this._dmg = cha.getCurrentHp();
                					}

                				}
                                else if (this._skillId == MANA_DRAIN) { // マナ ドレイン
                					final Random random = new Random();
                					final int chance = random.nextInt(10) + 5;
                					drainMana = chance + (this._user.getInt() / 2);
                					if (cha.getCurrentMp() < drainMana) {
                						drainMana = cha.getCurrentMp();
                					}

                				}
                                else if (this._skillId == WEAPON_BREAK) { // ウェポン ブレイク
                					/*
                					 * 対NPCの場合、L1Magicのダメージ算出でダメージ1/2としているので
                					 * こちらには、対PCの場合しか記入しない。 損傷量は1~(int/3)まで
                					 */
                					if ((this._calcType == PC_PC) || (this._calcType == NPC_PC)) {
                						if (cha instanceof L1PcInstance) {
                							final L1PcInstance pc = (L1PcInstance) cha;
                							final L1ItemInstance weapon = pc.getWeapon();
                							if (weapon != null) {
                								final Random random = new Random();
                								final int weaponDamage = random.nextInt(this._user.getInt() / 3) + 1;
                								// \f1あなたの%0が損傷しました。
                								pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
                								pc.getInventory().receiveDamage(weapon, weaponDamage);
                							}
                						}
                					} else {
                						((L1NpcInstance) cha).setWeaponBreaked(true);
                					}

                				}
                                else if (this._skillId == FOG_OF_SLEEPING) {
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, true));
                					}
                					cha.setSleeped(true);
                				}
                                else if (this._skillId == GUARD_BRAKE) { // ガードブレイク
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(10);
                					}
                				}
                                else if (this._skillId == HORROR_OF_DEATH) { // ホラーオブデス
                					if (cha instanceof L1PcInstance) {
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addStr(-3);
                						pc.addInt(-3);
                					}
                				}
        					/** 對PC使用技能的情況 */
        					if ((this._calcType == PC_PC) || (this._calcType == NPC_PC)) {
        						// ★★★ 特殊系スキル★★★ 特殊技能
        						if (this._skillId == CREATE_MAGICAL_WEAPON) { // クリエイト
        							// マジカル ウェポン
        							final L1PcInstance pc = (L1PcInstance) cha;
        							final L1ItemInstance item = pc.getInventory().getItem(this._itemobjid);
        							if ((item != null) && (item.getItem().getType2() == 1)) {
        								final int item_type = item.getItem().getType2();
        								final int safe_enchant = item.getItem().get_safeenchant();
        								final int enchant_level = item.getEnchantLevel();
        								String item_name = item.getName();
        								if (safe_enchant < 0) { // 強化不可
        									pc.sendPackets( // \f1何も起きませんでした。
        											new S_ServerMessage(79));
        								} else if (safe_enchant == 0) { // 安全圏+0
        									pc.sendPackets( // \f1何も起きませんでした。
        											new S_ServerMessage(79));
        								} else if ((item_type == 1) && (enchant_level == 0)) {
        									if (!item.isIdentified()) {// 未鑑定
        										pc.sendPackets( // \f1%0が%2%1光ります。
        												new S_ServerMessage(161, item_name, "$245", "$247"));
        									} else {
        										item_name = "+0 " + item_name;
        										pc.sendPackets( // \f1%0が%2%1光ります。
        												new S_ServerMessage(161, "+0 " + item_name, "$245", "$247"));
        									}
                                                item.setEnchantLevel(1);
                                                pc.getInventory().updateItem(item, 4);
                                            }
                                            else {
                                                pc.sendPackets(new S_ServerMessage(79));
                                            }
                                        }
                                        else {
                                            pc.sendPackets(new S_ServerMessage(79));
                                        }
                                    }
                                    else if (this._skillId == BRING_STONE) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        final Random random3 = new Random();
                                        final L1ItemInstance item2 = pc.getInventory().getItem(this._itemobjid);
                                        if (item2 != null) {
                                            final int dark = (int)(10.0 + pc.getLevel() * 0.8 + (pc.getWis() - 6) * 1.2);
                                            final int brave = (int)(dark / 2.1);
                                            final int wise = (int)(brave / 2.0);
                                            final int kayser = (int)(wise / 1.9);
                                            final int chance2 = random3.nextInt(100) + 1;
                                            if (item2.getItem().getItemId() == 40320) {
                                                pc.getInventory().removeItem(item2, 1L);
                                                if (dark >= chance2) {
                                                    pc.getInventory().storeItem(40321, 1L);
                                                    pc.sendPackets(new S_ServerMessage(403, "$2475"));
                                                }
                                                else {
                                                    pc.sendPackets(new S_ServerMessage(280));
                                                }
                                            }
                                            else if (item2.getItem().getItemId() == 40321) {
                                                pc.getInventory().removeItem(item2, 1L);
                                                if (brave >= chance2) {
                                                    pc.getInventory().storeItem(40322, 1L);
                                                    pc.sendPackets(new S_ServerMessage(403, "$2476"));
                                                }
                                                else {
                                                    pc.sendPackets(new S_ServerMessage(280));
                                                }
                                            }
                                            else if (item2.getItem().getItemId() == 40322) {
                                                pc.getInventory().removeItem(item2, 1L);
                                                if (wise >= chance2) {
                                                    pc.getInventory().storeItem(40323, 1L);
                                                    pc.sendPackets(new S_ServerMessage(403, "$2477"));
                                                }
                                                else {
                                                    pc.sendPackets(new S_ServerMessage(280));
                                                }
                                            }
                                            else if (item2.getItem().getItemId() == 40323) {
                                                pc.getInventory().removeItem(item2, 1L);
                                                if (kayser >= chance2) {
                                                    pc.getInventory().storeItem(40324, 1L);
                                                    pc.sendPackets(new S_ServerMessage(403, "$2478"));
                                                }
                                                else {
                                                    pc.sendPackets(new S_ServerMessage(280));
                                                }
                                            }
                                        }
                                    }
                                    else if (this._skillId == ABSOLUTE_BARRIER) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.stopHpRegeneration();
                                        pc.stopMpRegeneration();
                                    }
                                    if (this._skillId == LIGHT) {
                                        if (this._skillId == LIGHT) {
                                            final L1PcInstance pc = (L1PcInstance)this._target;
                                            pc.sendPackets(new S_Sound(145));
                                        }
                                    }
                                    else if (this._skillId == GLOWING_AURA) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.addHitup(5);
                                        pc.addBowHitup(5);
                                        pc.addDmgup(5);
                                        pc.addBowDmgup(5);
//                                      pc.addMr(20);
                                        pc.sendPackets(new S_SPMR(pc));
                                        pc.sendPackets(new S_PacketBoxIconAura(113, this._getBuffIconDuration));
                                    }
                                    else if (this._skillId == AREA_OF_SILENCE) {
                                        this._isFreeze = magic.calcProbabilityMagic(AREA_OF_SILENCE);
                                        if (this._isFreeze && cha instanceof L1PcInstance) {
                                            final L1PcInstance pc = (L1PcInstance)cha;
                                            for (final L1Object object : World.get().getVisibleObjects(pc, 3)) {
                                                if (object instanceof L1PcInstance) {
                                                    final L1PcInstance red = (L1PcInstance)object;
                                                    final L1SkillUse l1skilluse = new L1SkillUse();
                                                    pc.sendPacketsAll(new S_SkillSound(red.getId(), 10708));
                                                    l1skilluse.handleCommands(pc, AREA_OF_SILENCE, pc.getId(), pc.getX(), pc.getY(), 0, 4);
                                                }
                                            }
                                        }
                                    }
                                    else if (this._skillId == SHINING_AURA) { // 閃亮之盾
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(-8);
                						pc.sendPackets(new S_PacketBoxIconAura(114, this._getBuffIconDuration));

                					}
                                    else if (this._skillId == BRAVE_AURA) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
//                                        pc.addDmgup(5);
                                        pc.sendPackets(new S_PacketBoxIconAura(116, this._getBuffIconDuration));
                                    }
                                    else if (this._skillId == SHIELD) { // 保護罩
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(-2);
                						pc.sendPackets(new S_SkillIconShield(2, this._getBuffIconDuration));

                					} else if (this._skillId == DRESS_DEXTERITY) { // 敏捷提升
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDex((byte) 3);
                						pc.sendPackets(new S_Dexup(pc, 3, this._getBuffIconDuration));
                						pc.resetBaseAc();

                					} else if (this._skillId == DRESS_MIGHTY) { // 力量提升
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addStr((byte) 3);
                						pc.sendPackets(new S_Strup(pc, 3, this._getBuffIconDuration));

                					}
                                    else if (this._skillId == SHADOW_FANG) { // シャドウ ファング
                						final L1PcInstance pc = (L1PcInstance) cha;
                						final L1ItemInstance item = pc.getInventory().getItem(this._itemobjid);
                						if ((item != null) && (item.getItem().getType2() == 1)) {
                							item.setSkillWeaponEnchant(pc, this._skillId, this._skill.getBuffDuration() * 1000);
//                							pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951,this._skill.getBuffDuration(), true));
                						} else {
                							pc.sendPackets(new S_ServerMessage(79));
                						}

                					}
                                    else if (this._skillId == ENCHANT_WEAPON) { // エンチャント ウェポン
                						final L1PcInstance pc = (L1PcInstance) cha;
                						final L1ItemInstance item = pc.getInventory().getItem(this._itemobjid);
                						if ((item != null) && (item.getItem().getType2() == 1)) {
                							pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
                							item.setSkillWeaponEnchant(pc, this._skillId, this._skill.getBuffDuration() * 1000);
//                							pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, this._skill.getBuffDuration(), true));
                						} else {
                							pc.sendPackets(new S_ServerMessage(79));
                						}

                					}
                                    else if ((this._skillId == HOLY_WEAPON) || (this._skillId == BLESS_WEAPON)) { // ブレス
										// ウェポン
                                    	if (!(cha instanceof L1PcInstance)) {
                                    		return;
                                    	}
                                    	final L1PcInstance pc = (L1PcInstance) cha;
                                    	if (pc.getWeapon() == null) {
                                    		pc.sendPackets(new S_ServerMessage(79));
                                    		return;
                                    	}
                                    	for (final L1ItemInstance item : pc.getInventory().getItems()) {
                                    		if (pc.getWeapon().equals(item)) {
                                    			pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
                                    			item.setSkillWeaponEnchant(pc, this._skillId, this._skill.getBuffDuration() * 1000);
                                    			return;
                                    		}

//                                    		if (pc.getSecondWeapon() != null && pc.getSecondWeapon().equals(item)) {
//                                    			pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
//                                    			item.setSkillWeaponEnchant(pc, this._skillId, this._skill.getBuffDuration() * 1000);
//                                    			return;
//                                    		}
                                    	}

                                    }
                                    else if (this._skillId == AQUA_PROTECTER) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.setSkillEffect(160, 960000);
                                        pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                                    }
                                    else if (this._skillId == BLESSED_ARMOR) { // ブレスド アーマー
                						final L1PcInstance pc = (L1PcInstance) cha;
                						final L1ItemInstance item = pc.getInventory().getItem(this._itemobjid);
                						if ((item != null) && (item.getItem().getType2() == 2) && (item.getItem().getType() == 2)) {
                							pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"));
                							item.setSkillArmorEnchant(pc, this._skillId, this._skill.getBuffDuration() * 1000);
                						} else {
                							pc.sendPackets(new S_ServerMessage(79));
                						}

                					}
                                    else if (this._skillId == EARTH_BLESS) { // アース ブレス
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(-7);
                						pc.sendPackets(new S_SkillIconShield(7, this._getBuffIconDuration));

                					} else if (this._skillId == RESIST_MAGIC) { // レジスト マジック
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addMr(10);
                						pc.sendPackets(new S_SPMR(pc));

                					} else if (this._skillId == CLEAR_MIND) { // クリアー マインド
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addWis((byte) 3);
                						pc.resetBaseMr();

                					}
                                    else if (this._skillId == RESIST_ELEMENTAL) { // レジスト
										// エレメント
                                    	final L1PcInstance pc = (L1PcInstance) cha;
                                    	pc.addWind(10);
                                    	pc.addWater(10);
                                    	pc.addFire(10);
                                    	pc.addEarth(10);
                                    	pc.sendPackets(new S_OwnCharAttrDef(pc));
                                    }
                                    else if (this._skillId == ELEMENTAL_PROTECTION) { // エレメンタルプロテクション
                						final L1PcInstance pc = (L1PcInstance) cha;
                						final int attr = pc.getElfAttr();
                						if (attr == 1) {
                							pc.addEarth(50);
                						} else if (attr == 2) {
                							pc.addFire(50);
                						} else if (attr == 4) {
                							pc.addWater(50);
                						} else if (attr == 8) {
                							pc.addWind(50);
                						}
                					}
                                    else if ((this._skillId == INVISIBILITY) || (this._skillId == BLIND_HIDING)) { // インビジビリティ、ブラインドハイディング
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.sendPackets(new S_Invis(pc.getId(), 1));
                						pc.broadcastPacketAll(new S_RemoveObject(pc));

                					} else if (this._skillId == IRON_SKIN) { // アイアン スキン
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(-10);
                						pc.sendPackets(new S_SkillIconShield(10, this._getBuffIconDuration));

                					} else if (this._skillId == EARTH_SKIN) { // アース スキン
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(-6);
                						pc.sendPackets(new S_SkillIconShield(6, this._getBuffIconDuration));

                					} else if (this._skillId == PHYSICAL_ENCHANT_STR) { // フィジカルエンチャント：STR
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addStr((byte) 5);
                						pc.sendPackets(new S_Strup(pc, 5, this._getBuffIconDuration));

                					} else if (this._skillId == PHYSICAL_ENCHANT_DEX) { // フィジカルエンチャント：DEX
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDex((byte) 5);
                						pc.sendPackets(new S_Dexup(pc, 5, this._getBuffIconDuration));
                						pc.resetBaseAc();
                					}
                                    else if (this._skillId == FIRE_WEAPON) { // ファイアー ウェポン
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDmgup(4);
                						pc.sendPackets(new S_PacketBoxIconAura(147, this._getBuffIconDuration));
                					}
                                    
//                                    else if (this._skillId == FIRE_BLESS) {
//                                        final L1PcInstance pc = (L1PcInstance)cha;
//                                        pc.addDmgup(4);
//                                        pc.sendPackets(new S_PacketBoxIconAura(154, this._getBuffIconDuration));
//                                    }
                                    
                                    else if (this._skillId == BURNING_WEAPON) { // バーニング ウェポン
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDmgup(6);
                						pc.addHitup(3);
                						pc.sendPackets(new S_PacketBoxIconAura(162, this._getBuffIconDuration));

                					} else if (this._skillId == WIND_SHOT) { // ウィンド ショット
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addBowHitup(6);
                						pc.sendPackets(new S_PacketBoxIconAura(148, this._getBuffIconDuration));

                					} else if (this._skillId == STORM_EYE) { // ストーム アイ
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addBowHitup(2);
                						pc.addBowDmgup(3);
                						pc.sendPackets(new S_PacketBoxIconAura(155, this._getBuffIconDuration));

                					}
                                    else if (this._skillId == STORM_SHOT) { // ストーム ショット
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addBowDmgup(5);
                						pc.addBowHitup(-1);
                						pc.sendPackets(new S_PacketBoxIconAura(165, this._getBuffIconDuration));
                					}
                                    else if (this._skillId == BERSERKERS) { // 狂暴術
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addAc(10);
                						pc.addDmgup(5);
                						pc.addHitup(5);
                						pc.stopHpRegeneration();

                					}
                                    else if (this._skillId == GREATER_HASTE) { // グレーター ヘイスト
                						final L1PcInstance pc = (L1PcInstance) cha;
                						if (pc.getHasteItemEquipped() > 0) {
                							continue;
                						}
                						if (pc.getMoveSpeed() != 2) { // スロー中以外
                							pc.setDrink(false);
                							pc.setMoveSpeed(1);
                							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, this._getBuffIconDuration));
                							pc.broadcastPacketAll(new S_SkillHaste(pc.getId(), 1, 0));

                						} else { // スロー中
                							int skillNum = 0;
                							if (pc.hasSkillEffect(SLOW)) {
                								skillNum = SLOW;
                							} else if (pc.hasSkillEffect(MASS_SLOW)) {
                								skillNum = MASS_SLOW;
                							} else if (pc.hasSkillEffect(ENTANGLE)) {
                								skillNum = ENTANGLE;
                							}
                							if (skillNum != 0) {
                								pc.removeSkillEffect(skillNum);
                								pc.removeSkillEffect(GREATER_HASTE);
                								pc.setMoveSpeed(0);
                								continue;
                							}
                						}
                					}
                                    else if ((this._skillId == HOLY_WALK) || (this._skillId == MOVING_ACCELERATION)
                							|| (this._skillId == WIND_WALK)) { // ホーリーウォーク、ムービングアクセレーション、ウィンドウォーク
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.setBraveSpeed(4);
                						pc.sendPackets(new S_SkillBrave(pc.getId(), 4, this._getBuffIconDuration));
                						pc.broadcastPacketAll(new S_SkillBrave(pc.getId(), 4, 0));

                					}
                                    else if (this._skillId == ILLUSION_OGRE) { // イリュージョン：オーガ
                						final L1PcInstance pc = (L1PcInstance) cha;
                						pc.addDmgup(4);
                						pc.addHitup(4);

                					}
                                    else if (this._skillId == ILLUSION_DIA_GOLEM) {
                                        final L1PcInstance pc = (L1PcInstance)cha;
                                        pc.addAc(-8);
                                    }
                                }
                                /** 對NPC使用技能的情況 */
                				if ((_calcType == PC_NPC) || (_calcType == NPC_NPC)) {
                					// ★★★ ペット系スキル ★★★ 宠物使用的技能
                					if ((_skillId == TAMING_MONSTER) && ((L1MonsterInstance) cha).getNpcTemplate().isTamable()) { // 
                                        if (ConfigOther.petcountcha) {
                                            int petcost = 0;
                                            final int petCount = 0;
                                            final Object[] petlist = this._user.getPetList().values().toArray();
                                            if (petlist.length > ConfigOther.tamingmonstercount) {
                                                this._player.sendPackets(new S_ServerMessage(489));
                                                return;
                                            }
                                            Object[] array;
                                            for (int length2 = (array = petlist).length, j = 0; j < length2; ++j) {
                                                final Object pet = array[j];
                                                petcost += ((L1NpcInstance)pet).getPetcost();
                                            }
                                            int charisma = this._user.getCha();
                                            charisma -= petcost;
                                            charisma /= 2;
                                            if (charisma <= 0) {
                                                this._player.sendPackets(new S_ServerMessage(489));
                                                return;
                                            }
                                            final L1SummonInstance summon = new L1SummonInstance(this._targetNpc, this._user, false);
                                            ((L1NpcInstance)(this._target = summon)).setPetcost(2);
                                        }
                                        else {
                                            int petcost = 0;
                                            final Object[] petlist2 = this._user.getPetList().values().toArray();
                                            if (petlist2.length > ConfigOther.tamingmonstercount) {
                                                this._player.sendPackets(new S_ServerMessage(489));
                                                return;
                                            }
                                            Object[] array2;
                                            for (int length3 = (array2 = petlist2).length, k = 0; k < length3; ++k) {
                                                final Object pet2 = array2[k];
                                                petcost += ((L1NpcInstance)pet2).getPetcost();
                                            }
                                            int charisma2 = this._user.getCha();
                                            if (this._player.isElf()) {
                                                charisma2 += 12;
                                            }
                                            else if (this._player.isWizard()) {
                                                charisma2 += 6;
                                            }
                                            charisma2 -= petcost;
                                            if (charisma2 >= 6) {
                                                final L1SummonInstance summon2 = new L1SummonInstance(this._targetNpc, this._user, false);
                                                this._target = summon2;
                                            }
                                            else {
                                                this._player.sendPackets(new S_ServerMessage(319));
                                            }
                                        }
                                    }
                					else if (this._skillId == CREATE_ZOMBIE) { // クリエイトゾンビ
                						int petcost = 0;
                						final Object[] petlist = this._user.getPetList().values().toArray();
                						for (final Object pet : petlist) {
                							// 現在のペットコスト
                							petcost += ((L1NpcInstance) pet).getPetcost();
                						}
                						int charisma = this._user.getCha();
                						if (this._player.isElf()) { // エルフ
                							charisma += 12;
                						} else if (this._player.isWizard()) { // ウィザード
                							charisma += 6;
                						}
                						charisma -= petcost;
                						if (charisma >= 6) { // ペットコストの確認
                							final L1SummonInstance summon = new L1SummonInstance(this._targetNpc, this._user, true);
                							this._target = summon; // ターゲット入替え
                						} else {
                							this._player.sendPackets(new S_ServerMessage(319)); // \f1これ以上のモンスターを操ることはできません。
                						}
                                    }
                					else if (this._skillId == WEAK_ELEMENTAL) { // ウィーク エレメンタル
                						if (cha instanceof L1MonsterInstance) {
                							final L1Npc npcTemp = ((L1MonsterInstance) cha).getNpcTemplate();
                							final int weakAttr = npcTemp.get_weakAttr();
                							if ((weakAttr & 1) == 1) { // 地
                								cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2169));
                							}
                							if ((weakAttr & 2) == 2) { // 火
                								cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2167));
                							}
                							if ((weakAttr & 4) == 4) { // 水
                								cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2166));
                							}
                							if ((weakAttr & 8) == 8) { // 風
                								cha.broadcastPacketAll(new S_SkillSound(cha.getId(), 2168));
                							}
                						}
                                    }
                					else if (this._skillId == RETURN_TO_NATURE) { // リターントゥネイチャー
                						if (cha instanceof L1SummonInstance) {
                							final L1SummonInstance summon = (L1SummonInstance) cha;
                							summon.broadcastPacketAll(new S_SkillSound(summon.getId(), 2245));
                							summon.returnToNature();

                						} else {
                							if (this._user instanceof L1PcInstance) {
                								this._player.sendPackets(new S_ServerMessage(79));
                							}
                						}
                                    }
                                }
                				// ■■■■ 個別処理ここまで ■■■■

                				// 治癒性魔法攻擊不死係的怪物。
                				if ((this._skill.getType() == L1Skills.TYPE_HEAL) && (this._calcType == PC_NPC) && (undeadType == 1)) {// 不死系
                					this._dmg = _heal; // もし、アンデットで回復系スキルならばダメージになる。
                				}

                				// 治癒性魔法無法對此不死係起作用
                				if ((this._skill.getType() == L1Skills.TYPE_HEAL) && (this._calcType == PC_NPC) && (undeadType == 4)) {// 不死系(治癒術無傷害)
                					this._heal = 0; // もし、アンデット系ボスで回復系スキルならば無効
                				}

                				// 無法對城門、守護塔補血
                				if (((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) && (this._heal < 0)) { // ガーディアンタワー、ドアにヒールを使用
                					this._heal = 0;
                				}

                				// 執行傷害及吸魔計算
                				if ((this._dmg != 0) || (drainMana != 0)) {
                					// System.out.println("結果質2:(HP) " + this._dmg);
                					magic.commit(this._dmg, drainMana); // ダメージ系、回復系の値をターゲットにコミットする。
                				}

                				// 補血判斷
                				if ((_skill.getType() == L1Skills.TYPE_HEAL) && (_heal != 0) && (undeadType != 1)) {
                					cha.setCurrentHp(_heal + cha.getCurrentHp());
                				}

                				// 非治癒性魔法補血判斷(寒戰、吸吻等)
                				if (heal > 0) {
                					_user.setCurrentHp(heal + _user.getCurrentHp());
                				}

                				if (cha instanceof L1PcInstance) { // ターゲットがPCならば、ACとステータスを送信
                					final L1PcInstance pc = (L1PcInstance) cha;
                					pc.sendPackets(new S_SPMR(pc));// 魔攻魔防更新
                					pc.sendPackets(new S_OwnCharStatus(pc));// 角色資訊更新
                					pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));// 迴避率更新

                					sendHappenMessage(pc); // ターゲットにメッセージを送信
                				}

                                this.addMagicList(cha, false);
        				}
        				if ((_skillId == DETECTION) || (_skillId == COUNTER_DETECTION) || (_skillId == FREEZING_BREATH)
        						|| (_skillId == ARM_BREAKER)) { // 無所類型技能
        					detection(_player);
        				}
        			} catch (final Exception e) {
        				_log.error(e.getLocalizedMessage(), e);
        			}
        }

    
	private static void summonMonster(L1PcInstance pc, String s) {
		String[] summonstr_list;
		int[] summonid_list;
		int[] summonlvl_list;
		int[] summoncha_list;
		int summonid = 0;
		int levelrange = 0;
		int summoncost = 0;
		summonstr_list = new String[] { "7", "263", "519", "8", "264", "520", "9", "265", "521", "10", "266", "522",
				"11", "267", "523", "12", "268", "524", "13", "269", "525", "14", "270", "526", "15", "271", "527",
				"16", "17", "18", "274" };
		summonid_list = new int[] { 81210, 81211, 81212, 81213, 81214, 81215, 81216, 81217, 81218, 81219, 81220, 81221,
				81222, 81223, 81224, 81225, 81226, 81227, 81228, 81229, 81230, 81231, 81232, 81233, 81234, 81235, 81236,
				81237, 81238, 81239, 81240 };
		summonlvl_list = new int[] { 28, 28, 28, 32, 32, 32, 36, 36, 36, 40, 40, 40, 44, 44, 44, 48, 48, 48, 52, 52, 52,
				56, 56, 56, 60, 60, 60, 64, 68, 72, 72 };
		summoncha_list = new int[] { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
				14, 36, 36, 44 };
		// サモンの種類、必要Lv、ペットコストを得る
		for (int loop = 0; loop < summonstr_list.length; loop++) {
			if (s.equalsIgnoreCase(summonstr_list[loop])) {
				summonid = summonid_list[loop];
				levelrange = summonlvl_list[loop];
				summoncost = summoncha_list[loop];
				break;
			}
		}
		// Lv不足
		if (pc.getLevel() < levelrange) {
			// レベルが低くて該当のモンスターを召還することができません。
			pc.sendPackets(new S_ServerMessage(743));
			return;
		}

		int petcost = 0;
		for (L1NpcInstance petNpc : pc.getPetList().values()) {
			// 現在のペットコスト
			petcost += petNpc.getPetcost();
		}

		int pcCha = pc.getCha();
		int charisma = 0;
		int summoncount = 0;
		if ((levelrange <= 56) // max count = 5
				|| (levelrange == 64)) { // max count = 2
			if (pcCha > 34) {
				pcCha = 34;
			}
		} else if (levelrange == 60) {
			if (pcCha > 30) { // max count = 3
				pcCha = 30;
			}
		} else if (levelrange > 64) {
			if (pcCha > 44) { // max count = 1
				pcCha = 44;
			}
		}
		charisma = pcCha + 6 - petcost;
		summoncount = charisma / summoncost;

		L1Npc npcTemp = NpcTable.get().getTemplate(summonid);
		for (int cnt = 0; cnt < summoncount; cnt++) {
			L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
			summon.setPetcost(summoncost);
		}
	}
    
	private void detection(final L1PcInstance pc) {
		if (!pc.isGmInvis() && pc.isInvisble()) { // 自分
			pc.delInvis();
			pc.beginInvisTimer();
		}

		for (final L1PcInstance tgt : World.get().getVisiblePlayer(pc)) {
			if (!tgt.isGmInvis() && tgt.isInvisble()) {
				tgt.delInvis();
			}
		}

		// 偵測陷阱的處理
		WorldTrap.get().onDetection(pc);
	}
    
    /**
	 * 群體魔法目標判定
	 * 
	 * @param cha
	 * @param cha
	 * @return
	 */
	private boolean isTargetCalc(final L1Character cha) {
		// 攻撃魔法のNon−PvP判定
		if (this._skill.getTarget().equals("attack") && (this._skillId != TURN_UNDEAD)) { // 攻撃魔法
			if (this.isPcSummonPet(cha)) { // 対象がPC、サモン、ペット
				if (this._player.isSafetyZone() || // 自己位於安全區
						cha.isSafetyZone() || // 目標位於安全區
						this._player.checkNonPvP(this._player, cha)) {// 檢查是否可以攻擊
					return false;
				}
			}
		}
		switch (this._skillId) {
		// 沉睡之霧
		case FOG_OF_SLEEPING:
			if (this._user.getId() == cha.getId()) {
				return false;
			}
			break;

		// 集體緩速術
		case MASS_SLOW:
			if (this._user.getId() == cha.getId()) {
				return false;
			}

			if (cha instanceof L1SummonInstance) {
				final L1SummonInstance summon = (L1SummonInstance) cha;
				if (this._user.getId() == summon.getMaster().getId()) {
					return false;
				}

			} else if (cha instanceof L1PetInstance) {
				final L1PetInstance pet = (L1PetInstance) cha;
				if (this._user.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
			break;

		// 集體傳送術
		case MASS_TELEPORT:
			if (this._user.getId() != cha.getId()) {
				return false;
			}
			break;
		}
		return true;
	}
    
	/**
	 * 目標對象 是否為寵物 召喚獸 虛擬人物
	 * 
	 * @param cha
	 * @return
	 */
	private boolean isPcSummonPet(final L1Character cha) {
		// PC 對 PC
		switch (this._calcType) {
		case PC_PC:
			return true;

		// PC 對 NPC
		case PC_NPC:
			// 目標對象為召喚獸
			if (cha instanceof L1SummonInstance) {
				final L1SummonInstance summon = (L1SummonInstance) cha;
				// 目標對象具有主人
				if (summon.isExsistMaster()) {
					return true;
				}
			}
			// 目標對象為寵物
			if (cha instanceof L1PetInstance) {
				return true;
			}
			return false;

		default:
			return false;
		}
	}
    
    /**
	 * 檢查是否為不合法的目標
	 * 
	 * @param cha
	 * @return true:不合法 false:合法
	 */
	private boolean isTargetFailure(final L1Character cha) {
		boolean isTU = false;
		boolean isErase = false;
		boolean isManaDrain = false;
		int undeadType = 0;
		if ((cha instanceof L1TowerInstance) || (cha instanceof L1DoorInstance)) { // ガーディアンタワー、ドアには確率系スキル無効
			return true;
		}

		if (cha instanceof L1PcInstance) { // 対PCの場合
			if ((this._calcType == PC_PC) && this._player.checkNonPvP(this._player, cha)) { // Non-PvP設定
				final L1PcInstance pc = (L1PcInstance) cha;
				if ((this._player.getId() == pc.getId())
						|| ((pc.getClanid() != 0) && (this._player.getClanid() == pc.getClanid()))) {
					return false;
				}
				return true;
			}
			return false;
		}

		if (cha instanceof L1MonsterInstance) { // ターンアンデット可能か判定
			isTU = ((L1MonsterInstance) cha).getNpcTemplate().get_IsTU();
		}

		if (cha instanceof L1MonsterInstance) { // イレースマジック可能か判定
			isErase = ((L1MonsterInstance) cha).getNpcTemplate().get_IsErase();
		}

		if (cha instanceof L1MonsterInstance) { // アンデットの判定
			undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
		}

		// マナドレインが可能か？
		if (cha instanceof L1MonsterInstance) {
			isManaDrain = true;
		}
		/*
		 * 成功除外条件１：T-Uが成功したが、対象がアンデットではない。 成功除外条件２：T-Uが成功したが、対象にはターンアンデット無効。
		 * 成功除外条件３：スロー、マススロー、マナドレイン、エンタングル、イレースマジック、ウィンドシャックル無効
		 * 成功除外条件４：マナドレインが成功したが、モンスター以外の場合
		 */
		if (((this._skillId == TURN_UNDEAD) && ((undeadType == 0) || (undeadType == 2)))

				|| ((this._skillId == TURN_UNDEAD) && (isTU == false))

				|| (((this._skillId == ERASE_MAGIC) || (this._skillId == SLOW) || (this._skillId == MANA_DRAIN)
						|| (this._skillId == MASS_SLOW) || (this._skillId == ENTANGLE)
						|| (this._skillId == WIND_SHACKLE)) && (isErase == false))

				|| ((this._skillId == MANA_DRAIN) && (isManaDrain == false))) {
			return true;
		}
		return false;
	}
    
	/** 是否使用了魔法屏障 */
	private boolean isUseCounterMagic(final L1Character cha) {
		// カウンターマジック有効なスキルでカウンターマジック中
		if (this._isCounterMagic && cha.hasSkillEffect(COUNTER_MAGIC)) {
			cha.removeSkillEffect(COUNTER_MAGIC);
			final int castgfx2 = SkillsTable.get().getTemplate(31).getCastGfx2();
			cha.broadcastPacketAll(new S_SkillSound(cha.getId(), castgfx2));
			if (cha instanceof L1PcInstance) {
				final L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), castgfx2));
			}
			return true;
		}
		return false;
	}
    
    /**
	 * 生成毒霧區域
	 * 
	 * @param loc
	 */
	@SuppressWarnings("unused")
	private void SpawnPoisonArea(L1Location baseloc) {
		int locX = 0;
		int locY = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				switch (_user.getHeading()) {
				case 0:
					locX = (-1 + j);
					locY = -1 * (-3 + i);
					break;
				case 1:
					locX = -1 * (2 + j - i);
					locY = -1 * (-4 + j + i);
					break;
				case 2:
					locX = -1 * (3 - i);
					locY = (-1 + j);
					break;
				case 3:
					locX = -1 * (4 - j - i);
					locY = -1 * (2 + j - i);
					break;
				case 4:
					locX = (1 - j);
					locY = -1 * (3 - i);
					break;
				case 5:
					locX = -1 * (-2 - j + i);
					locY = -1 * (4 - j - i);
					break;
				case 6:
					locX = -1 * (-3 + i);
					locY = (1 - j);
					break;
				case 7:
					locX = -1 * (-4 + j + i);
					locY = -1 * (-2 - j + i);
					break;
				}
				L1EffectInstance effect = L1SpawnUtil.spawnEffect(86125, 3, baseloc.getX() - locX,
						baseloc.getY() - locY, _user.getMapId(), _user, 20011);

				effect.broadcastPacketAll(new S_SkillSound(effect.getId(), 1263));// 毒霧特效
			}
		}
	}
    
    public static void DoMySkillByMonster(final L1PcInstance pc, final int id) {
        if (pc.isDead()) {
            return;
        }
        if (pc.hasSkillEffect(id)) {
            return;
        }
        final L1SkillUse l1skilluse = new L1SkillUse();
        l1skilluse.handleCommands(pc, id, pc.getId(), pc.getX(), pc.getY(), 0, 4);
    }
}