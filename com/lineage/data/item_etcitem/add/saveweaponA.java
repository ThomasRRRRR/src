/*     */ package com.lineage.data.item_etcitem.add;
/*     */ 
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.datatables.RecordTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
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
/*     */ public class saveweaponA
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  31 */     return new saveweaponA();
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
/*     */ 
/*     */   
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  49 */     int targObjId = data[0];
/*     */     
/*  51 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  58 */     if (tgItem.isEquipped()) {
/*  59 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fR你必須先解除物品裝備!"));
/*     */       return;
/*     */     } 
/*  62 */     if (tgItem.getItem().getType2() == 1 && tgItem.getEnchantLevel() >= 15) {
/*  63 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("武器已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  66 */     if (tgItem.getItem().getType2() == 2 && tgItem.getEnchantLevel() >= 11) {
/*  67 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  70 */     if (tgItem.getItem().getItemId() >= 120477 && tgItem.getItem().getItemId() <= 120479 && tgItem.getEnchantLevel() >= 9) {
/*  71 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  74 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*  75 */     boolean isErr = false;
/*     */ 
/*     */     
/*  78 */     int use_type = tgItem.getItem().getUseType();
/*  79 */     switch (use_type) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 25:
/*     */       case 47:
///*飾品*/       	case 23:
///*飾品*/      	case 24:
///*飾品*/       	case 37:
///*飾品*/      	case 40:
/*  89 */         if (safe_enchant < 0) {
/*  90 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  95 */         isErr = true;
/*     */         break;
/*     */     } 
/*  98 */     if (tgItem.getBless() >= 128) {
/*  99 */       isErr = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (tgItem.getItem().getItemId() >= 301 && tgItem.getItem().getItemId() <= 305) {
/* 106 */       isErr = true;
/*     */     }
/* 108 */     if (isErr) {
/* 109 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     if (tgItem != null) {
/* 114 */       if (tgItem.getItem().getItemId() <= -1) {
/* 115 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         return;
/*     */       } 
/* 118 */       if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
/* 119 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 124 */       tgItem.setproctect(true);
/* 125 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 126 */       pc.getInventory().saveItem(tgItem, 4);
/* 127 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("高級裝備保護卷軸的力量附著的物品中。"));
/* 128 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/* 130 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予高階防爆", pc.getIp());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponA.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */