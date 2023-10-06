/*     */ package com.lineage.server.serverpackets;
/*     */ 
/*     */ import com.lineage.data.event.Npc_Dead;
/*     */ import com.lineage.server.datatables.MonsterEnhanceTable;
/*     */ import com.lineage.server.model.Instance.L1MonsterEnhanceInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
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
/*     */ public class S_NPCPack
/*     */   extends ServerBasePacket
/*     */ {
/*     */   private static final int STATUS_POISON = 1;
/*     */   private static final int STATUS_PC = 4;
/*  25 */   private byte[] _byte = null;
/*     */   
/*     */   public S_NPCPack(L1NpcInstance npc, L1PcInstance pc) {
/*  28 */     writeC(87);
/*     */     
/*  30 */     writeH(npc.getX());
/*  31 */     writeH(npc.getY());
/*  32 */     writeD(npc.getId());
/*     */     
/*  34 */     if (npc.getTempCharGfx() == 0) {
/*  35 */       writeH(npc.getGfxId());
/*     */     } else {
/*     */       
/*  38 */       writeH(npc.getTempCharGfx());
/*     */     } 
/*     */     
/*  41 */     if (npc.getNpcTemplate().is_doppel() && npc.getGfxId() != 31) {
/*  42 */       writeC(4);
/*     */     } else {
/*     */       
/*  45 */       writeC(npc.getStatus());
/*     */     } 
/*     */     
/*  48 */     writeC(npc.getHeading());
/*  49 */     writeC(npc.getChaLightSize());
/*  50 */     writeC(npc.getMoveSpeed());
/*  51 */     writeD((int)npc.getExp());
/*  52 */     writeH(npc.getTempLawful());
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
/*  68 */     int CurrentDc = 0;
/*  69 */     if (Npc_Dead.START && 
/*  70 */       MonsterEnhanceTable.getInstance().getTemplate(npc.getNpcId()) != null) {
/*  71 */       L1MonsterEnhanceInstance mei = MonsterEnhanceTable.getInstance().getTemplate(npc.getNpcId());
/*     */       
/*  73 */       if (mei.getCurrentDc() > 0) {
/*  74 */         CurrentDc = mei.getCurrentDc();
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  80 */     if (CurrentDc > 0) {
/*  81 */       writeS(String.valueOf(npc.getNameId()) + "強化(" + CurrentDc + ")次");
/*     */     } else {
/*  83 */       writeS(npc.getNameId());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     writeS(npc.getTitle());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     int status = 0;
/*  97 */     if (npc.getPoison() != null && 
/*  98 */       npc.getPoison().getEffectId() == 1) {
/*  99 */       status |= 0x1;
/*     */     }
/*     */     
/* 102 */     if (npc.getNpcTemplate().is_doppel())
/*     */     {
/* 104 */       if (npc.getNpcTemplate().get_npcId() != 81069) {
/* 105 */         status |= 0x4;
/*     */       }
/*     */     }
/* 108 */     if (npc.getNpcTemplate().get_npcId() == 90024) {
/* 109 */       status |= 0x1;
/*     */     }
this.writeC(status); // 狀態

this.writeD(0x00000000); // 0以外にするとC_27が飛ぶ
this.writeS(null);
this.writeS(null); // 主人的名稱

this.writeC(0x00); // 物件分類
this.writeC(0xff); // HP
this.writeC(0x00);
//this.writeC(0x00);// LV怪物名字顏色隨等級變化
this.writeC(npc.getLevel());// LV
this.writeC(0x00);
this.writeC(0xff);
this.writeC(0xff);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S_NPCPack(L1NpcInstance npc) {
/* 132 */     writeC(87);
/* 133 */     writeH(npc.getX());
/* 134 */     writeH(npc.getY());
/* 135 */     writeD(npc.getId());
/*     */     
/* 137 */     if (npc.getTempCharGfx() == 0) {
/* 138 */       writeH(npc.getGfxId());
/*     */     } else {
/*     */       
/* 141 */       writeH(npc.getTempCharGfx());
/*     */     } 
/*     */     
/* 144 */     if (npc.getNpcTemplate().is_doppel() && npc.getGfxId() != 31) {
/* 145 */       writeC(4);
/*     */     } else {
/*     */       
/* 148 */       writeC(npc.getStatus());
/*     */     } 
/*     */     
/* 151 */     writeC(npc.getHeading());
/* 152 */     writeC(npc.getChaLightSize());
/* 153 */     writeC(npc.getMoveSpeed());
/* 154 */     writeD((int)npc.getExp());
/* 155 */     writeH(npc.getTempLawful());
/* 156 */     writeS(npc.getNameId());
/*     */     
/* 158 */     writeS(npc.getTitle());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     int status = 0;
/* 165 */     if (npc.getPoison() != null && 
/* 166 */       npc.getPoison().getEffectId() == 1) {
/* 167 */       status |= 0x1;
/*     */     }
/*     */     
/* 170 */     if (npc.getNpcTemplate().is_doppel())
/*     */     {
/* 172 */       if (npc.getNpcTemplate().get_npcId() != 81069) {
/* 173 */         status |= 0x4;
/*     */       }
/*     */     }
/* 176 */     if (npc.getNpcTemplate().get_npcId() == 90024) {
/* 177 */       status |= 0x1;
/*     */     }
/* 179 */     writeC(status);
/*     */     
/* 181 */     writeD(0);
/* 182 */     writeS(null);
/* 183 */     writeS(null);
/*     */     
/* 185 */     writeC(0);
/*     */     
/* 187 */     writeC(255);
/* 188 */     writeC(0);
/* 189 */     writeC(0);
/*     */     
/* 191 */     writeC(0);
/* 192 */     writeC(255);
/* 193 */     writeC(255);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getContent() {
/* 198 */     if (this._byte == null) {
/* 199 */       this._byte = getBytes();
/*     */     }
/* 201 */     return this._byte;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getType() {
/* 206 */     return getClass().getSimpleName();
/*     */   }
/*     */ }


/* Location:              E:\Files\NewServertest\核心原碼\20230222更新版核心\原始\gs.jar!\com\lineage\server\serverpackets\S_NPCPack.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */