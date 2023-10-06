/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.InnKeyTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.AccountReading;
/*     */ import com.lineage.server.datatables.storage.DwarfForElfStorage;
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
/*     */ public class DwarfForElfTable
/*     */   implements DwarfForElfStorage
/*     */ {
/*  29 */   private static final Log _log = LogFactory.getLog(DwarfForElfTable.class);
/*     */   
/*  31 */   private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   public void load() {
/*  35 */     PerformanceTimer timer = new PerformanceTimer();
/*  36 */     int i = 0;
/*  37 */     Connection con = null;
/*  38 */     PreparedStatement pstm = null;
/*  39 */     ResultSet rs = null;
/*     */     try {
/*  41 */       con = DatabaseFactory.get().getConnection();
/*  42 */       pstm = con.prepareStatement(
/*  43 */           "SELECT * FROM `character_elf_warehouse` order by item_id");
/*  44 */       rs = pstm.executeQuery();
/*     */       
/*  46 */       while (rs.next()) {
/*  47 */         int objid = rs.getInt("id");
/*  48 */         String account_name = rs.getString("account_name").toLowerCase();
/*     */         
/*  50 */         boolean account = AccountReading.get().isAccountUT(account_name);
/*  51 */         if (account) {
/*  52 */           int item_id = rs.getInt("item_id");
/*     */           
/*  54 */           long count = rs.getLong("count");
/*     */           
/*  56 */           int enchantlvl = rs.getInt("enchantlvl");
/*  57 */           int is_id = rs.getInt("is_id");
/*  58 */           int durability = rs.getInt("durability");
/*  59 */           int charge_count = rs.getInt("charge_count");
/*  60 */           int remaining_time = rs.getInt("remaining_time");
/*  61 */           Timestamp last_used = null;
/*     */           try {
/*  63 */             last_used = rs.getTimestamp("last_used");
/*  64 */           } catch (Exception e) {
/*  65 */             last_used = null;
/*     */           } 
/*  67 */           int bless = rs.getInt("bless");
/*  68 */           int attr_enchant_kind = rs.getInt("attr_enchant_kind");
/*  69 */           int attr_enchant_level = rs.getInt("attr_enchant_level");
/*  70 */           int itemAttack = rs.getInt("ItemAttack");
/*  71 */           int itemBowAttack = rs.getInt("ItemBowAttack");
/*  72 */           int itemHit = rs.getInt("ItemHit");
/*  73 */           int itemBowHit = rs.getInt("ItemBowHit");
/*  74 */           int itemReductionDmg = rs.getInt("ItemReductionDmg");
/*  75 */           int itemSp = rs.getInt("ItemSp");
/*  76 */           int itemprobability = rs.getInt("Itemprobability");
/*  77 */           int itemStr = rs.getInt("ItemStr");
/*  78 */           int itemDex = rs.getInt("ItemDex");
/*  79 */           int itemInt = rs.getInt("ItemInt");
/*  80 */           int itemCon = rs.getInt("ItemCon");
/*  81 */           int itemCha = rs.getInt("ItemCha");
/*  82 */           int itemWis = rs.getInt("ItemWis");
/*  83 */           int itemHp = rs.getInt("ItemHp");
/*  84 */           int itemMp = rs.getInt("ItemMp");
/*  85 */           int itemMr = rs.getInt("ItemMr");
/*  86 */           int itemAc = rs.getInt("ItemAc");
/*  87 */           int itemMag_Red = rs.getInt("ItemMag_Red");
/*  88 */           int itemMag_Hit = rs.getInt("ItemMag_Hit");
/*  89 */           int itemDg = rs.getInt("ItemDg");
/*  90 */           int itemistSustain = rs.getInt("ItemistSustain");
/*  91 */           int itemistStun = rs.getInt("ItemistStun");
/*  92 */           int itemistStone = rs.getInt("ItemistStone");
/*  93 */           int itemistSleep = rs.getInt("ItemistSleep");
/*  94 */           int itemistFreeze = rs.getInt("ItemistFreeze");
/*  95 */           int itemistBlind = rs.getInt("ItemistBlind");
/*  96 */           int itemArmorType = rs.getInt("ItemArmorType");
/*  97 */           int itemArmorLv = rs.getInt("ItemArmorLv");
/*  98 */           int skilltype = rs.getInt("skilltype");
/*  99 */           int skilltypelv = rs.getInt("skilltypelv");
/* 100 */           int itemType = rs.getInt("ItemType");
/* 101 */           int itemHpr = rs.getInt("ItemHpr");
/* 102 */           int itemMpr = rs.getInt("ItemMpr");
/* 103 */           int itemhppotion = rs.getInt("Itemhppotion");
/* 104 */           String racegamno = rs.getString("racegamno");
/*     */           
/* 106 */           L1ItemInstance item = new L1ItemInstance();
/* 107 */           item.setId(objid);
/*     */           
/* 109 */           L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
/* 110 */           if (itemTemplate == null) {
/*     */             
/* 112 */             errorItem(objid);
/*     */             continue;
/*     */           } 
/* 115 */           item.setItem(itemTemplate);
/* 116 */           item.setCount(count);
/* 117 */           item.setEquipped(false);
/* 118 */           item.setEnchantLevel(enchantlvl);
/* 119 */           item.setIdentified((is_id != 0));
/* 120 */           item.set_durability(durability);
/* 121 */           item.setChargeCount(charge_count);
/* 122 */           item.setRemainingTime(remaining_time);
/* 123 */           item.setLastUsed(last_used);
/* 124 */           item.setBless(bless);
/* 125 */           item.setAttrEnchantKind(attr_enchant_kind);
/* 126 */           item.setAttrEnchantLevel(attr_enchant_level);
/* 127 */           item.setItemAttack(itemAttack);
/* 128 */           item.setItemBowAttack(itemBowAttack);
/* 129 */           item.setItemHit(itemHit);
/* 130 */           item.setItemBowHit(itemBowHit);
/* 131 */           item.setItemReductionDmg(itemReductionDmg);
/* 132 */           item.setItemSp(itemSp);
/* 133 */           item.setItemprobability(itemprobability);
/* 134 */           item.setItemStr(itemStr);
/* 135 */           item.setItemDex(itemDex);
/* 136 */           item.setItemInt(itemInt);
/* 137 */           item.setItemCon(itemCon);
/* 138 */           item.setItemCha(itemCha);
/* 139 */           item.setItemWis(itemWis);
/* 140 */           item.setItemHp(itemHp);
/* 141 */           item.setItemMp(itemMp);
/* 142 */           item.setItemMr(itemMr);
/* 143 */           item.setItemAc(itemAc);
/* 144 */           item.setItemMag_Red(itemMag_Red);
/* 145 */           item.setItemMag_Hit(itemMag_Hit);
/* 146 */           item.setItemDg(itemDg);
/* 147 */           item.setItemistSustain(itemistSustain);
/* 148 */           item.setItemistStun(itemistStun);
/* 149 */           item.setItemistStone(itemistStone);
/* 150 */           item.setItemistSleep(itemistSleep);
/* 151 */           item.setItemistFreeze(itemistFreeze);
/* 152 */           item.setItemistBlind(itemistBlind);
/* 153 */           item.setItemArmorType(itemArmorType);
/* 154 */           item.setItemArmorLv(itemArmorLv);
/* 155 */           item.setskilltype(skilltype);
/* 156 */           item.setskilltypelv(skilltypelv);
/* 157 */           item.setItemType(itemType);
/* 158 */           item.setItemHpr(itemHpr);
/* 159 */           item.setItemMpr(itemMpr);
/* 160 */           item.setItemhppotion(itemhppotion);
/* 161 */           item.setraceGamNo(racegamno);
/*     */           
/* 163 */           item.setGamNo(rs.getInt("gamNo"));
/* 164 */           item.setGamNpcId(rs.getInt("gamNpcId"));
/*     */           
/* 166 */           item.setStarNpcId(rs.getString("starNpcId"));
/*     */           
/* 168 */           if (item.getItem().getItemId() == 40312) {
/* 169 */             InnKeyTable.checkey(item);
/*     */           }
/* 171 */           if (item.getItem().getItemId() == 82503) {
/* 172 */             InnKeyTable.checkey(item);
/*     */           }
/* 174 */           if (item.getItem().getItemId() == 82504) {
/* 175 */             InnKeyTable.checkey(item);
/*     */           }
/*     */           
/* 178 */           addItem(account_name, item);
/* 179 */           i++;
/*     */           continue;
/*     */         } 
/* 182 */         deleteItem(account_name);
/*     */       }
/*     */     
/*     */     }
/* 186 */     catch (SQLException e) {
/* 187 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 190 */       SQLUtil.close(rs);
/* 191 */       SQLUtil.close(pstm);
/* 192 */       SQLUtil.close(con);
/*     */     } 
/* 194 */     _log.info("載入精靈倉庫物件清單資料數量: " + _itemList.size() + "/" + i + "(" + timer.get() + 
/* 195 */         "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void errorItem(int objid) {
/* 200 */     Connection con = null;
/* 201 */     PreparedStatement pstm = null;
/*     */     try {
/* 203 */       con = DatabaseFactory.get().getConnection();
/* 204 */       pstm = con.prepareStatement(
/* 205 */           "DELETE FROM `character_elf_warehouse` WHERE `id`=?");
/* 206 */       pstm.setInt(1, objid);
/* 207 */       pstm.execute();
/*     */     }
/* 209 */     catch (SQLException e) {
/* 210 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 213 */       SQLUtil.close(pstm);
/* 214 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addItem(String account_name, L1ItemInstance item) {
/* 220 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 221 */     if (list == null) {
/* 222 */       list = new CopyOnWriteArrayList<>();
/* 223 */       if (!list.contains(item)) {
/* 224 */         list.add(item);
/*     */       
/*     */       }
/*     */     }
/* 228 */     else if (!list.contains(item)) {
/* 229 */       list.add(item);
/*     */     } 
/*     */     
/* 232 */     if (World.get().findObject(item.getId()) == null) {
/* 233 */       World.get().storeObject((L1Object)item);
/*     */     }
/* 235 */     _itemList.put(account_name, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void deleteItem(String account_name) {
/* 240 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.remove(account_name);
/* 241 */     if (list != null)
/*     */     {
/* 243 */       for (L1ItemInstance item : list) {
/* 244 */         World.get().removeObject((L1Object)item);
/*     */       }
/*     */     }
/*     */     
/* 248 */     Connection cn = null;
/* 249 */     PreparedStatement ps = null;
/*     */     try {
/* 251 */       cn = DatabaseFactory.get().getConnection();
/* 252 */       ps = cn.prepareStatement(
/* 253 */           "DELETE FROM `character_elf_warehouse` WHERE `account_name`=?");
/* 254 */       ps.setString(1, account_name);
/* 255 */       ps.execute();
/*     */     }
/* 257 */     catch (SQLException e) {
/* 258 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 261 */       SQLUtil.close(ps);
/* 262 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CopyOnWriteArrayList<L1ItemInstance> loadItems(String account_name) {
/* 268 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 269 */     if (list != null) {
/* 270 */       return list;
/*     */     }
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delUserItems(String account_name) {
/* 277 */     deleteItem(account_name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUserItems(String account_name, int objid, int count) {
/* 282 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 283 */     if (list != null) {
/* 284 */       if (list.size() <= 0) {
/* 285 */         return false;
/*     */       }
/* 287 */       for (L1ItemInstance item : list) {
/* 288 */         if (item.getId() == objid && 
/* 289 */           item.getCount() >= count) {
/* 290 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertItem(String account_name, L1ItemInstance item) {
///* 300 */     _log.warn("帳號:" + account_name + " 加入妖精倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 301 */     addItem(account_name, item);
/*     */     
/* 303 */     Connection con = null;
/* 304 */     PreparedStatement pstm = null;
/*     */     try {
/* 306 */       con = DatabaseFactory.get().getConnection();
/* 307 */       pstm = con.prepareStatement(
/* 308 */           "INSERT INTO `character_elf_warehouse` SET `id`=?,`account_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
/*     */       
/* 310 */       int i = 0;
/* 311 */       pstm.setInt(++i, item.getId());
/* 312 */       pstm.setString(++i, account_name);
/* 313 */       pstm.setInt(++i, item.getItemId());
/* 314 */       pstm.setString(++i, item.getItem().getName());
/* 315 */       pstm.setLong(++i, item.getCount());
/* 316 */       pstm.setInt(++i, item.getEnchantLevel());
/* 317 */       pstm.setInt(++i, item.isIdentified() ? 1 : 0);
/* 318 */       pstm.setInt(++i, item.get_durability());
/* 319 */       pstm.setInt(++i, item.getChargeCount());
/* 320 */       pstm.setInt(++i, item.getRemainingTime());
/* 321 */       pstm.setTimestamp(++i, item.getLastUsed());
/* 322 */       pstm.setInt(++i, item.getBless());
/* 323 */       pstm.setInt(++i, item.getAttrEnchantKind());
/* 324 */       pstm.setInt(++i, item.getAttrEnchantLevel());
/* 325 */       pstm.setInt(++i, item.getItemAttack());
/* 326 */       pstm.setInt(++i, item.getItemBowAttack());
/* 327 */       pstm.setInt(++i, item.getItemHit());
/* 328 */       pstm.setInt(++i, item.getItemBowHit());
/* 329 */       pstm.setInt(++i, item.getItemReductionDmg());
/* 330 */       pstm.setInt(++i, item.getItemSp());
/* 331 */       pstm.setInt(++i, item.getItemprobability());
/* 332 */       pstm.setInt(++i, item.getItemStr());
/* 333 */       pstm.setInt(++i, item.getItemDex());
/* 334 */       pstm.setInt(++i, item.getItemInt());
/* 335 */       pstm.setInt(++i, item.getItemCon());
/* 336 */       pstm.setInt(++i, item.getItemCha());
/* 337 */       pstm.setInt(++i, item.getItemWis());
/* 338 */       pstm.setInt(++i, item.getItemHp());
/* 339 */       pstm.setInt(++i, item.getItemMp());
/* 340 */       pstm.setInt(++i, item.getItemMr());
/* 341 */       pstm.setInt(++i, item.getItemAc());
/* 342 */       pstm.setInt(++i, item.getItemMag_Red());
/* 343 */       pstm.setInt(++i, item.getItemMag_Hit());
/* 344 */       pstm.setInt(++i, item.getItemDg());
/* 345 */       pstm.setInt(++i, item.getItemistSustain());
/* 346 */       pstm.setInt(++i, item.getItemistStun());
/* 347 */       pstm.setInt(++i, item.getItemistStone());
/* 348 */       pstm.setInt(++i, item.getItemistSleep());
/* 349 */       pstm.setInt(++i, item.getItemistFreeze());
/* 350 */       pstm.setInt(++i, item.getItemistBlind());
/* 351 */       pstm.setInt(++i, item.getItemArmorType());
/* 352 */       pstm.setInt(++i, item.getItemArmorLv());
/* 353 */       pstm.setInt(++i, item.getskilltype());
/* 354 */       pstm.setInt(++i, item.getskilltypelv());
/* 355 */       pstm.setInt(++i, item.getItemType());
/* 356 */       pstm.setInt(++i, item.getItemHpr());
/* 357 */       pstm.setInt(++i, item.getItemMpr());
/* 358 */       pstm.setInt(++i, item.getItemhppotion());
/*     */       
/* 360 */       pstm.setInt(++i, item.getGamNo());
/* 361 */       pstm.setInt(++i, item.getGamNpcId());
/*     */       
/* 363 */       pstm.setString(++i, item.getStarNpcId());
/*     */       
/* 365 */       pstm.setString(++i, item.getraceGamNo());
/* 366 */       pstm.execute();
/*     */     }
/* 368 */     catch (SQLException e) {
/* 369 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 372 */       SQLUtil.close(pstm);
/* 373 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateItem(L1ItemInstance item) {
///* 379 */     _log.warn(" 更新妖精倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 380 */     Connection con = null;
/* 381 */     PreparedStatement pstm = null;
/*     */     try {
/* 383 */       con = DatabaseFactory.get().getConnection();
/* 384 */       pstm = con.prepareStatement(
/* 385 */           "UPDATE `character_elf_warehouse` SET `count`=? WHERE `id`=?");
/* 386 */       pstm.setLong(1, item.getCount());
/* 387 */       pstm.setInt(2, item.getId());
/* 388 */       pstm.execute();
/*     */     }
/* 390 */     catch (SQLException e) {
/* 391 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 394 */       SQLUtil.close(pstm);
/* 395 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteItem(String account_name, L1ItemInstance item) {
/* 401 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(account_name);
/* 402 */     if (list != null) {
///* 403 */       _log.warn("帳號:" + account_name + " 妖精倉庫物品移出:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 404 */       list.remove(item);
/*     */       
/* 406 */       Connection con = null;
/* 407 */       PreparedStatement pstm = null;
/*     */       try {
/* 409 */         con = DatabaseFactory.get().getConnection();
/* 410 */         pstm = con.prepareStatement(
/* 411 */             "DELETE FROM `character_elf_warehouse` WHERE `id`=?");
/* 412 */         pstm.setInt(1, item.getId());
/* 413 */         pstm.execute();
/*     */       }
/* 415 */       catch (SQLException e) {
/* 416 */         _log.error(e.getLocalizedMessage(), e);
/*     */       } finally {
/*     */         
/* 419 */         SQLUtil.close(pstm);
/* 420 */         SQLUtil.close(con);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\DwarfForElfTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */