package com.lineage.server.serverpackets;

import java.util.concurrent.atomic.AtomicInteger;

import com.lineage.config.ConfigOther;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.utils.Random;
import com.lineage.server.world.World;

/**
 * 物件攻擊(遠程-物理攻擊 PC/NPC共用)
 * 
 * @author dexc
 *
 */
public class S_UseArrowSkill extends ServerBasePacket {
    private static final AtomicInteger _sequentialNumber;

    static {
        _sequentialNumber = new AtomicInteger(0);
    }
	private byte[] _byte = null;   
    private int _actid;
	/**
	 * 物件攻擊 - <font color="#ff0000">命中</font>(遠程-物理攻擊 PC/NPC共用)
	 * 
	 * @param cha
	 *            執行者
	 * @param targetobj
	 *            目標OBJID
	 * @param spellgfx
	 *            遠程動畫編號
	 * @param x
	 *            目標X
	 * @param y
	 *            目標Y
	 * @param dmg
	 *            傷害力
	 */
	public S_UseArrowSkill(final L1Character cha, int targetobj,
			final int spellgfx, final int x, final int y, int dmg) {

		int aid = 1;
		// 外型編號改變動作
		switch (cha.getTempCharGfx()) {
		case 3860:// 妖魔弓箭手
			aid = 21;
			break;

		case 2716:// 古代亡靈
			aid = 19;
			break;
		}

		/*
		 * 0000: 5e 01 8e 24 bb 01 a4 6c 00 00 0a 00 05 52 01 00
		 * ^..$...l.....R.. 0010: 00 42 00 00 c3 83 e1 7e c1 83 e5 7e 00 00 00
		 * 85 .B.....~...~....
		 * 
		 * 0000: 5e 01 8e 24 bb 01 a4 6c 00 00 0d 00 05 52 01 00
		 * ^..$...l.....R.. 0010: 00 42 00 00 c3 83 e1 7e c1 83 e5 7e 00 00 00
		 * ee .B.....~...~....
		 * 
		 * 0000: 5e 01 8e 24 bb 01 3c 20 00 00 0b 00 05 52 01 00 ^..$..<
		 * .....R.. 0010: 00 42 00 00 c3 83 e1 7e c0 83 e5 7e 00 00 00 58
		 * .B.....~...~...X
		 */
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(aid);// 動作代號
		this.writeD(cha.getId());// 執行者OBJID
		this.writeD(targetobj);// 目標OBJID
		// 改無動作 by wei512

		/** 特定外形怪物不會有受傷動作 */ // TODO
		L1Object obj = World.get().findObject(targetobj);
		if (obj instanceof L1NpcInstance)
		{
			L1NpcInstance npc = (L1NpcInstance)obj;
			int bow_GFX_Arrow[] = ConfigOther.AtkNo;
			int ai[];
			int k = (ai = bow_GFX_Arrow).length;
			for (int i = 0; i < k; i++)
			{
				int gfx = ai[i];
				if (npc.getTempCharGfx() == gfx)
					targetobj = 0;
			}

			switch (npc.getTempCharGfx())
			{
			case 2544: 
			case 10913: 
				targetobj = 0;
				break;
			}
		}
		if (obj instanceof L1PcInstance)
		{
			L1PcInstance pc1 = (L1PcInstance)obj;
			int atkpc[] = ConfigOther.AtkNo_pc;
			int ai1[];
			int l = (ai1 = atkpc).length;
			for (int j = 0; j < l; j++)
			{
				int gfx = ai1[j];
				if (pc1.getTempCharGfx() == gfx)
					targetobj = 0;
			}

		}
		
		if (ConfigOther.Polyatk) {
			if (dmg > 0) {// 順跑防暴衝 
                if(obj instanceof L1PcInstance) {
                    this.writeH(0x00); // 伤害值
                	L1PcInstance pc = (L1PcInstance)obj;
        			pc.sendPackets(new S_DoActionGFX(pc.getId(),2));// 順跑防暴衝
        			pc.broadcastPacket(new S_DoActionGFX(pc.getId(),2));// 順跑防暴衝    
                } else {
                	this.writeH(dmg); // 伤害值
                }
			} else {
				this.writeH(0x00); // 伤害值
			}// 順跑防暴衝 

		} else {
        
			if (dmg > 0) {
				this.writeH(dmg); // 傷害值
			} else {
				this.writeH(0x00); // 傷害值
			}
		
		}

		this.writeC(cha.getHeading());// 新面向

		// 以原子方式將當前值加 1。
		this.writeD(0x00000152);

		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x7f);// ??
		this.writeH(cha.getX());// 執行者X點
		this.writeH(cha.getY());// 執行者Y點
		this.writeH(x);// 目標X點
		this.writeH(y);// 目標Y點

		this.writeD(0x00000000);
		this.writeC(0x00);
		// this.writeC(0x00);
		// this.writeC(0x00);
		// this.writeC(0x00);
	}

	/**
	 * 物件攻擊 - <font color="#ff0000">未命中</font>(遠程-物理攻擊 PC/NPC共用) 空攻擊使用
	 * 
	 * @param cha
	 *            執行者
	 * @param spellgfx
	 *            遠程動畫編號
	 * @param x
	 *            目標X
	 * @param y
	 *            目標Y
	 */
	public S_UseArrowSkill(final L1Character cha, final int spellgfx,
			final int x, final int y) {

		int aid = 1;
		// 外型編號改變動作
		if (cha.getTempCharGfx() == 3860) {
			aid = 21;
		}
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(aid);// 動作代號
		this.writeD(cha.getId());// 執行者OBJID
		this.writeD(0x00);// 目標OBJID
		this.writeH(0x00);// 傷害力
		this.writeC(cha.getHeading());// 新面向

		// 以原子方式將當前值加 1。
		this.writeD(0x00000152);

		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x7f);// ??
		this.writeH(cha.getX());// 執行者X點
		this.writeH(cha.getY());// 執行者Y點
		this.writeH(x);// 目標X點
		this.writeH(y);// 目標Y點

		this.writeD(0x00000000);
		this.writeC(0x00);
		// this.writeC(0x00);
		// this.writeC(0x00);
		// this.writeC(0x00);
	}

	/**
	 * 物件攻擊 <font color="#ff0000">遠距離未命中</font>
	 * 
	 * @param cha
	 * @param spellgfx
	 * @param targetx
	 * @param targety
	 * @param actId
	 */
	public S_UseArrowSkill(L1Character cha, int spellgfx, int targetx,
			int targety, int actId) {
		_byte = null;
		_actid = 1;
		switch (cha.getTempCharGfx()) {
		case 3860:// 妖魔弓箭手
			_actid = 21;
			break;
		case 2716:// 古代亡靈
			_actid = 19;
			break;
		case 5127:// 腐爛的骷髏弓箭手
		case 10649:// 腐蝕的 骷髏弓箭手
		case 11714:// 漆黑的骷髏弓箭手
			_actid = 30;
			break;
		case 10566:// 河太郎
			_actid = 18;
			break;
		}
		writeC(S_OPCODE_ATTACKPACKET);
		if (Random.nextInt(100) < 25) {
			_actid = SpecialActid(cha);
		}
		if (actId > 1) {// 有指定攻擊動作編號
			_actid = actId;
		}
		writeC(_actid);
		writeD(cha.getId());
		writeD(0);
		writeH(0);
		writeC(cha.getHeading());
		writeD(S_UseArrowSkill._sequentialNumber.incrementAndGet());
		writeH(spellgfx);
		writeC(0);
		writeH(cha.getX());
		writeH(cha.getY());
		writeH(targetx);
		writeH(targety);
		writeD(0);
		writeC(0);
	}
	
	/**
	 * 物件攻擊 - <font color="#ff0000">遠距離空擊</font>
	 * 
	 * @param pc
	 */
    public S_UseArrowSkill(L1Character cha) {
        _byte = null;
        _actid = 1;
        switch (cha.getTempCharGfx()) {
            case 3860: {
                _actid = 21;
                break;
            }
            case 2716: {
                _actid = 19;
                break;
            }
            case 5127:
            case 10649:
            case 11714: {
                _actid = 30;
                break;
            }
            case 10566: {
                _actid = 18;
                break;
            }
        }
        writeC(30);
        if (Random.nextInt(100) < 25) {
            _actid = SpecialActid(cha);
        }
        writeC(_actid);
        writeD(cha.getId());
        this.writeD(0x00);
		this.writeH(0x01); // damage
        writeC(cha.getHeading());
        writeD(S_UseArrowSkill._sequentialNumber.incrementAndGet());
        writeH(2510);
        writeC(0);
        writeH(cha.getX());
        writeH(cha.getY());
        writeH(0);
        writeH(0);
        writeD(0);
        writeC(0);
    }
    
	/**
	 * 取回特定外型的特殊攻擊動作(遠攻用)
	 * 
	 * @param pc
	 * @return
	 */
	private int SpecialActid(L1Character cha) {
		if (cha instanceof L1PcInstance) {// 執行者為PC
			L1PcInstance pc = (L1PcInstance) cha;
			int tempgfxid = cha.getTempCharGfx();
			L1ItemInstance weapon = pc.getWeapon();
			if (weapon == null) {// 空手
				return _actid;
			}
			int weapontype = weapon.getItem().getType1();

			if ((tempgfxid == 13723) && (weapontype == 20)) {// 真男精靈 拿弓
				_actid = 75;
			}
			if ((tempgfxid == 13725) && (weapontype == 20)) {// 真女精靈 拿弓
				_actid = 75;
			}
			if (tempgfxid == 13218) {// 日本變身-姬武者
				_actid = 30;
			}
			if (tempgfxid == 12314) {// 90級變身[絲莉安]
				_actid = 30;
			}
		}
		return _actid;
	}
	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
