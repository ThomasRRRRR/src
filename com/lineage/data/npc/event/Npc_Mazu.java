/*     */ package com.lineage.data.npc.event;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.data.executor.NpcExecutor;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_SkillSound;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ 
/*     */ public class Npc_Mazu
/*     */   extends NpcExecutor
/*     */ {
/*  21 */   private static final Log _log = LogFactory.getLog(Npc_Mazu.class);
/*     */ 
/*     */   
/*  24 */   private static final Map<Integer, String> _playList = new HashMap<>();
/*     */   
/*     */   public static NpcExecutor get() {
/*  27 */     return new Npc_Mazu();
/*     */   }
/*     */   
/*     */   public int type() {
/*  31 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void talk(L1PcInstance pc, L1NpcInstance npc) {
/*     */     try {
/*  37 */       if (!pc.is_mazu()) {
/*  38 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_e_g_01"));
/*     */       } else {
/*  40 */         pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_e_g_02"));
/*     */       } 
/*  42 */     } catch (Exception e) {
/*  43 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
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
/*     */   public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
/*     */     try {
/*  61 */       if (cmd.equalsIgnoreCase("0") && !pc.is_mazu()) {
/*  62 */         if (!pc.getInventory().checkItem(ConfigOther.monbossitem, ConfigOther.monbossitemcount)) {
/*  63 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("金幣不足" + ConfigOther.monbossitemcount));
/*     */           return;
/*     */         } 
/*  66 */         if (pc.getQuest().get_step(9955) == 1) {
/*  67 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("您今日媽祖已使用完畢，隔天再來找我吧。"));
/*     */           
/*     */           return;
/*     */         } 
/*  66 */         if (pc.hasSkillEffect(8591))  {
/*  67 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("目前正在使用媽祖狀態中。"));
/*     */           
/*     */           return;
/*     */         } 
/*  71 */         pc.getInventory().consumeItem(ConfigOther.monbossitem, ConfigOther.monbossitemcount);
/*  72 */         pc.set_mazu(true);
/*  73 */         pc.setSkillEffect(8591, ConfigOther.montime * 60 * 1000);
/*  74 */         pc.sendPacketsX8((ServerBasePacket)new S_SkillSound(pc.getId(), 7321, 3600000));
/*  75 */         pc.set_mazu_time((ConfigOther.montime * 60 * 1000));
/*  76 */         pc.getQuest().set_step(9955, 1);
/*     */       
/*     */       }
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
/* 102 */       pc.sendPackets((ServerBasePacket)new S_CloseList(pc.getId()));
/*     */     }
/* 104 */     catch (Exception e) {
/* 105 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\npc\event\Npc_Mazu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */