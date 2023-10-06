// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   S_ScrollShopSellList.java

package com.lineage.server.serverpackets;

import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.Instance.*;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1TaxCalculator;
import com.lineage.server.model.shop.L1Shop;
import com.lineage.server.templates.*;
import com.lineage.server.utils.BinaryOutputStream;
import com.lineage.server.world.World;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * 隨身NPC物品販賣列表
 * @author dexc
 *
 */
public class S_ScrollShopSellList extends ServerBasePacket
{

	public S_ScrollShopSellList(L1Npc npc)
	{
		writeC(S_OPCODE_SHOWSHOPBUYLIST);
		
		int objId = World.get().getObjId(npc);
		L1Object npcObj = World.get().findObject(objId);
		writeD(objId);
		int npcId = ((L1NpcInstance)npcObj).getNpcTemplate().get_npcId();
		//L1TaxCalculator calc = new L1TaxCalculator(npcId);
		L1Shop shop = ShopTable.get().get(npcId);
		final List<L1ShopItem> shopItems = shop.getSellingItems();
		writeH(shopItems.size());
		L1ItemInstance dummy = new L1ItemInstance();
		for (int i = 0; i < shopItems.size(); i++)
		{
			L1ShopItem shopItem = (L1ShopItem)shopItems.get(i);
			L1Item item = shopItem.getItem();
			//int price = calc.layTax(shopItem.getPrice());
			final int price = (int) (shopItem.getPrice());
			writeD(i);
			writeH(shopItem.getItem().getGfxId());
			writeD(price);
			if (shopItem.getPackCount() > 1)
				writeS((new StringBuilder(String.valueOf(item.getName()))).append(" (").append(shopItem.getPackCount()).append(")").toString());
			else
				writeS(item.getName());
			L1Item template = ItemTable.get().getTemplate(item.getItemId());
			if (template == null)
			{
				writeC(0);
			} else
			{
				L1ItemStatus itemInfo = new L1ItemStatus(item);
				byte status[] = itemInfo.getStatusBytes(true).getBytes();
				writeC(status.length);
				byte abyte0[];
				int k = (abyte0 = status).length;
				for (int j = 0; j < k; j++)
				{
					byte b = abyte0[j];
					writeC(b);
				}

			}
		}

		this.writeH(0x0007); // 0x0000:無顯示 0x0001:珍珠 0x0007:金幣 0x17d4:天寶
	}

	public byte[] getContent()
		throws IOException
	{
		return _bao.toByteArray();
	}
}
