package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_SPMR;

/**
 * 龍印魔石(衝鋒) 防禦-5、魔法防禦額外點數+15、額外傷害減免+2、昏迷耐性+2
 * @author dexc
 *
 */
public class DS_ASX08 extends SkillMode {

	private static final int _addac = -5;
	private static final int _addmr = 15;
	private static final int _adddmgdown = 2;
	private static final int _addstun = 2;

	public DS_ASX08() {
	}

	@Override
	public int start(final L1PcInstance srcpc, final L1Character cha, final L1Magic magic, final int integer) throws Exception {
		final int dmg = 0;
		if (!srcpc.hasSkillEffect(L1SkillId.DS_ASX08)) {
			srcpc.addAc(_addac);
			srcpc.addMr(_addmr);
			srcpc.addDamageReductionByArmor(_adddmgdown);
			srcpc.addRegistStun(_addstun);
			srcpc.setSkillEffect(L1SkillId.DS_ASX08, integer * 1000);
			srcpc.sendPackets(new S_OwnCharAttrDef(srcpc));
			srcpc.sendPackets(new S_SPMR(srcpc));
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
		cha.addAc(-_addac);
		cha.addMr(-_addmr);
		cha.addRegistStun(-_addstun);
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			pc.addDamageReductionByArmor(-_adddmgdown);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			pc.sendPackets(new S_SPMR(pc));
		}
	}
}
