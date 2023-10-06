package com.lineage.william;

import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_PacketBoxGree1;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.world.World;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;


public class ItemBox
{
  private static ArrayList<ArrayList<Object>> aData = new ArrayList<>();
  public static final HashMap<Integer, ItemBox> _questionIdIndex = new HashMap<>();

  private static boolean NO_GET_DATA = false;

  public static final String TOKEN = ",";
  
  private static ItemBox _instance;

  public static ItemBox getInstance() {
    if (_instance == null) {
      _instance = new ItemBox();
    }
    return _instance;
  }
  
  public static void reload() {
    _questionIdIndex.clear();
    _instance = null;
    getInstance();
  }
  private ItemBox() {
    getData();
  }

  public static void trueOutburst(L1PcInstance pc, L1ItemInstance item) {
    ArrayList<?> aTempData = null;
    if (!NO_GET_DATA) {
      NO_GET_DATA = true;
      getData();
    } 
    int itemid = item.getItemId();

    for (int i = 0; i < aData.size(); i++) {
      aTempData = (ArrayList)aData.get(i);

      if (((Integer)aTempData.get(0)).intValue() == itemid)
      {
        if (((Integer)aTempData.get(0)).intValue() == itemid) {
          if (((Integer)aTempData.get(1)).intValue() != 0 && pc.getLevel() < ((Integer)aTempData.get(1)).intValue()) {
            pc.sendPackets((ServerBasePacket)new S_SystemMessage("等級" + ((Integer)aTempData.get(1)).intValue() + "以上才可使用此道具。"));
            return;
          } 

//          final int[] giveItem = (int[])aTempData.get(2);
          final int[] giveCount = (int[])aTempData.get(3);
          
//          int rndItem = RandomArrayList.getInt(giveItem.length);
          int rndCount = RandomArrayList.getInt(giveCount.length);
          
//          int giveItemGet = giveItem[rndItem];
          int giveCountGet = giveCount[rndCount];
          
          
          int[] 機率 = (int[])aTempData.get(8);
          int[] 抽卡道具編號 = (int[])aTempData.get(2);
          int[] 累积機率 = new int[機率.length];
          累积機率[0] = 機率[0];

          if(機率 != null) {
          
          for (int i1 = 1; i1 < 機率.length; i1++) {
       	   累积機率[i1] = 累积機率[i1 - 1] + 機率[i1];
          }

          int sum = 累积機率[累积機率.length - 1]; // 機率的總和

          // 確保累積機率的最後一個元素等於機率總和
          累积機率[累积機率.length - 1] = sum;

          int randomNum = (int) (Math.random() * sum) + 1; // 生成隨機數

          int giveItemGet = -1;

          for (int i1 = 0; i1 < 累积機率.length; i1++) {
       	   if (randomNum < 累积機率[i1]) {
       		   giveItemGet = 抽卡道具編號[i1];
       		   break;
       	   }
          }
          
          
          L1ItemInstance item1 = ItemTable.get().createItem(giveItemGet);
          
          if (item1.isStackable()) {
            item1.setCount(giveCountGet);
          } else {
            item1.setCount(1L);
          } 
          
          item1.setIdentified(true);
          if (item1 != null) {
            if (pc.getInventory().checkAddItem(item1, giveCountGet) != 0) {
              return;
            }
            pc.getInventory().storeItem(item1);
            pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, String.valueOf(item1.getName()) + "(" + giveCountGet + ")"));
          } 
          
          if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 0) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 1);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 1) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 2);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 2) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 3);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 3) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 4);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 4) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 5);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 5) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 6);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 6) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 7);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 7) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 8);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 8) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 9);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 9) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 10);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 10) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 11);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 11) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 12);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 12) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 13);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 13) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 14);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 14) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 15);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 15) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 16);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 16) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 17);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 17) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 18);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 18) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 19);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 19) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 20);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 20) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 21);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 21) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 22);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 22) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 23);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 23) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 24);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 24) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 25);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 25) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 26);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 26) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 27);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 27) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 28);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 28) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 29);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 29) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 30);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          }
          else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 30) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 31);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 31) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 32);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 32) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 33);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 33) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 34);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 34) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 35);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 35) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 36);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 36) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 37);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 37) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 38);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 38) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 39);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 39) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 40);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 40) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 41);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 41) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 42);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 42) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 43);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 43) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 44);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 44) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 45);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 45) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 46);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 46) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 47);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 47) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 48);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 48) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 49);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 49) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 50);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 50) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 51);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          }
          else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 51) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 52);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 52) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 53);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 53) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 54);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 54) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 55);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 55) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 56);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 56) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 57);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 57) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 58);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 58) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 59);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 59) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 60);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } else if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == 60) {
            pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 61);
            checkboxcount(pc, item);
            pc.getInventory().removeItem(item, 1L);
          } 
          return;
        }
        }
      }
    } 
  }

  
  public static void checkboxcount(L1PcInstance pc, L1ItemInstance item) {
    ArrayList<?> aTempData = null;
    if (!NO_GET_DATA) {
      NO_GET_DATA = true;
      getData();
    } 

    for (int i = 0; i < aData.size(); i++) {
      aTempData = (ArrayList)aData.get(i);
 
      if (pc.getQuest().get_step(((Integer)aTempData.get(0)).intValue()) == ((Integer)aTempData.get(4)).intValue()) { 
        int[] giveItem = (int[])aTempData.get(5);
        int rndItem = RandomArrayList.getInt(giveItem.length);
        int giveItemGet = giveItem[rndItem];
        
        L1ItemInstance item1 = ItemTable.get().createItem(giveItemGet);
        item1.setCount(1L);
        item1.setIdentified(true);
        if (item1 != null) {
          if (pc.getInventory().checkAddItem(item1, 1L) != 0) {
            return;
          }
          pc.getInventory().storeItem(item1);
          pc.getQuest().set_step(((Integer)aTempData.get(0)).intValue(), 0);
          pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, String.valueOf(item1.getName()) + "(1)"));
          
          if (((Integer)aTempData.get(6)).intValue() == 1 && !pc.isGm()) {
            World.get().broadcastPacketToAll(new S_SystemMessage(String.format((String)aTempData.get(7), new Object[] { pc.getName(), item1.getLogName() })));
          }
        } 
      } 
    } 
  }

  private static void getData() {
    Connection con = null;
    try {
      con = DatabaseFactory.get().getConnection();
      Statement stat = con.createStatement();
      ResultSet rset = stat
        .executeQuery("SELECT * FROM w_道具寶相次數");
      ArrayList<Object> aReturn = null;
      if (rset != null)
        while (rset.next()) {
          aReturn = new ArrayList();
          aReturn.add(0, new Integer(rset.getInt("道具編號")));
          aReturn.add(1, new Integer(rset.getInt("等級限制")));
          aReturn.add(2, getArray(rset.getString("隨機獲取道具"), ",", 1));
          aReturn.add(3, getArray(rset.getString("隨機獲取數量"), ",", 1));
          aReturn.add(4, new Integer(rset.getInt("達滿此開箱次數")));
          aReturn.add(5, getArray(rset.getString("達滿給予物品"), ",", 1));
          aReturn.add(6, new Integer(rset.getInt("是否世界廣播")));
          aReturn.add(7, rset.getString("世界廣播文字"));
          if (rset.getString("機率") != null && !rset.getString("機率").equals("") && !rset.getString("機率").equals("0")) {
              aReturn.add(8, getArray(rset.getString("機率"), ",", 1));
            } else {
              aReturn.add(8, null);
            }
          aData.add(aReturn);
        }  
      if (con != null && !con.isClosed())
        con.close(); 
    } catch (Exception exception) {}
  }

  
  private static Object getArray(String s, String sToken, int iType) {
    StringTokenizer st = new StringTokenizer(s, sToken);
    int iSize = st.countTokens();
    String sTemp = null;
    
    if (iType == 1) {
      int[] iReturn = new int[iSize];
      for (int i = 0; i < iSize; i++) {
        sTemp = st.nextToken();
        iReturn[i] = Integer.parseInt(sTemp);
      } 
      return iReturn;
    } 
    
    if (iType == 2) {
      String[] sReturn = new String[iSize];
      for (int i = 0; i < iSize; i++) {
        sTemp = st.nextToken();
        sReturn[i] = sTemp;
      } 
      return sReturn;
    } 
    
    if (iType == 3) {
      String sReturn = null;
      for (int i = 0; i < iSize; i++) {
        sTemp = st.nextToken();
        sReturn = sTemp;
      } 
      return sReturn;
    } 
    return null;
  }
}
