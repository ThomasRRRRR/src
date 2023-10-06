package com.lineage.server.serverpackets;

/**
 * 魔法效果:詛咒
 * @author dexc
 *
 */
public class S_Paralysis extends ServerBasePacket {

	private byte[] _byte = null;

	/**你的身體完全麻痺了*/
	public static final int TYPE_PARALYSIS = 0x01;// 你的身體完全麻痺了

	/**你的身體完全麻痺了*/
	public static final int TYPE_PARALYSIS2 = 0x02;// 你的身體完全麻痺了

	/**睡眠狀態*/
	public static final int TYPE_SLEEP = 0x03;// 睡眠狀態

	/**凍結狀態*/
	public static final int TYPE_FREEZE = 0x04;// 凍結狀態

	/**衝擊之暈*/
	public static final int TYPE_STUN = 0x05;// 衝擊之暈

	/**雙腳被困*/
	public static final int TYPE_BIND = 0x06;// 雙腳被困

	/**解除傳送鎖定*/
	public static final int TYPE_TELEPORT_UNLOCK = 0x07;// 解除傳送鎖定
	
	/**拘束移動*/
	public static final int TYPE_POWERGRIP = 0x08;// 拘束移動
	
	/**亡命之徒*/
	public static final int TYPE_DESPERADO = 0x09;// 亡命之徒

	/**
	 * 魔法效果:詛咒
	 * @param type 1:麻痺 2:麻痺  3:睡眠 4:凍結 5:衝暈 6:綁腳  7:傳送鎖定解除  (登入器未支援 8:拘束移動 9:亡命之徒)
	 * @param flag
	 */
	public S_Paralysis(final int type, final boolean flag) {
		this.writeC(S_OPCODE_PARALYSIS);
		switch (type) {
		case TYPE_PARALYSIS: // 你的身體完全麻痺了
			if (flag == true) {
				this.writeC(0x02);
			} else {
				this.writeC(0x03);
			}
			break;

		case TYPE_PARALYSIS2: // 你的身體完全麻痺了
			if (flag == true) {
				this.writeC(0x04);
			} else {
				this.writeC(0x05);
			}
			break;

		case TYPE_SLEEP: // 睡眠狀態
			if (flag == true) {
				this.writeC(0x0a);// this.writeC(10);
			} else {
				this.writeC(0x0b);// this.writeC(11);
			}
			break;

		case TYPE_FREEZE: // 凍結狀態
			if (flag == true) {
				this.writeC(0x0c);// this.writeC(12);
			} else {
				this.writeC(0x0d);// this.writeC(13);
			}
			break;

		case TYPE_STUN: // 衝擊之暈
			if (flag == true) {
				this.writeC(0x16);// this.writeC(22);
			} else {
				this.writeC(0x17);// this.writeC(23);
			}
			break;

		case TYPE_BIND: // 雙腳被困
			if (flag == true) {
				this.writeC(0x18);// this.writeC(24);
			} else {
				this.writeC(0x19);// this.writeC(25);
			}
			break;
			
		case TYPE_TELEPORT_UNLOCK: // 傳送鎖定解除
			if (flag == true) {
				this.writeC(0x06);
			} else {
				this.writeC(0x07);
			}
			break;
			
		case TYPE_POWERGRIP: // 拘束移動
			if (flag == true) {
				this.writeC(26);
			} else {
				this.writeC(27);
			}
			break;
			
		case TYPE_DESPERADO: // 亡命之徒
			if (flag == true) {
				this.writeC(30);
			} else {
				this.writeC(31);
			}
			break;
		}
	}
	/**
	 * 魔法效果 詛咒 癱瘓類
	 * 1.你的身體完全麻痺了
	 * 2.你的身體完全麻痺了
	 * 3.睡眠狀態
	 * 4.凍結狀態
	 * 5.衝擊之暈
	 * 6.雙腳被困
	 * 7.解除傳送鎖定
	 * @param type
	 * @param flag
	 */
	public S_Paralysis(final int type, final boolean flag, final int time) {
		writeC(S_OPCODE_PARALYSIS);
		if (type == TYPE_PARALYSIS) {// 体が完全に麻痺しました。(技能)
			if (flag == true) {
				writeC(2);
				writeH(0x0000);
				writeH(time);
			} else {
				writeC(3);
				writeH(0x0000);
				writeH(0x0000);
			}
		} else if (type == TYPE_PARALYSIS2) {// 体が完全に麻痺しました。(怪物麻痺毒)
			if (flag == true) {
				writeC(4);
				writeH(0x0000);
				writeH(time);
			} else {
				writeC(5);
				writeH(0x0000);
				writeH(0x0000);
			}
		} else if (type == TYPE_TELEPORT_UNLOCK) {// テレポート待ち状態の解除
			writeC(7);
			writeH(0x0000);
			writeH(0x0000);
		} else if (type == TYPE_SLEEP) {// 強力な睡魔が襲ってきて、寝てしまいました。
			if (flag == true) {
				writeC(10);
				writeH(0x0000);
				writeH(0x0000);
			} else {
				writeC(11);
				writeH(0x0000);
				writeH(0x0000);
			}
		} else if (type == TYPE_FREEZE)  {// 体が凍りました。
			if (flag == true) {
				writeC(12);
				writeH(0x0000);
				writeH(0x0000);
			} else {
				writeC(13);
				writeH(0x0000);
				writeH(0x0000);
			}
		} else if (type == TYPE_STUN) {// スタン状態です。
			if (flag == true) {
				writeC(22);
				writeH(0x0000);
				writeH(0x0000);
			} else {
				writeC(23);
				writeH(0x0000);
				writeH(0x0000);
			}
		} else if (type == TYPE_BIND) {// 足が縛られたように動けません。
			if (flag == true) {
				writeC(24);
				writeH(0x0000);
				writeH(0x0000);
			} else {
				writeC(25);
				writeH(0x0000);
				writeH(0x0000);
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
