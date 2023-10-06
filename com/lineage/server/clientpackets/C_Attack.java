/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package com.lineage.server.clientpackets;

import static com.lineage.server.model.Instance.L1PcInstance.REGENSTATE_ATTACK;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.clientpackets.AcceleratorChecker.ACT_TYPE;
import com.lineage.server.datatables.sql.SpeedTable;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.L1SkillId;
import com.lineage.server.serverpackets.S_AttackPacketPc;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.world.World;

import java.util.Calendar;

/**
 * 要求角色近距离攻击<BR>
 * 类名称：C_Attack<br>
 * 创建人:四海<br>
 * 修改时间：2018年9月2日 下午11:31:34<br>
 * 修改人:QQ:403471355<br>
 * 修改备注:<br>
 * @version 2.7c<br>
 */
public class C_Attack extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_Attack.class);

	// @Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			// 資料載入
			this.read(decrypt);
			int targetId = readD();
			int x = readH();
			int y = readH();
			final L1PcInstance pc = client.getActiveChar();

			if (pc.isGhost() || pc.isDead() || pc.isTeleport()) {
				return;
			}		
			if (pc.isPrivateShop()) {
				return;
			}
			L1Object target = World.get().findObject(targetId);

			if (pc.getInventory().getWeight240() > 197) { // 重量过重
				// 110 \f1当负重过重的时候，无法战斗。
				pc.sendPackets(new S_ServerMessage(110));
				return;
			}

			if (pc.isInvisble()) {
				return;
			}

			if (pc.isInvisDelay()) {
				return;
			}
			
			if (pc.isParalyzedX()) {
                return;
            }
			
			long h_time;
            if (pc.get_weaknss() != 0 && (h_time = Calendar.getInstance().getTimeInMillis() / 1000L) - pc.get_weaknss_t() > 16L) {
                pc.set_weaknss(0, 0L);
            }

			if (target instanceof L1Character) {
				if (target.getMapId() != pc.getMapId()
						|| pc.getLocation().getLineDistance(
								target.getLocation()) > 20D) { // 攻击距离确认
					return;
				}
			}

			if (target instanceof L1NpcInstance) {
				if (((L1NpcInstance) target).getHiddenStatus() != 0) { // 空中、钻地
					return;
				}
			}

            boolean isSpeed = SpeedTable.get().isSpeed(pc.getAccountName());
            if (!isSpeed) {
            	int result = pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.ATTACK);
            	if (result == AcceleratorChecker.R_DISCONNECTED) {
            		_log.error((Object)("要求角色攻擊:速度異常(" + pc.getName() + ")"));
            	}
            }

			// 绝对屏障解除
			if (pc.hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER)) {
				pc.killSkillEffectTimer(L1SkillId.ABSOLUTE_BARRIER);
				pc.startHpRegeneration();
				pc.startMpRegeneration();
				// pc.startMpRegenerationByDoll();
			}
			pc.killSkillEffectTimer(L1SkillId.MEDITATION);

			pc.delInvis(); // 透明态の解除

			pc.setRegenState(REGENSTATE_ATTACK);

			if ((target != null)){
				if (target instanceof L1PcInstance) {
					L1PcInstance tg = (L1PcInstance) target;
					if(tg.isDead()) {
					pc.setNowTarget(tg);
					}
				}
				target.onAction(pc);
			} else { // 空攻
				int OutGfxId = -1;
				if (pc.getWeapon() != null) {
					L1ItemInstance arrow = pc.getInventory().getArrow();
					L1ItemInstance sting = pc.getInventory().getSting();
					L1ItemInstance weapon = pc.getWeapon();
					int weaponType = weapon.getItem().getType1();
					if (weaponType == 20) {
						if (arrow != null) { // 矢がある场合
							OutGfxId = 66;
							pc.getInventory().removeItem(arrow, 1);
						} else if (weapon.getItemId() == 190||weapon.getItemId() == 100190// &&weapon.getItemId()
															// ==
															// 100190//9.3活动新增
						) { // 矢が无くてサイハの场合
							OutGfxId = 2349;
						}
						pc.sendPacketsAll(new S_UseArrowSkill(pc, OutGfxId, x, y)); // 发送封包
					} else if (weaponType == 62 && sting != null) { // ガントレット
						OutGfxId = 2989;
						pc.getInventory().removeItem(sting, 1);
						pc.sendPacketsAll(new S_UseArrowSkill(pc, OutGfxId, x, y)); // 发送封包
					} else {
						pc.sendPacketsAll(new S_AttackPacketPc(pc));
					}
				}
				pc.setHeading(pc.targetDirection(x, y)); // 向きのセット
				pc.sendPackets(new S_ChangeHeading(pc));
				pc.sendPacketsAll(new S_AttackPacketPc(pc));
			}
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		} finally {
			this.over();
		}

	}
}
