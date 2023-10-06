package com.lineage.william;

import com.lineage.DatabaseFactory;
import com.lineage.Server;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Item;
import com.lineage.server.utils.RandomArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;










public class ItemUse
{
  private static ArrayList<ArrayList<Object>> aData15b = new ArrayList<>();

  
  private static boolean NO_MORE_GET_DATA15b = false;

  
  public static final String TOKEN = ",";


  
  public static void main(String[] a) {
    try {
      Server.main(null);
    }
    catch (Exception exception) {}
  }

  
  public static void forItemUSe(L1PcInstance user, L1ItemInstance itemInstance) {
    int itemid = itemInstance.getItemId();
    ArrayList<?> aTempData = null;
    if (!NO_MORE_GET_DATA15b) {
      
      NO_MORE_GET_DATA15b = true;
      getData15b();
    } 
    for (int i = 0; i < aData15b.size(); i++) {
      
      aTempData = aData15b.get(i);
      if (((Integer)aTempData.get(0)).intValue() == itemid) {
        
        if (((Integer)aTempData.get(1)).intValue() != 0) {
          
          byte class_id = 0;
          String msg = "";
          if (user.isCrown()) {
            class_id = 1;
          }
          else if (user.isKnight()) {
            class_id = 2;
          }
          else if (user.isWizard()) {
            class_id = 3;
          }
          else if (user.isElf()) {
            class_id = 4;
          }
          else if (user.isDarkelf()) {
            class_id = 5;
          }
          else if (user.isDragonKnight()) {
            class_id = 6;
          }
          else if (user.isIllusionist()) {
            class_id = 7;
          }  switch (((Integer)aTempData.get(1)).intValue()) {
            
            case 1:
              msg = "王族";
              break;
            
            case 2:
              msg = "騎士";
              break;
            
            case 3:
              msg = "法師";
              break;
            
            case 4:
              msg = "妖精";
              break;
            
            case 5:
              msg = "黑妖";
              break;
            
            case 6:
              msg = "龍騎";
              break;
            
            case 7:
              msg = "幻術";
              break;
          } 
          if (((Integer)aTempData.get(1)).intValue() != class_id) {
            
            user.sendPackets((ServerBasePacket)new S_SystemMessage("你的職業無法使用" + msg + "的專屬道具。"));
            return;
          } 
        } 
        if (((Integer)aTempData.get(14)).intValue() != 0 && user.getLevel() < ((Integer)aTempData.get(14)).intValue()) {
          
          user.sendPackets((ServerBasePacket)new S_SystemMessage("等級" + ((Integer)aTempData.get(14)).intValue() + "以上才可使用此道具。"));
          return;
        } 
        if (((Integer)aTempData.get(2)).intValue() != 0 && !user.getInventory().checkItem(((Integer)aTempData.get(2)).intValue())) {
          
          L1Item temp = ItemTable.get().getTemplate(((Integer)aTempData.get(2)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("使用此道具需要(" + temp.getName() + ")來作為媒介。"));
          return;
        } 
        if (((Integer)aTempData.get(3)).intValue() != 0) {
          
          L1ItemInstance item = user.getInventory().findItemId(((Integer)aTempData.get(0)).intValue());
          user.getInventory().removeItem(item.getId(), 1L);
        } 
        if (((Integer)aTempData.get(4)).intValue() != 0 && ((Integer)aTempData.get(17)).intValue() != 0) {
          if (user.hasSkillEffect(67)) {
            user.removeSkillEffect(67);
          }
          
          L1PolyMorph.doPoly((L1Character)user, ((Integer)aTempData.get(4)).intValue(), ((Integer)aTempData.get(5)).intValue(), 1);
        } else if (((Integer)aTempData.get(4)).intValue() != 0) {
          if (user.hasSkillEffect(67)) {
            user.removeSkillEffect(67);
          }
          
          L1PolyMorph.doPoly((L1Character)user, ((Integer)aTempData.get(4)).intValue(), ((Integer)aTempData.get(5)).intValue(), 1);
        } 
        
        if (((Integer)aTempData.get(6)).intValue() != 0) {
          
          user.addBaseMaxHp((short)((Integer)aTempData.get(6)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("HP永久增加了(" + ((Integer)aTempData.get(6)).intValue() + ")滴。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus(user));
        } 
        if (((Integer)aTempData.get(7)).intValue() != 0) {
          
          user.addBaseMaxMp((short)((Integer)aTempData.get(7)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("MP永久增加了(" + ((Integer)aTempData.get(7)).intValue() + ")滴。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus(user));
        } 
        if (((Integer)aTempData.get(8)).intValue() != 0) {
          
          user.addBaseStr((byte)((Integer)aTempData.get(8)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("力量永久增加了(" + ((Integer)aTempData.get(8)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(8)).intValue());
        } 
        if (((Integer)aTempData.get(9)).intValue() != 0) {
          
          user.addBaseDex((byte)((Integer)aTempData.get(9)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("敏捷永久增加了(" + ((Integer)aTempData.get(9)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(9)).intValue());
        } 
        if (((Integer)aTempData.get(10)).intValue() != 0) {
          
          user.addBaseCon((byte)((Integer)aTempData.get(10)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("體質永久增加了(" + ((Integer)aTempData.get(10)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(10)).intValue());
        } 
        if (((Integer)aTempData.get(11)).intValue() != 0) {
          
          user.addBaseWis((byte)((Integer)aTempData.get(11)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("精神永久增加了(" + ((Integer)aTempData.get(11)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(11)).intValue());
        } 
        if (((Integer)aTempData.get(12)).intValue() != 0) {
          
          user.addBaseInt((byte)((Integer)aTempData.get(12)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("智力永久增加了(" + ((Integer)aTempData.get(12)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(12)).intValue());
        } 
        if (((Integer)aTempData.get(13)).intValue() != 0) {
          
          user.addBaseCha((byte)((Integer)aTempData.get(13)).intValue());
          user.sendPackets((ServerBasePacket)new S_SystemMessage("魅力永久增加了(" + ((Integer)aTempData.get(13)).intValue() + ")點。"));
          user.sendPackets((ServerBasePacket)new S_OwnCharStatus2(user));
          user.setElixirStats(user.getElixirStats() + ((Integer)aTempData.get(13)).intValue());
        } 
        if (((Integer)aTempData.get(15)).intValue() != 0) {
          
          if (user.hasSkillEffect(71)) {
            
            user.sendPackets((ServerBasePacket)new S_ServerMessage(698));
            return;
          } 
          if (user.hasSkillEffect(5167)) {
            user.setCurrentHp(user.getCurrentHp() - RandomArrayList.getInc(32, 12));
          } else {
            user.setCurrentHp(user.getCurrentHp() + ((Integer)aTempData.get(15)).intValue());
          } 
        }  if (((Integer)aTempData.get(16)).intValue() != 0) {
          
          if (user.hasSkillEffect(71)) {
            
            user.sendPackets((ServerBasePacket)new S_ServerMessage(698));
            return;
          } 
          user.setCurrentMp(user.getCurrentMp() + ((Integer)aTempData.get(16)).intValue());
          user.sendPackets((ServerBasePacket)new S_ServerMessage(338, "$1084"));
        } 
        if (((Integer)aTempData.get(20)).intValue() != 0) {
          
          try {
            long exp = ((Integer)aTempData.get(20)).intValue();
            
            if (exp > 0L) {
              user.addExp(exp);
              user.sendPackets((ServerBasePacket)new S_OwnCharStatus(user));
            } 
          } catch (Exception exception) {}
        }

        
        if (((Integer)aTempData.get(21)).intValue() != 0) {
          
          int Lawful = user.getLawful() + ((Integer)aTempData.get(21)).intValue();
          if (Lawful > 32767) {
            Lawful = 32767;
          }
          else if (Lawful < -32768) {
            Lawful = -32768;
          }  user.setLawful(Lawful);
        } 
        if (((Integer)aTempData.get(22)).intValue() != 0) {
          
          user.sendPackets((ServerBasePacket)new S_SkillSound(user.getId(), ((Integer)aTempData.get(22)).intValue()));
          user.broadcastPacketAll((ServerBasePacket)new S_SkillSound(user.getId(), ((Integer)aTempData.get(22)).intValue()));
        } 
        if ((int[])aTempData.get(27) != null) {
          
          int[] Skills = (int[])aTempData.get(27);
          int time = ((Integer)aTempData.get(28)).intValue();
          for (int j = 0; j < Skills.length; j++) {
            
            L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(user, Skills[j], user.getId(), user.getX(), user.getY(), time, 4);
          } 
        } 
        
        if (((Integer)aTempData.get(33)).intValue() != 0) {
        	user.setKarma(user.getKarma() + ((Integer)aTempData.get(33)).intValue());
        }
        
        if (((Integer)aTempData.get(34)).intValue() != 0 && ((Integer)aTempData.get(35)).intValue() != 0) {
        	user.getInventory().storeItem(((Integer)aTempData.get(34)), ((Integer)aTempData.get(35)));
        }
        return;
      }
    } 
  }



  
  private static void getData15b() {
    Connection con = null;
    
    try {
      con = DatabaseFactory.get().getConnection();
      Statement stat = con.createStatement();
      ResultSet rset = stat.executeQuery("SELECT * FROM w_道具使用類別_2");
      ArrayList<Object> aReturn = null;
      if (rset != null) {
        for (; rset.next(); aData15b.add(aReturn)) {
          
          aReturn = new ArrayList();
          aReturn.add(0, new Integer(rset.getInt("item_id")));
          aReturn.add(1, new Integer(rset.getInt("checkClass")));
          aReturn.add(2, new Integer(rset.getInt("checkItem")));
          aReturn.add(3, new Integer(rset.getInt("removeItem")));
          aReturn.add(4, new Integer(rset.getInt("polyId")));
          aReturn.add(5, new Integer(rset.getInt("polyTime")));
          aReturn.add(6, new Integer(rset.getInt("permanenceHp")));
          aReturn.add(7, new Integer(rset.getInt("permanenceMp")));
          aReturn.add(8, new Integer(rset.getInt("permanenceStr")));
          aReturn.add(9, new Integer(rset.getInt("permanenceDex")));
          aReturn.add(10, new Integer(rset.getInt("permanenceCon")));
          aReturn.add(11, new Integer(rset.getInt("permanenceWis")));
          aReturn.add(12, new Integer(rset.getInt("permanenceInt")));
          aReturn.add(13, new Integer(rset.getInt("permanenceCha")));
          aReturn.add(14, new Integer(rset.getInt("level")));
          aReturn.add(15, new Integer(rset.getInt("hp")));
          aReturn.add(16, new Integer(rset.getInt("mp")));
          aReturn.add(17, new Integer(rset.getInt("讀取變身能力")));
          aReturn.add(18, Integer.valueOf(0));
          aReturn.add(19, Integer.valueOf(0));
          aReturn.add(20, new Integer(rset.getInt("Exp")));
          aReturn.add(21, new Integer(rset.getInt("Lawful")));
          aReturn.add(22, new Integer(rset.getInt("Gfx")));
          aReturn.add(23, Integer.valueOf(0));
          aReturn.add(24, Integer.valueOf(0));
          aReturn.add(25, Integer.valueOf(0));
          aReturn.add(26, Integer.valueOf(0));
          if (rset.getString("Skills") != null && !rset.getString("Skills").equals("") && !rset.getString("Skills").equals("0")) {
            aReturn.add(27, getArray(rset.getString("Skills"), ",", 1));
          } else {
            aReturn.add(27, null);
          }  aReturn.add(28, new Integer(rset.getInt("SkillsTime")));
          aReturn.add(29, Integer.valueOf(0));
          aReturn.add(30, Integer.valueOf(0));
          aReturn.add(31, Integer.valueOf(0));
          aReturn.add(32, Integer.valueOf(0));
          aReturn.add(33, new Integer(rset.getInt("Karma")));
          aReturn.add(34, new Integer(rset.getInt("giveitem_id")));
          aReturn.add(35, new Integer(rset.getInt("givecount")));
        } 
      }
      if (con != null && !con.isClosed()) {
        con.close();
      }
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