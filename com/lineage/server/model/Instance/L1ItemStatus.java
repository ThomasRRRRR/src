/*      */ package com.lineage.server.model.Instance;
/*      */ 
/*      */ import com.lineage.server.datatables.CheckItemPowerTable;
/*      */ import com.lineage.server.datatables.DollPowerTable;
/*      */ import com.lineage.server.datatables.ItemSpecialAttributeTable;
/*      */ import com.lineage.server.datatables.ItemVIPTable;
/*      */ import com.lineage.server.datatables.NpcTable;
/*      */ import com.lineage.server.datatables.PetItemTable;
/*      */ import com.lineage.server.datatables.PowerItemTable;
/*      */ import com.lineage.server.datatables.WeaponSkillTable;
/*      */ import com.lineage.server.model.L1WeaponSkill;
/*      */ import com.lineage.server.model.doll.L1DollExecutor;
/*      */ import com.lineage.server.templates.L1CheckItemPower;
/*      */ import com.lineage.server.templates.L1Doll;
/*      */ import com.lineage.server.templates.L1Item;
/*      */ import com.lineage.server.templates.L1ItemPower_name;
/*      */ import com.lineage.server.templates.L1ItemSpecialAttribute;
/*      */ import com.lineage.server.templates.L1ItemSpecialAttributeChar;
/*      */ import com.lineage.server.templates.L1ItemVIP;
/*      */ import com.lineage.server.templates.L1MagicWeapon;
/*      */ import com.lineage.server.templates.L1PetItem;
/*      */ import com.lineage.server.utils.BinaryOutputStream;
/*      */ import com.lineage.william.EnchantAccessory;
/*      */ import com.lineage.william.EnchantOrginal;
/*      */ import com.lineage.william.L1WilliamEnchantAccessory;
/*      */ import com.lineage.william.L1WilliamEnchantOrginal;
/*      */ import com.lineage.william.WilliamItemMessage;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class L1ItemStatus
/*      */ {
/*      */   private final L1ItemInstance _itemInstance;
/*      */   private final L1Item _item;
/*      */   private final BinaryOutputStream _os;
/*      */   private final L1ItemPower _itemPower;
/*      */   private final PowerItemTable _l1power;
/*      */   private boolean _statusx;
/*      */   
/*      */   public L1ItemStatus(L1ItemInstance itemInstance) {
/*   45 */     this._itemInstance = itemInstance;
/*   46 */     this._item = itemInstance.getItem();
/*   47 */     this._os = new BinaryOutputStream();
/*   48 */     this._itemPower = new L1ItemPower(this._itemInstance);
/*   49 */     this._l1power = PowerItemTable.get();
/*      */   }
/*      */ 
/*      */   
/*      */   public L1ItemStatus(L1Item template) {
/*   54 */     this._itemInstance = new L1ItemInstance();
/*   55 */     this._itemInstance.setItem(template);
/*   56 */     this._item = template;
/*   57 */     this._os = new BinaryOutputStream();
/*   58 */     this._itemPower = new L1ItemPower(this._itemInstance);
/*   59 */     this._l1power = PowerItemTable.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BinaryOutputStream getStatusBytes(boolean statusx) {
/*      */     String classname;
/*      */     L1PetItem petItem;
/*   68 */     this._statusx = statusx;
/*   69 */     int use_type = this._item.getUseType();
/*   70 */     switch (use_type) {
/*      */       case -11:
/*      */       case -10:
/*      */       case -9:
/*      */       case -8:
/*      */       case -7:
/*      */       case -6:
/*      */       case -5:
/*      */       case -4:
/*      */       case -1:
/*      */       case 0:
/*      */       case 3:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 39:
/*      */       case 42:
/*      */       case 46:
/*      */       case 55:
/*  108 */         classname = this._item.getclassname();
/*      */ 
/*      */ 
/*      */         
/*  112 */         if (classname.equalsIgnoreCase("doll.Magic_Doll") || classname.equalsIgnoreCase("doll.Magic_Doll_Power")) {
/*  113 */           return etcitem_doll();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  118 */         if (ItemVIPTable.get().checkVIP(this._item.getItemId())) {
/*  119 */           return etcitem_card(ItemVIPTable.get().getVIP(this._item.getItemId()));
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  124 */         if (this._item.getItemId() == 56147 || 
/*  125 */           this._item.getItemId() == 56148)
/*      */         {
/*  127 */           return effective_item();
/*      */         }
/*  129 */         if (CheckItemPowerTable.get().checkItem(this._item.getItemId())) {
/*  130 */           return etcitem_checkitempower(CheckItemPowerTable.get().getItem(this._item.getItemId()));
/*      */         }
/*      */ 
/*      */         
/*  134 */         return etcitem();
/*      */       
/*      */       case -12:
/*  137 */         petItem = PetItemTable.get().getTemplate(this._item.getItemId());
/*      */         
/*  139 */         if (petItem.isWeapom()) {
/*  140 */           return petweapon(petItem);
/*      */         }
/*      */         
/*  143 */         return petarmor(petItem);
/*      */       
/*      */       case -3:
/*      */       case -2:
/*  147 */         return arrow();
/*      */       
/*      */       case 38:
/*  150 */         return fooditem();
/*      */       
/*      */       case 10:
/*  153 */         return lightitem();
/*      */       case 2:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 25:
/*      */       case 47:
/*  162 */         return armor();
/*      */       
/*      */       case 23:
/*      */       case 24:
/*      */       case 37:
/*      */       case 40:
/*  168 */         return accessories();
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 48:
/*      */       case 49:
/*      */       case 51:
/*  175 */         return accessories2();
/*      */       case 1:
/*  177 */         return weapon();
/*      */     } 
/*      */     
/*  180 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream etcitem_checkitempower(L1CheckItemPower power) {
/*  190 */     if (power.get_ac() != 0) {
/*  191 */       this._os.writeC(39);
/*  192 */       this._os.writeS("防禦: +" + power.get_ac());
/*      */     } 
/*  194 */     if (power.get_str() != 0) {
/*  195 */       this._os.writeC(8);
/*  196 */       this._os.writeC(power.get_str());
/*      */     } 
/*  198 */     if (power.get_dex() != 0) {
/*  199 */       this._os.writeC(9);
/*  200 */       this._os.writeC(power.get_dex());
/*      */     } 
/*  202 */     if (power.get_con() != 0) {
/*  203 */       this._os.writeC(10);
/*  204 */       this._os.writeC(power.get_con());
/*      */     } 
/*  206 */     if (power.get_wis() != 0) {
/*  207 */       this._os.writeC(11);
/*  208 */       this._os.writeC(power.get_wis());
/*      */     } 
/*  210 */     if (power.get_intel() != 0) {
/*  211 */       this._os.writeC(12);
/*  212 */       this._os.writeC(power.get_intel());
/*      */     } 
/*  214 */     if (power.get_cha() != 0) {
/*  215 */       this._os.writeC(13);
/*  216 */       this._os.writeC(power.get_cha());
/*      */     } 
/*  218 */     if (power.get_hp() != 0) {
/*  219 */       this._os.writeC(14);
/*  220 */       this._os.writeH(power.get_hp());
/*      */     } 
/*  222 */     if (power.get_mp() != 0) {
/*  223 */       this._os.writeC(32);
/*  224 */       this._os.writeC(power.get_mp());
/*      */     } 
/*  226 */     if (power.get_mr() != 0) {
/*  227 */       this._os.writeC(15);
/*  228 */       this._os.writeH(power.get_mr());
/*      */     } 
/*  230 */     if (power.get_sp() != 0) {
/*  231 */       this._os.writeC(17);
/*  232 */       this._os.writeC(power.get_sp());
/*      */     } 
/*  234 */     if (power.get_dmg() != 0) {
/*  235 */       this._os.writeC(6);
/*  236 */       this._os.writeC(power.get_dmg());
/*      */     } 
/*  238 */     if (power.get_bow_dmg() != 0) {
/*  239 */       this._os.writeC(35);
/*  240 */       this._os.writeC(power.get_bow_dmg());
/*      */     } 
/*  242 */     if (power.get_hit() != 0) {
/*  243 */       this._os.writeC(5);
/*  244 */       this._os.writeC(power.get_hit());
/*      */     } 
/*  246 */     if (power.get_bow_hit() != 0) {
/*  247 */       this._os.writeC(24);
/*  248 */       this._os.writeC(power.get_bow_hit());
/*      */     } 
/*  250 */     if (power.get_dmg_r() != 0) {
/*  251 */       this._os.writeC(39);
/*  252 */       this._os.writeS("物理減傷 +" + power.get_dmg_r());
/*      */     } 
/*  254 */     if (power.get_magic_r() != 0) {
/*  255 */       this._os.writeC(39);
/*  256 */       this._os.writeS("魔法減傷 +" + power.get_magic_r());
/*      */     } 
/*  258 */     if (power.get_fire() != 0) {
/*  259 */       this._os.writeC(27);
/*  260 */       this._os.writeC(power.get_fire());
/*      */     } 
/*  262 */     if (power.get_water() != 0) {
/*  263 */       this._os.writeC(28);
/*  264 */       this._os.writeC(power.get_water());
/*      */     } 
/*  266 */     if (power.get_wind() != 0) {
/*  267 */       this._os.writeC(29);
/*  268 */       this._os.writeC(power.get_wind());
/*      */     } 
/*  270 */     if (power.get_earth() != 0) {
/*  271 */       this._os.writeC(30);
/*  272 */       this._os.writeC(power.get_earth());
/*      */     } 
/*      */     
/*  275 */     if (power.get_freeze() != 0) {
/*  276 */       this._os.writeC(39);
/*  277 */       this._os.writeC(1);
/*  278 */       this._os.writeS("寒冰耐性+" + power.get_freeze());
/*      */     } 
/*  280 */     if (power.get_stone() != 0) {
/*  281 */       this._os.writeC(39);
/*  282 */       this._os.writeC(2);
/*  283 */       this._os.writeS("石化耐性+" + power.get_stone());
/*      */     } 
/*  285 */     if (power.get_sleep() != 0) {
/*  286 */       this._os.writeC(39);
/*  287 */       this._os.writeC(3);
/*  288 */       this._os.writeS("睡眠耐性+" + power.get_sleep());
/*      */     } 
/*  290 */     if (power.get_blind() != 0) {
/*  291 */       this._os.writeC(39);
/*  292 */       this._os.writeC(4);
/*  293 */       this._os.writeS("暗黑耐性: +" + power.get_blind());
/*      */     } 
/*  295 */     if (power.get_stun() != 0) {
/*  296 */       this._os.writeC(39);
/*  297 */       this._os.writeC(5);
/*  298 */       this._os.writeS("暈眩耐性: +" + power.get_stun());
/*      */     } 
/*  300 */     if (power.get_sustain() != 0) {
/*  301 */       this._os.writeC(39);
/*  302 */       this._os.writeC(6);
/*  303 */       this._os.writeS("支撐耐性: +" + power.get_sustain());
/*      */     } 
/*  305 */     if (power.get_hpr() != 0) {
/*  306 */       this._os.writeC(37);
/*  307 */       this._os.writeC(power.get_hpr());
/*      */     } 
/*  309 */     if (power.get_mpr() != 0) {
/*  310 */       this._os.writeC(38);
/*  311 */       this._os.writeC(power.get_mpr());
/*      */     }
/*      */ 
/*      */     
/*  315 */     ArrayList<String> as = new ArrayList<>();
/*      */     try {
/*  317 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
/*  318 */         if (s != null && !s.isEmpty()) {
/*  319 */           this._os.writeC(39);
/*  320 */           this._os.writeS(s);
/*      */         } 
/*      */       } 
/*      */     } finally {
/*  324 */       as.clear();
/*      */     } 
/*      */     
/*  327 */     this._os.writeC(23);
/*  328 */     this._os.writeC(this._item.getMaterial());
/*  329 */     this._os.writeD(this._itemInstance.getWeight());
/*      */ 
/*      */     
/*  332 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream etcitem_card(L1ItemVIP vip) {
/*  340 */     if (this._itemInstance.get_time() != null) {
/*  341 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*  342 */       this._os.writeC(39);
/*  343 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*  345 */     int add_str = vip.get_add_str();
/*  346 */     if (add_str != 0) {
/*      */       
/*  348 */       this._os.writeC(8);
/*  349 */       this._os.writeC(add_str);
/*      */     } 
/*  351 */     int add_dex = vip.get_add_dex();
/*  352 */     if (add_dex != 0) {
/*      */       
/*  354 */       this._os.writeC(9);
/*  355 */       this._os.writeC(add_dex);
/*      */     } 
/*  357 */     int add_con = vip.get_add_con();
/*  358 */     if (add_con != 0) {
/*      */       
/*  360 */       this._os.writeC(10);
/*  361 */       this._os.writeC(add_con);
/*      */     } 
/*  363 */     int add_wis = vip.get_add_wis();
/*  364 */     if (add_wis != 0) {
/*      */       
/*  366 */       this._os.writeC(11);
/*  367 */       this._os.writeC(add_wis);
/*      */     } 
/*  369 */     int add_int = vip.get_add_int();
/*  370 */     if (add_int != 0) {
/*      */       
/*  372 */       this._os.writeC(12);
/*  373 */       this._os.writeC(add_int);
/*      */     } 
/*  375 */     int add_cha = vip.get_add_cha();
/*  376 */     if (add_cha != 0) {
/*      */       
/*  378 */       this._os.writeC(13);
/*  379 */       this._os.writeC(add_cha);
/*      */     } 
/*      */     
/*  382 */     int add_dmg = vip.get_add_dmg();
/*  383 */     if (add_dmg != 0) {
/*      */       
/*  385 */       this._os.writeC(6);
/*  386 */       this._os.writeC(add_dmg);
/*      */     } 
/*  388 */     int add_hit = vip.get_add_hit();
/*  389 */     if (add_hit != 0) {
/*      */       
/*  391 */       this._os.writeC(5);
/*  392 */       this._os.writeC(add_hit);
/*      */     } 
/*  394 */     int add_bow_dmg = vip.get_add_bow_dmg();
/*  395 */     if (add_bow_dmg != 0) {
/*      */       
/*  397 */       this._os.writeC(35);
/*  398 */       this._os.writeC(add_bow_dmg);
/*      */     } 
/*  400 */     int add_bow_hit = vip.get_add_bow_hit();
/*  401 */     if (add_bow_hit != 0) {
/*      */       
/*  403 */       this._os.writeC(24);
/*  404 */       this._os.writeC(add_bow_hit);
/*      */     } 
/*  406 */     int add_mr = vip.get_add_mr();
/*  407 */     if (add_mr != 0) {
/*      */       
/*  409 */       this._os.writeC(15);
/*  410 */       this._os.writeH(add_mr);
/*      */     } 
/*  412 */     int add_sp = vip.get_add_sp();
/*  413 */     if (add_sp != 0) {
/*      */       
/*  415 */       this._os.writeC(17);
/*  416 */       this._os.writeC(add_sp);
/*      */     } 
/*  418 */     int add_fire = vip.get_add_fire();
/*  419 */     if (add_fire != 0) {
/*      */       
/*  421 */       this._os.writeC(27);
/*  422 */       this._os.writeC(add_fire);
/*      */     } 
/*  424 */     int add_wind = vip.get_add_wind();
/*  425 */     if (add_wind != 0) {
/*      */       
/*  427 */       this._os.writeC(29);
/*  428 */       this._os.writeC(add_wind);
/*      */     } 
/*  430 */     int add_earth = vip.get_add_earth();
/*  431 */     if (add_earth != 0) {
/*      */       
/*  433 */       this._os.writeC(30);
/*  434 */       this._os.writeC(add_earth);
/*      */     } 
/*  436 */     int add_water = vip.get_add_water();
/*  437 */     if (add_water != 0) {
/*      */       
/*  439 */       this._os.writeC(28);
/*  440 */       this._os.writeC(add_water);
/*      */     } 
/*  442 */     int add_ac = vip.get_add_ac();
/*  443 */     if (add_ac != 0) {
/*      */       
/*  445 */       this._os.writeC(39);
/*  446 */       this._os.writeS("防禦: +" + add_ac);
/*      */     } 
/*      */     
/*  449 */     int add_hp = vip.get_add_hp();
/*  450 */     if (add_hp != 0) {
/*      */       
/*  452 */       this._os.writeC(39);
/*  453 */       this._os.writeS("血量: +" + add_hp);
/*      */     } 
/*      */     
/*  456 */     int add_mp = vip.get_add_mp();
/*  457 */     if (add_mp != 0) {
/*      */       
/*  459 */       this._os.writeC(39);
/*  460 */       this._os.writeS("魔量: +" + add_mp);
/*      */     } 
/*      */     
/*  463 */     int add_hpr = vip.get_add_hpr();
/*  464 */     if (add_hpr != 0) {
/*      */       
/*  466 */       this._os.writeC(39);
/*  467 */       this._os.writeS("回血: +" + add_hpr);
/*      */     } 
/*  469 */     int add_mpr = vip.get_add_mpr();
/*  470 */     if (add_mpr != 0) {
/*      */       
/*  472 */       this._os.writeC(39);
/*  473 */       this._os.writeS("回魔: +" + add_mpr);
/*      */     } 
/*  475 */     int add_freeze = vip.get_add_freeze();
/*  476 */     if (add_freeze != 0) {
/*      */       
/*  478 */       this._os.writeC(39);
/*  479 */       this._os.writeS("寒冰耐性: +" + add_freeze);
/*      */     } 
/*      */     
/*  482 */     int add_stone = vip.get_add_stone();
/*  483 */     if (add_stone != 0) {
/*  484 */       this._os.writeC(39);
/*  485 */       this._os.writeS("昏迷耐性: +" + add_stone);
/*      */     } 
/*      */     
/*  488 */     int add_sleep = vip.get_add_sleep();
/*  489 */     if (add_sleep != 0) {
/*      */       
/*  491 */       this._os.writeC(39);
/*  492 */       this._os.writeS("睡眠耐性: +" + add_sleep);
/*      */     } 
/*      */     
/*  495 */     int add_blind = vip.get_add_blind();
/*  496 */     if (add_blind != 0) {
/*      */       
/*  498 */       this._os.writeC(39);
/*  499 */       this._os.writeS("暗黑耐性: +" + add_blind);
/*      */     } 
/*  501 */     int add_stun = vip.get_add_stun();
/*  502 */     if (add_stun != 0) {
/*      */       
/*  504 */       this._os.writeC(39);
/*  505 */       this._os.writeS("暈眩耐性: +" + add_stun);
/*      */     } 
/*      */     
/*  508 */     int add_sustain = vip.get_add_sustain();
/*  509 */     if (add_sustain != 0) {
/*      */       
/*  511 */       this._os.writeC(39);
/*  512 */       this._os.writeS("支撐耐性: +" + add_sustain);
/*      */     } 
/*  514 */     int wmd = vip.get_add_wmd();
/*  515 */     if (wmd != 0) {
/*      */       
/*  517 */       this._os.writeC(39);
/*  518 */       this._os.writeS("魔武傷害增加: +" + wmd);
/*      */     } 
/*  520 */     int wmc = vip.get_add_wmc();
/*  521 */     if (wmc != 0) {
/*      */       
/*  523 */       this._os.writeC(39);
/*  524 */       this._os.writeS("魔武發動增加: +" + wmc);
/*      */     } 
/*      */     
/*  527 */     int dmgr = vip.get_add_dmg_r();
/*  528 */     if (dmgr != 0) {
/*      */       
/*  530 */       this._os.writeC(39);
/*  531 */       this._os.writeS("物理減傷: +" + dmgr);
/*      */     } 
/*      */     
/*  534 */     int mdmgr = vip.get_add_magic_r();
/*  535 */     if (mdmgr != 0) {
/*      */       
/*  537 */       this._os.writeC(39);
/*  538 */       this._os.writeS("魔法減傷: +" + mdmgr);
/*      */     } 
/*      */     
/*  541 */     int exp = vip.get_add_exp();
/*  542 */     if (exp != 0) {
/*      */       
/*  544 */       this._os.writeC(39);
/*  545 */       this._os.writeS("經驗值增加:" + exp + "%。");
/*      */     } 
/*  547 */     int gf = vip.get_add_adena();
/*  548 */     if (gf != 0) {
/*      */       
/*  550 */       this._os.writeC(39);
/*  551 */       this._os.writeS("金幣倍率增加:" + gf + "%。");
/*      */     } 
/*      */     
/*  554 */     boolean item = vip.get_death_item();
/*  555 */     if (item) {
/*      */       
/*  557 */       this._os.writeC(39);
/*  558 */       this._os.writeS("死亡不會噴道具。");
/*      */     } 
/*      */     
/*  561 */     boolean exp1 = vip.get_death_exp();
/*  562 */     if (exp1) {
/*      */       
/*  564 */       this._os.writeC(39);
/*  565 */       this._os.writeS("死亡不會噴經驗值。");
/*      */     } 
/*      */     
/*  568 */     boolean skill = vip.get_death_skill();
/*  569 */     if (skill) {
/*      */       
/*  571 */       this._os.writeC(39);
/*  572 */       this._os.writeS("死亡不會噴技能。");
/*      */     } 
/*  574 */     boolean score = vip.get_death_score();
/*  575 */     if (score) {
/*      */       
/*  577 */       this._os.writeC(39);
/*  578 */       this._os.writeS("死亡不會掉積分。");
/*      */     } 
/*  580 */     if (!this._item.isTradable()) {
/*  581 */       this._os.writeC(39);
/*  582 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  585 */     if (this._item.isCantDelete()) {
/*  586 */       this._os.writeC(39);
/*  587 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  590 */     if (this._item.get_safeenchant() < 0) {
/*  591 */       this._os.writeC(39);
/*  592 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/*  596 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/*  599 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/*  601 */         this._os.writeC(39);
/*  602 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  607 */       as.clear();
/*      */     } 
/*      */     
/*  610 */     return this._os;
/*      */   }
/*      */   private BinaryOutputStream effective_item() {
/*  613 */     if (this._item.getItemId() == 56147) {
/*  614 */       this._os.writeC(14);
/*  615 */       this._os.writeH(50);
/*      */       
/*  617 */       this._os.writeC(32);
/*  618 */       this._os.writeC(30);
/*      */       
/*  620 */       this._os.writeC(6);
/*  621 */       this._os.writeC(3);
/*      */       
/*  623 */       this._os.writeC(35);
/*  624 */       this._os.writeC(3);
/*      */       
/*  626 */       this._os.writeC(17);
/*  627 */       this._os.writeC(3);
/*      */       
/*  629 */       this._os.writeC(39);
/*  630 */       this._os.writeS("傷害減免+3");
/*      */       
/*  632 */       this._os.writeC(37);
/*  633 */       this._os.writeC(3);
/*      */       
/*  635 */       this._os.writeC(38);
/*  636 */       this._os.writeC(3);
/*  637 */     } else if (this._item.getItemId() == 56148) {
/*  638 */       this._os.writeC(14);
/*  639 */       this._os.writeH(30);
/*      */       
/*  641 */       this._os.writeC(32);
/*  642 */       this._os.writeC(30);
/*      */       
/*  644 */       this._os.writeC(6);
/*  645 */       this._os.writeC(2);
/*      */       
/*  647 */       this._os.writeC(35);
/*  648 */       this._os.writeC(2);
/*      */       
/*  650 */       this._os.writeC(17);
/*  651 */       this._os.writeC(2);
/*      */       
/*  653 */       this._os.writeC(39);
/*  654 */       this._os.writeS("傷害減免+2");
/*      */       
/*  656 */       this._os.writeC(37);
/*  657 */       this._os.writeC(2);
/*      */       
/*  659 */       this._os.writeC(38);
/*  660 */       this._os.writeC(2);
/*      */     } 
/*  685 */     if (!this._item.isTradable()) {
/*  686 */       this._os.writeC(39);
/*  687 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  690 */     if (this._item.isCantDelete()) {
/*  691 */       this._os.writeC(39);
/*  692 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  695 */     if (this._item.get_safeenchant() < 0) {
/*  696 */       this._os.writeC(39);
/*  697 */       this._os.writeS("無法強化");
/*      */     } 
/*      */     
/*  700 */     return this._os;
/*      */   }
/*      */   
/*      */   private BinaryOutputStream etcitem_doll() {
/*  704 */     L1Doll doll = DollPowerTable.get().get_type(this._item.getItemId());
/*  705 */     String msg = null;
/*  706 */     if (!doll.getPowerList().isEmpty()) {
/*  707 */       for (L1DollExecutor power : doll.getPowerList()) {
/*  708 */         if (power.get_note() != null) {
/*  709 */           msg = power.get_note();
/*  710 */           this._os.writeC(39);
/*  711 */           this._os.writeS(msg);
/*      */         } 
/*      */       } 
/*      */     }
/*  715 */     if (this._itemInstance.get_time() != null) {
/*  716 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/*  717 */       this._os.writeC(39);
/*  718 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*      */     
/*  721 */     if (!this._item.isTradable()) {
/*  722 */       this._os.writeC(39);
/*  723 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  726 */     if (this._item.isCantDelete()) {
/*  727 */       this._os.writeC(39);
/*  728 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  731 */     if (this._item.get_safeenchant() < 0) {
/*  732 */       this._os.writeC(39);
/*  733 */       this._os.writeS("無法強化");
/*      */     } 
/*      */     
/*  736 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream arrow() {
/*  744 */     this._os.writeC(1);
/*  745 */     this._os.writeC(this._item.getDmgSmall());
/*  746 */     this._os.writeC(this._item.getDmgLarge());
/*  747 */     this._os.writeC(this._item.getMaterial());
/*  748 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/*  750 */     if (!this._item.isTradable()) {
/*  751 */       this._os.writeC(39);
/*  752 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  755 */     if (this._item.isCantDelete()) {
/*  756 */       this._os.writeC(39);
/*  757 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  760 */     if (this._item.get_safeenchant() < 0) {
/*  761 */       this._os.writeC(39);
/*  762 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/*  766 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/*  769 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/*  771 */         this._os.writeC(39);
/*  772 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  777 */       as.clear();
/*      */     } 
/*  779 */     return this._os;
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryOutputStream fooditem() {
/*  784 */     this._os.writeC(21);
/*      */     
/*  786 */     this._os.writeH(this._item.getFoodVolume());
/*  787 */     this._os.writeC(this._item.getMaterial());
/*  788 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/*  790 */     if (!this._item.isTradable()) {
/*  791 */       this._os.writeC(39);
/*  792 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  795 */     if (this._item.isCantDelete()) {
/*  796 */       this._os.writeC(39);
/*  797 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  800 */     if (this._item.get_safeenchant() < 0) {
/*  801 */       this._os.writeC(39);
/*  802 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/*  806 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/*  809 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/*  811 */         this._os.writeC(39);
/*  812 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  817 */       as.clear();
/*      */     } 
/*  819 */     return this._os;
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryOutputStream lightitem() {
/*  824 */     this._os.writeC(22);
/*  825 */     this._os.writeH(this._item.getLightRange());
/*  826 */     this._os.writeC(this._item.getMaterial());
/*  827 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/*  829 */     if (!this._item.isTradable()) {
/*  830 */       this._os.writeC(39);
/*  831 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/*  834 */     if (this._item.isCantDelete()) {
/*  835 */       this._os.writeC(39);
/*  836 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/*  839 */     if (this._item.get_safeenchant() < 0) {
/*  840 */       this._os.writeC(39);
/*  841 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/*  845 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/*  848 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/*  850 */         this._os.writeC(39);
/*  851 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  856 */       as.clear();
/*      */     } 
/*  858 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream armor() {
/*  869 */     int attr_str = 0;
/*  870 */     int attr_con = 0;
/*  871 */     int attr_dex = 0;
/*  872 */     int attr_int = 0;
/*  873 */     int attr_wis = 0;
/*  874 */     int attr_cha = 0;
/*  875 */     int attr_hp = 0;
/*  876 */     int attr_mp = 0;
/*  877 */     int attr_sp = 0;
/*  878 */     int attr_mr = 0;
/*  879 */     int drain_min_hp = 0;
/*  880 */     int drain_max_hp = 0;
/*  881 */     int drain_hp_rand = 0;
/*  882 */     int attr_hpr = 0;
/*  883 */     int attr_mpr = 0;
/*  884 */     int drain_min_mp = 0;
/*  885 */     int drain_max_mp = 0;
/*  886 */     int drain_mp_rand = 0;
/*      */     
/*  888 */     int skill_gfxid = 0;
/*  889 */     int skill_rand = 0;
/*  890 */     int skill_dmg = 0;
/*  891 */     int attr_魔法格檔 = 0;
/*  892 */     int attr_物理格檔 = 0;
/*      */     
/*  894 */     int PVP增傷 = 0;
/*  895 */     int PVP減傷 = 0;
/*  896 */     int attr_potion_heal = 0;
/*  897 */     int attr_dmgR = 0;
/*      */     
/*  899 */     L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
/*  900 */     if (attr_char != null) {
/*  901 */       L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
/*      */       
/*  903 */       attr_str = attr.get_add_str();
/*  904 */       attr_con = attr.get_add_con();
/*  905 */       attr_dex = attr.get_add_dex();
/*  906 */       attr_int = attr.get_add_int();
/*  907 */       attr_wis = attr.get_add_wis();
/*  908 */       attr_cha = attr.get_add_cha();
/*  909 */       attr_hp = attr.get_add_hp();
/*  910 */       attr_mp = attr.get_add_mp();
/*  911 */       attr_sp = attr.get_add_sp();
/*  912 */       attr_mr = attr.get_add_m_def();
/*  913 */       drain_min_hp = attr.get_add_drain_min_hp();
/*  914 */       drain_max_hp = attr.get_add_drain_max_hp();
/*  915 */       drain_hp_rand = attr.get_drain_hp_rand();
/*  916 */       drain_min_mp = attr.get_add_drain_min_mp();
/*  917 */       drain_max_mp = attr.get_add_drain_max_mp();
/*  918 */       drain_mp_rand = attr.get_drain_mp_rand();
/*  919 */       attr_hpr = attr.get_add_hpr();
/*  920 */       attr_mpr = attr.get_add_mpr();
/*  921 */       skill_gfxid = attr.get_add_skill_gfxid();
/*  922 */       skill_rand = attr.get_add_skill_rand();
/*  923 */       skill_dmg = attr.get_add_skill_dmg();
/*      */       
/*  925 */       attr_魔法格檔 = attr.get魔法格檔();
/*  926 */       attr_物理格檔 = attr.get物理格檔();
/*      */       
/*  928 */       PVP增傷 = attr.get_add_pvp_dmg();
/*  929 */       PVP減傷 = attr.get_add_pvp_redmg();
/*  930 */       attr_potion_heal = attr.get_add_potion_heal();
/*  931 */       attr_dmgR = attr.get_add_dmgR();
/*      */     } 
/*  933 */     L1ItemPower_name power = this._itemInstance.get_power_name();
/*      */ 
/*      */ 
/*      */     
/*  937 */     String name2 = "";
/*  938 */     if (this._itemInstance.getskilltype() != 0) {
/*  939 */       int Skill_Level = this._itemInstance.getskilltypelv();
/*  940 */       int Skill_Type = this._itemInstance.getskilltype();
/*  941 */       switch (Skill_Type) {
/*      */         case 113:
/*  943 */           if (Skill_Level != 1)
/*      */             break; 
/*  945 */           name2 = "裝備天賦:Lv1減傷2%";
/*      */           break;
/*      */         case 114:
/*  948 */           if (Skill_Level != 2)
/*      */             break; 
/*  950 */           name2 = "裝備天賦:Lv2減傷5%";
/*      */           break;
/*      */         
/*      */         case 115:
/*  954 */           if (Skill_Level != 3)
/*      */             break; 
/*  956 */           name2 = "裝備天賦:Lv3減傷10%";
/*      */           break;
/*      */         
/*      */         case 116:
/*  960 */           if (Skill_Level != 4)
/*      */             break; 
/*  962 */           name2 = "裝備天賦:Lv4減傷15%";
/*      */           break;
/*      */         case 117:
/*  965 */           if (Skill_Level != 5)
/*      */             break; 
/*  967 */           name2 = "裝備天賦:Lv5減傷20%";
/*      */           break;
/*      */         case 118:
/*  970 */           if (Skill_Level != 1)
/*      */             break; 
/*  972 */           name2 = "裝備天賦:Lv1增傷+5";
/*      */           break;
/*      */         case 119:
/*  975 */           if (Skill_Level != 2)
/*      */             break; 
/*  977 */           name2 = "裝備天賦:Lv2增傷+10";
/*      */           break;
/*      */         case 120:
/*  980 */           if (Skill_Level != 3)
/*      */             break; 
/*  982 */           name2 = "裝備天賦:Lv3增傷+15";
/*      */           break;
/*      */         case 121:
/*  985 */           if (Skill_Level != 4)
/*      */             break; 
/*  987 */           name2 = "裝備天賦:Lv4增傷+20";
/*      */           break;
/*      */         case 122:
/*  990 */           if (Skill_Level != 5)
/*      */             break; 
/*  992 */           name2 = "裝備天賦:Lv5增傷+30";
/*      */           break;
/*      */         case 123:
/*  995 */           if (Skill_Level != 1)
/*      */             break; 
/*  997 */           name2 = "裝備天賦:Lv1爆擊1%";
/*      */           break;
/*      */         case 124:
/* 1000 */           if (Skill_Level != 2)
/*      */             break; 
/* 1002 */           name2 = "裝備天賦:Lv2爆擊3%";
/*      */           break;
/*      */         case 125:
/* 1005 */           if (Skill_Level != 3)
/*      */             break; 
/* 1007 */           name2 = "裝備天賦:Lv3爆擊7%";
/*      */           break;
/*      */         case 126:
/* 1010 */           if (Skill_Level != 4)
/*      */             break; 
/* 1012 */           name2 = "裝備天賦:Lv4爆擊12%";
/*      */           break;
/*      */         case 127:
/* 1015 */           if (Skill_Level != 5)
/*      */             break; 
/* 1017 */           name2 = "裝備天賦:Lv5爆擊20%";
/*      */           break;
/*      */         case 128:
/* 1020 */           if (Skill_Level != 1)
/*      */             break; 
/* 1022 */           name2 = "裝備天賦:Lv1閃避1%";
/*      */           break;
/*      */         case 129:
/* 1025 */           if (Skill_Level != 2)
/*      */             break; 
/* 1027 */           name2 = "裝備天賦:Lv2閃避2%";
/*      */           break;
/*      */         case 130:
/* 1030 */           if (Skill_Level != 3)
/*      */             break; 
/* 1032 */           name2 = "裝備天賦:Lv3閃避4%";
/*      */           break;
/*      */         case 131:
/* 1035 */           if (Skill_Level != 4)
/*      */             break; 
/* 1037 */           name2 = "裝備天賦:Lv4閃避7%";
/*      */           break;
/*      */         case 132:
/* 1040 */           if (Skill_Level != 5)
/*      */             break; 
/* 1042 */           name2 = "裝備天賦:Lv5閃避10%";
/*      */           break;
/*      */         case 133:
/* 1045 */           if (Skill_Level != 1)
/*      */             break; 
/* 1047 */           name2 = "裝備天賦:Lv1魔減2%";
/*      */           break;
/*      */         case 134:
/* 1050 */           if (Skill_Level != 2)
/*      */             break; 
/* 1052 */           name2 = "裝備天賦:Lv2魔減5%";
/*      */           break;
/*      */         case 135:
/* 1055 */           if (Skill_Level != 3)
/*      */             break; 
/* 1057 */           name2 = "裝備天賦:Lv3魔減10%";
/*      */           break;
/*      */         case 136:
/* 1060 */           if (Skill_Level != 4)
/*      */             break; 
/* 1062 */           name2 = "裝備天賦:Lv4魔減15%";
/*      */           break;
/*      */         case 137:
/* 1065 */           if (Skill_Level != 5)
/*      */             break; 
/* 1067 */           name2 = "裝備天賦:Lv5魔減20%";
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1072 */       this._os.writeC(39);
/* 1073 */       this._os.writeS(name2);
/*      */     } 
/*      */ 
/*      */     
/* 1077 */     this._os.writeC(19);
/* 1078 */     int power_ac = 0;
/* 1079 */     if (power != null) {
/* 1080 */       power_ac += (this._l1power.getItem(power.get_hole_1())).add_ac;
/* 1081 */       power_ac += (this._l1power.getItem(power.get_hole_2())).add_ac;
/* 1082 */       power_ac += (this._l1power.getItem(power.get_hole_3())).add_ac;
/* 1083 */       power_ac += (this._l1power.getItem(power.get_hole_4())).add_ac;
/* 1084 */       power_ac += (this._l1power.getItem(power.get_hole_5())).add_ac;
/*      */     } 
/* 1086 */     int ac = this._item.get_ac() - this._itemInstance.getItemAc();
/* 1087 */     if (ac < 0) {
/* 1088 */       ac = Math.abs(ac);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1093 */     this._os.writeC(ac);
/*      */     
/* 1095 */     this._os.writeC(this._item.getMaterial());
/* 1096 */     this._os.writeC(this._item.get_greater());
/* 1097 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/* 1099 */     if (this._itemInstance.getEnchantLevel() != 0 || power_ac != 0) {
/* 1100 */       this._os.writeC(2);
/* 1101 */       this._os.writeC(this._itemInstance.getEnchantLevel() + power_ac);
/*      */     } 
/* 1103 */     if (this._itemInstance.get_time() != null) {
/* 1104 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 1105 */       this._os.writeC(39);
/* 1106 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/* 1108 */     if (this._itemInstance.get_durability() != 0) {
/* 1109 */       this._os.writeC(3);
/* 1110 */       this._os.writeC(this._itemInstance.get_durability());
/*      */     } 
/*      */     
/* 1113 */     int s6_1 = 0;
/* 1114 */     int s6_2 = 0;
/* 1115 */     int s6_3 = 0;
/* 1116 */     int s6_4 = 0;
/* 1117 */     int s6_5 = 0;
/* 1118 */     int s6_6 = 0;
/* 1119 */     int aH_1 = 0;
/* 1120 */     int aM_1 = 0;
/* 1121 */     int aMR_1 = 0;
/* 1122 */     int aSP_1 = 0;
/* 1123 */     int aSS_1 = 0;
/* 1124 */     int d4_1 = 0;
/* 1125 */     int d4_2 = 0;
/* 1126 */     int d4_3 = 0;
/* 1127 */     int d4_4 = 0;
/* 1128 */     int k6_1 = 0;
/* 1129 */     int k6_2 = 0;
/* 1130 */     int k6_3 = 0;
/* 1131 */     int k6_4 = 0;
/* 1132 */     int k6_5 = 0;
/* 1133 */     int k6_6 = 0;
/* 1134 */     int aHpr = 0;
/* 1135 */     int aMpr = 0;
/* 1136 */     int admg = 0;
/* 1137 */     int drd = 0;
/* 1138 */     int mdmg = 0;
/* 1139 */     int mdrd = 0;
/* 1140 */     int bdmg = 0;
/* 1141 */     int hit = 0;
/*      */     
/* 1143 */     int mcri = 0;
/* 1144 */     int addDmgModifier = 0;
/* 1145 */     int addHitModifier = 0;
/* 1146 */     int addBowDmgModifier = 0;
/* 1147 */     int addBowHitModifier = 0;
/* 1148 */     int weapon_pro = this._item.getweaponskillpro();
/* 1149 */     int weapon_dmg = this._item.getweaponskilldmg();
/* 1150 */     int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
/* 1151 */     int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
/* 1152 */     if (this._itemInstance.isMatch()) {
/* 1153 */       s6_1 = this._item.get_mode()[0];
/* 1154 */       s6_2 = this._item.get_mode()[1];
/* 1155 */       s6_3 = this._item.get_mode()[2];
/* 1156 */       s6_4 = this._item.get_mode()[3];
/* 1157 */       s6_5 = this._item.get_mode()[4];
/* 1158 */       s6_6 = this._item.get_mode()[5];
/* 1159 */       aH_1 = this._item.get_mode()[6];
/* 1160 */       aM_1 = this._item.get_mode()[7];
/* 1161 */       aMR_1 = this._item.get_mode()[8];
/* 1162 */       aSP_1 = this._item.get_mode()[9];
/* 1163 */       aSS_1 = this._item.get_mode()[10];
/* 1164 */       d4_1 = this._item.get_mode()[11];
/* 1165 */       d4_2 = this._item.get_mode()[12];
/* 1166 */       d4_3 = this._item.get_mode()[13];
/* 1167 */       d4_4 = this._item.get_mode()[14];
/* 1168 */       k6_1 = this._item.get_mode()[15];
/* 1169 */       k6_2 = this._item.get_mode()[16];
/* 1170 */       k6_3 = this._item.get_mode()[17];
/* 1171 */       k6_4 = this._item.get_mode()[18];
/* 1172 */       k6_5 = this._item.get_mode()[19];
/* 1173 */       k6_6 = this._item.get_mode()[20];
/* 1174 */       aHpr = this._item.get_mode()[21];
/* 1175 */       aMpr = this._item.get_mode()[22];
/* 1176 */       admg = this._item.get_mode()[23];
/* 1177 */       drd = this._item.get_mode()[24];
/* 1178 */       mdmg = this._item.get_mode()[25];
/* 1179 */       mdrd = this._item.get_mode()[26];
/* 1180 */       bdmg = this._item.get_mode()[27];
/* 1181 */       hit = this._item.get_mode()[28];
/*      */       
/* 1183 */       mcri = this._item.get_mode()[30];
/* 1184 */       weapon_pro = this._item.get_mode()[31];
/* 1185 */       weapon_dmg = this._item.get_mode()[32];
/*      */     } 
/*      */     
/* 1188 */     if (power != null) {
/* 1189 */       aHpr += (this._l1power.getItem(power.get_hole_1())).add_hpr;
/* 1190 */       aMpr += (this._l1power.getItem(power.get_hole_1())).add_mpr;
/* 1191 */       s6_1 += (this._l1power.getItem(power.get_hole_1())).add_str;
/* 1192 */       s6_2 += (this._l1power.getItem(power.get_hole_1())).add_dex;
/* 1193 */       s6_3 += (this._l1power.getItem(power.get_hole_1())).add_con;
/* 1194 */       s6_4 += (this._l1power.getItem(power.get_hole_1())).add_wis;
/* 1195 */       s6_5 += (this._l1power.getItem(power.get_hole_1())).add_int;
/* 1196 */       s6_6 += (this._l1power.getItem(power.get_hole_1())).add_cha;
/* 1197 */       aH_1 += (this._l1power.getItem(power.get_hole_1())).addMaxHP;
/* 1198 */       aM_1 += (this._l1power.getItem(power.get_hole_1())).addMaxMP;
/* 1199 */       aMR_1 += (this._l1power.getItem(power.get_hole_1())).m_def;
/* 1200 */       aSP_1 += (this._l1power.getItem(power.get_hole_1())).add_sp;
/* 1201 */       addDmgModifier += (this._l1power.getItem(power.get_hole_1())).dmg_modifier;
/* 1202 */       addHitModifier += (this._l1power.getItem(power.get_hole_1())).hit_modifier;
/* 1203 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_1())).bow_dmg_modifier;
/* 1204 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_1())).bow_hit_modifier;
/* 1205 */       drd += (this._l1power.getItem(power.get_hole_1())).addDamageReductionByArmor;
/*      */       
/* 1207 */       aHpr += (this._l1power.getItem(power.get_hole_2())).add_hpr;
/* 1208 */       aMpr += (this._l1power.getItem(power.get_hole_2())).add_mpr;
/* 1209 */       s6_1 += (this._l1power.getItem(power.get_hole_2())).add_str;
/* 1210 */       s6_2 += (this._l1power.getItem(power.get_hole_2())).add_dex;
/* 1211 */       s6_3 += (this._l1power.getItem(power.get_hole_2())).add_con;
/* 1212 */       s6_4 += (this._l1power.getItem(power.get_hole_2())).add_wis;
/* 1213 */       s6_5 += (this._l1power.getItem(power.get_hole_2())).add_int;
/* 1214 */       s6_6 += (this._l1power.getItem(power.get_hole_2())).add_cha;
/* 1215 */       aH_1 += (this._l1power.getItem(power.get_hole_2())).addMaxHP;
/* 1216 */       aM_1 += (this._l1power.getItem(power.get_hole_2())).addMaxMP;
/* 1217 */       aMR_1 += (this._l1power.getItem(power.get_hole_2())).m_def;
/* 1218 */       aSP_1 += (this._l1power.getItem(power.get_hole_2())).add_sp;
/* 1219 */       addDmgModifier += (this._l1power.getItem(power.get_hole_2())).dmg_modifier;
/* 1220 */       addHitModifier += (this._l1power.getItem(power.get_hole_2())).hit_modifier;
/* 1221 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_2())).bow_dmg_modifier;
/* 1222 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_2())).bow_hit_modifier;
/* 1223 */       drd += (this._l1power.getItem(power.get_hole_2())).addDamageReductionByArmor;
/*      */       
/* 1225 */       aHpr += (this._l1power.getItem(power.get_hole_3())).add_hpr;
/* 1226 */       aMpr += (this._l1power.getItem(power.get_hole_3())).add_mpr;
/* 1227 */       s6_1 += (this._l1power.getItem(power.get_hole_3())).add_str;
/* 1228 */       s6_2 += (this._l1power.getItem(power.get_hole_3())).add_dex;
/* 1229 */       s6_3 += (this._l1power.getItem(power.get_hole_3())).add_con;
/* 1230 */       s6_4 += (this._l1power.getItem(power.get_hole_3())).add_wis;
/* 1231 */       s6_5 += (this._l1power.getItem(power.get_hole_3())).add_int;
/* 1232 */       s6_6 += (this._l1power.getItem(power.get_hole_3())).add_cha;
/* 1233 */       aH_1 += (this._l1power.getItem(power.get_hole_3())).addMaxHP;
/* 1234 */       aM_1 += (this._l1power.getItem(power.get_hole_3())).addMaxMP;
/* 1235 */       aMR_1 += (this._l1power.getItem(power.get_hole_3())).m_def;
/* 1236 */       aSP_1 += (this._l1power.getItem(power.get_hole_3())).add_sp;
/* 1237 */       addDmgModifier += (this._l1power.getItem(power.get_hole_3())).dmg_modifier;
/* 1238 */       addHitModifier += (this._l1power.getItem(power.get_hole_3())).hit_modifier;
/* 1239 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_3())).bow_dmg_modifier;
/* 1240 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_3())).bow_hit_modifier;
/* 1241 */       drd += (this._l1power.getItem(power.get_hole_3())).addDamageReductionByArmor;
/*      */       
/* 1243 */       aHpr += (this._l1power.getItem(power.get_hole_4())).add_hpr;
/* 1244 */       aMpr += (this._l1power.getItem(power.get_hole_4())).add_mpr;
/* 1245 */       s6_1 += (this._l1power.getItem(power.get_hole_4())).add_str;
/* 1246 */       s6_2 += (this._l1power.getItem(power.get_hole_4())).add_dex;
/* 1247 */       s6_3 += (this._l1power.getItem(power.get_hole_4())).add_con;
/* 1248 */       s6_4 += (this._l1power.getItem(power.get_hole_4())).add_wis;
/* 1249 */       s6_5 += (this._l1power.getItem(power.get_hole_4())).add_int;
/* 1250 */       s6_6 += (this._l1power.getItem(power.get_hole_4())).add_cha;
/* 1251 */       aH_1 += (this._l1power.getItem(power.get_hole_4())).addMaxHP;
/* 1252 */       aM_1 += (this._l1power.getItem(power.get_hole_4())).addMaxMP;
/* 1253 */       aMR_1 += (this._l1power.getItem(power.get_hole_4())).m_def;
/* 1254 */       aSP_1 += (this._l1power.getItem(power.get_hole_4())).add_sp;
/* 1255 */       addDmgModifier += (this._l1power.getItem(power.get_hole_4())).dmg_modifier;
/* 1256 */       addHitModifier += (this._l1power.getItem(power.get_hole_4())).hit_modifier;
/* 1257 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_4())).bow_dmg_modifier;
/* 1258 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_4())).bow_hit_modifier;
/* 1259 */       drd += (this._l1power.getItem(power.get_hole_4())).addDamageReductionByArmor;
/*      */       
/* 1261 */       aHpr += (this._l1power.getItem(power.get_hole_5())).add_hpr;
/* 1262 */       aMpr += (this._l1power.getItem(power.get_hole_5())).add_mpr;
/* 1263 */       s6_1 += (this._l1power.getItem(power.get_hole_5())).add_str;
/* 1264 */       s6_2 += (this._l1power.getItem(power.get_hole_5())).add_dex;
/* 1265 */       s6_3 += (this._l1power.getItem(power.get_hole_5())).add_con;
/* 1266 */       s6_4 += (this._l1power.getItem(power.get_hole_5())).add_wis;
/* 1267 */       s6_5 += (this._l1power.getItem(power.get_hole_5())).add_int;
/* 1268 */       s6_6 += (this._l1power.getItem(power.get_hole_5())).add_cha;
/* 1269 */       aH_1 += (this._l1power.getItem(power.get_hole_5())).addMaxHP;
/* 1270 */       aM_1 += (this._l1power.getItem(power.get_hole_5())).addMaxMP;
/* 1271 */       aMR_1 += (this._l1power.getItem(power.get_hole_5())).m_def;
/* 1272 */       aSP_1 += (this._l1power.getItem(power.get_hole_5())).add_sp;
/* 1273 */       addDmgModifier += (this._l1power.getItem(power.get_hole_5())).dmg_modifier;
/* 1274 */       addHitModifier += (this._l1power.getItem(power.get_hole_5())).hit_modifier;
/* 1275 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_5())).bow_dmg_modifier;
/* 1276 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_5())).bow_hit_modifier;
/* 1277 */       drd += (this._l1power.getItem(power.get_hole_5())).addDamageReductionByArmor;
/*      */     } 
/*      */     
/* 1280 */     int pw_s1 = this._item.get_addstr() + attr_str;
/* 1281 */     int pw_s2 = this._item.get_adddex() + attr_dex;
/* 1282 */     int pw_s3 = this._item.get_addcon() + attr_con;
/* 1283 */     int pw_s4 = this._item.get_addwis() + attr_wis;
/* 1284 */     int pw_s5 = this._item.get_addint() + attr_int;
/* 1285 */     int pw_s6 = this._item.get_addcha() + attr_cha;
/*      */     
/* 1287 */     int pw_sHp = this._itemPower.get_addhp() + attr_hp;
/* 1288 */     int pw_sMp = this._item.get_addmp() + attr_mp;
/* 1289 */     int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
/* 1290 */     int pw_sSp = this._itemPower.getSp() + attr_sp;
/* 1291 */     int pw_sAk = this._itemPower.get_addattack();
/* 1292 */     int pw_sbAk = this._itemPower.get_addbowattack();
/*      */ 
/*      */     
/* 1295 */     int pw_sDg = this._item.getDmgModifierByArmor();
/* 1296 */     int pw_sHi = this._itemPower.getHitModifierByArmor();
/* 1297 */     int pw_mHi = this._item.getMagicHitModifierByArmor();
/*      */     
/* 1299 */     int pw_bDg = this._item.getBowDmgModifierByArmor();
/* 1300 */     int pw_bHi = this._item.getBowHitModifierByArmor() + this._itemInstance.getItemBowHit() + addBowHitModifier;
/*      */     
/* 1302 */     int pw_d4_1 = this._item.get_defense_fire();
/* 1303 */     int pw_d4_2 = this._item.get_defense_water();
/* 1304 */     int pw_d4_3 = this._item.get_defense_wind();
/* 1305 */     int pw_d4_4 = this._item.get_defense_earth();
/*      */     
/* 1307 */     int pw_k6_1 = this._item.get_regist_freeze();
/* 1308 */     int pw_k6_2 = this._item.get_regist_stone();
/* 1309 */     int pw_k6_3 = this._item.get_regist_sleep();
/* 1310 */     int pw_k6_4 = this._item.get_regist_blind();
/* 1311 */     int pw_k6_5 = this._item.get_regist_stun();
/* 1312 */     int pw_k6_6 = this._item.get_regist_sustain();
/*      */     
/* 1314 */     int addexp = this._item.getExpPoint();
/* 1315 */     int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg() + attr_dmgR;
/* 1316 */     int uhp = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
/* 1317 */     int uhp_num = this._item.get_uhp_number();
/* 1318 */     int PVPdmgReduction = this._item.getPVPdmgReduction() + PVP減傷;
/* 1319 */     int PVPdmg = this._item.getPVPdmg() + PVP增傷;
int cri = _item.get_CriticalChance();// 近距離暴擊率
int bow_cri = _item.get_Bow_CriticalChance();// 遠距離暴擊率
int magic_cri = _item.get_Magic_CriticalChance();// 魔法暴擊率
/*      */     
/* 1321 */     L1WilliamEnchantOrginal accessoryOrginal = null;
/* 1322 */     L1WilliamEnchantOrginal accessoryOrginalOk = null;
/* 1323 */     L1WilliamEnchantOrginal[] accessoryOrginalSize = EnchantOrginal.getInstance().getArmorList();
/* 1324 */     for (int j = 0; j < accessoryOrginalSize.length; j++) {
/* 1325 */       accessoryOrginalOk = EnchantOrginal.getInstance().getTemplate(j);
/* 1326 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1327 */         accessoryOrginal = accessoryOrginalOk; break;
/*      */       } 
/* 1329 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1330 */         accessoryOrginal = accessoryOrginalOk; break;
/*      */       } 
/* 1332 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1333 */         accessoryOrginal = accessoryOrginalOk;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1337 */     if (accessoryOrginal != null) {
/* 1338 */       pw_s1 += accessoryOrginal.getAddStr();
/* 1339 */       pw_s2 += accessoryOrginal.getAddDex();
/* 1340 */       pw_s3 += accessoryOrginal.getAddCon();
/* 1341 */       pw_s5 += accessoryOrginal.getAddInt();
/* 1342 */       pw_s4 += accessoryOrginal.getAddWis();
/* 1343 */       pw_s6 += accessoryOrginal.getAddCha();
/* 1344 */       ac += accessoryOrginal.getAddAc();
/* 1345 */       pw_sHp += accessoryOrginal.getAddMaxHp();
/* 1346 */       pw_sMp += accessoryOrginal.getAddMaxMp();
/* 1347 */       pw_sHpr += accessoryOrginal.getAddHpr();
/* 1348 */       pw_sMpr += accessoryOrginal.getAddMpr();
/*      */       
/* 1350 */       pw_sDg += accessoryOrginal.getAddDmg();
/* 1351 */       hit += accessoryOrginal.getAddHit();
/* 1352 */       pw_bDg += accessoryOrginal.getAddBowDmg();
/* 1353 */       pw_bHi += accessoryOrginal.getAddBowHit();
/* 1354 */       pw_drd += accessoryOrginal.getAddDmgReduction();
/* 1355 */       pw_sMr += accessoryOrginal.getAddMr();
/* 1356 */       pw_sSp += accessoryOrginal.getAddSp();
/* 1357 */       PVPdmg += accessoryOrginal.getPVPdmg();
/* 1358 */       PVPdmgReduction += accessoryOrginal.getPVPdmgReduction();
/* 1359 */       uhp += accessoryOrginal.getPotion_Heal();
/* 1360 */       uhp_num += accessoryOrginal.getPotion_Healling();
/* 1361 */       pw_mHi += accessoryOrginal.getAddMagicHit();
/*      */     } 
/* 1363 */     int add_hit = pw_sHi + hit + addHitModifier + this._itemInstance.getItemHit();
/* 1364 */     if (add_hit != 0) {
/* 1365 */       this._os.writeC(5);
/* 1366 */       this._os.writeC(add_hit);
/*      */     } 
/*      */     
/* 1369 */     int add_sdg = pw_sDg + greater()[2] + admg + this._itemInstance.getItemAttack() + pw_sAk + addDmgModifier;
/* 1370 */     if (add_sdg != 0) {
/* 1371 */       this._os.writeC(6);
/* 1372 */       this._os.writeC(add_sdg);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1381 */     int add_bdg = pw_bDg + greater()[3] + bdmg + this._itemInstance.getItemBowAttack() + pw_sbAk + addBowDmgModifier;
/* 1382 */     if (add_bdg != 0) {
/* 1383 */       this._os.writeC(35);
/* 1384 */       this._os.writeC(add_bdg);
/*      */     } 
/* 1386 */     int weight = this._item.getWeightReduction() + this._itemInstance.getWeightReduction();
/* 1387 */     if (weight != 0) {
/* 1388 */       this._os.writeC(39);
/* 1389 */       this._os.writeS("負重增加 +" + weight);
/*      */     } 
/* 1391 */     if (mdmg != 0) {
/* 1392 */       this._os.writeC(39);
/* 1393 */       this._os.writeS("魔法傷害+" + mdmg);
/*      */     } 
/*      */     
/* 1396 */     if (mdrd != 0) {
/* 1397 */       this._os.writeC(39);
/* 1398 */       this._os.writeS("魔法傷害減免+" + mdrd);
/*      */     } 
/*      */     
/* 1401 */     int bit = 0;
/* 1402 */     bit |= this._item.isUseRoyal() ? 1 : 0;
/* 1403 */     bit |= this._item.isUseKnight() ? 2 : 0;
/* 1404 */     bit |= this._item.isUseElf() ? 4 : 0;
/* 1405 */     bit |= this._item.isUseMage() ? 8 : 0;
/* 1406 */     bit |= this._item.isUseDarkelf() ? 16 : 0;
/* 1407 */     bit |= this._item.isUseDragonknight() ? 32 : 0;
/* 1408 */     bit |= this._item.isUseIllusionist() ? 64 : 0;
/* 1409 */     this._os.writeC(7);
/* 1410 */     this._os.writeC(bit);
/*      */ 
/*      */ 
/*      */     
/* 1414 */     if (pw_bHi > 0) {
/* 1415 */       this._os.writeC(24);
/*      */       
/* 1417 */       this._os.writeC(pw_bHi);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1439 */     int safeenchant = this._item.get_safeenchant();
/* 1440 */     if (safeenchant >= 0) {
/* 1441 */       this._os.writeC(39);
/* 1442 */       this._os.writeS("安定值: " + this._item.get_safeenchant());
/*      */     } 
/*      */     
/* 1445 */     int addstr = pw_s1 + s6_1 + this._itemInstance.getItemStr();
/* 1446 */     int adddex = pw_s2 + s6_2 + this._itemInstance.getItemDex();
/* 1447 */     int addcon = pw_s3 + s6_3 + this._itemInstance.getItemCon();
/* 1448 */     int addwis = pw_s4 + s6_4 + this._itemInstance.getItemWis();
/* 1449 */     int addint = pw_s5 + s6_5 + this._itemInstance.getItemInt();
/* 1450 */     int addcha = pw_s6 + s6_6 + this._itemInstance.getItemCha();
/*      */     
/* 1452 */     if (addstr == 1 && adddex == 1 && addcon == 1 && addwis == 1 && addint == 1 && addcha == 1) {
/* 1453 */       this._os.writeC(39);
/* 1454 */       this._os.writeS("全能力值 +1");
/*      */     } else {
/* 1456 */       if (addstr != 0) {
/* 1457 */         this._os.writeC(8);
/* 1458 */         this._os.writeC(addstr);
/*      */       } 
/*      */       
/* 1461 */       if (adddex != 0) {
/* 1462 */         this._os.writeC(9);
/* 1463 */         this._os.writeC(adddex);
/*      */       } 
/*      */       
/* 1466 */       if (addcon != 0) {
/* 1467 */         this._os.writeC(10);
/* 1468 */         this._os.writeC(addcon);
/*      */       } 
/*      */       
/* 1471 */       if (addwis != 0) {
/* 1472 */         this._os.writeC(11);
/* 1473 */         this._os.writeC(addwis);
/*      */       } 
/*      */       
/* 1476 */       if (addint != 0) {
/* 1477 */         this._os.writeC(12);
/* 1478 */         this._os.writeC(addint);
/*      */       } 
/*      */       
/* 1481 */       if (addcha != 0) {
/* 1482 */         this._os.writeC(13);
/* 1483 */         this._os.writeC(addcha);
/*      */       } 
/*      */     } 
/*      */     
/* 1487 */     int addhp = pw_sHp + aH_1 + greater()[0] + this._itemInstance.getItemHp();
/* 1488 */     if (addhp != 0) {
/* 1489 */       this._os.writeC(14);
/* 1490 */       this._os.writeH(addhp);
/*      */     } 
/*      */     
/* 1493 */     int addmp = pw_sMp + aM_1 + this._itemInstance.getItemMp();
/* 1494 */     if (addmp != 0) {
/* 1495 */       if (addmp <= 120) {
/* 1496 */         this._os.writeC(32);
/* 1497 */         this._os.writeC(addmp);
/*      */       } else {
/*      */         
/* 1500 */         this._os.writeC(39);
/* 1501 */         this._os.writeS("魔力上限+" + addmp);
/*      */       } 
/*      */     }
/*      */     
/* 1505 */     int addhpr = pw_sHpr + aHpr;
/* 1506 */     if (addhpr != 0) {
/* 1507 */       this._os.writeC(37);
/* 1508 */       this._os.writeC(addhpr);
/*      */     } 
/*      */     
/* 1511 */     int addmpr = pw_sMpr + aMpr;
/* 1512 */     if (addmpr != 0) {
/* 1513 */       this._os.writeC(38);
/* 1514 */       this._os.writeC(addmpr);
/*      */     } 
/*      */     
/* 1517 */     int freeze = 
/* 1518 */       pw_k6_1 + k6_1;
/*      */     
/* 1520 */     if (freeze != 0) {
/* 1521 */       this._os.writeC(15);
/* 1522 */       this._os.writeH(freeze);
/* 1523 */       this._os.writeC(33);
/* 1524 */       this._os.writeC(1);
/*      */     } 
/*      */     
/* 1527 */     int stone = 
/* 1528 */       pw_k6_2 + k6_2;
/* 1529 */     if (stone != 0) {
/* 1530 */       this._os.writeC(15);
/* 1531 */       this._os.writeH(stone);
/* 1532 */       this._os.writeC(33);
/* 1533 */       this._os.writeC(2);
/*      */     } 
/*      */     
/* 1536 */     int sleep = 
/* 1537 */       pw_k6_3 + k6_3;
/*      */     
/* 1539 */     if (sleep != 0) {
/* 1540 */       this._os.writeC(15);
/* 1541 */       this._os.writeH(sleep);
/* 1542 */       this._os.writeC(33);
/* 1543 */       this._os.writeC(3);
/*      */     } 
/*      */     
/* 1546 */     int blind = 
/* 1547 */       pw_k6_4 + k6_4;
/*      */     
/* 1549 */     if (blind != 0) {
/* 1550 */       this._os.writeC(15);
/* 1551 */       this._os.writeH(blind);
/* 1552 */       this._os.writeC(33);
/* 1553 */       this._os.writeC(4);
/*      */     } 
/*      */     
/* 1556 */     int stun = 
/* 1557 */       pw_k6_5 + k6_5;
/*      */     
/* 1559 */     if (stun != 0) {
/* 1560 */       this._os.writeC(15);
/* 1561 */       this._os.writeH(stun);
/* 1562 */       this._os.writeC(33);
/* 1563 */       this._os.writeC(5);
/*      */     } 
/*      */     
/* 1566 */     int sustain = 
/* 1567 */       pw_k6_6 + k6_6;
/*      */     
/* 1569 */     if (sustain != 0) {
/* 1570 */       this._os.writeC(15);
/* 1571 */       this._os.writeH(sustain);
/* 1572 */       this._os.writeC(33);
/* 1573 */       this._os.writeC(6);
/*      */     } 
/*      */     
/* 1576 */     int addmr = 
/* 1577 */       pw_sMr + aMR_1;
/* 1578 */     if (addmr != 0) {
/* 1579 */       this._os.writeC(15);
/* 1580 */       this._os.writeH(addmr);
/*      */     } 
/*      */     
/* 1583 */     int addsp = pw_sSp + aSP_1 + this._itemInstance.getItemSp();
/* 1584 */     if (addsp != 0) {
/* 1585 */       this._os.writeC(17);
/* 1586 */       this._os.writeC(addsp);
/*      */     } 
/*      */     
/* 1589 */     boolean haste = this._item.isHasteItem();
/*      */     
/* 1591 */     if (aSS_1 == 1) {
/* 1592 */       haste = true;
/*      */     }
/* 1594 */     if (haste) {
/* 1595 */       this._os.writeC(18);
/*      */     }
/*      */     
/* 1598 */     int fire = 
/* 1599 */       pw_d4_1 + d4_1;
/* 1600 */     if (fire != 0) {
/* 1601 */       this._os.writeC(27);
/* 1602 */       this._os.writeC(fire);
/*      */     } 
/*      */     
/* 1605 */     int water = 
/* 1606 */       pw_d4_2 + d4_2;
/* 1607 */     if (water != 0) {
/* 1608 */       this._os.writeC(28);
/* 1609 */       this._os.writeC(water);
/*      */     } 
/*      */     
/* 1612 */     int wind = 
/* 1613 */       pw_d4_3 + d4_3;
/* 1614 */     if (wind != 0) {
/* 1615 */       this._os.writeC(29);
/* 1616 */       this._os.writeC(wind);
/*      */     } 
/*      */     
/* 1619 */     int earth = 
/* 1620 */       pw_d4_4 + d4_4;
/* 1621 */     if (earth != 0) {
/* 1622 */       this._os.writeC(30);
/* 1623 */       this._os.writeC(earth);
/*      */     } 
/* 1625 */     if (drain_hp_rand != 0) {
/* 1626 */       this._os.writeC(39);
/* 1627 */       this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
/*      */     } 
/* 1629 */     if (drain_mp_rand != 0) {
/* 1630 */       this._os.writeC(39);
/* 1631 */       this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
/*      */     } 
/*      */     
/* 1634 */     if (skill_gfxid != 0) {
/* 1635 */       this._os.writeC(39);
/* 1636 */       this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
/*      */     } 
/*      */     
/* 1639 */     if (attr_物理格檔 != 0) {
/* 1640 */       this._os.writeC(39);
/* 1641 */       this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
/*      */     } 
/* 1643 */     if (attr_魔法格檔 != 0) {
/* 1644 */       this._os.writeC(39);
/* 1645 */       this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
/*      */     } 
/*      */     
/* 1648 */     if (addexp != 0) {
/* 1649 */       this._os.writeC(39);
/* 1650 */       this._os.writeS("狩獵經驗值 +" + addexp);
/*      */     } 
/*      */     
/* 1653 */     if (this._itemInstance.getItemMag_Red() != 0) {
/* 1654 */       this._os.writeC(39);
/* 1655 */       this._os.writeS("魔法減免-" + this._itemInstance.getItemMag_Red());
/*      */     } 
/* 1657 */     if (this._itemInstance.getItemDg() != 0) {
/* 1658 */       this._os.writeC(39);
/* 1659 */       this._os.writeS("物理閃避+" + this._itemInstance.getItemDg());
/*      */     } 
/* 1661 */     if (this._itemInstance.getItemistSustain() != 0) {
/* 1662 */       this._os.writeC(39);
/* 1663 */       this._os.writeS("支撐耐性+" + this._itemInstance.getItemistSustain());
/*      */     } 
/* 1665 */     if (this._itemInstance.getItemistStun() != 0) {
/* 1666 */       this._os.writeC(39);
/* 1667 */       this._os.writeS("昏迷耐性+" + this._itemInstance.getItemistStun());
/*      */     } 
/* 1669 */     if (this._itemInstance.getItemistStone() != 0) {
/* 1670 */       this._os.writeC(39);
/* 1671 */       this._os.writeS("石化耐性+" + this._itemInstance.getItemistStone());
/*      */     } 
/* 1673 */     if (this._itemInstance.getItemistSleep() != 0) {
/* 1674 */       this._os.writeC(39);
/* 1675 */       this._os.writeS("睡眠耐性+" + this._itemInstance.getItemistSleep());
/*      */     } 
/* 1677 */     if (this._itemInstance.getItemistFreeze() != 0) {
/* 1678 */       this._os.writeC(39);
/* 1679 */       this._os.writeS("寒冰耐性+" + this._itemInstance.getItemistFreeze());
/*      */     } 
/* 1681 */     if (this._itemInstance.getItemistBlind() != 0) {
/* 1682 */       this._os.writeC(39);
/* 1683 */       this._os.writeS("暗黑耐性+" + this._itemInstance.getItemistBlind());
/*      */     } 
/* 1685 */     int alldrd = pw_drd + drd + greater()[4];
/* 1686 */     if (alldrd != 0) {
/* 1687 */       this._os.writeC(39);
/* 1688 */       this._os.writeS("傷害減免+" + alldrd);
/*      */     } 
/*      */     
/* 1691 */     if (pw_mHi != 0) {
/* 1692 */       this._os.writeC(39);
/* 1693 */       this._os.writeS("魔法命中+" + pw_mHi);
/*      */     } 
/*      */     
/* 1696 */     if (mcri != 0) {
/* 1697 */       this._os.writeC(39);
/* 1698 */       this._os.writeS("魔法爆擊率+" + mcri);
/*      */     } 
/*      */ 
int all_mcri = mcri + magic_cri;
if (all_mcri != 0) {
	_os.writeC(39);
	_os.writeS("魔法爆擊率 +" + all_mcri);
}

if (cri != 0) {
	_os.writeC(39);
	_os.writeS("近距離爆擊率 +" + cri);
}

if (bow_cri != 0) {
	_os.writeC(39);
	_os.writeS("遠距離爆擊率 +" + bow_cri);
}
/*      */ 
/*      */ 
/*      */     
/* 1704 */     StringBuilder name = new StringBuilder();
/* 1705 */     int adduhp = uhp + greater()[1];
/* 1706 */     if (adduhp != 0 || uhp_num != 0) {
/* 1707 */       name.append("藥水回復量+" + adduhp + "%");
/* 1708 */       if (uhp_num != 0) {
/* 1709 */         name.append("+" + uhp_num);
/*      */       }
/* 1711 */       this._os.writeC(39);
/* 1712 */       this._os.writeS(name.toString());
/*      */     } 
/* 1714 */     if (attr_potion_heal != 0) {
/* 1715 */       this._os.writeC(39);
/* 1716 */       this._os.writeS("藥水回復增量+" + attr_potion_heal);
/*      */     } 
/* 1718 */     if (this._item.getclassname().equalsIgnoreCase("Venom_Resist") || 
/* 1719 */       this._item.getclassname().equalsIgnoreCase("ElitePlateMail_Antharas")) {
/* 1720 */       this._os.writeC(39);
/* 1721 */       this._os.writeS("防護中毒狀態");
/*      */     } 
/*      */ 
/*      */     
/* 1725 */     if (weapon_pro != 0) {
/* 1726 */       this._os.writeC(39);
/* 1727 */       this._os.writeS("魔武發動率+" + weapon_pro);
/*      */     } 
/* 1729 */     if (weapon_dmg != 0) {
/* 1730 */       this._os.writeC(39);
/* 1731 */       this._os.writeS("魔武增傷害+" + weapon_dmg);
/*      */     } 
/*      */     
/* 1734 */     if (PVPdmgReduction != 0) {
/* 1735 */       this._os.writeC(39);
/* 1736 */       this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
/*      */     } 
/* 1738 */     if (PVPdmg != 0) {
/* 1739 */       this._os.writeC(39);
/* 1740 */       this._os.writeS("PVP傷害增加+" + PVPdmg);
/*      */     } 
/*      */     
/* 1743 */     if (!this._item.isTradable()) {
/* 1744 */       this._os.writeC(39);
/* 1745 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/* 1748 */     if (this._item.isCantDelete()) {
/* 1749 */       this._os.writeC(39);
/* 1750 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/* 1753 */     if (this._item.get_safeenchant() < 0) {
/* 1754 */       this._os.writeC(39);
/* 1755 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1760 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/* 1763 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/* 1765 */         this._os.writeC(39);
/* 1766 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 1771 */       as.clear();
/*      */     } 
/*      */     
/* 1774 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream accessories() {
/* 1782 */     int attr_str = 0;
/* 1783 */     int attr_con = 0;
/* 1784 */     int attr_dex = 0;
/* 1785 */     int attr_int = 0;
/* 1786 */     int attr_wis = 0;
/* 1787 */     int attr_cha = 0;
/* 1788 */     int attr_hp = 0;
/* 1789 */     int attr_mp = 0;
/* 1790 */     int attr_sp = 0;
/* 1791 */     int attr_mr = 0;
/* 1792 */     int drain_min_hp = 0;
/* 1793 */     int drain_max_hp = 0;
/* 1794 */     int drain_hp_rand = 0;
/* 1795 */     int attr_hpr = 0;
/* 1796 */     int attr_mpr = 0;
/* 1797 */     int drain_min_mp = 0;
/* 1798 */     int drain_max_mp = 0;
/* 1799 */     int drain_mp_rand = 0;
/*      */     
/* 1801 */     int skill_gfxid = 0;
/* 1802 */     int skill_rand = 0;
/* 1803 */     int skill_dmg = 0;
/* 1804 */     int attr_魔法格檔 = 0;
/* 1805 */     int attr_物理格檔 = 0;
/*      */     
/* 1807 */     int PVP增傷 = 0;
/* 1808 */     int PVP減傷 = 0;
/* 1809 */     int attr_potion_heal = 0;
/*      */     
/* 1811 */     L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
/* 1812 */     if (attr_char != null) {
/* 1813 */       L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
/*      */       
/* 1815 */       attr_str = attr.get_add_str();
/* 1816 */       attr_con = attr.get_add_con();
/* 1817 */       attr_dex = attr.get_add_dex();
/* 1818 */       attr_int = attr.get_add_int();
/* 1819 */       attr_wis = attr.get_add_wis();
/* 1820 */       attr_cha = attr.get_add_cha();
/* 1821 */       attr_hp = attr.get_add_hp();
/* 1822 */       attr_mp = attr.get_add_mp();
/* 1823 */       attr_sp = attr.get_add_sp();
/* 1824 */       attr_mr = attr.get_add_m_def();
/* 1825 */       drain_min_hp = attr.get_add_drain_min_hp();
/* 1826 */       drain_max_hp = attr.get_add_drain_max_hp();
/* 1827 */       drain_hp_rand = attr.get_drain_hp_rand();
/* 1828 */       drain_min_mp = attr.get_add_drain_min_mp();
/* 1829 */       drain_max_mp = attr.get_add_drain_max_mp();
/* 1830 */       drain_mp_rand = attr.get_drain_mp_rand();
/* 1831 */       attr_hpr = attr.get_add_hpr();
/* 1832 */       attr_mpr = attr.get_add_mpr();
/* 1833 */       skill_gfxid = attr.get_add_skill_gfxid();
/* 1834 */       skill_rand = attr.get_add_skill_rand();
/* 1835 */       skill_dmg = attr.get_add_skill_dmg();
/*      */       
/* 1837 */       attr_魔法格檔 = attr.get魔法格檔();
/* 1838 */       attr_物理格檔 = attr.get物理格檔();
/*      */       
/* 1840 */       PVP增傷 = attr.get_add_pvp_dmg();
/* 1841 */       PVP減傷 = attr.get_add_pvp_redmg();
/* 1842 */       attr_potion_heal = attr.get_add_potion_heal();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1847 */     int ac = this._item.get_ac() - this._itemInstance.getItemAc();
/* 1848 */     if (ac < 0) {
/* 1849 */       ac = Math.abs(ac);
/*      */     }
/*      */ 
/*      */     
/* 1853 */     if (this._item.get_greater() == 0) {
/* 1854 */       this._os.writeC(39);
/* 1855 */       this._os.writeS("飾品級別：耐性");
/* 1856 */     } else if (this._item.get_greater() == 1) {
/* 1857 */       this._os.writeC(39);
/* 1858 */       this._os.writeS("飾品級別：熱情");
/* 1859 */     } else if (this._item.get_greater() == 2) {
/* 1860 */       this._os.writeC(39);
/* 1861 */       this._os.writeS("飾品級別：意志");
/*      */     } 
/*      */     
/* 1864 */     int s6_1 = 0;
/* 1865 */     int s6_2 = 0;
/* 1866 */     int s6_3 = 0;
/* 1867 */     int s6_4 = 0;
/* 1868 */     int s6_5 = 0;
/* 1869 */     int s6_6 = 0;
/* 1870 */     int aH_1 = 0;
/* 1871 */     int aM_1 = 0;
/* 1872 */     int aMR_1 = 0;
/* 1873 */     int aSP_1 = 0;
/* 1874 */     int aSS_1 = 0;
/* 1875 */     int d4_1 = 0;
/* 1876 */     int d4_2 = 0;
/* 1877 */     int d4_3 = 0;
/* 1878 */     int d4_4 = 0;
/* 1879 */     int k6_1 = 0;
/* 1880 */     int k6_2 = 0;
/* 1881 */     int k6_3 = 0;
/* 1882 */     int k6_4 = 0;
/* 1883 */     int k6_5 = 0;
/* 1884 */     int k6_6 = 0;
/* 1885 */     int aHpr = 0;
/* 1886 */     int aMpr = 0;
/* 1887 */     int admg = 0;
/* 1888 */     int drd = 0;
/* 1889 */     int mdmg = 0;
/* 1890 */     int mdrd = 0;
/* 1891 */     int bdmg = 0;
/* 1892 */     int hit = 0;
/* 1893 */     int bhit = 0;
/* 1894 */     int mcri = 0;
/* 1895 */     int weapon_pro = this._item.getweaponskillpro();
/* 1896 */     int weapon_dmg = this._item.getweaponskilldmg();
/* 1897 */     if (this._itemInstance.isMatch()) {
/* 1898 */       s6_1 = this._item.get_mode()[0];
/* 1899 */       s6_2 = this._item.get_mode()[1];
/* 1900 */       s6_3 = this._item.get_mode()[2];
/* 1901 */       s6_4 = this._item.get_mode()[3];
/* 1902 */       s6_5 = this._item.get_mode()[4];
/* 1903 */       s6_6 = this._item.get_mode()[5];
/* 1904 */       aH_1 = this._item.get_mode()[6];
/* 1905 */       aM_1 = this._item.get_mode()[7];
/* 1906 */       aMR_1 = this._item.get_mode()[8];
/* 1907 */       aSP_1 = this._item.get_mode()[9];
/* 1908 */       aSS_1 = this._item.get_mode()[10];
/* 1909 */       d4_1 = this._item.get_mode()[11];
/* 1910 */       d4_2 = this._item.get_mode()[12];
/* 1911 */       d4_3 = this._item.get_mode()[13];
/* 1912 */       d4_4 = this._item.get_mode()[14];
/* 1913 */       k6_1 = this._item.get_mode()[15];
/* 1914 */       k6_2 = this._item.get_mode()[16];
/* 1915 */       k6_3 = this._item.get_mode()[17];
/* 1916 */       k6_4 = this._item.get_mode()[18];
/* 1917 */       k6_5 = this._item.get_mode()[19];
/* 1918 */       k6_6 = this._item.get_mode()[20];
/* 1919 */       aHpr = this._item.get_mode()[21];
/* 1920 */       aMpr = this._item.get_mode()[22];
/* 1921 */       admg = this._item.get_mode()[23];
/* 1922 */       drd = this._item.get_mode()[24];
/* 1923 */       mdmg = this._item.get_mode()[25];
/* 1924 */       mdrd = this._item.get_mode()[26];
/* 1925 */       bdmg = this._item.get_mode()[27];
/* 1926 */       hit = this._item.get_mode()[28];
/* 1927 */       bhit = this._item.get_mode()[29];
/* 1928 */       mcri = this._item.get_mode()[30];
/* 1929 */       weapon_pro = this._item.get_mode()[31];
/* 1930 */       weapon_dmg = this._item.get_mode()[32];
/*      */     } 
/*      */     
/* 1933 */     if (this._itemInstance.get_time() != null) {
/* 1934 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 1935 */       this._os.writeC(39);
/* 1936 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*      */     
/* 1939 */     int pw_s1 = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
/* 1940 */     int pw_s2 = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
/* 1941 */     int pw_s3 = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
/* 1942 */     int pw_s4 = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
/* 1943 */     int pw_s5 = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
/* 1944 */     int pw_s6 = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
/*      */ 
/*      */ 
/*      */     
/* 1948 */     int pw_sHp = this._itemPower.get_addhp() + attr_hp;
/* 1949 */     int pw_sMp = this._item.get_addmp() + attr_mp;
/*      */     
/* 1951 */     int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
/* 1952 */     int pw_sSp = this._itemPower.getSp() + this._itemInstance.getItemSp() + attr_sp;
/*      */     
/* 1954 */     int pw_sDg = this._item.getDmgModifierByArmor() + this._itemInstance.getItemAttack();
/* 1955 */     int pw_sHi = this._itemPower.getHitModifierByArmor();
/* 1956 */     int pw_mHi = this._item.getMagicHitModifierByArmor();
/*      */     
/* 1958 */     int pw_bDg = this._item.getBowDmgModifierByArmor() + this._itemInstance.getItemBowAttack();
/* 1959 */     int pw_bHi = this._item.getBowHitModifierByArmor();
/*      */     
/* 1961 */     int pw_d4_1 = this._item.get_defense_fire();
/* 1962 */     int pw_d4_2 = this._item.get_defense_water();
/* 1963 */     int pw_d4_3 = this._item.get_defense_wind();
/* 1964 */     int pw_d4_4 = this._item.get_defense_earth();
/*      */     
/* 1966 */     int pw_k6_1 = this._item.get_regist_freeze();
/* 1967 */     int pw_k6_2 = this._item.get_regist_stone();
/* 1968 */     int pw_k6_3 = this._item.get_regist_sleep();
/* 1969 */     int pw_k6_4 = this._item.get_regist_blind();
/* 1970 */     int pw_k6_5 = this._item.get_regist_stun();
/* 1971 */     int pw_k6_6 = this._item.get_regist_sustain();
/* 1972 */     int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
/* 1973 */     int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
/* 1974 */     int addexp = this._item.getExpPoint();
/* 1975 */     int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg();
/*      */     
/* 1977 */     int pvpdmg = 0;
/* 1978 */     int pvpdmg_r = 0;
/* 1979 */     int potion_heal = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
/* 1980 */     int potion_healling = this._item.get_uhp_number();

int cri = _item.get_CriticalChance();// 近距離暴擊率
int bow_cri = _item.get_Bow_CriticalChance();// 遠距離暴擊率
int magic_cri = _item.get_Magic_CriticalChance();// 魔法暴擊率
/*      */     
/* 1982 */     L1WilliamEnchantOrginal accessoryOrginal1 = null;
/* 1983 */     L1WilliamEnchantOrginal accessoryOrginalOk1 = null;
/* 1984 */     L1WilliamEnchantOrginal[] accessoryOrginalSize1 = EnchantOrginal.getInstance().getArmorList();
/* 1985 */     for (int j = 0; j < accessoryOrginalSize1.length; j++) {
/* 1986 */       accessoryOrginalOk1 = EnchantOrginal.getInstance().getTemplate(j);
/* 1987 */       if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1988 */         accessoryOrginal1 = accessoryOrginalOk1; break;
/*      */       } 
/* 1990 */       if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == 0 && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1991 */         accessoryOrginal1 = accessoryOrginalOk1; break;
/*      */       } 
/* 1993 */       if (accessoryOrginalOk1.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk1.getitemid() == 0 && accessoryOrginalOk1.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 1994 */         accessoryOrginal1 = accessoryOrginalOk1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 1998 */     if (accessoryOrginal1 != null) {
/* 1999 */       pw_s1 += accessoryOrginal1.getAddStr();
/* 2000 */       pw_s2 += accessoryOrginal1.getAddDex();
/* 2001 */       pw_s3 += accessoryOrginal1.getAddCon();
/* 2002 */       pw_s5 += accessoryOrginal1.getAddInt();
/* 2003 */       pw_s4 += accessoryOrginal1.getAddWis();
/* 2004 */       pw_s6 += accessoryOrginal1.getAddCha();
/* 2005 */       ac += accessoryOrginal1.getAddAc();
/* 2006 */       pw_sHp += accessoryOrginal1.getAddMaxHp();
/* 2007 */       pw_sMp += accessoryOrginal1.getAddMaxMp();
/* 2008 */       pw_sHpr += accessoryOrginal1.getAddHpr();
/* 2009 */       pw_sMpr += accessoryOrginal1.getAddMpr();
/*      */       
/* 2011 */       pw_sDg += accessoryOrginal1.getAddDmg();
/* 2012 */       hit += accessoryOrginal1.getAddHit();
/* 2013 */       pw_bDg += accessoryOrginal1.getAddBowDmg();
/* 2014 */       pw_bHi += accessoryOrginal1.getAddBowHit();
/* 2015 */       pw_drd += accessoryOrginal1.getAddDmgReduction();
/* 2016 */       pw_sMr += accessoryOrginal1.getAddMr();
/* 2017 */       pw_sSp += accessoryOrginal1.getAddSp();
/* 2018 */       pvpdmg += accessoryOrginal1.getPVPdmg();
/* 2019 */       pvpdmg_r += accessoryOrginal1.getPVPdmgReduction();
/* 2020 */       potion_heal += accessoryOrginal1.getPotion_Heal();
/* 2021 */       potion_healling += accessoryOrginal1.getPotion_Healling();
/* 2022 */       pw_mHi += accessoryOrginal1.getAddMagicHit();
/*      */     } 
/*      */ 
/*      */     
/* 2026 */     L1WilliamEnchantAccessory accessoryOrginal = null;
/* 2027 */     L1WilliamEnchantAccessory accessoryOrginalOk = null;
/* 2028 */     L1WilliamEnchantAccessory[] accessoryOrginalSize = EnchantAccessory.getInstance().getArmorList();
/* 2029 */     for (int i = 0; i < accessoryOrginalSize.length; i++) {
/* 2030 */       accessoryOrginalOk = EnchantAccessory.getInstance().getTemplate(i);
/* 2031 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getUseType() && accessoryOrginalOk.getStrength() == this._itemInstance.getItem().get_greater() && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 2032 */         accessoryOrginal = accessoryOrginalOk;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2037 */     if (accessoryOrginal != null) {
/* 2038 */       pw_s1 += accessoryOrginal.getAddStr();
/* 2039 */       pw_s2 += accessoryOrginal.getAddDex();
/* 2040 */       pw_s3 += accessoryOrginal.getAddCon();
/* 2041 */       pw_s5 += accessoryOrginal.getAddInt();
/* 2042 */       pw_s4 += accessoryOrginal.getAddWis();
/* 2043 */       pw_s6 += accessoryOrginal.getAddCha();
/* 2044 */       ac += accessoryOrginal.getAddAc();
/* 2045 */       pw_sHp += accessoryOrginal.getAddMaxHp();
/* 2046 */       pw_sMp += accessoryOrginal.getAddMaxMp();
/* 2047 */       pw_sHpr += accessoryOrginal.getAddHpr();
/* 2048 */       pw_sMpr += accessoryOrginal.getAddMpr();
/*      */       
/* 2050 */       pw_sDg += accessoryOrginal.getAddDmg();
/* 2051 */       hit += accessoryOrginal.getAddHit();
/* 2052 */       pw_bDg += accessoryOrginal.getAddBowDmg();
/* 2053 */       pw_bHi += accessoryOrginal.getAddBowHit();
/* 2054 */       pw_drd += accessoryOrginal.getAddDmgReduction();
/* 2055 */       pw_sMr += accessoryOrginal.getAddMr();
/* 2056 */       pw_sSp += accessoryOrginal.getAddSp();
/* 2057 */       pvpdmg += accessoryOrginal.getPVPdmg();
/* 2058 */       pvpdmg_r += accessoryOrginal.getPVPdmgReduction();
/* 2059 */       potion_heal += accessoryOrginal.getPotion_Heal();
/* 2060 */       potion_healling += accessoryOrginal.getPotion_Healling();
/* 2061 */       pw_mHi += accessoryOrginal.getAddMagicHit();
/*      */     } 
/*      */     
/* 2064 */     this._os.writeC(19);
/* 2065 */     this._os.writeC(ac);
/* 2066 */     this._os.writeC(this._item.getMaterial());
/* 2067 */     this._os.writeC(-1);
/* 2068 */     this._os.writeD(this._itemInstance.getWeight());
/*      */ 
/*      */ 
/*      */     
/* 2072 */     int add_hit = pw_sHi + hit + this._itemInstance.getItemHit();
/* 2073 */     if (add_hit != 0) {
/* 2074 */       this._os.writeC(5);
/* 2075 */       this._os.writeC(add_hit);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2084 */     int add_sdg = pw_sDg + greater()[4] + admg;
/* 2085 */     if (add_sdg != 0) {
/* 2086 */       this._os.writeC(6);
/* 2087 */       this._os.writeC(add_sdg);
/*      */     } 
/*      */     
/* 2090 */     int add_bowhit = pw_bHi + bhit;
/* 2091 */     if (add_bowhit != 0) {
/* 2092 */       this._os.writeC(24);
/* 2093 */       this._os.writeC(add_bowhit);
/*      */     } 
/*      */     
/* 2096 */     int add_bdg = pw_bDg + greater()[5] + bdmg;
/* 2097 */     if (add_bdg != 0) {
/* 2098 */       this._os.writeC(35);
/* 2099 */       this._os.writeC(add_bdg);
/*      */     } 
/* 2101 */     int weight = this._item.getWeightReduction() + this._itemInstance.getWeightReduction();
/* 2102 */     if (weight != 0) {
/* 2103 */       this._os.writeC(39);
/* 2104 */       this._os.writeS("負重增加 +" + weight);
/*      */     } 
/* 2106 */     if (mdmg != 0) {
/* 2107 */       this._os.writeC(39);
/* 2108 */       this._os.writeS("魔法傷害+" + mdmg);
/*      */     } 
/*      */     
/* 2111 */     if (mdrd != 0) {
/* 2112 */       this._os.writeC(39);
/* 2113 */       this._os.writeS("魔法傷害減免+" + mdrd);
/*      */     } 
/*      */ 
/*      */     
/* 2117 */     int PVPdmgReduction = pvpdmg_r;
/* 2118 */     if (PVPdmgReduction != 0) {
/* 2119 */       this._os.writeC(39);
/* 2120 */       this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
/*      */     } 
/* 2122 */     int PVPdmg = pvpdmg;
/* 2123 */     if (PVPdmg != 0) {
/* 2124 */       this._os.writeC(39);
/* 2125 */       this._os.writeS("PVP傷害增加+" + PVPdmg);
/*      */     } 
/* 2127 */     if (drain_hp_rand != 0) {
/* 2128 */       this._os.writeC(39);
/* 2129 */       this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
/*      */     } 
/* 2131 */     if (drain_mp_rand != 0) {
/* 2132 */       this._os.writeC(39);
/* 2133 */       this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
/*      */     } 
/*      */     
/* 2136 */     if (skill_gfxid != 0) {
/* 2137 */       this._os.writeC(39);
/* 2138 */       this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
/*      */     } 
/*      */     
/* 2141 */     if (attr_物理格檔 != 0) {
/* 2142 */       this._os.writeC(39);
/* 2143 */       this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
/*      */     } 
/* 2145 */     if (attr_魔法格檔 != 0) {
/* 2146 */       this._os.writeC(39);
/* 2147 */       this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
/*      */     } 
/* 2149 */     if (potion_heal != 0 && potion_healling != 0) {
/* 2150 */       this._os.writeC(39);
/* 2151 */       this._os.writeS("藥水回復量+" + potion_heal + "%+" + potion_healling);
/* 2152 */     } else if (potion_heal != 0 && potion_healling == 0) {
/* 2153 */       this._os.writeC(39);
/* 2154 */       this._os.writeS("藥水回復量+" + potion_heal + "%");
/*      */     } 
/* 2156 */     if (attr_potion_heal != 0) {
/* 2157 */       this._os.writeC(39);
/* 2158 */       this._os.writeS("藥水回復增量+" + attr_potion_heal);
/*      */     } 
/* 2160 */     if (weapon_pro != 0) {
/* 2161 */       this._os.writeC(39);
/* 2162 */       this._os.writeS("魔武發動率+" + weapon_pro);
/*      */     } 
/* 2164 */     if (weapon_dmg != 0) {
/* 2165 */       this._os.writeC(39);
/* 2166 */       this._os.writeS("魔武增傷害+" + weapon_dmg);
/*      */     } 
/*      */     
/* 2169 */     int bit = 0;
/* 2170 */     bit |= this._item.isUseRoyal() ? 1 : 0;
/* 2171 */     bit |= this._item.isUseKnight() ? 2 : 0;
/* 2172 */     bit |= this._item.isUseElf() ? 4 : 0;
/* 2173 */     bit |= this._item.isUseMage() ? 8 : 0;
/* 2174 */     bit |= this._item.isUseDarkelf() ? 16 : 0;
/* 2175 */     bit |= this._item.isUseDragonknight() ? 32 : 0;
/* 2176 */     bit |= this._item.isUseIllusionist() ? 64 : 0;
/* 2177 */     this._os.writeC(7);
/* 2178 */     this._os.writeC(bit);
/*      */     
/* 2180 */     int addstr = pw_s1 + s6_1;
/* 2181 */     if (addstr != 0) {
/* 2182 */       this._os.writeC(8);
/* 2183 */       this._os.writeC(addstr);
/*      */     } 
/*      */     
/* 2186 */     int adddex = pw_s2 + s6_2;
/* 2187 */     if (adddex != 0) {
/* 2188 */       this._os.writeC(9);
/* 2189 */       this._os.writeC(adddex);
/*      */     } 
/*      */     
/* 2192 */     int addcon = pw_s3 + s6_3;
/* 2193 */     if (addcon != 0) {
/* 2194 */       this._os.writeC(10);
/* 2195 */       this._os.writeC(addcon);
/*      */     } 
/*      */     
/* 2198 */     int addwis = pw_s4 + s6_4;
/* 2199 */     if (addwis != 0) {
/* 2200 */       this._os.writeC(11);
/* 2201 */       this._os.writeC(addwis);
/*      */     } 
/*      */     
/* 2204 */     int addint = pw_s5 + s6_5;
/* 2205 */     if (addint != 0) {
/* 2206 */       this._os.writeC(12);
/* 2207 */       this._os.writeC(addint);
/*      */     } 
/*      */     
/* 2210 */     int addcha = pw_s6 + s6_6;
/* 2211 */     if (addcha != 0) {
/* 2212 */       this._os.writeC(13);
/* 2213 */       this._os.writeC(addcha);
/*      */     } 
/*      */     
/* 2216 */     int addhp = pw_sHp + aH_1 + greater()[0] + this._itemInstance.getItemHp();
/* 2217 */     if (addhp != 0) {
/* 2218 */       this._os.writeC(14);
/* 2219 */       this._os.writeH(addhp);
/*      */     } 
/*      */     
/* 2222 */     int addmp = pw_sMp + aM_1 + greater()[1] + this._itemInstance.getItemMp();
/* 2223 */     if (addmp != 0) {
/* 2224 */       if (addmp <= 120) {
/* 2225 */         this._os.writeC(32);
/* 2226 */         this._os.writeC(addmp);
/*      */       } else {
/*      */         
/* 2229 */         this._os.writeC(39);
/* 2230 */         this._os.writeS("魔力上限+" + addmp);
/*      */       } 
/*      */     }
/*      */     
/* 2234 */     int addhpr = pw_sHpr + aHpr;
/* 2235 */     if (addhpr != 0) {
/* 2236 */       this._os.writeC(37);
/* 2237 */       this._os.writeC(addhpr);
/*      */     } 
/*      */     
/* 2240 */     int addmpr = pw_sMpr + aMpr;
/* 2241 */     if (addmpr != 0) {
/* 2242 */       this._os.writeC(38);
/* 2243 */       this._os.writeC(addmpr);
/*      */     } 
/*      */     
/* 2246 */     int freeze = pw_k6_1 + k6_1;
/* 2247 */     if (freeze != 0) {
/* 2248 */       this._os.writeC(15);
/* 2249 */       this._os.writeH(freeze);
/* 2250 */       this._os.writeC(33);
/* 2251 */       this._os.writeC(1);
/*      */     } 
/*      */     
/* 2254 */     int stone = pw_k6_2 + k6_2;
/* 2255 */     if (stone != 0) {
/* 2256 */       this._os.writeC(15);
/* 2257 */       this._os.writeH(stone);
/* 2258 */       this._os.writeC(33);
/* 2259 */       this._os.writeC(2);
/*      */     } 
/*      */     
/* 2262 */     int sleep = pw_k6_3 + k6_3;
/* 2263 */     if (sleep != 0) {
/* 2264 */       this._os.writeC(15);
/* 2265 */       this._os.writeH(sleep);
/* 2266 */       this._os.writeC(33);
/* 2267 */       this._os.writeC(3);
/*      */     } 
/*      */     
/* 2270 */     int blind = pw_k6_4 + k6_4;
/* 2271 */     if (blind != 0) {
/* 2272 */       this._os.writeC(15);
/* 2273 */       this._os.writeH(blind);
/* 2274 */       this._os.writeC(33);
/* 2275 */       this._os.writeC(4);
/*      */     } 
/*      */     
/* 2278 */     int stun = pw_k6_5 + k6_5;
/* 2279 */     if (stun != 0) {
/* 2280 */       this._os.writeC(15);
/* 2281 */       this._os.writeH(stun);
/* 2282 */       this._os.writeC(33);
/* 2283 */       this._os.writeC(5);
/*      */     } 
/*      */     
/* 2286 */     int sustain = pw_k6_6 + k6_6;
/* 2287 */     if (sustain != 0) {
/* 2288 */       this._os.writeC(15);
/* 2289 */       this._os.writeH(sustain);
/* 2290 */       this._os.writeC(33);
/* 2291 */       this._os.writeC(6);
/*      */     } 
/*      */     
/* 2294 */     int addmr = pw_sMr + aMR_1 + greater()[10];
/* 2295 */     if (addmr != 0) {
/* 2296 */       this._os.writeC(15);
/* 2297 */       this._os.writeH(addmr);
/*      */     } 
/*      */     
/* 2300 */     int addsp = pw_sSp + aSP_1 + greater()[9];
/* 2301 */     if (addsp != 0) {
/* 2302 */       this._os.writeC(17);
/* 2303 */       this._os.writeC(addsp);
/*      */     } 
/*      */     
/* 2306 */     boolean haste = this._item.isHasteItem();
/* 2307 */     if (aSS_1 == 1) {
/* 2308 */       haste = true;
/*      */     }
/* 2310 */     if (haste) {
/* 2311 */       this._os.writeC(18);
/*      */     }
/*      */     
/* 2314 */     int defense_fire = pw_d4_1 + d4_1;
/* 2315 */     if (defense_fire != 0) {
/* 2316 */       this._os.writeC(27);
/* 2317 */       this._os.writeC(defense_fire);
/*      */     } 
/*      */     
/* 2320 */     int defense_water = pw_d4_2 + d4_2;
/* 2321 */     if (defense_water != 0) {
/* 2322 */       this._os.writeC(28);
/* 2323 */       this._os.writeC(defense_water);
/*      */     } 
/*      */     
/* 2326 */     int defense_wind = pw_d4_3 + d4_3;
/* 2327 */     if (defense_wind != 0) {
/* 2328 */       this._os.writeC(29);
/* 2329 */       this._os.writeC(defense_wind);
/*      */     } 
/*      */     
/* 2332 */     int defense_earth = pw_d4_4 + d4_4;
/* 2333 */     if (defense_earth != 0) {
/* 2334 */       this._os.writeC(30);
/* 2335 */       this._os.writeC(defense_earth);
/*      */     } 
/*      */     
/* 2338 */     if (addexp != 0) {
/* 2339 */       this._os.writeC(36);
/* 2340 */       this._os.writeC(addexp);
/*      */     } 
/*      */     
/* 2343 */     int alldrd = pw_drd + drd + greater()[12];
/* 2344 */     if (alldrd != 0) {
/* 2345 */       this._os.writeC(39);
/* 2346 */       this._os.writeS("傷害減免+" + alldrd);
/*      */     } 
/*      */     
/* 2349 */     int addMhit = pw_mHi + greater()[6];
/* 2350 */     if (addMhit != 0) {
/* 2351 */       this._os.writeC(39);
/* 2352 */       this._os.writeS("魔法命中+" + addMhit);
/*      */     } 
/*      */ 
/*      */     
/* 2356 */     if (mcri != 0) {
/* 2357 */       this._os.writeC(39);
/* 2358 */       this._os.writeS("魔法爆擊率+" + mcri);
/*      */     } 
/* 2360 */     if (PVP減傷 != 0) {
/* 2361 */       this._os.writeC(39);
/* 2362 */       this._os.writeS("PVP傷害減免+" + PVP減傷);
/*      */     } 
/* 2364 */     if (PVP增傷 != 0) {
/* 2365 */       this._os.writeC(39);
/* 2366 */       this._os.writeS("PVP傷害增加+" + PVP增傷);
/*      */     } 
/*      */ 
int all_mcri = mcri + magic_cri;
if (all_mcri != 0) {
	_os.writeC(39);
	_os.writeS("魔法爆擊率 +" + all_mcri);
}

if (cri != 0) {
	_os.writeC(39);
	_os.writeS("近距離爆擊率 +" + cri);
}

if (bow_cri != 0) {
	_os.writeC(39);
	_os.writeS("遠距離爆擊率 +" + bow_cri);
}

/* 2381 */     if (this._item.getclassname().equalsIgnoreCase("Venom_Resist") || 
/* 2382 */       this._item.getclassname().equalsIgnoreCase("ElitePlateMail_Antharas")) {
/* 2383 */       this._os.writeC(39);
/* 2384 */       this._os.writeS("防護中毒狀態");
/*      */     } 

/* 2398 */     if (!this._item.isTradable()) {
/* 2399 */       this._os.writeC(39);
/* 2400 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/* 2403 */     if (this._item.isCantDelete()) {
/* 2404 */       this._os.writeC(39);
/* 2405 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/* 2408 */     if (this._item.get_safeenchant() < 0) {
/* 2409 */       this._os.writeC(39);
/* 2410 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/* 2414 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/* 2417 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/* 2419 */         this._os.writeC(39);
/* 2420 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 2425 */       as.clear();
/*      */     } 
/*      */     
/* 2428 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream accessories2() {
/* 2435 */     int attr_str = 0;
/* 2436 */     int attr_con = 0;
/* 2437 */     int attr_dex = 0;
/* 2438 */     int attr_int = 0;
/* 2439 */     int attr_wis = 0;
/* 2440 */     int attr_cha = 0;
/* 2441 */     int attr_hp = 0;
/* 2442 */     int attr_mp = 0;
/* 2443 */     int attr_sp = 0;
/* 2444 */     int attr_mr = 0;
/* 2445 */     int drain_min_hp = 0;
/* 2446 */     int drain_max_hp = 0;
/* 2447 */     int drain_hp_rand = 0;
/* 2448 */     int attr_hpr = 0;
/* 2449 */     int attr_mpr = 0;
/* 2450 */     int drain_min_mp = 0;
/* 2451 */     int drain_max_mp = 0;
/* 2452 */     int drain_mp_rand = 0;
/*      */     
/* 2454 */     int skill_gfxid = 0;
/* 2455 */     int skill_rand = 0;
/* 2456 */     int skill_dmg = 0;
/* 2457 */     int attr_魔法格檔 = 0;
/* 2458 */     int attr_物理格檔 = 0;
/*      */     
/* 2460 */     int PVP增傷 = 0;
/* 2461 */     int PVP減傷 = 0;
/* 2462 */     int attr_potion_heal = 0;
/*      */     
/* 2464 */     L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
/* 2465 */     if (attr_char != null) {
/* 2466 */       L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
/*      */       
/* 2468 */       attr_str = attr.get_add_str();
/* 2469 */       attr_con = attr.get_add_con();
/* 2470 */       attr_dex = attr.get_add_dex();
/* 2471 */       attr_int = attr.get_add_int();
/* 2472 */       attr_wis = attr.get_add_wis();
/* 2473 */       attr_cha = attr.get_add_cha();
/* 2474 */       attr_hp = attr.get_add_hp();
/* 2475 */       attr_mp = attr.get_add_mp();
/* 2476 */       attr_sp = attr.get_add_sp();
/* 2477 */       attr_mr = attr.get_add_m_def();
/* 2478 */       drain_min_hp = attr.get_add_drain_min_hp();
/* 2479 */       drain_max_hp = attr.get_add_drain_max_hp();
/* 2480 */       drain_hp_rand = attr.get_drain_hp_rand();
/* 2481 */       drain_min_mp = attr.get_add_drain_min_mp();
/* 2482 */       drain_max_mp = attr.get_add_drain_max_mp();
/* 2483 */       drain_mp_rand = attr.get_drain_mp_rand();
/* 2484 */       attr_hpr = attr.get_add_hpr();
/* 2485 */       attr_mpr = attr.get_add_mpr();
/* 2486 */       skill_gfxid = attr.get_add_skill_gfxid();
/* 2487 */       skill_rand = attr.get_add_skill_rand();
/* 2488 */       skill_dmg = attr.get_add_skill_dmg();
/*      */       
/* 2490 */       attr_魔法格檔 = attr.get魔法格檔();
/* 2491 */       attr_物理格檔 = attr.get物理格檔();
/*      */       
/* 2493 */       PVP增傷 = attr.get_add_pvp_dmg();
/* 2494 */       PVP減傷 = attr.get_add_pvp_redmg();
/* 2495 */       attr_potion_heal = attr.get_add_potion_heal();
/*      */     } 
/*      */     
/* 2498 */     this._os.writeC(19);
/* 2499 */     int ac = this._item.get_ac();
/* 2500 */     if (ac < 0) {
/* 2501 */       ac = Math.abs(ac);
/*      */     }
/* 2503 */     this._os.writeC(ac);
/*      */     
/* 2505 */     this._os.writeC(this._item.getMaterial());
/* 2506 */     this._os.writeC(this._item.get_greater());
/* 2507 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/* 2509 */     if (this._itemInstance.get_time() != null) {
/* 2510 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 2511 */       this._os.writeC(39);
/* 2512 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*      */     
/* 2515 */     int pw_s1 = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
/* 2516 */     int pw_s2 = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
/* 2517 */     int pw_s3 = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
/* 2518 */     int pw_s4 = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
/* 2519 */     int pw_s5 = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
/* 2520 */     int pw_s6 = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2528 */     int pw_sHp = this._itemPower.get_addhp() + this._itemInstance.getItemHp() + attr_hp;
/* 2529 */     int pw_sMp = this._item.get_addmp() + this._itemInstance.getItemMp() + attr_mp;
/* 2530 */     int pw_sMr = this._itemPower.getMr() + this._itemInstance.getItemMr() + attr_mr;
/* 2531 */     int pw_sSp = this._itemPower.getSp() + this._itemInstance.getItemSp() + attr_sp;
/*      */     
/* 2533 */     int pw_sDg = this._item.getDmgModifierByArmor() + this._itemInstance.getItemAttack();
/* 2534 */     int pw_sHi = this._itemPower.getHitModifierByArmor() + this._itemInstance.getItemHit();
/* 2535 */     int pw_mHi = this._item.getMagicHitModifierByArmor();
/*      */     
/* 2537 */     int pw_bDg = this._item.getBowDmgModifierByArmor();
/* 2538 */     int pw_bHi = this._item.getBowHitModifierByArmor();
/*      */     
/* 2540 */     int pw_d4_1 = this._item.get_defense_fire();
/* 2541 */     int pw_d4_2 = this._item.get_defense_water();
/* 2542 */     int pw_d4_3 = this._item.get_defense_wind();
/* 2543 */     int pw_d4_4 = this._item.get_defense_earth();
/*      */     
/* 2545 */     int pw_k6_1 = this._item.get_regist_freeze();
/* 2546 */     int pw_k6_2 = this._item.get_regist_stone();
/* 2547 */     int pw_k6_3 = this._item.get_regist_sleep();
/* 2548 */     int pw_k6_4 = this._item.get_regist_blind();
/* 2549 */     int pw_k6_5 = this._item.get_regist_stun();
/* 2550 */     int pw_k6_6 = this._item.get_regist_sustain();
/* 2551 */     int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
/* 2552 */     int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
/* 2553 */     int addexp = this._item.getExpPoint();
/* 2554 */     int pw_drd = this._itemPower.getDamageReduction() + this._itemInstance.getItemReductionDmg();
/* 2555 */     int uhp = this._item.get_up_hp_potion() + this._itemInstance.getItemhppotion();
/* 2556 */     int uhp_num = this._item.get_uhp_number();
/*      */ 
int cri = _item.get_CriticalChance();// 近距離暴擊率
int bow_cri = _item.get_Bow_CriticalChance();// 遠距離暴擊率
int magic_cri = _item.get_Magic_CriticalChance();// 魔法暴擊率
/*      */     
/* 2559 */     if (pw_sHi != 0) {
/* 2560 */       this._os.writeC(5);
/* 2561 */       this._os.writeC(pw_sHi);
/*      */     } 
/*      */     
/* 2564 */     int add_sdg = pw_sDg;
/* 2565 */     if (add_sdg != 0) {
/* 2566 */       this._os.writeC(6);
/* 2567 */       this._os.writeC(add_sdg);
/*      */     } 
/*      */     
/* 2570 */     if (pw_bHi != 0) {
/* 2571 */       this._os.writeC(24);
/* 2572 */       this._os.writeC(pw_bHi);
/*      */     } 
/*      */     
/* 2575 */     int add_bdg = pw_bDg;
/* 2576 */     if (add_bdg != 0) {
/* 2577 */       this._os.writeC(35);
/* 2578 */       this._os.writeC(add_bdg);
/*      */     } 
/*      */ 
/*      */     
/* 2582 */     int bit = 0;
/* 2583 */     bit |= this._item.isUseRoyal() ? 1 : 0;
/* 2584 */     bit |= this._item.isUseKnight() ? 2 : 0;
/* 2585 */     bit |= this._item.isUseElf() ? 4 : 0;
/* 2586 */     bit |= this._item.isUseMage() ? 8 : 0;
/* 2587 */     bit |= this._item.isUseDarkelf() ? 16 : 0;
/* 2588 */     bit |= this._item.isUseDragonknight() ? 32 : 0;
/* 2589 */     bit |= this._item.isUseIllusionist() ? 64 : 0;
/* 2590 */     this._os.writeC(7);
/* 2591 */     this._os.writeC(bit);
/*      */ 
/*      */     
/* 2594 */     int addstr = pw_s1;
/* 2595 */     int adddex = pw_s2;
/* 2596 */     int addcon = pw_s3;
/* 2597 */     int addwis = pw_s4;
/* 2598 */     int addint = pw_s5;
/* 2599 */     int addcha = pw_s6;
/*      */     
/* 2601 */     if (addstr == 1 && adddex == 1 && addcon == 1 && addwis == 1 && addint == 1 && addcha == 1) {
/* 2602 */       this._os.writeC(39);
/* 2603 */       this._os.writeS("全能力值 +1");
/*      */     } else {
/* 2605 */       if (addstr != 0) {
/* 2606 */         this._os.writeC(8);
/* 2607 */         this._os.writeC(addstr);
/*      */       } 
/*      */       
/* 2610 */       if (adddex != 0) {
/* 2611 */         this._os.writeC(9);
/* 2612 */         this._os.writeC(adddex);
/*      */       } 
/*      */       
/* 2615 */       if (addcon != 0) {
/* 2616 */         this._os.writeC(10);
/* 2617 */         this._os.writeC(addcon);
/*      */       } 
/*      */       
/* 2620 */       if (addwis != 0) {
/* 2621 */         this._os.writeC(11);
/* 2622 */         this._os.writeC(addwis);
/*      */       } 
/*      */       
/* 2625 */       if (addint != 0) {
/* 2626 */         this._os.writeC(12);
/* 2627 */         this._os.writeC(addint);
/*      */       } 
/*      */       
/* 2630 */       if (addcha != 0) {
/* 2631 */         this._os.writeC(13);
/* 2632 */         this._os.writeC(addcha);
/*      */       } 
/*      */     } 
/*      */     
/* 2636 */     int addhp = pw_sHp;
/* 2637 */     if (addhp != 0) {
/* 2638 */       this._os.writeC(14);
/* 2639 */       this._os.writeH(addhp);
/*      */     } 
/*      */     
/* 2642 */     int addmp = pw_sMp;
/* 2643 */     if (addmp != 0) {
/* 2644 */       if (addmp <= 120) {
/* 2645 */         this._os.writeC(32);
/* 2646 */         this._os.writeC(addmp);
/*      */       } else {
/*      */         
/* 2649 */         this._os.writeC(39);
/* 2650 */         this._os.writeS("魔力上限+" + addmp);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2655 */     int addhpr = pw_sHpr;
/* 2656 */     if (addhpr != 0) {
/* 2657 */       this._os.writeC(37);
/* 2658 */       this._os.writeC(addhpr);
/*      */     } 
/*      */     
/* 2661 */     int addmpr = pw_sMpr;
/* 2662 */     if (addmpr != 0) {
/* 2663 */       this._os.writeC(38);
/* 2664 */       this._os.writeC(addmpr);
/*      */     } 
/*      */     
/* 2667 */     int freeze = pw_k6_1;
/* 2668 */     if (freeze != 0) {
/* 2669 */       this._os.writeC(15);
/* 2670 */       this._os.writeH(freeze);
/* 2671 */       this._os.writeC(33);
/* 2672 */       this._os.writeC(1);
/*      */     } 
/*      */     
/* 2675 */     int stone = pw_k6_2;
/* 2676 */     if (stone != 0) {
/* 2677 */       this._os.writeC(15);
/* 2678 */       this._os.writeH(stone);
/* 2679 */       this._os.writeC(33);
/* 2680 */       this._os.writeC(2);
/*      */     } 
/*      */     
/* 2683 */     int sleep = pw_k6_3;
/* 2684 */     if (sleep != 0) {
/* 2685 */       this._os.writeC(15);
/* 2686 */       this._os.writeH(sleep);
/* 2687 */       this._os.writeC(33);
/* 2688 */       this._os.writeC(3);
/*      */     } 
/*      */     
/* 2691 */     int blind = pw_k6_4;
/* 2692 */     if (blind != 0) {
/* 2693 */       this._os.writeC(15);
/* 2694 */       this._os.writeH(blind);
/* 2695 */       this._os.writeC(33);
/* 2696 */       this._os.writeC(4);
/*      */     } 
/*      */     
/* 2699 */     int stun = pw_k6_5;
/* 2700 */     if (stun != 0) {
/* 2701 */       this._os.writeC(15);
/* 2702 */       this._os.writeH(stun);
/* 2703 */       this._os.writeC(33);
/* 2704 */       this._os.writeC(5);
/*      */     } 
/*      */     
/* 2707 */     int sustain = pw_k6_6;
/* 2708 */     if (sustain != 0) {
/* 2709 */       this._os.writeC(15);
/* 2710 */       this._os.writeH(sustain);
/* 2711 */       this._os.writeC(33);
/* 2712 */       this._os.writeC(6);
/*      */     } 
/*      */     
/* 2715 */     int addmr = pw_sMr;
/* 2716 */     if (addmr != 0) {
/* 2717 */       this._os.writeC(15);
/* 2718 */       this._os.writeH(addmr);
/*      */     } 
/*      */     
/* 2721 */     int addsp = pw_sSp;
/* 2722 */     if (addsp != 0) {
/* 2723 */       this._os.writeC(17);
/* 2724 */       this._os.writeC(addsp);
/*      */     } 
/*      */     
/* 2727 */     boolean haste = this._item.isHasteItem();
/* 2728 */     if (haste) {
/* 2729 */       this._os.writeC(18);
/*      */     }
/*      */     
/* 2732 */     int defense_fire = pw_d4_1;
/* 2733 */     if (defense_fire != 0) {
/* 2734 */       this._os.writeC(27);
/* 2735 */       this._os.writeC(defense_fire);
/*      */     } 
/*      */     
/* 2738 */     int defense_water = pw_d4_2;
/* 2739 */     if (defense_water != 0) {
/* 2740 */       this._os.writeC(28);
/* 2741 */       this._os.writeC(defense_water);
/*      */     } 
/*      */     
/* 2744 */     int defense_wind = pw_d4_3;
/* 2745 */     if (defense_wind != 0) {
/* 2746 */       this._os.writeC(29);
/* 2747 */       this._os.writeC(defense_wind);
/*      */     } 
/*      */     
/* 2750 */     int defense_earth = pw_d4_4;
/* 2751 */     if (defense_earth != 0) {
/* 2752 */       this._os.writeC(30);
/* 2753 */       this._os.writeC(defense_earth);
/*      */     } 
/*      */     
/* 2756 */     if (addexp != 0) {
/* 2757 */       this._os.writeC(39);
/* 2758 */       this._os.writeS("狩獵經驗值+" + addexp);
/*      */     } 
/*      */ 
/*      */     
/* 2762 */     if (pw_drd != 0) {
/* 2763 */       this._os.writeC(39);
/* 2764 */       this._os.writeS("傷害減免+" + pw_drd);
/*      */     } 
/*      */     
/* 2767 */     if (pw_mHi != 0) {
/* 2768 */       this._os.writeC(39);
/* 2769 */       this._os.writeS("魔法命中+" + pw_mHi);
/*      */     } 
/* 2771 */     if (drain_hp_rand != 0) {
/* 2772 */       this._os.writeC(39);
/* 2773 */       this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
/*      */     } 
/* 2775 */     if (drain_mp_rand != 0) {
/* 2776 */       this._os.writeC(39);
/* 2777 */       this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
/*      */     } 
/*      */     
/* 2780 */     if (skill_gfxid != 0) {
/* 2781 */       this._os.writeC(39);
/* 2782 */       this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
/*      */     } 
/*      */     
/* 2785 */     if (attr_物理格檔 != 0) {
/* 2786 */       this._os.writeC(39);
/* 2787 */       this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
/*      */     } 
/* 2789 */     if (attr_魔法格檔 != 0) {
/* 2790 */       this._os.writeC(39);
/* 2791 */       this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
/*      */     } 
/* 2793 */     StringBuilder name = new StringBuilder();
/* 2794 */     int adduhp = uhp + greater()[1];
/* 2795 */     if (adduhp != 0) {
/* 2796 */       name.append("藥水回復量+" + adduhp + "%");
/* 2797 */       if (uhp_num != 0) {
/* 2798 */         name.append("+" + uhp_num);
/*      */       }
/* 2800 */       this._os.writeC(39);
/* 2801 */       this._os.writeS(name.toString());
/*      */     } 
/* 2803 */     if (attr_potion_heal != 0) {
/* 2804 */       this._os.writeC(39);
/* 2805 */       this._os.writeS("藥水回復增量+" + attr_potion_heal);
/*      */     } 
/* 2807 */     if (PVP減傷 != 0) {
/* 2808 */       this._os.writeC(39);
/* 2809 */       this._os.writeS("PVP傷害減免+" + PVP減傷);
/*      */     } 
/* 2811 */     if (PVP增傷 != 0) {
/* 2812 */       this._os.writeC(39);
/* 2813 */       this._os.writeS("PVP傷害增加+" + PVP增傷);
/*      */     } 
if (magic_cri != 0) {
	_os.writeC(39);
	_os.writeS("魔法爆擊率 +" + magic_cri);
}

if (cri != 0) {
	_os.writeC(39);
	_os.writeS("近距離爆擊率 +" + cri);
}

if (bow_cri != 0) {
	_os.writeC(39);
	_os.writeS("遠距離爆擊率 +" + bow_cri);
}
/*      */     
/* 2816 */     if (!this._item.isTradable()) {
/* 2817 */       this._os.writeC(39);
/* 2818 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/* 2821 */     if (this._item.isCantDelete()) {
/* 2822 */       this._os.writeC(39);
/* 2823 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/* 2826 */     if (this._item.get_safeenchant() < 0) {
/* 2827 */       this._os.writeC(39);
/* 2828 */       this._os.writeS("無法強化");
/*      */     } 
/* 2967 */     if (this._statusx) {
/* 2968 */       if (!this._item.isTradable()) {
/* 2969 */         this._os.writeC(39);
/* 2970 */         this._os.writeS("無法交易");
/*      */       } 
/*      */       
/* 2973 */       if (this._item.isCantDelete()) {
/* 2974 */         this._os.writeC(39);
/* 2975 */         this._os.writeS("無法刪除");
/*      */       } 
/*      */       
/* 2978 */       if (this._item.get_safeenchant() < 0) {
/* 2979 */         this._os.writeC(39);
/* 2980 */         this._os.writeS("無法強化");
/*      */       } 
/*      */     } 
/*      */     
/* 2984 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/* 2987 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/* 2989 */         this._os.writeC(39);
/* 2990 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 2995 */       as.clear();
/*      */     } 
/* 2997 */     return this._os;
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryOutputStream weapon() {
/* 3002 */     int attr_DmgSmall = 0;
/* 3003 */     int attr_DmgLarge = 0;
/* 3004 */     int attr_HitModifier = 0;
/* 3005 */     int attr_DmgModifier = 0;
/* 3006 */     int attr_str = 0;
/* 3007 */     int attr_con = 0;
/* 3008 */     int attr_dex = 0;
/* 3009 */     int attr_int = 0;
/* 3010 */     int attr_wis = 0;
/* 3011 */     int attr_cha = 0;
/* 3012 */     int attr_hp = 0;
/* 3013 */     int attr_mp = 0;
/* 3014 */     int attr_sp = 0;
/* 3015 */     int attr_mr = 0;
/* 3016 */     int drain_min_hp = 0;
/* 3017 */     int drain_max_hp = 0;
/* 3018 */     int drain_hp_rand = 0;
/* 3019 */     int attr_hpr = 0;
/* 3020 */     int attr_mpr = 0;
/* 3021 */     int drain_min_mp = 0;
/* 3022 */     int drain_max_mp = 0;
/* 3023 */     int drain_mp_rand = 0;
/*      */     
/* 3025 */     int skill_gfxid = 0;
/* 3026 */     int skill_rand = 0;
/* 3027 */     int skill_dmg = 0;
/* 3028 */     int attr_魔法格檔 = 0;
/* 3029 */     int attr_物理格檔 = 0;
/*      */     
/* 3031 */     int PVP增傷 = 0;
/* 3032 */     int PVP減傷 = 0;
/* 3033 */     int attr_potion_heal = 0;
/*      */     
/* 3035 */     L1ItemSpecialAttributeChar attr_char = this._itemInstance.get_ItemAttrName();
/* 3036 */     if (attr_char != null) {
/* 3037 */       L1ItemSpecialAttribute attr = ItemSpecialAttributeTable.get().getAttrId(attr_char.get_attr_id());
/* 3038 */       attr_DmgSmall = attr.get_dmg_small();
/* 3039 */       attr_DmgLarge = attr.get_dmg_large();
/* 3040 */       attr_HitModifier = attr.get_hitmodifier();
/* 3041 */       attr_DmgModifier = attr.get_dmgmodifier();
/* 3042 */       attr_str = attr.get_add_str();
/* 3043 */       attr_con = attr.get_add_con();
/* 3044 */       attr_dex = attr.get_add_dex();
/* 3045 */       attr_int = attr.get_add_int();
/* 3046 */       attr_wis = attr.get_add_wis();
/* 3047 */       attr_cha = attr.get_add_cha();
/* 3048 */       attr_hp = attr.get_add_hp();
/* 3049 */       attr_mp = attr.get_add_mp();
/* 3050 */       attr_sp = attr.get_add_sp();
/* 3051 */       attr_mr = attr.get_add_m_def();
/* 3052 */       drain_min_hp = attr.get_add_drain_min_hp();
/* 3053 */       drain_max_hp = attr.get_add_drain_max_hp();
/* 3054 */       drain_hp_rand = attr.get_drain_hp_rand();
/* 3055 */       drain_min_mp = attr.get_add_drain_min_mp();
/* 3056 */       drain_max_mp = attr.get_add_drain_max_mp();
/* 3057 */       drain_mp_rand = attr.get_drain_mp_rand();
/* 3058 */       attr_hpr = attr.get_add_hpr();
/* 3059 */       attr_mpr = attr.get_add_mpr();
/* 3060 */       skill_gfxid = attr.get_add_skill_gfxid();
/* 3061 */       skill_rand = attr.get_add_skill_rand();
/* 3062 */       skill_dmg = attr.get_add_skill_dmg();
/*      */       
/* 3064 */       attr_魔法格檔 = attr.get魔法格檔();
/* 3065 */       attr_物理格檔 = attr.get物理格檔();
/*      */       
/* 3067 */       PVP增傷 = attr.get_add_pvp_dmg();
/* 3068 */       PVP減傷 = attr.get_add_pvp_redmg();
/* 3069 */       attr_potion_heal = attr.get_add_potion_heal();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3075 */     L1MagicWeapon magicWeapon = this._itemInstance.get_magic_weapon();
/* 3076 */     if (magicWeapon != null) {
/* 3077 */       this._os.writeC(39);
/* 3078 */       StringBuilder name1 = (new StringBuilder()).append(magicWeapon.getSkillName());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3083 */       this._os.writeS(name1.toString());
/*      */     } 
/*      */     
/* 3086 */     L1ItemPower_name power = this._itemInstance.get_power_name();
/* 3087 */     this._os.writeC(1);
/* 3088 */     this._os.writeC(this._item.getDmgSmall() + attr_DmgSmall);
/* 3089 */     this._os.writeC(this._item.getDmgLarge() + attr_DmgLarge);
/*      */     
/* 3091 */     this._os.writeC(this._item.getMaterial());
/* 3092 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/* 3094 */     if (this._itemInstance.getEnchantLevel() != 0) {
/* 3095 */       this._os.writeC(2);
/* 3096 */       this._os.writeC(this._itemInstance.getEnchantLevel());
/*      */     } 
/*      */     
/* 3099 */     if (this._itemInstance.get_durability() != 0) {
/* 3100 */       this._os.writeC(3);
/* 3101 */       this._os.writeC(this._itemInstance.get_durability());
/*      */     } 
/*      */     
/* 3104 */     if (this._item.isTwohandedWeapon()) {
/* 3105 */       this._os.writeC(4);
/*      */     }
/* 3107 */     if (this._itemInstance.get_time() != null) {
/* 3108 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 3109 */       this._os.writeC(39);
/* 3110 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*      */     
/* 3113 */     int get_addstr = this._item.get_addstr() + this._itemInstance.getItemStr() + attr_str;
/* 3114 */     int get_adddex = this._item.get_adddex() + this._itemInstance.getItemDex() + attr_dex;
/* 3115 */     int get_addcon = this._item.get_addcon() + this._itemInstance.getItemCon() + attr_con;
/* 3116 */     int get_addwis = this._item.get_addwis() + this._itemInstance.getItemWis() + attr_wis;
/* 3117 */     int get_addint = this._item.get_addint() + this._itemInstance.getItemInt() + attr_int;
/* 3118 */     int get_addcha = this._item.get_addcha() + this._itemInstance.getItemCha() + attr_cha;
/*      */ 
/*      */     
/* 3121 */     int get_addhp = this._itemPower.get_addhp() + this._itemInstance.getItemHp() + attr_hp;
/* 3122 */     int get_addmp = this._item.get_addmp() + this._itemInstance.getItemMp() + attr_mp;
/* 3123 */     int mr = this._itemPower.getMr() + attr_mr;
/*      */     
/* 3125 */     int addWeaponSp = this._itemPower.getSp() + attr_sp;
/* 3126 */     int addDmgModifier = this._item.getDmgModifier() + attr_DmgModifier;
/* 3127 */     int addHitModifier = this._item.getHitModifier() + this._itemInstance.getItemHit() + attr_HitModifier;
/* 3128 */     int addmagicdmg = this._item.getMagicDmgModifier();
/*      */     
/* 3130 */     int addBowDmgModifier = 0;
/* 3131 */     int addBowHitModifier = 0;
/*      */     
/* 3133 */     int pw_d4_1 = this._item.get_defense_fire();
/* 3134 */     int pw_d4_2 = this._item.get_defense_water();
/* 3135 */     int pw_d4_3 = this._item.get_defense_wind();
/* 3136 */     int pw_d4_4 = this._item.get_defense_earth();
/*      */     
/* 3138 */     int pw_k6_1 = this._item.get_regist_freeze();
/* 3139 */     int pw_k6_2 = this._item.get_regist_stone();
/* 3140 */     int pw_k6_3 = this._item.get_regist_sleep();
/* 3141 */     int pw_k6_4 = this._item.get_regist_blind();
/* 3142 */     int pw_k6_5 = this._item.get_regist_stun();
/* 3143 */     int pw_k6_6 = this._item.get_regist_sustain();
/* 3144 */     int pw_sHpr = this._item.get_addhpr() + this._itemInstance.getItemHpr() + attr_hpr;
/* 3145 */     int pw_sMpr = this._itemPower.getMpr() + this._itemInstance.getItemMpr() + attr_mpr;
/* 3146 */     int addexp = this._item.getExpPoint();
/* 3147 */     int PVPdmgReduction = this._item.getPVPdmgReduction() + PVP減傷;
/* 3148 */     int PVPdmg = this._item.getPVPdmg() + PVP增傷;
/* 3149 */     int pw_drd = this._itemInstance.getItemReductionDmg();
/* 3150 */     int uhp = this._itemInstance.getItemhppotion();
/* 3151 */     int uhp_num = 0;
/* 3152 */     int WeaponSkillDmg = 0;
/* 3153 */     int WeaponSkillChance = 0;
/*      */     
/* 3155 */     if (this._itemInstance.get_power_name() != null) {
/* 3156 */       pw_sHpr += (this._l1power.getItem(power.get_hole_1())).add_hpr;
/* 3157 */       pw_sMpr += (this._l1power.getItem(power.get_hole_1())).add_mpr;
/* 3158 */       get_addstr += (this._l1power.getItem(power.get_hole_1())).add_str;
/* 3159 */       get_adddex += (this._l1power.getItem(power.get_hole_1())).add_dex;
/* 3160 */       get_addcon += (this._l1power.getItem(power.get_hole_1())).add_con;
/* 3161 */       get_addwis += (this._l1power.getItem(power.get_hole_1())).add_wis;
/* 3162 */       get_addint += (this._l1power.getItem(power.get_hole_1())).add_int;
/* 3163 */       get_addcha += (this._l1power.getItem(power.get_hole_1())).add_cha;
/* 3164 */       get_addhp += (this._l1power.getItem(power.get_hole_1())).addMaxHP;
/* 3165 */       get_addmp += (this._l1power.getItem(power.get_hole_1())).addMaxMP;
/* 3166 */       addDmgModifier += (this._l1power.getItem(power.get_hole_1())).dmg_modifier;
/* 3167 */       addHitModifier += (this._l1power.getItem(power.get_hole_1())).hit_modifier;
/* 3168 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_1())).bow_dmg_modifier;
/* 3169 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_1())).bow_hit_modifier;
/* 3170 */       mr += (this._l1power.getItem(power.get_hole_1())).m_def;
/* 3171 */       addWeaponSp += (this._l1power.getItem(power.get_hole_1())).add_sp;
/*      */       
/* 3173 */       pw_sHpr += (this._l1power.getItem(power.get_hole_2())).add_hpr;
/* 3174 */       pw_sMpr += (this._l1power.getItem(power.get_hole_2())).add_mpr;
/* 3175 */       get_addstr += (this._l1power.getItem(power.get_hole_2())).add_str;
/* 3176 */       get_adddex += (this._l1power.getItem(power.get_hole_2())).add_dex;
/* 3177 */       get_addcon += (this._l1power.getItem(power.get_hole_2())).add_con;
/* 3178 */       get_addwis += (this._l1power.getItem(power.get_hole_2())).add_wis;
/* 3179 */       get_addint += (this._l1power.getItem(power.get_hole_2())).add_int;
/* 3180 */       get_addcha += (this._l1power.getItem(power.get_hole_2())).add_cha;
/* 3181 */       get_addhp += (this._l1power.getItem(power.get_hole_2())).addMaxHP;
/* 3182 */       get_addmp += (this._l1power.getItem(power.get_hole_2())).addMaxMP;
/* 3183 */       addDmgModifier += (this._l1power.getItem(power.get_hole_2())).dmg_modifier;
/* 3184 */       addHitModifier += (this._l1power.getItem(power.get_hole_2())).hit_modifier;
/* 3185 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_2())).bow_dmg_modifier;
/* 3186 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_2())).bow_hit_modifier;
/* 3187 */       mr += (this._l1power.getItem(power.get_hole_2())).m_def;
/* 3188 */       addWeaponSp += (this._l1power.getItem(power.get_hole_2())).add_sp;
/*      */       
/* 3190 */       pw_sHpr += (this._l1power.getItem(power.get_hole_3())).add_hpr;
/* 3191 */       pw_sMpr += (this._l1power.getItem(power.get_hole_3())).add_mpr;
/* 3192 */       get_addstr += (this._l1power.getItem(power.get_hole_3())).add_str;
/* 3193 */       get_adddex += (this._l1power.getItem(power.get_hole_3())).add_dex;
/* 3194 */       get_addcon += (this._l1power.getItem(power.get_hole_3())).add_con;
/* 3195 */       get_addwis += (this._l1power.getItem(power.get_hole_3())).add_wis;
/* 3196 */       get_addint += (this._l1power.getItem(power.get_hole_3())).add_int;
/* 3197 */       get_addcha += (this._l1power.getItem(power.get_hole_3())).add_cha;
/* 3198 */       get_addhp += (this._l1power.getItem(power.get_hole_3())).addMaxHP;
/* 3199 */       get_addmp += (this._l1power.getItem(power.get_hole_3())).addMaxMP;
/* 3200 */       addDmgModifier += (this._l1power.getItem(power.get_hole_3())).dmg_modifier;
/* 3201 */       addHitModifier += (this._l1power.getItem(power.get_hole_3())).hit_modifier;
/* 3202 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_3())).bow_dmg_modifier;
/* 3203 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_3())).bow_hit_modifier;
/* 3204 */       mr += (this._l1power.getItem(power.get_hole_3())).m_def;
/* 3205 */       addWeaponSp += (this._l1power.getItem(power.get_hole_3())).add_sp;
/*      */       
/* 3207 */       pw_sHpr += (this._l1power.getItem(power.get_hole_4())).add_hpr;
/* 3208 */       pw_sMpr += (this._l1power.getItem(power.get_hole_4())).add_mpr;
/* 3209 */       get_addstr += (this._l1power.getItem(power.get_hole_4())).add_str;
/* 3210 */       get_adddex += (this._l1power.getItem(power.get_hole_4())).add_dex;
/* 3211 */       get_addcon += (this._l1power.getItem(power.get_hole_4())).add_con;
/* 3212 */       get_addwis += (this._l1power.getItem(power.get_hole_4())).add_wis;
/* 3213 */       get_addint += (this._l1power.getItem(power.get_hole_4())).add_int;
/* 3214 */       get_addcha += (this._l1power.getItem(power.get_hole_4())).add_cha;
/* 3215 */       get_addhp += (this._l1power.getItem(power.get_hole_4())).addMaxHP;
/* 3216 */       get_addmp += (this._l1power.getItem(power.get_hole_4())).addMaxMP;
/* 3217 */       addDmgModifier += (this._l1power.getItem(power.get_hole_4())).dmg_modifier;
/* 3218 */       addHitModifier += (this._l1power.getItem(power.get_hole_4())).hit_modifier;
/* 3219 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_4())).bow_dmg_modifier;
/* 3220 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_4())).bow_hit_modifier;
/* 3221 */       mr += (this._l1power.getItem(power.get_hole_4())).m_def;
/* 3222 */       addWeaponSp += (this._l1power.getItem(power.get_hole_4())).add_sp;
/*      */       
/* 3224 */       pw_sHpr += (this._l1power.getItem(power.get_hole_5())).add_hpr;
/* 3225 */       pw_sMpr += (this._l1power.getItem(power.get_hole_5())).add_mpr;
/* 3226 */       get_addstr += (this._l1power.getItem(power.get_hole_5())).add_str;
/* 3227 */       get_adddex += (this._l1power.getItem(power.get_hole_5())).add_dex;
/* 3228 */       get_addcon += (this._l1power.getItem(power.get_hole_5())).add_con;
/* 3229 */       get_addwis += (this._l1power.getItem(power.get_hole_5())).add_wis;
/* 3230 */       get_addint += (this._l1power.getItem(power.get_hole_5())).add_int;
/* 3231 */       get_addcha += (this._l1power.getItem(power.get_hole_5())).add_cha;
/* 3232 */       get_addhp += (this._l1power.getItem(power.get_hole_5())).addMaxHP;
/* 3233 */       get_addmp += (this._l1power.getItem(power.get_hole_5())).addMaxMP;
/* 3234 */       addDmgModifier += (this._l1power.getItem(power.get_hole_5())).dmg_modifier;
/* 3235 */       addHitModifier += (this._l1power.getItem(power.get_hole_5())).hit_modifier;
/* 3236 */       addBowDmgModifier += (this._l1power.getItem(power.get_hole_5())).bow_dmg_modifier;
/* 3237 */       addBowHitModifier += (this._l1power.getItem(power.get_hole_5())).bow_hit_modifier;
/* 3238 */       mr += (this._l1power.getItem(power.get_hole_5())).m_def;
/* 3239 */       addWeaponSp += (this._l1power.getItem(power.get_hole_5())).add_sp;
/*      */     } 
/*      */     
/* 3242 */     L1WilliamEnchantOrginal accessoryOrginal = null;
/* 3243 */     L1WilliamEnchantOrginal accessoryOrginalOk = null;
/* 3244 */     L1WilliamEnchantOrginal[] accessoryOrginalSize = EnchantOrginal.getInstance().getArmorList();
/* 3245 */     for (int j = 0; j < accessoryOrginalSize.length; j++) {
/* 3246 */       accessoryOrginalOk = EnchantOrginal.getInstance().getTemplate(j);
/* 3247 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == this._itemInstance.getItem().getItemId() && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 3248 */         accessoryOrginal = accessoryOrginalOk; break;
/*      */       } 
/* 3250 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 3251 */         accessoryOrginal = accessoryOrginalOk; break;
/*      */       } 
/* 3253 */       if (accessoryOrginalOk.getType() == this._itemInstance.getItem().getType2() && accessoryOrginalOk.getitemid() == 0 && accessoryOrginalOk.getLevel() == this._itemInstance.getEnchantLevel()) {
/* 3254 */         accessoryOrginal = accessoryOrginalOk;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 3259 */     if (accessoryOrginal != null) {
/* 3260 */       get_addstr += accessoryOrginal.getAddStr();
/* 3261 */       get_adddex += accessoryOrginal.getAddDex();
/* 3262 */       get_addcon += accessoryOrginal.getAddCon();
/* 3263 */       get_addint += accessoryOrginal.getAddInt();
/* 3264 */       get_addwis += accessoryOrginal.getAddWis();
/* 3265 */       get_addcha += accessoryOrginal.getAddCha();
/* 3266 */       get_addhp += accessoryOrginal.getAddMaxHp();
/* 3267 */       get_addmp += accessoryOrginal.getAddMaxMp();
/* 3268 */       pw_sHpr += accessoryOrginal.getAddHpr();
/* 3269 */       pw_sMpr += accessoryOrginal.getAddMpr();
/*      */       
/* 3271 */       addDmgModifier += accessoryOrginal.getAddDmg();
/* 3272 */       addHitModifier += accessoryOrginal.getAddHit();
/* 3273 */       addBowDmgModifier += accessoryOrginal.getAddBowDmg();
/* 3274 */       addBowHitModifier += accessoryOrginal.getAddBowHit();
/* 3275 */       pw_drd += accessoryOrginal.getAddDmgReduction();
/* 3276 */       mr += accessoryOrginal.getAddMr();
/* 3277 */       addWeaponSp += accessoryOrginal.getAddSp();
/* 3278 */       PVPdmg += accessoryOrginal.getPVPdmg();
/* 3279 */       PVPdmgReduction += accessoryOrginal.getPVPdmgReduction();
/* 3280 */       uhp += accessoryOrginal.getPotion_Heal();
/* 3281 */       uhp_num += accessoryOrginal.getPotion_Healling();
/* 3282 */       WeaponSkillDmg = (int)(WeaponSkillDmg + accessoryOrginal.getWeaponSkillDmg());
/* 3283 */       WeaponSkillChance += accessoryOrginal.getWeaponSkillChance();
/*      */     } 
/*      */ 
/*      */     
/* 3287 */     int safeenchant = this._item.get_safeenchant();
/* 3288 */     if (safeenchant >= 0) {
/* 3289 */       this._os.writeC(39);
/* 3290 */       this._os.writeS("安定值: " + this._item.get_safeenchant());
/*      */     } 
/*      */     
/* 3293 */     if (addHitModifier != 0) {
/* 3294 */       this._os.writeC(5);
/* 3295 */       this._os.writeC(addHitModifier);
/*      */     } 
/* 3297 */     int BowHit = this._itemInstance.getItemBowHit() + addBowHitModifier;
/* 3298 */     if (BowHit != 0) {
/* 3299 */       this._os.writeC(39);
/* 3300 */       this._os.writeS("遠距離命中+" + BowHit);
/*      */     } 
/*      */     
/* 3303 */     if (addDmgModifier != 0 || this._itemInstance.getItemAttack() != 0) {
/* 3304 */       this._os.writeC(6);
/* 3305 */       this._os.writeC(addDmgModifier + this._itemInstance.getItemAttack());
/*      */     } 
/*      */ 
/*      */     
/* 3309 */     int BowAttack = this._itemInstance.getItemBowAttack() + addBowDmgModifier;
/* 3310 */     if (BowAttack != 0) {
/* 3311 */       this._os.writeC(39);
/* 3312 */       this._os.writeS("遠距離攻擊+" + BowAttack);
/*      */     } 
/* 3322 */     if (addmagicdmg != 0) {
/* 3323 */       this._os.writeC(39);
/* 3324 */       this._os.writeS("魔法傷害: " + addmagicdmg);
/*      */     } 
/* 3326 */     if (this._itemInstance.getItemMag_Hit() != 0) {
/* 3327 */       this._os.writeC(39);
/* 3328 */       this._os.writeS("魔法命中: " + this._itemInstance.getItemMag_Hit());
/*      */     } 
/*      */     
/* 3331 */     int addsp = this._itemInstance.getItemSp();
/* 3332 */     if (addsp != 0) {
/* 3333 */       this._os.writeC(17);
/* 3334 */       this._os.writeC(addsp);
/*      */     } 
/* 3336 */     int bit = 0;
/* 3337 */     bit |= this._item.isUseRoyal() ? 1 : 0;
/* 3338 */     bit |= this._item.isUseKnight() ? 2 : 0;
/* 3339 */     bit |= this._item.isUseElf() ? 4 : 0;
/* 3340 */     bit |= this._item.isUseMage() ? 8 : 0;
/* 3341 */     bit |= this._item.isUseDarkelf() ? 16 : 0;
/* 3342 */     bit |= this._item.isUseDragonknight() ? 32 : 0;
/* 3343 */     bit |= this._item.isUseIllusionist() ? 64 : 0;
/* 3344 */     this._os.writeC(7);
/* 3345 */     this._os.writeC(bit);
/*      */     
/* 3347 */     if (this._itemInstance.getItemId() == 126 || this._itemInstance.getItemId() == 127 || 
/* 3348 */       this._itemInstance.getItemId() == 259 || this._itemInstance.getItemId() == 305 || 
/* 3349 */       this._itemInstance.getItemId() == 310 || this._itemInstance.getItemId() == 315) {
/* 3350 */       this._os.writeC(16);
/*      */     }
/*      */     
/* 3353 */     if (this._itemInstance.getItemId() == 262 || this._itemInstance.getItemId() == 410157 || 
/* 3354 */       this._itemInstance.getItemId() == 12 || this._itemInstance.getItemId() == 410117 || 
/* 3355 */       this._itemInstance.getItemId() == 410164)
/*      */     {
/* 3357 */       this._os.writeC(34);
/*      */     }
/*      */     
/* 3360 */     if (get_addstr != 0) {
/* 3361 */       this._os.writeC(8);
/* 3362 */       this._os.writeC(get_addstr);
/*      */     } 
/*      */     
/* 3365 */     if (get_adddex != 0) {
/* 3366 */       this._os.writeC(9);
/* 3367 */       this._os.writeC(get_adddex);
/*      */     } 
/*      */     
/* 3370 */     if (get_addcon != 0) {
/* 3371 */       this._os.writeC(10);
/* 3372 */       this._os.writeC(get_addcon);
/*      */     } 
/*      */     
/* 3375 */     if (get_addwis != 0) {
/* 3376 */       this._os.writeC(11);
/* 3377 */       this._os.writeC(get_addwis);
/*      */     } 
/*      */     
/* 3380 */     if (get_addint != 0) {
/* 3381 */       this._os.writeC(12);
/* 3382 */       this._os.writeC(get_addint);
/*      */     } 
/*      */     
/* 3385 */     if (get_addcha != 0) {
/* 3386 */       this._os.writeC(13);
/* 3387 */       this._os.writeC(get_addcha);
/*      */     } 
/*      */     
/* 3390 */     if (get_addhp != 0) {
/* 3391 */       this._os.writeC(14);
/* 3392 */       this._os.writeH(get_addhp);
/*      */     } 
/*      */     
/* 3395 */     if (get_addmp != 0) {
/* 3396 */       if (get_addmp <= 120) {
/* 3397 */         this._os.writeC(32);
/* 3398 */         this._os.writeC(get_addmp);
/*      */       } else {
/*      */         
/* 3401 */         this._os.writeC(39);
/* 3402 */         this._os.writeS("魔力上限+" + get_addmp);
/*      */       } 
/*      */     }
/*      */     
/* 3406 */     if (pw_sHpr != 0) {
/* 3407 */       this._os.writeC(37);
/* 3408 */       this._os.writeC(pw_sHpr);
/*      */     } 
/*      */ 
/*      */     
/* 3412 */     if (pw_sMpr != 0) {
/* 3413 */       this._os.writeC(38);
/* 3414 */       this._os.writeC(pw_sMpr);
/*      */     } 
/*      */     
/* 3417 */     if (mr != 0) {
/* 3418 */       this._os.writeC(15);
/* 3419 */       this._os.writeH(mr);
/*      */     } 
/*      */     
/* 3422 */     if (addWeaponSp != 0) {
/* 3423 */       this._os.writeC(17);
/* 3424 */       this._os.writeC(addWeaponSp);
/*      */     } 
/*      */     
/* 3427 */     if (this._item.isHasteItem()) {
/* 3428 */       this._os.writeC(18);
/*      */     }
/*      */     
/* 3431 */     if (pw_d4_1 != 0) {
/* 3432 */       this._os.writeC(27);
/* 3433 */       this._os.writeC(pw_d4_1);
/*      */     } 
/*      */     
/* 3436 */     if (pw_d4_2 != 0) {
/* 3437 */       this._os.writeC(28);
/* 3438 */       this._os.writeC(pw_d4_2);
/*      */     } 
/*      */     
/* 3441 */     if (pw_d4_3 != 0) {
/* 3442 */       this._os.writeC(29);
/* 3443 */       this._os.writeC(pw_d4_3);
/*      */     } 
/*      */     
/* 3446 */     if (pw_d4_4 != 0) {
/* 3447 */       this._os.writeC(30);
/* 3448 */       this._os.writeC(pw_d4_4);
/*      */     } 
/*      */     
/* 3451 */     if (pw_k6_1 != 0) {
/* 3452 */       this._os.writeC(15);
/* 3453 */       this._os.writeH(pw_k6_1);
/* 3454 */       this._os.writeC(33);
/* 3455 */       this._os.writeC(1);
/*      */     } 
/*      */     
/* 3458 */     if (pw_k6_2 != 0) {
/* 3459 */       this._os.writeC(15);
/* 3460 */       this._os.writeH(pw_k6_2);
/* 3461 */       this._os.writeC(33);
/* 3462 */       this._os.writeC(2);
/*      */     } 
/*      */     
/* 3465 */     if (pw_k6_3 != 0) {
/* 3466 */       this._os.writeC(15);
/* 3467 */       this._os.writeH(pw_k6_3);
/* 3468 */       this._os.writeC(33);
/* 3469 */       this._os.writeC(3);
/*      */     } 
/*      */     
/* 3472 */     if (pw_k6_4 != 0) {
/* 3473 */       this._os.writeC(15);
/* 3474 */       this._os.writeH(pw_k6_4);
/* 3475 */       this._os.writeC(33);
/* 3476 */       this._os.writeC(4);
/*      */     } 
/*      */     
/* 3479 */     if (pw_k6_5 != 0) {
/* 3480 */       this._os.writeC(15);
/* 3481 */       this._os.writeH(pw_k6_5);
/* 3482 */       this._os.writeC(33);
/* 3483 */       this._os.writeC(5);
/*      */     } 
/*      */     
/* 3486 */     if (pw_k6_6 != 0) {
/* 3487 */       this._os.writeC(15);
/* 3488 */       this._os.writeH(pw_k6_6);
/* 3489 */       this._os.writeC(33);
/* 3490 */       this._os.writeC(6);
/*      */     } 
/*      */     
/* 3493 */     if (addexp != 0) {
/* 3494 */       this._os.writeC(39);
/* 3495 */       this._os.writeS("狩獵經驗值 +" + addexp);
/*      */     } 
/*      */ 
/*      */     
/* 3499 */     if (uhp != 0) {
/* 3500 */       this._os.writeC(39);
/* 3501 */       this._os.writeS("藥水回復量+" + uhp + "%" + uhp_num);
/*      */     } 
/*      */     
/* 3504 */     if (attr_potion_heal != 0) {
/* 3505 */       this._os.writeC(39);
/* 3506 */       this._os.writeS("藥水回復增量+" + attr_potion_heal);
/*      */     } 
/* 3508 */     int dr = this._itemInstance.getItemprobability();
/* 3509 */     L1WeaponSkill weaponSkill = WeaponSkillTable.get().getTemplate(this._itemInstance.getItemId());
/* 3510 */     if (weaponSkill != null) {
/* 3511 */       int lv = 0;
/* 3512 */       if (weaponSkill.getweapon_lv() != 0 && this._itemInstance.getEnchantLevel() == weaponSkill.getweapon_lv()) {
/* 3513 */         lv = 1;
/*      */       }
/* 3515 */       else if (weaponSkill.getweapon_lv() != 0 && this._itemInstance.getEnchantLevel() > weaponSkill.getweapon_lv()) {
/* 3516 */         lv = this._itemInstance.getEnchantLevel() - weaponSkill.getweapon_lv() + 1;
/*      */       } 
/*      */       
/* 3519 */       if (weaponSkill.getProbability() + WeaponSkillChance != 0) {
/* 3520 */         this._os.writeC(39);
/* 3521 */         this._os.writeS("魔法發動率:" + Math.min(weaponSkill.getProbability() + WeaponSkillChance + dr + lv, 100) + "%");
/*      */       } 
/*      */     } 
/* 3524 */     if (WeaponSkillDmg != 0) {
/* 3525 */       this._os.writeC(39);
/* 3526 */       this._os.writeS("魔法武器傷害*:" + WeaponSkillDmg + "%");
/*      */     } 

/* 3534 */     if (pw_drd != 0) {
/* 3535 */       this._os.writeC(39);
/* 3536 */       this._os.writeS("物理減傷+" + this._itemInstance.getItemReductionDmg());
/*      */     } 
/*      */     
/* 3539 */     if (this._item.getpenetrate() != 0) {
/* 3540 */       this._os.writeC(39);
/* 3541 */       this._os.writeS("貫穿效果");
/*      */     } 
/*      */     
/* 3544 */     if (this._item.getNoweaponRedmg() != 0) {
/* 3545 */       this._os.writeC(39);
/* 3546 */       this._os.writeS("無視傷害減免+" + this._item.getNoweaponRedmg());
/*      */     } 
/*      */     
/* 3549 */     if (this._item.getaddStunLevel() != 0) {
/* 3550 */       this._os.writeC(39);
/* 3551 */       this._os.writeS("昏迷命中+" + this._item.getaddStunLevel());
/*      */     } 
/* 3553 */     if (PVPdmgReduction != 0) {
/* 3554 */       this._os.writeC(39);
/* 3555 */       this._os.writeS("PVP傷害減免+" + PVPdmgReduction);
/*      */     } 
/* 3557 */     if (PVPdmg != 0) {
/* 3558 */       this._os.writeC(39);
/* 3559 */       this._os.writeS("PVP傷害增加+" + PVPdmg);
/*      */     } 
/*      */ 
/*      */     
/* 3563 */     if (drain_hp_rand != 0) {
/* 3564 */       this._os.writeC(39);
/* 3565 */       this._os.writeS(String.valueOf(drain_hp_rand) + "%吸血:" + drain_min_hp + "~" + drain_max_hp);
/*      */     } 
/* 3567 */     if (drain_mp_rand != 0) {
/* 3568 */       this._os.writeC(39);
/* 3569 */       this._os.writeS(String.valueOf(drain_mp_rand) + "%吸魔:" + drain_min_mp + "~" + drain_max_mp);
/*      */     } 
/*      */     
/* 3572 */     if (skill_gfxid != 0) {
/* 3573 */       this._os.writeC(39);
/* 3574 */       this._os.writeS(String.valueOf(skill_rand) + "%特效增傷+" + skill_dmg);
/*      */     } 
/*      */     
/* 3577 */     if (attr_物理格檔 != 0) {
/* 3578 */       this._os.writeC(39);
/* 3579 */       this._os.writeS(String.valueOf(attr_物理格檔) + "%物理格檔");
/*      */     } 
/* 3581 */     if (attr_魔法格檔 != 0) {
/* 3582 */       this._os.writeC(39);
/* 3583 */       this._os.writeS(String.valueOf(attr_魔法格檔) + "%魔法格檔");
/*      */     } 

/* 3597 */     if (this._statusx) {
/* 3598 */       if (!this._item.isTradable()) {
/* 3599 */         this._os.writeC(39);
/* 3600 */         this._os.writeS("無法交易");
/*      */       } 
/*      */       
/* 3603 */       if (this._item.isCantDelete()) {
/* 3604 */         this._os.writeC(39);
/* 3605 */         this._os.writeS("無法刪除");
/*      */       } 
/*      */       
/* 3608 */       if (this._item.get_safeenchant() < 0) {
/* 3609 */         this._os.writeC(39);
/* 3610 */         this._os.writeS("無法強化");
/*      */       } 
/*      */     } 
/*      */     
/* 3614 */     ArrayList<String> as = new ArrayList<>();
/*      */     try {
/* 3616 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance)) {
/* 3617 */         this._os.writeC(39);
/* 3618 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 3624 */       as.clear();
/*      */     } 
/* 3626 */     return this._os;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private BinaryOutputStream etcitem() {
/* 3632 */     if (this._item.getItemId() == 40312) {
/* 3633 */       this._os.writeC(39);
/* 3634 */       this._os.writeS("旅館編號:" + this._itemInstance.getInnKeyName());
/* 3635 */       this._os.writeC(39);
/* 3636 */       this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
/*      */     } 
/*      */     
/* 3639 */     if (this._item.getItemId() == 82503) {
/* 3640 */       this._os.writeC(39);
/* 3641 */       this._os.writeS("訓練所編號:" + this._itemInstance.getKeyId());
/* 3642 */       this._os.writeC(39);
/* 3643 */       this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
/*      */     } 
/*      */     
/* 3646 */     if (this._item.getItemId() == 82504) {
/* 3647 */       String npcname = NpcTable.get().getNpcName(this._itemInstance.getInnNpcId());
/* 3648 */       this._os.writeC(39);
/* 3649 */       this._os.writeS(npcname);
/* 3650 */       this._os.writeC(39);
/* 3651 */       this._os.writeS("副本編號:" + this._itemInstance.getKeyId());
/* 3652 */       this._os.writeC(39);
/* 3653 */       this._os.writeS("到期時間:(" + this._itemInstance.getDueTime() + ")");
/*      */     } 
/*      */     
/* 3656 */     this._os.writeC(23);
/* 3657 */     this._os.writeC(this._item.getMaterial());
/* 3658 */     this._os.writeD(this._itemInstance.getWeight());
/* 3659 */     if (this._itemInstance.get_time() != null) {
/* 3660 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 3661 */       this._os.writeC(39);
/* 3662 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/*      */     
/* 3665 */     if (this._statusx) {
/* 3666 */       if (!this._item.isTradable()) {
/* 3667 */         this._os.writeC(39);
/* 3668 */         this._os.writeS("無法交易");
/*      */       } 
/*      */       
/* 3671 */       if (this._item.isCantDelete()) {
/* 3672 */         this._os.writeC(39);
/* 3673 */         this._os.writeS("無法刪除");
/*      */       } 
/*      */       
/* 3676 */       if (this._item.get_safeenchant() < 0) {
/* 3677 */         this._os.writeC(39);
/* 3678 */         this._os.writeS("無法強化");
/*      */       } 
/*      */     } 
/*      */     
/* 3682 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/* 3685 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/* 3687 */         this._os.writeC(39);
/* 3688 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 3693 */       as.clear();
/*      */     } 
/* 3695 */     return this._os;
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryOutputStream petarmor(L1PetItem petItem) {
/* 3700 */     this._os.writeC(19);
/* 3701 */     int ac = petItem.getAddAc();
/* 3702 */     if (ac < 0) {
/* 3703 */       ac = Math.abs(ac);
/*      */     }
/*      */     
/* 3706 */     this._os.writeC(ac);
/* 3707 */     if (this._itemInstance.get_time() != null) {
/* 3708 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
/* 3709 */       this._os.writeC(39);
/* 3710 */       this._os.writeS("到期時間:\n[" + sdf.format(this._itemInstance.get_time()) + "]");
/*      */     } 
/* 3712 */     this._os.writeC(this._item.getMaterial());
/* 3713 */     this._os.writeC(-1);
/* 3714 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/* 3716 */     if (petItem.getHitModifier() != 0) {
/* 3717 */       this._os.writeC(5);
/* 3718 */       this._os.writeC(petItem.getHitModifier());
/*      */     } 
/*      */     
/* 3721 */     if (petItem.getDamageModifier() != 0) {
/* 3722 */       this._os.writeC(6);
/* 3723 */       this._os.writeC(petItem.getDamageModifier());
/*      */     } 
/*      */     
/* 3726 */     if (petItem.isHigher()) {
/* 3727 */       this._os.writeC(7);
/* 3728 */       this._os.writeC(128);
/*      */     } 
/*      */     
/* 3731 */     if (petItem.getAddStr() != 0) {
/* 3732 */       this._os.writeC(8);
/* 3733 */       this._os.writeC(petItem.getAddStr());
/*      */     } 
/* 3735 */     if (petItem.getAddDex() != 0) {
/* 3736 */       this._os.writeC(9);
/* 3737 */       this._os.writeC(petItem.getAddDex());
/*      */     } 
/* 3739 */     if (petItem.getAddCon() != 0) {
/* 3740 */       this._os.writeC(10);
/* 3741 */       this._os.writeC(petItem.getAddCon());
/*      */     } 
/* 3743 */     if (petItem.getAddWis() != 0) {
/* 3744 */       this._os.writeC(11);
/* 3745 */       this._os.writeC(petItem.getAddWis());
/*      */     } 
/* 3747 */     if (petItem.getAddInt() != 0) {
/* 3748 */       this._os.writeC(12);
/* 3749 */       this._os.writeC(petItem.getAddInt());
/*      */     } 
/*      */     
/* 3752 */     if (petItem.getAddHp() != 0) {
/* 3753 */       this._os.writeC(14);
/* 3754 */       this._os.writeH(petItem.getAddHp());
/*      */     } 
/* 3756 */     if (petItem.getAddMp() != 0) {
/* 3757 */       this._os.writeC(32);
/* 3758 */       this._os.writeC(petItem.getAddMp());
/*      */     } 
/*      */     
/* 3761 */     if (petItem.getAddMr() != 0) {
/* 3762 */       this._os.writeC(15);
/* 3763 */       this._os.writeH(petItem.getAddMr());
/*      */     } 
/*      */     
/* 3766 */     if (petItem.getAddSp() != 0) {
/* 3767 */       this._os.writeC(17);
/* 3768 */       this._os.writeC(petItem.getAddSp());
/*      */     } 
/*      */ 
/*      */     
/* 3772 */     if (!this._item.isTradable()) {
/* 3773 */       this._os.writeC(39);
/* 3774 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/* 3777 */     if (this._item.isCantDelete()) {
/* 3778 */       this._os.writeC(39);
/* 3779 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/* 3782 */     if (this._item.get_safeenchant() < 0) {
/* 3783 */       this._os.writeC(39);
/* 3784 */       this._os.writeS("無法強化");
/*      */     } 
/*      */ 
/*      */     
/* 3788 */     ArrayList<String> as = new ArrayList<>();
/*      */     
/*      */     try {
/* 3791 */       for (String s : WilliamItemMessage.getItemInfo(this._itemInstance))
/*      */       {
/* 3793 */         this._os.writeC(39);
/* 3794 */         this._os.writeS(s);
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/* 3799 */       as.clear();
/*      */     } 
/* 3801 */     return this._os;
/*      */   }
/*      */ 
/*      */   
/*      */   private BinaryOutputStream petweapon(L1PetItem petItem) {
/* 3806 */     this._os.writeC(1);
/* 3807 */     this._os.writeC(0);
/* 3808 */     this._os.writeC(0);
/* 3809 */     this._os.writeC(this._item.getMaterial());
/* 3810 */     this._os.writeD(this._itemInstance.getWeight());
/*      */     
/* 3812 */     if (petItem.getHitModifier() != 0) {
/* 3813 */       this._os.writeC(5);
/* 3814 */       this._os.writeC(petItem.getHitModifier());
/*      */     } 
/*      */     
/* 3817 */     if (petItem.getDamageModifier() != 0) {
/* 3818 */       this._os.writeC(6);
/* 3819 */       this._os.writeC(petItem.getDamageModifier());
/*      */     } 
/*      */     
/* 3822 */     if (petItem.isHigher()) {
/* 3823 */       this._os.writeC(7);
/* 3824 */       this._os.writeC(128);
/*      */     } 
/*      */     
/* 3827 */     if (petItem.getAddStr() != 0) {
/* 3828 */       this._os.writeC(8);
/* 3829 */       this._os.writeC(petItem.getAddStr());
/*      */     } 
/* 3831 */     if (petItem.getAddDex() != 0) {
/* 3832 */       this._os.writeC(9);
/* 3833 */       this._os.writeC(petItem.getAddDex());
/*      */     } 
/* 3835 */     if (petItem.getAddCon() != 0) {
/* 3836 */       this._os.writeC(10);
/* 3837 */       this._os.writeC(petItem.getAddCon());
/*      */     } 
/* 3839 */     if (petItem.getAddWis() != 0) {
/* 3840 */       this._os.writeC(11);
/* 3841 */       this._os.writeC(petItem.getAddWis());
/*      */     } 
/* 3843 */     if (petItem.getAddInt() != 0) {
/* 3844 */       this._os.writeC(12);
/* 3845 */       this._os.writeC(petItem.getAddInt());
/*      */     } 
/*      */     
/* 3848 */     if (petItem.getAddHp() != 0) {
/* 3849 */       this._os.writeC(14);
/* 3850 */       this._os.writeH(petItem.getAddHp());
/*      */     } 
/* 3852 */     if (petItem.getAddMp() != 0) {
/* 3853 */       this._os.writeC(32);
/* 3854 */       this._os.writeC(petItem.getAddMp());
/*      */     } 
/*      */     
/* 3857 */     if (petItem.getAddMr() != 0) {
/* 3858 */       this._os.writeC(15);
/* 3859 */       this._os.writeH(petItem.getAddMr());
/*      */     } 
/* 3861 */     if (petItem.getAddSp() != 0) {
/*      */ 
/*      */       
/* 3864 */       this._os.writeC(17);
/* 3865 */       this._os.writeC(petItem.getAddSp());
/*      */     } 
/*      */     
/* 3868 */     if (!this._item.isTradable()) {
/* 3869 */       this._os.writeC(39);
/* 3870 */       this._os.writeS("無法交易");
/*      */     } 
/*      */     
/* 3873 */     if (this._item.isCantDelete()) {
/* 3874 */       this._os.writeC(39);
/* 3875 */       this._os.writeS("無法刪除");
/*      */     } 
/*      */     
/* 3878 */     if (this._item.get_safeenchant() < 0) {
/* 3879 */       this._os.writeC(39);
/* 3880 */       this._os.writeS("無法強化");
/*      */     } 
/*      */     
/* 3883 */     return this._os;
/*      */   }
/*      */   
/*      */   private int[] greater() {
/* 3887 */     int level = this._itemInstance.getEnchantLevel();
/* 3888 */     if (level < 0) {
/* 3889 */       level = 0;
/*      */     }
/*      */     
/* 3892 */     int[] rint = new int[13];
/* 3893 */     switch (this._itemInstance.getItem().get_greater()) {
/*      */     
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3907 */     return rint;
/*      */   }
/*      */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\Instance\L1ItemStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */