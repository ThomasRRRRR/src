// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.utils;

import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.world.WorldEffect;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.datatables.SpawnRandomMobTable;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.model.Instance.L1CrockInstance;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.skill.L1SkillId;
import com.eric.RandomMobDeleteTimer;
import com.lineage.server.IdFactory;
import com.eric.RandomMobTable;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.templates.DeName;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.Instance.L1DoorInstance;

import java.util.Iterator;

import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_NPCPack;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.datatables.lock.FurnitureSpawnReading;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.templates.L1Furniture;
//import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.NpcBownTheadPool;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_PacketBoxGree1;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_NPCPack_Eff;
import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class L1SpawnUtil
{
    private static final Log _log;
    private static final byte[] HEADING_TABLE_X;
    private static final byte[] HEADING_TABLE_Y;
    
    static {
        _log = LogFactory.getLog((Class)L1SpawnUtil.class);
        HEADING_TABLE_X = new byte[] { 0, 1, 1, 1, 0, -1, -1, -1 };
        HEADING_TABLE_Y = new byte[] { -1, -1, 0, 1, 1, 1, 0, -1 };
    }
    
    public static L1EffectInstance spawnTrueTargetEffect(final int npcId, final int time, final L1Character cha, final L1PcInstance user, final int skiiId, final int gfxid) {
        L1EffectInstance effect = null;
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            if (npc == null) {
                return null;
            }
            effect = (L1EffectInstance)npc;
            effect.setId(IdFactoryNpc.get().nextId());
            effect.setGfxId(gfxid);
            effect.setTempCharGfx(gfxid);
            effect.setX(cha.getX());
            effect.setY(cha.getY());
            effect.setHomeX(cha.getX());
            effect.setHomeY(cha.getY());
            effect.setHeading(0);
            effect.setMap(cha.getMapId());
            effect.setSkillId(skiiId);
            effect.setFollowSpeed(cha);
            if (user != null) {
                effect.setMaster(user);
                effect.set_showId(user.get_showId());
            }
            World.get().storeObject(effect);
            if (user.getClan() != null) {
                final L1PcInstance[] onlinemembers = WorldClan.get().getClan(user.getClanname()).getOnlineClanMember();
                L1PcInstance[] array;
                for (int length = (array = onlinemembers).length, i = 0; i < length; ++i) {
                    final L1PcInstance clanmember = array[i];
                    effect.addKnownObject(clanmember);
                    clanmember.addKnownObject(effect);
                    clanmember.sendPackets(new S_NPCPack_Eff(effect));
                    clanmember.sendPackets(new S_DoActionGFX(effect.getId(), 4));
                    effect.onPerceive(clanmember);
                }
            }
            else {
                effect.addKnownObject(user);
                user.addKnownObject(effect);
                user.sendPackets(new S_NPCPack_Eff(effect));
                user.sendPackets(new S_DoActionGFX(effect.getId(), 4));
                effect.onPerceive(user);
            }
            if (time > 0) {
                effect.set_spawnTime(time);
            }
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return effect;
    }
    
    public static L1NpcInstance spawnS(final L1Location loc, final int npcId, final int showid, final int time, final int heading) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(loc.getMap());
            npc.getLocation().set(loc);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(heading);
            npc.set_showId(showid);
            World.get().storeObject(npc);
            if (time > 0) {
                npc.set_spawnTime(time);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("依LOC位置召喚指定NPC(傳回NPC訊息)發生異常: " + npcId), (Throwable)e);
            return null;
        }
    }
    
    public static L1NpcInstance spawnRx(final L1Location loc, final int npcId, final int showid, final int r, final int time) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(loc.getMap());
            if (r == 0) {
                npc.getLocation().set(loc);
            }
            else {
                int tryCount = 0;
                do {
                    ++tryCount;
                    npc.setX(loc.getX() + (int)(Math.random() * r) - (int)(Math.random() * r));
                    npc.setY(loc.getY() + (int)(Math.random() * r) - (int)(Math.random() * r));
                    if (npc.getMap().isInMap(npc.getLocation()) && npc.getMap().isPassable(npc.getLocation(), npc)) {
                        break;
                    }
                    Thread.sleep(2L);
                } while (tryCount < 50);
                if (tryCount >= 50) {
                    npc.getLocation().set(loc);
                }
            }
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(5);
            npc.set_showId(showid);
            final L1QuestUser q = WorldQuest.get().get(showid);
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            if (time > 0) {
                npc.set_spawnTime(time);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("依LOC位置召喚指定NPC(傳回NPC訊息)發生異常: " + npcId), (Throwable)e);
            return null;
        }
    }
    
    public static void spawnR(final L1Location loc, final int npcId, final int showid, final int randomRange) {
        final L1SpawnUtil util = new L1SpawnUtil();
        util.spawnR(loc, npcId, showid, randomRange, 0);
    }
    
    private void spawnR(final L1Location loc, final int npcId, final int showid, final int randomRange, final int timeMillisToDelete) {
    	SpawnR1 spawn = new SpawnR1(loc, npcId, showid, randomRange, timeMillisToDelete);
//        GeneralThreadPool.get().schedule(spawn, 0L);
        NpcBownTheadPool.get().schedule(spawn, 0);
    }
    
    public static void spawn(final L1Furniture temp) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(temp.get_npcid());
            if (npc == null) {
                return;
            }
            final L1FurnitureInstance furniture = (L1FurnitureInstance)npc;
            furniture.setId(IdFactoryNpc.get().nextId());
            furniture.setMap(temp.get_mapid());
            furniture.setX(temp.get_locx());
            furniture.setY(temp.get_locy());
            furniture.setHomeX(furniture.getX());
            furniture.setHomeY(furniture.getY());
            furniture.setHeading(0);
            furniture.setItemObjId(temp.get_item_obj_id());
            World.get().storeObject(furniture);
            World.get().addVisibleObject(furniture);
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行傢俱召喚發生異常!", (Throwable)e);
        }
    }
    
    public static L1FurnitureInstance spawn(final L1PcInstance pc, final int npcid, final int itemObjectId) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            final L1FurnitureInstance furniture = (L1FurnitureInstance)npc;
            furniture.setId(IdFactoryNpc.get().nextId());
            furniture.setMap(pc.getMapId());
            if (pc.getHeading() == 0) {
                furniture.setX(pc.getX());
                furniture.setY(pc.getY() - 1);
            }
            else if (pc.getHeading() == 2) {
                furniture.setX(pc.getX() + 1);
                furniture.setY(pc.getY());
            }
            furniture.setHomeX(furniture.getX());
            furniture.setHomeY(furniture.getY());
            furniture.setHeading(0);
            furniture.setItemObjId(itemObjectId);
            World.get().storeObject(furniture);
            World.get().addVisibleObject(furniture);
            FurnitureSpawnReading.get().insertFurniture(furniture);
            return furniture;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行傢俱召喚發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1NpcInstance spawn(final int npcid, final int x, final int y, final short m, final int h) {
        return spawnT(npcid, x, y, m, h, 0);
    }
    
    public static L1NpcInstance spawnT(final int npcid, final int x, final int y, final short m, final int h, final int time) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(m);
            npc.setX(x);
            npc.setY(y);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(h);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            if (time > 0) {
                npc.set_spawnTime(time);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("執行NPC召喚發生異常: " + npcid), (Throwable)e);
            return null;
        }
    }
    
    public static L1IllusoryInstance spawn(final L1PcInstance pc, final L1Location loc, final int h, final int time) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(81003);
            if (npc == null) {
                return null;
            }
            final L1IllusoryInstance ill = (L1IllusoryInstance)npc;
            ill.setId(IdFactoryNpc.get().nextId());
            ill.setMap(loc.getMap());
            ill.setX(loc.getX());
            ill.setY(loc.getY());
            ill.setHomeX(ill.getX());
            ill.setHomeY(ill.getY());
            ill.setHeading(h);
            ill.setNameId("分身");
            ill.setTitle(String.valueOf(String.valueOf(pc.getName())) + " 的");
            ill.setMaster(pc);
            ill.set_showId(pc.get_showId());
            final L1QuestUser q = WorldQuest.get().get(pc.get_showId());
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(ill);
            World.get().addVisibleObject(ill);
            if (time > 0) {
                ill.set_spawnTime(time);
            }
            if (pc.getWeapon() != null) {
                ill.setStatus(pc.getWeapon().getItem().getType1());
                if (pc.getWeapon().getItem().getRange() != -1) {
                    ill.set_ranged(2);
                }
                else {
                    ill.set_ranged(10);
                    ill.setBowActId(66);
                }
            }
            ill.setLevel((int)(pc.getLevel() * 0.7));
            ill.setStr((int)(pc.getStr() * 0.7));
            ill.setCon((int)(pc.getCon() * 0.7));
            ill.setDex((int)(pc.getDex() * 0.7));
            ill.setInt((int)(pc.getInt() * 0.7));
            ill.setWis((int)(pc.getWis() * 0.7));
            ill.setMaxMp(10);
            ill.setCurrentMpDirect(10);
            ill.setTempCharGfx(pc.getTempCharGfx());
            ill.setGfxId(pc.getGfxId());
            final int attack = SprTable.get().getAttackSpeed(pc.getGfxId(), 1);
            final int move = SprTable.get().getMoveSpeed(pc.getGfxId(), ill.getStatus());
            ill.setPassispeed(move);
            ill.setAtkspeed(attack);
            ill.setBraveSpeed(pc.getBraveSpeed());
            ill.setMoveSpeed(pc.getMoveSpeed());
            return ill;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行分身召喚發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1MonsterInstance spawnX(final int npcid, final L1Location loc, final int show_id) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            final L1MonsterInstance mob = (L1MonsterInstance)npc;
            mob.setId(IdFactoryNpc.get().nextId());
            mob.setMap(loc.getMap());
            mob.setX(loc.getX());
            mob.setY(loc.getY());
            mob.setHomeX(mob.getX());
            mob.setHomeY(mob.getY());
            mob.set_showId(show_id);
            final L1QuestUser q = WorldQuest.get().get(show_id);
            if (q != null) {
                q.addNpc(npc);
            }
            mob.setMovementDistance(20);
            World.get().storeObject(mob);
            World.get().addVisibleObject(mob);
            return mob;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行召喚救援發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1MonsterInstance spawnParty(final L1NpcInstance master, final int npcid, final L1Location loc) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            final L1MonsterInstance mob = (L1MonsterInstance)npc;
            mob.setId(IdFactoryNpc.get().nextId());
            mob.setMap(loc.getMap());
            mob.setX(loc.getX());
            mob.setY(loc.getY());
            mob.setHomeX(mob.getX());
            mob.setHomeY(mob.getY());
            mob.setHeading(master.getHeading());
            mob.setMaster(master);
            mob.set_showId(master.get_showId());
            final L1QuestUser q = WorldQuest.get().get(master.get_showId());
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(mob);
            World.get().addVisibleObject(mob);
            return mob;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行召喚指定隊員發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1NpcInstance spawn(final int npcid, final L1Location loc) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(loc.getMap());
            npc.setX(loc.getX());
            npc.setY(loc.getY());
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.onNpcAI();
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行分身召喚發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1NpcInstance spawn(final int npcid, final L1Location loc, final int heading, final int showId) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(loc.getMap());
            npc.setX(loc.getX());
            npc.setY(loc.getY());
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(heading);
            npc.set_showId(showId);
            final L1QuestUser q = WorldQuest.get().get(showId);
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            final int gfx = npc.getTempCharGfx();
            switch (gfx) {
                case 7548:
                case 7550:
                case 7552:
                case 7554:
                case 7585:
                case 7591:
                case 7840:
                case 8096: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                    break;
                }
                case 7539:
                case 7557:
                case 7558:
                case 7864:
                case 7869:
                case 7870:
                case 8036:
                case 8054:
                case 8055: {
                    for (final L1PcInstance _pc : World.get().getVisiblePlayer(npc, 50)) {
                        if (npc.getTempCharGfx() == 7539) {
                            _pc.sendPackets(new S_ServerMessage(1570));
                        }
                        else if (npc.getTempCharGfx() == 7864) {
                            _pc.sendPackets(new S_ServerMessage(1657));
                        }
                        else {
                            if (npc.getTempCharGfx() != 8036) {
                                continue;
                            }
                            _pc.sendPackets(new S_ServerMessage(1755));
                        }
                    }
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                    break;
                }
                case 145:
                case 2158:
                case 3547:
                case 3566:
                case 3957:
                case 3969:
                case 3984:
                case 3989:
                case 7719:
                case 10071:
                case 11465:
                case 11467: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 4));
                    break;
                }
                case 30: {
                    npc.broadcastPacketAll(new S_NPCPack(npc));
                    npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 30));
                    break;
                }
            }
            npc.turnOnOffLight();
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行副本NPC召喚發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1NpcInstance spawn1(final int npcid, final int x, final int y, final short m, final int h, final int gfxid, final int time) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(m);
            npc.setX(x);
            npc.setY(y);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(h);
            npc.setTempCharGfx(gfxid);
            npc.setGfxId(gfxid);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            if (time > 0) {
                npc.set_spawnTime(time);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("執行賭場參賽者NPC召喚發生異常: " + npcid), (Throwable)e);
            return null;
        }
    }
    
    public static void doSpawnFireWall(final L1Character cha, final int targetX, final int targetY) {
        final L1SpawnUtil util = new L1SpawnUtil();
        util.doSpawnFireWallR(cha, targetX, targetY);
    }
    
    public void doSpawnFireWallR(final L1Character cha, final int targetX, final int targetY) {
    	SpawnR2 spawn = new SpawnR2(cha, targetX, targetY);
//        GeneralThreadPool.get().schedule(spawn, 0L);
        NpcBownTheadPool.get().schedule(spawn, 0);
    }
    
    public static L1EffectInstance spawnEffect(final int npcId, final int time, final int locX, final int locY, final short mapId, final L1Character user, final int skiiId) {
        L1EffectInstance effect = null;
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            if (npc == null) {
                return null;
            }
            effect = (L1EffectInstance)npc;
            effect.setId(IdFactoryNpc.get().nextId());
            effect.setGfxId(effect.getGfxId());
            effect.setX(locX);
            effect.setY(locY);
            effect.setHomeX(locX);
            effect.setHomeY(locY);
            effect.setHeading(0);
            effect.setMap(mapId);
            if (user != null) {
                effect.setMaster(user);
                effect.set_showId(user.get_showId());
                final L1QuestUser q = WorldQuest.get().get(user.get_showId());
                if (q != null) {
                    q.addNpc(npc);
                }
                if (effect.getNpcId() == 86126 && effect.getMapId() != 5153) {
                    effect.setNameId(user.getName());
                    effect.setTempLawful(user.getLawful());
                }
            }
            effect.setSkillId(skiiId);
            World.get().storeObject(effect);
            World.get().addVisibleObject(effect);
            effect.broadcastPacketAll(new S_NPCPack_Eff(effect));
            if (effect.getGfxId() == 13592) {
                effect.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 4));
            }
            for (final L1PcInstance pc : World.get().getRecognizePlayer(effect)) {
                effect.addKnownObject(pc);
                pc.addKnownObject(effect);
            }
            if (time > 0) {
                effect.set_spawnTime(time);
            }
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return effect;
    }
    
    public static L1DoorInstance spawnDoor(final L1QuestUser quest, final int doorId, final int gfxid, final int x, final int y, final short mapid, final int direction) {
        final L1Npc l1npc = NpcTable.get().getTemplate(81158);
        if (l1npc != null) {
            final L1DoorInstance door = (L1DoorInstance)NpcTable.get().newNpcInstance(l1npc);
            final int objid = IdFactoryNpc.get().nextId();
            door.setId(objid);
            door.setDoorId(doorId);
            door.setGfxId(gfxid);
            door.setX(x);
            door.setY(y);
            door.setMap(mapid);
            door.setHomeX(x);
            door.setHomeY(y);
            door.setDirection(direction);
            switch (gfxid) {
                case 89: {
                    door.setLeftEdgeLocation(y);
                    door.setRightEdgeLocation(y);
                    break;
                }
                case 88: {
                    if (mapid == 9000) {
                        door.setLeftEdgeLocation(x - 1);
                        door.setRightEdgeLocation(x + 1);
                        break;
                    }
                    door.setLeftEdgeLocation(x - 1);
                    door.setRightEdgeLocation(x + 1);
                    break;
                }
                case 90: {
                    door.setLeftEdgeLocation(x);
                    door.setRightEdgeLocation(x + 1);
                    break;
                }
                case 7556: {
                    door.setLeftEdgeLocation(y - 1);
                    door.setRightEdgeLocation(y + 3);
                    break;
                }
                case 7858:
                case 8011: {
                    door.setLeftEdgeLocation(x - 2);
                    door.setRightEdgeLocation(x + 3);
                    break;
                }
                case 7859:
                case 8015:
                case 8019: {
                    door.setLeftEdgeLocation(y - 2);
                    door.setRightEdgeLocation(y + 3);
                    break;
                }
                default: {
                    door.setLeftEdgeLocation(y);
                    door.setRightEdgeLocation(y);
                    break;
                }
            }
            door.setMaxHp(0);
            door.setCurrentHp(0);
            door.setKeeperId(0);
            if (quest != null) {
                door.set_showId(quest.get_id());
                quest.addNpc(door);
            }
            door.close();
            World.get().storeObject(door);
            World.get().addVisibleObject(door);
            return door;
        }
        return null;
    }
    
    public static L1DeInstance spawn(final L1PcInstance pc, final DeName de, final int timeMillisToDelete) {
        try {
            final L1Npc l1npc = NpcTable.get().getTemplate(81002);
            if (l1npc == null) {
                pc.sendPackets(new S_SystemMessage("找不到該npc。"));
                return null;
            }
            final L1DeInstance npc = new L1DeInstance(l1npc);
            npc.startNpc(de);
            final int randomRange = 5;
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(pc.getMapId());
            int tryCount = 0;
            do {
                ++tryCount;
                npc.setX(pc.getX() + (int)(Math.random() * 5.0) - (int)(Math.random() * 5.0));
                npc.setY(pc.getY() + (int)(Math.random() * 5.0) - (int)(Math.random() * 5.0));
                if (npc.getMap().isInMap(npc.getLocation()) && npc.getMap().isPassable(npc.getLocation(), npc)) {
                    break;
                }
                Thread.sleep(2L);
            } while (tryCount < 50);
            if (tryCount >= 50) {
                npc.getLocation().set(pc.getLocation());
                npc.getLocation().forward(pc.getHeading());
            }
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(pc.getHeading());
            npc.set_showId(pc.get_showId());
            final L1QuestUser q = WorldQuest.get().get(pc.get_showId());
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            if (timeMillisToDelete > 0) {
                npc.set_spawnTime(timeMillisToDelete);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行DE召喚發生異常", (Throwable)e);
            return null;
        }
    }
    
    public static L1FieldObjectInstance spawn(final int showid, final int gfxid, final int x, final int y, final int map, final int timeMillisToDelete) {
        try {
            final L1FieldObjectInstance field = (L1FieldObjectInstance)NpcTable.get().newNpcInstance(71081);
            if (field != null) {
                field.setId(IdFactoryNpc.get().nextId());
                field.setGfxId(gfxid);
                field.setTempCharGfx(gfxid);
                field.setMap((short)map);
                field.setX(x);
                field.setY(y);
                field.setHomeX(x);
                field.setHomeY(y);
                field.setHeading(5);
                field.set_showId(showid);
                final L1QuestUser q = WorldQuest.get().get(showid);
                if (q != null) {
                    q.addNpc(field);
                }
                World.get().storeObject(field);
                World.get().addVisibleObject(field);
                if (timeMillisToDelete > 0) {
                    field.set_spawnTime(timeMillisToDelete);
                }
                return field;
            }
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行景觀(副本專用)召喚發生異常", (Throwable)e);
        }
        return null;
    }
    
    public static L1NpcInstance spawn(final int npcid, final int x, final int y, final short m, final int h, final int gfxid) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(m);
            npc.setX(x);
            npc.setY(y);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(h);
            npc.setTempCharGfx(gfxid);
            npc.setGfxId(gfxid);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("執行NPC召喚發生異常: " + npcid), (Throwable)e);
            return null;
        }
    }
    
    public static void spawn(final L1PcInstance pc, final int npcId, final int r, final int t) {
        final L1SpawnUtil util = new L1SpawnUtil();
        util.spawnR(pc.getLocation(), npcId, pc.get_showId(), r, t);
    }
    
    public static void spawn(final int randomMobId) {
        try {
            final RandomMobTable mobTable = RandomMobTable.getInstance();
            final int mobCont = mobTable.getCont(randomMobId);
            final int mobId = mobTable.getMobId(randomMobId);
            final L1NpcInstance[] npc = new L1NpcInstance[mobCont];
            for (int i = 0; i < mobCont; ++i) {
                final short mapId = mobTable.getRandomMapId(randomMobId);
                final int x = mobTable.getRandomMapX(mapId);
                final int y = mobTable.getRandomMapY(mapId);
                (npc[i] = NpcTable.get().newNpcInstance(mobId)).setId(IdFactory.get().nextId());
                npc[i].setMap(mapId);
                int tryCount = 0;
                do {
                    ++tryCount;
                    npc[i].setX(x + Random.nextInt(200) - Random.nextInt(200));
                    npc[i].setY(y + Random.nextInt(200) - Random.nextInt(200));
                    final L1Map map = npc[i].getMap();
                    if (map.isInMap(npc[i].getLocation()) && map.isPassable(npc[i].getLocation(), null)) {
                        break;
                    }
                    Thread.sleep(1L);
                } while (tryCount < 50);
                if (tryCount >= 50) {
                    boolean find = false;
                    for (final Object obj : World.get().getVisibleObjects(mapId).values()) {
                        if (obj instanceof L1PcInstance) {
                            final L1PcInstance findPc = (L1PcInstance)obj;
                            npc[i].getLocation().set(findPc.getLocation());
                            npc[i].getLocation().forward(findPc.getHeading());
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        continue;
                    }
                }
                npc[i].setHomeX(npc[i].getX());
                npc[i].setHomeY(npc[i].getY());
                npc[i].setHeading(0);
                World.get().storeObject(npc[i]);
                World.get().addVisibleObject(npc[i]);
                npc[i].turnOnOffLight();
                npc[i].startChat(0);
            }
            final int timeSecondToDelete = mobTable.getTimeSecondToDelete(randomMobId);
            if (timeSecondToDelete > 0) {
                final RandomMobDeleteTimer timer = new RandomMobDeleteTimer(randomMobId, npc);
                timer.begin();
            }
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static L1CrockInstance spawn(final L1Location loc) {
        try {
            final L1Npc l1npc = NpcTable.get().getTemplate(71254);
            if (l1npc == null) {
                L1SpawnUtil._log.error((Object)"找不到該npc:71254");
                return null;
            }
            final L1CrockInstance npc = new L1CrockInstance(l1npc);
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(loc.getMap());
            npc.setX(loc.getX());
            npc.setY(loc.getY());
            npc.setHeading(5);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行召喚時空裂痕發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static L1SkinInstance spawnSkin(final L1PcInstance pc, final int gfxid) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(81001);
            if (npc == null) {
                return null;
            }
            final L1SkinInstance skin = (L1SkinInstance)npc;
            skin.setId(IdFactoryNpc.get().nextId());
            skin.setMap(pc.getMap());
            skin.setX(pc.getX());
            skin.setY(pc.getY());
            skin.setHomeX(pc.getX());
            skin.setHomeY(pc.getY());
            skin.setHeading(pc.getHeading());
            skin.setMaster(pc);
            skin.set_showId(pc.get_showId());
            final L1QuestUser q = WorldQuest.get().get(pc.get_showId());
            if (q != null) {
                q.addNpc(npc);
            }
            World.get().storeObject(skin);
            World.get().addVisibleObject(skin);
            skin.setTempCharGfx(gfxid);
            skin.setGfxId(gfxid);
            final int attack = SprTable.get().getAttackSpeed(gfxid, 1);
            final int move = SprTable.get().getMoveSpeed(gfxid, skin.getStatus());
            skin.setPassispeed(move);
            skin.setAtkspeed(attack);
            skin.setBraveSpeed(pc.getBraveSpeed());
            skin.setMoveSpeed(pc.getMoveSpeed());
            return skin;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)"執行光圈召喚發生異常!", (Throwable)e);
            return null;
        }
    }
    
    public static void spawnMob(final int npcId, final int x, final int y, final short mapid, final int heading, final int isCount) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcId);
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(mapid);
            if (isCount > 0) {
                npc.setNameId("遠古" + npc.getNameId());
            }
            else {
                npc.setNameId(npc.getNameId());
            }
            npc.setX(x);
            npc.setY(y);
            npc.setHomeX(x);
            npc.setHomeY(y);
            npc.setHeading(heading);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.startChat(0);
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static L1NpcInstance spawnai(final int npcid, final int x, final int y, final int h, final int gfxid, final int time) {
        try {
            final L1NpcInstance npc = NpcTable.get().newNpcInstance(npcid);
            if (npc == null) {
                return null;
            }
            npc.setId(IdFactoryNpc.get().nextId());
            npc.setMap(npc.getMapId());
            npc.setX(x);
            npc.setY(y);
            npc.setHomeX(npc.getX());
            npc.setHomeY(npc.getY());
            npc.setHeading(h);
            npc.setTempCharGfx(gfxid);
            npc.setGfxId(gfxid);
            World.get().storeObject(npc);
            World.get().addVisibleObject(npc);
            npc.turnOnOffLight();
            npc.startChat(0);
            if (time > 0) {
                npc.set_spawnTime(time);
            }
            return npc;
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)("執行NPC召喚發生異常: " + npcid), (Throwable)e);
            return null;
        }
    }
    
    public static void spawnRandomMob(final int randomMobId) {
        try {
            final SpawnRandomMobTable mobTable = SpawnRandomMobTable.get();
            final int mobCont = mobTable.getCont(randomMobId);
            final int mobId = mobTable.getMobId(randomMobId);
            final L1NpcInstance[] npc = new L1NpcInstance[mobCont];
            for (int i = 0; i < mobCont; ++i) {
                final short mapId = mobTable.getRandomMapId(randomMobId);
                final int x = mobTable.getRandomMapX(mapId);
                final int y = mobTable.getRandomMapY(mapId);
                (npc[i] = NpcTable.get().newNpcInstance(mobId)).setId(IdFactoryNpc.get().nextId());
                npc[i].setMap(mapId);
                int tryCount = 0;
                do {
                    ++tryCount;
                    npc[i].setX(x + Random.nextInt(200) - Random.nextInt(200));
                    npc[i].setY(y + Random.nextInt(200) - Random.nextInt(200));
                    final L1Map map = npc[i].getMap();
                    if (map.isInMap(npc[i].getLocation()) && map.isPassable(npc[i].getLocation(), npc[i])) {
                        break;
                    }
                    Thread.sleep(1L);
                } while (tryCount < 50);
                if (tryCount >= 50) {
                    boolean find = false;
                    for (final Object obj : World.get().getVisibleObjects(mapId).values()) {
                        if (obj instanceof L1PcInstance) {
                            final L1PcInstance findPc = (L1PcInstance)obj;
                            npc[i].getLocation().set(findPc.getLocation());
                            npc[i].getLocation().forward(findPc.getHeading());
                            find = true;
                            break;
                        }
                    }
                    if (!find) {
                        continue;
                    }
                }
                npc[i].setHomeX(npc[i].getX());
                npc[i].setHomeY(npc[i].getY());
                npc[i].setHeading(0);
                World.get().storeObject(npc[i]);
                World.get().addVisibleObject(npc[i]);
                npc[i].startChat(0);
                final int timeMillisToDelete = mobTable.getTimeSecondToDelete(randomMobId);
                if (timeMillisToDelete > 0) {
                    npc[i].set_spawnTime(timeMillisToDelete);
                }
                if (mobTable.isBroad(randomMobId)) {
                    World.get().broadcastPacketToAll(new S_PacketBoxGree1("【" + npc[i].getName() + "】出現在" + MapsTable.get().getMapName(mapId)));
                }
                L1SpawnUtil._log.info((Object)("隨機生怪->【" + npc[i].getName() + "】出現在" + MapsTable.get().getMapName(mapId)));
            }
        }
        catch (Exception e) {
            L1SpawnUtil._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private class SpawnR1 implements Runnable
    {
        final L1Location _location;
        final int _npcId;
        final int _showid;
        final int _randomRange;
        final int _timeMillisToDelete;
        
        private SpawnR1(final L1Location location, final int npcId, final int showid, final int randomRange, final int timeMillisToDelete) {
            this._location = location;
            this._npcId = npcId;
            this._showid = showid;
            this._randomRange = randomRange;
            this._timeMillisToDelete = timeMillisToDelete;
        }
        
        @Override
        public void run() {
            try {
                final L1NpcInstance npc = NpcTable.get().newNpcInstance(this._npcId);
                if (npc == null) {
                    return;
                }
                if (npc instanceof L1DeInstance) {
                    ((L1DeInstance)npc).startNpc(null);
                }
                npc.setId(IdFactoryNpc.get().nextId());
                npc.setMap(this._location.getMap());
                if (this._randomRange == 0) {
                    npc.getLocation().set(this._location);
                }
                else {
                    int tryCount = 0;
                    do {
                        ++tryCount;
                        npc.setX(this._location.getX() + (int)(Math.random() * this._randomRange) - (int)(Math.random() * this._randomRange));
                        npc.setY(this._location.getY() + (int)(Math.random() * this._randomRange) - (int)(Math.random() * this._randomRange));
                        if (npc.getMap().isInMap(npc.getLocation()) && npc.getMap().isPassable(npc.getLocation(), npc)) {
                            break;
                        }
                        Thread.sleep(2L);
                    } while (tryCount < 50);
                    if (tryCount >= 50) {
                        npc.getLocation().set(this._location);
                    }
                }
                npc.setHomeX(npc.getX());
                npc.setHomeY(npc.getY());
                npc.setHeading(5);
                npc.set_showId(this._showid);
                final L1QuestUser q = WorldQuest.get().get(this._showid);
                if (q != null) {
                    q.addNpc(npc);
                }
                if (npc instanceof L1MonsterInstance) {
                    ((L1MonsterInstance)npc).initHide();
                }
                World.get().storeObject(npc);
                World.get().addVisibleObject(npc);
                final int gfx = npc.getTempCharGfx();
                switch (gfx) {
                    case 7548:
                    case 7550:
                    case 7552:
                    case 7554:
                    case 7585:
                    case 7591:
                    case 7840:
                    case 8096: {
                        npc.broadcastPacketAll(new S_NPCPack(npc));
                        npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                        break;
                    }
                    case 7539:
                    case 7557:
                    case 7558:
                    case 7864:
                    case 7869:
                    case 7870:
                    case 8036:
                    case 8054:
                    case 8055: {
                        for (final L1PcInstance _pc : World.get().getVisiblePlayer(npc, 50)) {
                            if (npc.getTempCharGfx() == 7539) {
                                _pc.sendPackets(new S_ServerMessage(1570));
                            }
                            else if (npc.getTempCharGfx() == 7864) {
                                _pc.sendPackets(new S_ServerMessage(1657));
                            }
                            else {
                                if (npc.getTempCharGfx() != 8036) {
                                    continue;
                                }
                                _pc.sendPackets(new S_ServerMessage(1755));
                            }
                        }
                        npc.broadcastPacketAll(new S_NPCPack(npc));
                        npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 11));
                        break;
                    }
                    case 145:
                    case 2158:
                    case 3547:
                    case 3566:
                    case 3957:
                    case 3969:
                    case 3984:
                    case 3989:
                    case 7719:
                    case 10071:
                    case 11465:
                    case 11467: {
                        npc.broadcastPacketAll(new S_NPCPack(npc));
                        npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 4));
                        break;
                    }
                    case 30: {
                        npc.broadcastPacketAll(new S_NPCPack(npc));
                        npc.broadcastPacketAll(new S_DoActionGFX(npc.getId(), 30));
                        break;
                    }
                }
                npc.turnOnOffLight();
                npc.startChat(0);
                if (this._timeMillisToDelete > 0) {
                    npc.set_spawnTime(this._timeMillisToDelete);
                }
            }
            catch (Exception e) {
                L1SpawnUtil._log.error((Object)("執行NPC召喚發生異常: " + this._npcId), (Throwable)e);
            }
        }
    }
    
    private class SpawnR2 implements Runnable
    {
        private final L1Character _cha;
        private final int _targetX;
        private final int _targetY;
        
        private SpawnR2(final L1Character cha, final int targetX, final int targetY) {
            this._cha = cha;
            this._targetX = targetX;
            this._targetY = targetY;
        }
        
        @Override
		public void run() {
			try {
				final int npcid = 81157;// 法師技能(火牢)
				final L1Npc firewall = NpcTable.get().getTemplate(npcid); // 法師技能(火牢)
				final int duration = SkillsTable.get().getTemplate(L1SkillId.FIRE_WALL).getBuffDuration();
				// System.out.println("法師技能(火牢)firewall:"+firewall);

				if (firewall == null) {
					return;
				}

				// 判斷位置用物件
				L1Character base = _cha;
				for (int i = 0; i < 8; i++) {
					Thread.sleep(2);
					// System.out.println("法師技能(火牢)i:"+i);
					int tmp = 0;

					for (final L1Object objects : World.get().getVisibleObjects(_cha)) {
						if (objects == null) {// 對象為空
							continue;
						}
						// 同地點相同主人 tmp + 1
						if (objects instanceof L1EffectInstance) {
							final L1EffectInstance effect = (L1EffectInstance) objects;
							if (effect != null && _cha != null && effect.getMaster() != null) {
								if (effect.getMaster().equals(_cha)) {
									tmp++;
								}
							}
						}
						// System.out.println("同地點相同物件 tmp + 1:"+tmp);
					}
					if (tmp >= 24) {// 畫面內 同使用者 最多召喚24個火牢物件
						return;
					}

					final int a = base.targetDirection(_targetX, _targetY);
					int x = base.getX();
					int y = base.getY();

					// XY位置增加
					x += HEADING_TABLE_X[a];
					y += HEADING_TABLE_Y[a];

					// System.out.println("XY位置增加:"+x+"/"+y);
					if (!base.isAttackPosition(x, y, 1)) {
						x = base.getX();
						y = base.getY();
					}

					// 判斷座標上 是否已有相同NPCID物件
					final L1Location loc = new L1Location(x, y, _cha.getMapId());
					if (WorldEffect.get().isEffect(loc, npcid)) {
						continue;
					}

					final L1Map map = L1WorldMap.get().getMap(_cha.getMapId());
					if (!map.isArrowPassable(x, y, _cha.getHeading())) {
						// System.out.println("指定座標遠程攻擊是否能通過。:"+map.isArrowPassable(x,
						// y, cha.getHeading()));
						break;
					}

					// 施展者 是PC
					if (_cha instanceof L1PcInstance) {
						L1PcInstance user = (L1PcInstance) _cha;
						base = spawnEffect(npcid, duration, x, y, user.getMapId(), user, L1SkillId.FIRE_WALL);

					} else {
						base = spawnEffect(npcid, duration, x, y, _cha.getMapId(), null, L1SkillId.FIRE_WALL);
					}
				}

			} catch (final Exception e) {
				_log.error(e.getLocalizedMessage(), e);

			} finally {
			}
		}
    }
}
