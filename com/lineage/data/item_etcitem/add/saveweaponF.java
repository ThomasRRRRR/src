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
/*     */ public class saveweaponF
/*     */   extends ItemExecutor
/*     */ {
/*     */   private int _type;
/*     */   
/*     */   public static ItemExecutor get() {
/*  32 */     return new saveweaponF();
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
/*  50 */     int targObjId = data[0];
/*     */     
/*  52 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */ 
/*     */ 
/*     */     
/*  56 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  59 */     if (tgItem.isEquipped()) {
/*  60 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fR你必須先解除物品裝備!"));
/*     */       return;
/*     */     } 
/*  63 */     if (tgItem.getItem().getType2() == 1 && tgItem.getEnchantLevel() >= 15) {
/*  64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("武器已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  67 */     if (tgItem.getItem().getType2() == 2 && tgItem.getEnchantLevel() >= 11) {
/*  68 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  71 */     if (tgItem.getItem().getItemId() >= 120477 && tgItem.getItem().getItemId() <= 120479 && tgItem.getEnchantLevel() >= 9) {
/*  72 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  75 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*  76 */     boolean isErr = false;
/*     */ 
/*     */     
/*  79 */     int use_type = tgItem.getItem().getUseType();
/*  80 */     switch (use_type) {
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
/*  90 */         if (safe_enchant < 0) {
/*  91 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  96 */         isErr = true;
/*     */         break;
/*     */     } 
/*  99 */     if (tgItem.getBless() >= 128) {
/* 100 */       isErr = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (tgItem.getItem().getItemId() >= 301 && tgItem.getItem().getItemId() <= 305) {
/* 107 */       isErr = true;
/*     */     }
/* 109 */     if (isErr) {
/* 110 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/* 114 */     if (tgItem != null) {
/* 115 */       if (tgItem.getItem().getItemId() <= -1) {
/* 116 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         return;
/*     */       } 
/* 119 */       if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
/* 120 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 125 */       tgItem.setproctect5(true);
/* 126 */       pc.setproctctran(this._type);
/* 127 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 128 */       pc.getInventory().saveItem(tgItem, 4);
/* 129 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("高級裝備保護卷軸的力量附著的物品中。"));
/* 130 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("保護中的裝備機率性 " + this._type + "% 保護成功"));
/* 131 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/* 133 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予機率高階防爆", pc.getIp());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 139 */       this._type = Integer.parseInt(set[1]);
/*     */     }
/* 141 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponF.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */