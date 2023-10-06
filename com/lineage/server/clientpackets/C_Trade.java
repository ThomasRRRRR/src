package com.lineage.server.clientpackets;

import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.Shutdown;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.william.L1WilliamSystemMessage;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1Trade;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.FaceToFace;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 要求交易(個人)
 *
 * @author daien
 *
 */
public class C_Trade extends ClientBasePacket {

	//private static final Log _log = LogFactory.getLog(C_Trade.class);

	/*public C_Trade() {
	}

	public C_Trade(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
            final L1PcInstance pc = client.getActiveChar();
            final L1PcInstance player = client.getActiveChar();
            pc.isGhost();
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            final L1PcInstance target = FaceToFace.faceToFace(pc);
            final L1PcInstance srcTrade = (L1PcInstance)World.get().findObject(pc.getTradeID());
            if (srcTrade != null) {
                final L1Trade trade = new L1Trade();
                trade.tradeCancel(srcTrade);
                return;
            }
            if (target.getTradeOk()) {
                return;
            }
            if (pc.getTradeOk()) {
                return;
            }
            if (target.getLevel() >= 1 && target.getLevel() < ConfigOther.tradelevel) {
                player.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(10)));
                return;
            }
            if (pc.getLevel() >= 1 && pc.getLevel() < ConfigOther.tradelevel) {
                pc.sendPackets(new S_SystemMessage(L1WilliamSystemMessage.ShowMessage(11)));
                return;
            }
            if (Shutdown.isSHUTDOWN) {
                pc.sendPackets(new S_SystemMessage("目前服務器準備關機狀態"));
                return;
            }
            if (pc.get_other().get_item() != null) {
                pc.sendPackets(new S_SystemMessage("\\fT物品正在進行託售中,請在次重新操作一次"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.get_other().set_item(null);
                return;
            }
            if (pc.get_followmaster() != null) {
                pc.sendPackets(new S_SystemMessage("\\fT高寵狀態中,無法交易。"));
                return;
            }
            if (target.get_other().get_item() != null) {
                pc.sendPackets(new S_SystemMessage("\\fT對方正在託售物品中請稍候"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.get_other().set_item(null);
                return;
            }
            final L1PcInstance srcTradetarget = (L1PcInstance)World.get().findObject(target.getTradeID());
            if (srcTradetarget != null) {
                final L1Trade trade2 = new L1Trade();
                trade2.tradeCancel(srcTradetarget);
                return;
            }
            if (target != null && !target.isParalyzed()) {
                pc.getTradeWindowInventory().getItems().clear();
                target.getTradeWindowInventory().getItems().clear();
                pc.setTradeID(target.getId());
                target.setTradeID(pc.getId());
                target.sendPackets(new S_Message_YN(pc.getName()));
                // 關閉其他對話檔
                pc.sendPackets(new S_CloseList(pc.getId()));
                target.sendPackets(new S_CloseList(target.getId()));
            }
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
