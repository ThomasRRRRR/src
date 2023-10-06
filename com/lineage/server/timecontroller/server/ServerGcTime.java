package com.lineage.server.timecontroller.server;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.thread.ServerGcTimePool;

/**
 * 自動伺服器資源回收GC時間軸
 * 
 * @author dexc
 * 
 */
public class ServerGcTime extends TimerTask {

	private static final Log _log = LogFactory.getLog(ServerGcTime.class);

	private ScheduledFuture<?> _timer;

	private static final ArrayList<L1ItemInstance> _itemList = new ArrayList<L1ItemInstance>();

	public void start() {
		final int timeMillis = 60 * 60 * 1000;
		_timer = ServerGcTimePool.get().scheduleAtFixedRate(this, timeMillis, timeMillis);
	}

	public static void add(final L1ItemInstance item) {
		_itemList.add(item);
	}

	public static boolean contains(final L1ItemInstance item) {
		return _itemList.contains(item);
	}

	@Override
	public void run() {
		try {
			if (true) {
				System.gc();
				return;
			}
		} catch (final Exception e) {
			_log.error("自動伺服器資源回收GC時間軸異常重啟", e);
			ServerGcTimePool.get().cancel(_timer, false);
			final ServerGcTime deleteItemTimer = new ServerGcTime();
			deleteItemTimer.start();
		}
	}
}
