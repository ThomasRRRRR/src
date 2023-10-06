/*     */ package com.lineage.data.npc.shop;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.data.executor.NpcExecutor;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.lock.DwarfForVIPReading;
///*     */ import com.lineage.server.datatables.sql.DwarfForVIPTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
import com.lineage.william.GiveBack;

import java.util.Iterator;
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
/*     */ public class Npc_GiveBack
/*     */   extends NpcExecutor
/*     */ {
/*     */   public static NpcExecutor get() {
/*  36 */     return new Npc_GiveBack();
/*     */   }
/*     */   
/*     */   public int type() {
/*  40 */     return 3;
/*     */   }
public void talk(L1PcInstance pc, L1NpcInstance npc) {
    try {
        String str1 = null;
        int count = 0;
        if (GiveBack.savepcid != null && GiveBack.savename != null) {
            for (int i = 0; i < GiveBack.savepcid.size(); i++) {
                if (GiveBack.savepcid.get(i) != null && GiveBack.savename.get(i) != null &&
                        GiveBack.savepcid.get(i).intValue() == pc.getId()) {
                    count++;
                }
            }
        }
        pc.set_backpage(1);
        String msg0 = " ";
        String msg1 = " ";
        String msg2 = " ";
        String msg3 = " ";
        int count2 = 0;
        if (GiveBack.savepcid != null && GiveBack.savename != null) {
            for (int j = 0; j < GiveBack.savepcid.size(); j++) {
                if (GiveBack.savepcid.get(j) != null && GiveBack.savename.get(j) != null &&
                        GiveBack.savepcid.get(j).intValue() == pc.getId()) {
                    count2++;
                    if (pc.get_backpage() == count2) {
                        msg0 = GiveBack.savename.get(j);
                        break;
                    }
                }
            }
        }
        int k = pc.get_backpage();
        int m = count;
        String[] price = ConfigOther.weapon_Item_Price6 != null ? ConfigOther.weapon_Item_Price6.split(",") : new String[0];
        if (price.length >= 2) {
            L1Item temper = ItemTable.get().getTemplate(Integer.valueOf(price[0]).intValue());
            if (temper != null) {
                msg3 = String.valueOf(price[1]) + "個" + temper.getName();
            }
        }
        if (count == 0) {
            str1 = "0";
        }
        String[] msg = { msg0, str1, String.valueOf(m), msg3 };
        if (count > 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "giveback", msg));
        }
        if (count == 0) {
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "nogiveback", msg));
        }
    } catch (Exception exception) {
        // 處理潛在的異常，例如打印堆棧信息或其他錯誤處理邏輯
        exception.printStackTrace();
    }
}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
/*  92 */     if (cmd.equals("accept")) {
/*  93 */       int count = 0;
/*  94 */       for (int i = 0; i < GiveBack.savepcid.size(); i++) {
/*     */         
/*  96 */         count++;
/*  97 */         if (((Integer)GiveBack.savepcid.get(i)).intValue() == pc.getId() && count == pc.get_backpage()) {
/*     */           
/*  99 */           String[] weapon_price6 = ConfigOther.weapon_Item_Price6.split(",");
/* 100 */           int weapon_itemids6 = Integer.valueOf(weapon_price6[0]).intValue();
/* 101 */           int weapon_counts6 = Integer.valueOf(weapon_price6[1]).intValue();
/*     */           
/* 103 */           String[] weapon_price7 = ConfigOther.weapon_Item_Price7.split(",");
/* 104 */           int weapon_itemids7 = Integer.valueOf(weapon_price7[0]).intValue();
/* 105 */           int weapon_counts7 = Integer.valueOf(weapon_price7[1]).intValue();
/*     */           
/* 107 */           String[] weapon_price8 = ConfigOther.weapon_Item_Price8.split(",");
/* 108 */           int weapon_itemids8 = Integer.valueOf(weapon_price8[0]).intValue();
/* 109 */           int weapon_counts8 = Integer.valueOf(weapon_price8[1]).intValue();
/*     */           
/* 111 */           String[] weapon_price9 = ConfigOther.weapon_Item_Price9.split(",");
/* 112 */           int weapon_itemids9 = Integer.valueOf(weapon_price9[0]).intValue();
/* 113 */           int weapon_counts9 = Integer.valueOf(weapon_price9[1]).intValue();
/*     */           
/* 115 */           String[] weapon_price10 = ConfigOther.weapon_Item_Price10.split(",");
/* 116 */           int weapon_itemids10 = Integer.valueOf(weapon_price10[0]).intValue();
/* 117 */           int weapon_counts10 = Integer.valueOf(weapon_price10[1]).intValue();
/*     */ 
/*     */ 
/*     */           
/* 121 */           String[] armor_price4 = ConfigOther.armor_Item_Price4.split(",");
/* 122 */           int armor_itemids4 = Integer.valueOf(armor_price4[0]).intValue();
/* 123 */           int armor_counts4 = Integer.valueOf(armor_price4[1]).intValue();
/*     */           
/* 125 */           String[] armor_price5 = ConfigOther.armor_Item_Price5.split(",");
/* 126 */           int armor_itemids5 = Integer.valueOf(armor_price5[0]).intValue();
/* 127 */           int armor_counts5 = Integer.valueOf(armor_price5[1]).intValue();
/*     */           
/* 129 */           String[] armor_price6 = ConfigOther.armor_Item_Price6.split(",");
/* 130 */           int armor_itemids6 = Integer.valueOf(armor_price6[0]).intValue();
/* 131 */           int armor_counts6 = Integer.valueOf(armor_price6[1]).intValue();
/*     */           
/* 133 */           String[] armor_price7 = ConfigOther.armor_Item_Price7.split(",");
/* 134 */           int armor_itemids7 = Integer.valueOf(armor_price7[0]).intValue();
/* 135 */           int armor_counts7 = Integer.valueOf(armor_price7[1]).intValue();
/*     */           
/* 137 */           String[] armor_price8 = ConfigOther.armor_Item_Price8.split(",");
/* 138 */           int armor_itemids8 = Integer.valueOf(armor_price8[0]).intValue();
/* 139 */           int armor_counts8 = Integer.valueOf(armor_price8[1]).intValue();
/*     */           
/* 141 */           String[] armor_price9 = ConfigOther.armor_Item_Price9.split(",");
/* 142 */           int armor_itemids9 = Integer.valueOf(armor_price9[0]).intValue();
/* 143 */           int armor_counts9 = Integer.valueOf(armor_price9[1]).intValue();
/*     */           
/* 145 */           String[] armor_price10 = ConfigOther.armor_Item_Price10.split(",");
/* 146 */           int armor_itemids10 = Integer.valueOf(armor_price10[0]).intValue();
/* 147 */           int armor_counts10 = Integer.valueOf(armor_price10[1]).intValue();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 152 */           L1ItemInstance olditem_weapon = GiveBack.saveweapon.get(i);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 157 */           if (olditem_weapon.getItem().getType2() == 1) {
/* 158 */             if (olditem_weapon.getEnchantLevel() == 6) {
/* 159 */               if (pc.getInventory().checkItem(weapon_itemids6, weapon_counts6)) {
/* 160 */                 pc.getInventory().consumeItem(weapon_itemids6, weapon_counts6);
/* 161 */                 int count2 = 0;
/* 162 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 163 */                 while (iter.hasNext()) {
/* 164 */                   count2++;
/* 165 */                   int s = ((Integer)iter.next()).intValue();
/* 166 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 167 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 171 */                 int count3 = 0;
/* 172 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 173 */                 while (iter2.hasNext()) {
/* 174 */                   count3++;
/* 175 */                   L1ItemInstance s = iter2.next();
/* 176 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 177 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 181 */                 int count4 = 0;
/* 182 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 183 */                 while (iter3.hasNext()) {
/* 184 */                   count4++;
/* 185 */                   String s = iter3.next();
/* 186 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 187 */                     count4 == pc.get_backpage()) {
/* 188 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 193 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 194 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 195 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 196 */                 pc.set_backpage(1);
/* 197 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 199 */                 L1Item temper = ItemTable.get().getTemplate(weapon_itemids6);
/* 200 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + weapon_counts6));
/* 201 */                 pc.set_backpage(1);
/* 202 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 206 */             if (olditem_weapon.getEnchantLevel() == 7) {
/* 207 */               if (pc.getInventory().checkItem(weapon_itemids7, weapon_counts7)) {
/* 208 */                 pc.getInventory().consumeItem(weapon_itemids7, weapon_counts7);
/* 209 */                 int count2 = 0;
/* 210 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 211 */                 while (iter.hasNext()) {
/* 212 */                   count2++;
/* 213 */                   int s = ((Integer)iter.next()).intValue();
/* 214 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 215 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 219 */                 int count3 = 0;
/* 220 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 221 */                 while (iter2.hasNext()) {
/* 222 */                   count3++;
/* 223 */                   L1ItemInstance s = iter2.next();
/* 224 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 225 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 229 */                 int count4 = 0;
/* 230 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 231 */                 while (iter3.hasNext()) {
/* 232 */                   count4++;
/* 233 */                   String s = iter3.next();
/* 234 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 235 */                     count4 == pc.get_backpage()) {
/* 236 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 241 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 242 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 243 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 244 */                 pc.set_backpage(1);
/* 245 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 247 */                 L1Item temper = ItemTable.get().getTemplate(weapon_itemids7);
/* 248 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + weapon_counts7));
/* 249 */                 pc.set_backpage(1);
/* 250 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 254 */             if (olditem_weapon.getEnchantLevel() == 8) {
/* 255 */               if (pc.getInventory().checkItem(weapon_itemids8, weapon_counts8)) {
/* 256 */                 pc.getInventory().consumeItem(weapon_itemids8, weapon_counts8);
/* 257 */                 int count2 = 0;
/* 258 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 259 */                 while (iter.hasNext()) {
/* 260 */                   count2++;
/* 261 */                   int s = ((Integer)iter.next()).intValue();
/* 262 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 263 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 267 */                 int count3 = 0;
/* 268 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 269 */                 while (iter2.hasNext()) {
/* 270 */                   count3++;
/* 271 */                   L1ItemInstance s = iter2.next();
/* 272 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 273 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 277 */                 int count4 = 0;
/* 278 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 279 */                 while (iter3.hasNext()) {
/* 280 */                   count4++;
/* 281 */                   String s = iter3.next();
/* 282 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 283 */                     count4 == pc.get_backpage()) {
/* 284 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 289 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 290 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 291 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 292 */                 pc.set_backpage(1);
/* 293 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 295 */                 L1Item temper = ItemTable.get().getTemplate(weapon_itemids8);
/* 296 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + weapon_counts8));
/* 297 */                 pc.set_backpage(1);
/* 298 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 302 */             if (olditem_weapon.getEnchantLevel() == 9) {
/* 303 */               if (pc.getInventory().checkItem(weapon_itemids9, weapon_counts9)) {
/* 304 */                 pc.getInventory().consumeItem(weapon_itemids9, weapon_counts9);
/* 305 */                 int count2 = 0;
/* 306 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 307 */                 while (iter.hasNext()) {
/* 308 */                   count2++;
/* 309 */                   int s = ((Integer)iter.next()).intValue();
/* 310 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 311 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 315 */                 int count3 = 0;
/* 316 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 317 */                 while (iter2.hasNext()) {
/* 318 */                   count3++;
/* 319 */                   L1ItemInstance s = iter2.next();
/* 320 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 321 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 325 */                 int count4 = 0;
/* 326 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 327 */                 while (iter3.hasNext()) {
/* 328 */                   count4++;
/* 329 */                   String s = iter3.next();
/* 330 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 331 */                     count4 == pc.get_backpage()) {
/* 332 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 337 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 338 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 339 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 340 */                 pc.set_backpage(1);
/* 341 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 343 */                 L1Item temper = ItemTable.get().getTemplate(weapon_itemids9);
/* 344 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + weapon_counts9));
/* 345 */                 pc.set_backpage(1);
/* 346 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 350 */             if (olditem_weapon.getEnchantLevel() == 10) {
/* 351 */               if (pc.getInventory().checkItem(weapon_itemids10, weapon_counts10)) {
/* 352 */                 pc.getInventory().consumeItem(weapon_itemids10, weapon_counts10);
/* 353 */                 int count2 = 0;
/* 354 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 355 */                 while (iter.hasNext()) {
/* 356 */                   count2++;
/* 357 */                   int s = ((Integer)iter.next()).intValue();
/* 358 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 359 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 363 */                 int count3 = 0;
/* 364 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 365 */                 while (iter2.hasNext()) {
/* 366 */                   count3++;
/* 367 */                   L1ItemInstance s = iter2.next();
/* 368 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 369 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 373 */                 int count4 = 0;
/* 374 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 375 */                 while (iter3.hasNext()) {
/* 376 */                   count4++;
/* 377 */                   String s = iter3.next();
/* 378 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 379 */                     count4 == pc.get_backpage()) {
/* 380 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 385 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 386 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 387 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 388 */                 pc.set_backpage(1);
/* 389 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 391 */                 L1Item temper = ItemTable.get().getTemplate(weapon_itemids10);
/* 392 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + weapon_counts10));
/* 393 */                 pc.set_backpage(1);
/* 394 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               }
/*     */             
/*     */             }
/* 398 */           } else if (olditem_weapon.getItem().getUseType() == 23) {
/* 399 */             if (olditem_weapon.getEnchantLevel() >= 0 && olditem_weapon.getEnchantLevel() <= 9) {
/* 400 */               if (pc.getInventory().checkItem(armor_itemids10, armor_counts10)) {
/* 401 */                 pc.getInventory().consumeItem(armor_itemids10, armor_counts10);
/* 402 */                 int count2 = 0;
/* 403 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 404 */                 while (iter.hasNext()) {
/* 405 */                   count2++;
/* 406 */                   int s = ((Integer)iter.next()).intValue();
/* 407 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 408 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 412 */                 int count3 = 0;
/* 413 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 414 */                 while (iter2.hasNext()) {
/* 415 */                   count3++;
/* 416 */                   L1ItemInstance s = iter2.next();
/* 417 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 418 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 422 */                 int count4 = 0;
/* 423 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 424 */                 while (iter3.hasNext()) {
/* 425 */                   count4++;
/* 426 */                   String s = iter3.next();
/* 427 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 428 */                     count4 == pc.get_backpage()) {
/* 429 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 434 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 435 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 436 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 437 */                 pc.set_backpage(1);
/* 438 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 440 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids10);
/* 441 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts10));
/* 442 */                 pc.set_backpage(1);
/* 443 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/* 446 */           } else if (olditem_weapon.getItem().getUseType() == 24) {
/* 447 */             if (olditem_weapon.getEnchantLevel() >= 0 && olditem_weapon.getEnchantLevel() <= 9) {
/* 448 */               if (pc.getInventory().checkItem(armor_itemids10, armor_counts10)) {
/* 449 */                 pc.getInventory().consumeItem(armor_itemids10, armor_counts10);
/* 450 */                 int count2 = 0;
/* 451 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 452 */                 while (iter.hasNext()) {
/* 453 */                   count2++;
/* 454 */                   int s = ((Integer)iter.next()).intValue();
/* 455 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 456 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 460 */                 int count3 = 0;
/* 461 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 462 */                 while (iter2.hasNext()) {
/* 463 */                   count3++;
/* 464 */                   L1ItemInstance s = iter2.next();
/* 465 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 466 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 470 */                 int count4 = 0;
/* 471 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 472 */                 while (iter3.hasNext()) {
/* 473 */                   count4++;
/* 474 */                   String s = iter3.next();
/* 475 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 476 */                     count4 == pc.get_backpage()) {
/* 477 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 482 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 483 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 484 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 485 */                 pc.set_backpage(1);
/* 486 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 488 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids10);
/* 489 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts10));
/* 490 */                 pc.set_backpage(1);
/* 491 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/* 494 */           } else if (olditem_weapon.getItem().getUseType() == 37) {
/* 495 */             if (olditem_weapon.getEnchantLevel() >= 0 && olditem_weapon.getEnchantLevel() <= 9) {
/* 496 */               if (pc.getInventory().checkItem(armor_itemids10, armor_counts10)) {
/* 497 */                 pc.getInventory().consumeItem(armor_itemids10, armor_counts10);
/* 498 */                 int count2 = 0;
/* 499 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 500 */                 while (iter.hasNext()) {
/* 501 */                   count2++;
/* 502 */                   int s = ((Integer)iter.next()).intValue();
/* 503 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 504 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 508 */                 int count3 = 0;
/* 509 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 510 */                 while (iter2.hasNext()) {
/* 511 */                   count3++;
/* 512 */                   L1ItemInstance s = iter2.next();
/* 513 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 514 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 518 */                 int count4 = 0;
/* 519 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 520 */                 while (iter3.hasNext()) {
/* 521 */                   count4++;
/* 522 */                   String s = iter3.next();
/* 523 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 524 */                     count4 == pc.get_backpage()) {
/* 525 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 530 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 531 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 532 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 533 */                 pc.set_backpage(1);
/* 534 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 536 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids10);
/* 537 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts10));
/* 538 */                 pc.set_backpage(1);
/* 539 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/* 542 */           } else if (olditem_weapon.getItem().getUseType() == 40) {
/* 543 */             if (olditem_weapon.getEnchantLevel() >= 0 && olditem_weapon.getEnchantLevel() <= 9) {
/* 544 */               if (pc.getInventory().checkItem(armor_itemids10, armor_counts10)) {
/* 545 */                 pc.getInventory().consumeItem(armor_itemids10, armor_counts10);
/* 546 */                 int count2 = 0;
/* 547 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 548 */                 while (iter.hasNext()) {
/* 549 */                   count2++;
/* 550 */                   int s = ((Integer)iter.next()).intValue();
/* 551 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 552 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 556 */                 int count3 = 0;
/* 557 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 558 */                 while (iter2.hasNext()) {
/* 559 */                   count3++;
/* 560 */                   L1ItemInstance s = iter2.next();
/* 561 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 562 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 566 */                 int count4 = 0;
/* 567 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 568 */                 while (iter3.hasNext()) {
/* 569 */                   count4++;
/* 570 */                   String s = iter3.next();
/* 571 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 572 */                     count4 == pc.get_backpage()) {
/* 573 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 578 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 579 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 580 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 581 */                 pc.set_backpage(1);
/* 582 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 584 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids10);
/* 585 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts10));
/* 586 */                 pc.set_backpage(1);
/* 587 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */           
/*     */           }
/* 597 */           else if (olditem_weapon.getItem().getType2() == 2) {
/* 598 */             if (olditem_weapon.getEnchantLevel() == 4) {
/* 599 */               if (pc.getInventory().checkItem(armor_itemids4, armor_counts4)) {
/* 600 */                 pc.getInventory().consumeItem(armor_itemids4, armor_counts4);
/* 601 */                 int count2 = 0;
/* 602 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 603 */                 while (iter.hasNext()) {
/* 604 */                   count2++;
/* 605 */                   int s = ((Integer)iter.next()).intValue();
/* 606 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 607 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 611 */                 int count3 = 0;
/* 612 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 613 */                 while (iter2.hasNext()) {
/* 614 */                   count3++;
/* 615 */                   L1ItemInstance s = iter2.next();
/* 616 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 617 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 621 */                 int count4 = 0;
/* 622 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 623 */                 while (iter3.hasNext()) {
/* 624 */                   count4++;
/* 625 */                   String s = iter3.next();
/* 626 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 627 */                     count4 == pc.get_backpage()) {
/* 628 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 633 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 634 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 635 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 636 */                 pc.set_backpage(1);
/* 637 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 639 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids4);
/* 640 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts4));
/* 641 */                 pc.set_backpage(1);
/* 642 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 646 */             if (olditem_weapon.getEnchantLevel() == 5) {
/* 647 */               if (pc.getInventory().checkItem(armor_itemids5, armor_counts5)) {
/* 648 */                 pc.getInventory().consumeItem(armor_itemids5, armor_counts5);
/* 649 */                 int count2 = 0;
/* 650 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 651 */                 while (iter.hasNext()) {
/* 652 */                   count2++;
/* 653 */                   int s = ((Integer)iter.next()).intValue();
/* 654 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 655 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 659 */                 int count3 = 0;
/* 660 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 661 */                 while (iter2.hasNext()) {
/* 662 */                   count3++;
/* 663 */                   L1ItemInstance s = iter2.next();
/* 664 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 665 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 669 */                 int count4 = 0;
/* 670 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 671 */                 while (iter3.hasNext()) {
/* 672 */                   count4++;
/* 673 */                   String s = iter3.next();
/* 674 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 675 */                     count4 == pc.get_backpage()) {
/* 676 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 681 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 682 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 683 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 684 */                 pc.set_backpage(1);
/* 685 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 687 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids5);
/* 688 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts5));
/* 689 */                 pc.set_backpage(1);
/* 690 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 694 */             if (olditem_weapon.getEnchantLevel() == 6) {
/* 695 */               if (pc.getInventory().checkItem(armor_itemids6, armor_counts6)) {
/* 696 */                 pc.getInventory().consumeItem(armor_itemids6, armor_counts6);
/* 697 */                 int count2 = 0;
/* 698 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 699 */                 while (iter.hasNext()) {
/* 700 */                   count2++;
/* 701 */                   int s = ((Integer)iter.next()).intValue();
/* 702 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 703 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 707 */                 int count3 = 0;
/* 708 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 709 */                 while (iter2.hasNext()) {
/* 710 */                   count3++;
/* 711 */                   L1ItemInstance s = iter2.next();
/* 712 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 713 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 717 */                 int count4 = 0;
/* 718 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 719 */                 while (iter3.hasNext()) {
/* 720 */                   count4++;
/* 721 */                   String s = iter3.next();
/* 722 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 723 */                     count4 == pc.get_backpage()) {
/* 724 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 729 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 730 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 731 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 732 */                 pc.set_backpage(1);
/* 733 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 735 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids6);
/* 736 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts6));
/* 737 */                 pc.set_backpage(1);
/* 738 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 742 */             if (olditem_weapon.getEnchantLevel() == 7) {
/* 743 */               if (pc.getInventory().checkItem(armor_itemids7, armor_counts7)) {
/* 744 */                 pc.getInventory().consumeItem(armor_itemids7, armor_counts7);
/* 745 */                 int count2 = 0;
/* 746 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 747 */                 while (iter.hasNext()) {
/* 748 */                   count2++;
/* 749 */                   int s = ((Integer)iter.next()).intValue();
/* 750 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 751 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 755 */                 int count3 = 0;
/* 756 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 757 */                 while (iter2.hasNext()) {
/* 758 */                   count3++;
/* 759 */                   L1ItemInstance s = iter2.next();
/* 760 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 761 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 765 */                 int count4 = 0;
/* 766 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 767 */                 while (iter3.hasNext()) {
/* 768 */                   count4++;
/* 769 */                   String s = iter3.next();
/* 770 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 771 */                     count4 == pc.get_backpage()) {
/* 772 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 777 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 778 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 779 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 780 */                 pc.set_backpage(1);
/* 781 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 783 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids7);
/* 784 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts7));
/* 785 */                 pc.set_backpage(1);
/* 786 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 790 */             if (olditem_weapon.getEnchantLevel() == 8) {
/* 791 */               if (pc.getInventory().checkItem(armor_itemids8, armor_counts8)) {
/* 792 */                 pc.getInventory().consumeItem(armor_itemids8, armor_counts8);
/* 793 */                 int count2 = 0;
/* 794 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 795 */                 while (iter.hasNext()) {
/* 796 */                   count2++;
/* 797 */                   int s = ((Integer)iter.next()).intValue();
/* 798 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 799 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 803 */                 int count3 = 0;
/* 804 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 805 */                 while (iter2.hasNext()) {
/* 806 */                   count3++;
/* 807 */                   L1ItemInstance s = iter2.next();
/* 808 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 809 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 813 */                 int count4 = 0;
/* 814 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 815 */                 while (iter3.hasNext()) {
/* 816 */                   count4++;
/* 817 */                   String s = iter3.next();
/* 818 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 819 */                     count4 == pc.get_backpage()) {
/* 820 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 825 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 826 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 827 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 828 */                 pc.set_backpage(1);
/* 829 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 831 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids8);
/* 832 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts8));
/* 833 */                 pc.set_backpage(1);
/* 834 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */             
/* 838 */             if (olditem_weapon.getEnchantLevel() == 9) {
/* 839 */               if (pc.getInventory().checkItem(armor_itemids9, armor_counts9)) {
/* 840 */                 pc.getInventory().consumeItem(armor_itemids9, armor_counts9);
/* 841 */                 int count2 = 0;
/* 842 */                 Iterator<Integer> iter = GiveBack.savepcid.iterator();
/* 843 */                 while (iter.hasNext()) {
/* 844 */                   count2++;
/* 845 */                   int s = ((Integer)iter.next()).intValue();
/* 846 */                   if (s == pc.getId() && count2 == pc.get_backpage()) {
/* 847 */                     iter.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 851 */                 int count3 = 0;
/* 852 */                 Iterator<L1ItemInstance> iter2 = GiveBack.saveweapon.iterator();
/* 853 */                 while (iter2.hasNext()) {
/* 854 */                   count3++;
/* 855 */                   L1ItemInstance s = iter2.next();
/* 856 */                   if (s == olditem_weapon && count3 == pc.get_backpage()) {
/* 857 */                     iter2.remove();
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 861 */                 int count4 = 0;
/* 862 */                 Iterator<String> iter3 = GiveBack.savename.iterator();
/* 863 */                 while (iter3.hasNext()) {
/* 864 */                   count4++;
/* 865 */                   String s = iter3.next();
/* 866 */                   if (s.equals(olditem_weapon.getNumberedViewName(1L)) && 
/* 867 */                     count4 == pc.get_backpage()) {
/* 868 */                     iter3.remove();
/*     */                     
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 873 */                 pc.getInventory().storeItem1(olditem_weapon);
/* 874 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV 您的 \\fV " + olditem_weapon.getNumberedViewName(1L) + "\\fV  已贖回。"));
/* 875 */                 DwarfForVIPReading.get().deleteItem(pc.getName(), olditem_weapon);
/* 876 */                 pc.set_backpage(1);
/* 877 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } else {
/* 879 */                 L1Item temper = ItemTable.get().getTemplate(armor_itemids9);
/* 880 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage(String.valueOf(temper.getName()) + "不足:" + armor_counts9));
/* 881 */                 pc.set_backpage(1);
/* 882 */                 pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */               } 
/*     */             }
/*     */           } else {
/* 886 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fV贖回物品不是武器。"));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 894 */     if (cmd.equals("prev")) {
/* 895 */       String str1 = null; int count = 0;
/* 896 */       for (int i = 0; i < GiveBack.savepcid.size(); i++) {
/* 897 */         if (((Integer)GiveBack.savepcid.get(i)).intValue() == pc
/* 898 */           .getId()) {
/* 899 */           count++;
/*     */         }
/*     */       } 
/* 902 */       pc.set_backpage(pc.get_backpage() - 1);
/* 903 */       if (pc.get_backpage() <= 1) {
/* 904 */         pc.set_backpage(1);
/*     */       }
/* 906 */       String msg0 = " ";
/* 907 */       String msg1 = " ";
/* 908 */       String msg2 = " ";
/* 909 */       String msg3 = " ";
/* 910 */       int count2 = 0;
/* 911 */       for (int j = 0; j < GiveBack.savepcid.size(); j++) {
/* 912 */         if (((Integer)GiveBack.savepcid.get(j)).intValue() == pc
/* 913 */           .getId()) {
/* 914 */           count2++;
/* 915 */           if (pc.get_backpage() == count2) {
/* 916 */             msg0 = GiveBack.savename.get(j);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 921 */       int k = pc.get_backpage();
/* 922 */       int m = count;
/* 923 */       String[] price = ConfigOther.weapon_Item_Price6.split(",");
/* 924 */       L1Item temper = ItemTable.get().getTemplate(Integer.valueOf(price[0]).intValue());
/* 925 */       msg3 = String.valueOf(price[1]) + "個" + temper.getName();
/* 926 */       if (count == 0) {
/* 927 */         str1 = "0";
/*     */       }
/* 929 */       String[] msg = { msg0, str1, String.valueOf(m), msg3 };
/* 930 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "giveback", msg));
/*     */     } 
/* 932 */     if (cmd.equals("next")) {
/* 933 */       String str1 = null; int count = 0;
/* 934 */       for (int i = 0; i < GiveBack.savepcid.size(); i++) {
/* 935 */         if (((Integer)GiveBack.savepcid.get(i)).intValue() == pc
/* 936 */           .getId()) {
/* 937 */           count++;
/*     */         }
/*     */       } 
/* 940 */       pc.set_backpage(pc.get_backpage() + 1);
/* 941 */       if (pc.get_backpage() > count) {
/* 942 */         pc.set_backpage(count);
/*     */       }
/* 944 */       String msg0 = " ";
/* 945 */       String msg1 = " ";
/* 946 */       String msg2 = " ";
/* 947 */       String msg3 = " ";
/* 948 */       int count2 = 0;
/* 949 */       for (int j = 0; j < GiveBack.savepcid.size(); j++) {
/* 950 */         if (((Integer)GiveBack.savepcid.get(j)).intValue() == pc
/* 951 */           .getId()) {
/* 952 */           count2++;
/* 953 */           if (pc.get_backpage() == count2) {
/* 954 */             msg0 = GiveBack.savename.get(j);
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 959 */       int k = pc.get_backpage();
/* 960 */       int m = count;
/* 961 */       String[] price = ConfigOther.weapon_Item_Price6.split(",");
/* 962 */       L1Item temper = ItemTable.get().getTemplate(Integer.valueOf(price[0]).intValue());
/* 963 */       msg3 = String.valueOf(price[1]) + "個" + temper.getName();
/* 964 */       if (count == 0) {
/* 965 */         str1 = "0";
/*     */       }
/* 967 */       String[] msg = { msg0, str1, String.valueOf(m), msg3 };
/* 968 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "giveback", msg));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\npc\shop\Npc_GiveBack.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */