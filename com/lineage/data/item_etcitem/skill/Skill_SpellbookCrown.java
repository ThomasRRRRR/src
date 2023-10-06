/*    */ package com.lineage.data.item_etcitem.skill;
/*    */ 
/*    */ import com.lineage.data.cmd.Skill_Check;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class Skill_SpellbookCrown
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 14 */     return new Skill_SpellbookCrown();
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 19 */     if (item == null) {
/*    */       return;
/*    */     }
/*    */     
/* 23 */     if (pc == null) {
/*    */       return;
/*    */     }
/*    */     
/* 27 */     if (!pc.isCrown()) {
/*    */       
/* 29 */       S_ServerMessage msg = new S_ServerMessage(79);
/* 30 */       pc.sendPackets((ServerBasePacket)msg);
/*    */     }
/*    */     else {
/*    */       
/* 34 */       String nameId = item.getItem().getNameId();
/*    */       
/* 36 */       int skillid = 0;
/*    */       
/* 38 */       int attribute = 4;
/*    */       
/* 40 */       int magicLv = 0;
/*    */       
/* 42 */       if (nameId.equalsIgnoreCase("$1959")) {
/*    */         
/* 44 */         skillid = 113;
/*    */         
/* 46 */         magicLv = 21;
/*    */       }
/* 48 */       else if (nameId.equalsIgnoreCase("$2089")) {
/*    */         
/* 50 */         skillid = 116;
/*    */         
/* 52 */         magicLv = 22;
/*    */       }
/* 54 */       else if (nameId.equalsIgnoreCase("魔法書 (灼熱武器)")) {
/*    */         
/* 56 */         skillid = 114;
/*    */         
/* 58 */         magicLv = 23;
/*    */       }
/* 60 */       else if (nameId.equalsIgnoreCase("魔法書 (援護盟友)")) {
/*    */         
/* 62 */         skillid = 118;
/*    */         
/* 64 */         magicLv = 24;
/*    */       }
/* 66 */       else if (nameId.equalsIgnoreCase("魔法書 (勇猛意志)")) {
/* 67 */         skillid = 117;
/*    */         
/* 69 */         magicLv = 25;
/*    */       }
/* 71 */       else if (nameId.equalsIgnoreCase("魔法書 (閃亮之盾)")) {
/*    */         
/* 73 */         skillid = 115;
/*    */         
/* 75 */         magicLv = 26;
/*    */       }
/* 77 */       else if (nameId.equalsIgnoreCase("魔法書(王者加護)")) {
/* 78 */         skillid = 119;
/*    */         
/* 80 */         magicLv = 26;
/*    */       } 
/* 82 */       Skill_Check.check(pc, item, skillid, magicLv, attribute);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookCrown.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */