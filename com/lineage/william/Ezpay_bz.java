/*    */ package com.lineage.william;
/*    */ 
/*    */ import com.lineage.DatabaseFactory;
import com.lineage.config.ConfigOther;
/*    */ import com.lineage.server.datatables.RecordTable;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Account;
/*    */ import com.lineage.server.world.World;	

/*    */ import java.sql.Connection;
import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.Statement;
import java.sql.Timestamp;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
public class Ezpay_bz {
    public static void getItem(L1PcInstance pc, int count) {
        Connection conn = null;
        final L1Account account = pc.getNetConnection().getAccount();
        try {
            conn = DatabaseFactory.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM ezpay_贊助比值");

            if (rs != null) {
                while (rs.next()) {
                    int itemid = rs.getInt("itemid");
                    int min_money = rs.getInt("min_money");
                    int max_money = rs.getInt("max_money");
                    double itemcountez = rs.getDouble("bz");

                    if (count >= min_money && count <= max_money) {
                        int count2 = (int) (count * itemcountez);
                        pc.getInventory().storeItem(itemid, count2);

                        int pay = account.get_first_pay();
                        int total = count2 + pay;

  
                        RecordTable.get().recordeSponsor(pc.getName(), count, 0, pc.getClanname(), pc.getIp());
                        pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fW獲得贊助幣:" + count2));
                        if (ConfigOther.Sponsorbroad) {
                        /*跑馬燈*/
                        World.get().broadcastPacketToAll(new S_PacketBoxGree(2, "\\f=感謝玩家：【" + pc.getName() + "】贊助：【" + count + "】獲得贊助幣：【" + count2 + "】 累儲：【" + total + "】"));
                        }
                    }
                }
                rs.close();
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
            // 异常处理
        }
    }
}