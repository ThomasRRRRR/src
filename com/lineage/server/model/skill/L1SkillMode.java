// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model.skill;

import com.lineage.server.model.Instance.L1PcInstance;
//import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.skill.skillmode.*;
import java.util.HashMap;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.skill.skillmode.SkillMode;
import java.util.Map;
import org.apache.commons.logging.Log;

public class L1SkillMode
{
    private static final Log _log;
    private static final Map<Integer, SkillMode> _skillMode;
    private static L1SkillMode _instance;
    
    static {
        _log = LogFactory.getLog((Class)L1SkillMode.class);
        _skillMode = new HashMap<Integer, SkillMode>();
    }
    
    public static L1SkillMode get() {
        if (L1SkillMode._instance == null) {
            L1SkillMode._instance = new L1SkillMode();
        }
        return L1SkillMode._instance;
    }
    
    public boolean isNotCancelable(final int skillNum) {
        return skillNum == 12 || skillNum == 21 || skillNum == 87 || skillNum == 44 || skillNum == 79;
    }
    
    public boolean isNotCancelable1(final int skillNum) {
        return skillNum == 67;
    }
    
    public void load() {
        try {
            L1SkillMode._skillMode.put(5, new TELEPORT());
            L1SkillMode._skillMode.put(69, new MASS_TELEPORT());
            L1SkillMode._skillMode.put(43, new HASTE());
            L1SkillMode._skillMode.put(44, new CANCELLATION());
            L1SkillMode._skillMode.put(9, new CURE_POISON());
            L1SkillMode._skillMode.put(37, new REMOVE_CURSE());
            L1SkillMode._skillMode.put(67, new SHAPE_CHANGE());
            L1SkillMode._skillMode.put(61, new RESURRECTION());
            L1SkillMode._skillMode.put(75, new GREATER_RESURRECTION());
            L1SkillMode._skillMode.put(79, new ADVANCE_SPIRIT());
            L1SkillMode._skillMode.put(33, new CURSE_PARALYZE());
            L1SkillMode._skillMode.put(4001, new CURSE_PARALYZE());
            L1SkillMode._skillMode.put(20, new CURSE_BLIND());
            L1SkillMode._skillMode.put(40, new CURSE_BLIND());
            L1SkillMode._skillMode.put(71, new DECAY_POTION_DOLL());
            L1SkillMode._skillMode.put(51, new SUMMON_MONSTER());
            L1SkillMode._skillMode.put(21, new BLESSED_ARMOR());
            L1SkillMode._skillMode.put(116, new CALL_CLAN());
            L1SkillMode._skillMode.put(118, new RUN_CLAN());
            L1SkillMode._skillMode.put(113, new TRUE_TARGET());
            L1SkillMode._skillMode.put(87, new SHOCK_STUN());
            L1SkillMode._skillMode.put(89, new BOUNCE_ATTACK());
            L1SkillMode._skillMode.put(90, new SOLID_CARRIAGE());
            L1SkillMode._skillMode.put(165, new CALL_OF_NATURE());
            L1SkillMode._skillMode.put(133, new ELEMENTAL_FALL_DOWN());
            L1SkillMode._skillMode.put(130, new BODY_TO_MIND());
            L1SkillMode._skillMode.put(146, new BLOODY_SOUL());
            L1SkillMode._skillMode.put(132, new TRIPLE_ARROW());
            L1SkillMode._skillMode.put(131, new TELEPORT_TO_MATHER());
            L1SkillMode._skillMode.put(162, new GREATER_ELEMENTAL());
            L1SkillMode._skillMode.put(154, new LESSER_ELEMENTAL());
            L1SkillMode._skillMode.put(167, new WIND_SHACKLE());
            L1SkillMode._skillMode.put(155, new FIRE_BLESS());
            L1SkillMode._skillMode.put(157, new EARTH_BIND());
            L1SkillMode._skillMode.put(111, new DRESS_EVASION());
            L1SkillMode._skillMode.put(106, new UNCANNY_DODGE());
            L1SkillMode._skillMode.put(103, new DARK_BLIND());
            L1SkillMode._skillMode.put(99, new SHADOW_ARMOR());
            L1SkillMode._skillMode.put(185, new AWAKEN_ANTHARAS());
            L1SkillMode._skillMode.put(190, new AWAKEN_FAFURION());
            L1SkillMode._skillMode.put(195, new AWAKEN_VALAKAS());
            L1SkillMode._skillMode.put(187, new FOE_SLAYER());
            L1SkillMode._skillMode.put(186, new BLOODLUST());
            L1SkillMode._skillMode.put(188, new RESIST_FEAR());
            L1SkillMode._skillMode.put(192, new THUNDER_GRAB());
            L1SkillMode._skillMode.put(202, new CONFUSION_DOLL());
            L1SkillMode._skillMode.put(212, new PHANTASM());
            L1SkillMode._skillMode.put(217, new PANIC());
            L1SkillMode._skillMode.put(216, new INSIGHT());
            L1SkillMode._skillMode.put(208, new BONE_BREAK());
            L1SkillMode._skillMode.put(207, new MIND_BREAK());
            L1SkillMode._skillMode.put(219, new ILLUSION_AVATAR());
            L1SkillMode._skillMode.put(209, new ILLUSION_LICH());
            L1SkillMode._skillMode.put(201, new MIRROR_IMAGE());
            L1SkillMode._skillMode.put(4000, new STATUS_FREEZE());
            L1SkillMode._skillMode.put(6683, new DRAGONEYE_VALAKAS());
            L1SkillMode._skillMode.put(6684, new DRAGONEYE_ANTHARAS());
            L1SkillMode._skillMode.put(6685, new DRAGONEYE_FAFURION());
            L1SkillMode._skillMode.put(6686, new DRAGONEYE_LINDVIOR());
            L1SkillMode._skillMode.put(6687, new DRAGONEYE_LIFE());
            L1SkillMode._skillMode.put(6688, new DRAGONEYE_BIRTH());
            L1SkillMode._skillMode.put(6689, new DRAGONEYE_FIGURE());
            L1SkillMode._skillMode.put(4009, new ADLV80_1());
            L1SkillMode._skillMode.put(4010, new ADLV80_2());
            L1SkillMode._skillMode.put(4018, new ADLV80_3());
            L1SkillMode._skillMode.put(11060, new KIRTAS_BARRIER1());
            L1SkillMode._skillMode.put(11059, new KIRTAS_BARRIER2());
            L1SkillMode._skillMode.put(11058, new KIRTAS_BARRIER3());
            L1SkillMode._skillMode.put(11057, new KIRTAS_BARRIER4());
            L1SkillMode._skillMode.put(11061, new LINDVIOR_SKY_SPIKED());
            L1SkillMode._skillMode.put(4500, new DS_GX00());
            L1SkillMode._skillMode.put(4501, new DS_GX01());
            L1SkillMode._skillMode.put(4502, new DS_GX02());
            L1SkillMode._skillMode.put(4503, new DS_GX03());
            L1SkillMode._skillMode.put(4504, new DS_GX04());
            L1SkillMode._skillMode.put(4505, new DS_GX05());
            L1SkillMode._skillMode.put(4506, new DS_GX06());
            L1SkillMode._skillMode.put(4507, new DS_GX07());
            L1SkillMode._skillMode.put(4508, new DS_GX08());
            L1SkillMode._skillMode.put(4509, new DS_GX09());
            L1SkillMode._skillMode.put(4510, new DS_AX00());
            L1SkillMode._skillMode.put(4511, new DS_AX01());
            L1SkillMode._skillMode.put(4512, new DS_AX02());
            L1SkillMode._skillMode.put(4513, new DS_AX03());
            L1SkillMode._skillMode.put(4514, new DS_AX04());
            L1SkillMode._skillMode.put(4515, new DS_AX05());
            L1SkillMode._skillMode.put(4516, new DS_AX06());
            L1SkillMode._skillMode.put(4517, new DS_AX07());
            L1SkillMode._skillMode.put(4518, new DS_AX08());
            L1SkillMode._skillMode.put(4519, new DS_AX09());
            L1SkillMode._skillMode.put(4520, new DS_WX00());
            L1SkillMode._skillMode.put(4521, new DS_WX01());
            L1SkillMode._skillMode.put(4522, new DS_WX02());
            L1SkillMode._skillMode.put(4523, new DS_WX03());
            L1SkillMode._skillMode.put(4524, new DS_WX04());
            L1SkillMode._skillMode.put(4525, new DS_WX05());
            L1SkillMode._skillMode.put(4526, new DS_WX06());
            L1SkillMode._skillMode.put(4527, new DS_WX07());
            L1SkillMode._skillMode.put(4528, new DS_WX08());
            L1SkillMode._skillMode.put(4529, new DS_WX09());
            L1SkillMode._skillMode.put(4530, new DS_ASX00());
            L1SkillMode._skillMode.put(4531, new DS_ASX01());
            L1SkillMode._skillMode.put(4532, new DS_ASX02());
            L1SkillMode._skillMode.put(4533, new DS_ASX03());
            L1SkillMode._skillMode.put(4534, new DS_ASX04());
            L1SkillMode._skillMode.put(4535, new DS_ASX05());
            L1SkillMode._skillMode.put(4536, new DS_ASX06());
            L1SkillMode._skillMode.put(4537, new DS_ASX07());
            L1SkillMode._skillMode.put(4538, new DS_ASX08());
            L1SkillMode._skillMode.put(4539, new DS_ASX09());
            L1SkillMode._skillMode.put(4401, new BS_GX01());
            L1SkillMode._skillMode.put(4402, new BS_GX02());
            L1SkillMode._skillMode.put(4403, new BS_GX03());
            L1SkillMode._skillMode.put(4404, new BS_GX04());
            L1SkillMode._skillMode.put(4405, new BS_GX05());
            L1SkillMode._skillMode.put(4406, new BS_GX06());
            L1SkillMode._skillMode.put(4407, new BS_GX07());
            L1SkillMode._skillMode.put(4408, new BS_GX08());
            L1SkillMode._skillMode.put(4409, new BS_GX09());
            L1SkillMode._skillMode.put(4411, new BS_AX01());
            L1SkillMode._skillMode.put(4412, new BS_AX02());
            L1SkillMode._skillMode.put(4413, new BS_AX03());
            L1SkillMode._skillMode.put(4414, new BS_AX04());
            L1SkillMode._skillMode.put(4415, new BS_AX05());
            L1SkillMode._skillMode.put(4416, new BS_AX06());
            L1SkillMode._skillMode.put(4417, new BS_AX07());
            L1SkillMode._skillMode.put(4418, new BS_AX08());
            L1SkillMode._skillMode.put(4419, new BS_AX09());
            L1SkillMode._skillMode.put(4421, new BS_WX01());
            L1SkillMode._skillMode.put(4422, new BS_WX02());
            L1SkillMode._skillMode.put(4423, new BS_WX03());
            L1SkillMode._skillMode.put(4424, new BS_WX04());
            L1SkillMode._skillMode.put(4425, new BS_WX05());
            L1SkillMode._skillMode.put(4426, new BS_WX06());
            L1SkillMode._skillMode.put(4427, new BS_WX07());
            L1SkillMode._skillMode.put(4428, new BS_WX08());
            L1SkillMode._skillMode.put(4429, new BS_WX09());
            L1SkillMode._skillMode.put(4431, new BS_ASX01());
            L1SkillMode._skillMode.put(4432, new BS_ASX02());
            L1SkillMode._skillMode.put(4433, new BS_ASX03());
            L1SkillMode._skillMode.put(4434, new BS_ASX04());
            L1SkillMode._skillMode.put(4435, new BS_ASX05());
            L1SkillMode._skillMode.put(4436, new BS_ASX06());
            L1SkillMode._skillMode.put(4437, new BS_ASX07());
            L1SkillMode._skillMode.put(4438, new BS_ASX08());
            L1SkillMode._skillMode.put(4439, new BS_ASX09());
            L1SkillMode._skillMode.put(6797, new DRAGON_BLOOD1());
            L1SkillMode._skillMode.put(6798, new DRAGON_BLOOD2());
            L1SkillMode._skillMode.put(6799, new DRAGON_BLOOD3());
         // 戰士技能
            L1SkillMode._skillMode.put(225, new HOWL());
            L1SkillMode._skillMode.put(226, new GIGANTIC());
            L1SkillMode._skillMode.put(228, new POWERGRIP());
//            L1SkillMode._skillMode.put(229, new TOMAHAWK());未新增
//            L1SkillMode._skillMode.put(230, new DESPERADO());未新增
            L1SkillMode._skillMode.put(8911, new DECAY_POTION_DOLL());
            L1SkillMode._skillMode.put(8912, new CONFUSION_DOLL());
        }
        catch (Exception e) {
            L1SkillMode._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public boolean isUseCounterMagic(final int skill_id, final L1Character cha) {
        switch (skill_id) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            case 19:
            case 21:
            case 26:
            case 31:
            case 32:
            case 35:
            case 37:
            case 42:
            case 43:
            case 44:
            case 48:
            case 49:
            case 51:
            case 52:
            case 54:
            case 55:
            case 57:
            case 60:
            case 61:
            case 63:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 75:
            case 78:
            case 79:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 104:
            case 105:
            case 106:
            case 107:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 129:
            case 130:
            case 131:
            case 132:
            case 134:
            case 137:
            case 138:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 154:
            case 155:
            case 156:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 175:
            case 176:
            case 181:
            case 185:
            case 186:
            case 187:
            case 190:
            case 195:
            case 201:
            case 204:
            case 206:
            case 209:
            case 211:
            case 214:
            case 216:
            case 218:
            case 219:
            case 10026:
            case 10027:
            case 10028:
            case 10029: {
                break;
            }
            default: {
                if (cha.hasSkillEffect(31)) {
                    cha.removeSkillEffect(31);
                    final int castgfx = SkillsTable.get().getTemplate(31).getCastGfx2();
                    cha.broadcastPacketAll(new S_SkillSound(cha.getId(), castgfx));
                    if (cha instanceof L1PcInstance) {
                        final L1PcInstance pc = (L1PcInstance)cha;
                        pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
                    }
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public boolean isCantErasEMagic(final int skill_id) {
        switch (skill_id) {
            case 87:
            case 132: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public SkillMode getSkill(final int skillid) {
        return L1SkillMode._skillMode.get(skillid);
    }
}
