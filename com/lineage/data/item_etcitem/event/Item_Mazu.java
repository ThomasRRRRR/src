/*    */ package com.lineage.data.item_etcitem.event;
/*    */ 
/*     */ import com.lineage.config.ConfigOther;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;

/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Item_Mazu
/*    */   extends ItemExecutor
/*    */ {
/* 16 */   private static final Log _log = LogFactory.getLog(Item_Mazu.class);
/*    */ 
/*    */   
/*    */   public static ItemExecutor get() {
/* 20 */     return new Item_Mazu();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*    */     try {
/* 27 */       if (item == null) {
/*    */         return;
/*    */       }
/*    */       
/* 31 */       if (pc == null) {
/*    */         return;
/*    */       }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       if (pc.hasSkillEffect(8591)) {
/* 44 */         int i = pc.getSkillEffectTimeSec(8591);
/* 45 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("媽祖的祝福效果剩餘時間(秒):" + i));
/*    */         return;
/*    */       } 
/*    */       pc.set_mazu(true);
/*  73 */      pc.setSkillEffect(8591, ConfigOther.montime * 60 * 1000);
/* 51 */       pc.getInventory().removeItem(item, 1L);
/*    */ 	   pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 7321, 3600000));
/*    */       pc.set_mazu_time((ConfigOther.montime * 60 * 1000));
/* 54 */       
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 61 */     catch (Exception e) {
/* 62 */       _log.error(e.getLocalizedMessage(), e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\item_etcitem\event\Item_Mazu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */