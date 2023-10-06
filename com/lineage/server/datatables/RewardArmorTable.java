/*     */ package com.lineage.server.datatables;
/*     */ import com.lineage.DatabaseFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_HPUpdate;
/*     */ import com.lineage.server.serverpackets.S_MPUpdate;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;

/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class RewardArmorTable {
/*  14 */   private static ArrayList<ArrayList<Object>> aData15b = new ArrayList<>();
/*     */   private static boolean NO_MORE_GET_DATA15b = false;
/*     */   private static RewardArmorTable _instance;
/*     */   
/*     */   public static RewardArmorTable getInstance() {
/*  19 */     if (_instance == null) {
/*  20 */       _instance = new RewardArmorTable();
/*     */     }
/*  22 */     return _instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void forArmor(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
/*  27 */     ArrayList<?> aTempData = null;
/*  28 */     if (!NO_MORE_GET_DATA15b) {
/*  29 */       NO_MORE_GET_DATA15b = true;
/*  30 */       getData15b();
/*     */     } 
/*  32 */     for (int i = 0; i < aData15b.size(); i++) {
/*  33 */       aTempData = aData15b.get(i);
/*     */       
/*  35 */       if (weapon_level == ((Integer)aTempData.get(0)).intValue()) {
/*  36 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/*  37 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aL物理減傷增加:" + (
/*  38 */                 (Integer)aTempData.get(1)).intValue()));
/*  39 */           pc.add_reduction_dmg(((Integer)aTempData.get(1))
/*  40 */               .intValue());
/*     */         } 
/*     */         
/*  43 */         if (((Integer)aTempData.get(2)).intValue() != 0) {
/*  44 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aL魔法減傷增加:" + (
/*  45 */                 (Integer)aTempData.get(2)).intValue()));
/*  46 */           pc.add_magic_reduction_dmg(((Integer)aTempData.get(2))
/*  47 */               .intValue());
/*     */         } 
/*     */         
/*  50 */         if (((Integer)aTempData.get(3)).intValue() != 0) {
/*  51 */           pc.addMaxHp(((Integer)aTempData.get(3)).intValue());
/*  52 */           pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc
/*  53 */                 .getMaxHp()));
/*  54 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aL血量增加+" + (
/*  55 */                 (Integer)aTempData.get(3)).intValue()));
/*  56 */           if (pc.isInParty()) {
/*  57 */             pc.getParty().updateMiniHP(pc);
/*     */           }
/*     */         } 
/*  60 */         if (((Integer)aTempData.get(4)).intValue() != 0) {
/*  61 */           pc.addMaxMp(((Integer)aTempData.get(4)).intValue());
/*  62 */           pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc
/*  63 */                 .getMaxMp()));
/*  64 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aL魔量增加+" + (
/*  65 */                 (Integer)aTempData.get(4)).intValue()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void forArmor1(L1PcInstance pc, L1ItemInstance item, int weapon_level) {
/*  75 */     ArrayList<?> aTempData = null;
/*  76 */     if (!NO_MORE_GET_DATA15b) {
/*  77 */       NO_MORE_GET_DATA15b = true;
/*  78 */       getData15b();
/*     */     } 
/*  80 */     for (int i = 0; i < aData15b.size(); i++) {
/*  81 */       aTempData = aData15b.get(i);
/*     */ 
/*     */       
/*  84 */       if (weapon_level == ((Integer)aTempData.get(0)).intValue()) {
/*  85 */         if (((Integer)aTempData.get(1)).intValue() != 0) {
/*  86 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aB物理減傷減少:" + (
/*  87 */                 (Integer)aTempData.get(1)).intValue()));
/*  88 */           pc.add_reduction_dmg(
/*  89 */               -((Integer)aTempData.get(1)).intValue());
/*     */         } 
/*     */         
/*  92 */         if (((Integer)aTempData.get(2)).intValue() != 0) {
/*  93 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aB魔法減傷減少:" + (
/*  94 */                 (Integer)aTempData.get(2)).intValue()));
/*  95 */           pc.add_magic_reduction_dmg(
/*  96 */               -((Integer)aTempData.get(2)).intValue());
/*     */         } 
/*  98 */         if (((Integer)aTempData.get(3)).intValue() != 0) {
/*  99 */           pc.addMaxHp(-((Integer)aTempData.get(3)).intValue());
/* 100 */           pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc
/* 101 */                 .getMaxHp()));
/* 102 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aB血量減少:" + (
/* 103 */                 (Integer)aTempData.get(3)).intValue()));
/* 104 */           if (pc.isInParty()) {
/* 105 */             pc.getParty().updateMiniHP(pc);
/*     */           }
/*     */         } 
/* 108 */         if (((Integer)aTempData.get(4)).intValue() != 0) {
/* 109 */           pc.addMaxMp(-((Integer)aTempData.get(4)).intValue());
/* 110 */           pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc
/* 111 */                 .getMaxMp()));
/* 112 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aB魔量減少:" + (
/* 113 */                 (Integer)aTempData.get(4)).intValue()));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void getData15b() {
/* 120 */     Connection con = null;
/*     */     try {
/* 122 */       con = DatabaseFactory.get().getConnection();
/* 123 */       Statement stat = con.createStatement();
/* 124 */       ResultSet rset = stat.executeQuery("SELECT * FROM w_過安定防具");
/* 125 */       ArrayList<Object> aReturn = null;
/* 126 */       if (rset != null) {
/* 127 */         for (; rset.next(); aData15b.add(aReturn)) {
/* 128 */           aReturn = new ArrayList();
/* 129 */           aReturn.add(0, new Integer(rset.getInt("Level")));
/* 130 */           aReturn.add(1, new Integer(rset.getInt("Dmg_r")));
/* 131 */           aReturn.add(2, new Integer(rset.getInt("Dmg_Magic_r")));
/* 132 */           aReturn.add(3, new Integer(rset.getInt("Hp")));
/* 133 */           aReturn.add(4, new Integer(rset.getInt("Mp")));
/*     */         } 
/*     */       }
/* 136 */       if (con != null && !con.isClosed())
/* 137 */         con.close(); 
/* 138 */     } catch (Exception exception) {}
/*     */   }
/*     */ }