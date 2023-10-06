package com.lineage.server.serverpackets;

/**
 * 選取物品數量
 * (NPC道具交換數量)
 * @author dexc
 *
 */
public class S_HowManyMake extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 選取物品數量
	 * (NPC道具交換-附加HTML)
	 * @param objId
	 * @param max
	 * @param htmlId
	 */
	public S_HowManyMake(final int objId, final int max, final String htmlId) {
		this.writeC(S_OPCODE_INPUTAMOUNT);
		this.writeD(objId);
		this.writeD(0x00000000);// ?
		this.writeD(0x00000000);// 數量初始質
		this.writeD(0x00000000);// 最低可換數量
		this.writeD(max);// 最高可換數量
		this.writeH(0x0000);// ?
		this.writeS("request");// HTML
		this.writeS(htmlId);// 命令
	}
    public S_HowManyMake(int objId, long min, long max, String action, String htmlId) {
        writeC(S_OPCODE_INPUTAMOUNT);
        writeD(objId);
        writeD(0);
        writeD(0);
        writeL(min);
        writeL(max);
        writeH(0);
        writeS(htmlId);
        writeS(action);
    }

    public S_HowManyMake(int objId, long min, long max, String action, String htmlId, String[] data) {
        writeC(S_OPCODE_INPUTAMOUNT);
        writeD(objId);
        writeD(0);
        writeD(0);
        writeL(min);
        writeL(max);
        writeH(0);
        writeS(htmlId);
        writeS(action);
        if (data != null && data.length > 0) {
            writeH(data.length);
            for (String s : data) {
                writeS(s);
            }
        }
    }
    

	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
