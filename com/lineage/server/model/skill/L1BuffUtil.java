/*     */ package com.lineage.server.model.skill;
/*     */ 
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1Character;
/*     */ import com.lineage.server.serverpackets.S_ChangeShape;
/*     */ import com.lineage.server.serverpackets.S_CharVisualUpdate;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SkillBrave;
/*     */ import com.lineage.server.serverpackets.S_SkillHaste;
/*     */ import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class L1BuffUtil {
/*  17 */   private static final Log _log = LogFactory.getLog(L1BuffUtil.class);
/*     */   
/*     */   public static boolean stopPotion(L1PcInstance paramL1PcInstance) {
/*  20 */     if (paramL1PcInstance.is_decay_potion()) {
/*  21 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage(698));
/*  22 */       return false;
/*     */     } 
/*  24 */     return true;
/*     */   }
/*     */   
/*     */   public static void cancelAbsoluteBarrier(L1PcInstance paramL1PcInstance) {
/*  28 */     if (paramL1PcInstance.hasSkillEffect(78)) {
/*  29 */       paramL1PcInstance.killSkillEffectTimer(78);
/*  30 */       paramL1PcInstance.startHpRegeneration();
/*  31 */       paramL1PcInstance.startMpRegeneration();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int cancelBaphomet(L1PcInstance paramL1PcInstance) {
/*  36 */     if (paramL1PcInstance.hasSkillEffect(40001))
/*  37 */       return paramL1PcInstance.getSkillEffectTimeSec(40001); 
/*  38 */     return -1;
/*     */   }
/*     */   
/*     */   public static void hasteStart(L1PcInstance paramL1PcInstance) {
/*     */     try {
/*  43 */       if (paramL1PcInstance.hasSkillEffect(43)) {
/*  44 */         paramL1PcInstance.killSkillEffectTimer(43);
/*  45 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillHaste(paramL1PcInstance.getId(), 0, 0));
/*  46 */         paramL1PcInstance.setMoveSpeed(0);
/*     */       } 
/*  48 */       if (paramL1PcInstance.hasSkillEffect(54)) {
/*  49 */         paramL1PcInstance.killSkillEffectTimer(54);
/*  50 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillHaste(paramL1PcInstance.getId(), 0, 0));
/*  51 */         paramL1PcInstance.setMoveSpeed(0);
/*     */       } 
/*  53 */       if (paramL1PcInstance.hasSkillEffect(1001)) {
/*  54 */         paramL1PcInstance.killSkillEffectTimer(1001);
/*  55 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillHaste(paramL1PcInstance.getId(), 0, 0));
/*  56 */         paramL1PcInstance.setMoveSpeed(0);
/*     */       } 
/*  58 */     } catch (Exception exception) {
/*  59 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void haste(L1PcInstance paramL1PcInstance, int paramInt) {
/*     */     try {
/*  65 */       hasteStart(paramL1PcInstance);
/*  66 */       paramL1PcInstance.setSkillEffect(1001, paramInt);
/*  67 */       int i = paramL1PcInstance.getId();
/*  68 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_SkillHaste(i, 1, paramInt / 1000));
/*  69 */       paramL1PcInstance.broadcastPacketAll((ServerBasePacket)new S_SkillHaste(i, 1, 0));
/*  70 */       paramL1PcInstance.sendPacketsX8((ServerBasePacket)new S_SkillSound(i, 191));
/*  71 */       paramL1PcInstance.setMoveSpeed(1);
/*  72 */     } catch (Exception exception) {
/*  73 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void braveStart(L1PcInstance paramL1PcInstance) {
/*     */     try {
/*  79 */       if (paramL1PcInstance.hasSkillEffect(52)) {
/*  80 */         paramL1PcInstance.killSkillEffectTimer(52);
/*  81 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/*  82 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/*  84 */       if (paramL1PcInstance.hasSkillEffect(101)) {
/*  85 */         paramL1PcInstance.killSkillEffectTimer(101);
/*  86 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/*  87 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/*  89 */       if (paramL1PcInstance.hasSkillEffect(150)) {
/*  90 */         paramL1PcInstance.killSkillEffectTimer(150);
/*  91 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/*  92 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/*  94 */       if (paramL1PcInstance.hasSkillEffect(1000)) {
/*  95 */         paramL1PcInstance.killSkillEffectTimer(1000);
/*  96 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/*  97 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/*  99 */       if (paramL1PcInstance.hasSkillEffect(1016)) {
/* 100 */         paramL1PcInstance.killSkillEffectTimer(1016);
/* 101 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/* 102 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/* 104 */       if (paramL1PcInstance.hasSkillEffect(1017)) {
/* 105 */         paramL1PcInstance.killSkillEffectTimer(1017);
/* 106 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/* 107 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/* 109 */       if (paramL1PcInstance.hasSkillEffect(186)) {
/* 110 */         paramL1PcInstance.killSkillEffectTimer(186);
/* 111 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/* 112 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/* 114 */       if (paramL1PcInstance.hasSkillEffect(155)) {
/* 115 */         paramL1PcInstance.killSkillEffectTimer(155);
/* 116 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_SkillBrave(paramL1PcInstance.getId(), 0, 0));
/* 117 */         paramL1PcInstance.setBraveSpeed(0);
/*     */       } 
/* 119 */     } catch (Exception exception) {
/* 120 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void brave(L1PcInstance paramL1PcInstance, int paramInt) {
/*     */     try {
/* 126 */       braveStart(paramL1PcInstance);
/* 127 */       paramL1PcInstance.setSkillEffect(1000, paramInt);
/* 128 */       int i = paramL1PcInstance.getId();
/* 129 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_SkillBrave(i, 1, paramInt / 1000));
/* 130 */       paramL1PcInstance.broadcastPacketAll((ServerBasePacket)new S_SkillBrave(i, 1, 0));
/* 131 */       paramL1PcInstance.sendPacketsX8((ServerBasePacket)new S_SkillSound(i, 751));
/* 132 */       paramL1PcInstance.setBraveSpeed(1);
/* 133 */     } catch (Exception exception) {
/* 134 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void doPoly(L1PcInstance paramL1PcInstance) {
/*     */     try {
/* 140 */       int i = paramL1PcInstance.getAwakeSkillId();
/* 141 */       char c = Character.MIN_VALUE;
/* 142 */       switch (i) {
/*     */         case 185:
/* 144 */           c = '⒒';
/*     */           break;
/*     */         case 190:
/* 147 */           c = '⒔';
/*     */           break;
/*     */         case 195:
/* 150 */           c = '⒓';
/*     */           break;
/*     */       } 
/* 153 */       if (paramL1PcInstance.hasSkillEffect(67))
/* 154 */         paramL1PcInstance.killSkillEffectTimer(67); 
/* 155 */       paramL1PcInstance.setTempCharGfx(c);
/* 156 */       paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_ChangeShape((L1Character)paramL1PcInstance, c));
/* 157 */       L1ItemInstance l1ItemInstance = paramL1PcInstance.getWeapon();
/* 158 */       if (l1ItemInstance != null)
/* 159 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_CharVisualUpdate(paramL1PcInstance)); 
/* 160 */     } catch (Exception exception) {
/* 161 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void undoPoly(L1PcInstance paramL1PcInstance) {
/*     */     try {
/* 167 */       int i = paramL1PcInstance.getClassId();
/* 168 */       paramL1PcInstance.setTempCharGfx(i);
/* 169 */       paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_ChangeShape((L1Character)paramL1PcInstance, i));
/* 170 */       L1ItemInstance l1ItemInstance = paramL1PcInstance.getWeapon();
/* 171 */       if (l1ItemInstance != null)
/* 172 */         paramL1PcInstance.sendPacketsAll((ServerBasePacket)new S_CharVisualUpdate(paramL1PcInstance)); 
/* 173 */     } catch (Exception exception) {
/* 174 */       _log.error(exception.getLocalizedMessage(), exception);
/*     */     } 
/*     */   }
/*     */   public static boolean cancelMazu(L1PcInstance paramL1PcInstance) {
/* 43 */       if (paramL1PcInstance.hasSkillEffect(8591)) {
/* 44 */         int i = paramL1PcInstance.getSkillEffectTimeSec(8591);
				 paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX媽祖的祝福效果 剩餘時間(秒):" + i));
/*    */         return false;
				} 
				return true;     } 


/*     */   public static boolean cancelExpSkill(L1PcInstance paramL1PcInstance) {
/* 179 */     if (paramL1PcInstance.hasSkillEffect(6666)) {
/* 180 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6666);
/* 181 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水130% 剩餘時間(秒):" + i));
/* 182 */       return false;
/*     */     } 
/* 184 */     if (paramL1PcInstance.hasSkillEffect(6667)) {
/* 185 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6667);
/* 186 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水150% 剩餘時間(秒):" + i));
/* 187 */       return false;
/*     */     } 
/* 189 */     if (paramL1PcInstance.hasSkillEffect(6668)) {
/* 190 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6668);
/* 191 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水170% 剩餘時間(秒):" + i));
/* 192 */       return false;
/*     */     } 
/* 194 */     if (paramL1PcInstance.hasSkillEffect(6669)) {
/* 195 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6669);
/* 196 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水200% 剩餘時間(秒):" + i));
/* 197 */       return false;
/*     */     } 
/* 199 */     if (paramL1PcInstance.hasSkillEffect(6670)) {
/* 200 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6670);
/* 201 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水250% 剩餘時間(秒):" + i));
/* 202 */       return false;
/*     */     } 
/* 204 */     if (paramL1PcInstance.hasSkillEffect(6671)) {
/* 205 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6671);
/* 206 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水300% 剩餘時間(秒):" + i));
/* 207 */       return false;
/*     */     } 
/* 209 */     if (paramL1PcInstance.hasSkillEffect(6672)) {
/* 210 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6672);
/* 211 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水350% 剩餘時間(秒):" + i));
/* 212 */       return false;
/*     */     } 
/* 214 */     if (paramL1PcInstance.hasSkillEffect(6673)) {
/* 215 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6673);
/* 216 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水400% 剩餘時間(秒):" + i));
/* 217 */       return false;
/*     */     } 
/* 219 */     if (paramL1PcInstance.hasSkillEffect(6674)) {
/* 220 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6674);
/* 221 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水450% 剩餘時間(秒):" + i));
/* 222 */       return false;
/*     */     } 
/* 224 */     if (paramL1PcInstance.hasSkillEffect(6675)) {
/* 225 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6675);
/* 226 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水500% 剩餘時間(秒):" + i));
/* 227 */       return false;
/*     */     } 
/* 229 */     if (paramL1PcInstance.hasSkillEffect(6676)) {
/* 230 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6676);
/* 231 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水550% 剩餘時間(秒):" + i));
/* 232 */       return false;
/*     */     } 
/* 234 */     if (paramL1PcInstance.hasSkillEffect(6677)) {
/* 235 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6677);
/* 236 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水600% 剩餘時間(秒):" + i));
/* 237 */       return false;
/*     */     } 
/* 239 */     if (paramL1PcInstance.hasSkillEffect(6678)) {
/* 240 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6678);
/* 241 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水650% 剩餘時間(秒):" + i));
/* 242 */       return false;
/*     */     } 
/* 244 */     if (paramL1PcInstance.hasSkillEffect(6679)) {
/* 245 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6679);
/* 246 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水700% 剩餘時間(秒):" + i));
/* 247 */       return false;
/*     */     } 
/* 249 */     if (paramL1PcInstance.hasSkillEffect(6680)) {
/* 250 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6680);
/* 251 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水750% 剩餘時間(秒):" + i));
/* 252 */       return false;
/*     */     } 
/* 254 */     if (paramL1PcInstance.hasSkillEffect(6681)) {
/* 255 */       int i = paramL1PcInstance.getSkillEffectTimeSec(6681);
/* 256 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fX第一段經驗藥水800% 剩餘時間(秒):" + i));
/* 257 */       return false;
/*     */     } 
/* 259 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean cancelExpSkill_2(L1PcInstance paramL1PcInstance) {
/* 263 */     if (paramL1PcInstance.hasSkillEffect(5000)) {
/* 264 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5000);
/* 265 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水150% 剩餘時間(秒):" + i));
/* 266 */       return false;
/*     */     } 
/* 268 */     if (paramL1PcInstance.hasSkillEffect(5001)) {
/* 269 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5001);
/* 270 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水175% 剩餘時間(秒):" + i));
/* 271 */       return false;
/*     */     } 
/* 273 */     if (paramL1PcInstance.hasSkillEffect(5002)) {
/* 274 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5002);
/* 275 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水200% 剩餘時間(秒):" + i));
/* 276 */       return false;
/*     */     } 
/* 278 */     if (paramL1PcInstance.hasSkillEffect(5003)) {
/* 279 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5003);
/* 280 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水650% 剩餘時間(秒):" + i));
/* 281 */       return false;
/*     */     } 
/* 283 */     if (paramL1PcInstance.hasSkillEffect(5004)) {
/* 284 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5004);
/* 285 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水250% 剩餘時間(秒):" + i));
/* 286 */       return false;
/*     */     } 
/* 288 */     if (paramL1PcInstance.hasSkillEffect(5005)) {
/* 289 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5005);
/* 290 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水700% 剩餘時間(秒):" + i));
/* 291 */       return false;
/*     */     } 
/* 293 */     if (paramL1PcInstance.hasSkillEffect(5006)) {
/* 294 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5006);
/* 295 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水300% 剩餘時間(秒):" + i));
/* 296 */       return false;
/*     */     } 
/* 298 */     if (paramL1PcInstance.hasSkillEffect(5007)) {
/* 299 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5007);
/* 300 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水750% 剩餘時間(秒):" + i));
/* 301 */       return false;
/*     */     } 
/* 303 */     if (paramL1PcInstance.hasSkillEffect(5008)) {
/* 304 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5008);
/* 305 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水350% 剩餘時間(秒):" + i));
/* 306 */       return false;
/*     */     } 
/* 308 */     if (paramL1PcInstance.hasSkillEffect(5009)) {
/* 309 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5009);
/* 310 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水800% 剩餘時間(秒):" + i));
/* 311 */       return false;
/*     */     } 
/* 313 */     if (paramL1PcInstance.hasSkillEffect(5010)) {
/* 314 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5010);
/* 315 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水400% 剩餘時間(秒):" + i));
/* 316 */       return false;
/*     */     } 
/* 318 */     if (paramL1PcInstance.hasSkillEffect(5011)) {
/* 319 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5011);
/* 320 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水450% 剩餘時間(秒):" + i));
/* 321 */       return false;
/*     */     } 
/* 323 */     if (paramL1PcInstance.hasSkillEffect(5012)) {
/* 324 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5012);
/* 325 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水500% 剩餘時間(秒):" + i));
/* 326 */       return false;
/*     */     } 
/* 328 */     if (paramL1PcInstance.hasSkillEffect(5013)) {
/* 329 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5013);
/* 330 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水550% 剩餘時間(秒):" + i));
/* 331 */       return false;
/*     */     } 
/* 333 */     if (paramL1PcInstance.hasSkillEffect(5014)) {
/* 334 */       int i = paramL1PcInstance.getSkillEffectTimeSec(5014);
/* 335 */       paramL1PcInstance.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY第二段神力藥水600% 剩餘時間(秒):" + i));
/* 336 */       return false;
/*     */     } 
/* 338 */     return true;
/*     */   }
/*     */   
/*     */   public static int cancelDragon(L1PcInstance paramL1PcInstance) {
/* 342 */     if (paramL1PcInstance.hasSkillEffect(6683))
/* 343 */       return paramL1PcInstance.getSkillEffectTimeSec(6683); 
/* 344 */     if (paramL1PcInstance.hasSkillEffect(6684))
/* 345 */       return paramL1PcInstance.getSkillEffectTimeSec(6684); 
/* 346 */     if (paramL1PcInstance.hasSkillEffect(6685))
/* 347 */       return paramL1PcInstance.getSkillEffectTimeSec(6685); 
/* 348 */     if (paramL1PcInstance.hasSkillEffect(6686))
/* 349 */       return paramL1PcInstance.getSkillEffectTimeSec(6686); 
/* 350 */     if (paramL1PcInstance.hasSkillEffect(6687))
/* 351 */       return paramL1PcInstance.getSkillEffectTimeSec(6687); 
/* 352 */     if (paramL1PcInstance.hasSkillEffect(6688))
/* 353 */       return paramL1PcInstance.getSkillEffectTimeSec(6688); 
/* 354 */     if (paramL1PcInstance.hasSkillEffect(6689))
/* 355 */       return paramL1PcInstance.getSkillEffectTimeSec(6689); 
/* 356 */     return -1;
/*     */   }
/*     */   
/*     */   public static void cancelBuffStone(L1PcInstance paramL1PcInstance) {
/* 360 */     int[] arrayOfInt = { 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409, 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419, 4421, 4422, 4423, 4424, 4425, 4426, 4427, 4428, 4429, 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, 4439 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 368 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 369 */       if (paramL1PcInstance.hasSkillEffect(arrayOfInt[b]))
/* 370 */         paramL1PcInstance.killSkillEffectTimer(arrayOfInt[b]); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int cancelDragonSign1(L1PcInstance paramL1PcInstance) {
/* 375 */     int[] arrayOfInt = { 4401, 4402, 4403, 4404, 4405, 4406, 4407, 4408, 4409, 4411, 4412, 4413, 4414, 4415, 4416, 4417, 4418, 4419, 4421, 4422, 4423, 4424, 4425, 4426, 4427, 4428, 4429, 4431, 4432, 4433, 4434, 4435, 4436, 4437, 4438, 4439 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 384 */       if (paramL1PcInstance.hasSkillEffect(arrayOfInt[b]))
/* 385 */         return paramL1PcInstance.getSkillEffectTimeSec(arrayOfInt[b]); 
/*     */     } 
/* 387 */     return -1;
/*     */   }
/*     */   
/*     */   public static int cancelDragonSign(L1PcInstance paramL1PcInstance) {
/* 391 */     int[] arrayOfInt = { 4500, 4501, 4502, 4503, 4504, 4505, 4506, 4507, 4508, 4509, 4510, 4511, 4512, 4513, 4514, 4515, 4516, 4517, 4518, 4519, 4520, 4521, 4522, 4523, 4524, 4525, 4526, 4527, 4528, 4529, 4530, 4531, 4532, 4533, 4534, 4535, 4536, 4537, 4538, 4539 };
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     for (byte b = 0; b < arrayOfInt.length; b++) {
/* 397 */       if (paramL1PcInstance.hasSkillEffect(arrayOfInt[b]))
/* 398 */         return paramL1PcInstance.getSkillEffectTimeSec(arrayOfInt[b]); 
/*     */     } 
/* 400 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean getUseItemTeleport(L1PcInstance paramL1PcInstance) {
/* 404 */     if (paramL1PcInstance.hasSkillEffect(157))
/* 405 */       return false; 
/* 406 */     if (paramL1PcInstance.hasSkillEffect(189))
/* 407 */       return false; 
/* 408 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\server\model\skill\L1BuffUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */