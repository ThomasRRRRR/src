/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.CharObjidTable;
/*     */ import com.lineage.server.datatables.InnKeyTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.storage.DwarfForClanStorage;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.L1Object;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import com.lineage.server.world.World;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DwarfForClanTable
/*     */   implements DwarfForClanStorage
/*     */ {
/*  30 */   private static final Log _log = LogFactory.getLog(DwarfForClanTable.class);
/*     */   private static DwarfForClanTable _instance;
/*  32 */   private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList = new ConcurrentHashMap<>();
/*     */   public static DwarfForClanTable get() {
/*  34 */     if (_instance == null) {
/*  35 */       _instance = new DwarfForClanTable();
/*     */     }
/*  37 */     return _instance;
/*     */   }
/*     */   
/*     */   public void load() {
/*  41 */     PerformanceTimer timer = new PerformanceTimer();
/*  42 */     int i = 0;
/*  43 */     Connection con = null;
/*  44 */     PreparedStatement pstm = null;
/*  45 */     ResultSet rs = null;
/*     */     try {
/*  47 */       con = DatabaseFactory.get().getConnection();
/*  48 */       pstm = con.prepareStatement(
/*  49 */           "SELECT * FROM `clan_warehouse` order by item_id");
/*  50 */       rs = pstm.executeQuery();
/*     */       
/*  52 */       while (rs.next()) {
/*  53 */         int objid = rs.getInt("id");
/*  54 */         String clan_name = rs.getString("clan_name");
/*     */         
/*  56 */         int clan_id = CharObjidTable.get().clanObjid(clan_name);
/*  57 */         if (clan_id != 0) {
/*  58 */           int item_id = rs.getInt("item_id");
/*     */           
/*  60 */           long count = rs.getLong("count");
/*     */           
/*  62 */           int enchantlvl = rs.getInt("enchantlvl");
/*  63 */           int is_id = rs.getInt("is_id");
/*  64 */           int durability = rs.getInt("durability");
/*  65 */           int charge_count = rs.getInt("charge_count");
/*  66 */           int remaining_time = rs.getInt("remaining_time");
/*  67 */           Timestamp last_used = null;
/*     */           try {
/*  69 */             last_used = rs.getTimestamp("last_used");
/*  70 */           } catch (Exception e) {
/*  71 */             last_used = null;
/*     */           } 
/*  73 */           int bless = rs.getInt("bless");
/*  74 */           int attr_enchant_kind = rs.getInt("attr_enchant_kind");
/*  75 */           int attr_enchant_level = rs.getInt("attr_enchant_level");
/*  76 */           int itemAttack = rs.getInt("ItemAttack");
/*  77 */           int itemBowAttack = rs.getInt("ItemBowAttack");
/*  78 */           int itemHit = rs.getInt("ItemHit");
/*  79 */           int itemBowHit = rs.getInt("ItemBowHit");
/*  80 */           int itemReductionDmg = rs.getInt("ItemReductionDmg");
/*  81 */           int itemSp = rs.getInt("ItemSp");
/*  82 */           int itemprobability = rs.getInt("Itemprobability");
/*  83 */           int itemStr = rs.getInt("ItemStr");
/*  84 */           int itemDex = rs.getInt("ItemDex");
/*  85 */           int itemInt = rs.getInt("ItemInt");
/*  86 */           int itemCon = rs.getInt("ItemCon");
/*  87 */           int itemCha = rs.getInt("ItemCha");
/*  88 */           int itemWis = rs.getInt("ItemWis");
/*  89 */           int itemHp = rs.getInt("ItemHp");
/*  90 */           int itemMp = rs.getInt("ItemMp");
/*  91 */           int itemMr = rs.getInt("ItemMr");
/*  92 */           int itemAc = rs.getInt("ItemAc");
/*  93 */           int itemMag_Red = rs.getInt("ItemMag_Red");
/*  94 */           int itemMag_Hit = rs.getInt("ItemMag_Hit");
/*  95 */           int itemDg = rs.getInt("ItemDg");
/*  96 */           int itemistSustain = rs.getInt("ItemistSustain");
/*  97 */           int itemistStun = rs.getInt("ItemistStun");
/*  98 */           int itemistStone = rs.getInt("ItemistStone");
/*  99 */           int itemistSleep = rs.getInt("ItemistSleep");
/* 100 */           int itemistFreeze = rs.getInt("ItemistFreeze");
/* 101 */           int itemistBlind = rs.getInt("ItemistBlind");
/* 102 */           int itemArmorType = rs.getInt("ItemArmorType");
/* 103 */           int itemArmorLv = rs.getInt("ItemArmorLv");
/* 104 */           int skilltype = rs.getInt("skilltype");
/* 105 */           int skilltypelv = rs.getInt("skilltypelv");
/* 106 */           int itemType = rs.getInt("ItemType");
/* 107 */           int itemHpr = rs.getInt("ItemHpr");
/* 108 */           int itemMpr = rs.getInt("ItemMpr");
/* 109 */           int itemhppotion = rs.getInt("Itemhppotion");
/* 110 */           String racegamno = rs.getString("racegamno");
/*     */           
/* 112 */           L1ItemInstance item = new L1ItemInstance();
/* 113 */           item.setId(objid);
/*     */           
/* 115 */           L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
/* 116 */           if (itemTemplate == null) {
/*     */             
/* 118 */             errorItem(objid);
/*     */             continue;
/*     */           } 
/* 121 */           item.setItem(itemTemplate);
/* 122 */           item.setCount(count);
/* 123 */           item.setEquipped(false);
/* 124 */           item.setEnchantLevel(enchantlvl);
/* 125 */           item.setIdentified((is_id != 0));
/* 126 */           item.set_durability(durability);
/* 127 */           item.setChargeCount(charge_count);
/* 128 */           item.setRemainingTime(remaining_time);
/* 129 */           item.setLastUsed(last_used);
/* 130 */           item.setBless(bless);
/* 131 */           item.setAttrEnchantKind(attr_enchant_kind);
/* 132 */           item.setAttrEnchantLevel(attr_enchant_level);
/* 133 */           item.setItemAttack(itemAttack);
/* 134 */           item.setItemBowAttack(itemBowAttack);
/* 135 */           item.setItemHit(itemHit);
/* 136 */           item.setItemBowHit(itemBowHit);
/* 137 */           item.setItemReductionDmg(itemReductionDmg);
/* 138 */           item.setItemSp(itemSp);
/* 139 */           item.setItemprobability(itemprobability);
/* 140 */           item.setItemStr(itemStr);
/* 141 */           item.setItemDex(itemDex);
/* 142 */           item.setItemInt(itemInt);
/* 143 */           item.setItemCon(itemCon);
/* 144 */           item.setItemCha(itemCha);
/* 145 */           item.setItemWis(itemWis);
/* 146 */           item.setItemHp(itemHp);
/* 147 */           item.setItemMp(itemMp);
/* 148 */           item.setItemMr(itemMr);
/* 149 */           item.setItemAc(itemAc);
/* 150 */           item.setItemMag_Red(itemMag_Red);
/* 151 */           item.setItemMag_Hit(itemMag_Hit);
/* 152 */           item.setItemDg(itemDg);
/* 153 */           item.setItemistSustain(itemistSustain);
/* 154 */           item.setItemistStun(itemistStun);
/* 155 */           item.setItemistStone(itemistStone);
/* 156 */           item.setItemistSleep(itemistSleep);
/* 157 */           item.setItemistFreeze(itemistFreeze);
/* 158 */           item.setItemistBlind(itemistBlind);
/* 159 */           item.setItemArmorType(itemArmorType);
/* 160 */           item.setItemArmorLv(itemArmorLv);
/* 161 */           item.setskilltype(skilltype);
/* 162 */           item.setskilltypelv(skilltypelv);
/* 163 */           item.setItemType(itemType);
/* 164 */           item.setItemHpr(itemHpr);
/* 165 */           item.setItemMpr(itemMpr);
/* 166 */           item.setItemhppotion(itemhppotion);
/* 167 */           item.setraceGamNo(racegamno);
/*     */           
/* 169 */           item.setGamNo(rs.getInt("gamNo"));
/* 170 */           item.setGamNpcId(rs.getInt("gamNpcId"));
/*     */           
/* 172 */           item.setStarNpcId(rs.getString("starNpcId"));
/*     */           
/* 174 */           if (item.getItem().getItemId() == 40312) {
/* 175 */             InnKeyTable.checkey(item);
/*     */           }
/* 177 */           if (item.getItem().getItemId() == 82503) {
/* 178 */             InnKeyTable.checkey(item);
/*     */           }
/* 180 */           if (item.getItem().getItemId() == 82504) {
/* 181 */             InnKeyTable.checkey(item);
/*     */           }
/*     */           
/* 184 */           addItem(clan_name, item);
/* 185 */           i++;
/*     */           continue;
/*     */         } 
/* 188 */         deleteItem(clan_name);
/*     */       }
/*     */     
/*     */     }
/* 192 */     catch (SQLException e) {
/* 193 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 196 */       SQLUtil.close(rs);
/* 197 */       SQLUtil.close(pstm);
/* 198 */       SQLUtil.close(con);
/*     */     } 
/* 200 */     _log.info("載入血盟倉庫物件清單資料數量: " + _itemList.size() + "/" + i + "(" + timer.get() + 
/* 201 */         "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void errorItem(int objid) {
/* 206 */     Connection con = null;
/* 207 */     PreparedStatement pstm = null;
/*     */     try {
/* 209 */       con = DatabaseFactory.get().getConnection();
/* 210 */       pstm = con.prepareStatement(
/* 211 */           "DELETE FROM `clan_warehouse` WHERE `id`=?");
/* 212 */       pstm.setInt(1, objid);
/* 213 */       pstm.execute();
/*     */     }
/* 215 */     catch (SQLException e) {
/* 216 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 219 */       SQLUtil.close(pstm);
/* 220 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addItem(String clan_name, L1ItemInstance item) {
/* 226 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(clan_name);
/* 227 */     if (list == null) {
/* 228 */       list = new CopyOnWriteArrayList<>();
/* 229 */       if (!list.contains(item)) {
/* 230 */         list.add(item);
/*     */       
/*     */       }
/*     */     }
/* 234 */     else if (!list.contains(item)) {
/* 235 */       list.add(item);
/*     */     } 
/*     */     
/* 238 */     if (World.get().findObject(item.getId()) == null) {
/* 239 */       World.get().storeObject((L1Object)item);
/*     */     }
/* 241 */     _itemList.put(clan_name, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void deleteItem(String clan_name) {
/* 246 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.remove(clan_name);
/* 247 */     if (list != null)
/*     */     {
/* 249 */       for (L1ItemInstance item : list) {
/* 250 */         World.get().removeObject((L1Object)item);
/*     */       }
/*     */     }
/*     */     
/* 254 */     Connection cn = null;
/* 255 */     PreparedStatement ps = null;
/*     */     try {
/* 257 */       cn = DatabaseFactory.get().getConnection();
/* 258 */       ps = cn.prepareStatement(
/* 259 */           "DELETE FROM `clan_warehouse` WHERE `clan_name`=?");
/* 260 */       ps.setString(1, clan_name);
/* 261 */       ps.execute();
/*     */     }
/* 263 */     catch (SQLException e) {
/* 264 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 267 */       SQLUtil.close(ps);
/* 268 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CopyOnWriteArrayList<L1ItemInstance> loadItems(String clan_name) {
/* 274 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(clan_name);
/* 275 */     if (list != null) {
/* 276 */       return list;
/*     */     }
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delUserItems(String clan_name) {
/* 283 */     deleteItem(clan_name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUserItems(String clan_name, int objid, int count) {
/* 288 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(clan_name);
/* 289 */     if (list != null) {
/* 290 */       if (list.size() <= 0) {
/* 291 */         return false;
/*     */       }
/* 293 */       for (L1ItemInstance item : list) {
/* 294 */         if (item.getId() == objid && 
/* 295 */           item.getCount() >= count) {
/* 296 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertItem(String clan_name, L1ItemInstance item) {
///* 306 */     _log.warn("血盟:" + clan_name + " 加入血盟倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 307 */     addItem(clan_name, item);
/*     */     
/* 309 */     Connection con = null;
/* 310 */     PreparedStatement pstm = null;
/*     */     try {
/* 312 */       con = DatabaseFactory.get().getConnection();
/* 313 */       pstm = con.prepareStatement(
/* 314 */           "INSERT INTO `clan_warehouse` SET `id`=?,`clan_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
/*     */       
/* 316 */       int i = 0;
/* 317 */       pstm.setInt(++i, item.getId());
/* 318 */       pstm.setString(++i, clan_name);
/* 319 */       pstm.setInt(++i, item.getItemId());
/* 320 */       pstm.setString(++i, item.getItem().getName());
/* 321 */       pstm.setLong(++i, item.getCount());
/* 322 */       pstm.setInt(++i, item.getEnchantLevel());
/* 323 */       pstm.setInt(++i, item.isIdentified() ? 1 : 0);
/* 324 */       pstm.setInt(++i, item.get_durability());
/* 325 */       pstm.setInt(++i, item.getChargeCount());
/* 326 */       pstm.setInt(++i, item.getRemainingTime());
/* 327 */       pstm.setTimestamp(++i, item.getLastUsed());
/* 328 */       pstm.setInt(++i, item.getBless());
/* 329 */       pstm.setInt(++i, item.getAttrEnchantKind());
/* 330 */       pstm.setInt(++i, item.getAttrEnchantLevel());
/* 331 */       pstm.setInt(++i, item.getItemAttack());
/* 332 */       pstm.setInt(++i, item.getItemBowAttack());
/* 333 */       pstm.setInt(++i, item.getItemHit());
/* 334 */       pstm.setInt(++i, item.getItemBowHit());
/* 335 */       pstm.setInt(++i, item.getItemReductionDmg());
/* 336 */       pstm.setInt(++i, item.getItemSp());
/* 337 */       pstm.setInt(++i, item.getItemprobability());
/* 338 */       pstm.setInt(++i, item.getItemStr());
/* 339 */       pstm.setInt(++i, item.getItemDex());
/* 340 */       pstm.setInt(++i, item.getItemInt());
/* 341 */       pstm.setInt(++i, item.getItemCon());
/* 342 */       pstm.setInt(++i, item.getItemCha());
/* 343 */       pstm.setInt(++i, item.getItemWis());
/* 344 */       pstm.setInt(++i, item.getItemHp());
/* 345 */       pstm.setInt(++i, item.getItemMp());
/* 346 */       pstm.setInt(++i, item.getItemMr());
/* 347 */       pstm.setInt(++i, item.getItemAc());
/* 348 */       pstm.setInt(++i, item.getItemMag_Red());
/* 349 */       pstm.setInt(++i, item.getItemMag_Hit());
/* 350 */       pstm.setInt(++i, item.getItemDg());
/* 351 */       pstm.setInt(++i, item.getItemistSustain());
/* 352 */       pstm.setInt(++i, item.getItemistStun());
/* 353 */       pstm.setInt(++i, item.getItemistStone());
/* 354 */       pstm.setInt(++i, item.getItemistSleep());
/* 355 */       pstm.setInt(++i, item.getItemistFreeze());
/* 356 */       pstm.setInt(++i, item.getItemistBlind());
/* 357 */       pstm.setInt(++i, item.getItemArmorType());
/* 358 */       pstm.setInt(++i, item.getItemArmorLv());
/* 359 */       pstm.setInt(++i, item.getskilltype());
/* 360 */       pstm.setInt(++i, item.getItemType());
/* 361 */       pstm.setInt(++i, item.getItemHpr());
/* 362 */       pstm.setInt(++i, item.getItemMpr());
/* 363 */       pstm.setInt(++i, item.getItemhppotion());
/* 364 */       pstm.setInt(++i, item.getskilltypelv());
/*     */ 
/*     */       
/* 367 */       pstm.setInt(++i, item.getGamNo());
/* 368 */       pstm.setInt(++i, item.getGamNpcId());
/*     */       
/* 370 */       pstm.setString(++i, item.getStarNpcId());
/*     */       
/* 372 */       pstm.setString(++i, item.getraceGamNo());
/* 373 */       pstm.execute();
/*     */     }
/* 375 */     catch (SQLException e) {
/* 376 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 379 */       SQLUtil.close(pstm);
/* 380 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateItem(L1ItemInstance item) {
///* 386 */     _log.warn(" 更新血盟倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 387 */     Connection con = null;
/* 388 */     PreparedStatement pstm = null;
/*     */     try {
/* 390 */       con = DatabaseFactory.get().getConnection();
/* 391 */       pstm = con.prepareStatement(
/* 392 */           "UPDATE `clan_warehouse` SET `count`=? WHERE `id`=?");
/* 393 */       pstm.setLong(1, item.getCount());
/* 394 */       pstm.setInt(2, item.getId());
/* 395 */       pstm.execute();
/*     */     }
/* 397 */     catch (SQLException e) {
/* 398 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 401 */       SQLUtil.close(pstm);
/* 402 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteItem(String clan_name, L1ItemInstance item) {
/* 408 */     CopyOnWriteArrayList<?> list = _itemList.get(clan_name);
/* 409 */     if (list != null) {
///* 410 */       _log.warn("血盟:" + clan_name + " 血盟倉庫物品移出:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 411 */       list.remove(item);
/*     */       
/* 413 */       Connection con = null;
/* 414 */       PreparedStatement pstm = null;
/*     */       try {
/* 416 */         con = DatabaseFactory.get().getConnection();
/* 417 */         pstm = con.prepareStatement(
/* 418 */             "DELETE FROM `clan_warehouse` WHERE `id`=?");
/* 419 */         pstm.setInt(1, item.getId());
/* 420 */         pstm.execute();
/*     */       }
/* 422 */       catch (SQLException e) {
/* 423 */         _log.error(e.getLocalizedMessage(), e);
/*     */       } finally {
/*     */         
/* 426 */         SQLUtil.close(pstm);
/* 427 */         SQLUtil.close(con);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getUserItem(int objid) {
/* 440 */     for (CopyOnWriteArrayList<L1ItemInstance> list : _itemList.values()) {
/* 441 */       for (L1ItemInstance item : list) {
/* 442 */         if (item.getId() == objid) {
/* 443 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 447 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\DwarfForClanTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */