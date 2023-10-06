/*    */ package com.lineage.server.model.skill.skillmode;
/*    */ 
/*    */ import java.util.Random;

import com.lineage.config.ConfigSkill;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
/*    */ import com.lineage.server.model.L1Character;
/*    */ import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1PinkName;
/*    */ import com.lineage.server.serverpackets.S_PacketBoxDk;
import com.lineage.server.serverpackets.S_Paralysis;
/*    */ import com.lineage.server.serverpackets.S_SkillSound;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.utils.L1SpawnUtil;
/*    */ 
/*    */ public class FOE_SLAYER
/*    */   extends SkillMode
/*    */ {
	private static final Random _random = new Random();
/*    */   public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 15 */     int dmg = 0;
/* 16 */     srcpc.setFoeSlayer(true);
/*    */     
/* 18 */     for (int i = 0; i < 3; i++) {
/* 19 */       cha.onAction(srcpc);
/* 20 */       if (i == 2) {// 第三次
/* 21 */         srcpc.setFoeSlayer(false);
/* 22 */         srcpc.set_weaknss(0, 0L);
/* 23 */         srcpc.sendPackets((ServerBasePacket)new S_PacketBoxDk(0));
/*    */       } 
/*    */     } 
/*    */     
/* 27 */     srcpc.sendPacketsAll((ServerBasePacket)new S_SkillSound(srcpc.getId(), 7020));// 屠宰者 加速封包
/* 28 */     srcpc.sendPacketsAll((ServerBasePacket)new S_SkillSound(cha.getId(), 12119));// 屠宰者 特效動畫
/*    */     dmg += _random.nextInt(ConfigSkill.FOE_SLAYER_DMG) + 1;
if (ConfigSkill.FOE_SLAYER_RND != 0) {
	if (!cha.hasSkillEffect(87) &&
			_random.nextInt(100) <= ConfigSkill.FOE_SLAYER_RND) {
		cha.setSkillEffect(87, ConfigSkill.FOE_SLAYER_SEC * 1000);
		L1SpawnUtil.spawnEffect
		(81162, ConfigSkill.FOE_SLAYER_SEC, cha.getX(), 
				cha.getY(), srcpc.getMapId(), srcpc, 0);
		if ((cha instanceof L1PcInstance)) {
			L1PcInstance pc = (L1PcInstance) cha;
			pc.sendPackets(new S_Paralysis(5, true));
			L1PinkName.onAction(pc, srcpc);
		} else if (((cha instanceof L1MonsterInstance)) || ((cha instanceof L1SummonInstance))
				|| ((cha instanceof L1PetInstance))) {
			L1NpcInstance tgnpc = (L1NpcInstance) cha;
			tgnpc.setParalyzed(true);
		}
	}
}
/* 30 */     return dmg;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
/* 36 */     int dmg = 0;
/* 37 */     for (int i = 0; i < 3; i++) {
/* 38 */       npc.attackTarget(cha);
/*    */     }
/* 40 */     npc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(cha.getId(), 7020));
/* 41 */     npc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(cha.getId(), 12119));
/* 42 */     return dmg;
/*    */   }
/*    */   
/*    */   public void start(L1PcInstance srcpc, Object obj) throws Exception {}
/*    */   
/*    */   public void stop(L1Character cha) throws Exception {}
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skill\skillmode\FOE_SLAYER.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */