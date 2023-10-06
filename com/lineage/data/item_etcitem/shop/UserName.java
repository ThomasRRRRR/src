/*    */ package com.lineage.data.item_etcitem.shop;
/*    */ 
/*    */ import com.lineage.data.executor.ItemExecutor;
/*    */ import com.lineage.server.model.Instance.L1ItemInstance;
/*    */ import com.lineage.server.model.Instance.L1PcInstance;
/*    */ import com.lineage.server.serverpackets.S_Message_YN;
/*    */ import com.lineage.server.serverpackets.S_ServerMessage;
/*    */ import com.lineage.server.serverpackets.ServerBasePacket;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class UserName
/*    */   extends ItemExecutor
/*    */ {
/* 15 */   private static final Log _log = LogFactory.getLog(UserName.class);
/*    */ 
/*    */   
/*    */   public static ItemExecutor get() {
/* 19 */     return new UserName();
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/* 24 */     if (item == null) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     if (pc == null) {
/*    */       return;
/*    */     }
/*    */     
/* 32 */     if (pc.isGhost()) {
/*    */       return;
/*    */     }
/*    */     
/* 36 */     if (pc.isDead()) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     if (pc.isTeleport()) {
/*    */       return;
/*    */     }
/*    */     
///* 44 */     if (pc.getLawful() < 32767) {
///* 45 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT正義值必須為32767才可以使用"));
///*    */       
///*    */       return;
///*    */     }
/* 49 */     if (pc.isPrivateShop()) {
/* 50 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先結束商店村模式!"));
/*    */       
/*    */       return;
/*    */     } 
/* 54 */     Object[] petList = pc.getPetList().values().toArray();
/* 55 */     if (petList.length > 0) {
/* 56 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先回收寵物!"));
/*    */       return;
/*    */     } 
/* 59 */     if (pc.getHierarchs() != null) {
/* 60 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先回收祭司!"));
/*    */       return;
/*    */     } 
/* 63 */     if (!pc.getDolls().isEmpty()) {
/* 64 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先回收魔法娃娃!"));
/*    */       
/*    */       return;
/*    */     } 
/* 68 */     if (pc.getParty() != null) {
/* 69 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先退出隊伍!"));
/*    */       
/*    */       return;
/*    */     } 
/* 73 */     if (pc.getClanid() != 0) {
/* 74 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fT請先退出血盟!"));
/*    */       
/*    */       return;
/*    */     } 
/*    */     try {
/* 79 */       pc.sendPackets((ServerBasePacket)new S_Message_YN(325));
/* 80 */       pc.rename(true);
/*    */     }
/* 82 */     catch (Exception e) {
/* 83 */       _log.error(e.getLocalizedMessage(), e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\data\item_etcitem\shop\UserName.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */