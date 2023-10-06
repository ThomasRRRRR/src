package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1ClanJoin;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ClanMatching;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;

public class C_ClanMatching extends ClientBasePacket
{
  private static final Log _log = LogFactory.getLog(C_ClanMatching.class);

  public void start(byte[] decrypt, ClientExecutor client)
  {
    try
    {
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

      int type = readC();
      int objid = 0;
      String text = null;
      int htype = 0;
            
      if (type == 0) {//推薦血盟登録、修正
    	//System.out.println("推薦血盟登錄、修正");
        L1ClanMatching cml = L1ClanMatching.getInstance();
        htype = readC();// 血盟類型：0戰鬥1狩獵2友好            
        text = readS();//讀取輸入的介紹文
        
        if (!cml.isClanMatchingList(pc.getClanname())) {//尚未登錄
          cml.writeClanMatching(pc.getClanname(), text, htype);
        }
        else {
          cml.updateClanMatching(pc.getClanname(), text, htype);
        }
        
        //血盟推薦登錄結果
        pc.sendPackets(new S_ClanMatching(true, pc.getClanname()));
        pc.sendPackets(new S_ServerMessage(3261));//成功登錄
      }
      else if (type == 1) {// 登録を削除
    	//System.out.println("推薦血盟取消登陸");
        L1ClanMatching cml = L1ClanMatching.getInstance();
        if (cml.isClanMatchingList(pc.getClanname())) {
          cml.deleteClanMatching(pc);
          cml.deleteClanMatchingApcList(pc.getClanname());
        }
        
        //血盟推薦登錄結果
        pc.sendPackets(new S_ClanMatching(false, pc.getClanname()));  
        pc.sendPackets(new S_ServerMessage(3262));//登錄取消
      } 
      else if (type >= 2 && type <= 4) {// 打開血盟推薦介面
    	  final L1ClanMatching cml = L1ClanMatching.getInstance();
          if (pc.getClanid() == 0) {
              if (!pc.isCrown()) {
                  cml.loadClanMatchingApcList_User(pc);
              }
          }
          else {
              switch (pc.getClanRank()) {
                  case 3:
                  case 4:
                  case 6:
                  case 9:
                  case 10: {
                      cml.loadClanMatchingApcList_Crown(pc);
                      break;
                  }
              }
          }
    	//System.out.println("打開血盟推薦介面。");
      
        //打開血盟推薦  申請/處理申請
        pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
      } 
      else if (type == 5) {// 申請加入
        objid = readD();
        L1Clan clan = WorldClan.get().getClan(objid);
        if ((clan != null) && 
          (!pc.getCMAList().contains(clan.getClanName()))) {
          L1ClanMatching cml = L1ClanMatching.getInstance();
          cml.writeClanMatchingApcList_User(pc, clan);
        }
        //打開血盟推薦  申請/處理申請
        pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
      }
      else if (type == 6) {// 加入許可, 拒絕申請, 取消申請
        objid = readD();
        htype = readC();
        L1ClanMatching cml = L1ClanMatching.getInstance();
        if (htype == 1) {// 加入許可
          L1Object target = World.get().findObject(objid);
          if ((target != null) && ((target instanceof L1PcInstance))) {
            L1PcInstance joinpc = (L1PcInstance)target;
            if (!pc.getInviteList().contains(joinpc.getName())) {
              pc.sendPackets(new S_SystemMessage("此玩家已取消加入請求。"));
            }
            else if (L1ClanJoin.getInstance().ClanJoin(pc, joinpc)) {
              cml.deleteClanMatchingApcList(joinpc);
            }
          }
          else if (target == null) {
            pc.sendPackets(new S_SystemMessage("此玩家已離線。"));
          }
        }
        else if (htype == 2) {// 拒絕申請
          L1Object target = World.get().findObject(objid);
          if (target != null) {//申請人在線上
            if ((target instanceof L1PcInstance)) {
              L1PcInstance user = (L1PcInstance)target;
              cml.deleteClanMatchingApcList(user, user.getId(), pc.getClan());
              user.sendPackets(new S_ServerMessage(3267));//提出的加入邀請被拒絕了
            }
          }
          else {//申請人離線中
        	  String charname = CharacterTable.get().getCharName(objid);
        	  cml.deleteClanMatchingApcList(pc.getClan(), charname);
          }
        }
        else if (htype == 3) {// 取消申請
          L1Clan clan = WorldClan.get().getClan(objid);
          if ((clan != null) && 
            (pc.getCMAList().contains(clan.getClanName()))) {
            cml.deleteClanMatchingApcList(pc, clan);
          }       
        } 
        //打開血盟推薦  申請/處理申請
        pc.sendPackets(new S_ClanMatching(pc, type, objid, htype));
      }
           
    } catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    }
    finally {
      over();
    }
  }


  public String getType()
  {
    return getClass().getSimpleName();
  }
}
