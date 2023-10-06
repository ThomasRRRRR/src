/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.templates.L1Taketreasure;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Taketreasure
/*     */ {
/*  23 */   private static final Log _log = LogFactory.getLog(Taketreasure.class);
/*     */   
/*     */   private static Taketreasure _instance;
/*     */   
/*  27 */   private static final ArrayList<L1Taketreasure> _stealList = new ArrayList<>();
/*     */   
/*     */   public static Taketreasure getInstance() {
/*  30 */     if (_instance == null) {
/*  31 */       _instance = new Taketreasure();
/*     */     }
/*  33 */     return _instance;
/*     */   }
/*     */   
/*     */   public final void load() {
/*  37 */     PerformanceTimer timer = new PerformanceTimer();
/*  38 */     Connection con = null;
/*  39 */     PreparedStatement pstm = null;
/*  40 */     ResultSet rs = null;
/*     */     try {
/*  42 */       con = DatabaseFactory.get().getConnection();
/*  43 */       pstm = con.prepareStatement("SELECT * FROM 系統_敵人死亡奪寶");
/*  44 */       rs = pstm.executeQuery();
/*     */       
/*  46 */       while (rs.next()) {
/*  47 */         int item_id = rs.getInt("道具");
/*  48 */         L1Item temp = ItemTable.get().getTemplate(item_id);
/*  49 */         if (temp == null) {
/*  50 */           del_box(item_id);
/*     */           
/*     */           continue;
/*     */         } 
/*  54 */         int level = rs.getInt("等級");
/*  55 */         int steal_chance = rs.getInt("奪取機率");
/*  56 */         int min_steal_count = rs.getInt("奪取最低數量");
/*  57 */         int max_steal_count = rs.getInt("奪取最高數量");
/*  58 */         int drop_on_floor = rs.getInt("奪取類型");
/*  59 */         int anti_steal_item_id = rs.getInt("防止奪取道具編號");
/*  60 */         String dropmsg = rs.getString("廣播1");
/*  61 */         String dropmsg1 = rs.getString("廣播2");
/*  62 */         String dropmsg2 = rs.getString("廣播3");
/*  63 */         int itemid = rs.getInt("額外獲得道具");
/*  64 */         int itemcount = rs.getInt("額外獲得道具數量");
/*  65 */         int deaditemid = rs.getInt("額外噴道具");
/*  66 */         int deaditemcount = rs.getInt("額外噴道具數量");
int isEquipped = rs.getInt("是否需要裝備");
int ditemid = rs.getInt("被殺者獲得道具");
int dcount = rs.getInt("被殺者獲得數量");
/*     */ 
/*     */         
/*  69 */         L1Taketreasure itemSteal = new L1Taketreasure(item_id, level, steal_chance, min_steal_count, 
/*  70 */             max_steal_count, drop_on_floor, 
/*  71 */             anti_steal_item_id, dropmsg, dropmsg1, dropmsg2, itemid, itemcount, deaditemid, deaditemcount, isEquipped, ditemid, dcount);
/*     */ 
/*     */         
/*  74 */         _stealList.add(itemSteal);
/*     */       }
/*     */     
/*  77 */     } catch (SQLException e) {
/*  78 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/*  81 */       SQLUtil.close(rs);
/*  82 */       SQLUtil.close(pstm);
/*  83 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void del_box(int id) {
/*  89 */     Connection cn = null;
/*  90 */     PreparedStatement ps = null;
/*     */     try {
/*  92 */       cn = DatabaseFactory.get().getConnection();
/*  93 */       ps = cn.prepareStatement("DELETE FROM `系統_敵人死亡奪寶` WHERE `道具`=?");
/*  94 */       ps.setInt(1, id);
/*  95 */       ps.execute();
/*     */     }
/*  97 */     catch (SQLException e) {
/*  98 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 101 */       SQLUtil.close(ps);
/* 102 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */   public final ArrayList<L1Taketreasure> getList() {
/* 106 */     return _stealList;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\Taketreasure.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */