/*    */ package com.lineage.data.item_etcitem.event;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.skill.L1BuffUtil;
/*    */ import com.lineage.server.serverpackets.S_PacketBoxCooking;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.S_SkillSound;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class Mazu
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 16 */     return new Mazu();
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 21 */     if (item == null) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     if (pc == null) {
/*    */       return;
/*    */     }
/*    */     
/* 29 */     if (L1BuffUtil.cancelMazu(pc)) {
	/* 44 */ int i = pc.getSkillEffectTimeSec(8591);
	/* 45 */ pc.sendPackets((ServerBasePacket)new S_ServerMessage("媽祖的祝福效果剩餘時間(秒):" + i));
/*    */       
/*    */       
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\item_etcitem\event\Exp13_1.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */