package com.lineage.server.clientpackets;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1DwarfInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.world.World;

/**
 * 要求開設個人商店
 *
 * @author daien
 *
 */
public class C_Shop extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Shop.class);

	/*public C_Shop() {
	}

	public C_Shop(final byte[] abyte0, final ClientExecutor client) {
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
      

      /*int mapId = pc.getMapId();
      // if ((mapId != 340) && (mapId != 350) && (mapId != 360) && (mapId != 370) && (mapId != 800))
      if (mapId != 800)
      {
        pc.sendPackets(new S_ServerMessage(876));
        return;
      }*/
	  if ((pc.getMap().isUsableShop() <= 0) || 
			        (ItemTable.get().getTemplate(pc.getMap().isUsableShop()) == null))
			      {
			       pc.sendPackets(new S_ServerMessage(876));
			        return;
			      }
			     
			      for (L1Object object : World.get().getVisibleObjects(pc, 3)) {
			       if ((object instanceof L1PcInstance)) {
			         if (((L1PcInstance)object).isPrivateShop()) {
			           pc.sendPackets(new S_SystemMessage(
			             "無法在此開啟商店，附近已有其他個人商店。"));
			         }
			        }        else if ((object instanceof L1DwarfInstance)) {
			        	         pc.sendPackets(new S_SystemMessage("無法在倉庫附近開啟個人商店。"));
			                 return;
			              }
			      }

      final ArrayList<L1PrivateShopSellList> sellList = pc.getSellList();
      final ArrayList<L1PrivateShopBuyList> buyList = pc.getBuyList();

      boolean tradable = true;

      int type = readC();
      if (type == 0) {
        int sellTotalCount = readH();

        for (int i = 0; i < sellTotalCount; i++) {
          int sellObjectId = readD();
          int sellPrice = Math.max(0, readD());
          if (sellPrice <= 0) {
            _log.error("要求開設個人商店傳回金幣小於等於0: " + pc.getName());
            break;
          }
          int sellCount = Math.max(0, readD());
          if (sellCount <= 0) {
            _log.error("要求開設個人商店傳回數量小於等於0: " + pc.getName());
            break;
          }

          L1ItemInstance checkItem = pc.getInventory().getItem(sellObjectId);
          if (!checkItem.getItem().isTradable()) {
            tradable = false;

            pc.sendPackets(new S_ServerMessage(1497));
          }

          if (checkItem.get_time() != null)
          {
            pc.sendPackets(new S_ServerMessage(1497));
            tradable = false;
          }
          
          if (checkItem.getBless() >= 128)
          {
            pc.sendPackets(new S_ServerMessage(1497));
            return;
          }

          if (checkItem.isEquipped())
          {
            pc.sendPackets(new S_ServerMessage(141));
            return;
          }

          if (ItemRestrictionsTable.RESTRICTIONS.contains(Integer.valueOf(checkItem.getItemId())))
          {
            pc.sendPackets(new S_ServerMessage(210, checkItem.getItem().getNameId()));
            return;
          }

          Object[] petlist = pc.getPetList().values().toArray();
          for (Object petObject : petlist) {
            if ((petObject instanceof L1PetInstance)) {
            	 L1PetInstance pet = (L1PetInstance)petObject;
              if (checkItem.getId() == pet.getItemObjId()) {
                tradable = false;

                pc.sendPackets(new S_ServerMessage(1187));
                return;
              }
            }
          }
          
		  if (checkItem.getId() == pc.getQuest().get_step(L1PcQuest.QUEST_MARRY)) { // 以結婚過的戒指(無法擺商店)
			  pc.sendPackets(new S_ServerMessage(166, checkItem.getName(), " 無法交易"));
			  return;
		  }

          if (pc.getDoll(checkItem.getId()) != null)
          {
            pc.sendPackets(new S_ServerMessage(1181));
            return;
          }
          
          if (checkItem.getItem().cantBeSold()) {
        	pc.sendPackets(new S_ServerMessage(166, checkItem.getName(), " 無法販售"));
      		return;
      	  }
          
			// 取回娃娃
			if (pc.get_power_doll() != null) {
				if (pc.get_power_doll().getItemObjId() == checkItem
						.getId()) {
					// 1,181：這個魔法娃娃目前正在使用中。
					pc.sendPackets(new S_ServerMessage(1181));
					return;
				}
			}
          L1PrivateShopSellList pssl = new L1PrivateShopSellList();
          pssl.setItemObjectId(sellObjectId);
          pssl.setSellPrice(sellPrice);
          pssl.setSellTotalCount(sellCount);
          sellList.add(pssl);
        }

        int buyTotalCount = readH();
        for (int i = 0; i < buyTotalCount; i++) {
          int buyObjectId = readD();
          int buyPrice = Math.max(0, readD());
          if (buyPrice <= 0) {
            _log.error("要求買入道具傳回金幣小於等於0: " + pc.getName());
            break;
          }
          int buyCount = Math.max(0, readD());
          if (buyCount <= 0) {
            _log.error("要求買入道具傳回數量小於等於0: " + pc.getName());
            break;
          }

          L1ItemInstance checkItem = pc.getInventory().getItem(buyObjectId);

          if (checkItem.getCount() > 0L)
          {
            if (!checkItem.getItem().isTradable()) {
              tradable = false;

              pc.sendPackets(new S_ServerMessage(1497));
            }
            
            if (checkItem.get_time() != null)
            {
              pc.sendPackets(new S_ServerMessage(1497));
              tradable = false;
            }

            if (checkItem.getBless() >= 128)
            {
              pc.sendPackets(new S_ServerMessage(1497));
              return;
            }

            if (checkItem.isEquipped())
            {
              pc.sendPackets(new S_ServerMessage(141));
              return;
            }
            
            if (ItemRestrictionsTable.RESTRICTIONS.contains(Integer.valueOf(checkItem.getItemId())))
            {
              pc.sendPackets(new S_ServerMessage(210, checkItem.getItem().getNameId()));
              return;
            }

            Object[] petlist = pc.getPetList().values().toArray();
            for (Object petObject : petlist) {
              if ((petObject instanceof L1PetInstance)) {
                L1PetInstance pet = (L1PetInstance)petObject;
                if (checkItem.getId() == pet.getItemObjId()) {
                  tradable = false;

                  pc.sendPackets(new S_ServerMessage(1187));
                  return;
                }
              }
            }
            
			if (checkItem.getId() == pc.getQuest().get_step(L1PcQuest.QUEST_MARRY)) { // 以結婚過的戒指(無法擺商店)
				pc.sendPackets(new S_ServerMessage(166, checkItem.getName(), " 無法交易"));
				return;
			}

            if (pc.getDoll(checkItem.getId()) != null)
            {
              pc.sendPackets(new S_ServerMessage(1181));
              return;
            }

			L1PrivateShopBuyList psbl = new L1PrivateShopBuyList();
			psbl.setItemObjectId(buyObjectId);
			psbl.setBuyPrice(buyPrice);
			psbl.setBuyTotalCount(buyCount);
			buyList.add(psbl);
          }
        }
        if (!tradable) {
          sellList.clear();
          buyList.clear();
          pc.setPrivateShop(false);
          pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 3));
          return;
        }

        byte[] chat = readByte();
        pc.setShopChat(chat);
        pc.setPrivateShop(true);
		/**3.80 更動(個人商店變身) */
		int SelectedPolyNum = 0;
		try {
			SelectedPolyNum = Integer.parseInt(new String(chat, "big5").split("tradezone")[1].substring(0, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		L1PolyMorph.doPolyPraivateShop(pc, SelectedPolyNum);
		pc.sendPacketsAll(new S_DoActionShop(pc.getId(), chat));//擺攤動作
		
		
      } else if (type == 1) {//取消擺攤
        sellList.clear();
        buyList.clear();
        pc.setPrivateShop(false);
        pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), 3));
        /**3.80 更動(個人商店取消變身) */
		L1PolyMorph.undoPolyPrivateShop(pc);
      }
    }
    catch (Exception localException)
    {
    }
    finally {
      over();
    }
  }

  public String getType()
  {
    return getClass().getSimpleName();
  }
}

