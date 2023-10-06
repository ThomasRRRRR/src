// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.data.item_etcitem;

import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.model.skill.L1BuffUtil;
//import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.data.cmd.CreateNewItem;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.data.executor.ItemExecutor;

public class Fish extends ItemExecutor
{
    public static ItemExecutor get() {
        return new Fish();
    }
    
    @Override
    public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
        final int itemId = item.getItemId();
        final Random random = new Random();
        int getItemId = 0;
        int getCount = 0;
        switch (itemId) {
            case 41298: {
                this.UseHeallingPotion(pc, 4, 189);
                break;
            }
            case 41299: {
                this.UseHeallingPotion(pc, 15, 194);
                break;
            }
            case 41300: {
                this.UseHeallingPotion(pc, 35, 197);
                break;
            }
            case 41301: {
                final int chance1 = random.nextInt(10);
                if (chance1 >= 0 && chance1 < 5) {
                    this.UseHeallingPotion(pc, 15, 189);
                    break;
                }
                if (chance1 >= 5 && chance1 < 9) {
                    getItemId = 40019;
                    getCount = 1;
                    break;
                }
                if (chance1 < 9) {
                    break;
                }
                final int gemChance = random.nextInt(3);
                if (gemChance == 0) {
                    getItemId = 40045;
                    getCount = 1;
                    break;
                }
                if (gemChance == 1) {
                    getItemId = 40049;
                    getCount = 1;
                    break;
                }
                if (gemChance == 2) {
                    getItemId = 40053;
                    getCount = 1;
                    break;
                }
                break;
            }
            case 41302: {
                final int chance2 = random.nextInt(3);
                if (chance2 >= 0 && chance2 < 5) {
                    this.UseHeallingPotion(pc, 15, 189);
                    break;
                }
                if (chance2 >= 5 && chance2 < 9) {
                    getItemId = 40018;
                    getCount = 1;
                    break;
                }
                if (chance2 < 9) {
                    break;
                }
                final int gemChance2 = random.nextInt(3);
                if (gemChance2 == 0) {
                    getItemId = 40047;
                    getCount = 1;
                    break;
                }
                if (gemChance2 == 1) {
                    getItemId = 40051;
                    getCount = 1;
                    break;
                }
                if (gemChance2 == 2) {
                    getItemId = 40055;
                    getCount = 1;
                    break;
                }
                break;
            }
            case 41303: {
                final int chance3 = random.nextInt(3);
                if (chance3 >= 0 && chance3 < 5) {
                    this.UseHeallingPotion(pc, 15, 189);
                    break;
                }
                if (chance3 >= 5 && chance3 < 9) {
                    getItemId = 40015;
                    getCount = 1;
                    break;
                }
                if (chance3 < 9) {
                    break;
                }
                final int gemChance3 = random.nextInt(3);
                if (gemChance3 == 0) {
                    getItemId = 40046;
                    getCount = 1;
                    break;
                }
                if (gemChance3 == 1) {
                    getItemId = 40050;
                    getCount = 1;
                    break;
                }
                if (gemChance3 == 2) {
                    getItemId = 40054;
                    getCount = 1;
                    break;
                }
                break;
            }
            case 41304: {
                final int chance4 = random.nextInt(3);
                if (chance4 >= 0 && chance4 < 5) {
                    this.UseHeallingPotion(pc, 15, 189);
                    break;
                }
                if (chance4 >= 5 && chance4 < 9) {
                    getItemId = 40021;
                    getCount = 1;
                    break;
                }
                if (chance4 < 9) {
                    break;
                }
                final int gemChance4 = random.nextInt(3);
                if (gemChance4 == 0) {
                    getItemId = 40044;
                    getCount = 1;
                    break;
                }
                if (gemChance4 == 1) {
                    getItemId = 40048;
                    getCount = 1;
                    break;
                }
                if (gemChance4 == 2) {
                    getItemId = 40052;
                    getCount = 1;
                    break;
                }
                break;
            }
        }
        pc.getInventory().removeItem(item, 1L);
        if (getCount != 0) {
            CreateNewItem.createNewItem(pc, getItemId, getCount);
        }
    }
    
    private void UseHeallingPotion(final L1PcInstance pc, int healHp, final int gfxid) {
        final Random _random = new Random();
        if (pc.hasSkillEffect(71) && pc.hasSkillEffect(8911)) {
            pc.sendPackets(new S_ServerMessage(698));
            return;
        }
        L1BuffUtil.cancelAbsoluteBarrier(pc);
        pc.sendPacketsX8(new S_SkillSound(pc.getId(), gfxid));
        healHp *= (int)(_random.nextGaussian() / 5.0 + 1.0);
        if (pc.get_up_hp_potion() > 0) {
            healHp += healHp * pc.get_up_hp_potion() / 100;
            healHp += pc.get_up_hp_potion();
        }
        if (pc.hasSkillEffect(173)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4012)) {
            healHp >>= 1;
        }
        if (pc.hasSkillEffect(4011)) {
            healHp *= -1;
        }
        if (healHp > 0) {
            pc.sendPackets(new S_ServerMessage(77));
        }
        pc.setCurrentHp(pc.getCurrentHp() + healHp);
    }
}
