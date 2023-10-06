// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.Shutdown;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.datatables.ServerItemDropTable;
import com.lineage.data.event.DropItem;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAlt;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_DropItem extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog((Class)C_DropItem.class);
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {

            this.read(decrypt);
            final int x = this.readH();
            final int y = this.readH();
            final int objectId = this.readD();
            int count = this.readD();
            if (count > Integer.MAX_VALUE) {
                count = Integer.MAX_VALUE;
            }
            count = Math.max(0, count);
            final L1PcInstance pc = client.getActiveChar();
            
            // 關閉其他對話檔
			pc.sendPackets(new S_CloseList(pc.getId()));

            
            if (!pc.isGhost()) {
                if (!ConfigAlt.DORP_ITEM && !pc.isGm()) {
                    pc.sendPackets(new S_ServerMessage(125));
                }
                else {
                    final L1ItemInstance item = pc.getInventory().getItem(objectId);
                    if (item == null) {
                        return;
                    }
                    if (item.getCount() <= 0L) {
                        return;
                    }
                    if (!pc.isGm()) {
                        if (!item.getItem().isTradable()) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        if (item.getItemId() == 44070 && !pc.isGm()) {
                            pc.sendPackets(new S_ServerMessage("\u8a72\u7269\u54c1\u8cb4\u91cd.\u7121\u6cd5\u6368\u68c4"));
                            return;
                        }
                        if (DropItem.START) {
                            final int maxCount = ServerItemDropTable.get().getMaxCount(item.getItemId());
                            if (maxCount < 0) {
                                pc.sendPackets(new S_ServerMessage(125));
                                return;
                            }
                            if (count > maxCount) {
                                pc.sendPackets(new S_ServerMessage(166, String.valueOf(String.valueOf(item.getName())) + "\u6700\u591a\u53ea\u80fd\u4e1f\u68c4" + maxCount + "\u500b"));
                                return;
                            }
                        }
                        if (item.get_time() != null) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                            pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                            return;
                        }
                        if (pc.get_other().get_item() != null) {
                            pc.sendPackets(new S_SystemMessage("\\fT\u7269\u54c1\u6b63\u5728\u9032\u884c\u8a17\u552e\u4e2d\u7981\u6b62\u4e1f\u68c4"));
                            pc.sendPackets(new S_CloseList(pc.getId()));
                            pc.get_other().set_item(null);
                            return;
                        }
                        if (Shutdown.isSHUTDOWN) {
                            pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                            return;
                        }
                    }
                    RecordTable.get().recorddropitem(pc.getName(), item.getAllName(), count, item.getId(), pc.getIp());
                    final Object[] petlist = pc.getPetList().values().toArray();
                    Object[] array;
                    for (int length = (array = petlist).length, i = 0; i < length; ++i) {
                        final Object petObject = array[i];
                        if (petObject instanceof L1PetInstance) {
                            final L1PetInstance pet = (L1PetInstance)petObject;
                            if (item.getId() == pet.getItemObjId()) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
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
                    if (item.isEquipped()) {
                        pc.sendPackets(new S_ServerMessage(125));
                        return;
                    }
                    if (item.getBless() >= 128) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    pc.getInventory().tradeItem(item, count, pc.get_showId(), World.get().getInventory(x, y, pc.getMapId()));
                    item.setdropitemcheck(2);
                    item.setdropitemname(item.getName());
                    pc.turnOnOffLight();
                }
            }
        }
        catch (Exception ex) {}
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
