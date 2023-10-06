/*     */ package com.lineage.server.model;
/*     */ 
/*     */ import com.lineage.server.datatables.ExtraAttrWeaponTable;
/*     */ import com.lineage.server.model.Instance.L1DollInstance;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1MonsterInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CurseBlind;
/*     */ import com.lineage.server.serverpackets.S_Paralysis;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1AttrWeapon;
/*     */ import com.lineage.server.utils.L1SpawnUtil;
/*     */ import com.lineage.server.world.World;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class L1AttackPower
/*     */ {
/*  28 */   private static final Log _log = LogFactory.getLog(L1AttackPower.class);
/*     */   
/*  30 */   private static final Random _random = new Random();
/*     */   
/*     */   private final L1PcInstance _pc;
/*     */   private final L1Character _target;
/*     */   private L1PcInstance _targetPc;
/*     */   private L1NpcInstance _targetNpc;
/*     */   private int _weaponAttrEnchantKind;
/*     */   private int _weaponAttrEnchantLevel;
/*     */   
/*     */   public L1AttackPower(L1PcInstance attacker, L1Character target, int weaponAttrEnchantKind, int weaponAttrEnchantLevel) {
/*  40 */     this._pc = attacker;
/*  41 */     this._target = target;
/*  42 */     if (this._target instanceof L1NpcInstance) {
/*  43 */       this._targetNpc = (L1NpcInstance)this._target;
/*     */     }
/*  45 */     else if (this._target instanceof L1PcInstance) {
/*  46 */       this._targetPc = (L1PcInstance)this._target;
/*     */     } 
/*  48 */     this._weaponAttrEnchantKind = weaponAttrEnchantKind;
/*  49 */     this._weaponAttrEnchantLevel = weaponAttrEnchantLevel;
/*     */   }
/*     */   
/*     */   public void AttackPower1(int weaponAttrEnchantKind, int weaponAttrEnchantLevel) {
/*  53 */     this._weaponAttrEnchantKind = weaponAttrEnchantKind;
/*  54 */     this._weaponAttrEnchantLevel = weaponAttrEnchantLevel;
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
/*     */   public int set_item_power(int damage) {
/*  78 */     int reset_dmg = damage;
/*     */     try {
/*  80 */       if (this._weaponAttrEnchantKind > 0) {
/*  81 */         L1AttrWeapon attrWeapon = ExtraAttrWeaponTable.getInstance().get(this._weaponAttrEnchantKind, this._weaponAttrEnchantLevel);
/*  82 */         if (attrWeapon == null) {
/*  83 */           return damage;
/*     */         }
/*     */         
/*  86 */         int skillfor3 = this._pc.getlogpcpower_SkillFor3() * 10;
/*     */         
/*  88 */         if (attrWeapon.getProbability() + skillfor3 >= _random.nextInt(100) + 1) {
/*     */           
/*  90 */           if (this._targetPc != null && 
/*  91 */             this._targetPc.getInventory().consumeItem(44073, 1L)) {
/*  92 */             this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("成功抵抗屬性能力的發動"));
/*  93 */             return damage;
/*     */           } 
/*     */           
/*  96 */           if (attrWeapon.getTypeBind() > 0.0D && 
/*  97 */             !L1WeaponSkill.isFreeze(this._target)) {
/*  98 */             int time = (int)(attrWeapon.getTypeBind() * 1000.0D);
/*  99 */             this._target.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/* 100 */             if (this._targetPc != null) {
/*     */               
/* 102 */               this._targetPc.setSkillEffect(14009, time);
/* 103 */               this._targetPc.sendPackets((ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/* 104 */               this._targetPc.sendPackets((ServerBasePacket)new S_Paralysis(6, true));
/*     */             }
/* 106 */             else if (this._targetNpc != null && 
/* 107 */               !this._targetNpc.getNpcTemplate().is_boss()) {
/* 108 */               this._targetNpc.setSkillEffect(4000, time);
/*     */               
/* 110 */               this._targetNpc.setPassispeed(0);
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 119 */           if (attrWeapon.getTypeDrainHp() > 0.0D) {
/* 120 */             int drainHp = 1 + _random.nextInt((int)attrWeapon.getTypeDrainHp());
/*     */             
/* 122 */             S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 123 */             this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/* 124 */             this._pc.setCurrentHp((short)(this._pc.getCurrentHp() + drainHp));
/* 125 */             if (this._targetPc != null) {
/* 126 */               this._targetPc.setCurrentHp(Math.max(this._targetPc.getCurrentHp() - drainHp, 0));
/*     */             }
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 133 */           if (attrWeapon.getTypeDrainMp() > 0) {
/* 134 */             int drainMp = 1 + _random.nextInt(attrWeapon.getTypeDrainMp());
/* 135 */             S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 136 */             this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/*     */             
/* 138 */             if (this._targetPc != null) {
/* 139 */               if (this._targetPc.getCurrentMp() > drainMp) {
/* 140 */                 this._pc.setCurrentMp((short)(this._pc.getCurrentMp() + drainMp));
/*     */                 
/* 142 */                 this._targetPc.setCurrentMp((short)(this._targetPc.getCurrentMp() - drainMp));
/*     */               } 
/* 144 */             } else if (this._targetNpc != null && 
/* 145 */               this._targetNpc.getCurrentMp() > drainMp) {
/* 146 */               this._pc.setCurrentMp((short)(this._pc.getCurrentMp() + drainMp));
/* 147 */               this._targetNpc.setCurrentMp((short)(this._targetNpc.getCurrentMp() - drainMp));
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 152 */           if (attrWeapon.getTypeDmgup() > 0.0D) {
/*     */             
/* 154 */             S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 155 */             this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/* 156 */             reset_dmg = (int)(reset_dmg * attrWeapon.getTypeDmgup());
/*     */           } 
/*     */           
/* 159 */           if (attrWeapon.getTypeRange() > 0 && attrWeapon.getTypeRangeDmg() > 0) {
/*     */             
/* 161 */             S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 162 */             this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/* 163 */             int dmg = attrWeapon.getTypeRangeDmg() + _random.nextInt(30);
/*     */             
/* 165 */             if (this._targetPc != null) {
/* 166 */               Iterator<?> localIterator1 = World.get()
/* 167 */                 .getVisibleObjects((L1Object)this._targetPc, attrWeapon.getTypeRange()).iterator();
/*     */               
/* 169 */               while (localIterator1.hasNext()) {
/* 170 */                 L1Object tgobj = (L1Object)localIterator1.next();
/* 171 */                 if (tgobj instanceof L1PcInstance) {
/* 172 */                   L1PcInstance tgpc = (L1PcInstance)tgobj;
/* 173 */                   if (!tgpc.isDead())
/*     */                   {
/* 175 */                     if (tgpc.getClanid() != this._pc.getClanid() || 
/* 176 */                       tgpc.getClanid() == 0)
/*     */                     {
/* 178 */                       if (!tgpc.getMap().isSafetyZone(tgpc.getLocation())) {
/* 179 */                         tgpc.receiveDamage((L1Character)this._pc, dmg, false, false);
///* 180 */                         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("該0101"));
/*     */                       }
/*     */                     
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               
/*     */               } 
/* 188 */             } else if (this._targetNpc != null) {
/*     */               
/* 190 */               Iterator<?> localIterator1 = World.get()
/* 191 */                 .getVisibleObjects((L1Object)this._targetNpc, attrWeapon.getTypeRange()).iterator();
/*     */               
/* 193 */               while (localIterator1.hasNext()) {
/* 194 */                 L1Object tgobj = (L1Object)localIterator1.next();
/* 195 */                 if (tgobj instanceof L1MonsterInstance) {
/* 196 */                   L1MonsterInstance tgmob = (L1MonsterInstance)tgobj;
/* 197 */                   if (!tgmob.isDead()) {
/* 198 */                     tgmob.receiveDamage((L1Character)this._pc, dmg);
///* 199 */                     this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("該0202"));
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 206 */           if (attrWeapon.getfix_damage() > 0) {
/* 207 */             reset_dmg += attrWeapon.getfix_damage();
/* 208 */             S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 209 */             this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/*     */           } 
/*     */           
/* 212 */           if (attrWeapon.getrandom_damage() > 0) {
/* 213 */             reset_dmg += _random.nextInt(attrWeapon.getrandom_damage());
/*     */           }

/* 239 */     	int c_hp = attrWeapon.getc_hp();
/* 216 */       if (c_hp != 0) {
/* 241 */       int pchp = this._pc.getCurrentHp();
/* 242 */       int tghp = this._target.getCurrentHp();	
/* 243 */       if (tghp >= c_hp) {
/* 244 */         pchp += c_hp;
/* 245 */         tghp -= c_hp;
/* 246 */         this._pc.setCurrentHp(pchp);
/* 247 */         this._target.setCurrentHp(tghp);
/* 219 */         S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 220 */         this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/*     */           }
/*     */        }
/*     */           
/* 239 */     	int c_mp = attrWeapon.getc_mp();
/* 216 */       if (c_mp != 0) {
/* 241 */       int pcmp = this._pc.getCurrentMp();
/* 242 */       int tgmp = this._target.getCurrentMp();	
/* 243 */       if (tgmp >= c_mp) {
/* 244 */         pcmp += c_mp;
/* 245 */         tgmp -= c_mp;
/* 246 */         this._pc.setCurrentMp(pcmp);
/* 247 */         this._target.setCurrentMp(tgmp);
/* 219 */         S_SkillSound s_SkillSound = new S_SkillSound(this._target.getId(), attrWeapon.getgfixd());
/* 220 */         this._target.broadcastPacketX8((ServerBasePacket)s_SkillSound);
/*     */           }
/*     */        }
/*     */ 
/*     */ 
/*     */           
/* 235 */           if (attrWeapon.getTypeLightDmg() > 0) {
/* 236 */             int dmg = attrWeapon.getTypeLightDmg();
/* 237 */             this._target.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/*     */             
/* 239 */             if (this._targetPc != null) {
/* 240 */               this._targetPc.sendPackets((ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/* 241 */               this._targetPc.receiveDamage((L1Character)this._pc, dmg, false, false);
/*     */             }
/* 243 */             else if (this._targetNpc != null) {
/* 244 */               this._targetNpc.receiveDamage((L1Character)this._pc, dmg);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 249 */           if (attrWeapon.getTypeSkill1() && attrWeapon.getTypeSkillTime() > 0.0D) {
/* 250 */             int timeSec = (int)attrWeapon.getTypeSkillTime();
/*     */             
/* 252 */             if (this._targetPc != null && !this._target.hasSkillEffect(40)) {
/* 253 */               this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), attrWeapon.getgfixd(), timeSec));
/* 254 */               this._targetPc.setSkillEffect(40, timeSec * 1000);
/* 255 */               this._targetPc.sendPackets((ServerBasePacket)new S_CurseBlind(1));
/*     */             } 
/*     */           } 
/*     */           
/* 259 */           if (attrWeapon.getTypeSkill2() && attrWeapon.getTypeSkillTime() > 0.0D) {
/* 260 */             int timeSec = (int)attrWeapon.getTypeSkillTime();
/*     */             
/* 262 */             if (this._targetPc != null && !this._target.hasSkillEffect(64)) {
/* 263 */               this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), attrWeapon.getgfixd(), timeSec));
/* 264 */               this._target.setSkillEffect(64, timeSec * 1000);
/*     */             } 
/*     */           } 
/*     */           
/* 268 */           if (attrWeapon.getTypeSkill3() && attrWeapon.getTypeSkillTime() > 0.0D && 
/* 269 */             attrWeapon.getTypePolyList() != null) {
/* 270 */             int polyId = Integer.parseInt(attrWeapon.getTypePolyList()[
/* 271 */                   _random.nextInt((attrWeapon.getTypePolyList()).length)]);
/* 272 */             if (this._targetPc != null) {
/*     */               
/* 274 */               if (this._targetPc.getInventory().checkEquipped(20281) || 
/* 275 */                 this._targetPc.getInventory().checkEquipped(120281)) {
/* 276 */                 return reset_dmg;
/*     */               }
/* 278 */               this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/*     */               
/* 280 */               L1PolyMorph.doPoly((L1Character)this._targetPc, polyId, 
/* 281 */                   (int)attrWeapon.getTypeSkillTime(), 
/* 282 */                   1);
/*     */             }
/* 284 */             else if (this._targetNpc != null) {
/*     */               
/* 286 */               if (!this._targetNpc.getNpcTemplate().is_boss()) {
/* 287 */                 this._target.broadcastPacketAll(
/* 288 */                     (ServerBasePacket)new S_SkillSound(this._target.getId(), attrWeapon.getgfixd()));
/*     */                 
/* 290 */                 L1PolyMorph.doPoly(this._target, polyId, 
/* 291 */                     (int)attrWeapon.getTypeSkillTime(), 
/* 292 */                     1);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 298 */           if (attrWeapon.getTypeSkill4() && attrWeapon.getTypeSkillTime() > 0.0D) {
/* 299 */             int timeSec = (int)attrWeapon.getTypeSkillTime();
/*     */             
/* 301 */             if (this._targetPc != null && !this._targetPc.hasSkillEffect(87)) {
/* 302 */               this._targetPc.setSkillEffect(87, timeSec * 1000);
/* 303 */               L1SpawnUtil.spawnEffect(81162, timeSec, this._targetPc.getX(), this._targetPc.getY(), this._targetPc.getMapId(), (L1Character)this._targetPc, 0);
/*     */ 
/*     */ 
/*     */               
/* 307 */               this._targetPc.sendPackets((ServerBasePacket)new S_Paralysis(5, true, timeSec * 1000));
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 312 */           if (attrWeapon.getTypeRemoveWeapon() && 
/* 313 */             this._targetPc != null && this._targetPc.getWeapon() != null) {
/*     */             
/* 315 */             this._targetPc.getInventory().setEquipped(
/* 316 */                 this._targetPc.getWeapon(), false, false, false);
/*     */             
/* 318 */             this._targetPc.sendPackets((ServerBasePacket)new S_ServerMessage(1027));
/*     */           } 
/*     */           
/* 321 */           if (attrWeapon.getTypeRemoveDoll() && 
/* 322 */             this._targetPc != null && 
/* 323 */             !this._targetPc.getDolls().isEmpty()) {
/* 324 */             Iterator<L1DollInstance> iter = 
/* 325 */               this._targetPc.getDolls().values().iterator();
/* 326 */             if (iter.hasNext()) {
/* 327 */               L1DollInstance doll = iter.next();
/*     */               
/* 329 */               doll.deleteDoll();
/*     */             } 
/*     */           } 
/*     */           
/* 333 */           if (attrWeapon.getTypeRemoveArmor() > 0 && this._targetPc != null) {
/* 334 */             int counter = _random.nextInt(attrWeapon.getTypeRemoveArmor()) + 1;
/* 335 */             StringBuffer sbr = new StringBuffer();
/* 336 */             Iterator<L1ItemInstance> iterator2 = this._targetPc.getInventory().getItems().iterator();
/* 337 */             while (iterator2.hasNext()) {
/*     */               
/* 339 */               L1ItemInstance item = iterator2.next();
/* 340 */               if (item.getItem().getType2() != 2 || !item.isEquipped())
/*     */                 continue; 
/* 342 */               this._targetPc.getInventory().setEquipped(item, false, false, false);
/* 343 */               sbr.append("[").append(item.getNumberedName(1L)).append("]");
/* 344 */               if (--counter <= 0)
/*     */                 break; 
/*     */             } 
/* 347 */             if (sbr.length() > 0) {
/* 348 */               this._targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("以下裝備被對方卸除:" + sbr.toString()));
/* 349 */               this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("成功卸除對方以下裝備:" + sbr.toString()));
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/* 355 */     } catch (Exception e) {
/*     */       
/* 357 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 359 */     return reset_dmg;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\L1AttackPower.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */