package com.lineage.server.clientpackets;

import java.util.Collection;
import java.util.EnumMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigOther;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_ToGmMessage;
import com.lineage.server.world.World;
import com.lineage.william.PolySpeedSkill;


/**
 * 加速器檢測
 */
public class AcceleratorChecker {

	private static final Log _log = LogFactory.getLog(AcceleratorChecker.class);

	private final L1PcInstance _pc;

	private int _injusticeCount;

	private int _justiceCount;

	private static final int INJUSTICE_COUNT_LIMIT = ConfigOther.INJUSTICE_COUNT;// 允許錯誤次數

	private static final int JUSTICE_COUNT_LIMIT = ConfigOther.JUSTICE_COUNT;// 必須正常次數

	// 加大的允許範圍質

	public static double CHECK_STRICTNESS = ConfigOther.SPEED_TIME;

	private final EnumMap<ACT_TYPE, Long> _actTimers = new EnumMap<ACT_TYPE, Long>(
			ACT_TYPE.class);

	private final EnumMap<ACT_TYPE, Long> _checkTimers = new EnumMap<ACT_TYPE, Long>(
			ACT_TYPE.class);

//	public static enum ACT_TYPE {
//		MOVE, ATTACK, SPELL_DIR, SPELL_NODIR
//	}
	
	public enum ACT_TYPE
    {
        MOVE("MOVE", 0, "MOVE", 0), 
        ATTACK("ATTACK", 1, "ATTACK", 1), 
        SPELL_DIR("SPELL_DIR", 2, "SPELL_DIR", 2), 
        SPELL_NODIR("SPELL_NODIR", 3, "SPELL_NODIR", 3);
        
        private ACT_TYPE(final String name, final int ordinal, final String s, final int n) {
        }
    }

	public static final int R_OK = 0;// 正常

	public static final int R_DETECTED = 1;// 異常

	public static final int R_DISCONNECTED = 2;// 連續異常
	
	private static final double HASTE_RATE = 0.755; // 速度 * 1.33

	private static final double WAFFLE_RATE = 0.87; // 速度 * 1.15

	private static final double DOUBLE_HASTE_RATE = 0.375; // 速度 * 2.66
	
	private static final double STANDARD_RATE = 1.00;

	public AcceleratorChecker(final L1PcInstance pc) {
		this._pc = pc;
		this._injusticeCount = 0;
		this._justiceCount = 0;
		final long now = System.currentTimeMillis();
		for (final ACT_TYPE each : ACT_TYPE.values()) {
			this._actTimers.put(each, now);
			this._checkTimers.put(each, now);
		}
	}

	public static void Setspeed() {
		CHECK_STRICTNESS = ConfigOther.SPEED_TIME;

	}

	/**
	 * 限制行動
	 */
	private void doDisconnect() {
		try {
			final StringBuilder name = new StringBuilder();
			name.append(this._pc.getName());
			// 945：發現外掛程式因此強制中斷遊戲
			_log.error("角色速度異常:(" + _pc.getName() + ")");
			this._pc.sendPackets(new S_Paralysis(6, true));
			this._pc.sendPackets(new S_SystemMessage("\\aG加速器檢測警告"
					+ ConfigOther.Stint + "秒後解除您的行動。"));
			try {
				Thread.sleep(ConfigOther.Stint * 1000);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			this._pc.sendPackets(new S_Paralysis(6, false));

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 斷開用戶
	 */
	private void doDisconnect2() {
		try {
			final StringBuilder name = new StringBuilder();
			name.append(this._pc.getName());
			// 945：發現外掛程式因此強制中斷遊戲
			this._pc.sendPackets(new S_Disconnect());

			this._pc.getNetConnection().kick();// 中斷
			toGmKickMsg(name.toString());

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 麻痺人物
	 */
	private void doDisconnect3() {
		try {
			final StringBuilder name = new StringBuilder();
			name.append(this._pc.getName());
			// 945：發現外掛程式因此強制中斷遊戲
			_log.error("角色速度異常:(" + _pc.getName() + ")");
			this._pc.sendPackets(new S_Paralysis(1, true));
			this._pc.sendPackets(new S_SystemMessage("\\aG加速器檢測警告"
					+ ConfigOther.Stint + "秒後解除您的麻痺。"));
			try {
				Thread.sleep(ConfigOther.Stint * 1000);
			} catch (Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			this._pc.sendPackets(new S_Paralysis(1, false));

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 通知GM
	 */
	private void toGmErrMsg(final String name, int count) {
		try {
			if (count >= 5) {
				final Collection<L1PcInstance> allPc = World.get()
						.getAllPlayers();
				for (L1PcInstance tgpc : allPc) {
					if (tgpc.isGm()) {
						tgpc.sendPackets(new S_ToGmMessage("人物:" + name
								+ " 速度異常!(" + count + "次)"));
					}
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 通知GM
	 */
	private void toGmKickMsg(final String name) {
		try {
			final Collection<L1PcInstance> allPc = World.get().getAllPlayers();
			for (L1PcInstance tgpc : allPc) {
				if (tgpc.isGm()) {
					tgpc.sendPackets(new S_ToGmMessage("人物:" + name
							+ " 速度異常斷線!"));
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 封包速度正常與否的檢測
	 * 
	 * @param type
	 *            檢測類型
	 * @return 0:正常 1:異常 2:連續異常
	 */
	public int checkInterval(final ACT_TYPE type) {
		if (!ConfigOther.SPEED) {
			return R_OK;
		}
		int result = R_OK;
		try {
			final long now = System.currentTimeMillis();
			long interval = now - this._actTimers.get(type);
			final int rightInterval = this.getRightInterval(type);

			interval *= CHECK_STRICTNESS;

			if ((0 < interval) && (interval < rightInterval)) {
				this._injusticeCount++;
				toGmErrMsg(this._pc.getName(), this._injusticeCount);
				this._justiceCount = 0;
				if (this._injusticeCount >= INJUSTICE_COUNT_LIMIT) {// 允許錯誤次數
					if (ConfigOther.opein == 1) {
						this.doDisconnect();
					}
					if (ConfigOther.opein == 2) {
						this.doDisconnect2();
					}
					if (ConfigOther.opein == 3) {
						this.doDisconnect3();
					}
					return R_DISCONNECTED;
				}
				result = R_DETECTED;

			} else if (interval >= rightInterval) {
				this._justiceCount++;
				if (this._justiceCount >= JUSTICE_COUNT_LIMIT) {// 連續正常 恢復計算
					this._injusticeCount = 0;
					this._justiceCount = 0;
				}
			}

			this._actTimers.put(type, now);

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	/**
	 * 正常的速度
	 * 
	 * @param type
	 *            檢測類型
	 * @return 正常應該接收的速度(MS)
	 */
	public int getRightInterval(final ACT_TYPE type) {
		int interval = 0;

		// 動作判斷
		switch (type) {
		case ATTACK:
			interval = SprTable.get().getAttackSpeed(_pc.getTempCharGfx(), _pc.getCurrentWeapon() + 1);
			break;
			
		case MOVE:
			interval = SprTable.get().getMoveSpeed(_pc.getTempCharGfx(), _pc.getCurrentWeapon());			
			break;
			
		case SPELL_DIR:
			interval = SprTable.get().getDirSpellSpeed(_pc.getTempCharGfx());
			break;
			
		case SPELL_NODIR:
			interval = SprTable.get().getNodirSpellSpeed(_pc.getTempCharGfx());
			break;
		default:
			return 0;
		}
		return intervalR(type, interval);
	}

	private int intervalR(final ACT_TYPE type, int interval) {
		try {
			// 二段加速
			switch (_pc.getBraveSpeed()) {
			case 1: // 勇水
				interval *= HASTE_RATE; // 攻速、移速 * 1.33倍
				break;
			case 2: // 生命之樹果實
				if (type.equals(ACT_TYPE.MOVE) && this._pc.isRibrave()) {
					interval *= WAFFLE_RATE; // 移速 * 1.15倍
				}
				break;
			case 3: // 精餅
				interval *= WAFFLE_RATE; // 攻速、移速 * 1.15倍
				break;
			case 4: // 神疾、風走、行走
				if (type.equals(ACT_TYPE.MOVE) && this._pc.isFastMovable()) {
					interval *= HASTE_RATE; // 移速 * 1.33倍
				}
				break;
			case 5: // 超級加速
				interval *= DOUBLE_HASTE_RATE; // 攻速、移速 * 2.66倍
				break;
			case 6: // 血之渴望
				if (type.equals(ACT_TYPE.ATTACK)) {
					interval *= HASTE_RATE; // 攻速 * 1.33倍
				}
				break;
			default:
				break;
			}
			
			// 三段加速
			if (_pc.isBraveX()) { // 攻速、移速 * 1.15倍
				interval *= WAFFLE_RATE;
			}
			// 風之枷鎖
			if (_pc.isWindShackle() && !type.equals(ACT_TYPE.MOVE)) { // 攻速or施法速度 /
																		// 2倍
				interval /= 2;
			}
			if (_pc.getMapId() == 5143) { // 寵物競速例外
				interval *= 0.1;
			}
			
			if (this._pc.isGm()) {
				interval /= 99999999999.0;
			}

			if (this._pc.isHaste()) {
				interval *= 0.755;// 0.755
			}

			if (type.equals(ACT_TYPE.MOVE) && this._pc.isRibrave()) {
				interval *= WAFFLE_RATE; // 移速 * 1.15倍
			}
			
			if (type.equals(ACT_TYPE.MOVE) && this._pc.isFastMovable()) {
				interval *= 0.665;// 0.665
			}

			if (type.equals(ACT_TYPE.ATTACK) && this._pc.isFastAttackable()) {
				interval *= 0.775;// 0.775
			}

			if (this._pc.isBrave()) {
				interval *= 0.755;// 0.755
			}

			if (this._pc.isBraveX()) {
				interval *= 0.87;// 0.755
			}

			if (this._pc.isElfBrave()) {
				interval *= 0.87;// 0.855
			}

			if (type.equals(ACT_TYPE.ATTACK) && this._pc.isElfBrave()) {
				interval *= 0.9;// 0.9
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return interval;
	}
}
