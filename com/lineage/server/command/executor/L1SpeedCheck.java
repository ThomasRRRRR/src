package com.lineage.server.command.executor;

import com.lineage.config.Config;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SystemMessage;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class L1SpeedCheck implements L1CommandExecutor {
	private static final Log _log = LogFactory.getLog(L1SpeedCheck.class);

	public static L1CommandExecutor getInstance() {
		return new L1SpeedCheck();
	}

	public Calendar getRealTime() {
		TimeZone _tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(_tz);
		return cal;
	}

	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(arg);
			double move = Double.parseDouble(stringtokenizer.nextToken());
			int type = Integer.parseInt(stringtokenizer.nextToken());
			int time = Integer.parseInt(stringtokenizer.nextToken());
			ConfigOther.SPEED_TIME = move;
			//AcceleratorChecker.Setspeed();
			pc.sendPackets(new S_SystemMessage("\\aD目前移動、動作防加速誤差值為 :"
					+ ConfigOther.SPEED_TIME));

			ConfigOther.opein = type;
			pc.sendPackets(new S_SystemMessage("\\aG目前防加速的懲罰類行為 :"
					+ ConfigOther.opein));

			ConfigOther.Stint = time;
			pc.sendPackets(new S_SystemMessage("\\aH目前防加速的懲罰時間為 :"
					+ ConfigOther.Stint));

		} catch (Exception e) {
			_log.error("錯誤的 GM 指令格式: " + getClass().getSimpleName()
					+ " 執行 GM :" + pc.getName());
			pc.sendPackets(new S_SystemMessage("\\aG請輸入  誤差值 逞罰類型 時間 參數。"));
		}
	}
}