package com.lineage.server.serverpackets;

import java.util.List;
import java.util.Random;

import com.lineage.server.templates.L1Rank;
/**
 * 畫面中央顏色對話訊息
 * @author dexc
 *
 */
public class S_PacketBoxGree1 extends ServerBasePacket {

	private byte[] _byte = null;
	
	private static final Random _random = new Random();

	/**畫面中央顏色對話訊息*/
	private static final int GREEN_MESSAGE = 0x54;

	/**畫面特效*/
	private static final int SECRETSTORY_GFX = 0x53;
	
	/**
	 * \\fW=莓紅紫 \\fT=橄欖綠 \\fR=大便色 \\fU=土耳其藍 \\fY=暗粉紅 \\fI=鵝黃色 \\fS=綠藍色 \\fX=紫紅色
	 * \\fV=紫色
	 * 
	 * @param time
	 */
	public S_PacketBoxGree1(String msg) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(GREEN_MESSAGE);
		writeC(0x02);
		writeS(msg);
	}

	/**
	 * \\fW=莓紅紫
	 * \\fT=橄欖綠
	 * \\fR=大便色
	 * \\fU=土耳其藍
	 * \\fY=暗粉紅 
	 * \\fI=鵝黃色 
	 * \\fS=綠藍色
	 * \\fX=紫紅色 
	 * \\fV=紫色
	 * @param time
	 */
	public S_PacketBoxGree1(int type, int numbers) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(GREEN_MESSAGE);// 57
		writeC(4);// 44
		writeS("" + numbers);
	}
	
	/**
	 * 秘壇副本特殊特效(哈汀-歐林)
	 * @param type 01 畫面粉紅特效
	 * @param type 02 畫面震動
	 * @param type 03 煙火特效 
	 */
	public S_PacketBoxGree1(int type) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(SECRETSTORY_GFX);//0x53
		writeD(type);//01畫面震動 02 畫面粉紅特效 03 煙火特效
		writeC(0x00);
		writeC(0x00);
		writeC(0x00); 
	}

	public S_PacketBoxGree1(List<L1Rank> totalList, int totalSize,
			int this_order, int this_score) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(112);
		writeD(0);
		writeD(totalSize);
		for (L1Rank rank : totalList) {
			writeC(rank.getMemberSize());
			writeD(rank.getScore());
			writeS(rank.getPartyLeader());
			for (String memberName : rank.getPartyMember()) {
				writeS(memberName);
			}
		}
		writeC(this_order);
		writeD(this_score);
	}


	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return getClass().getSimpleName();
	}
}
