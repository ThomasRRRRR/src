/*     */ package com.add.system;
/*     */ 
/*     */ import com.add.system.ACardTable_doll;
import com.add.system.ACard_doll;
import com.lineage.config.ConfigOther;
/*     */ import com.lineage.server.datatables.DollPowerTable;
/*     */ import com.lineage.server.datatables.ItemTable;
/*     */ import com.lineage.server.datatables.NpcTable;
/*     */ import com.lineage.server.model.Instance.L1DollInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1War;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Doll;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.templates.L1Npc;
/*     */ import com.lineage.server.timecontroller.server.ServerWarExecutor;
/*     */ import com.lineage.server.world.WorldWar;

/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;

/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ public class CardBookCmd_doll
/*     */ {
/*  37 */   private static final Log _log = LogFactory.getLog(CardBookCmd_doll.class);
/*  38 */   private Map<Integer, L1DollInstance> _dolls2 = new HashMap<>();
/*     */   
/*  40 */   private Map<Integer, L1DollInstance> _dolls3 = new HashMap<>();
/*     */   private static CardBookCmd_doll _instance;
/*     */   
/*     */   public static CardBookCmd_doll get() {
/*  44 */     if (_instance == null) {
/*  45 */       _instance = new CardBookCmd_doll();
/*     */     }
/*  47 */     return _instance;
/*     */   }
/*     */   
/*     */   public boolean Cmd(L1PcInstance pc, String cmd) {
/*     */     try {
/*     */       ACard_doll card;

final StringBuilder stringBuilder = new StringBuilder();
int size = ACardTable_doll.get().ACardSize();
for (int i = 0 ; i <= size;i++) {
	final ACard_doll card1 = ACardTable_doll.get().getCard(i);
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
case "D_1":	
	ok = true;
	pc.setCarId(-pc.getCardId());
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll01", msg));
	break;
case "D_2":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll02", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "D_3":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll03", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "D_4":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll04", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "D_5":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll05", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "D_6":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll06", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
case "D_7":
	pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "cadoll07", msg));
	ok = true;
	pc.setCarId(-pc.getCardId());
	break;
    }


/*     */       String str;
/*  55 */       switch ((str = cmd).hashCode()) { case -886665974: if (!str.equals("cardset2_doll")) {
/*     */             break;
/*     */           }
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
/* 111 */           CardAllSet(pc);
/* 112 */           ok = true; break;case -404145970: if (!str.equals("polycard_doll"))
/*     */             break;  card = ACardTable_doll.get().getCard(pc.getCardId()); if (card != null)
/*     */             if (pc.getQuest().get_step(card.getQuestId()) != 0) { if (card.getPolyId() != 0) { useMagicDoll(pc, card.getPolyId(), card.getPolyId()); } else { pc.sendPackets((ServerBasePacket)new S_SystemMessage("無法召喚")); }  } else { pc.sendPackets((ServerBasePacket)new S_SystemMessage("此娃娃尚未登入卡冊")); }   ok = true; break;case 1814231064: if (!str.equals("cardset_doll"))
/* 115 */             break;  CardSet(pc); ok = true; break; }  if (ok) {
/* 116 */         return true;
/*     */       }
/* 118 */     } catch (Exception e) {
/* 119 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   private void CardAllSet(L1PcInstance pc) {
/*     */     try {
/* 126 */       int str = 0;
/* 127 */       int dex = 0;
/* 128 */       int con = 0;
/* 129 */       int Int = 0;
/* 130 */       int wis = 0;
/* 131 */       int cha = 0;
/* 132 */       int ac = 0;
/* 133 */       int hp = 0;
/* 134 */       int mp = 0;
/* 135 */       int hpr = 0;
/* 136 */       int mpr = 0;
/* 137 */       int dmg = 0;
/* 138 */       int bdmg = 0;
/* 139 */       int hit = 0;
/* 140 */       int bhit = 0;
/* 141 */       int dr = 0;
/* 142 */       int mdr = 0;
/* 143 */       int sp = 0;
/* 144 */       int mhit = 0;
/* 145 */       int mr = 0;
/* 146 */       int f = 0;
/* 147 */       int wind = 0;
/* 148 */       int w = 0;
/* 149 */       int e = 0; int i;
/* 150 */       for (i = 0; i <= ACardTable_doll.get().ACardSize(); i++) {
/* 151 */         ACard_doll card = ACardTable_doll.get().getCard(i);
/* 152 */         if (card != null && 
/* 153 */           pc.getQuest().get_step(card.getQuestId()) != 0) {
/* 154 */           str += card.getAddStr();
/* 155 */           dex += card.getAddDex();
/* 156 */           con += card.getAddCon();
/* 157 */           Int += card.getAddInt();
/* 158 */           wis += card.getAddWis();
/* 159 */           cha += card.getAddCha();
/* 160 */           ac += card.getAddAc();
/* 161 */           hp += card.getAddHp();
/* 162 */           mp += card.getAddMp();
/* 163 */           hpr += card.getAddHpr();
/* 164 */           mpr += card.getAddMpr();
/* 165 */           dmg += card.getAddDmg();
/* 166 */           bdmg += card.getAddBowDmg();
/* 167 */           hit += card.getAddHit();
/* 168 */           bhit += card.getAddBowHit();
/* 169 */           dr += card.getAddDmgR();
/* 170 */           mdr += card.getAddMagicDmgR();
/* 171 */           sp += card.getAddSp();
/* 172 */           mhit += card.getAddMagicHit();
/* 173 */           mr += card.getAddMr();
/* 174 */           f += card.getAddFire();
/* 175 */           wind += card.getAddWind();
/* 176 */           e += card.getAddEarth();
/* 177 */           w += card.getAddWater();
/*     */         } 
/*     */       } 
/*     */       
/* 181 */       for (i = 0; i <= CardSetTable_doll.get().CardCardSize(); i++) {
/* 182 */         CardPolySet_doll cards = CardSetTable_doll.get().getCard(i);
/* 183 */         if (cards != null && 
/* 184 */           pc.getQuest().get_step(cards.getQuestId()) != 0) {
/* 185 */           str += cards.getAddStr();
/* 186 */           dex += cards.getAddDex();
/* 187 */           con += cards.getAddCon();
/* 188 */           Int += cards.getAddInt();
/* 189 */           wis += cards.getAddWis();
/* 190 */           cha += cards.getAddCha();
/* 191 */           ac += cards.getAddAc();
/* 192 */           hp += cards.getAddHp();
/* 193 */           mp += cards.getAddMp();
/* 194 */           hpr += cards.getAddHpr();
/* 195 */           mpr += cards.getAddMpr();
/* 196 */           dmg += cards.getAddDmg();
/* 197 */           bdmg += cards.getAddBowDmg();
/* 198 */           hit += cards.getAddHit();
/* 199 */           bhit += cards.getAddBowHit();
/* 200 */           dr += cards.getAddDmgR();
/* 201 */           mdr += cards.getAddMagicDmgR();
/* 202 */           sp += cards.getAddSp();
/* 203 */           mhit += cards.getAddMagicHit();
/* 204 */           mr += cards.getAddMr();
/* 205 */           f += cards.getAddFire();
/* 206 */           wind += cards.getAddWind();
/* 207 */           e += cards.getAddEarth();
/* 208 */           w += cards.getAddWater();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 213 */       StringBuilder stringBuilder = new StringBuilder();
/* 214 */       stringBuilder.append("力量 +" + str + ",");
/* 215 */       stringBuilder.append("敏捷 +" + dex + ",");
/* 216 */       stringBuilder.append("體質 +" + con + ",");
/* 217 */       stringBuilder.append("智力 +" + Int + ",");
/* 218 */       stringBuilder.append("精神 +" + wis + ",");
/* 219 */       stringBuilder.append("魅力 +" + cha + ",");
/* 220 */       stringBuilder.append("防禦提升 " + ac + ",");
/* 221 */       stringBuilder.append("HP +" + hp + ",");
/* 222 */       stringBuilder.append("MP +" + mp + ",");
/* 223 */       stringBuilder.append("血量回復 +" + hpr + ",");
/* 224 */       stringBuilder.append("魔力回復 +" + mpr + ",");
/* 225 */       stringBuilder.append("近距離傷害 +" + dmg + ",");
/* 226 */       stringBuilder.append("遠距離傷害 +" + bdmg + ",");
/* 227 */       stringBuilder.append("近距離命中 +" + hit + ",");
/* 228 */       stringBuilder.append("遠距離命中 +" + bhit + ",");
/* 229 */       stringBuilder.append("物理傷害減免 +" + dr + ",");
/* 230 */       stringBuilder.append("魔法傷害減免 +" + mdr + ",");
/* 231 */       stringBuilder.append("魔攻 +" + sp + ",");
/* 232 */       stringBuilder.append("魔法命中 +" + mhit + ",");
/* 233 */       stringBuilder.append("魔法防禦 +" + mr + ",");
/* 234 */       stringBuilder.append("火屬性防禦 +" + f + ",");
/* 235 */       stringBuilder.append("風屬性防禦 +" + wind + ",");
/* 236 */       stringBuilder.append("地屬性防禦 +" + e + ",");
/* 237 */       stringBuilder.append("水屬性防禦 +" + w + ",");
/* 238 */       String[] clientStrAry = stringBuilder.toString().split(",");
/* 239 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_11_doll", clientStrAry));
/* 240 */     } catch (Exception e) {
/* 241 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void CardSet(L1PcInstance pc) {
/*     */     try {
/* 247 */       StringBuilder stringBuilder = new StringBuilder();
/* 248 */       for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); i++) {
/* 249 */         CardPolySet_doll cards = CardSetTable_doll.get().getCard(i);
/* 250 */         if (cards != null) {
/*     */           
/* 252 */           stringBuilder.append(String.valueOf(cards.getMsg1()) + ",");
/* 253 */           int k = 0;
/* 254 */           for (int j = 0; j < (cards.getNeedQuest()).length; j++) {
/* 255 */             if (pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0) {
/* 256 */               stringBuilder.append(String.valueOf(cards.getNeedName()[j]) + "(登入),");
/* 257 */               k++;
/*     */             } else {
/* 259 */               stringBuilder.append(String.valueOf(cards.getNeedName()[j]) + "(未登),");
/*     */             } 
/*     */           } 
/*     */           
/* 263 */           if (k == (cards.getNeedQuest()).length) {
/* 264 */             if (cards.getAddStr() != 0) {
/* 265 */               stringBuilder.append("力量 +" + cards.getAddStr() + ",");
/*     */             }
/* 267 */             if (cards.getAddDex() != 0) {
/* 268 */               stringBuilder.append("敏捷 +" + cards.getAddDex() + ",");
/*     */             }
/* 270 */             if (cards.getAddCon() != 0) {
/* 271 */               stringBuilder.append("體質 +" + cards.getAddCon() + ",");
/*     */             }
/* 273 */             if (cards.getAddInt() != 0) {
/* 274 */               stringBuilder.append("智力 +" + cards.getAddInt() + ",");
/*     */             }
/* 276 */             if (cards.getAddWis() != 0) {
/* 277 */               stringBuilder.append("精神 +" + cards.getAddWis() + ",");
/*     */             }
/* 279 */             if (cards.getAddCha() != 0) {
/* 280 */               stringBuilder.append("魅力 +" + cards.getAddCha() + ",");
/*     */             }
/* 282 */             if (cards.getAddAc() != 0) {
/* 283 */               stringBuilder.append("防禦提升 " + cards.getAddAc() + ",");
/*     */             }
/* 285 */             if (cards.getAddHp() != 0) {
/* 286 */               stringBuilder.append("HP +" + cards.getAddHp() + ",");
/*     */             }
/* 288 */             if (cards.getAddMp() != 0) {
/* 289 */               stringBuilder.append("MP +" + cards.getAddMp() + ",");
/*     */             }
/* 291 */             if (cards.getAddHpr() != 0) {
/* 292 */               stringBuilder.append("血量回復 +" + cards.getAddHpr() + ",");
/*     */             }
/* 294 */             if (cards.getAddMpr() != 0) {
/* 295 */               stringBuilder.append("魔力回復 +" + cards.getAddMpr() + ",");
/*     */             }
/* 297 */             if (cards.getAddDmg() != 0) {
/* 298 */               stringBuilder.append("近距離傷害 +" + cards.getAddDmg() + ",");
/*     */             }
/* 300 */             if (cards.getAddBowDmg() != 0) {
/* 301 */               stringBuilder.append("遠距離傷害 +" + cards.getAddBowDmg() + ",");
/*     */             }
/* 303 */             if (cards.getAddHit() != 0) {
/* 304 */               stringBuilder.append("近距離命中 +" + cards.getAddHit() + ",");
/*     */             }
/* 306 */             if (cards.getAddBowHit() != 0) {
/* 307 */               stringBuilder.append("遠距離命中 +" + cards.getAddBowHit() + ",");
/*     */             }
/* 309 */             if (cards.getAddDmgR() != 0) {
/* 310 */               stringBuilder.append("物理傷害減免 +" + cards.getAddDmgR() + ",");
/*     */             }
/* 312 */             if (cards.getAddMagicDmgR() != 0) {
/* 313 */               stringBuilder.append("魔法傷害減免 +" + cards.getAddMagicDmgR() + ",");
/*     */             }
/* 315 */             if (cards.getAddSp() != 0) {
/* 316 */               stringBuilder.append("魔攻 +" + cards.getAddSp() + ",");
/*     */             }
/* 318 */             if (cards.getAddMagicHit() != 0) {
/* 319 */               stringBuilder.append("魔法命中 +" + cards.getAddMagicHit() + ",");
/*     */             }
/* 321 */             if (cards.getAddMr() != 0) {
/* 322 */               stringBuilder.append("魔法防禦 +" + cards.getAddMr() + ",");
/*     */             }
/* 324 */             if (cards.getAddFire() != 0) {
/* 325 */               stringBuilder.append("火屬性防禦 +" + cards.getAddFire() + ",");
/*     */             }
/* 327 */             if (cards.getAddWind() != 0) {
/* 328 */               stringBuilder.append("風屬性防禦 +" + cards.getAddWind() + ",");
/*     */             }
/* 330 */             if (cards.getAddEarth() != 0) {
/* 331 */               stringBuilder.append("地屬性防禦 +" + cards.getAddEarth() + ",");
/*     */             }
/* 333 */             if (cards.getAddWater() != 0) {
/* 334 */               stringBuilder.append("水屬性防禦 +" + cards.getAddWater() + ",");
/*     */             }
/* 336 */             stringBuilder.append("<以上為此套卡能力加成>,");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 341 */       String[] clientStrAry = stringBuilder.toString().split(",");
/* 342 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_10_doll", clientStrAry));
/* 343 */     } catch (Exception e) {
/* 344 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean PolyCmd(L1PcInstance pc, String cmd) {
/*     */     try {
/* 350 */       boolean ok = false;
/* 351 */       StringBuilder stringBuilder = new StringBuilder();
/* 352 */       for (int i = 0; i <= ACardTable_doll.get().ACardSize(); i++) {
/* 353 */         ACard_doll card = ACardTable_doll.get().getCard(i);
/* 354 */         if (card != null && 
/* 355 */           cmd.equals(card.getCmd())) {
/* 356 */           pc.setCarId(i);
/* 357 */           stringBuilder.append(String.valueOf(card.getMsg2()) + ",");
/* 358 */           stringBuilder.append(String.valueOf(card.getAddStr()) + ",");
/* 359 */           stringBuilder.append(String.valueOf(card.getAddDex()) + ",");
/* 360 */           stringBuilder.append(String.valueOf(card.getAddCon()) + ",");
/* 361 */           stringBuilder.append(String.valueOf(card.getAddInt()) + ",");
/* 362 */           stringBuilder.append(String.valueOf(card.getAddWis()) + ",");
/* 363 */           stringBuilder.append(String.valueOf(card.getAddCha()) + ",");
/*     */           
/* 365 */           stringBuilder.append(String.valueOf(card.getAddAc()) + ",");
/* 366 */           stringBuilder.append(String.valueOf(card.getAddHp()) + ",");
/* 367 */           stringBuilder.append(String.valueOf(card.getAddMp()) + ",");
/* 368 */           stringBuilder.append(String.valueOf(card.getAddHpr()) + ",");
/* 369 */           stringBuilder.append(String.valueOf(card.getAddMpr()) + ",");
/*     */           
/* 371 */           stringBuilder.append(String.valueOf(card.getAddDmg()) + ",");
/* 372 */           stringBuilder.append(String.valueOf(card.getAddBowDmg()) + ",");
/* 373 */           stringBuilder.append(String.valueOf(card.getAddHit()) + ",");
/* 374 */           stringBuilder.append(String.valueOf(card.getAddBowHit()) + ",");
/* 375 */           stringBuilder.append(String.valueOf(card.getAddDmgR()) + ",");
/* 376 */           stringBuilder.append(String.valueOf(card.getAddMagicDmgR()) + ",");
/*     */           
/* 378 */           stringBuilder.append(String.valueOf(card.getAddSp()) + ",");
/* 379 */           stringBuilder.append(String.valueOf(card.getAddMagicHit()) + ",");
/* 380 */           stringBuilder.append(String.valueOf(card.getAddMr()) + ",");
/* 381 */           stringBuilder.append(String.valueOf(card.getAddFire()) + ",");
/* 382 */           stringBuilder.append(String.valueOf(card.getAddWind()) + ",");
/* 383 */           stringBuilder.append(String.valueOf(card.getAddEarth()) + ",");
/* 384 */           stringBuilder.append(String.valueOf(card.getAddWater()) + ",");
/* 385 */           if (pc.getQuest().get_step(card.getQuestId()) != 0) {
/* 386 */             stringBuilder.append("[已存入],");
/*     */           } else {
/* 388 */             stringBuilder.append("未偵測[存入],");
/*     */           } 
/* 390 */           String[] clientStrAry = stringBuilder.toString().split(",");
/* 391 */           pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "card_0_doll", clientStrAry));
/*     */           
/* 393 */           ok = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 399 */       if (ok) {
/* 400 */         return true;
/*     */       }
/* 402 */     } catch (Exception e) {
/* 403 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 405 */     return false;
/*     */   }
/*     */   
/*     */   public static void useMagicDoll(L1PcInstance pc, int itemId, int itemObjectId) {
/* 409 */     L1Doll type = DollPowerTable.get().get_type(itemId);
/* 410 */     if (pc.getDoll(itemObjectId) != null) {
/*     */       
/* 412 */       pc.getDoll(itemObjectId).deleteDoll();
/*     */       return;
/*     */     } 
/* 415 */     if (pc.isInvisble()) {
/*     */       return;
/*     */     }
/* 418 */     for (int castle_id = 1; castle_id < 8; castle_id++) {
/* 419 */       if (!ConfigOther.WAR_DOLL && 
/* 420 */         ServerWarExecutor.get().isNowWar(castle_id) && pc.getMapId() == 4) {
/* 421 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("攻城戰期間無法使用娃娃!!"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     if (pc.getdollcount() >= ConfigOther.dollcount && type.get_mode() == 0) {
/*     */       
/* 440 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("已攜帶:" + pc.getdollcount() + "隻魔法娃娃，不能帶更多了"));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 447 */     if (pc.getdollcount1() >= ConfigOther.dollcount1 && type.get_mode() == 1) {
/* 448 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("已攜帶:" + pc.getdollcount1() + "隻補助娃娃，不能帶更多了"));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 455 */     if (!pc.getDolls().isEmpty()) {
/*     */ 
/*     */       
/* 458 */       if (pc.getdollcount_itemid() == itemId && type.get_mode() == 0) {
/* 459 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY不能攜帶相同的娃娃"));
/*     */         return;
/*     */       } 
/* 462 */       if (pc.getdollcount1_itemid() == itemId && type.get_mode() == 1) {
/* 463 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY不能攜帶相同的輔助娃娃"));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 470 */     if (!ConfigOther.WAR_DOLL)
/*     */     {
/* 472 */       if (pc.getClan() != null) {
/* 473 */         boolean inWar = false;
/* 474 */         if (pc.getClan().getCastleId() != 0) {
/* 475 */           if (ServerWarExecutor.get().isNowWar(pc.getClan().getCastleId())) {
/* 476 */             inWar = true;
/*     */           }
/*     */         } else {
/*     */           
/* 480 */           List<L1War> warList = WorldWar.get().getWarList();
/* 481 */           for (Iterator<L1War> iter = warList.iterator(); iter.hasNext(); ) {
/* 482 */             L1War war = iter.next();
/* 483 */             if (war.checkClanInWar(pc.getClan().getClanName())) {
/* 484 */               inWar = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 490 */         if (inWar) {
/*     */           
/* 492 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage(1531));
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     }
/* 498 */     boolean iserror = false;
/* 499 */     if (type != null) {
/* 500 */       if (type.get_need() != null) {
/* 501 */         int[] itemids = type.get_need();
/* 502 */         int[] counts = type.get_counts();
/*     */         int i;
/* 504 */         for (i = 0; i < itemids.length; i++) {
/* 505 */           if (!pc.getInventory().checkItem(itemids[i], counts[i])) {
/* 506 */             L1Item temp = 
/* 507 */               ItemTable.get().getTemplate(itemids[i]);
/* 508 */             pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, temp.getNameId()));
/* 509 */             iserror = true;
/*     */           } 
/*     */         } 
/*     */         
/* 513 */         if (!iserror) {
/* 514 */           for (i = 0; i < itemids.length; i++) {
/* 515 */             pc.getInventory().consumeItem(itemids[i], counts[i]);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 520 */       if (!iserror) {
/* 521 */         L1Npc template = NpcTable.get().getTemplate(71082);
/*     */ 
/*     */ 
/*     */         
/* 525 */         if (type.get_mode() == 0) {
/*     */           
/* 527 */           L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, false);
/* 528 */           pc.add_dollcount(1);
/* 529 */           pc.add_dollcount_itemid(itemId);
/*     */         } 
/*     */         
/* 532 */         if (type.get_mode() == 1) {
/*     */           
/* 534 */           L1DollInstance doll = new L1DollInstance(template, pc, itemObjectId, type, true);
/* 535 */           pc.add_dollcount1(1);
/* 536 */           pc.add_dollcount1_itemid(itemId);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\add\system\CardBookCmd_doll.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */