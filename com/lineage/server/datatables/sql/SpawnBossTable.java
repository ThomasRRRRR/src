/*     */ package com.lineage.server.datatables.sql;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.NpcTable;
/*     */ import com.lineage.server.datatables.storage.SpawnBossStorage;
/*     */ import com.lineage.server.model.L1Spawn;
/*     */ import com.lineage.server.templates.L1Npc;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnBossTable
/*     */   implements SpawnBossStorage
/*     */ {
/*  33 */   private static final Log _log = LogFactory.getLog(SpawnBossTable.class);
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final Map<Integer, L1Spawn> _bossSpawnTable = new HashMap<>();
/*     */ 
/*     */   
/*  40 */   private List<Integer> _bossId = new ArrayList<>();
/*  41 */   private List<Integer> _bossreid = new ArrayList<>();
/*     */   private Calendar timestampToCalendar(Timestamp ts) {
/*  43 */     Calendar cal = Calendar.getInstance();
/*  44 */     cal.setTimeInMillis(ts.getTime());
/*  45 */     return cal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load() {
/*  53 */     PerformanceTimer timer = new PerformanceTimer();
/*  54 */     Connection con = null;
/*  55 */     PreparedStatement pstm = null;
/*  56 */     ResultSet rs = null;
/*     */     try {
/*  58 */       con = DatabaseFactory.get().getConnection();
/*  59 */       pstm = con.prepareStatement("SELECT * FROM `spawnlist_boss`");
/*  60 */       rs = pstm.executeQuery();
/*     */       
/*  62 */       while (rs.next()) {
/*  63 */         int id = rs.getInt("id");
/*  64 */         String loc = rs.getString("location");
/*  65 */         int npcTemplateId = rs.getInt("npc_templateid");
/*  66 */         L1Npc temp1 = NpcTable.get().getTemplate(npcTemplateId);
/*     */         
/*  68 */         if (temp1 == null) {
/*  69 */           _log.error("BOSS召喚MOB編號: " + npcTemplateId + " 不存在資料庫中!");
/*     */           
/*     */           continue;
/*     */         } 
/*  73 */         this._bossId.add(new Integer(npcTemplateId));
/*  74 */         temp1.set_boss(true);
/*     */ 
/*     */         
/*  77 */         int tmp_id = temp1.getTransformId();
/*  78 */         while (tmp_id > 0) {
/*     */           
/*  80 */           this._bossId.add(new Integer(tmp_id));
/*  81 */           L1Npc temp2 = NpcTable.get().getTemplate(tmp_id);
/*  82 */           temp2.set_boss(true);
/*  83 */           tmp_id = temp2.getTransformId();
/*     */         } 
/*     */ 
/*     */         
/*  87 */         int count = rs.getInt("count");
/*  88 */         if (count <= 0) {
/*     */           continue;
/*     */         }
/*     */         
/*  92 */         int group_id = rs.getInt("group_id");
/*  93 */         int locx1 = rs.getInt("locx1");
/*  94 */         int locy1 = rs.getInt("locy1");
/*  95 */         int locx2 = rs.getInt("locx2");
/*  96 */         int locy2 = rs.getInt("locy2");
/*  97 */         int heading = rs.getInt("死亡廣播");
/*  98 */         int mapid = rs.getShort("mapid");
int movement_distance = rs.getInt("movement_distance");
/*     */         
/* 100 */         Timestamp time = rs.getTimestamp("next_spawn_time");
/*     */         
/* 102 */         Calendar next_spawn_time = null;
/* 103 */         if (time != null)
/*     */         {
/* 105 */           next_spawn_time = timestampToCalendar(rs.getTimestamp("next_spawn_time"));
/*     */         }
/*     */         
/* 108 */         int spawn_interval = rs.getInt("spawn_interval");
/*     */         
/* 110 */         int exist_time = rs.getInt("存在時間");
/* 111 */         String msg1 = rs.getString("死亡廣播時間");
/* 112 */         String msg2 = rs.getString("死亡不廣播時間");
/*     */         
/* 114 */         L1Spawn spawnDat = new L1Spawn(temp1);
/* 115 */         spawnDat.setId(id);
/* 116 */         this._bossreid.add(new Integer(id));
/* 117 */         spawnDat.setLocation(loc);
/* 118 */         spawnDat.setAmount(count);
/* 119 */         spawnDat.setGroupId(group_id);
/* 120 */         spawnDat.setNpcid(npcTemplateId);
/*     */         
/* 122 */         if (locx2 == 0 && locy2 == 0) {
/* 123 */           spawnDat.setLocX(locx1);
/* 124 */           spawnDat.setLocY(locy1);
/* 125 */           spawnDat.setLocX1(0);
/* 126 */           spawnDat.setLocY1(0);
/* 127 */           spawnDat.setLocX2(0);
/* 128 */           spawnDat.setLocY2(0);
/*     */         } else {
/*     */           
/* 131 */           spawnDat.setLocX(locx1);
/* 132 */           spawnDat.setLocY(locy1);
/* 133 */           spawnDat.setLocX1(locx1);
/* 134 */           spawnDat.setLocY1(locy1);
/* 135 */           spawnDat.setLocX2(locx2);
/* 136 */           spawnDat.setLocY2(locy2);
/*     */         } 
/*     */         
/* 139 */         if (locx2 < locx1 && locx2 != 0) {
/* 140 */           _log.error("spawnlist_boss : locx2 < locx1:" + id);
/*     */           
/*     */           continue;
/*     */         } 
/* 144 */         if (locy2 < locy1 && locy2 != 0) {
/* 145 */           _log.error("spawnlist_boss : locy2 < locy1:" + id);
/*     */           continue;
/*     */         } 
/* 148 */         spawnDat.setHeading(heading);
/* 149 */         spawnDat.setMapId((short)mapid);
/*     */         
/* 151 */         spawnDat.setMinRespawnDelay(10);
///* 152 */         spawnDat.setMovementDistance(50);
/*     */         spawnDat.setMovementDistance(movement_distance);
/* 154 */         spawnDat.setName(temp1.get_name());
/*     */         
/* 156 */         spawnDat.set_nextSpawnTime(next_spawn_time);
/* 157 */         spawnDat.set_spawnInterval(spawn_interval);
/*     */         
/* 159 */         spawnDat.set_existTime(exist_time);
/* 160 */         spawnDat.setSpawnType(0);
/*     */         
/* 162 */         spawnDat.setBroadcast(rs.getBoolean("重生是否廣播"));
/* 163 */         spawnDat.setBroadcastInfo(rs.getString("重生廣播文字"));
/*     */         
/* 165 */         spawnDat.setmsg1(msg1);
/* 166 */         spawnDat.setmsg2(msg2);
/* 167 */         if (count > 1 && spawnDat.getLocX1() == 0) {
/* 168 */           int range = Math.min(count * 6, 30);
/* 169 */           spawnDat.setLocX1(spawnDat.getLocX() - range);
/* 170 */           spawnDat.setLocY1(spawnDat.getLocY() - range);
/* 171 */           spawnDat.setLocX2(spawnDat.getLocX() + range);
/* 172 */           spawnDat.setLocY2(spawnDat.getLocY() + range);
/*     */         } 
/* 174 */         spawnDat.init();
/* 175 */         _bossSpawnTable.put(new Integer(spawnDat.getId()), spawnDat);
/*     */       }
/*     */     
/*     */     }
/* 179 */     catch (SQLException e) {
/* 180 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 183 */       SQLUtil.close(rs);
/* 184 */       SQLUtil.close(pstm);
/* 185 */       SQLUtil.close(con);
/*     */     } 
/* 187 */     _log.info("載入BOSS召喚資料數量: " + _bossSpawnTable.size() + "(" + timer.get() + "ms)");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upDateNextSpawnTime(int id, Calendar spawnTime) {
/* 198 */     Connection con = null;
/* 199 */     PreparedStatement pstm = null;
/*     */     
/*     */     try {
/* 202 */       con = DatabaseFactory.get().getConnection();
/* 203 */       pstm = con.prepareStatement("UPDATE `spawnlist_boss` SET `next_spawn_time`=? WHERE `id`=?");
/*     */       
/* 205 */       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
/* 206 */       String fm = sdf.format(spawnTime.getTime());
/*     */       
/* 208 */       int i = 0;
/* 209 */       pstm.setString(++i, fm);
/* 210 */       pstm.setInt(++i, id);
/* 211 */       pstm.execute();
/*     */     }
/* 213 */     catch (Exception e) {
/* 214 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 217 */       SQLUtil.close(pstm);
/* 218 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<Integer, L1Spawn> get_bossSpawnTable() {
/* 223 */     return _bossSpawnTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L1Spawn getTemplate(int key) {
/* 233 */     return _bossSpawnTable.get(Integer.valueOf(key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Integer> bossIds() {
/* 242 */     return this._bossId;
/*     */   }
/*     */   
/*     */   public List<Integer> bossreid() {
/* 246 */     return this._bossreid;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\datatables\sql\SpawnBossTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */