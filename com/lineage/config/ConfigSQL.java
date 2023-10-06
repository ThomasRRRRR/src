package com.lineage.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;



public final class ConfigSQL
{
  public static String DB_DRIVER;
  public static String DB_URL1_LOGIN;
  public static String DB_URL2_LOGIN;
  public static String DB_URL3_LOGIN;
  public static String DB_LOGIN_LOGIN;
  public static String DB_PASSWORD_LOGIN;
  public static String DB_URL1;
  public static String DB_URL2;
  public static String DB_URL3;
  public static String DB_LOGIN;
  public static String DB_PASSWORD;
//private static final String SQL_CONFIG = "./config/sql.properties";
  
  public static void load() throws ConfigErrorException {
    Properties set = new Properties();
//    try {
//      InputStream is = new FileInputStream(new File("./config/sql.properties"));
//      InputStreamReader isr = new InputStreamReader(is, "utf-8");
//      set.load(isr);
//      is.close();
      

    DB_DRIVER = set.getProperty("Driver", "com.mysql.jdbc.Driver");
    DB_URL1_LOGIN = set.getProperty("URL1_LOGIN", "jdbc:mysql://localhost/");
    DB_URL2_LOGIN = set.getProperty("URL2_LOGIN", "380");
    DB_URL3_LOGIN = set.getProperty("URL3_LOGIN", "?useUnicode=true&characterEncoding=UTF8");
    DB_LOGIN_LOGIN = set.getProperty("Login_LOGIN", "root");
    DB_PASSWORD_LOGIN = set.getProperty("Password_LOGIN", "062488");
    
    DB_URL1 = set.getProperty("URL1", "jdbc:mysql://localhost/");
    DB_URL2 = set.getProperty("URL2", "380");
    DB_URL3 = set.getProperty("URL3", "?useUnicode=true&characterEncoding=UTF8");
    DB_LOGIN = set.getProperty("Login", "root");
    DB_PASSWORD = set.getProperty("Password", "062488");
    
    

//      DB_DRIVER = set.getProperty("Driver", "com.mysql.jdbc.Driver");
//      DB_URL1_LOGIN = set.getProperty("URL1_LOGIN", "jdbc:mysql://lineagesf.securitytactics.com/");
//      DB_URL2_LOGIN = set.getProperty("URL2_LOGIN", "380newtest");
//      DB_URL3_LOGIN = set.getProperty("URL3_LOGIN", "?useUnicode=true&characterEncoding=UTF8");
//      DB_LOGIN_LOGIN = set.getProperty("Login_LOGIN", "serverbox");
//      DB_PASSWORD_LOGIN = set.getProperty("Password_LOGIN", "3nB9RLlcixsoYU3R9ieUgw==");
//      
//      DB_URL1 = set.getProperty("URL1", "jdbc:mysql://lineagesf.securitytactics.com/");
//      DB_URL2 = set.getProperty("URL2", "380newtest");
//      DB_URL3 = set.getProperty("URL3", "?useUnicode=true&characterEncoding=UTF8");
//      DB_LOGIN = set.getProperty("Login", "serverbox");
//      DB_PASSWORD = set.getProperty("Password", "3nB9RLlcixsoYU3R9ieUgw==");


    
//    	DB_DRIVER = set.getProperty("Driver", "com.mysql.jdbc.Driver");
//    	DB_URL1_LOGIN = set.getProperty("URL1_LOGIN", "jdbc:mysql://localhost/");
//    	DB_URL2_LOGIN = set.getProperty("URL2_LOGIN", "380");
//    	DB_URL3_LOGIN = set.getProperty("URL3_LOGIN", "?useUnicode=true&characterEncoding=UTF8");
//    	DB_LOGIN_LOGIN = set.getProperty("Login_LOGIN", "root");
//    	DB_PASSWORD_LOGIN = set.getProperty("Password_LOGIN", "168168168");
//    
//    	DB_URL1 = set.getProperty("URL1", "jdbc:mysql://localhost/");
//    	DB_URL2 = set.getProperty("URL2", "380");
//    	DB_URL3 = set.getProperty("URL3", "?useUnicode=true&characterEncoding=UTF8");
//    	DB_LOGIN = set.getProperty("Login", "root");
//    	DB_PASSWORD = set.getProperty("Password", "168168168");

    
    
    
//    }
//    catch (Exception e) {
//    throw new ConfigErrorException("設置檔案遺失: ./config/sql.properties");
//    } finally
{
      set.clear();
    } 
  }
}