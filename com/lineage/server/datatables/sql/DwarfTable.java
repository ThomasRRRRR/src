/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.InnKeyTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.AccountReading;
/*     */ import com.lineage.server.datatables.storage.DwarfStorage;
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
/*     */ public class DwarfTable
/*     */   implements DwarfStorage
/*     */ {
/*  29 */   private static final Log _log = LogFactory.getLog(DwarfTable.class);
/*     */   private static DwarfTable _instance;
/*  31 */   private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList = new ConcurrentHashMap<>();
/*     */   public static DwarfTable get() {
/*  33 */     if (_instance == null) {
/*  34 */       _instance = new DwarfTable();
/*     */     }
/*  36 */     return _instance;
/*     */   }
/*     */   
/*     */   public void load() {
/*  40 */     PerformanceTimer timer = new PerformanceTimer();
/*  41 */     int i = 0;
/*  42 */     Connection co = null;
/*  43 */     PreparedStatement ps = null;
/*  44 */     ResultSet rs = null;
/*     */     try {
/*  46 */       co = DatabaseFactory.get().getConnection();
/*  47 */       ps = co.prepareStatement(
/*  48 */           "SELECT * FROM character_warehouse order by item_id");
/*  49 */       rs = ps.executeQuery();
/*     */       
/*  51 */       while (rs.next()) {
/*  52 */         int objid = rs.getInt("id");
/*  53 */         String account_name = rs.getString("account_name").toLowerCase();
/*     */         
/*  55 */         boolean account = AccountReading.get().isAccountUT(account_name);
/*  56 */         if (account) {
/*  57 */           int item_id = rs.getInt("item_id");
/*     */           
/*  59 */           long count = rs.getLong("count");
/*     */           
/*  61 */           int enchantlvl = rs.getInt("enchantlvl");
/*  62 */           int is_id = rs.getInt("is_id");
/*  63 */           int durability = rs.getInt("durability");
/*  64 */           int charge_count = rs.getInt("charge_count");
/*  65 */           int remaining_time = rs.getInt("remaining_time");
/*  66 */           Timestamp last_used = null;
/*     */           try {
/*  68 */             last_used = rs.getTimestamp("last_used");
/*  69 */           } catch (Exception e) {
/*  70 */             last_used = null;
/*     */           } 
/*  72 */           int bless = rs.getInt("bless");
/*  73 */           int attr_enchant_kind = rs.getInt("attr_enchant_kind");
/*  74 */           int attr_enchant_level = rs.getInt("attr_enchant_level");
/*  75 */           int itemAttack = rs.getInt("ItemAttack");
/*  76 */           int itemBowAttack = rs.getInt("ItemBowAttack");
/*  77 */           int itemHit = rs.getInt("ItemHit");
/*  78 */           int itemBowHit = rs.getInt("ItemBowHit");
/*  79 */           int itemReductionDmg = rs.getInt("ItemReductionDmg");
/*  80 */           int itemSp = rs.getInt("ItemSp");
/*  81 */           int itemprobability = rs.getInt("Itemprobability");
/*  82 */           int itemStr = rs.getInt("ItemStr");
/*  83 */           int itemDex = rs.getInt("ItemDex");
/*  84 */           int itemInt = rs.getInt("ItemInt");
/*  85 */           int itemCon = rs.getInt("ItemCon");
/*  86 */           int itemCha = rs.getInt("ItemCha");
/*  87 */           int itemWis = rs.getInt("ItemWis");
/*  88 */           int itemHp = rs.getInt("ItemHp");
/*  89 */           int itemMp = rs.getInt("ItemMp");
/*  90 */           int itemMr = rs.getInt("ItemMr");
/*  91 */           int itemAc = rs.getInt("ItemAc");
/*  92 */           int itemMag_Red = rs.getInt("ItemMag_Red");
/*  93 */           int itemMag_Hit = rs.getInt("ItemMag_Hit");
/*  94 */           int itemDg = rs.getInt("ItemDg");
/*  95 */           int itemistSustain = rs.getInt("ItemistSustain");
/*  96 */           int itemistStun = rs.getInt("ItemistStun");
/*  97 */           int itemistStone = rs.getInt("ItemistStone");
/*  98 */           int itemistSleep = rs.getInt("ItemistSleep");
/*  99 */           int itemistFreeze = rs.getInt("ItemistFreeze");
/* 100 */           int itemistBlind = rs.getInt("ItemistBlind");
/* 101 */           int itemArmorType = rs.getInt("ItemArmorType");
/* 102 */           int itemArmorLv = rs.getInt("ItemArmorLv");
/* 103 */           int skilltype = rs.getInt("skilltype");
/* 104 */           int skilltypelv = rs.getInt("skilltypelv");
/* 105 */           int itemType = rs.getInt("ItemType");
/* 106 */           int itemHpr = rs.getInt("ItemHpr");
/* 107 */           int itemMpr = rs.getInt("ItemMpr");
/* 108 */           int itemhppotion = rs.getInt("Itemhppotion");
/* 109 */           String racegamno = rs.getString("racegamno");
/*     */           
/* 111 */           L1ItemInstance item = new L1ItemInstance();
/* 112 */           item.setId(objid);
/*     */           
/* 114 */           L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
/* 115 */           if (itemTemplate == null) {
/*     */             
/* 117 */             errorItem(objid);
/*     */             continue;
/*     */           } 
/* 120 */           item.setItem(itemTemplate);
/* 121 */           item.setCount(count);
/* 122 */           item.setEquipped(false);
/* 123 */           item.setEnchantLevel(enchantlvl);
/* 124 */           item.setIdentified((is_id != 0));
/* 125 */           item.set_durability(durability);
/* 126 */           item.setChargeCount(charge_count);
/* 127 */           item.setRemainingTime(remaining_time);
/* 128 */           item.setLastUsed(last_used);
/* 129 */           item.setBless(bless);
/* 130 */           item.setAttrEnchantKind(attr_enchant_kind);
/* 131 */           item.setAttrEnchantLevel(attr_enchant_level);
/* 132 */           item.setItemAttack(itemAttack);
/* 133 */           item.setItemBowAttack(itemBowAttack);
/* 134 */           item.setItemHit(itemHit);
/* 135 */           item.setItemBowHit(itemBowHit);
/* 136 */           item.setItemReductionDmg(itemReductionDmg);
/* 137 */           item.setItemSp(itemSp);
/* 138 */           item.setItemprobability(itemprobability);
/* 139 */           item.setItemStr(itemStr);
/* 140 */           item.setItemDex(itemDex);
/* 141 */           item.setItemInt(itemInt);
/* 142 */           item.setItemCon(itemCon);
/* 143 */           item.setItemCha(itemCha);
/* 144 */           item.setItemWis(itemWis);
/* 145 */           item.setItemHp(itemHp);
/* 146 */           item.setItemMp(itemMp);
/* 147 */           item.setItemMr(itemMr);
/* 148 */           item.setItemAc(itemAc);
/* 149 */           item.setItemMag_Red(itemMag_Red);
/* 150 */           item.setItemMag_Hit(itemMag_Hit);
/* 151 */           item.setItemDg(itemDg);
/* 152 */           item.setItemistSustain(itemistSustain);
/* 153 */           item.setItemistStun(itemistStun);
/* 154 */           item.setItemistStone(itemistStone);
/* 155 */           item.setItemistSleep(itemistSleep);
/* 156 */           item.setItemistFreeze(itemistFreeze);
/* 157 */           item.setItemistBlind(itemistBlind);
/* 158 */           item.setItemArmorType(itemArmorType);
/* 159 */           item.setItemArmorLv(itemArmorLv);
/* 160 */           item.setskilltype(skilltype);
/* 161 */           item.setskilltypelv(skilltypelv);
/* 162 */           item.setItemType(itemType);
/* 163 */           item.setItemHpr(itemHpr);
/* 164 */           item.setItemMpr(itemMpr);
/* 165 */           item.setItemhppotion(itemhppotion);
/* 166 */           item.setraceGamNo(racegamno);
/*     */           
/* 168 */           item.setGamNo(rs.getInt("gamNo"));
/* 169 */           item.setGamNpcId(rs.getInt("gamNpcId"));
/*     */           
/* 171 */           item.setStarNpcId(rs.getString("starNpcId"));
/*     */           
/* 173 */           if (item.getItem().getItemId() == 40312) {
/* 174 */             InnKeyTable.checkey(item);
/*     */           }
/* 176 */           if (item.getItem().getItemId() == 82503) {
/* 177 */             InnKeyTable.checkey(item);
/*     */           }
/* 179 */           if (item.getItem().getItemId() == 82504) {
/* 180 */             InnKeyTable.checkey(item);
/*     */           }
/*     */           
/* 183 */           addItem(account_name, item);
/* 184 */           i++;
/*     */           continue;
/*     */         } 
/* 187 */         deleteItem(account_name);
/*     */       }
/*     */     
/*     */     }
/* 191 */     catch (SQLException e) {
/* 192 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 195 */       SQLUtil.close(rs);
/* 196 */       SQLUtil.close(ps);
/* 197 */       SQLUtil.close(co);
/*     */     } 
/* 199 */     _log.info("載入帳號倉庫物件清單資料數量: " + _itemList.size() + "/" + i + "(" + timer.get() + 
/* 200 */         "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void errorItem(int objid) {
/* 205 */     Connection co = null;
/* 206 */     PreparedStatement ps = null;
/*     */     try {
/* 208 */       co = DatabaseFactory.get().getConnection();
/* 209 */       ps = co.prepareStatement(
/* 210 */           "DELETE FROM `character_warehouse` WHERE `id`=?");
/* 211 */       ps.setInt(1, objid);
/* 212 */       ps.execute();
/*     */     }
/* 214 */     catch (SQLException e) {
/* 215 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 218 */       SQLUtil.close(ps);
/* 219 */       SQLUtil.close(co);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addItem(String account_name, L1ItemInstance item) {
/* 229 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 230 */     if (list == null) {
/* 231 */       list = new CopyOnWriteArrayList<>();
/* 232 */       if (!list.contains(item)) {
/* 233 */         list.add(item);
/*     */       
/*     */       }
/*     */     }
/* 237 */     else if (!list.contains(item)) {
/* 238 */       list.add(item);
/*     */     } 
/*     */ 
/*     */     
/* 242 */     if (World.get().findObject(item.getId()) == null) {
/* 243 */       World.get().storeObject((L1Object)item);
/*     */     }
/* 245 */     _itemList.put(account_name, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void deleteItem(String account_name) {
/* 253 */     System.out.println("刪除遺失資料-帳號不存在");
/* 254 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.remove(account_name);
/* 255 */     if (list != null)
/*     */     {
/* 257 */       for (L1ItemInstance item : list) {
/* 258 */         World.get().removeObject((L1Object)item);
/*     */       }
/*     */     }
/*     */     
/* 262 */     Connection cn = null;
/* 263 */     PreparedStatement ps = null;
/*     */     try {
/* 265 */       cn = DatabaseFactory.get().getConnection();
/* 266 */       ps = cn.prepareStatement(
/* 267 */           "DELETE FROM `character_warehouse` WHERE `account_name`=?");
/* 268 */       ps.setString(1, account_name);
/* 269 */       ps.execute();
/*     */     }
/* 271 */     catch (SQLException e) {
/* 272 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 275 */       SQLUtil.close(ps);
/* 276 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, CopyOnWriteArrayList<L1ItemInstance>> allItems() {
/* 286 */     return _itemList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CopyOnWriteArrayList<L1ItemInstance> loadItems(String account_name) {
/* 295 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 296 */     if (list != null) {
/* 297 */       return list;
/*     */     }
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delUserItems(String account_name) {
/* 308 */     deleteItem(account_name);
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
/*     */   public boolean getUserItems(String account_name, int objid, int count) {
/* 321 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 322 */     if (list != null) {
/* 323 */       if (list.size() <= 0) {
/* 324 */         return false;
/*     */       }
/* 326 */       for (L1ItemInstance item : list) {
/* 327 */         if (item.getId() == objid && 
/* 328 */           item.getCount() >= count) {
/* 329 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertItem(String account_name, L1ItemInstance item) {
///* 340 */     _log.warn("帳號:" + account_name + " 加入帳號倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 341 */     addItem(account_name, item);
/*     */     
/* 343 */     Connection co = null;
/* 344 */     PreparedStatement ps = null;
/*     */     try {
/* 346 */       co = DatabaseFactory.get().getConnection();
/* 347 */       ps = co.prepareStatement(
/* 348 */           "INSERT INTO `character_warehouse` SET `id`=?,`account_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
/*     */       
/* 350 */       int i = 0;
/* 351 */       ps.setInt(++i, item.getId());
/* 352 */       ps.setString(++i, account_name);
/* 353 */       ps.setInt(++i, item.getItemId());
/* 354 */       ps.setString(++i, item.getItem().getName());
/* 355 */       ps.setLong(++i, item.getCount());
/* 356 */       ps.setInt(++i, item.getEnchantLevel());
/* 357 */       ps.setInt(++i, item.isIdentified() ? 1 : 0);
/* 358 */       ps.setInt(++i, item.get_durability());
/* 359 */       ps.setInt(++i, item.getChargeCount());
/* 360 */       ps.setInt(++i, item.getRemainingTime());
/* 361 */       if (item.getLastUsed() != null) {
/* 362 */         System.out.println(item.getLastUsed().getTime());
/*     */       }
/* 364 */       ps.setTimestamp(++i, item.getLastUsed());
/* 365 */       ps.setInt(++i, item.getBless());
/* 366 */       ps.setInt(++i, item.getAttrEnchantKind());
/* 367 */       ps.setInt(++i, item.getAttrEnchantLevel());
/* 368 */       ps.setInt(++i, item.getItemAttack());
/* 369 */       ps.setInt(++i, item.getItemBowAttack());
/* 370 */       ps.setInt(++i, item.getItemHit());
/* 371 */       ps.setInt(++i, item.getItemBowHit());
/* 372 */       ps.setInt(++i, item.getItemReductionDmg());
/* 373 */       ps.setInt(++i, item.getItemSp());
/* 374 */       ps.setInt(++i, item.getItemprobability());
/* 375 */       ps.setInt(++i, item.getItemStr());
/* 376 */       ps.setInt(++i, item.getItemDex());
/* 377 */       ps.setInt(++i, item.getItemInt());
/* 378 */       ps.setInt(++i, item.getItemCon());
/* 379 */       ps.setInt(++i, item.getItemCha());
/* 380 */       ps.setInt(++i, item.getItemWis());
/* 381 */       ps.setInt(++i, item.getItemHp());
/* 382 */       ps.setInt(++i, item.getItemMp());
/* 383 */       ps.setInt(++i, item.getItemMr());
/* 384 */       ps.setInt(++i, item.getItemAc());
/* 385 */       ps.setInt(++i, item.getItemMag_Red());
/* 386 */       ps.setInt(++i, item.getItemMag_Hit());
/* 387 */       ps.setInt(++i, item.getItemDg());
/* 388 */       ps.setInt(++i, item.getItemistSustain());
/* 389 */       ps.setInt(++i, item.getItemistStun());
/* 390 */       ps.setInt(++i, item.getItemistStone());
/* 391 */       ps.setInt(++i, item.getItemistSleep());
/* 392 */       ps.setInt(++i, item.getItemistFreeze());
/* 393 */       ps.setInt(++i, item.getItemistBlind());
/* 394 */       ps.setInt(++i, item.getItemArmorType());
/* 395 */       ps.setInt(++i, item.getItemArmorLv());
/* 396 */       ps.setInt(++i, item.getskilltype());
/* 397 */       ps.setInt(++i, item.getskilltypelv());
/* 398 */       ps.setInt(++i, item.getItemType());
/* 399 */       ps.setInt(++i, item.getItemHpr());
/* 400 */       ps.setInt(++i, item.getItemMpr());
/* 401 */       ps.setInt(++i, item.getItemhppotion());
/*     */       
/* 403 */       ps.setInt(++i, item.getGamNo());
/* 404 */       ps.setInt(++i, item.getGamNpcId());
/*     */       
/* 406 */       ps.setString(++i, item.getStarNpcId());
/*     */       
/* 408 */       ps.setString(++i, item.getraceGamNo());
/* 409 */       ps.execute();
/*     */     }
/* 411 */     catch (SQLException e) {
/* 412 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 415 */       SQLUtil.close(ps);
/* 416 */       SQLUtil.close(co);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItem(L1ItemInstance item) {
///* 426 */     _log.warn("更新帳號倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 427 */     Connection con = null;
/* 428 */     PreparedStatement ps = null;
/*     */     try {
/* 430 */       con = DatabaseFactory.get().getConnection();
/* 431 */       ps = con.prepareStatement(
/* 432 */           "UPDATE `character_warehouse` SET `count`=? WHERE `id`=?");
/* 433 */       ps.setLong(1, item.getCount());
/* 434 */       ps.setInt(2, item.getId());
/* 435 */       ps.execute();
/*     */     }
/* 437 */     catch (SQLException e) {
/* 438 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 441 */       SQLUtil.close(ps);
/* 442 */       SQLUtil.close(con);
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
/*     */   public void deleteItem(String account_name, L1ItemInstance item) {
/* 454 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 455 */     if (list != null) {
///* 456 */       _log.warn("帳號:" + account_name + " 帳號倉庫物品移出 :" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 457 */       list.remove(item);
/*     */       
/* 459 */       Connection co = null;
/* 460 */       PreparedStatement pstm = null;
/*     */       try {
/* 462 */         co = DatabaseFactory.get().getConnection();
/* 463 */         pstm = co.prepareStatement(
/* 464 */             "DELETE FROM `character_warehouse` WHERE `id`=?");
/* 465 */         pstm.setInt(1, item.getId());
/* 466 */         pstm.execute();
/*     */       }
/* 468 */       catch (SQLException e) {
/* 469 */         _log.error(e.getLocalizedMessage(), e);
/*     */       } finally {
/*     */         
/* 472 */         SQLUtil.close(pstm);
/* 473 */         SQLUtil.close(co);
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
/* 486 */     for (CopyOnWriteArrayList<L1ItemInstance> list : _itemList.values()) {
/* 487 */       for (L1ItemInstance item : list) {
/* 488 */         if (item.getId() == objid) {
/* 489 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 493 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\DwarfTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */