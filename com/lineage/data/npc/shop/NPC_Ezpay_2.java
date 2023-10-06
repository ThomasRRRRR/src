/*     */ package com.lineage.data.npc.shop;
/*     */ 
/*     */ import com.lineage.data.executor.NpcExecutor;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.EzpayReading2;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
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
/*     */ public class NPC_Ezpay_2
/*     */   extends NpcExecutor
/*     */ {
/*  61 */   private static final Log _log = LogFactory.getLog(NPC_Ezpay_2.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NpcExecutor get() {
/*  71 */     return new NPC_Ezpay_2();
/*     */   }
/*     */ 
/*     */   
/*     */   public int type() {
/*  76 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void talk(L1PcInstance pc, L1NpcInstance npc) {
/*     */     try {
/*  82 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "s539_s_0"));
/*     */     }
/*  84 */     catch (Exception e) {
/*  85 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
/*  92 */     boolean isCloseList = false;
/*  93 */     if (cmd.equals("up")) {
/*  94 */       int page = pc.get_other().get_page() - 1;
/*  95 */       showPage(pc, npc, page);
/*     */     }
/*  97 */     else if (cmd.equals("dn")) {
/*  98 */       int page = pc.get_other().get_page() + 1;
/*  99 */       showPage(pc, npc, page);
/*     */     }
/* 101 */     else if (cmd.equalsIgnoreCase("1")) {
/* 102 */       (pc.get_otherList()).SHOPLIST.clear();
/*     */       
/* 104 */       Map<Integer, int[]> info = EzpayReading2.get().ezpayInfo(
/* 105 */           pc.getAccountName().toLowerCase());
/* 106 */       if (info.size() <= 0) {
/*     */         
/* 108 */         isCloseList = true;
/*     */         
/* 110 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aD並沒有查詢到您的相關商品記錄!!"));
/*     */       } else {
/*     */         
/* 113 */         pc.get_other().set_page(0);
/* 114 */         int index = 0;
/* 115 */         for (Integer key : info.keySet()) {
/* 116 */           int[] value = info.get(key);
/* 117 */           if (value != null) {
/* 118 */             (pc.get_otherList()).SHOPLIST.put(Integer.valueOf(index), value);
/* 119 */             index++;
/*     */           } 
/*     */         } 
/* 122 */         showPage(pc, npc, 0);
/*     */       }
/*     */     
/* 125 */     } else if (cmd.equalsIgnoreCase("2")) {
/*     */       
/* 127 */       Map<Integer, int[]> info = EzpayReading2.get().ezpayInfo(
/* 128 */           pc.getAccountName().toLowerCase());
/* 129 */       if (info.size() <= 0) {
/*     */         
/* 131 */         isCloseList = true;
/*     */         
/* 133 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aG並沒有查詢到您的相關商品記錄!!"));
/*     */       } else {
/*     */         
/* 136 */         for (Integer key : info.keySet()) {
/* 137 */           int[] value = info.get(key);
/* 138 */           int id = value[0];
/* 139 */           int itemid = value[1];
/* 140 */           int count = value[2];
/* 141 */           if (EzpayReading2.get().update(pc.getAccountName(), id, 
/* 142 */               pc.getName(), 
/* 143 */               pc.getNetConnection().getIp().toString()))
/*     */           {
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
/* 161 */             createNewItem(pc, npc, itemid, count);
/*     */           }
/*     */         } 
/*     */       } 
/* 165 */       isCloseList = true;
/*     */     } else {
/*     */       
/* 168 */       isCloseList = true;
/*     */     } 
/*     */     
/* 171 */     if (isCloseList)
/*     */     {
/* 173 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
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
/*     */   private static void showPage(L1PcInstance pc, L1NpcInstance npc, int page) {
/* 186 */     Map<Integer, int[]> list = (pc.get_otherList()).SHOPLIST;
/*     */ 
/*     */     
/* 189 */     int allpage = list.size() / 10;
/* 190 */     if (page > allpage || page < 0) {
/* 191 */       page = 0;
/*     */     }
/*     */     
/* 194 */     if (list.size() % 10 != 0) {
/* 195 */       allpage++;
/*     */     }
/*     */     
/* 198 */     pc.get_other().set_page(page);
/*     */     
/* 200 */     int showId = page * 10;
/*     */     
/* 202 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 204 */     for (int key = showId; key < showId + 10; key++) {
/* 205 */       int[] info = list.get(Integer.valueOf(key));
/* 206 */       if (info != null) {
/*     */         
/* 208 */         L1Item itemtmp = ItemTable.get().getTemplate(info[1]);
/* 209 */         if (itemtmp != null) {
/* 210 */           stringBuilder.append(String.valueOf(itemtmp.getName()) + "(" + info[2] + 
/* 211 */               "),");
/*     */         }
/*     */       } 
/*     */     } 
/* 215 */     String[] clientStrAry = stringBuilder.toString().split(",");
/* 216 */     if (allpage == 1) {
/*     */       
/* 218 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_s_1", 
/* 219 */             clientStrAry));
/*     */     
/*     */     }
/* 222 */     else if (page < 1) {
/* 223 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_s_3", 
/* 224 */             clientStrAry));
/*     */     }
/* 226 */     else if (page >= allpage - 1) {
/* 227 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_s_4", 
/* 228 */             clientStrAry));
/*     */     } else {
/*     */       
/* 231 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_s_2", 
/* 232 */             clientStrAry));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createNewItem(L1PcInstance pc, L1NpcInstance npc, int item_id, long count) {
/*     */     try {
/* 240 */       if (pc == null) {
/*     */         return;
/*     */       }
/*     */       
/* 244 */       L1ItemInstance item = ItemTable.get().createItem(item_id);
/* 245 */       if (item != null) {
/* 246 */         item.setCount(count);
/* 247 */         item.setIdentified(true);
/*     */         
/* 249 */         pc.getInventory().storeItem(item);
/* 250 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fW" + npc.getNameId() + 
/* 251 */               "給你" + item.getLogName()));
/*     */       } else {
/*     */         
/* 254 */         _log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")");
/*     */       }
/*     */     
/* 257 */     } catch (Exception e) {
/* 258 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\npc\shop\NPC_Ezpay.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */