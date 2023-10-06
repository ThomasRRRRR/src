package com.lineage.server.clientpackets;

import com.add.system.L1ItemNpc;
import com.lineage.config.ConfigOther;
import com.lineage.data.ItemClass;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.Acc_use_Item;
import com.lineage.server.datatables.Char_use_Item;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.datatables.ItemBoxTable;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1ItemDelay;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_PacketBoxItemLv;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ScrollShopSellList;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Box;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.templates.L1EtcItem;
import com.lineage.server.utils.CheckUtil;
import com.lineage.william.ItemIntegration;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;













public class C_ItemUSe extends ClientBasePacket {
  private static final Log _log = LogFactory.getLog(C_ItemUSe.class);
  
  public void start(byte[] decrypt, ClientExecutor client) {
    try {
      read(decrypt);
      
      L1PcInstance pc = client.getActiveChar();

      if (!pc.isGhost())
      {
        if (!pc.isDead())
        {
          if (!pc.isFreezeAtion())
          {
            if (pc.isTeleport()) {
              pc.setTeleport(false);
              pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
            }
            else if (pc.get_isTeleportToOk()) {
              pc.setTeleport(false);
              pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
            }
            else {
              int itemObjid = readD();
              L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);
              if (useItem == null) {
                return;
              }
              int[] bow_GFX_Arrow = ConfigOther.WAR_DISABLE_ITEM;
              boolean castle_area = L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId()); byte b1; int i, arrayOfInt1[];
              for (i = (arrayOfInt1 = bow_GFX_Arrow).length, b1 = 0; b1 < i; ) { int gfx = arrayOfInt1[b1];
                if (useItem.getItemId() == gfx && 
                  castle_area && pc.castleWarResult()) {
                  pc.sendPackets((ServerBasePacket)new S_ServerMessage("攻城中禁止使用此道具"));
                  
                  return;
                } 
                b1++; }
              
              int[] MAP_IDSKILL = ConfigOther.MAP_IDITEM;
              int[] MAP_SKILL = ConfigOther.MAP_ITEM; byte b2; int j, arrayOfInt2[];
              for (j = (arrayOfInt2 = MAP_IDSKILL).length, b2 = 0; b2 < j; ) { int mapid = arrayOfInt2[b2]; byte b; int k, arrayOfInt[];
                for (k = (arrayOfInt = MAP_SKILL).length, b = 0; b < k; ) { int mapskill = arrayOfInt[b];
                  if (pc.getMapId() == mapid && 
                    useItem.getItemId() == mapskill) {
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage("此地圖無法使用此道具"));
                    
                    pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                    return;
                  } 
                  b++; }
                
                b2++; }
              
              if (!useItem(pc, useItem)) {
                return;
              }
              if (useItem.getItem().getacccount() == 1) {
                if (!Acc_use_Item.get().checkAreaArmorOff(pc, useItem.getItemId())) {
                  pc.sendPackets((ServerBasePacket)new S_ServerMessage("該帳號已使用過:" + useItem.getAllName(), 17));
                  return;
                } 
                Acc_use_Item.get().Add(pc, useItem.getItemId());
                Acc_use_Item.get().load();
              } 
              if (useItem.getItem().getcharcount() == 1) {
                if (!Char_use_Item.get().checkAreaArmorOff(pc, useItem.getItemId())) {
                  pc.sendPackets((ServerBasePacket)new S_ServerMessage("該角色已使用過:" + useItem.getAllName(), 17));
                  return;
                } 
                Char_use_Item.get().Add(pc, useItem.getItemId());
                Char_use_Item.get().load();
              } 
              useItem.set_char_objid(pc.getId());
              
              boolean isStop = false;
              boolean isStop1 = false;

              if (pc.hasSkillEffect(78)) {
                pc.killSkillEffectTimer(78);
                pc.startHpRegeneration();
                pc.startMpRegeneration();
              } 

              L1ItemNpc.forRequestItemUSe(client, useItem);

              if (!pc.getMap().isUsableItem()) {
                pc.sendPackets((ServerBasePacket)new S_ServerMessage(563));
                isStop = true;
              } 
              
              if (pc.isParalyzedX() && !isStop) {
                isStop = true;
              }
              
              if (!isStop) {
                switch (pc.getMapId()) {
                  case 22:
                    switch (useItem.getItemId()) {
                      case 30:
                      case 40017:
                        break;
                    } 
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage(563));
                    isStop = true;
                    break;
                } 
              }
              if (!CheckUtil.getUseItemAll(pc) && !isStop) {
                isStop = true;
              }
              
              if (pc.isPrivateShop() && !isStop) {
                isStop = true;
              }
              
              if (pc.hasSkillEffect(8872)) {
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY白息之光狀態下無法使用"));
                isStop = true;
              } 

              if (pc.hasSkillEffect(9990) && 
                pc.getAccessLevel() == 0) {
                isStop1 = true;
              }
              
              if (isStop) {
                pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                return;
              } 
              if (isStop1) {
                pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("目前受到監禁狀態中無法使用道具。"));
                return;
              } 
              if (pc.getCurrentHp() > 0) {
                L1Doll doll; int maxusetime, delay_id = 0;
                if (useItem.getItem().getType2() == 0) {
                  delay_id = ((L1EtcItem)useItem.getItem()).get_delayid();
                  if (delay_id != 0 && pc.hasItemDelay(delay_id)) {
                    pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                    return;
                  } 
                } 
                
                if (useItem.getCount() <= 0L) {
                  pc.sendPackets((ServerBasePacket)new S_ServerMessage(329, useItem.getLogName()));
                  return;
                } 

                int min = useItem.getItem().getMinLevel();
                int max = useItem.getItem().getMaxLevel();
                int minlogpcpower = useItem.getItem().getminlogpcpower();
                int maxlogpcpower = useItem.getItem().getmaxlogpcpower();
                
                if (min != 0 && min > pc.getLevel()) {
                  if (min < 50) {
                    S_PacketBoxItemLv toUser = new S_PacketBoxItemLv(min, 0);
                    pc.sendPackets((ServerBasePacket)toUser);
                  }
                  else {
                    S_ServerMessage toUser = new S_ServerMessage(318, String.valueOf(min));
                    pc.sendPackets((ServerBasePacket)toUser);
                  } 
                  return;
                } 
                if (max != 0 && max < pc.getLevel())
                {
                  if (useItem.getItem().getType2() == 0 || !useItem.isEquipped()) {
                    S_PacketBoxItemLv toUser = new S_PacketBoxItemLv(0, max);
                    pc.sendPackets((ServerBasePacket)toUser);
                    return;
                  } 
                }
                
                if (useItem.getItem().getType2() == 0 && 
                  minlogpcpower != -1 && pc.getMeteLevel() < minlogpcpower) {
                  pc.sendPackets((ServerBasePacket)new S_SystemMessage("您的轉生次數不夠~暫時無法使用此物品"));
                  return;
                } 
                
                if (useItem.getItem().getType2() == 0 && 
                  maxlogpcpower != -1 && pc.getMeteLevel() > maxlogpcpower) {
                  pc.sendPackets((ServerBasePacket)new S_SystemMessage("您的轉生次太高~無法使用此物品唷"));
                  return;
                } 

// 例外狀況:具有延遲時間設置				
int delayitemcount = 0;		
boolean isDelayEffect = false;//是否具有延遲時間設置	
boolean DelayMsg = false;//是否顯示延遲訊息
			
L1ItemInstance[] sameitems = pc.getInventory().findItemsId(useItem.getItemId());//找回相同道具ID陣列		
ArrayList<Long> waittimelist = new ArrayList<Long>();//所有道具等待時間列表

if (delay_id == 10) {//道具分類延遲ID為10 (神聖魔法書系列)
	for (int i1 = 0; i1 < sameitems.length; i1++) {	
		if (sameitems[i1].getItem().getType2() == 0) {//道具類別
			final int delayEffect = ((L1EtcItem) sameitems[i1].getItem()).get_delayEffect();
						
			if (delayEffect > 0) {//DB設定延遲時間大於0			
				isDelayEffect = true;
				final Timestamp lastUsed = sameitems[i1].getLastUsed();		

				if (lastUsed != null) {//上次使用時間不為空		
					final Calendar cal = Calendar.getInstance();
					long PassTime = (cal.getTimeInMillis() - lastUsed.getTime()) / 1000;//上次使用後已過時間												 							
					long waitTime = (delayEffect - PassTime);//計算需等待時間
					
					//System.out.println("waitTime ==:" + waitTime);	
					if (waitTime <= 0) {//不需等待了
						useItem = sameitems[i1];//使用道具定義為此未延遲道具
						//System.out.println("找到未延遲道具:" + sameitems[i].getId());	
						break;//結束迴圈
						
					} else {//須等待時間大於0	
						waittimelist.add(waitTime);		
						delayitemcount++;//延遲道具數量 +1
						//System.out.println("延遲道具數量:" + delayitemcount);	
					}		
					
				} else {//沒有上次使用時間紀錄(可能為新獲得的物品)
					useItem = sameitems[i1];//使用道具定義為此未延遲道具
					//System.out.println("沒有上次使用時間紀錄:" + sameitems[i].getId());
					break;//結束迴圈
				}
			}
		}	
	}
	
	if (delayitemcount >= sameitems.length) {//所有相同道具都在延遲中	
		DelayMsg = true;
	}
	
} else {//其他道具分類延遲ID
	if (useItem.getItem().getType2() == 0) {
		final int delayEffect = ((L1EtcItem) useItem.getItem()).get_delayEffect();
		if (delayEffect > 0) {//DB設定延遲時間大於0	
			isDelayEffect = true;
			final Timestamp lastUsed = useItem.getLastUsed();
			if (lastUsed != null) {//上次使用時間不為空
				final Calendar cal = Calendar.getInstance();
				long PassTime = (cal.getTimeInMillis() - lastUsed.getTime()) / 1000;//上次使用後已過時間												 							
				long waitTime = (delayEffect - PassTime);//計算需等待時間
				if (waitTime > 0) {//須等待時間大於0	
					waittimelist.add(waitTime);	
					DelayMsg = true;
				}
			}
		}
	}
}

if (DelayMsg) {
	long minwaitTime = waittimelist.get(0);
	for (int t = 0; t < waittimelist.size(); t++) {//尋找最短的延遲時間
		if (waittimelist.get(t) < minwaitTime) {
			minwaitTime = waittimelist.get(t);
		}					
	}
	//System.out.println("最短的延遲時間 ==" + minwaitTime);
	
	if (minwaitTime > 60) {//等待時間大於60秒
		minwaitTime /= 60;//轉為分鐘
		// 時間數字 轉換為字串
		final String useTimeS = useItem.getLogName() + " " + String.valueOf(minwaitTime);
		// 1139 %0 分鐘之內無法使用。
		pc.sendPackets(new S_ServerMessage(1139, useTimeS));
		return;
		
	} else {//等待時間60秒以下
		// 時間數字 轉換為字串
		final String useTimeS = useItem.getLogName() + " " + String.valueOf(minwaitTime);						
		pc.sendPackets(new S_SystemMessage(useTimeS + "秒鐘之內無法使用。"));	
		return;
	}		
}

if (isDelayEffect) {//具有延遲時間設置	
	//System.out.println("更新上次使用時間" + useItem.getId());
	final Timestamp ts = new Timestamp(System.currentTimeMillis());//目前時間
	// 更新上次使用時間
	useItem.setLastUsed(ts);					
	pc.getInventory().updateItem(useItem, L1PcInventory.COL_DELAY_EFFECT);
	pc.getInventory().saveItem(useItem, L1PcInventory.COL_DELAY_EFFECT);					
}

                if (!CheckUtil.getUseItemAll(pc) && !isStop) {
                  isStop = true;
                }
                
                if (pc.isPrivateShop() && !isStop) {
                  isStop = true;
                }
                int use_type = useItem.getItem().getUseType();
                
                boolean isClass = false;
                String className = useItem.getItem().getclassname();
                if (!className.equals("0")) {
                  isClass = true;
                }
                
                switch (use_type) {
                  case -11:
                    doll = DollPowerTable.get().get_type(useItem.getItemId());
                    maxusetime = useItem.getItem().getMaxUseTime();
                    if (doll != null && maxusetime > 0 && 
                      useItem.getRemainingTime() <= 0) {
                      pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY必須先使用能量石進行充電。"));
                      
                      return;
                    } 
                    
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -10:
                    if (!CheckUtil.getUseItem(pc)) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -9:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -8:
                    if (isClass) {
                      try {
                        int[] newData = new int[2];
                        newData[0] = readC();
                        newData[1] = readC();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case -7:
                    if (!CheckUtil.getUseItem(pc)) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -6:
                    if (!CheckUtil.getUseItem(pc)) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -4:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case -3:
                    pc.getInventory().setSting(useItem.getItemId());
                    
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage(452, useItem.getLogName()));
                    break;
                  
                  case -2:
                    pc.getInventory().setArrow(useItem.getItemId());
                    
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage(452, useItem.getLogName()));
                    break;
                  case -12:
                  case -5:
                  case -1:
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage(74, useItem.getLogName()));
                    break;
                  
                  case 0:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  case 1:
                    if (useItem(pc, useItem)) {
                      useWeapon(pc, useItem);
                    }
                    break;
                  case 2:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                  case 37:
                  case 40:
                  case 43:
                  case 44:
                  case 45:
                  case 47:
                  case 48:
                  case 49:
                  case 51:
                    if (useItem.getItemId() >= 21157 && useItem.getItemId() <= 21178 && 
                      useItem.get_time() == null) {
                      pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY必須先使用魔法氣息解除封印。"));
                      
                      return;
                    } 
                    if (useItem.getItemId() >= 301060 && useItem.getItemId() <= 301099 && 
                      useItem.getRemainingTime() <= 0) {
                      pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY必須先使用龍的血液解除封印。"));
                      
                      return;
                    } 
                    if (useItem(pc, useItem)) {
                      useArmor(pc, useItem);
                    }
                    break;
                  
                  case 3:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case 4:
                    break;
                  
                  case 5:
                    if (isClass) {
                      try {
                        int[] newData = new int[3];
                        newData[0] = readD();
                        newData[1] = readH();
                        newData[2] = readH();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 6:
                    if (!CheckUtil.getUseItem(pc, useItem.getItem())) {
                      return;
                    }
                    if (isClass) {
                      try {
                        int[] newData = new int[2];
                        newData[1] = readH();
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                        pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 7:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 8:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 9:
                    if (!CheckUtil.getUseItem(pc)) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;

                  
                  case 10:
                    if (useItem.getRemainingTime() <= 0 && useItem.getItemId() != 40004) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case 14:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 15:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case 16:
                    if (isClass) {
                      String cmd = readS();
                      pc.setText(cmd);
                      ItemClass.get().item(null, pc, useItem);
                    } 
                    break;
                  
                  case 17:
                    if (isClass) {
                      try {
                        int[] newData = new int[3];
                        newData[0] = readD();
                        newData[1] = readH();
                        newData[2] = readH();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 26:
                  case 27:
                  case 46:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 28:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readC();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 29:
                    if (!L1BuffUtil.getUseItemTeleport(pc)) {
                      
                      pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                      return;
                    } 
                    if (isClass) {
                      try {
                        int[] newData = new int[2];
                        
                        newData[1] = readH();
                        
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                        pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
                      } catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 30:
                    if (isClass) {
                      try {
                        int obj = readD();
                        int[] newData = { obj };
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 12:
                  case 31:
                  case 33:
                  case 35:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readH();
                        pc.setText(readS());
                        pc.setTextByte(readByte());
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 13:
                  case 32:
                  case 34:
                  case 36:
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case 38:
                    if (!CheckUtil.getUseItem(pc)) {
                      return;
                    }
                    if (isClass) {
                      ItemClass.get().item(null, pc, useItem);
                    }
                    break;
                  
                  case 39:
                    if (isClass) {
                      try {
                        int[] newData = new int[3];
                        newData[0] = readD();
                        newData[1] = readH();
                        newData[2] = readH();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 42:
                    if (isClass) {
                      try {
                        int[] newData = new int[3];
                        newData[0] = readH();
                        newData[1] = readH();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  case 55:
                    if (isClass) {
                      try {
                        int[] newData = new int[1];
                        newData[0] = readD();
                        ItemClass.get().item(newData, pc, useItem);
                      }
                      catch (Exception e) {
                        return;
                      } 
                    }
                    break;
                  
                  default:
                    _log.info("未處理的物品分類: " + use_type);
                    break;
                } 
                
                try {
                  if (useItem.getItem().getType() == 20 && use_type == 0) {
                    int npcId = Integer.parseInt(useItem.getItem().getName());
                    pc.sendPackets((ServerBasePacket)new S_ScrollShopSellList(NpcTable.get().getTemplate(npcId)));
                    return;
                  } 
                } catch (NumberFormatException e) {
                  System.out.println("商店卷軸有誤 ID : " + useItem.getItem().getItemId() + " 。");
                  e.printStackTrace();
                } 
                
                if (useItem.getItem().getType2() == 0 && use_type == 0) {

                  
                  int k = useItem.getItem().getItemId();
                  
                  switch (k) {
                    case 40630:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "diegodiary"));
                      break;
                    
                    case 40663:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "sonsletter"));
                      break;
                    
                    case 40701:
                      if (pc.getQuest().get_step(23) == 1) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "firsttmap")); break;
                      }  if (pc.getQuest().get_step(23) == 2) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "secondtmapa")); break;
                      }  if (pc.getQuest().get_step(23) == 3) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "secondtmapb")); break;
                      }  if (pc.getQuest().get_step(23) == 4) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "secondtmapc")); break;
                      }  if (pc.getQuest().get_step(23) == 5) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmapd")); break;
                      }  if (pc.getQuest().get_step(23) == 6) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmape")); break;
                      }  if (pc.getQuest().get_step(23) == 7) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmapf")); break;
                      }  if (pc.getQuest().get_step(23) == 8) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmapg")); break;
                      }  if (pc.getQuest().get_step(23) == 9) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmaph")); break;
                      }  if (pc.getQuest().get_step(23) == 10) {
                        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), 
                              "thirdtmapi"));
                      }
                      break;
                    
                    case 41007:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "erisscroll"));
                      break;
                    
                    case 41009:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "erisscroll2"));
                      break;
                    
                    case 41060:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "nonames"));
                      break;
                    
                    case 41061:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "kames"));
                      break;
                    
                    case 41062:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "bakumos"));
                      break;
                    
                    case 41063:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "bukas"));
                      break;
                    
                    case 41064:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "huwoomos"));
                      break;
                    
                    case 41065:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "noas"));
                      break;
                    
                    case 41317:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "rarson"));
                      break;
                    
                    case 41318:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "kuen"));
                      break;
                    
                    case 41329:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "anirequest"));
                      break;
                    
                    case 41340:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "tion"));
                      break;
                    
                    case 41356:
                      pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "rparum3"));
                      break;
                  } 
                } 
                if (useItem.getItem().getType2() == 0 && 
                  useItem.getItem().getType() == 16) {
                  
                  if (pc.getInventory().getSize() >= 160) {
                    
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage("角色身上攜帶超過160個道具時無法開啟寶箱。"));
                    
                    return;
                  } 
                  if (pc.getInventory().getWeight240() >= 180) {
                    
                    pc.sendPackets((ServerBasePacket)new S_ServerMessage(82));
                    return;
                  } 
                  if (!pc.hasSkillEffect(90991)) {
                    ArrayList<L1Box> list = null;
                    
                    try {
                      list = ItemBoxTable.get().get(pc, useItem);
                      pc.setSkillEffect(90991, 1000);
                      
                      if (list == null) {
                        ItemBoxTable.get().get_all(pc, useItem);
                        pc.setSkillEffect(90991, 1000);
                      }
                    
                    } catch (Exception exception) {
                    
                    } finally {
                      list = null;
                    } 
                  } 
                } 


                
                if (isDelayEffect && !isClass) {
                  Timestamp ts = new Timestamp(System.currentTimeMillis());
                  
                  useItem.setLastUsed(ts);
                  pc.getInventory().updateItem(useItem, 32);
                  pc.getInventory().saveItem(useItem, 32);
                } 
                
                try {
                  L1ItemDelay.onItemUse(client, useItem);
                } catch (Exception e) {
                  _log.error("分類道具使用延遲異常:" + useItem.getItemId(), e);
                } 
                
                int itemId = useItem.getItem().getItemId();

                
                if (!isClass) {
                  
                  int l = 0;
                  int blanksc_skillid = 0;
                  int spellsc_objid = 0;
                  int spellsc_x = 0;
                  int spellsc_y = 0;
                  int resid = 0;
                  int letterCode = 0;
                  String letterReceiver = "";
                  byte[] letterText = null;
                  int cookStatus = 0;
                  int cookNo = 0;
                  int fishX = 0;
                  int fishY = 0;
                  int mapid = 0;
                  if (itemId == 40126 || itemId == 40098 || 
                    itemId == 41029 || itemId == 40317 || itemId == 41036 || 
                    itemId == 41245 || (itemId >= 41048 && itemId <= 41057) || (
                    itemId >= 40925 && itemId <= 40929) || (itemId >= 40931 && itemId <= 40958) || 
                    itemId == 40964 || itemId == 41426 || itemId == 41427 || itemId == 40075 || 
                    itemId == 70370 || 
                    useItem.getItem().getType() == 19 || 
                    useItem.getItem().getType() == 20 || 
                    useItem.getItem().getType() == 21 || 
                    useItem.getItem().getType() == 24) {
                    l = readD();
                  } else if (itemId == 40090 || itemId == 40091 || itemId == 40092 || 
                    itemId == 40093 || itemId == 40094) {
                    blanksc_skillid = readC();
                  } else if (use_type == 30 || itemId == 40870 || itemId == 40879) {
                    spellsc_objid = readD();
                  } else if (use_type == 5 || use_type == 17) {
                    spellsc_objid = readD();
                    spellsc_x = readH();
                    spellsc_y = readH();
                  } else if (itemId == 40089 || itemId == 140089) {
                    resid = readD();
                  } else if (itemId == 40310 || itemId == 40311 || itemId == 40730 || 
                    itemId == 40731 || itemId == 40732) {
                    letterCode = readH();
                    letterReceiver = readS();
                    letterText = readByte();
                  } else if (itemId >= 41255 && itemId <= 41259) {
                    cookStatus = readC();
                    cookNo = readC();
                  } else if (itemId == 41293 || itemId == 41294 || itemId == 70533) {
                    fishX = readH();
                    fishY = readH();
                  } else if (use_type == 9) {
                    mapid = readH();
                  } else {
                    l = readC();
                  } 
                  
                  L1ItemInstance tgItem = pc.getInventory().getItem(l);
                  
                  int itemType = useItem.getItem().getType();
                  switch (itemType) {
                    case 19:
                      ItemIntegration.forItemIntegration(pc, useItem, tgItem); break;
                  } 
                } 
              }  return;
            }  }  }  } 
      return;
    } catch (Exception exception) {

    
    } finally {
      over();
    } 
  }

  private boolean useItem(L1PcInstance pc, L1ItemInstance useItem) {
    boolean isEquipped = false;
    
    if (pc.isCrown()) {
      if (useItem.getItem().isUseRoyal()) {
        isEquipped = true;
      }
    } else if (pc.isKnight()) {
      if (useItem.getItem().isUseKnight()) {
        isEquipped = true;
      }
    } else if (pc.isElf()) {
      if (useItem.getItem().isUseElf()) {
        isEquipped = true;
      }
    } else if (pc.isWizard()) {
      if (useItem.getItem().isUseMage()) {
        isEquipped = true;
      }
    } else if (pc.isDarkelf()) {
      if (useItem.getItem().isUseDarkelf()) {
        isEquipped = true;
      }
    } else if (pc.isDragonKnight()) {
      if (useItem.getItem().isUseDragonknight()) {
        isEquipped = true;
      }
    } else if (pc.isIllusionist() && 
      useItem.getItem().isUseIllusionist()) {
      isEquipped = true;
    } 

    
    if (!isEquipped)
    {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage(264));
    }
    return isEquipped;
  }
  private void useArmor(L1PcInstance pc, L1ItemInstance armor) {
    boolean equipeSpace;
    int itemid = armor.getItem().getItemId();
    int type = armor.getItem().getType();
    L1PcInventory pcInventory = pc.getInventory();
    
    if (pc.getLevel() < 15 && armor.getItem().get_addcon() < 0) {
      pc.sendPackets((ServerBasePacket)new S_SystemMessage(
            "未滿 15 級的人物將無法裝備會減少「體質」點數的道具。"));
      return;
    } 
    if (type == 9) {
      equipeSpace = (pcInventory.getTypeEquipped(2, 9) <= 3);
    } else if (type == 12) {
      equipeSpace = (pcInventory.getTypeEquipped(2, 12) <= 1);
    } else {
      equipeSpace = (pcInventory.getTypeEquipped(2, type) <= 0);
    } 
    
    if (equipeSpace && !armor.isEquipped()) {
      if (type == 9 && pcInventory.getTypeEquipped(2, 9) == 2 && 
        pc.getQuest().get_step(58003) != 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(3253));
        return;
      } 

      if (type == 9 && pcInventory.getTypeEquipped(2, 9) == 3 && 
        pc.getQuest().get_step(58002) != 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(3253));
        return;
      } 

      if (pcInventory.getTypeAndItemIdEquipped(2, 9, itemid) == 2) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(3278));
        return;
      } 
      
      if (type == 12 && pcInventory.getTypeEquipped(2, 12) == 1 && 
        pc.getQuest().get_step(58001) != 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(3253));
        return;
      } 

      
      if (pcInventory.getTypeAndItemIdEquipped(2, 12, itemid) == 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(3278));
        return;
      } 
      int polyid = pc.getTempCharGfx();
      
      if (!L1PolyMorph.isEquipableArmor(polyid, type)) {
        return;
      }
      
      if ((type == 7 && pcInventory.getTypeEquipped(2, 13) >= 1) || (
        type == 13 && pcInventory.getTypeEquipped(2, 7) >= 1)) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(124));
        return;
      } 
      
      if (type == 7 && pc.getWeapon() != null && 
        pc.getWeapon().getItem().isTwohandedWeapon()) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(129));
        return;
      } 
      
      if (type == 3 && pcInventory.getTypeEquipped(2, 4) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(126, "$224", "$225"));
        return;
      } 
      if (type == 3 && pcInventory.getTypeEquipped(2, 2) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(126, "$224", "$226"));
        return;
      } 
      if (type == 2 && pcInventory.getTypeEquipped(2, 4) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(126, "$226", "$225"));
        return;
      } 
      pcInventory.setEquipped(armor, true);
    }
    else if (armor.isEquipped()) {
      if (armor.getItem().getBless() == 2) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(150));
        return;
      } 
      if (type == 3 && pcInventory.getTypeEquipped(2, 2) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(127));
        return;
      } 
      if ((type == 2 || type == 3) && pcInventory.getTypeEquipped(2, 4) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(127));
        return;
      } 
      if ((type == 7 || type == 13) && 
        pc.hasSkillEffect(90)) {
        pc.removeSkillEffect(90);
      }
      pcInventory.setEquipped(armor, false);
    } else {
      if (armor.getItem().getUseType() == 23) {
        pc.sendPackets((ServerBasePacket)new S_SystemMessage("你已經戴著四個戒指。"));
        return;
      } 
      pc.sendPackets((ServerBasePacket)new S_ServerMessage(124));
      return;
    } 
    pc.setCurrentHp(pc.getCurrentHp());
    pc.setCurrentMp(pc.getCurrentMp());
    pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
    pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(pc));
    pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
  }

  private void useWeapon(L1PcInstance pc, L1ItemInstance weapon) {
    if (pc.getLevel() < 15 && weapon.getItem().get_addcon() < 0) {
      pc.sendPackets((ServerBasePacket)new S_SystemMessage("未滿 15 級的人物將無法裝備會減少「體質」點數的道具。"));
      return;
    } 
    switch (weapon.getItemId()) {
      case 65:
      case 133:
      case 191:
      case 192:
        if (pc.getMapId() != 2000 && !pc.getWeapon().equals(weapon)) {
          pc.sendPackets((ServerBasePacket)new S_ServerMessage(563));
          return;
        } 
        break;
      default:
        if (pc.hasSkillEffect(4007)) {
          
          pc.sendPackets((ServerBasePacket)new S_ServerMessage(563));
          return;
        } 
        break;
    } 
    L1PcInventory pcInventory = pc.getInventory();
    if (pc.getWeapon() == null || !pc.getWeapon().equals(weapon)) {
      int weapon_type = weapon.getItem().getType();
      int polyid = pc.getTempCharGfx();
      
      if (!L1PolyMorph.isEquipableWeapon(polyid, weapon_type)) {
        return;
      }
      if (weapon.getItem().isTwohandedWeapon() && 
        pcInventory.getTypeEquipped(2, 7) >= 1) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(128));
        return;
      } 
    } 
    if (pc.hasSkillEffect(78)) {
      pc.killSkillEffectTimer(78);
      pc.startHpRegeneration();
      pc.startMpRegeneration();
    } 
    if (pc.getWeapon() != null) {
      if (pc.getWeapon().getItem().getBless() == 2) {
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(150));
        return;
      } 
      if (pc.getWeapon().equals(weapon)) {
        pcInventory.setEquipped(pc.getWeapon(), false, false, false);
        return;
      } 
      pcInventory.setEquipped(pc.getWeapon(), false, false, true);
    } 
    if (weapon.getItem().getBless() == 2)
    {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage(149, weapon.getLogName()));
    }
    pcInventory.setEquipped(weapon, true, false, false);
  }
  public String getType() {
    return getClass().getSimpleName();
  }
}