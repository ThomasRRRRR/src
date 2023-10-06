/*     */ package com.lineage.data.item_etcitem.shop;
/*     */ 
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.datatables.ItemPowerUpdateTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.RecordTable;
/*     */ import com.lineage.server.datatables.lock.CharItemsReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_AddItem;
/*     */ import com.lineage.server.serverpackets.S_DeleteInventoryItem;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.templates.L1ItemPowerUpdate;
/*     */ import com.lineage.server.world.World;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Power_Up_01
/*     */   extends ItemExecutor
/*     */ {
/*  31 */   private static final Log _log = LogFactory.getLog(Power_Up_01.class);
/*     */   
/*  33 */   private static final Random _random = new Random();
/*     */ 
/*     */   
/*     */   public static ItemExecutor get() {
/*  37 */     return new Power_Up_01();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*     */     try {
/*  44 */       int targObjId = data[0];
/*     */       
/*  46 */       L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */       
/*  48 */       if (tgItem == null) {
/*     */         return;
/*     */       }
/*     */       
/*  52 */       if (tgItem.isEquipped()) {
/*  53 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("請先卸除該裝備在升級。"));
/*     */         return;
/*     */       } 
/*  56 */       L1ItemPowerUpdate info = 
/*  57 */         ItemPowerUpdateTable.get().get(tgItem.getItemId());
/*  58 */       if (info == null) {
/*     */         
/*  60 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         
/*     */         return;
/*     */       } 
/*  64 */       if (info.get_mode() == 4) {
/*     */         
/*  66 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         
/*     */         return;
/*     */       } 
/*  70 */       if (info.get_nedid() != item.getItemId()) {
/*     */         
/*  72 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       Map<Integer, L1ItemPowerUpdate> tmplist = 
/*  77 */         ItemPowerUpdateTable.get().get_type_id(tgItem.getItemId());
/*  78 */       if (tmplist.isEmpty()) {
/*     */         
/*  80 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         
/*     */         return;
/*     */       }
/*  84 */       int order_id = info.get_order_id();
/*  85 */       L1ItemPowerUpdate tginfo = tmplist.get(Integer.valueOf(order_id + 1));
/*  86 */       if (tginfo == null) {
/*  87 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         
/*     */         return;
/*     */       } 
/*  91 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/*  93 */       if (_random.nextInt(1000) < info.get_random()) {
/*     */ 
/*     */ 
/*     */         
/*  97 */         L1Item l1item = ItemTable.get().getTemplate(tginfo.get_itemid());
/*     */         
/*  99 */         RecordTable.get().recordFailureArmor2(pc.getName(), tgItem.getName(), l1item.getName(), pc.getIp());
/*     */         
/* 101 */         if (info.getallmsg() != null) {
/* 102 */           World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(info.getallmsg(), new Object[] { pc.getName(), tgItem.getName(), l1item.getName() })));
/*     */         }
tgItem.setproctect6(false);
tgItem.setproctect7(false);
/* 104 */         pc.sendPackets((ServerBasePacket)new S_DeleteInventoryItem(tgItem.getId()));
/* 105 */         tgItem.setItemId(tginfo.get_itemid());
/* 106 */         tgItem.setItem(l1item);
/* 107 */         if (tgItem.getBless() == 0) {
/* 108 */           l1item.setBless(0);
/*     */         } else {
/* 110 */           l1item.setBless(1);
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 115 */           CharItemsReading.get().updateItemId_Name(tgItem);
/* 116 */           CharItemsReading.get().updateItemBless(tgItem);
/* 117 */         } catch (Exception e) {
/* 118 */           e.printStackTrace();
/*     */         } 
/*     */ 
/*     */         
/* 122 */         pc.sendPackets((ServerBasePacket)new S_AddItem(tgItem));
/* 123 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(1410, tgItem.getName()));
/*     */       } else {
/*     */         L1ItemPowerUpdate lastinfo;
/*     */ 
/*     */ 
/*     */         
/*     */         L1ItemInstance lastitem;
/*     */ 
/*     */         
/* 132 */         switch (info.get_mode()) {
/*     */           case 0:
/* 134 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(1411, tgItem.getName()));
/*     */             break;
/*     */           case 1:
/* 137 */             if (tgItem.getproctect6()) {
/* 138 */               tgItem.setproctect6(false);
/* 139 */               pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 140 */               pc.getInventory().updateItem(tgItem, 4);
/* 141 */               pc.getInventory().saveItem(tgItem, 4);
/* 142 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("該裝備受到保護卷的祝福,沒發生什麼事情"));
/* 143 */               RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到保護卷的保護", pc.getIp());
/*     */               return;
/*     */             } 
/* 146 */             lastinfo = tmplist.get(Integer.valueOf(order_id - 1));
/* 147 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(1411, tgItem.getName()));
/*     */ 
/*     */ 
/*     */             
/* 151 */             lastitem = ItemTable.get().createItem(lastinfo.get_itemid());
/*     */             
/* 153 */             if (tgItem.getBless() == 0) {
/* 154 */               lastitem.setsaveBless(0);
/*     */             } else {
/* 156 */               lastitem.setsaveBless(1);
/*     */             } 
/*     */             
/* 159 */             pc.getInventory().removeItem(tgItem, 1L);
/*     */             
/* 161 */             lastitem.setIdentified(true);
/* 162 */             lastitem.setCount(1L);
/* 163 */             lastitem.setsaveBless(lastitem.getsaveBless());
/* 164 */             pc.getInventory().storeItem(lastitem);
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 169 */             if (tgItem.getproctect7()) {
/* 170 */               tgItem.setproctect7(false);
/* 171 */               pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 172 */               pc.getInventory().updateItem(tgItem, 4);
/* 173 */               pc.getInventory().saveItem(tgItem, 4);
/* 174 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("該裝備受到保護卷的祝福,沒發生什麼事情"));
/* 143 */               RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到保護卷的保護", pc.getIp());
/*     */               return;
/*     */             } 
/* 179 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(164, tgItem.getLogName(), "$252"));
/* 180 */             pc.getInventory().removeItem(tgItem, 1L);
/*     */             break;
/*     */           case 3:
/* 183 */             if (_random.nextBoolean()) {
/* 184 */               L1ItemPowerUpdate lastinfo2 = tmplist.get(Integer.valueOf(order_id - 1));
/* 185 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage(1411, tgItem.getName()));
/* 186 */               pc.getInventory().removeItem(tgItem, 1L);
/* 187 */               L1ItemInstance lastitem2 = ItemTable.get().createItem(lastinfo2.get_itemid());
/* 188 */               lastitem2.setIdentified(true);
/* 189 */               lastitem2.setCount(1L);
/* 190 */               pc.getInventory().storeItem(lastitem2);
break;
/*     */             }
/* 192 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(164, tgItem.getLogName(), "$252"));
/* 193 */             pc.getInventory().removeItem(tgItem, 1L);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 199 */     } catch (Exception e) {
/* 200 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\shop\Power_Up_01.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */