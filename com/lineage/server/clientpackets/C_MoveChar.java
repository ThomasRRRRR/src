package com.lineage.server.clientpackets;

import static com.lineage.server.model.Instance.L1PcInstance.REGENSTATE_MOVE;
import static com.lineage.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static com.lineage.server.model.skill.L1SkillId.MEDITATION;

import com.add.L1PcUnlock;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Map;
import com.lineage.server.serverpackets.S_NPCPack_Skin;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;
import com.lineage.server.model.Instance.L1SkinInstance;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import java.util.Iterator;
import java.util.List;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NewMaster;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.data.event.ProtectorSet;
import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.world.WorldTrap;
import com.lineage.server.model.L1Character;
import com.lineage.server.serverpackets.S_MoveCharPacket;
import com.lineage.server.datatables.DungeonRTable;
import com.lineage.server.datatables.DungeonTable;
import com.lineage.server.utils.CheckUtil;
import com.lineage.server.utils.Teleportation;
import com.lineage.server.templates.L1MapCheck;
import com.lineage.server.datatables.MapCheckTable;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import java.sql.Timestamp;
import com.lineage.server.datatables.MapTileTable;
import com.lineage.server.model.map.L1WorldMap;
import com.lineage.server.command.executor.L1MoveXY;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.types.Point;
import com.lineage.server.model.L1Trade;
import com.lineage.config.Config;
import com.lineage.echo.ClientExecutor;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import com.lineage.server.datatables.sql.SpeedTable;

/**
 * 要求角色移動
 * 基本封包長度:
 *
 * @author daien
 *
 */
public class C_MoveChar extends ClientBasePacket {
	
	private static final int CLIENT_LANGUAGE = Config.CLIENT_LANGUAGE;

	private static final Log _log = LogFactory.getLog(C_MoveChar.class);

	/*public C_MoveChar() {
	}

	public C_MoveChar(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
						this.read(decrypt);
						
						final L1PcInstance pc = client.getActiveChar();

						/*final long noetime = System.currentTimeMillis();
						if (noetime - pc.get_spr_move_time() <= SprTable.get().spr_move_speed(pc.getTempCharGfx())) {
							if (!pc.isGm()) {
								pc.getNetConnection().kick();
								return;
							}
						}
						pc.set_spr_move_time(noetime);*/
						
						if (pc == null) {
							return;
						}

						if (pc.isDead()) {// 死亡
							return;
						}

						if (pc.isTeleport()) { // 順移處理作業
							return;
						}
            if (pc.getTradeID() != 0) {
                final L1Trade trade = new L1Trade();
                trade.tradeCancel(pc);
            }
            int locx = 0;
            int locy = 0;
            int heading = 0;
            try {
                locx = this.readH();
                locy = this.readH();
                heading = this.readC();
                
				// TODO 伺服器綑綁
				if (!Config.LOGINS_TO_AUTOENTICATION) {
					if (CLIENT_LANGUAGE == 0x03) { // Taiwan Only
						heading ^= 0x49;// 換位
						locx = pc.getX();
						locy = pc.getY();
					}
				}
                
                heading = Math.min(heading, 7);
			} catch (final Exception e) {
				// 座標取回失敗
				return;
			}
			pc.killSkillEffectTimer(MEDITATION);// 解除冥想術
			pc.setCallClanId(0); // 人物移動呼喚盟友無效

			if (!pc.hasSkillEffect(ABSOLUTE_BARRIER)) { // 絕對屏障狀態
				pc.setRegenState(REGENSTATE_MOVE);
			}
            pc.getMap().setPassable(pc.getLocation(), true);
            final int oleLocx = pc.getX();
            final int oleLocy = pc.getY();
			// 移動後位置
			final int newlocx = locx + HEADING_TABLE_X[heading];
			final int newlocy = locy + HEADING_TABLE_Y[heading];
			
			try {
				// 不允許穿過該點
				boolean isError = false;
				
				// 異位判斷(封包數據 與 核心數據 不吻合)
				if ((locx != oleLocx) && (locy != oleLocy)) {
					isError = true;
				}
				
				// 商店村模式
				if (pc.isPrivateShop()) {
					isError = true;
				}
				
				// 無法攻擊/使用道具/技能/回城的狀態
				if (pc.isParalyzedX()) {
					isError = true;
				}
                final int minX = MapsTable.get().getStartX(pc.getMapId());
                final int minY = MapsTable.get().getStartY(pc.getMapId());
                final int maxX = MapsTable.get().getEndX(pc.getMapId());
                final int maxY = MapsTable.get().getEndY(pc.getMapId());
                if (newlocx < minX || newlocx > maxX || newlocy < minY || newlocy > maxY) {
                    isError = true;
                }
                if (L1MoveXY.SHUTDOWN1) {
                    final boolean isPassable = pc.getMap().isOriginalTile(newlocx, newlocy);
                    if (!isPassable) {
                        int tile = 0;
                        if (pc.getMap().isCombatZone(oleLocx, oleLocy)) {
                            tile = 47;
                        }
                        else if (pc.getMap().isNormalZone(oleLocx, oleLocy)) {
                            tile = 15;
                        }
                        else if (pc.getMap().isSafetyZone(oleLocx, oleLocy)) {
                            tile = 31;
                        }
                        L1WorldMap.get().getMap(pc.getMapId()).setTestTile(newlocx, newlocy, tile);
                        MapTileTable.get().addData(pc.getMapId(), newlocx, newlocy, tile);
                        movexy("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "玩家採點座標地圖:" + "【 " + pc.getMapId() + " 】 " + "X座標:" + "[" + newlocx + "] Y座標:[" + newlocy + "]區域(" + tile + ")" + " 】" + new Timestamp(System.currentTimeMillis()) + ")。");
                        if (pc.isGm()) {
                            pc.sendPackets(new S_ServerMessage("已解除障礙，該點設為可通行。"));
                        }
                    }
                }
                if (pc.isGm() && L1MoveXY.SHUTDOWN1) {
                    final boolean isPassable = pc.getMap().isOriginalTile(newlocx, newlocy);
                    if (!isPassable) {
                        int tile = 0;
                        if (pc.getMap().isCombatZone(oleLocx, oleLocy)) {
                            tile = 47;
                        }
                        else if (pc.getMap().isNormalZone(oleLocx, oleLocy)) {
                            tile = 15;
                        }
                        else if (pc.getMap().isSafetyZone(oleLocx, oleLocy)) {
                            tile = 31;
                        }
                        L1WorldMap.get().getMap(pc.getMapId()).setTestTile(newlocx, newlocy, tile);
                        MapTileTable.get().addData(pc.getMapId(), newlocx, newlocy, tile);
                        movexy("IP(" + (Object)pc.getNetConnection().getIp() + ")" + "玩家採點座標地圖:" + "【 " + pc.getMapId() + " 】 " + "X座標:" + "[" + newlocx + "] Y座標:[" + newlocy + "]區域(" + tile + ")" + " 】" + new Timestamp(System.currentTimeMillis()) + ")。");
                        if (pc.isGm()) {
                            pc.sendPackets(new S_ServerMessage("已解除障礙，該點設為可通行。"));
                        }
                    }
                }
                boolean isCheck = false;
                final List<L1MapCheck> check = MapCheckTable.get().getList();
                for (final L1MapCheck temp : check) {
                    if (pc.getMapId() == temp.getMapid()) {
                        if (temp.getStartX() == 0) {
                            isCheck = true;
                            break;
                        }
                        if (pc.getX() >= temp.getStartX() && pc.getX() <= temp.getEndX() && pc.getY() >= temp.getStartY() && pc.getY() <= temp.getEndY()) {
                            isCheck = true;
                            break;
                        }
                        continue;
                    }
                }
                if (isCheck) {
                    final boolean isPassable2 = pc.getMap().isOriginalTile(newlocx, newlocy);
                    if (!isPassable2) {
                        isError = true;
                    }
                }
                if (pc.checkPassable(newlocx, newlocy)) {
                    isError = true;
                }
                if (isError) {
                	//原地更新畫面
					L1PcUnlock.Pc_Unlock(pc);
                    pc.setTeleportX(pc.getOleLocX());
                    pc.setTeleportY(pc.getOleLocY());
                    pc.setTeleportMapId(pc.getMapId());
                    pc.setTeleportHeading(pc.getHeading());
                    Teleportation.teleportation(pc);
                    return;
                }
            }
            catch (Exception e) {
                C_MoveChar._log.error((Object)e.getLocalizedMessage(), (Throwable)e);
            }
			boolean isSpeed = SpeedTable.get().isSpeed(pc.getAccountName());
			if (!isSpeed) {
				final int result = pc.speed_Attack().checkInterval(AcceleratorChecker.ACT_TYPE.MOVE);
				if (result == AcceleratorChecker.R_DISCONNECTED) {
					C_MoveChar._log.error((Object)("要求角色移動:速度異常(" + pc.getName() + ")"));
				}
			}
            CheckUtil.isUserMap(pc);
            if (DungeonTable.get().dg(newlocx, newlocy, pc.getMap().getId(), pc)) {
                return;
            }
            if (DungeonRTable.get().dg(newlocx, newlocy, pc.getMap().getId(), pc)) {
                return;
            }
            pc.setOleLocX(oleLocx);
            pc.setOleLocY(oleLocy);
            pc.getLocation().set(newlocx, newlocy);
            pc.setHeading(heading);
            if (!pc.isGmInvis() && !pc.isGhost() && !pc.isInvisble()) {
                pc.broadcastPacketAll(new S_MoveCharPacket(pc));
            }
            pc.setNpcSpeed();
            this.setNpcSpeed(pc);
            pc.getMap().setPassable(pc.getLocation(), false);
            WorldTrap.get().onPlayerMoved(pc);
            if (pc.getHierarchs() != null && L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                pc.getHierarchs().deleteHierarch();
            }
            if (!ConfigOther.warProtector && pc.isProtector() && L1CastleLocation.checkInAllWarArea(pc.getX(), pc.getY(), pc.getMapId())) {
                final L1ItemInstance item = pc.getInventory().findItemId(ProtectorSet.ITEM_ID);
                if (item != null) {
                    pc.getInventory().removeItem(item, 1L);
                }
            }
            if (!ConfigOther.war_pet_summ && !pc.getPetList().isEmpty() && isInWarAreaAndWarTime(pc)) {
                Object[] array;
                for (int length = (array = pc.getPetList().values().toArray()).length, i = 0; i < length; ++i) {
                    final Object obj = array[i];
                    final L1NpcInstance petObject = (L1NpcInstance)obj;
                    if (petObject != null) {
                        if (petObject instanceof L1PetInstance) {
                            final L1PetInstance pet = (L1PetInstance)petObject;
                            pet.collect(true);
                            pc.removePet(pet);
                            pet.deleteMe();
                        }
                        if (petObject instanceof L1SummonInstance) {
                            final L1SummonInstance summon = (L1SummonInstance)petObject;
                            final S_NewMaster packet = new S_NewMaster(summon);
                            if (summon != null) {
                                if (summon.destroyed()) {
                                    return;
                                }
                                summon.Death(null);
                            }
                        }
                    }
                }
            }
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}
    
    private static boolean isInWarAreaAndWarTime(final L1PcInstance pc) {
        final int castleId = L1CastleLocation.getCastleIdByArea(pc);
        return castleId != 0 && ServerWarExecutor.get().isNowWar(castleId);
    }
    
	private void setNpcSpeed(L1PcInstance pc){
		Map<Integer, L1SkinInstance> skinList = pc.getSkins();
		if (skinList.size() > 0){
			for (Iterator<Integer> iterator = skinList.keySet().iterator(); iterator.hasNext();){
				Integer gfxid = (Integer)iterator.next();
				L1SkinInstance skin = (L1SkinInstance)skinList.get(gfxid);
				skin.setX(pc.getX());
				skin.setY(pc.getY());
				skin.setMap(pc.getMap());
				skin.setHeading(pc.getHeading());
				if (skin.getMoveType() == 0){
					L1PcInstance visiblePc;
					for (Iterator<L1PcInstance> iterator1 = World.get().getVisiblePlayer(skin).iterator(); iterator1.hasNext(); visiblePc.removeKnownObject(skin))
						visiblePc = (L1PcInstance)iterator1.next();
					skin.broadcastPacketAll(new S_NPCPack_Skin(skin));
				} else {
					skin.setNpcMoveSpeed();
					skin.broadcastPacketAll(new S_MoveCharPacket(skin));
				}
			}
		}
	}
    
    public static void 玩家穿透(final String info) {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter("./玩家紀錄/[玩家穿透紀錄].txt", true));
            out.write(String.valueOf(String.valueOf(info)) + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void movexy(final String info) {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter("\u73a9\u5bb6\u7d00\u9304/\u73a9\u5bb6\u958b\u901a\u5ea7\u6a19\u7d00\u9304.txt", true));
            out.write(String.valueOf(String.valueOf(info)) + "\r\n");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
