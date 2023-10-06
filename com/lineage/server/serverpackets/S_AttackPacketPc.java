package com.lineage.server.serverpackets;

import com.lineage.config.ConfigOther;
import com.lineage.server.ActionCodes;
import com.lineage.server.datatables.SprTable;
import com.lineage.server.model.Instance.*;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.templates.L1Item;
import com.lineage.server.utils.Random;
import com.lineage.server.world.World;
import com.lineage.william.PolySpeedSkill;

// Referenced classes of package com.lineage.server.serverpackets:
//			ServerBasePacket, S_Sound

public class S_AttackPacketPc extends ServerBasePacket
{

	private byte[] _byte = null;
	private int _actid = 1;// 預設攻擊動作

//	/**
//	 * 取回特定外型的特殊攻擊動作(近戰用)
//	 * 
//	 * @param pc
//	 * @return
//	 */
//	private int SpecialActid(L1PcInstance pc)
//	{
//		int tempgfxid = pc.getTempCharGfx();
//		L1ItemInstance weapon = pc.getWeapon();
//		if (weapon == null)
//			return _actid;
//		int weapontype = weapon.getItem().getType1();
//		if (tempgfxid == 13715 && weapontype == 4)
//			_actid = 73;
//		if (tempgfxid == 13717 && weapontype == 4)
//			_actid = 73;
//		if (tempgfxid == 13719 && weapontype == 50)
//			_actid = 79;
//		if (tempgfxid == 13721 && weapontype == 50)
//			_actid = 79;
//		if (tempgfxid == 13723 && weapontype == 4)
//			_actid = 73;
//		if (tempgfxid == 13725 && weapontype == 4)
//			_actid = 73;
//		if (tempgfxid == 13727 && weapontype == 40)
//			_actid = 77;
//		if (tempgfxid == 13729 && weapontype == 40)
//			_actid = 77;
//		if (tempgfxid == 13731 && weapontype == 54)
//			_actid = 80;
//		if (tempgfxid == 13731 && weapontype == 58)
//			_actid = 81;
//		if (tempgfxid == 13733 && weapontype == 54)
//			_actid = 80;
//		if (tempgfxid == 13733 && weapontype == 58)
//			_actid = 81;
//		if (tempgfxid == 13735 && weapontype == 24)
//			_actid = 41;
//		if (tempgfxid == 13737 && weapontype == 24)
//			_actid = 41;
//		if (tempgfxid == 13741 && weapontype == 40)
//			_actid = 77;
//		if (tempgfxid == 13743 && weapontype == 11)
//			_actid = 93;
//		if (tempgfxid == 13743 && weapontype == 24)
//			_actid = 76;
//		if (tempgfxid == 13745 && weapontype == 11)
//			_actid = 93;
//		if (tempgfxid == 13745 && weapontype == 24)
//			_actid = 76;
//		if (tempgfxid >= 13216 && tempgfxid <= 13220)
//			_actid = 30;
//		if (tempgfxid == 12280)
//			_actid = 30;
//		if (tempgfxid == 12283)
//			_actid = 30;
//		if (tempgfxid == 12286)
//			_actid = 30;
//		if (tempgfxid == 12295)
//			_actid = 30;
//		return _actid;
//	}
	/**
	 * 物件攻擊 - <font color="#ff0000">命中</font>(PC 用 - 近距離)
	 * 
	 * @param pc
	 *            執行者
	 * @param target
	 *            目標
	 * @param type
	 *            0x00:none 0x02:暴擊 0x04:雙擊 0x08:鏡反射
	 * @param dmg
	 *            傷害力
	 */
	public S_AttackPacketPc(L1PcInstance pc, L1Character target, int type, int dmg) {
		/*
		 * 0000: 5e 01 be ac bf 01 a4 6c 00 00 01 00 04 00 00 00
		 * ^......l........ 0010: 00 00 44 00 01 00 aa 30 ..D....0
		 * 
		 * 0000: 5e 01 be ac bf 01 a4 6c 00 00 00 00 04 00 00 00
		 * ^......l........ 0010: 00 00 39 38 00 00 40 97 ..98..@.
		 * 
		 * 0000: 5e 01 be ac bf 01 3c 20 00 00 01 00 05 00 00 00 ^.....<
		 * ........ 0010: 00 00 f7 00 35 34 91 ba ....54..
		 */
		writeC(S_OPCODE_ATTACKPACKET);
//		if (Random.nextInt(100) < 25) {
//			_actid = SpecialActid(pc);
//		}
		writeC(_actid);// ACTION_AltAttack
		writeD(pc.getId());
		int targetobj = target.getId();
		L1Object obj = World.get().findObject(targetobj);
		if (obj instanceof L1NpcInstance) {
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
			case 16778: 
				targetobj = 0;
				pc.sendPackets(new S_Sound(1151));
				break;
			}
		}
		if (obj instanceof L1PcInstance) {
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
		
		writeC(pc.getHeading());
		this.writeH(0x00); // target x
		this.writeH(0x00); // target y
		this.writeC(type); // 0x00:none 0x02:暴擊 0x04:雙擊 0x08:鏡反射
	}
	/**
	 * 物件攻擊 - <font color="#ff0000">未命中</font>(PC 用 - 近距離)
	 * 
	 * @param pc
	 * @param target
	 */
	public S_AttackPacketPc(L1PcInstance pc, L1Character target)
	{
		writeC(S_OPCODE_ATTACKPACKET);
//		if (Random.nextInt(100) < 25) {
//			_actid = SpecialActid(pc);
//		}
		writeC(_actid);// ACTION_AltAttack
		writeD(pc.getId());
		writeD(target.getId());
		writeH(0x00); // damage
		writeC(pc.getHeading());
		writeH(0x00); // target x
		writeH(0x00); // target y
		writeC(0x00); // 0x00:none 0x02:暴擊 0x04:雙擊 0x08:鏡反射
	}
	/**
	 * 物件攻擊 - <font color="#ff0000">空擊</font>(PC 用 - 近距離)
	 * 
	 * @param pc
	 */
	public S_AttackPacketPc(L1PcInstance pc)
	{
		writeC(S_OPCODE_ATTACKPACKET);
//		if (Random.nextInt(100) < 25) {
//			_actid = SpecialActid(pc);
//		}
		writeC(_actid);// ACTION_AltAttack
		writeD(pc.getId());
		writeD(0x00);
		writeH(0x00); // damage
		writeC(pc.getHeading());
		writeH(0x00); // target x
		writeH(0x00); // target y
		writeC(0x00); // 0x00:none 0x02:暴擊 0x04:雙擊 0x08:鏡反射
	}

	public byte[] getContent()
	{
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType()
	{
		return getClass().getSimpleName();
	}
}
