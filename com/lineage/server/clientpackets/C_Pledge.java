package com.lineage.server.clientpackets;

import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import java.util.ArrayList;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_Pledge;
import com.lineage.server.world.WorldClan;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 要求查詢血盟成員
 *
 * @author daien
 *
 */
public class C_Pledge extends ClientBasePacket {

	//private static final Log _log = LogFactory.getLog(C_Pledge.class);

	/*public C_Pledge() {
	}

	public C_Pledge(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/
	
	private static final Log _log = LogFactory.getLog(C_Pledge.class);

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
            this.read(decrypt);
            final L1PcInstance pc = client.getActiveChar();
            if (pc.getClanid() > 0) {
                final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                pc.sendPackets(new S_Pledge(clan.getClanId()));
                ArrayList<String> page1 = new ArrayList<String>();
                ArrayList<String> page2 = new ArrayList<String>();
                ArrayList<String> page3 = new ArrayList<String>();
                final String[] members = clan.getAllMembers();
                int div = 0;
                try {
                    div = members.length / 127;
                }
                catch (Exception ex) {}
                if (div > 0) {
                    for (int i = 0; i < members.length; ++i) {
                        if (i < 127) {
                            page1.add(members[i]);
                        }
                        else if (i < 256) {
                            page2.add(members[i]);
                        }
                        else {
                            page3.add(members[i]);
                        }
                    }
                    if (page3.size() > 0) {
                        div = 3;
                    }
                    else if (page2.size() > 0) {
                        div = 2;
                    }
                    else {
                        div = 1;
                    }
                    if (page1.size() > 0) {
                        pc.sendPackets(new S_Pledge(div, 0, page1));
                    }
                    if (page2.size() > 0) {
                        pc.sendPackets(new S_Pledge(div, 1, page2));
                    }
                    if (page3.size() > 0) {
                        pc.sendPackets(new S_Pledge(div, 2, page3));
                    }
                    page1.clear();
                    page2.clear();
                    page3.clear();
                    page1 = null;
                    page2 = null;
                    page3 = null;
                }
                else {
                    for (int i = 0; i < members.length; ++i) {
                        page1.add(members[i]);
                    }
                    pc.sendPackets(new S_Pledge(1, 0, page1));
                }
                if (pc.isCrown() && pc.getId() == clan.getLeaderId()) {
                    pc.sendPackets(new S_PacketBox(171, clan.getOnlineClanMember()));
                }
                else {
                    pc.sendPackets(new S_PacketBox(171, clan.getOnlineClanMember()));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(1064));
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
