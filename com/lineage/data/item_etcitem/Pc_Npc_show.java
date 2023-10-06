/*     */ package com.lineage.data.item_etcitem;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.data.executor.ItemExecutor;
/*     */ import com.lineage.server.datatables.NpcTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1MonsterInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1Object;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.S_WhoCharinfo;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Npc;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import com.lineage.server.world.World;

/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pc_Npc_show
/*     */   extends ItemExecutor
/*     */ {
/*     */   public static ItemExecutor get() {
/*  38 */     return new Pc_Npc_show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void execute(int[] data, L1PcInstance pc, L1ItemInstance useItem) {
/*  50 */     int spellsc_objid = data[0];
/*  52 */     L1Object target = World.get().findObject(spellsc_objid);

/*  57 */     if (target != null) {
/*  58 */       int i, j; String msg0 = "";
/*  59 */       String msg1 = "";
/*  60 */       String msg2 = "";
/*  61 */       String msg3 = "";
/*  62 */       String msg4 = "";
/*  63 */       String msg5 = "";
/*  64 */       String msg6 = "";
/*  65 */       String msg7 = "";
/*  66 */       String msg8 = "";
/*  67 */       String msg9 = "";
/*  68 */       String msg10 = "";
/*  69 */       String msg11 = "";
/*     */       
/*  71 */       String msg12 = "";
/*  72 */       String msg13 = "";
/*  73 */       String msg14 = "";
/*  74 */       String msg15 = "";
/*  75 */       String msg16 = "";
/*  76 */       String msg17 = "";
/*     */       
/*  78 */       String msg18 = "";
/*  79 */       String msg19 = "";
/*     */       
/*  81 */       if (target instanceof L1PcInstance) {
/*  82 */         L1PcInstance target_pc = (L1PcInstance)target;
/*  83 */         int mr = 0;
/*  84 */         switch (target_pc.guardianEncounter()) {
/*     */           case 0:
/*  86 */             mr = 3;
/*     */             break;
/*     */           
/*     */           case 1:
/*  90 */             mr = 6;
/*     */             break;
/*     */           
/*     */           case 2:
/*  94 */             mr = 9;
/*     */             break;
/*     */         } 
/*  97 */         msg0 = target_pc.getName();
/*  98 */         int k = target_pc.getLevel();
/*  99 */         msg2 = target_pc.getCurrentHp() + " / " + target_pc.getMaxHp();
/* 100 */         msg3 = target_pc.getCurrentMp() + " / " + target_pc.getMaxMp();
/* 101 */         int m = target_pc.getAc();
/* 102 */         short s1 = target_pc.getStr();
/* 103 */         short s2 = target_pc.getDex();
/* 104 */         short s3 = target_pc.getInt();
/* 105 */         short s4 = target_pc.getCon();
/* 106 */         short s5 = target_pc.getWis();
/* 107 */         short s6 = target_pc.getCha();
/* 108 */         msg11 = (target_pc.getMr() + mr) + " %";
/* 109 */         i = target_pc.getLawful();
/* 110 */         j = target_pc.getSp();
/* 111 */         S_WhoCharinfo whoChar = new S_WhoCharinfo(pc, target_pc);
/* 112 */         pc.sendPackets((ServerBasePacket)whoChar);
/* 113 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fT等級:[" + k + "]"));
/* 114 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fW血量:[" + msg2 + "] // 魔力:[" + msg3 + "]"));
/* 115 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU防禦:[" + m + "]  //  魔防:[" + msg11 + "]"));
/* 116 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fU正義:[" + i + "]  //  魔攻:[" + j + "]"));
/* 117 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fR力量:[" + s1 + "]  //  敏捷:[" + s2 + "]"));
/* 118 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fR智力:[" + s3 + "]  //  體質:[" + s4 + "]"));
/* 119 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fR精神:[" + s5 + "]  //  魅力:[" + s6 + "]"));
/*     */       
/*     */       }
/* 122 */       else if (target instanceof L1MonsterInstance) {
/* 123 */         L1MonsterInstance target_npc = (L1MonsterInstance)target;
/* 124 */         msg0 = target_npc.getName();
/* 125 */         int k = target_npc.getLevel();
/* 126 */         msg2 = target_npc.getCurrentHp() + " / " + target_npc.getMaxHp();
/* 127 */         msg3 = target_npc.getCurrentMp() + " / " + target_npc.getMaxMp();
/* 128 */         int m = target_npc.getAc();
/* 129 */         msg5 = "0";
/* 130 */         msg6 = target_npc.getMr() + " %";
/* 131 */         msg7 = target_npc.getFire() + " %";
/* 132 */         msg8 = target_npc.getWater() + " %";
/* 133 */         msg9 = target_npc.getWind() + " %";
/* 134 */         msg10 = target_npc.getEarth() + " %";
/* 135 */         msg11 = "0";
/* 136 */         short s1 = target_npc.getStr();
/* 137 */         short s2 = target_npc.getInt();
/* 138 */         short s3 = target_npc.getCon();
/* 139 */         short s4 = target_npc.getDex();
/* 140 */         short s5 = target_npc.getWis();
/* 141 */         long l = target_npc.getExp();
/* 142 */         String[] msg = { msg0, new StringBuilder().append(target_npc.getLevel()).toString(), msg2, msg3, new StringBuilder().append(target_npc.getLevel()).toString(), msg5, msg6, msg7, msg8, msg9, msg10, msg11, new StringBuilder().append((int) target_npc.getStr()).toString(), new StringBuilder().append((int) target_npc.getInt()).toString(), new StringBuilder().append((int) target_npc.getCon()).toString(), new StringBuilder().append((int) target_npc.getDex()).toString(), new StringBuilder().append((int) target_npc.getWis()).toString(), new StringBuilder().append(target_npc.getExp()).toString()};
/* 143 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), "ajplayer1", msg));
/* 144 */         L1Npc npc = NpcTable.get().getTemplate(target_npc.getNpcId());
/* 145 */         if (npc == null) {
/* 146 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\aT不存在該怪物。你可以在怪物附近輸入.4查看id"));
/*     */           return;
/*     */         } 
/* 149 */         Connection con = null;
/* 150 */         PreparedStatement pstm = null;
/* 151 */         ResultSet rs = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 161 */           con = DatabaseFactory.get().getConnection();
/* 162 */           pstm = con.prepareStatement("SELECT itemId,min,max,chance FROM droplist WHERE mobId=?");
/* 163 */           pstm.setInt(1, target_npc.getNpcId());
/* 164 */           rs = pstm.executeQuery();
/* 165 */           rs.last();
/* 166 */           int rows = rs.getRow();
/* 167 */           int[] itemID = new int[rows];
/* 168 */           int[] min = new int[rows];
/* 169 */           int[] max = new int[rows];
/* 170 */           double[] chance = new double[rows];
/* 171 */           String[] name = new String[rows];
/* 172 */           rs.beforeFirst();
/*     */           
/* 174 */           int n = 0;
/* 175 */           while (rs.next()) {
/* 176 */             itemID[n] = rs.getInt("itemId");
/* 177 */             min[n] = rs.getInt("min");
/* 178 */             max[n] = rs.getInt("max");
/* 179 */             chance[n] = rs.getInt("chance") / 10000.0D;
/* 180 */             n++;
/*     */           } 
/* 182 */           rs.close();
/* 183 */           pstm.close();
/* 184 */           if (pc.isGm()) {
/* 185 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fR" + npc.get_name() + "(" + target_npc.getNpcId() + ") 常規掉落查詢:"));
/*     */           } else {
/* 187 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fR(" + npc.get_name() + ") 常規掉落查詢:"));
/*     */           } 
/*     */           
/* 190 */           for (int i1 = 0; i1 < itemID.length; i1++) {
/* 191 */             pstm = con.prepareStatement("SELECT name FROM etcitem WHERE item_id=?");
/* 192 */             pstm.setInt(1, itemID[i1]);
/* 193 */             rs = pstm.executeQuery();
/* 194 */             while (rs.next()) {
/* 195 */               name[i1] = rs.getString("name");
/*     */             }
/* 197 */             rs.close();
/* 198 */             pstm.close();
/* 199 */             pstm = con.prepareStatement("SELECT name FROM weapon WHERE item_id=?");
/* 200 */             pstm.setInt(1, itemID[i1]);
/* 201 */             rs = pstm.executeQuery();
/* 202 */             while (rs.next()) {
/* 203 */               name[i1] = rs.getString("name");
/*     */             }
/* 205 */             rs.close();
/* 206 */             pstm.close();
/* 207 */             pstm = con.prepareStatement("SELECT name FROM armor WHERE item_id=?");
/* 208 */             pstm.setInt(1, itemID[i1]);
/* 209 */             rs = pstm.executeQuery();
/* 210 */             while (rs.next()) {
/* 211 */               name[i1] = rs.getString("name");
/*     */             }
/* 213 */             rs.close();
/* 214 */             pstm.close();
/*     */             
/* 216 */             if (pc.isGm()) {
/* 217 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY物品: " + name[i1] + " " + " 幾率:" + chance[i1] + "%"));
/*     */             } else {
/* 219 */               pc.sendPackets((ServerBasePacket)new S_SystemMessage("\\fY物品: " + name[i1]));
/*     */             } 
/*     */           } 
/* 222 */         } catch (Exception exception) {
/*     */         
/*     */         } finally {
/* 225 */           SQLUtil.close(rs);
/* 226 */           SQLUtil.close(pstm);
/* 227 */           SQLUtil.close(con);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\item_etcitem\Pc_Npc_show.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */