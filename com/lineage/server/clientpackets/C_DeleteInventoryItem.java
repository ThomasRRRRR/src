package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_DeleteInventoryItem extends ClientBasePacket
{
  private static final Log _log = LogFactory.getLog(C_DeleteInventoryItem.class);

  public void start(byte[] decrypt, ClientExecutor client) {
    try { 
        read(decrypt);

        L1PcInstance pc = client.getActiveChar();

        int itemObjectId = readD();
        
        L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
        
  	  // 物品為空
        if (item == null) {
  		return;
        }
        if (item.getCount() <= 0) {
  		return;
        }
        
        int count = readD();//要求刪除的數量
        
        if (count == 0) {
  		count = (int) item.getCount();
        }

        // 執行人物不是GM
        if (!pc.isGm()) {
        	if (item.getItem().isCantDelete()) {
        		// 125 \f1你不能夠放棄此樣物品。
        		pc.sendPackets(new S_ServerMessage(125));
        		return;
        	}
        }
        Object[] petlist = pc.getPetList().values().toArray();
        for (Object petObject : petlist) {
          if ((petObject instanceof L1PetInstance)) {
            L1PetInstance pet = (L1PetInstance)petObject;
            if (item.getId() == pet.getItemObjId())
            {
              pc.sendPackets(new S_ServerMessage(125));
              return;
            }
          }
        }
		/** [原碼] 結婚系統 */
		if (item.getId() == pc.getQuest().get_step(L1PcQuest.QUEST_MARRY)) { // 以結婚過的戒指(無法刪除)
			// \f1你不能夠放棄此樣物品。
			pc.sendPackets(new S_ServerMessage(125));
			return;
		}
		/** End */
		// 取回娃娃
				if (pc.getDoll(item.getId()) != null) {
					// 1,181：這個魔法娃娃目前正在使用中。  
					pc.sendPackets(new S_ServerMessage(1181));
					return;
				}
				// 取回娃娃
				if (pc.get_power_doll() != null) {
					if (pc.get_power_doll().getItemObjId() == item.getId()) {
						// 1,181：這個魔法娃娃目前正在使用中。
						pc.sendPackets(new S_ServerMessage(1181));
						return;
					}
				}
				if (item.isEquipped()) {
					// 125 \f1你不能夠放棄此樣物品。
					pc.sendPackets(new S_ServerMessage(125));
					return;
				}
				
				if (item.getBless() >= 128) { // 封印的装備
					// 210 \f1%0%d是不可轉移的…
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
					return;
				}
//			_log.info("人物:" + pc.getName() + "刪除物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
            RecordTable.get().recordDeleItem(pc.getName(), item.getAllName(), (int)item.getCount(), item.getId(), pc.getIp());
            pc.getInventory().removeItem(item, count);
            pc.turnOnOffLight();
            World.get().removeObject(item);
    } catch (final Exception e) {
		//_log.error(e.getLocalizedMessage(), e);
		
	} finally {
		this.over();
	}
  }
    
  public String getType()
  {
    return getClass().getSimpleName();
  }
}