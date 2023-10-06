package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.*;

import com.lineage.server.datatables.lock.SpawnBossReading;

import java.util.HashMap;

public class L1AttackList
{
  /**
   * 料理命中降低或追加(_weaponType != 20) && (_weaponType != 62)近距離武器
   */
  protected static final HashMap<Integer, Integer> SKU1 = new HashMap<Integer, Integer>();

  /**
   * 料理命中降低或追加(_weaponType == 20) && (_weaponType == 62)遠距離武器
   */
  protected static final HashMap<Integer, Integer> SKU2 = new HashMap<Integer, Integer>();

  /**
   * NPC需附加技能才可攻擊
   */
  public static final HashMap<Integer, Integer> SKNPC = new HashMap<Integer, Integer>();

  /**
   * NPC指定外型不可攻擊
   */
  public static final HashMap<Integer, Integer> PLNPC = new HashMap<Integer, Integer>();

  /**
   * 料理追加傷害(_weaponType != 20) && (_weaponType != 62)近距離武器
   */
  protected static final HashMap<Integer, Integer> SKD1 = new HashMap<Integer, Integer>();

  /**
   * 料理追加傷害(_weaponType == 20) && (_weaponType == 62)遠距離武器
   */
  protected static final HashMap<Integer, Integer> SKD2 = new HashMap<Integer, Integer>();

  /**
   * 傷害降低
   */
  public static final HashMap<Integer, Integer> SKD3 = new HashMap<Integer, Integer>();

  /**
   * 受到下列法術效果  傷害為0
   */
  public static final HashMap<Integer, Integer> SKM0 = new HashMap<Integer, Integer>();

  /**
   * 力量增加命中(7.6)
   */
  protected static final HashMap<Integer, Integer> STRH = new HashMap<Integer, Integer>();
  
  /**
   * 力量增加傷害(7.6)
   */
  protected static final HashMap<Integer, Integer> STRD = new HashMap<Integer, Integer>();
  
  /**
   * 力量增加近距離爆擊機率(7.6)
   */
  protected static final HashMap<Integer, Integer> STRCRI = new HashMap<Integer, Integer>();

  /**
   * 敏捷增加命中(7.6)
   */
  protected static final HashMap<Integer, Integer> DEXH = new HashMap<Integer, Integer>();

  /**
   * 敏捷增加傷害(7.6)
   */
  protected static final HashMap<Integer, Integer> DEXD = new HashMap<Integer, Integer>();
  
  /**
   * 敏捷增加遠距離爆擊機率(7.6)
   */
  protected static final HashMap<Integer, Integer> DEXCRI = new HashMap<Integer, Integer>();

  /**
   * 智力增加魔法命中(7.6)
   */
  public static final HashMap<Integer, Integer> INTH = new HashMap<Integer, Integer>();
  
  /**
   * 智力增加魔法傷害(7.6)
   */
  protected static final HashMap<Integer, Integer> INTD = new HashMap<Integer, Integer>();

  /**
   * 智力增加魔法爆擊機率(7.6)
   */
  protected static final HashMap<Integer, Integer> INTCRI = new HashMap<Integer, Integer>();
  
  /**
   * 智力減少MP消耗量(7.6)
   */
  public static final HashMap<Integer, Integer> INTMPCONSUME = new HashMap<Integer, Integer>();

  /**
   * NPC抵抗技能(NPCID / 技能編號) 列表中該技能對該NPC施展失敗
   */
  protected static final HashMap<Integer, Integer[]> DNNPC = new HashMap<Integer, Integer[]>();

  /**
   * 安全區域不可使用的技能
   */
  protected static final HashMap<Integer, Boolean> NZONE = new HashMap<Integer, Boolean>();

	// MR傷害降低
 public static final HashMap<Integer, Double[]> MRDMG = new HashMap<Integer, Double[]>();

  public static void load() {		
	// BOSS抵抗技能
    for (Integer bossid : SpawnBossReading.get().bossIds()) {
    	Integer[] ids = { 
			new Integer(FOG_OF_SLEEPING), 
			new Integer(ICE_LANCE), 
			new Integer(CURSE_PARALYZE), 
			new Integer(EARTH_BIND), 
			new Integer(DARK_BLIND), 
			new Integer(DARKNESS), 
			new Integer(CURSE_BLIND), 
			new Integer(SILENCE),
			new Integer(DISEASE),
			new Integer(WEAPON_BREAK),
        	new Integer(GUARD_BRAKE),
        	new Integer(RESIST_FEAR),
        	new Integer(HORROR_OF_DEATH),
        	new Integer(CONFUSION),
        	new Integer(PHANTASM),
        	new Integer(PANIC),    
			new Integer(FREEZING_BLIZZARD)// 冰雪颶風
    	};

    	if (DNNPC.get(bossid) == null) {
    		DNNPC.put(bossid, ids);
    	}
    }
	// MR傷害降低
	for (int mr = 0; mr < 255; mr++) {
		double mrFloor = 0;
		double mrCoefficient = 0;
		if (mr == 0) {
			mrFloor = 1.0;
			mrCoefficient = 1.0;

		} else if (mr > 0 && mr <= 50) {
			mrFloor = 2.0;
			mrCoefficient = 1.0;

		} else if (mr > 50 && mr <= 100) {
			mrFloor = 3.0;
			mrCoefficient = 0.9;

		} else if (mr > 100 && mr <= 120) {
			mrFloor = 4.0;
			mrCoefficient = 0.9;

		} else if (mr > 120 && mr <= 140) {
			mrFloor = 5.0;
			mrCoefficient = 0.8;

		} else if (mr > 140 && mr <= 160) {
			mrFloor = 6.0;
			mrCoefficient = 0.8;

		} else if (mr > 160 && mr <= 180) {
			mrFloor = 7.0;
			mrCoefficient = 0.7;

		} else if (mr > 180 && mr <= 200) {
			mrFloor = 8.0;
			mrCoefficient = 0.7;

		} else if (mr > 200 && mr <= 220) {
			mrFloor = 9.0;
			mrCoefficient = 0.6;

		} else if (mr > 220 && mr <= 240) {
			mrFloor = 10.0;
			mrCoefficient = 0.6;

		} else if (mr > 240) {
			mrFloor = 11.0;
			mrCoefficient = 0.5;
		}
		MRDMG.put(new Integer(mr), new Double[] { mrFloor, mrCoefficient });
	}
    // 安全區域不可使用的技能
	NZONE.put(new Integer(20), Boolean.valueOf(false));//闇盲咒術
    NZONE.put(new Integer(27), Boolean.valueOf(false));//壞物術
    NZONE.put(new Integer(29), Boolean.valueOf(false));//緩速術
    NZONE.put(new Integer(33), Boolean.valueOf(false));//木乃伊的詛咒
    NZONE.put(new Integer(39), Boolean.valueOf(false));//魔力奪取
    NZONE.put(new Integer(40), Boolean.valueOf(false));//黑闇之影
    NZONE.put(new Integer(44), Boolean.valueOf(false));//魔法相消術
    NZONE.put(new Integer(47), Boolean.valueOf(false));//弱化術
    NZONE.put(new Integer(ICE_LANCE), Boolean.valueOf(false));//冰矛圍籬
    NZONE.put(new Integer(56), Boolean.valueOf(false));//疾病術
    NZONE.put(new Integer(64), Boolean.valueOf(false));//魔法封印
    NZONE.put(new Integer(66), Boolean.valueOf(false));//沉睡之霧
    NZONE.put(new Integer(71), Boolean.valueOf(false));//藥水霜化術
    NZONE.put(new Integer(76), Boolean.valueOf(false));//集體緩速術
    NZONE.put(new Integer(SHAPE_CHANGE), Boolean.valueOf(false));//變形術
    NZONE.put(new Integer(87), Boolean.valueOf(false));//衝擊之暈
    NZONE.put(new Integer(FREEZING_BLIZZARD), false);// 冰雪颶風
    NZONE.put(new Integer(133), Boolean.valueOf(false));//弱化屬性
    NZONE.put(new Integer(145), Boolean.valueOf(false));//釋放元素
    NZONE.put(new Integer(152), Boolean.valueOf(false));//地面障礙
    NZONE.put(new Integer(153), Boolean.valueOf(false));//魔法消除
    NZONE.put(new Integer(157), Boolean.valueOf(false));//大地屏障
    NZONE.put(new Integer(161), Boolean.valueOf(false));//封印禁地
    NZONE.put(new Integer(167), Boolean.valueOf(false));//風之枷鎖
    NZONE.put(new Integer(173), Boolean.valueOf(false));//污濁之水
    NZONE.put(new Integer(174), Boolean.valueOf(false));//精準射擊
      
    NZONE.put(new Integer(103), Boolean.valueOf(false));//暗黑盲咒 
    NZONE.put(new Integer(112), Boolean.valueOf(false));//破壞盔甲

    NZONE.put(new Integer(193), Boolean.valueOf(false));//驚悚死神
    NZONE.put(new Integer(188), Boolean.valueOf(false));//恐懼無助
    NZONE.put(new Integer(183), Boolean.valueOf(false));//護衛毀滅
    
    NZONE.put(new Integer(202), Boolean.valueOf(false));//混亂
    NZONE.put(new Integer(212), Boolean.valueOf(false));//幻想
    NZONE.put(new Integer(217), Boolean.valueOf(false));//恐慌
    
    NZONE.put(new Integer(228), Boolean.valueOf(false));//拘束移動
    NZONE.put(new Integer(230), Boolean.valueOf(false));//亡命之徒
    
    // 料理追加傷害(_weaponType != 20) && (_weaponType != 62)近距離武器
    SKD1.put(new Integer(3016), new Integer(1));
    SKD1.put(new Integer(3024), new Integer(1));
    SKD1.put(new Integer(3016), new Integer(1));
    SKD1.put(new Integer(3016), new Integer(1));
    SKD1.put(new Integer(COOKING_4_0_N), new Integer(2));
    
    // 料理追加傷害(_weaponType == 20) && (_weaponType == 62)遠距離武器
    SKD2.put(new Integer(3019), new Integer(1));
    SKD2.put(new Integer(3027), new Integer(1));
    SKD2.put(new Integer(3032), new Integer(1));
    SKD2.put(new Integer(3040), new Integer(1));
    SKD2.put(new Integer(COOKING_4_1_N), new Integer(2));

    // 傷害降低
    SKD3.put(new Integer(3008), new Integer(-5));
    SKD3.put(new Integer(3009), new Integer(-5));
    SKD3.put(new Integer(3010), new Integer(-5));
    SKD3.put(new Integer(3011), new Integer(-5));
    SKD3.put(new Integer(3012), new Integer(-5));
    SKD3.put(new Integer(3013), new Integer(-5));
    SKD3.put(new Integer(3014), new Integer(-5));
    SKD3.put(new Integer(3024), new Integer(-5));
    SKD3.put(new Integer(3025), new Integer(-5));
    SKD3.put(new Integer(3026), new Integer(-5));
    SKD3.put(new Integer(3027), new Integer(-5));
    SKD3.put(new Integer(3028), new Integer(-5));
    SKD3.put(new Integer(3029), new Integer(-5));
    SKD3.put(new Integer(3030), new Integer(-5));
    SKD3.put(new Integer(3040), new Integer(-5));
    SKD3.put(new Integer(3041), new Integer(-5));
    SKD3.put(new Integer(3042), new Integer(-5));
    SKD3.put(new Integer(3043), new Integer(-5));
    SKD3.put(new Integer(3044), new Integer(-5));
    SKD3.put(new Integer(3045), new Integer(-5));
    SKD3.put(new Integer(3046), new Integer(-5));
    SKD3.put(new Integer(3015), new Integer(-5));
    SKD3.put(new Integer(3031), new Integer(-5));
    SKD3.put(new Integer(3047), new Integer(-5));
    SKD3.put(new Integer(COOKING_4_0_N), new Integer(-2));
    SKD3.put(new Integer(COOKING_4_1_N), new Integer(-2));
    SKD3.put(new Integer(COOKING_4_2_N), new Integer(-2));
    SKD3.put(new Integer(COOKING_4_3_N), new Integer(-2));
    SKD3.put(new Integer(181), new Integer(-5));
    SKD3.put(new Integer(211), new Integer(-2));
    SKD3.put(new Integer(159), new Integer(-2));
    SKD3.put(new Integer(68), new Integer(68));

    // 受到下列法術效果 傷害為0
    SKM0.put(new Integer(78), new Integer(0));//絕對屏障
    SKM0.put(new Integer(ICE_LANCE), new Integer(0));//冰矛圍籬
    SKM0.put(new Integer(157), new Integer(0));//大地屏障
    SKM0.put(new Integer(FREEZING_BLIZZARD), new Integer(0));
    
    // 料理追加命中(_weaponType != 20) && (_weaponType != 62)近距離武器
    SKU1.put(new Integer(3016), new Integer(1));
    SKU1.put(new Integer(3024), new Integer(1));
    SKU1.put(new Integer(3034), new Integer(2));
    SKU1.put(new Integer(3042), new Integer(2));
    SKU1.put(new Integer(COOKING_4_0_N), new Integer(1));

    // 料理命中追加(_weaponType == 20) && (_weaponType == 62)遠距離武器
    SKU2.put(new Integer(3019), new Integer(1));
    SKU2.put(new Integer(3027), new Integer(1));
    SKU2.put(new Integer(3032), new Integer(1));
    SKU2.put(new Integer(3040), new Integer(1));
    SKU2.put(new Integer(COOKING_4_1_N), new Integer(1));

    // NPC需附加技能可攻擊
    SKNPC.put(new Integer(45912), new Integer(STATUS_HOLY_WATER));
    SKNPC.put(new Integer(45913), new Integer(STATUS_HOLY_WATER));
    SKNPC.put(new Integer(45914), new Integer(STATUS_HOLY_WATER));
    SKNPC.put(new Integer(45915), new Integer(STATUS_HOLY_WATER));
    SKNPC.put(new Integer(45916), new Integer(STATUS_HOLY_MITHRIL_POWDER));
    SKNPC.put(new Integer(45941), new Integer(STATUS_HOLY_WATER_OF_EVA));
    SKNPC.put(new Integer(45752), new Integer(STATUS_CURSE_BARLOG));
    SKNPC.put(new Integer(45753), new Integer(STATUS_CURSE_BARLOG));
    SKNPC.put(new Integer(45675), new Integer(STATUS_CURSE_YAHEE));
    SKNPC.put(new Integer(81082), new Integer(STATUS_CURSE_YAHEE));
    SKNPC.put(new Integer(45625), new Integer(STATUS_CURSE_YAHEE));
    SKNPC.put(new Integer(45674), new Integer(STATUS_CURSE_YAHEE));
    SKNPC.put(new Integer(45685), new Integer(STATUS_CURSE_YAHEE));
    SKNPC.put(new Integer(87000), new Integer(CKEW_LV50));
    SKNPC.put(new Integer(45020), new Integer(I_LV30));
    SKNPC.put(new Integer(99019), new Integer(STATUS_SUNRISE));//巨型骷髏
//    SKNPC.put(new Integer(97446), new Integer(STATUS_RED_SOLVENT));//紅光變異實驗體
//    SKNPC.put(new Integer(97447), new Integer(STATUS_GREEN_SOLVENT));//綠光變異實驗體
//    SKNPC.put(new Integer(97448), new Integer(STATUS_BLUE_SOLVENT));//藍光變異實驗體
    

    // NPC指定外型不可攻擊
    PLNPC.put(new Integer(46069), new Integer(6035));
    PLNPC.put(new Integer(46070), new Integer(6035));
    PLNPC.put(new Integer(46071), new Integer(6035));
    PLNPC.put(new Integer(46072), new Integer(6035));
    PLNPC.put(new Integer(46073), new Integer(6035));
    PLNPC.put(new Integer(46074), new Integer(6035));
    PLNPC.put(new Integer(46075), new Integer(6035));
    PLNPC.put(new Integer(46076), new Integer(6035));
    PLNPC.put(new Integer(46077), new Integer(6035));
    PLNPC.put(new Integer(46078), new Integer(6035));
    PLNPC.put(new Integer(46079), new Integer(6035));
    PLNPC.put(new Integer(46080), new Integer(6035));
    PLNPC.put(new Integer(46081), new Integer(6035));
    PLNPC.put(new Integer(46082), new Integer(6035));
    PLNPC.put(new Integer(46083), new Integer(6035));
    PLNPC.put(new Integer(46084), new Integer(6035));
    PLNPC.put(new Integer(46085), new Integer(6035));
    PLNPC.put(new Integer(46086), new Integer(6035));
    PLNPC.put(new Integer(46087), new Integer(6035));
    PLNPC.put(new Integer(46088), new Integer(6035));
    PLNPC.put(new Integer(46089), new Integer(6035));
    PLNPC.put(new Integer(46090), new Integer(6035));
    PLNPC.put(new Integer(46091), new Integer(6035));
    PLNPC.put(new Integer(46092), new Integer(6034));
    PLNPC.put(new Integer(46093), new Integer(6034));
    PLNPC.put(new Integer(46094), new Integer(6034));
    PLNPC.put(new Integer(46095), new Integer(6034));
    PLNPC.put(new Integer(46096), new Integer(6034));
    PLNPC.put(new Integer(46097), new Integer(6034));
    PLNPC.put(new Integer(46098), new Integer(6034));
    PLNPC.put(new Integer(46099), new Integer(6034));
    PLNPC.put(new Integer(46100), new Integer(6034));
    PLNPC.put(new Integer(46100), new Integer(6034));
    PLNPC.put(new Integer(46101), new Integer(6034));
    PLNPC.put(new Integer(46102), new Integer(6034));
    PLNPC.put(new Integer(46103), new Integer(6034));
    PLNPC.put(new Integer(46104), new Integer(6034));
    PLNPC.put(new Integer(46105), new Integer(6034));
    PLNPC.put(new Integer(46106), new Integer(6034));

    //7.6力量命中補正	
	for (int str = 0; str <= 7; str++) {// 0~7 命中=4
		// 0~7 = 4
		STRH.put(new Integer(str), new Integer(4));
	}

	int strH = 4;//起始命中
	for (int str = 8; str <= 127; str++) { // 8~127 	
		if (str % 3 == 0 || str % 3 == 2) {//餘數為0 or 餘數為2
			strH++;
		}	
		STRH.put(new Integer(str), new Integer(strH));
	}

	// 7.6力量傷害補正	
	for (int str = 0; str <= 9; str++) { // 0~9 傷害=2
		STRD.put(new Integer(str), new Integer(2));
	}
	
	int dmgStr = 2;//起始傷害
	for (int str = 10; str <= 127; str++) { // 10~127 每2+1
		if (str % 2 == 0) {//餘數為0
			dmgStr++;
		}	
		STRD.put(new Integer(str), new Integer(dmgStr));
	}
	
	// 7.6力量增加近距離爆擊機率
	int strcrichance = 0;//起始爆擊機率
	for (int str = 40; str <= 127; str++) { // 40~127		
		strcrichance = (str - 30) / 10;//取商數		
		STRCRI.put(new Integer(str), new Integer(strcrichance));
	}

    //7.6敏捷命中補正	
	for (int dex = 0; dex <= 7; dex++) {// 0~7 命中=-3
		// 0~7 = -3
		DEXH.put(new Integer(dex), new Integer(-3));
	}

	int dexH = -3;//起始命中
	for (int dex = 8; dex <= 127; dex++) { // 8~127 毎點+1命中		
		dexH++;		
		DEXH.put(new Integer(dex), new Integer(dexH));
	}

	// 7.6敏捷傷害補正
	for (int dex = 0; dex <= 8; dex++) {
		// 0~8 = 2
		DEXD.put(new Integer(dex), new Integer(2));
	}

	int dmgDex = 2;//起始傷害
	for (int dex = 9; dex <= 127; dex++) { // 9~127 毎3+1
		if (dex % 3 == 0) {//餘數為0
			dmgDex++;
		}
		DEXD.put(new Integer(dex), new Integer(dmgDex));
	}
	
	// 7.6敏捷增加遠距離爆擊機率
	int dexcrichance = 0;//起始爆擊機率
	for (int dex = 40; dex <= 127; dex++) { // 40~127		
		dexcrichance = (dex - 30) / 10;//取商數		
		DEXCRI.put(new Integer(dex), new Integer(dexcrichance));
	}
	
    //7.6智力魔法命中補正	
	for (int intel = 0; intel <= 22; intel++) {// 0~22 命中=0
		// 0~22 = 0
		INTH.put(new Integer(intel), new Integer(0));
	}

	int intH = 0;//起始命中
	for (int intel = 23; intel <= 127; intel++) { // 23~127 毎3+1	
		if (intel % 3 == 2) {//餘數為2
			intH++;
		}	
		INTH.put(new Integer(intel), new Integer(intH));
	}

	// 7.6智力魔法傷害補正	
	for (int intel = 0; intel <= 14; intel++) { // 0~14 傷害=0
		INTD.put(new Integer(intel), new Integer(0));
	}
	
	int dmgint = 0;//起始傷害
	for (int intel = 15; intel <= 127; intel++) { // 15~127 每5+1
		if (intel % 5 == 0) {//餘數為0
			dmgint++;
		}	
		INTD.put(new Integer(intel), new Integer(dmgint));
	}
	
	// 7.6智力增加魔法爆擊機率
	int intcrichance = 0;//起始爆擊機率
	for (int intel = 35; intel <= 127; intel++) { // 35~127		
		intcrichance = (intel - 30) / 5;//取商數		
		INTCRI.put(new Integer(intel), new Integer(intcrichance));
	}
	
	//7.6智力減少MP消耗量
	for (int intel = 0; intel <= 7; intel++) {// 0~7 減少=0
		INTMPCONSUME.put(new Integer(intel), new Integer(0));
	}

	int minusmpconsume = 5;//起始減少消耗量
	for (int intel = 8; intel <= 127; intel++) { // 8~127 	
		if (intel % 3 == 0 || intel % 3 == 2) {//餘數為0 or 餘數為2
			minusmpconsume++;
		}	
		if (minusmpconsume > 30) {//限制最大減免30%
			minusmpconsume = 30;
		}
		INTMPCONSUME.put(new Integer(intel), new Integer(minusmpconsume));
	}

  }
}

