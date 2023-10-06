package com.lineage.data.item_etcitem;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_EffectLocation;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;

import java.util.Random;

public class Si̍p_pat
  extends ItemExecutor
{
  public static ItemExecutor get() {
    return new Si̍p_pat();
  }

  
  public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
    if (!pc.getInventory().checkItem(40318, 1L)) {
      pc.sendPackets((ServerBasePacket)new S_ServerMessage("魔法寶石不足1個"));
      return;
    } 
    Random _random = new Random();
    
    int locx = pc.getX();
    int locy = pc.getY();
    
    int a1 = _random.nextInt(6) + 1; // 1~6點
    int a2 = _random.nextInt(6) + 1;
    int a3 = _random.nextInt(6) + 1;
    int a4 = _random.nextInt(6) + 1;

    int gfx1 = 3203 + a1; // 骰子1的圖,3204為1點
    int gfx2 = 3203 + a2; // 骰子2的圖,3209為6點
    int gfx3 = 3203 + a3; // 骰子3的圖
    int gfx4 = 3203 + a4; // 骰子4的圖
    
    S_EffectLocation packet1 = new S_EffectLocation(locx - 1, locy - 1, gfx1);
    S_EffectLocation packet2 = new S_EffectLocation(locx + 1, locy - 3, gfx2);
    S_EffectLocation packet3 = new S_EffectLocation(locx + 1, locy + 1, gfx3);
    S_EffectLocation packet4 = new S_EffectLocation(locx + 3, locy - 1, gfx4);
    
    pc.sendPackets(packet1);                
    pc.sendPackets(packet2);                
    pc.sendPackets(packet3);
    pc.sendPackets(packet4);
    
    pc.broadcastPacket(packet1);
    pc.broadcastPacket(packet2);
    pc.broadcastPacket(packet3);
    pc.broadcastPacket(packet4);

    // 判斷有兩個相同的數字時，輸出另外兩個數字的和
    int sum = 0;
    if (a1 == a2 && a2 == a3) {
        // 三个数字相同，sum设为0
        sum = 0;
    } else if (a1 == a2 && a2 == a4) {
        // 三个数字相同，sum设为0
        sum = 0;
    } else if (a1 == a3 && a3 == a4) {
        // 三个数字相同，sum设为0
        sum = 0;
    } else if (a1 == a2) {
        sum = a3 + a4;
    } else if (a1 == a3) {
        sum = a2 + a4;
    } else if (a1 == a4) {
        sum = a2 + a3;
    } else if (a2 == a3) {
        sum = a1 + a4;
    } else if (a2 == a4) {
        sum = a1 + a3;
    } else if (a3 == a4) {
        sum = a1 + a2;
    }
    
    if (sum == 3) {
        pc.sendPackets(new S_ServerMessage(a1 + "、" + a2 + "、" + a3 + "、" + a4 + "，點數為[ " + sum + " ]，逼機！"));
    } else if (a1 == a2 && a1 == a3 && a1 == a4) {
        pc.sendPackets(new S_ServerMessage(a1 + "、" + a2 + "、" + a3 + "、" + a4 + "，恭喜骰到豹子！"));
    } else if (sum > 0) {
        pc.sendPackets(new S_ServerMessage(a1 + "、" + a2 + "、" + a3 + "、" + a4 + "，點數為[ " + sum + " ]！"));
    } else {
        pc.sendPackets(new S_ServerMessage(a1 + "、" + a2 + "、" + a3 + "、" + a4 + "，沒點！"));
    }

    pc.getInventory().consumeItem(40318, 1L);
  }
}