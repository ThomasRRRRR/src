package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;

public class C_LeaveParty extends ClientBasePacket
{
  //private static final Log _log = LogFactory.getLog(C_LeaveParty.class);

  public void start(byte[] decrypt, ClientExecutor client)
  {
    try {
    	L1PcInstance pc = client.getActiveChar();

    	if ((pc.getMapId() == 9000) || (pc.getMapId() == 9101)) {
    		return;
    	}
    	if (pc.isInParty()) {
    		pc.getParty().leaveMember(pc);
    	  
    	} else {
    		pc.sendPackets(new S_ServerMessage(425));
    	}
    	
	} catch (Exception localException) {
    		
	} finally {
		over();
	}
  }

  public String getType()
  {
    return getClass().getSimpleName();
  }
}

