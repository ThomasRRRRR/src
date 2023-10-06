/*     */ package com.lineage.william;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.world.World;

/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemAction
/*     */ {
/*  40 */   private static ArrayList aData = new ArrayList<>();
/*  41 */   public static final HashMap _questionIdIndex = new HashMap();
/*     */ 
/*     */   
/*     */   private static boolean NO_GET_DATA = false;
/*     */ 
/*     */   
/*     */   public static final String TOKEN = ",";
/*     */   
/*     */   private static ItemAction _instance;
/*     */ 
/*     */   
/*     */   public static ItemAction getInstance() {
/*  53 */     if (_instance == null) {
/*  54 */       _instance = new ItemAction();
/*     */     }
/*  56 */     return _instance;
/*     */   }
/*     */   
/*     */   public static void reload() {
/*  60 */     _questionIdIndex.clear();
/*  61 */     _instance = null;
/*  62 */     getInstance();
/*     */   }
/*     */   private ItemAction() {
/*  65 */     getData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean forNpcQuest(String s, L1PcInstance pc, L1ItemInstance item) {
/*  73 */     ArrayList aTempData = null;
/*  74 */     if (!NO_GET_DATA) {
/*  75 */       NO_GET_DATA = true;
/*  76 */       getData();
/*     */     } 
/*     */     
/*  79 */     for (int i = 0; i < aData.size(); i++) {
/*  80 */       aTempData = (ArrayList)aData.get(i);
/*     */ 
/*     */       
/*  83 */       if (((String)aTempData.get(0)).equals(s)) {
/*     */         
/*  85 */         if (pc.getInventory().getSize() >= 170) {
/*  86 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, "身上太多東西,請先去清理"));
/*     */           
/*  88 */           return false;
/*     */         } 
/*  90 */         String msg0 = "";
/*  91 */         String msg1 = "";
/*  92 */         String msg2 = "";
/*  93 */         String msg3 = "";
/*  94 */         String msg4 = "";
/*  95 */         String msg5 = "";
/*  96 */         String msg6 = "";
/*  97 */         String msg7 = "";
/*  98 */         String msg8 = "";
/*  99 */         String msg9 = "";
/* 100 */         String msg10 = "";
/* 101 */         String msg11 = "";
/* 102 */         String msg12 = "";
/* 103 */         String msg13 = "";
/* 104 */         String msg14 = "";
/* 105 */         String msg15 = "";
/* 106 */         String msg16 = "";
/* 107 */         String msg17 = "";
/* 108 */         String msg18 = "";
/* 109 */         String msg19 = "";
/* 110 */         String msg20 = "";
/* 111 */         String msg21 = "";
/* 112 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/* 113 */           msg0 = " " + ((Integer)aTempData.get(1)).intValue() + "級以上。 ";
/*     */         } else {
/* 115 */           msg0 = " 無限制 ";
/*     */         } 
/* 117 */         if (((Integer)aTempData.get(2)).intValue() == 1) {
/* 118 */           msg1 = " 王族";
/* 119 */         } else if (((Integer)aTempData.get(2)).intValue() == 2) {
/* 120 */           msg1 = " 騎士";
/* 121 */         } else if (((Integer)aTempData.get(2)).intValue() == 3) {
/* 122 */           msg1 = " 法師";
/* 123 */         } else if (((Integer)aTempData.get(2)).intValue() == 4) {
/* 124 */           msg1 = " 妖精";
/* 125 */         } else if (((Integer)aTempData.get(2)).intValue() == 5) {
/* 126 */           msg1 = " 黑妖";
/* 127 */         } else if (((Integer)aTempData.get(2)).intValue() == 0) {
/* 128 */           msg1 = " 所有職業";
/*     */         } 
/*     */         
/* 131 */         if (((Integer)aTempData.get(3)).intValue() != 0) {
/* 132 */           if (((Integer)aTempData.get(17)).intValue() != 0) {
/* 133 */             msg2 = (new StringBuilder()).append(((Integer)aTempData.get(17)).intValue()).toString();
/*     */           } else {
/* 135 */             msg2 = (new StringBuilder()).append(((Integer)aTempData.get(3)).intValue()).toString();
/*     */           } 
/*     */         } else {
/* 138 */           msg2 = "無限制";
/*     */         } 
/* 140 */         if ((String)aTempData.get(5) != null) {
/* 141 */           msg3 = (String) aTempData.get(5);
/*     */         } else {
/* 143 */           msg3 = "無標題";
/*     */         } 
/*     */ 
/*     */         
/* 147 */         int HammerCount1 = 0;
/* 148 */         L1ItemInstance firehammer1 = pc.getInventory().findItemId(((Integer)aTempData.get(18)).intValue());
/* 149 */         if (firehammer1 != null) {
/* 150 */           HammerCount1 = (int)firehammer1.getCount();
/* 151 */           if (HammerCount1 > ((Integer)aTempData.get(20)).intValue()) {
/* 152 */             HammerCount1 = ((Integer)aTempData.get(20)).intValue();
/*     */           }
/* 154 */           if ((String)aTempData.get(19) != null && HammerCount1 > 0) {
/* 155 */             msg21 = "[" + (String)aTempData.get(19) + "]成功機率+: " + HammerCount1 + " %";
/*     */           }
/*     */         } 
/* 158 */         int[] materials = (int[])aTempData.get(6);
/* 159 */         int[] counts = (int[])aTempData.get(7);
/* 160 */         int[] enchants = (int[])aTempData.get(8);
/*     */         
/* 162 */         if (materials != null) {
/* 163 */           for (int j = 0; j < materials.length; j++) {
/* 164 */             L1ItemInstance temp = ItemTable.get().createItem(materials[j]);
/* 165 */             temp.setEnchantLevel(enchants[j]);
/* 166 */             temp.setIdentified(true);
/* 167 */             switch (j) {
/*     */               case 0:
/* 169 */                 msg4 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 1:
/* 172 */                 msg5 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 2:
/* 175 */                 msg6 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 3:
/* 178 */                 msg7 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 4:
/* 181 */                 msg8 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 5:
/* 184 */                 msg9 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 6:
/* 187 */                 msg10 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 7:
/* 190 */                 msg11 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 8:
/* 193 */                 msg12 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 9:
/* 196 */                 msg13 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 10:
/* 199 */                 msg14 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 11:
/* 202 */                 msg15 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 12:
/* 205 */                 msg16 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 13:
/* 208 */                 msg17 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 14:
/* 211 */                 msg18 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 15:
/* 214 */                 msg18 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */               case 16:
/* 217 */                 msg20 = String.valueOf(temp.getLogName()) + " (" + counts[j] + ") 個";
/*     */                 break;
/*     */             } 
/*     */ 
/*     */           
/*     */           } 
/*     */         }
/* 224 */         if (((Integer)aTempData.get(4)).intValue() == 1) {
/* 225 */           pc.getInventory().removeItem(item, 1L);
/*     */         }
/* 227 */         String[] msgs = { msg0, msg1, msg2, msg3, msg4, msg5, msg6, 
/* 228 */             msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, 
/* 229 */             msg15, msg16, msg17, msg18, msg19, msg20, msg21 };
/*     */ 
/*     */         
/* 232 */         pc.setitemactionhtml((String) aTempData.get(0));
/*     */ 
/*     */         
/* 235 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "ItemBlend3", msgs));
/*     */       } 
/*     */       
/* 238 */       if (pc.getitemactionhtml() == (String)aTempData.get(0) && ((String)aTempData.get(9)).equals(s)) {
/* 239 */         int[] materials = (int[])aTempData.get(6);
/* 240 */         int[] counts = (int[])aTempData.get(7);
/* 241 */         int[] enchants = (int[])aTempData.get(8);
/*     */ 
/*     */ 
/*     */         
/* 245 */         if (((Integer)aTempData.get(1)).intValue() != 0 && 
/* 246 */           pc.getLevel() < ((Integer)aTempData.get(1)).intValue()) {
/* 247 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("等級必須為" + ((Integer)aTempData.get(1)).intValue() + "以上。"));
/* 248 */           return false;
/*     */         } 
/*     */ 
/*     */         
/* 252 */         if (((Integer)aTempData.get(2)).intValue() != 0) {
/* 253 */           byte class_id = 0;
/* 254 */           String Classmsg = "";
/*     */           
/* 256 */           if (pc.isCrown()) {
/* 257 */             class_id = 1;
/* 258 */           } else if (pc.isKnight()) {
/* 259 */             class_id = 2;
/* 260 */           } else if (pc.isWizard()) {
/* 261 */             class_id = 3;
/* 262 */           } else if (pc.isElf()) {
/* 263 */             class_id = 4;
/* 264 */           } else if (pc.isDarkelf()) {
/* 265 */             class_id = 5;
/* 266 */           } else if (pc.isDragonKnight()) {
/* 267 */             class_id = 6;
/* 268 */           } else if (pc.isIllusionist()) {
/* 269 */             class_id = 7;
/*     */           } 
/* 271 */           switch (((Integer)aTempData.get(2)).intValue()) {
/*     */             case 1:
/* 273 */               Classmsg = "王族";
/*     */               break;
/*     */             case 2:
/* 276 */               Classmsg = "騎士";
/*     */               break;
/*     */             case 3:
/* 279 */               Classmsg = "法師";
/*     */               break;
/*     */             case 4:
/* 282 */               Classmsg = "妖精";
/*     */               break;
/*     */             case 5:
/* 285 */               Classmsg = "黑暗妖精";
/*     */               break;
/*     */           } 
/*     */           
/* 289 */           if (((Integer)aTempData.get(2)).intValue() != class_id) {
/* 290 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, "職業必須是", Classmsg, "才能製造此道具"));
/* 291 */             return false;
/*     */           } 
/*     */         } 
/* 294 */         boolean enough = true;
/*     */         
/* 296 */         int num = 0;
/*     */         
/* 298 */         for (int j = 0; j < materials.length; j++) {
/* 299 */           if (!pc.getInventory().checkEnchantItem(materials[j], enchants[j], counts[j])) {
/* 300 */             L1Item temp = ItemTable.get().getTemplate(materials[j]);
/* 301 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(temp.getName()) + "(" + (counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
/* 302 */             enough = false;
/*     */           } 
/*     */         } 
/* 305 */         if (enough) {
/* 306 */           num++;
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
/*     */ 
/*     */           
/* 322 */           if (num == materials.length) {
/* 323 */             enough = true;
/*     */           }
/*     */           
/* 326 */           if (enough) {
/*     */             
/* 328 */             int[] newcounts = new int[counts.length];
/* 329 */             for (int k = 0; k < counts.length; k++) {
/* 330 */               newcounts[k] = counts[k];
/*     */               
/* 332 */               pc.getInventory().consumeEnchantItem(materials[k], enchants[k], newcounts[k]);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 338 */             int HammerCount = 0;
/* 339 */             L1ItemInstance firehammer = pc.getInventory().findItemId(((Integer)aTempData.get(18)).intValue());
/* 340 */             if (firehammer != null) {
/* 341 */               HammerCount = (int)firehammer.getCount();
/* 342 */               if (HammerCount > ((Integer)aTempData.get(20)).intValue()) {
/* 343 */                 HammerCount = ((Integer)aTempData.get(20)).intValue();
/*     */               }
/*     */             } 
/*     */             
/* 347 */             int TotalChance = ((Integer)aTempData.get(3)).intValue() + HammerCount;
/*刪除加成道具*/				if(HammerCount != 0){
/*刪除加成道具*/				pc.getInventory().consumeItem((Integer)aTempData.get(18), HammerCount); 
/*刪除加成道具*/				}
/*     */ 
/*     */ 
/*     */             
/* 352 */             Random _random = new Random();
/* 353 */             int New_itemid = ((Integer)aTempData.get(10)).intValue();
/* 354 */             int New_item_counts = ((Integer)aTempData.get(11)).intValue();
/* 355 */             int newcounts1 = New_item_counts;
/* 356 */             if (_random.nextInt(100) < TotalChance) {
/*     */               
/* 358 */               L1ItemInstance newitem = ItemTable.get().createItem(New_itemid);
/* 359 */               if (newitem != null)
/*     */               {
/* 361 */                 if (newitem.isStackable()) {
/*     */                   
/* 363 */                   newitem.setEnchantLevel(((Integer)aTempData.get(12)).intValue());
/*     */                   
/* 365 */                   newitem.setIdentified(true);
/*     */                   
/* 367 */                   newitem.setCount(newcounts1);
/*     */                   
/* 369 */                   pc.getInventory().storeItem(newitem);
/*     */                   
/* 371 */                   pc.sendPackets((ServerBasePacket)new S_ServerMessage(143, newitem.getLogName()));
/* 372 */                   if ((String)aTempData.get(13) != null && ((Integer)aTempData.get(15)).intValue() == 1) {
/* 373 */                     World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String) aTempData.get(13), new Object[] { pc.getName(), newitem.getLogName() })));
/*     */                   }
/*     */                   
/* 376 */                   if (((Integer)aTempData.get(16)).intValue() == 1) {
/* 377 */                     pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */                   }
/*     */                   
/* 380 */                   enough = false;
/*     */                 } else {
/*     */                   
/* 383 */                   L1ItemInstance newitem2 = ItemTable.get().createItem(New_itemid);
/*     */                   
/* 385 */                   newitem2.setEnchantLevel(((Integer)aTempData.get(12)).intValue());
/*     */                   
/* 387 */                   newitem2.setIdentified(true);
/*     */                   
/* 389 */                   newitem2.setCount(1L);
/* 390 */                   pc.getInventory().storeItem(newitem2);
/* 391 */                   pc.sendPackets((ServerBasePacket)new S_ServerMessage(143, newitem2.getLogName()));
/*     */ 
/*     */                   
/* 394 */                   if ((String)aTempData.get(13) != null && ((Integer)aTempData.get(15)).intValue() == 1) {
/* 395 */                     World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String) aTempData.get(13), new Object[] { pc.getName(), newitem.getLogName() })));
/*     */                   }
/* 397 */                   if (((Integer)aTempData.get(16)).intValue() == 1) {
/* 398 */                     pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */                   }
/* 400 */                   enough = false;
/*     */                 } 
/* 402 */                 return false;
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 407 */             else if (((Integer)aTempData.get(21)).intValue() > 0 && ((Integer)aTempData.get(22)).intValue() > 0) {
/* 408 */               L1ItemInstance residueitem = ItemTable.get().createItem(((Integer)aTempData.get(21)).intValue());
/* 409 */               residueitem.setEnchantLevel(((Integer)aTempData.get(23)).intValue());
/* 410 */               residueitem.setIdentified(true);
/* 411 */               residueitem.setCount(((Integer)aTempData.get(22)).intValue());
/* 412 */               pc.getInventory().storeItem(residueitem);
/* 413 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("退還物品:" + residueitem.getLogName()));
/* 414 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage((String) aTempData.get(14)));
/* 415 */               if (((Integer)aTempData.get(16)).intValue() == 1) {
/* 416 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               }
/*     */             } else {
/* 419 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage((String) aTempData.get(14)));
/* 420 */               if (((Integer)aTempData.get(16)).intValue() == 1) {
/* 421 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               }
/* 423 */               enough = false;
/* 424 */               return false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   private static void getData() {
/* 440 */     Connection con = null;
/*     */     try {
/* 442 */       con = DatabaseFactory.get().getConnection();
/* 443 */       Statement stat = con.createStatement();
/* 444 */       ResultSet rset = stat
/* 445 */         .executeQuery("SELECT * FROM w_道具火神製作");
/* 446 */       ArrayList<Object> aReturn = null;
/* 447 */       String sTemp = null;
/* 448 */       if (rset != null)
/* 449 */         while (rset.next()) {
/* 450 */           aReturn = new ArrayList();
/*     */           
/* 452 */           sTemp = rset.getString("action");
/* 453 */           aReturn.add(0, sTemp);
/* 454 */           aReturn.add(1, Integer.valueOf(rset.getInt("checkLevel_min")));
/* 455 */           aReturn.add(2, Integer.valueOf(rset.getInt("classid")));
/* 456 */           aReturn.add(3, new Integer(rset.getInt("成功機率")));
/* 457 */           aReturn.add(4, new Integer(rset.getInt("是否扣除道具")));
/* 458 */           aReturn.add(5, rset.getString("製造清單名稱"));
/*     */           
/* 460 */           if (rset.getString("需求物品") != null && 
/* 461 */             !rset.getString("需求物品").equals("") && 
/* 462 */             !rset.getString("需求物品").equals("0")) {
/* 463 */             aReturn.add(6, getArray(rset.getString("需求物品"), ",", 1));
/*     */           } else {
/* 465 */             aReturn.add(6, null);
/*     */           } 
/* 467 */           if (rset.getString("需求物品數量") != null && 
/* 468 */             !rset.getString("需求物品數量").equals("") && 
/* 469 */             !rset.getString("需求物品數量").equals("0")) {
/* 470 */             aReturn.add(7, getArray(rset.getString("需求物品數量"), ",", 1));
/*     */           } else {
/* 472 */             aReturn.add(7, null);
/*     */           } 
/*     */           
/* 475 */           aReturn.add(8, getArray(rset.getString("需求物品強化值"), ",", 1));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 485 */           aReturn.add(9, rset.getString("確認製作action"));
/*     */           
/* 487 */           aReturn.add(10, Integer.valueOf(rset.getInt("給予道具")));
/* 488 */           aReturn.add(11, Integer.valueOf(rset.getInt("給予道具數量")));
/* 489 */           aReturn.add(12, Integer.valueOf(rset.getInt("給予道具強化值")));
/*     */           
/* 491 */           aReturn.add(13, rset.getString("製作成功"));
/* 492 */           aReturn.add(14, rset.getString("製作失敗"));
/* 493 */           aReturn.add(15, new Integer(rset.getInt("世界廣播")));
/*     */           
/* 495 */           aReturn.add(16, new Integer(rset.getInt("是否關閉對話視窗")));
/*     */           
/* 497 */           aReturn.add(17, Integer.valueOf(rset.getInt("顯示假機率")));
/*     */           
/* 499 */           aReturn.add(18, new Integer(rset.getInt("自訂機率道具")));
/* 500 */           aReturn.add(19, rset.getString("自訂機率道具名稱"));
/* 501 */           aReturn.add(20, new Integer(rset.getInt("自訂機率數量上限")));
/*     */           
/* 503 */           aReturn.add(21, new Integer(rset.getInt("失敗退還道具")));
/* 504 */           aReturn.add(22, new Integer(rset.getInt("失敗退還道具數量")));
/* 505 */           aReturn.add(23, new Integer(rset.getInt("失敗退還強化值")));
/*     */           
/* 507 */           aData.add(aReturn);
/*     */         }  
/* 509 */       if (con != null && !con.isClosed())
/* 510 */         con.close(); 
/* 511 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static Object getArray(String s, String sToken, int iType) {
/* 516 */     StringTokenizer st = new StringTokenizer(s, sToken);
/* 517 */     int iSize = st.countTokens();
/* 518 */     String sTemp = null;
/*     */     
/* 520 */     if (iType == 1) {
/* 521 */       int[] iReturn = new int[iSize];
/* 522 */       for (int i = 0; i < iSize; i++) {
/* 523 */         sTemp = st.nextToken();
/* 524 */         iReturn[i] = Integer.parseInt(sTemp);
/*     */       } 
/* 526 */       return iReturn;
/*     */     } 
/*     */     
/* 529 */     if (iType == 2) {
/* 530 */       String[] sReturn = new String[iSize];
/* 531 */       for (int i = 0; i < iSize; i++) {
/* 532 */         sTemp = st.nextToken();
/* 533 */         sReturn[i] = sTemp;
/*     */       } 
/* 535 */       return sReturn;
/*     */     } 
/*     */     
/* 538 */     if (iType == 3) {
/* 539 */       String sReturn = null;
/* 540 */       for (int i = 0; i < iSize; i++) {
/* 541 */         sTemp = st.nextToken();
/* 542 */         sReturn = sTemp;
/*     */       } 
/* 544 */       return sReturn;
/*     */     } 
/* 546 */     return null;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\william\ItemAction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */