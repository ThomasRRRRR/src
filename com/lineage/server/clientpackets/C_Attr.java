package com.lineage.server.clientpackets;

import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.types.Point;

import java.util.Random;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;

import java.sql.Timestamp;

import com.lineage.server.datatables.ClanMembersTable;
import com.add.L1PcUnlock;
import com.lineage.server.serverpackets.S_CharReset;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.config.ConfigClan;

import java.util.Collection;

import com.lineage.server.templates.L1House;
import com.lineage.server.model.Instance.L1EffectInstance;

import java.util.Iterator;

import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.william.ConDmg;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.L1ChatParty;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.datatables.lock.HouseReading;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.L1Object;
import com.lineage.server.datatables.CharObjidTable;

import java.util.regex.Matcher;

import com.lineage.server.serverpackets.S_CharVisualUpdate;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_Resurrection;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_Trade;
import com.lineage.server.utils.FaceToFace;
import com.lineage.server.world.WorldWar;
import com.lineage.server.model.L1War;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.model.L1ClanJoin;
import com.lineage.server.datatables.lock.ClanReading;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.lineage.server.model.item.L1ItemId;
import com.lineage.server.datatables.lock.DwarfForChaReading;

import java.util.Map;
import java.util.HashMap;

/**
 * 要求點選項目的結果(Y/N)
 *
 * @author dexc
 *
 */
public class C_Attr extends ClientBasePacket {
	
//	// 定義靜態變量來存儲初始坐標和地圖編號
//    private static int initialLocX;
//    private static int initialLocY;
//    private static short initialMapId;
//    private static L1Map initialMap;
//
//    public static void setInitialLocation(int locX, int locY, short mapId, final L1Map map) {
//        initialLocX = locX;
//        initialLocY = locY;
//        initialMapId = mapId;
//        initialMap = map;
//    }
    
	
    private static Map<Integer, Integer> initialLocXMap = new HashMap<>();
    private static Map<Integer, Integer> initialLocYMap = new HashMap<>();
    private static Map<Integer, Short> initialMapIdMap = new HashMap<>();
    private static Map<Integer, L1Map> initialMapMap = new HashMap<>();
    private static Map<Integer, Integer> initialHeadingMap = new HashMap<>(); // 添加保存方向的 Map

    public static void setInitialLocation(int playerId, int locX, int locY, short mapId, L1Map map, int heading) {
        initialLocXMap.put(playerId, locX);
        initialLocYMap.put(playerId, locY);
        initialMapIdMap.put(playerId, mapId);
        initialMapMap.put(playerId, map);
        initialHeadingMap.put(playerId, heading); // 保存方向
    }
    public static int getInitialLocX(int playerId) {
        return initialLocXMap.getOrDefault(playerId, 0);
    }

    public static int getInitialLocY(int playerId) {
        return initialLocYMap.getOrDefault(playerId, 0);
    }

    public static short getInitialMapId(int playerId) {
        return initialMapIdMap.getOrDefault(playerId, (short) 0);
    }

    public static L1Map getInitialMap(int playerId) {
        return initialMapMap.getOrDefault(playerId, null);
    }

    public static int getInitialHeading(int playerId) {
        return initialHeadingMap.getOrDefault(playerId, 0);
    }
    
    
    
    private static final Log _log = LogFactory.getLog(C_Attr.class);
    
	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);
			
			final L1PcInstance pc = client.getActiveChar();
			
			if (pc.isGhost()) { // 鬼魂模式
				return;
			}

			if (pc.isTeleport()) { // 傳送中
				return;
			}
			
			int mode = readH();

			@SuppressWarnings("unused")
			int tgobjid = 0;

			if (mode == 0) {
				tgobjid = readD();
				mode = readH();
			}
			
			int c;
			L1Clan clan;
			L1PcInstance clanMember[];
			String clan_name;
			boolean loginLeader;
			
            switch (mode) {
            
			case 97: // \f3%0%s 想加入你的血盟。你接受嗎。(Y/N)
				c = this.readC();
				L1PcInstance joinPc = (L1PcInstance) World.get().findObject(pc.getTempID());
				pc.setTempID(0);
				if (joinPc != null) {
					if (c == 0) { // No
						joinPc.sendPackets(new S_ServerMessage(96, pc.getName())); // 拒絕你的請求。
					} else if (c == 1) { // YES
						
						L1ClanJoin.getInstance().ClanJoin(pc, joinPc);
					}
				}
				break;
				
				// 同盟系統 by Manly
				case 223: // %0 血盟要與你同盟。是否接受？(Y/N)
				final L1PcInstance alliancePc = (L1PcInstance) World.get().findObject(pc.getTempID());
				pc.setTempID(0);
				if (alliancePc == null) {
					return;
				}
				c = this.readC();

				if (c == 1) {
				final L1Clan user_clan = pc.getClan();
				if (user_clan == null || pc.getId() != user_clan.getLeaderId()) {
				// 血盟君主才可使用此命令。
				pc.sendPackets(new S_ServerMessage(518));
				return;
				}

				final L1Clan target_clan = alliancePc.getClan();
				if (target_clan == null) {
					return;
				}

				L1Alliance alliance = ClanAllianceReading.get().getAlliance(user_clan.getClanId());
				if (alliance == null) {
					alliance = new L1Alliance(user_clan.getClanId(), user_clan,target_clan);
					ClanAllianceReading.get().insertAlliance(alliance);
					} else {
					if (!alliance.checkSize()) {
						alliancePc.sendPackets(new S_ServerMessage(1201));
						return;
						}
						alliance.addAlliance(target_clan);
						ClanAllianceReading.get().updateAlliance(alliance.getOrderId(), alliance.getTotalList());
						}
						World.get().broadcastPacketToAll(new S_ServerMessage(224, user_clan.getClanName(),target_clan.getClanName()));
						alliance.sendPacketsAll("", new S_ServerMessage(1200,target_clan.getClanName()));
						} else if (c == 0) {
						alliancePc.sendPackets(new S_ServerMessage(1198));
						}
						break;
								
				case 1210: // 確定要退出同盟嗎? (Y/N)
					if (readC() == 1) {
						final L1Clan user_clan = pc.getClan();
						if (user_clan == null || pc.getId() != user_clan.getLeaderId()) {
							// 血盟君主才可使用此命令。
							pc.sendPackets(new S_ServerMessage(518));
							return;
						}

						final L1Alliance alliance = ClanAllianceReading.get()
								.getAlliance(user_clan.getClanId());
						if (alliance == null) {
							return;
						}

						for (final L1Clan l1clan : alliance.getTotalList()) {
							if (l1clan.getClanId() == user_clan.getClanId()) {
								alliance.getTotalList().remove(l1clan);
								break;
							}
						}

						if (alliance.getTotalList().size() < 2) {
							ClanAllianceReading.get().deleteAlliance(
									alliance.getOrderId());

						} else {
							ClanAllianceReading.get().updateAlliance(
									alliance.getOrderId(), alliance.getTotalList());
						}

						World.get().broadcastPacketToAll(
								new S_ServerMessage(225, alliance.getTotalList()
										.get(0).getClanName(), user_clan.getClanName()));

						alliance.sendPacketsAll("",
								new S_ServerMessage(1204, user_clan.getClanName()));

					}
					break;
					
				case 1906: // 於血戰中任意退出時會被處以3小時的血盟加入限制。(請求君主同意Y，任意退出N)
					c = this.readC();
					if (c == 0) { // No
						leaveClan(pc, false);
					} else { // Yes
						loginLeader = false;
						clan_name = pc.getClanname();
						clan = WorldClan.get().getClan(clan_name);
						clanMember = clan.getOnlineClanMember();
						for (int i = 0; i < clanMember.length; i++) {
							if (clanMember[i].getClanRank() == L1Clan.CLAN_RANK_LEAGUE_PRINCE
									|| clanMember[i].getClanRank() == L1Clan.CLAN_RANK_LEAGUE_VICEPRINCE || clanMember[i].getClanRank() == L1Clan.CLAN_RANK_PRINCE) {
								// %0%s已申請退出血盟。請問要同意嗎？
								clanMember[i].setTempID(pc.getId()); // 相手のオブジェクトIDを保存しておく
								clanMember[i].sendPackets(new S_Message_YN(1908, pc.getName()));
								loginLeader = true;
							}

						}
						if (loginLeader) {
							// 跟君主/副君主請求允許退出血盟中，請稍候。 
							pc.sendPackets(new S_ServerMessage(1907));
						} else {
							// 君主/副君主為非連線中，請問要任意退出嗎？
							pc.sendPackets(new S_Message_YN(1914));
						}
					}
					break;
					
				case 1908: // %0%s已申請退出血盟。請問要同意嗎？
					L1PcInstance leavePc = (L1PcInstance) World.get()
							.findObject(pc.getTempID());
					c = this.readC();
					if (c == 0) { // No
						clan_name = pc.getClanname();
						clan = WorldClan.get().getClan(clan_name);
						clanMember = clan.getOnlineClanMember();
						for (int i = 0; i < clanMember.length; i++) {
							// %0君主(副君主)已拒絕%1退出血盟。
							clanMember[i].sendPackets(new S_ServerMessage(1917, pc
									.getName(), leavePc.getName()));
						}
						// 君主已拒絕您退出血盟，請問要任意退出嗎？
						leavePc.sendPackets(new S_Message_YN(1912));
					} else { // Yes
						clan_name = pc.getClanname();
						clan = WorldClan.get().getClan(clan_name);
						clanMember = clan.getOnlineClanMember();
						for (int i = 0; i < clanMember.length; i++) {
							// %0君主(副君主)已同意%1退出血盟。
							clanMember[i].sendPackets(new S_ServerMessage(1916, pc
									.getName(), leavePc.getName()));
						}
						leaveClan(leavePc, true);
					}
					break;
					
				case 1912: // 君主已拒絕您退出血盟，請問要任意退出嗎？
				case 1914: // 君主/副君主為非連線中，請問要任意退出嗎？
					c = this.readC();
					if (c == 0) { // No
						return;
					}
					leaveClan(pc, false);
					break;
				case 217: // %0 血盟向你的血盟宣戰。是否接受？(Y/N)
				case 221: // %0 血盟要向你投降。是否接受？(Y/N)
				case 222: // %0 血盟要結束戰爭。是否接受？(Y/N)
					c = this.readC();
					// 宣戰者
					final L1PcInstance enemyLeader = (L1PcInstance) World.get().findObject(pc.getTempID());
					if (enemyLeader == null) {
						return;
					}
					pc.setTempID(0);
					final String clanName = pc.getClanname();
					final String enemyClanName = enemyLeader.getClanname();// 宣戰盟
					if (c == 0) { // No
						if (mode == 217) {
							// 236 %0 血盟拒絕你的宣戰。
							enemyLeader.sendPackets(new S_ServerMessage(236, clanName));
							
						} else if ((mode == 221) || (mode == 222)) {
							// 237 %0 血盟拒絕你的提案。
							enemyLeader.sendPackets(new S_ServerMessage(237, clanName));
						}
						
					} else if (c == 1) { // Yes
						if (mode == 217) {
							final L1War war = new L1War();
							war.handleCommands(2, enemyClanName, clanName); // 模擬戦開始
						
						} else if ((mode == 221) || (mode == 222)) {
							// 取回全部戰爭清單
							for (final L1War war : WorldWar.get().getWarList()) {
								if (war.checkClanInWar(clanName)) { // 戰爭中
									if (mode == 221) {
										war.surrenderWar(enemyClanName, clanName); // 投降
										
									} else if (mode == 222) {
										war.ceaseWar(enemyClanName, clanName); // 結束
									}
									break;
								}
							}
						}
					}
					break;
					
				case 252: // \f2%0%s 要與你交易。願不願交易？ (Y/N)
					c = this.readC();
					final L1PcInstance trading_partner = (L1PcInstance) World.get().findObject(pc.getTradeID());
					if (trading_partner != null) {
						if (c == 0) {// No
							// 253 %0%d 拒絕與你交易。
							trading_partner.sendPackets(new S_ServerMessage(253, pc.getName()));
							pc.setTradeID(0);
							trading_partner.setTradeID(0);
							
						} else if (c == 1) {// Yes
							// 關閉其他對話檔
							pc.sendPackets(new S_CloseList(pc.getId()));
							trading_partner.sendPackets(new S_CloseList(trading_partner.getId()));
							
							pc.sendPackets(new S_Trade(trading_partner.getName()));
							trading_partner.sendPackets(new S_Trade(pc.getName()));
						}
					}
					break;
					
				case 321: // 321 是否要復活？ (Y/N)
					c = this.readC();
					final L1PcInstance resusepc1 = (L1PcInstance) World.get().findObject(pc.getTempID());
					pc.setTempID(0);
					if (resusepc1 != null) { // 復活スクロール
						if (c == 0) { // No
							;
						} else if (c == 1) { // Yes
							//刪除人物墓碑
							L1EffectInstance tomb = pc.get_tomb();
							if (tomb != null) {
								tomb.broadcastPacketAll(new S_DoActionGFX(tomb.getId(), 8));
								tomb.deleteMe();
							}
										
							pc.sendPacketsAll(new S_SkillSound(pc.getId(), '\346'));
							pc.resurrect(pc.getMaxHp() / 2);
							pc.startHpRegeneration();
							pc.startMpRegeneration();
							pc.stopPcDeleteTimer();
							pc.sendPacketsAll(new S_Resurrection(pc, resusepc1, 0));
							pc.sendPacketsAll(new S_CharVisualUpdate(pc));
						}
					}
					break;
					
				case 322: // 322 是否要復活？ (Y/N)
					c = this.readC();
					final L1PcInstance resusepc2 = (L1PcInstance) World.get().findObject(pc.getTempID());
					pc.setTempID(0);
					if (resusepc2 != null) { // 祝福された 復活スクロール、リザレクション、グレーター リザレクション
						if (c == 0) { // No
							;
						} else if (c == 1) { // Yes
							//刪除人物墓碑
							L1EffectInstance tomb = pc.get_tomb();
							if (tomb != null) {
								tomb.broadcastPacketAll(new S_DoActionGFX(tomb.getId(), 8));
								tomb.deleteMe();
							}
											
							pc.sendPacketsAll(new S_SkillSound(pc.getId(), '\346'));
							pc.resurrect(pc.getMaxHp());
							pc.startHpRegeneration();
							pc.startMpRegeneration();
							pc.stopPcDeleteTimer();
							pc.sendPacketsAll(new S_Resurrection(pc, resusepc2, 0));
							pc.sendPacketsAll(new S_CharVisualUpdate(pc));
							// EXPロストしている、G-RESを掛けられた、EXPロストした死亡
							// 全てを満たす場合のみEXP復旧
							if ((pc.getExpRes() == 1) && pc.isGres() && pc.isGresValid()) {
								pc.resExp();
								pc.setExpRes(0);
								pc.setGres(false);
							}
						}
					}
					break;
                
                case 325: // 你想叫牠什麼名字？ 
    				c = readC();
    				String petName = readS();

                    if (pc.is_rname()) {
                    	String name = Matcher.quoteReplacement(petName);
    					name = name.replaceAll("\\s", "");
    					name = name.replaceAll("　", "");
    					name = name.substring(0,1).toUpperCase() + name.substring(1);
    					
    					for (String ban : C_CreateChar.BANLIST) {
    						if (name.indexOf(ban) != -1) {
    							name = "";
    						}
    					}
    					
    					if (name.length() == 0) {
    						// 53 無效的名字。 若您擅自修改，將無法進行遊戲。
    						pc.sendPackets(new S_ServerMessage(53));
    						return;
    					}
    					
    					// 名稱是否包含禁止字元
    					if (!C_CreateChar.isInvalidName(name)) {
    						// 53 無效的名字。 若您擅自修改，將無法進行遊戲。
    						pc.sendPackets(new S_ServerMessage(53));
    						return;
    					}
    					
    					// 檢查名稱是否以被使用
    					if (CharObjidTable.get().charObjid(name) != 0) {
    						// 58 已經有同樣的角色名稱。請重新輸入。
    						pc.sendPackets(new S_ServerMessage(58));
    						return;
    					}
    					
    					String srcname = pc.getName();//人物原本名稱
    					
    					World.get().removeObject(pc);// 移出世界
    					
    					pc.getInventory().consumeItem(41227, 1);

                        RecordTable.get().recordPcChangeName(pc.getId(), pc.getAccountName(), pc.getName(), name, pc.getIp());
                        pc.setName(name);//人物目前名稱
    					CharObjidTable.get().reChar(pc.getId(), name);//角色OBJID資料更新
    					CharacterTable.get().newCharName(pc.getId(), name);//角色名稱資料更新
    					DwarfForChaReading.get().updateCharName(name, srcname);//角色專屬倉庫資料更新
    					World.get().storeObject(pc);// 重新加入世界
    					
    					// 改變顯示(復原正常)
    					pc.sendPacketsAll(new S_ChangeName(pc, true));
    					
    					pc.sendPackets(new S_ServerMessage(166, "由於人物名稱異動!請重新登入遊戲!將於5秒後強制斷線!"));
    					final KickPc kickPc = new KickPc(pc);
    					kickPc.start_cmd();
                    } else {
    					final L1PetInstance pet = (L1PetInstance) World.get().findObject(pc.getTempID());
    					pc.setTempID(0);
    					renamePet(pet, petName);
    				}
                    pc.rename(false);
    				break;
                
                case 512: // 請輸入血盟小屋名稱?
    				c = this.readC(); // ?
    				String houseName = this.readS();
    				final int houseId = pc.getTempID();
    				pc.setTempID(0);
    				if (houseName.length() <= 16) {
    					final L1House house = HouseReading.get().getHouseTable(houseId);
    					house.setHouseName(houseName);
    					HouseReading.get().updateHouse(house); // DBに書き込み
    					
    				} else {
    					pc.sendPackets(new S_ServerMessage(513)); // 血盟小屋名稱太長。
    				}
    				break;

    			case 630: // %0%s 要與你決鬥。你是否同意？(Y/N)
    				c = this.readC();
    				final L1PcInstance fightPc = 
    					(L1PcInstance) World.get().findObject(pc.getFightId());
    				if (c == 0) {
    					pc.setFightId(0);
    					fightPc.setFightId(0);
    					// 631 %0%d 拒絕了與你的決鬥。
    					fightPc.sendPackets(new S_ServerMessage(631, pc.getName()));
    				
    				} else if (c == 1) {
    					fightPc.sendPackets(new S_PacketBox(
    							S_PacketBox.MSG_DUEL,
    							fightPc.getFightId(), fightPc.getId()));
    					
    					pc.sendPackets(new S_PacketBox(
    							S_PacketBox.MSG_DUEL, 
    							pc.getFightId(), pc.getId()));
    				}
    				break;
    				
    			case 653: // 若你離婚，你的結婚戒指將會消失。你決定要離婚嗎？(Y/N)
    				c = this.readC();
    				final L1PcInstance target653 = (L1PcInstance) World.get().findObject(pc.getPartnerId());
    				if (c == 0) { // No
    					return;
    				} else if (c == 1) { // Yes
    					if (target653 != null) {
    						target653.setPartnerId(0); // 設定結婚戒指編號為 0
    						target653.save();
    						target653.sendPackets(new S_ServerMessage(662)); // \f1你(妳)目前未婚。
    					} else {
    						CharacterTable.get();
    						CharacterTable.updatePartnerId(pc.getPartnerId());			
    					}
    				}			
    				pc.setPartnerId(0);
    				pc.save(); // 將玩家資料儲存到資料庫中
    				pc.sendPackets(new S_ServerMessage(662)); // \f1你(妳)目前未婚。
    				break;
    				
    			case 654: // %0 向你(妳)求婚，你(妳)答應嗎?
    				c = this.readC();
    				final L1PcInstance partner = (L1PcInstance) World.get().findObject(pc.getTempID());
    				pc.setTempID(0);
    				if (partner != null) {
    					if (c == 0) { // No
    						// 656 %0 拒絕你(妳)的求婚。
    						partner.sendPackets(new S_ServerMessage(656, pc.getName()));
    						
    					} else if (c == 1) { // Yes
    						pc.setPartnerId(partner.getId());
    						pc.save();
    						pc.sendPackets(new S_ServerMessage(790)); // 倆人的結婚在所有人的祝福下完成						
    						pc.sendPackets(new S_ServerMessage(655, partner.getName())); // 恭喜!! %0
    																					// 已接受你(妳)的求婚。
    						partner.setPartnerId(pc.getId());
    						partner.save();
    						partner.sendPackets(new S_ServerMessage(790));// 倆人的結婚在所有人的祝福下完成
    						partner.sendPackets(new S_ServerMessage(655, pc.getName())); // 恭喜!! %0  已接受你(妳)的求婚。
    								
    						/** [原碼] 結婚公告 */
    						for (L1PcInstance allpc : World.get().getAllPlayers()) {
    							if (allpc != null) {
    								allpc.sendPackets(new S_SystemMessage("恭喜 "
//    								allpc.sendPackets(new S_PacketBoxGree("\\fI恭喜 "
    										+ pc.getName() + " 與 " + partner.getName()
    										+ " 結為夫妻。"));
    								allpc.sendPackets(new S_SystemMessage(
//    								allpc.sendPackets(new S_PacketBoxGree(
    										"\\fX讓我們一起為這對新人送上最真摯的祝福。"));
    							}
    						}
    					}
    				}
    				break;				
    			case 729: // 盟主正在呼喚你，你要接受他的呼喚嗎？(Y/N)
    				c = this.readC();
                    if (c == 0) {// No
                        final int objId = pc.getTempID();
                        final Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                        for (final L1PcInstance each : allPc) {
                            if (each.getId() == objId) {
                                each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                break;
                            }
                        }
                        if (pc.getcallclanal() > 0) {
                            pc.setcallclanal(0);
                        }
                        pc.setTempID(0);
                        break;
                    }   
                    else if (c == 1) { // Yes
                    		if (pc.getcallclanal() > 0) {

                    			if (!pc.getInventory().checkItem(ConfigOther.target_clan_itemid, ConfigOther.target_clan_count)) {
                    				pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg2));
                    				return;
                    			}
                    			if (!pc.getMap().isClanPc()) {
                    				pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                    				return;
                    			}
                    			if (isInWarAreaAndWarTime(pc)) {
                    				pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                    				return;
                    			}
                    			this.callClan1(pc);

                    		} else {
                    			this.callClan(pc);
                    		}
                    	}
                    	break;
                    
                case 4001: 
                	c = this.readC();
                    if (c == 0) {
                        final int objId = pc.getTempID();
                        final Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                        for (final L1PcInstance each : allPc) {
                            if (each.getId() == objId) {
                                each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                break;
                            }
                        }
                        if (pc.getcallclana2() > 0) {
                            pc.setcallclana2(0);
                        }
                        pc.setTempID(0);
                        break;
                    }
                    
                    if (c != 1 || pc.getcallclana2() <= 0) {
                        break;
                    }
                    
                    if (c == 1 && pc.getcallclana2() > 0) {
                    
                    if (!pc.getInventory().checkItem(ConfigOther.target_clan_itemid2, ConfigOther.target_clan_count2)) {
                        pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg16));
                        return;
                    }
                    if (!pc.getMap().isClanPc()) {
                        pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                        return;
                    }
                    if (isInWarAreaAndWarTime(pc)) {
                        pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                        return;
                    }
    					this.callClan2(pc);
                    }
                    break;

                case 4000: 
                	c = this.readC();
                    if (c == 0) {
                        final int objId = pc.getTempID();
                        final Collection<L1PcInstance> allPc = World.get().getAllPlayers();
                        for (final L1PcInstance each : allPc) {
                            if (each.getId() == objId) {
                                each.sendPackets(new S_ServerMessage("對方拒絕了你的呼喚。"));
                                break;
                            }
                        }
                        if (pc.getcallclanal() > 0) {
                            pc.setcallclanal(0);
                        }
                        pc.setTempID(0);
                        break;
                    }
                    if (c != 1 || pc.getcallclanal() <= 0) {
                        break;
                    }
                    
                    if (c == 1) {
                    
                    if (!pc.getInventory().checkItem(ConfigOther.target_party_itemid, ConfigOther.target_party_count)) {
                        pc.sendPackets(new S_ServerMessage(ConfigOther.clanmsg6));
                        return;
                    }
                    if (!pc.getMap().isClanPc()) {
                        pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
                        return;
                    }
                    if (isInWarAreaAndWarTime(pc)) {
                        pc.sendPackets(new S_ServerMessage("旗子內禁止使用"));
                        return;
                    }
    					this.callpartyall(pc);
                    }
                    break;
                
                case 738: // 恢復經驗值需消耗%0金幣。想要恢復經驗值嗎?
    				c = this.readC();
    				if (c == 0) { // No
    					;
    				} else if ((c == 1) && (pc.getExpRes() == 1)) { // Yes
    					int cost = 0;
    					final int level = pc.getLevel();
    					final int lawful = pc.getLawful();
    					if (level < 45) {
    						cost = level * level * 100;
    						
    					} else {
    						cost = level * level * 200;
    					}
    					
    					if (lawful >= 0) {
    						cost = (cost / 2);
    					}
    					
    					if (pc.getInventory().consumeItem(L1ItemId.ADENA, cost)) {
    						pc.resExp();
    						pc.setExpRes(0);
    						
    					} else {
    						// 189 \f1金幣不足。
    						pc.sendPackets(new S_ServerMessage(189));
    					}
    				}
    				break;
    				
                case 748: 
                    if (pc.hasSkillEffect(1692)) {
                    	c = this.readC();
                        if (c == 0) {
                            break;
                        }
                        if (c != 1) {
                            break;
                        }
                        if (pc.isParalyzed_guaji()) {
                            return;
                        }
                        final int k = pc.getTeleportX();
                        final int m = pc.getTeleportY();
                        final short s = pc.getTeleportMapId();
                        L1Teleport.teleport(pc, k, m, s, 5, true);
                        break;
                    }
                    else {
                        if (pc.isParalyzed_guaji()) {
                            pc.sendPackets(new S_Paralysis(7, false));
                            return;
                        }
                        final int newX = pc.getTeleportX();
                        final int newY = pc.getTeleportY();
                        final short mapId = pc.getTeleportMapId();
                        L1Teleport.teleport(pc, newX, newY, mapId, 5, true);
                    }
                    break;
                case 951: // 您要接受玩家 %0%s 提出的隊伍對話邀請嗎？(Y/N)
    				c = this.readC();
    				final L1PcInstance chatPc = (L1PcInstance) World.get().findObject(pc.getPartyID());
    				if (chatPc != null) {
    					if (c == 0) { // No
    						// 423 %0%s 拒絕了您的邀請。
    						chatPc.sendPackets(new S_ServerMessage(423, pc.getName()));
    						pc.setPartyID(0);
    						
    					} else if (c == 1) { // Yes
    						if (chatPc.isInChatParty()) {
    							if (chatPc.getChatParty().isVacancy() || chatPc.isGm()) {
    								chatPc.getChatParty().addMember(pc);
    								
    							} else {
    								// 417 你的隊伍已經滿了，無法再接受隊員。
    								chatPc.sendPackets(new S_ServerMessage(417));
    							}
    							
    						} else {
    							final L1ChatParty chatParty = new L1ChatParty();
    							chatParty.addMember(chatPc);
    							chatParty.addMember(pc);
    							// 424 %0%s 加入了您的隊伍。
    							chatPc.sendPackets(new S_ServerMessage(424, pc.getName()));
    						}
    					}
    				}
    				break;
    				
                case 953: // 玩家 %0%s 邀請您加入隊伍？(Y/N)
    				c = this.readC();
    				final L1PcInstance target = (L1PcInstance) World.get().findObject(pc.getPartyID());
    				if (target != null) {
    					if (c == 0) {// No
    						// 423 %0%s 拒絕了您的邀請。
    						target.sendPackets(new S_ServerMessage(423, pc.getName()));
    						pc.setPartyID(0);
    						
    					} else if (c == 1) {// Yes
    						if (target.isInParty()) {
    							// 邀請加入者 已成立隊伍
    							if (target.getParty().isVacancy()) {
    								// 加入新的隊伍成員
    								target.getParty().addMember(pc);
    								
    							} else {
    								// 417：你的隊伍已經滿了，無法再接受隊員。  
    								target.sendPackets(new S_ServerMessage(417));
    							}
    							
    						} else {
    							// 邀請加入者 尚未成立隊伍
    							final L1Party party = new L1Party();
    							party.addMember(target);// 第一個加入隊伍者將為隊長
    							party.addMember(pc);
    							// 424：%0%s 加入了您的隊伍。  
    							target.sendPackets(new S_ServerMessage(424, pc.getName()));
    						}
    					}
    				}
    				break;
    				
    			case 479: // 你想提昇那一種屬性?
    				c = this.readC();
    				if (c == 1) {
    					final String s = this.readS();
    					if (!(pc.getLevel() - 50 > pc.getBonusStats())) {
    						return;
    					}
    					if (s.equalsIgnoreCase("str")) {
    						if (pc.getBaseStr() < ConfigAlt.POWER) {
    							pc.addBaseStr((byte) 1); // 素のSTR値に+1
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    						
    					} else if (s.equalsIgnoreCase("dex")) {
    						if (pc.getBaseDex() < ConfigAlt.POWER) {
    							pc.addBaseDex((byte) 1); // 素のDEX値に+1
    							pc.resetBaseAc();
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    						
    					} else if (s.equalsIgnoreCase("con")) {
    						if (pc.getBaseCon() < ConfigAlt.POWER) {
    							pc.addBaseCon((byte) 1); // 素のCON値に+1
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));												
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    						
    					} else if (s.equalsIgnoreCase("int")) {
    						if (pc.getBaseInt() < ConfigAlt.POWER) {
    							pc.addBaseInt((byte) 1); // 素のINT値に+1
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    						
    					} else if (s.equalsIgnoreCase("wis")) {
    						if (pc.getBaseWis() < ConfigAlt.POWER) {
    							pc.addBaseWis((byte) 1); // 素のWIS値に+1
    							pc.resetBaseMr();
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    						
    					} else if (s.equalsIgnoreCase("cha")) {
    						if (pc.getBaseCha() < ConfigAlt.POWER) {
    							pc.addBaseCha((byte) 1); // 素のCHA値に+1
    							pc.setBonusStats(pc.getBonusStats() + 1);
    							pc.sendPackets(new S_OwnCharStatus2(pc));
    							pc.save(); // 人物資料記錄
    							
    						} else {
    							pc.sendPackets(new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWER +"。 請重試一次。"));
    						}
    					}
                    if (pc.power()) {
                        pc.sendPackets(new S_Bonusstats(pc.getId()));
                        break;
                    	}
    				}
                    break;

                case 3312: //76級戒指欄位
                	c = readC();
                    if (c == 1) {
                        pc.getInventory().consumeItem(ConfigOther.checkitem76, ConfigOther.checkitemcount76);
                        pc.getQuest().set_step(58003, 1);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                    }
                    break;

                case 3313: //81級戒指欄位
                	c = readC();
                    if (c == 1) {
                        pc.getInventory().consumeItem(ConfigOther.checkitem81, ConfigOther.checkitemcount81);
                        pc.getQuest().set_step(58002, 1);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                    }
                    break;
                    
                case 3589: // 是否開啟飾品欄位
                	c = readC();
                    if (c == 1) {
                        pc.getInventory().consumeItem(ConfigOther.checkitem59, ConfigOther.checkitemcount59);
                        pc.getQuest().set_step(58001, 1);
                        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "slot9"));
                    }
                    break;
                    
                case 1268: // TODO 死亡競賽系統
                    if (pc.getLevel() <= 29 || pc.getLevel() >= 52) {
                        pc.getLevel();
                    }
                    break;
                default:
    				break;
            }
        }
		catch (Exception ex) {
			//_log.error(e.getLocalizedMessage(), e);
        }
		finally {
			this.over();
		}
		this.over();
    }
    
	
	private void leaveClan(L1PcInstance leavePc, boolean isApproved) {
		String clan_name = leavePc.getClanname();
		L1Clan clan = WorldClan.get().getClan(clan_name);
		L1PcInstance clanMember[] = clan.getOnlineClanMember();
		for (int i = 0; i < clanMember.length; i++) {
			clanMember[i].sendPackets(new S_ServerMessage(178, leavePc
					.getName(), clan_name));
			// \f1%0が%1血盟を脱退しました。
		}
		if (clan.getWarehouseUsingChar() == leavePc.getId()) { // 血盟成員使用血盟倉庫中
			clan.setWarehouseUsingChar(0); // 移除使用血盟倉庫的成員
		}
		try {
			long time = 0; // 再加入時間(ミリ秒)
			if (isApproved) { // 君主または副君主による承認済の脱退			
				time = 0;
			}
//			else { // 任意脱退
//				time = 60 * 60 * 3 * 1000; // 3小時
//			}
			
			leavePc.setClanid(0);
			leavePc.setClanname("");
			leavePc.setClanRank(0);
			leavePc.setClanMemberId(0);
			leavePc.setClanMemberNotes("");
			leavePc.setTitle("");
			leavePc.sendPacketsAll(new S_CharTitle(leavePc.getId(), ""));							
			leavePc.sendPacketsAll(new S_CharReset(leavePc.getId(), 0));	
			leavePc.save(); // 儲存玩家資料到資料庫中	
			L1PcUnlock.Pc_Unlock(leavePc);//原地更新畫面
			clan.delMemberName(leavePc.getName());//移出血盟成員列表
			ClanMembersTable.getInstance().deleteMember(leavePc.getId());//刪除血盟成員資料
			leavePc.setRejoinClanTime(new Timestamp(System.currentTimeMillis() + time));		
			
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}		
	}
    
	private static void renamePet(final L1PetInstance pet, final String name) {
		if ((pet == null) || (name == null)) {
			throw new NullPointerException();
		}

		final int petItemObjId = pet.getItemObjId();
		final L1Pet petTemplate = PetReading.get().getTemplate(petItemObjId);
		if (petTemplate == null) {
			throw new NullPointerException();
		}

		final L1PcInstance pc = (L1PcInstance) pet.getMaster();
		if (PetReading.get().isNameExists(name)) {
			// 327 同樣的名稱已經存在。
			pc.sendPackets(new S_ServerMessage(327));
			return;
		}
		
		final L1Npc l1npc = NpcTable.get().getTemplate(pet.getNpcId());
		if (!(pet.getName().equalsIgnoreCase(l1npc.get_name()))) {
			// 326 一旦你已決定就不能再變更。
			pc.sendPackets(new S_ServerMessage(326));
			return;
		}
//		final L1Npc l1npc = NpcTable.get().getTemplate(pet.getNpcId());
//		if (!(pet.getName().equalsIgnoreCase(l1npc.get_name()))) {
//			if (pc.getTemporary() != 6) {
//				// 一旦你已決定就不能再變更。
//				pc.sendPackets(new S_ServerMessage(326));
//				return;
//			} else {
//				pc.setTemporary(0);
//			}
//		}
		
		pet.setName(name);
		petTemplate.set_name(name);
		PetReading.get().storePet(petTemplate);
		
		final L1ItemInstance item = pc.getInventory().getItem(pet.getItemObjId());
		pc.getInventory().updateItem(item);
		pc.sendPacketsAll(new S_ChangeName(pet.getId(), name));
	}
    
    private void callClan(final L1PcInstance pc) {
        final L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getTempID());
        pc.setTempID(0);
        if (pc.isParalyzedX()) {
            return;
        }
        if (callClanPc == null) {
            return;
        }
        if (!pc.getMap().isEscapable() && !pc.isGm()) {
            pc.sendPackets(new S_ServerMessage(647));
            return;
        }
        if (pc.getId() != callClanPc.getCallClanId()) {
            return;
        }
        boolean isInWarArea = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
        if (castleId != 0) {
            isInWarArea = true;
            if (ServerWarExecutor.get().isNowWar(castleId)) {
                isInWarArea = false;
            }
        }
        final short mapId = callClanPc.getMapId();
        if ((mapId != 0 && mapId != 4 && mapId != 304) || isInWarArea) {
            pc.sendPackets(new S_ServerMessage(629));
            return;
        }
        if (QuestMapTable.get().isQuestMap(pc.getMapId())) {
            pc.sendPackets(new S_ServerMessage(629));
            return;
        }
        final int[] HEADING_TABLE_X = { 0, 1, 1, 1, -1, -1, -1 };
        final int[] HEADING_TABLE_Y = { -1, -1, 1, 1, 1, -1 };
        Random random = new Random();
        final L1Map map = callClanPc.getMap();
        int locX = callClanPc.getX() + random.nextInt(3) - 1;
        int locY = callClanPc.getY() + random.nextInt(3) - 1;
        int heading = callClanPc.getCallClanHeading();
        locX += HEADING_TABLE_X[heading];
        locY += HEADING_TABLE_Y[heading];
        heading = (heading + 4) % 4;
        boolean isExsistCharacter = false;
        for (final L1Object object : World.get().getVisibleObjects(callClanPc, 1)) {
            if (object instanceof L1Character) {
                final L1Character cha = (L1Character)object;
                if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                    isExsistCharacter = true;
                    break;
                }
                continue;
            }
        }
        if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
            pc.sendPackets(new S_ServerMessage(627));
            return;
        }
        L1Teleport.teleport(pc, locX, locY, mapId, heading, true, 3);
    }
    
    
    private void callClan1(final L1PcInstance pc) {//血盟穿雲劍
        final L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclanal());
        pc.setcallclanal(0);
//        if (pc.isParalyzedX()) {
//            return;
//        }
//        if (callClanPc == null) {
//            return;
//        }
//        if (pc.isParalyzedX()) {
//            return;
//        }
//        
//        if (callClanPc.getClanid() != pc.getClanid()) {
//            return;
//        }
        
        if (pc.isParalyzedX() || callClanPc == null || callClanPc.getClanid() != pc.getClanid()) {
        	pc.sendPackets(new S_ServerMessage("所在地無法進行傳送"));
            return;
        }
        
        boolean isInWarArea = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
        if (castleId != 0) {
            isInWarArea = true;
            if (ServerWarExecutor.get().isNowWar(castleId)) {
                isInWarArea = false;
            }
        }
        if (isInWarArea) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }
        if (isInWarAreaAndWarTime(callClanPc)) {
            pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
            return;
        }
        if (!callClanPc.getMap().isClanPc()) {
            pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
            return;
        }
//        for (final L1PcInstance tgpc : World.get().getAllPlayers()) {
//            if (callClanPc.getLocation().isInScreen(tgpc.getLocation()) && callClanPc.getClanid() != tgpc.getClanid()) {
//                pc.sendPackets(new S_ServerMessage("偵測到召集人周圍有其他血盟成員,無法傳送。"));
//                return;
//            }
//        }

        
//        for (L1Object object : World.get().getVisibleObjects((L1Object)callClanPc, 9)) {//距離格數
//            if (object instanceof L1PcInstance) {
//              L1PcInstance tgpc = (L1PcInstance)object;
//              if (!tgpc.isGm() && tgpc.getClanid() != 0 && callClanPc.getClanid() != tgpc.getClanid()) {
//                pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到召集人周圍有其他血盟成員,無法傳送。"));
//                return;
//              } 
//            } 
//         }
        
        //排除旅館房間
        if (QuestMapTable.get().isQuestMap(pc.getMapId()) && pc.getMapId() != 16384 && pc.getMapId() != 16896 && pc.getMapId() != 17408 && pc.getMapId() != 17920 && pc.getMapId() != 18432 && pc.getMapId() != 18944 && pc.getMapId() != 19456 && pc.getMapId() != 19968 && pc.getMapId() != 20480 && pc.getMapId() != 20992 && pc.getMapId() != 21504 && pc.getMapId() != 22016 && pc.getMapId() != 22528 && pc.getMapId() != 23040 && pc.getMapId() != 23552 && pc.getMapId() != 24064 && pc.getMapId() != 24576 && pc.getMapId() != 25088) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }

        pc.getInventory().consumeItem(ConfigOther.target_clan_itemid, ConfigOther.target_clan_count);
//        final int[] HEADING_TABLE_X = { 0, 1, 1, 1, -1, -1, -1 };
//        final int[] HEADING_TABLE_Y = { -1, -1, 1, 1, 1, -1 };
        Random random = new Random();
        int randomInt = random.nextInt(5) - 2;

        // 這裡使用 C_Attr.java 中的方法取得血盟成員的初始座標和地圖編號
        int locX = C_Attr.getInitialLocX(callClanPc.getId()) + randomInt;
        int locY = C_Attr.getInitialLocY(callClanPc.getId()) + randomInt;
        short mapId = C_Attr.getInitialMapId(callClanPc.getId());
        final L1Map map = C_Attr.getInitialMap(callClanPc.getId());
        int heading = C_Attr.getInitialHeading(callClanPc.getId());
//        System.err.println("Execute method - locX: " + locX + ", locY: " + locY + ", mapId: " + mapId);
        
//        final L1Map map = initialMap;
//        int locX = initialLocX + random.nextInt(3) - 1;
//        int locY = initialLocY + random.nextInt(3) - 1;
//        short mapId = initialMapId;

//        final L1Map map = callClanPc.getMap();
//        int locX = callClanPc.getX() + random.nextInt(3) - 1;
//        int locY = callClanPc.getY() + random.nextInt(3) - 1;
//        final short mapId = callClanPc.getMapId();
//        int heading = callClanPc.getCallClanHeading();
//        locX += HEADING_TABLE_X[heading];
//        locY += HEADING_TABLE_Y[heading];
        
        heading = (heading + 4) % 4;
        
        boolean isExsistCharacter = false;
        for (final L1Object object : World.get().getVisibleObjects(callClanPc, 1)) {
            if (object instanceof L1Character) {
                final L1Character cha = (L1Character)object;
                if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                    isExsistCharacter = true;
                    break;
                }
                continue;
            }
        }
        
        if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
//        	locX = callClanPc.getX();
//    		locY = callClanPc.getY();
        	locX = C_Attr.getInitialLocX(callClanPc.getId());
            locY = C_Attr.getInitialLocY(callClanPc.getId());
//          pc.sendPackets(new S_ServerMessage(627));
        }
        
        L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
    }
    
    private void callClan2(final L1PcInstance pc) {//聯盟穿雲劍
        final L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclana2());
        pc.setcallclana2(0);
        
        if (pc.isParalyzedX() || callClanPc == null || callClanPc.getClanid() != pc.getClanid()) {
            return;
        }
        
//        if (pc.isParalyzedX()) {
//            return;
//        }
//        if (callClanPc == null) {
//            return;
//        }
//        if (pc.isParalyzedX()) {
//            return;
//        }
//        
//        if (callClanPc.getClanid() != pc.getClanid()) {
//            return;
//        }
        
        boolean isInWarArea = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
        if (castleId != 0) {
            isInWarArea = true;
            if (ServerWarExecutor.get().isNowWar(castleId)) {
                isInWarArea = false;
            }
        }
        if (isInWarArea) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }
        if (isInWarAreaAndWarTime(callClanPc)) {
            pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
            return;
        }
        if (!callClanPc.getMap().isClanPc()) {
            pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
            return;
        }
//        for (final L1PcInstance tgpc : World.get().getAllPlayers()) {
//            if (callClanPc.getLocation().isInScreen(tgpc.getLocation()) && callClanPc.getClanid() != tgpc.getClanid()) {
//                pc.sendPackets(new S_ServerMessage("偵測到召集人周圍有其他血盟成員,無法傳送。"));
//                return;
//            }
//        }
        
//        for (L1Object object : World.get().getVisibleObjects((L1Object)callClanPc, 9)) {//距離格數
//            if (object instanceof L1PcInstance) {
//              L1PcInstance tgpc = (L1PcInstance)object;
//              if (!tgpc.isGm() && tgpc.getClanid() != 0 && callClanPc.getClanid() != tgpc.getClanid()) {
//                pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到召集人周圍有其他血盟成員,無法傳送。"));
//                return;
//              } 
//            } 
//         }
        
        if (QuestMapTable.get().isQuestMap(pc.getMapId()) && pc.getMapId() != 16384 && pc.getMapId() != 16896 && pc.getMapId() != 17408 && pc.getMapId() != 17920 && pc.getMapId() != 18432 && pc.getMapId() != 18944 && pc.getMapId() != 19456 && pc.getMapId() != 19968 && pc.getMapId() != 20480 && pc.getMapId() != 20992 && pc.getMapId() != 21504 && pc.getMapId() != 22016 && pc.getMapId() != 22528 && pc.getMapId() != 23040 && pc.getMapId() != 23552 && pc.getMapId() != 24064 && pc.getMapId() != 24576 && pc.getMapId() != 25088) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }
        pc.getInventory().consumeItem(ConfigOther.target_clan_itemid2, ConfigOther.target_clan_count2);
//        final int[] HEADING_TABLE_X = { 0, 1, 1, 1, -1, -1, -1 };
//        final int[] HEADING_TABLE_Y = { -1, -1, 1, 1, 1, -1 };
        Random random = new Random();
        int randomInt = random.nextInt(5) - 2;

        // 這裡使用 C_Attr.java 中的方法取得血盟成員的初始座標和地圖編號
        int locX = C_Attr.getInitialLocX(callClanPc.getId()) + randomInt;
        int locY = C_Attr.getInitialLocY(callClanPc.getId()) + randomInt;
        short mapId = C_Attr.getInitialMapId(callClanPc.getId());
        final L1Map map = C_Attr.getInitialMap(callClanPc.getId());
        int heading = C_Attr.getInitialHeading(callClanPc.getId());
        
//        final L1Map map = initialMap;
//        int locX = initialLocX + random.nextInt(3) - 1;
//        int locY = initialLocY + random.nextInt(3) - 1;
//        short mapId = initialMapId;

//        final L1Map map = callClanPc.getMap();
//        int locX = callClanPc.getX() + random.nextInt(3) - 1;
//		int locY = callClanPc.getY() + random.nextInt(3) - 1;
//		final short mapId = callClanPc.getMapId();
//        int heading = callClanPc.getCallClanHeading();
//        locX += HEADING_TABLE_X[heading];
//        locY += HEADING_TABLE_Y[heading];
        
        heading = (heading + 4) % 4;
        
        boolean isExsistCharacter = false;
        for (final L1Object object : World.get().getVisibleObjects(callClanPc, 1)) {
            if (object instanceof L1Character) {
                final L1Character cha = (L1Character)object;
                if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                    isExsistCharacter = true;
                    break;
                }
                continue;
            }
        }
        if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
//        	locX = callClanPc.getX();
//    		locY = callClanPc.getY();
        	locX = C_Attr.getInitialLocX(callClanPc.getId());
            locY = C_Attr.getInitialLocY(callClanPc.getId());
//            pc.sendPackets(new S_ServerMessage(627));
        }
        
        L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
    }
    
    private void callpartyall(final L1PcInstance pc) {//隊伍穿雲劍
        final L1PcInstance callClanPc = (L1PcInstance)World.get().findObject(pc.getcallclanal());
        pc.setcallclanal(0);
        if (pc.isParalyzedX()) {
            return;
        }
        if (callClanPc == null) {
            return;
        }
        if (pc.isParalyzedX()) {
            return;
        }
        boolean isInWarArea = false;
        final int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);
        if (castleId != 0) {
            isInWarArea = true;
            if (ServerWarExecutor.get().isNowWar(castleId)) {
                isInWarArea = false;
            }
        }
        if (isInWarArea) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }
        if (QuestMapTable.get().isQuestMap(pc.getMapId()) && pc.getMapId() != 16384 && pc.getMapId() != 16896 && pc.getMapId() != 17408 && pc.getMapId() != 17920 && pc.getMapId() != 18432 && pc.getMapId() != 18944 && pc.getMapId() != 19456 && pc.getMapId() != 19968 && pc.getMapId() != 20480 && pc.getMapId() != 20992 && pc.getMapId() != 21504 && pc.getMapId() != 22016 && pc.getMapId() != 22528 && pc.getMapId() != 23040 && pc.getMapId() != 23552 && pc.getMapId() != 24064 && pc.getMapId() != 24576 && pc.getMapId() != 25088) {
            pc.sendPackets(new S_ServerMessage(626));
            return;
        }
        if (isInWarAreaAndWarTime(callClanPc)) {
            pc.sendPackets(new S_ServerMessage("對方旗子內,無法傳送"));
            return;
        }
        if (!callClanPc.getMap().isPartyPc()) {
            pc.sendPackets(new S_ServerMessage("所在地圖無法進行傳送"));
            return;
        }
//        for (L1Object object : World.get().getVisibleObjects((L1Object)pc, 13)) {//距離格數
//            if (object instanceof L1PcInstance) {
//              L1PcInstance tgpc = (L1PcInstance)object;
//              if (callClanPc.getLocation().isInScreen(tgpc.getLocation()) && callClanPc.getClanid() != tgpc.getClanid()) {
//                pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到召集人周圍有其他血盟成員,無法使用。"));
//                
//                return;
//              } 
//            } 
//         }
        pc.getInventory().consumeItem(ConfigOther.target_party_itemid, ConfigOther.target_party_count);
//        final int[] HEADING_TABLE_X = { 0, 1, 1, 1, -1, -1, -1 };
//        final int[] HEADING_TABLE_Y = { -1, -1, 1, 1, 1, -1 };
        Random random = new Random();
        int randomInt = random.nextInt(5) - 2;
        
        // 這裡使用 C_Attr.java 中的方法取得血盟成員的初始座標和地圖編號
        int locX = C_Attr.getInitialLocX(callClanPc.getId()) + randomInt;
        int locY = C_Attr.getInitialLocY(callClanPc.getId()) + randomInt;
        short mapId = C_Attr.getInitialMapId(callClanPc.getId());
        final L1Map map = C_Attr.getInitialMap(callClanPc.getId());
        int heading = C_Attr.getInitialHeading(callClanPc.getId());
        
//        final L1Map map = initialMap;
//        int locX = initialLocX + random.nextInt(3) - 1;
//        int locY = initialLocY + random.nextInt(3) - 1;
//        short mapId = initialMapId;
        
//        final L1Map map = callClanPc.getMap();
//        int locX = callClanPc.getX() + random.nextInt(3) - 1;
//		int locY = callClanPc.getY() + random.nextInt(3) - 1;
//		final short mapId = callClanPc.getMapId();       
//        int heading = callClanPc.getCallClanHeading();
//        locX += HEADING_TABLE_X[heading];
//        locY += HEADING_TABLE_Y[heading];
        
        heading = (heading + 4) % 4;
        
        boolean isExsistCharacter = false;
        for (final L1Object object : World.get().getVisibleObjects(callClanPc, 1)) {
            if (object instanceof L1Character) {
                final L1Character cha = (L1Character)object;
                if (cha.getX() == locX && cha.getY() == locY && cha.getMapId() == mapId) {
                    isExsistCharacter = true;
                    break;
                }
                continue;
            }
        }
        if ((locX == 0 && locY == 0) || !map.isPassable(locX, locY, null) || isExsistCharacter) {
//        	locX = callClanPc.getX();
//    		locY = callClanPc.getY();
        	locX = C_Attr.getInitialLocX(callClanPc.getId());
            locY = C_Attr.getInitialLocY(callClanPc.getId());
//            pc.sendPackets(new S_ServerMessage(627));
        }
        L1Teleport.teleport(pc, locX, locY, mapId, heading, false);
    }
    
    
    private static boolean isInWarAreaAndWarTime(final L1PcInstance pc) {
        final int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
	private class KickPc implements Runnable {
		
		private ClientExecutor _client;
		
		private KickPc(L1PcInstance pc) {
			_client = pc.getNetConnection();
		}
		
		private void start_cmd() {
			GeneralThreadPool.get().execute(this);
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				_client.kick();
				
			} catch (InterruptedException e) {
                C_Attr._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
			}
		}
	}
}
