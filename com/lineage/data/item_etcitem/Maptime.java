package com.lineage.data.item_etcitem;

import com.lineage.data.executor.*;
import com.lineage.server.model.Instance.*;
import com.lineage.server.serverpackets.*;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class Maptime extends ItemExecutor
{
	
	private int _map = 1;
	private int _time = 0;
    private Maptime() {
    }
    
    public static ItemExecutor get() {
        return new Maptime();
    }
    /**
     * 參數設定<BR>
     * 道具CLASSNAME 
     * 第1為地圖群組編號
     * 第2為增加的秒數
     * EX:Maptime 1 3600
     * EX為增加 mapids_group ID:1 的地圖3600秒
     * 
     * 計算方式
     * 角色進入限時地圖開始讀秒
     * 讀滿mapids_group限制的時間即退出
     * 道具設定3600秒 則角色進入地圖後需在裡面超過3600秒才能使用道具
     * 角色在地圖使用時間EX:3601秒 使用道具後 -3600秒
     * 等於角色現在只在地圖1秒而已
     * @author
     *
     */
    @Override
    public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		// 例外狀況:物件為空
		if (item == null) {
			return;
		}
		// 例外狀況:人物為空
		if (pc == null) {
			return;
		}
			
		int used_time = pc.getMapsTime(_map);

		if (used_time >= _time) {
			used_time = used_time - _time;

			if (used_time < 0) {
				used_time = 0;
			}

			pc.getInventory().removeItem(item, 1);

			pc.putMapsTime(_map, used_time);
			
//			// 獲取目前時間
//            Date currentTime = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(currentTime);
//            calendar.add(Calendar.MINUTE, (_time / 60)); // 將目前時間增加_time的數值
//            Date newTime = calendar.getTime(); // 獲取新的時間
//            String formattedTime = formatTime(newTime); // 將新的時間格式化為"幾點幾分"的格式
            
			pc.sendPackets(new S_SystemMessage("成功增加地圖時間：" + _time / 60 + "分鐘"));
			} else {
			pc.sendPackets(new S_SystemMessage("地圖時間還足夠，無需增加"));
			}
		
		}
	public void set_set(String[] set) {
		try {
			_map = Integer.parseInt(set[1]);

		} catch (Exception e) {
		}
		try {
			_time = Integer.parseInt(set[2]);

		} catch (Exception e) {
		}
	}
}


	
