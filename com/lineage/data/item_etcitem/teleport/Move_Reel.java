package com.lineage.data.item_etcitem.teleport;

import static com.lineage.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static com.lineage.server.model.skill.L1SkillId.THUNDER_GRAB;

import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.CharBookReading;
import com.lineage.server.model.L1ItemDelay;
import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1BookMark;
import com.lineage.server.utils.Teleportation;

/**
 * 魔法卷軸(指定傳送)40863<br>
 * 瞬间移动卷轴 40100
 * 瞬间移动卷轴（祝福）140100
 */
public class Move_Reel extends ItemExecutor {

	/**
	 *
	 */
	private Move_Reel() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Move_Reel();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(int[] data, L1PcInstance pc, L1ItemInstance item) {
		final int map = data[0];
		int mapid = pc.getMapId();
		
		if (pc.hasSkillEffect(230)) {// 亡命之徒
			pc.sendPackets(new S_ServerMessage(1413));
			return;
		}
		if (pc.hasSkillEffect(THUNDER_GRAB)) {
			pc.sendPackets(new S_ServerMessage("\\fY身上有奪命之雷的效果無法瞬移"));
			return;
		}
		if (pc.hasSkillEffect(4000)) {
			pc.sendPackets(new S_ServerMessage("\\fY已被束縛的效果無法瞬移"));
			return ;
		}
		if (pc.getAutoX() != 0 && pc.getAutoY() != 0 && pc.isActived()) {
		    pc.sendPackets((ServerBasePacket)new S_ServerMessage("定點內掛中無法瞬移"));
			return;
		}
		
		// 所在位置 是否允許傳送
		boolean isTeleport = pc.getMap().isTeleportable();
		
		if (pc.getInventory().checkItem(84041, 1L) && mapid == 3301) {
			 isTeleport = true;
			} else if (pc.getInventory().checkItem(84042, 1L) && mapid == 3302) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84043, 1L) && mapid == 3303) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84044, 1L) && mapid == 3304) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84045, 1L) && mapid == 3305) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84046, 1L) && mapid == 3306) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84047, 1L) && mapid == 3307) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84048, 1L) && mapid == 3308) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84049, 1L) && mapid == 3309) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84050, 1L) && mapid == 3310) {
			isTeleport = true;
			} else if (pc.getInventory().checkItem(84071, 1L) && mapid >= 3301 && mapid <= 3310) {
			isTeleport = true;
			}
		
		if (!isTeleport && !pc.isGm()) {
			// 647 這附近的能量影響到瞬間移動。在此地無法使用瞬間移動。
			pc.sendPackets(new S_ServerMessage(647));
			// 解除傳送鎖定
			pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));

		}
		 else {// 可以順移
			 
			// 取出記憶座標
			final L1BookMark bookm = CharBookReading.get().getBookMark(pc, map); // 記憶座標取出
			if (bookm != null) {// 有記憶座標資料
				// 記憶座標點排序
				final int m = bookm.getMapId();
				final int x = bookm.getLocX();
				final int y = bookm.getLocY();
				// 刪除道具
				pc.getInventory().removeItem(item, 1);
				L1ItemDelay.teleportUnlock(pc, item);// 99nets團隊解除傳送卡點問題 R299
				L1Teleport.teleport(pc, x, y, (short)m, 5, true);
//				pc.sendPackets(new S_ServerMessage("000000000000"));
				} else {
					// 刪除道具
					pc.getInventory().removeItem(item, 1);
					L1ItemDelay.teleportUnlock(pc, item);// 99nets團隊解除傳送卡點問題 R299
					L1Teleport.randomTeleport(pc, true);
//					pc.sendPackets(new S_ServerMessage("1111111111"));
					
					
//			final L1BookMark bookm = CharBookReading.get().getBookMark(pc, btele); // 記憶座標取出
//			// 取出記憶座標
//			if (bookm != null) { // 有記憶座標資料
//				// 刪除道具
//				pc.getInventory().removeItem(item, 1);
//				L1Teleport.teleport(pc, bookm.getLocX(), bookm.getLocY(), bookm.getMapId(), 5, true);
//
//			} else {
//				// 刪除道具
//				pc.getInventory().removeItem(item, 1);
//
//				L1Teleport.randomTeleport(pc, true);
			}
			// 絕對屏障解除
			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
				pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
				pc.startHpRegeneration();
				pc.startMpRegeneration();
			}
			//pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false));
		}
	}
}