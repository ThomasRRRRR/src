/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.InnKeyTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.storage.DwarfForChaStorage;
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
/*     */ public class DwarfForChaTable
/*     */   implements DwarfForChaStorage
/*     */ {
/*  29 */   private static final Log _log = LogFactory.getLog(DwarfForChaTable.class);
/*     */   private static DwarfForChaTable _instance;
/*  31 */   private static final Map<String, CopyOnWriteArrayList<L1ItemInstance>> _itemList = new ConcurrentHashMap<>();
/*     */   public static DwarfForChaTable get() {
/*  33 */     if (_instance == null) {
/*  34 */       _instance = new DwarfForChaTable();
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
/*  48 */           "SELECT * FROM character_warehouse_for_cha order by item_id");
/*  49 */       rs = ps.executeQuery();
/*     */       
/*  51 */       while (rs.next()) {
/*  52 */         int objid = rs.getInt("id");
/*  53 */         String owner_name = rs.getString("owner_name");
/*     */         
/*  55 */         boolean owner = CharacterTable.doesCharNameExist(owner_name);
/*  56 */         if (owner) {
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
/* 105 */           String racegamno = rs.getString("racegamno");
/*     */           
/* 107 */           L1ItemInstance item = new L1ItemInstance();
/* 108 */           item.setId(objid);
/*     */           
/* 110 */           L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
/* 111 */           if (itemTemplate == null) {
/*     */             
/* 113 */             errorItem(objid);
/*     */             continue;
/*     */           } 
/* 116 */           item.setItem(itemTemplate);
/* 117 */           item.setCount(count);
/* 118 */           item.setEquipped(false);
/* 119 */           item.setEnchantLevel(enchantlvl);
/* 120 */           item.setIdentified((is_id != 0));
/* 121 */           item.set_durability(durability);
/* 122 */           item.setChargeCount(charge_count);
/* 123 */           item.setRemainingTime(remaining_time);
/* 124 */           item.setLastUsed(last_used);
/* 125 */           item.setBless(bless);
/* 126 */           item.setAttrEnchantKind(attr_enchant_kind);
/* 127 */           item.setAttrEnchantLevel(attr_enchant_level);
/* 128 */           item.setItemAttack(itemAttack);
/* 129 */           item.setItemBowAttack(itemBowAttack);
/* 130 */           item.setItemHit(itemHit);
/* 131 */           item.setItemBowHit(itemBowHit);
/* 132 */           item.setItemReductionDmg(itemReductionDmg);
/* 133 */           item.setItemSp(itemSp);
/* 134 */           item.setItemprobability(itemprobability);
/* 135 */           item.setItemStr(itemStr);
/* 136 */           item.setItemDex(itemDex);
/* 137 */           item.setItemInt(itemInt);
/* 138 */           item.setItemCon(itemCon);
/* 139 */           item.setItemCha(itemCha);
/* 140 */           item.setItemWis(itemWis);
/* 141 */           item.setItemHp(itemHp);
/* 142 */           item.setItemMp(itemMp);
/* 143 */           item.setItemMr(itemMr);
/* 144 */           item.setItemAc(itemAc);
/* 145 */           item.setItemMag_Red(itemMag_Red);
/* 146 */           item.setItemMag_Hit(itemMag_Hit);
/* 147 */           item.setItemDg(itemDg);
/* 148 */           item.setItemistSustain(itemistSustain);
/* 149 */           item.setItemistStun(itemistStun);
/* 150 */           item.setItemistStone(itemistStone);
/* 151 */           item.setItemistSleep(itemistSleep);
/* 152 */           item.setItemistFreeze(itemistFreeze);
/* 153 */           item.setItemistBlind(itemistBlind);
/* 154 */           item.setItemArmorType(itemArmorType);
/* 155 */           item.setItemArmorLv(itemArmorLv);
/* 156 */           item.setskilltype(skilltype);
/* 157 */           item.setskilltypelv(skilltypelv);
/* 158 */           item.setraceGamNo(racegamno);
/*     */           
/* 160 */           item.setGamNo(rs.getInt("gamNo"));
/* 161 */           item.setGamNpcId(rs.getInt("gamNpcId"));
/*     */           
/* 163 */           item.setStarNpcId(rs.getString("starNpcId"));
/*     */           
/* 165 */           if (item.getItem().getItemId() == 40312) {
/* 166 */             InnKeyTable.checkey(item);
/*     */           }
/* 168 */           if (item.getItem().getItemId() == 82503) {
/* 169 */             InnKeyTable.checkey(item);
/*     */           }
/* 171 */           if (item.getItem().getItemId() == 82504) {
/* 172 */             InnKeyTable.checkey(item);
/*     */           }
/*     */           
/* 175 */           addItem(owner_name, item);
/* 176 */           i++;
/*     */           continue;
/*     */         } 
/* 179 */         deleteItem(owner_name);
/*     */       }
/*     */     
/*     */     }
/* 183 */     catch (SQLException e) {
/* 184 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 187 */       SQLUtil.close(rs);
/* 188 */       SQLUtil.close(ps);
/* 189 */       SQLUtil.close(co);
/*     */     } 
/* 191 */     _log.info("載入角色專屬倉庫物件清單資料數量: " + _itemList.size() + "/" + i + "(" + timer.get() + 
/* 192 */         "ms)");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void errorItem(int objid) {
/* 197 */     Connection co = null;
/* 198 */     PreparedStatement ps = null;
/*     */     try {
/* 200 */       co = DatabaseFactory.get().getConnection();
/* 201 */       ps = co.prepareStatement(
/* 202 */           "DELETE FROM `character_warehouse_for_cha` WHERE `id`=?");
/* 203 */       ps.setInt(1, objid);
/* 204 */       ps.execute();
/*     */     }
/* 206 */     catch (SQLException e) {
/* 207 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 210 */       SQLUtil.close(ps);
/* 211 */       SQLUtil.close(co);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addItem(String owner_name, L1ItemInstance item) {
/* 222 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(owner_name);
/* 223 */     if (list == null) {
/* 224 */       list = new CopyOnWriteArrayList<>();
/* 225 */       if (!list.contains(item)) {
/* 226 */         list.add(item);
/*     */       
/*     */       }
/*     */     }
/* 230 */     else if (!list.contains(item)) {
/* 231 */       list.add(item);
/*     */     } 
/*     */ 
/*     */     
/* 235 */     if (World.get().findObject(item.getId()) == null) {
/* 236 */       World.get().storeObject((L1Object)item);
/*     */     }
/* 238 */     _itemList.put(owner_name, list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void deleteItem(String owner_name) {
/* 246 */     System.out.println("刪除遺失資料-角色名稱不存在");
/* 247 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.remove(owner_name);
/* 248 */     if (list != null)
/*     */     {
/* 250 */       for (L1ItemInstance item : list) {
/* 251 */         World.get().removeObject((L1Object)item);
/*     */       }
/*     */     }
/*     */     
/* 255 */     Connection cn = null;
/* 256 */     PreparedStatement ps = null;
/*     */     try {
/* 258 */       cn = DatabaseFactory.get().getConnection();
/* 259 */       ps = cn.prepareStatement(
/* 260 */           "DELETE FROM `character_warehouse_for_cha` WHERE `owner_name`=?");
/* 261 */       ps.setString(1, owner_name);
/* 262 */       ps.execute();
/*     */     }
/* 264 */     catch (SQLException e) {
/* 265 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 268 */       SQLUtil.close(ps);
/* 269 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, CopyOnWriteArrayList<L1ItemInstance>> allItems() {
/* 279 */     return _itemList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CopyOnWriteArrayList<L1ItemInstance> loadItems(String owner_name) {
/* 288 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(owner_name);
/*     */     
/* 290 */     if (list != null) {
/* 291 */       return list;
/*     */     }
/* 293 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delUserItems(String owner_name) {
/* 302 */     deleteItem(owner_name);
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
/*     */   public boolean getUserItems(String owner_name, int objid, int count) {
/* 315 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(owner_name);
/* 316 */     if (list != null) {
/* 317 */       if (list.size() <= 0) {
/* 318 */         return false;
/*     */       }
/* 320 */       for (L1ItemInstance item : list) {
/* 321 */         if (item.getId() == objid && 
/* 322 */           item.getCount() >= count) {
/* 323 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertItem(String owner_name, L1ItemInstance item) {
///* 334 */     _log.warn("角色:" + owner_name + " 加入角色專屬倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 335 */     addItem(owner_name, item);
/*     */     
/* 337 */     Connection co = null;
/* 338 */     PreparedStatement ps = null;
/*     */     try {
/* 340 */       co = DatabaseFactory.get().getConnection();
/* 341 */       ps = co.prepareStatement(
/* 342 */           "INSERT INTO `character_warehouse_for_cha` SET `id`=?,`owner_name`=?,`item_id`= ?,`item_name`=?,`count`=?,`is_equipped`=0,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?");
/*     */       
/* 344 */       int i = 0;
/* 345 */       ps.setInt(++i, item.getId());
/* 346 */       ps.setString(++i, owner_name);
/* 347 */       ps.setInt(++i, item.getItemId());
/* 348 */       ps.setString(++i, item.getItem().getName());
/* 349 */       ps.setLong(++i, item.getCount());
/* 350 */       ps.setInt(++i, item.getEnchantLevel());
/* 351 */       ps.setInt(++i, item.isIdentified() ? 1 : 0);
/* 352 */       ps.setInt(++i, item.get_durability());
/* 353 */       ps.setInt(++i, item.getChargeCount());
/* 354 */       ps.setInt(++i, item.getRemainingTime());
/* 355 */       if (item.getLastUsed() != null) {
/* 356 */         System.out.println(item.getLastUsed().getTime());
/*     */       }
/* 358 */       ps.setTimestamp(++i, item.getLastUsed());
/* 359 */       ps.setInt(++i, item.getBless());
/* 360 */       ps.setInt(++i, item.getAttrEnchantKind());
/* 361 */       ps.setInt(++i, item.getAttrEnchantLevel());
/* 362 */       ps.setInt(++i, item.getItemAttack());
/* 363 */       ps.setInt(++i, item.getItemBowAttack());
/* 364 */       ps.setInt(++i, item.getItemHit());
/* 365 */       ps.setInt(++i, item.getItemBowHit());
/* 366 */       ps.setInt(++i, item.getItemReductionDmg());
/* 367 */       ps.setInt(++i, item.getItemSp());
/* 368 */       ps.setInt(++i, item.getItemprobability());
/* 369 */       ps.setInt(++i, item.getItemStr());
/* 370 */       ps.setInt(++i, item.getItemDex());
/* 371 */       ps.setInt(++i, item.getItemInt());
/* 372 */       ps.setInt(++i, item.getItemCon());
/* 373 */       ps.setInt(++i, item.getItemCha());
/* 374 */       ps.setInt(++i, item.getItemWis());
/* 375 */       ps.setInt(++i, item.getItemHp());
/* 376 */       ps.setInt(++i, item.getItemMp());
/* 377 */       ps.setInt(++i, item.getItemMr());
/* 378 */       ps.setInt(++i, item.getItemAc());
/* 379 */       ps.setInt(++i, item.getItemMag_Red());
/* 380 */       ps.setInt(++i, item.getItemMag_Hit());
/* 381 */       ps.setInt(++i, item.getItemDg());
/* 382 */       ps.setInt(++i, item.getItemistSustain());
/* 383 */       ps.setInt(++i, item.getItemistStun());
/* 384 */       ps.setInt(++i, item.getItemistStone());
/* 385 */       ps.setInt(++i, item.getItemistSleep());
/* 386 */       ps.setInt(++i, item.getItemistFreeze());
/* 387 */       ps.setInt(++i, item.getItemistBlind());
/* 388 */       ps.setInt(++i, item.getItemArmorType());
/* 389 */       ps.setInt(++i, item.getItemArmorLv());
/* 390 */       ps.setInt(++i, item.getskilltype());
/* 391 */       ps.setInt(++i, item.getskilltypelv());
/*     */       
/* 393 */       ps.setInt(++i, item.getGamNo());
/* 394 */       ps.setInt(++i, item.getGamNpcId());
/*     */       
/* 396 */       ps.setString(++i, item.getStarNpcId());
/*     */       
/* 398 */       ps.setString(++i, item.getraceGamNo());
/* 399 */       ps.execute();
/*     */     }
/* 401 */     catch (SQLException e) {
/* 402 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 405 */       SQLUtil.close(ps);
/* 406 */       SQLUtil.close(co);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItem(L1ItemInstance item) {
///* 416 */     _log.warn("更新角色專屬倉庫數據:" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 417 */     Connection con = null;
/* 418 */     PreparedStatement ps = null;
/*     */     try {
/* 420 */       con = DatabaseFactory.get().getConnection();
/* 421 */       ps = con.prepareStatement(
/* 422 */           "UPDATE `character_warehouse_for_cha` SET `count`=? WHERE `id`=?");
/* 423 */       ps.setLong(1, item.getCount());
/* 424 */       ps.setInt(2, item.getId());
/* 425 */       ps.execute();
/*     */     }
/* 427 */     catch (SQLException e) {
/* 428 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 431 */       SQLUtil.close(ps);
/* 432 */       SQLUtil.close(con);
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
/*     */   public void deleteItem(String owner_name, L1ItemInstance item) {
/* 444 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(owner_name);
/* 445 */     if (list != null) {
///* 446 */       _log.warn("角色:" + owner_name + " 角色專屬倉庫物品移出 :" + item.getNumberedName(item.getCount()) + " OBJID:" + item.getId());
/* 447 */       list.remove(item);
/*     */       
/* 449 */       Connection co = null;
/* 450 */       PreparedStatement pstm = null;
/*     */       try {
/* 452 */         co = DatabaseFactory.get().getConnection();
/* 453 */         pstm = co.prepareStatement(
/* 454 */             "DELETE FROM `character_warehouse_for_cha` WHERE `id`=?");
/* 455 */         pstm.setInt(1, item.getId());
/* 456 */         pstm.execute();
/*     */       }
/* 458 */       catch (SQLException e) {
/* 459 */         _log.error(e.getLocalizedMessage(), e);
/*     */       } finally {
/*     */         
/* 462 */         SQLUtil.close(pstm);
/* 463 */         SQLUtil.close(co);
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
/*     */   public void updateCharName(String newname, String srcname) {
/* 475 */     Connection con = null;
/* 476 */     PreparedStatement ps = null;
/*     */     
/*     */     try {
/* 479 */       con = DatabaseFactory.get().getConnection();
/* 480 */       ps = con.prepareStatement("UPDATE `character_warehouse_for_cha` SET `owner_name`=? WHERE `owner_name`=?");
/* 481 */       ps.setString(1, newname);
/* 482 */       ps.setString(2, srcname);
/* 483 */       ps.execute();
/*     */     }
/* 485 */     catch (SQLException e) {
/* 486 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 489 */       SQLUtil.close(ps);
/* 490 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\DwarfForChaTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */