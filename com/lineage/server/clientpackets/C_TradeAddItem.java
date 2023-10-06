// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Trade;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.data.event.TimeTrade;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_TradeAddItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog((Class)C_TradeAddItem.class);
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
            this.read(decrypt);
            final L1PcInstance pc = client.getActiveChar();
            pc.isGhost();
            if (pc.isDead() || pc.isTeleport()) {
                return;
            }
            final int itemObjid = this.readD();
            long itemcount = this.readD();
            if (itemcount > 2147483647L) {
                itemcount = 2147483647L;
            }
            itemcount = Math.max(0L, itemcount);
            final L1ItemInstance item = pc.getInventory().getItem(itemObjid);
            if (item == null) {
                return;
            }
            if (item.getCount() <= 0L) {
                return;
            }
            if ((item.getItemId() == 40308 || item.getItemId() == 44070) && pc.getLevel() < 35) {
                pc.sendPackets((ServerBasePacket)new S_ServerMessage("預防作弊啟動，35級以下無法做此動作"));
                return;
            }
            if (item.getItemId() == 47011 && pc.getLevel() < 52) {
                return;
            }
            if (!pc.isGm()) {
                if (!item.getItem().isTradable() && !item.getproctect10()) {
                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    return;
                }
                if (item.getBless() >= 128) {
                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    return;
                }
                if (TimeTrade.START && item.get_time() != null) {
                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    return;
                }
                if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                    pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    return;
                }
            }
            if (item.isEquipped()) {
                pc.sendPackets(new S_ServerMessage(141));
                return;
            }
            final Object[] petlist = pc.getPetList().values().toArray();
            final Object[] arrayOfObject1;
            final int i = (arrayOfObject1 = petlist).length;
            for (byte b = 0; b < i; ++b) {
                final Object petObject = arrayOfObject1[b];
                if (petObject instanceof L1PetInstance) {
                    final L1PetInstance pet = (L1PetInstance)petObject;
                    if (item.getId() == pet.getItemObjId()) {
                        pc.sendPackets(new S_ServerMessage(1187));
                        return;
                    }
                }
            }
            if (item.getId() == pc.getQuest().get_step(74)) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
                return;
            }
            if (pc.getDoll(item.getId()) != null) {
                pc.sendPackets(new S_ServerMessage(1181));
                return;
            }
            if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getItemId()) {
                pc.sendPackets(new S_ServerMessage(1181));
                return;
            }
            final L1PcInstance tradingPartner = (L1PcInstance)World.get().findObject(pc.getTradeID());
            if (tradingPartner == null) {
                return;
            }
            if (pc.getTradeOk()) {
                return;
            }
            if (tradingPartner.getInventory().checkAddItem(item, itemcount) != 0) {
                tradingPartner.sendPackets(new S_ServerMessage(270));
                pc.sendPackets(new S_ServerMessage(271));
                return;
            }
            final L1Trade trade = new L1Trade();
            if (itemcount <= 0L) {
                C_TradeAddItem._log.error((Object)("\u8981\u6c42\u589e\u52a0\u4ea4\u6613\u7269\u54c1\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                return;
            }
            trade.tradeAddItem(pc, itemObjid, itemcount);
            item.setproctect10(false);
        }
        catch (Exception ex) {
            return;
        }
        finally {
            this.over();
        }
        this.over();
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
