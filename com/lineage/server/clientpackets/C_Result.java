package com.lineage.server.clientpackets;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import com.lineage.DatabaseFactory;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.lineage.server.model.shop.L1ShopBuyOrderList;
import com.lineage.server.model.shop.L1ShopSellOrderList;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.datatables.lock.OtherUserBuyReading;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.datatables.lock.OtherUserSellReading;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.datatables.lock.DwarfForChaReading;
import com.lineage.server.datatables.lock.DwarfReading;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.DwarfForClanReading;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.serverpackets.S_Disconnect;
import java.sql.Timestamp;
import com.lineage.server.datatables.lock.DwarfForElfReading;
import com.lineage.server.timecontroller.event.GamblingTime;
import com.lineage.server.serverpackets.S_CnsSell;
import com.lineage.data.event.ShopXSet;
import com.lineage.data.npc.shop.Npc_ShopX;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ShopXTable;
import java.util.Map;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.Shutdown;
import java.util.HashMap;
import com.lineage.server.templates.L1Item;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ItemUpdate;
import com.lineage.server.datatables.ItemUpdateTable;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1CnInstance;
import com.lineage.server.model.Instance.L1GamblingInstance;
import com.lineage.server.model.Instance.L1MerchantInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

/**
 * 要求列表物品取得
 * @author dexc
 *
 */
public class C_Result extends ClientBasePacket {

	public static final Log _log = LogFactory.getLog(C_Result.class);

	public static final Random _random = new Random();
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
            this.read(decrypt);
            final L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {
                return;
            }
            if (pc.isDead()) {
                return;
            }
            if (pc.isTeleport()) {
                return;
            }
            if (pc.isPrivateShop()) {
                return;
            }
            final int npcObjectId = this.readD();
            final int resultType = this.readC();
            final int size = this.readC();
            @SuppressWarnings("unused")
            final int unknown = this.readC();
            int npcId = 0;
            boolean isPrivateShop = false;
            final L1Object findObject = World.get().findObject(npcObjectId);
            if (findObject != null) {
                boolean isStop = false;
                if (findObject instanceof L1NpcInstance) {
                    final L1NpcInstance targetNpc = (L1NpcInstance)findObject;
                    npcId = targetNpc.getNpcTemplate().get_npcId();
                    isStop = true;
                }
                else if (findObject instanceof L1PcInstance) {
                    isPrivateShop = true;
                    isStop = true;
                }
                if (isStop) {
					final int diffLocX = Math.abs(pc.getX() - findObject.getX());
					final int diffLocY = Math.abs(pc.getY() - findObject.getY());
					// 距離5格以上無效
					if ((diffLocX > 5) || (diffLocY > 5)) {
						return;
					}
				}
            }
            switch (resultType) {
                case 0: {
                    if (size <= 0) {
                        break;
                    }
                    if (findObject instanceof L1MerchantInstance) {
                        switch (npcId) {
                            case 70535: {
                                this.mode_shopS(pc, size);
                                break;
                            }
                            case 210109: {
                                this.mode_shopS(pc, size);
                                break;
                            }
                            default: {
                                this.mode_buy(pc, npcId, size);
                                break;
                            }
                        }
                        return;
                    }
                    if (findObject instanceof L1GamblingInstance) {
                        this.mode_gambling(pc, npcId, size, true);
                        return;
                    }
                    if (findObject instanceof L1CnInstance) {
                        this.mode_cn_buy(pc, size);
                        return;
                    }
                    if (findObject instanceof L1DeInstance) {
                        final L1DeInstance de = (L1DeInstance)findObject;
                        this.mode_buyde(pc, de, size);
                        return;
                    }
                    if (pc.equals(findObject)) {
                        this.mode_cn_buy(pc, size);
                        return;
                    }
                    if (findObject instanceof L1PcInstance && isPrivateShop) {
                        final L1PcInstance targetPc = (L1PcInstance)findObject;
                        this.mode_buypc(pc, targetPc, size);
                        return;
                    }
                    break;
                }
                case 1: {
                    if (size <= 0) {
                        break;
                    }
                    if (findObject instanceof L1MerchantInstance) {
                        switch (npcId) {
                            case 70023: {
                                this.mode_sellall(pc, size);
                                break;
                            }
                            case 111414: {
                                this.mode_firecrystal(pc, size);
                                break;
                            }
                            default: {
                                this.mode_sell(pc, npcId, size);
                                break;
                            }
                        }
                        return;
                    }
                    if (findObject instanceof L1CnInstance) {
                        this.mode_cn_sell(pc, size);
                        return;
                    }
                    if (findObject instanceof L1GamblingInstance) {
                        this.mode_gambling(pc, npcId, size, false);
                        return;
                    }
                    if (findObject instanceof L1DeInstance) {
                        if (findObject instanceof L1DeInstance) {
                            final L1DeInstance de = (L1DeInstance)findObject;
                            this.mode_sellde(pc, de, size);
                        }
                        return;
                    }
                    if (findObject instanceof L1PcInstance && isPrivateShop) {
                        final L1PcInstance targetPc = (L1PcInstance)findObject;
                        this.mode_sellpc(pc, targetPc, size);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 5) {
                        this.mode_warehouse_in(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 5) {
                        this.mode_warehouse_out(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 15) {
                        this.mode_warehouse_clan_in(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 5: {
                    if (size > 0) {
                        if (!(findObject instanceof L1DwarfInstance)) {
                            break;
                        }
                        final int level = pc.getLevel();
                        if (level >= 5) {
                            this.mode_warehouse_clan_out(pc, npcId, size);
                            break;
                        }
                        break;
                    }
                    else {
                        final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                        if (clan != null) {
                            clan.setWarehouseUsingChar(0);
                            break;
                        }
                    }
                    break;
                }
                case 8: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 5 && pc.isElf()) {
                        this.mode_warehouse_elf_in(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 9: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 5 && pc.isElf()) {
                        this.mode_warehouse_elf_out(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 10: {
                    if (size > 0) {
                        switch (npcId) {
                            case 111410: {
                                this.mode_update_item(pc, size, npcObjectId);
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
                case 12: {
                    if (size > 0) {
                        switch (npcId) {
                            case 70535: {
                                this.mode_shop_item(pc, size, npcObjectId);
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
                case 17: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 155) {
                        this.mode_warehouse_cha_in(pc, npcId, size);
                        break;
                    }
                    break;
                }
                case 18: {
                    if (size <= 0) {
                        break;
                    }
                    if (!(findObject instanceof L1DwarfInstance)) {
                        break;
                    }
                    final int level = pc.getLevel();
                    if (level >= 155) {
                        this.mode_warehouse_cha_out(pc, npcId, size);
                        break;
                    }
                    break;
                }
            }
        }
        catch (Exception ex) {
            return;
        }
        finally {
            this.over();
        }
        this.over();
    }
    
    private void mode_update_item(final L1PcInstance pc, final int size, final int npcObjectId) {
        try {
            if (size != 1) {
                pc.sendPackets(new S_ServerMessage("\\fR\u4f60\u53ea\u80fd\u9078\u53d6\u4e00\u6a23\u88dd\u5099\u7528\u4f86\u5347\u7d1a\u3002"));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final int orderId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count != 1) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final L1ItemInstance item = pc.getInventory().getItem(orderId);
            final ArrayList<L1ItemUpdate> items = ItemUpdateTable.get().get(item.getItemId());
            final String[] names = new String[items.size()];
            for (int index = 0; index < items.size(); ++index) {
                final int toid = items.get(index).get_toid();
                final L1Item tgitem = ItemTable.get().getTemplate(toid);
                if (tgitem != null) {
                    names[index] = tgitem.getName();
                }
            }
            pc.set_mode_id(orderId);
            pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_up_i1", names));
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u5347\u7d1a\u88dd\u5099\u7269\u54c1\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_shopS(final L1PcInstance pc, final int size) {
        try {
            final Map<Integer, Integer> sellScoreMapMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int orderId = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                }
                else {
                    if (Shutdown.isSHUTDOWN) {
                        pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                        return;
                    }
                    sellScoreMapMap.put(new Integer(orderId), new Integer(count));
                }
            }
            pc.get_otherList().get_buyCnS(sellScoreMapMap);
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u8cfc\u8cb7\u4eba\u7269\u8a17\u552e\u7269\u54c1\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_shop_item(final L1PcInstance pc, final int size, final int npcObjectId) {
        try {
            if (size == 1) {
                final int objid = this.readD();
                final L1ItemInstance l1ItemInstance = pc.getInventory().getItem(objid);
                boolean isError = false;
                if (l1ItemInstance instanceof L1ItemInstance) {
                    final L1ItemInstance item = l1ItemInstance;
                    if (!item.getItem().isTradable()) {
                        isError = true;
                    }
                    if (item.isEquipped()) {
                        isError = true;
                    }
                    if (!item.isIdentified()) {
                        isError = true;
                    }
                    if (item.getItem().getMaxUseTime() != 0) {
                        isError = true;
                    }
                    if (item.get_time() != null) {
                        isError = true;
                    }
                    if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                        isError = true;
                    }
                    final Object[] petlist = pc.getPetList().values().toArray();
                    final Object[] arrayOfObject1;
                    final int i = (arrayOfObject1 = petlist).length;
                    for (byte b = 0; b < i; ++b) {
                        final Object petObject = arrayOfObject1[b];
                        if (petObject instanceof L1PetInstance) {
                            final L1PetInstance pet = (L1PetInstance)petObject;
                            if (item.getId() == pet.getItemObjId()) {
                                isError = true;
                            }
                        }
                    }
                    if (pc.getDoll(item.getId()) != null) {
                        isError = true;
                    }
                    if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                        isError = true;
                    }
                    if (item.getraceGamNo() != null) {
                        isError = true;
                    }
                    if (item.getEnchantLevel() < 0) {
                        isError = true;
                    }
                    if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                        isError = true;
                    }
                    if (isError) {
                        pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_x_e1"));
                    }
                    else {
                        final L1ItemInstance itemT = pc.getInventory().checkItemX(Npc_ShopX._itemid, ShopXSet.ADENA);
                        if (itemT == null) {
                            pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                            pc.sendPackets(new S_CloseList(pc.getId()));
                            return;
                        }
                        pc.get_other().set_item(item);
                        pc.sendPackets(new S_CnsSell(npcObjectId, "y_x_3", "ma"));
                    }
                }
            }
            else {
                pc.sendPackets(new S_NPCTalkReturn(npcObjectId, "y_x_e"));
            }
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u4eba\u7269\u8a17\u552e\u7269\u54c1\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_sellall(final L1PcInstance pc, final int size) {
        try {
            final Map<Integer, Integer> sellallMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int objid = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                sellallMap.put(new Integer(objid), new Integer(count));
            }
            pc.get_otherList().sellall(sellallMap);
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u73a9\u5bb6\u8ce3\u51fa\u7269\u54c1\u7d66\u4e88\u842c\u7269\u56de\u6536\u5546\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_cn_buy(final L1PcInstance pc, final int size) {
        try {
            final Map<Integer, Integer> cnMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int orderId = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    return;
                }
                if (count > 9999) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5927\u65bc9999: " + pc.getName()));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                cnMap.put(new Integer(orderId), new Integer(count));
            }
            pc.get_otherList().get_buyCn(cnMap);
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u73a9\u5bb6\u8cb7\u5165\u5546\u57ce\u7269\u54c1\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_cn_sell(final L1PcInstance pc, final int size) {
        try {
            final Map<Integer, Integer> cnsellMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int objid = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    return;
                }
                if (count > 9999) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5927\u65bc9999: " + pc.getName()));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                cnsellMap.put(new Integer(objid), new Integer(count));
            }
            pc.get_otherList().sellcnitem(cnsellMap);
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u73a9\u5bb6\u8ce3\u51fa\u7269\u54c1\u7d66\u4e88\u5546\u57ce\u9053\u5177\u56de\u6536\u5c08\u54e1\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_firecrystal(final L1PcInstance pc, final int size) {
        try {
            final Map<Integer, Integer> FCMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int objid = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                FCMap.put(new Integer(objid), new Integer(count));
            }
            pc.get_otherList().sellforfirecrystal(FCMap);
        }
        catch (Exception e) {
            C_Result._log.error((Object)("\u73a9\u5bb6\u8ce3\u51fa\u7269\u54c1\u53d6\u5f97\u706b\u795e\u7d50\u6676\u6578\u64da\u7570\u5e38: " + pc.getName()));
        }
    }
    
    private void mode_gambling(final L1PcInstance pc, final int npcId, final int size, final boolean isShop) {
        if (isShop) {
            if (GamblingTime.isStart()) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final Map<Integer, Integer> gamMap = new HashMap<Integer, Integer>();
            for (int i = 0; i < size; ++i) {
                final int orderId = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    return;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    return;
                }
                gamMap.put(new Integer(orderId), new Integer(count));
            }
            pc.get_otherList().get_buyGam(gamMap);
        }
        else {
            for (int j = 0; j < size; ++j) {
                final int objid = this.readD();
                final int count2 = Math.max(0, this.readD());
                if (count2 <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                }
                else {
                    pc.get_otherList().get_sellGam(objid, count2);
                }
            }
        }
    }
    
    private void mode_warehouse_elf_out(final L1PcInstance pc, final int npcId, final int size) {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u7cbe\u9748\u5009\u5eab\u53d6\u51fa\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                return;
            }
            final L1ItemInstance item = pc.getDwarfForElfInventory().getItem(objectId);
            if (item == null) {
                C_Result._log.error((Object)("\u7cbe\u9748\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (!DwarfForElfReading.get().getUserItems(pc.getAccountName(), objectId, count)) {
                C_Result._log.error((Object)("\u7cbe\u9748\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (Shutdown.isSHUTDOWN) {
                pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                return;
            }
            if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                result("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "\u73a9\u5bb6:\u3010 " + pc.getName() + " \u3011\u7591\u4f3c\u5229\u7528\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u6d17\u9053\u5177\uff0c\u8acb\u6ce8\u610f " + "\u6642\u9593:" + "(" + new Timestamp(System.currentTimeMillis()) + ")\u3002", 10);
                return;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                break;
            }
            if (objectId != item.getId()) {
                pc.sendPackets(new S_Disconnect());
                break;
            }
            if (!item.isStackable() && count != 1) {
                pc.sendPackets(new S_Disconnect());
                break;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (item.getCount() > 10000000L) {
                break;
            }
            if (count > 10000000) {
                break;
            }
            if (count > item.getCount()) {
                count = (int)item.getCount();
            }
            if (pc.getInventory().checkAddItem(item, count) != 0) {
                pc.sendPackets(new S_ServerMessage(270));
                break;
            }
            if (!pc.getInventory().consumeItem(40494, 2L)) {
                pc.sendPackets(new S_ServerMessage("\u7d14\u7cb9\u7684\u7c73\u7d22\u5229\u584a\u4e0d\u8db32\u500b"));
                break;
            }
            if (item.isStackable()) {
                if (this.getPcItemCountelf(item, pc) >= count && this.getPcItemCountelf(item, pc) == item.getCount()) {
                    pc.getDwarfForElfInventory().tradeItem(item, count, pc.getInventory());
                }
                else {
                    result("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "\u73a9\u5bb6" + ":\u3010 " + pc.getName() + " \u3011 " + "\u5009\u5eab\u9818\u53d6\u7570\u5e38\u3010" + item.getName() + "\u3011\u6642\u9593:" + "(" + new Timestamp(System.currentTimeMillis()) + ")\u3002", 7);
                }
            }
            else {
                pc.getDwarfForElfInventory().tradeItem(item, count, pc.getInventory());
                pc.getDwarfForElfInventory().tradeItem(item, count, pc.getInventory());
//				RecordTable.get().recordeWarehouse_elf(pc.getName(), "領取", "妖倉", item.getAllName(), count, item.getId(), pc.getIp());
            }
        }
    }
    
    private void mode_warehouse_elf_in(final L1PcInstance pc, final int npcId, final int size) {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u7cbe\u9748\u5009\u5eab\u5b58\u5165\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                return;
            }
            final L1ItemInstance l1ItemInstance1 = pc.getInventory().getItem(objectId);
            if (l1ItemInstance1 == null) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, count)) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (Shutdown.isSHUTDOWN) {
                pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                return;
            }
            final L1ItemInstance item = l1ItemInstance1;
            if (item.getItemId() == 44070) {
                pc.sendPackets(new S_ServerMessage("\u5143\u5bf6\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (item.getItemId() == 40308) {
                pc.sendPackets(new S_ServerMessage("\u91d1\u5e63\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (!item.getItem().isTradable()) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                return;
            }
            if (item.get_time() != null) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                return;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                return;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (item.getCount() > 100000000L) {
                break;
            }
            if (count > 100000000) {
                break;
            }
            if (pc.getDwarfInventory().checkAddItemToWarehouse(item, count, 0) == 1) {
                pc.sendPackets(new S_ServerMessage(75));
                break;
            }
            final Object[] petlist = pc.getPetList().values().toArray();
            final Object[] arrayOfObject1;
            final int j = (arrayOfObject1 = petlist).length;
            for (byte b = 0; b < j; ++b) {
                final Object petObject = arrayOfObject1[b];
                if (petObject instanceof L1PetInstance) {
                    final L1PetInstance pet = (L1PetInstance)petObject;
                    if (item.getId() == pet.getItemObjId()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        break;
                    }
                }
            }
            if (pc.getDoll(item.getId()) != null) {
                pc.sendPackets(new S_ServerMessage(1181));
                return;
            }
            if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                pc.sendPackets(new S_ServerMessage(1181));
                break;
            }
            if (pc.getDwarfForElfInventory().checkAddItemToWarehouse(item, count, 0) == 1) {
                pc.sendPackets(new S_ServerMessage(75));
                break;
            }
            if (item.isStackable()) {
                if (this.getPcItemCount2(item, pc) >= count && item.getCount() == this.getPcItemCount2(item, pc)) {
                    pc.getInventory().tradeItem(objectId, count, pc.getDwarfForElfInventory());
                }
                else {
                    pc.getInventory().deleteItem(item);
                    result("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "\u73a9\u5bb6" + ":\u3010 " + pc.getName() + " \u3011 " + "\u5996\u7cbe\u5009\u5eab\u5b58\u53d6\u7570\u5e38(\u4e26\u522a\u9664)\u3010" + item.getName() + "\u3011\u6642\u9593:" + "(" + new Timestamp(System.currentTimeMillis()) + ")\u3002", 7);
                }
            }
            else {
                pc.getInventory().tradeItem(objectId, count, pc.getDwarfForElfInventory());
//				RecordTable.get().recordeWarehouse_elf(pc.getName(), "存入", "妖倉", item.getAllName(), count, item.getId(), pc.getIp());
            }
        }
    }
    
    private void mode_warehouse_clan_out(final L1PcInstance pc, final int npcId, final int size) {
        final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        try {
            if (clan != null) {
                for (int i = 0; i < size; ++i) {
                    final int objectId = this.readD();
                    final int count = Math.max(0, this.readD());
                    if (count <= 0) {
                        C_Result._log.error((Object)("\u8981\u6c42\u8840\u76df\u5009\u5eab\u53d6\u51fa\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                        return;
                    }
                    final L1ItemInstance item = clan.getDwarfForClanInventory().getItem(objectId);
                    if (item == null) {
                        C_Result._log.error((Object)("\u8840\u76df\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                        return;
                    }
                    if (!DwarfForClanReading.get().getUserItems(pc.getClanname(), objectId, count)) {
                        C_Result._log.error((Object)("\u8840\u76df\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                        return;
                    }
                    if (Shutdown.isSHUTDOWN) {
                        pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                        return;
                    }
                    if (clan.getWarehouseUsingChar() != pc.getId()) {
                        pc.sendPackets(new S_ServerMessage("\u8840\u76df\u5009\u5eab\u6709\u4eba\u6b63\u5728\u4f7f\u7528\u4e2d\u3002"));
                        return;
                    }
                    if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 2)) {
                        return;
                    }
                    if (item == null || item.getCount() < count) {
                        break;
                    }
                    if (count <= 0) {
                        break;
                    }
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        break;
                    }
                    if (pc.getInventory().checkAddItem(item, count) != 0) {
                        pc.sendPackets(new S_ServerMessage(270));
                        break;
                    }
                    if (!pc.getInventory().consumeItem(40308, 30L)) {
                        pc.sendPackets(new S_ServerMessage(189));
                        break;
                    }
//                    RecordTable.get().recordeWarehouse_clan(pc.getName(), "\u9818\u53d6", "\u76df\u5009", item.getAllName(), count, item.getId(), pc.getIp());
                    clan.getDwarfForClanInventory().tradeItem(item, count, pc.getInventory());
                    clan.getDwarfForClanInventory().writeHistory(pc, item, count, 1);
                }
                if (clan != null) {
                    clan.setWarehouseUsingChar(0);
                }
            }
        }
        catch (Exception e) {
            C_Result._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void mode_warehouse_clan_in(final L1PcInstance pc, final int npcId, final int size) {
        final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
        try {
            if (clan != null) {
                for (int i = 0; i < size; ++i) {
                    final int objectId = this.readD();
                    final int count = Math.max(0, this.readD());
                    if (count <= 0) {
                        C_Result._log.error((Object)("\u8981\u6c42\u8840\u76df\u5009\u5eab\u5b58\u5165\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                        return;
                    }
                    final L1ItemInstance l1ItemInstance1 = pc.getInventory().getItem(objectId);
                    if (l1ItemInstance1 == null) {
                        C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                        return;
                    }
                    if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, count)) {
                        C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                        return;
                    }
                    if (Shutdown.isSHUTDOWN) {
                        pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                        return;
                    }
                    final L1ItemInstance item = l1ItemInstance1;
                    if (!pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), count)) {
                        return;
                    }
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    if (item.getItemId() == 44070) {
                        pc.sendPackets(new S_ServerMessage("\u5143\u5bf6\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                        return;
                    }
                    if (item.getItemId() == 40308) {
                        pc.sendPackets(new S_ServerMessage("\u91d1\u5e63\u9650\u5236\u5b58\u653e\u5009\u5eab"));
                        return;
                    }
                    if (!item.getItem().isTradable()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    if (item.get_time() != null) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        return;
                    }
                    if (!this.isAvailableTrade(pc, objectId, item, count)) {
                        return;
                    }
                    if (item == null || item.getCount() < count) {
                        break;
                    }
                    if (count <= 0) {
                        break;
                    }
                    if (item.getCount() <= 0L) {
                        break;
                    }
                    if (item.getCount() > 100000000L) {
                        break;
                    }
                    if (count > 100000000) {
                        break;
                    }
                    final Object[] petlist = pc.getPetList().values().toArray();
                    final Object[] arrayOfObject1;
                    final int j = (arrayOfObject1 = petlist).length;
                    for (byte b = 0; b < j; ++b) {
                        final Object petObject = arrayOfObject1[b];
                        if (petObject instanceof L1PetInstance) {
                            final L1PetInstance pet = (L1PetInstance)petObject;
                            if (item.getId() == pet.getItemObjId()) {
                                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                                break;
                            }
                        }
                    }
                    if (pc.getDoll(item.getId()) != null) {
                        pc.sendPackets(new S_ServerMessage(1181));
                        return;
                    }
                    if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                        pc.sendPackets(new S_ServerMessage(1181));
                        break;
                    }
                    if (clan.getDwarfForClanInventory().checkAddItemToWarehouse(item, count, 1) == 1) {
                        pc.sendPackets(new S_ServerMessage(75));
                        return;
                    }
//					RecordTable.get().recordeWarehouse_clan(pc.getName(), "存入", "盟倉", item.getAllName(), count, item.getId(), pc.getIp());
                    pc.getInventory().tradeItem(objectId, count, clan.getDwarfForClanInventory());
                    clan.getDwarfForClanInventory().writeHistory(pc, item, count, 0);
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(208));
            }
        }
        catch (Exception e) {
            C_Result._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void mode_warehouse_out(final L1PcInstance pc, final int npcId, final int size) throws InterruptedException {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u500b\u4eba\u5009\u5eab\u53d6\u51fa\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                break;
            }
            final L1ItemInstance item = pc.getDwarfInventory().getItem(objectId);
            if (item == null) {
                C_Result._log.error((Object)("\u500b\u4eba\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                break;
            }
            if (!DwarfReading.get().getUserItems(pc.getAccountName(), objectId, count)) {
                C_Result._log.error((Object)("\u5e33\u865f\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                break;
            }
            if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                result("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "\u73a9\u5bb6:\u3010 " + pc.getName() + " \u3011\u7591\u4f3c\u5229\u7528\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u6d17\u9053\u5177\uff0c\u8acb\u6ce8\u610f " + "\u6642\u9593:" + "(" + new Timestamp(System.currentTimeMillis()) + ")\u3002", 10);
                return;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                break;
            }
            if (pc.getInventory().checkAddItem(item, count) != 0) {
                pc.sendPackets(new S_ServerMessage(270));
                break;
            }
            if (!pc.getInventory().consumeItem(40308, 30L)) {
                pc.sendPackets(new S_ServerMessage(189));
                break;
            }
            if (item.isStackable()) {
                if (this.getPcItemCount(item, pc) >= count && this.getPcItemCount(item, pc) == item.getCount()) {
                    pc.getDwarfInventory().tradeItem(item, count, pc.getInventory());
                }
                else {
                    result("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "\u73a9\u5bb6" + ":\u3010 " + pc.getName() + " \u3011 " + "\u5009\u5eab\u9818\u53d6\u7570\u5e38\u3010" + item.getName() + "\u3011\u6642\u9593:" + "(" + new Timestamp(System.currentTimeMillis()) + ")\u3002", 7);
                }
            }
            else {
                pc.getDwarfInventory().tradeItem(item, count, pc.getInventory());
            }
        }
    }
    
    private void mode_warehouse_in(final L1PcInstance pc, final int npcId, final int size) throws InterruptedException {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u500b\u4eba\u5009\u5eab\u5b58\u5165\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                break;
            }
            final L1ItemInstance l1ItemInstance1 = pc.getInventory().getItem(objectId);
            if (l1ItemInstance1 == null) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                break;
            }
            if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, count)) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                break;
            }
            final L1ItemInstance item = l1ItemInstance1;
            if (!pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), count)) {
                return;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (!item.isStackable() && count != 1) {
                pc.sendPackets(new S_Disconnect());
                break;
            }
            if (!item.getItem().isTradable()) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                break;
            }
            if (item.getItemId() == 44070) {
                pc.sendPackets(new S_ServerMessage("\u5143\u5bf6\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (item.getItemId() == 40308) {
                pc.sendPackets(new S_ServerMessage("\u91d1\u5e63\u9650\u5236\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (item.get_time() != null) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                break;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                return;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (item.getCount() > 100000000L) {
                break;
            }
            if (count > 100000000) {
                break;
            }
            final Object[] petlist = pc.getPetList().values().toArray();
            final Object[] arrayOfObject1;
            final int j = (arrayOfObject1 = petlist).length;
            for (byte b = 0; b < j; ++b) {
                final Object petObject = arrayOfObject1[b];
                if (petObject instanceof L1PetInstance) {
                    final L1PetInstance pet = (L1PetInstance)petObject;
                    if (item.getId() == pet.getItemObjId()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        break;
                    }
                }
            }
            if (pc.getDoll(item.getId()) != null) {
                pc.sendPackets(new S_ServerMessage(1181));
                break;
            }
            if (pc.getDwarfInventory().checkAddItemToWarehouse(item, count, 0) == 1) {
                pc.sendPackets(new S_ServerMessage(75));
                break;
            }
//			RecordTable.get().recordeWarehouse_pc(pc.getName(), "存入", "個倉", item.getAllName(), count, item.getId(), pc.getIp());
            pc.getInventory().tradeItem(objectId, count, pc.getDwarfInventory());
//pc.sendPackets(new S_Paralysis(4, true));			
//Thread.sleep(3000);
//pc.sendPackets(new S_Paralysis(4, false));
      
// if (item.isStackable()) {
//if (getPcItemCount2(item, pc) >= count && 
//item.getCount() == getPcItemCount2(item, pc)) {
//pc.getInventory().tradeItem(objectId, count, 
//(L1Inventory)pc.getDwarfInventory());
// } else {      
//pc.getInventory().deleteItem(item);
//result("IP(" + pc.getNetConnection().getIp() + ")" + 
//"玩家" + ":【 " + pc.getName() + " 】 " + 
//"倉庫存取異常(並刪除)【" + item.getName() + "】時間:" + "(" + 
//new Timestamp(System.currentTimeMillis()) + 
//")。", 7);
//} 
//} else {
//RecordTable.get().recordeWarehouse_pc(pc.getName(), "存入", "個倉", item.getAllName(), count, item.getId(), pc.getIp());
//pc.getInventory().tradeItem(objectId, count, 
//(L1Inventory)pc.getDwarfInventory());
//} 
        }
    }
    
    private void mode_warehouse_cha_out(final L1PcInstance pc, final int npcId, final int size) {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u53d6\u51fa\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                return;
            }
            final L1ItemInstance item = pc.getDwarfForChaInventory().getItem(objectId);
            if (item == null) {
                C_Result._log.error((Object)("\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (!DwarfForChaReading.get().getUserItems(pc.getName(), objectId, count)) {
                C_Result._log.error((Object)("\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u5009\u5eab\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (Shutdown.isSHUTDOWN) {
                pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                return;
            }
            if (pc.getInventory().checkDBResultItemCount(pc.getId(), item.getId(), count, 1)) {
                return;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                break;
            }
            if (pc.getInventory().checkAddItem(item, count) != 0) {
                pc.sendPackets(new S_ServerMessage(270));
                break;
            }
            if (!pc.getInventory().consumeItem(40308, 30L)) {
                pc.sendPackets(new S_ServerMessage(189));
                break;
            }
            pc.getDwarfForChaInventory().tradeItem(item, count, pc.getInventory());
        }
    }
    
    private void mode_warehouse_cha_in(final L1PcInstance pc, final int npcId, final int size) {
        for (int i = 0; i < size; ++i) {
            final int objectId = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u89d2\u8272\u5c08\u5c6c\u5009\u5eab\u5b58\u5165\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                return;
            }
            final L1ItemInstance l1ItemInstance1 = pc.getInventory().getItem(objectId);
            if (l1ItemInstance1 == null) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u7269\u54c1\u70ba\u7a7a): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            if (!CharItemsReading.get().getUserItems(pc.getId(), objectId, count)) {
                C_Result._log.error((Object)("\u4eba\u7269\u80cc\u5305\u8cc7\u6599\u53d6\u51fa\u6578\u64da\u7570\u5e38(\u8a72\u4eba\u7269\u80cc\u5305\u6307\u5b9a\u6578\u64da\u6709\u8aa4): " + pc.getName() + "/" + pc.getNetConnection().hashCode()));
                return;
            }
            final L1ItemInstance item = l1ItemInstance1;
            if (item.getCount() <= 0L) {
                return;
            }
            if (!this.isAvailableTrade(pc, objectId, item, count)) {
                return;
            }
            if (item == null || item.getCount() < count) {
                break;
            }
            if (count <= 0) {
                break;
            }
            if (item.getCount() <= 0L) {
                break;
            }
            if (item.getCount() > 100000000L) {
                break;
            }
            if (count > 100000000) {
                break;
            }
            if (Shutdown.isSHUTDOWN) {
                pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                return;
            }
            if (item.getItemId() == 44070) {
                pc.sendPackets(new S_ServerMessage("\u5143\u5bf6\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (item.getItemId() == 40308) {
                pc.sendPackets(new S_ServerMessage("\u91d1\u5e63\u7121\u6cd5\u5b58\u653e\u5009\u5eab"));
                return;
            }
            if (!item.getItem().isTradable()) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                break;
            }
            if (item.get_time() != null) {
                pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                break;
            }
            final Object[] petlist = pc.getPetList().values().toArray();
            final Object[] arrayOfObject1;
            final int j = (arrayOfObject1 = petlist).length;
            for (byte b = 0; b < j; ++b) {
                final Object petObject = arrayOfObject1[b];
                if (petObject instanceof L1PetInstance) {
                    final L1PetInstance pet = (L1PetInstance)petObject;
                    if (item.getId() == pet.getItemObjId()) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                        break;
                    }
                }
            }
            if (pc.getDoll(item.getId()) != null) {
                pc.sendPackets(new S_ServerMessage(1181));
                break;
            }
            if (pc.get_power_doll() != null && pc.get_power_doll().getItemObjId() == item.getId()) {
                pc.sendPackets(new S_ServerMessage(1181));
                break;
            }
            if (pc.getDwarfForChaInventory().checkAddItemToWarehouse(item, count, 0) == 1) {
                pc.sendPackets(new S_ServerMessage(75));
                break;
            }
            pc.getInventory().tradeItem(objectId, count, pc.getDwarfForChaInventory());
        }
    }
    
    private void mode_sellpc(final L1PcInstance pc, final L1PcInstance targetPc, final int size) {
        final ArrayList<L1PrivateShopBuyList> buyList = targetPc.getBuyList();
        final boolean[] isRemoveFromList = new boolean[8];
        if (targetPc.isTradingInPrivateShop()) {
            return;
        }
        final int adenaItemId = targetPc.getMap().isUsableShop();
        if (adenaItemId <= 0 || ItemTable.get().getTemplate(adenaItemId) == null) {
            return;
        }
        targetPc.setTradingInPrivateShop(true);
        for (int i = 0; i < size; ++i) {
            final int itemObjectId = this.readD();
            int count = this.readCH();
            count = Math.max(0, count);
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                break;
            }
            final int order = this.readC();
            final L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
            if (item != null && item.get_time() == null) {
                final L1PrivateShopBuyList psbl = buyList.get(order);
                final int buyItemObjectId = psbl.getItemObjectId();
                final long buyPrice = psbl.getBuyPrice();
                final int buyTotalCount = psbl.getBuyTotalCount();
                final int buyCount = psbl.getBuyCount();
                if (count > buyTotalCount - buyCount) {
                    count = buyTotalCount - buyCount;
                }
                if (item.isEquipped()) {
                    pc.sendPackets(new S_ServerMessage(905));
                }
                else {
                    final L1ItemInstance srcItem = targetPc.getInventory().getItem(buyItemObjectId);
                    if (srcItem.get_time() == null) {
                        if (item.getItemId() != srcItem.getItemId() || item.getEnchantLevel() != srcItem.getEnchantLevel()) {
                            C_Result._log.error((Object)("\u53ef\u80fd\u4f7f\u7528bug\u9032\u884c\u4ea4\u6613 \u4eba\u7269\u540d\u7a31(\u8ce3\u51fa\u9053\u5177\u7d66\u4e88\u500b\u4eba\u5546\u5e97/\u4ea4\u6613\u689d\u4ef6\u4e0d\u543b\u5408): " + pc.getName() + " objid:" + pc.getId()));
                            return;
                        }
                        if (targetPc.getInventory().checkAddItem(item, count) != 0) {
                            pc.sendPackets(new S_ServerMessage(271));
                            break;
                        }
                        for (int j = 0; j < count; ++j) {
                            if (buyPrice * j > 2000000000L) {
                                targetPc.sendPackets(new S_ServerMessage(904, "2000000000"));
                                return;
                            }
                        }
                        if (!targetPc.getInventory().checkItem(adenaItemId, count * buyPrice)) {
                            targetPc.sendPackets(new S_ServerMessage(189));
                            break;
                        }
                        final L1ItemInstance adena = targetPc.getInventory().findItemId(adenaItemId);
                        if (adena != null && pc.getInventory().checkDBItemCount(pc.getId(), item.getItemId(), count)) {
                            if (item.getCount() < count) {
                                pc.sendPackets(new S_ServerMessage(989));
                                C_Result._log.error((Object)("\u53ef\u80fd\u4f7f\u7528bug\u9032\u884c\u4ea4\u6613 \u4eba\u7269\u540d\u7a31(\u8ce3\u51fa\u9053\u5177\u7d66\u4e88\u500b\u4eba\u5546\u5e97/\u4ea4\u6613\u6578\u91cf\u4e0d\u543b\u5408): " + pc.getName() + " objid:" + pc.getId()));
                            }
                            else {
                                OtherUserSellReading.get().add(item.getItem().getName(), item.getId(), (int)buyPrice, count, pc.getId(), pc.getName(), targetPc.getId(), targetPc.getName());
                                targetPc.getInventory().tradeItem(adena, count * buyPrice, pc.getInventory());
                                pc.getInventory().tradeItem(item, count, targetPc.getInventory());
                                final String message = String.valueOf(String.valueOf(String.valueOf(item.getItem().getNameId()))) + " (" + String.valueOf(count) + ")";
                                pc.sendPackets(new S_ServerMessage(877, targetPc.getName(), message));
                                psbl.setBuyCount(count + buyCount);
                                buyList.set(order, psbl);
                                if (psbl.getBuyCount() == psbl.getBuyTotalCount()) {
                                    isRemoveFromList[order] = true;
                                }
//								RecordTable.get().recordeShop(pc.getName(), targetPc.getName(), item.getAllName(), count, item.getId(), price, adena.getName(), pc.getIp(), targetPc.getIp());
                            }
                        }
                    }
                }
            }
        }
        for (int i = 7; i >= 0; --i) {
            if (isRemoveFromList[i]) {
                buyList.remove(i);
            }
        }
        targetPc.setTradingInPrivateShop(false);
    }
    
    private void mode_buypc(final L1PcInstance pc, final L1PcInstance targetPc, final int size) {
        ArrayList<L1PrivateShopSellList> sellList = targetPc.getSellList();
        final boolean[] isRemoveFromList = new boolean[8];
        if (targetPc.isTradingInPrivateShop()) {
            return;
        }
        final int adenaItemId = targetPc.getMap().isUsableShop();
        if (adenaItemId <= 0 || ItemTable.get().getTemplate(adenaItemId) == null) {
            return;
        }
        sellList = targetPc.getSellList();
        synchronized (sellList) {
            if (pc.getPartnersPrivateShopItemCount() != sellList.size()) {
                // monitorexit(sellList)
                return;
            }
            targetPc.setTradingInPrivateShop(true);
            for (int i = 0; i < size; ++i) {
                final int order = this.readD();
                int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u8cb7\u5165\u500b\u4eba\u5546\u5e97\u7269\u54c1\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                    break;
                }
                if (Shutdown.isSHUTDOWN) {
                    pc.sendPackets(new S_SystemMessage("\u76ee\u524d\u670d\u52d9\u5668\u6e96\u5099\u95dc\u6a5f\u72c0\u614b"));
                    // monitorexit(sellList)
                    return;
                }
                final L1PrivateShopSellList pssl = sellList.get(order);
                final int itemObjectId = pssl.getItemObjectId();
                final int sellPrice = pssl.getSellPrice();
                final int sellTotalCount = pssl.getSellTotalCount();
                final int sellCount = pssl.getSellCount();
                final L1ItemInstance item = targetPc.getInventory().getItem(itemObjectId);
                if (item != null && item.get_time() == null) {
                    if (count > sellTotalCount - sellCount) {
                        count = sellTotalCount - sellCount;
                    }
                    if (count > 0) {
                        if (pc.getInventory().checkAddItem(item, count) != 0) {
                            pc.sendPackets(new S_ServerMessage(270));
                            break;
                        }
                        for (int j = 0; j < count; ++j) {
                            if (sellPrice * j > 2000000000) {
                                pc.sendPackets(new S_ServerMessage(904, "2000000000"));
                                targetPc.setTradingInPrivateShop(false);
                                // monitorexit(sellList)
                                return;
                            }
                        }
                        final int price = count * sellPrice;
                        final L1ItemInstance adena = pc.getInventory().findItemId(adenaItemId);
                        if (adena == null) {
                            pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(adenaItemId).getName()));
                        }
                        else if (adena.getCount() < price) {
                            pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(adenaItemId).getName()));
                        }
                        else if (targetPc != null && item.getCount() < count) {
                            pc.sendPackets(new S_ServerMessage(989));
                        }
                        else if (targetPc != null && pc.getInventory().checkDBItemCount(pc.getId(), adena.getItemId(), price) && adena.getCount() >= price) {
                            if (item.getCount() < count) {
                                pc.sendPackets(new S_ServerMessage(989));
                            }
                            else {
                                OtherUserBuyReading.get().add(item.getItem().getName(), item.getId(), sellPrice, count, pc.getId(), pc.getName(), targetPc.getId(), targetPc.getName());
                                targetPc.getInventory().tradeItem(item, count, pc.getInventory());
                                pc.getInventory().tradeItem(adena, price, targetPc.getInventory());
                                final String message = String.valueOf(String.valueOf(String.valueOf(item.getItem().getNameId()))) + " (" + String.valueOf(count) + ")";
                                targetPc.sendPackets(new S_ServerMessage(877, pc.getName(), message));
                                pssl.setSellCount(count + sellCount);
                                sellList.set(order, pssl);
                                if (pssl.getSellCount() == pssl.getSellTotalCount()) {
                                    isRemoveFromList[order] = true;
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 7; i >= 0; --i) {
                if (isRemoveFromList[i]) {
                    sellList.remove(i);
                }
            }
            targetPc.setTradingInPrivateShop(false);
        }
        // monitorexit(sellList)
    }
    
    private void mode_sell(final L1PcInstance pc, final int npcId, final int size) {
        final L1Shop shop = ShopTable.get().get(npcId);
        if (shop != null) {
            final L1ShopSellOrderList orderList = shop.newSellOrderList(pc);
            for (int i = 0; i < size; ++i) {
                final int objid = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                }
                else {
                    orderList.add(objid, count);
                }
            }
            shop.buyItems(pc, orderList);
        }
        else {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void mode_buy(final L1PcInstance pc, final int npcId, final int size) {
        final L1Shop shop = ShopTable.get().get(npcId);
        if (shop != null) {
            final L1ShopBuyOrderList orderList = shop.newBuyOrderList();
            for (int i = 0; i < size; ++i) {
                final int orderId = this.readD();
                final int count = Math.max(0, this.readD());
                if (count <= 0) {
                    C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                }
                else {
                    orderList.add(orderId, count);
                }
            }
            shop.sellItems(pc, orderList);
        }
        else {
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void mode_sellde(final L1PcInstance pc, final L1DeInstance de, final int size) {
        final Map<Integer, int[]> buyList = de.get_buyList();
        final Queue<Integer> removeList = new ConcurrentLinkedQueue<Integer>();
        for (int i = 0; i < size; ++i) {
            final int itemObjectId = this.readD();
            int count = this.readCH();
            count = Math.max(0, count);
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u5217\u8868\u7269\u54c1\u53d6\u5f97\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
            }
            else {
                final L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
                if (item != null) {
                    if (item.get_time() != null) {
                        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
                    }
                    else if (item.isEquipped()) {
                        pc.sendPackets(new S_ServerMessage(905));
                    }
                    else {
                        final int[] sellX = buyList.get(item.getItemId());
                        if (sellX != null) {
                            final int price = sellX[0];
                            final int enchantlvl = sellX[1];
                            final int buycount = sellX[2];
                            if (item.getEnchantLevel() == enchantlvl && buycount >= count) {
                                if (pc.getInventory().removeItem(itemObjectId, count) == count) {
                                    pc.getInventory().storeItem(40308, price * count);
                                }
                                else {
                                    pc.sendPackets(new S_ServerMessage(989));
                                }
                                final int newcount = buycount - count;
                                if (newcount <= 0) {
                                    removeList.add(item.getItemId());
                                }
                                else {
                                    final int[] newSellX = { price, enchantlvl, newcount };
                                    de.updateBuyList(item.getItemId(), newSellX);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!removeList.isEmpty()) {
            final Iterator<Integer> iter = removeList.iterator();
            while (iter.hasNext()) {
                final Integer reitem = iter.next();
                iter.remove();
                buyList.remove(reitem);
            }
        }
        pc.get_otherList().DELIST.clear();
    }
    
    private void mode_buyde(final L1PcInstance pc, final L1DeInstance de, final int size) {
        final Map<L1ItemInstance, Integer> sellList = de.get_sellList();
        final Queue<L1ItemInstance> removeList = new ConcurrentLinkedQueue<L1ItemInstance>();
        for (int i = 0; i < size; ++i) {
            final int order = this.readD();
            final int count = Math.max(0, this.readD());
            if (count <= 0) {
                C_Result._log.error((Object)("\u8981\u6c42\u8cb7\u5165\u500b\u4eba\u5546\u5e97\u7269\u54c1\u50b3\u56de\u6578\u91cf\u5c0f\u65bc\u7b49\u65bc0: " + pc.getName()));
                break;
            }
            final Map<Integer, L1ItemInstance> list = pc.get_otherList().DELIST;
            for (final int key : list.keySet()) {
                if (order == key) {
                    final L1ItemInstance item = list.get(key);
                    if (item == null) {
                        continue;
                    }
                    if (item.getCount() < count) {
                        continue;
                    }
                    if (pc.getInventory().checkAddItem(item, count) != 0) {
                        pc.sendPackets(new S_ServerMessage(270));
                        break;
                    }
                    final long price = count * sellList.get(item);
                    if (!pc.getInventory().consumeItem(40308, price)) {
                        pc.sendPackets(new S_ServerMessage(189));
                        break;
                    }
                    if (item.isStackable()) {
                        pc.getInventory().storeItem(item.getItemId(), count);
                        final long newCount = item.getCount() - count;
                        if (newCount > 0L) {
                            item.setCount(newCount);
                        }
                        else {
                            removeList.add(item);
                        }
                    }
                    else {
                        pc.getInventory().storeTradeItem(item);
                        removeList.add(item);
                    }
                }
            }
        }
        if (!removeList.isEmpty()) {
            final Iterator<L1ItemInstance> iter = removeList.iterator();
            while (iter.hasNext()) {
                final L1ItemInstance reitem = iter.next();
                iter.remove();
                sellList.remove(reitem);
            }
        }
        pc.get_otherList().DELIST.clear();
    }
    
    private boolean isAvailableTrade(final L1PcInstance pc, final int objectId, final L1ItemInstance item, final int count) {
        boolean result = true;
        if (item == null) {
            result = false;
        }
        if (objectId != item.getId()) {
            result = false;
        }
        if (!item.isStackable() && count != 1) {
            result = false;
        }
        if (item.getCount() <= 0L || item.getCount() > 2000000000L) {
            result = false;
        }
        if (count <= 0 || count > 2000000000) {
            result = false;
        }
        if (!result) {
            pc.sendPackets(new S_Disconnect());
        }
        return result;
    }
    
    private static void result(final String info, final int x) {
        String y = "";
        if (x == 1) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5009\u5eab\u5b58\u653e\u7d00\u9304.txt";
        }
        else if (x == 2) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5009\u5eab\u9818\u53d6\u7d00\u9304.txt";
        }
        else if (x == 3) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u5996\u7cbe\u5009\u5eab\u5b58\u653e\u7d00\u9304.txt";
        }
        else if (x == 4) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u5996\u7cbe\u5009\u5eab\u9818\u53d6\u7d00\u9304.txt";
        }
        else if (x == 5) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u8840\u76df\u5009\u5eab\u5b58\u653e\u7d00\u9304.txt";
        }
        else if (x == 6) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u8840\u76df\u5009\u5eab\u9818\u53d6\u7d00\u9304.txt";
        }
        else if (x == 7) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u5009\u5eab\u9818\u53d6\u7570\u5e38\u8a18\u9304.txt";
        }
        else if (x == 8) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5546\u5e97\u639b\u8ce3\u7d00\u9304.txt";
        }
        else if (x == 9) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5546\u5e97\u639b\u6536\u7d00\u9304.txt";
        }
        else if (x == 10) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u8207NPC\u5546\u5e97\u7570\u5e38\u8a18\u9304.txt";
        }
        else if (x == 11) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5546\u5e97\u5143\u5bf6\u639b\u8ce3\u7d00\u9304.txt";
        }
        else if (x == 12) {
            y = "\u73a9\u5bb6\u7d00\u9304/\u500b\u4eba\u5546\u5e97\u5143\u5bf6\u639b\u6536\u7d00\u9304.txt";
        }
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(y, true));
            out.write(String.valueOf(String.valueOf(String.valueOf(info))) + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getPcItemCount(final L1ItemInstance item, final L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_warehouse where account_name=? && item_id =? order by id DESC limit 0,1;");
            pstm.setString(1, pc.getAccountName());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return n;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        return n;
    }
    
    public int getPcItemCountelf(final L1ItemInstance item, final L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_elf_warehouse where account_name=? && item_id =? order by id DESC limit 0,1;");
            pstm.setString(1, pc.getAccountName());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return n;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        return n;
    }
    
    public int getPcItemCount2(final L1ItemInstance item, final L1PcInstance pc) {
        int n = 0;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("select count from character_items where char_id=? && item_id =? order by id DESC limit 0,1;");
            pstm.setInt(1, pc.getId());
            pstm.setInt(2, item.getItemId());
            rs = pstm.executeQuery();
            if (rs.next()) {
                n = rs.getInt(1);
            }
        }
        catch (SQLException e) {
            C_Result._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return n;
        }
        finally {
            SQLUtil.close(rs);
            SQLUtil.close(pstm);
            SQLUtil.close(con);
        }
        SQLUtil.close(rs);
        SQLUtil.close(pstm);
        SQLUtil.close(con);
        return n;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
