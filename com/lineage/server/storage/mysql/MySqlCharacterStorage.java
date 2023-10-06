/*     */ package com.lineage.server.storage.mysql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.CharObjidTable;
/*     */ import com.lineage.server.datatables.lock.CharItemsReading;
/*     */ import com.lineage.server.datatables.lock.CharOtherReading;
/*     */ import com.lineage.server.datatables.lock.CharOtherReading1;
/*     */ import com.lineage.server.datatables.lock.CharOtherReading2;
/*     */ import com.lineage.server.datatables.lock.CharOtherReading3;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.storage.CharacterStorage;
/*     */ import com.lineage.server.templates.L1PcOther;
/*     */ import com.lineage.server.templates.L1PcOther1;
/*     */ import com.lineage.server.templates.L1PcOther2;
/*     */ import com.lineage.server.templates.L1PcOther3;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySqlCharacterStorage
/*     */   implements CharacterStorage
/*     */ {
/*  30 */   private static final Log _log = LogFactory.getLog(MySqlCharacterStorage.class);
/*     */ 
/*     */   
/*     */   public L1PcInstance loadCharacter(String charName) {
/*  34 */     L1PcInstance pc = null;
/*  35 */     Connection con = null;
/*  36 */     PreparedStatement pstm = null;
/*  37 */     PreparedStatement pstm2 = null;
/*  38 */     ResultSet rs = null;
/*     */ 
/*     */     
/*  41 */     try { con = DatabaseFactory.get().getConnection();
/*  42 */       pstm = con.prepareStatement(
/*  43 */           "SELECT * FROM characters WHERE char_name=?");
/*  44 */       pstm.setString(1, charName);
/*     */       
/*  46 */       rs = pstm.executeQuery();
/*  47 */       if (!rs.next()) {
/*  48 */         return null;
/*     */       }
/*  50 */       pc = new L1PcInstance();
/*  51 */       String loginName = rs.getString("account_name").toLowerCase();
/*  52 */       pc.setAccountName(loginName);
/*     */       
/*  54 */       int objid = rs.getInt("objid");
/*  55 */       pc.setId(objid);
/*     */       
/*  57 */       pc.set_showId(-1);
/*     */       
/*  59 */       L1PcOther other = CharOtherReading.get().getOther(pc);
/*  60 */       if (other == null) {
/*  61 */         other = new L1PcOther();
/*  62 */         other.set_objid(objid);
/*     */       } 
/*  64 */       pc.set_other(other);
/*     */ 
/*     */       
/*  67 */       L1PcOther1 other1 = CharOtherReading1.get().getOther(pc);
/*  68 */       if (other1 == null) {
/*  69 */         other1 = new L1PcOther1();
/*  70 */         other1.set_objid(objid);
/*     */       } 
/*  72 */       pc.set_other1(other1);
/*     */       
/*  74 */       L1PcOther2 l1PcOther2 = CharOtherReading2.get().getOther(pc);
/*  75 */       if (l1PcOther2 == null) {
/*  76 */         l1PcOther2 = new L1PcOther2();
/*  77 */         l1PcOther2.set_objid(objid);
/*     */       } 
/*  79 */       pc.set_other2(l1PcOther2);
/*     */       
/*  81 */       L1PcOther3 other3 = CharOtherReading3.get().getOther(pc);
/*  82 */       if (other3 == null) {
/*  83 */         other3 = new L1PcOther3();
/*  84 */         other3.set_objid(objid);
/*     */       } 
/*  86 */       pc.set_other3(other3);
/*     */       
/*  88 */       pc.setName(rs.getString("char_name"));
/*  89 */       pc.setHighLevel(rs.getInt("HighLevel"));
/*  90 */       pc.setExp(rs.getLong("Exp"));
/*  91 */       pc.addBaseMaxHp(rs.getShort("MaxHp"));
/*  92 */       short currentHp = rs.getShort("CurHp");
/*  93 */       if (currentHp < 1) {
/*  94 */         currentHp = 1;
/*     */       }
/*  96 */       pc.setDead(false);
/*  97 */       pc.setCurrentHpDirect(currentHp);
/*  98 */       pc.setStatus(0);
/*  99 */       pc.addBaseMaxMp(rs.getShort("MaxMp"));
/* 100 */       pc.setCurrentMpDirect(rs.getShort("CurMp"));
/* 101 */       pc.addBaseStr(rs.getInt("Str"));
/* 102 */       pc.addBaseCon(rs.getInt("Con"));
/* 103 */       pc.addBaseDex(rs.getInt("Dex"));
/* 104 */       pc.addBaseCha(rs.getInt("Cha"));
/* 105 */       pc.addBaseInt(rs.getInt("Intel"));
/* 106 */       pc.addBaseWis(rs.getInt("Wis"));
/* 107 */       int status = rs.getInt("Status");
/* 108 */       pc.setCurrentWeapon(status);
/* 109 */       int classId = rs.getInt("Class");
/* 110 */       pc.setClassId(classId);
/* 111 */       pc.setTempCharGfx(classId);
/* 112 */       pc.setGfxId(classId);
/* 113 */       pc.set_sex(rs.getInt("Sex"));
/* 114 */       pc.setType(rs.getInt("Type"));
/* 115 */       int head = rs.getInt("Heading");
/* 116 */       if (head > 7) {
/* 117 */         head = 0;
/*     */       }
/* 119 */       pc.setHeading(head);
/*     */       
/* 121 */       pc.setX(rs.getInt("locX"));
/* 122 */       pc.setY(rs.getInt("locY"));
/* 123 */       pc.setMap(rs.getShort("MapID"));
/* 124 */       pc.set_food(rs.getInt("Food"));
/* 125 */       pc.setLawful(rs.getInt("Lawful"));
/* 126 */       pc.setTitle(rs.getString("Title"));
/* 127 */       pc.setClanid(rs.getInt("ClanID"));
/* 128 */       pc.setClanname(rs.getString("Clanname"));
/* 129 */       pc.setClanRank(rs.getInt("ClanRank"));
/* 130 */       pc.setRejoinClanTime(rs.getTimestamp("rejoin_clan_time"));
/* 131 */       pc.setBonusStats(rs.getInt("BonusStatus"));
/* 132 */       pc.setElixirStats(rs.getInt("ElixirStatus"));
/* 133 */       pc.setElfAttr(rs.getInt("ElfAttr"));
/* 134 */       pc.set_PKcount(rs.getInt("PKcount"));
/* 135 */       pc.setPkCountForElf(rs.getInt("PkCountForElf"));
/* 136 */       pc.setExpRes(rs.getInt("ExpRes"));
/* 137 */       pc.setPartnerId(rs.getInt("PartnerID"));
/* 138 */       pc.setAccessLevel(rs.getShort("AccessLevel"));
/*     */       
/* 140 */       if (pc.getAccessLevel() >= 200) {
/* 141 */         pc.setGm(true);
/* 142 */         pc.setMonitor(false);
/*     */       }
/* 144 */       else if (pc.getAccessLevel() == 100) {
/* 145 */         pc.setGm(false);
/* 146 */         pc.setMonitor(true);
/*     */       } else {
/*     */         
/* 149 */         pc.setGm(false);
/* 150 */         pc.setMonitor(false);
/*     */       } 
/*     */       
/* 153 */       pc.setOnlineStatus(rs.getInt("OnlineStatus"));
/* 154 */       pc.setHomeTownId(rs.getInt("HomeTownID"));
/* 155 */       pc.setContribution(rs.getInt("Contribution"));
/* 156 */       pc.setPay(rs.getInt("Pay"));
/* 157 */       pc.setHellTime(rs.getInt("HellTime"));
/* 158 */       pc.setBanned(rs.getBoolean("Banned"));
/* 159 */       pc.setKarma(rs.getInt("Karma"));
/* 160 */       pc.setLastPk(rs.getTimestamp("LastPk"));
/* 161 */       pc.setLastPkForElf(rs.getTimestamp("LastPkForElf"));
/* 162 */       pc.setDeleteTime(rs.getTimestamp("DeleteTime"));
/* 163 */       pc.setOriginalStr(rs.getInt("OriginalStr"));
/* 164 */       pc.setOriginalCon(rs.getInt("OriginalCon"));
/* 165 */       pc.setOriginalDex(rs.getInt("OriginalDex"));
/* 166 */       pc.setOriginalCha(rs.getInt("OriginalCha"));
/* 167 */       pc.setOriginalInt(rs.getInt("OriginalInt"));
/* 168 */       pc.setOriginalWis(rs.getInt("OriginalWis"));
/* 169 */       pc.setCreateTime(rs.getTimestamp("CreateTime"));
/* 170 */       pc.setMeteLevel(rs.getInt("logpcpower"));
/* 171 */       pc.setPunishTime(rs.getTimestamp("PunishTime"));
/* 172 */       pc.setBanError(rs.getInt("BanError"));
/* 173 */       pc.setInputBanError(rs.getInt("InputBanError"));
/* 174 */       pc.setSpeedError(rs.getInt("SpeedError"));
/*     */       
/* 176 */       pc.setRocksPrisonTime(rs.getInt("RocksPrisonTime"));
/* 177 */       pc.setIvoryTowerTime(rs.getInt("IvorytowerTime"));
/* 178 */       pc.setLastabardTime(rs.getInt("LastabardTime"));
/* 179 */       pc.setDragonValleyTime(rs.getInt("DragonValleyTime"));
/* 180 */       pc.setPcContribution(rs.getInt("PcContribution"));
/* 181 */       pc.setClanContribution(rs.getInt("ClanContribution"));
/* 182 */       pc.setClanNameContribution(rs.getString("ClanNameContribution"));
/* 183 */       pc.setlogpcpower_SkillCount(rs.getInt("logpcpower_SkillCount"));
/* 184 */       pc.setlogpcpower_SkillFor1(rs.getInt("logpcpower_SkillFor1"));
/* 185 */       pc.setlogpcpower_SkillFor2(rs.getInt("logpcpower_SkillFor2"));
/* 186 */       pc.setlogpcpower_SkillFor3(rs.getInt("logpcpower_SkillFor3"));
/* 187 */       pc.setlogpcpower_SkillFor4(rs.getInt("logpcpower_SkillFor4"));
/* 188 */       pc.setlogpcpower_SkillFor5(rs.getInt("logpcpower_SkillFor5"));
/* 189 */       pc.setReductionDmg(rs.getInt("ReductionDmg"));
/* 190 */       pc.setpcdmg(rs.getInt("pcdmg"));
/* 191 */       pc.setpaycount(rs.getInt("paycount"));
/* 192 */       pc.setlogintime(rs.getInt("logintime"));
/* 193 */       pc.setloginpoly(rs.getInt("loginpoly"));
/* 194 */       pc.setguaji_count(rs.getInt("guaji_count"));
/* 195 */       pc.setday(rs.getInt("day"));
/* 196 */       pc.setPrestige(rs.getInt("PrestigeLv"));
/* 197 */       pc.setQuburcount(rs.getInt("Quburcount"));
/* 198 */       pc.setclanadena(rs.getInt("clanadena"));
/* 199 */       pc.setcardpoly(rs.getInt("cardpoly"));
/* 200 */       pc.setpcezpay(rs.getInt("pcezpay"));
/* 201 */       pc.setTheEnemy(rs.getString("TheEnemy"));
/*     */       
pc.setWyType1(rs.getInt("紋樣類型1"));
pc.setWyLevel1(rs.getInt("紋樣等級1"));
pc.setWyType2(rs.getInt("紋樣類型2"));
pc.setWyLevel2(rs.getInt("紋樣等級2"));
pc.setWyType3(rs.getInt("紋樣類型3"));
pc.setWyLevel3(rs.getInt("紋樣等級3"));
pc.setWyType4(rs.getInt("紋樣類型4"));
pc.setWyLevel4(rs.getInt("紋樣等級4"));
pc.setWyType5(rs.getInt("紋樣類型5"));
pc.setWyLevel5(rs.getInt("紋樣等級5"));
pc.setWenyangJiFen(rs.getInt("紋樣積分"));

/* 203 */       rs.close();
/*     */       
/* 205 */       pc.refresh();
/* 206 */       pc.setMoveSpeed(0);
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
/*     */        }
/*     */     
/* 222 */     catch (SQLException e)
/*     */     
/* 224 */     { _log.error(e.getLocalizedMessage(), e);
/* 225 */       return null; }
/*     */     finally
/*     */     
/* 228 */     { SQLUtil.close(rs);
/* 229 */       SQLUtil.close(pstm);
/* 230 */       SQLUtil.close(con); }  SQLUtil.close(rs); SQLUtil.close(pstm); SQLUtil.close(con);
/*     */     
/* 232 */     SQLUtil.close(rs);
/* 233 */     SQLUtil.close(pstm);
/* 234 */     SQLUtil.close(con);
/*     */     
/* 236 */     return pc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void createCharacter(L1PcInstance pc) {
/* 241 */     Connection con = null;
/* 242 */     PreparedStatement pstm = null;
/*     */     try {
/* 244 */       int i = 0;
/* 245 */       con = DatabaseFactory.get().getConnection();
/* 246 */       pstm = con.prepareStatement(
/* 247 */           "INSERT INTO characters SET account_name=?,objid=?,char_name=?,level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,ClanID=?,Clanname=?,ClanRank=?,rejoin_clan_time=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,Pay=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?,CreateTime=?,logpcpower=?,PunishTime=?,BanError=?,InputBanError=?,SpeedError=?,RocksPrisonTime=?,IvorytowerTime=?,LastabardTime=?,DragonValleyTime=?,PcContribution=?,ClanContribution=?,ClanNameContribution=?,logpcpower_SkillCount=?,logpcpower_SkillFor1=?,logpcpower_SkillFor2=?,logpcpower_SkillFor3=?,logpcpower_SkillFor4=?,logpcpower_SkillFor5=?,ReductionDmg=?,pcdmg=?,paycount=?,logintime=?,loginpoly=?,guaji_count=?,day=?,PrestigeLv=?,clanadena=?,Quburcount=?,cardpoly=?,pcezpay=?,TheEnemy=?,紋樣類型1=?,紋樣等級1=?,紋樣類型2=?,紋樣等級2=?,紋樣類型3=?,紋樣等級3=?,紋樣類型4=?,紋樣等級4=?,紋樣類型5=?,紋樣等級5=?,紋樣積分=?");
/*     */       
/* 249 */       pstm.setString(++i, pc.getAccountName());
/* 250 */       pstm.setInt(++i, pc.getId());
/* 251 */       pstm.setString(++i, pc.getName());
/* 252 */       pstm.setInt(++i, pc.getLevel());
/* 253 */       pstm.setInt(++i, pc.getHighLevel());
/* 254 */       pstm.setLong(++i, pc.getExp());
/* 255 */       pstm.setInt(++i, pc.getBaseMaxHp());
/* 256 */       int hp = pc.getCurrentHp();
/* 257 */       if (hp < 1) {
/* 258 */         hp = 1;
/*     */       }
/* 260 */       pstm.setInt(++i, hp);
/* 261 */       pstm.setInt(++i, pc.getBaseMaxMp());
/* 262 */       pstm.setInt(++i, pc.getCurrentMp());
/* 263 */       pstm.setInt(++i, pc.getAc());
/* 264 */       pstm.setInt(++i, pc.getBaseStr());
/* 265 */       pstm.setInt(++i, pc.getBaseCon());
/* 266 */       pstm.setInt(++i, pc.getBaseDex());
/* 267 */       pstm.setInt(++i, pc.getBaseCha());
/* 268 */       pstm.setInt(++i, pc.getBaseInt());
/* 269 */       pstm.setInt(++i, pc.getBaseWis());
/* 270 */       pstm.setInt(++i, pc.getCurrentWeapon());
/* 271 */       pstm.setInt(++i, pc.getClassId());
/* 272 */       pstm.setInt(++i, pc.get_sex());
/* 273 */       pstm.setInt(++i, pc.getType());
/* 274 */       pstm.setInt(++i, pc.getHeading());
/* 275 */       pstm.setInt(++i, pc.getX());
/* 276 */       pstm.setInt(++i, pc.getY());
/* 277 */       pstm.setInt(++i, pc.getMapId());
/* 278 */       pstm.setInt(++i, pc.get_food());
/* 279 */       pstm.setInt(++i, pc.getLawful());
/* 280 */       pstm.setString(++i, pc.getTitle());
/* 281 */       pstm.setInt(++i, pc.getClanid());
/* 282 */       pstm.setString(++i, pc.getClanname());
/* 283 */       pstm.setInt(++i, pc.getClanRank());
/* 284 */       pstm.setTimestamp(++i, pc.getRejoinClanTime());
/* 285 */       pstm.setInt(++i, pc.getBonusStats());
/* 286 */       pstm.setInt(++i, pc.getElixirStats());
/* 287 */       pstm.setInt(++i, pc.getElfAttr());
/* 288 */       pstm.setInt(++i, pc.get_PKcount());
/* 289 */       pstm.setInt(++i, pc.getPkCountForElf());
/* 290 */       pstm.setInt(++i, pc.getExpRes());
/* 291 */       pstm.setInt(++i, pc.getPartnerId());
/* 292 */       short accesslevel = pc.getAccessLevel();
/* 293 */       if (accesslevel > 200) {
/* 294 */         accesslevel = 0;
/*     */       }
/* 296 */       pstm.setShort(++i, accesslevel);
/* 297 */       pstm.setInt(++i, pc.getOnlineStatus());
/* 298 */       pstm.setInt(++i, pc.getHomeTownId());
/* 299 */       pstm.setInt(++i, pc.getContribution());
/* 300 */       pstm.setInt(++i, pc.getPay());
/* 301 */       pstm.setInt(++i, pc.getHellTime());
/* 302 */       pstm.setBoolean(++i, pc.isBanned());
/* 303 */       pstm.setInt(++i, pc.getKarma());
/* 304 */       pstm.setTimestamp(++i, pc.getLastPk());
/* 305 */       pstm.setTimestamp(++i, pc.getLastPkForElf());
/* 306 */       pstm.setTimestamp(++i, pc.getDeleteTime());
/*     */       
/* 308 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/* 309 */       String times = sdf.format(Long.valueOf(System.currentTimeMillis()));
/* 310 */       int time = Integer.parseInt(times.replace("-", ""));
/* 311 */       pstm.setInt(++i, time);
/*     */       
/* 313 */       pstm.setInt(++i, pc.getMeteLevel());
/* 314 */       pstm.setTimestamp(++i, pc.getPunishTime());
/* 315 */       pstm.setInt(++i, pc.getBanError());
/* 316 */       pstm.setInt(++i, pc.getInputBanError());
/* 317 */       pstm.setInt(++i, pc.getSpeedError());
/*     */       
/* 319 */       pstm.setInt(++i, pc.getRocksPrisonTime());
/* 320 */       pstm.setInt(++i, pc.getIvoryTowerTime());
/* 321 */       pstm.setInt(++i, pc.getLastabardTime());
/* 322 */       pstm.setInt(++i, pc.getDragonValleyTime());
/* 323 */       pstm.setInt(++i, pc.getPcContribution());
/* 324 */       pstm.setInt(++i, pc.getClanContribution());
/* 325 */       pstm.setString(++i, pc.getClanNameContribution());
/* 326 */       pstm.setInt(++i, pc.getlogpcpower_SkillCount());
/* 327 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor1());
/* 328 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor2());
/* 329 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor3());
/* 330 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor4());
/* 331 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor5());
/* 332 */       pstm.setInt(++i, pc.getReductionDmg());
/* 333 */       pstm.setInt(++i, pc.getpcdmg());
/* 334 */       pstm.setInt(++i, pc.getpaycount());
/* 335 */       pstm.setInt(++i, pc.getlogintime());
/* 336 */       pstm.setInt(++i, pc.getloginpoly());
/* 337 */       pstm.setInt(++i, pc.getguaji_count());
/* 338 */       pstm.setInt(++i, pc.getday());
/* 339 */       pstm.setInt(++i, pc.getPrestige());
/* 340 */       pstm.setInt(++i, pc.getclanadena());
/* 341 */       pstm.setInt(++i, pc.getQuburcount());
/* 342 */       pstm.setInt(++i, pc.getcardpoly());
/* 343 */       pstm.setInt(++i, pc.getpcezpay());
/* 344 */       pstm.setString(++i, pc.getTheEnemy());
pstm.setInt(++i, pc.getWyType1());
pstm.setInt(++i, pc.getWyLevel1());
pstm.setInt(++i, pc.getWyType2());
pstm.setInt(++i, pc.getWyLevel2());
pstm.setInt(++i, pc.getWyType3());
pstm.setInt(++i, pc.getWyLevel3());
pstm.setInt(++i, pc.getWyType4());
pstm.setInt(++i, pc.getWyLevel4());
pstm.setInt(++i, pc.getWyType5());
pstm.setInt(++i, pc.getWyLevel5());
pstm.setInt(++i, pc.getWenyangJiFen());
/* 345 */       pstm.execute();
/*     */     }
/* 347 */     catch (SQLException e) {
/*     */       
/* 349 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 352 */       SQLUtil.close(pstm);
/* 353 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteCharacter(String accountName, String charName) throws Exception {
/* 360 */     Connection con = null;
/* 361 */     PreparedStatement pstm = null;
/* 362 */     ResultSet rs = null;
/*     */     try {
/* 364 */       con = DatabaseFactory.get().getConnection();
/* 365 */       pstm = con.prepareStatement(
/* 366 */           "SELECT * FROM characters WHERE account_name=? AND char_name=?");
/* 367 */       pstm.setString(1, accountName);
/* 368 */       pstm.setString(2, charName);
/* 369 */       rs = pstm.executeQuery();
/*     */       
/* 371 */       if (!rs.next()) {
/* 372 */         throw new RuntimeException("could not delete character");
/*     */       }
/*     */       
/* 375 */       int objid = CharObjidTable.get().charObjid(charName);
/*     */       
/* 377 */       if (objid != 0) {
/* 378 */         CharItemsReading.get().delUserItems(Integer.valueOf(objid));
/*     */       }
/*     */       
/* 381 */       pstm = con.prepareStatement(
/* 382 */           "DELETE FROM character_buddys WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 383 */       pstm.setString(1, charName);
/* 384 */       pstm.execute();
/*     */       
/* 386 */       pstm = con.prepareStatement(
/* 387 */           "DELETE FROM character_buff WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 388 */       pstm.setString(1, charName);
/* 389 */       pstm.execute();
/*     */       
/* 391 */       pstm = con.prepareStatement(
/* 392 */           "DELETE FROM character_config WHERE object_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 393 */       pstm.setString(1, charName);
/* 394 */       pstm.execute();
/*     */       
/* 396 */       pstm = con.prepareStatement(
/* 397 */           "DELETE FROM character_quests WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 398 */       pstm.setString(1, charName);
/* 399 */       pstm.execute();
/*     */       
/* 401 */       pstm = con.prepareStatement(
/* 402 */           "DELETE FROM character_skills WHERE char_obj_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 403 */       pstm.setString(1, charName);
/* 404 */       pstm.execute();
/*     */       
/* 406 */       pstm = con.prepareStatement(
/* 407 */           "DELETE FROM character_teleport WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 408 */       pstm.setString(1, charName);
/* 409 */       pstm.execute();
/*     */       
/* 411 */       pstm = con.prepareStatement(
/* 412 */           "DELETE FROM characters WHERE char_name=?");
/* 413 */       pstm.setString(1, charName);
/* 414 */       pstm.execute();
/*     */       
/* 416 */       pstm = con.prepareStatement(
/* 417 */           "DELETE FROM clan_members WHERE char_id IN (SELECT objid FROM characters WHERE char_name = ?)");
/* 418 */       pstm.setString(1, charName);
/* 419 */       pstm.execute();
/*     */     
/*     */     }
/* 422 */     catch (SQLException e) {
/* 423 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 426 */       SQLUtil.close(rs);
/* 427 */       SQLUtil.close(pstm);
/* 428 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeCharacter(L1PcInstance pc) {
/* 434 */     Connection con = null;
/* 435 */     PreparedStatement pstm = null;
/*     */     try {
/* 437 */       int i = 0;
/* 438 */       con = DatabaseFactory.get().getConnection();
/* 439 */       pstm = con.prepareStatement(
/* 440 */           "UPDATE characters SET level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,ClanID=?,Clanname=?,ClanRank=?,rejoin_clan_time=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?,logpcpower=?,PunishTime=?,BanError=?,InputBanError=?,SpeedError=?,RocksPrisonTime=?,IvorytowerTime=?,LastabardTime=?,DragonValleyTime=?,PcContribution=?,ClanContribution=?,ClanNameContribution=?,logpcpower_SkillCount=?,logpcpower_SkillFor1=?,logpcpower_SkillFor2=?,logpcpower_SkillFor3=?,logpcpower_SkillFor4=?,logpcpower_SkillFor5=?,ReductionDmg=?,pcdmg=?,paycount=?,logintime=?,loginpoly=?,guaji_count=?,day=?,PrestigeLv=?,clanadena=?,Quburcount=?,cardpoly=?,pcezpay=?,TheEnemy=?,紋樣類型1=?,紋樣等級1=?,紋樣類型2=?,紋樣等級2=?,紋樣類型3=?,紋樣等級3=?,紋樣類型4=?,紋樣等級4=?,紋樣類型5=?,紋樣等級5=?,紋樣積分=? WHERE objid=?");
/*     */       
/* 442 */       pstm.setInt(++i, pc.getLevel());
/* 443 */       pstm.setInt(++i, pc.getHighLevel());
/* 444 */       pstm.setLong(++i, pc.getExp());
/* 445 */       pstm.setInt(++i, pc.getBaseMaxHp());
/* 446 */       int hp = pc.getCurrentHp();
/* 447 */       if (hp < 1) {
/* 448 */         hp = 1;
/*     */       }
/* 450 */       pstm.setInt(++i, hp);
/* 451 */       pstm.setInt(++i, pc.getBaseMaxMp());
/* 452 */       pstm.setInt(++i, pc.getCurrentMp());
/* 453 */       pstm.setInt(++i, pc.getAc());
/* 454 */       pstm.setInt(++i, pc.getBaseStr());
/* 455 */       pstm.setInt(++i, pc.getBaseCon());
/* 456 */       pstm.setInt(++i, pc.getBaseDex());
/* 457 */       pstm.setInt(++i, pc.getBaseCha());
/* 458 */       pstm.setInt(++i, pc.getBaseInt());
/* 459 */       pstm.setInt(++i, pc.getBaseWis());
/* 460 */       pstm.setInt(++i, pc.getCurrentWeapon());
/* 461 */       pstm.setInt(++i, pc.getClassId());
/* 462 */       pstm.setInt(++i, pc.get_sex());
/* 463 */       pstm.setInt(++i, pc.getType());
/* 464 */       pstm.setInt(++i, pc.getHeading());
/* 465 */       pstm.setInt(++i, pc.getX());
/* 466 */       pstm.setInt(++i, pc.getY());
/* 467 */       pstm.setInt(++i, pc.getMapId());
/* 468 */       pstm.setInt(++i, pc.get_food());
/* 469 */       pstm.setInt(++i, pc.getLawful());
/* 470 */       pstm.setString(++i, pc.getTitle());
/* 471 */       pstm.setInt(++i, pc.getClanid());
/* 472 */       pstm.setString(++i, pc.getClanname());
/* 473 */       pstm.setInt(++i, pc.getClanRank());
/* 474 */       pstm.setTimestamp(++i, pc.getRejoinClanTime());
/* 475 */       pstm.setInt(++i, pc.getBonusStats());
/* 476 */       pstm.setInt(++i, pc.getElixirStats());
/* 477 */       pstm.setInt(++i, pc.getElfAttr());
/* 478 */       pstm.setInt(++i, pc.get_PKcount());
/* 479 */       pstm.setInt(++i, pc.getPkCountForElf());
/* 480 */       pstm.setInt(++i, pc.getExpRes());
/* 481 */       pstm.setInt(++i, pc.getPartnerId());
/* 482 */       short accesslevel = pc.getAccessLevel();
/* 483 */       if (accesslevel > 200) {
/* 484 */         accesslevel = 0;
/*     */       }
/* 486 */       pstm.setShort(++i, accesslevel);
/* 487 */       pstm.setInt(++i, pc.getOnlineStatus());
/* 488 */       pstm.setInt(++i, pc.getHomeTownId());
/* 489 */       pstm.setInt(++i, pc.getContribution());
/* 490 */       pstm.setInt(++i, pc.getHellTime());
/* 491 */       pstm.setBoolean(++i, pc.isBanned());
/* 492 */       pstm.setInt(++i, pc.getKarma());
/* 493 */       pstm.setTimestamp(++i, pc.getLastPk());
/* 494 */       pstm.setTimestamp(++i, pc.getLastPkForElf());
/* 495 */       pstm.setTimestamp(++i, pc.getDeleteTime());
/* 496 */       pstm.setInt(++i, pc.getMeteLevel());
/* 497 */       pstm.setTimestamp(++i, pc.getPunishTime());
/* 498 */       pstm.setInt(++i, pc.getBanError());
/* 499 */       pstm.setInt(++i, pc.getInputBanError());
/* 500 */       pstm.setInt(++i, pc.getSpeedError());
/*     */       
/* 502 */       pstm.setInt(++i, pc.getRocksPrisonTime());
/* 503 */       pstm.setInt(++i, pc.getIvoryTowerTime());
/* 504 */       pstm.setInt(++i, pc.getLastabardTime());
/* 505 */       pstm.setInt(++i, pc.getDragonValleyTime());
/* 506 */       pstm.setInt(++i, pc.getPcContribution());
/* 507 */       pstm.setInt(++i, pc.getClanContribution());
/* 508 */       pstm.setString(++i, pc.getClanNameContribution());
/* 509 */       pstm.setInt(++i, pc.getlogpcpower_SkillCount());
/* 510 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor1());
/* 511 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor2());
/* 512 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor3());
/* 513 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor4());
/* 514 */       pstm.setInt(++i, pc.getlogpcpower_SkillFor5());
/* 515 */       pstm.setInt(++i, pc.getReductionDmg());
/* 516 */       pstm.setInt(++i, pc.getpcdmg());
/* 517 */       pstm.setInt(++i, pc.getpaycount());
/* 518 */       pstm.setInt(++i, pc.getlogintime());
/* 519 */       pstm.setInt(++i, pc.getloginpoly());
/* 520 */       pstm.setInt(++i, pc.getguaji_count());
/* 521 */       pstm.setInt(++i, pc.getday());
/*     */       
/* 523 */       pstm.setInt(++i, pc.getPrestige());
/* 524 */       pstm.setInt(++i, pc.getclanadena());
/* 525 */       pstm.setInt(++i, pc.getQuburcount());
/* 526 */       pstm.setInt(++i, pc.getcardpoly());
/* 527 */       pstm.setInt(++i, pc.getpcezpay());
/* 528 */       pstm.setString(++i, pc.getTheEnemy());
pstm.setInt(++i, pc.getWyType1());
pstm.setInt(++i, pc.getWyLevel1());
pstm.setInt(++i, pc.getWyType2());
pstm.setInt(++i, pc.getWyLevel2());
pstm.setInt(++i, pc.getWyType3());
pstm.setInt(++i, pc.getWyLevel3());
pstm.setInt(++i, pc.getWyType4());
pstm.setInt(++i, pc.getWyLevel4());
pstm.setInt(++i, pc.getWyType5());
pstm.setInt(++i, pc.getWyLevel5());
pstm.setInt(++i, pc.getWenyangJiFen());
/* 529 */       pstm.setInt(++i, pc.getId());
/* 530 */       pstm.execute();
/*     */     }
/* 532 */     catch (SQLException e) {
/* 533 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 536 */       SQLUtil.close(pstm);
/* 537 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void alldeleteCharacter(String accountName, String charName) throws Exception {
/* 542 */     Connection con = null;
/* 543 */     PreparedStatement pstm = null;
/* 544 */     ResultSet rs = null;
/*     */     try {
/* 546 */       con = DatabaseFactory.get().getConnection();
/* 547 */       pstm = con.prepareStatement("SELECT * FROM characters WHERE account_name=? AND char_name=?");
/* 548 */       pstm.setString(1, accountName);
/* 549 */       pstm.setString(2, charName);
/* 550 */       rs = pstm.executeQuery();
/* 551 */       if (!rs.next()) {
/* 552 */         throw new RuntimeException("could not delete character");
/*     */       }
/*     */ 
/*     */       
/* 556 */       pstm = con.prepareStatement("DELETE FROM characters WHERE char_name=?");
/* 557 */       pstm.setString(1, charName);
/* 558 */       pstm.execute();
/*     */     }
/* 560 */     catch (SQLException e) {
/* 561 */       throw e;
/*     */     } finally {
/* 563 */       SQLUtil.close(rs);
/* 564 */       SQLUtil.close(pstm);
/* 565 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\storage\mysql\MySqlCharacterStorage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */