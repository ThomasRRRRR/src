/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
/*    */ 
/*    */ 
/*    */ public class DECAY_POTION_DOLL
/*    */   extends SkillMode
/*    */ {
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 13 */     int dmg = 0;
/* 14 */     if (cha.hasSkillEffect(8911)) {
/* 15 */       return 0;
/*    */     }
/* 17 */     cha.set_decay_potion(true);
/* 18 */     cha.setSkillEffect(8911, integer * 1000);
/* 19 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 25 */     int dmg = 0;
/* 26 */     if (cha.hasSkillEffect(8911)) {
/* 27 */       return 0;
/*    */     }
/* 29 */     cha.set_decay_potion(true);
/* 30 */     cha.setSkillEffect(8911, integer * 1000);
/* 31 */     return dmg;
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
/* 42 */     cha.set_decay_potion(false);
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\DECAY_POTION.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */