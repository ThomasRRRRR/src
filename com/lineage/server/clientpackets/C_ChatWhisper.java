package com.lineage.server.clientpackets;

import static com.lineage.server.model.skill.L1SkillId.STATUS_CHAT_PROHIBITED;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eric.gui.J_Main;
import com.lineage.config.Config;
import com.lineage.config.ConfigAlt;

import com.lineage.config.ConfigRecord;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1DeInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ChatWhisperFrom;
import com.lineage.server.serverpackets.S_ChatWhisperTo;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldNpc;

/**
 * 要求使用密語聊天頻道
 *
 * @author daien
 *
 */
public class C_ChatWhisper extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_ChatWhisper.class);

	/*public C_ChatWhisper() {
	}

	public C_ChatWhisper(final byte[] abyte0, final ClientExecutor client) {
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

			// 來源對象
			final L1PcInstance whisperFrom = client.getActiveChar();

			// 你從現在被禁止閒談。
			if (whisperFrom.hasSkillEffect(STATUS_CHAT_PROHIBITED)) {
				whisperFrom.sendPackets(new S_ServerMessage(242));
				return;
			}

			// 等級 %0 以下無法使用密談。
			if ((whisperFrom.getLevel() < ConfigAlt.WHISPER_CHAT_LEVEL) && !whisperFrom.isGm()) {
				whisperFrom.sendPackets(new S_ServerMessage(404, String.valueOf(ConfigAlt.WHISPER_CHAT_LEVEL)));
				return;
			}

			// 取回對話內容
			final String targetName = this.readS();
			final String text = this.readS();
			if (text.length() > 52) {
				_log.warn("人物:" + whisperFrom.getName() + 
						"對話長度超過限制:" + client.getIp().toString());
				client.set_error(client.get_error() + 1);
				return;
			}

			// 目標對象
			final L1PcInstance whisperTo = World.get().getPlayer(targetName);
			L1DeInstance de = null;
			
			if (whisperTo == null) {
				de = getDe(targetName);
			}
			
			if (de != null) {
				whisperFrom.sendPackets(new S_ChatWhisperTo(de, text));
				return;
			}
			
			// \f1%0%d 不在線上。
			if (whisperTo == null) {
				whisperFrom.sendPackets(new S_ServerMessage(73, targetName));
				return;
			}

			// 對象是自己
			if (whisperTo.equals(whisperFrom)) {
				return;
			}

			// %0%s 斷絕你的密語。
			if (whisperTo.getExcludingList().contains(whisperFrom.getName())) {
				whisperFrom.sendPackets(new S_ServerMessage(117, whisperTo.getName()));
				return;
			}

			// \f1%0%d 目前關閉悄悄話。
			if (!whisperTo.isCanWhisper()) {
				whisperFrom.sendPackets(new S_ServerMessage(205, whisperTo.getName()));
				return;
			}
			if (ConfigRecord.LOGGING_CHAT_WHISPER) {
		        for (L1Object visible : World.get().getAllPlayers()) {
		        	if ((visible instanceof L1PcInstance)) {
		        		L1PcInstance GM = (L1PcInstance)visible;
		        		if ((GM.isGm()) && (whisperFrom.getId() != GM.getId())) {
		        			GM.sendPackets(new S_SystemMessage("\\fV【密】" + 
		        					whisperFrom.getName() + " -> " + targetName + ":" + text));
		        		}
		        	}
		        }
			}
	      
			if (Config.GUI) {
				J_Main.getInstance().addPrivateChat(whisperFrom.getName(), whisperTo.getName(), text);
			}

			whisperFrom.sendPackets(new S_ChatWhisperTo(whisperTo, text));
			whisperTo.sendPackets(new S_ChatWhisperFrom(whisperFrom, text));

			if (ConfigRecord.LOGGING_CHAT_WHISPER) {
				RecordTable.get().recordeTalk("密語", whisperFrom.getName(), whisperFrom.getClanname(), whisperTo.getName(), text);
			}

		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	/**
	 * 虛擬人物資料
	 * @return
	 */
	public static L1DeInstance getDe(String s) {
		final Collection<L1NpcInstance> allNpc = WorldNpc.get().all();
		// 不包含元素
		if (allNpc.isEmpty()) {
			return null;
		}
		
		for (final L1NpcInstance npc : allNpc) {
			if (npc instanceof L1DeInstance) {
				L1DeInstance de = (L1DeInstance) npc;
				//System.out.println("de:" + de.getNameId());
				if (de.getNameId().equalsIgnoreCase(s)) {
					return de;
				}
			}
		}
		return null;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
