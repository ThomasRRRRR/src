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
/*     */ public class saveweaponB
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  31 */     return new saveweaponB();
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
/*  53 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  56 */     if (tgItem.isEquipped()) {
/*  57 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fR你必須先解除物品裝備!"));
/*     */       return;
/*     */     } 
/*  60 */     if (tgItem.getItem().getType2() == 1 && tgItem.getEnchantLevel() >= 15) {
/*  61 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("武器已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  64 */     if (tgItem.getItem().getType2() == 2 && tgItem.getEnchantLevel() >= 11) {
/*  65 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  68 */     if (tgItem.getItem().getItemId() >= 120477 && tgItem.getItem().getItemId() <= 120479 && tgItem.getEnchantLevel() >= 9) {
/*  69 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  72 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*  73 */     boolean isErr = false;
/*     */ 
/*     */     
/*  76 */     int use_type = tgItem.getItem().getUseType();
/*  77 */     switch (use_type) {
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
/*  87 */         if (safe_enchant < 0) {
/*  88 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  93 */         isErr = true;
/*     */         break;
/*     */     } 
/*  96 */     if (tgItem.getBless() >= 128) {
/*  97 */       isErr = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (tgItem.getItem().getItemId() >= 301 && tgItem.getItem().getItemId() <= 305) {
/* 104 */       isErr = true;
/*     */     }
/* 106 */     if (isErr) {
/* 107 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 112 */     if (tgItem != null) {
/* 113 */       if (tgItem.getItem().get_safeenchant() <= -1) {
/* 114 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         return;
/*     */       } 
/* 117 */       if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
/* 118 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*     */         
/*     */         return;
/*     */       } 
/* 122 */       tgItem.setproctect1(true);
/* 123 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 124 */       pc.getInventory().saveItem(tgItem, 4);
/*     */       
/* 126 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("中級裝備保護卷軸的力量附著的物品中。"));
/* 127 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/* 129 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予中階防爆", pc.getIp());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */