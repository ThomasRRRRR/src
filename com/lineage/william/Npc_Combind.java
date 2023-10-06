package com.lineage.william;

import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.HeChengTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_PacketBoxGree1;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.templates.L1HeCheng;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.world.World;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Npc_Combind {
    public static final String TOKEN = ",";
    private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
    private static boolean NO_GET_DATA = false;

    public static boolean forNpcQuest(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
        if (!NO_GET_DATA) {
            NO_GET_DATA = true;
            getData();
        }
        for (int i = 0; i < aData.size(); i++) {
            ArrayList aTempData = aData.get(i);
            Random _random = new Random();
            if (aTempData.get(0) != null && ((Integer) aTempData.get(0)).intValue() == npcid && ((String) aTempData.get(1)).equals(s)) {
                ArrayList<Integer> cousumedolls = new ArrayList<>();
                boolean enough = false;
                int[] olddolls = (int[]) aTempData.get(2);
                int[] giveItem = (int[]) aTempData.get(5);
                int giveItemGet = giveItem[RandomArrayList.getInt(giveItem.length)];
                int needcount = ((Integer) aTempData.get(3)).intValue();
                for (int i2 : olddolls) {
                    L1ItemInstance[] dolls = pc.getInventory().findItemsId1(i2);
                    if (dolls != null) {
                        for (L1ItemInstance l1ItemInstance : dolls) {
                            cousumedolls.add(Integer.valueOf(l1ItemInstance.getItemId()));
                            if (cousumedolls.size() == needcount) {
                                break;
                            }
                            if (!pc.getDolls().isEmpty()) {
                                for (Object obj : pc.getDolls().values().toArray()) {
                                    L1DollInstance doll = (L1DollInstance) obj;
                                    if (doll != null) {
                                        doll.deleteDoll();
                                    }
                                }
                                pc.getDolls().clear();
                            }
                        }
                    }
                    if (cousumedolls.size() == needcount) {
                        break;
                    }
                }
                if (cousumedolls.size() == needcount) {
                    enough = true;
                    if (((Integer) aTempData.get(13)).intValue() == 1) {
                        int[] giveItem1 = (int[]) aTempData.get(5);
                        String[] text = (String[]) aTempData.get(14);
                        for (int j = 0; j < text.length; j++) {
                            try {
                                pc.sendPackets(new S_NPCTalkReturn(pc.getId(), text[j]));
//                                pc.setcomtext0(giveItem1[j]);
//                                pc.setcomtextc0(text[j]);
                                save0(pc);
                                Thread.sleep((long) ((Integer) aTempData.get(15)).intValue());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (pc.getInventory().consumeItemsIdArray(cousumedolls)) {
                        if (_random.nextInt(100) < ((Integer) aTempData.get(4)).intValue()) {
                            L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
                            
                            int itemId = item.getItem().getItemId();//對話當調用IMG
                            final StringBuilder stringBuilder = new StringBuilder();//對話當調用IMG
        					final L1HeCheng card = HeChengTable.getInstance().getTemplate(itemId);//對話當調用IMG
                        	
                            if (((String) aTempData.get(8)) != null) {
                                pc.sendPackets(new S_SystemMessage("\\fT" + ((String) aTempData.get(8))));
                            }
                            
        					if (card != null) {//列表ID空白不啟動
        						   stringBuilder.append(String.valueOf(card.getGfxid()) + ",");
        						}
                            final String[] msg = stringBuilder.toString().split(",");//從0開始分裂以逗號為單位
                            
                            item.setIdentified(true);
                            pc.getInventory().storeItem(item);
                            
/*成功跑圖*/                 pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhccg", msg));//對話當調用IMG

                            give_text(pc, item);
                            text00(pc);


                            pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item.getLogName()));
                            if (((String) aTempData.get(9)) != null) {
                                World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String) aTempData.get(9), pc.getName(), item.getLogName())));
/*跑馬燈*/   					World.get().broadcastPacketToAll(new S_PacketBoxGree1(String.format((String) aTempData.get(9), pc.getName(), item.getLogName())));
                            }
                            pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 0);
                        } else {
                            if (((Integer) aTempData.get(11)).intValue() != 0) {
                                if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 0) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 1);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 1) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 2);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 2) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 3);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 3) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 4);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 4) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 5);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 5) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 6);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 6) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 7);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 7) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 8);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 8) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 9);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 9) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 10);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 10) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 11);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 11) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 12);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 12) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 13);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 13) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 14);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 14) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 15);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 15) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 16);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 16) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 17);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 17) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 18);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 18) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 19);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 19) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 20);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 20) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 21);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 21) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 22);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 22) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 23);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 23) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 24);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 24) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 25);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 25) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 26);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 26) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 27);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 27) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 28);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 28) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 29);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 29) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 30);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 30) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 31);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 31) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 32);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 32) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 33);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 33) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 34);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 34) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 35);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 35) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 36);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 36) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 37);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 37) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 38);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 38) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 39);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 39) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 40);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 40) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 41);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 41) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 42);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 42) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 43);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 43) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 44);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 44) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 45);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 45) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 46);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 46) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 47);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 47) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 48);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 48) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 49);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 49) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 50);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 50) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 51);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 51) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 52);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 52) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 53);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 53) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 54);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 54) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 55);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 55) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 56);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 56) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 57);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 57) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 58);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 58) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 59);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 59) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 60);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 60) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 61);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 61) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 62);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 62) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 63);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 63) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 64);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 64) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 65);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 65) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 66);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 66) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 67);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 67) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 68);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 68) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 69);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 69) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 70);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 70) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 71);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 71) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 72);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 72) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 73);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 73) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 74);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 74) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 75);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 75) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 76);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 76) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 77);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 77) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 78);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 78) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 79);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 79) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 80);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 80) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 81);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 81) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 82);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 82) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 83);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 83) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 84);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 84) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 85);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 85) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 86);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 86) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 87);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 87) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 88);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 88) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 89);
                                } else if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == 89) {
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 90);
                                }
                                if (pc.getQuest().get_step(((Integer) aTempData.get(11)).intValue()) == ((Integer) aTempData.get(12)).intValue()) {
                                    if (((String) aTempData.get(8)) != null) {
                                        pc.sendPackets(new S_SystemMessage("\\fT保底" + ((String) aTempData.get(8))));
                                    }
                                    L1ItemInstance item2 = ItemTable.get().createItem(giveItemGet);
                                    item2.setIdentified(true);
                                    pc.getInventory().storeItem(item2);
                                    give_text(pc, item2);
                                    text00(pc);
                                    pc.sendPackets(new S_ServerMessage(143, npc.getNpcTemplate().get_name(), item2.getLogName()));
                                    if (((String) aTempData.get(9)) != null) {
                                        World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String) aTempData.get(9), pc.getName(), item2.getLogName())));
/*跑馬燈*/   							World.get().broadcastPacketToAll(new S_PacketBoxGree1(String.format((String) aTempData.get(9), pc.getName(), item2.getLogName())));
                                    }
                                    pc.getQuest().set_step(((Integer) aTempData.get(11)).intValue(), 0);
                                    return false;
                                }
                            }
                            if (((String) aTempData.get(7)) != null) {
                                pc.sendPackets(new S_CloseList(pc.getId()));
                                pc.sendPackets(new S_SystemMessage("\\fY" + ((String) aTempData.get(7))));
                            }	
                            

                            if (((Integer) aTempData.get(10)).intValue() == 1) {
                                L1ItemInstance item1 = ItemTable.get().createItem(cousumedolls.get(_random.nextInt(cousumedolls.size())).intValue());
                                
                                int itemId = item1.getItem().getItemId();
                                final StringBuilder stringBuilder = new StringBuilder();
                                final L1HeCheng card = HeChengTable.getInstance().getTemplate(itemId);
        						if (card != null) {//列表ID空白不啟動														
        							stringBuilder.append(String.valueOf(card.getGfxid()) + ",");
        							}
        						final String[] msg = stringBuilder.toString().split(",");//從0開始分裂以逗號為單位
                                
                                item1.setIdentified(true);
                                pc.getInventory().storeItem(item1);
                                
/*失敗跑圖*/                     pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "wwhcsb", msg));//對話當調用IMG   
                                pc.sendPackets(new S_SystemMessage("\\fY失敗退還:" + item1.getViewName()));
                                

                            }
                        }
                    }
                }
                if (!enough && ((String) aTempData.get(6)) != null) {
                    pc.sendPackets(new S_SystemMessage("\\fY" + ((String) aTempData.get(6))));
                }
            }
        }
        return false;
    }

    private static void getData() {
        try {
            Connection con = DatabaseFactory.get().getConnection();
            ResultSet rset = con.createStatement().executeQuery("SELECT * FROM w_npc天m合成");
            if (rset != null) {
                while (rset.next()) {
                    ArrayList<Object> aReturn = new ArrayList<>();
                    aReturn.add(0, new Integer(rset.getInt("npcid")));
                    aReturn.add(1, rset.getString("action"));
                    aReturn.add(2, getArray(rset.getString("合成需求編號"), ",", 1));
                    aReturn.add(3, Integer.valueOf(rset.getInt("物品合成數量")));
                    aReturn.add(4, Integer.valueOf(rset.getInt("機率")));
                    aReturn.add(5, getArray(rset.getString("獲取合成編號"), ",", 1));
                    aReturn.add(6, rset.getString("物品不足Msg"));
                    aReturn.add(7, rset.getString("失敗Msg"));
                    aReturn.add(8, rset.getString("成功Msg"));
                    aReturn.add(9, rset.getString("世界廣播"));
                    aReturn.add(10, Integer.valueOf(rset.getInt("失敗是否退還")));
                    aReturn.add(11, Integer.valueOf(rset.getInt("保底紀錄編號")));
                    aReturn.add(12, Integer.valueOf(rset.getInt("保底次數")));
                    aReturn.add(13, Integer.valueOf(rset.getInt("是否讀取跑圖")));
                    if (rset.getString("對話檔名") == null || rset.getString("對話檔名").equals("") || rset.getString("對話檔名").equals("0")) {
                        aReturn.add(14, null);
                    } else {
                        aReturn.add(14, getArray(rset.getString("對話檔名"), ",", 2));
                    }
                    aReturn.add(15, Integer.valueOf(rset.getInt("跑圖延遲毫秒")));
                    aData.add(aReturn);
                }
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    private static Object getArray(String s, String sToken, int iType) {
        StringTokenizer st = new StringTokenizer(s, sToken);
        int iSize = st.countTokens();
        if (iType == 1) {
            int[] iReturn = new int[iSize];
            for (int i = 0; i < iSize; i++) {
                iReturn[i] = Integer.parseInt(st.nextToken());
            }
            return iReturn;
        } else if (iType == 2) {
            String[] sReturn = new String[iSize];
            for (int i2 = 0; i2 < iSize; i2++) {
                sReturn[i2] = st.nextToken();
            }
            return sReturn;
        } else if (iType != 3) {
            return null;
        } else {
            String sReturn2 = null;
            for (int i3 = 0; i3 < iSize; i3++) {
                sReturn2 = st.nextToken();
            }
            return sReturn2;
        }
    }

    public static void save0(L1PcInstance pc) {
        if (pc.getcomtext0() > 0 && pc.getcomtext1() == 0) {
            pc.setcomtext1(pc.getcomtext0());
            pc.setcomtextc1(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext2() == 0) {
            pc.setcomtext2(pc.getcomtext0());
            pc.setcomtextc2(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext3() == 0) {
            pc.setcomtext3(pc.getcomtext0());
            pc.setcomtextc3(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext4() == 0) {
            pc.setcomtext4(pc.getcomtext0());
            pc.setcomtextc4(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext5() == 0) {
            pc.setcomtext5(pc.getcomtext0());
            pc.setcomtextc5(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext6() == 0) {
            pc.setcomtext6(pc.getcomtext0());
            pc.setcomtextc6(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext7() == 0) {
            pc.setcomtext7(pc.getcomtext0());
            pc.setcomtextc7(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext8() == 0) {
            pc.setcomtext8(pc.getcomtext0());
            pc.setcomtextc8(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext9() == 0) {
            pc.setcomtext9(pc.getcomtext0());
            pc.setcomtextc9(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext10() == 0) {
            pc.setcomtext10(pc.getcomtext0());
            pc.setcomtextc10(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext11() == 0) {
            pc.setcomtext11(pc.getcomtext0());
            pc.setcomtextc11(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext12() == 0) {
            pc.setcomtext12(pc.getcomtext0());
            pc.setcomtextc12(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext13() == 0) {
            pc.setcomtext13(pc.getcomtext0());
            pc.setcomtextc13(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext14() == 0) {
            pc.setcomtext14(pc.getcomtext0());
            pc.setcomtextc14(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext15() == 0) {
            pc.setcomtext15(pc.getcomtext0());
            pc.setcomtextc15(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext16() == 0) {
            pc.setcomtext16(pc.getcomtext0());
            pc.setcomtextc16(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext17() == 0) {
            pc.setcomtext17(pc.getcomtext0());
            pc.setcomtextc17(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext18() == 0) {
            pc.setcomtext18(pc.getcomtext0());
            pc.setcomtextc18(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext19() == 0) {
            pc.setcomtext19(pc.getcomtext0());
            pc.setcomtextc19(pc.getcomtextc0());
        } else if (pc.getcomtext0() > 0 && pc.getcomtext20() == 0) {
            pc.setcomtext20(pc.getcomtext0());
            pc.setcomtextc20(pc.getcomtextc0());
        }
    }

    public static void give_text(L1PcInstance pc, L1ItemInstance item) {
        if (pc.getcomtext0() > 0 && pc.getcomtext1() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc1()));
        } else if (pc.getcomtext2() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc2()));
        } else if (pc.getcomtext3() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc3()));
        } else if (pc.getcomtext4() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc4()));
        } else if (pc.getcomtext5() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc5()));
        } else if (pc.getcomtext6() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc6()));
        } else if (pc.getcomtext7() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc7()));
        } else if (pc.getcomtext8() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc8()));
        } else if (pc.getcomtext9() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc9()));
        } else if (pc.getcomtext10() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc10()));
        } else if (pc.getcomtext11() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc11()));
        } else if (pc.getcomtext12() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc12()));
        } else if (pc.getcomtext13() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc13()));
        } else if (pc.getcomtext14() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc14()));
        } else if (pc.getcomtext15() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc15()));
        } else if (pc.getcomtext16() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc16()));
        } else if (pc.getcomtext17() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc17()));
        } else if (pc.getcomtext18() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc18()));
        } else if (pc.getcomtext19() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc19()));
        } else if (pc.getcomtext20() == item.getItemId()) {
            pc.sendPackets(new S_NPCTalkReturn(pc.getId(), pc.getcomtextc20()));
        }
    }

    public static void text00(L1PcInstance pc) {
        pc.setcomtext0(0);
        pc.setcomtextc0(null);
        pc.setcomtext1(0);
        pc.setcomtextc1(null);
        pc.setcomtext2(0);
        pc.setcomtextc2(null);
        pc.setcomtext3(0);
        pc.setcomtextc3(null);
        pc.setcomtext4(0);
        pc.setcomtextc4(null);
        pc.setcomtext5(0);
        pc.setcomtextc5(null);
        pc.setcomtext6(0);
        pc.setcomtextc6(null);
        pc.setcomtext7(0);
        pc.setcomtextc7(null);
        pc.setcomtext8(0);
        pc.setcomtextc8(null);
        pc.setcomtext9(0);
        pc.setcomtextc9(null);
        pc.setcomtext10(0);
        pc.setcomtextc10(null);
        pc.setcomtext11(0);
        pc.setcomtextc11(null);
        pc.setcomtext12(0);
        pc.setcomtextc12(null);
        pc.setcomtext13(0);
        pc.setcomtextc13(null);
        pc.setcomtext14(0);
        pc.setcomtextc14(null);
        pc.setcomtext15(0);
        pc.setcomtextc15(null);
        pc.setcomtext16(0);
        pc.setcomtextc16(null);
        pc.setcomtext17(0);
        pc.setcomtextc17(null);
        pc.setcomtext18(0);
        pc.setcomtextc18(null);
        pc.setcomtext19(0);
        pc.setcomtextc19(null);
        pc.setcomtext20(0);
        pc.setcomtextc20(null);
    }
}