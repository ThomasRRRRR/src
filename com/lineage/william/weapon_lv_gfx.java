/*     */ package com.lineage.william;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.Server;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class weapon_lv_gfx
/*     */ {
/*  27 */   private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
/*     */   private static boolean BUILD_DATA = false;
/*  29 */   private static Random _random = new Random();
/*     */   public static final String TOKEN = ",";
/*     */   
/*     */   public static void main(String[] a) {
/*     */     while (true) {
/*     */       try {
/*     */         while (true)
/*  36 */           Server.main(null);
/*  37 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void forWeapon(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
/*  43 */     ArrayList<Object> aTempData = null;
/*     */ 
/*     */     
/*  46 */     if (!BUILD_DATA) {
/*  47 */       BUILD_DATA = true;
/*  48 */       getData();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     for (int i = 0; i < aData.size(); i++) {
/*     */       
/*  56 */       aTempData = aData.get(i);
/*  57 */       if (weapon_level == ((Integer)aTempData.get(0)).intValue() && item.getItem().getType() == ((Integer)aTempData.get(14)).intValue()) {
/*     */ 
/*     */         
/*  60 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/*  61 */           pc.addDmgup(((Integer)aTempData.get(1)).intValue());
/*  62 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL近距離傷害+ " + ((Integer)aTempData.get(1)).intValue()));
/*     */         } 
/*  64 */         if (((Integer)aTempData.get(2)).intValue() != 0) {
/*  65 */           pc.addBowDmgup(((Integer)aTempData.get(2)).intValue());
/*  66 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL遠距離傷害+ " + ((Integer)aTempData.get(2)).intValue()));
/*     */         } 
/*     */         
/*  69 */         if (((Integer)aTempData.get(3)).intValue() != 0) {
/*  70 */           pc.addHitup(((Integer)aTempData.get(3)).intValue());
/*  71 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL近距離命中+ " + ((Integer)aTempData.get(3)).intValue()));
/*     */         } 
/*  73 */         if (((Integer)aTempData.get(4)).intValue() != 0) {
/*  74 */           pc.addBowHitup(((Integer)aTempData.get(4)).intValue());
/*  75 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL遠距離命中+ " + ((Integer)aTempData.get(4)).intValue()));
/*     */         } 
/*     */         
/*  78 */         if (((Integer)aTempData.get(5)).intValue() != 0) {
/*  79 */           pc.setmagicdmg(((Integer)aTempData.get(5)).intValue());
/*  80 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL魔法增加傷害+ " + ((Integer)aTempData.get(5)).intValue()));
/*     */         } 
/*     */         
/*  83 */         if (((Double)aTempData.get(6)).doubleValue() > 0.0D) {
/*  84 */           pc.addDmgdouble(((Double)aTempData.get(6)).doubleValue());
/*  85 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL物理爆傷增加* " + ((Double)aTempData.get(6)).doubleValue()));
/*     */         } 
/*     */ 
/*     */         
/*  89 */         if (((Integer)aTempData.get(7)).intValue() != 0) {
/*  90 */           pc.addSp(((Integer)aTempData.get(7)).intValue());
/*  91 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL魔攻+ " + ((Integer)aTempData.get(7)).intValue()));
/*     */         } 
/*     */         
/*  94 */         if (((Integer)aTempData.get(8)).intValue() != 0) {
/*  95 */           pc.setweaponran(((Integer)aTempData.get(8)).intValue());
/*     */ 
/*     */           
/*  98 */           if (((Integer)aTempData.get(9)).intValue() != 0 && ((Integer)aTempData.get(10)).intValue() != 0) {
/*  99 */             int type = ((Integer)aTempData.get(10)).intValue() - ((Integer)aTempData.get(9)).intValue();
/* 100 */             pc.setweapondmg(type);
/* 101 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL" + ((Integer)aTempData.get(8)).intValue() + "%機率傷害+" + ((Integer)aTempData.get(9)).intValue() + "~" + ((Integer)aTempData.get(10)).intValue()));
/*     */           } 
/* 103 */           if (((Integer)aTempData.get(11)).intValue() != 0) {
/* 104 */             pc.setweaponchp(((Integer)aTempData.get(11)).intValue());
/* 105 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL" + ((Integer)aTempData.get(8)).intValue() + "%機率吸血+" + ((Integer)aTempData.get(11)).intValue() + "滴"));
/*     */           } 
/* 107 */           if (((Integer)aTempData.get(12)).intValue() != 0) {
/* 108 */             pc.setweaponcmp(((Integer)aTempData.get(12)).intValue());
/* 109 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aL" + ((Integer)aTempData.get(8)).intValue() + "%機率吸血+" + ((Integer)aTempData.get(12)).intValue() + "滴"));
/*     */           } 
/* 111 */           if (((Integer)aTempData.get(13)).intValue() != 0) {
/* 112 */             pc.setweapongfx(((Integer)aTempData.get(13)).intValue());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forWeapon1(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
/* 123 */     ArrayList<Object> aTempData = null;
/*     */ 
/*     */     
/* 126 */     if (!BUILD_DATA) {
/* 127 */       BUILD_DATA = true;
/* 128 */       getData();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     for (int i = 0; i < aData.size(); i++) {
/* 135 */       aTempData = aData.get(i);
/* 136 */       if (weapon_level == ((Integer)aTempData.get(0)).intValue() && item.getItem().getType() == ((Integer)aTempData.get(14)).intValue()) {
/*     */         
/* 138 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/* 139 */           pc.addDmgup(-((Integer)aTempData.get(1)).intValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB近距離傷害- " + ((Integer)aTempData.get(1)).intValue()));
/*     */         }
/*     */         
/* 142 */         if (((Integer)aTempData.get(2)).intValue() != 0) {
/* 143 */           pc.addBowDmgup(-((Integer)aTempData.get(2)).intValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB遠距離傷害- " + ((Integer)aTempData.get(2)).intValue()));
/*     */         }
/*     */ 
/*     */         
/* 147 */         if (((Integer)aTempData.get(3)).intValue() != 0) {
/* 148 */           pc.addHitup(-((Integer)aTempData.get(3)).intValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB近距離命中- " + ((Integer)aTempData.get(3)).intValue()));
/*     */         }
/*     */         
/* 151 */         if (((Integer)aTempData.get(4)).intValue() != 0) {
/* 152 */           pc.addBowHitup(-((Integer)aTempData.get(4)).intValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB遠距離命中- " + ((Integer)aTempData.get(4)).intValue()));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 157 */         if (((Integer)aTempData.get(5)).intValue() != 0) {
/* 158 */           pc.setmagicdmg(0);
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB魔法增加傷害- " + ((Integer)aTempData.get(5)).intValue()));
/*     */         }
/*     */ 
/*     */         
/* 162 */         if (((Double)aTempData.get(6)).doubleValue() > 0.0D) {
/* 163 */           pc.addDmgdouble(-((Double)aTempData.get(6)).doubleValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB物理爆傷增加* " + ((Double)aTempData.get(6)).doubleValue() + "效果消失"));
/*     */         }
/*     */         
/* 166 */         if (((Integer)aTempData.get(7)).intValue() != 0) {
/* 167 */           pc.addSp(-((Integer)aTempData.get(7)).intValue());
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB魔攻- " + ((Integer)aTempData.get(7)).intValue()));
/*     */         }
/*     */ 
/*     */         
/* 171 */         if (((Integer)aTempData.get(8)).intValue() != 0) {
/* 172 */           pc.setweaponran(0);
/*     */ 
/*     */           
/* 175 */           if (((Integer)aTempData.get(9)).intValue() != 0 && ((Integer)aTempData.get(10)).intValue() != 0)
/*     */           {
/* 177 */             pc.setweapondmg(0);
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB" + ((Integer)aTempData.get(8)).intValue() + "%機率傷害+" + ((Integer)aTempData.get(9)).intValue() + "~" + ((Integer)aTempData.get(10)).intValue() + "效果消失"));
/*     */           }
/*     */           
/* 180 */           if (((Integer)aTempData.get(11)).intValue() != 0) {
/* 181 */             pc.setweaponchp(0);
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB" + ((Integer)aTempData.get(8)).intValue() + "%機率吸血+" + ((Integer)aTempData.get(11)).intValue() + "滴 效果消失"));
/*     */           }
/*     */           
/* 184 */           if (((Integer)aTempData.get(12)).intValue() != 0) {
/* 185 */             pc.setweaponcmp(0);
pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aB" + ((Integer)aTempData.get(8)).intValue() + "%機率吸血+" + ((Integer)aTempData.get(12)).intValue() + "滴 效果消失"));
/*     */           }
/*     */           
/* 188 */           if (((Integer)aTempData.get(13)).intValue() != 0) {
/* 189 */             pc.setweapongfx(0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   private static void getData() {
/* 205 */     Connection conn = null;
/* 206 */     PreparedStatement pstmt = null;
/* 207 */     ResultSet rs = null;
/*     */     
/* 209 */     try { conn = DatabaseFactory.get().getConnection();
/* 210 */       pstmt = conn.prepareStatement("SELECT * FROM w_過安定武器");
/* 211 */       rs = pstmt.executeQuery();
/* 212 */       ArrayList<Object> aReturn = null;
/* 213 */       if (rs != null) {
/* 214 */         while (rs.next()) {
/* 215 */           aReturn = new ArrayList();
/*     */ 
/*     */           
/* 218 */           aReturn.add(0, new Integer(rs.getInt("Level")));
/* 219 */           aReturn.add(1, new Integer(rs.getInt("近距離攻擊")));
/* 220 */           aReturn.add(2, new Integer(rs.getInt("遠距離攻擊")));
/* 221 */           aReturn.add(3, new Integer(rs.getInt("近距離命中")));
/* 222 */           aReturn.add(4, new Integer(rs.getInt("遠距離命中")));
/* 223 */           aReturn.add(5, new Integer(rs.getInt("魔法增傷")));
/* 224 */           aReturn.add(6, new Double(rs.getDouble("物理爆擊")));
/* 225 */           aReturn.add(7, new Integer(rs.getInt("魔攻")));
/*     */           
/* 227 */           aReturn.add(8, new Integer(rs.getInt("機率")));
/* 228 */           aReturn.add(9, new Integer(rs.getInt("最小攻擊")));
/* 229 */           aReturn.add(10, new Integer(rs.getInt("最大攻擊")));
/* 230 */           aReturn.add(11, new Integer(rs.getInt("吸血")));
/* 231 */           aReturn.add(12, new Integer(rs.getInt("吸魔")));
/* 232 */           aReturn.add(13, new Integer(rs.getInt("特效")));
/*     */ 			aReturn.add(14, new Integer(rs.getInt("Type")));
/*     */ 
/*     */           
/* 236 */           aData.add(aReturn);
/*     */         } 
/*     */       } }
/* 239 */     catch (SQLException sQLException) {  }
/*     */     finally
/* 241 */     { SQLUtil.close(rs);
/* 242 */       SQLUtil.close(pstmt);
/* 243 */       SQLUtil.close(conn); }
/*     */   
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\william\weapon_lv_gfx.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */