package com.lineage.server.clientpackets;

import com.lineage.server.model.map.L1Map;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.map.L1WorldMap;
import com.add.NewAutoPractice;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.templates.L1Item;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;

import com.lineage.server.utils.SQLUtil;
import com.lineage.DatabaseFactory;
import com.lineage.server.datatables.ItemTable;

import java.util.StringTokenizer;
import java.util.List;

import com.lineage.server.serverpackets.S_WhoHero;

import java.util.concurrent.CopyOnWriteArrayList;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.data.event.BroadcastSet;
import com.lineage.server.BroadcastController;
import com.lineage.server.serverpackets.S_NpcChatShouting;
import com.lineage.server.serverpackets.S_NpcChat;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.serverpackets.S_Chat;
import com.lineage.server.command.GMCommands;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.serverpackets.S_ChatShouting;
import com.lineage.server.serverpackets.S_ChatClan;

import java.util.concurrent.ConcurrentHashMap;

import com.lineage.server.serverpackets.S_ChatParty;
import com.lineage.server.serverpackets.S_ChatClanUnion;
import com.lineage.server.world.WorldClan;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRecord;
import com.lineage.server.serverpackets.S_ChatParty2;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.model.L1Alliance;
import com.lineage.server.model.L1Clan;
import com.lineage.server.serverpackets.S_ChatClanAlliance;
import com.lineage.server.datatables.lock.ClanAllianceReading;
import com.lineage.server.model.L1Object;

import java.util.Iterator;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.world.World;
import com.lineage.config.ConfigAlt;
import com.lineage.william.Npc_BuyPet1;
import com.lineage.data.event.Chatvip;
import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.server.datatables.ServerQuestMobTable;
import com.lineage.data.event.QuestMobSet;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.config.ConfigAi;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.utils.Random;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.S_CharTitle;
import com.add.AutoAttackUpdate;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.echo.ClientExecutor;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class C_Chat extends ClientBasePacket
{
    private static final Log _log;
    private static final String _check_pwd = "abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#";
    private static final String _autype = "0123456789";
    
    static {
        _log = LogFactory.getLog((Class)C_Chat.class);
    }
    
    @Override
    public void start(final byte[] decrypt, final ClientExecutor client) {
        try {
            this.read(decrypt);
            final L1PcInstance pc = client.getActiveChar();
            final int chatType = this.readC();
            String chatText = this.readS();
            if (chatText != null && chatText.length() > 52) {
                chatText = chatText.substring(0, 52);
            }
            boolean isStop = false;
            boolean errMessage = false;
            if (pc.hasSkillEffect(64) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(8912) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(161) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(1007) && !pc.isGm()) {
                isStop = true;
            }
            if (pc.hasSkillEffect(4002)) {
                isStop = true;
                errMessage = true;
            }
            if (pc.hasSkillEffect(7004)) {
                isStop = true;
                errMessage = true;
            }
            if (pc.hasSkillEffect(9990)) {
                pc.sendPackets(new S_SystemMessage("目前受到監禁狀態中無法正常說話。"));
            }
            else {
                if ((pc.getMapId() == 5153 || pc.getMapId() == 5154) && !pc.isGm()) {
                    isStop = true;
                }
                if (!isStop) {
                    switch (chatType) {
                        case 0: {
                            if (pc.is_retitle()) {
                                this.re_title(pc, chatText.trim());
                            }
                            else if (pc.is_repass() != 0) {
                                this.re_repass(pc, chatText.trim());
                            }
                            else if (pc.IsKeyInEnemy()) {
                                this.KeyInEnemyName(pc, chatText.trim());
                            }
                            else if (pc.IsKeyOutEnemy()) {
                                this.KeyOutEnemyName(pc, chatText.trim());
                            }
                            else if (pc.IsBadKeyInEnemy()) {
                                this.BadKeyInEnemyName(pc, chatText.trim());
                            }
                            else if (pc.IsBadKeyOutEnemy()) {
                                this.BadKeyOutEnemyName(pc, chatText.trim());
                            }
                            else {
                                if (pc.IsAu_SetShop()) {
                                    boolean err_1 = false;
                                    for (int i = 0; i < chatText.length(); ++i) {
                                        final String ch = chatText.substring(i, i + 1);
                                        if (!"0123456789".contains(ch.toLowerCase())) {
                                            pc.sendPackets(new S_SystemMessage("無效字元！請重新設定"));
                                            err_1 = true;
                                            break;
                                        }
                                    }
                                    if (err_1) {
                                        pc.setAu_SetShop(false);
                                        pc.SetAu_SetShopType(-1);
                                    }
                                    else {
                                        int count = Integer.valueOf(chatText);
                                        if (count > 1000) {
                                            count = 1000;
                                            pc.sendPackets(new S_SystemMessage("由於設定數量超過最大上限1000"));
                                            pc.sendPackets(new S_SystemMessage("系統將自動設定1000上限值"));
                                        }
                                        pc.setAu_BuyItemCount(pc.getAu_SetShopType(), count);
                                        if (pc.getAu_SetShopType() == 0 && pc.getAu_BuyItemSwitch(0) == 1) {
                                            pc.get_other1().set_type3(count);
                                        }
                                        if (pc.getAu_SetShopType() == 1 && pc.getAu_BuyItemSwitch(1) == 1) {
                                            pc.get_other1().set_type5(count);
                                        }
                                        if (pc.getAu_SetShopType() == 2 && pc.getAu_BuyItemSwitch(2) == 1) {
                                            pc.get_other1().set_type7(count);
                                        }
                                        if (pc.getAu_SetShopType() == 3 && pc.getAu_BuyItemSwitch(3) == 1) {
                                            pc.get_other1().set_type9(count);
                                        }
                                        if (pc.getAu_SetShopType() == 4 && pc.getAu_BuyItemSwitch(4) == 1) {
                                            pc.get_other1().set_type11(count);
                                        }
                                        if (pc.getAu_SetShopType() == 5 && pc.getAu_BuyItemSwitch(5) == 1) {
                                            pc.get_other1().set_type13(count);
                                        }
                                        if (pc.getAu_SetShopType() == 6 && pc.getAu_BuyItemSwitch(6) == 1) {
                                            pc.get_other1().set_type15(count);
                                        }
                                        if (pc.getAu_SetShopType() == 7 && pc.getAu_BuyItemSwitch(7) == 1) {
                                            pc.get_other1().set_type17(count);
                                        }
                                        if (pc.getAu_SetShopType() == 8 && pc.getAu_BuyItemSwitch(8) == 1) {
                                            pc.get_other1().set_type19(count);
                                        }
                                        pc.sendPackets(new S_SystemMessage("設置完成"));
                                        pc.setAu_SetShop(false);
                                        pc.SetAu_SetShopType(-1);
                                        AutoAttackUpdate.getInstance().Au_Shop(pc);
                                    }
                                    return;
                                }
                                if (!pc.isGm() && pc.hasSkillEffect(98767)) {
                                    if (chatText.startsWith(String.valueOf(pc.getrantitle()))) {
                                        pc.killSkillEffectTimer(98767);
                                        pc.sendPackets(new S_SystemMessage("感謝您的驗證,[驗證成功]"));
                                        pc.sendPackets(new S_CharTitle(pc.getId(), pc.get_savetitle()));
                                        pc.setrantitle(0);
                                        pc.set_savetitle(null);
                                        pc.addaierrorcheck(0);
                                    }
                                    else {
                                        pc.addaierrorcheck(1);
                                        if (pc.getaierrorcheck() == 2) {
                                            pc.sendPackets(new S_Disconnect());
                                        }
                                        else {
                                            pc.sendPackets(new S_SystemMessage("由於您輸入錯誤再給一次機會"));
											pc.sendPackets(new S_SystemMessage("請重新輸入新的[封號上的驗證碼]"));
											pc.sendPackets(new S_SystemMessage("\\fT由於您輸入錯誤再給一次機會"));
											pc.sendPackets(new S_SystemMessage("\\fT請重新輸入新的[封號上的驗證碼]"));
                                            pc.sendPackets(new S_SystemMessage("\\fU由於您輸入錯誤再給一次機會"));
                                            pc.sendPackets(new S_SystemMessage("\\fU請重新輸入新的[封號上的驗證碼]"));
                                            pc.setrantitle(Random.nextInt(9999));
                                            pc.sendPackets(new S_CharTitle(pc.getId(), "驗證碼:" + pc.getrantitle()));
                                            pc.setSkillEffect(98767, 60000);
                                        }
                                    }
                                }
                                else if (pc.isGm() && pc.hasSkillEffect(98767)) {
                                    if (chatText.startsWith(ConfigOther.verificationcode)) {
                                        pc.sendPackets(new S_Paralysis(4, false)); //解除凍結狀態
                                        pc.setSkillEffect(51239, 99999000); //GM權限
                                        pc.killSkillEffectTimer(98767); //驗證狀態解除
                                        pc.sendPackets(new S_SystemMessage("[驗證成功]"));
                                    }
                                    else {
                                        pc.addaierrorcheck(1);
                                        if (pc.getaierrorcheck() == 2) {
                                            pc.sendPackets(new S_Disconnect());
                                        }
                                    }
                                }
                                else if (pc.hasSkillEffect(8008)) {
                                    pc.killSkillEffectTimer(8008);
                                    this.check_broadcast(pc, chatText);
                                }
                                else {
                                    if (chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954)) {
                                        pc.killSkillEffectTimer(7954);
                                        pc.setnewaicount_2(0);
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg3));
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg4));
                                        final int time = ConfigAi.aitimelast + Random.nextInt(ConfigAi.aitimeran) + 1;
                                        pc.setSkillEffect(7950, time * 1000);
                                    }
                                    else if (!chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954) && pc.getInputError() == 0) {
                                        pc.setInputError(pc.getInputError() + 1);
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg7));
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                                    }
                                    else if (!chatText.startsWith(String.valueOf(pc.getnewaicount_2())) && pc.hasSkillEffect(7954) && pc.getInputError() == 1) {
                                        pc.setInputError(pc.getInputError() + 1);
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg7));
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg5));
                                        pc.sendPackets(new S_ServerMessage(ConfigAi.msg6));
                                    }
                                    else if (pc.getInputError() == 2 && pc.hasSkillEffect(7954)) {
                                        pc.setInputError(0);
                                        pc.saveInventory();
                                        pc.sendPackets(new S_Disconnect());
                                        C_Chat._log.info(String.format("玩家 : %s 因輸入錯誤未通過外掛偵測，已強制切斷其連線", new Object[] { pc.getName() }));
                                    }
                                    if (pc.hasSkillEffect(95133) && chatText.startsWith(chatText)) {
                                        pc.killSkillEffectTimer(95133);
                                        CharBookReading.get().deleteBookmark(pc, chatText);
                                        pc.sendPackets(new S_SystemMessage("已刪除此名稱座標.請重新登入遊戲"));
                                    }
                                    else if (pc.isItemName()) {
                                        this.ItemName(pc, chatText);
                                        pc.setItemName(false);
                                    }
                                    else if (!chatText.startsWith(ConfigGuaji.guaji)) {
                                        if (chatText.startsWith("解卡點")) {
                                            if (!pc.isParalyzed() && !pc.isSleeped() && !pc.isParalyzedX() && !pc.isDead() && !pc.isInvisble()) {
                                                if (pc.hasSkillEffect(55889)) {
                                                    pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                                }
                                                else if (pc.hasSkillEffect(76001)) {
                                                    pc.sendPackets(new S_SystemMessage("請不要按太快.."));
                                                }
                                                else if (!pc.hasSkillEffect(4000)) {
                                                    pc.setSkillEffect(55889, 30000);
                                                    pc.set_unfreezingTime(5);
                                                }
                                            }
                                        }
                                        else if (chatText.startsWith("解錯位")) {
                                            if (!pc.isParalyzed() && !pc.isSleeped() && !pc.isParalyzedX() && !pc.isDead() && !pc.isInvisble()) {
                                                if (pc.hasSkillEffect(55889)) {
                                                    pc.sendPackets(new S_ServerMessage("無法連續使用該功能"));
                                                }
                                                else if (pc.hasSkillEffect(76001)) {
                                                    pc.sendPackets(new S_SystemMessage("請不要按太快.."));
                                                }
                                                else {
                                                    pc.setSkillEffect(55889, 30000);
                                                    pc.set_misslocTime(3);
                                                }
                                            }
                                        }
                                        else if (QuestMobSet.START && chatText.equalsIgnoreCase(".任務")) {
                                            ServerQuestMobTable.get().getQuestMobNote(pc);
                                        }
                                        else {
                                            if (Config.GUI) {
                                                J_Main.getInstance().addNormalChat(pc.getName(), chatText);
                                            }
                                            if (Chatvip.START) {
                                                Npc_BuyPet1.forNpcQuest(chatText, pc);
                                            }
                                            this.chatType_0(pc, chatText);
                                            if (ConfigAlt.GM_OVERHEARD0) {
                                                for (final L1Object visible : World.get().getAllPlayers()) {
                                                    if (visible instanceof L1PcInstance) {
                                                        final L1PcInstance GM = (L1PcInstance)visible;
                                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                                            continue;
                                                        }
                                                        if (pc.getId() == GM.getId()) {
                                                            continue;
                                                        }
                                                        GM.sendPackets(new S_ServerMessage(166, "\\fW【一般】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                                    }
                                                }
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                            return;
                        }
                        case 2: {
                            if (Config.GUI) {
                                J_Main.getInstance().addNormalChat(pc.getName(), chatText);
                            }
                            this.chatType_2(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD0) {
                                for (final L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        final L1PcInstance GM = (L1PcInstance)visible;
                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                            continue;
                                        }
                                        if (pc.getId() == GM.getId()) {
                                            continue;
                                        }
                                        GM.sendPackets(new S_ServerMessage(166, "\\fV【大喊】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 4: {
                            if (Config.GUI) {
                                J_Main.getInstance().addClanChat(pc.getName(), chatText);
                            }
                            this.chatType_4(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD4) {
                                for (final L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        final L1PcInstance GM = (L1PcInstance)visible;
                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                            continue;
                                        }
                                        if (pc.getId() == GM.getId()) {
                                            continue;
                                        }
                                        GM.sendPackets(new S_ServerMessage(166, "\\fV【血盟】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 11: {
                            if (Config.GUI) {
                                J_Main.getInstance().addTeamChat(pc.getName(), chatText);
                            }
                            this.chatType_11(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD11) {
                                for (final L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        final L1PcInstance GM = (L1PcInstance)visible;
                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                            continue;
                                        }
                                        if (pc.getId() == GM.getId()) {
                                            continue;
                                        }
                                        GM.sendPackets(new S_ServerMessage(166, "\\fV【隊伍】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 13: {
                            if (Config.GUI) {
                                J_Main.getInstance().addClanChat(pc.getName(), chatText);
                            }
                            this.chatType_13(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD4) {
                                for (final L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        final L1PcInstance GM = (L1PcInstance)visible;
                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                            continue;
                                        }
                                        if (pc.getId() == GM.getId()) {
                                            continue;
                                        }
                                        GM.sendPackets(new S_ServerMessage(166, "\\fV【守護】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 14: {
                            if (Config.GUI) {
                                J_Main.getInstance().addTeamChat(pc.getName(), chatText);
                            }
                            this.chatType_14(pc, chatText);
                            if (ConfigAlt.GM_OVERHEARD0) {
                                for (final L1Object visible : World.get().getAllPlayers()) {
                                    if (visible instanceof L1PcInstance) {
                                        final L1PcInstance GM = (L1PcInstance)visible;
                                        if (!GM.getcheckgm() || !GM.isGm()) {
                                            continue;
                                        }
                                        if (pc.getId() == GM.getId()) {
                                            continue;
                                        }
                                        GM.sendPackets(new S_ServerMessage(166, "\\fV【群聊】", " ( " + pc.getName() + " ) :【 ", String.valueOf(chatText) + " 】"));
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 15: {
                            this.chatType_15(pc, chatText);
                            break;
                        }
                    }
                    if (!pc.isGm()) {
                        pc.checkChatInterval();
                    }
                    return;
                }
                if (errMessage) {
                    pc.sendPackets(new S_ServerMessage(242));
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
    
    private void chatType_15(final L1PcInstance pc, final String chatText) {
        if (pc.getClanid() != 0) {
            final L1Clan clan = pc.getClan();
            if (clan == null) {
                return;
            }
            final L1Alliance alliance = ClanAllianceReading.get().getAlliance(clan.getClanId());
            if (alliance == null) {
                return;
            }
            final S_ChatClanAlliance chatpacket = new S_ChatClanAlliance(pc, clan.getClanName(), chatText);
            alliance.sendPacketsAll(pc.getName(), chatpacket);
        }
    }
    
    private void re_repass(final L1PcInstance pc, final String password) {
        try {
            switch (pc.is_repass()) {
                case 1: {
                    if (!pc.getNetConnection().getAccount().get_password().equals(password)) {
                        pc.sendPackets(new S_ServerMessage(1744));
                        return;
                    }
                    pc.repass(2);
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[] { "請輸入您的新密碼" }));
                    break;
                }
                case 2: {
                    boolean iserr = false;
                    for (int i = 0; i < password.length(); ++i) {
                        final String ch = password.substring(i, i + 1);
                        if (!"abcdefghijklmnopqrstuvwxyz0123456789!_=+-?.#".contains(ch.toLowerCase())) {
                            pc.sendPackets(new S_ServerMessage(1742));
                            iserr = true;
                            break;
                        }
                    }
                    if (password.length() > 13) {
                        pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
                        iserr = true;
                    }
                    if (password.length() < 3) {
                        pc.sendPackets(new S_ServerMessage(166, "密碼長度過長"));
                        iserr = true;
                    }
                    if (iserr) {
                        return;
                    }
                    pc.setText(password);
                    pc.repass(3);
                    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_pass_01", new String[] { "請確認您的新密碼" }));
                    break;
                }
                case 3: {
                    if (!pc.getText().equals(password)) {
                        pc.sendPackets(new S_ServerMessage(1982));
                        return;
                    }
                    pc.sendPackets(new S_CloseList(pc.getId()));
                    pc.getInventory().consumeItem(49538, 1L);
                    pc.sendPackets(new S_ServerMessage(1985));
                    AccountReading.get().updatePwd(pc.getAccountName(), password);
                    pc.setText(null);
                    pc.repass(0);
                    RecordTable.get().recordPcChangePassWord(pc.getId(), pc.getAccountName(), pc.getName(), password, pc.getIp());
                    break;
                }
            }
        }
        catch (Exception e) {
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.sendPackets(new S_ServerMessage(45));
            pc.setText(null);
            pc.repass(0);
            C_Chat._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void re_title(final L1PcInstance pc, final String chatText) {
        try {
            final String newchatText = chatText.trim();
            if (newchatText.isEmpty() || newchatText.length() <= 0) {
                pc.sendPackets(new S_ServerMessage("\\fU請輸入封號內容"));
                return;
            }
            final int length = Config.LOGINS_TO_AUTOENTICATION ? 18 : 13;
            if (newchatText.getBytes().length > length) {
                pc.sendPackets(new S_ServerMessage("\\fU封號長度過長"));
                return;
            }
            final StringBuilder title = new StringBuilder();
            title.append(newchatText);
            pc.setTitle(title.toString());
            pc.sendPacketsAll(new S_CharTitle(pc.getId(), title));
            pc.save();
            pc.retitle(false);
            pc.sendPackets(new S_ServerMessage("\\fU封號變更完成"));
            pc.getInventory().removeItem(49537, 1L);
        }
        catch (Exception e) {
            C_Chat._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void chatType_14(final L1PcInstance pc, final String chatText) {
        if (pc.isInChatParty()) {
            final S_ChatParty2 chatpacket = new S_ChatParty2(pc, chatText);
            final L1PcInstance[] partyMembers = pc.getChatParty().getMembers();
            final L1PcInstance[] arrayOfL1PcInstance1;
            final int i = (arrayOfL1PcInstance1 = partyMembers).length;
            for (byte b = 0; b < i; ++b) {
                final L1PcInstance listner = arrayOfL1PcInstance1[b];
                if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                    listner.sendPackets(chatpacket);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_CHAT_PARTY) {
                RecordTable.get().recordeTalk("群聊", pc.getName(), pc.getClanname(), null, chatText);
            }
        }
    }
    
    private void chatType_13(final L1PcInstance pc, final String chatText) {
        if (pc.getClanid() != 0) {
            final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan == null) {
                return;
            }
            switch (pc.getClanRank()) {
                case 3:
                case 4:
                case 6:
                case 9:
                case 10: {
                    final S_ChatClanUnion chatpacket = new S_ChatClanUnion(pc, chatText);
                    final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                    final L1PcInstance[] arrayOfL1PcInstance1;
                    final int i = (arrayOfL1PcInstance1 = clanMembers).length;
                    for (byte b = 0; b < i; ++b) {
                        final L1PcInstance listner = arrayOfL1PcInstance1[b];
                        if (!listner.getExcludingList().contains(pc.getName())) {
                            switch (listner.getClanRank()) {
                                case 3:
                                case 4:
                                case 6:
                                case 9:
                                case 10: {
                                    listner.sendPackets(chatpacket);
                                    break;
                                }
                            }
                        }
                    }
                    if (ConfigRecord.LOGGING_CHAT_COMBINED) {
                        RecordTable.get().recordeTalk("聯盟", pc.getName(), pc.getClanname(), null, chatText);
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private void chatType_11(final L1PcInstance pc, final String chatText) {
        if (pc.isInParty()) {
            final S_ChatParty chatpacket = new S_ChatParty(pc, chatText);
            final ConcurrentHashMap<Integer, L1PcInstance> pcs = pc.getParty().partyUsers();
            if (pcs.isEmpty()) {
                return;
            }
            if (pcs.size() <= 0) {
                return;
            }
            for (final L1PcInstance listner : pcs.values()) {
                if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                    listner.sendPackets(chatpacket);
                }
            }
            if (ConfigRecord.LOGGING_CHAT_PARTY) {
                RecordTable.get().recordeTalk("組隊", pc.getName(), pc.getClanname(), null, chatText);
            }
        }
    }
    
    private void chatType_4(final L1PcInstance pc, final String chatText) {
        if (pc.getClanid() != 0) {
            final L1Clan clan = WorldClan.get().getClan(pc.getClanname());
            if (clan != null) {
                final S_ChatClan chatpacket = new S_ChatClan(pc, chatText);
                final L1PcInstance[] clanMembers = clan.getOnlineClanMember();
                final L1PcInstance[] arrayOfL1PcInstance1;
                final int i = (arrayOfL1PcInstance1 = clanMembers).length;
                for (byte b = 0; b < i; ++b) {
                    final L1PcInstance listner = arrayOfL1PcInstance1[b];
                    if (!listner.isBadInEnemyList(pc.getName()) && !listner.getExcludingList().contains(pc.getName())) {
                        listner.sendPackets(chatpacket);
                    }
                }
                if (ConfigRecord.LOGGING_CHAT_CLAN) {
                    RecordTable.get().recordeTalk("盟頻", pc.getName(), pc.getClanname(), null, chatText);
                }
            }
        }
    }
    
    private void chatType_2(final L1PcInstance pc, final String chatText) {
        if (pc.isGhost()) {
            return;
        }
        S_ChatShouting chatpacket = null;
        String name = pc.getName();
        if (pc.get_outChat() == null) {
            chatpacket = new S_ChatShouting(pc, chatText);
        }
        else {
            chatpacket = new S_ChatShouting(pc.get_outChat(), chatText);
            name = pc.get_outChat().getNameId();
        }
        pc.sendPackets(chatpacket);
        for (final L1PcInstance listner : World.get().getVisiblePlayer(pc, 50)) {
            if (!listner.getExcludingList().contains(name) && !listner.isBadInEnemyList(pc.getName()) && pc.get_showId() == listner.get_showId()) {
                listner.sendPackets(chatpacket);
            }
        }
        if (ConfigRecord.LOGGING_CHAT_SHOUT) {
            RecordTable.get().recordeTalk("大喊", pc.getName(), pc.getClanname(), null, chatText);
        }
        this.doppelShouting(pc, chatText);
    }
    
    private void chatType_0(final L1PcInstance pc, final String chatText) {
        if (pc.isGhost() && !pc.isGm() && !pc.isMonitor()) {
            return;
        }
        if ((pc.getAccessLevel() == 200 || pc.getAccessLevel() == 100) && chatText.startsWith(".")) {
            final String cmd = chatText.substring(1);
            GMCommands.getInstance().handleCommands(pc, cmd);
            return;
        }
        S_Chat chatpacket = null;
        String name = pc.getName();
        if (pc.get_outChat() == null) {
            chatpacket = new S_Chat(pc, chatText);
        }
        else {
            chatpacket = new S_Chat(pc.get_outChat(), chatText);
            name = pc.get_outChat().getNameId();
        }
        pc.sendPackets(chatpacket);
        for (final L1PcInstance listner : World.get().getRecognizePlayer(pc)) {
            if (!listner.isBadInEnemyList(pc.getName()) && pc.get_showId() == listner.get_showId()) {
                listner.sendPackets(chatpacket);
            }
        }
        if (ConfigRecord.LOGGING_CHAT_NORMAL) {
            RecordTable.get().recordeTalk("一般", pc.getName(), pc.getClanname(), null, chatText);
        }
        this.doppelGenerally(pc, chatText);
    }
    
    private void doppelGenerally(final L1PcInstance pc, final String chatText) {
        for (final L1Object obj : pc.getKnownObjects()) {
            if (obj instanceof L1MonsterInstance) {
                final L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (!mob.getNpcTemplate().is_doppel()) {
                    continue;
                }
                if (!mob.getName().equals(pc.getName())) {
                    continue;
                }
                mob.broadcastPacketX8(new S_NpcChat(mob, chatText));
            }
        }
    }
    
    private void doppelShouting(final L1PcInstance pc, final String chatText) {
        for (final L1Object obj : pc.getKnownObjects()) {
            if (obj instanceof L1MonsterInstance) {
                final L1MonsterInstance mob = (L1MonsterInstance)obj;
                if (!mob.getNpcTemplate().is_doppel()) {
                    continue;
                }
                if (!mob.getName().equals(pc.getName())) {
                    continue;
                }
                mob.broadcastPacketX8(new S_NpcChatShouting(mob, chatText));
            }
        }
    }
    
    private void check_broadcast(final L1PcInstance pc, final String chatText) {
        try {
            if (chatText.isEmpty() || chatText.length() <= 0) {
                pc.sendPackets(new S_SystemMessage("請輸入訊息內容。"));
                return;
            }
            if (pc.isGm()) {
                if (chatText.equals("開啟")) {
                    BroadcastController.getInstance().setStop(false);
                    pc.sendPackets(new S_SystemMessage("廣播系統已開啟。"));
                    return;
                }
                if (chatText.equals("關閉")) {
                    BroadcastController.getInstance().setStop(true);
                    pc.sendPackets(new S_SystemMessage("廣播系統已關閉。"));
                    return;
                }
            }
            if (chatText.getBytes().length > 50) {
                pc.sendPackets(new S_SystemMessage("廣播訊息長度過長 (不能超過25個中文字)"));
                return;
            }
            final StringBuilder message = new StringBuilder();
            message.append("玩家【").append(pc.getName()).append("】對大家說 : ").append(chatText);
            final L1ItemInstance item = pc.getInventory().checkItemX(BroadcastSet.ITEM_ID, 1L);
            if (item == null) {
                pc.sendPackets(new S_SystemMessage("不具有廣播卡，因此無法發送訊息。"));
                return;
            }
            if (BroadcastController.getInstance().requestWork(message.toString())) {
                pc.getInventory().removeItem(item, 1L);
                pc.sendPackets(new S_SystemMessage("已成功發布廣播訊息。"));
            }
            else {
                pc.sendPackets(new S_SystemMessage("目前有太多等待訊息，請稍後再嘗試一次。"));
            }
        }
        catch (Exception e) {
            C_Chat._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void Itemopen(final L1PcInstance pc, final String arg) {
        final L1PcInstance target = World.get().getPlayer(arg);
        if (target != null) {
            final List<L1ItemInstance> items = pc.getInventory().getItems();
            final List<L1ItemInstance> itemsx = new CopyOnWriteArrayList<L1ItemInstance>();
            for (final L1ItemInstance item : items) {
                if (item.isEquipped()) {
                    itemsx.add(item);
                }
            }
            target.sendPackets(new S_WhoHero(target, 0, itemsx));
            final String msg1 = "";
            String msg2 = "";
            String msg3 = "";
            final String msg4 = "";
            final String msg5 = "";
            final String msg6 = "";
            final String msg7 = "";
            final String msg8 = "";
            final String msg9 = "";
            final String msg10 = "";
            String msg11 = "";
            final int i = pc.getLevel();
            msg2 = String.valueOf(String.valueOf(pc.getCurrentHp())) + " / " + pc.getMaxHp();
            msg3 = String.valueOf(String.valueOf(pc.getCurrentMp())) + " / " + pc.getMaxMp();
            final int j = pc.getAc();
            final short s1 = pc.getStr();
            final short s2 = pc.getDex();
            final short s3 = pc.getInt();
            final short s4 = pc.getCon();
            final short s5 = pc.getWis();
            final short s6 = pc.getCha();
            msg11 = String.valueOf(String.valueOf(pc.getMr())) + " %";
			target.sendPackets(new S_SystemMessage("\\fT等級:[" + i + "]"));
			target.sendPackets(new S_SystemMessage("\\fW血量:[" + msg2 + "]  //  魔力:[" + msg3 + "]"));
			target.sendPackets(new S_SystemMessage("\\fU防禦:[" + j + "]  //  魔防:[" + msg11 + "]"));
			target.sendPackets(new S_SystemMessage("\\fR力量:[" + s1 + "]  //  敏捷:[" + s2 + "]"));
			target.sendPackets(new S_SystemMessage("\\fR智力:[" + s3 + "]  //  體質:[" + s4 + "]"));
			target.sendPackets(new S_SystemMessage("\\fR精神:[" + s5 + "]  //  魅力:[" + s6 + "]"));
        }
    }
    
    public void ItemName(final L1PcInstance pc, final String arg) {
        final StringTokenizer st = new StringTokenizer(arg);
        final String nameid = st.nextToken();
        int dropID = 0;
        try {
            dropID = Integer.parseInt(nameid);
        }
        catch (NumberFormatException e) {
            dropID = ItemTable.get().findItemIdByNameWithoutSpace1(nameid);
            if (dropID == 0) {
                pc.sendPackets(new S_SystemMessage("找不到您指定的道具名稱"));
                return;
            }
        }
        if (dropID == 40308) {
            pc.sendPackets(new S_SystemMessage("【金幣】幾乎都會掉落"));
        }
        else {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                final L1Item item = ItemTable.get().getTemplate(dropID);
                String blessed;
                if (item.getBless() == 1) {
                    blessed = "";
                }
                else if (item.getBless() == 0) {
                    blessed = "\\fR";
                }
                else {
                    blessed = "\\fY";
                }
                con = DatabaseFactory.get().getConnection();
                pstm = con.prepareStatement("SELECT mobId,min,max,chance FROM droplist WHERE itemId=?");
                pstm.setInt(1, dropID);
                rs = pstm.executeQuery();
                rs.last();
                final int rows = rs.getRow();
                final int[] mobID = new int[rows];
                final double[] chance = new double[rows];
                final String[] name = new String[rows];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    mobID[i] = rs.getInt("mobId");
                    chance[i] = rs.getInt("chance") / 10000.0;
                    ++i;
                }
                rs.close();
                pstm.close();
                pc.sendPackets(new S_SystemMessage("\\f=此物品:【" + blessed + item.getName() + "】掉落的怪物如下:"));
                for (int j = 0; j < mobID.length; ++j) {
                    pstm = con.prepareStatement("SELECT name FROM npc WHERE npcid=?");
                    pstm.setInt(1, mobID[j]);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        name[j] = rs.getString("name");
                    }
                    rs.close();
                    pstm.close();
                    if (pc.isGm()) {
                        pc.sendPackets(new S_SystemMessage("\\fS怪物: " + name[j] + " 機率: " + chance[j] + "%"));
                    } else {
                        pc.sendPackets(new S_SystemMessage("\\fS怪物: " + name[j]));
                    }
                }
            }
            catch (Exception ex) {
                return;
            }
            finally {
                SQLUtil.close(rs, pstm, con);
            }
            SQLUtil.close(rs, pstm, con);
            SQLUtil.close(rs, pstm, con);
        }
    }
    
    private void KeyInEnemyName(final L1PcInstance pc, final String name) {
    if (!CharacterTable.doesCharNameExist(name)) {
        pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
        pc.setKeyInEnemy(false);
    } else if (!pc.isInEnemyList(name)) {
        if (pc.getName().equals(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU不能添加自己為仇人"));
        } else {
            pc.sendPackets(new S_ServerMessage("\\fU資料確認成功！已經成功添加名單"));
            NewAutoPractice.get().AddEnemyList(pc, name);
            pc.setKeyInEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.setInEnemyList(name);
        }
    } else {
        pc.sendPackets(new S_ServerMessage("\\fU名稱重複 請確認"));
        pc.setKeyInEnemy(false);
        pc.sendPackets(new S_CloseList(pc.getId()));
    }
}
    
private void KeyOutEnemyName(final L1PcInstance pc, final String name) {
    if (!CharacterTable.doesCharNameExist(name)) {
        pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
        pc.setKeyOutEnemy(false);
    } else if (pc.isInEnemyList(name)) {
        pc.sendPackets(new S_ServerMessage("\\fU資料確認成功！已經成功從名單中移除"));
        NewAutoPractice.get().DeleteEnemyList(pc, name);
        pc.setKeyOutEnemy(false);
        pc.sendPackets(new S_CloseList(pc.getId()));
        pc.removeInEnemyList(name);
    } else {
        pc.sendPackets(new S_ServerMessage("\\fU你的仇人名單內並無此人喔"));
        pc.setKeyOutEnemy(false);
        pc.sendPackets(new S_CloseList(pc.getId()));
    }
}
    
    private void BadKeyInEnemyName(final L1PcInstance pc, final String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setBadKeyInEnemy(false);
        }
        else if (!pc.isBadInEnemyList(name)) {
            if (pc.getName().equals(name)) {
                pc.sendPackets(new S_ServerMessage("\\fU不能添加自己為黑名單"));
            }
            else {
                pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功添加名單"));
                NewAutoPractice.get().AddBadEnemyList(pc, name);
                pc.setBadKeyInEnemy(false);
                pc.sendPackets(new S_CloseList(pc.getId()));
                pc.setBadInEnemyList(name);
            }
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU名稱重複 請確認"));
            pc.setBadKeyInEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    private void BadKeyOutEnemyName(final L1PcInstance pc, final String name) {
        if (!CharacterTable.doesCharNameExist(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU您輸入的名稱錯誤 資料庫無此資料。"));
            pc.setBadKeyOutEnemy(false);
        }
        else if (pc.isBadInEnemyList(name)) {
            pc.sendPackets(new S_ServerMessage("\\fU資料確認成功 !已經成功從黑名單中移除"));
            NewAutoPractice.get().DeleteBadEnemyList(pc, name);
            pc.setBadKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
            pc.removeBadInEnemyList(name);
        }
        else {
            pc.sendPackets(new S_ServerMessage("\\fU你的黑名單內並無此人喔"));
            pc.setBadKeyOutEnemy(false);
            pc.sendPackets(new S_CloseList(pc.getId()));
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
    
    private class TeleportRunnable implements Runnable
    {
        private final L1PcInstance _pc;
        private int _locX;
        private int _locY;
        private int _mapid;
        
        public TeleportRunnable(final L1PcInstance pc, final int x, final int y, final int mapid) {
            this._locX = 0;
            this._locY = 0;
            this._mapid = 0;
            this._pc = pc;
            this._locX = x;
            this._locY = y;
            this._mapid = mapid;
        }
        
        @Override
        public void run() {
            try {
                final L1Map map = L1WorldMap.get().getMap((short)this._mapid);
                final int r = 10;
                int tryCount = 0;
                int newX = this._locX;
                int newY = this._locY;
                do {
                    ++tryCount;
                    newX = this._locX + (int)(Math.random() * 10.0) - (int)(Math.random() * 10.0);
                    newY = this._locY + (int)(Math.random() * 10.0) - (int)(Math.random() * 10.0);
                    if (map.isPassable(newX, newY, this._pc)) {
                        break;
                    }
                    Thread.sleep(1L);
                } while (tryCount < 5);
                if (tryCount >= 5) {
                    L1Teleport.teleport(this._pc, this._locX, this._locY, (short)this._mapid, this._pc.getHeading(), true);
                }
                else {
                    L1Teleport.teleport(this._pc, newX, newY, (short)this._mapid, this._pc.getHeading(), true);
                }
            }
            catch (InterruptedException ex) {}
        }
    }
}
