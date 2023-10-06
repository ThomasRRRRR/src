package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigSkill;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.serverpackets.S_SkillSound;

public class TRIPLE_ARROW extends SkillMode {
	
	public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
		int dmg = 0;
	//	boolean gfxcheck = false;
		int playerGFX = srcpc.getTempCharGfx();//玩家變身外型
//		System.out.println(playerGFX);
//		switch (playerGFX) {
//		case 23268:
//		case 23248:
//			gfxcheck = true;
//			break;
//		}
//		if (!gfxcheck) {
//			return dmg;
//		}
		final int attack21 = SprTable.get().getAttackSpeed(playerGFX, 21);
		if (attack21 == 0) {// 沒有弓箭攻擊動作資料
			return 0;
		}
		
		if (srcpc.getCurrentWeapon() != 20) {//目前武器動作外型不是拿弓
			return 0;
		}
		
		if (ConfigSkill.TRIPLE_ARROW_DMG > 1) {
			srcpc.setTripleArrow(true);
		}
		for (int i = 0; i < 3; i++) {
			cha.onAction(srcpc);
		}
		if (ConfigSkill.TRIPLE_ARROW_DMG > 1) {
			srcpc.setTripleArrow(false);
		}
		// 三重矢 加速封包
		srcpc.sendPacketsX8(new S_SkillSound(srcpc.getId(), 4394));
		srcpc.sendPacketsAll(new S_SkillSound(srcpc.getId(), 11764));// 特效動畫
//		Thread.sleep(300);
		return dmg;
	}

	public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
		int dmg = 0;
		for (int i = 3; i > 0; i--) {
			npc.attackTarget(cha);
		}

		npc.broadcastPacketX10(new S_SkillSound(npc.getId(), 4394));// 加速封包
		npc.broadcastPacketAll(new S_SkillSound(npc.getId(), 11764));// 特效動畫
//		Thread.sleep(300);
		return dmg;
	}

	public void start(L1PcInstance srcpc, Object obj) throws Exception {
	
	}

	public void stop(L1Character cha) throws Exception {
	
	}
}