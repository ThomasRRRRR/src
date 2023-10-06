/*      */ package com.lineage.server.model;
/*      */ 
/*      */ import com.add.AutoAttackUpdate;
/*      */ import com.add.TFUpdate;
/*      */ import com.add.system.CardBookCmd;
/*      */ import com.add.system.CardBookCmd_doll;
/*      */ import com.lineage.DatabaseFactory;
import com.lineage.FollowAutoAttackUpdate;
/*      */ import com.lineage.config.ConfigAlt;
/*      */ import com.lineage.config.ConfigClan;
/*      */ import com.lineage.config.ConfigOther;
/*      */ import com.lineage.data.QuestClass;
/*      */ import com.lineage.data.item_etcitem.extra.Show_pcitem;
/*      */ import com.lineage.server.clientpackets.C_CreateChar;
/*      */ import com.lineage.server.clientpackets.C_War;
/*      */ import com.lineage.server.command.executor.L1ToPC2;
/*      */ import com.lineage.server.datatables.ExpTable;
/*      */ import com.lineage.server.datatables.ItemTable;
/*      */ import com.lineage.server.datatables.PolyTable;
/*      */ import com.lineage.server.datatables.QuestTable;
/*      */ import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.SkillsTable;
/*      */ import com.lineage.server.datatables.lock.CharacterQuestReading;
/*      */ import com.lineage.server.datatables.lock.SpawnBossReading;
/*      */ import com.lineage.server.datatables.sql.CharacterTable;
/*      */ import com.lineage.server.datatables.sql.ClanTable;
/*      */ import com.lineage.server.model.Instance.L1ItemInstance;
/*      */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillUse;
/*      */ import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_ClanMarkSee;
/*      */ import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_Message_YN;
/*      */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*      */ import com.lineage.server.serverpackets.S_OwnCharStatus;
/*      */ import com.lineage.server.serverpackets.S_OwnCharStatus2;
/*      */ import com.lineage.server.serverpackets.S_PacketBox;
/*      */ import com.lineage.server.serverpackets.S_Paralysis;
/*      */ import com.lineage.server.serverpackets.S_SPMR;
/*      */ import com.lineage.server.serverpackets.S_ServerMessage;
/*      */ import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.S_War;
/*      */ import com.lineage.server.serverpackets.ServerBasePacket;
/*      */ import com.lineage.server.templates.L1Item;
/*      */ import com.lineage.server.templates.L1Quest;
import com.lineage.server.templates.L1Skills;
/*      */ import com.lineage.server.timecontroller.server.ServerWarExecutor;
/*      */ import com.lineage.server.utils.BinaryOutputStream;
/*      */ import com.lineage.server.utils.CalcStat;
/*      */ import com.lineage.server.utils.SQLUtil;
import com.lineage.server.world.World;
/*      */ import com.lineage.server.world.WorldClan;
/*      */ import com.lineage.william.ItemAction;
/*      */ import com.lineage.william.ItemActionDoll;
/*      */ import com.lineage.william.ItemActionPoly;
/*      */ import com.lineage.william.ItemActionPolyBS;
/*      */ import com.lineage.william.ItemActionTele;
/*      */ import com.lineage.william.ItemShop;
/*      */ import com.lineage.william.PayBonus;

/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.Date;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Timestamp;
/*      */ import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
/*      */ import java.util.Random;

/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;


/*      */ 
/*      */ public class L1ActionPc
/*      */ {
/*  187 */   private static final Log _log = LogFactory.getLog(L1ActionPc.class);
/*      */   
/*  189 */   public static final Random _random = new Random();
/*      */   
/*      */   private final L1PcInstance _pc;
/*      */   
/*      */   boolean start = false;
/*      */   
/*      */   private static BufferedWriter out;
/*      */ 
/*      */   
/*      */   public L1ActionPc(L1PcInstance pc) {
/*  199 */     this._pc = pc;
/*      */   }

/*      */   public L1PcInstance get_pc() {
/*  207 */     return this._pc;
/*      */   }

/*      */   public void action(String cmd, long amount) {
/*      */     try {
/*  217 */       if (this._pc.isInCharReset())
/*  218 */       { String str; switch ((str = cmd).hashCode()) { case 49: if (!str.equals("1")) {
/*      */               break;
/*      */             }  
/*  269 */             if (checkvalid(this._pc, cmd)) {
/*  270 */               setLevelUp(this._pc, 1);
/*      */             }
/*  272 */             checkLevelUp(this._pc); break;
/*      */           case 1567:
/*      */             if (!str.equals("10"))
/*      */               break; 
/*  276 */             if (checkvalid(this._pc, cmd)) {
/*  277 */               setLevelUp(this._pc, 10);
/*      */             }
/*  279 */             checkLevelUp(this._pc);
/*      */             break;

/*      */           case 2524:
/*      */             if (!str.equals("OK")) {
/*      */               break;
/*      */             }
/*  379 */             saveNewCharStatus(this._pc); break;case 67708: if (!str.equals("Cha")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseCha(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 67938: if (!str.equals("Con")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseCon(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 68599: if (!str.equals("Dex")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseDex(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 73679: if (!str.equals("Int")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseInt(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 83473: if (!str.equals("Str")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseStr(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 86977: if (!str.equals("Wis")) break;  if (checkvalid(this._pc, cmd)) { this._pc.addBaseWis(1); setLevelUp(this._pc, 1); }  checkBonusPower(this._pc); break;case 96918249: if (!str.equals("exCha")) break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseCha(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 96918479: if (!str.equals("exCon")) break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseCon(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 96919140: if (!str.equals("exDex")) break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseDex(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 96924220: if (!str.equals("exInt")) break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseInt(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 96934014: if (!str.equals("exStr")) break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseStr(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 96937518: if (!str.equals("exWis"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempElixirstats(this._pc.getTempElixirstats() + 1); this._pc.addBaseWis(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkElixirPower(this._pc); break;case 1948305772: if (!str.equals("initCha"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseCha(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break;case 1948306002: if (!str.equals("initCon"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseCon(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break;case 1948306663: if (!str.equals("initDex"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseDex(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break;case 1948311743: if (!str.equals("initInt"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseInt(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break;case 1948321537: if (!str.equals("initStr"))
/*      */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseStr(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break;case 1948325041: if (!str.equals("initWis"))
/*  386 */               break;  if (checkvalid(this._pc, cmd)) { this._pc.setTempInitPoint(this._pc.getTempInitPoint() + 1); this._pc.addBaseWis(1); this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(this._pc)); }  checkInitPower(this._pc); break; }  return; }  if (this._pc.isShapeChange()) {
/*      */         
/*  388 */         this._pc.get_other().set_gmHtml(null);
/*  389 */         int awakeSkillId = this._pc.getAwakeSkillId();
/*  390 */         if (awakeSkillId == 185 || 
/*  391 */           awakeSkillId == 190 || 
/*  392 */           awakeSkillId == 195) {
/*      */           
/*  394 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(1384));
/*      */           return;
/*      */         } 
/*  397 */         L1PolyMorph.handleCommands(this._pc, cmd);
/*  398 */         this._pc.setShapeChange(false);
/*  399 */         this._pc.setSummonMonster(false);
/*      */         
/*      */         return;
/*      */       } 
/*  403 */       if (this._pc.isItemPoly()) {
/*  404 */         L1ItemInstance item = this._pc.getPolyScroll();
/*  405 */         L1PolyMorph poly = PolyTable.get().getTemplate(cmd);
/*  406 */         if (poly != null || cmd.equals("none")) {
/*  407 */           if (item.getItemId() == 44212 || (item.getItemId() >= 80057 && item.getItemId() <= 80066)) {
/*  408 */             usePolyBook(this._pc, item, cmd); return;
/*      */           } 
/*  410 */           if (item.getItemId() >= 80067 && item.getItemId() <= 80070) {
/*  411 */             usePolyBookcouitem(this._pc, item, cmd);
/*      */             return;
/*      */           } 
/*  414 */           usePolyScroll(this._pc, item, cmd);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */       
/*  420 */       if (this._pc.isPhantomTeleport()) {
/*  421 */         usePhantomTeleport(this._pc, cmd);
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  426 */       if (this._pc.get_other().get_gmHtml() != null) {
/*  427 */         this._pc.get_other().get_gmHtml().action(cmd);
/*      */         
/*      */         return;
/*      */       } 
/*  431 */       if (AutoAttackUpdate.getInstance().PcCommand(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*  434 */       if (TFUpdate.getInstance().PcCommand(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*  437 */       if (CardBookCmd.get().Cmd(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*      */       
/*  441 */       if (CardBookCmd.get().PolyCmd(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*      */       
/*  445 */       if (CardBookCmd_doll.get().Cmd(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*      */       
/*  449 */       if (CardBookCmd_doll.get().PolyCmd(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*  452 */       if (Show_pcitem.get().PcCommand(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*  450 */       if (FollowAutoAttackUpdate.getInstance().PcCommand(this._pc, cmd)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  462 */       if (ItemActionPoly.forNpcQuest(cmd, this._pc)) {
/*      */         return;
/*      */       }
/*  465 */       if (ItemActionDoll.forNpcQuest(cmd, this._pc)) {
/*      */         return;
/*      */       }
/*  468 */       if (ItemActionPolyBS.forNpcQuest(cmd, this._pc)) {
/*      */         return;
/*      */       }
/*  471 */       if (ItemActionTele.forNpcQuest(cmd, this._pc)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  477 */       L1ItemInstance item2 = this._pc.getPolyScrol2();
/*  478 */       if (ItemAction.forNpcQuest(cmd, this._pc, item2)) {
/*      */         return;
/*      */       }
/*      */       
/*  482 */       if (ItemShop.forNpcQuest(cmd, this._pc)) {
/*      */         return;
/*      */       }
/*      */       
/*  486 */       this._pc.get_other().set_gmHtml(null);
/*      */ 
/*      */       
/*  489 */       if (this._pc.isGm()) {
/*  490 */         if (cmd.equals("tp_refresh")) {
/*  491 */           L1ToPC2.checkTPhtmlPredicate(this._pc, 0, true);
/*      */         }
/*  493 */         else if (cmd.equals("tp_refresh_map")) {
/*  494 */           L1ToPC2.checkTPhtmlPredicate(this._pc, 0, false);
/*      */         }
/*  496 */         else if (cmd.equals("tp_page_up")) {
/*  497 */           L1ToPC2.checkTPhtml(this._pc, this._pc.get_other().get_page() - 1);
/*      */         }
/*  499 */         else if (cmd.equals("tp_page_down")) {
/*  500 */           L1ToPC2.checkTPhtml(this._pc, this._pc.get_other().get_page() + 1);
/*      */         }
/*  502 */         else if (cmd.matches("tp_[0-9]+")) {
/*  503 */           int index = Integer.parseInt(cmd.substring(3));
/*  504 */           L1ToPC2.teleport2Player(this._pc, index);
/*      */         } 
/*      */       }
/*      */ 
/*      */       
/*  509 */       if (cmd.equalsIgnoreCase("power")) {
/*      */         
/*  511 */         if (this._pc.power()) {
/*  512 */           this._pc.sendPackets((ServerBasePacket)new S_Bonusstats(this._pc.getId()));
/*      */         }
/*      */ }
/*      */ 
/*      */ 
/*      */ 
else if (cmd.equalsIgnoreCase("onekeyparty")) { // 一鍵組隊
	Iterator<L1PcInstance> localPreparedStatement;
	for (localPreparedStatement = World.get().getAllPlayers().iterator();localPreparedStatement.hasNext();) {
		L1PcInstance tgpc = (L1PcInstance)localPreparedStatement.next();
		if (_pc.getLocation().isInScreen(tgpc.getLocation()) && !tgpc.isInParty() && tgpc.getClanname().equalsIgnoreCase(_pc.getClanname()) && _pc.getId() != tgpc.getId()) {
			tgpc.setPartyID(_pc.getId());
			tgpc.sendPackets(new S_Message_YN(953,_pc.getName()));
			_pc.sendPackets(new S_SystemMessage((new StringBuilder("\\fR邀請與您\\fY同盟或無盟\\fR的玩家\\fY【")).append(tgpc.getName()).append("】\\fR加入隊伍!").toString()));	
		}
		if (_pc.getLocation().isInScreen(tgpc.getLocation()) && _pc.getId() != tgpc.getId() && (tgpc.isInParty() 
				|| !tgpc.getClanname().equalsIgnoreCase(_pc.getClanname()))) {
			_pc.sendPackets(new S_SystemMessage((new StringBuilder("\\fR玩家\\fY【")).append(tgpc.getName()).append("】\\fR與你\\fY不同盟\\fR或\\fV已經有隊伍了！").toString()));
		}
	}
}
/*      */ 
/*  54 */     else if (cmd.equalsIgnoreCase("npcclanbuff")) {
/*  55 */       if (_pc.hasSkillEffect(95413)) {
/*     */         return;
/*     */       }
/*  58 */       if (_pc.getClan() == null) {
/*  59 */         _pc.sendPackets((ServerBasePacket)new S_SystemMessage("必須先擁有血盟。"));
/*     */         return;
/*     */       } 
/*  62 */       if (_pc.getInventory().checkItem(ConfigOther.NeedItem, ConfigOther.NeedItemCount)) {
/*  63 */         _pc.getInventory().consumeItem(ConfigOther.NeedItem, ConfigOther.NeedItemCount);
/*     */         
/*  65 */         _pc.setSkillEffect(95413, 10000);
/*     */ 
/*     */         
/*  68 */         int[] skills = ConfigOther.Give_skill;
/*     */ 
/*     */         
/*  71 */         L1Clan clan = WorldClan.get().getClan(_pc.getClanname());
/*     */         
/*  73 */         L1PcInstance[] clanMembers = clan.getOnlineClanMember();
/*  74 */         for (int i = 0; i < skills.length; i++) {
/*  75 */           byte b1; int k; L1PcInstance[] arrayOfL1PcInstance; for (k = (arrayOfL1PcInstance = clanMembers).length, b1 = 0; b1 < k; ) { L1PcInstance clanMember1 = arrayOfL1PcInstance[b1];
/*  76 */             L1Skills skill = SkillsTable.get().getTemplate(skills[i]);
/*  77 */             L1SkillUse skillUse = new L1SkillUse();
/*     */             
/*  79 */             skillUse.handleCommands(clanMember1, skills[i], clanMember1.getId(), clanMember1.getX(), clanMember1.getY(), skill.getBuffDuration(), 4); b1++; }
/*     */         
/*     */         }  byte b;
/*     */         int j;
/*     */         L1PcInstance[] arrayOfL1PcInstance1;
/*  84 */         for (j = (arrayOfL1PcInstance1 = clan.getOnlineClanMember()).length, b = 0; b < j; ) { L1PcInstance clanMembers2 = arrayOfL1PcInstance1[b];
/*  85 */           clanMembers2.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.Msg, new Object[] { _pc.getName() }))); b++; }
/*     */       
/*     */       } else {
/*  88 */         _pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg));
/*     */       } 
/*     */     } 
///*  91 */     else if (cmd.equalsIgnoreCase("npcallbuff")) {
///*  92 */       if (pc.hasSkillEffect(95413)) {
///*     */         return;
///*     */       }
///*     */       
///*  96 */       pc.setSkillEffect(95413, 10000);
///*  97 */       if (pc.getInventory().checkItem(ConfigOther.NeedItem1, ConfigOther.NeedItemCount1)) {
///*  98 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.Msg1, new Object[] { pc.getName() })));
///*  99 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_BlueMessage(166, "\\f2" + String.format(ConfigOther.Msg1, new Object[] { pc.getName() })));
///* 100 */         pc.getInventory().consumeItem(ConfigOther.NeedItem1, ConfigOther.NeedItemCount1);
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */         
///* 106 */         AllBuffRunnable allBuffRunnable = new AllBuffRunnable();
///* 107 */         GeneralThreadPool.get().execute(allBuffRunnable);
///*     */         try {
///* 109 */           Thread.sleep(1000L);
///* 110 */         } catch (InterruptedException e) {
///*     */           
///* 112 */           e.printStackTrace();
///*     */         }
///*     */       
///*     */       } else {
///*     */         
///* 117 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg1));
///*     */       } 
///*     */     } 


/*  91 */     else if (cmd.equalsIgnoreCase("mybuff")) {
/*  92 */       if (_pc.hasSkillEffect(95413)) {
/*     */         return;
/*     */       }
/*     */       
/*  96 */       _pc.setSkillEffect(95413, 10000);
/*  97 */       if (_pc.getInventory().checkItem(ConfigOther.NeedItem2, ConfigOther.NeedItemCount2)) {
/* 100 */         _pc.getInventory().consumeItem(ConfigOther.NeedItem2, ConfigOther.NeedItemCount2);
/*     */ 
				final int[] allBuffSkill = ConfigOther.Give_skill;
				for (int i = 0; i < allBuffSkill.length; ++i) {
				final L1Skills skill = SkillsTable.get().getTemplate(allBuffSkill[i]);
				final L1SkillUse skillUse = new L1SkillUse();
				skillUse.handleCommands(_pc, allBuffSkill[i], _pc.getId(), _pc.getX(), _pc.getY(), skill.getBuffDuration(), 4);
/*     */       }
/*     */       } else {
/*     */         
/* 117 */         _pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg2));
/*     */       } 
/*     */     } 
//else if (cmd.matches("Warhard") || cmd.matches("Warhardoff")) {// 顯示盟徽
//    boolean war = false;
//    int warType = 0;
//    if (cmd.matches("Warhard")) {
//        warType = 1;
//    } else if (cmd.matches("Warhardoff")) {
//        warType = 3;
//    }
//    if (_pc.getClanid() != 0) {
//        final Collection<L1Clan> allClans = (Collection<L1Clan>) WorldClan.get().getAllClans();
//        for (final L1Clan clan : allClans) {
//            if (!clan.getClanName().equalsIgnoreCase(_pc.getClanname())) {
//                _pc.sendPackets((ServerBasePacket) new S_War(warType, _pc.getClanname(), clan.getClanName()));
//                if (warType == 1) {
//                    war = true;
//                } else if (warType == 3) {
//                    war = false;
//                }
//            }
//        }
//        if (war && warType == 1) {
//            _pc.sendPackets((ServerBasePacket) new S_War(3, _pc.getClanname(), ""));
//        }
//    } else {
//        _pc.sendPackets((ServerBasePacket) new S_SystemMessage("未加入血盟無法顯示盟徽!"));
//    }
//}
/*      */ 
else if (cmd.equalsIgnoreCase("Warhard")) {
	if (_pc.isClanGfx() == false) {
		//L1Clan clan = _pc.getClan();
		//clan.setEmblemStatus(1);
		//ClanReading.get().updateClan(clan);
		//for (L1PcInstance member : clan.getOnlineClanMember()) {
		//_pc.sendPackets(new S_PacketBox(S_PacketBox.PLEDGE_EMBLEM_STATUS, 1));
		_pc.sendPackets(new S_ClanMarkSee(2));
		//}
		_pc.set_isClanGfx(true);
		_pc.sendPackets(new S_SystemMessage("血盟盟徽已開啟"));
	} else {

		//L1Clan clan = _pc.getClan();
		//clan.setEmblemStatus(0);
		//ClanReading.get().updateClan(clan);
		//for (L1PcInstance member : clan.getOnlineClanMember()) {
		//_pc.sendPackets(new S_PacketBox(S_PacketBox.PLEDGE_EMBLEM_STATUS, 0));
		_pc.sendPackets(new S_ClanMarkSee());
		//}
		_pc.set_isClanGfx(false);
		_pc.sendPackets(new S_SystemMessage("血盟盟徽已關閉"));
	}
}

/*  675 */       else if (cmd.equalsIgnoreCase("bosspage2")) {
/*  676 */         int pag1 = SpawnBossReading.get().bossreid().size() / 15;
/*  677 */         int pag2 = (SpawnBossReading.get().bossreid().size() % 15 != 0) ? 1 : 0;
/*  678 */         if (pag1 + pag2 > this._pc.get_bosspage()) {
/*  679 */           this._pc.set_bosspage(this._pc.get_bosspage() + 1);
/*  680 */           String[] info = new String[17];
/*  681 */           int num = 0;
/*  682 */           for (int i = 15 * (this._pc.get_bosspage() - 1); i < SpawnBossReading.get().bossreid().size(); i++) {
/*  683 */             int now = ((Integer)SpawnBossReading.get().bossreid().get(i)).intValue();
/*  684 */             if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime() != null) {
/*  685 */               Date localDate = new Date(SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis());
/*  686 */               SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
/*  687 */               String time = localSimpleDateFormat.format(localDate);
/*  688 */               if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() < System.currentTimeMillis())
/*  689 */                 info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName(); 
/*  690 */               if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() >= System.currentTimeMillis())
/*  691 */                 info[num] = "【下次重生:" + time + "】" + SpawnBossReading.get().getTemplate(now).getName(); 
/*      */             } else {
/*  693 */               info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName();
/*  694 */             }  num++;
/*  695 */             if (num > 14) {
/*      */               break;
/*      */             }
/*      */           } 
/*  699 */           info[15] = "上一頁";
/*  700 */           info[16] = "下一頁";
/*  701 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "bosslist", info));
/*      */         } 
/*  703 */       } else if (cmd.equalsIgnoreCase("bosspage1")) {
/*  704 */         if (this._pc.get_bosspage() > 1) {
/*  705 */           this._pc.set_bosspage(this._pc.get_bosspage() - 1);
/*  706 */           String[] info = new String[17];
/*  707 */           int num = 0;
/*  708 */           for (int i = 15 * (this._pc.get_bosspage() - 1); i < SpawnBossReading.get().bossreid().size(); i++) {
/*  709 */             int now = ((Integer)SpawnBossReading.get().bossreid().get(i)).intValue();
/*  710 */             if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime() != null) {
/*  711 */               Date localDate = new Date(SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis());
/*  712 */               SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
/*  713 */               String time = localSimpleDateFormat.format(localDate);
/*  714 */               if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() < System.currentTimeMillis())
/*  715 */                 info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName(); 
/*  716 */               if (SpawnBossReading.get().getTemplate(now).get_nextSpawnTime().getTimeInMillis() >= System.currentTimeMillis())
/*  717 */                 info[num] = "【★下次重生:" + time + "】" + SpawnBossReading.get().getTemplate(now).getName(); 
/*      */             } else {
/*  719 */               info[num] = "【已降臨】" + SpawnBossReading.get().getTemplate(now).getName();
/*  720 */             }  num++;
/*  721 */             if (num > 14) {
/*      */               break;
/*      */             }
/*      */           } 
/*  725 */           info[15] = "上一頁";
/*  726 */           info[16] = "下一頁";
/*  727 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "bosslist", info));
/*      */         }
/*      */       
/*  730 */       } else if (cmd.equalsIgnoreCase("bad_3")) {
/*  731 */         this._pc.setBadKeyInEnemy(true);
/*  732 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入要加入黑名單的[玩家名稱]。"));
/*  733 */       } else if (cmd.equalsIgnoreCase("bad_4")) {
/*  734 */         this._pc.setBadKeyOutEnemy(true);
/*  735 */         this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入要移除黑名單的[玩家名稱]。"));
/*      */       
/*      */       }
/*  738 */       else if (cmd.equalsIgnoreCase("bad_5")) {
/*  739 */         BadEnemyList(this._pc);
/*      */       }
/*  753 */       else if (cmd.equalsIgnoreCase("clanlv1")) {
/*      */         
/*  755 */         if (this._pc.isCrown()) {
/*  756 */           if (this._pc.getClan() == null) {
/*  757 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("血盟等級升級必須先擁有血盟喔。"));
/*      */           } else {
/*  759 */             clanLevel(this._pc, 1);
/*  760 */             L1Clan clan = WorldClan.get()
/*  761 */               .getClan(this._pc.getClanname());
/*  762 */             String[] info = new String[7];
/*      */             
/*  764 */             if (this._pc.getClanid() > 0) {
/*  765 */               info[0] = String.valueOf(this._pc.getClanname());
/*      */             } else {
/*  767 */               info[0] = "無";
/*      */             } 
/*  769 */             if (this._pc.getClanid() > 0) {
/*  770 */               info[1] = String.valueOf(clan.getClanLevel());
/*      */             } else {
/*  772 */               info[1] = "無";
/*      */             } 
/*  774 */             if (this._pc.getClanid() > 0) {
/*  775 */               info[2] = 
/*  776 */                 String.valueOf(clan.getClanContribution());
/*      */             } else {
/*  778 */               info[2] = "無";
/*      */             } 
/*  780 */             info[3] = String.valueOf(this._pc.getPcContribution());
/*  781 */             info[4] = String.valueOf(this._pc.getClanContribution());
/*  782 */             info[5] = String.valueOf(this._pc.getclanadena());
/*  783 */             info[6] = String.valueOf(clan.getclanadena());
/*  784 */             this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), 
/*  785 */                   "clanlvadd", info));
/*      */           } 
/*      */         } else {
/*  788 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的職業無法強化血盟等級。"));
/*      */         } 
/*  790 */       } else if (cmd.equals("deletebook")) {
/*  791 */         this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("請輸入刪除座標名稱(60秒內)"));
/*  792 */         this._pc.setSkillEffect(95133, 60000);
/*      */       }
/*  794 */       else if (cmd.equals("clanlv3")) {
/*  795 */         if (this._pc.getClan() == null) {
/*  796 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
/*  797 */         } else if (this._pc.getInventory().checkItem(40308, 
/*  798 */             ConfigClan.PcClanAdena)) {
/*  799 */           if (this._pc.getLevel() >= ConfigClan.clanlevel) {
/*  800 */             clanLevel(this._pc, 3);
/*  801 */             L1Clan clan = WorldClan.get()
/*  802 */               .getClan(this._pc.getClanname());
/*  803 */             String[] info = new String[7];
/*      */             
/*  805 */             if (this._pc.getClanid() > 0) {
/*  806 */               info[0] = String.valueOf(this._pc.getClanname());
/*      */             } else {
/*  808 */               info[0] = "無";
/*      */             } 
/*  810 */             if (this._pc.getClanid() > 0) {
/*  811 */               info[1] = String.valueOf(clan.getClanLevel());
/*      */             } else {
/*  813 */               info[1] = "無";
/*      */             } 
/*  815 */             if (this._pc.getClanid() > 0) {
/*  816 */               info[2] = 
/*  817 */                 String.valueOf(clan.getClanContribution());
/*      */             } else {
/*  819 */               info[2] = "無";
/*      */             } 
/*  821 */             info[3] = String.valueOf(this._pc.getPcContribution());
/*  822 */             info[4] = String.valueOf(this._pc.getClanContribution());
/*  823 */             info[5] = String.valueOf(this._pc.getclanadena());
/*  824 */             info[6] = String.valueOf(clan.getclanadena());
/*  825 */             this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), 
/*  826 */                   "clanlvadd", info));
/*      */           } else {
/*  828 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage(ConfigClan.clanmsg1));
/*      */           } 
/*      */         } else {
/*  831 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("金幣不足無法幫助你的血盟喔。"));
/*      */         } 
/*  833 */       } else if (cmd.equals("clanlv5")) {
/*  834 */         if (this._pc.getClan() == null) {
/*  835 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
/*  836 */         } else if (this._pc.getInventory().checkItem(44070, 
/*  837 */             ConfigClan.ClanItem44070)) {
/*  838 */           if (this._pc.getLevel() >= ConfigClan.clanlevel) {
/*  839 */             clanLevel(this._pc, 5);
/*  840 */             L1Clan clan = WorldClan.get()
/*  841 */               .getClan(this._pc.getClanname());
/*  842 */             String[] info = new String[7];
/*      */             
/*  844 */             if (this._pc.getClanid() > 0) {
/*  845 */               info[0] = String.valueOf(this._pc.getClanname());
/*      */             } else {
/*  847 */               info[0] = "無";
/*      */             } 
/*  849 */             if (this._pc.getClanid() > 0) {
/*  850 */               info[1] = String.valueOf(clan.getClanLevel());
/*      */             } else {
/*  852 */               info[1] = "無";
/*      */             } 
/*  854 */             if (this._pc.getClanid() > 0) {
/*  855 */               info[2] = 
/*  856 */                 String.valueOf(clan.getClanContribution());
/*      */             } else {
/*  858 */               info[2] = "無";
/*      */             } 
/*  860 */             info[3] = String.valueOf(this._pc.getPcContribution());
/*  861 */             info[4] = String.valueOf(this._pc.getClanContribution());
/*  862 */             info[5] = String.valueOf(this._pc.getclanadena());
/*  863 */             info[6] = String.valueOf(clan.getclanadena());
/*  864 */             this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), 
/*  865 */                   "clanlvadd", info));
/*      */           } else {
/*  867 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage(ConfigClan.clanmsg1));
/*      */           } 
/*      */         } else {
/*  870 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("元寶不足無法幫助你的血盟喔。"));
/*      */         } 
/*  872 */       } else if (cmd.equals("clanlv6")) {
/*  873 */         if (this._pc.getClan() == null) {
/*  874 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("想要貢獻必須先擁有血盟喔。"));
/*  875 */         } else if (this._pc.getInventory().checkItem(44070, 
/*  876 */             ConfigClan.ClanItem44070_1)) {
/*  877 */           if (this._pc.getLevel() >= ConfigClan.clanlevel) {
/*  878 */             clanLevel(this._pc, 6);
/*  879 */             L1Clan clan = WorldClan.get()
/*  880 */               .getClan(this._pc.getClanname());
/*  881 */             String[] info = new String[7];
/*      */             
/*  883 */             if (this._pc.getClanid() > 0) {
/*  884 */               info[0] = String.valueOf(this._pc.getClanname());
/*      */             } else {
/*  886 */               info[0] = "無";
/*      */             } 
/*  888 */             if (this._pc.getClanid() > 0) {
/*  889 */               info[1] = String.valueOf(clan.getClanLevel());
/*      */             } else {
/*  891 */               info[1] = "無";
/*      */             } 
/*  893 */             if (this._pc.getClanid() > 0) {
/*  894 */               info[2] = 
/*  895 */                 String.valueOf(clan.getClanContribution());
/*      */             } else {
/*  897 */               info[2] = "無";
/*      */             } 
/*  899 */             info[3] = String.valueOf(this._pc.getPcContribution());
/*  900 */             info[4] = String.valueOf(this._pc.getClanContribution());
/*  901 */             info[5] = String.valueOf(this._pc.getclanadena());
/*  902 */             info[6] = String.valueOf(clan.getclanadena());
/*  903 */             this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), 
/*  904 */                   "clanlvadd", info));
/*      */           } else {
/*  906 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage(ConfigClan.clanmsg1));
/*      */           } 
/*      */         } else {
/*  909 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("元寶不足無法幫助你的血盟喔。"));
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  919 */       else if (cmd.equalsIgnoreCase("cleanaiq")) {
/*  920 */         this._pc.setnewai4(0);
/*  921 */         this._pc.setnewai5(0);
/*  922 */         this._pc.setnewai6(0);
/*  923 */         newai(this._pc);
/*      */       }
/*  925 */       else if (cmd.equalsIgnoreCase("1q")) {
/*  926 */         this._pc.setnewaiq1(1);
/*      */         
/*  928 */         if (this._pc.getnewai4() == 0) {
/*  929 */           this._pc.setnewai4(this._pc.getnewaiq1());
/*  930 */         } else if (this._pc.getnewai5() == 0) {
/*  931 */           this._pc.setnewai5(this._pc.getnewaiq1());
/*  932 */         } else if (this._pc.getnewai6() == 0) {
/*  933 */           this._pc.setnewai6(this._pc.getnewaiq1());
/*      */         } 
/*  935 */         newai(this._pc);
/*  936 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/*  937 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/*  938 */           this._pc.setnewai4(0);
/*  939 */           this._pc.setnewai5(0);
/*  940 */           this._pc.setnewai6(0);
/*  941 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/*  942 */           this._pc.killSkillEffectTimer(6933);
/*  943 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/*  945 */       } else if (cmd.equalsIgnoreCase("2q")) {
/*  946 */         this._pc.setnewaiq2(2);
/*  947 */         if (this._pc.getnewai4() == 0) {
/*  948 */           this._pc.setnewai4(this._pc.getnewaiq2());
/*  949 */         } else if (this._pc.getnewai5() == 0) {
/*  950 */           this._pc.setnewai5(this._pc.getnewaiq2());
/*  951 */         } else if (this._pc.getnewai6() == 0) {
/*  952 */           this._pc.setnewai6(this._pc.getnewaiq2());
/*      */         } 
/*  954 */         newai(this._pc);
/*  955 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/*  956 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/*  957 */           this._pc.setnewai4(0);
/*  958 */           this._pc.setnewai5(0);
/*  959 */           this._pc.setnewai6(0);
/*  960 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/*  961 */           this._pc.killSkillEffectTimer(6933);
/*  962 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/*  964 */       } else if (cmd.equalsIgnoreCase("3q")) {
/*  965 */         this._pc.setnewaiq3(3);
/*  966 */         if (this._pc.getnewai4() == 0) {
/*  967 */           this._pc.setnewai4(this._pc.getnewaiq3());
/*  968 */         } else if (this._pc.getnewai5() == 0) {
/*  969 */           this._pc.setnewai5(this._pc.getnewaiq3());
/*  970 */         } else if (this._pc.getnewai6() == 0) {
/*  971 */           this._pc.setnewai6(this._pc.getnewaiq3());
/*      */         } 
/*  973 */         newai(this._pc);
/*  974 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/*  975 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/*  976 */           this._pc.setnewai4(0);
/*  977 */           this._pc.setnewai5(0);
/*  978 */           this._pc.setnewai6(0);
/*  979 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/*  980 */           this._pc.killSkillEffectTimer(6933);
/*  981 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/*  983 */       } else if (cmd.equalsIgnoreCase("4q")) {
/*  984 */         this._pc.setnewaiq4(4);
/*  985 */         if (this._pc.getnewai4() == 0) {
/*  986 */           this._pc.setnewai4(this._pc.getnewaiq4());
/*  987 */         } else if (this._pc.getnewai5() == 0) {
/*  988 */           this._pc.setnewai5(this._pc.getnewaiq4());
/*  989 */         } else if (this._pc.getnewai6() == 0) {
/*  990 */           this._pc.setnewai6(this._pc.getnewaiq4());
/*      */         } 
/*  992 */         newai(this._pc);
/*  993 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/*  994 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/*  995 */           this._pc.setnewai4(0);
/*  996 */           this._pc.setnewai5(0);
/*  997 */           this._pc.setnewai6(0);
/*  998 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/*  999 */           this._pc.setSkillEffect(6930, 1200000);
/* 1000 */           this._pc.killSkillEffectTimer(6933);
/*      */         } 
/* 1002 */       } else if (cmd.equalsIgnoreCase("5q")) {
/* 1003 */         this._pc.setnewaiq5(5);
/* 1004 */         if (this._pc.getnewai4() == 0) {
/* 1005 */           this._pc.setnewai4(this._pc.getnewaiq5());
/* 1006 */         } else if (this._pc.getnewai5() == 0) {
/* 1007 */           this._pc.setnewai5(this._pc.getnewaiq5());
/* 1008 */         } else if (this._pc.getnewai6() == 0) {
/* 1009 */           this._pc.setnewai6(this._pc.getnewaiq5());
/*      */         } 
/* 1011 */         newai(this._pc);
/* 1012 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1013 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1014 */           this._pc.setnewai4(0);
/* 1015 */           this._pc.setnewai5(0);
/* 1016 */           this._pc.setnewai6(0);
/* 1017 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1018 */           this._pc.killSkillEffectTimer(6933);
/* 1019 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1021 */       } else if (cmd.equalsIgnoreCase("6q")) {
/* 1022 */         this._pc.setnewaiq6(6);
/* 1023 */         if (this._pc.getnewai4() == 0) {
/* 1024 */           this._pc.setnewai4(this._pc.getnewaiq6());
/* 1025 */         } else if (this._pc.getnewai5() == 0) {
/* 1026 */           this._pc.setnewai5(this._pc.getnewaiq6());
/* 1027 */         } else if (this._pc.getnewai6() == 0) {
/* 1028 */           this._pc.setnewai6(this._pc.getnewaiq6());
/*      */         } 
/* 1030 */         newai(this._pc);
/* 1031 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1032 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1033 */           this._pc.setnewai4(0);
/* 1034 */           this._pc.setnewai5(0);
/* 1035 */           this._pc.setnewai6(0);
/* 1036 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1037 */           this._pc.killSkillEffectTimer(6933);
/* 1038 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1040 */       } else if (cmd.equalsIgnoreCase("7q")) {
/* 1041 */         this._pc.setnewaiq7(7);
/* 1042 */         if (this._pc.getnewai4() == 0) {
/* 1043 */           this._pc.setnewai4(this._pc.getnewaiq7());
/* 1044 */         } else if (this._pc.getnewai5() == 0) {
/* 1045 */           this._pc.setnewai5(this._pc.getnewaiq7());
/* 1046 */         } else if (this._pc.getnewai6() == 0) {
/* 1047 */           this._pc.setnewai6(this._pc.getnewaiq7());
/*      */         } 
/* 1049 */         newai(this._pc);
/* 1050 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1051 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1052 */           this._pc.setnewai4(0);
/* 1053 */           this._pc.setnewai5(0);
/* 1054 */           this._pc.setnewai6(0);
/* 1055 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1056 */           this._pc.killSkillEffectTimer(6933);
/* 1057 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1059 */       } else if (cmd.equalsIgnoreCase("8q")) {
/* 1060 */         this._pc.setnewaiq8(8);
/* 1061 */         if (this._pc.getnewai4() == 0) {
/* 1062 */           this._pc.setnewai4(this._pc.getnewaiq8());
/* 1063 */         } else if (this._pc.getnewai5() == 0) {
/* 1064 */           this._pc.setnewai5(this._pc.getnewaiq8());
/* 1065 */         } else if (this._pc.getnewai6() == 0) {
/* 1066 */           this._pc.setnewai6(this._pc.getnewaiq8());
/*      */         } 
/* 1068 */         newai(this._pc);
/* 1069 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1070 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1071 */           this._pc.setnewai4(0);
/* 1072 */           this._pc.setnewai5(0);
/* 1073 */           this._pc.setnewai6(0);
/* 1074 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1075 */           this._pc.killSkillEffectTimer(6933);
/* 1076 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1078 */       } else if (cmd.equalsIgnoreCase("9q")) {
/* 1079 */         this._pc.setnewaiq9(9);
/* 1080 */         if (this._pc.getnewai4() == 0) {
/* 1081 */           this._pc.setnewai4(this._pc.getnewaiq9());
/* 1082 */         } else if (this._pc.getnewai5() == 0) {
/* 1083 */           this._pc.setnewai5(this._pc.getnewaiq9());
/* 1084 */         } else if (this._pc.getnewai6() == 0) {
/* 1085 */           this._pc.setnewai6(this._pc.getnewaiq9());
/*      */         } 
/* 1087 */         newai(this._pc);
/* 1088 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1089 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1090 */           this._pc.setnewai4(0);
/* 1091 */           this._pc.setnewai5(0);
/* 1092 */           this._pc.setnewai6(0);
/* 1093 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1094 */           this._pc.killSkillEffectTimer(6933);
/* 1095 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1097 */       } else if (cmd.equalsIgnoreCase("0q")) {
/* 1098 */         this._pc.setnewaiq0(0);
/* 1099 */         if (this._pc.getnewai4() == 0) {
/* 1100 */           this._pc.setnewai4(this._pc.getnewaiq1());
/* 1101 */         } else if (this._pc.getnewai5() == 0) {
/* 1102 */           this._pc.setnewai5(this._pc.getnewaiq1());
/* 1103 */         } else if (this._pc.getnewai6() == 0) {
/* 1104 */           this._pc.setnewai6(this._pc.getnewaiq1());
/*      */         } 
/* 1106 */         newai(this._pc);
/* 1107 */         if (this._pc.getnewai4() == this._pc.getnewai1() && this._pc.getnewai5() == this._pc.getnewai2() && this._pc.getnewai6() == this._pc.getnewai3()) {
/* 1108 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("恭喜您驗證成功"));
/* 1109 */           this._pc.setnewai4(0);
/* 1110 */           this._pc.setnewai5(0);
/* 1111 */           this._pc.setnewai6(0);
/* 1112 */           this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/* 1113 */           this._pc.killSkillEffectTimer(6933);
/* 1114 */           this._pc.setSkillEffect(6930, 1200000);
/*      */         } 
/* 1116 */       } else if (cmd.equalsIgnoreCase("locerr1")) {
/* 1117 */         if (this._pc.isParalyzed() || this._pc.isSleeped() || this._pc.isParalyzedX()) {
/*      */           return;
/*      */         }
/* 1120 */         if (this._pc.isDead()) {
/*      */           return;
/*      */         }
/*      */         
/* 1124 */         if (this._pc.isInvisble()) {
/*      */           return;
/*      */         }
/* 1127 */         if (this._pc.hasSkillEffect(55889)) {
/* 1128 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("無法連續使用該功能"));
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1133 */         this._pc.setSkillEffect(55889, 30000);
/* 1134 */         this._pc.set_unfreezingTime(5);
/*      */       }
/* 1136 */       else if (cmd.equalsIgnoreCase("locerr2")) {
/* 1137 */         if (this._pc.isParalyzed() || this._pc.isSleeped() || this._pc.isParalyzedX()) {
/*      */           return;
/*      */         }
/* 1140 */         if (this._pc.isDead()) {
/*      */           return;
/*      */         }
/*      */         
/* 1144 */         if (this._pc.isInvisble()) {
/*      */           return;
/*      */         }
/* 1147 */         if (this._pc.hasSkillEffect(55889)) {
/* 1148 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("無法連續使用該功能"));
/*      */           
/*      */           return;
/*      */         } 
/* 1153 */         this._pc.setSkillEffect(55889, 30000);
/* 1154 */         this._pc.set_misslocTime(3);
/*      */       }
/* 1162 */       else if (cmd.equalsIgnoreCase("index")) {
/* 1163 */         this._pc.isWindows();
/* 1164 */       } else if (cmd.equalsIgnoreCase("gfxid")) {
/* 1165 */         if (this._pc.getopengfxid()) {
/* 1166 */           this._pc.setopengfxid(false);
/* 1167 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("關閉-畫面中玩家特效"));
/*      */         } else {
/* 1169 */           this._pc.setopengfxid(true);
/* 1170 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("開啟-畫面中玩家特效"));
/*      */         } 
/* 1172 */       } else if (cmd.equalsIgnoreCase("npcdropmenu")) {
/* 1173 */         this._pc.setItemName(true);
/* 1174 */         this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY請輸入要查詢道具名稱後，按(Enter)。"));
/*      */       }
/* 1178 */       else if (cmd.equalsIgnoreCase("pc_score")) {
/* 1179 */         if (this._pc.get_other().get_score() >= 0) {
/* 1180 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\aD目前陣營積分:" + this._pc.get_other().get_score()));
/*      */         }
/*      */       }
/* 1215 */       else if (cmd.equalsIgnoreCase("kor_meau")) {
/* 1216 */         if (this._pc.isActived()) {
/* 1217 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1220 */         String type1 = "";
/* 1221 */         String type2 = "";
/* 1222 */         String type3 = "";
/* 1223 */         String type4 = "";
/* 1224 */         String type5 = "";
/* 1225 */         if (this._pc.hasSkillEffect(1688)) {
/* 1226 */           type1 = "開啟";
/*      */         } else {
/* 1228 */           type1 = "關閉";
/*      */         } 
/* 1230 */         if (this._pc.hasSkillEffect(1689)) {
/* 1231 */           type2 = "開啟";
/*      */         } else {
/* 1233 */           type2 = "關閉";
/*      */         } 
/* 1235 */         if (this._pc.hasSkillEffect(1690)) {
/* 1236 */           type3 = "開啟";
/*      */         } else {
/* 1238 */           type3 = "關閉";
/*      */         } 
/* 1240 */         if (this._pc.hasSkillEffect(1691)) {
/* 1241 */           type4 = "開啟";
/*      */         } else {
/* 1243 */           type4 = "關閉";
/*      */         } 
/* 1245 */         if (this._pc.hasSkillEffect(1692)) {
/* 1246 */           type5 = "開啟";
/*      */         } else {
/* 1248 */           type5 = "關閉";
/*      */         } 
/* 1250 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1255 */       else if (cmd.equalsIgnoreCase("aa")) {
/* 1256 */         boolean isTalent = false;
/* 1257 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1258 */           isTalent = true;
/*      */         }
/* 1260 */         if (isTalent) {
/* 1261 */           if (this._pc.getlogpcpower_SkillFor1() >= 10) {
/* 1262 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("該天賦技能已滿"));
/* 1263 */             isTalent = false;
/*      */           } else {
/* 1265 */             this._pc.setlogpcpower_SkillFor1(this._pc.getlogpcpower_SkillFor1() + 1);
/* 1266 */             this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
/*      */           } 
/*      */         }
/* 1269 */         String[] info = new String[6];
/* 1270 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1271 */           info[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
/*      */         } else {
/* 1273 */           info[0] = "0";
/*      */         } 
/* 1275 */         if (this._pc.getlogpcpower_SkillFor1() > 0) {
/* 1276 */           info[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
/*      */         } else {
/* 1278 */           info[1] = "0";
/*      */         } 
/* 1280 */         if (this._pc.getlogpcpower_SkillFor2() > 0) {
/* 1281 */           info[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
/*      */         } else {
/* 1283 */           info[2] = "0";
/*      */         } 
/* 1285 */         if (this._pc.getlogpcpower_SkillFor3() > 0) {
/* 1286 */           info[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
/*      */         } else {
/* 1288 */           info[3] = "0";
/*      */         } 
/* 1290 */         if (this._pc.getlogpcpower_SkillFor4() > 0) {
/* 1291 */           info[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
/*      */         } else {
/* 1293 */           info[4] = "0";
/*      */         } 
/* 1295 */         if (this._pc.getlogpcpower_SkillFor5() > 0) {
/* 1296 */           info[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
/*      */         } else {
/* 1298 */           info[5] = "0";
/*      */         } 
/* 1300 */         if (this._pc.isCrown()) {
/* 1301 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talCrown", info));
/* 1302 */         } else if (this._pc.isKnight()) {
/* 1303 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talKnight", info));
/* 1304 */         } else if (this._pc.isWizard()) {
/* 1305 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talWizard", info));
/* 1306 */         } else if (this._pc.isElf()) {
/* 1307 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talElf", info));
/* 1308 */         } else if (this._pc.isDarkelf()) {
/* 1309 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info));
/* 1310 */         } else if (this._pc.isDragonKnight()) {
/* 1311 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info));
/* 1312 */         } else if (this._pc.isIllusionist()) {
/* 1313 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info));
/*      */         }
/*      */       
/* 1316 */       } else if (cmd.equalsIgnoreCase("bb")) {
/* 1317 */         boolean isTalent = false;
/* 1318 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1319 */           isTalent = true;
/*      */         }
/* 1321 */         if (isTalent) {
/* 1322 */           if (this._pc.getlogpcpower_SkillFor2() >= 10) {
/* 1323 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("該天賦技能已滿"));
/* 1324 */             isTalent = false;
/*      */           } else {
/* 1326 */             this._pc.setlogpcpower_SkillFor2(this._pc.getlogpcpower_SkillFor2() + 1);
/* 1327 */             this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
/*      */           } 
/*      */         }
/* 1330 */         String[] info = new String[6];
/* 1331 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1332 */           info[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
/*      */         } else {
/* 1334 */           info[0] = "0";
/*      */         } 
/* 1336 */         if (this._pc.getlogpcpower_SkillFor1() > 0) {
/* 1337 */           info[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
/*      */         } else {
/* 1339 */           info[1] = "0";
/*      */         } 
/* 1341 */         if (this._pc.getlogpcpower_SkillFor2() > 0) {
/* 1342 */           info[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
/*      */         } else {
/* 1344 */           info[2] = "0";
/*      */         } 
/* 1346 */         if (this._pc.getlogpcpower_SkillFor3() > 0) {
/* 1347 */           info[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
/*      */         } else {
/* 1349 */           info[3] = "0";
/*      */         } 
/* 1351 */         if (this._pc.getlogpcpower_SkillFor4() > 0) {
/* 1352 */           info[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
/*      */         } else {
/* 1354 */           info[4] = "0";
/*      */         } 
/* 1356 */         if (this._pc.getlogpcpower_SkillFor5() > 0) {
/* 1357 */           info[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
/*      */         } else {
/* 1359 */           info[5] = "0";
/*      */         } 
/* 1361 */         if (this._pc.isCrown()) {
/* 1362 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talCrown", info));
/* 1363 */         } else if (this._pc.isKnight()) {
/* 1364 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talKnight", info));
/* 1365 */         } else if (this._pc.isWizard()) {
/* 1366 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talWizard", info));
/* 1367 */         } else if (this._pc.isElf()) {
/* 1368 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talElf", info));
/* 1369 */         } else if (this._pc.isDarkelf()) {
/* 1370 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info));
/* 1371 */         } else if (this._pc.isDragonKnight()) {
/* 1372 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info));
/* 1373 */         } else if (this._pc.isIllusionist()) {
/* 1374 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info));
/*      */         } 
/* 1376 */       } else if (cmd.equalsIgnoreCase("cc")) {
/* 1377 */         boolean isTalent = false;
/* 1378 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1379 */           isTalent = true;
/*      */         }
/* 1381 */         if (isTalent) {
/* 1382 */           if (this._pc.getlogpcpower_SkillFor3() >= 10) {
/* 1383 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("該天賦技能已滿"));
/* 1384 */             isTalent = false;
/*      */           } else {
/* 1386 */             this._pc.setlogpcpower_SkillFor3(this._pc.getlogpcpower_SkillFor3() + 1);
/* 1387 */             this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
/*      */           } 
/*      */         }
/* 1390 */         String[] info = new String[6];
/* 1391 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1392 */           info[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
/*      */         } else {
/* 1394 */           info[0] = "0";
/*      */         } 
/* 1396 */         if (this._pc.getlogpcpower_SkillFor1() > 0) {
/* 1397 */           info[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
/*      */         } else {
/* 1399 */           info[1] = "0";
/*      */         } 
/* 1401 */         if (this._pc.getlogpcpower_SkillFor2() > 0) {
/* 1402 */           info[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
/*      */         } else {
/* 1404 */           info[2] = "0";
/*      */         } 
/* 1406 */         if (this._pc.getlogpcpower_SkillFor3() > 0) {
/* 1407 */           info[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
/*      */         } else {
/* 1409 */           info[3] = "0";
/*      */         } 
/* 1411 */         if (this._pc.getlogpcpower_SkillFor4() > 0) {
/* 1412 */           info[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
/*      */         } else {
/* 1414 */           info[4] = "0";
/*      */         } 
/* 1416 */         if (this._pc.getlogpcpower_SkillFor5() > 0) {
/* 1417 */           info[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
/*      */         } else {
/* 1419 */           info[5] = "0";
/*      */         } 
/* 1421 */         if (this._pc.isCrown()) {
/* 1422 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talCrown", info));
/* 1423 */         } else if (this._pc.isKnight()) {
/* 1424 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talKnight", info));
/* 1425 */         } else if (this._pc.isWizard()) {
/* 1426 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talWizard", info));
/* 1427 */         } else if (this._pc.isElf()) {
/* 1428 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talElf", info));
/* 1429 */         } else if (this._pc.isDarkelf()) {
/* 1430 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info));
/* 1431 */         } else if (this._pc.isDragonKnight()) {
/* 1432 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info));
/* 1433 */         } else if (this._pc.isIllusionist()) {
/* 1434 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info));
/*      */         } 
/* 1436 */       } else if (cmd.equalsIgnoreCase("dd")) {
/* 1437 */         boolean isTalent = false;
/* 1438 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1439 */           isTalent = true;
/*      */         }
/* 1441 */         if (isTalent) {
/* 1442 */           if (this._pc.getlogpcpower_SkillFor4() >= 10) {
/* 1443 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("該天賦技能已滿"));
/* 1444 */             isTalent = false;
/*      */           } else {
/* 1446 */             this._pc.setlogpcpower_SkillFor4(this._pc.getlogpcpower_SkillFor4() + 1);
/* 1447 */             this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
/*      */           } 
/*      */         }
/* 1450 */         String[] info = new String[6];
/* 1451 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1452 */           info[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
/*      */         } else {
/* 1454 */           info[0] = "0";
/*      */         } 
/* 1456 */         if (this._pc.getlogpcpower_SkillFor1() > 0) {
/* 1457 */           info[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
/*      */         } else {
/* 1459 */           info[1] = "0";
/*      */         } 
/* 1461 */         if (this._pc.getlogpcpower_SkillFor2() > 0) {
/* 1462 */           info[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
/*      */         } else {
/* 1464 */           info[2] = "0";
/*      */         } 
/* 1466 */         if (this._pc.getlogpcpower_SkillFor3() > 0) {
/* 1467 */           info[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
/*      */         } else {
/* 1469 */           info[3] = "0";
/*      */         } 
/* 1471 */         if (this._pc.getlogpcpower_SkillFor4() > 0) {
/* 1472 */           info[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
/*      */         } else {
/* 1474 */           info[4] = "0";
/*      */         } 
/* 1476 */         if (this._pc.getlogpcpower_SkillFor5() > 0) {
/* 1477 */           info[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
/*      */         } else {
/* 1479 */           info[5] = "0";
/*      */         } 
/* 1481 */         if (this._pc.isCrown()) {
/* 1482 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talCrown", info));
/* 1483 */         } else if (this._pc.isKnight()) {
/* 1484 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talKnight", info));
/* 1485 */         } else if (this._pc.isWizard()) {
/* 1486 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talWizard", info));
/* 1487 */         } else if (this._pc.isElf()) {
/* 1488 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talElf", info));
/* 1489 */         } else if (this._pc.isDarkelf()) {
/* 1490 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info));
/* 1491 */         } else if (this._pc.isDragonKnight()) {
/* 1492 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info));
/* 1493 */         } else if (this._pc.isIllusionist()) {
/* 1494 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info));
/*      */         } 
/* 1496 */       } else if (cmd.equalsIgnoreCase("ee")) {
/* 1497 */         boolean isTalent = false;
/* 1498 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1499 */           isTalent = true;
/*      */         }
/* 1501 */         if (isTalent) {
/* 1502 */           if (this._pc.getlogpcpower_SkillFor5() >= 10) {
/* 1503 */             this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("該天賦技能已滿"));
/* 1504 */             isTalent = false;
/*      */           } else {
/* 1506 */             this._pc.setlogpcpower_SkillFor5(this._pc.getlogpcpower_SkillFor5() + 1);
/* 1507 */             this._pc.setlogpcpower_SkillCount(this._pc.getlogpcpower_SkillCount() - 1);
/*      */             
/* 1509 */             if (this._pc.isDragonKnight()) {
/* 1510 */               if (this._pc.getlogpcpower_SkillFor5() == 1) {
/* 1511 */                 this._pc.addMaxHp(400);
/* 1512 */               } else if (this._pc.getlogpcpower_SkillFor5() == 2) {
/* 1513 */                 this._pc.addMaxHp(400);
/* 1514 */               } else if (this._pc.getlogpcpower_SkillFor5() == 3) {
/* 1515 */                 this._pc.addMaxHp(400);
/* 1516 */               } else if (this._pc.getlogpcpower_SkillFor5() == 4) {
/* 1517 */                 this._pc.addMaxHp(400);
/* 1518 */               } else if (this._pc.getlogpcpower_SkillFor5() == 5) {
/* 1519 */                 this._pc.addMaxHp(400);
/* 1520 */               } else if (this._pc.getlogpcpower_SkillFor5() == 6) {
/* 1521 */                 this._pc.addMaxHp(400);
/* 1522 */               } else if (this._pc.getlogpcpower_SkillFor5() == 7) {
/* 1523 */                 this._pc.addMaxHp(400);
/* 1524 */               } else if (this._pc.getlogpcpower_SkillFor5() == 8) {
/* 1525 */                 this._pc.addMaxHp(400);
/* 1526 */               } else if (this._pc.getlogpcpower_SkillFor5() == 9) {
/* 1527 */                 this._pc.addMaxHp(400);
/* 1528 */               } else if (this._pc.getlogpcpower_SkillFor5() == 10) {
/* 1529 */                 this._pc.addMaxHp(400);
/*      */                 
/* 1531 */                 this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(this._pc));
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/* 1536 */         String[] info = new String[6];
/* 1537 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1538 */           info[0] = String.valueOf(this._pc.getlogpcpower_SkillCount());
/*      */         } else {
/* 1540 */           info[0] = "0";
/*      */         } 
/* 1542 */         if (this._pc.getlogpcpower_SkillFor1() > 0) {
/* 1543 */           info[1] = String.valueOf(this._pc.getlogpcpower_SkillFor1());
/*      */         } else {
/* 1545 */           info[1] = "0";
/*      */         } 
/* 1547 */         if (this._pc.getlogpcpower_SkillFor2() > 0) {
/* 1548 */           info[2] = String.valueOf(this._pc.getlogpcpower_SkillFor2());
/*      */         } else {
/* 1550 */           info[2] = "0";
/*      */         } 
/* 1552 */         if (this._pc.getlogpcpower_SkillFor3() > 0) {
/* 1553 */           info[3] = String.valueOf(this._pc.getlogpcpower_SkillFor3());
/*      */         } else {
/* 1555 */           info[3] = "0";
/*      */         } 
/* 1557 */         if (this._pc.getlogpcpower_SkillFor4() > 0) {
/* 1558 */           info[4] = String.valueOf(this._pc.getlogpcpower_SkillFor4());
/*      */         } else {
/* 1560 */           info[4] = "0";
/*      */         } 
/* 1562 */         if (this._pc.getlogpcpower_SkillFor5() > 0) {
/* 1563 */           info[5] = String.valueOf(this._pc.getlogpcpower_SkillFor5());
/*      */         } else {
/* 1565 */           info[5] = "0";
/*      */         } 
/* 1567 */         if (this._pc.isCrown()) {
/* 1568 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talCrown", info));
/* 1569 */         } else if (this._pc.isKnight()) {
/* 1570 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talKnight", info));
/* 1571 */         } else if (this._pc.isWizard()) {
/* 1572 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talWizard", info));
/* 1573 */         } else if (this._pc.isElf()) {
/* 1574 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talElf", info));
/* 1575 */         } else if (this._pc.isDarkelf()) {
/* 1576 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDarkelf", info));
/* 1577 */         } else if (this._pc.isDragonKnight()) {
/* 1578 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talDragonK", info));
/* 1579 */         } else if (this._pc.isIllusionist()) {
/* 1580 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "talIllusi", info));
/*      */         } 
/* 1582 */       } else if (cmd.equalsIgnoreCase("ff")) {
/* 1583 */         L1Item temp = ItemTable.get().getTemplate(ConfigOther.recaseff);
/* 1584 */         if (!this._pc.getInventory().checkItem(ConfigOther.recaseff, ConfigOther.recaseffcount)) {
/* 1585 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.valueOf(temp.getName()) + "不足" + ConfigOther.recaseffcount + "個"));
/*      */           return;
/*      */         } 
/* 1588 */         if (this._pc.getMeteLevel() == 0) {
/* 1589 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("您尚未轉身無法重置"));
/*      */           return;
/*      */         } 
/* 1592 */         if (this._pc.getlogpcpower_SkillCount() > 0) {
/* 1593 */           this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("請先將天賦點數點完"));
/*      */           return;
/*      */         } 
/* 1596 */         this._pc.getInventory().consumeItem(44070, 499L);
/*      */         
/* 1598 */         int SkillCount = this._pc.getlogpcpower_SkillFor1() + this._pc.getlogpcpower_SkillFor2() + 
/* 1599 */           this._pc.getlogpcpower_SkillFor3() + this._pc.getlogpcpower_SkillFor4() + this._pc.getlogpcpower_SkillFor5();
/* 1600 */         this._pc.setlogpcpower_SkillCount(SkillCount);
/*      */         
/* 1602 */         if (this._pc.isDragonKnight()) {
/*      */ 
/*      */           
/* 1605 */           this._pc.addMaxHp(-400 * this._pc.getlogpcpower_SkillFor5());
/* 1606 */           this._pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(this._pc));
/*      */         } 
/*      */ 
/*      */         
/* 1610 */         this._pc.setlogpcpower_SkillFor1(0);
/* 1611 */         this._pc.setlogpcpower_SkillFor2(0);
/* 1612 */         this._pc.setlogpcpower_SkillFor3(0);
/* 1613 */         this._pc.setlogpcpower_SkillFor4(0);
/* 1614 */         this._pc.setlogpcpower_SkillFor5(0);
/* 1615 */         this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("天賦能力已還原"));
/*      */         
/* 1617 */         this._pc.sendPackets((ServerBasePacket)new S_CloseList(this._pc.getId()));
/*      */       
/*      */       }
/* 1620 */       else if (cmd.equalsIgnoreCase("atkmessage")) {
/* 1621 */         if (this._pc.isActived()) {
/* 1622 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1625 */         if (this._pc.hasSkillEffect(1688)) {
/* 1626 */           this._pc.killSkillEffectTimer(1688);
/* 1627 */         } else if (!this._pc.hasSkillEffect(1688)) {
/* 1628 */           this._pc.setSkillEffect(1688, 0);
/*      */         } 
/* 1630 */         String type1 = "";
/* 1631 */         String type2 = "";
/* 1632 */         String type3 = "";
/* 1633 */         String type4 = "";
/* 1634 */         String type5 = "";
/* 1635 */         if (this._pc.hasSkillEffect(1688)) {
/* 1636 */           type1 = "開啟";
/*      */         } else {
/* 1638 */           type1 = "關閉";
/*      */         } 
/* 1640 */         if (this._pc.hasSkillEffect(1689)) {
/* 1641 */           type2 = "開啟";
/*      */         } else {
/* 1643 */           type2 = "關閉";
/*      */         } 
/* 1645 */         if (this._pc.hasSkillEffect(1690)) {
/* 1646 */           type3 = "開啟";
/*      */         } else {
/* 1648 */           type3 = "關閉";
/*      */         } 
/* 1650 */         if (this._pc.hasSkillEffect(1691)) {
/* 1651 */           type4 = "開啟";
/*      */         } else {
/* 1653 */           type4 = "關閉";
/*      */         } 
/* 1655 */         if (this._pc.hasSkillEffect(1692)) {
/* 1656 */           type5 = "開啟";
/*      */         } else {
/* 1658 */           type5 = "關閉";
/*      */         } 
/* 1660 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/* 1661 */       } else if (cmd.equalsIgnoreCase("partymessage")) {
/* 1662 */         if (this._pc.isActived()) {
/* 1663 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1666 */         if (this._pc.hasSkillEffect(1689)) {
/* 1667 */           this._pc.killSkillEffectTimer(1689);
/* 1668 */         } else if (!this._pc.hasSkillEffect(1689)) {
/* 1669 */           this._pc.setSkillEffect(1689, 0);
/*      */         } 
/* 1671 */         String type1 = "";
/* 1672 */         String type2 = "";
/* 1673 */         String type3 = "";
/* 1674 */         String type4 = "";
/* 1675 */         String type5 = "";
/* 1676 */         if (this._pc.hasSkillEffect(1688)) {
/* 1677 */           type1 = "開啟";
/*      */         } else {
/* 1679 */           type1 = "關閉";
/*      */         } 
/* 1681 */         if (this._pc.hasSkillEffect(1689)) {
/* 1682 */           type2 = "開啟";
/*      */         } else {
/* 1684 */           type2 = "關閉";
/*      */         } 
/* 1686 */         if (this._pc.hasSkillEffect(1690)) {
/* 1687 */           type3 = "開啟";
/*      */         } else {
/* 1689 */           type3 = "關閉";
/*      */         } 
/* 1691 */         if (this._pc.hasSkillEffect(1691)) {
/* 1692 */           type4 = "開啟";
/*      */         } else {
/* 1694 */           type4 = "關閉";
/*      */         } 
/* 1696 */         if (this._pc.hasSkillEffect(1692)) {
/* 1697 */           type5 = "開啟";
/*      */         } else {
/* 1699 */           type5 = "關閉";
/*      */         } 
/* 1701 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/* 1702 */       } else if (cmd.equalsIgnoreCase("pcdrop")) {
/* 1703 */         if (this._pc.isActived()) {
/* 1704 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1707 */         if (this._pc.hasSkillEffect(1690)) {
/* 1708 */           this._pc.killSkillEffectTimer(1690);
/* 1709 */         } else if (!this._pc.hasSkillEffect(1690)) {
/* 1710 */           this._pc.setSkillEffect(1690, 0);
/*      */         } 
/* 1712 */         String type1 = "";
/* 1713 */         String type2 = "";
/* 1714 */         String type3 = "";
/* 1715 */         String type4 = "";
/* 1716 */         String type5 = "";
/* 1717 */         if (this._pc.hasSkillEffect(1688)) {
/* 1718 */           type1 = "開啟";
/*      */         } else {
/* 1720 */           type1 = "關閉";
/*      */         } 
/* 1722 */         if (this._pc.hasSkillEffect(1689)) {
/* 1723 */           type2 = "開啟";
/*      */         } else {
/* 1725 */           type2 = "關閉";
/*      */         } 
/* 1727 */         if (this._pc.hasSkillEffect(1690)) {
/* 1728 */           type3 = "開啟";
/*      */         } else {
/* 1730 */           type3 = "關閉";
/*      */         } 
/* 1732 */         if (this._pc.hasSkillEffect(1691)) {
/* 1733 */           type4 = "開啟";
/*      */         } else {
/* 1735 */           type4 = "關閉";
/*      */         } 
/* 1737 */         if (this._pc.hasSkillEffect(1692)) {
/* 1738 */           type5 = "開啟";
/*      */         } else {
/* 1740 */           type5 = "關閉";
/*      */         } 
/* 1742 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/* 1743 */       } else if (cmd.equalsIgnoreCase("alldrop")) {
/* 1744 */         if (this._pc.isActived()) {
/* 1745 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1748 */         if (this._pc.hasSkillEffect(1691)) {
/* 1749 */           this._pc.killSkillEffectTimer(1691);
/* 1750 */         } else if (!this._pc.hasSkillEffect(1691)) {
/* 1751 */           this._pc.setSkillEffect(1691, 0);
/*      */         } 
/* 1753 */         String type1 = "";
/* 1754 */         String type2 = "";
/* 1755 */         String type3 = "";
/* 1756 */         String type4 = "";
/* 1757 */         String type5 = "";
/* 1758 */         if (this._pc.hasSkillEffect(1688)) {
/* 1759 */           type1 = "開啟";
/*      */         } else {
/* 1761 */           type1 = "關閉";
/*      */         } 
/* 1763 */         if (this._pc.hasSkillEffect(1689)) {
/* 1764 */           type2 = "開啟";
/*      */         } else {
/* 1766 */           type2 = "關閉";
/*      */         } 
/* 1768 */         if (this._pc.hasSkillEffect(1690)) {
/* 1769 */           type3 = "開啟";
/*      */         } else {
/* 1771 */           type3 = "關閉";
/*      */         } 
/* 1773 */         if (this._pc.hasSkillEffect(1691)) {
/* 1774 */           type4 = "開啟";
/*      */         } else {
/* 1776 */           type4 = "關閉";
/*      */         } 
/* 1778 */         if (this._pc.hasSkillEffect(1692)) {
/* 1779 */           type5 = "開啟";
/*      */         } else {
/* 1781 */           type5 = "關閉";
/*      */         } 
/* 1783 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/* 1784 */       } else if (cmd.equalsIgnoreCase("yncheck")) {
/* 1785 */         if (this._pc.isActived()) {
/* 1786 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中無法使用開關"));
/*      */           return;
/*      */         } 
/* 1789 */         if (this._pc.hasSkillEffect(1692)) {
/* 1790 */           this._pc.killSkillEffectTimer(1692);
/* 1791 */         } else if (!this._pc.hasSkillEffect(1692)) {
/* 1792 */           this._pc.setSkillEffect(1692, 0);
/*      */         } 
/* 1794 */         String type1 = "";
/* 1795 */         String type2 = "";
/* 1796 */         String type3 = "";
/* 1797 */         String type4 = "";
/* 1798 */         String type5 = "";
/* 1799 */         if (this._pc.hasSkillEffect(1688)) {
/* 1800 */           type1 = "開啟";
/*      */         } else {
/* 1802 */           type1 = "關閉";
/*      */         } 
/* 1804 */         if (this._pc.hasSkillEffect(1689)) {
/* 1805 */           type2 = "開啟";
/*      */         } else {
/* 1807 */           type2 = "關閉";
/*      */         } 
/* 1809 */         if (this._pc.hasSkillEffect(1690)) {
/* 1810 */           type3 = "開啟";
/*      */         } else {
/* 1812 */           type3 = "關閉";
/*      */         } 
/* 1814 */         if (this._pc.hasSkillEffect(1691)) {
/* 1815 */           type4 = "開啟";
/*      */         } else {
/* 1817 */           type4 = "關閉";
/*      */         } 
/* 1819 */         if (this._pc.hasSkillEffect(1692)) {
/* 1820 */           type5 = "開啟";
/*      */         } else {
/* 1822 */           type5 = "關閉";
/*      */         } 
/* 1824 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "pconly3", new String[] { type1, type2, type3, type4, type5 }));
/*      */       
/*      */       }
/* 1827 */       else if (cmd.equalsIgnoreCase("newcharpra")) {
/* 1828 */         if (this._pc.hasSkillEffect(55899)) {
/* 1829 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("此功能無法連續使用"));
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/* 1834 */         if (!L1CastleLocation.checkInAllWarArea(this._pc.getX(), this._pc.getY(), this._pc.getMapId())) {
/* 1835 */           if (this._pc.getLevel() <= ConfigOther.newcharpralv) {
/* 1836 */             if (this._pc.getnewcharpra()) {
/* 1837 */               this._pc.setnewcharpra(false);
/* 1838 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("關閉-新手保護"));
/* 1839 */               this._pc.setSkillEffect(55899, 60000);
/* 1840 */             } else if (!this._pc.getnewcharpra()) {
/* 1841 */               this._pc.setnewcharpra(true);
/*      */               
/* 1843 */               this._pc.setSkillEffect(55899, 60000);
/* 1844 */               this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("開啟-新手保護"));
/*      */             } 
/*      */           } else {
/* 1847 */             this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("等級大於" + ConfigOther.newcharpralv + "無法對您進行保護了"));
/*      */           } 
/*      */         } else {
/* 1850 */           this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("旗子內禁止使用此功能"));
/*      */         } 
/* 1852 */       } else if (cmd.equalsIgnoreCase("qt")) {
/* 1853 */         showStartQuest(this._pc, this._pc.getId());
/*      */       }
/* 1855 */       else if (cmd.equalsIgnoreCase("quest")) {
/* 1856 */         showQuest(this._pc, this._pc.getId());
/*      */       }
/* 1858 */       else if (cmd.equalsIgnoreCase("questa")) {
/* 1859 */         showQuestAll(this._pc, this._pc.getId());
/*      */       }
/* 1861 */       else if (cmd.equalsIgnoreCase("i")) {
/* 1862 */         L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
/* 1863 */         this._pc.setTempID(0);
/*      */         
/* 1865 */         if (quest == null) {
/*      */           return;
/*      */         }
/* 1868 */         QuestClass.get().showQuest(this._pc, quest.get_id());
/*      */       }
/* 1870 */       else if (cmd.equalsIgnoreCase("d")) {
/* 1871 */         L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
/* 1872 */         this._pc.setTempID(0);
/*      */         
/* 1874 */         if (quest == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1878 */         if (this._pc.getQuest().isEnd(quest.get_id())) {
/* 1879 */           questDel(quest);
/*      */           
/*      */           return;
/*      */         } 
/* 1883 */         if (!this._pc.getQuest().isStart(quest.get_id())) {
/*      */           
/* 1885 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_q_not6"));
/*      */           
/*      */           return;
/*      */         } 
/* 1889 */         questDel(quest);
/*      */       }
/* 1891 */       else if (cmd.equalsIgnoreCase("dy")) {
/* 1892 */         L1Quest quest = QuestTable.get().getTemplate(this._pc.getTempID());
/* 1893 */         this._pc.setTempID(0);
/*      */         
/* 1895 */         if (quest == null) {
/*      */           return;
/*      */         }
/*      */         
/* 1899 */         if (this._pc.getQuest().isEnd(quest.get_id())) {
/* 1900 */           isDel(quest);
/*      */           
/*      */           return;
/*      */         } 
/* 1904 */         if (!this._pc.getQuest().isStart(quest.get_id())) {
/*      */           
/* 1906 */           this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_q_not6"));
/*      */           
/*      */           return;
/*      */         } 
/* 1910 */         isDel(quest);
/*      */       }
/* 1912 */       else if (cmd.equalsIgnoreCase("up")) {
/* 1913 */         int page = this._pc.get_other().get_page() - 1;
/* 1914 */         L1ActionShowHtml show = new L1ActionShowHtml(this._pc);
/* 1915 */         show.showQuestMap(page);
/*      */       }
/* 1917 */       else if (cmd.equalsIgnoreCase("dn")) {
/* 1918 */         int page = this._pc.get_other().get_page() + 1;
/* 1919 */         L1ActionShowHtml show = new L1ActionShowHtml(this._pc);
/* 1920 */         show.showQuestMap(page);
/*      */       }
/* 1922 */       else if (cmd.equalsIgnoreCase("q0")) {
/* 1923 */         int key = this._pc.get_other().get_page() * 10 + 0;
/* 1924 */         showPage(key);
/*      */       }
/* 1926 */       else if (cmd.equalsIgnoreCase("q1")) {
/* 1927 */         int key = this._pc.get_other().get_page() * 10 + 1;
/* 1928 */         showPage(key);
/*      */       }
/* 1930 */       else if (cmd.equalsIgnoreCase("q2")) {
/* 1931 */         int key = this._pc.get_other().get_page() * 10 + 2;
/* 1932 */         showPage(key);
/*      */       }
/* 1934 */       else if (cmd.equalsIgnoreCase("q3")) {
/* 1935 */         int key = this._pc.get_other().get_page() * 10 + 3;
/* 1936 */         showPage(key);
/*      */       }
/* 1938 */       else if (cmd.equalsIgnoreCase("q4")) {
/* 1939 */         int key = this._pc.get_other().get_page() * 10 + 4;
/* 1940 */         showPage(key);
/*      */       }
/* 1942 */       else if (cmd.equalsIgnoreCase("q5")) {
/* 1943 */         int key = this._pc.get_other().get_page() * 10 + 5;
/* 1944 */         showPage(key);
/*      */       }
/* 1946 */       else if (cmd.equalsIgnoreCase("q6")) {
/* 1947 */         int key = this._pc.get_other().get_page() * 10 + 6;
/* 1948 */         showPage(key);
/*      */       }
/* 1950 */       else if (cmd.equalsIgnoreCase("q7")) {
/* 1951 */         int key = this._pc.get_other().get_page() * 10 + 7;
/* 1952 */         showPage(key);
/*      */       }
/* 1954 */       else if (cmd.equalsIgnoreCase("q8")) {
/* 1955 */         int key = this._pc.get_other().get_page() * 10 + 8;
/* 1956 */         showPage(key);
/*      */       }
/* 1958 */       else if (cmd.equalsIgnoreCase("q9")) {
/* 1959 */         int key = this._pc.get_other().get_page() * 10 + 9;
/* 1960 */         showPage(key);
/*      */       }
/*      */     }
/* 1967 */     catch (Exception e) {
/* 1968 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void usePolyScroll(L1PcInstance pc, L1ItemInstance item, String s) {
/*      */     try {
/* 1980 */       L1PolyMorph poly = PolyTable.get().getTemplate(s);
/* 1981 */       int time = 1800;
/* 1982 */       if (item.getBless() == 0) {
/* 1983 */         time = 2100;
/*      */       }
/* 1985 */       if (item.getBless() == 128) {
/* 1986 */         time = 2100;
/*      */       }
/* 1988 */       boolean isUseItem = false;
/* 1989 */       if (s.equals("none")) {
/* 1990 */         if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
/* 1991 */           isUseItem = true;
/*      */         } else {
/* 1993 */           L1PolyMorph.undoPoly((L1Character)pc);
/*      */           
/* 1995 */           isUseItem = true;
/*      */         } 
/* 1997 */       } else if (poly.getMinLevel() <= pc.getLevel() || pc.isGm()) {
/*      */         
/* 1999 */         if (poly.getPolyId() == 13715 && (pc.get_sex() != 0 || !pc.isCrown())) {
/* 2000 */           isUseItem = false;
/* 2001 */         } else if (poly.getPolyId() == 13717 && (pc.get_sex() != 1 || !pc.isCrown())) {
/* 2002 */           isUseItem = false;
/* 2003 */         } else if (poly.getPolyId() == 13719 && (pc.get_sex() != 0 || !pc.isKnight())) {
/* 2004 */           isUseItem = false;
/* 2005 */         } else if (poly.getPolyId() == 13721 && (pc.get_sex() != 1 || !pc.isKnight())) {
/* 2006 */           isUseItem = false;
/* 2007 */         } else if (poly.getPolyId() == 13723 && (pc.get_sex() != 0 || !pc.isElf())) {
/* 2008 */           isUseItem = false;
/* 2009 */         } else if (poly.getPolyId() == 13725 && (pc.get_sex() != 1 || !pc.isElf())) {
/* 2010 */           isUseItem = false;
/* 2011 */         } else if (poly.getPolyId() == 13727 && (pc.get_sex() != 0 || !pc.isWizard())) {
/* 2012 */           isUseItem = false;
/* 2013 */         } else if (poly.getPolyId() == 13729 && (pc.get_sex() != 1 || !pc.isWizard())) {
/* 2014 */           isUseItem = false;
/* 2015 */         } else if (poly.getPolyId() == 13731 && (pc.get_sex() != 0 || !pc.isDarkelf())) {
/* 2016 */           isUseItem = false;
/* 2017 */         } else if (poly.getPolyId() == 13733 && (pc.get_sex() != 1 || !pc.isDarkelf())) {
/* 2018 */           isUseItem = false;
/* 2019 */         } else if (poly.getPolyId() == 13735 && (pc.get_sex() != 0 || !pc.isDragonKnight())) {
/* 2020 */           isUseItem = false;
/* 2021 */         } else if (poly.getPolyId() == 13737 && (pc.get_sex() != 1 || !pc.isDragonKnight())) {
/* 2022 */           isUseItem = false;
/* 2023 */         } else if (poly.getPolyId() == 13739 && (pc.get_sex() != 0 || !pc.isIllusionist())) {
/* 2024 */           isUseItem = false;
/* 2025 */         } else if (poly.getPolyId() == 13741 && (pc.get_sex() != 1 || !pc.isIllusionist())) {
/* 2026 */           isUseItem = false;
/*      */         }
/*      */         else {
/*      */           
/* 2030 */           L1PolyMorph.doPoly((L1Character)pc, poly.getPolyId(), time, 1);
/* 2031 */           isUseItem = true;
/*      */         } 
/*      */       } 
/*      */       
/* 2035 */       if (isUseItem) {
/* 2036 */         pc.getInventory().removeItem(item, 1L);
/* 2037 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } else {
/* 2039 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(181));
/* 2040 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } 
/*      */       
/* 2043 */       pc.setItemPoly(false);
/* 2044 */       pc.setPolyScroll(null);
/*      */     }
/* 2046 */     catch (Exception e) {
/* 2047 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void usePolyBook(L1PcInstance pc, L1ItemInstance item, String s) {
/*      */     try {
/* 2061 */       L1PolyMorph poly = PolyTable.get().getTemplate(s);
/* 2062 */       int time = 1800;
/* 2063 */       if (item.getBless() == 0) {
/* 2064 */         time = 2100;
/*      */       }
/* 2066 */       if (item.getBless() == 128) {
/* 2067 */         time = 2100;
/*      */       }
/* 2069 */       boolean isUseItem = false;
/* 2070 */       if (s.equals("none")) {
/* 2071 */         if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
/* 2072 */           isUseItem = true;
/*      */         } else {
/* 2074 */           L1PolyMorph.undoPoly((L1Character)pc);
/*      */           
/* 2076 */           isUseItem = true;
/*      */         } 
/*      */       } else {
/* 2079 */         L1PolyMorph.doPoly((L1Character)pc, poly.getPolyId(), time, 1);
/* 2080 */         isUseItem = true;
/*      */       } 
/* 2090 */       if (isUseItem) {
/* 2091 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } else {
/* 2093 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(181));
/* 2094 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } 
/* 2096 */       pc.setItemPoly(false);
/* 2097 */       pc.setPolyScroll(null);
/*      */     }
/* 2099 */     catch (Exception e) {
/* 2100 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void usePolyBookcouitem(L1PcInstance pc, L1ItemInstance item, String s) {
/*      */     try {
/* 2105 */       L1PolyMorph poly = PolyTable.get().getTemplate(s);
/* 2106 */       int time = 1800;
/* 2107 */       if (item.getBless() == 0) {
/* 2108 */         time = 2100;
/*      */       }
/* 2110 */       if (item.getBless() == 128) {
/* 2111 */         time = 2100;
/*      */       }
/* 2113 */       boolean isUseItem = false;
/* 2114 */       if (s.equals("none")) {
/* 2115 */         if (pc.getTempCharGfx() == 6034 || pc.getTempCharGfx() == 6035) {
/* 2116 */           isUseItem = true;
/*      */         } else {
/* 2118 */           L1PolyMorph.undoPoly((L1Character)pc);
/* 2119 */           isUseItem = true;
/*      */         } 
/*      */       } else {
/* 2122 */         L1PolyMorph.doPoly((L1Character)pc, poly.getPolyId(), time, 1);
/* 2123 */         isUseItem = true;
/*      */       } 
/*      */       
/* 2126 */       if (isUseItem) {
/* 2127 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } else {
/* 2129 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(181));
/* 2130 */         pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*      */       } 
/* 2132 */       pc.setItemPoly(false);
/* 2133 */       pc.setPolyScroll(null);
/* 2134 */       pc.getInventory().removeItem(item, 1L);
/* 2135 */     } catch (Exception e) {
/* 2136 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void usePhantomTeleport(L1PcInstance pc, String cmd) {
/*      */     try {
/* 2148 */       int x = 0;
/* 2149 */       int y = 0;
/* 2150 */       short mapid = 0;
/*      */       String str;
/* 2152 */       switch ((str = cmd).hashCode()) { case 97: if (!str.equals("a"))
/*      */             break; 
/* 2154 */           x = 32797;
/* 2155 */           y = 32799;
/* 2156 */           mapid = 3301; break;
/*      */         case 98:
/*      */           if (!str.equals("b"))
/* 2159 */             break;  x = 32797;
/* 2160 */           y = 32799;
/* 2161 */           mapid = 3302; break;
/*      */         case 99:
/*      */           if (!str.equals("c"))
/* 2164 */             break;  x = 32797;
/* 2165 */           y = 32799;
/* 2166 */           mapid = 3303; break;
/*      */         case 100:
/*      */           if (!str.equals("d"))
/* 2169 */             break;  x = 32668;
/* 2170 */           y = 32864;
/* 2171 */           mapid = 3304; break;
/*      */         case 101:
/*      */           if (!str.equals("e"))
/* 2174 */             break;  x = 32668;
/* 2175 */           y = 32864;
/* 2176 */           mapid = 3305; break;
/*      */         case 102:
/*      */           if (!str.equals("f"))
/* 2179 */             break;  x = 32717;
/* 2180 */           y = 32871;
/* 2181 */           mapid = 3306; break;
/*      */         case 103:
/*      */           if (!str.equals("g"))
/* 2184 */             break;  x = 32668;
/* 2185 */           y = 32864;
/* 2186 */           mapid = 3307; break;
/*      */         case 104:
/*      */           if (!str.equals("h"))
/* 2189 */             break;  x = 32668;
/* 2190 */           y = 32864;
/* 2191 */           mapid = 3308; break;
/*      */         case 105:
/*      */           if (!str.equals("i"))
/* 2194 */             break;  x = 32668;
/* 2195 */           y = 32864;
/* 2196 */           mapid = 3309; break;
/*      */         case 106:
/*      */           if (!str.equals("j"))
/* 2199 */             break;  x = 32797;
/* 2200 */           y = 32799;
/* 2201 */           mapid = 3310; break;
/*      */         case 107:
/*      */           if (!str.equals("k"))
/* 2204 */             break;  x = 32760;
/* 2205 */           y = 32894;
/* 2206 */           mapid = 7100; break;
/*      */         case 108:
/*      */           if (!str.equals("l"))
/* 2209 */             break;  x = 32692;
/* 2210 */           y = 32903;
/* 2211 */           mapid = 7100;
/*      */           break; }
/* 2215 */       L1Teleport.teleport(pc, x, y, mapid, pc.getHeading(), true);
/* 2216 */       pc.setPhantomTeleport(false);
/*      */     }
/* 2218 */     catch (Exception e) {
/* 2219 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void questDel(L1Quest quest) {
/*      */     try {
/* 2230 */       if (quest.is_del()) {
/* 2231 */         this._pc.setTempID(quest.get_id());
/* 2232 */         String over = null;
/*      */         
/* 2234 */         if (this._pc.getQuest().isEnd(quest.get_id())) {
/* 2235 */           over = "完成任務";
/*      */         } else {
/* 2237 */           over = String.valueOf(this._pc.getQuest().get_step(quest.get_id())) + " / " + quest.get_difficulty();
/*      */         } 
/*      */         
/* 2240 */         String[] info = {
/* 2241 */             quest.get_questname(), 
/* 2242 */             Integer.toString(quest.get_questlevel()), 
/* 2243 */             over
/*      */           };
/*      */         
/* 2246 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_qi2", info));
/*      */       }
/*      */       else {
/*      */         
/* 2250 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_q_not5"));
/*      */       }
/*      */     
/* 2253 */     } catch (Exception e) {
/* 2254 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private void isDel(L1Quest quest) {
/*      */     try {
/* 2264 */       if (quest.is_del())
/*      */       {
/* 2266 */         QuestClass.get().stopQuest(this._pc, quest.get_id());
/*      */         
/* 2268 */         CharacterQuestReading.get().delQuest(this._pc.getId(), quest.get_id());
/* 2269 */         String[] info = {
/* 2270 */             quest.get_questname(), 
/* 2271 */             Integer.toString(quest.get_questlevel())
/*      */           };
/*      */         
/* 2274 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_qi3", info));
/*      */       }
/*      */       else
/*      */       {
/* 2278 */         this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_q_not5"));
/*      */       }
/*      */     
/* 2281 */     } catch (Exception e) {
/* 2282 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   public static void showStartQuest(L1PcInstance pc, int objid) {
/*      */     try {
/* 2294 */       (pc.get_otherList()).QUESTMAP.clear();
/*      */       
/* 2296 */       int key = 0;
/* 2297 */       for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; i++) {
/* 2298 */         L1Quest value = QuestTable.get().getTemplate(i);
/* 2299 */         if (value != null)
/*      */         {
/* 2301 */           if (!pc.getQuest().isEnd(value.get_id()))
/*      */           {
/*      */ 
/*      */             
/* 2305 */             if (pc.getQuest().isStart(value.get_id())) {
/* 2306 */               (pc.get_otherList()).QUESTMAP.put(Integer.valueOf(key++), value);
/*      */             }
/*      */           }
/*      */         }
/*      */       } 
/* 2311 */       if ((pc.get_otherList()).QUESTMAP.size() <= 0) {
/*      */         
/* 2313 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(objid, "y_q_not7"));
/*      */       } else {
/*      */         
/* 2316 */         L1ActionShowHtml show = new L1ActionShowHtml(pc);
/* 2317 */         show.showQuestMap(0);
/*      */       }
/*      */     
/* 2320 */     } catch (Exception e) {
/* 2321 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   public static void showQuest(L1PcInstance pc, int objid) {
/*      */     try {
/* 2333 */       (pc.get_otherList()).QUESTMAP.clear();
/*      */       
/* 2335 */       int key = 0;
/*      */       
/* 2337 */       for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; i++) {
/* 2338 */         L1Quest value = QuestTable.get().getTemplate(i);
/* 2339 */         if (value != null)
/*      */         {
/* 2341 */           if (pc.getLevel() >= value.get_questlevel())
/*      */           {
/* 2343 */             if (!pc.getQuest().isEnd(value.get_id()))
/*      */             {
/*      */ 
/*      */               
/* 2347 */               if (!pc.getQuest().isStart(value.get_id()))
/*      */               {
/*      */ 
/*      */                 
/* 2351 */                 if (value.check(pc))
/* 2352 */                   (pc.get_otherList()).QUESTMAP.put(Integer.valueOf(key++), value); 
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       } 
/* 2358 */       if ((pc.get_otherList()).QUESTMAP.size() <= 0) {
/*      */         
/* 2360 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(objid, "y_q_not4"));
/*      */       } else {
/*      */         
/* 2363 */         L1ActionShowHtml show = new L1ActionShowHtml(pc);
/* 2364 */         show.showQuestMap(0);
/*      */       }
/*      */     
/* 2367 */     } catch (Exception e) {
/* 2368 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   public static void showQuestAll(L1PcInstance pc, int objid) {
/*      */     try {
/* 2380 */       (pc.get_otherList()).QUESTMAP.clear();
/*      */       
/* 2382 */       int key = 0;
/* 2383 */       for (int i = QuestTable.MINQID; i <= QuestTable.MAXQID; i++) {
/* 2384 */         L1Quest value = QuestTable.get().getTemplate(i);
/* 2385 */         if (value != null)
/*      */         {
/* 2387 */           if (value.check(pc)) {
/* 2388 */             (pc.get_otherList()).QUESTMAP.put(Integer.valueOf(key++), value);
/*      */           }
/*      */         }
/*      */       } 
/* 2392 */       L1ActionShowHtml show = new L1ActionShowHtml(pc);
/* 2393 */       show.showQuestMap(0);
/*      */     }
/* 2395 */     catch (Exception e) {
/* 2396 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private static synchronized void checkSponsor(L1PcInstance pc) {
/* 2400 */     Connection con = null;
/* 2401 */     PreparedStatement pstm = null;
/* 2402 */     ResultSet rs = null;
/* 2403 */     PreparedStatement pstm2 = null;
/*      */ 
/*      */     
/*      */     try {
/* 2407 */       String AccountName = pc.getAccountName();
/* 2408 */       con = DatabaseFactory.get().getConnection();
/* 2409 */       pstm = con.prepareStatement("select ordernumber,amount,payname,state from ezpay where state = 1 and payname ='" + AccountName + "'");
/* 2410 */       rs = pstm.executeQuery();
/* 2411 */       boolean isfind = false;
/* 2415 */       while (rs.next() && rs != null) {
/* 2420 */         int serial = rs.getInt("ordernumber");
/*      */         
/* 2422 */         if (pc.getAccountName().equalsIgnoreCase(rs.getString("payname"))) {
/* 2423 */           isfind = true;
/* 2424 */           pstm2 = con.prepareStatement("update ezpay set state = 2 where ordernumber = ?");
/* 2425 */           pstm2.setInt(1, serial);
/* 2426 */           pstm2.execute();
/* 2427 */           int count = rs.getInt("amount");
/*      */           
/* 2429 */           GiveItem(pc, 44070, count);
/* 2430 */           PayBonus.getItem(pc, count);
/* 2431 */           pc.setpaycount(pc.getpaycount() + count);
/* 2432 */           writeSponsorlog(pc, count);
/*      */         } 
/*      */       } 
/* 2435 */       if (!isfind) {
/* 2436 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("尚未偵測到關於您的贊助。"));
/*      */       }
/*      */     }
/* 2439 */     catch (SQLException e) {
/* 2440 */       _log.error(e.getLocalizedMessage());
/*      */     } finally {
/*      */       
/* 2443 */       SQLUtil.close(rs);
/* 2444 */       SQLUtil.close(pstm);
/* 2445 */       SQLUtil.close(pstm2);
/* 2446 */       SQLUtil.close(con);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void GiveItem(L1PcInstance pc, int itemId, int count) {
/* 2452 */     L1ItemInstance item = ItemTable.get().createItem(itemId);
/* 2453 */     item.setCount(count);
/*      */     
/* 2455 */     if (pc.getInventory().checkAddItem(item, count) == 0) {
/* 2456 */       pc.getInventory().storeItem(item);
/* 2457 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("本次贊助共獲得： " + item.getLogName() + "。"));
/*      */     } 
/*      */   }

/*      */   public static void writeSponsorlog(L1PcInstance player, int count) {
/*      */     try {
/* 2468 */       File DeleteLog = new File("自動贊助\\贊助領取資料.log");
/* 2469 */       if (DeleteLog.createNewFile()) {
/* 2470 */         out = new BufferedWriter(new FileWriter("自動贊助\\贊助領取資料.log", false));
/* 2471 */         out.write("※以下是玩家[領取贊助]的所有紀錄※\r\n");
/* 2472 */         out.close();
/*      */       } 
/* 2474 */       out = new BufferedWriter(new FileWriter("自動贊助\\贊助領取資料.log", true));
/* 2475 */       out.write("\r\n");
/* 2476 */       out.write("來自帳號: " + player.getAccountName() + 
/* 2477 */           "來自ip: " + player.getNetConnection().getIp() + 
/* 2478 */           ",來自玩家: " + player.getName() + 
/* 2479 */           ",領取了: " + count + " 個 " + 
/* 2480 */           ",<領取時間:" + new Timestamp(System.currentTimeMillis()) + ">" + 
/* 2481 */           "\r\n");
/* 2482 */       out.close();
/* 2483 */     } catch (IOException e) {
/* 2484 */       System.out.println("以下是錯誤訊息: " + e.getMessage());
/*      */     } 
/*      */   }

/*      */   public static void initCharStatus(L1PcInstance pc, int inithp, int initmp, int str, int intel, int wis, int dex, int con, int cha) {
/* 2492 */     pc.addBaseMaxHp((short)(inithp - pc.getBaseMaxHp()));
/* 2493 */     pc.addBaseMaxMp((short)(initmp - pc.getBaseMaxMp()));
/* 2494 */     pc.addBaseStr((byte)(str - pc.getBaseStr()));
/* 2495 */     pc.addBaseInt((byte)(intel - pc.getBaseInt()));
/* 2496 */     pc.addBaseWis((byte)(wis - pc.getBaseWis()));
/* 2497 */     pc.addBaseDex((byte)(dex - pc.getBaseDex()));
/* 2498 */     pc.addBaseCon((byte)(con - pc.getBaseCon()));
/* 2499 */     pc.addBaseCha((byte)(cha - pc.getBaseCha()));
/*      */     
/* 2501 */     pc.getQuest().set_step(85, 0);
/* 2502 */     pc.getQuest().set_step(86, 0);
/* 2503 */     pc.getQuest().set_step(87, 0);
/*      */     
/* 2505 */     pc.getQuest().set_step(88, 0);
/* 2506 */     pc.getQuest().set_step(89, 0);
/* 2507 */     pc.getQuest().set_step(90, 0);
/*      */   }

/*      */   private boolean checkvalid(L1PcInstance pc, String cmd) {
/* 2516 */     int initpoint = C_CreateChar.ORIGINAL_AMOUNT[pc.getType()];
/*      */     
/* 2518 */     if (pc.isInCharReset()) {
/* 2519 */       if (cmd.equalsIgnoreCase("initStr")) {
/* 2520 */         if (pc.getBaseStr() >= C_CreateChar.MAX_STR[pc.getType()]) {
/* 2521 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2522 */           return false;
/*      */         } 
/* 2524 */         if (pc.getBaseStr() < 20) {
/* 2525 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2526 */             return true;
/*      */           }
/* 2528 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2531 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2534 */       } else if (cmd.equalsIgnoreCase("initInt")) {
/* 2535 */         if (pc.getBaseInt() >= C_CreateChar.MAX_INT[pc.getType()]) {
/* 2536 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2537 */           return false;
/*      */         } 
/* 2539 */         if (pc.getBaseInt() < 20) {
/* 2540 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2541 */             return true;
/*      */           }
/* 2543 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2546 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2549 */       } else if (cmd.equalsIgnoreCase("initWis")) {
/* 2550 */         if (pc.getBaseWis() >= C_CreateChar.MAX_WIS[pc.getType()]) {
/* 2551 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2552 */           return false;
/*      */         } 
/* 2554 */         if (pc.getBaseWis() < 20) {
/* 2555 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2556 */             return true;
/*      */           }
/* 2558 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2561 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2564 */       } else if (cmd.equalsIgnoreCase("initDex")) {
/* 2565 */         if (pc.getBaseDex() >= C_CreateChar.MAX_DEX[pc.getType()]) {
/* 2566 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2567 */           return false;
/*      */         } 
/* 2569 */         if (pc.getBaseDex() < 20) {
/* 2570 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2571 */             return true;
/*      */           }
/* 2573 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2576 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2579 */       } else if (cmd.equalsIgnoreCase("initCon")) {
/* 2580 */         if (pc.getBaseCon() >= C_CreateChar.MAX_CON[pc.getType()]) {
/* 2581 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2582 */           return false;
/*      */         } 
/* 2584 */         if (pc.getBaseCon() < 20) {
/* 2585 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2586 */             return true;
/*      */           }
/* 2588 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2591 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2594 */       } else if (cmd.equalsIgnoreCase("initCha")) {
/* 2595 */         if (pc.getBaseCha() >= C_CreateChar.MAX_CHA[pc.getType()]) {
/* 2596 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("該能力初始已達上限"));
/* 2597 */           return false;
/*      */         } 
/* 2599 */         if (pc.getBaseCha() < 20) {
/* 2600 */           if (initpoint - pc.getTempInitPoint() > 0) {
/* 2601 */             return true;
/*      */           }
/* 2603 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2606 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("初始點數上限只能到20。 請重試一次。"));
/*      */         }
/*      */       
/* 2609 */       } else if (cmd.equalsIgnoreCase("Str")) {
/* 2610 */         if (pc.getBaseStr() < ConfigAlt.BaseResetPOWER) {
/* 2611 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2612 */             return true;
/*      */           }
/* 2614 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2617 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2620 */       } else if (cmd.equalsIgnoreCase("Int")) {
/* 2621 */         if (pc.getBaseInt() < ConfigAlt.BaseResetPOWER) {
/* 2622 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2623 */             return true;
/*      */           }
/* 2625 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2628 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2631 */       } else if (cmd.equalsIgnoreCase("Wis")) {
/* 2632 */         if (pc.getBaseWis() < ConfigAlt.BaseResetPOWER) {
/* 2633 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2634 */             return true;
/*      */           }
/* 2636 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2639 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2642 */       } else if (cmd.equalsIgnoreCase("Dex")) {
/* 2643 */         if (pc.getBaseDex() < ConfigAlt.BaseResetPOWER) {
/* 2644 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2645 */             return true;
/*      */           }
/* 2647 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2650 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2653 */       } else if (cmd.equalsIgnoreCase("Con")) {
/* 2654 */         if (pc.getBaseCon() < ConfigAlt.BaseResetPOWER) {
/* 2655 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2656 */             return true;
/*      */           }
/* 2658 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2661 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2664 */       } else if (cmd.equalsIgnoreCase("Cha")) {
/* 2665 */         if (pc.getBaseCha() < ConfigAlt.BaseResetPOWER) {
/* 2666 */           if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/* 2667 */             return true;
/*      */           }
/* 2669 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2672 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.BaseResetPOWER + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2675 */       } else if (cmd.equalsIgnoreCase("exStr")) {
/* 2676 */         if (pc.getBaseStr() < ConfigAlt.POWERMEDICINE) {
/* 2677 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2678 */             return true;
/*      */           }
/* 2680 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2683 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2686 */       } else if (cmd.equalsIgnoreCase("exInt")) {
/* 2687 */         if (pc.getBaseInt() < ConfigAlt.POWERMEDICINE) {
/* 2688 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2689 */             return true;
/*      */           }
/* 2691 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2694 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2697 */       } else if (cmd.equalsIgnoreCase("exWis")) {
/* 2698 */         if (pc.getBaseWis() < ConfigAlt.POWERMEDICINE) {
/* 2699 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2700 */             return true;
/*      */           }
/* 2702 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2705 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2708 */       } else if (cmd.equalsIgnoreCase("exDex")) {
/* 2709 */         if (pc.getBaseDex() < ConfigAlt.POWERMEDICINE) {
/* 2710 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2711 */             return true;
/*      */           }
/* 2713 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2716 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2719 */       } else if (cmd.equalsIgnoreCase("exCon")) {
/* 2720 */         if (pc.getBaseCon() < ConfigAlt.POWERMEDICINE) {
/* 2721 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2722 */             return true;
/*      */           }
/* 2724 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2727 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2730 */       } else if (cmd.equalsIgnoreCase("exCha")) {
/* 2731 */         if (pc.getBaseCha() < ConfigAlt.POWERMEDICINE) {
/* 2732 */           if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/* 2733 */             return true;
/*      */           }
/* 2735 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經沒有多餘的點數了。"));
/*      */         } else {
/*      */           
/* 2738 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage("屬性最大值只能到" + ConfigAlt.POWERMEDICINE + "。 請重試一次。"));
/*      */         }
/*      */       
/* 2741 */       } else if (cmd.equalsIgnoreCase("1")) {
/* 2742 */         if (pc.getTempMaxLevel() - pc.getTempLevel() >= 1) {
/* 2743 */           return true;
/*      */         }
/* 2745 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("無法再繼續升級了。"));
/*      */       }
/* 2747 */       else if (cmd.equalsIgnoreCase("10")) {
/* 2748 */         if (pc.getTempMaxLevel() - pc.getTempLevel() >= 10) {
/* 2749 */           return true;
/*      */         }
/* 2751 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("無法再繼續升級了。"));
/*      */       } 
/*      */     } else {
/*      */       
/* 2755 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage("必須在人物重置模式下才能使用！"));
/*      */     } 
/* 2757 */     return false;
/*      */   }

/*      */   public static void checkInitPower(L1PcInstance pc) {
/* 2766 */     int initpoint = C_CreateChar.ORIGINAL_AMOUNT[pc.getType()];
/*      */     
/* 2768 */     String msg0 = "初始能力點數";
/* 2769 */     String msg1 = String.valueOf(initpoint - pc.getTempInitPoint());
/* 2770 */     String msg2 = "請選擇想要提升的屬性：";
/*      */     
/* 2772 */     if (initpoint - pc.getTempInitPoint() > 0) {
/*      */       
/* 2774 */       String[] msgs = { msg0, msg1, msg2 };
/*      */       
/* 2776 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "charreset1", msgs));
/*      */     } else {
/*      */       
/* 2779 */       if (pc.getLevel() == 1) {
/* 2780 */         CharacterTable.saveCharStatus(pc);
/*      */       }
/* 2782 */       checkLevelUp(pc);
/*      */     } 
/*      */   }

/*      */   private static void checkLevelUp(L1PcInstance pc) {
/* 2791 */     String msg0 = "提升等級";
/* 2792 */     String msg1 = "0";
/* 2793 */     String msg2 = "要提升多少等級呢?";
/* 2794 */     String msg3 = "";
/* 2795 */     String msg4 = "";
/*      */     
/* 2797 */     if (pc.getTempMaxLevel() - pc.getTempLevel() > 10 && pc.getTempLevel() + 10 < 51) {
/* 2798 */       msg3 = "提升 1 級";
/* 2799 */       msg4 = "提升 10 級";
/*      */     } else {
/*      */       
/* 2802 */       msg3 = "提升 1 級";
/* 2803 */       msg4 = "";
/*      */     } 
/*      */     
/* 2806 */     if (pc.getTempMaxLevel() - pc.getTempLevel() > 0 && pc.getTempLevel() < 50) {
/*      */       
/* 2808 */       String[] msgs = { msg0, msg1, msg2, msg3, msg4 };
/*      */       
/* 2810 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "charreset2", msgs));
/*      */     }
/*      */     else {
/*      */       
/* 2814 */       checkBonusPower(pc);
/*      */     } 
/*      */   }

/*      */   private static void checkBonusPower(L1PcInstance pc) {
/* 2823 */     String msg0 = "等級獎勵點數";
/* 2824 */     String msg1 = String.valueOf(pc.getTempMaxLevel() - pc.getTempLevel());
/* 2825 */     String msg2 = "請選擇想要提升的屬性：";
/*      */     
/* 2827 */     if (pc.getTempMaxLevel() - pc.getTempLevel() > 0) {
/*      */       
/* 2829 */       String[] msgs = { msg0, msg1, msg2 };
/*      */       
/* 2831 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "charreset3", msgs));
/*      */     }
/*      */     else {
/*      */       
/* 2835 */       checkElixirPower(pc);
/*      */     } 
/*      */   }

/*      */   private static void checkElixirPower(L1PcInstance pc) {
/* 2844 */     String msg0 = "萬能藥點數";
/* 2845 */     String msg1 = String.valueOf(pc.getElixirStats() - pc.getTempElixirstats());
/* 2846 */     String msg2 = "請選擇想要提升的屬性：";
/* 2847 */     String msg3 = "力量";
/* 2848 */     String msg4 = "智力";
/* 2849 */     String msg5 = "精神";
/* 2850 */     String msg6 = "敏捷";
/* 2851 */     String msg7 = "體質";
/* 2852 */     String msg8 = "魅力";
/*      */     
/* 2854 */     if (pc.getElixirStats() - pc.getTempElixirstats() > 0) {
/*      */       
/* 2856 */       String[] msgs = { msg0, msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8 };
/*      */       
/* 2858 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "charreset4", msgs));
/*      */     }
/*      */     else {
/*      */       
/* 2862 */       msg0 = "重置完成。";
/* 2863 */       msg1 = String.valueOf(pc.getElixirStats() - pc.getTempElixirstats());
/* 2864 */       msg2 = "已完成所有點數的分配！";
/*      */       
/* 2866 */       String[] msgs = { msg0, msg1, msg2 };
/*      */       
/* 2868 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "charreset5", msgs));
/*      */     } 
/*      */   }

/*      */   private void setLevelUp(L1PcInstance pc, int addLv) {
/* 2880 */     pc.setTempLevel(pc.getTempLevel() + addLv);
/*      */     
/* 2882 */     for (int i = 0; i < addLv; i++) {
/*      */       
/* 2884 */       short randomHp = CalcStat.calcStatHp(pc.getType(), pc.getBaseMaxHp(), pc.getBaseCon(), pc.getOriginalHpup());
/* 2885 */       short randomMp = CalcStat.calcStatMp(pc.getType(), pc.getBaseMaxMp(), pc.getBaseWis(), pc.getOriginalMpup());
/*      */ 
/*      */       
/* 2888 */       pc.addBaseMaxHp(randomHp);
/* 2889 */       pc.addBaseMaxMp(randomMp);
/*      */     } 
/*      */     
/* 2892 */     pc.setExp(ExpTable.getExpByLevel(pc.getTempLevel()));
/* 2893 */     pc.resetLevel();
/*      */     
/* 2895 */     pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 2896 */     pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(pc));
/* 2897 */     pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
/*      */   }

/*      */   private static void saveNewCharStatus(L1PcInstance pc) {
/* 2905 */     pc.getInventory().consumeItem(49142, 1L);
/* 2906 */     pc.setInCharReset(false);
/* 2907 */     pc.refresh();
/* 2908 */     pc.setExp(pc.getoldexp());
/* 2909 */     pc.setCurrentHp(pc.getMaxHp());
/* 2910 */     pc.setCurrentMp(pc.getMaxMp());
/*      */     
/* 2912 */     if (pc.getTempMaxLevel() != pc.getLevel()) {
/* 2913 */       pc.setLevel(pc.getTempMaxLevel());
/* 2914 */       pc.setExp(ExpTable.getExpByLevel(pc.getTempMaxLevel()));
/*      */     } 
/*      */     
/* 2917 */     pc.setTempMaxLevel(0);
/*      */     
/* 2919 */     if (pc.getLevel() > 50) {
/* 2920 */       pc.setBonusStats(pc.getLevel() - 50);
/*      */     } else {
/*      */       
/* 2923 */       pc.setBonusStats(0);
/*      */     } 
/*      */     
/* 2926 */     pc.sendPackets((ServerBasePacket)new S_Paralysis(4, false));
/*      */     
/*      */     try {
/* 2929 */       pc.save();
/*      */     }
/* 2931 */     catch (Exception e) {
/* 2932 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */     
/* 2935 */     L1Teleport.teleport(pc, 32628, 32772, (short)4, 4, false);
/* 2947 */     RecordTable.get().reshp1(pc.getName());
/*      */     
/* 2949 */     pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 2950 */     pc.sendPackets((ServerBasePacket)new S_OwnCharStatus(pc));
/* 2951 */     pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
/*      */     
/* 2953 */     L1PolyMorph.undoPoly((L1Character)pc);
/* 2954 */     RecordTable.get().reshp1(pc.getName());
/* 2955 */     if (ConfigOther.restsavepclog) {
/* 2956 */       pc.setExp(pc.getoldexp());
/* 2957 */       pc.onChangeExp();
/*      */     } 
/*      */     
/* 2960 */     if (pc.getQuest().get_step(30677) == 1) {
/* 2961 */       pc.getQuest().set_step(30677, 2);
/*      */       try {
/* 2963 */         Thread.sleep(1000L);
/* 2964 */       } catch (Exception exception) {}
/*      */       
/* 2966 */       pc.setExp(pc.getoldexp());
/* 2967 */       pc.onChangeExp();
/*      */     } 
/*      */   }

/*      */   private void showPage(int key) {
/*      */     try {
/* 2980 */       L1Quest quest = (L1Quest)(this._pc.get_otherList()).QUESTMAP.get(Integer.valueOf(key));
/* 2981 */       this._pc.setTempID(quest.get_id());
/* 2982 */       String over = null;
/*      */       
/* 2984 */       if (this._pc.getQuest().isEnd(quest.get_id())) {
/* 2985 */         over = "完成任務";
/*      */       } else {
/* 2987 */         over = String.valueOf(this._pc.getQuest().get_step(quest.get_id())) + " / " + quest.get_difficulty();
/*      */       } 
/*      */       
/* 2990 */       String[] info = {
/* 2991 */           quest.get_questname(), 
/* 2992 */           Integer.toString(quest.get_questlevel()), 
/* 2993 */           over, 
/* 2994 */           ""
/*      */         };
/* 2996 */       this._pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(this._pc.getId(), "y_qi1", info));
/*      */     }
/* 2998 */     catch (Exception e) {
/* 2999 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void clanLevel(L1PcInstance pc, int yy) {
/* 3004 */     if (yy == 1) {
/* 3005 */       int clan_level = 0;
/* 3006 */       int clan_contribution = 0;
/* 3007 */       int clan_adena = 0;
/*      */ 
/*      */       
/* 3010 */       int clanadena = 0;
/* 3011 */       int clanadenacount = 0;
/* 3012 */       int contribution = 0;
/*      */       
/* 3014 */       if (pc.getClanid() != 0) {
/* 3015 */         L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/* 3016 */         if (clan != null) {
/* 3017 */           clan_level = clan.getClanLevel();
/* 3018 */           clan_contribution = clan.getClanContribution();
/* 3019 */           clan_adena = clan.getclanadena();
/*      */         } 
/*      */       } 
/* 3022 */       if (clan_level >= 10) {
/* 3023 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("你所屬的血盟已強化至最高等級。"));
/*      */         
/*      */         return;
/*      */       } 
/* 3027 */       if (clan_level == 0) {
/* 3028 */         clanadena = ConfigClan.clanlv1;
/* 3029 */         clanadenacount = ConfigClan.clanlvcount1;
/* 3030 */         contribution = ConfigClan.clanenergy1;
/* 3031 */       } else if (clan_level == 1) {
/* 3032 */         clanadena = ConfigClan.clanlv2;
/* 3033 */         clanadenacount = ConfigClan.clanlvcount2;
/* 3034 */         contribution = ConfigClan.clanenergy2;
/* 3035 */       } else if (clan_level == 2) {
/* 3036 */         clanadena = ConfigClan.clanlv3;
/* 3037 */         clanadenacount = ConfigClan.clanlvcount3;
/* 3038 */         contribution = ConfigClan.clanenergy3;
/* 3039 */       } else if (clan_level == 3) {
/* 3040 */         clanadena = ConfigClan.clanlv4;
/* 3041 */         clanadenacount = ConfigClan.clanlvcount4;
/* 3042 */         contribution = ConfigClan.clanenergy4;
/* 3043 */       } else if (clan_level == 4) {
/* 3044 */         clanadena = ConfigClan.clanlv5;
/* 3045 */         clanadenacount = ConfigClan.clanlvcount5;
/* 3046 */         contribution = ConfigClan.clanenergy5;
/* 3047 */       } else if (clan_level == 5) {
/* 3048 */         clanadena = ConfigClan.clanlv6;
/* 3049 */         clanadenacount = ConfigClan.clanlvcount6;
/* 3050 */         contribution = ConfigClan.clanenergy6;
/* 3051 */       } else if (clan_level == 6) {
/* 3052 */         clanadena = ConfigClan.clanlv7;
/* 3053 */         clanadenacount = ConfigClan.clanlvcount7;
/* 3054 */         contribution = ConfigClan.clanenergy7;
/* 3055 */       } else if (clan_level == 7) {
/* 3056 */         clanadena = ConfigClan.clanlv8;
/* 3057 */         clanadenacount = ConfigClan.clanlvcount8;
/* 3058 */         contribution = ConfigClan.clanenergy8;
/* 3059 */       } else if (clan_level == 8) {
/* 3060 */         clanadena = ConfigClan.clanlv9;
/* 3061 */         clanadenacount = ConfigClan.clanlvcount9;
/* 3062 */         contribution = ConfigClan.clanenergy9;
/* 3063 */       } else if (clan_level == 9) {
/* 3064 */         clanadena = ConfigClan.clanlv10;
/* 3065 */         clanadenacount = ConfigClan.clanlvcount10;
/* 3066 */         contribution = ConfigClan.clanenergy10;
/*      */       } 
/*      */       
/* 3069 */       boolean isCreate = true;
/* 3070 */       int newClanLevel = clan_level + 1;
/*      */ 
/*      */       
/* 3073 */       if (clan_adena < clanadenacount) {
/*      */         
/* 3075 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage("血盟資金不足" + clanadenacount));
/* 3076 */         isCreate = false;
/*      */         return;
/*      */       } 
/* 3079 */       if (contribution > clan_contribution) {
/* 3080 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("血盟目前貢獻度不足【" + contribution + 
/* 3081 */               "】點，請再提升血盟貢獻度吧。"));
/* 3082 */         isCreate = false;
/*      */         return;
/*      */       } 
/* 3085 */       if (isCreate) {
/* 3086 */         pc.getInventory().consumeItem(clanadena, clanadenacount);
/*      */         
/* 3088 */         L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/*      */         
/* 3090 */         clan.setClanLevel(newClanLevel);
/* 3091 */         clan.setClanContribution(clan.getClanContribution() - contribution);
/* 3092 */         clan.setclanadena(clan.getclanadena() - clanadenacount);
/* 3093 */         ClanTable.getInstance().updateClan(clan);
/*      */         
/* 3095 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的血盟等級已強化為 " + newClanLevel + 
/* 3096 */               " 級。"));
/* 3097 */         L1PcInstance[] clanMember = clan.getOnlineClanMember();
/* 3098 */         if (clanMember.length > 0) {
/* 3099 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("你所屬的血盟(" + 
/* 3100 */                 pc.getClanname() + ")等級已強化為 " + newClanLevel + 
/* 3101 */                 " 級"));
/*      */         }
/*      */       }
/*      */     }
/* 3123 */     else if (yy == 3) {
/* 3124 */       if (pc.getQuest().get_step(8541) == 10) {
/* 3125 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("今日貢獻金幣已達上限"));
/*      */         return;
/*      */       } 
/* 3128 */       if (!pc.getClanname().equals(pc.getClanNameContribution())) {
/* 3129 */         pc.setClanNameContribution(pc.getClanname());
/*      */       }
/*      */       
/* 3132 */       if (pc.getQuest().get_step(8541) == 0) {
/* 3133 */         pc.getQuest().set_step(8541, 1);
/* 3134 */       } else if (pc.getQuest().get_step(8541) == 1) {
/* 3135 */         pc.getQuest().set_step(8541, 2);
/* 3136 */       } else if (pc.getQuest().get_step(8541) == 2) {
/* 3137 */         pc.getQuest().set_step(8541, 3);
/* 3138 */       } else if (pc.getQuest().get_step(8541) == 3) {
/* 3139 */         pc.getQuest().set_step(8541, 4);
/* 3140 */       } else if (pc.getQuest().get_step(8541) == 4) {
/* 3141 */         pc.getQuest().set_step(8541, 5);
/* 3142 */       } else if (pc.getQuest().get_step(8541) == 5) {
/* 3143 */         pc.getQuest().set_step(8541, 6);
/* 3144 */       } else if (pc.getQuest().get_step(8541) == 6) {
/* 3145 */         pc.getQuest().set_step(8541, 7);
/* 3146 */       } else if (pc.getQuest().get_step(8541) == 7) {
/* 3147 */         pc.getQuest().set_step(8541, 8);
/* 3148 */       } else if (pc.getQuest().get_step(8541) == 8) {
/* 3149 */         pc.getQuest().set_step(8541, 9);
/* 3150 */       } else if (pc.getQuest().get_step(8541) == 9) {
/* 3151 */         pc.getQuest().set_step(8541, 10);
/*      */       } 
/* 3153 */       L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/* 3154 */       pc.setclanadena(pc.getclanadena() + ConfigClan.PcClanAdena);
/* 3155 */       clan.setclanadena(clan.getclanadena() + ConfigClan.PcClanAdena);
/* 3156 */       pc.getInventory().consumeItem(40308, ConfigClan.PcClanAdena);
/* 3157 */       ClanTable.getInstance().updateClan(clan);
/*      */       
/* 3159 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你給予目前的血盟資金有【" + 
/* 3160 */             pc.getclanadena() + "】。"));
/* 3161 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的血盟目前所獲得的資金總合:【" + 
/* 3162 */             clan.getclanadena() + "】。"));
/* 3163 */     } else if (yy == 5) {
/* 3164 */       if (pc.getQuest().get_step(8545) == 1) {
/* 3165 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("元寶貢獻能量今日已達上限"));
/*      */         return;
/*      */       } 
/* 3175 */       if (!pc.getClanname().equals(pc.getClanNameContribution())) {
/* 3176 */         pc.setClanNameContribution(pc.getClanname());
/*      */       }
/* 3178 */       L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/* 3179 */       clan.setClanContribution(clan.getClanContribution() + 
/* 3180 */           ConfigClan.ClanContribution);
/* 3181 */       ClanTable.getInstance().updateClan(clan);
/*      */       
/* 3183 */       pc.setClanContribution(pc.getClanContribution() + 
/* 3184 */           ConfigClan.ClanContribution);
/* 3185 */       pc.getInventory().storeItem(92164, ConfigClan.ClanContribution);
/* 3186 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("獲得血盟貢獻幣:【" + 
/* 3187 */             ConfigClan.ClanContribution + "】個。"));
/*      */       
/* 3189 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你給予目前的血盟貢獻度有【" + 
/* 3190 */             pc.getClanContribution() + "】點。"));
/* 3191 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的血盟目前所獲得的貢獻度總合有【" + 
/* 3192 */             clan.getClanContribution() + "】點。"));
/* 3193 */       pc.getQuest().set_step(8545, 1);
/* 3194 */       pc.getInventory().consumeItem(44070, ConfigClan.ClanItem44070);
/* 3195 */     } else if (yy == 6) {
/* 3196 */       if (pc.getQuest().get_step(8546) == 1) {
/* 3197 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("元寶貢獻金幣今日已達上限"));
/*      */         return;
/*      */       } 
/* 3207 */       if (!pc.getClanname().equals(pc.getClanNameContribution())) {
/* 3208 */         pc.setClanNameContribution(pc.getClanname());
/*      */       }
/* 3210 */       L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/* 3211 */       clan.setclanadena(clan.getclanadena() + 
/* 3212 */           ConfigClan.ClanContribution_1);
/* 3213 */       ClanTable.getInstance().updateClan(clan);
/*      */       
/* 3215 */       pc.setclanadena(pc.getclanadena() + ConfigClan.ClanContribution_1);
/* 3216 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你給予目前的血盟資金有:【" + 
/* 3217 */             pc.getclanadena() + "】。"));
/* 3218 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("你的血盟目前所獲得的資金總合有【" + 
/* 3219 */             clan.getclanadena() + "】。"));
/* 3220 */       pc.getQuest().set_step(8546, 1);
/* 3221 */       pc.getInventory().consumeItem(44070, ConfigClan.ClanItem44070_1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void newai(L1PcInstance pc) {
/* 3227 */     String[] info = new String[34];
/* 3228 */     info[1] = String.valueOf(pc.getnewai1());
/* 3229 */     info[2] = String.valueOf(pc.getnewai2());
/* 3230 */     info[3] = String.valueOf(pc.getnewai3());
/*      */ 
/*      */     
/* 3233 */     info[4] = String.valueOf(pc.getnewai4());
/* 3234 */     info[5] = String.valueOf(pc.getnewai5());
/* 3235 */     info[6] = String.valueOf(pc.getnewai6());
/*      */     
/* 3237 */     switch (_random.nextInt(10) + 1) {
/*      */       case 1:
/* 3239 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai", info));
/*      */         break;
/*      */       case 2:
/* 3242 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai1", info));
/*      */         break;
/*      */       case 3:
/* 3245 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai2", info));
/*      */         break;
/*      */       case 4:
/* 3248 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai3", info));
/*      */         break;
/*      */       case 5:
/* 3251 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai4", info));
/*      */         break;
/*      */       case 6:
/* 3254 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai5", info));
/*      */         break;
/*      */       case 7:
/* 3257 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai6", info));
/*      */         break;
/*      */       case 8:
/* 3260 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai7", info));
/*      */         break;
/*      */       case 9:
/* 3263 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai8", info));
/*      */         break;
/*      */       case 10:
/* 3266 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai9", info));
/*      */         break;
/*      */     } 
/*      */   }

/*      */   public void BadEnemyList(L1PcInstance pc) {
/* 3276 */     StringBuilder msg = new StringBuilder();
/* 3277 */     for (String name : pc.InBadEnemyList()) {
/* 3278 */       msg.append(String.valueOf(name) + ",");
/*      */     }
/* 3280 */     String[] clientStrAry = msg.toString().split(",");
/* 3281 */     pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(
/* 3282 */           pc.getId(), "x_autolist4", clientStrAry));
/*      */   }
/*      */   private void declareWar(L1PcInstance pc, String value) {
/* 3285 */     if (pc == null || pc.isDead() || pc.isGhost()) {
/*      */       return;
/*      */     }
/*      */     
/* 3289 */     int id = -1;
/*      */     try {
/* 3291 */       id = Integer.parseInt(value);
/* 3292 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 3295 */     if (id == -1) {
/*      */       return;
/*      */     }
/*      */     
/* 3299 */     if (!ServerWarExecutor.get().isNowWar(id)) {
/* 3300 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/* 3301 */       pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aG攻城戰時尚未開始"));
/*      */       
/*      */       return;
/*      */     } 
/* 3305 */     if (!pc.isCrown()) {
/* 3306 */       pc.sendPackets((ServerBasePacket)new S_ServerMessage(478));
/*      */       
/*      */       return;
/*      */     } 
/* 3310 */     if (pc.getClan() == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 3315 */       pc.setDeclareId(id);
/* 3316 */       BinaryOutputStream os = new BinaryOutputStream();
/* 3317 */       os.writeC(227);
/* 3318 */       os.writeC(0);
/* 3319 */       os.writeS(WorldClan.get().getCastleClanName(id));
/* 3320 */       (new C_War()).start(os.getBytes(), pc.getNetConnection());
/* 3321 */       os.close();
/* 3322 */     } catch (Exception exception) {}
/*      */   }
/*      */ }