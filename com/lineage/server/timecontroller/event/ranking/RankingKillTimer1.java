// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.timecontroller.event.ranking;

import com.lineage.server.templates.L1Account;
import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.thread.GeneralThreadPool;
import org.apache.commons.logging.LogFactory;
import java.util.concurrent.ScheduledFuture;
import org.apache.commons.logging.Log;
import java.util.TimerTask;

public class RankingKillTimer1 extends TimerTask
{
    private static final Log _log;
    private ScheduledFuture<?> _timer;
    private static boolean _load;
    private static String[] _killName;
    
    static {
        _log = LogFactory.getLog((Class)RankingKillTimer1.class);
        RankingKillTimer1._load = false;
        RankingKillTimer1._killName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    public void start() {
        this._timer = GeneralThreadPool.get().scheduleAtFixedRate(this, 60 * 1000 * 5,  60 * 1000 * 15);
    }
    
    public static String[] killName() {
        if (!RankingKillTimer1._load) {
            load();
        }
        return RankingKillTimer1._killName;
    }
    
    @Override
    public void run() {
        try {
            load();
        }
        catch (Exception e) {
            RankingKillTimer1._log.error((Object)"贊助排行榜時間軸異常重啟", (Throwable)e);
            GeneralThreadPool.get().cancel(this._timer, false);
            final RankingKillTimer1 killTimer = new RankingKillTimer1();
            killTimer.start();
        }
    }
    
    private static void load() {
        try {
            final Collection<L1PcInstance> allPc = World.get().getAllPlayers();
            if (allPc.isEmpty()) {
                return;
            }
            RankingKillTimer1._load = true;
            restart();
            for (final L1PcInstance tgpc : allPc) {
            	if (tgpc == null || tgpc.getNetConnection() == null) {
                    continue;
                }
                if (tgpc.isGm()) {
                    continue;
                }
                final L1Account account = tgpc.getNetConnection().getAccount();
                if (account == null) {
                    continue;
                }
                final int killCount = account.get_first_pay();
                if (killCount > 0) {
                    RankingKillTimer1._killName = intTree(killCount, tgpc.getName(), RankingKillTimer1._killName);
                }
                Thread.sleep(1L);
            }
        }
        catch (Exception e) {
            RankingKillTimer1._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private static void restart() {
        RankingKillTimer1._killName = new String[] { " ", " ", " ", " ", " ", " ", " ", " ", " ", " " };
    }
    
    private static String[] intTree(final int killCount, final String name, final String[] userName) {
        if (userName[0].equals(" ")) {
            userName[0] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        String[] set = userName[0].split(",");
        int srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = userName[4];
            userName[4] = userName[3];
            userName[3] = userName[2];
            userName[2] = userName[1];
            userName[1] = userName[0];
            userName[0] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[1].equals(" ")) {
            userName[1] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[1].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = userName[4];
            userName[4] = userName[3];
            userName[3] = userName[2];
            userName[2] = userName[1];
            userName[1] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[2].equals(" ")) {
            userName[2] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[2].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = userName[4];
            userName[4] = userName[3];
            userName[3] = userName[2];
            userName[2] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[3].equals(" ")) {
            userName[3] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[3].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = userName[4];
            userName[4] = userName[3];
            userName[3] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[4].equals(" ")) {
            userName[4] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[4].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = userName[4];
            userName[4] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[5].equals(" ")) {
            userName[5] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[5].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = userName[5];
            userName[5] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[6].equals(" ")) {
            userName[6] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[6].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = userName[6];
            userName[6] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[7].equals(" ")) {
            userName[7] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[7].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = userName[7];
            userName[7] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[8].equals(" ")) {
            userName[8] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[8].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = userName[8];
            userName[8] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        if (userName[9].equals(" ")) {
            userName[9] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        set = userName[9].split(",");
        srcCount = Integer.parseInt(set[1]);
        if (srcCount < killCount && !set[0].equals(name)) {
            userName[9] = String.valueOf(name) + "," + killCount;
            return userName;
        }
        return userName;
    }
}
