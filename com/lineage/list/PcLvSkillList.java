/*     */ package com.lineage.list;
/*     */ 
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import java.util.ArrayList;
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
/*     */ public class PcLvSkillList
/*     */ {
/*     */   public static ArrayList<Integer> scount(L1PcInstance pc) {
/*  18 */     ArrayList<Integer> skillList = new ArrayList<>();
/*  19 */     switch (pc.getType()) {
/*     */       case 0:
/*  21 */         if (pc.getLevel() >= 10) {
/*  22 */           for (int skillid = 0; skillid < 8; skillid++) {
/*  23 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         
/*  27 */         if (pc.getLevel() >= 20) {
/*  28 */           for (int skillid = 8; skillid < 16; skillid++) {
/*  29 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         break;
/*     */       
/*     */       case 1:
/*  35 */         if (pc.getLevel() >= 50) {
/*  36 */           for (int skillid = 0; skillid < 8; skillid++) {
/*  37 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         break;
/*     */       
/*     */       case 2:
/*  43 */         if (pc.getLevel() >= 8) {
/*  44 */           for (int skillid = 0; skillid < 8; skillid++) {
/*  45 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*  48 */         if (pc.getLevel() >= 16) {
/*  49 */           for (int skillid = 8; skillid < 16; skillid++) {
/*  50 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*  53 */         if (pc.getLevel() >= 24) {
/*  54 */           for (int skillid = 16; skillid < 23; skillid++) {
/*  55 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         break;
/*     */       
/*     */       case 3:
/*  61 */         if (pc.getLevel() >= 4) {
/*  62 */           for (int skillid = 0; skillid < 8; skillid++) {
/*  63 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*  66 */         if (pc.getLevel() >= 8) {
/*  67 */           for (int skillid = 8; skillid < 16; skillid++) {
/*  68 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*  71 */         if (pc.getLevel() >= 12) {
/*  72 */           for (int skillid = 16; skillid < 23; skillid++) {
/*  73 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         break;
/*     */       
/*     */       case 4:
/*  79 */         if (pc.getLevel() >= 12) {
/*  80 */           for (int skillid = 0; skillid < 8; skillid++) {
/*  81 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*  84 */         if (pc.getLevel() >= 24) {
/*  85 */           for (int skillid = 8; skillid < 16; skillid++) {
/*  86 */             skillList.add(new Integer(skillid));
/*     */           }
/*     */         }
/*     */         break;
/*     */     } 
/*  91 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isIllusionist(L1PcInstance pc) {
/*  99 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 100 */     if (pc.getLevel() >= 10) {
/* 101 */       for (int skillid = 200; skillid < 208; skillid++) {
/* 102 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 105 */     if (pc.getLevel() >= 20) {
/* 106 */       for (int skillid = 208; skillid < 212; skillid++) {
/* 107 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 110 */     if (pc.getLevel() >= 30) {
/* 111 */       for (int skillid = 212; skillid < 216; skillid++) {
/* 112 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 115 */     if (pc.getLevel() >= 40) {
/* 116 */       for (int skillid = 216; skillid < 220; skillid++) {
/* 117 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 120 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isDragonKnight(L1PcInstance pc) {
/* 128 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 129 */     if (pc.getLevel() >= 15) {
/* 130 */       for (int skillid = 180; skillid < 184; skillid++) {
/* 131 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 134 */     if (pc.getLevel() >= 30) {
/* 135 */       for (int skillid = 184; skillid < 192; skillid++) {
/* 136 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 139 */     if (pc.getLevel() >= 45) {
/* 140 */       for (int skillid = 192; skillid < 195; skillid++) {
/* 141 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 144 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isDarkelf(L1PcInstance pc) {
/* 152 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 153 */     if (pc.getLevel() >= 12) {
/* 154 */       for (int skillid = 0; skillid < 8; skillid++) {
/* 155 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 158 */     if (pc.getLevel() >= 24) {
/* 159 */       for (int skillid = 8; skillid < 16; skillid++) {
/* 160 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 163 */     if (pc.getLevel() >= 15) {
/* 164 */       for (int skillid = 96; skillid < 100; skillid++) {
/* 165 */         skillList.add(new Integer(skillid));
/*     */       }
/* 167 */       skillList.add(new Integer(108));
/*     */     } 
/* 169 */     if (pc.getLevel() >= 30) {
/* 170 */       for (int skillid = 100; skillid < 104; skillid++) {
/* 171 */         skillList.add(new Integer(skillid));
/*     */       }
/* 173 */       skillList.add(new Integer(109));
/*     */     } 
/* 175 */     if (pc.getLevel() >= 45) {
/* 176 */       for (int skillid = 104; skillid < 108; skillid++) {
/* 177 */         skillList.add(new Integer(skillid));
/*     */       }
/* 179 */       skillList.add(new Integer(110));
/*     */     } 
/* 181 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isWizard(L1PcInstance pc) {
/* 189 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 190 */     if (pc.getLevel() >= 4) {
/* 191 */       for (int skillid = 0; skillid < 8; skillid++) {
/* 192 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 195 */     if (pc.getLevel() >= 8) {
/* 196 */       for (int skillid = 8; skillid < 16; skillid++) {
/* 197 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 200 */     if (pc.getLevel() >= 12) {
/* 201 */       for (int skillid = 16; skillid < 23; skillid++) {
/* 202 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 205 */     if (pc.getLevel() >= 16) {
/* 206 */       for (int skillid = 24; skillid < 32; skillid++) {
/* 207 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 210 */     if (pc.getLevel() >= 20) {
/* 211 */       for (int skillid = 32; skillid < 40; skillid++) {
/* 212 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 215 */     if (pc.getLevel() >= 24) {
/* 216 */       for (int skillid = 40; skillid < 48; skillid++) {
/* 217 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 220 */     if (pc.getLevel() >= 28) {
/* 221 */       for (int skillid = 48; skillid < 56; skillid++) {
/* 222 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 225 */     if (pc.getLevel() >= 32) {
/* 226 */       for (int skillid = 56; skillid < 64; skillid++) {
/* 227 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 230 */     if (pc.getLevel() >= 36) {
/* 231 */       for (int skillid = 64; skillid < 72; skillid++) {
/* 232 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 235 */     if (pc.getLevel() >= 40) {
/* 236 */       for (int skillid = 72; skillid < 80; skillid++) {
/* 237 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 240 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isElf(L1PcInstance pc) {
/* 248 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 249 */     if (pc.getLevel() >= 8) {
/* 250 */       for (int skillid = 0; skillid < 8; skillid++) {
/* 251 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 254 */     if (pc.getLevel() >= 16) {
/* 255 */       for (int skillid = 8; skillid < 16; skillid++) {
/* 256 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 259 */     if (pc.getLevel() >= 24) {
/* 260 */       for (int skillid = 16; skillid < 23; skillid++) {
/* 261 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 264 */     if (pc.getLevel() >= 32) {
/* 265 */       for (int skillid = 24; skillid < 32; skillid++) {
/* 266 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 269 */     if (pc.getLevel() >= 40) {
/* 270 */       for (int skillid = 32; skillid < 40; skillid++) {
/* 271 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 274 */     if (pc.getLevel() >= 48) {
/* 275 */       for (int skillid = 40; skillid < 48; skillid++) {
/* 276 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/*     */     
/* 280 */     if (pc.getLevel() >= 10) {
/* 281 */       skillList.add(new Integer(129));
/* 282 */       skillList.add(new Integer(130));
/* 283 */       skillList.add(new Integer(131));
/*     */     } 
/* 285 */     if (pc.getLevel() >= 20) {
/* 286 */       skillList.add(new Integer(137));
/* 287 */       skillList.add(new Integer(138));
/*     */     } 
/* 289 */     if (pc.getLevel() >= 30) {
/* 290 */       skillList.add(new Integer(132));
/* 291 */       skillList.add(new Integer(145));
/* 292 */       skillList.add(new Integer(146));
/* 293 */       skillList.add(new Integer(147));
/*     */       
/* 295 */       switch (pc.getElfAttr()) {
/*     */         case 1:
/* 297 */           skillList.add(new Integer(151));
/* 298 */           skillList.add(new Integer(152));
/*     */           break;
/*     */         
/*     */         case 2:
/* 302 */           skillList.add(new Integer(148));
/*     */           break;
/*     */         
/*     */         case 4:
/* 306 */           skillList.add(new Integer(170));
/*     */           break;
/*     */         
/*     */         case 8:
/* 310 */           skillList.add(new Integer(149));
/* 311 */           skillList.add(new Integer(150));
/*     */           break;
/*     */       } 
/*     */     } 
/* 315 */     if (pc.getLevel() >= 40) {
/* 316 */       skillList.add(new Integer(133));
/* 317 */       skillList.add(new Integer(153));
/* 318 */       skillList.add(new Integer(154));
/*     */       
/* 320 */       switch (pc.getElfAttr()) {
/*     */         case 1:
/* 322 */           skillList.add(new Integer(157));
/* 323 */           skillList.add(new Integer(159));
/*     */           break;
/*     */         
/*     */         case 2:
/* 327 */           skillList.add(new Integer(155));
/*     */           break;
/*     */         
/*     */         case 4:
/* 331 */           skillList.add(new Integer(158));
/* 332 */           skillList.add(new Integer(160));
/*     */           break;
/*     */         
/*     */         case 8:
/* 336 */           skillList.add(new Integer(156));
/*     */           break;
/*     */       } 
/*     */     } 
/* 340 */     if (pc.getLevel() >= 50) {
/* 341 */       skillList.add(new Integer(134));
/* 342 */       skillList.add(new Integer(161));
/* 343 */       skillList.add(new Integer(162));
/*     */       
/* 345 */       switch (pc.getElfAttr()) {
/*     */         case 1:
/* 347 */           skillList.add(new Integer(168));
/* 348 */           skillList.add(new Integer(169));
/*     */           break;
/*     */         
/*     */         case 2:
/* 352 */           skillList.add(new Integer(163));
/* 353 */           skillList.add(new Integer(171));
/* 354 */           skillList.add(new Integer(175));
/* 355 */           skillList.add(new Integer(176));
/*     */           break;
/*     */         
/*     */         case 4:
/* 359 */           skillList.add(new Integer(164));
/* 360 */           skillList.add(new Integer(165));
/* 361 */           skillList.add(new Integer(173));
/*     */           break;
/*     */         
/*     */         case 8:
/* 365 */           skillList.add(new Integer(166));
/* 366 */           skillList.add(new Integer(167));
/* 367 */           skillList.add(new Integer(174));
/*     */           break;
/*     */       } 
/*     */     } 
/* 371 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isKnight(L1PcInstance pc) {
/* 379 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 380 */     if (pc.getLevel() >= 50) {
/* 381 */       for (int skillid = 0; skillid < 8; skillid++) {
/* 382 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 385 */     if (pc.getLevel() >= 50) {
/* 386 */       skillList.add(new Integer(86));
/* 387 */       skillList.add(new Integer(87));
/* 388 */       skillList.add(new Integer(89));
/* 389 */       skillList.add(new Integer(90));
/*     */     } 
/* 391 */     if (pc.getLevel() >= 60) {
/* 392 */       skillList.add(new Integer(89));
/*     */     }
/*     */     
/* 395 */     return skillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Integer> isCrown(L1PcInstance pc) {
/* 403 */     ArrayList<Integer> skillList = new ArrayList<>();
/* 404 */     if (pc.getLevel() >= 10) {
/* 405 */       for (int skillid = 0; skillid < 8; skillid++) {
/* 406 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/*     */     
/* 410 */     if (pc.getLevel() >= 20) {
/* 411 */       for (int skillid = 8; skillid < 16; skillid++) {
/* 412 */         skillList.add(new Integer(skillid));
/*     */       }
/*     */     }
/* 415 */     if (pc.getLevel() >= 15) {
/* 416 */       skillList.add(new Integer(112));
/*     */     }
/* 418 */     if (pc.getLevel() >= 30) {
/* 419 */       skillList.add(new Integer(115));
/*     */     }
/* 421 */     if (pc.getLevel() >= 40) {
/* 422 */       skillList.add(new Integer(113));
/*     */     }
/* 424 */     if (pc.getLevel() >= 45) {
/* 425 */       skillList.add(new Integer(117));
/*     */     }
/* 427 */     if (pc.getLevel() >= 50) {
/* 428 */       skillList.add(new Integer(116));
/*     */     }
/* 430 */     if (pc.getLevel() >= 55) {
/* 431 */       skillList.add(new Integer(114));
/*     */     }
/* 433 */     return skillList;
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\list\PcLvSkillList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */