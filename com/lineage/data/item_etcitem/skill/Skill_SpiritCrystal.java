/*     */ package com.lineage.data.item_etcitem.skill;
/*     */ 
/*     */ import com.lineage.data.cmd.Skill_Check;
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ 
/*     */ public class Skill_SpiritCrystal
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  14 */     return new Skill_SpiritCrystal();
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  19 */     if (item == null) {
/*     */       return;
/*     */     }
/*     */     
/*  23 */     if (pc == null) {
/*     */       return;
/*     */     }
/*     */     
/*  27 */     if (!pc.isElf()) {
/*     */       
/*  29 */       S_ServerMessage msg = new S_ServerMessage(79);
/*  30 */       pc.sendPackets((ServerBasePacket)msg);
/*     */     }
/*     */     else {
/*     */       
/*  34 */       String nameId = item.getItem().getNameId();
/*     */       
/*  36 */       int skillid = 0;
/*     */       
/*  38 */       int attribute = 3;
/*     */       
/*  40 */       int magicLv = 0;
/* 32 */
/*判斷地點學習*/	int X = pc.getX();
/*判斷地點學習*/	int Y = pc.getY();		
/*判斷地點學習*/	int M = pc.getMapId();	
///*判斷地點學習*/  if (X <= 33055 && Y <= 32343 && X >= 33048 && Y >= 32336 && M == 4) {	//妖精森林
/*     */       
/*  42 */       if (nameId.equalsIgnoreCase("$1829")) {
/*     */         
/*  44 */         skillid = 129;
/*     */         
/*  46 */         magicLv = 11;
/*     */       }
/*  48 */       else if (nameId.equalsIgnoreCase("$1830")) {
/*     */         
/*  50 */         skillid = 130;
/*     */         
/*  52 */         magicLv = 11;
/*     */       }
/*  54 */       else if (nameId.equalsIgnoreCase("$1831")) {
/*     */         
/*  56 */         skillid = 131;
/*     */         
/*  58 */         magicLv = 11;
/*     */       }
/*  60 */       else if (nameId.equalsIgnoreCase("$1832")) {
/*     */         
/*  62 */         skillid = 137;
/*     */         
/*  64 */         magicLv = 12;
/*     */       }
/*  66 */       else if (nameId.equalsIgnoreCase("$1833")) {
/*     */         
/*  68 */         skillid = 138;
/*     */         
/*  70 */         magicLv = 12;
/*     */       }
/*  72 */       else if (nameId.equalsIgnoreCase("$3261")) {
/*     */         
/*  74 */         skillid = 132;
/*     */         
/*  76 */         magicLv = 13;
/*     */       }
/*  78 */       else if (nameId.equalsIgnoreCase("$1834")) {
/*     */         
/*  80 */         skillid = 145;
/*     */         
/*  82 */         magicLv = 13;
/*     */       }
/*  84 */       else if (nameId.equalsIgnoreCase("$1835")) {
/*     */         
/*  86 */         skillid = 146;
/*     */         
/*  88 */         magicLv = 13;
/*     */       }
/*  90 */       else if (nameId.equalsIgnoreCase("$1836")) {
/*     */         
/*  92 */         skillid = 147;
/*     */         
/*  94 */         magicLv = 13;
/*     */       }
/*  96 */       else if (nameId.equalsIgnoreCase("$3262")) {
/*     */         
/*  98 */         skillid = 133;
/*     */         
/* 100 */         magicLv = 14;
/*     */       }
/* 102 */       else if (nameId.equalsIgnoreCase("$1842")) {
/*     */         
/* 104 */         skillid = 153;
/*     */         
/* 106 */         magicLv = 14;
/*     */       }
/* 108 */       else if (nameId.equalsIgnoreCase("$1843")) {
/*     */         
/* 110 */         skillid = 154;
/*     */         
/* 112 */         magicLv = 14;
/*     */       }
/* 114 */       else if (nameId.equalsIgnoreCase("$3263")) {
/*     */         
/* 116 */         skillid = 134;
/*     */         
/* 118 */         magicLv = 15;
/*     */       }
/* 120 */       else if (nameId.equalsIgnoreCase("$1849")) {
/*     */         
/* 122 */         skillid = 161;
/*     */         
/* 124 */         magicLv = 15;
/*     */       }
/* 126 */       else if (nameId.equalsIgnoreCase("$1850")) {
/*     */         
/* 128 */         skillid = 162;
/*     */         
/* 130 */         magicLv = 15;
/*     */       }
/* 132 */       else if (nameId.equalsIgnoreCase("$1856")) {
/*     */         
/* 134 */         skillid = 168;
/*     */         
/* 136 */         magicLv = 15;
/*     */       }
/* 138 */       else if (nameId.equalsIgnoreCase("$4716")) {
/*     */         
/* 140 */         skillid = 160;
/*     */         
/* 142 */         magicLv = 14;
/*     */       }
/* 144 */       else if (nameId.equalsIgnoreCase("$1840")) {
/*     */         
/* 146 */         skillid = 151;
/*     */         
/* 148 */         magicLv = 13;
/*     */       }
/* 150 */       else if (nameId.equalsIgnoreCase("$1841")) {
/*     */         
/* 152 */         skillid = 152;
/*     */         
/* 154 */         magicLv = 13;
/*     */       }
/* 156 */       else if (nameId.equalsIgnoreCase("$1846")) {
/*     */         
/* 158 */         skillid = 157;
/*     */         
/* 160 */         magicLv = 14;
/*     */       }
/* 162 */       else if (nameId.equalsIgnoreCase("精靈水晶(大地的祝福)")) {
/*     */         
/* 164 */         skillid = 159;
/*     */         
/* 166 */         magicLv = 14;
/*     */       }
/* 168 */       else if (nameId.equalsIgnoreCase("$1856")) {
/*     */         
/* 170 */         skillid = 168;
/*     */         
/* 172 */         magicLv = 15;
/*     */       }
/* 174 */       else if (nameId.equalsIgnoreCase("$3265")) {
/*     */         
/* 176 */         skillid = 169;
/*     */         
/* 178 */         magicLv = 15;
/*     */       }
/* 180 */       else if (nameId.equalsIgnoreCase("$1837")) {
/*     */         
/* 182 */         skillid = 148;
/*     */         
/* 184 */         magicLv = 13;
/*     */       }
/* 186 */       else if (nameId.equalsIgnoreCase("精靈水晶(烈炎氣息)")) {
/*     */         
/* 188 */         skillid = 155;
/*     */         
/* 190 */         magicLv = 14;
/*     */       }
/* 192 */       else if (nameId.equalsIgnoreCase("$1851")) {
/*     */         
/* 194 */         skillid = 163;
/*     */         
/* 196 */         magicLv = 15;
/*     */       }
/* 198 */       else if (nameId.equalsIgnoreCase("$3267")) {
/*     */         
/* 200 */         skillid = 171;
/*     */         
/* 202 */         magicLv = 15;
/*     */       }
/* 204 */       else if (nameId.equalsIgnoreCase("$4714")) {
/*     */         
/* 206 */         skillid = 175;
/*     */         
/* 208 */         magicLv = 15;
/*     */       }
/* 210 */       else if (nameId.equalsIgnoreCase("$4715")) {
/*     */         
/* 212 */         skillid = 176;
/*     */         
/* 214 */         magicLv = 15;
/*     */       }
/* 216 */       else if (nameId.equalsIgnoreCase("$3266")) {
/*     */         
/* 218 */         skillid = 170;
/*     */         
/* 220 */         magicLv = 13;
/*     */       }
/* 222 */       else if (nameId.equalsIgnoreCase("$1847")) {
/*     */         
/* 224 */         skillid = 158;
/*     */         
/* 226 */         magicLv = 14;
/*     */       }
/* 228 */       else if (nameId.equalsIgnoreCase("$4716")) {
/*     */         
/* 230 */         skillid = 160;
/*     */         
/* 232 */         magicLv = 14;
/*     */       }
/* 234 */       else if (nameId.equalsIgnoreCase("$1852")) {
/*     */         
/* 236 */         skillid = 164;
/*     */         
/* 238 */         magicLv = 15;
/*     */       }
/* 240 */       else if (nameId.equalsIgnoreCase("$1853")) {
/*     */         
/* 242 */         skillid = 165;
/*     */         
/* 244 */         magicLv = 15;
/*     */       }
/* 246 */       else if (nameId.equalsIgnoreCase("$4717")) {
/*     */         
/* 248 */         skillid = 173;
/*     */         
/* 250 */         magicLv = 15;
/*     */       }
/* 252 */       else if (nameId.equalsIgnoreCase("$1838")) {
/*     */         
/* 254 */         skillid = 149;
/*     */         
/* 256 */         magicLv = 13;
/*     */       }
/* 258 */       else if (nameId.equalsIgnoreCase("$1839")) {
/*     */         
/* 260 */         skillid = 150;
/*     */         
/* 262 */         magicLv = 13;
/*     */       }
/* 264 */       else if (nameId.equalsIgnoreCase("$1845")) {
/*     */         
/* 266 */         skillid = 156;
/*     */         
/* 268 */         magicLv = 14;
/*     */       }
/* 270 */       else if (nameId.equalsIgnoreCase("$1854")) {
/*     */         
/* 272 */         skillid = 166;
/*     */         
/* 274 */         magicLv = 15;
/*     */       }
/* 276 */       else if (nameId.equalsIgnoreCase("$1855")) {
/*     */         
/* 278 */         skillid = 167;
/*     */         
/* 280 */         magicLv = 15;
/*     */       }
/* 282 */       else if (nameId.equalsIgnoreCase("$4718")) {
/*     */         
/* 284 */         skillid = 174;
/*     */         
/* 286 */         magicLv = 15;
/*     */       } 
/*    */     
///*判斷地點學習*/ }
/* 40 */																		
/*判斷地點學習*///	else
/*判斷地點學習*///pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\f=不能在這個地方使用"));
/* 288 */       Skill_Check.check(pc, item, skillid, magicLv, 3);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\skill\Skill_SpiritCrystal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */