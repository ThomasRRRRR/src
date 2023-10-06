/*     */ package com.lineage.data.cmd;
/*     */ 
/*     */ import com.lineage.config.Configtype;
/*     */ import com.lineage.server.datatables.lock.DwarfForVIPReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_ItemStatus;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxGree;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.william.GiveBack;

/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantWeapon
/*     */   extends EnchantExecutor
/*     */ {
/*  27 */   private static final Log _log = LogFactory.getLog(EnchantWeapon.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void failureEnchant(L1PcInstance pc, L1ItemInstance item) {
/*  36 */     StringBuilder s = new StringBuilder();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  41 */     if (pc.get_other().get_item() != null) {
/*  42 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
/*  43 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*  44 */       pc.get_other().set_item(null);
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  52 */     String pm = "";
/*  53 */     if (item.getEnchantLevel() > 0) {
/*  54 */       pm = "+";
/*     */     }
/*  56 */     s.append(String.valueOf(pm) + item.getEnchantLevel() + " " + item.getName());
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (!pc.isGm() && Configtype.weaponbroadfail == 1 && 
/*  61 */       item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad)
/*     */     {
/*     */       
/*  64 */       World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(Configtype.msg1_fail, new Object[] { pc.getName(), s.toString() })));
/*跑馬燈*/   	World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fW強化武器失敗  失去【+ " + s + "】"));
/*     */     }
/*     */ 
/*     */     
/*  68 */     GiveBack.savepcid.add(Integer.valueOf(pc.getId()));
/*  69 */     GiveBack.saveweapon.add(item);
/*  70 */     GiveBack.savename.add(item.getNumberedViewName(1L));
/*  71 */     DwarfForVIPReading.get().insertItem(pc.getName(), item);
/*  72 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(164, s.toString(), "$252"));
/*     */ 
/*     */ 
/*     */     
/*  76 */     pc.getInventory().removeItem(item, item.getCount());
/*     */     
/*  78 */     _log.info("人物:" + pc.getName() + "點爆物品(武器)" + item.getItem().getName() + " 物品OBJID:" + item.getId());
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
/*     */   public void successEnchant(L1PcInstance pc, L1ItemInstance item, int i) {
/*  91 */     StringBuilder s = new StringBuilder();
/*  92 */     StringBuilder sa = new StringBuilder();
/*  93 */     StringBuilder sb = new StringBuilder();
/*  94 */     if (pc.get_other().get_item() != null) {
/*  95 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT物品正在進行託售中.請在重新操作一次"));
/*  96 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*  97 */       pc.get_other().set_item(null);
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     if (!item.isIdentified()) {
/* 102 */       s.append(item.getName());
/*     */     } else {
/*     */       
/* 105 */       s.append(item.getLogName());
/*     */     } 
/*     */     
/* 108 */     switch (i) {
/*     */       
/*     */       case 0:
/* 111 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(160, s.toString(), "$252", "$248"));
/*     */         return;
/*     */       
/*     */       case -1:
/* 115 */         sa.append("$246");
/* 116 */         sb.append("$247");
/*     */         break;
/*     */       
/*     */       case 1:
/* 120 */         sa.append("$245");
/* 121 */         sb.append("$247");
/*     */         break;
/*     */       
/*     */       case 2:
/*     */       case 3:
/* 126 */         sa.append("$245");
/* 127 */         sb.append("$248");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     pc.sendPackets((ServerBasePacket)new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));
/*     */     
/* 134 */     int oldEnchantLvl = item.getEnchantLevel();
/* 135 */     int newEnchantLvl = oldEnchantLvl + i;
/*     */     
/* 137 */     item.setEnchantLevel(newEnchantLvl);
/* 138 */     pc.getInventory().updateItem(item, 4);
/* 139 */     pc.getInventory().saveItem(item, 4);
/*     */ 
/*     */ 
/* 163 */     if (oldEnchantLvl != newEnchantLvl) {
/* 164 */       if (!pc.isGm() && Configtype.weaponbroadtrue == 1 && 
/* 165 */         item.getEnchantLevel() - item.getItem().get_safeenchant() >= Configtype.weapon_savebroad)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*跑馬燈*/   	  World.get().broadcastPacketToAll(new S_PacketBoxGree(2,"\\fT玩家：【" + pc.getName() + "】 \\fQ強化武器成功  獲得【+ " + newEnchantLvl + " " + item.getName() +"】"));
/*     */       }
/*     */     
/* 142 */     pc.sendPackets((ServerBasePacket)new S_ItemStatus(item));
/* 198 */       pc.getInventory().saveItem(item, 4);
/*     */   	}
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\cmd\EnchantWeapon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */