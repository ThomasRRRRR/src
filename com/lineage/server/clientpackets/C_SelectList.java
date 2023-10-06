package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 要求物品維修
 *
 * @author daien
 *
 */
public class C_SelectList extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_SelectList.class);

	/*public C_SelectList() {
	}

	public C_SelectList(final byte[] abyte0, final ClientExecutor client) {
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

						final int itemObjectId = this.readD();
						final int npcObjectId = this.readD();
            if (npcObjectId != 0) {// 武器修理
                final L1Object obj = World.get().findObject(npcObjectId);
                if (obj != null && obj instanceof L1NpcInstance) {
                    final L1NpcInstance npc = (L1NpcInstance)obj;
                    final int difflocx = Math.abs(pc.getX() - npc.getX());
                    final int difflocy = Math.abs(pc.getY() - npc.getY());
                    // 5格以上距離無效
                    if (difflocx > 5 || difflocy > 5) {
                        return;
                    }
                }
                final L1PcInventory pcInventory = pc.getInventory();
                final L1ItemInstance item = pcInventory.getItem(itemObjectId);
                final int cost = item.get_durability() * 200;// 每一點損壞度200元
                if (!pc.getInventory().consumeItem(40308, cost)) {
                    pc.sendPackets(new S_ServerMessage(189));
                    return;
                }
                item.set_durability(0);
             // 464：%0 現在變成像個新的一樣。
                pc.sendPackets(new S_ServerMessage(464, item.getLogName()));
                pcInventory.updateItem(item, 1);
            }
            else if (ConfigOther.petcountcha) {
                pc.petReceive(itemObjectId);
            }
            else {
                pc.petReceive1(itemObjectId);
            }
        }
        catch (Exception e) {
            C_SelectList._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        finally {
            this.over();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
