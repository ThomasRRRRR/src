/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.datatables.sql.CharItemsTable;
/*     */ import com.lineage.server.datatables.sql.DwarfForClanTable;
/*     */ import com.lineage.server.datatables.sql.DwarfTable;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.templates.L1ItemSpecialAttributeChar;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import com.lineage.server.world.WorldItem;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ public class ItemSpecialAttributeCharTable
/*     */ {
/*  35 */   private static final Log _log = LogFactory.getLog(ItemSpecialAttributeCharTable.class);
/*     */   
/*     */   private static ItemSpecialAttributeCharTable _instance;
/*     */   
/*  39 */   private static final HashMap<Integer, L1ItemSpecialAttributeChar> _AtrrCharList = new HashMap<>();
/*     */   
/*     */   public static ItemSpecialAttributeCharTable get() {
/*  42 */     if (_instance == null) {
/*  43 */       _instance = new ItemSpecialAttributeCharTable();
/*     */     }
/*  45 */     return _instance;
/*     */   }
/*     */   
/*     */   public void load() {
/*  49 */     PerformanceTimer timer = new PerformanceTimer();
/*  50 */     Connection cn = null;
/*  51 */     PreparedStatement ps = null;
/*  52 */     ResultSet rs = null;
/*  53 */     int i = 0;
/*     */     try {
/*  55 */       cn = DatabaseFactory.get().getConnection();
/*  56 */       ps = cn.prepareStatement("SELECT * FROM `炫色二_記錄資料`");
/*  57 */       rs = ps.executeQuery();
/*     */       
/*  59 */       while (rs.next()) {
/*  60 */         int itemobjid = rs.getInt("玩家流水號");
/*     */         
/*  62 */         if (!CharItemsTable.get().getUserItem(itemobjid) && !DwarfTable.get().getUserItem(itemobjid) && !DwarfForClanTable.get().getUserItem(itemobjid)) {
/*  63 */           errorItem1(itemobjid);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  70 */         String item_name = rs.getString("炫色武防名稱");
/*  71 */         int attr_id = rs.getInt("炫色代碼");
/*  72 */         String add_pc_name = rs.getString("角色名稱");
/*  73 */         Timestamp add_time = rs.getTimestamp("時間");
/*  74 */         String add_mon_name = rs.getString("使用方式");
/*  75 */         String mapname = rs.getString("地圖方式");
/*  76 */         String Acquisition_mode = rs.getString("洗白方式");
/*     */         
/*  78 */         L1ItemSpecialAttributeChar attr_char = new L1ItemSpecialAttributeChar();
/*  79 */         attr_char.set_itemobjid(itemobjid);
/*  80 */         attr_char.set_item_name(item_name);
/*  81 */         attr_char.set_attr_id(attr_id);
/*  82 */         attr_char.set_add_pc_name(add_pc_name);
/*  83 */         attr_char.set_add_time(add_time);
/*  84 */         attr_char.set_add_mon_name(add_mon_name);
/*  85 */         attr_char.set_mapname(mapname);
/*  86 */         attr_char.set_Acquisition_mode(Acquisition_mode);
/*  87 */         addValue(itemobjid, attr_char);
/*  88 */         i++;
/*  89 */         _AtrrCharList.put(Integer.valueOf(itemobjid), attr_char);
/*     */       } 
/*  91 */     } catch (SQLException e) {
/*  92 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/*  95 */       SQLUtil.close(rs);
/*  96 */       SQLUtil.close(ps);
/*  97 */       SQLUtil.close(cn);
/*     */     } 
/*  99 */     _log.info("讀取->人物物品特殊屬性數量: " + i + "(" + timer.get() + "ms)");
/*     */   }
/*     */   
/*     */   private static void errorItem1(int objid) {
/* 103 */     Connection con = null;
/* 104 */     PreparedStatement pstm = null;
/*     */     try {
/* 106 */       con = DatabaseFactory.get().getConnection();
/* 107 */       pstm = con.prepareStatement(
/* 108 */           "DELETE FROM `炫色二_記錄資料` WHERE `玩家流水號`=?");
/* 109 */       pstm.setInt(1, objid);
/* 110 */       pstm.execute();
/*     */     }
/* 112 */     catch (SQLException e) {
/* 113 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 116 */       SQLUtil.close(pstm);
/* 117 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public L1ItemSpecialAttributeChar getAttrId(int itemobjid) {
/* 126 */     return _AtrrCharList.get(Integer.valueOf(itemobjid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addValue(int item_obj_id, L1ItemSpecialAttributeChar ItemAttr) {
/* 135 */     L1ItemInstance item = WorldItem.get().getItem(Integer.valueOf(item_obj_id));
/* 136 */     if (item != null && 
/* 137 */       item.get_ItemAttrName() == null) {
/* 138 */       item.set_ItemAttrName(ItemAttr);
/*     */     }
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
/*     */   
/*     */   public void storeItem(int itemobjid, L1ItemSpecialAttributeChar ItemAttr) throws Exception {
/* 152 */     if (_AtrrCharList.get(Integer.valueOf(itemobjid)) != null) {
/*     */       return;
/*     */     }
/* 155 */     _AtrrCharList.put(Integer.valueOf(itemobjid), ItemAttr);
/* 156 */     Connection con = null;
/* 157 */     PreparedStatement pstm = null;
/*     */     try {
/* 159 */       con = DatabaseFactory.get().getConnection();
/* 160 */       pstm = con
/* 161 */         .prepareStatement("INSERT INTO `炫色二_記錄資料` SET `玩家流水號`=?,`炫色武防名稱`=?,`炫色代碼`=?,`角色名稱`=?,`時間`=?,`使用方式`=?,`地圖方式`=?,`洗白方式`=?");
/*     */ 
/*     */       
/* 164 */       int i = 0;
/* 165 */       pstm.setInt(++i, itemobjid);
/* 166 */       pstm.setString(++i, ItemAttr.get_item_name());
/* 167 */       pstm.setInt(++i, ItemAttr.get_attr_id());
/* 168 */       pstm.setString(++i, ItemAttr.get_add_pc_name());
/* 169 */       pstm.setTimestamp(++i, ItemAttr.get_add_time());
/* 170 */       pstm.setString(++i, ItemAttr.get_add_mon_name());
/* 171 */       pstm.setString(++i, ItemAttr.get_mapname());
/* 172 */       pstm.setString(++i, ItemAttr.get_Acquisition_mode());
/* 173 */       pstm.execute();
/*     */     }
/* 175 */     catch (SQLException e) {
/* 176 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 179 */       SQLUtil.close(pstm);
/* 180 */       SQLUtil.close(con);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItem(int itemobjid, L1ItemSpecialAttributeChar ItemAttr) {
/* 191 */     Connection co = null;
/* 192 */     PreparedStatement pm = null;
/*     */     try {
/* 194 */       co = DatabaseFactory.get().getConnection();
/* 195 */       pm = co.prepareStatement("UPDATE `炫色二_記錄資料` SET `炫色武防名稱`=?,`炫色代碼`=?,`角色名稱`=?,`時間`=?,`使用方式`=?,`地圖方式`=?,`洗白方式`=? WHERE `玩家流水號`=?");
/*     */ 
/*     */       
/* 198 */       int i = 0;
/* 199 */       pm.setString(++i, ItemAttr.get_item_name());
/* 200 */       pm.setInt(++i, ItemAttr.get_attr_id());
/* 201 */       pm.setString(++i, ItemAttr.get_add_pc_name());
/* 202 */       pm.setTimestamp(++i, ItemAttr.get_add_time());
/* 203 */       pm.setString(++i, ItemAttr.get_add_mon_name());
/* 204 */       pm.setString(++i, ItemAttr.get_mapname());
/* 205 */       pm.setString(++i, ItemAttr.get_Acquisition_mode());
/*     */       
/* 207 */       pm.setInt(++i, itemobjid);
/*     */       
/* 209 */       pm.execute();
/*     */     }
/* 211 */     catch (SQLException e) {
/* 212 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/*     */       
/* 215 */       SQLUtil.close(pm);
/* 216 */       SQLUtil.close(co);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\server\datatables\ItemSpecialAttributeCharTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */