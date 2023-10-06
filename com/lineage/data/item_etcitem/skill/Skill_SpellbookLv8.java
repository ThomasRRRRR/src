/*    */ package com.lineage.data.item_etcitem.skill;
/*    */ 
/*    */ import com.lineage.data.cmd.Skill_Check;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class Skill_SpellbookLv8
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 14 */     return new Skill_SpellbookLv8();
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
/* 27 */     if (!pc.isWizard()) {
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
/* 38 */       int attribute = 0;
/*    */       
/* 40 */       int magicLv = 8;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33118 && Y <= 32938 && X >= 33115 && Y >= 32936 && M == 4) {	//正義神殿(舊)
///*判斷地點學習*/  if (X <= 33152 && Y <= 32957 && X >= 33134 && Y >= 32940 && M == 4) {	//正義神殿(新)	
/*    */       
/* 42 */       if (nameId.equalsIgnoreCase("$552")) {
/*    */         
/* 44 */         skillid = 57;
/*    */         
/* 46 */         attribute = 1;
/*    */       }
/* 48 */       else if (nameId.equalsIgnoreCase("$553")) {
/*    */         
/* 50 */         skillid = 58;
/*    */         
/* 52 */         attribute = 0;
/*    */       }
/* 60 */       else if (nameId.equalsIgnoreCase("$555")) {
/*    */         
/* 62 */         skillid = 60;
/*    */         
/* 64 */         attribute = 0;
/*    */       }
/* 66 */       else if (nameId.equalsIgnoreCase("$556")) {
/*    */         
/* 68 */         skillid = 61;
/*    */         
/* 70 */         attribute = 1;
/*    */       }
/* 72 */       else if (nameId.equalsIgnoreCase("$1589")) {
/*    */         
/* 74 */         skillid = 62;
/*    */         
/* 76 */         attribute = 0;
/*    */       }
/* 78 */       else if (nameId.equalsIgnoreCase("$1868")) {
/*    */         
/* 80 */         skillid = 63;
/*    */         
/* 82 */         attribute = 0;
/*    */       }
/* 84 */       else if (nameId.equalsIgnoreCase("$1869")) {
/*    */         
/* 86 */         skillid = 64;
/*    */         
/* 88 */         attribute = 0;
/*    */       } 
/*    */       
/*    */     
///*判斷地點學習*/ }
/* 40 */	
///*判斷地點學習*/if (X <= 32889 && Y <= 32654 && X >= 32881 && Y >= 32650 && M == 4) {  //邪惡神殿
/*    */
/* 54 */       if (nameId.equalsIgnoreCase("$554")) {
/*    */         
/* 56 */         skillid = 59;
/*    */         
/* 58 */         attribute = 2;
/*    */       }
/*    */
///*判斷地點學習*/ }																	//邪惡神殿
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 91 */       Skill_Check.check(pc, item, skillid, 8, attribute);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookLv8.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */