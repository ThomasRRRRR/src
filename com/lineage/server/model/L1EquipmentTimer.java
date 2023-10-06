package com.lineage.server.model;

import java.util.TimerTask;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ServerMessage;

/**
 * 計時物件使用時間軸
 * @author dexc
 *
 */
public class L1EquipmentTimer extends TimerTask {

	private final L1PcInstance _pc;// 擁有者
	
	private final L1ItemInstance _item;// 計時物件

	public L1EquipmentTimer(final L1PcInstance pc, final L1ItemInstance item) {
		this._pc = pc;
		this._item = item;
	}

	@Override
	public void run() {
		if ((this._item.getRemainingTime() - 1) >= 0) {//還有剩餘時間
			this._item.setRemainingTime(this._item.getRemainingTime() - 1);
			this._pc.getInventory().updateItem(this._item, L1PcInventory.COL_REMAINING_TIME);
			if (this._pc.getOnlineStatus() == 0) {
				this.cancel();
			}
			
		} else {//使用時間已結束
			//this._pc.getInventory().removeItem(this._item, 1);			
			_pc.sendPackets(new S_ServerMessage(_item.getItem().getNameId() + "的使用時間已結束。"));
			if (_item.isEquipped()) {//裝備中道具
				_pc.getInventory().setEquipped(_item, false);//解除裝備
			}
			if (_pc.getDoll(_item.getId()) != null) {//使用中娃娃				
				_pc.getDoll(_item.getId()).deleteDoll();// 娃娃收回			
			}
			this.cancel();
		}
	}
}
