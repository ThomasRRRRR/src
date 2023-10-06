/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package com.lineage.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.DatabaseFactory;
import com.lineage.server.templates.L1HeCheng;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;
/**
 * 娃娃合成调用图片系统
 * By Manly
 * @author Administrator
 *
 */
public class HeChengTable {

	private static final Log _log = LogFactory.getLog(HeChengTable.class);

	private static HeChengTable _instance;

	private final HashMap<Integer, L1HeCheng> _itemidIndex
			= new HashMap<Integer, L1HeCheng>();

	public static HeChengTable getInstance() {
		if (_instance == null) {
			_instance = new HeChengTable();
		}
		return _instance;
	}
	
	/**
	 * 載入資料
	 */
	public void load() {
		final PerformanceTimer timer = new PerformanceTimer();
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int i = 0;
		try {
			cn = DatabaseFactory.get().getConnection();
			ps = cn.prepareStatement("SELECT * FROM `系統_合成圖片調用`");
			rs = ps.executeQuery();
			while (rs.next()) {
				final int itemId = rs.getInt("itemid");
				final String gfxid = rs.getString("gfxid");
				
				final L1HeCheng card = new L1HeCheng(itemId,gfxid);
				_itemidIndex.put(itemId, card);
				i++;
			}
		} catch (final SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(ps);
			SQLUtil.close(cn);
		}
		_log.info("讀取->系統_合成圖片調用: " + i + "(" + timer.get() + "ms)");
	}
	

	public L1HeCheng getTemplate(int itemid) {
		return _itemidIndex.get(itemid);
	}
	
	public int HeChengSize() {
		return _itemidIndex.size();
	}
	
	public L1HeCheng[] getItemIdList() {
		return _itemidIndex.values().toArray(new L1HeCheng[_itemidIndex.size()]);
	}
}
