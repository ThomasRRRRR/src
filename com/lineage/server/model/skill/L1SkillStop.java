package com.lineage.server.model.skill;

import static com.lineage.server.model.skill.L1SkillId.*;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.ItemUseEXTable;
import com.lineage.server.datatables.ServerQuestMaPTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.serverpackets.S_CharTitle;
import com.lineage.server.serverpackets.S_CurseBlind;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_PacketBoxWaterLife;
import com.lineage.server.serverpackets.S_PacketBoxWisdomPotion;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_PinkName;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_SkillIconBlessOfEva;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.utils.Random;
import com.lineage.server.world.WorldClan;
import com.lineage.william.ExcavateTable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * 技能停止
 * 
 * @author dexc
 * 
 */
public class L1SkillStop {
	private static final Log _log = LogFactory.getLog(L1SkillStop.class);

	private static final Random _random = new Random();

	private static ClientExecutor _client;

	public static void stopSkill(L1Character cha, int skillId) {
		try {
			SkillMode mode = L1SkillMode.get().getSkill(skillId);
			if (mode != null) {
				mode.stop(cha);
			} else if (ItemUseEXTable.get().checkItem(skillId)) {
/*  103 */         if (cha instanceof L1PcInstance) {
/*  104 */           L1PcInstance pc = (L1PcInstance)cha;
/*  105 */           ItemUseEXTable.get().remove(pc, skillId);
/*      */         } 
/*      */       } else {
/*      */         try {
/*  111 */           if (cha instanceof L1PcInstance) {
/*  112 */             L1PcInstance pc = (L1PcInstance)cha;
/*      */             
/*  114 */             if (pc.BuffSkillList() != null) {
/*  115 */               boolean ok = false;
/*  116 */               for (Integer list : pc.BuffSkillList()) {
/*  117 */                 if (skillId == list.intValue()) {
/*  118 */                   ok = true;
/*      */                   break;
/*      */                 } 
/*      */               } 
/*  122 */               if (ok) {
/*  123 */                 L1Skills skill = SkillsTable.get().getTemplate(skillId);
/*  124 */                 L1SkillUse skillUse = new L1SkillUse();
/*  125 */                 skillUse.handleCommands(pc, 
/*  126 */                     skillId, 
/*  127 */                     pc.getId(), 
/*  128 */                     pc.getX(), 
/*  129 */                     pc.getY(), 
/*  130 */                     skill.getBuffDuration(), 4);
               }
             }
           }
         } catch (Exception e) {
           _log.error(e.getLocalizedMessage(), e);
         }
         	switch (skillId) {
         	case 98766:
         		if (cha instanceof L1PcInstance) {
         			L1PcInstance pc = (L1PcInstance)cha;
         		if (pc.isGm()){	//GM驗證
         			pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU[請在5秒內輸入驗證碼]"));
         			pc.setSkillEffect(98767, 5000);
         			} else {
         		if (!pc.isGm()){
					pc.set_savetitle(pc.getTitle());
					pc.setrantitle(Random.nextInt(9999));
					pc.sendPackets((ServerBasePacket)new S_CharTitle(pc.getId(), "驗證碼:" + pc.getrantitle()));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT請在60秒內在[對話框]輸入封號上的驗證碼"));
					pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU[請注意輸入法是否是數字]"));
					pc.sendPackets((ServerBasePacket)new S_PacketBoxGree(2, "\\fT請在60秒內在[對話框]輸入稱號上的驗證碼"));
					pc.setSkillEffect(98767, 60000);
					}
				}
			}
/*      */             break;
/*      */           case 98767:
/*  161 */             if (cha instanceof L1PcInstance) {
/*  162 */               L1PcInstance pc = (L1PcInstance)cha;
/*      */               
/*  164 */               if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
/*      */                 return;
/*      */               }
/*      */               
/*  168 */               pc.saveInventory();
/*  169 */               KickPc(pc);
/*      */ 				 pc.sendPackets((ServerBasePacket)new S_Disconnect());
/*      */               
/*  172 */               _log.info(String.format("玩家 : %s 因逾時未通過外掛偵測，已強制切斷其連線", new Object[] { pc.getName() }));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8877:
/*  177 */             if (cha instanceof L1PcInstance) {
/*  178 */               L1PcInstance pc = (L1PcInstance)cha;
/*  179 */               pc.sendPackets((ServerBasePacket)new S_Paralysis(5, false));
/*      */             } 
/*      */             break;
/*      */           case 4017:
/*  183 */             if (cha instanceof L1PcInstance) {
/*  184 */               L1PcInstance pc = (L1PcInstance)cha;
/*  185 */               pc.sendPackets((ServerBasePacket)new S_Paralysis(6, false));
/*      */             } 
/*      */             break;
/*      */           case 79501:
/*  189 */             if (cha instanceof L1PcInstance) {
/*  190 */               L1PcInstance pc = (L1PcInstance)cha;
/*  191 */               if (!pc.isParalyzed_guaji()) {
/*  192 */                 L1Teleport.randomTeleportai(pc);
/*      */               }
/*      */             } 
/*      */             break;
/*      */           case 14009:
/*  197 */             if (cha instanceof L1PcInstance) {
/*  198 */               L1PcInstance pc = (L1PcInstance)cha;
/*      */               
/*  200 */               pc.sendPackets((ServerBasePacket)new S_Paralysis(6, false));
/*      */             } 
/*      */             break;
/*      */           case 80552:
/*  204 */             if (cha instanceof L1PcInstance) {
/*  205 */               L1PcInstance pc = (L1PcInstance)cha;
/*  206 */               if (pc.get_other3().get_type1() == 0) {
/*  207 */                 ServerQuestMaPTable.checkcount(pc); break;
/*  208 */               }  if (pc.get_other3().get_type1() == -1) {
/*  209 */                 ServerQuestMaPTable.checkcount(pc);
/*      */               }
/*      */             } 
/*      */             break;

/*      */           case 5122:
/*  236 */             if (cha instanceof L1PcInstance) {
/*  237 */               L1PcInstance tgpc = (L1PcInstance)cha;
/*  238 */               tgpc.sendPackets((ServerBasePacket)new S_PinkName(tgpc.getId(), 0));
/*  239 */               tgpc.setPinkName(false);
/*      */             } 
/*      */             break;

/*      */           case 8906:
/*  245 */             if (cha instanceof L1PcInstance) {
/*  246 */               L1PcInstance pc = (L1PcInstance)cha;
/*  247 */               pc.addHitup(-30);
/*  248 */               pc.addDmgup(-30);
/*  249 */               pc.addBowHitup(-30);
/*  250 */               pc.addBowDmgup(-30);
/*  251 */               pc.addSp(-30);
/*  252 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*  253 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("下層戰鬥強化卷軸效果消失！"));
/*      */             } 
/*      */             break;

/*      */           case 8907:
/*  259 */             if (cha instanceof L1PcInstance) {
/*  260 */               L1PcInstance pc = (L1PcInstance)cha;
/*  261 */               pc.addAc(50);
/*  262 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("下層防禦強化卷軸效果消失！"));
/*      */             } 
/*      */             break;
/*      */           case 157:
/*  266 */             if (cha instanceof L1PcInstance) {
/*  267 */               L1PcInstance pc = (L1PcInstance)cha;
/*  268 */               pc.sendPacketsAll((ServerBasePacket)new S_Poison(pc.getId(), 0));
/*  270 */               pc.sendPackets((ServerBasePacket)new S_Paralysis(4,  false));
						break;
/*  272 */             }
						if (cha instanceof com.lineage.server.model.Instance.L1MonsterInstance || 
/*  273 */               cha instanceof com.lineage.server.model.Instance.L1SummonInstance || 
/*  274 */               cha instanceof com.lineage.server.model.Instance.L1PetInstance) {
/*  275 */               L1NpcInstance npc = (L1NpcInstance)cha;
/*  276 */               npc.broadcastPacketAll((ServerBasePacket)new S_Poison(npc.getId(), 0));
/*  277 */               npc.setParalyzed(false);
/*      */             } 
/*      */             break;
/*      */           case 6930:
/*  281 */             if (cha instanceof L1PcInstance) {
/*  282 */               L1PcInstance tgpc = (L1PcInstance)cha;
/*  283 */               tgpc.setSkillEffect(6931, 0);
/*      */             } 
/*      */             break;
/*      */           case 6932:
/*  287 */             if (cha instanceof L1PcInstance) {
/*  288 */               L1PcInstance tgpc = (L1PcInstance)cha;
/*  289 */               if (!tgpc.isActived() && tgpc.get_followmaster() == null) {
/*  290 */                 tgpc.setSkillEffect(6933, 120000);
/*  291 */                 tgpc.setnewai1(1 + Random.nextInt(8));
/*  292 */                 tgpc.setnewai2(1 + Random.nextInt(8));
/*  293 */                 tgpc.setnewai3(1 + Random.nextInt(8));
/*  294 */                 newai(tgpc);
/*      */                 break;
/*      */               } 
/*  297 */               tgpc.setSkillEffect(6930, 600000);
/*      */             } 
/*      */             break;

/*      */           case 8853:
/*  335 */             if (cha instanceof L1PcInstance) {
/*  336 */               L1PcInstance tgpc = (L1PcInstance)cha;
/*  337 */               if (tgpc.isActived() && tgpc.getAu_AutoSet(0) == 0) {
/*  338 */                 L1Teleport.randomTeleportai(tgpc);
/*  339 */                 tgpc.setSkillEffect(8853, 60000);
/*      */                 
/*  341 */                 if (tgpc.getAutoX() > 0) {
/*  342 */                   tgpc.setAutoX(0);
/*  343 */                   tgpc.setAutoY(0);
/*  344 */                   tgpc.setAutoMap(0);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             break;

/*      */           case 7944:
/*  352 */             if (cha instanceof L1PcInstance) {
/*  353 */               L1PcInstance pc = (L1PcInstance)cha;
/*  354 */               L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/*  355 */               if (clan != null && clan.getWarehouseUsingChar() == pc.getId()) {
/*  356 */                 clan.setWarehouseUsingChar(0);
/*  357 */                 pc.sendPackets((ServerBasePacket)new S_ServerMessage("盟倉使用權已被中斷。"));
/*  358 */                 pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "y_who"));
/*  359 */                 pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "y_who"));
/*  360 */                 pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "y_who"));
/*      */               } 
/*      */             } 
/*      */             break;

/*      */           case 6933:
/*  368 */             if (cha instanceof L1PcInstance) {
/*  369 */               L1PcInstance pc = (L1PcInstance)cha;
/*      */               
/*  371 */               if (L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
/*      */                 return;
/*      */               }
/*      */               
/*  375 */               pc.saveInventory();
/*  376 */               KickPc(pc);

/*  379 */               _log.info(String.format("玩家 : %s 因逾時未通過外掛偵測，已強制切斷其連線", new Object[] { pc.getName() }));
/*      */             } 
/*      */             break;
/*      */           case 8030:
/*  383 */             if (cha instanceof L1PcInstance) {
/*  384 */               L1PcInstance pc = (L1PcInstance)cha;
/*  385 */               pc.addMaxHp(-20);
/*  386 */               pc.addMaxMp(-20);
/*  387 */               pc.addDmgup(-3);
/*  388 */               pc.addBowDmgup(-3);
/*  389 */               pc.addSp(-3);
/*  390 */               pc.addDamageReductionByArmor(-2);
/*  391 */               pc.addHpr(-2);
/*  392 */               pc.addMpr(-2);
/*      */               
/*  394 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc));
/*  395 */               if (pc.isInParty()) {
/*  396 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/*  398 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc));
/*  399 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 85578:
/*  404 */             if (cha instanceof L1PcInstance) {
/*  405 */               L1PcInstance pc = (L1PcInstance)cha;
/*  406 */               pc.set_outChat(null);
/*  407 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fY解除控制假人"));
/*      */             } 
/*      */             break;

/*      */           case 40:
/*  450 */             if (cha instanceof L1PcInstance) {
/*  451 */               L1PcInstance pc = (L1PcInstance)cha;
/*  452 */               pc.sendPackets((ServerBasePacket)new S_CurseBlind(0));
/*      */             } 
/*      */             break;
/*      */           case 8065:
/*  456 */             if (cha instanceof L1PcInstance) {
/*  457 */               L1PcInstance pc = (L1PcInstance)cha;
/*  458 */               pc.addStr(-1);
/*  459 */               pc.addDex(-1);
/*  460 */               pc.addInt(-1);
/*  461 */               pc.addMr(-10);
/*  462 */               pc.addRegistStun(-2);
/*  463 */               pc.addRegistSustain(-2);
/*  464 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*  465 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/*  466 */               pc.sendPackets((ServerBasePacket)new S_PacketBox(180, 0, 479));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8865:
/*  471 */             if (cha instanceof L1PcInstance) {
/*  472 */               L1PcInstance pc = (L1PcInstance)cha;
/*  473 */               pc.addMaxHp(-500);
/*  474 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/*  475 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("特殊技能-體能倍化恢復了"));
/*      */             } 
/*      */             break;
/*      */           case 8866:
/*  479 */             if (cha instanceof L1PcInstance) {
/*  480 */               L1PcInstance pc = (L1PcInstance)cha;
/*  481 */               pc.addSp(-2);
/*  482 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*  483 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("魔法增壓似乎消失了!!"));
/*      */             } 
/*      */             break;
/*      */           case 8868:
/*  487 */             cha.addDex(-4);
/*  488 */             if (cha instanceof L1PcInstance) {
/*  489 */               L1PcInstance pc = (L1PcInstance)cha;
/*  490 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("特殊技能-能力祝福消失了"));
/*  491 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/*      */             } 
/*      */             break;
/*      */           case 8869:
/*  495 */             cha.addStr(-4);
/*  496 */             if (cha instanceof L1PcInstance) {
/*  497 */               L1PcInstance pc = (L1PcInstance)cha;
/*  498 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("特殊技能-能力祝福消失了"));
/*  499 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/*      */             } 
/*      */             break;
/*      */           case 8870:
/*  503 */             cha.addInt(-4);
/*  504 */             if (cha instanceof L1PcInstance) {
/*  505 */               L1PcInstance pc = (L1PcInstance)cha;
/*  506 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("特殊技能-能力祝福消失了"));
/*  507 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 5198:
/*  512 */             if (cha instanceof L1PcInstance) {
/*  513 */               L1PcInstance pc = (L1PcInstance)cha;
/*  514 */               pc.setEsotericSkill(0);
/*  515 */               pc.setEsotericCount(0);
/*  516 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("魔擊累積的效果已消失。"));
/*      */             } 
/*      */             break;
/*      */           case 10010:
/*  520 */             if (cha instanceof L1PcInstance) {
/*  521 */               L1PcInstance pc = (L1PcInstance)cha;
/*  529 */               ExcavateTable.forExcavate(pc);
/*      */             } 
/*      */             break;
/*      */           case 5923:
/*  533 */             if (cha instanceof L1PcInstance) {
/*  534 */               L1PcInstance pc = (L1PcInstance)cha;
/*  535 */               long adenaCount = pc.getInventory().countItems(
/*  536 */                   44070);
/*  537 */               if (adenaCount > 0L) {
/*  538 */                 long difference = adenaCount - pc.getShopAdenaRecord();
/*  539 */                 if (difference >= 100L) {
/*      */                   
/*  541 */                   元寶差異紀錄("元寶差異紀錄 IP(" + 
/*  542 */                       pc.getNetConnection().getIp() + 
/*  543 */                       ")玩家:【" + pc.getName() + 
/*  544 */                       "】的在線元寶數量增加:【" + difference + 
/*  545 */                       "】個, 時間:(" + new Timestamp(System.currentTimeMillis()) + 
/*  546 */                       ")。");
/*      */                 }
/*  548 */                 else if (difference <= -100L) {
/*  549 */                   元寶差異紀錄("元寶差異紀錄 IP(" + 
/*  550 */                       pc.getNetConnection().getIp() + 
/*  551 */                       ")玩家:【" + pc.getName() + 
/*  552 */                       "】的在線元寶數量減少:【" + difference + 
/*  553 */                       "】個, 時間:(" + new Timestamp(System.currentTimeMillis()) + 
/*  554 */                       ")。");
/*      */                 } 
/*      */                 
/*  557 */                 pc.setShopAdenaRecord(adenaCount);
/*      */               } 
/*      */               
/*  560 */               pc.setSkillEffect(5923, 15000);
/*      */             } 
/*      */             break;

/*      */           case 85501:
/*  570 */             if (cha instanceof L1PcInstance) {
/*  571 */               L1PcInstance pc = (L1PcInstance)cha;
/*  572 */               pc.addMaxHp(-100);
/*  573 */               pc.addMaxMp(-100);
/*  574 */               pc.addDmgup(-5);
/*  575 */               pc.addHitup(-10);
/*  576 */               pc.addBowDmgup(-5);
/*  577 */               pc.addBowHitup(-10);
/*  578 */               pc.addSp(-5);
/*  579 */               pc.addAc(10);
/*  580 */               pc.addMr(-10);
/*  581 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*  582 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/*  583 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/*  584 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
/*  585 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/*      */             } 
/*      */             break;
/*      */           case 7002:
/*  589 */             if (cha instanceof L1PcInstance) {
/*  590 */               L1PcInstance pc = (L1PcInstance)cha;
/*  591 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("陣營積分2倍卷軸效果消失。"));
/*      */             } 
/*      */             break;
/*      */           case 7003:
/*  595 */             if (cha instanceof L1PcInstance) {
/*  596 */               L1PcInstance pc = (L1PcInstance)cha;
/*  597 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("陣營積分3倍卷軸效果消失 "));
/*      */             } 
/*      */             break;
/*      */           case 6797:
/*  601 */             if (cha instanceof L1PcInstance) {
/*  602 */               L1PcInstance pc = (L1PcInstance)cha;
/*  603 */               pc.addAc(2);
/*  604 */               pc.addWater(-50);
/*  605 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/*      */             } 
/*      */             break;
/*      */           case 6798:
/*  609 */             if (cha instanceof L1PcInstance) {
/*  610 */               L1PcInstance pc = (L1PcInstance)cha;
/*  611 */               pc.addHpr(-3);
/*  612 */               pc.addMpr(-1);
/*  613 */               pc.addWind(-50);
/*  614 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/*      */             } 
/*      */             break;
/*      */           case 6799:
/*  618 */             if (cha instanceof L1PcInstance) {
/*  619 */               L1PcInstance pc = (L1PcInstance)cha;
/*  620 */               pc.addFire(-50);
/*  621 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 9990:
/*  626 */             if (cha instanceof L1PcInstance) {
/*  627 */               L1PcInstance pc = (L1PcInstance)cha;
/*  628 */               L1Teleport.teleport(pc, 33443, 32797, (short)4, 5, true);
/*      */             } 
					case LIGHT:
						if (((cha instanceof L1PcInstance)) && 
							(!cha.isInvisble())) {
							L1PcInstance pc = (L1PcInstance)cha;
							pc.turnOnOffLight();
						}
						break;

					case GLOWING_AURA://灼熱武器
						cha.addHitup(-5);
						cha.addBowHitup(-5);
						cha.addDmgup(-5);
						cha.addBowDmgup(-5);
//    					cha.addMr(-20);
						if ((cha instanceof L1PcInstance)) {
							L1PcInstance pc = (L1PcInstance)cha;
							pc.sendPackets(new S_PacketBoxIconAura(113, 0));
						}
						break;
    
			        case SHINING_AURA://閃亮之盾
				          cha.addAc(8);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(114, 0));
				          }
				          break;
				          
			        case BRAVE_AURA://勇猛意志
//				          cha.addDmgup(-5);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(116, 0));
				          }
				          break;
				          
			        case SHIELD:
				          cha.addAc(2);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_SkillIconShield(2, 0));
				          }
				          break;
				          
				    case BLIND_HIDING:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.delBlindHiding();
				          }
				          break;
				          
			        case DRESS_DEXTERITY://敏捷提升
				          cha.addDex(-3);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_Dexup(pc, 3, 0));
				            pc.resetBaseAc();
				          }
				          break;
				          
			        case DRESS_MIGHTY://力量提升
				          cha.addStr(-3);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_Strup(pc, 3, 0));
				          }
				          break;
           
			        case EARTH_BLESS:
				          cha.addAc(7);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_SkillIconShield(7, 0));
				          }
				          break;
           
			        case RESIST_MAGIC:
				          cha.addMr(-10);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_SPMR(pc));
				          }
				          break;

			        case CLEAR_MIND://淨化精神
				          cha.addWis(-3);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.resetBaseMr();
				          }
				          break;
				          
			        case RESIST_ELEMENTAL:
				          cha.addWind(-10);
				          cha.addWater(-10);
				          cha.addFire(-10);
				          cha.addEarth(-10);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_OwnCharAttrDef(pc));
				          }
				          break;

			        case ELEMENTAL_PROTECTION:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            int attr = pc.getElfAttr();
				            if (attr == 1)
				              cha.addEarth(-50);
				            else if (attr == 2)
				              cha.addFire(-50);
				            else if (attr == 4)
				              cha.addWater(-50);
				            else if (attr == 8) {
				              cha.addWind(-50);
				            }
				            pc.sendPackets(new S_OwnCharAttrDef(pc));
				          }
				          break;
          
			        case ERASE_MAGIC:  // 魔法消除
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_PacketBoxIconAura(152, 0));
						}		
					  break;

			        case WATER_LIFE:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxWaterLife());
				          }
				          break;

			        case IRON_SKIN:
				          cha.addAc(10);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_SkillIconShield(10, 0));
				          }
				          break;

			        case EARTH_SKIN:
				          cha.addAc(6);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_SkillIconShield(6, 0));
				          }
				          break;

			        case PHYSICAL_ENCHANT_STR://體魄強健術
				          cha.addStr(-5);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_Strup(pc, 5, 0));
				          }
				          break;
				          
			        case PHYSICAL_ENCHANT_DEX://通暢氣脈術
				          cha.addDex(-5);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_Dexup(pc, 5, 0));
				            pc.resetBaseAc();
				          }
				          break;
          
			        case FIRE_WEAPON:
				          cha.addDmgup(-4);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(147, 0));
				          }
				          break;
				          
			        case STRIKER_GALE:
			        	if ((cha instanceof L1PcInstance)) {
			                L1PcInstance pc = (L1PcInstance)cha;
			                pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));//迴避率更新
			        	}
			    	  break;

///*      */           case FIRE_BLESS:
///*  790 */             cha.addDmgup(-4);
///*  791 */             if (cha instanceof L1PcInstance) {
///*  792 */               L1PcInstance pc = (L1PcInstance)cha;
///*  793 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxIconAura(154, 0));
///*      */             } 
///*      */             break;

			        case BURNING_WEAPON:
			        	cha.addDmgup(-6);
			        	cha.addHitup(-3);
			        	if ((cha instanceof L1PcInstance)) {
			        		L1PcInstance pc = (L1PcInstance)cha;
			        		pc.sendPackets(new S_PacketBoxIconAura(162, 0));
			        	}
			        	break;
			        	
			        case WIND_SHOT:
				          cha.addBowHitup(-6);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(148, 0));
				          }
				          break;

			        case STORM_EYE:
				          cha.addBowHitup(-2);
				          cha.addBowDmgup(-3);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(155, 0));
				          }
				          break;

			        case STORM_SHOT:
				          cha.addBowDmgup(-5);
				          cha.addBowHitup(1);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_PacketBoxIconAura(165, 0));
				          }
				          break;

					case BERSERKERS:
						cha.addAc(-10);
						cha.addDmgup(-5);
						cha.addHitup(-5);
						if ((cha instanceof L1PcInstance)) {
							L1PcInstance pc = (L1PcInstance)cha;
							pc.startHpRegeneration();
						}
						break;
						
					case HASTE:
					case GREATER_HASTE:
						cha.setMoveSpeed(0);
						if ((cha instanceof L1PcInstance)) {
							L1PcInstance pc = (L1PcInstance)cha;
							pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
						}
						break;

			        case HOLY_WALK:
			        case MOVING_ACCELERATION:
			        case WIND_WALK:
			          cha.setBraveSpeed(0);
			          if ((cha instanceof L1PcInstance)) {
			            L1PcInstance pc = (L1PcInstance)cha;
			            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
			          }
			          break;
			          
			        case ILLUSION_OGRE:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.addDmgup(-4);
				            pc.addHitup(-4);
				          }
				          break;

				          
			        case ILLUSION_DIA_GOLEM:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.addAc(8);
				          }
				          break;          

					case WEAKNESS:
						if ((cha instanceof L1PcInstance)) {
							L1PcInstance pc = (L1PcInstance)cha;
							pc.addDmgup(5);
							pc.addHitup(1);
						}
						break;

			        case DISEASE:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.addHitup(6);
				            pc.addAc(-12);
				          }
				          break;		
					 
			        case 8909:
			        	if (cha instanceof L1PcInstance) {
			        		L1PcInstance pc = (L1PcInstance)cha;
			        		pc.addHitup(6);
			        		pc.addAc(-12);
						 pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY防禦力與命中率恢復"));
						}
						break;
						
			        case 8910:
			        	if (cha instanceof L1PcInstance) {
			        		L1PcInstance pc = (L1PcInstance)cha;
			        		pc.addHitup(6);
			        		pc.addAc(-12);
						 pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY防禦力與命中率恢復"));
			        	}
			        	break;

			        case ICE_LANCE:
			        case FREEZING_BLIZZARD:
			        case FREEZING_BREATH:
			          if ((cha instanceof L1PcInstance)) {
			            L1PcInstance pc = (L1PcInstance)cha;
			            pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
			
			            pc.sendPackets(new S_Paralysis(4, false));
			          } else if (((cha instanceof L1MonsterInstance)) || 
			            ((cha instanceof L1SummonInstance)) || 
			            ((cha instanceof L1PetInstance))) {
			            L1NpcInstance npc = (L1NpcInstance)cha;
			            npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
			            npc.setParalyzed(false);
			          }
			          break;

			        case FOG_OF_SLEEPING:
				          cha.setSleeped(false);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, false));
				          }
				          break;

			        case ABSOLUTE_BARRIER:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.startHpRegeneration();
				            pc.startMpRegeneration();
				          }
				          break;

			        case SLOW:
			        case MASS_SLOW:
			        case ENTANGLE:
			        	if ((cha instanceof L1PcInstance)) {
			        		L1PcInstance pc = (L1PcInstance)cha;
			        		pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
			        	}
			        	cha.setMoveSpeed(0);
			        	break;
			          
			        case GUARD_BRAKE:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.addAc(-10);
				          }
				          break;

			        case HORROR_OF_DEATH:
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.addStr(3);
				            pc.addInt(3);
				            pc.sendPackets(new S_OwnCharStatus2(pc));
				          }
				          break;

			        	case STATUS_CUBE_IGNITION_TO_ALLY:
				          cha.addFire(-30);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_OwnCharAttrDef(pc));
				          }
				          break;
				          
				        case STATUS_CUBE_QUAKE_TO_ALLY:
				          cha.addEarth(-30);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_OwnCharAttrDef(pc));
				          }
				          break;
				          
				        case STATUS_CUBE_SHOCK_TO_ALLY:
				          cha.addWind(-30);
				          if ((cha instanceof L1PcInstance)) {
				            L1PcInstance pc = (L1PcInstance)cha;
				            pc.sendPackets(new S_OwnCharAttrDef(pc));
				          }
				          break;

				        case STATUS_BRAVE:
					          if ((cha instanceof L1PcInstance)) {
					            L1PcInstance pc = (L1PcInstance)cha;
					            pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
					          }
					          cha.setBraveSpeed(0);
					          break;
					          
					    case STATUS_BRAVE3:
					          if ((cha instanceof L1PcInstance)) {
					            L1PcInstance pc = (L1PcInstance)cha;
					            pc.sendPacketsAll(new S_Liquor(pc.getId(), 0));
					          }
					          break;

/*      */           case 6666:
/*      */           case 6667:
/*      */           case 6668:
/*      */           case 6669:
/*      */           case 6670:
/*      */           case 6671:
/*      */           case 6672:
/*      */           case 6673:
/*      */           case 6674:
/*      */           case 6675:
/*      */           case 6676:
/*      */           case 6677:
/*      */           case 6678:
/*      */           case 6679:
/*      */           case 6680:
/*      */           case 6681:
/*  988 */             if (cha instanceof L1PcInstance) {
/*  989 */               L1PcInstance pc = (L1PcInstance)cha;
/*  991 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("第一段經驗藥水效果消失。"));
/*      */             } 
/*      */             break;
/*      */           case 5000:
/*      */           case 5001:
/*      */           case 5002:
/*      */           case 5003:
/*      */           case 5004:
/*      */           case 5005:
/*      */           case 5006:
/*      */           case 5007:
/*      */           case 5008:
/*      */           case 5009:
/*      */           case 5010:
/*      */           case 5011:
/*      */           case 5012:
/*      */           case 5013:
/*      */           case 5014:
/* 1009 */             if (cha instanceof L1PcInstance) {
/* 1010 */               L1PcInstance pc = (L1PcInstance)cha;
/*      */               
/* 1012 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("第二段神力藥水效果消失。"));
/*      */             } 
/*      */             break;
/*      */           case 8591:
/* 1016 */             if (cha instanceof L1PcInstance) {
/* 1017 */               L1PcInstance pc = (L1PcInstance)cha;
/*      */               
/* 1019 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage("媽祖的祝福效果消失。"));
/*      */             } 
/*      */             break;
/*      */           case 1016:
/* 1023 */             if (cha instanceof L1PcInstance) {
/* 1024 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1025 */               pc.sendPacketsAll((ServerBasePacket)new S_SkillBrave(pc.getId(), 0, 0));
/*      */             } 
/* 1027 */             cha.setBraveSpeed(0);
/*      */             break;
/*      */           case 1017:
/* 1030 */             if (cha instanceof L1PcInstance) {
/* 1031 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1032 */               pc.setBraveSpeed(0);
/* 1033 */               pc.sendPacketsAll((ServerBasePacket)new S_SkillHaste(pc.getId(), 0, 0));
/*      */               
/*      */               break;
/*      */             } 
/* 1037 */             cha.setBraveSpeed(0);
/*      */             break;
/*      */           
/*      */           case 1001:
/* 1041 */             if (cha instanceof L1PcInstance) {
/* 1042 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1043 */               pc.sendPacketsAll((ServerBasePacket)new S_SkillHaste(pc.getId(), 0, 0));
/*      */             } 
/* 1045 */             cha.setMoveSpeed(0);
/*      */             break;
/*      */ 
/*      */           
/*      */           case 1003:
/* 1050 */             if (cha instanceof L1PcInstance) {
/* 1051 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1052 */               pc.sendPackets((ServerBasePacket)new S_SkillIconBlessOfEva(pc.getId(), 0));
/*      */             } 
/*      */             break;
/*      */           case 1004:
/* 1056 */             if (cha instanceof L1PcInstance) {
/* 1057 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1058 */               pc.addSp(-2);
/* 1059 */               pc.addMpr(-2);
/* 1060 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxWisdomPotion(0));
/*      */             } 
/*      */             break;
/*      */           case 4002:
/* 1064 */             if (cha instanceof L1PcInstance) {
/* 1065 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1066 */               pc.sendPackets((ServerBasePacket)new S_ServerMessage(288));
/*      */             } 
/*      */             break;
/*      */           case 1006:
/* 1070 */             cha.curePoison();
/*      */             break;
/*      */           case 3000:
/*      */           case 3008:
/* 1074 */             if (cha instanceof L1PcInstance) {
/* 1075 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1076 */               pc.addWind(-10);
/* 1077 */               pc.addWater(-10);
/* 1078 */               pc.addFire(-10);
/* 1079 */               pc.addEarth(-10);
/* 1080 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/* 1081 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 0, 0));
/* 1082 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3001:
/*      */           case 3009:
/* 1087 */             if (cha instanceof L1PcInstance) {
/* 1088 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1089 */               pc.addMaxHp(-30);
/* 1090 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/* 1091 */               if (pc.isInParty()) {
/* 1092 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1094 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 1, 0));
/* 1095 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3002:
/*      */           case 3010:
/* 1100 */             if (cha instanceof L1PcInstance) {
/* 1101 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1102 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 2, 0));
/* 1103 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3003:
/*      */           case 3011:
/* 1108 */             if (cha instanceof L1PcInstance) {
/* 1109 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1110 */               pc.addAc(1);
/* 1111 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 3, 0));
/* 1112 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3004:
/*      */           case 3012:
/* 1117 */             if (cha instanceof L1PcInstance) {
/* 1118 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1119 */               pc.addMaxMp(-20);
/* 1120 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
/* 1121 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 4, 0));
/* 1122 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3005:
/*      */           case 3013:
/* 1127 */             if (cha instanceof L1PcInstance) {
/* 1128 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1129 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 5, 0));
/* 1130 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3006:
/*      */           case 3014:
/* 1135 */             if (cha instanceof L1PcInstance) {
/* 1136 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1137 */               pc.addMr(-5);
/* 1138 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1139 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 6, 0));
/* 1140 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3007:
/*      */           case 3015:
/* 1145 */             if (cha instanceof L1PcInstance) {
/* 1146 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1147 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 7, 0));
/* 1148 */               pc.setDessertId(0);
/*      */             } 
/*      */             break;
/*      */           case 3016:
/*      */           case 3024:
/* 1153 */             if (cha instanceof L1PcInstance) {
/* 1154 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1155 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 16, 0));
/* 1156 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3017:
/*      */           case 3025:
/* 1161 */             if (cha instanceof L1PcInstance) {
/* 1162 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1163 */               pc.addMaxHp(-30);
/* 1164 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/* 1165 */               if (pc.isInParty()) {
/* 1166 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1168 */               pc.addMaxMp(-30);
/* 1169 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
/* 1170 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 17, 0));
/* 1171 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3018:
/*      */           case 3026:
/* 1176 */             if (cha instanceof L1PcInstance) {
/* 1177 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1178 */               pc.addAc(2);
/* 1179 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 18, 0));
/* 1180 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3019:
/*      */           case 3027:
/* 1185 */             if (cha instanceof L1PcInstance) {
/* 1186 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1187 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 19, 0));
/* 1188 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3020:
/*      */           case 3028:
/* 1193 */             if (cha instanceof L1PcInstance) {
/* 1194 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1195 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 20, 0));
/* 1196 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3021:
/*      */           case 3029:
/* 1201 */             if (cha instanceof L1PcInstance) {
/* 1202 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1203 */               pc.addMr(-10);
/* 1204 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1205 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 21, 0));
/* 1206 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3022:
/*      */           case 3030:
/* 1211 */             if (cha instanceof L1PcInstance) {
/* 1212 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1213 */               pc.addSp(-1);
/* 1214 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1215 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 22, 0));
/* 1216 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3023:
/*      */           case 3031:
/* 1221 */             if (cha instanceof L1PcInstance) {
/* 1222 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1223 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 32, 0));
/* 1224 */               pc.setDessertId(0);
/*      */             } 
/*      */             break;
/*      */           case 3032:
/*      */           case 3040:
/* 1229 */             if (cha instanceof L1PcInstance) {
/* 1230 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1231 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 37, 0));
/* 1232 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3033:
/*      */           case 3041:
/* 1237 */             if (cha instanceof L1PcInstance) {
/* 1238 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1239 */               pc.addMaxHp(-50);
/* 1240 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/* 1241 */               if (pc.isInParty()) {
/* 1242 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1244 */               pc.addMaxMp(-50);
/* 1245 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
/* 1246 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 38, 0));
/* 1247 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3034:
/*      */           case 3042:
/* 1252 */             if (cha instanceof L1PcInstance) {
/* 1253 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1254 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 39, 0));
/* 1255 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3035:
/*      */           case 3043:
/* 1260 */             if (cha instanceof L1PcInstance) {
/* 1261 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1262 */               pc.addAc(3);
/* 1263 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 40, 0));
/* 1264 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3036:
/*      */           case 3044:
/* 1269 */             if (cha instanceof L1PcInstance) {
/* 1270 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1271 */               pc.addMr(-15);
/* 1272 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1273 */               pc.addWind(-10);
/* 1274 */               pc.addWater(-10);
/* 1275 */               pc.addFire(-10);
/* 1276 */               pc.addEarth(-10);
/* 1277 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/* 1278 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 41, 0));
/* 1279 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3037:
/*      */           case 3045:
/* 1284 */             if (cha instanceof L1PcInstance) {
/* 1285 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1286 */               pc.addSp(-2);
/* 1287 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1288 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 42, 0));
/* 1289 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3038:
/*      */           case 3046:
/* 1294 */             if (cha instanceof L1PcInstance) {
/* 1295 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1296 */               pc.addMaxHp(-30);
/* 1297 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
/* 1298 */               if (pc.isInParty()) {
/* 1299 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1301 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 43, 0));
/* 1302 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3039:
/*      */           case 3047:
/* 1307 */             if (cha instanceof L1PcInstance) {
/* 1308 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1309 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 44, 0));
/* 1310 */               pc.setDessertId(0);
/*      */             } 
/*      */             break;
/*      */           case 3048://強壯的牛排
/* 1314 */             if (cha instanceof L1PcInstance) {
/* 1315 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1316 */               pc.addMr(-10);
/* 1317 */               pc.addEarth(-10);
/* 1318 */               pc.addWater(-10);
/* 1319 */               pc.addFire(-10);
/* 1320 */               pc.addWind(-10);
/* 1321 */               pc.addHpr(-2);
/* 1322 */               pc.addMpr(-2);
/* 1323 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1324 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/* 1325 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 157, 0));
/* 1326 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3049://敏捷的煎鮭魚
/* 1330 */             if (cha instanceof L1PcInstance) {
/* 1331 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1332 */               pc.addMr(-10);
/* 1333 */               pc.addEarth(-10);
/* 1334 */               pc.addWater(-10);
/* 1335 */               pc.addFire(-10);
/* 1336 */               pc.addWind(-10);
/* 1337 */               pc.addHpr(-2);
/* 1338 */               pc.addMpr(-2);
/* 1339 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1340 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/* 1341 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 158, 0));
/* 1342 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3050://炭烤的火雞
/* 1346 */             if (cha instanceof L1PcInstance) {
/* 1347 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1348 */               pc.addSp(-2);
/* 1349 */               pc.addMr(-10);
/* 1350 */               pc.addEarth(-10);
/* 1351 */               pc.addWater(-10);
/* 1352 */               pc.addFire(-10);
/* 1353 */               pc.addWind(-10);
/* 1354 */               pc.addHpr(-2);
/* 1355 */               pc.addMpr(-3);
/* 1356 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/* 1357 */               pc.sendPackets((ServerBasePacket)new S_OwnCharAttrDef(pc));
/* 1358 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 159, 0));
/* 1359 */               pc.setCookingId(0);
/*      */             } 
/*      */             break;
/*      */           case 3051://養生的雞湯
/* 1363 */             if (cha instanceof L1PcInstance) {
/* 1364 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1365 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxCooking(pc, 160, 0));
/* 1366 */               pc.setDessertId(0);
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8040:
/* 1371 */             if (cha instanceof L1PcInstance) {
/* 1372 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1373 */               pc.addMaxHp(-100);
/* 1374 */               pc.addMaxMp(-100);
/* 1375 */               pc.addDmgup(-6);
/* 1376 */               pc.addBowDmgup(-6);
/* 1377 */               pc.addSp(-4);
/* 1378 */               pc.addDamageReductionByArmor(-4);
/*      */               
/* 1380 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc));
/* 1381 */               if (pc.isInParty()) {
/* 1382 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1384 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc));
/* 1385 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8041:
/* 1390 */             if (cha instanceof L1PcInstance) {
/* 1391 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1392 */               pc.addMaxHp(-100);
/* 1393 */               pc.addMaxMp(-100);
/* 1394 */               pc.addHpr(-5);
/* 1395 */               pc.addMpr(-5);
/* 1396 */               pc.addStr(-1);
/* 1397 */               pc.addDex(-1);
/* 1398 */               pc.addCon(-1);
/* 1399 */               pc.addInt(-1);
/* 1400 */               pc.addWis(-1);
/* 1401 */               pc.addCha(-1);
/* 1402 */               pc.addOriginalEr(-5);
/* 1403 */               pc.add_dodge(-1);
/* 1404 */               pc.addDmgup(-5);
/* 1405 */               pc.addBowDmgup(-5);
/* 1406 */               pc.addSp(-3);
/* 1407 */               pc.addAc(-5);
/* 1408 */               pc.addDamageReductionByArmor(-5);
/*      */               
/* 1410 */               pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
/* 1411 */               pc.sendPackets((ServerBasePacket)new S_PacketBox(132, pc.getEr()));
/* 1412 */               pc.sendPackets((ServerBasePacket)new S_PacketBoxIcon1(true, pc.get_dodge()));
/* 1413 */               pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc));
/* 1414 */               if (pc.isInParty()) {
/* 1415 */                 pc.getParty().updateMiniHP(pc);
/*      */               }
/* 1417 */               pc.sendPackets((ServerBasePacket)new S_MPUpdate(pc));
/* 1418 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*      */             } 
/*      */             break;
/*      */           
/*      */           case 8060:
/* 1423 */             if (cha instanceof L1PcInstance) {
/* 1424 */               L1PcInstance pc = (L1PcInstance)cha;
/* 1425 */               pc.addHitup(-3);
/* 1426 */               pc.addDmgup(-3);
/* 1427 */               pc.addBowHitup(-3);
/* 1428 */               pc.addBowDmgup(-3);
/* 1429 */               pc.addSp(-3);
/* 1430 */               pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
/*      */             } 
/*      */             break;
/*      */         }
/*      */       }
/* 1436 */     } catch (final Exception e) {
	_log.error(e.getLocalizedMessage(), e);
}

		if ((cha instanceof L1PcInstance)) {
	    	L1PcInstance pc = (L1PcInstance)cha;
	    	sendStopMessage(pc, skillId);
	    	pc.sendPackets(new S_SPMR(pc));
	    	pc.sendPackets(new S_OwnCharStatus(pc));    
	    	pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));//迴避率更新
	    }
//if (cha instanceof L1PcInstance) {
//	final L1PcInstance pc = (L1PcInstance) cha;
//	sendStopMessage(pc, skillId);
//	pc.sendPackets(new S_OwnCharStatus(pc));
//}
}

	// メッセージの表示（終了するとき）
		private static void sendStopMessage(final L1PcInstance charaPc,
				final int skillid) {
			final L1Skills l1skills = SkillsTable.get().getTemplate(skillid);
			if ((l1skills == null) || (charaPc == null)) {
				return;
			}

			final int msgID = l1skills.getSysmsgIdStop();
			if (msgID > 0) {
				charaPc.sendPackets(new S_ServerMessage(msgID));
			}
		}

	  	//強制踢除	
		private static void KickPc(L1PcInstance pc) {
				_client = pc.getNetConnection();		
		try {
				Thread.sleep(5000);
				_client.kick();
				
			} catch (InterruptedException e) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}	
		
/*      */   private static void ai(L1PcInstance pc) {
/*      */     try {
/* 1475 */       StringBuilder title = new StringBuilder();
/* 1476 */       int newaicount = 0;
/* 1477 */       switch (Random.nextInt(86) + 1) {
/*      */         case 1:
/* 1479 */           pc.setnewaititle("●");
/* 1480 */           title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
/* 1481 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1482 */           newaicount = 1;
/*      */           break;
/*      */         case 2:
/* 1485 */           pc.setnewaititle("● ●");
/* 1486 */           title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
/* 1487 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1488 */           newaicount = 2;
/*      */           break;
/*      */         case 3:
/* 1491 */           pc.setnewaititle("● ● ●");
/* 1492 */           title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
/* 1493 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1494 */           newaicount = 3;
/*      */           break;
/*      */         case 4:
/* 1497 */           pc.setnewaititle("● ● ● ●");
/* 1498 */           title.append("\\f2幾個(●):[ " + pc.getnewaititle() + " ]");
/* 1499 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1500 */           newaicount = 4;
/*      */           break;
/*      */         case 5:
/* 1503 */           pc.setnewaititle("▲");
/* 1504 */           title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
/* 1505 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1506 */           newaicount = 1;
/*      */           break;
/*      */         case 6:
/* 1509 */           pc.setnewaititle("▲▲");
/* 1510 */           title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
/* 1511 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1512 */           newaicount = 2;
/*      */           break;
/*      */         case 7:
/* 1515 */           pc.setnewaititle("▲ ▲ ▲");
/* 1516 */           title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
/* 1517 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1518 */           newaicount = 3;
/*      */           break;
/*      */         case 8:
/* 1521 */           pc.setnewaititle("▲▲▲▲");
/* 1522 */           title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
/* 1523 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1524 */           newaicount = 4;
/*      */           break;
/*      */         case 9:
/* 1527 */           pc.setnewaititle("▲▲▲▲▲");
/* 1528 */           title.append("\\f2幾個(▲):[ " + pc.getnewaititle() + " ]");
/* 1529 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1530 */           newaicount = 5;
/*      */           break;
/*      */         case 10:
/* 1533 */           pc.setnewaititle("９");
/* 1534 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1535 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1536 */           newaicount = 1;
/*      */           break;
/*      */         case 11:
/* 1539 */           pc.setnewaititle("９９");
/* 1540 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1541 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1542 */           newaicount = 2;
/*      */           break;
/*      */         case 12:
/* 1545 */           pc.setnewaititle("９９９");
/* 1546 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1547 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1548 */           newaicount = 3;
/*      */           break;
/*      */         case 13:
/* 1551 */           pc.setnewaititle("９９９９");
/* 1552 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1553 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1554 */           newaicount = 4;
/*      */           break;
/*      */         case 14:
/* 1557 */           pc.setnewaititle("９９９９９");
/* 1558 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1559 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1560 */           newaicount = 5;
/*      */           break;
/*      */         case 15:
/* 1563 */           pc.setnewaititle("８８８８８８");
/* 1564 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1565 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1566 */           newaicount = 6;
/*      */           break;
/*      */         case 16:
/* 1569 */           pc.setnewaititle("８８８８８");
/* 1570 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1571 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1572 */           newaicount = 5;
/*      */           break;
/*      */         case 17:
/* 1575 */           pc.setnewaititle("８８８８");
/* 1576 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1577 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1578 */           newaicount = 4;
/*      */           break;
/*      */         case 18:
/* 1581 */           pc.setnewaititle("８８８");
/* 1582 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1583 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1584 */           newaicount = 3;
/*      */           break;
/*      */         case 19:
/* 1587 */           pc.setnewaititle("８８");
/* 1588 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1589 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1590 */           newaicount = 2;
/*      */           break;
/*      */         case 20:
/* 1593 */           pc.setnewaititle("８");
/* 1594 */           title.append("\\f2幾個(8):[ " + pc.getnewaititle() + " ]");
/* 1595 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1596 */           newaicount = 1;
/*      */           break;
/*      */         case 21:
/* 1599 */           pc.setnewaititle("♂");
/* 1600 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1601 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1602 */           newaicount = 1;
/*      */           break;
/*      */         case 22:
/* 1605 */           pc.setnewaititle("♂ ♂");
/* 1606 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1607 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1608 */           newaicount = 2;
/*      */           break;
/*      */         case 23:
/* 1611 */           pc.setnewaititle("♂ ♂ ♂");
/* 1612 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1613 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1614 */           newaicount = 3;
/*      */           break;
/*      */         case 24:
/* 1617 */           pc.setnewaititle("♂ ♂ ♂ ♂");
/* 1618 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1619 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1620 */           newaicount = 4;
/*      */           break;
/*      */         case 25:
/* 1623 */           pc.setnewaititle("♂♂♂♂♂");
/* 1624 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1625 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1626 */           newaicount = 5;
/*      */           break;
/*      */         case 26:
/* 1629 */           pc.setnewaititle("X");
/* 1630 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1631 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1632 */           newaicount = 1;
/*      */           break;
/*      */         case 27:
/* 1635 */           pc.setnewaititle("X X");
/* 1636 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1637 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1638 */           newaicount = 2;
/*      */           break;
/*      */         case 28:
/* 1641 */           pc.setnewaititle("X X X");
/* 1642 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1643 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1644 */           newaicount = 3;
/*      */           break;
/*      */         case 29:
/* 1647 */           pc.setnewaititle("X X X X");
/* 1648 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1649 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1650 */           newaicount = 4;
/*      */           break;
/*      */         case 30:
/* 1653 */           pc.setnewaititle("XXXXX");
/* 1654 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1655 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1656 */           newaicount = 5;
/*      */           break;
/*      */         case 31:
/* 1659 */           pc.setnewaititle("■");
/* 1660 */           title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
/* 1661 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1662 */           newaicount = 1;
/*      */           break;
/*      */         case 32:
/* 1665 */           pc.setnewaititle("■ ■");
/* 1666 */           title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
/* 1667 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1668 */           newaicount = 2;
/*      */           break;
/*      */         case 33:
/* 1671 */           pc.setnewaititle("■ ■ ■");
/* 1672 */           title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
/* 1673 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1674 */           newaicount = 3;
/*      */           break;
/*      */         case 34:
/* 1677 */           pc.setnewaititle("■ ■ ■ ■");
/* 1678 */           title.append("\\f2幾個方形:[ " + pc.getnewaititle() + " ]");
/* 1679 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1680 */           newaicount = 4;
/*      */           break;
/*      */         case 35:
/* 1683 */           pc.setnewaititle("卍");
/* 1684 */           title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
/* 1685 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1686 */           newaicount = 1;
/*      */           break;
/*      */         case 36:
/* 1689 */           pc.setnewaititle("卍卍");
/* 1690 */           title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
/* 1691 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1692 */           newaicount = 2;
/*      */           break;
/*      */         case 37:
/* 1695 */           pc.setnewaititle("卍卍卍");
/* 1696 */           title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
/* 1697 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1698 */           newaicount = 3;
/*      */           break;
/*      */         case 38:
/* 1701 */           pc.setnewaititle("卍卍卍卍");
/* 1702 */           title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
/* 1703 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1704 */           newaicount = 4;
/*      */           break;
/*      */         case 39:
/* 1707 */           pc.setnewaititle("卍卍卍卍卍");
/* 1708 */           title.append("\\f2幾個(卍):[ " + pc.getnewaititle() + " ]");
/* 1709 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1710 */           newaicount = 5;
/*      */           break;
/*      */         case 40:
/* 1713 */           pc.setnewaititle("力");
/* 1714 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1715 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1716 */           newaicount = 1;
/*      */           break;
/*      */         case 41:
/* 1719 */           pc.setnewaititle("力力");
/* 1720 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1721 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1722 */           newaicount = 2;
/*      */           break;
/*      */         case 42:
/* 1725 */           pc.setnewaititle("力力力");
/* 1726 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1727 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1728 */           newaicount = 3;
/*      */           break;
/*      */         case 43:
/* 1731 */           pc.setnewaititle("力力力力");
/* 1732 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1733 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1734 */           newaicount = 4;
/*      */           break;
/*      */         case 44:
/* 1737 */           pc.setnewaititle("力力力力力");
/* 1738 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1739 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1740 */           newaicount = 5;
/*      */           break;
/*      */         case 45:
/* 1743 */           pc.setnewaititle("力力力力力力");
/* 1744 */           title.append("\\f2幾個(力):[ " + pc.getnewaititle() + " ]");
/* 1745 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1746 */           newaicount = 6;
/*      */           break;
/*      */         case 46:
/* 1749 */           pc.setnewaititle("９");
/* 1750 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1751 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1752 */           newaicount = 1;
/*      */           break;
/*      */         case 47:
/* 1755 */           pc.setnewaititle("９９");
/* 1756 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1757 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1758 */           newaicount = 2;
/*      */           break;
/*      */         case 48:
/* 1761 */           pc.setnewaititle("９９９");
/* 1762 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1763 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1764 */           newaicount = 3;
/*      */           break;
/*      */         case 49:
/* 1767 */           pc.setnewaititle("９９９９");
/* 1768 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1769 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1770 */           newaicount = 4;
/*      */           break;
/*      */         case 50:
/* 1773 */           pc.setnewaititle("９９９９９");
/* 1774 */           title.append("\\f2幾個(9):[ " + pc.getnewaititle() + " ]");
/* 1775 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1776 */           newaicount = 5;
/*      */           break;
/*      */         case 51:
/* 1779 */           pc.setnewaititle("♂♂♂");
/* 1780 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1781 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1782 */           newaicount = 3;
/*      */           break;
/*      */         case 52:
/* 1785 */           pc.setnewaititle("♂");
/* 1786 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1787 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1788 */           newaicount = 1;
/*      */           break;
/*      */         case 53:
/* 1791 */           pc.setnewaititle("♂♂");
/* 1792 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1793 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1794 */           newaicount = 2;
/*      */           break;
/*      */         case 54:
/* 1797 */           pc.setnewaititle("♂♂♂♂");
/* 1798 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1799 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1800 */           newaicount = 4;
/*      */           break;
/*      */         case 55:
/* 1803 */           pc.setnewaititle("♂♂♂♂♂");
/* 1804 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1805 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1806 */           newaicount = 5;
/*      */           break;
/*      */         case 56:
/* 1809 */           pc.setnewaititle("♂♂♂♂♂♂");
/* 1810 */           title.append("\\f2幾個符號:[ " + pc.getnewaititle() + " ]");
/* 1811 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1812 */           newaicount = 6;
/*      */           break;
/*      */         case 57:
/* 1815 */           pc.setnewaititle("XX");
/* 1816 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1817 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1818 */           newaicount = 2;
/*      */           break;
/*      */         case 58:
/* 1821 */           pc.setnewaititle("X");
/* 1822 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1823 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1824 */           newaicount = 1;
/*      */           break;
/*      */         case 59:
/* 1827 */           pc.setnewaititle("XXX");
/* 1828 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1829 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1830 */           newaicount = 3;
/*      */           break;
/*      */         case 60:
/* 1833 */           pc.setnewaititle("XXXX");
/* 1834 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1835 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1836 */           newaicount = 4;
/*      */           break;
/*      */         case 61:
/* 1839 */           pc.setnewaititle("XXXXX");
/* 1840 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1841 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1842 */           newaicount = 5;
/*      */           break;
/*      */         case 62:
/* 1845 */           pc.setnewaititle("XXXXXX");
/* 1846 */           title.append("\\f2幾個(X):[ " + pc.getnewaititle() + " ]");
/* 1847 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1848 */           newaicount = 6;
/*      */           break;
/*      */         case 63:
/* 1851 */           pc.setnewaititle("5");
/* 1852 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1853 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1854 */           newaicount = 1;
/*      */           break;
/*      */         case 64:
/* 1857 */           pc.setnewaititle("55");
/* 1858 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1859 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1860 */           newaicount = 2;
/*      */           break;
/*      */         case 65:
/* 1863 */           pc.setnewaititle("555");
/* 1864 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1865 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1866 */           newaicount = 3;
/*      */           break;
/*      */         case 66:
/* 1869 */           pc.setnewaititle("5555");
/* 1870 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1871 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1872 */           newaicount = 4;
/*      */           break;
/*      */         case 67:
/* 1875 */           pc.setnewaititle("55555");
/* 1876 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1877 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1878 */           newaicount = 5;
/*      */           break;
/*      */         case 68:
/* 1881 */           pc.setnewaititle("555555");
/* 1882 */           title.append("\\f2幾個(5):[ " + pc.getnewaititle() + " ]");
/* 1883 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1884 */           newaicount = 6;
/*      */           break;
/*      */         case 69:
/* 1887 */           pc.setnewaititle("Q");
/* 1888 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1889 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1890 */           newaicount = 1;
/*      */           break;
/*      */         case 70:
/* 1893 */           pc.setnewaititle("QQ");
/* 1894 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1895 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1896 */           newaicount = 2;
/*      */           break;
/*      */         case 71:
/* 1899 */           pc.setnewaititle("QQQ");
/* 1900 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1901 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1902 */           newaicount = 3;
/*      */           break;
/*      */         case 72:
/* 1905 */           pc.setnewaititle("QQQQ");
/* 1906 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1907 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1908 */           newaicount = 4;
/*      */           break;
/*      */         case 73:
/* 1911 */           pc.setnewaititle("QQQQQ");
/* 1912 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1913 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1914 */           newaicount = 5;
/*      */           break;
/*      */         case 74:
/* 1917 */           pc.setnewaititle("QQQQQQ");
/* 1918 */           title.append("\\f2幾個(Q):[ " + pc.getnewaititle() + " ]");
/* 1919 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1920 */           newaicount = 6;
/*      */           break;
/*      */         case 75:
/* 1923 */           pc.setnewaititle("A");
/* 1924 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1925 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1926 */           newaicount = 1;
/*      */           break;
/*      */         case 76:
/* 1929 */           pc.setnewaititle("AA");
/* 1930 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1931 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1932 */           newaicount = 2;
/*      */           break;
/*      */         case 77:
/* 1935 */           pc.setnewaititle("AAA");
/* 1936 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1937 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1938 */           newaicount = 3;
/*      */           break;
/*      */         case 78:
/* 1941 */           pc.setnewaititle("AAAA");
/* 1942 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1943 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1944 */           newaicount = 4;
/*      */           break;
/*      */         case 79:
/* 1947 */           pc.setnewaititle("AAAAA");
/* 1948 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1949 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1950 */           newaicount = 5;
/*      */           break;
/*      */         case 80:
/* 1953 */           pc.setnewaititle("AAAAAA");
/* 1954 */           title.append("\\f2幾個(A):[ " + pc.getnewaititle() + " ]");
/* 1955 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1956 */           newaicount = 6;
/*      */           break;
/*      */         case 81:
/* 1959 */           pc.setnewaititle("@");
/* 1960 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1961 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1962 */           newaicount = 1;
/*      */           break;
/*      */         
/*      */         case 82:
/* 1966 */           pc.setnewaititle("@@");
/* 1967 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1968 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1969 */           newaicount = 2;
/*      */           break;
/*      */         
/*      */         case 83:
/* 1973 */           pc.setnewaititle("@@@");
/* 1974 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1975 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1976 */           newaicount = 3;
/*      */           break;
/*      */         case 84:
/* 1979 */           pc.setnewaititle("@@@@");
/* 1980 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1981 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1982 */           newaicount = 4;
/*      */           break;
/*      */         case 85:
/* 1985 */           pc.setnewaititle("@@@@@");
/* 1986 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1987 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1988 */           newaicount = 5;
/*      */           break;
/*      */         case 86:
/* 1991 */           pc.setnewaititle("@@@@@@");
/* 1992 */           title.append("\\f2幾個(@):[ " + pc.getnewaititle() + " ]");
/* 1993 */           pc.sendPacketsAll((ServerBasePacket)new S_CharTitle(pc.getId(), title));
/* 1994 */           newaicount = 6;
/*      */           break;
/*      */       } 
/* 1997 */       pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 227));
/* 1998 */       pc.setnewaicount(newaicount);
/*      */     }
/* 2000 */     catch (Exception e) {
/* 2001 */       _log.error(e.getLocalizedMessage(), e);
/*      */     } 
/*      */   }
/*      */   private static void 元寶差異紀錄(String info) {
/*      */     try {
/* 2006 */       BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[元寶差異紀錄].txt", true));
/* 2007 */       out.write(String.valueOf(info) + "\r\n");
/* 2008 */       out.close();
/* 2009 */     } catch (IOException e) {
/* 2010 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */   private static void newai(L1PcInstance pc) {
/* 2014 */     String[] info = new String[34];
/* 2015 */     info[1] = String.valueOf(pc.getnewai1());
/* 2016 */     info[2] = String.valueOf(pc.getnewai2());
/* 2017 */     info[3] = String.valueOf(pc.getnewai3());
/*      */ 
/*      */     
/* 2020 */     info[4] = String.valueOf(pc.getnewai4());
/* 2021 */     info[5] = String.valueOf(pc.getnewai5());
/* 2022 */     info[6] = String.valueOf(pc.getnewai6());
/*      */ 
/*      */ 
/*      */     
/* 2026 */     switch (Random.nextInt(10) + 1) {
/*      */       case 1:
/* 2028 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai", info));
/*      */         break;
/*      */       case 2:
/* 2031 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai1", info));
/*      */         break;
/*      */       case 3:
/* 2034 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai2", info));
/*      */         break;
/*      */       case 4:
/* 2037 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai3", info));
/*      */         break;
/*      */       case 5:
/* 2040 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai4", info));
/*      */         break;
/*      */       case 6:
/* 2043 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai5", info));
/*      */         break;
/*      */       case 7:
/* 2046 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai6", info));
/*      */         break;
/*      */       case 8:
/* 2049 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai7", info));
/*      */         break;
/*      */       case 9:
/* 2052 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai8", info));
/*      */         break;
/*      */       case 10:
/* 2055 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "newai9", info));
/*      */         break;
/*      */     } 
/*      */   }
/*      */ }