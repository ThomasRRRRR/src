/*      */ package com.lineage.server.datatables.sql;
/*      */ 
/*      */ import com.lineage.DatabaseFactory;
/*      */ import com.lineage.server.datatables.CharObjidTable;
/*      */ import com.lineage.server.datatables.InnKeyTable;
/*      */ import com.lineage.server.datatables.ItemTable;
/*      */ import com.lineage.server.datatables.storage.CharItemsStorage;
/*      */ import com.lineage.server.model.Instance.L1ItemInstance;
/*      */ import com.lineage.server.model.Instance.L1PcInstance;
/*      */ import com.lineage.server.model.L1Object;
/*      */ import com.lineage.server.templates.L1Item;
/*      */ import com.lineage.server.utils.PerformanceTimer;
/*      */ import com.lineage.server.utils.SQLUtil;
/*      */ import com.lineage.server.world.World;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CharItemsTable
/*      */   implements CharItemsStorage
/*      */ {
/*   35 */   private static final Log _log = LogFactory.getLog(BuddyTable.class);
/*      */   private static CharItemsTable _instance;
/*   37 */   private static final Map<Integer, CopyOnWriteArrayList<L1ItemInstance>> _itemList = new ConcurrentHashMap<>();
/*      */   public static CharItemsTable get() {
/*   39 */     if (_instance == null) {
/*   40 */       _instance = new CharItemsTable();
/*      */     }
/*   42 */     return _instance;
/*      */   }
/*      */   
/*      */   public void load() {
/*   46 */     PerformanceTimer timer = new PerformanceTimer();
/*   47 */     int i = 0;
/*   48 */     Connection cn = null;
/*   49 */     PreparedStatement ps = null;
/*   50 */     ResultSet rs = null;
/*      */     try {
/*   52 */       cn = DatabaseFactory.get().getConnection();
/*   53 */       ps = cn.prepareStatement("SELECT * FROM `character_items`");
/*   54 */       rs = ps.executeQuery();
/*      */       
/*   56 */       while (rs.next()) {
/*   57 */         int objid = rs.getInt("id");
/*   58 */         int item_id = rs.getInt("item_id");
/*   59 */         int char_id = rs.getInt("char_id");
/*      */         
/*   61 */         if (CharObjidTable.get().isChar(char_id) != null) {
/*   62 */           L1Item itemTemplate = ItemTable.get().getTemplate(item_id);
/*   63 */           if (itemTemplate == null) {
/*   64 */             errorItem(objid);
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*   69 */           long count = rs.getLong("count");
/*   70 */           int is_equipped = rs.getInt("is_equipped");
/*   71 */           int enchantlvl = rs.getInt("enchantlvl");
/*   72 */           int is_id = rs.getInt("is_id");
/*   73 */           int durability = rs.getInt("durability");
/*   74 */           int charge_count = rs.getInt("charge_count");
/*   75 */           int remaining_time = rs.getInt("remaining_time");
/*   76 */           Timestamp last_used = null;
/*      */           try {
/*   78 */             last_used = rs.getTimestamp("last_used");
/*   79 */           } catch (Exception e) {
/*   80 */             last_used = null;
/*      */           } 
/*   82 */           int bless = rs.getInt("bless");
/*   83 */           int attr_enchant_kind = rs.getInt("attr_enchant_kind");
/*   84 */           int attr_enchant_level = rs.getInt("attr_enchant_level");
/*   85 */           int itemAttack = rs.getInt("ItemAttack");
/*   86 */           int itemBowAttack = rs.getInt("ItemBowAttack");
/*   87 */           int itemHit = rs.getInt("ItemHit");
/*   88 */           int itemBowHit = rs.getInt("ItemBowHit");
/*   89 */           int itemReductionDmg = rs.getInt("ItemReductionDmg");
/*   90 */           int itemSp = rs.getInt("ItemSp");
/*   91 */           int itemprobability = rs.getInt("Itemprobability");
/*   92 */           int itemStr = rs.getInt("ItemStr");
/*   93 */           int itemDex = rs.getInt("ItemDex");
/*   94 */           int itemInt = rs.getInt("ItemInt");
/*   95 */           int itemCon = rs.getInt("ItemCon");
/*   96 */           int itemCha = rs.getInt("ItemCha");
/*   97 */           int itemWis = rs.getInt("ItemWis");
/*   98 */           int itemHp = rs.getInt("ItemHp");
/*   99 */           int itemMp = rs.getInt("ItemMp");
/*  100 */           int itemMr = rs.getInt("ItemMr");
/*  101 */           int itemAc = rs.getInt("ItemAc");
/*  102 */           int itemMag_Red = rs.getInt("ItemMag_Red");
/*  103 */           int itemMag_Hit = rs.getInt("ItemMag_Hit");
/*  104 */           int itemDg = rs.getInt("ItemDg");
/*  105 */           int itemistSustain = rs.getInt("ItemistSustain");
/*  106 */           int itemistStun = rs.getInt("ItemistStun");
/*  107 */           int itemistStone = rs.getInt("ItemistStone");
/*  108 */           int itemistSleep = rs.getInt("ItemistSleep");
/*  109 */           int itemistFreeze = rs.getInt("ItemistFreeze");
/*  110 */           int itemistBlind = rs.getInt("ItemistBlind");
/*  111 */           int itemArmorType = rs.getInt("ItemArmorType");
/*  112 */           int itemArmorLv = rs.getInt("ItemArmorLv");
/*  113 */           int skilltype = rs.getInt("skilltype");
/*  114 */           int skilltypelv = rs.getInt("skilltypelv");
/*  115 */           int itemType = rs.getInt("ItemType");
/*  116 */           int itemHpr = rs.getInt("ItemHpr");
/*  117 */           int itemMpr = rs.getInt("ItemMpr");
/*  118 */           int itemhppotion = rs.getInt("Itemhppotion");
/*   99 */           int itemweight_reduction = rs.getInt("weight_reduction");//負重
/*  119 */           String racegamno = rs.getString("racegamno");
/*      */           
/*  121 */           L1ItemInstance item = new L1ItemInstance();
/*  122 */           item.setId(objid);
/*  123 */           item.setItem(itemTemplate);
/*  124 */           item.setCount(count);
/*  125 */           item.setEquipped((is_equipped != 0));
/*  126 */           item.setEnchantLevel(enchantlvl);
/*  127 */           item.setIdentified((is_id != 0));
/*  128 */           item.set_durability(durability);
/*  129 */           item.setChargeCount(charge_count);
/*  130 */           item.setRemainingTime(remaining_time);
/*  131 */           item.setLastUsed(last_used);
/*  132 */           item.setBless(bless);
/*  133 */           item.setAttrEnchantKind(attr_enchant_kind);
/*  134 */           item.setAttrEnchantLevel(attr_enchant_level);
/*  135 */           item.setItemAttack(itemAttack);
/*  136 */           item.setItemBowAttack(itemBowAttack);
/*  137 */           item.setItemHit(itemHit);
/*  138 */           item.setItemBowHit(itemBowHit);
/*  139 */           item.setItemReductionDmg(itemReductionDmg);
/*  140 */           item.setItemSp(itemSp);
/*  141 */           item.setItemprobability(itemprobability);
/*  142 */           item.setItemStr(itemStr);
/*  143 */           item.setItemDex(itemDex);
/*  144 */           item.setItemInt(itemInt);
/*  145 */           item.setItemCon(itemCon);
/*  146 */           item.setItemCha(itemCha);
/*  147 */           item.setItemWis(itemWis);
/*  148 */           item.setItemHp(itemHp);
/*  149 */           item.setItemMp(itemMp);
/*  150 */           item.setItemMr(itemMr);
/*  151 */           item.setItemAc(itemAc);
/*  152 */           item.setItemMag_Red(itemMag_Red);
/*  153 */           item.setItemMag_Hit(itemMag_Hit);
/*  154 */           item.setItemDg(itemDg);
/*  155 */           item.setItemistSustain(itemistSustain);
/*  156 */           item.setItemistStun(itemistStun);
/*  157 */           item.setItemistStone(itemistStone);
/*  158 */           item.setItemistSleep(itemistSleep);
/*  159 */           item.setItemistFreeze(itemistFreeze);
/*  160 */           item.setItemistBlind(itemistBlind);
/*  161 */           item.setItemArmorType(itemArmorType);
/*  162 */           item.setItemArmorLv(itemArmorLv);
/*  163 */           item.setskilltype(skilltype);
/*  164 */           item.setskilltypelv(skilltypelv);
/*  165 */           item.setItemType(itemType);
/*  166 */           item.setItemHpr(itemHpr);
/*  167 */           item.setItemMpr(itemMpr);
/*  168 */           item.setItemhppotion(itemhppotion);
/*      */           
/*  170 */           item.setraceGamNo(racegamno);
/*      */           
/*  172 */           item.setGamNo(rs.getInt("gamNo"));
/*  173 */           item.setGamNpcId(rs.getInt("gamNpcId"));
/*      */           
/*  175 */           item.setStarNpcId(rs.getString("starNpcId"));
/*  176 */           item.set_char_objid(char_id);
/*      */           
/*  178 */           item.getLastStatus().updateAll();
/*      */           
/*  170 */           item.setWeightReduction(itemweight_reduction);//負重
/*  180 */           if (item.getItem().getItemId() == 40312) {
/*  181 */             InnKeyTable.checkey(item);
/*      */           }
/*  183 */           if (item.getItem().getItemId() == 82503) {
/*  184 */             InnKeyTable.checkey(item);
/*      */           }
/*  186 */           if (item.getItem().getItemId() == 82504) {
/*  187 */             InnKeyTable.checkey(item);
/*      */           }
/*      */           
/*  190 */           addItem(Integer.valueOf(char_id), item);
/*      */ 
/*      */           
/*  193 */           i++;
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  201 */         deleteItem(Integer.valueOf(char_id));
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  206 */     catch (SQLException e) {
/*  207 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  210 */       SQLUtil.close(rs);
/*  211 */       SQLUtil.close(ps);
/*  212 */       SQLUtil.close(cn);
/*      */     } 
/*  214 */     _log.info("載入人物背包物件清單資料數量: " + _itemList.size() + "/" + i + "(" + timer.get() + 
/*  215 */         "ms)");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void errorItem(int objid) {
/*  224 */     Connection con = null;
/*  225 */     PreparedStatement pstm = null;
/*      */     try {
/*  227 */       con = DatabaseFactory.get().getConnection();
/*  228 */       pstm = con.prepareStatement(
/*  229 */           "DELETE FROM `character_items` WHERE `id`=?");
/*  230 */       pstm.setInt(1, objid);
/*  231 */       pstm.execute();
/*      */     }
/*  233 */     catch (SQLException e) {
/*  234 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  237 */       SQLUtil.close(pstm);
/*  238 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void errorItempower(int item_obj_id) {
/*  246 */     System.out.println("Welco555555555555555555555555555555555555555555555");
/*  247 */     Connection con = null;
/*  248 */     PreparedStatement pstm = null;
/*      */     try {
/*  250 */       con = DatabaseFactory.get().getConnection();
/*  251 */       pstm = con.prepareStatement(
/*  252 */           "DELETE FROM `character_item_power` WHERE `item_obj_id`=?");
/*  253 */       pstm.setInt(1, item_obj_id);
/*  254 */       pstm.execute();
/*      */     }
/*  256 */     catch (SQLException e) {
/*  257 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  260 */       SQLUtil.close(pstm);
/*  261 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void addItem(Integer objid, L1ItemInstance item) {
/*  270 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(objid);
/*  271 */     if (list == null) {
/*  272 */       list = new CopyOnWriteArrayList<>();
/*  273 */       if (!list.contains(item)) {
/*  274 */         list.add(item);
/*      */       
/*      */       }
/*      */     }
/*  278 */     else if (!list.contains(item)) {
/*  279 */       list.add(item);
/*      */     } 
/*      */ 
/*      */     
/*  283 */     if (World.get().findObject(item.getId()) == null) {
/*  284 */       World.get().storeObject((L1Object)item);
/*      */     }
/*  286 */     _itemList.put(objid, list);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void deleteItem(Integer objid) {
/*  296 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.remove(objid);
/*  297 */     if (list != null)
/*      */     {
/*  299 */       for (L1ItemInstance item : list) {
/*  300 */         World.get().removeObject((L1Object)item);
/*      */       }
/*      */     }
/*      */     
/*  304 */     Connection cn = null;
/*  305 */     PreparedStatement ps = null;
/*      */     try {
/*  307 */       cn = DatabaseFactory.get().getConnection();
/*  308 */       ps = cn.prepareStatement(
/*  309 */           "DELETE FROM `character_items` WHERE `char_id`=?");
/*  310 */       ps.setInt(1, objid.intValue());
/*  311 */       ps.execute();
/*      */     }
/*  313 */     catch (SQLException e) {
/*  314 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  317 */       SQLUtil.close(ps);
/*  318 */       SQLUtil.close(cn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CopyOnWriteArrayList<L1ItemInstance> loadItems(Integer objid) {
/*  330 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(objid);
/*  331 */     if (list != null) {
/*  332 */       return list;
/*      */     }
/*  334 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delUserItems(Integer objid) {
/*  343 */     deleteItem(objid);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUserItems(Integer pcObjid, int objid, long count) {
/*  355 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(pcObjid);
/*  356 */     if (list != null) {
/*  357 */       for (L1ItemInstance item : list) {
/*  358 */         if (item.getId() == objid && item.getCount() >= count) {
/*  359 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*  363 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUserItem(int objid) {
/*  376 */     for (CopyOnWriteArrayList<L1ItemInstance> list : _itemList.values()) {
/*  377 */       for (L1ItemInstance item : list) {
/*  378 */         if (item.getId() == objid) {
/*  379 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*  383 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, L1ItemInstance> getUserItems(int itemid) {
/*  395 */     Map<Integer, L1ItemInstance> outList = new ConcurrentHashMap<>();
/*      */     try {
/*  397 */       for (Integer key : _itemList.keySet()) {
/*  398 */         CopyOnWriteArrayList<L1ItemInstance> value = _itemList.get(key);
/*  399 */         for (L1ItemInstance item : value) {
/*  400 */           if (item.getItemId() == itemid) {
/*  401 */             outList.put(key, item);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  406 */     } catch (Exception e) {
/*  407 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */     
/*  410 */     return outList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void del_item(int itemid) {
/*      */     try {
/*  420 */       for (Integer key : _itemList.keySet()) {
/*      */         
/*  422 */         CopyOnWriteArrayList<L1ItemInstance> value = _itemList.get(key);
/*  423 */         for (L1ItemInstance item : value) {
/*  424 */           if (item.getItemId() == itemid) {
/*  425 */             deleteItem(key.intValue(), item);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*  430 */     } catch (Exception e) {
/*  431 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void storeItem(int objId, L1ItemInstance item) throws Exception {
/*  447 */     addItem(Integer.valueOf(objId), item);
/*  448 */     item.getLastStatus().updateAll();
/*      */     
/*  450 */     Connection con = null;
/*  451 */     PreparedStatement pstm = null;
/*      */ 
/*      */     
/*      */     try {
/*  455 */       con = DatabaseFactory.get().getConnection();
/*  456 */       pstm = con.prepareStatement(
/*  457 */           "INSERT INTO `character_items` SET `id`=?,`item_id`=?,`char_id`=?,`item_name`=?,`count`=?,`is_equipped`=?,`enchantlvl`=?,`is_id`=?,`durability`=?,`charge_count`=?,`remaining_time`=?,`last_used`=?,`bless`=?,`attr_enchant_kind`=?,`attr_enchant_level`=?,`ItemAttack`=?,`ItemBowAttack`=?,`ItemHit`=?,`ItemBowHit`=?,`ItemReductionDmg`=?,`ItemSp`=?,`Itemprobability`=?,`ItemStr`=?,`ItemDex`=?,`ItemInt`=?,`ItemCon`=?,`ItemCha`=?,`ItemWis`=?,`ItemHp`=?,`ItemMp`=?,`itemMr`=?,`itemAc`=?,`itemMag_Red`=?,`itemMag_Hit`=?,`itemDg`=?,`itemistSustain`=?,`itemistStun`=?,`itemistStone`=?,`itemistSleep`=?,`itemistFreeze`=?,`itemistBlind`=?,`itemArmorType`=?,`itemArmorLv`=?,`skilltype`=?,`skilltypelv`=?,`itemType`=?,`ItemHpr`=?,`ItemMpr`=?,`Itemhppotion`=?,`gamNo`=?,`gamNpcId` = ?,`starNpcId`=?,`racegamno`=?,`weight_reduction`=?");
/*      */       
/*  459 */       int i = 0;
/*  460 */       pstm.setInt(++i, item.getId());
/*  461 */       pstm.setInt(++i, item.getItem().getItemId());
/*  462 */       pstm.setInt(++i, objId);
/*  463 */       pstm.setString(++i, item.getItem().getName());
/*  464 */       pstm.setLong(++i, item.getCount());
/*  465 */       pstm.setInt(++i, item.isEquipped() ? 1 : 0);
/*  466 */       pstm.setInt(++i, item.getEnchantLevel());
/*  467 */       pstm.setInt(++i, item.isIdentified() ? 1 : 0);
/*  468 */       pstm.setInt(++i, item.get_durability());
/*  469 */       pstm.setInt(++i, item.getChargeCount());
/*  470 */       pstm.setInt(++i, item.getRemainingTime());
/*  471 */       pstm.setTimestamp(++i, item.getLastUsed());
/*  472 */       pstm.setInt(++i, item.getBless());
/*  473 */       pstm.setInt(++i, item.getAttrEnchantKind());
/*  474 */       pstm.setInt(++i, item.getAttrEnchantLevel());
/*  475 */       pstm.setInt(++i, item.getItemAttack());
/*  476 */       pstm.setInt(++i, item.getItemBowAttack());
/*  477 */       pstm.setInt(++i, item.getItemHit());
/*  478 */       pstm.setInt(++i, item.getItemBowHit());
/*  479 */       pstm.setInt(++i, item.getItemReductionDmg());
/*  480 */       pstm.setInt(++i, item.getItemSp());
/*  481 */       pstm.setInt(++i, item.getItemprobability());
/*  482 */       pstm.setInt(++i, item.getItemStr());
/*  483 */       pstm.setInt(++i, item.getItemDex());
/*  484 */       pstm.setInt(++i, item.getItemInt());
/*  485 */       pstm.setInt(++i, item.getItemCon());
/*  486 */       pstm.setInt(++i, item.getItemCha());
/*  487 */       pstm.setInt(++i, item.getItemWis());
/*  488 */       pstm.setInt(++i, item.getItemHp());
/*  489 */       pstm.setInt(++i, item.getItemMp());
/*  490 */       pstm.setInt(++i, item.getItemMr());
/*  491 */       pstm.setInt(++i, item.getItemAc());
/*  492 */       pstm.setInt(++i, item.getItemMag_Red());
/*  493 */       pstm.setInt(++i, item.getItemMag_Hit());
/*  494 */       pstm.setInt(++i, item.getItemDg());
/*  495 */       pstm.setInt(++i, item.getItemistSustain());
/*  496 */       pstm.setInt(++i, item.getItemistStun());
/*  497 */       pstm.setInt(++i, item.getItemistStone());
/*  498 */       pstm.setInt(++i, item.getItemistSleep());
/*  499 */       pstm.setInt(++i, item.getItemistFreeze());
/*  500 */       pstm.setInt(++i, item.getItemistBlind());
/*  501 */       pstm.setInt(++i, item.getItemArmorType());
/*  502 */       pstm.setInt(++i, item.getItemArmorLv());
/*  503 */       pstm.setInt(++i, item.getskilltype());
/*  504 */       pstm.setInt(++i, item.getskilltypelv());
/*  505 */       pstm.setInt(++i, item.getItemType());
/*  506 */       pstm.setInt(++i, item.getItemHpr());
/*  507 */       pstm.setInt(++i, item.getItemMpr());
/*  508 */       pstm.setInt(++i, item.getItemhppotion());
/*      */ 
/*      */ 
/*      */       
/*  512 */       pstm.setInt(++i, item.getGamNo());
/*  513 */       pstm.setInt(++i, item.getGamNpcId());
/*      */       
/*  515 */       pstm.setString(++i, item.getStarNpcId());
/*      */       
/*  517 */       pstm.setString(++i, item.getraceGamNo());
/*  517 */       pstm.setInt(++i, item.getWeightReduction());//負重
/*  518 */       pstm.execute();
/*      */     }
/*  520 */     catch (SQLException e) {
/*  521 */       _log.error("背包物品增加時發生異常 人物OBJID:" + objId, e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  534 */       SQLUtil.close(pstm);
/*  535 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteItem(int objid, L1ItemInstance item) throws Exception {
/*  565 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(Integer.valueOf(objid));
/*  566 */     if (list != null) {
/*  567 */       list.remove(item);
/*      */       
/*  569 */       World.get().removeObject((L1Object)item);
/*      */ 
/*      */ 
/*      */       
/*  573 */       Connection cn = null;
/*  574 */       PreparedStatement ps = null;
/*      */       try {
/*  576 */         cn = DatabaseFactory.get().getConnection();
/*  577 */         ps = cn.prepareStatement(
/*  578 */             "DELETE FROM `character_items` WHERE `id`=?");
/*  579 */         ps.setInt(1, item.getId());
/*  580 */         ps.execute();
/*      */       }
/*  582 */       catch (SQLException e) {
/*  583 */         _log.error(e.getLocalizedMessage(), e);
/*      */       } finally {
/*      */         
/*  586 */         SQLUtil.close(ps);
/*  587 */         SQLUtil.close(cn);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateName(L1ItemInstance item) throws Exception {
/*  595 */     Connection con = null;
/*  596 */     PreparedStatement pstm = null;
/*  597 */     L1Object object = World.get().findObject(item.get_char_objid());
/*  598 */     L1PcInstance tgpc = (L1PcInstance)object;
/*      */     try {
/*  600 */       con = DatabaseFactory.get().getConnection();
/*  601 */       pstm = con.prepareStatement("UPDATE `character_items` SET `item_id`=?,`item_name`=?,`bless`=? WHERE `id`=?");
/*  602 */       pstm.setInt(1, item.getItemId());
/*  603 */       pstm.setString(2, String.valueOf(tgpc.getName()) + "=>" + item.getItem().getName());
/*      */       
/*  605 */       pstm.setInt(3, item.getItem().getBless());
/*  606 */       pstm.setInt(4, item.getId());
/*  607 */       pstm.execute();
/*      */     }
/*  609 */     catch (SQLException e) {
/*  610 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  613 */       SQLUtil.close(pstm);
/*  614 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateItemId_Name(L1ItemInstance item) throws Exception {
/*  620 */     Connection con = null;
/*  621 */     PreparedStatement pstm = null;
/*      */     try {
/*  623 */       con = DatabaseFactory.get().getConnection();
/*  624 */       pstm = con.prepareStatement("UPDATE `character_items` SET `item_id`=?,`item_name`=?,`bless`=? WHERE `id`=?");
/*  625 */       pstm.setInt(1, item.getItemId());
/*  626 */       pstm.setString(2, item.getItem().getName());
/*  627 */       pstm.setInt(3, item.getItem().getBless());
/*  628 */       pstm.setInt(4, item.getId());
/*  629 */       pstm.execute();
/*      */     }
/*  631 */     catch (SQLException e) {
/*  632 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  635 */       SQLUtil.close(pstm);
/*  636 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemId(L1ItemInstance item) throws Exception {
/*  643 */     executeUpdate(item.getId(), 
/*  644 */         "UPDATE `character_items` SET `item_id`=? WHERE `id`=?", 
/*  645 */         item.getItemId());
/*      */     
/*  647 */     item.getLastStatus().updateItemId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemCount(L1ItemInstance item) throws Exception {
/*  653 */     executeUpdate(item.getId(), 
/*  654 */         "UPDATE `character_items` SET `count`=? WHERE `id`=?", 
/*  655 */         item.getCount());
/*  656 */     item.getLastStatus().updateCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateGamNo(L1ItemInstance item) throws Exception {
/*  661 */     executeUpdate(item.getId(), 
/*  662 */         "UPDATE character_items SET gamNo = ? WHERE id = ?", 
/*  663 */         item.getGamNo());
/*  664 */     item.getLastStatus().updateGamNo();
/*      */   }
/*      */   
/*      */   public void updateGamNpcId(L1ItemInstance item) throws Exception {
/*  668 */     executeUpdate(item.getId(), 
/*  669 */         "UPDATE character_items SET gamNpcId = ? WHERE id = ?", 
/*  670 */         item.getGamNpcId());
/*  671 */     item.getLastStatus().updateGamNpcId();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateStarNpcId(L1ItemInstance item) throws Exception {
/*  676 */     executeUpdate(item.getId(), 
/*  677 */         "UPDATE character_items SET StarNpcId = ? WHERE id = ?", 
/*  678 */         item.getStarNpcId());
/*  679 */     item.getLastStatus().updateStarNpcId();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void executeUpdate(int objId, String sql, String updateNum) throws SQLException {
/*  685 */     Connection con = null;
/*  686 */     PreparedStatement pstm = null;
/*      */     try {
/*  688 */       con = DatabaseFactory.get().getConnection();
/*  689 */       pstm = con.prepareStatement(sql.toString());
/*  690 */       pstm.setString(1, updateNum);
/*  691 */       pstm.setInt(2, objId);
/*  692 */       pstm.execute();
/*  693 */     } catch (SQLException e) {
/*  694 */       throw e;
/*      */     } finally {
/*  696 */       SQLUtil.close(pstm);
/*  697 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemDurability(L1ItemInstance item) throws Exception {
/*  704 */     executeUpdate(item.getId(), 
/*  705 */         "UPDATE `character_items` SET `durability`=? WHERE `id`=?", 
/*  706 */         item.get_durability());
/*      */     
/*  708 */     item.getLastStatus().updateDuraility();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemChargeCount(L1ItemInstance item) throws Exception {
/*  714 */     executeUpdate(item.getId(), 
/*  715 */         "UPDATE `character_items` SET `charge_count`=? WHERE `id`=?", 
/*  716 */         item.getChargeCount());
/*      */     
/*  718 */     item.getLastStatus().updateChargeCount();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemRemainingTime(L1ItemInstance item) throws Exception {
/*  724 */     executeUpdate(item.getId(), 
/*  725 */         "UPDATE `character_items` SET `remaining_time`=? WHERE `id`=?", 
/*  726 */         item.getRemainingTime());
/*      */     
/*  728 */     item.getLastStatus().updateRemainingTime();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemEnchantLevel(L1ItemInstance item) throws Exception {
/*  734 */     executeUpdate(item.getId(), 
/*  735 */         "UPDATE `character_items` SET `enchantlvl`=? WHERE `id`=?", 
/*  736 */         item.getEnchantLevel());
/*      */     
/*  738 */     item.getLastStatus().updateEnchantLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemEquipped(L1ItemInstance item) throws Exception {
/*  744 */     executeUpdate(item.getId(), 
/*  745 */         "UPDATE `character_items` SET `is_equipped`=? WHERE `id`=?", (
/*  746 */         item.isEquipped() ? 1L : 0L));
/*      */     
/*  748 */     item.getLastStatus().updateEquipped();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemIdentified(L1ItemInstance item) throws Exception {
/*  754 */     executeUpdate(item.getId(), 
/*  755 */         "UPDATE `character_items` SET `is_id`=? WHERE `id`=?", (
/*  756 */         item.isIdentified() ? 1L : 0L));
/*      */     
/*  758 */     item.getLastStatus().updateIdentified();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemBless(L1ItemInstance item) throws Exception {
/*  764 */     executeUpdate(item.getId(), 
/*  765 */         "UPDATE `character_items` SET `bless`=? WHERE `id`=?", 
/*  766 */         item.getBless());
/*      */     
/*  768 */     item.getLastStatus().updateBless();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemAttrEnchantKind(L1ItemInstance item) throws Exception {
/*  774 */     executeUpdate(
/*  775 */         item.getId(), 
/*  776 */         "UPDATE `character_items` SET `attr_enchant_kind`=? WHERE `id`=?", 
/*  777 */         item.getAttrEnchantKind());
/*      */     
/*  779 */     item.getLastStatus().updateAttrEnchantKind();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemAttrEnchantLevel(L1ItemInstance item) throws Exception {
/*  785 */     executeUpdate(
/*  786 */         item.getId(), 
/*  787 */         "UPDATE `character_items` SET `attr_enchant_level`=? WHERE `id`=?", 
/*  788 */         item.getAttrEnchantLevel());
/*      */     
/*  790 */     item.getLastStatus().updateAttrEnchantLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateItemDelayEffect(L1ItemInstance item) throws Exception {
/*  796 */     executeUpdate(item.getId(), 
/*  797 */         "UPDATE `character_items` SET `last_used`=? WHERE `id`=?", 
/*  798 */         item.getLastUsed());
/*      */     
/*  800 */     item.getLastStatus().updateLastUsed();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount(int objId) throws Exception {
/*  806 */     int count = 0;
/*  807 */     Connection con = null;
/*  808 */     PreparedStatement pstm = null;
/*  809 */     ResultSet rs = null;
/*      */     try {
/*  811 */       con = DatabaseFactory.get().getConnection();
/*  812 */       pstm = con.prepareStatement(
/*  813 */           "SELECT * FROM `character_items` WHERE `char_id`=?");
/*  814 */       pstm.setInt(1, objId);
/*  815 */       rs = pstm.executeQuery();
/*  816 */       while (rs.next()) {
/*  817 */         count++;
/*      */       }
/*  819 */     } catch (SQLException e) {
/*      */       
/*  821 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/*  824 */       SQLUtil.close(rs);
/*  825 */       SQLUtil.close(pstm);
/*  826 */       SQLUtil.close(con);
/*      */     } 
/*  828 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void getAdenaCount(int objid, long count) throws Exception {
/*  834 */     CopyOnWriteArrayList<L1ItemInstance> list = _itemList.get(Integer.valueOf(objid));
/*  835 */     if (list != null) {
/*  836 */       boolean isAdena = false;
/*  837 */       for (L1ItemInstance item : list) {
/*  838 */         if (item.getItemId() == 40308) {
/*      */           
/*  840 */           item.setCount(item.getCount() + count);
/*  841 */           updateItemCount(item);
/*  842 */           isAdena = true;
/*      */         } 
/*      */       } 
/*      */       
/*  846 */       if (!isAdena) {
/*  847 */         L1ItemInstance item = ItemTable.get().createItem(40308);
/*  848 */         item.setCount(count);
/*  849 */         storeItem(objid, item);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateItemAttack(L1ItemInstance item) throws Exception {
/*  856 */     executeUpdate(
/*  857 */         item.getId(), 
/*  858 */         "UPDATE `character_items` SET `ItemAttack`=? WHERE `id`=?", 
/*  859 */         item.getItemAttack());
/*      */     
/*  861 */     item.getLastStatus().updateItemAttack();
/*      */   }
/*      */   
/*      */   public void updateItemBowAttack(L1ItemInstance item) throws Exception {
/*  865 */     executeUpdate(
/*  866 */         item.getId(), 
/*  867 */         "UPDATE `character_items` SET `ItemBowAttack`=? WHERE `id`=?", 
/*  868 */         item.getItemBowAttack());
/*      */     
/*  870 */     item.getLastStatus().updateItemBowAttack();
/*      */   }
/*      */   
/*      */   public void updateItemHit(L1ItemInstance item) throws Exception {
/*  874 */     executeUpdate(
/*  875 */         item.getId(), 
/*  876 */         "UPDATE `character_items` SET `ItemHit`=? WHERE `id`=?", 
/*  877 */         item.getItemHit());
/*      */     
/*  879 */     item.getLastStatus().updateItemHit();
/*      */   }
/*      */   
/*      */   public void updateItemBowHit(L1ItemInstance item) throws Exception {
/*  883 */     executeUpdate(
/*  884 */         item.getId(), 
/*  885 */         "UPDATE `character_items` SET `ItemBowHit`=? WHERE `id`=?", 
/*  886 */         item.getItemBowHit());
/*      */     
/*  888 */     item.getLastStatus().updateItemBowHit();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateItemReductionDmg(L1ItemInstance item) throws Exception {
/*  893 */     executeUpdate(
/*  894 */         item.getId(), 
/*  895 */         "UPDATE `character_items` SET `ItemReductionDmg`=? WHERE `id`=?", 
/*  896 */         item.getItemReductionDmg());
/*      */     
/*  898 */     item.getLastStatus().updateItemReductionDmg();
/*      */   }
/*      */   
/*      */   public void updateItemSp(L1ItemInstance item) throws Exception {
/*  902 */     executeUpdate(
/*  903 */         item.getId(), 
/*  904 */         "UPDATE `character_items` SET `ItemSp`=? WHERE `id`=?", 
/*  905 */         item.getItemSp());
/*      */     
/*  907 */     item.getLastStatus().updateItemSp();
/*      */   }
/*      */   
/*      */   public void updateItemprobability(L1ItemInstance item) throws Exception {
/*  911 */     executeUpdate(
/*  912 */         item.getId(), 
/*  913 */         "UPDATE `character_items` SET `Itemprobability`=? WHERE `id`=?", 
/*  914 */         item.getItemprobability());
/*      */     
/*  916 */     item.getLastStatus().updateItemprobability();
/*      */   }
/*      */   
/*      */   public void updateItemStr(L1ItemInstance item) throws Exception {
/*  920 */     executeUpdate(
/*  921 */         item.getId(), 
/*  922 */         "UPDATE `character_items` SET `ItemStr`=? WHERE `id`=?", 
/*  923 */         item.getItemStr());
/*      */     
/*  925 */     item.getLastStatus().updateItemStr();
/*      */   }
/*      */   
/*      */   public void updateItemDex(L1ItemInstance item) throws Exception {
/*  929 */     executeUpdate(
/*  930 */         item.getId(), 
/*  931 */         "UPDATE `character_items` SET `ItemDex`=? WHERE `id`=?", 
/*  932 */         item.getItemDex());
/*      */     
/*  934 */     item.getLastStatus().updateItemDex();
/*      */   }
/*      */   
/*      */   public void updateItemInt(L1ItemInstance item) throws Exception {
/*  938 */     executeUpdate(
/*  939 */         item.getId(), 
/*  940 */         "UPDATE `character_items` SET `ItemInt`=? WHERE `id`=?", 
/*  941 */         item.getItemInt());
/*      */     
/*  943 */     item.getLastStatus().updateItemInt();
/*      */   }
/*      */   
/*      */   public void updateItemHp(L1ItemInstance item) throws Exception {
/*  947 */     executeUpdate(
/*  948 */         item.getId(), 
/*  949 */         "UPDATE `character_items` SET `ItemHp`=? WHERE `id`=?", 
/*  950 */         item.getItemHp());
/*      */     
/*  952 */     item.getLastStatus().updateItemHp();
/*      */   }
/*      */   
/*      */   public void updateItemMp(L1ItemInstance item) throws Exception {
/*  956 */     executeUpdate(
/*  957 */         item.getId(), 
/*  958 */         "UPDATE `character_items` SET `ItemMp`=? WHERE `id`=?", 
/*  959 */         item.getItemMp());
/*      */     
/*  961 */     item.getLastStatus().updateItemMp();
/*      */   }
/*      */   
/*      */   public void updateskilltype(L1ItemInstance item) throws Exception {
/*  965 */     executeUpdate(
/*  966 */         item.getId(), 
/*  967 */         "UPDATE `character_items` SET `skilltype`=? WHERE `id`=?", 
/*  968 */         item.getskilltype());
/*      */     
/*  970 */     item.getLastStatus().updateskilltype();
/*      */   }
/*      */   
/*      */   public void updateskilltypelv(L1ItemInstance item) throws Exception {
/*  974 */     executeUpdate(
/*  975 */         item.getId(), 
/*  976 */         "UPDATE `character_items` SET `skilltypelv`=? WHERE `id`=?", 
/*  977 */         item.getskilltypelv());
/*      */     
/*  979 */     item.getLastStatus().updateskilltypelv();
/*      */   }
/*      */   
/*      */   public void updateItemCon(L1ItemInstance item) throws Exception {
/*  983 */     executeUpdate(
/*  984 */         item.getId(), 
/*  985 */         "UPDATE `character_items` SET `ItemCon`=? WHERE `id`=?", 
/*  986 */         item.getItemCon());
/*      */     
/*  988 */     item.getLastStatus().updateItemCon();
/*      */   }
/*      */   
/*      */   public void updateItemWis(L1ItemInstance item) throws Exception {
/*  992 */     executeUpdate(
/*  993 */         item.getId(), 
/*  994 */         "UPDATE `character_items` SET `ItemWis`=? WHERE `id`=?", 
/*  995 */         item.getItemWis());
/*      */     
/*  997 */     item.getLastStatus().updateItemWis();
/*      */   }
/*      */   
/*      */   public void updateItemCha(L1ItemInstance item) throws Exception {
/* 1001 */     executeUpdate(
/* 1002 */         item.getId(), 
/* 1003 */         "UPDATE `character_items` SET `ItemCha`=? WHERE `id`=?", 
/* 1004 */         item.getItemCha());
/*      */     
/* 1006 */     item.getLastStatus().updateItemCha();
/*      */   }
/*      */   
/*      */   public void updateItemMr(L1ItemInstance item) throws Exception {
/* 1010 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMr`=? WHERE `id`=?", 
/* 1011 */         item.getItemMr());
/*      */     
/* 1013 */     item.getLastStatus().updateItemMr();
/*      */   }
/*      */   
/*      */   public void updateItemAc(L1ItemInstance item) throws Exception {
/* 1017 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemAc`=? WHERE `id`=?", 
/* 1018 */         item.getItemAc());
/*      */     
/* 1020 */     item.getLastStatus().updateItemAc();
/*      */   }
/*      */   
/*      */   public void updateItemMag_Red(L1ItemInstance item) throws Exception {
/* 1024 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMag_Red`=? WHERE `id`=?", 
/* 1025 */         item.getItemMag_Red());
/*      */     
/* 1027 */     item.getLastStatus().updateItemMag_Red();
/*      */   }
/*      */   
/*      */   public void updateItemMag_Hit(L1ItemInstance item) throws Exception {
/* 1031 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemMag_Hit`=? WHERE `id`=?", 
/* 1032 */         item.getItemMag_Hit());
/*      */     
/* 1034 */     item.getLastStatus().updateItemMag_Hit();
/*      */   }
/*      */   
/*      */   public void updateItemDg(L1ItemInstance item) throws Exception {
/* 1038 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemDg`=? WHERE `id`=?", 
/* 1039 */         item.getItemDg());
/*      */     
/* 1041 */     item.getLastStatus().updateItemDg();
/*      */   }
/*      */   
/*      */   public void updateItemistSustain(L1ItemInstance item) throws Exception {
/* 1045 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistSustain`=? WHERE `id`=?", 
/* 1046 */         item.getItemistSustain());
/*      */     
/* 1048 */     item.getLastStatus().updateItemistSustain();
/*      */   }
/*      */   
/*      */   public void updateItemistStun(L1ItemInstance item) throws Exception {
/* 1052 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistStun`=? WHERE `id`=?", 
/* 1053 */         item.getItemistStun());
/*      */     
/* 1055 */     item.getLastStatus().updateItemistStun();
/*      */   }
/*      */   
/*      */   public void updateItemistStone(L1ItemInstance item) throws Exception {
/* 1059 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistStone`=? WHERE `id`=?", 
/* 1060 */         item.getItemistStone());
/*      */     
/* 1062 */     item.getLastStatus().updateItemistStone();
/*      */   }
/*      */   
/*      */   public void updateItemistSleep(L1ItemInstance item) throws Exception {
/* 1066 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistSleep`=? WHERE `id`=?", 
/* 1067 */         item.getItemistSleep());
/*      */     
/* 1069 */     item.getLastStatus().updateItemistSleep();
/*      */   }
/*      */   
/*      */   public void updateItemistFreeze(L1ItemInstance item) throws Exception {
/* 1073 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistFreeze`=? WHERE `id`=?", 
/* 1074 */         item.getItemistFreeze());
/*      */     
/* 1076 */     item.getLastStatus().updateItemistFreeze();
/*      */   }
/*      */   
/*      */   public void updateItemistBlind(L1ItemInstance item) throws Exception {
/* 1080 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemistBlind`=? WHERE `id`=?", 
/* 1081 */         item.getItemistBlind());
/*      */     
/* 1083 */     item.getLastStatus().updateItemistBlind();
/*      */   }
/*      */   
/*      */   public void updateItemArmorLv(L1ItemInstance item) throws Exception {
/* 1087 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemArmorLv`=? WHERE `id`=?", 
/* 1088 */         item.getItemArmorLv());
/*      */     
/* 1090 */     item.getLastStatus().updateItemArmorLv();
/*      */   }
/*      */   
/*      */   public void updateItemArmorType(L1ItemInstance item) throws Exception {
/* 1094 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemArmorType`=? WHERE `id`=?", 
/* 1095 */         item.getItemArmorType());
/*      */     
/* 1097 */     item.getLastStatus().updateItemArmorType();
/*      */   }
/*      */   
/*      */   public void updateItemType(L1ItemInstance item) throws Exception {
/* 1101 */     executeUpdate(item.getId(), "UPDATE `character_items` SET `ItemType`=? WHERE `id`=?", 
/* 1102 */         item.getItemArmorType());
/*      */     
/* 1104 */     item.getLastStatus().updateItemArmorType();
/*      */   }
/*      */   
/*      */   public void updateItemHpr(L1ItemInstance item) throws Exception {
/* 1108 */     executeUpdate(
/* 1109 */         item.getId(), 
/* 1110 */         "UPDATE `character_items` SET `ItemHpr`=? WHERE `id`=?", 
/* 1111 */         item.getItemHpr());
/*      */     
/* 1113 */     item.getLastStatus().updateItemHpr();
/*      */   }
/*      */   
/*      */   public void updateItemMpr(L1ItemInstance item) throws Exception {
/* 1117 */     executeUpdate(
/* 1118 */         item.getId(), 
/* 1119 */         "UPDATE `character_items` SET `ItemMpr`=? WHERE `id`=?", 
/* 1120 */         item.getItemMpr());
/*      */     
/* 1122 */     item.getLastStatus().updateItemMpr();
/*      */   }
/*      */   
/*      */   public void updateBless(L1ItemInstance item) throws Exception {
/* 1126 */     executeUpdate(
/* 1127 */         item.getId(), 
/* 1128 */         "UPDATE `character_items` SET `Bless`=? WHERE `id`=?", 
/* 1129 */         item.getBless());
/*      */     
/* 1131 */     item.getLastStatus().updateBless();
/*      */   }
/*      */   
/*      */   public void updateItemhppotion(L1ItemInstance item) throws Exception {
/* 1135 */     executeUpdate(
/* 1136 */         item.getId(), 
/* 1137 */         "UPDATE `character_items` SET `Itemhppotion`=? WHERE `id`=?", 
/* 1138 */         item.getItemhppotion());
/*      */     
/* 1140 */     item.getLastStatus().updateItemhppotion();
/*      */   }
/*      */   public void updateWeightReduction(L1ItemInstance item) throws Exception {
/*  785 */     executeUpdate(
/*  786 */         item.getId(), 
/*  787 */         "UPDATE `character_items` SET `weight_reduction`=? WHERE `id`=?", 
/*  788 */         item.getWeightReduction());
/*      */     
/*  790 */     item.getLastStatus().updateWeightReduction();
/*      */   }
/*      */   private void executeUpdate(int objId, String sql, long updateNum) {
/* 1143 */     Connection con = null;
/* 1144 */     PreparedStatement pstm = null;
/*      */     try {
/* 1146 */       con = DatabaseFactory.get().getConnection();
/* 1147 */       pstm = con.prepareStatement(sql.toString());
/* 1148 */       pstm.setLong(1, updateNum);
/* 1149 */       pstm.setInt(2, objId);
/* 1150 */       pstm.execute();
/*      */     }
/* 1152 */     catch (SQLException e) {
/* 1153 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/* 1156 */       SQLUtil.close(pstm);
/* 1157 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void executeUpdate(int objId, String sql, Timestamp ts) throws SQLException {
/* 1162 */     Connection con = null;
/* 1163 */     PreparedStatement pstm = null;
/*      */     try {
/* 1165 */       con = DatabaseFactory.get().getConnection();
/* 1166 */       pstm = con.prepareStatement(sql.toString());
/* 1167 */       pstm.setTimestamp(1, ts);
/* 1168 */       pstm.setInt(2, objId);
/* 1169 */       pstm.execute();
/*      */     }
/* 1171 */     catch (SQLException e) {
/* 1172 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/* 1175 */       SQLUtil.close(pstm);
/* 1176 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int checkItemId(int itemId) {
/* 1182 */     int counter = 0;
/* 1183 */     for (Iterator<CopyOnWriteArrayList<L1ItemInstance>> iterator = _itemList.values().iterator(); iterator.hasNext(); ) {
/*      */       
/* 1185 */       CopyOnWriteArrayList<?> list = iterator.next();
/* 1186 */       for (Iterator<?> iterator1 = list.iterator(); iterator1.hasNext(); ) {
/*      */         
/* 1188 */         L1ItemInstance item = (L1ItemInstance)iterator1.next();
/* 1189 */         if (item.getItemId() == itemId)
/* 1190 */           counter++; 
/*      */       } 
/*      */     } 
/* 1193 */     return counter;
/*      */   }
/*      */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\CharItemsTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */