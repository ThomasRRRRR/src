/*     */ package com.lineage.server.serverpackets;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.server.datatables.lock.DwarfShopReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1ItemStatus;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.templates.L1ShopS;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S_CnSShopSellListarmor
/*     */   extends ServerBasePacket
/*     */ {
/*  23 */   private byte[] _byte = null;
/*     */   
/*     */   public S_CnSShopSellListarmor(L1PcInstance pc, L1NpcInstance npc, int type) {
/*  26 */     buildPacket(pc, npc.getId(), type);
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildPacket(L1PcInstance pc, int tgObjid, int type) {
/*  31 */     Map<L1ShopS, L1ItemInstance> shopItems = new HashMap<>();
/*     */     
/*  33 */     Map<Integer, L1ItemInstance> srcMap = DwarfShopReading.get().allItems();
/*     */     
/*  35 */     for (Integer key : srcMap.keySet()) {
/*  36 */       L1ShopS info = DwarfShopReading.get().getShopS(key.intValue());
/*  37 */       if (info == null) {
/*     */         continue;
/*     */       }
/*  40 */       if (info.get_end() != 0) {
/*     */         continue;
/*     */       }
/*  43 */       if (info.get_item() == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  47 */       L1ItemInstance item = srcMap.get(key);
/*  48 */       switch (type) {
/*     */         case 2:
/*  50 */           if (item.getItem().getUseType() != 2) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 18:
/*  55 */           if (item.getItem().getUseType() != 18) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 19:
/*  60 */           if (item.getItem().getUseType() != 19) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 20:
/*  65 */           if (item.getItem().getUseType() != 20) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 21:
/*  70 */           if (item.getItem().getUseType() != 21) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 22:
/*  75 */           if (item.getItem().getUseType() != 22) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 25:
/*  80 */           if (item.getItem().getUseType() != 25) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 37:
/*  85 */           if (item.getItem().getUseType() != 37) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 23:
/*  90 */           if (item.getItem().getUseType() != 23) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 40:
/*  95 */           if (item.getItem().getUseType() != 40) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 24:
/* 100 */           if (item.getItem().getUseType() != 24) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */         case 50:
/* 105 */           if (item.getItem().getUseType() == 2) {
/*     */             continue;
/*     */           }
/* 108 */           if (item.getItem().getUseType() == 18) {
/*     */             continue;
/*     */           }
/* 111 */           if (item.getItem().getUseType() == 19) {
/*     */             continue;
/*     */           }
/* 114 */           if (item.getItem().getUseType() == 20) {
/*     */             continue;
/*     */           }
/* 117 */           if (item.getItem().getUseType() == 21) {
/*     */             continue;
/*     */           }
/* 120 */           if (item.getItem().getUseType() == 22) {
/*     */             continue;
/*     */           }
/* 123 */           if (item.getItem().getUseType() == 25) {
/*     */             continue;
/*     */           }
/* 126 */           if (item.getItem().getUseType() == 37) {
/*     */             continue;
/*     */           }
/* 129 */           if (item.getItem().getUseType() == 23) {
/*     */             continue;
/*     */           }
/* 132 */           if (item.getItem().getUseType() == 24) {
/*     */             continue;
/*     */           }
/* 135 */           if (item.getItem().getUseType() == 40) {
/*     */             continue;
/*     */           }
/* 138 */           if (item.getItem().getUseType() == 1) {
/*     */             continue;
/*     */           }
/*     */           break;
/*     */       } 
/* 143 */       shopItems.put(info, item);
/*     */     } 
/*     */     
/* 146 */     writeC(70);
/*     */     
/* 148 */     writeD(tgObjid);
/*     */     
/* 150 */     if (shopItems.size() <= 0) {
/* 151 */       writeH(0);
/*     */       
/*     */       return;
/*     */     } 
/* 155 */     writeH(shopItems.size());
/*     */     
/* 157 */     int i = 0;
/* 158 */     for (L1ShopS key : shopItems.keySet()) {
/* 159 */       i++;
/* 160 */       L1ItemInstance item = shopItems.get(key);
/* 161 */       pc.get_otherList().add_cnSList(item, i);
/*     */       
/* 163 */       writeD(i);
/* 164 */       writeH(item.getItem().getGfxId());
/* 165 */       writeD(key.get_adena());
/*     */ 
/* 75 */       writeS(item.getLogName());
/*     */ 
/*     */       
/* 170 */       if (ConfigOther.SHOPINFO) {
/* 171 */         L1ItemStatus itemInfo = new L1ItemStatus(item);
/* 172 */         byte[] status = itemInfo.getStatusBytes(true).getBytes();
/* 173 */         writeC(status.length); byte b; int j; byte[] arrayOfByte1;
/* 174 */         for (j = (arrayOfByte1 = status).length, b = 0; b < j; ) { byte b1 = arrayOfByte1[b];
/* 175 */           writeC(b1); b++; }
/*     */          continue;
/*     */       } 
/* 178 */       writeC(0);
/*     */     } 
/*     */     
/* 181 */     writeH(6100);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getContent() {
/* 186 */     if (this._byte == null) {
/* 187 */       this._byte = getBytes();
/*     */     }
/* 189 */     return this._byte;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 194 */     return getClass().getSimpleName();
/*     */   }
/*     */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\server\serverpackets\S_CnSShopSellListarmor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */