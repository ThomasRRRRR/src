package com.lineage.data.item_armor;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 屬性守護之鍊
 * @author server
 *
 */
public class AttrAmulet extends ItemExecutor {
	private static final Log _log = LogFactory.getLog(AttrAmulet.class);
	private int _AttrAmulet_rnd;//觸發機率
	private int _AttrAmulet_dmg;//固定傷害
	private int _AttrAmulet_gfxid;//動畫特效編號

	public static ItemExecutor get() {
		return new AttrAmulet();
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
				pc.set_AttrAmulet(0, 0, 0);
				break;
			case 1:
				pc.set_AttrAmulet(_AttrAmulet_rnd, _AttrAmulet_dmg, _AttrAmulet_gfxid);
				break;
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	public void set_set(String[] set) {
		try {
			_AttrAmulet_rnd = Integer.parseInt(set[1]);
		} catch (Exception e) {
		}
		try {
			_AttrAmulet_dmg = Integer.parseInt(set[2]);
		} catch (Exception e) {
		}
		try {
			_AttrAmulet_gfxid = Integer.parseInt(set[3]);
		} catch (Exception e) {
		}
	}
}
