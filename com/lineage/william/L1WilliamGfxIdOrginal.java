package com.lineage.william;

import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1PcInstance;

public class L1WilliamGfxIdOrginal
{
    private int _gfxId;
    private boolean _deadExp;
    private boolean _cancellation;
    private byte _addStr;
    private byte _addDex;
    private byte _addCon;
    private byte _addInt;
    private byte _addWis;
    private byte _addCha;
    private int _addAc;
    private int _addMaxHp;
    private int _addMaxMp;
    private int _addHpr;
    private int _addMpr;
    private int _addDmg;
    private int _addBowDmg;
    private int _addHit;
    private int _addBowHit;
    private int _reduction_dmg;
    private int _reduction_magic_dmg;
    private int _addMr;
    private int _addSp;
    private int _addFire;
    private int _addWind;
    private int _addEarth;
    private int _addWater;
    private double _addExp;
    private final int _potion_heal;
    private final int _PVPdmg;
    private final int _PVPdmgReduction;
    private final int _magic_hit;
    private int _regist_stun;
    private int _regist_stone;
    private int _regist_sleep;
    private int _regist_freeze;
    private int _regist_sustain;
    private int _regist_blind;
    
    public static boolean DeadExp(final int gfxId) {
        final L1WilliamGfxIdOrginal gfxIdOrginal = GfxIdOrginal.getInstance().getTemplate(gfxId);
        return gfxIdOrginal != null && gfxIdOrginal.getDeadExp();
    }
    
    public static boolean Cancellation(final int gfxId) {
        final L1WilliamGfxIdOrginal gfxIdOrginal = GfxIdOrginal.getInstance().getTemplate(gfxId);
        return gfxIdOrginal != null && gfxIdOrginal.getCancellation();
    }
    
    public static void getAddGfxIdOrginal(final L1PcInstance pc, final int gfxId) {
        final L1WilliamGfxIdOrginal gfxIdOrginal = GfxIdOrginal.getInstance().getTemplate(gfxId);
        if (gfxIdOrginal == null) {
            return;
        }
        pc.sendPackets(new S_ServerMessage("\\aH[此變身獲得以下能力]"));
        if (gfxIdOrginal.getAddStr() != 0) {
            pc.addStr(gfxIdOrginal.getAddStr());
            pc.sendPackets(new S_ServerMessage("\\aI力量+" + gfxIdOrginal.getAddStr()));
        }
        if (gfxIdOrginal.getAddDex() != 0) {
            pc.addDex(gfxIdOrginal.getAddDex());
            pc.sendPackets(new S_ServerMessage("\\aI敏捷+" + gfxIdOrginal.getAddDex()));
        }
        if (gfxIdOrginal.getAddCon() != 0) {
            pc.addCon(gfxIdOrginal.getAddCon());
            pc.sendPackets(new S_ServerMessage("\\aI體質+" + gfxIdOrginal.getAddCon()));
        }
        if (gfxIdOrginal.getAddInt() != 0) {
            pc.addInt(gfxIdOrginal.getAddInt());
            pc.sendPackets(new S_ServerMessage("\\aI智力+" + gfxIdOrginal.getAddInt()));
        }
        if (gfxIdOrginal.getAddWis() != 0) {
            pc.addWis(gfxIdOrginal.getAddWis());
            pc.sendPackets(new S_ServerMessage("\\aI精神+" + gfxIdOrginal.getAddWis()));
        }
        if (gfxIdOrginal.getAddCha() != 0) {
            pc.addCha(gfxIdOrginal.getAddCha());
            pc.sendPackets(new S_ServerMessage("\\aI魅力+" + gfxIdOrginal.getAddCha()));
        }
        if (gfxIdOrginal.getAddAc() != 0) {
            pc.addAc(-gfxIdOrginal.getAddAc());
            pc.sendPackets(new S_ServerMessage("\\aI防禦+" + gfxIdOrginal.getAddAc()));
        }
        if (gfxIdOrginal.getAddMaxHp() != 0) {
            pc.addMaxHp(gfxIdOrginal.getAddMaxHp());
            pc.sendPackets(new S_ServerMessage("\\aI血量增加+" + gfxIdOrginal.getAddMaxHp()));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        }
        if (gfxIdOrginal.getAddMaxMp() != 0) {
            pc.addMaxMp(gfxIdOrginal.getAddMaxMp());
            pc.sendPackets(new S_ServerMessage("\\aI魔量增加+" + gfxIdOrginal.getAddMaxMp()));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
        }
        if (gfxIdOrginal.getAddHpr() != 0) {
            pc.addHpr(gfxIdOrginal.getAddHpr());
            pc.sendPackets(new S_ServerMessage("\\aI回血+" + gfxIdOrginal.getAddHpr()));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        }
        if (gfxIdOrginal.getAddMpr() != 0) {
            pc.addMpr(gfxIdOrginal.getAddMpr());
            pc.sendPackets(new S_ServerMessage("\\aI回魔+" + gfxIdOrginal.getAddMpr()));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
        }
        if (gfxIdOrginal.getAddDmg() != 0) {
            pc.addDmgup(gfxIdOrginal.getAddDmg());
            pc.sendPackets(new S_ServerMessage("\\aI近戰傷害+" + gfxIdOrginal.getAddDmg()));
        }
        if (gfxIdOrginal.getAddHit() != 0) {
            pc.addHitup(gfxIdOrginal.getAddHit());
            pc.sendPackets(new S_ServerMessage("\\aI近戰命中+" + gfxIdOrginal.getAddHit()));
        }
        if (gfxIdOrginal.getAddBowDmg() != 0) {
            pc.addBowDmgup(gfxIdOrginal.getAddBowDmg());
            pc.sendPackets(new S_ServerMessage("\\aI遠戰傷害+" + gfxIdOrginal.getAddBowDmg()));
        }
        if (gfxIdOrginal.getAddBowHit() != 0) {
            pc.addBowHitup(gfxIdOrginal.getAddBowHit());
            pc.sendPackets(new S_ServerMessage("\\aI遠戰命中+" + gfxIdOrginal.getAddBowHit()));
        }
        if (gfxIdOrginal.getReduction_dmg() != 0) {
            pc.addother_ReductionDmg(gfxIdOrginal.getReduction_dmg());
            pc.sendPackets(new S_ServerMessage("\\aI減免物理傷害+" + gfxIdOrginal.getReduction_dmg()));
        }
        if (gfxIdOrginal.getAddMr() != 0) {
            pc.addMr(gfxIdOrginal.getAddMr());
            pc.sendPackets(new S_ServerMessage("\\aI抗魔+" + gfxIdOrginal.getAddMr()));
        }
        if (gfxIdOrginal.getAddSp() != 0) {
            pc.addSp(gfxIdOrginal.getAddSp());
            pc.sendPackets(new S_ServerMessage("\\aI魔攻+" + gfxIdOrginal.getAddSp()));
        }
        if (gfxIdOrginal.getAddFire() != 0) {
            pc.addFire(gfxIdOrginal.getAddFire());
            pc.sendPackets(new S_ServerMessage("\\aI+抗火屬性" + gfxIdOrginal.getAddFire()));
        }
        if (gfxIdOrginal.getAddWind() != 0) {
            pc.addWind(gfxIdOrginal.getAddWind());
            pc.sendPackets(new S_ServerMessage("\\aI抗風屬性+" + gfxIdOrginal.getAddWind()));
        }
        if (gfxIdOrginal.getAddEarth() != 0) {
            pc.addEarth(gfxIdOrginal.getAddEarth());
            pc.sendPackets(new S_ServerMessage("\\aI抗地屬性+" + gfxIdOrginal.getAddEarth()));
        }
        if (gfxIdOrginal.getAddWater() != 0) {
            pc.addWater(gfxIdOrginal.getAddWater());
            pc.sendPackets(new S_ServerMessage("\\aI抗水屬性+" + gfxIdOrginal.getAddWater()));
        }
        if (gfxIdOrginal.getAddExp() != 0.0) {
            pc.addExpByArmor(gfxIdOrginal.getAddExp());
            pc.sendPackets(new S_ServerMessage("\\aI經驗+" + gfxIdOrginal.getAddExp() + "%"));
        }
        if (gfxIdOrginal.getPotion_Heal() != 0) {
            pc.add_potion_heal(gfxIdOrginal.getPotion_Heal());
            pc.sendPackets(new S_ServerMessage("\\aI藥水回復量+" + gfxIdOrginal.getPotion_Heal() + "%"));
        }
        if (gfxIdOrginal.getPVPdmg() != 0) {
            pc.add_PVPdmgg(gfxIdOrginal.getPVPdmg());
            pc.sendPackets(new S_ServerMessage("\\aIPVP傷害+" + gfxIdOrginal.getPVPdmg()));
        }
        if (gfxIdOrginal.getPVPdmgReduction() != 0) {
            pc.addPVPdmgReduction(gfxIdOrginal.getPVPdmgReduction());
            pc.sendPackets(new S_ServerMessage("\\aIPVP減傷+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getAddMagicHit() != 0) {
            pc.addOriginalMagicHit(gfxIdOrginal.getAddMagicHit());
            pc.sendPackets(new S_ServerMessage("\\aI魔法命中+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_stun() != 0) {
            pc.addRegistStun(gfxIdOrginal.getRegist_stun());
            pc.sendPackets(new S_ServerMessage("\\aI昏迷耐性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_stone() != 0) {
            pc.addRegistStone(gfxIdOrginal.getRegist_stone());
            pc.sendPackets(new S_ServerMessage("\\aI石化耐性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_sleep() != 0) {
            pc.addRegistSleep(gfxIdOrginal.getRegist_sleep());
            pc.sendPackets(new S_ServerMessage("\\aI睡眠耐性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_freeze() != 0) {
            pc.add_regist_freeze(gfxIdOrginal.getRegist_freeze());
            pc.sendPackets(new S_ServerMessage("\\aI冰耐性性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_sustain() != 0) {
            pc.addRegistSustain(gfxIdOrginal.getRegist_sustain());
            pc.sendPackets(new S_ServerMessage("\\aI支撐耐性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        if (gfxIdOrginal.getRegist_blind() != 0) {
            pc.addRegistBlind(gfxIdOrginal.getRegist_blind());
            pc.sendPackets(new S_ServerMessage("\\aI暗黑耐性+" + gfxIdOrginal.getPVPdmgReduction()));
        }
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_OwnCharStatus2(pc));
    }
    
    public static void getReductionGfxIdOrginal(final L1PcInstance pc, final int gfxId) {
        final L1WilliamGfxIdOrginal gfxIdOrginal = GfxIdOrginal.getInstance().getTemplate(gfxId);
        if (gfxIdOrginal == null) {
            return;
        }
        pc.sendPackets(new S_ServerMessage("\\aH[以下變身能力消失]"));
        if (gfxIdOrginal.getAddStr() != 0) {
            pc.addStr(-gfxIdOrginal.getAddStr());
            pc.sendPackets(new S_ServerMessage("\\aB力量+" + gfxIdOrginal.getAddStr() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddDex() != 0) {
            pc.addDex(-gfxIdOrginal.getAddDex());
            pc.sendPackets(new S_ServerMessage("\\aB敏捷+" + gfxIdOrginal.getAddDex() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddCon() != 0) {
            pc.addCon(-gfxIdOrginal.getAddCon());
            pc.sendPackets(new S_ServerMessage("\\aB體質+" + gfxIdOrginal.getAddCon() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddInt() != 0) {
            pc.addInt(-gfxIdOrginal.getAddInt());
            pc.sendPackets(new S_ServerMessage("\\aB智力+" + gfxIdOrginal.getAddInt() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddWis() != 0) {
            pc.addWis(-gfxIdOrginal.getAddWis());
            pc.sendPackets(new S_ServerMessage("\\aB精神+" + gfxIdOrginal.getAddWis() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddCha() != 0) {
            pc.addCha(-gfxIdOrginal.getAddCha());
            pc.sendPackets(new S_ServerMessage("\\aB魅力+" + gfxIdOrginal.getAddCha() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddAc() != 0) {
            pc.addAc(gfxIdOrginal.getAddAc());
            pc.sendPackets(new S_ServerMessage("\\aB防禦+" + gfxIdOrginal.getAddAc() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddMaxHp() != 0) {
            pc.addMaxHp(-gfxIdOrginal.getAddMaxHp());
            pc.sendPackets(new S_ServerMessage("\\aB血量增加+" + gfxIdOrginal.getAddMaxHp() + "[能力消失了]"));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        }
        if (gfxIdOrginal.getAddMaxMp() != 0) {
            pc.addMaxMp(-gfxIdOrginal.getAddMaxMp());
            pc.sendPackets(new S_ServerMessage("\\aB魔量增加+" + gfxIdOrginal.getAddMaxMp() + "[能力消失了]"));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
        }
        if (gfxIdOrginal.getAddHpr() != 0) {
            pc.addHpr(-gfxIdOrginal.getAddHpr());
            pc.sendPackets(new S_ServerMessage("\\aB回血+" + gfxIdOrginal.getAddHpr() + "[能力消失了]"));
            pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        }
        if (gfxIdOrginal.getAddMpr() != 0) {
            pc.addMpr(-gfxIdOrginal.getAddMpr());
            pc.sendPackets(new S_ServerMessage("\\aB回魔+" + gfxIdOrginal.getAddMpr() + "[能力消失了]"));
            pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
        }
        if (gfxIdOrginal.getAddDmg() != 0) {
            pc.addDmgup(-gfxIdOrginal.getAddDmg());
            pc.sendPackets(new S_ServerMessage("\\aB近戰傷害+" + gfxIdOrginal.getAddDmg() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddHit() != 0) {
            pc.addHitup(-gfxIdOrginal.getAddHit());
            pc.sendPackets(new S_ServerMessage("\\aB近戰命中+" + gfxIdOrginal.getAddHit() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddBowDmg() != 0) {
            pc.addBowDmgup(-gfxIdOrginal.getAddBowDmg());
            pc.sendPackets(new S_ServerMessage("\\aB遠戰傷害+" + gfxIdOrginal.getAddBowDmg() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddBowHit() != 0) {
            pc.addBowHitup(-gfxIdOrginal.getAddBowHit());
            pc.sendPackets(new S_ServerMessage("\\aB遠戰命中+" + gfxIdOrginal.getAddBowHit() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getReduction_dmg() != 0) {
            pc.addother_ReductionDmg(-gfxIdOrginal.getReduction_dmg());
            pc.sendPackets(new S_ServerMessage("\\aB減免物理傷害+" + gfxIdOrginal.getReduction_dmg() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddMr() != 0) {
            pc.addMr(-gfxIdOrginal.getAddMr());
            pc.sendPackets(new S_ServerMessage("\\aB抗魔+" + gfxIdOrginal.getAddMr() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddSp() != 0) {
            pc.addSp(-gfxIdOrginal.getAddSp());
            pc.sendPackets(new S_ServerMessage("\\aB魔攻+" + gfxIdOrginal.getAddSp() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddFire() != 0) {
            pc.addFire(-gfxIdOrginal.getAddFire());
            pc.sendPackets(new S_ServerMessage("\\aB+抗火屬性" + gfxIdOrginal.getAddFire() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddWind() != 0) {
            pc.addWind(-gfxIdOrginal.getAddWind());
            pc.sendPackets(new S_ServerMessage("\\aB抗風屬性+" + gfxIdOrginal.getAddWind() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddEarth() != 0) {
            pc.addEarth(-gfxIdOrginal.getAddEarth());
            pc.sendPackets(new S_ServerMessage("\\aB抗地屬性+" + gfxIdOrginal.getAddEarth() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddWater() != 0) {
            pc.addWater(-gfxIdOrginal.getAddWater());
            pc.sendPackets(new S_ServerMessage("\\aB抗水屬性+" + gfxIdOrginal.getAddWater() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddExp() != 0.0) {
            pc.addExpByArmor(-gfxIdOrginal.getAddExp());
            pc.sendPackets(new S_ServerMessage("\\aB經驗" + gfxIdOrginal.getAddExp() + "%.[能力消失了]"));
        }
        if (gfxIdOrginal.getPotion_Heal() != 0) {
            pc.add_potion_heal(-gfxIdOrginal.getPotion_Heal());
            pc.sendPackets(new S_ServerMessage("\\aB藥水回復量+" + gfxIdOrginal.getPotion_Heal() + "%.[能力消失了]"));
        }
        if (gfxIdOrginal.getPVPdmg() != 0) {
            pc.add_PVPdmgg(-gfxIdOrginal.getPVPdmg());
            pc.sendPackets(new S_ServerMessage("\\aBPVP傷害+" + gfxIdOrginal.getPVPdmg() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getPVPdmgReduction() != 0) {
            pc.addPVPdmgReduction(-gfxIdOrginal.getPVPdmgReduction());
            pc.sendPackets(new S_ServerMessage("\\aBPVP減傷+" + gfxIdOrginal.getPVPdmgReduction() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getAddMagicHit() != 0) {
            pc.addOriginalMagicHit(-gfxIdOrginal.getAddMagicHit());
            pc.sendPackets(new S_ServerMessage("\\aB魔法命中+" + gfxIdOrginal.getAddMagicHit() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_stun() != 0) {
            pc.addRegistStun(-gfxIdOrginal.getRegist_stun());
            pc.sendPackets(new S_ServerMessage("\\aB昏迷耐性+" + gfxIdOrginal.getRegist_stun() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_stone() != 0) {
            pc.addRegistStone(-gfxIdOrginal.getRegist_stone());
            pc.sendPackets(new S_ServerMessage("\\aB石化耐性+" + gfxIdOrginal.getRegist_stone() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_sleep() != 0) {
            pc.addRegistSleep(-gfxIdOrginal.getRegist_sleep());
            pc.sendPackets(new S_ServerMessage("\\aB睡眠耐性+" + gfxIdOrginal.getRegist_sleep() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_freeze() != 0) {
            pc.add_regist_freeze(-gfxIdOrginal.getRegist_freeze());
            pc.sendPackets(new S_ServerMessage("\\aB冰耐性性+" + gfxIdOrginal.getRegist_freeze() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_sustain() != 0) {
            pc.addRegistSustain(-gfxIdOrginal.getRegist_sustain());
            pc.sendPackets(new S_ServerMessage("\\aB支撐耐性+" + gfxIdOrginal.getRegist_sustain() + "[能力消失了]"));
        }
        if (gfxIdOrginal.getRegist_blind() != 0) {
            pc.addRegistBlind(-gfxIdOrginal.getRegist_blind());
            pc.sendPackets(new S_ServerMessage("\\aB暗黑耐性+" + gfxIdOrginal.getRegist_blind() + "[能力消失了]"));
            pc.sendPackets(new S_SPMR(pc));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_OwnCharStatus2(pc));
        }
    }
    
    public L1WilliamGfxIdOrginal(final int gfxId, final boolean deadExp, final boolean cancellation, final byte addStr, final byte addDex, final byte addCon, final byte addInt, final byte addWis, final byte addCha, final int addAc, final int addMaxHp, final int addMaxMp, final int addHpr, final int addMpr, final int addDmg, final int addBowDmg, final int addHit, final int addBowHit, final int reduction_dmg, final int reduction_magic_dmg, final int addMr, final int addSp, final int addFire, final int addWind, final int addEarth, final int addWater, final double addExp, final int potion_heal, final int PVPdmg, final int PVPdmgReduction, final int magic_hit, final int regist_stun, final int regist_stone, final int regist_sleep, final int regist_freeze, final int regist_sustain, final int regist_blind) {
        this._gfxId = gfxId;
        this._deadExp = deadExp;
        this._cancellation = cancellation;
        this._addStr = addStr;
        this._addDex = addDex;
        this._addCon = addCon;
        this._addInt = addInt;
        this._addWis = addWis;
        this._addCha = addCha;
        this._addAc = addAc;
        this._addMaxHp = addMaxHp;
        this._addMaxMp = addMaxMp;
        this._addHpr = addHpr;
        this._addMpr = addMpr;
        this._addDmg = addDmg;
        this._addBowDmg = addBowDmg;
        this._addHit = addHit;
        this._addBowHit = addBowHit;
        this._reduction_dmg = reduction_dmg;
        this._reduction_magic_dmg = reduction_magic_dmg;
        this._addMr = addMr;
        this._addSp = addSp;
        this._addFire = addFire;
        this._addWind = addWind;
        this._addEarth = addEarth;
        this._addWater = addWater;
        this._addExp = addExp;
        this._potion_heal = potion_heal;
        this._PVPdmg = PVPdmg;
        this._PVPdmgReduction = PVPdmgReduction;
        this._magic_hit = magic_hit;
        this._regist_stun = regist_stun;
        this._regist_stone = regist_stone;
        this._regist_sleep = regist_sleep;
        this._regist_freeze = regist_freeze;
        this._regist_sustain = regist_sustain;
        this._regist_blind = regist_blind;
    }
    
    public int getGfxId() {
        return this._gfxId;
    }
    
    public boolean getDeadExp() {
        return this._deadExp;
    }
    
    public boolean getCancellation() {
        return this._cancellation;
    }
    
    public byte getAddStr() {
        return this._addStr;
    }
    
    public byte getAddDex() {
        return this._addDex;
    }
    
    public byte getAddCon() {
        return this._addCon;
    }
    
    public byte getAddInt() {
        return this._addInt;
    }
    
    public byte getAddWis() {
        return this._addWis;
    }
    
    public byte getAddCha() {
        return this._addCha;
    }
    
    public int getAddAc() {
        return this._addAc;
    }
    
    public int getAddMaxHp() {
        return this._addMaxHp;
    }
    
    public int getAddMaxMp() {
        return this._addMaxMp;
    }
    
    public int getAddHpr() {
        return this._addHpr;
    }
    
    public int getAddMpr() {
        return this._addMpr;
    }
    
    public int getAddDmg() {
        return this._addDmg;
    }
    
    public int getAddBowDmg() {
        return this._addBowDmg;
    }
    
    public int getAddHit() {
        return this._addHit;
    }
    
    public int getAddBowHit() {
        return this._addBowHit;
    }
    
    public int getReduction_dmg() {
        return this._reduction_dmg;
    }
    
    public int getReduction_magic_dmg() {
        return this._reduction_magic_dmg;
    }
    
    public int getAddMr() {
        return this._addMr;
    }
    
    public int getAddSp() {
        return this._addSp;
    }
    
    public int getAddFire() {
        return this._addFire;
    }
    
    public int getAddWind() {
        return this._addWind;
    }
    
    public int getAddEarth() {
        return this._addEarth;
    }
    
    public int getAddWater() {
        return this._addWater;
    }
    
    public double getAddExp() {
        return this._addExp;
    }
    
    public int getPVPdmg() {
        return this._PVPdmg;
    }
    
    public int getPVPdmgReduction() {
        return this._PVPdmgReduction;
    }
    
    public int getPotion_Heal() {
        return this._potion_heal;
    }
    
    public int getAddMagicHit() {
        return this._magic_hit;
    }
    
    public int getRegist_stun() {
        return this._regist_stun;
    }
    
    public void setRegist_stun(final int regist_stun) {
        this._regist_stun = regist_stun;
    }
    
    public int getRegist_stone() {
        return this._regist_stone;
    }
    
    public void setRegist_stone(final int regist_stone) {
        this._regist_stone = regist_stone;
    }
    
    public int getRegist_sleep() {
        return this._regist_sleep;
    }
    
    public void setRegist_sleep(final int regist_sleep) {
        this._regist_sleep = regist_sleep;
    }
    
    public int getRegist_freeze() {
        return this._regist_freeze;
    }
    
    public void setRegist_freeze(final int regist_freeze) {
        this._regist_freeze = regist_freeze;
    }
    
    public int getRegist_sustain() {
        return this._regist_sustain;
    }
    
    public void setRegist_sustain(final int regist_sustain) {
        this._regist_sustain = regist_sustain;
    }
    
    public int getRegist_blind() {
        return this._regist_blind;
    }
    
    public void setRegist_blind(final int regist_blind) {
        this._regist_blind = regist_blind;
    }
}
