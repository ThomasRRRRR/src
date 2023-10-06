/*     */ package com.lineage.server.model.shop;
/*     */ 
/*     */ import com.lineage.config.ConfigRate;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.CastleReading;
/*     */ import com.lineage.server.datatables.lock.TownReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1CastleLocation;
/*     */ import com.lineage.server.model.L1PcInventory;
/*     */ import com.lineage.server.model.L1TaxCalculator;
/*     */ import com.lineage.server.model.L1TownLocation;
/*     */ import com.lineage.server.serverpackets.S_Disconnect;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Castle;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.templates.L1ShopItem;
/*     */ import com.lineage.server.utils.RangeInt;
/*     */ import com.lineage.server.world.World;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class L1Shop
/*     */ {
/*     */   private final int _npcId;
/*     */   private final int _currencyItemId;
/*     */   private final List<L1ShopItem> _sellingItems;
/*     */   private final List<L1ShopItem> _purchasingItems;
/*     */   
/*     */   public L1Shop(int npcId, int currencyItemId, List<L1ShopItem> sellingItems, List<L1ShopItem> purchasingItems) {
/*  39 */     if (sellingItems == null || purchasingItems == null) {
/*  40 */       throw new NullPointerException();
/*     */     }
/*     */     
/*  43 */     this._npcId = npcId;
/*  44 */     this._currencyItemId = currencyItemId;
/*  45 */     this._sellingItems = sellingItems;
/*  46 */     this._purchasingItems = purchasingItems;
/*     */   }
/*     */   
/*     */   public int getNpcId() {
/*  50 */     return this._npcId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<L1ShopItem> getSellingItems() {
/*  58 */     return this._sellingItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<L1ShopItem> getPurchasingItems() {
/*  66 */     return this._purchasingItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelling(int itemid) {
/*  75 */     for (L1ShopItem shopitem : this._sellingItems) {
/*  76 */       if (shopitem.getItemId() == itemid) {
/*  77 */         return true;
/*     */       }
/*     */     } 
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPurchasing(int itemid) {
/*  89 */     for (L1ShopItem shopitem : this._purchasingItems) {
/*  90 */       if (shopitem.getItemId() == itemid) {
/*  91 */         return true;
/*     */       }
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPurchaseableItem(L1ItemInstance item) {
/* 100 */     if (item == null) {
/* 101 */       return false;
/*     */     }
/* 103 */     if (item.isEquipped()) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (item.getEnchantLevel() != 0) {
/* 107 */       return false;
/*     */     }
/* 109 */     if (item.getBless() >= 128) {
/* 110 */       return false;
/*     */     }
/*     */     
if (item.getItem().cantBeSold()) {
	return false;
}
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   private L1ShopItem getPurchasingItem(int itemId) {
/* 117 */     for (L1ShopItem shopItem : this._purchasingItems) {
/* 118 */       if (shopItem.getItemId() == itemId) {
/* 119 */         return shopItem;
/*     */       }
/*     */     } 
/* 122 */     return null;
/*     */   }
/*     */   
/*     */   public L1AssessedItem assessItem(L1ItemInstance item) {
/* 126 */     L1ShopItem shopItem = getPurchasingItem(item.getItemId());
/* 127 */     if (shopItem == null) {
/* 128 */       return null;
/*     */     }
/* 130 */     return new L1AssessedItem(item.getId(), getAssessedPrice(shopItem), item.getName2());
/*     */   }
/*     */   
/*     */   private int getAssessedPrice(L1ShopItem item) {
/* 134 */     return (int)(item.getPrice() * ConfigRate.RATE_SHOP_PURCHASING_PRICE / item.getPackCount());
/*     */   }
/*     */ 
/*     */   
public List<L1AssessedItem> assessItems(L1PcInventory inv) {
    List<L1AssessedItem> result = new ArrayList<>();
    if (_purchasingItems != null) {
        for (L1ShopItem item : _purchasingItems) {
            L1ItemInstance[] arrayOfL1ItemInstance = inv.findItemsId(item.getItemId());
            if (arrayOfL1ItemInstance != null) {
                for (int i = 0; i < arrayOfL1ItemInstance.length; i++) {
                    L1ItemInstance targetItem = arrayOfL1ItemInstance[i];
                    if (isPurchaseableItem(targetItem)) {
                        result.add(new L1AssessedItem(targetItem.getId(), getAssessedPrice(item), targetItem.getName()));
                    }
                }
            }
        }
    }
    return result;
}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ensureSell(L1PcInstance pc, L1ShopBuyOrderList orderList) {
/* 160 */     int price = orderList.getTotalPrice();
/*     */     
/* 162 */     if (!RangeInt.includes(price, 0, 2000000000)) {
/*     */       
/* 164 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(904, "2000000000"));
/* 165 */       return false;
/*     */     } 
/*     */     
/* 168 */     for (L1ShopBuyOrder order : orderList.getList()) {
/* 169 */       int itemid = order.getItem().getItemId();
/* 170 */       if (itemid == 56148 && (
/* 171 */         pc.getInventory().findItemId(56147) != null || 
/* 172 */         pc.getInventory().findItemId(56148) != null)) {
/* 173 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("身上已有妲蒂斯的魔力。"));
/* 174 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 179 */       if (!pc.getInventory().checkItem(this._currencyItemId, price)) {
/* 180 */         L1Item item = ItemTable.get().getTemplate(this._currencyItemId);
/* 181 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, item.getName()));
/* 182 */         return false;
/*     */       } 
/*     */       
/* 185 */       int currentWeight = pc.getInventory().getWeight() * 1000;
/* 186 */       if ((currentWeight + orderList.getTotalWeight()) > pc.getMaxWeight() * 1000.0D) {
/* 187 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(82));
/* 188 */         return false;
/*     */       } 
/* 190 */       int DailyBuyinglevel = order.getItem().getDailyBuyinglevel();
/* 191 */       if (DailyBuyinglevel != 0 && 
/* 192 */         pc.getLevel() < DailyBuyinglevel) {
/* 193 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("該商品限制購買等級:" + DailyBuyinglevel));
/* 194 */         return false;
/*     */       } 
/*     */       
/* 197 */       int DailyBuyingCount = order.getItem().getDailyBuyingCount();
/* 198 */       if (DailyBuyingCount > 0) {
/* 199 */         int AlreadyBoughtCount = pc.getQuest().get_step(itemid);
/* 200 */         int buyingcount = order.getCount();
/*     */         
/* 202 */         if (AlreadyBoughtCount < DailyBuyingCount && 
/* 203 */           buyingcount + AlreadyBoughtCount <= DailyBuyingCount) {
/* 204 */           pc.getQuest().set_step(itemid, buyingcount + AlreadyBoughtCount);
/*     */           continue;
/*     */         } 
/* 207 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("超過每日限制購買數量上限:" + order.getItem().getDailyBuyingCount()));
/* 208 */         return false;
/*     */       } 
/*     */     } 
/*     */     
int totalCount = pc.getInventory().getSize();
for (L1ShopBuyOrder order : orderList.getList()) {
    L1Item temp = order.getItem().getItem();
    if (temp.isStackable() && !pc.getInventory().checkItem(temp.getItemId())) {
        totalCount++;
    } else {
        totalCount++;
    }
} 
/*     */     
/* 223 */     if (totalCount > 180) {
/*     */       
/* 225 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(263));
/* 226 */       return false;
/*     */     } 
/* 228 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void payCastleTax(L1ShopBuyOrderList orderList) {
/* 237 */     L1TaxCalculator calc = orderList.getTaxCalculator();
/*     */     
/* 239 */     int price = orderList.getTotalPrice();
/*     */     
/* 241 */     int castleId = L1CastleLocation.getCastleIdByNpcid(this._npcId);
/* 242 */     int castleTax = calc.calcCastleTaxPrice(price);
/* 243 */     int nationalTax = calc.calcNationalTaxPrice(price);
/*     */     
/* 245 */     if (castleId == 7 || 
/* 246 */       castleId == 8) {
/* 247 */       castleTax += nationalTax;
/* 248 */       nationalTax = 0;
/*     */     } 
/*     */     
/* 251 */     if (castleId != 0 && castleTax > 0) {
/* 252 */       L1Castle castle = CastleReading.get().getCastleTable(castleId);
/*     */       
/* 254 */       synchronized (castle) {
/* 255 */         long money = castle.getPublicMoney();
/* 256 */         money += castleTax;
/* 257 */         castle.setPublicMoney(money);
/* 258 */         CastleReading.get().updateCastle(castle);
/*     */       } 
/*     */       
/* 261 */       if (nationalTax > 0) {
/* 262 */         L1Castle aden = CastleReading.get().getCastleTable(7);
/* 263 */         synchronized (aden) {
/* 264 */           long money = aden.getPublicMoney();
/* 265 */           money += nationalTax;
/* 266 */           aden.setPublicMoney(money);
/* 267 */           CastleReading.get().updateCastle(aden);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void payDiadTax(L1ShopBuyOrderList orderList) {
/* 279 */     L1TaxCalculator calc = orderList.getTaxCalculator();
/*     */     
/* 281 */     int price = orderList.getTotalPrice();
/*     */     
/* 283 */     int diadTax = calc.calcDiadTaxPrice(price);
/* 284 */     if (diadTax <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 288 */     L1Castle castle = CastleReading.get().getCastleTable(8);
/* 289 */     synchronized (castle) {
/* 290 */       long money = castle.getPublicMoney();
/* 291 */       money += diadTax;
/* 292 */       castle.setPublicMoney(money);
/* 293 */       CastleReading.get().updateCastle(castle);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void payTownTax(L1ShopBuyOrderList orderList) {
/* 303 */     int price = orderList.getTotalPrice();
/*     */     
/* 305 */     if (!World.get().isProcessingContributionTotal()) {
/* 306 */       int town_id = L1TownLocation.getTownIdByNpcid(this._npcId);
/* 307 */       if (town_id >= 1 && town_id <= 10) {
/* 308 */         TownReading.get().addSalesMoney(town_id, price);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void payTax(L1ShopBuyOrderList orderList) {
/* 319 */     payCastleTax(orderList);
/* 320 */     payTownTax(orderList);
/* 321 */     payDiadTax(orderList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
private void sellItems(L1PcInventory inv, L1ShopBuyOrderList orderList, L1PcInstance pc) {
    if (orderList == null) {
        return;
    }
    
    int price = orderList.getTotalPrice();
    if (price <= 0) {
        return;
    }
    
    if (!inv.consumeItem(this._currencyItemId, price)) {
        return;
    }
    
    for (L1ShopBuyOrder order : orderList.getList()) {
        int itemId = order.getItem().getItemId();
        long amount = order.getCount();
        int enchantLevel = order.getItem().getEnchantLevel();
        
        if (amount <= 0) {
            continue;
        }
        
        L1ItemInstance item = ItemTable.get().createItem(itemId);
        item.setCount(amount);
        item.setIdentified(true);
        item.setEnchantLevel(enchantLevel);
        
        inv.storeItem(item);
        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
    }
}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sellItems(L1PcInstance pc, L1ShopBuyOrderList orderList) {
/* 366 */     if (orderList.isEmpty()) {
/*     */       return;
/*     */     }
/* 369 */     if (!ensureSell(pc, orderList)) {
/*     */       return;
/*     */     }
/* 372 */     if (pc.getInventory().getSize() + orderList.getList().size() >= 180) {
/*     */       
/* 374 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(263));
/*     */       
/*     */       return;
/*     */     } 
/* 378 */     if (getNpcId() == 93214) {
/* 379 */       if (!ensureCashSell2(pc, orderList, getNpcId())) {
/*     */         return;
/*     */       }
/* 382 */       sellCashItems2(pc, pc.getInventory(), orderList, getNpcId());
/*     */       return;
/*     */     } 
/* 385 */     sellItems(pc.getInventory(), orderList, pc);
this.payTax(orderList);
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
/*     */   private boolean ensureCashSell2(L1PcInstance pc, L1ShopBuyOrderList orderList, int npcId) {
/* 398 */     int price = orderList.getTotalPrice();
/* 399 */     L1Item item = ItemTable.get().getTemplate(95294);
/* 400 */     long srcCount = pc.getInventory().countItems(95294);
/* 401 */     long nc = price - srcCount;
/*     */     
/* 403 */     if (!pc.getInventory().checkItem(95294, price)) {
/* 404 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(item.getNameId()) + "(" + nc + ")"));
/* 405 */       return false;
/*     */     } 
/*     */     
/* 408 */     int currentWeight = pc.getInventory().getWeight() * 1000;
/* 409 */     if ((currentWeight + orderList.getTotalWeight()) > pc.getMaxWeight() * 1000.0D) {
/*     */       
/* 411 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(82));
/* 412 */       return false;
/*     */     } 
/*     */     
/* 415 */     int totalCount = pc.getInventory().getSize();
/* 416 */     for (L1ShopBuyOrder order : orderList.getList()) {
/* 417 */       L1Item temp = order.getItem().getItem();
/* 418 */       if (temp.isStackable()) {
/* 419 */         if (!pc.getInventory().checkItem(temp.getItemId()))
/* 420 */           totalCount++;  continue;
/*     */       } 
/* 422 */       totalCount++;
/*     */     } 
/*     */     
/* 425 */     if (totalCount > 180) {
/*     */       
/* 427 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(263));
/* 428 */       return false;
/*     */     } 
/* 430 */     if (price <= 0 || price > 100000000) {
/* 431 */       pc.sendPackets((ServerBasePacket)new S_Disconnect());
/* 432 */       return false;
/*     */     } 
/* 434 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sellCashItems2(L1PcInstance pc, L1PcInventory inv, L1ShopBuyOrderList orderList, int npcId) {
/* 445 */     if (!inv.consumeItem(95294, orderList.getTotalPrice())) {
/* 446 */       throw new IllegalStateException("屍魂幣不足.");
/*     */     }
/* 448 */     L1ItemInstance item = null;
/* 449 */     for (L1ShopBuyOrder order : orderList.getList()) {
/* 450 */       int itemId = order.getItem().getItemId();
/* 451 */       int amount = order.getCount();
/* 452 */       int EnchantLevel = order.getItem().getEnchantLevel();
/* 453 */       item = ItemTable.get().createItem(itemId);
/* 454 */       if (amount <= 0) {
/*     */         continue;
/*     */       }
/* 457 */       item.setCount(amount);
/* 458 */       item.setEnchantLevel(EnchantLevel);
/* 459 */       item.setIdentified(true);
/* 460 */       inv.storeItem(item);
/* 461 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buyItems(L1PcInstance pc, L1ShopSellOrderList orderList) {
/* 472 */     L1PcInventory inv = orderList.getPc().getInventory();
/* 473 */     int totalPrice = 0;
/* 474 */     for (L1ShopSellOrder order : orderList.getList()) {
/* 475 */       long count = inv.removeItem(order.getItem().getTargetId(), order.getCount());
/* 476 */       if (count > 0L) {
/* 477 */         totalPrice = (int)(totalPrice + order.getItem().getAssessedPrice() * count);
/* 478 */         sellnpcitem("IP(" + pc.getNetConnection().getIp() + ")" + 
/* 479 */             "玩家" + ":【" + pc.getName() + "】將" + order.getItem().getTargetName() + "(" + order.getCount() + ")個,賣給Npc獲得金幣(" + (order.getItem().getAssessedPrice() * count) + 
/* 480 */             ")時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。"); continue;
/*     */       } 
/* 482 */       sellnpcitem("IP(" + pc.getNetConnection().getIp() + ")" + 
/* 483 */           "玩家非法販售,數量小於0" + ":【" + pc.getName() + "】將" + order.getItem().getTargetId() + "(" + order.getCount() + ")個,賣給Npc獲得金幣(" + (order.getItem().getAssessedPrice() * count) + 
/* 484 */           ")時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     totalPrice = RangeInt.ensure(totalPrice, 0, 2000000000);
/* 491 */     if (totalPrice > 0) {
/* 492 */       L1ItemInstance item = ItemTable.get().createItem(this._currencyItemId);
/* 493 */       item.setCount(totalPrice);
/* 494 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(403, item.getLogName()));
/* 495 */       inv.storeItem(item);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public L1ShopBuyOrderList newBuyOrderList() {
/* 501 */     return new L1ShopBuyOrderList(this);
/*     */   }
/*     */   
/*     */   public L1ShopSellOrderList newSellOrderList(L1PcInstance pc) {
/* 505 */     return new L1ShopSellOrderList(this, pc);
/*     */   }
/*     */   private static void sellnpcitem(String info) {
/*     */     try {
/* 509 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 510 */             "玩家紀錄/萬物回收商紀錄.txt", true));
/* 511 */       out.write(String.valueOf(info) + "\r\n");
/* 512 */       out.close();
/* 513 */     } catch (IOException e) {
/* 514 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\shop\L1Shop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */