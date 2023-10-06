/*     */ package com.lineage.server.utils;
/*     */ 
/*     */ import com.lineage.server.datatables.MapsGroupTable;
/*     */ import com.lineage.server.model.Instance.L1DollInstance;
/*     */ import com.lineage.server.model.Instance.L1EffectInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.Instance.L1PetInstance;
/*     */ import com.lineage.server.model.Instance.L1SkinInstance;
/*     */ import com.lineage.server.model.Instance.L1SummonInstance;
/*     */ import com.lineage.server.model.L1Clan;
/*     */ import com.lineage.server.model.L1Location;
/*     */ import com.lineage.server.model.L1Object;
/*     */ import com.lineage.server.model.map.L1Map;
/*     */ import com.lineage.server.model.map.L1WorldMap;
/*     */ import com.lineage.server.serverpackets.S_CharVisualUpdate;
/*     */ import com.lineage.server.serverpackets.S_MapID;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Doll;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Eff;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Hierarch;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Pet;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Skin;
/*     */ import com.lineage.server.serverpackets.S_NPCPack_Summon;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_OtherCharPacks;
/*     */ import com.lineage.server.serverpackets.S_OwnCharPack;
/*     */ import com.lineage.server.serverpackets.S_PacketBox;
/*     */ import com.lineage.server.serverpackets.S_PacketBoxWindShackle;
/*     */ import com.lineage.server.serverpackets.S_Paralysis;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1MapsLimitTime;
/*     */ import com.lineage.server.timecontroller.server.ServerUseMapTimer;
/*     */ import com.lineage.server.types.Point;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.server.world.WorldClan;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Teleportation
/*     */ {
/*  57 */   private static final Log _log = LogFactory.getLog(Teleportation.class);
/*     */   
/*  59 */   private static Random _random = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   public static void teleportation(L1PcInstance pc) {
/*     */     try {
/*  65 */       if (pc == null) {
/*     */         return;
/*     */       }
/*     */       
/*  69 */       if (pc.getOnlineStatus() == 0) {
/*     */         return;
/*     */       }
/*     */       
/*  73 */       if (pc.getNetConnection() == null) {
/*     */         return;
/*     */       }
/*     */       
/*  77 */       if (pc.isDead()) {
/*     */         return;
/*     */       }
/*     */       
/*  81 */       if (pc.isTeleport()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  86 */       pc.getMap().setPassable((Point)pc.getLocation(), true);
/*     */       
/*  88 */       short mapId = pc.getTeleportMapId();
/*  89 */       if (pc.isDead() && mapId != 9101) {
/*     */         return;
/*     */       }
/*     */       
/*  93 */       int x = pc.getTeleportX();
/*  94 */       int y = pc.getTeleportY();
/*  95 */       int head = pc.getTeleportHeading();
/*     */ 
/*     */       
/*  98 */       short pc_mapId = pc.getMapId();
/*     */       
/* 100 */       L1Map map = L1WorldMap.get().getMap(mapId);
/*     */       
/* 102 */       if (!map.isInMap(x, y) && !pc.isGm()) {
/* 103 */         x = pc.getX();
/* 104 */         y = pc.getY();
/* 105 */         mapId = pc.getMapId();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 110 */       L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/* 111 */       if (clan != null && 
/* 112 */         clan.getWarehouseUsingChar() == pc.getId()) {
/* 113 */         clan.setWarehouseUsingChar(0);
/* 114 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "y_who"));
/* 115 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "y_who"));
/*     */       } 
/*     */ 
/*     */       
/* 119 */       World.get().moveVisibleObject((L1Object)pc, mapId);
/*     */       
/* 121 */       pc.setLocation(x, y, mapId);
/* 122 */       pc.setHeading(head);
/*     */       
/* 124 */       pc.setOleLocX(x);
/* 125 */       pc.setOleLocY(y);
/*     */       
/* 127 */       boolean isUnderwater = pc.getMap().isUnderwater();
/* 128 */       pc.sendPackets((ServerBasePacket)new S_MapID(pc, pc.getMapId(), isUnderwater));
/*     */       
/* 130 */       if (!pc.isGhost() && !pc.isGmInvis() && !pc.isInvisble()) {
/* 131 */         pc.broadcastPacketAll((ServerBasePacket)new S_OtherCharPacks(pc));
/*     */       }
/*     */       
/* 134 */       if (pc.isReserveGhost()) {
/* 135 */         pc.endGhost();
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       pc.sendPackets((ServerBasePacket)new S_OwnCharPack(pc));
/* 155 */       pc.removeAllKnownObjects();
/* 156 */       pc.updateObject();
/* 157 */       pc.sendVisualEffectAtTeleport();
/* 158 */       pc.sendPackets((ServerBasePacket)new S_CharVisualUpdate(pc));
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
/*     */       
/* 173 */       pc.killSkillEffectTimer(32);
/* 174 */       pc.setCallClanId(0);
/*     */ 
/*     */       
/* 177 */       HashSet<L1PcInstance> subjects = new HashSet<>();
/* 178 */       subjects.add(pc);
/*     */       
/* 180 */       if (!pc.isGhost()) {
/*     */         
/* 182 */         if (pc.getMap().isTakePets())
/*     */         {
/* 184 */           for (L1NpcInstance petNpc : pc.getPetList().values()) {
/*     */ 
/*     */             
/* 187 */             int nx = pc.getX();
/* 188 */             int ny = pc.getY();
/*     */ 
/*     */ 
/*     */             
/* 192 */             petNpc.set_showId(pc.get_showId());
/*     */             
/* 194 */             teleport(petNpc, nx, ny, mapId, head);
/*     */             
/* 196 */             if (petNpc instanceof L1SummonInstance) {
/* 197 */               L1SummonInstance summon = (L1SummonInstance)petNpc;
/* 198 */               pc.sendPackets((ServerBasePacket)new S_NPCPack_Summon(summon, pc));
/*     */             }
/* 200 */             else if (petNpc instanceof L1PetInstance) {
/* 201 */               L1PetInstance pet = (L1PetInstance)petNpc;
/* 202 */               pc.sendPackets((ServerBasePacket)new S_NPCPack_Pet(pet, pc));
/*     */             } 
/*     */             
/* 205 */             for (L1PcInstance visiblePc : World.get().getVisiblePlayer((L1Object)petNpc)) {
/*     */               
/* 207 */               visiblePc.removeKnownObject((L1Object)petNpc);
/* 208 */               if (visiblePc.get_showId() == petNpc.get_showId()) {
/* 209 */                 subjects.add(visiblePc);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 216 */         if (!pc.getDolls().isEmpty()) {
/*     */           
/* 218 */           L1Location loc = pc.getLocation().randomLocation(3, false);
/* 219 */           int nx = loc.getX();
/* 220 */           int ny = loc.getY();
/*     */           
/* 222 */           Object[] dolls = pc.getDolls().values().toArray(); byte b; int i; Object[] arrayOfObject1;
/* 223 */           for (i = (arrayOfObject1 = dolls).length, b = 0; b < i; ) { Object obj = arrayOfObject1[b];
/* 224 */             L1DollInstance doll = (L1DollInstance)obj;
/* 225 */             teleport((L1NpcInstance)doll, nx, ny, mapId, head);
/* 226 */             pc.sendPackets((ServerBasePacket)new S_NPCPack_Doll(doll, pc));
/*     */             
/* 228 */             doll.set_showId(pc.get_showId());
/*     */             
/* 230 */             for (L1PcInstance visiblePc : World.get().getVisiblePlayer((L1Object)doll)) {
/*     */               
/* 232 */               visiblePc.removeKnownObject((L1Object)doll);
/* 233 */               if (visiblePc.get_showId() == doll.get_showId()) {
/* 234 */                 subjects.add(visiblePc);
/*     */               }
/*     */             } 
/*     */             b++; }
/*     */         
/*     */         } 
/* 240 */         if (pc_mapId != mapId) {
/*     */           
/* 242 */           L1MapsLimitTime mapsLimitTime = MapsGroupTable.get().findGroupMap(mapId);
/* 243 */           if (mapsLimitTime != null) {
/* 244 */             int order_id = mapsLimitTime.getOrderId();
/* 245 */             int used_time = pc.getMapsTime(order_id);
/* 246 */             int limit_time = mapsLimitTime.getLimitTime();
/*     */             
/* 248 */             if (used_time < limit_time) {
/* 249 */               pc.sendPackets((ServerBasePacket)new S_PacketBox(153, limit_time - used_time));
/*     */             }
/*     */           } 
/*     */         } 
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
/* 277 */         ArrayList<L1EffectInstance> effectlist = pc.get_TrueTargetEffectList();
/* 278 */         for (L1EffectInstance effect : effectlist) {
/* 279 */           L1Location loc = pc.getLocation();
/* 280 */           int nx = loc.getX();
/* 281 */           int ny = loc.getY();
/* 282 */           teleport((L1NpcInstance)effect, nx, ny, mapId, head);
/* 283 */           pc.sendPackets((ServerBasePacket)new S_NPCPack_Eff(effect));
/*     */           
/* 285 */           effect.set_showId(pc.get_showId());
/*     */           
/* 287 */           for (L1PcInstance knownPc : effect.getKnownPlayers()) {
/*     */             
/* 289 */             knownPc.removeKnownObject((L1Object)effect);
/* 290 */             if (knownPc.get_showId() == effect.get_showId()) {
/* 291 */               knownPc.addKnownObject((L1Object)effect);
/* 292 */               knownPc.sendPackets((ServerBasePacket)new S_NPCPack_Eff(effect));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 297 */         if (pc.getSkins() != null) {
/* 298 */           Map<Integer, L1SkinInstance> skinList = pc.getSkins();
/* 299 */           for (Integer gfxid : skinList.keySet()) {
/* 300 */             L1SkinInstance skin = skinList.get(gfxid);
/* 301 */             teleport((L1NpcInstance)skin, pc.getX(), pc.getY(), pc.getMapId(), pc.getHeading());
/* 302 */             pc.sendPackets((ServerBasePacket)new S_NPCPack_Skin(skin));
/*     */             
/* 304 */             skin.set_showId(pc.get_showId());
/*     */             
/* 306 */             Iterator<L1PcInstance> iterator = World.get().getVisiblePlayer((L1Object)skin).iterator(); while (iterator.hasNext()) { L1PcInstance visiblePc = iterator.next();
/*     */               
/* 308 */               visiblePc.removeKnownObject((L1Object)skin);
/* 309 */               if (visiblePc.get_showId() == skin.get_showId()) {
/* 310 */                 subjects.add(visiblePc);
/*     */               } }
/*     */           
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 317 */         if (pc.getHierarchs() != null) {
/* 318 */           int nx = pc.getX();
/* 319 */           int ny = pc.getY();
/*     */ 
/*     */           
/* 322 */           teleport((L1NpcInstance)pc.getHierarchs(), nx, ny, mapId, head);
/* 323 */           pc.sendPackets((ServerBasePacket)new S_NPCPack_Hierarch(pc.getHierarchs()));
/*     */           
/* 325 */           pc.getHierarchs().set_showId(pc.get_showId());
/*     */           
/* 327 */           for (L1PcInstance visiblePc : World.get().getVisiblePlayer((L1Object)pc.getHierarchs())) {
/* 328 */             visiblePc.removeKnownObject((L1Object)pc.getHierarchs());
/* 329 */             if (visiblePc.get_showId() == pc.getHierarchs().get_showId()) {
/* 330 */               subjects.add(visiblePc);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       for (L1PcInstance updatePc : subjects) {
/* 341 */         updatePc.updateObject();
/*     */       }
/*     */       
/* 344 */       Integer time = (Integer)ServerUseMapTimer.MAP.get(pc);
/* 345 */       if (time != null) {
/* 346 */         ServerUseMapTimer.put(pc, time.intValue());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 353 */       if (pc.getoldMapId() != pc.getMapId()) {
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
/* 364 */         if (pc.getnpcdmg() > 0.0D) {
///* 365 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU目前攻擊累積傷害:" + pc.getnpcdmg()));
/* 366 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU因您更新地圖,攻擊傷害累積重新計算"));
/* 367 */           pc.setnpcdmg(0.0D);
/*     */         } 
/* 369 */         pc.setoldMapId(pc.getMapId());
/*     */       } 
/* 371 */       pc.setTeleport(false);
/*     */ 
/*     */       
/* 374 */       if (pc.hasSkillEffect(167)) {
/* 375 */         pc.sendPackets((ServerBasePacket)new S_PacketBoxWindShackle(pc.getId(), pc.getSkillEffectTimeSec(167)));
/*     */       }
/* 377 */       pc.sendPackets((ServerBasePacket)new S_Paralysis(7, false));
/*     */       
/* 379 */       if (!pc.isGmInvis()) {
/* 380 */         pc.getMap().setPassable((Point)pc.getLocation(), false);
/*     */       }
/*     */       
/* 383 */       pc.getPetModel();
/*     */     }
/* 385 */     catch (Exception e) {
/* 386 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
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
/*     */   public static void teleport(L1NpcInstance npc, int x, int y, short map, int head) {
/*     */     try {
/* 428 */       World.get().moveVisibleObject((L1Object)npc, map);
/*     */       
/* 430 */       L1WorldMap.get().getMap(npc.getMapId())
/* 431 */         .setPassable(npc.getX(), npc.getY(), true, 2);
/* 432 */       npc.setX(x);
/* 433 */       npc.setY(y);
/* 434 */       npc.setMap(map);
/* 435 */       npc.setHeading(head);
/* 436 */       L1WorldMap.get().getMap(npc.getMapId())
/* 437 */         .setPassable(npc.getX(), npc.getY(), false, 2);
/*     */     }
/* 439 */     catch (Exception e) {
/* 440 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\serve\\utils\Teleportation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */