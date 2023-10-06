package com.lineage.server.model.Instance;

import com.lineage.config.ConfigGuaji;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.thread.PcAutoThreadPoolN;
import com.lineage.server.thread.NpcAiThreadPool;
import com.lineage.server.thread.PcAutoThreadPool;
import com.lineage.server.types.Point;
import com.lineage.server.world.World;

import java.util.Calendar;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;





public class PcAI
  implements Runnable
{
  private static final Log _log = LogFactory.getLog(PcAI.class);
  private static Random _random = new Random();
  private final L1PcInstance _pc;
  protected int[][] DIR_TABLE = new int[][] { { 0, -1 }, { 1, -1 }, { 1 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1
      }, { -1, -1 } };
  public PcAI(L1PcInstance pc) {
    this._pc = pc;
  }
  
  public void startAI() {
//    NpcAiThreadPool.get().execute(this);
	  PcAutoThreadPool.get().execute(this);
  }

  public void run() {
    try {
      while (this._pc.getMaxHp() > 0) {

        if (AIProcess()) {
          break;
        }
        try {
          Thread.sleep(getRightInterval(2));
        }
        catch (Exception e) {
          break;
        } 
      } 
      do {
        try {
          Thread.sleep((getRightInterval(1) + 15));
        }
        catch (Exception e) {
          break;
        } 
      } while (this._pc.isDead());
      
      this._pc.allTargetClear();
      this._pc.setAiRunning(false);
      this._pc.setActived(false);
      Thread.sleep(10L);
    }
    catch (Exception e) {
      _log.error("pcAI發生例外狀況: " + this._pc.getName(), e);
    } 
  }

  private boolean AIProcess() {
    try {
      if (this._pc.isDead()) {
        return true;
      }
      
      if (this._pc.getOnlineStatus() == 0) {
        return true;
      }
      
      if (this._pc.getCurrentHp() <= 0) {
        return true;
      }
      if (!this._pc.isActived())
      {
        return true;
      }
      
      if (ConfirmTheEnemy(this._pc)) {
        return true;
      }

      
      if (this._pc.isInvisble()) {
        this._pc.setActived(false);
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("自動狩獵已停止。"));
        this._pc.setAutoX(0);
        this._pc.setAutoY(0);
        this._pc.setAutoMap(0);
        this._pc.killSkillEffectTimer(8853);
        
        TeleportRunnable runnable = new TeleportRunnable(this._pc, 33440, 32802, 4);
        PcAutoThreadPoolN.get().schedule(runnable, 0L);
        
        return true;
      } 
      if (this._pc.getInventory().getWeight240() >= 197) {
        
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(110));
        this._pc.setActived(false);
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("自動狩獵已停止。"));
        this._pc.setAutoX(0);
        this._pc.setAutoY(0);
        this._pc.setAutoMap(0);
        this._pc.killSkillEffectTimer(8853);
        
        TeleportRunnable runnable = new TeleportRunnable(this._pc, 33440, 32802, 4);
        PcAutoThreadPoolN.get().schedule(runnable, 0L);
        return true;
      } 
      if (this._pc.getWeapon() == null) {
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU沒拿武器，自動關閉掛機！"));
        this._pc.setActived(false);
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("自動狩獵已停止。"));
        this._pc.setAutoX(0);
        this._pc.setAutoY(0);
        this._pc.setAutoMap(0);
        this._pc.killSkillEffectTimer(8853);

        return true;
      } 

      
      Calendar date = Calendar.getInstance();
      int nowHour = date.get(11);
      if (ConfigGuaji.checktimeguaji) {
        
        int[] GUAJI_TIME = ConfigGuaji.GUAJI_ITEM; byte b; int i, arrayOfInt1[];
        for (i = (arrayOfInt1 = GUAJI_TIME).length, b = 0; b < i; ) { int guajitimme = arrayOfInt1[b];
          if (nowHour == guajitimme) {
            this._pc.setActived(false);
            this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("自動狩獵已停止。"));
            
            TeleportRunnable runnable = new TeleportRunnable(this._pc, 33440, 32802, 4);
            PcAutoThreadPoolN.get().schedule(runnable, 0L);
            
            this._pc.setAutoX(0);
            this._pc.setAutoY(0);
            this._pc.setAutoMap(0);
            this._pc.killSkillEffectTimer(8853);
            return true;
          } 
          b++; }
      
      } 
      if (!this._pc.getMap().isGuaji() && this._pc.isActived()) {
        this._pc.setActived(false);
        this._pc.setAutoX(0);
        this._pc.setAutoY(0);
        this._pc.setAutoMap(0);
        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("此地圖不能掛機"));
        this._pc.killSkillEffectTimer(8853);
        
        return true;
      } 

      if (this._pc.getAu_AutoSet(0) > 0 && this._pc.getAu_AutoSet(1) > 0 && this._pc.get_followmaster() == null && 
        this._pc.getAutoX() > 0 && this._pc.getAutoY() > 0 && this._pc.getLocation().getTileLineDistance(new Point(this._pc.getAutoX(), this._pc.getAutoY())) > this._pc.getAu_AutoSet(1)) {
        L1Teleport.teleport(this._pc, this._pc.getAutoX(), this._pc.getAutoY(), this._pc.getMapId(), 0, false);
        if (this._pc.isPathfinding()) {
          this._pc.setrandomMoveDirection(_random.nextInt(7));
        }
        this._pc.targetClear();
      } 
      
      int result = this._pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.MOVE);
      if (result == 2) {
        _log.error("要求角色移動:速度異常(" + this._pc.getName() + ")");
      }

      this._pc.checkTarget();
      
      boolean searchTarget = true;
      if (this._pc.is_now_target() != null) {
        searchTarget = false;
      }
      
      if (searchTarget)
      {
        this._pc.searchTarget();
      }

      if (this._pc.is_now_target() == null) {
        if (!this._pc.isPathfinding()) {
          this._pc.setrandomMoveDirection(_random.nextInt(8));
        }
        this._pc.noTarget();
        Thread.sleep(50L);
        return false;
      } 
      this._pc.onTarget();
      if (this._pc.isPathfinding()) {
        this._pc.setPathfinding(false);
      }
      
      Thread.sleep(50L);
    }
    catch (Exception e) {
      _log.error("pcAI發生例外狀況: " + this._pc.getName(), e);
    } 
    return false;
  }
  
  private boolean ConfirmTheEnemy(L1PcInstance pc) {
    try {
      if (pc.getAu_OtherSet(2) > 0 && pc.getAu_AutoSet(0) == 0 && pc.getMap().isTeleportable()) {
        for (L1Object objid : World.get().getVisibleObjects((L1Object)pc, 5)) {
          if (objid instanceof L1PcInstance) {
            L1PcInstance _pc1 = (L1PcInstance)objid;
            if (pc.isInEnemyList(_pc1.getName())) {
              L1Teleport.randomTeleportai(pc);
            }
          } 
        } 
      }
    } catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
    return false;
  }

  private int getRightInterval(int type) {
    int interval = 0;
    switch (type) {
      case 1:
        interval = SprTable.get().getAttackSpeed(
            this._pc.getTempCharGfx(), 
            this._pc.getCurrentWeapon() + 1);
        return intervalR(type, interval);case 2: interval = SprTable.get().getMoveSpeed(this._pc.getTempCharGfx(), this._pc.getCurrentWeapon()); return intervalR(type, interval);
    } 
    return 0;
  }
  private int intervalR(int type, int interval) {
    try {
      if (this._pc.isHaste()) {
        interval = (int)(interval * 0.755D);
      }
      
      if (type == 2 && this._pc.isFastMovable()) {
        interval = (int)(interval * 0.755D);
      }

      if (this._pc.isBrave()) {
        interval = (int)(interval * 0.755D);
      }

      if (this._pc.isElfBrave()) {
        interval = (int)(interval * 0.855D);
      }
      
      if (type == 1 && this._pc.isElfBrave()) {
        interval = (int)(interval * 0.9D);
      }
      if (this._pc.isBraveX()) {
        interval = (int)(interval * 0.755D);
      }
    }
    catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
    return interval;
  }
  
  private class TeleportRunnable implements Runnable {
    private final L1PcInstance _pc;
    private int _locX = 0;
    private int _locY = 0;
    private int _mapid = 0;
    
    public TeleportRunnable(L1PcInstance pc, int x, int y, int mapid) {
      this._pc = pc;
      this._locX = x;
      this._locY = y;
      this._mapid = mapid;
    }
    
    public void run() {
      try {
        L1Map map = L1WorldMap.get().getMap((short)this._mapid);
        int r = 10;
        int tryCount = 0;
        int newX = this._locX;
        int newY = this._locY;
        do {
          tryCount++;
          newX = this._locX + (int)(Math.random() * r) - (int)(Math.random() * r);
          newY = this._locY + (int)(Math.random() * r) - (int)(Math.random() * r);
          if (map.isPassable(newX, newY, this._pc)) {
            break;
          }
          Thread.sleep(1L);
        } while (tryCount < 5);
        
        if (tryCount >= 5) {
          L1Teleport.teleport(this._pc, this._locX, this._locY, (short)this._mapid, this._pc.getHeading(), true);
        } else {
          L1Teleport.teleport(this._pc, newX, newY, (short)this._mapid, this._pc.getHeading(), true);
        }
        
      }
      catch (InterruptedException interruptedException) {}
    }
  }
}