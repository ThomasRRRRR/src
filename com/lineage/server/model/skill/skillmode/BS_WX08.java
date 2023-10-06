package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_MPUpdate;

/**
 * 附魔石(恢復) 【+8】MP+40、魔力恢復量+4
 * @author dexc
 *
 */
public class BS_WX08 extends SkillMode {

	private static final int _addmp = 40;
	private static final int _addmpr = 4;

	public BS_WX08() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.BS_WX08)) {
			srcpc.addMaxMp(_addmp);
			srcpc.addMpr(_addmpr);
			srcpc.setSkillEffect(L1SkillId.BS_WX08, integer * 1000);
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
