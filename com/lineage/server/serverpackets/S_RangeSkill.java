// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   S_RangeSkill.java

package com.lineage.server.serverpackets;

import com.lineage.config.ConfigOther;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.skill.TargetStatus;
import com.lineage.server.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

// Referenced classes of package com.lineage.server.serverpackets:
//			ServerBasePacket

public class S_RangeSkill extends ServerBasePacket
{

	private static AtomicInteger _sequentialNumber = new AtomicInteger(0x895440);
	private byte _byte[];
	public static final int TYPE_NODIR = 0;
	public static final int TYPE_DIR = 8;

	public S_RangeSkill(L1Character cha, ArrayList targetList, int spellgfx, int actionId, int type)
	{
		_byte = null;
		writeC(42);
		writeC(actionId);
		writeD(cha.getId());
		writeH(cha.getX());
		writeH(cha.getY());
		switch (type)
		{
		case 0: // '\0'
			writeC(cha.getHeading());
			break;

		case 8: // '\b'
			int newHeading = calcheading(cha.getX(), cha.getY(), ((TargetStatus)targetList.get(0)).getTarget().getX(), ((TargetStatus)targetList.get(0)).getTarget().getY());
			cha.setHeading(newHeading);
			writeC(cha.getHeading());
			break;
		}
		writeD(_sequentialNumber.incrementAndGet());
		writeH(spellgfx);
		writeC(type);
		writeH(0);
		writeH(targetList.size());
		for (Iterator iterator = targetList.iterator(); iterator.hasNext();)
		{
			TargetStatus target = (TargetStatus)iterator.next();
			int targetobj = target.getTarget().getId();
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
				case 45024: 
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
			writeD(targetobj);
			
			if (ConfigOther.Polyatk) {
				if (target.isCalc()){	
					if(obj instanceof L1PcInstance)	{	// 順跑防暴衝
						this.writeH(0x00); // 伤害值
						L1PcInstance pc = (L1PcInstance)obj;
						pc.sendPackets(new S_DoActionGFX(pc.getId(),2));	// 順跑防暴衝
						pc.broadcastPacket(new S_DoActionGFX(pc.getId(),2));	// 順跑防暴衝
					} else {
						this.writeH(0x20); // 伤害值
					}
				} else {
					this.writeH(0x00); // 伤害值
				}		// 順跑防暴衝
				
			} else {
				
				if (target.isCalc()){
					writeH(0x20);
				} else {
					writeH(0x00);
				}
			}
		}
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

	public byte[] getContent()
	{
		if (_byte == null)
			_byte = getBytes();
		return _byte;
	}

	public String getType()
	{
		return getClass().getSimpleName();
	}

}
