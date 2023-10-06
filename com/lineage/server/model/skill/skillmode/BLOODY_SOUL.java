/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import com.lineage.config.ConfigSkill;
import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BLOODY_SOUL
/*    */   extends SkillMode
/*    */ {
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 18 */     int dmg = 0;
/*    */ 
/*    */     
/* 21 */     int rr = 0;
/* 22 */     if (srcpc.getlogpcpower_SkillFor2() != 0 && 
/* 23 */       srcpc.getlogpcpower_SkillFor2() > 0) {
/* 24 */       rr += srcpc.getlogpcpower_SkillFor2() * 2;
/*    */     }
/*    */ 
/*    */     
/* 28 */     srcpc.setCurrentMp(srcpc.getCurrentMp() + ConfigSkill.BLOODY_SOULADDMP + rr);
/*    */ 
/*    */     
/* 31 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 37 */     int dmg = 0;
/*    */     
/* 39 */     return dmg;
/*    */   }
/*    */   
/*    */   public void start(L1PcInstance srcpc, Object obj) throws Exception {}
/*    */   
/*    */   public void stop(L1Character cha) throws Exception {}
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\BLOODY_SOUL.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */