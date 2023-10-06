// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.clientpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.config.ConfigIpCheck;
import com.lineage.server.serverpackets.S_PacketBoxSelect;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.world.World;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_NewCharSelect extends ClientBasePacket
{
    private static final Log _log;
    
    static {
        _log = LogFactory.getLog((Class)C_NewCharSelect.class);
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
        	if (client.getActiveChar() == null) {
                C_NewCharSelect._log.error((Object)("帳號：" + client.getAccountName() + " 空角色退出服務器。"));
                return;
            }
            final L1PcInstance pc = client.getActiveChar();
            if (pc == null) {
				return;
			}
            if ((pc.getMapId() == 9000) || (pc.getMapId() == 9101)) {
				return;
			}
            if (World.get().getPlayer(pc.getName()) == null) {
                client.kick();
            }
                pc.sendPackets(new S_ChangeName(pc, false));
                pc.sendPackets(new S_ChangeName(pc.getId(), pc.getName()));
                pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
                Thread.sleep(250L);
                client.quitGame();
                client.sendPacket(new S_PacketBoxSelect());
                _log.info("角色切換: " + pc.getName());
                if (ConfigIpCheck.timeOutSocket != 0) {
                    client.SetSocket(ConfigIpCheck.timeOutSocket);
                }
		} catch (Exception localException) {

		} finally {
			over();
		}
	}
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
