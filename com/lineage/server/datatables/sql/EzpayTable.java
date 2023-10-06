/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactoryLogin;
/*     */ import com.lineage.server.datatables.storage.EzpayStorage;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EzpayTable
/*     */   implements EzpayStorage
/*     */ {
/*  23 */   private static final Log _log = LogFactory.getLog(EzpayTable.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, int[]> ezpayInfo(String loginName) {
/*  33 */     Connection co = null;
/*  34 */     PreparedStatement ps = null;
/*  35 */     ResultSet rs = null;
/*  36 */     Map<Integer, int[]> list = (Map)new HashMap<>();
/*     */     try {
/*  38 */       co = DatabaseFactoryLogin.get().getConnection();
/*  39 */       String sqlstr = "SELECT * FROM `w_players_get_instant` WHERE `accounts`=? ORDER BY `id`";
/*  40 */       ps = co.prepareStatement(sqlstr);
/*  41 */       ps.setString(1, loginName.toLowerCase());
/*  42 */       rs = ps.executeQuery();
/*     */       
/*  44 */       while (rs.next()) {
/*  45 */         int[] value = new int[3];
/*  46 */         int out = rs.getInt("isget");
/*  47 */         int ready = rs.getInt("hand");
/*  48 */         if (out == 0 && ready == 1) {
/*  49 */           int key = rs.getInt("id");
/*  50 */           int p_id = rs.getInt("itemid");
/*  51 */           int count = rs.getInt("count");
/*  52 */           value[0] = key;
/*  53 */           value[1] = p_id;
/*  54 */           value[2] = count;
/*     */           
/*  56 */           list.put(Integer.valueOf(key), value);
/*     */         } 
/*     */       } 
/*  59 */     } catch (Exception e) {
/*  60 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*  62 */       SQLUtil.close(ps);
/*  63 */       SQLUtil.close(co);
/*  64 */       SQLUtil.close(rs);
/*     */     } 
/*  66 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean is_holding(String loginName, int id) {
/*  79 */     Connection co = null;
/*  80 */     PreparedStatement ps = null;
/*  81 */     ResultSet rs = null;
/*     */     try {
/*  83 */       co = DatabaseFactoryLogin.get().getConnection();
/*  84 */       String sqlstr = "SELECT * FROM `w_players_get_instant` WHERE `accounts`=? AND `id`=?";
/*  85 */       ps = co.prepareStatement(sqlstr);
/*  86 */       ps.setString(1, loginName.toLowerCase());
/*  87 */       ps.setInt(2, id);
/*  88 */       rs = ps.executeQuery();
/*     */       
/*  90 */       while (rs.next()) {
/*  91 */         int out = rs.getInt("isget");
/*  92 */         if (out != 0) {
/*  93 */           return false;
/*     */         }
/*     */       } 
/*  96 */     } catch (Exception e) {
/*  97 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*  99 */       SQLUtil.close(ps);
/* 100 */       SQLUtil.close(co);
/* 101 */       SQLUtil.close(rs);
/*     */     } 
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean update(String loginName, int id, String pcname, String ip) {
/* 119 */     if (!is_holding(loginName, id)) {
/* 120 */       return false;
/*     */     }
/*     */     
/* 123 */     Connection con = null;
/* 124 */     PreparedStatement pstm = null;
/*     */     try {
/* 126 */       Timestamp lastactive = new Timestamp(System.currentTimeMillis());
/*     */       
/* 128 */       con = DatabaseFactoryLogin.get().getConnection();
/* 129 */       String sqlstr = "UPDATE `w_players_get_instant` SET `isget`=1,`getpalyer`=?,`time`=?,`ip`=? WHERE `id`=? AND `accounts`=?";
/* 130 */       pstm = con.prepareStatement(sqlstr);
/*     */       
/* 132 */       pstm.setString(1, pcname);
/* 133 */       pstm.setTimestamp(2, lastactive);
/* 134 */       pstm.setString(3, ip);
/*     */       
/* 136 */       pstm.setInt(4, id);
/* 137 */       pstm.setString(5, loginName);
/*     */       
/* 139 */       pstm.execute();
/* 140 */       return true;
/* 141 */     } catch (Exception e) {
/* 142 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/* 144 */       SQLUtil.close(pstm);
/* 145 */       SQLUtil.close(con);
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\EzpayTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */