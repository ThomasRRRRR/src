package com.lineage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.StringTokenizer;


public final class ConfigOther {

  public static int Call_clan_itemid2;
  public static int Call_clan_count2;
  public static int Call_clan_itemid2B;
  public static int Call_clan_count2B;
  public static int target_clan_itemid2;
  public static int target_clan_count2;
  public static String clanmsg13;
  public static String clanmsg15;
  public static String clanmsg16;
  public static boolean prcheck = false;
  public static int prchecktype;
  public static int prcheckcount1;
  public static int prcheckcount2;
  public static int prcheckcount3;
  public static int takeitem;
  public static int pnitem;
  public static String weapon_Item_Price6;
  public static String weapon_Item_Price7;
  public static String weapon_Item_Price8;
  public static String weapon_Item_Price9;
  public static String weapon_Item_Price10;
  public static String armor_Item_Price4;
  public static String armor_Item_Price5;
  public static String armor_Item_Price6;
  public static String armor_Item_Price7;
  public static String armor_Item_Price8;
  public static String armor_Item_Price9;
  public static String armor_Item_Price10;
  public static boolean restsavepclog = false;
  public static int Call_party_itemid;
  public static int Call_party_count;
  public static int Call_party_itemidB;
  public static int Call_party_countB;
  public static int target_party_itemid;
  public static int target_party_count;
  public static String partymsg;
  public static String clanmsg5;
  public static String clanmsg6;
  public static double pcdmgpet;
  public static double pcdmgsumm;
  public static int petcountchatype;
  public static int petcountchatype1;
  public static int tamingmonstercount;
  public static int summmonstercount;
  public static boolean summoncountcha;
  public static boolean petcountcha;
  public static double petdmgother = 0.0D;
  public static double summondmgother = 0.0D;
  public static boolean war_pet_summ;
  public static double petdmgotherpc_war;
  public static double summondmgotherpc_war;
  public static double pcdmgpet_war;
  public static double pcdmgsumm_war;
  public static double petdmgotherpc = 0.0D;
  public static double summondmgotherpc = 0.0D;
  public static boolean Quitgameshop = false;
  public static int recaseff;
  public static int recaseffcount;
  public static int PcLevelUp;
  public static int PcLevelUpExp;
  public static boolean Pickupitemtoall = false;
  public static boolean DropitemMsgall = false;
  public static int PetLevelUp;
  public static boolean AllCall_clan_Crown;
  public static int Call_clan_itemid;
  public static int Call_clan_count;
  public static int Call_clan_itemidB;
  public static int Call_clan_countB;
  public static int target_clan_itemid;
  public static int target_clan_count;
  public static String clanmsg;
  public static String clanmsg1;
  public static String clanmsg2;
  public static boolean Reset_Map;
  public static int Reset_Map_Time;

  // TODO 防加速核心偵測相關設定
  public static boolean SPEED = false;
  public static double SPEED_TIME = 1.0D;
  public static boolean CHECK_MOVE_INTERVAL;
  public static boolean CHECK_ATTACK_INTERVAL;
  public static boolean CHECK_SPELL_INTERVAL;
  public static short INJUSTICE_COUNT;
  public static int JUSTICE_COUNT;
  public static int DE_SPAWN_TIMER;
  public static int DE_SPAWN_TIMER_DELETE;
  public static int Stint;
  public static int opein;
  
  public static double IMMUNE_TO_HARM = 1.0D;
  public static double IMMUNE_TO_HARM_NPC = 1.0D;
  public static int CharNamebyte;
  public static int CharNameEnbyte;
  public static boolean KILLRED = true;
  public static boolean IS_CHECK = false;
  public static boolean dropcolor = true;
  public static boolean dmgspr = true;
  public static boolean CLANDEL;
  public static boolean CLANTITLE;
  public static int SCLANCOUNT;
  public static int dead_score;
  public static boolean LIGHT;
  public static boolean HPBAR;
  public static boolean SHOPINFO;
  public static int CASTLEHPR;
  public static int CASTLEMPR;
  public static int FORESTHPR;
  public static int FORESTMPR;
  public static boolean WAR_DOLL;
  public static int SET_GLOBAL;
  public static int SET_GLOBAL_COUNT;
  public static int SET_GLOBAL_TIME;
  public static int ENCOUNTER_LV;
  public static int ILLEGAL_SPEEDUP_PUNISHMENT;
  public static String CreateCharInfo;
  public static boolean WHO_ONLINE_MSG_ON;
  public static int[] NEW_CHAR_LOC;
  public static int[] AtkNo;
  public static int[] AtkNo_pc;
  public static int[] WAR_DISABLE_SKILLS;
  public static int[] WAR_DISABLE_ITEM;
  public static int[] MAP_IDSKILL;
  public static int[] MAP_SKILL;
  public static int[] MAP_IDITEM;
  public static int[] MAP_ITEM;
  public static int drainedMana;
  public static boolean newcharpra;
  public static int newcharpralv;
  public static int summtime;
  public static boolean logpcpower;
  public static int armor_type1;
  public static int armor_type2;
  public static int armor_type3;
  public static int armor_type4;
  public static int armor_type5;
  public static int armor_type6;
  public static int armor_type7;
  public static int armor_type8;
  public static int armor_type9;
  public static int armor_type10;
  public static int armor_type11;
  public static int armor_type12;
  public static int armor_type13;
  public static int armor_type14;
  public static int armor_type15;
  public static int armor_type16;
  public static int armor_type17;
  public static int armor_type18;
  public static int armor_type19;
  public static int armor_type20;
  public static int armor_type21;
  public static int armor_type22;
  public static int armor_type23;
  public static int armor_type24;
  public static int armor_type25;
  public static int Attack_1;
  public static int Attack_2;
  public static int Attack_3;
  public static int Attack_4;
  public static int Attack_5;
  public static int Attack_Miss;
  public static int NeedItem;
  public static int NeedItemCount;
  public static String Msg;
  public static String ItemMsg;
  public static int NeedItem1;
  public static int NeedItemCount1;
  public static String Msg1;
  public static String ItemMsg1;
  public static int[] Give_skill;
  public static int[] Give_skill1;
  public static int TextMinPlayer;
  public static int TextMaxPlayer;
  public static int TextLevel;
  public static int tcheckitem;
  public static int TextMoney;
  public static int Textnpa1;
  public static int Textnpa2;
  public static int Textnpa3;
  public static int Textnpa4;
  public static int Textnpa5;
  public static int Textnpa6;
  public static int Textnpa7;
  public static int Textnpa8;
  public static int Textnpa9;
  public static int Textnpa10;
  public static boolean RankLevel = false;
  public static int day_level = 40;
  public static int restday = 18;
  public static boolean Illusionistpc = false;
  public static boolean DragonKnightpc = false;
  public static int clancount = 3;
  public static int clancountexp = 10;
  public static int clanLeaderlv = 45;
  public static boolean warProtector = true;
  public static int checkitem76;
  public static int checkitemcount76;
  public static int checkitem81;
  public static int checkitemcount81;
  public static int checkitem59;
  public static int checkitemcount59;
  public static int monbossitem = 40308;
  public static int monbossitemcount = 10000;
  public static int montime = 40;
  public static int monsec = 18;
  public static int killmsg = 1;
  public static int dropmsg = 1;
  public static int boxsmsg = 1;
  public static int killlevel = 40;
  public static int JOY_OF_PAIN_PC;
  public static int JOY_OF_PAIN_NPC;
  public static int JOY_OF_PAIN_DMG;
  public static boolean logpcgiveitem;
  public static int logpclevel;
  public static int logpcresthp;
  public static int logpcrestmp;
  public static boolean logpcallmsg;
  public static int logpctfcount;
  public static boolean onlydaypre;
  public static int onlydaytime;
  public static int dateStartTime;
  public static int dollcount;
  public static int dollcount1;
  public static int petlevel;
  public static int petexp;
  public static boolean partyexp;
  public static double partyexp1;
  public static int partycount;
  public static int warehouselevel;
  public static int shopitemrest = 0;
  public static int tradelevel;
  public static int Scarecrowlevel;
public static int NeedItem2;
public static int NeedItemCount2;
public static String ItemMsg2;
  public static int BSPolyItemId;
  public static int BSPolyItemCount;
  public static String BSPolyItem;
//  public static boolean newbless1weapon;
//  public static boolean newbless1armor;
public static boolean gmverify = false;
public static String verificationcode;

public static int StrCriticalgfx;
public static int DexCriticalgfx;
public static int IntCriticalgfx;
public static int MagicCriticalgfx;
public static int Great;
public static int Critical;
public static int ActidCriticalgfx;

public static int[] GM_LOC;

public static boolean Polyatk = true;
public static boolean Sponsorbroad = false;


  private static final String LIANG = "./config/other.properties";

  public static void load() throws ConfigErrorException {
    Properties set = new Properties();
    try {
      InputStream is = new FileInputStream(new File("./config/other.properties"));
      InputStreamReader isr = new InputStreamReader(is, "utf-8");
      set.load(isr);
      is.close();

      NeedItem2 = Integer.parseInt(set.getProperty("NeedItem2", "3600"));
      NeedItemCount2 = Integer.parseInt(set.getProperty("NeedItemCount2", 
            "3600"));
      ItemMsg2 = set.getProperty("ItemMsg2", "");

      BSPolyItemId = Integer.parseInt(set.getProperty("BSPolyItemId", "40088"));
      BSPolyItemCount = Integer.parseInt(set.getProperty("BSPolyItemCount", "1"));
      BSPolyItem = set.getProperty("BSPolyItem", "");
      
//      newbless1weapon = Boolean.parseBoolean(set.getProperty("newbless1weapon", "true"));
//      newbless1armor = Boolean.parseBoolean(set.getProperty("newbless1armor", "true"));
      
      Call_clan_itemid2 = Integer.parseInt(set.getProperty("Call_clan_itemid2", "10"));
      Call_clan_count2 = Integer.parseInt(set.getProperty("Call_clan_count2", "10"));
      Call_clan_itemid2B = Integer.parseInt(set.getProperty("Call_clan_itemid2B", "10"));
      Call_clan_count2B = Integer.parseInt(set.getProperty("Call_clan_count2B", "10"));
      target_clan_itemid2 = Integer.parseInt(set.getProperty("target_clan_itemid2", "10"));
      target_clan_count2 = Integer.parseInt(set.getProperty("target_clan_count2", "10"));
      clanmsg13 = set.getProperty("clanmsg13", "");
      clanmsg15 = set.getProperty("clanmsg15", "");
      clanmsg16 = set.getProperty("clanmsg16", "");

      prcheck = Boolean.parseBoolean(set.getProperty("prcheck", "false"));
      prchecktype = Integer.parseInt(set.getProperty("prchecktype", "0"));
      prcheckcount1 = Integer.parseInt(set.getProperty("prcheckcount1", "0"));
      prcheckcount2 = Integer.parseInt(set.getProperty("prcheckcount2", "0"));
      prcheckcount3 = Integer.parseInt(set.getProperty("prcheckcount3", "0"));

      weapon_Item_Price6 = set.getProperty("weapon_Item_Price6", "44070,500");
      weapon_Item_Price7 = set.getProperty("weapon_Item_Price7", "44070,500");
      weapon_Item_Price8 = set.getProperty("weapon_Item_Price8", "44070,500");
      weapon_Item_Price9 = set.getProperty("weapon_Item_Price9", "44070,500");
      weapon_Item_Price10 = set.getProperty("weapon_Item_Price10", "44070,500");
      
      armor_Item_Price4 = set.getProperty("armor_Item_Price4", "44070,500");
      armor_Item_Price5 = set.getProperty("armor_Item_Price5", "44070,500");
      armor_Item_Price6 = set.getProperty("armor_Item_Price6", "44070,500");
      armor_Item_Price7 = set.getProperty("armor_Item_Price7", "44070,500");
      armor_Item_Price8 = set.getProperty("armor_Item_Price8", "44070,500");
      armor_Item_Price9 = set.getProperty("armor_Item_Price9", "44070,500");
      armor_Item_Price10 = set.getProperty("armor_Item_Price10", "44070,500");
      
      restsavepclog = Boolean.parseBoolean(set.getProperty("restsavepclog", "false"));

      takeitem = Integer.parseInt(set.getProperty("takeitem", "0"));
      pnitem = Integer.parseInt(set.getProperty("pnitem", "0"));
      Call_party_itemid = Integer.parseInt(set.getProperty("Call_party_itemid", "10"));
      Call_party_count = Integer.parseInt(set.getProperty("Call_party_count", "10"));
      Call_party_itemidB = Integer.parseInt(set.getProperty("Call_party_itemidB", "10"));
      Call_party_countB = Integer.parseInt(set.getProperty("Call_party_countB", "10"));
      target_party_itemid = Integer.parseInt(set.getProperty("target_party_itemid", "10"));
      target_party_count = Integer.parseInt(set.getProperty("target_party_count", "10"));
      partymsg = set.getProperty("partymsg", "");
      clanmsg5 = set.getProperty("clanmsg5", "");
      clanmsg6 = set.getProperty("clanmsg6", "");
      
      pcdmgpet = Double.parseDouble(set.getProperty("pcdmgpet", "0.0"));
      pcdmgsumm = Double.parseDouble(set.getProperty("pcdmgsumm", "0.0."));
      petcountchatype = Integer.parseInt(set.getProperty("petcountchatype", "100"));
      petcountchatype1 = Integer.parseInt(set.getProperty("petcountchatype1", "100"));
      tamingmonstercount = Integer.parseInt(set.getProperty("tamingmonstercount", "10"));
      summmonstercount = Integer.parseInt(set.getProperty("summmonstercount", "10"));
      summoncountcha = Boolean.parseBoolean(set.getProperty("summoncountcha", "false"));
      petcountcha = Boolean.parseBoolean(set.getProperty("petcountcha", "false"));
      petdmgother = Double.parseDouble(set.getProperty("petdmgother", "0.0"));
      summondmgother = Double.parseDouble(set.getProperty("summondmgother", "0.0"));
      petdmgotherpc = Double.parseDouble(set.getProperty("petdmgotherpc", "0.0"));
      summondmgotherpc = Double.parseDouble(set.getProperty("summondmgotherpc", "0.0"));
      petdmgotherpc_war = Double.parseDouble(set.getProperty("petdmgotherpc_war", "0.0"));
      summondmgotherpc_war = Double.parseDouble(set.getProperty("summondmgotherpc_war", "0.0"));
      pcdmgpet_war = Double.parseDouble(set.getProperty("pcdmgpet_war", "0.0"));
      pcdmgsumm_war = Double.parseDouble(set.getProperty("pcdmgsumm_war", "0.0"));
      war_pet_summ = Boolean.parseBoolean(set.getProperty("war_pet_summ", "false"));
      Quitgameshop = Boolean.parseBoolean(set.getProperty("Quitgameshop", "false"));
      recaseff = Integer.parseInt(set.getProperty("recaseff", "20"));
      recaseffcount = Integer.parseInt(set.getProperty("recaseffcount", "20"));
      
      Pickupitemtoall = Boolean.parseBoolean(set.getProperty("Pickupitemtoall", "false"));
      DropitemMsgall = Boolean.parseBoolean(set.getProperty("DropitemMsgall", "false"));
      PcLevelUp = Integer.parseInt(set.getProperty("PcLevelUp", "10"));
      PcLevelUpExp = Integer.parseInt(set.getProperty("PcLevelUpExp", "10"));
      PetLevelUp = Integer.parseInt(set.getProperty("PetLevelUp", "50"));
      AllCall_clan_Crown = Boolean.parseBoolean(set.getProperty("AllCall_clan_Crown", "false"));
      Call_clan_itemid = Integer.parseInt(set.getProperty("Call_clan_itemid", "10"));
      Call_clan_count = Integer.parseInt(set.getProperty("Call_clan_count", "10"));
      Call_clan_itemidB = Integer.parseInt(set.getProperty("Call_clan_itemidB", "10"));
      Call_clan_countB = Integer.parseInt(set.getProperty("Call_clan_countB", "10"));
      target_clan_itemid = Integer.parseInt(set.getProperty("target_clan_itemid", "10"));
      target_clan_count = Integer.parseInt(set.getProperty("target_clan_count", "10"));
      clanmsg = set.getProperty("clanmsg", "");
      clanmsg1 = set.getProperty("clanmsg1", "");
      clanmsg2 = set.getProperty("clanmsg2", "");

      Reset_Map = Boolean.parseBoolean(set.getProperty("Reset_Map", "false"));
      Reset_Map_Time = Integer.parseInt(set.getProperty("Reset_Map_Time", "20"));
      MAP_IDITEM = toIntArray(set.getProperty("MAP_IDITEM", ""), ",");
      MAP_ITEM = toIntArray(set.getProperty("MAP_ITEM", ""), ",");
      MAP_IDSKILL = toIntArray(set.getProperty("MAP_IDSKILL", ""), ",");
      MAP_SKILL = toIntArray(set.getProperty("MAP_SKILL", ""), ",");
      WAR_DISABLE_SKILLS = toIntArray(set.getProperty("WAR_DISABLE_SKILLS", ""), ",");
      WAR_DISABLE_ITEM = toIntArray(set.getProperty("WAR_DISABLE_ITEM", ""), ",");
      
      // TODO 防加速核心偵測相關設定
   		SPEED = Boolean.parseBoolean(set.getProperty("speed", "false"));
   		SPEED_TIME = Double.parseDouble(set.getProperty("speed_time", "1.2"));
   		CHECK_MOVE_INTERVAL = Boolean.parseBoolean(set.getProperty("CheckMoveInterval", "false"));
   		CHECK_ATTACK_INTERVAL = Boolean.parseBoolean(set.getProperty("CheckAttackInterval", "false"));
   		CHECK_SPELL_INTERVAL = Boolean.parseBoolean(set.getProperty("CheckSpellInterval", "false"));
   		INJUSTICE_COUNT = Short.parseShort(set.getProperty("InjusticeCount", "10"));
   		JUSTICE_COUNT = Integer.parseInt(set.getProperty("JusticeCount","4"));
   		Stint = Short.parseShort(set.getProperty("Stint", "5"));
   		opein = Short.parseShort(set.getProperty("opein", "1"));
      
      dropcolor = Boolean.parseBoolean(set.getProperty("dropcolor", "false"));
      dmgspr = Boolean.parseBoolean(set.getProperty("dmgspr", "false"));
      IMMUNE_TO_HARM = Double.parseDouble(set.getProperty("IMMUNE_TO_HARM", "1.0"));
      IMMUNE_TO_HARM_NPC = Double.parseDouble(set.getProperty("IMMUNE_TO_HARM_NPC", "1.0"));
      AtkNo = toIntArray(set.getProperty("AtkNo", ""), ",");
      AtkNo_pc = toIntArray(set.getProperty("AtkNo_pc", ""), ",");
      ILLEGAL_SPEEDUP_PUNISHMENT = Integer.parseInt(set.getProperty("Punishment", "0"));
      
      ENCOUNTER_LV = Integer.parseInt(set.getProperty("encounter_lv", "20"));
      
      KILLRED = Boolean.parseBoolean(set.getProperty("kill_red", "false"));

      CLANDEL = Boolean.parseBoolean(set.getProperty("clanadel", "false"));
      
      CLANTITLE = Boolean.parseBoolean(set.getProperty("clanatitle", "false"));
      CharNamebyte = Integer.parseInt(set.getProperty("CharNamebyte", "5"));
      CharNameEnbyte = Integer.parseInt(set.getProperty("CharNameEnbyte", "12"));
      armor_type1 = Integer.parseInt(set.getProperty("armor_type1", "100"));
      armor_type2 = Integer.parseInt(set.getProperty("armor_type2", "100"));
      armor_type3 = Integer.parseInt(set.getProperty("armor_type3", "100"));
      armor_type4 = Integer.parseInt(set.getProperty("armor_type4", "100"));
      armor_type5 = Integer.parseInt(set.getProperty("armor_type5", "100"));
      armor_type6 = Integer.parseInt(set.getProperty("armor_type6", "100"));
      armor_type7 = Integer.parseInt(set.getProperty("armor_type7", "100"));
      armor_type8 = Integer.parseInt(set.getProperty("armor_type8", "100"));
      armor_type9 = Integer.parseInt(set.getProperty("armor_type9", "100"));
      armor_type10 = Integer.parseInt(set.getProperty("armor_type10", "100"));
      armor_type11 = Integer.parseInt(set.getProperty("armor_type11", "100"));
      armor_type12 = Integer.parseInt(set.getProperty("armor_type12", "100"));
      armor_type13 = Integer.parseInt(set.getProperty("armor_type13", "100"));
      armor_type14 = Integer.parseInt(set.getProperty("armor_type14", "100"));
      armor_type15 = Integer.parseInt(set.getProperty("armor_type15", "100"));
      armor_type16 = Integer.parseInt(set.getProperty("armor_type16", "100"));
      armor_type17 = Integer.parseInt(set.getProperty("armor_type17", "100"));
      armor_type18 = Integer.parseInt(set.getProperty("armor_type18", "100"));
      armor_type19 = Integer.parseInt(set.getProperty("armor_type19", "100"));
      armor_type20 = Integer.parseInt(set.getProperty("armor_type20", "100"));
      armor_type21 = Integer.parseInt(set.getProperty("armor_type21", "100"));
      armor_type22 = Integer.parseInt(set.getProperty("armor_type22", "100"));
      armor_type23 = Integer.parseInt(set.getProperty("armor_type23", "100"));
      armor_type24 = Integer.parseInt(set.getProperty("armor_type24", "100"));
      armor_type25 = Integer.parseInt(set.getProperty("armor_type25", "100"));

      SCLANCOUNT = Integer.parseInt(set.getProperty("sclancount", "100"));
      dead_score = Integer.parseInt(set.getProperty("dead_score", "100"));
      LIGHT = Boolean.parseBoolean(set.getProperty("light", "false"));
      
      HPBAR = Boolean.parseBoolean(set.getProperty("hpbar", "false"));
      
      SHOPINFO = Boolean.parseBoolean(set.getProperty("shopinfo", "false"));
      
      SET_GLOBAL = Integer.parseInt(set.getProperty("set_global", "100"));
      
      SET_GLOBAL_COUNT = Integer.parseInt(set.getProperty("set_global_count", "100"));
      
      SET_GLOBAL_TIME = Integer.parseInt(set.getProperty("set_global_time", "5"));
      
      WAR_DOLL = Boolean.parseBoolean(set.getProperty("war_doll", "true"));
      logpcpower = Boolean.parseBoolean(set.getProperty("logpcpower", "true"));
      newcharpra = Boolean.parseBoolean(set.getProperty("newcharpra", "true"));
      newcharpralv = Integer.parseInt(set.getProperty("newcharpralv", "5"));
      
      String tmp13 = set.getProperty("CreateCharInfo", "");
      if (!tmp13.equalsIgnoreCase("null")) {
        CreateCharInfo = tmp13;
      }
      WHO_ONLINE_MSG_ON = Boolean.parseBoolean(set.getProperty("WHO_ONLINE_MSG_ON", "true"));
      summtime = Integer.parseInt(set.getProperty("summtime", "3600"));
      drainedMana = Integer.parseInt(set.getProperty("drainedMana", "5"));
      
      NeedItem = Integer.parseInt(set.getProperty("NeedItem", "3600"));
      NeedItemCount = Integer.parseInt(set.getProperty("NeedItemCount", "3600"));
      Msg = set.getProperty("Msg", "");
      
      NeedItem1 = Integer.parseInt(set.getProperty("NeedItem1", "3600"));
      NeedItemCount1 = Integer.parseInt(set.getProperty("NeedItemCount1", "3600"));
      Msg1 = set.getProperty("Msg1", "");
      ItemMsg1 = set.getProperty("ItemMsg1", "");
      ItemMsg = set.getProperty("ItemMsg", "");

      Give_skill = toIntArray(set.getProperty("Give_skill", ""), ",");
      Give_skill1 = toIntArray(set.getProperty("Give_skill1", ""), ",");
      Attack_1 = Integer.parseInt(set.getProperty("Attack_1", "100"));
      Attack_2 = Integer.parseInt(set.getProperty("Attack_2", "100"));
      Attack_3 = Integer.parseInt(set.getProperty("Attack_3", "100"));
      Attack_4 = Integer.parseInt(set.getProperty("Attack_4", "100"));
      Attack_5 = Integer.parseInt(set.getProperty("Attack_5", "100"));
      Attack_Miss = Integer.parseInt(set.getProperty("Attack_Miss", "100"));
      Great = Integer.parseInt(set.getProperty("Great", "0"));
      Critical = Integer.parseInt(set.getProperty("Critical", "0"));
      ActidCriticalgfx = Integer.parseInt(set.getProperty("ActidCriticalgfx", "0"));
      
      TextMinPlayer = Integer.parseInt(set.getProperty("TextMinPlayer", "1"));
      TextMaxPlayer = Integer.parseInt(set.getProperty("TextMaxPlayer", "20"));
      TextLevel = Integer.parseInt(set.getProperty("TextLevel", "52"));
      
      tcheckitem = Integer.parseInt(set.getProperty("tcheckitem", "5"));
      TextMoney = Integer.parseInt(set.getProperty("TextMoney", "5"));
      Textnpa1 = Integer.parseInt(set.getProperty("Textnpa1", "5"));
      Textnpa2 = Integer.parseInt(set.getProperty("Textnpa2", "5"));
      Textnpa3 = Integer.parseInt(set.getProperty("Textnpa3", "5"));
      Textnpa4 = Integer.parseInt(set.getProperty("Textnpa4", "5"));
      Textnpa5 = Integer.parseInt(set.getProperty("Textnpa5", "5"));
      Textnpa6 = Integer.parseInt(set.getProperty("Textnpa6", "5"));
      Textnpa7 = Integer.parseInt(set.getProperty("Textnpa7", "5"));
      Textnpa8 = Integer.parseInt(set.getProperty("Textnpa8", "5"));
      Textnpa9 = Integer.parseInt(set.getProperty("Textnpa9", "5"));
      Textnpa10 = Integer.parseInt(set.getProperty("Textnpa10", "5"));
      
      String rb1 = set.getProperty("NEW_CHAR_LOC", "33080,33392,4");
      if (!rb1.equalsIgnoreCase("null")) {
        String[] rb11 = rb1.split(",");
        int[] rb111 = { Integer.valueOf(rb11[0]).intValue(), Integer.valueOf(rb11[1]).intValue(), Integer.valueOf(rb11[2]).intValue() };
        NEW_CHAR_LOC = rb111;
      } 
      
      String gm1 = set.getProperty("GM_LOC", "32736,32796,99");
      if (!gm1.equalsIgnoreCase("null")) {
        String[] gm11 = gm1.split(",");
        int[] gm111 = { Integer.valueOf(gm11[0]).intValue(), Integer.valueOf(gm11[1]).intValue(), Integer.valueOf(gm11[2]).intValue() };
        GM_LOC = gm111;
      } 

      RankLevel = Boolean.parseBoolean(set.getProperty("RankLevel", "false"));

      day_level = Integer.parseInt(set.getProperty("day_level", "5"));

      restday = Integer.parseInt(set.getProperty("restday", "5"));

      Illusionistpc = Boolean.parseBoolean(set.getProperty("Illusionistpc", "false"));

      DragonKnightpc = Boolean.parseBoolean(set.getProperty("DragonKnightpc", "false"));

      clancount = Integer.parseInt(set.getProperty("clancount", "5"));
      
      clancountexp = Integer.parseInt(set.getProperty("clancountexp", "5"));

      clanLeaderlv = Integer.parseInt(set.getProperty("clanLeaderlv", "5"));

      warProtector = Boolean.parseBoolean(set.getProperty("warProtector", "false"));

      checkitem76 = Integer.parseInt(set.getProperty("checkitem76", "5"));
      
      checkitemcount76 = Integer.parseInt(set.getProperty("checkitemcount76", "5"));

      checkitem81 = Integer.parseInt(set.getProperty("checkitem81", "5"));
      
      checkitemcount81 = Integer.parseInt(set.getProperty("checkitemcount81", "5"));

      checkitem59 = Integer.parseInt(set.getProperty("checkitem59", "5"));

      checkitemcount59 = Integer.parseInt(set.getProperty("checkitemcount59", "5"));

      monbossitem = Integer.parseInt(set.getProperty("monbossitem", "5"));
      monbossitemcount = Integer.parseInt(set.getProperty("monbossitemcount", "5"));
      
      montime = Integer.parseInt(set.getProperty("montime", "5"));
      
      monsec = Integer.parseInt(set.getProperty("monsec", "5"));

      killmsg = Integer.parseInt(set.getProperty("killmsg", "5"));
      
      dropmsg = Integer.parseInt(set.getProperty("dropmsg", "5"));
      
      boxsmsg = Integer.parseInt(set.getProperty("boxsmsg", "5"));

      killlevel = Integer.parseInt(set.getProperty("killlevel", "5"));

      JOY_OF_PAIN_PC = Integer.parseInt(set.getProperty("JOY_OF_PAIN_PC", "5"));
      
      JOY_OF_PAIN_NPC = Integer.parseInt(set.getProperty("JOY_OF_PAIN_NPC", "5"));
      
      JOY_OF_PAIN_DMG = Integer.parseInt(set.getProperty("JOY_OF_PAIN_DMG", "5"));

      logpcgiveitem = Boolean.parseBoolean(set.getProperty("logpcgiveitem", "false"));
      
      logpclevel = Integer.parseInt(set.getProperty("logpclevel", "5"));
      
      logpcresthp = Integer.parseInt(set.getProperty("logpcresthp", "5"));
      
      logpcrestmp = Integer.parseInt(set.getProperty("logpcrestmp", "5"));
      
      logpcallmsg = Boolean.parseBoolean(set.getProperty("logpcallmsg", "false"));
      
      logpctfcount = Integer.parseInt(set.getProperty("logpctfcount", "5"));

      onlydaypre = Boolean.parseBoolean(set.getProperty("onlydaypre", "false"));
      
      onlydaytime = Integer.parseInt(set.getProperty("onlydaytime", "5"));

      dateStartTime = Integer.parseInt(set.getProperty("dateStartTime", "5"));

      dollcount = Integer.parseInt(set.getProperty("dollcount", "5"));
      
      dollcount1 = Integer.parseInt(set.getProperty("dollcount1", "5"));

      petlevel = Integer.parseInt(set.getProperty("petlevel", "5"));
      
      petexp = Integer.parseInt(set.getProperty("petexp", "5"));

      partyexp = Boolean.parseBoolean(set.getProperty("partyexp", "false"));

      partyexp1 = Double.parseDouble(set.getProperty("partyexp1", "0.1"));

      partycount = Integer.parseInt(set.getProperty("partycount", "5"));

      warehouselevel = Integer.parseInt(set.getProperty("warehouselevel", "5"));

      shopitemrest = Integer.parseInt(set.getProperty("shopitemrest", "0"));

      tradelevel = Integer.parseInt(set.getProperty("tradelevel", "5"));

      Scarecrowlevel = Integer.parseInt(set.getProperty("Scarecrowlevel", "5"));
      
      gmverify = Boolean.parseBoolean(set.getProperty("gmverify", "true"));
      verificationcode = set.getProperty("verificationcode", "112233");
      
      StrCriticalgfx = Integer.parseInt(set.getProperty("StrCriticalgfx", "0"));
      DexCriticalgfx = Integer.parseInt(set.getProperty("DexCriticalgfx", "0"));
      IntCriticalgfx = Integer.parseInt(set.getProperty("IntCriticalgfx", "0"));
      MagicCriticalgfx = Integer.parseInt(set.getProperty("MagicCriticalgfx", "0"));
      
      Polyatk = Boolean.parseBoolean(set.getProperty("Polyatk", "true"));
      Sponsorbroad = Boolean.parseBoolean(set.getProperty("Sponsorbroad", "false"));
    }
    catch (Exception e) {
      throw new ConfigErrorException("設置檔案遺失: ./config/other.properties");
    } finally {
      
      set.clear();
    } 
  }
  
  public static int[] toIntArray(String text, String type) {
    StringTokenizer st = new StringTokenizer(text, type);
    int[] iReturn = new int[st.countTokens()];
    for (int i = 0; i < iReturn.length; i++)
      iReturn[i] = Integer.parseInt(st.nextToken()); 
    return iReturn;
  }
}