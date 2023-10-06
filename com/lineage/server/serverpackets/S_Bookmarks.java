package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.templates.L1BookMark;

/**
 * 角色座標名單
 * @author dexc
 *
 */
public class S_Bookmarks extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 角色座標名單
	 * @param name
	 * @param map
	 * @param id
	 */
	public S_Bookmarks(final String name, final int map, final int x, final int y, final int id) {
		this.buildPacket(name, map, x, y, id);
	}

	private void buildPacket(final String name, final int map, final int x, final int y, final int id) {
		//0000: 0d 30 30 30 31 00 04 00 3e 82 3f 80 d8 26 4c b8    .0001...>.?..&L.
		this.writeC(S_OPCODE_BOOKMARKS);
	    writeS(name);
	    writeH(map);
	    writeD(id);
	    writeD(id);
	    //writeH(x);
	    //writeH(y);	    
	}

	/**
	 * 角色重登載入
	 * @param data
	 * @param maxSize
	 * @param bookList
	 */
	public S_Bookmarks(final byte[] data, final int maxSize,
			final List<L1BookMark> bookList) {
		this.writeC(S_OPCODE_CHARRESET);
		this.writeC(0x2a);
		if (data != null) {
			this.writeH(data.length);
			for (final int value : data) {
				this.writeC(value);
			}
		} else {
			this.writeH(128);
			for (int i = 0; i < 128; i++) {
				this.writeC(0x00);
			}
		}
		this.writeH(maxSize);
		this.writeH(bookList.size());
		for (final L1BookMark book : bookList) {		
			this.writeD(book.getId());
			this.writeS(book.getName());
			this.writeH(book.getMapId());
			//this.writeH(book.getLocX());
			//this.writeH(book.getLocY());
			this.writeD(book.getId());
		}
	}

	/**
	 * 記憶欄位擴充
	 * @param value
	 */
	public S_Bookmarks(final int value) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(0x8d);
		this.writeC(value);
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
