package com.lineage.server.timecontroller.skill;

import static com.lineage.server.model.skill.L1SkillId.BRAVE_AVATAR;

import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;

public class BraveAvatarTimer extends TimerTask {
	
  private static final Log _log = LogFactory.getLog(BraveAvatarTimer.class);
  
  private ScheduledFuture<?> _timer;

  public void start() {
	  int timeMillis = 5000;//5秒
	  _timer = GeneralThreadPool.get().scheduleAtFixedRate(this, timeMillis, timeMillis);
  }

  @Override
  public void run() {
	try {
		for (L1PcInstance pc : World.get().getAllPlayers()) {
			
			if (pc == null || pc.getNetConnection() == null) {
				continue;
			}
			
			if (pc.getParty() != null) {//具有隊伍
				L1PcInstance leader = pc.getParty().getLeader();
				if (leader.isCrown()//隊長是王族
					&& leader.isSkillMastery(119)//隊長有學習王者加護
					&& leader.getLocation().getTileLineDistance(pc.getLocation()) <= 16) {//16格範圍內
					
					if (pc.getParty().getNumOfMembers() >= 2) {//2人以上
						if (!pc.hasSkillEffect(BRAVE_AVATAR)) {//身上沒有王者加護效果
							pc.setSkillEffect(BRAVE_AVATAR, 0);
							pc.addStr(1);
							pc.addDex(1);
							pc.addInt(1);
							pc.addMr(10);
							pc.addRegistStun(2);
							pc.addRegistSustain(2);
							pc.sendPackets(new S_SPMR(pc));
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPacketsAll(new S_SkillSound(pc.getId(), 9009));
							pc.sendPackets(new S_PacketBox(S_PacketBox.NONE_TIME_ICON, 1, 479));
						}					
					}
					
				} else if (leader.getLocation().getTileLineDistance(pc.getLocation()) > 16) {//與隊長距離超過16格
					if (pc.hasSkillEffect(BRAVE_AVATAR)) {
						pc.removeNoTimerSkillEffect(BRAVE_AVATAR);	
					}
				}
			} else {//沒有隊伍
				if (pc.hasSkillEffect(BRAVE_AVATAR)) {
					pc.removeNoTimerSkillEffect(BRAVE_AVATAR);		
				}
			}
		}
	} catch (Exception e) {
		_log.error("王者加護時間軸異常重啟", e);
		GeneralThreadPool.get().cancel(_timer, false);
		BraveAvatarTimer BraveAvatarTimer = new BraveAvatarTimer();
		BraveAvatarTimer.start();
    }		
  }

}
