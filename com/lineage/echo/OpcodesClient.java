package com.lineage.echo;

import com.lineage.server.serverpackets.OpcodesServer;

public class OpcodesClient
{
	protected static final int _seed = 0x70254d0a; // 381C_TW
	
	protected static final byte[] _firstPacket = { // 封包輸出不需要演算
		(byte) 0x12, // 全部封包長度
		(byte) 0x00,
		// 改版時不需要變動以上2個BYTE
		(byte) OpcodesServer.S_OPCODE_INITOPCODE, // 初始化封包*/
	
		// 3.63
		(byte) 0x0a,(byte) 0x4d,(byte) 0x25,(byte) 0x70,
		(byte) 0x67,(byte) 0xac,(byte) 0x7b,(byte) 0x01,
		(byte) 0xc1,(byte) 0xe1,(byte) 0x6a,(byte) 0xf0,
		(byte) 0x96,(byte) 0xed,(byte) 0xbf 
		 
	};
    public static final int C_OPCODE_EXTRAPACKET = 1;
    public static final int C_OPCODE_TRADE = 2;// 請求交易
    public static final int C_OPCODE_BOOKMARKDELETE = 3;// 請求刪除記憶座標
    public static final int C_OPCODE_BUDDYLIST = 4;//請求查詢好友名單
    public static final int C_OPCODE_FIGHT = 5;// 請求決鬥
    public static final int C_OPCODE_USESKILL = 6;// 請求使用技能
    public static final int C_OPCODE_CHANGECHAR = 7;// 請求切換角色
    public static final int C_OPCODE_BOARD = 10; // 請求瀏覽公佈欄
    public static final int C_OPCODE_AMOUNT = 11;// 請求傳回選取的數量
    public static final int C_OPCODE_PWD = 13;// 請求變更倉庫密碼 && 送出倉庫密碼
    public static final int C_OPCODE_CLIENTVERSION = 14; // 請求驗證客戶端版本
    public static final int C_OPCODE_COMMONCLICK = 16;// 公告 確認
    public static final int C_OPCODE_EMBLEM = 18;// 請求上傳盟徽
    public static final int C_OPCODE_TAXRATE = 19;// 請求配置稅收
    public static final int C_OPCODE_SELECTLIST = 20;// 請求修理道具
    public static final int C_OPCODE_DROPITEM = 25;// 請求丟棄物品
    public static final int C_OPCODE_LOGINTOSERVEROK = 26;// 請求配置角色設定
    public static final int C_OPCODE_MOVECHAR = 29;// 請求移動角色
    public static final int C_OPCODE_LEAVEPARTY = 33;// 請求退出隊伍
    public static final int C_OPCODE_NPCTALK = 34;// 請求對話視窗
    public static final int C_OPCODE_TRADEADDITEM = 37;// 請求交易(添加物品)
    public static final int C_OPCODE_SHOP = 38;// 請求開設個人商店
    public static final int C_OPCODE_CHATGLOBAL = 40;// 請求使用廣播聊天頻道
    public static final int C_OPCODE_DOOR = 41;// 請求開門或關門
    public static final int C_OPCODE_PARTY = 43;// 請求查詢隊伍成員
    public static final int C_OPCODE_DRAWAL = 44;// 請求領取城堡寶庫資金
    public static final int C_OPCODE_GIVEITEM = 45;// 請求給予物品
    public static final int C_OPCODE_PRIVATESHOPLIST = 47;// 請求購買指定的個人商店商品
    public static final int C_OPCODE_PROPOSE = 50;// 請求結婚
    public static final int C_OPCODE_CHECKPK = 51;// 請求查詢PK次數
    public static final int C_OPCODE_TELEPORT = 52;// 請求解除傳送鎖定
    public static final int C_OPCODE_TELEPORT2 = 246;
    public static final int C_OPCODE_DEPOSIT = 56;// 請求將資金存入城堡寶庫
    public static final int C_OPCODE_LEAVECLANE = 61;// 請求離開血盟
    public static final int C_OPCODE_FISHCLICK = 62;// 請求釣魚收竿
    public static final int C_OPCODE_RANK = 63;// 請求給予角色血盟階級
    public static final int C_OPCODE_PLEDGE = 68;// 請求查詢血盟成員
    public static final int C_OPCODE_BANCLAN = 69;// 請求驅逐血盟成員
    public static final int C_OPCODE_TRADEADDOK = 71;// 請求完成交易
    public static final int C_OPCODE_CLAN = 72;// 請求下載盟徽
    public static final int C_OPCODE_CLAN_RECOMMEND = 76;// 請求打開推薦血盟
    public static final int C_OPCODE_PLEDGECONTENT = 78;// 請求寫入血盟查詢名單內容
    public static final int C_OPCODE_NEWCHAR = 84;// 請求創造角色
    public static final int C_OPCODE_TRADEADDCANCEL = 86;// 請求取消交易
    public static final int C_OPCODE_MAIL = 87;// 請求閱讀信件
    public static final int C_OPCODE_TITLE = 90;// 請求賦予封號
    public static final int C_OPCODE_KEEPALIVE = 95;// 請求更新連線狀態
    public static final int C_OPCODE_CHARRESET = 98;// 要求重置人物點數
    public static final int C_OPCODE_PETMENU = 103;// 請求寵物回報選單
    public static final int C_OPCODE_PICKUPITEM = 112;// 請求拾取物品
    public static final int C_OPCODE_BOARDREAD = 114;// 請求閱讀佈告單個欄訊息
    public static final int C_OPCODE_FIX_WEAPON_LIST = 118;// 請求查詢損壞的道具
    public static final int C_OPCODE_EXTCOMMAND = 120;// 請求角色表情動作
    public static final int C_OPCODE_ATTR = 121;// 請求點選項目的結果
    public static final int C_OPCODE_QUITGAME = 122;// 請求離開遊戲
    public static final int C_OPCODE_ARROWATTACK = 123;// 請求使用遠距攻擊
    public static final int C_OPCODE_NPCACTION = 125;// 請求執行對話視窗的動作
    public static final int C_OPCODE_CASTLESECURITY = 128;// 請求管理城內治安
    public static final int C_OPCODE_CLANATTENTION = 129;// 請求使用血盟注視
    public static final int C_OPCODE_CHAT = 136;// 請求使用一般聊天頻道
    public static final int C_OPCODE_LOGINTOSERVER = 137;// 請求登錄角色
    public static final int C_OPCODE_DELETEINVENTORYITEM = 138;// 請求刪除物品
    public static final int C_OPCODE_BOARDWRITE = 141;// 請求撰寫新的佈告欄訊息
    public static final int C_OPCODE_BOARDDELETE = 153;// 請求刪除公佈欄內容
    public static final int C_OPCODE_RESULT = 161;// 請求取得列表中的項目
    public static final int C_OPCODE_DELETECHAR = 162;// 請求刪除角色
    public static final int C_OPCODE_USEITEM = 164;// 請求使用物品
    public static final int C_OPCODE_BOOKMARK = 165;// 請求增加記憶座標
    public static final int C_OPCODE_EXCLUDE = 171;// 請求使用拒絕名單(開啟指定人物訊息)
    public static final int C_OPCODE_EXIT_GHOST = 173;// 請求退出觀看模式
    public static final int C_OPCODE_RESTART = 177;// 請求死亡後重新開始
    public static final int C_OPCODE_CHATWHISPER = 184;// 請求使用密語聊天頻道
    public static final int C_OPCODE_CALL = 185;// 請求傳送至指定的外掛使用者身旁
    public static final int C_OPCODE_JOINCLAN = 194;// 請求加入血盟
    public static final int C_OPCODE_CAHTPARTY = 199;// 請求聊天隊伍
    public static final int C_OPCODE_DELBUDDY = 202;// 請求刪除好友
    public static final int C_OPCODE_WHO = 206;// 請求查詢遊戲人數
    public static final int C_OPCODE_ADDBUDDY = 207;// 請求新增好友
    public static final int C_OPCODE_BEANFUNLOGINPACKET = 210;// 請求登錄伺服器【beanfun】
    public static final int C_OPCODE_ENTERPORTAL = 219;// 請求傳送 (進入地監)
    public static final int C_OPCODE_CREATECLAN = 222;// 請求創立血盟
    public static final int C_OPCODE_SELECTTARGET = 223;// 請求攻擊指定物件(寵物&召喚)
    public static final int C_OPCODE_CHANGEHEADING = 225;// 請求改變角色面向
    public static final int C_OPCODE_WAR = 227;// 請求宣戰
    public static final int C_OPCODE_ATTACK = 229;// 請求攻擊對象
    public static final int C_OPCODE_CREATEPARTY = 230;// 請求邀請加入隊伍或建立隊伍
    public static final int C_OPCODE_SHIP = 231;// 請求下船
    public static final int C_OPCODE_CHARACTERCONFIG = 244;// 請求紀錄快速鍵
    public static final int C_OPCODE_SMS = 253;// 請求傳送簡訊
    public static final int C_OPCODE_WINDOWS = 254;// 請求傳送位置
    public static final int C_OPCODE_BANPARTY = 255;// 請求驅逐隊伍
    public static final int C_OPCODE_SKILLBUY = 145;// 請求查詢可以學習的魔法清單
    public static final int C_OPCODE_SKILLBUYOK = 39;
    public static final int C_OPCODE_SKILLBUYITEM = 245;// 要求使用寵物裝備 
    public static final int C_OPCODE_USEPETITEM = 104;// 要求使用寵物裝備 
    public static final int C_OPCODE_HIRESOLDIER = 1411;
    public static final int C_OPCODE_CHANGEWARTIME = 1443;//修正城堡總管全部功能
    public static final int C_OPCODE_PUTSOLDIER = 1453;//要求配置已僱用士兵
    public static final int C_OPCODE_SELECTWARTIME = 1463;//要求選擇 變更攻城時間(but3.3C無使用)
    public static final int C_OPCODE_PUTBOWSOLDIER = 1473;//要求配置城牆上弓手
    public static final int C_OPCODE_LOGINPACKET = 119;// 角色皇冠
    public static final int C_OPCODE_BOARDBACK = 23;
    public static final int C_OPCODE_CHECK = 99;
}
