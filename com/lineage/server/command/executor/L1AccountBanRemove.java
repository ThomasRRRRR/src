package com.lineage.server.command.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.lock.AccountReading;
import com.lineage.server.datatables.lock.IpReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
 * 帳號解除封鎖(參數:帳號)
 *
 * @author dexc
 *
 */
public class L1AccountBanRemove implements L1CommandExecutor {

	private static final Log _log = LogFactory.getLog(L1AccountBanRemove.class);

	private L1AccountBanRemove() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1AccountBanRemove();
	}

	@Override
	public void execute(final L1PcInstance pc, final String cmdName, final String arg) {
		try {
				final boolean account = AccountReading.get().isAccount(arg);
				// 輸入資料是否為帳號
				if (!account) {
					_log.error("指令異常: 這個命令必須輸入正確帳號名稱才能執行。");
					pc.sendPackets(new S_SystemMessage("沒有此帳號。"));
					return;
				}
				if (account) {
					IpReading.get().remove(arg);
					_log.warn("系統命令執行: " + cmdName + " " + arg + " 帳號解除封鎖。");
					pc.sendPackets(new S_SystemMessage(arg + " 帳號解除封鎖。"));
					return;
				}
			
		} catch (final Exception e) {
			if (pc == null) {
				_log.error("錯誤的命令格式: " + this.getClass().getSimpleName());
				
			} else {
				_log.error("錯誤的GM指令格式: " + this.getClass().getSimpleName() + " 執行的GM:" + pc.getName());
				// 261 \f1指令錯誤。
				pc.sendPackets(new S_ServerMessage(261));
			}
		}
	}
}
