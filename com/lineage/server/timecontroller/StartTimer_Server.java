package com.lineage.server.timecontroller;

import com.lineage.server.timecontroller.event.T_Special;
import com.lineage.data.event.RandomGiftSet;
import com.lineage.server.timecontroller.server.ServerItemUserTimer;
import com.lineage.server.timecontroller.server.ServerSpawnMob;
import com.lineage.data.event.NowTimeSpawn;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.timecontroller.server.ServerRestartTimer;
import com.lineage.server.timecontroller.server.ServerDeleteItemTimer;
import com.lineage.server.timecontroller.server.ServerElementalStoneTimer;
import com.lineage.config.ConfigAlt;
import com.lineage.server.timecontroller.server.ServerLightTimer;
import com.lineage.server.timecontroller.server.ServerHouseTaxTimer;
import com.lineage.server.timecontroller.server.ServerAuctionTimer;
import com.lineage.server.timecontroller.server.ServerWarTimer;
import com.lineage.server.timecontroller.server.ServerTrapTimer;
import com.lineage.server.timecontroller.server.ServerGcTime;

/**
 * 服務器專用時間軸 初始化啟動
 * @author dexc
 *
 */
public class StartTimer_Server {

	public void start() throws InterruptedException {
		// 陷阱召喚處理時間軸
		final ServerTrapTimer trapTimer = new ServerTrapTimer();
		trapTimer.start();
		Thread.sleep(50);// 延遲
				
		// 村莊系統
		//ServerHomeTownTime.getInstance();
		//Thread.sleep(50);// 延遲
				
		// 城戰計時軸
		final ServerWarTimer warTimer = new ServerWarTimer();
		warTimer.start();
		Thread.sleep(50);// 延遲
				
		// 拍賣公告欄 更新計時器
		final ServerAuctionTimer auctionTimeController = new ServerAuctionTimer();
		auctionTimeController.start();
		Thread.sleep(50);// 延遲
		
		// 血盟小屋稅收計時器
		final ServerHouseTaxTimer houseTaxTimeController = new ServerHouseTaxTimer();
		houseTaxTimeController.start();
		Thread.sleep(50);// 延遲
		
		// 燈光照明計時器
		final ServerLightTimer lightTimeController = new ServerLightTimer();
		lightTimeController.start();
		Thread.sleep(50);// 延遲
		
		// 定時伺服器資源回收GC時間軸
		final ServerGcTime ServerGcTime = new ServerGcTime();
		ServerGcTime.start();
		Thread.sleep(50);// 延遲
		
		// 元素石生成 計時器
		if (ConfigAlt.ELEMENTAL_STONE_AMOUNT > 0) {
			final ServerElementalStoneTimer elementalStoneGenerator = new ServerElementalStoneTimer();
			elementalStoneGenerator.start();
			Thread.sleep(50);// 延遲
		}
		
		// 地面物品清除
		if (ConfigAlt.ALT_ITEM_DELETION_TIME > 0) {
			final ServerDeleteItemTimer deleteitem = new ServerDeleteItemTimer();
			deleteitem.start();
			Thread.sleep(50);// 延遲
		}
		
		// 自動重啟計時器
		final ServerRestartTimer autoRestart = new ServerRestartTimer();
		autoRestart.start();
		Thread.sleep(50);// 延遲
		
		// 計時地圖時間軸
		final ServerUseMapTimer useMapTimer = new ServerUseMapTimer();
		useMapTimer.start();
		Thread.sleep(50);// 延遲
        
        if (NowTimeSpawn.START) {
            final ServerSpawnMob SpawnMob = new ServerSpawnMob();
            SpawnMob.start();
            Thread.sleep(50);
        }
        
		// 物品使用期限計時時間軸
		final ServerItemUserTimer userTimer = new ServerItemUserTimer();
		userTimer.start();	
        if (RandomGiftSet.START) {
            T_Special.getStart();
        }
    }
}
