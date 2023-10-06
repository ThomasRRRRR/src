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

public class PolySpeed
{
  private static Logger _log = Logger.getLogger(PolySpeed.class
      .getName());
  private static PolySpeed _instance;
  private final HashMap<Integer, PolySpeedSkill> _itemIdIndex = new HashMap<>();
  
  public static PolySpeed getInstance() {
    if (_instance == null) {
      _instance = new PolySpeed();
    }
    return _instance;
  }
  
  private PolySpeed() {
	  loadPolySpeed();
  }
  
  private void loadPolySpeed() {
    Connection con = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    
    try {
      con = DatabaseFactory.get().getConnection();
      pstm = con.prepareStatement("SELECT * FROM polyspeed");
      rs = pstm.executeQuery();
      fillItemSummon(rs);
    } catch (SQLException e) {
      _log.log(Level.SEVERE, "error while creating 變身速度控制 table", 
          e);
    } finally {
      SQLUtil.close(rs);
      SQLUtil.close(pstm);
      SQLUtil.close(con);
    } 
  }
  
  private void fillItemSummon(ResultSet rs) throws SQLException {
    while (rs.next()) {
      int polyid = rs.getInt("polyid");
      int attack_speed = rs.getInt("attack_speed");
      int walk_speed = rs.getInt("walk_speed");
      int sleep = rs.getInt("sleep");
      
      PolySpeedSkill polyskill = new PolySpeedSkill(polyid, attack_speed, walk_speed, sleep);
      this._itemIdIndex.put(Integer.valueOf(polyid), polyskill);
    }
  }
  
  public PolySpeedSkill getTemplate(int PolySpeedSkill) {
    return this._itemIdIndex.get(Integer.valueOf(PolySpeedSkill));
  }
}