package com.lineage.william;

import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/* loaded from: C:\Users\ADMINI~1\AppData\Local\Temp\jadx-702613068644786864\classes.dex */
public class Npc_BuyPet1 {
    public static final String TOKEN = ",";
    private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
    private static boolean NO_GET_DATA = false;

    public static boolean forNpcQuest(String s, L1PcInstance pc) {
        if (!NO_GET_DATA) {
            NO_GET_DATA = true;
            getData();
        }
        int i = 0;
        while (true) {
            if (i >= aData.size()) {
                break;
            }
            ArrayList aTempData = aData.get(i);
            Timestamp end_time = (Timestamp) aTempData.get(6);
            Timestamp current_time = new Timestamp(System.currentTimeMillis());
            if (aTempData.get(0) != null && ((String) aTempData.get(0)).equals(s)) {
                if (pc.getInventory().getSize() >= 160) {
                    pc.sendPackets(new S_ServerMessage("角色身上攜帶超過160個道具,功能無效。"));
                    break;
                } else if (pc.getQuest().get_step(((Integer) aTempData.get(3)).intValue()) == 1) {
                    pc.sendPackets(new S_SystemMessage("該角色已領取此禮包無法重複領取"));
                    break;
                } else if (pc.getLevel() < (((Integer) aTempData.get(5)).intValue())) {
                    pc.sendPackets(new S_SystemMessage("角色未達可領取等級"));
                    break;
                } else if (pc.getLevel() < (((Integer) aTempData.get(5)).intValue())) {
                    pc.sendPackets(new S_SystemMessage("角色未達可領取等級"));
                    break;
                } else if (end_time != null && current_time.after(end_time)) {
                    pc.sendPackets(new S_SystemMessage("已超過領取截止時間，無法領取。"));
                    break;
                }
                
                  else {
                    pc.getInventory().storeItem(((Integer) aTempData.get(1)).intValue(), (long) ((Integer) aTempData.get(2)).intValue());
                    pc.getQuest().set_step(((Integer) aTempData.get(3)).intValue(), 1);
                    if (((String) aTempData.get(4)) != null) {
                        pc.sendPackets(new S_SystemMessage("\\fY" + ((String) aTempData.get(4))));
                    }
                }
            }
            i++;
        }
        return false;
    }

    private static void getData() {
        try {
            Connection con = DatabaseFactory.get().getConnection();
            ResultSet rset = con.createStatement().executeQuery("SELECT * FROM 系統_輸入禮包碼");
            if (rset != null) {
                while (rset.next()) {
                    ArrayList<Object> aReturn = new ArrayList<>();
                    aReturn.add(0, rset.getString("禮包碼"));
                    aReturn.add(1, new Integer(rset.getInt("給予道具")));
                    aReturn.add(2, new Integer(rset.getInt("給予道具數量")));
                    aReturn.add(3, new Integer(rset.getInt("紀錄領取編號")));
                    aReturn.add(4, rset.getString("顯示文字"));
                    aReturn.add(5, new Integer(rset.getInt("等級")));       
                    aReturn.add(6, rset.getTimestamp("領取截止")); // 新增領取截止時間的資料到 aReturn
                    aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
        }
    }
}