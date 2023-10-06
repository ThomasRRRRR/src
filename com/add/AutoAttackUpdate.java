package com.add;

import com.lineage.config.ConfigAi;
import com.lineage.config.ConfigGuaji;
import com.lineage.server.datatables.RecordTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.*;
import com.lineage.server.templates.L1Skills;

import java.util.Calendar;
import java.util.Iterator;
import java.util.logging.Logger;

public class AutoAttackUpdate {
    private static Logger _log = Logger.getLogger(AutoAttackUpdate.class.getName());
    
    private static AutoAttackUpdate _instance;
    
    public static AutoAttackUpdate getInstance() {
        if (_instance == null) {
            _instance = new AutoAttackUpdate();
        }
        return _instance;
    }

    public boolean PcCommand(L1PcInstance _pc, String cmd) {
    	boolean hasV3 = _pc.getInventory().findItemId(92533) != null;
    	boolean hasV4 = _pc.getInventory().findItemId(92534) != null;
    	boolean hasV5 = _pc.getInventory().findItemId(92535) != null;
    	boolean hasV6 = _pc.getInventory().findItemId(92536) != null;
    	boolean hasV7 = _pc.getInventory().findItemId(92537) != null;
    	boolean hasV8 = _pc.getInventory().findItemId(92538) != null;
    	boolean hasV9 = _pc.getInventory().findItemId(92539) != null;
    	boolean hasV10 = _pc.getInventory().findItemId(92540) != null;
        
        
        if (cmd.equalsIgnoreCase("guaji_start")) {
            Calendar date = Calendar.getInstance();
            int nowHour = date.get(11);

            if (ConfigGuaji.checktimeguaji) {
                int[] GUAJI_TIME = ConfigGuaji.GUAJI_ITEM;
                for (int guajitimme : GUAJI_TIME) {
                    if (nowHour == guajitimme) {
                        _pc.sendPackets((ServerBasePacket)new S_ServerMessage("此時間未開放使用掛機"));
                        return false;
                    }
                }
            }
            
            if (ConfigGuaji.Guaji_action) {
                if (!_pc.getMap().isGuaji()) {
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("此地圖無法掛機.."));
                    return false;
                }
                
                if (!_pc.isActived()) {
                    if (_pc.hasSkillEffect(99666)) {
                        _pc.sendPackets((ServerBasePacket)new S_ServerMessage("該道具使用延遲中請稍候再執行"));
                        return false;
                    }
                    if (_pc.isParalyzed_guaji()) {
                        _pc.sendPackets((ServerBasePacket)new S_ServerMessage("負面狀態中無法開啟內掛功能"));
                        return false;
                    }
                    L1PcUnlock.Pc_Unlock(_pc);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("自動狩獵已開始。"));
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("請勿在自動狩獵點擊物品.消失物品一概不負責"));
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("掛機中:不顯示掉落物品。"));
                    RecordTable.get().guaji(_pc.getName(), "道具啟動");
                    
                    _pc.killSkillEffectTimer(6930);
                    _pc.killSkillEffectTimer(6931);
                    _pc.killSkillEffectTimer(6932);
                    if (ConfigAi.longntimeai_3) {
                        _pc.setSkillEffect(6930, 300000);
                    }
                    
                    if (_pc.getAu_AutoSet(0) > 0 && _pc.getAu_AutoSet(1) > 0) {
                        _pc.sendPackets((ServerBasePacket)new S_ServerMessage("[定點巡邏] 此座標已記錄。"));
                        _pc.setAutoX(_pc.getX());
                        _pc.setAutoY(_pc.getY());
                        _pc.setAutoMap(_pc.getMapId());
                    }
                    _pc.startAI();
                    _pc.setSkillEffect(99666, 15000);
                } else {
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("目前正在內掛中。"));
                }
            }
        } else if (cmd.equalsIgnoreCase("guaji_stop")) {
            if (_pc.isActived()) {
                _pc.setActived(false);
                _pc.sendPackets((ServerBasePacket)new S_ServerMessage(" 自動狩獵已停止。"));
                
                L1PcUnlock.Pc_Unlock(_pc);
                
                if (_pc.getAutoX() > 0) {
                    _pc.setAutoX(0);
                    _pc.setAutoY(0);
                    _pc.setAutoMap(0);
                }
                _pc.killSkillEffectTimer(8853);
                _pc.killSkillEffectTimer(6930);
                _pc.killSkillEffectTimer(6931);
                _pc.killSkillEffectTimer(6932);
                if (ConfigAi.longntimeai_3) {
                    _pc.setSkillEffect(6930, 300000);
                }
            }
        } else if (cmd.equalsIgnoreCase("Au_19")) {
            NewAutoPractice.get().ClearAutoLog(_pc.getId());
            _pc.sendPackets((ServerBasePacket)new S_CloseList(_pc.getId()));
            _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU紀錄已刪除。"));
        } else if (cmd.equalsIgnoreCase("naas0")) {
            Au_Shop(_pc);
        } else if (cmd.equalsIgnoreCase("naas1")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.IsAu_Shop()) {
                    _pc.setAu_Shop(false);
                    _pc.get_other1().set_type1(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買。"));
                } else {
                    _pc.setAu_Shop(true);
                    _pc.get_other1().set_type1(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naas2")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(0) > 0) {
                    _pc.setAu_BuyItemSwitch(0, 0);
                    _pc.get_other1().set_type2(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname1));
                } else {
                    _pc.setAu_BuyItemSwitch(0, 1);
                    _pc.get_other1().set_type2(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname1));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naas3")) {
            if (_pc.getAu_BuyItemSwitch(0) == 0) {
                _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(0);
            _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname1 + "的數量。"));
        } else if (cmd.equalsIgnoreCase("naas4")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(1) > 0) {
                    _pc.setAu_BuyItemSwitch(1, 0);
                    _pc.get_other1().set_type4(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname2 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(1, 1);
                    _pc.get_other1().set_type4(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname2 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naas5")) {
            if (_pc.getAu_BuyItemSwitch(1) == 0) {
                _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(1);
            _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname2 + "的數量。"));
        } else if (cmd.equalsIgnoreCase("naas6")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(2) > 0) {
                    _pc.setAu_BuyItemSwitch(2, 0);
                    _pc.get_other1().set_type6(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname3 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(2, 1);
                    _pc.get_other1().set_type6(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname3 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naas7")) {
            if (_pc.getAu_BuyItemSwitch(2) == 0) {
                _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(2);
            _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname3 + "的數量。"));
        } else if (cmd.equalsIgnoreCase("naas8")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(3) > 0) {
                    _pc.setAu_BuyItemSwitch(3, 0);
                    _pc.get_other1().set_type8(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname4 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(3, 1);
                    _pc.get_other1().set_type8(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname4 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naas9")) {
            if (_pc.getAu_BuyItemSwitch(3) == 0) {
                _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(3);
            _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname4 + "的數量。"));
        } else if (cmd.equalsIgnoreCase("naas10")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(4) > 0) {
                    _pc.setAu_BuyItemSwitch(4, 0);
                    _pc.get_other1().set_type10(0);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname5 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(4, 1);
                    _pc.get_other1().set_type10(1);
                    _pc.sendPackets((ServerBasePacket)new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname5 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        }
        else if (cmd.equalsIgnoreCase("naas11")) {
            if (_pc.getAu_BuyItemSwitch(4) == 0) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(4);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname5 + "的數量。"));
        }
        else if (cmd.equalsIgnoreCase("naas12")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(5) > 0) {
                    _pc.setAu_BuyItemSwitch(5, 0);
                    _pc.get_other1().set_type12(0);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname6 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(5, 1);
                    _pc.get_other1().set_type12(1);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname6 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        }
        else if (cmd.equalsIgnoreCase("naas13")) {
            if (_pc.getAu_BuyItemSwitch(5) == 0) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(5);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname6 + "的數量。"));
        }
        else if (cmd.equalsIgnoreCase("naas14")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(6) > 0) {
                    _pc.setAu_BuyItemSwitch(6, 0);
                    _pc.get_other1().set_type14(0);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname7 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(6, 1);
                    _pc.get_other1().set_type14(1);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname7 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        }
        else if (cmd.equalsIgnoreCase("naas15")) {
            if (_pc.getAu_BuyItemSwitch(6) == 0) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(6);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname7 + "的數量。"));
        }
        else if (cmd.equalsIgnoreCase("naas16")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(7) > 0) {
                    _pc.setAu_BuyItemSwitch(7, 0);
                    _pc.get_other1().set_type16(0);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname8 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(7, 1);
                    _pc.get_other1().set_type16(1);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname8 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        }
        else if (cmd.equalsIgnoreCase("naas17")) {
            if (_pc.getAu_BuyItemSwitch(7) == 0) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(7);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname8 + "的數量。"));
        }
        else if (cmd.equalsIgnoreCase("naas18")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
                if (_pc.getAu_BuyItemSwitch(8) > 0) {
                    _pc.setAu_BuyItemSwitch(8, 0);
                    _pc.get_other1().set_type18(0);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已關閉內掛自動購買" + ConfigGuaji.itemname9 + "。"));
                } else {
                    _pc.setAu_BuyItemSwitch(8, 1);
                    _pc.get_other1().set_type18(1);
                    _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU已開啟內掛自動購買" + ConfigGuaji.itemname9 + "。"));
                }
                Au_Shop(_pc);
            } else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        }
        else if (cmd.equalsIgnoreCase("naas19")) {
            if (_pc.getAu_BuyItemSwitch(8) == 0) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請先[開啟]選項自動購買"));
                return false;
            }
            _pc.setAu_SetShop(true);
            _pc.SetAu_SetShopType(8);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU請輸入你要自動購買" + ConfigGuaji.itemname9 + "的數量。"));
        }
        if (cmd.equalsIgnoreCase("naam0")) {
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam1")) {
        	if (hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {//魔法攻擊預設V5
            if (_pc.getAu_AutoSkill(2) > 0) {
                _pc.setAu_AutoSkill(2, 0);
                _pc.get_other1().set_type20(0);
            } else {
                _pc.setAu_AutoSkill(2, 1);
                _pc.get_other1().set_type20(1);
            }
            Au_MagicSet(_pc);
        	}
          	else {
          		_pc.sendPackets(new S_ServerMessage("尚未開通VIP5.."));
          	}	
        }
        else if (cmd.equalsIgnoreCase("naam21")) {
            _pc.setAu_AutoSkill(3, 5);
            _pc.get_other1().set_type21(5);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam22")) {
            _pc.setAu_AutoSkill(3, 10);
            _pc.get_other1().set_type21(10);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam23")) {
            _pc.setAu_AutoSkill(3, 20);
            _pc.get_other1().set_type21(20);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam24")) {
            _pc.setAu_AutoSkill(3, 30);
            _pc.get_other1().set_type21(30);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam25")) {
            _pc.setAu_AutoSkill(3, 40);
            _pc.get_other1().set_type21(40);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam2")) {
            _pc.setAu_AutoSkill(3, 50);
            _pc.get_other1().set_type21(50);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam3")) {
            _pc.setAu_AutoSkill(3, 60);
            _pc.get_other1().set_type21(60);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam4")) {
            _pc.setAu_AutoSkill(3, 70);
            _pc.get_other1().set_type21(70);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam5")) {
            _pc.setAu_AutoSkill(3, 80);
            _pc.get_other1().set_type21(80);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam6")) {
            _pc.setAu_AutoSkill(3, 90);
            _pc.get_other1().set_type21(90);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam7")) {
            if (_pc.getAu_AutoSkill(4) > 0) {
                _pc.setAu_AutoSkill(4, 0);
                _pc.get_other1().set_type22(0);
                _pc.setAu_AutoSkill(5, 0);
                _pc.get_other1().set_type23(0);
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("您取消[單體技能]攻擊。"));
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("請再點擊一次進行選擇。"));
                getInstance().Au_MagicSet(_pc);
                return false;
            }
            _pc.setAu_AutoSkill(0, 1);
            if (_pc.getAu_AutoSkill(1) > 0) {
                _pc.setAu_AutoSkill(1, 0);
            }
            _pc.sendPackets((ServerBasePacket) new S_SkillBuy(_pc));
        }
        else if (cmd.equalsIgnoreCase("naam8")) {
            _pc.setAu_AutoSkill(5, 1);
            _pc.get_other1().set_type23(1);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam9")) {
            _pc.setAu_AutoSkill(5, 2);
            _pc.get_other1().set_type23(2);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam10")) {
            _pc.setAu_AutoSkill(5, 3);
            _pc.get_other1().set_type23(3);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam11")) {
            _pc.setAu_AutoSkill(5, 4);
            _pc.get_other1().set_type23(4);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam12")) {
            _pc.setAu_AutoSkill(5, 5);
            _pc.get_other1().set_type23(5);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam13")) {
            if (_pc.getAu_AutoSkill(6) > 0) {
                _pc.setAu_AutoSkill(6, 0);
                _pc.get_other1().set_type24(0);
                _pc.setAu_AutoSkill(7, 0);
                _pc.get_other1().set_type25(0);
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("您取消[群體技能]攻擊。"));
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("請再點擊一次進行選擇。"));
                getInstance().Au_MagicSet(_pc);
                return false;
            }
            _pc.setAu_AutoSkill(1, 1);
            if (_pc.getAu_AutoSkill(0) > 0) {
                _pc.setAu_AutoSkill(0, 0);
            }
            _pc.sendPackets((ServerBasePacket) new S_SkillBuy(_pc));
        }
        else if (cmd.equalsIgnoreCase("naam14")) {
            _pc.setAu_AutoSkill(7, 2);
            _pc.get_other1().set_type25(2);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam15")) {
            _pc.setAu_AutoSkill(7, 3);
            _pc.get_other1().set_type25(3);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam16")) {
            _pc.setAu_AutoSkill(7, 4);
            _pc.get_other1().set_type25(4);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam17")) {
            _pc.setAu_AutoSkill(7, 5);
            _pc.get_other1().set_type25(5);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam18")) {
            _pc.setAu_AutoSkill(7, 6);
            _pc.get_other1().set_type25(6);
            Au_MagicSet(_pc);
        }
        else if (cmd.equalsIgnoreCase("naam19")) {
        	if (hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
            if (!CharSkillReading.get().spellCheck(_pc.getId(), 51)) {
                _pc.sendPackets((ServerBasePacket) new S_SystemMessage("您尚未學習召喚術"));
                return false;
            }
            if (!_pc.getInventory().checkItem(40318, 3L)) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("魔法寶石不足,無法開啟"));
                return false;
            }
            if (_pc.getsummon_skillid() == 0) {
                _pc.sendPackets((ServerBasePacket) new S_SystemMessage("請先設定要治癒的技能"));
                return false;
            }
            if (_pc.getAu_AutoSkill(8) > 0) {
                _pc.setAu_AutoSkill(8, 0);
                _pc.get_other1().set_type26(0);
            } else {
                _pc.setAu_AutoSkill(8, 1);
                _pc.get_other1().set_type26(1);
            }
            Au_MagicSet_summ(_pc);
        	}
          	else {
          		_pc.sendPackets(new S_ServerMessage("尚未開通VIP5.."));
          	}	
        }
        else if (cmd.equalsIgnoreCase("Au_44")) {
            if (_pc.getsummon_skillid() == 1) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 19)) {
                    _pc.sendPackets((ServerBasePacket) new S_SystemMessage("您尚未學習中治"));
                    return false;
                }
                _pc.setsummon_skillid(19);
                _pc.setsummon_skillidmp(15);
                _pc.get_other1().set_type27(19);
            } else if (_pc.getsummon_skillid() == 19) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 35)) {
                    _pc.sendPackets((ServerBasePacket) new S_SystemMessage("您尚未學習高治"));
                    return false;
                }
                _pc.setsummon_skillid(35);
                _pc.setsummon_skillidmp(20);
                _pc.get_other1().set_type27(35);
            } else if (_pc.getsummon_skillid() == 35) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 57)) {
                    _pc.sendPackets((ServerBasePacket) new S_SystemMessage("您尚未學習全治"));
                    _pc.sendPackets((ServerBasePacket) new S_SystemMessage("將返回初治"));
                    _pc.setsummon_skillid(1);
                    _pc.setsummon_skillidmp(4);
                    return false;
                }
                _pc.setsummon_skillid(57);
                _pc.setsummon_skillidmp(48);
                _pc.get_other1().set_type27(57);
            } else if (_pc.getsummon_skillid() == 57) {
                _pc.setsummon_skillid(1);
                _pc.setsummon_skillidmp(4);
                _pc.get_other1().set_type27(1);
            } else if (_pc.getsummon_skillid() == 0) {
                if (!CharSkillReading.get().spellCheck(_pc.getId(), 1)) {
                    _pc.sendPackets((ServerBasePacket) new S_SystemMessage("您尚未學習初治"));
                    return false;
                }
                _pc.setsummon_skillid(1);
                _pc.setsummon_skillidmp(4);
                _pc.get_other1().set_type27(1);
            }
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("Au_46")) {
            if ((!_pc.getInventory().checkEquipped(120284)) && (!_pc.getInventory().checkEquipped(20284))) {
                _pc.sendPackets(new S_SystemMessage("未裝備[召喚控制戒指]"));
                return false;
            }
            _pc.sendPackets((ServerBasePacket) new S_NPCTalkReturn(_pc.getId(), "guajisummon"));
        } else if (cmd.equalsIgnoreCase("Au_47")) {
            _pc.setsummon_skillidmp_1(30);
            _pc.get_other1().set_type28(30);
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("Au_48")) {
            _pc.setsummon_skillidmp_1(40);
            _pc.get_other1().set_type28(40);
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("Au_49")) {
            _pc.setsummon_skillidmp_1(50);
            _pc.get_other1().set_type28(50);
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("Au_50")) {
            _pc.setsummon_skillidmp_1(60);
            _pc.get_other1().set_type28(60);
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("Au_51")) {
            _pc.setsummon_skillidmp_1(70);
            _pc.get_other1().set_type28(70);
            Au_MagicSet_summ(_pc);
        } else if (cmd.equalsIgnoreCase("summ_7")) {
            _pc.setSummon_npcid("7");
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_263")) {
            _pc.setSummon_npcid("263");
            Au_MagicSet_summ(_pc);
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_519")) {
            _pc.setSummon_npcid("519");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_8")) {
            _pc.setSummon_npcid("8");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_264")) {
            _pc.setSummon_npcid("264");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_520")) {
            _pc.setSummon_npcid("520");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_9")) {
            _pc.setSummon_npcid("9");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_265")) {
            _pc.setSummon_npcid("265");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_521")) {
            _pc.setSummon_npcid("521");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_10")) {
            _pc.setSummon_npcid("10");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_266")) {
            _pc.setSummon_npcid("266");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_522")) {
            _pc.setSummon_npcid("522");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_11")) {
            _pc.setSummon_npcid("11");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_267")) {
            _pc.setSummon_npcid("267");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_523")) {
            _pc.setSummon_npcid("523");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_12")) {
            _pc.setSummon_npcid("12");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_268")) {
            _pc.setSummon_npcid("268");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_524")) {
            _pc.setSummon_npcid("524");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_13")) {
            _pc.setSummon_npcid("13");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_269")) {
            _pc.setSummon_npcid("269");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_525")) {
            _pc.setSummon_npcid("525");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_14")) {
            _pc.setSummon_npcid("14");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_270")) {
            _pc.setSummon_npcid("270");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_526")) {
            _pc.setSummon_npcid("526");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_15")) {
            _pc.setSummon_npcid("15");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_271")) {
            _pc.setSummon_npcid("271");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_527")) {
            _pc.setSummon_npcid("527");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_16")) {
            _pc.setSummon_npcid("16");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_17")) {
            _pc.setSummon_npcid("17");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_18")) {
            _pc.setSummon_npcid("18");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        } else if (cmd.equalsIgnoreCase("summ_274")) {
            _pc.setSummon_npcid("274");
            _pc.get_other1().set_type30(_pc.getSummon_npcid());
            Au_MagicSet_summ(_pc);
            _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU設定召戒召喚怪物完成。"));
        }
        else if (cmd.equalsIgnoreCase("AI4")) {
            Au_MagicSet_summ(_pc);

        } else if (cmd.equalsIgnoreCase("AI5")) {
            Au_AutoTeleport(_pc);
        }else if (cmd.equalsIgnoreCase("naaa0")) {
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa1")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU高寵狀態下禁止定點巡視。"));
                return false;
            }
            if (_pc.getAu_AutoSet(0) > 0) {
                _pc.setAu_AutoSet(0, 0);
                _pc.get_other1().set_type42(0);
                _pc.setAutoX(0);
                _pc.setAutoY(0);
                _pc.setAutoMap(0);
            } else {
                _pc.setAu_AutoSet(0, 1);
                _pc.get_other1().set_type42(1);
                _pc.setAu_AutoSet(3, 0);
                _pc.get_other1().set_type43(0);
                _pc.setAu_AutoSet(4, 0);
                _pc.get_other1().set_type45(0);
                _pc.setAu_AutoSet(5, 0);
                _pc.get_other1().set_type44(0);
            }
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa2")) {
            _pc.setAu_AutoSet(1, 5);
            _pc.get_other1().set_type41(5);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa3")) {
            _pc.setAu_AutoSet(1, 10);
            _pc.get_other1().set_type41(10);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa4")) {
            _pc.setAu_AutoSet(1, 15);
            _pc.get_other1().set_type41(15);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa5")) {
            _pc.setAu_AutoSet(1, 20);
            _pc.get_other1().set_type41(20);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa6")) {
            _pc.setAu_AutoSet(1, 25);
            _pc.get_other1().set_type41(25);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa8")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            _pc.setAu_AutoSet(3, 1);
            _pc.get_other1().set_type43(1);
            if (_pc.getAu_AutoSet(0) > 0) {
                _pc.setAu_AutoSet(0, 0);
                _pc.get_other1().set_type42(0);
            }
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa9")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            _pc.setAu_AutoSet(3, 2);
            _pc.get_other1().set_type43(2);
            if (_pc.getAu_AutoSet(0) > 0) {
                _pc.setAu_AutoSet(0, 0);
                _pc.get_other1().set_type42(0);
            }
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa10")) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU高寵狀態下禁止瞬移。"));
                return false;
            }
            _pc.setAu_AutoSet(3, 0);
            _pc.get_other1().set_type43(0);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa11")) {
        	if (hasV3 || hasV4 || hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
            if (_pc.get_followmaster() != null) {
                _pc.sendPackets((ServerBasePacket) new S_ServerMessage("\\fU高寵狀態下禁止此設定。"));
                return false;
            }
            if (_pc.getAu_AutoSet(4) > 0) {
                _pc.setAu_AutoSet(4, 0);
                _pc.get_other1().set_type45(0);
            } else {
                _pc.setAu_AutoSet(4, 1);
                _pc.get_other1().set_type45(1);
                _pc.setAu_AutoSet(0, 0);
                _pc.get_other1().set_type42(0);
                _pc.setAu_AutoSet(1, 0);
                _pc.get_other1().set_type41(0);
            }
            Au_AutoTeleport(_pc);
        	} else {
                _pc.sendPackets(new S_ServerMessage("尚未開通VIP3.."));
            }
        } else if (cmd.equalsIgnoreCase("naaa12")) {
            _pc.setAu_AutoSet(5, 2);
            _pc.get_other1().set_type44(2);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa13")) {
            _pc.setAu_AutoSet(5, 3);
            _pc.get_other1().set_type44(3);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa14")) {
            _pc.setAu_AutoSet(5, 5);
            _pc.get_other1().set_type44(5);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa15")) {
            _pc.setAu_AutoSet(5, 10);
            _pc.get_other1().set_type44(10);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa16")) {
            _pc.setAu_AutoSet(5, 15);
            _pc.get_other1().set_type44(15);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa17")) {
            _pc.setAu_AutoSet(5, 20);
            _pc.get_other1().set_type44(20);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("naaa18")) {
            _pc.setAu_AutoSet(5, 30);
            _pc.get_other1().set_type44(30);
            Au_AutoTeleport(_pc);
        } else if (cmd.equalsIgnoreCase("AI6")) {
            Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("naao1")) {
    if (_pc.getAu_OtherSet(0) > 0) {
        _pc.setAu_OtherSet(0, 0);
    } else {
        _pc.setAu_OtherSet(0, 1);
    }
    Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("naao2")) {
	if (hasV8 || hasV9 || hasV10) {
        if (_pc.getAu_OtherSet(1) > 0) {
            _pc.setAu_OtherSet(1, 0);
            _pc.get_other1().set_type46(0);
        } else {
            _pc.setAu_OtherSet(1, 1);
            _pc.get_other1().set_type46(1);
        }
        Au_OtherSet(_pc);
    } else {
        _pc.sendPackets(new S_ServerMessage("尚未開通VIP8.."));
    }
} else if (cmd.equalsIgnoreCase("naao3")) {
    EnemyList(_pc);
} else if (cmd.equalsIgnoreCase("naao4")) {
    _pc.setKeyInEnemy(true);
    _pc.sendPackets(new S_ServerMessage("\\fU請輸入玩家名稱。"));
} else if (cmd.equalsIgnoreCase("naao44")) {
    _pc.setKeyOutEnemy(true);
    _pc.sendPackets(new S_ServerMessage("\\fU請輸入清除仇人名稱。"));
} else if (cmd.equalsIgnoreCase("naao5")) {
	if (hasV8 || hasV9 || hasV10) {
        if (_pc.getAu_OtherSet(2) > 0) {
            _pc.setAu_OtherSet(2, 0);
            _pc.get_other1().set_type47(0);
        } else {
            _pc.setAu_OtherSet(2, 1);
            _pc.get_other1().set_type47(1);
        }
        Au_OtherSet(_pc);
    } else {
        _pc.sendPackets(new S_ServerMessage("尚未開通VIP8.."));
    }
} else if (cmd.equalsIgnoreCase("naao6")) {
    _pc.setAu_OtherSet(3, 0);
    Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("naao7")) {
    if (_pc.getAu_OtherSet(3) > 0) {
        _pc.setAu_OtherSet(3, 0);
        _pc.get_other1().set_type48(0);
    } else {
        _pc.setAu_OtherSet(3, 1);
        _pc.get_other1().set_type48(1);
    }
    Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("naao8")) {
	if (hasV5 || hasV6 || hasV7 || hasV8 || hasV9 || hasV10) {
        if (_pc.getAu_OtherSet(4) > 0) {
            _pc.setAu_OtherSet(4, 0);
            _pc.get_other1().set_type49(0);
        } else {
            _pc.setAu_OtherSet(4, 1);
            _pc.get_other1().set_type49(1);
        }
        Au_OtherSet(_pc);
    } else {
        _pc.sendPackets(new S_ServerMessage("尚未開通VIP5.."));
    }
} else if (cmd.equalsIgnoreCase("naao9")) {
    _pc.setAu_OtherSet(5, 0);
    Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("naao10")) {
    if (_pc.getAu_OtherSet(5) > 0) {
        _pc.setAu_OtherSet(5, 0);
        _pc.get_other1().set_type50(0);
    } else {
        _pc.get_other1().set_type50(1);
        _pc.setAu_OtherSet(5, 1);
    }
    Au_OtherSet(_pc);
} else if (cmd.equalsIgnoreCase("Au_6")) {
    NewAutoPractice.get().SearchAutoLog(_pc);
} else {
    return false;
}
return true;
}

public void EnemyList(L1PcInstance pc) {
    StringBuilder msg = new StringBuilder();
    for (String name : pc.InEnemyList()) {
        msg.append(name).append(",");
    }
    String[] clientStrAry = msg.toString().split(",");
    pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "x_autolist2", clientStrAry));
}

public void Au_MagicSet(L1PcInstance pc) {
    try {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < 11; i++) {
            switch (i) {
                case 2:
                    if (pc.getAu_AutoSkill(2) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;
                case 3:
                    stringBuilder.append("【").append(pc.getAu_AutoSkill(3)).append("】,");
                    break;
                case 4:
                    if (pc.getAu_AutoSkill(4) > 0) {
                        L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(4));
                        if (skill != null) {
                            stringBuilder.append(skill.getName()).append(",");
                        } else {
                            stringBuilder.append("【查無資料】,");
                        }
                    } else {
                        stringBuilder.append("【選擇】,");
                    }
                    break;
                case 5:
                    stringBuilder.append(pc.getAu_AutoSkill(5)).append(",");
                    break;
                case 6:
                    if (pc.getAu_AutoSkill(6) > 0) {
                        L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(6));
                        if (skill != null) {
                            stringBuilder.append(skill.getName()).append(",");
                        } else {
                            stringBuilder.append("【查無資料】,");
                        }
                    } else {
                        stringBuilder.append("【選擇】,");
                    }
                    break;
                case 7:
                    stringBuilder.append(pc.getAu_AutoSkill(7)).append(",");
                    break;
                case 8:
                    if (pc.getAu_AutoSkill(8) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;
                case 9:
                    if (pc.getsummon_skillid() == 1) {
                        stringBuilder.append("【初級治癒術】,");
                    } else if (pc.getsummon_skillid() == 19) {
                        stringBuilder.append("【中級治癒術】,");
                    } else if (pc.getsummon_skillid() == 35) {
                        stringBuilder.append("【高級治癒術】,");
                    } else if (pc.getsummon_skillid() == 57) {
                        stringBuilder.append("【全部治癒術】,");
                    } else {
                        stringBuilder.append("【尚未設定】,");
                    }
                    break;
                case 10:
                    if (pc.getsummon_skillidmp_1() > 0) {
                        stringBuilder.append("【").append(pc.getsummon_skillidmp_1()).append("】,");
                    } else {
                        stringBuilder.append("【未設】,");
                    }
                    break;
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI3", clientStrAry));
    } catch (Throwable throwable) {
    }
}

public void Au_MagicSet_summ(L1PcInstance pc) {
    try {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 2; i < 11; i++) {
            switch (i) {
                case 2:
                    if (pc.getAu_AutoSkill(2) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;
                case 3:
                    stringBuilder.append("【").append(pc.getAu_AutoSkill(3)).append("】,");
                    break;
                case 4:
                    if (pc.getAu_AutoSkill(4) > 0) {
                        L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(4));
                        if (skill != null) {
                            stringBuilder.append(skill.getName()).append(",");
                        } else {
                            stringBuilder.append("【查無資料】,");
                        }
                    } else {
                        stringBuilder.append("【選擇】,");
                    }
                    break;
                case 5:
                    stringBuilder.append(pc.getAu_AutoSkill(5)).append(",");
                    break;
                case 6:
                    if (pc.getAu_AutoSkill(6) > 0) {
                        L1Skills skill = SkillsTable.get().getTemplate(pc.getAu_AutoSkill(6));
                        if (skill != null) {
                            stringBuilder.append(skill.getName()).append(",");
                        } else {
                            stringBuilder.append("【查無資料】,");
                        }
                    } else {
                        stringBuilder.append("【選擇】,");
                    }
                    break;
                case 7:
                    stringBuilder.append(pc.getAu_AutoSkill(7)).append(",");
                    break;
                case 8:
                    if (pc.getAu_AutoSkill(8) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;
                case 9:
                    if (pc.getsummon_skillid() == 1) {
                        stringBuilder.append("【初級治癒術】,");
                    } else if (pc.getsummon_skillid() == 19) {
                        stringBuilder.append("【中級治癒術】,");
                    } else if (pc.getsummon_skillid() == 35) {
                        stringBuilder.append("【高級治癒術】,");
                    } else if (pc.getsummon_skillid() == 57) {
                        stringBuilder.append("【全部治癒術】,");
                    } else {
                        stringBuilder.append("【尚未設定】,");
                    }
                    break;
                case 10:
                    if (pc.getsummon_skillidmp_1() > 0) {
                        stringBuilder.append("【").append(pc.getsummon_skillidmp_1()).append("】,");
                    } else {
                        stringBuilder.append("【未設】,");
                    }
                    break;
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "AI4", clientStrAry));
    } catch (Throwable throwable) {
    }
}
public void Au_Shop(L1PcInstance pc) {
    try {
        StringBuilder stringBuilder = new StringBuilder();
        if (pc.IsAu_Shop()) {
            stringBuilder.append("【開啟】,");
        } else {
            stringBuilder.append("【關閉】,");
        }
        for (int i = 0; i < 9; i++) {
            switch (pc.getAu_BuyItemSwitch(i)) {
                case 0:
                    stringBuilder.append(String.valueOf(pc.getAu_BuyItemCount(i)) + ",");
                    stringBuilder.append("【關閉】,");
                    break;
                case 1:
                    stringBuilder.append(String.valueOf(pc.getAu_BuyItemCount(i)) + ",");
                    stringBuilder.append("【開啟】,");
                    break;
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "AI2", clientStrAry));
    } catch (Throwable throwable) {}
}

public boolean CheckAreaAttackSkill(int Skillid) {
    for (Iterator<Integer> iterator = ConfigGuaji.AREA_SKILL.iterator(); iterator.hasNext(); ) {
        int skillId = ((Integer)iterator.next()).intValue();
        if (skillId == Skillid) {
            return true;
        }
    }
    return false;
}

public boolean CheckAttackSkill(int Skillid) {
    for (Iterator<Integer> iterator = ConfigGuaji.USE_SKILL.iterator(); iterator.hasNext(); ) {
        int skillId = ((Integer)iterator.next()).intValue();
        if (skillId == Skillid) {
            return true;
        }
    }
    return false;
}

public void Au_AutoTeleport(L1PcInstance pc) {
    try {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            switch (i) {
                case 0:
                    if (pc.getAu_AutoSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 1:
                    stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                    break;

                case 2:
                    if (pc.getAu_AutoSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 3:
                    if (pc.getAu_AutoSet(i) == 0) {
                        stringBuilder.append("【無】,");
                    } else if (pc.getAu_AutoSet(i) == 1) {
                        stringBuilder.append("【指定傳送】,");
                    } else if (pc.getAu_AutoSet(i) == 2) {
                        stringBuilder.append("【瞬移卷軸】,");
                    }
                    break;

                case 4:
                    if (pc.getAu_AutoSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 5:
                    stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                    break;

                case 6:
                    if (pc.getAu_AutoSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 7:
                    stringBuilder.append(String.valueOf(pc.getAu_AutoSet(i)) + ",");
                    break;
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "AI5", clientStrAry));
    } catch (Throwable throwable) {}
}

public void Au_OtherSet(L1PcInstance pc) {
    try {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 1:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 2:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 3:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【瞬移】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 4:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【開啟】,");
                    } else {
                        stringBuilder.append("【關閉】,");
                    }
                    break;

                case 5:
                    if (pc.getAu_OtherSet(i) > 0) {
                        stringBuilder.append("【瞬移】,");
                    } else {
                        stringBuilder.append("【未設定】,");
                    }
                    break;
            }
        }
        String[] clientStrAry = stringBuilder.toString().split(",");
        pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "AI6", clientStrAry));
    } catch (Throwable throwable) {}
}
}