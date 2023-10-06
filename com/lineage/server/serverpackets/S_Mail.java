/**
 *                            License
 * THE WORK (AS DEFINED BELOW) IS PROVIDED UNDER THE TERMS OF THIS  
 * CREATIVE COMMONS PUBLIC LICENSE ("CCPL" OR "LICENSE"). 
 * THE WORK IS PROTECTED BY COPYRIGHT AND/OR OTHER APPLICABLE LAW.  
 * ANY USE OF THE WORK OTHER THAN AS AUTHORIZED UNDER THIS LICENSE OR  
 * COPYRIGHT LAW IS PROHIBITED.
 * 
 * BY EXERCISING ANY RIGHTS TO THE WORK PROVIDED HERE, YOU ACCEPT AND  
 * AGREE TO BE BOUND BY THE TERMS OF THIS LICENSE. TO THE EXTENT THIS LICENSE  
 * MAY BE CONSIDERED TO BE A CONTRACT, THE LICENSOR GRANTS YOU THE RIGHTS CONTAINED 
 * HERE IN CONSIDERATION OF YOUR ACCEPTANCE OF SUCH TERMS AND CONDITIONS.
 * 
 */
package com.lineage.server.serverpackets;

import java.util.ArrayList;

import com.lineage.server.datatables.sql.MailTable;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.templates.L1Mail;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

public class S_Mail extends ServerBasePacket {

//	private static final String S_MAIL = "[S] S_Mail";

	private byte[] _byte = null;


	/**
	 * 取回信件的標題
	 * @param pc
	 * @param type
	 */
//	public S_Mail(L1PcInstance pc, int type) {
//		ArrayList<L1Mail> mails = new ArrayList<L1Mail>();
//		for (L1Mail mail : MailTable.get().getAllMail()) {
//			if(mail.getInBoxId() == pc.getId()){
//				if (mail.getType() == type) {
//					mails.add(mail);
//				}
//			}
//		}
//		
//		if (mails.isEmpty()) {
//			return;
//		}
//	
//		writeC(S_OPCODE_MAIL);
//		writeC(type);
//		writeH(mails.size());
//		
//		for (int i = 0; i < mails.size(); i++) {
//			L1Mail mail = mails.get(i);
//			writeD(mail.getId());
//			writeC(mail.getReadStatus());
//			writeD((int) (mail.getDate().getTime() / 1000));
//			writeC(mail.getSenderName().equalsIgnoreCase(pc.getName()) ? 1 : 0);
//			writeS(mail.getSenderName().equalsIgnoreCase(pc.getName()) ? mail.getReceiverName() : mail.getSenderName());
//			writeByte(mail.getSubject());
//		}
//	}
	
	/**
	 * <b>寄出信件</b>
	 * @param pc 寄出信件: 寄信人 , 寄件備份: 收信人<br>
	 * @param isDraft 是否是寄件備份 ? true:備份  , false:寄出
	 */
//	public S_Mail(L1PcInstance pc, int mailId, boolean isDraft){
//		L1Mail mail = MailTable.get().getMail(mailId);
//		writeC(S_OPCODE_MAIL);
//		writeC(0x50);
//		writeD(mailId);
//		writeC(isDraft ? 1 : 0);
//		writeS(pc.getName());
//		writeByte(mail.getSubject());
//	}

	/**
	 * 寄信結果通知
	 * @param type 信件類別 
	 * @param isDelivered 寄出:1 ,失敗:0
	 */
//	public S_Mail(int type, boolean isDelivered) {
//		writeC(S_OPCODE_MAIL);
//		writeC(type);
//		writeC(isDelivered ? 1 : 0);
//	}

	/**
	 * 刪除信件、保存到保管箱、讀取信件
	 * @param mailId
	 * @param type
	 */
//	public S_Mail(int mailId, int type) {
//		
//		switch (type) {
//		case 0x30:// 刪除一般
//		case 0x31:// 刪除血盟
//		case 0x32:// 刪除保管箱
//		case 0x40:// 一般存到保管箱	
//		//case 0x41:// 血盟存到保管箱
//			writeC(S_OPCODE_MAIL);
//			writeC(type);
//			writeD(mailId);
//			writeC(1);
//			return;
//		}
//		
//		//讀取信件
//		L1Mail mail = MailTable.get().getMail(mailId);
//		if (mail != null) {
//			writeC(S_OPCODE_MAIL);
//			writeC(type);
//			writeD(mail.getId());
//			writeByte(mail.getContent());
//		}
//	}
	
	

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

//	@Override
//	public String getType() {
//		return S_MAIL;
//	}
}
