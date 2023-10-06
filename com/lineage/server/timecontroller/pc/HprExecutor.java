package com.lineage.server.timecontroller.pc;

import static com.lineage.server.model.Instance.L1PcInstance.REGENSTATE_NONE;
import static com.lineage.server.model.skill.L1SkillId.ADLV80_2;
import static com.lineage.server.model.skill.L1SkillId.COOKING_1_5_N;
import static com.lineage.server.model.skill.L1SkillId.COOKING_1_5_S;
import static com.lineage.server.model.skill.L1SkillId.COOKING_2_4_N;
import static com.lineage.server.model.skill.L1SkillId.COOKING_2_4_S;
import static com.lineage.server.model.skill.L1SkillId.COOKING_3_6_N;
import static com.lineage.server.model.skill.L1SkillId.COOKING_3_6_S;
import static com.lineage.server.model.skill.L1SkillId.NATURES_TOUCH;
import static com.lineage.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;
import com.lineage.server.datatables.RevertHpMp;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Object;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HprExecutor
{
  private static final Log _log = LogFactory.getLog(HprExecutor.class);

  private static final Map<Integer, Integer> _skill = new HashMap<Integer, Integer>();

  private static final Map<Integer, Integer> _mapIdU = new HashMap<Integer, Integer>();

  private static final Map<Integer, Integer> _mapIdD = new HashMap<Integer, Integer>();
  private static HprExecutor _instance;

protected static HprExecutor get() {
  if (_instance == null) {
    _instance = new HprExecutor();
  }
  return _instance;
}

	private HprExecutor() {
	// 技能回復HP增加
	_skill.put(NATURES_TOUCH, 15);
	_skill.put(COOKING_1_5_N, 3);
	_skill.put(COOKING_1_5_S, 3);
	_skill.put(COOKING_2_4_N, 2);
	_skill.put(COOKING_2_4_S, 2);
	_skill.put(COOKING_3_6_N, 2);
	_skill.put(COOKING_3_6_S, 2);
    _mapIdD.put(Integer.valueOf(410), Integer.valueOf(-10));
    _mapIdD.put(Integer.valueOf(2000), Integer.valueOf(-10));
    _mapIdD.put(Integer.valueOf(306), Integer.valueOf(-10));
   	}
  
/**
 * PC HP回復執行 判斷
 * @param tgpc
 * @return true:執行 false:不執行
 */
protected boolean check(final L1PcInstance tgpc) {
	try {
		// 人物為空
		if (tgpc == null) {
			return false;
		}
		
		// 人物登出
		if (tgpc.getOnlineStatus() == 0) {
			return false;
		}
		
		// 中斷連線
		if (tgpc.getNetConnection() == null) {
			return false;
		}
		
		// 死亡
		if (tgpc.isDead()) {
			return false;
		}
		
		// 傳送狀態
		if (tgpc.isTeleport()) {
			return false;
		}

		// 人物在降低HP地圖中
		final Integer dhp = _mapIdD.get(new Integer(tgpc.getMapId()));
		if (dhp != null) {
			return true;
		}
		
		// 海底
		if (isUnderwater(tgpc)) {
			return true;
		}
		
		// HP已滿
		if (tgpc.getCurrentHp() >= tgpc.getMaxHp()) {
			return false;
		}
		
	} catch (final Exception e) {
		_log.error(e.getLocalizedMessage(), e);
		return false;
	}
	return true;
}

protected void checkRegenHp(final L1PcInstance tgpc) {
	try {
		tgpc.set_hpRegenType(tgpc.hpRegenType() + tgpc.getHpRegenState());
		tgpc.setRegenState(REGENSTATE_NONE);

		if (tgpc.isRegenHp()) {
			regenHp(tgpc);
		}
		
	} catch (final Exception e) {
		_log.error(e.getLocalizedMessage(), e);
	}
}

/**
 * 執行HP恢復
 * @param tgpc
 */
private static void regenHp(L1PcInstance tgpc) {
  tgpc.set_hpRegenType(0);
  int maxBonus = 0;

	// 等級大於11
	if (tgpc.getLevel() > 11) {
		//7.6版體質增加HP恢復量
		if (tgpc.getCon() <= 11) {//體質11以下
			maxBonus = 5;
			
		} else if (tgpc.getCon() >= 12) {//體質12以上
			maxBonus = (tgpc.getCon() - 12) / 2 + 6;
		}
		
		if (tgpc.getBaseCon() >= 25 && tgpc.getBaseCon() <= 44) {//純體質25~44之間
			maxBonus += (tgpc.getBaseCon() - 15) / 10;//取商數
			
		} else if (tgpc.getBaseCon() >= 45) {//純體質45以上 HP恢復量共加5
			maxBonus += 5;
		}
	}
	
	maxBonus = Math.max(1, maxBonus);//取回最大

  Random random = new Random();
  int bonus = random.nextInt(maxBonus) + 1;//HP恢復量

  if ((!tgpc.getSkillisEmpty()) && (tgpc.getSkillEffect().size() > 0)) {
  	try {
  		for (Integer skillid : _skill.keySet()) {
  			if (tgpc.hasSkillEffect(skillid.intValue())) {
  				Integer integer = (Integer)_skill.get(skillid);
  				if (integer != null) {
  					bonus += integer.intValue();
  				}
  			}
  		}
  	} catch (ConcurrentModificationException localConcurrentModificationException) {
  	} catch (Exception e) {
  		_log.error(e.getLocalizedMessage(), e);
  	}
  }
  
  for (int i = 0; i <= RevertHpMp.get().RevertSize(); i++) {
		final RevertHpMp r = 
				RevertHpMp.get().getRevert(i);
		if (r != null) {
			if (tgpc.getMapId() == r.getMapId()) {
				if (r.getSx() == 0) {
					bonus += r.getHpr();
					break;
				} else {
					if (tgpc.getX() >= r.getSx() && tgpc.getX() <= r.getEx() 
							&& tgpc.getY() >= r.getSy() && tgpc.getY() <= r.getEy()) {
						bonus += r.getHpr();
						break;
					}
				}
			}
		}
	}
  Integer rhp = (Integer)_mapIdU.get(new Integer(tgpc.getMapId()));
  if (rhp != null) {
  	bonus += rhp.intValue();
  }

  boolean inLifeStream = false;

  if (isPlayerInLifeStream(tgpc)) {
  	inLifeStream = true;
  	bonus += 3;
  }
      
  bonus += tgpc.getHpr();//其他HP恢復量增加
  
  int equipHpr = tgpc.getInventory().hpRegenPerTick();//裝備增加HPR
  bonus += equipHpr;
  
  if (120 <= tgpc.getInventory().getWeight240()) {//負重狀態下
  	if (tgpc.getBaseCon() >= 45) {//純體質45以上
  		bonus /= 2;//恢復量減半
  	}
  }

  int newHp = tgpc.getCurrentHp() + bonus;
  
  if (isUnderwater(tgpc) /*|| tgpc.getMaxWeight() < tgpc.getInventory().getWeight240()*/) {//水裡自動扣血
  	newHp -= 20;
  }

  Integer dhp = (Integer)_mapIdD.get(new Integer(tgpc.getMapId()));
  if ((dhp != null) && (!inLifeStream)) {//特定地圖自動扣血
  	newHp += dhp.intValue();
  }

  newHp = Math.max(newHp, 0);//取回最大

  tgpc.setCurrentHp(newHp);
}

	/**
	 * 是否執行水裡自動扣血
	 * @param pc
	 * @return
	 */
private static boolean isUnderwater(final L1PcInstance pc) {
	if (pc.getInventory().checkEquipped(20207)) {// 深水長靴
		return false;
	}
	if (pc.hasSkillEffect(STATUS_UNDERWATER_BREATH)) {// 伊娃的祝福藥水效果
		return false;
	}
	if (pc.hasSkillEffect(ADLV80_2)) {// 莎爾的祝福效果
		return false;
	}
	if (pc.getInventory().checkEquipped(21048)// 修好的戒指
			&& pc.getInventory().checkEquipped(21049)// 修好的耳環
			&& pc.getInventory().checkEquipped(21050)// 修好的項鏈
			) {
		return false;
	}

	return pc.getMap().isUnderwater();
} 
/**
 * 法師技能(治癒能量風暴)
 *
 * @param pc
 *            PC
 * @return true PC在4格範圍內
 */
private static boolean isPlayerInLifeStream(final L1PcInstance pc) {
	for (final L1Object object : pc.getKnownObjects()) {
		if ((object instanceof L1EffectInstance) == false) {
			continue;
		}
		final L1EffectInstance effect = (L1EffectInstance) object;
		// 法師技能(治癒能量風暴)
		if (effect.getNpcId() == 81169) {
			if (effect.getLocation().getTileLineDistance(pc.getLocation()) < 4) {
				return true;
			}
		}
	}
	return false;
}
/*     */ }