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

public class PcDexDmg
{
  private static Logger _log = Logger.getLogger(PcDexDmg.class
      .getName());
  private static PcDexDmg _instance;
  private final HashMap<Integer, DexDmg> _itemIdIndex = new HashMap<>();
  
  public static PcDexDmg getInstance() {
    if (_instance == null) {
      _instance = new PcDexDmg();
    }
    return _instance;
  }
  
  private PcDexDmg() {
    loadDexDmg();
  }
  
  private void loadDexDmg() {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM 系統_能力敏捷設置");
      rs = pstm.executeQuery();
      fillItemSummon(rs);
    } catch (SQLException e) {
      _log.log(Level.SEVERE, "error while creating 系統_能力敏捷設置 table", 
          e);
    } finally {
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
  }
  
  private void fillItemSummon(ResultSet rs) throws SQLException {
    while (rs.next()) {
      int DexDmg = rs.getInt("Dex");
      int AddDmg = rs.getInt("AddDmg");
      int AddHit = rs.getInt("AddHit");
      int AddCrichance = rs.getInt("AddCrichance");
      int Cridmg = rs.getInt("Cridmg");
      
      DexDmg DexDmgSkill = new DexDmg(DexDmg, AddDmg, AddHit, AddCrichance, Cridmg);
      this._itemIdIndex.put(Integer.valueOf(DexDmg), DexDmgSkill);
    } 
  }
  
  public DexDmg getTemplate(int DexDmg) {
    return this._itemIdIndex.get(Integer.valueOf(DexDmg));
  }
}