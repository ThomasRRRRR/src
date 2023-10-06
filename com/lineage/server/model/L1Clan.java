/*     */ package com.lineage.server.model;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.server.datatables.sql.CharacterTable;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_PacketBox;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.server.world.WorldClan;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class L1Clan
/*     */ {
/*  24 */   private static final Log _log = LogFactory.getLog(L1Clan.class);
/*     */   
/*  26 */   private final Lock _lock = new ReentrantLock(true);
/*     */   
/*     */   public static final int CLAN_RANK_LEAGUE_PUBLIC = 2;
/*     */   
/*     */   public static final int CLAN_RANK_LEAGUE_VICEPRINCE = 3;
/*     */   
/*     */   public static final int CLAN_RANK_LEAGUE_PRINCE = 4;
/*     */   
/*     */   public static final int CLAN_RANK_LEAGUE_PROBATION = 5;
/*     */   
/*     */   public static final int CLAN_RANK_LEAGUE_GUARDIAN = 6;
/*     */   
/*     */   public static final int CLAN_RANK_PUBLIC = 7;
/*     */   
/*     */   public static final int CLAN_RANK_PROBATION = 8;
/*     */   
/*     */   public static final int CLAN_RANK_GUARDIAN = 9;
/*     */   
/*     */   public static final int CLAN_RANK_PRINCE = 10;
/*     */   private int _clanId;
/*     */   private String _clanName;
/*     */   private int _leaderId;
/*     */   private String _leaderName;
/*     */   private int _castleId;
/*     */   private int _houseId;
/*  51 */   private int _warehouse = 0;
/*     */   
/*     */   private int _ClanSkillId;
/*     */   private int _ClanSkillLv;
/*     */   private int _clanadena;
/*  56 */   private final L1DwarfForClanInventory _dwarfForClan = new L1DwarfForClanInventory(this);
/*     */   
/*  58 */   private final ArrayList<String> _membersNameList = new ArrayList<>();
/*     */   private int _emblemId;
/*  60 */   String[] _rank = new String[] { 
/*  61 */       "", "", "[聯盟一般]", "[聯盟副君主]", "[聯盟君主]", "[聯盟修習騎士]", "[聯盟守護騎士]", "[一般]", "[修習騎士]", "[守護騎士]", "[君主]" };
/*     */   
/*     */   private boolean _clanskill = false;
/*     */   
/*  65 */   private Timestamp _skilltime = null;
/*     */   private int _maxuser;
/*     */   private Timestamp _foundDate;
/*     */   private String _announcement;
/*     */   
private ArrayList<String> MarkSeeList = new ArrayList<String>();

/*     */   public int getClanId() {
/*  71 */     return this._clanId;
/*     */   }
/*     */   private int _emblemStatus; private int _clanLevel; private int _clanContribution;
/*     */   
/*     */   public void setClanId(int clan_id) {
/*  76 */     this._clanId = clan_id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClanName() {
/*  81 */     return this._clanName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClanName(String clan_name) {
/*  86 */     this._clanName = clan_name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLeaderId() {
/*  91 */     return this._leaderId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeaderId(int leader_id) {
/*  96 */     this._leaderId = leader_id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getLeaderName() {
/* 101 */     return this._leaderName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLeaderName(String leader_name) {
/* 106 */     this._leaderName = leader_name;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCastleId() {
/* 111 */     return this._castleId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCastleId(int hasCastle) {
/* 116 */     this._castleId = hasCastle;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHouseId() {
/* 121 */     return this._houseId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHouseId(int hasHideout) {
/* 126 */     this._houseId = hasHideout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void CheckClan_Exp20(L1PcInstance leavePc) {
/* 133 */     L1PcInstance[] clanMembers = getOnlineClanMember();
/* 134 */     int OnlineMemberSize = getOnlineClanMemberSize();
/*     */     
/* 136 */     if (leavePc != null && 
/* 137 */       leavePc.hasSkillEffect(8070)) {
/* 138 */       leavePc.removeNoTimerSkillEffect(8070);
/* 139 */       leavePc.add_exp(-ConfigOther.clancountexp);
/* 140 */       leavePc.sendPackets((ServerBasePacket)new S_PacketBox(165, 0));
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (OnlineMemberSize >= ConfigOther.clancount) {
/* 145 */       byte b; int i; L1PcInstance[] arrayOfL1PcInstance; for (i = (arrayOfL1PcInstance = clanMembers).length, b = 0; b < i; ) { L1PcInstance clanMember = arrayOfL1PcInstance[b];
/* 146 */         if (!clanMember.hasSkillEffect(8070)) {
/* 147 */           clanMember.setSkillEffect(8070, 0);
/* 148 */           clanMember.add_exp(ConfigOther.clancountexp);
/* 149 */           clanMember.sendPackets((ServerBasePacket)new S_PacketBox(165, 1));
/*     */         }  b++; }
/*     */     
/*     */     } else {
/*     */       byte b; int i; L1PcInstance[] arrayOfL1PcInstance;
/* 154 */       for (i = (arrayOfL1PcInstance = clanMembers).length, b = 0; b < i; ) { L1PcInstance clanMember = arrayOfL1PcInstance[b];
/* 155 */         if (clanMember.hasSkillEffect(8070)) {
/* 156 */           clanMember.removeNoTimerSkillEffect(8070);
/* 157 */           clanMember.add_exp(-ConfigOther.clancountexp);
/* 158 */           clanMember.sendPackets((ServerBasePacket)new S_PacketBox(165, 0));
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMemberName(String member_name) {
/* 168 */     this._lock.lock();
/*     */     try {
/* 170 */       if (!this._membersNameList.contains(member_name)) {
/* 171 */         this._membersNameList.add(member_name);
/*     */       }
/*     */       
/* 174 */       CheckClan_Exp20(null);
/*     */     } finally {
/*     */       
/* 177 */       this._lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delMemberName(String member_name) {
/* 186 */     this._lock.lock();
/*     */     try {
/* 188 */       if (this._membersNameList.contains(member_name)) {
/* 189 */         this._membersNameList.remove(member_name);
/*     */       }
/*     */       
/* 192 */       L1PcInstance leavePc = World.get().getPlayer(member_name);
/* 193 */       CheckClan_Exp20(leavePc);
/*     */     } finally {
/*     */       
/* 196 */       this._lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOnlineClanMemberSize() {
/* 205 */     int count = 0;
/*     */     try {
/* 207 */       for (String name : this._membersNameList) {
/* 208 */         L1PcInstance pc = World.get().getPlayer(name);
/*     */         
/* 210 */         if (pc != null) {
/* 211 */           count++;
/*     */         }
/*     */       } 
/* 214 */     } catch (Exception e) {
/*     */       
/* 216 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 218 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAllMembersSize() {
/*     */     try {
/* 225 */       return this._membersNameList.size();
/*     */     }
/* 227 */     catch (Exception e) {
/* 228 */       _log.error(e.getLocalizedMessage(), e);
/*     */       
/* 230 */       return 0;
/*     */     } 
/*     */   } public void sendPacketsAll(ServerBasePacket packet) {
/*     */     try {
/*     */       byte b;
/*     */       int i;
/*     */       Object[] arrayOfObject;
/* 237 */       for (i = (arrayOfObject = this._membersNameList.toArray()).length, b = 0; b < i; ) { Object nameobj = arrayOfObject[b];
/* 238 */         String name = (String)nameobj;
/* 239 */         L1PcInstance pc = World.get().getPlayer(name);
/* 240 */         if (pc != null)
/* 241 */           pc.sendPackets(packet); 
/*     */         b++; }
/*     */     
/* 244 */     } catch (Exception e) {
/*     */       
/* 246 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L1PcInstance[] getOnlineClanMember() {
/* 257 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/* 259 */     ArrayList<L1PcInstance> onlineMembers = new ArrayList<>();
/*     */     try {
/* 261 */       temp.addAll(this._membersNameList);
/*     */       
/* 263 */       for (String name : temp) {
/* 264 */         L1PcInstance pc = World.get().getPlayer(name);
/* 265 */         if (pc != null && !onlineMembers.contains(pc)) {
/* 266 */           onlineMembers.add(pc);
/*     */         }
/*     */       }
/*     */     
/* 270 */     } catch (Exception e) {
/* 271 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 273 */     return onlineMembers.<L1PcInstance>toArray(new L1PcInstance[onlineMembers.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getOnlineMembersFP() {
/* 283 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/* 285 */     StringBuilder result = new StringBuilder();
/*     */     try {
/* 287 */       temp.addAll(this._membersNameList);
/*     */       
/* 289 */       for (String name : temp) {
/* 290 */         L1PcInstance pc = World.get().getPlayer(name);
/* 291 */         if (pc != null) {
/* 292 */           result.append(String.valueOf(name) + " ");
/*     */         }
/*     */       }
/*     */     
/* 296 */     } catch (Exception e) {
/* 297 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 299 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getAllMembersFP() {
/* 309 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/* 311 */     StringBuilder result = new StringBuilder();
/*     */     try {
/* 313 */       temp.addAll(this._membersNameList);
/*     */       
/* 315 */       for (String name : temp) {
/* 316 */         result.append(String.valueOf(name) + " ");
/*     */       }
/*     */     }
/* 319 */     catch (Exception e) {
/* 320 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 322 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEmblemId() {
/* 327 */     return this._emblemId;
/*     */   }
/*     */   
/*     */   public void setEmblemId(int i) {
/* 331 */     this._emblemId = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getOnlineMembersFPWithRank() {
/* 341 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/* 343 */     StringBuilder result = new StringBuilder();
/*     */     try {
/* 345 */       temp.addAll(this._membersNameList);
/*     */       
/* 347 */       for (String name : temp) {
/* 348 */         L1PcInstance pc = World.get().getPlayer(name);
/* 349 */         if (pc != null) {
/* 350 */           result.append(String.valueOf(name) + getRankString(pc) + " ");
/*     */         }
/*     */       }
/*     */     
/* 354 */     } catch (Exception e) {
/* 355 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 357 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuilder getAllMembersFPWithRank() {
/* 367 */     ArrayList<String> temp = new ArrayList<>();
/*     */     
/* 369 */     StringBuilder result = new StringBuilder();
/*     */     try {
/* 371 */       temp.addAll(this._membersNameList);
/*     */       
/* 373 */       for (String name : temp) {
/* 374 */         L1PcInstance pc = CharacterTable.get().restoreCharacter(name);
/* 375 */         if (pc != null) {
/* 376 */           result.append(String.valueOf(name) + " ");
/*     */         }
/*     */       }
/*     */     
/* 380 */     } catch (Exception e) {
/* 381 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/* 383 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getRankString(L1PcInstance pc) {
/* 393 */     if (pc != null && 
/* 394 */       pc.getClanRank() > 0) {
/* 395 */       return this._rank[pc.getClanRank()];
/*     */     }
/*     */     
/* 398 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAllMembers() {
/* 406 */     return this._membersNameList.<String>toArray(new String[this._membersNameList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L1DwarfForClanInventory getDwarfForClanInventory() {
/* 415 */     return this._dwarfForClan;
/*     */   }
/*     */   
/*     */   public int getWarehouseUsingChar() {
/* 419 */     return this._warehouse;
/*     */   }
/*     */   
/*     */   public void setWarehouseUsingChar(int objid) {
/* 423 */     this._warehouse = objid;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set_clanskill(boolean boolean1) {
/* 428 */     this._clanskill = boolean1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClanskill() {
/* 433 */     return this._clanskill;
/*     */   }
/*     */ 
/*     */   
/*     */   public void set_skilltime(Timestamp skilltime) {
/* 438 */     this._skilltime = skilltime;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp get_skilltime() {
/* 443 */     return this._skilltime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Timestamp getFoundDate() {
/* 453 */     return this._foundDate;
/*     */   }
/*     */   
/*     */   public void setFoundDate(Timestamp _foundDate) {
/* 457 */     this._foundDate = _foundDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOnlineMaxUser() {
/* 462 */     return this._maxuser;
/*     */   }
/*     */   
/*     */   public void setOnlineMaxUser(int i) {
/* 466 */     this._maxuser = i;
/*     */   }
/*     */   
/*     */   public String getAnnouncement() {
/* 470 */     return this._announcement;
/*     */   }
/*     */   
/*     */   public void setAnnouncement(String announcement) {
/* 474 */     this._announcement = announcement;
/*     */   }
/*     */   
/*     */   public int getEmblemStatus() {
/* 478 */     return this._emblemStatus;
/*     */   }
/*     */   
/*     */   public void setEmblemStatus(int emblemStatus) {
/* 482 */     this._emblemStatus = emblemStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getClanLevel() {
/* 488 */     return this._clanLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClanLevel(int clanLevel) {
/* 493 */     this._clanLevel = clanLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getClanContribution() {
/* 498 */     return this._clanContribution;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClanContribution(int clanContribution) {
/* 503 */     this._clanContribution = clanContribution;
/*     */   }
/*     */   public int getClanSkillId() {
/* 506 */     return this._ClanSkillId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClanSkillId(int i) {
/* 511 */     this._ClanSkillId = i;
/*     */   }
/*     */   
/*     */   public int getClanSkillLv() {
/* 515 */     return this._ClanSkillLv;
/*     */   }
/*     */ 
/**
 * 加入關注血盟列表
 * @param clanname
 */
public void addMarkSeeList(String clanname) {
	  MarkSeeList.add(clanname);
}

/**
 * 移出關注血盟列表
 * @param clanname
 */
public void removeMarkSeeList(String clanname) {
	  MarkSeeList.remove(clanname);
}

/**
 * 取回關注血盟列表
 * @return
 */
public ArrayList<String> getMarkSeeList() {
	  return MarkSeeList;
}
/*     */   
/*     */   public void setClanSkillLv(int i) {
/* 520 */     this._ClanSkillLv = i;
/*     */   }
/*     */   public ArrayList<L1PcInstance> getOnlineMemberList() {
/* 523 */     ArrayList<L1PcInstance> onlineMembers = new ArrayList<>();
/* 524 */     for (String name : this._membersNameList) {
/* 525 */       L1PcInstance pc = World.get().getPlayer(name);
/* 526 */       if (pc != null && !onlineMembers.contains(pc)) {
/* 527 */         onlineMembers.add(pc);
/*     */       }
/*     */     } 
/* 530 */     return onlineMembers;
/*     */   }
/*     */   public int getclanadena() {
/* 533 */     return this._clanadena;
/*     */   }
/*     */   
/*     */   public void setclanadena(int clanadena) {
/* 537 */     this._clanadena = clanadena;
/*     */   }
/* 539 */   private ArrayList<Integer> allianceList = new ArrayList<>();
/*     */   
/*     */   public Integer[] Alliance() {
/* 542 */     Integer[] i = this.allianceList.<Integer>toArray(new Integer[this.allianceList.size()]);
/* 543 */     return i;
/*     */   }
/*     */   public void addAlliance(int i) {
/* 546 */     if (i == 0) {
/*     */       return;
/*     */     }
/* 549 */     if (!this.allianceList.contains(Integer.valueOf(i))) {
/* 550 */       this.allianceList.add(Integer.valueOf(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public int AllianceSize() {
/* 555 */     return this.allianceList.size();
/*     */   }
/*     */   public L1Clan getAlliance(int i) {
/* 558 */     if (this.allianceList.size() > 0) {
/* 559 */       for (Iterator<Integer> iterator = this.allianceList.iterator(); iterator.hasNext(); ) { int id = ((Integer)iterator.next()).intValue();
/* 560 */         if (id == i) {
/* 561 */           return WorldClan.get().getClan(i);
/*     */         } }
/*     */     
/*     */     }
/* 565 */     return null;
/*     */   }
/*     */   
/*     */   public void AllianceDelete() {
/* 569 */     if (this.allianceList.size() > 0)
/* 570 */       this.allianceList.clear(); 
/*     */   }
/*     */   
/*     */   public void removeAlliance(int i) {
/* 574 */     if (i == 0) {
/*     */       return;
/*     */     }
/* 577 */     if (this.allianceList.contains(Integer.valueOf(i)))
/* 578 */       this.allianceList.remove(Integer.valueOf(i)); 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\model\L1Clan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */