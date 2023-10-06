/*    */ package com.lineage.server.datatables.lock;
/*    */ 
/*    */ import com.lineage.server.datatables.sql.EzpayTable2;
/*    */ import com.lineage.server.datatables.storage.EzpayStorage2;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.locks.Lock;
/*    */ import java.util.concurrent.locks.ReentrantLock;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EzpayReading2
/*    */ {
/* 18 */   private final Lock _lock = new ReentrantLock(true);
/* 19 */   private final EzpayStorage2 _storage = (EzpayStorage2)new EzpayTable2();
/*    */   private static EzpayReading2 _instance;
/*    */   
/*    */   public static EzpayReading2 get() {
/* 23 */     if (_instance == null) {
/* 24 */       _instance = new EzpayReading2();
/*    */     }
/* 26 */     return _instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<Integer, int[]> ezpayInfo(String loginName) {
/* 31 */     this._lock.lock();
/* 32 */     Map<Integer, int[]> tmp = null;
/*    */     try {
/* 34 */       tmp = this._storage.ezpayInfo(loginName);
/*    */     } finally {
/*    */       
/* 37 */       this._lock.unlock();
/*    */     } 
/* 39 */     return tmp;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean update(String loginName, int id, String pcname, String ip) {
/* 45 */     this._lock.lock();
/* 46 */     boolean tmp = false;
/*    */     try {
/* 48 */       tmp = this._storage.update(loginName, id, pcname, ip);
/*    */     } finally {
/*    */       
/* 51 */       this._lock.unlock();
/*    */     } 
/* 53 */     return tmp;
/*    */   }
/*    */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\server\datatables\lock\EzpayReading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */