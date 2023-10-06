package com.lineage.server.model.skill.skillmode;

import static com.lineage.server.model.skill.L1SkillId.FIRE_BLESS;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_SkillBrave;

public class FIRE_BLESS extends SkillMode {
	public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer) throws Exception {
		int dmg = 0;

		L1BuffUtil.braveStart(srcpc);

		srcpc.setSkillEffect(FIRE_BLESS, integer * 1000);

		srcpc.setBraveSpeed(1);
		srcpc.sendPackets(new S_SkillBrave(srcpc.getId(), 1, integer));
		srcpc.broadcastPacketAll(new S_SkillBrave(srcpc.getId(), 1, 0));
		srcpc.sendPackets(new S_PacketBoxIconAura(154, integer));

		return dmg;
	}

	public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer) throws Exception {
		return 0;
	}

	public void start(L1PcInstance srcpc, Object obj) throws Exception {
	}

	public void stop(L1Character cha) throws Exception {
		cha.setBraveSpeed(0);
		if ((cha instanceof L1PcInstance)) {
			L1PcInstance pc = (L1PcInstance) cha;
			pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
			pc.sendPackets(new S_PacketBoxIconAura(154, 0));
		}
	}
}

/*
 * Location: C:\Users\glhl\Downloads\古龍互聯\Server_Game.jar Qualified Name:
 * com.lineage.server.model.skill.skillmode.BLOODLUST JD-Core Version: 0.6.2
 */