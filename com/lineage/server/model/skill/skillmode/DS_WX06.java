package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_MPUpdate;

/**
 * 龍印魔石(賢者) 魔力上限+55、魔力恢復量+1
 * @author dexc
 *
 */
public class DS_WX06 extends SkillMode {

	private static final int _addmp = 55;
	private static final int _addmpr = 1;

	public DS_WX06() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.DS_WX06)) {
			srcpc.addMaxMp(_addmp);
			srcpc.addMpr(_addmpr);
			srcpc.setSkillEffect(L1SkillId.DS_WX06, integer * 1000);
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
		cha.addMaxMp(-_addmp);
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addMpr(-_addmpr);
			pc.sendPackets(new S_MPUpdate(pc));
		}
	}
}
