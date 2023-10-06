package com.lineage.server.serverpackets;

import com.lineage.config.ConfigOther;
import com.lineage.server.ActionCodes;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.world.World;

// Referenced classes of package com.lineage.server.serverpackets:
//			ServerBasePacket

public class S_AttackPacketNpc extends ServerBasePacket
{

	private byte[] _byte = null;

	/**
	 * 物件攻擊 - <font color="#ff0000">命中</font>(NPC用  - 近距離)
	 * @param npc 執行者
	 * @param target  目標
	 * @param type 動作編號
	 * @param dmg 傷害值
	 */
	public S_AttackPacketNpc(L1NpcInstance npc, L1Character target, int actid, int dmg)
	{
		writeC(S_OPCODE_ATTACKPACKET);
		writeC(actid);
		writeD(npc.getId());// 執行者OBJID
		
//		writeD(target.getId());// 被攻擊者OBJID
		
		int targetobj = target.getId();
		/**特定外形怪物不會有受傷動作*/ //TODO  
		L1Object obj = World.get().findObject(targetobj);
		if (obj instanceof L1NpcInstance)
		{
			L1NpcInstance tgnpc = (L1NpcInstance)obj;
			int bow_GFX_Arrow[] = ConfigOther.AtkNo;
			int ai[];
			int k = (ai = bow_GFX_Arrow).length;
			for (int i = 0; i < k; i++)
			{
				int gfx = ai[i];
				if (tgnpc.getTempCharGfx() == gfx)
					targetobj = 0;
			}

			switch (tgnpc.getTempCharGfx())
			{
			case 2544: 
			case 10913: 
				targetobj = 0;
				break;
			}
		}
		if (obj instanceof L1PcInstance)
		{
			L1PcInstance pc = (L1PcInstance)obj;
			int atkpc[] = ConfigOther.AtkNo_pc;
			int ai1[];
			int l = (ai1 = atkpc).length;
			for (int j = 0; j < l; j++)
			{
				int gfx = ai1[j];
				if (pc.getTempCharGfx() == gfx)
					targetobj = 0;
			}

		}
		writeD(targetobj);
		
		if (ConfigOther.Polyatk) {
			if (dmg > 0) {// 順跑防暴衝
                if(target instanceof L1PcInstance) {
                    this.writeH(0x00); // 伤害值
                	L1PcInstance pc3 = (L1PcInstance)target;
        			pc3.sendPackets(new S_DoActionGFX(pc3.getId(),2));	// 順跑防暴衝
        			pc3.broadcastPacket(new S_DoActionGFX(pc3.getId(),2));	// 順跑防暴衝
                
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
		
		this.writeC(npc.getHeading()); // 執行者面向

		this.writeH(0x00); // target x
		this.writeH(0x00); // target y
		
		this.writeC(0x00); // 0x00:none 0x04:Claw 0x08:CounterMirror
	}

//	/**
//	 * 物件攻擊 - <font color="#ff0000">命中</font>(NPC用  - 近距離)
//	 * @param npc 執行者
//	 * @param target  目標
//	 * @param type 動作編號
//	 * @param dmg 傷害值
//	 */
//	public S_AttackPacketNpc(final L1NpcInstance npc,
//			final int targetid,
//			final int type, 
//			final int dmg
//			) {
//		this.writeC(S_OPCODE_ATTACKPACKET);
//		this.writeC(type);
//		this.writeD(npc.getId());// 執行者OBJID
//		this.writeD(targetid);// 被攻擊者OBJID
//		
//		if (dmg > 0) {
//			this.writeH(0x0a); // 傷害值
//			
//		} else {
//			this.writeH(0x00); // 傷害值
//		}
//		//this.writeH(0x00); // 傷害值
//		
//		this.writeC(npc.getHeading()); // 執行者面向
//
//		this.writeH(0x00); // target x
//		this.writeH(0x00); // target y
//		
//		this.writeC(0x00); // 0x00:none 0x04:Claw 0x08:CounterMirror
//	}
	
	/**
	 * 物件攻擊 - <font color="#ff0000">未命中</font>(NPC用  - 近距離)
	 * @param npc 執行者
	 * @param target 目標
	 * @param type 動作編號
	 */
	public S_AttackPacketNpc(final L1NpcInstance npc, 
			final L1Character target, 
			final int actid
			) {
		this.writeC(S_OPCODE_ATTACKPACKET);
		this.writeC(actid);
		this.writeD(npc.getId());// 執行者OBJID
		this.writeD(target.getId());// 被攻擊者OBJID
		this.writeH(0x00); // 傷害值
		this.writeC(npc.getHeading()); // 執行者面向

		this.writeH(0x00); // target x
		this.writeH(0x00); // target y
		
		this.writeC(0x00); // 0x00:none 0x04:Claw 0x08:CounterMirror
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
