/*    */ package com.lineage.data.item_etcitem;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.S_SkillSound;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Dice
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 15 */     return new Dice();
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 20 */     if (!pc.getInventory().checkItem(40318, 1L)) {
/* 21 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("魔法寶石不足1個"));
/*    */       return;
/*    */     } 
/* 24 */     int itemId = item.getItemId();
/* 25 */     Random _random = new Random();
/* 26 */     int gfxid = 0;
/* 27 */     switch (itemId) {
/*    */       case 40325:
/* 29 */         if (pc.getInventory().checkItem(40318, 1L)) {
/* 30 */           gfxid = 3237 + _random.nextInt(2);
/*    */           break;
/*    */         } 
/* 33 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(299));
/*    */         break;
/*    */       
/*    */       case 40326:
/* 37 */         if (pc.getInventory().checkItem(40318, 1L)) {
/* 38 */           gfxid = 3229 + _random.nextInt(3);
/*    */           break;
/*    */         } 
/* 41 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(299));
/*    */         break;
/*    */       
/*    */       case 40327:
/* 45 */         if (pc.getInventory().checkItem(40318, 1L)) {
/* 46 */           gfxid = 3241 + _random.nextInt(4);
/*    */           break;
/*    */         } 
/* 49 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(299));
/*    */         break;
/*    */       
/*    */       case 40328:
/* 53 */         if (pc.getInventory().checkItem(40318, 1L)) {
/* 54 */           gfxid = 3204 + _random.nextInt(6);
/*    */           break;
/*    */         } 
/* 57 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(299));
/*    */         break;
/*    */     } 
/*    */ 
/*    */     
/* 62 */     if (gfxid != 0) {
/* 63 */       pc.getInventory().consumeItem(40318, 1L);
/* 64 */       pc.sendPacketsAll((ServerBasePacket)new S_SkillSound(pc.getId(), gfxid));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\Dice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */