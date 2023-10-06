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
/*     */ public class saveweaponE
/*     */   extends ItemExecutor
/*     */ {
/*     */   private int _type;
/*     */   
/*     */   public static ItemExecutor get() {
/*  32 */     return new saveweaponE();
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
/*  54 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  57 */     if (tgItem.isEquipped()) {
/*  58 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fR你必須先解除物品裝備!"));
/*     */       return;
/*     */     } 
/*  61 */     if (tgItem.getItem().getType2() == 1 && tgItem.getEnchantLevel() >= 15) {
/*  62 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("武器已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  65 */     if (tgItem.getItem().getType2() == 2 && tgItem.getEnchantLevel() >= 11) {
/*  66 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  69 */     if (tgItem.getItem().getItemId() >= 120477 && tgItem.getItem().getItemId() <= 120479 && tgItem.getEnchantLevel() >= 9) {
/*  70 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("防具已達到裝備保護卷的最高保護階段。"));
/*     */       return;
/*     */     } 
/*  73 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*  74 */     boolean isErr = false;
/*     */ 
/*     */     
/*  77 */     int use_type = tgItem.getItem().getUseType();
/*  78 */     switch (use_type) {
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
/*  88 */         if (safe_enchant < 0) {
/*  89 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  94 */         isErr = true;
/*     */         break;
/*     */     } 
/*  97 */     if (tgItem.getBless() >= 128) {
/*  98 */       isErr = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (tgItem.getItem().getItemId() >= 301 && tgItem.getItem().getItemId() <= 305) {
/* 105 */       isErr = true;
/*     */     }
/* 107 */     if (isErr) {
/* 108 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 113 */     if (tgItem != null) {
/* 114 */       if (tgItem.getItem().get_safeenchant() <= -1) {
/* 115 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */         return;
/*     */       } 
/* 118 */       if (tgItem.getproctect() || tgItem.getproctect1() || tgItem.getproctect2()) {
/* 119 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*     */         
/*     */         return;
/*     */       } 
/* 123 */       tgItem.setproctect4(true);
/* 124 */       pc.setproctctran(this._type);
/* 125 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 126 */       pc.getInventory().saveItem(tgItem, 4);
/*     */       
/* 128 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("中級裝備保護卷軸的力量附著的物品中。"));
/* 129 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("保護中的裝備機率性 " + this._type + "% 保護成功"));
/* 130 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/* 132 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予機率中階防爆", pc.getIp());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 138 */       this._type = Integer.parseInt(set[1]);
/*     */     }
/* 140 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponE.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */