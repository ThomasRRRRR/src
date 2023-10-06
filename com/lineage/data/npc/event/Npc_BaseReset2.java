package com.lineage.data.npc.event;

import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.data.event.BaseResetSet;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.clientpackets.C_CreateChar;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.data.executor.NpcExecutor;

/**
 * 71251	回憶蠟燭嚮導露露
 * @author loli
 *
 */
public class Npc_BaseReset2 extends NpcExecutor {

	private static final Log _log = LogFactory.getLog(Npc_BaseReset2.class);

	private Npc_BaseReset2() {
		// TODO Auto-generated constructor stub
	}

	public static NpcExecutor get() {
		return new Npc_BaseReset2();
	}

	@Override
	public int type() {
		return 3;
	}
    
    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
        try {
            if (pc.getMeteLevel() > 0) {
            	pc.sendPackets(new S_ServerMessage("回憶蠟燭使用轉身限制0轉"));
                return;
            }
            pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "baseReset"));
        }
        catch (Exception e) {
            Npc_BaseReset2._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    @Override
	public void action(final L1PcInstance pc, final L1NpcInstance npc, final String cmd, final long amount) {
		try {
			if (cmd.equalsIgnoreCase("ent")) {// 點燃回憶蠟燭
				
				if (!pc.getInventory().checkItem(49142)) { // 回憶蠟燭
					pc.sendPackets(new S_ServerMessage(1290)); // 沒有角色初始化所需要的道具。
					return;
				}
				pc.setInCharReset(true);//設定人物正在重置
				pc.clearAllSkill();//刪除全部技能效果
				CharBuffReading.get().deleteBuff(pc);
				pc.getInventory().takeoffEquip(945);

                pc.setoldexp(pc.getExp());

				// 傳送至轉生用地圖
				L1Teleport.teleport(pc, 32737, 32789, (short) 997, 4, false);
				
				pc.sendPackets(new S_Paralysis(4, true));//設定為凍結狀態
								
				pc.setTempMaxLevel(pc.getLevel());//設定重置時最大等級為目前等級
				
				pc.setTempLevel(1);//設定重置時基礎等級
				
				pc.setTempInitPoint(0);//歸0暫時初始點數
				
				pc.setTempElixirstats(0);//歸0暫時萬能藥點數
				
				pc.setTempStr(C_CreateChar.ORIGINAL_STR[pc.getType()]);
				pc.setTempDex(C_CreateChar.ORIGINAL_DEX[pc.getType()]);
				pc.setTempCon(C_CreateChar.ORIGINAL_CON[pc.getType()]);
				pc.setTempWis(C_CreateChar.ORIGINAL_WIS[pc.getType()]);
				pc.setTempCha(C_CreateChar.ORIGINAL_CHA[pc.getType()]);
				pc.setTempInt(C_CreateChar.ORIGINAL_INT[pc.getType()]);
				
//                if (pc.isCrown()) {
//                    pc.setTempStr(13);
//                    pc.setTempDex(10);
//                    pc.setTempCon(10);
//                    pc.setTempWis(11);
//                    pc.setTempCha(13);
//                    pc.setTempInt(10);
//                }
//                else if (pc.isKnight()) {
//                    pc.setTempStr(16);
//                    pc.setTempDex(12);
//                    pc.setTempCon(14);
//                    pc.setTempWis(9);
//                    pc.setTempCha(12);
//                    pc.setTempInt(8);
//                }
//                else if (pc.isElf()) {
//                    pc.setTempStr(11);
//                    pc.setTempDex(12);
//                    pc.setTempCon(12);
//                    pc.setTempWis(12);
//                    pc.setTempCha(9);
//                    pc.setTempInt(12);
//                }
//                else if (pc.isWizard()) {
//                    pc.setTempStr(8);
//                    pc.setTempDex(7);
//                    pc.setTempCon(12);
//                    pc.setTempWis(12);
//                    pc.setTempCha(8);
//                    pc.setTempInt(12);
//                }
//                else if (pc.isDarkelf()) {
//                    pc.setTempStr(12);
//                    pc.setTempDex(15);
//                    pc.setTempCon(8);
//                    pc.setTempWis(10);
//                    pc.setTempCha(9);
//                    pc.setTempInt(11);
//                }
//                else if (pc.isDragonKnight()) {
//                    pc.setTempStr(13);
//                    pc.setTempDex(11);
//                    pc.setTempCon(14);
//                    pc.setTempWis(12);
//                    pc.setTempCha(8);
//                    pc.setTempInt(11);
//                }
//                else if (pc.isIllusionist()) {
//                    pc.setTempStr(11);
//                    pc.setTempDex(10);
//                    pc.setTempCon(12);
//                    pc.setTempWis(12);
//                    pc.setTempCha(8);
//                    pc.setTempInt(12);
//                }
				pc.setExp(1L);//經驗設回1
			    pc.resetLevel();//重新計算等級
			    
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
                pc.sendPackets(new S_SPMR(pc));//魔攻魔防更新		 
				pc.sendPackets(new S_OwnCharStatus(pc));//角色資訊更新		
				pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_ER, pc.getEr()));//迴避率更新
				L1ActionPc.checkInitPower(pc);//檢查是否有未點完的初始點數
            }
        }
        catch (Exception e) {
            Npc_BaseReset2._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
}
