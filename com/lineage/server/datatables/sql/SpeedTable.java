package com.lineage.server.datatables.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DatabaseFactoryLogin;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;

/**
 * 人物帳戶資料
 */
public class SpeedTable {

	// 已有人物帳戶名稱資料
	private final Map<String, String> _SpeedList = new HashMap<String, String>();
	
	private static final Log _log = LogFactory.getLog(SpeedTable.class);

	private static SpeedTable _instance;

	public static SpeedTable get() {
		if (_instance == null) {
			_instance = new SpeedTable();
		}
		return _instance;
	}

	public void load() {
		final PerformanceTimer timer = new PerformanceTimer();
		Connection co = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			co = DatabaseFactoryLogin.get().getConnection();
			final String sqlstr = "SELECT * FROM `排除加速偵測名單`";
			ps = co.prepareStatement(sqlstr);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				final String login = rs.getString("帳號").toLowerCase();
				_SpeedList.put(login, login);
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			SQLUtil.close(ps);
			SQLUtil.close(co);
			SQLUtil.close(rs);
		}
		_log.info("讀取->加速偵測排除帳戶名單資料數量: " + _SpeedList.size() + "(" + timer.get()
				+ "ms)");
	}
	
	public boolean isSpeed(final String loginName) {
		return _SpeedList.get(loginName) != null;
	}
}