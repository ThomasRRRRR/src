package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Message_YN;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.utils.FaceToFace;

/**
 * 要求決鬥
 *
 * @author daien
 *
 */
public class C_Fight extends ClientBasePacket {

	//private static final Log _log = LogFactory.getLog(C_Fight.class);

	/*public C_Fight() {
	}

	public C_Fight(final byte[] abyte0, final ClientExecutor client) {
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
			//this.read(decrypt);

			final L1PcInstance pc = client.getActiveChar();
			if (pc.isGhost()) {
				return;
			}
			
			final L1PcInstance target = FaceToFace.faceToFace(pc);
			if (target != null) {
			
				if (pc.getFightId() != 0) {
					// 633 \f1你已經與其他人決鬥中。
					pc.sendPackets(new S_ServerMessage(633));
					return;
				}
				
				if (target.getFightId() != 0) {
					// 634 \f11對方已經與其他人決鬥中。
					target.sendPackets(new S_ServerMessage(634));
					return;
				}
				
				pc.setFightId(target.getId());
				target.setFightId(pc.getId());
				// 630 %0%s 要與你決鬥。你是否同意？(Y/N)
				target.sendPackets(new S_Message_YN(630, pc.getName()));
			
			}
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
