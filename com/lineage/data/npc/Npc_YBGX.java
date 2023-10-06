package com.lineage.data.npc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.cmd.CreateNewItem;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.data.quest.DragonKnightLv30_1;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;


public class Npc_YBGX extends NpcExecutor {

	private static final Log _log = LogFactory.getLog(Npc_YBGX.class);

	private Npc_YBGX() {
		// TODO Auto-generated constructor stub
	}

	public static NpcExecutor get() {
		return new Npc_YBGX();
	}

	@Override
	public int type() {
		return 3;
	}

	@Override
	public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
		try {
			// 請問有什麼事嗎？
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_ybgx"));

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void action(final L1PcInstance pc, final L1NpcInstance npc,
			final String cmd, final long amount) {
		boolean isCloseList = false;
		
		if (cmd.equalsIgnoreCase("YBGX")) {// 按鈕
			/*
			 * 需要的物品與個數
			 */
			int[] items = new int[] {44070};//
			int[] counts = new int[] {1};
			


			// 可製作數量
			long xcount = CreateNewItem.checkNewItem(pc, items, counts);
			if (xcount == 1) {
				// 收回需要物件 給予完成物件
				CreateNewItem.delItems(pc, items, counts,1);
				pc.sendPackets(new S_SystemMessage("已劃轉至網頁帳戶,請到帳戶確認"));
				RecordTable.get().recordeYBGX(pc.getAccountName(), pc.getName(), 1, pc.getIp());

				isCloseList = true;

			} else if (xcount > 1) {
				pc.sendPackets(new S_ItemCount(npc.getId(), (int) xcount, "a1"));

			} else if (xcount < 1) {
				isCloseList = true;
			}

		} else if (cmd.equalsIgnoreCase("a1")) {// 按鈕
			/*
			 * 需要的物品與個數
			 */
			int[] items = new int[] {44070};//
			int[] counts = new int[] {1};//
			
			// 可製作數量
			long xcount = CreateNewItem.checkNewItem(pc, items, counts);
			if (xcount >= amount) {
				// 收回需要物件 給予完成物件
				CreateNewItem.delItems(pc, items, counts, // 需要
						amount);// 給予
				RecordTable.get().recordeYBGX(pc.getAccountName(), pc.getName(), (int)amount, pc.getIp());//
				pc.sendPackets(new S_SystemMessage("已劃轉至網頁帳戶,請到帳戶確認"));
			}
			isCloseList = true;

		}

		if (isCloseList) {
			// 關閉對話窗
			pc.sendPackets(new S_CloseList(pc.getId()));
		}
	}
}
