package com.lineage.server.serverpackets;

public class OpcodesServer
{
  public static final int S_OPCODE_CLANMATCHING = 0;
  /** 存入資金城堡寶庫 */
  public static final int S_OPCODE_DEPOSIT = 4;
  /** 物品清單(進入遊戲調用) */
  public static final int S_OPCODE_INVLIST = 5;
  /** 角色移除 [ 非立即 ] */
  public static final int S_OPCODE_DETELECHAROK = 6;
  /** 更新角色屬性與能力值 */
  public static final int S_OPCODE_OWNCHARSTATUS = 8;
  /** 物件移動 */
  public static final int S_OPCODE_MOVEOBJECT = 10;
  /** 魔法動畫:精準目標 */
  public static final int S_OPCODE_TRUETARGET = 11;
  /** 物品增加封包 */
  public static final int S_OPCODE_ADDITEM = 15;
  /** 撥放指定音效 */
  public static final int S_OPCODE_SOUND = 22;
  /** 物品狀態更新 */
  public static final int S_OPCODE_ITEMSTATUS = 24;
  
  public static final int S_OPCODE_ITEMAMOUNT = 24;
  /** 物件攻擊 */
  public static final int S_OPCODE_ATTACKPACKET = 30;
  /** 魔力更新 */
  public static final int S_OPCODE_MPUPDATE = 33;
  /** 更新正義值 */
  public static final int S_OPCODE_LAWFUL = 34;
  /** 增加交易物品封包 */
  public static final int S_OPCODE_TRADEADDITEM = 35;
  /** 戒指封包 */
  public static final int S_OPCODE_ABILITY = 36;
  /** 更新魔法攻擊力與魔法防禦力 */
  public static final int S_OPCODE_SPMR = 37;
  /** 物件對話視窗HTML */
  public static final int S_OPCODE_SHOWHTML = 39;
  /** 更新物件亮度 */
  public static final int S_OPCODE_LIGHT = 40;
  /** 範圍魔法攻擊封包*/
  public static final int S_OPCODE_RANGESKILLS = 42;
  /** 改變物件名稱 */
  public static final int S_OPCODE_CHANGENAME = 46;
  /** 魔法效果 - 暗盲咒術(效果編號) */
  public static final int S_OPCODE_CURSEBLIND = 47;
  /** 登入公告視窗 */
  public static final int S_OPCODE_COMMONNEWS = 48;
  /** 交易視窗 */
  public static final int S_OPCODE_TRADE = 52;
  /** 產生動畫(物件)*/
  public static final int S_OPCODE_SKILLSOUNDGFX = 55;
  /** 物品刪除 */
  public static final int S_OPCODE_DELETEINVENTORYITEM = 57;
  /** 角色名稱變紫色 */
  public static final int S_OPCODE_PINKNAME = 60;
  /** 多功能封包(重置設定/寵物控制便捷UI介面...)*/
  public static final int S_OPCODE_CHARSYNACK = 64;
  
  public static final int S_OPCODE_CHARRESET = 64;
  /** NPC商店收購清單(action sell) */
  public static final int S_OPCODE_SHOWSHOPSELLLIST = 65;
  /** 效果圖標 : 2段加速 */
  public static final int S_OPCODE_SKILLBRAVE = 67;
  /** 佈告欄 ( 訊息列表 )*/
  public static final int S_OPCODE_BOARD = 68;
  /** 角色皇冠 */
  public static final int S_OPCODE_CASTLEMASTER = 69;
  /** NPC商店販售清單(action buy) */
  public static final int S_OPCODE_SHOWSHOPBUYLIST = 70;
  /** 伺服器訊息 (調用String-c.tbl/自定義字串)*/
  public static final int S_OPCODE_SERVERMSG = 71;
  /** 更新血盟數據 */
  public static final int S_OPCODE_UPDATECLANID = 72;
  /** 要求使用密語聊天頻道*/
  public static final int S_OPCODE_WHISPERCHAT = 73;
  /** 物件外型改變 */
  public static final int S_OPCODE_POLY = 76;
  /** 一般聊天頻道 */
  public static final int S_OPCODE_NORMALCHAT = 81;
  /** 損壞武器名單 */
  public static final int S_OPCODE_SELECTLIST = 83;
  /** 血盟戰爭訊息 { 編號, 血盟名稱, 目標血盟名稱 } */
  public static final int S_OPCODE_WAR = 84;
  /** 物件復活 */
  public static final int S_OPCODE_RESURRECTION = 85;
  /** 物件封包 */
  public static final int S_OPCODE_CHARPACK = 87;
  
  public static final int S_OPCODE_DROPITEM = 87;
  /** 更新記憶座標 */
  public static final int S_OPCODE_BOOKMARKS = 92;
  /** 角色資訊(一般) */
  public static final int S_OPCODE_CHARLIST = 93;
  /** 創造角色的結果事件 */
  public static final int S_OPCODE_NEWCHARWRONG = 98;
  /** 物品顯示名稱 */
  public static final int S_OPCODE_ITEMNAME = 100;
  /** 魔法效果:三段加速 / 海底波紋效果*/
  public static final int S_OPCODE_LIQUOR = 103;
  /** 畫面中央訊息(調用String-h.tbl/自定義字串) */
  public static final int S_OPCODE_REDMESSAGE = 105;
  /** 產生動畫 (座標點) */
  public static final int S_OPCODE_EFFECTLOCATION = 106;
  /** 交易結果 */
  public static final int S_OPCODE_TRADESTATUS = 112;
  /** 遊戲世界天氣 */
  public static final int S_OPCODE_WEATHER = 115;
  /** 選取要召喚排列的傭兵數量(HTML) */
  public static final int S_OPCODE_PUTSOLDIER = 117;
  /** 角色盟徽 */
  public static final int S_OPCODE_EMBLEM = 118;
  /** 更新物件外型動作種類 */
  public static final int S_OPCODE_CHARVISUALUPDATE = 119;
  /** 物件刪除 */
  public static final int S_OPCODE_REMOVE_OBJECT = 120;
  /** 物件面向 */
  public static final int S_OPCODE_CHANGEHEADING = 122;
  /** 更新目前遊戲世界時間 */
  public static final int S_OPCODE_GAMETIME = 123;
  /** 魔法圖標: 水底呼吸 */
  public static final int S_OPCODE_BLESSOFEVA = 126;
  /** 角色資訊(創造角色) */
  public static final int S_OPCODE_NEWCHARPACK = 127;
  /** 選取物品需求數量HTML */
  public static final int S_OPCODE_INPUTAMOUNT = 136;
  /** 伺服器版本 */
  public static final int S_OPCODE_SERVERVERSION = 139;
  /** 角色個人商店 出售/收購 清單 */
  public static final int S_OPCODE_PRIVATESHOPLIST = 140;
  /** 取出城堡寶庫金幣 */
  public static final int S_OPCODE_DRAWAL = 141;
  /** 佈告欄( 訊息閱讀 ) */
  public static final int S_OPCODE_BOARDREAD = 148;
  /** 初始化OpCode */
  public static final int S_OPCODE_INITOPCODE = 150;
  /** 更新六項能力值以及負重 */
  public static final int S_OPCODE_OWNCHARSTATUS2 = 155;
  /** 血盟小屋販售清單(佈告欄) */
  public static final int S_OPCODE_HOUSELIST = 156;
  /** 物件指定動作效果 */
  public static final int S_OPCODE_DOACTIONGFX = 158;
  /** 移出技能 */
  public static final int S_OPCODE_DELSKILL = 160;
  /** NPC喊話字串 */
  public static final int S_OPCODE_NPCSHOUT = 161;
  /** 加入技能 */
  public static final int S_OPCODE_ADDSKILL = 164;
  /** 更新物件外型中毒狀態 */
  public static final int S_OPCODE_POISON = 165;
  /** 效果圖標 : 體魄強健術/力量提升*/
  public static final int S_OPCODE_STRUP = 166;
  /** 更新角色自身物件隱形狀態 */
  public static final int S_OPCODE_INVIS = 171;
  /** 更新角色防禦 屬性防御*/
  public static final int S_OPCODE_OWNCHARATTRDEF = 174;
  /** 倉庫/項圈 物品清單 */
  public static final int S_OPCODE_SHOWRETRIEVELIST = 176;
  /** 角色列表 */
  public static final int S_OPCODE_CHARAMOUNT = 178;
  /** 角色封號 */
  public static final int S_OPCODE_CHARTITLE = 183;
  /** 稅收設定封包 */
  public static final int S_OPCODE_TAXRATE = 185;
  /** 郵件封包 */
  public static final int S_OPCODE_MAIL = 186;
  /** 血盟小屋位置地圖 */
  public static final int S_OPCODE_HOUSEMAP = 187;
  /** 效果圖標 : 通暢氣脈術/敏捷提升*/
  public static final int S_OPCODE_DEXUP = 188;
  
  public static final int S_OPCODE_CLANMARKSEE = 200;
  /** 魔法效果 : 詛咒相關 */
  public static final int S_OPCODE_PARALYSIS = 202;
  /** 更新角色所在的地圖 */
  public static final int S_OPCODE_MAPID = 206;
  
  public static final int S_OPCODE_UNDERWATER = 206;
  /** 更新門開關屬性 */
  public static final int S_OPCODE_ATTRIBUTE = 209;
  /** 魔法圖標 : 防禦纇 */
  public static final int S_OPCODE_SKILLICONSHIELD = 216;
  /** 選項封包 { Yes | No } */
  public static final int S_OPCODE_YES_NO = 219;
  /** 進入遊戲 */
  public static final int S_OPCODE_LOGINTOGAME = 223;
  /** 體力更新封包 */
  public static final int S_OPCODE_HPUPDATE = 225;
  /** 立即中斷連線 */
  public static final int S_OPCODE_DISCONNECT = 227;
  /** 輸入帳號密碼的結果事件 */
  public static final int S_OPCODE_LOGINRESULT = 233;
  /** 控制一個物件 */
  public static final int S_OPCODE_SELECTTARGET = 236;
  /** 物件血條 */
  public static final int S_OPCODE_HPMETER = 237;
  /** 物品祝福狀態 */
  public static final int S_OPCODE_ITEMCOLOR = 240;
  /** 廣播聊天頻道 / 伺服器訊息 ( 字串 ) */
  public static final int S_OPCODE_GLOBALCHAT = 243;
  /** 物品鑑定資訊訊息 */
  public static final int S_OPCODE_IDENTIFYDESC = 245;
  /** 多功能封包(封包盒子) */
  public static final int S_OPCODE_PACKETBOX = 250;
  
  public static final int S_OPCODE_ACTIVESPELLS = 250;
  
  public static final int S_OPCODE_SKILLICONGFX = 250;
  
  public static final int S_OPCODE_GREENMESSAGE = 250;
  /** 效果圖標 : 1段加速 */
  public static final int S_OPCODE_SKILLHASTE = 255;
  /** 經驗值更新封包  */
  public static final int S_OPCODE_EXP = 113;
  /** 圍城時間設定 */
  public static final int S_OPCODE_WARTIME = 231;
  /** 吉倫魔法學習購買清單(金幣) */
  public static final int S_OPCODE_SKILLBUY = 41;
  
  public static final int S_OPCODE_SKILLBUYITEM = 41;
  /** 寵物新增主人名稱 */
  public static final int S_OPCODE_NEWMASTER = 88;
  /** 座標點鎖定(角色異常穿透物件) */
  public static final int S_OPCODE_TELEPORTLOCK = 241;
  
  public static final int S_OPCODE_ITEMERROR = 197;
  
  //public static final int S_OPCODE_REDMESSAGETEST = 58;
  //public static final int S_OPCODE_BLUEMESSAGE = 1000005;
  //public static final int S_OPCODE_SYSMSG = 1000007;
  //public static final int S_OPCODE_FIX_WEAPON_MENU = 132;
  //public static final int S_OPCODE_CHARLOCK = 149;
  //public static final int S_OPCODE_MOVELOCK = 20;
  //public static final int S_OPCODE_DRAGONPERL = 31;
  //public static final int S_OPCODE_SPOLY = 230;
  
}


