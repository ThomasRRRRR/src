package com.lineage.data.npc.shop;

import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.data.cmd.CreateNewItem;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.serverpackets.S_CnSRetrieve;
import com.lineage.server.datatables.ItemRestrictionsTable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import com.lineage.server.datatables.ShopXTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.templates.L1ShopS;
import java.util.Map;
import com.lineage.server.datatables.lock.DwarfShopReading;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_CnSShopSellListarmor;
import com.lineage.server.serverpackets.S_CnSShopSellList;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.data.event.ShopXSet;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

/**
 * 託售管理員<BR>
 * 70535
 * @author dexc
 *
 */
public class Npc_ShopX extends NpcExecutor {

	private static final Log _log = LogFactory.getLog(Npc_ShopX.class);

    private static final int _count = 200;
    
    public static int _itemid;
   
	/**
	 *
	 */
	private Npc_ShopX() {
		// TODO Auto-generated constructor stub
	}
    
    public static NpcExecutor get() {
        return new Npc_ShopX();
    }
    
    @Override
    public int type() {
        return 3;
    }
    
	@Override
	public void talk(final L1PcInstance pc, final L1NpcInstance npc) {

		try {
			
			pc.get_other().set_item(null);
			
			String[] info = new String[]{String.valueOf(ShopXSet.ADENA), 
					String.valueOf(ShopXSet.DATE), 
					String.valueOf(ShopXSet.MIN), 
					String.valueOf(ShopXSet.MAX)
					};
			
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_1", info));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {

        if (cmd.equalsIgnoreCase("s")) {
            cmd_s2(pc, npc);
        }
        else if (cmd.equalsIgnoreCase("shopweapon")) {
            pc.sendPackets(new S_CnSShopSellList(pc, npc));
        }
        else if (cmd.equalsIgnoreCase("shoparmor2")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 2));
        }
        else if (cmd.equalsIgnoreCase("shoparmor18")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 18));
        }
        else if (cmd.equalsIgnoreCase("shoparmor19")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 19));
        }
        else if (cmd.equalsIgnoreCase("shoparmor20")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 20));
        }
        else if (cmd.equalsIgnoreCase("shoparmor21")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 21));
        }
        else if (cmd.equalsIgnoreCase("shoparmor22")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 22));
        }
        else if (cmd.equalsIgnoreCase("shoparmor23")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 23));
        }
        else if (cmd.equalsIgnoreCase("shoparmor24")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 24));
        }
        else if (cmd.equalsIgnoreCase("shoparmor25")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 25));
        }
        else if (cmd.equalsIgnoreCase("shoparmor40")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 40));
        }
        else if (cmd.equalsIgnoreCase("shoparmor37")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 37));
        }
        else if (cmd.equalsIgnoreCase("shoparmorother")) {
            pc.sendPackets(new S_CnSShopSellListarmor(pc, npc, 50));
        }
        else if (cmd.equalsIgnoreCase("l")) {
            cmd_1(pc, npc);
        }
        else if (cmd.equalsIgnoreCase("ma")) {
            cmd_ma(pc, npc, amount);
        }
        else if (cmd.equals("up")) {
            final int page = pc.get_other().get_page() - 1;
            showPage(pc, npc.getId(), page);
        }
        else if (cmd.equals("dn")) {
            final int page = pc.get_other().get_page() + 1;
            showPage(pc, npc.getId(), page);
        }
        else if (cmd.equals("over")) {
            cmd_over(pc, npc);
        }
        else if (cmd.equals("no")) {
            pc.setTempID(0);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
        else if (cmd.matches("[0-9]+")) {
            final String pagecmd = String.valueOf(String.valueOf(pc.get_other().get_page())) + cmd;
            update(pc, npc, Integer.parseInt(pagecmd));
        }
    }
    
	/**
	 * 我的出售紀錄
	 * @param pc
	 * @param npc
	 */
	public static void cmd_1(L1PcInstance pc, L1NpcInstance npc) {
		try {
			pc.get_otherList().SHOPXMAP.clear();
			Map<Integer, L1ShopS> temp = 
				DwarfShopReading.get().getShopSMap(pc.getId());

			if (temp != null) {
				pc.get_otherList().SHOPXMAP.putAll(temp);
				temp.clear();
			}
			showPage(pc, npc.getId(), 0);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
    public static void cmd_ma(final L1PcInstance pc, final L1NpcInstance npc, final long amount) {
        try {
            final L1ItemInstance itemT = pc.getInventory().checkItemX(Npc_ShopX._itemid, ShopXSet.ADENA);
            boolean isError = false;
            if (itemT == null) {
                pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                isError = true;
            }
            if (amount < ShopXSet.MIN) {
                isError = true;
            }
            if (amount > ShopXSet.MAX) {
                isError = true;
            }
            final L1ItemInstance item = pc.get_other().get_item();
            if (item == null) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            if (!item.getItem().isTradable()) {// 不可轉移
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
            if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                isError = true;
            }
			// 寵物
			final Object[] petlist = pc.getPetList().values().toArray();
			for (final Object petObject : petlist) {
				if (petObject instanceof L1PetInstance) {
					final L1PetInstance pet = (L1PetInstance) petObject;
					if (item.getId() == pet.getItemObjId()) {
						isError = true;
					}
				}
			}
			// 使用中的娃娃
			if (pc.getDoll(item.getId()) != null) {
				isError = true;
			}
            if (item.getEnchantLevel() < 0) {
                isError = true;
            }
			if (item.getraceGamNo() != null) {// 賭狗票
				isError = true;
			}
            if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                isError = true;
            }
            if (isError) {
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.get_other().set_item(null);
                return;
            }
            pc.get_other().set_item(null);
            final long time = ShopXSet.DATE * 24 * 60 * 60 * 1000;
            final Timestamp overTime = new Timestamp(System.currentTimeMillis() + time);
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final String key = sdf.format(overTime);
            final String[] info = { item.getLogName(), String.valueOf(amount), key };
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_4", info));
            final L1ShopS shopS = new L1ShopS();
            shopS.set_id(DwarfShopReading.get().nextId());
            shopS.set_item_obj_id(item.getId());
            shopS.set_user_obj_id(pc.getId());
            shopS.set_adena((int)amount);
            shopS.set_overtime(overTime);
            shopS.set_end(0);
            final String outname = item.getNumberedName_to_String();
            shopS.set_none(outname);
            shopS.set_item(item);
            pc.getInventory().removeItem(itemT, ShopXSet.ADENA);
            pc.getInventory().removeItem(item);
            DwarfShopReading.get().insertItem(item.getId(), item, shopS);
            try {
                pc.save();
                pc.saveInventory();
            }
            catch (Exception e) {
                Npc_ShopX._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            }
        }
        catch (Exception e2) {
            Npc_ShopX._log.error((Object)e2.getLocalizedMessage(), (Throwable)e2);
        }
    }
    
	/**
	 * 我要取消託售
	 * @param pc
	 * @param npc
	 */
	public static void cmd_over(L1PcInstance pc, L1NpcInstance npc) {
		try {
			final L1ShopS shopS = 
				DwarfShopReading.get().getShopS(pc.getTempID());
			pc.setTempID(0);
			shopS.set_end(3);
			shopS.set_item(null);
			DwarfShopReading.get().updateShopS(shopS);
			// 關閉對話窗
			pc.sendPackets(new S_CloseList(pc.getId()));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
    private static void cmd_s1(final L1PcInstance pc, final L1NpcInstance npc) {
        try {
            final Map<Integer, L1ShopS> allShopS = DwarfShopReading.get().allShopS();
            if (allShopS.size() >= _count) {
                pc.sendPackets(new S_ServerMessage(75));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final L1ItemInstance itemT = pc.getInventory().checkItemX(Npc_ShopX._itemid, ShopXSet.ADENA);
            if (itemT == null) {
                pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final List<L1ItemInstance> itemsx = new CopyOnWriteArrayList<L1ItemInstance>();
            final List<L1ItemInstance> items = pc.getInventory().getItems();
            for (final L1ItemInstance item : items) {
                if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                    continue;
                }
				// 使用中的娃娃
				if (pc.getDoll(item.getId()) != null) {
					continue;
				}
				// 寵物
				final Object[] petlist = pc.getPetList().values().toArray();
				for (final Object petObject : petlist) {
					if (petObject instanceof L1PetInstance) {
						final L1PetInstance pet = (L1PetInstance) petObject;
						if (item.getId() == pet.getItemObjId()) {
							continue;
						}
					}
				}
				if (!item.getItem().isTradable()) {// 不可轉移
					continue;
				}
                if (item.isEquipped()) {
                    continue;
                }
                if (!item.isIdentified()) {
                    continue;
                }
                if (item.getItem().getMaxUseTime() != 0) {
                    continue;
                }
                if (item.get_time() != null) {
                    continue;
                }
                if (item.getEnchantLevel() < 0) {
                    continue;
                }
                if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                    continue;
                }
                if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                    continue;
                }
                itemsx.add(item);
            }
            pc.sendPackets(new S_CnSRetrieve(pc, npc.getId(), itemsx));
            allShopS.clear();
        }
        catch (Exception e) {
            Npc_ShopX._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
	/**
	 * 我要進行道具出售
	 * @param pc
	 * @param npc
	 */
    public static void cmd_s2(final L1PcInstance pc, final L1NpcInstance npc) {
        try {
            final Map<Integer, L1ShopS> allShopS = DwarfShopReading.get().allShopS();
            if (allShopS.size() >= _count) {
                pc.sendPackets(new S_ServerMessage(75));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final L1ItemInstance itemT = pc.getInventory().checkItemX(Npc_ShopX._itemid, ShopXSet.ADENA);
            if (itemT == null) {
                pc.sendPackets(new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
                pc.sendPackets(new S_CloseList(pc.getId()));
                return;
            }
            final List<L1ItemInstance> itemsx = new CopyOnWriteArrayList<L1ItemInstance>();
            final List<L1ItemInstance> items = pc.getInventory().getItems();
            for (final L1ItemInstance item : items) {
                if (ShopXTable.get().getTemplate(item.getItem().getItemId()) != null) {
                    continue;
                }
				// 使用中的娃娃
				if (pc.getDoll(item.getId()) != null) {
					continue;
				}
				// 寵物
				final Object[] petlist = pc.getPetList().values().toArray();
				for (final Object petObject : petlist) {
					if (petObject instanceof L1PetInstance) {
						final L1PetInstance pet = (L1PetInstance) petObject;
						if (item.getId() == pet.getItemObjId()) {
							continue;
						}
					}
				}
				if (!item.getItem().isTradable()) {// 不可轉移
					continue;
				}
                if (item.isEquipped()) {
                    continue;
                }
                if (!item.isIdentified()) {
                    continue;
                }
                if (item.getItem().getMaxUseTime() != 0) {
                    continue;
                }
                if (item.get_time() != null) {
                    continue;
                }
				if (item.getraceGamNo() != null) {// 賭票
					continue;
				}
                if (item.getEnchantLevel() < 0) {
                    continue;
                }
                if (item.getItem().getMaxChargeCount() != 0 && item.getChargeCount() <= 0) {
                    continue;
                }
                if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
                    continue;
                }
                itemsx.add(item);
            }
            pc.sendPackets(new S_CnSRetrieve(pc, npc.getId(), itemsx));
            allShopS.clear();
        }
        catch (Exception e) {
            Npc_ShopX._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    /**
	 * 取回物品銷售結果
	 * @param pc 人物
	 * @param npc NPC
	 * @param key 排序
	 */
	public static void update(L1PcInstance pc, L1NpcInstance npc, int key) {
		Map<Integer, L1ShopS> list = 
				pc.get_otherList().SHOPXMAP;

		pc.setTempID(0);
		final L1ShopS shopS = list.get(key);
		switch (shopS.get_end()) {
		case 0:// 出售中
			pc.setTempID(shopS.get_item_obj_id());
			final SimpleDateFormat sdf = 
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final String overTime = sdf.format(shopS.get_overtime());// 到期時間

			final String[] info = 
				new String[]{shopS.get_item().getLogName() + "(" + shopS.get_item().getCount() + ")", 
					String.valueOf(shopS.get_adena()), 
					overTime };
			pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "y_x_5", info));
			break;
			
			case 1:// 已售出未領回
				shopS.set_end(2);
				DwarfShopReading.get().updateShopS(shopS);
				CreateNewItem.createNewItem(pc, Npc_ShopX._itemid, shopS.get_adena());
			
				// 關閉對話窗
				pc.sendPackets(new S_CloseList(pc.getId()));
				break;
				
			case 2:// 已售出已領回
				// 6158 該託售物品收入已領回
				pc.sendPackets(new S_ServerMessage(166, "該託售物品收入已領回"));
				
				// 關閉對話窗
				pc.sendPackets(new S_CloseList(pc.getId()));
				break;
				
			case 3:// 未售出未領回
				shopS.set_end(4);
				shopS.set_item(null);
				DwarfShopReading.get().updateShopS(shopS);
				L1ItemInstance item = 
					DwarfShopReading.get().allItems().get(shopS.get_item_obj_id());
				DwarfShopReading.get().deleteItem(shopS.get_item_obj_id());
				pc.getInventory().storeTradeItem(item);
				pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // 获得0%。
				
				// 關閉對話窗
				pc.sendPackets(new S_CloseList(pc.getId()));
				break;
				
			case 4:// 未售出已領回
				// 6159 該託售物品已領回
				pc.sendPackets(new S_ServerMessage(166, "該託售物品已領回"));
				
				// 關閉對話窗
				pc.sendPackets(new S_CloseList(pc.getId()));
				break;
			}
        CharItemPowerReading.get().load();
        pc.get_other().set_item(null);
    }
    
	/**
	 * 展示出售紀錄
	 * @param pc
	 * @param npcobjid
	 * @param page
	 */
	public static void showPage(L1PcInstance pc, int npcobjid, int page) {
		Map<Integer, L1ShopS> list = 
			pc.get_otherList().SHOPXMAP;
		if (list == null) {
			return;
		}
		
        int allpage = list.size() / 10;
        if (page > allpage || page < 0) {
            page = 0;
        }
        
		if (list.size() % 10 != 0) {
			allpage += 1;
		}
		
		pc.get_other().set_page(page);// 設置頁面

		final int or = page * 10;
		//System.out.println("OR:"+or);

		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append((page + 1) + "/" + allpage + ",");
		
		// 每頁顯示10筆(showId + 10)資料
		for (int key = or ; key < or + 10 ; key++) {
            final L1ShopS shopS = list.get(key);
            if (shopS != null) {
                stringBuilder.append(String.valueOf(String.valueOf(shopS.get_none())) + " / " + shopS.get_adena() + ",");
				switch (shopS.get_end()) {
				case 0:// 出售中
					stringBuilder.append("出售中,");
					break;
					
				case 1:// 已售出未領回
					stringBuilder.append("已售出未領回,");
					break;
					
				case 2:// 已售出已領回
					stringBuilder.append("已售出已領回,");
					break;
					
				case 3:// 未售出未領回
					stringBuilder.append("未售出未領回,");
					break;
					
				case 4:// 未售出已領回
					stringBuilder.append("未售出已領回,");
					break;
				}
            }
            else {
                stringBuilder.append(" ,");
            }
        }
        if (allpage >= page + 1) {
            final String out = stringBuilder.toString();
            final String[] clientStrAry = out.split(",");
            pc.sendPackets(new S_NPCTalkReturn(npcobjid, "y_x_2", clientStrAry));
        }
        else {
        	// $6157 沒有可以顯示的項目
        	pc.sendPackets(new S_ServerMessage(166, "沒有可以顯示的項目"));
        }
    }
    
    @Override
    public void set_set(final String[] set) {
        try {
            Npc_ShopX._itemid = Integer.parseInt(set[1]);
        }
        catch (Exception e) {
        	Npc_ShopX._log.error((Object)"NPC專屬貨幣設置錯誤:檢查CLASSNAME為Npc_Strange的NPC設置!");
        }
    }
}
