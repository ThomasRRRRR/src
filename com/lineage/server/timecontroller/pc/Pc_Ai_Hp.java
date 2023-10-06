/*     */ package com.lineage.server.timecontroller.pc;
/*     */ 
/*     */ import com.lineage.config.ConfigOther;
/*     */ import com.add.system.CardBookCmd_doll;
/*     */ import com.lineage.server.datatables.lock.CharSkillReading;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.model.Instance.L1PetInstance;
/*     */ import com.lineage.server.model.Instance.L1SummonInstance;
/*     */ import com.lineage.server.model.L1Character;
/*     */ import com.lineage.server.model.L1PolyMorph;
/*     */ import com.lineage.server.model.skill.L1SkillUse;
/*     */ import com.lineage.server.thread.PcOtherThreadPool;
/*     */ import com.lineage.server.world.World;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
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
/*     */ public class Pc_Ai_Hp
/*     */   extends TimerTask
/*     */ {
/*  51 */   private static final Log _log = LogFactory.getLog(Pc_Ai_Hp.class);
/*     */   
/*     */   private ScheduledFuture<?> _timer;
/*     */   
/*     */   public void start() {
/*  56 */     int timeMillis = 1000;
/*  57 */     this._timer = PcOtherThreadPool.get().scheduleAtFixedRate(this, 1000L, 1000L);
/*     */   }
/*     */   
/*  60 */   private static int _time = 0;
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*  65 */       Collection<L1PcInstance> all = World.get().getAllPlayers();
/*     */       
/*  67 */       if (all.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/*  71 */       for (Iterator<L1PcInstance> iter = all.iterator(); iter.hasNext(); ) {
/*  72 */         L1PcInstance tgpc = iter.next();
/*     */ 
/*     */         
/*  75 */         if (tgpc.getdollcount() == 0 && 
/*  76 */           tgpc.getdollcheck() > 0 && tgpc.getdollcheckitem() > 0 && 
/*  77 */           tgpc.getQuest().get_step(tgpc.getdollcheck()) == 1) {
/*  78 */           CardBookCmd_doll.useMagicDoll(tgpc, tgpc.getdollcheckitem(), tgpc.getdollcheckitem());
/*     */         }
/*     */ 
/*     */         
/*  82 */         if (tgpc.getdollcount1() == 0 && 
/*  83 */           tgpc.getdollcheck1() > 0 && tgpc.getdollcheckitem1() > 0 && 
/*  84 */           tgpc.getQuest().get_step(tgpc.getdollcheck1()) == 1) {
/*  85 */           CardBookCmd_doll.useMagicDoll(tgpc, tgpc.getdollcheckitem1(), tgpc.getdollcheckitem1());
/*     */         }
/*     */ 
/*     */ 

/*  90 */         if (!tgpc.isDead() && tgpc.getcardspoly() > 0 && 
/*  91 */           tgpc.getTempCharGfx() != tgpc.getcardspoly()) {
					if (tgpc.getInventory().checkItem(ConfigOther.BSPolyItemId,1)) {
/*  92 */           L1PolyMorph.doPoly((L1Character)tgpc, tgpc.getcardspoly(), 2100, 1);
					tgpc.getInventory().consumeItem(ConfigOther.BSPolyItemId, ConfigOther.BSPolyItemCount);
/*     */         }
					else 
					tgpc.sendPackets((ServerBasePacket)new S_SystemMessage(ConfigOther.BSPolyItem + "不足"));	
					}

/*  95 */         if (check(tgpc) && 
/*  96 */           tgpc.isActived()) {
/*  97 */           if (tgpc.getAu_AutoSkill(8) > 0 && !tgpc.hasSkillEffect(9981)) {
/*  98 */             tgpc.setSkillEffect(9981, 1800000);
/*  99 */             L1SkillUse skillUse = new L1SkillUse();
/* 100 */             skillUse.handleCommands(tgpc, 51, tgpc.getId(), tgpc.getX(), tgpc.getY(), 0, 4);
/*     */           } 
/* 102 */           if (tgpc.getAu_AutoSkill(8) > 0)
/*     */           {
/* 104 */             for (L1NpcInstance petNpc : tgpc.getPetList().values()) {
/* 105 */               double e = tgpc.getCurrentMp();
/* 106 */               double f = tgpc.getMaxMp();
/* 107 */               if (petNpc instanceof L1SummonInstance) {
/* 108 */                 L1SummonInstance summon = (L1SummonInstance)petNpc;
/* 109 */                 if (e / f * 100.0D > tgpc.getsummon_skillidmp_1() && !summon.isDead() && 
/* 110 */                   petNpc != null) {
/* 111 */                   if (tgpc.getCurrentMp() > tgpc.getsummon_skillidmp() && summon.getCurrentHp() < summon.getMaxHp() / 2) {
/* 112 */                     L1SkillUse skillUse = new L1SkillUse();
/* 113 */                     skillUse.handleCommands(tgpc, tgpc.getsummon_skillid(), summon.getId(), summon.getX(), summon.getY(), 0, 2);
/* 114 */                     tgpc.setCurrentMp(tgpc.getCurrentMp() - tgpc.getsummon_skillidmp());
/*     */                   } 
/*     */                   
/* 117 */                   if (CharSkillReading.get().spellCheck(tgpc.getId(), 43) && tgpc.getCurrentMp() > 40 && !summon.hasSkillEffect(43)) {
/* 118 */                     L1SkillUse skillUse = new L1SkillUse();
/* 119 */                     skillUse.handleCommands(tgpc, 43, summon.getId(), summon.getX(), summon.getY(), 0, 2);
/* 120 */                     tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 129 */           for (L1NpcInstance petNpc : tgpc.getPetList().values()) {
/* 130 */             double e = tgpc.getCurrentMp();
/* 131 */             double f = tgpc.getMaxMp();
/* 132 */             if (petNpc instanceof L1PetInstance) {
/* 133 */               L1PetInstance pet = (L1PetInstance)petNpc;
/* 134 */               if (e / f * 100.0D > tgpc.getsummon_skillidmp_1() && !pet.isDead() && 
/* 135 */                 petNpc != null) {
/* 136 */                 if (tgpc.getCurrentMp() > tgpc.getsummon_skillidmp() && pet.getCurrentHp() < pet.getMaxHp() / 2) {
/* 137 */                   L1SkillUse skillUse = new L1SkillUse();
/* 138 */                   skillUse.handleCommands(tgpc, tgpc.getsummon_skillid(), pet.getId(), pet.getX(), pet.getY(), 0, 2);
/* 139 */                   tgpc.setCurrentMp(tgpc.getCurrentMp() - tgpc.getsummon_skillidmp());
/*     */                 } 
/*     */                 
/* 142 */                 if (CharSkillReading.get().spellCheck(tgpc.getId(), 43) && tgpc.getCurrentMp() > 40 && !pet.hasSkillEffect(43)) {
/* 143 */                   L1SkillUse skillUse = new L1SkillUse();
/* 144 */                   skillUse.handleCommands(tgpc, 43, pet.getId(), pet.getX(), pet.getY(), 0, 2);
/* 145 */                   tgpc.setCurrentMp(tgpc.getCurrentMp() - 40);
/*     */                 }
/*     */               
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 157 */     } catch (Exception e) {
/* 158 */       _log.error("自動購水處理時間軸異常重啟", e);
/* 159 */       PcOtherThreadPool.get().cancel(this._timer, false);
/* 160 */       Pc_Ai_Hp lawfulTimer = new Pc_Ai_Hp();
/* 161 */       lawfulTimer.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean check(L1PcInstance tgpc) {
/*     */     try {
/* 172 */       if (tgpc == null) {
/* 173 */         return false;
/*     */       }
/*     */       
/* 176 */       if (tgpc.getOnlineStatus() == 0) {
/* 177 */         return false;
/*     */       }
/*     */       
/* 180 */       if (tgpc.getNetConnection() == null) {
/* 181 */         return false;
/*     */       }
/*     */       
/* 184 */       if (tgpc.isTeleport()) {
/* 185 */         return false;
/*     */       }
/*     */       
/* 188 */       if (tgpc.isDead()) {
/* 189 */         return false;
/*     */       }
/*     */       
/* 192 */       if (tgpc.getCurrentHp() <= 0) {
/* 193 */         return false;
/*     */       }
/* 195 */       if (tgpc.isParalyzed() || tgpc.hasSkillEffect(33) || tgpc.hasSkillEffect(4000) || tgpc.hasSkillEffect(192) || tgpc.hasSkillEffect(50) || tgpc.hasSkillEffect(66) || tgpc.hasSkillEffect(87) || tgpc.hasSkillEffect(4017) || tgpc.hasSkillEffect(192)) {
/* 196 */         return false;
/*     */       }
/* 198 */     } catch (Exception e) {
/* 199 */       return false;
/*     */     } 
/* 201 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Downloads\380\gs.jar!\com\lineage\server\timecontroller\pc\Pc_Ai_Hp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */