/*    */ package com.lineage.data.item_etcitem;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_SPMR;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.S_SkillSound;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Battle_Reel
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 18 */     return new Battle_Reel();
/*    */   }
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 22 */     if (item == null) {
/*    */       return;
/*    */     }
/*    */     
/* 26 */     if (pc == null) {
/*    */       return;
/*    */     }
/*    */     
/* 30 */     if (!pc.hasSkillEffect(8060)) {
/* 31 */       pc.setSkillEffect(8060, 900000);
/* 32 */       pc.getInventory().removeItem(item, 1L);
/*    */       
/* 34 */       pc.addHitup(3);
/* 35 */       pc.addDmgup(3);
/* 36 */       pc.addBowHitup(3);
/* 37 */       pc.addBowDmgup(3);
/* 38 */       pc.addSp(3);
/* 39 */       pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*    */       
/* 41 */       pc.sendPacketsAll((ServerBasePacket)new S_SkillSound(pc.getId(), 6995));
/*    */     } else {
/* 43 */       int time = pc.getSkillEffectTimeSec(8060);
/*    */       
/* 45 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("戰鬥強化卷軸 剩餘時間(秒):" + time));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\Battle_Reel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */