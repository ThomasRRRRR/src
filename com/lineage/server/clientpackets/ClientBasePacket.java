package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;
import com.lineage.echo.ClientExecutor;

/**
 * 客戶端封包解析
 * @author DaiEn
 *
 */
public abstract class ClientBasePacket {

	private static final Log _log = LogFactory.getLog(ClientBasePacket.class);

	private static final String CLIENT_LANGUAGE_CODE = Config.CLIENT_LANGUAGE_CODE;

	private byte _decrypt[] = null;

	private int _off = 0;

	/**
	 * 執行客戶端封包處理接口
	 */
	public abstract void start(byte[] decrypt, ClientExecutor client) throws Exception;

	/*public ClientBasePacket() {

	}*/

	/*protected ClientBasePacket(final byte abyte0[]) {
		//_log.finest("type=" + getType() + ", len=" + abyte0.length);
		this._decrypt = abyte0;
		this._off = 1;
	}*/

	/**
	 * 載入BYTE陣列
	 *
	 * @param abyte0
	 */
	protected void read(final byte abyte0[]) {
		try{
			this._decrypt = abyte0;
			this._off = 1;// 忽略第一個封包(OP編組)

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 由byte[]中取回一個int
	 * 
	 * @return
	 */
	protected int readD() {
		try{
			//10: 01010
			//20: 10100
			//X=: 11110(30)
			//00010
			//10000
			if (this._decrypt == null) {
				return 0x00;
			}
			if (this._decrypt.length < this._off + 4) {
				return 0x00;
			}
			
			int i = this._decrypt[this._off++] & 0xff;
			i |= this._decrypt[this._off++] << 8 & 0xff00;
			i |= this._decrypt[this._off++] << 16 & 0xff0000;
			i |= this._decrypt[this._off++] << 24 & 0xff000000;
			
			return i;
		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return 0x00;
	}

	/**
	 * 由byte[]中取回一個byte
	 * 
	 * @return
	 */
	protected int readC() {
		try{
			if (this._decrypt == null) {
				return 0x00;
			}
			if (this._decrypt.length < this._off + 1) {
				return 0x00;
			}
			final int i = this._decrypt[this._off++] & 0xff;
			
			return i;
		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return 0x00;
	}

	/**
	 * 由byte[]中取回一個short
	 * 
	 * @return
	 */
	protected int readH() {
		try{
			if (this._decrypt == null) {
				return 0x00;
			}
			if (this._decrypt.length < this._off + 2) {
				return 0x00;
			}
			int i = this._decrypt[this._off++] & 0xff;
			i |= this._decrypt[this._off++] << 8 & 0xff00;
			
			return i;

		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return 0x00;
	}

	/**
	 * 由byte[]中取回一個short
	 * 
	 * @return
	 */
	protected int readCH() {
		try{
			if (this._decrypt == null) {
				return 0x00;
			}
			if (this._decrypt.length < this._off + 3) {
				return 0x00;
			}
			int i = this._decrypt[this._off++] & 0xff;
			i |= this._decrypt[this._off++] << 8 & 0xff00;
			i |= this._decrypt[this._off++] << 16 & 0xff0000;
			
			return i;

		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return 0x00;
	}

	/**
	 * 由byte[]中取回一個double
	 * 
	 * @return
	 */
	protected double readF() {
		try{
			if (this._decrypt == null) {
				return 0x00;
			}
			if (this._decrypt.length < this._off + 8) {
				return 0D;
			}
			long l = this._decrypt[this._off++] & 0xff;
			l |= this._decrypt[this._off++] << 8 & 0xff00;
			l |= this._decrypt[this._off++] << 16 & 0xff0000;
			l |= this._decrypt[this._off++] << 24 & 0xff000000;
			l |= (long) this._decrypt[this._off++] << 32 & 0xff00000000L;
			l |= (long) this._decrypt[this._off++] << 40 & 0xff0000000000L;
			l |= (long) this._decrypt[this._off++] << 48 & 0xff000000000000L;
			l |= (long) this._decrypt[this._off++] << 56 & 0xff00000000000000L;
			
			return Double.longBitsToDouble(l);

		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return 0D;
	}

	/**
	 * 由byte[]中取回一個String
	 * 
	 * @return
	 */
	protected String readS() {
		String s = null;
		try {
			if (this._decrypt == null) {
				return s;
			}
			s = new String(this._decrypt, this._off, this._decrypt.length - this._off,
					CLIENT_LANGUAGE_CODE);
			s = s.substring(0, s.indexOf('\0'));
			this._off += s.getBytes(CLIENT_LANGUAGE_CODE).length + 1;

		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return s;
	}

	/**
	 * 由byte[]中取回一組byte[]
	 * 
	 * @return
	 */
	protected byte[] readByte() {
	    if (_decrypt == null || _decrypt.length == 0 || _off < 0 || _off >= _decrypt.length) {
	        return new byte[0]; // 返回空陣列或適當的預設值
	    }

	    final byte[] result = new byte[_decrypt.length - _off];
		try {
			System.arraycopy(this._decrypt, this._off, result, 0, this._decrypt.length - this._off);
			this._off = this._decrypt.length;

		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		/*} catch (final NullPointerException e) {// 要求的byte內容為空
			ClientError.isPackError(_log, "OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
			
		} catch (final ArrayIndexOutOfBoundsException e) {// 非法長度
			// 要求的byte索引位置超過byte.length
			_log.error("索引位置錯誤:" + _off + "/" + _decrypt.length, e);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}*/
		return result;
	}

	protected byte[] readBytes() {
		int index = this._off, length = this._decrypt.length - this._off;
		final int endOfLine = (byte) 0xff;
		for ( ; index < length; index++) {
			if (this._decrypt[index] == endOfLine) break;
		}
		
		final byte[] result = new byte[index - this._off];
		try {
			System.arraycopy(this._decrypt, this._off, result, 0, index - this._off);
			this._off = index;
			
		} catch (final Exception e) {
			//_log.error("OpCode:" + (_decrypt[0] & 0xff) + "/" + _decrypt.length, e);
		}
		return result;
	}

	/**
	 * 結束byte[]取回
	 */
	public void over() {
		try{
			this._decrypt = null;
			this._off = 0;

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	public String getType() {
		return this.getClass().getSimpleName();
	}
}
