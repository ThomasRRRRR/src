/*     */ package com.lineage.data.item_etcitem.reel;
/*     */ 
/*     */ import com.lineage.config.ConfigAccessory;
import com.lineage.config.Configtype;
/*     */ import com.lineage.data.cmd.EnchantAccessory_Acc;
/*     */ import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.ItemMaxENTable;
/*     */ import com.lineage.server.datatables.ItemNoENTable;
import com.lineage.server.datatables.RecordTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.world.World;

/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScrollEnchantAccessory
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  39 */     return new ScrollEnchantAccessory();
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
/*  51 */     int targObjId = data[0];
/*     */ 
/*     */     
/*  54 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*     */     
/*  56 */
/*  56 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*  59 */     if (tgItem.isEquipped()) {
/*  60 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法強化"));
/*     */       return;
/*     */     } 
/*  63 */     if (ItemNoENTable.RESTRICTIONS.contains(Integer.valueOf(tgItem.getItemId()))) {
/*  64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品禁止強化。"));
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
/*  68 */     int safe_enchant = tgItem.getItem().get_safeenchant();
/*     */     
/*  70 */     boolean isErr = false;
/*     */ 
/*     */     
/*  73 */     int use_type = tgItem.getItem().getUseType();
/*  74 */     switch (use_type) {
/*     */       case 23:
/*     */       case 24:
/*     */       case 37:
/*     */       case 40:
/*  79 */         if (tgItem.getItem().get_greater() == 3) {
/*  80 */           isErr = true;
/*     */         }
/*  82 */         if (safe_enchant < 0) {
/*  83 */           isErr = true;
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/*  88 */         isErr = true;
/*     */         break;
/*     */     } 
/*     */     
/*  92 */     if (tgItem.getBless() >= 128) {
/*  93 */       isErr = true;
/*     */     }
/*     */     
/*  96 */     if (isErr) {
/*  97 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 102 */     int enchant_level = tgItem.getEnchantLevel();
/*     */ 
/*     */     
/* 105 */     if (enchant_level == 9) {
/* 106 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/* 110 */     EnchantAccessory_Acc enchantAccessory_Acc = new EnchantAccessory_Acc();
/* 111 */     int randomELevel = enchantAccessory_Acc.randomELevel(tgItem, item.getBless());
/* 112 */     pc.getInventory().removeItem(item, 1L);
/*     */     
/* 114 */     Random random = new Random();
/* 115 */     int lv = 0;
/* 139 */       int rnd = random.nextInt(100) + 1;
/* 116 */     if (tgItem.getEnchantLevel() == 0) {
/* 117 */       lv = ConfigAccessory.ran1;
/* 118 */     } else if (tgItem.getEnchantLevel() == 1) {
/* 119 */       lv = ConfigAccessory.ran2;
/* 120 */     } else if (tgItem.getEnchantLevel() == 2) {
/* 121 */       lv = ConfigAccessory.ran3;
/* 122 */     } else if (tgItem.getEnchantLevel() == 3) {
/* 123 */       lv = ConfigAccessory.ran4;
/* 124 */     } else if (tgItem.getEnchantLevel() == 4) {
/* 125 */       lv = ConfigAccessory.ran5;
/* 126 */     } else if (tgItem.getEnchantLevel() == 5) {
/* 127 */       lv = ConfigAccessory.ran6;
/* 128 */     } else if (tgItem.getEnchantLevel() == 6) {
/* 129 */       lv = ConfigAccessory.ran7;
/* 130 */     } else if (tgItem.getEnchantLevel() == 7) {
/* 131 */       lv = ConfigAccessory.ran8;
/* 132 */     } else if (tgItem.getEnchantLevel() == 8) {
/* 133 */       lv = ConfigAccessory.ran9;
/*     */     } 
/* 135 */     if (random.nextInt(100) <= lv) {
/* 136 */       enchantAccessory_Acc.successEnchant(pc, tgItem, randomELevel);
/* 137 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg1, new Object[] { tgItem.getName() })));
/* 138 */       飾品強化成功記錄表("IP(" + 
/* 139 */           pc.getNetConnection().getIp() + ")" + 
/* 140 */           "玩家" + 
/* 141 */           ":【 " + pc.getName() + " 】 " + 
/* 142 */           "的" + 
/* 143 */           "【  " + tgItem.getEnchantLevel() + 
/* 144 */           " " + tgItem.getName() + 
/* 145 */           "】- (強化成功)" + 
/* 146 */           "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
/*     */     } else {
/*     */       
	
	
	
	
	
	

//		if (tgItem.getproctect()) {
//			/* 217 */       tgItem.setproctect(false);
//			/* 218 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 219 */       pc.getInventory().updateItem(tgItem, 4);
//			/* 220 */       pc.getInventory().saveItem(tgItem, 4);
//			/* 278 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
//			/* 221 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到高級卷的保護", pc.getIp());
//			/* 222 */     }else if (tgItem.getproctect1()) {
//			/* 223 */       tgItem.setproctect1(false);
//			/* 224 */       tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
//			/* 225 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 226 */       pc.getInventory().updateItem(tgItem, 4);
//			/* 227 */       pc.getInventory().saveItem(tgItem, 4);
//			/* 228 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
//			/* 229 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到中級卷的保護", pc.getIp());
//			/*     */     }
//			/* 231 */     else if (tgItem.getproctect2()) {
//			/* 232 */       tgItem.setproctect2(false);
//			/* 233 */       tgItem.setEnchantLevel(0);
//			/* 234 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 235 */       pc.getInventory().updateItem(tgItem, 4);
//			/* 236 */       pc.getInventory().saveItem(tgItem, 4);
//			/* 237 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
//			/* 238 */       RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到低級卷的保護", pc.getIp());
//			/*     */     
//			/*     */     }
//			/* 241 */     else if (tgItem.getproctect3()) {
//			/* 242 */       if (random.nextInt(100) + 1 <= pc.getproctctran()) {
//			/* 243 */         tgItem.setproctect3(false);
//			/* 244 */         pc.setproctctran(0);
//			/* 245 */         tgItem.setEnchantLevel(0);
//			/* 246 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 247 */         pc.getInventory().updateItem(tgItem, 4);
//			/* 248 */         pc.getInventory().saveItem(tgItem, 4);
//			/* 249 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到初級裝備保護卷軸的影響,失敗後物品歸0。"));
//			/* 250 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率初卷的保護", pc.getIp());
//			/*     */       } else {
//			/*     */         
//			/* 253 */         pc.setproctctran(0);
//			/* 254 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
//			/*     */       } 
//			/* 256 */     } else if (tgItem.getproctect4()) {
//			/* 257 */       if (random.nextInt(100) + 1 <= pc.getproctctran()) {
//			/* 258 */         tgItem.setproctect4(false);
//			/* 259 */         pc.setproctctran(0);
//			/* 260 */         tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
//			/* 261 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 262 */         pc.getInventory().updateItem(tgItem, 4);
//			/* 263 */         pc.getInventory().saveItem(tgItem, 4);
//			/* 264 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到中級裝備保護卷軸的影響,失敗後物品倒扣1。"));
//			/* 265 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率中卷的保護", pc.getIp());
//			/*     */       } else {
//			/*     */         
//			/* 268 */         pc.setproctctran(0);
//			/* 269 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
//			/*     */       } 
//			/* 271 */     } else if (tgItem.getproctect5()) {
//			/* 272 */       if (random.nextInt(100) + 1 <= pc.getproctctran()) {
//			/* 273 */         tgItem.setproctect5(false);
//			/* 274 */         pc.setproctctran(0);
//			/* 275 */         pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
//			/* 276 */         pc.getInventory().updateItem(tgItem, 4);
//			/* 277 */         pc.getInventory().saveItem(tgItem, 4);
//			/* 278 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("受到高級裝備保護卷軸的影響,失敗後物品無變化。"));
//			/* 279 */         RecordTable.get().recordFailureArmor1(pc.getName(), item.getAllName(), tgItem.getAllName(), item.getId(), "受到機率高卷的保護", pc.getIp());
//			/*     */       } else {
//				/* 282 */         pc.setproctctran(0);
//				/* 283 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
//				/*     */       } 
//				/*     */     }
				
				
/* 149 */       if (tgItem.getEnchantLevel() == 0 && ConfigAccessory.removeitem1 == 1) {
/* 150 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 151 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 152 */       } else if (tgItem.getEnchantLevel() == 0 && ConfigAccessory.removeitem1 == 2) {
/* 153 */         if (tgItem.getEnchantLevel() > 0) {
/* 154 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 155 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 156 */           pc.getInventory().updateItem(tgItem, 4);
/* 157 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 159 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 162 */       else if (tgItem.getEnchantLevel() == 1 && ConfigAccessory.removeitem2 == 1) {
/* 163 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 164 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 165 */       } else if (tgItem.getEnchantLevel() == 1 && ConfigAccessory.removeitem2 == 2) {
/* 166 */         if (tgItem.getEnchantLevel() > 0) {
/* 167 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 168 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 169 */           pc.getInventory().updateItem(tgItem, 4);
/* 170 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 172 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 175 */       else if (tgItem.getEnchantLevel() == 2 && ConfigAccessory.removeitem3 == 1) {
/* 176 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 177 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 178 */       } else if (tgItem.getEnchantLevel() == 2 && ConfigAccessory.removeitem3 == 2) {
/* 179 */         if (tgItem.getEnchantLevel() > 0) {
/* 180 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 181 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 182 */           pc.getInventory().updateItem(tgItem, 4);
/* 183 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 185 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 188 */       else if (tgItem.getEnchantLevel() == 3 && ConfigAccessory.removeitem4 == 1) {
/* 189 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 190 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 191 */       } else if (tgItem.getEnchantLevel() == 3 && ConfigAccessory.removeitem4 == 2) {
/* 192 */         if (tgItem.getEnchantLevel() > 0) {
/* 193 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 194 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 195 */           pc.getInventory().updateItem(tgItem, 4);
/* 196 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 198 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 201 */       else if (tgItem.getEnchantLevel() == 4 && ConfigAccessory.removeitem5 == 1) {
/* 202 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 203 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 204 */       } else if (tgItem.getEnchantLevel() == 4 && ConfigAccessory.removeitem5 == 2) {
/* 205 */         if (tgItem.getEnchantLevel() > 0) {
/* 206 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 207 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 208 */           pc.getInventory().updateItem(tgItem, 4);
/* 209 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 211 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 214 */       else if (tgItem.getEnchantLevel() == 5 && ConfigAccessory.removeitem6 == 1) {
/* 215 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 216 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 217 */       } else if (tgItem.getEnchantLevel() == 5 && ConfigAccessory.removeitem6 == 2) {
/* 218 */         if (tgItem.getEnchantLevel() > 0) {
/* 219 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 220 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 221 */           pc.getInventory().updateItem(tgItem, 4);
/* 222 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 224 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 227 */       else if (tgItem.getEnchantLevel() == 6 && ConfigAccessory.removeitem7 == 1) {
/* 228 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 229 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 230 */       } else if (tgItem.getEnchantLevel() == 6 && ConfigAccessory.removeitem7 == 2) {
/* 231 */         if (tgItem.getEnchantLevel() > 0) {
/* 232 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 233 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 234 */           pc.getInventory().updateItem(tgItem, 4);
/* 235 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 237 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 240 */       else if (tgItem.getEnchantLevel() == 7 && ConfigAccessory.removeitem8 == 1) {
/* 241 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 242 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 243 */       } else if (tgItem.getEnchantLevel() == 7 && ConfigAccessory.removeitem8 == 2) {
/* 244 */         if (tgItem.getEnchantLevel() > 0) {
/* 245 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 246 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 247 */           pc.getInventory().updateItem(tgItem, 4);
/* 248 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 250 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */       
/* 253 */       else if (tgItem.getEnchantLevel() == 8 && ConfigAccessory.removeitem9 == 1) {
/* 254 */         enchantAccessory_Acc.failureEnchant(pc, tgItem);
/* 255 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg2, new Object[] { tgItem.getName() })));
/* 256 */       } else if (tgItem.getEnchantLevel() == 8 && ConfigAccessory.removeitem9 == 2) {
/* 257 */         if (tgItem.getEnchantLevel() > 0) {
/* 258 */           tgItem.setEnchantLevel(tgItem.getEnchantLevel() - 1);
/* 259 */           pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/* 260 */           pc.getInventory().updateItem(tgItem, 4);
/* 261 */           pc.getInventory().saveItem(tgItem, 4);
/*     */         } 
/* 263 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.format(ConfigAccessory.msg3, new Object[] { tgItem.getName() })));
/*     */       } 
/*     */ 
/*     */       
/* 267 */       飾品強化失敗記錄表("IP(" + 
/* 268 */           pc.getNetConnection().getIp() + ")" + 
/* 269 */           "玩家" + 
/* 270 */           ":【 " + pc.getName() + " 】 " + 
/* 271 */           "的" + 
/* 272 */           "【  " + tgItem.getEnchantLevel() + 
/* 273 */           " " + tgItem.getName() + 
/* 274 */           "】- (爆了)" + 
/* 275 */           "時間:" + "(" + new Timestamp(System.currentTimeMillis()) + ")。");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void 飾品強化失敗記錄表(String info) {
/*     */     try {
/* 282 */       BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[飾品強化[失敗]記錄表].txt", true));
/* 283 */       out.write(String.valueOf(info) + "\r\n");
/* 284 */       out.close();
/* 285 */     } catch (IOException e) {
/* 286 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void 飾品強化成功記錄表(String info) {
/*     */     try {
/* 291 */       BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[飾品強化[成功]記錄表].txt", true));
/* 292 */       out.write(String.valueOf(info) + "\r\n");
/* 293 */       out.close();
/* 294 */     } catch (IOException e) {
/* 295 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\reel\ScrollEnchantAccessory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */