/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package com.lineage.server.serverpackets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;

public class S_ClanMarkSee extends ServerBasePacket {
	
	private static final String S_ClanMarkSee = "[S] S_ClanMarkSee";

	/**
	 * 
	 * @param clan
	 * @param type
	 *            0:加入清單 1:移出清單 2:載入清單
	 */
	public S_ClanMarkSee(L1Clan clan, int type) {
		if (clan == null) {
			return;
		}
		buildPacket(clan, type);
	}
	
	/**
	 * 開啟血盟注視
	 * type = 2
	 * @param type
	 */
	public S_ClanMarkSee(int type) {
		buildPacketAll(type);
	}
	
	private void buildPacketAll(int type) {
		writeC(S_OPCODE_CLANMARKSEE);
		writeH(type);
		if (type == 2) {
			//所有人血盟注視
			final Collection<L1Clan> allClans = WorldClan.get().getAllClans();
			int size = allClans.size();
			writeD(size);
			for (final Iterator<L1Clan> iter = allClans.iterator(); iter.hasNext();) {
				final L1Clan clan = iter.next(); // 檢查是否有同名的血盟
				writeS(clan.getClanName());//血盟名稱
			}
		}
		writeH(0);
	}
	
	/**
	 * 關閉血盟注視
	 */
	public S_ClanMarkSee() {
		writeC(S_OPCODE_CLANMARKSEE);
		writeH(2);
		writeD(0);
	}

	private void buildPacket(L1Clan clan, int type) {
		writeC(S_OPCODE_CLANMARKSEE);
		writeH(type);
		if (type == 2) {
			if (clan == null) {
				writeD(0x00);
			} else {
				ArrayList<String> list = clan.getMarkSeeList();
				int size = list.size();
				writeD(size);
				if (size > 0) {
					for (int i = 0; i < size; i++) {
						writeS(list.get(i));
					}
				}
			}
		}
		writeH(0);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_ClanMarkSee;
	}
}
