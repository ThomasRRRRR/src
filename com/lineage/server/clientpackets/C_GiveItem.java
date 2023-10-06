// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.clientpackets;

import java.util.Random;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.templates.L1Npc;
import java.util.Iterator;
import com.lineage.server.templates.L1ServerItemGive;
import com.lineage.server.templates.L1PetType;
import com.lineage.server.templates.L1PetItem;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1Inventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.datatables.ServerItemGiveTable;
import com.lineage.server.datatables.PetTypeTable;
import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_GiveItem extends ClientBasePacket {
	@SuppressWarnings("unused")
	private static final Log _log = LogFactory.getLog(C_GiveItem.class);

	private static final String[] receivableImpls = { "L1Npc", "L1Monster", "L1Guardian", "L1Teleporter", "L1Guard" };

	public void start(byte[] decrypt, ClientExecutor client) {
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

						if (pc.isPrivateShop()) { // 商店村模式
							return;
						}

						int targetId = readD();
						int x = readH();
						int y = readH();

						int itemObjId = readD();

						long count = readD();
						if (count > 2147483647L) {
							count = 2147483647L;
						}
						count = Math.max(0L, count);
						L1Object object = World.get().findObject(targetId);
						if ((object == null) || (!(object instanceof L1NpcInstance))) {
							return;
						}

						L1NpcInstance target = (L1NpcInstance) object;
						if (!isNpcItemReceivable(target.getNpcTemplate())) {
							return;
						}

						L1Inventory targetInv = target.getInventory();

						L1Inventory inv = pc.getInventory();
						L1ItemInstance item = inv.getItem(itemObjId);

						if (item == null) {
							return;
						}
						if (item.getCount() <= 0L) {
							return;
						}
						if (item.isEquipped()) {
							pc.sendPackets(new S_ServerMessage(141));
							return;
						}

						if (!item.getItem().isTradable()) {
							pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
							return;
						}
						if (item.getBless() >= 128) {
							pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
							return;
						}

						if (item.get_time() != null) {
							pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
							return;
						}
						if (ItemRestrictionsTable.RESTRICTIONS.contains(Integer.valueOf(item.getItemId()))) {
							pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
							return;
						}
            final L1PetItem petItem = PetItemTable.get().getTemplate(item.getItemId());
            final L1PetType petType = PetTypeTable.getInstance().get(target.getNpcTemplate().get_npcId());
            if (item.getItem().getUseType() == -12 && petType.getPetArmor() == 0) {
                pc.sendPackets(new S_ServerMessage(166, "\u8a72\u5bf5\u7269\u7121\u6cd5\u7a7f\u8457\u8a72\u88dd\u5099"));
                return;
            }
            if (!pc.isGm()) {
     	       L1ServerItemGive itemGive = ServerItemGiveTable.get().getTemplate(item.getItemId());
     	      if (itemGive == null)
     	      {
     	        pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
     	        return;
     	      }
     	     if (!itemGive.ispet()) {
     	        if ((object instanceof L1PetInstance))
     	          pc.sendPackets(new S_ServerMessage(166, "只能給予寵物"));
     	      }
                else {
        	        int maxCount = itemGive.get_max();
        	        if ((maxCount > 0) && 
        	          (count > maxCount)) {
        	          pc.sendPackets(new S_ServerMessage(166, item.getName() + "最多只能給予" + maxCount + "個"));
        	          return;
        	        }
                }
            }
            int pcx = pc.getX();
			int pcy = pc.getY();
			if ((Math.abs(pcx - x) >= 3) || (Math.abs(pcy - y) >= 3)) {
				pc.sendPackets(new S_ServerMessage(142));
				return;
			}

			// 寵物(已經召喚出來)
			for (final Object petObject : pc.getPetList().values()) {
				if (petObject instanceof L1PetInstance) {
					final L1PetInstance pet = (L1PetInstance) petObject;
					if (item.getId() == pet.getItemObjId()) {
						// \f1%0%d是不可轉移的…
						pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
						return;
					}
				}
			}
			/** [原碼] 結婚系統 */
			if (item.getId() == pc.getQuest().get_step(L1PcQuest.QUEST_MARRY)) { // 以結婚過的戒指(無法給予)
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getName()));
				return;
			}
			/** End */

			if (pc.getDoll(item.getId()) != null) {
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
			if (targetInv.checkAddItem(item, count) != 0) {
				pc.sendPackets(new S_ServerMessage(942));
				return;
			}
            RecordTable.get().recordgivenpc(pc.getName(), item.getAllName(), (int)count, item.getId(), pc.getIp());
            // 給予的物件
         	final L1ItemInstance getItem = inv.tradeItem(item, count, targetInv);
         	target.onGetItem(getItem);
         	target.turnOnOffLight();
         	pc.turnOnOffLight();
			if ((petType == null) || (target.isDead())) {
				return;
			}

			if (getItem.getItemId() == petType.getItemIdForTaming()) {
				tamePet(pc, target);
			}

			// 給予的對象 是寵物
			if (target instanceof L1PetInstance) {
				final L1PetInstance tgPet = (L1PetInstance) target;
				// pc.sendPackets(new S_PetInventory(tgPet, true));
				// 給予的道具是進化道具
				if ((getItem.getItemId() == petType.getEvolvItemId()) && petType.canEvolve()) {
					this.evolvePet(pc, tgPet);
				}
                if (petItem != null) {
                    if (petItem.isWeapom()) {
                        C_UsePetItem.usePetWeapon(pc, tgPet, item, petItem);
                    }
                    else {
                        C_UsePetItem.usePetArmor(pc, tgPet, item, petItem);
                    }
                }
            }
		} catch (Exception localException) {
		} finally {
			over();
		}
	}
    
	/**
	 * 是否是可以給予物品的物件
	 * 
	 * @param npc
	 * @return
	 */
	private boolean isNpcItemReceivable(final L1Npc npc) {
		for (final String impl : receivableImpls) {
			if (npc.getImpl().equals(impl)) {
				return true;
			}
		}
		return false;
	}
    
	/**
	 * 抓寵物的判斷
	 * 
	 * @param pc
	 * @param target
	 */
	private void tamePet(final L1PcInstance pc, final L1NpcInstance target) {
		if ((target instanceof L1PetInstance) || (target instanceof L1SummonInstance)) {
			return;
		}
		
		int petcost = 0;
		int divisor = 6;
		int petCount = 0;
		final Object[] petList = pc.getPetList().values().toArray();
		/*
		 * if (petList.length > 2) { // 489：你無法一次控制那麼多寵物。 pc.sendPackets(new
		 * S_ServerMessage(489)); return; }
		 */
		for (final Object pet : petList) {
			int nowpetcost = ((L1NpcInstance) pet).getPetcost();
			petcost += nowpetcost;
		}
		int charisma = pc.getCha();
		if (pc.isCrown()) { // 君主
			charisma += 6;
		} else if (pc.isElf()) { // エルフ
			charisma += 12;
		} else if (pc.isWizard()) { // WIZ
			charisma += 6;
		} else if (pc.isDarkelf()) { // DE
			charisma += 6;
		} else if (pc.isDragonKnight()) { // ドラゴンナイト
			charisma += 6;
		} else if (pc.isIllusionist()) { // イリュージョニスト
			charisma += 6;
		}
		charisma -= petcost;
		int npcId = target.getNpcId();

		if ((npcId == 45313) || (npcId == 45710) // 虎男、真虎男
				|| (npcId == 45711) || (npcId == 45712)) { // 高麗幼犬、高麗犬
			divisor = 12;
		} else {
			divisor = 6;
		}

		petCount = charisma / divisor;

		if (petCount <= 0) {
			// 你無法一次控制那麼多寵物。
			pc.sendPackets(new S_ServerMessage(489));
			return;
		}
		
		final L1PcInventory inv = pc.getInventory();
		if (inv.getSize() < 180) {
			if (this.isTamePet(target)) {
				final L1ItemInstance petamu = inv.storeItem(40314, 1); // 項圈
				if (petamu != null) {
					new L1PetInstance(target, pc, petamu.getId());
					pc.sendPackets(new S_ItemName(petamu));
				}

			} else {
				// 馴養失敗。
				pc.sendPackets(new S_ServerMessage(324));
			}
		}
    }
    
	/**
	 * 進化寵物的判斷
	 * 
	 * @param pc
	 * @param pet
	 */
	private void evolvePet(L1PcInstance pc, L1PetInstance pet) {
		L1PcInventory inv = pc.getInventory();
		L1ItemInstance petamu = inv.getItem(pet.getItemObjId());
		pet.getInventory().consumeItem(40070, 1L);

		int level = ConfigOther.PetLevelUp;

		if ((pet.getLevel() >= level) && (pc == pet.getMaster()) && (petamu != null)) {
			L1ItemInstance highpetamu = inv.storeItem(40316, 1L);// 高等寵物項圈
			if (highpetamu != null) {
				pet.evolvePet(highpetamu.getId());
				pc.sendPackets(new S_ItemName(highpetamu));
				inv.removeItem(petamu, 1L);// 移除原寵物項圈
			}
		}
	}
    
	/**
	 * 是否馴服成功
	 * 
	 * @param npc
	 * @return
	 */
	private boolean isTamePet(L1NpcInstance npc) {
		boolean isSuccess = false;
		int npcId = npc.getNpcTemplate().get_npcId();
		if (npcId == 45313) {// 虎男
			Random random = new Random();

			if ((npc.getMaxHp() / 3 > npc.getCurrentHp()) && (random.nextInt(16) == 15)) {
				isSuccess = true;
			}

		} else if (npc.getMaxHp() / 3 > npc.getCurrentHp()) {
			isSuccess = true;
		}

		// 虎男、浣熊、高麗幼犬
		if (((npcId == 45313) || (npcId == 45044) || (npcId == 45711)) && (npc.isResurrect())) {// 復活過的
			isSuccess = false;
		}

		return isSuccess;
	}

	public String getType() {
		return getClass().getSimpleName();
	}
}
