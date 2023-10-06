package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_HPUpdate;

/**
 * 龍印魔石(鬥士) 體力上限+70、近距離命中率+1、體力恢復量+2、近距離攻擊力+1
 * @author dexc
 *
 */
public class DS_GX07 extends SkillMode {

	private static final int _addhp = 70;
	private static final int _addhit = 1;
	private static final int _addhpr = 2;
	private static final int _adddmg = 1;

	public DS_GX07() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.DS_GX07)) {
			srcpc.addMaxHp(_addhp);
			srcpc.addHitup(_addhit);
			srcpc.addHpr(_addhpr);
			srcpc.addDmgup(_adddmg);
			srcpc.setSkillEffect(L1SkillId.DS_GX07, integer * 1000);
			srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
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
		cha.addHitup(-_addhit);
		cha.addDmgup(-_adddmg);
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addHpr(-_addhpr);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
		}
	}
}
