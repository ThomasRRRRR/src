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
/*    */ public class Skill_SpellbookLv6
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 13 */     return new Skill_SpellbookLv6();
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
/* 32 */     int magicLv = 6;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33118 && Y <= 32938 && X >= 33115 && Y >= 32936 && M == 4) {	//正義神殿(舊)
///*判斷地點學習*/  if (X <= 33152 && Y <= 32957 && X >= 33134 && Y >= 32940 && M == 4) {	//正義神殿(新)	
/*    */     
/* 40 */     if (nameId.equalsIgnoreCase("$543")) {
/*    */       
/* 42 */       skillid = 42;
/*    */       
/* 44 */       attribute = 0;
/*    */     }
/* 46 */     else if (nameId.equalsIgnoreCase("$544")) {
/*    */       
/* 48 */       skillid = 43;
/*    */       
/* 50 */       attribute = 0;
/*    */     }
/* 52 */     else if (nameId.equalsIgnoreCase("$545")) {
/*    */       
/* 54 */       skillid = 44;
/*    */       
/* 56 */       attribute = 1;
/*    */     }
/* 58 */     else if (nameId.equalsIgnoreCase("$546")) {
/*    */       
/* 60 */       skillid = 45;
/*    */       
/* 62 */       attribute = 0;
/*    */     }
/* 64 */     else if (nameId.equalsIgnoreCase("$1588")) {
/*    */       
/* 66 */       skillid = 46;
/*    */       
/* 68 */       attribute = 0;
/*    */     }
/* 76 */     else if (nameId.equalsIgnoreCase("$1865")) {
/*    */       
/* 78 */       skillid = 48;
/*    */       
/* 80 */       attribute = 0;
/*    */     } 
/*    */     
/*    */     
///*判斷地點學習*/ }
/* 40 */	
///*判斷地點學習*/if (X <= 32889 && Y <= 32654 && X >= 32881 && Y >= 32650 && M == 4) {  //邪惡神殿
/* 34 */     if (nameId.equalsIgnoreCase("$542")) {
/*    */       
/* 36 */       skillid = 41;
/*    */       
/* 38 */       attribute = 2;
/*    */     }
/* 70 */     else if (nameId.equalsIgnoreCase("$1864")) {
/*    */       
/* 72 */       skillid = 47;
/*    */       
/* 74 */       attribute = 2;
/*    */     }
///*判斷地點學習*/ }																	//邪惡神殿
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 83 */     Skill_Check.check(pc, item, skillid, 6, attribute);
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookLv6.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */