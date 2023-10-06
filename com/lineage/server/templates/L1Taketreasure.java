/*     */ package com.lineage.server.templates;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class L1Taketreasure
/*     */ {
/*     */   private final int _item_id;
/*     */   private final int _level;
/*     */   private final int _steal_chance;
/*     */   private final int _min_steal_count;
/*     */   private final int _max_steal_count;
/*     */   private final int _drop_on_floor;
/*     */   private final int _itemid;
/*     */   private final int _itemcount;
/*     */   private final int _anti_steal_item_id;
/*     */   private String _dropmsg;
/*     */   private String _dropmsg1;
/*     */   private String _dropmsg2;
/*     */   private final int _deaditemid;
/*     */   private final int _deaditemcount;
/*     */   private final int _isEquipped;
/*     */   private final int _ditemid;
/*     */   private final int _dcount;
/*     */   
/*     */   public L1Taketreasure(int item_id, int level, int steal_chance, int min_steal_count, int max_steal_count, int drop_on_floor, int anti_steal_item_id, String dropmsg, String dropmsg1, String dropmsg2, int itemid, int itemcount, int deaditemid, int deaditemcount, int isEquipped, int ditemid, int dcount) {
/*  54 */     this._item_id = item_id;
/*  55 */     this._level = level;
/*  56 */     this._steal_chance = steal_chance;
/*  57 */     this._min_steal_count = min_steal_count;
/*  58 */     this._max_steal_count = max_steal_count;
/*     */     
/*  60 */     this._drop_on_floor = drop_on_floor;
/*  61 */     this._anti_steal_item_id = anti_steal_item_id;
/*  62 */     this._dropmsg = dropmsg;
/*  63 */     this._dropmsg1 = dropmsg1;
/*  64 */     this._dropmsg2 = dropmsg2;
/*  65 */     this._itemid = itemid;
/*  66 */     this._itemcount = itemcount;
/*  67 */     this._deaditemid = deaditemid;
/*  68 */     this._deaditemcount = deaditemcount;
this._isEquipped = isEquipped;
this._ditemid = ditemid;
this._dcount = dcount;
/*     */   }
/*     */   
/*     */   public String getdropmsg() {
/*  72 */     return this._dropmsg;
/*     */   }
/*     */   
/*     */   public void setdropmsg(String _dropmsg) {
/*  76 */     this._dropmsg = _dropmsg;
/*     */   }
/*     */   
/*     */   public String getdropmsg1() {
/*  80 */     return this._dropmsg1;
/*     */   }
/*     */   
/*     */   public void setdropmsg1(String _dropmsg1) {
/*  84 */     this._dropmsg1 = _dropmsg1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getdropmsg2() {
/*  89 */     return this._dropmsg2;
/*     */   }
/*     */   
/*     */   public void setdropmsg2(String _dropmsg2) {
/*  93 */     this._dropmsg2 = _dropmsg2;
/*     */   }
/*     */   public final int getItemId() {
/*  96 */     return this._item_id;
/*     */   }
/*     */   
/*     */   public final int getLevel() {
/* 100 */     return this._level;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getStealChance() {
/* 106 */     return this._steal_chance;
/*     */   }
/*     */   
/*     */   public final int getMinStealCount() {
/* 110 */     return this._min_steal_count;
/*     */   }
/*     */   
/*     */   public final int getMaxStealCount() {
/* 114 */     return this._max_steal_count;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final int isDropOnFloor() {
/* 120 */     return this._drop_on_floor;
/*     */   }
/*     */   public final int getitemid() {
/* 123 */     return this._itemid;
/*     */   }
/*     */   public final int getitemcount() {
/* 126 */     return this._itemcount;
/*     */   }
/*     */   public final int getAntiStealItemId() {
/* 129 */     return this._anti_steal_item_id;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getdeaditemid() {
/* 134 */     return this._deaditemid;
/*     */   }
/*     */   public final int getdeaditemcount() {
/* 137 */     return this._deaditemcount;
/*     */   }
/*     */   public final int getisEquipped() {
/* 137 */     return this._isEquipped;
/*     */   }
/*     */   public final int getditemid() {
/* 137 */     return this._ditemid;
/*     */   }
/*     */   public final int getdcount() {
/* 137 */     return this._dcount;
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\templates\L1Taketreasure.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */