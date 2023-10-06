/*     */ package com.lineage.config;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.serverpackets.S_KillMessage;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxGree;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import com.lineage.server.world.World;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class ConfigKill
/*     */ {
/*  21 */   private static final Log _log = LogFactory.getLog(ConfigKill.class);
/*     */   
/*  23 */   private static final Map<Integer, Kill> KILL_TEXT_LIST = new HashMap<>();
/*     */   private static ConfigKill _instance;
/*  25 */   private static final Random _random = new Random();
/*     */   
/*  27 */   public static int KILLLEVEL = 90;
/*     */   
/*     */   public static ConfigKill get() {
/*  30 */     if (_instance == null) {
/*  31 */       _instance = new ConfigKill();
/*     */     }
/*  33 */     return _instance;
/*     */   }
/*     */   
/*     */   private ConfigKill() {
/*  37 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   private void load() {
/*  42 */     Connection co = null;
/*  43 */     PreparedStatement pm = null;
/*  44 */     ResultSet rs = null;
/*  45 */     int i = 0;
/*     */     try {
/*  47 */       co = DatabaseFactory.get().getConnection();
/*  48 */       pm = co.prepareStatement("SELECT * FROM `message訊息_殺人`");
/*  49 */       rs = pm.executeQuery();
/*  50 */       while (rs.next()) {
/*     */         
/*  52 */         int id = rs.getInt("id");
/*  53 */         int type = rs.getInt("trpe");
/*     */         
/*  55 */         if (id > 5) {
/*  56 */           String message = rs.getString("message");
/*     */           
/*  58 */           Kill msg = new Kill();
/*     */           
/*  60 */           msg._type = type;
/*  61 */           msg._message = message;
/*     */           
/*  63 */           KILL_TEXT_LIST.put(Integer.valueOf(i), msg);
/*  64 */           i++;
/*     */         }
/*     */       
/*     */       } 
/*  68 */     } catch (SQLException e) {
/*  69 */       _log.error("message訊息_殺人", e);
/*     */     } finally {
/*  71 */       SQLUtil.close(rs);
/*  72 */       SQLUtil.close(pm);
/*  73 */       SQLUtil.close(co);
/*     */     } 
/*  75 */     _log.info("message訊息_殺人公告->" + KILL_TEXT_LIST.size());
/*     */   }
/*     */   
/*     */   public void msg(String fpcName, String pcName, String weaponName) {
/*  79 */     if (KILL_TEXT_LIST.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     try {
/*  83 */       int r = _random.nextInt(KILL_TEXT_LIST.size());
/*  84 */       String msg = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._message;
/*  85 */       if (msg != null) {
/*  86 */         int type = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._type;
/*  87 */         String out = null;
/*  88 */         switch (type) {
/*     */           case 0:
/*  90 */             out = String.format(msg, new Object[] { fpcName, pcName });
/*     */             break;
/*     */           case 1:
/*  93 */             out = String.format(msg, new Object[] { pcName, fpcName });
/*     */             break;
/*     */           case 2:
/*  96 */             out = String.format(msg, new Object[] { fpcName, weaponName, pcName });
/*     */             break;
/*     */           case 3:
/*  99 */             out = String.format(msg, new Object[] { pcName, fpcName, weaponName });
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 139 */         if (ConfigOther.killmsg == 0) {
/* 140 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_KillMessage(out));
/*     */         } else {
/* 142 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_PacketBoxGree(2, out));
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 110 */     catch (Exception e) {
/* 111 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   public void msgnoweapon(String fpcName, String pcName, String weaponName) {
/* 115 */     if (KILL_TEXT_LIST.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     try {
/* 119 */       int r = _random.nextInt(KILL_TEXT_LIST.size());
/* 120 */       String msg = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._message;
/* 121 */       if (msg != null) {
/* 122 */         int type = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._type;
/* 123 */         String out = null;
/* 124 */         switch (type) {
/*     */           case 0:
/* 126 */             out = String.format(msg, new Object[] { fpcName, pcName });
/*     */             break;
/*     */           case 1:
/* 129 */             out = String.format(msg, new Object[] { pcName, fpcName });
/*     */             break;
/*     */           case 2:
/* 132 */             out = String.format(msg, new Object[] { fpcName, weaponName, pcName });
/*     */             break;
/*     */           case 3:
/* 135 */             out = String.format(msg, new Object[] { pcName, fpcName, "空手" });
/*     */             break;
/*     */         } 
/*     */         
/* 139 */         if (ConfigOther.killmsg == 0) {
/* 140 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_KillMessage(out));
/*     */         } else {
/* 142 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_PacketBoxGree(2, out));
/*     */         }
/*     */       
/*     */       } 
/* 146 */     } catch (Exception e) {
/* 147 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   public void msg1(String fpcName, String pcName, String weaponName) {
/* 151 */     if (KILL_TEXT_LIST.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     try {
/* 155 */       int r = _random.nextInt(KILL_TEXT_LIST.size());
/* 156 */       String msg = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._message;
/* 157 */       if (msg != null) {
/* 158 */         int type = (KILL_TEXT_LIST.get(Integer.valueOf(r)))._type;
/* 159 */         String out = null;
/* 160 */         switch (type) {
/*     */           case 0:
/* 162 */             out = String.format(msg, new Object[] { "**守護者**", pcName });
/*     */             break;
/*     */           case 1:
/* 165 */             out = String.format(msg, new Object[] { pcName, "**守護者**" });
/*     */             break;
/*     */           case 2:
/* 168 */             out = String.format(msg, new Object[] { "**守護者**", weaponName, pcName });
/*     */             break;
/*     */           case 3:
/* 171 */             out = String.format(msg, new Object[] { pcName, "**守護者**", weaponName });
/*     */             break;
/*     */         } 
/*     */         
/* 175 */         if (ConfigOther.killmsg == 0) {
/* 176 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_KillMessage(out));
/*     */         } else {
/* 178 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_PacketBoxGree(2, out));
/*     */         }
/*     */       
/*     */       } 
/* 182 */     } catch (Exception e) {
/* 183 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class Kill {
/*     */     private int _type;
/*     */     private String _message;
/*     */     
/*     */     private Kill() {}
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\config\ConfigKill.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */