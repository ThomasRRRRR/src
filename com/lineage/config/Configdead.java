/*     */ package com.lineage.config;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ public final class Configdead
/*     */ {
/*     */   public static int KillTargetlawful;
/*     */   public static boolean pkpinkcheck;
/*     */   public static boolean deaddropitem;
/*     */   public static int deadleveldrop;
/*     */   public static int deaditemran1;
/*     */   public static int deaditemcount1;
/*     */   public static int deaditemran2;
/*     */   public static int deaditemcount2;
/*     */   public static int deaditemran3;
/*     */   public static int deaditemcount3;
/*     */   public static int deaditemran4;
/*     */   public static int deaditemcount4;
/*     */   public static int deaditemran5;
/*     */   public static int deaditemcount5;
/*     */   public static int deaditemran6;
/*     */   public static int deaditemcount6;
/*     */   public static int deaditemran7;
/*     */   public static int deaditemcount7;
/*     */   public static int deaditemran8;
/*     */   public static int deaditemcount8;
/*     */   public static int deaditemran9;
/*     */   public static int deaditemcount9;
/*     */   public static boolean deaddropskill;
/*     */   public static int deadlevelskill;
/*     */   public static int deadskillran1;
/*     */   public static int deadskillcount1;
/*     */   public static int deadskillran2;
/*     */   public static int deadskillcount2;
/*     */   public static int deadskillran3;
/*     */   public static int deadskillcount3;
/*     */   public static int deadskillran4;
/*     */   public static int deadskillcount4;
/*     */   public static int deadskillran5;
/*     */   public static int deadskillcount5;
/*     */   public static int deadskillran6;
/*     */   public static int deadskillcount6;
/*     */   public static int deadskillran7;
/*     */   public static int deadskillcount7;
/*     */   public static int deadskillran8;
/*     */   public static int deadskillcount8;
/*     */   public static int deadskillran9;
/*     */   public static int deadskillcount9;
/*     */   public static boolean PKHealing;
/*     */   private static final String OTHER_SETTINGS_FILE = "./config/其他控制端/紅人噴裝備技能設置表.properties";
/*     */   
/*     */   public static void load() throws ConfigErrorException {
/*  84 */     Properties set = new Properties();
/*     */     try {
/*  86 */       InputStream is = new FileInputStream(new File("./config/其他控制端/紅人噴裝備技能設置表.properties"));
/*     */       
/*  88 */       InputStreamReader isr = new InputStreamReader(is, "utf-8");
/*  89 */       set.load(isr);
/*  90 */       is.close();
/*     */ 		
PKHealing = Boolean.parseBoolean(set.getProperty("PKHealing", "false"));
/*     */       
/*  93 */       KillTargetlawful = Integer.parseInt(set.getProperty("KillTargetlawful", "10"));
/*  94 */       pkpinkcheck = Boolean.parseBoolean(set.getProperty("pkpinkcheck", "false"));
/*     */       
/*  96 */       deaddropitem = Boolean.parseBoolean(set.getProperty("deaddropitem", "false"));
/*     */       
/*  98 */       deadleveldrop = Integer.parseInt(set.getProperty("deadleveldrop", "0"));
/*     */       
/* 100 */       deaditemran1 = Integer.parseInt(set.getProperty("deaditemran1", "0"));
/* 101 */       deaditemcount1 = Integer.parseInt(set.getProperty("deaditemcount1", "0"));
/*     */       
/* 103 */       deaditemran2 = Integer.parseInt(set.getProperty("deaditemran2", "0"));
/* 104 */       deaditemcount2 = Integer.parseInt(set.getProperty("deaditemcount2", "0"));
/*     */       
/* 106 */       deaditemran3 = Integer.parseInt(set.getProperty("deaditemran3", "0"));
/* 107 */       deaditemcount3 = Integer.parseInt(set.getProperty("deaditemcount3", "0"));
/*     */       
/* 109 */       deaditemran4 = Integer.parseInt(set.getProperty("deaditemran4", "0"));
/* 110 */       deaditemcount4 = Integer.parseInt(set.getProperty("deaditemcount4", "0"));
/*     */       
/* 112 */       deaditemran5 = Integer.parseInt(set.getProperty("deaditemran5", "0"));
/* 113 */       deaditemcount5 = Integer.parseInt(set.getProperty("deaditemcount5", "0"));
/*     */       
/* 115 */       deaditemran6 = Integer.parseInt(set.getProperty("deaditemran6", "0"));
/* 116 */       deaditemcount6 = Integer.parseInt(set.getProperty("deaditemcount6", "0"));
/*     */       
/* 118 */       deaditemran7 = Integer.parseInt(set.getProperty("deaditemran7", "0"));
/* 119 */       deaditemcount7 = Integer.parseInt(set.getProperty("deaditemcount7", "0"));
/*     */       
/* 121 */       deaditemran8 = Integer.parseInt(set.getProperty("deaditemran8", "0"));
/* 122 */       deaditemcount8 = Integer.parseInt(set.getProperty("deaditemcount8", "0"));
/*     */       
/* 124 */       deaditemran9 = Integer.parseInt(set.getProperty("deaditemran9", "0"));
/* 125 */       deaditemcount9 = Integer.parseInt(set.getProperty("deaditemcount9", "0"));
/*     */ 
/*     */ 
/*     */       
/* 129 */       deaddropskill = Boolean.parseBoolean(set.getProperty("deaddropskill", "false"));
/*     */       
/* 131 */       deadlevelskill = Integer.parseInt(set.getProperty("deadlevelskill", "0"));
/*     */       
/* 133 */       deadskillran1 = Integer.parseInt(set.getProperty("deadskillran1", "0"));
/* 134 */       deadskillcount1 = Integer.parseInt(set.getProperty("deadskillcount1", "0"));
/*     */       
/* 136 */       deadskillran2 = Integer.parseInt(set.getProperty("deadskillran2", "0"));
/* 137 */       deadskillcount2 = Integer.parseInt(set.getProperty("deadskillcount2", "0"));
/*     */       
/* 139 */       deadskillran3 = Integer.parseInt(set.getProperty("deadskillran3", "0"));
/* 140 */       deadskillcount3 = Integer.parseInt(set.getProperty("deadskillcount3", "0"));
/*     */       
/* 142 */       deadskillran4 = Integer.parseInt(set.getProperty("deadskillran4", "0"));
/* 143 */       deadskillcount4 = Integer.parseInt(set.getProperty("deadskillcount4", "0"));
/*     */       
/* 145 */       deadskillran5 = Integer.parseInt(set.getProperty("deadskillran5", "0"));
/* 146 */       deadskillcount5 = Integer.parseInt(set.getProperty("deadskillcount5", "0"));
/*     */       
/* 148 */       deadskillran6 = Integer.parseInt(set.getProperty("deadskillran6", "0"));
/* 149 */       deadskillcount6 = Integer.parseInt(set.getProperty("deadskillcount6", "0"));
/*     */       
/* 151 */       deadskillran7 = Integer.parseInt(set.getProperty("deadskillran7", "0"));
/* 152 */       deadskillcount7 = Integer.parseInt(set.getProperty("deadskillcount7", "0"));
/*     */       
/* 154 */       deadskillran8 = Integer.parseInt(set.getProperty("deadskillran8", "0"));
/* 155 */       deadskillcount8 = Integer.parseInt(set.getProperty("deadskillcount8", "0"));
/*     */       
/* 157 */       deadskillran9 = Integer.parseInt(set.getProperty("deadskillran9", "0"));
/* 158 */       deadskillcount9 = Integer.parseInt(set.getProperty("deadskillcount9", "0"));
/*     */     
/*     */     }
/* 161 */     catch (Exception e) {
/* 162 */       throw new ConfigErrorException("設置檔案遺失: ./config/其他控制端/紅人噴裝備技能設置表.properties");
/*     */     } finally {
/*     */       
/* 165 */       set.clear();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\config\Configdead.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */