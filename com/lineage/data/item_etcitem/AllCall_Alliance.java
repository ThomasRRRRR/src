/*     */ package com.lineage.data.item_etcitem;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.clientpackets.C_Attr;
/*     */ import com.lineage.server.datatables.MapsTable;
/*     */ import com.lineage.server.datatables.lock.ClanAllianceReading;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.map.L1Map;
/*     */ import com.lineage.server.model.L1Alliance;
/*     */ import com.lineage.server.model.L1CastleLocation;
/*     */ import com.lineage.server.model.L1Clan;
/*     */ import com.lineage.server.model.L1Object;
/*     */ import com.lineage.server.serverpackets.S_Message_YN;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxGree;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.server.world.WorldClan;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AllCall_Alliance
/*     */   extends ItemExecutor
/*     */ {
	
//	public static  int initialLocX;
//	public static  int initialLocY;
//	public static  short initialMapId;
	
/*     */   public static ItemExecutor get() {
/*  31 */     return new AllCall_Alliance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
/*  37 */     if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
/*  38 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("旗子內禁止使用"));
/*     */       return;
/*     */     } 
/*  41 */     if (pc.getClanid() == 0) {
/*  42 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(
/*  43 */             "\\fY您尚未加入血盟無法使用"));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  48 */     L1Alliance alliance = ClanAllianceReading.get().getAlliance(pc.getClan().getClanId());
/*  49 */     if (alliance == null) {
/*  50 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("尚未加入同盟 無法使用此道具"));
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     if (!pc.getMap().isClanPc()) {
/*  55 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("所在地圖無法進行傳送"));
/*     */       return;
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
/*     */     
/*  71 */     if (!pc.getInventory().checkItem(ConfigOther.Call_clan_itemid2, ConfigOther.Call_clan_count2)) {
/* 118 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.clanmsg15));
				return;
/*     */     } 
pc.getInventory().consumeItem(ConfigOther.Call_clan_itemid2, ConfigOther.Call_clan_count2);

World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.clanmsg13, new Object[] { pc.getClanname(), pc.getName(), MapsTable.get().getMapName(pc.getMapId()) })));
World.get().broadcastPacketToAll(new S_PacketBoxGree(2, "\\fC血盟\\fM[" + pc.getClanname() + "]" + "\\fC玩家\\f=[" + pc.getName() + "]" + "\\fC在\\fH["  + MapsTable.get().getMapName(pc.getMapId())+ "]\\fC使用\\f>[聯盟]穿雲箭\\fC,千軍萬馬來相見"));

// 在這裡設置 locX、locY 和 mapId 的值
int locX = pc.getX();
int locY = pc.getY();
short mapId = pc.getMapId();
final L1Map map = pc.getMap();
int heading = pc.getHeading(); // 從 L1PcInstance 中獲取方向

//呼叫 C_Attr 的 setInitialLocation 方法，設置初始坐標和地圖編號
C_Attr.setInitialLocation(pc.getId(), locX, locY, mapId, map, heading);

/*  70 */     L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/*  79 */       L1PcInstance[] clanMembers = clan.getOnlineClanMember(); byte b; int i; L1PcInstance[] arrayOfL1PcInstance1;
/*  80 */       for (i = (arrayOfL1PcInstance1 = clanMembers).length, b = 0; b < i; ) { L1PcInstance clanMember1 = arrayOfL1PcInstance1[b];
/*  81 */         if (clanMember1.getId() != pc.getId()) {
/*  82 */           clanMember1.setcallclanal(pc.getId());
/*  83 */           clanMember1.sendPackets((ServerBasePacket)new S_Message_YN(729));
/*     */         } 
/*     */         b++; }
/*  94 */       Integer[] allianceids = clan.Alliance();
/*  95 */       if (allianceids.length > 0) {
/*  96 */         String TargetClanName = null;
/*  97 */         L1Clan TargegClan = null;
/*  98 */         for (int j = 0; j < allianceids.length; j++) {
/*  99 */           TargegClan = clan.getAlliance(allianceids[j].intValue());
/* 100 */           if (TargegClan != null) {
/* 101 */             TargetClanName = TargegClan.getClanName();
/* 102 */             if (TargetClanName != null) {
/* 103 */               byte b1;
int k;
L1PcInstance[] arrayOfL1PcInstance;
for (k = (arrayOfL1PcInstance = TargegClan.getOnlineClanMember()).length, b1 = 0; b1 < k; ) {
	L1PcInstance alliancelistner = arrayOfL1PcInstance[b1];

// // 儲存初始座標和地圖編號
//initialLocX = pc.getX();
//initialLocY = pc.getY();
//initialMapId = pc.getMapId();

/* 105 */                 alliancelistner.setcallclana2(pc.getId());
/* 106 */                 alliancelistner.sendPackets((ServerBasePacket)new S_Message_YN(4001));
/*     */                 b1++; }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */   }
/*     */ }
