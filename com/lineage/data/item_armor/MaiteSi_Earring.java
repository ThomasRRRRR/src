package com.lineage.data.item_armor;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import com.lineage.data.executor.ItemExecutor;

public class MaiteSi_Earring extends ItemExecutor
{
	private static final Log _log = LogFactory.getLog(MaiteSi_Earring.class);
    private int _uhp_number;
    
    
    public MaiteSi_Earring() {
        this._uhp_number = 0;
    }
    
    public static ItemExecutor get() {
        return new MaiteSi_Earring();
    }
    
    @Override
    public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
        try {
            if (item == null) {
                return;
            }
            if (pc == null) {
                return;
            }
            switch (data[0]) {
                case 0: {
                    pc.add_uhp_number(-this._uhp_number);
                    break;
                }
                case 1: {
                    pc.add_uhp_number(this._uhp_number);
                    break;
                }
            }
        }
        catch (Exception e) {
            MaiteSi_Earring._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
        }
    }
    
    @Override
    public void set_set(final String[] set) {
        try {
            this._uhp_number = Integer.parseInt(set[1]);
        }
        catch (Exception ex) {}
    }
}
