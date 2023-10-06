package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.templates.L1Rank;

/**
 * 畫面中央顏色對話訊息
 * 
 * @author dexc
 * 
 */
public class S_PacketBoxGree extends ServerBasePacket {

	private byte[] _byte = null;

	private static final int GREEN_MESSAGE = 0x54;

	private static final int SECRETSTORY_GFX = 0x53;

	private static final int SHOW_BOARD = 112;

	/**
	 * \\fW=莓紅紫 \\fT=橄欖綠 \\fR=大便色 \\fU=土耳其藍 \\fY=暗粉紅 \\fI=鵝黃色 \\fS=綠藍色 \\fX=紫紅色
	 * \\fV=紫色
	 * 
	 * @param time
	 */
	public S_PacketBoxGree(final int type, final String msg) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(GREEN_MESSAGE);
		writeC(type);
		writeS(msg);
	}

	public S_PacketBoxGree(int type) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(SECRETSTORY_GFX);
		writeD(type);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
	}

	public S_PacketBoxGree(List<L1Rank> totalList, int totalSize,
			int this_order, int this_score) {
		writeC(S_OPCODE_PACKETBOX);
		writeC(SHOW_BOARD);
		writeD(0x00000000); // ???
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
