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
/*     */ public class saveweaponD
/*     */   extends ItemExecutor
/*     */ {
/*     */   private int _type;
/*     */   
/*     */   public static ItemExecutor get() {
/*  31 */     return new saveweaponD();
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
/* 111 */     if (tgItem != null) {
/* 112 */       if (tgItem.getItem().get_safeenchant() <= -1) {
/* 113 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         return;
/*     */       } 
/* 116 */       if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
/* 117 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*     */         
/*     */         return;
/*     */       } 
/* 121 */       tgItem.setproctect3(true);
/* 122 */       pc.setproctctran(this._type);
/* 123 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 124 */       pc.getInventory().saveItem(tgItem, 4);
/*     */       
/* 126 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("初級裝備保護卷軸的力量附著的物品中。"));
/* 127 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("保護中的裝備機率性 " + this._type + "% 保護成功"));
/* 128 */       pc.getInventory().removeItem(item, 1L);
/* 129 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予機率低階防爆", pc.getIp());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 135 */       this._type = Integer.parseInt(set[1]);
/*     */     }
/* 137 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */