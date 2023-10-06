// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.model.Instance;

import com.lineage.server.templates.L1Skills;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Magic;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_NPCPack_Doll;
import com.lineage.server.world.WorldItem;

import java.util.Iterator;

import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_UseAttackSkill;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.doll.Doll_Skill;
import com.lineage.server.model.doll.L1DollExecutor;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.L1Character;
import com.lineage.server.IdFactoryNpc;
import com.lineage.server.templates.L1Npc;

import org.apache.commons.logging.LogFactory;

import com.lineage.server.templates.L1Doll;
import com.lineage.server.utils.L1SpawnUtil;

import java.util.Random;

import org.apache.commons.logging.Log;

public class L1DollInstance extends L1NpcInstance
{
    private static final long serialVersionUID = 1L;
    private static final Log _log;
    private static Random _random;
    private int _itemObjId;
    private boolean _power_doll;
    private L1Doll _type;
    private int _time;
    private int _skillid;
    private int _r;
    private int _olX;
    private int _olY;
    private L1ItemInstance _item;
    
    static {
        _log = LogFactory.getLog((Class)L1DollInstance.class);
        L1DollInstance._random = new Random();
    }
    
    public L1DollInstance(final L1Npc template, final L1PcInstance master, final int itemObjId, final L1Doll type, final boolean power_doll) {
        super(template);
        this._power_doll = false;
        this._time = 0;
        this._skillid = -1;
        this._r = -1;
        this._olX = 0;
        this._olY = 0;
        this._item = null;
        try {
            this._power_doll = power_doll;
            this.setId(IdFactoryNpc.get().nextId());
            this.set_showId(master.get_showId());
            this.setItemObjId(itemObjId);
            this._type = type;
            this.setGfxId(type.get_gfxid());
            this.setTempCharGfx(type.get_gfxid());
            this.setNameId(type.get_nameid());
            this.set_time(type.get_time());
            this.setMaster(master);
            this.setX(master.getX() + L1DollInstance._random.nextInt(5) - 2);
            this.setY(master.getY() + L1DollInstance._random.nextInt(5) - 2);
            this.setMap(master.getMapId());
            this.setHeading(5);
            this.setLightSize(template.getLightSize());
            World.get().storeObject(this);
            World.get().addVisibleObject(this);
            master.setUsingDoll(this);
            master.addDoll(this);
            for (final L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                this.onPerceive(pc);
            }
            if (this._power_doll) {
                master.add_power_doll(this);
            }
            else {
                master.addDoll(this);
            }
            L1PcInstance masterpc = null;
            if (this._master instanceof L1PcInstance) {
                masterpc = (L1PcInstance)this._master;
                if (!this._type.getPowerList().isEmpty()) {
                	for (L1DollExecutor power : _type.getPowerList()) {
						if ((power instanceof Doll_Skill)) {
							Doll_Skill skill = (Doll_Skill) power;
							set_skill(skill.get_int()[0], skill.get_int()[1]);
						} else {
							power.setDoll(masterpc);
						}
						if (power.is_reset()) {// 是否具有輔助技能
							_power_doll = true;
						}
					}
                }
                masterpc.setNpcSpeed();// 設定娃娃跟隨速度
                master.sendPackets(new S_PacketBox(56, type.get_time()));
            }
            this.broadcastPacketAll(new S_SkillSound(this.getId(), 5935));
            this.set_olX(this.getX());
            this.set_olY(this.getY());
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public L1DollInstance(final L1Npc template, final L1NpcInstance master, final L1Doll type) {
        super(template);
        this._power_doll = false;
        this._time = 0;
        this._skillid = -1;
        this._r = -1;
        this._olX = 0;
        this._olY = 0;
        this._item = null;
        try {
            this.setId(IdFactoryNpc.get().nextId());
            this.set_showId(master.get_showId());
            this._type = type;
            this.setGfxId(type.get_gfxid());
            this.setTempCharGfx(type.get_gfxid());
            this.setNameId(type.get_nameid());
            this.set_time(type.get_time());
            this.set_time(1800);
            this.setMaster(master);
            this.setX(master.getX() + L1DollInstance._random.nextInt(5) - 2);
            this.setY(master.getY() + L1DollInstance._random.nextInt(5) - 2);
            this.setMap(master.getMapId());
            this.setHeading(5);
            this.setLightSize(template.getLightSize());
            World.get().storeObject(this);
            World.get().addVisibleObject(this);
            for (final L1PcInstance pc : World.get().getRecognizePlayer(this)) {
                this.onPerceive(pc);
            }
            master.addDoll(this);
            
            
            
            for (L1PcInstance pc : World.get().getRecognizePlayer(this)) {
				onPerceive(pc);// 發送接觸資訊
			}
            if ((_master instanceof L1PcInstance)) {
				L1PcInstance masterpc = (L1PcInstance) _master;
				if (!_type.getPowerList().isEmpty()) {
					for (L1DollExecutor power : _type.getPowerList()) {
						if ((power instanceof Doll_Skill)) {
							Doll_Skill skill = (Doll_Skill) power;
							set_skill(skill.get_int()[0], skill.get_int()[1]);
						} else {
							power.setDoll(masterpc);
						}
						if (power.is_reset()) {// 是否具有輔助技能
							_power_doll = true;
						}
					}
				}
				
				masterpc.setNpcSpeed();// 設定娃娃跟隨速度
				masterpc.sendPackets(new S_PacketBox(56, type.get_time()));//右上角圖示
			}
            
            
            
            
            
            this.broadcastPacketX10(new S_SkillSound(this.getId(), 5935));
            this.set_olX(this.getX());
            this.set_olY(this.getY());
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void deleteDoll() {
        try {
            final L1ItemInstance item = WorldItem.get().getItem(this._itemObjId);
            if (item != null) {
                item.stopEquipmentTimer((L1PcInstance)this._master);
            }
            this.broadcastPacketAll(new S_SkillSound(this.getId(), 5936));
            L1PcInstance masterpc = null;
            if (this._master instanceof L1PcInstance) {
                masterpc = (L1PcInstance)this._master;
                if (!this._type.getPowerList().isEmpty()) {
                    for (final L1DollExecutor p : this._type.getPowerList()) {
                        p.removeDoll(masterpc);
                    }
                }
                masterpc.sendPackets(new S_PacketBox(56, 0));
            }
            if (this._power_doll) {
                this._master.remove_power_doll();
                this._master.removeDoll(this);
                if (masterpc.getdollcount1() > 0) {
                    masterpc.add_dollcount1(-1);
                }
                masterpc.add_dollcount1_itemid(-1);
                masterpc.setopendoll(0);
            }
            else {
                this._master.removeDoll(this);
                if (masterpc.getdollcount() > 0) {
                    masterpc.add_dollcount(-1);
                }
                masterpc.add_dollcount_itemid(-1);
                masterpc.setopendoll(0);
            }
            this.deleteMe();
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    @Override
    public void onPerceive(final L1PcInstance perceivedFrom) {
        try {
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_NPCPack_Doll(this, perceivedFrom));
            if (this.getBraveSpeed() > 0) {
                perceivedFrom.sendPackets(new S_SkillBrave(this.getId(), this.getBraveSpeed(), 600000));
            }
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void setNpcMoveSpeed() {
        try {
            if (!ConfigOther.WAR_DOLL) {
                for (int castle_id = 1; castle_id < 8; ++castle_id) {
                    if (ServerWarExecutor.get().isNowWar(castle_id) && L1CastleLocation.checkInWarArea(castle_id, this)) {
                        this.deleteDoll();
                        return;
                    }
                }
            }
            if (this._master != null && this._master.isInvisble()) {
                this.deleteDoll();
                return;
            }
            if (this._master.isDead()) {
                this.deleteDoll();
                return;
            }
            if (this._master.getMoveSpeed() != this.getMoveSpeed()) {
                this.setMoveSpeed(this._master.getMoveSpeed());
            }
            if (this._master.getBraveSpeed() != this.getBraveSpeed()) {
                this.setBraveSpeed(this._master.getBraveSpeed());
            }
            if (this._master != null && this._master.getMapId() == this.getMapId()) {
                if (this.getLocation().getTileLineDistance(this._master.getLocation()) > 2) {
                    int dir = this.targetDirection(this._master.getX(), this._master.getY());
                    for (final L1Object object : World.get().getVisibleObjects(this, 1)) {
                        if (dir >= 0 && dir <= 7) {
                            final int locx = this.getX() + L1DollInstance.HEADING_TABLE_X[dir];
                            final int locy = this.getY() + L1DollInstance.HEADING_TABLE_Y[dir];
                            if (!(object instanceof L1DollInstance) || locx != object.getX() || locy != object.getY()) {
                                continue;
                            }
                            ++dir;
                        }
                    }
                    if (dir >= 0 && dir <= 7) {
                        this.setDirectionMoveSrc(dir);
                        this.broadcastPacketAll(new S_MoveCharPacket(this));
                    }
                }
            }
            else {
                this.deleteDoll();
            }
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public int getItemObjId() {
        return this._itemObjId;
    }
    
    public void setItemObjId(final int i) {
        this._itemObjId = i;
    }
    
    public int get_time() {
        return this._time;
    }
    
    public void set_time(final int time) {
        this._time = time;
    }
    
    public void startDollSkill(final L1Character target, final double dmg) {
        try {
            if (this._skillid != -1 && L1DollInstance._random.nextInt(100) <= this._r) {
                final L1Magic magic = new L1Magic(this._master, target);
                final int magic_dmg = magic.calcMagicDamage(this._skillid);
                magic.commit(magic_dmg, 0);
                final L1Skills skill = SkillsTable.get().getTemplate(this._skillid);
                final int castgfx = skill.getCastGfx();
                if (target instanceof L1NpcInstance) {
                    final L1NpcInstance npc = (L1NpcInstance)target;
                    if (this._skillid == 33 && npc.getNpcTemplate().is_boss()) {
                        return;
                    }
                    if (this._skillid == 29 && npc.getNpcTemplate().is_boss()) {
                        return;
                    }
                }
                target.broadcastPacketX10(new S_SkillSound(target.getId(), castgfx));
                if (target instanceof L1PcInstance) {
                    final L1PcInstance pc = (L1PcInstance)target;
                    pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
                }
                switch (this._skillid) {
                    case 4:
                    case 6:
                    case 7:
                    case 10:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 22:
                    case 25:
                    case 28:
                    case 30:
                    case 34:
                    case 38:
                    case 45:
                    case 46:
                    case 50:
                    case 58:
                    case 65:
                    case 74:
                    case 77:
                    case 108:
                    case 132:
                    case 184:
                    case 187:
                    case 192:
                    case 194:
                    case 202:
                    case 203:
                    case 207:
                    case 208:
                    case 213:
                    case 5003:
                    case 5004:
                    case 5005: {
                        this.broadcastPacketAll(new S_DoActionGFX(this.getId(), 67));
                        break;
                    }
                    case 29: {
                        switch (target.getMoveSpeed()) {
                            case 0: {
                                if (target instanceof L1PcInstance) {
                                    final L1PcInstance pc = (L1PcInstance)target;
                                    pc.sendPackets(new S_SkillHaste(pc.getId(), 2, skill.getBuffDuration()));
                                }
                                target.broadcastPacketAll(new S_SkillHaste(target.getId(), 2, skill.getBuffDuration()));
                                target.setMoveSpeed(2);
                                break;
                            }
                            case 1: {
                                int skillNum = 0;
                                if (target.hasSkillEffect(43)) {
                                    skillNum = 43;
                                }
                                else if (target.hasSkillEffect(54)) {
                                    skillNum = 54;
                                }
                                else if (target.hasSkillEffect(1001)) {
                                    skillNum = 1001;
                                }
                                if (skillNum != 0) {
                                    target.removeSkillEffect(skillNum);
                                    target.setMoveSpeed(0);
                                    break;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    case 11: {
                        final L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(null, this._skillid, target.getId(), target.getX(), target.getX(), 0, 4, this);
                        break;
                    }
                    case 33: {
                        final L1SkillUse skillUse2 = new L1SkillUse();
                        skillUse2.handleCommands(null, this._skillid, target.getId(), target.getX(), target.getX(), 0, 4, this);
                        break;
                    }
                    case 8911: {
                    	final L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(null, this._skillid, target.getId(), target.getX(), target.getX(), 0, 4, this);
                        break;
                    }
                    case 8912: {
                    	final L1SkillUse skillUse = new L1SkillUse();
                        skillUse.handleCommands(null, this._skillid, target.getId(), target.getX(), target.getX(), 0, 4, this);
                        break;
                    }
                    case 5000: {
                        if (target instanceof L1PcInstance) {
                            final L1PcInstance pc2 = (L1PcInstance)target;
                            pc2.sendPacketsAll(new S_SkillSound(pc2.getId(), castgfx));
                            pc2.receiveDamage(this._master, 80.0, true, true);
                            break;
                        }
                        if (target instanceof L1NpcInstance) {
                            final L1NpcInstance npc2 = (L1NpcInstance)target;
                            npc2.broadcastPacketAll(new S_SkillSound(npc2.getId(), castgfx));
                            npc2.receiveDamage(this._master, 80);
                            break;
                        }
                        break;
                    }
                    case 5001: {
                        if (target instanceof L1PcInstance) {
                            final L1PcInstance pc2 = (L1PcInstance)target;
                            pc2.sendPacketsAll(new S_SkillSound(pc2.getId(), castgfx));
                            pc2.receiveDamage(this._master, 160.0, true, true);
                            break;
                        }
                        if (target instanceof L1NpcInstance) {
                            final L1NpcInstance npc2 = (L1NpcInstance)target;
                            npc2.broadcastPacketAll(new S_SkillSound(npc2.getId(), castgfx));
                            npc2.receiveDamage(this._master, 160);
                            break;
                        }
                        break;
                    }
                    default:// 其他技能
						if (skill.getTarget().equalsIgnoreCase("attack")) {// 攻擊技能
							if (_skillid >= 5000 && _skillid <= 6000) {// 技能ID介於5000~6000
								int castgfx2 = skill.getCastGfx2();// 特效出現在娃娃身上時調用
								int castgfx3 = skill.getActionId();// 特效為飛行類魔法時調用(例如光箭)
								int mindmg = skill.getDamageValue();// 固定傷害
								int randomdmg = skill.getDamageDice();// 借欄位設定隨機傷害
								int dmg1 = mindmg + (_random.nextInt(randomdmg + 1));// 固定傷害
																					// +
																					// 隨機傷害

								if (target instanceof L1PcInstance) {
									L1PcInstance pc = (L1PcInstance) target;
									pc.receiveDamage(_master, dmg1, true, true);

								} else if (target instanceof L1NpcInstance) {
									L1NpcInstance npc = (L1NpcInstance) target;
									npc.receiveDamage(_master, dmg1);
								}

								if (castgfx != 0) {// 特效出現在怪物身上
									target.broadcastPacketAll(new S_SkillSound(target.getId(), castgfx));
									if (target instanceof L1PcInstance) {
										L1PcInstance pc = (L1PcInstance) target;
										pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
									}
									
								} else if (castgfx2 != 0) {// 特效出現在娃娃身上
									this.broadcastPacketAll(new S_SkillSound(this.getId(), castgfx2));
									
								} else if (castgfx3 != 0) {// 飛行類魔法動畫							
									S_UseAttackSkill packet = new S_UseAttackSkill(_master, target.getId(), castgfx3, target.getX(),
											target.getY(), 1, false);

									target.broadcastPacketAll(packet);
									if (target instanceof L1PcInstance) {
										L1PcInstance pc = (L1PcInstance) target;
										pc.sendPackets(packet);
									}
								}

							} else {// 其他技能 (特效只會出現在怪物身上)
								L1Magic magic1 = new L1Magic(_master, target);
								int magic_dmg1 = magic1.calcMagicDamage(_skillid);// 計算傷害
								if (magic_dmg1 > 200) {
									magic_dmg1 = 200;// 限制最大傷害為200
								}
								magic1.commit(magic_dmg1, 0);

								// 發送技能動畫
								target.broadcastPacketAll(new S_SkillSound(target.getId(), castgfx));
								if (target instanceof L1PcInstance) {
									L1PcInstance pc = (L1PcInstance) target;
									pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
								}
							}
						}
						break;
                }
            }
        }
        catch (Exception e) {
            L1DollInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void set_skill(final int int1, final int int2) {
        this._skillid = int1;
        if (this._skillid != -1) {
            this.setLevel(this._master.getLevel());
            this.setInt(this._master.getInt());
        }
        this._r = int2;
    }
    
    public int get_skillid() {
        return this._skillid;
    }
    
    public int get_skillrandom() {
        return this._r;
    }
    
    public void set_olX(final int x) {
        this._olX = x;
    }
    
    public int get_olX() {
        return this._olX;
    }
    
    public void set_olY(final int y) {
        this._olY = y;
    }
    
    public int get_olY() {
        return this._olY;
    }
    
    public L1ItemInstance getInstance() {
        return this._item;
    }
    
    public void setInstance(final L1ItemInstance item) {
        this._item = item;
    }
    
    public void startDollSkill() {
        if (!this._type.getPowerList().isEmpty() && this._master instanceof L1PcInstance) {
            final L1PcInstance masterpc = (L1PcInstance)this._master;
            for (final L1DollExecutor p : this._type.getPowerList()) {
                if (p.is_reset()) {
                    p.setDoll(masterpc);
                }
            }
        }
    }
    
    public void show_action(final int i) {
        if (!this._type.getPowerList().isEmpty()) {
            if (i == 1 && this._master instanceof L1PcInstance) {
                final L1PcInstance pc = (L1PcInstance)this._master;
                pc.sendPacketsAll(new S_SkillSound(this._master.getId(), 6319));
            }
            if (i == 2 && this._master instanceof L1PcInstance) {
                final L1PcInstance pc = (L1PcInstance)this._master;
                pc.sendPacketsAll(new S_SkillSound(this._master.getId(), 6320));
            }
            if (i == 3 && this._master instanceof L1PcInstance) {
                final L1PcInstance pc = (L1PcInstance)this._master;
                pc.sendPacketsAll(new S_SkillSound(this._master.getId(), 6321));
            }
        }
    }
    
    public boolean is_power_doll() {
        return this._power_doll;
    }
}
