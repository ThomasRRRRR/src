/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import com.lineage.config.ConfigSkill;
import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
/*    */ import com.lineage.server.serverpackets.S_PacketBoxIcon1;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ 
/*    */ public class MIRROR_IMAGE
/*    */   extends SkillMode
/*    */ {
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 14 */     int dmg = 0;
/* 15 */     if (!srcpc.hasSkillEffect(201)) {
/* 16 */       srcpc.setSkillEffect(201, integer * 1000);
/* 17 */       srcpc.add_dodge(ConfigSkill.MIRROR);
/*    */       
/* 19 */       srcpc.sendPackets((ServerBasePacket)new S_PacketBoxIcon1(true, srcpc.get_dodge()));
/*    */     } 
/* 21 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 27 */     int dmg = 0;
/*    */     
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void start(L1PcInstance srcpc, Object obj) throws Exception {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void stop(L1Character cha) throws Exception {
/* 40 */     if (cha instanceof L1PcInstance) {
/* 41 */       L1PcInstance pc = (L1PcInstance)cha;
///* 42 */       pc.add_dodge(-5);
/* 43 */       pc.sendPackets((ServerBasePacket)new S_PacketBoxIcon1(true, pc.get_dodge()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\MIRROR_IMAGE.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */