/*     */ package com.lineage.data.item_etcitem.reel;
/*     */ 
/*     */ import com.lineage.config.Configtype;
/*     */ import com.lineage.data.cmd.EnchantArmor;
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
/*     */ 
/*     */ public class ScrollEnchantArmor
/*     */   extends ItemExecutor
/*     */ {
/*  34 */   final Random random = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemExecutor get() {
/*  44 */     return new ScrollEnchantArmor();
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
/*  56 */     int targObjId = data[0];
/*     */     
/*  58 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */     
/*  60 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  63 */     if (tgItem.isEquipped()) {
/*  64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
/*     */       return;
/*     */     } 
/*  67 */     if (ItemNoENTable.RESTRICTIONS.contains(Integer.valueOf(tgItem.getItemId()))) {
/*  68 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品禁止強化。"));
/*     */       
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
/*  72 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*     */     
/*  74 */     boolean isErr = false;
/*     */ 
/*     */     
/*  77 */     int use_type = tgItem.getItem().getUseType();
/*  78 */     switch (use_type) {
/*     */       case 2:
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 25:
/*     */       case 47:
/*  87 */         if (safe_enchant < 0) {
/*  88 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  93 */         isErr = true;
/*     */         break;
/*     */     } 
/*     */     
/*  97 */     int armorId = tgItem.getItem().getItemId();
/*  98 */     if (armorId >= 401004 && armorId <= 401007) {
/*  99 */       isErr = true;
/*     */     }
/*     */     
/* 102 */     if (armorId >= 120444 && armorId <= 120448) {
/* 103 */       isErr = true;
/*     */     }
/* 105 */     if (armorId >= 120477 && armorId <= 120479) {
/* 106 */       isErr = true;
/*     */     }
/* 108 */     if (tgItem.getBless() >= 128) {
/* 109 */       isErr = true;
/*     */     }
/*     */     
/* 112 */     if (isErr) {
/* 113 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/* 117 */     if (tgItem.getEnchantLevel() == Configtype.armorlv) {
/* 118 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("您的防具已達上限值。"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 123 */     int enchant_level = tgItem.getEnchantLevel();
/* 124 */     EnchantArmor enchantArmor = new EnchantArmor();
/* 125 */     int randomELevel = enchantArmor.randomELevel(tgItem, item.getBless());
/* 126 */     pc.getInventory().removeItem(item, 1L);
/*     */     
/* 128 */     boolean isEnchant = true;
/* 129 */     boolean isEnchant1 = true;
/* 130 */     if (enchant_level < -6) {
/* 131 */       isEnchant = false;
/*     */     }
/* 133 */     else if (enchant_level < safe_enchant) {
/* 134 */       isEnchant = true;
/*     */     } else {
/*     */       int enchant_chance_armor, enchant_level_tmp;
/*     */       
/* 138 */       Random random = new Random();
/* 139 */       int rnd = random.nextInt(100) + 1;
/*     */ 
/*     */ 
/*     */       
/* 143 */       if (safe_enchant == 0) {
/* 144 */         enchant_level_tmp = enchant_level + 2;
/*     */       } else {
/*     */         
/* 147 */         enchant_level_tmp = enchant_level;
/*     */       } 
/*     */       
/* 150 */       if (enchant_level >= 9) {
/* 151 */         enchant_chance_armor = (int)L1ItemUpdata.enchant_armor_up9(enchant_level_tmp);
/*     */       } else {
/*     */         
/* 154 */         enchant_chance_armor = (int)L1ItemUpdata.enchant_armor_dn9(enchant_level_tmp);
/*     */       } 
/*     */       
/* 157 */       if (item.getItemId() == 44065) {
/* 158 */         enchant_chance_armor *= 2;
/*     */       }
/*     */       
/* 161 */       if (rnd < enchant_chance_armor) {
/* 162 */         isEnchant = true;
/*     */       
/*     */       }
/* 165 */       else if (enchant_level >= 9 && rnd < enchant_chance_armor * 2) {
/* 166 */         randomELevel = 0;
/* 167 */         isEnchant1 = true;
/*     */       } else {
/* 169 */         isEnchant = false;
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (randomELevel <= 0 && enchant_level > -6) {
/* 174 */       isEnchant = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     if (isEnchant) {
/*     */ 
/*     */ 
/* 173 */       RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "強化成功", pc.getIp());
/*     */       
/*  36 */     StringBuilder s = new StringBuilder();
/*  53 */     if (item.getEnchantLevel() > 0) {
/*     */     }
/*  56 */     s.append("+" + item.getEnchantLevel() + " " + item.getName());

/* 186 */       pc.setoldAllName(tgItem.getAllName());
/*     */       
/* 188 */       enchantArmor.successEnchant(pc, tgItem, randomELevel);
/* 189 */       if (Configtype.armorbroadtrue == 1 && !isEnchant1 && 
/* 190 */         tgItem.getEnchantLevel() - tgItem.getItem().get_safeenchant() >= Configtype.armor_savebroad) {
/* 191 */         if (isEnchant1) {
/* 192 */           isEnchant1 = false;
/*     */         }
/* 194 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg1_true, new Object[] { pc.getName(), pc.getoldAllName(), s.toString() })));

/* 196 */         pc.setoldAllName(null);
/*     */       } 
/*     */       
/* 199 */       tgItem.setproctect(false);
/* 200 */       tgItem.setproctect1(false);
/* 201 */       tgItem.setproctect2(false);
/*     */       
/* 203 */       tgItem.setproctect3(false);
/* 204 */       tgItem.setproctect4(false);
/* 205 */       tgItem.setproctect5(false);
/*     */ 
/*     */       
/* 208 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 209 */       pc.getInventory().saveItem(tgItem, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       pc.setcheck_lv(false);
/*     */     }
/* 216 */     else if (tgItem.getproctect()) {
/* 217 */       tgItem.setproctect(false);
/* 218 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 219 */       pc.getInventory().updateItem(tgItem, 4);
/* 220 */       pc.getInventory().saveItem(tgItem, 4);
/* 221 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到高級卷的保護", pc.getIp());
/* 222 */     } else if (tgItem.getproctect1()) {
/* 223 */       tgItem.setproctect1(false);
/* 224 */       tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 225 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 226 */       pc.getInventory().updateItem(tgItem, 4);
/* 227 */       pc.getInventory().saveItem(tgItem, 4);
/* 228 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
/* 229 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到中級卷的保護", pc.getIp());
/*     */     }
/* 231 */     else if (tgItem.getproctect2()) {
/* 232 */       tgItem.setproctect2(false);
/* 233 */       tgItem.setEnchantLevel(0);
/* 234 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 235 */       pc.getInventory().updateItem(tgItem, 4);
/* 236 */       pc.getInventory().saveItem(tgItem, 4);
/* 237 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
/* 238 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到低級卷的保護", pc.getIp());
/*     */     
/*     */     }
/* 241 */     else if (tgItem.getproctect3()) {
/* 242 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 243 */         tgItem.setproctect3(false);
/* 244 */         pc.setproctctran(0);
/* 245 */         tgItem.setEnchantLevel(0);
/* 246 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 247 */         pc.getInventory().updateItem(tgItem, 4);
/* 248 */         pc.getInventory().saveItem(tgItem, 4);
/* 249 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
/* 250 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率初卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 253 */         pc.setproctctran(0);
/* 254 */         enchantArmor.failureEnchant(pc, tgItem);
/*     */       } 
/* 256 */     } else if (tgItem.getproctect4()) {
/* 257 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 258 */         tgItem.setproctect4(false);
/* 259 */         pc.setproctctran(0);
/* 260 */         tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 261 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 262 */         pc.getInventory().updateItem(tgItem, 4);
/* 263 */         pc.getInventory().saveItem(tgItem, 4);
/* 264 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
/* 265 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率中卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 268 */         pc.setproctctran(0);
/* 269 */         enchantArmor.failureEnchant(pc, tgItem);
/*     */       } 
/* 271 */     } else if (tgItem.getproctect5()) {
/* 272 */       if (this.random.nextInt(100) + 1 <= pc.getproctctran()) {
/* 273 */         tgItem.setproctect5(false);
/* 274 */         pc.setproctctran(0);
/* 275 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 276 */         pc.getInventory().updateItem(tgItem, 4);
/* 277 */         pc.getInventory().saveItem(tgItem, 4);
/* 278 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
/* 279 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率高卷的保護", pc.getIp());
/*     */       } else {
/*     */         
/* 282 */         pc.setproctctran(0);
/* 283 */         enchantArmor.failureEnchant(pc, tgItem);
/*     */       } 
/*     */     } else {
/* 286 */       enchantArmor.failureEnchant(pc, tgItem);
/* 287 */       RecordTable.get().recordFailureArmor(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "強化失敗", pc.getIp());
/* 288 */       if (!tgItem.getproctect() || !tgItem.getproctect1() || !tgItem.getproctect2())
/*     */       {
/* 290 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "未受保護爆了", pc.getIp());
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\reel\ScrollEnchantArmor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */