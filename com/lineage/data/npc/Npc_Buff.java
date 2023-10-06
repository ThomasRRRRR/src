/*     */ package com.lineage.data.npc;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.lineage.data.executor.NpcExecutor;
/*     */ import com.lineage.server.datatables.SkillsTable;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.L1Clan;
/*     */ import com.lineage.server.model.skill.L1SkillUse;
/*     */ import com.lineage.server.serverpackets.S_BlueMessage;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Skills;
/*     */ import com.lineage.server.thread.GeneralThreadPool;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.server.world.WorldClan;
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
/*     */ public class Npc_Buff
/*     */   extends NpcExecutor
/*     */ {
/*     */   public static NpcExecutor get() {
/*  33 */     return new Npc_Buff();
/*     */   }
/*     */ 
/*     */   
/*     */   public int type() {
/*  38 */     return 3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void talk(L1PcInstance pc, L1NpcInstance npc) {
/*     */     try {
/*  44 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(npc.getId(), "y_npcbuff"));
/*     */     }
/*  46 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(L1PcInstance pc, L1NpcInstance npc, String cmd, long amount) {
/*  54 */     if (cmd.equalsIgnoreCase("npcclanbuff")) {
/*  55 */       if (pc.hasSkillEffect(95413)) {
/*     */         return;
/*     */       }
/*  58 */       if (pc.getClan() == null) {
/*  59 */         pc.sendPackets((ServerBasePacket)new S_SystemMessage("必須先擁有血盟。"));
/*     */         return;
/*     */       } 
/*  62 */       if (pc.getInventory().checkItem(ConfigOther.NeedItem, ConfigOther.NeedItemCount)) {
/*  63 */         pc.getInventory().consumeItem(ConfigOther.NeedItem, ConfigOther.NeedItemCount);
/*     */         
/*  65 */         pc.setSkillEffect(95413, 10000);
/*     */ 
/*     */         
/*  68 */         int[] skills = ConfigOther.Give_skill;
/*     */ 
/*     */         
/*  71 */         L1Clan clan = WorldClan.get().getClan(pc.getClanname());
/*     */         
/*  73 */         L1PcInstance[] clanMembers = clan.getOnlineClanMember();
/*  74 */         for (int i = 0; i < skills.length; i++) {
/*  75 */           byte b1; int k; L1PcInstance[] arrayOfL1PcInstance; for (k = (arrayOfL1PcInstance = clanMembers).length, b1 = 0; b1 < k; ) { L1PcInstance clanMember1 = arrayOfL1PcInstance[b1];
/*  76 */             L1Skills skill = SkillsTable.get().getTemplate(skills[i]);
/*  77 */             L1SkillUse skillUse = new L1SkillUse();
/*     */             
/*  79 */             skillUse.handleCommands(clanMember1, skills[i], clanMember1.getId(), clanMember1.getX(), clanMember1.getY(), skill.getBuffDuration(), 4); b1++; }
/*     */         
/*     */         }  byte b;
/*     */         int j;
/*     */         L1PcInstance[] arrayOfL1PcInstance1;
/*  84 */         for (j = (arrayOfL1PcInstance1 = clan.getOnlineClanMember()).length, b = 0; b < j; ) { L1PcInstance clanMembers2 = arrayOfL1PcInstance1[b];
/*  85 */           clanMembers2.sendPackets((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.Msg, new Object[] { pc.getName() }))); b++; }
/*     */       
/*     */       } else {
/*  88 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg));
/*     */       } 
/*     */     } 
///*  91 */     if (cmd.equalsIgnoreCase("npcallbuff")) {
///*  92 */       if (pc.hasSkillEffect(95413)) {
///*     */         return;
///*     */       }
///*     */       
///*  96 */       pc.setSkillEffect(95413, 10000);
///*  97 */       if (pc.getInventory().checkItem(ConfigOther.NeedItem1, ConfigOther.NeedItemCount1)) {
///*  98 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format(ConfigOther.Msg1, new Object[] { pc.getName() })));
///*  99 */         World.get().broadcastPacketToAll((ServerBasePacket)new S_BlueMessage(166, "\\f2" + String.format(ConfigOther.Msg1, new Object[] { pc.getName() })));
///* 100 */         pc.getInventory().consumeItem(ConfigOther.NeedItem1, ConfigOther.NeedItemCount1);
///*     */ 
///*     */ 
///*     */ 
///*     */ 
///*     */         
///* 106 */         AllBuffRunnable allBuffRunnable = new AllBuffRunnable();
///* 107 */         GeneralThreadPool.get().execute(allBuffRunnable);
///*     */         try {
///* 109 */           Thread.sleep(1000L);
///* 110 */         } catch (InterruptedException e) {
///*     */           
///* 112 */           e.printStackTrace();
///*     */         }
///*     */       
///*     */       } else {
///*     */         
///* 117 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg1));
///*     */       } 
///*     */     } 


/*  91 */     if (cmd.equalsIgnoreCase("mybuff")) {
/*  92 */       if (pc.hasSkillEffect(95413)) {
/*     */         return;
/*     */       }
/*     */       
/*  96 */       pc.setSkillEffect(95413, 10000);
/*  97 */       if (pc.getInventory().checkItem(ConfigOther.NeedItem2, ConfigOther.NeedItemCount2)) {
/* 100 */         pc.getInventory().consumeItem(ConfigOther.NeedItem2, ConfigOther.NeedItemCount2);
/*     */ 
				final int[] allBuffSkill = ConfigOther.Give_skill;
				for (int i = 0; i < allBuffSkill.length; ++i) {
				final L1Skills skill = SkillsTable.get().getTemplate(allBuffSkill[i]);
				final L1SkillUse skillUse = new L1SkillUse();
				skillUse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), skill.getBuffDuration(), 4);
/*     */       }
/*     */       } else {
/*     */         
/* 117 */         pc.sendPackets((ServerBasePacket)new S_ServerMessage(ConfigOther.ItemMsg2));
/*     */       } 
/*     */     } 


/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class AllBuffRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private AllBuffRunnable() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 133 */         for (L1PcInstance tgpc : World.get().getAllPlayers()) {
/* 134 */           startPc(tgpc);
/* 135 */           Thread.sleep(1L);
/*     */         }
/*     */       
/*     */       }
/* 139 */       catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */     
/*     */     public void startPc(L1PcInstance target) {
/* 144 */       int[] allBuffSkill = ConfigOther.Give_skill1;
/*     */       
/* 146 */       for (int i = 0; i < allBuffSkill.length; i++) {
/* 147 */         L1Skills skill = SkillsTable.get().getTemplate(allBuffSkill[i]);
/* 148 */         L1SkillUse skillUse = new L1SkillUse();
/*     */         
/* 150 */         skillUse.handleCommands(target, allBuffSkill[i], target.getId(), target.getX(), target.getY(), skill.getBuffDuration(), 4);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\data\npc\Npc_Buff.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */