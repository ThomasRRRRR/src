package com.lineage.server.clientpackets;

import com.lineage.server.ActionCodes;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.L1Inventory;
import com.lineage.config.ConfigTakeitem;
import com.lineage.config.ConfigOther;
import com.lineage.server.types.Point;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 要求撿取物品
 *
 * @author daien
 *
 */
public class C_PickUpItem extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_PickUpItem.class);

	/*public C_PickUpItem() {
	}

	public C_PickUpItem(final byte[] abyte0, final ClientExecutor client) {
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

						if (pc.isPrivateShop()) { // 商店村模式
							return;
						}

						if (pc.isInvisble()) { // 隱身狀態
							return;
						}

						if (pc.isInvisDelay()) { // 隱身延遲
							return;
						}
						final int x = this.readH();
						final int y = this.readH();
						final int objectId = this.readD();
						long pickupCount = this.readD();
						if (pickupCount > Integer.MAX_VALUE) {
							pickupCount = Integer.MAX_VALUE;
						}
						pickupCount = Math.max(0, pickupCount);
						final L1Inventory groundInventory = 
							World.get().getInventory(x, y, pc.getMapId());
						
						if (groundInventory == null) {
						    // 处理空 inventory 的情况，可能是输出日志或返回
						    return;
						}
						
						final L1Object object = groundInventory.getItem(objectId);
						if ((object != null) && !pc.isDead()) {
							final L1ItemInstance item = (L1ItemInstance) object;
							if (item != null && item.getCount() <= 0) {
								return;
							}
							if (item != null && (item.getItemOwnerId() != 0) && (pc.getId() != item.getItemOwnerId())) {
								// 道具取得失敗。
								pc.sendPackets(new S_ServerMessage(623));
								return;
							}
							if (pc.getLocation().getTileLineDistance(item.getLocation()) > 3) {
								return;
							}
							item.set_showId(-1);
							// 容量重量確認
                if (pc.getInventory().checkAddItem(item, pickupCount) == 0 && item.getX() != 0 && item.getY() != 0) {
                    if (ConfigOther.Pickupitemtoall && item.getItem().getItemId() != 40515 && item.getdropitemcheck() == 1) {
                        if (item.getItem().getType2() == 2 && item.getEnchantLevel() > 0) {
                            ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), "+" + item.getEnchantLevel() + item.getItem().getName());
                        }
                        else if (item.getItem().getType2() == 1 && item.getEnchantLevel() > 0) {
                            ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), "+" + item.getEnchantLevel() + item.getItem().getName());
                        }
                        else if (item.getItem().getType2() == 2 && item.getEnchantLevel() == 0) {
                            ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), item.getItem().getName());
                        }
                        else if (item.getItem().getType2() == 1 && item.getEnchantLevel() == 0) {
                            ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), item.getItem().getName());
                        }
                        else {
                            ConfigTakeitem.msg(pc.getName(), item.getdropitemname(), String.valueOf(String.valueOf(item.getItem().getName())) + "(" + pickupCount + ")");
                        }
                    }
                    C_PickUpItem._log.info((Object)("人物:" + pc.getName() + " 撿起物品 " + item.getItem().getName() + " 物品OBJID:" + item.getId()));
                    groundInventory.tradeItem(item, pickupCount, pc.getInventory());
                    pc.turnOnOffLight();
                    pc.setHeading(pc.targetDirection(item.getX(), item.getY()));
                    if (!pc.isGmInvis()) {
						pc.broadcastPacketAll(new S_ChangeHeading(pc));
						// 送出封包(動作)
						pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Pickup));
						//pc.broadcastPacketAll(new S_AttackPickUpItem(pc, objectId));
					}
                    RecordTable.get().recordtakeitem(pc.getName(), item.getAllName(), (int)pickupCount, item.getId(), pc.getIp());
                }
            }
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
