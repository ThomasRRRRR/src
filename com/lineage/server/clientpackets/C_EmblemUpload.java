package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.IdFactory;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.world.World;

/**
 * 要求上傳盟徽
 *
 * @author daien
 *
 */
public class C_EmblemUpload extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_EmblemUpload.class);

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);

			final L1PcInstance pc = client.getActiveChar();

			if (pc.isGhost()) { // 鬼魂模式
				return;
			}
			
			if (pc.isDead()) { // 死亡
				return;
			}
			
			if (pc.isTeleport()) { // 傳送中
				return;
			}

			final int clan_id = pc.getClanid();
			// 人物具有血盟
			if (clan_id != 0) {
				if (pc.getClan().getLeaderId() != pc.getId()) {
					// 219 \f1王子或公主才能上傳徽章。
					pc.sendPackets(new S_ServerMessage(219));
					return;
				}
				
		        L1Clan clan = pc.getClan();
		        if (clan == null) {
		          return;
		        }
				
				final byte[] iconByte = this.readByte();
				//System.out.println(iconByte + " length:" + iconByte.length + " clan_id:" + clan_id);
				L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan_id);

				int newemblemid = IdFactory.get().nextId();
				clan.setEmblemId(newemblemid);
		        ClanReading.get().updateClan(pc.getClan());
		        
				if (emblemIcon != null) {
					//System.out.println("update");
					emblemIcon.set_emblemid(newemblemid);
					emblemIcon.set_clanIcon(iconByte);
					emblemIcon.set_update(emblemIcon.get_update() + 1);
					ClanEmblemReading.get().updateClanIcon(emblemIcon);

				} else {
					//System.out.println("NEW");
					emblemIcon = ClanEmblemReading.get().storeClanIcon(clan_id, iconByte, newemblemid);
				}
				
				World.get().broadcastPacketToAll(new S_Emblem(emblemIcon));
				//System.out.println("emblemIcon.get_emblemid() ==" + emblemIcon.get_emblemid());
								
				//線上血盟成員更新盟暉
				for (L1PcInstance clanmember : clan.getOnlineClanMember()) {
					clanmember.sendPacketsAll((new S_CharReset(clanmember.getId(), clan.getEmblemId())));
					//System.out.println("線上血盟成員更新盟暉" + clan.getEmblemId());
				}
			
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
