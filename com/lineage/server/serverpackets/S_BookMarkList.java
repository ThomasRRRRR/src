/*    */ package com.lineage.server.serverpackets;
/*    */ 
/*    */ import com.lineage.server.templates.L1BookMark;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class S_BookMarkList
/*    */   extends ServerBasePacket
/*    */ {
/* 14 */   private byte[] _byte = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public S_BookMarkList(String name, int map, int id) {
/* 23 */     buildPacket(name, map, id);
/*    */   }
/*    */ 
/*    */   
/*    */   private void buildPacket(String name, int map, int id) {
/* 28 */     writeC(92);
/* 29 */     writeS(name);
/* 30 */     writeH(map);
/* 31 */     writeD(id);
/*    */   }
/*    */   
/*    */   public S_BookMarkList(byte[] data, int maxSize, List<L1BookMark> bookList) {
/* 35 */     writeC(64);
/* 36 */     writeC(42);
/* 37 */     if (data != null) {
/* 38 */       writeH(data.length); byte b; int i; byte[] arrayOfByte;
/* 39 */       for (i = (arrayOfByte = data).length, b = 0; b < i; ) { int value = arrayOfByte[b];
/* 40 */         writeC(value); b++; }
/*    */     
/*    */     } else {
/* 43 */       writeH(128);
/* 44 */       for (int i = 0; i < 128; i++) {
/* 45 */         writeC(0);
/*    */       }
/*    */     } 
/* 48 */     writeH(maxSize);
/* 49 */     writeH(bookList.size());
/* 50 */     for (L1BookMark book : bookList) {
/* 51 */       writeD(book.getId());
/* 52 */       writeS(book.getName());
/* 53 */       writeH(book.getMapId());
/*    */ 
/*    */       
/* 56 */       writeD(book.getId());
/*    */     } 
/*    */   }
/*    */   
/*    */   public S_BookMarkList(int value) {
/* 61 */     writeC(250);
/* 62 */     writeC(141);
/* 63 */     writeC(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getContent() {
/* 68 */     if (this._byte == null) {
/* 69 */       this._byte = getBytes();
/*    */     }
/* 71 */     return this._byte;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 76 */     return getClass().getSimpleName();
/*    */   }
/*    */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\serverpackets\S_BookMarkList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */