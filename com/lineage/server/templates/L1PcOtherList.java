/*      */ package com.lineage.server.templates;
/*      */ 
/*      */ import com.add.system.L1FireCrystal;
/*      */ import com.add.system.L1FireSmithCrystalTable;
/*      */ import com.lineage.DatabaseFactory;
/*      */ import com.lineage.DatabaseFactoryLogin;
/*      */ import com.lineage.data.event.GamblingSet;
/*      */ import com.lineage.data.event.gambling.GamblingNpc;
/*      */ import com.lineage.data.npc.shop.Npc_ShopX;
/*      */ import com.lineage.server.datatables.ItemPowerUpdateTable;
/*      */ import com.lineage.server.datatables.ItemTable;
/*      */ import com.lineage.server.datatables.RecordTable;
/*      */ import com.lineage.server.datatables.ShopCnTable;
/*      */ import com.lineage.server.datatables.ShopTable;
/*      */ import com.lineage.server.datatables.lock.DwarfShopReading;
/*      */ import com.lineage.server.datatables.lock.GamblingReading;
/*      */ import com.lineage.server.datatables.lock.IpReading;
/*      */ import com.lineage.server.datatables.lock.ServerCnInfoReading;
/*      */ import com.lineage.server.model.Instance.L1IllusoryInstance;
/*      */ import com.lineage.server.model.Instance.L1ItemInstance;
/*      */ import com.lineage.server.model.Instance.L1PcInstance;
/*      */ import com.lineage.server.serverpackets.S_ServerMessage;
/*      */ import com.lineage.server.serverpackets.S_SystemMessage;
/*      */ import com.lineage.server.serverpackets.ServerBasePacket;
/*      */ import com.lineage.server.timecontroller.event.GamblingTime;
/*      */ import com.lineage.server.utils.ListMapUtil;
/*      */ import com.lineage.server.utils.RangeInt;
/*      */ import com.lineage.server.utils.SQLUtil;
/*      */ import com.lineage.server.world.World;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Timestamp;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class L1PcOtherList
/*      */ {
/*   51 */   private static final Log _log = LogFactory.getLog(L1PcOtherList.class);
/*      */   
/*      */   private L1PcInstance _pc;
/*      */   
/*      */   public Map<Integer, L1ItemInstance> DELIST;
/*      */   
/*      */   private Map<Integer, L1ShopItem> _cnList;
/*      */   
/*      */   private Map<Integer, L1ItemInstance> _cnSList;
/*      */   
/*      */   private Map<Integer, GamblingNpc> _gamList;
/*      */   
/*      */   private Map<Integer, L1Gambling> _gamSellList;
/*      */   
/*      */   private Map<Integer, L1IllusoryInstance> _illusoryList;
/*      */   
/*      */   private Map<Integer, L1TeleportLoc> _teleport;
/*      */   
/*      */   private Map<Integer, Integer> _uplevelList;
/*      */   
/*      */   private Map<Integer, String[]> _shiftingList;
/*      */   
/*      */   private Map<Integer, L1ItemInstance> _sitemList;
/*      */   
/*      */   private Map<Integer, Integer> _sitemList2;
/*      */   
/*      */   public Map<Integer, L1Quest> QUESTMAP;
/*      */   
/*      */   public Map<Integer, L1ShopS> SHOPXMAP;
/*      */   
/*      */   public ArrayList<Integer> ATKNPC;
/*      */   
/*      */   private int[] _is;
/*      */   
/*      */   public Map<Integer, int[]> EZPAYLIST;
/*      */   
/*      */   public Map<Integer, int[]> SHOPLIST;
/*      */   
/*      */   public L1PcOtherList(L1PcInstance pc) {
/*   90 */     this._pc = pc;
/*   91 */     this.DELIST = new HashMap<>();
/*      */     
/*   93 */     this._cnList = new HashMap<>();
/*   94 */     this._cnSList = new HashMap<>();
/*   95 */     this._gamList = new HashMap<>();
/*   96 */     this._gamSellList = new HashMap<>();
/*   97 */     this._illusoryList = new HashMap<>();
/*      */     
/*   99 */     this._teleport = new HashMap<>();
/*  100 */     this._uplevelList = new HashMap<>();
/*  101 */     this._shiftingList = (Map)new HashMap<>();
/*  102 */     this._sitemList = new HashMap<>();
/*  103 */     this._sitemList2 = new HashMap<>();
/*      */     
/*  105 */     this.QUESTMAP = new HashMap<>();
/*  106 */     this.SHOPXMAP = new HashMap<>();
/*  107 */     this.ATKNPC = new ArrayList<>();
/*  108 */     this.EZPAYLIST = (Map)new HashMap<>();
/*  109 */     this.SHOPLIST = (Map)new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearAll() {
/*      */     try {
/*  117 */       ListMapUtil.clear(this.DELIST);
/*  118 */       ListMapUtil.clear(this._cnList);
/*  119 */       ListMapUtil.clear(this._cnSList);
/*  120 */       ListMapUtil.clear(this._gamList);
/*  121 */       ListMapUtil.clear(this._gamSellList);
/*  122 */       ListMapUtil.clear(this._illusoryList);
/*  123 */       ListMapUtil.clear(this._teleport);
/*  124 */       ListMapUtil.clear(this._uplevelList);
/*  125 */       ListMapUtil.clear(this._shiftingList);
/*  126 */       ListMapUtil.clear(this._sitemList);
/*  127 */       ListMapUtil.clear(this._sitemList2);
/*  128 */       ListMapUtil.clear(this.QUESTMAP);
/*  129 */       ListMapUtil.clear(this.SHOPXMAP);
/*  130 */       ListMapUtil.clear(this.ATKNPC);
/*  131 */       ListMapUtil.clear(this.EZPAYLIST);
/*  132 */       ListMapUtil.clear(this.SHOPLIST);
/*      */       
/*  134 */       this.DELIST = null;
/*  135 */       this._cnList = null;
/*  136 */       this._cnSList = null;
/*  137 */       this._gamList = null;
/*  138 */       this._gamSellList = null;
/*  139 */       this._illusoryList = null;
/*  140 */       this._teleport = null;
/*  141 */       this._uplevelList = null;
/*  142 */       this._shiftingList = null;
/*  143 */       this._sitemList = null;
/*  144 */       this._sitemList2 = null;
/*  145 */       this.QUESTMAP = null;
/*  146 */       this.SHOPXMAP = null;
/*  147 */       this.ATKNPC = null;
/*  148 */       this.EZPAYLIST = null;
/*  149 */       this.SHOPLIST = null;
/*  150 */       this._is = null;
/*  151 */       this._pc = null;
/*      */     }
/*  153 */     catch (Exception e) {
/*  154 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, Integer> get_sitemList2() {
/*  165 */     return this._sitemList2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_sitemList2(Integer key, Integer value) {
/*  174 */     this._sitemList2.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear_sitemList2() {
/*  181 */     this._sitemList2.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, L1ItemInstance> get_sitemList() {
/*  191 */     return this._sitemList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_sitemList(Integer key, L1ItemInstance value) {
/*  200 */     this._sitemList.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear_sitemList() {
/*  207 */     this._sitemList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, String[]> get_shiftingList() {
/*  217 */     return this._shiftingList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_shiftingList(Integer key, String[] value) {
/*  226 */     this._shiftingList.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove_shiftingList(Integer key) {
/*  234 */     this._shiftingList.remove(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_shiftingList() {
/*      */     try {
/*  243 */       this._shiftingList.clear();
/*  244 */       Connection conn = null;
/*  245 */       PreparedStatement pstm = null;
/*  246 */       ResultSet rs = null;
/*      */       
/*      */       try {
/*  249 */         conn = DatabaseFactory.get().getConnection();
/*  250 */         pstm = conn.prepareStatement(
/*  251 */             "SELECT * FROM `characters` WHERE `account_name`=?");
/*  252 */         pstm.setString(1, this._pc.getAccountName());
/*  253 */         rs = pstm.executeQuery();
/*      */         
/*  255 */         int key = 0;
/*  256 */         while (rs.next()) {
/*  257 */           int objid = rs.getInt("objid");
/*  258 */           String name = rs.getString("char_name");
/*  259 */           if (!name.equalsIgnoreCase(this._pc.getName())) {
/*  260 */             key++;
/*  261 */             add_shiftingList(Integer.valueOf(key), new String[] { String.valueOf(objid), name });
/*      */           }
/*      */         
/*      */         } 
/*  265 */       } catch (Exception e) {
/*  266 */         _log.error(e.getLocalizedMessage(), e);
/*      */       } finally {
/*      */         
/*  269 */         SQLUtil.close(rs);
/*  270 */         SQLUtil.close(pstm);
/*  271 */         SQLUtil.close(conn);
/*      */       }
/*      */     
/*  274 */     } catch (Exception e) {
/*  275 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, L1IllusoryInstance> get_illusoryList() {
/*  286 */     return this._illusoryList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addIllusoryList(Integer key, L1IllusoryInstance value) {
/*  295 */     this._illusoryList.put(key, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeIllusoryList(Integer key) {
/*      */     try {
/*  304 */       if (this._illusoryList == null) {
/*      */         return;
/*      */       }
/*  307 */       if (this._illusoryList.get(key) != null) {
/*  308 */         this._illusoryList.remove(key);
/*      */       }
/*      */     }
/*  311 */     catch (Exception e) {
/*  312 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void teleport(HashMap<Integer, L1TeleportLoc> teleportMap) {
/*      */     try {
/*  324 */       ListMapUtil.clear(this._teleport);
/*  325 */       this._teleport.putAll(teleportMap);
/*      */     }
/*  327 */     catch (Exception e) {
/*  328 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, L1TeleportLoc> teleportMap() {
/*  337 */     return this._teleport;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sellall(Map<Integer, Integer> sellallMap) {
/*      */     try {
/*  346 */       int getprice = 0;
/*  347 */       int totalprice = 0;
/*  348 */       for (Integer integer : sellallMap.keySet()) {
/*  349 */         L1ItemInstance item = 
/*  350 */           this._pc.getInventory().getItem(integer.intValue());
/*  351 */         if (item != null) {
/*  352 */           int key = item.getItemId();
/*  353 */           int price = ShopTable.get().getPrice(key);
/*  354 */           Integer count = sellallMap.get(integer);
/*      */           
/*  356 */           if (price >= 200000 && count.intValue() > 9999) {
/*      */             
/*  358 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("販賣數量無法超過 9999個。"));
/*      */             
/*      */             return;
/*      */           } 
/*  362 */           totalprice += price * count.intValue();
/*  363 */           if (!RangeInt.includes(totalprice, 0, 2000000000)) {
/*  364 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("總共販賣價格無法超過 20億金幣。"));
/*      */             
/*      */             return;
/*      */           } 
/*  368 */           long remove = this._pc.getInventory().removeItem(integer.intValue(), count.intValue());
/*  369 */           if (remove == count.intValue()) {
/*  370 */             getprice += price * count.intValue();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  375 */       if (getprice > 0)
/*      */       {
/*  377 */         L1ItemInstance item = ItemTable.get().createItem(40308);
/*  378 */         item.setCount(getprice);
/*  379 */         createNewItem(item);
/*      */       }
/*      */     
/*  382 */     } catch (Exception e) {
/*  383 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sellforfirecrystal(Map<Integer, Integer> FCMap) {
/*      */     try {
/*  392 */       int allcount = 0;
/*  393 */       for (Iterator<Integer> iterator = FCMap.keySet().iterator(); iterator.hasNext(); ) { int objid = ((Integer)iterator.next()).intValue();
/*  394 */         L1ItemInstance item = this._pc.getInventory().getItem(objid);
/*  395 */         if (item != null) {
/*  396 */           int key = item.getItemId();
/*  397 */           if (item.getBless() == 0) {
/*  398 */             key = item.getItemId() - 100000;
/*  399 */           } else if (item.getBless() == 2) {
/*  400 */             key = item.getItemId() - 200000;
/*      */           } 
/*      */           
/*  403 */           L1FireCrystal firecrystal = L1FireSmithCrystalTable.get().getTemplate(key);
/*  404 */           int crystalcount = firecrystal.get_CrystalCount(item);
/*  405 */           int count = ((Integer)FCMap.get(Integer.valueOf(objid))).intValue();
/*      */           
/*  407 */           long remove = this._pc.getInventory().removeItem(objid, count);
/*  408 */           if (remove == count) {
/*  409 */             allcount += crystalcount * count;
/*      */           }
/*      */         }  }
/*      */ 
/*      */       
/*  414 */       if (allcount > 0)
/*      */       {
/*  416 */         L1ItemInstance item = ItemTable.get().createItem(80029);
/*  417 */         item.setCount(allcount);
/*  418 */         createNewItem(item);
/*      */       }
/*      */     
/*  421 */     } catch (Exception e) {
/*  422 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sellcnitem(Map<Integer, Integer> cnsellMap) {
/*      */     try {
/*  432 */       int allprice = 0;
/*  433 */       ArrayList<Integer> uplist = ItemPowerUpdateTable.get().get_updeatitemidlist();
/*  434 */       for (Iterator<Integer> iterator = cnsellMap.keySet().iterator(); iterator.hasNext(); ) { int objid = ((Integer)iterator.next()).intValue();
/*  435 */         L1ItemInstance item = this._pc.getInventory().getItem(objid);
/*  436 */         if (item != null) {
/*  437 */           int key = item.getItemId();
/*      */           
/*  439 */           int price = 0;
/*  440 */           int type_id = ItemPowerUpdateTable.get().get_original_type(key);
/*  441 */           int original_itemid = ItemPowerUpdateTable.get().get_original_itemid(type_id);
/*      */           
/*  443 */           if (uplist.contains(Integer.valueOf(key))) {
/*  444 */             price = ShopCnTable.get().getPrice(original_itemid);
/*      */           } else {
/*  446 */             price = ShopCnTable.get().getPrice(key);
/*      */           } 
/*      */           
/*  449 */           int count = ((Integer)cnsellMap.get(Integer.valueOf(objid))).intValue();
/*  450 */           long remove = this._pc.getInventory().removeItem(objid, count);
/*  451 */           if (remove == count) {
/*  452 */             allprice += price * count;
/*      */           }
/*      */         }  }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  459 */       if (allprice > 0)
/*      */       {
/*  461 */         L1ItemInstance item = ItemTable.get().createItem(44070);
/*  462 */         item.setCount(allprice);
/*  463 */         createNewItem(item);
/*      */       }
/*      */     
/*  466 */     } catch (Exception e) {
/*  467 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*      */     try {
/*  478 */       ListMapUtil.clear(this._cnList);
/*  479 */       ListMapUtil.clear(this._gamList);
/*      */     }
/*  481 */     catch (Exception e) {
/*  482 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_gamSellList(Map<Integer, L1Gambling> sellList) {
/*      */     try {
/*  494 */       ListMapUtil.clear(this._gamSellList);
/*  495 */       this._gamSellList.putAll(sellList);
/*      */     }
/*  497 */     catch (Exception e) {
/*  498 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get_sellGam(int objid, int count) {
/*      */     try {
/*  509 */       L1Gambling element = this._gamSellList.get(Integer.valueOf(objid));
/*  510 */       if (element == null) {
/*      */         return;
/*      */       }
/*  513 */       long countx = (long)(element.get_rate() * GamblingSet.GAMADENA) * count;
/*  514 */       long remove = this._pc.getInventory().removeItem(objid, count);
/*  515 */       if (remove == count) {
/*  516 */         int outcount = element.get_outcount() - count;
/*  517 */         if (outcount < 0) {
/*      */           return;
/*      */         }
/*  520 */         element.set_outcount(outcount);
/*  521 */         GamblingReading.get().updateGambling(element.get_id(), outcount);
/*      */         
/*  523 */         L1ItemInstance item = 
/*  524 */           ItemTable.get().createItem(GamblingSet.ADENAITEM);
/*  525 */         item.setCount(countx);
/*  526 */         createNewItem(item);
/*      */       }
/*      */     
/*  529 */     } catch (Exception e) {
/*  530 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_gamList(GamblingNpc element, int index) {
/*  540 */     this._gamList.put(new Integer(index), element);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get_buyGam(Map<Integer, Integer> gamMap) {
/*      */     try {
/*  549 */       for (Integer integer : gamMap.keySet()) {
/*  550 */         int index = integer.intValue();
/*  551 */         int count = ((Integer)gamMap.get(integer)).intValue();
/*  552 */         get_gamItem(index, count);
/*      */       } 
/*  554 */       ListMapUtil.clear(this._gamList);
/*      */     }
/*  556 */     catch (Exception e) {
/*  557 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void get_gamItem(int index, int count) {
/*      */     try {
/*  563 */       if (count <= 0) {
/*      */         return;
/*      */       }
/*  566 */       GamblingNpc element = this._gamList.get(Integer.valueOf(index));
/*  567 */       if (element == null) {
/*      */         return;
/*      */       }
/*      */       
/*  571 */       int npcid = element.get_npc().getNpcId();
/*  572 */       int no = GamblingTime.get_gamblingNo();
/*  573 */       long adena = (GamblingSet.GAMADENA * count);
/*  574 */       long srcCount = this._pc.getInventory().countItems(GamblingSet.ADENAITEM);
/*      */ 
/*      */       
/*  577 */       if (srcCount >= adena) {
/*      */         
/*  579 */         L1ItemInstance item = 
/*  580 */           ItemTable.get().createItem(40309);
/*      */         
/*  582 */         if (this._pc.getInventory().checkAddItem(item, count) == 0) {
/*      */           
/*  584 */           this._pc.getInventory().consumeItem(GamblingSet.ADENAITEM, adena);
/*      */           
/*  586 */           item.setCount(count);
/*  587 */           item.setraceGamNo(String.valueOf(no) + "-" + npcid);
/*  588 */           createNewItem(item);
/*  589 */           element.add_adena(adena);
/*      */         }
/*      */         else {
/*      */           
/*  593 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(270));
/*      */         } 
/*      */       } else {
/*      */         
/*  597 */         L1Item item = 
/*  598 */           ItemTable.get().getTemplate(GamblingSet.ADENAITEM);
/*  599 */         long nc = adena - srcCount;
/*      */         
/*  601 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(item.getNameId()) + "(" + nc + ")"));
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  606 */     catch (Exception e) {
/*  607 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_cnSList(L1ItemInstance shopItem, int index) {
/*  617 */     this._cnSList.put(new Integer(index), shopItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get_buyCnS(Map<Integer, Integer> cnMap) {
/*      */     try {
/*  625 */       int itemid_cn = Npc_ShopX._itemid;
/*  626 */       for (Integer integer : cnMap.keySet()) {
/*  627 */         int count = ((Integer)cnMap.get(integer)).intValue();
/*  628 */         if (count > 0) {
/*      */           
/*  630 */           L1ItemInstance element = this._cnSList.get(Integer.valueOf(integer.intValue()));
/*  631 */           L1ShopS shopS = 
/*  632 */             DwarfShopReading.get().getShopS(element.getId());
/*  633 */           if (element == null || shopS == null || 
/*  634 */             shopS.get_end() != 0) {
/*      */             continue;
/*      */           }
/*  637 */           if (shopS.get_item() == null) {
/*      */             continue;
/*      */           }
/*      */           
/*  641 */           L1ItemInstance itemT = this._pc.getInventory().checkItemX(itemid_cn, shopS.get_adena());
/*  642 */           if (itemT == null) {
/*      */             
/*  644 */             this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, ItemTable.get().getTemplate(Npc_ShopX._itemid).getName()));
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/*  649 */           shopS.set_end(1);
/*  650 */           shopS.set_item(null);
/*  651 */           DwarfShopReading.get().updateShopS(shopS);
/*  652 */           DwarfShopReading.get().deleteItem(element.getId());
/*      */           
/*  654 */           this._pc.getInventory().consumeItem(itemid_cn, shopS.get_adena());
/*  655 */           this._pc.getInventory().storeTradeItem(element);
/*  656 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, element.getLogName()));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  661 */       ListMapUtil.clear(this._cnList);
/*      */     }
/*  663 */     catch (Exception e) {
/*  664 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_cnList(int index, L1ShopItem shopItem) {
/*  674 */     this._cnList.put(Integer.valueOf(index), shopItem);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void get_buyCn(Map<Integer, Integer> cnMap) {
/*      */     try {
/*  682 */       if (this._pc.getInventory().getSize() + cnMap.size() >= 170) {
/*  683 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(263));
/*      */       } else {
/*  685 */         for (Iterator<Integer> iterator = cnMap.keySet().iterator(); iterator.hasNext(); ) { int index = ((Integer)iterator.next()).intValue();
/*  686 */           int count = ((Integer)cnMap.get(Integer.valueOf(index))).intValue();
/*  687 */           if (count > 0) {
/*  688 */             L1ShopItem element = this._cnList.get(Integer.valueOf(index));
/*  689 */             if (element != null) {
/*  690 */               get_cnItem(element, count);
/*      */             }
/*      */           }  }
/*      */       
/*      */       } 
/*  695 */       bonusCheck(this._pc.get_consume_point());
/*  696 */       this._pc.set_consume_point(0L);
/*  697 */       this._pc.set_temp_adena(0);
/*  698 */       ListMapUtil.clear(this._cnList);
/*      */     }
/*  700 */     catch (Exception e) {
/*  701 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void get_cnItem(L1ShopItem element, int count) {
/*      */     try {
/*  713 */       int itemid_cn = this._pc.get_temp_adena();
/*  714 */       int itemid = element.getItemId();
/*  715 */       int packcount = element.getPackCount();
/*  716 */       int getCount = count;
/*  717 */       if (packcount > 1) {
/*  718 */         getCount = element.getPackCount() * count;
/*      */       }
/*  720 */       int enchantlevel = element.getEnchantLevel();
/*  721 */       int adenaCount = element.getPrice() * count;
/*      */ 
/*      */       
/*  724 */       int DailyBuyingCount = element.getDailyBuyingCount();
/*  725 */       int DailyBuyinglevel = element.getDailyBuyinglevel();
/*      */ 
/*      */       
/*  728 */       if (this._pc.getInventory().checkAddItem(element.getItem(), getCount) != 0) {
/*      */         return;
/*      */       }
/*      */       
/*  732 */       if (adenaCount <= 0) {
/*  733 */         IpReading.get().add(this._pc.getAccountName(), "負數洗物:" + adenaCount);
/*  734 */         this._pc.getNetConnection().kick();
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  740 */       long srcCount = this._pc.getInventory().countItems(itemid_cn);
/*      */       
/*  742 */       if (srcCount >= adenaCount && this._pc.getInventory().checkItem(itemid_cn, adenaCount)) {
/*      */ 
/*      */         
/*  745 */         L1Item itemtmp = ItemTable.get().getTemplate(itemid);
/*  746 */         if (!itemtmp.isStackable() && this._pc.getInventory().getSize() + getCount >= 170) {
/*  747 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(263));
/*      */           
/*      */           return;
/*      */         } 
/*  751 */         if (DailyBuyinglevel != 0 && 
/*  752 */           this._pc.getLevel() < DailyBuyinglevel) {
/*  753 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("該商品限制購買等級:" + DailyBuyinglevel));
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/*      */ 
/*      */         
/*  760 */         if (itemtmp.isStackable()) {
/*  761 */           if (DailyBuyingCount > 0) {
/*  762 */             int AlreadyBoughtCount = this._pc.getQuest().get_step(itemid);
/*  763 */             int buyingcount = getCount;
/*  764 */             if (buyingcount > DailyBuyingCount) {
/*  765 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
/*      */               return;
/*      */             } 
/*  768 */             if (AlreadyBoughtCount < DailyBuyingCount && 
/*  769 */               buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
/*  770 */               this._pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
/*      */             } else {
/*      */               
/*  773 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  778 */           L1ItemInstance item = 
/*  779 */             ItemTable.get().createItem(itemid);
/*  780 */           item.setCount(getCount);
/*  781 */           item.setEnchantLevel(enchantlevel);
/*  782 */           item.setIdentified(true);
/*      */           
/*  784 */           bonusCheck1(adenaCount);
/*      */           
/*  786 */           createNewItem(item);
/*  787 */           toGmMsg(itemtmp, adenaCount, true, itemid_cn);
/*  788 */           L1Item temp = ItemTable.get().getTemplate(itemid_cn);
///*  789 */           RecordTable.get().recordeShop1(this._pc.getName(), temp.getName(), adenaCount, item.getAllName(), getCount, item.getId(), this._pc.getIp());
/*      */         } else {
/*      */           
/*  792 */           if (DailyBuyingCount > 0) {
/*  793 */             int AlreadyBoughtCount = this._pc.getQuest().get_step(itemid);
/*  794 */             int buyingcount = getCount;
/*  795 */             if (buyingcount > DailyBuyingCount) {
/*  796 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
/*      */               return;
/*      */             } 
/*  799 */             if (AlreadyBoughtCount < DailyBuyingCount && 
/*  800 */               buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
/*  801 */               this._pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
/*      */             } else {
/*      */               
/*  804 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("超過每日限制購買數量上限:" + element.getDailyBuyingCount()));
/*      */               return;
/*      */             } 
/*      */           } 
/*  808 */           L1ItemInstance item1 = ItemTable.get().createItem(itemid);
/*  809 */           L1Item temp = ItemTable.get().getTemplate(itemid_cn);
///*  810 */           RecordTable.get().recordeShop1(this._pc.getName(), temp.getName(), adenaCount, item1.getAllName(), getCount, item1.getId(), this._pc.getIp());
/*  811 */           for (int i = 0; i < getCount; i++) {
/*      */             
/*  813 */             L1ItemInstance item = ItemTable.get().createItem(itemid);
/*  814 */             item.setEnchantLevel(enchantlevel);
/*  815 */             item.setIdentified(true);
/*      */             
/*  817 */             createNewItem(item);
/*  818 */             toGmMsg(itemtmp, adenaCount, true, itemid_cn);
/*      */           } 
/*      */         } 
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
/*  832 */         this._pc.getInventory().consumeItem(itemid_cn, adenaCount);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  837 */         this._pc.set_consume_point(this._pc.get_consume_point() + adenaCount);
/*      */       } else {
/*      */         
/*  840 */         long nc = adenaCount - srcCount;
/*  841 */         L1Item item = ItemTable.get().getTemplate(itemid_cn);
/*      */         
/*  843 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(item.getName()) + "(" + nc + ")"));
/*      */       }
/*      */     
/*  846 */     } catch (Exception e) {
/*  847 */       _log.error(e.getLocalizedMessage(), e);
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
/*      */   private void toGmMsg(L1Item itemtmp, int adenaCount, boolean mode, int itemid_cn) {
/*      */     try {
/*  863 */       ServerCnInfoReading.get().create(this._pc, itemtmp, adenaCount, mode, itemid_cn);
/*      */       
/*  865 */       Collection<L1PcInstance> allPc = World.get().getAllPlayers();
/*  866 */       for (L1PcInstance tgpc : allPc) {
/*  867 */         if (tgpc.isGm()) {
/*  868 */           StringBuilder topc = new StringBuilder();
/*  869 */           if (mode) {
/*  870 */             topc.append("人物:" + this._pc.getName() + " 買入:" + itemtmp.getName() + " 花費商城幣:" + adenaCount);
/*  871 */             tgpc.sendPackets((ServerBasePacket)new S_ServerMessage(166, topc.toString()));
/*      */             continue;
/*      */           } 
/*  874 */           topc.append("人物:" + this._pc.getName() + " 賣出:" + itemtmp.getName() + " 獲得商城幣:" + adenaCount);
/*  875 */           tgpc.sendPackets((ServerBasePacket)new S_ServerMessage(166, topc.toString()));
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  880 */     } catch (Exception e) {
/*  881 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createNewItem(L1ItemInstance item) {
/*      */     try {
/*  892 */       this._pc.getInventory().storeItem(item);
/*  893 */       this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*      */     }
/*  895 */     catch (Exception e) {
/*  896 */       _log.error(e.getLocalizedMessage(), e);
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
/*      */   public void add_levelList(int key, int value) {
/*  925 */     this._uplevelList.put(Integer.valueOf(key), Integer.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map<Integer, Integer> get_uplevelList() {
/*  933 */     return this._uplevelList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer get_uplevelList(int key) {
/*  942 */     return this._uplevelList.get(Integer.valueOf(key));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear_uplevelList() {
/*  949 */     ListMapUtil.clear(this._uplevelList);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void set_newPcOriginal(int[] is) {
/*  957 */     this._is = is;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] get_newPcOriginal() {
/*  965 */     return this._is;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void bonusCheck(long count) {
/*  973 */     if (count == 0L) {
/*      */       return;
/*      */     }
/*  976 */     Connection co = null;
/*  977 */     PreparedStatement ps = null;
/*  978 */     ResultSet rs = null;
/*      */     try {
/*  980 */       co = DatabaseFactoryLogin.get().getConnection();
/*  981 */       String sqlstr = "SELECT * FROM `shop_cn_商城滿額獎勵` ORDER BY `滿額金額` DESC";
/*  982 */       ps = co.prepareStatement("SELECT * FROM `shop_cn_商城滿額獎勵` ORDER BY `滿額金額` DESC");
/*  983 */       rs = ps.executeQuery();
/*      */       
/*  985 */       Timestamp now_time = new Timestamp(System.currentTimeMillis());
/*  986 */       while (rs.next()) {
/*  987 */         Timestamp end_time = rs.getTimestamp("限制結束時間");
/*      */         
/*  989 */         if (end_time != null && now_time.after(end_time)) {
/*      */           continue;
/*      */         }
/*      */         
/*  993 */         int need_counts = rs.getInt("滿額金額");
/*  994 */         if (count < need_counts) {
/*      */           continue;
/*      */         }
/*  997 */         int item_id = rs.getInt("物品編號");
/*  998 */         int counts = rs.getInt("物品數量");
/*      */         
/* 1000 */         L1ItemInstance item = ItemTable.get().createItem(item_id);
/* 1001 */         if (item == null) {
/* 1002 */           _log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")"); break;
/*      */         } 
/* 1004 */         if (item.isStackable()) {
/* 1005 */           item.setCount(counts);
/* 1006 */           item.setIdentified(true);
/* 1007 */           this._pc.getInventory().storeItem(item);
/*      */         } else {
/* 1009 */           item.setIdentified(true);
/* 1010 */           this._pc.getInventory().storeItem(item);
/*      */         } 
/* 1012 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/* 1017 */     } catch (Exception e) {
/* 1018 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/* 1021 */       SQLUtil.close(ps);
/* 1022 */       SQLUtil.close(co);
/* 1023 */       SQLUtil.close(rs);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void bonusCheck1(long count) {
/* 1028 */     if (count == 0L) {
/*      */       return;
/*      */     }
/* 1031 */     Connection co = null;
/* 1032 */     PreparedStatement ps = null;
/* 1033 */     ResultSet rs = null;
/*      */     try {
/* 1035 */       co = DatabaseFactoryLogin.get().getConnection();
/* 1036 */       String sqlstr = "SELECT * FROM `shop_商城一比一回饋` ORDER BY `道具編號` DESC";
/* 1037 */       ps = co.prepareStatement("SELECT * FROM `shop_商城一比一回饋` ORDER BY `道具編號` DESC");
/* 1038 */       rs = ps.executeQuery();
/*      */       
/* 1040 */       Timestamp now_time = new Timestamp(System.currentTimeMillis());
/* 1041 */       while (rs.next()) {
/* 1042 */         Timestamp end_time = rs.getTimestamp("限制結束時間");
/*      */         
/* 1044 */         if (end_time != null && now_time.after(end_time)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1052 */         int item_id = rs.getInt("道具編號");
/*      */         
/* 1054 */         L1ItemInstance item = ItemTable.get().createItem(item_id);
/* 1055 */         if (item == null) {
/* 1056 */           _log.error("給予物件失敗 原因: 指定編號物品不存在(" + item_id + ")"); break;
/*      */         } 
/* 1058 */         if (item.isStackable()) {
/* 1059 */           item.setCount(count);
/* 1060 */           item.setIdentified(true);
/* 1061 */           this._pc.getInventory().storeItem(item);
/*      */         } else {
/* 1063 */           item.setIdentified(true);
/* 1064 */           this._pc.getInventory().storeItem(item);
/*      */         } 
/* 1066 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/* 1071 */     } catch (Exception e) {
/* 1072 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } finally {
/*      */       
/* 1075 */       SQLUtil.close(ps);
/* 1076 */       SQLUtil.close(co);
/* 1077 */       SQLUtil.close(rs);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void add_cnList(L1ShopItem shopItem, int index) {
/* 1084 */     this._cnList.put(new Integer(index), shopItem);
/*      */   }
/*      */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\templates\L1PcOtherList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */