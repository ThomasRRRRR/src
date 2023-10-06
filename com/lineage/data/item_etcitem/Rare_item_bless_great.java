/*     */ package com.lineage.data.item_etcitem;
/*     */ 
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.william.New_BlessItem1;
import com.lineage.william.New_BlessItem2;

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
/*     */ public class Rare_item_bless_great
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  33 */     return new Rare_item_bless_great();
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
/*  48 */     int itemobj = data[0];
/*  49 */     L1ItemInstance item1 = pc.getInventory().getItem(itemobj);
/*  50 */     if (item1 == null) {
/*     */       return;
/*     */     }
/*  53 */     if (item1.getItem().getUseType() != 23 && item1.getItem().getUseType() != 24 && item1.getItem().getUseType() != 37 && item1.getItem().getUseType() != 40) {
/*  54 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品不是飾品無法祝福"));
/*     */       return;
/*     */     } 
/*  57 */     if (item1.getBless() == 0) {
/*  58 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經是祝福的物品"));
/*     */       return;
/*     */     } 
/*  61 */     if (item1.getBless() == 128) {
/*  62 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經是祝福的物品"));
/*     */       return;
/*     */     } 
/*  65 */     if (!item1.getItem().isgivebless()) {
/*  66 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("該物品無法進行祝福。"));
/*     */       
/*     */       return;
/*     */     } 
/*  70 */     if (item1.isEquipped()) {
/*  71 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("(預防誤點機制啟動)裝備中無法祝福"));
/*     */       
/*     */       return;
/*     */     }
/*  72 */     if (item1.getItem().getnewbless() == 0) {
/*  73 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("該物品無法進行高級祝福。"));
/*     */       return;
/*     */     }
/*     */     
/*  76 */     Random _random = new Random();
/*     */     
/*  78 */     int _xianzhi1 = this._xianzhi;
/*     */     
/*  80 */     if (pc.isGm()) {
/*  81 */       _xianzhi1 = 100;
/*     */     }
/*  83 */     if (_random.nextInt(100) + 1 <= _xianzhi1) {
/*  84 */       item1.setBless(0);
/*  78 */       if (item1.getItem().getnewbless() == 1) {
/*祝福系統稀有*/        New_BlessItem1.forIntensifyArmor(pc, item1);
/*   */      } else if (item1.getItem().getnewbless() == 2) {
/*祝福系統稀有自訂義*/         New_BlessItem2.forIntensifyArmor(pc, item1);
/*     */       }
/*     */       
/*  91 */       pc.getInventory().updateItem(item1, 576);
/*  92 */       pc.getInventory().saveItem(item1, 576);
/*     */ 
/*     */       
/*  95 */       pc.getInventory().removeItem(item, 1L);
/*     */ 
/*     */       
/*  98 */       pc.sendPackets((ServerBasePacket)new S_ItemStatus(item1));
/*  99 */       祝福升級成功("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級成功 ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/*     */     } else {
/* 101 */       pc.getInventory().removeItem(item, 1L);
/* 102 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("祝福失敗"));
/* 103 */       祝福升級失敗("玩家 :" + pc.getName() + " 使用(" + item.getName() + ") 對 (" + item1.getViewName() + ")升級(失敗) ,時間:" + new Timestamp(System.currentTimeMillis()) + ")");
/* 104 */       if (this.type2)
/* 105 */         pc.getInventory().removeItem(item1, 1L); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void 祝福升級成功(String info) {
/*     */     try {
/* 111 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 112 */             "./玩家紀錄/[祝福升級成功].txt", true));
/* 113 */       out.write(String.valueOf(info) + "\r\n");
/* 114 */       out.close();
/* 115 */     } catch (IOException e) {
/* 116 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   public static void 祝福升級失敗(String info) {
/*     */     try {
/* 121 */       BufferedWriter out = new BufferedWriter(new FileWriter(
/* 122 */             "./玩家紀錄/[祝福升級失敗].txt", true));
/* 123 */       out.write(String.valueOf(info) + "\r\n");
/* 124 */       out.close();
/* 125 */     } catch (IOException e) {
/* 126 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/* 130 */   private int _xianzhi = 0;
/*     */   private boolean type2 = false;
/*     */   
/*     */   public void set_set(String[] set) {
/*     */     try {
/* 135 */       this._xianzhi = Integer.parseInt(set[1]);
/*     */     }
/* 137 */     catch (Exception exception) {}
/*     */     
/*     */     try {
/* 140 */       this.type2 = Boolean.parseBoolean(set[2]);
/*     */     }
/* 142 */     catch (Exception exception) {}
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\Reel_item_bless_great.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */