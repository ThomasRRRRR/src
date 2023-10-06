/*     */ package com.lineage.data.item_etcitem;
/*     */ 
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.william.New_BlessItem;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.Random;
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
/*     */ public class Reel_item_bless_weapon
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  34 */     return new Reel_item_bless_weapon();
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
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  49 */     int itemobj = data[0];
/*  50 */     L1ItemInstance item1 = pc.getInventory().getItem(itemobj);
/*  51 */     if (item1 == null) {
/*     */       return;
/*     */     }
/*  54 */     if (item1.getBless() == 0) {
/*  55 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經是祝福的物品!"));
/*     */       return;
/*     */     } 
/*  58 */     if (item1.getBless() == 128) {
/*  59 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經是祝福的物品"));
/*     */       return;
/*     */     } 
/*  62 */     if (item1.getItem().getType2() != 1) {
/*  63 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品不是武器無法祝福"));
/*     */       return;
/*     */     } 
/*  66 */     if (!item1.getItem().isgivebless()) {
/*  67 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("該物品無法進行祝福。"));
/*     */       return;
/*     */     } 
/*  70 */     if (item1.isEquipped()) {
/*  71 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法祝福"));
/*     */       return;
/*     */     }
/*  72 */     if (item1.getItem().getnewbless() > 0) {
/*  73 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品無法進行一般祝福。"));
/*     */       return;
/*     */     }
/*  74 */     Random _random = new Random();
/*  75 */     int _xianzhi1 = this._xianzhi;
/*     */     
/*  77 */     if (pc.isGm()) {
/*  78 */       _xianzhi1 = 100;
/*     */     }
/*  80 */     if (_random.nextInt(100) + 1 <= _xianzhi1) {
/*  81 */       item1.setBless(0);
/*  82 */       if (item1.getItem().getnewbless() == 0) {
///*  83 */         New_BlessItem1.forIntensifyweapon(pc, item1);
///*     */       } else {
/*祝福系統*/         New_BlessItem.forIntensifyweapon(pc, item1);
/*祝福系統*/       } 
/*  88 */       pc.getInventory().updateItem(item1, 576);
/*  89 */       pc.getInventory().saveItem(item1, 576);
/*     */       
/*  91 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("祝福成功"));
/*     */       
/*  93 */       pc.getInventory().removeItem(item, 1L);
/*     */       
/*  95 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(item1));
/*     */       
/*  97 */       祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*     */     } else {
/*  99 */       pc.getInventory().removeItem(item, 1L);
/* 100 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("祝福失敗"));
/* 101 */       祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/* 102 */       if (this.type2)
/* 103 */         pc.getInventory().removeItem(item1, 1L); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void 祝福升級成功(String info) {
/*     */     try {
/* 109 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 110 */             "./玩家紀錄/[祝福升級成功].txt", true));
/* 111 */       out.write(String.valueOf(info) + "\r\n");
/* 112 */       out.close();
/* 113 */     } catch (IOException e) {
/* 114 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void 祝福升級失敗(String info) {
/*     */     try {
/* 119 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 120 */             "./玩家紀錄/[祝福升級失敗].txt", true));
/* 121 */       out.write(String.valueOf(info) + "\r\n");
/* 122 */       out.close();
/* 123 */     } catch (IOException e) {
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */   }
/* 127 */   private int _xianzhi = 0;
/*     */   private boolean type2 = false;
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 132 */       this._xianzhi = Integer.parseInt(set[1]);
/*     */     }
/* 134 */     catch (Exception exception) {}
/*     */     
/*     */     try {
/* 137 */       this.type2 = Boolean.parseBoolean(set[2]);
/*     */     }
/* 139 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\Reel_item_bless_weapon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */