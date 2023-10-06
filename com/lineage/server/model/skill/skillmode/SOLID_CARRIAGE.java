/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import com.lineage.config.ConfigSkill;
import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
/*    */ import com.lineage.server.serverpackets.S_PacketBox;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class SOLID_CARRIAGE
/*    */   extends SkillMode
/*    */ {
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 15 */     int dmg = 0;
/* 16 */     L1PcInstance pc = (L1PcInstance)cha;
/*    */     
if (ConfigSkill.SOLID_CARRIAGE_MODE == 0) {
/* 18 */     if (pc.getInventory().getTypeEquipped(2, 7) >= 1) {
/* 19 */       pc.setSkillEffect(90, integer * 1000);
/* 20 */       pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
/*    */     } else {
/*    */       
/* 23 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("你並未裝備盾牌或臂甲。"));
/*    */     } 
} else {
	if (pc.getInventory().getTypeEquipped(2, 7) >= 1 || pc.getInventory().getTypeEquipped(2, 13) >= 1) {
		pc.setSkillEffect(90, integer * 1000);
		pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
	} else {
		pc.sendPackets(new S_ServerMessage("你並未裝備盾牌或臂甲。"));
	}
}
/*    */     
/* 26 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 32 */     int dmg = 0;
/*    */     
/* 34 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void start(L1PcInstance srcpc, Object obj) throws Exception {}
/*    */ 
/*    */   
/*    */   public void stop(L1Character cha) throws Exception {
/* 43 */     if (cha instanceof L1PcInstance) {
/* 44 */       L1PcInstance pc = (L1PcInstance)cha;
/* 45 */       pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\SOLID_CARRIAGE.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */