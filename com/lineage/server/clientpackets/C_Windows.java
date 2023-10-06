 package com.lineage.server.clientpackets;
 
 import com.lineage.echo.ClientExecutor;
 import com.lineage.server.datatables.lock.CharBookConfigReading;
 import com.lineage.server.datatables.lock.CharBookReading;
 import com.lineage.server.datatables.lock.ClanReading;
 import com.lineage.server.model.Instance.L1PcInstance;
 import com.lineage.server.model.L1Clan;
 import com.lineage.server.model.L1Object;
 import com.lineage.server.model.L1Teleport;
 import com.lineage.server.serverpackets.S_MapTimerOut;
 import com.lineage.server.serverpackets.S_OwnCharStatus;
 import com.lineage.server.serverpackets.S_PacketBox;
 import com.lineage.server.serverpackets.S_PacketBoxLoc;
 import com.lineage.server.serverpackets.S_ServerMessage;
 import com.lineage.server.serverpackets.S_SystemMessage;
 import com.lineage.server.serverpackets.ServerBasePacket;
 import com.lineage.server.templates.L1BookConfig;
 import com.lineage.server.templates.L1BookMark;
 import com.lineage.server.utils.L1SpawnUtil;
 import com.lineage.server.world.World;
 import java.io.BufferedWriter;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.sql.Timestamp;
 import java.util.Calendar;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 
 
public class C_Windows extends ClientBasePacket {
    private static final Log _log = LogFactory.getLog(C_Windows.class);

    public void start(byte[] decrypt, ClientExecutor client) {
        try {
        	// 資料載入
        	read(decrypt);

        	L1PcInstance pc = client.getActiveChar();
        	int type = readC();

        	switch (type) {
        	case 0x00:
        		int targetId = readD();
                L1Object temp = World.get().findObject(targetId);
                if (temp instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance) temp;
                    if (pc.getId() == targetPc.getId()) {
                        pc.sendPackets(new S_ServerMessage(3130));
                    } else if (pc.getLevel() < 10) {
                        pc.sendPackets(new S_ServerMessage(3129));
                    } else {
                        for (Object visible : World.get().getAllPlayers()) {
                            if (visible instanceof L1PcInstance) {
                                L1PcInstance GM = (L1PcInstance) visible;
                                if (GM.isGm() && pc.getId() != GM.getId()) {
                                    GM.sendPackets(new S_ServerMessage("\\fW玩家" + pc.getName() + " 申訴:(" + targetPc.getName() + ")使用外掛"));
                                }
                            }
                        }
                        if (!pc.isGm()) {
                            pc.sendPackets(new S_SystemMessage("\\fT感謝您申訴，為了遊戲品質我們會嚴加監控"));
                            pc.sendPackets(new S_SystemMessage("\\fT系統立即回報給予線上GM以及備份回報!!"));
                            玩家檢舉("玩家:【 " + pc.getName() + " 】 " +
                                    "舉報玩家" + ":【 " + targetPc.getName() + " 】" +
                                    "為外掛使用者，請注意，" + "檢舉時間:" + "(" +
                                    new Timestamp(System.currentTimeMillis()) + ")。");
                        }
                    }
                } else {
                    pc.sendPackets(new S_ServerMessage(1023));
                }
                break;
                
        	case 0x06: // 龍之門扉選單
				int itemobjid = readD();
				int selectdoor = readC();
				if (pc.getInventory().getItem(itemobjid) != null) {
					switch (selectdoor) {
					case 0: // 地
						if (pc.getInventory().consumeItem(56072, 1)) {
							L1SpawnUtil.spawn(pc, 70932, 0, 7200);
						}
						break;
					case 1: // 水
						if (pc.getInventory().consumeItem(47010, 1)) {
							L1SpawnUtil.spawn(pc, 70937, 0, 7200);
						}
						break;
					case 2: // 風
						if (pc.getInventory().consumeItem(47010, 1)) {
							L1SpawnUtil.spawn(pc, 70934, 0, 7200);
						}
						break;
					case 3: // 火
						/*
						 * if (pc.getInventory().consumeItem(47010, 1)) {
						 * L1SpawnUtil.spawn(pc, 70933, 0, 7200); }
						 */
						pc.sendPackets(new S_SystemMessage("該副本尚未實裝。"));
						break;
					default:
						return;
					}
				}
				break;
				
        	case 0x0b:
				String name = readS();
				int mapid = readH();
				int x = readH();
				int y = readH();
				int zone = readD();
				L1PcInstance target = World.get().getPlayer(name);
				if (target != null) {
					target.sendPackets(new S_PacketBoxLoc(pc.getName(), mapid, x, y, zone));
					pc.sendPackets(new S_ServerMessage(1783, name));// 已發送座標位置給%0。

				} else {
					pc.sendPackets(new S_ServerMessage(1782));// 無法找到該角色或角色不在線上。
				}
				break;
				
        	case 0x22:
        		final byte[] data = this.readBytes();
                final L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
                if (config == null) {
                    CharBookConfigReading.get().storeCharBookConfig(pc.getId(), data);
                    break;
                }
                CharBookConfigReading.get().updateCharBookConfig(pc.getId(), data);
                break;
				
			case 0x27:
				int changeCount = readD();
				for (int i = 0; i < changeCount; i++) {
					int bookId = readD();
					final L1BookMark bookm = CharBookReading.get().getBookMark(pc, bookId);
					if (bookm != null) {
						String changeName = readS();
						bookm.setName(changeName);
						CharBookReading.get().updateBookmarkName(bookm);
					}
				}
				break;
        	
			case 0x2c:
				pc.setKillCount(0);
		        pc.sendPackets(new S_OwnCharStatus(pc));
		        break;
		        
			case 0x09:
				pc.sendPackets(new S_MapTimerOut(pc));
				break;
				
			case 0x2e: // 識別盟徽 狀態
				// 如果不是君主或聯盟王
				if (pc.getClanRank() != 4 && pc.getClanRank() != 10) {
					return;
				}

				int emblemStatus = readC(); // 0: 關閉 1:開啟

				L1Clan clan = pc.getClan();
				clan.setEmblemStatus(emblemStatus);
				ClanReading.get().updateClan(clan);

				for (L1PcInstance member : clan.getOnlineClanMember()) {
					member.sendPackets(new S_PacketBox(S_PacketBox.PLEDGE_EMBLEM_STATUS, emblemStatus));
				}
				break;
				
			case 0x30: // 村莊便利傳送
				int mapIndex = readH(); // 1: 亞丁 2:古魯丁 3: 奇岩
				int point = readH();
				int locx = 0;
				int locy = 0;
				if (mapIndex == 1) {
					if (point == 0) { // 亞丁-村莊北邊地區
						// X34079 Y33136 右下角 X 34090 Y 33150
						locx = 34079 + (int) (Math.random() * 12);
						locy = 33136 + (int) (Math.random() * 15);
					} else if (point == 1) { // 亞丁-村莊中心地區
						// 左上角 X 33970 Y 33243 右下角 X33979 Y33256
						locx = 33970 + (int) (Math.random() * 10);
						locy = 33243 + (int) (Math.random() * 14);
					} else if (point == 2) { // 亞丁-村莊教堂地區
						// 左上 X33925 Y33351 右下 X33938 Y33359
						locx = 33925 + (int) (Math.random() * 14);
						locy = 33351 + (int) (Math.random() * 9);
					}
				} else if (mapIndex == 2) {
					if (point == 0) { // 古魯丁-北邊地區
						// 左上 X32615 Y32719 右下 X32625 Y32725
						locx = 32615 + (int) (Math.random() * 11);
						locy = 32719 + (int) (Math.random() * 7);
					} else if (point == 1) { // 古魯丁-南邊地區
						// 左上 X32621 Y32788 右下 X32629 Y32800
						locx = 32621 + (int) (Math.random() * 9);
						locy = 32788 + (int) (Math.random() * 13);
					}
				} else if (mapIndex == 3) {
					if (point == 0) { // 奇岩-北邊地區
						// 左上 X33501 Y32765 右下 X33511 Y32773
						locx = 33501 + (int) (Math.random() * 11);
						locy = 32765 + (int) (Math.random() * 9);
					} else if (point == 1) { // 奇岩-南邊地區
						// 左上 X33440 Y32784 右下 X33450 Y32794
						locx = 33440 + (int) (Math.random() * 11);
						locy = 32784 + (int) (Math.random() * 11);
					}
				} else if (mapIndex == 4) { // 市場

					int loc[][] = { { 32838, 32886 }, { 32800, 32874 }, { 32755, 32899 }, { 32741, 32938 },
							{ 32740, 32964 }, { 32801, 32982 }, { 32845, 32986 }, { 32852, 32932 }, { 32799, 32927 } };

					locx = loc[point][0];
					locy = loc[point][1];
				}
				L1Teleport.teleport(pc, locx, locy, pc.getMapId(), pc.getHeading(), true);
				pc.sendPackets(new S_PacketBox(S_PacketBox.TOWN_TELEPORT, pc));
        	}
        } catch (Exception e) {
            C_Windows._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return;
        }
        finally {
            this.over();
        }
        this.over();
	}
    public static void 玩家檢舉(String info) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[玩家檢舉].txt", true));
            out.write(info + "\r\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}