package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * 自動登入
 * @author server
 *
 */
public class C_AutoLogin extends ClientBasePacket {
	private static final Log _log = LogFactory.getLog(C_AutoLogin.class);
	private static final int AUTO_LOGIN = 6;
	private static final int MENTOR_SYSTEM = 13;

	public void start(byte[] decrypt, ClientExecutor client) {
		try {
			read(decrypt);

			int mode = readC();

			if (mode == AUTO_LOGIN) {
				String loginName = readS().toLowerCase();

				String password = readS();
				C_AuthLogin authLogin = new C_AuthLogin();
				authLogin.checkLogin(client, loginName, password, true);
				return;
			}
		} catch (Exception localException) {
			
			} finally {
				over();
			}
	}

	public String getType() {
		return getClass().getSimpleName();
	}
}