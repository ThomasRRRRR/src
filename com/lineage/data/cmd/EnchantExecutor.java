// 
// Decompiled by Procyon v0.5.36
// 

package com.lineage.data.cmd;

import com.lineage.config.ConfigRate;
import java.util.Random;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

public abstract class EnchantExecutor {
	
    public abstract void failureEnchant(final L1PcInstance p0, final L1ItemInstance p1);

    public abstract void successEnchant(final L1PcInstance p0, final L1ItemInstance p1, final int p2);
    
    public int randomELevel(final L1ItemInstance item, final int bless) {
        int level = 0;
        switch (bless) {
			case 0:// 祝福
			case 128:// 封印祝福
                if (item.getBless() >= 3) {
                    break;
                }
                final Random random = new Random();
                final int i = random.nextInt(100);
                if (item.getItem().getType2() == 1) {
                    if (item.getEnchantLevel() >= -1 && item.getEnchantLevel() <= 2) {
                        if (i <= ConfigRate.ran3) {
                            level = 1;
                            break;
                        }
                        if (i <= ConfigRate.ran2) {
                            level = 2;
                            break;
                        }
                        if (i <= ConfigRate.ran1) {
                            level = 3;
                            break;
                        }
                        level = 1;
                        break;
                    }
                    else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
                        if (i <= ConfigRate.ran3) {
                            level = 1;
                            break;
                        }
                        if (i <= ConfigRate.ran2) {
                            level = 2;
                            break;
                        }
                        if (i <= ConfigRate.ran1) {
                            level = 2;
                            break;
                        }
                        level = 1;
                        break;
                    }
                    else {
                        if (item.getEnchantLevel() >= 6) {
                            level = 1;
                            break;
                        }
                        break;
                    }
                }
                else {
                    if (item.getItem().getType2() != 2) {
                        break;
                    }
                    if (item.getEnchantLevel() >= -1 && item.getEnchantLevel() <= 2) {
                        if (i <= ConfigRate.ran8) {
                            level = 1;
                            break;
                        }
                        if (i <= ConfigRate.ran7) {
                            level = 2;
                            break;
                        }
                        if (i <= ConfigRate.ran6) {
                            level = 3;
                            break;
                        }
                        level = 1;
                        break;
                    }
                    else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
                        if (i <= ConfigRate.ran8) {
                            level = 1;
                            break;
                        }
                        if (i <= ConfigRate.ran7) {
                            level = 2;
                            break;
                        }
                        if (i <= ConfigRate.ran6) {
                            level = 2;
                            break;
                        }
                        level = 1;
                        break;
                    }
                    else {
                        if (item.getEnchantLevel() >= 6) {
                            level = 1;
                            break;
                        }
                    }
                }
                break;
			case 1:// 一般
			case 129:// 封印一般
				if (item.getBless() < 3) {
					level = 1;
				}
				break;
			case 2:// 詛咒
			case 130:// 封印詛咒
				if (item.getBless() < 3) {
					level = -1;
				}
				break;
			case 3:// 幻象
			case 131:// 封印幻象
				if (item.getBless() == 3) {
					level = 1;
				}
				break;
			}
			return level;
    }
}
