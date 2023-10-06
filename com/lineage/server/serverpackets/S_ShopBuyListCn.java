package com.lineage.server.serverpackets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lineage.server.datatables.ItemPowerUpdateTable;
import com.lineage.server.datatables.ShopCnTable;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.shop.L1Shop;

/**
 * 商城道具回收
 * @author server
 *
 */
public class S_ShopBuyListCn extends ServerBasePacket {
	private byte[] _byte = null;

	public S_ShopBuyListCn(L1PcInstance pc, L1NpcInstance npc) {
		Map<L1ItemInstance, Integer> assessedItems = assessItems(pc.getInventory());

		if (assessedItems.isEmpty()) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		if (assessedItems.size() <= 0) {
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		writeC(S_OPCODE_SHOWSHOPSELLLIST);
		writeD(npc.getId());

		writeH(assessedItems.size());

		for (L1ItemInstance key : assessedItems.keySet()) {
			writeD(key.getId());
			writeD(((Integer) assessedItems.get(key)).intValue());
		}
		//writeH(0x0007); // 0x0000:無顯示 0x0001:珍珠 0x0007:金幣 0x17d4:商城幣
		writeH(0x17d4); // 0x0000:無顯示 0x0001:珍珠 0x0007:金幣 0x17d4:商城幣
	}

	private Map<L1ItemInstance, Integer> assessItems(L1PcInventory inv) {
		Map<L1ItemInstance, Integer> result = new HashMap<L1ItemInstance, Integer>();
		for (L1ItemInstance item : inv.getItems()) {
			switch (item.getItem().getItemId()) {
			case 40308: // 金幣
			case 41246: // 魔法結晶體
			case 44070: // 商城幣
			case 40314: // 項圈
			case 40316: // 高等寵物項圈
			case 83000: // 貝利
			case 83022: // 黃金貝利
				// case 80033: // 推廣銀幣
			case 80028: // 火神契約
				break;
			default:
				if (!item.isEquipped()) {// 未裝備
					int itemid = item.getItem().getItemId();
					ArrayList<Integer> cnlist = ShopCnTable.get().get_cnitemidlist();// 商城販賣物品列表
					ArrayList<Integer> uplist = ItemPowerUpdateTable.get().get_updeatitemidlist();// 升級物品列表

					ArrayList<Integer> allcnlist = new ArrayList<Integer>();
					allcnlist.addAll(cnlist);
					allcnlist.addAll(uplist);

					if (!allcnlist.contains(itemid)) {// 如果不在所有商城物品列表中則略過
						continue;
					}

					boolean contains = ShopCnTable.get().isSelling(110811, itemid);// 推廣銀幣商人
					if (contains) {// 在推廣銀幣商人販賣物品中則略過
						continue;
					}

					L1Shop shop = ShopTable.get().get(110641);// 貝利商人
					if (shop != null) {
						if (shop.isSelling(itemid)) {// 如果在貝利販賣物品中則略過
							continue;
						}
						if (shop.isPurchasing(itemid)) {// 如果在貝利收購物品中則略過
							continue;
						}
					}

					L1Shop shop2 = ShopTable.get().get(200206);// 湖中女神妲蒂斯
					if (shop2 != null) {
						if (shop2.isSelling(itemid)) {// 如果在湖中女神妲蒂斯販賣物品中則略過
							continue;
						}
						if (shop2.isPurchasing(itemid)) {// 如果在湖中女神妲蒂斯收購物品中則略過
							continue;
						}
					}

					if (item.getBless() >= 128) {// 封印狀態略過
						continue;
					}

					if (item.get_time() != null) {// 具有期限道具略過
						continue;
					}
					if (item.getItem().cantBeSold()) {
						continue;
					}
					int type_id = ItemPowerUpdateTable.get().get_original_type(itemid);
					int original_itemid = ItemPowerUpdateTable.get().get_original_itemid(type_id);// 未升級前的原始物品

					int price = 0;
					if (uplist.contains(itemid)) {// 如果在升級物品列表中
						price = ShopCnTable.get().getPrice(original_itemid);// 取得原始物品價錢
					} else {
						price = ShopCnTable.get().getPrice(itemid);// 取回回收單價
						// System.out.println("itemid ==" + itemid + "price =="
						// + price);
					}

					if (price > 0) {
						result.put(item, Integer.valueOf(price));
					}
				}
				break;
			}
		}
		return result;
	}

	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return getClass().getSimpleName();
	}
}

