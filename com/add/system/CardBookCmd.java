/*     */ package com.add.system;
/*     */ 
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1Character;
/*     */ import com.lineage.server.model.L1PolyMorph;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CardBookCmd
/*     */ {
/*  18 */   private static final Log _log = LogFactory.getLog(CardBookCmd.class);
/*     */   
/*     */   private static CardBookCmd _instance;
/*     */   
/*     */   public static CardBookCmd get() {
/*  23 */     if (_instance == null) {
/*  24 */       _instance = new CardBookCmd();
/*     */     }
/*  26 */     return _instance;
/*     */   }
/*     */   public boolean Cmd(L1PcInstance pc, String cmd) {
/*     */     try {
/*     */       ACard card;
/*  31 */       

final StringBuilder stringBuilder = new StringBuilder();
int size = ACardTable.get().ACardSize();
for (int i = 0 ; i <= size;i++) {
	final ACard card1 = ACardTable.get().getCard(i);
	//获取列表id
	if (card1 != null) {//列表ID空白不启动
		if (pc.getQuest().get_step(card1.getQuestId()) != 0) {												
			stringBuilder.append(String.valueOf(card1.getAddcgfxid()) + ",");							
		} else {
			stringBuilder.append(String.valueOf(card1.getAddhgfxid()) + ",");							
		}
	}
}

final String[] msg = stringBuilder.toString().split(",");//从0开始分裂以逗号为单位

boolean ok = false; 

switch (cmd) {
case "C_1":	
	ok = true;
	pc.setCarId(-pc.getCardId());
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly01", msg));
	break;
case "C_2":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly02", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "C_3":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly03", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "C_4":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly04", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "C_5":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly05", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "C_6":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly06", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "C_7":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "capoly07", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
    }


String str;
/*  32 */       switch ((str = cmd).hashCode()) { 
case -7322848: if (!str.equals("cardset2")) {
/*     */             break;
/*     */           }
/*     */           
/* 106 */           CardAllSet(pc);
/* 107 */           ok = true;
break;
case 553953106: if (!str.equals("cardset"))
/*     */             break;
CardSet(pc);
ok = true;
break;
case 561663196: if (!str.equals("polycard"))
/*     */             break;






card = ACardTable.get().getCard(pc.getCardId());
if (card != null)
/* 110 */             if (pc.getQuest().get_step(card.getQuestId()) != 0) {
	if (card.getPolyId() != 0) {
		if (card.getPolyItemId() != 0) {
			if (pc.getInventory().checkItem(card.getPolyItemId(), card.getPolyItemCount())) {
				if (pc.getTempCharGfx() == pc.getcardpoly()) { 
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("無法重複變身,請先取消變身"));
					return false; }  
				L1PolyMorph.doPoly((L1Character)pc, card.getPolyId(), card.getPolyTime(), 1);
				pc.getInventory().consumeItem(card.getPolyItemId(), card.getPolyItemCount());
				pc.setcardpoly(card.getPolyId()); } 
			else {
				pc.sendPackets((ServerBasePacket)new S_SystemMessage("變身需求道具不足")); }  }
		else { L1PolyMorph.doPoly((L1Character)pc, card.getPolyId(), card.getPolyTime(), 1); }  }
	else { pc.sendPackets((ServerBasePacket)new S_SystemMessage("無法變身")); }  }
else { 
	pc.sendPackets((ServerBasePacket)new S_SystemMessage("尚未登入卡冊")); }
ok = true;
break; 
}
if (ok) {
/* 111 */         return true;
/*     */       }
/* 113 */     } catch (Exception e) {
/* 114 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   private void CardAllSet(L1PcInstance pc) {
/*     */     try {
/* 121 */       int str = 0;
/* 122 */       int dex = 0;
/* 123 */       int con = 0;
/* 124 */       int Int = 0;
/* 125 */       int wis = 0;
/* 126 */       int cha = 0;
/* 127 */       int ac = 0;
/* 128 */       int hp = 0;
/* 129 */       int mp = 0;
/* 130 */       int hpr = 0;
/* 131 */       int mpr = 0;
/* 132 */       int dmg = 0;
/* 133 */       int bdmg = 0;
/* 134 */       int hit = 0;
/* 135 */       int bhit = 0;
/* 136 */       int dr = 0;
/* 137 */       int mdr = 0;
/* 138 */       int sp = 0;
/* 139 */       int mhit = 0;
/* 140 */       int mr = 0;
/* 141 */       int f = 0;
/* 142 */       int wind = 0;
/* 143 */       int w = 0;
/* 144 */       int e = 0; int i;
/* 145 */       for (i = 0; i <= ACardTable.get().ACardSize(); i++) {
/* 146 */         ACard card = ACardTable.get().getCard(i);
/* 147 */         if (card != null && 
/* 148 */           pc.getQuest().get_step(card.getQuestId()) != 0) {
/* 149 */           str += card.getAddStr();
/* 150 */           dex += card.getAddDex();
/* 151 */           con += card.getAddCon();
/* 152 */           Int += card.getAddInt();
/* 153 */           wis += card.getAddWis();
/* 154 */           cha += card.getAddCha();
/* 155 */           ac += card.getAddAc();
/* 156 */           hp += card.getAddHp();
/* 157 */           mp += card.getAddMp();
/* 158 */           hpr += card.getAddHpr();
/* 159 */           mpr += card.getAddMpr();
/* 160 */           dmg += card.getAddDmg();
/* 161 */           bdmg += card.getAddBowDmg();
/* 162 */           hit += card.getAddHit();
/* 163 */           bhit += card.getAddBowHit();
/* 164 */           dr += card.getAddDmgR();
/* 165 */           mdr += card.getAddMagicDmgR();
/* 166 */           sp += card.getAddSp();
/* 167 */           mhit += card.getAddMagicHit();
/* 168 */           mr += card.getAddMr();
/* 169 */           f += card.getAddFire();
/* 170 */           wind += card.getAddWind();
/* 171 */           e += card.getAddEarth();
/* 172 */           w += card.getAddWater();
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       for (i = 0; i <= CardSetTable.get().CardCardSize(); i++) {
/* 177 */         CardPolySet cards = CardSetTable.get().getCard(i);
/* 178 */         if (cards != null && 
/* 179 */           pc.getQuest().get_step(cards.getQuestId()) != 0) {
/* 180 */           str += cards.getAddStr();
/* 181 */           dex += cards.getAddDex();
/* 182 */           con += cards.getAddCon();
/* 183 */           Int += cards.getAddInt();
/* 184 */           wis += cards.getAddWis();
/* 185 */           cha += cards.getAddCha();
/* 186 */           ac += cards.getAddAc();
/* 187 */           hp += cards.getAddHp();
/* 188 */           mp += cards.getAddMp();
/* 189 */           hpr += cards.getAddHpr();
/* 190 */           mpr += cards.getAddMpr();
/* 191 */           dmg += cards.getAddDmg();
/* 192 */           bdmg += cards.getAddBowDmg();
/* 193 */           hit += cards.getAddHit();
/* 194 */           bhit += cards.getAddBowHit();
/* 195 */           dr += cards.getAddDmgR();
/* 196 */           mdr += cards.getAddMagicDmgR();
/* 197 */           sp += cards.getAddSp();
/* 198 */           mhit += cards.getAddMagicHit();
/* 199 */           mr += cards.getAddMr();
/* 200 */           f += cards.getAddFire();
/* 201 */           wind += cards.getAddWind();
/* 202 */           e += cards.getAddEarth();
/* 203 */           w += cards.getAddWater();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 208 */       StringBuilder stringBuilder = new StringBuilder();
/* 209 */       stringBuilder.append("力量 +" + str + ",");
/* 210 */       stringBuilder.append("敏捷 +" + dex + ",");
/* 211 */       stringBuilder.append("體質 +" + con + ",");
/* 212 */       stringBuilder.append("智力 +" + Int + ",");
/* 213 */       stringBuilder.append("精神 +" + wis + ",");
/* 214 */       stringBuilder.append("魅力 +" + cha + ",");
/* 215 */       stringBuilder.append("防禦提升 " + ac + ",");
/* 216 */       stringBuilder.append("HP +" + hp + ",");
/* 217 */       stringBuilder.append("MP +" + mp + ",");
/* 218 */       stringBuilder.append("血量回復 +" + hpr + ",");
/* 219 */       stringBuilder.append("魔力回復 +" + mpr + ",");
/* 220 */       stringBuilder.append("近距離傷害 +" + dmg + ",");
/* 221 */       stringBuilder.append("遠距離傷害 +" + bdmg + ",");
/* 222 */       stringBuilder.append("近距離命中 +" + hit + ",");
/* 223 */       stringBuilder.append("遠距離命中 +" + bhit + ",");
/* 224 */       stringBuilder.append("物理傷害減免 +" + dr + ",");
/* 225 */       stringBuilder.append("魔法傷害減免 +" + mdr + ",");
/* 226 */       stringBuilder.append("魔攻 +" + sp + ",");
/* 227 */       stringBuilder.append("魔法命中 +" + mhit + ",");
/* 228 */       stringBuilder.append("魔法防禦 +" + mr + ",");
/* 229 */       stringBuilder.append("火屬性防禦 +" + f + ",");
/* 230 */       stringBuilder.append("風屬性防禦 +" + wind + ",");
/* 231 */       stringBuilder.append("地屬性防禦 +" + e + ",");
/* 232 */       stringBuilder.append("水屬性防禦 +" + w + ",");
/* 233 */       String[] clientStrAry = stringBuilder.toString().split(",");
/* 234 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_11", clientStrAry));
/* 235 */     } catch (Exception e) {
/* 236 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void CardSet(L1PcInstance pc) {
/*     */     try {
/* 242 */       StringBuilder stringBuilder = new StringBuilder();
/* 243 */       for (int i = 0; i <= CardSetTable.get().CardCardSize(); i++) {
/* 244 */         CardPolySet cards = CardSetTable.get().getCard(i);
/* 245 */         if (cards != null) {
/*     */           
/* 247 */           stringBuilder.append(String.valueOf(cards.getMsg1()) + ",");
/* 248 */           int k = 0;
/* 249 */           for (int j = 0; j < (cards.getNeedQuest()).length; j++) {
/* 250 */             if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
/* 251 */               stringBuilder.append(String.valueOf(cards.getNeedName()[j]) + "(登入),");
/* 252 */               k++;
/*     */             } else {
/* 254 */               stringBuilder.append(String.valueOf(cards.getNeedName()[j]) + "(未登),");
/*     */             } 
/*     */           } 
/*     */           
/* 258 */           if (k == (cards.getNeedQuest()).length) {
/* 259 */             if (cards.getAddStr() != 0) {
/* 260 */               stringBuilder.append("力量 +" + cards.getAddStr() + ",");
/*     */             }
/* 262 */             if (cards.getAddDex() != 0) {
/* 263 */               stringBuilder.append("敏捷 +" + cards.getAddDex() + ",");
/*     */             }
/* 265 */             if (cards.getAddCon() != 0) {
/* 266 */               stringBuilder.append("體質 +" + cards.getAddCon() + ",");
/*     */             }
/* 268 */             if (cards.getAddInt() != 0) {
/* 269 */               stringBuilder.append("智力 +" + cards.getAddInt() + ",");
/*     */             }
/* 271 */             if (cards.getAddWis() != 0) {
/* 272 */               stringBuilder.append("精神 +" + cards.getAddWis() + ",");
/*     */             }
/* 274 */             if (cards.getAddCha() != 0) {
/* 275 */               stringBuilder.append("魅力 +" + cards.getAddCha() + ",");
/*     */             }
/* 277 */             if (cards.getAddAc() != 0) {
/* 278 */               stringBuilder.append("防禦提升 " + cards.getAddAc() + ",");
/*     */             }
/* 280 */             if (cards.getAddHp() != 0) {
/* 281 */               stringBuilder.append("HP +" + cards.getAddHp() + ",");
/*     */             }
/* 283 */             if (cards.getAddMp() != 0) {
/* 284 */               stringBuilder.append("MP +" + cards.getAddMp() + ",");
/*     */             }
/* 286 */             if (cards.getAddHpr() != 0) {
/* 287 */               stringBuilder.append("血量回復 +" + cards.getAddHpr() + ",");
/*     */             }
/* 289 */             if (cards.getAddMpr() != 0) {
/* 290 */               stringBuilder.append("魔力回復 +" + cards.getAddMpr() + ",");
/*     */             }
/* 292 */             if (cards.getAddDmg() != 0) {
/* 293 */               stringBuilder.append("近距離傷害 +" + cards.getAddDmg() + ",");
/*     */             }
/* 295 */             if (cards.getAddBowDmg() != 0) {
/* 296 */               stringBuilder.append("遠距離傷害 +" + cards.getAddBowDmg() + ",");
/*     */             }
/* 298 */             if (cards.getAddHit() != 0) {
/* 299 */               stringBuilder.append("近距離命中 +" + cards.getAddHit() + ",");
/*     */             }
/* 301 */             if (cards.getAddBowHit() != 0) {
/* 302 */               stringBuilder.append("遠距離命中 +" + cards.getAddBowHit() + ",");
/*     */             }
/* 304 */             if (cards.getAddDmgR() != 0) {
/* 305 */               stringBuilder.append("物理傷害減免 +" + cards.getAddDmgR() + ",");
/*     */             }
/* 307 */             if (cards.getAddMagicDmgR() != 0) {
/* 308 */               stringBuilder.append("魔法傷害減免 +" + cards.getAddMagicDmgR() + ",");
/*     */             }
/* 310 */             if (cards.getAddSp() != 0) {
/* 311 */               stringBuilder.append("魔攻 +" + cards.getAddSp() + ",");
/*     */             }
/* 313 */             if (cards.getAddMagicHit() != 0) {
/* 314 */               stringBuilder.append("魔法命中 +" + cards.getAddMagicHit() + ",");
/*     */             }
/* 316 */             if (cards.getAddMr() != 0) {
/* 317 */               stringBuilder.append("魔法防禦 +" + cards.getAddMr() + ",");
/*     */             }
/* 319 */             if (cards.getAddFire() != 0) {
/* 320 */               stringBuilder.append("火屬性防禦 +" + cards.getAddFire() + ",");
/*     */             }
/* 322 */             if (cards.getAddWind() != 0) {
/* 323 */               stringBuilder.append("風屬性防禦 +" + cards.getAddWind() + ",");
/*     */             }
/* 325 */             if (cards.getAddEarth() != 0) {
/* 326 */               stringBuilder.append("地屬性防禦 +" + cards.getAddEarth() + ",");
/*     */             }
/* 328 */             if (cards.getAddWater() != 0) {
/* 329 */               stringBuilder.append("水屬性防禦 +" + cards.getAddWater() + ",");
/*     */             }
/* 331 */             stringBuilder.append("<以上為此套卡能力加成>,");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 336 */       String[] clientStrAry = stringBuilder.toString().split(",");
/* 337 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_10", clientStrAry));
/* 338 */     } catch (Exception e) {
/* 339 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean PolyCmd(L1PcInstance pc, String cmd) {
/*     */     try {
/* 345 */       boolean ok = false;
/* 346 */       StringBuilder stringBuilder = new StringBuilder();
/* 347 */       for (int i = 0; i <= ACardTable.get().ACardSize(); i++) {
/* 348 */         ACard card = ACardTable.get().getCard(i);
/* 349 */         if (card != null && 
/* 350 */           cmd.equals(card.getCmd())) {
/* 351 */           pc.setCarId(i);
/* 352 */           stringBuilder.append(String.valueOf(card.getMsg2()) + ",");
/* 353 */           stringBuilder.append(String.valueOf(card.getAddStr()) + ",");
/* 354 */           stringBuilder.append(String.valueOf(card.getAddDex()) + ",");
/* 355 */           stringBuilder.append(String.valueOf(card.getAddCon()) + ",");
/* 356 */           stringBuilder.append(String.valueOf(card.getAddInt()) + ",");
/* 357 */           stringBuilder.append(String.valueOf(card.getAddWis()) + ",");
/* 358 */           stringBuilder.append(String.valueOf(card.getAddCha()) + ",");
/*     */           
/* 360 */           stringBuilder.append(String.valueOf(card.getAddAc()) + ",");
/* 361 */           stringBuilder.append(String.valueOf(card.getAddHp()) + ",");
/* 362 */           stringBuilder.append(String.valueOf(card.getAddMp()) + ",");
/* 363 */           stringBuilder.append(String.valueOf(card.getAddHpr()) + ",");
/* 364 */           stringBuilder.append(String.valueOf(card.getAddMpr()) + ",");
/*     */           
/* 366 */           stringBuilder.append(String.valueOf(card.getAddDmg()) + ",");
/* 367 */           stringBuilder.append(String.valueOf(card.getAddBowDmg()) + ",");
/* 368 */           stringBuilder.append(String.valueOf(card.getAddHit()) + ",");
/* 369 */           stringBuilder.append(String.valueOf(card.getAddBowHit()) + ",");
/* 370 */           stringBuilder.append(String.valueOf(card.getAddDmgR()) + ",");
/* 371 */           stringBuilder.append(String.valueOf(card.getAddMagicDmgR()) + ",");
/*     */           
/* 373 */           stringBuilder.append(String.valueOf(card.getAddSp()) + ",");
/* 374 */           stringBuilder.append(String.valueOf(card.getAddMagicHit()) + ",");
/* 375 */           stringBuilder.append(String.valueOf(card.getAddMr()) + ",");
/* 376 */           stringBuilder.append(String.valueOf(card.getAddFire()) + ",");
/* 377 */           stringBuilder.append(String.valueOf(card.getAddWind()) + ",");
/* 378 */           stringBuilder.append(String.valueOf(card.getAddEarth()) + ",");
/* 379 */           stringBuilder.append(String.valueOf(card.getAddWater()) + ",");
/* 380 */           if (pc.getQuest().get_step(card.getQuestId()) != 0) {
/* 381 */             stringBuilder.append("[已存入],");
/*     */           } else {
/* 383 */             stringBuilder.append("未偵測[存入],");
/*     */           } 
/* 385 */           String[] clientStrAry = stringBuilder.toString().split(",");
/* 386 */           pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_0", clientStrAry));
/*     */           
/* 388 */           ok = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 394 */       if (ok) {
/* 395 */         return true;
/*     */       }
/* 397 */     } catch (Exception e) {
/* 398 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 400 */     return false;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\add\system\CardBookCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */