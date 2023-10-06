package com.lineage.server.timecontroller.skill;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;;

/**
 * 戰斧投擲傷害處理時間軸
 */
public class Skill_TomaHawk_Timer extends TimerTask {

	private static final Log _log = LogFactory.getLog(Skill_TomaHawk_Timer.class);

	private ScheduledFuture<?> _timer;
	private int _timeCounter = 0;
	private final L1PcInstance _pc;
	private final L1Character _cha;
	private int _dmg = 0;
	
	public Skill_TomaHawk_Timer(L1PcInstance pc, L1Character cha, int dmg) {
		_pc = pc;
		_cha = cha;
		_dmg = dmg;
	}

	public void start() {
		final int timeMillis = 1000;
		_timer = GeneralThreadPool.get().scheduleAtFixedRate(this, timeMillis, timeMillis);
	}

	@Override
	public void run() {
		try {
			if (_cha == null || _cha.isDead()) {
				stop();
				return;
			}
			_timeCounter++;
			attack();

			if (_timeCounter >= 6) {
				stop();
				return;
			}
		
		} catch (final Exception e) {
			_log.error("戰斧投擲傷害處理時間軸異常重啟", e);
			GeneralThreadPool.get().cancel(_timer, false);
			final Skill_TomaHawk_Timer TomaHawk_Timer = new Skill_TomaHawk_Timer(_pc, _cha, _dmg);
			TomaHawk_Timer.start();
		}
	}

	public void stop() {
		if (_timer != null) {
			_timer.cancel(false);
		}
	}

	public void attack() {		
		if (_cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) _cha;
			pc.sendPacketsAll(new S_SkillSound(pc.getId(), 12617));
			pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 2));
			pc.receiveDamage(_pc, _dmg, false, true);	
			
		} else if (_cha instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) _cha;
			npc.broadcastPacketAll(new S_SkillSound(npc.getId(), 12617));
			npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 2));
			npc.receiveDamage(_pc, _dmg);
		}
	}

}
