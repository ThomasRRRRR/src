
package com.lineage.server.Manly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;

/**
 * 紋樣系統 BY Manly
 * 
 * @author Administrator
 *
 */

public class WenYangTable {
	private static final Log _log = LogFactory.getLog(WenYangTable.class);

	private static WenYangTable _instance;

	private final Map<String, L1WenYang> _itemIdIndex = new HashMap<String, L1WenYang>();

	private static final Map<Integer, Integer> _checkMaxEnchantLevelmaps = new HashMap<Integer, Integer>();

	public static WenYangTable getInstance() {
		if (_instance == null) {
			_instance = new WenYangTable();
		}
		return _instance;
	}

	private WenYangTable() {
		loadMagicCrystalItem();
	}

	private void loadMagicCrystalItem() {
		PerformanceTimer timer = new PerformanceTimer();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = DatabaseFactory.get().getConnection();
			pstm = con.prepareStatement("SELECT * FROM 系統_紋樣屬性設定");
			rs = pstm.executeQuery();
			fillMagicCrystalItem(rs);
		} catch (SQLException e) {
			_log.info("讀取->[系統_紋樣屬性設定]資料數量: " + _itemIdIndex.size() + "(" + timer.get() + "ms)");
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void fillMagicCrystalItem(ResultSet rs) throws SQLException {
		while (rs.next()) {
			int type = rs.getInt("類型");
			int Level = rs.getInt("等級");
			String not = rs.getString("紋樣名稱");
			int liliang = rs.getInt("力量");
			int minjie = rs.getInt("敏捷");
			int zhili = rs.getInt("智力");
			int jingshen = rs.getInt("精神");
			int tizhi = rs.getInt("體質");
			int meili = rs.getInt("魅力");
			int xue = rs.getInt("血");
			int mo = rs.getInt("魔");
			int huixue = rs.getInt("回血");
			int huimo = rs.getInt("回魔");
			int ewai = rs.getInt("額外傷害");
			int chenggong = rs.getInt("攻擊成功");
			int mogong = rs.getInt("魔攻");
			int mofang = rs.getInt("魔防");
			int feng = rs.getInt("風屬性防禦");
			int shui = rs.getInt("水屬性防禦");
			int tu = rs.getInt("地屬性防禦");
			int huo = rs.getInt("火屬性防禦");
			int jianmian = rs.getInt("傷害減免");
			int jingyan = rs.getInt("經驗");

			L1WenYang MagicCrystal_Item = new L1WenYang(type, Level, not,liliang,minjie,zhili,jingshen,tizhi,meili,xue,mo,huixue,huimo,ewai,chenggong,mogong,mofang,feng,shui,tu,huo,jianmian,jingyan);
			if (_checkMaxEnchantLevelmaps.containsKey(type)) {
				final Integer checkMaxEnchanrLevel = _checkMaxEnchantLevelmaps.get(type);
				if (Level > checkMaxEnchanrLevel.intValue()) {
					_checkMaxEnchantLevelmaps.put(type, Level);
				}
			} else {
				_checkMaxEnchantLevelmaps.put(type, Level);
			}
			_itemIdIndex.put(new StringBuilder().append(type).append(Level).toString(), MagicCrystal_Item);
		}
	}

	public L1WenYang getTemplate(int type, int enchantLevel) {
		if (_checkMaxEnchantLevelmaps.containsKey(type)) {
			final Integer checkMaxEnchanrLevel = _checkMaxEnchantLevelmaps.get(type);
			int maxEnchantLevel = enchantLevel;
			if (enchantLevel > checkMaxEnchanrLevel.intValue()) {
				maxEnchantLevel = checkMaxEnchanrLevel.intValue();
			}
			final String checkKey = new StringBuilder().append(type).append(maxEnchantLevel).toString();
			if (_itemIdIndex.containsKey(checkKey)) {
				return _itemIdIndex.get(checkKey);
			}
		}
		return null;
	}
}
