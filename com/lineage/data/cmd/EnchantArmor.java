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
/*     */ public class EnchantArmor
/*     */   extends EnchantExecutor
/*     */ {
/*  28 */   private static final Log _log = LogFactory.getLog(EnchantArmor.class);
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
/* 114 */     GiveBack.savepcid.add(Integer.valueOf(pc.getId()));
/* 115 */     GiveBack.saveweapon.add(item);
/* 116 */     GiveBack.savename.add(item.getNumberedViewName(1L));
/* 117 */     DwarfForVIPReading.get().insertItem(pc.getName(), item);
/* 118 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(164, s.toString(), "$252"));
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (!pc.isGm() && Configtype.armorbroadfail == 1 && 
/* 123 */       item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.armor_savebroad)
/*     */     {
/* 125 */       World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg1_fail, new Object[] { pc.getName(), s.toString() })));
/*跑馬燈*/   	World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fJ強化防具失敗  失去【+ " + item.getEnchantLevel() + " " + item.getName() +"】"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 130 */     pc.getInventory().removeItem(item, item.getCount());
/* 131 */     _log.info("人物:" + pc.getName() + "點爆物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void successEnchant(L1PcInstance pc, L1ItemInstance item, int randomELevel) {
/* 136 */     if (pc.get_other().get_item() != null) {
/* 137 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
/* 138 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/* 139 */       pc.get_other().set_item(null);
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
/* 150 */     StringBuilder s = new StringBuilder();
/* 151 */     StringBuilder sa = new StringBuilder();
/* 152 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 154 */     if (!item.isIdentified()) {
/* 155 */       s.append(item.getName());
/*     */     }
/* 157 */     else if (item.getEnchantLevel() > 0) {
/* 158 */       s.append("+" + item.getEnchantLevel() + " " + item.getName());
/*     */     }
/* 160 */     else if (item.getEnchantLevel() < 0) {
/* 161 */       s.append(String.valueOf(item.getEnchantLevel()) + " " + item.getName());
/*     */     } else {
/*     */       
/* 164 */       s.append(item.getName());
/*     */     } 
/*     */     
/* 167 */     switch (randomELevel) {
/*     */       
/*     */       case 0:
/* 170 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(160, s.toString(), "$252", "$248"));
/* 171 */         pc.setcheck_lv(true);
/*     */         return;
/*     */       case -1:
/* 174 */         sa.append("$246");
/* 175 */         sb.append("$247");
/*     */         break;
/*     */       case 1:
/* 178 */         sa.append("$252");
/* 179 */         sb.append("$247");
/*     */         break;
/*     */       case 2:
/*     */       case 3:
/* 183 */         sa.append("$252");
/* 184 */         sb.append("$248");
/*     */         break;
/*     */     } 
/* 187 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
/*     */     
/* 189 */     int oldEnchantLvl = item.getEnchantLevel();
/* 190 */     int newEnchantLvl = oldEnchantLvl + randomELevel;
/*     */     
/* 192 */     if (oldEnchantLvl != newEnchantLvl) {
/* 193 */       item.setEnchantLevel(newEnchantLvl);
/* 194 */       pc.getInventory().updateItem(item, 4);
/* 195 */       pc.getInventory().saveItem(item, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 201 */       if (!pc.isGm() && Configtype.armorbroadtrue == 1 && 
/* 202 */         item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.armor_savebroad)
/*     */       {
/* 194 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg1_true, new Object[] { pc.getName(), pc.getoldAllName(), "+" + newEnchantLvl + " " + item.getName()})));
/*跑馬燈*/   	  World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fH強化防具成功  獲得【+ " + newEnchantLvl + " " + item.getName() +"】"));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       if (item.isEquipped()) {
/* 215 */         Integer attack, bowattack, mr, sp, weight, hp; int use_type = item.getItem().getUseType();
/* 216 */         switch (use_type) {
/*     */           case 2:
/*     */           case 18:
/*     */           case 19:
/*     */           case 20:
/*     */           case 21:
/*     */           case 22:
/*     */           case 25:
/*     */           case 47:
/* 225 */             pc.addAc(-randomELevel);
/*     */             
/* 227 */             attack = (Integer)L1ItemPower.Attack.get(Integer.valueOf(item.getItemId()));
/* 228 */             if (attack != null) {
/* 229 */               pc.addMr(randomELevel * attack.intValue());
/*     */             }
/*     */             
/* 232 */             bowattack = (Integer)L1ItemPower.BowAttack.get(Integer.valueOf(item.getItemId()));
/* 233 */             if (bowattack != null) {
/* 234 */               pc.addMr(randomELevel * bowattack.intValue());
/*     */             }
/* 236 */             mr = (Integer)L1ItemPower.MR.get(Integer.valueOf(item.getItemId()));
/* 237 */             if (mr != null) {
/* 238 */               pc.addMr(randomELevel * mr.intValue());
/*     */             }
/* 240 */             sp = (Integer)L1ItemPower.SP.get(Integer.valueOf(item.getItemId()));
/* 241 */             if (sp != null) {
/* 242 */               pc.addSp(randomELevel * sp.intValue());
/*     */             }
/*     */             
/* 245 */             weight = (Integer)L1ItemPower.weightReductionByEnchant.get(Integer.valueOf(item.getItemId()));
/* 246 */             if (weight != null) {
/* 247 */               pc.addWeightReduction(randomELevel * weight.intValue());
/*     */             }
/* 249 */             hp = (Integer)L1ItemPower.HP.get(Integer.valueOf(item.getItemId()));
/* 250 */             if (hp != null) {
/* 251 */               if (item.getItemId() == 300429) {
/* 252 */                 int enchantlvl = item.getEnchantLevel();
/* 253 */                 if (randomELevel == -1) {
/* 254 */                   enchantlvl++;
/*     */                 }
/* 256 */                 switch (enchantlvl) {
/*     */                   case 5:
/*     */                   case 7:
/*     */                   case 9:
/* 260 */                     pc.addMaxHp(randomELevel * hp.intValue()); break;
/*     */                 } 
/*     */                 break;
/*     */               } 
/* 264 */               pc.addMaxHp(randomELevel * hp.intValue());
/*     */             } 
/*     */             break;
/*     */           
/*     */           case 23:
/*     */           case 24:
/*     */           case 37:
/*     */           case 40:
/* 272 */             if (item.getItem().get_greater() != 3) {
/* 273 */               item.GreaterAtEnchant(pc, randomELevel);
/*     */             }
/*     */             break;
/*     */         } 
/* 277 */         pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 278 */         pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(pc));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\cmd\EnchantArmor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */