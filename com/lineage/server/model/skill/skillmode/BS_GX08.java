package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_HPUpdate;

/**
 * 附魔石(近戰) 【+8】HP+80、體力恢復量+4、近距離命中率+1
 * @author dexc
 *
 */
public class BS_GX08 extends SkillMode {

	private static final int _addhp = 80;
	private static final int _addhit = 1;
	private static final int _addhpr = 4;

	public BS_GX08() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.BS_GX08)) {
			srcpc.addMaxHp(_addhp);
			srcpc.addHitup(_addhit);
			srcpc.addHpr(_addhpr);
			srcpc.setSkillEffect(L1SkillId.BS_GX08, integer * 1000);
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
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addHpr(-_addhpr);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
		}
	}
}
