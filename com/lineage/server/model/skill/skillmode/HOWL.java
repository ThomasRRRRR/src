package com.lineage.server.model.skill.skillmode;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_SkillSound;


public class HOWL extends SkillMode {
	
  public int start(L1PcInstance srcpc, L1Character cha, L1Magic magic, int integer)throws Exception {
	  int dmg = 50;

	  if ((cha instanceof L1PcInstance)) {
		  L1PcInstance pc = (L1PcInstance)cha;
		  pc.receiveDamage(srcpc, dmg, false, true);
		  pc.sendPacketsAll(new S_SkillSound(pc.getId(), 12563));
      
	  } else if (cha instanceof L1MonsterInstance || 
			  cha instanceof L1SummonInstance || 
			  cha instanceof L1PetInstance) {
		  L1NpcInstance npc = (L1NpcInstance)cha;
		  npc.receiveDamage(srcpc, dmg);
		  npc.broadcastPacketAll(new S_SkillSound(npc.getId(), 12563));
	  }     
      
      return dmg;
  }

  public int start(L1NpcInstance npc, L1Character cha, L1Magic magic, int integer)throws Exception {
	  int dmg = 0;
	  return dmg;
  }

  public void start(L1PcInstance srcpc, Object obj)throws Exception {
  }

  public void stop(L1Character cha)throws Exception {
  }
}

