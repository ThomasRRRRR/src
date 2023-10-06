package com.lineage.william;

import com.lineage.DatabaseFactory;
import com.lineage.server.utils.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PcIntSp
{
  private static Logger _log = Logger.getLogger(PcIntSp.class
      .getName());
  private static PcIntSp _instance;
  private final HashMap<Integer, IntSp> _itemIdIndex = new HashMap<>();
  
  public static PcIntSp getInstance() {
    if (_instance == null) {
      _instance = new PcIntSp();
    }
    return _instance;
  }
  
  private PcIntSp() {
    loadIntSp();
  }
  
  private void loadIntSp() {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM 系統_能力智力設置");
      rs = pstm.executeQuery();
      fillItemSummon(rs);
    } catch (SQLException e) {
      _log.log(Level.SEVERE, "error while creating 系統_能力智力設置 table", 
          e);
    } finally {
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
  }
  
  private void fillItemSummon(ResultSet rs) throws SQLException {
    while (rs.next()) {
      int IntSp = rs.getInt("int");
      int sp = rs.getInt("Sp");
      int mgs = rs.getInt("魔法穿透率");
      int mgs2 = rs.getInt("忽略魔防");
      int AddCrichance = rs.getInt("AddCrichance");
      int Cridmg = rs.getInt("Cridmg");
      int AddMCrichance = rs.getInt("AddMCrichance");
      int MCridmg = rs.getInt("MCridmg");
      
      IntSp IntSpSkill = new IntSp(IntSp, sp, mgs, mgs2, AddCrichance, Cridmg, AddMCrichance, MCridmg);
      this._itemIdIndex.put(Integer.valueOf(IntSp), IntSpSkill);
    } 
  }
  
  public IntSp getTemplate(int IntSp) {
    return this._itemIdIndex.get(Integer.valueOf(IntSp));
  }
}