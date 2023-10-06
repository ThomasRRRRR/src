package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.*;

import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigSkill;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.config.Configdead;
import com.lineage.server.datatables.NpcTable;
import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.S_SystemMessage;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1ItemSpecialAttributeChar;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.utils.Random;
import com.lineage.server.utils.RandomArrayList;
import com.lineage.william.IntSp;
import com.lineage.william.PcMr;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class L1MagicPc
  extends L1MagicMode
{
  private static final Log _log = LogFactory.getLog(L1MagicPc.class);
  L1ItemSpecialAttributeChar item_attr_char = null;
  
  protected L1ItemInstance _weapon;
  
  public L1MagicPc(L1PcInstance attacker, L1Character target) {
    if (attacker == null) {
      return;
    }
    
    this._pc = attacker;
    
    if (target instanceof L1PcInstance) {
      this._calcType = 1;
      this._targetPc = (L1PcInstance)target;
    } else {
      
      this._calcType = 2;
      this._targetNpc = (L1NpcInstance)target;
    } 
  }

  
  private int getMagicLevel() {
    return this._pc.getMagicLevel();
  }

  
  private int getMagicBonus() {
    return this._pc.getMagicBonus();
  }

  
  private int getLawful() {
    return this._pc.getLawful();
  }
  
  public boolean calcProbabilityMagic(int skillId) {
    boolean castle_area;
    int probability = 0;
    boolean isSuccess = false;
    
    switch (this._calcType) {
      
      case PC_PC:
        if (this._targetPc.getattr_魔法格檔() > 0 && 
          _random.nextInt(100) < this._targetPc.getattr_魔法格檔())
        {
          return false;
        }
        
        if (skillId == 44 && 
          this._pc != null && this._targetPc != null) {
          
          if (this._pc.getId() == this._targetPc.getId()) {
            return true;
          }
          
          if (this._pc.getClanid() > 0 && 
            this._pc.getClanid() == this._targetPc.getClanid()) {
            return true;
          }
        } 

        if (skillId == 67) {
          if (this._pc.getId() == this._targetPc.getId()) {
            return true;
          }
          
          if (this._pc.getClanid() > 0 && 
            this._pc.getClanid() == this._targetPc.getClanid()) {
            return true;
          }
        } 
        if (ConfigGuaji.Guaji_save && this._targetPc.isActived() && 
          this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
          return false;
        }
        
        if (this._targetPc == this._pc) {
          return false;
        }
        castle_area = L1CastleLocation.checkInAllWarArea(this._targetPc.getX(), this._targetPc.getY(), this._targetPc.getMapId());

        if (this._targetPc.getnewcharpra()) {
          return false;
        }
        if (this._pc.getnewcharpra()) {
          return false;
        }
        
        if (!checkZone(skillId)) {
          return false;
        }
        
        if (this._targetPc.hasSkillEffect(50) && 
          skillId != 44) {
          return false;
        }

        
        if (this._targetPc.hasSkillEffect(157))
        {
          if (skillId != 44) {
            return false;
          }
        }

        if (calcEvasion()) {
          return false;
        }
        break;

      case PC_NPC:
        if (this._targetNpc != null) {
          if (this._targetNpc instanceof com.lineage.server.model.Instance.L1DeInstance)
          {
            if (!checkZoneDE(skillId)) {
              return false;
            }
          }
          
          int gfxid = this._targetNpc.getNpcTemplate().get_gfxid();
          switch (gfxid) {
            case 2412:
              if (!this._pc.getInventory().checkEquipped(20046)) {
                return false;
              }
              break;
          } 

          int npcId = this._targetNpc.getNpcTemplate().get_npcId();
          Integer tgskill = L1AttackList.SKNPC.get(Integer.valueOf(npcId));
          if (tgskill != null && 
            !this._pc.hasSkillEffect(tgskill.intValue())) {
            return false;
          }
          
          Integer tgpoly = L1AttackList.PLNPC.get(Integer.valueOf(npcId));
          if (tgpoly != null && 
            tgpoly.equals(Integer.valueOf(this._pc.getTempCharGfx()))) {
            return false;
          }
          
          boolean dgskill = L1AttackList.DNNPC.containsKey(Integer.valueOf(npcId));
          if (dgskill) {
            Integer[] dgskillids = L1AttackList.DNNPC.get(Integer.valueOf(npcId)); byte b; int i; Integer[] arrayOfInteger1;
            for (i = (arrayOfInteger1 = dgskillids).length, b = 0; b < i; ) { Integer dgskillid = arrayOfInteger1[b];
              if (dgskillid.equals(Integer.valueOf(skillId))) {
                return false;
              }
              b++; }
          
          } 
        } 
        if (skillId == 44) {
          return true;
        }
        
        if (skillId == 67 && 
          this._targetNpc.getLevel() >= 50) {
          return false;
        }

        
        if (this._targetNpc.hasSkillEffect(50) && 
          skillId != 27 && skillId != 44) {
          return false;
        }

        
        if (this._targetNpc.hasSkillEffect(157) && 
          skillId != 27 && skillId != 44) {
          return false;
        }
        break;
    } 

    probability = calcProbability(skillId);
    
    if ((this._calcType == 1 || this._calcType == 2) && 
      this._pc.isWizard() && this._pc.getlogpcpower_SkillFor3() != 0) {
      probability += this._pc.getlogpcpower_SkillFor3();
    }
    
    if (skillId == 208 && 
      this._pc.getlogpcpower_SkillFor3() != 0) {
      probability += this._pc.getlogpcpower_SkillFor3();
    }

    int rnd = _random.nextInt(100) + 1;

    if (probability >= rnd) {
      isSuccess = true;
      if (this._pc.isGm()) {
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("玩家發動:" + probability + "大於系統發動值" + rnd + "觸發"));
      }
    } else {
      isSuccess = false;
      if (this._pc.isGm()) {
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("玩家發動:" + probability + "小於系統發動值" + rnd + "失敗"));
      }
    } 

    if (!ConfigAlt.ALT_ATKMSG) {
      return isSuccess;
    }
    
    switch (this._calcType) {
      case 1:
        if (!this._pc.isGm() && 
          !this._targetPc.isGm()) {
          return isSuccess;
        }
        break;
      
      case 2:
        if (!this._pc.isGm()) {
          return isSuccess;
        }
        break;
    } 

    
    switch (this._calcType) {
      case 1:
        if (this._pc.isGm()) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("對PC送出技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetPc.getName()) + " ");
          atkMsg.append(isSuccess ? "成功" : "失敗");
          atkMsg.append(" 成功機率:" + probability + "%");
          
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        if (this._targetPc.isGm()) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("受到PC技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetPc.getName()) + " ");
          atkMsg.append(isSuccess ? "成功" : "失敗");
          atkMsg.append(" 成功機率:" + probability + "%");
          
          this._targetPc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        break;
      case 2:
        if (this._pc.isGm()) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("對NPC送出技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetNpc.getName()) + " ");
          atkMsg.append(isSuccess ? "成功" : "失敗");
          atkMsg.append(" 成功機率:" + probability + "%");
          
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        break;
    } 
    return isSuccess;
  }

  
  private boolean checkZone(int skillId) {
    if (this._pc != null && this._targetPc != null)
    {
      if (this._pc.isSafetyZone() || this._targetPc.isSafetyZone()) {
        
        Boolean isBoolean = L1AttackList.NZONE.get(Integer.valueOf(skillId));
        if (isBoolean != null) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("在安全區域無法使用此技能。"));
          return false;
        } 
      } 
    }
    return true;
  }

  
  private boolean checkZoneDE(int skillId) {
    if (this._pc != null && this._targetNpc != null)
    {
      if (this._pc.isSafetyZone() || this._targetNpc.isSafetyZone()) {
        
        Boolean isBoolean = L1AttackList.NZONE.get(Integer.valueOf(skillId));
        if (isBoolean != null) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("在安全區域無法使用此技能。"));
          return false;
        } 
      } 
    }
    return true;
  }




  
  private int calcProbability(int skillId) {
    int dice2, diceCount2, i;
    L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
    int attackLevel = this._pc.getLevel();
    int defenseLevel = 0;
    int probability = 0;
    
    switch (this._calcType) {

      case 1:
        defenseLevel = this._targetPc.getLevel();
        break;
      case 2:
        defenseLevel = this._targetNpc.getLevel();
        if (skillId == 145 && this._targetNpc instanceof L1SummonInstance) {
          L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
          defenseLevel = summon.getMaster().getLevel();
        } 
        break;
    } 


    
    Integer magichit = L1AttackList.INTH.get(Integer.valueOf(this._pc.getInt()));
    if (magichit == null) {
      magichit = Integer.valueOf(0);
    }
    
    if (this._calcType == 1 && (
      skillId == 27 || skillId == 29 || skillId == 33 || skillId == 47 || skillId == 50 || skillId == 59 || skillId == 64 || skillId == 66 || skillId == 71 || 
      skillId == 152 || skillId == 167 || skillId == 153 || skillId == 157 || skillId == 161 || skillId == 173 || skillId == 174 || skillId == 39 || 
      skillId == 103 || skillId == 183 || skillId == 188 || skillId == 193 || skillId == 202 || skillId == 212 || skillId == 217 || skillId == 11 || 
      skillId == 20 || skillId == 40 || skillId == 76) && 
      !isInWarAreaAndWarTime(this._pc, this._targetPc) && !this._targetPc.isPinkName() && !this._targetPc.isCombatZone()) {
      L1PinkName.runPinkNameTimer(this._pc);
    }

    switch (skillId) {
      case 18:
        probability = l1skills.getProbabilityValue();
        
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        if (this._pc.isWizard() && probability > 70) {
          probability = 70;
        } else if (this._pc.isElf()) {
          if (probability > 70) {
            probability = 55;
          } else {
            probability /= 2;
          } 
        } 
        probability += this._pc.getDoll_MagicHit();
        break;
      case 91:
        probability = l1skills.getProbabilityValue();
        
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        probability += this._pc.getDoll_MagicHit();
        break;
      case 145:
        probability = (int)(l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
        probability += l1skills.getProbabilityValue();
        probability -= getTargetMr() / 10;
        probability += this._pc.getOriginalMagicHit();
        break;

      case 133:
      case 152:
      case 153:
      case 157:
      case 161:
      case 167:
      case 173:
      case 174:
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        probability += this._pc.getDoll_MagicHit();
        break;
      
      case 27:
      case 29:
      case 33:
      case 39:
      case 44:
      case 50:
      case 64:
      case 66:
      case 71:
        probability = (int)(l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("type1;" + probability));
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("1公式;(dice/10)*(我等-對等)級"));
        } 
        probability += l1skills.getProbabilityValue();
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("type2;" + probability));
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("2公式:+SKILLS欄位ProbabilityValue"));
        } 
        
        if (l1skills.gettype23() != 0 && this._pc.getOriginalMagicHit() > 0) {
          this._pc.addOriginalMagicHit(this._pc.getOriginalMagicHit() * l1skills.gettype23() / 100);
        }
        probability += this._pc.getOriginalMagicHit();
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("type3;" + probability));
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("3公式:自身魔法命中增加成功機率"));
        } 
        probability += magichit.intValue();
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("type4;" + probability));
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("4公式:智力魔法命中補正"));
        } 
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        
        probability -= getTargetMr() / 10;
        if (this._pc.isGm()) {
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage("type5:最終值對方抗/10=" + probability));
        }

        if (this._calcType == 1) {
          int othersp = PcMr.getIntSpSkill(this._targetPc.getMr());
          if (othersp != 0) {
            probability -= othersp;
            
            if (probability < 0) {
              probability = 0;
            }
          } 
        } 
        
        if (l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        
        probability += this._pc.getDoll_MagicHit();
        break;

      case 87:
        probability += this._pc.getOriginalMagicHit();
        
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        probability += this._pc.getaddStunLevel();
        
        probability += this._pc.getDoll_MagicHit();
        break;

      case 103:
      case 112:
        probability += this._pc.getArmorBreakLevel();
        probability += this._pc.getOriginalMagicHit();
        
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        
        probability += this._pc.getDoll_MagicHit();
        break;

      case 202:
      case 212:
      case 217:
        probability = Random.nextInt(11) + 20;
        
        probability += (attackLevel - defenseLevel) * 2;
        
        probability += this._pc.getOriginalMagicHit();
        
        probability += magichit.intValue();
        
        if (this._pc.isIllusionist() && this._pc.getlogpcpower_SkillFor1() != 0) {
          probability += this._pc.getlogpcpower_SkillFor1();
        }
        probability += (int)(l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        
        probability += this._pc.getDoll_MagicHit();
        break;
      case 208:
        probability += this._pc.getStunLevel() + this._pc.getaddStunLevel();

        probability += magichit.intValue();
        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        
        probability += this._pc.getDoll_MagicHit();
        break;
      case 183:
      case 188:
      case 193:
        probability += l1skills.getProbabilityValue();
        probability += this._pc.getOriginalMagicHit();
        probability += (int)(l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
        
        probability += this._pc.getDoll_MagicHit();
        break;
      case 67:
        probability = 3 * (attackLevel - defenseLevel) + 225 - getTargetMr();
        
        probability += (int)(l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
        
        probability += this._pc.getDoll_MagicHit();
        break;
      
case 230:// 亡命之徒
	if (attackLevel > defenseLevel) {// 攻擊方等級大於防禦方
		probability = 70;
	} else if (attackLevel == defenseLevel) {// 攻擊方等級相等防禦方
		probability = 50;
	} else if (attackLevel < defenseLevel) {// 攻擊方等級小於防禦方
		probability = 30;
	}
	probability += (this._pc.getOriginalMagicHit());// 魔法命中增加成功機率

	probability += magichit; // 7.6智力魔法命中補正

	if (this._pc.getBaseInt() >= 25 && this._pc.getBaseInt() <= 44) {// 純智力25~44
															// 每10級增加1命中
		probability += (this._pc.getBaseInt() - 15) / 10;// 取商數

	} else if (this._pc.getBaseInt() >= 45) {// 純智力45以上 共增加5命中
		probability += 5;
	}
	break;
case 229:// 戰斧投擲
	probability = (int) (l1skills.getProbabilityDice() / 10.0D * (attackLevel - defenseLevel));
	probability += l1skills.getProbabilityValue();
	probability += (this._pc.getOriginalMagicHit());// 魔法命中增加成功機率

	probability += magichit; // 7.6智力魔法命中補正

	if (this._pc.getBaseInt() >= 25 && this._pc.getBaseInt() <= 44) {// 純智力25~44
															// 每10級增加1命中
		probability += (this._pc.getBaseInt() - 15) / 10;// 取商數

	} else if (this._pc.getBaseInt() >= 45) {// 純智力45以上 共增加5命中
		probability += 5;
	}
	break;
case 237:// 泰坦岩石
case 238:// 泰坦子彈
case 239:// 泰坦魔法
case 8912:
	probability += l1skills.getProbabilityValue();
	break;
      default:
        dice2 = l1skills.getProbabilityDice();
        diceCount2 = 0;
        
        diceCount2 = getMagicBonus() + getMagicLevel();
        
        diceCount2 = Math.max(diceCount2, 1);
        
        for (i = 0; i < diceCount2; i++) {
          if (dice2 > 0) {
            probability += _random.nextInt(dice2) + 1;
          }
        } 

        probability += this._pc.getOriginalMagicHit();
        probability += magichit.intValue();
        probability -= getTargetMr() / 10;
        probability += this._pc.getDoll_MagicHit();

        if (l1skills.gettype1() != 0 && attackLevel > defenseLevel) {
          probability = l1skills.gettype1();
        }
        if (l1skills.gettype2() != 0 && attackLevel == defenseLevel) {
          probability = l1skills.gettype2();
        }
        if (l1skills.gettype3() != 0 && attackLevel < defenseLevel) {
          probability = l1skills.gettype3();
        }
        if (l1skills.gettype4() != 0) {
          probability += l1skills.gettype4();
        }
        if (l1skills.gettype5() != 0) {
          probability += l1skills.gettype5() * (attackLevel - defenseLevel) / 10;
        }
        if (l1skills.gettype6() != 0) {
          probability += l1skills.gettype6() * this._pc.getInt() / 10;
        }
        if (l1skills.gettype7() != 0) {
          probability += l1skills.gettype7() + (attackLevel - defenseLevel) * 5;
        }
        if (l1skills.gettype8() != 0) {
          probability += l1skills.gettype8() + (attackLevel - defenseLevel) * 2;
        }
        if (l1skills.gettype9() != 0) {
          probability += l1skills.gettype9() + attackLevel - defenseLevel;
        }
        if (l1skills.gettype10() != 0 && 
          this._pc.getInt() >= l1skills.gettype10()) {
          probability += 3;
        }
        if (l1skills.gettype11() != 0) {
          probability += l1skills.gettype11() * (attackLevel - defenseLevel);
        }
        if (l1skills.gettype12() != 0) {
          probability = (int)(probability + l1skills.gettype12() + (this._pc.getMr() - l1skills.gettype12()) * 0.02D);
        }
        if (l1skills.gettype13() != 0) {
          probability = (int)(probability + l1skills.gettype13() + (this._pc.getMr() - l1skills.gettype13()) * 0.04D);
        }
        if (this._calcType == 1 && l1skills.gettype14() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype14()) {
          probability -= l1skills.gettype14() / 7;
        }
        if (this._calcType == 1 && l1skills.gettype15() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype15()) {
          probability -= l1skills.gettype15() / 9;
        }
        if (this._calcType == 1 && l1skills.gettype16() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype16()) {
          probability -= l1skills.gettype16() / 13;
        }
        if (this._calcType == 1 && l1skills.gettype17() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype17()) {
          probability -= l1skills.gettype17() / 15;
        }
        if (this._calcType == 1 && l1skills.gettype18() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype18()) {
          probability -= l1skills.gettype18() / 20;
        }
        if (this._calcType == 1 && l1skills.gettype19() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype19()) {
          probability -= 15;
        }
        if (l1skills.gettype20() != 0 && 
          attackLevel - defenseLevel > l1skills.gettype20()) {
          probability = 70;
        }
        if (l1skills.gettype21() != 0 && 
          probability >= l1skills.gettype21()) {
          probability = l1skills.gettype21();
        }
        if (this._calcType == 1 && l1skills.gettype22() != 0 && 
          this._targetPc.getMr() >= l1skills.gettype22()) {
          probability = 5;
        }
        if (this._calcType == 1 && l1skills.gettype24() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype24()) / 7;
        }
        if (this._calcType == 1 && l1skills.gettype25() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype25()) / 9;
        }
        if (this._calcType == 1 && l1skills.gettype26() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype26()) / 13;
        }
        if (this._calcType == 1 && l1skills.gettype27() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype27()) / 15;
        }
        if (this._calcType == 1 && l1skills.gettype28() != 0) {
          probability -= (this._targetPc.getMr() - l1skills.gettype28()) / 20;
        }
        if (skillId == 36) {
          double probabilityRevision = 1.0D;
          if (this._targetNpc.getMaxHp() * 1 / 4 > this._targetNpc.getCurrentHp()) {
            probabilityRevision = 1.3D;
          } else if (this._targetNpc.getMaxHp() * 2 / 4 > this._targetNpc
            .getCurrentHp()) {
            probabilityRevision = 1.2D;
          } else if (this._targetNpc.getMaxHp() * 3 / 4 > this._targetNpc
            .getCurrentHp()) {
            probabilityRevision = 1.1D;
          } 
          probability = (int)(probability * probabilityRevision);
        } 
        break;
    } 
    switch (this._calcType) {
      case 1:
        switch (skillId) {
          case 157:
case 228:// 拘束移動
case 230:// 亡命之徒
	probability -= this._targetPc.getRegistSustain();
	break;
          case 192:
            probability -= this._targetPc.getRegistSustain();
            break;
          
          case 87:
            probability -= this._targetPc.getRegistStun();
            break;
          case 33:
            probability -= this._targetPc.getRegistStone();
            break;
          case 66:
          case 103:
          case 212:
            probability -= this._targetPc.getRegistSleep();
            break;
          case 50:
          case 80:
            probability -= this._targetPc.getRegistFreeze();
            break;
          case 20:
          case 40:
            probability -= this._targetPc.getRegistBlind();
            break;
        } 

        break;
    } 

    return probability;
  }

  public int calcMagicDamage(int skillId) {
    int damage = 0;
    switch (this._calcType) {
	case PC_PC:
		damage = calcPcMagicDamage(skillId);
		break;
	case PC_NPC:
		damage = calcNpcMagicDamage(skillId);
	}
    
    L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
    if (l1skills.getTarget().equals("attack") && l1skills.getArea() == 0) {// 單體攻擊魔法
      
		AttrAmuletEffect();// 觸發屬性守護之鍊附加技能 火焰之暈

		MoonAmuletEffect();// 觸發月亮項鍊附加技能 月光氣息

		Imperius_Tshirt_Effect();// 觸發奪魂T恤吸血功能
    } 
    
    damage = calcMrDefense(damage);
    return damage;
  }


  private int calcPcMagicDamage(int skillId) {
    if (this._targetPc == null) {
      return 0;
    }
    if (this._targetPc == this._pc) {
      return 0;
    }
    if (dmg0((L1Character)this._targetPc)) {
      return 0;
    }
    
    if (calcEvasion()) {
      return 0;
    }
    if (this._targetPc.getnewcharpra()) {
      return 0;
    }
    if (this._pc.getnewcharpra()) {
      return 0;
    }
    int dmg = 0;
    if (skillId == 108) {
      dmg = this._pc.getCurrentMp();
    } else {
      
      dmg = calcMagicDiceDamage(skillId);// 魔法基礎傷害計算
      dmg = (int)(dmg * getLeverage() / 10.0D);
    } 

    if (this._pc.get_other().get_Ran_PvP_magic_b() > 0 && _random.nextInt(100) + 1 <= this._pc.get_other().get_Ran_PvP_magic_b()) {
      if (this._pc.get_other().getPvP_magic_b() >= 1.0D) {
        dmg = (int)(dmg * this._pc.get_other().getPvP_magic_b());
      } else {
        dmg = (int)(dmg * (1.0D + this._pc.get_other().getPvP_magic_b()));
      } 
    }
    if (this._targetPc.get_other().get_PvP_magic_r() > 0) {
      dmg -= this._targetPc.get_other().get_PvP_magic_r();
    }

    dmg -= this._targetPc.getMagicDmgReduction() + this._targetPc.get_magic_reduction_dmg();
    
    dmg -= this._targetPc.dmgDowe();
    
    if (this._targetPc.getClanid() != 0) {
      dmg = (int)(dmg - getDamageReductionByClan(this._targetPc));
    }
    if (this._targetPc.isActived() && this._targetPc.getAu_OtherSet(1) > 0 && this._targetPc.getMap().isTeleportable() && this._targetPc.getAu_AutoSet(0) == 0 && !this._targetPc.hasSkillEffect(79501))
    {
      this._targetPc.setSkillEffect(79501, 2000);
    }

    if (this._pc.getmagicdmg() > 0) {
      dmg += this._pc.getmagicdmg();
    }
    if (this._targetPc.hasSkillEffect(88)) {
      int targetPcLvl = Math.max(this._targetPc.getLevel(), 50);
      dmg -= (targetPcLvl - 50) / 5 + 1;
    } 

    if (this._pc.isCrown()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Crown_ADD_MagicPC);
    } else if (this._pc.isKnight()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Knight_ADD_MagicPC);
    } else if (this._pc.isElf()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Elf_ADD_MagicPC);
    } else if (this._pc.isDarkelf()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Darkelf_ADD_MagicPC);
    } else if (this._pc.isWizard()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Wizard_ADD_MagicPC);
    } else if (this._pc.isDragonKnight()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.DragonKnight_ADD_MagicPC);
    } else if (this._pc.isIllusionist()) {
      
      dmg = (int)(dmg * Config_Pc_Damage.Illusionist_ADD_MagicPC);
    } 
    int ran = _random.nextInt(100) + 1;
    
    if (this._targetPc.getInventory().checkSkillType(133) && ran <= ConfigOther.armor_type21) {
      dmg = (int)(dmg * 0.99D);
    }
    if (this._targetPc.getInventory().checkSkillType(134) && ran <= ConfigOther.armor_type22) {
      dmg = (int)(dmg * 0.95D);
    }
    if (this._targetPc.getInventory().checkSkillType(135) && ran <= ConfigOther.armor_type23) {
      dmg = (int)(dmg * 0.9D);
    }
    if (this._targetPc.getInventory().checkSkillType(136) && ran <= ConfigOther.armor_type24) {
      dmg = (int)(dmg * 0.85D);
    }
    if (this._targetPc.getInventory().checkSkillType(137) && ran <= ConfigOther.armor_type25) {
      dmg = (int)(dmg * 0.8D);
    }
    
    if (this._pc.isWizard() && this._pc.getlogpcpower_SkillFor1() != 0 && this._pc.getlogpcpower_SkillFor1() >= 1) {
      if (RandomArrayList.getInc(100, 1) <= this._pc.getlogpcpower_SkillFor1()) {
        this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._pc.getId(), 5377));
        this._pc.sendPackets((ServerBasePacket)new S_SystemMessage("觸發 致命一擊"));
        this._pc.setchecklogpc(true);
      } else {
        this._pc.setchecklogpc(false);
      } 
    }

    if (this._pc.isWizard() && this._pc.getEsotericCount() != 0) {
      if (this._pc.getEsotericCount() == 4) {
        dmg = (int)(dmg * (1.0D + 0.12D * this._pc.getlogpcpower_SkillFor5()));
      } else if (this._pc.getEsotericCount() == 3) {
        dmg = (int)(dmg * (1.0D + 0.09D * this._pc.getlogpcpower_SkillFor5()));
      } else if (this._pc.getEsotericCount() == 2) {
        dmg = (int)(dmg * (1.0D + 0.06D * this._pc.getlogpcpower_SkillFor5()));
      } else if (this._pc.getEsotericCount() == 1) {
        dmg = (int)(dmg * (1.0D + 0.03D * this._pc.getlogpcpower_SkillFor5()));
      } 
    }

    if (this._pc.get魔法爆擊發動率() > 0 && this._pc.get魔法爆擊倍率() > 0.0D && 
      ran <= this._pc.get魔法爆擊發動率()) {
      dmg = (int)(dmg * this._pc.get魔法爆擊倍率());
    }
    
    if (this._targetPc.hasSkillEffect(68)) {
      dmg = (int)(dmg / ConfigOther.IMMUNE_TO_HARM);
    }
   if (this._targetPc.hasSkillEffect(8908)) {
      dmg /= 2;
    }
    
    if ((this._targetPc.hasSkillEffect(6685) || 
      this._targetPc.hasSkillEffect(6687) || 
      this._targetPc.hasSkillEffect(6688) || 
      this._targetPc.hasSkillEffect(6689)) && 
      _random.nextInt(100) < 10) {
      dmg /= 2;
      this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 6320));
    } 
    
    if (this._targetPc.hasSkillEffect(134) && 
      this._calcType == 1 && 
      this._targetPc.getWis() >= _random.nextInt(100)) {
      this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), 2));
      this._pc.receiveDamage((L1Character)this._targetPc, dmg, false, false);
      this._pc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 4395));
      dmg = 0;
      this._targetPc.killSkillEffectTimer(134);
    } 
    
    return Math.max(dmg, 0);
  }

  private int calcNpcMagicDamage(int skillId) {
    if (this._targetNpc == null) {
      return 0;
    }
    
    if (dmg0(_targetNpc)) {
		return 0;
	}
    
    int npcId = this._targetNpc.getNpcTemplate().get_npcId();
    Integer tgskill = L1AttackList.SKNPC.get(Integer.valueOf(npcId));
    if (tgskill != null && 
      !this._pc.hasSkillEffect(tgskill.intValue())) {
      return 0;
    }
    
    Integer tgpoly = L1AttackList.PLNPC.get(Integer.valueOf(npcId));
    if (tgpoly != null && 
      tgpoly.equals(Integer.valueOf(this._pc.getTempCharGfx()))) {
      return 0;
    }
    
    int dmg = 0;
    if (skillId == 108) {
      dmg = this._pc.getCurrentMp();
    } else {
      
      dmg = calcMagicDiceDamage(skillId);
      dmg = (int)(dmg * getLeverage() / 10.0D);
    } 
    
    boolean isNowWar = false;
    int castleId = L1CastleLocation.getCastleIdByArea((L1Character)this._targetNpc);
    if (castleId > 0) {
      isNowWar = ServerWarExecutor.get().isNowWar(castleId);
    }
    
    boolean isPet = false;
    if (this._targetNpc instanceof com.lineage.server.model.Instance.L1PetInstance) {
      isPet = true;
      if (this._targetNpc.getMaster().equals(this._pc)) {
        dmg = 0;
      }
    } 
    if (this._targetNpc instanceof L1SummonInstance) {
      L1SummonInstance summon = (L1SummonInstance)this._targetNpc;
      if (summon.isExsistMaster()) {
        isPet = true;
      }
      if (this._targetNpc.getMaster().equals(this._pc)) {
        dmg = 0;
      }
    } 
    
    if (!isNowWar && isPet && dmg != 0) {
      dmg /= 8;
    }

    
    if (this._pc.get_followmaster() != null) {
      dmg = (int)(dmg * 0.5D);
    }

    
    if (!this._targetNpc.getNpcTemplate().is_boss()) {
      L1Npc template = NpcTable.get().getTemplate(this._targetNpc.getNpcId());
      if (template.getImpl().equals("L1Monster") && 
        !this._pc.isActived() && this._pc.get_followmaster() == null && this._pc.hasSkillEffect(6931)) {
        this._pc.killSkillEffectTimer(6931);
        this._pc.setSkillEffect(6932, 1000);
      } 
    } 

    
    if (this._pc.get魔法爆擊發動率() > 0 && this._pc.get魔法爆擊倍率() > 0.0D && 
      _random.nextInt(100) + 1 <= this._pc.get魔法爆擊發動率()) {
      dmg = (int)(dmg * this._pc.get魔法爆擊倍率());
    }
    
    if (this._targetNpc.hasSkillEffect(68)) {// 聖界減傷
      dmg /= 2;
    }
    
    if (this._targetNpc.hasSkillEffect(134) && 
      this._calcType == 2 && 
      this._targetNpc.getWis() >= _random.nextInt(100)) {
      this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), 2));
      this._pc.receiveDamage((L1Character)this._targetNpc, dmg, false, false);
      this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 4395));
      dmg = 0;
      this._targetNpc.killSkillEffectTimer(134);
    } 
    
    if (this._targetNpc.hasSkillEffect(11059) && 
      this._calcType == 2) {
      this._pc.sendPacketsAll((ServerBasePacket)new S_DoActionGFX(this._pc.getId(), 2));
      this._pc.receiveDamage((L1Character)this._targetNpc, dmg, false, false);
      this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 4395));
      dmg = 0;
    } 
    
    return dmg;
  }
  
  private static boolean isInWarAreaAndWarTime(L1PcInstance pc) {
    int castleId = L1CastleLocation.getCastleIdByArea((L1Character)pc);
    if (castleId != 0 && 
      ServerWarExecutor.get().isNowWar(castleId)) {
      return true;
    }
    
    return false;
  }

  private void AttrAmuletEffect() {
    int rnd = this._pc.get_AttrAmulet_rnd();
    int dmg = this._pc.get_AttrAmulet_dmg();
    int gfxid = this._pc.get_AttrAmulet_gfxid();
    
    if (rnd <= 0) {
      return;
    }
    
    switch (this._calcType) {
      case 1:
        if (_random.nextInt(1000) < rnd) {
          if (this._targetPc.hasSkillEffect(68)) {
            dmg /= 2;
          }
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), gfxid));
          this._targetPc.receiveDamage((L1Character)this._pc, dmg, false, true);
        } 
        break;
      case 2:
        if (_random.nextInt(1000) < rnd) {
          if (this._targetNpc.hasSkillEffect(68)) {
            dmg /= 2;
          }
          this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), gfxid));
          this._targetNpc.receiveDamage((L1Character)this._pc, dmg);
        } 
        break;
    } 
  }

	/**
	 * 魔法基礎傷害計算
	 * 
	 * @param skillId
	 * @return
	 */
  private int calcMagicDiceDamage(int skillId) {
    L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
    int dice = l1skills.getDamageDice();
    int diceCount = l1skills.getDamageDiceCount();
    int value = l1skills.getDamageValue();
    int magicDamage = 0;
    int charaIntelligence = 0;
    
    for (int i = 0; i < diceCount; i++) {
      magicDamage += _random.nextInt(dice) + 1;
    }
    magicDamage += value;
    
    if (this._pc.getClanid() != 0) {
      magicDamage = (int)(magicDamage + getDamageUpByClan(this._pc));// 血盟魔法增傷
    }
    
    int spByItem = getTargetSp();// 計算施展者額外增加魔攻
    
    charaIntelligence = Math.max(this._pc.getInt() + spByItem - 12, 1);
    
    double attrDeffence = calcAttrResistance(l1skills.getAttr());
    
    double coefficient = Math.max(1.0D - attrDeffence + charaIntelligence * 3.0D / 32.0D, 0.0D);
    
    magicDamage *= coefficient;

if (l1skills.getSkillLevel() <= 6 || skillId == DISINTEGRATE) { // 小於六級魔法
	
	int addchance = 0;// 爆擊機率
	int addMcrichance = IntSp.getAddMCrichanceSkill(this._pc, this._pc.getInt());// 爆擊機率
	double MCriDmg = IntSp.getMCriDmgSkill(this._pc, this._pc.getInt());// 爆擊傷害
	
	// 7.6智力增加魔法爆擊機率
//	if (_pc.getBaseInt() >= ConfigSkill.MagicDiceLV) {// 純智力45以上
//		addchance += ConfigSkill.MagicDiceaddchance;
//	}
//	Integer chance = L1AttackList.INTCRI.get(Integer.valueOf(_pc.getInt()));
//	if (chance != null) {
//		addchance += chance;
//	}
	
	addchance += addMcrichance + _pc.getOriginalMagicCritical();// 其他增加魔法暴擊率

//	System.out.println("addMcrichance : " + addMcrichance + " getOriginalMagicCritical : " + _pc.getOriginalMagicCritical());
	
	if (_random.nextInt(100) < addchance) {// 成功觸發魔法爆擊
		magicDamage *= MCriDmg;// 1.5倍傷害
		_pc.setMagicCritical(true);// 魔法爆擊狀態
		
		int MagicCriticalgfx = ConfigOther.MagicCriticalgfx;
		
		if (MagicCriticalgfx > 0) {
			final S_SkillSound MagicCritical = new S_SkillSound(_pc.getId(),
					MagicCriticalgfx);
			_pc.sendPackets(MagicCritical);
		}
	}
}   
    magicDamage += this._pc.getOriginalMagicDamage() + this._pc.getMagicDmgModifier();
    
    return magicDamage;
  }

  public int calcHealing(int skillId) {
    L1Skills l1skills = SkillsTable.get().getTemplate(skillId);
    int dice = l1skills.getDamageDice();
    int value = l1skills.getDamageValue();
    int magicDamage = 0;
    int magicBonus = Math.min(getMagicBonus(), 10);
    int diceCount = value + magicBonus;
    for (int i = 0; i < diceCount; i++) {
      magicDamage += _random.nextInt(dice) + 1;
    }
    
    double alignmentRevision = 1.0D;
if (Configdead.PKHealing) {
    if (getLawful() > 0) { //治癒術正義邪惡判斷
      alignmentRevision += getLawful() / 32768.0D;
    }
}
    magicDamage = (int)(magicDamage * alignmentRevision);
    magicDamage = (int)(magicDamage * getLeverage() / 10.0D);
    return magicDamage;
  }

  
  private void Imperius_Tshirt_Effect() {
    int value = 0;
    int rnd = this._pc.get_Imperius_Tshirt_rnd();
    int min = this._pc.get_Tshirt_drainingHP_min();
    int max = this._pc.get_Tshirt_drainingHP_max();
    
    if (rnd <= 0) {
      return;
    }
    
    switch (this._calcType) {
      case 1:
        if (_random.nextInt(1000) < rnd) {
          value = _random.nextInt(max - min + 1) + min;
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), 11769));
          this._targetPc.receiveDamage((L1Character)this._pc, value, false, true);
          short newHp = (short)(this._pc.getCurrentHp() + value);
          this._pc.setCurrentHp(newHp);
        } 
        break;
      case 2:
        if (_random.nextInt(1000) < rnd) {
          value = _random.nextInt(max - min + 1) + min;
          this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), 11769));
          this._targetNpc.receiveDamage((L1Character)this._pc, value);
          short newHp = (short)(this._pc.getCurrentHp() + value);
          this._pc.setCurrentHp(newHp);
        } 
        break;
    } 
  }

  private int calcMrDefense(int dmg) {
    int mr = getTargetMr();
    double mrFloor = 0.0D;
    
    if (mr < 100) {
      mrFloor = Math.floor(((mr - this._pc.getOriginalMagicHit()) / 2));
    }
    else if (mr >= 100) {
      mrFloor = Math.floor(((mr - this._pc.getOriginalMagicHit()) / 10));
    } 
    double mrCoefficient = 0.0D;
    if (mr < 100) {
      mrCoefficient = 1.0D - 0.01D * mrFloor;
    }
    else if (mr >= 100) {
      mrCoefficient = 0.6D - 0.01D * mrFloor;
    } 
    
    int Mgs = 0;
    int othersp = IntSp.getIntSpSkillMgs(this._pc, this._pc.getInt());
    if (othersp != 0) {
      Mgs += othersp;
      
      if (Mgs < 0) {
        Mgs = 0;
      }
    } 

    if (!this._pc.getchecklogpc() && Mgs < _random.nextInt(100) + 1) {
      dmg = (int)(dmg * mrCoefficient);
    }

    this._pc.setchecklogpc(false);
    
    if (this._calcType == 1) {
      int othersp1 = PcMr.getIntSpSkillMgs(this._targetPc.getMr());
      
      if (othersp1 != 0) {
        dmg -= othersp1;
        if (dmg < 0) {
          dmg = 0;
        }
      } 
    } 
    return dmg;
  }


  
  private void MoonAmuletEffect() {
    int rnd = this._pc.get_MoonAmulet_rnd();
    int dmgmin = this._pc.get_MoonAmulet_dmg_min();
    int dmgmax = this._pc.get_MoonAmulet_dmg_max();
    int gfxid = this._pc.get_MoonAmulet_gfxid();
    
    if (rnd <= 0) {
      return;
    }
    
    int damage = 0;
    switch (this._calcType) {
    case PC_PC:
        if (_random.nextInt(1000) < rnd) {
          damage = _random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
          if (this._targetPc.hasSkillEffect(68)) {
            damage /= 2;
          }
          this._targetPc.sendPacketsAll((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), gfxid));
          this._targetPc.receiveDamage((L1Character)this._pc, damage, false, true);
        } 
        break;
    case PC_NPC:
        if (_random.nextInt(1000) < rnd) {
          damage = _random.nextInt(dmgmax - dmgmin + 1) + dmgmin;
          if (this._targetNpc.hasSkillEffect(68)) {
            damage /= 2;
          }
          this._targetNpc.broadcastPacketAll((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), gfxid));
          this._targetNpc.receiveDamage((L1Character)this._pc, damage);
        } 
        break;
    } 
  }

  public void commit(int damage, int drainMana) {
    if (this._calcType == 1 && 
      ConfigGuaji.Guaji_save && this._targetPc.isActived() && 
      this._targetPc.getLevel() <= ConfigGuaji.Guaji_level) {
      return;
    }
L1Character _target = null;
switch (_calcType) {
case PC_PC:
	_target = _targetPc;
	commitPc(damage, drainMana);
	break;

case PC_NPC:
	_target = _targetNpc;
	commitNpc(damage, drainMana);
	break;
}
if (ConfigOther.dmgspr && this._pc.hasSkillEffect(1688) && !this._pc.isActived()){
////加入傷害顯示優化寫法
//	int i = (int) ((damage / Math.pow(10, 0)) % 10) + ConfigOther.Attack_1;
//	int k = (int) ((damage / Math.pow(10, 1)) % 10) + ConfigOther.Attack_2;
//	int h = (int) ((damage / Math.pow(10, 2)) % 10) + ConfigOther.Attack_3;
//	int s = (int) ((damage / Math.pow(10, 3)) % 10) + ConfigOther.Attack_4;
//	int m = (int) ((damage / Math.pow(10, 4)) % 10) + ConfigOther.Attack_5;
//
//		if (damage <= 0) {
//			this._pc.sendPacketsAll(new S_SkillSound(_target.getId(),
//					13418));// Miss
//		} else if (damage > 0 && damage < 10) {
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					i));// 個位數
//		} else if (damage >= 10 && damage < 100) {
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					k));// 十位數
//		} else if (damage >= 100 && damage < 1000) {
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					h));// 百位數
//		} else if (damage >= 1000 && damage < 10000) {
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					h));// 百位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					s));// 千位數
//		} else if (damage >= 10000) {
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					i));// 個位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					k));// 十位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					h));// 百位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					s));// 千位數
//			this._pc.sendPackets(new S_SkillSound(_target.getId(),
//					m));// 萬位數
//		}
		//寫法2
				int units = damage % 10;
				int tens = (damage / 10) % 10;
				int hundreads = (damage / 100) % 10;
				int thousands = (damage / 1000) % 10;
				int tenthousands = (damage / 10000) % 10;
				if ((units > 0) || (tens > 0) || (hundreads > 0)
						|| (thousands > 0) || (tenthousands > 0)) {
					units += ConfigOther.Attack_1;
					final S_SkillSound units_s = new S_SkillSound(
							_target.getId(), units);
					_pc.sendPackets(units_s);
				}
				if ((tens > 0) || (hundreads > 0) || (thousands > 0)
						|| (tenthousands > 0)) {
					tens += ConfigOther.Attack_2;
					final S_SkillSound tens_s = new S_SkillSound(
							_target.getId(), tens);
					_pc.sendPackets(tens_s);
				}
				if ((hundreads > 0) || (thousands > 0) || (tenthousands > 0)) {
					hundreads += ConfigOther.Attack_3;
					final S_SkillSound hundreads_s = new S_SkillSound(
							_target.getId(), hundreads);
					_pc.sendPackets(hundreads_s);
				}
				if ((thousands > 0) || (tenthousands > 0)) {
					thousands += ConfigOther.Attack_4;
					final S_SkillSound thousands_s = new S_SkillSound(
							_target.getId(), thousands);
					_pc.sendPackets(thousands_s);
				}
				if (tenthousands > 0) {
					tenthousands += ConfigOther.Attack_5;
					final S_SkillSound tenthousands_s = new S_SkillSound(
							_target.getId(), tenthousands);
					_pc.sendPackets(tenthousands_s);
				}
//				int MagicCriticalgfx = ConfigOther.IntCriticalgfx;
//				if (_pc.isMagicCritical() && MagicCriticalgfx > 0) {
//					final S_SkillSound Critical = new S_SkillSound(_pc.getId(),
//							MagicCriticalgfx);
//					_pc.sendPackets(Critical);
//				}
				if (damage <= 0) {
					final S_SkillSound miss = new S_SkillSound(_target.getId(),
							13418);
					_pc.sendPackets(miss);
				}
				//寫法2
}
//    int i = (int)(damage / Math.pow(10.0D, 0.0D) % 10.0D) + ConfigOther.Attack_1;
//    int k = (int)(damage / Math.pow(10.0D, 1.0D) % 10.0D) + ConfigOther.Attack_2;
//    int h = (int)(damage / Math.pow(10.0D, 2.0D) % 10.0D) + ConfigOther.Attack_3;
//    int s = (int)(damage / Math.pow(10.0D, 3.0D) % 10.0D) + ConfigOther.Attack_4;
//    int m = (int)(damage / Math.pow(10.0D, 4.0D) % 10.0D) + ConfigOther.Attack_5;
//    switch (this._calcType) {
//      
//      case 1:
//        commitPc(damage, drainMana);
//       if (this._pc.hasSkillEffect(1688) && ConfigOther.dmgspr) { //要關閉內掛顯示傷害加入這段 && !this._pc.isActived()
//          if (damage > 0 && damage < 10) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i)); break;
//          }  if (damage >= 10 && damage < 100) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k)); break;
//          }  if (damage >= 100 && damage < 1000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h)); break;
//          }  if (damage >= 1000 && damage < 10000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), s)); break;
//          }  if (damage >= 10000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), h));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), s));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetPc.getId(), m));
//          } 
//        } 
//        break;
//      
//      case 2:
//        commitNpc(damage, drainMana);
//       if (this._pc.hasSkillEffect(1688) && ConfigOther.dmgspr) { //要關閉內掛顯示傷害加入這段 && !this._pc.isActived()
//          if (damage > 0 && damage < 10) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i)); break;
//          } 
//          if (damage >= 10 && damage < 100) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k)); break;
//          } 
//          if (damage >= 100 && damage < 1000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h)); break;
//          } 
//          if (damage >= 1000 && damage < 10000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), s)); break;
//          } 
//          if (damage >= 10000) {
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), i));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), k));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), h));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), s));
//            this._pc.sendPackets((ServerBasePacket)new S_SkillSound(this._targetNpc.getId(), m));
//          } 
//        } 
//        break;
//    } 
    
    if (!ConfigAlt.ALT_ATKMSG) {
      return;
    }
    switch (this._calcType) {
      case PC_PC:
        if (this._pc.getAccessLevel() == 0 && 
          this._targetPc.getAccessLevel() == 0) {
          return;
        }
        break;

      case PC_NPC:
        if (this._pc.getAccessLevel() == 0) {
          return;
        }
        break;
    } 

    switch (this._calcType) {
      case PC_PC:
        if (this._pc.getAccessLevel() > 0) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("對PC送出技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetPc.getName()) + " ");
          atkMsg.append("傷害: " + damage);
          atkMsg.append(" 目標HP:" + this._targetPc.getCurrentHp());
          
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        if (this._targetPc.getAccessLevel() > 0) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("受到PC技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetPc.getName()) + " ");
          atkMsg.append("傷害: " + damage);
          atkMsg.append(" 目標HP:" + this._targetPc.getCurrentHp());
          
          this._targetPc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        break;
      case PC_NPC:
        if (this._pc.getAccessLevel() > 0) {
          StringBuilder atkMsg = new StringBuilder();
          atkMsg.append("對NPC送出技能: ");
          atkMsg.append(String.valueOf(this._pc.getName()) + ">");
          atkMsg.append(String.valueOf(this._targetNpc.getNameId()) + " ");
          atkMsg.append("傷害: " + damage);
          atkMsg.append(" 目標HP:" + this._targetNpc.getCurrentHp());
          
          this._pc.sendPackets((ServerBasePacket)new S_ServerMessage(166, atkMsg.toString()));
        } 
        break;
    } 
  }

  private void commitPc(int damage, int drainMana) {
    try {
      if (drainMana > 0) {
        if (this._targetPc.getCurrentMp() > 0) {
          drainMana = Math.min(drainMana, this._targetPc.getCurrentMp());
          int newMp = this._pc.getCurrentMp() + drainMana;
          this._pc.setCurrentMp(newMp);
        } else {
          
          drainMana = 0;
        } 
      }
      this._targetPc.receiveManaDamage((L1Character)this._pc, drainMana);
      this._targetPc.receiveDamage((L1Character)this._pc, damage, true, false);
    }
    catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
  }

  
  private boolean isInWarAreaAndWarTime(L1PcInstance pc, L1PcInstance target) {
    int castleId = L1CastleLocation.getCastleIdByArea((L1Character)pc);
    int targetCastleId = L1CastleLocation.getCastleIdByArea((L1Character)target);
    if (castleId != 0 && targetCastleId != 0 && castleId == targetCastleId && 
      ServerWarExecutor.get().isNowWar(castleId)) {
      return true;
    }
    
    return false;
  }

  private void commitNpc(int damage, int drainMana) {
    try {
      if (drainMana > 0) {
        if (this._targetNpc.getCurrentMp() > 0) {
          int drainValue = this._targetNpc.drainMana(drainMana);
          int newMp = this._pc.getCurrentMp() + drainValue;
          this._pc.setCurrentMp(newMp);
        } else {
          
          drainMana = 0;
        } 
      }
      this._targetNpc.ReceiveManaDamage((L1Character)this._pc, drainMana);
      this._targetNpc.receiveDamage((L1Character)this._pc, damage);
    }
    catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    } 
  }
}