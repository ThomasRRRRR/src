package com.lineage.server.clientpackets;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.clientpackets.ClientBasePacket;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.serverpackets.S_ChangeHeading;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_UseArrowSkill;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.world.World;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.datatables.sql.SpeedTable;

import static com.lineage.server.model.Instance.L1PcInstance.REGENSTATE_ATTACK;
import static com.lineage.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static com.lineage.server.model.skill.L1SkillId.MEDITATION;

/**
 * 要求角色攻擊(遠距離)
 *
 * @author dexc
 *
 */
public class C_AttackBow extends ClientBasePacket {
    private static final Log _log = LogFactory.getLog(C_AttackBow.class);

    @Override
    public void start(byte[] decrypt, ClientExecutor client) {
        try {
            long h_time;
            this.read(decrypt);
            L1PcInstance pc = client.getActiveChar();
            pc.setFoeSlayer(false);
            if (pc.isGhost()) {
                return;
            }
            if (pc.isDead()) {
                return;
            }
            if (pc.isTeleport()) {
                return;
            }
            if (pc.isPrivateShop()) {
                return;
            }
            if (pc.getInventory().getWeight240() >= 197) {
                pc.sendPackets(new S_ServerMessage(110));
                return;
            }
            boolean isSpeed = SpeedTable.get().isSpeed(pc.getAccountName());
            if (!isSpeed) {
            	int result = pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.ATTACK);
            	if (result == AcceleratorChecker.R_DISCONNECTED) {
            		_log.error("要求角色攻擊:速度異常(" + pc.getName() + ")");
            	}
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
            if (pc.get_weaknss() != 0 && (h_time = Calendar.getInstance().getTimeInMillis() / 1000L) - pc.get_weaknss_t() > 16L) {
                pc.set_weaknss(0, 0L);
            }
            int targetId = 0;
            int locx = 0;
            int locy = 0;
            int targetX = 0;
            int targetY = 0;
            try {
                targetId = this.readD();
                locx = this.readH();
                locy = this.readH();
            }
            catch (Exception e) {
                this.over();
                return;
            }
            if (locx == 0) {
                return;
            }
            if (locy == 0) {
                return;
            }
            targetX = locx;
            targetY = locy;
            L1Object target = World.get().findObject(targetId);
            if (target instanceof L1Character && target.getMapId() != pc.getMapId()) {
                return;
            }
            CheckUtil.isUserMap(pc);
            if (target instanceof L1NpcInstance) {
                int hiddenStatus = ((L1NpcInstance)target).getHiddenStatus();
			if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) { // 躲藏
				return;
				}
				if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY) { // 空中
				return;
				}
            }
            try {
			if (pc.hasSkillEffect(ABSOLUTE_BARRIER)) {
				pc.killSkillEffectTimer(ABSOLUTE_BARRIER);
				pc.startHpRegeneration();
				pc.startMpRegeneration();
			}

				pc.killSkillEffectTimer(MEDITATION);
				pc.delInvis(); // 透明状態の解除
				pc.setRegenState(REGENSTATE_ATTACK);
                if (target != null && !((L1Character)target).isDead()) {
                    if (target instanceof L1PcInstance) {
                        L1PcInstance tg = (L1PcInstance)target;
                        pc.setNowTarget(tg);
                    }
                    target.onAction(pc);
                } else {
                    pc.setHeading(pc.targetDirection(locx, locy));
                    L1ItemInstance weapon = pc.getWeapon();
                    if (weapon != null) {
                        int weaponType = weapon.getItem().getType1();
                        switch (weaponType) {
                            case 20: {
                                L1ItemInstance arrow = pc.getInventory().getArrow();
                                if (arrow != null) {
                                    int arrowGfxid = 66;
                                    switch (pc.getTempCharGfx()) {
                                        case 8842: 
                                        case 8900: {
                                            arrowGfxid = 8904;
                                            break;
                                        }
                                        case 8845: 
                                        case 8913: {
                                            arrowGfxid = 8916;
                                            break;
                                        }
                                        case 7959: 
                                        case 7967: 
                                        case 7968: 
                                        case 7969: 
                                        case 7970: {
                                            arrowGfxid = 7972;
                                            break;
                                        }
                                        case 13631: {
                                            arrowGfxid = 13656;
                                            break;
                                        }
                                        case 13635: {
                                            arrowGfxid = 13658;
                                        }
                                    }
                                    if (pc.getTempCharGfx() >= 13715 && pc.getTempCharGfx() <= 13745) {
                                        arrowGfxid = 11762;
                                    }
                                    this.arrowAction(pc, arrow, arrowGfxid, targetX, targetY);
                                    break;
                                }
                                if (weapon.getName().equals("$1821")) {
                                    this.arrowAction(pc, null, 2349, targetX, targetY);
                                    break;
                                }
                                this.nullAction(pc);
                                break;
                            }
                            case 62: {
                                L1ItemInstance sting = pc.getInventory().getSting();
                                if (sting != null) {
                                    int stingGfxid = 2989;
                                    switch (pc.getTempCharGfx()) {
                                        case 8842: 
                                        case 8900: {
                                            stingGfxid = 8904;
                                            break;
                                        }
                                        case 8845: 
                                        case 8913: {
                                            stingGfxid = 8916;
                                            break;
                                        }
                                        case 7959: 
                                        case 7967: 
                                        case 7968: 
                                        case 7969: 
                                        case 7970: {
                                            stingGfxid = 7972;
                                            break;
                                        }
                                        case 13631: {
                                            stingGfxid = 13656;
                                            break;
                                        }
                                        case 13635: {
                                            stingGfxid = 13658;
                                        }
                                    }
                                    if (pc.getTempCharGfx() >= 13715 && pc.getTempCharGfx() <= 13745) {
                                        stingGfxid = 11762;
                                    }
                                    this.arrowAction(pc, sting, stingGfxid, targetX, targetY);
                                    break;
                                }
                                this.nullAction(pc);
                            }
                            default: {
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception ex) {
                return;
            }
        }
        finally {
            this.over();
        }
    }

    private void arrowAction(L1PcInstance pc, L1ItemInstance item, int gfx, int targetX, int targetY) {
        pc.sendPacketsAll(new S_UseArrowSkill(pc, gfx, targetX, targetY, 1));
        if (item != null) {
            pc.getInventory().removeItem(item, 1L);
        }
    }

    private void nullAction(L1PcInstance pc) {
        int aid = 1;
        if (pc.getTempCharGfx() == 3860) {
            aid = 21;
        }
        pc.sendPacketsAll(new S_ChangeHeading(pc));
        pc.sendPacketsAll(new S_DoActionGFX(pc.getId(), aid));
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}

