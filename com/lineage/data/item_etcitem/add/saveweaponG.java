/*    */ package com.lineage.data.item_etcitem.add;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.datatables.ItemPowerUpdateTable;
/*    */ import com.lineage.server.datatables.RecordTable;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ItemStatus;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ import com.lineage.server.templates.L1ItemPowerUpdate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class saveweaponG
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 34 */     return new saveweaponG();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 50 */     int targObjId = data[0];
/*    */     
/* 52 */     int itemobj = data[0];
/* 53 */     L1ItemInstance item1 = pc.getInventory().getItem(itemobj);
/*    */     
/* 55 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*    */     
/* 57 */     if (tgItem == null) {
/*    */       return;
/*    */     }
/*    */     
/* 61 */     L1ItemPowerUpdate info = ItemPowerUpdateTable.get().get(item1.getItemId());
/* 62 */     if (info == null) {
/*    */       
/* 64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*    */       return;
/*    */     } 
/* 67 */     if (info.get_mode() == 4) {
/* 68 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*    */       return;
/*    */     } 
/* 71 */     if (tgItem != null) {
/* 72 */       if (tgItem.getItem().getItemId() <= -1) {
/* 73 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*    */         return;
/*    */       } 
/* 76 */       if (tgItem.getproctect6()) {
/* 77 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前該裝備已經受到保護中"));
/*    */         
/*    */         return;
/*    */       } 
/*    */       
/* 82 */       tgItem.setproctect6(true);
/* 83 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 84 */       pc.getInventory().saveItem(tgItem, 4);
/* 85 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("保護卷軸的力量附著的物品中。"));
/* 86 */       pc.getInventory().removeItem(item, 1L);
/*    */       
/* 88 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "賦予升接保護", pc.getIp());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\add\saveweaponG.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */