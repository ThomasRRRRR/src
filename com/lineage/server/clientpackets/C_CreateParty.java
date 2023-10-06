package com.lineage.server.clientpackets;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;


/**
 * 要求邀請加入隊伍(要求創立隊伍)
 *
 * @author daien
 *
 */
public class C_CreateParty extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_CreateParty.class);

	/*public C_CreateParty() {
	}

	public C_CreateParty(final byte[] abyte0, final ClientExecutor client) {
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

			final int type = this.readC();
			
			switch (type) {
			case 0:
			case 1: // パーティー(パーティー自動分配ON/OFFで異なる)
			case 4:
			case 5:
				final int targetId;
				final L1Object temp;
				if (type == 4 || type == 5) {
					String name = readS();
					L1PcInstance tar = World.get().getPlayer(name);
					temp = tar;
					targetId = tar.getId();
				} else {
					targetId = readD();
					temp = World.get().findObject(targetId);
				}

				if (temp instanceof L1PcInstance) {
					final L1PcInstance targetPc = (L1PcInstance) temp;
					if (pc.getId() == targetPc.getId()) {
						return;
					}
					
					if ((!pc.getLocation().isInScreen(targetPc.getLocation()) || (pc
							.getLocation().getTileLineDistance(
									targetPc.getLocation()) > 7))) {
						// 邀請組隊時，對象不再螢幕內或是7步內
						pc.sendPackets(new S_ServerMessage(952));
						return;
					}
					
					if (targetPc.isInParty()) {
						// 您無法邀請已經參加其他隊伍的人。
						pc.sendPackets(new S_ServerMessage(415));
						return;
					}

					if (pc.isInParty()) {
						if (pc.getParty().isLeader(pc)) {
							targetPc.setPartyID(pc.getId());
							// 玩家 %0%s 邀請您加入隊伍？(Y/N)
							targetPc.sendPackets(new S_Message_YN(953, pc.getName()));
						
						} else {
							// 只有領導者才能邀請其他的成員。
							pc.sendPackets(new S_ServerMessage(416));
						}
						
					} else {
						targetPc.setPartyID(pc.getId());
						// 玩家 %0%s 邀請您加入隊伍？(Y/N)
						targetPc.sendPackets(new S_Message_YN(953, pc.getName()));
					}
				}
				break;
				
			case 2: // チャットパーティー
				final String name = this.readS();
				final L1PcInstance targetPc = World.get().getPlayer(name);
				if (targetPc == null) {
					// 沒有叫%0的人。
					pc.sendPackets(new S_ServerMessage(109));
					return;
				}
				
				if (pc.getId() == targetPc.getId()) {
					return;
				}
				
				if ((!pc.getLocation().isInScreen(targetPc.getLocation()) || (pc
						.getLocation().getTileLineDistance(targetPc.getLocation()) > 7))) {
					// 邀請組隊時，對象不再螢幕內或是7步內
					pc.sendPackets(new S_ServerMessage(952));
					return;
				}
				
				if (targetPc.isInChatParty()) {
					// 您無法邀請已經參加其他隊伍的人。
					pc.sendPackets(new S_ServerMessage(415));
					return;
				}

				if (pc.isInChatParty()) {
					if (pc.getChatParty().isLeader(pc)) {
						targetPc.setPartyID(pc.getId());
						// 您要接受玩家 %0%s 提出的隊伍對話邀請嗎？(Y/N)
						targetPc.sendPackets(new S_Message_YN(951, pc.getName()));
					
					} else {
						// 只有領導者才能邀請其他的成員。
						pc.sendPackets(new S_ServerMessage(416));
					}
					
				} else {
					targetPc.setPartyID(pc.getId());
					// 您要接受玩家 %0%s 提出的隊伍對話邀請嗎？(Y/N)
					targetPc.sendPackets(new S_Message_YN(951, pc.getName()));
				}
				break;
				
			case 3:// 隊長轉移
				if (pc.isInParty()) {
					if (pc.getParty().isLeader(pc)) {
						final int objid = this.readD();
						final L1Object object = World.get().findObject(objid);
						if (object instanceof L1PcInstance) {
							final L1PcInstance tgpc = (L1PcInstance) object;
							// 不同地圖
							if (tgpc.getMapId() != pc.getMapId()) {
								// 1,695：要委任隊長的隊員沒在附近。  
								pc.sendPackets(new S_Message_YN(1695));
							}
							// 指定座標19格範圍內
							if (pc.getLocation().isInScreen(tgpc.getLocation())) {
								final HashMap<Integer, L1PcInstance> map = new HashMap<Integer, L1PcInstance>();
								map.putAll(pc.getParty().partyUsers());
								
								// 建立新的成員名單
								final ArrayList<L1PcInstance> newList = new ArrayList<L1PcInstance>();
								
								for (L1PcInstance newpc : map.values()) {
									// 不是新隊長 加入新成員名單
									if (!newpc.equals(tgpc)) {
										newList.add(newpc);
									}
								}
								map.clear();
								
								// 解散原隊伍
								pc.getParty().breakup();
								
								// 建立新隊伍資訊
								final L1Party party = new L1Party();
								party.addMember(tgpc);// 第一個加入隊伍者將為隊長
								for (L1PcInstance newpc : newList) {
									party.addMember(newpc);
									// 424：%0%s 加入了您的隊伍。  
									tgpc.sendPackets(new S_ServerMessage(424, newpc.getName()));
								}
								
								// 傳遞新隊長訊息
								party.msgToAll();
								newList.clear();
								
							} else {
								// 1,695：要委任隊長的隊員沒在附近。  
								pc.sendPackets(new S_ServerMessage(1695));
							}
							
						}
					} else {
						// 1,697：不是隊長，無法行使許可權。  
						pc.sendPackets(new S_ServerMessage(1697));
					}
				}
				break;
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
