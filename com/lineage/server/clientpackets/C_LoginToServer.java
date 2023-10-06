package com.lineage.server.clientpackets;

import com.lineage.server.model.L1ClanMatching;
import com.lineage.data.event.ProtectorSet;
import com.lineage.server.datatables.lock.CharMapTimeReading;
import com.lineage.server.serverpackets.S_PacketBoxActiveSpells;
import com.lineage.server.serverpackets.S_NewMaster;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.templates.L1UserSkillTmp;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.templates.L1BookConfig;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.serverpackets.S_Bookmarks;
import com.lineage.config.ConfigAlt;
import com.lineage.server.datatables.lock.CharBookConfigReading;
import com.lineage.server.datatables.lock.CharBookReading;

import java.util.List;

import com.lineage.server.serverpackets.S_InvList;
import com.lineage.server.templates.L1GetBackRestart;
import com.lineage.server.model.L1Character;
import com.lineage.server.datatables.GetBackRestartTable;
import com.lineage.server.templates.L1EmblemIcon;
import com.lineage.server.serverpackets.S_War;
import com.lineage.server.model.L1War;
import com.lineage.server.world.WorldWar;
import com.lineage.server.serverpackets.S_Emblem;
import com.lineage.server.datatables.lock.ClanEmblemReading;
import com.lineage.data.event.ClanSkillDBSet;
import com.lineage.data.npc.Npc_clan;
import com.lineage.server.world.WorldClan;
import com.lineage.data.event.OnlineGiftSet;
import com.lineage.server.templates.L1PcOtherList;
import com.lineage.server.serverpackets.S_Weather;
import com.lineage.server.serverpackets.S_Karma;
import com.lineage.server.serverpackets.S_Mail;

import java.util.ArrayList;

import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_OwnCharPack;
import com.lineage.server.serverpackets.S_MapID;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_RemoveObject;

import java.util.Map;

import com.lineage.server.serverpackets.S_CastleMaster;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1CastleLocation;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;

import com.lineage.server.utils.SQLUtil;

import java.sql.SQLException;

import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.CheckItemPowerTable;
import com.add.system.CardPolySet_doll;
import com.add.system.ACard_doll;
import com.add.system.CardPolySet;
import com.add.system.ACard;
import com.lineage.server.Manly.L1WenYang;
import com.lineage.server.templates.L1User_Power;

import java.util.Collection;

import com.lineage.server.templates.L1Config;

import java.util.Iterator;

import com.lineage.server.templates.L1Account;
import com.add.system.CardSetTable_doll;
import com.add.system.ACardTable_doll;
import com.add.system.CardSetTable;
import com.add.system.ACardTable;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.Manly.WenYangTable;
import com.lineage.server.datatables.ServerCrockTable;
import com.lineage.data.event.servercrock;
import com.lineage.config.ConfigAi;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.data.event.checkpcitem;
import com.lineage.william.CastleOriginal;
import com.add.L1PcUnlock;
import com.lineage.server.datatables.C1_Name_Table;
import com.lineage.server.datatables.lock.CharacterC1Reading;
import com.lineage.data.event.CampSet;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.datatables.RewardPrestigeTable;
import com.lineage.server.storage.mysql.MySqlCharacterStorage;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.add.NewAutoPractice;
import com.lineage.william.ClanOriginal;
import com.lineage.server.serverpackets.S_PacketBoxProtection;
import com.lineage.config.ConfigOther;

import java.util.Calendar;

import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.serverpackets.S_CharResetInfo;
import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.serverpackets.S_PacketBoxConfig;
import com.lineage.server.datatables.lock.CharacterConfigReading;
import com.lineage.server.datatables.lock.shop_lxReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.serverpackets.S_EnterGame;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_InitialAbilityGrowth;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.commons.system.LanSecurityManager;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.echo.ClientExecutor;

import org.apache.commons.logging.LogFactory;

import java.util.Random;

import org.apache.commons.logging.Log;

public class C_LoginToServer extends ClientBasePacket
{
    private static final Log _log;
    private static Random _random;
    
    static {
        _log = LogFactory.getLog((Class)C_LoginToServer.class);
        C_LoginToServer._random = new Random();
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
            this.read(decrypt);
            final String loginName = client.getAccountName();
            final String charName = this.readS();
            final L1PcInstance pc = L1PcInstance.load(charName);
            if (pc == null || !loginName.equals(pc.getAccountName())) {
                C_LoginToServer._log.info((Object)("無效登入要求: " + charName + " 帳號(" + loginName + ", " + (Object)client.getIp() + ")"));
                client.kick();
                return;
            }
            final L1Account act = AccountReading.get().getAccount(pc.getAccountName());
            if (act.is_isLoad()) {
                C_LoginToServer._log.info("同一個帳號雙重角色登入，強制切斷 " + client.getIp() + ") 的連結");
                client.close();
                return;
            }
            if (Config.GUI) {
                J_Main.getInstance().addPlayerTable(loginName, charName, client.getIp());
            }
            LanSecurityManager.Loginer.put(client.getIp().toString(), 3);
            final int currentHpAtLoad = pc.getCurrentHp();
            final int currentMpAtLoad = pc.getCurrentMp();
            client.set_error(0);
            pc.clearSkillMastery();
            pc.setOnlineStatus(1);
            CharacterTable.updateOnlineStatus(pc);
            World.get().storeObject(pc);
            pc.setNetConnection(client);
            client.setActiveChar(pc);
            if (pc.getTheEnemy() == null) {
                pc.setTheEnemy(pc.getName());
            }
            this.getOther(pc);
            pc.sendPackets(new S_InitialAbilityGrowth(pc));
            pc.sendPackets(new S_EnterGame(pc));
            items(pc);
            this.bookmarks(pc);
            this.backRestart(pc);
            this.getFocus(pc);
            pc.sendVisualEffectAtLogin();
            this.skills(pc);
            this.buff(pc);
            for (final L1ItemInstance item : pc.getInventory().getItems()) {
                if (item.isEquipped()) {
                    pc.getInventory().toSlotPacket(pc, item);
                }
            }
            pc.turnOnOffLight();
            if (pc.getCurrentHp() > 0) {
                pc.setDead(false);
                pc.setStatus(0);
            }
            else {
                pc.setDead(true);
                pc.setStatus(8);
            }
            shop_lxReading.get().remove(pc.getName());
            final L1Config config = CharacterConfigReading.get().get(pc.getId());
            if (config != null) {
                pc.sendPackets(new S_PacketBoxConfig(config));
            }
            this.serchSummon(pc);
            ServerWarExecutor.get().checkCastleWar(pc);
            this.war(pc);
            this.marriage(pc);
            this.ClanMatching(pc);
            if (currentHpAtLoad > pc.getCurrentHp()) {
                pc.setCurrentHp(currentHpAtLoad);
            }
            if (currentMpAtLoad > pc.getCurrentMp()) {
                pc.setCurrentMp(currentMpAtLoad);
            }
            pc.startHpRegeneration();
            pc.startMpRegeneration();
            pc.startObjectAutoUpdate();
            this.crown(pc);
            if (LeavesSet.START) {
                final int logintime = pc.getlogintime() / LeavesSet.EXP;
                if (logintime > 0) {
                    pc.sendPackets(new S_PacketBoxExp(logintime));
                }
            }
            if (pc.getcardpoly() == 0) {
                pc.setcardpoly(-1);
            }
            pc.save();
            if (pc.getHellTime() > 0) {
                pc.beginHell(false);
            }
            pc.sendPackets(new S_CharResetInfo(pc));
            pc.load_src();
            pc.getQuest().load();
            pc.showWindows();
            pc.get_other().set_usemap(-1);
            pc.get_other().set_usemapTime(0);
            ServerUseMapTimer.MAP.remove(pc);
            if (QuestMobSet.START) {
                ServerQuestMobTable.get().getQuestMobNote(pc);
            }
            if (pc.get_food() >= 225) {
                final Calendar cal = Calendar.getInstance();
                final long h_time = cal.getTimeInMillis() / 1000L;
                pc.set_h_time(h_time);
            }
            if (pc.getLevel() <= ConfigOther.ENCOUNTER_LV) {
                pc.sendPackets(new S_PacketBoxProtection(6, 1));
            }
            pc.lawfulUpdate();
            ClanOriginal.forIntensifyArmor(pc);
            NewAutoPractice.get().addEnemy(pc);
            NewAutoPractice.get().addBadEnemy(pc);
			if (ConfigOther.DragonKnightpc && pc.isIllusionist()) {
				pc.sendPackets(new S_SystemMessage(" \\fT[目前尚未開放此職業進行遊戲"));
				pc.sendPackets(new S_SystemMessage(" \\fT請另選職業進行遊戲冒險"));
				pc.sendPackets(new S_SystemMessage(" \\fT詳細資訊職業開放查看官方公告資訊 。]"));
    			pc.sendPackets(new S_SystemMessage(" \\fT[10秒後將進行刪除角色動作]"));
    			pc.sendPackets(new S_Paralysis(5, true));
    			Thread.sleep(10000L);
    			pc.sendPackets(new S_Disconnect());
    			final MySqlCharacterStorage mySqlCharacterStorage = new MySqlCharacterStorage();
    			mySqlCharacterStorage.alldeleteCharacter(pc.getAccountName(), pc.getName());
			}
			if (ConfigOther.Illusionistpc && pc.isDragonKnight()) {
    			pc.sendPackets(new S_SystemMessage(" \\fT[目前尚未開放此職業進行遊戲"));
    			pc.sendPackets(new S_SystemMessage(" \\fT請另選職業進行遊戲冒險"));
    			pc.sendPackets(new S_SystemMessage(" \\fT詳細資訊職業開放查看官方公告資訊 。]"));
    			pc.sendPackets(new S_SystemMessage(" \\fT[10秒後將進行刪除角色動作]"));
    			pc.sendPackets(new S_Paralysis(5, true));
    			Thread.sleep(10000L);
    			pc.sendPackets(new S_Disconnect());
    			final MySqlCharacterStorage mySqlCharacterStorage = new MySqlCharacterStorage();
    			mySqlCharacterStorage.alldeleteCharacter(pc.getAccountName(), pc.getName());
			}
            if (pc.getPrestigeLv() != 0) {
                RewardPrestigeTable.get().addPrestige(pc);
            }
            if (ConfigOther.WHO_ONLINE_MSG_ON) {
                final Collection<L1PcInstance> allplayer = World.get().getAllPlayers();
                for (final L1PcInstance object : allplayer) {
                    if (object instanceof L1PcInstance) {
                        final L1PcInstance GM = object;
                        if (GM.getAccessLevel() != 100 && GM.getAccessLevel() != 200) {
                            continue;
                        }
                        String msg = "";
			if (pc.isCrown()) {
			    msg = "王子";
			}
			else if (pc.isKnight()) {
    			msg = "騎士";
			}
			else if (pc.isElf()) {
    			msg = "精靈";
			}
			else if (pc.isWizard()) {
    			msg = "法師";
			}
			else if (pc.isDarkelf()) {
    			msg = "黑精靈";
			}
			else if (pc.isDragonKnight()) {
    			msg = "龍騎士";
			}
			else if (pc.isIllusionist()) {
    			msg = "幻術師";
			}

			if (pc.isGm()) {
    			GM.sendPackets(new S_ServerMessage("\\fY【管理員帳號】"));
    			GM.sendPackets(new S_ServerMessage("\\fY【帳號:" + client.getAccountName() + "】【名稱:" + pc.getName() + "】"));
    			GM.sendPackets(new S_ServerMessage("\\fY【職業:" + msg + "】【IP:" + (Object)client.getIp() + "】登入。"));
    			pc.setcheckgm(true);
			}
			else {
    			GM.sendPackets(new S_ServerMessage("\\fY【帳號:" + client.getAccountName() + "】【名稱:" + pc.getName() + "】"));
    			GM.sendPackets(new S_ServerMessage("\\fY【職業:" + msg + "】【IP:" + (Object)client.getIp() + "】登入。"));
			}

                    }
                }
            }
            if (CampSet.CAMPSTART) {
                final L1User_Power value = CharacterC1Reading.get().get(pc.getId());
                if (value != null) {
                    pc.set_c_power(value);
                    if (value.get_c1_type() != 0) {
                        pc.get_c_power().set_power(pc, true);
                        final String type = C1_Name_Table.get().get(pc.get_c_power().get_c1_type());
                        pc.sendPackets(new S_ServerMessage("\\fR您目前所屬的陣營：" + type));
                        L1PcUnlock.Pc_Unlock(pc);
                    }
                }
            }
            checkforProtector(pc);
            CastleOriginal.forCastleOriginal(pc);
            checkforDADISStone(pc);
            checkforSouls(pc);
            if (checkpcitem.START) {
                CheckItemPower(pc);
            }
            pc.setSkillEffect(1688, 0);
            pc.setSkillEffect(1689, 0);
            pc.setSkillEffect(1690, 0);
            pc.setSkillEffect(1691, 0);
            pc.setSkillEffect(1692, 0);
            if (pc.isDragonKnight() && pc.getlogpcpower_SkillFor5() > 0) {
                pc.addMaxHp(400 * pc.getlogpcpower_SkillFor5());
                pc.sendPackets(new S_OwnCharStatus(pc));
            }
//            if (this.CheckMail(pc) > 0) {
//                pc.sendPackets(new S_SkillSound(pc.getId(), 1091));
//                pc.sendPackets(new S_ServerMessage(428));
//            }

			pc.sendPackets(new S_ServerMessage("找服請搜尋 私服123 網址:Gamex123.com"));

			if (pc.isGm()) {
				pc.setGmInvis(true);
				pc.sendPackets(new S_Invis(pc.getId(), 1));
				pc.broadcastPacketAll(new S_RemoveObject(pc));
				pc.sendPackets(new S_SystemMessage("\\F3啟用線上GM隱身模式。"));
				if(ConfigOther.gmverify) { //驗證碼開關
				pc.killSkillEffectTimer(51239); //刪除GM權限
				pc.sendPackets(new S_Paralysis(4, true)); //凍結狀態
				pc.setSkillEffect(98766, 1); //驗證碼輸入
				}
			}

            if (!pc.isGm() && ConfigAi.longntimeai_3) {
                final int time = ConfigAi.logintime + C_LoginToServer._random.nextInt(ConfigAi.aitimeran) + 1;
                pc.setSkillEffect(6930, time * 1000);
            }
            pc.set_MOVE_STOP(false);
            if (servercrock.START) {
                ServerCrockTable.get().loginMessage(pc);
            }
            this.getUpdate(pc);
            if (pc.getWyType1() == 1 && pc.getWyLevel1() > 0) {
                final L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType1(), pc.getWyLevel1());
                if (wenYang != null) {
                    final int liliang = wenYang.getliliang();
                    if (liliang != 0) {
                        pc.addStr(liliang);
                    }
                    final int minjie = wenYang.getminjie();
                    if (minjie != 0) {
                        pc.addDex(minjie);
                    }
                    final int zhili = wenYang.getzhili();
                    if (zhili != 0) {
                        pc.addInt(zhili);
                    }
                    final int jingshen = wenYang.getjingshen();
                    if (jingshen != 0) {
                        pc.addWis(jingshen);
                    }
                    final int tizhi = wenYang.gettizhi();
                    if (tizhi != 0) {
                        pc.addCon(tizhi);
                    }
                    final int meili = wenYang.getmeili();
                    if (meili != 0) {
                        pc.addCha(meili);
                    }
                    final int xue = wenYang.getxue();
                    if (xue != 0) {
                        pc.addMaxHp(xue);
                    }
                    final int mo = wenYang.getmo();
                    if (mo != 0) {
                        pc.addMaxMp(mo);
                    }
                    final int huixue = wenYang.gethuixue();
                    if (huixue != 0) {
                        pc.addHpr(huixue);
                    }
                    final int huimo = wenYang.gethuimo();
                    if (huimo != 0) {
                        pc.addMpr(huimo);
                    }
                    final int ewai = wenYang.getewai();
                    if (ewai != 0) {
                        pc.addDmgup(ewai);
                        pc.addBowDmgup(ewai);
                    }
                    final int chenggong = wenYang.getchenggong();
                    if (chenggong != 0) {
                        pc.addHitup(chenggong);
                        pc.addBowHitup(chenggong);
                    }
                    final int mogong = wenYang.getmogong();
                    if (mogong != 0) {
                        pc.addSp(mogong);
                    }
                    final int mofang = wenYang.getmofang();
                    if (mofang != 0) {
                        pc.addMr(mofang);
                    }
                    final int feng = wenYang.getfeng();
                    if (feng != 0) {
                        pc.addWind(feng);
                    }
                    final int shui = wenYang.getshui();
                    if (shui != 0) {
                        pc.addWater(shui);
                    }
                    final int tu = wenYang.gettu();
                    if (tu != 0) {
                        pc.addEarth(tu);
                    }
                    final int huo = wenYang.gethuo();
                    if (huo != 0) {
                        pc.addFire(huo);
                    }
                    final int jianmian = wenYang.getjianmian();
                    if (jianmian != 0) {
                        pc.add_reduction_dmg(jianmian);
                    }
                    final int jingyan = wenYang.getjingyan();
                    if (jingyan != 0) {
                        pc.setExpRes(jingyan);
                    }
                    pc.sendPackets(new S_SystemMessage("獲得" + wenYang.getNot() + "紋樣屬性"));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            if (pc.getWyType2() == 2 && pc.getWyLevel2() > 0) {
                final L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType2(), pc.getWyLevel2());
                if (wenYang != null) {
                    final int liliang = wenYang.getliliang();
                    if (liliang != 0) {
                        pc.addStr(liliang);
                    }
                    final int minjie = wenYang.getminjie();
                    if (minjie != 0) {
                        pc.addDex(minjie);
                    }
                    final int zhili = wenYang.getzhili();
                    if (zhili != 0) {
                        pc.addInt(zhili);
                    }
                    final int jingshen = wenYang.getjingshen();
                    if (jingshen != 0) {
                        pc.addWis(jingshen);
                    }
                    final int tizhi = wenYang.gettizhi();
                    if (tizhi != 0) {
                        pc.addCon(tizhi);
                    }
                    final int meili = wenYang.getmeili();
                    if (meili != 0) {
                        pc.addCha(meili);
                    }
                    final int xue = wenYang.getxue();
                    if (xue != 0) {
                        pc.addMaxHp(xue);
                    }
                    final int mo = wenYang.getmo();
                    if (mo != 0) {
                        pc.addMaxMp(mo);
                    }
                    final int huixue = wenYang.gethuixue();
                    if (huixue != 0) {
                        pc.addHpr(huixue);
                    }
                    final int huimo = wenYang.gethuimo();
                    if (huimo != 0) {
                        pc.addMpr(huimo);
                    }
                    final int ewai = wenYang.getewai();
                    if (ewai != 0) {
                        pc.addDmgup(ewai);
                        pc.addBowDmgup(ewai);
                    }
                    final int chenggong = wenYang.getchenggong();
                    if (chenggong != 0) {
                        pc.addHitup(chenggong);
                        pc.addBowHitup(chenggong);
                    }
                    final int mogong = wenYang.getmogong();
                    if (mogong != 0) {
                        pc.addSp(mogong);
                    }
                    final int mofang = wenYang.getmofang();
                    if (mofang != 0) {
                        pc.addMr(mofang);
                    }
                    final int feng = wenYang.getfeng();
                    if (feng != 0) {
                        pc.addWind(feng);
                    }
                    final int shui = wenYang.getshui();
                    if (shui != 0) {
                        pc.addWater(shui);
                    }
                    final int tu = wenYang.gettu();
                    if (tu != 0) {
                        pc.addEarth(tu);
                    }
                    final int huo = wenYang.gethuo();
                    if (huo != 0) {
                        pc.addFire(huo);
                    }
                    final int jianmian = wenYang.getjianmian();
                    if (jianmian != 0) {
                        pc.add_reduction_dmg(jianmian);
                    }
                    final int jingyan = wenYang.getjingyan();
                    if (jingyan != 0) {
                        pc.setExpRes(jingyan);
                    }
                    pc.sendPackets(new S_SystemMessage("獲得" + wenYang.getNot() + "紋樣屬性"));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            if (pc.getWyType3() == 3 && pc.getWyLevel3() > 0) {
                final L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType3(), pc.getWyLevel3());
                if (wenYang != null) {
                    final int liliang = wenYang.getliliang();
                    if (liliang != 0) {
                        pc.addStr(liliang);
                    }
                    final int minjie = wenYang.getminjie();
                    if (minjie != 0) {
                        pc.addDex(minjie);
                    }
                    final int zhili = wenYang.getzhili();
                    if (zhili != 0) {
                        pc.addInt(zhili);
                    }
                    final int jingshen = wenYang.getjingshen();
                    if (jingshen != 0) {
                        pc.addWis(jingshen);
                    }
                    final int tizhi = wenYang.gettizhi();
                    if (tizhi != 0) {
                        pc.addCon(tizhi);
                    }
                    final int meili = wenYang.getmeili();
                    if (meili != 0) {
                        pc.addCha(meili);
                    }
                    final int xue = wenYang.getxue();
                    if (xue != 0) {
                        pc.addMaxHp(xue);
                    }
                    final int mo = wenYang.getmo();
                    if (mo != 0) {
                        pc.addMaxMp(mo);
                    }
                    final int huixue = wenYang.gethuixue();
                    if (huixue != 0) {
                        pc.addHpr(huixue);
                    }
                    final int huimo = wenYang.gethuimo();
                    if (huimo != 0) {
                        pc.addMpr(huimo);
                    }
                    final int ewai = wenYang.getewai();
                    if (ewai != 0) {
                        pc.addDmgup(ewai);
                        pc.addBowDmgup(ewai);
                    }
                    final int chenggong = wenYang.getchenggong();
                    if (chenggong != 0) {
                        pc.addHitup(chenggong);
                        pc.addBowHitup(chenggong);
                    }
                    final int mogong = wenYang.getmogong();
                    if (mogong != 0) {
                        pc.addSp(mogong);
                    }
                    final int mofang = wenYang.getmofang();
                    if (mofang != 0) {
                        pc.addMr(mofang);
                    }
                    final int feng = wenYang.getfeng();
                    if (feng != 0) {
                        pc.addWind(feng);
                    }
                    final int shui = wenYang.getshui();
                    if (shui != 0) {
                        pc.addWater(shui);
                    }
                    final int tu = wenYang.gettu();
                    if (tu != 0) {
                        pc.addEarth(tu);
                    }
                    final int huo = wenYang.gethuo();
                    if (huo != 0) {
                        pc.addFire(huo);
                    }
                    final int jianmian = wenYang.getjianmian();
                    if (jianmian != 0) {
                        pc.add_reduction_dmg(jianmian);
                    }
                    final int jingyan = wenYang.getjingyan();
                    if (jingyan != 0) {
                        pc.setExpRes(jingyan);
                    }
                    pc.sendPackets(new S_SystemMessage("獲得" + wenYang.getNot() + "紋樣屬性"));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            if (pc.getWyType4() == 4 && pc.getWyLevel4() > 0) {
                final L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType4(), pc.getWyLevel4());
                if (wenYang != null) {
                    final int liliang = wenYang.getliliang();
                    if (liliang != 0) {
                        pc.addStr(liliang);
                    }
                    final int minjie = wenYang.getminjie();
                    if (minjie != 0) {
                        pc.addDex(minjie);
                    }
                    final int zhili = wenYang.getzhili();
                    if (zhili != 0) {
                        pc.addInt(zhili);
                    }
                    final int jingshen = wenYang.getjingshen();
                    if (jingshen != 0) {
                        pc.addWis(jingshen);
                    }
                    final int tizhi = wenYang.gettizhi();
                    if (tizhi != 0) {
                        pc.addCon(tizhi);
                    }
                    final int meili = wenYang.getmeili();
                    if (meili != 0) {
                        pc.addCha(meili);
                    }
                    final int xue = wenYang.getxue();
                    if (xue != 0) {
                        pc.addMaxHp(xue);
                    }
                    final int mo = wenYang.getmo();
                    if (mo != 0) {
                        pc.addMaxMp(mo);
                    }
                    final int huixue = wenYang.gethuixue();
                    if (huixue != 0) {
                        pc.addHpr(huixue);
                    }
                    final int huimo = wenYang.gethuimo();
                    if (huimo != 0) {
                        pc.addMpr(huimo);
                    }
                    final int ewai = wenYang.getewai();
                    if (ewai != 0) {
                        pc.addDmgup(ewai);
                        pc.addBowDmgup(ewai);
                    }
                    final int chenggong = wenYang.getchenggong();
                    if (chenggong != 0) {
                        pc.addHitup(chenggong);
                        pc.addBowHitup(chenggong);
                    }
                    final int mogong = wenYang.getmogong();
                    if (mogong != 0) {
                        pc.addSp(mogong);
                    }
                    final int mofang = wenYang.getmofang();
                    if (mofang != 0) {
                        pc.addMr(mofang);
                    }
                    final int feng = wenYang.getfeng();
                    if (feng != 0) {
                        pc.addWind(feng);
                    }
                    final int shui = wenYang.getshui();
                    if (shui != 0) {
                        pc.addWater(shui);
                    }
                    final int tu = wenYang.gettu();
                    if (tu != 0) {
                        pc.addEarth(tu);
                    }
                    final int huo = wenYang.gethuo();
                    if (huo != 0) {
                        pc.addFire(huo);
                    }
                    final int jianmian = wenYang.getjianmian();
                    if (jianmian != 0) {
                        pc.add_reduction_dmg(jianmian);
                    }
                    final int jingyan = wenYang.getjingyan();
                    if (jingyan != 0) {
                        pc.setExpRes(jingyan);
                    }
                    pc.sendPackets(new S_SystemMessage("獲得" + wenYang.getNot() + "紋樣屬性"));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            if (pc.getWyType5() == 5 && pc.getWyLevel5() > 0) {
                final L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType5(), pc.getWyLevel5());
                if (wenYang != null) {
                    final int liliang = wenYang.getliliang();
                    if (liliang != 0) {
                        pc.addStr(liliang);
                    }
                    final int minjie = wenYang.getminjie();
                    if (minjie != 0) {
                        pc.addDex(minjie);
                    }
                    final int zhili = wenYang.getzhili();
                    if (zhili != 0) {
                        pc.addInt(zhili);
                    }
                    final int jingshen = wenYang.getjingshen();
                    if (jingshen != 0) {
                        pc.addWis(jingshen);
                    }
                    final int tizhi = wenYang.gettizhi();
                    if (tizhi != 0) {
                        pc.addCon(tizhi);
                    }
                    final int meili = wenYang.getmeili();
                    if (meili != 0) {
                        pc.addCha(meili);
                    }
                    final int xue = wenYang.getxue();
                    if (xue != 0) {
                        pc.addMaxHp(xue);
                    }
                    final int mo = wenYang.getmo();
                    if (mo != 0) {
                        pc.addMaxMp(mo);
                    }
                    final int huixue = wenYang.gethuixue();
                    if (huixue != 0) {
                        pc.addHpr(huixue);
                    }
                    final int huimo = wenYang.gethuimo();
                    if (huimo != 0) {
                        pc.addMpr(huimo);
                    }
                    final int ewai = wenYang.getewai();
                    if (ewai != 0) {
                        pc.addDmgup(ewai);
                        pc.addBowDmgup(ewai);
                    }
                    final int chenggong = wenYang.getchenggong();
                    if (chenggong != 0) {
                        pc.addHitup(chenggong);
                        pc.addBowHitup(chenggong);
                    }
                    final int mogong = wenYang.getmogong();
                    if (mogong != 0) {
                        pc.addSp(mogong);
                    }
                    final int mofang = wenYang.getmofang();
                    if (mofang != 0) {
                        pc.addMr(mofang);
                    }
                    final int feng = wenYang.getfeng();
                    if (feng != 0) {
                        pc.addWind(feng);
                    }
                    final int shui = wenYang.getshui();
                    if (shui != 0) {
                        pc.addWater(shui);
                    }
                    final int tu = wenYang.gettu();
                    if (tu != 0) {
                        pc.addEarth(tu);
                    }
                    final int huo = wenYang.gethuo();
                    if (huo != 0) {
                        pc.addFire(huo);
                    }
                    final int jianmian = wenYang.getjianmian();
                    if (jianmian != 0) {
                        pc.add_reduction_dmg(jianmian);
                    }
                    final int jingyan = wenYang.getjingyan();
                    if (jingyan != 0) {
                        pc.setExpRes(jingyan);
                    }
                    pc.sendPackets(new S_SystemMessage("獲得" + wenYang.getNot() + "紋樣屬性"));
                    pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
                    pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_OwnCharStatus2(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            if (pc.getQuest().get_step(30677) == 1) {
                pc.setoldexp(pc.getExp());
                pc.setInCharReset(true);
                pc.clearAllSkill();
                CharBuffReading.get().deleteBuff(pc);
                pc.getInventory().takeoffEquip(945);
                L1Teleport.teleport(pc, 32737, 32789, (short)997, 4, false);
                pc.sendPackets(new S_Paralysis(4, true));
                pc.setTempMaxLevel(pc.getHighLevel());
                pc.setTempLevel(1);
                pc.setTempInitPoint(0);
                pc.setTempElixirstats(0);
                if (pc.isCrown()) {
                    pc.setTempStr(13);
                    pc.setTempDex(10);
                    pc.setTempCon(10);
                    pc.setTempWis(11);
                    pc.setTempCha(13);
                    pc.setTempInt(10);
                }
                else if (pc.isKnight()) {
                    pc.setTempStr(16);
                    pc.setTempDex(12);
                    pc.setTempCon(14);
                    pc.setTempWis(9);
                    pc.setTempCha(12);
                    pc.setTempInt(8);
                }
                else if (pc.isElf()) {
                    pc.setTempStr(11);
                    pc.setTempDex(12);
                    pc.setTempCon(12);
                    pc.setTempWis(12);
                    pc.setTempCha(9);
                    pc.setTempInt(12);
                }
                else if (pc.isWizard()) {
                    pc.setTempStr(8);
                    pc.setTempDex(7);
                    pc.setTempCon(12);
                    pc.setTempWis(12);
                    pc.setTempCha(8);
                    pc.setTempInt(12);
                }
                else if (pc.isDarkelf()) {
                    pc.setTempStr(12);
                    pc.setTempDex(15);
                    pc.setTempCon(8);
                    pc.setTempWis(10);
                    pc.setTempCha(9);
                    pc.setTempInt(11);
                }
                else if (pc.isDragonKnight()) {
                    pc.setTempStr(13);
                    pc.setTempDex(11);
                    pc.setTempCon(14);
                    pc.setTempWis(12);
                    pc.setTempCha(8);
                    pc.setTempInt(11);
                }
                else if (pc.isIllusionist()) {
                    pc.setTempStr(11);
                    pc.setTempDex(10);
                    pc.setTempCon(12);
                    pc.setTempWis(12);
                    pc.setTempCha(8);
                    pc.setTempInt(12);
                }
                pc.setExp(1L);
                pc.resetLevel();
                int hp = 0;
                int mp = 0;
                if (BaseResetSet.RETAIN != 0) {
                    hp = pc.getMaxHp() * BaseResetSet.RETAIN / 100;
                    mp = pc.getMaxMp() * BaseResetSet.RETAIN / 100;
                }
                else {
                    hp = CalcInitHpMp.calcInitHp(pc);
                    mp = CalcInitHpMp.calcInitMp(pc);
                }
                L1ActionPc.initCharStatus(pc, hp, mp, pc.getTempStr(), pc.getTempInt(), pc.getTempWis(), pc.getTempDex(), pc.getTempCon(), pc.getTempCha());
                pc.sendPackets(new S_SPMR(pc));
                pc.sendPackets(new S_OwnCharStatus(pc));
                pc.sendPackets(new S_PacketBox(132, pc.getEr()));
                L1ActionPc.checkInitPower(pc);
            }
            for (int i = 0; i <= ACardTable.get().ACardSize(); ++i) {
                final ACard card = ACardTable.get().getCard(i);
                if (card != null && pc.getQuest().get_step(card.getQuestId()) != 0) {
                    pc.addStr(card.getAddStr());
                    pc.addDex(card.getAddDex());
                    pc.addCon(card.getAddCon());
                    pc.addInt(card.getAddInt());
                    pc.addWis(card.getAddWis());
                    pc.addCha(card.getAddCha());
                    pc.addAc(card.getAddAc());
                    pc.addMaxHp(card.getAddHp());
                    pc.addMaxMp(card.getAddMp());
                    pc.addHpr(card.getAddHpr());
                    pc.addMpr(card.getAddMpr());
                    pc.addDmgup(card.getAddDmg());
                    pc.addBowDmgup(card.getAddBowDmg());
                    pc.addHitup(card.getAddHit());
                    pc.addBowHitup(card.getAddBowHit());
                    pc.addDamageReductionByArmor(card.getAddDmgR());
                    pc.addMagicDmgReduction(card.getAddMagicDmgR());
                    pc.addSp(card.getAddSp());
                    pc.addMagicDmgModifier(card.getAddMagicHit());
                    pc.addMr(card.getAddMr());
                    pc.addFire(card.getAddFire());
                    pc.addWater(card.getAddWater());
                    pc.addWind(card.getAddWind());
                    pc.addEarth(card.getAddEarth());
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_SPMR(pc));
                    pc.sendPackets(new S_SystemMessage(card.getMsg1()));
                }
            }
            for (int i = 0; i <= CardSetTable.get().CardCardSize(); ++i) {
                final CardPolySet cards = CardSetTable.get().getCard(i);
                if (cards != null && pc.getQuest().get_step(cards.getQuestId()) != 0) {
                    pc.addStr(cards.getAddStr());
                    pc.addDex(cards.getAddDex());
                    pc.addCon(cards.getAddCon());
                    pc.addInt(cards.getAddInt());
                    pc.addWis(cards.getAddWis());
                    pc.addCha(cards.getAddCha());
                    pc.addAc(cards.getAddAc());
                    pc.addMaxHp(cards.getAddHp());
                    pc.addMaxMp(cards.getAddMp());
                    pc.addHpr(cards.getAddHpr());
                    pc.addMpr(cards.getAddMpr());
                    pc.addDmgup(cards.getAddDmg());
                    pc.addBowDmgup(cards.getAddBowDmg());
                    pc.addHitup(cards.getAddHit());
                    pc.addBowHitup(cards.getAddBowHit());
                    pc.addDamageReductionByArmor(cards.getAddDmgR());
                    pc.addMagicDmgReduction(cards.getAddMagicDmgR());
                    pc.addSp(cards.getAddSp());
                    pc.addMagicDmgModifier(cards.getAddMagicHit());
                    pc.addMr(cards.getAddMr());
                    pc.addFire(cards.getAddFire());
                    pc.addWater(cards.getAddWater());
                    pc.addWind(cards.getAddWind());
                    pc.addEarth(cards.getAddEarth());
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
            for (int i = 0; i <= ACardTable_doll.get().ACardSize(); ++i) {
                final ACard_doll card2 = ACardTable_doll.get().getCard(i);
                if (card2 != null && pc.getQuest().get_step(card2.getQuestId()) != 0) {
                    pc.addStr(card2.getAddStr());
                    pc.addDex(card2.getAddDex());
                    pc.addCon(card2.getAddCon());
                    pc.addInt(card2.getAddInt());
                    pc.addWis(card2.getAddWis());
                    pc.addCha(card2.getAddCha());
                    pc.addAc(card2.getAddAc());
                    pc.addMaxHp(card2.getAddHp());
                    pc.addMaxMp(card2.getAddMp());
                    pc.addHpr(card2.getAddHpr());
                    pc.addMpr(card2.getAddMpr());
                    pc.addDmgup(card2.getAddDmg());
                    pc.addBowDmgup(card2.getAddBowDmg());
                    pc.addHitup(card2.getAddHit());
                    pc.addBowHitup(card2.getAddBowHit());
                    pc.addDamageReductionByArmor(card2.getAddDmgR());
                    pc.addMagicDmgReduction(card2.getAddMagicDmgR());
                    pc.addSp(card2.getAddSp());
                    pc.addMagicDmgModifier(card2.getAddMagicHit());
                    pc.addMr(card2.getAddMr());
                    pc.addFire(card2.getAddFire());
                    pc.addWater(card2.getAddWater());
                    pc.addWind(card2.getAddWind());
                    pc.addEarth(card2.getAddEarth());
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_SPMR(pc));
                    pc.sendPackets(new S_SystemMessage(card2.getMsg1()));
                }
            }
            for (int i = 0; i <= CardSetTable_doll.get().CardCardSize(); ++i) {
                final CardPolySet_doll cards2 = CardSetTable_doll.get().getCard(i);
                if (cards2 != null && pc.getQuest().get_step(cards2.getQuestId()) != 0) {
                    pc.addStr(cards2.getAddStr());
                    pc.addDex(cards2.getAddDex());
                    pc.addCon(cards2.getAddCon());
                    pc.addInt(cards2.getAddInt());
                    pc.addWis(cards2.getAddWis());
                    pc.addCha(cards2.getAddCha());
                    pc.addAc(cards2.getAddAc());
                    pc.addMaxHp(cards2.getAddHp());
                    pc.addMaxMp(cards2.getAddMp());
                    pc.addHpr(cards2.getAddHpr());
                    pc.addMpr(cards2.getAddMpr());
                    pc.addDmgup(cards2.getAddDmg());
                    pc.addBowDmgup(cards2.getAddBowDmg());
                    pc.addHitup(cards2.getAddHit());
                    pc.addBowHitup(cards2.getAddBowHit());
                    pc.addDamageReductionByArmor(cards2.getAddDmgR());
                    pc.addMagicDmgReduction(cards2.getAddMagicDmgR());
                    pc.addSp(cards2.getAddSp());
                    pc.addMagicDmgModifier(cards2.getAddMagicHit());
                    pc.addMr(cards2.getAddMr());
                    pc.addFire(cards2.getAddFire());
                    pc.addWater(cards2.getAddWater());
                    pc.addWind(cards2.getAddWind());
                    pc.addEarth(cards2.getAddEarth());
                    pc.sendPackets(new S_OwnCharStatus(pc));
                    pc.sendPackets(new S_SPMR(pc));
                }
            }
        }
        catch (Exception ex) {
            return;
        }
        finally {
            this.over();
        }
        this.over();
    }
    
    public static void CheckItemPower(final L1PcInstance pc) {
        for (final L1ItemInstance item : pc.getInventory().getItems()) {
            if (CheckItemPowerTable.get().checkItem(item.getItemId())) {
                CheckItemPowerTable.get().givepower(pc, item.getItemId());
            }
        }
    }
    
//	private int CheckMail(L1PcInstance pc) {
//	  int count = 0;
//	  Connection con = null;
//	  PreparedStatement pstm1 = null;
//	  ResultSet rs = null;
//	  try {
//	    con = DatabaseFactory.get().getConnection();
//	    pstm1 = con
//	      .prepareStatement(" SELECT count(*) as cnt FROM mails where receiver =? AND read_status = 0");
//	    pstm1.setString(1, pc.getName());
//	    
//	    rs = pstm1.executeQuery();
//	    if (rs.next()) {
//	      count = rs.getInt("cnt");
//	    }
//	  }
//	  catch (SQLException e) {
//	    _log.error(e.getLocalizedMessage(), e);
//	  } finally {
//	    SQLUtil.close(rs);
//	    SQLUtil.close(pstm1);
//	    SQLUtil.close(con);
//	  } 
//	  
//	  return count;
//	}
    
    private void crown(final L1PcInstance pc) {
        try {
            final Map<Integer, L1Clan> map = L1CastleLocation.mapCastle();
            for (final Integer key : map.keySet()) {
                final L1Clan clan = map.get(key);
                if (clan != null) {
                    if (key.equals(2)) {
                        pc.sendPackets(new S_CastleMaster(8, clan.getLeaderId()));
                    }
                    else {
                        pc.sendPackets(new S_CastleMaster(key, clan.getLeaderId()));
                    }
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void getFocus(final L1PcInstance pc) {
        try {
            pc.set_showId(-1);
            World.get().addVisibleObject(pc);
            if (pc.getMeteLevel() > 0) {
                pc.resetMeteAbility();
            }
            pc.sendPackets(new S_PacketBox(132, pc.getEr()));
            pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
            pc.sendPackets(new S_OwnCharStatus(pc));
            pc.sendPackets(new S_MapID(pc, pc.getMapId(), pc.getMap().isUnderwater()));
            pc.sendPackets(new S_OwnCharPack(pc));
            final ArrayList<L1PcInstance> otherPc = World.get().getVisiblePlayer(pc);
            if (otherPc.size() > 0) {
                for (final L1PcInstance tg : otherPc) {
                    tg.sendPackets(new S_OtherCharPacks(pc));
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void getUpdate(final L1PcInstance pc) {
//	  pc.sendPackets((ServerBasePacket)new S_Mail(pc, 0));
//	  pc.sendPackets((ServerBasePacket)new S_Mail(pc, 1));
//	  pc.sendPackets((ServerBasePacket)new S_Mail(pc, 2));
        pc.sendPackets(new S_SPMR(pc));
        pc.sendPackets(new S_Karma(pc));
        pc.sendPackets(new S_Weather(World.get().getWeather()));
    }
    
    private void marriage(final L1PcInstance pc) {
        try {
            if (pc.getPartnerId() != 0) {
                final L1PcInstance partner = (L1PcInstance)World.get().findObject(pc.getPartnerId());
                if (partner != null && partner.getPartnerId() != 0 && pc.getPartnerId() == partner.getId() && partner.getPartnerId() == pc.getId()) {
                    pc.sendPackets(new S_ServerMessage(548));
                    partner.sendPackets(new S_ServerMessage(549));
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void getOther(final L1PcInstance pc) throws Exception {
        try {
            pc.set_otherList(new L1PcOtherList(pc));
            pc.addDmgup(pc.get_other().get_Attack());
            pc.addBowDmgup(pc.get_other().get_BowAttack());
            pc.addHitup(pc.get_other().get_Hit());
            pc.addBowHitup(pc.get_other().get_BowHit());
            pc.addSp(pc.get_other().get_Sp());
            pc.addStr(pc.get_other().get_Str());
            pc.addDex(pc.get_other().get_Dex());
            pc.addInt(pc.get_other().get_Int());
            pc.addCon(pc.get_other().get_Con());
            pc.addCha(pc.get_other().get_Cha());
            pc.addWis(pc.get_other().get_Wis());
            pc.addMaxHp(pc.get_other().get_Hp());
            pc.addMaxMp(pc.get_other().get_Mp());
            pc.addMr(pc.get_other().get_Mr());
            pc.addother_ReductionDmg(pc.get_other().get_ReductionDmg());
            pc.addHpr(pc.get_other().get_Hpr());
            pc.addMpr(pc.get_other().get_Mpr());
            pc.add_hppotion(pc.get_other().get_hppotion());
            pc.add_exp(pc.get_other().get_exp());
            pc.addAc(pc.get_other().get_ac());
            pc.addWeightReduction(pc.get_other().get_weight());
            pc.addRegistStun(pc.get_other().get_regist_stun());
            pc.addRegistStone(pc.get_other().get_regist_stone());
            pc.addRegistSleep(pc.get_other().get_regist_sleep());
            pc.add_regist_freeze(pc.get_other().get_regist_freeze());
            pc.addRegistSustain(pc.get_other().get_regist_sustain());
            pc.addRegistBlind(pc.get_other().get_regist_blind());
            pc.addDmgup(pc.get_other2().get_Attack());
            pc.addBowDmgup(pc.get_other2().get_BowAttack());
            pc.addHitup(pc.get_other2().get_Hit());
            pc.addBowHitup(pc.get_other2().get_BowHit());
            pc.addSp(pc.get_other2().get_Sp());
            pc.addStr(pc.get_other2().get_Str());
            pc.addDex(pc.get_other2().get_Dex());
            pc.addInt(pc.get_other2().get_Int());
            pc.addCon(pc.get_other2().get_Con());
            pc.addCha(pc.get_other2().get_Cha());
            pc.addWis(pc.get_other2().get_Wis());
            pc.addMaxHp(pc.get_other2().get_Hp());
            pc.addMaxMp(pc.get_other2().get_Mp());
            pc.addMr(pc.get_other2().get_Mr());
            pc.addother_ReductionDmg(pc.get_other2().get_ReductionDmg());
            pc.addHpr(pc.get_other2().get_Hpr());
            pc.addMpr(pc.get_other2().get_Mpr());
            pc.add_hppotion(pc.get_other2().get_hppotion());
            pc.add_exp(pc.get_other2().get_exp());
            pc.addAc(pc.get_other2().get_ac());
            pc.addWeightReduction(pc.get_other2().get_weight());
            pc.addRegistStun(pc.get_other2().get_regist_stun());
            pc.addRegistStone(pc.get_other2().get_regist_stone());
            pc.addRegistSleep(pc.get_other2().get_regist_sleep());
            pc.add_regist_freeze(pc.get_other2().get_regist_freeze());
            pc.addRegistSustain(pc.get_other2().get_regist_sustain());
            pc.addRegistBlind(pc.get_other2().get_regist_blind());
            pc.add_pvp(pc.get_other2().get_pvp());
            pc.add_bowpvp(pc.get_other2().get_bowpvp());
            if (pc.get_other1().get_type1() != 0) {
                pc.setAu_Shop(true);
            }
            if (pc.get_other1().get_type20() != 0) {
                pc.setAu_AutoSkill(2, pc.get_other1().get_type20());
            }
            if (pc.get_other1().get_type21() != 0) {
                pc.setAu_AutoSkill(3, pc.get_other1().get_type21());
            }
            if (pc.get_other1().get_type22() != 0) {
                pc.setAu_AutoSkill(4, pc.get_other1().get_type22());
            }
            if (pc.get_other1().get_type23() != 0) {
                pc.setAu_AutoSkill(5, pc.get_other1().get_type23());
            }
            if (pc.get_other1().get_type24() != 0) {
                pc.setAu_AutoSkill(6, pc.get_other1().get_type24());
            }
            if (pc.get_other1().get_type25() != 0) {
                pc.setAu_AutoSkill(7, pc.get_other1().get_type25());
            }
            if (pc.get_other1().get_type26() != 0) {
                pc.setAu_AutoSkill(8, 1);
            }
            if (pc.get_other1().get_type27() != 0) {
                pc.setsummon_skillid(pc.get_other1().get_type27());
            }
            if (pc.get_other1().get_type28() != 0) {
                pc.setsummon_skillidmp_1(pc.get_other1().get_type28());
            }
            if (pc.get_other1().get_type30() != null) {
                pc.setSummon_npcid(pc.get_other1().get_type30());
            }
            if (pc.get_other1().get_type41() != 0) {
                pc.setAu_AutoSet(1, pc.get_other1().get_type41());
            }
            if (pc.get_other1().get_type42() != 0) {
                pc.setAu_AutoSet(0, 1);
            }
            if (pc.get_other1().get_type43() != 0) {
                pc.setAu_AutoSet(3, pc.get_other1().get_type43());
            }
            if (pc.get_other1().get_type44() != 0) {
                pc.setAu_AutoSet(5, pc.get_other1().get_type44());
            }
            if (pc.get_other1().get_type45() != 0) {
                pc.setAu_AutoSet(4, pc.get_other1().get_type45());
            }
            if (pc.get_other1().get_type46() != 0) {
                pc.setAu_OtherSet(1, 1);
            }
            if (pc.get_other1().get_type47() != 0) {
                pc.setAu_OtherSet(2, 1);
            }
            if (pc.get_other1().get_type48() != 0) {
                pc.setAu_OtherSet(3, 1);
            }
            if (pc.get_other1().get_type49() != 0) {
                pc.setAu_OtherSet(4, 1);
            }
            if (pc.get_other1().get_type50() != 0) {
                pc.setAu_OtherSet(5, 1);
            }
            if (pc.get_other1().get_type2() != 0 && pc.get_other1().get_type3() != 0) {
                pc.setAu_BuyItemSwitch(0, 1);
                pc.setAu_BuyItemCount(0, pc.get_other1().get_type3());
            }
            if (pc.get_other1().get_type4() != 0 && pc.get_other1().get_type5() != 0) {
                pc.setAu_BuyItemSwitch(1, 1);
                pc.setAu_BuyItemCount(1, pc.get_other1().get_type5());
            }
            if (pc.get_other1().get_type6() != 0 && pc.get_other1().get_type7() != 0) {
                pc.setAu_BuyItemSwitch(2, 1);
                pc.setAu_BuyItemCount(2, pc.get_other1().get_type7());
            }
            if (pc.get_other1().get_type8() != 0 && pc.get_other1().get_type9() != 0) {
                pc.setAu_BuyItemSwitch(3, 1);
                pc.setAu_BuyItemCount(3, pc.get_other1().get_type9());
            }
            if (pc.get_other1().get_type10() != 0 && pc.get_other1().get_type11() != 0) {
                pc.setAu_BuyItemSwitch(4, 1);
                pc.setAu_BuyItemCount(4, pc.get_other1().get_type11());
            }
            if (pc.get_other1().get_type12() != 0 && pc.get_other1().get_type13() != 0) {
                pc.setAu_BuyItemSwitch(5, 1);
                pc.setAu_BuyItemCount(5, pc.get_other1().get_type13());
            }
            if (pc.get_other1().get_type14() != 0 && pc.get_other1().get_type15() != 0) {
                pc.setAu_BuyItemSwitch(6, 1);
                pc.setAu_BuyItemCount(6, pc.get_other1().get_type15());
            }
            if (pc.get_other1().get_type16() != 0 && pc.get_other1().get_type17() != 0) {
                pc.setAu_BuyItemSwitch(7, 1);
                pc.setAu_BuyItemCount(7, pc.get_other1().get_type17());
            }
            if (pc.get_other1().get_type18() != 0 && pc.get_other1().get_type19() != 0) {
                pc.setAu_BuyItemSwitch(8, 1);
                pc.setAu_BuyItemCount(8, pc.get_other1().get_type19());
            }
            if (pc.get_other3().get_type2() != 0) {
                pc.setSave_Quest_Map1(pc.get_other3().get_type2());
            }
            if (pc.get_other3().get_type3() != 0) {
                pc.setSave_Quest_Map2(pc.get_other3().get_type3());
            }
            if (pc.get_other3().get_type4() != 0) {
                pc.setSave_Quest_Map3(pc.get_other3().get_type4());
            }
            if (pc.get_other3().get_type5() != 0) {
                pc.setSave_Quest_Map4(pc.get_other3().get_type5());
            }
            if (pc.get_other3().get_type6() != 0) {
                pc.setSave_Quest_Map5(pc.get_other3().get_type6());
            }
            OnlineGiftSet.add(pc);
            final int time = pc.get_other().get_usemapTime();
            if (time > 0) {
                ServerUseMapTimer.put(pc, time);
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void war(final L1PcInstance pc) {
        try {
            if (pc.getClanid() != 0) {
                final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    if (pc.getClanid() == clan.getClanId() && pc.getClanname().toLowerCase().equals(clan.getClanName().toLowerCase())) {
                        final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                        final L1PcInstance[] arrayOfL1PcInstance1;
                        final int i = (arrayOfL1PcInstance1 = clanMembers).length;
                        for (byte b = 0; b < i; ++b) {
                            final L1PcInstance clanMember = arrayOfL1PcInstance1[b];
                            if (clanMember.getId() != pc.getId()) {
                                clanMember.sendPackets(new S_ServerMessage(843, pc.getName()));
                            }
                        }
                        final int clanMan = clan.getOnlineClanMember().length;
                        pc.sendPackets(new S_ServerMessage("\\fU線上血盟成員:" + clanMan));
                        if (clan.getAnnouncement() != null) {
                            pc.sendPackets(new S_SystemMessage("血盟公告:" + clan.getAnnouncement()));
                        }
                        clan.CheckClan_Exp20(null);
                        if (clan.isClanskill()) {
                            switch (pc.get_other().get_clanskill()) {
                                case 1: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[0]));
                                    break;
                                }
                                case 2: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[1]));
                                    break;
                                }
                                case 4: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[2]));
                                    break;
                                }
                                case 8: {
                                    pc.sendPackets(new S_ServerMessage(Npc_clan.SKILLINFO[3]));
                                    break;
                                }
                            }
                        }
                        ClanSkillDBSet.add(pc);
                        final L1EmblemIcon emblemIcon = ClanEmblemReading.get().get(clan.getClanId());
                        if (emblemIcon != null) {
                            pc.sendPackets(new S_Emblem(emblemIcon));
                        }
                        for (final L1War war : WorldWar.get().getWarList()) {
                            final boolean ret = war.checkClanInWar(pc.getClanname());
                            if (ret) {
                                final String enemy_clan_name = war.getEnemyClanName(pc.getClanname());
                                if (enemy_clan_name != null) {
                                    pc.sendPackets(new S_War(8, pc.getClanname(), enemy_clan_name));
                                    break;
                                }
                                break;
                            }
                        }
                    }
                }
                else {
                    pc.setClanid(0);
                    pc.setClanname("");
                    pc.setClanRank(0);
                    pc.save();
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void backRestart(final L1PcInstance pc) {
        try {
        	int[] gmloc = null;
        	if (pc.isGm() && ConfigOther.GM_LOC != null) {// GM返回休息區
        		gmloc = ConfigOther.GM_LOC;
        		pc.setX(gmloc[0]);
        		pc.setY(gmloc[1]);
        		pc.setMap((short)gmloc[2]);
        		return;
        	}
            if (pc.getMapId() >= 4001 && pc.getMapId() <= 4050) {
                pc.setX(33448);
                pc.setY(32791);
                pc.setMap((short)4);
            }
            final L1GetBackRestart gbr = GetBackRestartTable.get().getGetBackRestart(pc.getMapId());
            if (gbr != null) {
                pc.setX(gbr.getLocX());
                pc.setY(gbr.getLocY());
                pc.setMap(gbr.getMapId());
            }
            final int castle_id = L1CastleLocation.getCastleIdByArea(pc);
            if (castle_id > 0 && ServerWarExecutor.get().isNowWar(castle_id)) {
                final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
                if (clan != null) {
                    if (clan.getCastleId() != castle_id) {
                        int[] loc = new int[3];
                        loc = L1CastleLocation.getGetBackLoc(castle_id);
                        pc.setX(loc[0]);
                        pc.setY(loc[1]);
                        pc.setMap((short)loc[2]);
                    }
                }
                else {
                    int[] loc = new int[3];
                    loc = L1CastleLocation.getGetBackLoc(castle_id);
                    pc.setX(loc[0]);
                    pc.setY(loc[1]);
                    pc.setMap((short)loc[2]);
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void items(final L1PcInstance pc) {
        try {
            CharacterTable.restoreInventory(pc);
            final List<L1ItemInstance> items = pc.getInventory().getItems();
            if (items.size() > 0) {
                pc.sendPackets(new S_InvList(pc.getInventory().getItems()));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void bookmarks(final L1PcInstance pc) {
        try {
            final ArrayList<L1BookMark> bookList = CharBookReading.get().getBookMarks(pc);
            if (bookList != null) {
                final L1BookConfig config = CharBookConfigReading.get().get(pc.getId());
                final int maxSize = ConfigAlt.CHAR_BOOK_INIT_COUNT + ((config != null) ? config.getMaxSize() : 0);
                pc.sendPackets(new S_Bookmarks((byte[])((config != null) ? config.getData() : null), maxSize, bookList));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void skills(final L1PcInstance pc) {
        try {
            final ArrayList<L1UserSkillTmp> skillList = CharSkillReading.get().skills(pc.getId());
            final int[] skills = new int[28];
            if (skillList != null && skillList.size() > 0) {
                for (final L1UserSkillTmp userSkillTmp : skillList) {
                    final L1Skills skill = SkillsTable.get().getTemplate(userSkillTmp.get_skill_id());
                    final int[] array = skills;
                    final int n = skill.getSkillLevel() - 1;
                    array[n] += skill.getId();
                }
                pc.sendPackets(new S_AddSkill(pc, skills));
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void serchSummon(final L1PcInstance pc) {
        try {
            final Collection<L1SummonInstance> summons = WorldSummons.get().all();
            if (summons.size() > 0) {
                for (final L1SummonInstance summon : summons) {
                    if (summon.getMaster().getId() == pc.getId()) {
                        summon.setMaster(pc);
                        pc.addPet(summon);
                        final S_NewMaster packet = new S_NewMaster(pc.getName(), summon);
                        for (final L1PcInstance visiblePc : World.get().getVisiblePlayer(summon)) {
                            visiblePc.sendPackets(packet);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void buff(final L1PcInstance pc) {
        try {
            CharBuffReading.get().buff(pc);
            pc.sendPackets(new S_PacketBoxActiveSpells(pc));
            CharMapTimeReading.get().getTime(pc);
        }
        catch (Exception e) {
            C_LoginToServer._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public static void checkforProtector(final L1PcInstance pc) {
        for (final L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == ProtectorSet.ITEM_ID && !pc.isProtector()) {
                pc.setProtector(true);
                break;
            }
        }
    }
    
    public static void checkforDADISStone(final L1PcInstance pc) {
        for (final L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == 56147) {
                if (!pc.isEffectDADIS()) {
                    pc.setDADIS(true);
                    break;
                }
                continue;
            }
            else if (item.getItemId() == 56148) {
                if (!pc.isEffectGS() && !pc.isEffectDADIS()) {
                    pc.setGS(true);
                    break;
                }
                continue;
            }
            else {
                if (item.getItemId() == 56148 && !pc.isEffectGS() && !pc.isEffectDADIS()) {
                    pc.setGS(true);
                    break;
                }
                continue;
            }
        }
    }
    
    public static void checkforSouls(final L1PcInstance pc) {
        for (final L1ItemInstance item : pc.getInventory().getItems()) {
            if (item.getItemId() == 44216) {
                if (!pc._isCraftsmanHeirloom() && !pc._isMarsSoul()) {
                    pc.setCraftsmanHeirloom(true);
                    break;
                }
                continue;
            }
            else {
                if (item.getItemId() == 44217 && !pc._isMarsSoul() && !pc._isCraftsmanHeirloom()) {
                    pc.setMarsSoul(true);
                    break;
                }
                continue;
            }
        }
    }
    
    private void ClanMatching(final L1PcInstance pc) {
        final L1ClanMatching cml = L1ClanMatching.getInstance();
        if (pc.getClanid() == 0) {
            if (!pc.isCrown()) {
                if (!cml.getMatchingList().isEmpty()) {
                    pc.sendPackets(new S_ServerMessage(3245));
                }
            }
            else {
                pc.sendPackets(new S_ServerMessage(3247));
            }
        }
        else {
            switch (pc.getClanRank()) {
                case 3:
                case 4:
                case 6:
                case 9:
                case 10: {
                    if (!pc.getInviteList().isEmpty()) {
                        pc.sendPackets(new S_ServerMessage(3246));
                        break;
                    }
                    break;
                }
            }
            pc.sendPackets(new S_PacketBox(173, pc.getClan().getEmblemStatus()));
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
