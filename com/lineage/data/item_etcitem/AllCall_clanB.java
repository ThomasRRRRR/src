package com.lineage.data.item_etcitem;

// import java.util.Iterator;

import com.lineage.config.ConfigOther;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.clientpackets.C_Attr;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;








public class AllCall_clanB extends ItemExecutor {
  public static ItemExecutor get() {
    return new AllCall_clanB();
  }

  public synchronized void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
    if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage("旗子內禁止使用"));
      return;
    } 
    if (pc.getClanid() == 0) {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage(
            "\\fY您尚未加入血盟無法使用"));
      return;
    } 
    if (ConfigOther.AllCall_clan_Crown && 
      !pc.isCrown()) {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage(
            "\\fY您的職業不是王族無法使用"));
      
      return;
    } 
    
    if (!pc.getMap().isClanPc()) {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage("所在地圖無法進行傳送"));
      return;
    } 

//Iterator<L1PcInstance> localPreparedStatement;
//for (localPreparedStatement = World.get().getAllPlayers().iterator();localPreparedStatement.hasNext();) {
//	L1PcInstance tgpc = (L1PcInstance)localPreparedStatement.next();
//if (pc.getLocation().isInScreen(tgpc.getLocation()) && pc.getClanid() != tgpc.getClanid()) {
//	/* 53 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到周圍有其他血盟成員,無法使用。"));
//	/*    */       return;
//}
//}

for (L1Object object : World.get().getVisibleObjects((L1Object)pc, 9)) {//距離格數
    if (object instanceof L1PcInstance) {
      L1PcInstance red = (L1PcInstance)object;
      if (!red.isGm() && red.getClanid() != 0 && 
        red.getClanid() != pc.getClanid()) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到周圍有非本盟玩家,無法使用。"));
        
        return;
      } 
    } 
  } 

//	if (pc.getInventory().checkItem(ConfigOther.Call_clan_itemidB, ConfigOther.Call_clan_countB)) {
//		pc.getInventory().consumeItem(ConfigOther.Call_clan_itemidB, ConfigOther.Call_clan_countB);
//
//		World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.clanmsg, new Object[] { pc.getClanname(), pc.getName(), MapsTable.get().getMapName(pc.getMapId()) })));
//		World.get().broadcastPacketToAll(new S_PacketBoxGree(2, "\\fF血盟\\fM[" + pc.getClanname() + "]" + "\\fF玩家\\f=[" + pc.getName() + "]" + "\\fF在\\fH["  + MapsTable.get().getMapName(pc.getMapId())+ "]\\fF使用\\fZ[血盟]穿雲箭\\fF,千軍萬馬來相見"));
//
//		L1Clan clan = WorldClan.get().getClan(pc.getClanname());
//		L1PcInstance[] clanMembers = clan.getOnlineClanMember();
//		byte b;
//		int i;
//		
//		L1PcInstance[] arrayOfL1PcInstance1;
//		for (i = (arrayOfL1PcInstance1 = clanMembers).length, b = 0; b < i; ) {
//			L1PcInstance clanMember1 = arrayOfL1PcInstance1[b];
//			if (clanMember1.getId() != pc.getId()) {
//				clanMember1.setcallclanal(pc.getId());
//				
//				// 在這裡設置 locX、locY 和 mapId 的值
//		        int locX = pc.getX();
//		        int locY = pc.getY();
//		        short mapId = pc.getMapId();
//
//		        // 呼叫 C_Attr 的 setInitialLocation 方法，設置初始坐標和地圖編號
//		        C_Attr.setInitialLocation(locX, locY, mapId);
//				
//		        // 729 盟主正在呼喚你，你要接受他的呼喚嗎？(Y/N)
//				clanMember1.sendPackets((ServerBasePacket)new S_Message_YN(729));
//			}
//			b++;
//		}
//
//	} else {
//		pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.clanmsg1));
//	}

if (!pc.getInventory().checkItem(ConfigOther.Call_clan_itemidB, ConfigOther.Call_clan_countB)) {
	pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.clanmsg1));
	return;
}

//在這裡設置 locX、locY 和 mapId 的值
int locX = pc.getX();
int locY = pc.getY();
short mapId = pc.getMapId();
final L1Map map = pc.getMap();
int heading = pc.getHeading(); // 從 L1PcInstance 中獲取方向

//呼叫 C_Attr 的 setInitialLocation 方法，設置初始坐標和地圖編號
C_Attr.setInitialLocation(pc.getId(), locX, locY, mapId, map, heading);

	L1Clan clan = WorldClan.get().getClan(pc.getClanid());

	if (clan != null) {
	
	final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
	for (L1PcInstance clan_pc : clanMembers) {
	    if (clan_pc != null && clan_pc.getClanid() == pc.getClanid() && pc.getId() != clan_pc.getId()) {
	    	clan_pc.setcallclanal(pc.getId());

	        //729 盟主正在呼喚你，你要接受他的呼喚嗎？(Y/N)
	    	clan_pc.sendPackets(new S_Message_YN(729));
	    }
	}
}
	pc.getInventory().consumeItem(ConfigOther.Call_clan_itemidB, ConfigOther.Call_clan_countB);

	World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.clanmsg, new Object[] { pc.getClanname(), pc.getName(), MapsTable.get().getMapName(pc.getMapId()) })));
	World.get().broadcastPacketToAll(new S_PacketBoxGree(2, "\\fF血盟\\fM[" + pc.getClanname() + "]" + "\\fF玩家\\f=[" + pc.getName() + "]" + "\\fF在\\fH["  + MapsTable.get().getMapName(pc.getMapId())+ "]\\fF使用\\fZ[血盟]穿雲箭\\fF,千軍萬馬來相見"));
//    for (String member : clan.getAllMembers()) {
//        final L1PcInstance clan_pc = World.get().getPlayer(member);
//        if (clan_pc != null && clan_pc.getClanid() == pc.getClanid() && pc.getId() != clan_pc.getId()) {
//            clan_pc.setTempID(pc.getId()); // 暫存盟主ID
//            clan_pc.setcallclanal(pc.getId());
//
//            // 729 盟主正在呼喚你，你要接受他的呼喚嗎？(Y/N)
//            sendCallMessage(clan_pc);
//        	}
//    	}
//	}
}
}