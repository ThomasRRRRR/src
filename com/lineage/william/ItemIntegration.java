/*     */ package com.lineage.william;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.CharItemsReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_AddItem;
/*     */ import com.lineage.server.serverpackets.S_DeleteInventoryItem;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.RandomArrayList;
/*     */ import com.lineage.server.world.World;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemIntegration
/*     */ {
/*  52 */   private static final Log _logx = LogFactory.getLog(ItemIntegration.class);
/*     */   
/*  54 */   private static ArrayList<ArrayList<Object>> aData20 = new ArrayList<>();
/*     */   
/*     */   public static final String TOKEN = ",";
/*     */   
/*     */   private static ItemIntegration _instance;
/*     */ 
/*     */   
/*     */   public static ItemIntegration getInstance() {
/*  62 */     if (_instance == null) {
/*  63 */       _instance = new ItemIntegration();
/*     */     }
/*  65 */     return _instance;
/*     */   }
/*     */   
/*     */   public static void reload() {
/*  69 */     aData20.clear();
/*  70 */     _instance = null;
/*  71 */     getInstance();
/*     */   }
/*     */   
/*     */   public static void forItemIntegration(L1PcInstance pc, L1ItemInstance item, L1ItemInstance itemG) {
/*  75 */     int itemid = item.getItem().getItemId();
/*  76 */     ArrayList<?> aTempData = null;
/*     */     
/*  78 */     for (int i = 0; i < aData20.size(); i++) {
/*  79 */       aTempData = aData20.get(i);
/*  80 */       if (((Integer)aTempData.get(0)).intValue() == itemid && itemG.getItem().getItemId() == ((Integer)aTempData.get(4)).intValue()) {
/*     */         
/*  82 */         if (pc.getInventory().getSize() >= 170) {
/*  83 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("身上物品太多,無法操作"));
/*     */           
/*     */           return;
/*     */         } 
/*  87 */         if (pc.getInventory().getWeight() / pc.getMaxWeight() * 100.0D > 90.0D) {
/*  88 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("身上物品負重,無法操作"));
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */         
/*  96 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/*  97 */           byte class_id = 0;
/*  98 */           String msg = "";
/*  99 */           if (pc.isCrown()) {
/* 100 */             class_id = 1;
/* 101 */           } else if (pc.isKnight()) {
/* 102 */             class_id = 2;
/* 103 */           } else if (pc.isWizard()) {
/* 104 */             class_id = 3;
/* 105 */           } else if (pc.isElf()) {
/* 106 */             class_id = 4;
/* 107 */           } else if (pc.isDarkelf()) {
/* 108 */             class_id = 5;
/*     */           } 
/*     */           
/* 111 */           switch (((Integer)aTempData.get(1)).intValue()) {
/*     */             case 1:
/* 113 */               msg = "王族";
/*     */               break;
/*     */             case 2:
/* 116 */               msg = "騎士";
/*     */               break;
/*     */             case 3:
/* 119 */               msg = "法師";
/*     */               break;
/*     */             case 4:
/* 122 */               msg = "妖精";
/*     */               break;
/*     */             case 5:
/* 125 */               msg = "黑暗妖精";
/*     */               break;
/*     */           } 
/*     */           
/* 129 */           if (((Integer)aTempData.get(1)).intValue() != class_id && !pc.isGm()) {
/* 130 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/* 135 */         if (((Integer)aTempData.get(2)).intValue() != 0 && !pc.isGm() && 
/* 136 */           pc.getLevel() < ((Integer)aTempData.get(2)).intValue()) {
/* 137 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("等級" + ((Integer)aTempData.get(2)).intValue() + "以上才可使用此道具。"));
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 142 */         if (item.getCount() >= ((Integer)aTempData.get(3)).intValue() || ((Integer)aTempData.get(3)).intValue() == 0) {
/*     */           
/* 144 */           if (itemG.getItem().getItemId() == ((Integer)aTempData.get(4)).intValue()) {
/*     */             
/* 146 */             if (itemG.getCount() >= ((Integer)aTempData.get(5)).intValue()) {
/*     */               
/* 148 */               if (itemG.getEnchantLevel() >= ((Integer)aTempData.get(19)).intValue()) {
/*     */ 
/*     */                 
/* 151 */                 if (((int[])aTempData.get(7) != null && (int[])aTempData.get(8) != null) || ((int[])aTempData.get(7) == null && (int[])aTempData.get(8) == null)) {
/* 152 */                   int[] materials = (int[])aTempData.get(7);
/* 153 */                   int[] counts = (int[])aTempData.get(8);
/* 154 */                   boolean isCreate = true;
/*     */                   
/* 156 */                   if ((int[])aTempData.get(7) != null && (int[])aTempData.get(8) != null) {
/* 157 */                     for (int j = 0; j < materials.length; j++) {
/* 158 */                       if (!pc.getInventory().checkItem(materials[j], counts[j])) {
/* 159 */                         L1Item temp = ItemTable.get().getTemplate(materials[j]);
/* 160 */                         pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(temp.getName()) + "(" + (counts[j] - pc.getInventory().countItems(temp.getItemId())) + ")"));
/* 161 */                         isCreate = false;
/*     */                       } 
/*     */                     } 
/*     */                   }
/* 165 */                   if (isCreate) {
/*     */                     
/* 167 */                     if ((int[])aTempData.get(7) != null && (int[])aTempData.get(8) != null) {
/* 168 */                       for (int k = 0; k < materials.length; k++) {
/* 169 */                         pc.getInventory().consumeItem(materials[k], counts[k]);
/*     */                       }
/*     */                     }
/*     */                     
/* 173 */                     if (((Integer)aTempData.get(3)).intValue() != 0) {
/* 174 */                       pc.getInventory().removeItem(item, ((Integer)aTempData.get(3)).intValue());
/*     */                     }
/*     */                     
/* 177 */                     if ((int[])aTempData.get(9) != null && (int[])aTempData.get(10) != null) {
/* 178 */                       int[] giveMaterials = (int[])aTempData.get(9);
/* 179 */                       int[] giveCounts = (int[])aTempData.get(10);
/* 180 */                       int rnd = ((Integer)aTempData.get(6)).intValue();
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
/*     */ 
/*     */ 
/*     */                       
/* 218 */                       if (RandomArrayList.getInc(100, 1) <= rnd) {
/* 219 */                         for (int l = 0; l < giveMaterials.length; l++) {
/* 220 */                           L1ItemInstance item2 = ItemTable.get().createItem(giveMaterials[l]);
/*     */                           
/* 222 */                           if (item2.isStackable()) {
/* 223 */                             item2.setCount(giveCounts[l]);
/*     */                           } else {
/* 225 */                             item2.setCount(1L);
/*     */                           } 
/* 227 */                           if (item2 != null) {
/*     */                             
/* 229 */                             if (itemG.getItem().getType2() == 0 && item2.getItem().getType2() == 0) {
/* 230 */                               if (itemG.getItem().getType() == 32) {
/*     */ 
/*     */                                 
/* 233 */                                 強化道具(pc, itemG, giveMaterials[l]);
/*     */                               } else {
/*     */                                 
/* 236 */                                 pc.getInventory().removeItem(itemG, ((Integer)aTempData.get(5)).intValue());
/* 237 */                                 if (pc.getInventory().checkAddItem(item2, giveCounts[l]) == 0) {
/* 238 */                                   pc.getInventory().storeItem(item2);
/*     */                                 } else {
/*     */                                   
/* 241 */                                   World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item2);
/*     */                                 
/*     */                                 }
/*     */ 
/*     */                               
/*     */                               }
/*     */                             
/*     */                             }
/* 249 */                             else if (itemG.getItem().getType2() == 1 && item2.getItem().getType2() == 1) {
/*     */ 
/*     */                               
/* 252 */                               強化武器(pc, itemG, giveMaterials[l]);
/*     */                             }
/* 254 */                             else if (itemG.getItem().getType2() == 2 && item2.getItem().getType2() == 2) {
/*     */ 
/*     */                               
/* 257 */                               強化裝備(pc, itemG, giveMaterials[l]);
/*     */                             } 
/*     */ 
/*     */ 
/*     */                             
/* 262 */                             if ((String)aTempData.get(11) != null) {
/* 263 */                               pc.sendPackets((ServerBasePacket)new S_SystemMessage((String)aTempData.get(11)));
/*     */                             } else {
/* 265 */                               pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item2.getLogName()));
/*     */                             } 
/* 267 */                             if ((String)aTempData.get(18) != null) {
/* 268 */                               World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String)aTempData.get(18), new Object[] { pc.getName(), "+" + itemG.getEnchantLevel() + item2.getViewName() })));
/*     */                             }
/*     */                             
/* 271 */                             if (((Integer)aTempData.get(12)).intValue() != 0) {
/* 272 */                               pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), ((Integer)aTempData.get(12)).intValue()));
/* 273 */                               pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(pc.getId(), ((Integer)aTempData.get(12)).intValue()));
/*     */                             } 
/*     */                             
/* 276 */                             祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (+" + itemG.getEnchantLevel() + item2.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*     */                           } 
/*     */                         } 
/*     */                         break;
/*     */                       } 
/* 281 */                       if (((Integer)aTempData.get(13)).intValue() != 0) {
/* 282 */                         pc.getInventory().removeItem(itemG, ((Integer)aTempData.get(5)).intValue());
/*     */                       }
/*     */                       
/* 285 */                       if ((String)aTempData.get(14) != null) {
/* 286 */                         pc.sendPackets((ServerBasePacket)new S_SystemMessage((String)aTempData.get(14)));
/*     */                       }
/* 288 */                       祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (+" + itemG.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/* 289 */                       if (((Integer)aTempData.get(15)).intValue() == 0) {
/* 290 */                         pc.getInventory().removeItem(itemG, 1L);
/* 289 */                       if (((Integer)aTempData.get(16)).intValue() != 0) {
									int itemn = ((Integer)aTempData.get(16)).intValue();
/* 290 */                         pc.getInventory().storeItem(itemn,1);
/*     */                       		}
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               } 
/* 298 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("該物品強化值需求+" + ((Integer)aTempData.get(19)).intValue()));
/*     */               break;
/*     */             } 
/* 301 */             L1Item temp2 = ItemTable.get().getTemplate(((Integer)aTempData.get(4)).intValue());
/* 302 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(temp2.getName()) + "(" + (((Integer)aTempData.get(5)).intValue() - pc.getInventory().countItems(temp2.getItemId())) + ")"));
/*     */             break;
/*     */           } 
/* 305 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("沒發生什麼事情"));
/*     */           break;
/*     */         } 
/* 308 */         L1Item temp1 = ItemTable.get().getTemplate(itemid);
/* 309 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(temp1.getName()) + "(" + (((Integer)aTempData.get(3)).intValue() - pc.getInventory().countItems(temp1.getItemId())) + ")"));
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void 祝福升級成功(String info) {
/*     */     try {
/* 319 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 320 */             "./玩家紀錄/[祝福升級成功].txt", true));
/* 321 */       out.write(String.valueOf(info) + "\r\n");
/* 322 */       out.close();
/* 323 */     } catch (IOException e) {
/* 324 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void 祝福升級失敗(String info) {
/*     */     try {
/* 329 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 330 */             "./玩家紀錄/[祝福升級失敗].txt", true));
/* 331 */       out.write(String.valueOf(info) + "\r\n");
/* 332 */       out.close();
/* 333 */     } catch (IOException e) {
/* 334 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   private ItemIntegration() {
/* 338 */     PerformanceTimer timer = new PerformanceTimer();
/* 339 */     Connection con = null;
/*     */     try {
/* 341 */       con = DatabaseFactory.get().getConnection();
/* 342 */       Statement stat = con.createStatement();
/* 343 */       ResultSet rset = stat.executeQuery("SELECT * FROM w_物品升級系統_2");
/* 344 */       ArrayList<Object> aReturn = null;
/* 345 */       if (rset != null) {
/* 346 */         while (rset.next()) {
/* 347 */           aReturn = new ArrayList();
/* 348 */           aReturn.add(0, new Integer(rset.getInt("道具")));
/* 349 */           aReturn.add(1, new Integer(rset.getInt("職業")));
/* 350 */           aReturn.add(2, new Integer(rset.getInt("等級")));
/* 351 */           aReturn.add(3, new Integer(rset.getInt("道具數量")));
/* 352 */           aReturn.add(4, new Integer(rset.getInt("Integration_ID")));
/* 353 */           aReturn.add(5, new Integer(rset.getInt("Integration_count")));
/* 354 */           aReturn.add(6, new Integer(rset.getInt("random")));
/*     */ 
/*     */           
/* 357 */           if (rset.getString("materials") != null && !rset.getString("materials").equals("") && !rset.getString("materials").equals("0")) {
/* 358 */             aReturn.add(7, getArray(rset.getString("materials"), ",", 1));
/*     */           } else {
/* 360 */             aReturn.add(7, null);
/*     */           } 
/* 362 */           if (rset.getString("counts") != null && !rset.getString("counts").equals("") && !rset.getString("counts").equals("0")) {
/* 363 */             aReturn.add(8, getArray(rset.getString("counts"), ",", 1));
/*     */           } else {
/* 365 */             aReturn.add(8, null);
/*     */           } 
/* 367 */           if (rset.getString("new_item") != null && !rset.getString("new_item").equals("") && !rset.getString("new_item").equals("0")) {
/* 368 */             aReturn.add(9, getArray(rset.getString("new_item"), ",", 1));
/*     */           } else {
/* 370 */             aReturn.add(9, null);
/*     */           } 
/* 372 */           if (rset.getString("new_item_counts") != null && !rset.getString("new_item_counts").equals("") && !rset.getString("new_item_counts").equals("0")) {
/* 373 */             aReturn.add(10, getArray(rset.getString("new_item_counts"), ",", 1));
/*     */           } else {
/* 375 */             aReturn.add(10, null);
/*     */           } 
/* 377 */           aReturn.add(11, rset.getString("msg"));
/* 378 */           aReturn.add(12, new Integer(rset.getInt("gfxId")));
/*     */           
/* 380 */           aReturn.add(13, new Integer(rset.getInt("失敗是否刪除材料")));
/* 381 */           aReturn.add(14, rset.getString("失敗顯示"));
/* 382 */           aReturn.add(15, new Integer(rset.getInt("失敗是否保留")));
/* 383 */           aReturn.add(16, new Integer(rset.getInt("失敗退還道具")));
/* 384 */           aReturn.add(17, new Integer(rset.getInt("預留欄位2")));
/*     */           
/* 386 */           aReturn.add(18, rset.getString("All_message"));
/* 387 */           aReturn.add(19, new Integer(rset.getInt("EnchantItem")));
/* 388 */           aData20.add(aReturn);
/*     */         } 
/*     */       }
/*     */       
/* 392 */       stat.close();
/* 393 */       rset.close();
/* 394 */       if (con != null && !con.isClosed())
/* 395 */         con.close(); 
/* 396 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getArray(String s, String sToken, int iType) {
/* 402 */     StringTokenizer st = new StringTokenizer(s, sToken);
/* 403 */     int iSize = st.countTokens();
/* 404 */     String sTemp = null;
/* 405 */     if (iType == 1) {
/* 406 */       int[] iReturn = new int[iSize];
/* 407 */       for (int i = 0; i < iSize; i++) {
/* 408 */         sTemp = st.nextToken();
/* 409 */         iReturn[i] = Integer.parseInt(sTemp);
/*     */       } 
/* 411 */       return iReturn;
/*     */     } 
/*     */     
/* 414 */     if (iType == 2) {
/* 415 */       String[] sReturn = new String[iSize];
/* 416 */       for (int i = 0; i < iSize; i++) {
/* 417 */         sTemp = st.nextToken();
/* 418 */         sReturn[i] = sTemp;
/*     */       } 
/* 420 */       return sReturn;
/*     */     } 
/*     */     
/* 423 */     if (iType == 3) {
/* 424 */       String sReturn = null;
/* 425 */       for (int i = 0; i < iSize; i++) {
/* 426 */         sTemp = st.nextToken();
/* 427 */         sReturn = sTemp;
/*     */       } 
/* 429 */       return sReturn;
/*     */     } 
/* 431 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void 強化裝備(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
/* 437 */     L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
/* 438 */     if (item.getBless() == 0) {
/* 439 */       New_BlessItem.cleanall(pc, item);
New_BlessItem1.cleanall(pc, item);
New_BlessItem2.cleanall(pc, item);
/*     */     }
/*     */ 
/*     */     
/* 443 */     pc.sendPackets((ServerBasePacket)new S_DeleteInventoryItem(item.getId()));
/* 444 */     item.setItemId(new_ItemId);
/* 445 */     item.setItem(l1item);
/* 446 */     item.setBless(l1item.getBless());
/*     */     
/*     */     try {
/* 449 */       CharItemsReading.get().updateItemId_Name(item);
/* 450 */     } catch (Exception e) {
/*     */       
/* 452 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 456 */     pc.sendPackets((ServerBasePacket)new S_AddItem(item));
/* 457 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void 強化武器(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
/* 466 */     L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
/*     */     
/* 468 */     if (item.getBless() == 0) {
/* 469 */       New_BlessItem.cleanall(pc, item);
New_BlessItem1.cleanall(pc, item);
New_BlessItem2.cleanall(pc, item);
/*     */     }
/*     */ 
/*     */     
/* 473 */     pc.sendPackets((ServerBasePacket)new S_DeleteInventoryItem(item.getId()));
/* 474 */     item.setItemId(new_ItemId);
/* 475 */     item.setItem(l1item);
/* 476 */     item.setBless(l1item.getBless());
/*     */     
/*     */     try {
/* 479 */       CharItemsReading.get().updateItemId_Name(item);
/* 480 */     } catch (Exception e) {
/*     */       
/* 482 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 486 */     pc.sendPackets((ServerBasePacket)new S_AddItem(item));
/* 487 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*     */   }
/*     */   
/*     */   private static void 強化道具(L1PcInstance pc, L1ItemInstance item, int new_ItemId) {
/* 491 */     L1Item l1item = ItemTable.get().getTemplate(new_ItemId);
/* 492 */     if (item.getBless() == 0) {
/* 493 */       New_BlessItem.cleanall(pc, item);
New_BlessItem1.cleanall(pc, item);
New_BlessItem2.cleanall(pc, item);
/*     */     }
/*     */ 
/*     */     
/* 497 */     pc.sendPackets((ServerBasePacket)new S_DeleteInventoryItem(item.getId()));
/* 498 */     item.setItemId(new_ItemId);
/* 499 */     item.setItem(l1item);
/* 500 */     item.setBless(l1item.getBless());
/*     */     
/*     */     try {
/* 503 */       CharItemsReading.get().updateItemId_Name(item);
/* 504 */     } catch (Exception e) {
/*     */       
/* 506 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/* 510 */     pc.sendPackets((ServerBasePacket)new S_AddItem(item));
/* 511 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\william\ItemIntegration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */