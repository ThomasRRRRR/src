package com.lineage.data.item_armor;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Imperius_Tshirt extends ItemExecutor {
	private static final Log _log = LogFactory.getLog(Imperius_Tshirt.class);
	private int _r;
	private int _drainingHP_min;
	private int _drainingHP_max;

	public static ItemExecutor get() {
		return new Imperius_Tshirt();
	}

	public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
		try {
			if (item == null) {
				return;
			}

			if (pc == null) {
				return;
			}

			switch (data[0]) {
			case 0:
				pc.set_Imperius_Tshirt(0, 0, 0);
				break;
			case 1:
				pc.set_Imperius_Tshirt(_r, _drainingHP_min, _drainingHP_max);
				break;
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	public void set_set(String[] set) {
		try {
			_r = Integer.parseInt(set[1]);
		} catch (Exception e) {
		}
		try {
			_drainingHP_min = Integer.parseInt(set[2]);
		} catch (Exception e) {
		}
		try {
			_drainingHP_max = Integer.parseInt(set[3]);
		} catch (Exception e) {
		}
	}
}

/*
 * Location: C:\Users\kenny\Downloads\奧茲之戰\Server_Game.jar Qualified Name:
 * com.lineage.data.item_armor.ElitePlateMail_Lindvior JD-Core Version: 0.6.2
 */