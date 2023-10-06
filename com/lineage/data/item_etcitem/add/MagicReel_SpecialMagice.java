package com.lineage.data.item_etcitem.add;

import com.add.PeepCard;
import com.lineage.config.ConfigOther;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1AttackList;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1WeaponSkill;
import com.lineage.server.model.skill.L1BuffUtil;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_OwnCharStatus2;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.types.Point;
import com.lineage.server.world.World;

import java.util.ArrayList;
import java.util.Random;

public class MagicReel_SpecialMagice extends ItemExecutor {
    private static final Random _random = new Random();
    private int _type;

    public static ItemExecutor get() {
        return new MagicReel_SpecialMagice();
            }
    public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
        L1Character cha;
        int i, range, dmg, j;
        ArrayList<L1Object> array_list;
        if (pc == null) {
            return;
        }
        if (item == null) {
            return;
        }


        if (pc.isInvisble() || pc.isInvisDelay()) {
            pc.sendPackets((ServerBasePacket)new S_ServerMessage(281));
            return;
        }

        L1BuffUtil.cancelAbsoluteBarrier(pc);

        switch (this._type) {
            case 1:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 4; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 6509));
                break;
                
            case 2:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 5; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 6509));
                break;
                
            case 3:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 6; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 6509));
                break;
                
            case 4:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 7; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 6509));
                break;
                
            case 5:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 8; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 6509));
                break;
                
            case 6:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 4; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 4394));
                break;
                
            case 7:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 5; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 4394));
                break;
                
            case 8:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 6; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 4394));
                break;
                
            case 9:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 7; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 4394));
                break;
                
            case 10:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                for (i = 0; i < 8; i++) {
                    cha.onAction(pc);
                }
                pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 4394));
                break;
                
            case 11:
                if (pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 2244));
					pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(pc.getId(), 2244));
                    pc.setCurrentHp(pc.getCurrentHp() + 300);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY生命力恢復了300點，並收取了5000000現金"));
                    break;
                	}
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY金幣不足500W"));
                break;
                
            case 12:
                if (pc.getInventory().checkItem(40308, 5000000L)) {
                    pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 2244));
					pc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(pc.getId(), 2244));
                    pc.setCurrentMp(pc.getCurrentMp() + 150);
                    pc.getInventory().consumeItem(40308, 5000000L);
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY魔力恢復了150點，並收取了5000000現金"));
                    break;
                	}
                	pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY金幣不足500W"));
                	break;
                	
            case 13:
                pc.setSkillEffect(8895, 15000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY環繞著氣息擊退敵人能力持續15秒"));
                break;
                
            case 14:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                if (_random.nextInt(100) < 100) {
                    if (!L1WeaponSkill.isFreeze(cha)) {
                        cha.setSkillEffect(4000, 3000);
                        cha.broadcastPacketX8((ServerBasePacket)new S_SkillSound(cha.getId(), 4184));
                        if (cha instanceof L1PcInstance) {
                            L1PcInstance targetPc = (L1PcInstance)cha;
                            targetPc.sendPackets((ServerBasePacket)new S_SkillSound(cha.getId(), 4184));
                            targetPc.sendPackets((ServerBasePacket)new S_Paralysis(6, true));
                            targetPc.setSkillEffect(4017, 3000);
                            break;
                        }
                        if (cha instanceof L1NpcInstance) {
                            L1NpcInstance targetNpc = (L1NpcInstance)cha;
                            if (!targetNpc.getNpcTemplate().is_boss()) {
                                targetNpc.setParalyzed(true);
                            }
                        }
                    }
                    break;
                }
                pc.sendPackets((ServerBasePacket)new S_ServerMessage(280));
                return;
                
            case 15:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                if (_random.nextInt(100) < 20) {
                    if (cha instanceof L1PcInstance) {
                        L1PcInstance targetPc = (L1PcInstance)cha;
                        PeepCard.TeleportPc(pc, (L1Object)targetPc);
                        break;
                    }
                    if (cha instanceof L1NpcInstance) {
                        L1NpcInstance targetNpc = (L1NpcInstance)cha;
                        PeepCard.TeleportPc(pc, (L1Object)targetNpc);
                    }
                    break;
                }
                pc.sendPackets((ServerBasePacket)new S_ServerMessage(280));
                break;
                
            case 16:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                if (_random.nextInt(100) < 30) {
                    if (cha instanceof L1PcInstance) {
                        L1PcInstance targetPc = (L1PcInstance)cha;
                        PeepCard.TakePc(pc, (L1Object)targetPc);
                        break;
                    }
                    if (cha instanceof L1NpcInstance) {
                        L1NpcInstance targetNpc = (L1NpcInstance)cha;
                        PeepCard.TakePc(pc, (L1Object)targetNpc);
                    }
                    break;
                }
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY運氣不好 沒偷到!!"));
                break;
                
            case 17:
                if (pc.hasSkillEffect(8863)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY目前逆流時間還持續著"));
                    return;
                }
                pc.setSkillEffect(8863, 1800000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY受到逆流的攻擊.強力暴發"));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 5010));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 5010));
                break;
                
            case 18:
                pc.setSkillEffect(8864, 60000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY武器附加隱毒效果持續60秒"));
                break;
                
            case 19:
                if (pc.hasSkillEffect(8865)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY該技能時間尚未消失"));
                    return;
                }
                pc.setSkillEffect(8865, 1800000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY體能似乎湧出來了"));
                pc.addMaxHp(500);
                pc.sendPackets((ServerBasePacket)new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 4848));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 4848));
                break;
                
            case 20:
                if (pc.hasSkillEffect(8866)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY該技能時間尚未消失"));
                    return;
                }
                pc.setSkillEffect(8866, 900000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY魔法增壓似乎讓我魔法增傷不少"));
                pc.addSp(2);
                pc.sendPackets((ServerBasePacket)new S_SPMR(pc));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 4601));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 4601));
                break;
                
            case 21:
                pc.setSkillEffect(8867, 4000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY強烈的防護罩保護著你"));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 5010));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 5010));
                break;
                
            case 22:
                if (pc.hasSkillEffect(8868)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                    return;
                }
                pc.setSkillEffect(8868, 900000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY敏捷祝福 環繞全身"));
				pc.addDex(4);
				pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 4899));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 4899));
                break;
                
            case 23:
                if (pc.hasSkillEffect(8869)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                    return;
                }
                pc.setSkillEffect(8869, 900000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY力量祝福 環繞全身"));
				pc.addStr(4);
				pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
				pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 4895));
				pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 4895));
                break;
                
            case 24:
                if (pc.hasSkillEffect(8870)) {
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY該技能祝福時間尚未消失"));
                    return;
                }
                pc.setSkillEffect(8870, 900000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY智力祝福 環繞全身"));
                pc.addInt(4);
                pc.sendPackets((ServerBasePacket)new S_OwnCharStatus2(pc));
                pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 4891));
                pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 4891));
                break;
                
            case 25:
                pc.setSkillEffect(8871, 5000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY魯夫的霸王氣.環繞全身"));
                break;
                
            case 26:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                if (cha instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance)cha;
                    targetPc.setSkillEffect(8872, 5000);
                    targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY敵人受到白息之光的詛咒"));
                    targetPc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY受到白息之光的逞罰無法使用物品"));
                    pc.sendPackets((ServerBasePacket)new S_SkillSound(pc.getId(), 227));
                    pc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(pc.getId(), 227));
                }
                break;
                
            case 27:
                cha = getTarget(pc, data[0]);
                if (cha == null) {
                    return;
                }
                range = 5;
                j = 150 + _random.nextInt(200) + 1;
                array_list = World.get().getVisibleObjects((L1Object)cha, 5);
                array_list.add(cha);
                if (cha instanceof L1PcInstance) {
                    for (L1Object tgobj : array_list) {
                        if (tgobj instanceof L1PcInstance) {
                            L1PcInstance tgpc = (L1PcInstance)tgobj;
                            if (tgpc.isDead()) {
                                continue;
                            }
                            if (tgpc.getClanid() == pc.getClanid() && tgpc.getClanid() != 0) {
                                continue;
                            }
                            if (tgpc.getMap().isSafetyZone((Point)tgpc.getLocation())) {
                                continue;
                            }
                            tgpc.receiveDamage((L1Character)pc, j, false, false);
                            tgpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(tgpc.getId(), 5653));
                        }
                    }
                    break;
                }
                if (cha instanceof L1NpcInstance) {
                    for (L1Object tgobj : array_list) {
                        if (tgobj instanceof L1MonsterInstance) {
                            L1MonsterInstance tgmob = (L1MonsterInstance)tgobj;
                            if (tgmob.isDead()) {
                                continue;
                            }
                            tgmob.receiveDamage((L1Character)pc, j);
                            tgmob.broadcastPacketAll((ServerBasePacket)new S_SkillSound(tgmob.getId(), 5653));
                        }
                    }
                }
                break;
                
            case 28:
                pc.setSkillEffect(8873, 5000);
                pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY重擊暈眩的能量附加在武器上了"));
                break;
                
            case 30:
                cha = getTarget(pc, data[0]);
                dmg = 200 + _random.nextInt(400) + 1;
                if (cha == null) {
                    return;
                }
                if (cha instanceof L1PcInstance) {
                    L1PcInstance targetPc = (L1PcInstance)cha;
                    pc.setCurrentHp((short)(pc.getCurrentHp() / 5));
                    targetPc.receiveDamage((L1Character)pc, dmg, false, false);
                    pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY自殺性 犧牲給予強大傷害"));
                    targetPc.sendPackets((ServerBasePacket)new S_SkillSound(targetPc.getId(), 4594));
                    targetPc.broadcastPacketX10((ServerBasePacket)new S_SkillSound(targetPc.getId(), 4594));
                }
                break;
                
            case 31:
            	
            	int skillid = 8908;
            	
                int mpConsume = 30;
    			// 7.6智力減少MP消耗量
    			Integer minusmpconsume = L1AttackList.INTMPCONSUME.get(Integer.valueOf(pc.getInt()));
    			if (minusmpconsume != null) {
    				mpConsume -= Math.round(mpConsume * minusmpconsume / 100.0D);// 四捨五入
    			}
    			
                cha = getTarget(pc, data[0]);
                if (cha == null) {
					// 處理 cha 為 null 的情況
					// 可以返回或執行其他錯誤處理邏輯
                    return;
                }

                if (!(cha instanceof L1PcInstance)) {
					// 處理 cha 不是 L1PcInstance 的情況
					// 可以返回或執行其他錯誤處理邏輯
                    return;
                }

                L1PcInstance targetPc = (L1PcInstance) cha;

                if (targetPc.isDead()) {
                    return;
                }
                
                if (!CharSkillReading.get().spellCheck(pc.getId(), 68)) {
                    pc.sendPackets(new S_SystemMessage("您尚未學習聖結界"));
                    return;
                }
                
                if (!pc.getInventory().checkItem(40318, 2)) {
                    pc.sendPackets(new S_ServerMessage("魔法寶石不足"));
                    return;
                }
                
                if (pc.isSkillDelay()) {
                    pc.sendPackets(new S_SystemMessage("技能延遲中，無法使用。"));
                    return;
                }
                
                if (pc.getClanid() != targetPc.getClanid()) {
                    pc.sendPackets(new S_ServerMessage(201));
                    return;
                }
                
                if (pc.getCurrentMp() < mpConsume) {
                	// 278 \f1因魔力不足而無法使用魔法。
    				pc.sendPackets(new S_ServerMessage(278));
                	return;
                }
         
                pc.setCurrentMp(pc.getCurrentMp() - mpConsume);
                pc.getInventory().consumeItem(40318, 2);
                L1BuffUtil.cancelAbsoluteBarrier(pc);
                L1SkillUse l1skilluse = new L1SkillUse();
                	if (targetPc == pc) {
//                		l1skilluse.handleCommands(pc, skillid, pc.getId(), 0, 0, 0, 2);
                		l1skilluse.handleCommands(pc, skillid, targetPc.getId(), targetPc.getX(), targetPc.getY(), 0, 2);
                	} else {
//                		l1skilluse.handleCommands(pc, skillid, targetPc.getId(), 0, 0, 0, 2);
                		l1skilluse.handleCommands(pc, skillid, targetPc.getId(), targetPc.getX(), targetPc.getY(), 0, 2);
                	}
                break;
            }
    }
            private final L1Character getTarget(L1PcInstance pc, int targetID) {
                L1Object target = World.get().findObject(targetID);
                if (target == null || !(target instanceof L1Character)) {
                    pc.sendPackets(new S_ServerMessage(281));
                    return null;
                }

                return (L1Character) target;
            }

            public void set_set(String[] set) {
                try {
                    this._type = Integer.parseInt(set[1]);
                } catch (Exception exception) {}
            }
 }