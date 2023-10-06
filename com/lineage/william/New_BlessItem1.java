/*     */ package com.lineage.william;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.Server;
import com.lineage.config.ConfigOther;
/*     */ import com.lineage.server.datatables.sql.CharItemsTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;

/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class New_BlessItem1
/*     */ {
/*  31 */   private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
/*     */   private static boolean BUILD_DATA = false;
/*  33 */   private static Random _random = new Random();
/*     */   public static final String TOKEN = ",";
/*     */   
/*     */   public static void main(String[] a) {
/*     */     while (true) {
/*     */       try {
/*     */         while (true)
/*  40 */           Server.main(null);
/*  41 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void forIntensifyArmor(L1PcInstance pc, L1ItemInstance item) {
/*  47 */     ArrayList<Object> aTempData = null;
/*     */ 
/*     */ 
/*     */     
/*  51 */     if (!BUILD_DATA) {
/*  52 */       BUILD_DATA = true;
/*  53 */       getData();
/*     */     } 
/*     */     
/*  56 */     for (int i = 0; i < aData.size(); i++) {
/*  57 */       aTempData = aData.get(i);
/*  57 */
/*  57 */
/*  57 */
/*  57 */
/*     */ 
/*  60 */     if (((Integer)aTempData.get(17)).intValue() == 2 && item.getItem().getType2() == 2)
/*     */       {
/*  62 */         if ((((Integer)aTempData.get(0)).intValue() == 2 && item.getItem().getUseType() == 2) || ((
/*  63 */           (Integer)aTempData.get(0)).intValue() == 25 && item.getItem().getUseType() == 25) || ((
/*  64 */           (Integer)aTempData.get(0)).intValue() == 18 && item.getItem().getUseType() == 18) || ((
/*  65 */           (Integer)aTempData.get(0)).intValue() == 19 && item.getItem().getUseType() == 19) || ((
/*  66 */           (Integer)aTempData.get(0)).intValue() == 20 && item.getItem().getUseType() == 20) || ((
/*  67 */           (Integer)aTempData.get(0)).intValue() == 21 && item.getItem().getUseType() == 21) || ((
/*  68 */           (Integer)aTempData.get(0)).intValue() == 22 && item.getItem().getUseType() == 22) || ((
/*  69 */           (Integer)aTempData.get(0)).intValue() == 23 && item.getItem().getUseType() == 23) || ((
/*  70 */           (Integer)aTempData.get(0)).intValue() == 23 && item.getItem().getUseType() == 23) || ((
/*  71 */           (Integer)aTempData.get(0)).intValue() == 24 && item.getItem().getUseType() == 24) || ((
/*  72 */           (Integer)aTempData.get(0)).intValue() == 37 && item.getItem().getUseType() == 37) || ((
/*  73 */           (Integer)aTempData.get(0)).intValue() == 40 && item.getItem().getUseType() == 40) || ((
/*  74 */           (Integer)aTempData.get(0)).intValue() == 47 && item.getItem().getUseType() == 47))
				{
/*  75 */           item.setItemAttack(item.getItemAttack() + ((Integer)aTempData.get(1)).intValue());
/*  76 */           item.setItemHit(item.getItemHit() + ((Integer)aTempData.get(2)).intValue());
/*  77 */           item.setItemSp(item.getItemSp() + ((Integer)aTempData.get(3)).intValue());
/*  78 */           item.setItemStr(item.getItemStr() + ((Integer)aTempData.get(4)).intValue());
/*  79 */           item.setItemDex(item.getItemDex() + ((Integer)aTempData.get(5)).intValue());
/*  80 */           item.setItemInt(item.getItemInt() + ((Integer)aTempData.get(6)).intValue());
/*  81 */           item.setItemCon(item.getItemCon() + ((Integer)aTempData.get(7)).intValue());
/*  82 */           item.setItemWis(item.getItemWis() + ((Integer)aTempData.get(8)).intValue());
/*  83 */           item.setItemCha(item.getItemCha() + ((Integer)aTempData.get(9)).intValue());
/*  84 */           item.setItemHp(item.getItemHp() + ((Integer)aTempData.get(10)).intValue());
/*  85 */           item.setItemMp(item.getItemMp() + ((Integer)aTempData.get(11)).intValue());
/*  86 */           item.setItemMr(item.getItemMr() + ((Integer)aTempData.get(12)).intValue());
/*  87 */           item.setItemReductionDmg(item.getItemReductionDmg() + ((Integer)aTempData.get(13)).intValue());
/*  88 */           item.setItemHpr(item.getItemHpr() + ((Integer)aTempData.get(14)).intValue());
/*  89 */           item.setItemMpr(item.getItemMpr() + ((Integer)aTempData.get(15)).intValue());
/*  90 */           item.setItemhppotion(item.getItemhppotion() + ((Integer)aTempData.get(16)).intValue());
/*  91 */           item.setItemAc(item.getItemAc() + ((Integer)aTempData.get(19)).intValue());
item.setItemBowAttack(item.getItemBowAttack() + ((Integer)aTempData.get(20)).intValue());
/*  92 */           if (!pc.isGm()) {
/*  93 */             World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String)aTempData.get(18), new Object[] { pc.getName(), item.getLogName() })));
/*     */           }
/*     */ 
/*     */           
/*  97 */           CharItemsTable cit = new CharItemsTable();
/*     */           try {
/*  99 */             cit.updateItemAttack(item);
/* 100 */             cit.updateItemHit(item);
/* 101 */             cit.updateItemAc(item);
/* 102 */             cit.updateItemSp(item);
/* 103 */             cit.updateItemStr(item);
/* 104 */             cit.updateItemDex(item);
/* 105 */             cit.updateItemInt(item);
/* 106 */             cit.updateItemCon(item);
/* 107 */             cit.updateItemWis(item);
/* 108 */             cit.updateItemCha(item);
/* 109 */             cit.updateItemHp(item);
/* 110 */             cit.updateItemMp(item);
/* 111 */             cit.updateItemMr(item);
/* 112 */             cit.updateItemReductionDmg(item);
/* 113 */             cit.updateItemHpr(item);
/* 114 */             cit.updateItemMpr(item);
/* 115 */             cit.updateItemhppotion(item);
/* 116 */             cit.updateBless(item);
cit.updateItemBowAttack(item);
/* 117 */           } catch (Exception e) {
/* 118 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */   }
/*     */  }
/*     */   
/*     */   public static void forIntensifyweapon(L1PcInstance pc, L1ItemInstance item) {
/* 127 */     ArrayList<Object> aTempData = null;
/*     */ 
/*     */ 
/*     */     
/* 131 */     if (!BUILD_DATA) {
/* 132 */       BUILD_DATA = true;
/* 133 */       getData();
/*     */     } 
/*     */     
/* 136 */     for (int i = 0; i < aData.size(); i++) {
/* 137 */       aTempData = aData.get(i);

/* 140 */       if (((Integer)aTempData.get(17)).intValue() == 1 && item.getItem().getType2() == 1 && (((
/* 141 */         (Integer)aTempData.get(0)).intValue() == 1 && item.getItem().getType() == 1) || ((
/* 142 */         (Integer)aTempData.get(17)).intValue() == 1 && ((Integer)aTempData.get(0)).intValue() == 2 && item.getItem().getType() == 2) || ((
/* 143 */         (Integer)aTempData.get(0)).intValue() == 3 && item.getItem().getType() == 3) || ((
/* 144 */         (Integer)aTempData.get(0)).intValue() == 15 && item.getItem().getType() == 15) || ((
/* 145 */         (Integer)aTempData.get(0)).intValue() == 5 && item.getItem().getType() == 5) || ((
/* 146 */         (Integer)aTempData.get(0)).intValue() == 6 && item.getItem().getType() == 6) || ((
/* 147 */         (Integer)aTempData.get(0)).intValue() == 14 && item.getItem().getType() == 14) || ((
/* 148 */         (Integer)aTempData.get(0)).intValue() == 7 && item.getItem().getType() == 7) || ((
/* 149 */         (Integer)aTempData.get(0)).intValue() == 16 && item.getItem().getType() == 16) || ((
/* 150 */         (Integer)aTempData.get(0)).intValue() == 11 && item.getItem().getType() == 11) || ((
/* 151 */         (Integer)aTempData.get(0)).intValue() == 12 && item.getItem().getType() == 12) || ((
/* 152 */         (Integer)aTempData.get(0)).intValue() == 4 && item.getItem().getType() == 4) || ((
/* 153 */         (Integer)aTempData.get(0)).intValue() == 13 && item.getItem().getType() == 13) || ((
/* 154 */         (Integer)aTempData.get(0)).intValue() == 11 && item.getItem().getType() == 11) || ((
/* 155 */         (Integer)aTempData.get(0)).intValue() == 12 && item.getItem().getType() == 12) || ((
/* 156 */         (Integer)aTempData.get(0)).intValue() == 17 && item.getItem().getType() == 17) || ((
/* 157 */         (Integer)aTempData.get(17)).intValue() == 1 && ((Integer)aTempData.get(0)).intValue() == 18 && item.getItem().getType() == 18))) 
{
/* 158 */         item.setItemAttack(item.getItemAttack() + ((Integer)aTempData.get(1)).intValue());
/* 159 */         item.setItemHit(item.getItemHit() + ((Integer)aTempData.get(2)).intValue());
/* 160 */         item.setItemSp(item.getItemSp() + ((Integer)aTempData.get(3)).intValue());
/* 161 */         item.setItemStr(item.getItemStr() + ((Integer)aTempData.get(4)).intValue());
/* 162 */         item.setItemDex(item.getItemDex() + ((Integer)aTempData.get(5)).intValue());
/* 163 */         item.setItemInt(item.getItemInt() + ((Integer)aTempData.get(6)).intValue());
/* 164 */         item.setItemCon(item.getItemCon() + ((Integer)aTempData.get(7)).intValue());
/* 165 */         item.setItemWis(item.getItemWis() + ((Integer)aTempData.get(8)).intValue());
/* 166 */         item.setItemCha(item.getItemCha() + ((Integer)aTempData.get(9)).intValue());
/* 167 */         item.setItemHp(item.getItemHp() + ((Integer)aTempData.get(10)).intValue());
/* 168 */         item.setItemMp(item.getItemMp() + ((Integer)aTempData.get(11)).intValue());
/* 169 */         item.setItemMr(item.getItemMr() + ((Integer)aTempData.get(12)).intValue());
/* 170 */         item.setItemReductionDmg(item.getItemReductionDmg() + ((Integer)aTempData.get(13)).intValue());
/* 171 */         item.setItemHpr(item.getItemHpr() + ((Integer)aTempData.get(14)).intValue());
/* 172 */         item.setItemMpr(item.getItemMpr() + ((Integer)aTempData.get(15)).intValue());
/* 173 */         item.setItemAc(item.getItemAc() + ((Integer)aTempData.get(19)).intValue());
/* 174 */         item.setItemhppotion(item.getItemhppotion() + ((Integer)aTempData.get(16)).intValue());
item.setItemBowAttack(item.getItemBowAttack() + ((Integer)aTempData.get(20)).intValue());
/*     */ 
/*     */         
/* 177 */         if (!pc.isGm()) {
/* 178 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String)aTempData.get(18), new Object[] { pc.getName(), item.getLogName() })));
/*     */         }
/* 180 */         CharItemsTable cit = new CharItemsTable();
/*     */         try {
/* 182 */           cit.updateItemAttack(item);
/* 183 */           cit.updateItemHit(item);
/* 184 */           cit.updateItemSp(item);
/* 185 */           cit.updateItemStr(item);
/* 186 */           cit.updateItemDex(item);
/* 187 */           cit.updateItemInt(item);
/* 188 */           cit.updateItemCon(item);
/* 189 */           cit.updateItemAc(item);
/* 190 */           cit.updateItemWis(item);
/* 191 */           cit.updateItemCha(item);
/* 192 */           cit.updateItemHp(item);
/* 193 */           cit.updateItemMp(item);
/* 194 */           cit.updateItemMr(item);
/* 195 */           cit.updateItemReductionDmg(item);
/* 196 */           cit.updateItemHpr(item);
/* 197 */           cit.updateItemMpr(item);
/* 198 */           cit.updateBless(item);
/* 199 */           cit.updateItemhppotion(item);
cit.updateItemBowAttack(item);
/* 200 */         } catch (Exception e) {
/* 201 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */     }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cleanall(L1PcInstance pc, L1ItemInstance item) {
/* 210 */     ArrayList<Object> aTempData = null;
/*     */ 
/*     */ 
/*     */     
/* 214 */     if (!BUILD_DATA) {
/* 215 */       BUILD_DATA = true;
/* 216 */       getData();
/*     */     } 
/*     */     
/* 219 */     for (int i = 0; i < aData.size(); i++) {
/* 220 */       aTempData = aData.get(i);
/*     */ 
/*     */       
/* 223 */       item.setItemAttack(0);
/* 224 */       item.setItemHit(0);
/* 225 */       item.setItemSp(0);
/* 226 */       item.setItemStr(0);
/* 227 */       item.setItemDex(0);
/* 228 */       item.setItemInt(0);
/* 229 */       item.setItemCon(0);
/* 230 */       item.setItemWis(0);
/* 231 */       item.setItemCha(0);
/* 232 */       item.setItemHp(0);
/* 233 */       item.setItemMp(0);
/* 234 */       item.setItemMr(0);
/* 235 */       item.setItemReductionDmg(0);
/* 236 */       item.setItemHpr(0);
/* 237 */       item.setItemMpr(0);
/* 238 */       item.setItemhppotion(0);
/* 239 */       item.setItemAc(0);
item.setItemBowAttack(0);
/*     */ 
/*     */ 
/*     */       
/* 243 */       CharItemsTable cit = new CharItemsTable();
/*     */       try {
/* 245 */         cit.updateItemAttack(item);
/* 246 */         cit.updateItemHit(item);
/* 247 */         cit.updateItemAc(item);
/* 248 */         cit.updateItemSp(item);
/* 249 */         cit.updateItemStr(item);
/* 250 */         cit.updateItemDex(item);
/* 251 */         cit.updateItemInt(item);
/* 252 */         cit.updateItemCon(item);
/* 253 */         cit.updateItemWis(item);
/* 254 */         cit.updateItemCha(item);
/* 255 */         cit.updateItemHp(item);
/* 256 */         cit.updateItemMp(item);
/* 257 */         cit.updateItemMr(item);
/* 258 */         cit.updateItemReductionDmg(item);
/* 259 */         cit.updateItemHpr(item);
/* 260 */         cit.updateItemMpr(item);
/* 261 */         cit.updateItemhppotion(item);
/* 262 */         cit.updateBless(item);
cit.updateItemBowAttack(item);
/* 263 */       } catch (Exception e) {
/* 264 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void getData() {
/* 275 */     Connection con = null;
/*     */     try {
/* 277 */       con = DatabaseFactory.get().getConnection();
/* 278 */       Statement stat = con.createStatement();
/* 279 */       ResultSet rs = stat.executeQuery("SELECT * FROM w_物品祝福系統稀有");
/* 280 */       ArrayList<Object> aReturn = null;
/*     */       
/* 282 */       if (rs != null) {
/* 283 */         while (rs.next()) {
/* 284 */           aReturn = new ArrayList();
/*     */ 
/*     */           
/* 287 */           aReturn.add(0, new Integer(rs.getInt("item_type")));
/* 288 */           aReturn.add(1, new Integer(rs.getInt("Attack")));
/* 289 */           aReturn.add(2, new Integer(rs.getInt("Hit")));
/* 290 */           aReturn.add(3, new Integer(rs.getInt("Sp")));
/* 291 */           aReturn.add(4, new Integer(rs.getInt("Str")));
/* 292 */           aReturn.add(5, new Integer(rs.getInt("Dex")));
/* 293 */           aReturn.add(6, new Integer(rs.getInt("Int")));
/* 294 */           aReturn.add(7, new Integer(rs.getInt("Con")));
/* 295 */           aReturn.add(8, new Integer(rs.getInt("Wis")));
/* 296 */           aReturn.add(9, new Integer(rs.getInt("Cha")));
/* 297 */           aReturn.add(10, new Integer(rs.getInt("Hp")));
/* 298 */           aReturn.add(11, new Integer(rs.getInt("Mp")));
/* 299 */           aReturn.add(12, new Integer(rs.getInt("Mr")));
/* 300 */           aReturn.add(13, new Integer(rs.getInt("ReductionDmg")));
/* 301 */           aReturn.add(14, new Integer(rs.getInt("Hpr")));
/* 302 */           aReturn.add(15, new Integer(rs.getInt("Mpr")));
/* 303 */           aReturn.add(16, new Integer(rs.getInt("hppotion")));
/* 304 */           aReturn.add(17, new Integer(rs.getInt("type")));
/* 305 */           aReturn.add(18, rs.getString("All_message"));
/* 306 */           aReturn.add(19, new Integer(rs.getInt("ac")));   
aReturn.add(20, new Integer(rs.getInt("BowAttack"))); 
/* 308 */           aData.add(aReturn);
/*     */         } 
/*     */       }
/* 311 */       stat.close();
/* 312 */       rs.close();
/* 313 */       if (con != null && !con.isClosed())
/* 314 */         con.close(); 
/* 315 */     } catch (SQLException sQLException) {}
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\william\New_BlessItem1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */