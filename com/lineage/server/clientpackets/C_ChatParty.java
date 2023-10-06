package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.L1ChatParty;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_Party;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;

/**
 * 要求隊伍對話控制
 *
 * @author daien
 *
 */
public class C_ChatParty extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_ChatParty.class);

	/*public C_ChatParty() {
	}

	public C_ChatParty(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);

			final L1PcInstance pc = client.getActiveChar();

			if (decrypt.length > 108) {
				_log.warn("人物:" + pc.getName() + "對話(隊伍)長度超過限制:" + client.getIp().toString());
				client.set_error(client.get_error() + 1);
				return;
			}

			if (pc.isGhost()) { // 鬼魂模式
				return;
			}
			
			if (pc.isTeleport()) { // 傳送中
				return;
			}

			final int type = this.readC();
			switch (type) {
			case 0:// /chatbanishコマンド
				final String name = this.readS();

				if (!pc.isInChatParty()) {
					// 425 您並沒有參加任何隊伍。
					pc.sendPackets(new S_ServerMessage(425));
					return;
				}
				
				if (!pc.getChatParty().isLeader(pc)) {
					// 427 只有領導者才有驅逐隊伍成員的權力。
					pc.sendPackets(new S_ServerMessage(427));
					return;
				}
				
				final L1PcInstance targetPc = World.get().getPlayer(name);
				if (targetPc == null) {
					// 109 沒有叫%0的人。
					pc.sendPackets(new S_ServerMessage(109));
					return;
				}
				
				if (pc.getId() == targetPc.getId()) {
					return;
				}

				for (final L1PcInstance member : pc.getChatParty().getMembers()) {
					if (member.getName().toLowerCase().equals(name.toLowerCase())) {
						pc.getChatParty().kickMember(member);
						return;
					}
				}
				// 426 %0%d 不屬於任何隊伍。
				pc.sendPackets(new S_ServerMessage(426, name));
				break;

			case 1: // /chatoutpartyコマンド
				if (pc.isInChatParty()) {
					pc.getChatParty().leaveMember(pc);
				}
				break;

			case 2: // /chatpartyコマンド
				final L1ChatParty chatParty = pc.getChatParty();
				if (pc.isInChatParty()) {
					pc.sendPackets(new S_Party("party", pc.getId(),
							chatParty.getLeader().getName(),
							chatParty.getMembersNameList()));
					
				} else {
					// 425 您並沒有參加任何隊伍。
					pc.sendPackets(new S_ServerMessage(425));
				}
				break;
			case 3: // 邀請聊天組隊
				final String name2 = this.readS();
				final L1PcInstance targetPc2 = World.get().getPlayer(name2);
				if (targetPc2 == null) {
					// 沒有叫%0的人。
					pc.sendPackets(new S_ServerMessage(109));
					return;
				}
				
				if (pc.getId() == targetPc2.getId()) {
					return;
				}
				
				if (targetPc2.isInChatParty()) {
					// 您無法邀請已經參加其他隊伍的人。
					pc.sendPackets(new S_ServerMessage(415));
					return;
				}

				if (pc.isInChatParty()) {
					if (pc.getChatParty().isLeader(pc)) {
						targetPc2.setPartyID(pc.getId());
						// 您要接受玩家 %0%s 提出的隊伍對話邀請嗎？(Y/N)
						targetPc2.sendPackets(new S_Message_YN(951, pc.getName()));
					
					} else {
						// 只有領導者才能邀請其他的成員。
						pc.sendPackets(new S_ServerMessage(416));
					}
					
				} else {
					targetPc2.setPartyID(pc.getId());
					// 您要接受玩家 %0%s 提出的隊伍對話邀請嗎？(Y/N)
					targetPc2.sendPackets(new S_Message_YN(951, pc.getName()));
				}
				break;
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
