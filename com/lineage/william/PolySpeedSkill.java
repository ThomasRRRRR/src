package com.lineage.william;

import com.lineage.server.model.Instance.L1PcInstance;



public class PolySpeedSkill
{
  private int _polyid;
  private int _attack_speed;
  private int _walk_speed;
  private int _sleep;
  
  public PolySpeedSkill(int polyid, int atk, int walk, int sleep) {
    this._polyid = polyid;
    this._attack_speed = atk;
    this._walk_speed = walk;
    this._sleep = sleep;
  }


  
  public int getpolyid() {
    return this._polyid;
  }
  public int getattack_speed() {
    return this._attack_speed;
  }
  public int getwalk_speed() {
    return this._walk_speed;
  }
  public int getsleep() {
	return this._sleep;
  }

  
  public static int getattack_speed(L1PcInstance pc, int gfx) {
    int attack_speed = 0;
    
    PolySpeedSkill attackS = PolySpeed.getInstance().getTemplate(gfx);

    if (gfx >= attackS.getpolyid()) {
    	attack_speed = attackS.getattack_speed();
    }
    return attack_speed;
  }

  public static int getwalk_speed(L1PcInstance pc, int gfx) {
	    int walk_speed = 0;
	    
	    PolySpeedSkill walkS = PolySpeed.getInstance().getTemplate(gfx);

	    if (gfx >= walkS.getpolyid()) {
	    	walk_speed = walkS.getattack_speed();
	    }
	    return walk_speed;
	  }
  
  public static int getsleep(L1PcInstance pc, int gfx) {
	    int sleep = 0;
	    
	    PolySpeedSkill Psleep = PolySpeed.getInstance().getTemplate(gfx);

	    if (gfx >= Psleep.getpolyid()) {
	    	sleep = Psleep.getattack_speed();
	    }
	    return sleep;
	  }
}