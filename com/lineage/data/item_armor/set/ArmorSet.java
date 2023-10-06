package com.lineage.data.item_armor.set;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.ArmorSetTable;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1ArmorSets;

/**
 * 套裝設置抽象接口
 * @author daien
 *
 */
public abstract class ArmorSet {

	private static final Log _log = LogFactory.getLog(ArmorSet.class);
	
	/**
	 * 套裝物品編號陣列
	 * @param pc
	 */
	public abstract int[] get_ids();
	
	/**
	 * 傳回該套裝附加的效果陣列
	 * @return
	 */
	public abstract int[] get_mode();
	
	/**
	 * 套裝完成效果
	 * @param pc
	 */
	public abstract void giveEffect(L1PcInstance pc);

	/**
	 * 套裝解除效果
	 * @param pc
	 */
	public abstract void cancelEffect(L1PcInstance pc);

	/**
	 * 套裝完成
	 * @param pc
	 * @return
	 */
	public abstract boolean isValid(L1PcInstance pc);

	/**
	 * 是否為套裝中組件
	 * @param id
	 * @return
	 */
	public abstract boolean isPartOfSet(int id);

	/**
	 * 是否裝備了相同界指2個
	 * @param pc
	 * @return
	 */
	public abstract boolean isEquippedRingOfArmorSet(L1PcInstance pc);

	/**
	 * 全部套裝設置
	 * @return
	 */
	public static HashMap<Integer, ArmorSet> getAllSet() {
		return _allSet;
	}

	// 全部套裝設置
	private static final HashMap<Integer, ArmorSet> _allSet = new HashMap<Integer, ArmorSet>();
	
	/**
	 * 設置資料初始化
	 */
    public static void load() {
        try {
            L1ArmorSets[] allList;
            for (int length = (allList = ArmorSetTable.get().getAllList()).length, i = 0; i < length; ++i) {
                final L1ArmorSets armorSets = allList[i];
                final int id = armorSets.getId();
                final ArmorSetImpl value = new ArmorSetImpl(id, getArray(id, armorSets.getSets()), armorSets.getEffectId());
                if (armorSets.getPolyId() != -1) {
                    value.addEffect(new EffectPolymorph(armorSets.getPolyId()));
                }
                if (armorSets.getAc() != 0) {
                    value.addEffect(new EffectAc(armorSets.getAc()));
                }
                if (armorSets.getMr() != 0) {
                    value.addEffect(new EffectMr(armorSets.getMr()));
                }
                if (armorSets.getHp() != 0) {
                    value.addEffect(new EffectHp(armorSets.getHp()));
                }
                if (armorSets.getHpr() != 0) {
                    value.addEffect(new EffectHpR(armorSets.getHpr()));
                }
                if (armorSets.getMp() != 0) {
                    value.addEffect(new EffectMp(armorSets.getMp()));
                }
                if (armorSets.getMpr() != 0) {
                    value.addEffect(new EffectMpR(armorSets.getMpr()));
                }
                if (armorSets.getDefenseWater() != 0) {
                    value.addEffect(new EffectDefenseWater(armorSets.getDefenseWater()));
                }
                if (armorSets.getDefenseWind() != 0) {
                    value.addEffect(new EffectDefenseWind(armorSets.getDefenseWind()));
                }
                if (armorSets.getDefenseFire() != 0) {
                    value.addEffect(new EffectDefenseFire(armorSets.getDefenseFire()));
                }
                if (armorSets.getDefenseEarth() != 0) {
                    value.addEffect(new EffectDefenseEarth(armorSets.getDefenseEarth()));
                }
                if (armorSets.get_regist_stun() != 0) {
                    value.addEffect(new EffectRegist_Stun(armorSets.get_regist_stun()));
                }
                if (armorSets.get_regist_stone() != 0) {
                    value.addEffect(new EffectRegist_Stone(armorSets.get_regist_stone()));
                }
                if (armorSets.get_regist_sleep() != 0) {
                    value.addEffect(new EffectRegist_Sleep(armorSets.get_regist_sleep()));
                }
                if (armorSets.get_regist_freeze() != 0) {
                    value.addEffect(new EffectRegist_Freeze(armorSets.get_regist_freeze()));
                }
                if (armorSets.get_regist_sustain() != 0) {
                    value.addEffect(new EffectRegist_Sustain(armorSets.get_regist_sustain()));
                }
                if (armorSets.get_regist_blind() != 0) {
                    value.addEffect(new EffectRegist_Blind(armorSets.get_regist_blind()));
                }
                if (armorSets.getStr() != 0) {
                    value.addEffect(new EffectStat_Str(armorSets.getStr()));
                }
                if (armorSets.getDex() != 0) {
                    value.addEffect(new EffectStat_Dex(armorSets.getDex()));
                }
                if (armorSets.getCon() != 0) {
                    value.addEffect(new EffectStat_Con(armorSets.getCon()));
                }
                if (armorSets.getWis() != 0) {
                    value.addEffect(new EffectStat_Wis(armorSets.getWis()));
                }
                if (armorSets.getCha() != 0) {
                    value.addEffect(new EffectStat_Cha(armorSets.getCha()));
                }
                if (armorSets.getIntl() != 0) {
                    value.addEffect(new EffectStat_Int(armorSets.getIntl()));
                }
                if (armorSets.get_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Modifier_dmg(armorSets.get_modifier_dmg()));
                }
                if (armorSets.get_reduction_dmg() != 0) {
                    value.addEffect(new Effect_Reduction_dmg(armorSets.get_reduction_dmg()));
                }
                if (armorSets.get_magic_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Magic_modifier_dmg(armorSets.get_magic_modifier_dmg()));
                }
                if (armorSets.get_magic_reduction_dmg() != 0) {
                    value.addEffect(new Effect_Magic_reduction_dmg(armorSets.get_magic_reduction_dmg()));
                }
                if (armorSets.get_bow_modifier_dmg() != 0) {
                    value.addEffect(new Effect_Bow_modifier_dmg(armorSets.get_bow_modifier_dmg()));
                }
                if (armorSets.get_haste() != 0) {
                    value.addEffect(new EffectHaste(armorSets.get_haste()));
                }
                if (armorSets.get_sp() != 0) {
                    value.addEffect(new EffectSp(armorSets.get_sp()));
                }
                if (armorSets.get_hit_modifier() != 0) {
                    value.addEffect(new Effect_Hit_modifier(armorSets.get_hit_modifier()));
                }
                if (armorSets.get_bow_hit_modifier() != 0) {
                    value.addEffect(new Effect_Bow_Hit_modifier(armorSets.get_bow_hit_modifier()));
                }
                if (armorSets.get_magiccritical_chance() != 0) {
                    value.addEffect(new Effect_MagicCritical_chance(armorSets.get_magiccritical_chance()));
                }
                if (armorSets.getweaponskillpro() != 0) {
                    value.addEffect(new Effect_getweaponskillpro(armorSets.getweaponskillpro()));
                }
                if (armorSets.getweaponskilldmg() != 0) {
                    value.addEffect(new Effect_getweaponskilldmg(armorSets.getweaponskilldmg()));
                }
                if (armorSets.getInterval1() != 0 && armorSets.getEffectId1() != 0) {
                    value.addEffect(new Effect_SkillSound(armorSets.getEffectId1(), armorSets.getInterval1()));
                }
                ArmorSet._allSet.put(armorSets.getId(), value);
            }
        }
        catch (Exception e) {
            ArmorSet._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return;
        }
        finally {
            ItemTable.get().se_mode();
        }
        ItemTable.get().se_mode();
        ItemTable.get().se_mode();
    }

	/**
	 * 將文字串 轉為數字陣列
	 * @param id 套件編號
	 * @param s 轉換的字串
	 * @return
	 */
	private static int[] getArray(final int id, final String s) {
		// 根据给定正则表达式的匹配拆分此字符串。
		final String[] clientStrAry = s.split(",");
		final int[] array = new int[clientStrAry.length];
		try {
			for (int i = 0; i < clientStrAry.length; i++) {
				array[i] = Integer.parseInt(clientStrAry[i]);
			}
			
		} catch (final Exception e) {
			_log.error("編號:" + id + " 套件設置錯誤!!檢查資料庫!!", e);
		}
		return array;
	}
	
	/**
	 * 將文字串 轉為數字陣列
	 * @param s
	 * @param sToken
	 * @return
	 */
	/*private static int[] getArray(final String s, final String sToken) {
		final StringTokenizer st = new StringTokenizer(s, sToken);
		// 计算在生成异常之前可以调用此 tokenizer 的 nextToken 方法的次数。当前位置没有提前。
		final int size = st.countTokens();
		String temp = null;
		final int[] array = new int[size];
		for (int i = 0; i < size; i++) {
			temp = st.nextToken();
			array[i] = Integer.parseInt(temp);
		}
		return array;
	}*/
}