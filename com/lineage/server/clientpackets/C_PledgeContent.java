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
package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_Pledge;

//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket

public class C_PledgeContent extends ClientBasePacket {
	private static final String C_PledgeContent = "[C] C_PledgeContent";

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		// 資料載入
		this.read(decrypt);
		
		L1PcInstance pc = client.getActiveChar();
		if (pc == null) {
			return;
		}
		
		if (pc.getClanid() == 0) {
			return;
		}
		
		int data = readC();
		
		if (data == 15) { // 寫入血盟公告
			// 讀取公告字串封包
			String announce = readS();
			/* 取出L1Clan物件 */
			L1Clan clan = ClanReading.get().getTemplate(pc.getClanid());
			/* 更新公告 */
			clan.setAnnouncement(announce);
			/* 更新L1Clan物件 */
			ClanReading.get().updateClan(clan);
			/* 送出血盟公告封包 */
			pc.sendPackets(new S_PacketBox(S_PacketBox.HTML_PLEDGE_REALEASE_ANNOUNCE, announce));
		} else if(data == 16) { // 寫入個人備註
			// 讀取備註字串封包
			String notes = readS();
			/* 更新角色備註資料 */
			pc.setClanMemberNotes(notes);
			/* 寫入備註資料到資料庫 */
			ClanMembersTable.getInstance().updateMemberNotes(pc, notes);
			/* 送出寫入備註更新封包 */
			pc.sendPackets(new S_Pledge(pc.getName(), notes));
		}
	}

	@Override
	public String getType() {
		return C_PledgeContent;
	}

}
