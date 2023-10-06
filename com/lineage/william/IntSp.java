package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;


public class IntSp
{
  private int _IntSp;
  private int _Sp;
  private int _Mgs;
  private int _Mgs2;
  private int _AddCrichance;
  private int _CriDmg;
  private int _AddMCrichance;
  private double _MCriDmg;
  
  public IntSp(int i, int Sp, int Mgs, int Mgs2, int AddCrichance,int CriDmg, int AddMCrichance,double MCriDmg) {
    this._IntSp = i;
    this._Sp = Sp;
    this._Mgs = Mgs;
    this._Mgs2 = Mgs2;
    this._AddCrichance = AddCrichance;
    this._CriDmg = CriDmg;
    this._AddMCrichance = AddMCrichance;
    this._MCriDmg = MCriDmg;
  }
  
  public int getIntSp() {
    return this._IntSp;
  }
  public int getSp() {
    return this._Sp;
  }
  public int getMgs() {
    return this._Mgs;
  }
  public int getMgs2() {
    return this._Mgs2;
  }
  public int getAddCrichance() {
	return this._AddCrichance;
  }
  public int getCriDmg() {
	return this._CriDmg;
  }
  public int getAddMCrichance() {
	return this._AddMCrichance;
  }
  public double getMCriDmg() {
	return this._MCriDmg;
  }

  
  public static int getIntSpSkill(L1PcInstance pc, int pcint) {
    int dmg = 0;
    
    IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

    
    if (pcint >= IntSpSkill.getIntSp()) {
      dmg = IntSpSkill.getSp();
    }
    
    return dmg;
  }
  
  public static int getIntSpSkillMgs(L1PcInstance pc, int pcint) {
    int dmg = 0;
    
    IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

    
    if (pcint >= IntSpSkill.getIntSp()) {
      dmg = IntSpSkill.getMgs();
    }
    return dmg;
  }
  
  public static int getIntSpSkillMgs2(L1PcInstance pc, int pcint) {
    int dmg = 0;
    
    IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

    
    if (pcint >= IntSpSkill.getIntSp()) {
      dmg = IntSpSkill.getMgs2();
    }
    return dmg;
  }
  
  public static int getAddCrichanceSkill(L1PcInstance pc, int pcint) {
	    int Crichance = 0;
	    
	    IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

	    if (pcint >= IntSpSkill.getIntSp()) {
	    	Crichance = IntSpSkill.getAddCrichance();
	      }
	    return Crichance;
	  }
	  
public static int getCriDmgSkill(L1PcInstance pc, int pcint) {
		int CriDmg = 0;
		    
		IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

		if (pcint >= IntSpSkill.getIntSp()) {
			CriDmg = IntSpSkill.getCriDmg();
		}
		 return CriDmg;
		}

public static int getAddMCrichanceSkill(L1PcInstance pc, int pcint) {
    int MCrichance = 0;
    
    IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

    if (pcint >= IntSpSkill.getIntSp()) {
    	MCrichance = IntSpSkill.getAddMCrichance();
      }
    return MCrichance;
  }
  
public static double getMCriDmgSkill(L1PcInstance pc, int pcint) {
	double MCriDmg = 0;
	    
	IntSp IntSpSkill = PcIntSp.getInstance().getTemplate(pcint);

	if (pcint >= IntSpSkill.getIntSp()) {
		MCriDmg = IntSpSkill.getMCriDmg();
	}
	 return MCriDmg;
	}
}