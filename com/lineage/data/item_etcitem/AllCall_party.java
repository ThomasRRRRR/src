/*     */ package com.lineage.data.item_etcitem;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.clientpackets.C_Attr;
/*     */ import com.lineage.server.datatables.MapsTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.map.L1Map;
/*     */ import com.lineage.server.model.L1CastleLocation;
/*     */ import com.lineage.server.model.L1Object;
/*     */ import com.lineage.server.model.L1Party;
/*     */ import com.lineage.server.serverpackets.S_Message_YN;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxGree;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;
import java.util.concurrent.ConcurrentHashMap;

/*     */ public class AllCall_party
/*     */   extends ItemExecutor
/*     */ {
	
//	public static  int initialLocX;
//	public static  int initialLocY;
//	public static  short initialMapId;
	
/*     */   public static ItemExecutor get() {
/*  37 */     return new AllCall_party();
/*     */   }

/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  43 */     L1Party party = pc.getParty();
/*     */     
/*  45 */     if (!pc.isInParty()) {
/*     */       
/*  47 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("尚未有組隊無法使用此物品"));
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
/*  52 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("旗子內禁止使用"));
/*     */       return;
/*     */     } 
/*  55 */     if (!pc.getMap().isPartyPc()) {
/*  56 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("所在地圖無法進行傳送"));
/*     */       return;
/*     */     } 

/*  76 */     ConcurrentHashMap<Integer, L1PcInstance> pcs = party.partyUsers();
/*     */     
/*  78 */     if (pcs.isEmpty()) {
/*     */       return;
/*     */     }
/*  81 */     if (pcs.size() <= 0) {
/*     */       return;
/*     */     }
/*  84 */     if (!pc.isInParty()) {
/*     */       return;
/*     */     }
/*  87 */     if (!pc.getInventory().checkItem(ConfigOther.Call_party_itemid, ConfigOther.Call_party_count)) {
				pc.sendPacketsAll((ServerBasePacket)new S_SystemMessage(ConfigOther.clanmsg5));
/*     */       return;
/*     */     } 
/*  91 */     for (L1Object object : World.get().getVisibleObjects((L1Object)pc, 14)) {
/*  92 */       if (object instanceof L1PcInstance) {
/*  93 */         L1PcInstance red = (L1PcInstance)object;
/*  94 */         if (red.getcallclanal() != pc.getId() && 
/*  95 */           !pc.getParty().isMember(red)) {
/*  96 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到周圍有非本隊伍玩家,無法使用。"));
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*  58 */     for (L1Object object : World.get().getVisibleObjects((L1Object)pc, 16)) {
/*  59 */       if (object instanceof L1PcInstance) {
/*  60 */         L1PcInstance red = (L1PcInstance)object;
/*  61 */         if (red.getClanid() != 0 && 
/*  62 */           red.getClanid() != pc.getClanid()) {
/*  63 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("偵測到周圍有非本盟玩家,無法使用。"));
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 102 */     
pc.getInventory().consumeItem(ConfigOther.Call_party_itemid, ConfigOther.Call_party_count);

       World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.clanmsg, new Object[] { pc.getClanname(), pc.getName(), MapsTable.get().getMapName(pc.getMapId()) })));
       World.get().broadcastPacketToAll(new S_PacketBoxGree(2, "\\fD血盟\\fM[" + pc.getClanname() + "]" + "\\fD玩家\\f=[" + pc.getName() + "]" + "\\fD在\\fH["  + MapsTable.get().getMapName(pc.getMapId())+ "]\\fD使用\\f;[隊伍]穿雲箭\\fD,千軍萬馬來相見"));

     //在這裡設置 locX、locY 和 mapId 的值
       int locX = pc.getX();
       int locY = pc.getY();
       short mapId = pc.getMapId();
       final L1Map map = pc.getMap();
       int heading = pc.getHeading(); // 從 L1PcInstance 中獲取方向

       //呼叫 C_Attr 的 setInitialLocation 方法，設置初始坐標和地圖編號
       C_Attr.setInitialLocation(pc.getId(), locX, locY, mapId, map, heading);
       
/* 103 */     for (L1PcInstance pc2 : pcs.values()) {
/* 104 */       if (pc.isInParty() && pc.getParty().isMember(pc2)) {
/*     */ 
/*     */         
/* 107 */         pc2.setcallclanal(pc.getId());
///* 108 */         pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 2047));
/*     */ 
/*     */         
// // 儲存初始座標和地圖編號
//initialLocX = pc.getX();
//initialLocY = pc.getY();
//initialMapId = pc.getMapId();

/* 111 */         pc.setcallclanal(1);
/* 112 */         if (pc2.getcallclanal() != 1) {
/* 113 */           pc2.sendPackets((ServerBasePacket)new S_Message_YN(4000));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }