/*    */ package com.lineage.data.item_etcitem.skill;
/*    */ 
/*    */ import com.lineage.data.cmd.Skill_Check;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class Skill_SpellbookLv9
/*    */   extends ItemExecutor {
/*    */   public static ItemExecutor get() {
/* 13 */     return new Skill_SpellbookLv9();
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
/* 26 */     if (!pc.isWizard()) {
/*    */       
/* 28 */       S_ServerMessage msg = new S_ServerMessage(79);
/* 29 */       pc.sendPackets((ServerBasePacket)msg);
/*    */     }
/*    */     else {
/*    */       
/* 33 */       String nameId = item.getItem().getNameId();
/*    */       
/* 35 */       int skillid = 0;
/*    */ 
/*    */ 
/*    */       
/* 39 */       int attribute = 0;
/*    */       
/* 41 */       int magicLv = 9;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33118 && Y <= 32938 && X >= 33115 && Y >= 32936 && M == 4) {	//正義神殿(舊)
///*判斷地點學習*/  if (X <= 33152 && Y <= 32957 && X >= 33134 && Y >= 32940 && M == 4) {	//正義神殿(新)	
/*    */       
/* 43 */       if (nameId.equalsIgnoreCase("$557")) {
/*    */         
/* 45 */         skillid = 65;
/*    */         
/* 47 */         attribute = 0;
/*    */       }
/* 55 */       else if (nameId.equalsIgnoreCase("$559")) {
/*    */         
/* 57 */         skillid = 67;
/*    */         
/* 59 */         attribute = 0;
/*    */       }
/* 61 */       else if (nameId.equalsIgnoreCase("$560")) {
/*    */         
/* 63 */         skillid = 68;
/*    */         
/* 65 */         attribute = 1;
/*    */       }
/* 73 */       else if (nameId.equalsIgnoreCase("$1590")) {
/*    */         
/* 75 */         skillid = 70;
/*    */         
/* 77 */         attribute = 0;
/*    */       }
/* 79 */       else if (nameId.equalsIgnoreCase("$1870")) {
/*    */         
/* 81 */         skillid = 71;
/*    */         
/* 83 */         attribute = 0;
/*    */       }
/* 85 */       else if (nameId.equalsIgnoreCase("$1871")) {
/*    */         
/* 87 */         skillid = 72;
/*    */         
/* 89 */         attribute = 0;
/*    */       } 
/*    */       
/*    */     
///*判斷地點學習*/ }
/* 40 */	
///*判斷地點學習*/if (X <= 32889 && Y <= 32654 && X >= 32881 && Y >= 32650 && M == 4) {  //邪惡神殿
/*    */
/* 49 */       if (nameId.equalsIgnoreCase("$558")) {
/*    */         
/* 51 */         skillid = 66;
/*    */         
/* 53 */         attribute = 2;
/*    */       }
/* 67 */       else if (nameId.equalsIgnoreCase("$561")) {
/*    */         
/* 69 */         skillid = 69;
/*    */         
/* 71 */         attribute = 0;
/*    */       }
/*    */
///*判斷地點學習*/ }																	//邪惡神殿
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 92 */       Skill_Check.check(pc, item, skillid, 9, attribute);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookLv9.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */