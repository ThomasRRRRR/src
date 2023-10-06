package com.lineage.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.list.OnlineUser;
import com.lineage.server.command.GMCommands;
import com.lineage.server.utils.SystemUtil;

/**
 * 指令視窗監聽器
 * @author daien
 *
 */
public class CmdEcho {

	private static final Log _log = LogFactory.getLog(CmdEcho.class);

	private static final boolean _msg = false;
	
	private static final String _t1 = "\n\r--------------------------------------------------";
	
	private static final String _t2 = "\n\r--------------------------------------------------";

	/**
	 * 指令視窗監聽器
	 * @param timer 
	 */
	public CmdEcho(long timer) {
		if (!_msg) {
			final String info = 
				_t1
				+ (timer != 0 ? "\n       啟動指令視窗監聽器!!遊戲伺服器啟動完成!!"
						+ "\n       服務器啟動耗用時間: " + timer + "ms":"\n       目前連線帳號: " + OnlineUser.get().size())
				+ _t2;
			
			_log.warn(info);
			SystemUtil.printMemoryUsage(_log);
		}
	}
	
	/**
	 * 啟動指令視窗監聽器
	 * @param timer 
	 */
	public void runCmd() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = br.readLine();

			GMCommands.getInstance().handleCommands(str);
			
			reRunCmd();
			
		} catch (IOException e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * 重新啟動指令是窗監聽器
	 */
	private void reRunCmd() {
		CmdEcho cmdEcho = new CmdEcho(0);
		cmdEcho.runCmd();
	}
}
