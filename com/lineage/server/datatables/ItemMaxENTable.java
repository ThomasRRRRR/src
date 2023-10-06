/*    */ package com.lineage.server.datatables;
/*    */ 
/*    */ import com.lineage.DatabaseFactory;
/*    */ import com.lineage.server.utils.PerformanceTimer;
/*    */ import com.lineage.server.utils.SQLUtil;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemMaxENTable
/*    */ {
/* 18 */   private static final Log _log = LogFactory.getLog(ItemMaxENTable.class);
/*    */   private static ItemMaxENTable _instance;
/* 20 */   public static final ArrayList<Integer> RESTRICTIONS = new ArrayList<>();
/* 20 */   public static final ArrayList<Integer> RESTRICTIONS1 = new ArrayList<>(); 
/*    */   public static ItemMaxENTable get() {
/* 23 */     if (_instance == null) {
/* 24 */       _instance = new ItemMaxENTable();
/*    */     }
/* 26 */     return _instance;
/*    */   }
/*    */   
/*    */   public void load() {
/* 30 */     PerformanceTimer timer = new PerformanceTimer();
/* 31 */     Connection con = null;
/* 32 */     PreparedStatement pstm = null;
/* 33 */     ResultSet rs = null;
/*    */     try {
/* 35 */       con = DatabaseFactory.get().getConnection();
/* 36 */       pstm = con.prepareStatement("SELECT * FROM `w_裝備強化上限`");
/* 37 */       rs = pstm.executeQuery();
/* 38 */       while (rs.next()) {
/* 39 */         int itemid = rs.getInt("itemid");
/* 39 */         int enmax = rs.getInt("enmax");
/* 40 */         RESTRICTIONS.add(Integer.valueOf(itemid));
/* 40 */         RESTRICTIONS1.add(Integer.valueOf(enmax));
/*    */       } 
/* 42 */     } catch (SQLException e) {
/* 43 */       _log.error(e.getLocalizedMessage(), e);
/*    */     } finally {
/*    */       
/* 46 */       SQLUtil.close(rs);
/* 47 */       SQLUtil.close(pstm);
/* 48 */       SQLUtil.close(con);
/*    */     } 
/* 50 */     _log.info("載入強化上限道具: " + RESTRICTIONS.size() + "(" + timer.get() + "ms)");
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\ItemNoENTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */