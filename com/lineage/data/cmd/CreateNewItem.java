package com.lineage.data.cmd;

import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.lock.CharShiftingReading;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.utils.DigitalUtil;
import com.lineage.server.templates.L1Item;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ItemCount;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class CreateNewItem
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog((Class)CreateNewItem.class);
    }
    
    public static boolean delItems(final L1PcInstance pc, final int[] srcItemIds, final int[] counts, final long amount) {
        try {
            if (pc == null) {
                return false;
            }
            if (amount <= 0L) {
                return false;
            }
            if (srcItemIds.length <= 0) {
                return false;
            }
            if (counts.length <= 0) {
                return false;
            }
            if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error((Object)"\u9053\u5177\u4ea4\u63db\u7269\u54c1\u8207\u6578\u91cf\u9663\u5217\u8a2d\u7f6e\u7570\u5e38!");
                return false;
            }
            for (int i = 0; i < srcItemIds.length; ++i) {
                final long itemCount = counts[i] * amount;
                final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                if (item == null) {
                    return false;
                }
            }
            for (int i = 0; i < srcItemIds.length; ++i) {
                final long itemCount2 = counts[i] * amount;
                final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                if (item == null) {
                    return false;
                }
                pc.getInventory().removeItem(item, itemCount2);
            }
            return true;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return false;
        }
    }
    
    public static boolean getItem(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final int[] items, final int[] counts, final int[] gitems, final int[] gcounts, final long amount) {
        final long xcount = checkNewItem(pc, items, counts);
        if (xcount <= 0L) {
            return true;
        }
        if (amount == 0L) {
            pc.sendPackets(new S_ItemCount(npc.getId(), (int)xcount, cmd));
            return false;
        }
        if (xcount >= amount) {
            createNewItem(pc, items, counts, gitems, amount, gcounts);
        }
        return true;
    }
    
    public static long checkNewItem(final L1PcInstance pc, final int srcItemId, final int count) {
        try {
            if (pc == null) {
                return -1L;
            }
            final L1ItemInstance item = pc.getInventory().findItemIdNoEq(srcItemId);
            long itemCount = -1L;
            if (item != null) {
                itemCount = item.getCount() / count;
            }
            if (itemCount < 1L) {
                final L1Item tgItem = ItemTable.get().getTemplate(srcItemId);
                pc.sendPackets(new S_ServerMessage(337, String.valueOf(String.valueOf(tgItem.getNameId())) + "(" + (count - ((item == null) ? 0L : item.getCount())) + ")"));
                return -1L;
            }
            return itemCount;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return -1L;
        }
    }
    
    public static long checkNewItem(final L1PcInstance pc, final int[] srcItemIds, final int[] counts) {
        try {
            if (pc == null) {
                return -1L;
            }
            if (srcItemIds.length <= 0) {
                return -1L;
            }
            if (counts.length <= 0) {
                return -1L;
            }
            if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error((Object)"\u9053\u5177\u4ea4\u63db\u7269\u54c1\u8207\u6578\u91cf\u9663\u5217\u8a2d\u7f6e\u7570\u5e38!");
                return -1L;
            }
            final long[] checkCount = new long[srcItemIds.length];
            boolean error = false;
            for (int i = 0; i < srcItemIds.length; ++i) {
                final int itemid = srcItemIds[i];
                final int count = counts[i];
                final L1ItemInstance item = pc.getInventory().findItemIdNoEq(itemid);
                if (item != null) {
                    final long itemCount = item.getCount() / count;
                    checkCount[i] = itemCount;
                    if (itemCount < 1L) {
                        pc.sendPackets(new S_ServerMessage(337, String.valueOf(String.valueOf(item.getName())) + "(" + (count - item.getCount()) + ")"));
                        error = true;
                    }
                }
                else {
                    final L1Item tgItem = ItemTable.get().getTemplate(itemid);
                    pc.sendPackets(new S_ServerMessage(337, String.valueOf(String.valueOf(tgItem.getNameId())) + "(" + count + ")"));
                    error = true;
                }
            }
            if (!error) {
                final long count2 = DigitalUtil.returnMin(checkCount);
                return count2;
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return -1L;
    }
    
    public static void createNewItem(final L1PcInstance pc, final int[] srcItemIds, final int[] counts, final int getItemId, final long amount, final int getCount) {
        try {
            if (pc == null) {
                return;
            }
            if (amount <= 0L) {
                return;
            }
            if (srcItemIds.length <= 0) {
                return;
            }
            if (counts.length <= 0) {
                return;
            }
            if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error((Object)"\u9053\u5177\u4ea4\u63db\u7269\u54c1\u8207\u6578\u91cf\u9663\u5217\u8a2d\u7f6e\u7570\u5e38!");
                return;
            }
            if (getItemId == 0) {
                return;
            }
            if (getCount == 0) {
                return;
            }
            boolean error = false;
            for (int i = 0; i < srcItemIds.length; ++i) {
                final long itemCount = counts[i] * amount;
                final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                if (item == null) {
                    error = true;
                }
            }
            if (!error && !getItemIsOk(pc, getItemId, amount, getCount)) {
                error = true;
            }
            if (!error) {
                for (int i = 0; i < srcItemIds.length; ++i) {
                    final long itemCount2 = counts[i] * amount;
                    final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                    if (item != null) {
                        pc.getInventory().removeItem(item, itemCount2);
                    }
                    else {
                        error = true;
                    }
                }
            }
            if (!error) {
                giveItem(pc, getItemId, amount, getCount);
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void createNewItem(final L1PcInstance pc, final int[] srcItemIds, final int[] counts, final int[] getItemIds, final long amount, final int[] getCounts) {
        try {
            if (pc == null) {
                return;
            }
            if (amount <= 0L) {
                return;
            }
            if (srcItemIds.length <= 0) {
                return;
            }
            if (counts.length <= 0) {
                return;
            }
            if (srcItemIds.length != counts.length) {
                CreateNewItem._log.error((Object)"\u9053\u5177\u4ea4\u63db\u7269\u54c1\u8207\u6578\u91cf\u9663\u5217\u8a2d\u7f6e\u7570\u5e38!");
                return;
            }
            if (getItemIds.length <= 0) {
                return;
            }
            if (getCounts.length <= 0) {
                return;
            }
            if (getItemIds.length != getCounts.length) {
                CreateNewItem._log.error((Object)"\u9053\u5177\u4ea4\u63db\u7269\u54c1\u8207\u6578\u91cf\u9663\u5217\u8a2d\u7f6e\u7570\u5e38!");
                return;
            }
            boolean error = false;
            for (int i = 0; i < srcItemIds.length; ++i) {
                final long itemCount = counts[i] * amount;
                final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount);
                if (item == null) {
                    error = true;
                }
            }
            if (!error) {
                for (int i = 0; i < getItemIds.length; ++i) {
                    if (!getItemIsOk(pc, getItemIds[i], amount, getCounts[i])) {
                        error = true;
                    }
                }
            }
            if (!error) {
                for (int i = 0; i < srcItemIds.length; ++i) {
                    final long itemCount2 = counts[i] * amount;
                    final L1ItemInstance item = pc.getInventory().checkItemX(srcItemIds[i], itemCount2);
                    if (item != null) {
                        pc.getInventory().removeItem(item, itemCount2);
                    }
                    else {
                        error = true;
                    }
                }
            }
            if (!error) {
                for (int i = 0; i < getItemIds.length; ++i) {
                    giveItem(pc, getItemIds[i], amount, getCounts[i]);
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private static boolean getItemIsOk(final L1PcInstance pc, final int getItemId, final long amount, final int getCount) {
        try {
            if (pc == null) {
                return false;
            }
            final L1Item tgItem = ItemTable.get().getTemplate(getItemId);
            if (pc.getInventory().checkAddItem(tgItem, amount * getCount) != 0) {
                return false;
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return true;
    }
    
    private static void giveItem(final L1PcInstance pc, final int getItemId, final long amount, final int getCount) {
        try {
            if (pc == null) {
                return;
            }
            final L1Item tgItem = ItemTable.get().getTemplate(getItemId);
            if (tgItem.isStackable()) {
                final L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                tgItemX.setCount(amount * getCount);
                createNewItem(pc, tgItemX);
            }
            else {
                for (int get = 0; get < amount * getCount; ++get) {
                    final L1ItemInstance tgItemX2 = ItemTable.get().createItem(getItemId);
                    tgItemX2.setCount(1L);
                    createNewItem(pc, tgItemX2);
                }
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void createNewItem(final L1PcInstance pc, final int srcItemId, final int count, final int getItemId, final int getCount) {
        createNewItem(pc, srcItemId, count, getItemId, 1L, getCount);
    }
    
    public static void createNewItem(final L1PcInstance pc, final int srcItemId, final int count, final int getItemId, final long amount, final int getCount) {
        final long itemCount1 = count * amount;
        final L1ItemInstance item1 = pc.getInventory().checkItemX(srcItemId, itemCount1);
        if (item1 != null) {
            final L1ItemInstance tgItem = ItemTable.get().createItem(getItemId);
            if (pc.getInventory().checkAddItem(tgItem, amount * getCount) == 0) {
                pc.getInventory().removeItem(item1, itemCount1);
                if (tgItem.isStackable()) {
                    tgItem.setCount(amount * getCount);
                    createNewItem(pc, tgItem);
                }
                else {
                    for (int get = 0; get < amount * getCount; ++get) {
                        final L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                        tgItemX.setCount(1L);
                        createNewItem(pc, tgItemX);
                    }
                }
            }
            else {
                World.get().removeObject(tgItem);
            }
        }
    }
    
    public static void createNewItem(final L1PcInstance pc, final int srcItemId, final int count, final int getItemId, final long amount) {
        final long itemCount1 = count * amount;
        final L1ItemInstance item1 = pc.getInventory().checkItemX(srcItemId, itemCount1);
        if (item1 != null) {
            final L1ItemInstance tgItem = ItemTable.get().createItem(getItemId);
            if (pc.getInventory().checkAddItem(tgItem, amount) == 0) {
                pc.getInventory().removeItem(item1, itemCount1);
                if (tgItem.isStackable()) {
                    tgItem.setCount(amount);
                    createNewItem(pc, tgItem);
                }
                else {
                    for (int get = 0; get < amount; ++get) {
                        final L1ItemInstance tgItemX = ItemTable.get().createItem(getItemId);
                        tgItemX.setCount(1L);
                        createNewItem(pc, tgItemX);
                    }
                }
            }
        }
    }
    
    public static void createNewItem(final L1PcInstance pc, final L1ItemInstance item) {
        try {
            if (pc == null) {
                return;
            }
            if (item == null) {
                return;
            }
            pc.getInventory().storeItem(item);
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void createNewItem(final L1PcInstance pc, final L1ItemInstance item, final long count) {
        try {
            if (pc == null) {
                return;
            }
            if (item == null) {
                return;
            }
            item.setCount(count);
            if (pc.getInventory().checkAddItem(item, count) == 0) {
                pc.getInventory().storeItem(item);
            }
            else {
                item.set_showId(pc.get_showId());
                World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
            }
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static boolean createNewItem(final L1PcInstance pc, final int item_id, final long count) {
        try {
            if (pc == null) {
                return false;
            }
            final L1ItemInstance item = ItemTable.get().createItem(item_id);
            if (item != null) {
                item.setCount(count);
                if (pc.getInventory().checkAddItem(item, count) == 0) {
                    pc.getInventory().storeItem(item);
                }
                else {
                    item.set_showId(pc.get_showId());
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                return true;
            }
            CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + item_id + ")"));
            return false;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return false;
        }
    }
    
    public static boolean createNewItemAi(final L1PcInstance pc, final int item_id, final long count) {
        try {
            if (pc == null) {
                return false;
            }
            final L1ItemInstance item = ItemTable.get().createItem(item_id);
            if (item != null) {
                item.setCount(count);
                if (pc.getInventory().checkAddItem(item, count) == 0) {
                    pc.getInventory().storeItem(item);
                }
                else {
                    item.set_showId(pc.get_showId());
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                return true;
            }
            CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + item_id + ")"));
            return false;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return false;
        }
    }
    
    public static void getQuestItem(final L1Character atk, final L1NpcInstance npc, final int item_id, final long count) {
        try {
            if (atk == null) {
                return;
            }
            final L1ItemInstance item = ItemTable.get().createItem(item_id);
            if (item != null) {
                item.setCount(count);
                if (atk.getInventory().checkAddItem(item, count) == 0) {
                    atk.getInventory().storeItem(item);
                }
                else {
                    item.set_showId(atk.get_showId());
                    World.get().getInventory(atk.getX(), atk.getY(), atk.getMapId()).storeItem(item);
                }
                if (atk instanceof L1PcInstance) {
                    final L1PcInstance pc = (L1PcInstance)atk;
                    if (npc != null) {
                        pc.sendPackets(new S_ServerMessage(143, npc.getNameId(), item.getLogName()));
                        pc.sendPackets(new S_HelpMessage("\\fW" + npc.getNameId() + "\u7d66\u4f60" + item.getLogName()));
                    }
                    else {
                        pc.sendPackets(new S_HelpMessage("\\fW\u7d66\u4f60", item.getLogName()));
                    }
                }
            }
            else {
                CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + item_id + ")"));
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void updateA(final L1PcInstance pc, final L1ItemInstance srcItem, final L1ItemInstance newItem, final int enchant, final int down, final int mode) {
        try {
            if (pc == null) {
                return;
            }
            if (srcItem == null) {
                return;
            }
            if (newItem == null) {
                return;
            }
            newItem.setCount(1L);
            if (srcItem.getEnchantLevel() > enchant) {
                newItem.setEnchantLevel(srcItem.getEnchantLevel() - down);
            }
            else {
                newItem.setEnchantLevel(srcItem.getEnchantLevel());
            }
            newItem.setAttrEnchantKind(srcItem.getAttrEnchantKind());
            newItem.setAttrEnchantLevel(srcItem.getAttrEnchantLevel());
            newItem.setIdentified(true);
            final int srcObjid = srcItem.getId();
            final L1Item srcItemX = srcItem.getItem();
            if (pc.getInventory().removeItem(srcItem) == 1L) {
                pc.getInventory().storeItem(newItem);
                pc.sendPackets(new S_ServerMessage(403, newItem.getLogName()));
                CharShiftingReading.get().newShifting(pc, 0, null, srcObjid, srcItemX, newItem, mode);
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void updateB(final L1PcInstance pc, final L1ItemInstance srcItem, final int newid) {
        try {
            if (pc == null) {
                return;
            }
            if (srcItem == null) {
                return;
            }
            final L1ItemInstance newItem = ItemTable.get().createItem(newid);
            if (newItem != null) {
                if (pc.getInventory().removeItem(srcItem) == 1L) {
                    pc.getInventory().storeItem(newItem);
                }
            }
            else {
                CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + newid + ")"));
            }
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void createNewItem_LV(final L1PcInstance pc, final int item_id, final int count, final int EnchantLevel) {
        final L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount(count);
        item.setEnchantLevel(EnchantLevel);
        item.setIdentified(true);
        if (pc.getInventory().checkAddItem(item, count) == 0) {
            pc.getInventory().storeItem(item);
            pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
        }
        else {
            World.get().removeObject(item);
        }
    }
    
    public static boolean createNewItem(final L1PcInstance pc, final int item_id, final int count, final int en) {
        try {
            if (pc == null) {
                return false;
            }
            final L1ItemInstance item = ItemTable.get().createItem(item_id);
            if (item != null) {
                if (item.isStackable()) {
                    item.setCount(count);
                }
                else {
                    item.setCount(1L);
                }
                item.setEnchantLevel(en);
                if (pc.getInventory().checkAddItem(item, count) == 0) {
                    pc.getInventory().storeItem(item);
                }
                else {
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }
                pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                return true;
            }
            CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + item_id + ")"));
            return false;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return false;
        }
    }
    
    public static void createNewItem_NPC(final L1PcInstance pc, final String npcName, final int item_id, final int count) {
        final L1ItemInstance item = ItemTable.get().createItem(item_id);
        item.setCount(count);
        if (pc.getInventory().checkAddItem(item, count) == 0) {
            pc.getInventory().storeItem(item);
        }
        else {
            World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
        }
        pc.sendPackets(new S_ServerMessage(143, npcName, item.getLogName()));
    }
    
    public static boolean createNewItemRandomGifts(final L1PcInstance pc, final int item_id, final int count) {
        try {
            if (pc == null) {
                return false;
            }
            final L1ItemInstance item = ItemTable.get().createItem(item_id);
            if (item != null) {
                if (item.isStackable()) {
                    item.setCount(count);
                }
                else {
                    item.setCount(1L);
                }
                if (pc.getInventory().checkAddItem(item, count) == 0) {
                    pc.getInventory().storeItem(item);
                }
                else {
                    World.get().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
                }
                pc.sendPackets(new S_SystemMessage(String.format("\u606d\u559c\u4f60\u7372\u5f97\u7cfb\u7d71\u7dda\u4e0a\u62bd\u734e\u5927\u79ae\uff1a", item.getLogName())));
                return true;
            }
            CreateNewItem._log.error((Object)("\u7d66\u4e88\u7269\u4ef6\u5931\u6557 \u539f\u56e0: \u6307\u5b9a\u7de8\u865f\u7269\u54c1\u4e0d\u5b58\u5728(" + item_id + ")"));
            return false;
        }
        catch (Exception e) {
            CreateNewItem._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return false;
        }
    }
}
