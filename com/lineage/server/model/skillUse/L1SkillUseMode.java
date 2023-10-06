/*     */ package com.lineage.server.model.skillUse;
/*     */ 
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1Character;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Skills;
/*     */ 
/*     */ public class L1SkillUseMode
/*     */ {
/*     */   public boolean isConsume(L1Character user, L1Skills skill) {
/*  13 */     int mpConsume = skill.getMpConsume();
/*  14 */     int hpConsume = skill.getHpConsume();
/*     */     
/*  16 */     int itemConsume = skill.getItemConsumeId();
/*  17 */     int itemConsumeCount = skill.getItemConsumeCount();
/*     */     
/*  19 */     int lawful = skill.getLawful();
/*     */     
/*  21 */     int skillId = skill.getSkillId();
/*     */     
/*  23 */     int currentMp = 0;
/*  24 */     int currentHp = 0;
/*     */     
/*  26 */     L1NpcInstance useNpc = null;
/*  27 */     L1PcInstance usePc = null;
/*     */     
/*  29 */     if (user instanceof L1NpcInstance) {
/*  30 */       useNpc = (L1NpcInstance)user;
/*  31 */       currentMp = useNpc.getCurrentMp();
/*  32 */       currentHp = useNpc.getCurrentHp();
/*     */       
/*  34 */       boolean isStop = false;
/*     */       
/*  36 */       if (useNpc.hasSkillEffect(64)) {
/*  37 */         isStop = true;
/*     */       }
/*     */       
/*  40 */       if (useNpc.hasSkillEffect(161) && !isStop) {
/*  41 */         isStop = true;
/*     */       }
/*     */       
/*  44 */       if (useNpc.hasSkillEffect(1007) && !isStop) {
/*  45 */         isStop = true;
/*     */       }
/*     */       
/*  36 */       if (useNpc.hasSkillEffect(8912)) {
/*  37 */         isStop = true;
/*     */       }

/*  48 */       if (isStop) {
/*  49 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  54 */     if (user instanceof L1PcInstance) {
/*     */       
/*  56 */       usePc = (L1PcInstance)user;
/*  57 */       currentMp = usePc.getCurrentMp();
/*  58 */       currentHp = usePc.getCurrentHp();
/*     */       
/*  60 */       if (usePc.getInt() > 12 && skillId > 8 && 
/*  61 */         skillId <= 80) {
/*  62 */         mpConsume--;
/*     */       }
/*  64 */       if (usePc.getInt() > 13 && skillId > 16 && 
/*  65 */         skillId <= 80) {
/*  66 */         mpConsume--;
/*     */       }
/*  68 */       if (usePc.getInt() > 14 && skillId > 23 && 
/*  69 */         skillId <= 80) {
/*  70 */         mpConsume--;
/*     */       }
/*  72 */       if (usePc.getInt() > 15 && skillId > 32 && 
/*  73 */         skillId <= 80) {
/*  74 */         mpConsume--;
/*     */       }
/*  76 */       if (usePc.getInt() > 16 && skillId > 40 && 
/*  77 */         skillId <= 80) {
/*  78 */         mpConsume--;
/*     */       }
/*  80 */       if (usePc.getInt() > 17 && skillId > 48 && 
/*  81 */         skillId <= 80) {
/*  82 */         mpConsume--;
/*     */       }
/*  84 */       if (usePc.getInt() > 18 && skillId > 56 && 
/*  85 */         skillId <= 80) {
/*  86 */         mpConsume--;
/*     */       }
/*     */       
/*  89 */       if (usePc.getInt() > 12 && skillId >= 87 && 
/*  90 */         skillId <= 91) {
/*  91 */         mpConsume -= usePc.getInt() - 12;
/*     */       }
/*     */       
/*  94 */       switch (skillId) {
/*     */         case 26:
/*  96 */           if (usePc.getInventory().checkEquipped(20013)) {
/*  97 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 43:
/* 101 */           if (usePc.getInventory().checkEquipped(20013)) {
/* 102 */             mpConsume >>= 1;
/*     */           }
/* 104 */           if (usePc.getInventory().checkEquipped(20008)) {
/* 105 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 1:
/* 109 */           if (usePc.getInventory().checkEquipped(20014)) {
/* 110 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 19:
/* 114 */           if (usePc.getInventory().checkEquipped(20014)) {
/* 115 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 12:
/* 119 */           if (usePc.getInventory().checkEquipped(20015)) {
/* 120 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 13:
/* 124 */           if (usePc.getInventory().checkEquipped(20015)) {
/* 125 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 42:
/* 129 */           if (usePc.getInventory().checkEquipped(20015)) {
/* 130 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */         case 54:
/* 134 */           if (usePc.getInventory().checkEquipped(20023)) {
/* 135 */             mpConsume >>= 1;
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 141 */       if (usePc.getOriginalMagicConsumeReduction() > 0) {
/* 142 */         mpConsume -= usePc.getOriginalMagicConsumeReduction();
/*     */       }
/*     */       
/* 145 */       if (skill.getMpConsume() > 0) {
/* 146 */         mpConsume = Math.max(mpConsume, 1);
/*     */       }
/*     */       
/* 149 */       if (usePc.isElf()) {
/* 150 */         boolean isError = false;
/* 151 */         String msg = null;
/* 152 */         if (skill.getSkillLevel() >= 17 && skill.getSkillLevel() <= 22) {
/* 153 */           int magicattr = skill.getAttr();
/* 154 */           switch (magicattr) {
/*     */             case 1:
/* 156 */               if (magicattr != usePc.getElfAttr()) {
/* 157 */                 isError = true;
/* 158 */                 msg = "$1062";
/*     */               } 
/*     */               break;
/*     */             case 2:
/* 162 */               if (magicattr != usePc.getElfAttr()) {
/* 163 */                 isError = true;
/* 164 */                 msg = "$1059";
/*     */               } 
/*     */               break;
/*     */             case 4:
/* 168 */               if (magicattr != usePc.getElfAttr()) {
/* 169 */                 isError = true;
/* 170 */                 msg = "$1060";
/*     */               } 
/*     */               break;
/*     */             case 8:
/* 174 */               if (magicattr != usePc.getElfAttr()) {
/* 175 */                 isError = true;
/* 176 */                 msg = "$1061";
/*     */               } 
/*     */               break;
/*     */           } 
/*     */ 
/*     */           
/* 182 */           if (skillId == 147 && usePc.getElfAttr() == 0) {
/*     */             
/* 184 */             usePc.sendPackets((ServerBasePacket)new S_ServerMessage(280));
/* 185 */             return false;
/*     */           } 
/*     */         } 
/* 188 */         if (isError && 
/* 189 */           !usePc.isGm()) {
/*     */           
/* 191 */           usePc.sendPackets((ServerBasePacket)new S_ServerMessage(1385, msg));
/* 192 */           return false;
/*     */         } 
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
/*     */ 
/*     */ 
/*     */       
/* 207 */       if (usePc.isDarkelf() && 
/* 208 */         skillId == 108) {
/* 209 */         hpConsume = currentHp - 100;
/* 210 */         mpConsume = currentMp - 1;
/*     */       } 
/*     */       
/* 213 */       if (itemConsume != 0 && 
/* 214 */         !usePc.getInventory().checkItem(itemConsume, itemConsumeCount) && 
/* 215 */         !usePc.isGm()) {
/* 216 */         usePc.sendPackets((ServerBasePacket)new S_ServerMessage(299));
/* 217 */         return false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 222 */     if (currentHp < hpConsume + 1) {
/* 223 */       if (usePc != null) {
/* 224 */         usePc.sendPackets((ServerBasePacket)new S_ServerMessage(279));
/* 225 */         if (usePc.isGm()) {
/* 226 */           usePc.setCurrentHp(usePc.getMaxHp());
/*     */         }
/*     */       } 
/* 229 */       return false;
/*     */     } 
/*     */     
/* 232 */     if (currentMp < mpConsume) {
/* 233 */       if (usePc != null) {
/*     */         
/* 235 */         usePc.sendPackets((ServerBasePacket)new S_ServerMessage(278));
/* 236 */         if (usePc.isGm()) {
/* 237 */           usePc.setCurrentMp(usePc.getMaxMp());
/*     */         }
/*     */       } 
/* 240 */       return false;
/*     */     } 
/*     */     
/* 243 */     if (usePc != null) {
/*     */       
/* 245 */       if (lawful != 0) {
/* 246 */         usePc.addLawful(lawful);
/*     */       }
/*     */       
/* 249 */       if (itemConsume != 0 && !usePc.isGm()) {
/* 250 */         usePc.getInventory().consumeItem(itemConsume, itemConsumeCount);
/*     */       }
/*     */     } 
/*     */     
/* 254 */     int current_hp = user.getCurrentHp() - hpConsume;
/* 255 */     user.setCurrentHp(current_hp);
/*     */     
/* 257 */     int current_mp = user.getCurrentMp() - mpConsume;
/* 258 */     user.setCurrentMp(current_mp);
/*     */     
/* 260 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\skillUse\L1SkillUseMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */