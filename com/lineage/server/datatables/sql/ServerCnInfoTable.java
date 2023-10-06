/*    */ package com.lineage.server.datatables.sql;
/*    */ 
/*    */ import com.lineage.DatabaseFactory;
/*    */ import com.lineage.server.datatables.ItemTable;
/*    */ import com.lineage.server.datatables.storage.ServerCnInfoStorage;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.templates.L1Item;
/*    */ import com.lineage.server.utils.SQLUtil;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Timestamp;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class ServerCnInfoTable
/*    */   implements ServerCnInfoStorage
/*    */ {
/* 19 */   private static final Log _log = LogFactory.getLog(ServerCnInfoTable.class);
/*    */ 
/*    */   
/*    */   public void create(L1PcInstance pc, L1Item itemtmp, long count, boolean mode, int itemid_cn) {
/* 23 */     Connection con = null;
/* 24 */     PreparedStatement pstm = null;
/*    */     try {
/* 26 */       Timestamp lastactive = new Timestamp(System.currentTimeMillis());
/*    */       
/* 28 */       con = DatabaseFactory.get().getConnection();
/* 29 */       String sqlstr = 
/* 30 */         "INSERT INTO `other_cn_shop` SET `itemname`=?,`itemid`=?,`selling_price`=?,`time`=?,`pcobjid`=?,`mode`=?";
/*    */       
/* 32 */       pstm = con.prepareStatement(sqlstr);
/* 33 */       int i = 0;
/* 34 */       String pcinfo = "(玩家)";
/* 35 */       if (pc.isGm()) {
/* 36 */         pcinfo = "(管理者)";
/*    */       }
/* 38 */       L1Item temp = ItemTable.get().getTemplate(itemid_cn);
/*    */       
/* 40 */       pstm.setString(++i, String.valueOf(itemtmp.getName()) + pcinfo + "-(" + temp.getName() + ")");
/* 41 */       pstm.setInt(++i, itemtmp.getItemId());
/* 42 */       pstm.setLong(++i, count);
/* 43 */       pstm.setTimestamp(++i, lastactive);
/* 44 */       pstm.setInt(++i, pc.getId());
/* 45 */       pstm.setBoolean(++i, mode);
/*    */       
/* 47 */       pstm.execute();
/*    */       
///* 49 */       if (mode) {
///* 50 */         _log.info("建立商城紀錄 人物:" + pc.getName() + " 買入:" + itemtmp.getName() + " 花費商城幣:" + count);
///*    */       } else {
///* 52 */         _log.info("建立商城紀錄 人物:" + pc.getName() + " 賣出:" + itemtmp.getName() + " 獲得商城幣:" + count);
///*    */       }
/*    */     
/*    */     }
/* 56 */     catch (SQLException e) {
/* 57 */       _log.error(e.getLocalizedMessage(), e);
/*    */     } finally {
/*    */       
/* 60 */       SQLUtil.close(pstm);
/* 61 */       SQLUtil.close(con);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\ServerCnInfoTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */