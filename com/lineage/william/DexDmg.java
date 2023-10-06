package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;



public class DexDmg
{
  private int _DexDmg;
  private int _AddDmg;
  private int _AddHit;
  private int _AddCrichance;
  private int _CriDmg;
  
  public DexDmg(int i, int AddDmg, int AddHit, int AddCrichance,int CriDmg) {
    this._DexDmg = i;
    this._AddDmg = AddDmg;
    this._AddHit = AddHit;
    this._AddCrichance = AddCrichance;
    this._CriDmg = CriDmg;
  }


  
  public int getDexDmg() {
    return this._DexDmg;
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

  
  public static int getDexDmgSkill(L1PcInstance pc, int dex) {
    int dmg = 0;
    
    DexDmg DexDmgSkill = PcDexDmg.getInstance().getTemplate(dex);

    
    if (dex >= DexDmgSkill.getDexDmg()) {
      dmg = DexDmgSkill.getAddDmg();
    }
    return dmg;
  }

  
  public static int getDexHitSkill(L1PcInstance pc, int dex) {
    int hit = 0;
    
    DexDmg DexDmgSkill = PcDexDmg.getInstance().getTemplate(dex);

    
    if (dex >= DexDmgSkill.getDexDmg()) {
      hit = DexDmgSkill.getAddHit();
    }
    return hit;
  }
  
  public static int getAddCrichanceSkill(L1PcInstance pc, int dex) {
	    int Crichance = 0;
	    
	    DexDmg DexDmgSkill = PcDexDmg.getInstance().getTemplate(dex);

	    if (dex >= DexDmgSkill.getDexDmg()) {
	    	Crichance = DexDmgSkill.getAddCrichance();
	      }
	    return Crichance;
	  }
	  
  public static int getCriDmgSkill(L1PcInstance pc, int dex) {
		int CriDmg = 0;
		    
		DexDmg DexDmgSkill = PcDexDmg.getInstance().getTemplate(dex);

		if (dex >= DexDmgSkill.getDexDmg()) {
			CriDmg = DexDmgSkill.getCriDmg();
		}
		 return CriDmg;
		}
}