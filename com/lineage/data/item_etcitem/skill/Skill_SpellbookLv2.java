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
/*    */ public class Skill_SpellbookLv2
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 13 */     return new Skill_SpellbookLv2();
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
/*    */     
/*    */
/* 26 */	
/* 26 */
/* 26 */     String nameId = item.getItem().getNameId();
/*    */     
/* 28 */     int skillid = 0;
/*    */     
/* 30 */     int attribute = 0;
/*    */     
/* 32 */     int magicLv = 2;
/*    */     
/*    */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33118 && Y <= 32938 && X >= 33115 && Y >= 32936 && M == 4) {	//正義神殿(舊)
///*判斷地點學習*/  if (X <= 33152 && Y <= 32957 && X >= 33134 && Y >= 32940 && M == 4) {	//正義神殿(新)	
/*    */
/* 34 */     if (nameId.equalsIgnoreCase("$522")) {
/*    */       
/* 36 */       skillid = 9;
/*    */       
/* 38 */       attribute = 1;
/*    */     }
/* 52 */     else if (nameId.equalsIgnoreCase("$525")) {
/*    */       
/* 54 */       skillid = 12;
/*    */       
/* 56 */       attribute = 0;
/*    */     }
/* 58 */     else if (nameId.equalsIgnoreCase("$526")) {
/*    */       
/* 60 */       skillid = 13;
/*    */       
/* 62 */       attribute = 0;
/*    */     }
/* 64 */     else if (nameId.equalsIgnoreCase("$1858")) {
/*    */       
/* 66 */       skillid = 14;
/*    */       
/* 68 */       attribute = 0;
/*    */     }
/* 70 */     else if (nameId.equalsIgnoreCase("$1583")) {
/*    */       
/* 72 */       skillid = 15;
/*    */       
/* 74 */       attribute = 0;
/*    */     }
/* 76 */     else if (nameId.equalsIgnoreCase("$1584")) {
/*    */       
/* 78 */       skillid = 16;
/*    */       
/* 80 */       attribute = 0;
/*    */     } 
/*    */     
///*判斷地點學習*/ }
/* 40 */	
///*判斷地點學習*/if (X <= 32889 && Y <= 32654 && X >= 32881 && Y >= 32650 && M == 4) {  //邪惡神殿
/* 40 */	
/* 40 */     if (nameId.equalsIgnoreCase("$523")) {
/*    */       
/* 42 */       skillid = 10;
/*    */       
/* 44 */       attribute = 2;
/*    */     }
/* 46 */     else if (nameId.equalsIgnoreCase("$524")) {
/*    */       
/* 48 */       skillid = 11;
/*    */       
/* 50 */       attribute = 2;
/*    */   		}
///*判斷地點學習*/ }																	//邪惡神殿
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 83 */     Skill_Check.check(pc, item, skillid, 2, attribute);
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpellbookLv2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */