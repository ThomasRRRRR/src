/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
/*    */ 
/*    */ 
/*    */ public class CONFUSION_DOLL
/*    */   extends SkillMode
/*    */ {
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 13 */     int dmg = 0;
/* 14 */     if (!cha.hasSkillEffect(8912)) {
/* 15 */       cha.setSkillEffect(8912, integer * 1000);
/*    */     }
/*    */     
/* 18 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
	/* 13 */     int dmg = 0;
	/* 14 */     if (!cha.hasSkillEffect(8912)) {
	/* 15 */       cha.setSkillEffect(8912, integer * 1000);
	/*    */     }
	/*    */     
	/* 18 */     return dmg;
/*    */   }
/*    */   
/*    */   public void start(L1PcInstance srcpc, Object obj) throws Exception {}
/*    */   
/*    */   public void stop(L1Character cha) throws Exception {}
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\CONFUSION.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */