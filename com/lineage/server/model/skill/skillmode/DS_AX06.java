package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_MPUpdate;

/**
 * 龍印魔石(弓手) 體力上限+35、魔力上限+20、遠距離命中率+1、體力恢復量+1
 * @author dexc
 *
 */
public class DS_AX06 extends SkillMode {

	private static final int _addhp = 35;
	private static final int _addmp = 20;
	private static final int _addhitbow = 1;
	private static final int _addhpr = 1;

	public DS_AX06() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.DS_AX06)) {
			srcpc.addMaxHp(_addhp);
			srcpc.addMaxMp(_addmp);
			srcpc.addBowHitup(_addhitbow);
			srcpc.addHpr(_addhpr);
			srcpc.setSkillEffect(L1SkillId.DS_AX06, integer * 1000);
			srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
			srcpc.sendPackets(new S_MPUpdate(srcpc));
		}
		return dmg;
	}

	@Override
	public int start(final L1NpcInstance npc, final L1Character cha, final L1Magic magic,
			final int integer) throws Exception {
		final int dmg = 0;
		
		return dmg;
	}

	@Override
	public void start(final L1PcInstance srcpc, final Object obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop(final L1Character cha) throws Exception {
		cha.addMaxHp(-_addhp);
		cha.addMaxMp(-_addmp);
		cha.addBowHitup(-_addhitbow);
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addHpr(-_addhpr);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			pc.sendPackets(new S_MPUpdate(pc));
		}
	}
}
