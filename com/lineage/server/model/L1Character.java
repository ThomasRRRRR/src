package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.*;
import java.util.Collection;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.datatables.QuestMapTable;
import com.lineage.server.serverpackets.S_Light;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.utils.RangeInt;
import com.lineage.server.utils.IntRange;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_PetCtrlMenu;
import com.lineage.server.model.Instance.L1PetInstance;
import java.util.Set;
import com.lineage.server.model.skill.L1SkillStop;
import com.lineage.server.model.skill.L1SkillTimerCreator;
import com.lineage.server.types.Point;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.S_HPMeter;
import java.util.Iterator;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.world.World;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1EffectInstance;
import java.util.ArrayList;
import com.lineage.server.model.Instance.L1HierarchInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.List;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.model.Instance.L1FollowerInstance;
import com.lineage.server.model.skill.L1SkillTimer;
import java.util.HashMap;
import com.lineage.server.model.Instance.L1NpcInstance;
import java.util.Map;
import com.lineage.server.model.poison.L1Poison;
import org.apache.commons.logging.Log;

/**
 * L1Character
 * @author daien
 *
 */
public class L1Character extends L1Object {
	private static final Log _log = LogFactory.getLog(L1Character.class);
	private static final long serialVersionUID = 1L;
    private L1Poison _poison;
    private boolean _sleeped;
    private final Map<Integer, L1NpcInstance> _petlist;
    private final HashMap<Integer, L1SkillTimer> _skillEffect;
    private final Map<Integer, L1ItemDelay.ItemDelayTimer> _itemdelay;
    private final Map<Integer, L1FollowerInstance> _followerlist;
    private int _secHp;
    private int _currentHp;
    private int _currentMp;
    private boolean _paralyzed;
    L1Paralysis _paralysis;
	protected static final byte[] HEADING_TABLE_X = { 0, 1, 1, 1, 0, -1, -1, -1 };
	protected static final byte[] HEADING_TABLE_Y = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private boolean _isSkillDelay;
    private final Map<Integer, L1DollInstance> _dolls;
    private long _exp;
    private final List<L1Object> _knownObjects;
    private final List<L1PcInstance> _knownPlayer;
    private String _name;
    private int _level;
    private int _maxHp;
    private int _trueMaxHp;
    private int _maxMp;
    private int _trueMaxMp;
    private int _ac;
    private int _trueAc;
    private short _str;
    private short _trueStr;
    private short _con;
    private short _trueCon;
    private short _dex;
    private short _trueDex;
    private short _cha;
    private short _trueCha;
    private short _int;
    private short _trueInt;
    private short _wis;
    private short _trueWis;
    private int _wind;
    private int _trueWind;
    private int _water;
    private int _trueWater;
    private int _fire;
    private int _trueFire;
    private int _earth;
    private int _trueEarth;
    private int _addAttrKind;
    private int _registStun;
    private int _trueRegistStun;
    private int _registStone;
    private int _trueRegistStone;
    private int _registSleep;
    private int _trueRegistSleep;
    private int _registFreeze;
    private int _trueRegistFreeze;
    private int _registSustain;
    private int _trueRegistSustain;
    private int _registBlind;
    private int _trueRegistBlind;
    private int _dmgup;
    private int _bowDmgup;
    private int _hitup;
    private int _trueHitup;
    private int _bowHitup;
    private int _trueBowHitup;
    private int _mr;
    private int _trueMr;
    private int _sp;
    private boolean _isDead;
    private int _status;
    private String _title;
    private int _lawful;
    private int _heading;
    private int _moveSpeed;
    private int _braveSpeed;
    private int _tempCharGfx;
    private int _gfxid;
    private int _karma;
    private int _chaLightSize;
    private int _ownLightSize;
    private int _tmp_targetid;
    private int _tmp_mr;
    private int _dodge_up;
    private int _dodge_down;
    private boolean _decay_potion;
    private int _innRoomNumber;
    private boolean _isHall;
    private L1DollInstance _usingdoll;
    private L1DollInstance _power_doll;
    private int _trueSp;
    private L1HierarchInstance _Hierarch;
    private int _hierarch;
    private ArrayList<L1EffectInstance> _TrueTargetEffectList;
    private boolean _TripleArrow;
    private int _cancellation;
    private int _double_score;
    private int _poisonStatus2;
    private int _poisonStatus7;
    private boolean _isPinkName;
    private int _useitemobjid;

    public L1Character() {
        this._poison = null;
        this._petlist = new HashMap<Integer, L1NpcInstance>();
        this._skillEffect = new HashMap<Integer, L1SkillTimer>();
        this._itemdelay = new HashMap<Integer, L1ItemDelay.ItemDelayTimer>();
        this._followerlist = new HashMap<Integer, L1FollowerInstance>();
        this._secHp = -1;
        this._isSkillDelay = false;
        this._dolls = new HashMap<Integer, L1DollInstance>();
        this._knownObjects = new CopyOnWriteArrayList<L1Object>();
        this._knownPlayer = new CopyOnWriteArrayList<L1PcInstance>();
        this._maxHp = 0;
        this._trueMaxHp = 0;
        this._maxMp = 0;
        this._trueMaxMp = 0;
        this._ac = 10;
        this._trueAc = 0;
        this._str = 0;
        this._trueStr = 0;
        this._con = 0;
        this._trueCon = 0;
        this._dex = 0;
        this._trueDex = 0;
        this._cha = 0;
        this._trueCha = 0;
        this._int = 0;
        this._trueInt = 0;
        this._wis = 0;
        this._trueWis = 0;
        this._wind = 0;
        this._trueWind = 0;
        this._water = 0;
        this._trueWater = 0;
        this._fire = 0;
        this._trueFire = 0;
        this._earth = 0;
        this._trueEarth = 0;
        this._registStun = 0;
        this._trueRegistStun = 0;
        this._registStone = 0;
        this._trueRegistStone = 0;
        this._registSleep = 0;
        this._trueRegistSleep = 0;
        this._registFreeze = 0;
        this._trueRegistFreeze = 0;
        this._registSustain = 0;
        this._trueRegistSustain = 0;
        this._registBlind = 0;
        this._trueRegistBlind = 0;
        this._dmgup = 0;
        this._bowDmgup = 0;
        this._hitup = 0;
        this._trueHitup = 0;
        this._bowHitup = 0;
        this._trueBowHitup = 0;
        this._mr = 0;
        this._trueMr = 0;
        this._sp = 0;
        this._dodge_up = 0;
        this._dodge_down = 0;
        this._decay_potion = false;
        this._power_doll = null;
        this._trueSp = 0;
        this._Hierarch = null;
        this._hierarch = 1;
        this._TrueTargetEffectList = new ArrayList<L1EffectInstance>();
        this._TripleArrow = false;
        this._cancellation = 0;
        this._double_score = 0;
        this._isPinkName = false;
        this._level = 1;
    }
    
	/**
	 * 傳回與目標相反的面向
	 * 
	 * @param cha
	 * @return heading
	 */
	public int targetReverseHeading(L1Character cha) {
		int heading = cha.getHeading();
		heading += 4;
		if (heading > 7) {
			heading -= 8;
		}
		return heading;
	}
    
	/**
	 * 物件復活
	 *
	 * @param hp
	 *            復活後的HP
	 */
	public void resurrect(int hp) {
		try {
			if (!isDead()) {
				return;
			}
			if (hp <= 0) {
				hp = 1;
			}
			// 設置為未死亡
			setDead(false);
			// 設置HP
			setCurrentHp(hp);
			// 設置狀態
			setStatus(0);
			// 解除變身
			L1PolyMorph.undoPoly(this);

			if (this instanceof L1PetInstance) {// 寵物
				L1PetInstance pet = (L1PetInstance) this;
				pet.setCurrentPetStatus(3);// 設為休息狀態
			}

			// 重新認識物件
			for (final L1PcInstance pc : World.get().getRecognizePlayer(this)) {
				pc.sendPackets(new S_RemoveObject(this));
				pc.removeKnownObject(this);
				pc.updateObject();
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	public void broadcastPacketHP(L1PcInstance pc) {
		try {
			if (_secHp != getCurrentHp()) {
				_secHp = getCurrentHp();
				pc.sendPackets(new S_HPMeter(this));
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	public int getCurrentHp() {
		return _currentHp;
	}

	public void setCurrentHp(int i) {
		_currentHp = i;
		if (_currentHp >= getMaxHp()) {
			_currentHp = getMaxHp();
		}
	}
    
    public void setCurrentHpDirect(final int i) {
        this._currentHp = i;
    }
    
    public int getCurrentMp() {
        return this._currentMp;
    }
    
    public void setCurrentMp(final int i) {
        this._currentMp = i;
        if (this._currentMp >= this.getMaxMp()) {
            this._currentMp = this.getMaxMp();
        }
    }
    
    public void setCurrentMpDirect(final int i) {
        this._currentMp = i;
    }
    
    public boolean isSleeped() {
        return this._sleeped;
    }
    
    public void setSleeped(final boolean sleeped) {
        this._sleeped = sleeped;
    }
    
    /**
	 * 是否在癱瘓狀態(無法攻擊/無法使用道具/無法使用技能/無法傳送)
	 * 
	 * @return
	 */
	public boolean isParalyzedX() {
		
		// 冰雪颶風
		if (hasSkillEffect(FREEZING_BLIZZARD)) {
			return true;
		}
		// 寒冰噴吐
		if (hasSkillEffect(FREEZING_BREATH)) {
			return true;
		}
		if (hasSkillEffect(STATUS_CURSE_PARALYZED)) {// 詛咒麻痺
			return true;
		}

		if (hasSkillEffect(STATUS_POISON_PARALYZED)) {// 毒性麻痺
			return true;
		}

		if (hasSkillEffect(FOG_OF_SLEEPING)) {// 沉睡之霧
			return true;
		}

		if (hasSkillEffect(SHOCK_STUN)) {// 衝暈
			return true;
		}

		if (hasSkillEffect(PHANTASM)) {// 幻想
			return true;
		}

		if (hasSkillEffect(ICE_LANCE)) {// 冰矛圍籬
			return true;
		}

		if (hasSkillEffect(EARTH_BIND)) {// 大地屏障
			return true;
		}

		if (hasSkillEffect(DARK_BLIND)) {// 暗黑盲咒
			return true;
		}

		if (hasSkillEffect(BONE_BREAK)) {// 骷髏毀壞
			return true;
		}
		return false;
	}
    
	/**
	 * NPC是否在癱瘓狀態
	 * 
	 * @param paralyzed
	 */
	public boolean isParalyzed() {
		return _paralyzed;
	}

	/**
	 * NPC是否在癱瘓狀態
	 * 
	 * @param paralyzed
	 */
	public void setParalyzed(boolean paralyzed) {
		_paralyzed = paralyzed;
	}
    
    public L1Paralysis getParalysis() {
        return this._paralysis;
    }
    
    public void setParalaysis(final L1Paralysis p) {
        this._paralysis = p;
    }
    
    public void cureParalaysis() {
        if (this._paralysis != null) {
            this._paralysis.cure();
        }
    }
    
    /**
	 * 該物件全部可見範圍封包發送(不包含自己)
	 * 
	 * @param packet
	 *            封包
	 */
	public void broadcastPacketAll(final ServerBasePacket packet) {
		try {
			for (final L1PcInstance pc : World.get().getVisiblePlayer(this)) {
				// 副本ID相等
				if (pc.get_showId() == this.get_showId()) {
					pc.sendPackets(packet);
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
    public void broadcastPacketYN(final ServerBasePacket packet) {
        try {
            for (final L1PcInstance pc : World.get().getVisiblePlayer(this)) {
                if (pc.get_showId() == this.get_showId() && pc.getopengfxid()) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
	/**
	 * 該物件指定範圍封包發送(範圍10)
	 * 
	 * @param packet
	 *            封包
	 */
	public void broadcastPacketX10(final ServerBasePacket packet) {
		try {
			for (final L1PcInstance pc : World.get().getVisiblePlayer(this, 10)) {
				// 副本ID相等
				if (pc.get_showId() == this.get_showId()) {
					pc.sendPackets(packet);
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 該物件指定範圍封包發送(範圍8)
	 * 
	 * @param packet
	 *            封包
	 */
	public void broadcastPacketX8(final ServerBasePacket packet) {
		try {
			for (final L1PcInstance pc : World.get().getVisiblePlayer(this, 8)) {
				// 副本ID相等
				if (pc.get_showId() == this.get_showId()) {
					pc.sendPackets(packet);
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 該物件指定範圍封包發送(指定範圍)
	 * 
	 * @param packet
	 *            封包
	 * @param r
	 *            指定範圍
	 */
	public void broadcastPacketXR(final ServerBasePacket packet, final int r) {
		try {
			for (final L1PcInstance pc : World.get().getVisiblePlayer(this, r)) {
				// 副本ID相等
				if (pc.get_showId() == this.get_showId()) {
					pc.sendPackets(packet);
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 該物件50格範圍封包發送
	 * 
	 * @param packet
	 *            封包
	 */
	public void wideBroadcastPacket(final ServerBasePacket packet) {
		for (final L1PcInstance pc : World.get().getVisiblePlayer(this, 50)) {
			// 副本ID相等
			if (pc.get_showId() == this.get_showId()) {
				pc.sendPackets(packet);
			}
		}
	}
    
	/**
	 * 該物件可見範圍封包發送, (指定物件)
	 * 
	 * @param packet
	 *            封包
	 * @param target
	 *            指定物件
	 */
	public void broadcastPacketExceptTargetSight(final ServerBasePacket packet, final L1Character target) {
		boolean isX8 = false;
		// 檢查城堡戰爭狀態
		if (ServerWarExecutor.get().checkCastleWar() > 0) {
			isX8 = true;
			;
		}
		if (isX8) {
			for (final L1PcInstance tgpc : World.get().getVisiblePlayerExceptTargetSight(this, target, 8)) {
				tgpc.sendPackets(packet);
			}

		} else {
			for (final L1PcInstance tgpc : World.get().getVisiblePlayerExceptTargetSight(this, target)) {
				tgpc.sendPackets(packet);
			}
		}
	}
    
	/**
	 * キャラクターの正面の座標を返す。
	 *
	 * @return 正面の座標
	 */
	public int[] getFrontLoc() {
		final int[] loc = new int[2];
		int x = this.getX();
		int y = this.getY();
		final int heading = this.getHeading();

		x += HEADING_TABLE_X[heading];
		y += HEADING_TABLE_Y[heading];

		loc[0] = x;
		loc[1] = y;
		return loc;
	}
    
	/**
	 * 指定座標對應的面向
	 *
	 * @param tx
	 *            座標 X値
	 * @param ty
	 *            座標 Y値
	 * @return 指定座標對硬的面向
	 */
	public int targetDirection(final int tx, final int ty) {

		final float dis_x = Math.abs(this.getX() - tx); // X點方向距離
		final float dis_y = Math.abs(this.getY() - ty); // Y點方向距離
		final float dis = Math.max(dis_x, dis_y); // 取回2者最大質
		if (dis == 0) {
			return this.getHeading(); // 距離為0表示不須改變面向
		}
		final int avg_x = (int) Math.floor((dis_x / dis) + 0.59f); // 上下左右がちょっと優先な丸め
		final int avg_y = (int) Math.floor((dis_y / dis) + 0.59f); // 上下左右がちょっと優先な丸め

		int dir_x = 0;
		int dir_y = 0;
		if (this.getX() < tx) {
			dir_x = 1;
		}
		if (this.getX() > tx) {
			dir_x = -1;
		}
		if (this.getY() < ty) {
			dir_y = 1;
		}
		if (this.getY() > ty) {
			dir_y = -1;
		}

		if (avg_x == 0) {
			dir_x = 0;
		}
		if (avg_y == 0) {
			dir_y = 0;
		}

		switch (dir_x) {
		case -1:
			switch (dir_y) {
			case -1:
				return 7; // 左
			case 0:
				return 6; // 左下
			case 1:
				return 5; // 下
			}
			break;
		case 0:
			switch (dir_y) {
			case -1:
				return 0; // 左上
			case 1:
				return 4; // 右下
			}
			break;
		case 1:
			switch (dir_y) {
			case -1:
				return 1; // 上
			case 0:
				return 2; // 右上
			case 1:
				return 3; // 右
			}
			break;
		}
		return this.getHeading(); // ここにはこない。はず
	}
    
	/**
	 * 檢查是否有障礙物阻擋
	 * 
	 * @param tx
	 * @param ty
	 * @return true:無障礙物;false:有障礙物阻擋
	 */
	public boolean glanceCheck(final int tx, final int ty) {
		final L1Map map = getMap();
		int chx = getX();
		int chy = getY();
		// final int arw = 0;

		for (int i = 0; i < 15; i++) {
			if (((chx == tx) && (chy == ty)) || ((chx + 1 == tx) && (chy - 1 == ty)) || ((chx + 1 == tx) && (chy == ty))
					|| ((chx + 1 == tx) && (chy + 1 == ty)) || ((chx == tx) && (chy + 1 == ty))
					|| ((chx - 1 == tx) && (chy + 1 == ty)) || ((chx - 1 == tx) && (chy == ty))
					|| ((chx - 1 == tx) && (chy - 1 == ty)) || ((chx == tx) && (chy - 1 == ty))) {
				break;

			} else {
				int th = targetDirection(tx, ty);
				if (!map.isArrowPassable(chx, chy, th)) {
					return false;
				}
				if (chx < tx) {
					if (chy == ty) {
						chx++;

					} else if (chy > ty) {
						chx++;
						chy--;

					} else if (chy < ty) {
						chx++;
						chy++;

					}

				} else if (chx == tx) {
					if (chy < ty) {
						chy++;

					} else if (chy > ty) {
						chy--;
					}

				} else if (chx > tx) {
					if (chy == ty) {
						chx--;

					} else if (chy < ty) {
						chx--;
						chy++;

					} else if (chy > ty) {
						chx--;
						chy--;
					}
				}
			}
		}
		return true;
	}
    
	/**
	 * 是否到達可攻擊的距離
	 * 
	 * @param x
	 *            目標的X座標
	 * @param y
	 *            目標的Y座標
	 * @param range
	 *            攻擊距離
	 * @return
	 */
	public boolean isAttackPosition(int x, int y, int range) {
		if (getLocation().getTileLineDistance(new Point(x, y)) > range) {
			return false;
		}

		return glanceCheck(x, y);
	}

	public L1Inventory getInventory() {
		return null;
	}
    
	/**
	 * 加入技能效果清單
	 * 
	 * @param skillId
	 * @param timeMillis
	 */
	private void addSkillEffect(int skillId, int timeMillis) {
		L1SkillTimer timer = null;
		if (timeMillis > 0) {// 技能效果時間大於0
			timer = L1SkillTimerCreator.create(this, skillId, timeMillis);// 創建計時器
			timer.begin();// 開始計時
		}
		_skillEffect.put(Integer.valueOf(skillId), timer);
	}
    
	/**
	 * 給予技能效果與時間
	 * 
	 * @param skillId
	 * @param timeMillis
	 */
	public void setSkillEffect(int skillId, int timeMillis) {
		L1SkillTimer timer = (L1SkillTimer) _skillEffect.get(Integer.valueOf(skillId));
		if (timer != null) {// 已有計時器
			int remainingTimeMills = timer.getRemainingTime();// 剩餘時間
			timeMillis /= 1000;// 轉換為秒

			if ((remainingTimeMills >= 0) // 還有剩餘時間
					&& ((remainingTimeMills < timeMillis) || (timeMillis == 0))) {
				timer.setRemainingTime(timeMillis);
			}

		} else {// 沒有計時器
			addSkillEffect(skillId, timeMillis);
		}
	}

	/**
	 * 刪除技能效果(有計時器的技能)
	 * 
	 * @param skillId
	 */
	public void removeSkillEffect(int skillId) {
		L1SkillTimer timer = (L1SkillTimer) _skillEffect.remove(Integer.valueOf(skillId));
		if (timer != null) {// 具有計時器
			timer.end();
		}
	}
    
	/**
	 * 刪除技能效果(無計時器的技能)
	 * 
	 * @param skillId
	 */
	public void removeNoTimerSkillEffect(int skillId) {
		_skillEffect.remove(Integer.valueOf(skillId));
		L1SkillStop.stopSkill(this, skillId);
	}

	/**
	 * 刪除全部技能效果
	 */
	public void clearAllSkill() {

		ArrayList<Integer> effectlist = new ArrayList<Integer>();
		for (Integer key : _skillEffect.keySet()) {
			effectlist.add(key);
		}

		for (int i = 0; i < effectlist.size(); i++) {
			if (this.hasSkillEffect(effectlist.get(i))) {
				this.removeSkillEffect(effectlist.get(i));
			}
		}

		/*
		 * for (final L1SkillTimer timer : _skillEffect.values()) { if (timer !=
		 * null) { timer.end(); } }
		 */
		_skillEffect.clear();
	}
    
	/**
	 * 刪除技能計時器
	 * 
	 * @param skillId
	 */
	public void killSkillEffectTimer(int skillId) {
		L1SkillTimer timer = (L1SkillTimer) _skillEffect.remove(Integer.valueOf(skillId));
		if (timer != null) {
			timer.kill();
		}
	}

	/**
	 * 刪除全部技能計時器
	 */
	public void clearSkillEffectTimer() {
		for (L1SkillTimer timer : _skillEffect.values()) {
			if (timer != null) {
				timer.kill();
			}
		}
		_skillEffect.clear();
	}

	/**
	 * 是否有此技能效果
	 * @param skillId
	 * @return
	 */
	public boolean hasSkillEffect(int skillId) {
		return _skillEffect.containsKey(Integer.valueOf(skillId));
	}
    
	/**
	 * 取回全部技能效果id
	 * @return
	 */
	public Set<Integer> getSkillEffect() {
		return _skillEffect.keySet();
	}

	/**
	 * 身上技能效果是否為空
	 * @return
	 */
	public boolean getSkillisEmpty() {
		return _skillEffect.isEmpty();
	}

	/**
	 * 取回此技能的剩餘時間(秒)
	 * @param skillId
	 * @return
	 */
	public int getSkillEffectTimeSec(int skillId) {
		L1SkillTimer timer = (L1SkillTimer) _skillEffect.get(Integer.valueOf(skillId));
		if (timer == null) {
			return -1;
		}
		return timer.getRemainingTime();
	}
	/**
	 * 設定是否技能延遲中
	 * @param flag
	 */
	public void setSkillDelay(boolean flag) {
		_isSkillDelay = flag;
	}
	/**
	 * 是否技能延遲中
	 * @return
	 */
	public boolean isSkillDelay() {
		return _isSkillDelay;
	}
    
	/**
	 * 加入道具分類延遲清單
	 * @param delayId
	 * @param timer
	 */
	public void addItemDelay(int delayId, L1ItemDelay.ItemDelayTimer timer) {
		_itemdelay.put(Integer.valueOf(delayId), timer);
	}
    
	/**
	 * 移出道具分類延遲清單
	 * @param delayId
	 */
	public void removeItemDelay(int delayId) {
		_itemdelay.remove(Integer.valueOf(delayId));
	}

	/**
	 * 此道具分類是否延遲中
	 * @param delayId
	 * @return
	 */
	public boolean hasItemDelay(int delayId) {
		return _itemdelay.containsKey(Integer.valueOf(delayId));
	}

	/**
	 * 取回此道具分類延遲計時器
	 * @param delayId
	 * @return
	 */
	public L1ItemDelay.ItemDelayTimer getItemDelayTimer(int delayId) {
		return (L1ItemDelay.ItemDelayTimer) _itemdelay.get(Integer.valueOf(delayId));
	}

	/**
	 * 加入寵物清單
	 * @param npc
	 */
	public void addPet(L1NpcInstance npc) {
		_petlist.put(Integer.valueOf(npc.getId()), npc);

		sendPetCtrlMenu(npc, true);
	}

	/**
	 * 移出寵物清單
	 * @param npc
	 */
	public void removePet(L1NpcInstance npc) {
		_petlist.remove(Integer.valueOf(npc.getId()));

		sendPetCtrlMenu(npc, false);
	}

	/**
	 * 傳回寵物清單
	 * @return
	 */
	public Map<Integer, L1NpcInstance> getPetList() {
		return _petlist;
	}
    
	private final void sendPetCtrlMenu(L1NpcInstance npc, boolean type) {
		if ((npc instanceof L1PetInstance)) {
			L1PetInstance pet = (L1PetInstance) npc;
			L1Character cha = pet.getMaster();

			if ((cha instanceof L1PcInstance)) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_PetCtrlMenu(pc, pet, type));

				if (type) {
					pc.sendPackets(new S_HPMeter(pet));
				}
			}
		} else if ((npc instanceof L1SummonInstance)) {
			L1SummonInstance summon = (L1SummonInstance) npc;
			L1Character cha = summon.getMaster();

			if ((cha instanceof L1PcInstance)) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_PetCtrlMenu(pc, summon, type));

				if (type) {
					pc.sendPackets(new S_HPMeter(summon));
				}
			}
		}
	}

	/**
	 * 設定目前正在使用的娃娃
	 * @param doll
	 */
	public void setUsingDoll(L1DollInstance doll) {
		_usingdoll = doll;
	}

	/**
	 * 取回目前正在使用的娃娃
	 * @return
	 */
	public L1DollInstance getUsingDoll() {
		return _usingdoll;
	}
    
	/**
	 * 加入使用中娃娃清單
	 * 
	 * @param doll
	 */
	public void addDoll(L1DollInstance doll) {
		_dolls.put(Integer.valueOf(doll.getItemObjId()), doll);
	}
    
	/**
	 * 從使用中娃娃清單中移出
	 * 
	 * @param doll
	 */
	public void removeDoll(L1DollInstance doll) {
		_dolls.remove(Integer.valueOf(doll.getItemObjId()));
	}

	/**
	 * 依照道具OBJID取回使用中的娃娃資料
	 * 
	 * @param ItemObjId
	 * @return
	 */
	public L1DollInstance getDoll(int ItemObjId) {
		return (L1DollInstance) _dolls.get(Integer.valueOf(ItemObjId));
	}
    
	/**
	 * 取回使用中的娃娃清單
	 * 
	 * @return
	 */
	public Map<Integer, L1DollInstance> getDolls() {
		return _dolls;
	}

	/**
	 * 設置超級娃娃
	 * 
	 * @param doll
	 */
	public void add_power_doll(final L1DollInstance doll) {
		_power_doll = doll;
	}

	/**
	 * 移除超級娃娃
	 */
	public void remove_power_doll() {
		_power_doll = null;
	}
    
	/**
	 * 目前攜帶的超級娃娃
	 * 
	 * @return 目前攜帶的超級娃娃
	 */
	public L1DollInstance get_power_doll() {
		return _power_doll;
	}
	public void addFollower(L1FollowerInstance follower) {
		_followerlist.put(Integer.valueOf(follower.getId()), follower);
	}

	public void removeFollower(L1FollowerInstance follower) {
		_followerlist.remove(Integer.valueOf(follower.getId()));
	}

	public Map<Integer, L1FollowerInstance> getFollowerList() {
		return _followerlist;
	}

	public void setPoison(L1Poison poison) {
		_poison = poison;
	}

	public void curePoison() {
		if (_poison == null) {
			return;
		}
		_poison.cure();
	}
    
	public L1Poison getPoison() {
		return _poison;
	}

	/**
	 * 設定中毒顏色 0:通常 1:綠色 2:灰色
	 * @param type
	 */
	public void setPoisonEffect(int type) {
		broadcastPacketX8(new S_Poison(getId(), type));
	}
    
	public int getZoneType() {
		if (getMap().isSafetyZone(getLocation()))
			return 1;
		if (getMap().isCombatZone(getLocation())) {
			return -1;
		}
		return 0;
	}

	public boolean isSafetyZone() {
		if (getMap().isSafetyZone(getLocation())) {
			return true;
		}
		return false;
	}

	public boolean isCombatZone() {
		if (getMap().isCombatZone(getLocation())) {
			return true;
		}
		return false;
	}

	public boolean isNoneZone() {
		return getZoneType() == 0;
	}
    
	public long getExp() {
		return _exp;
	}

	public void setExp(long exp) {
		_exp = exp;
	}
    
	public boolean knownsObject(L1Object obj) {
		return _knownObjects.contains(obj);
	}

	public List<L1Object> getKnownObjects() {
		return _knownObjects;
	}

	public List<L1PcInstance> getKnownPlayers() {
		return _knownPlayer;
	}

	public void addKnownObject(L1Object obj) {
		if (!_knownObjects.contains(obj)) {
			_knownObjects.add(obj);
			if ((obj instanceof L1PcInstance)) {
				_knownPlayer.add((L1PcInstance) obj);
			}
		}
	}
    
	public void removeKnownObject(L1Object obj) {
		_knownObjects.remove(obj);
		if ((obj instanceof L1PcInstance))
			_knownPlayer.remove(obj);
	}

	public void removeAllKnownObjects() {
		_knownObjects.clear();
		_knownPlayer.clear();
	}
	private String _srcname;// 人物改名前名稱

	/**
	 * 取回改名前名稱
	 * @return
	 */
	public String getSrcName() {
		return _srcname;
	}

	/**
	 * 設定改名前名稱
	 * @param s
	 */
	public void setSrcName(String s) {
		_srcname = s;
	}
	/**
	 * 目前名稱
	 * @return
	 */
	public String getName() {
		return _name;
	}

	/**
	 * 設定目前名稱
	 * @param s
	 */
	public void setName(String s) {
		_name = s;
	}
    
	/**
	 * 取回目前等級
	 * @return
	 */
	public synchronized int getLevel() {
		return _level;
	}

	/**
	 * 設定目前等級
	 * @param level
	 */
	public synchronized void setLevel(int level) {
		_level = level;
	}

	/**
	 * 取回最大血量
	 * 
	 * @return
	 */
	public int getMaxHp() {
		return _maxHp;
	}
    
    public void setMaxHp(final int hp) {
        this._trueMaxHp = hp;
        if (this instanceof L1PcInstance) {
            this._maxHp = (short)IntRange.ensure(this._trueMaxHp, 1, 32767);
        }
        else {
            this._maxHp = IntRange.ensure(this._trueMaxHp, 1, Integer.MAX_VALUE);
        }
        this._currentHp = Math.min(this._currentHp, this._maxHp);
    }
    
    /**
	 * 增加最大血量
	 * 
	 * @param i
	 */
	public void addMaxHp(int i) {
		setMaxHp(_trueMaxHp + i);
	}
	/**
	 * 取回最大魔量
	 * 
	 * @return
	 */
    public int getMaxMp() {
        return this._maxMp;
    }
    
    public void setMaxMp(final int mp) {
        this._trueMaxMp = mp;
        if (this instanceof L1PcInstance) {
            this._maxMp = (short)IntRange.ensure(this._trueMaxMp, 1, 32767);
        }
        else {
            this._maxMp = IntRange.ensure(this._trueMaxMp, 1, Integer.MAX_VALUE);
        }
        this._currentMp = Math.min(this._currentMp, this._maxMp);
    }
    
    /**
	 * 增加最大魔量
	 * 
	 * @param i
	 */
	public void addMaxMp(int i) {
		setMaxMp(_trueMaxMp + i);
	}
    
	/**
	 * 取回防禦值AC(7.6版)
	 * 
	 * @return
	 */
	public int getAc() {
		int ac = _ac;

		if (this instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) this;
            switch (pc.guardianEncounter()) {
                case 0: {
                    ac -= 2;
                    break;
                }
                case 1: {
                    ac -= 4;
                    break;
                }
                case 2: {
                    ac -= 6;
                    break;
                }
            }
        }
        return RangeInt.ensure(ac, -211, 44);
    }
    
    public void setAc(final int i) {
        this._trueAc = i;
        this._ac = IntRange.ensure(i, -219, 44);
    }
    
    public void addAc(final int i) {
        this.setAc(this._trueAc + i);
    }
    
    public short getStr() {
        return this._str;
    }
    
    public void setStr(final int i) {
        this._trueStr = (short)i;
        this._str = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addStr(final int i) {
        this.setStr(this._trueStr + i);
    }
    
    public short getCon() {
        return this._con;
    }
    
    public void setCon(final int i) {
        this._trueCon = (short)i;
        this._con = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addCon(final int i) {
        this.setCon(this._trueCon + i);
    }
    
    public short getDex() {
        return this._dex;
    }
    
    public void setDex(final int i) {
        this._trueDex = (short)i;
        this._dex = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addDex(final int i) {
        this.setDex(this._trueDex + i);
    }
    
    public short getCha() {
        return this._cha;
    }
    
    public void setCha(final int i) {
        this._trueCha = (short)i;
        this._cha = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addCha(final int i) {
        this.setCha(this._trueCha + i);
    }
    
    public short getInt() {
        return this._int;
    }
    
    public void setInt(final int i) {
        this._trueInt = (short)i;
        this._int = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addInt(final int i) {
        this.setInt(this._trueInt + i);
    }
    
    public short getWis() {
        return this._wis;
    }
    
    public void setWis(final int i) {
        this._trueWis = (short)i;
        this._wis = (short)RangeInt.ensure(i, 1, 254);
    }
    
    public void addWis(final int i) {
        this.setWis(this._trueWis + i);
    }
    
    public int getWind() {
        return this._wind;
    }
    
    public void addWind(final int i) {
        this._trueWind += i;
        if (this._trueWind >= 150) {
            this._wind = 150;
        }
        else if (this._trueWind <= -150) {
            this._wind = -150;
        }
        else {
            this._wind = this._trueWind;
        }
    }
    
    public int getWater() {
        return this._water;
    }
    
    public void addWater(final int i) {
        this._trueWater += i;
        if (this._trueWater >= 150) {
            this._water = 150;
        }
        else if (this._trueWater <= -150) {
            this._water = -150;
        }
        else {
            this._water = this._trueWater;
        }
    }
    
    public int getFire() {
        return this._fire;
    }
    
    public void addFire(final int i) {
        this._trueFire += i;
        if (this._trueFire >= 150) {
            this._fire = 150;
        }
        else if (this._trueFire <= -150) {
            this._fire = -150;
        }
        else {
            this._fire = this._trueFire;
        }
    }
    
    public int getEarth() {
        return this._earth;
    }
    
    public void addEarth(final int i) {
        this._trueEarth += i;
        if (this._trueEarth >= 150) {
            this._earth = 150;
        }
        else if (this._trueEarth <= -150) {
            this._earth = -150;
        }
        else {
            this._earth = this._trueEarth;
        }
    }
    
    public int getAddAttrKind() {
        return this._addAttrKind;
    }
    
    public void setAddAttrKind(final int i) {
        this._addAttrKind = i;
    }
    
    public int getRegistStun() {
        return this._registStun;
    }
    
    public void addRegistStun(final int i) {
        this._trueRegistStun += i;
        if (this._trueRegistStun > 127) {
            this._registStun = 127;
        }
        else if (this._trueRegistStun < -128) {
            this._registStun = -128;
        }
        else {
            this._registStun = this._trueRegistStun;
        }
    }
    
    public int getRegistStone() {
        return this._registStone;
    }
    
    public void addRegistStone(final int i) {
        this._trueRegistStone += i;
        if (this._trueRegistStone > 127) {
            this._registStone = 127;
        }
        else if (this._trueRegistStone < -128) {
            this._registStone = -128;
        }
        else {
            this._registStone = this._trueRegistStone;
        }
    }
    
    public int getRegistSleep() {
        return this._registSleep;
    }
    
    public void addRegistSleep(final int i) {
        this._trueRegistSleep += i;
        if (this._trueRegistSleep > 127) {
            this._registSleep = 127;
        }
        else if (this._trueRegistSleep < -128) {
            this._registSleep = -128;
        }
        else {
            this._registSleep = this._trueRegistSleep;
        }
    }
    
    public int getRegistFreeze() {
        return this._registFreeze;
    }
    
    public void add_regist_freeze(final int i) {
        this._trueRegistFreeze += i;
        if (this._trueRegistFreeze > 127) {
            this._registFreeze = 127;
        }
        else if (this._trueRegistFreeze < -128) {
            this._registFreeze = -128;
        }
        else {
            this._registFreeze = this._trueRegistFreeze;
        }
    }
    
    public int getRegistSustain() {
        return this._registSustain;
    }
    
    public void addRegistSustain(final int i) {
        this._trueRegistSustain += i;
        if (this._trueRegistSustain > 127) {
            this._registSustain = 127;
        }
        else if (this._trueRegistSustain < -128) {
            this._registSustain = -128;
        }
        else {
            this._registSustain = this._trueRegistSustain;
        }
    }
    
    public int getRegistBlind() {
        return this._registBlind;
    }
    
    public void addRegistBlind(final int i) {
        this._trueRegistBlind += i;
        if (this._trueRegistBlind > 127) {
            this._registBlind = 127;
        }
        else if (this._trueRegistBlind < -128) {
            this._registBlind = -128;
        }
        else {
            this._registBlind = this._trueRegistBlind;
        }
    }
    
    public int getDmgup() {
        return this._dmgup;
    }
    
    public void addDmgup(final int i) {
        this._dmgup += i;
    }
    
    public int getBowDmgup() {
        return this._bowDmgup;
    }
    
    public void addBowDmgup(final int i) {
        this._bowDmgup += i;
    }
    
    public int getHitup() {
        return this._hitup;
    }
    
    public void addHitup(final int i) {
        this._trueHitup += i;
        if (this._trueHitup >= 127) {
            this._hitup = 127;
        }
        else if (this._trueHitup <= -128) {
            this._hitup = -128;
        }
        else {
            this._hitup = this._trueHitup;
        }
    }
    
    public int getBowHitup() {
        return this._bowHitup;
    }
    
    public void addBowHitup(final int i) {
        this._trueBowHitup += i;
        if (this._trueBowHitup >= 127) {
            this._bowHitup = 127;
        }
        else if (this._trueBowHitup <= -128) {
            this._bowHitup = -128;
        }
        else {
            this._bowHitup = this._trueBowHitup;
        }
    }
    
	/**
	 * 取回魔防MR(真實魔防+等級加成+戰鬥特化加成)
	 * 
	 * @return
	 */
	public int getMr() {
		int mr = _trueMr;

		if (this instanceof L1PcInstance) {// 是PC角色
			L1PcInstance pc = (L1PcInstance) this;

			mr += pc.getLevel() / 2;// 等級加成

			switch (pc.guardianEncounter()) {// 戰鬥特化加成
			case 0:
				mr += 3;
				break;
			case 1:
				mr += 6;
				break;
			case 2:
				mr += 9;
				break;
			}
		}

		if (hasSkillEffect(153)) {// 魔法消除
			return mr >> 2;
		}

		return mr;
	}
    
	/**
	 * 取回真實魔防(精神加成+職業基本魔防+其他加成)
	 * 
	 * @return
	 */
	public int getTrueMr() {
		return _trueMr;
	}

	/**
	 * 增加魔防
	 * 
	 * @param i
	 */
	public void addMr(int i) {
		_trueMr += i;
		_trueMr = Math.max(_trueMr, 0);
	}
	/**
	 * 設定魔防
	 * 
	 * @param i
	 */
	public void setMr(int i) {
		_trueMr = i;
		_trueMr = Math.max(_trueMr, 0);
	}
    
    public int getSp() {
        return this.getTrueSp() + this._sp;
    }
    
    public int getTrueSp() {
        return this.getMagicLevel() + this.getMagicBonus();
    }
    
    public void addSp(final int i) {
        this._sp += i;
    }
    
    public boolean isDead() {
        return this._isDead;
    }
    
    public void setDead(final boolean flag) {
        this._isDead = flag;
    }
    
    public int getStatus() {
        return this._status;
    }
    
    public void setStatus(final int i) {
        this._status = i;
    }
    
    public String getTitle() {
        return this._title;
    }
    
    public void setTitle(final String s) {
        this._title = s;
    }
    
    public int getLawful() {
        return this._lawful;
    }
    
    public void setLawful(final int i) {
        this._lawful = i;
    }
    
    public synchronized void addLawful(final int i) {
        this._lawful += i;
        if (this._lawful > 32767) {
            this._lawful = 32767;
        }
        else if (this._lawful < -32768) {
            this._lawful = -32768;
        }
    }
    
    public int getHeading() {
        return this._heading;
    }
    
    public void setHeading(final int i) {
        this._heading = i;
    }
    
    public int getMoveSpeed() {
        return this._moveSpeed;
    }
    
    public void setMoveSpeed(final int i) {
        this._moveSpeed = i;
    }
    
    public int getBraveSpeed() {
        return this._braveSpeed;
    }
    
    public void setBraveSpeed(final int i) {
        this._braveSpeed = i;
    }
    
    public int getTempCharGfx() {
        return this._tempCharGfx;
    }
    
    public void setTempCharGfx(final int i) {
        this._tempCharGfx = i;
    }
    
    public int getGfxId() {
        return this._gfxid;
    }
    
    public void setGfxId(final int i) {
        this._gfxid = i;
    }
    
    public int getMagicLevel() {
        return this.getLevel() / 2;
    }
    
    /**
	 * 額外魔法點數(7.6版)
	 * 
	 * @return
	 */
	public int getMagicBonus() {//修復智力加血
		int i = getInt();
		if (i <= 5) {
			return -2;
		} else if (i <= 8) {
			return -1;
		} else if (i <= 11) {
			return 0;
		} else if (i <= 14) {
			return 1;
		} else if (i <= 17) {
			return 2;
		} else if (i <= 24) {
			return i - 15;
		} else if (i <= 35) {
			return 10;
		} else if (i <= 42) {
			return 11;
		} else if (i <= 49) {
			return 12;
		} else if (i <= 50) {
			return 13;
		} else {
			return 13;
		}
	}
	/**
	 * 額外魔法點數(3.8版)
	 * 
	 * @return
	 */
	public int getMagicBonus380() {
		int i = getInt();
		if (i <= 5) {
			return -2;
		} else if (i <= 8) {
			return -1;
		} else if (i <= 11) {
			return 0;
		} else if (i <= 14) {
			return 1;
		} else if (i <= 17) {
			return 2;
		} else if (i <= 24) {
			return i - 15;
		} else if (i <= 35) {
			return 10;
		} else if (i <= 42) {
			return 11;
		} else if (i <= 49) {
			return 12;
		} else if (i <= 50) {
			return 13;
		} else {
			return 13;
		}
	}
	
    public boolean isInvisble() {
        return this.hasSkillEffect(60) || this.hasSkillEffect(97);
    }
    
    public void healHp(final int pt) {
        this.setCurrentHp(this.getCurrentHp() + pt);
    }
    
    public int getKarma() {
        return this._karma;
    }
    
    public void setKarma(final int karma) {
        this._karma = karma;
    }
    
    public void turnOnOffLight() {
		int lightSize = 0;
		if ((this instanceof L1NpcInstance)) {
			L1NpcInstance npc = (L1NpcInstance) this;
			lightSize = npc.getLightSize();
		}

		for (L1ItemInstance item : getInventory().getItems()) {
			if ((item.getItem().getType2() == 0) && (item.getItem().getType() == 2)) {
				int itemlightSize = item.getItem().getLightRange();
				if ((itemlightSize != 0) && (item.isNowLighting()) && (itemlightSize > lightSize)) {
					lightSize = itemlightSize;
				}
			}

		}

		if (hasSkillEffect(2)) {
			lightSize = 14;
		}

		if ((this instanceof L1PcInstance)) {
			if (ConfigOther.LIGHT) {
				lightSize = 20;
			}
			L1PcInstance pc = (L1PcInstance) this;
			pc.sendPackets(new S_Light(pc.getId(), lightSize));
		}

		if (!isInvisble()) {
			broadcastPacketAll(new S_Light(getId(), lightSize));
		}

		setOwnLightSize(lightSize);
		setChaLightSize(lightSize);
	}
    
    public int getChaLightSize() {
		if (isInvisble()) {
			return 0;
		}
		if (ConfigOther.LIGHT) {
			return 14;
		}
		return _chaLightSize;
	}
    
    public void setChaLightSize(final int i) {
        this._chaLightSize = i;
    }
    
    public int getOwnLightSize() {
        if (this.isInvisble()) {
            return 0;
        }
        if (ConfigOther.LIGHT) {
            return 14;
        }
        return this._ownLightSize;
    }
    
    public void setOwnLightSize(final int i) {
        this._ownLightSize = i;
    }
    
    public int get_tmp_targetid() {
        return this._tmp_targetid;
    }
    
    public void set_tmp_targetid(final int targetid) {
        this._tmp_targetid = targetid;
    }
    
    public int get_tmp_mr() {
        return this._tmp_mr;
    }
    
    public void set_tmp_mr(final int tmp) {
        this._tmp_mr = tmp;
    }
    
    public int get_dodge() {
        return this._dodge_up;
    }
    
    public void add_dodge(final int i) {
        this._dodge_up += i;
        if (this._dodge_up > 10) {
            this._dodge_up = 10;
        }
        else if (this._dodge_up < 0) {
            this._dodge_up = 0;
        }
    }
    
    public int get_dodge_down() {
        return this._dodge_down;
    }
    
    public void add_dodge_down(final int i) {
        this._dodge_down += i;
        if (this._dodge_down > 10) {
            this._dodge_down = 10;
        }
        else if (this._dodge_down < 0) {
            this._dodge_down = 0;
        }
    }
    
    public void addHierarch(final L1HierarchInstance hierarch) {
        this._Hierarch = hierarch;
    }
    
    public void removeHierarch() {
        this._Hierarch = null;
    }
    
    public L1HierarchInstance getHierarchs() {
        return this._Hierarch;
    }
    
    public int getHierarch() {
        return this._hierarch;
    }
    
    public void setHierarch(final int i) {
        this._hierarch = i;
    }
    
    public void set_decay_potion(final boolean b) {
        this._decay_potion = b;
    }
    
    public boolean is_decay_potion() {
        return this._decay_potion;
    }
    
    public String getViewName() {
        return this._name;
    }
    
    public int getInnRoomNumber() {
        return this._innRoomNumber;
    }
    
    public void setInnRoomNumber(final int i) {
        this._innRoomNumber = i;
    }
    
    public boolean checkRoomOrHall() {
        return this._isHall;
    }
    
    public void setHall(final boolean i) {
        this._isHall = i;
    }
    
    public void add_TrueTargetEffect(final L1EffectInstance effect) {
        this._TrueTargetEffectList.add(effect);
    }
    
    public ArrayList<L1EffectInstance> get_TrueTargetEffectList() {
        return this._TrueTargetEffectList;
    }
    
    public void setTripleArrow(final boolean b) {
        this._TripleArrow = b;
    }
    
    public boolean isTripleArrow() {
        return this._TripleArrow;
    }
    
    public boolean isFreezeAtion() {
        return this.hasSkillEffect(87) || this.hasSkillEffect(50) || this.hasSkillEffect(157) || this.isSleeped() || this.isParalyzed() || this.isDead();
    }
    
    public void broadcastPacketBossWeaponAll(final ServerBasePacket packet) {
        try {
            for (final L1PcInstance pc : World.get().getVisiblePlayer(this)) {
                if (pc.get_showId() == this.get_showId() && !L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                    pc.sendPackets(packet);
                }
            }
        }
        catch (Exception e) {
            L1Character._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public int getcancellation() {
        return this._cancellation;
    }
    
    public void setcancellation(final int i) {
        this._cancellation = i;
    }
    
    public int getdouble_score() {
        return this._double_score;
    }
    
    public void setdouble_score(final int i) {
        this._double_score = i;
    }
    
    public int get_poisonStatus2() {
        return this._poisonStatus2;
    }
    
    public void set_poisonStatus2(final int i) {
        this._poisonStatus2 = i;
    }
    
    public int get_poisonStatus7() {
        return this._poisonStatus7;
    }
    
    public void set_poisonStatus7(final int i) {
        this._poisonStatus7 = i;
    }
    
    public boolean isPinkName() {
        return this._isPinkName;
    }
    
    public void setPinkName(final boolean flag) {
        this._isPinkName = flag;
    }
    
    public void setuseitemobjid(final int objid) {
        this._useitemobjid = objid;
    }
    
    public int getuseitemobjid() {
        return this._useitemobjid;
    }
    
    public boolean checkPassable(final int locx, final int locy) {
        boolean isPc = false;
        L1PcInstance pc = null;
        if (this instanceof L1PcInstance) {
            pc = (L1PcInstance)this;
            isPc = true;
        }
        if (isPc && QuestMapTable.get().isQuestMap(this.getMapId())) {
            return false;
        }
        final Collection<L1Object> allObj = World.get().getVisibleObjects(this, 1);
        if (allObj.isEmpty()) {
            return false;
        }
        for (final L1Object obj : allObj) {
            if (obj instanceof L1ItemInstance) {
                continue;
            }
            if (!(obj instanceof L1Character)) {
                continue;
            }
            final L1Character character = (L1Character)obj;
            if (character.isInvisble()) {
                continue;
            }
            if (character instanceof L1MonsterInstance) {
                final L1MonsterInstance mon = (L1MonsterInstance)character;
                if (mon.isDead()) {
                    continue;
                }
                if (mon.getHiddenStatus() == 2) {
                    continue;
                }
                if (mon.getHiddenStatus() == 1) {
                    continue;
                }
                if (mon.getHiddenStatus() == 3) {
                    continue;
                }
                if (mon.isInvisble()) {
                    continue;
                }
                if (isPc) {
                    if (pc.isMoveStatus()) {
                        pc.setMoveErrorCount(0);
                    }
                    else if (pc.getMoveErrorCount() >= 2) {
                        pc.setMoveErrorCount(pc.getMoveErrorCount() - 1);
                        return true;
                    }
                    if (mon.getX() == locx && mon.getY() == locy && mon.getMapId() == this.getMapId()) {
                        pc.setMoveErrorCount(pc.getMoveErrorCount() + 1);
                        pc.setMoveStatus(false);
                    }
                    else {
                        pc.setMoveStatus(true);
                    }
                }
                else if (mon.getX() == locx && mon.getY() == locy) {
                    return true;
                }
            }
            if (!(character instanceof L1PcInstance)) {
                continue;
            }
            final L1PcInstance tgpc = (L1PcInstance)character;
            if (tgpc.isDead()) {
                continue;
            }
            if (tgpc.isInvisble()) {
                continue;
            }
            if (tgpc.isGmInvis()) {
                continue;
            }
            if (tgpc.isGhost()) {
                continue;
            }
            if (tgpc.getX() == locx && tgpc.getY() == locy && tgpc.getMapId() == this.getMapId()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isParalyzed_guaji() {
        return this.hasSkillEffect(50) || this.hasSkillEffect(80) || this.hasSkillEffect(157) || this.hasSkillEffect(87) || this.hasSkillEffect(66) || this.hasSkillEffect(33) || this.hasSkillEffect(4000) || this.hasSkillEffect(192) || this.hasSkillEffect(4017);
    }
}
