package com.lineage.data.item_armor;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 月亮項鍊系列
 * @author server
 *
 */
public class MoonAmulet extends ItemExecutor {
	private static final Log _log = LogFactory.getLog(MoonAmulet.class);
	private int _MoonAmulet_rnd;//觸發機率
	private int _MoonAmulet_dmg_min;//最小傷害
	private int _MoonAmulet_dmg_max;//最大傷害
	private int _MoonAmulet_gfxid;//動畫特效編號

	public static ItemExecutor get() {
		return new MoonAmulet();
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
				pc.set_MoonAmulet(0, 0, 0, 0);
				break;
			case 1:
				pc.set_MoonAmulet(_MoonAmulet_rnd, _MoonAmulet_dmg_min, _MoonAmulet_dmg_max, _MoonAmulet_gfxid);
				break;
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	public void set_set(String[] set) {
		try {
			_MoonAmulet_rnd = Integer.parseInt(set[1]);
		} catch (Exception e) {
		}
		try {
			_MoonAmulet_dmg_min = Integer.parseInt(set[2]);
		} catch (Exception e) {
		}
		try {
			_MoonAmulet_dmg_max = Integer.parseInt(set[3]);
		} catch (Exception e) {
		}
		try {
			_MoonAmulet_gfxid = Integer.parseInt(set[4]);
		} catch (Exception e) {
		}
	}
}
