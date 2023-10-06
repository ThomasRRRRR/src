package com.lineage.server.clientpackets;

import com.lineage.server.model.npc.L1NpcHtml;
import com.lineage.server.model.npc.action.L1NpcAction;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.datatables.NpcActionTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.command.GmHtml;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_NPCTalk extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog((Class)C_NPCTalk.class);
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
        	// 資料載入
        	this.read(decrypt);

        	final L1PcInstance pc = client.getActiveChar();
        				
        	if (pc.isDead()) { // 死亡
        		return;
        	}
        				
        	if (pc.isTeleport()) { // 傳送中
        		return;
        	}

        	if (pc.isPrivateShop()) { // 商店村模式
        		return;
        	}
        	
        	if(pc.getTradeID() != 0) { // 交易中
    			return;
    		}
        	
        	final int objid = this.readD();

			final L1Object obj = World.get().findObject(objid);

			// 清空全部買入資料
			pc.get_otherList().clear();
			// 解除GM管理狀態
			pc.get_other().set_gmHtml(null);

			if ((obj != null) && (pc != null)) {
				// 紀錄對話NPC OBJID
				if (obj instanceof L1NpcInstance) {
					final L1NpcInstance npc = (L1NpcInstance) obj;
					// 具有執行項
					if (npc.TALK != null) {
						npc.TALK.talk(pc, npc);
						return;
					}
				}
				final L1NpcAction action = NpcActionTable.getInstance().get(pc, obj);
				if (action != null) {
					final L1NpcHtml html = action.execute("", pc, obj, new byte[0]);
					if (html != null) {
						pc.sendPackets(new S_NPCTalkReturn(obj.getId(), html));
					}
					return;
				}
				obj.onTalkAction(pc);
            }
            else {
                C_NPCTalk._log.error("指定的 OBJID 不存在: " + objid + "角色:" + pc.getName());
            }
        }
        catch (Exception ex) {
//            ex.printStackTrace();
//            return;
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
