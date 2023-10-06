package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;

/**
 * 附魔石(遠攻) 【+9】HP+50、MP+30、體力恢復量+2、魔力恢復量+2、遠距離攻擊力+2、遠距離命中率+2、敏捷+1
 * @author dexc
 *
 */
public class BS_AX09 extends SkillMode {

	private static final int _addhp = 50;
	private static final int _addmp = 30;
	private static final int _addhitbow = 2;
	private static final int _addhpr = 2;
	private static final int _addmpr = 2;
	private static final int _adddmgbow = 2;
	private static final int _adddex = 1;

	public BS_AX09() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.BS_AX09)) {
			srcpc.addMaxHp(_addhp);
			srcpc.addMaxMp(_addmp);
			srcpc.addBowHitup(_addhitbow);
			srcpc.addHpr(_addhpr);
			srcpc.addMpr(_addmpr);
			srcpc.addBowDmgup(_adddmgbow);
			srcpc.addDex(_adddex);
			srcpc.setSkillEffect(L1SkillId.BS_AX09, integer * 1000);
			srcpc.sendPackets(new S_HPUpdate(srcpc.getCurrentHp(), srcpc.getMaxHp()));
			srcpc.sendPackets(new S_MPUpdate(srcpc));
			srcpc.sendPackets(new S_OwnCharStatus2(srcpc));
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
		cha.addBowDmgup(-_adddmgbow);
		cha.addDex(-_adddex);
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addHpr(-_addhpr);
			pc.addMpr(-_addmpr);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			pc.sendPackets(new S_MPUpdate(pc));
			pc.sendPackets(new S_OwnCharStatus2(pc));
		}
	}
}
