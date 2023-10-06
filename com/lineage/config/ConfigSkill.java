package com.lineage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;

/**
 * 特殊系統設置
 *
 * @author Manly
 *
 */
public final class ConfigSkill {

//	//角色能力值攻擊暴擊設定---------------------------------------------------
//	//角色數值大於等於設定值
//	public static double StrCriticalLV;
//	public static double DexCriticalLV;
//	public static double IntCriticalLV;
//	public static double MagicDiceLV;
//	//角色數值如果大於等於以上設定值 暴擊機率增加 設定值
//	public static double StrCriticaladdchance;
//	public static double DexCriticaladdchance;
//	public static double IntCriticaladdchance;
//	public static double MagicDiceaddchance;
//	//暴擊傷害*設定值
//	public static int StrCriticalDmg;
//	public static int DexCriticalDmg;
//	public static int IntCriticalDmg;
//	public static double MagicDiceDmg;
	
	//王族---------------------------------------------------
	/**精準目標倍率 */
	public static double STRIKER_DMG;
	//---------------------------------------------------
	//騎士---------------------------------------------------
	/**堅固防護 0=只包含盾牌  1=可使用包含盾牌臂甲*/
	public static int SOLID_CARRIAGE_MODE;
	/**衝暈秒數*/
	public static String SHOCK_STUN_TIMER;
	//---------------------------------------------------
	//妖精---------------------------------------------------
	/**三重矢原始傷害倍率*/
	public static double TRIPLE_ARROW_DMG;
	/**烈焰之魂倍率*/
	public static double SOUL_OF_FLAME_DAMAGE;
	/**烈焰之魂總傷害倍率*/
	public static double SOUL_OF_FLAME_ALLDAMAGE;
	/**屬性之火近(原本1.5倍)*/
	public static double ELEMENTAL_FIRE;
	/**屬性之火發動機率*/
	public static int ELEMENTAL_FIRE_RND;
	/**妖精技能(魂體轉換)轉一次+多少低魔(預設12)*/
	public static int BLOODY_SOULADDMP;
	//---------------------------------------------------
	//黑妖---------------------------------------------------
	/**黑妖 燃燒鬥志發動機率(%)*/
	public static int BURNING_CHANCE;
	/**黑妖 燃燒鬥志傷害倍率*/
	public static double BURNING_DMG;
	/**黑妖 雙重破壞發動機率(%)*/
	public static int DOUBLE_BREAK_CHANCE;
	/**黑妖 雙重破壞傷害倍率*/
	public static double DOUBLE_BREAK_DMG;
	public static boolean DOUBLE_BREAK_NO_WEAPON = false;
	/**黑妖技能 111	迴避提升*/
	public static int DRESS_EVASION;
	//龍騎---------------------------------------------------
	/**屠宰衝暈機率(1=1%)*/
	public static int FOE_SLAYER_RND;
	/**屠宰衝暈秒數(1000=1秒)*/
	public static int FOE_SLAYER_SEC;
	/**屠宰隨機亂數傷害*/
	public static int FOE_SLAYER_DMG;
	/**裝備 (410189 殲滅者鎖鏈劍) 額外弱點曝光機率(%)*/
	public static int VULNERABILITY_OTHER;
	/**#弱點曝光啟動機率*/
	public static int VULNERABILITY_ROM;
	/**弱點曝光階段Lv1【設定】= 增加的傷害*/
	public static int VULNERABILITY_1;
	/**弱點曝光階段Lv2【設定】= 增加的傷害*/
	public static int VULNERABILITY_2;
	/**弱點曝光階段Lv3【設定】= 增加的傷害*/
	public static int VULNERABILITY_3;
	//幻術---------------------------------------------------
	/**鏡像閃避率1=10%登入器只接受10位數*/
	public static int MIRROR;
	
	
	private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/各職業技能相關設置.properties";

	public static void load() throws ConfigErrorException {
		//_log.info("載入服務器限制設置!");
		final Properties set = new Properties();
		try {
			final InputStream is = new FileInputStream(new File(OTHER_SETTINGS_FILE));
			// 指定檔案編碼
			final InputStreamReader isr = new InputStreamReader(is, "utf-8");
			set.load(isr);
			is.close();
			
//			//角色數值大於等於設定值
//			StrCriticalLV = Double.parseDouble(set.getProperty("StrCriticalLV", "1.0"));
//			DexCriticalLV = Double.parseDouble(set.getProperty("DexCriticalLV", "1.0"));
//			IntCriticalLV = Double.parseDouble(set.getProperty("IntCriticalLV", "1.0"));
//			MagicDiceLV = Double.parseDouble(set.getProperty("MagicDiceLV", "1.0"));
//			//角色數值如果大於等於以上設定值 暴擊機率增加 設定值
//			StrCriticaladdchance = Double.parseDouble(set.getProperty("StrCriticaladdchance", "1.0"));
//			DexCriticaladdchance = Double.parseDouble(set.getProperty("DexCriticaladdchance", "1.0"));
//			IntCriticaladdchance = Double.parseDouble(set.getProperty("IntCriticaladdchance", "1.0"));
//			MagicDiceaddchance = Double.parseDouble(set.getProperty("MagicDiceaddchance", "1.0"));
//			//暴擊傷害*設定值
//			StrCriticalDmg = Integer.parseInt(set.getProperty("StrCriticalDmg", "1.0"));
//			DexCriticalDmg = Integer.parseInt(set.getProperty("DexCriticalDmg", "1.0"));
//			IntCriticalDmg = Integer.parseInt(set.getProperty("IntCriticalDmg", "1.0"));
//			MagicDiceDmg = Double.parseDouble(set.getProperty("MagicDiceDmg", "1.0"));
			
			//王族---------------------------------------------------
			STRIKER_DMG = Double.parseDouble(set.getProperty("STRIKER_DMG", "1.0"));
			//騎士---------------------------------------------------
			SOLID_CARRIAGE_MODE = Integer.parseInt(set.getProperty("SOLID_CARRIAGE_MODE", "1"));
			SHOCK_STUN_TIMER = set.getProperty("SHOCK_STUN_TIMER", "3~6");
			//妖精---------------------------------------------------
			TRIPLE_ARROW_DMG = Double.parseDouble(set.getProperty("Triple_Arrow_Dmg", "1.0"));
			SOUL_OF_FLAME_DAMAGE = Double.parseDouble(set.getProperty("SOUL_OF_FLAME_DAMAGE", "1.0"));
			SOUL_OF_FLAME_ALLDAMAGE = Double.parseDouble(set.getProperty("SOUL_OF_FLAME_ALLDAMAGE", "1.0"));
			ELEMENTAL_FIRE = Double.parseDouble(set.getProperty("ELEMENTAL_FIRE", "1.0"));
			ELEMENTAL_FIRE_RND = Integer.parseInt(set.getProperty("ELEMENTAL_FIRE_RND", "33"));
			BLOODY_SOULADDMP = Integer.parseInt(set.getProperty("BLOODY_SOULADDMP", "12"));
			//黑妖---------------------------------------------------
			BURNING_CHANCE = Integer.parseInt(set.getProperty("BURNING_CHANCE", "15"));
			BURNING_DMG = Double.parseDouble(set.getProperty("BURNING_DMG", "1.3"));
			DOUBLE_BREAK_CHANCE = Integer.parseInt(set.getProperty("DOUBLE_BREAK_CHANCE", "15"));
			DOUBLE_BREAK_DMG = Double.parseDouble(set.getProperty("DOUBLE_BREAK_DMG", "1.5"));
			DOUBLE_BREAK_NO_WEAPON = Boolean.parseBoolean(set.getProperty("DOUBLE_BREAK_NO_WEAPON", "false"));
			DRESS_EVASION = Integer.parseInt(set.getProperty("DRESS_EVASION", "18"));
			//龍騎---------------------------------------------------
			FOE_SLAYER_RND = Integer.parseInt(set.getProperty("FOE_SLAYER_RND", "15"));
			FOE_SLAYER_SEC = Integer.parseInt(set.getProperty("FOE_SLAYER_SEC", "30"));
			FOE_SLAYER_DMG = Integer.parseInt(set.getProperty("FOE_SLAYER_DMG", "10"));
			VULNERABILITY_OTHER = Integer.parseInt(set.getProperty("VULNERABILITY_OTHER", "10"));
			VULNERABILITY_ROM = Integer.parseInt(set.getProperty("VULNERABILITY_ROM", "15"));
            VULNERABILITY_1 = Integer.parseInt(set.getProperty("VULNERABILITY_1", "5"));
			VULNERABILITY_2 = Integer.parseInt(set.getProperty("VULNERABILITY_2", "10"));
			VULNERABILITY_3 = Integer.parseInt(set.getProperty("VULNERABILITY_3", "15"));
			//幻術---------------------------------------------------
			MIRROR = Integer.parseInt(set.getProperty("MIRROR", "5"));
			
			
			
			
			
			
			
			
		} catch (final Exception e) {
			throw new ConfigErrorException("設置檔案遺失: " + OTHER_SETTINGS_FILE);

		} finally {
			set.clear();
		}
	}

	public static int[] toIntArray(final String text, final String type){
		StringTokenizer st = new StringTokenizer(text, type);
		int[] iReturn = new int[st.countTokens()];
		for (int i = 0; i < iReturn.length; i++) {
			iReturn[i] = Integer.parseInt(st.nextToken());
		}
		return iReturn;
	}
}