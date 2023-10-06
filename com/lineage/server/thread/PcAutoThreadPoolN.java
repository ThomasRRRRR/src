package com.lineage.server.thread;

import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;



/**
 * 線程管理中心(PC)
 * @author dexc
 *
 */
public class PcAutoThreadPoolN {

	private static final Log _log = LogFactory.getLog(PcAutoThreadPoolN.class);

	private static PcAutoThreadPoolN _instance;

	private Executor _executor;
	
	private ScheduledExecutorService _scheduler;

	private final int _pcautoSchedulerPoolSize = 300;
	//private final int _pcautoSchedulerPoolSize = 1000 + Config.MAX_ONLINE_USERS / 2;
	
	
	public static PcAutoThreadPoolN get() {
		if (_instance == null) {
			_instance = new PcAutoThreadPoolN();
		}
		return _instance;
	}

	private PcAutoThreadPoolN() {
		_executor = Executors.newCachedThreadPool();
		_scheduler = Executors.newScheduledThreadPool(_pcautoSchedulerPoolSize,
				new PriorityThreadFactory("PcAuto", Thread.NORM_PRIORITY));
	}
	
	public void execute(final Runnable r) {
		try {
			if (_executor == null) {
				final Thread t = new Thread(r);
				t.start();
			} else {
				_executor.execute(r);
			}
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 创建并执行在给定延迟后启用的一次性操作。
	 * @param r 要执行的任务
	 * @param delay 从现在开始延迟执行的时间
	 * @return
	 */
	public ScheduledFuture<?> schedule(final Runnable r, final long delay) {
		try {
			if (delay <= 0) {
				_executor.execute(r);
				return null;
			}
			return _scheduler.schedule(r, delay, TimeUnit.MILLISECONDS);
			
		} catch (final RejectedExecutionException e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return null;
	}
	
	public ScheduledFuture<?> scheduleAtFixedRate(final TimerTask command, 
			final long initialDelay, final long period) {
		try {
			return _scheduler.scheduleAtFixedRate(command, 
					initialDelay, period, TimeUnit.MILLISECONDS);

		} catch (final RejectedExecutionException e) {
			_log.error(e.getLocalizedMessage(), e);
			return null;
		}
	}
	
	public void cancel(final ScheduledFuture<?> future, boolean mayInterruptIfRunning) {
		try {
			future.cancel(mayInterruptIfRunning);

		} catch (final RejectedExecutionException e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * 根据需要创建新线程的对象。
	 * 使用线程工厂就无需再手工编写对 new Thread 的调用了，从而允许应用程序使用特殊的线程子类、属性等等。
	 * @author daien
	 *
	 */
	private class PriorityThreadFactory implements ThreadFactory {
		
		private final int _prio;

		private final String _name;

		private final AtomicInteger _threadNumber = new AtomicInteger(1); 

		private final ThreadGroup _group;

		/**
		 * PriorityThreadFactory
		 * @param name 線程名稱
		 * @param prio 優先等級
		 */
		public PriorityThreadFactory(final String name, final int prio) {
			_prio = prio;
			_name = name;
			_group = new ThreadGroup(_name);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
		 */
		@Override
		public Thread newThread(final Runnable r) {
			final Thread t = new Thread(_group, r);
			t.setName(_name + "-" + _threadNumber.getAndIncrement());
			t.setPriority(_prio);
			return t;
		}

		@SuppressWarnings("unused")
		public ThreadGroup getGroup() {
			return _group;
		}
	}
}

