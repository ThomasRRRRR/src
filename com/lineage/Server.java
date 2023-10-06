package com.lineage;

import com.eric.gui.J_Main;
import com.lineage.CompressFile;
import com.lineage.DatabaseFactory;
import com.lineage.DatabaseFactoryLogin;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.config.Config;
import com.lineage.config.ConfigAccessory;
import com.lineage.config.ConfigAi;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigBad;
import com.lineage.config.ConfigBoxs;
import com.lineage.config.ConfigCharSetting;
import com.lineage.config.ConfigClan;
import com.lineage.config.ConfigClastle;
import com.lineage.config.ConfigDescs;
import com.lineage.config.ConfigDrop;
import com.lineage.config.ConfigDropSkill;
import com.lineage.config.ConfigGuaji;
import com.lineage.config.ConfigIpCheck;
import com.lineage.config.ConfigItem;
import com.lineage.config.ConfigKill;
import com.lineage.config.ConfigOrAccessory;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigPnitem;
import com.lineage.config.ConfigQuest;
import com.lineage.config.ConfigRate;
import com.lineage.config.ConfigRecord;
import com.lineage.config.ConfigSQL;
import com.lineage.config.ConfigServer;
import com.lineage.config.ConfigSkill;
import com.lineage.config.ConfigTakeitem;
import com.lineage.config.ConfigWenyang;
import com.lineage.config.ConfigWho;
import com.lineage.config.Config_Pc_Damage;
import com.lineage.config.Configdead;
import com.lineage.config.Configtf;
import com.lineage.config.Configtype;
import com.lineage.list.Announcements;
import com.lineage.server.GameServer;
import com.lineage.server.utils.DBClearAllUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;

import org.apache.log4j.PropertyConfigurator;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to lineage game Line：@724ijgmu");
        CompressFile bean = new CompressFile();
        try {
            String[] loginfofileList;
            File file = new File("./back");
            if (!file.exists()) {
                file.mkdir();
            }
            String nowDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
            bean.zip("./loginfo", "./back/" + nowDate + ".zip");
            File loginfofile = new File("./loginfo");
            String[] arrayOfString1 = loginfofileList = loginfofile.list();
            int i = loginfofileList.length;
            for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
                String fileName = arrayOfString1[b];
                File readfile = new File("./loginfo/" + fileName);
                if (!readfile.exists() || readfile.isDirectory()) continue;
                readfile.delete();
            }
        }
        catch (IOException e2) {
            System.out.println("資料夾不存在: ./back 已經自動建立!");
        }
        boolean error = false;
        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream("./config/logging.properties"));
            LogManager.getLogManager().readConfiguration(is);
            ((InputStream)is).close();
        }
        catch (IOException e3) {
            System.out.println("檔案遺失: ./config/logging.properties");
            error = true;
        }
        try {
            PropertyConfigurator.configure((String)"./config/log4j.properties");
        }
        catch (Exception e) {
            System.out.println("檔案遺失: ./config/log4j.properties");
            System.exit(0);
        }
        
        try {
            Config.load();
            ConfigAlt.load();
            ConfigCharSetting.load();
            ConfigOther.load();
            Config_Pc_Damage.load();
            ConfigRate.load();
            ConfigSQL.load();
            ConfigRecord.load();
            ConfigWho.load();
            ConfigBad.load();
            Configtype.load();
            ConfigAi.load();
            ConfigClastle.load();
            Configtf.load();
            ConfigIpCheck.load();
            ConfigClan.load();
            ConfigGuaji.load();
            Configdead.load();
            ConfigWenyang.load();
            ConfigSkill.load();
        }
        catch (Exception e) {
            System.out.println("CONFIG 資料加載異常!" + e);
            error = true;
        }
        
//        String hostName = InetAddress.getLocalHost().getHostAddress();
//        String currentIpAddress = InetAddress.getByName(hostName).getHostAddress();
//        System.out.println("-------------------------------------------------- ");
//        System.out.println("[Ver-Author]"
//            				+ "[IP:" + currentIpAddress + "(" + Config.GAME_SERVER_PORT + ")]");//顯示版本號
//        System.out.println("[" + SerialNumberUtil.getCPUSerial() + "]"//顯示MAC
//            				+ "[" + SerialNumberUtil.getMotherboardSN() + "]"//顯示CPU
//            				+ "[" + SerialNumberUtil.getHardDiskSN("c") + "]");//顯示DISK
//        System.out.println("-------------------------------------------------- ");
        
        System.out.println("資料設定中...");
        Thread.sleep(1000);

        if (error) {
            System.exit(0);
        }
        DatabaseFactoryLogin.setDatabaseSettings();
        DatabaseFactory.setDatabaseSettings();
        DatabaseFactoryIp.setDatabaseSettings();
        DatabaseFactoryLogin.get();
        DatabaseFactory.get();
        DatabaseFactoryIp.get();
        ConfigBoxs.get();
        ConfigKill.get();
        ConfigDrop.get();
        ConfigDropSkill.get();
        ConfigDescs.get();
        ConfigTakeitem.get();
        ConfigPnitem.get();
        ConfigQuest.load();
        ConfigItem.load();
        ConfigServer.loadDB();
        ConfigAccessory.load();
        ConfigOrAccessory.load();
        if (ConfigServer.DBClearAll) {
            DBClearAllUtil.start();
		}
        if (Config.NEWS) {
            Announcements.get();
        }
        if (Config.GUI) {
            J_Main.getInstance().setVisible(true);
        }
        LanSecurityManager securityManager = new LanSecurityManager();
        System.setSecurityManager(securityManager);
        String osname = System.getProperties().getProperty("os.name");
        if (osname.lastIndexOf("Linux") != -1) {
            Config.ISUBUNTU = true;
        }
        GameServer.getInstance().initialize();
    }
}

