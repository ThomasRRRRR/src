// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.server.command;

import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1Location;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.templates.L1Doll;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.model.Instance.L1DollInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.datatables.DollPowerTable;
import com.lineage.server.serverpackets.S_CloseList;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.datatables.DeGlobalChatTable;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import java.util.Iterator;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.world.WorldNpc;
import com.lineage.server.world.World;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1DeInstance;
import java.util.TreeMap;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.Random;
import org.apache.commons.logging.Log;

public class GmHtml
{
    private static final Log _log;
    private static final Random _random;
    private L1PcInstance _pc;
    private int _mode;
    private int _users;
    private TreeMap<Integer, L1DeInstance> _allDeList;
    private TreeMap<Integer, L1PcInstance> _allPcList;
    private TreeMap<Integer, String> _banList;
    private TreeMap<Integer, String> _banTmpList;
    
    static {
        _log = LogFactory.getLog((Class)GmHtml.class);
        _random = new Random();
    }
    
    public GmHtml(final L1PcInstance pc) {
        this._pc = pc;
        this._pc.get_other().set_page(0);
        this._pc.get_other().set_gmHtml(this);
    }
    
    public GmHtml(final L1PcInstance pc, final int mode) {
        this._pc = pc;
        this._pc.get_other().set_page(0);
        this._pc.get_other().set_gmHtml(this);
        this._users = World.get().getAllPlayers().size();
        this._allDeList = new TreeMap<Integer, L1DeInstance>();
        this._allPcList = new TreeMap<Integer, L1PcInstance>();
        this._banList = new TreeMap<Integer, String>();
        this._banTmpList = new TreeMap<Integer, String>();
        this._mode = mode;
        int keyDe = 0;
        for (final L1NpcInstance npc : WorldNpc.get().all()) {
            if (npc instanceof L1DeInstance) {
                final L1DeInstance de = (L1DeInstance)npc;
                this._allDeList.put(new Integer(keyDe), de);
                ++keyDe;
            }
        }
        int keyPc = 0;
        for (final L1PcInstance tgpc : World.get().getAllPlayers()) {
            this._allPcList.put(new Integer(keyPc), tgpc);
            ++keyPc;
        }
        int keyBan = 0;
        for (final String ban : LanSecurityManager.BANIPMAP.keySet()) {
            this._banList.put(new Integer(keyBan), ban);
            ++keyBan;
        }
        for (final String ban : LanSecurityManager.BANNAMEMAP.keySet()) {
            this._banList.put(new Integer(keyBan), ban);
            ++keyBan;
        }
    }
    
    public void show() {
        this.showPage(0);
    }
    
    public void show(final L1DeInstance de_tmp) {
        final L1DeInstance de = this._pc.get_outChat();
        if (de != null) {
            String[] type = { "王族", "騎士", "精靈", "法師", "黑妖", "龍騎", "幻術" };
			String[] sex = { "男", "女" };
            final String[] info = { de.getNameId(), type[de.get_deName().get_type()], sex[de.get_deName().get_sex()], String.valueOf(de.getLevel()) };
            this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_GmDE", info));
        }
    }
    
    public void action(final String cmd) {
        try {
            if (cmd.equals("up")) {
                final int page = this._pc.get_other().get_page() - 1;
                this.showPage(page);
            }
            else if (cmd.equals("dn")) {
                final int page = this._pc.get_other().get_page() + 1;
                this.showPage(page);
            }
            else if (cmd.startsWith("K")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(1, xcmd);
            }
            else if (cmd.startsWith("D")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(2, xcmd);
            }
            else if (cmd.startsWith("M")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(3, xcmd);
            }
            else if (cmd.startsWith("T")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(4, xcmd);
            }
            else if (cmd.startsWith("C")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(5, xcmd);
            }
            else if (cmd.startsWith("W")) {
                final int xcmd = Integer.parseInt(cmd.substring(1));
                this.startCmd(6, xcmd);
            }
            else if (cmd.startsWith("de")) {
                final int xcmd = Integer.parseInt(cmd.substring(2));
                this.action_to_de(xcmd);
            }
        }
        catch (Exception e) {
            GmHtml._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    public void action_to_de(final int cmd) {
        try {
            final L1DeInstance de = this._pc.get_outChat();
            if (de != null) {
                int petid = -1;
                int count = -1;
                int dollid = -1;
                int heading = -1;
                int polyid = -1;
                boolean move = false;
                if (cmd >= 1 && cmd <= 9) {
                    switch (de.getClassId()) {
                        case 734:
                        case 1186: {
    break;
}
default: {
    this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "不是法師"));
    return;
}
}
if (cmd >= 10 && cmd <= 17) {
    switch (de.getClassId()) {
        case 37:
        case 138: {
            break;
        }
        default: {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "不是精靈"));
            return;
        }
    }
}
switch (cmd) {
    case 24:
    case 32: {
        if (de.isShop()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "正在商店模式"));
            return;
        }
        if (de.isFishing()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "已經在釣魚了"));
            return;
        }
        if (cmd == 24) {
            de.start_fishing();
        }
        else if (cmd == 32) {
            de.start_fishingAI();
        }
        return;
    }
    case 33: {
        if (de.isShop()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "正在商店模式"));
            return;
        }
        if (!de.isFishing()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "並沒有在釣魚"));
            return;
        }
        de.stop_fishing();
        return;
    }
    case 25: {
        switch (de.getMapId()) {
            case 800: {
                if (de.isShop()) {
                    this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "已經在商店模式"));
                    return;
                }
                de.start_shop();
                return;
            }
            default: {
                this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "這裡不是商店村"));
                return;
            }
        }
    }
    case 26: {
        if (de.isFishing()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "正在釣魚模式"));
            return;
        }
        if (!de.isShop()) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "並沒有在商店模式"));
            return;
        }
        de.stop_shop();
        return;
    }
    case 27:
    case 28: {
        if (de.get_chat() != null) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "正在自動喊話"));
            return;
        }
        final String chat = DeGlobalChatTable.get().getChat();
        de.set_chat(chat, cmd);
        return;
    }
    case 43: {
        if (de.get_chat() == null) {
            this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(de.getNameId()) + "並沒有在自動喊話"));
            return;
        }
                        de.set_chat(null, 0);
                        return;
                    }
                    case 29: {
                        de.broadcastPacketAll(new S_SkillSound(de.getId(), 169));
                        de.deleteMe();
                        this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                        return;
                    }
                    case 30: {
                        de.deleteMe();
                        this._pc.sendPackets(new S_CloseList(this._pc.getId()));
                        return;
                    }
                    case 1: {
                        petid = 81239;
                        count = 1;
                        break;
                    }
                    case 2: {
                        petid = 81240;
                        count = 1;
                        break;
                    }
                    case 3: {
                        petid = 81228;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 4: {
                        petid = 81231;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 5: {
                        petid = 81236;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 6: {
                        petid = 81238;
                        count = 1;
                        break;
                    }
                    case 7: {
                        petid = 81227;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 8: {
                        petid = 81226;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 9: {
                        petid = 81237;
                        count = GmHtml._random.nextInt(3) + 1;
                        break;
                    }
                    case 10: {
                        petid = 81053;
                        count = 1;
                        break;
                    }
                    case 11: {
                        petid = 81050;
                        count = 1;
                        break;
                    }
                    case 12: {
                        petid = 81051;
                        count = 1;
                        break;
                    }
                    case 13: {
                        petid = 81052;
                        count = 1;
                        break;
                    }
                    case 14: {
                        petid = 45306;
                        count = 1;
                        break;
                    }
                    case 15: {
                        petid = 45303;
                        count = 1;
                        break;
                    }
                    case 16: {
                        petid = 45304;
                        count = 1;
                        break;
                    }
                    case 17: {
                        petid = 45305;
                        count = 1;
                        break;
                    }
                    case 55: {
                        petid = 100060;
                        count = 3;
                        break;
                    }
                    case 56: {
                        petid = 100061;
                        count = 3;
                        break;
                    }
                    case 57: {
                        petid = 100062;
                        count = 3;
                        break;
                    }
                    case 58: {
                        petid = 100063;
                        count = 3;
                        break;
                    }
                    case 59: {
                        petid = 100064;
                        count = 3;
                        break;
                    }
                    case 60: {
                        petid = 100080;
                        count = 3;
                        break;
                    }
                    case 61: {
                        petid = 100081;
                        count = 3;
                        break;
                    }
                    case 62: {
                        petid = 100082;
                        count = 3;
                        break;
                    }
                    case 63: {
                        petid = 100083;
                        count = 3;
                        break;
                    }
                    case 64: {
                        petid = 100084;
                        count = 3;
                        break;
                    }
                    case 18: {
                        dollid = 55000;
                        break;
                    }
                    case 19: {
                        dollid = 55001;
                        break;
                    }
                    case 20: {
                        dollid = 55002;
                        break;
                    }
                    case 21: {
                        dollid = 55011;
                        break;
                    }
                    case 22: {
                        dollid = 55013;
                        break;
                    }
                    case 23: {
                        final Object[] dolls = DollPowerTable.get().map().keySet().toArray();
                        final Object doll = dolls[GmHtml._random.nextInt(dolls.length)];
                        dollid = (int)doll;
                        break;
                    }
                    case 31: {
                        final int[] polyids = { 6137, 6140 };
                        polyid = polyids[GmHtml._random.nextInt(polyids.length)];
                        break;
                    }
                    case 50: {
                        final int[] polyids2 = { 8817, 8900, 8851, 8774 };
                        polyid = polyids2[GmHtml._random.nextInt(polyids2.length)];
                        break;
                    }
                    case 51: {
                        polyid = 9206;
                        break;
                    }
                    case 52: {
                        polyid = 9012;
                        break;
                    }
                    case 53: {
                        polyid = 8913;
                        break;
                    }
                    case 54: {
                        polyid = 9226;
                        break;
                    }
                    case 34: {
                        move = true;
                        break;
                    }
                    case 35: {
                        heading = 1;
                        break;
                    }
                    case 36: {
                        heading = 2;
                        break;
                    }
                    case 37: {
                        heading = 3;
                        break;
                    }
                    case 38: {
                        heading = 4;
                        break;
                    }
                    case 39: {
                        heading = 5;
                        break;
                    }
                    case 40: {
                        heading = 6;
                        break;
                    }
                    case 41: {
                        heading = 7;
                        break;
                    }
                    case 42: {
                        heading = 0;
                        break;
                    }
                }
                if (de.isShop()) {
                    this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(String.valueOf(String.valueOf(de.getNameId()))) + "正在商店模式"));
                    return;
                }
                if (de.isFishing()) {
                    this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(String.valueOf(String.valueOf(de.getNameId()))) + "正在釣魚模式"));
                    return;
                }
                if (move) {
                    de.getMove().setDirectionMove(de.getHeading());
                    de.setNpcSpeed();
                    return;
                }
                if (heading != -1) {
                    de.setHeading(heading);
                    de.broadcastPacketX8(new S_ChangeHeading(de));
                    return;
                }
                if (polyid != -1) {
                    L1PolyMorph.doPoly(de, polyid, 300, 1);
                    return;
                }
                if (petid != -1) {
                    if (de.getPetList().size() > 0) {
                        this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(String.valueOf(String.valueOf(de.getNameId()))) + "已經有寵物"));
                        return;
                    }
                    final L1Npc template = NpcTable.get().getTemplate(petid);
                    if (template != null) {
                        for (int i = 0; i < count; ++i) {
                            final L1SummonInstance summon = new L1SummonInstance(template, de);
                            summon.set_currentPetStatus(1);
                            summon.setMoveSpeed(1);
                        }
                    }
                    de.broadcastPacketX8(new S_DoActionGFX(de.getId(), 19));
                }
                else if (dollid != -1) {
                    if (!de.getDolls().isEmpty()) {
                        this._pc.sendPackets(new S_ServerMessage(166, String.valueOf(String.valueOf(String.valueOf(de.getNameId()))) + "已經有娃娃"));
                        return;
                    }
                    final L1Npc template = NpcTable.get().getTemplate(71082);
                    if (template != null) {
                        final L1Doll type = DollPowerTable.get().get_type(dollid);
                        final L1DollInstance l1DollInstance = new L1DollInstance(template, de, type);
                        l1DollInstance.setNpcMoveSpeed();
                    }
                }
            }
        }
    }
        catch (Exception e) {
            GmHtml._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void startCmd(final int mode, final int cmd) {
        try {
            int page = this._pc.get_other().get_page();
            if (page > 0) {
                page *= 50;
            }
            final int xcmd = page + cmd;
            boolean isDe = false;
            if (this._mode == 0) {
                switch (mode) {
                    case 1: {
                        final L1PcInstance target_pc1 = this._allPcList.get(xcmd);
                        this._pc.sendPackets(new S_ServerMessage(String.valueOf(String.valueOf(String.valueOf(target_pc1.getName()))) + " 踢除下線。"));
                        final L1PcInstance target_pcX1 = World.get().getPlayer(target_pc1.getName());
                        if (target_pcX1 != null) {
                            target_pcX1.getNetConnection().kick();
                            break;
                        }
                        break;
                    }
                    case 2: {
                        final L1PcInstance target_pc2 = this._allPcList.get(xcmd);
                        IpReading.get().add(target_pc2.getAccountName(), "GM命令:L1AccountBanKick 封鎖帳號");
                        this._pc.sendPackets(new S_ServerMessage(String.valueOf(String.valueOf(String.valueOf(target_pc2.getName()))) + " 帳號封鎖。"));
                        final L1PcInstance target_pcX2 = World.get().getPlayer(target_pc2.getName());
                        if (target_pcX2 != null) {
                            target_pcX2.getNetConnection().kick();
                            break;
                        }
                        break;
                    }
                    case 3: {
                        final L1PcInstance target_pc3 = this._allPcList.get(xcmd);
                        final L1PcInstance target_pcX3 = World.get().getPlayer(target_pc3.getName());
                        if (target_pcX3 != null) {
                            IpReading.get().add(target_pcX3.getIp(), "GM命令:L1PowerKick 封鎖IP");
                            this._pc.sendPackets(new S_ServerMessage(String.valueOf(String.valueOf(String.valueOf(target_pcX3.getName()))) + " 封鎖IP。"));
                            target_pcX3.getNetConnection().kick();
                            break;
                        }
                        IpReading.get().add(target_pc3.getAccountName(), "GM命令:L1AccountBanKick 封鎖帳號");
                        this._pc.sendPackets(new S_ServerMessage(String.valueOf(String.valueOf(String.valueOf(target_pc3.getName()))) + " (離線)帳號封鎖。"));
                        break;
                    }
                    case 4: {
                        final L1PcInstance target_pc4 = this._allPcList.get(xcmd);
                        final L1PcInstance target_pcX4 = World.get().getPlayer(target_pc4.getName());
                        if (target_pcX4 != null) {
                            final L1Location loc = L1Location.randomLocation(target_pcX4.getLocation(), 1, 2, false);
                            L1Teleport.teleport(this._pc, loc.getX(), loc.getY(), target_pcX4.getMapId(), this._pc.getHeading(), false);
                            this._pc.sendPackets(new S_ServerMessage("移動座標至指定人物身邊: " + target_pcX4.getName()));
                            final int gmHtml = this._pc.get_other().get_page();
                            this.showPage(gmHtml);
                            break;
                        }
                    }
                    case 5: {
                        final L1PcInstance target_pc5 = this._allPcList.get(xcmd);
                        final L1PcInstance target_pcX5 = World.get().getPlayer(target_pc5.getName());
                        if (target_pcX5 != null) {
                            L1Teleport.teleportToTargetFront(target_pcX5, this._pc, 2);
                            target_pcX5.sendPackets(new S_SystemMessage("管理者召喚。"));
                            break;
                        }
                    }
                    case 6: {
                        final L1PcInstance target_pc6 = this._allPcList.get(xcmd);
                        final L1PcInstance target_pcX6 = World.get().getPlayer(target_pc6.getName());
                        if (target_pcX6 != null && target_pcX6.getWeapon() != null) {
							
							// 解除裝備
//							target_pcX6.getInventory().setEquipped(
//							target_pcX6.getWeapon(), false, false, false); // 脫除武器
							
                            target_pcX6.getInventory().takeoffEquip(945); // 脫除全部裝備
							target_pcX6.sendPackets(new S_ServerMessage(1027)); // 裝備的武器被強制解除。
							
                            this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("玩家: " + target_pcX6.getName() + " 全部裝備已卸除"));
                            break;
                        }
                        break;
                    }
                }
            }
            else if (this._mode == 1) {
                switch (mode) {
                    case 1:
                    case 2:
                    case 3: {
                        isDe = true;
                        break;
                    }
                    case 4: {
                        final L1DeInstance target_de = this._allDeList.get(xcmd);
                        final L1Object obj = World.get().findObject(target_de.getId());
                        if (obj != null) {
                            final L1Location loc2 = L1Location.randomLocation(target_de.getLocation(), 1, 2, false);
                            L1Teleport.teleport(this._pc, loc2.getX(), loc2.getY(), target_de.getMapId(), this._pc.getHeading(), false);
                            this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("移動座標至指定虛擬人物身邊: " + target_de.getName()));
                            break;
                        }
                        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經移除虛擬人物: " + target_de.getName()));
                        break;
                    }
                }
            }
            else if (this._mode == 2) {
                switch (mode) {
                    case 1: {
                        final String banInfo = this._banList.get(xcmd);
                        IpReading.get().remove(banInfo);
                        this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("解除封鎖: " + banInfo));
                        break;
                    }
                }
            }
            if (isDe) {
                final L1DeInstance target_de = this._allDeList.get(xcmd);
                final L1Object obj = World.get().findObject(target_de.getId());
                if (obj != null) {
                    target_de.deleteMe();
                    this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("刪除虛擬人物: " + target_de.getName()));
                }
                else {
                    this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("已經移除虛擬人物: " + target_de.getName()));
                }
            }
        }
        catch (Exception e) {
            GmHtml._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    private void showPage(int page) {
        try {
            int allpage = 0;
            final StringBuilder stringBuilder = new StringBuilder();
            if (this._mode == 0) {
                allpage = (int)Math.ceil(this._allPcList.size() / 50.0);
                if (page > allpage || page < 0) {
                    page = 0;
                }
                if (this._allPcList.size() % 50 != 0) {
                    ++allpage;
                }
                this._pc.get_other().set_page(page);
                final int or = page * 50;
                stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(String.valueOf(this._users)))) + ",");
                int i = 0;
                for (final Integer key : this._allPcList.keySet()) {
                    if (i >= or && i < or + 50) {
                        final L1PcInstance tgpc = this._allPcList.get(key);
                        if (tgpc != null) {
                            stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(tgpc.getName()))) + "(" + tgpc.getAccountName() + ") PcLv:" + tgpc.getLevel() + ",");
                        }
                    }
                    ++i;
                }
            }
            else if (this._mode == 1) {
                allpage = this._allDeList.size() / 10;
                if (page > allpage || page < 0) {
                    page = 0;
                }
                if (this._allDeList.size() % 10 != 0) {
                    ++allpage;
                }
                this._pc.get_other().set_page(page);
                final int or = page * 50;
                stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(String.valueOf(this._users)))) + ",");
                int i = 0;
                for (final Integer key : this._allDeList.keySet()) {
                    if (i >= or && i < or + 10) {
                        final L1DeInstance tgde = this._allDeList.get(key);
                        if (tgde != null) {
                            stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(tgde.getNameId()))) + " DeLv:" + tgde.getLevel() + ",");
                        }
                    }
                    ++i;
                }
            }
            else if (this._mode == 2) {
                allpage = this._banList.size() / 50;
                if (page > allpage || page < 0) {
                    page = 0;
                }
                if (this._banList.size() % 50 != 0) {
                    ++allpage;
                }
                this._pc.get_other().set_page(page);
                final int or = page * 50;
                int i = 0;
                for (final Integer key : this._banList.keySet()) {
                    if (i >= or && i < or + 50) {
                        final String banIp = this._banList.get(key);
                        if (banIp != null) {
                            stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(banIp))) + ",");
                        }
                    }
                    ++i;
                }
            }
            else if (this._mode == 3) {
                allpage = this._banTmpList.size() / 50;
                if (page > allpage || page < 0) {
                    page = 0;
                }
                if (this._banTmpList.size() % 50 != 0) {
                    ++allpage;
                }
                this._pc.get_other().set_page(page);
                final int or = page * 50;
                int i = 0;
                for (final Integer key : this._banTmpList.keySet()) {
                    if (i >= or && i < or + 50) {
                        final String banIp = this._banTmpList.get(key);
                        if (banIp != null) {
                            stringBuilder.append(String.valueOf(String.valueOf(String.valueOf(banIp))) + ",");
                        }
                    }
                    ++i;
                }
            }
            if (allpage >= page + 1) {
                final String[] clientStrAry = stringBuilder.toString().split(",");
                final int length = clientStrAry.length - 1;
                if (this._mode == 2) {
                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_GmE", clientStrAry));
                }
                else if (this._mode == 3) {
                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_GmE", clientStrAry));
                }
                else if (length > 0) {
                    this._pc.sendPackets(new S_NPCTalkReturn(this._pc.getId(), "y_Gm" + length, clientStrAry));
                }
            }
            else {
                this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("沒有可以顯示的項目"));
            }
        }
        catch (Exception e) {
            GmHtml._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
}
