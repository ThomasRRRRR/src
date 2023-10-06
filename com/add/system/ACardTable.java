/*     */ package com.add.system;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ public class ACardTable
/*     */ {
/*  23 */   private static final Log _log = LogFactory.getLog(ACardTable.class);
/*     */   
/*     */   private static ACardTable _instance;
/*     */   
/*  27 */   private static final Map<Integer, ACard> _cardIndex = new HashMap<>();
/*     */   
/*     */   public static ACardTable get() {
/*  30 */     if (_instance == null) {
/*  31 */       _instance = new ACardTable();
/*     */     }
/*  33 */     return _instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*  40 */     PerformanceTimer timer = new PerformanceTimer();
/*  41 */     Connection cn = null;
/*  42 */     PreparedStatement ps = null;
/*  43 */     ResultSet rs = null;
/*  44 */     int i = 0;
/*     */     try {
/*  46 */       cn = DatabaseFactory.get().getConnection();
/*  47 */       ps = cn.prepareStatement("SELECT * FROM `卡冊變身卡片能力登入`");
/*  48 */       rs = ps.executeQuery();
/*  49 */       while (rs.next()) {
/*  50 */         int id = rs.getInt("流水號");
/*     */         
/*  52 */         String msg1 = rs.getString("獲得能力時的訊息");
/*  53 */         String msg2 = rs.getString("出現顯示能力頁面名稱");
/*  54 */         String cmd = rs.getString("對話檔指令");
/*     */         
/*  56 */         int questid = rs.getInt("任務編號");
/*  57 */         int polyid = rs.getInt("變身編號");
/*  58 */         int polytime = rs.getInt("變身時效");
/*  59 */         int polyitemid = rs.getInt("變身消耗道具編號");
/*  60 */         int polyitemcount = rs.getInt("變身消耗道具數量");
/*     */         
/*  62 */         int str = rs.getInt("力量");
/*  63 */         int dex = rs.getInt("敏捷");
/*  64 */         int con = rs.getInt("體質");
/*  65 */         int Int = rs.getInt("智力");
/*  66 */         int wis = rs.getInt("精神");
/*  67 */         int cha = rs.getInt("魅力");
/*     */         
/*  69 */         int ac = rs.getInt("防禦");
/*  70 */         int hp = rs.getInt("血量");
/*  71 */         int mp = rs.getInt("魔量");
/*  72 */         int hpr = rs.getInt("回血量");
/*  73 */         int mpr = rs.getInt("回魔量");
/*     */         
/*  75 */         int dmg = rs.getInt("近距離傷害");
/*  76 */         int bowdmg = rs.getInt("遠距離傷害");
/*  77 */         int hit = rs.getInt("近距離命中");
/*  78 */         int bowhit = rs.getInt("遠戰攻擊命中");
/*  79 */         int dmgr = rs.getInt("物理減免傷害");
/*     */         
/*  81 */         int mdmgr = rs.getInt("魔法減免傷害");
/*  82 */         int sp = rs.getInt("魔攻");
/*  83 */         int mhit = rs.getInt("魔法命中");
/*  84 */         int mr = rs.getInt("魔法防禦");
/*     */         
/*  86 */         int fire = rs.getInt("火屬性防禦");
/*  87 */         int water = rs.getInt("水屬性防禦");
/*  88 */         int wind = rs.getInt("風屬性防禦");
/*  89 */         int earth = rs.getInt("地屬性防禦");
/*     */ 
/*     */ String hgfx = rs.getString("黑色圖片");
/*     */ String cgfx = rs.getString("彩色圖片");
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
/* 104 */         ACard card = new ACard(
/* 105 */             id, 
/* 106 */             msg1, msg2, 
/* 107 */             cmd, 
/* 108 */             questid, 
/* 109 */             polyid, polytime, polyitemid, polyitemcount, 
/* 110 */             str, dex, con, Int, wis, cha, 
/* 111 */             ac, hp, mp, hpr, mpr, 
/* 112 */             dmg, bowdmg, hit, bowhit, dmgr, 
/* 113 */             mdmgr, sp, mhit, mr, 
/* 114 */             fire, water, wind, earth, hgfx, cgfx);
/*     */         
/* 116 */         _cardIndex.put(Integer.valueOf(id), card);
/* 117 */         i++;
/*     */       } 
/* 119 */     } catch (SQLException e) {
/* 120 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/* 122 */       SQLUtil.close(rs);
/* 123 */       SQLUtil.close(ps);
/* 124 */       SQLUtil.close(cn);
/*     */     } 
/* 126 */     _log.info("讀取->卡冊變身卡片能力登入: " + i + "(" + timer.get() + "ms)");
/*     */   }
/*     */   
/*     */   public int ACardSize() {
/* 130 */     return _cardIndex.size();
/*     */   }
/*     */   
/*     */   public ACard getCard(int id) {
/* 134 */     return _cardIndex.get(Integer.valueOf(id));
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\add\system\ACardTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */