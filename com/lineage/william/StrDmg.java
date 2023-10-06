package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;



public class StrDmg
{
  private int _StrDmg;
  private int _AddDmg;
  private int _AddHit;
  private int _AddCrichance;
  private int _CriDmg;
  
  public StrDmg(int i, int AddDmg, int AddHit, int AddCrichance,int CriDmg) {
    this._StrDmg = i;
    this._AddDmg = AddDmg;
    this._AddHit = AddHit;
    this._AddCrichance = AddCrichance;
    this._CriDmg = CriDmg;
  }


  
  public int getStrDmg() {
    return this._StrDmg;
  }
  public int getAddDmg() {
    return this._AddDmg;
  }
  public int getAddHit() {
    return this._AddHit;
  }
  public int getAddCrichance() {
    return this._AddCrichance;
  }
  public int getCriDmg() {
	return this._CriDmg;
  }

  
  public static int getStrDmgSkill(L1PcInstance pc, int str) {
    int dmg = 0;
    
    StrDmg StrDmgSkill = PcStrDmg.getInstance().getTemplate(str);

    
    if (str >= StrDmgSkill.getStrDmg()) {
      dmg = StrDmgSkill.getAddDmg();
    }
    return dmg;
  }

  
  public static int getStrHitSkill(L1PcInstance pc, int str) {
    int hit = 0;
    
    StrDmg StrDmgSkill = PcStrDmg.getInstance().getTemplate(str);

    
    if (str >= StrDmgSkill.getStrDmg()) {
      hit = StrDmgSkill.getAddHit();
    }
    return hit;
  }

  public static int getAddCrichanceSkill(L1PcInstance pc, int str) {
    int Crichance = 0;
    
    StrDmg StrDmgSkill = PcStrDmg.getInstance().getTemplate(str);

    
    if (str >= StrDmgSkill.getStrDmg()) {
    	Crichance = StrDmgSkill.getAddCrichance();
    }
    return Crichance;
  }
  
  public static int getCriDmgSkill(L1PcInstance pc, int str) {
	    int CriDmg = 0;
	    
	    StrDmg StrDmgSkill = PcStrDmg.getInstance().getTemplate(str);

	    
	    if (str >= StrDmgSkill.getStrDmg()) {
	    	CriDmg = StrDmgSkill.getCriDmg();
	    }
	    return CriDmg;
	  }
}