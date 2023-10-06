package com.lineage.server.utils;

import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.data.event.LeavesSet;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.datatables.Explogpcpower;
import com.lineage.server.datatables.MapsTable;
import com.lineage.server.datatables.lock.PetReading;
import com.lineage.server.model.Instance.L1EffectInstance;
import com.lineage.server.model.Instance.L1IllusoryInstance;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Party;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_NPCPack_Pet;
import com.lineage.server.serverpackets.S_PacketBoxExp;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Pet;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldItem;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 經驗值取得計算
 * 
 * @author dexc
 *
 */
public class CalcExp {

	private static final Log _log = LogFactory.getLog(CalcExp.class);

	public static void calcExp(final L1PcInstance srcpc, final int targetid, final ArrayList<?> acquisitorList,
			final ArrayList<Integer> hateList, final long exp) {
		try {
			int i = 0;
			double party_level = 0;
			double dist = 0;
			long member_exp = 0;
			int member_lawful = 0;
			int pet_lawful = 0;
			final L1Object object = World.get().findObject(targetid);
			L1NpcInstance npc = null;

			if (object instanceof L1NpcInstance) {
				npc = (L1NpcInstance) object;

			} else {
				// object 不是 L1NpcInstance
				return;
			}

			// ヘイトの合計を取得
			L1Character acquisitor;
			int hate = 0;
			long acquire_exp = 0;
			int acquire_lawful = 0;
			long party_exp = 0;
			int party_lawful = 0;
			long totalHateExp = 0;
			int totalHateLawful = 0;
			long ownHateExp = 0;

			if (acquisitorList.size() != hateList.size()) {
				return;
			}
      
			for (i = hateList.size() - 1; i >= 0; i--) {
				acquisitor = (L1Character) acquisitorList.get(i);
				hate = hateList.get(i);
        
				boolean isRemove = false;// 取消經驗質獎勵
				// 攻擊者是 分身
				if (acquisitor instanceof L1IllusoryInstance) {
					isRemove = true;
				}
				// 攻擊者是 技能物件
				if (acquisitor instanceof L1EffectInstance) {
					isRemove = true;
				}
				// 取消經驗質獎勵(該物件不分取經驗質)
				if (isRemove) {
					if (acquisitor != null) {
						acquisitorList.remove(i);
						hateList.remove(i);
					}
					continue;
				}
				
				if ((acquisitor != null) && !acquisitor.isDead()) {
					totalHateExp += hate;
					if (acquisitor instanceof L1PcInstance) {
						totalHateLawful += hate;
					}

				} else { // nullだったり死んでいたら排除
					acquisitorList.remove(i);
					hateList.remove(i);
				}
			}
      
			if (totalHateExp == 0) { // 取得者がいない場合
				return;
			}

			if ((object != null) && !(npc instanceof L1PetInstance) && !(npc instanceof L1SummonInstance)) {
				// int exp = npc.get_exp();
				if (!World.get().isProcessingContributionTotal() && (srcpc.getHomeTownId() > 0)) {
					final int contribution = npc.getLevel() / 10;
					srcpc.addContribution(contribution);
				}
				final int lawful = npc.getLawful();
        
				//TODO 隊伍分配
				if (srcpc.isInParty()) { // 隊伍中
					// 隊伍經驗分配
					long partyHateExp = 0;
					int partyHateLawful = 0;
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							final L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == srcpc) {
								partyHateExp += hate;
								partyHateLawful += hate;
							} else if (srcpc.getParty().isMember(pc)) {
								partyHateExp += hate;
								partyHateLawful += hate;
								//addExp(pc, 10000, 10000);
							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}
								if (totalHateLawful > 0) {
									acquire_lawful = (lawful * hate / totalHateLawful);
								}
								addExp(pc, acquire_exp, acquire_lawful);
							}
            
						} else if (acquisitor instanceof L1PetInstance) {
							final L1PetInstance pet = (L1PetInstance) acquisitor;
							final L1PcInstance master = (L1PcInstance) pet.getMaster();
							if (master == srcpc) {
								partyHateExp += hate;

							} else if (srcpc.getParty().isMember(master)) {
								partyHateExp += hate;

							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}
								addExpPet(pet, acquire_exp);
							}

						} else if (acquisitor instanceof L1SummonInstance) {
							final L1SummonInstance summon = (L1SummonInstance) acquisitor;
							final L1PcInstance master = (L1PcInstance) summon
									.getMaster();
							if (master == srcpc) {
								partyHateExp += hate;

							} else if (srcpc.getParty().isMember(master)) {
								partyHateExp += hate;

							} else {
							
							}
						}
					}

					if (totalHateExp > 0) {
						party_exp = (exp * partyHateExp / totalHateExp);
					}

					if (totalHateLawful > 0) {
						party_lawful = (lawful * partyHateLawful / totalHateLawful);
					}

					// EXP，最低分配

					// 獎金前
					double pri_bonus = 0.0D;
					final L1PcInstance leader = srcpc.getParty().getLeader();
					if (leader.isCrown() && (srcpc.knownsObject(leader) || srcpc.equals(leader))) {
						pri_bonus = 0.059;
					}
          
					final Object[] pcs = srcpc.getParty().partyUsers().values().toArray();
          
					double pt_bonus = 0.0D;
					for (final Object obj : pcs) {
						if (obj instanceof L1PcInstance) {
							final L1PcInstance each = (L1PcInstance) obj;
							if (each.isDead()) {
								continue;
							}
							
							if (srcpc.knownsObject(each) || srcpc.equals(each)) {
								party_level += each.getLevel()
										* each.getLevel();
							}
							
							if (srcpc.knownsObject(each)) {
								pt_bonus += 0.04;
							}
						}
					}
          
					party_exp = (long) (party_exp * (1 + pt_bonus + pri_bonus));
          
					// 計算您的角色及其寵物召喚的仇恨之和
					if (party_level > 0) {
						dist = ((srcpc.getLevel() * srcpc.getLevel()) / party_level);
					}
					member_exp = (long) (party_exp * dist);
					member_lawful = (int) (party_lawful * dist);

					ownHateExp = 0;
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							final L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == srcpc) {
								ownHateExp += hate;
							}

						} else if (acquisitor instanceof L1PetInstance) {
							final L1PetInstance pet = (L1PetInstance) acquisitor;
							final L1PcInstance master = (L1PcInstance) pet
									.getMaster();
							if (master == srcpc) {
								ownHateExp += hate;
							}
						} else if (acquisitor instanceof L1SummonInstance) {
							final L1SummonInstance summon = (L1SummonInstance) acquisitor;
							final L1PcInstance master = (L1PcInstance) summon
									.getMaster();
							if (master == srcpc) {
								ownHateExp += hate;
							}
						}
					}
					// 分發給您的角色及其寵物/召喚物
					if (ownHateExp != 0) { // 參與攻擊
						for (i = hateList.size() - 1; i >= 0; i--) {
							acquisitor = (L1Character) acquisitorList.get(i);
							hate = hateList.get(i);
							if (acquisitor instanceof L1PcInstance) {
								final L1PcInstance pc = (L1PcInstance) acquisitor;
								if (pc == srcpc) {
									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}
									addExp(pc, acquire_exp, member_lawful);
								}

							} else if (acquisitor instanceof L1PetInstance) {
								final L1PetInstance pet = (L1PetInstance) acquisitor;
								final L1PcInstance master = (L1PcInstance) pet
										.getMaster();
								if (master == srcpc) {
									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}
									addExpPet(pet, acquire_exp);
								}

							} else if (acquisitor instanceof L1SummonInstance) {
							}
						}

					} else { // 攻撃に参加していなかった
						// 自キャラクターのみに分配
						addExp(srcpc, member_exp, member_lawful);
					}
          
					// パーティーメンバーとそのペット・サモンのヘイトの合計を算出

					for (final Object obj : pcs) {
						if (obj instanceof L1PcInstance) {
							final L1PcInstance tgpc = (L1PcInstance) obj;
							if (tgpc.isDead()) {
								continue;
							}
							if (srcpc.knownsObject(tgpc)) {
								if (party_level > 0) {
									dist = ((tgpc.getLevel() * tgpc.getLevel()) / party_level);
								}
								member_exp = (int) (party_exp * dist);
								member_lawful = (int) (party_lawful * dist);

								ownHateExp = 0;
								for (i = hateList.size() - 1; i >= 0; i--) {
									acquisitor = (L1Character) acquisitorList
											.get(i);
									hate = hateList.get(i);
									if (acquisitor instanceof L1PcInstance) {
										final L1PcInstance pc = (L1PcInstance) acquisitor;
										if (pc == tgpc) {
											ownHateExp += hate;
										}

									} else if (acquisitor instanceof L1PetInstance) {
										final L1PetInstance pet = (L1PetInstance) acquisitor;
										final L1PcInstance master = (L1PcInstance) pet
												.getMaster();
										if (master == tgpc) {
											ownHateExp += hate;
										}

									} else if (acquisitor instanceof L1SummonInstance) {
										final L1SummonInstance summon = (L1SummonInstance) acquisitor;
										final L1PcInstance master = (L1PcInstance) summon
												.getMaster();
										if (master == tgpc) {
											ownHateExp += hate;
										}
									}
								}
								// パーティーメンバーとそのペット・サモンに分配
								if (ownHateExp != 0) { // 攻撃に参加していた
									for (i = hateList.size() - 1; i >= 0; i--) {
										acquisitor = (L1Character) acquisitorList
												.get(i);
										hate = hateList.get(i);
										if (acquisitor instanceof L1PcInstance) {
											final L1PcInstance pc = (L1PcInstance) acquisitor;
											if (pc == tgpc) {
												if (ownHateExp > 0) {
													acquire_exp = (member_exp
															* hate / ownHateExp);
												}
												addExp(pc, acquire_exp,
														member_lawful);
											}
                      
										} else if (acquisitor instanceof L1PetInstance) {
											final L1PetInstance pet = (L1PetInstance) acquisitor;
											final L1PcInstance master = (L1PcInstance) pet
													.getMaster();
											if (master == tgpc) {
												if (ownHateExp > 0) {
													acquire_exp = (member_exp
															* hate / ownHateExp);
												}
												addExpPet(pet, acquire_exp);
											}

										} else if (acquisitor instanceof L1SummonInstance) {
										}
									}

								} else { // 攻撃に参加していなかった
									// パーティーメンバーのみに分配
									addExp(tgpc, member_exp, member_lawful);
								}
							}
						}
					}
        
				} else { // パーティーを組んでいない
					// EXP、ロウフルの分配
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = hateList.get(i);
						acquire_exp = (exp * hate / totalHateExp);
						if (acquisitor instanceof L1PcInstance) {
							if (totalHateLawful > 0) {
								acquire_lawful = (lawful * hate / totalHateLawful);
							}
						}

						if (acquisitor instanceof L1PcInstance) {
							final L1PcInstance pc = (L1PcInstance) acquisitor;
							addExp(pc, acquire_exp, acquire_lawful);

						} else if (acquisitor instanceof L1PetInstance) {
							final L1PetInstance pet = (L1PetInstance) acquisitor;
							addExpPet(pet, acquire_exp);

						} else if (acquisitor instanceof L1SummonInstance) {

						}
					}
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}


	/**
	 * 建立EXP 與 正義值 取得
	 * 
	 * @param pc
	 * @param exp
	 * @param lawful
	 */
  private static void addExp(L1PcInstance pc, long exp, int lawful) {
    try {
      double addExp = exp;
      int add_lawful = (int)(lawful * ConfigRate.RATE_LA) * -1;
      pc.addLawful(add_lawful);
      
      if (pc.getLevel() >= ExpTable.MAX_LEVEL) {
        return;
      }
      if (LeavesSet.START && pc.getlogintime() > 0) {
        int add = (int)(addExp * 0.77D);

        
        int add1 = (int)addExp + add;

        
        int add2 = pc.getlogintime() - add1;
        
        if (add2 > 0) {
          
          pc.setlogintime(add2);
          
          pc.sendPackets((ServerBasePacket)new S_PacketBoxExp(pc.getlogintime() / LeavesSet.EXP));
        } else {
          add = pc.getlogintime();
          pc.setlogintime(0);
          
          pc.sendPackets((ServerBasePacket)new S_PacketBoxExp());
        } 
        addExp += add;
      } 
      double exppenalty = ExpTable.getPenaltyRate(pc.getLevel(), pc.getMeteLevel());
      
      if (exppenalty < 1.0D) {
        addExp *= exppenalty;
      }

      
      if (ConfigRate.RATE_XP > 1.0D) {
        addExp *= ConfigRate.RATE_XP;
      }

      
      if (MapsTable.get().getEXP(pc.getMapId()) > 100.0D) {
        addExp *= MapsTable.get().getEXP(pc.getMapId()) / 100.0D;
      }
      
      addExp *= add(pc);
      
      addExp *= Explogpcpower.get().getRate(pc.getMeteLevel());

      
		/** [原碼] 修正經驗值大於2147483647會變負值.暴等 */
		if (addExp < 0) {
			addExp = 0;
		} else if (addExp > 36065092) {
			addExp = 36065092;
		}

      
      pc.addExp((long)addExp);
    }
    catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
  }

  /**
	 * 經驗加倍計算
	 * 
	 * @param pc
	 * @return
	 */
  private static double add(L1PcInstance pc) {
		try {
			double add_exp = 1.0D;

			if (pc.is_mazu()) {// 媽祖祝福增加100%
				add_exp += 1.0D;
			}

			if (pc.getExpPoint() > 0) {// 裝備增加EXP
				add_exp += pc.getExpPoint() / 100.0D;
			}

			double foodBonus = 0.0D;
			if ((pc.hasSkillEffect(3007)) || (pc.hasSkillEffect(3015))) {
				foodBonus = 0.01D;
			}
			if ((pc.hasSkillEffect(3023)) || (pc.hasSkillEffect(3031))) {
				foodBonus = 0.02D;
			}
			if ((pc.hasSkillEffect(3039)) || (pc.hasSkillEffect(3047))) {
				foodBonus = 0.03D;
			}

			if ((pc.hasSkillEffect(3048)) || (pc.hasSkillEffect(3049)) || (pc.hasSkillEffect(3050))) {// 新料理
				foodBonus = 0.02D;
			}

			if (pc.hasSkillEffect(3051)) {// 新料理
				foodBonus = 0.04D;
			}

			/* 1段經驗加倍效果 */
			if (pc.hasSkillEffect(6666)) {
				foodBonus += 1.3D;
			}
			if (pc.hasSkillEffect(6667)) {
				foodBonus += 1.5D;
			}
			if (pc.hasSkillEffect(6668)) {
				foodBonus += 1.7D;
			}
			if (pc.hasSkillEffect(6669)) {
				foodBonus += 2.0D;
			}
			if (pc.hasSkillEffect(6670)) {
				foodBonus += 2.5D;
			}
			if (pc.hasSkillEffect(6671)) {
				foodBonus += 3.0D;
			}
			if (pc.hasSkillEffect(6672)) {
				foodBonus += 3.5D;
			}
			if (pc.hasSkillEffect(6673)) {
				foodBonus += 4.0D;
			}
			if (pc.hasSkillEffect(6674)) {
				foodBonus += 4.5D;
			}
			if (pc.hasSkillEffect(6675)) {
				foodBonus += 5.0D;
			}
			if (pc.hasSkillEffect(6676)) {
				foodBonus += 5.5D;
			}
			if (pc.hasSkillEffect(6677)) {
				foodBonus += 6.0D;
			}
			if (pc.hasSkillEffect(6678)) {
				foodBonus += 6.5D;
			}
			if (pc.hasSkillEffect(6679)) {
				foodBonus += 7.0D;
			}
			if (pc.hasSkillEffect(6680)) {
				foodBonus += 7.5D;
			}
			if (pc.hasSkillEffect(6681)) {
				foodBonus += 8.0D;
			}
			// 食物經驗值倍數/第一段經驗加倍
				if (foodBonus > 0) {
					add_exp += foodBonus;
				}
 
				// 經驗值增加
				add_exp += pc.getExpAdd();// 娃娃 月卡 血盟 增加EXP
				// System.out.println(pc.getName() + " 經驗加成%：" + pc.getExpAdd());
 
				// 第二段經驗加倍
				double s2_exp = 0.0D;
				if (pc.hasSkillEffect(5000)) {
				    s2_exp += 1.5D;
				}
				if (pc.hasSkillEffect(5001)) {
				    s2_exp += 1.75D;
				}
				if (pc.hasSkillEffect(5002)) {
				    s2_exp += 2.0D;
				}
				if (pc.hasSkillEffect(5003)) {
				    s2_exp += 2.25D;
				}
				if (pc.hasSkillEffect(5004)) {
				    s2_exp += 2.5D;
				}
				if (pc.hasSkillEffect(5005)) {
				    s2_exp += 2.75D;
				}
				if (pc.hasSkillEffect(5006)) {
				    s2_exp += 3.0D;
				}
				if (pc.hasSkillEffect(5007)) {
				    s2_exp += 3.25D;
				}
				if (pc.hasSkillEffect(5008)) {
				    s2_exp += 3.5D;
				}
				if (pc.hasSkillEffect(5009)) {
				    s2_exp += 3.75D;
				}
				if (pc.hasSkillEffect(5010)) {
				    s2_exp += 4.0D;
				}
				if (pc.hasSkillEffect(5011)) {
				    s2_exp += 4.5D;
				}
				if (pc.hasSkillEffect(5012)) {
				    s2_exp += 5.0D;
				}
				if (pc.hasSkillEffect(5013)) {
				    s2_exp += 5.5D;
				}
				if (pc.hasSkillEffect(5014)) {
				    s2_exp += 6.0D;
				}

				if (s2_exp > 0.0D) {
				    add_exp += s2_exp;
				}

      L1Party party = pc.getParty();
      if (ConfigOther.partyexp && party != null) {
        for (L1PcInstance player : World.get().getVisiblePlayer((L1Object)pc, 8)) {
          if (pc.getParty().isMember(player)) {
            int count = player.getParty().getNumOfMembers();
            if (count > 1) {
              player.setPartyExp(ConfigOther.partyexp1 * count);
            }
          } 
          add_exp += pc.getPartyExp();
        } 
      }

      if (pc.getExpByArmor() > 0.0D) {
        add_exp += pc.getExpByArmor() / 100.0D;
      }

      if (pc.isActived() || pc.get_followmaster() != null) {
        add_exp *= ConfigGuaji.guajiexp2;
      }

      return add_exp;
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return 1.0D;
	}

  /**
	 * 寵物獲得經驗值判斷
	 * 
	 * @param pet
	 * @param exp
	 */
	private static void addExpPet(L1PetInstance pet, long exp) {
    try {
      if (pet == null) {
        return;
      }
      if (pet.getPetType() == null) {
        return;
      }
      L1PcInstance pc = (L1PcInstance)pet.getMaster();
      if (pc == null) {
        return;
      }
		int petItemObjId = pet.getItemObjId();

		int levelBefore = pet.getLevel();
      long totalExp = exp * ConfigOther.petexp + pet.getExp();
		/**寵物最高等级限制 by Manly*/
		//int maxLevel = 50;// 寵物等級限制
      int maxLevel = ConfigOther.petlevel;
      
//int lawfulIncrease = 1 + Random.nextInt(15); // 增加的正義值
//int currentLawful = pet.getLawful(); // 當前的正義值
//
//// 檢查正義值是否已達到上限
//if (currentLawful == 32767 || currentLawful == -32767) {
//    lawfulIncrease = 0; // 不增加正義值
//} else {
//    int maxLawfulIncrease = 32767 - currentLawful;
//    int minLawfulIncrease = -32767 - currentLawful;
//
//    // 檢查正義值是否超過上限
//    if (lawfulIncrease > maxLawfulIncrease) {
//        lawfulIncrease = maxLawfulIncrease;
//    }
//
//    // 檢查正義值是否低於下限
//    if (lawfulIncrease < minLawfulIncrease) {
//        lawfulIncrease = minLawfulIncrease;
//    }
//}
//// 增加正義值
//pet.addLawful(lawfulIncrease);
//
//// 更新寵物模板的正義值
//L1Pet petTemplate1 = PetReading.get().getTemplate(petItemObjId);
//petTemplate1.set_lawful(pet.getLawful());
//PetReading.get().storePet(petTemplate1);

      if (totalExp >= ExpTable.getExpByLevel(maxLevel)) {
        totalExp = ExpTable.getExpByLevel(maxLevel) - 1L;
      }
      pet.setExp(totalExp);
      
      pet.setLevel(ExpTable.getLevelByExp(totalExp));
      
      int expPercentage = ExpTable.getExpPercentage(pet.getLevel(), totalExp);
      
      int gap = pet.getLevel() - levelBefore;
      for (int i = 1; i <= gap; i++) {
        RangeInt hpUpRange = pet.getPetType().getHpUpRange();
        RangeInt mpUpRange = pet.getPetType().getMpUpRange();
        pet.addMaxHp(hpUpRange.randomValue());
        pet.addMaxMp(mpUpRange.randomValue());
      } 
      
      pet.setExpPercent(expPercentage);
      pc.sendPackets((ServerBasePacket)new S_NPCPack_Pet(pet, pc));
      
      if (gap != 0) {
        L1Pet petTemplate = PetReading.get().getTemplate(petItemObjId);
        if (petTemplate == null) {
          return;
        }
        petTemplate.set_exp((int)pet.getExp());
        petTemplate.set_level(pet.getLevel());
        petTemplate.set_hp(pet.getMaxHp());
        petTemplate.set_mp(pet.getMaxMp());
        petTemplate.set_lawful(pet.getLawful());
        PetReading.get().storePet(petTemplate);
        
        pc.sendPackets((ServerBasePacket)new S_ServerMessage(320, pet.getName()));
        L1ItemInstance item = WorldItem.get().getItem(Integer.valueOf(pet.getItemObjId()));
        pc.sendPackets((ServerBasePacket)new S_ItemName(item));
      }
    
    } catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
  }
}