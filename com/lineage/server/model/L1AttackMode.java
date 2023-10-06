// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model;

import com.lineage.server.model.Instance.L1DollInstance;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

public abstract class L1AttackMode
{
    private static final Log _log;
    protected L1Character _target;
    protected L1PcInstance _pc;
    protected L1PcInstance _targetPc;
    protected L1NpcInstance _npc;
    protected L1NpcInstance _targetNpc;
    protected int _targetId;
    protected int _targetX;
    protected int _targetY;
    protected int _statusDamage;
    protected double _statusDamage1;
    protected int _hitRate;
    protected int _calcType;
    protected static final int PC_PC = 1;
    protected static final int PC_NPC = 2;
    protected static final int NPC_PC = 3;
    protected static final int NPC_NPC = 4;
    protected boolean _isHit;
    protected int _damage;
    protected int hppotion;
    protected int _drainMana;
    protected int _drainHp;
    protected int _attckGrfxId;
    protected int _attckActId;
    protected L1ItemInstance _weapon;
    protected int _weaponId;
    protected int _weaponType;
    protected int _weaponType2;
    protected int _weaponAddHit;
    protected int _weaponAddDmg;
    protected int _weaponSmall;
    protected int _weaponLarge;
    protected int _weaponRange;
    protected int _weaponBless;
    protected int _weaponEnchant;
    protected int _weaponMaterial;
    protected int _weaponDoubleDmgChance;
    protected int _weaponAttrEnchantKind;
    protected int _weaponAttrEnchantLevel;
    protected L1ItemInstance _arrow;
    protected int _arrowGfxid;
    protected L1ItemInstance _sting;
    protected int _stingGfxid;
    protected int _leverage;
    protected static final Random _random;
    
    static {
        _log = LogFactory.getLog((Class)L1AttackMode.class);
        _random = new Random();
    }
    
    public L1AttackMode() {
        this.hppotion = 0;
        this._weaponRange = 1;
        this._weaponBless = 1;
        this._arrowGfxid = 66;
        this._stingGfxid = 2989;
        this._leverage = 10;
    }
    
    protected static double getDamageUpByClan(final L1PcInstance pc) {
        double dmg = 0.0;
        try {
            if (pc == null) {
                return 0.0;
            }
            final L1Clan clan = pc.getClan();
            if (clan == null) {
                return 0.0;
            }
            if (clan.isClanskill() && pc.get_other().get_clanskill() == 1) {
                final int clanMan = clan.getOnlineClanMemberSize();
                dmg += 0.25 * clanMan;
            }
        }
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return dmg;
    }
    
    protected static double getDamageReductionByClan(final L1PcInstance targetPc) {
        double dmg = 0.0;
        try {
            if (targetPc == null) {
                return 0.0;
            }
            final L1Clan clan = targetPc.getClan();
            if (clan == null) {
                return 0.0;
            }
            if (clan.isClanskill() && targetPc.get_other().get_clanskill() == 2) {
                final int clanMan = clan.getOnlineClanMemberSize();
                dmg += 0.25 * clanMan;
            }
        }
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return dmg;
    }
    
    protected static boolean dmg0(final L1Character character) {
        try {
            if (character == null) {
                return false;
            }
            if (character.getSkillisEmpty()) {
                return false;
            }
            if (character.getSkillEffect().size() <= 0) {
                return false;
            }
            for (final Integer key : character.getSkillEffect()) {
                final Integer integer = L1AttackList.SKM0.get(key);
                if (integer != null) {
                    return true;
                }
            }
        }
        catch (ConcurrentModificationException ex) {}
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return false;
    }
    
    protected static int attackerDice(final L1Character character) {
        try {
            int attackerDice = 0;
            if (character.get_dodge() > 0) {
                attackerDice -= character.get_dodge();
            }
            if (character.get_dodge_down() > 0) {
                attackerDice += character.get_dodge_down();
            }
            return attackerDice;
        }
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return 0;
        }
    }
    
    public void setLeverage(final int i) {
        this._leverage = i;
    }
    
    protected int getLeverage() {
        return this._leverage;
    }
    
    public void setActId(final int actId) {
        this._attckActId = actId;
    }
    
    public void setGfxId(final int gfxId) {
        this._attckGrfxId = gfxId;
    }
    
    public int getActId() {
        return this._attckActId;
    }
    
    public int getGfxId() {
        return this._attckGrfxId;
    }
    
	/** 遠距離迴避率計算 亂數100 */
	protected boolean calcErEvasion() {
		int er = _targetPc.getEr();// 角色ER迴避率
		int rnd = _random.nextInt(3000) + 1;// 亂數1~100

		if (rnd > er) {
			return true;// 命中

		} else {
			return false;// 未命中
		}
	}
    
	/**
	 * 是否觸發完全閃避 亂數1000
	 */
	protected boolean calcEvasion() {
		if (_targetPc == null) {
			return false;
		}
		int ev = _targetPc.get_evasion();
		if (ev == 0) {
			return false;
		}
		int rnd = _random.nextInt(1000) + 1;
		if (rnd <= ev) {
			if (!_targetPc.getDolls().isEmpty()) {
				for (L1DollInstance doll : _targetPc.getDolls().values()) {
					doll.show_action(2);
				}
			}
			return true;
		}
		return false;
	}
    
	protected int calcPcDefense() {
		try {
			if (_targetPc != null) {
				int ac = Math.max(0, 10 - _targetPc.getAc());

				int acDefMax = _targetPc.getClassFeature().getAcDefenseMax(ac);
				if (acDefMax != 0) {
					int srcacd = Math.max(1, acDefMax >> 3);
					return _random.nextInt(acDefMax) + srcacd;
				}
			}

		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return 0;
	}
    
    protected int calcNpcDamageReduction() {
        final int damagereduction = this._targetNpc.getNpcTemplate().get_damagereduction();
        try {
            final int srcac = this._targetNpc.getAc();
            final int ac = Math.max(0, 10 - srcac);
            final int acDefMax = ac / 7;
            if (acDefMax != 0) {
                final int srcacd = Math.max(1, acDefMax);
                return L1AttackMode._random.nextInt(acDefMax) + srcacd + damagereduction;
            }
        }
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return damagereduction;
    }
    
    protected int calcCounterBarrierDamage() {
        int damage = 0;
        try {
            if (this._targetPc != null) {
                final L1ItemInstance weapon = this._targetPc.getWeapon();
                if (weapon != null && weapon.getItem().getType() == 3) {
                    damage = weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon.getItem().getDmgModifier() << 1;
                }
            }
            else if (this._targetNpc != null) {
                damage = this._targetNpc.getStr() + this._targetNpc.getLevel() << 1;
            }
        }
        catch (Exception e) {
            L1AttackMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return damage;
    }
    
    public abstract boolean calcHit();
    
    public abstract void action();
    
    public abstract int calcDamage();
    
    public abstract void commit();
    
    public abstract boolean isShortDistance();
    
    public abstract void commitCounterBarrier();
    
    public abstract void calcStaffOfMana();
}
