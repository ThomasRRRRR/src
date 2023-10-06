/*     */ package com.lineage.data.cmd;
/*     */ 
/*     */ import com.lineage.config.Configtype;
/*     */ import com.lineage.server.datatables.lock.DwarfForVIPReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1ItemPower;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_OwnCharStatus;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxGree;
/*     */ import com.lineage.server.serverpackets.S_SPMR;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.william.GiveBack;

/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantAccessory_Acc
/*     */   extends EnchantExecutor_Acc
/*     */ {
/*  28 */   private static final Log _log = LogFactory.getLog(EnchantAccessory_Acc.class);
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
/*     */   public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
/*  96 */     if (pc.get_other().get_item() != null) {
/*  97 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
/*  98 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*  99 */       pc.get_other().set_item(null);
/*     */       return;
/*     */     } 
/* 102 */     StringBuilder s = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     if (!item.isIdentified()) {
/* 109 */       s.append(item.getName());
/*     */     } else {
/*     */       
/* 112 */       s.append(item.getLogName());
/*     */     } 
/*     */     
/* 115 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(164, s.toString(), "$252"));
/*     */ 
/*     */ 
/*     */     
/* 119 */     if (!pc.isGm() && Configtype.Accessoryfalse == 1 && 
/* 120 */       item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.Accessory_savebroad)
/*     */     {
/* 122 */       World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg4_fail, new Object[] { pc.getName(), s.toString() })));
/*跑馬燈*/   	World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fO強化飾品失敗  失去【+ " + item.getEnchantLevel() + " " + item.getItem().getName() +"】"));
/*     */     }
/*     */     
/* 125 */     GiveBack.savepcid.add(Integer.valueOf(pc.getId()));
/* 126 */     GiveBack.saveweapon.add(item);
/* 127 */     GiveBack.savename.add(item.getNumberedViewName(1L));
/* 128 */     DwarfForVIPReading.get().insertItem(pc.getName(), item);
/* 129 */     pc.getInventory().removeItem(item, item.getCount());
/* 130 */     _log.info("人物:" + pc.getName() + "點爆物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void successEnchant(L1PcInstance pc, L1ItemInstance item, int randomELevel) {
/* 135 */     if (pc.get_other().get_item() != null) {
/* 136 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
/* 137 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/* 138 */       pc.get_other().set_item(null);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     StringBuilder s = new StringBuilder();
/* 150 */     StringBuilder sa = new StringBuilder();
/* 151 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 153 */     if (!item.isIdentified()) {
/* 154 */       s.append(item.getName());
/*     */     }
/* 156 */     else if (item.getEnchantLevel() > 0) {
/* 157 */       s.append("+" + item.getEnchantLevel() + " " + item.getName());
/*     */     }
/* 159 */     else if (item.getEnchantLevel() < 0) {
/* 160 */       s.append(String.valueOf(item.getEnchantLevel()) + " " + item.getName());
/*     */     } else {
/*     */       
/* 163 */       s.append(item.getName());
/*     */     } 
/*     */     
/* 166 */     switch (randomELevel) {
/*     */       
/*     */       case 0:
/* 169 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(160, s.toString(), "$252", "$248"));
/* 170 */         pc.setcheck_lv(true);
/*     */         return;
/*     */       case -1:
/* 173 */         sa.append("$246");
/* 174 */         sb.append("$247");
/*     */         break;
/*     */       case 1:
/* 177 */         sa.append("$252");
/* 178 */         sb.append("$247");
/*     */         break;
/*     */       case 2:
/*     */       case 3:
/* 182 */         sa.append("$252");
/* 183 */         sb.append("$248");
/*     */         break;
/*     */     } 
/* 186 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
/*     */     
/* 188 */     int oldEnchantLvl = item.getEnchantLevel();
/* 189 */     int newEnchantLvl = oldEnchantLvl + randomELevel;
/*     */     
/* 191 */     if (oldEnchantLvl != newEnchantLvl) {
/* 192 */       item.setEnchantLevel(newEnchantLvl);
/* 193 */       pc.getInventory().updateItem(item, 4);
/* 194 */       pc.getInventory().saveItem(item, 4);
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
/* 205 */       if (!pc.isGm() && Configtype.Accessorytrue == 1 && 
/* 206 */         item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.Accessory_savebroad)
/*     */       {
/*     */ 
/*     */         
/* 210 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg4_true, new Object[] { pc.getName(), s.toString() })));
/*跑馬燈*/   	World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fD強化飾品成功  獲得【+ " + item.getEnchantLevel() + " " + item.getItem().getName() +"】"));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 215 */       if (item.isEquipped()) {
/* 216 */         Integer attack, bowattack, mr, sp, weight, hp; int use_type = item.getItem().getUseType();
/* 217 */         switch (use_type) {
/*     */           case 2:
/*     */           case 18:
/*     */           case 19:
/*     */           case 20:
/*     */           case 21:
/*     */           case 22:
/*     */           case 25:
/*     */           case 47:
/* 226 */             pc.addAc(-randomELevel);
/*     */             
/* 228 */             attack = (Integer)L1ItemPower.Attack.get(Integer.valueOf(item.getItemId()));
/* 229 */             if (attack != null) {
/* 230 */               pc.addMr(randomELevel * attack.intValue());
/*     */             }
/*     */             
/* 233 */             bowattack = (Integer)L1ItemPower.BowAttack.get(Integer.valueOf(item.getItemId()));
/* 234 */             if (bowattack != null) {
/* 235 */               pc.addMr(randomELevel * bowattack.intValue());
/*     */             }
/* 237 */             mr = (Integer)L1ItemPower.MR.get(Integer.valueOf(item.getItemId()));
/* 238 */             if (mr != null) {
/* 239 */               pc.addMr(randomELevel * mr.intValue());
/*     */             }
/* 241 */             sp = (Integer)L1ItemPower.SP.get(Integer.valueOf(item.getItemId()));
/* 242 */             if (sp != null) {
/* 243 */               pc.addSp(randomELevel * sp.intValue());
/*     */             }
/*     */             
/* 246 */             weight = (Integer)L1ItemPower.weightReductionByEnchant.get(Integer.valueOf(item.getItemId()));
/* 247 */             if (weight != null) {
/* 248 */               pc.addWeightReduction(randomELevel * weight.intValue());
/*     */             }
/* 250 */             hp = (Integer)L1ItemPower.HP.get(Integer.valueOf(item.getItemId()));
/* 251 */             if (hp != null) {
/* 252 */               if (item.getItemId() == 300429) {
/* 253 */                 int enchantlvl = item.getEnchantLevel();
/* 254 */                 if (randomELevel == -1) {
/* 255 */                   enchantlvl++;
/*     */                 }
/* 257 */                 switch (enchantlvl) {
/*     */                   case 5:
/*     */                   case 7:
/*     */                   case 9:
/* 261 */                     pc.addMaxHp(randomELevel * hp.intValue()); break;
/*     */                 } 
/*     */                 break;
/*     */               } 
/* 265 */               pc.addMaxHp(randomELevel * hp.intValue());
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 23:
/*     */           case 24:
/*     */           case 37:
/*     */           case 40:
/* 273 */             if (item.getItem().get_greater() != 3) {
/* 274 */               item.GreaterAtEnchant(pc, randomELevel);
/*     */             }
/*     */             break;
/*     */         } 
/* 278 */         pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 279 */         pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(pc));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\cmd\EnchantAccessory_Acc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */