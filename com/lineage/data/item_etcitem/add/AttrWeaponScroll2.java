/*     */ package com.lineage.data.item_etcitem.add;
/*     */ 
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.datatables.ExtraAttrWeaponTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1AttrWeapon;
/*     */ import com.lineage.server.world.World;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class AttrWeaponScroll2
/*     */   extends ItemExecutor
/*     */ {
/*  22 */   private static final Random _random = new Random();
/*     */   private int _attr_id;
/*     */   
/*     */   public static ItemExecutor get() {
/*  26 */     return new AttrWeaponScroll2();
/*     */   }
/*     */   private int _type;
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  30 */     int targObjId = data[0];
/*     */     
/*  32 */     L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);
/*  33 */     if (tgItem == null) {
/*     */       return;
/*     */     }
/*     */     
/*  37 */     if (tgItem.getItem().getType2() != 1) {
/*  38 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/*不偵測祝福*/ if (tgItem.getBless() >= 128) {
///*偵測祝福*/     if (tgItem.getBless() != 0) {
/*  43 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(79));
/*     */       
/*     */       return;
/*     */     } 
/*  47 */     int attrEnchantKind = tgItem.getAttrEnchantKind();
/*  48 */     int attrEnchantLevel = tgItem.getAttrEnchantLevel();
/*     */     
/*  50 */     if (attrEnchantKind != this._attr_id) {
/*  51 */       attrEnchantLevel = 0;
/*     */     }
/*     */     
/*  54 */     L1AttrWeapon attrWeapon = ExtraAttrWeaponTable.getInstance().get(this._attr_id, attrEnchantLevel + 1);
/*  55 */     if (attrWeapon == null || attrWeapon.getChance() <= 0) {
/*  56 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("已達該屬性最高階級。"));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  64 */     pc.getInventory().removeItem(item, 1L);
/*     */     
/*  66 */     if (_random.nextInt(100) < attrWeapon.getChance()) {
/*  67 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(1410, tgItem.getLogName()));
/*  68 */       if (attrWeapon.getMessage() != null && !pc.isGm()) {
/*  69 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(attrWeapon.getMessage(), new Object[] { pc.getName(), "+" + tgItem.getEnchantLevel() + tgItem.getName(), attrWeapon.getName() })));
/*     */       }
/*  71 */       tgItem.setAttrEnchantKind(this._attr_id);
/*  72 */       tgItem.setAttrEnchantLevel(attrEnchantLevel + 1);
/*  73 */       屬性卷軸成敗紀錄("玩家 :" + pc.getName() + "使用 " + item.getName() + "(升)至Lv" + tgItem.getAttrEnchantLevel() + " ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*     */     }
/*  75 */     else if (attrWeapon.gettype() == 0) {
/*  76 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,沒發生什麼事情。"));
/*  77 */     } else if (attrWeapon.gettype() == 1) {
/*  78 */       if (attrEnchantLevel > 0) {
/*  79 */         tgItem.setAttrEnchantLevel(attrEnchantLevel - 1);
/*  80 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,倒退-1。"));
/*     */       } 
/*  82 */       if (attrEnchantLevel == 0) {
/*  83 */         tgItem.setAttrEnchantKind(0);
/*  84 */         tgItem.setAttrEnchantLevel(0);
/*  85 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,沒發生什麼事情。"));
/*     */       } 
/*  87 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/*  88 */       pc.getInventory().saveItem(tgItem, 4);
/*     */     }
/*  90 */     else if (attrWeapon.gettype() == 2) {
/*  91 */       pc.getInventory().removeItem(tgItem, 1L);
/*  92 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,並刪除強化物品。"));
/*  93 */     } else if (attrWeapon.gettype() == 3) {
/*  94 */       tgItem.setAttrEnchantKind(0);
/*  95 */       tgItem.setAttrEnchantLevel(0);
/*  96 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(tgItem));
/*  97 */       pc.getInventory().saveItem(tgItem, 4);
/*  98 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,該裝倍武器屬性歸0。"));
/*     */     } else {
/*     */       
/* 101 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("強化失敗,沒發生什麼事情。"));
/*     */     } 
/*     */ 
/*     */     
/* 105 */     pc.getInventory().updateItem(tgItem, 3072);
/* 106 */     pc.getInventory().saveItem(tgItem, 3072);
/*     */   }
/*     */   public static void 屬性卷軸成敗紀錄(String info) {
/*     */     try {
/* 110 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 111 */             "./玩家紀錄/[記錄]-武器屬性卷軸.txt", true));
/* 112 */       out.write(String.valueOf(info) + "\r\n");
/* 113 */       out.close();
/* 114 */     } catch (IOException e) {
/* 115 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 121 */       this._attr_id = Integer.parseInt(set[1]);
/* 122 */     } catch (Exception exception) {}
/*     */     
/*     */     try {
/* 125 */       this._type = Integer.parseInt(set[2]);
/* 126 */     } catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\add\AttrWeaponScroll2.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */