package com.lineage.data.item_etcitem;

import com.lineage.data.executor.*;
import com.lineage.server.model.Instance.*;
import com.lineage.server.serverpackets.*;

public class ItemBuff extends ItemExecutor
{
    public static ItemExecutor get() {
        return new ItemBuff();
    }
    
    @Override
    public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_npcbuff"));
    }
}
