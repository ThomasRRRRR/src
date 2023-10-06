package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.*;

import com.add.PeepCard;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigSkill;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.data.event.FeatureItemSet;
import com.lineage.server.datatables.ItemSpecialAttributeTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.PowerItemTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.poison.L1DamagePoison;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.model.weaponskill.WeaponSkillStart;
import com.lineage.server.serverpackets.S_AttackPacketPc;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.serverpackets.S_PacketBoxDk;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_TrueTarget;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1ItemSpecialAttribute;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.templates.L1MagicWeapon;
import com.lineage.server.templates.L1Mon;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.william.DexDmg;
import com.lineage.william.IntSp;
import com.lineage.william.L1WilliamGfxIdOrginal;
import com.lineage.william.StrDmg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class L1AttackPc extends L1AttackMode {
	private static final Log _log = LogFactory.getLog(L1AttackPc.class);

	private byte _attackType = 0;

	private int _weaponDamage;// 武器大小傷

	private int _weaponTotalDamage;// 武器總傷害

	private int hit_rnd;// 攻擊命中機率

	L1ItemSpecialAttributeChar item_attr_char = null;
	
  Random random = new Random();
  
  private void skillEffice(int hole, L1Character target) {
    if (hole > 0) {
      Random random = new Random();
      int skill_id = (PowerItemTable.get().getItem(hole)).skill_id;
      
      int probability = (PowerItemTable.get().getItem(hole)).probability;
      int target_to = (PowerItemTable.get().getItem(hole)).target_to;
      int rnd = random.nextInt(100) + 1;

      if (!this._pc.hasSkillEffect(90000000 + skill_id) && skill_id > 0 && rnd <= probability) {
        if (L1WilliamGfxIdOrginal.Cancellation(target.getTempCharGfx())) {
          return;
        }

        L1Skills skill = SkillsTable.get().getTemplate(skill_id);
        this._pc.setSkillEffect(90000000 + skill_id, 2000);

        if (skill.getTarget().equals("none")) {
          if (target_to == 0) {
            (new L1SkillUse()).handleCommands(this._pc, skill_id, this._pc.getId(), this._pc.getX(), this._pc.getY(), skill.getBuffDuration(), 5);
          } else if (target_to == 1) {
            (new L1SkillUse()).handleCommands(this._pc, skill_id, target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 5);
          } 
        } else if (skill.getTarget().equals("attack") && 
          target_to == 1) {
          (new L1SkillUse()).handleCommands(this._pc, skill_id, target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 5);
        } 
      } 

      boolean unequipment = (PowerItemTable.get().getItem(hole)).unequipment;
      probability = (PowerItemTable.get().getItem(hole)).probability_unequi;
      rnd = random.nextInt(100) + 1;

      if (unequipment && rnd <= probability && !this._targetPc.isSafetyZone() && target instanceof L1PcInstance) {
        L1PcInstance targetPC = (L1PcInstance)target;

        targetPC.getInventory().setEquipped(targetPC.getWeapon(), false, false, false);
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("成功解除對方裝備!"));
        targetPC.sendPackets((ServerBasePacket)new S_SystemMessage("你已被對方解除武器!"));
      } 
    } 
  }


  
  private boolean polyidEictff(int hole, L1Character target) {
    if (hole > 0 && 
      target instanceof L1PcInstance) {
      Random random = new Random();
      int polyid = (PowerItemTable.get().getItem(hole)).polyid;
      int polyid_time = (PowerItemTable.get().getItem(hole)).polyid_time;
      int probability = (PowerItemTable.get().getItem(hole)).probability_polyid;
      int rnd = random.nextInt(100) + 1;
      if (polyid > 0 && !this._targetPc.isSafetyZone() && rnd <= probability) {
        
        if (L1WilliamGfxIdOrginal.Cancellation(target.getTempCharGfx())) {
          return false;
        }

        L1PolyMorph.doPoly(target, polyid, polyid_time, 2);

        return true;
      } 
    } 
    return false;
  }

public L1AttackPc(L1PcInstance attacker, L1Character target) {
	if (target == null) {
		return;
	}

	if (target.isDead()) {
		return;
	}

	_pc = attacker;

	if ((target instanceof L1PcInstance)) {
		_targetPc = ((L1PcInstance) target);
		_calcType = PC_PC;

	} else if ((target instanceof L1NpcInstance)) {
		_targetNpc = ((L1NpcInstance) target);
		_calcType = PC_NPC;
	}

	_weapon = _pc.getWeapon();// 主武器
	if (_weapon != null) {
		this.item_attr_char = this._weapon.get_ItemAttrName();
		int attr_DmgSmall = 0;
	    int attr_DmgLarge = 0;
	    int attr_HitModifier = 0;
	    int attr_DmgModifier = 0;
	      if (this.item_attr_char != null) {
		        L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(this.item_attr_char.get_attr_id());
		        attr_DmgSmall = attr.get_dmg_small();
		        attr_DmgLarge = attr.get_dmg_large();
		        attr_HitModifier = attr.get_hitmodifier();
		        attr_DmgModifier = attr.get_dmgmodifier();
		      } 
	      	_weaponId = _weapon.getItem().getItemId();
			_weaponType = _weapon.getItem().getType1();
			_weaponType2 = _weapon.getItem().getType();
			_weaponAddHit = (_weapon.getItem().getHitModifier() + _weapon.getHitByMagic() + attr_HitModifier);
			_weaponAddDmg = (_weapon.getItem().getDmgModifier() + _weapon.getDmgByMagic() + attr_DmgModifier);

			_weaponSmall = _weapon.getItem().getDmgSmall() + attr_DmgSmall;
			_weaponLarge = _weapon.getItem().getDmgLarge() + attr_DmgLarge;
			_weaponRange = _weapon.getItem().getRange();
			_weaponBless = _weapon.getItem().getBless();

      
      L1ItemPower_name power = this._weapon.get_power_name();
      if (power != null) {
        skillEffice(power.get_hole_1(), target);
        skillEffice(power.get_hole_2(), target);
        skillEffice(power.get_hole_3(), target);
        skillEffice(power.get_hole_4(), target);
        skillEffice(power.get_hole_5(), target);
        boolean polyid = polyidEictff(power.get_hole_1(), target);
        if (!polyid) {
          polyid = polyidEictff(power.get_hole_2(), target);
          if (!polyid) {
            polyid = polyidEictff(power.get_hole_3(), target);
            if (!polyid) {
              polyid = polyidEictff(power.get_hole_4(), target);
              if (!polyid) {
                polyid = polyidEictff(power.get_hole_5(), target);
              }
            } 
          } 
        } 
      } 
if ((_weaponType != 20) && (_weaponType != 62)) {//假如是遠距離武器
	_weaponEnchant = (_weapon.getEnchantLevel() - _weapon.get_durability());

} else {
	_weaponEnchant = _weapon.getEnchantLevel();
}

_weaponMaterial = _weapon.getItem().getMaterial();
if (_weaponType == 20) {
	_arrow = _pc.getInventory().getArrow();

	if (_arrow != null) {
		_weaponBless = _arrow.getItem().getBless();
		_weaponMaterial = _arrow.getItem().getMaterial();
	}
}
if (_weaponType == 62) {
	_sting = _pc.getInventory().getSting();
	if (_sting != null) {
		_weaponBless = _sting.getItem().getBless();
		_weaponMaterial = _sting.getItem().getMaterial();
	}
}

_weaponDoubleDmgChance = _weapon.getDoubleDmgChance();
_weaponAttrEnchantKind = _weapon.getAttrEnchantKind();
_weaponAttrEnchantLevel = _weapon.getAttrEnchantLevel();
}
    
    if (this._weaponType == 20 || this._weaponType == 62) {
      int dmg = DexDmg.getDexDmgSkill(this._pc, this._pc.getDex());
      if (dmg != 0) {
        this._statusDamage = dmg;
      }
    }
    else {
      
      int dmg = StrDmg.getStrDmgSkill(this._pc, this._pc.getStr());
      if (dmg != 0) {
        this._statusDamage = dmg;
      }
    } 

_target = target;
_targetId = target.getId();
_targetX = target.getX();
_targetY = target.getY();
  }


/**
 * 計算攻擊是否命中
 */
public boolean calcHit() {
	if (_target == null) {
		_isHit = false;
		return _isHit;
	}
	if (_weaponRange != -1) {// 近距離武器
		int range = _pc.getLocation().getTileLineDistance(_target.getLocation());
		if (range > _weaponRange + 2) {// 超過武器打擊距離+2格以上
			_isHit = false;
			return _isHit;
		}

	} else if (!_pc.getLocation().isInScreen(_target.getLocation())) {// 遠距離武器
																		// 對象不在畫面內
		_isHit = false;
		return _isHit;
	}
	if ((_weaponType == 20) && (_weaponId != 190) && (_arrow == null)) {// 弓類型武器且沒有箭
		_isHit = false;

	} else if ((_weaponType == 62) && (_sting == null)) {// 鐵手甲類型武器且沒有飛刀
		_isHit = false;

	} else if (!_pc.glanceCheck(_targetX, _targetY)) {// 有障礙物阻擋且對象不是門
		_isHit = false;

	} else if ((_weaponId == 247) || (_weaponId == 248) || (_weaponId == 249)) {// 試煉武器
		_isHit = false;

	} else if (_calcType == PC_PC) {
		_isHit = calcPcHit();

	} else if (_calcType == PC_NPC) {
		_isHit = calcNpcHit();
	}

	return _isHit;
}
 
  private int str_dex_Hit() {
    int hitRate = 0;
    int hitDex = DexDmg.getDexHitSkill(this._pc, this._pc.getDex());
    if (hitDex != 0) {
      hitRate += hitDex;
    }
    
    int hitStr = 
      StrDmg.getStrHitSkill(this._pc, this._pc.getStr());
    if (hitStr != 0) {
      hitRate += hitStr;
    }
    if (this._pc.isWizard()) {
      hitRate += 5;
    }
    
    return hitRate;
  }
/**
 * 攻擊PC時的命中率計算
 * 
 * @return
 */
private boolean calcPcHit() {
    if (ConfigGuaji.Guaji_save && this._targetPc.isActived() && 
      this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
      return false;
    }
    
    if (this._targetPc == this._pc) {
      return false;
    }
    if (this._targetPc == null) {
      return false;
    }
    
    if (dmg0((L1Character)this._targetPc)) {
      return false;
    }
    
    if (ConfigOther.newcharpra && this._targetPc.getnewcharpra()) {
      return false;
    }
    if (ConfigOther.newcharpra && this._pc.getnewcharpra()) {
      return false;
    }
    if (calcEvasion()) {
      return false;
    }
    
//    if (this._targetPc.getattr_物理格檔() > 0 && 
//    	      _random.nextInt(100) < this._targetPc.getattr_物理格檔())
//    	return false; 
    
    int attr_物理格檔 = 0;
    if (this.item_attr_char != null) {
      L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(this.item_attr_char.get_attr_id());
      attr_物理格檔 = attr.get物理格檔();
    }
    if (_random.nextInt(100) < _targetPc.getattr_物理格檔()+attr_物理格檔){
		return false;
	}
    
    if (_targetPc.hasSkillEffect(L1SkillId.FREEZING_BLIZZARD) ||
		_targetPc.hasSkillEffect(L1SkillId.ICE_LANCE)) {
		return false;
		}
    
    if (this._weaponType2 == 17) {
      return true;
    }

    this._hitRate = this._pc.getLevel();
    
    this._hitRate += str_dex_Hit();// 力量 敏捷命中補正
    
    if (this._weaponType != 20 && this._weaponType != 62) {
      this._hitRate = (int)(this._hitRate + (this._weaponAddHit + this._pc.getHitup() + this._pc.getOriginalHitup()) + this._weaponEnchant * 0.6D);
    } else {
      this._hitRate = (int)(this._hitRate + (this._weaponAddHit + this._pc.getBowHitup() + this._pc.getOriginalBowHitup()) + this._weaponEnchant * 0.6D);
    } 
    if (this._weaponType != 20 && this._weaponType != 62) {
      this._hitRate += this._pc.getHitModifierByArmor();
    } else {
      this._hitRate += this._pc.getBowHitModifierByArmor();
    } 
    
//    if ((_weaponType != 20) && (_weaponType != 62)) {
//		   _hitRate += ((int) (_hitRate + _pc.getHitup() + (_weaponAddHit +
//		     _weaponEnchant * 0.6D)) + _pc.getHitModifierByArmor()
//		     + hitUp() + (attackerDice(_targetPc) * 10));
//		   /*if (_pc.get_other().get_Speciali_Hit() != 0) {
//		    _hitRate += _pc.get_other().get_Speciali_Hit() ;
//		   }*/
//		  } else {
//		   _hitRate += ((int) (_hitRate + _pc.getBowHitup() + (_weaponAddHit + _pc.getBowHitup() +
//		     _weaponEnchant * 0.6D)) + _pc.getBowHitModifierByArmor() + hitUp()+ (attackerDice(_targetPc) * 10));
//		   /*if (_pc.get_other().get_Speciali_BowHit() != 0) {
//		    _hitRate += _pc.get_other().get_Speciali_BowHit();
//		   }*/
//		   if (attackerDice(_targetPc) != 0) {
//		    _hitRate -= 10;
//		   }
//		  }
    
	int weight240 = _pc.getInventory().getWeight240();
	if (weight240 > 80) {
		if ((80 < weight240) && (120 >= weight240)) {
			_hitRate -= 1;

		} else if ((121 <= weight240) && (160 >= weight240)) {
			_hitRate -= 3;

		} else if ((161 <= weight240) && (200 >= weight240)) {
			_hitRate -= 5;
		}
	}

    if (this._targetPc.isDarkelf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && 
      this._targetPc.getlogpcpower_SkillFor3() >= 1) {
      this._hitRate -= this._targetPc.getlogpcpower_SkillFor3();
    }
    _hitRate += hitUp();// 料理追加命中

	if (_pc.is_mazu()) {// 媽祖祝福攻擊命中+5
		_hitRate += 5;
	}
	
    if (this._pc.get_other().get_PvP_hit() > 0) {
      this._hitRate += this._pc.get_other().get_PvP_hit();
    }
    if ((this._pc.get_other().get_PvP_bow_hit() > 0 && this._weaponType == 20) || this._weaponType == 62) {
      this._hitRate += this._pc.get_other().get_PvP_bow_hit();
    }
    
    int attackerDice = _random.nextInt(20) + 1 + this._hitRate - 10;
    
    attackerDice += attackerDice((L1Character)this._targetPc);

    _hitRate += (attackerDice(_targetPc) * 10);
    
int defenderDice = 0;

int defenderValue = (int) (_targetPc.getAc() * 1.5D) * -1;// 防禦*1.5並且負轉正

if (_targetPc.getAc() >= 0) {// 防禦為正數
	defenderDice = 10 - _targetPc.getAc();

} else if (_targetPc.getAc() < 0) {// 防禦為負數
	defenderDice = 10 + defenderValue;
}

// System.out.println("defenderDice == " + defenderDice);
if (defenderDice <= 0) {
	defenderDice = 1;
}

    int fumble = this._hitRate - 9;
    int critical = this._hitRate + 10;

    if (this._pc.isElf())
    {
      if (this._pc.getElfAttr() == 2) {
        attackerDice++;
      }
    }

    if (attackerDice <= fumble) {
      this._hitRate = 15;
    }
    else if (attackerDice >= critical) {
      this._hitRate = 100;
    
    }
    else if (attackerDice > defenderDice) {
      this._hitRate = 100;
    }
    else if (attackerDice <= defenderDice) {
      this._hitRate = 15;
    } 

    
int rnd = _random.nextInt(100) + 1;// 比對機率
if ((_weaponType == 20 || _weaponType == 62) // 遠距離武器
		&& hit_rnd >= rnd) {// 命中時
	return calcErEvasion();// ER迴避計算
}
      if (this._hitRate > 95) {
        this._hitRate = 95;
      }
    return (this._hitRate >= rnd);
  }

  
/**
 * 攻擊NPC時的命中率計算
 * 
 * @return
 */
private boolean calcNpcHit() {
	int gfxid = _targetNpc.getNpcTemplate().get_gfxid();
	switch (gfxid) {
	case 2412:
		if (!_pc.getInventory().checkEquipped(20046)) {
			return false;
		}
		break;
	}

	if (dmg0(_targetNpc)) {
		return false;
	}

	if (_targetNpc.isShop()) {
		return false;
	}

	if (_weaponType2 == 17) {// 奇古獸
		return true;
	}
    
    if (this._targetNpc.hasSkillEffect(91)) {
      L1Magic magic = new L1Magic((L1Character)this._targetNpc, (L1Character)this._pc);
      boolean isCounterBarrier = false;
      boolean isProbability = magic.calcProbabilityMagic(91);
      boolean isShortDistance = isShortDistance();
      L1ItemInstance weapon = this._pc.getWeapon();
      if (isProbability && isShortDistance && 
        weapon.getItem().getType() != 17) {
        isCounterBarrier = true;
      }
      if (isCounterBarrier) {
        commitCounterBarrier();
        return false;
      } 
    } 

    if (this._targetNpc.hasSkillEffect(11060)) {
      commitCounterBarrier();
      return false;
    } 
    
    this._hitRate = this._pc.getLevel();
    
    this._hitRate += str_dex_Hit();
    
    if (this._weaponType != 20 && this._weaponType != 62) {
      this._hitRate = (int)(this._hitRate + (this._weaponAddHit + this._pc.getHitup() + this._pc.getOriginalHitup()) + this._weaponEnchant * 0.6D);
    } else {
      
      this._hitRate = (int)(this._hitRate + (this._weaponAddHit + this._pc.getBowHitup() + this._pc.getOriginalBowHitup()) + this._weaponEnchant * 0.6D);
    } 
    
    if (this._weaponType != 20 && this._weaponType != 62) {
      this._hitRate += this._pc.getHitModifierByArmor();
    } else {
      
      this._hitRate += this._pc.getBowHitModifierByArmor();
    } 
    
    int weight240 = this._pc.getInventory().getWeight240();
    if (weight240 > 80) {
      if (80 < weight240 && 120 >= weight240) {
        this._hitRate--;
      }
      else if (121 <= weight240 && 160 >= weight240) {
        this._hitRate -= 3;
      }
      else if (161 <= weight240 && 200 >= weight240) {
        this._hitRate -= 5;
      } 
    }
    
    this._hitRate += hitUp();
    
    if (this._pc.is_mazu()) {
      this._hitRate += 5;
    }
    
    int attackerDice = _random.nextInt(20) + 2 + this._hitRate - 10;
    
    attackerDice += attackerDice((L1Character)this._targetNpc);
    
    int defenderDice = 10 - this._targetNpc.getAc();
    
    int fumble = this._hitRate - 9;
    int critical = this._hitRate + 10;
    
    if (attackerDice <= fumble) {
      this._hitRate = 0;
    } else if (attackerDice >= critical) {
      this._hitRate = 100;
    }
    else if (attackerDice > defenderDice) {
      this._hitRate = 100;
    } else if (attackerDice <= defenderDice) {
      this._hitRate = 0;
    } 

    
    int npcId = this._targetNpc.getNpcTemplate().get_npcId();
    
    Integer tgskill = L1AttackList.SKNPC.get(Integer.valueOf(npcId));
    if (tgskill != null && 
      !this._pc.hasSkillEffect(tgskill.intValue())) {
      this._hitRate = 0;
    }
    
    Integer tgpoly = L1AttackList.PLNPC.get(Integer.valueOf(npcId));
    if (tgpoly != null && 
      tgpoly.equals(Integer.valueOf(this._pc.getTempCharGfx()))) {
      this._hitRate = 0;
    }
    
    int rnd = _random.nextInt(100) + 1;
    
    return (this._hitRate >= rnd);
  }

/**
 * 料理追加命中
 * 
 * @return
 */
private int hitUp() {
	int hitUp = 0;
	if (_pc.getSkillEffect().size() <= 0) {
		return hitUp;
	}

	if (!_pc.getSkillisEmpty()) {
		try {
			if ((_weaponType != 20) && (_weaponType != 62)) {
				for (Integer key : _pc.getSkillEffect()) {
					Integer integer = (Integer) L1AttackList.SKU1.get(key);
					if (integer != null) {
						hitUp += integer.intValue();
					}
				}
			} else {
				for (Integer key : _pc.getSkillEffect()) {
					Integer integer = (Integer) L1AttackList.SKU2.get(key);
					if (integer != null)
						hitUp += integer.intValue();
				}
			}
		} catch (ConcurrentModificationException localConcurrentModificationException) {
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	return hitUp;
}

/**
 * 最終傷害計算
 */
  public int calcDamage() {
    switch (this._calcType) {
    case PC_PC:
        this._damage = calcPcDamage();
        break;
    case PC_NPC:
        this._damage = calcNpcDamage();
        break;
    } 

    if (this._targetNpc instanceof com.lineage.server.model.Instance.L1DeInstance)
    {
      if (!this._targetNpc.isSafetyZone())
      {
        this._damage = 0;
      }
    }
    
    if (this._isHit) {

      
      if (this._weapon != null)
      {
        if (this._weapon.get_magic_weapon() != null) {
          boolean isLongRange;
          
          if (this._weaponType == 20 || this._weaponType == 62) {
            isLongRange = true;
          } else {
            isLongRange = false;
          } 
          this
            ._damage = (int)(this._damage + L1MagicWeapon.getWeaponSkillDamage(this._pc, this._target, this._damage, this._weapon.get_magic_weapon(), isLongRange));
        } 
      }
      if (this._weapon != null && 
        FeatureItemSet.POWER_START) {
        L1AttackPower attackPower = new L1AttackPower(this._pc, this._target, this._weaponAttrEnchantKind, this._weaponAttrEnchantLevel);
        this._damage = attackPower.set_item_power(this._damage);
      } 

      if (!this._pc.isFoeSlayer()) {
        dk_dmgUp();// 弱點曝光機率計算
      }
      
		if (_pc.hasSkillEffect(SOUL_OF_FLAME) && _weapon != null && _weaponType != 20 && _weaponType != 62) {
			_damage = (int) (_damage * ConfigSkill.SOUL_OF_FLAME_ALLDAMAGE);
		}
      
      addPcPoisonAttack(this._target);// 附加劇毒機率計算
      
      AttrAmuletEffect();// 觸發屬性守護之鍊附加技能 火焰之暈
  
      MoonAmuletEffect();// 觸發月亮項鍊附加技能 月光氣息
      
      Imperius_Tshirt_Effect();// 觸發奪魂T恤吸血功能
      
      if (this._pc.isFoeSlayer())
      {// 使用屠宰者
        if (this._pc.get_weaknss() == 1) {
          this._damage += 10;
          this._damage += this._pc.get_FoeSlayerBonusDmg();
        }
        else if (this._pc.get_weaknss() == 2) {
          this._damage += 25;
          this._damage += this._pc.get_FoeSlayerBonusDmg();
        }
        else if (this._pc.get_weaknss() == 3) {
          this._damage += 40;
          this._damage += this._pc.get_FoeSlayerBonusDmg();
        } 
      }
    } 
    if (this._damage > 0) {
      soulHp();
    }
    return this._damage;
  }

/**
 * 武器亂數傷害計算
 * 
 * @param weaponMaxDamage
 * @return
 */
private int weaponDamage1(int weaponMaxDamage) {
	int weaponDamage = 0;

	switch (_weaponType2) {
	case 0:// 空手
		break;
	case 3:// 巨劍
		weaponDamage = _random.nextInt(weaponMaxDamage) + 1;
		if ((_weaponId == 217) && (_pc.getLawful() < 500)) { // 吉爾塔斯之劍，邪惡值越高
																// 攻擊力越高
			int a = _pc.getLawful() / 1000;
			int b = Math.abs(a);
			weaponDamage += b;
		} else if ((_weaponId == 410165) && (_pc.getLawful() < 500)) { // 紅騎士巨劍，邪惡值越高
																		// 攻擊力越高
			int a = _pc.getLawful() / 3000;
			int b = Math.abs(a);
			weaponDamage += b;
		}
		break;
	case 11:// 鋼爪
		weaponDamage = _random.nextInt(weaponMaxDamage) + 1;

		if (_random.nextInt(100) + 1 <= _weaponDoubleDmgChance) {
			weaponDamage = weaponMaxDamage;// 重擊
			_attackType = 2;
		}

		if ((_weaponId == 915) && (_pc.getLawful() < 500)) { // 震怒的鋼爪，邪惡值越高
																// 攻擊力越高
			int a = _pc.getLawful() / 3000;
			int b = Math.abs(a);
			weaponDamage += b;
		}
		break;
	case 1:// 劍
	case 2:// 匕首
	case 4:// 雙手弓
	case 5:// 矛(雙手)
	case 6:// 斧(單手)
	case 7:// 魔杖
	case 8:// 飛刀
	case 9:// 箭
	case 10:// 鐵手甲
	case 12:// 雙刀
	case 13:// 單手弓
	case 14:// 矛(單手)
	case 15:// 雙手斧
	case 16:// 魔杖(雙手)
	case 17:// 奇古獸
	case 18:// 鎖鏈劍
		
		weaponDamage = _random.nextInt(weaponMaxDamage) + 1;
		if ((this._weaponId == 1818) && (_pc.getLawful() < 500)) { // 惡夢的長弓，邪惡值越高
			// 攻擊力越高
			int a = this._pc.getLawful() / 3000;
			int b = Math.abs(a);
			weaponDamage += b;
		}
		break;
	}
	
	if (_pc.getClanid() != 0) {
		weaponDamage += getDamageUpByClan(_pc);// 血盟技能傷害提升
	}
	switch (_pc.guardianEncounter()) {
	case 3:
		weaponDamage++;
		break;
	case 4:
		weaponDamage += 3;
		break;
	case 5:
		weaponDamage += 5;
	}
	// System.out.println("weaponDamage ==" + weaponDamage);
	return weaponDamage;
}

/**
 * 四屬性加成、道具加成、初始加成計算
 * 
 * @param weaponTotalDamage
 * @return
 */
  private double weaponDamage2(double weaponTotalDamage) {
    int addchance, totalchance, addchance2, totalchance2, add;
    Integer dmgint;
    double dmg = 0.0D;
    
weaponTotalDamage += calcAttrEnchantDmg();// 屬性武器增傷計算

switch (_weaponType2) {
case 1:// 劍
case 2:// 匕首
case 3:// 雙手劍
case 5:// 矛(雙手)
case 6:// 斧(單手)
case 7:// 魔杖
case 8:// 飛刀
case 9:// 箭
case 14:// 矛(單手)
case 15:// 雙手斧
case 16:// 魔杖(雙手)
case 18:// 鎖鏈劍
        dmg = weaponTotalDamage + this._statusDamage + this._pc.getDmgup() + this._pc.getOriginalDmgup();
        break;

case 11: // 鋼爪
    addchance = 0;
    if (_pc.getLevel() > 45) { // 等級超過45級之後每5級增加1%發動機率
        addchance = (_pc.getLevel() - 45) / 5;
    }
    
    totalchance = 20 + addchance + ConfigSkill.DOUBLE_BREAK_CHANCE; // 總發動機率
    
    if (_pc.getInventory().checkItem(150003, 1)) { // 觸發雙重破壞
        totalchance = 20 + addchance + ConfigSkill.DOUBLE_BREAK_CHANCE + 10; // 總發動機率
    }
    
    if (this._pc.hasSkillEffect(105) && _random.nextInt(100) < totalchance && !ConfigSkill.DOUBLE_BREAK_NO_WEAPON) { // 觸發雙重破壞
        weaponTotalDamage *= ConfigSkill.DOUBLE_BREAK_DMG;
    }
    
    dmg = weaponTotalDamage + this._statusDamage + this._pc.getDmgup() + this._pc.getOriginalDmgup();
    break;

case 12: // 雙刀
//    if (!this._pc.hasSkillEffect(96230) && _random.nextInt(100) + 1 <= this._weaponDoubleDmgChance) {
	if (_random.nextInt(100) + 1 <= this._weaponDoubleDmgChance) {
        weaponTotalDamage *= 2; // 雙擊
//        this._pc.setSkillEffect(96230, 2000);
    }
	
    addchance2 = 0;
    
    if (_pc.getLevel() > 45) { // 等級超過45級之後每5級增加1%發動機率
        addchance2 = (_pc.getLevel() - 45) / 5;
    }
    
    totalchance2 = 20 + addchance2 + ConfigSkill.DOUBLE_BREAK_CHANCE; // 總發動機率
    
    if (_pc.getInventory().checkItem(150003, 1)) { // 觸發雙重破壞
        totalchance2 = 20 + addchance2 + ConfigSkill.DOUBLE_BREAK_CHANCE + 6; // 總發動機率
    }
    
//    if (!this._pc.hasSkillEffect(96230) && this._pc.hasSkillEffect(105) && _random.nextInt(100) < totalchance2 && !ConfigSkill.DOUBLE_BREAK_NO_WEAPON) {
    if (this._pc.hasSkillEffect(105) && _random.nextInt(100) < totalchance2 && !ConfigSkill.DOUBLE_BREAK_NO_WEAPON) {
    	weaponTotalDamage *= ConfigSkill.DOUBLE_BREAK_DMG;
        this._attackType = 4;
    }
    
    dmg = weaponTotalDamage + this._statusDamage + this._pc.getDmgup() + this._pc.getOriginalDmgup();
    break;

case 0:// 空手
	dmg = _random.nextInt(5) + 4 >> 2;
	break;

case 4:// 雙手弓
case 13:// 單手弓
        add = this._statusDamage;
        dmg = weaponTotalDamage + add + this._pc.getBowDmgup() + this._pc.getOriginalBowDmgup();
        if (this._arrow != null) {
          int add_dmg = Math.max(this._arrow.getItem().getDmgSmall(), 1);
          dmg = dmg + _random.nextInt(add_dmg) + 1.0D; break;
        } 
        if (this._weaponId == 190) {
          dmg += 10.0D;
        }
        break;
      case 10:
        dmg = weaponTotalDamage + this._statusDamage + this._pc.getBowDmgup() + this._pc.getOriginalBowDmgup();
        
        if (this._sting != null) {
          int add_dmg = Math.max(this._sting.getItem().getDmgSmall(), 1);
          dmg += (_random.nextInt(add_dmg) + 1);
        } 
        break;
      case 17:
        dmg = L1WeaponSkill.getKiringkuDamage(this._pc, this._target);
        
        dmg += weaponTotalDamage;
        dmgint = L1AttackList.INTD.get(Integer.valueOf(this._pc.getInt()));
        if (dmgint != null) {
          dmg += dmgint.intValue();
        }
        break;
    } 

    if (this._weaponType2 != 0) {
      int add_dmg = this._weapon.getItem().get_add_dmg();
      if (add_dmg != 0) {
        dmg += add_dmg;
      }
    } 
    
    return dmg;
  }
/**
 * 其它增傷計算
 * 
 * @param dmg
 * @return
 */
private double pcDmgMode(double dmg) {
	dmg = calcBuffDamage(dmg);// 近戰BUFF增傷

	if ((_weaponType != 20) && (_weaponType != 62)) {// 近距離武器
		if (_weaponType2 != 17) {// 除了奇古獸
			dmg += _pc.getDmgModifierByArmor();// 防具增加近距離傷害
		}
	} else {// 遠距離武器
		dmg += _pc.getBowDmgModifierByArmor();// 防具增加遠距離傷害
	}

	if (_weaponType2 != 17) {// 除了奇古獸
		dmg += dmgUp();// 料理增傷
	}

	dmg += _pc.dmgAdd();// 娃娃機率增傷
    if (this.item_attr_char != null) {
      L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(this.item_attr_char.get_attr_id());
      int drain_min_hp = attr.get_add_drain_min_hp();
      int drain_max_hp = attr.get_add_drain_max_hp();
      if (drain_min_hp > 0 && drain_max_hp > 0) {
        int random = _random.nextInt(100) + 1;
        if (random <= attr.get_drain_hp_rand() && this._target != null) {
          this._drainHp = Math.min(_random.nextInt(drain_max_hp) + drain_min_hp, drain_max_hp);
          this._pc.setCurrentHp((short)(this._pc.getCurrentHp() + this._drainHp));
        } 
      } 

      
      int drain_min_mp = attr.get_add_drain_min_mp();
      int drain_max_mp = attr.get_add_drain_max_mp();
      if (drain_min_mp > 0 && drain_max_mp > 0) {
        int random = _random.nextInt(100) + 1;
        if (random <= attr.get_drain_mp_rand() && this._target != null) {
          this._drainMana = Math.min(_random.nextInt(drain_max_mp) + drain_min_mp, drain_max_mp);
          this._pc.setCurrentMp((short)(this._pc.getCurrentMp() + this._drainMana));
        } 
      } 
      if (attr.get_add_skill_gfxid() > 0 && 
        attr.get_add_skill_rand() >= _random.nextInt(100) + 1) {
        this._pc.sendPacketsX10((ServerBasePacket)new S_SkillSound(this._target.getId(), attr.get_add_skill_gfxid()));
        dmg += attr.get_add_skill_dmg();
      } 
    } 

    
    if (this._pc.get_double_dmg() != 0 && 
      _random.nextInt(100) + 1 <= this._pc.get_double_dmg()) {
      dmg *= 2.0D;
    }
    
    return dmg;
  }

/**
 * 是否觸發近距離爆擊傷害
 * 
 * @param pc
 * @return
 */
private boolean isStrCriticalDmg() {
	int criticalchance = StrDmg.getAddCrichanceSkill(this._pc, this._pc.getStr());// 爆擊機率

	
//	if (_pc.getBaseStr() >= ConfigSkill.StrCriticalLV) {// 純力大於等於45
//		criticalchance += ConfigSkill.StrCriticaladdchance;
//	}
//
//	// 力量增加近距離爆擊機率(7.6)
//	final Integer addchance = L1AttackList.STRCRI.get(Integer.valueOf(_pc.getStr()));
//	if (addchance != null) {
//		criticalchance += addchance;
//	}

	criticalchance += _pc.get_Critical();// 增加近距離暴擊率

	if (_random.nextInt(100) < criticalchance) {// 成功觸發爆擊
//		System.out.println("爆擊機率 ==" + criticalchance);
		// 改變面向
		if (_pc.getHeading() != _pc.targetDirection(_target.getX(), _target.getY())) {
			_pc.setHeading(_pc.targetDirection(_target.getX(), _target.getY()));
		}
		
		int attackgfx = 0;
		switch (_weaponType2) {
		case 1:// 劍
		case 2:// 匕首
		case 3:// 雙手劍
		case 14:// 矛(單手)
		case 5:// 矛(雙手)
		case 15:// 雙手斧
//		case 7:// 魔杖
//		case 16:// 魔杖(雙手)
		case 18:// 鎖鏈劍
		case 11:// 鋼爪
		case 12:// 雙刀
		attackgfx = ConfigOther.StrCriticalgfx;
		break;
		}
		
//		int attackgfx = 0;
//		switch (_weaponType2) {
//		case 1:// 劍
//			attackgfx = 18223;
//			break;
//		case 2:// 匕首
//			attackgfx = 18223;
//			break;
//		case 3:// 雙手劍
//			attackgfx = 18223;
//			break;
//		case 14:// 矛(單手)
//		case 5:// 矛(雙手)
//			attackgfx = 18223;
//			break;
////		case 6:// 斧(單手)
////			if (_pc.getSecondWeapon() != null) {
////				attackgfx = 13415;
////			} else {
////				attackgfx = 13414;
////			}
////			break;
//		case 15:// 雙手斧
//		case 7:// 魔杖
//		case 16:// 魔杖(雙手)
//			attackgfx = 18223;
//			break;
//		case 18:// 鎖鏈劍
//			attackgfx = 18223;
//			break;
//		case 11:// 鋼爪
//			attackgfx = 18223;
//			break;
//		case 12:// 雙刀
//			attackgfx = 18223;
//			break;
//		}

		if (attackgfx > 0 && !ispolyactid()) {
			//所有玩家都可見
//			L1Location loc = _target.getLocation();
//			L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, _pc.get_showId(), 1, _pc.getHeading());
//			dummy.broadcastPacketAll(new S_NPCPack(dummy));
//			dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), attackgfx));
			
			//個人可見
			final S_SkillSound attack = new S_SkillSound(_pc.getId(),attackgfx);
			_pc.sendPackets(attack);
		}

		return true;
	}

	return false;
}

/**
 * 是否觸發遠距離爆擊傷害
 * 
 * @param pc
 * @return
 */
private boolean isDexCriticalDmg() {
	int criticalchance = DexDmg.getAddCrichanceSkill(this._pc, this._pc.getDex());// 爆擊機率

//	if (_pc.getBaseDex() >= ConfigSkill.DexCriticalLV) {// 純敏大於等於45
//		criticalchance += ConfigSkill.DexCriticaladdchance;
//	}
//
//	// 敏捷增加遠距離爆擊機率(7.6)
//	final Integer addchance = L1AttackList.DEXCRI.get(Integer.valueOf(_pc.getDex()));
//	if (addchance != null) {
//		criticalchance += addchance;
//	}

	criticalchance += _pc.get_Bow_Critical();// 增加遠距離暴擊率

	if (_random.nextInt(100) < criticalchance) {// 成功觸發爆擊
//		 System.out.println("爆擊機率2 ==" + criticalchance);
		// 改變面向
		if (_pc.getHeading() != _pc.targetDirection(_target.getX(), _target.getY())) {
			_pc.setHeading(_pc.targetDirection(_target.getX(), _target.getY()));
		}

		int attackgfx = 0;
		switch (_weaponType) {
		case 20:// 弓
		case 62:// 鐵手甲
		attackgfx = ConfigOther.DexCriticalgfx;
		break;
		}
		
//		int attackgfx = 0;
//		switch (_weaponType) {
//		case 20:// 弓
//			attackgfx = 18223;
//			break;
//		case 62:// 鐵手甲
//			attackgfx = 18223;
//			break;
//		}

		if (attackgfx > 0 && !ispolyactid()) {
		//所有玩家都可見
//		L1Location loc = _target.getLocation();
//		L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, _pc.get_showId(), 1, _pc.getHeading());
//		dummy.broadcastPacketAll(new S_NPCPack(dummy));
//		dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), attackgfx));
		
		//個人可見
		final S_SkillSound attack = new S_SkillSound(_pc.getId(),attackgfx);
		_pc.sendPackets(attack);
		}

		return true;
	}

	return false;
}

/**
 * 是否觸發魔法爆擊
 * 
 * @return
 */
private boolean isIntCriticalDmg() {
	int criticalchance = IntSp.getAddCrichanceSkill(this._pc, this._pc.getInt());// 爆擊機率

//	if (_pc.getBaseInt() >= ConfigSkill.IntCriticalLV) {// 純智大於等於45
//		criticalchance += ConfigSkill.IntCriticaladdchance;
//	}
//
//	// 智力增加魔法爆擊機率(7.6)
//	final Integer addchance = L1AttackList.INTCRI.get(Integer.valueOf(_pc.getInt()));
//	if (addchance != null) {
//		criticalchance += addchance;
//	}

	criticalchance += _pc.get_Magic_Critical();// 其他增加魔法暴擊率

	if (_random.nextInt(100) < criticalchance) {// 成功觸發爆擊
//		System.out.println("爆擊機率3 ==" + criticalchance);
		// 改變面向
		if (_pc.getHeading() != _pc.targetDirection(_target.getX(), _target.getY())) {
			_pc.setHeading(_pc.targetDirection(_target.getX(), _target.getY()));
		}

		int attackgfx = 0;
		switch (_weaponType2) {
		case 17:// 奇古獸
		case 7:// 魔杖
		case 16:// 魔杖(雙手)
		attackgfx = ConfigOther.IntCriticalgfx;
		break;
		}
		
		if (attackgfx > 0 && !ispolyactid()) {
			//所有玩家都可見
			L1Location loc = _target.getLocation();
			L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, _pc.get_showId(), 1, _pc.getHeading());
			dummy.broadcastPacketAll(new S_NPCPack(dummy));
			dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), attackgfx));
		
//		//個人可見
//		final S_SkillSound attack = new S_SkillSound(_pc.getId(),attackgfx);
//		_pc.sendPackets(attack);

		}

		return true;
	}

	return false;
}

/**
 * 是否觸發ACTID變身特殊動作
 * 
 * @return
 */
private boolean ispolyactid() {
	int ran = _random.nextInt(100) + 1;
	if (this._pc.getpolyactidran() > 0 && ran <= this._pc.getpolyactidran()) {
		 
//		      //所有玩家可見
//		      L1Location loc = _target.getLocation();
//		      L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, _pc.get_showId(), 1, _pc.getHeading());
//		      dummy.broadcastPacketAll(new S_NPCPack(dummy));
//		      dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), 18225));
		      
		      //個人可見
		      final S_SkillSound polyactiddmg = new S_SkillSound(_pc.getId(),ConfigOther.ActidCriticalgfx);
		      _pc.sendPackets(polyactiddmg);

		      return true;
		    } 
	return false;
}

/**
 * 攻擊PC時的傷害計算
 * 
 * @return
 */
public int calcPcDamage() {
	if (_targetPc == null) {
		return 0;
	}

	if (dmg0(_targetPc)) {
		_isHit = false;
		_drainHp = 0;
		return 0;
	}

	if (!_isHit) {
		return 0;
	}
    if (this._targetPc.isActived() && this._targetPc.getAu_OtherSet(1) > 0 && this._targetPc.getMap().isTeleportable() && this._targetPc.getAu_AutoSet(0) == 0 && !this._targetPc.hasSkillEffect(79501))
    {
      this._targetPc.setSkillEffect(79501, 2000);
    }
    int weaponMaxDamage = this._weaponSmall;
    int StrCridmg = StrDmg.getCriDmgSkill(this._pc, this._pc.getStr());// 爆擊傷害
    int DexCridmg = DexDmg.getCriDmgSkill(this._pc, this._pc.getDex());// 爆擊傷害
    int IntCridmg = IntSp.getCriDmgSkill(this._pc, this._pc.getInt());// 爆擊傷害
    
    this._weaponDamage = weaponDamage1(weaponMaxDamage);
    
    if (_pc.hasSkillEffect(175)) {// 烈焰之魂 武器最大傷害值
    	if (_weaponType != 20 && _weaponType != 62) {
    		_weaponDamage = weaponMaxDamage;
    	}
    }

    if (_weaponType == 20 || _weaponType == 62) {// 遠距離武器
    	if (isDexCriticalDmg()) {
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * DexCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * DexCridmg;// 武器最大傷害值
    	}
    } else if (_weaponType2 == 7 || _weaponType2 == 16 || _weaponType2 == 17) {// 奇古獸
    	if (isIntCriticalDmg()) {// 魔法爆擊
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * IntCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * IntCridmg;// 武器最大傷害值
    	}

    } /*else {// 近距離武器
    	if (isStrCriticalDmg()) {
    		_weaponDamage = weaponMaxDamage;// 武器最大傷害值
    	}
    }*/
    else {// 近距離武器
    	if (isStrCriticalDmg()) {
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * StrCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * StrCridmg;// 武器最大傷害值
    	} else if (_pc.hasSkillEffect(SOUL_OF_FLAME) && _weapon != null) {// 武器最大傷害值 * 烈焰之魂傷害倍率
    		_weaponDamage = weaponMaxDamage;
			_weaponDamage = (int) (_weaponDamage * ConfigSkill.SOUL_OF_FLAME_DAMAGE);
    	}
    }
    
    if (this._pc.is_mazu()) {
      this._weaponDamage += 5;
    }
    this._weaponTotalDamage = this._weaponDamage + this._weaponAddDmg + this._weaponEnchant;
    
    double dmg = weaponDamage2(this._weaponTotalDamage);
    
    dmg = pcDmgMode(dmg);

    if (this._targetPc.hasSkillEffect(113)) {
//      double attackerlv = this._pc.getLevel();
//      double adddmg = attackerlv / 15.0D / 100.0D + 1.01D;
      dmg *= ConfigSkill.STRIKER_DMG;
    } 
// 近距離武器
if (_weaponRange != -1 && _targetPc.hasSkillEffect(ARMOR_BREAK)) {
	dmg += dmg * 0.58;
}

    int DamageReduction = this._targetPc.getDamageReductionByArmor() + this._targetPc.getother_ReductionDmg() + this._targetPc.getClan_ReductionDmg() + this._targetPc.get_reduction_dmg();
    dmg -= DamageReduction;
    
    dmg -= this._targetPc.dmgDowe();
    
    if (this._targetPc.getClanid() != 0) {
      dmg -= getDamageReductionByClan(this._targetPc);
    }

    if (DamageReduction > 0 && 
      this._targetPc.getNoweaponRedmg() > 0) {
      dmg += this._targetPc.getNoweaponRedmg();
    }

    if (this._pc.hasSkillEffect(98767)) {
      this._pc.killSkillEffectTimer(98767);
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("已偵測您處於打架狀態,並取消驗證"));
      this._pc.sendPackets((ServerBasePacket)new S_CharTitle(this._pc.getId(), this._pc.get_savetitle()));
      this._pc.setrantitle(0);
      this._pc.set_savetitle(null);
      this._pc.addaierrorcheck(0);
    } 
    dmg += weaponSkill(this._pc, (L1Character)this._targetPc, this._weaponTotalDamage);
    
    if (!this._pc.getDolls().isEmpty()) {
      Iterator<L1DollInstance> iter = this._pc.getDolls().values()
        .iterator(); while (iter.hasNext()) {
        L1DollInstance doll = iter.next();
        
        doll.startDollSkill((L1Character)this._targetPc, dmg);
      } 
    } 
    if (this._targetPc.hasSkillEffect(88)) {
      int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
      dmg -= ((targetPcLvl - 50) / 5 + 10);
    } 
    if (this._pc.get_other().get_PvP_dmg() > 0 && this._weaponType != 20) {
      dmg += this._pc.get_other().get_PvP_dmg();
    }
    if (this._pc.get_other().get_PvP_bow_dmg() > 0 && this._weaponType == 20) {
      dmg += this._pc.get_other().get_PvP_bow_dmg();
    }
    if (this._pc.get_other().get_Ran_PvP_dmg_b() > 0 && _random.nextInt(100) + 1 <= this._pc.get_other().get_Ran_PvP_dmg_b()) {
      if (this._pc.get_other().getPvP_dmg_b() >= 1.0D) {
        dmg *= this._pc.get_other().getPvP_dmg_b();
      } else {
        dmg *= 1.0D + this._pc.get_other().getPvP_dmg_b();
      } 
    }
    if (this._targetPc.get_other().get_PvP_dmg_r() > 0) {
      dmg -= this._targetPc.get_other().get_PvP_dmg_r();
    }
    if (this._pc.get_PVPdmgg() > 0) {
      dmg += this._pc.get_PVPdmgg();
    }
    
    if (this._pc.getDmgdouble() > 0.0D) {
      dmg *= this._pc.getDmgdouble();
    }
    if (this._pc.getweaponran() != 0 && _random.nextInt(100) + 1 <= this._pc.getweaponran()) {
      if (this._pc.getweapondmg() != 0) {
        dmg += (_random.nextInt(this._pc.getweapondmg()) + 1);
      }
      
      if (this._pc.getweaponchp() != 0) {
        this._pc.setCurrentHp(this._pc.getCurrentHp() + this._pc.getweaponchp());
      }
      
      if (this._pc.getweaponcmp() != 0) {
        this._pc.setCurrentMp(this._pc.getCurrentMp() + this._pc.getweaponcmp());
      }
      if (this._pc.getweapongfx() != 0) {
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), this._pc.getweapongfx()));
      }
    }

    if (this._pc.isCrown()) {
      dmg *= Config_Pc_Damage.Other_To_isCrownpc;
    }
    else if (this._pc.isKnight()) {
      dmg *= Config_Pc_Damage.Other_To_isKnightpc;
    }
    else if (this._pc.isWizard()) {
      dmg *= Config_Pc_Damage.Other_To_isWizardpc;
    }
    else if (this._pc.isElf()) {
      dmg *= Config_Pc_Damage.Other_To_isElfpc;
    }
    else if (this._pc.isDarkelf()) {
      dmg *= Config_Pc_Damage.Other_To_isDarkelfpc;
    }
    else if (this._pc.isDragonKnight()) {
      dmg *= Config_Pc_Damage.Other_To_isDragonKnightpc;
    }
    else if (this._pc.isIllusionist()) {
      dmg *= Config_Pc_Damage.Other_To_isIllusionistpc;
    } 
    
    if (this._targetPc.getPVPdmgReduction() > 0) {
      dmg -= this._targetPc.getPVPdmgReduction();
    }
    
    if (this._pc.getPVPdmg() != 0) {
      dmg += this._pc.getPVPdmg();
    }
    if (this._pc.get_weaknss() == 1) {
      dmg += 3.0D;
    }
    else if (this._pc.get_weaknss() == 2) {
      dmg += 8.0D;
    }
    else if (this._pc.get_weaknss() == 3) {
      dmg += 15.0D;
    } 

    if (this._pc.hasSkillEffect(8895) && 
      _random.nextInt(100) + 1 <= 3) {
      PeepCard.BackMagic(this._pc, (L1Object)this._targetPc);
    }

    if (this._pc.hasSkillEffect(8863)) {
      this._damage += 5;
    }
    if (this._targetPc.hasSkillEffect(8867)) {
      dmg = 0.0D;
    }
    
    if (this._pc.hasSkillEffect(8871)) {
      dmg += ((int)dmg + this._pc.getStr());
    }
    if (this._pc.hasSkillEffect(8873)) {
      this._targetPc.receiveDamage((L1Character)this._pc, (200 + _random.nextInt(150) + 1), false, false);
      L1PcInstance pc = (L1PcInstance)this._target;
      L1SpawnUtil.spawnEffect(81162, 2, pc.getX(), pc.getY(), pc.getMapId(), (L1Character)pc, 0);
      pc.setSkillEffect(87, 3000);
      pc.sendPackets((ServerBasePacket)new S_Paralysis(5, true));
      pc.killSkillEffectTimer(8873);
    } 
    
    if (this._targetPc.isIllusionist() && this._targetPc.getlogpcpower_SkillFor4() != 0) {
      int hp = this._targetPc.getMaxHp() / this._targetPc.getCurrentHp();
      if (hp > 1) {
        if (hp > 10) {
          hp = 10;
        }
        if (RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor4()) {
          dmg -= (hp * 40);
          this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 痛苦化身 減免了" + (hp * 40) + "滴傷害。"));
          this._targetPc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 5377));
        } 
      } 
    } 

    if (this._pc.isIllusionist() && this._pc.getlogpcpower_SkillFor5() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
      this._targetPc.setSkillEffect(8007, 2000);
      
      this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("對方發動 冰封心智 使你無法攻擊二秒 。"));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 冰封心智"));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
    } 

    if (this._targetPc.hasSkillEffect(9055)) {
      dmg = 0.0D;
    }
    if (this._targetPc.isCrown() && this._targetPc.getlogpcpower_SkillFor1() != 0 && !this._targetPc.hasSkillEffect(9055) && 
      RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor1()) {
      this._targetPc.setSkillEffect(9055, 1000);
      this._targetPc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 2234));
      this._targetPc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 2234));
      this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 王者無敵1秒"));
    } 

    if (this._targetPc.isCrown() && this._targetPc.isEsoteric()) {
      dmg = (int)(dmg * (1.0D + this._targetPc.getlogpcpower_SkillFor2() * 0.01D));
    }
    
    if (this._pc.isCrown() && this._pc.isEsoteric()) {
      dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor2() * 0.02D));
    }
    
    if (this._pc.isCrown() && this._pc.getlogpcpower_SkillFor4() != 0 && 
      this._pc.getlogpcpower_SkillFor4() >= 1 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
      this._targetPc.setSkillEffect(4017, 1000);
      
      this._target.broadcastPacketX8((ServerBasePacket)new S_SkillSound(this._target.getId(), 4184));
      this._targetPc.sendPackets((ServerBasePacket)new S_SkillSound(this._target.getId(), 4184));
      this._targetPc.sendPackets((ServerBasePacket)new S_Paralysis(6, true));
    } 

    if (this._weaponType == 20 && this._pc.isTripleArrow() && 
      this._pc.getlogpcpower_SkillFor1() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
      dmg += (this._targetPc.getCurrentHp() * 2 / 170);
      this._pc.setTripleArrow(false);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 射擊之箭"));
    } 

    if (this._pc.hasSkillEffect(175) && this._weaponType != 20 && 
      this._weaponType != 62 && this._pc.isElf() && 
      this._pc.getlogpcpower_SkillFor3() != 0 && 
      this._pc.getElfAttr() == 2 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
      dmg = (int) (dmg * ConfigSkill.SOUL_OF_FLAME_ALLDAMAGE);;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發精通能量 造成4倍傷害！"));
    } 
    if (this._weaponType == 20 && this._pc.hasSkillEffect(166) && 
      this._pc.getElfAttr() == 8 && this._pc.isElf() && 
      this._pc.getlogpcpower_SkillFor3() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
      dmg *= 1.4D;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發精通能量 造成2倍傷害！"));
    } 
    if (this._targetPc.hasSkillEffect(168) && this._targetPc.isElf() && this._targetPc.getlogpcpower_SkillFor3() != 0 && 
      this._targetPc.getElfAttr() == 1 && 
      RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
      dmg *= 0.3D;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 精通能量 減免傷害"));
    }
    if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor4() != 0) {
      int rr = this._targetPc.getCurrentMp() / 100;
      if (RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
        this._targetPc.setCurrentMp(Math.max(this._targetPc.getCurrentMp() - rr, 0));
      }
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 吞噬魔力"));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
    } 
    if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor5() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
      if (this._weaponType != 20 && this._weaponType != 62) {
        dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor5() * 0.03D));
      } else {
        dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor5() * 0.02D));
      } 
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 傷害擊殺"));
    } 
    if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.isEsoteric() && 
      this._pc.getlogpcpower_SkillFor4() >= 1) {
      dmg = (int)(dmg * (this._pc.getlogpcpower_SkillFor4() * 0.03D + 1.0D));
    }
    if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor5() != 0 && this._pc.isEsoteric())
    {
      if (this._pc.getCurrentMp() > 2 * this._pc.getlogpcpower_SkillFor5()) {
        dmg += (5 * this._pc.getlogpcpower_SkillFor5());
        this._pc.setCurrentMp(this._pc.getCurrentMp() - 2 * this._pc.getlogpcpower_SkillFor5());
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 4592));
        this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), 4592));
      } else {
        this._pc.setEsoteric(false);
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU關閉轉生技能(刀劍之影)"));
      } 
    }
    if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor1() != 0) {
      boolean isSameAttr = false;
      if (this._targetPc.getHeading() == 0 && (this._pc.getHeading() == 7 || this._pc.getHeading() == 0 || this._pc
        .getHeading() == 1)) {
        isSameAttr = true;
      } else if (this._targetPc.getHeading() == 7 && (this._pc.getHeading() == 6 || this._pc.getHeading() == 7 || this._pc
        .getHeading() == 0)) {
        isSameAttr = true;
      } else if (this._targetPc.getHeading() == this._pc.getHeading() || 
        this._targetPc.getHeading() == this._pc.getHeading() + 1 || 
        this._targetPc.getHeading() == this._pc.getHeading() - 1) {
        isSameAttr = true;
      } 
      if (isSameAttr && 
        RandomArrayList.getInc(100, 1) <= this._pc
        .getlogpcpower_SkillFor1()) {
        dmg = (int)(dmg * 1.5D);
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 背部襲擊 造成 1.5倍傷害"));
      } 
    } 
    if (this._pc.isDragonKnight() && this._pc.isFoeSlayer() && 
      this._pc.getlogpcpower_SkillFor1() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
      dmg += (this._target.getCurrentHp() * 5 / 100);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 屠宰擊殺"));
    } 
    if (this._pc.isDragonKnight() && this._pc.getlogpcpower_SkillFor2() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor2()) {
      double chp = dmg;
      this._pc.setCurrentHp((int)(this._pc.getCurrentHp() + chp));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 1609));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 乾坤挪移 吸取 " + chp + "滴生命值"));
    } 
    if (this._targetPc.isDragonKnight() && this._targetPc.getlogpcpower_SkillFor3() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._targetPc.getlogpcpower_SkillFor3()) {
      dmg -= (int)dmg * this._targetPc.getlogpcpower_SkillFor3() * 0.01D;
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 強之護鎧"));
    } 
    if (this._pc.isDragonKnight() && 
      this._pc.getlogpcpower_SkillFor4() != 0 && 
      this._targetPc.getMaxHp() / 20 * 9 >= this._targetPc.getCurrentHp()) {
      double adddmg = 1.0D;
      if (this._pc.getlogpcpower_SkillFor4() >= 1) {
        adddmg += this._pc.getlogpcpower_SkillFor4() * 0.3D;
      }
      dmg *= adddmg;
    } 
    if (this._pc.isKnight() && this._pc.getlogpcpower_SkillFor4() != 0 && 
      this._pc.getMaxHp() / 3 >= this._pc.getCurrentHp() && 
      !this._pc.isEsoteric() && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
      dmg *= 1.9D;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 狂暴致命給予3倍高傷害"));
    } 
    int ran = _random.nextInt(100) + 1;
    if (this._targetPc.getInventory().checkSkillType(113) && ran <= ConfigOther.armor_type1) {
      dmg *= 0.98D;
    }
    if (this._targetPc.getInventory().checkSkillType(114) && ran <= ConfigOther.armor_type2) {
      dmg *= 0.95D;
    }
    if (this._targetPc.getInventory().checkSkillType(115) && ran <= ConfigOther.armor_type3) {
      dmg *= 0.9D;
    }
    if (this._targetPc.getInventory().checkSkillType(116) && ran <= ConfigOther.armor_type4) {
      dmg *= 0.85D;
    }
    if (this._targetPc.getInventory().checkSkillType(117) && ran <= ConfigOther.armor_type5) {
      dmg *= 0.8D;
    }
    if (this._pc.getInventory().checkSkillType(118) && ran <= ConfigOther.armor_type6) {
      dmg += 5.0D;
    }
    if (this._pc.getInventory().checkSkillType(119) && ran <= ConfigOther.armor_type7) {
      dmg += 10.0D;
    }
    if (this._pc.getInventory().checkSkillType(120) && ran <= ConfigOther.armor_type8) {
      dmg += 15.0D;
    }
    if (this._pc.getInventory().checkSkillType(121) && ran <= ConfigOther.armor_type9) {
      dmg += 20.0D;
    }
    if (this._pc.getInventory().checkSkillType(122) && ran <= ConfigOther.armor_type10) {
      dmg += 30.0D;
    }
    if (this._pc.getInventory().checkSkillType(123) && ran <= ConfigOther.armor_type11) {
      dmg *= 1.2D;
    }
    if (this._pc.getInventory().checkSkillType(124) && ran <= ConfigOther.armor_type12) {
      dmg *= 1.4D;
    }
    if (this._pc.getInventory().checkSkillType(125) && ran <= ConfigOther.armor_type13) {
      dmg *= 1.6D;
    }
    if (this._pc.getInventory().checkSkillType(126) && ran <= ConfigOther.armor_type14) {
      dmg *= 1.8D;
    }
    if (this._pc.getInventory().checkSkillType(127) && ran <= ConfigOther.armor_type15) {
      dmg *= 2.0D;
    }
    if (this._targetPc.getInventory().checkSkillType(128) && ran <= ConfigOther.armor_type16) {
      dmg = 0.0D;
    }
    if (this._targetPc.getInventory().checkSkillType(129) && ran <= ConfigOther.armor_type17) {
      dmg = 0.0D;
    }
    if (this._targetPc.getInventory().checkSkillType(130) && ran <= ConfigOther.armor_type18) {
      dmg = 0.0D;
    }
    if (this._targetPc.getInventory().checkSkillType(131) && ran <= ConfigOther.armor_type19) {
      dmg = 0.0D;
    }
    if (this._targetPc.getInventory().checkSkillType(132) && ran <= ConfigOther.armor_type20) {
      dmg = 0.0D;
    }
    if (this._pc.getDmgup_b() > 0.0D && _random.nextInt(100) + 1 <= this._pc.getDmgup_b_ran()) {
      dmg *= this._pc.getDmgup_b();
    }
    if (this._pc.get近戰爆擊發動率() > 0 && this._pc.get近戰爆擊倍率() > 0.0D && 
      this._weaponType != 20 && this._weaponType != 62 && 
      ran <= this._pc.get近戰爆擊發動率()) {
      dmg *= this._pc.get近戰爆擊倍率();
    }

    
    if (this._pc.get遠攻爆擊發動率() > 0 && this._pc.get遠攻爆擊倍率() > 0.0D && 
      this._weaponType == 20 && this._weaponType == 62 && 
      ran <= this._pc.get遠攻爆擊發動率()) {
      dmg *= this._pc.get遠攻爆擊倍率();
    }

    
    if (this._targetPc.getDmgReductionChance() > 0 && this._targetPc.getDmgReductionDmg() > 0 && 
      ran <= this._targetPc.getDmgReductionChance()) {
      dmg -= this._targetPc.getDmgReductionDmg();
    }
    
    if (this._pc.get吸取HP機率() > 0 && this._pc.get吸取HP固定值() > 0 && this._pc.get吸取HP隨機值() > 0 && this._pc.get吸取HP動畫() > 0 && 
      ran <= this._pc.get吸取HP機率()) {
      this._pc.setCurrentHp((short)(this._pc.getCurrentHp() + this._pc.get吸取HP固定值() + _random.nextInt(this._pc.get吸取HP隨機值()) + 1));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
    } 
    
    if (this._pc.get吸取MP機率() > 0 && this._pc.get吸取MP固定值() > 0 && this._pc.get吸取MP隨機值() > 0 && this._pc.get吸取MP動畫() > 0 && 
      ran <= this._pc.get吸取MP機率()) {
      this._pc.setCurrentMp((short)(this._pc.getCurrentMp() + this._pc.get吸取MP固定值() + _random.nextInt(this._pc.get吸取MP隨機值()) + 1));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
    } 
    
    if (this._pc.get攻擊力發動機率() > 0 && this._pc.get攻擊力固定值() > 0 && this._pc.get攻擊力隨機值() > 0 && this._pc.get攻擊力動畫() > 0 && 
      ran <= this._pc.get攻擊力發動機率()) {
      dmg += (this._pc.get攻擊力固定值() + _random.nextInt(this._pc.get攻擊力隨機值()) + 1);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
    } 
    
    if (ispolyactid()) {
      dmg += (this._pc.getpolyactiddmg() + _random.nextInt(this._pc.getpolyactiddmg1()) + 1);
      this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), this._pc.getpolyactidactid()));
    }


    
    for (L1ItemInstance item : this._targetPc.getInventory().getItems()) {
      if (item.getItemId() == 400041 && item.isEquipped()) {
        Random random = new Random();
        int r = random.nextInt(100) + 1;
        if (item.getEnchantLevel() * 2 >= r) {
          dmg -= 50.0D;
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 6320));
        } 
      } 
    } 

	// 其他減免計算
	boolean dmgX2 = false;
	if ((!_targetPc.getSkillisEmpty()) && (_targetPc.getSkillEffect().size() > 0)) {
		try {
			for (Integer key : _targetPc.getSkillEffect()) {
				Integer integer = (Integer) L1AttackList.SKD3.get(key);
				if (integer != null) {
					if (integer.equals(key)) {
						dmgX2 = true;
					} else {
						dmg += integer.intValue();
					}
				}
			}
		} catch (ConcurrentModificationException localConcurrentModificationException) {

		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	dmg = BuffDmgUp(dmg);// 屬火、燃鬥、勇猛意志增傷計算
	
//if (dmgX2) {// 聖界減傷
//	dmg /= 2.0D;
//}
	
	if (_targetPc.hasSkillEffect(IMMUNE_TO_HARM)) {// 聖界減傷
      dmg /= ConfigOther.IMMUNE_TO_HARM;
    }

   if (this._targetPc.hasSkillEffect(8908)) {// 聖界lv2減傷
      dmg /= 2;
    }

    if (dmg <= 0.0D) {// 減免計算後傷害0以下
      this._isHit = false;
      this._drainHp = 0;
    } 
    
    return (int)dmg;
  }


  
  private int c3_power() {
    if (this._pc.hasSkillEffect(7005)) {
      return 1;
    }
    if (this._pc.hasSkillEffect(7006)) {
      return 2;
    }
    return 0;
  }

  
  private int c3_power_to_pc(int type) {
    int damage = c3_power_dmg(type);
    int resist = 0;
    switch (type) {
      case 1:
        resist = this._targetPc.getFire();
        
        if (resist > 0) {
          damage = c3_power_dmg_down(damage, Math.min(100, resist)); break;
        } 
        if (resist < 0) {
          damage = c3_power_dmg_up(damage, Math.min(0, resist));
        }
        break;
      case 2:
        resist = this._targetPc.getWater();
        
        if (resist > 0) {
          damage = c3_power_dmg_down(damage, Math.min(100, resist)); break;
        } 
        if (resist < 0) {
          damage = c3_power_dmg_up(damage, Math.min(0, resist));
        }
        break;
    } 
    return damage;
  }

  
  private int c3_power_to_npc(int type) {
    int damage = c3_power_dmg(type);
    switch (type) {
      case 1:
        if (this._targetNpc instanceof L1MonsterInstance) {
          L1MonsterInstance tgmob = (L1MonsterInstance)this._targetNpc;
          if (!tgmob.isDead()) {
            tgmob.receiveDamage((L1Character)this._pc, damage, 2);
            tgmob.broadcastPacketX8((ServerBasePacket)new S_DoActionGFX(tgmob.getId(), 2));
          } 
        } 
        break;
      
      case 2:
        if (this._targetNpc instanceof L1MonsterInstance) {
          L1MonsterInstance tgmob = (L1MonsterInstance)this._targetNpc;
          if (!tgmob.isDead()) {
            tgmob.receiveDamage((L1Character)this._pc, damage, 4);
            tgmob.broadcastPacketX8((ServerBasePacket)new S_DoActionGFX(tgmob.getId(), 2));
          } 
        } 
        break;
    } 
    
    return 0;
  }

  
  private int c3_power_dmg_down(int damage, int resist) {
    int r = 100 - resist;
    int dmg = damage * r / 100;
    return Math.max(10, dmg);
  }

  
  private int c3_power_dmg_up(int damage, int resist) {
    int dmg = damage - damage * resist / 100;
    return Math.abs(dmg);
  }
  
  private int c3_power_dmg(int type) {
    int damage = 0;
    int level = this._pc.getLevel();
    switch (type) {
      case 1:
        if (level >= 50 && level < 70) {
          damage = random_dmg(40, 100); break;
        } 
        if (level >= 70 && level < 90) {
          damage = random_dmg(50, 120); break;
        } 
        if (level >= 90 && level < 110) {
          damage = random_dmg(60, 140); break;
        } 
        if (level >= 110 && level < 130) {
          damage = random_dmg(70, 160); break;
        } 
        if (level >= 130 && level < 150) {
          damage = random_dmg(80, 180); break;
        } 
        if (level >= 150 && level < 175) {
          damage = random_dmg(90, 200); break;
        } 
        if (level >= 175 && level < 190) {
          damage = random_dmg(100, 250); break;
        } 
        if (level >= 190 && level < 200) {
          damage = random_dmg(110, 300);
          break;
        } 
        damage = random_dmg(200, 300);
        break;
      
      case 2:
        if (level >= 50 && level < 70) {
          damage = random_dmg(40, 100); break;
        } 
        if (level >= 70 && level < 90) {
          damage = random_dmg(50, 120); break;
        } 
        if (level >= 90 && level < 110) {
          damage = random_dmg(60, 140); break;
        } 
        if (level >= 110 && level < 130) {
          damage = random_dmg(70, 160); break;
        } 
        if (level >= 130 && level < 150) {
          damage = random_dmg(80, 180); break;
        } 
        if (level >= 150 && level < 175) {
          damage = random_dmg(90, 200); break;
        } 
        if (level >= 175 && level < 190) {
          damage = random_dmg(100, 250); break;
        } 
        if (level >= 190 && level < 200) {
          damage = random_dmg(110, 300);
          break;
        } 
        damage = random_dmg(200, 300);
        break;
    } 
    
    return damage;
  }
  
  private int random_dmg(int i, int j) {
    return _random.nextInt(j - i) + i;
  }


	/**
	 * 攻擊NPC時的傷害計算
	 * 
	 * @return
	 */
  private int calcNpcDamage() {
    if (this._targetNpc == null) {
      return 0;
    }
    
    if (dmg0((L1Character)this._targetNpc)) {
      this._isHit = false;
      this._drainHp = 0;
      return 0;
    } 
    
    if (!this._isHit) {
      return 0;
    }

    int weaponMaxDamage = 0;
    int StrCridmg = StrDmg.getCriDmgSkill(this._pc, this._pc.getStr());// 爆擊傷害
    int DexCridmg = DexDmg.getCriDmgSkill(this._pc, this._pc.getDex());// 爆擊傷害
    int IntCridmg = IntSp.getCriDmgSkill(this._pc, this._pc.getInt());// 爆擊傷害
    
    if (this._targetNpc.getNpcTemplate().isSmall() && this._weaponSmall > 0) {
      weaponMaxDamage = this._weaponSmall;
    }
    else if (this._targetNpc.getNpcTemplate().isLarge() && this._weaponLarge > 0) {
      weaponMaxDamage = this._weaponLarge;
    }
    else if (this._weaponSmall > 0) {
      weaponMaxDamage = this._weaponSmall;
    } 
    
    this._weaponDamage = weaponDamage1(weaponMaxDamage);// 武器亂數傷害計算

    if (_pc.hasSkillEffect(175)) {// 烈焰之魂 武器最大傷害值
		if (_weaponType != 20 && _weaponType != 62) {
			_weaponDamage = weaponMaxDamage;
		}
	}

	if (_weaponType == 20 || _weaponType == 62) {// 遠距離武器
    	if (isDexCriticalDmg()) {
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * DexCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * DexCridmg;// 武器最大傷害值
    	}
		
    } else if (_weaponType2 == 7 || _weaponType2 == 16 || _weaponType2 == 17) {// 奇古獸
    	if (isIntCriticalDmg()) {// 魔法爆擊
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * IntCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * 1;// 武器最大傷害值
    	}

    } /*else {// 近距離武器
    	if (isStrCriticalDmg()) {
    		_weaponDamage = weaponMaxDamage;// 武器最大傷害值
    	}
    }*/
    else {// 近距離武器
    	if (isStrCriticalDmg()) {
    		_weaponDamage = (weaponMaxDamage + _weaponEnchant) * StrCridmg;// 武器最大傷害值 + 強化值 * 爆擊傷害
//    		_weaponDamage = weaponMaxDamage * StrCridmg;// 武器最大傷害值
    	} else if (_pc.hasSkillEffect(SOUL_OF_FLAME) && _weapon != null) {// 武器最大傷害值 * 烈焰之魂傷害倍率
			_weaponDamage = weaponMaxDamage;
			_weaponDamage = (int) (_weaponDamage * ConfigSkill.SOUL_OF_FLAME_DAMAGE);
    	}
    }

    if (this._pc.is_mazu()) {
      this._weaponDamage += 5;
    }
    
    this._weaponTotalDamage = this._weaponDamage + this._weaponAddDmg + this._weaponEnchant;
    
    this._weaponTotalDamage += calcMaterialBlessDmg();
    
    double dmg = weaponDamage2(this._weaponTotalDamage);

    dmg = pcDmgMode(dmg);
    
    dmg -= calcNpcDamageReduction();
    
    dmg -= calcPcDefense();
    
    dmg += weaponSkill(this._pc, (L1Character)this._targetNpc, this._weaponTotalDamage);
    boolean isNowWar = false;
    int castleId = L1CastleLocation.getCastleIdByArea((L1Character)this._targetNpc);
    if (castleId > 0) {
      isNowWar = ServerWarExecutor.get().isNowWar(castleId);
    }
    if (!isNowWar) {
      if (this._targetNpc instanceof com.lineage.server.model.Instance.L1PetInstance) {
        dmg *= ConfigOther.pcdmgpet;
      }

      if (this._targetNpc instanceof L1SummonInstance) {
        L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
        if (summon.isExsistMaster()) {
          dmg *= ConfigOther.pcdmgsumm;
        }
      }
    
    }
    else if (isNowWar) {
      if (this._targetNpc instanceof com.lineage.server.model.Instance.L1PetInstance) {
        dmg *= ConfigOther.pcdmgpet_war;
      }

      if (this._targetNpc instanceof L1SummonInstance) {
        L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
        if (summon.isExsistMaster()) {
          dmg *= ConfigOther.pcdmgsumm_war;
        }
      } 
    } 

    if (this._pc.getweapon_b_gfx_r() > 0 && 
      RandomArrayList.getInc(100, 1) >= this._pc.getweapon_b_gfx_r()) {
      int[] giveItem = this._pc.getweapon_b_gfx();
      int rndItem = RandomArrayList.getInt(giveItem.length);
      int giveItemGet = giveItem[rndItem];
      this._pc.sendPacketsYN((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), giveItemGet));
    } 
    
    if (this._pc.getweaponran() != 0 && _random.nextInt(100) + 1 <= this._pc.getweaponran()) {
      if (this._pc.getweapondmg() != 0) {
        dmg += (_random.nextInt(this._pc.getweapondmg()) + 1);
      }
      
      if (this._pc.getweaponchp() != 0) {
        this._pc.setCurrentHp(this._pc.getCurrentHp() + this._pc.getweaponchp());
      }
      
      if (this._pc.getweaponcmp() != 0) {
        this._pc.setCurrentMp(this._pc.getCurrentMp() + this._pc.getweaponcmp());
      }
      if (this._pc.getweapongfx() != 0) {
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), this._pc.getweapongfx()));
      }
    } 
    
    if (this._targetNpc.getNpcTemplate().is_hard() && this._pc.getpenetrate() != 1 && (
      this._weaponType == 20 || this._weaponType == 62)) {
      dmg -= 3.0D;
    }
    
    if (this._pc.getDmgdouble() > 0.0D) {
      dmg *= this._pc.getDmgdouble();
    }
if (this._pc.isTripleArrow() && _pc.getInventory().checkItem(150002,1)) {
	dmg = dmg * ConfigSkill.TRIPLE_ARROW_DMG + 3.0D;
//	System.err.println(111111);
}
	else if (this._pc.isTripleArrow()) {
		dmg *= ConfigSkill.TRIPLE_ARROW_DMG;
//		System.err.println(222222);
	}

	dmg = BuffDmgUp(dmg);// 屬火、燃鬥、勇猛意志增傷計算
    if (this._pc.isCrown()) {
      dmg *= Config_Pc_Damage.Other_To_isCrownnpc;
    }
    else if (this._pc.isKnight()) {
      dmg *= Config_Pc_Damage.Other_To_isKnightnpc;
    }
    else if (this._pc.isWizard()) {
      dmg *= Config_Pc_Damage.Other_To_isWizardnpc;
    }
    else if (this._pc.isElf()) {
      dmg *= Config_Pc_Damage.Other_To_isElfnpc;
    }
    else if (this._pc.isDarkelf()) {
      dmg *= Config_Pc_Damage.Other_To_isDarkelfnpc;
    }
    else if (this._pc.isDragonKnight()) {
      dmg *= Config_Pc_Damage.Other_To_isDragonKnightnpc;
    }
    else if (this._pc.isIllusionist()) {
      dmg *= Config_Pc_Damage.Other_To_isIllusionistnpc;
    } 
    if (this._weaponEnchant >= 10) {
      dmg += (this._weaponEnchant - 9);
    }
    if (this._pc.get_weaknss() == 1) {
      dmg += 3.0D;
    }
    else if (this._pc.get_weaknss() == 2) {
      dmg += 8.0D;
    }
    else if (this._pc.get_weaknss() == 3) {
      dmg += 15.0D;
    } 
    if (!this._targetNpc.getNpcTemplate().is_boss()) {
      L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
      if (template.getImpl().equals("L1Monster") && 
        this._pc.hasSkillEffect(98765)) {
        this._pc.killSkillEffectTimer(98765);
        this._pc.setSkillEffect(98766, 2000);
      } 
    } 

    if (!this._targetNpc.getNpcTemplate().is_boss()) {
      L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
      if (template.getImpl().equals("L1Monster") && 
        !this._pc.isActived() && this._pc.get_followmaster() == null && this._pc.hasSkillEffect(6931)) {
        this._pc.killSkillEffectTimer(6931);
        this._pc.setSkillEffect(6932, 1000);
      } 
    } 

    if (this._pc.hasSkillEffect(7951) && !this._pc.isGm() && !this._targetNpc.getNpcTemplate().is_boss() && !isInWarAreaAndWarTime(this._pc)) {
      
      this._pc.killSkillEffectTimer(7951);
      this._pc.setSkillEffect(7952, 2000);
    } 
    if (this._weaponRange != -1 && this._targetNpc.hasSkillEffect(ARMOR_BREAK) && !this._targetNpc.getNpcTemplate().is_boss())
    {
      dmg += dmg * 0.58;
    }
    
    if (this._pc.hasSkillEffect(8863)) {
      this._damage += 5;
    }
    
    if (this._pc.hasSkillEffect(8871)) {
      dmg += ((int)dmg + this._pc.getStr());
    }
    if (this._pc.getDmgup_b() > 0.0D && _random.nextInt(100) + 1 <= this._pc.getDmgup_b_ran()) {
      dmg *= this._pc.getDmgup_b();
    }

    if (!this._pc.getDolls().isEmpty()) {
      Iterator<L1DollInstance> iter = this._pc.getDolls().values()
        .iterator(); while (iter.hasNext()) {
        L1DollInstance doll = iter.next();
        doll.startDollSkill((L1Character)this._targetNpc, dmg);
      } 
    } 
    
    if (this._pc.isCrown() && this._pc.isEsoteric()) {
      dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor2() * 0.02D));
    }
    
    if (this._weaponType == 20 && this._pc.isTripleArrow() && 
      this._pc.getlogpcpower_SkillFor1() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
      dmg += 110.0D;
      this._pc.setTripleArrow(false);
      
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 射擊之箭"));
    } 

    if (this._pc.hasSkillEffect(175) && this._weaponType != 20 && 
      this._weaponType != 62 && this._pc.isElf() && 
      this._pc.getlogpcpower_SkillFor3() != 0 && 
      this._pc.getElfAttr() == 2 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
      dmg = (int) (dmg * ConfigSkill.SOUL_OF_FLAME_ALLDAMAGE);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發精通元素 造成4倍傷害！"));
    } 

    if (this._weaponType == 20 && this._pc.hasSkillEffect(166) && 
      this._pc.getElfAttr() == 8 && this._pc.isElf() && 
      this._pc.getlogpcpower_SkillFor3() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor3()) {
      dmg *= 1.6D;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發精通元素 造成2倍傷害！"));
    } 

    if (this._pc.isElf() && this._pc.getlogpcpower_SkillFor5() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor5()) {
      if (this._weaponType != 20 && this._weaponType != 62) {
        dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor5() * 0.03D));
      } else {
        dmg = (int)(dmg * (1.0D + this._pc.getlogpcpower_SkillFor5() * 0.02D));
      } 
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 傷害擊殺"));
    } 

    if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor4() != 0 && this._pc.isEsoteric() && 
      this._pc.getlogpcpower_SkillFor4() >= 1) {
      dmg = (int)(dmg * (this._pc.getlogpcpower_SkillFor4() * 0.03D + 1.0D));
    }

    if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor5() != 0 && 
      this._pc.isEsoteric()) {
      if (this._pc.getCurrentMp() > 2 * this._pc.getlogpcpower_SkillFor5()) {
        dmg += (5 * this._pc.getlogpcpower_SkillFor5());
        this._pc.setCurrentMp(this._pc.getCurrentMp() - 2 * this._pc.getlogpcpower_SkillFor5());
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 4592));
        this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), 
              4592));
      } else {
        this._pc.setEsoteric(false);
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU關閉轉生技能(刀劍之影)"));
      } 
    }
    
    if (this._pc.isDragonKnight() && this._pc.isFoeSlayer() && 
      this._pc.getlogpcpower_SkillFor1() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
      dmg += (this._targetNpc.getCurrentHp() * 3 / 100);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 屠宰擊殺"));
    } 

    if (this._pc.isDragonKnight() && this._pc.getlogpcpower_SkillFor2() != 0 && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor2()) {
      double chp = dmg;
      this._pc.setCurrentHp((int)(this._pc.getCurrentHp() + chp));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 1609));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 乾坤挪移 吸取 " + chp + "滴生命值"));
    } 

    if (this._pc.isKnight() && this._pc.getlogpcpower_SkillFor4() != 0 && 
      this._pc.getMaxHp() / 3 >= this._pc.getCurrentHp() && 
      !this._pc.isEsoteric() && 
      RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor4()) {
      dmg *= 1.9D;
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
      this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 狂暴致命給予3倍高傷害"));
    } 

    int ran = _random.nextInt(100) + 1;

    if (this._pc.getInventory().checkSkillType(118) && ran <= ConfigOther.armor_type6) {
      dmg += 5.0D;
    }
    if (this._pc.getInventory().checkSkillType(119) && ran <= ConfigOther.armor_type7) {
      dmg += 10.0D;
    }
    if (this._pc.getInventory().checkSkillType(120) && ran <= ConfigOther.armor_type8) {
      dmg += 15.0D;
    }
    if (this._pc.getInventory().checkSkillType(121) && ran <= ConfigOther.armor_type9) {
      dmg += 20.0D;
    }
    if (this._pc.getInventory().checkSkillType(122) && ran <= ConfigOther.armor_type10) {
      dmg += 30.0D;
    }
    if (this._pc.getInventory().checkSkillType(123) && ran <= ConfigOther.armor_type11) {
      dmg *= 1.2D;
    }
    if (this._pc.getInventory().checkSkillType(124) && ran <= ConfigOther.armor_type12) {
      dmg *= 1.4D;
    }
    if (this._pc.getInventory().checkSkillType(125) && ran <= ConfigOther.armor_type13) {
      dmg *= 1.6D;
    }
    if (this._pc.getInventory().checkSkillType(126) && ran <= ConfigOther.armor_type14) {
      dmg *= 1.8D;
    }
    if (this._pc.getInventory().checkSkillType(127) && ran <= ConfigOther.armor_type15) {
      dmg *= 2.0D;
    }
    if (this._pc.get_followmaster() != null) {
      dmg /= 3.0D;
    }
    int Npc_Mid = this._targetNpc.getNpcTemplate().get_npcId();
    if (Npc_Mid == L1Mon.CheckNpcMid(Npc_Mid)) {
      this._pc.setnpcdmg((int)this._pc.getnpcdmg() + dmg);
      if (this._pc.getnpciddmg() == 0) {
        this._pc.setnpciddmg(this._targetNpc.getNpcTemplate().get_npcId());
      }
      this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU累積攻擊傷害" + this._pc.getnpcdmg()));

      if (this._pc.getnpciddmg() != this._targetNpc.getNpcTemplate().get_npcId()) {
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU您攻擊累積傷害的怪物不是同一隻"));
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU累積攻擊傷害歸0"));
        this._pc.setnpcdmg(0.0D);
        this._pc.setnpciddmg(0);
      } 
    } 
    
    if (this._pc.get近戰爆擊發動率() > 0 && this._pc.get近戰爆擊倍率() > 0.0D && 
      this._weaponType != 20 && this._weaponType != 62 && 
      ran <= this._pc.get近戰爆擊發動率()) {
      dmg *= this._pc.get近戰爆擊倍率();
    }

    if (this._pc.get遠攻爆擊發動率() > 0 && this._pc.get遠攻爆擊倍率() > 0.0D && 
      this._weaponType == 20 && this._weaponType == 62 && 
      ran <= this._pc.get遠攻爆擊發動率()) {
      dmg *= this._pc.get遠攻爆擊倍率();
    }

    if (this._pc.get吸取HP機率() > 0 && this._pc.get吸取HP固定值() > 0 && this._pc.get吸取HP隨機值() > 0 && this._pc.get吸取HP動畫() > 0 && 
      ran <= this._pc.get吸取HP機率()) {
      this._pc.setCurrentHp((short)(this._pc.getCurrentHp() + this._pc.get吸取HP固定值() + _random.nextInt(this._pc.get吸取HP隨機值()) + 1));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取HP動畫()));
    } 
    
    if (this._pc.get吸取MP機率() > 0 && this._pc.get吸取MP固定值() > 0 && this._pc.get吸取MP隨機值() > 0 && this._pc.get吸取MP動畫() > 0 && 
      ran <= this._pc.get吸取MP機率()) {
      this._pc.setCurrentMp((short)(this._pc.getCurrentMp() + this._pc.get吸取MP固定值() + _random.nextInt(this._pc.get吸取MP隨機值()) + 1));
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get吸取MP動畫()));
    } 
    
    if (this._pc.get攻擊力發動機率() > 0 && this._pc.get攻擊力固定值() > 0 && this._pc.get攻擊力隨機值() > 0 && this._pc.get攻擊力動畫() > 0 && 
      ran <= this._pc.get攻擊力發動機率()) {
      dmg += (this._pc.get攻擊力固定值() + _random.nextInt(this._pc.get攻擊力隨機值()) + 1);
      this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
      this._pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), this._pc.get攻擊力動畫()));
    } 

    if (this._pc.getpolyactidran() > 0 && 
      ran <= this._pc.getpolyactidran()) {
      dmg += (this._pc.getpolyactiddmg() + _random.nextInt(this._pc.getpolyactiddmg1()) + 1);
      this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), this._pc.getpolyactidactid()));
//    //所有玩家可見
//    L1Location loc = _target.getLocation();
//    L1NpcInstance dummy = L1SpawnUtil.spawnS(loc, 86132, _pc.get_showId(), 1, _pc.getHeading());
//    dummy.broadcastPacketAll(new S_NPCPack(dummy));
//    dummy.broadcastPacketAll(new S_SkillSound(dummy.getId(), 18225));

    //個人可見
    final S_SkillSound polyactiddmg = new S_SkillSound(_pc.getId(),ConfigOther.ActidCriticalgfx);
    _pc.sendPackets(polyactiddmg);
    }

    if (dmg <= 0.0D) {
      this._isHit = false;
      this._drainHp = 0;
    } 
    return (int)dmg;
  }

  private void dk_dmgUp() {
    if (this._pc.isDragonKnight() && this._weaponType2 == 18) {// 鎖鍊劍
      long h_time = Calendar.getInstance().getTimeInMillis() / 1000L;// 換算為秒
      int random = _random.nextInt(100);
      int weaponchance = ConfigSkill.VULNERABILITY_ROM;// 弱點曝光機率
      if (this._weapon.getItemId() == 410189) {// 殲滅者鎖鏈劍
        weaponchance += ConfigSkill.VULNERABILITY_OTHER;
      }
      
      if (this._pc.get_tmp_targetid() != this._targetId) {
        this._pc.set_weaknss(0, 0L);
        this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(0));
      } 
      
      this._pc.set_tmp_targetid(this._targetId);

      
      switch (this._pc.get_weaknss()) {
        case 0:
          if (random < weaponchance) {
            this._pc.set_weaknss(1, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(1));
this._damage += ConfigSkill.VULNERABILITY_1;
          } 
          break;
        case 1:
          if (random < weaponchance) {
            this._pc.set_weaknss(1, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(1));
this._damage += ConfigSkill.VULNERABILITY_1;
          }  else if (random >= weaponchance && random < weaponchance * 2) {
            this._pc.set_weaknss(2, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(2));
this._damage += ConfigSkill.VULNERABILITY_2;
          } 
          break;
        case 2:
          if (random < weaponchance) {
            this._pc.set_weaknss(2, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(2));
this._damage += ConfigSkill.VULNERABILITY_2;
          }  if (random >= weaponchance && random < weaponchance * 2) {
            this._pc.set_weaknss(3, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(3));
this._damage += ConfigSkill.VULNERABILITY_3;
          } 
          break;
        case 3:
          if (random < weaponchance) {
            this._pc.set_weaknss(3, h_time);
            this._pc.sendPackets((ServerBasePacket)new S_PacketBoxDk(3));
this._damage += ConfigSkill.VULNERABILITY_3;
          } 
          break;
      } 
    } 
return;
  }

/**
 * 料裡增傷
 * 
 * @return
 */
private double dmgUp() {
	double dmg = 0.0D;

	if (_pc.getSkillEffect().size() <= 0) {
		return dmg;
	}

	if (!_pc.getSkillisEmpty()) {
		try {
			// 料理追加傷害(近距離武器)
			if ((_weaponType != 20) && (_weaponType != 62)) {
				for (final Integer key : _pc.getSkillEffect()) {
					final Integer integer = L1AttackList.SKD1.get(key);
					if (integer != null) {
						dmg += integer;
					}
				}

				// 料理追加傷害(遠距離武器)
			} else {
				for (final Integer key : _pc.getSkillEffect()) {
					final Integer integer = L1AttackList.SKD2.get(key);
					if (integer != null) {
						dmg += integer;
					}
				}
			}

		} catch (ConcurrentModificationException localConcurrentModificationException) {
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	return dmg;
}

/**
 * 魔法武器傷害計算
 * 
 * @param pcInstance
 * @param character
 * @param weaponTotalDamage
 * @return
 */
private double weaponSkill(L1PcInstance pcInstance, L1Character character, double weaponTotalDamage) {
	double dmg = 0.0D;
	dmg = WeaponSkillStart.start_weapon_skill(pcInstance, character, _weapon, weaponTotalDamage);
	if (dmg != 0.0D) {
		return dmg;
	}
	switch (_weaponId) {
	case 124://舊版巴風特魔杖
		dmg = L1WeaponSkill.getBaphometStaffDamage(_pc, _target);
		break;
	case 204://深紅之弩
	case 86://20161201束縛術加入紅影
	case 100204://深紅之弩
		L1WeaponSkill.giveFettersEffect(_pc, _target);
		break;
	case 261://舊版大法師魔仗
		L1WeaponSkill.giveArkMageDiseaseEffect(_pc, _target);
		break;
	case 410131://天使魔杖
		L1WeaponSkill.giveTurn_Undead(_pc, _target);
		break;
	case 260://舊版狂風之斧
	case 263://舊版酷寒之矛
		dmg = L1WeaponSkill.getAreaSkillWeaponDamage(_pc, _target, _weaponId);
		break;
	case 264://舊版雷雨之劍
		dmg = L1WeaponSkill.getLightningEdgeDamage(_pc, _target);
		break;
	default:
		dmg = L1WeaponSkill.getWeaponSkillDamage(_pc, _target, _weaponId);
	}
	return dmg;
}

/**
 * 近戰武器輔助魔法增傷
 * 
 * @param dmg
 * @return
 */
private double calcBuffDamage(double dmg) {
	if (_weaponType == 20) {// 弓
		return dmg;
	}
	if (_weaponType == 62) {// 鐵手甲
		return dmg;
	}
	if (_weaponType2 == 17) {// 奇古獸
		return dmg;
	}

	if (_pc.hasSkillEffect(FIRE_WEAPON)) {
		dmg += 4.0D;
	}

	/*
	 * if (_pc.hasSkillEffect(155)) { dmg += 5.0D; }
	 */

	if (_pc.hasSkillEffect(BURNING_WEAPON)) {
		dmg += 6.0D;
	}
    
	if (_pc.hasSkillEffect(BERSERKERS)) {
		dmg += 5.0D;
	}

	if (_pc.hasSkillEffect(BURNING_SLASH)) { // 燃燒擊砍
		dmg += 10.0D;
		_pc.sendPacketsX10(new S_EffectLocation(_targetX, _targetY, 6591));
		_pc.killSkillEffectTimer(BURNING_SLASH);
	}
    return dmg;
  }

/**
 * 祝福武器 銀/米索莉/奧里哈魯根材質武器<BR>
 * 其他屬性定義
 * 
 * @return
 */
private int calcMaterialBlessDmg() {
	int damage = 0;
	if (_pc.getWeapon() != null) {
		final int undead = _targetNpc.getNpcTemplate().get_undead();
		switch (undead) {
		case 1:// 不死系
		case 3:// 殭屍系
		case 4:// 不死系(治療系無傷害/無法使用起死回生)
			if ((_weaponMaterial == 14) || (_weaponMaterial == 17) || (_weaponMaterial == 22)) {// 銀/米索莉/奧里哈魯根
				damage += _random.nextInt(20) + 1;
			}
			if (_weaponBless == 0) { // 祝福武器
				damage += _random.nextInt(4) + 1;
			}
			switch (_weaponType) {
			case 20:
			case 62:
				break;
			default:
				if (_weapon.getHolyDmgByMagic() != 0) {
					damage += _weapon.getHolyDmgByMagic();// 武器強化魔法
				}
				break;
			}
			break;
		case 2:// 惡魔系
			if ((_weaponMaterial == 17) || (_weaponMaterial == 22)) {// 米索莉/奧里哈魯根
				damage += _random.nextInt(3) + 1;
			}
			if (_weaponBless == 0) { // 祝福武器
				damage += _random.nextInt(4) + 1;
			}
			break;
		case 5:// 狼人系
			if ((_weaponMaterial == 14) || (_weaponMaterial == 17) || (_weaponMaterial == 22)) {// 銀/米索莉/奧里哈魯根
				damage += _random.nextInt(20) + 1;
			}
			break;
		}
	}
	return damage;
}

  private void soulHp() {
    switch (this._calcType) {
      case 1:
        if (this._pc.isSoulHp() > 0) {
          ArrayList<Integer> soulHp = new ArrayList<>();
          soulHp = this._pc.get_soulHp();
          int r = ((Integer)soulHp.get(0)).intValue();
          int min = ((Integer)soulHp.get(1)).intValue();
          int max = ((Integer)soulHp.get(2)).intValue();
          if (_random.nextInt(100) < r) {
            if (this._pc.isSoulHp() == 1) {
              this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 11673));
            } else {
              this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 11677));
            } 
            
            int hpadd = _random.nextInt(max - min) + min;
            int newHp = this._pc.getCurrentHp() + hpadd;
            if (newHp >= this._pc.getMaxHp()) {
              this._pc.setCurrentHp(this._pc.getMaxHp());
            } else {
              this._pc.setCurrentHp(newHp);
            } 
            
            this._targetPc.receiveDamage((L1Character)this._pc, hpadd, false, true);
          } 
        } 
        break;

      
      case 2:
        if (this._pc.isSoulHp() > 0 && this._targetNpc instanceof L1MonsterInstance) {
          ArrayList<Integer> soulHp = new ArrayList<>();
          soulHp = this._pc.get_soulHp();
          int r = ((Integer)soulHp.get(0)).intValue();
          int min = ((Integer)soulHp.get(1)).intValue();
          int max = ((Integer)soulHp.get(2)).intValue();
          
          if (_random.nextInt(100) < r) {
            if (this._pc.isSoulHp() == 1) {
              
              this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 11673));
            } else {
              this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 11677));
            } 
            int hpadd = _random.nextInt(max - min) + min;
            int newHp = this._pc.getCurrentHp() + hpadd;
            if (newHp >= this._pc.getMaxHp()) {
              this._pc.setCurrentHp(this._pc.getMaxHp());
            } else {
              this._pc.setCurrentHp(newHp);
            } 
            this._targetNpc.receiveDamage((L1Character)this._pc, hpadd);
          } 
        } 
        break;
    } 
  }

  private void AttrAmuletEffect() {
    int rnd = this._pc.get_AttrAmulet_rnd();
    int dmg = this._pc.get_AttrAmulet_dmg();
    int gfxid = this._pc.get_AttrAmulet_gfxid();
    
    if (rnd <= 0) {
      return;
    }
    
    switch (this._calcType) {
      case 1:
        if (_random.nextInt(1000) < rnd) {
          if (this._targetPc.hasSkillEffect(68)) {
            dmg = (int)(dmg / ConfigOther.IMMUNE_TO_HARM);
          }
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), gfxid));
          this._targetPc.receiveDamage((L1Character)this._pc, dmg, false, true);
        } 
        break;
      case 2:
        if (_random.nextInt(1000) < rnd) {
          if (this._targetNpc.hasSkillEffect(68)) {
            dmg = (int)(dmg / ConfigOther.IMMUNE_TO_HARM);
          }
          this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), gfxid));
          this._targetNpc.receiveDamage((L1Character)this._pc, dmg);
        } 
        break;
    } 
  }
  private int calcAttrEnchantDmg() {
    int damage = 0;

    if (this._weaponType == 20 && this._weaponId != 190 && this._arrow == null && 
      this._arrow.getItemId() >= 84077 && this._arrow.getItemId() >= 84080) {
      damage += 3;
    }
    if (this._weaponType == 20 && this._weaponId != 190) {
      if (this._arrow.getItemId() == 84080) {
        this._arrowGfxid = 4579;
      }
      
      if (this._arrow.getItemId() == 84077) {
        this._arrowGfxid = 7191;
      }
      
      if (this._arrow.getItemId() == 84079) {
        this._arrowGfxid = 2546;
      }

      if (this._arrow.getItemId() == 84078) {
        this._arrowGfxid = 4432;
      }
    } 

    
    int resist = 0;
    switch (this._calcType) {
      case 1:
        switch (this._weaponAttrEnchantKind) {
          case 1:
            resist = this._targetPc.getEarth();
            break;
          case 2:
            resist = this._targetPc.getFire();
            break;
          case 4:
            resist = this._targetPc.getWater();
            break;
          case 8:
            resist = this._targetPc.getWind();
            break;
        } 
        break;
      case 2:
        switch (this._weaponAttrEnchantKind) {
          case 1:
            resist = this._targetNpc.getEarth();
            break;
          case 2:
            resist = this._targetNpc.getFire();
            break;
          case 4:
            resist = this._targetNpc.getWater();
            break;
          case 8:
            resist = this._targetNpc.getWind();
            break;
        } 
        break;
    } 
    int resistFloor = (int)(0.16D * Math.abs(resist));
    
    if (resist < 0) {
      resistFloor *= -1;
    }
    
    double attrDeffence = resistFloor / 32.0D;
    
    double attrCoefficient = 1.0D - attrDeffence;
    
    damage = (int)(damage * attrCoefficient);

    return damage;
  }

  private void addPcPoisonAttack(L1Character target) {
    boolean isCheck = false;
    boolean isCheck1 = false;
    switch (this._weaponId) {
      case 0:
        break;
        
      case 13:
      case 14:
        isCheck = true;
        break;
      
      default:
        if (this._pc.hasSkillEffect(98)) {
          isCheck = true;
        }
        if (this._pc.hasSkillEffect(8864)) {
          isCheck1 = true;
        }
        break;
    } 
    
    int hp = 0;
    if (this._pc.isDarkelf() && this._pc.getlogpcpower_SkillFor2() != 0 && 
      this._pc.getlogpcpower_SkillFor2() >= 1) {
      hp += this._pc.getlogpcpower_SkillFor2() * 15;
    }
    
    if (isCheck1) {
      L1DamagePoison.doInfection((L1Character)this._pc, target, 1000, 50);
    }
    if (isCheck) {
      int chance = _random.nextInt(100) + 1;
      if (chance <= 10)
      {
        L1DamagePoison.doInfection((L1Character)this._pc, target, 3000, 5 + hp);
      }
    } 
  }

  /**
	 * 屬火、燃鬥、勇猛意志增傷計算
	 * 
	 * @param dmg
	 */
	private double BuffDmgUp(double dmg) {
		int random = _random.nextInt(100) + 1;

		/*if ((_pc.hasSkillEffect(ELEMENTAL_FIRE)) && // 屬性之火
				(random <= 33)) {
			dmg *= 1.5D;

		} */
		if ((_pc.hasSkillEffect(ELEMENTAL_FIRE)) && // 屬性之火
				(random <= ConfigSkill.ELEMENTAL_FIRE_RND) && _weaponType != 20 && _weaponType != 62) {
			dmg *= ConfigSkill.ELEMENTAL_FIRE;
		}
		else if ((_pc.hasSkillEffect(BURNING_SPIRIT)) && // 燃燒鬥志
				(random <= ConfigSkill.BURNING_CHANCE)) {
			dmg *= ConfigSkill.BURNING_DMG;

		}
		/*else if ((_pc.hasSkillEffect(BURNING_SPIRIT)) && // 燃燒鬥志
				(random <= 33)) {
			dmg *= 1.5D;

		} */else if ((_pc.hasSkillEffect(BRAVE_AURA)) && // 勇猛意志
				(random <= 33)) {
			dmg *= 1.5D;
		}

		return dmg;
	}
  
  
/**
 * 攻擊動作送出
 */
@Override
public void action() {
	try {
		if (_pc == null) {
			return;
		}
		if (_target == null) {
			return;
		}
		// 改變面向
		_pc.setHeading(_pc.targetDirection(_targetX, _targetY));

		if (_weaponRange == -1) {// 遠距離武器
			actionX1();

		} else {// 近距離武器
			actionX2();
		}

	if (ConfigOther.dmgspr && this._pc.hasSkillEffect(1688) && !this._pc.isActived()){
//	//加入傷害顯示優化寫法
//		int i = (int) ((_damage / Math.pow(10, 0)) % 10) + ConfigOther.Attack_1;
//		int k = (int) ((_damage / Math.pow(10, 1)) % 10) + ConfigOther.Attack_2;
//		int h = (int) ((_damage / Math.pow(10, 2)) % 10) + ConfigOther.Attack_3;
//		int s = (int) ((_damage / Math.pow(10, 3)) % 10) + ConfigOther.Attack_4;
//		int m = (int) ((_damage / Math.pow(10, 4)) % 10) + ConfigOther.Attack_5;
//
//		if (_damage <= 0) {
//			this._pc.sendPacketsAll(new S_SkillSound(this._target.getId(),
//					13418));// Miss
//		} else if (_damage > 0 && _damage < 10) {
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					i));// 個位數
//		} else if (_damage >= 10 && _damage < 100) {
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					k));// 十位數
//		} else if (_damage >= 100 && _damage < 1000) {
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					h));// 百位數
//		} else if (_damage >= 1000 && _damage < 10000) {
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					h));// 百位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					s));// 千位數
//		} else if (_damage >= 10000) {
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					h));// 百位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					s));// 千位數
//			this._pc.sendPackets(new S_SkillSound(this._target.getId(),
//					m));// 萬位數
//			}
		
		//寫法2
		int Greatgfx = ConfigOther.Great;
		int Criticalgfx = ConfigOther.Critical;
		
		int weaponTotalD = _weaponDamage + _weaponAddDmg + _weaponEnchant;
		int weaponmaxLD = _weaponAddDmg + _weaponEnchant + _weaponLarge;
		int weaponmaxSD = _weaponAddDmg + _weaponEnchant + _weaponSmall;
		int weaponTotalD2 = weaponTotalD / 2;
		
		// 计算进行四舍五入
		int weaponmaxLD2 = Math.round(weaponmaxLD / 2);
		int weaponmaxSD2 = Math.round(weaponmaxSD / 2);
		
		
		// 检查伤害和音效条件
		if (_damage > 0 && Greatgfx > 0 && Criticalgfx > 0) {
		    if (weaponTotalD2 > 0 && (weaponTotalD2 >= weaponmaxLD2 || weaponTotalD2 >= weaponmaxSD2) && (weaponTotalD != weaponmaxLD || weaponTotalD != weaponmaxSD)) {
		        final S_SkillSound Great = new S_SkillSound(_pc.getId(), Greatgfx);
		        _pc.sendPackets(Great);
		        
		    } else if (weaponTotalD == weaponmaxLD || weaponTotalD == weaponmaxSD) {
		        final S_SkillSound Critical = new S_SkillSound(_pc.getId(), Criticalgfx);
		        _pc.sendPackets(Critical);
		    }
		    
		    
//	        System.out.println("weaponD : " + _weaponDamage);
//	        System.out.println("weaponTotalD2 : " + weaponTotalD2);
//	        System.out.println("weaponmaxLD2 : " + weaponmaxLD2);
//	        System.out.println("weaponmaxSD2 : " + weaponmaxSD2);
//	        System.out.println("weaponTotalD : " + weaponTotalD);
//	        System.out.println("weaponmaxLD : " + weaponmaxLD);
//	        System.out.println("weaponmaxSD : " + weaponmaxSD);
	        
		}
		
			int units = _damage % 10;
			int tens = (_damage / 10) % 10;
			int hundreads = (_damage / 100) % 10;
			int thousands = (_damage / 1000) % 10;
			int tenthousands = (_damage / 10000) % 10;
		
			if ((units > 0) || (tens > 0) || (hundreads > 0)
				|| (thousands > 0) || (tenthousands > 0)) {
				units += ConfigOther.Attack_1;
				final S_SkillSound units_s = new S_SkillSound(
					_target.getId(), units);
				_pc.sendPackets(units_s);
			}
			if ((tens > 0) || (hundreads > 0) || (thousands > 0)
				|| (tenthousands > 0)) {
				tens += ConfigOther.Attack_2;
				final S_SkillSound tens_s = new S_SkillSound(
					_target.getId(), tens);
				_pc.sendPackets(tens_s);
			}
			if ((hundreads > 0) || (thousands > 0) || (tenthousands > 0)) {
				hundreads += ConfigOther.Attack_3;
				final S_SkillSound hundreads_s = new S_SkillSound(
					_target.getId(), hundreads);
				_pc.sendPackets(hundreads_s);
			}
			if ((thousands > 0) || (tenthousands > 0)) {
				thousands += ConfigOther.Attack_4;
				final S_SkillSound thousands_s = new S_SkillSound(
					_target.getId(), thousands);
				_pc.sendPackets(thousands_s);
			}
			if (tenthousands > 0) {
				tenthousands += ConfigOther.Attack_5;
				final S_SkillSound tenthousands_s = new S_SkillSound(
					_target.getId(), tenthousands);
				_pc.sendPackets(tenthousands_s);
			}
			if (_damage <= 0) {
				final S_SkillSound miss = new S_SkillSound(_target.getId(), ConfigOther.Attack_Miss);
				_pc.sendPackets(miss);
			}
			//寫法2
		}
	} catch (final Exception e) {
		_log.error(e.getLocalizedMessage(), e);
	}
}
/**
 * 近距離武器/空手
 */
private void actionX2() {
	try {
		if (_isHit) {// 命中
			_pc.sendPacketsAll(new S_AttackPacketPc(_pc, _target, _attackType, _damage));

		} else if (_targetId > 0) {// 未命中但是具有目標
			_pc.sendPacketsAll(new S_AttackPacketPc(_pc, _target));

		} else {// 空攻擊
			_pc.sendPacketsAll(new S_AttackPacketPc(_pc));
		}
	} catch (Exception e) {
		_log.error(e.getLocalizedMessage(), e);
	}
}

private void Imperius_Tshirt_Effect() {
	int rnd = _pc.get_Imperius_Tshirt_rnd();
	int min = _pc.get_Tshirt_drainingHP_min();
	int max = _pc.get_Tshirt_drainingHP_max();

	if (rnd <= 0) {// 機率0以下
		return;
	}

	int value = 0;
	switch (_calcType) {
	case PC_PC:
		if (_random.nextInt(1000) < rnd) {
			value = _random.nextInt(max - min + 1) + min;
			_targetPc.sendPacketsAll(new S_SkillSound(_targetPc.getId(), 11769));
			_targetPc.receiveDamage(_pc, value, false, true);
			short newHp = (short) (_pc.getCurrentHp() + value);
			_pc.setCurrentHp(newHp);
		}
		break;
	case PC_NPC:
		if (_random.nextInt(1000) < rnd) {
			value = _random.nextInt(max - min + 1) + min;
			_targetNpc.broadcastPacketAll(new S_SkillSound(_targetNpc.getId(), 11769));
			_targetNpc.receiveDamage(_pc, value);
			short newHp = (short) (_pc.getCurrentHp() + value);
			_pc.setCurrentHp(newHp);
		}
		break;
	}
}
  private void actionX1() {
    try {
      if (this._pc.getpolyarrow() > 0) {
        this._arrowGfxid = this._pc.getpolyarrow();
      }
      
      if (this._pc.getTempCharGfx() >= 13715 && this._pc.getTempCharGfx() <= 13745) {
        this._arrowGfxid = 11762;
        this._stingGfxid = 11762;
      } 
      
      if (this._isHit) {
        switch (this._weaponType) {
          case 20:
            if (this._arrow != null) {
              this._pc.sendPacketsAll(
                  (ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._targetId, this._arrowGfxid, this._targetX, this._targetY, this._damage));
              this._pc.getInventory().removeItem(this._arrow, 1L);
              break;
            } 
            if (this._weaponId == 190) {
              this._pc.sendPacketsAll(
                  (ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._targetId, 2349, this._targetX, this._targetY, this._damage));
            }
            break;
          
          case 62:
            if (this._sting != null) {
              this._pc.sendPacketsAll(
                  (ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._targetId, this._stingGfxid, this._targetX, this._targetY, this._damage));
              
              this._pc.getInventory().removeItem(this._sting, 1L);
            } 
            break;
        } 
      
      } else if (this._targetId > 0) {
        switch (this._weaponType) {
          case 20:
            if (this._arrow != null) {
              this._pc.sendPacketsAll((ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._arrowGfxid, this._targetX, this._targetY, 1));
              this._pc.getInventory().removeItem(this._arrow, 1L);
              break;
            } 
            if (this._weaponId == 190) {
              this._pc.sendPacketsAll(
                  (ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._targetId, 2349, this._targetX, this._targetY, this._damage));
              break;
            } 
            this._pc.sendPacketsAll((ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc));
            break;

          
          case 62:
            if (this._sting != null) {
              this._pc.sendPacketsAll((ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc, this._stingGfxid, this._targetX, this._targetY, 1));
              this._pc.getInventory().removeItem(this._sting, 1L);
              break;
            } 
            this._pc.sendPacketsAll((ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc));
            break;
        } 

      
      } else {
        this._pc.sendPacketsAll((ServerBasePacket)new S_UseArrowSkill((L1Character)this._pc));
      }
    
    } catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
  }



  
  public void commit() {
    if (this._isHit) {
      if (this._pc.dice_hp() != 0 && 
        _random.nextInt(100) + 1 <= this._pc.dice_hp()) {
        this._drainHp = this._pc.sucking_hp();
      }
      
      if (this._pc.dice_mp() != 0 && 
        _random.nextInt(100) + 1 <= this._pc.dice_mp()) {
        this._drainMana = this._pc.sucking_mp();
      }
      
      if (this._pc.has_powerid(6610)) {
        int rad = 3;
        int time = 5;
        if (_random.nextInt(100) < rad && !this._pc.hasSkillEffect(998)) {
          this._pc.setSkillEffect(998, time * 1000);
          this._pc.sendPacketsAll((ServerBasePacket)new S_Liquor(this._pc.getId(), 8));
          this._pc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._pc.getId(), 11817));
        } 
      } 
      
//      int i = (int)(this._damage / Math.pow(10.0D, 0.0D) % 10.0D) + ConfigOther.Attack_1;
//      int k = (int)(this._damage / Math.pow(10.0D, 1.0D) % 10.0D) + ConfigOther.Attack_2;
//      int h = (int)(this._damage / Math.pow(10.0D, 2.0D) % 10.0D) + ConfigOther.Attack_3;
//      int s = (int)(this._damage / Math.pow(10.0D, 3.0D) % 10.0D) + ConfigOther.Attack_4;
//      int m = (int)(this._damage / Math.pow(10.0D, 4.0D) % 10.0D) + ConfigOther.Attack_5;
      
      switch (this._calcType) {
        case PC_PC:
          if (this._pc.lift() != 0) {
            int counter = _random.nextInt(this._pc.lift()) + 1;
            StringBuffer sbr = new StringBuffer();
            Iterator<L1ItemInstance> iterator2 = this._targetPc.getInventory().getItems().iterator();
            while (iterator2.hasNext()) {
              
              L1ItemInstance item = iterator2.next();
              if (item.getItem().getType2() != 2 || !item.isEquipped())
                continue; 
              this._targetPc.getInventory().setEquipped(item, false, false, false);
              sbr.append("[").append(item.getNumberedName(1L)).append("]");
              if (--counter <= 0)
                break; 
            } 
            if (sbr.length() > 0) {
              this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("以下裝備被對方卸除:" + sbr.toString()));
              this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("成功卸除對方以下裝備:" + sbr.toString()));
            } 
          } 

          
          commitPc();
pc_and_pc();
//       if (this._pc.hasSkillEffect(1688) && ConfigOther.dmgspr) { //要關閉內掛顯示傷害加入這段 && !this._pc.isActived()
//            if (this._damage > 0 && this._damage < 10) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i)); break;
//            }  if (this._damage >= 10 && this._damage < 100) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k)); break;
//            }  if (this._damage >= 100 && this._damage < 1000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h)); break;
//            }  if (this._damage >= 1000 && this._damage < 10000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), s)); break;
//            }  if (this._damage >= 10000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), s));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), m));
//            } 
//          } 
          break;
        
        case PC_NPC:
		commitNpc();
pc_and_npc();
//     if (this._pc.hasSkillEffect(1688) && ConfigOther.dmgspr) {  //要關閉內掛顯示傷害加入這段 && !this._pc.isActived()
//            
//            if (this._damage > 0 && this._damage < 10) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//            } else if (this._damage >= 10 && this._damage < 100) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//            }
//            else if (this._damage >= 100 && this._damage < 1000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h));
//            }
//            else if (this._damage >= 1000 && this._damage < 10000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), s));
//            }
//            else if (this._damage >= 10000) {
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), s));
//              this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), m));
//            } 
//          }
          break;
      }
    }
//else {//未命中
///*使用三重矢返回*/if (this._pc.isTripleArrow()) return;
//	if (this._damage <= 0) {
//	this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 13418));//MISS特效編號   
//	}
//}
    if (!ConfigAlt.ALT_ATKMSG) {//傷害資訊顯示關閉   
      return;
    }
    switch (this._calcType) {//傷害資訊顯示開啟//20161128
      case PC_PC:
        if (!this._pc.isGm() && !this._targetPc.isGm()) {
          return;
        }
        break;
      case PC_NPC:
        if (!this._pc.isGm()) {
          return;
        }
        break;
    } 
    
    String srcatk = this._pc.getName();
    String tgatk = "";
    String hitinfo = "";
    String dmginfo = "";
    String x = "";
    
    switch (this._calcType) {
      case PC_PC:
        tgatk = this._targetPc.getName();
        hitinfo = "命中機率:" + this._hitRate + "% 剩餘hp:" + this._targetPc.getCurrentHp();
        dmginfo = this._isHit ? ("傷害:" + this._damage + " ") : "未命中 ";
        x = String.valueOf(srcatk) + ">" + tgatk + " " + dmginfo + hitinfo;
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, "對PC送出攻擊: " + x));
        }
        if (this._targetPc.isGm()) {
          this._targetPc.sendPackets((ServerBasePacket)new S_ServerMessage(166, "受到PC攻擊: " + x));
        }
        break;
      case PC_NPC:
        tgatk = this._targetNpc.getName();
        hitinfo = "命中機率:" + this.hit_rnd + "% 剩餘hp:" + this._targetNpc.getCurrentHp();
        dmginfo = this._isHit ? ("傷害:" + this._damage + " ") : "未命中 ";
        x = String.valueOf(srcatk) + ">" + tgatk + " " + dmginfo + hitinfo;
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, "對NPC送出攻擊: " + x));
        }
        break;
    } 
  }



/**
 * 傷害顯示算出 pc_adn_pc
 */
private void pc_and_pc() {
	String dmginfo = "";// 傷害
	dmginfo = this._isHit ? " " + this._damage : " miss";
	if (this._pc.hasSkillEffect(20008)) {
		// 166 \f1%0%s %4%1%3 %2。
		this._pc.sendPackets(new S_TrueTarget(this._targetPc.getId(),
				this._pc.getId(), "(" + dmginfo + " )"));
	}
}

/**
 * 傷害顯示算出 pc_and_npc
 */
private void pc_and_npc() {
	String dmginfo = "";// 傷害
	dmginfo = this._isHit ? " " + this._damage : " miss";
	if (this._pc.hasSkillEffect(20008)) {
		// 166 \f1%0%s %4%1%3 %2。
		this._pc.sendPackets(new S_TrueTarget(this._targetNpc.getId(),
				this._pc.getId(), "(" + dmginfo + " )"));
	}
}


/**
 * 對PC的傷害資訊送出
 */
  private void commitPc() {
    if (this._drainMana > 0 && this._targetPc.getCurrentMp() > 0) {
      if (this._drainMana > this._targetPc.getCurrentMp()) {
        this._drainMana = this._targetPc.getCurrentMp();
      }
      this._targetPc.receiveManaDamage((L1Character)this._pc, this._drainMana);
      
      int newMp = this._pc.getCurrentMp() + this._drainMana;
      this._pc.setCurrentMp(newMp);
    } 
    
    if (this._drainHp > 0) {
      short newHp = (short)(this._pc.getCurrentHp() + this._drainHp);
      this._pc.setCurrentHp(newHp);
    } 

    // damagePcWeaponDurability();
    this._targetPc.receiveDamage((L1Character)this._pc, this._damage, false, false);
  }


  /**
	 * 對NPC的傷害資訊送出
	 */
  private void commitNpc() {
    if (this._drainMana > 0) {
      int drainValue = this._targetNpc.drainMana(this._drainMana);
      if (drainValue > 0) {
        this._targetNpc.ReceiveManaDamage((L1Character)this._pc, drainValue);
        
        int newMp = this._pc.getCurrentMp() + drainValue;
        this._pc.setCurrentMp(newMp);
      } 
    } 
    
    if (this._drainHp > 0) {
      short newHp = (short)(this._pc.getCurrentHp() + this._drainHp);
      this._pc.setCurrentHp(newHp);
    } 
    
    damageNpcWeaponDurability();
    this._targetNpc.receiveDamage((L1Character)this._pc, this._damage);
  }

  
  public boolean isShortDistance() {
    boolean isShortDistance = true;
    if (this._weaponType == 20 || this._weaponType == 62) {
      isShortDistance = false;
    }
    return isShortDistance;
  }



  
  private void MoonAmuletEffect() {
    int rnd = this._pc.get_MoonAmulet_rnd();
    int dmgmin = this._pc.get_MoonAmulet_dmg_min();
    int dmgmax = this._pc.get_MoonAmulet_dmg_max();
    int gfxid = this._pc.get_MoonAmulet_gfxid();
    
    if (rnd <= 0) {
      return;
    }
    
    int damage = 0;
    switch (this._calcType) {
      case 1:
        if (_random.nextInt(1000) < rnd) {
          damage = _random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
          if (this._targetPc.hasSkillEffect(68)) {
            damage = (int)(damage / ConfigOther.IMMUNE_TO_HARM);
          }
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), gfxid));
          this._targetPc.receiveDamage((L1Character)this._pc, damage, false, true);
        } 
        break;
      case 2:
        if (_random.nextInt(1000) < rnd) {
          damage = _random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
          if (this._targetNpc.hasSkillEffect(68)) {
            damage = (int)(damage / ConfigOther.IMMUNE_TO_HARM);
          }
          this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), gfxid));
          this._targetNpc.receiveDamage((L1Character)this._pc, damage);
        } 
        break;
    } 
  }





  
  public void commitCounterBarrier() {
    int damage = calcCounterBarrierDamage();
    if (damage == 0) {
      return;
    }
    
    if (this._pc.getId() == this._target.getId()) {
      return;
    }
    
    if (this._pc.hasSkillEffect(68)) {
      damage = (int)(damage / ConfigOther.IMMUNE_TO_HARM);
    }
    
    this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), 2));
    this._pc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._target.getId(), 10710));
    this._pc.receiveDamage(this._target, damage, false, true);
  }





  
  private void damageNpcWeaponDurability() {
    if (this._calcType != 2) {
      return;
    }
    
    if (!this._targetNpc.getNpcTemplate().is_hard()) {
      return;
    }
    
    if (this._weaponType == 0) {
      return;
    }
    
    if (this._weapon.getItem().get_canbedmg() == 0) {
      return;
    }
if (this._weapon.getItem().getBless() == 0) {
       return;
     }
    
    if (this._pc.hasSkillEffect(175)) {
      return;
    }
    if (this._pc.getInventory().checkSkillTypebless(0) && this._weaponBless == 1) {
      return;
    }

    
    int random = _random.nextInt(100) + 1;
    switch (this._weaponBless) {
      case 0:
        if (random < 3) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(268, this._weapon.getLogName()));
          this._pc.getInventory().receiveDamage(this._weapon);
          this._pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(this._pc.getId(), 10712));
        } 
        break;
      case 1:
      case 2:
        if (random < 10) {
          
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(268, this._weapon.getLogName()));
          this._pc.getInventory().receiveDamage(this._weapon);
          this._pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(this._pc.getId(), 10712));
        } 
        break;
    } 
  }
  
  private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
    int castleId = L1CastleLocation.getCastleIdByArea((L1Character)pc);
    if (castleId != 0 && 
      ServerWarExecutor.get().isNowWar(castleId)) {
      return true;
    }
    
    return false;
  }
  public void calcStaffOfMana() {
    int som_lvl;
    switch (this._weaponId) {
      case 126:
      case 127:
      case 1012:
      case 1065:
        som_lvl = this._weaponEnchant + 3;
        if (som_lvl < 0) {
          som_lvl = 0;
        }
        
        this._drainMana = Math.min(_random.nextInt(som_lvl) + 1, 9);
        break;
      
      case 259:
        switch (this._calcType) {
          case 1:
            if (this._targetPc.getMr() <= _random.nextInt(100) + 1) {
              this._drainMana = 1;
            }
            break;
          
          case 2:
            if (this._targetNpc.getMr() <= _random.nextInt(100) + 1)
              this._drainMana = 1; 
            break;
        } 
        break;
    }
  }
  
	/**
	 * 攻擊PC時武器受損
	 */
	@SuppressWarnings("unused")
	private void damagePcWeaponDurability() {
		if (_calcType != PC_PC) {
			return;
		}

		if (_weaponType == 0) {
			return;
		}

		if (_weaponType == 20) {
			return;
		}

		if (_weaponType == 62) {
			return;
		}

		if (!_targetPc.hasSkillEffect(89)) {
			return;
		}

		if (_pc.hasSkillEffect(175)) {
			return;
		}

		if (_random.nextInt(100) + 1 <= 10) {
			_pc.sendPackets(new S_ServerMessage(268, _weapon.getLogName()));
			_pc.getInventory().receiveDamage(_weapon);
			_pc.sendPacketsX8(new S_SkillSound(_pc.getId(), 10712));
		}
	}
}
