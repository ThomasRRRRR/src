package com.lineage.server.datatables.storage;

import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public interface CharItemsStorage {
  void load();
  
  CopyOnWriteArrayList<L1ItemInstance> loadItems(Integer paramInteger);
  
  void delUserItems(Integer paramInteger);
  
  boolean getUserItems(Integer paramInteger, int paramInt, long paramLong);
  
  boolean getUserItem(int paramInt);
  
  void del_item(int paramInt);
  
  void storeItem(int paramInt, L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void deleteItem(int paramInt, L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemId_Name(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemId(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemCount(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemDurability(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemChargeCount(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemRemainingTime(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemEnchantLevel(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemEquipped(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemIdentified(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemBless(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemAttrEnchantKind(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemAttrEnchantLevel(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemAttack(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemBowAttack(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemHit(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemBowHit(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemReductionDmg(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemSp(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemprobability(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemStr(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemDex(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemInt(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemCon(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemCha(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemWis(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemHp(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemMp(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateskilltype(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateskilltypelv(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemMr(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemAc(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemMag_Red(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemMag_Hit(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemDg(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistSustain(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistStun(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistStone(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistSleep(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistFreeze(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemistBlind(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemArmorLv(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemArmorType(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemType(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemHpr(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemMpr(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemhppotion(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateBless(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateItemDelayEffect(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  int getItemCount(int paramInt) throws Exception;
  
  void getAdenaCount(int paramInt, long paramLong) throws Exception;
  
  void updateGamNo(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateGamNpcId(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateStarNpcId(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  void updateWeightReduction(L1ItemInstance paramL1ItemInstance) throws Exception;
  
  Map<Integer, L1ItemInstance> getUserItems(int paramInt);
  
  int checkItemId(int paramInt);
}


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\storage\CharItemsStorage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */