/*     */ package com.lineage.data.item_etcitem.reel;
/*     */ 
/*     */ import com.lineage.config.Configtype;
/*     */ import com.lineage.data.cmd.EnchantWeapon;
/*     */ import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.ItemMaxENTable;
/*     */ import com.lineage.server.datatables.ItemNoENTable;
/*     */ import com.lineage.server.datatables.RecordTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1ItemUpdata;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;

import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrollEnchantWeapon
/*     */   extends ItemExecutor
/*     */ {
/*  33 */   final Random random = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemExecutor get() {
/*  43 */     return new ScrollEnchantWeapon();
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
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  55 */     int targObjId = data[0];
/*     */     
/*  57 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */     
/*  59 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  62 */     if (tgItem.isEquipped()) {
/*  63 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
/*     */       return;
/*     */     } 
/*  66 */     if (ItemNoENTable.RESTRICTIONS.contains(Integer.valueOf(tgItem.getItemId()))) {
/*  67 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品禁止強化。"));
/*     */       return;
/*     */     } 
/*  63 */     if (ItemMaxENTable.RESTRICTIONS.contains(Integer.valueOf(tgItem.getItemId())) && ItemMaxENTable.RESTRICTIONS1.contains(Integer.valueOf(tgItem.getEnchantLevel()))) {
/*  64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品已達強化上限。"));
/*     */       
/*     */       return;
/*     */     }
/*  62 */     if (!tgItem.isIdentified()) {
/*  63 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該裝備未鑑定無法強化"));
/*     */       return;
/*     */     } 
/*  70 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*  71 */     boolean isErr = false;
/*     */ 
/*     */     
/*  74 */     int use_type = tgItem.getItem().getUseType();
/*  75 */     switch (use_type) {
/*     */       case 1:
/*  77 */         if (safe_enchant < 0) {
/*  78 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  83 */         isErr = true;
/*     */         break;
/*     */     } 
/*     */     
/*  87 */     int weaponId = tgItem.getItem().getItemId();
/*  88 */     if (weaponId >= 246 && weaponId <= 255) {
/*  89 */       isErr = true;
/*     */     }
/*  91 */     if (weaponId >= 301 && weaponId <= 310) {
/*  92 */       isErr = true;
/*     */     }
/*     */     
/*  95 */     if (weaponId >= 1040 && weaponId <= 1043) {
/*  96 */       isErr = true;
/*     */     }
/*  98 */     if (weaponId >= 100213 && weaponId <= 100217) {
/*  99 */       isErr = true;
/*     */     }
/* 101 */     if (weaponId >= 100213 && weaponId <= 100217) {
/* 102 */       isErr = true;
/*     */     }
/* 104 */     if (tgItem.getBless() >= 128) {
/* 105 */       isErr = true;
/*     */     }
/* 107 */     if (isErr) {
/* 108 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       return;
/*     */     } 
/* 111 */     if (tgItem.getEnchantLevel() == Configtype.weaponlv) {
/* 112 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("您的武器已達上限值。"));
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     int enchant_level = tgItem.getEnchantLevel();
/* 117 */     EnchantWeapon enchantWeapon = new EnchantWeapon();
/* 118 */     int randomELevel = enchantWeapon.randomELevel(tgItem, item.getBless());
/* 119 */     pc.getInventory().removeItem(item, 1L);
/*     */     
/* 121 */     boolean isEnchant = true;
/* 122 */     boolean isEnchant1 = false;
/* 123 */     if (enchant_level < -6) {
/* 124 */       isEnchant = false;
/*     */     }
/* 126 */     else if (enchant_level < safe_enchant) {
/* 127 */       isEnchant = true;
/*     */     } else {
/*     */       int enchant_chance_wepon, enchant_level_tmp;
/*     */       
/* 131 */       Random random = new Random();
/* 132 */       int rnd2 = random.nextInt(100) + 1;
/*     */ 
/*     */ 
/*     */       
/* 136 */       if (safe_enchant == 0) {
/* 137 */         enchant_level_tmp = enchant_level + 6;
/*     */       } else {
/*     */         
/* 140 */         enchant_level_tmp = enchant_level;
/*     */       } 
/*     */       
/* 143 */       if (enchant_level >= 9) {
/* 144 */         enchant_chance_wepon = (int)L1ItemUpdata.enchant_wepon_up9(enchant_level_tmp);
/*     */       } else {
/*     */         
/* 147 */         enchant_chance_wepon = (int)L1ItemUpdata.enchant_wepon_dn9(enchant_level_tmp);
/*     */       } 
/*     */       
/* 150 */       if (item.getItemId() == 44066) {
/* 151 */         enchant_chance_wepon *= 2;
/*     */       }
/*     */       
/* 154 */       if (rnd2 < enchant_chance_wepon) {
/* 155 */         isEnchant = true;
/*     */       
/*     */       }
/* 158 */       else if (enchant_level >= 9 && rnd2 < enchant_chance_wepon * 2) {
/* 159 */         randomELevel = 0;
/* 160 */         isEnchant1 = true;
/*     */       } else {
/* 162 */         isEnchant = false;
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     if (randomELevel <= 0 && enchant_level > -6) {
/* 167 */       isEnchant = true;
/*     */     }
/*     */     
/* 170 */     if (isEnchant) {
/*     */ 
/*     */       
/* 173 */       RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "強化成功", pc.getIp());
/*     */ 
/*  36 */     StringBuilder s = new StringBuilder();
/*  53 */     if (item.getEnchantLevel() > 0) {
/*     */     }
/*  56 */     s.append("+" + tgItem.getEnchantLevel() + " " + tgItem.getName());
/*     */       
/* 177 */       pc.setoldAllName(tgItem.getAllName());
/*     */       
/* 179 */       enchantWeapon.successEnchant(pc, tgItem, randomELevel);
/* 180 */       if (Configtype.weaponbroadtrue == 1 && !isEnchant1 && 
/* 181 */         tgItem.getEnchantLevel() - tgItem.getItem().get_safeenchant() >= Configtype.weapon_savebroad) {
/* 182 */         if (isEnchant1) {
/* 183 */           isEnchant1 = false;
/*     */         }
/* 185 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg1_true, new Object[] { pc.getName(), pc.getoldAllName(), s.toString() })));

/* 187 */         pc.setoldAllName(null);
/*     */       } 
/*     */       
/* 190 */       tgItem.setproctect(false);
/* 191 */       tgItem.setproctect1(false);
/* 192 */       tgItem.setproctect2(false);
/* 193 */       tgItem.setproctect3(false);
/* 194 */       tgItem.setproctect4(false);
/* 195 */       tgItem.setproctect5(false);
/* 196 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 197 */       pc.getInventory().saveItem(tgItem, 4);
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
/*     */ 
/*     */       
/* 210 */       pc.setcheck_lv(false);
/*     */     }
/* 212 */     else if (tgItem.getproctect()) {
/* 213 */       tgItem.setproctect(false);
/* 214 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 215 */       pc.getInventory().updateItem(tgItem, 4);
/* 216 */       pc.getInventory().saveItem(tgItem, 4);
/* 217 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
/* 218 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到高級卷的保護", pc.getIp());
/*     */     }
/* 220 */     else if (tgItem.getproctect1()) {
/* 221 */       tgItem.setproctect1(false);
/* 222 */       tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/*     */       
/* 224 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 225 */       pc.getInventory().updateItem(tgItem, 4);
/* 226 */       pc.getInventory().saveItem(tgItem, 4);
/* 227 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
/* 228 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到中級卷的保護", pc.getIp());
/* 229 */     } else if (tgItem.getproctect2()) {
/* 230 */       tgItem.setproctect2(false);
/* 231 */       tgItem.setEnchantLevel(0);
/*     */       
/* 233 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 234 */       pc.getInventory().updateItem(tgItem, 4);
/* 235 */       pc.getInventory().saveItem(tgItem, 4);
/* 236 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
/* 237 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到低級卷的保護", pc.getIp());
/*     */     }
/* 239 */     else if (tgItem.getproctect3()) {
/* 240 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 241 */         tgItem.setproctect3(false);
/* 242 */         pc.setproctctran(0);
/* 243 */         tgItem.setEnchantLevel(0);
/* 244 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 245 */         pc.getInventory().updateItem(tgItem, 4);
/* 246 */         pc.getInventory().saveItem(tgItem, 4);
/* 247 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
/* 248 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率初卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 251 */         pc.setproctctran(0);
/* 252 */         enchantWeapon.failureEnchant(pc, tgItem);
/*     */       } 
/* 254 */     } else if (tgItem.getproctect4()) {
/* 255 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 256 */         tgItem.setproctect4(false);
/* 257 */         pc.setproctctran(0);
/* 258 */         tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 259 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 260 */         pc.getInventory().updateItem(tgItem, 4);
/* 261 */         pc.getInventory().saveItem(tgItem, 4);
/* 262 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
/* 263 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率中卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 266 */         pc.setproctctran(0);
/* 267 */         enchantWeapon.failureEnchant(pc, tgItem);
/*     */       } 
/* 269 */     } else if (tgItem.getproctect5()) {
/* 270 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 271 */         tgItem.setproctect5(false);
/* 272 */         pc.setproctctran(0);
/* 273 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 274 */         pc.getInventory().updateItem(tgItem, 4);
/* 275 */         pc.getInventory().saveItem(tgItem, 4);
/* 276 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
/* 277 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率高卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 280 */         pc.setproctctran(0);
/* 281 */         enchantWeapon.failureEnchant(pc, tgItem);
/*     */       } 
/*     */     } else {
/* 284 */       enchantWeapon.failureEnchant(pc, tgItem);
/* 285 */       RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "強化失敗", pc.getIp());
/* 286 */       if (!tgItem.getproctect() || !tgItem.getproctect1() || !tgItem.getproctect2())
/*     */       {
/* 288 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "未受保護爆了", pc.getIp());
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\reel\ScrollEnchantWeapon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */