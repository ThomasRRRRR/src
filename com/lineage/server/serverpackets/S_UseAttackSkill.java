package com.lineage.server.serverpackets;

import com.lineage.config.ConfigOther;
import com.lineage.server.ActionCodes;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.utils.Random;
import com.lineage.server.world.World;
import static com.lineage.server.model.skill.L1SkillId.SHAPE_CHANGE;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 物件攻擊(技能使用)
 * @author dexc
 *
 */
public class S_UseAttackSkill extends ServerBasePacket {

	private static AtomicInteger _sequentialNumber = new AtomicInteger(4500000); 

	private byte[] _byte = null;

	private int SpecialActid(L1Character cha)
	{
		int actionId = 18;
		int tempgfxid = cha.getTempCharGfx();
		if (Random.nextInt(100) < 25)
		{
			if (tempgfxid == 13727 || tempgfxid == 13729)
				actionId = 31;
			if (tempgfxid == 13731 || tempgfxid == 13733)
				actionId = 31;
		}
		if (tempgfxid == 5727 || tempgfxid == 5730)
			actionId = 19;
		if (tempgfxid == 5733 || tempgfxid == 5736)
			actionId = 1;
		return actionId;
	}

	/**
	 * 物件攻擊(武器 技能使用-不需動作代號-不送出傷害)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param motion 具有執行者
	 */
	public S_UseAttackSkill(final L1Character cha, final int targetobj, final int spellgfx,
			final int x, final int y, final int actionId, final boolean motion) {
		this.buildPacket(cha, targetobj, spellgfx, x, y, actionId, 0, motion);
	}

	/**
	 * 物件攻擊(NPC / PC 技能使用)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param dmg 傷害力
	 */
	public S_UseAttackSkill(final L1Character cha, final int targetobj, final int spellgfx,
			final int x, final int y, final int actionId, final int dmg) {
		this.buildPacket(cha, targetobj, spellgfx, x, y, 18, dmg, true);
	}

	
	public S_UseAttackSkill(L1Character cha, int targetobj, int x, int y, int data[], boolean withCastMotion)
	{
		_byte = null;
		buildPacket(cha, targetobj, x, y, data, withCastMotion);
	}

	private void buildPacket(L1Character cha, int targetobj, int x, int y, int data[], boolean withCastMotion)
	{
		if ((cha instanceof L1PcInstance) && cha.hasSkillEffect(67) && data[0] == 18)
		{
			int tempchargfx = cha.getTempCharGfx();
			if (tempchargfx == 5727 || tempchargfx == 5730)
				data[0] = 19;
			else
			if (tempchargfx == 5733 || tempchargfx == 5736)
				data[0] = 1;
		}
		if (cha.getTempCharGfx() == 4013)
			data[0] = 1;
		int newheading = calcheading(cha.getX(), cha.getY(), x, y);
		cha.setHeading(newheading);
		writeC(30);
		writeC(data[0]);
		writeD(withCastMotion ? cha.getId() : 0);
		writeD(targetobj);
		writeH(data[1]);
		writeC(newheading);
		writeD(_sequentialNumber.incrementAndGet());
		writeH(data[2]);
		writeC(data[3]);
		writeH(cha.getX());
		writeH(cha.getY());
		writeH(x);
		writeH(y);
		writeC(0);
		writeC(0);
		writeC(0);
	}

	/**
	 * 物件攻擊(技能使用 - PC/NPC共用)
	 * @param cha 執行者
	 * @param targetobj 目標OBJID
	 * @param spellgfx 遠程動畫編號
	 * @param x X點
	 * @param y Y點
	 * @param actionId 動作代號
	 * @param dmg 傷害力
	 * @param withCastMotion 具有執行者
	 */
	private void buildPacket(final L1Character cha, int targetobj, 
			final int spellgfx,
			final int x, final int y, 
			int actionId, 
			final int dmg, 
			final boolean withCastMotion) {
		if (cha instanceof L1PcInstance) {
			// 變身中變動作代號異動
			if (cha.hasSkillEffect(SHAPE_CHANGE)
					&& (actionId == ActionCodes.ACTION_SkillAttack)) {
				
				final int tempchargfx = cha.getTempCharGfx();
				if ((tempchargfx == 5727) || (tempchargfx == 5730)) {
					// 物件具有變身 改變動作代號
					actionId = ActionCodes.ACTION_SkillBuff;
					
				} else if ((tempchargfx == 5733) || (tempchargfx == 5736)) {
					// 物件具有變身 改變動作代號
					actionId = ActionCodes.ACTION_Attack;
				}
				
				actionId = SpecialActid(cha);
			}
		}
		// 火靈之主動作代號強制變更
		if (cha.getTempCharGfx() == 4013) {
			actionId = ActionCodes.ACTION_Attack;
		}

		// 設置新面向
		final int newheading = calcheading(cha.getX(), cha.getY(), x, y);
		cha.setHeading(newheading);
		/*
		0000: 5e 12 1a cc bd 01 a4 6c 00 00 04 00 05 a3 d2 bd    ^......l........
		0010: 01 a7 00 06 c3 83 e1 7e c1 83 e5 7e 00 00 00 af    .......~...~....

		0000: 5e 12 1a cc bd 01 a4 6c 00 00 07 00 05 ff d6 bd    ^......l........
		0010: 01 a7 00 06 c3 83 e1 7e c1 83 e5 7e 00 00 00 1a    .......~...~....

		0000: 5e 12 1a cc bd 01 3c 20 00 00 07 00 05 f2 da bd    ^.....< ........
		0010: 01 a7 00 06 c3 83 e1 7e c0 83 e5 7e 00 00 00 a9    .......~...~....
		// 吸吻
		0000: 5e 12 e1 b1 63 00 a9 1f 00 00 23 00 06 93 c4 65    ^...c.....#....e
		0010: 00 ec 00 00 68 7f 96 81 67 7f 96 81 00 00 00 19    ....h..g......
		*/
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(actionId);// 動作代號
		this.writeD(withCastMotion ? cha.getId() : 0x00000000);// 執行者OBJID

		
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
		this.writeD(targetobj);// 目標OBJID
        if (dmg > 0) {// 順跑防暴衝
            if(obj instanceof L1PcInstance)
            {
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
        
//		if (dmg > 0) {
//			this.writeH(dmg); // 傷害值
//		} else {
//			this.writeH(0x00); // 傷害值
//		}
        
		this.writeC(newheading);// 新面向
		
		// 以原子方式将当前值加 1。
		this.writeD(_sequentialNumber.incrementAndGet());
		
		this.writeH(spellgfx);// 遠程動畫編號
		this.writeC(0x00); // 具備飛行動畫:6, 不具備飛行動畫:0
		this.writeH(cha.getX());// 執行者X點
		this.writeH(cha.getY());// 執行者Y點
		this.writeH(x);// 目標X點
		this.writeH(y);// 目標Y點

		this.writeD(0x00000000);
		this.writeC(0x00);
		//this.writeC(0x00);
		//this.writeC(0x00);
		//this.writeC(0x00);
	}

	private static int calcheading(int myx, int myy, int tx, int ty)
	{
		int newheading = 0;
		if (tx > myx && ty > myy)
			newheading = 3;
		if (tx < myx && ty < myy)
			newheading = 7;
		if (tx > myx && ty == myy)
			newheading = 2;
		if (tx < myx && ty == myy)
			newheading = 6;
		if (tx == myx && ty < myy)
			newheading = 0;
		if (tx == myx && ty > myy)
			newheading = 4;
		if (tx < myx && ty > myy)
			newheading = 5;
		if (tx > myx && ty < myy)
			newheading = 1;
		return newheading;
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
