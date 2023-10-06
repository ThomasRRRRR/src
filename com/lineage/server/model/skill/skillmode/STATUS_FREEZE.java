package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.serverpackets.S_Paralysis;
/**
 * 魔法效果:凍結狀態
 * @author dexc
 *
 */
public class STATUS_FREEZE extends SkillMode {
	public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
		int dmg = 0;
		if (!cha.hasSkillEffect(4000)) {
			cha.setSkillEffect(4000, 4000);
			if ((cha instanceof L1PcInstance)) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_Paralysis(6, true));
			} else if (((cha instanceof L1MonsterInstance)) || ((cha instanceof L1SummonInstance))
					|| ((cha instanceof L1PetInstance))) {
				L1NpcInstance tgnpc = (L1NpcInstance) cha;
				tgnpc.setParalyzed(true);
				tgnpc.setPassispeed(0);
			}
		}
		return dmg;
	}

	public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
		int dmg = 0;
		if (!cha.hasSkillEffect(4000)) {
			cha.setSkillEffect(4000, 4000);
			if ((cha instanceof L1PcInstance)) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_Paralysis(6, true));
			} else if (((cha instanceof L1MonsterInstance)) || ((cha instanceof L1SummonInstance))
					|| ((cha instanceof L1PetInstance))) {
				L1NpcInstance tgnpc = (L1NpcInstance) cha;
				tgnpc.setParalyzed(true);
				tgnpc.setPassispeed(0);
			}
		}
		return dmg;
	}

	public void start(L1PcInstance srcpc, Object obj) throws Exception {
	}

	public void stop(L1Character cha) throws Exception {
		if ((cha instanceof L1PcInstance)) {
			L1PcInstance pc = (L1PcInstance) cha;
			pc.sendPackets(new S_Paralysis(6, false));
		} else if (((cha instanceof L1MonsterInstance)) || ((cha instanceof L1SummonInstance))
				|| ((cha instanceof L1PetInstance))) {
			L1NpcInstance npc = (L1NpcInstance) cha;
			npc.setParalyzed(false);
			npc.setPassispeed(npc.getNpcTemplate().get_passispeed());
		}
	}
}

