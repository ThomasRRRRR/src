package com.lineage.server.model;

import com.lineage.echo.ClientExecutor;
import java.util.Iterator;
import com.lineage.list.OnlineUser;
import com.lineage.server.datatables.sql.DwarfTable;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.lineage.server.templates.L1Item;
import java.sql.Statement;
import com.lineage.server.utils.SQLUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.concurrent.CopyOnWriteArrayList;
import com.lineage.server.datatables.lock.DwarfReading;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import org.apache.commons.logging.Log;

/**
 * 倉庫資料
 * @author dexc
 *
 */
public class L1DwarfInventory extends L1Inventory {

	public static final Log _log = LogFactory.getLog(L1DwarfInventory.class);

	private static final long serialVersionUID = 1L;

	private final L1PcInstance _owner;

	public L1DwarfInventory(final L1PcInstance owner) {
		this._owner = owner;
	}

	/**
	 * 傳回該倉庫資料
	 */
	@Override
	public void loadItems() {
		try {
			final CopyOnWriteArrayList<L1ItemInstance> items = 
				DwarfReading.get().loadItems(this._owner.getAccountName());
			
			if (items != null) {
				_items = items;
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 加入倉庫數據
	 */
	@Override
	public void insertItem(final L1ItemInstance item) {
		if (item.getCount() <= 0) {
			return;
		}
		try {
			DwarfReading.get().insertItem(this._owner.getAccountName(), item);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 倉庫資料更新(物品數量)
	 */
	@Override
	public void updateItem(final L1ItemInstance item) {
		//System.out.println("倉庫資料更新(物品數量)");
		try {
			DwarfReading.get().updateItem(item);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 倉庫物品資料刪除
	 */
	@Override
	public void deleteItem(final L1ItemInstance item) {
		//System.out.println("倉庫物品資料刪除");
		try {
			_items.remove(item);
			DwarfReading.get().deleteItem(this._owner.getAccountName(), item);
			World.get().removeObject(item);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	    @Override
    public void onRemoveItem(final L1ItemInstance item) {
        this._items.remove(item);
    }
    
    public static void present(final int minlvl, final int maxlvl, final int itemid, final int count) throws Exception {
        final L1Item temp = ItemTable.get().getTemplate(itemid);
        if (temp == null) {
            return;
        }
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = DatabaseFactory.get().getConnection();
            pstm = con.prepareStatement("SELECT distinct(account_name) as account_name FROM characters WHERE level between ? and ?");
            pstm.setInt(1, minlvl);
            pstm.setInt(2, maxlvl);
            rs = pstm.executeQuery();
            final ArrayList<String> accountList = new ArrayList<String>();
            while (rs.next()) {
                accountList.add(rs.getString("account_name"));
            }
            present(accountList, itemid, count);
        } catch (SQLException e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
    
    private static void present(final ArrayList<String> accountList, final int itemid, final int count) throws Exception {
        final L1Item temp = ItemTable.get().getTemplate(itemid);
        if (temp == null) {
        	throw new Exception("指定的道具編號不存在。");
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DatabaseFactory.get().getConnection();
            con.setAutoCommit(false);
            for (final String account : accountList) {
                if (temp.isStackable()) {
                    final L1ItemInstance item = ItemTable.get().createItem(itemid);
                    item.setCount(count);
                    item.setBless(item.getItem().getBless());
                    DwarfTable.addItem(account, item);
                    ps = con.prepareStatement("INSERT INTO `character_warehouse` SET `id`=?,`account_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
                    int i = 0;
                    ps.setInt(++i, item.getId());
                    ps.setString(++i, account);
                    ps.setInt(++i, item.getItemId());
                    ps.setString(++i, item.getItem().getName());
                    ps.setLong(++i, item.getCount());
                    ps.setInt(++i, item.getEnchantLevel());
                    ps.setInt(++i, item.isIdentified() ? 1 : 0);
                    ps.setInt(++i, item.get_durability());
                    ps.setInt(++i, item.getChargeCount());
                    ps.setInt(++i, item.getRemainingTime());
                    if (item.getLastUsed() != null) {
                        System.out.println(item.getLastUsed().getTime());
                    }
                    ps.setTimestamp(++i, item.getLastUsed());
                    ps.setInt(++i, item.getBless());
                    ps.setInt(++i, item.getAttrEnchantKind());
                    ps.setInt(++i, item.getAttrEnchantLevel());
                    ps.setInt(++i, item.getItemAttack());
                    ps.setInt(++i, item.getItemBowAttack());
                    ps.setInt(++i, item.getItemHit());
                    ps.setInt(++i, item.getItemBowHit());
                    ps.setInt(++i, item.getItemReductionDmg());
                    ps.setInt(++i, item.getItemSp());
                    ps.setInt(++i, item.getItemprobability());
                    ps.setInt(++i, item.getItemStr());
                    ps.setInt(++i, item.getItemDex());
                    ps.setInt(++i, item.getItemInt());
                    ps.setInt(++i, item.getItemCon());
                    ps.setInt(++i, item.getItemCha());
                    ps.setInt(++i, item.getItemWis());
                    ps.setInt(++i, item.getItemHp());
                    ps.setInt(++i, item.getItemMp());
                    ps.setInt(++i, item.getItemMr());
                    ps.setInt(++i, item.getItemAc());
                    ps.setInt(++i, item.getItemMag_Red());
                    ps.setInt(++i, item.getItemMag_Hit());
                    ps.setInt(++i, item.getItemDg());
                    ps.setInt(++i, item.getItemistSustain());
                    ps.setInt(++i, item.getItemistStun());
                    ps.setInt(++i, item.getItemistStone());
                    ps.setInt(++i, item.getItemistSleep());
                    ps.setInt(++i, item.getItemistFreeze());
                    ps.setInt(++i, item.getItemistBlind());
                    ps.setInt(++i, item.getItemArmorType());
                    ps.setInt(++i, item.getItemArmorLv());
                    ps.setInt(++i, item.getskilltype());
                    ps.setInt(++i, item.getskilltypelv());
                    ps.setInt(++i, item.getItemType());
                    ps.setInt(++i, item.getItemHpr());
                    ps.setInt(++i, item.getItemMpr());
                    ps.setInt(++i, item.getItemhppotion());
                    ps.setInt(++i, item.getGamNo());
                    ps.setInt(++i, item.getGamNpcId());
                    ps.setString(++i, item.getStarNpcId());
                    ps.setString(++i, item.getraceGamNo());
                    ps.execute();
                }
                final ClientExecutor cl = OnlineUser.get().get(account);
                if (cl != null) {
                    final L1PcInstance tgpc = cl.getActiveChar();
                    if (tgpc == null) {
                        continue;
                    }
                    tgpc.getDwarfInventory().loadItems();
                }
            }
            con.commit();
            con.setAutoCommit(true);
        }
        catch (SQLException e) {
            try {
                con.rollback();
            }
            catch (SQLException ex) {}
            _log.error(e.getLocalizedMessage(), e);
            throw new Exception(".present在處理中發生錯誤。");
        }
        finally {
            SQLUtil.close(ps);
            SQLUtil.close(con);
        }
    }
}
