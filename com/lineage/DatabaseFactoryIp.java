package com.lineage;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseFactoryIp {
	
    private static final Log _log = LogFactory.getLog(DatabaseFactoryIp.class);
    private ComboPooledDataSource _source;
    private static String _driver;
    private static String _url;
    private static String _user;
    private static String _password;

    private static class Holder {
        static DatabaseFactoryIp instance = new DatabaseFactoryIp();
    }
    
    public static DatabaseFactoryIp get() {
        return Holder.instance;
    }
 
    private DatabaseFactoryIp() {
        try {
            this._source = new ComboPooledDataSource();
            this._source.setDriverClass(_driver);
            this._source.setJdbcUrl(_url);
            this._source.setUser(_user);
            this._source.setPassword(_password);

            this._source.getConnection().close();
        } catch (SQLException e) {
            // Handle SQLException
        } catch (Exception e) {
            // Handle other exceptions
        }
    }
    
    public static void setDatabaseSettings() {//驗證讀取
        _driver = "com.mysql.jdbc.Driver";
        _url = "jdbc:mysql://lineagesf.servegame.com:3306/acc?useUnicode=true&characterEncoding=utf8";
        _user = "boxverify";
        _password = "5DAhl4ZEVVJoNmpnSXs3iA==";
    }

    public void shutdown() {
        try {
            this._source.close();
        } catch (Exception e) {
            // Handle exception
        }

        try {
            this._source = null;
        } catch (Exception e) {
            // Handle exception
        }
    }
    
    public Connection getConnection() {
        Connection con = null;

        while (con == null) {
            try {
                con = this._source.getConnection();
            } catch (SQLException e) {
                // Handle SQLException
            }
        }

        return con;
    }
}