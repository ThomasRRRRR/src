package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_MPUpdate;

/**
 * 附魔石(遠攻) 【+8】HP+40、MP+25、體力恢復量+2、魔力恢復量+1
 * @author dexc
 *
 */
public class BS_AX08 extends SkillMode {

	private static final int _addhp = 40;
	private static final int _addmp = 25;
	private static final int _addhpr = 2;
	private static final int _addmpr = 1;

	public BS_AX08() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.BS_AX08)) {
			srcpc.addMaxHp(_addhp);
			srcpc.addMaxMp(_addmp);
			srcpc.addHpr(_addhpr);
			srcpc.addMpr(_addmpr);
			srcpc.setSkillEffect(L1SkillId.BS_AX08, integer * 1000);
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
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addHpr(-_addhpr);
			pc.addMpr(-_addmpr);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			pc.sendPackets(new S_MPUpdate(pc));
		}
	}
}
