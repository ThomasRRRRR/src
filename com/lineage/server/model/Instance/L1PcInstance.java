package com.lineage.server.model.Instance;

import com.lineage.william.L1WilliamGfxIdOrginal;
import com.lineage.server.templates.L1Name_Power;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.datatables.C1_Name_Type_Table;
import com.lineage.william.L1WilliamSystemMessage;
import com.lineage.data.event.CampSet;
import com.lineage.config.Configdead;
import com.lineage.data.event.taketreasure;
import com.lineage.data.event.ItemSteallok;
import com.lineage.config.ConfigKill;
import com.lineage.server.serverpackets.S_NPCPack_Pet1;
import com.lineage.server.utils.L1SpawnUtil;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.serverpackets.S_NewMaster;
import com.add.NewAutoPractice;
import com.lineage.server.serverpackets.S_PacketBoxParty;
import static com.lineage.server.model.skill.L1SkillId.*;
import java.util.TimerTask;

import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.model.SoulTower.SoulTowerThread;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;

import com.lineage.server.ActionCodes;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.datatables.lock.CharMapTimeReading;
import com.lineage.server.datatables.MapsTable;

import java.text.SimpleDateFormat;

import com.lineage.server.templates.L1Taketreasure1;
import com.lineage.server.datatables.Taketreasure1;
import com.lineage.server.templates.L1Taketreasure;
import com.lineage.server.datatables.Taketreasure;
import com.lineage.server.templates.L1Skills;
import com.lineage.config.ConfigDropSkill;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.datatables.RewardPrestigeTable;
import com.lineage.data.event.prestigtable;
import com.lineage.data.event.ProtectorSet;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.datatables.ExtraMeteAbilityTable;
import com.lineage.server.utils.DoubleUtil;
import com.lineage.server.model.L1TownLocation;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.templates.L1Item;
import com.lineage.server.datatables.AutoAddSkillTable;
import com.lineage.data.event.Addskill;
import com.lineage.server.datatables.MapLevelTable;
import com.lineage.william.L1WilliamLimitedReward;
import com.lineage.data.event.lvgiveitemcount;
import com.lineage.william.Reward1;
import com.lineage.william.Reward;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.datatables.ItemTable;
import com.lineage.server.utils.CalcStat;
import com.lineage.server.model.monitor.L1PcMonitor;
import com.lineage.server.model.monitor.L1PcInvisDelay;
import com.lineage.server.model.monitor.PcAttackThread;
import com.lineage.config.ConfigRate;
import com.lineage.server.datatables.lock.CharOtherReading3;
import com.lineage.server.datatables.lock.CharOtherReading2;
import com.lineage.server.datatables.lock.CharOtherReading1;
import com.lineage.server.datatables.lock.CharOtherReading;
import com.lineage.server.datatables.sql.CharacterTable;

import java.util.Calendar;

import com.lineage.config.ConfigSkill;
import com.lineage.server.datatables.RecordTable;
import com.lineage.config.ConfigPnitem;
import com.lineage.server.datatables.ItemdropdeadTable;
import com.lineage.server.datatables.Itemdeaddrop;
//import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.DeathThreadPool;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.L1AttackNpc;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.config.ConfigAlt;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1AttackPc;
import com.eric.gui.J_Main;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.datatables.lock.CharBuffReading;

import java.util.List;

import com.lineage.server.world.WorldClan;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.model.L1CastleLocation;
import com.add.L1PcUnlock;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.world.World;

import java.util.Iterator;

import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.types.Point;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_Fishing;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_Exp;
import com.lineage.server.datatables.ExpTable;
import com.lineage.config.ConfigOther;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_PacketBoxProtection;
import com.lineage.server.serverpackets.S_Karma;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_Lawful;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.data.event.OnlineGiftSet;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;

import java.util.HashMap;

import com.lineage.config.Config;

import org.apache.commons.logging.LogFactory;

import java.util.Timer;

import com.lineage.william.SustainEffect2;
import com.lineage.server.model.L1HateList;

import com.lineage.server.model.L1Inventory;

import java.util.concurrent.ConcurrentHashMap;

import com.lineage.server.model.L1Apprentice;
import com.lineage.server.templates.L1MeteAbility;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.templates.L1TradeItem;
import com.lineage.server.templates.L1User_Power;
import com.lineage.server.templates.L1ItemPower_text;
import com.lineage.data.quest.Chapter01R;
import com.lineage.server.templates.L1PcOtherList;
import com.lineage.server.templates.L1PcOther3;
import com.lineage.server.templates.L1PcOther2;
import com.lineage.server.templates.L1PcOther1;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.model.L1ExcludingList;

import java.sql.Timestamp;

import com.lineage.server.model.L1EquipmentSlot;
import com.lineage.server.model.L1ActionSummon;
import com.lineage.server.model.L1ActionPet;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.L1ChatParty;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.L1DwarfForElfInventory;
import com.lineage.server.model.L1DwarfForChaInventory;
import com.lineage.server.model.L1DwarfInventory;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.L1Karma;
import com.lineage.echo.ClientExecutor;
import com.lineage.echo.EncryptExecutor;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.model.classes.L1ClassFeature;

import java.util.ArrayList;
import java.util.Map;

import com.lineage.server.model.L1PcRewardPrestigeGfxTimer;

import java.util.Random;

import org.apache.commons.logging.Log;

import com.lineage.server.model.L1Character;

public class L1PcInstance extends L1Character
{
    private static final Log _log;
    private static final long serialVersionUID = 1L;
    public static final int CLASSID_KNIGHT_MALE = 61;
    public static final int CLASSID_KNIGHT_FEMALE = 48;
    public static final int CLASSID_ELF_MALE = 138;
    public static final int CLASSID_ELF_FEMALE = 37;
    public static final int CLASSID_WIZARD_MALE = 734;
    public static final int CLASSID_WIZARD_FEMALE = 1186;
    public static final int CLASSID_DARK_ELF_MALE = 2786;
    public static final int CLASSID_DARK_ELF_FEMALE = 2796;
    public static final int CLASSID_PRINCE = 0;
    public static final int CLASSID_PRINCESS = 1;
    public static final int CLASSID_DRAGON_KNIGHT_MALE = 6658;
    public static final int CLASSID_DRAGON_KNIGHT_FEMALE = 6661;
    public static final int CLASSID_ILLUSIONIST_MALE = 6671;
    public static final int CLASSID_ILLUSIONIST_FEMALE = 6650;
    private static Random _random;
    private L1PcRewardPrestigeGfxTimer _gfxTimer4;
    private final Map<Integer, L1SkinInstance> _skins;
    private boolean _isKill;
    private ArrayList<String> _attackenemy;
    private ArrayList<String> _Badattackenemy;
    private short _hpr;
    private short _trueHpr;
    private short _mpr;
    private short _trueMpr;
    public short _originalHpr;
    public short _originalMpr;
    private boolean _mpRegenActive;
    private boolean _mpReductionActiveByAwake;
    private boolean _hpRegenActive;
    private int _hpRegenType;
    private int _hpRegenState;
    private int _mpRegenType;
    private int _mpRegenState;
    public static final int REGENSTATE_NONE = 4;
    public static final int REGENSTATE_MOVE = 2;
    public static final int REGENSTATE_ATTACK = 1;
    public static final int INTERVAL_BY_AWAKE = 4;
    private int _awakeMprTime;
    private int _awakeSkillId;
    private int _old_lawful;
    private int _old_karma;
    private boolean _jl1;
    private boolean _jl2;
    private boolean _jl3;
    private boolean _el1;
    private boolean _el2;
    private boolean _el3;
    private long _old_exp;
    private boolean _isCHAOTIC;
    private ArrayList<Integer> _skillList;
    private L1ClassFeature _classFeature;
    private int _PKcount;
    private int _PkCountForElf;
    private int _clanid;
    private String clanname;
    private int _clanRank;
    private byte _sex;
    private ArrayList<L1PrivateShopSellList> _sellList;
    private ArrayList<L1PrivateShopBuyList> _buyList;
    private byte[] _shopChat;
    private boolean _isPrivateShop;
    private boolean _isTradingInPrivateShop;
    private int _partnersPrivateShopItemCount;
    private EncryptExecutor _out;
    private static boolean _debug;
    public long _oldTime;
    private static final Map<Long, Double> _magicDamagerList;
    private int _originalEr;
    private ClientExecutor _netConnection;
    private int _classId;
    private int _type;
    private long _exp;
    private final L1Karma _karma;
    private boolean _gm;
    private boolean _monitor;
    private boolean _gmInvis;
    private short _accessLevel;
    private int _currentWeapon;
    private final L1PcInventory _inventory;
    private final L1DwarfInventory _dwarf;
    private final L1DwarfForChaInventory _dwarfForCha;
    private final L1DwarfForElfInventory _dwarfForElf;
    private L1ItemInstance _weapon;
    private L1Party _party;
    private L1ChatParty _chatParty;
    private int _partyID;
    private int _tradeID;
    private boolean _tradeOk;
    private int _tempID;
    private boolean _isTeleport;
    private boolean _isDrink;
    private boolean _isGres;
    private L1PcQuest _quest;
    private L1ActionPc _action;
    private L1ActionPet _actionPet;
    private L1ActionSummon _actionSummon;
    public short _temp;
    private L1EquipmentSlot _equipSlot;
    private String _accountName;
    L1Character target;
    private short _baseMaxHp;
    private short _baseMaxMp;
    public short _baseMaxMpc;
    private int _baseAc;
    private int _originalAc;
    private int _baseStr;
    private int _baseCon;
    private int _baseDex;
    private int _baseCha;
    private int _baseInt;
    private int _baseWis;
    private int _originalStr;
    private int _originalCon;
    private int _originalDex;
    private int _originalCha;
    private int _originalInt;
    private int _originalWis;
    private int _originalDmgup;
    private int _originalBowDmgup;
    private int _originalHitup;
    private int _originalBowHitup;
    private int _originalMr;
    private int _originalMagicHit;
    private int _originalMagicCritical;
    private int _originalMagicConsumeReduction;
    private int _originalMagicDamage;
    private int _originalHpup;
    private int _originalMpup;
    private int _baseDmgup;
    private int _baseBowDmgup;
    private int _baseHitup;
    private int _baseBowHitup;
    private int _baseMr;
    private int _advenHp;
    private int _advenMp;
    private int _highLevel;
    private int _bonusStats;
    private int _elixirStats;
    private int _elfAttr;
    private int _expRes;
    private int _partnerId;
    private int _onlineStatus;
    private int _homeTownId;
    private int _contribution;
    private int _hellTime;
    private boolean _banned;
    private int _food;
    private int invisDelayCounter;
    private Object _invisTimerMonitor;
    private static final long DELAY_INVIS = 3000L;
    private boolean _ghost;
    private int _ghostTime;
    private boolean _ghostCanTalk;
    private boolean _isReserveGhost;
    private int _ghostSaveLocX;
    private int _ghostSaveLocY;
    private short _ghostSaveMapId;
    private int _ghostSaveHeading;
    private Timestamp _lastPk;
    private Timestamp _lastPkForElf;
    private Timestamp _deleteTime;
    private double _weightUP;
    private int _weightReduction;
    private int _originalStrWeightReduction;
    private int _originalConWeightReduction;
    private int _hasteItemEquipped;
    private int _damageReductionByArmor;
    private int _hitModifierByArmor;
    private int _dmgModifierByArmor;
    private int _bowHitModifierByArmor;
    private int _bowDmgModifierByArmor;
    private boolean _gresValid;
    private boolean _isFishing;
    private int _fishX;
    private int _fishY;
    private int _cookingId;
    private int _dessertId;
    private final L1ExcludingList _excludingList;
    private int _teleportX;
    private int _teleportY;
    private short _teleportMapId;
    private int _teleportHeading;
    private int _tempCharGfxAtDead;
    private boolean _isCanWhisper;
    private boolean _isShowTradeChat;
    private boolean _isShowWorldChat;
    private int _fightId;
    private byte _chatCount;
    private long _oldChatTimeInMillis;
    private int _callClanId;
    private int _callClanHeading;
    private boolean _isInCharReset;
    private int _tempLevel;
    private int _tempMaxLevel;
    private boolean _isSummonMonster;
    private boolean _isShapeChange;
    private String _text;
    private byte[] _textByte;
    private L1PcOther _other;
    private L1PcOther1 _other1;
    private L1PcOther2 _other2;
    private L1PcOther3 _other3;
    private L1PcOtherList _otherList;
    private int _oleLocX;
    private int _oleLocY;
    private L1DeInstance _outChat;
    private long _h_time;
    private boolean _mazu;
    private long _mazu_time;
    private int _int1;
    private int _int2;
    private int _evasion;
    private double _expadd;
    private int _dd1;
    private int _dd2;
    private boolean _isFoeSlayer;
    private long _weaknss_t;
    private int _actionId;
    private Chapter01R _hardin;
    private final Map<Integer, L1ItemPower_text> _allpowers;
    private int _unfreezingTime;
    private int _misslocTime;
    private L1User_Power _c_power;
    private int _dice_hp;
    private int _sucking_hp;
    private int _dice_mp;
    private int _sucking_mp;
    private int _double_dmg;
    private int _lift;
    private int _magic_modifier_dmg;
    private int _magic_reduction_dmg;
    private boolean _rname;
    private boolean _retitle;
    private int _repass;
    private ArrayList<L1TradeItem> _trade_items;
    private int _mode_id;
    private boolean _check_item;
    private boolean _vip_1;
    private boolean _vip_2;
    private boolean _vip_3;
    private boolean _vip_4;
    private long _global_time;
    private int _doll_hpr;
    private int _doll_hpr_time;
    private int _doll_hpr_time_src;
    private int _doll_mpr;
    private int _doll_mpr_time;
    private int _doll_mpr_time_src;
    private int[] _doll_get;
    private int _doll_get_time;
    private int _doll_get_time_src;
    private String _board_title;
    private String _board_content;
    private long _spr_move_time;
    private long _spr_attack_time;
    private long _spr_skill_time;
    private int _delete_time;
    private int _up_hp_potion;
    private int _uhp_number;
    int _venom_resist;
//    private AcceleratorChecker _speed;
    private int _arena;
    private int _temp_adena;
    private int _temp_adena1;
    private int _temp_adena2;
    private long _ss_time;
    private int _ss;
    private int killCount;
    private int _meteLevel;
    private L1MeteAbility _meteAbility;
    private boolean _isProtector;
    private L1Apprentice _apprentice;
    private int _tempType;
    private Timestamp _punishTime;
    private int _magicDmgModifier;
    private int _magicDmgReduction;
    private int _elitePlateMail_Fafurion;
    private int _fafurion_hpmin;
    private int _fafurion_hpmax;
    private int _elitePlateMail_Lindvior;
    private int _lindvior_mpmin;
    private int _lindvior_mpmax;
    private int _hades_cloak;
    private int _hades_cloak_dmgmin;
    private int _hades_cloak_dmgmax;
    private int _Hexagram_Magic_Rune;
    private int _hexagram_hpmin;
    private int _hexagram_hpmax;
    private int _hexagram_gfx;
    private int _dimiter_mpr_rnd;
    private int _dimiter_mpmin;
    private int _dimiter_mpmax;
    private int _dimiter_bless;
    private int _dimiter_time;
    private int _expPoint;
    private int _pay;
    private int _SummonId;
    private int _lap;
    private int _lapCheck;
    private boolean _order_list;
    private int _state;
    private int followstep;
    private L1PcInstance followmaster;
    private int _fishingpoleid;
    private L1Character _target;
    private int _weaknss;
    private ArrayList<String> _InviteList;
    private ArrayList<String> _cmalist;
    private boolean _EffectDADIS;
    private boolean _EffectGS;
    private int _elitePlateMail_Valakas;
    private int _valakas_dmgmin;
    private int _valakas_dmgmax;
    private int _isBigHot;
    private String _bighot1;
    private String _bighot2;
    private String _bighot3;
    private String _bighot4;
    private String _bighot5;
    private String _bighot6;
    private boolean _isATeam;
    private boolean _isBTeam;
    private Timestamp _rejoinClanTime;
    private Timestamp _CreateTime;
    private int _partyType;
    private int _ubscore;
    private int _inputerror;
    private int _speederror;
    private int _banerror;
    private int _inputbanerror;
//    private final AcceleratorChecker _acceleratorChecker;
    private int _Slot;
    private boolean _itempoly;
    private boolean _itempoly1;
    private L1ItemInstance _polyscroll;
    private L1ItemInstance _polyscrol2;
    private L1ItemInstance _itembox;
    private L1EffectInstance _tomb;
    private boolean _isMagicCritical;
    private boolean _isPhantomTeleport;
    private int _rocksPrisonTime;
    private int _lastabardTime;
    private int _ivorytowerTime;
    private int _dragonvalleyTime;
    private boolean isTimeMap;
    private ConcurrentHashMap<Integer, Integer> mapTime;
    private int _clanMemberId;
    private String _clanMemberNotes;
    private int _stunlevel;
    private int _other_ReductionDmg;
    private int _Clan_ReductionDmg;
    private int _Clanmagic_reduction_dmg;
    private double _addExpByArmor;
    private int _PcContribution;
    private int _clanContribution;
    private int _clanadena;
    private String clanNameContribution;
    private boolean _checkgm;
    private boolean check_lv;
    private int _logpcpower_SkillCount;
    private int _logpcpower_SkillFor1;
    private int _logpcpower_SkillFor2;
    private int _logpcpower_SkillFor3;
    private int _logpcpower_SkillFor4;
    private int _logpcpower_SkillFor5;
    private int _EsotericSkill;
    private int _EsotericCount;
    private boolean _isEsoteric;
    private boolean _TripleArrow;
    private boolean _checklogpc;
    private int _savepclog;
    private int _ReductionDmg;
    private int _pcdmg;
    private int _paycount;
    private int _ArmorCount1;
    private int _logintime;
    private int _logintime1;
    public double _PartyExp;
    private boolean ATK_ai;
    private long _shopAdenaRecord;
    private int _dolldamageReductionByArmor;
    private int _weaponMD;
    private int _weaponMDC;
    private int _reduction_dmg;
    private double _GF;
    private boolean _isTeleportToOk;
    private boolean _MOVE_STOP;
    private int _amount;
    private final L1Inventory _tradewindow;
    private long _consume_point;
    private Map<Integer, Integer> _mapsList;
    private int _tempStr;
    private int _tempDex;
    private int _tempCon;
    private int _tempWis;
    private int _tempCha;
    private int _tempInt;
    private int _tempInitPoint;
    private int _tempElixirstats;
    private int weapondmg;
    private double _Dmgdouble;
    private int _PVPdmg;
    private int _PVPdmgReduction;
    private int _attr_potion_heal;
    private int _penetrate;
    private int _attr_物理格檔;
    private int _attr_魔法格檔;
    private int _NoweaponRedmg;
    private int _addStunLevel;
    private int _loginpoly;
    private int backX;
    private int backY;
    private int backHeading;
    private int _Imperius_Tshirt_rnd;
    private int _drainingHP_min;
    private int _drainingHP_max;
    private int _MoonAmulet_rnd;
    private int _MoonAmulet_dmg_min;
    private int _MoonAmulet_dmg_max;
    private int _MoonAmulet_gfxid;
    private int _AttrAmulet_rnd;
    private int _AttrAmulet_dmg;
    private int _AttrAmulet_gfxid;
    private int _range;
    private PcAttackThread attackThread;
    private int _day;
    private int _prestige;
    private int _prestigeLv;
    private long _oldexp;
    private boolean _isItemName;
    private boolean _isItemopen;
    private boolean _isfollow;
    private boolean _isfollowcheck;
    private int _poisonStatus2;
    private int _poisonStatus7;
    private boolean _isCraftsmanHeirloom;
    private boolean _isMarsSoul;
    private int _super;
    private int guaji_poly;
    protected final L1HateList _hateList;
    private boolean _firstAttack;
    protected NpcMoveExecutor _pcMove;
    private int move;
    private boolean _aiRunning;
    private boolean _actived;
    private boolean _Pathfinding;
    private int _randomMoveDirection;
    private int _tguajiX;
    private int _tguajiY;
    private int _guajiMapId;
    private int _armorbreaklevel;
    private int _FoeSlayerBonusDmg;
    private int _soulHp_r;
    private int _soulHp_hpmin;
    private int _soulHp_hpmax;
    private int isSoulHp;
    private ArrayList<Integer> soulHp;
    private String oldtitle;
    private String vipname;
    private int _PVPdmgg;
    private int _potion_healling;
    private int _potion_heal;
    private int _weaponSkillChance;
    private double _addWeaponSkillDmg;
    private String newaititle;
    private int _newaicount;
    private int _proctctran;
    private boolean _newcharpra;
    private int _guaji_count;
    private int _aibig;
    private int _aismall;
    private int _newaicount_2;
    private boolean _opengfxid;
    public int ValakasStatus;
    private int _AiGxfxid;
    private int _Aierror;
    private int _add_er;
    private int moveErrorCount;
    private boolean moveStatus;
    private int _followskilltype;
    private int _followskillhp;
    private boolean _followmebuff;
    private int _ItemBlendcheckitem;
    private String _ItemBlendcheckitemname;
    private String _ItemBlendAllmsg;
    private int _ItemBlendcheckitemcount;
    private int _ItemBlendResdueItem;
    private int _ItemBlendResdueItemcount;
    private int _ItemBlendResdueItemLv;
    private int _ItemBlendrnd;
    private int _ItemBlendGvEnchantlvl;
    private int _hppotion;
    private int _pvp;
    private int _bowpvp;
    private int _followxy1;
    private int _polyarrow;
    private int callclanal;
    private int callclana2;
    private int _changtype1;
    private int _changtype2;
    private int _changtype3;
    private int _changtype4;
    private int _changtype5;
    private String changtypename1;
    private String changtypename2;
    private String changtypename3;
    private String changtypename4;
    private int _pag;
    private boolean _keyenemy;
    private boolean _outenemy;
    private double _npcdmg;
    private int _newai1;
    private int _newai2;
    private int _newai3;
    private int _newai4;
    private int _newai5;
    private int _newai6;
    private int _newaiq1;
    private int _newaiq2;
    private int _newaiq3;
    private int _newaiq4;
    private int _newaiq5;
    private int _newaiq6;
    private int _newaiq7;
    private int _newaiq8;
    private int _newaiq9;
    private int _newaiq0;
    private int _npciddmg;
    private boolean _followatk;
    private boolean _followatkmagic;
    private boolean _isfollowskill26;
    private boolean _isfollowskill42;
    private boolean _isfollowskill55;
    private boolean _isfollowskill68;
    private boolean _isfollowskill160;
    private boolean _isfollowskill79;
    private boolean _isfollowskill148;
    private boolean _isfollowskill151;
    private boolean _isfollowskill149;
    private boolean _isfollowskill158;
    private boolean _isnomoveguaji;
    private boolean _Badkeyenemy;
    private boolean _Badoutenemy;
    private short _oldMapId;
    private boolean _ischeckpoly;
    private String _itemactionhtml;
    private boolean _isOutbur;
    private boolean _ischeckOutbur;
    private int _Quburcount;
    private int _WeaponTotalDmg;
    private int _weaknss1;
    private int _WeaponSkillPro;
    private int _Save_Quest_Map1;
    private int _Save_Quest_Map2;
    private int _Save_Quest_Map3;
    private int _Save_Quest_Map4;
    private int _Save_Quest_Map5;
    private int _CardId;
    Thread _tempThread;
    private int soulTower;
    private boolean _isarmor_setgive;
    private String _Summon_npcid;
    private int _summon_skillid;
    private int _summon_skillidmp;
    private int _towerIsWhat;
    private int _Doll_MagicHit;
    private int _first_pay;
    private boolean _au_shop;
    private int[] _au_buyitemswitch;
    private int[] _au_buyitemcount;
    private boolean _au_setshop;
    private int _setshoptype;
    private int[] _au_automagic;
    private int _a53;
    private int _summon_skillidmp_1;
    private int[] _au_autoset;
    int _autoX;
    int _autoy;
    int _autom;
    private int[] _au_otherautoset;
    private int _cmpcount;
    private double _weapon_b;
    private int _weapon_b_gfx_r;
    private int[] _weapon_b_gfx;
    private int _backpage;
    private L1ItemInstance _polyscrol3;
    private int Dmgup_b_ran;
    public double Dmgup_b;
    private int _dollcount;
    private int _dollcount1;
    private int _dollcount_itemid;
    private int _dollcount1_itemid;
    private int bosspage;
    private ArrayList<Integer> _autobuff;
    private long skill_timeDelay;
    private int _cardpoly;
    private int _pcezpay;
    private String _oldAllName;
    private int[] _blendcheckitemcount;
    private int[] _blendcheckitemen;
    private int[] _blendcheckitem;
    private SustainEffect2 SustainEffect2;
    private boolean _isClanGfx;
    private int _wenyangjifen;
    private int _wytype1;
    private int _wytype2;
    private int _wytype3;
    private int _wytype4;
    private int _wytype5;
    private int _wylevel1;
    private int _wylevel2;
    private int _wylevel3;
    private int _wylevel4;
    private int _wylevel5;
    private int _wyjiajilv;
    private int _wyjianjilv;
    private static Timer _regenTimer;
    private String TheEnemy;
    private int _weaponskillpro;
    private int _weaponskilldmg;
    private int _effect_gfxid;
    private int _effect_time;
    private int _hpr_time;
    private int _hprr;
    private int _hpr_gfxid;
    private int _mpr_time;
    private int _mprr;
    private int _mpr_gfxid;
    private int _DmgReductionChance;
    private int _DmgReductionDmg;
    private int _近戰爆擊發動率;
    public double _近戰爆擊倍率;
    private int _遠攻爆擊發動率;
    public double _遠攻爆擊倍率;
    private int _魔法爆擊發動率;
    public double _魔法爆擊倍率;
    private int _吸取HP機率;
    private int _吸取HP固定值;
    private int _吸取HP隨機值;
    private int _吸取HP動畫;
    private int _吸取MP機率;
    private int _吸取MP固定值;
    private int _吸取MP隨機值;
    private int _吸取MP動畫;
    private int _攻擊力發動機率;
    private int _攻擊力固定值;
    private int _攻擊力隨機值;
    private int _攻擊力動畫;
    private int _dollcheck;
    private int _dollcheckitem;
    private int _dollcheck1;
    private int _dollcheckitem1;
    private int _comtext0;
    private String _comtextc0;
    private int _comtext1;
    private String _comtextc1;
    private int _comtext2;
    private String _comtextc2;
    private int _comtext3;
    private String _comtextc3;
    private int _comtext4;
    private String _comtextc4;
    private int _comtext5;
    private String _comtextc5;
    private int _comtext6;
    private String _comtextc6;
    private int _comtext7;
    private String _comtextc7;
    private int _comtext8;
    private String _comtextc8;
    private int _comtext9;
    private String _comtextc9;
    private int _comtext10;
    private String _comtextc10;
    private int _comtext11;
    private String _comtextc11;
    private int _comtext12;
    private String _comtextc12;
    private int _comtext13;
    private String _comtextc13;
    private int _comtext14;
    private String _comtextc14;
    private int _comtext15;
    private String _comtextc15;
    private int _comtext16;
    private String _comtextc16;
    private int _comtext17;
    private String _comtextc17;
    private int _comtext18;
    private String _comtextc18;
    private int _comtext19;
    private String _comtextc19;
    private int _comtext20;
    private String _comtextc20;
    private int _polyactidran;
    private int _polyactiddmg;
    private int _polyactiddmg1;
    private int _polyactidactid;
    private int _cardspoly;
    private int _polycheck;
    private int[] _armortype;
    private int _adenapoint;
    private int _weaponchp;
    private int _weaponcmp;
    private int _magicdmg;
    private int _weaponran;
    private int _weapongfx;
    private int declareId;
    private int _iceTime;
    private int opendoll;
    private String _savetitle;
    private int rantitle;
    private int _aierrorcheck;
    private int _eqconitem;
    private boolean _vviipp;
    private String _checkip;
    private String _checkip1;
    private boolean _is_bounce_attack;
    
    private int _Critical = 0;// 近距離暴擊率

	/**
	 * 取回近距離暴擊率
	 * 
	 * @return
	 */
	public int get_Critical() {
		return _Critical;
	}

	/**
	 * 增加近距離暴擊率
	 * 
	 * @param _Critical
	 */
	public void add_Critical(int _Critical) {
		this._Critical += _Critical;
	}

	private int _Bow_Critical = 0;// 遠距離暴擊率

	/**
	 * 取回遠距離暴擊率
	 * 
	 * @return
	 */
	public int get_Bow_Critical() {
		return _Bow_Critical;
	}

	/**
	 * 增加遠距離暴擊率
	 * 
	 * @param _Bow_Critical
	 */
	public void add_Bow_Critical(int _Bow_Critical) {
		this._Bow_Critical = _Bow_Critical;
	}
    
	private final AcceleratorChecker _speedCheck = new AcceleratorChecker(this); // 加速器檢知機能

	/**
	 * 獲取速度控制模塊
	 * @return
	 * */
	public AcceleratorChecker getSpeedCheck() {
		return this._speedCheck;
	}
	
	// 加速檢測器
	private AcceleratorChecker _speed = null;
	
	/**
	 * 加速檢測器
	 * @return
	 */
	public AcceleratorChecker speed_Attack() {
		if (_speed == null) {
			_speed = new AcceleratorChecker(this);
		}
		return _speed;
	}
	
    static {
        _log = LogFactory.getLog((Class)L1PcInstance.class);
        L1PcInstance._random = new Random();
        L1PcInstance._debug = Config.DEBUG;
        _magicDamagerList = new HashMap<Long, Double>();
        L1PcInstance._regenTimer = new Timer(true);
    }
    
    public void load_src() {
        this._old_exp = this.getExp();
        this._old_lawful = this.getLawful();
        this._old_karma = this.getKarma();
    }
    
    public boolean is_isKill() {
        return this._isKill;
    }
    
    public void set_isKill(final boolean _isKill) {
        this._isKill = _isKill;
    }
    
    public short getHpr() {
        return this._hpr;
    }
    
    public void addHpr(final int i) {
        this._trueHpr += (short)i;
        this._hpr = (short)Math.max(0, this._trueHpr);
    }
    
    public short getMpr() {
        return this._mpr;
    }
    
    public void addMpr(final int i) {
        this._trueMpr += (short)i;
        this._mpr = (short)Math.max(0, this._trueMpr);
    }
    
    public short getOriginalHpr() {
        return this._originalHpr;
    }
    
    public short getOriginalMpr() {
        return this._originalMpr;
    }
    
    public int getHpRegenState() {
        return this._hpRegenState;
    }
    
    public void set_hpRegenType(final int hpRegenType) {
        this._hpRegenType = hpRegenType;
    }
    
    public int hpRegenType() {
        return this._hpRegenType;
    }
    
    private int regenMax() {
        final int[] lvlTable = { 30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2 };
        int regenLvl = Math.min(10, this.getLevel());
        if (30 <= this.getLevel() && this.isKnight()) {
            regenLvl = 11;
        }
        return lvlTable[regenLvl - 1] << 2;
    }
    
    public boolean isRegenHp() {
        if (this._temp != 0) {
            this._accessLevel = this._temp;
        }
        if (!this._hpRegenActive) {
            return false;
        }
        if (this.hasSkillEffect(169) || this.hasSkillEffect(176)) {
            return this._hpRegenType >= this.regenMax();
        }
        return 120 > this._inventory.getWeight240() && this._food >= 3 && this._hpRegenType >= this.regenMax();
    }
    
    public int getMpRegenState() {
        return this._mpRegenState;
    }
    
    public void set_mpRegenType(final int hpmpRegenType) {
        this._mpRegenType = hpmpRegenType;
    }
    
    public int mpRegenType() {
        return this._mpRegenType;
    }
    
    public boolean isRegenMp() {
        if (!this._mpRegenActive) {
            return false;
        }
        if (this._food < 3) {
            return false;
        }
        final int weight = this._inventory.getWeight240();
        if (weight >= 120) {
            if (!this.hasSkillEffect(169) && !this.hasSkillEffect(176) && !this.hasSkillEffect(190)) {
                return false;
            }
            if (weight >= 197) {
                return false;
            }
        }
        return this._mpRegenType >= 64;
    }
    
    public void setRegenState(final int state) {
        this._mpRegenState = state;
        this._hpRegenState = state;
    }
    
    public int getRegenState() {
        return this._state;
    }
    
    public void startHpRegeneration() {
        if (!this._hpRegenActive) {
            this._hpRegenActive = true;
        }
    }
    
    public void stopHpRegeneration() {
        if (this._hpRegenActive) {
            this._hpRegenActive = false;
        }
    }
    
    public boolean getHpRegeneration() {
        return this._hpRegenActive;
    }
    
    public void startMpRegeneration() {
        if (!this._mpRegenActive) {
            this._mpRegenActive = true;
        }
    }
    
    public void stopMpRegeneration() {
        if (this._mpRegenActive) {
            this._mpRegenActive = false;
        }
    }
    
    public boolean getMpRegeneration() {
        return this._mpRegenActive;
    }
    
    public int get_awakeMprTime() {
        return this._awakeMprTime;
    }
    
    public void set_awakeMprTime(final int awakeMprTime) {
        this._awakeMprTime = awakeMprTime;
    }
    
    public void startMpReductionByAwake() {
        if (!this._mpReductionActiveByAwake) {
            this.set_awakeMprTime(4);
            this._mpReductionActiveByAwake = true;
        }
    }
    
    public void stopMpReductionByAwake() {
        if (this._mpReductionActiveByAwake) {
            this.set_awakeMprTime(0);
            this._mpReductionActiveByAwake = false;
        }
    }
    
    public boolean isMpReductionActiveByAwake() {
        return this._mpReductionActiveByAwake;
    }
    
    public int getAwakeSkillId() {
        return this._awakeSkillId;
    }
    
    public void setAwakeSkillId(final int i) {
        this._awakeSkillId = i;
    }
    
    public void startObjectAutoUpdate() {
        this.removeAllKnownObjects();
    }
    
    public void stopEtcMonitor() {
        this.set_ghostTime(-1);
        this.setGhost(false);
        this.setGhostCanTalk(true);
        this.setReserveGhost(false);
        this.set_mazu_time(0L);
        this.set_mazu(false);
        this.stopMpReductionByAwake();
        if (ServerUseMapTimer.MAP.get(this) != null) {
            ServerUseMapTimer.MAP.remove(this);
        }
        OnlineGiftSet.remove(this);
        ListMapUtil.clear(this._skillList);
        ListMapUtil.clear(this._sellList);
        ListMapUtil.clear(this._buyList);
        ListMapUtil.clear(this._trade_items);
        ListMapUtil.clear(this._allpowers);
    }
    
    public int getLawfulo() {
        return this._old_lawful;
    }
    
    public void onChangeLawful() {
        if (this._old_lawful != this.getLawful()) {
            this._old_lawful = this.getLawful();
            this.sendPacketsAll(new S_Lawful(this));
            this.lawfulUpdate();
        }
    }
    
    public int getKarmalo() {
        return this._old_karma;
    }
    
    public void onChangeKarma() {
        if (this._old_karma != this.getKarma()) {
            this._old_karma = this.getKarma();
            this.sendPackets(new S_Karma(this));
        }
    }
    
    public void lawfulUpdate() {
        final int l = this.getLawful();
        if (l >= 10000 && l <= 19999) {
            if (!this._jl1) {
                this.overUpdate();
                this._jl1 = true;
                this.sendPackets(new S_PacketBoxProtection(0, 1));
                this.sendPackets(new S_OwnCharAttrDef(this));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (l >= 20000 && l <= 29999) {
            if (!this._jl2) {
                this.overUpdate();
                this._jl2 = true;
                this.sendPackets(new S_PacketBoxProtection(1, 1));
                this.sendPackets(new S_OwnCharAttrDef(this));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (l >= 30000 && l <= 39999) {
            if (!this._jl3) {
                this.overUpdate();
                this._jl3 = true;
                this.sendPackets(new S_PacketBoxProtection(2, 1));
                this.sendPackets(new S_OwnCharAttrDef(this));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (l >= -19999 && l <= -10000) {
            if (!this._el1) {
                this.overUpdate();
                this._el1 = true;
                this.sendPackets(new S_PacketBoxProtection(3, 1));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (l >= -29999 && l <= -20000) {
            if (!this._el2) {
                this.overUpdate();
                this._el2 = true;
                this.sendPackets(new S_PacketBoxProtection(4, 1));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (l >= -39999 && l <= -30000) {
            if (!this._el3) {
                this.overUpdate();
                this._el3 = true;
                this.sendPackets(new S_PacketBoxProtection(5, 1));
                this.sendPackets(new S_SPMR(this));
            }
        }
        else if (this.overUpdate()) {
            this.sendPackets(new S_OwnCharAttrDef(this));
            this.sendPackets(new S_SPMR(this));
        }
    }
    
    private boolean overUpdate() {
        if (this._jl1) {
            this._jl1 = false;
            this.sendPackets(new S_PacketBoxProtection(0, 0));
            return true;
        }
        if (this._jl2) {
            this._jl2 = false;
            this.sendPackets(new S_PacketBoxProtection(1, 0));
            return true;
        }
        if (this._jl3) {
            this._jl3 = false;
            this.sendPackets(new S_PacketBoxProtection(2, 0));
            return true;
        }
        if (this._el1) {
            this._el1 = false;
            this.sendPackets(new S_PacketBoxProtection(3, 0));
            return true;
        }
        if (this._el2) {
            this._el2 = false;
            this.sendPackets(new S_PacketBoxProtection(4, 0));
            return true;
        }
        if (this._el3) {
            this._el3 = false;
            this.sendPackets(new S_PacketBoxProtection(5, 0));
            return true;
        }
        return false;
    }
    
    private boolean isEncounter() {
        return this.getLevel() <= ConfigOther.ENCOUNTER_LV;
    }
    
    public int guardianEncounter() {
        if (this._jl1) {
            return 0;
        }
        if (this._jl2) {
            return 1;
        }
        if (this._jl3) {
            return 2;
        }
        if (this._el1) {
            return 3;
        }
        if (this._el2) {
            return 4;
        }
        if (this._el3) {
            return 5;
        }
        return -1;
    }
    
    public long getExpo() {
        return this._old_exp;
    }
    
    public void onChangeExp() {
        if (this._old_exp != this.getExp()) {
            this._old_exp = this.getExp();
            final int level = ExpTable.getLevelByExp(this.getExp());
            final int char_level = this.getLevel();
            final int gap = level - char_level;
            if (gap == 0) {
                if (level <= 127) {
                    this.sendPackets(new S_Exp(this));
                }
                else {
                    this.sendPackets(new S_OwnCharStatus(this));
                }
                return;
            }
            if (gap > 0) {
                this.levelUp(gap);
            }
            else if (gap < 0) {
                this.levelDown(gap);
            }
            if (this.getLevel() > ConfigOther.ENCOUNTER_LV) {
                this.sendPackets(new S_PacketBoxProtection(6, 0));
            }
            else {
                this.sendPackets(new S_PacketBoxProtection(6, 1));
            }
        }
    }
    
    @Override
    public void onPerceive(final L1PcInstance perceivedFrom) {
        try {
            if (this.isGmInvis() || this.isGhost() || this.isInvisble()) {
                return;
            }
            if (perceivedFrom.get_showId() != this.get_showId()) {
                return;
            }
            perceivedFrom.addKnownObject(this);
            perceivedFrom.sendPackets(new S_OtherCharPacks(this));
            if (this.isInParty() && this.getParty().isMember(perceivedFrom)) {
                perceivedFrom.sendPackets(new S_HPMeter(this));
            }
            if (this._isFishing) {
                perceivedFrom.sendPackets(new S_Fishing(this.getId(), 71, this.get_fishX(), this.get_fishY()));
            }
            if (this.isPrivateShop()) {
                final int mapId = this.getMapId();
                if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370 && mapId != 800) {
                    this.getSellList().clear();
                    this.getBuyList().clear();
                    this.setPrivateShop(false);
                    this.sendPacketsAll(new S_DoActionGFX(this.getId(), 3));
                }
                else {
                    perceivedFrom.sendPackets(new S_DoActionShop(this.getId(), this.getShopChat()));
                }
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void removeOutOfRangeObjects() {
        for (final L1Object known : this.getKnownObjects()) {
            if (known != null) {
                if (Config.PC_RECOGNIZE_RANGE == -1) {
                    if (this.getLocation().isInScreen(known.getLocation())) {
                        continue;
                    }
                    this.removeKnownObject(known);
                    this.sendPackets(new S_RemoveObject(known));
                }
                else {
                    if (this.getLocation().getTileLineDistance(known.getLocation()) <= Config.PC_RECOGNIZE_RANGE) {
                        continue;
                    }
                    this.removeKnownObject(known);
                    this.sendPackets(new S_RemoveObject(known));
                }
            }
        }
    }
    
    public int get_followstep() {
        return this.followstep;
    }
    
    public void set_followstep(final int _followstep) {
        this.followstep = _followstep;
    }
    
    public L1PcInstance get_followmaster() {
        return this.followmaster;
    }
    
    public void set_followmaster(final L1PcInstance _followmaster) {
        this.followmaster = _followmaster;
    }
    
    public void updateObject() {
        if (this.getOnlineStatus() != 1) {
            return;
        }
        this.removeOutOfRangeObjects();
        for (final L1Object visible : World.get().getVisibleObjects(this, Config.PC_RECOGNIZE_RANGE)) {
            if (visible instanceof L1MerchantInstance) {
                if (this.knownsObject(visible)) {
                    continue;
                }
                final L1MerchantInstance npc = (L1MerchantInstance)visible;
                npc.onPerceive(this);
            }
            else if (visible instanceof L1DwarfInstance) {
                if (this.knownsObject(visible)) {
                    continue;
                }
                final L1DwarfInstance npc2 = (L1DwarfInstance)visible;
                npc2.onPerceive(this);
            }
            else if (visible instanceof L1FieldObjectInstance) {
                if (this.knownsObject(visible)) {
                    continue;
                }
                final L1FieldObjectInstance npc3 = (L1FieldObjectInstance)visible;
                npc3.onPerceive(this);
            }
            else {
                if (visible.get_showId() != this.get_showId()) {
                    continue;
                }
                if (!this.knownsObject(visible)) {
                    visible.onPerceive(this);
                }
                else if (visible instanceof L1NpcInstance) {
                    final L1NpcInstance npc4 = (L1NpcInstance)visible;
                    if (this.getLocation().isInScreen(npc4.getLocation()) && npc4.getHiddenStatus() != 0) {
                        npc4.approachPlayer(this);
                    }
                }
                if (this.isHpBarTarget(visible)) {
                    final L1Character cha = (L1Character)visible;
                    cha.broadcastPacketHP(this);
                }
                if (!this.hasSkillEffect(2001)) {
                    continue;
                }
                if (!this.isGmHpBarTarget(visible)) {
                    continue;
                }
                final L1Character cha = (L1Character)visible;
                cha.broadcastPacketHP(this);
            }
        }
        if (this.get_followmaster() != null) {
            final int dir = this.targetDirection(this.get_followmaster().getX(), this.get_followmaster().getY());
            if (this.get_followmaster().getMapId() == this.getMapId() && this.getLocation().getTileLineDistance(this.get_followmaster().getLocation()) > 3) {
                L1Teleport.teleport(this, this.get_followmaster().getLocation(), dir, false);
            }
            else if (this.get_followmaster().getMapId() != this.getMapId() && ConfigGuaji.followtele) {
                final int[] follow_map = ConfigGuaji.N_followmap;
                final int[] arrayOfInt1;
                final int i = (arrayOfInt1 = follow_map).length;
                for (byte b = 0; b < i; ++b) {
                    final int follmap = arrayOfInt1[b];
                    if (this.get_followmaster().getMapId() == follmap) {
                        this.set_followmaster(null);
                        this.set_followstep(0);
                        this.setfollow(false);
                        this.sendPackets(new S_ServerMessage("跟隨對的對象地圖無法跟隨"));
                        this.sendPackets(new S_ServerMessage("並取消自動高寵模式"));
                        if (this.getfollowatk()) {
                            this.setfollowatk(false);
                        }
                        if (this.isActived()) {
                            this.setActived(false);
                            L1PcUnlock.Pc_Unlock(this);
                            if (this.getAutoX() > 0) {
                                this.setAutoX(0);
                                this.setAutoY(0);
                                this.setAutoMap(0);
                            }
                            this.killSkillEffectTimer(8853);
                        }
                        L1Teleport.teleport(this, this.getLocation(), this.getHeading(), false);
                        return;
                    }
                }
                L1Teleport.teleport(this, this.get_followmaster().getLocation(), dir, false);
            }
            else if (this.get_followmaster().getOnlineStatus() != 1 || !this.get_followmaster().isfollowcheck() || L1CastleLocation.checkInAllWarArea(this.followmaster.getX(), this.followmaster.getY(), this.followmaster.getMapId()) || this.get_followmaster().getMapId() != this.getMapId() || !this.followmaster.isfollow()) {
                this.set_followmaster(null);
                this.set_followstep(0);
                this.setfollow(false);
                this.sendPackets(new S_ServerMessage("跟隨對像遺失,取消自動高寵模式"));
                if (this.getfollowatk()) {
                    this.setfollowatk(false);
                }
                if (this.isActived()) {
                    this.setActived(false);
                    L1PcUnlock.Pc_Unlock(this);
                    if (this.getAutoX() > 0) {
                        this.setAutoX(0);
                        this.setAutoY(0);
                        this.setAutoMap(0);
                    }
                    this.killSkillEffectTimer(8853);
                }
                L1Teleport.teleport(this, this.getLocation(), this.getHeading(), false);
            }
            else if (this.getLocation().getTileLineDistance(this.get_followmaster().getLocation()) > 1 && this.getLocation().getTileLineDistance(this.get_followmaster().getLocation()) < 8) {
                int locx = this.getX();
                int locy = this.getY();
                locx += L1PcInstance.HEADING_TABLE_X[dir];
                locy += L1PcInstance.HEADING_TABLE_Y[dir];
                final boolean isPassable1 = this.getMap().isOriginalTile(locx, locy);
                final boolean isPassable2 = this.checkPassable(locx, locy);
                if (isPassable1 && !isPassable2) {
                    this.setHeading(dir);
                    this.getMap().setPassable(this.getLocation(), true);
                    this.setX(locx);
                    this.setY(locy);
                    this.getMap().setPassable(this.getLocation(), false);
                    this.broadcastPacketAll(new S_MoveCharPacket(this));
                    this.sendPackets(new S_MoveCharPacket(this));
                    if (this.get_followstep() > 7) {
                        L1Teleport.teleport(this, this.getLocation(), dir, false);
                        this.set_followstep(0);
                    }
                    this.set_followstep(this.get_followstep() + 1);
                }
            }
            else if (!World.get().getVisibleObjects(this, this.followmaster)) {
                L1Teleport.teleport(this, this.followmaster.getX(), this.followmaster.getY(), this.followmaster.getMapId(), 5, false);
            }
        }
    }
    
    public boolean isHpBarTarget(final L1Object obj) {
        switch (this.getMapId()) {
            case 400: {
                if (!(obj instanceof L1FollowerInstance)) {
                    break;
                }
                final L1FollowerInstance follower = (L1FollowerInstance)obj;
                if (follower.getMaster().equals(this)) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    public boolean isGmHpBarTarget(final L1Object obj) {
        return obj instanceof L1PetInstance || obj instanceof L1MonsterInstance || obj instanceof L1SummonInstance || obj instanceof L1DeInstance || obj instanceof L1FollowerInstance;
    }
    
    public boolean GmHpBarForPc(final L1Object obj) {
        return obj instanceof L1PcInstance;
    }
    
    private void sendVisualEffect() {
        int poisonId = 0;
        if (this.getPoison() != null) {
            poisonId = this.getPoison().getEffectId();
        }
        if (this.getParalysis() != null) {
            poisonId = this.getParalysis().getEffectId();
        }
        if (poisonId != 0) {
            this.sendPacketsAll(new S_Poison(this.getId(), poisonId));
        }
    }
    
    public void sendVisualEffectAtLogin() {
        this.sendVisualEffect();
    }
    
    public boolean isCHAOTIC() {
        return this._isCHAOTIC;
    }
    
    public void setCHAOTIC(final boolean flag) {
        this._isCHAOTIC = flag;
    }
    
    public void sendVisualEffectAtTeleport() {
        if (this.isDrink()) {
            this.sendPackets(new S_Liquor(this.getId()));
        }
        if (this.isCHAOTIC()) {
            this.sendPackets(new S_Liquor(this.getId(), 2));
        }
        this.sendVisualEffect();
    }
    
    public void setSkillMastery(final int skillid) {
        if (!this._skillList.contains(new Integer(skillid))) {
            this._skillList.add(new Integer(skillid));
        }
    }
    
    public void removeSkillMastery(final int skillid) {
        if (this._skillList.contains(new Integer(skillid))) {
            this._skillList.remove(new Integer(skillid));
        }
    }
    
    public boolean isSkillMastery(final int skillid) {
        return this._skillList.contains(new Integer(skillid));
    }
    
    public void clearSkillMastery() {
        this._skillList.clear();
    }
    
    @Override
    public void setCurrentHp(final int i) {
        int currentHp = Math.min(i, this.getMaxHp());
        if (this.getCurrentHp() == currentHp) {
            return;
        }
        if (currentHp <= 0) {
            if (this.isGm()) {
                currentHp = this.getMaxHp();
            }
            else if (!this.isDead()) {
                this.death(null);
                this.followmaster.set_followmaster(null);
                this.followmaster.set_followstep(0);
                this.followmaster.setfollow(false);
                this.set_followmaster(null);
                this.set_followstep(0);
                this.setfollow(false);
                this.setActived(false);
            }
        }
        this.setCurrentHpDirect(currentHp);
        this.sendPackets(new S_HPUpdate(currentHp, this.getMaxHp()));
        if (this.isInParty()) {
            this.getParty().updateMiniHP(this);
        }
    }
    
    @Override
    public void setCurrentMp(final int i) {
        final int currentMp = Math.min(i, this.getMaxMp());
        if (this.getCurrentMp() == currentMp) {
            return;
        }
        this.setCurrentMpDirect(currentMp);
        this.sendPackets(new S_MPUpdate(currentMp, this.getMaxMp()));
    }
    
    @Override
    public L1PcInventory getInventory() {
        return this._inventory;
    }
    
    public L1DwarfInventory getDwarfInventory() {
        return this._dwarf;
    }
    
    public L1DwarfForChaInventory getDwarfForChaInventory() {
        return this._dwarfForCha;
    }
    
    public L1DwarfForElfInventory getDwarfForElfInventory() {
        return this._dwarfForElf;
    }
    
    public boolean isGmInvis() {
        return this._gmInvis;
    }
    
    public void setGmInvis(final boolean flag) {
        this._gmInvis = flag;
    }
    
    public int getCurrentWeapon() {
        return this._currentWeapon;
    }
    
    public void setCurrentWeapon(final int i) {
        this._currentWeapon = i;
    }
    
    public int getType() {
        return this._type;
    }
    
    public void setType(final int i) {
        this._type = i;
    }
    
    public short getAccessLevel() {
        return this._accessLevel;
    }
    
    public void setAccessLevel(final short i) {
        this._accessLevel = i;
    }
    
    public int getClassId() {
        return this._classId;
    }
    
    public void setClassId(final int i) {
        this._classId = i;
        this._classFeature = L1ClassFeature.newClassFeature(i);
    }
    
    public L1ClassFeature getClassFeature() {
        return this._classFeature;
    }
    
    @Override
    public synchronized long getExp() {
        return this._exp;
    }
    
    @Override
    public synchronized void setExp(final long i) {
        if (!this.isAddExp(i)) {
            return;
        }
        this._exp = i;
    }
    
    public int get_PKcount() {
        return this._PKcount;
    }
    
    public void set_PKcount(final int i) {
        this._PKcount = i;
    }
    
    public int getPkCountForElf() {
        return this._PkCountForElf;
    }
    
    public void setPkCountForElf(final int i) {
        this._PkCountForElf = i;
    }
    
    public int getClanid() {
        return this._clanid;
    }
    
    public void setClanid(final int i) {
        this._clanid = i;
    }
    
    public String getClanname() {
        return this.clanname;
    }
    
    public void setClanname(final String s) {
        this.clanname = s;
    }
    
    public L1Clan getClan() {
        return WorldClan.get().getClan(this.getClanname());
    }
    
    public int getClanRank() {
        return this._clanRank;
    }
    
    public void setClanRank(final int i) {
        this._clanRank = i;
    }
    
    public byte get_sex() {
        return this._sex;
    }
    
    public void set_sex(final int i) {
        this._sex = (byte)i;
    }
    
    public boolean isGm() {
        return this._gm;
    }
    
    public void setGm(final boolean flag) {
        this._gm = flag;
    }
    
    public boolean isMonitor() {
        return this._monitor;
    }
    
    public void setMonitor(final boolean flag) {
        this._monitor = flag;
    }
    
    private L1PcInstance getStat() {
        return null;
    }
    
    public void reduceCurrentHp(final double d, final L1Character l1character) {
        this.getStat().reduceCurrentHp(d, l1character);
    }
    
    private void notifyPlayersLogout(final List<L1PcInstance> playersArray) {
        for (final L1PcInstance player : playersArray) {
            if (player.knownsObject(this)) {
                player.removeKnownObject(this);
                player.sendPackets(new S_RemoveObject(this));
            }
        }
    }
    
    public void logout() {
        final L1EffectInstance tomb = this.get_tomb();
        if (tomb != null) {
            tomb.broadcastPacketAll(new S_DoActionGFX(tomb.getId(), 8));
            tomb.deleteMe();
        }
        CharBuffReading.get().deleteBuff(this);
        CharBuffReading.get().saveBuff(this);
        this.getMap().setPassable(this.getLocation(), true);
        if (this.getClanid() != 0) {
            final L1Clan l1Clan = WorldClan.get().getClan(this.getClanname());
            if (l1Clan != null && l1Clan.getWarehouseUsingChar() == this.getId()) {
                l1Clan.setWarehouseUsingChar(0);
            }
        }
        this.notifyPlayersLogout(this.getKnownPlayers());
        if (this.get_showId() != -1 && WorldQuest.get().isQuest(this.get_showId())) {
            WorldQuest.get().remove(this.get_showId(), this);
        }
        this.set_showId(-1);
        World.get().removeVisibleObject(this);
        World.get().removeObject(this);
        this.notifyPlayersLogout(World.get().getRecognizePlayer(this));
        this.removeAllKnownObjects();
        this.stopHpRegeneration();
        this.stopMpRegeneration();
        this.setDead(true);
        this.setNetConnection(null);
        final L1Clan clan = WorldClan.get().getClan(this.getClanname());
        if (clan != null) {
            if (clan.getWarehouseUsingChar() == this.getId()) {
                clan.setWarehouseUsingChar(0);
            }
            clan.CheckClan_Exp20(null);
        }
        if (Config.GUI) {
            J_Main.getInstance().delPlayerTable(this.getName());
        }
    }
    
    public ClientExecutor getNetConnection() {
        return this._netConnection;
    }
    
    public void setNetConnection(final ClientExecutor clientthread) {
        this._netConnection = clientthread;
    }
    
    public boolean isInParty() {
        return this.getParty() != null;
    }
    
    public L1Party getParty() {
        return this._party;
    }
    
    public void setParty(final L1Party p) {
        this._party = p;
    }
    
    public boolean isInChatParty() {
        return this.getChatParty() != null;
    }
    
    public L1ChatParty getChatParty() {
        return this._chatParty;
    }
    
    public void setChatParty(final L1ChatParty cp) {
        this._chatParty = cp;
    }
    
    public int getPartyID() {
        return this._partyID;
    }
    
    public void setPartyID(final int partyID) {
        this._partyID = partyID;
    }
    
    public int getTradeID() {
        return this._tradeID;
    }
    
    public void setTradeID(final int tradeID) {
        this._tradeID = tradeID;
    }
    
    public void setTradeOk(final boolean tradeOk) {
        this._tradeOk = tradeOk;
    }
    
    public boolean getTradeOk() {
        return this._tradeOk;
    }
    
    public int getTempID() {
        return this._tempID;
    }
    
    public void setTempID(final int tempID) {
        this._tempID = tempID;
    }
    
    public boolean isTeleport() {
        return this._isTeleport;
    }
    
    public void setTeleport(final boolean flag) {
        if (flag) {
            this.setNowTarget(null);
        }
        this._isTeleport = flag;
    }
    
    public boolean isDrink() {
        return this._isDrink;
    }
    
    public void setDrink(final boolean flag) {
        this._isDrink = flag;
    }
    
    public boolean isGres() {
        return this._isGres;
    }
    
    public void setGres(final boolean flag) {
        this._isGres = flag;
    }
    
    public ArrayList<L1PrivateShopSellList> getSellList() {
        return this._sellList;
    }
    
    public ArrayList<L1PrivateShopBuyList> getBuyList() {
        return this._buyList;
    }
    
    public void setShopChat(final byte[] chat) {
        this._shopChat = chat;
    }
    
    public byte[] getShopChat() {
        return this._shopChat;
    }
    
    public boolean isPrivateShop() {
        return this._isPrivateShop;
    }
    
    public void setPrivateShop(final boolean flag) {
        this._isPrivateShop = flag;
    }
    
    public boolean isTradingInPrivateShop() {
        return this._isTradingInPrivateShop;
    }
    
    public void setTradingInPrivateShop(final boolean flag) {
        this._isTradingInPrivateShop = flag;
    }
    
    public int getPartnersPrivateShopItemCount() {
        return this._partnersPrivateShopItemCount;
    }
    
    public void setPartnersPrivateShopItemCount(final int i) {
        this._partnersPrivateShopItemCount = i;
    }
    
    public void setPacketOutput(final EncryptExecutor out) {
        this._out = out;
    }
    
    public void sendPackets(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsAll(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketAll(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsYN(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketYN(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsAllUnderInvis(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis()) {
                this.broadcastPacketAll(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsX8(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketX8(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsX10(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketX10(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public void sendPacketsXR(final ServerBasePacket packet, final int r) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketXR(packet, r);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    private void close() {
        try {
            this.getNetConnection().close();
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void onAction(final L1PcInstance attacker) {
        if (attacker == null) {
            return;
        }
        if (this.isTeleport()) {
            return;
        }
        if (this.isSafetyZone() || attacker.isSafetyZone()) {
            final L1AttackPc l1AttackPc = new L1AttackPc(attacker, this);
            l1AttackPc.action();
            return;
        }
        if (this.checkNonPvP(this, attacker)) {
            final L1AttackPc l1AttackPc = new L1AttackPc(attacker, this);
            l1AttackPc.action();
            return;
        }
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            attacker.delInvis();
            boolean isCounterBarrier = false;
            final L1AttackPc l1AttackPc2 = new L1AttackPc(attacker, this);
            if (l1AttackPc2.calcHit()) {
                final L1ItemInstance weapon1 = this.getWeapon();
                if (this.hasSkillEffect(91) && weapon1.getItem().getType() == 3) {
                    final boolean isShortDistance = l1AttackPc2.isShortDistance();
                    final L1ItemInstance weapon2 = attacker.getWeapon();
                    final int level = this.getLevel() - attacker.getLevel();
                    int isProbability = 0;
                    final int ran = L1PcInstance._random.nextInt(100) + 1;
                    if (level > 0) {
                        isProbability = 15 + level;
                    }
                    else {
                        isProbability = 15;
                    }
                    if (isProbability >= 40) {
                        isProbability = 40;
                    }
                    if (ran <= isProbability) {
                        if (isShortDistance && weapon2.getItem().getType() != 17) {
                            isCounterBarrier = true;
                            if (this.isGm()) {
                                this.sendPackets(new S_SystemMessage("玩家發動:" + isProbability + "大於系統發動值" + ran + "觸發"));
                            }
                        }
                    }
                    else if (this.isGm()) {
                        this.sendPackets(new S_SystemMessage("玩家發動:" + isProbability + "小於系統發動值" + ran + "觸發"));
                    }
                }
                if (!isCounterBarrier) {
                    attacker.setPetTarget(this);
                    l1AttackPc2.calcDamage();
                    l1AttackPc2.calcStaffOfMana();
                }
            }
            if (isCounterBarrier) {
                l1AttackPc2.commitCounterBarrier();
            }
            else {
                l1AttackPc2.action();
                l1AttackPc2.commit();
            }
        }
    }
    
    public boolean checkNonPvP(final L1PcInstance pc, final L1Character target) {
        L1PcInstance targetpc = null;
        if (target instanceof L1PcInstance) {
            targetpc = (L1PcInstance)target;
        }
        else if (target instanceof L1PetInstance) {
            targetpc = (L1PcInstance)((L1PetInstance)target).getMaster();
        }
        else if (target instanceof L1SummonInstance) {
            targetpc = (L1PcInstance)((L1SummonInstance)target).getMaster();
        }
        if (targetpc == null) {
            return false;
        }
        if (ConfigAlt.ALT_NONPVP) {
            return false;
        }
        if (this.getMap().isCombatZone(this.getLocation())) {
            return false;
        }
        for (final L1War war : WorldWar.get().getWarList()) {
            if (pc.getClanid() != 0 && targetpc.getClanid() != 0) {
                final boolean same_war = war.checkClanInSameWar(pc.getClanname(), targetpc.getClanname());
                if (same_war) {
                    return false;
                }
                continue;
            }
        }
        if (target instanceof L1PcInstance) {
            final L1PcInstance targetPc = (L1PcInstance)target;
            if (this.isInWarAreaAndWarTime(pc, targetPc)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean isInWarAreaAndWarTime(final L1PcInstance pc, final L1PcInstance target) {
        final int castleId = L1CastleLocation.getCastleIdByArea(pc);
        final int targetCastleId = L1CastleLocation.getCastleIdByArea(target);
        return castleId != 0 && targetCastleId != 0 && castleId == targetCastleId && ServerWarExecutor.get().isNowWar(castleId);
    }
    
    public void setPetTarget(final L1Character target) {
        if (target == null) {
            return;
        }
        if (target.isDead()) {
            return;
        }
        final Map<Integer, L1NpcInstance> petList = this.getPetList();
        try {
            if (!petList.isEmpty()) {
                for (final L1NpcInstance pet : petList.values()) {
                    if (pet != null) {
                        if (pet instanceof L1PetInstance) {
                            final L1PetInstance pets = (L1PetInstance)pet;
                            pets.setMasterTarget(target);
                        }
                        else {
                            if (!(pet instanceof L1SummonInstance)) {
                                continue;
                            }
                            final L1SummonInstance summon = (L1SummonInstance)pet;
                            summon.setMasterTarget(target);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            if (L1PcInstance._debug) {
                L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            }
        }
        final Map<Integer, L1IllusoryInstance> illList = this.get_otherList().get_illusoryList();
        try {
            if (!illList.isEmpty() && this.getId() != target.getId()) {
                for (final L1IllusoryInstance ill : illList.values()) {
                    if (ill != null) {
                        ill.setLink(target);
                    }
                }
            }
        }
        catch (Exception e2) {
            if (L1PcInstance._debug) {
                L1PcInstance._log.error((Object)e2.getLocalizedMessage(), (Throwable)e2);
            }
        }
    }
    
    public void delInvis() {
        if (this.hasSkillEffect(60)) {
            this.killSkillEffectTimer(60);
            this.sendPackets(new S_Invis(this.getId(), 0));
            this.broadcastPacketAll(new S_OtherCharPacks(this));
        }
        if (this.hasSkillEffect(97)) {
            this.killSkillEffectTimer(97);
            this.sendPackets(new S_Invis(this.getId(), 0));
            this.broadcastPacketAll(new S_OtherCharPacks(this));
        }
    }
    
    public void delBlindHiding() {
        this.killSkillEffectTimer(97);
        this.sendPackets(new S_Invis(this.getId(), 0));
        this.broadcastPacketAll(new S_OtherCharPacks(this));
    }
    
    public void receiveDamage(final L1Character attacker, double damage, final int attr) {
        final int player_mr = this.getMr();
        final int rnd = L1PcInstance._random.nextInt(300) + 1;
        if (player_mr >= rnd) {
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
        int resistFloor = (int)(0.32 * Math.abs(resist));
        if (resist >= 0) {
            resistFloor *= 1;
        }
        else {
            resistFloor *= -1;
        }
        final double attrDeffence = resistFloor / 32.0;
        final double coefficient = 1.0 - attrDeffence + 0.09375;
        if (coefficient > 0.0) {
            damage *= coefficient;
        }
        this.receiveDamage(attacker, damage, false, false);
    }
    
    public void receiveManaDamage(final L1Character attacker, final int mpDamage) {
        if (mpDamage > 0 && !this.isDead()) {
            this.delInvis();
            if (attacker instanceof L1PcInstance) {
                L1PinkName.onAction(this, attacker);
            }
            if (attacker instanceof L1PcInstance && ((L1PcInstance)attacker).isPinkName()) {
                for (final L1Object object : World.get().getVisibleObjects(attacker)) {
                    if (object instanceof L1GuardInstance) {
                        final L1GuardInstance guard = (L1GuardInstance)object;
                        guard.setTarget((L1PcInstance)attacker);
                    }
                }
            }
            int newMp = this.getCurrentMp() - mpDamage;
            if (newMp > this.getMaxMp()) {
                newMp = this.getMaxMp();
            }
            newMp = Math.max(newMp, 0);
            this.setCurrentMp(newMp);
        }
    }
    
    public static void load() {
        double newdmg = 100.0;
        for (long i = 2000L; i > 0L; --i) {
            if (i % 100L == 0L) {
                newdmg -= 3.33;
            }
            L1PcInstance._magicDamagerList.put(i, newdmg);
        }
    }
    
    public double isMagicDamager(final double damage) {
        final long nowTime = System.currentTimeMillis();
        final long interval = nowTime - this._oldTime;
        double newdmg = 0.0;
        if (damage < 0.0) {
            newdmg = damage;
        }
        else {
            final Double tmpnewdmg = L1PcInstance._magicDamagerList.get(interval);
            if (tmpnewdmg != null) {
                newdmg = damage * tmpnewdmg / 100.0;
            }
            else {
                newdmg = damage;
            }
            newdmg = Math.max(newdmg, 0.0);
            this._oldTime = nowTime;
        }
        return newdmg;
    }
    
    public void receiveDamage(final L1Character attacker, double damage, final boolean isMagicDamage, final boolean isCounterBarrier) {
        if (damage <= 0.0) {
            return;
        }
        if (attacker == this) {
            return;
        }
        if (this.getCurrentHp() > 0 && !this.isDead()) {
            if (attacker != null) {
                if (attacker != this && !(attacker instanceof L1EffectInstance) && !this.knownsObject(attacker) && attacker.getMapId() == this.getMapId()) {
                    attacker.onPerceive(this);
                }
                if (isMagicDamage) {
                    damage = this.isMagicDamager(damage);
                }
                L1PcInstance attackPc = null;
                L1NpcInstance attackNpc = null;
                if (attacker instanceof L1PcInstance) {
                    attackPc = (L1PcInstance)attacker;
                }
                else if (attacker instanceof L1NpcInstance) {
                    attackNpc = (L1NpcInstance)attacker;
                }
                if (damage > 0.0) {
                    this.delInvis();
                    this.removeSkillEffect(66);
                    this.removeSkillEffect(212);
                    if (attackPc != null) {
                        L1PinkName.onAction(this, attackPc);
                        if (attackPc.isPinkName()) {
                            for (final L1Object object : World.get().getVisibleObjects(attacker)) {
                                if (object instanceof L1GuardInstance) {
                                    final L1GuardInstance guard = (L1GuardInstance)object;
                                    guard.setTarget((L1PcInstance)attacker);
                                }
                            }
                        }
                    }
                }
                if (!isCounterBarrier) {
                    if (this.hasSkillEffect(191) && this.getId() != attacker.getId()) {
                        final int rnd = L1PcInstance._random.nextInt(100);
                        if (damage > 0.0 && rnd < 23) {
                            int dmg = 40;
                            if (attackPc != null) {
                                if (attackPc.hasSkillEffect(68)) {
                                    dmg /= 2;
                                }
                                attackPc.sendPacketsAll(new S_DoActionGFX(attackPc.getId(), 2));
                                this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                                attackPc.receiveDamage(this, dmg, false, true);
                            }
                            else if (attackNpc != null) {
                                if (attackNpc.hasSkillEffect(68)) {
                                    dmg /= 2;
                                }
                                attackNpc.getCurrentHp();
                                attackNpc.broadcastPacketAll(new S_DoActionGFX(attackNpc.getId(), 2));
                                this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                                attackNpc.receiveDamage(this, dmg);
                            }
                        }
                    }
                    if (!isMagicDamage && this._elitePlateMail_Valakas > 0) {
                        int nowDamage = L1PcInstance._random.nextInt(this._valakas_dmgmax - this._valakas_dmgmin + 1) + this._valakas_dmgmin;
                        if (attackPc != null) {
                            final L1ItemInstance weapon = attackPc.getWeapon();
                            if (weapon != null && (weapon.getItem().getType1() == 20 || weapon.getItem().getType1() == 62) && L1PcInstance._random.nextInt(100) < this._elitePlateMail_Valakas) {
                                if (attackPc.hasSkillEffect(68)) {
                                    nowDamage /= 2;
                                }
                                this.sendPacketsAll(new S_SkillSound(this.getId(), 10419));
                                attackPc.sendPacketsAll(new S_DoActionGFX(attackPc.getId(), 2));
                                attackPc.receiveDamage(this, nowDamage, false, true);
                            }
                        }
                        else if (attackNpc != null && L1PcInstance._random.nextInt(100) < this._elitePlateMail_Valakas) {
                            final L1AttackNpc l1AttackNpc = new L1AttackNpc(attackNpc, this);
                            final boolean isShortDistance = l1AttackNpc.isShortDistance();
                            if (!isShortDistance) {
                                if (attackNpc.hasSkillEffect(68)) {
                                    nowDamage /= 2;
                                }
                                this.sendPacketsAll(new S_SkillSound(this.getId(), 10419));
                                attackNpc.broadcastPacketAll(new S_DoActionGFX(attackNpc.getId(), 2));
                                attackNpc.receiveDamage(this, nowDamage);
                            }
                        }
                    }
                    if (this._hades_cloak > 0) {
                        int nowDamage = L1PcInstance._random.nextInt(this._hades_cloak_dmgmax - this._hades_cloak_dmgmin + 1) + this._hades_cloak_dmgmin;
                        if (attackPc != null && L1PcInstance._random.nextInt(1000) < this._hades_cloak) {
                            if (attackPc.hasSkillEffect(68)) {
                                nowDamage /= 2;
                            }
                            attackPc.sendPacketsAll(new S_DoActionGFX(attackPc.getId(), 2));
                            this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                            attackPc.receiveDamage(this, nowDamage, false, true);
                        }
                        else if (attackNpc != null && L1PcInstance._random.nextInt(1000) < this._hades_cloak) {
                            if (attackNpc.hasSkillEffect(68)) {
                                nowDamage /= 2;
                            }
                            attackNpc.getCurrentHp();
                            attackNpc.broadcastPacketAll(new S_DoActionGFX(attackNpc.getId(), 2));
                            this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                            attackNpc.receiveDamage(this, nowDamage);
                        }
                    }
                    if (this.has_powerid(6612)) {
                        final int rad = 15;
                        int dmg = 80;
                        if (attackPc != null && damage > 0.0 && L1PcInstance._random.nextInt(100) < rad) {
                            if (attackPc.hasSkillEffect(68)) {
                                dmg /= 2;
                            }
                            attackPc.sendPacketsAll(new S_DoActionGFX(attackPc.getId(), 2));
                            this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                            attackPc.receiveDamage(this, dmg, false, true);
                        }
                        else if (attackNpc != null && damage > 0.0 && L1PcInstance._random.nextInt(100) < rad) {
                            if (attackNpc.hasSkillEffect(68)) {
                                dmg /= 2;
                            }
                            attackNpc.getCurrentHp();
                            attackNpc.broadcastPacketAll(new S_DoActionGFX(attackNpc.getId(), 2));
                            this.sendPacketsAll(new S_SkillSound(this.getId(), 10710));
                            attackNpc.receiveDamage(this, dmg);
                        }
                    }
                    if (attacker.hasSkillEffect(218) && this.getId() != attacker.getId() && !this.hasSkillEffect(218)) {
                        final int hpup = this.get_other().get_addhp();
                        int nowDamage2 = (this.getMaxHp() - this.getCurrentHp() - hpup) / ConfigOther.JOY_OF_PAIN_PC;
                        final int nowDamage3 = (this.getMaxHp() - this.getCurrentHp() - hpup) / ConfigOther.JOY_OF_PAIN_NPC;
                        if (nowDamage2 > 0) {
                            if (nowDamage2 > ConfigOther.JOY_OF_PAIN_DMG) {
                                nowDamage2 = ConfigOther.JOY_OF_PAIN_DMG;
                            }
                            final int skilltype = this.getlogpcpower_SkillFor1() * 100;
                            if (this.isIllusionist() && this.getlogpcpower_SkillFor1() > 0) {
                                nowDamage2 += skilltype;
                            }
                            if (attackPc != null) {
                                attackPc.sendPacketsX10(new S_DoActionGFX(attackPc.getId(), 2));
                                attackPc.receiveDamage(this, nowDamage2, false, true);
                            }
                            else if (attackNpc != null) {
                                attackNpc.broadcastPacketX10(new S_DoActionGFX(attackNpc.getId(), 2));
                                attackNpc.receiveDamage(this, nowDamage3);
                            }
                        }
                    }
                }
            }
            if (this.getInventory().checkEquipped(145) || this.getInventory().checkEquipped(149)) {
                damage *= 1.5;
            }
            if (this.hasSkillEffect(219)) {
                damage *= 1.05;
            }
            int addmp = 0;
            if (this._elitePlateMail_Lindvior > 0 && L1PcInstance._random.nextInt(100) < this._elitePlateMail_Lindvior) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), 2188));
                addmp = L1PcInstance._random.nextInt(this._lindvior_mpmax - this._lindvior_mpmin + 1) + this._lindvior_mpmin;
                final int newMp = this.getCurrentMp() + addmp;
                this.setCurrentMp(newMp);
            }
            int addhp = 0;
            if (this._elitePlateMail_Fafurion > 0 && L1PcInstance._random.nextInt(100) < this._elitePlateMail_Fafurion) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), 2187));
                addhp = L1PcInstance._random.nextInt(this._fafurion_hpmax - this._fafurion_hpmin + 1) + this._fafurion_hpmin;
            }
            if (this._Hexagram_Magic_Rune > 0 && L1PcInstance._random.nextInt(1000) < this._Hexagram_Magic_Rune) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), this._hexagram_gfx));
                addhp = L1PcInstance._random.nextInt(this._hexagram_hpmax - this._hexagram_hpmin + 1) + this._hexagram_hpmin;
            }
            if (this._dimiter_bless > 0 && L1PcInstance._random.nextInt(1000) < this._dimiter_bless && !this.hasSkillEffect(68)) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), 11101));
                this.setSkillEffect(68, this._dimiter_time * 1000);
                this.sendPackets(new S_PacketBox(40, this._dimiter_time));
            }
            if (this._dimiter_mpr_rnd > 0 && L1PcInstance._random.nextInt(1000) < this._dimiter_mpr_rnd) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), 2188));
                addmp = L1PcInstance._random.nextInt(this._dimiter_mpmax - this._dimiter_mpmin + 1) + this._dimiter_mpmin;
                final int newMp2 = this.getCurrentMp() + addmp;
                this.setCurrentMp(newMp2);
            }
            if (this.isKnight() && this.isEsoteric() && damage > 0.0) {
                final int mp = (int)(damage * 5.0 / this.getlogpcpower_SkillFor3());
                if (this.getCurrentMp() > mp) {
                    int newMp3 = this.getCurrentMp() - mp;
                    if (newMp3 > (short)this.getMaxMp()) {
                        newMp3 = (short)this.getMaxMp();
                    }
                    if (newMp3 <= 0) {
                        newMp3 = 1;
                    }
                    this.setCurrentMp(newMp3);
                    damage = 0.0;
                    this.sendPackets(new S_SkillSound(this.getId(), 214));
                    this.broadcastPacketAll(new S_SkillSound(this.getId(), 214));
                }
                else {
                    this.setEsoteric(false);
                    this.sendPackets(new S_SystemMessage("\\fU關閉轉身技能(聖法之盾)"));
                }
            }
            if (this.isKnight() && this.getlogpcpower_SkillFor5() != 0) {
                boolean isSameAttr = false;
                if (this.getHeading() == 0 && (attacker.getHeading() == 3 || attacker.getHeading() == 4 || attacker.getHeading() == 4)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 1 && (attacker.getHeading() == 4 || attacker.getHeading() == 5 || attacker.getHeading() == 6)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 2 && (attacker.getHeading() == 5 || attacker.getHeading() == 6 || attacker.getHeading() == 7)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 3 && (attacker.getHeading() == 6 || attacker.getHeading() == 7 || attacker.getHeading() == 0)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 4 && (attacker.getHeading() == 7 || attacker.getHeading() == 0 || attacker.getHeading() == 1)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 5 && (attacker.getHeading() == 0 || attacker.getHeading() == 1 || attacker.getHeading() == 2)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 6 && (attacker.getHeading() == 1 || attacker.getHeading() == 2 || attacker.getHeading() == 3)) {
                    isSameAttr = true;
                }
                else if (this.getHeading() == 7 && (attacker.getHeading() == 2 || attacker.getHeading() == 3 || attacker.getHeading() == 4)) {
                    isSameAttr = true;
                }
                if (isSameAttr && RandomArrayList.getInc(100, 1) <= this.getlogpcpower_SkillFor5()) {
                    damage *= 0.7;
                    this.sendPackets(new S_SkillSound(this.getId(), 5377));
                    this.sendPackets(new S_SystemMessage("觸發 神聖壁壘 減免了 30%傷害。"));
                }
            }
            int newHp = this.getCurrentHp() - (int)damage + addhp;
            if (newHp > this.getMaxHp()) {
                newHp = this.getMaxHp();
            }
            if (newHp <= 0 && !this.isGm()) {
                this.death(attacker);
            }
            this.setCurrentHp(newHp);
        }
        else if (!this.isDead()) {
            L1PcInstance._log.error((Object)"人物hp減少處理失敗 可能原因: 初始hp為0");
            this.death(attacker);
        }
    }
    
    public void death(final L1Character lastAttacker) {
        synchronized (this) {
            if (this.isDead()) {
                // monitorexit(this)
                return;
            }
            this.setNowTarget(null);
            this.setDead(true);
            this.setStatus(ActionCodes.ACTION_Die);
            if (this.isActived()) {
                this.setActived(false);
                this.sendPackets(new S_ServerMessage("自動狩獵已停止了。"));
                if (this.getAutoX() > 0) {
                    this.setAutoX(0);
                    this.setAutoY(0);
                    this.setAutoMap(0);
                    this.killSkillEffectTimer(8853);
                }
            }
        }
        // 死亡執行緒
//     	GeneralThreadPool.get().execute(new Death(lastAttacker));
     	DeathThreadPool.get().execute(new Death(lastAttacker));
    }
    
    private void caoPenaltyResult(final int count) {
        for (int i = 0; i < count; ++i) {
            final L1ItemInstance item = this.getInventory().caoPenalty();
            if (item != null) {
                if (Itemdeaddrop.RESTRICTIONS.contains(item.getItemId())) {
                    break;
                }
                if (ItemdropdeadTable.RESTRICTIONS.contains(item.getItemId())) {
                    L1PcInstance._log.warn((Object)("玩家：" + this.getName() + "裝備 死亡噴出遺失:" + item.getId() + "/" + item.getItem().getName()));
                    this.getInventory().deleteItem(item);
                    bowisbuy("玩家【 " + this.getName() + " 】的" + "【 + " + item.getEnchantLevel() + " " + item.getName() + " 】在死亡時(設定強制)消失了，" + "時間:(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    this.sendPackets(new S_ServerMessage(638, item.getLogName()));
                }
                else if (item.getBless() >= 128) {
                    L1PcInstance._log.warn((Object)("玩家：" + this.getName() + "封印裝備 死亡噴出遺失:" + item.getId() + "/" + item.getItem().getName()));
                    this.getInventory().deleteItem(item);
                    bowisbuy("玩家【 " + this.getName() + " 】的(封印)" + "【 + " + item.getEnchantLevel() + " " + item.getName() + " 】在死亡時消失了，" + "時間:(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    this.sendPackets(new S_ServerMessage(638, item.getLogName()));
                    if (ConfigOther.DropitemMsgall) {
                        ConfigPnitem.msg(this.getName(), item.getLogName());
                    }
                }
                else {
                    if (ConfigOther.DropitemMsgall) {
                        ConfigPnitem.msg(this.getName(), item.getLogName());
                    }
                    bowisbuy("玩家【 " + this.getName() + " 】的" + "【 + " + item.getEnchantLevel() + " " + item.getName() + " 】在死亡噴出，" + "時間:(" + new Timestamp(System.currentTimeMillis()) + ")。");
                    L1PcInstance._log.warn((Object)("玩家：" + this.getName() + "死亡噴出物品:" + item.getId() + "/" + item.getItem().getName()));
                    item.set_showId(this.get_showId());
                    final int x = this.getX();
                    final int y = this.getY();
                    final short m = this.getMapId();
                    this.getInventory().tradeItem(item, item.isStackable() ? item.getCount() : 1L, World.get().getInventory(x, y, m));
                }
                item.setdropitemcheck(1);
                item.setdropitemname(this.getName());
                this.sendPackets(new S_ServerMessage(638, item.getLogName()));
                RecordTable.get().recordeDeadItem(this.getName(), item.getAllName(), (int)item.getCount(), item.getId(), this.getIp());
            }
        }
    }
    
    public void stopPcDeleteTimer() {
        this.setDead(false);
        this.set_delete_time(0);
    }
    
    public boolean castleWarResult() {
        if (this.getClanid() != 0 && this.isCrown()) {
            final L1Clan clan = WorldClan.get().getClan(this.getClanname());
            if (clan.getCastleId() == 0) {
                for (final L1War war : WorldWar.get().getWarList()) {
                    final int warType = war.getWarType();
                    final boolean isInWar = war.checkClanInWar(this.getClanname());
                    final boolean isAttackClan = war.checkAttackClan(this.getClanname());
                    if (this.getId() == clan.getLeaderId() && warType == 1 && isInWar && isAttackClan) {
                        final String enemyClanName = war.getEnemyClanName(this.getClanname());
                        if (enemyClanName != null) {
                            war.ceaseWar(this.getClanname(), enemyClanName);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        int castleId = 0;
        boolean isNowWar = false;
        castleId = L1CastleLocation.getCastleIdByArea(this);
        if (castleId != 0) {
            isNowWar = ServerWarExecutor.get().isNowWar(castleId);
        }
        return isNowWar;
    }
    
    public boolean simWarResult(final L1Character lastAttacker) {
        if (this.getClanid() == 0) {
            return false;
        }
        L1PcInstance attacker = null;
        String enemyClanName = null;
        boolean sameWar = false;
        if (lastAttacker instanceof L1PcInstance) {
            attacker = (L1PcInstance)lastAttacker;
        }
        else if (lastAttacker instanceof L1PetInstance) {
            attacker = (L1PcInstance)((L1PetInstance)lastAttacker).getMaster();
        }
        else if (lastAttacker instanceof L1SummonInstance) {
            attacker = (L1PcInstance)((L1SummonInstance)lastAttacker).getMaster();
        }
        else if (lastAttacker instanceof L1IllusoryInstance) {
            attacker = (L1PcInstance)((L1IllusoryInstance)lastAttacker).getMaster();
        }
        else {
            if (!(lastAttacker instanceof L1EffectInstance)) {
                return false;
            }
            attacker = (L1PcInstance)((L1EffectInstance)lastAttacker).getMaster();
        }
        final L1Clan clan = WorldClan.get().getClan(this.getClanname());
        for (final L1War war : WorldWar.get().getWarList()) {
            final int warType = war.getWarType();
            if (warType != 1) {
                final boolean isInWar = war.checkClanInWar(this.getClanname());
                if (!isInWar) {
                    continue;
                }
                if (attacker != null && attacker.getClanid() != 0) {
                    sameWar = war.checkClanInSameWar(this.getClanname(), attacker.getClanname());
                }
                if (this.getId() == clan.getLeaderId()) {
                    enemyClanName = war.getEnemyClanName(this.getClanname());
                    if (enemyClanName != null) {
                        war.ceaseWar(this.getClanname(), enemyClanName);
                    }
                }
                if (warType == 2 && sameWar) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    /**
	 * 恢復經驗值
	 */
    public void resExp() {
        final int oldLevel = this.getLevel();
        final long needExp = ExpTable.getNeedExpNextLevel(oldLevel);
        final double DeathExp = ExpTable.getHfExprates(oldLevel);
        final long exp = (long)(needExp * DeathExp);
        if (exp > 0L) {
            final long de = (long)(exp * 0.5);
            this.addExp(de);
        }
    }
    
    /**
	 * 死亡損失經驗值
	 * 
	 * @return
	 */
    private void deathPenalty() {
        final int oldLevel = this.getLevel();
        final long needExp = ExpTable.getNeedExpNextLevel(oldLevel);
        final double DeathExp = ExpTable.getDeathExprates(oldLevel);
        final long exp = (long)(needExp * DeathExp);
        if (exp > 0L) {
            final long de = (long)(exp * 0.5);
//            this.addExp(-de);
            final long currentExp = this.getExp();
            final long newExp = currentExp - de;
            
            if (this.getLevel() >= ConfigOther.PcLevelUp) {
                this.setExp(newExp);
            } else {
                // 確保經驗值不低於 0
                this.setExp(Math.max(newExp, 0L));
            }
        }
    }
    
    /**
	 * 取回其他ER加成
	 * 
	 * @return
	 */
    public int getOriginalEr() {
        return this._originalEr;
    }
    
    /**
	 * 取回ER迴避率(7.6版)
	 * 
	 * @return
	 */
    public int getEr() {
        int er = 0;
     // 各職業的等級，對ER的增益
        if (this.isKnight()) {
        	er += getLevel() / 4;
        }
        else if (this.isCrown() || this.isElf()) {
            er += this.getLevel() / 6;
        }
        else if (this.isDarkelf()) {
            er += this.getLevel() / 4;
        }
        else if (this.isWizard()) {
            er += this.getLevel() / 10;
        }
        else if (this.isDragonKnight()) {
            er += this.getLevel() / 5;
        }
        else if (this.isIllusionist()) {
            er += this.getLevel() / 9;
        }
        if (this.getDex() <= 7) {
			er += 3;

		} else if (this.getDex() >= 8) {
			er += (this.getDex() - 8) / 2 + 4;
		}
        er += this.getOriginalEr();// 其他ER加成
        
        if (this.hasSkillEffect(DRESS_EVASION)) {// 迴避提升
            er += ConfigSkill.DRESS_EVASION;
        }
        
        if (this.hasSkillEffect(SOLID_CARRIAGE)) {// 堅固防護
			er += 15;
		}
        
		/*if (hasSkillEffect(STRIKER_GALE)) {// 精準射擊
			er /= 3;
		}*/
        if (hasSkillEffect(STRIKER_GALE)) {// 精準射擊
        	return 0;
        }
        if (this.hasSkillEffect(AQUA_PROTECTER)) {//水之防護
			er += 5;
		}
        if (this.hasSkillEffect(ADLV80_1)) {// 卡瑞的祝福(地龍副本)
            er += 30;
        }
        if (this.hasSkillEffect(ADLV80_2)) {// 莎爾的祝福(水龍副本)
            er += 15;
        }
        return er;
    }
    /**
	 * 取回目前使用的主武器
	 * 
	 * @return
	 */
    public L1ItemInstance getWeapon() {
        return this._weapon;
    }
    
    /**
	 * 定義目前使用的主武器
	 * 
	 * @param weapon
	 */
    public void setWeapon(final L1ItemInstance weapon) {
        this._weapon = weapon;
    }
    
    public L1PcQuest getQuest() {
        return this._quest;
    }
    
    public L1ActionPc getAction() {
        return this._action;
    }
    
    public L1ActionPet getActionPet() {
        return this._actionPet;
    }
    
    public L1ActionSummon getActionSummon() {
        return this._actionSummon;
    }
    
    public boolean isCrown() {
        return this.getClassId() == 0 || this.getClassId() == 1;
    }
    
    public boolean isKnight() {
        return this.getClassId() == 61 || this.getClassId() == 48;
    }
    
    public boolean isElf() {
        return this.getClassId() == 138 || this.getClassId() == 37;
    }
    
    public boolean isWizard() {
        return this.getClassId() == 734 || this.getClassId() == 1186;
    }
    
    public boolean isDarkelf() {
        return this.getClassId() == 2786 || this.getClassId() == 2796;
    }
    
    public boolean isDragonKnight() {
        return this.getClassId() == 6658 || this.getClassId() == 6661;
    }
    
    public boolean isIllusionist() {
        return this.getClassId() == 6671 || this.getClassId() == 6650;
    }
    
    public String getAccountName() {
        return this._accountName;
    }
    
    public void setAccountName(final String s) {
        this._accountName = s;
    }
    
    public short getBaseMaxHp() {
        return this._baseMaxHp;
    }
    
    public void addBaseMaxHp(short i) {
        i += this._baseMaxHp;
        if (i >= 32767) {
            i = 32767;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addMaxHp(i - this._baseMaxHp);
        this._baseMaxHp = i;
    }
    
    public short getBaseMaxMp() {
        return this._baseMaxMp;
    }
    
    public void addBaseMaxMp(short i) {
        i += this._baseMaxMp;
        if (i >= 32767) {
            i = 32767;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addMaxMp(i - this._baseMaxMp);
        this._baseMaxMp = i;
    }
    
    public int getBaseAc() {
        return this._baseAc;
    }
    
    public int getOriginalAc() {
        return this._originalAc;
    }
    
    public int getBaseStr() {
        return this._baseStr;
    }
    
    public void addBaseStr(int i) {
        i += this._baseStr;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addStr(i - this._baseStr);
        this._baseStr = i;
    }
    
    public int getBaseCon() {
        return this._baseCon;
    }
    
    public void addBaseCon(int i) {
        i += this._baseCon;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addCon(i - this._baseCon);
        this._baseCon = i;
    }
    
    public int getBaseDex() {
        return this._baseDex;
    }
    
    public void addBaseDex(int i) {
        i += this._baseDex;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addDex(i - this._baseDex);
        this._baseDex = i;
    }
    
    public int getBaseCha() {
        return this._baseCha;
    }
    
    public void addBaseCha(int i) {
        i += this._baseCha;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addCha(i - this._baseCha);
        this._baseCha = i;
    }
    
    public int getBaseInt() {
        return this._baseInt;
    }
    
    public void addBaseInt(int i) {
        i += this._baseInt;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addInt(i - this._baseInt);
        this._baseInt = i;
    }
    
    public int getBaseWis() {
        return this._baseWis;
    }
    
    public void addBaseWis(int i) {
        i += this._baseWis;
        if (i >= 254) {
            i = 254;
        }
        else if (i < 1) {
            i = 1;
        }
        this.addWis(i - this._baseWis);
        this._baseWis = i;
    }
    
    public int getOriginalStr() {
        return this._originalStr;
    }
    
    public void setOriginalStr(final int i) {
        this._originalStr = i;
    }
    
    public int getOriginalCon() {
        return this._originalCon;
    }
    
    public void setOriginalCon(final int i) {
        this._originalCon = i;
    }
    
    public int getOriginalDex() {
        return this._originalDex;
    }
    
    public void setOriginalDex(final int i) {
        this._originalDex = i;
    }
    
    public int getOriginalCha() {
        return this._originalCha;
    }
    
    public void setOriginalCha(final int i) {
        this._originalCha = i;
    }
    
    public int getOriginalInt() {
        return this._originalInt;
    }
    
    public void setOriginalInt(final int i) {
        this._originalInt = i;
    }
    
    public int getOriginalWis() {
        return this._originalWis;
    }
    
    public void setOriginalWis(final int i) {
        this._originalWis = i;
    }
    
    public int getOriginalDmgup() {
        return this._originalDmgup;
    }
    
    public int getOriginalBowDmgup() {
        return this._originalBowDmgup;
    }
    
    public int getOriginalHitup() {
        return this._originalHitup;
    }
    
    public int getOriginalBowHitup() {
        return this._originalHitup + this._originalBowHitup;
    }
    
    public int getOriginalMr() {
        return this._originalMr;
    }
    
    public void addOriginalMagicHit(final int i) {
        this._originalMagicHit += i;
    }
    
    public int getOriginalMagicHit() {
        return this._originalMagicHit;
    }
    
    public void addOriginalMagicCritical(final int i) {
        this._originalMagicCritical += i;
    }
    
    public int getOriginalMagicCritical() {
        return this._originalMagicCritical;
    }
    
    public int getOriginalMagicConsumeReduction() {
        return this._originalMagicConsumeReduction;
    }
    
    public int getOriginalMagicDamage() {
        return this._originalMagicDamage;
    }
    
    private int _Magic_Critical = 0;

	/**
	 * 增加魔法暴擊率
	 * 
	 * @param i
	 */
	public void add_Magic_Critical(int i) {
		_Magic_Critical += i;
	}

	/**
	 * 取回其他增加魔法暴擊率
	 * 
	 * @return
	 */
	public int get_Magic_Critical() {
		return _Magic_Critical;
	}
    
    public int getOriginalHpup() {
        return this._originalHpup;
    }
    
    public int getOriginalMpup() {
        return this._originalMpup;
    }
    
    public int getBaseDmgup() {
        return this._baseDmgup;
    }
    
    public int getBaseBowDmgup() {
        return this._baseBowDmgup;
    }
    
    public int getBaseHitup() {
        return this._baseHitup;
    }
    
    public int getBaseBowHitup() {
        return this._baseBowHitup;
    }
    
    public int getBaseMr() {
        return this._baseMr;
    }
    
    public int getAdvenHp() {
        return this._advenHp;
    }
    
    public void setAdvenHp(final int i) {
        this._advenHp = i;
    }
    
    public int getAdvenMp() {
        return this._advenMp;
    }
    
    public void setAdvenMp(final int i) {
        this._advenMp = i;
    }
    
    public int getHighLevel() {
        return this._highLevel;
    }
    
    public void setHighLevel(final int i) {
        this._highLevel = i;
    }
    
    public int getBonusStats() {
        return this._bonusStats;
    }
    
    public void setBonusStats(final int i) {
        this._bonusStats = i;
    }
    
    public int getElixirStats() {
        return this._elixirStats;
    }
    
    public void setElixirStats(final int i) {
        this._elixirStats = i;
    }
    
    public int getElfAttr() {
        return this._elfAttr;
    }
    
    public void setElfAttr(final int i) {
        this._elfAttr = i;
    }
    
    public int getExpRes() {
        return this._expRes;
    }
    
    public void setExpRes(final int i) {
        this._expRes = i;
    }
    
    public int getPartnerId() {
        return this._partnerId;
    }
    
    public void setPartnerId(final int i) {
        this._partnerId = i;
    }
    
    public int getOnlineStatus() {
        return this._onlineStatus;
    }
    
    public void setOnlineStatus(final int i) {
        this._onlineStatus = i;
    }
    
    public int getHomeTownId() {
        return this._homeTownId;
    }
    
    public void setHomeTownId(final int i) {
        this._homeTownId = i;
    }
    
    public int getContribution() {
        return this._contribution;
    }
    
    public void setContribution(final int i) {
        this._contribution = i;
    }
    
    public int getPay() {
        return this._pay;
    }
    
    public void setPay(final int i) {
        this._pay = i;
    }
    
    public int getHellTime() {
        return this._hellTime;
    }
    
    public void setHellTime(final int i) {
        this._hellTime = i;
    }
    
    public boolean isBanned() {
        return this._banned;
    }
    
    public void setBanned(final boolean flag) {
        this._banned = flag;
    }
    
    public int get_food() {
        return this._food;
    }
    
    public void set_food(int i) {
        if (i > 225) {
            i = 225;
        }
        else if (i < 0) {
            i = 0;
        }
        this._food = i;
        if (this._food == 225) {
            final Calendar cal = Calendar.getInstance();
            final long h_time = cal.getTimeInMillis() / 1000L;
            this.set_h_time(h_time);
        }
        else {
            this.set_h_time(-1L);
        }
    }
    
    public L1EquipmentSlot getEquipSlot() {
        return this._equipSlot;
    }
    
    public static L1PcInstance load(final String charName) {
        L1PcInstance result = null;
        try {
            result = CharacterTable.get().loadCharacter(charName);
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        return result;
    }
    
    public void save() throws Exception {
        if (this.isGhost()) {
            return;
        }
        if (this.isInCharReset()) {
            return;
        }
        if (this._other != null) {
            CharOtherReading.get().storeOther(this.getId(), this._other);
        }
        if (this._other1 != null) {
            CharOtherReading1.get().storeOther(this.getId(), this._other1);
        }
        if (this._other2 != null) {
            CharOtherReading2.get().storeOther(this.getId(), this._other2);
        }
        if (this._other3 != null) {
            CharOtherReading3.get().storeOther(this.getId(), this._other3);
        }
        CharacterTable.get().storeCharacter(this);
    }
    
    public void saveInventory() {
        for (final L1ItemInstance item : this.getInventory().getItems()) {
            this.getInventory().saveItem(item, item.getRecordingColumns());
        }
    }
    
    public double getMaxWeight() {
        final int str = this.getStr();
        final int con = this.getCon();
        double maxWeight = 150.0 * Math.floor(0.6 * str + 0.4 * con + 1.0) * this.get_weightUP();
        double weightReductionByArmor = this.getWeightReduction();
        weightReductionByArmor /= 100.0;
        int weightReductionByMagic = 0;
        if (this.hasSkillEffect(14) || this.hasSkillEffect(218)) {
            weightReductionByMagic = 180;
        }
        double originalWeightReduction = 0.0;
        originalWeightReduction += 0.04 * (this.getOriginalStrWeightReduction() + this.getOriginalConWeightReduction());
        final double weightReduction = 1.0 + weightReductionByArmor + originalWeightReduction;
        maxWeight *= weightReduction;
        maxWeight += weightReductionByMagic;
        maxWeight *= ConfigRate.RATE_WEIGHT_LIMIT;
        return maxWeight;
    }
    
    public boolean isRibrave() {
        return this.hasSkillEffect(1017);
    }
    
    public boolean isFastMovable() {
        return this.hasSkillEffect(52) || this.hasSkillEffect(101) || this.hasSkillEffect(150) || this.hasSkillEffect(1017);
    }
    
    public boolean isFastAttackable() {
        return false;
    }
    
    /**
	 * 是否具有勇敢藥水、舞躍之火、血之渴望效果
	 * 
	 * @return
	 */
	public boolean isBrave() {
		return hasSkillEffect(STATUS_BRAVE) || (hasSkillEffect(FIRE_BLESS)) || (hasSkillEffect(BLOODLUST));
	}
    
    public boolean isElfBrave() {
        return this.hasSkillEffect(1016);
    }
    
    public boolean isBraveX() {
        return this.hasSkillEffect(998);
    }
    
    public boolean isHaste() {
        return this.hasSkillEffect(1001) || this.hasSkillEffect(43) || this.hasSkillEffect(54) || this.getMoveSpeed() == 1;
    }
    
    public boolean isInvisDelay() {
        return this.invisDelayCounter > 0;
    }
    
    public void addInvisDelayCounter(final int counter) {
        synchronized (this._invisTimerMonitor) {
            this.invisDelayCounter += counter;
        }
        // monitorexit(this._invisTimerMonitor)
    }
    
    public void beginInvisTimer() {
        this.addInvisDelayCounter(1);
//        GeneralThreadPool.get().pcSchedule(new L1PcInvisDelay(this.getId()), 3000L);
        DeathThreadPool.get().pcSchedule(new L1PcInvisDelay(getId()), DELAY_INVIS);
    }
    
    @Override
    public synchronized void addLawful(final int i) {
        int lawful = this.getLawful() + i;
        if (lawful > 32767) {
            lawful = 32767;
        }
        else if (lawful < -32768) {
            lawful = -32768;
        }
        this.setLawful(lawful);
    }
    
    public synchronized void addExp(final long exp) {
        final long newexp = this._exp + exp;
        if (ConfigOther.PcLevelUpExp > 0 && ConfigOther.PcLevelUp == this.getLevel() && this._exp >= ConfigOther.PcLevelUpExp) {
            return;
        }
        if (!this.isAddExp(newexp)) {
            return;
        }
        this.setExp(newexp);
    }
    
    private boolean isAddExp(final long exp) {
        final int level = ConfigOther.PcLevelUp + 1;
        final long maxExp = ExpTable.getExpByLevel(level) - 44L;
        if (exp >= maxExp) {
            this._exp = maxExp;
            return false;
        }
        return true;
    }
    
    public synchronized void addContribution(final int contribution) {
        this._contribution += contribution;
    }
    
    private void levelUp(final int gap) {
        this.resetLevel();
        for (int i = 0; i < gap; ++i) {
            final short randomHp = CalcStat.calcStatHp(this.getType(), this.getBaseMaxHp(), this.getBaseCon(), this.getOriginalHpup());
            final short randomMp = CalcStat.calcStatMp(this.getType(), this.getBaseMaxMp(), this.getBaseWis(), this.getOriginalMpup());
            this.addBaseMaxHp(randomHp);
            this.addBaseMaxMp(randomMp);
        }
        if (ConfigOther.logpcgiveitem && this.getLevel() >= ConfigOther.logpclevel) {
            try {
                final L1Item l1item = ItemTable.get().getTemplate(43000);
                if (l1item != null && this.getInventory().checkAddItem(l1item, 1L) == 0) {
                    this.getInventory().storeItem(43000, 1L);
                    this.sendPackets(new S_ServerMessage(403, l1item.getName()));
                }
                else {
                    this.sendPackets(new S_SystemMessage("無法獲得轉生藥水。可能此道具不存在！"));
                }
            }
            catch (Exception e) {
                this.sendPackets(new S_SystemMessage("無法獲得轉生藥水。可能此道具不存在！"));
            }
        }
        this.resetBaseHitup();
        this.resetBaseDmgup();
        this.resetBaseAc();
        this.resetBaseMr();
        if (this.getLevel() > this.getHighLevel()) {
            this.setHighLevel(this.getLevel());
        }
        this.setCurrentHp(this.getMaxHp());
        this.setCurrentMp(this.getMaxMp());
        try {
            this.save();
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return;
        }
        finally {
            if (this.power()) {
                this.sendPackets(new S_Bonusstats(this.getId()));
            }
            this.sendPackets(new S_OwnCharStatus(this));
            this.sendPackets(new S_PacketBox(132, this.getEr()));
            Reward.getItem(this);
            Reward1.getItem(this);
            if (lvgiveitemcount.START) {
                L1WilliamLimitedReward.check_Task_For_Level(this);
            }
            MapLevelTable.get().get_level(this.getMapId(), this);
            this.showWindows();
            if (Addskill.START) {
                AutoAddSkillTable.get().forAutoAddSkill(this);
            }
            if (ConfigOther.newcharpra && this.getnewcharpra() && this.getLevel() >= ConfigOther.newcharpralv) {
                this.setnewcharpra(false);
                this.sendPackets(new S_ServerMessage("您的等級已達新手保護機制。"));
            }
        }
        if (this.power()) {
            this.sendPackets(new S_Bonusstats(this.getId()));
        }
        this.sendPackets(new S_OwnCharStatus(this));
        this.sendPackets(new S_PacketBox(132, this.getEr()));
        Reward.getItem(this);
        Reward1.getItem(this);
        if (lvgiveitemcount.START) {
            L1WilliamLimitedReward.check_Task_For_Level(this);
        }
        MapLevelTable.get().get_level(this.getMapId(), this);
        this.showWindows();
        if (Addskill.START) {
            AutoAddSkillTable.get().forAutoAddSkill(this);
        }
        if (ConfigOther.newcharpra && this.getnewcharpra() && this.getLevel() >= ConfigOther.newcharpralv) {
            this.setnewcharpra(false);
            this.sendPackets(new S_ServerMessage("您的等級已達新手保護機制。"));
        }
        if (this.power()) {
            this.sendPackets(new S_Bonusstats(this.getId()));
        }
        this.sendPackets(new S_OwnCharStatus(this));
        this.sendPackets(new S_PacketBox(132, this.getEr()));
        Reward.getItem(this);
        Reward1.getItem(this);
        if (lvgiveitemcount.START) {
            L1WilliamLimitedReward.check_Task_For_Level(this);
        }
        MapLevelTable.get().get_level(this.getMapId(), this);
        this.showWindows();
        if (Addskill.START) {
            AutoAddSkillTable.get().forAutoAddSkill(this);
        }
        if (ConfigOther.newcharpra && this.getnewcharpra() && this.getLevel() >= ConfigOther.newcharpralv) {
            this.setnewcharpra(false);
            this.sendPackets(new S_ServerMessage("您的等級已達新手保護機制。"));
        }
        this.getApprentice();
        this.sendPackets(new S_OwnCharStatus(this));
        Reward.getItem(this);
        Reward1.getItem(this);
        if (lvgiveitemcount.START) {
            L1WilliamLimitedReward.check_Task_For_Level(this);
        }
        MapLevelTable.get().get_level(this.getMapId(), this);
        this.showWindows();
        if (Addskill.START) {
            AutoAddSkillTable.get().forAutoAddSkill(this);
        }
        if (ConfigOther.newcharpra && this.getnewcharpra() && this.getLevel() >= ConfigOther.newcharpralv) {
            this.setnewcharpra(false);
            this.sendPackets(new S_ServerMessage("您的等級已達新手保護機制。"));
        }
    }
    
    public void showWindows() {
        if (this.power()) {
            if (this.getMeteLevel() > 0 && ConfigOther.logpcpower) {
                this.sendPackets(new S_Bonusstats(this.getId()));
            }
            else if (this.getMeteLevel() == 0) {
                this.sendPackets(new S_Bonusstats(this.getId()));
            }
        }
    }
    
    public void isWindows() {
        if (this.power()) {
            this.sendPackets(new S_NPCTalkReturn(this.getId(), "y_qs_10"));
        }
        else {
            this.sendPackets(new S_NPCTalkReturn(this.getId(), "y_qs_00"));
        }
    }
    
    public boolean power() {
        if (this.getLevel() >= 51 && this.getLevel() - 50 > this.getBonusStats()) {
            final int power = this.getBaseStr() + this.getBaseDex() + this.getBaseCon() + this.getBaseInt() + this.getBaseWis() + this.getBaseCha();
            if (power < ConfigAlt.POWER * 6) {
                return true;
            }
        }
        return false;
    }
    
    private void levelDown(final int gap) {
        this.resetLevel();
        for (int i = 0; i > gap; --i) {
            final short randomHp = CalcStat.calcStatHp(this.getType(), 0, this.getBaseCon(), this.getOriginalHpup());
            final short randomMp = CalcStat.calcStatMp(this.getType(), 0, this.getBaseWis(), this.getOriginalMpup());
            this.addBaseMaxHp((short)(-randomHp));
            this.addBaseMaxMp((short)(-randomMp));
        }
        if (this.getLevel() == 1) {
            final int initHp = CalcInitHpMp.calcInitHp(this);
            final int initMp = CalcInitHpMp.calcInitMp(this);
            this.addBaseMaxHp((short)(-this.getBaseMaxHp()));
            this.addBaseMaxHp((short)initHp);
            this.setCurrentHp(initHp);
            this.addBaseMaxMp((short)(-this.getBaseMaxMp()));
            this.addBaseMaxMp((short)initMp);
            this.setCurrentMp(initMp);
        }
        this.resetBaseHitup();
        this.resetBaseDmgup();
        this.resetBaseAc();
        this.resetBaseMr();
        try {
            this.save();
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            return;
        }
        finally {
            this.sendPackets(new S_OwnCharStatus(this));
            this.sendPackets(new S_PacketBox(132, this.getEr()));
            MapLevelTable.get().get_level(this.getMapId(), this);
            this.getApprentice();
        }
        this.sendPackets(new S_OwnCharStatus(this));
        this.sendPackets(new S_PacketBox(132, this.getEr()));
        MapLevelTable.get().get_level(this.getMapId(), this);
        this.getApprentice();
        this.sendPackets(new S_OwnCharStatus(this));
        this.sendPackets(new S_PacketBox(132, this.getEr()));
        MapLevelTable.get().get_level(this.getMapId(), this);
        this.getApprentice();
    }
    
    public boolean isGhost() {
        return this._ghost;
    }
    
    private void setGhost(final boolean flag) {
        this._ghost = flag;
    }
    
    public int get_ghostTime() {
        return this._ghostTime;
    }
    
    public void set_ghostTime(final int ghostTime) {
        this._ghostTime = ghostTime;
    }
    
    public boolean isGhostCanTalk() {
        return this._ghostCanTalk;
    }
    
    private void setGhostCanTalk(final boolean flag) {
        this._ghostCanTalk = flag;
    }
    
    public boolean isReserveGhost() {
        return this._isReserveGhost;
    }
    
    public void setReserveGhost(final boolean flag) {
        this._isReserveGhost = flag;
    }
    
    public void beginGhost(final int locx, final int locy, final short mapid, final boolean canTalk) {
        this.beginGhost(locx, locy, mapid, canTalk, 0);
    }
    
    public void beginGhost(final int locx, final int locy, final short mapid, final boolean canTalk, final int sec) {
        if (this.isGhost()) {
            return;
        }
        this.setGhost(true);
        this._ghostSaveLocX = this.getX();
        this._ghostSaveLocY = this.getY();
        this._ghostSaveMapId = this.getMapId();
        this._ghostSaveHeading = this.getHeading();
        this.setGhostCanTalk(canTalk);
        L1Teleport.teleport(this, locx, locy, mapid, 5, true);
        if (sec > 0) {
            this.set_ghostTime(sec * 1000);
        }
    }
    
    public void makeReadyEndGhost() {
        this.setReserveGhost(true);
        L1Teleport.teleport(this, this._ghostSaveLocX, this._ghostSaveLocY, this._ghostSaveMapId, this._ghostSaveHeading, true);
    }
    
    public void makeReadyEndGhost(final boolean effectble) {
        this.setReserveGhost(true);
        L1Teleport.teleport(this, this._ghostSaveLocX, this._ghostSaveLocY, this._ghostSaveMapId, this._ghostSaveHeading, effectble);
    }
    
    public void endGhost() {
        this.set_ghostTime(-1);
        this.setGhost(false);
        this.setGhostCanTalk(true);
        this.setReserveGhost(false);
    }
    
    public void beginHell(final boolean isFirst) {
        if (this.getMapId() != 666) {
            final int locx = 32701;
            final int locy = 32777;
            final short mapid = 666;
            L1Teleport.teleport(this, locx, locy, mapid, 5, false);
        }
        if (isFirst) {
            if (this.get_PKcount() <= 10) {
                this.setHellTime(180);
            }
            else {
                this.setHellTime(180);
            }
            this.sendPackets(new S_BlueMessage(552, String.valueOf(this.get_PKcount()), String.valueOf(this.getHellTime() / 60)));
        }
        else {
            this.sendPackets(new S_BlueMessage(637, String.valueOf(this.getHellTime())));
        }
    }
    
    public void endHell() {
        final int[] loc = L1TownLocation.getGetBackLoc(4);
        L1Teleport.teleport(this, loc[0], loc[1], (short)loc[2], 5, true);
        try {
            this.save();
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    @Override
    public void setPoisonEffect(final int effectId) {
        this.sendPackets(new S_Poison(this.getId(), effectId));
        if (!this.isGmInvis() && !this.isGhost() && !this.isInvisble()) {
            this.broadcastPacketAll(new S_Poison(this.getId(), effectId));
        }
    }
    
    @Override
    public void healHp(final int pt) {
        super.healHp(pt);
        this.sendPackets(new S_HPUpdate(this));
    }
    
    @Override
    public int getKarma() {
        return this._karma.get();
    }
    
    @Override
    public void setKarma(final int i) {
        this._karma.set(i);
    }
    
    public void addKarma(final int i) {
        synchronized (this._karma) {
            this._karma.add(i);
            this.onChangeKarma();
        }
        // monitorexit(this._karma)
    }
    
    public int getKarmaLevel() {
        return this._karma.getLevel();
    }
    
    public int getKarmaPercent() {
        return this._karma.getPercent();
    }
    
    public Timestamp getLastPk() {
        return this._lastPk;
    }
    
    public void setLastPk(final Timestamp time) {
        this._lastPk = time;
    }
    
    public void setLastPk() {
        this._lastPk = new Timestamp(System.currentTimeMillis());
    }
    
    public boolean isWanted() {
        if (this._lastPk == null) {
            return false;
        }
        if (System.currentTimeMillis() - this._lastPk.getTime() > 3600000L) {
            this.setLastPk(null);
            return false;
        }
        return true;
    }
    
    public Timestamp getLastPkForElf() {
        return this._lastPkForElf;
    }
    
    public void setLastPkForElf(final Timestamp time) {
        this._lastPkForElf = time;
    }
    
    public void setLastPkForElf() {
        this._lastPkForElf = new Timestamp(System.currentTimeMillis());
    }
    
    public boolean isWantedForElf() {
        if (this._lastPkForElf == null) {
            return false;
        }
        if (System.currentTimeMillis() - this._lastPkForElf.getTime() > 86400000L) {
            this.setLastPkForElf(null);
            return false;
        }
        return true;
    }
    
    public Timestamp getDeleteTime() {
        return this._deleteTime;
    }
    
    public void setDeleteTime(final Timestamp time) {
        this._deleteTime = time;
    }
    
    @Override
    public int getMagicLevel() {
        return this.getClassFeature().getMagicLevel(this.getLevel());
    }
    
    public double get_weightUP() {
        return this._weightUP;
    }
    
    public void add_weightUP(final int i) {
        this._weightUP += i / 100.0;
    }
    
    public int getWeightReduction() {
        return this._weightReduction;
    }
    
    public void addWeightReduction(final int i) {
        this._weightReduction += i;
    }
    
    public int getOriginalStrWeightReduction() {
        return this._originalStrWeightReduction;
    }
    
    public int getOriginalConWeightReduction() {
        return this._originalConWeightReduction;
    }
    
    public int getHasteItemEquipped() {
        return this._hasteItemEquipped;
    }
    
    public void addHasteItemEquipped(final int i) {
        this._hasteItemEquipped += i;
    }
    
    public void removeHasteSkillEffect() {
        if (this.hasSkillEffect(29)) {
            this.removeSkillEffect(29);
        }
        if (this.hasSkillEffect(76)) {
            this.removeSkillEffect(76);
        }
        if (this.hasSkillEffect(152)) {
            this.removeSkillEffect(152);
        }
        if (this.hasSkillEffect(43)) {
            this.removeSkillEffect(43);
        }
        if (this.hasSkillEffect(54)) {
            this.removeSkillEffect(54);
        }
        if (this.hasSkillEffect(1001)) {
            this.removeSkillEffect(1001);
        }
    }
    
    public int getDamageReductionByArmor() {
        return this._damageReductionByArmor;
    }
    
    public void addDamageReductionByArmor(final int i) {
        this._damageReductionByArmor += i;
    }
    
    public int getHitModifierByArmor() {
        return this._hitModifierByArmor;
    }
    
    public void addHitModifierByArmor(final int i) {
        this._hitModifierByArmor += i;
    }
    
    public int getDmgModifierByArmor() {
        return this._dmgModifierByArmor;
    }
    
    public void addDmgModifierByArmor(final int i) {
        this._dmgModifierByArmor += i;
    }
    
    public int getBowHitModifierByArmor() {
        return this._bowHitModifierByArmor;
    }
    
    public void addBowHitModifierByArmor(final int i) {
        this._bowHitModifierByArmor += i;
    }
    
    public int getBowDmgModifierByArmor() {
        return this._bowDmgModifierByArmor;
    }
    
    public void addBowDmgModifierByArmor(final int i) {
        this._bowDmgModifierByArmor += i;
    }
    
    private void setGresValid(final boolean valid) {
        this._gresValid = valid;
    }
    
    public boolean isGresValid() {
        return this._gresValid;
    }
    
    public boolean isFishing() {
        return this._isFishing;
    }
    
    public int get_fishX() {
        return this._fishX;
    }
    
    public int get_fishY() {
        return this._fishY;
    }
    
    public void setFishing(final boolean flag, final int fishX, final int fishY) {
        this._isFishing = flag;
        this._fishX = fishX;
        this._fishY = fishY;
    }
    
    public int getFishingPoleId() {
        return this._fishingpoleid;
    }
    
    public void setFishingPoleId(final int itemid) {
        this._fishingpoleid = itemid;
    }
    
    public int getCookingId() {
        return this._cookingId;
    }
    
    public void setCookingId(final int i) {
        this._cookingId = i;
    }
    
    public int getDessertId() {
        return this._dessertId;
    }
    
    public void setDessertId(final int i) {
        this._dessertId = i;
    }
    
    public void resetBaseDmgup() {
        int newBaseDmgup = 0;
        int newBaseBowDmgup = 0;
        if (this.isKnight() || this.isDarkelf() || this.isDragonKnight()) {
            newBaseDmgup = this.getLevel() / 10;
            newBaseBowDmgup = 0;
        }
        else if (this.isElf()) {
            newBaseDmgup = 0;
            newBaseBowDmgup = this.getLevel() / 10;
        }
        this.addDmgup(newBaseDmgup - this._baseDmgup);
        this.addBowDmgup(newBaseBowDmgup - this._baseBowDmgup);
        this._baseDmgup = newBaseDmgup;
        this._baseBowDmgup = newBaseBowDmgup;
    }
    
    public void resetBaseHitup() {
        int newBaseHitup = 0;
        int newBaseBowHitup = 0;
        if (this.isCrown()) {
            newBaseHitup = this.getLevel() / 5;
            newBaseBowHitup = this.getLevel() / 5;
        }
        else if (this.isKnight()) {
            newBaseHitup = this.getLevel() / 3;
            newBaseBowHitup = this.getLevel() / 3;
        }
        else if (this.isElf()) {
            newBaseHitup = this.getLevel() / 5;
            newBaseBowHitup = this.getLevel() / 5;
        }
        else if (this.isDarkelf()) {
            newBaseHitup = this.getLevel() / 3;
            newBaseBowHitup = this.getLevel() / 3;
        }
        else if (this.isDragonKnight()) {
            newBaseHitup = this.getLevel() / 4;
            newBaseBowHitup = this.getLevel() / 4;
        }
        else if (this.isIllusionist()) {
            newBaseHitup = this.getLevel() / 5;
            newBaseBowHitup = this.getLevel() / 5;
        }
        this.addHitup(newBaseHitup - this._baseHitup);
        this.addBowHitup(newBaseBowHitup - this._baseBowHitup);
        this._baseHitup = newBaseHitup;
        this._baseBowHitup = newBaseBowHitup;
    }
    
    public void resetBaseAc() {
        final int newAc = CalcStat.calcAc(this.getLevel(), this.getDex());
        this.addAc(newAc - this._baseAc);
        this._baseAc = newAc;
    }
    
    public void resetBaseMr() {
        int newMr = 0;
        if (this.isCrown()) {
            newMr = 10;
        }
        else if (this.isElf()) {
            newMr = 25;
        }
        else if (this.isWizard()) {
            newMr = 15;
        }
        else if (this.isDarkelf()) {
            newMr = 10;
        }
        else if (this.isDragonKnight()) {
            newMr = 18;
        }
        else if (this.isIllusionist()) {
            newMr = 20;
        }
        newMr += CalcStat.calcStatMr(this.getWis());
        newMr += this.getLevel() / 2;
        this.addMr(newMr - this._baseMr);
        this._baseMr = newMr;
    }
    
    public void resetLevel() {
        this.setLevel(ExpTable.getLevelByExp(this._exp));
    }
    
    public void resetOriginalHpup() {
        this._originalHpup = L1PcOriginal.resetOriginalHpup(this);
    }
    
    public void resetOriginalMpup() {
        this._originalMpup = L1PcOriginal.resetOriginalMpup(this);
    }
    
    public void resetOriginalStrWeightReduction() {
        this._originalStrWeightReduction = L1PcOriginal.resetOriginalStrWeightReduction(this);
    }
    
    public void resetOriginalDmgup() {
        this._originalDmgup = L1PcOriginal.resetOriginalDmgup(this);
    }
    
    public void resetOriginalConWeightReduction() {
        this._originalConWeightReduction = L1PcOriginal.resetOriginalConWeightReduction(this);
    }
    
    public void resetOriginalBowDmgup() {
        this._originalBowDmgup = L1PcOriginal.resetOriginalBowDmgup(this);
    }
    
    public void resetOriginalHitup() {
        this._originalHitup = L1PcOriginal.resetOriginalHitup(this);
    }
    
    public void resetOriginalBowHitup() {
        this._originalBowHitup = L1PcOriginal.resetOriginalBowHitup(this);
    }
    
    public void resetOriginalMr() {
        this.addMr(this._originalMr = L1PcOriginal.resetOriginalMr(this));
    }
    
    public void resetOriginalMagicHit() {
        this._originalMagicHit = L1PcOriginal.resetOriginalMagicHit(this);
    }
    
    public void resetOriginalMagicCritical() {
        this._originalMagicCritical = L1PcOriginal.resetOriginalMagicCritical(this);
    }
    
    public void resetOriginalMagicConsumeReduction() {
        this._originalMagicConsumeReduction = L1PcOriginal.resetOriginalMagicConsumeReduction(this);
    }
    
    public void resetOriginalMagicDamage() {
        this._originalMagicDamage = L1PcOriginal.resetOriginalMagicDamage(this);
    }
    
    public void resetOriginalAc() {
        this._originalAc = L1PcOriginal.resetOriginalAc(this);
        this.addAc(0 - this._originalAc);
    }
    
    public void resetOriginalEr() {
        this._originalEr = L1PcOriginal.resetOriginalEr(this);
    }
    
    public void resetOriginalHpr() {
        this._originalHpr = L1PcOriginal.resetOriginalHpr(this);
    }
    
    public void resetOriginalMpr() {
        this._originalMpr = L1PcOriginal.resetOriginalMpr(this);
    }
    
    public void refresh() {
        this.resetLevel();
        this.resetBaseHitup();
        this.resetBaseDmgup();
        this.resetBaseMr();
        this.resetBaseAc();
        this.resetOriginalHpup();
        this.resetOriginalMpup();
        this.resetOriginalDmgup();
        this.resetOriginalBowDmgup();
        this.resetOriginalHitup();
        this.resetOriginalBowHitup();
        this.resetOriginalMr();
        this.resetOriginalMagicHit();
        this.resetOriginalMagicCritical();
        this.resetOriginalMagicConsumeReduction();
        this.resetOriginalMagicDamage();
        this.resetOriginalAc();
        this.resetOriginalEr();
        this.resetOriginalHpr();
        this.resetOriginalMpr();
        this.resetOriginalStrWeightReduction();
        this.resetOriginalConWeightReduction();
    }
    
    public L1ExcludingList getExcludingList() {
        return this._excludingList;
    }
    
    public int getTeleportX() {
        return this._teleportX;
    }
    
    public void setTeleportX(final int i) {
        this._teleportX = i;
    }
    
    public int getTeleportY() {
        return this._teleportY;
    }
    
    public void setTeleportY(final int i) {
        this._teleportY = i;
    }
    
    public short getTeleportMapId() {
        return this._teleportMapId;
    }
    
    public void setTeleportMapId(final short i) {
        this._teleportMapId = i;
    }
    
    public int getTeleportHeading() {
        return this._teleportHeading;
    }
    
    public void setTeleportHeading(final int i) {
        this._teleportHeading = i;
    }
    
    public int getTempCharGfxAtDead() {
        return this._tempCharGfxAtDead;
    }
    
    private void setTempCharGfxAtDead(final int i) {
        this._tempCharGfxAtDead = i;
    }
    
    public boolean isCanWhisper() {
        return this._isCanWhisper;
    }
    
    public void setCanWhisper(final boolean flag) {
        this._isCanWhisper = flag;
    }
    
    public boolean isShowTradeChat() {
        return this._isShowTradeChat;
    }
    
    public void setShowTradeChat(final boolean flag) {
        this._isShowTradeChat = flag;
    }
    
    public boolean isShowWorldChat() {
        return this._isShowWorldChat;
    }
    
    public void setShowWorldChat(final boolean flag) {
        this._isShowWorldChat = flag;
    }
    
    public int getFightId() {
        return this._fightId;
    }
    
    public void setFightId(final int i) {
        this._fightId = i;
    }
    
    public void checkChatInterval() {
        final long nowChatTimeInMillis = System.currentTimeMillis();
        if (this._chatCount == 0) {
            ++this._chatCount;
            this._oldChatTimeInMillis = nowChatTimeInMillis;
            return;
        }
        final long chatInterval = nowChatTimeInMillis - this._oldChatTimeInMillis;
        if (chatInterval > 2000L) {
            this._chatCount = 0;
            this._oldChatTimeInMillis = 0L;
        }
        else {
            if (this._chatCount >= 5) {
                this.setSkillEffect(4002, 120000);
                this.sendPackets(new S_PacketBox(36, 120));
                this.sendPackets(new S_ServerMessage(153));
                this._chatCount = 0;
                this._oldChatTimeInMillis = 0L;
            }
            ++this._chatCount;
        }
    }
    
    public int getCallClanId() {
        return this._callClanId;
    }
    
    public void setCallClanId(final int i) {
        this._callClanId = i;
    }
    
    public int getCallClanHeading() {
        return this._callClanHeading;
    }
    
    public void setCallClanHeading(final int i) {
        this._callClanHeading = i;
    }
    
    public boolean isInCharReset() {
        return this._isInCharReset;
    }
    
    public void setInCharReset(final boolean flag) {
        this._isInCharReset = flag;
    }
    
    public int getTempLevel() {
        return this._tempLevel;
    }
    
    public void setTempLevel(final int i) {
        this._tempLevel = i;
    }
    
    public int getTempMaxLevel() {
        return this._tempMaxLevel;
    }
    
    public void setTempMaxLevel(final int i) {
        this._tempMaxLevel = i;
    }
    
    public void setSummonMonster(final boolean SummonMonster) {
        this._isSummonMonster = SummonMonster;
    }
    
    public boolean isSummonMonster() {
        return this._isSummonMonster;
    }
    
    public void setShapeChange(final boolean isShapeChange) {
        this._isShapeChange = isShapeChange;
    }
    
    public boolean isShapeChange() {
        return this._isShapeChange;
    }
    
    public void setText(final String text) {
        this._text = text;
    }
    
    public String getText() {
        return this._text;
    }
    
    public void setTextByte(final byte[] textByte) {
        this._textByte = textByte;
    }
    
    public byte[] getTextByte() {
        return this._textByte;
    }
    
    public void set_other(final L1PcOther other) {
        this._other = other;
    }
    
    public L1PcOther get_other() {
        return this._other;
    }
    
    public void set_other1(final L1PcOther1 other1) {
        this._other1 = other1;
    }
    
    public L1PcOther1 get_other1() {
        return this._other1;
    }
    
    public void set_other2(final L1PcOther2 l1PcOther2) {
        this._other2 = l1PcOther2;
    }
    
    public L1PcOther2 get_other2() {
        return this._other2;
    }
    
    public void set_other3(final L1PcOther3 other3) {
        this._other3 = other3;
    }
    
    public L1PcOther3 get_other3() {
        return this._other3;
    }
    
    public void set_otherList(final L1PcOtherList other) {
        this._otherList = other;
    }
    
    public L1PcOtherList get_otherList() {
        return this._otherList;
    }
    
    public void setOleLocX(final int oleLocx) {
        this._oleLocX = oleLocx;
    }
    
    public int getOleLocX() {
        return this._oleLocX;
    }
    
    public void setOleLocY(final int oleLocy) {
        this._oleLocY = oleLocy;
    }
    
    public int getOleLocY() {
        return this._oleLocY;
    }
    
    public void set_isClanGfx(final boolean b) {
        this._isClanGfx = b;
    }
    
    public boolean isClanGfx() {
        return this._isClanGfx;
    }
    
    public int getWenyangJiFen() {
        return this._wenyangjifen;
    }
    
    public void setWenyangJiFen(final int i) {
        this._wenyangjifen = i;
    }
    
    public int getWyType1() {
        return this._wytype1;
    }
    
    public void setWyType1(final int i) {
        this._wytype1 = i;
    }
    
    public int getWyType2() {
        return this._wytype2;
    }
    
    public void setWyType2(final int i) {
        this._wytype2 = i;
    }
    
    public int getWyType3() {
        return this._wytype3;
    }
    
    public void setWyType3(final int i) {
        this._wytype3 = i;
    }
    
    public int getWyType4() {
        return this._wytype4;
    }
    
    public void setWyType4(final int i) {
        this._wytype4 = i;
    }
    
    public int getWyType5() {
        return this._wytype5;
    }
    
    public void setWyType5(final int i) {
        this._wytype5 = i;
    }
    
    public int getWyLevel1() {
        return this._wylevel1;
    }
    
    public void setWyLevel1(final int i) {
        this._wylevel1 = i;
    }
    
    public int getWyLevel2() {
        return this._wylevel2;
    }
    
    public void setWyLevel2(final int i) {
        this._wylevel2 = i;
    }
    
    public int getWyLevel3() {
        return this._wylevel3;
    }
    
    public void setWyLevel3(final int i) {
        this._wylevel3 = i;
    }
    
    public int getWyLevel4() {
        return this._wylevel4;
    }
    
    public void setWyLevel4(final int i) {
        this._wylevel4 = i;
    }
    
    public int getWyLevel5() {
        return this._wylevel5;
    }
    
    public void setWyLevel5(final int i) {
        this._wylevel5 = i;
    }
    
    public int getWyjilvjia() {
        return this._wyjiajilv;
    }
    
    public void setWyjilvjia(final int i) {
        this._wyjiajilv = i;
    }
    
    public int getWyjilvjian() {
        return this._wyjianjilv;
    }
    
    public void setWyjilvjian(final int i) {
        this._wyjianjilv = i;
    }
    
    public L1PcInstance() {
        this._originalBowDmgup = 0;
        this._originalHitup = 0;
        this._originalBowHitup = 0;
        this._skins = new HashMap<Integer, L1SkinInstance>();
        this._isKill = false;
        this._attackenemy = new ArrayList<String>();
        this._Badattackenemy = new ArrayList<String>();
        this._hpr = 0;
        this._trueHpr = 0;
        this._mpr = 0;
        this._trueMpr = 0;
        this._originalHpr = 0;
        this._originalMpr = 0;
        this._hpRegenType = 0;
        this._hpRegenState = 4;
        this._mpRegenType = 0;
        this._mpRegenState = 4;
        this._awakeMprTime = 0;
        this._awakeSkillId = 0;
        this._jl1 = false;
        this._jl2 = false;
        this._jl3 = false;
        this._el1 = false;
        this._el2 = false;
        this._el3 = false;
        this._isCHAOTIC = false;
        this._skillList = new ArrayList<Integer>();
        this._classFeature = null;
        this._sellList = new ArrayList<L1PrivateShopSellList>();
        this._buyList = new ArrayList<L1PrivateShopBuyList>();
        this._isPrivateShop = false;
        this._isTradingInPrivateShop = false;
        this._partnersPrivateShopItemCount = 0;
        this._oldTime = 0L;
        this._originalEr = 0;
        this._netConnection = null;
        this._karma = new L1Karma();
        this._isTeleport = false;
        this._isDrink = false;
        this._isGres = false;
        this.target = null;
        this._baseMaxHp = 0;
        this._baseMaxMp = 0;
        this._baseAc = 0;
        this._originalAc = 0;
        this._baseStr = 0;
        this._baseCon = 0;
        this._baseDex = 0;
        this._baseCha = 0;
        this._baseInt = 0;
        this._baseWis = 0;
        this._originalStr = 0;
        this._originalCon = 0;
        this._originalDex = 0;
        this._originalCha = 0;
        this._originalInt = 0;
        this._originalWis = 0;
        this._originalDmgup = 0;
        this._originalBowDmgup = 0;
        this._originalHitup = 0;
        this._originalBowHitup = 0;
        this._originalMr = 0;
        this._originalMagicHit = 0;
        this._originalMagicCritical = 0;
        this._originalMagicConsumeReduction = 0;
        this._originalMagicDamage = 0;
        this._originalHpup = 0;
        this._originalMpup = 0;
        this._baseDmgup = 0;
        this._baseBowDmgup = 0;
        this._baseHitup = 0;
        this._baseBowHitup = 0;
        this._baseMr = 0;
        this.invisDelayCounter = 0;
        this._invisTimerMonitor = new Object();
        this._ghost = false;
        this._ghostTime = -1;
        this._ghostCanTalk = true;
        this._isReserveGhost = false;
        this._ghostSaveLocX = 0;
        this._ghostSaveLocY = 0;
        this._ghostSaveMapId = 0;
        this._ghostSaveHeading = 0;
        this._weightUP = 1.0;
        this._weightReduction = 0;
        this._originalStrWeightReduction = 0;
        this._originalConWeightReduction = 0;
        this._hasteItemEquipped = 0;
        this._damageReductionByArmor = 0;
        this._hitModifierByArmor = 0;
        this._dmgModifierByArmor = 0;
        this._bowHitModifierByArmor = 0;
        this._bowDmgModifierByArmor = 0;
        this._isFishing = false;
        this._fishX = -1;
        this._fishY = -1;
        this._cookingId = 0;
        this._dessertId = 0;
        this._excludingList = new L1ExcludingList();
        this._teleportX = 0;
        this._teleportY = 0;
        this._teleportMapId = 0;
        this._teleportHeading = 0;
        this._isCanWhisper = true;
        this._isShowTradeChat = true;
        this._isShowWorldChat = true;
        this._chatCount = 0;
        this._oldChatTimeInMillis = 0L;
        this._isInCharReset = false;
        this._tempLevel = 1;
        this._tempMaxLevel = 1;
        this._isSummonMonster = false;
        this._isShapeChange = false;
        this._textByte = null;
        this._outChat = null;
        this._mazu = false;
        this._mazu_time = 0L;
        this._expadd = 0.0;
        this._isFoeSlayer = false;
        this._actionId = -1;
        this._allpowers = new ConcurrentHashMap<Integer, L1ItemPower_text>();
        this._magic_modifier_dmg = 0;
        this._magic_reduction_dmg = 0;
        this._rname = false;
        this._retitle = false;
        this._repass = 0;
        this._trade_items = new ArrayList<L1TradeItem>();
        this._mode_id = 0;
        this._check_item = false;
        this._vip_1 = false;
        this._vip_2 = false;
        this._vip_3 = false;
        this._vip_4 = false;
        this._global_time = 0L;
        this._doll_hpr = 0;
        this._doll_hpr_time = 0;
        this._doll_hpr_time_src = 0;
        this._doll_mpr = 0;
        this._doll_mpr_time = 0;
        this._doll_mpr_time_src = 0;
        this._doll_get = new int[2];
        this._doll_get_time = 0;
        this._doll_get_time_src = 0;
        this._spr_move_time = 0L;
        this._spr_attack_time = 0L;
        this._spr_skill_time = 0L;
        this._delete_time = 0;
        this._up_hp_potion = 0;
        this._venom_resist = 0;
        this._speed = null;
        this._arena = 0;
        this._temp_adena = 0;
        this._temp_adena1 = 0;
        this._temp_adena2 = 0;
        this._ss_time = 0L;
        this._ss = 0;
        this._elitePlateMail_Fafurion = 0;
        this._fafurion_hpmin = 0;
        this._fafurion_hpmax = 0;
        this._SummonId = 0;
        this._lap = 1;
        this._lapCheck = 0;
        this._order_list = false;
        this._state = 0;
        this._isClanGfx = false;
        this._target = null;
        this._InviteList = new ArrayList<String>();
        this._cmalist = new ArrayList<String>();
        this._isATeam = false;
        this._isBTeam = false;
//        this._acceleratorChecker = new AcceleratorChecker(this);
        this._Slot = 0;
        this._itempoly = false;
        this._itempoly1 = false;
        this._stunlevel = 0;
        this._other_ReductionDmg = 0;
        this._Clan_ReductionDmg = 0;
        this._Clanmagic_reduction_dmg = 0;
        this._addExpByArmor = 0.0;
        this._PcContribution = 0;
        this._clanContribution = 0;
        this._clanadena = 0;
        this._checkgm = false;
        this.check_lv = false;
        this._EsotericSkill = 0;
        this._EsotericCount = 0;
        this._isEsoteric = false;
        this._TripleArrow = false;
        this._checklogpc = false;
        this._savepclog = 0;
        this._ReductionDmg = 0;
        this._pcdmg = 0;
        this._paycount = 0;
        this._ArmorCount1 = 0;
        this._logintime = 0;
        this._logintime1 = 0;
        this._PartyExp = 0.0;
        this.ATK_ai = false;
        this._dolldamageReductionByArmor = 0;
        this._reduction_dmg = 0;
        this._isTeleportToOk = false;
        this._MOVE_STOP = false;
        this._amount = 0;
        this._consume_point = 0L;
        this._tempStr = 0;
        this._tempDex = 0;
        this._tempCon = 0;
        this._tempWis = 0;
        this._tempCha = 0;
        this._tempInt = 0;
        this._tempInitPoint = 0;
        this._tempElixirstats = 0;
        this.weapondmg = 0;
        this._Dmgdouble = 0.0;
        this._PVPdmg = 0;
        this._PVPdmgReduction = 0;
        this._attr_potion_heal = 0;
        this._penetrate = 0;
        this._attr_物理格檔 = 0;
        this._attr_魔法格檔 = 0;
        this._addStunLevel = 0;
        this._loginpoly = 0;
        this._range = 0;
        this._day = 0;
        this._prestige = 0;
        this._prestigeLv = 0;
        this._oldexp = 0L;
        this._isItemName = false;
        this._isItemopen = false;
        this._isfollow = false;
        this._isfollowcheck = false;
        this.guaji_poly = 0;
        this._hateList = new L1HateList();
        this._firstAttack = false;
        this._pcMove = null;
        this.move = 0;
        this._aiRunning = false;
        this._actived = false;
        this._Pathfinding = false;
        this._randomMoveDirection = 0;
        this._tguajiX = 0;
        this._tguajiY = 0;
        this._guajiMapId = 0;
        this._armorbreaklevel = 0;
        this._soulHp_r = 0;
        this._soulHp_hpmin = 0;
        this._soulHp_hpmax = 0;
        this.isSoulHp = 0;
        this.soulHp = new ArrayList<Integer>();
        this._PVPdmgg = 0;
        this._weaponSkillChance = 0;
        this._addWeaponSkillDmg = 0.0;
        this._newcharpra = false;
        this._opengfxid = true;
        this.ValakasStatus = 0;
        this._followmebuff = false;
        this._ItemBlendcheckitem = 0;
        this._ItemBlendcheckitemcount = 0;
        this._ItemBlendResdueItem = 0;
        this._ItemBlendResdueItemcount = 0;
        this._ItemBlendResdueItemLv = 0;
        this._ItemBlendrnd = 0;
        this._ItemBlendGvEnchantlvl = 0;
        this._hppotion = 0;
        this._pvp = 0;
        this._bowpvp = 0;
        this._followxy1 = 1;
        this._polyarrow = 66;
        this._changtype1 = 0;
        this._changtype2 = 0;
        this._changtype3 = 0;
        this._changtype4 = 0;
        this._changtype5 = 0;
        this._pag = 0;
        this._keyenemy = false;
        this._outenemy = false;
        this._npcdmg = 0.0;
        this._newai1 = 0;
        this._newai2 = 0;
        this._newai3 = 0;
        this._newai4 = 0;
        this._newai5 = 0;
        this._newai6 = 0;
        this._newaiq1 = 0;
        this._newaiq2 = 0;
        this._newaiq3 = 0;
        this._newaiq4 = 0;
        this._newaiq5 = 0;
        this._newaiq6 = 0;
        this._newaiq7 = 0;
        this._newaiq8 = 0;
        this._newaiq9 = 0;
        this._newaiq0 = 0;
        this._npciddmg = 0;
        this._followatk = false;
        this._followatkmagic = false;
        this._isfollowskill26 = false;
        this._isfollowskill42 = false;
        this._isfollowskill55 = false;
        this._isfollowskill68 = false;
        this._isfollowskill160 = false;
        this._isfollowskill79 = false;
        this._isfollowskill148 = false;
        this._isfollowskill151 = false;
        this._isfollowskill149 = false;
        this._isfollowskill158 = false;
        this._isnomoveguaji = false;
        this._Badkeyenemy = false;
        this._Badoutenemy = false;
        this._oldMapId = 0;
        this._ischeckpoly = false;
        this._isOutbur = false;
        this._ischeckOutbur = false;
        this._WeaponTotalDmg = 0;
        this._WeaponSkillPro = 0;
        this._Save_Quest_Map1 = 0;
        this._Save_Quest_Map2 = 0;
        this._Save_Quest_Map3 = 0;
        this._Save_Quest_Map4 = 0;
        this._Save_Quest_Map5 = 0;
        this._CardId = 0;
        this.soulTower = 0;
        this._isarmor_setgive = false;
        this._summon_skillid = 0;
        this._summon_skillidmp = 0;
        this._Doll_MagicHit = 0;
        this._first_pay = 0;
        this._au_shop = false;
        this._au_buyitemswitch = new int[9];
        this._au_buyitemcount = new int[9];
        this._au_setshop = false;
        this._setshoptype = -1;
        this._au_automagic = new int[9];
        this._a53 = 0;
        this._summon_skillidmp_1 = 0;
        this._au_autoset = new int[8];
        this._autoX = 0;
        this._autoy = 0;
        this._autom = 0;
        this._au_otherautoset = new int[8];
        this._cmpcount = 0;
        this._weapon_b = 0.0;
        this._weapon_b_gfx_r = 0;
        this._backpage = 1;
        this.Dmgup_b_ran = 0;
        this.Dmgup_b = 0.0;
        this.bosspage = 1;
        this._autobuff = new ArrayList<Integer>();
        this._cardpoly = 0;
        this._pcezpay = 0;
        this._近戰爆擊倍率 = 0.0;
        this._遠攻爆擊倍率 = 0.0;
        this._魔法爆擊倍率 = 0.0;
        this._armortype = new int[324];
        this._vviipp = false;
        this._accessLevel = 0;
        this._currentWeapon = 0;
        this._inventory = new L1PcInventory(this);
        this._dwarf = new L1DwarfInventory(this);
        this._dwarfForCha = new L1DwarfForChaInventory(this);
        this._dwarfForElf = new L1DwarfForElfInventory(this);
        this._tradewindow = new L1Inventory();
        this._quest = new L1PcQuest(this);
        this._action = new L1ActionPc(this);
        this._actionPet = new L1ActionPet(this);
        this._actionSummon = new L1ActionSummon(this);
        this._equipSlot = new L1EquipmentSlot(this);
    }
    
    public void setNowTarget(final L1Character target) {
        this._target = target;
    }
    
    public L1Character getNowTarget() {
        return this._target;
    }
    
    public void setPetModel() {
        try {
            for (final L1NpcInstance petNpc : this.getPetList().values()) {
                if (petNpc != null) {
                    if (petNpc instanceof L1SummonInstance) {
                        final L1SummonInstance summon = (L1SummonInstance)petNpc;
                        summon.set_tempModel();
                    }
                    else {
                        if (!(petNpc instanceof L1PetInstance)) {
                            continue;
                        }
                        final L1PetInstance pet = (L1PetInstance)petNpc;
                        pet.set_tempModel();
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void getPetModel() {
        try {
            for (final L1NpcInstance petNpc : this.getPetList().values()) {
                if (petNpc != null) {
                    if (petNpc instanceof L1SummonInstance) {
                        final L1SummonInstance summon = (L1SummonInstance)petNpc;
                        summon.get_tempModel();
                    }
                    else {
                        if (!(petNpc instanceof L1PetInstance)) {
                            continue;
                        }
                        final L1PetInstance pet = (L1PetInstance)petNpc;
                        pet.get_tempModel();
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void set_outChat(final L1DeInstance b) {
        this._outChat = b;
    }
    
    public L1DeInstance get_outChat() {
        return this._outChat;
    }
    
    public long get_h_time() {
        return this._h_time;
    }
    
    public void set_h_time(final long time) {
        this._h_time = time;
    }
    
    public void set_mazu(final boolean b) {
        this._mazu = b;
    }
    
    public boolean is_mazu() {
        return this._mazu;
    }
    
    public long get_mazu_time() {
        return this._mazu_time;
    }
    
    public void set_mazu_time(final long time) {
        this._mazu_time = time;
    }
    
    public void set_dmgAdd(final int int1, final int int2) {
        this._int1 += int1;
        this._int2 += int2;
    }
    
    public int dmgAdd() {
        if (this._int2 == 0) {
            return 0;
        }
        if (L1PcInstance._random.nextInt(100) + 1 <= this._int2) {
            if (!this.getDolls().isEmpty()) {
                for (final L1DollInstance doll : this.getDolls().values()) {
                    doll.show_action(1);
                }
            }
            return this._int1;
        }
        return 0;
    }
    
    public void set_evasion(final int int1) {
        this._evasion += int1;
    }
    
    public int get_evasion() {
        return this._evasion;
    }
    
    public double getExpAdd() {
        return this._expadd;
    }
    
    public void add_exp(final int s) {
        if (s > 0) {
            this._expadd = DoubleUtil.sum(this._expadd, s / 100.0);
        }
        else {
            this._expadd = DoubleUtil.sub(this._expadd, s * -1 / 100.0);
        }
    }
    
    public void set_dmgDowe(final int int1, final int int2) {
        this._dd1 += int1;
        this._dd2 += int2;
    }
    
    public int dmgDowe() {
        if (this.has_powerid(6611)) {
            final int rad = 10;
            if (L1PcInstance._random.nextInt(100) < rad) {
                this.sendPacketsAll(new S_SkillSound(this.getId(), 9800));
                return this._dd1;
            }
        }
        if (this._dd2 == 0) {
            return 0;
        }
        if (L1PcInstance._random.nextInt(100) + 1 <= this._dd2) {
            if (!this.getDolls().isEmpty()) {
                for (final L1DollInstance doll : this.getDolls().values()) {
                    doll.show_action(2);
                }
            }
            return this._dd1;
        }
        return 0;
    }
    
    public boolean isFoeSlayer() {
        return this._isFoeSlayer;
    }
    
    public void setFoeSlayer(final boolean FoeSlayer) {
        this._isFoeSlayer = FoeSlayer;
    }
    
    public long get_weaknss_t() {
        return this._weaknss_t;
    }
    
    public int get_weaknss() {
        return this._weaknss;
    }
    
    public void set_weaknss(final int lv, final long t) {
        this._weaknss = lv;
        this._weaknss_t = t;
    }
    
    public void set_actionId(final int actionId) {
        this._actionId = actionId;
    }
    
    public int get_actionId() {
        return this._actionId;
    }
    
    public void set_hardinR(final Chapter01R hardin) {
        this._hardin = hardin;
    }
    
    public Chapter01R get_hardinR() {
        return this._hardin;
    }
    
    public void add_power(final L1ItemPower_text value, final L1ItemInstance eq) {
        if (!this._allpowers.containsKey(value.get_id())) {
            this._allpowers.put(value.get_id(), value);
        }
        if (eq.isEquipped()) {
            value.add_pc_power(this);
            this.sendPackets(new S_ServerMessage("\\fW獲得" + value.getMsg() + " 效果"));
        }
        if (value.getGfx() != null) {
            final int[] arrayOfInt;
            final int i = (arrayOfInt = value.getGfx()).length;
            for (byte b = 0; b < i; ++b) {
                final int gfx = arrayOfInt[b];
                this.sendPacketsAll(new S_SkillSound(this.getId(), gfx));
            }
        }
    }
    
    public void remove_power(final L1ItemPower_text value, final L1ItemInstance eq) {
        if (this._allpowers.containsKey(value.get_id())) {
            this._allpowers.remove(value.get_id());
        }
        if (!eq.isEquipped()) {
            value.remove_pc_power(this);
            this.sendPackets(new S_ServerMessage("\\fY失去 " + value.getMsg() + " 效果"));
        }
    }
    
    public boolean has_powerid(final int powerid) {
        return this._allpowers.containsKey(powerid);
    }
    
    public Map<Integer, L1ItemPower_text> get_allpowers() {
        return this._allpowers;
    }
    
    public void set_unfreezingTime(final int i) {
        this._unfreezingTime = i;
    }
    
    public int get_unfreezingTime() {
        return this._unfreezingTime;
    }
    
    public void set_misslocTime(final int i) {
        this._misslocTime = i;
    }
    
    public int get_misslocTime() {
        return this._misslocTime;
    }
    
    public void set_c_power(final L1User_Power power) {
        this._c_power = power;
    }
    
    public L1User_Power get_c_power() {
        return this._c_power;
    }
    
    public void add_dice_hp(final int dice_hp, final int sucking_hp) {
        this._dice_hp += dice_hp;
        this._sucking_hp += sucking_hp;
    }
    
    public int dice_hp() {
        return this._dice_hp;
    }
    
    public int sucking_hp() {
        return this._sucking_hp;
    }
    
    public void add_dice_mp(final int dice_mp, final int sucking_mp) {
        this._dice_mp += dice_mp;
        this._sucking_mp += sucking_mp;
    }
    
    public int dice_mp() {
        return this._dice_mp;
    }
    
    public int sucking_mp() {
        return this._sucking_mp;
    }
    
    public void add_double_dmg(final int double_dmg) {
        this._double_dmg += double_dmg;
    }
    
    public int get_double_dmg() {
        return this._double_dmg;
    }
    
    public void add_lift(final int lift) {
        this._lift += lift;
    }
    
    public int lift() {
        return this._lift;
    }
    
    public void add_magic_modifier_dmg(final int add) {
        this._magic_modifier_dmg += add;
    }
    
    public int get_magic_modifier_dmg() {
        return this._magic_modifier_dmg;
    }
    
    public void add_magic_reduction_dmg(final int add) {
        this._magic_reduction_dmg += add;
    }
    
    public int get_magic_reduction_dmg() {
        return this._magic_reduction_dmg;
    }
    
    public void rename(final boolean b) {
        this._rname = b;
    }
    
    public boolean is_rname() {
        return this._rname;
    }
    
    public boolean is_retitle() {
        return this._retitle;
    }
    
    public void retitle(final boolean b) {
        this._retitle = b;
    }
    
    public int is_repass() {
        return this._repass;
    }
    
    public void repass(final int b) {
        this._repass = b;
    }
    
    public void add_trade_item(final L1TradeItem info) {
        if (this._trade_items.size() == 16) {
            return;
        }
        this._trade_items.add(info);
    }
    
    public ArrayList<L1TradeItem> get_trade_items() {
        return this._trade_items;
    }
    
    public void get_trade_clear() {
        this._tradeID = 0;
        this._trade_items.clear();
    }
    
    public void set_mode_id(final int mode) {
        this._mode_id = mode;
    }
    
    public int get_mode_id() {
        return this._mode_id;
    }
    
    public void set_check_item(final boolean b) {
        this._check_item = b;
    }
    
    public boolean get_check_item() {
        return this._check_item;
    }
    
    public void set_VIP1(final boolean b) {
        this._vip_1 = b;
    }
    
    public void set_VIP2(final boolean b) {
        this._vip_2 = b;
    }
    
    public void set_VIP3(final boolean b) {
        this._vip_3 = b;
    }
    
    public void set_VIP4(final boolean b) {
        this._vip_4 = b;
    }
    
    public long get_global_time() {
        return this._global_time;
    }
    
    public void set_global_time(final long global_time) {
        this._global_time = global_time;
    }
    
    public int get_doll_hpr() {
        return this._doll_hpr;
    }
    
    public void set_doll_hpr(final int hpr) {
        this._doll_hpr = hpr;
    }
    
    public int get_doll_hpr_time() {
        return this._doll_hpr_time;
    }
    
    public void set_doll_hpr_time(final int time) {
        this._doll_hpr_time = time;
    }
    
    public int get_doll_hpr_time_src() {
        return this._doll_hpr_time_src;
    }
    
    public void set_doll_hpr_time_src(final int time) {
        this._doll_hpr_time_src = time;
    }
    
    public int get_doll_mpr() {
        return this._doll_mpr;
    }
    
    public void set_doll_mpr(final int mpr) {
        this._doll_mpr = mpr;
    }
    
    public int get_doll_mpr_time() {
        return this._doll_mpr_time;
    }
    
    public void set_doll_mpr_time(final int time) {
        this._doll_mpr_time = time;
    }
    
    public int get_doll_mpr_time_src() {
        return this._doll_mpr_time_src;
    }
    
    public void set_doll_mpr_time_src(final int time) {
        this._doll_mpr_time_src = time;
    }
    
    public int[] get_doll_get() {
        return this._doll_get;
    }
    
    public void set_doll_get(final int itemid, final int count) {
        this._doll_get[0] = itemid;
        this._doll_get[1] = count;
    }
    
    public int get_doll_get_time() {
        return this._doll_get_time;
    }
    
    public void set_doll_get_time(final int time) {
        this._doll_get_time = time;
    }
    
    public int get_doll_get_time_src() {
        return this._doll_get_time_src;
    }
    
    public void set_doll_get_time_src(final int time) {
        this._doll_get_time_src = time;
    }
    
    public void set_board_title(final String text) {
        this._board_title = text;
    }
    
    public String get_board_title() {
        return this._board_title;
    }
    
    public void set_board_content(final String text) {
        this._board_content = text;
    }
    
    public String get_board_content() {
        return this._board_content;
    }
    
    public void set_spr_move_time(final long spr_time) {
        this._spr_move_time = spr_time;
    }
    
    public long get_spr_move_time() {
        return this._spr_move_time;
    }
    
    public void set_spr_attack_time(final long spr_time) {
        this._spr_attack_time = spr_time;
    }
    
    public long get_spr_attack_time() {
        return this._spr_attack_time;
    }
    
    public void set_spr_skill_time(final long spr_time) {
        this._spr_skill_time = spr_time;
    }
    
    public long get_spr_skill_time() {
        return this._spr_skill_time;
    }
    
    public void set_delete_time(final int time) {
        this._delete_time = time;
    }
    
    public int get_delete_time() {
        return this._delete_time;
    }
    
    public void add_up_hp_potion(final int up_hp_potion) {
        this._up_hp_potion += up_hp_potion;
    }
    
    public int get_up_hp_potion() {
        return this._up_hp_potion;
    }
    
    public void add_uhp_number(final int uhp_number) {
        this._uhp_number += uhp_number;
    }
    
    public int get_uhp_number() {
        return this._uhp_number;
    }
    
    public void set_venom_resist(final int i) {
        this._venom_resist += i;
    }
    
    public int get_venom_resist() {
        return this._venom_resist;
    }
    
    public void addInviteList(final String playername) {
        if (this._InviteList.contains(playername)) {
            return;
        }
        this._InviteList.add(playername);
    }
    
    public void removeInviteList(final String name) {
        if (!this._InviteList.contains(name)) {
            return;
        }
        this._InviteList.remove(name);
    }
    
    public ArrayList<String> getInviteList() {
        return this._InviteList;
    }
    
    public void addCMAList(final String clanname) {
        if (this._cmalist.contains(clanname)) {
            return;
        }
        this._cmalist.add(clanname);
    }
    
    public void removeCMAList(final String name) {
        if (!this._cmalist.contains(name)) {
            return;
        }
        this._cmalist.remove(name);
    }
    
    public ArrayList<String> getCMAList() {
        return this._cmalist;
    }
    
    public final int getEmblemId() {
        if (this.isProtector() || this.getClanid() <= 0) {
            return 0;
        }
        final L1Clan clan = this.getClan();
        if (clan == null) {
            return 0;
        }
        return clan.getEmblemId();
    }
    
//    public AcceleratorChecker speed_Attack() {
//        if (this._speed == null) {
//            this._speed = new AcceleratorChecker(this);
//        }
//        return this._speed;
//    }
    
    public void set_arena(final int i) {
        this._arena = i;
    }
    
    public int get_arena() {
        return this._arena;
    }
    
    public void set_temp_adena(final int itemid) {
        this._temp_adena = itemid;
    }
    
    public int get_temp_adena() {
        return this._temp_adena;
    }
    
    public void set_temp_adena1(final int itemid) {
        this._temp_adena1 = itemid;
    }
    
    public int get_temp_adena1() {
        return this._temp_adena1;
    }
    
    public void set_temp_adena2(final int itemid) {
        this._temp_adena2 = itemid;
    }
    
    public int get_temp_adena2() {
        return this._temp_adena2;
    }
    
    public long get_ss_time() {
        return this._ss_time;
    }
    
    public void set_ss_time(final long ss_time) {
        this._ss_time = ss_time;
    }
    
    public int get_ss() {
        return this._ss;
    }
    
    public void set_ss_time(final int ss) {
        this._ss = ss;
    }
    
    public final int getKillCount() {
        return this.killCount;
    }
    
    public final void setKillCount(final int killCount) {
        this.killCount = killCount;
    }
    
    public int getMeteLevel() {
        return this._meteLevel;
    }
    
    public void setMeteLevel(final int i) {
        this._meteLevel = i;
    }
    
    public final L1MeteAbility getMeteAbility() {
        return this._meteAbility;
    }
    
    public final void resetMeteAbility() {
        if (this._meteAbility != null) {
            ExtraMeteAbilityTable.effectBuff(this, this._meteAbility, -1);
        }
        this._meteAbility = ExtraMeteAbilityTable.getInstance().get(this.getMeteLevel(), this.getType());
        if (this._meteAbility != null) {
            ExtraMeteAbilityTable.effectBuff(this, this._meteAbility, 1);
        }
    }
    
    public final boolean isEffectDADIS() {
        return this._EffectDADIS;
    }
    
    public final void setDADIS(final boolean checkFlag) {
        if (this._EffectDADIS != checkFlag) {
            this.giveDADIS(checkFlag);
            this.sendPackets(new S_HPUpdate(this));
            if (this.isInParty()) {
                this.getParty().updateMiniHP(this);
            }
            this.sendPackets(new S_MPUpdate(this));
            this.sendPackets(new S_SPMR(this));
            L1PcUnlock.Pc_Unlock(this);
        }
    }
    
    public final void giveDADIS(final boolean checkFlag) {
        this._EffectDADIS = checkFlag;
        if (checkFlag) {
            this.addMaxHp(50);
            this.addMaxMp(30);
            this.addDmgup(3);
            this.addBowDmgup(3);
            this.addSp(3);
            this.addDamageReductionByArmor(3);
            this.addHpr(3);
            this.addMpr(3);
        }
        else {
            this.addMaxHp(-50);
            this.addMaxMp(-30);
            this.addDmgup(-3);
            this.addBowDmgup(-3);
            this.addSp(-3);
            this.addDamageReductionByArmor(-3);
            this.addHpr(-3);
            this.addMpr(-3);
            this.sendPackets(new S_PacketBox(180, 553, 3907, 0));
        }
    }
    
    public final boolean isEffectGS() {
        return this._EffectGS;
    }
    
    public final void setGS(final boolean checkFlag) {
        if (this._EffectGS != checkFlag) {
            this.giveGS(checkFlag);
            this.sendPackets(new S_HPUpdate(this));
            if (this.isInParty()) {
                this.getParty().updateMiniHP(this);
            }
            this.sendPackets(new S_MPUpdate(this));
            this.sendPackets(new S_SPMR(this));
            L1PcUnlock.Pc_Unlock(this);
        }
    }
    
    public final void giveGS(final boolean checkFlag) {
        this._EffectGS = checkFlag;
        if (checkFlag) {
            this.addMaxHp(30);
            this.addMaxMp(30);
            this.addDmgup(2);
            this.addBowDmgup(2);
            this.addSp(2);
            this.addDamageReductionByArmor(2);
            this.addHpr(2);
            this.addMpr(2);
        }
        else {
            this.addMaxHp(-30);
            this.addMaxMp(-30);
            this.addDmgup(-2);
            this.addBowDmgup(-2);
            this.addSp(-2);
            this.addDamageReductionByArmor(-2);
            this.addHpr(-2);
            this.addMpr(-2);
        }
    }
    
    public final boolean isProtector() {
        return this._isProtector;
    }
    
    public final void setProtector(final boolean checkFlag) {
        if (this._isProtector != checkFlag) {
            this.giveProtector(checkFlag);
            this.sendPackets(new S_HPUpdate(this));
            if (this.isInParty()) {
                this.getParty().updateMiniHP(this);
            }
            this.sendPackets(new S_MPUpdate(this));
            this.sendPackets(new S_SPMR(this));
            this.sendPackets(new S_OwnCharStatus(this));
            this.sendPackets(new S_OwnCharPack(this));
            this.removeAllKnownObjects();
            this.updateObject();
        }
    }
    
    public final void giveProtector(final boolean checkFlag) {
        this._isProtector = checkFlag;
        if (checkFlag) {
            this.addMaxHp(ProtectorSet.HP_UP);
            this.addMaxMp(ProtectorSet.MP_UP);
            this.addDmgup(ProtectorSet.DMG_UP);
            this.addBowDmgup(ProtectorSet.DMG_UP);
            this.addDamageReductionByArmor(ProtectorSet.DMG_DOWN);
            this.addSp(ProtectorSet.SP_UP);
            this.sendPackets(new S_PacketBox(144, 1));
        }
        else {
            this.addMaxHp(-ProtectorSet.HP_UP);
            this.addMaxMp(-ProtectorSet.MP_UP);
            this.addDmgup(-ProtectorSet.DMG_UP);
            this.addBowDmgup(-ProtectorSet.DMG_UP);
            this.addDamageReductionByArmor(-ProtectorSet.DMG_DOWN);
            this.addSp(-ProtectorSet.SP_UP);
            this.sendPackets(new S_PacketBox(144, 0));
        }
    }
    
    public final L1Apprentice getApprentice() {
        return this._apprentice;
    }
    
    public final void setApprentice(final L1Apprentice apprentice) {
        this._apprentice = apprentice;
    }
    
    public final void checkEffect() {
        int checkType = 0;
        if (this.getApprentice() != null) {
            final L1PcInstance master = World.get().getPlayer(this.getApprentice().getMaster().getName());
            if (master != null) {
                final L1Party party = this.getParty();
                if (party != null) {
                    checkType = party.checkMentor(this.getApprentice());
                }
                else {
                    checkType = 1;
                }
            }
        }
        if (this._tempType != checkType) {
            if (checkType > 0) {
                this.sendEffectBuff(this._tempType, -1);
            }
            if (checkType > 0) {
                this.sendEffectBuff(checkType, 1);
            }
            this.sendPackets(new S_SPMR(this));
            this.sendPackets(new S_OwnCharStatus(this));
            this.sendPackets(new S_PacketBox(132, this.getEr()));
            if (checkType <= 0) {
                this.sendPackets(new S_PacketBox(147, 0, Math.max(this._tempType - 1, 0)));
            }
            else {
                this.sendPackets(new S_PacketBox(147, (int)((checkType != 0) ? 1 : 0), Math.max(checkType - 1, 0)));
            }
            this._tempType = checkType;
        }
    }
    
    /**
	 * ER加成
	 * 
	 * @param i
	 */
    public void addOriginalEr(final int i) {
        this._originalEr += i;
    }
    
    private final void sendEffectBuff(final int buffType, final int negative) {
        switch (buffType) {
            case 1: {
                this.addAc(-1 * negative);
                break;
            }
            case 2: {
                this.addAc(-1 * negative);
                this.addMr(1 * negative);
                break;
            }
            case 3: {
                this.addAc(-1 * negative);
                this.addMr(1 * negative);
                this.addWater(2 * negative);
                this.addWind(2 * negative);
                this.addFire(2 * negative);
                this.addEarth(2 * negative);
                break;
            }
            case 4: {
                this.addAc(-1 * negative);
                this.addMr(1 * negative);
                this.addWater(2 * negative);
                this.addWind(2 * negative);
                this.addFire(2 * negative);
                this.addEarth(2 * negative);
                this.addOriginalEr(1 * negative);
                break;
            }
            case 5: {
                this.addAc(-3 * negative);
                break;
            }
            case 6: {
                this.addAc(-3 * negative);
                this.addMr(3 * negative);
                break;
            }
            case 7: {
                this.addAc(-3 * negative);
                this.addMr(3 * negative);
                this.addWater(6 * negative);
                this.addWind(6 * negative);
                this.addFire(6 * negative);
                this.addEarth(6 * negative);
                break;
            }
            case 8: {
                this.addAc(-3 * negative);
                this.addMr(3 * negative);
                this.addWater(6 * negative);
                this.addWind(6 * negative);
                this.addFire(6 * negative);
                this.addEarth(6 * negative);
                this.addOriginalEr(3 * negative);
                break;
            }
        }
    }
    
    public final Timestamp getPunishTime() {
        return this._punishTime;
    }
    
    public final void setPunishTime(final Timestamp timestamp) {
        this._punishTime = timestamp;
    }
    
    @Override
    public final String getViewName() {
        final StringBuffer sbr = new StringBuffer();
        if (this.isProtector()) {
            sbr.append("**守護者**");
        }
        else {
            sbr.append(this.getName());
            String getPrestigeLv = "";
            if (prestigtable.START && this.getPrestige() > 0) {
                getPrestigeLv = RewardPrestigeTable.get().getTitle(this.getPrestigeLv());
                sbr.append(getPrestigeLv);
            }
            if (this._meteAbility != null) {
                sbr.append(this._meteAbility.getTitle());
            }
            if (this.getvipname() != null) {
                sbr.append(this.getvipname());
            }
        }
        return sbr.toString();
    }
    
    public int getMagicDmgModifier() {
        return this._magicDmgModifier;
    }
    
    public void addMagicDmgModifier(final int i) {
        this._magicDmgModifier += i;
    }
    
    public int getMagicDmgReduction() {
        return this._magicDmgReduction;
    }
    
    public void addMagicDmgReduction(final int i) {
        this._magicDmgReduction += i;
    }
    
    public void set_elitePlateMail_Fafurion(final int r, final int hpmin, final int hpmax) {
        this._elitePlateMail_Fafurion = r;
        this._fafurion_hpmin = hpmin;
        this._fafurion_hpmax = hpmax;
    }
    
    public void set_elitePlateMail_Lindvior(final int r, final int mpmin, final int mpmax) {
        this._elitePlateMail_Lindvior = r;
        this._lindvior_mpmin = mpmin;
        this._lindvior_mpmax = mpmax;
    }
    
    public void set_elitePlateMail_Valakas(final int r, final int dmgmin, final int dmgmax) {
        this._elitePlateMail_Valakas = r;
        this._valakas_dmgmin = dmgmin;
        this._valakas_dmgmax = dmgmax;
    }
    
    public void set_hades_cloak(final int r, final int dmgmin, final int dmgmax) {
        this._hades_cloak = r;
        this._hades_cloak_dmgmin = dmgmin;
        this._hades_cloak_dmgmax = dmgmax;
    }
    
    public void set_Hexagram_Magic_Rune(final int r, final int hpmin, final int hpmax, final int gfx) {
        this._Hexagram_Magic_Rune = r;
        this._hexagram_hpmin = hpmin;
        this._hexagram_hpmax = hpmax;
        this._hexagram_gfx = gfx;
    }
    
    public void set_DimiterBless(final int r, final int mpmin, final int mpmax, final int r2, final int time) {
        this._dimiter_mpr_rnd = r;
        this._dimiter_mpmin = mpmin;
        this._dimiter_mpmax = mpmax;
        this._dimiter_bless = r2;
        this._dimiter_time = time;
    }
    
    public int getExpPoint() {
        return this._expPoint;
    }
    
    public void setExpPoint(final int i) {
        this._expPoint = i;
    }
    
    public void setSummonId(final int SummonId) {
        this._SummonId = SummonId;
    }
    
    public int getSummonId() {
        return this._SummonId;
    }
    
    public void setLap(final int i) {
        this._lap = i;
    }
    
    public int getLap() {
        return this._lap;
    }
    
    public void setLapCheck(final int i) {
        this._lapCheck = i;
    }
    
    public int getLapCheck() {
        return this._lapCheck;
    }
    
    public int getLapScore() {
        return this._lap * 29 + this._lapCheck;
    }
    
    public boolean isInOrderList() {
        return this._order_list;
    }
    
    public void setInOrderList(final boolean bool) {
        this._order_list = bool;
    }
    
    public void set_bighot(final int i) {
        this._isBigHot = i;
    }
    
    public int get_bighot() {
        return this._isBigHot;
    }
    
    public void setBighot1(final String bighot1) {
        this._bighot1 = bighot1;
    }
    
    public String getBighot1() {
        return this._bighot1;
    }
    
    public void setBighot2(final String bighot2) {
        this._bighot2 = bighot2;
    }
    
    public String getBighot2() {
        return this._bighot2;
    }
    
    public void setBighot3(final String bighot3) {
        this._bighot3 = bighot3;
    }
    
    public String getBighot3() {
        return this._bighot3;
    }
    
    public void setBighot4(final String bighot4) {
        this._bighot4 = bighot4;
    }
    
    public String getBighot4() {
        return this._bighot4;
    }
    
    public void setBighot5(final String bighot5) {
        this._bighot5 = bighot5;
    }
    
    public String getBighot5() {
        return this._bighot5;
    }
    
    public void setBighot6(final String bighot6) {
        this._bighot6 = bighot6;
    }
    
    public String getBighot6() {
        return this._bighot6;
    }
    
    public boolean isWindShackle() {
        return this.hasSkillEffect(167);
    }
    
    private void delSkill(final int count) {
        for (int i = 0; i < count; ++i) {
            final int index = L1PcInstance._random.nextInt(this._skillList.size());
            final Integer skillid = this._skillList.get(index);
            final L1Skills skill = SkillsTable.get().getTemplate(skillid);
            if (this._skillList.remove(skillid)) {
                this.sendPackets(new S_DelSkill(this, skillid));
                CharSkillReading.get().spellLost(this.getId(), skillid);
                ConfigDropSkill.msg(this.getName(), skill.getName());
            }
        }
    }
    
    private void checkItemSteal(final L1PcInstance fightPc) {
        if (Taketreasure.getInstance().getList().isEmpty()) {
            return;
        }
        for (final L1Taketreasure itemSteal : Taketreasure.getInstance().getList()) {
            final L1ItemInstance steal_item = this.getInventory().findItemId(itemSteal.getItemId());
            if (steal_item == null) {
                continue;
            }
            if (itemSteal.getLevel() > 0 && this.getLevel() < itemSteal.getLevel()) {
                continue;
            }
            if (L1PcInstance._random.nextInt(100) >= itemSteal.getStealChance()) {
                continue;
            }
            if (itemSteal.getisEquipped() == 1 && !steal_item.isEquipped()) {
                continue;
            }
            if (itemSteal.getAntiStealItemId() <= 0 || !this.getInventory().consumeItem(itemSteal.getAntiStealItemId(), 1L)) {
                long steal_count;
                if (steal_item.isStackable()) {
                    steal_count = L1PcInstance._random.nextInt(Math.max(itemSteal.getMaxStealCount() - itemSteal.getMinStealCount(), 0) + 1) + itemSteal.getMinStealCount();
                    steal_count = ((steal_item.getCount() >= steal_count) ? steal_count : steal_item.getCount());
                }
                else {
                    steal_count = 1L;
                    this.getInventory().setEquipped(steal_item, false);
                }
                this.sendPackets(new S_ServerMessage(638, steal_item.getNumberedViewName(steal_count)));
                if (itemSteal.isDropOnFloor() == 1) {
                    steal_item.set_showId(this.get_showId());
                    this.getInventory().tradeItem(steal_item, steal_count, World.get().getInventory(this.getX(), this.getY(), this.getMapId()));
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(itemSteal.getdropmsg1(), this.getViewName(), steal_item.getNumberedViewName(steal_count))));
                    if (itemSteal.getitemid() != 0) {
                        fightPc.getInventory().storeItem(itemSteal.getitemid(), itemSteal.getitemcount());
                        final L1Item temp = ItemTable.get().getTemplate(itemSteal.getitemid());
                        fightPc.sendPackets(new S_ServerMessage(403, String.valueOf(String.valueOf(String.valueOf(String.valueOf(temp.getName())))) + "(" + itemSteal.getitemcount() + "}"));
                    }
                    if (itemSteal.getdeaditemid() != 0) {
                        this.getInventory().tradeItem1(itemSteal.getdeaditemid(), itemSteal.getdeaditemcount(), World.get().getInventory(this.getX(), this.getY(), this.getMapId()));
                    }
                    if (itemSteal.getditemid() != 0) {
                        this.getInventory().storeItem(itemSteal.getditemid(), itemSteal.getdcount());
                    }
                }
                else if (itemSteal.isDropOnFloor() == 0) {
                    this.getInventory().tradeItem(steal_item, steal_count, fightPc.getInventory());
                    fightPc.sendPackets(new S_ServerMessage(403, steal_item.getNumberedViewName(steal_count)));
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(itemSteal.getdropmsg(), this.getViewName(), fightPc.getViewName(), steal_item.getNumberedViewName(steal_count))));
                    if (itemSteal.getitemid() != 0) {
                        fightPc.getInventory().storeItem(itemSteal.getitemid(), itemSteal.getitemcount());
                        final L1Item temp = ItemTable.get().getTemplate(itemSteal.getitemid());
                        fightPc.sendPackets(new S_ServerMessage(403, String.valueOf(String.valueOf(String.valueOf(String.valueOf(temp.getName())))) + "(" + itemSteal.getitemcount() + "}"));
                    }
                    if (itemSteal.getdeaditemid() != 0) {
                        this.getInventory().tradeItem1(itemSteal.getdeaditemid(), itemSteal.getdeaditemcount(), World.get().getInventory(this.getX(), this.getY(), this.getMapId()));
                    }
                    if (itemSteal.getditemid() != 0) {
                        this.getInventory().storeItem(itemSteal.getditemid(), itemSteal.getdcount());
                    }
                }
                else if (itemSteal.isDropOnFloor() == 2) {
                    this.getInventory().deleteItem(steal_item);
                    World.get().broadcastPacketToAll(new S_SystemMessage(String.format(itemSteal.getdropmsg2(), this.getViewName(), steal_item.getNumberedViewName(steal_count))));
                    if (itemSteal.getdeaditemid() != 0) {
                        this.getInventory().tradeItem1(itemSteal.getdeaditemid(), itemSteal.getdeaditemcount(), World.get().getInventory(this.getX(), this.getY(), this.getMapId()));
                    }
                    if (itemSteal.getditemid() != 0) {
                        this.getInventory().storeItem(itemSteal.getditemid(), itemSteal.getdcount());
                    }
                }
                if (itemSteal.getitemid() != 0) {
                    fightPc.getInventory().storeItem(itemSteal.getitemid(), itemSteal.getitemcount());
                    final L1Item temp = ItemTable.get().getTemplate(itemSteal.getitemid());
                    fightPc.sendPackets(new S_ServerMessage(403, String.valueOf(String.valueOf(String.valueOf(String.valueOf(temp.getName())))) + "(" + itemSteal.getitemcount() + "}"));
                }
                return;
            }
            this.sendPackets(new S_SystemMessage("由於身上有[" + ItemTable.get().getTemplate(itemSteal.getAntiStealItemId()).getNameId() + "] 免於被對方奪取: " + steal_item.getLogName()));
        }
    }
    
    private void checkItemSteal12(final L1PcInstance fightPc, final int itemid) {
        this.getInventory().tradeItem(itemid, 1L, World.get().getInventory(this.getX(), this.getY(), this.getMapId()));
    }
    
    private void checkItemSteal1() {
        if (Taketreasure1.getInstance().getList().isEmpty()) {
            return;
        }
        for (final L1Taketreasure1 itemSteal1 : Taketreasure1.getInstance().getList()) {
            final L1ItemInstance steal_item = this.getInventory().findItemId(itemSteal1.getItemId());
            if (steal_item == null) {
                continue;
            }
            this.getInventory().setEquipped(steal_item, false);
            this.sendPackets(new S_ServerMessage(638, steal_item.getLogName()));
            this.getInventory().deleteItem(steal_item);
        }
    }
    
    public boolean isATeam() {
        return this._isATeam;
    }
    
    public void setATeam(final boolean bool) {
        this._isATeam = bool;
    }
    
    public boolean isBTeam() {
        return this._isBTeam;
    }
    
    public void setBTeam(final boolean bool) {
        this._isBTeam = bool;
    }
    
    public Timestamp getRejoinClanTime() {
        return this._rejoinClanTime;
    }
    
    public void setRejoinClanTime(final Timestamp time) {
        this._rejoinClanTime = time;
    }
    
    public Timestamp getCreateTime() {
        return this._CreateTime;
    }
    
    public int getSimpleCreateTime() {
        if (this._CreateTime != null) {
            final SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMdd");
            final int BornTime = Integer.parseInt(SimpleDate.format(this._CreateTime.getTime()));
            return BornTime;
        }
        return 0;
    }
    
    public void setCreateTime(final Timestamp time) {
        this._CreateTime = time;
    }
    
    public void setCreateTime() {
        this._CreateTime = new Timestamp(System.currentTimeMillis());
    }
    
    public void setPartyType(final int type) {
        this._partyType = type;
    }
    
    public int getPartyType() {
        return this._partyType;
    }
    
    public int getUbScore() {
        return this._ubscore;
    }
    
    public void setUbScore(final int i) {
        this._ubscore = i;
    }
    
    public int getInputError() {
        return this._inputerror;
    }
    
    public void setInputError(final int i) {
        this._inputerror = i;
    }
    
    public int getSpeedError() {
        return this._speederror;
    }
    
    public void setSpeedError(final int i) {
        this._speederror = i;
    }
    
    public int getBanError() {
        return this._banerror;
    }
    
    public void setBanError(final int i) {
        this._banerror = i;
    }
    
    public int getInputBanError() {
        return this._inputbanerror;
    }
    
    public void setInputBanError(final int i) {
        this._inputbanerror = i;
    }
    
//    public AcceleratorChecker getAcceleratorChecker() {
//        return this._acceleratorChecker;
//    }
    
	// -- 加速器■知機能 --
	private final AcceleratorChecker _acceleratorChecker = new AcceleratorChecker(this);

	public AcceleratorChecker getAcceleratorChecker() {
		
		return _acceleratorChecker;
	}
    
    public void setSlot(final int i) {
        this._Slot = i;
    }
    
    public int getSlot() {
        return this._Slot;
    }
    
    public void setItemPoly(final boolean itempoly) {
        this._itempoly = itempoly;
    }
    
    public boolean isItemPoly() {
        return this._itempoly;
    }
    
    public void setItemPoly1(final boolean itempoly1) {
        this._itempoly1 = itempoly1;
    }
    
    public boolean isItemPoly1() {
        return this._itempoly1;
    }
    
    public void setPolyScroll(final L1ItemInstance item) {
        this._polyscroll = item;
    }
    
    public L1ItemInstance getPolyScroll() {
        return this._polyscroll;
    }
    
    public void setPolyScrol2(final L1ItemInstance item) {
        this._polyscrol2 = item;
    }
    
    public L1ItemInstance getPolyScrol2() {
        return this._polyscrol2;
    }
    
    public void setitembox(final L1ItemInstance item) {
        this._itembox = item;
    }
    
    public L1ItemInstance getitembox() {
        return this._itembox;
    }
    
    public void set_tomb(final L1EffectInstance tomb) {
        this._tomb = tomb;
    }
    
    public L1EffectInstance get_tomb() {
        return this._tomb;
    }
    
    public void setMagicCritical(final boolean flag) {
        this._isMagicCritical = flag;
    }
    
    public boolean isMagicCritical() {
        return this._isMagicCritical;
    }
    
    public void setPhantomTeleport(final boolean flag) {
        this._isPhantomTeleport = flag;
    }
    
    public boolean isPhantomTeleport() {
        return this._isPhantomTeleport;
    }
    
    public int getRocksPrisonTime() {
        return this._rocksPrisonTime;
    }
    
    public void setRocksPrisonTime(final int time) {
        this._rocksPrisonTime = time;
    }
    
    public int getLastabardTime() {
        return this._lastabardTime;
    }
    
    public void setLastabardTime(final int time) {
        this._lastabardTime = time;
    }
    
    public int getIvoryTowerTime() {
        return this._ivorytowerTime;
    }
    
    public void setIvoryTowerTime(final int time) {
        this._ivorytowerTime = time;
    }
    
    public int getDragonValleyTime() {
        return this._dragonvalleyTime;
    }
    
    public void setDragonValleyTime(final int time) {
        this._dragonvalleyTime = time;
    }
    
    public void resetAllMapTime() {
        this._rocksPrisonTime = 0;
        this._lastabardTime = 0;
        this._ivorytowerTime = 0;
        this._dragonvalleyTime = 0;
    }
    
    public int getMapUseTime(final int mapid) {
        int result = 0;
        switch (mapid) {
            case 53:
            case 54:
            case 55:
            case 56:
            case 807:
            case 808:
            case 809:
            case 810:
            case 811:
            case 812:
            case 813: {
                result = this._rocksPrisonTime;
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82: {
                result = this._ivorytowerTime;
                break;
            }
            case 452:
            case 453:
            case 461:
            case 462:
            case 471:
            case 475:
            case 479:
            case 492:
            case 495: {
                result = this._lastabardTime;
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37: {
                result = this._dragonvalleyTime;
                break;
            }
        }
        return result;
    }
    
    public void setMapUseTime(final int mapid, final int time) {
        switch (mapid) {
            case 53:
            case 54:
            case 55:
            case 56:
            case 807:
            case 808:
            case 809:
            case 810:
            case 811:
            case 812:
            case 813: {
                this.setRocksPrisonTime(time);
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82: {
                this.setIvoryTowerTime(time);
                break;
            }
            case 452:
            case 453:
            case 461:
            case 462:
            case 471:
            case 475:
            case 479:
            case 492:
            case 495: {
                this.setLastabardTime(time);
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37: {
                this.setDragonValleyTime(time);
                break;
            }
        }
    }
    
    public boolean isInTimeMap() {
        final int map = this.getMapId();
        final int maxMapUsetime = MapsTable.get().getMapTime(map);
        return maxMapUsetime > 0;
    }
    
    public void setMapTime(final ConcurrentHashMap<Integer, Integer> map) {
        this.mapTime = map;
    }
    
    public void updateMapTime(final int time) {
        int mapid = this.getMapId();
        if (mapid >= 4001 && mapid <= 4050) {
            mapid = 4001;
        }
        if (this.mapTime.get(mapid) == null) {
            return;
        }
        final int temp = this.mapTime.get(mapid);
        this.mapTime.put((int)this.getMapId(), temp + time);
    }
    
    public int getMapTime(final int mapid) {
        if (this.mapTime.get(mapid) == null) {
            L1PcInstance._log.error((Object)("記時地圖ID:" + mapid + "不存在"));
            return -1;
        }
        return this.mapTime.get(mapid);
    }
    
    public ConcurrentHashMap<Integer, Integer> getMapTime() {
        return this.mapTime;
    }
    
    public boolean isTimeMap() {
        return this.isTimeMap;
    }
    
    public void setTimeMap(final boolean isTimeMap) {
        this.isTimeMap = isTimeMap;
    }
    
    public void stopTimeMap() {
        if (this.isTimeMap) {
            this.isTimeMap = false;
        }
    }
    
    public void startTimeMap() {
        if (!this.isTimeMap) {
            this.isTimeMap = true;
        }
    }
    
    public int getClanMemberId() {
        return this._clanMemberId;
    }
    
    public void setClanMemberId(final int i) {
        this._clanMemberId = i;
    }
    
    public String getClanMemberNotes() {
        return this._clanMemberNotes;
    }
    
    public void setClanMemberNotes(final String s) {
        this._clanMemberNotes = s;
    }
    
    public void addStunLevel(final int add) {
        this._stunlevel += add;
    }
    
    public int getStunLevel() {
        return this._stunlevel;
    }
    
    public int getother_ReductionDmg() {
        return this._other_ReductionDmg;
    }
    
    public void setother_ReductionDmg(final int i) {
        this._other_ReductionDmg = i;
    }
    
    public void addother_ReductionDmg(final int i) {
        this._other_ReductionDmg += i;
    }
    
    public int getClan_ReductionDmg() {
        return this._Clan_ReductionDmg;
    }
    
    public void setClan_ReductionDmg(final int i) {
        this._Clan_ReductionDmg = i;
    }
    
    public void addClan_ReductionDmg(final int i) {
        this._Clan_ReductionDmg += i;
    }
    
    public void add_Clanmagic_reduction_dmg(final int add) {
        this._Clanmagic_reduction_dmg += add;
    }
    
    public int get_Clanmagic_reduction_dmg() {
        return this._Clanmagic_reduction_dmg;
    }
    
    public void addExpByArmor(final double i) {
        this._addExpByArmor += i;
    }
    
    public double getExpByArmor() {
        return this._addExpByArmor;
    }
    
    public int getPcContribution() {
        return this._PcContribution;
    }
    
    public void setPcContribution(final int i) {
        this._PcContribution = i;
    }
    
    
    private int _listpage = 0;

	public int getPage() {
		return _listpage;
	}

	public void addPage(final int page) {
		_listpage += page;
	}

	public void setPage(final int page) {
		_listpage = page;
	}
	
    public int getClanContribution() {
        return this._clanContribution;
    }
    
    public void setClanContribution(final int i) {
        this._clanContribution = i;
    }
    
    public int getclanadena() {
        return this._clanadena;
    }
    
    public void setclanadena(final int i) {
        this._clanadena = i;
    }
    
    public String getClanNameContribution() {
        return this.clanNameContribution;
    }
    
    public void setClanNameContribution(final String s) {
        this.clanNameContribution = s;
    }
    
    public void setcheckgm(final boolean checkgm) {
        this._checkgm = checkgm;
    }
    
    public boolean getcheckgm() {
        return this._checkgm;
    }
    
    public void setcheck_lv(final boolean b) {
        this.check_lv = b;
    }
    
    public boolean getcheck_lv() {
        return this.check_lv;
    }
    
    public int getlogpcpower_SkillCount() {
        return this._logpcpower_SkillCount;
    }
    
    public void setlogpcpower_SkillCount(final int i) {
        this._logpcpower_SkillCount = i;
    }
    
    public int getlogpcpower_SkillFor1() {
        return this._logpcpower_SkillFor1;
    }
    
    public void setlogpcpower_SkillFor1(final int i) {
        this._logpcpower_SkillFor1 = i;
    }
    
    public int getlogpcpower_SkillFor2() {
        return this._logpcpower_SkillFor2;
    }
    
    public void setlogpcpower_SkillFor2(final int i) {
        this._logpcpower_SkillFor2 = i;
    }
    
    public int getlogpcpower_SkillFor3() {
        return this._logpcpower_SkillFor3;
    }
    
    public void setlogpcpower_SkillFor3(final int i) {
        this._logpcpower_SkillFor3 = i;
    }
    
    public int getlogpcpower_SkillFor4() {
        return this._logpcpower_SkillFor4;
    }
    
    public void setlogpcpower_SkillFor4(final int i) {
        this._logpcpower_SkillFor4 = i;
    }
    
    public int getlogpcpower_SkillFor5() {
        return this._logpcpower_SkillFor5;
    }
    
    public void setlogpcpower_SkillFor5(final int i) {
        this._logpcpower_SkillFor5 = i;
    }
    
    public int getEsotericSkill() {
        return this._EsotericSkill;
    }
    
    public void setEsotericSkill(final int i) {
        this._EsotericSkill = i;
    }
    
    public int getEsotericCount() {
        return this._EsotericCount;
    }
    
    public void setEsotericCount(final int i) {
        this._EsotericCount = i;
    }
    
    public boolean isEsoteric() {
        return this._isEsoteric;
    }
    
    public void setEsoteric(final boolean flag) {
        this._isEsoteric = flag;
    }
    
    @Override
    public void setTripleArrow(final boolean TripleArrow) {
        this._TripleArrow = TripleArrow;
    }
    
    @Override
    public boolean isTripleArrow() {
        return this._TripleArrow;
    }
    
    public void setchecklogpc(final boolean checklogpc) {
        this._checklogpc = checklogpc;
    }
    
    public boolean getchecklogpc() {
        return this._checklogpc;
    }
    
    public int gesavepclog() {
        return this._savepclog;
    }
    
    public void setsavepclog(final int i) {
        this._savepclog = i;
    }
    
    public int getReductionDmg() {
        return this._ReductionDmg;
    }
    
    public void setReductionDmg(final int i) {
        this._ReductionDmg = i;
    }
    
    public int getpcdmg() {
        return this._pcdmg;
    }
    
    public void setpcdmg(final int i) {
        this._pcdmg = i;
    }
    
    public int getpaycount() {
        return this._paycount;
    }
    
    public void setpaycount(final int i) {
        this._paycount = i;
    }
    
    public int getArmorCount1() {
        return this._ArmorCount1;
    }
    
    public void setArmorCount1(final int i) {
        this._ArmorCount1 = i;
    }
    
    public int getlogintime() {
        return this._logintime;
    }
    
    public void setlogintime(final int i) {
        this._logintime = i;
    }
    
    public int getlogintime1() {
        return this._logintime1;
    }
    
    public void setlogintime1(final int i) {
        this._logintime1 = i;
    }
    
    public double getPartyExp() {
        return this._PartyExp;
    }
    
    public void setPartyExp(final double d) {
        this._PartyExp = d;
    }
    
    public void setATK_ai(final boolean b) {
        this.ATK_ai = b;
    }
    
    public boolean getATK_ai() {
        return this.ATK_ai;
    }
    
    public final long getShopAdenaRecord() {
        return this._shopAdenaRecord;
    }
    
    public final void setShopAdenaRecord(final long i) {
        this._shopAdenaRecord = i;
    }
    
    public int getdolldamageReductionByArmor() {
        int dolldamageReduction = 0;
        if (this._dolldamageReductionByArmor > 10) {
            dolldamageReduction = 10 + L1PcInstance._random.nextInt(this._dolldamageReductionByArmor - 10) + 1;
        }
        else {
            dolldamageReduction = this._dolldamageReductionByArmor;
        }
        return dolldamageReduction;
    }
    
    public void adddollDamageReductionByArmor(final int i) {
        this._dolldamageReductionByArmor += i;
    }
    
    public void addweaponMD(final int weaponMD) {
        this._weaponMD += weaponMD;
    }
    
    public int getweaponMD() {
        return this._weaponMD;
    }
    
    public void addweaponMDC(final int weaponMDC) {
        this._weaponMDC += weaponMDC;
    }
    
    public int getweaponMDC() {
        return this._weaponMDC;
    }
    
    public void add_reduction_dmg(final int add) {
        this._reduction_dmg += add;
    }
    
    public int get_reduction_dmg() {
        return this._reduction_dmg;
    }
    
    public void addSkin(final L1SkinInstance skin, final int gfxid) {
        this._skins.put(gfxid, skin);
    }
    
    public void addGF(final int i) {
        if (i > 0) {
            this._GF = DoubleUtil.sum(this._GF, i / 100.0);
        }
        else {
            this._GF = DoubleUtil.sub(this._GF, i * -1 / 100.0);
        }
    }
    
    public double getGF() {
        if (this._GF < 0.0) {
            return 0.0;
        }
        return this._GF;
    }
    
    public void removeSkin(final int gfxid) {
        this._skins.remove(gfxid);
    }
    
    public L1SkinInstance getSkin(final int gfxid) {
        return this._skins.get(gfxid);
    }
    
    public Map<Integer, L1SkinInstance> getSkins() {
        return this._skins;
    }
    
    public void set_isTeleportToOk(final boolean b) {
        this._isTeleportToOk = b;
    }
    
    public boolean get_isTeleportToOk() {
        return this._isTeleportToOk;
    }
    
    public void set_MOVE_STOP(final boolean b) {
        this._MOVE_STOP = b;
    }
    
    public boolean get_MOVE_STOP() {
        return this._MOVE_STOP;
    }
    
    public void sendPacketsBossWeaponAll(final ServerBasePacket packet) {
        if (this._netConnection == null) {
            return;
        }
        try {
            this._netConnection.sendPacket(packet);
            if (!this.isGmInvis() && !this.isInvisble()) {
                this.broadcastPacketBossWeaponAll(packet);
            }
        }
        catch (Exception e) {
            this.logout();
            this.close();
        }
    }
    
    public String getIp() {
        return this._netConnection.getIp().toString();
    }
    
    public int getAmount() {
        return this._amount;
    }
    
    public void setAmount(final int i) {
        this._amount = i;
    }
    
    public synchronized void setExp_Direct(final long i) {
        this.setExp(i);
        this.onChangeExp();
    }
    
    public L1Inventory getTradeWindowInventory() {
        return this._tradewindow;
    }
    
    public void set_consume_point(final long count) {
        this._consume_point = count;
    }
    
    public long get_consume_point() {
        return this._consume_point;
    }
    
    public final void setMapsList(final HashMap<Integer, Integer> list) {
        this._mapsList = list;
    }
    
    public final int getMapsTime(final int key) {
        if (this._mapsList == null || !this._mapsList.containsKey(key)) {
            return 0;
        }
        return this._mapsList.get(key);
    }
    
    public void putMapsTime(final int key, final int value) {
        if (this._mapsList == null) {
            this._mapsList = CharMapTimeReading.get().addTime(this.getId(), key, value);
        }
        this._mapsList.put(key, value);
    }
    
    public int getTempStr() {
        return this._tempStr;
    }
    
    public void setTempStr(final int i) {
        this._tempStr = i;
    }
    
    public int getTempDex() {
        return this._tempDex;
    }
    
    public void setTempDex(final int i) {
        this._tempDex = i;
    }
    
    public int getTempCon() {
        return this._tempCon;
    }
    
    public void setTempCon(final int i) {
        this._tempCon = i;
    }
    
    public int getTempWis() {
        return this._tempWis;
    }
    
    public void setTempWis(final int i) {
        this._tempWis = i;
    }
    
    public int getTempCha() {
        return this._tempCha;
    }
    
    public void setTempCha(final int i) {
        this._tempCha = i;
    }
    
    public int getTempInt() {
        return this._tempInt;
    }
    
    public void setTempInt(final int i) {
        this._tempInt = i;
    }
    
    public int getTempInitPoint() {
        return this._tempInitPoint;
    }
    
    public void setTempInitPoint(final int i) {
        this._tempInitPoint = i;
    }
    
    public int getTempElixirstats() {
        return this._tempElixirstats;
    }
    
    public void setTempElixirstats(final int i) {
        this._tempElixirstats = i;
    }
    
    public int getweapondmg() {
        return this.weapondmg;
    }
    
    public void setweapondmg(final int i) {
        this.weapondmg = i;
    }
    
    public void addDmgdouble(final double i) {
        this._Dmgdouble += i;
    }
    
    public double getDmgdouble() {
        return this._Dmgdouble;
    }
    
    public int getPVPdmg() {
        return this._PVPdmg;
    }
    
    public void setPVPdmg(final int i) {
        this._PVPdmg = i;
    }
    
    public void addPVPdmg(final int i) {
        this._PVPdmg += i;
    }
    
    public int getPVPdmgReduction() {
        return this._PVPdmgReduction;
    }
    
    public void setPVPdmgReduction(final int i) {
        this._PVPdmgReduction = i;
    }
    
    public void addPVPdmgReduction(final int i) {
        this._PVPdmgReduction += i;
    }
    
    public int getattr_potion_heal() {
        return this._attr_potion_heal;
    }
    
    public void setattr_potion_heal(final int i) {
        this._attr_potion_heal = i;
    }
    
    public void addattr_potion_heal(final int i) {
        this._attr_potion_heal += i;
    }
    
    public int getpenetrate() {
        return this._penetrate;
    }
    
    public void setpenetrate(final int i) {
        this._penetrate = i;
    }
    
    public int getattr_物理格檔() {
        return this._attr_物理格檔;
    }
    
    public void setattr_物理格檔(final int i) {
        this._attr_物理格檔 = i;
    }
    
    public void addattr_物理格檔(final int i) {
        this._attr_物理格檔 += i;
    }
    
    public int getattr_魔法格檔() {
        return this._attr_魔法格檔;
    }
    
    public void setattr_魔法格檔(final int i) {
        this._attr_魔法格檔 = i;
    }
    
    public void addattr_魔法格檔(final int i) {
        this._attr_魔法格檔 += i;
    }
    
    public int getNoweaponRedmg() {
        return this._NoweaponRedmg;
    }
    
    public void setNoweaponRedmg(final int i) {
        this._NoweaponRedmg = i;
    }
    
    public int getaddStunLevel() {
        return this._addStunLevel;
    }
    
    public void setaddStunLevel(final int i) {
        this._addStunLevel = i;
    }
    
    public void addaddStunLevel(final int add) {
        this._addStunLevel += add;
    }
    
    public int getloginpoly() {
        return this._loginpoly;
    }
    
    public void setloginpoly(final int i) {
        this._loginpoly = i;
    }
    
    public int getBackHeading() {
        return this.backHeading;
    }
    
    public void setBackHeading(final int backHeading) {
        this.backHeading = backHeading;
    }
    
    public int getBackX() {
        return this.backX;
    }
    
    public void setBackX(final int backX) {
        this.backX = backX;
    }
    
    public int getBackY() {
        return this.backY;
    }
    
    public void setBackY(final int backY) {
        this.backY = backY;
    }
    
    public void set_Imperius_Tshirt(final int r, final int drainingHP_min, final int drainingHP_max) {
        this._Imperius_Tshirt_rnd = r;
        this._drainingHP_min = drainingHP_min;
        this._drainingHP_max = drainingHP_max;
    }
    
    public int get_Imperius_Tshirt_rnd() {
        return this._Imperius_Tshirt_rnd;
    }
    
    public int get_Tshirt_drainingHP_min() {
        return this._drainingHP_min;
    }
    
    public int get_Tshirt_drainingHP_max() {
        return this._drainingHP_max;
    }
    
    public void set_MoonAmulet(final int r, final int dmgmin, final int dmgmax, final int gfxid) {
        this._MoonAmulet_rnd = r;
        this._MoonAmulet_dmg_min = dmgmin;
        this._MoonAmulet_dmg_max = dmgmax;
        this._MoonAmulet_gfxid = gfxid;
    }
    
    public int get_MoonAmulet_rnd() {
        return this._MoonAmulet_rnd;
    }
    
    public int get_MoonAmulet_dmg_min() {
        return this._MoonAmulet_dmg_min;
    }
    
    public int get_MoonAmulet_dmg_max() {
        return this._MoonAmulet_dmg_max;
    }
    
    public int get_MoonAmulet_gfxid() {
        return this._MoonAmulet_gfxid;
    }
    
    public void set_AttrAmulet(final int r, final int dmg, final int gfxid) {
        this._AttrAmulet_rnd = r;
        this._AttrAmulet_dmg = dmg;
        this._AttrAmulet_gfxid = gfxid;
    }
    
    public int get_AttrAmulet_rnd() {
        return this._AttrAmulet_rnd;
    }
    
    public int get_AttrAmulet_dmg() {
        return this._AttrAmulet_dmg;
    }
    
    public int get_AttrAmulet_gfxid() {
        return this._AttrAmulet_gfxid;
    }
    
    public void setRange(final int i) {
        this._range = i;
    }
    
    public int getRange() {
        return this._range;
    }
    
    public PcAttackThread getAttackThread() {
        return this.attackThread;
    }
    
    public void setAttackThread(final PcAttackThread attackThread) {
        this.attackThread = attackThread;
    }
   
    
    public int getday() {
        return this._day;
    }
    
    public void setday(final int i) {
        this._day = i;
    }
    
    public void addPrestige(final int i) {
        this._prestige += i;
        this._prestige = Math.max(this._prestige, 0);
        if (this._prestigeLv != RewardPrestigeTable.get().getLv(this._prestige)) {
            if (this._prestigeLv != 0) {
                RewardPrestigeTable.get().removePrestige(this);
            }
            this._prestigeLv = RewardPrestigeTable.get().getLv(this._prestige);
            if (this._prestigeLv != 0) {
                RewardPrestigeTable.get().addPrestige(this);
            }
            L1Teleport.teleport(this, this.getX(), this.getY(), this.getMapId(), this.getHeading(), false);
        }
    }
    
    public void setPrestige(final int i) {
        this._prestige = i;
        if (RewardPrestigeTable.START && this._prestige == 0) {
            this._prestigeLv = RewardPrestigeTable.get().getLv(this._prestige);
            this.sendPackets(new S_ServerMessage("\\fU你身上帶有,陣營積分受到守護!"));
        }
        else if (RewardPrestigeTable.START && this._prestige != 0) {
            this._prestigeLv = RewardPrestigeTable.get().getLv(this._prestige);
        }
    }
    
    public int getPrestige() {
        return this._prestige;
    }
    
    public int getPrestigeLv() {
        return this._prestigeLv;
    }
    
    public long getoldexp() {
        return this._oldexp;
    }
    
    public void setoldexp(final long oldexp) {
        this._oldexp = oldexp;
    }
    
    public boolean isItemName() {
        return this._isItemName;
    }
    
    public void setItemName(final boolean flag) {
        this._isItemName = flag;
    }
    
    public boolean isItemopen() {
        return this._isItemopen;
    }
    
    public void setItemopen(final boolean flag) {
        this._isItemopen = flag;
    }
    
    public boolean isfollow() {
        return this._isfollow;
    }
    
    public void setfollow(final boolean flag) {
        this._isfollow = flag;
    }
    
    public boolean isfollowcheck() {
        return this._isfollowcheck;
    }
    
    public void setfollowcheck(final boolean flag) {
        this._isfollowcheck = flag;
    }
    
    @Override
    public int get_poisonStatus2() {
        return this._poisonStatus2;
    }
    
    @Override
    public void set_poisonStatus2(final int i) {
        this._poisonStatus2 = i;
    }
    
    @Override
    public int get_poisonStatus7() {
        return this._poisonStatus7;
    }
    
    @Override
    public void set_poisonStatus7(final int i) {
        this._poisonStatus7 = i;
    }
    
    public final boolean _isCraftsmanHeirloom() {
        return this._isCraftsmanHeirloom;
    }
    
    public final void setCraftsmanHeirloom(final boolean checkFlag) {
        if (this._isCraftsmanHeirloom != checkFlag) {
            this.giveCraftsmanHeirloom(checkFlag);
            this.sendPackets(new S_HPUpdate(this));
            if (this.isInParty()) {
                this.getParty().updateMiniHP(this);
            }
            this.sendPackets(new S_MPUpdate(this));
            this.sendPackets(new S_SPMR(this));
            L1PcUnlock.Pc_Unlock(this);
        }
    }
    
    public final void giveCraftsmanHeirloom(final boolean checkFlag) {
        this._isCraftsmanHeirloom = checkFlag;
        if (checkFlag) {
            this.addMaxHp(120);
            this.addMaxMp(100);
            this.addDmgup(50);
            this.addSp(15);
            this.addDamageReductionByArmor(30);
            this.sendPackets(new S_PacketBox(180, 1, 460));
        }
        else {
            this.addMaxHp(-120);
            this.addMaxMp(-100);
            this.addDmgup(-50);
            this.addSp(-15);
            this.addDamageReductionByArmor(-30);
            this.sendPackets(new S_PacketBox(180, 0, 460));
        }
    }
    
    public final boolean _isMarsSoul() {
        return this._isMarsSoul;
    }
    
    public final void setMarsSoul(final boolean checkFlag) {
        if (this._isMarsSoul != checkFlag) {
            this.giveMarsSoul(checkFlag);
            this.sendPackets(new S_HPUpdate(this));
            if (this.isInParty()) {
                this.getParty().updateMiniHP(this);
            }
            this.sendPackets(new S_MPUpdate(this));
            this.sendPackets(new S_SPMR(this));
            L1PcUnlock.Pc_Unlock(this);
        }
    }
    
    public final void giveMarsSoul(final boolean checkFlag) {
        this._isMarsSoul = checkFlag;
        if (checkFlag) {
            this.addMaxHp(120);
            this.addMaxMp(100);
            this.addDmgup(15);
            this.addBowDmgup(15);
            this.addSp(5);
            this.addDamageReductionByArmor(8);
            this.sendPackets(new S_PacketBox(180, 1, 457));
        }
        else {
            this.addMaxHp(-120);
            this.addMaxMp(-100);
            this.addDmgup(-15);
            this.addBowDmgup(-15);
            this.addSp(-5);
            this.addDamageReductionByArmor(-8);
            this.sendPackets(new S_PacketBox(180, 0, 457));
        }
    }
    
    public int getSuper() {
        return this._super;
    }
    
    public void setSuper(final int i) {
        this._super = i;
    }
    
    public void setguaji_poly(final int b) {
        this.guaji_poly = b;
    }
    
    public int getguaji_poly() {
        return this.guaji_poly;
    }
    
    public void startAI() {
        if (this.isDead()) {
            return;
        }
        if (this.isGhost()) {
            return;
        }
        if (this.getCurrentHp() <= 0) {
            return;
        }
        if (this.isPrivateShop()) {
            return;
        }
        if (this.isParalyzed()) {
            return;
        }
        if (this.get_followmaster() == null) {
            this._pcMove = new pcMove(this);
        }
        this.setAiRunning(true);
        this.setActived(true);
        final PcAI npcai = new PcAI(this);
        npcai.startAI();
    }
    
    protected void setAiRunning(final boolean aiRunning) {
        this._aiRunning = aiRunning;
    }
    
    protected boolean isAiRunning() {
        return this._aiRunning;
    }
    
    public void allTargetClear() {
        if (this._pcMove != null) {
            this._pcMove.clear();
        }
        this._hateList.clear();
        this._target = null;
        this.setFirstAttack(false);
    }
    
    public void checkTarget() {
        try {
            if (this._target == null) {
                this.allTargetClear();
                return;
            }
            if (this._target.getMapId() != this.getMapId()) {
                this.allTargetClear();
                return;
            }
            if (this._target.getCurrentHp() <= 0) {
                this.allTargetClear();
                return;
            }
            if (this._target.isDead()) {
                this.allTargetClear();
                return;
            }
            if (this.get_showId() != this._target.get_showId()) {
                this.allTargetClear();
                return;
            }
            if (!this._hateList.containsKey(this._target)) {
                this.allTargetClear();
                return;
            }
            final int distance = this.getLocation().getTileDistance(this._target.getLocation());
            if (distance > 5) {
                this.allTargetClear();
            }
        }
        catch (Exception ex) {}
    }
    
    public L1Character is_now_target() {
        return this._target;
    }
    
    public void attackTarget(final L1Character target) {
        if (this.getInventory().getWeight240() >= 197) {
            this.sendPackets(new S_ServerMessage(110));
            return;
        }
        if (this.hasSkillEffect(1011)) {
            return;
        }
        if (this.hasSkillEffect(1009)) {
            return;
        }
        if (this.hasSkillEffect(66)) {
            return;
        }
        if (this.hasSkillEffect(87)) {
            return;
        }
        if (this.hasSkillEffect(212)) {
            return;
        }
        if (this.hasSkillEffect(50)) {
            return;
        }
        if (this.hasSkillEffect(157)) {
            return;
        }
        if (this.hasSkillEffect(103)) {
            return;
        }
        if (this.hasSkillEffect(208)) {
            return;
        }
        if (target instanceof L1PcInstance) {
            final L1PcInstance player = (L1PcInstance)target;
            if (player.isTeleport()) {
                return;
            }
            if (!player.isPinkName()) {
                this.allTargetClear();
                return;
            }
        }
        else if (target instanceof L1PetInstance) {
            final L1PetInstance pet = (L1PetInstance)target;
            final L1Character cha = pet.getMaster();
            if (cha instanceof L1PcInstance) {
                final L1PcInstance player2 = (L1PcInstance)cha;
                if (player2.isTeleport()) {
                    return;
                }
            }
        }
        else if (target instanceof L1SummonInstance) {
            final L1SummonInstance summon = (L1SummonInstance)target;
            final L1Character cha = summon.getMaster();
            if (cha instanceof L1PcInstance) {
                final L1PcInstance player2 = (L1PcInstance)cha;
                if (player2.isTeleport()) {
                    return;
                }
            }
        }
        if (target instanceof L1NpcInstance) {
            final L1NpcInstance npc = (L1NpcInstance)target;
            if (npc.getHiddenStatus() != 0) {
                this.allTargetClear();
                return;
            }
        }
        if (target instanceof L1PetInstance) {
            this.allTargetClear();
            return;
        }
        if (target.getCurrentHp() > 0 && !target.isDead()) {
            target.onAction(this);
        }
        if (((this.get_followmaster() == null && !this.getfollowatkmagic()) || (this.get_followmaster() != null && this.getfollowatkmagic())) && !this.hasSkillEffect(1007) && !this.hasSkillEffect(64) && !this.hasSkillEffect(8912) && this.getAu_AutoSkill(2) > 0) {
            if (this.getAu_AutoSkill(4) > 0) {
                final double c = this.getCurrentMp();
                final double d = this.getMaxMp();
                if (c / d * 100.0 > this.getAu_AutoSkill(3) && this.getWeapon() != null && this.getAutoNow_AttackSkillDelay() <= 0 && this.getAu_AutoSkill(5) > 0) {
                    this.SkillDmg2(this, target);
                }
            }
            int autoSkill6Value = this.getAu_AutoSkill(6);
            int autoSkill7Value = this.getAu_AutoSkill(7);
            int autoSkill3Value = this.getAu_AutoSkill(3);

            if (autoSkill6Value > 0) {
                int count = 0;
                for (final L1Object objid : World.get().getVisibleObjects(this, 3)) {
                    if (objid instanceof L1MonsterInstance) {
                        count++;
                    }
                }
                if (count >= autoSkill7Value) {
                    double currentMp = this.getCurrentMp();
                    double maxMp = this.getMaxMp();
                    double mpPercentage = currentMp / maxMp * 100.0;
                    if (mpPercentage > autoSkill3Value) {
                        this.SkillDmg3(this, target);
                    }
                }
            }

        }
    }
    
    private void SkillDmg2(final L1PcInstance pc, final L1Character targets) {
        try {
            if (!pc.isSkillDelay()) {
                final L1ItemInstance weapon = pc.getWeapon();
                final int skillid = pc.getAu_AutoSkill(4);
                final L1Skills skill = SkillsTable.get().getTemplate(skillid);
                final L1SkillUse skillUse = new L1SkillUse();
                if (skillid == 132 && weapon.getItem().getType1() == 20) {
                    skillUse.handleCommands(pc, skillid, targets.getId(), targets.getX(), targets.getY(), skill.getBuffDuration(), 0);
                }
                else if (skillid == 187 && weapon.getItem().getType1() == 24) {
                    skillUse.handleCommands(pc, skillid, targets.getId(), targets.getX(), targets.getY(), skill.getBuffDuration(), 0);
                }
                else if (skillid != 132 && skillid != 187) {
                    skillUse.handleCommands(pc, skillid, targets.getId(), targets.getX(), targets.getY(), skill.getBuffDuration(), 0);
                }
                pc.setAutoNow_AttackSkillDelay(pc.getAu_AutoSkill(5));
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void SkillDmg3(final L1PcInstance pc, final L1Character targets) {
        try {
            if (!pc.isSkillDelay()) {
                final int skillid = pc.getAu_AutoSkill(6);
                final L1Skills skill = SkillsTable.get().getTemplate(skillid);
                final L1SkillUse skillUse = new L1SkillUse();
                skillUse.handleCommands(pc, skillid, targets.getId(), targets.getX(), targets.getY(), skill.getBuffDuration(), 0);
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void searchTarget() {
        final Collection<L1Object> allObj = World.get().getVisibleObjects(this, 3);
        for (final L1Object obj : allObj) {
            if (!(obj instanceof L1MonsterInstance)) {
                continue;
            }
            final L1MonsterInstance mob = (L1MonsterInstance) obj;

            if (this.getAu_OtherSet(4) > 0 && mob.getNpcTemplate().is_boss() && this.get_followmaster() == null && this.getMap().isTeleportable()) {
                L1Teleport.randomTeleportai(this);
                this.move = 0;
            } else {
                if (mob.hasSkillEffect(33) || mob.hasSkillEffect(50) || mob.hasSkillEffect(1011) || mob.hasSkillEffect(1009)) {
                    continue;
                }

                if (mob.isDead() || mob.getCurrentHp() <= 0 || mob.getHiddenStatus() > 0 || mob.getAtkspeed() == 0) {
                    continue;
                }

                if (mob.hasSkillEffect(this.getId() + 100000) && !this.isAttackPosition(mob.getX(), mob.getY(), 1)) {
                    continue;
                }

                int distance = 0;
                if (this.glanceCheck(mob.getX(), mob.getY())) {
                    distance += 5;
                }

                this._hateList.add(mob, distance);
            }
        }

        Collection<L1Object> allObj2;
        if (this.get_followmaster() != null) {
            allObj2 = World.get().getVisibleObjects(this, 3);
        } else {
            allObj2 = World.get().getVisibleObjects(this, 7);
        }

        for (final L1Object obj2 : allObj2) {
            if (!(obj2 instanceof L1MonsterInstance)) {
                continue;
            }

            final L1MonsterInstance mob2 = (L1MonsterInstance) obj2;

            if (this.getAu_OtherSet(4) > 0 && mob2.getNpcTemplate().is_boss() && this.get_followmaster() == null && this.getMap().isTeleportable()) {
                L1Teleport.randomTeleportai(this);
                this.move = 0;
                break; // 使用 break 中斷迴圈
            }

            if (mob2.hasSkillEffect(33) || mob2.hasSkillEffect(50) || mob2.hasSkillEffect(1011) || mob2.hasSkillEffect(1009)) {
                continue;
            }

            if (mob2.isDead() || mob2.getCurrentHp() <= 0 || mob2.getHiddenStatus() > 0 || mob2.getAtkspeed() == 0) {
                continue;
            }

            if (mob2.hasSkillEffect(this.getId() + 100000) && !this.isAttackPosition(mob2.getX(), mob2.getY(), 1)) {
                continue;
            }

            int distance = 0;
            if (this.glanceCheck(mob2.getX(), mob2.getY())) {
                distance += 5;
            }

            this._hateList.add(mob2, distance);
        }

        this._target = this._hateList.getMaxHateCharacter();
        if (this._target == null && this.getRegenState() != 1 && this.getAu_AutoSet(3) > 0 && this.getAutoX() == 0 && this.getAutoY() == 0 && !this.hasSkillEffect(8852) && this.get_followmaster() == null && this.getMap().isTeleportable()) {
            boolean isStop = true;
            if (this.isParalyzed_guaji()) {
                isStop = false;
            }
            if (isStop && this.getAu_AutoSet(3) > 0 && this.getAu_AutoSet(5) > 0) {
                if (this.getAu_AutoSet(3) == 1) {
                    if (this.getCurrentMp() > 5 && !this.hasSkillEffect(1007) && !this.hasSkillEffect(64) && !this.hasSkillEffect(8912)) {
                        this.setCurrentMp(this.getCurrentMp() - 5);
                        L1Teleport.randomTeleportai(this);
                        this.move = 0;
                        this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
                    }
                }
                else if (this.getAu_AutoSet(3) == 2) {
                    if (this.getInventory().checkItem(77780, 1L)) {
                        L1Teleport.randomTeleportai(this);
                        this.getInventory().consumeItem(77780, 1L);
                        this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
                    }
                    else if (this.getInventory().checkItem(40100, 1L)) {
                        L1Teleport.randomTeleportai(this);
                        this.getInventory().consumeItem(40100, 1L);
                        this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
                    }
                    else if (this.getInventory().checkItem(40099, 1L)) {
                        L1Teleport.randomTeleportai(this);
                        this.getInventory().consumeItem(40099, 1L);
                        this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
                    }
                    else if (this.getInventory().checkItem(40863, 1L)) {
                        L1Teleport.randomTeleportai(this);
                        this.getInventory().consumeItem(40863, 1L);
                        this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
                    }
                    else {
                        this.sendPackets(new S_ServerMessage("瞬移道具不足無法瞬移"));
                    }
                }
            }
        }
        allObj.clear();
        allObj2.clear();
    }
    
    private void FlyChecking() {
        boolean ok = false;
        boolean ok2 = false;
        if (this.getAu_AutoSet(3) == 1) {
            if (this.getCurrentMp() > 5) {
                this.setCurrentMp(this.getCurrentMp() - 5);
                ok = true;
            }
        }
        else if (this.getAu_AutoSet(3) == 2) {
            if (this.getInventory().checkItem(40100, 1L)) {
                this.getInventory().consumeItem(40100, 1L);
                ok = true;
            }
            else if (this.getInventory().checkItem(40099, 1L)) {
                this.getInventory().consumeItem(40099, 1L);
                ok = true;
            }
            else if (this.getInventory().checkItem(40863, 1L)) {
                this.getInventory().consumeItem(40863, 1L);
                ok = true;
            }
        }
        if (!this.hasSkillEffect(33) || !this.hasSkillEffect(4000) || !this.hasSkillEffect(192) || !this.hasSkillEffect(50) || !this.hasSkillEffect(66) || !this.hasSkillEffect(87) || !this.hasSkillEffect(4017) || !this.hasSkillEffect(192) || !this.hasSkillEffect(157)) {
            ok2 = true;
        }
        if (ok && ok2) {
            L1Teleport.randomTeleportai(this);
            this.move = 0;
            this.setSkillEffect(8852, this.getAu_AutoSet(5) * 1000);
        }
    }
    
    public void targetClear() {
        if (this._target == null) {
            return;
        }
        this._hateList.remove(this._target);
        this._target = null;
    }
    
    public void onTarget() {
        try {
            final L1Character target = this._target;
            if (target == null) {
                return;
            }
            this.attack(target);
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void attack(final L1Character target) {
        int attack_Range = 1;
        if (this.getWeapon() != null) {
            attack_Range = this.getWeapon().getItem().getRange();
            if (this.get_followmaster() != null && attack_Range == -1) {
                attack_Range = 8;
            }
            else if (this.get_followmaster() != null && attack_Range == 1) {
                attack_Range = 1;
            }
            else if (this.get_followmaster() != null && attack_Range == 2) {
                attack_Range = 2;
            }
            else if (attack_Range == -1) {
                attack_Range = 8;
            }
            final int location = this.getLocation().getTileLineDistance(target.getLocation());
            if (location <= attack_Range && this.glanceCheck(target.getX(), target.getY())) {
                this.setHeading(this.targetDirection(target.getX(), target.getY()));
                this.attackTarget(target);
                if (this._pcMove != null) {
                    this._pcMove.clear();
                }
            }
            else if (this._pcMove != null) {
                final int dir = this._pcMove.moveDirection(target.getX(), target.getY());
                if (dir == -1) {
                    this._target.setSkillEffect(this.getId() + 100000, 20000);
                    this.allTargetClear();
                }
                else {
                    this._pcMove.setDirectionMove(dir);
                    ++this.move;
                }
            }
        }
    }
    
    public void setActived(final boolean actived) {
        this._actived = actived;
    }
    
    public boolean isActived() {
        return this._actived;
    }
    
    protected void setFirstAttack(final boolean firstAttack) {
        this._firstAttack = firstAttack;
    }
    
    protected boolean isFirstAttack() {
        return this._firstAttack;
    }
    
    public void setHate(final L1Character cha, final int hate) {
        try {
            if (cha != null && this._target != null && !this.isFirstAttack() && hate > 0) {
                this.setFirstAttack(true);
                if (this._pcMove != null) {
                    this._pcMove.clear();
                }
                this._hateList.add(cha, 5);
                this._target = this._hateList.getMaxHateCharacter();
                this.checkTarget();
            }
        }
        catch (Exception ex) {}
    }
    
    public boolean isPathfinding() {
        return this._Pathfinding;
    }
    
    public void setPathfinding(final boolean fla) {
        this._Pathfinding = fla;
    }
    
    public int getrandomMoveDirection() {
        return this._randomMoveDirection;
    }
    
    public void setrandomMoveDirection(final int randomMoveDirection) {
        this._randomMoveDirection = randomMoveDirection;
    }
    
    public void noTarget() {
        if (this.hasSkillEffect(1011)) {
            return;
        }
        if (this.hasSkillEffect(1009)) {
            return;
        }
        if (this.hasSkillEffect(4000)) {
            return;
        }
        if (!this._Pathfinding) {
            this._Pathfinding = true;
        }
        if (this._randomMoveDirection > 7) {
            this._randomMoveDirection = 0;
        }
        if (this._pcMove != null && this.getrandomMoveDirection() < 8) {
            int dir = this._pcMove.checkObject(this._randomMoveDirection);
            dir = this._pcMove.openDoor(dir);
            if (dir != -1) {
                this._pcMove.setDirectionMove(dir);
            }
            else {
                this._randomMoveDirection = L1PcInstance._random.nextInt(8);
                ++this.move;
            }
        }
    }
    
    public int getguajiX() {
        return this._tguajiX;
    }
    
    public void setguajiX(final int i) {
        this._tguajiX = i;
    }
    
    public int getguajiY() {
        return this._tguajiY;
    }
    
    public void setguajiY(final int i) {
        this._tguajiY = i;
    }
    
    public int getguajiMapId() {
        return this._guajiMapId;
    }
    
    public void setguajiMapId(final int i) {
        this._guajiMapId = i;
    }
    
    public void addArmorBreakLevel(final int add) {
        this._armorbreaklevel += add;
    }
    
    public int getArmorBreakLevel() {
        return this._armorbreaklevel;
    }
    
    public int get_FoeSlayerBonusDmg() {
        return this._FoeSlayerBonusDmg;
    }
    
    public void add_FoeSlayerBonusDmg(final int Dmg) {
        this._FoeSlayerBonusDmg += Dmg;
    }
    
    public void set_soulHp_val(final int r, final int hpmin, final int hpmax) {
        this._soulHp_r = r;
        this._soulHp_hpmin = hpmin;
        this._soulHp_hpmax = hpmax;
    }
    
    public void set_soulHp(final int flag) {
        this.isSoulHp = flag;
    }
    
    public int isSoulHp() {
        return this.isSoulHp;
    }
    
    public ArrayList<Integer> get_soulHp() {
        this.soulHp.add(0, this._soulHp_r);
        this.soulHp.add(1, this._soulHp_hpmin);
        this.soulHp.add(2, this._soulHp_hpmax);
        return this.soulHp;
    }
    
    public String getoldtitle() {
        return this.oldtitle;
    }
    
    public void setoldtitle(final String s) {
        this.oldtitle = s;
    }
    
    public final void setvipname1() {
        this.sendPackets(new S_HPUpdate(this));
        if (this.isInParty()) {
            this.getParty().updateMiniHP(this);
        }
        this.sendPackets(new S_MPUpdate(this));
        this.sendPackets(new S_SPMR(this));
        this.sendPackets(new S_OwnCharStatus(this));
        this.sendPackets(new S_OwnCharPack(this));
        this.removeAllKnownObjects();
        this.updateObject();
    }
    
    public String getvipname() {
        return this.vipname;
    }
    
    public void setvipname(final String s) {
        this.vipname = s;
    }
    
    public int get_PVPdmgg() {
        return this._PVPdmgg;
    }
    
    public void add_PVPdmgg(final int i) {
        this._PVPdmgg += i;
    }
    
    public int get_potion_healling() {
        return this._potion_healling;
    }
    
    public void add_potion_healling(final int i) {
        this._potion_healling += i;
    }
    
    public int get_potion_heal() {
        return this._potion_heal;
    }
    
    public void add_potion_heal(final int i) {
        this._potion_heal += i;
    }
    
    public void startPcRewardPrestigeGfxTimer(final int gfx, final int time) {
        (this._gfxTimer4 = new L1PcRewardPrestigeGfxTimer(this, gfx, time)).start();
    }
    
    public void stopPcRewardPrestigeGfxTimer() {
        if (this._gfxTimer4 != null) {
            this._gfxTimer4.cancel();
            this._gfxTimer4 = null;
        }
    }
    
    public int getWeaponSkillChance() {
        return this._weaponSkillChance;
    }
    
    public void setWeaponSkillChance(final int i) {
        this._weaponSkillChance = i;
    }
    
    public void setWeaponSkillDmg(final double d) {
        this._addWeaponSkillDmg = d;
    }
    
    public double getWeaponSkillDmg() {
        return this._addWeaponSkillDmg;
    }
    
    public String getnewaititle() {
        return this.newaititle;
    }
    
    public void setnewaititle(final String s) {
        this.newaititle = s;
    }
    
    public void setnewaicount(final int i) {
        this._newaicount = i;
    }
    
    public int getnewaicount() {
        return this._newaicount;
    }
    
    public void setproctctran(final int i) {
        this._proctctran = i;
    }
    
    public int getproctctran() {
        return this._proctctran;
    }
    
    public void setnewcharpra(final boolean newcharpra) {
        this._newcharpra = newcharpra;
    }
    
    public boolean getnewcharpra() {
        return this._newcharpra;
    }
    
    public void setguaji_count(final int i) {
        this._guaji_count = i;
    }
    
    public int getguaji_count() {
        return this._guaji_count;
    }
    
    public void setaibig(final int i) {
        this._aibig = i;
    }
    
    public int getaibig() {
        return this._aibig;
    }
    
    public void setaismall(final int i) {
        this._aismall = i;
    }
    
    public int getaismall() {
        return this._aismall;
    }
    
    public void setnewaicount_2(final int i) {
        this._newaicount_2 = i;
    }
    
    public int getnewaicount_2() {
        return this._newaicount_2;
    }
    
    public void setopengfxid(final boolean opengfxid) {
        this._opengfxid = opengfxid;
    }
    
    public boolean getopengfxid() {
        return this._opengfxid;
    }
    
    public void removeAICheck(final int itemid, final long count) {
        if (this != null) {
            this.getInventory().consumeItem(itemid);
        }
    }
    
    public void setAiGxfxid(final int i) {
        this._AiGxfxid = i;
    }
    
    public int getAiGxfxid() {
        return this._AiGxfxid;
    }
    
    public void setAierror(final int i) {
        this._Aierror = i;
    }
    
    public int getAierror() {
        return this._Aierror;
    }
    
    public int getAdd_Er() {
        return this._add_er;
    }
    
    public void setAdd_Er(final int add_er) {
        this._add_er = add_er;
    }
    
    public int getMoveErrorCount() {
        return this.moveErrorCount;
    }
    
    public void setMoveErrorCount(final int moveErrorCount) {
        this.moveErrorCount = moveErrorCount;
    }
    
    public boolean isMoveStatus() {
        return this.moveStatus;
    }
    
    public void setMoveStatus(final boolean moveStatus) {
        this.moveStatus = moveStatus;
    }
    
    public void setfollowskilltype(final int followskilltype) {
        this._followskilltype = followskilltype;
    }
    
    public int getfollowskilltype() {
        return this._followskilltype;
    }
    
    public void setfollowskillhp(final int followskillhp) {
        this._followskillhp = followskillhp;
    }
    
    public int getfollowskillhp() {
        return this._followskillhp;
    }
    
    public void setfollowmebuff(final boolean followmebuff) {
        this._followmebuff = followmebuff;
    }
    
    public boolean getfollowmebuff() {
        return this._followmebuff;
    }
    
    public int getItemBlendcheckitem() {
        return this._ItemBlendcheckitem;
    }
    
    public void setItemBlendcheckitem(final int i) {
        this._ItemBlendcheckitem = i;
    }
    
    public String get_ItemBlendcheckitemname() {
        return this._ItemBlendcheckitemname;
    }
    
    public void set_ItemBlendcheckitemname(final String s) {
        this._ItemBlendcheckitemname = s;
    }
    
    public String get_ItemBlendAllmsg() {
        return this._ItemBlendAllmsg;
    }
    
    public void set_ItemBlendAllmsg(final String s) {
        this._ItemBlendAllmsg = s;
    }
    
    public int getItemBlendcheckitemcount() {
        return this._ItemBlendcheckitemcount;
    }
    
    public void setItemBlendcheckitemcount(final int i) {
        this._ItemBlendcheckitemcount = i;
    }
    
    public int getItemBlendResdueItem() {
        return this._ItemBlendResdueItem;
    }
    
    public void setItemBlendResdueItem(final int i) {
        this._ItemBlendResdueItem = i;
    }
    
    public int getItemBlendResdueItemcount() {
        return this._ItemBlendResdueItemcount;
    }
    
    public void setItemBlendResdueItemcount(final int i) {
        this._ItemBlendResdueItemcount = i;
    }
    
    public int getItemBlendResdueItemLv() {
        return this._ItemBlendResdueItemLv;
    }
    
    public void setItemBlendResdueItemLv(final int i) {
        this._ItemBlendResdueItemLv = i;
    }
    
    public int getItemBlendrnd() {
        return this._ItemBlendrnd;
    }
    
    public void setItemBlendrnd(final int i) {
        this._ItemBlendrnd = i;
    }
    
    public int getItemBlendGvEnchantlvl() {
        return this._ItemBlendGvEnchantlvl;
    }
    
    public void setItemBlendGvEnchantlvl(final int i) {
        this._ItemBlendGvEnchantlvl = i;
    }
    
    public int get_hppotion() {
        return this._hppotion;
    }
    
    public void add_hppotion(final int i) {
        this._hppotion += i;
    }
    
    public int get_pvp() {
        return this._pvp;
    }
    
    public void setvviipp(final boolean vviipp) {
        this._vviipp = vviipp;
    }
    
    public void add_pvp(final int i) {
        this._pvp += i;
    }
    
    public int get_bowpvp() {
        return this._bowpvp;
    }
    
    public void add_bowpvp(final int i) {
        this._bowpvp += i;
    }
    
    public void setfollowxy1(final int followxy1) {
        this._followxy1 = followxy1;
    }
    
    public int getfollowxy1() {
        return this._followxy1;
    }
    
    public void setpolyarrow(final int i) {
        this._polyarrow = i;
    }
    
    public int getpolyarrow() {
        return this._polyarrow;
    }
    
    public int getcallclanal() {
        return this.callclanal;
    }
    
    public void setcallclanal(final int s) {
        this.callclanal = s;
    }
    
    public int getcallclana2() {
        return this.callclana2;
    }
    
    public void setcallclana2(final int s) {
        this.callclana2 = s;
    }
    
    public void setchangtype1(final int i) {
        this._changtype1 = i;
    }
    
    public int getchangtype1() {
        return this._changtype1;
    }
    
    public void setchangtype2(final int i) {
        this._changtype2 = i;
    }
    
    public int getchangtype2() {
        return this._changtype2;
    }
    
    public void setchangtype3(final int i) {
        this._changtype3 = i;
    }
    
    public int getchangtype3() {
        return this._changtype3;
    }
    
    public void setchangtype4(final int i) {
        this._changtype4 = i;
    }
    
    public int getchangtype4() {
        return this._changtype4;
    }
    
    public void setchangtype5(final int i) {
        this._changtype5 = i;
    }
    
    public int getchangtype5() {
        return this._changtype5;
    }
    
    public String getchangtypename1() {
        return this.changtypename1;
    }
    
    public void setchangtypename1(final String s) {
        this.changtypename1 = s;
    }
    
    public String getchangtypename2() {
        return this.changtypename2;
    }
    
    public void setchangtypename2(final String s) {
        this.changtypename2 = s;
    }
    
    public String getchangtypename3() {
        return this.changtypename3;
    }
    
    public void setchangtypename3(final String s) {
        this.changtypename3 = s;
    }
    
    public String getchangtypename4() {
        return this.changtypename4;
    }
    
    public void setchangtypename4(final String s) {
        this.changtypename4 = s;
    }
    
    public void setpag(final int i) {
        this._pag = i;
    }
    
    public int getpag() {
        return this._pag;
    }
    
    public boolean IsKeyInEnemy() {
        return this._keyenemy;
    }
    
    public void setKeyInEnemy(final boolean b) {
        this._keyenemy = b;
    }
    
    public void setInEnemyList(final String id) {
        if (!this._attackenemy.contains(new String(id))) {
            this._attackenemy.add(new String(id));
        }
    }
    
    public void removeInEnemyList(final String id) {
        if (this._attackenemy.contains(new String(id))) {
            this._attackenemy.remove(new String(id));
        }
    }
    
    public boolean isInEnemyList(final String id) {
        return this._attackenemy.contains(new String(id));
    }
    
    public ArrayList<String> InEnemyList() {
        return this._attackenemy;
    }
    
    public void clearInEnemyList() {
        this._attackenemy.clear();
    }
    
    public boolean IsKeyOutEnemy() {
        return this._outenemy;
    }
    
    public void setKeyOutEnemy(final boolean b) {
        this._outenemy = b;
    }
    
    public void setnpcdmg(final double d) {
        this._npcdmg = d;
    }
    
    public double getnpcdmg() {
        return this._npcdmg;
    }
    
    public int getnewai1() {
        return this._newai1;
    }
    
    public void setnewai1(final int i) {
        this._newai1 = i;
    }
    
    public int getnewai2() {
        return this._newai2;
    }
    
    public void setnewai2(final int i) {
        this._newai2 = i;
    }
    
    public int getnewai3() {
        return this._newai3;
    }
    
    public void setnewai3(final int i) {
        this._newai3 = i;
    }
    
    public int getnewai4() {
        return this._newai4;
    }
    
    public void setnewai4(final int i) {
        this._newai4 = i;
    }
    
    public int getnewai5() {
        return this._newai5;
    }
    
    public void setnewai5(final int i) {
        this._newai5 = i;
    }
    
    public int getnewai6() {
        return this._newai6;
    }
    
    public void setnewai6(final int i) {
        this._newai6 = i;
    }
    
    public int getnewaiq1() {
        return this._newaiq1;
    }
    
    public void setnewaiq1(final int i) {
        this._newaiq1 = i;
    }
    
    public int getnewaiq2() {
        return this._newaiq2;
    }
    
    public void setnewaiq2(final int i) {
        this._newaiq2 = i;
    }
    
    public int getnewaiq3() {
        return this._newaiq3;
    }
    
    public void setnewaiq3(final int i) {
        this._newaiq3 = i;
    }
    
    public int getnewaiq4() {
        return this._newaiq4;
    }
    
    public void setnewaiq4(final int i) {
        this._newaiq4 = i;
    }
    
    public int getnewaiq5() {
        return this._newaiq5;
    }
    
    public void setnewaiq5(final int i) {
        this._newaiq5 = i;
    }
    
    public int getnewaiq6() {
        return this._newaiq6;
    }
    
    public void setnewaiq6(final int i) {
        this._newaiq6 = i;
    }
    
    public int getnewaiq7() {
        return this._newaiq7;
    }
    
    public void setnewaiq7(final int i) {
        this._newaiq7 = i;
    }
    
    public int getnewaiq8() {
        return this._newaiq8;
    }
    
    public void setnewaiq8(final int i) {
        this._newaiq8 = i;
    }
    
    public int getnewaiq9() {
        return this._newaiq9;
    }
    
    public void setnewaiq9(final int i) {
        this._newaiq9 = i;
    }
    
    public int getnewaiq0() {
        return this._newaiq0;
    }
    
    public void setnewaiq0(final int i) {
        this._newaiq0 = i;
    }
    
    public static void bowisbuy(final String info) {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter("玩家紀錄/物品噴掉紀錄.txt", true));
            out.write(String.valueOf(String.valueOf(String.valueOf(String.valueOf(info)))) + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int getnpciddmg() {
        return this._npciddmg;
    }
    
    public void setnpciddmg(final int i) {
        this._npciddmg = i;
    }
    
    public void broadcastPacket(final ServerBasePacket packet) {
        ArrayList<L1PcInstance> objs = World.get().getVisiblePlayer(this);
        try {
            for (L1PcInstance pc : objs) {
                if (pc.getMapId() >= 16384 && pc.getMapId() <= 25088) {
                    continue;
                }
                pc.sendPackets(packet);
            }
        } catch (Exception e) {
            L1PcInstance._log.error(e.getLocalizedMessage(), e);
        } finally {
            objs.clear();
            objs = null;
        }
    }
    
    public void setNpcSpeed() {
        try {
            if (!this.getDolls().isEmpty()) {
                final Object[] arrayOfObject;
                final int i = (arrayOfObject = this.getDolls().values().toArray()).length;
                for (byte b = 0; b < i; ++b) {
                    final Object obj = arrayOfObject[b];
                    final L1DollInstance doll = (L1DollInstance)obj;
                    if (doll != null) {
                        doll.setNpcMoveSpeed();
                    }
                }
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
        try {
            if (this.getHierarchs() != null) {
                this.getHierarchs().setNpcMoveSpeed();
            }
        }
        catch (Exception e) {
            L1PcInstance._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void setfollowatk(final boolean followatk) {
        this._followatk = followatk;
    }
    
    public boolean getfollowatk() {
        return this._followatk;
    }
    
    public void setfollowatkmagic(final boolean followatkmagic) {
        this._followatkmagic = followatkmagic;
    }
    
    public boolean getfollowatkmagic() {
        return this._followatkmagic;
    }
    
    public boolean isfollowskill26() {
        return this._isfollowskill26;
    }
    
    public void setfollowskill26(final boolean flag) {
        this._isfollowskill26 = flag;
    }
    
    public boolean isfollowskill42() {
        return this._isfollowskill42;
    }
    
    public void setfollowskill42(final boolean flag) {
        this._isfollowskill42 = flag;
    }
    
    public boolean isfollowskill55() {
        return this._isfollowskill55;
    }
    
    public void setfollowskill55(final boolean flag) {
        this._isfollowskill55 = flag;
    }
    
    public boolean isfollowskill68() {
        return this._isfollowskill68;
    }
    
    public void setfollowskill68(final boolean flag) {
        this._isfollowskill68 = flag;
    }
    
    public boolean isfollowskill160() {
        return this._isfollowskill160;
    }
    
    public void setfollowskill160(final boolean flag) {
        this._isfollowskill160 = flag;
    }
    
    public boolean isfollowskill79() {
        return this._isfollowskill79;
    }
    
    public void setfollowskill79(final boolean flag) {
        this._isfollowskill79 = flag;
    }
    
    public boolean isfollowskill148() {
        return this._isfollowskill148;
    }
    
    public void setfollowskill148(final boolean flag) {
        this._isfollowskill148 = flag;
    }
    
    public boolean isfollowskill151() {
        return this._isfollowskill151;
    }
    
    public void setfollowskill151(final boolean flag) {
        this._isfollowskill151 = flag;
    }
    
    public boolean isfollowskill149() {
        return this._isfollowskill149;
    }
    
    public void setfollowskill149(final boolean flag) {
        this._isfollowskill149 = flag;
    }
    
    public boolean isfollowskill158() {
        return this._isfollowskill158;
    }
    
    public void setfollowskill158(final boolean flag) {
        this._isfollowskill158 = flag;
    }
    
    public boolean isnomoveguaji() {
        return this._isnomoveguaji;
    }
    
    public void setnomoveguaji(final boolean flag) {
        this._isnomoveguaji = flag;
    }
    
    public boolean IsBadKeyInEnemy() {
        return this._Badkeyenemy;
    }
    
    public void setBadKeyInEnemy(final boolean b) {
        this._Badkeyenemy = b;
    }
    
    public void setBadInEnemyList(final String id) {
        if (!this._Badattackenemy.contains(new String(id))) {
            this._Badattackenemy.add(new String(id));
        }
    }
    
    public void removeBadInEnemyList(final String id) {
        if (this._Badattackenemy.contains(new String(id))) {
            this._Badattackenemy.remove(new String(id));
        }
    }
    
    public boolean isBadInEnemyList(final String id) {
        return this._Badattackenemy.contains(new String(id));
    }
    
    public ArrayList<String> InBadEnemyList() {
        return this._Badattackenemy;
    }
    
    public void clearBadInEnemyList() {
        this._Badattackenemy.clear();
    }
    
    public boolean IsBadKeyOutEnemy() {
        return this._Badoutenemy;
    }
    
    public void setBadKeyOutEnemy(final boolean b) {
        this._Badoutenemy = b;
    }
    
    public short getoldMapId() {
        return this._oldMapId;
    }
    
    public void setoldMapId(final short i) {
        this._oldMapId = i;
    }
    
    public boolean getcheckpoly() {
        return this._ischeckpoly;
    }
    
    public void setcheckpoly(final boolean flag) {
        this._ischeckpoly = flag;
    }
    
    public void setitemactionhtml(final String itemactionhtml) {
        this._itemactionhtml = itemactionhtml;
    }
    
    public String getitemactionhtml() {
        return this._itemactionhtml;
    }
    
    public boolean getOutbur() {
        return this._isOutbur;
    }
    
    public void setOutbur(final boolean flag) {
        this._isOutbur = flag;
    }
    
    public boolean getcheckOutbur() {
        return this._ischeckOutbur;
    }
    
    public void setcheckOutbur(final boolean flag) {
        this._ischeckOutbur = flag;
    }
    
    public int getQuburcount() {
        return this._Quburcount;
    }
    
    public void setQuburcount(final int i) {
        this._Quburcount = i;
    }
    
    public void setWeaponTotalDmg(final int i) {
        this._WeaponTotalDmg = i;
    }
    
    public int getWeaponTotalDmg() {
        return this._WeaponTotalDmg;
    }
    
    public void addWeaponTotalDmg(final int i) {
        this._WeaponTotalDmg += i;
    }
    
    public void setweaknss1(final int i) {
        this._weaknss1 = i;
    }
    
    public int getweaknss1() {
        return this._weaknss1;
    }
    
    public void addweaknss1(final int i) {
        this._weaknss += i;
    }
    
    public void setWeaponSkillPro(final int i) {
        this._WeaponSkillPro = i;
    }
    
    public int getWeaponSkillPro() {
        return this._WeaponSkillPro;
    }
    
    public void addWeaponSkillPro(final int i) {
        this._WeaponSkillPro += i;
    }
    
    public void setSave_Quest_Map1(final int i) {
        this._Save_Quest_Map1 = i;
    }
    
    public int getSave_Quest_Map1() {
        return this._Save_Quest_Map1;
    }
    
    public void setSave_Quest_Map2(final int i) {
        this._Save_Quest_Map2 = i;
    }
    
    public int getSave_Quest_Map2() {
        return this._Save_Quest_Map2;
    }
    
    public void setSave_Quest_Map3(final int i) {
        this._Save_Quest_Map3 = i;
    }
    
    public int getSave_Quest_Map3() {
        return this._Save_Quest_Map3;
    }
    
    public void setSave_Quest_Map4(final int i) {
        this._Save_Quest_Map4 = i;
    }
    
    public int getSave_Quest_Map4() {
        return this._Save_Quest_Map4;
    }
    
    public void setSave_Quest_Map5(final int i) {
        this._Save_Quest_Map5 = i;
    }
    
    public int getSave_Quest_Map5() {
        return this._Save_Quest_Map5;
    }
    
    public void setCarId(final int i) {
        this._CardId = i;
    }
    
    public int getCardId() {
        return this._CardId;
    }
    
    public void setTempThread(final SoulTowerThread thread) {
        this._tempThread = thread;
    }
    
    public Thread getTempThread() {
        return this._tempThread;
    }
    
    public void setSoulTower(final int i) {
        this.soulTower = i;
    }
    
    public boolean isSoulTower() {
        return this.soulTower == 0;
    }
    
    public int getSoulTower() {
        return this.soulTower;
    }
    
    public boolean petReceive(final int itemObjectId) {
        if (!this.getMap().isTakePets()) {
            this.sendPackets(new S_ServerMessage(563));
            return false;
        }
        int petCost = 0;
        int petCount = 0;
        int divisor = 6;
        final Object[] petList = this.getPetList().values().toArray();
        if (petList.length > ConfigOther.petcountchatype1) {
            this.sendPackets(new S_ServerMessage(489));
            return false;
        }
        final Object[] arrayOfObject1;
        final int i = (arrayOfObject1 = petList).length;
        for (byte b = 0; b < i; ++b) {
            final Object pet = arrayOfObject1[b];
            if (pet instanceof L1PetInstance && ((L1PetInstance)pet).getItemObjId() == itemObjectId) {
                return false;
            }
            petCost += ((L1NpcInstance)pet).getPetcost();
        }
        int charisma = this.getCha();
        final L1Pet l1pet = PetReading.get().getTemplate(itemObjectId);
        if (l1pet == null) {
            return false;
        }
        final int npcId = l1pet.get_npcid();
        charisma -= petCost;
        if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            divisor = 12;
        }
        else {
            divisor = 2;
        }
        petCount = charisma / divisor;
        if (petCount <= 0) {
            this.sendPackets(new S_ServerMessage(489));
            return false;
        }
        final L1Npc npcTemp = NpcTable.get().getTemplate(npcId);
        final L1PetInstance pet2 = new L1PetInstance(npcTemp, this, l1pet);
        pet2.setPetcost(divisor);
        return true;
    }
    
    public boolean petReceive1(final int itemObjectId) {
        if (!this.getMap().isTakePets()) {
            this.sendPackets(new S_ServerMessage(563));
            return false;
        }
        int petCost = 0;
        int petCount = 0;
        int divisor = 6;
        final Object[] petList = this.getPetList().values().toArray();
        if (petList.length > ConfigOther.petcountchatype) {
            this.sendPackets(new S_ServerMessage(489));
            return false;
        }
        final Object[] arrayOfObject1;
        final int i = (arrayOfObject1 = petList).length;
        for (byte b = 0; b < i; ++b) {
            final Object pet = arrayOfObject1[b];
            petCost += ((L1NpcInstance)pet).getPetcost();
        }
        int charisma = this.getCha();
        if (this.isCrown()) {
            charisma += 6;
        }
        else if (this.isElf()) {
            charisma += 12;
        }
        else if (this.isWizard()) {
            charisma += 6;
        }
        else if (this.isDarkelf()) {
            charisma += 6;
        }
        else if (this.isDragonKnight()) {
            charisma += 6;
        }
        else if (this.isIllusionist()) {
            charisma += 6;
        }
        final L1Pet l1pet = PetReading.get().getTemplate(itemObjectId);
        if (l1pet == null) {
            return false;
        }
        final int npcId = l1pet.get_npcid();
        charisma -= petCost;
        if (npcId == 45313 || npcId == 45710 || npcId == 45711 || npcId == 45712) {
            divisor = 12;
        }
        else {
            divisor = 6;
        }
        petCount = charisma / divisor;
        if (petCount <= 0) {
            this.sendPackets(new S_ServerMessage(489));
            return false;
        }
        final L1Npc npcTemp = NpcTable.get().getTemplate(npcId);
        final L1PetInstance pet2 = new L1PetInstance(npcTemp, this, l1pet);
        pet2.setPetcost(divisor);
        return true;
    }
    
    public boolean getarmor_setgive() {
        return this._isarmor_setgive;
    }
    
    public void setarmor_setgive(final boolean flag) {
        this._isarmor_setgive = flag;
    }
    
    public void setSummon_npcid(final String Summon_npcid) {
        this._Summon_npcid = Summon_npcid;
    }
    
    public String getSummon_npcid() {
        return this._Summon_npcid;
    }
    
    public void setsummon_skillid(final int i) {
        this._summon_skillid = i;
    }
    
    public int getsummon_skillid() {
        return this._summon_skillid;
    }
    
    public void setsummon_skillidmp(final int i) {
        this._summon_skillidmp = i;
    }
    
    public int getsummon_skillidmp() {
        return this._summon_skillidmp;
    }
    
    public int getTowerIsWhat() {
        return this._towerIsWhat;
    }
    
    public void setTowerIsWhat(final int i) {
        this._towerIsWhat = i;
    }
    
    public int getDoll_MagicHit() {
        return this._Doll_MagicHit;
    }
    
    public void setDoll_MagicHit(final int i) {
        this._Doll_MagicHit = i;
    }
    
    public void addDoll_MagicHit(final int i) {
        this._Doll_MagicHit += i;
    }
    
    public int getfirst_pay() {
        return this._first_pay;
    }
    
    public void setfirst_pay(final int i) {
        this._first_pay = i;
    }
    
    public boolean IsAu_Shop() {
        return this._au_shop;
    }
    
    public void setAu_Shop(final boolean b) {
        this._au_shop = b;
    }
    
    public int getAu_BuyItemSwitch(final int i) {
        return this._au_buyitemswitch[i];
    }
    
    public void setAu_BuyItemSwitch(final int i, final int j) {
        this._au_buyitemswitch[i] = j;
    }
    
    public int getAu_BuyItemCount(final int i) {
        return this._au_buyitemcount[i];
    }
    
    public void setAu_BuyItemCount(final int i, final int j) {
        this._au_buyitemcount[i] = j;
    }
    
    public boolean IsAu_SetShop() {
        return this._au_setshop;
    }
    
    public void setAu_SetShop(final boolean b) {
        this._au_setshop = b;
    }
    
    public int getAu_SetShopType() {
        return this._setshoptype;
    }
    
    public void SetAu_SetShopType(final int i) {
        this._setshoptype = i;
    }
    
    public int getAu_AutoSkill(final int i) {
        return this._au_automagic[i];
    }
    
    public void setAu_AutoSkill(final int i, final int j) {
        this._au_automagic[i] = j;
    }
    
    public int getAutoNow_AttackSkillDelay() {
        return this._a53;
    }
    
    public void setAutoNow_AttackSkillDelay(final int i) {
        this._a53 = i;
    }
    
    public void setsummon_skillidmp_1(final int i) {
        this._summon_skillidmp_1 = i;
    }
    
    public int getsummon_skillidmp_1() {
        return this._summon_skillidmp_1;
    }
    
    public int getAu_AutoSet(final int i) {
        return this._au_autoset[i];
    }
    
    public void setAu_AutoSet(final int i, final int j) {
        this._au_autoset[i] = j;
    }
    
    public void setAutoX(final int i) {
        this._autoX = i;
    }
    
    public int getAutoX() {
        return this._autoX;
    }
    
    public void setAutoY(final int i) {
        this._autoy = i;
    }
    
    public int getAutoY() {
        return this._autoy;
    }
    
    public void setAutoMap(final int i) {
        this._autom = i;
    }
    
    public int getAutoMap() {
        return this._autom;
    }
    
    public int getAu_OtherSet(final int i) {
        return this._au_otherautoset[i];
    }
    
    public void setAu_OtherSet(final int i, final int j) {
        this._au_otherautoset[i] = j;
    }
    
    public void setcmpcount(final int i) {
        this._cmpcount = i;
    }
    
    public int getcmpcount() {
        return this._cmpcount;
    }
    
    public void setweapon_b(final double d) {
        this._weapon_b = d;
    }
    
    public double getweapon_b() {
        return this._weapon_b;
    }
    
    public void setweapon_b_gfx_r(final int i) {
        this._weapon_b_gfx_r = i;
    }
    
    public int getweapon_b_gfx_r() {
        return this._weapon_b_gfx_r;
    }
    
    public void setweapon_b_gfx(final int[] giveItem) {
        this._weapon_b_gfx = giveItem;
    }
    
    public int[] getweapon_b_gfx() {
        return this._weapon_b_gfx;
    }
    
    public int get_backpage() {
        return this._backpage;
    }
    
    public void set_backpage(final int _backpage) {
        this._backpage = _backpage;
    }
    
    public void setPolyScrol3(final L1ItemInstance item) {
        this._polyscrol3 = item;
    }
    
    public L1ItemInstance getPolyScrol3() {
        return this._polyscrol3;
    }
    
    public void setDmgup_b_ran(final int i) {
        this.Dmgup_b_ran = i;
    }
    
    public int getDmgup_b_ran() {
        return this.Dmgup_b_ran;
    }
    
    public double getDmgup_b() {
        return this.Dmgup_b;
    }
    
    public void setDmgup_b(final double d) {
        this.Dmgup_b = d;
    }
    
    public int getdollcount() {
        return this._dollcount;
    }
    
    public void add_dollcount(final int i) {
        this._dollcount += i;
    }
    
    public int getdollcount1() {
        return this._dollcount1;
    }
    
    public void add_dollcount1(final int i) {
        this._dollcount1 += i;
    }
    
    public int getdollcount_itemid() {
        return this._dollcount_itemid;
    }
    
    public void add_dollcount_itemid(final int i) {
        this._dollcount_itemid += i;
    }
    
    public int getdollcount1_itemid() {
        return this._dollcount1_itemid;
    }
    
    public void add_dollcount1_itemid(final int i) {
        this._dollcount1_itemid += i;
    }
    
    public int get_bosspage() {
        return this.bosspage;
    }
    
    public void set_bosspage(final int _bosspage) {
        this.bosspage = _bosspage;
    }
    
    public boolean isBuffSkillList(final Integer id) {
        return this._autobuff.contains(id);
    }
    
    public ArrayList<Integer> BuffSkillList() {
        return this._autobuff;
    }
    
    public int BuffSkillSize() {
        return this._autobuff.size();
    }
    
    public void clearBuffSkillList() {
        this._autobuff.clear();
    }
    
    public void setBuffSkillList(final Integer id) {
        if (!this._autobuff.contains(id)) {
            this._autobuff.add(new Integer(id));
        }
    }
    
    public void setBuffSkillList1(final Integer skillid) {
        if (this._autobuff.contains(skillid)) {
            this._autobuff.remove(skillid);
        }
    }
    
    public long getskill_timeDelay() {
        return this.skill_timeDelay;
    }
    
    public void setskill_timeDelay(final long now_time) {
        this.skill_timeDelay = now_time;
    }
    
    public void setcardpoly(final int i) {
        this._cardpoly = i;
    }
    
    public int getcardpoly() {
        return this._cardpoly;
    }
    
    public void setpcezpay(final int i) {
        this._pcezpay = i;
    }
    
    public int getpcezpay() {
        return this._pcezpay;
    }
    
    public void setoldAllName(final String oldAllName) {
        this._oldAllName = oldAllName;
    }
    
    public String getoldAllName() {
        return this._oldAllName;
    }
    
    public void setblendcheckitemcount(final int[] blendcheckitemcount) {
        this._blendcheckitemcount = blendcheckitemcount;
    }
    
    public int[] getblendcheckitemcount() {
        return this._blendcheckitemcount;
    }
    
    public void setblendcheckitemen(final int[] blendcheckitemen) {
        this._blendcheckitemen = blendcheckitemen;
    }
    
    public int[] getblendcheckitemen() {
        return this._blendcheckitemen;
    }
    
    public void setblendcheckitem(final int[] blendcheckitem) {
        this._blendcheckitem = blendcheckitem;
    }
    
    public int[] getblendcheckitem() {
        return this._blendcheckitem;
    }
    
    public void startSustainEffect2(final L1PcInstance pc, final int effect_id, final int Interval) {
        final int _Interval = Interval;
        this.SustainEffect2 = new SustainEffect2(pc, effect_id);
        L1PcInstance._regenTimer.scheduleAtFixedRate(this.SustainEffect2, _Interval, _Interval);
    }
    
    public void stopSustainEffect2() {
        this.SustainEffect2.cancel();
        this.SustainEffect2 = null;
    }
    
    public String getTheEnemy() {
        return this.TheEnemy;
    }
    
    public void setTheEnemy(final String s) {
        this.TheEnemy = s;
    }
    
    public int getweaponskillpro() {
        return this._weaponskillpro;
    }
    
    public void setweaponskillpro(final int i) {
        this._weaponskillpro = i;
    }
    
    public void addweaponskillpro(final int i) {
        this._weaponskillpro += i;
    }
    
    public int getweaponskilldmg() {
        return this._weaponskilldmg;
    }
    
    public void setweaponskilldmg(final int i) {
        this._weaponskilldmg = i;
    }
    
    public void addweaponskilldmg(final int i) {
        this._weaponskilldmg += i;
    }
    
    public int geteffect_gfxid() {
        return this._effect_gfxid;
    }
    
    public void seteffect_gfxid(final int i) {
        this._effect_gfxid = i;
    }
    
    public void addeffect_gfxid(final int i) {
        this._effect_gfxid += i;
    }
    
    public int geteffect_time() {
        return this._effect_time;
    }
    
    public void seteffect_time(final int i) {
        this._effect_time = i;
    }
    
    public void addeffect_time(final int i) {
        this._effect_time += i;
    }
    
    public int gethpr_time() {
        return this._hpr_time;
    }
    
    public void sethpr_time(final int i) {
        this._hpr_time = i;
    }
    
    public void addhpr_time(final int i) {
        this._hpr_time += i;
    }
    
    public int gethprr() {
        return this._hprr;
    }
    
    public void sethprr(final int i) {
        this._hprr = i;
    }
    
    public void addhprr(final int i) {
        this._hprr += i;
    }
    
    public int gethpr_gfxid() {
        return this._hpr_gfxid;
    }
    
    public void sethpr_gfxid(final int i) {
        this._hpr_gfxid = i;
    }
    
    public void addhpr_gfxid(final int i) {
        this._hpr_gfxid += i;
    }
    
    public int getmpr_time() {
        return this._mpr_time;
    }
    
    public void setmpr_time(final int i) {
        this._mpr_time = i;
    }
    
    public void addmpr_time(final int i) {
        this._mpr_time += i;
    }
    
    public int getmprr() {
        return this._mprr;
    }
    
    public void setmprr(final int i) {
        this._mprr = i;
    }
    
    public void addmprr(final int i) {
        this._mprr += i;
    }
    
    public int getmpr_gfxid() {
        return this._mpr_gfxid;
    }
    
    public void setmpr_gfxid(final int i) {
        this._mpr_gfxid = i;
    }
    
    public void addmpr_gfxid(final int i) {
        this._mpr_gfxid += i;
    }
    
    public int getDmgReductionChance() {
        return this._DmgReductionChance;
    }
    
    public void setDmgReductionChance(final int i) {
        this._DmgReductionChance = i;
    }
    
    public void addDmgReductionChance(final int i) {
        this._DmgReductionChance += i;
    }
    
    public int getDmgReductionDmg() {
        return this._DmgReductionDmg;
    }
    
    public void setDmgReductionDmg(final int i) {
        this._DmgReductionDmg = i;
    }
    
    public void addDmgReductionDm(final int i) {
        this._DmgReductionDmg += i;
    }
    
    public int get近戰爆擊發動率() {
        return this._近戰爆擊發動率;
    }
    
    public void set近戰爆擊發動率(final int i) {
        this._近戰爆擊發動率 = i;
    }
    
    public void add近戰爆擊發動率(final int i) {
        this._近戰爆擊發動率 += i;
    }
    
    public double get近戰爆擊倍率() {
        return this._近戰爆擊倍率;
    }
    
    public void set近戰爆擊倍率(final double d) {
        this._近戰爆擊倍率 = d;
    }
    
    public int get遠攻爆擊發動率() {
        return this._遠攻爆擊發動率;
    }
    
    public void set遠攻爆擊發動率(final int i) {
        this._遠攻爆擊發動率 = i;
    }
    
    public void add遠攻爆擊發動率(final int i) {
        this._遠攻爆擊發動率 += i;
    }
    
    public double get遠攻爆擊倍率() {
        return this._遠攻爆擊倍率;
    }
    
    public void set遠攻爆擊倍率(final double d) {
        this._遠攻爆擊倍率 = d;
    }
    
    public int get魔法爆擊發動率() {
        return this._魔法爆擊發動率;
    }
    
    public void set魔法爆擊發動率(final int i) {
        this._魔法爆擊發動率 = i;
    }
    
    public void add魔法爆擊發動率(final int i) {
        this._魔法爆擊發動率 += i;
    }
    
    public double get魔法爆擊倍率() {
        return this._魔法爆擊倍率;
    }
    
    public void set魔法爆擊倍率(final double d) {
        this._魔法爆擊倍率 = d;
    }
    
    public int get吸取HP機率() {
        return this._吸取HP機率;
    }
    
    public void set吸取HP機率(final int i) {
        this._吸取HP機率 = i;
    }
    
    public int get吸取HP固定值() {
        return this._吸取HP固定值;
    }
    
    public void set吸取HP固定值(final int i) {
        this._吸取HP固定值 = i;
    }
    
    public int get吸取HP隨機值() {
        return this._吸取HP隨機值;
    }
    
    public void set吸取HP隨機值(final int i) {
        this._吸取HP隨機值 = i;
    }
    
    public int get吸取HP動畫() {
        return this._吸取HP動畫;
    }
    
    public void set吸取HP動畫值(final int i) {
        this._吸取HP動畫 = i;
    }
    
    public int get吸取MP機率() {
        return this._吸取MP機率;
    }
    
    public void set吸取MP機率(final int i) {
        this._吸取MP機率 = i;
    }
    
    public int get吸取MP固定值() {
        return this._吸取MP固定值;
    }
    
    public void set吸取MP固定值(final int i) {
        this._吸取MP固定值 = i;
    }
    
    public int get吸取MP隨機值() {
        return this._吸取MP隨機值;
    }
    
    public void set吸取MP隨機值(final int i) {
        this._吸取MP隨機值 = i;
    }
    
    public int get吸取MP動畫() {
        return this._吸取MP動畫;
    }
    
    public void set吸取MP動畫值(final int i) {
        this._吸取MP動畫 = i;
    }
    
    public int get攻擊力發動機率() {
        return this._攻擊力發動機率;
    }
    
    public void set攻擊力發動機率(final int i) {
        this._攻擊力發動機率 = i;
    }
    
    public int get攻擊力固定值() {
        return this._攻擊力固定值;
    }
    
    public void set攻擊力固定值(final int i) {
        this._攻擊力固定值 = i;
    }
    
    public int get攻擊力隨機值() {
        return this._攻擊力隨機值;
    }
    
    public void set攻擊力隨機值(final int i) {
        this._攻擊力隨機值 = i;
    }
    
    public int get攻擊力動畫() {
        return this._攻擊力動畫;
    }
    
    public void set攻擊力動畫(final int i) {
        this._攻擊力動畫 = i;
    }
    
    public int getdollcheck() {
        return this._dollcheck;
    }
    
    public void setdollcheck(final int i) {
        this._dollcheck = i;
    }
    
    public void adddollcheck(final int i) {
        this._dollcheck += i;
    }
    
    public int getdollcheckitem() {
        return this._dollcheckitem;
    }
    
    public void setdollcheckitem(final int i) {
        this._dollcheckitem = i;
    }
    
    public void adddollcheckitem(final int i) {
        this._dollcheckitem += i;
    }
    
    public int getdollcheck1() {
        return this._dollcheck1;
    }
    
    public void setdollcheck1(final int i) {
        this._dollcheck1 = i;
    }
    
    public void adddollcheck1(final int i) {
        this._dollcheck1 += i;
    }
    
    public int getdollcheckitem1() {
        return this._dollcheckitem1;
    }
    
    public void setdollcheckitem1(final int i) {
        this._dollcheckitem1 = i;
    }
    
    public void adddollcheckitem1(final int i) {
        this._dollcheckitem1 += i;
    }
    
    public int getcomtext0() {
        return this._comtext0;
    }
    
    public void setcomtext0(final int i) {
        this._comtext0 = i;
    }
    
    public String getcomtextc0() {
        return this._comtextc0;
    }
    
    public void setcomtextc0(final String s) {
        this._comtextc0 = s;
    }
    
    public int getcomtext1() {
        return this._comtext1;
    }
    
    public void setcomtext1(final int i) {
        this._comtext1 = i;
    }
    
    public String getcomtextc1() {
        return this._comtextc1;
    }
    
    public void setcomtextc1(final String s) {
        this._comtextc1 = s;
    }
    
    public int getcomtext2() {
        return this._comtext2;
    }
    
    public void setcomtext2(final int i) {
        this._comtext2 = i;
    }
    
    public String getcomtextc2() {
        return this._comtextc2;
    }
    
    public void setcomtextc2(final String s) {
        this._comtextc2 = s;
    }
    
    public int getcomtext3() {
        return this._comtext3;
    }
    
    public void setcomtext3(final int i) {
        this._comtext3 = i;
    }
    
    public String getcomtextc3() {
        return this._comtextc3;
    }
    
    public void setcomtextc3(final String s) {
        this._comtextc3 = s;
    }
    
    public int getcomtext4() {
        return this._comtext4;
    }
    
    public void setcomtext4(final int i) {
        this._comtext4 = i;
    }
    
    public String getcomtextc4() {
        return this._comtextc4;
    }
    
    public void setcomtextc4(final String s) {
        this._comtextc4 = s;
    }
    
    public int getcomtext5() {
        return this._comtext5;
    }
    
    public void setcomtext5(final int i) {
        this._comtext5 = i;
    }
    
    public String getcomtextc5() {
        return this._comtextc5;
    }
    
    public void setcomtextc5(final String s) {
        this._comtextc5 = s;
    }
    
    public int getcomtext6() {
        return this._comtext6;
    }
    
    public void setcomtext6(final int i) {
        this._comtext6 = i;
    }
    
    public String getcomtextc6() {
        return this._comtextc6;
    }
    
    public void setcomtextc6(final String s) {
        this._comtextc6 = s;
    }
    
    public int getcomtext7() {
        return this._comtext7;
    }
    
    public void setcomtext7(final int i) {
        this._comtext7 = i;
    }
    
    public String getcomtextc7() {
        return this._comtextc7;
    }
    
    public void setcomtextc7(final String s) {
        this._comtextc7 = s;
    }
    
    public int getcomtext8() {
        return this._comtext8;
    }
    
    public void setcomtext8(final int i) {
        this._comtext8 = i;
    }
    
    public String getcomtextc8() {
        return this._comtextc8;
    }
    
    public void setcomtextc8(final String s) {
        this._comtextc8 = s;
    }
    
    public int getcomtext9() {
        return this._comtext9;
    }
    
    public void setcomtext9(final int i) {
        this._comtext9 = i;
    }
    
    public String getcomtextc9() {
        return this._comtextc9;
    }
    
    public void setcomtextc9(final String s) {
        this._comtextc9 = s;
    }
    
    public int getcomtext10() {
        return this._comtext10;
    }
    
    public void setcomtext10(final int i) {
        this._comtext10 = i;
    }
    
    public String getcomtextc10() {
        return this._comtextc10;
    }
    
    public void setcomtextc10(final String s) {
        this._comtextc10 = s;
    }
    
    public int getcomtext11() {
        return this._comtext11;
    }
    
    public void setcomtext11(final int i) {
        this._comtext11 = i;
    }
    
    public String getcomtextc11() {
        return this._comtextc11;
    }
    
    public void setcomtextc11(final String s) {
        this._comtextc11 = s;
    }
    
    public int getcomtext12() {
        return this._comtext12;
    }
    
    public void setcomtext12(final int i) {
        this._comtext12 = i;
    }
    
    public String getcomtextc12() {
        return this._comtextc12;
    }
    
    public void setcomtextc12(final String s) {
        this._comtextc12 = s;
    }
    
    public int getcomtext13() {
        return this._comtext13;
    }
    
    public void setcomtext13(final int i) {
        this._comtext13 = i;
    }
    
    public String getcomtextc13() {
        return this._comtextc13;
    }
    
    public void setcomtextc13(final String s) {
        this._comtextc13 = s;
    }
    
    public int getcomtext14() {
        return this._comtext14;
    }
    
    public void setcomtext14(final int i) {
        this._comtext14 = i;
    }
    
    public String getcomtextc14() {
        return this._comtextc14;
    }
    
    public void setcomtextc14(final String s) {
        this._comtextc14 = s;
    }
    
    public int getcomtext15() {
        return this._comtext15;
    }
    
    public void setcomtext15(final int i) {
        this._comtext15 = i;
    }
    
    public String getcomtextc15() {
        return this._comtextc15;
    }
    
    public void setcomtextc15(final String s) {
        this._comtextc15 = s;
    }
    
    public int getcomtext16() {
        return this._comtext16;
    }
    
    public void setcomtext16(final int i) {
        this._comtext16 = i;
    }
    
    public String getcomtextc16() {
        return this._comtextc16;
    }
    
    public void setcomtextc16(final String s) {
        this._comtextc16 = s;
    }
    
    public int getcomtext17() {
        return this._comtext17;
    }
    
    public void setcomtext17(final int i) {
        this._comtext17 = i;
    }
    
    public String getcomtextc17() {
        return this._comtextc17;
    }
    
    public void setcomtextc17(final String s) {
        this._comtextc17 = s;
    }
    
    public int getcomtext18() {
        return this._comtext18;
    }
    
    public void setcomtext18(final int i) {
        this._comtext18 = i;
    }
    
    public String getcomtextc18() {
        return this._comtextc18;
    }
    
    public void setcomtextc18(final String s) {
        this._comtextc18 = s;
    }
    
    public int getcomtext19() {
        return this._comtext19;
    }
    
    public void setcomtext19(final int i) {
        this._comtext19 = i;
    }
    
    public String getcomtextc19() {
        return this._comtextc19;
    }
    
    public void setcomtextc19(final String s) {
        this._comtextc19 = s;
    }
    
    public int getcomtext20() {
        return this._comtext20;
    }
    
    public void setcomtext20(final int i) {
        this._comtext20 = i;
    }
    
    public String getcomtextc20() {
        return this._comtextc20;
    }
    
    public void setcomtextc20(final String s) {
        this._comtextc20 = s;
    }
    
    public int getpolyactidran() {
        return this._polyactidran;
    }
    
    public void setpolyactidran(final int i) {
        this._polyactidran = i;
    }
    
    public int getpolyactiddmg() {
        return this._polyactiddmg;
    }
    
    public void setpolyactiddmg(final int i) {
        this._polyactiddmg = i;
    }
    
    public int getpolyactiddmg1() {
        return this._polyactiddmg1;
    }
    
    public void setpolyactiddmg1(final int i) {
        this._polyactiddmg1 = i;
    }
    
    public int getpolyactidactid() {
        return this._polyactidactid;
    }
    
    public void setpolyactidactid(final int i) {
        this._polyactidactid = i;
    }
    
    public int getcardspoly() {
        return this._cardspoly;
    }
    
    public void setcardspoly(final int i) {
        this._cardspoly = i;
    }
    
    public int getpolycheck() {
        return this._polycheck;
    }
    
    public void setpolycheck(final int i) {
        this._polycheck = i;
    }
    
    public int getarmortype(final int i) {
        return this._armortype[i];
    }
    
    public void setarmortype(final int i, final int j) {
        this._armortype[i] = j;
    }
    
    public int getadenapoint() {
        return this._adenapoint;
    }
    
    public void setadenapoint(final int i) {
        this._adenapoint = i;
    }
    
    public void addadenapoint(final int i) {
        this._adenapoint += i;
    }
    
    public int getweaponchp() {
        return this._weaponchp;
    }
    
    public void setweaponchp(final int i) {
        this._weaponchp = i;
    }
    
    public int getweaponcmp() {
        return this._weaponcmp;
    }
    
    public void setweaponcmp(final int i) {
        this._weaponcmp = i;
    }
    
    public int getmagicdmg() {
        return this._magicdmg;
    }
    
    public void setmagicdmg(final int i) {
        this._magicdmg = i;
    }
    
    public int getweaponran() {
        return this._weaponran;
    }
    
    public void setweaponran(final int i) {
        this._weaponran = i;
    }
    
    public int getweapongfx() {
        return this._weapongfx;
    }
    
    public void setweapongfx(final int i) {
        this._weapongfx = i;
    }
    
    public void setDeclareId(final int i) {
        this.declareId = i;
    }
    
    public int getDeclareId() {
        return this.declareId;
    }
    
    public int getIceTime() {
        return this._iceTime;
    }
    
    public void setIceTime(final int time) {
        this._iceTime = time;
    }
    
    public int getopendoll() {
        return this.opendoll;
    }
    
    public void setopendoll(final int opendoll) {
        this.opendoll = opendoll;
    }
    
    public String get_savetitle() {
        return this._savetitle;
    }
    
    public void set_savetitle(final String s) {
        this._savetitle = s;
    }
    
    public int getrantitle() {
        return this.rantitle;
    }
    
    public void setrantitle(final int rantitle) {
        this.rantitle = rantitle;
    }
    
    public int getaierrorcheck() {
        return this._aierrorcheck;
    }
    
    public void setaierrorcheck(final int i) {
        this._aierrorcheck = i;
    }
    
    public void addaierrorcheck(final int i) {
        this._aierrorcheck += i;
    }
    
    public int get_eqconitem() {
        return this._eqconitem;
    }
    
    public void set_eqconitem(final int i) {
        this._eqconitem = i;
    }
    
    public void add_eqconitem(final int i) {
        this._eqconitem += i;
    }
    
    public boolean getvviipp() {
        return this._vviipp;
    }
    
    public String getcheckip() {
        return this._checkip;
    }
    
    public void setcheckip(final String s) {
        this._checkip = s;
    }
    
    public String getcheckip1() {
        return this._checkip1;
    }
    
    public void setcheckip1(final String s) {
        this._checkip1 = s;
    }
    
    public boolean is_bounce_attack() {
        return this._is_bounce_attack;
    }
    
    public void set_bounce_attack(final boolean flg) {
        this._is_bounce_attack = true;
    }
    
    private boolean _disconnect = false;
	 
	 /**
	  * 離開遊戲
	  * @return
	  */
	 public boolean IsDisconnect() {
	  return _disconnect;
	 }
	 
	 /**
	  * 離開遊戲
	  * @param b
	  */
	 public void setDisconnect(boolean b) {
	  _disconnect = b;
	 }

		private ArrayList<String> _ds = new ArrayList<String>();

		public ArrayList<String> ds() {
			return _ds;
		}
    
    private class Death implements Runnable
    {
        private L1Character _lastAttacker;
        
        private Death(final L1Character cha) {
            this._lastAttacker = cha;
        }

		@Override
        public void run() {
            final L1Character lastAttacker = this._lastAttacker;
            this._lastAttacker = null;
            L1PcInstance.this.setCurrentHp(0);
            L1PcInstance.this.setCurrentHp(0);
            L1PcInstance.this.setGresValid(false);
            while (L1PcInstance.this.isTeleport()) {
                try {
                    Thread.sleep(300L);
                }
                catch (Exception ex) {}
            }
            if (L1PcInstance.this.isInParty()) {
                for (final L1PcInstance member : L1PcInstance.this.getParty().partyUsers().values()) {
                    member.sendPackets(new S_PacketBoxParty(L1PcInstance.this.getParty(), L1PcInstance.this));
                }
            }
            if (L1PcInstance.this.isActived()) {
                L1PcInstance enemy = null;
                if (lastAttacker instanceof L1PcInstance) {
                    enemy = (L1PcInstance)lastAttacker;
                    if (enemy != null) {
                        NewAutoPractice.get().AddAutoList(L1PcInstance.this, enemy);
                    }
                }
                L1PcInstance.this.setActived(false);
            }
            L1PcInstance.this.set_delete_time(180);
            L1PcInstance.this.killSkillEffectTimer(1000);
            if (L1PcInstance.this.getnpcdmg() > 0.0) {
                L1PcInstance.this.sendPackets(new S_SystemMessage("\\fU因您死亡,攻擊傷害累積重新計算"));
                L1PcInstance.this.setnpcdmg(0.0);
                L1PcInstance.this.setnpciddmg(0);
            }
            if (!L1PcInstance.this.getPetList().isEmpty()) {
                final Object[] arrayOfObject;
                final int i = (arrayOfObject = L1PcInstance.this.getPetList().values().toArray()).length;
                for (byte b = 0; b < i; ++b) {
                    final Object petList = arrayOfObject[b];
                    if (petList instanceof L1SummonInstance) {
                        final L1SummonInstance summon = (L1SummonInstance)petList;
                        final S_NewMaster packet = new S_NewMaster(summon);
                        if (summon != null) {
                            if (summon.destroyed()) {
                                return;
                            }
                            summon.Death(null);
                        }
                    }
                }
            }
            if (!L1PcInstance.this.getDolls().isEmpty()) {
                final Object[] arrayOfObject2;
                final int i = (arrayOfObject2 = L1PcInstance.this.getDolls().values().toArray()).length;
                for (byte b2 = 0; b2 < i; ++b2) {
                    final Object obj = arrayOfObject2[b2];
                    final L1DollInstance doll = (L1DollInstance)obj;
                    doll.deleteDoll();
                }
            }
            if (L1PcInstance.this.getHierarchs() != null) {
                L1PcInstance.this.getHierarchs().deleteHierarch();
            }
            L1PcInstance.this.stopHpRegeneration();
            L1PcInstance.this.stopMpRegeneration();
            L1PcInstance.this.getMap().setPassable(L1PcInstance.this.getLocation(), true);
            int tempchargfx = 0;
            if (L1PcInstance.this.hasSkillEffect(67)) {
                tempchargfx = L1PcInstance.this.getTempCharGfx();
                L1PcInstance.this.setTempCharGfxAtDead(tempchargfx);
            }
            else {
                L1PcInstance.this.setTempCharGfxAtDead(L1PcInstance.this.getClassId());
            }
            final L1SkillUse l1skilluse = new L1SkillUse();
            l1skilluse.handleCommands(L1PcInstance.this, 44, L1PcInstance.this.getId(), L1PcInstance.this.getX(), L1PcInstance.this.getY(), 0, 1);
            if (tempchargfx != 0) {
                L1PcInstance.this.sendPacketsAll(new S_ChangeShape(L1PcInstance.this, tempchargfx));
            }
            else {
                try {
                    Thread.sleep(1000L);
                }
                catch (Exception ex2) {}
            }
            L1PcInstance.this.sendPacketsAll(new S_DoActionGFX(L1PcInstance.this.getId(), 8));
            final L1EffectInstance tomb = L1SpawnUtil.spawnEffect(86126, 300, L1PcInstance.this.getX(), L1PcInstance.this.getY(), L1PcInstance.this.getMapId(), L1PcInstance.this, 0);
            L1PcInstance.this.set_tomb(tomb);
            boolean isSafetyZone = false;
            boolean isCombatZone = false;
            boolean isWar = false;
            if (L1PcInstance.this.isSafetyZone()) {
                isSafetyZone = true;
            }
            if (L1PcInstance.this.isCombatZone()) {
                isCombatZone = true;
            }
            if (lastAttacker instanceof L1GuardInstance) {
                if (L1PcInstance.this.get_PKcount() > 0) {
                    L1PcInstance.this.set_PKcount(L1PcInstance.this.get_PKcount() - 1);
                }
                L1PcInstance.this.setLastPk(null);
            }
            if (lastAttacker instanceof L1GuardianInstance) {
                if (L1PcInstance.this.getPkCountForElf() > 0) {
                    L1PcInstance.this.setPkCountForElf(L1PcInstance.this.getPkCountForElf() - 1);
                }
                L1PcInstance.this.setLastPkForElf(null);
            }
            L1PcInstance fightPc = null;
            if (lastAttacker instanceof L1PcInstance) {
                fightPc = (L1PcInstance)lastAttacker;
            }
            else if (lastAttacker instanceof L1PetInstance) {
                final L1PetInstance npc = (L1PetInstance)lastAttacker;
                if (npc.getMaster() != null) {
                    npc.addLawful(-10000);
                    final L1Pet petTemplate1 = PetReading.get().getTemplate(npc.getItemObjId());
                    petTemplate1.set_lawful(npc.getLawful());
                    PetReading.get().storePet(petTemplate1);
                    L1PcInstance.this.sendPacketsAll(new S_NPCPack_Pet1(npc));
                }
            }
            else if (lastAttacker instanceof L1SummonInstance) {
                final L1SummonInstance npc2 = (L1SummonInstance)lastAttacker;
                if (npc2.getMaster() != null) {
                    fightPc = (L1PcInstance)npc2.getMaster();
                }
            }
            else if (lastAttacker instanceof L1IllusoryInstance) {
                final L1IllusoryInstance npc3 = (L1IllusoryInstance)lastAttacker;
                if (npc3.getMaster() != null) {
                    fightPc = (L1PcInstance)npc3.getMaster();
                }
            }
            else if (lastAttacker instanceof L1EffectInstance) {
                final L1EffectInstance npc4 = (L1EffectInstance)lastAttacker;
                if (npc4.getMaster() != null) {
                    fightPc = (L1PcInstance)npc4.getMaster();
                }
            }
            if (ConfigOther.prcheck && fightPc != null) {
                if (L1PcInstance.this.isSafetyZone()) {
                    return;
                }
                if (L1PcInstance.this.isCombatZone()) {
                    return;
                }
                if (fightPc.isCombatZone()) {
                    return;
                }
                if (ConfigOther.prchecktype == 1) {
                    if (L1PcInstance.this.getPrestige() > 0) {
                        L1PcInstance.this.addPrestige(-ConfigOther.prcheckcount1);
                        L1PcInstance.this.sendPackets(new S_ServerMessage("死亡噴威望:" + ConfigOther.prcheckcount1 + "目前威望:" + L1PcInstance.this.getPrestige()));
                    }
                }
                else if (ConfigOther.prchecktype == 2 && L1PcInstance.this.getPrestige() > 0) {
                    L1PcInstance.this.addPrestige(-ConfigOther.prcheckcount2);
                    L1PcInstance.this.sendPackets(new S_ServerMessage("被對方打噴威望:" + ConfigOther.prcheckcount2 + "目前威望:" + L1PcInstance.this.getPrestige()));
                    fightPc.addPrestige(ConfigOther.prcheckcount3);
                    fightPc.sendPackets(new S_ServerMessage("對方死亡獲得威望:" + ConfigOther.prcheckcount3 + "目前威望:" + fightPc.getPrestige()));
                }
            }
            if (fightPc != null) {
                if (L1PcInstance.this.getFightId() == fightPc.getId() && fightPc.getFightId() == L1PcInstance.this.getId()) {
                    L1PcInstance.this.setFightId(0);
                    L1PcInstance.this.sendPackets(new S_PacketBox(5, 0, 0));
                    fightPc.setFightId(0);
                    fightPc.sendPackets(new S_PacketBox(5, 0, 0));
                    return;
                }
                if (L1PcInstance.this.castleWarResult()) {
                    isWar = true;
                }
                if (L1PcInstance.this.simWarResult(lastAttacker)) {
                    isWar = true;
                }
                if (L1PcInstance.this.isInWarAreaAndWarTime(L1PcInstance.this, fightPc)) {
                    isWar = true;
                }
                NewAutoPractice.get().AddAutoList(L1PcInstance.this, fightPc);
                if (L1PcInstance.this.getLevel() >= ConfigOther.killlevel) {
                    boolean isShow = false;
                    if (isWar) {
                        isShow = true;
                    }
                    else if (!L1PcInstance.this.isSafetyZone()) {
                        isShow = true;
                    }
                    if (isShow && !L1PcInstance.this.isGm()) {
                        if (fightPc.getWeapon() != null) {
                            ConfigKill.get().msg(L1PcInstance.this.getName(), fightPc.getName(), fightPc.getWeapon().getViewName());
                        }
                        else {
                            ConfigKill.get().msgnoweapon(fightPc.getName(), L1PcInstance.this.getName(), "空手");
                        }
                        RecordTable.get().killpc(fightPc.getName(), L1PcInstance.this.getName());
                    }
                }
                fightPc.get_other().add_killCount(1);
                L1PcInstance.this.get_other().add_deathCount(1);
            }
            if (isSafetyZone && !(lastAttacker instanceof L1MonsterInstance)) {
                return;
            }
            if (isCombatZone && !(lastAttacker instanceof L1MonsterInstance)) {
                return;
            }
            if (!L1PcInstance.this.getMap().isEnabledDeathPenalty()) {
                return;
            }
            final boolean castle_area = L1CastleLocation.checkInAllWarArea(L1PcInstance.this.getX(), L1PcInstance.this.getY(), L1PcInstance.this.getMapId());
            if (L1PcInstance.this.castleWarResult() && !ConfigAlt.ALT_WARPUNISHMENT) {
                return;
            }
            this.c1TypeRate(fightPc);
            if (fightPc != null) {
                this.expRate();
            }
            else {
                this.expRateNPC();
            }
            if (ItemSteallok.START) {
                L1PcInstance.this.checkItemSteal1();
            }
            if (taketreasure.START && fightPc != null) {
                L1PcInstance.this.checkItemSteal(fightPc);
            }
//            if (!Configdead.deaddropitem && !L1PcInstance.this.castleWarResult() && !castle_area && L1PcInstance.this.getLawful() != 32767 && !L1PcInstance.this.isProtector()) {
//                this.lostRate();
//            }
//            if (Configdead.deaddropitem && !L1PcInstance.this.castleWarResult() && !castle_area && !L1PcInstance.this.isProtector() && L1PcInstance.this.getLevel() > Configdead.deadleveldrop) {
//                this.lostRate();
//            }
//            if (!Configdead.deaddropskill && !L1PcInstance.this.castleWarResult() && !castle_area && L1PcInstance.this.getLawful() != 32767 && !L1PcInstance.this.isProtector()) {
//                this.lostSkillRate();
//            }
//            if (Configdead.deaddropskill && !L1PcInstance.this.castleWarResult() && !castle_area && !L1PcInstance.this.isProtector() && L1PcInstance.this.getLevel() > Configdead.deadleveldrop) {
//                this.lostSkillRate();
//            }
            if (!Configdead.deaddropitem && !L1PcInstance.this.castleWarResult() && L1PcInstance.this.getLawful() != 32767 && !L1PcInstance.this.isProtector()) {
                this.lostRate();
            }
            if (Configdead.deaddropitem && !L1PcInstance.this.castleWarResult() && !L1PcInstance.this.isProtector() && L1PcInstance.this.getLevel() > Configdead.deadleveldrop) {
                this.lostRate();
            }
            if (!Configdead.deaddropskill && !L1PcInstance.this.castleWarResult() && L1PcInstance.this.getLawful() != 32767 && !L1PcInstance.this.isProtector()) {
                this.lostSkillRate();
            }
            if (Configdead.deaddropskill && !L1PcInstance.this.castleWarResult() && !L1PcInstance.this.isProtector() && L1PcInstance.this.getLevel() > Configdead.deadleveldrop) {
                this.lostSkillRate();
            }
            if (fightPc != null) {
                if (isWar) {
                    return;
                }
                if (fightPc.getClan() != null && L1PcInstance.this.getClan() != null && WorldWar.get().isWar(fightPc.getClan().getClanName(), L1PcInstance.this.getClan().getClanName())) {
                    return;
                }
                if (fightPc.isSafetyZone()) {
                    return;
                }
                if (fightPc.isCombatZone()) {
                    return;
                }
                if (Configdead.pkpinkcheck) {
                    if (isWar) {
                        return;
                    }
                    if (fightPc.getClan() != null && L1PcInstance.this.getClan() != null && WorldWar.get().isWar(fightPc.getClan().getClanName(), L1PcInstance.this.getClan().getClanName())) {
                        return;
                    }
                    if (fightPc.isSafetyZone()) {
                        return;
                    }
                    if (fightPc.isCombatZone()) {
                        return;
                    }
                    boolean isChangePkCount = false;
                    if (fightPc.getLawful() < 30000) {
                        fightPc.set_PKcount(fightPc.get_PKcount() + 1);
                        isChangePkCount = true;
                        if (fightPc.isElf() && L1PcInstance.this.isElf()) {
                            fightPc.setPkCountForElf(fightPc.getPkCountForElf() + 1);
                        }
                    }
                    fightPc.setLastPk();
                    if (fightPc.getLawful() == 32767) {
                        fightPc.setLastPk(null);
                    }
                    if (fightPc.isElf() && L1PcInstance.this.isElf()) {
                        fightPc.setLastPkForElf();
                    }
                    int lawful = fightPc.getLawful();
                    if (fightPc.getLawful() <= 0) {
                        lawful = fightPc.getLawful() - Configdead.KillTargetlawful;
                    }
                    if (fightPc.getLawful() >= 0) {
                        lawful = -1 * Configdead.KillTargetlawful;
                    }
                    if (lawful <= -32768) {
                        lawful = -32768;
                    }
                    fightPc.setLawful(lawful);
                    fightPc.sendPacketsAll(new S_Lawful(fightPc));
                    if (ConfigAlt.ALT_PUNISHMENT) {
                        if (isChangePkCount && fightPc.get_PKcount() >= 5 && fightPc.get_PKcount() < 100) {
                            fightPc.sendPackets(new S_BlueMessage(551, String.valueOf(fightPc.get_PKcount()), "100"));
                        }
                        else if (isChangePkCount && fightPc.get_PKcount() >= 100) {
                            fightPc.beginHell(true);
                        }
                    }
                }
                else if (L1PcInstance.this.getLawful() >= 0 && !L1PcInstance.this.hasSkillEffect(5122) && !Configdead.pkpinkcheck) {
                    boolean isChangePkCount2 = false;
                    if (fightPc.getLawful() < 30000) {
                        fightPc.set_PKcount(fightPc.get_PKcount() + 1);
                        isChangePkCount2 = true;
                        if (fightPc.isElf() && L1PcInstance.this.isElf()) {
                            fightPc.setPkCountForElf(fightPc.getPkCountForElf() + 1);
                        }
                    }
                    fightPc.setLastPk();
                    if (fightPc.getLawful() == 32767) {
                        fightPc.setLastPk(null);
                    }
                    if (fightPc.isElf() && L1PcInstance.this.isElf()) {
                        fightPc.setLastPkForElf();
                    }
                    int lawful = fightPc.getLawful();
                    if (fightPc.getLawful() < 0) {
                        lawful = fightPc.getLawful() - Configdead.KillTargetlawful;
                    }
                    if (fightPc.getLawful() > 0) {
                        lawful = -1 * Configdead.KillTargetlawful;
                    }
                    if (lawful <= -32768) {
                        lawful = -32768;
                    }
                    fightPc.setLawful(lawful);
                    fightPc.sendPacketsAll(new S_Lawful(fightPc));
                    if (ConfigAlt.ALT_PUNISHMENT) {
                        if (isChangePkCount2 && fightPc.get_PKcount() >= 5 && fightPc.get_PKcount() < 100) {
                            fightPc.sendPackets(new S_BlueMessage(551, String.valueOf(fightPc.get_PKcount()), "100"));
                        }
                        else if (isChangePkCount2 && fightPc.get_PKcount() >= 100) {
                            fightPc.beginHell(true);
                        }
                    }
                }
                else {
                    L1PcInstance.this.setPinkName(false);
                }
            }
        }
        
        private void c1TypeRate(final L1Character lastAttacker) {
            L1PcInstance attacker = null;
            if (CampSet.CAMPSTART && L1PcInstance.this._c_power != null && L1PcInstance.this._c_power.get_c1_type() != 0 && L1PcInstance.this._c_power.get_c1_type() != 0) {
                final L1ItemInstance item1 = L1PcInstance.this.getInventory().checkItemX(44165, 1L);
                if (item1 != null) {
                    L1PcInstance.this.getInventory().removeItem(item1, 1L);
                    L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU你身上帶有" + item1.getName() + ",陣營積分受到守護!"));
                    return;
                }
                final L1Name_Power power = L1PcInstance.this._c_power.get_power();
                final int score = L1PcInstance.this._other.get_score() - power.get_down();
                if (score > 0) {
                    L1PcInstance.this._other.set_score(score);
                    L1PcInstance.this.sendPackets(new S_ServerMessage(String.valueOf(String.valueOf(String.valueOf(String.valueOf(L1WilliamSystemMessage.ShowMessage(8))))) + power.get_down()));
                    if (score < 0) {
                        L1PcInstance.this._other.set_score(0);
                    }
                    if (power.get_getscore() > 0 && lastAttacker instanceof L1PcInstance) {
                        attacker = (L1PcInstance)lastAttacker;
                        if (attacker._other.get_score() > 0) {
                            attacker._other.set_score(attacker._other.get_score() + power.get_getscore());
                            if (attacker._other.get_score() < 0) {
                                attacker._other.set_score(0);
                            }
                            L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU您被搶奪陣營積分:" + power.get_getscore()));
                            attacker.sendPackets(new S_ServerMessage("\\fU您搶奪陣營積分獲得:" + power.get_getscore()));
                        }
                    }
                }
                else {
                    L1PcInstance.this._other.set_score(0);
                    L1PcInstance.this.sendPackets(new S_ServerMessage(L1WilliamSystemMessage.ShowMessage(9)));
                }
                final int lv = C1_Name_Type_Table.get().getLv(L1PcInstance.this._c_power.get_c1_type(), L1PcInstance.this._other.get_score());
                if (lv != L1PcInstance.this._c_power.get_power().get_c1_id()) {
                    L1PcInstance.this._c_power.set_power(L1PcInstance.this, false);
                    L1PcInstance.this.sendPackets(new S_ServerMessage("\\fR階級變更:" + L1PcInstance.this._c_power.get_power().get_c1_name_type()));
                    L1PcInstance.this.sendPacketsAll(new S_ChangeName(L1PcInstance.this, true));
                }
            }
        }
        /*怪物*/
        private void expRateNPC() {
            if (L1PcInstance.this.isProtector() && !ProtectorSet.DEATH_VALUE_EXP) {
                return;
            }
            if (L1PcInstance.this._isCraftsmanHeirloom()) {
                return;
            }
            if (L1PcInstance.this._vip_2) {
                L1PcInstance.this.sendPackets(new S_ServerMessage("受到經驗加值卡的保護,死亡不損失經驗。"));
                return;
            }
            final L1ItemInstance item1 = L1PcInstance.this.getInventory().checkItemX(44164, 1L);
            if (item1 != null) {
                L1PcInstance.this.getInventory().removeItem(item1, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item1.getName() + "的關係,因此經驗受到保護。"));
                return;
            }
            final L1ItemInstance item2 = L1PcInstance.this.getInventory().checkItemX(44147, 1L);
            if (item2 != null) {
                L1PcInstance.this.getInventory().removeItem(item2, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item2.getName() + "的關係,因此經驗受到保護。"));
                return;
            }
            final L1ItemInstance item3 = L1PcInstance.this.getInventory().findItemId(92691);
            if (item3 != null && L1PcInstance.this.getInventory().checkEquipped(92691)) { //裝備狀態
                L1PcInstance.this.getInventory().removeItem(item3, 1L);
                L1PcInstance.this.getInventory().storeItem(92690, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item3.getName() + "的關係,因此經驗受到保護。"));
                return;
            }
            if (L1PcInstance.this.hasSkillEffect(8000) && L1PcInstance.this.getMapId() == 537) {
                L1PcInstance.this.killSkillEffectTimer(8000);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU受到祝福之光的保護,剛剛死掉沒有掉%。"));
                return;
            }
            if (L1WilliamGfxIdOrginal.DeadExp(L1PcInstance.this.getTempCharGfx())) {
                L1PcInstance.this.setExpRes(1);
                return;
            }
            L1PcInstance.this.deathPenalty();
            L1PcInstance.this.setGresValid(true);
            if (L1PcInstance.this.getExpRes() == 0) {
                L1PcInstance.this.setExpRes(1);
            }
        }
        /*玩家*/
        private void expRate() {
            if (L1PcInstance.this.isProtector() && !ProtectorSet.DEATH_VALUE_EXP) {
                return;
            }
            if (L1PcInstance.this._isCraftsmanHeirloom()) {
                return;
            }
            if (L1PcInstance.this._vip_2) {
                L1PcInstance.this.sendPackets(new S_ServerMessage("受到經驗加值卡的保護,死亡不損失經驗。"));
                return;
            }
            final L1ItemInstance item1 = L1PcInstance.this.getInventory().checkItemX(44164, 1L);
            if (item1 != null) {
                L1PcInstance.this.getInventory().removeItem(item1, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item1.getName() + "的關係,因此經驗受到保護。"));
                return;
            }
            final L1ItemInstance item3 = L1PcInstance.this.getInventory().findItemId(92692);
            if (item3 != null && L1PcInstance.this.getInventory().checkEquipped(92692)) { //裝備狀態
//                L1PcInstance.this.getInventory().removeItem(item3, 1L);
//                L1PcInstance.this.getInventory().storeItem(92690, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item3.getName() + "的關係,因此經驗受到保護。"));
                return;
            }
            if (L1PcInstance.this.hasSkillEffect(8000) && L1PcInstance.this.getMapId() == 537) {
                L1PcInstance.this.killSkillEffectTimer(8000);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU受到祝福之光的保護,剛剛死掉沒有掉%。"));
                return;
            }
            if (L1WilliamGfxIdOrginal.DeadExp(L1PcInstance.this.getTempCharGfx())) {
                L1PcInstance.this.setExpRes(1);
                return;
            }
            L1PcInstance.this.deathPenalty();
            L1PcInstance.this.setGresValid(true);
            if (L1PcInstance.this.getExpRes() == 0) {
                L1PcInstance.this.setExpRes(1);
            }
        }
        
        private void lostRate() {
            if (L1PcInstance.this._isCraftsmanHeirloom()) {
                return;
            }
            if (L1PcInstance.this.getMap().isdropitem()) {
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU此地圖死亡不會噴出物品。"));
                return;
            }
            final L1ItemInstance item1 = L1PcInstance.this.getInventory().checkItemX(44163, 1L);
            if (item1 != null) {
                L1PcInstance.this.getInventory().removeItem(item1, 1L);
                L1PcInstance.this.sendPackets(new S_ServerMessage("\\fU" + item1.getName() + "的關係,因此裝備受到保護。"));
                return;
            }
            if (L1PcInstance.this.isProtector() && ProtectorSet.DEATH_VALUE_ITEM) {
                final L1ItemInstance item2 = L1PcInstance.this.getInventory().findItemId(ProtectorSet.ITEM_ID);
                if (item2 != null) {
                    item2.set_showId(L1PcInstance.this.get_showId());
                    final int x = L1PcInstance.this.getX();
                    final int y = L1PcInstance.this.getY();
                    final short m = L1PcInstance.this.getMapId();
                    L1PcInstance.this.getInventory().tradeItem(item2, item2.isStackable() ? item2.getCount() : 1L, World.get().getInventory(x, y, m));
                }
                L1PcInstance.this.sendPackets(new S_ServerMessage(638, item2.getLogName()));
                return;
            }
            if (Configdead.deaddropitem) {
                final int rnd = L1PcInstance._random.nextInt(100) + 1;
                int count = 0;
                final int lawful = L1PcInstance.this.getLawful();
                if (lawful >= 32767 && rnd <= Configdead.deaditemran1) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount1) + 1;
                }
                else if (lawful >= 0 && lawful <= 10000 && rnd <= Configdead.deaditemran2) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount2) + 1;
                }
                else if (lawful >= 10001 && lawful <= 20000 && rnd <= Configdead.deaditemran3) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount3) + 1;
                }
                else if (lawful >= 20001 && lawful <= 30000 && rnd <= Configdead.deaditemran4) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount4) + 1;
                }
                else if (lawful >= 30001 && lawful <= 32766 && rnd <= Configdead.deaditemran5) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount5) + 1;
                }
                else if (lawful < -1 && lawful >= -10000 && rnd <= Configdead.deaditemran6) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount6) + 1;
                }
                else if (lawful < -10000 && lawful >= -20000 && rnd <= Configdead.deaditemran7) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount7) + 1;
                }
                else if (lawful < -20000 && lawful >= -30000 && rnd <= Configdead.deaditemran8) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount8) + 1;
                }
                else if (lawful < -30000 && lawful >= -32768 && rnd <= Configdead.deaditemran9) {
                    count = L1PcInstance._random.nextInt(Configdead.deaditemcount9) + 1;
                }
                if (count > 0) {
                    L1PcInstance.this.caoPenaltyResult(count);
                }
            }
            else {
                int lostRate = (int)(((L1PcInstance.this.getLawful() + 32768.0) / 1000.0 - 65.0) * 4.0);
                if (lostRate < 0) {
                    lostRate *= -1;
                    if (L1PcInstance.this.getLawful() < 0) {
                        lostRate = 1000;
                    }
                    final int rnd2 = L1PcInstance._random.nextInt(1000) + 1;
                    if (rnd2 <= lostRate) {
                        int count2 = 0;
                        final int lawful2 = L1PcInstance.this.getLawful();
                        if (lawful2 >= -32768 && lawful2 <= -30000) {
                            count2 = L1PcInstance._random.nextInt(4) + 1;
                        }
                        else if (lawful2 > -30000 && lawful2 <= -20000) {
                            count2 = L1PcInstance._random.nextInt(3) + 1;
                        }
                        else if (lawful2 > -20000 && lawful2 <= -10000) {
                            count2 = L1PcInstance._random.nextInt(2) + 1;
                        }
                        else if (lawful2 > -10000 && lawful2 <= 32767) {
                            count2 = L1PcInstance._random.nextInt(1) + 1;
                        }
                        if (count2 > 0) {
                            L1PcInstance.this.caoPenaltyResult(count2);
                        }
                    }
                }
            }
        }
        
        private void lostSkillRate() {
            final int skillCount = L1PcInstance.this._skillList.size();
            int count = 0;
            if (Configdead.deaddropskill) {
                if (skillCount > 0) {
                    final int rnd = L1PcInstance._random.nextInt(100) + 1;
                    final int lawful = L1PcInstance.this.getLawful();
                    if (lawful >= 32767 && rnd <= Configdead.deadskillran1) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount1) + 1;
                    }
                    else if (lawful >= 0 && lawful <= 10000 && rnd <= Configdead.deadskillran2) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount2) + 1;
                    }
                    else if (lawful >= 10001 && lawful <= 20000 && rnd <= Configdead.deadskillran3) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount3) + 1;
                    }
                    else if (lawful >= 20001 && lawful <= 30000 && rnd <= Configdead.deadskillran4) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount4) + 1;
                    }
                    else if (lawful >= 30001 && lawful <= 32766 && rnd <= Configdead.deadskillran5) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount5) + 1;
                    }
                    else if (lawful < -1 && lawful >= -10000 && rnd <= Configdead.deadskillran6) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount6) + 1;
                    }
                    else if (lawful < -10000 && lawful >= -20000 && rnd <= Configdead.deadskillran7) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount7) + 1;
                    }
                    else if (lawful < -20000 && lawful >= -30000 && rnd <= Configdead.deadskillran8) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount8) + 1;
                    }
                    else if (lawful < -30000 && lawful >= -32768 && rnd <= Configdead.deadskillran9) {
                        count = L1PcInstance._random.nextInt(Configdead.deadskillcount9) + 1;
                    }
                    if (count > 0) {
                        L1PcInstance.this.delSkill(count);
                    }
                }
            }
            else if (skillCount > 0) {
                final int lawful2 = L1PcInstance.this.getLawful();
                if (lawful2 < 0 && lawful2 >= -32767) {
                    count = 1 + L1PcInstance._random.nextInt(2);
                }
                if (lawful2 == -32768) {
                    count = 1 + L1PcInstance._random.nextInt(3);
                }
                if (count > 0) {
                    L1PcInstance.this.delSkill(count);
                }
            }
        }
    }
}
