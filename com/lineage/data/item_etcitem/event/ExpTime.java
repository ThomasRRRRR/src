/*    */ package com.lineage.data.item_etcitem.event;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.skill.L1BuffUtil;

/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class ExpTime
/*    */   extends ItemExecutor {
/*    */   public static ItemExecutor get() {
/* 13 */     return new ExpTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 18 */     if (item == null) {
/*    */       return;
/*    */     }
/*    */     
/* 22 */     if (pc == null) {
/*    */       return;
/*    */     }
/*    */     
/* 26 */     if (L1BuffUtil.cancelExpSkill(pc)) {
/* 27 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX目前沒有第一段經驗藥水的效果。"));
/*    */     }
/*    */     
/* 30 */     if (L1BuffUtil.cancelExpSkill_2(pc)) {
/* 31 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY目前沒有第二段神力藥水的效果。"));
}
/* 43 */       if (L1BuffUtil.cancelMazu(pc)) 
/* 45 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY目前沒有媽祖的祝福。"));
}
/*    */   
/*    */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\item_etcitem\event\ExpTime.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */