package com.lineage.server.clientpackets;

import java.util.Iterator;
import java.util.Collection;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.datatables.CharObjidTable;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.model.L1Clan;
import com.lineage.server.world.WorldClan;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.echo.ClientExecutor;

public class C_CreateClan extends ClientBasePacket
{
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
            this.read(decrypt);
            final L1PcInstance pc = client.getActiveChar();
            if (pc.isGhost()) {
                return;
            }
            if (pc.isDead()) {
                return;
            }
            if (pc.isTeleport()) {
                return;
            }
            final String s = this.readS();
            final int i = s.length();
            if (i > 16) {
                pc.sendPackets(new S_ServerMessage(98));
                return;
            }
            if (!C_CreateChar.isInvalidName(s)) {
                pc.sendPackets(new S_ServerMessage(53));
                return;
            }
            if (pc.isCrown()) {
                if (pc.getClanid() == 0) {
                    final Collection<L1Clan> allClans = WorldClan.get().getAllClans();
                    for (final L1Clan clan : allClans) {
                        if (clan.getClanName().toLowerCase().equals(s.toLowerCase())) {
                            pc.sendPackets(new S_ServerMessage(99));
                            return;
                        }
                    }
//					if (pc.getInventory().checkItem(40308, 30000L)) {		// 身上有金幣3萬
                    final L1Clan clan2 = ClanReading.get().createClan(pc, s);// 建立血盟
                    ClanMembersTable.getInstance().newMember(pc);
                    if (clan2 != null) {
//						pc.getInventory().consumeItem(40308, 30000L);
                        pc.sendPackets(new S_ServerMessage(84, s));// 創立\f1%0  血盟。
                        L1PcUnlock.Pc_Unlock(pc);//原地更新畫面
                        CharObjidTable.get().addClan(clan2.getClanId(), clan2.getClanName());
                    }
//					} else {
//             		pc.sendPackets((ServerBasePacket)new S_ServerMessage(189)); // \f1金幣不足。
//           		} 
                }
                else {
                    pc.sendPackets(new S_ServerMessage(86));// \f1已經創立血盟。
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(85));// \f1王子和公主才可創立血盟。
            }
        }
        catch (Exception ex) {
            return;
        }
        finally {
            this.over();
        }
        this.over();
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
