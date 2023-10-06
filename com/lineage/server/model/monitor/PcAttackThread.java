package com.lineage.server.model.monitor;

import com.lineage.server.model.L1Object;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.clientpackets.AcceleratorChecker;
import java.util.Calendar;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.World;
import java.util.concurrent.ScheduledFuture;
import com.lineage.server.model.Instance.L1PcInstance;

public class PcAttackThread implements Runnable
{
    private L1PcInstance pc;
    private ScheduledFuture timer;
    private int targetId;
    private long delayTime;
    private int gfxid;
    private int currentWeapon;
    private boolean stopped;
    public static final int REGENSTATE_ATTACK = 1;
    
    public PcAttackThread() {
        this.stopped = true;
    }
    
    @Override
    public void run() {
        if (this.stopped || this.pc == null || this.pc.isGhost() || this.pc.isDead() || this.pc.isTeleport() || this.pc.isParalyzed() || this.pc.isSleeped() || this.pc.isInvisDelay() || this.pc.isFishing() || this.pc.hasSkillEffect(1009)) {
            this.stop(this.pc);
            return;
        }
        final L1Object target = World.get().findObject(this.targetId);
        if (target == null) {
            this.stop(this.pc);
            return;
        }
        if (this.pc.isGhost()) {
            this.stop(this.pc);
            return;
        }
        if (this.pc.isDead()) {
            this.stop(this.pc);
            return;
        }
        if (this.pc.isTeleport()) {
            this.stop(this.pc);
            return;
        }
        if (this.pc.isPrivateShop()) {
            this.stop(this.pc);
            return;
        }
        if (this.pc.isParalyzedX()) {
            this.stop(this.pc);
            return;
        }
        if (target instanceof L1MonsterInstance) {
            final L1MonsterInstance mon = (L1MonsterInstance)target;
            if (mon.getNpcTemplate().get_npcId() == 45941 && !this.pc.hasSkillEffect(1015)) {
                this.pc.sendPackets(new S_SystemMessage("\u60a8\u9700\u8981\u4f7f\u7528\u4f0a\u5a03\u8056\u6c34\u624d\u80fd\u653b\u64ca."));
                this.stop(this.pc);
                return;
            }
        }
        if (this.pc.getInventory().getWeight() >= 83) {
            this.pc.sendPackets(new S_ServerMessage(110));
            this.stop(this.pc);
            return;
        }
        if (target instanceof L1Character && (target.getMapId() != this.pc.getMapId() || this.pc.getLocation().getLineDistance(target.getLocation()) > 20.0)) {
            this.stop(this.pc);
            return;
        }
        if (target instanceof L1NpcInstance) {
            final int hiddenStatus = ((L1NpcInstance)target).getHiddenStatus();
            if (hiddenStatus == 1 || hiddenStatus == 2) {
                this.stop(this.pc);
                return;
            }
        }
        if (target instanceof L1Character) {
            final L1Character _target = (L1Character)target;
            final int size = 0;
            if (_target.isInvisble()) {
                this.stop(this.pc);
                return;
            }
        }
        if (this.pc.hasSkillEffect(32)) {
            this.pc.killSkillEffectTimer(32);
        }
        this.pc.setRegenState(1);
        if (target != null && !((L1Character)target).isDead() && !this.stopped) {
            if (this.pc.hasSkillEffect(97) || this.pc.hasSkillEffect(1033) || this.pc.hasSkillEffect(60)) {
                this.pc.delInvis();
                this.pc.setSkillEffect(10400, 3000);
            }
            target.onAction(this.pc);
            if (this.pc.get_weaknss() != 0) {
                final long h_time = Calendar.getInstance().getTimeInMillis() / 1000L;
                if (h_time - this.pc.get_weaknss_t() > 16L) {
                    this.pc.set_weaknss(0, 0L);
                }
            }
            long tempDelaTime = 0L;
            tempDelaTime = this.pc.getAcceleratorChecker().getRightInterval(AcceleratorChecker.ACT_TYPE.ATTACK);
            this.timer = PcOtherThreadPool.get().schedule(this, tempDelaTime);
        }
        else {
            this.stop(this.pc);
        }
    }
    
    public void stop(final L1PcInstance pc) {
        if (this.timer != null || !this.stopped) {
            this.stopped = true;
            try {
                pc.setAttackThread(null);
                this.timer.cancel(false);
            }
            catch (Exception ex) {}
        }
    }
    
    public int getTargetId() {
        return this.targetId;
    }
    
    public void setTargetId(final int targetId) {
        this.targetId = targetId;
    }
    
    public void start(final L1PcInstance _pc, final int _targetId, final long _delayTime, final int _gfxid) {
        this.pc = _pc;
        this.targetId = _targetId;
        this.stopped = false;
        this.delayTime = _delayTime;
        this.gfxid = _gfxid;
        this.currentWeapon = _pc.getCurrentWeapon();
        this.timer = PcOtherThreadPool.get().schedule(this, 0L);
    }
}
