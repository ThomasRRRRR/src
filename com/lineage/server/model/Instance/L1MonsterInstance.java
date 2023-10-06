// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model.Instance;

import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.server.world.WorldMob;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import java.util.TimerTask;
import com.lineage.server.templates.L1QuestUser;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.L1UltimateBattle;
import com.lineage.server.datatables.UBTable;
import com.lineage.config.ConfigRate;
import com.lineage.server.model.drop.DropShareExecutor;
import com.lineage.server.model.drop.DropShare;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import com.lineage.server.model.L1Clan;
import com.lineage.server.datatables.MonsterEnhanceTable;
import com.lineage.data.event.Npc_Dead;
import com.lineage.server.datatables.ServerQuestMaPTable;
import java.sql.Timestamp;
import com.lineage.server.datatables.sql.L1MonTable;
import com.lineage.server.templates.L1Mon;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.config.ConfigClan;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.datatables.sql.ClanTable;
import com.lineage.server.world.WorldClan;
import com.lineage.server.datatables.NpcClanContribution;
import com.lineage.data.event.ClanContribution;
import com.lineage.server.model.pr_type_name;
import com.lineage.server.datatables.NpcPrestigeTable;
import com.lineage.data.event.prestigtable;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.C1_Name_Type_Table;
import com.lineage.data.event.CampSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.NpcScoreTable;
import java.util.ArrayList;
import com.lineage.server.utils.CalcExp;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.data.event.ProtectorSet;
import com.lineage.william.Npc_Dead_span;
import com.lineage.data.event.Npe_DeadSpan;
//import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.NpcDeathTheadPool;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.model.drop.SetDropExecutor;
import com.lineage.server.model.drop.SetDrop;
import com.lineage.server.templates.L1Npc;
import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.NpcTable;
import com.lineage.config.ConfigGuardTower;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_NPCPack;
import java.util.Iterator;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import java.util.Random;
import org.apache.commons.logging.Log;

public class L1MonsterInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static final Random _random;
    private boolean _storeDroped;
    private int _ubSealCount;
    private int _ubId;
    private long _lasthprtime;
    private long _lastmprtime;
    
    static {
        _log = LogFactory.getLog((Class)L1MonsterInstance.class);
        _random = new Random();
    }
    
    @Override
    public void onItemUse() {
        if (!this.isActived() && this._target != null && this.getNpcTemplate().is_doppel() && this._target instanceof L1PcInstance) {
            final L1PcInstance targetPc = (L1PcInstance)this._target;
            this.setName(this._target.getName());
            this.setNameId(this._target.getName());
            this.setTitle(this._target.getTitle());
            this.setTempLawful(this._target.getLawful());
            this.setTempCharGfx(targetPc.getClassId());
            this.setGfxId(targetPc.getClassId());
            this.setPassispeed(640);
            this.setAtkspeed(900);
            for (final L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                pc.sendPackets(new S_RemoveObject(this));
                pc.removeKnownObject(this);
                pc.updateObject();
            }
        }
        if (this.getCurrentHp() * 100 / this.getMaxHp() < 40) {
            this.useItem(1, 50);
        }
    }
    
    @Override
    public void onPerceive(final L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            perceivedFrom.addKnownObject(this);
            if (this.getCurrentHp() > 0) {
                perceivedFrom.sendPackets(new S_NPCPack(this, perceivedFrom));
                this.onNpcAI();
                if (this.getBraveSpeed() == 1) {
                    perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), 1, 600000));
                }
            }
            else {
                perceivedFrom.sendPackets(new S_NPCPack(this));
            }
        }
        catch (Exception e) {
            L1MonsterInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    @Override
    public void searchTarget() {
        final L1NpcInstance targetNpc = this.searchTargetnpc(this);
        final L1PcInstance targetPlayer = this.searchTarget(this);
        if (targetNpc != null) {
            this._hateList.add(targetNpc, 0);
            this._target = targetNpc;
        }
        else if (targetPlayer != null) {
            this._hateList.add(targetPlayer, 0);
            this._target = targetPlayer;
        }
        else {
            this.ISASCAPE = false;
        }
    }
    
    private L1MonsterInstance searchTargetnpc(final L1MonsterInstance npc) {
        L1MonsterInstance targetNpc = null;
        if (this.getMapId() == 93) {
            final Point npcpt = this.getLocation();
            for (final L1Object object : World.get().getVisibleObjects(this, -1)) {
                if (object instanceof L1MonsterInstance) {
                    final L1MonsterInstance tgnpc = (L1MonsterInstance)object;
                    if (this.getMapId() != tgnpc.getMapId()) {
                        continue;
                    }
                    if (!npcpt.isInScreen(tgnpc.getLocation())) {
                        continue;
                    }
                    if (tgnpc == null || tgnpc.getCurrentHp() <= 0 || tgnpc.isDead() || this.getId() == tgnpc.getId()) {
                        continue;
                    }
                    if (this.get_showId() != tgnpc.get_showId()) {
                        continue;
                    }
                    targetNpc = tgnpc;
                }
            }
        }
        if (this.getMapId() == ConfigGuardTower.MAPID) {
            for (final L1Object object2 : World.get().getVisibleObjects(npc, 50)) {
                if (object2 instanceof L1MonsterInstance) {
                    final L1MonsterInstance tgnpc2 = (L1MonsterInstance)object2;
                    if (tgnpc2.getNpcId() != ConfigGuardTower.TOWERID) {
                        continue;
                    }
                    targetNpc = tgnpc2;
                }
            }
        }
        return targetNpc;
    }
    
    private L1PcInstance searchTarget(final L1MonsterInstance npc) {
        L1PcInstance targetPlayer = null;
        for (final L1PcInstance pc : World.get().getVisiblePlayer(npc)) {
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException e) {
                L1MonsterInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            }
            if (pc.getCurrentHp() <= 0) {
                continue;
            }
            if (pc.isDead()) {
                continue;
            }
            if (pc.isGhost()) {
                continue;
            }
            if (pc.isGm()) {
                continue;
            }
            if (npc.getNpcTemplate().is_Atkpc()) {
                continue;
            }
            if (npc.get_showId() != pc.get_showId()) {
                continue;
            }
            if (npc.getMapId() == 410 && pc.getTempCharGfx() == 4261) {
                continue;
            }
            if (npc.getNpcTemplate().get_family() == NpcTable.ORC && pc.getClan() != null && pc.getClan().getCastleId() == 2) {
                continue;
            }
            final L1PcInstance tgpc1 = npc.attackPc1(pc);
            if (tgpc1 != null) {
                targetPlayer = tgpc1;
                return targetPlayer;
            }
            final L1PcInstance tgpc2 = npc.attackPc2(pc);
            if (tgpc2 != null) {
                targetPlayer = tgpc2;
                return targetPlayer;
            }
            if (npc.getNpcTemplate().getKarma() < 0 && pc.getKarmaLevel() >= 1) {
                continue;
            }
            if (npc.getNpcTemplate().getKarma() > 0 && pc.getKarmaLevel() <= -1) {
                continue;
            }
            if (pc.getTempCharGfx() == 6034 && npc.getNpcTemplate().getKarma() < 0) {
                continue;
            }
            if (pc.getTempCharGfx() == 6035) {
                if (npc.getNpcTemplate().getKarma() > 0) {
                    continue;
                }
                if (npc.getNpcTemplate().get_npcId() == 46070) {
                    continue;
                }
                if (npc.getNpcTemplate().get_npcId() == 46072) {
                    continue;
                }
            }
            if (npc.getNpcTemplate().is_Atkpc_red() && npc.targetPlayer1(pc) != null) {
                targetPlayer = npc.targetPlayer1(pc);
                return targetPlayer;
            }
            final L1PcInstance tgpc3 = npc.targetPlayer1000(pc);
            if (tgpc3 != null) {
                targetPlayer = tgpc3;
                return targetPlayer;
            }
            if (pc.isInvisble() && !this.getNpcTemplate().is_agrocoi()) {
                continue;
            }
            if (pc.hasSkillEffect(67)) {
                if (this.getNpcTemplate().is_agrososc()) {
                    targetPlayer = pc;
                    break;
                }
            }
            else if (this.getNpcTemplate().is_agro()) {
                targetPlayer = pc;
                break;
            }
            if (npc.getNpcTemplate().is_agrogfxid1() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid1()) {
                targetPlayer = pc;
                return targetPlayer;
            }
            if (npc.getNpcTemplate().is_agrogfxid2() >= 0 && pc.getGfxId() == npc.getNpcTemplate().is_agrogfxid2()) {
                targetPlayer = pc;
                return targetPlayer;
            }
        }
        return targetPlayer;
    }
    
    private L1PcInstance attackPc2(final L1PcInstance pc) {
        if (this.getNpcId() == 45600) {
            if (pc.isCrown() && pc.getTempCharGfx() == pc.getClassId()) {
                return pc;
            }
            if (pc.isDarkelf()) {
                return pc;
            }
        }
        return null;
    }
    
    private L1PcInstance attackPc1(final L1PcInstance pc) {
        final int mapId = this.getMapId();
        boolean isCheck = false;
        if (mapId == 88) {
            isCheck = true;
        }
        if (mapId == 98) {
            isCheck = true;
        }
        if (mapId == 92) {
            isCheck = true;
        }
        if (mapId == 91) {
            isCheck = true;
        }
        if (mapId == 95) {
            isCheck = true;
        }
        if (isCheck && (!pc.isInvisble() || this.getNpcTemplate().is_agrocoi())) {
            return pc;
        }
        return null;
    }
    
    private L1PcInstance targetPlayer1000(final L1PcInstance pc) {
        if (ConfigOther.KILLRED && !this.getNpcTemplate().is_agro() && !this.getNpcTemplate().is_agrososc() && this.getNpcTemplate().is_agrogfxid1() < 0 && this.getNpcTemplate().is_agrogfxid2() < 0 && pc.getLawful() < -1000) {
            return pc;
        }
        return null;
    }
    
    private L1PcInstance targetPlayer1(final L1PcInstance pc) {
        if (pc.getLawful() < -1) {
            return pc;
        }
        return null;
    }
    
    @Override
    public void setLink(final L1Character cha) {
        if (this.get_showId() != cha.get_showId()) {
            return;
        }
        if (cha != null && this._hateList.isEmpty()) {
            this._hateList.add(cha, 0);
            this.checkTarget();
        }
    }
    
    public L1MonsterInstance(final L1Npc template) {
        super(template);
        this._ubSealCount = 0;
        this._ubId = 0;
        this._lasthprtime = 0L;
        this._lastmprtime = 0L;
        this._storeDroped = false;
    }
    
    @Override
    public void onNpcAI() {
        if (this.isAiRunning()) {
            return;
        }
        if (!this._storeDroped) {
            final SetDropExecutor setdrop = new SetDrop();
            setdrop.setDrop(this, this.getInventory());
            this.getInventory().shuffle();
            this._storeDroped = true;
        }
        this.setActived(false);
        this.startAI();
    }
    
    @Override
    public void onTalkAction(final L1PcInstance pc) {
        this.setHeading(this.targetDirection(pc.getX(), pc.getY()));
        this.broadcastPacketAll(new S_ChangeHeading(this));
        this.set_stop_time(10);
        this.setRest(true);
    }
    
    @Override
    public void onAction(final L1PcInstance pc) {
        if (this.ATTACK != null) {
            this.ATTACK.attack(pc, this);
        }
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            final L1AttackMode attack = new L1AttackPc(pc, this);
            if (attack.calcHit()) {
                attack.calcDamage();
                attack.calcStaffOfMana();
            }
            attack.action();
            attack.commit();
        }
    }
    
    @Override
    public void ReceiveManaDamage(final L1Character attacker, final int mpDamage) {
        if (mpDamage > 0 && !this.isDead()) {
            this.setHate(attacker, mpDamage);
            this.onNpcAI();
            if (attacker instanceof L1PcInstance) {
                this.serchLink((L1PcInstance)attacker, this.getNpcTemplate().get_family());
            }
            int newMp = this.getCurrentMp() - mpDamage;
            if (newMp < 0) {
                newMp = 0;
            }
            this.setCurrentMp(newMp);
        }
    }
    
    public void receiveDamage(final L1Character attacker, double damage, final int attr) {
        final int mrdef = this.getMr();
        final int rnd = L1MonsterInstance._random.nextInt(100) + 1;
        if (mrdef >= rnd) {
            damage /= 2.0;
        }
        int resist = 0;
        switch (attr) {
            case 1: {
                resist = this.getEarth();
                break;
            }
            case 2: {
                resist = this.getFire();
                break;
            }
            case 4: {
                resist = this.getWater();
                break;
            }
            case 8: {
                resist = this.getWind();
                break;
            }
        }
        int resistFloor = (int)(0.16 * Math.abs(resist));
        if (resist >= 0) {
            resistFloor *= 1;
        }
        else {
            resistFloor *= -1;
        }
        final double attrDeffence = resistFloor / 32.0;
        final double coefficient = 1.0 - attrDeffence;
        damage *= coefficient;
        this.receiveDamage(attacker, (int)damage);
    }
    
    @Override
    public void receiveDamage(L1Character attacker, int damage) {
        this.ISASCAPE = false;
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            if (this.getHiddenStatus() == 1 || this.getHiddenStatus() == 2) {
                return;
            }
            if (damage >= 0) {
                if (attacker instanceof L1EffectInstance) {
                    final L1EffectInstance effect = (L1EffectInstance)attacker;
                    attacker = effect.getMaster();
                    if (attacker != null) {
                        this.setHate(attacker, damage);
                    }
                }
                else if (attacker instanceof L1IllusoryInstance) {
                    final L1IllusoryInstance ill = (L1IllusoryInstance)attacker;
                    attacker = ill.getMaster();
                    if (attacker != null) {
                        this.setHate(attacker, damage);
                    }
                }
                else if (attacker instanceof L1MonsterInstance) {
                    switch (this.getNpcTemplate().get_npcId()) {
                        case 91290:
                        case 91294:
                        case 91295:
                        case 91296: {
                            this.setHate(attacker, damage);
                            damage = 0;
                            break;
                        }
                    }
                }
                else {
                    this.setHate(attacker, damage);
                }
            }
            if (damage > 0) {
                this.removeSkillEffect(66);
                this.removeSkillEffect(212);
            }
            this.onNpcAI();
            L1PcInstance atkpc = null;
            if (attacker instanceof L1PcInstance) {
                atkpc = (L1PcInstance)attacker;
                if (damage > 0) {
                    atkpc.setPetTarget(this);
                    switch (this.getNpcTemplate().get_npcId()) {
                        case 45681:
                        case 45682:
                        case 45683:
                        case 45684: {
                            this.recall(atkpc);
                            break;
                        }
                    }
                }
                this.serchLink(atkpc, this.getNpcTemplate().get_family());
            }
            if (this.hasSkillEffect(219)) {
                damage *= (int)1.05;
            }
            final int newHp = this.getCurrentHp() - damage;
            if (newHp <= 0 && !this.isDead()) {
                final int transformId = this.getNpcTemplate().getTransformId();
                if (transformId == -1) {
                    this.setCurrentHpDirect(0);
                    this.setDead(true);
                    this.setStatus(8);
                    openDoorWhenNpcDied(this);
                    final Death death = new Death(attacker);
//                    GeneralThreadPool.get().execute(death);
                    NpcDeathTheadPool.get().execute(death);
                    if (Npe_DeadSpan.START) {
                        Npc_Dead_span.forresolvent(atkpc, this);
                    }
                    if (atkpc != null && !this.isResurrect() && ProtectorSet.CHANCE > 0 && L1MonsterInstance._random.nextInt(1000) < ProtectorSet.CHANCE && CharItemsReading.get().checkItemId(ProtectorSet.ITEM_ID) < ProtectorSet.DROP_LIMIT) {
                        final L1ItemInstance item = ItemTable.get().createItem(ProtectorSet.ITEM_ID);
                        atkpc.getInventory().storeItem(item);
                        World.get().broadcastPacketToAll(new S_BlueMessage(166, "\\f=有人獲得了守護者的靈魂"));
                    }
                    if (this.getNpcId() >= 71014 && this.getNpcId() <= 71016) {
//                        GeneralThreadPool.get().execute(new deathDragonTimer1(this, this.getMapId()));
                        NpcDeathTheadPool.get().execute(new deathDragonTimer1(this, getMapId()));
                    }
                    else if (this.getNpcId() >= 71026 && this.getNpcId() <= 71028) {
//                        GeneralThreadPool.get().execute(new deathDragonTimer2(this, this.getMapId()));
                        NpcDeathTheadPool.get().execute(new deathDragonTimer2(this, getMapId()));
                    }
                    else if (this.getNpcId() >= 97204 && this.getNpcId() <= 97206) {
//                        GeneralThreadPool.get().execute(new deathDragonTimer3(this));
                        NpcDeathTheadPool.get().execute(new deathDragonTimer3(this));
                        
                    }
                }
                else {
                    this.transform(transformId);
                }
            }
            if (newHp > 0) {
                this.setCurrentHp(newHp);
                this.hide();
            }
            if (ConfigOther.HPBAR) {
                if (atkpc == null) {
                    if (attacker instanceof L1PetInstance) {
                        atkpc = (L1PcInstance)((L1PetInstance)attacker).getMaster();
                    }
                    else if (attacker instanceof L1SummonInstance) {
                        atkpc = (L1PcInstance)((L1SummonInstance)attacker).getMaster();
                    }
                    if (atkpc != null) {
                        this.broadcastPacketHP(atkpc);
                    }
                }
                else {
                    this.broadcastPacketHP(atkpc);
                }
            }
        }
        else if (!this.isDead()) {
            this.setDead(true);
            this.setStatus(8);
            final Death death2 = new Death(attacker);
//            GeneralThreadPool.get().execute(death2);
            NpcDeathTheadPool.get().execute(death2);
        }
    }
    
    private void recall(final L1PcInstance pc) {
        if (this.getMapId() != pc.getMapId()) {
            return;
        }
        if (this.getLocation().getTileLineDistance(pc.getLocation()) > 4) {
            for (int count = 0; count < 10; ++count) {
                final L1Location newLoc = this.getLocation().randomLocation(3, 4, false);
                if (this.glanceCheck(newLoc.getX(), newLoc.getY())) {
                    L1Teleport.teleport(pc, newLoc.getX(), newLoc.getY(), this.getMapId(), 5, true);
                    break;
                }
            }
        }
    }
    
    private static void openDoorWhenNpcDied(final L1NpcInstance npc) {
        final int[] npcId = { 46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152 };
        final int[] doorId = { 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010 };
        for (int i = 0; i < npcId.length; ++i) {
            if (npc.getNpcTemplate().get_npcId() == npcId[i]) {
                openDoorInCrystalCave(doorId[i]);
            }
        }
    }
    
    private static void openDoorInCrystalCave(final int doorId) {
        for (final L1Object object : World.get().getObject()) {
            if (object instanceof L1DoorInstance) {
                final L1DoorInstance door = (L1DoorInstance)object;
                if (door.getDoorId() != doorId) {
                    continue;
                }
                door.open();
            }
        }
    }
    
    @Override
    public void setCurrentHp(final int i) {
        final int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        this.setCurrentHpDirect(currentHp);
    }
    
    @Override
    public void setCurrentMp(final int i) {
        final int currentMp = Math.min(i, this.getMaxMp());
        if (this.getCurrentMp() == currentMp) {
            return;
        }
        this.setCurrentMpDirect(currentMp);
    }
    
    private void distributeExpDropKarma(final L1Character lastAttacker) {
        if (lastAttacker == null) {
            return;
        }
        L1PcInstance pc = null;
        if (this.DEATH != null) {
            pc = this.DEATH.death(lastAttacker, this);
        }
        else {
            pc = CheckUtil.checkAtkPc(lastAttacker);
        }
        if (pc != null) {
            final ArrayList<L1Character> targetList = this._hateList.toTargetArrayList();
            final ArrayList<Integer> hateList = this._hateList.toHateArrayList();
            final long exp = this.getExp();
            CalcExp.calcExp(pc, this.getId(), targetList, hateList, exp);
            if (pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                int score = NpcScoreTable.get().get_score(this.getNpcId());
                if (score > 0 && !this.isResurrect()) {
                    if (pc.getdouble_score() != 0) {
                        score *= pc.getdouble_score();
                    }
                    pc.get_other().add_score(score);
                    pc.sendPackets(new S_ServerMessage("\\fR獲得陣營積分:" + score));
                    if (CampSet.CAMPSTART && pc.get_c_power() != null && pc.get_c_power().get_c1_type() != 0) {
                        final int lv = C1_Name_Type_Table.get().getLv(pc.get_c_power().get_c1_type(), pc.get_other().get_score());
                        if (lv != pc.get_c_power().get_power().get_c1_id()) {
                            pc.get_c_power().set_power(pc, false);
                            pc.sendPackets(new S_ServerMessage("\\fR階級變更:" + pc.get_c_power().get_power().get_c1_name_type()));
                            pc.sendPacketsAll(new S_ChangeName(pc, true));
                        }
                    }
                }
            }
            if (prestigtable.START) {
                final int prestige = NpcPrestigeTable.get().get_score(this.getNpcId());
                if (prestige != 0) {
                    pc.addPrestige(prestige);
                    pc.sendPackets(new S_ServerMessage("狩獵獲得" + pr_type_name._1 + "增加:" + prestige, 11));
                }
            }
            if (ClanContribution.START && pc.getClanid() != 0) {
                final int ClanContribution = NpcClanContribution.get().get_score(this.getNpcId());
                final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (ClanContribution != 0 && pc.getQuest().get_step(8544) != 1) {
                    clan.setClanContribution(clan.getClanContribution() + ClanContribution);
                    ClanTable.getInstance().updateClan(clan);
                    pc.setPcContribution(pc.getPcContribution() + ClanContribution);
                    pc.setClanContribution(pc.getClanContribution() + ClanContribution);
                    pc.getInventory().storeItem(92164, ClanContribution);
                    pc.sendPackets(new S_ServerMessage("狩獵獲得血盟能量+:" + ClanContribution, 11));
                    pc.sendPackets(new S_SystemMessage("你給予目前的血盟貢獻度有【" + pc.getClanContribution() + "】點。"));
                    pc.sendPackets(new S_SystemMessage("你的血盟目前所獲得的貢獻度總合有【" + clan.getClanContribution() + "】點。"));
                    pc.sendPackets(new S_SystemMessage("獲得血盟貢獻幣:【" + ClanContribution + "】個。"));
                    if (pc.getPcContribution() >= ConfigClan.PcContribution) {
                        pc.sendPackets(new S_ServerMessage("\\fY每日血盟能量上限:" + ConfigClan.PcContribution));
                        pc.setPcContribution(ConfigClan.PcContribution);
                        pc.getQuest().set_step(8544, 1);
                        pc.setPcContribution(0);
                    }
                }
            }
            if (this.getNpcTemplate().get_npcbroad() == 1) {
                World.get().broadcastPacketToAll(new S_SystemMessage("\\fY玩家[:" + pc.getName() + "] 使用:[" + pc.getWeapon().getLogName() + "]\n\\fT☆擊殺:( " + this.getName() + " )☆"));
            }
            if (pc.getATK_ai()) {
                pc.setATK_ai(false);
                pc.setSkillEffect(7902, 2000);
            }
            if (QuestMobSet.START && !this.isResurrect()) {
                ServerQuestMobTable.get().checkQuestMob(pc, this.getNpcId());
            }
            final int Npc_Mid = this.getNpcTemplate().get_npcId();
            if (Npc_Mid == L1Mon.CheckNpcMid(Npc_Mid)) {
                final L1Mon mon = L1MonTable.get().getMon(Npc_Mid);
                if (mon.getX1() > 0) {
                    L1Teleport.teleport(pc, mon.getX1(), mon.getY1(), (short)mon.getMap1(), 5, true);
                }
                if (mon.getDorplostid() > 0) {
                    if (pc.getnpcdmg() > mon.getpcdmg()) {
                        final int itemid = mon.getDorplostid();
                        final L1ItemInstance item = ItemTable.get().createItem(itemid);
                        pc.getInventory().storeItem(item);
                        final String a = this.getNpcTemplate().get_name();
                        final String b = item.getName();
                        final String c = pc.getName();
                        World.get().broadcastPacketToAll(new S_SystemMessage("\\fU恭喜：[" + c + "],神尾刀【" + a + "】"));
                        World.get().broadcastPacketToAll(new S_SystemMessage("\\fU獲得獎勵【" + b + "】"));
                        bossbox("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "玩家" + ":【 " + pc.getName() + " 】 " + "(尾刀)擊敗" + "【 + " + this.getName() + "】時間:" + new Timestamp(System.currentTimeMillis()) + ")。");
                    }
                    else {
                        pc.sendPackets(new S_ServerMessage("\\fU由於您打擊傷害,未達標準。"));
                        pc.sendPackets(new S_ServerMessage("\\fU將不發放擊殺[尾刀]寶相"));
                    }
                }
            }
            if (Npc_Mid == L1Mon.CheckNpcMid(Npc_Mid)) {
                final L1Mon mon = L1MonTable.get().getMon(Npc_Mid);
                if (mon.getDorpid() > 0 || mon.getX() > 0) {
                    for (final L1Object tgobj : World.get().getVisibleObjects(this, 15)) {
                        if (tgobj instanceof L1PcInstance) {
                            final L1PcInstance tgpc = (L1PcInstance)tgobj;
                            if (mon.getX() > 0) {
                                L1Teleport.teleport(tgpc, mon.getX(), mon.getY(), (short)mon.getMap(), 5, true);
                            }
                            if (mon.getDorpid() <= 0) {
                                continue;
                            }
                            if (tgpc.getnpcdmg() > mon.getpcdmg()) {
                                final int itemid2 = mon.getDorpid();
                                final L1ItemInstance item2 = ItemTable.get().createItem(itemid2);
                                tgpc.getInventory().storeItem(item2);
                                final String b2 = item2.getName();
                                tgpc.sendPackets(new S_ServerMessage("\\fT本次傷害達標準,獲得獎勵【" + b2 + "】"));
                                if (tgpc.getnpcdmg() <= 0.0) {
                                    continue;
                                }
                                tgpc.setnpcdmg(0.0);
                                tgpc.setnpciddmg(0);
                            }
                            else {
                                tgpc.sendPackets(new S_ServerMessage("\\fU本次打擊傷害,未達標準。"));
                                tgpc.sendPackets(new S_ServerMessage("\\fU將不發放擊殺寶相"));
                                if (tgpc.getnpcdmg() <= 0.0) {
                                    continue;
                                }
                                tgpc.setnpcdmg(0.0);
                                tgpc.setnpciddmg(0);
                            }
                        }
                    }
                }
            }
            if (pc.getnpcdmg() > 0.0) {
                pc.setnpcdmg(0.0);
                pc.setnpciddmg(0);
            }
            if (pc.get_other3().get_type1() > 0 && !this.isResurrect()) {
                ServerQuestMaPTable.check(pc);
            }
            if (this.isDead()) {
                if (Npc_Dead.START && MonsterEnhanceTable.getInstance().getTemplate(this.getNpcId()) != null) {
                    final L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(this.getNpcId());
                    if (mei.getDcEnhance1() != 0 && mei.getCurrentDc() == mei.getDcEnhance1()) {
                        mei.setCurrentDc(0);
                        MonsterEnhanceTable.getInstance().save(mei);
                    }
                    if (mei.getDcEnhance() != 0) {
                        mei.setCurrentDc(mei.getCurrentDc() + 1);
                        MonsterEnhanceTable.getInstance().save(mei);
                        if (mei.getCurrentDc() % mei.getDcEnhance() == 0) {
                            World.get().broadcastPacketToAll(new S_SystemMessage(String.valueOf(String.valueOf(this.getName())) + "含著淚說：當下次你在見到我，必定讓你吃驚！"));
                        }
                    }
                }
                this.distributeDrop();
                this.giveKarma(pc);
                if (pc.getAu_AutoSet(0) == 0 && pc.getAutoX() == 0 && pc.getAutoY() == 0 && pc.isActived() && pc.getMap().isTeleportable()) {
                    pc.setSkillEffect(8853, 60000);
                }
            }
        }
    }
    
    public static void bossbox(final String info) {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/尾刀王紀錄.txt", true));
            out.write(String.valueOf(String.valueOf(info)) + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void distributeDrop() {
        final ArrayList<L1Character> dropTargetList = this._dropHateList.toTargetArrayList();
        final ArrayList<Integer> dropHateList = this._dropHateList.toHateArrayList();
        try {
            final DropShareExecutor dropShareExecutor = new DropShare();
            dropShareExecutor.dropShare(this, dropTargetList, dropHateList);
        }
        catch (Exception e) {
            L1MonsterInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void giveKarma(final L1PcInstance pc) {
        int karma = this.getKarma();
        if (karma != 0) {
            final int karmaSign = Integer.signum(karma);
            final int pcKarmaLevel = pc.getKarmaLevel();
            final int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
            if (pcKarmaLevelSign != 0 && karmaSign != pcKarmaLevelSign) {
                karma *= 5;
            }
            pc.addKarma((int)(karma * ConfigRate.RATE_KARMA));
        }
    }
    
    private void giveUbSeal() {
        if (this.getUbSealCount() != 0) {
            final L1UltimateBattle ub = UBTable.getInstance().getUb(this.getUbId());
            if (ub != null) {
                L1PcInstance[] membersArray;
                for (int length = (membersArray = ub.getMembersArray()).length, i = 0; i < length; ++i) {
                    final L1PcInstance pc = membersArray[i];
                    if (pc != null && !pc.isDead() && !pc.isGhost()) {
                        final L1ItemInstance item = pc.getInventory().storeItem(41402, this.getUbSealCount());
                        pc.sendPackets(new S_ServerMessage(403, item.getLogName()));
                    }
                }
            }
        }
    }
    
    public boolean is_storeDroped() {
        return this._storeDroped;
    }
    
    public void set_storeDroped(final boolean flag) {
        this._storeDroped = flag;
    }
    
    public int getUbSealCount() {
        return this._ubSealCount;
    }
    
    public void setUbSealCount(final int i) {
        this._ubSealCount = i;
    }
    
    public int getUbId() {
        return this._ubId;
    }
    
    public void setUbId(final int i) {
        this._ubId = i;
    }
    
    private void hide() {
        final int npcid = this.getNpcTemplate().get_npcId();
        switch (npcid) {
            case 45061:
            case 45161:
            case 45181:
            case 45455: {
                if (this.getMaxHp() / 3 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(10);
                if (rnd < 1) {
                    this.allTargetClear();
                    this.setHiddenStatus(1);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                    this.setStatus(13);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
            case 45682: {
                if (this.getMaxHp() / 3 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(100);
                if (rnd < 1) {
                    this.allTargetClear();
                    this.setHiddenStatus(1);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 20));
                    this.setStatus(20);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
            case 97259: {
                if (this.getMaxHp() / 3 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(100);
                if (rnd < 1) {
                    this.allTargetClear();
                    this.setHiddenStatus(1);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                    this.setStatus(11);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
            case 45067:
            case 45090:
            case 45264:
            case 45321:
            case 45445:
            case 45452: {
                if (this.getMaxHp() / 3 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(10);
                if (rnd < 1) {
                    this.allTargetClear();
                    this.setHiddenStatus(2);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 44));
                    this.setStatus(4);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
            case 46107:
            case 46108: {
                if (this.getMaxHp() / 4 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(10);
                if (rnd < 1) {
                    this.allTargetClear();
                    this.setHiddenStatus(1);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 11));
                    this.setStatus(13);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
            case 105078: {
                if (this.getMaxHp() / 3 <= this.getCurrentHp()) {
                    break;
                }
                final int rnd = L1MonsterInstance._random.nextInt(100);
                if (rnd < 2) {
                    this.allTargetClear();
                    this.setHiddenStatus(1);
                    this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 4));
                    this.setStatus(4);
                    this.broadcastPacketAll(new S_NPCPack(this));
                    break;
                }
                break;
            }
        }
    }
    
    public void initHide() {
        final int npcid = this.getNpcTemplate().get_npcId();
        final int rnd = L1MonsterInstance._random.nextInt(3);
        switch (npcid) {
            case 45061:
            case 45161:
            case 45181:
            case 45455: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                break;
            }
            case 45045:
            case 45126:
            case 45134:
            case 45281: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(4);
                    break;
                }
                break;
            }
            case 45067:
            case 45090:
            case 45264:
            case 45321:
            case 45445:
            case 45452: {
                this.setHiddenStatus(2);
                this.setStatus(4);
                break;
            }
            case 45681: {
                this.setHiddenStatus(2);
                this.setStatus(11);
                break;
            }
            case 46107:
            case 46108: {
                if (1 > rnd) {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                break;
            }
            case 46125:
            case 46126:
            case 46127:
            case 46128: {
                this.setHiddenStatus(3);
                this.setStatus(4);
                break;
            }
            case 97259: {
                this.setHiddenStatus(1);
                this.setStatus(11);
                break;
            }
        }
    }
    
    public void initHideForMinion(final L1NpcInstance leader) {
        final int npcid = this.getNpcTemplate().get_npcId();
        if (leader.getHiddenStatus() == 1) {
            switch (npcid) {
                case 45061:
                case 45161:
                case 45181:
                case 45455: {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
                case 45045:
                case 45126:
                case 45134:
                case 45281: {
                    this.setHiddenStatus(1);
                    this.setStatus(4);
                    break;
                }
                case 46107:
                case 46108: {
                    this.setHiddenStatus(1);
                    this.setStatus(13);
                    break;
                }
            }
        }
        else if (leader.getHiddenStatus() == 2) {
            switch (npcid) {
                case 45067:
                case 45090:
                case 45264:
                case 45321:
                case 45445:
                case 45452: {
                    this.setHiddenStatus(2);
                    this.setStatus(4);
                    break;
                }
                case 45681: {
                    this.setHiddenStatus(2);
                    this.setStatus(11);
                    break;
                }
                case 46125:
                case 46126:
                case 46127:
                case 46128: {
                    this.setHiddenStatus(3);
                    this.setStatus(4);
                    break;
                }
            }
        }
    }
    
    @Override
    public void transform(final int transformId) {
        super.transform(transformId);
        this.getInventory().clearItems();
        final SetDropExecutor setDropExecutor = new SetDrop();
        setDropExecutor.setDrop(this, this.getInventory());
        this.getInventory().shuffle();
    }
    
    private final void sendServerMessage(final int msgid) {
        final L1QuestUser quest = WorldQuest.get().get(this.get_showId());
        if (quest != null && !quest.pcList().isEmpty()) {
            for (final L1PcInstance pc : quest.pcList()) {
                pc.sendPackets(new S_ServerMessage(msgid));
            }
        }
    }
    
    public long getLastHprTime() {
        if (this._lasthprtime == 0L) {
            return this._lasthprtime = System.currentTimeMillis() / 1000L - 5L;
        }
        return this._lasthprtime;
    }
    
    public void setLastHprTime(final long time) {
        this._lasthprtime = time;
    }
    
    public long getLastMprTime() {
        if (this._lastmprtime == 0L) {
            return this._lastmprtime = System.currentTimeMillis() / 1000L - 5L;
        }
        return this._lastmprtime;
    }
    
    public void setLastMprTime(final long time) {
        this._lastmprtime = time;
    }
    
    private final class CountDownTimer extends TimerTask
    {
        private final int _loc_x;
        private final int _loc_y;
        private final short _loc_mapId;
        private final L1QuestUser _quest;
        private final int _firstMsgId;
        
        public CountDownTimer(final int loc_x, final int loc_y, final short loc_mapId, final L1QuestUser quest, final int firstMsgId) {
            this._loc_x = loc_x;
            this._loc_y = loc_y;
            this._loc_mapId = loc_mapId;
            this._quest = quest;
            this._firstMsgId = firstMsgId;
        }
        
        @Override
        public void run() {
            try {
                if (this._firstMsgId != 0) {
                    L1MonsterInstance.this.sendServerMessage(this._firstMsgId);
                }
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1476);
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1477);
                Thread.sleep(10000L);
                L1MonsterInstance.this.sendServerMessage(1478);
                Thread.sleep(5000L);
                L1MonsterInstance.this.sendServerMessage(1480);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1481);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1482);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1483);
                Thread.sleep(1000L);
                L1MonsterInstance.this.sendServerMessage(1484);
                Thread.sleep(1000L);
                for (int i = 10; i > 0; --i) {
                    if (this._quest != null && !this._quest.pcList().isEmpty()) {
                        for (final L1PcInstance pc : this._quest.pcList()) {
                            L1Teleport.teleport(pc, this._loc_x, this._loc_y, this._loc_mapId, pc.getHeading(), true);
                        }
                    }
                    Thread.sleep(500L);
                }
            }
            catch (Exception ex) {}
        }
    }
    
    class Death implements Runnable
    {
        L1Character _lastAttacker;
        
        public Death(final L1Character lastAttacker) {
            this._lastAttacker = lastAttacker;
        }
        
        @Override
        public void run() {
            final L1MonsterInstance mob = L1MonsterInstance.this;
            if (this._lastAttacker instanceof L1PcInstance) {
                final L1PcInstance pc = (L1PcInstance)this._lastAttacker;
                pc.setKillCount(pc.getKillCount() + 1);
                pc.sendPackets(new S_OwnCharStatus(pc));
                if (mob.getUbId() != 0) {
                    final int ubexp = (int)(L1MonsterInstance.this.getExp() / 10L);
                    pc.setUbScore(pc.getUbScore() + ubexp);
                }
            }
            mob.setDeathProcessing(true);
            mob.setCurrentHpDirect(0);
            mob.setDead(true);
            mob.setStatus(8);
            mob.broadcastPacketAll(new S_DoActionGFX(mob.getId(), 8));
            mob.getMap().setPassable(mob.getLocation(), true);
            mob.startChat(1);
            mob.distributeExpDropKarma(this._lastAttacker);
            mob.giveUbSeal();
            mob.setDeathProcessing(false);
            mob.setExp(0L);
            mob.setKarma(0);
            mob.allTargetClear();
            switch (mob.getNpcId()) {
                case 46123:
                case 46124: {
                    if (L1MonsterInstance.this.getMapId() == 782 && WorldMob.get().getCount(L1MonsterInstance.this.getMapId(), 46124) == 0 && WorldMob.get().getCount(L1MonsterInstance.this.getMapId(), 46123) == 0) {
                        World.get().broadcastPacketToAll(new S_ServerMessage(1470));
                        ServerCrockTable.get().updateKillTime();
                        break;
                    }
                    break;
                }
                case 92000:
                case 92001: {
                    if (L1MonsterInstance.this.getMapId() == 784 && WorldMob.get().getCount(L1MonsterInstance.this.getMapId(), 92000) == 0 && WorldMob.get().getCount(L1MonsterInstance.this.getMapId(), 92001) == 0) {
                        World.get().broadcastPacketToAll(new S_ServerMessage(1470));
                        ServerCrockTable.get().updateKillTime();
                        break;
                    }
                    break;
                }
            }
            int deltime = 0;
            switch (mob.getNpcId()) {
                case 71016:
                case 71028:
                case 97206: {
                    deltime = 60;
                    break;
                }
                default: {
                    deltime = ConfigAlt.NPC_DELETION_TIME;
                    break;
                }
            }
            mob.startDeleteTimer(deltime);
        }
    }
    
    private class deathDragonTimer1 extends TimerTask
    {
        private final L1MonsterInstance npc;
        private final short mapId;
        
        public deathDragonTimer1(final L1MonsterInstance paramShort, final short arg3) {
            this.npc = paramShort;
            this.mapId = arg3;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 71014) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1573);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1574);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1575);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1576);
                    Thread.sleep(10000L);
                    final int i = 32776 + L1MonsterInstance._random.nextInt(20);
                    final int k = 32679 + L1MonsterInstance._random.nextInt(20);
                    Thread.sleep(5000L);
                    final L1Location loc = new L1Location(i, k, this.mapId);
                    L1SpawnUtil.spawn(71015, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71015) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1577);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1578);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1579);
                    Thread.sleep(10000L);
                    final int j = 32776 + L1MonsterInstance._random.nextInt(20);
                    final int m = 32679 + L1MonsterInstance._random.nextInt(20);
                    Thread.sleep(5000L);
                    final L1Location loc = new L1Location(j, m, this.mapId);
                    L1SpawnUtil.spawn(71016, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71016) {
                    for (final L1PcInstance pc : World.get().getVisiblePlayer(this.npc)) {
                        pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7854));
                        final SkillMode mode = L1SkillMode.get().getSkill(6797);
                        if (mode != null) {
                            mode.start(pc, null, null, 86400);
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1580);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1581);
                    Thread.sleep(10000L);
//                    GeneralThreadPool.get().execute(new CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                    NpcDeathTheadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short) 4, WorldQuest.get().get(get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private class deathDragonTimer2 extends TimerTask
    {
        private final L1MonsterInstance npc;
        private final short mapId;
        
        public deathDragonTimer2(final L1MonsterInstance paramShort, final short arg3) {
            this.npc = paramShort;
            this.mapId = arg3;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 71026) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1661);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1662);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1663);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1664);
                    Thread.sleep(10000L);
                    final int j = 32948 + L1MonsterInstance._random.nextInt(20);
                    final int m = 32825 + L1MonsterInstance._random.nextInt(20);
                    final L1Location loc = new L1Location(j, m, this.mapId);
                    L1SpawnUtil.spawn(71027, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71027) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1665);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1666);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1667);
                    Thread.sleep(10000L);
                    final int k = 32948 + L1MonsterInstance._random.nextInt(20);
                    final int n = 32825 + L1MonsterInstance._random.nextInt(20);
                    final L1Location loc = new L1Location(k, n, this.mapId);
                    L1SpawnUtil.spawn(71028, loc, new Random().nextInt(8), L1MonsterInstance.this.get_showId());
                }
                else if (this.npc.getNpcId() == 71028) {
                    final ArrayList<L1Character> targetList = this.npc.getHateList().toTargetArrayList();
                    if (!targetList.isEmpty()) {
                        for (final L1Character cha : targetList) {
                            if (cha instanceof L1PcInstance) {
                                final L1PcInstance pc = (L1PcInstance)cha;
                                pc.sendPacketsX8(new S_SkillSound(pc.getId(), 7854));
                                final SkillMode mode = L1SkillMode.get().getSkill(6798);
                                if (mode == null) {
                                    continue;
                                }
                                mode.start(pc, null, null, 86400);
                            }
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1668);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1669);
                    Thread.sleep(10000L);
//                    GeneralThreadPool.get().execute(new CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                    NpcDeathTheadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short) 4, WorldQuest.get().get(get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }
    
    private class deathDragonTimer3 extends TimerTask
    {
        private final L1MonsterInstance npc;
        
        public deathDragonTimer3(final L1MonsterInstance npc) {
            this.npc = npc;
        }
        
        @Override
        public void run() {
            try {
                if (this.npc.getNpcId() == 97204) {
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1759);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1760);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1761);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1762);
                    Thread.sleep(10000L);
                    final L1Location loc = new L1Location(32846, 32878, this.npc.getMapId()).randomLocation(10, true);
                    L1SpawnUtil.spawn(97205, loc, 0, this.npc.get_showId());
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1763);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1764);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1765);
                    Thread.sleep(10000L);
                    L1MonsterInstance.this.sendServerMessage(1766);
                    Thread.sleep(10000L);
                    Thread.sleep(5000L);
                    final L1Location loc2 = new L1Location(32846, 32877, this.npc.getMapId()).randomLocation(10, true);
                    L1SpawnUtil.spawn(97206, loc2, 0, this.npc.get_showId());
                }
                else if (this.npc.getNpcId() == 97206) {
                    for (final L1PcInstance pc : World.get().getVisiblePlayer(this.npc)) {
                        pc.broadcastPacketX10(new S_SkillSound(pc.getId(), 7854));
                        final SkillMode mode = L1SkillMode.get().getSkill(6799);
                        if (mode != null) {
                            mode.start(pc, null, null, 86400);
                        }
                    }
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1772);
                    Thread.sleep(5000L);
                    L1MonsterInstance.this.sendServerMessage(1773);
                    Thread.sleep(10000L);
//                    GeneralThreadPool.get().execute(new CountDownTimer(33718, 32506, (short)4, WorldQuest.get().get(L1MonsterInstance.this.get_showId()), 0));
                    NpcDeathTheadPool.get().execute(new L1MonsterInstance.CountDownTimer(33718, 32506, (short) 4, WorldQuest.get().get(get_showId()), 0));
                }
            }
            catch (Exception ex) {}
        }
    }
}
