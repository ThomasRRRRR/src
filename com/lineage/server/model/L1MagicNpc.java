// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.utils.Random;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import org.apache.commons.logging.Log;

public class L1MagicNpc extends L1MagicMode
{
    private static final Log _log;
    L1ItemSpecialAttributeChar item_attr_char;
    protected L1ItemInstance _weapon;
    
    static {
        _log = LogFactory.getLog((Class)L1MagicNpc.class);
    }
    
    public L1MagicNpc(final L1NpcInstance attacker, final L1Character target) {
        this.item_attr_char = null;
        if (attacker == null) {
            return;
        }
        if (target instanceof L1PcInstance) {
            this._calcType = 3;
            this._npc = attacker;
            this._targetPc = (L1PcInstance)target;
        }
        else {
            this._calcType = 4;
            this._npc = attacker;
            this._targetNpc = (L1NpcInstance)target;
        }
    }
    
    private int getMagicLevel() {
        final int magicLevel = this._npc.getMagicLevel();
        return magicLevel;
    }
    
    private int getMagicBonus() {
        final int magicBonus = this._npc.getMagicBonus();
        return magicBonus;
    }
    
    private int getLawful() {
        final int lawful = this._npc.getLawful();
        return lawful;
    }
    
    @Override
    public boolean calcProbabilityMagic(final int skillId) {
        int probability = 0;
        boolean isSuccess = false;
        switch (this._calcType) {
            case 3: {
                if (this._targetPc.getattr_\u9b54\u6cd5\u683c\u6a94() > 0 && L1MagicNpc._random.nextInt(100) < this._targetPc.getattr_\u9b54\u6cd5\u683c\u6a94()) {
                    return false;
                }
                if (this._targetPc.hasSkillEffect(50) && skillId != 44) {
                    return false;
                }
                if (this._targetPc.hasSkillEffect(157) && skillId != 44) {
                    return false;
                }
                if (skillId == 44) {
                    return true;
                }
                break;
            }
            case 4: {
                if (this._targetNpc.hasSkillEffect(50) && skillId != 44) {
                    return false;
                }
                if (this._targetNpc.hasSkillEffect(157) && skillId != 44) {
                    return false;
                }
                break;
            }
        }
        probability = this.calcProbability(skillId);
        final int rnd = L1MagicNpc._random.nextInt(100) + 1;
        probability = Math.min(probability, 90);
        isSuccess = (probability >= rnd);
        return !this.calcEvasion() && isSuccess;
    }
    
    private int calcProbability(final int skillId) {
        final L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        final int attackLevel = this._npc.getLevel();
        int defenseLevel = 0;
        int probability = 0;
        switch (this._calcType) {
            case 3: {
                defenseLevel = this._targetPc.getLevel();
                break;
            }
            case 4: {
                defenseLevel = this._targetNpc.getLevel();
                if (skillId == 145 && this._targetNpc instanceof L1SummonInstance) {
                    final L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
                    defenseLevel = summon.getMaster().getLevel();
                    break;
                }
                break;
            }
        }
        switch (skillId) {
            case 133:
            case 145:
            case 152:
            case 153:
            case 157:
            case 161:
            case 167:
            case 173:
            case 174: {
                probability = (int)(l1skills.getProbabilityDice() / 10.0 * (attackLevel - defenseLevel));
                probability += l1skills.getProbabilityValue();
                probability -= this.getTargetMr() / 10;
                probability *= (int)(this.getLeverage() / 10.0);
                break;
            }
            case 87: {
                if (attackLevel > defenseLevel) {
                    probability = 70;
                }
                else if (attackLevel == defenseLevel) {
                    probability = 50;
                }
                else if (attackLevel < defenseLevel) {
                    probability = 30;
                }
                probability *= (int)(this.getLeverage() / 10.0);
                break;
            }
            case 91: {
                probability = 20;
                break;
            }
            case 103:
            case 112: {
                probability = 38 + (attackLevel - defenseLevel) * (L1MagicNpc._random.nextInt(3) + 2);
                probability *= (int)(this.getLeverage() / 10.0);
                break;
            }
            case 202:
            case 208:
            case 212:
            case 217: {
                probability = Random.nextInt(11) + 20;
                probability += (attackLevel - defenseLevel) * 2;
                probability *= (int)(this.getLeverage() / 10.0);
                break;
            }
            case 183:
            case 188:
            case 192:
            case 193: {
                probability = (int)(l1skills.getProbabilityDice() / 10.0 * (attackLevel - defenseLevel));
                probability += l1skills.getProbabilityValue();
                probability *= (int)(this.getLeverage() / 10.0);
                break;
            }
            case 67:// 變形術
    			probability = 3 * (attackLevel - defenseLevel) + 200 - getTargetMr();
    			break;
    		case 228:// 拘束移動
    		case 230:// 亡命之徒
    			if (attackLevel > defenseLevel) {// 攻擊方等級大於防禦方
    				probability = 70;
    			} else if (attackLevel == defenseLevel) {// 攻擊方等級相等防禦方
    				probability = 50;
    			} else if (attackLevel < defenseLevel) {// 攻擊方等級小於防禦方
    				probability = 30;
    			}
    			probability = (int) (probability * (getLeverage() / 10.0D));// DB設定增加命中倍率
    			break;
    		case 229:// 戰斧投擲
    			probability = (int) (l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
    			probability += l1skills.getProbabilityValue();
    			probability = (int) (probability * (getLeverage() / 10.0D));// DB設定增加命中倍率
    			break;
    		case 237:// 泰坦岩石
    		case 238:// 泰坦子彈
    		case 239:// 泰坦魔法
    			probability += l1skills.getProbabilityValue();
    			break;
            default: {
                final int dice2 = l1skills.getProbabilityDice();
                int diceCount2 = 0;
                diceCount2 = this.getMagicBonus() + this.getMagicLevel();
                diceCount2 = Math.max(diceCount2, 1);
                for (int i = 0; i < diceCount2; ++i) {
                    if (dice2 > 0) {
                        probability += L1MagicNpc._random.nextInt(dice2) + 1;
                    }
                }
                probability *= (int)(this.getLeverage() / 10.0);
                probability -= this.getTargetMr();
                break;
            }
        }
        if (this._calcType == 3) {
            switch (skillId) {
                case 157:
                case 192: {
                    probability -= this._targetPc.getRegistSustain();
                    break;
                }
    			case 228:// 拘束移動
    			case 230:// 亡命之徒
    				probability -= this._targetPc.getRegistSustain();
    				break;
                case 87:
                case 208: {
                    probability -= this._targetPc.getRegistStun();
                    break;
                }
                case 33: {
                    probability -= this._targetPc.getRegistStone();
                    break;
                }
                case 103:
                case 212: {
                    probability -= this._targetPc.getRegistSleep();
                    break;
                }
                case 20:
                case 40:
                case 50: {
                    probability -= this._targetPc.getRegistBlind();
                    break;
                }
            }
        }
        return probability;
    }
    
    @Override
    public int calcMagicDamage(final int skillId) {
        int damage = 0;
        switch (this._calcType) {
            case 3: {
                damage = this.calcPcMagicDamage(skillId);
                break;
            }
            case 4: {
                damage = this.calcNpcMagicDamage(skillId);
                break;
            }
        }
        damage = this.calcMrDefense(damage);
        return damage;
    }
    
    private int calcPcMagicDamage(final int skillId) {
        if (this._targetPc == null) {
            return 0;
        }
        if ((this._npc instanceof L1PetInstance || this._npc instanceof L1SummonInstance) && this._targetPc.getZoneType() == 1) {
            return 0;
        }
        if (L1MagicMode.dmg0(this._targetPc)) {
            return 0;
        }
        if (this.calcEvasion()) {
            return 0;
        }
        int dmg = 0;
        if (skillId == 108) {
            dmg = this._npc.getCurrentMp();
        }
        else {
            dmg = this.calcMagicDiceDamage(skillId);
            dmg *= (int)(this.getLeverage() / 10.0);
            if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                dmg *= 2;
            }
        }
        dmg -= this._targetPc.getMagicDmgReduction() + this._targetPc.get_Clanmagic_reduction_dmg() + this._targetPc.get_magic_reduction_dmg();
        dmg -= this._targetPc.dmgDowe();
        if (this._targetPc.getClanid() != 0) {
            dmg -= (int)L1MagicMode.getDamageReductionByClan(this._targetPc);
        }
        if (this._targetPc.hasSkillEffect(88)) {
            final int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
            dmg -= (targetPcLvl - 50) / 5 + 1;
        }
        final int ran = L1MagicNpc._random.nextInt(100) + 1;
        if (this._targetPc.getInventory().checkSkillType(133) && ran <= ConfigOther.armor_type21) {
            dmg *= (int)0.99;
        }
        if (this._targetPc.getInventory().checkSkillType(134) && ran <= ConfigOther.armor_type22) {
            dmg *= (int)0.95;
        }
        if (this._targetPc.getInventory().checkSkillType(135) && ran <= ConfigOther.armor_type23) {
            dmg *= (int)0.9;
        }
        if (this._targetPc.getInventory().checkSkillType(136) && ran <= ConfigOther.armor_type24) {
            dmg *= (int)0.85;
        }
        if (this._targetPc.getInventory().checkSkillType(137) && ran <= ConfigOther.armor_type25) {
            dmg *= (int)0.8;
        }
        boolean isNowWar = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(this._targetPc);
        if (castleId > 0) {
            isNowWar = ServerWarExecutor.get().isNowWar(castleId);
        }
        if (!isNowWar) {
            if (this._npc instanceof L1PetInstance) {
                dmg >>= 3;
            }
            if (this._npc instanceof L1SummonInstance) {
                final L1SummonInstance summon = (L1SummonInstance)this._npc;
                if (summon.isExsistMaster()) {
                    dmg >>= 3;
                }
            }
        }
        if (this._targetPc.hasSkillEffect(68)) {
            dmg /= (int)ConfigOther.IMMUNE_TO_HARM_NPC;
        }
        if ((this._targetPc.hasSkillEffect(6685) || this._targetPc.hasSkillEffect(6687) || this._targetPc.hasSkillEffect(6688) || this._targetPc.hasSkillEffect(6689)) && L1MagicNpc._random.nextInt(100) < 10) {
            dmg /= 2;
            this._targetPc.sendPacketsAll(new S_SkillSound(this._targetPc.getId(), 6320));
        }
        if (this._targetPc.hasSkillEffect(134)) {
            final int npcId = this._npc.getNpcTemplate().get_npcId();
            switch (npcId) {
                case 45681:
                case 45682:
                case 45683:
                case 45684:
                case 71014:
                case 71015:
                case 71016:
                case 71026:
                case 71027:
                case 71028:
                case 97204:
                case 97205:
                case 97206:
                case 97207:
                case 97208:
                case 97209: {
                    break;
                }
                default: {
                    if (this._npc.getNpcTemplate().get_IsErase() && this._targetPc.getWis() >= L1MagicNpc._random.nextInt(100)) {
                        this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
                        this._npc.receiveDamage(this._targetPc, dmg);
                        this._npc.broadcastPacketAll(new S_SkillSound(this._targetPc.getId(), 4395));
                        dmg = 0;
                        this._targetPc.killSkillEffectTimer(134);
                        break;
                    }
                    break;
                }
            }
        }
        final int dmgOut = Math.max(dmg, 0);
        return dmgOut;
    }
    
    private int calcNpcMagicDamage(final int skillId) {
        if (this._targetNpc == null) {
            return 0;
        }
        if (L1MagicMode.dmg0(this._targetNpc)) {
            return 0;
        }
        int dmg = 0;
        if (skillId == 108) {
            dmg = this._npc.getCurrentMp();
        }
        else {
            dmg = this.calcMagicDiceDamage(skillId);
            dmg *= (int)(this.getLeverage() / 10.0);
            if (this._npc.getNpcTemplate().get_nameid().startsWith("BOSS")) {
                dmg *= 2;
            }
        }
        if (this._targetNpc.hasSkillEffect(68)) {
            dmg /= 2;
        }
        if (this._targetNpc.hasSkillEffect(134) && this._npc.getNpcTemplate().get_IsErase() && this._targetNpc.getWis() >= L1MagicNpc._random.nextInt(100)) {
            this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
            this._npc.receiveDamage(this._targetNpc, dmg);
            this._npc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
            dmg = 0;
            this._targetNpc.killSkillEffectTimer(134);
        }
        if (this._targetNpc.hasSkillEffect(11059) && this._npc.getNpcTemplate().get_IsErase()) {
            this._npc.broadcastPacketAll(new S_DoActionGFX(this._npc.getId(), 2));
            this._npc.receiveDamage(this._targetNpc, dmg);
            this._npc.broadcastPacketAll(new S_SkillSound(this._targetNpc.getId(), 4395));
            dmg = 0;
        }
        return dmg;
    }
    
    private int calcMagicDiceDamage(final int skillId) {
        final L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        final int dice = l1skills.getDamageDice();
        final int diceCount = l1skills.getDamageDiceCount();
        final int value = l1skills.getDamageValue();
        int magicDamage = 0;
        int charaIntelligence = 0;
        for (int i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicNpc._random.nextInt(dice) + 1;
        }
        magicDamage += value;
        final int spByItem = this.getTargetSp();
        charaIntelligence = Math.max(this._npc.getInt() + spByItem - 12, 1);
        final double attrDeffence = this.calcAttrResistance(l1skills.getAttr());
        final double coefficient = Math.max(1.0 - attrDeffence + charaIntelligence * 3.0 / 32.0, 0.0);
        magicDamage *= (int)coefficient;
        return magicDamage;
    }
    
    @Override
    public int calcHealing(final int skillId) {
        final L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
        final int dice = l1skills.getDamageDice();
        final int value = l1skills.getDamageValue();
        int magicDamage = 0;
        final int magicBonus = Math.min(this.getMagicBonus(), 10);
        for (int diceCount = value + magicBonus, i = 0; i < diceCount; ++i) {
            magicDamage += L1MagicNpc._random.nextInt(dice) + 1;
        }
        double alignmentRevision = 1.0;
        if (this.getLawful() > 0) {
            alignmentRevision += this.getLawful() / 32768.0;
        }
        magicDamage *= (int)alignmentRevision;
        magicDamage *= (int)(this.getLeverage() / 10.0);
        return magicDamage;
    }
    
    private int calcMrDefense(int dmg) {
        final int mr = this.getTargetMr();
        final int rnd = L1MagicNpc._random.nextInt(100) + 1;
        if (mr >= rnd) {
            dmg /= 2;
        }
        return dmg;
    }
    
    @Override
    public void commit(final int damage, final int drainMana) {
        switch (this._calcType) {
            case 3: {
                this.commitPc(damage, drainMana);
                break;
            }
            case 4: {
                this.commitNpc(damage, drainMana);
                break;
            }
        }
        if (!ConfigAlt.ALT_ATKMSG) {
            return;
        }
        if (this._calcType == 4) {
            return;
        }
        if (!this._targetPc.isGm()) {
            return;
        }
        final StringBuilder atkMsg = new StringBuilder();
        atkMsg.append("\u53d7\u5230NPC\u6280\u80fd: ");
        atkMsg.append(String.valueOf(this._npc.getNameId()) + ">");
        atkMsg.append(String.valueOf(this._targetPc.getName()) + " ");
        atkMsg.append("\u50b7\u5bb3: " + damage);
        this._targetPc.sendPackets(new S_ServerMessage(166, atkMsg.toString()));
    }
    
    private void commitPc(final int damage, final int drainMana) {
        try {
            this._targetPc.receiveDamage(this._npc, damage, true, false);
        }
        catch (Exception e) {
            L1MagicNpc._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void commitNpc(final int damage, final int drainMana) {
        try {
            this._targetNpc.receiveDamage(this._npc, damage);
        }
        catch (Exception e) {
            L1MagicNpc._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
}
