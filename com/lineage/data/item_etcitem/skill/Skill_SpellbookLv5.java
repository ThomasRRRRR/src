/*    */ package com.lineage.data.item_etcitem.skill;
/*    */ 
/*    */ import com.lineage.data.cmd.Skill_Check;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ 
/*    */ public class Skill_SpellbookLv5
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 13 */     return new Skill_SpellbookLv5();
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
/* 26 */     String nameId = item.getItem().getNameId();
/*    */     
/* 28 */     int skillid = 0;
/*    */     
/* 30 */     int attribute = 0;
/*    */     
/* 32 */     int magicLv = 5;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33118 && Y <= 32938 && X >= 33115 && Y >= 32936 && M == 4) {	//正義神殿(舊)
///*判斷地點學習*/  if (X <= 33152 && Y <= 32957 && X >= 33134 && Y >= 32940 && M == 4) {	//正義神殿(新)	
/*    */     
/* 40 */     if (nameId.equalsIgnoreCase("$538")) {
/*    */       
/* 42 */       skillid = 34;
/*    */       
/* 44 */       attribute = 1;
/*    */     }
/* 46 */     else if (nameId.equalsIgnoreCase("$539")) {
/*    */       
/* 48 */       skillid = 35;
/*    */       
/* 50 */       attribute = 1;
/*    */     }
/* 52 */     else if (nameId.equalsIgnoreCase("$540")) {
/*    */       
/* 54 */       skillid = 36;
/*    */       
/* 56 */       attribute = 0;
/*    */     }
/* 58 */     else if (nameId.equalsIgnoreCase("$541")) {
/*    */       
/* 60 */       skillid = 37;
/*    */       
/* 62 */       attribute = 1;
/*    */     }
/* 64 */     else if (nameId.equalsIgnoreCase("$1587")) {
/*    */       
/* 66 */       skillid = 38;
/*    */       
/* 68 */       attribute = 0;
/*    */     }
/* 70 */     else if (nameId.equalsIgnoreCase("$1862")) {
/*    */       
/* 72 */       skillid = 39;
/*    */       
/* 74 */       attribute = 0;
/*    */     } 
/*    */     
/*    */     
///*判斷地點學習*/ }
/* 40 */	
///*判斷地點學習*/if (X <= 32889 && Y <= 32654 && X >= 32881 && Y >= 32650 && M == 4) {  //邪惡神殿
/* 34 */     if (nameId.equalsIgnoreCase("$537")) {
/*    */       
/* 36 */       skillid = 33;
/*    */       
/* 38 */       attribute = 2;
/*    */     }
/* 76 */     else if (nameId.equalsIgnoreCase("$1863")) {
/*    */       
/* 78 */       skillid = 40;
/*    */       
/* 80 */       attribute = 0;
/*    */     }
///*判斷地點學習*/ }																	//邪惡神殿
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 83 */     Skill_Check.check(pc, item, skillid, 5, attribute);
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookLv5.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */