package com.lineage.data.npc;



import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigWenyang;
import com.lineage.data.executor.NpcExecutor;
import com.lineage.server.Manly.L1WenYang;
import com.lineage.server.Manly.WenYangTable;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_SystemMessage;

/**
  * 紋樣系統
  * 大陸Manly製作
 * @author Administrator
 *
 */
public class Npc_Wenyang extends NpcExecutor {
	
	private static final Log _log = LogFactory.getLog(Npc_Wenyang.class);
	
	Random _Random = new Random();
	
    private Npc_Wenyang() {
        // TODO Auto-generated constructor stub
    }

    public static NpcExecutor get() {
        return new Npc_Wenyang();
    }

    @Override
    public int type() {
        return 3;
    }

    @Override
    public void talk(final L1PcInstance pc, final L1NpcInstance npc) {
    	String[] data  = new String[6];
    	if (pc.getWyType1() == 1 && pc.getWyLevel1() > 0) {
    		L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType1(), pc.getWyLevel1());
    		if (wenYang != null) {
    			data[0] = String.valueOf("【" + wenYang.getNot() + "】紋樣等級為【" + pc.getWyLevel1() + "】");
			}
		} else {
	    	data[0] = "還未獲得紋樣";
		}
    	if (pc.getWyType2() == 2 && pc.getWyLevel2() > 0) {
    		L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType2(), pc.getWyLevel2());
    		if (wenYang != null) {
    			data[1] = String.valueOf("【" + wenYang.getNot() + "】紋樣等級為【" + pc.getWyLevel2() + "】");
			}
		} else {
			data[1] = "還未獲得紋樣";
		}
    	if (pc.getWyType3() == 3 && pc.getWyLevel3() > 0) {
    		L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType3(), pc.getWyLevel3());
    		if (wenYang != null) {
    			data[2] = String.valueOf("【" + wenYang.getNot() + "】紋樣等級為【" + pc.getWyLevel3() + "】");
			}
		} else {
			data[2] = "還未獲得紋樣";
		}
    	if (pc.getWyType4() == 4 && pc.getWyLevel4() > 0) {
    		L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType4(), pc.getWyLevel4());
    		if (wenYang != null) {
    			data[3] = String.valueOf("【" + wenYang.getNot() + "】紋樣等級為【" + pc.getWyLevel4() + "】");
			}
		} else {
			data[3] = "還未獲得紋樣";
		}
    	if (pc.getWyType5() == 5 && pc.getWyLevel5() > 0) {
    		L1WenYang wenYang = WenYangTable.getInstance().getTemplate(pc.getWyType5(), pc.getWyLevel5());
    		if (wenYang != null) {
    			data[4] = String.valueOf("【" + wenYang.getNot() + "】紋樣等級為【" + pc.getWyLevel5() + "】");
			}
		} else {
			data[4] = "還未獲得紋樣";
		}
    	if (pc.getWenyangJiFen() > 0) {
    		data[5] = String.valueOf("目前您的紋樣積分數量：【" + pc.getWenyangJiFen() + "】");
		} else {
			data[5] = "目前您的紋樣積分數量：【0】";
		}
        pc.sendPackets(new S_NPCTalkReturn(npc.getId(), "wenyang",data));
    }

    @Override
    public void action(final L1PcInstance pc, final L1NpcInstance npc,
            final String cmd, final long amount) {
    	try {
	         if (cmd.equals("a1")) {
	        	 if (pc.getWenyangJiFen() < ConfigWenyang.JIFEN1) {
	        		 pc.sendPackets(new S_SystemMessage("您的紋樣積分不足"));
					return;
				 }
	        	 if (pc.getWyLevel1() >= 30) {
	        		 pc.sendPackets(new S_SystemMessage("伺服器最高紋樣等級為30等"));
					return;
				 }
	        	 if (_Random.nextInt(100) < ConfigWenyang.JILV1) {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN1);
					pc.setWyType1(1);
					pc.setWyLevel1(pc.getWyLevel1() + 1);
					pc.sendPackets(new S_SystemMessage("恭喜升級成功,重登後獲得紋樣屬性"));
					pc.save();
				 } else {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN1);
					pc.sendPackets(new S_SystemMessage("很遺憾升級失敗"));
					pc.save();
				 }	        	 
	         }
///////////////////////////////////////////////////////////////////////////////////////////
	         if (cmd.equals("a2")) {
	        	 if (pc.getWenyangJiFen() < ConfigWenyang.JIFEN2) {
	        		 pc.sendPackets(new S_SystemMessage("您的紋樣積分不足"));
					return;
				 }
	        	 if (pc.getWyLevel2() >= 30) {
	        		 pc.sendPackets(new S_SystemMessage("伺服器最高紋樣等級為30等"));
					return;
				 }
	        	 if (_Random.nextInt(100) < ConfigWenyang.JILV2) {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN2);
					pc.setWyType2(2);
					pc.setWyLevel2(pc.getWyLevel2() + 1);
					pc.sendPackets(new S_SystemMessage("恭喜升級成功,重登後獲得紋樣屬性"));
					pc.save();		        	
				 } else {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN2);
					pc.sendPackets(new S_SystemMessage("很遺憾升級失敗"));
					pc.save();
				 }	        	 
	         }
///////////////////////////////////////////////////////////////////////////////////////////
	         if (cmd.equals("a3")) {
	        	 if (pc.getWenyangJiFen() < ConfigWenyang.JIFEN3) {
	        		 pc.sendPackets(new S_SystemMessage("您的紋樣積分不足"));
					return;
				 }
	        	 if (pc.getWyLevel3() >= 30) {
	        		 pc.sendPackets(new S_SystemMessage("伺服器最高紋樣等級為30等"));
					return;
				 }
	        	 if (_Random.nextInt(100) < ConfigWenyang.JILV3) {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN3);
					pc.setWyType3(3);
					pc.setWyLevel3(pc.getWyLevel3() + 1);
					pc.sendPackets(new S_SystemMessage("恭喜升級成功,重登後獲得紋樣屬性"));
					pc.save();
				 } else {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN3);
					pc.sendPackets(new S_SystemMessage("很遺憾升級失敗"));
					pc.save();
				 }	        	 
	         }
///////////////////////////////////////////////////////////////////////////////////////////
	         if (cmd.equals("a4")) {
	        	 if (pc.getWenyangJiFen() < ConfigWenyang.JIFEN4) {
	        		 pc.sendPackets(new S_SystemMessage("您的紋樣積分不足"));
					return;
				 }
	        	 if (pc.getWyLevel4() >= 30) {
	        		 pc.sendPackets(new S_SystemMessage("伺服器最高紋樣等級為30等"));
					return;
				 }
	        	 if (_Random.nextInt(100) < ConfigWenyang.JILV4) {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN4);
					pc.setWyType4(4);
					pc.setWyLevel4(pc.getWyLevel4() + 1);
					pc.sendPackets(new S_SystemMessage("恭喜升級成功,重登後獲得紋樣屬性"));
					pc.save();
				 } else {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN4);
					pc.sendPackets(new S_SystemMessage("很遺憾升級失敗"));
					pc.save();
				 }	        	 
	         }
///////////////////////////////////////////////////////////////////////////////////////////
	         if (cmd.equals("a5")) {
	        	 if (pc.getWenyangJiFen() < ConfigWenyang.JIFEN5) {
	        		 pc.sendPackets(new S_SystemMessage("您的紋樣積分不足"));
					return;
				 }
	        	 if (pc.getWyLevel5() >= 30) {
	        		 pc.sendPackets(new S_SystemMessage("伺服器最高紋樣等級為30等"));
					return;
				 }
	        	 if (_Random.nextInt(100) < ConfigWenyang.JILV5) {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN5);
					pc.setWyType5(5);
					pc.setWyLevel5(pc.getWyLevel5() + 1);
					pc.sendPackets(new S_SystemMessage("恭喜升級成功,重登後獲得紋樣屬性"));
					pc.save();
				 } else {
					pc.setWenyangJiFen(pc.getWenyangJiFen() - ConfigWenyang.JIFEN5);
					pc.sendPackets(new S_SystemMessage("很遺憾升級失敗"));
					pc.save();
				 }	        	 
	         }
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
    }
}
