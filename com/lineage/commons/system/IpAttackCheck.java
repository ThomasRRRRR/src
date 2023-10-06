package com.lineage.commons.system;

import java.util.Iterator;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.config.ConfigIpCheck;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.LogFactory;
import java.util.HashMap;
import com.lineage.echo.ClientExecutor;
import java.util.Map;
import org.apache.commons.logging.Log;

/**
 * IP攻擊行為檢查器
 * @author loli
 */
public class IpAttackCheck {
	private static final Log _log = LogFactory.getLog(IpAttackCheck.class);
	
	public static final Map<ClientExecutor, String> SOCKETLIST = new ConcurrentHashMap<ClientExecutor, String>();// DaiEn 2012-04-25
	
	private static final HashMap<String, IpTemp> _ipList = new HashMap<String, IpTemp>();
	
	private static IpAttackCheck _instance;
	
	private class IpTemp {
		long _time;
		int _count;
	}
	
	public static IpAttackCheck get() {
		if (_instance == null) {
			_instance = new IpAttackCheck();
		}
		return _instance;
	}
	
	private IpAttackCheck() {
		_ipList.clear();
	}
    
    public boolean check(final String key) {
        try {
			final long nowtime = System.currentTimeMillis();
			IpTemp value = _ipList.get(key);
			
			if (value == null) {//加入IP登入時間及次數
				value = new IpTemp();
				value._time = nowtime;//登入時間
				value._count = 1;//登入次數
				_ipList.put(key, value);
                IpAttackCheck._log.warn((Object)("初始連接 錯誤次數為: " + value._count));
            } else {
				//登入間隔小於設定時間
				if (nowtime - value._time <= ConfigIpCheck.TIMEMILLIS) {
					value._count += 1;//登入次數+1
					//登入次數大於設定次數
					if (value._count >= ConfigIpCheck.COUNT) {
						kick(key);//剔除相同IP所有連線						
						if (ConfigIpCheck.SETDB) {// 加入IP封鎖						
							IpReading.get().add(key, "IP類似攻擊行為");
							System.out.println("IP類似攻擊行為" + key);
							return false;
						}
					}
				} else {//登入間隔大於設定時間	
					value._time = nowtime;//更新登入時間
					value._count = 1;//更新登入次數			
				}
			}
        }
        catch (Exception e) {
            IpAttackCheck._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return true;
    }
    
    private void kick(final String key) {
        try {
            for (final ClientExecutor socket : IpAttackCheck.SOCKETLIST.keySet()) {
                final String ip = IpAttackCheck.SOCKETLIST.get(socket);
                if (ip != null && ip.equals(key)) {
                    socket.close();
                }
            }
        }
        catch (Exception e) {
            IpAttackCheck._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
}
