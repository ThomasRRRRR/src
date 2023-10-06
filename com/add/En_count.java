package com.add;

import com.lineage.DatabaseFactory;
import com.lineage.Server;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.*;
import com.lineage.server.utils.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class En_count {
    private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
    private static boolean BUILD_DATA = false;
    public static final String TOKEN = ",";

    public static void main(String[] a) {
        while (true) {
            try {
                while (true)
                    Server.main(null);
            } catch (Exception exception) {
            }
        }
    }

    public static void forIntensifyArmor(L1PcInstance pc, L1ItemInstance item) {
        ArrayList<Object> aTempData = null;

        if (!BUILD_DATA) {
            BUILD_DATA = true;
            getData();
        }

        for (int i = 0; i < aData.size(); i++) {
            aTempData = aData.get(i);

            if (pc.getArmorCount1() >= ((Integer) aTempData.get(0)).intValue() && pc.getArmorCount1() <= ((Integer) aTempData.get(16)).intValue()) {

                StringBuilder message = new StringBuilder();
                message.append("裝備總加成[").append(pc.getArmorCount1()).append("]賦予下方能力:");

                for (int j = 1; j <= 14; j++) {
                    int value = ((Integer) aTempData.get(j)).intValue();
                    if (value != 0) {
                        addAttribute(pc, j, value);
                        String attributeName = getAttributeName(j);
                        message.append(" (").append(attributeName).append("+ ").append(value).append(")");
                    }
                }

                int remaining_time = 0;
                if (((Integer) aTempData.get(16)).intValue() != 0) {
                    L1PolyMorph.doPoly((L1Character) pc, (Integer) aTempData.get(16), remaining_time, 0);
                }

                pc.sendPackets(new S_SystemMessage(message.toString()));
                updateCharacterStatus(pc);
            }
        }
    }

    public static void forIntensifyArmor1(L1PcInstance pc, L1ItemInstance item) {
        ArrayList<Object> aTempData = null;

        if (!BUILD_DATA) {
            BUILD_DATA = true;
            getData();
        }

        for (int i = 0; i < aData.size(); i++) {
            aTempData = aData.get(i);

            if (pc.getArmorCount1() >= ((Integer) aTempData.get(0)).intValue() && pc.getArmorCount1() <= ((Integer) aTempData.get(16)).intValue()) {
                for (int j = 1; j <= 14; j++) {
                    int value = ((Integer) aTempData.get(j)).intValue();
                    if (value != 0) {
                        removeAttribute(pc, j, value);
                    }
                }

                int remaining_time = 0;
                if (((Integer) aTempData.get(16)).intValue() != 0) {
                    if ((pc.get_sex() == 0) && pc.isCrown()) { // 王族男 還原 王族男
                        L1PolyMorph.doPoly(pc, 0, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isCrown()) { // 王族女 還原 王族女
                        L1PolyMorph.doPoly((L1Character) pc, 1, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isKnight()) { // 騎士男 還原 騎士男
                        L1PolyMorph.doPoly((L1Character) pc, 61, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isKnight()) { // 騎士女 還原 騎士女
                        L1PolyMorph.doPoly((L1Character) pc, 48, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isWizard()) { // 法師男 還原 法師男
                        L1PolyMorph.doPoly((L1Character) pc, 734, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isWizard()) { // 法師女 還原 法師女
                        L1PolyMorph.doPoly((L1Character) pc, 1186, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isElf()) { // 妖精男 還原 妖精男
                        L1PolyMorph.doPoly((L1Character) pc, 138, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isElf()) { // 妖精女 還原 妖精女
                        L1PolyMorph.doPoly((L1Character) pc, 37, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isDarkelf()) { // 黑暗妖精男 還原 黑暗妖精男
                        L1PolyMorph.doPoly((L1Character) pc, 2786, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isDarkelf()) { // 黑暗妖精女 還原 黑暗妖精女
                        L1PolyMorph.doPoly((L1Character) pc, 2796, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isDragonKnight()) { // 龍騎男 還原 龍騎男
                        L1PolyMorph.doPoly((L1Character) pc, 6658, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isDragonKnight()) { // 龍騎女 還原 龍騎女
                        L1PolyMorph.doPoly((L1Character) pc, 6661, remaining_time, 0);
                    } else if ((pc.get_sex() == 0) && pc.isIllusionist()) { // 幻術男 還原 幻術男
                        L1PolyMorph.doPoly((L1Character) pc, 6671, remaining_time, 0);
                    } else if ((pc.get_sex() == 1) && pc.isIllusionist()) { // 幻術女 還原 幻術女
                        L1PolyMorph.doPoly((L1Character) pc, 6650, remaining_time, 0);
                    }
                }

                updateCharacterStatus(pc);
            }
        }
    }

    private static void addAttribute(L1PcInstance pc, int attributeIndex, int value) {
        switch (attributeIndex) {
            case 1: // str
                pc.addStr(value);
                break;
            case 2: // dex
                pc.addDex(value);
                break;
            case 3: // int
                pc.addInt(value);
                break;
            case 4: // con
                pc.addCon(value);
                break;
            case 5: // wis
                pc.addWis(value);
                break;
            case 6: // cha
                pc.addCha(value);
                break;
            case 7: // sp
                pc.addSp(value);
                break;
            case 8: // hp
                pc.addMaxHp(value);
                break;
            case 9: // mp
                pc.addMaxMp(value);
                break;
            case 10: // ReductionDmg
                pc.addother_ReductionDmg(value);
                break;
            case 11: // Mr
                pc.addMr(value);
                break;
            case 12: // Ac
                pc.addAc(value);
                break;
            case 13: // attack
                pc.addDmgup(value);
                break;
            case 14: // bowattack
                pc.addBowDmgup(value);
                break;
        }
    }

    private static void removeAttribute(L1PcInstance pc, int attributeIndex, int value) {
        switch (attributeIndex) {
            case 1: // str
                pc.addStr(-value);
                break;
            case 2: // dex
                pc.addDex(-value);
                break;
            case 3: // int
                pc.addInt(-value);
                break;
            case 4: // con
                pc.addCon(-value);
                break;
            case 5: // wis
                pc.addWis(-value);
                break;
            case 6: // cha
                pc.addCha(-value);
                break;
            case 7: // sp
                pc.addSp(-value);
                break;
            case 8: // hp
                pc.addMaxHp(-value);
                break;
            case 9: // mp
                pc.addMaxMp(-value);
                break;
            case 10: // ReductionDmg
                pc.addother_ReductionDmg(-value);
                break;
            case 11: // Mr
                pc.addMr(-value);
                break;
            case 12: // Ac
                pc.addAc(-value);
                break;
            case 13: // attack
                pc.addDmgup(-value);
                break;
            case 14: // bowattack
                pc.addBowDmgup(-value);
                break;
        }
    }

    private static String getAttributeName(int attributeIndex) {
        switch (attributeIndex) {
            case 1:
                return "力量";
            case 2:
                return "敏捷";
            case 3:
                return "智力";
            case 4:
                return "體質";
            case 5:
                return "精神";
            case 6:
                return "魅力";
            case 7:
                return "魔攻";
            case 8:
                return "血量";
            case 9:
                return "魔量";
            case 10:
                return "減傷";
            case 11:
                return "抗魔";
            case 12:
                return "防禦";
            case 13:
                return "近戰攻擊";
            case 14:
                return "遠戰攻擊";
            default:
                return "";
        }
    }

    private static void updateCharacterStatus(L1PcInstance pc) {
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_OwnCharStatus(pc));
        pc.sendPackets(new S_OwnCharStatus2(pc));
        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
    }

    private static void getData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseFactory.get().getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM w_裝備總加成能力");
            rs = pstmt.executeQuery();
            ArrayList<Object> aReturn = null;
            if (rs != null) {
                while (rs.next()) {
                    aReturn = new ArrayList<>();

                    aReturn.add(0, rs.getInt("EnchantLevel_min"));
                    aReturn.add(1, rs.getInt("str"));
                    aReturn.add(2, rs.getInt("dex"));
                    aReturn.add(3, rs.getInt("int"));
                    aReturn.add(4, rs.getInt("con"));
                    aReturn.add(5, rs.getInt("wis"));
                    aReturn.add(6, rs.getInt("cha"));
                    aReturn.add(7, rs.getInt("sp"));
                    aReturn.add(8, rs.getInt("hp"));
                    aReturn.add(9, rs.getInt("mp"));
                    aReturn.add(10, rs.getInt("ReductionDmg"));
                    aReturn.add(11, rs.getInt("Mr"));
                    aReturn.add(12, rs.getInt("Ac"));
                    aReturn.add(13, rs.getInt("attack"));
                    aReturn.add(14, rs.getInt("bowattack"));
                    aReturn.add(15, rs.getInt("EnchantLevel_max"));
                    aReturn.add(16, rs.getInt("polyid"));

                    aData.add(aReturn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstmt);
            SQLUtil.close(conn);
        }
    }
}
