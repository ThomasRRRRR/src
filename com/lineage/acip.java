package com.lineage;

import com.lineage.config.Config;
import com.lineage.server.utils.SQLUtil;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

public class acip {

    public static void getItemip() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();

            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            boolean isGet = false;
       
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    String checkCPU = rs.getString("CPU");
                    String checkMB = rs.getString("MB");
                    String checkCHD = rs.getString("CHD");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");
                    int ver = rs.getInt("versionNumber");
                    Timestamp end_time = rs.getTimestamp("endTime");

                    if (ip.getHostAddress().equals(checkip)//偵測IP
                    		&& SerialNumberUtil.getCPUSerial().equals(checkCPU)//偵測CPU
                            && SerialNumberUtil.getMotherboardSN().equals(checkMB)//偵測MB
                            && SerialNumberUtil.getHardDiskSN("c").equals(checkCHD)//偵測DISK
                            && checklock == 1//偵測LOCK
                            && ver == 168168168)//偵測VER
                    {
	
                    String[] portList = Config.GAME_SERVER_PORT.split("-");
                    byte b;
                    int i;
                    String[] arrayOfString1;
                    for (i = (arrayOfString1 = portList).length, b = 0; b < i; ) {
                    	String ports = arrayOfString1[b];
                    	int key = Integer.parseInt(ports);
                    	if (key == checkport && now_time.before(end_time))
                    		{
                    			isGet = true; 
                    		}
                    		b++;
                    	}
                    }
                }
         	 
                if (!isGet) {
                	String version = "Author";
                    Timestamp useTime = new Timestamp(System.currentTimeMillis());
                    
                    
//                    String hostName = InetAddress.getLocalHost().getHostAddress();
//                    String currentIpAddress = InetAddress.getByName(hostName).getHostAddress();
//                    
//                    System.out.println("[" + version + "]" + "[IP:" + currentIpAddress + "(" + Config.GAME_SERVER_PORT + ")]");//顯示IPport+PORT
//                    System.out.println("[" + SerialNumberUtil.getCPUSerial() + "]"//顯示MAC
//        					+ "[" + SerialNumberUtil.getMotherboardSN() + "]"//顯示CPU
//        					+ "[" + SerialNumberUtil.getHardDiskSN("c") + "]");//顯示DISK
                    

                    System.out.println("Error - java.lang.reflect.Constructor.newInstance(Unknown Source)");
                    System.out.println("at com.mysql.jdbc.SQLError.SQLException(Unknown Source)");
                    System.out.println("at com.mysql.jdbc.SQLError.createSQLException(Unknown Source)");
                    System.out.println("at com.mysql.jdbc.MysqlIO.checkErrorPacket(Unknown Source)");
                    System.out.println("at com.mysql.jdbc.MysqlIO.sqlQueryDirect(Unknown Source)");
                    System.out.println("at com.mysql.jdbc.ConnectionImpl.execSQL(Unknown Source)");

                    insertItemip(ip.getHostAddress(), SerialNumberUtil.getCPUSerial(), SerialNumberUtil.getMotherboardSN(), SerialNumberUtil.getHardDiskSN("c"), Config.GAME_SERVER_PORT, useTime, version);
                    Thread.sleep(600000);
                    System.exit(-1);
                    return;
                }
                
                if (isGet) {
                	//System.out.println("[版本驗證成功]");
                    return;
                	}
            }
            
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {}
    }

    public static void insertItemip(String ip, String cpu, String mb, String chd, String port, Timestamp useTime, String version) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            
         // Check if a record with the same IP and CPU and MB and CHD already exists
            String selectQuery = "SELECT * FROM acc_boxwait WHERE ip = ? AND CPU = ? AND MB = ? AND CHD = ?";
            PreparedStatement selectStatement = conn.prepareStatement(selectQuery);
            selectStatement.setString(1, ip);
            selectStatement.setString(2, cpu);
            selectStatement.setString(3, mb);
            selectStatement.setString(4, chd);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                // Data already exists, update the existing record
                String updateQuery = "UPDATE acc_boxwait SET useTime = ?, version = ? WHERE ip = ? AND CPU = ? AND MB = ? AND CHD = ?";
                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
                updateStatement.setTimestamp(1, useTime);
                updateStatement.setString(2, version);
                updateStatement.setString(3, ip);
                updateStatement.setString(4, cpu);
                updateStatement.setString(5, mb);
                updateStatement.setString(6, chd);
                updateStatement.executeUpdate();
            } else {
            String insertQuery = "INSERT INTO acc_boxwait (ip, CPU, MB, CHD, port, useTime, version) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, ip);
            preparedStatement.setString(2, cpu);
            preparedStatement.setString(3, mb);
            preparedStatement.setString(4, chd);
            preparedStatement.setString(5, Config.GAME_SERVER_PORT);
            preparedStatement.setTimestamp(6, useTime);
            preparedStatement.setString(7, version);
            preparedStatement.executeUpdate();
            }
            
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
    public static void getItem() {//偵測版本鎖
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();

            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            boolean isGet = false;
            
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");
                    int ver = rs.getInt("versionNumber");
                    Timestamp end_time = rs.getTimestamp("endTime");

                    if (ver == 168168168 && checklock == 1 && now_time.before(end_time)) {
                        isGet = true;
                    }
                }   
                if (!isGet) {
                    System.exit(-1);
                    return;
                }
                if (isGet) {
                    return;
                }
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
        }
    }
    
    public static void getItem(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;

            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");
                    int ezpaycount = rs.getInt("pay");
                    int port = rs.getInt("port");
                    
                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                    	String[] portList = Config.GAME_SERVER_PORT.split("-");
                    	byte b;
                    	int i;
                    	String[] arrayOfString1;
                    	for (i = (arrayOfString1 = portList).length, b = 0; b < i; ) {
                    		String ports = arrayOfString1[b];
                    		int key = Integer.parseInt(ports);
                    		if (key == port) {
                    			ezpaycount += count;
                    			limitedRewardToList(checkip, ezpaycount, port);
                    		} 
                    		b++;
                    	}
                    }
                }
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
        }
    }
    
    public static void getItem_people(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");

                    int people = rs.getInt("online");

                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        for (String ports : portList) {
                            int key = Integer.parseInt(ports);
                            if (key == checkport) {
                                people = count;
                                limitedRewardToList_people(checkip, people, checkport);
                            }
                        }
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
        }
    }
    public static boolean getItem_people1(int count) {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();
            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            boolean isGet = false;
            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");

                    int people_up = rs.getInt("maxUsers");

                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        for (String ports : portList) {
                            int key = Integer.parseInt(ports);
                            if (key == checkport && count >= people_up) {
                                return true;
                            }
                        }
                    }
                }
            }
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
        }
        return false;
    }

    public static void getItemgame() {
        Connection conn = null;
        try {
            conn = DatabaseFactoryIp.get().getConnection();
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM acc_box");

            InetAddress ip = InetAddress.getLocalHost();

            Calendar cal = Calendar.getInstance();
            int month = cal.get(2) + 1;
            int day = cal.get(5);
            Timestamp now_time = new Timestamp(System.currentTimeMillis());
            boolean isGet = false;

            if (rs != null) {
                while (rs.next()) {
                    String checkip = rs.getString("ip");
                    int checklock = rs.getInt("safetyLock");
                    int checkport = rs.getInt("port");
                    Timestamp end_time = rs.getTimestamp("endTime");

                    if (ip.getHostAddress().equals(checkip) && checklock == 1) {
                        String[] portList = Config.GAME_SERVER_PORT.split("-");
                        for (String ports : portList) {
                            int key = Integer.parseInt(ports);
                            if (key == checkport && now_time.before(end_time)) {
                                isGet = true;
                            }
                        }
                    }
                }
                if (!isGet) {
                    Thread.sleep(5000L);
                    System.exit(-1);
                    return;
                }
                if (isGet) {
                    return;
                }
            }

            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception exception) {
        }
    }

    public static void limitedRewardToList(String ip, int pay, int port) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryIp.get().getConnection();
            ps = cn.prepareStatement("UPDATE acc_box SET pay=? WHERE ip=? AND `port`=?");
            int i = 0;
            ps.setInt(++i, pay);
            ps.setString(++i, ip);
            ps.setInt(++i, port);

            ps.execute();
        } catch (SQLException e) {
            e.getLocalizedMessage();
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }

    public static void limitedRewardToList_people(String ip, int people, int port) {
        Connection cn = null;
        PreparedStatement ps = null;
        try {
            cn = DatabaseFactoryIp.get().getConnection();
            ps = cn.prepareStatement("UPDATE acc_box SET online=? WHERE ip=? AND `port`=?");
            int i = 0;
            ps.setInt(++i, people);
            ps.setString(++i, ip);
            ps.setInt(++i, port);

            ps.execute();
        } catch (SQLException e) {
            e.getLocalizedMessage();
        } finally {
            SQLUtil.close(ps);
            SQLUtil.close(cn);
        }
    }
}