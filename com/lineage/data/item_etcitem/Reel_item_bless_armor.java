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
/*     */ public class Reel_item_bless_armor
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  34 */     return new Reel_item_bless_armor();
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
/*     */     
/*  55 */     if (item1.getItem().getUseType() != 2 && item1.getItem().getUseType() != 18 && item1.getItem().getUseType() != 19 && item1.getItem().getUseType() != 20 && 
/*  56 */       item1.getItem().getUseType() != 21 && item1.getItem().getUseType() != 22 && item1.getItem().getUseType() != 25 && item1.getItem().getUseType() != 47) {
/*  57 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品不是防具無法祝福"));
/*     */       return;
/*     */     } 
/*  60 */     if (item1.getBless() == 0 || item1.getBless() == 128) {
/*  61 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經是祝福的物品"));
/*     */       return;
/*     */     } 
/*  64 */     if (!item1.getItem().isgivebless()) {
/*  65 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("該物品無法進行祝福。"));
/*     */       return;
/*     */     } 
/*  68 */     if (item1.getBless() == 2) {
/*  69 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品是詛咒品,無法祝福"));
/*     */       return;
/*     */     } 
/*  72 */     if (item1.isEquipped()) {
/*  73 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法祝福"));
/*     */       return;
/*     */     }
/*  72 */     if (item1.getItem().getnewbless() > 0) {
/*  73 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品無法進行一般祝福。"));
/*     */       return;
/*     */     }
/*  76 */     Random _random = new Random();
/*  77 */     if (_random.nextInt(100) + 1 <= this._xianzhi) {
/*  78 */       if (item1.getItem().getnewbless() == 0) {
///*  79 */         New_BlessItem1.forIntensifyArmor(pc, item1);
///*     */       } else {
/*祝福系統*/        New_BlessItem.forIntensifyArmor(pc, item1);
/*祝福系統*/       } 
/*  83 */       item1.setBless(0);
/*  84 */       pc.getInventory().updateItem(item1, 576);
/*  85 */       pc.getInventory().saveItem(item1, 576);
/*     */ 
/*     */       
/*  88 */       pc.getInventory().removeItem(item, 1L);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(item1));
/*  94 */       祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*     */     } else {
/*  96 */       pc.getInventory().removeItem(item, 1L);
/*  97 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("祝福失敗"));
/*  98 */       祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*  99 */       if (this.type2)
/* 100 */         pc.getInventory().removeItem(item1, 1L); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void 祝福升級成功(String info) {
/*     */     try {
/* 106 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 107 */             "./玩家紀錄/[祝福升級成功].txt", true));
/* 108 */       out.write(String.valueOf(info) + "\r\n");
/* 109 */       out.close();
/* 110 */     } catch (IOException e) {
/* 111 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void 祝福升級失敗(String info) {
/*     */     try {
/* 116 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 117 */             "./玩家紀錄/[祝福升級失敗].txt", true));
/* 118 */       out.write(String.valueOf(info) + "\r\n");
/* 119 */       out.close();
/* 120 */     } catch (IOException e) {
/* 121 */       e.printStackTrace();
/*     */     } 
/*     */   }
/* 124 */   private int _xianzhi = 0;
/*     */   private boolean type2 = false;
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 129 */       this._xianzhi = Integer.parseInt(set[1]);
/*     */     }
/* 131 */     catch (Exception exception) {}
/*     */     
/*     */     try {
/* 134 */       this.type2 = Boolean.parseBoolean(set[2]);
/*     */     }
/* 136 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\Reel_item_bless_armor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */