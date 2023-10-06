package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.add.L1PcUnlock;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.ClanMembersTable;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;

public class C_BanClan extends ClientBasePacket
{
  private static final Log _log = LogFactory.getLog(C_BanClan.class);

  public void start(byte[] decrypt, ClientExecutor client)
  {
    try {
    	read(decrypt);

    	String s = readS();

    	L1PcInstance pc = client.getActiveChar();  
    	if (pc == null) {
    		return;
    	}
		
    	L1Clan clan = WorldClan.get().getClan(pc.getClanname());

    	if (clan != null) {
    		String[] clanMemberName = clan.getAllMembers();

    		if ((pc.isCrown()) && (pc.getId() == clan.getLeaderId())) {
    			for (int i = 0; i < clanMemberName.length; i++) {
    				if (pc.getName().equalsIgnoreCase(s)) {
    					return;
    				}
    			}

    			L1PcInstance tempPc = World.get().getPlayer(s);
    			if (tempPc != null) {//人物在線上
    				try {
    					if (tempPc.getClanid() == pc.getClanid()) {
    						tempPc.setClanid(0);
    						tempPc.setClanname("");
    						tempPc.setClanRank(0);       
    						tempPc.setClanMemberId(0);
    						tempPc.sendPacketsAll(new S_CharTitle(tempPc.getId(), ""));
    						tempPc.sendPacketsAll(new S_CharReset(tempPc.getId(), 0));
    						tempPc.save();
                
    						clan.delMemberName(tempPc.getName());               
    						ClanMembersTable.getInstance().deleteMember(tempPc.getId());//刪除血盟成員資料
    						
    						L1PcUnlock.Pc_Unlock(tempPc);//原地更新畫面
    						
    						tempPc.sendPackets(new S_ServerMessage(238, pc.getClanname()));// 238 你被 %0 血盟驅逐了。                  							
    						pc.sendPackets(new S_ServerMessage(240, tempPc.getName()));	// 240 %0%o 被你從你的血盟驅逐了。

    					} else {
    						// 109 沒有叫%0的人。
    						pc.sendPackets(new S_ServerMessage(109, s));
    					}
    					
    				} catch (Exception e) {
    					_log.error(e.getLocalizedMessage(), e);
    				}
    				
    			} else {//不在線上
    				try {
    					L1PcInstance restorePc = CharacterTable.get().restoreCharacter(s);
    					if ((restorePc != null) && (restorePc.getClanid() == pc.getClanid())) {
    						restorePc.setClanid(0);
    						restorePc.setClanname("");
    						restorePc.setClanRank(0);   
    						restorePc.setClanMemberId(0);
    						restorePc.setTitle("");
    						restorePc.save();
                               
    						clan.delMemberName(restorePc.getName());
    						ClanMembersTable.getInstance().deleteMember(restorePc.getId());
    						// 240 %0%o 被你從你的血盟驅逐了。
    						pc.sendPackets(new S_ServerMessage(240, restorePc.getName()));
              
    					} else {
    						// 109 沒有叫%0的人。
    						pc.sendPackets(new S_ServerMessage(109, s));
    					}
    					
    				} catch (Exception e) {
    					_log.error(e.getLocalizedMessage(), e);
    				}
    			}
    			
    		} else {	
    			// 518 血盟君主才可使用此命令。
    			pc.sendPackets(new S_ServerMessage(518));
    		}
		}
    } catch (Exception localException1) {
    	
    } finally {
    	over();
    }
  }

  public String getType()
  {
    return getClass().getSimpleName();
  }
}
