/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.server.utils.L1QueryUtil;

import java.sql.Timestamp;
/*     */ 
/*     */ 
/*     */ public class RecordTable
/*     */ {
/*     */   private static RecordTable _instance;
/*     */   
/*     */   public static RecordTable get() {
/*  12 */     if (_instance == null) {
/*  13 */       _instance = new RecordTable();
/*     */     }
/*  15 */     return _instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordblend(String pcName, String itemName, int itemCount, String ip) {
/*  23 */     String sql = "INSERT INTO 紀錄_火神_製作 (玩家,道具,數量,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
/*  24 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), ip });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordPcChangePassWord1(String accName, String pcName) {
/*  30 */     String sql = "INSERT INTO 紀錄_角色_刪人 (帳號, 玩家, 時間) VALUE (?, ?, SYSDATE())";
/*  31 */     L1QueryUtil.execute(sql, new Object[] { accName, pcName });
/*     */   }
/*     */   
/*     */   public void recordFailureArmor2(String pcName, String oldname, String newname, String ip) {
/*  35 */     String sql = "INSERT INTO 紀錄_物品_升級 (玩家,被升級,後升級,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
/*  36 */     L1QueryUtil.execute(sql, new Object[] { pcName, oldname, newname, ip });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordtakeitem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
/*  42 */     String sql = "INSERT INTO 紀錄_角色_撿物 (玩家,撿取物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  43 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordgivenpc(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
/*  49 */     String sql = "INSERT INTO 紀錄_角色_丟怪 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  50 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recorddropitem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
/*  56 */     String sql = "INSERT INTO 紀錄_角色_丟物 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  57 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordDeleItem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
/*  62 */     String sql = "INSERT INTO 紀錄_角色_刪物 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  63 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordeDeadItem(String pcName, String itemName, int itemCount, int itemObjid, String ip) {
/*  68 */     String sql = "INSERT INTO 紀錄_角色_噴裝 (玩家,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  69 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordeShop(String pcName, String targeName, String itemName, int itemCount, int itemObjid, int money, String moneyName, String ipA, String ipB) {
/*  74 */     String sql = "INSERT INTO 紀錄_角色_擺攤 (買方,賣方,物品,數量,編號,花費,幣種,買方IP,賣方IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/*  75 */     L1QueryUtil.execute(sql, new Object[] { pcName, targeName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), Integer.valueOf(money), moneyName, ipA, ipB });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordeTrade(String pcName, String targeName, String itemName, int itemCount, int itemObjid, String ipA, String ipB) {
/*  80 */     String sql = "INSERT INTO 紀錄_角色_交易 (交易者,接受者,物品,數量,編號,交易者IP,接受者IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/*  81 */     L1QueryUtil.execute(sql, new Object[] { pcName, targeName, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ipA, ipB });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordeTalk(String mod, String pcName, String pcClan, String targeName, String content) {
/*  86 */     String sql = "INSERT INTO 紀錄_聊天 (頻道,發話,血盟,對象,內容,時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/*  87 */     L1QueryUtil.execute(sql, new Object[] { mod, pcName, pcClan, targeName, content });
/*     */   }
/*     */ 
/*     */   
/*     */   public void aikill(String pcName) {
/*  92 */     String sql = "INSERT INTO 紀錄_AI驗證未通過 (玩家,時間) VALUE (?, SYSDATE())";
/*  93 */     L1QueryUtil.execute(sql, new Object[] { pcName });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordeCmd(String pcName, String cmd, String note, String ip) {
/* 100 */     String sql = "INSERT INTO 紀錄_gm_指令 (GM,指令,說明,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
/* 101 */     L1QueryUtil.execute(sql, new Object[] { pcName, cmd, note, ip });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordeSponsorItem(String accName, String pcName, int sponsor, String ip) {
/* 114 */     String sql = "INSERT INTO 紀錄_贊助_滿額禮 (帳號,玩家,滿額,IP,時間) VALUE (?, ?, ?, ?, SYSDATE())";
/* 115 */     L1QueryUtil.execute(sql, new Object[] { accName, pcName, Integer.valueOf(sponsor), ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordeItemEcho(String pcName, String itemName, int count, int item_obj, String s, String ip) {
/* 120 */     String sql = "INSERT INTO 紀錄_掉寶 (玩家,道具,數量,編號,來源,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
/* 121 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, Integer.valueOf(count), Integer.valueOf(item_obj), s, ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordPcChangePassWord(int pcid, String accName, String pcName, String changPass, String ip) {
/* 126 */     String sql = "INSERT INTO 紀錄_角色_改密 (編號, 帳號, 玩家, 改密, IP, 時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/* 127 */     L1QueryUtil.execute(sql, new Object[] { Integer.valueOf(pcid), accName, pcName, changPass, ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordPcChangeName(int pcid, String accName, String pcName, String changName, String ip) {
/* 132 */     String sql = "INSERT INTO 紀錄_角色_更名 (編號, 帳號, 玩家, 改名, IP, 時間) VALUE (?, ?, ?, ?, ?, SYSDATE())";
/* 133 */     L1QueryUtil.execute(sql, new Object[] { Integer.valueOf(pcid), accName, pcName, changName, ip });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordCastleReward(String castle, String pcName, String itemName, int count, int item_obj, String ip) {
/* 139 */     String sql = "INSERT INTO 紀錄_攻城獎勵 (城堡,玩家,物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
/* 140 */     L1QueryUtil.execute(sql, new Object[] { castle, pcName, itemName, Integer.valueOf(count), Integer.valueOf(item_obj), ip });
/*     */   }
/*     */ 
public void recordeYBGX(String accName,String pcName, int itemCount, String ip)
{
	String sql = "INSERT INTO transfers (account,name,amount,IP,time) VALUE (?, ?, ?, ?, SYSDATE())";
	L1QueryUtil.execute(sql, new Object[] {
			accName,pcName,Integer.valueOf(itemCount), ip
	});
}
/*     */   
/*     */   public void r_teleport(String pcName, String note) {
/* 145 */     String sql = "INSERT INTO 除錯_傳送 (玩家, 說明, 時間) VALUE (?, ?, SYSDATE())";
/* 146 */     L1QueryUtil.execute(sql, new Object[] { pcName, note });
/*     */   }
/*     */ 
/*     */   
/*     */   public void r_bug(String note) {
/* 151 */     String sql = "INSERT INTO 除錯_封包 (說明, 時間) VALUE (?, SYSDATE())";
/* 152 */     L1QueryUtil.execute(sql, new Object[] { note });
/*     */   }
/*     */ 
/*     */   
/*     */   public void r_speed(String pcName, String note) {
/* 157 */     String sql = "INSERT INTO 紀錄_加速 (玩家, 說明, 時間) VALUE (?, ?, SYSDATE())";
/* 158 */     L1QueryUtil.execute(sql, new Object[] { pcName, note });
/*     */   }
/*     */   
/*     */   public void recordFailureArmor(String pcName, String itemName, String armorName, int armorObjid, String note, String ip) {
/* 162 */     String sql = "INSERT INTO 紀錄_角色_衝裝 (玩家,使用,裝備,編號,說明,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
/* 163 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, armorName, Integer.valueOf(armorObjid), note, ip });
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordFailureArmor1(String pcName, String itemName, String armorName, int armorObjid, String note, String ip) {
/* 168 */     String sql = "INSERT INTO 紀錄_裝武_保護 (玩家,使用,裝備,編號,說明,IP,時間) VALUE (?, ?, ?, ?, ?, ?, SYSDATE())";
/* 169 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, armorName, Integer.valueOf(armorObjid), note, ip });
/*     */   }
/*     */   
/*     */   public void recordeWarehouse_elf(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
/* 173 */     String sql = "INSERT INTO 紀錄_角色_妖倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/* 174 */     L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */   
/*     */   public void recordeWarehouse_clan(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
/* 178 */     String sql = "INSERT INTO 紀錄_角色_盟倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/* 179 */     L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */   
/*     */   public void recordeWarehouse_pc(String pcName, String action, String warehouse, String itemName, int itemCount, int itemObjid, String ip) {
/* 183 */     String sql = "INSERT INTO 紀錄_角色_個倉 (玩家,執行,倉庫,道具,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/* 184 */     L1QueryUtil.execute(sql, new Object[] { pcName, action, warehouse, itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */   
/*     */   public void recordeSponsor(String pcName, int sponsor, int allowance, String clanid, String ip) {
/* 188 */     String sql = "INSERT INTO record_sponsorship (player,sponsor,other,clan,IP,time) VALUE (?, ?, ?, ?, ?,SYSDATE())";
/* 189 */     L1QueryUtil.execute(sql, new Object[] { pcName, Integer.valueOf(sponsor), Integer.valueOf(allowance), clanid, ip });
/*     */   }
/*     */   
/*     */   public void recordeShop1(String pcName, String moneyName, int moneyCount, String itemName, int itemCount, int itemObjid, String ip) {
/* 193 */     String sql = "INSERT INTO 紀錄_消費 (玩家,幣種,消費,物品,數量,編號,IP,時間) VALUE (?, ?, ?, ?, ?, ?, ?, SYSDATE())";
/* 194 */     L1QueryUtil.execute(sql, new Object[] { pcName, moneyName, Integer.valueOf(moneyCount), itemName, Integer.valueOf(itemCount), Integer.valueOf(itemObjid), ip });
/*     */   }
/*     */   
/*     */   public void killpc(String pcName, String targeName) {
/* 198 */     String sql = "INSERT INTO 紀錄_角色_殺人 (生存,死亡,時間) VALUE (?, ?, SYSDATE())";
/* 199 */     L1QueryUtil.execute(sql, new Object[] { pcName, targeName });
/*     */   }
/*     */   
/*     */   public void rangiveitem(String pcName) {
/* 203 */     String sql = "INSERT INTO 紀錄_角色_隨機禮物 (玩家,時間) VALUE (?, SYSDATE())";
/* 204 */     L1QueryUtil.execute(sql, new Object[] { pcName });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recordbox(String pcName, String itemNamebox, String itemName, int itemCount) {
/* 210 */     String sql = "INSERT INTO 紀錄_角色_寶箱 (玩家,使用寶箱,獲得道具,數量,時間) VALUE (?, ?, ?, ?,  SYSDATE())";
/* 211 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemNamebox, itemName, Integer.valueOf(itemCount) });
/*     */   }
/*     */   public void recordItemTime(String pcName, String itemName, Timestamp timer, int item_obj) {
/* 214 */     String sql = "INSERT INTO 紀錄_物品_時間 (玩家,道具,期限,編號,時間) VALUE (?, ?, ?, ?, SYSDATE())";
/* 215 */     L1QueryUtil.execute(sql, new Object[] { pcName, itemName, timer, Integer.valueOf(item_obj) });
/*     */   }
/*     */   
/*     */   public void guaji(String pcName, String action) {
/* 219 */     String sql = "INSERT INTO 紀錄_角色_使用掛機 (玩家,開啟方式,時間) VALUE (?,?, SYSDATE())";
/* 220 */     L1QueryUtil.execute(sql, new Object[] { pcName, action });
/*     */   }
/*     */   
/*     */   public void reshp(String pcName) {
/* 224 */     String sql = "INSERT INTO 紀錄_玩家洗血 (玩家,時間) VALUE (?, SYSDATE())";
/* 225 */     L1QueryUtil.execute(sql, new Object[] { pcName });
/*     */   }
/*     */   
/*     */   public void reshp1(String pcName) {
/* 229 */     String sql = "INSERT INTO 紀錄_回憶蠟燭(玩家,時間) VALUE (?, SYSDATE())";
/* 230 */     L1QueryUtil.execute(sql, new Object[] { pcName });
/*     */   }
/*     */
 }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\server\datatables\RecordTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */