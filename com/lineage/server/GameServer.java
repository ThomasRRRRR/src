package com.lineage.server;

import com.add.ItemTimeTableadd;
import com.add.L1DayPresentTimer;
import com.add.L1SystemMessageTable;
import com.add.NewAutoPractice;
import com.add.system.ACardTable;
import com.add.system.ACardTable_doll;
import com.add.system.CardSetTable;
import com.add.system.CardSetTable_doll;
import com.add.system.L1BlendTable;
import com.add.system.L1BlendTable_1;
import com.add.system.L1FireSmithCrystalTable;
import com.eric.RandomMobTable;
import com.eric.StartCheckWarTime;
import com.lineage.DatabaseFactory;
import com.lineage.acip;
import com.lineage.config.*;
import com.lineage.data.event.ClanSkillDBSet;
import com.lineage.data.event.NowTimeSpawn;
import com.lineage.data.event.RandomGiftSet;
import com.lineage.echo.PacketHandler;
import com.lineage.list.BadNamesList;
import com.lineage.list.L1Karma_Pc;
import com.lineage.server.CheckTimeController;
import com.lineage.server.CmdEcho;
import com.lineage.server.EchoServerTimer;
import com.lineage.server.IdFactory;
import com.lineage.server.Manly.WenYangTable;
import com.lineage.server.Shutdown;
import com.lineage.server.datatables.*;
import com.lineage.server.datatables.lock.*;
import com.lineage.server.datatables.sql.CharacterQuestTable;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.datatables.sql.L1MonTable;
//import com.lineage.server.datatables.sql.MailTable;
import com.lineage.server.datatables.sql.SpeedTable;
import com.lineage.server.model.Instance.L1DoorInstance;
import com.lineage.server.model.Instance.L1ItemPower;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1ClanMatching;
import com.lineage.server.model.gametime.L1GameTimeClock;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.model.skill.L1SkillMode;
import com.lineage.server.templates.L1MapTile;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.thread.DeAiThreadPool;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.thread.NpcAiThreadPool;
import com.lineage.server.thread.PcAutoThreadPool;
import com.lineage.server.thread.PcAutoThreadPoolN;
import com.lineage.server.thread.PcOtherThreadPool;
import com.lineage.server.thread.DeathThreadPool;
import com.lineage.server.thread.NpcBownTheadPool;
import com.lineage.server.thread.NpcDeadTimerPool;
import com.lineage.server.thread.NpcDeathTheadPool;
import com.lineage.server.thread.ServerGcTimePool;
import com.lineage.server.timecontroller.Special;
import com.lineage.server.timecontroller.StartTimer_Npc;
import com.lineage.server.timecontroller.StartTimer_Pc;
import com.lineage.server.timecontroller.StartTimer_Pet;
import com.lineage.server.timecontroller.StartTimer_Server;
import com.lineage.server.timecontroller.StartTimer_Skill;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimer;
import com.lineage.server.timecontroller.event.ranking.RankingHeroTimerlv;
import com.lineage.server.utils.DBClearAllUtil;
import com.lineage.server.utils.DBClearitemb;
import com.lineage.server.utils.PerformanceTimer;
import com.lineage.server.utils.SQLUtil;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldCrown;
import com.lineage.server.world.WorldDarkelf;
import com.lineage.server.world.WorldDe;
import com.lineage.server.world.WorldDragonKnight;
import com.lineage.server.world.WorldElf;
import com.lineage.server.world.WorldIllusionist;
import com.lineage.server.world.WorldKnight;
import com.lineage.server.world.WorldPet;
import com.lineage.server.world.WorldSummons;
import com.lineage.server.world.WorldWizard;
import com.lineage.william.ClanOriginal;
import com.lineage.william.EnchantAccessory;
import com.lineage.william.EnchantOrginal;
import com.lineage.william.ExcavateTable;
import com.lineage.william.GfxIdOrginal;
import com.lineage.william.ItemIntegration;
import com.lineage.william.NpcTalk10;
import com.lineage.william.WilliamBuff;
import com.lineage.william.WilliamItemMessage;
import com.lineage.william.William_Online_Reward;
import com.lineage.william.new_npc_talk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GameServer {
    private static final Log _log = LogFactory.getLog(GameServer.class);
    private static GameServer _instance;

    public static GameServer getInstance() {
        if (_instance == null) {
            _instance = new GameServer();
        }
        return _instance;
    }

    public void initialize() throws Exception {
        PerformanceTimer timer = new PerformanceTimer();
        try {
            try {
            	
            	_log.info(
            	        "\n\r--------------------------------------------------" +
            	        "\n\r       外部設置：經驗倍率: " + ConfigRate.RATE_XP + 
            	        "\n\r       外部設置：正義質倍率: " + ConfigRate.RATE_LA + 
            	        "\n\r       外部設置：友好度倍率: " + ConfigRate.RATE_KARMA + 
            	        "\n\r       外部設置：物品掉落倍率: " + ConfigRate.RATE_DROP_ITEMS + 
            	        "\n\r       外部設置：金幣掉落倍率: " + ConfigRate.RATE_DROP_ADENA + 
            	        "\n\r       外部設置：廣播等級限制: " + ConfigAlt.GLOBAL_CHAT_LEVEL + 
            	        "\n\r       外部設置：PK設置: " + (ConfigAlt.ALT_NONPVP ? "允許" : "不允許") + 
            	        "\n\r       外部設置：最大連線設置: " + Config.MAX_ONLINE_USERS + 
            	        "\n\r--------------------------------------------------");
            	
            	acip.getItemip();//驗證器
            	L1Karma_Pc.main_check(1L);//抓取網路時間
                
                DBClearitemb.start();
                PacketHandler.load();
                ServerReading.get().load();
                IdFactory.get().load();
                CharObjidTable.get().load();
                AccountReading.get().load();
                GeneralThreadPool.get();
                PcOtherThreadPool.get();
                NpcAiThreadPool.get();
                DeAiThreadPool.get();
                
                DeathThreadPool.get();// 線程工廠設置
                NpcDeadTimerPool.get();
                NpcDeathTheadPool.get();
                NpcBownTheadPool.get();
                ServerGcTimePool.get();// 線程工廠設置
                PcAutoThreadPool.get();
                PcAutoThreadPoolN.get();
                SpeedTable.get().load();
                
                L1SystemMessageTable.get().loadSystemMessage();
                ExpTable.get().load();
                SprTable.get().load();
                MapsTable.get().load();
                MapLevelTable.get().load();
                ItemTimeTable.get().load();
                L1WorldMap.get().load();
                L1GameTimeClock.init();
                NpcTable.get().load();
                NpcScoreTable.get().load();
                CharacterTable.loadAllCharName();
                CharacterTable.clearOnlineStatus();
                CharacterTable.clearSpeedError();
                World.get();
                WorldCrown.get();
                WorldDe.get();
                WorldKnight.get();
                WorldElf.get();
                WorldWizard.get();
                WorldDarkelf.get();
                WorldDragonKnight.get();
                WorldIllusionist.get();
                WorldPet.get();
                WorldSummons.get();
                TrapTable.get().load();
                TrapsSpawn.get().load();
                ItemTable.get().load();
                DropTable.get().load();
                DropMapTable.get().load();
                DropItemTable.get().load();
                DropItemEnchantTable.get().load();
                SkillsTable.get().load();
                SkillsItemTable.get().load();
                MobGroupTable.get().load();
                NPCTalkDataTable.get().load();
                NpcActionTable.load();
                SpawnTable.get().load();
                PolyTable.get().load();
                ShopTable.get().load();
                ShopCnTable.get().load();
                DungeonTable.get().load();
                DungeonRTable.get().load();
                NpcSpawnTable.get().load();
                DwarfForClanReading.get().load();
                ClanReading.get().load();
                ClanEmblemReading.get().load();
                ClanAllianceReading.get().load();
                L1ClanMatching.getInstance().loadClanMatching();
                if (ClanSkillDBSet.START) {
                    ClanReading.get().load();
                }
                RandomMobTable.getInstance().startRandomMob();
                StartCheckWarTime.getInstance();
                if (NowTimeSpawn.START) {
                    NowTimeSpawn.get();
                }
                Special.getStart();
                CastleReading.get().load();
                L1CastleLocation.setCastleTaxRate();
                GetBackRestartTable.get().load();
                DoorSpawnTable.get().load();
                WeaponSkillTable.get().load();
                WeaponSkillPowerTable.get().load();
                GetbackTable.loadGetBack();
                PetTypeTable.load();
                PetItemTable.get().load();
                ItemBoxTable.get().load();
                ResolventTable.get().load();
                NpcTeleportTable.get().load();
                NpcChatTable.get().load();
                ArmorSetTable.get().load();
                ItemTeleportTable.get().load();
                ItemPowerUpdateTable.get().load();
                CommandsTable.get().load();
                BeginnerTable.get().load();
                ItemRestrictionsTable.get().load();
                ItemdropTable.get().load();
                ItemdropdeadTable.get().load();
                Itemdeaddrop.get().load();
                SpawnBossReading.get().load();
                Skill_type.get();
                Skill_type.load();
                Skill_type1.get().load();
                HouseReading.get().load();
                IpReading.get().load();
                TownReading.get().load();
//                MailTable.get().load();
                AuctionBoardReading.get().load();
                BoardReading.get().load();
                CharBuffReading.get().load();
                CharSkillReading.get().load();
                CharacterConfigReading.get().load();
                BuddyReading.get().load();
                CharBookReading.get().load();
                CharBookConfigReading.get().load();
                CharOtherReading.get().load();
                CharOtherReading1.get().load();
                CharOtherReading2.get().load();
                CharOtherReading3.get().load();
                QuizSetTable.getInstance().updateAllPcQuizSet();
                if (ConfigOther.monsec == 0) {
                    this.cleanMissionStatus();
                    _log.info(">>>>>>>>(每次重啟)角色任務媽祖欄位清理完畢。");
                } else if (ConfigOther.monsec > 0 && Calendar.getInstance().get(11) == ConfigOther.monsec) {
                    this.cleanMissionStatus();
                    _log.info(">>>>>>>>(時間重啟)角色任務媽祖欄位清理完畢。");
                }
                if (ConfigClan.clanskill) {
                    this.clanaddskill();
                    this.clanaddskill1();
                    this.clanaddskill2();
                    this.clanaddskill3();
                    _log.info(">>>>>>>>(每次重啟)角色任務血盟貢獻清理完畢。");
                } else if (Calendar.getInstance().get(11) == ConfigClan.clanresttime) {
                    this.clanaddskill();
                    this.clanaddskill1();
                    this.clanaddskill2();
                    this.clanaddskill3();
                    _log.info(">>>>>>>>(時間重啟)角色任務血盟貢獻清理完畢。");
                }
                if (ConfigOther.restday == 0) {
                    this.day();
                    _log.info(">>>>>>>>(每次重啟)角色每日簽到欄位清理完畢。");
                } else if (ConfigOther.restday > 0 && Calendar.getInstance().get(11) == ConfigOther.restday) {
                    this.day();
                    _log.info(">>>>>>>>(時間重啟)角色每日簽到欄位清理完畢。");
                }
                if (ConfigOther.shopitemrest == 0) {
                    for (int questid : ShopTable._DailyItem.keySet()) {
                        GameServer.deleteData(questid);
                    }
                    for (int questid : ShopCnTable._DailyCnItem.keySet()) {
                        GameServer.deleteData(questid);
                    }
                } else if (ConfigOther.shopitemrest > 0 && Calendar.getInstance().get(11) == ConfigOther.shopitemrest) {
                    for (int questid : ShopTable._DailyItem.keySet()) {
                        GameServer.deleteData(questid);
                    }
                    for (int questid : ShopCnTable._DailyCnItem.keySet()) {
                        GameServer.deleteData(questid);
                    }
                }
                NewAutoPractice.get().load();
                NewAutoPractice.get().load2();
                NewAutoPractice.get().load3();
                if (ConfigQuest.Time > 0 && Calendar.getInstance().get(11) == ConfigQuest.Time) {
                    this.chcekQuest();
                } else if (ConfigQuest.Time == 0) {
                    this.chcekQuest();
                }
                if (ConfigQuest.QuestMap_Time > 0 && Calendar.getInstance().get(11) == ConfigQuest.QuestMap_Time) {
                    DBClearAllUtil.DBClearQuesemap.start();
                    this.map_chcekmap_Quest();
                } else if (ConfigQuest.QuestMap_Time == 0) {
                    DBClearAllUtil.DBClearQuesemap.start();
                    this.map_chcekmap_Quest();
                }
                if (ConfigItem.bd_Time > 0 && Calendar.getInstance().get(11) == ConfigItem.bd_Time) {
                    DBClearAllUtil.DBClearQuesemap.start();
                    this.chcekitemid();
                } else if (ConfigItem.bd_Time == 0) {
                    this.chcekitemid();
                }
                CharacterQuestReading.get().load();
                BadNamesList.get().load();
                SceneryTable.get().load();
                L1SkillMode.get().load();
                L1AttackList.load();
                L1ItemPower.load();
                L1PcInstance.load();
                CharItemsReading.get().load();
                DwarfReading.get().load();
                DwarfForElfReading.get().load();
                DollPowerTable.get().load();
                PetReading.get().load();
                CharItemsTimeReading.get().load();
                L1PcOther.load();
                EventTable.get().load();
                if (EventTable.get().size() > 0) {
                    EventSpawnTable.get().load();
                }
                QuestMapTable.get().load();
                FurnitureSpawnReading.get().load();
                ItemMsgTable.get().load();
                WeaponPowerTable.get().load();
                FishingTable.get().load();
                ExtraMeteAbilityTable.getInstance().load();
                Acc_use_Item.get();
                Char_use_Item.get();
                ExtraAttrWeaponTable.getInstance().load();
                List<L1MapTile> mapTile = MapTileTable.get().getList();
                if (!mapTile.isEmpty()) {
                    for (L1MapTile tgMap : mapTile) {
                        L1WorldMap.get().getMap((short)tgMap.getMapid()).setTestTile(tgMap.getX(), tgMap.getY(), tgMap.getTile());
                    }
                }
                MapCheckTable.get();
                new_npc_talk.load();
                if (ConfigOther.RankLevel) {
                    RankingHeroTimerlv.load();
                } else {
                    RankingHeroTimer.load();
                }
                L1BlendTable.getInstance().loadBlendTable();
                L1BlendTable_1.getInstance().loadBlendTable();
                EnchantOrginal.getInstance();
                EnchantAccessory.getInstance();
                RevertHpMp.get().load();
                L1FireSmithCrystalTable.get().load();
                if (ConfigAlt.QUIZ_SET_SWITCH) {
                    QuizSetTable.getInstance().load();
                }
                ItemVIPTable.get();
                ServerItemGiveTable.get();
                ItemHtmlTable.get();
                CardSetTable.get().load();
                ACardTable.get().load();
                CardSetTable_doll.get().load();
                ACardTable_doll.get().load();
                NpcHierarchTable.get();
                StartTimer_Server startTimer = new StartTimer_Server();
                startTimer.start();
                StartTimer_Pc pcTimer = new StartTimer_Pc();
                pcTimer.start();
                StartTimer_Npc npcTimer = new StartTimer_Npc();
                npcTimer.start();
                StartTimer_Pet petTimer = new StartTimer_Pet();
                petTimer.start();
                StartTimer_Skill skillTimer = new StartTimer_Skill();
                skillTimer.start();
                Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
                DeNameTable.get().load();
                DeClanTable.get().loadIcon();
                DeClanTable.get().load();
                DeTitleTable.get().load();
                DeShopChatTable.get().load();
                DeGlobalChatTable.get().load();
                DeShopItemTable.get().load();
                L1DoorInstance.openDoor();
                MapHprMprTable.get();
                WilliamItemMessage.getData();
                GfxIdOrginal.getInstance();
                NpcTalk10.load();
                ClanOriginal.getInstance();
                Explogpcpower.get().load();
                RewardArmorTable.getInstance();
                CheckTimeController.getInstance().start();
                MapsGroupTable.get().load();
                CharMapTimeReading.get().load();
                DropMobTable.get().load();
                L1MonTable.get().load();
                ItemLimitation.get();
                AutoAddSkillTable.get();
                ItemIntegration.getInstance();
                PowerItemTable.get();
                ItemSpecialAttributeTable.get().load();
                ItemSpecialAttributeCharTable.get().load();
                if (ConfigOther.onlydaypre) {
                    L1DayPresentTimer.initialize();
                }
                ItemNoENTable.get().load();
                ItemMaxENTable.get().load();
                ExcavateTable.get();
                WilliamBuff.load();
                ServerItemDropTable.get();
                ItemLimitTable.get().load();
                LaBarGameTable.get();
                LaBarGameTable1.get();
                LaBarGameTable2.get();
                ServerQuestMaPTable.get();
                ExtraMagicWeaponTable.getInstance().load();
                CharWeaponTimeReading.get().load();
                SkillsProbabilityTable.get();
                ItemUseEXTable.get();
                BlendTable.getInstance().load();
                MonsterEnhanceTable.getInstance().load();
                if (RandomGiftSet.START) {
                    William_Online_Reward.getInstance();
                }
                ItemTimeTableadd.get().load();
                WenYangTable.getInstance();//紋樣系統 By Manly
                HeChengTable.getInstance().load();//合成圖片調用系統 By Manly

                if (ConfigOther.Reset_Map) {
                    CharMapTimeReading.get().clearAllTime();
                } else if (Calendar.getInstance().get(11) == ConfigOther.Reset_Map_Time) {
                    CharMapTimeReading.get().clearAllTime();
                }
                EchoServerTimer.get().start();
            }
            catch (Exception e) {
                _log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                CmdEcho cmdEcho1 = new CmdEcho(timer.get());
                cmdEcho1.runCmd();
                return;
            }
        }
        finally {
        	
            String str = "\n\r--------------------------------------------------\n\r       [特此聲明：]\n\r       開服者一切行為皆屬個人行為\n\r       核心作者僅提供技術修改服務\n\r       並未參與任何伺服器營運管理\n\r       如任何違法行為不附連帶責任\n\r       版本作者官方LINE：@724ijgmu\n\r--------------------------------------------------";
               _log.info(str);
        	
            CmdEcho cmdEcho1 = new CmdEcho(timer.get());
            cmdEcho1.runCmd();
        }
        CmdEcho cmdEcho1 = new CmdEcho(timer.get());
        cmdEcho1.runCmd();
    }
    private void cleanMissionStatus() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 9955");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void cleanclanorg() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8599");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void day() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8991");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void map_chcekmap_Quest() {
        if (ConfigQuest.map_Quest1 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest1);
        }
        if (ConfigQuest.map_Quest2 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest2);
        }
        if (ConfigQuest.map_Quest3 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest3);
        }
        if (ConfigQuest.map_Quest4 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest4);
        }
        if (ConfigQuest.map_Quest5 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest5);
        }
        if (ConfigQuest.map_Quest6 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest6);
        }
        if (ConfigQuest.map_Quest7 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest7);
        }
        if (ConfigQuest.map_Quest8 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest8);
        }
        if (ConfigQuest.map_Quest9 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest9);
        }
        if (ConfigQuest.map_Quest10 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest10);
        }
        if (ConfigQuest.map_Quest11 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest11);
        }
        if (ConfigQuest.map_Quest12 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest12);
        }
        if (ConfigQuest.map_Quest13 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest13);
        }
        if (ConfigQuest.map_Quest14 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest14);
        }
        if (ConfigQuest.map_Quest15 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest15);
        }
        if (ConfigQuest.map_Quest16 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest16);
        }
        if (ConfigQuest.map_Quest17 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest17);
        }
        if (ConfigQuest.map_Quest18 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest18);
        }
        if (ConfigQuest.map_Quest19 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest19);
        }
        if (ConfigQuest.map_Quest20 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest20);
        }
        if (ConfigQuest.map_Quest21 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest21);
        }
        if (ConfigQuest.map_Quest22 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest22);
        }
        if (ConfigQuest.map_Quest23 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest23);
        }
        if (ConfigQuest.map_Quest24 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest24);
        }
        if (ConfigQuest.map_Quest25 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest25);
        }
        if (ConfigQuest.map_Quest26 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest26);
        }
        if (ConfigQuest.map_Quest27 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest27);
        }
        if (ConfigQuest.map_Quest28 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest28);
        }
        if (ConfigQuest.map_Quest29 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest29);
        }
        if (ConfigQuest.map_Quest30 > 0) {
            GameServer.deleteData(ConfigQuest.map_Quest30);
        }
    }

    private void chcekitemid() {
        if (ConfigItem.itemid1 > 0) {
            GameServer.deleteData99(ConfigItem.itemid1);
        }
        if (ConfigItem.itemid2 > 0) {
            GameServer.deleteData99(ConfigItem.itemid2);
        }
        if (ConfigItem.itemid3 > 0) {
            GameServer.deleteData99(ConfigItem.itemid3);
        }
        if (ConfigItem.itemid4 > 0) {
            GameServer.deleteData99(ConfigItem.itemid4);
        }
        if (ConfigItem.itemid5 > 0) {
            GameServer.deleteData99(ConfigItem.itemid5);
        }
        if (ConfigItem.itemid6 > 0) {
            GameServer.deleteData99(ConfigItem.itemid6);
        }
        if (ConfigItem.itemid7 > 0) {
            GameServer.deleteData99(ConfigItem.itemid7);
        }
        if (ConfigItem.itemid8 > 0) {
            GameServer.deleteData99(ConfigItem.itemid8);
        }
        if (ConfigItem.itemid9 > 0) {
            GameServer.deleteData99(ConfigItem.itemid9);
        }
        if (ConfigItem.itemid10 > 0) {
            GameServer.deleteData99(ConfigItem.itemid10);
        }
        if (ConfigItem.itemid11 > 0) {
            GameServer.deleteData99(ConfigItem.itemid11);
        }
        if (ConfigItem.itemid12 > 0) {
            GameServer.deleteData99(ConfigItem.itemid12);
        }
        if (ConfigItem.itemid13 > 0) {
            GameServer.deleteData99(ConfigItem.itemid13);
        }
        if (ConfigItem.itemid14 > 0) {
            GameServer.deleteData99(ConfigItem.itemid14);
        }
        if (ConfigItem.itemid15 > 0) {
            GameServer.deleteData99(ConfigItem.itemid15);
        }
        if (ConfigItem.itemid16 > 0) {
            GameServer.deleteData99(ConfigItem.itemid16);
        }
        if (ConfigItem.itemid17 > 0) {
            GameServer.deleteData99(ConfigItem.itemid17);
        }
        if (ConfigItem.itemid18 > 0) {
            GameServer.deleteData99(ConfigItem.itemid18);
        }
        if (ConfigItem.itemid19 > 0) {
            GameServer.deleteData99(ConfigItem.itemid19);
        }
        if (ConfigItem.itemid20 > 0) {
            GameServer.deleteData99(ConfigItem.itemid20);
        }
        if (ConfigItem.itemid21 > 0) {
            GameServer.deleteData99(ConfigItem.itemid21);
        }
        if (ConfigItem.itemid22 > 0) {
            GameServer.deleteData99(ConfigItem.itemid22);
        }
        if (ConfigItem.itemid23 > 0) {
            GameServer.deleteData99(ConfigItem.itemid23);
        }
        if (ConfigItem.itemid24 > 0) {
            GameServer.deleteData99(ConfigItem.itemid24);
        }
        if (ConfigItem.itemid25 > 0) {
            GameServer.deleteData99(ConfigItem.itemid25);
        }
        if (ConfigItem.itemid26 > 0) {
            GameServer.deleteData99(ConfigItem.itemid26);
        }
        if (ConfigItem.itemid27 > 0) {
            GameServer.deleteData99(ConfigItem.itemid27);
        }
        if (ConfigItem.itemid28 > 0) {
            GameServer.deleteData99(ConfigItem.itemid28);
        }
        if (ConfigItem.itemid29 > 0) {
            GameServer.deleteData99(ConfigItem.itemid29);
        }
        if (ConfigItem.itemid30 > 0) {
            GameServer.deleteData99(ConfigItem.itemid30);
        }
    }

    private void chcekQuest() {
        if (ConfigQuest.Quest1 > 0) {
            GameServer.deleteData(ConfigQuest.Quest1);
        }
        if (ConfigQuest.Quest2 > 0) {
            GameServer.deleteData(ConfigQuest.Quest2);
        }
        if (ConfigQuest.Quest3 > 0) {
            GameServer.deleteData(ConfigQuest.Quest3);
        }
        if (ConfigQuest.Quest4 > 0) {
            GameServer.deleteData(ConfigQuest.Quest4);
        }
        if (ConfigQuest.Quest5 > 0) {
            GameServer.deleteData(ConfigQuest.Quest5);
        }
        if (ConfigQuest.Quest6 > 0) {
            GameServer.deleteData(ConfigQuest.Quest6);
        }
        if (ConfigQuest.Quest7 > 0) {
            GameServer.deleteData(ConfigQuest.Quest7);
        }
        if (ConfigQuest.Quest8 > 0) {
            GameServer.deleteData(ConfigQuest.Quest8);
        }
        if (ConfigQuest.Quest9 > 0) {
            GameServer.deleteData(ConfigQuest.Quest9);
        }
        if (ConfigQuest.Quest10 > 0) {
            GameServer.deleteData(ConfigQuest.Quest10);
        }
        if (ConfigQuest.Quest11 > 0) {
            GameServer.deleteData(ConfigQuest.Quest11);
        }
        if (ConfigQuest.Quest12 > 0) {
            GameServer.deleteData(ConfigQuest.Quest12);
        }
        if (ConfigQuest.Quest13 > 0) {
            GameServer.deleteData(ConfigQuest.Quest13);
        }
        if (ConfigQuest.Quest14 > 0) {
            GameServer.deleteData(ConfigQuest.Quest14);
        }
        if (ConfigQuest.Quest15 > 0) {
            GameServer.deleteData(ConfigQuest.Quest15);
        }
        if (ConfigQuest.Quest16 > 0) {
            GameServer.deleteData(ConfigQuest.Quest16);
        }
        if (ConfigQuest.Quest17 > 0) {
            GameServer.deleteData(ConfigQuest.Quest17);
        }
        if (ConfigQuest.Quest18 > 0) {
            GameServer.deleteData(ConfigQuest.Quest18);
        }
        if (ConfigQuest.Quest19 > 0) {
            GameServer.deleteData(ConfigQuest.Quest19);
        }
        if (ConfigQuest.Quest20 > 0) {
            GameServer.deleteData(ConfigQuest.Quest20);
        }
        if (ConfigQuest.Quest21 > 0) {
            GameServer.deleteData(ConfigQuest.Quest21);
        }
        if (ConfigQuest.Quest22 > 0) {
            GameServer.deleteData(ConfigQuest.Quest22);
        }
        if (ConfigQuest.Quest23 > 0) {
            GameServer.deleteData(ConfigQuest.Quest23);
        }
        if (ConfigQuest.Quest24 > 0) {
            GameServer.deleteData(ConfigQuest.Quest24);
        }
        if (ConfigQuest.Quest25 > 0) {
            GameServer.deleteData(ConfigQuest.Quest25);
        }
        if (ConfigQuest.Quest26 > 0) {
            GameServer.deleteData(ConfigQuest.Quest26);
        }
        if (ConfigQuest.Quest27 > 0) {
            GameServer.deleteData(ConfigQuest.Quest27);
        }
        if (ConfigQuest.Quest28 > 0) {
            GameServer.deleteData(ConfigQuest.Quest28);
        }
        if (ConfigQuest.Quest29 > 0) {
            GameServer.deleteData(ConfigQuest.Quest29);
        }
        if (ConfigQuest.Quest30 > 0) {
            GameServer.deleteData(ConfigQuest.Quest30);
        }
        if (ConfigQuest.Quest31 > 0) {
            GameServer.deleteData(ConfigQuest.Quest31);
        }
        if (ConfigQuest.Quest32 > 0) {
            GameServer.deleteData(ConfigQuest.Quest32);
        }
        if (ConfigQuest.Quest33 > 0) {
            GameServer.deleteData(ConfigQuest.Quest33);
        }
        if (ConfigQuest.Quest34 > 0) {
            GameServer.deleteData(ConfigQuest.Quest34);
        }
        if (ConfigQuest.Quest35 > 0) {
            GameServer.deleteData(ConfigQuest.Quest35);
        }
        if (ConfigQuest.Quest36 > 0) {
            GameServer.deleteData(ConfigQuest.Quest36);
        }
        if (ConfigQuest.Quest37 > 0) {
            GameServer.deleteData(ConfigQuest.Quest37);
        }
        if (ConfigQuest.Quest38 > 0) {
            GameServer.deleteData(ConfigQuest.Quest38);
        }
        if (ConfigQuest.Quest39 > 0) {
            GameServer.deleteData(ConfigQuest.Quest39);
        }
        if (ConfigQuest.Quest40 > 0) {
            GameServer.deleteData(ConfigQuest.Quest40);
        }
        if (ConfigQuest.Quest41 > 0) {
            GameServer.deleteData(ConfigQuest.Quest41);
        }
        if (ConfigQuest.Quest42 > 0) {
            GameServer.deleteData(ConfigQuest.Quest42);
        }
        if (ConfigQuest.Quest43 > 0) {
            GameServer.deleteData(ConfigQuest.Quest43);
        }
        if (ConfigQuest.Quest44 > 0) {
            GameServer.deleteData(ConfigQuest.Quest44);
        }
        if (ConfigQuest.Quest45 > 0) {
            GameServer.deleteData(ConfigQuest.Quest45);
        }
        if (ConfigQuest.Quest46 > 0) {
            GameServer.deleteData(ConfigQuest.Quest46);
        }
        if (ConfigQuest.Quest47 > 0) {
            GameServer.deleteData(ConfigQuest.Quest47);
        }
        if (ConfigQuest.Quest48 > 0) {
            GameServer.deleteData(ConfigQuest.Quest48);
        }
        if (ConfigQuest.Quest49 > 0) {
            GameServer.deleteData(ConfigQuest.Quest49);
        }
        if (ConfigQuest.Quest50 > 0) {
            GameServer.deleteData(ConfigQuest.Quest50);
        }
    }

    public static void deleteData99(int questid) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            try {
                co = DatabaseFactory.get().getConnection();
                pm = co.prepareStatement("DELETE FROM `character_items` WHERE `item_id`=?");
                pm.setInt(1, questid);
                pm.execute();
            }
            catch (SQLException ex) {
                SQLUtil.close(pm);
                SQLUtil.close(co);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pm);
            SQLUtil.close(co);
            throw throwable;
        }
        SQLUtil.close(pm);
        SQLUtil.close(co);
    }

    public static void deleteData(int questid) {
        Connection co = null;
        PreparedStatement pm = null;
        try {
            try {
                co = DatabaseFactory.get().getConnection();
                pm = co.prepareStatement("DELETE FROM `character_quests` WHERE `quest_id`=?");
                pm.setInt(1, questid);
                pm.execute();
            }
            catch (SQLException ex) {
                SQLUtil.close(pm);
                SQLUtil.close(co);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pm);
            SQLUtil.close(co);
            throw throwable;
        }
        SQLUtil.close(pm);
        SQLUtil.close(co);
    }

    private void clanaddskill() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8541");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void clanaddskill1() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8544");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void clanaddskill2() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8545");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void clanaddskill3() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 8546");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void deleclan() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("DELETE FROM character_quests WHERE quest_id = 50988");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    private void dele1() {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("UPDATE `character_other` SET `usemap`=-1");
                pstm.execute();
            }
            catch (SQLException e) {
                CharacterQuestTable._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
                SQLUtil.close(pstm);
                SQLUtil.close(con);
                return;
            }
        }
        catch (Throwable throwable) {
            SQLUtil.close(pstm);
            SQLUtil.close(con);
            throw throwable;
        }
        SQLUtil.close(pstm);
        SQLUtil.close(con);
    }

    public static String getIp() throws IOException {
        URL whatismyip = new URL("http://icanhazip.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        return in.readLine();
    }
}

