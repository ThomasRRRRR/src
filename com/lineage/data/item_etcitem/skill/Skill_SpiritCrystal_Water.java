/*    */ package com.lineage.data.item_etcitem.skill;
/*    */ 
/*    */ import com.lineage.data.cmd.Skill_Check;
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class Skill_SpiritCrystal_Water
/*    */   extends ItemExecutor
/*    */ {
/*    */   public static ItemExecutor get() {
/* 14 */     return new Skill_SpiritCrystal_Water();
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
/* 27 */     if (!pc.isElf()) {
/*    */       
/* 29 */       S_ServerMessage msg = new S_ServerMessage(79);
/* 30 */       pc.sendPackets((ServerBasePacket)msg);
/*    */     }
/* 32 */     else if (pc.getElfAttr() != 4) {
/*    */       
/* 34 */       S_ServerMessage msg = new S_ServerMessage(684);
/* 35 */       pc.sendPackets((ServerBasePacket)msg);
/*    */     }
/*    */     else {
/*    */       
/* 39 */       String nameId = item.getItem().getNameId();
/*    */       
/* 41 */       int skillid = 0;
/*    */       
/* 43 */       int attribute = 3;
/*    */       
/* 45 */       int magicLv = 0;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33055 && Y <= 32343 && X >= 33048 && Y >= 32336 && M == 4) {	//妖精森林
/*    */       
/* 47 */       if (nameId.equalsIgnoreCase("$3266")) {
/*    */         
/* 49 */         skillid = 170;
/*    */         
/* 51 */         magicLv = 13;
/*    */       }
/* 53 */       else if (nameId.equalsIgnoreCase("$1847")) {
/*    */         
/* 55 */         skillid = 158;
/*    */         
/* 57 */         magicLv = 14;
/*    */       }
/* 59 */       else if (nameId.equalsIgnoreCase("$4716")) {
/*    */         
/* 61 */         skillid = 160;
/*    */         
/* 63 */         magicLv = 14;
/*    */       }
/* 65 */       else if (nameId.equalsIgnoreCase("$1852")) {
/*    */         
/* 67 */         skillid = 164;
/*    */         
/* 69 */         magicLv = 15;
/*    */       }
/* 71 */       else if (nameId.equalsIgnoreCase("$1853")) {
/*    */         
/* 73 */         skillid = 165;
/*    */         
/* 75 */         magicLv = 15;
/*    */       }
/* 77 */       else if (nameId.equalsIgnoreCase("$4717")) {
/*    */         
/* 79 */         skillid = 173;
/*    */         
/* 81 */         magicLv = 15;
/*    */       } 
/*    */       
/*    */     
///*判斷地點學習*/ }
/* 40 */																		
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 84 */       Skill_Check.check(pc, item, skillid, magicLv, 3);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpiritCrystal_Water.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */