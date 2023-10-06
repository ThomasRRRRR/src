package com.lineage.server.model;

//import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.NpcBownTheadPool;
import com.lineage.server.model.Instance.L1MonsterEnhanceInstance;
import java.util.Iterator;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.Instance.L1BowInstance;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1FurnitureInstance;
import com.lineage.server.model.Instance.L1FieldObjectInstance;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.MonsterEnhanceTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.world.World;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.datatables.NpcTable;
import java.util.HashMap;
import com.lineage.config.ConfigAlt;
import com.lineage.server.model.gametime.L1GameTimeListener;
import com.lineage.server.model.gametime.L1GameTime;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.timecontroller.npc.NpcSpawnBossTimer;
import java.util.ArrayList;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.List;
import com.lineage.server.types.Point;
import java.util.Map;
import java.util.Calendar;
import com.lineage.server.templates.L1SpawnTime;
import com.lineage.server.templates.L1Npc;
import org.apache.commons.logging.Log;
import com.lineage.server.model.gametime.L1GameTimeAdapter;

public class L1Spawn extends L1GameTimeAdapter
{
    private static final Log _log;
    private final L1Npc _template;
    private int _id;
    private String _location;
    private int _maximumCount;
    private int _npcid;
    private int _groupId;
    private int _locx;
    private int _locy;
    private int _tmplocx;
    private int _tmplocy;
    private short _tmpmapid;
    private int _randomx;
    private int _randomy;
    private int _locx1;
    private int _locy1;
    private int _locx2;
    private int _locy2;
    private int _heading;
    private int _minRespawnDelay;
    private int _maxRespawnDelay;
    private short _mapid;
    private boolean _respaenScreen;
    private int _movementDistance;
    private boolean _rest;
    private int _spawnType;
    private int _delayInterval;
    private L1SpawnTime _time;
    private Calendar _nextSpawnTime;
    private long _spawnInterval;
    private int _existTime;
    private Map<Integer, Point> _homePoint;
    private List<L1NpcInstance> _mobs;
    private Random _random;
    private String _name;
    private boolean _initSpawn;
    private boolean _spawnHomePoint;
    private static final int SPAWN_TYPE_PC_AROUND = 1;
    private L1NpcInstance npcTemp;
    private long deleteTime;
    private boolean _isBroadcast;
    private String _broadcastInfo;
    private String _msg1;
    private String _msg2;
    
    static {
        _log = LogFactory.getLog((Class)L1Spawn.class);
    }
    
    public L1Spawn(final L1Npc mobTemplate) {
        this._nextSpawnTime = null;
        this._spawnInterval = 0L;
        this._existTime = 0;
        this._homePoint = null;
        this._mobs = new ArrayList<L1NpcInstance>();
        this._random = new Random();
        this._initSpawn = false;
        this._template = mobTemplate;
    }
    
    public String getName() {
        return this._name;
    }
    
    public void setName(final String name) {
        this._name = name;
    }
    
    public short getMapId() {
        return this._mapid;
    }
    
    public void setMapId(final short _mapid) {
        this._mapid = _mapid;
    }
    
    public boolean isRespawnScreen() {
        return this._respaenScreen;
    }
    
    public void setRespawnScreen(final boolean flag) {
        this._respaenScreen = flag;
    }
    
    public int getMovementDistance() {
        return this._movementDistance;
    }
    
    public void setMovementDistance(final int i) {
        this._movementDistance = i;
    }
    
    public int getAmount() {
        return this._maximumCount;
    }
    
    public int getGroupId() {
        return this._groupId;
    }
    
    public int getId() {
        return this._id;
    }
    
    public String getLocation() {
        return this._location;
    }
    
    public int getLocX() {
        return this._locx;
    }
    
    public int getLocY() {
        return this._locy;
    }
    
    public int getNpcId() {
        return this._npcid;
    }
    
    public int getHeading() {
        return this._heading;
    }
    
    public int getRandomx() {
        return this._randomx;
    }
    
    public int getRandomy() {
        return this._randomy;
    }
    
    public int getLocX1() {
        return this._locx1;
    }
    
    public int getLocY1() {
        return this._locy1;
    }
    
    public int getLocX2() {
        return this._locx2;
    }
    
    public int getLocY2() {
        return this._locy2;
    }
    
    public int getMinRespawnDelay() {
        return this._minRespawnDelay;
    }
    
    public int getMaxRespawnDelay() {
        return this._maxRespawnDelay;
    }
    
    public void setAmount(final int amount) {
        this._maximumCount = amount;
    }
    
    public void setId(final int id) {
        this._id = id;
    }
    
    public void setGroupId(final int i) {
        this._groupId = i;
    }
    
    public void setLocation(final String location) {
        this._location = location;
    }
    
    public void setLocX(final int locx) {
        this._locx = locx;
    }
    
    public void setLocY(final int locy) {
        this._locy = locy;
    }
    
    public void setNpcid(final int npcid) {
        this._npcid = npcid;
    }
    
    public void setHeading(final int heading) {
        this._heading = heading;
    }
    
    public void setRandomx(final int randomx) {
        this._randomx = randomx;
    }
    
    public void setRandomy(final int randomy) {
        this._randomy = randomy;
    }
    
    public void setLocX1(final int locx1) {
        this._locx1 = locx1;
    }
    
    public void setLocY1(final int locy1) {
        this._locy1 = locy1;
    }
    
    public void setLocX2(final int locx2) {
        this._locx2 = locx2;
    }
    
    public void setLocY2(final int locy2) {
        this._locy2 = locy2;
    }
    
    public void setMinRespawnDelay(final int i) {
        this._minRespawnDelay = i;
    }
    
    public void setMaxRespawnDelay(final int i) {
        this._maxRespawnDelay = i;
    }
    
    public int getTmpLocX() {
        return this._tmplocx;
    }
    
    public int getTmpLocY() {
        return this._tmplocy;
    }
    
    public short getTmpMapid() {
        return this._tmpmapid;
    }
    
    private boolean isSpawnTime(final L1NpcInstance npcTemp) {
        if (this._nextSpawnTime == null) {
            return true;
        }
        final Calendar cals = Calendar.getInstance();
        final long nowTime = System.currentTimeMillis();
        cals.setTimeInMillis(nowTime);
        if (cals.after(this._nextSpawnTime)) {
            return true;
        }
        if (NpcSpawnBossTimer.MAP.get(npcTemp) == null) {
            final long spawnTime = this._nextSpawnTime.getTimeInMillis();
            final long spa = (spawnTime - nowTime) / 1000L + 5L;
            NpcSpawnBossTimer.MAP.put(npcTemp, spa);
        }
        return false;
    }
    
    public Calendar get_nextSpawnTime() {
        return this._nextSpawnTime;
    }
    
    public void set_nextSpawnTime(final Calendar next_spawn_time) {
        this._nextSpawnTime = next_spawn_time;
    }
    
    public void set_spawnInterval(final long spawn_interval) {
        this._spawnInterval = spawn_interval;
    }
    
    public long get_spawnInterval() {
        return this._spawnInterval;
    }
    
    public void set_existTime(final int exist_time) {
        this._existTime = exist_time;
    }
    
    public int get_existTime() {
        return this._existTime;
    }
    
    private int calcRespawnDelay() {
        int respawnDelay = this._minRespawnDelay * 1000;
        if (this._delayInterval > 0) {
            respawnDelay += this._random.nextInt(this._delayInterval) * 1000;
        }
        final L1GameTime currentTime = L1GameTimeClock.getInstance().currentTime();
        if (this._time != null && !this._time.getTimePeriod().includes(currentTime)) {
            long diff = this._time.getTimeStart().getTime() - currentTime.toTime().getTime();
            if (diff < 0L) {
                diff += 86400000L;
            }
            diff /= 6L;
            respawnDelay = (int)diff;
        }
        return respawnDelay;
    }
    
    public void executeSpawnTask(final int spawnNumber, final int objectId) {
        if (this._nextSpawnTime != null) {
            this.doSpawn(spawnNumber, objectId);
        }
        else {
        	SpawnTask task = new SpawnTask(spawnNumber, objectId, calcRespawnDelay());
            task.getStart();
        }
    }
    
    public void init() {
        if (this._time != null && this._time.isDeleteAtEndTime()) {
            L1GameTimeClock.getInstance().addListener(this);
        }
        this._delayInterval = this._maxRespawnDelay - this._minRespawnDelay;
        this._initSpawn = true;
        if (ConfigAlt.SPAWN_HOME_POINT && ConfigAlt.SPAWN_HOME_POINT_COUNT <= this.getAmount() && ConfigAlt.SPAWN_HOME_POINT_DELAY >= this.getMinRespawnDelay() && this.isAreaSpawn()) {
            this._spawnHomePoint = true;
            this._homePoint = new HashMap<Integer, Point>();
        }
        int spawnNum = 0;
        while (spawnNum < this._maximumCount) {
            this.doSpawn(++spawnNum);
        }
        this._initSpawn = false;
    }
    
    protected void doSpawn(final int spawnNumber) {
        if (this._time != null && !this._time.getTimePeriod().includes(L1GameTimeClock.getInstance().currentTime())) {
            this.executeSpawnTask(spawnNumber, 0);
            return;
        }
        this.doSpawn(spawnNumber, 0);
    }
    
    protected void doSpawn(final int spawnNumber, final int objectId) {
        this._tmplocx = 0;
        this._tmplocy = 0;
        this._tmpmapid = 0;
        L1NpcInstance npcTemp = null;
        try {
            int newlocx = this.getLocX();
            int newlocy = this.getLocY();
            int tryCount = 0;
            npcTemp = NpcTable.get().newNpcInstance(this._template);
            synchronized (this._mobs) {
                this._mobs.add(npcTemp);
            }
            // monitorexit(this._mobs)
            if (objectId == 0) {
                npcTemp.setId(IdFactoryNpc.get().nextId());
            }
            else {
                npcTemp.setId(objectId);
            }
            if (this.getHeading() >= 0 && this.getHeading() <= 7) {
                npcTemp.setHeading(this.getHeading());
            }
            else {
                npcTemp.setHeading(5);
            }
            final int npcId = npcTemp.getNpcTemplate().get_npcId();
            if (npcId == 45488 && this.getMapId() == 9) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(2)));
            }
            else if (npcId == 45601 && this.getMapId() == 11) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else if (npcId == 45649 && this.getMapId() == 80) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else if (npcId == 105040 && this.getMapId() == 78) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(5)));
            }
            else if (npcId == 105070 && this.getMapId() == 80) {
                npcTemp.setMap((short)(this.getMapId() + this._random.nextInt(3)));
            }
            else {
                npcTemp.setMap(this.getMapId());
            }
            npcTemp.setMovementDistance(this.getMovementDistance());
            npcTemp.setRest(this.isRest());
            while (tryCount <= 50) {
                if (this.isAreaSpawn()) {
                    Point pt = null;
                    if (this._spawnHomePoint && (pt = this._homePoint.get(spawnNumber)) != null) {
                        final L1Location loc = new L1Location(pt, this.getMapId()).randomLocation(ConfigAlt.SPAWN_HOME_POINT_RANGE, false);
                        newlocx = loc.getX();
                        newlocy = loc.getY();
                    }
                    else {
                        final int rangeX = this.getLocX2() - this.getLocX1();
                        final int rangeY = this.getLocY2() - this.getLocY1();
                        newlocx = this._random.nextInt(rangeX) + this.getLocX1();
                        newlocy = this._random.nextInt(rangeY) + this.getLocY1();
                    }
                    if (tryCount > 49) {
                        if (this._nextSpawnTime != null) {
                        	// 延後5秒後重試
							final SpawnTask task = new SpawnTask(spawnNumber, npcTemp.getId(), 5000L);
                            task.getStart();
                            return;
                        }
                        newlocx = this.getLocX();
                        newlocy = this.getLocY();
                    }
                }
                else if (this.isRandomSpawn()) {
                    newlocx = this.getLocX() + ((int)(Math.random() * this.getRandomx()) - (int)(Math.random() * this.getRandomx()));
                    newlocy = this.getLocY() + ((int)(Math.random() * this.getRandomy()) - (int)(Math.random() * this.getRandomy()));
                }
                else {
                    newlocx = this.getLocX();
                    newlocy = this.getLocY();
                }
                if (this.getSpawnType() == 1) {
                    final L1Location loc2 = new L1Location(newlocx, newlocy, this.getMapId());
                    final ArrayList<L1PcInstance> pcs = World.get().getVisiblePc(loc2);
                    if (pcs.size() > 0) {
                        final L1Location newloc = loc2.randomLocation(20, false);
                        newlocx = newloc.getX();
                        newlocy = newloc.getY();
                    }
                }
                npcTemp.setX(newlocx);
                npcTemp.setHomeX(newlocx);
                npcTemp.setY(newlocy);
                npcTemp.setHomeY(newlocy);
                if (this._nextSpawnTime == null) {
                    if (npcTemp.getMap().isInMap(npcTemp.getLocation()) && npcTemp.getMap().isPassable(npcTemp.getLocation(), npcTemp) && npcTemp instanceof L1MonsterInstance) {
                        if (this.isRespawnScreen()) {
                            break;
                        }
                        final ArrayList<L1PcInstance> pcs2 = World.get().getVisiblePc(npcTemp.getLocation());
                        if (pcs2.size() == 0) {
                            break;
                        }
                        // 畫面內具有PC物件 延後5秒
						final SpawnTask task = new SpawnTask(spawnNumber, npcTemp.getId(), 5000L);
                        task.getStart();
                        return;
                    }
                }
                else if (npcTemp.getMap().isPassable(npcTemp.getLocation(), npcTemp)) {
                    break;
                }
                ++tryCount;
            }
            if (npcTemp instanceof L1MonsterInstance) {
                ((L1MonsterInstance)npcTemp).initHide();
            }
            npcTemp.setSpawn(this);
            npcTemp.setreSpawn(true);
            npcTemp.setSpawnNumber(spawnNumber);
            if (this._initSpawn && this._spawnHomePoint) {
                final Point pt = new Point(npcTemp.getX(), npcTemp.getY());
                this._homePoint.put(spawnNumber, pt);
            }
            if (this._nextSpawnTime != null && !this.isSpawnTime(npcTemp)) {
                return;
            }
            if (npcTemp instanceof L1MonsterInstance) {
                final L1MonsterInstance mob = (L1MonsterInstance)npcTemp;
                if (mob.getMapId() == 666) {
                    mob.set_storeDroped(true);
                }
            }
            if (npcId == 45573 && npcTemp.getMapId() == 2) {
                for (final L1PcInstance pc : World.get().getAllPlayers()) {
                    if (pc.getMapId() == 2) {
                        L1Teleport.teleport(pc, 32664, 32797, (short)2, 0, true);
                    }
                }
            }
            if ((npcId == 46142 && npcTemp.getMapId() == 73) || (npcId == 46141 && npcTemp.getMapId() == 74)) {
                for (final L1PcInstance pc : World.get().getAllPlayers()) {
                    if (pc.getMapId() >= 72 && pc.getMapId() <= 74) {
                        L1Teleport.teleport(pc, 32733, 32858, (short)74, pc.getHeading(), true);
                    }
                }
            }
            if (MonsterEnhanceTable.getInstance().getTemplate(npcId) != null) {
                final L1MonsterInstance mob = (L1MonsterInstance)npcTemp;
                final L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(mob.getNpcId());
                if (mei.getDcEnhance2() == 1) {
                    mei.setCurrentDc(0);
                    MonsterEnhanceTable.getInstance().save(mei);
                }
                final int divisor = mei.getCurrentDc() / mei.getDcEnhance();
                mob.setLevel(mob.getLevel() + mei.getLevel() * divisor);
                mob.setMaxHp(mob.getMaxHp() + mei.getHp() * divisor);
                mob.setMaxMp(mob.getMaxMp() + mei.getLevel() * divisor);
                mob.setCurrentHp(mob.getMaxHp());
                mob.setCurrentMp(mob.getMaxMp());
                mob.setAc(mob.getAc() + mei.getAc() * divisor);
                mob.setStr(mob.getStr() + mei.getStr() * divisor);
                mob.setDex(mob.getDex() + mei.getDex() * divisor);
                mob.setCon(mob.getCon() + mei.getCon() * divisor);
                mob.setWis(mob.getWis() + mei.getWis() * divisor);
                mob.setInt(mob.getInt() + mei.getInt() * divisor);
                mob.setMr(mob.getMr() + mei.getMr() * divisor);
            }
            doCrystalCave(npcId);
            World.get().storeObject(npcTemp);
            World.get().addVisibleObject(npcTemp);
            if (npcTemp instanceof L1MonsterInstance) {
                final L1MonsterInstance mobtemp = (L1MonsterInstance)npcTemp;
                if (!this._initSpawn && mobtemp.getHiddenStatus() == 0) {
                    mobtemp.onNpcAI();
                }
            }
            if (this.getGroupId() != 0) {
                L1MobGroupSpawn.getInstance().doSpawn(npcTemp, this.getGroupId(), this.isRespawnScreen(), this._initSpawn);
            }
            npcTemp.turnOnOffLight();
            npcTemp.startChat(0);
            if (this.isBroadcast() && this.getBroadcastInfo() != null && !this.getBroadcastInfo().isEmpty()) {
                World.get().broadcastPacketToAll(new S_SystemMessage(String.format(this.getBroadcastInfo(), npcTemp.getName())));
            }
            this._tmplocx = newlocx;
            this._tmplocy = newlocy;
            this._tmpmapid = npcTemp.getMapId();
            boolean setPassable = true;
            if (npcTemp instanceof L1DollInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1HierarchInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1EffectInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1FieldObjectInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1FurnitureInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1DoorInstance) {
                setPassable = false;
            }
            if (npcTemp instanceof L1BowInstance) {
                setPassable = false;
            }
            if (setPassable) {
                L1WorldMap.get().getMap(npcTemp.getMapId()).setPassable(npcTemp.getX(), npcTemp.getY(), false, 2);
            }
        }
        catch (Exception e) {
            L1Spawn._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void setRest(final boolean flag) {
        this._rest = flag;
    }
    
    public boolean isRest() {
        return this._rest;
    }
    
    private int getSpawnType() {
        return this._spawnType;
    }
    
    public void setSpawnType(final int type) {
        this._spawnType = type;
    }
    
    private boolean isAreaSpawn() {
        return this.getLocX1() != 0 && this.getLocY1() != 0 && this.getLocX2() != 0 && this.getLocY2() != 0;
    }
    
    private boolean isRandomSpawn() {
        return this.getRandomx() != 0 || this.getRandomy() != 0;
    }
    
    public L1SpawnTime getTime() {
        return this._time;
    }
    
    public void setTime(final L1SpawnTime time) {
        this._time = time;
    }
    
    @Override
    public void onMinuteChanged(final L1GameTime time) {
        if (this._time.getTimePeriod().includes(time)) {
            return;
        }
        synchronized (this._mobs) {
            if (this._mobs.isEmpty()) {
                // monitorexit(this._mobs)
                return;
            }
            for (final L1NpcInstance mob : this._mobs) {
                mob.setCurrentHpDirect(0);
                mob.setDead(true);
                mob.setStatus(8);
                mob.deleteMe();
            }
            this._mobs.clear();
        }
        // monitorexit(this._mobs)
    }
    
    public static void doCrystalCave(final int npcId) {
        final int[] npcId2 = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
        final int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };
        for (int i = 0; i < npcId2.length; ++i) {
            if (npcId == npcId2[i]) {
                closeDoorInCrystalCave(doorId[i]);
            }
        }
    }
    
    public final L1NpcInstance getNpcTemp() {
        return this.npcTemp;
    }
    
    public final long getDeleteTime() {
        return this.deleteTime;
    }
    
    public final void setDeleteTime(final long deleteTime) {
        this.deleteTime = deleteTime;
    }
    
    private static void closeDoorInCrystalCave(final int doorId) {
        for (final L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                final L1DoorInstance door = (L1DoorInstance)object;
                if (door.getDoorId() != doorId) {
                    continue;
                }
                door.close();
            }
        }
    }
    
    public final boolean isBroadcast() {
        return this._isBroadcast;
    }
    
    public final void setBroadcast(final boolean isBroadcast) {
        this._isBroadcast = isBroadcast;
    }
    
    public final String getBroadcastInfo() {
        return this._broadcastInfo;
    }
    
    public final void setBroadcastInfo(final String broadcastInfo) {
        this._broadcastInfo = broadcastInfo;
    }
    
    public final String getmsg1() {
        return this._msg1;
    }
    
    public final void setmsg1(final String msg1) {
        this._msg1 = msg1;
    }
    
    public final String getmsg2() {
        return this._msg2;
    }
    
    public final void setmsg2(final String msg2) {
        this._msg2 = msg2;
    }
    
    private class SpawnTask implements Runnable
    {
        private int _spawnNumber;
        private int _objectId;
        private long _delay;
        
        private SpawnTask(final int spawnNumber, final int objectId, final long delay) {
            this._spawnNumber = spawnNumber;
            this._objectId = objectId;
            this._delay = delay;
        }
        
        public void getStart() {
//            GeneralThreadPool.get().schedule(this, this._delay);
            NpcBownTheadPool.get().schedule(this, _delay);
        }
        
        @Override
        public void run() {
            L1Spawn.this.doSpawn(this._spawnNumber, this._objectId);
        }
    }
}
