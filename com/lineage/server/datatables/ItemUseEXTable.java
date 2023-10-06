/*     */ package com.lineage.server.datatables;
/*     */ import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.sql.CharBuffTable;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_MPUpdate;
/*     */ import com.lineage.server.serverpackets.S_OwnCharAttrDef;
/*     */ import com.lineage.server.serverpackets.S_OwnCharStatus;
/*     */ import com.lineage.server.serverpackets.S_OwnCharStatus2;
/*     */ import com.lineage.server.serverpackets.S_SPMR;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1ItemUseEX;
/*     */ import com.lineage.server.utils.BinaryOutputStream;
/*     */ import com.lineage.server.utils.SQLUtil;

/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.List;
/*     */ import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class ItemUseEXTable {
/*  21 */   private static final Log _log = LogFactory.getLog(ItemUseEXTable.class);
/*  22 */   public static final Map<Integer, L1ItemUseEX> _list = new HashMap<>();
/*  23 */   public static final Map<Integer, ArrayList<Integer>> _type = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  33 */   private static final HashMap<Integer, BinaryOutputStream> _os = new HashMap<>();
/*     */   private static ItemUseEXTable _instance;
/*     */   
/*     */   public static ItemUseEXTable get() {
/*  37 */     if (_instance == null) {
/*  38 */       _instance = new ItemUseEXTable();
/*     */     }
/*  40 */     return _instance;
/*     */   }
/*     */   
/*     */   private ItemUseEXTable() {
/*  44 */     load();
/*     */   }
/*     */   
/*     */   private void load() {
/*  48 */     Connection co = null;
/*  49 */     PreparedStatement pstm = null;
/*  50 */     ResultSet rs = null;
/*     */     
/*     */     try {
/*  53 */       co = DatabaseFactory.get().getConnection();
/*  54 */       pstm = co.prepareStatement("SELECT * FROM `w_道具使用類別_1`");
/*  55 */       rs = pstm.executeQuery();
/*  56 */       while (rs.next()) {
/*  57 */         int item_id = rs.getInt("item_id");
/*  58 */         String itemName = rs.getString("item_name");
/*  59 */         String Message = rs.getString("Message");
/*  60 */         int type = rs.getInt("type");
/*  61 */         int type_mod = rs.getInt("type_mod");
/*  62 */         int buff_time = rs.getInt("has_time");
/*  63 */         int buff_gfx = rs.getInt("gfix");
/*  64 */         boolean buff_save = rs.getBoolean("buff_save");
/*  65 */         int poly = rs.getInt("poly");
/*  66 */         int cancellation = rs.getInt("cancellation");
/*  67 */         int add_str = rs.getInt("add_str");
/*  68 */         int add_dex = rs.getInt("add_dex");
/*  69 */         int add_con = rs.getInt("add_con");
/*  70 */         int add_int = rs.getInt("add_int");
/*  71 */         int add_wis = rs.getInt("add_wis");
/*  72 */         int add_cha = rs.getInt("add_cha");
/*  73 */         int add_ac = rs.getInt("add_ac");
/*  74 */         int add_hp = rs.getInt("add_hp");
/*  75 */         int add_mp = rs.getInt("add_mp");
/*  76 */         int add_hpr = rs.getInt("add_hpr");
/*  77 */         int add_mpr = rs.getInt("add_mpr");
/*  78 */         int add_dmg = rs.getInt("add_dmg");
/*  79 */         int add_hit = rs.getInt("add_hit");
/*  80 */         int add_bow_dmg = rs.getInt("add_bow_dmg");
/*  81 */         int add_bow_hit = rs.getInt("add_bow_hit");
/*  82 */         int add_dmg_r = rs.getInt("add_dmg_r");
/*  83 */         int add_magic_r = rs.getInt("add_magic_r");
/*  84 */         int add_mr = rs.getInt("add_mr");
/*  85 */         int add_sp = rs.getInt("add_sp");
/*  86 */         int add_fire = rs.getInt("add_fire");
/*  87 */         int add_wind = rs.getInt("add_wind");
/*  88 */         int add_earth = rs.getInt("add_earth");
/*  89 */         int add_water = rs.getInt("add_water");
/*  90 */         int add_stun = rs.getInt("add_stun");
/*  91 */         int add_stone = rs.getInt("add_stone");
/*  92 */         int add_sleep = rs.getInt("add_sleep");
/*  93 */         int add_freeze = rs.getInt("add_freeze");
/*  94 */         int add_sustain = rs.getInt("add_sustain");
/*  95 */         int add_blind = rs.getInt("add_blind");
/*  96 */         int double_score = rs.getInt("陣營積分加倍");
/*  97 */         int removeitem = rs.getInt("removeitem");
/*  98 */         String type_msg = rs.getString("type_msg");
/*  99 */         L1ItemUseEX vip = new L1ItemUseEX();
/* 100 */         vip.set_type(type);
/* 101 */         vip.setItem_name(itemName);
/* 102 */         vip.setMessage(Message);
/* 103 */         vip.set_type_mod(type_mod);
/* 104 */         vip.set_buff_time(buff_time);
/* 105 */         vip.set_buff_gfx(buff_gfx);
/* 106 */         vip.set_buff_save(buff_save);
/* 107 */         vip.set_type(type);
/* 108 */         vip.set_cancellation(cancellation);
/* 109 */         vip.set_poly(poly);
/* 110 */         vip.set_add_str(add_str);
/* 111 */         vip.set_add_dex(add_dex);
/* 112 */         vip.set_add_con(add_con);
/* 113 */         vip.set_add_int(add_int);
/* 114 */         vip.set_add_wis(add_wis);
/* 115 */         vip.set_add_cha(add_cha);
/* 116 */         vip.set_add_ac(add_ac);
/* 117 */         vip.set_add_hp(add_hp);
/* 118 */         vip.set_add_mp(add_mp);
/* 119 */         vip.set_add_hpr(add_hpr);
/* 120 */         vip.set_add_mpr(add_mpr);
/* 121 */         vip.set_add_dmg(add_dmg);
/* 122 */         vip.set_add_hit(add_hit);
/* 123 */         vip.set_add_bow_dmg(add_bow_dmg);
/* 124 */         vip.set_add_bow_hit(add_bow_hit);
/* 125 */         vip.set_add_dmg_r(add_dmg_r);
/* 126 */         vip.set_add_magic_r(add_magic_r);
/* 127 */         vip.set_add_mr(add_mr);
/* 128 */         vip.set_add_sp(add_sp);
/* 129 */         vip.set_add_fire(add_fire);
/* 130 */         vip.set_add_wind(add_wind);
/* 131 */         vip.set_add_earth(add_earth);
/* 132 */         vip.set_add_water(add_water);
/* 133 */         vip.set_add_stun(add_stun);
/* 134 */         vip.set_add_stone(add_stone);
/* 135 */         vip.set_add_sleep(add_sleep);
/* 136 */         vip.set_add_freeze(add_freeze);
/* 137 */         vip.set_add_sustain(add_sustain);
/* 138 */         vip.set_add_blind(add_blind);
/* 139 */         vip.settype_msg(type_msg);
/* 140 */         vip.set_double_score(double_score);
/* 141 */         vip.set_removeitem(removeitem);
/* 142 */         _list.put(Integer.valueOf(item_id), vip);
/* 143 */         ArrayList<Integer> map = _type.get(Integer.valueOf(type));
/* 144 */         if (map == null) {
/* 145 */           map = new ArrayList<>();
/* 146 */           map.add(Integer.valueOf(item_id));
/* 147 */           _type.put(Integer.valueOf(type), map);
/*     */           continue;
/*     */         } 
/* 150 */         map.add(Integer.valueOf(item_id));
/*     */       }
/*     */     
/*     */     }
/* 154 */     catch (SQLException e) {
/* 155 */       _log.error(e.getLocalizedMessage(), e);
/*     */     }
/*     */     finally {
/*     */       
/* 159 */       SQLUtil.close(rs);
/* 160 */       SQLUtil.close(pstm);
/* 161 */       SQLUtil.close(co);
/*     */     } 
/* 163 */     SQLUtil.close(rs);
/* 164 */     SQLUtil.close(pstm);
/* 165 */     SQLUtil.close(co);
/*     */     
/* 167 */     _log.info("載入進階版效果道具數量: " + _list.size());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkItem(int item_id) {
/* 173 */     return _list.containsKey(Integer.valueOf(item_id));
/*     */   }
/*     */   
/*     */   public BinaryOutputStream getOS(int item_id) {
/* 177 */     return _os.get(Integer.valueOf(item_id));
/*     */   }
/*     */   
/*     */   public static L1ItemUseEX getItemPro(int itemId) {
/* 181 */     if (_list == null) {
/* 182 */       return null;
/*     */     }
/*     */     
/* 185 */     if (!_list.containsKey(Integer.valueOf(itemId))) {
/* 186 */       return null;
/*     */     }
/*     */     
/* 189 */     return _list.get(Integer.valueOf(itemId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
public static void getSaveSkillid(L1PcInstance pc) {
    for (Map.Entry<Integer, L1ItemUseEX> entry : _list.entrySet()) {
        int skillId = entry.getKey();
        L1ItemUseEX value = entry.getValue();
        if (value.is_buff_save()) {
            int timeSec = pc.getSkillEffectTimeSec(skillId);
            if (timeSec > 0) {
                CharBuffTable.storeBuff(pc.getId(), skillId, timeSec);
            }
        }
    }
}

	public boolean add(L1PcInstance pc, int item_id, int buff_time) {
    if (!_list.containsKey(item_id)) {
        return false;
    }

    L1ItemUseEX value = _list.get(item_id);
    String itemName = value.getItem_name();

    if (pc.hasSkillEffect(item_id) && value.get_type_mod() == 0) {
        pc.sendPackets(new S_ServerMessage(166, itemName + "\\aD道具賦予時效剩餘\\aI" + pc.getSkillEffectTimeSec(item_id) + "秒"));
        return false;
    }

    if (value.get_type() != 0) {
        List<Integer> buffs = _type.get(value.get_type());
        for (Integer buff_id : buffs) {
            if (pc.hasSkillEffect(buff_id)) {
                if (value.get_type_mod() == 0) {
                    pc.sendPackets(new S_ServerMessage(value.gettype_msg()));
                    return false;
                }
                pc.removeSkillEffect(buff_id);
            }
        }
    }

    int buffDuration = value.get_buff_time() * 1000;
    pc.setSkillEffect(item_id, buffDuration);
    pc.sendPackets(new S_ServerMessage(166, "\\aC道具賦予時效剩餘:\\aI" + pc.getSkillEffectTimeSec(item_id) + "秒"));

    if (value.get_buff_gfx() != 0) {
        pc.sendPacketsX8(new S_SkillSound(pc.getId(), value.get_buff_gfx()));
    }

    StringBuilder name = new StringBuilder();
    boolean status2 = false;
    boolean spmr = false;
    boolean attr = false;

    if (value.getMessage() != null) {
        pc.sendPackets(new S_ServerMessage("\\aC" + value.getMessage()));
    }

    if (value.get_poly() != -1) {
        if (pc.hasSkillEffect(67)) {
            pc.removeSkillEffect(67);
        }
        L1PolyMorph.doPoly(pc, value.get_poly(), value.get_buff_time(), 1);
    }

    if (value.get_double_score() != 0) {
        pc.setdouble_score(value.get_double_score());
    }

    if (value.get_add_str() != 0) {
        pc.addStr(value.get_add_str());
        status2 = true;
    }

    if (value.get_add_dex() != 0) {
        pc.addDex(value.get_add_dex());
        status2 = true;
    }

    if (value.get_add_con() != 0) {
        pc.addCon(value.get_add_con());
        status2 = true;
    }

    if (value.get_add_int() != 0) {
        pc.addInt(value.get_add_int());
        status2 = true;
    }

    if (value.get_add_wis() != 0) {
        pc.addWis(value.get_add_wis());
        status2 = true;
    }

    if (value.get_add_cha() != 0) {
        pc.addCha(value.get_add_cha());
        status2 = true;
    }

    if (value.get_add_ac() != 0) {
        pc.addAc(-value.get_add_ac());
        attr = true;
    }

    if (value.get_add_hp() != 0) {
        pc.addMaxHp(value.get_add_hp());
        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        if (pc.isInParty()) {
            pc.getParty().updateMiniHP(pc);
        }
    }

    if (value.get_add_mp() != 0) {
        pc.addMaxMp(value.get_add_mp());
        pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
    }

    if (value.get_add_hpr() != 0) {
        pc.addHpr(value.get_add_hpr());
    }

    if (value.get_add_mpr() != 0) {
        pc.addMpr(value.get_add_mpr());
    }

    if (value.get_add_dmg() != 0) {
        pc.addDmgup(value.get_add_dmg());
    }

    if (value.get_add_hit() != 0) {
        pc.addHitup(value.get_add_hit());
    }

    if (value.get_add_bow_dmg() != 0) {
        pc.addBowDmgup(value.get_add_bow_dmg());
    }

    if (value.get_add_bow_hit() != 0) {
        pc.addBowHitup(value.get_add_bow_hit());
    }

    if (value.get_add_dmg_r() != 0) {
        pc.addDamageReductionByArmor(value.get_add_dmg_r());
    }

    if (value.get_add_magic_r() != 0) {
        pc.add_magic_reduction_dmg(value.get_add_magic_r());
    }

    if (value.get_add_mr() != 0) {
        pc.addMr(value.get_add_mr());
        spmr = true;
    }

    if (value.get_add_sp() != 0) {
        pc.addSp(value.get_add_sp());
        spmr = true;
    }

    if (value.get_add_fire() != 0) {
        pc.addFire(value.get_add_fire());
        attr = true;
    }

    if (value.get_add_wind() != 0) {
        pc.addWind(value.get_add_wind());
        attr = true;
    }

    if (value.get_add_earth() != 0) {
        pc.addEarth(value.get_add_earth());
        attr = true;
    }

    if (value.get_add_water() != 0) {
        pc.addWater(value.get_add_water());
        attr = true;
    }

    if (value.get_add_stun() != 0) {
        pc.addRegistStun(value.get_add_stun());
    }

    if (value.get_add_stone() != 0) {
        pc.addRegistStone(value.get_add_stone());
    }

    if (value.get_add_sleep() != 0) {
        pc.addRegistSleep(value.get_add_sleep());
    }

    if (value.get_add_freeze() != 0) {
        pc.add_regist_freeze(value.get_add_freeze());
    }

    if (value.get_add_sustain() != 0) {
        pc.addRegistSustain(value.get_add_sustain());
    }

    if (value.get_add_blind() != 0) {
        pc.addRegistBlind(value.get_add_blind());
    }

    if (status2) {
        pc.sendPackets(new S_OwnCharStatus2(pc));
    }

    if (attr) {
        pc.sendPackets(new S_OwnCharAttrDef(pc));
    }

    if (spmr) {
        pc.sendPackets(new S_SPMR(pc));
    }

    pc.sendPackets(new S_ServerMessage(name.toString()));
    return true;
}

	public void remove(L1PcInstance pc, int item_id) {
    if (!_list.containsKey(item_id)) {
        return;
    }

    L1ItemUseEX value = _list.get(item_id);
    String itemName = value.getItem_name();

    pc.sendPackets(new S_ServerMessage(166, "\\aI[" + itemName + "]道具賦予時間結束"));

    if (value.getMessage() != null) {
        pc.sendPackets(new S_ServerMessage("\\aI" + value.getMessage() + " 效果結束"));
    }

    boolean status2 = false;
    boolean spmr = false;
    boolean attr = false;

    if (value.get_double_score() != 0) {
        pc.setdouble_score(-value.get_double_score());
    }

    if (value.get_add_str() != 0) {
        pc.addStr(-value.get_add_str());
        status2 = true;
    }

    if (value.get_add_dex() != 0) {
        pc.addDex(-value.get_add_dex());
        status2 = true;
    }

    if (value.get_add_con() != 0) {
        pc.addCon(-value.get_add_con());
        status2 = true;
    }

    if (value.get_add_int() != 0) {
        pc.addInt(-value.get_add_int());
        status2 = true;
    }

    if (value.get_add_wis() != 0) {
        pc.addWis(-value.get_add_wis());
        status2 = true;
    }

    if (value.get_add_cha() != 0) {
        pc.addCha(-value.get_add_cha());
        status2 = true;
    }

    if (value.get_add_ac() != 0) {
        pc.addAc(value.get_add_ac());
        attr = true;
    }

    if (value.get_add_hp() != 0) {
        pc.addMaxHp(-value.get_add_hp());
        pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
        if (pc.isInParty()) {
            pc.getParty().updateMiniHP(pc);
        }
    }

    if (value.get_add_mp() != 0) {
        pc.addMaxMp(-value.get_add_mp());
        pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
    }

    if (value.get_add_hpr() != 0) {
        pc.addHpr(-value.get_add_hpr());
    }

    if (value.get_add_mpr() != 0) {
        pc.addMpr(-value.get_add_mpr());
    }

    if (value.get_add_dmg() != 0) {
        pc.addDmgup(-value.get_add_dmg());
    }

    if (value.get_add_hit() != 0) {
        pc.addHitup(-value.get_add_hit());
    }

    if (value.get_add_bow_dmg() != 0) {
        pc.addBowDmgup(-value.get_add_bow_dmg());
    }

    if (value.get_add_bow_hit() != 0) {
        pc.addBowHitup(-value.get_add_bow_hit());
    }

    if (value.get_add_dmg_r() != 0) {
        pc.addDamageReductionByArmor(-value.get_add_dmg_r());
    }

    if (value.get_add_magic_r() != 0) {
        pc.add_magic_reduction_dmg(-value.get_add_magic_r());
    }

    if (value.get_add_mr() != 0) {
        pc.addMr(-value.get_add_mr());
        spmr = true;
    }

    if (value.get_add_sp() != 0) {
        pc.addSp(-value.get_add_sp());
        spmr = true;
    }

    if (value.get_add_fire() != 0) {
        pc.addFire(-value.get_add_fire());
        attr = true;
    }

    if (value.get_add_wind() != 0) {
        pc.addWind(-value.get_add_wind());
        attr = true;
    }

    if (value.get_add_earth() != 0) {
        pc.addEarth(-value.get_add_earth());
        attr = true;
    }

    if (value.get_add_water() != 0) {
        pc.addWater(-value.get_add_water());
        attr = true;
    }

    if (value.get_add_stun() != 0) {
        pc.addRegistStun(-value.get_add_stun());
    }

    if (value.get_add_stone() != 0) {
        pc.addRegistStone(-value.get_add_stone());
    }

    if (value.get_add_sleep() != 0) {
        pc.addRegistSleep(-value.get_add_sleep());
    }

    if (value.get_add_freeze() != 0) {
        pc.add_regist_freeze(-value.get_add_freeze());
    }

    if (value.get_add_sustain() != 0) {
        pc.addRegistSustain(-value.get_add_sustain());
    }

    if (value.get_add_blind() != 0) {
        pc.addRegistBlind(-value.get_add_blind());
    }

    StringBuilder name = new StringBuilder();
    pc.sendPackets(new S_ServerMessage(name.toString()));

    if (status2) {
        pc.sendPackets(new S_OwnCharStatus2(pc));
    }

    if (attr) {
        pc.sendPackets(new S_OwnCharAttrDef(pc));
    }

    if (spmr) {
        pc.sendPackets(new S_SPMR(pc));
    }
  }
}
