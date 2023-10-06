package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.DeClanTable;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.templates.DeClan;
import com.lineage.server.templates.L1EmblemIcon;

/**
 * 要求更新盟輝
 * @author DaiEn
 *
 */
public class C_EmblemDownload extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_EmblemDownload.class);

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
			
			int emblemid = readD();

			/**一般血盟*/
			final L1EmblemIcon emblemIcon = ClanEmblemReading.get().getEmblem(emblemid);			
			L1Clan clan = ClanReading.get().getTemplate(emblemIcon.get_clanid());
			if (clan != null) {
				if (emblemIcon != null) {
					pc.sendPackets(new S_Emblem(emblemIcon));
					//System.out.println("要求更新人物盟徽");
					//System.out.println("clanId ==" + emblemIcon.get_clanid());
				}
			}
			
			
			/**虛擬血盟*/
			final L1EmblemIcon emblemIcon2 = ClanEmblemReading.get().getEmblem(emblemid);			
			DeClan deClan = DeClanTable.get().get(emblemIcon2.get_clanid());		
			if (deClan != null) {
				if (emblemIcon2 != null) {
					pc.sendPackets(new S_Emblem(emblemIcon2));
					//System.out.println("要求更新假人盟徽");
					//System.out.println("clanId ==" + emblemIcon2.get_clanid());
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
