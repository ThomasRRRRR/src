package com.lineage.server.datatables;

import com.lineage.DatabaseFactory;
import com.lineage.data.ItemClass;
import com.lineage.data.item_armor.set.ArmorSet;
import com.lineage.server.IdFactory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.templates.L1Armor;
import com.lineage.server.templates.L1EtcItem;
import com.lineage.server.templates.L1Item;
import com.lineage.server.templates.L1Weapon;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;
import com.lineage.server.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ItemTable
{
  private static final Log _log = LogFactory.getLog(ItemTable.class);

  private static final Map<String, Integer> _armorTypes = new HashMap<>();

  private static final Map<String, Integer> _weaponTypes = new HashMap<>();

  private static final Map<String, Integer> _weaponId = new HashMap<>();

  private static final Map<String, Integer> _materialTypes = new HashMap<>();

  private static final Map<String, Integer> _etcItemTypes = new HashMap<>();

  private static final Map<String, Integer> _useTypes = new HashMap<>();

  private static ItemTable _instance;

  private L1Item[] _allTemplates;
  
  private static Map<Integer, L1EtcItem> _etcitems;
  
  private static Map<Integer, L1Armor> _armors;
  
  private static Map<Integer, L1Weapon> _weapons;

  
  static {
    _etcItemTypes.put("arrow", new Integer(0));
    _etcItemTypes.put("wand", new Integer(1));
    _etcItemTypes.put("light", new Integer(2));
    _etcItemTypes.put("gem", new Integer(3));
    _etcItemTypes.put("totem", new Integer(4));
    _etcItemTypes.put("firecracker", new Integer(5));
    _etcItemTypes.put("potion", new Integer(6));
    _etcItemTypes.put("food", new Integer(7));
    _etcItemTypes.put("scroll", new Integer(8));
    _etcItemTypes.put("questitem", new Integer(9));
    _etcItemTypes.put("spellbook", new Integer(10));
    _etcItemTypes.put("petitem", new Integer(11));
    _etcItemTypes.put("other", new Integer(12));
    _etcItemTypes.put("material", new Integer(13));
    _etcItemTypes.put("event", new Integer(14));
    _etcItemTypes.put("sting", new Integer(15));
    _etcItemTypes.put("treasure_box", new Integer(16));
    _etcItemTypes.put("ItemIntegration", new Integer(19));
    _etcItemTypes.put("scrollshop", new Integer(20));

    
    _useTypes.put("petitem", new Integer(-12));
    _useTypes.put("other", new Integer(-11));
    _useTypes.put("power", new Integer(-10));
    _useTypes.put("book", new Integer(-9));
    _useTypes.put("makecooking", new Integer(-8));
    _useTypes.put("hpr", new Integer(-7));
    _useTypes.put("mpr", new Integer(-6));
    _useTypes.put("ticket", new Integer(-5));
    _useTypes.put("petcollar", new Integer(-4));
    _useTypes.put("sting", new Integer(-3));
    _useTypes.put("arrow", new Integer(-2));
    _useTypes.put("none", new Integer(-1));
    _useTypes.put("normal", new Integer(0));
    _useTypes.put("weapon", new Integer(1));
    _useTypes.put("armor", new Integer(2));
    _useTypes.put("spell_1", new Integer(3));
    _useTypes.put("4", new Integer(4));
    _useTypes.put("spell_long", new Integer(5));
    _useTypes.put("ntele", new Integer(6));
    _useTypes.put("identify", new Integer(7));
    _useTypes.put("res", new Integer(8));
    _useTypes.put("home", new Integer(9));
    _useTypes.put("light", new Integer(10));
    _useTypes.put("letter", new Integer(12));
    _useTypes.put("letter_card", new Integer(13));
    _useTypes.put("choice", new Integer(14));
    _useTypes.put("instrument", new Integer(15));
    _useTypes.put("sosc", new Integer(16));
    _useTypes.put("spell_short", new Integer(17));
    _useTypes.put("T", new Integer(18));
    _useTypes.put("cloak", new Integer(19));
    _useTypes.put("glove", new Integer(20));
    _useTypes.put("boots", new Integer(21));
    _useTypes.put("helm", new Integer(22));
    _useTypes.put("ring", new Integer(23));
    _useTypes.put("amulet", new Integer(24));
    _useTypes.put("shield", new Integer(25));
    _useTypes.put("guarder", new Integer(25));
    _useTypes.put("dai", new Integer(26));
    _useTypes.put("zel", new Integer(27));
    _useTypes.put("blank", new Integer(28));
    _useTypes.put("btele", new Integer(29));
    _useTypes.put("spell_buff", new Integer(30));
    _useTypes.put("ccard", new Integer(31));
    _useTypes.put("ccard_w", new Integer(32));
    _useTypes.put("vcard", new Integer(33));
    _useTypes.put("vcard_w", new Integer(34));
    _useTypes.put("wcard", new Integer(35));
    _useTypes.put("wcard_w", new Integer(36));
    _useTypes.put("belt", new Integer(37));
    _useTypes.put("food", new Integer(38));
    _useTypes.put("spell_long2", new Integer(39));
    _useTypes.put("earring", new Integer(40));
    _useTypes.put("fishing_rod", new Integer(42));
    _useTypes.put("runeword_left", new Integer(43));
    _useTypes.put("runeword_right", new Integer(44));
    _useTypes.put("runeword_middle", new Integer(45));
    _useTypes.put("enc", new Integer(46));
    _useTypes.put("pants", new Integer(47));
    _useTypes.put("talisman", new Integer(48));
    _useTypes.put("talisman2", new Integer(49));
    _useTypes.put("talisman3", new Integer(51));
    _useTypes.put("choice_doll", new Integer(55));


    
    _armorTypes.put("none", new Integer(0));
    _armorTypes.put("helm", new Integer(1));
    _armorTypes.put("armor", new Integer(2));
    _armorTypes.put("T", new Integer(3));
    _armorTypes.put("cloak", new Integer(4));
    _armorTypes.put("glove", new Integer(5));
    _armorTypes.put("boots", new Integer(6));
    _armorTypes.put("shield", new Integer(7));
    _armorTypes.put("amulet", new Integer(8));
    _armorTypes.put("ring", new Integer(9));
    _armorTypes.put("belt", new Integer(10));
    _armorTypes.put("ring2", new Integer(11));
    _armorTypes.put("earring", new Integer(12));
    _armorTypes.put("guarder", new Integer(13));
    _armorTypes.put("runeword_left", new Integer(14));
    _armorTypes.put("runeword_right", new Integer(15));
    _armorTypes.put("runeword_middle", new Integer(16));
    _armorTypes.put("pants", new Integer(17));
    _armorTypes.put("talisman", new Integer(18));
    _armorTypes.put("talisman2", new Integer(19));
    _armorTypes.put("talisman3", new Integer(20));


    
    _weaponTypes.put("none", new Integer(0));
    _weaponTypes.put("sword", new Integer(1));
    _weaponTypes.put("dagger", new Integer(2));
    _weaponTypes.put("tohandsword", new Integer(3));
    _weaponTypes.put("bow", new Integer(4));
    _weaponTypes.put("spear", new Integer(5));
    _weaponTypes.put("blunt", new Integer(6));
    _weaponTypes.put("staff", new Integer(7));
    _weaponTypes.put("throwingknife", new Integer(8));
    _weaponTypes.put("arrow", new Integer(9));
    _weaponTypes.put("gauntlet", new Integer(10));
    _weaponTypes.put("claw", new Integer(11));
    _weaponTypes.put("edoryu", new Integer(12));
    _weaponTypes.put("singlebow", new Integer(13));
    _weaponTypes.put("singlespear", new Integer(14));
    _weaponTypes.put("tohandblunt", new Integer(15));
    _weaponTypes.put("tohandstaff", new Integer(16));
    _weaponTypes.put("kiringku", new Integer(17));
    _weaponTypes.put("chainsword", new Integer(18));

    
    _weaponId.put("sword", new Integer(4));
    _weaponId.put("dagger", new Integer(46));
    _weaponId.put("tohandsword", new Integer(50));
    _weaponId.put("bow", new Integer(20));
    _weaponId.put("blunt", new Integer(11));
    _weaponId.put("spear", new Integer(24));
    _weaponId.put("staff", new Integer(40));
    _weaponId.put("throwingknife", new Integer(2922));
    _weaponId.put("arrow", new Integer(66));
    _weaponId.put("gauntlet", new Integer(62));
    _weaponId.put("claw", new Integer(58));
    _weaponId.put("edoryu", new Integer(54));
    _weaponId.put("singlebow", new Integer(20));
    _weaponId.put("singlespear", new Integer(24));
    _weaponId.put("tohandblunt", new Integer(11));
    _weaponId.put("tohandstaff", new Integer(40));
    _weaponId.put("kiringku", new Integer(58));
    _weaponId.put("chainsword", new Integer(24));


    
    _materialTypes.put("none", new Integer(0));
    _materialTypes.put("liquid", new Integer(1));
    _materialTypes.put("web", new Integer(2));
    _materialTypes.put("vegetation", new Integer(3));
    _materialTypes.put("animalmatter", new Integer(4));
    _materialTypes.put("paper", new Integer(5));
    _materialTypes.put("cloth", new Integer(6));
    _materialTypes.put("leather", new Integer(7));
    _materialTypes.put("wood", new Integer(8));
    _materialTypes.put("bone", new Integer(9));
    _materialTypes.put("dragonscale", new Integer(10));
    _materialTypes.put("iron", new Integer(11));
    _materialTypes.put("steel", new Integer(12));
    _materialTypes.put("copper", new Integer(13));
    _materialTypes.put("silver", new Integer(14));
    _materialTypes.put("gold", new Integer(15));
    _materialTypes.put("platinum", new Integer(16));
    _materialTypes.put("mithril", new Integer(17));
    _materialTypes.put("blackmithril", new Integer(18));
    _materialTypes.put("glass", new Integer(19));
    _materialTypes.put("gemstone", new Integer(20));
    _materialTypes.put("mineral", new Integer(21));
    _materialTypes.put("oriharukon", new Integer(22));
  }
  
  public static ItemTable get() {
    if (_instance == null) {
      _instance = new ItemTable();
    }
    return _instance;
  }
  
  public void load() {
    PerformanceTimer timer = new PerformanceTimer();
    _etcitems = allEtcItem();
    _weapons = allWeapon();
    _armors = allArmor();
    
    buildFastLookupTable();
    _log.info("載入道具,武器,防具資料: " + 
        _etcitems.size() + "+" + _weapons.size() + "+" + _armors.size() + "=" + (
        _etcitems.size() + _weapons.size() + _armors.size()) + "(" + 
        timer.get() + "ms)");
  }
  
  public void loadarmors() {
    PerformanceTimer timer = new PerformanceTimer();
    _armors = allArmor();
    buildFastLookupTable();
    _log.info("載入防具資料: " + _armors.size() + "=" + "(" + timer.get() + "ms)");
  }
  
  public void loadweapons() {
    PerformanceTimer timer = new PerformanceTimer();
    _weapons = allWeapon();
    buildFastLookupTable();
    _log.info("載入武器資料: " + _weapons.size() + "=" + "(" + timer.get() + "ms)");
  }
  
  public void loaditems() {
    PerformanceTimer timer = new PerformanceTimer();
    _etcitems = allEtcItem();
    buildFastLookupTable();
    _log.info("載入道具資料: " + _etcitems.size() + "=" + "(" + timer.get() + "ms)");
  }

  
  private Map<Integer, L1EtcItem> allEtcItem() {
    Map<Integer, L1EtcItem> result = new HashMap<>();
    
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    L1EtcItem item = null;
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM `etcitem`");
      rs = pstm.executeQuery();
      while (rs.next()) {
        item = new L1EtcItem();
        int itemid = rs.getInt("item_id");
        item.setItemId(itemid);
        item.setName(rs.getString("name"));
        String classname = rs.getString("classname");
        item.setClassname(classname);
        item.setNameId(rs.getString("name_id"));
        item.setType(((Integer)_etcItemTypes.get(rs.getString("item_type"))).intValue());
        item.setUseType(((Integer)_useTypes.get(rs.getString("use_type"))).intValue());
        item.setType2(0);
        item.setMaterial(((Integer)_materialTypes.get(rs.getString("material"))).intValue());
        item.setWeight(rs.getInt("weight"));
        item.setGfxId(rs.getInt("invgfx"));
        item.setGroundGfxId(rs.getInt("grdgfx"));
        item.setItemDescId(rs.getInt("itemdesc_id"));
        item.setMinLevel(rs.getInt("min_lvl"));
        item.setMaxLevel(rs.getInt("max_lvl"));
        item.setBless(rs.getInt("bless"));
        item.setTradable((rs.getInt("trade") == 0));
        item.setCantDelete((rs.getInt("cant_delete") == 1));
        item.setDmgSmall(rs.getInt("dmg_small"));
        item.setDmgLarge(rs.getInt("dmg_large"));
        item.set_stackable((rs.getInt("stackable") == 1));
        item.setMaxChargeCount(rs.getInt("max_charge_count"));
        item.setMaxUseTime(rs.getInt("max_use_time"));
        item.set_delayid(rs.getInt("delay_id"));
        item.set_delaytime(rs.getInt("delay_time"));
        item.set_delayEffect(rs.getInt("delay_effect"));
        item.setFoodVolume(rs.getInt("food_volume"));
        item.setToBeSavedAtOnce((rs.getInt("save_at_once") == 1));
        item.setminlogpcpower(rs.getInt("minlogpcpower"));
        item.setmaxlogpcpower(rs.getInt("maxlogpcpower"));
        item.setacccount(rs.getInt("帳號使用一次"));
        item.setcharcount(rs.getInt("角色使用一次"));
        
     // 是否不能被賣掉  by Manly
		item.cantBeSold(rs.getBoolean("此商品是否販賣"));
        
        int use_career = rs.getInt("職業判斷");
        item.setUseRoyal(((use_career & 0x1) == 1));
        item.setUseKnight(((use_career & 0x2) == 2));
        item.setUseElf(((use_career & 0x4) == 4));
        item.setUseMage(((use_career & 0x8) == 8));
        item.setUseDarkelf(((use_career & 0x10) == 16));
        item.setUseDragonknight(((use_career & 0x20) == 32));
        item.setUseIllusionist(((use_career & 0x40) == 64));
        
        ItemClass.get().addList(itemid, classname, 0);
        result.put(new Integer(item.getItemId()), item);
      } 
    } catch (NullPointerException e) {
      _log.error("加載失敗: " + item.getItemId(), e);
    }
    catch (SQLException e) {
      _log.error(e.getLocalizedMessage(), e);
    } finally {
      
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
    return result;
  }

  
  private Map<Integer, L1Weapon> allWeapon() {
    Map<Integer, L1Weapon> result = new HashMap<>();
    
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    L1Weapon weapon = null;
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM `weapon`");
      rs = pstm.executeQuery();
      while (rs.next()) {
        weapon = new L1Weapon();
        int itemid = rs.getInt("item_id");
        weapon.setItemId(itemid);
        weapon.setName(rs.getString("name"));
        String classname = rs.getString("classname");
        weapon.setClassname(classname);
        weapon.setNameId(rs.getString("name_id"));
        weapon.setType(((Integer)_weaponTypes.get(rs.getString("type"))).intValue());
        weapon.setType1(((Integer)_weaponId.get(rs.getString("type"))).intValue());
        weapon.setType2(1);
        weapon.setUseType(1);
        weapon.setMaterial(((Integer)_materialTypes.get(rs.getString("material"))).intValue());
        weapon.setWeight(rs.getInt("weight"));
        weapon.setGfxId(rs.getInt("invgfx"));
        weapon.setGroundGfxId(rs.getInt("grdgfx"));
        weapon.setItemDescId(rs.getInt("itemdesc_id"));
        weapon.setDmgSmall(rs.getInt("dmg_small"));
        weapon.setDmgLarge(rs.getInt("dmg_large"));
        weapon.setRange(rs.getInt("range"));
        weapon.set_safeenchant(rs.getInt("safenchant"));
        weapon.setUseRoyal((rs.getInt("use_royal") != 0));
        weapon.setUseKnight((rs.getInt("use_knight") != 0));
        weapon.setUseElf((rs.getInt("use_elf") != 0));
        weapon.setUseMage((rs.getInt("use_mage") != 0));
        weapon.setUseDarkelf((rs.getInt("use_darkelf") != 0));
        weapon.setUseDragonknight((rs.getInt("use_dragonknight") != 0));
        weapon.setUseIllusionist((rs.getInt("use_illusionist") != 0));
        weapon.setHitModifier(rs.getInt("hitmodifier"));
        weapon.setDmgModifier(rs.getInt("dmgmodifier"));
        weapon.set_addstr(rs.getByte("add_str"));
        weapon.set_adddex(rs.getByte("add_dex"));
        weapon.set_addcon(rs.getByte("add_con"));
        weapon.set_addint(rs.getByte("add_int"));
        weapon.set_addwis(rs.getByte("add_wis"));
        weapon.set_addcha(rs.getByte("add_cha"));
        weapon.set_addhp(rs.getInt("add_hp"));
        weapon.set_addmp(rs.getInt("add_mp"));
        weapon.set_addhpr(rs.getInt("add_hpr"));
        weapon.set_addmpr(rs.getInt("add_mpr"));
        weapon.set_addsp(rs.getInt("add_sp"));
        weapon.set_mdef(rs.getInt("m_def"));
        weapon.setDoubleDmgChance(rs.getInt("double_dmg_chance"));
        weapon.setMagicDmgModifier(rs.getInt("magicdmgmodifier"));
        weapon.set_canbedmg(rs.getInt("canbedmg"));
        weapon.setMinLevel(rs.getInt("min_lvl"));
        weapon.setMaxLevel(rs.getInt("max_lvl"));
        weapon.setBless(rs.getInt("bless"));
        weapon.setTradable((rs.getInt("trade") == 0));
        weapon.setCantDelete((rs.getInt("cant_delete") == 1));
        weapon.setHasteItem((rs.getInt("haste_item") != 0));
        weapon.setMaxUseTime(rs.getInt("max_use_time"));
        weapon.setExpPoint(rs.getInt("exp_point"));
        weapon.setPVPdmg(rs.getInt("PVPdmg"));
        weapon.setPVPdmgReduction(rs.getInt("PVPdmgReduction"));
        weapon.setpenetrate(rs.getInt("貫穿效果"));
        weapon.setNoweaponRedmg(rs.getInt("無視傷害減免"));
        weapon.setaddStunLevel(rs.getInt("昏迷命中率"));
        weapon.setdropcolor(!(rs.getInt("裝備炫彩") == 0));
        weapon.setgivebless(!(rs.getInt("是否可祝福") == 0));
        weapon.setnewbless(rs.getInt("稀有祝福讀取"));
        
     // 是否不能被賣掉 by Manly
		weapon.cantBeSold(rs.getBoolean("此商品是否販賣"));
        
        ItemClass.get().addList(itemid, classname, 1);
        result.put(new Integer(weapon.getItemId()), weapon);
      } 
    } catch (NullPointerException e) {
      _log.error("加載失敗: " + weapon.getItemId(), e);
    }
    catch (SQLException e) {
      _log.error(e.getLocalizedMessage(), e);
    } finally {
      
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
    
    return result;
  }
  
  public static void init() {
    _instance = new ItemTable();
  }

  
  private Map<Integer, L1Armor> allArmor() {
    Map<Integer, L1Armor> result = new HashMap<>();
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    L1Armor armor = null;
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM `armor`");
      rs = pstm.executeQuery();
      while (rs.next()) {
        armor = new L1Armor();
        int itemid = rs.getInt("item_id");
        armor.setItemId(itemid);
        armor.setName(rs.getString("name"));
        String classname = rs.getString("classname");
        armor.setClassname(classname);
        armor.setNameId(rs.getString("name_id"));
        armor.setType(((Integer)_armorTypes.get(rs.getString("type"))).intValue());
        armor.setType2(2);
        armor.setUseType(((Integer)_useTypes.get(rs.getString("type"))).intValue());
        armor.setMaterial(((Integer)_materialTypes.get(rs.getString("material"))).intValue());
        armor.setWeight(rs.getInt("weight"));
        armor.setGfxId(rs.getInt("invgfx"));
        armor.setGroundGfxId(rs.getInt("grdgfx"));
        armor.setItemDescId(rs.getInt("itemdesc_id"));
        armor.set_ac(rs.getInt("ac"));
        armor.set_safeenchant(rs.getInt("safenchant"));
        armor.setUseRoyal((rs.getInt("use_royal") != 0));
        armor.setUseKnight((rs.getInt("use_knight") != 0));
        armor.setUseElf((rs.getInt("use_elf") != 0));
        armor.setUseMage((rs.getInt("use_mage") != 0));
        armor.setUseDarkelf((rs.getInt("use_darkelf") != 0));
        armor.setUseDragonknight((rs.getInt("use_dragonknight") != 0));
        armor.setUseIllusionist((rs.getInt("use_illusionist") != 0));
        armor.set_addstr(rs.getByte("add_str"));
        armor.set_addcon(rs.getByte("add_con"));
        armor.set_adddex(rs.getByte("add_dex"));
        armor.set_addint(rs.getByte("add_int"));
        armor.set_addwis(rs.getByte("add_wis"));
        armor.set_addcha(rs.getByte("add_cha"));
        armor.set_addhp(rs.getInt("add_hp"));
        armor.set_addmp(rs.getInt("add_mp"));
        armor.set_addhpr(rs.getInt("add_hpr"));
        armor.set_addmpr(rs.getInt("add_mpr"));
        armor.set_addsp(rs.getInt("add_sp"));
        armor.setMinLevel(rs.getInt("min_lvl"));
        armor.setMaxLevel(rs.getInt("max_lvl"));
        armor.set_mdef(rs.getInt("m_def"));
        armor.setDamageReduction(rs.getInt("damage_reduction"));
        armor.setWeightReduction(rs.getInt("weight_reduction"));
        armor.setHitModifierByArmor(rs.getInt("hit_modifier"));
        armor.setDmgModifierByArmor(rs.getInt("dmg_modifier"));
        armor.setBowHitModifierByArmor(rs.getInt("bow_hit_modifier"));
        armor.setBowDmgModifierByArmor(rs.getInt("bow_dmg_modifier"));
        armor.setHasteItem((rs.getInt("haste_item") != 0));
        armor.setBless(rs.getInt("bless"));
        armor.setTradable((rs.getInt("trade") == 0));
        armor.setCantDelete((rs.getInt("cant_delete") == 1));
        armor.set_defense_earth(rs.getInt("defense_earth"));
        armor.set_defense_water(rs.getInt("defense_water"));
        armor.set_defense_wind(rs.getInt("defense_wind"));
        armor.set_defense_fire(rs.getInt("defense_fire"));
        armor.set_regist_stun(rs.getInt("regist_stun"));
        armor.set_regist_stone(rs.getInt("regist_stone"));
        armor.set_regist_sleep(rs.getInt("regist_sleep"));
        armor.set_regist_freeze(rs.getInt("regist_freeze"));
        armor.set_regist_sustain(rs.getInt("regist_sustain"));
        armor.set_regist_blind(rs.getInt("regist_blind"));
        armor.setMaxUseTime(rs.getInt("max_use_time"));
        armor.set_greater(rs.getInt("greater"));
        armor.setExpPoint(rs.getInt("exp_point"));
        armor.setMagicHitModifierByArmor(rs.getInt("magic_hit_modifier"));
        armor.set_up_hp_potion(rs.getInt("up_hp_potion"));
        armor.set_uhp_number(rs.getInt("uhp_number"));
        armor.setPVPdmg(rs.getInt("PVPdmg"));
        armor.setPVPdmgReduction(rs.getInt("PVPdmgReduction"));
        
        armor.setdropcolor(!(rs.getInt("裝備炫彩") == 0));
        armor.setgivebless(!(rs.getInt("是否可祝福") == 0));
        
        armor.setweaponskillpro(rs.getInt("魔武發動率"));
        armor.setweaponskilldmg(rs.getInt("魔武增傷害"));
        armor.setnewbless(rs.getInt("稀有祝福讀取"));
        armor.setadenapoint(rs.getInt("金幣"));
        
        armor.set_CriticalChance(rs.getInt("critical"));
		armor.set_Bow_CriticalChance(rs.getInt("bow_critical"));
		armor.set_Magic_CriticalChance(rs.getInt("magic_critical"));
		
		// 是否不能被賣掉 by Manly
		armor.cantBeSold(rs.getBoolean("此商品是否販賣"));
		
        ItemClass.get().addList(itemid, classname, 2);
        result.put(new Integer(armor.getItemId()), armor);
      } 
    } catch (NullPointerException e) {
      _log.error("加載失敗: " + armor.getItemId(), e);
    }
    catch (SQLException e) {
      _log.error(e.getLocalizedMessage(), e);
    } finally {
      
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
    
    return result;
  }

  private void buildFastLookupTable() {
    int highestId = 0;
    
    Collection<L1EtcItem> items = _etcitems.values();
    for (L1EtcItem item : items) {
      if (item.getItemId() > highestId) {
        highestId = item.getItemId();
      }
    } 
    
    Collection<L1Weapon> weapons = _weapons.values();
    for (L1Weapon weapon : weapons) {
      if (weapon.getItemId() > highestId) {
        highestId = weapon.getItemId();
      }
    } 
    
    Collection<L1Armor> armors = _armors.values();
    for (L1Armor armor : armors) {
      if (armor.getItemId() > highestId) {
        highestId = armor.getItemId();
      }
    } 

    this._allTemplates = new L1Item[highestId + 1];
    Iterator<Integer> iter;
    for (iter = _etcitems.keySet().iterator(); iter.hasNext(); ) {
      Integer id = iter.next();
      L1EtcItem item = _etcitems.get(id);
      this._allTemplates[id.intValue()] = (L1Item)item;
    } 
    
    for (iter = _weapons.keySet().iterator(); iter.hasNext(); ) {
      Integer id = iter.next();
      L1Weapon item = _weapons.get(id);
      this._allTemplates[id.intValue()] = (L1Item)item;
    } 
    
    for (iter = _armors.keySet().iterator(); iter.hasNext(); ) {
      Integer id = iter.next();
      L1Armor item = _armors.get(id);
      this._allTemplates[id.intValue()] = (L1Item)item;
    } 
  }

	/**
	 * 套裝效果
	 */
	public void se_mode() {
		PerformanceTimer timer = new PerformanceTimer();
		for (L1Item item : _allTemplates) {
			if (item != null) {
				for (Integer key : ArmorSet.getAllSet().keySet()) {
					ArmorSet armorSet = (ArmorSet) ArmorSet.getAllSet().get(key);

					if (armorSet.isPartOfSet(item.getItemId())) {
						item.set_mode(armorSet.get_mode());
					}
				}
			}
		}
		_log.info("載入套裝效果數字陣列: " + timer.get() + "ms)");
	}

	/**
	 * 傳回指定編號物品資料
	 * 
	 * @param itemid
	 * @return
	 */
	public L1Item getTemplate(final int itemid) {
		try {
			return this._allTemplates[itemid];

		} catch (final Exception e) {
		}
		return null;
	}

	/**
	 * 傳回指定名稱物品資料
	 * 
	 * @param nameid
	 * @return
	 */
	public L1Item getTemplate(final String nameid) {
		for (final L1Item item : this._allTemplates) {
			if ((item != null) && item.getNameId().equals(nameid)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 產生新物件
	 * 
	 * @param itemId
	 * @return
	 */
	public L1ItemInstance createItem(final int itemId) {
		final L1Item temp = this.getTemplate(itemId);
		if (temp == null) {
			return null;
		}
		final L1ItemInstance item = new L1ItemInstance();
		item.setId(IdFactory.get().nextId());
		item.setItem(temp);
		item.setBless(temp.getBless());

		World.get().storeObject(item);
		return item;
	}

	/**
	 * 依名稱(NameId)找回itemid
	 * 
	 * @param name
	 * @return
	 */
	public int findItemIdByName(final String name) {
		int itemid = 0;
		for (final L1Item item : this._allTemplates) {
			if ((item != null) && item.getNameId().equals(name)) {
				itemid = item.getItemId();
				break;
			}
		}
		return itemid;
	}

	/**
	 * 依名稱(中文)找回itemid
	 * 
	 * @param name
	 * @return
	 */
	public int findItemIdByNameWithoutSpace(final String name) {
		int itemid = 0;
		for (final L1Item item : this._allTemplates) {
			if ((item != null) && item.getName().replace(" ", "").equals(name)) {
				itemid = item.getItemId();
				break;
			}
		}
		return itemid;
	}

	/**
	 * 依名稱(中文)找回itemid
	 * 
	 * @param name
	 * @return
	 */
	public int findItemIdByNameWithoutSpace1(final String name) {
		int itemid = 0;
		for (final L1Item item : this._allTemplates) {
			if ((item != null) && item.getName().replace(" ", "").equals(name)) {
				itemid = item.getItemId();
				break;
			}
		}
		return itemid;
	}
}