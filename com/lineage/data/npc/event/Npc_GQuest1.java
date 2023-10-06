/*     */ package com.lineage.data.npc.event;
/*     */ 
/*     */ import com.lineage.data.executor.NpcExecutor;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public class Npc_GQuest1 extends NpcExecutor {
/*  12 */   private static final Log _log = LogFactory.getLog(Npc_GQuest1.class);
/*     */   
/*     */   public static NpcExecutor get() {
/*  15 */     return new Npc_GQuest1();
/*     */   }
/*     */   
/*     */   public int type() {
/*  19 */     return 3;
/*     */   }
/*     */   
/*     */   public void talk(L1PcInstance paramL1PcInstance, L1NpcInstance paramL1NpcInstance) {
/*  23 */     String str1 = "[未解]";
/*  24 */     String str2 = "[未解]";
/*  25 */     String str3 = "[未解]";
/*  26 */     String str4 = "[未解]";
/*  27 */     String str5 = "[未解]";
/*  28 */     String str6 = "[未解]";
/*  29 */     String str7 = "[未解]";
/*  30 */     String str8 = "[未解]";
/*  31 */     String str9 = "[未解]";
/*  32 */     String str10 = "[未解]";
/*  33 */     String str11 = "[未解]";
/*  34 */     String str12 = "[未解]";
/*  35 */     String str13 = "[未解]";
/*  36 */     String str14 = "[未解]";
/*  37 */     String str15 = "[未解]";
/*  38 */     String str16 = "[未解]";
/*  39 */     String str17 = "[未解]";
/*  40 */     String str18 = "[未解]";
/*  41 */     String str19 = "[未解]";
/*  42 */     String str20 = "[未解]";
/*  43 */     String str21 = "[未解]";
/*  44 */     String str22 = "[未解]";
/*  45 */     String str23 = "[未解]";
/*  46 */     String str24 = "[未解]";
/*  47 */     String str25 = "[未解]";
/*  48 */     String str26 = "[未解]";
/*  49 */     String str27 = "[未解]";
/*  50 */     String str28 = "[未解]";
/*  51 */     String str29 = "[未解]";
/*  52 */     String str30 = "[未解]";
/*  53 */     String str31 = "[未解]";
/*  54 */     String str32 = "[未解]";
/*  55 */     String str33 = "[未解]";
/*  56 */     String str34 = "[未解]";
/*  57 */     String str35 = "[未解]";
/*  58 */     String str36 = "[未解]";
/*  59 */     String str37 = "[未解]";
/*  60 */     String str38 = "[未解]";
/*  61 */     String str39 = "[未解]";
/*  62 */     String str40 = "[未解]";
/*  63 */     String str41 = "[未解]";
/*  64 */     String str42 = "[未解]";
/*  65 */     String str43 = "[未解]";
/*  66 */     String str44 = "[未解]";
/*  67 */     String str45 = "[未解]";
/*  68 */     String str46 = "[未解]";
/*  69 */     String str47 = "[未解]";
/*  70 */     String str48 = "[未解]";
/*  71 */     String str49 = "[未解]";
/*  72 */     String str50 = "[未解]";
/*  73 */     String str51 = "[未解]";
/*  74 */     String str52 = "[未解]";
/*  75 */     String str53 = "[未解]";
/*  76 */     String str54 = "[未解]";
/*  77 */     String str55 = "[未解]";
/*  78 */     String str56 = "[未解]";
/*  79 */     String str57 = "[未解]";
/*  80 */     String str58 = "[未解]";
/*  81 */     String str59 = "[未解]";
/*  82 */     String str60 = "[未解]";
/*  83 */     String str61 = "[未解]";
/*  84 */     String str62 = "[未解]";
/*  85 */     String str63 = "[未解]";
/*  86 */     String str64 = "[未解]";
/*  87 */     String str65 = "[未解]";
/*  88 */     String str66 = "[未解]";
/*  89 */     String str67 = "[未解]";
/*  90 */     String str68 = "[未解]";
/*  91 */     String str69 = "[未解]";
/*  92 */     String str70 = "[未解]";
/*  93 */     String str71 = "[未解]";
/*  94 */     String str72 = "[未解]";
/*  95 */     String str73 = "[未解]";
/*  96 */     String str74 = "[未解]";
/*  97 */     String str75 = "[未解]";
/*  98 */     String str76 = "[未解]";
/*  99 */     String str77 = "[未解]";
/* 100 */     String str78 = "[未解]";
/* 101 */     String str79 = "[未解]";
/* 102 */     String str80 = "[未解]";
/* 103 */     String str81 = "[未解]";
/* 104 */     String str82 = "[未解]";
/* 105 */     String str83 = "[未解]";
/* 106 */     String str84 = "[未解]";
/* 107 */     String str85 = "[未解]";
/* 108 */     String str86 = "[未解]";
/* 109 */     String str87 = "[未解]";
/* 110 */     String str88 = "[未解]";
/* 111 */     String str89 = "[未解]";
/* 112 */     String str90 = "[未解]";
/* 113 */     String str91 = "[未解]";
/* 114 */     String str92 = "[未解]";
/* 115 */     String str93 = "[未解]";
/* 116 */     String str94 = "[未解]";
/* 117 */     String str95 = "[未解]";
/* 118 */     String str96 = "[未解]";
/* 119 */     String str97 = "[未解]";
/* 120 */     String str98 = "[未解]";
/* 121 */     String str99 = "[未解]";
/* 122 */     String str100 = "[未解]";
/* 123 */     String str101 = "[未解]";
/* 124 */     String str102 = "[未解]";
/* 125 */     String str103 = "[未解]";
/* 126 */     String str104 = "[未解]";
/* 127 */     String str105 = "[未解]";
/* 128 */     String str106 = "[未解]";
/* 129 */     String str107 = "[未解]";
/* 130 */     String str108 = "[未解]";
/* 131 */     String str109 = "[未解]";
/* 132 */     String str110 = "[未解]";
/* 133 */     String str111 = "[未解]";
/* 134 */     String str112 = "[未解]";
/* 135 */     String str113 = "[未解]";
/* 136 */     String str114 = "[未解]";
/* 137 */     String str115 = "[未解]";
/* 138 */     String str116 = "[未解]";
/* 139 */     String str117 = "[未解]";
/* 140 */     String str118 = "[未解]";
/* 141 */     String str119 = "[未解]";
/* 142 */     String str120 = "[未解]";
/* 143 */     String str121 = "[未解]";
/* 144 */     String str122 = "[未解]";
/* 145 */     String str123 = "[未解]";
/* 146 */     String str124 = "[未解]";
/* 147 */     String str125 = "[未解]";
/* 148 */     String str126 = "[未解]";
/* 149 */     String str127 = "[未解]";
/* 150 */     String str128 = "[未解]";
/* 151 */     String str129 = "[未解]";
/* 152 */     String str130 = "[未解]";
/* 153 */     String str131 = "[未解]";
/* 154 */     String str132 = "[未解]";
/* 155 */     String str133 = "[未解]";
/* 156 */     String str134 = "[未解]";
/* 157 */     String str135 = "[未解]";
/* 158 */     String str136 = "[未解]";
/* 159 */     String str137 = "[未解]";
/* 160 */     String str138 = "[未解]";
/* 161 */     String str139 = "[未解]";
/* 162 */     String str140 = "[未解]";
/* 163 */     String str141 = "[未解]";
/* 164 */     String str142 = "[未解]";
/* 165 */     String str143 = "[未解]";
/* 166 */     String str144 = "[未解]";
/* 167 */     String str145 = "[未解]";
/* 168 */     String str146 = "[未解]";
/* 169 */     String str147 = "[未解]";
/* 170 */     String str148 = "[未解]";
/* 171 */     String str149 = "[未解]";
/* 172 */     String str150 = "[未解]";
/* 173 */     String str151 = "[未解]";
/* 174 */     String str152 = "[未解]";
/* 175 */     String str153 = "[未解]";
/* 176 */     String str154 = "[未解]";
/* 177 */     String str155 = "[未解]";
/* 178 */     String str156 = "[未解]";
/* 179 */     String str157 = "[未解]";
/* 180 */     String str158 = "[未解]";
/* 181 */     String str159 = "[未解]";
/* 182 */     String str160 = "[未解]";
/* 183 */     String str161 = "[未解]";
/* 184 */     String str162 = "[未解]";
/* 185 */     String str163 = "[未解]";
/* 186 */     String str164 = "[未解]";
/* 187 */     String str165 = "[未解]";
/* 188 */     String str166 = "[未解]";
/* 189 */     String str167 = "[未解]";
/* 190 */     String str168 = "[未解]";
/* 191 */     String str169 = "[未解]";
/* 192 */     String str170 = "[未解]";
/* 193 */     String str171 = "[未解]";
/* 194 */     String str172 = "[未解]";
/* 195 */     String str173 = "[未解]";
/* 196 */     String str174 = "[未解]";
/* 197 */     String str175 = "[未解]";
/* 198 */     String str176 = "[未解]";
/* 199 */     String str177 = "[未解]";
/* 200 */     String str178 = "[未解]";
/* 201 */     String str179 = "[未解]";
/* 202 */     String str180 = "[未解]";
/* 203 */     String str181 = "[未解]";
/* 204 */     String str182 = "[未解]";
/* 205 */     String str183 = "[未解]";
/* 206 */     String str184 = "[未解]";
/* 207 */     String str185 = "[未解]";
/* 208 */     String str186 = "[未解]";
/* 209 */     String str187 = "[未解]";
/* 210 */     String str188 = "[未解]";
/* 211 */     String str189 = "[未解]";
/* 212 */     String str190 = "[未解]";
/* 213 */     String str191 = "[未解]";
/* 214 */     String str192 = "[未解]";
/* 215 */     String str193 = "[未解]";
/* 216 */     String str194 = "[未解]";
/* 217 */     String str195 = "[未解]";
/* 218 */     String str196 = "[未解]";
/* 219 */     String str197 = "[未解]";
/* 220 */     String str198 = "[未解]";
/* 221 */     String str199 = "[未解]";
/* 222 */     String str200 = "[未解]";
/* 123 */     String str201 = "[未解]";
/* 124 */     String str202 = "[未解]";
/* 125 */     String str203 = "[未解]";
/* 126 */     String str204 = "[未解]";
/* 127 */     String str205 = "[未解]";
/* 128 */     String str206 = "[未解]";
/* 129 */     String str207 = "[未解]";
/* 130 */     String str208 = "[未解]";
/* 131 */     String str209 = "[未解]";
/* 132 */     String str210 = "[未解]";
/* 133 */     String str211 = "[未解]";
/* 134 */     String str212 = "[未解]";
/* 135 */     String str213 = "[未解]";
/* 136 */     String str214 = "[未解]";
/* 137 */     String str215 = "[未解]";
/* 138 */     String str216 = "[未解]";
/* 139 */     String str217 = "[未解]";
/* 140 */     String str218 = "[未解]";
/* 141 */     String str219 = "[未解]";
/* 142 */     String str220 = "[未解]";
/* 143 */     String str221 = "[未解]";
/* 144 */     String str222 = "[未解]";
/* 145 */     String str223 = "[未解]";
/* 146 */     String str224 = "[未解]";
/* 147 */     String str225 = "[未解]";
/* 148 */     String str226 = "[未解]";
/* 149 */     String str227 = "[未解]";
/* 150 */     String str228 = "[未解]";
/* 151 */     String str229 = "[未解]";
/* 152 */     String str230 = "[未解]";
/* 153 */     String str231 = "[未解]";
/* 154 */     String str232 = "[未解]";
/* 155 */     String str233 = "[未解]";
/* 156 */     String str234 = "[未解]";
/* 157 */     String str235 = "[未解]";
/* 158 */     String str236 = "[未解]";
/* 159 */     String str237 = "[未解]";
/* 160 */     String str238 = "[未解]";
/* 161 */     String str239 = "[未解]";
/* 162 */     String str240 = "[未解]";
/* 163 */     String str241 = "[未解]";
/* 164 */     String str242 = "[未解]";
/* 165 */     String str243 = "[未解]";
/* 166 */     String str244 = "[未解]";
/* 167 */     String str245 = "[未解]";
/* 168 */     String str246 = "[未解]";
/* 169 */     String str247 = "[未解]";
/* 170 */     String str248 = "[未解]";
/* 171 */     String str249 = "[未解]";
/* 172 */     String str250 = "[未解]";
/* 173 */     String str251 = "[未解]";
/* 174 */     String str252 = "[未解]";
/* 175 */     String str253 = "[未解]";
/* 176 */     String str254 = "[未解]";
/* 177 */     String str255 = "[未解]";
/* 178 */     String str256 = "[未解]";
/* 179 */     String str257 = "[未解]";
/* 180 */     String str258 = "[未解]";
/* 181 */     String str259 = "[未解]";
/* 182 */     String str260 = "[未解]";
/* 183 */     String str261 = "[未解]";
/* 184 */     String str262 = "[未解]";
/* 185 */     String str263 = "[未解]";
/* 186 */     String str264 = "[未解]";
/* 187 */     String str265 = "[未解]";
/* 188 */     String str266 = "[未解]";
/* 189 */     String str267 = "[未解]";
/* 190 */     String str268 = "[未解]";
/* 191 */     String str269 = "[未解]";
/* 192 */     String str270 = "[未解]";
/* 193 */     String str271 = "[未解]";
/* 194 */     String str272 = "[未解]";
/* 195 */     String str273 = "[未解]";
/* 196 */     String str274 = "[未解]";
/* 197 */     String str275 = "[未解]";
/* 198 */     String str276 = "[未解]";
/* 199 */     String str277 = "[未解]";
/* 200 */     String str278 = "[未解]";
/* 201 */     String str279 = "[未解]";
/* 202 */     String str280 = "[未解]";
/* 203 */     String str281 = "[未解]";
/* 204 */     String str282 = "[未解]";
/* 205 */     String str283 = "[未解]";
/* 206 */     String str284 = "[未解]";
/* 207 */     String str285 = "[未解]";
/* 208 */     String str286 = "[未解]";
/* 209 */     String str287 = "[未解]";
/* 210 */     String str288 = "[未解]";
/* 211 */     String str289 = "[未解]";
/* 212 */     String str290 = "[未解]";
/* 213 */     String str291 = "[未解]";
/* 214 */     String str292 = "[未解]";
/* 215 */     String str293 = "[未解]";
/* 216 */     String str294 = "[未解]";
/* 217 */     String str295 = "[未解]";
/* 218 */     String str296 = "[未解]";
/* 219 */     String str297 = "[未解]";
/* 220 */     String str298 = "[未解]";
/* 221 */     String str299 = "[未解]";
/* 222 */     String str300 = "[未解]";
/* 123 */     String str301 = "[未解]";
/* 124 */     String str302 = "[未解]";
/* 125 */     String str303 = "[未解]";
/* 126 */     String str304 = "[未解]";
/* 127 */     String str305 = "[未解]";
/* 128 */     String str306 = "[未解]";
/* 129 */     String str307 = "[未解]";
/* 130 */     String str308 = "[未解]";
/* 131 */     String str309 = "[未解]";
/* 132 */     String str310 = "[未解]";
/* 133 */     String str311 = "[未解]";
/* 134 */     String str312 = "[未解]";
/* 135 */     String str313 = "[未解]";
/* 136 */     String str314 = "[未解]";
/* 137 */     String str315 = "[未解]";
/* 138 */     String str316 = "[未解]";
/* 139 */     String str317 = "[未解]";
/* 140 */     String str318 = "[未解]";
/* 141 */     String str319 = "[未解]";
/* 142 */     String str320 = "[未解]";
/* 143 */     String str321 = "[未解]";
/* 144 */     String str322 = "[未解]";
/* 145 */     String str323 = "[未解]";
/* 146 */     String str324 = "[未解]";
/* 147 */     String str325 = "[未解]";
/* 148 */     String str326 = "[未解]";
/* 149 */     String str327 = "[未解]";
/* 150 */     String str328 = "[未解]";
/* 151 */     String str329 = "[未解]";
/* 152 */     String str330 = "[未解]";
/* 153 */     String str331 = "[未解]";
/* 154 */     String str332 = "[未解]";
/* 155 */     String str333 = "[未解]";
/* 156 */     String str334 = "[未解]";
/* 157 */     String str335 = "[未解]";
/* 158 */     String str336 = "[未解]";
/* 159 */     String str337 = "[未解]";
/* 160 */     String str338 = "[未解]";
/* 161 */     String str339 = "[未解]";
/* 162 */     String str340 = "[未解]";
/* 163 */     String str341 = "[未解]";
/* 164 */     String str342 = "[未解]";
/* 165 */     String str343 = "[未解]";
/* 166 */     String str344 = "[未解]";
/* 167 */     String str345 = "[未解]";
/* 168 */     String str346 = "[未解]";
/* 169 */     String str347 = "[未解]";
/* 170 */     String str348 = "[未解]";
/* 171 */     String str349 = "[未解]";
/* 172 */     String str350 = "[未解]";
/* 223 */     if (paramL1PcInstance.getQuest().get_step(40100) == 2)
/* 224 */       str1 = "[已完成]"; 
/* 225 */     if (paramL1PcInstance.getQuest().get_step(40101) == 2)
/* 226 */       str2 = "[已完成]"; 
/* 227 */     if (paramL1PcInstance.getQuest().get_step(40102) == 2)
/* 228 */       str3 = "[已完成]"; 
/* 229 */     if (paramL1PcInstance.getQuest().get_step(40103) == 2)
/* 230 */       str4 = "[已完成]"; 
/* 231 */     if (paramL1PcInstance.getQuest().get_step(40104) == 2)
/* 232 */       str5 = "[已完成]"; 
/* 233 */     if (paramL1PcInstance.getQuest().get_step(40105) == 2)
/* 234 */       str6 = "[已完成]"; 
/* 235 */     if (paramL1PcInstance.getQuest().get_step(40106) == 2)
/* 236 */       str7 = "[已完成]"; 
/* 237 */     if (paramL1PcInstance.getQuest().get_step(40107) == 2)
/* 238 */       str8 = "[已完成]"; 
/* 239 */     if (paramL1PcInstance.getQuest().get_step(40108) == 2)
/* 240 */       str9 = "[已完成]"; 
/* 241 */     if (paramL1PcInstance.getQuest().get_step(40109) == 2)
/* 242 */       str10 = "[已完成]"; 
/* 243 */     if (paramL1PcInstance.getQuest().get_step(40110) == 2)
/* 244 */       str11 = "[已完成]"; 
/* 245 */     if (paramL1PcInstance.getQuest().get_step(40111) == 2)
/* 246 */       str12 = "[已完成]"; 
/* 247 */     if (paramL1PcInstance.getQuest().get_step(40112) == 2)
/* 248 */       str13 = "[已完成]"; 
/* 249 */     if (paramL1PcInstance.getQuest().get_step(40113) == 2)
/* 250 */       str14 = "[已完成]"; 
/* 251 */     if (paramL1PcInstance.getQuest().get_step(40114) == 2)
/* 252 */       str15 = "[已完成]"; 
/* 253 */     if (paramL1PcInstance.getQuest().get_step(40115) == 2)
/* 254 */       str16 = "[已完成]"; 
/* 255 */     if (paramL1PcInstance.getQuest().get_step(40116) == 2)
/* 256 */       str17 = "[已完成]"; 
/* 257 */     if (paramL1PcInstance.getQuest().get_step(40117) == 2)
/* 258 */       str18 = "[已完成]"; 
/* 259 */     if (paramL1PcInstance.getQuest().get_step(40118) == 2)
/* 260 */       str19 = "[已完成]"; 
/* 261 */     if (paramL1PcInstance.getQuest().get_step(40119) == 2)
/* 262 */       str20 = "[已完成]"; 
/* 263 */     if (paramL1PcInstance.getQuest().get_step(40120) == 2)
/* 264 */       str21 = "[已完成]"; 
/* 265 */     if (paramL1PcInstance.getQuest().get_step(40121) == 2)
/* 266 */       str22 = "[已完成]"; 
/* 267 */     if (paramL1PcInstance.getQuest().get_step(40122) == 2)
/* 268 */       str23 = "[已完成]"; 
/* 269 */     if (paramL1PcInstance.getQuest().get_step(40123) == 2)
/* 270 */       str24 = "[已完成]"; 
/* 271 */     if (paramL1PcInstance.getQuest().get_step(40124) == 2)
/* 272 */       str25 = "[已完成]"; 
/* 273 */     if (paramL1PcInstance.getQuest().get_step(40125) == 2)
/* 274 */       str26 = "[已完成]"; 
/* 275 */     if (paramL1PcInstance.getQuest().get_step(40126) == 2)
/* 276 */       str27 = "[已完成]"; 
/* 277 */     if (paramL1PcInstance.getQuest().get_step(40127) == 2)
/* 278 */       str28 = "[已完成]"; 
/* 279 */     if (paramL1PcInstance.getQuest().get_step(40128) == 2)
/* 280 */       str29 = "[已完成]"; 
/* 281 */     if (paramL1PcInstance.getQuest().get_step(40129) == 2)
/* 282 */       str30 = "[已完成]"; 
/* 283 */     if (paramL1PcInstance.getQuest().get_step(40130) == 2)
/* 284 */       str31 = "[已完成]"; 
/* 285 */     if (paramL1PcInstance.getQuest().get_step(40131) == 2)
/* 286 */       str32 = "[已完成]"; 
/* 287 */     if (paramL1PcInstance.getQuest().get_step(40132) == 2)
/* 288 */       str33 = "[已完成]"; 
/* 289 */     if (paramL1PcInstance.getQuest().get_step(40133) == 2)
/* 290 */       str34 = "[已完成]"; 
/* 291 */     if (paramL1PcInstance.getQuest().get_step(40134) == 2)
/* 292 */       str35 = "[已完成]"; 
/* 293 */     if (paramL1PcInstance.getQuest().get_step(40135) == 2)
/* 294 */       str36 = "[已完成]"; 
/* 295 */     if (paramL1PcInstance.getQuest().get_step(40136) == 2)
/* 296 */       str37 = "[已完成]"; 
/* 297 */     if (paramL1PcInstance.getQuest().get_step(40137) == 2)
/* 298 */       str38 = "[已完成]"; 
/* 299 */     if (paramL1PcInstance.getQuest().get_step(40138) == 2)
/* 300 */       str39 = "[已完成]"; 
/* 301 */     if (paramL1PcInstance.getQuest().get_step(40139) == 2)
/* 302 */       str40 = "[已完成]"; 
/* 303 */     if (paramL1PcInstance.getQuest().get_step(40140) == 2)
/* 304 */       str41 = "[已完成]"; 
/* 305 */     if (paramL1PcInstance.getQuest().get_step(40141) == 2)
/* 306 */       str42 = "[已完成]"; 
/* 307 */     if (paramL1PcInstance.getQuest().get_step(40142) == 2)
/* 308 */       str43 = "[已完成]"; 
/* 309 */     if (paramL1PcInstance.getQuest().get_step(40143) == 2)
/* 310 */       str44 = "[已完成]"; 
/* 311 */     if (paramL1PcInstance.getQuest().get_step(40144) == 2)
/* 312 */       str45 = "[已完成]"; 
/* 313 */     if (paramL1PcInstance.getQuest().get_step(40145) == 2)
/* 314 */       str46 = "[已完成]"; 
/* 315 */     if (paramL1PcInstance.getQuest().get_step(40146) == 2)
/* 316 */       str47 = "[已完成]"; 
/* 317 */     if (paramL1PcInstance.getQuest().get_step(40147) == 2)
/* 318 */       str48 = "[已完成]"; 
/* 319 */     if (paramL1PcInstance.getQuest().get_step(40148) == 2)
/* 320 */       str49 = "[已完成]"; 
/* 321 */     if (paramL1PcInstance.getQuest().get_step(40149) == 2)
/* 322 */       str50 = "[已完成]"; 
/* 323 */     if (paramL1PcInstance.getQuest().get_step(40150) == 2)
/* 324 */       str51 = "[已完成]"; 
/* 325 */     if (paramL1PcInstance.getQuest().get_step(40151) == 2)
/* 326 */       str52 = "[已完成]"; 
/* 327 */     if (paramL1PcInstance.getQuest().get_step(40152) == 2)
/* 328 */       str53 = "[已完成]"; 
/* 329 */     if (paramL1PcInstance.getQuest().get_step(40153) == 2)
/* 330 */       str54 = "[已完成]"; 
/* 331 */     if (paramL1PcInstance.getQuest().get_step(40154) == 2)
/* 332 */       str55 = "[已完成]"; 
/* 333 */     if (paramL1PcInstance.getQuest().get_step(40155) == 2)
/* 334 */       str56 = "[已完成]"; 
/* 335 */     if (paramL1PcInstance.getQuest().get_step(40156) == 2)
/* 336 */       str57 = "[已完成]"; 
/* 337 */     if (paramL1PcInstance.getQuest().get_step(40157) == 2)
/* 338 */       str58 = "[已完成]"; 
/* 339 */     if (paramL1PcInstance.getQuest().get_step(40158) == 2)
/* 340 */       str59 = "[已完成]"; 
/* 341 */     if (paramL1PcInstance.getQuest().get_step(40159) == 2)
/* 342 */       str60 = "[已完成]"; 
/* 343 */     if (paramL1PcInstance.getQuest().get_step(40160) == 2)
/* 344 */       str61 = "[已完成]"; 
/* 345 */     if (paramL1PcInstance.getQuest().get_step(40161) == 2)
/* 346 */       str62 = "[已完成]"; 
/* 347 */     if (paramL1PcInstance.getQuest().get_step(40162) == 2)
/* 348 */       str63 = "[已完成]"; 
/* 349 */     if (paramL1PcInstance.getQuest().get_step(40163) == 2)
/* 350 */       str64 = "[已完成]"; 
/* 351 */     if (paramL1PcInstance.getQuest().get_step(40164) == 2)
/* 352 */       str65 = "[已完成]"; 
/* 353 */     if (paramL1PcInstance.getQuest().get_step(40165) == 2)
/* 354 */       str66 = "[已完成]"; 
/* 355 */     if (paramL1PcInstance.getQuest().get_step(40166) == 2)
/* 356 */       str67 = "[已完成]"; 
/* 357 */     if (paramL1PcInstance.getQuest().get_step(40167) == 2)
/* 358 */       str68 = "[已完成]"; 
/* 359 */     if (paramL1PcInstance.getQuest().get_step(40168) == 2)
/* 360 */       str69 = "[已完成]"; 
/* 361 */     if (paramL1PcInstance.getQuest().get_step(40169) == 2)
/* 362 */       str70 = "[已完成]"; 
/* 363 */     if (paramL1PcInstance.getQuest().get_step(40170) == 2)
/* 364 */       str71 = "[已完成]"; 
/* 365 */     if (paramL1PcInstance.getQuest().get_step(40171) == 2)
/* 366 */       str72 = "[已完成]"; 
/* 367 */     if (paramL1PcInstance.getQuest().get_step(40172) == 2)
/* 368 */       str73 = "[已完成]"; 
/* 369 */     if (paramL1PcInstance.getQuest().get_step(40173) == 2)
/* 370 */       str74 = "[已完成]"; 
/* 371 */     if (paramL1PcInstance.getQuest().get_step(40174) == 2)
/* 372 */       str75 = "[已完成]"; 
/* 373 */     if (paramL1PcInstance.getQuest().get_step(40175) == 2)
/* 374 */       str76 = "[已完成]"; 
/* 375 */     if (paramL1PcInstance.getQuest().get_step(40176) == 2)
/* 376 */       str77 = "[已完成]"; 
/* 377 */     if (paramL1PcInstance.getQuest().get_step(40177) == 2)
/* 378 */       str78 = "[已完成]"; 
/* 379 */     if (paramL1PcInstance.getQuest().get_step(40178) == 2)
/* 380 */       str79 = "[已完成]"; 
/* 381 */     if (paramL1PcInstance.getQuest().get_step(40179) == 2)
/* 382 */       str80 = "[已完成]"; 
/* 383 */     if (paramL1PcInstance.getQuest().get_step(40180) == 2)
/* 384 */       str81 = "[已完成]"; 
/* 385 */     if (paramL1PcInstance.getQuest().get_step(40181) == 2)
/* 386 */       str82 = "[已完成]"; 
/* 387 */     if (paramL1PcInstance.getQuest().get_step(40182) == 2)
/* 388 */       str83 = "[已完成]"; 
/* 389 */     if (paramL1PcInstance.getQuest().get_step(40183) == 2)
/* 390 */       str84 = "[已完成]"; 
/* 391 */     if (paramL1PcInstance.getQuest().get_step(40184) == 2)
/* 392 */       str85 = "[已完成]"; 
/* 393 */     if (paramL1PcInstance.getQuest().get_step(40185) == 2)
/* 394 */       str86 = "[已完成]"; 
/* 395 */     if (paramL1PcInstance.getQuest().get_step(40186) == 2)
/* 396 */       str87 = "[已完成]"; 
/* 397 */     if (paramL1PcInstance.getQuest().get_step(40187) == 2)
/* 398 */       str88 = "[已完成]"; 
/* 399 */     if (paramL1PcInstance.getQuest().get_step(40188) == 2)
/* 400 */       str89 = "[已完成]"; 
/* 401 */     if (paramL1PcInstance.getQuest().get_step(40189) == 2)
/* 402 */       str90 = "[已完成]"; 
/* 403 */     if (paramL1PcInstance.getQuest().get_step(40190) == 2)
/* 404 */       str91 = "[已完成]"; 
/* 405 */     if (paramL1PcInstance.getQuest().get_step(40191) == 2)
/* 406 */       str92 = "[已完成]"; 
/* 407 */     if (paramL1PcInstance.getQuest().get_step(40192) == 2)
/* 408 */       str93 = "[已完成]"; 
/* 409 */     if (paramL1PcInstance.getQuest().get_step(40193) == 2)
/* 410 */       str94 = "[已完成]"; 
/* 411 */     if (paramL1PcInstance.getQuest().get_step(40194) == 2)
/* 412 */       str95 = "[已完成]"; 
/* 413 */     if (paramL1PcInstance.getQuest().get_step(40195) == 2)
/* 414 */       str96 = "[已完成]"; 
/* 415 */     if (paramL1PcInstance.getQuest().get_step(40196) == 2)
/* 416 */       str97 = "[已完成]"; 
/* 417 */     if (paramL1PcInstance.getQuest().get_step(40197) == 2)
/* 418 */       str98 = "[已完成]"; 
/* 419 */     if (paramL1PcInstance.getQuest().get_step(40198) == 2)
/* 420 */       str99 = "[已完成]"; 
/* 421 */     if (paramL1PcInstance.getQuest().get_step(40199) == 2)
/* 422 */       str100 = "[已完成]"; 
/* 423 */     if (paramL1PcInstance.getQuest().get_step(40200) == 2)
/* 424 */       str101 = "[已完成]"; 
/* 425 */     if (paramL1PcInstance.getQuest().get_step(40201) == 2)
/* 426 */       str102 = "[已完成]"; 
/* 427 */     if (paramL1PcInstance.getQuest().get_step(40202) == 2)
/* 428 */       str103 = "[已完成]"; 
/* 429 */     if (paramL1PcInstance.getQuest().get_step(40203) == 2)
/* 430 */       str104 = "[已完成]"; 
/* 431 */     if (paramL1PcInstance.getQuest().get_step(40204) == 2)
/* 432 */       str105 = "[已完成]"; 
/* 433 */     if (paramL1PcInstance.getQuest().get_step(40205) == 2)
/* 434 */       str106 = "[已完成]"; 
/* 435 */     if (paramL1PcInstance.getQuest().get_step(40206) == 2)
/* 436 */       str107 = "[已完成]"; 
/* 437 */     if (paramL1PcInstance.getQuest().get_step(40207) == 2)
/* 438 */       str108 = "[已完成]"; 
/* 439 */     if (paramL1PcInstance.getQuest().get_step(40208) == 2)
/* 440 */       str109 = "[已完成]"; 
/* 441 */     if (paramL1PcInstance.getQuest().get_step(40209) == 2)
/* 442 */       str110 = "[已完成]"; 
/* 443 */     if (paramL1PcInstance.getQuest().get_step(40210) == 2)
/* 444 */       str111 = "[已完成]"; 
/* 445 */     if (paramL1PcInstance.getQuest().get_step(40211) == 2)
/* 446 */       str112 = "[已完成]"; 
/* 447 */     if (paramL1PcInstance.getQuest().get_step(40212) == 2)
/* 448 */       str113 = "[已完成]"; 
/* 449 */     if (paramL1PcInstance.getQuest().get_step(40213) == 2)
/* 450 */       str114 = "[已完成]"; 
/* 451 */     if (paramL1PcInstance.getQuest().get_step(40214) == 2)
/* 452 */       str115 = "[已完成]"; 
/* 453 */     if (paramL1PcInstance.getQuest().get_step(40215) == 2)
/* 454 */       str116 = "[已完成]"; 
/* 455 */     if (paramL1PcInstance.getQuest().get_step(40216) == 2)
/* 456 */       str117 = "[已完成]"; 
/* 457 */     if (paramL1PcInstance.getQuest().get_step(40217) == 2)
/* 458 */       str118 = "[已完成]"; 
/* 459 */     if (paramL1PcInstance.getQuest().get_step(40218) == 2)
/* 460 */       str119 = "[已完成]"; 
/* 461 */     if (paramL1PcInstance.getQuest().get_step(40219) == 2)
/* 462 */       str120 = "[已完成]"; 
/* 463 */     if (paramL1PcInstance.getQuest().get_step(40220) == 2)
/* 464 */       str121 = "[已完成]"; 
/* 465 */     if (paramL1PcInstance.getQuest().get_step(40221) == 2)
/* 466 */       str122 = "[已完成]"; 
/* 467 */     if (paramL1PcInstance.getQuest().get_step(40222) == 2)
/* 468 */       str123 = "[已完成]"; 
/* 469 */     if (paramL1PcInstance.getQuest().get_step(40223) == 2)
/* 470 */       str124 = "[已完成]"; 
/* 471 */     if (paramL1PcInstance.getQuest().get_step(40224) == 2)
/* 472 */       str125 = "[已完成]"; 
/* 473 */     if (paramL1PcInstance.getQuest().get_step(40225) == 2)
/* 474 */       str126 = "[已完成]"; 
/* 475 */     if (paramL1PcInstance.getQuest().get_step(40226) == 2)
/* 476 */       str127 = "[已完成]"; 
/* 477 */     if (paramL1PcInstance.getQuest().get_step(40227) == 2)
/* 478 */       str128 = "[已完成]"; 
/* 479 */     if (paramL1PcInstance.getQuest().get_step(40228) == 2)
/* 480 */       str129 = "[已完成]"; 
/* 481 */     if (paramL1PcInstance.getQuest().get_step(40229) == 2)
/* 482 */       str130 = "[已完成]"; 
/* 483 */     if (paramL1PcInstance.getQuest().get_step(40230) == 2)
/* 484 */       str131 = "[已完成]"; 
/* 485 */     if (paramL1PcInstance.getQuest().get_step(40231) == 2)
/* 486 */       str132 = "[已完成]"; 
/* 487 */     if (paramL1PcInstance.getQuest().get_step(40232) == 2)
/* 488 */       str133 = "[已完成]"; 
/* 489 */     if (paramL1PcInstance.getQuest().get_step(40233) == 2)
/* 490 */       str134 = "[已完成]"; 
/* 491 */     if (paramL1PcInstance.getQuest().get_step(40234) == 2)
/* 492 */       str135 = "[已完成]"; 
/* 493 */     if (paramL1PcInstance.getQuest().get_step(40235) == 2)
/* 494 */       str136 = "[已完成]"; 
/* 495 */     if (paramL1PcInstance.getQuest().get_step(40236) == 2)
/* 496 */       str137 = "[已完成]"; 
/* 497 */     if (paramL1PcInstance.getQuest().get_step(40237) == 2)
/* 498 */       str138 = "[已完成]"; 
/* 499 */     if (paramL1PcInstance.getQuest().get_step(40238) == 2)
/* 500 */       str139 = "[已完成]"; 
/* 501 */     if (paramL1PcInstance.getQuest().get_step(40239) == 2)
/* 502 */       str140 = "[已完成]"; 
/* 503 */     if (paramL1PcInstance.getQuest().get_step(40240) == 2)
/* 504 */       str141 = "[已完成]"; 
/* 505 */     if (paramL1PcInstance.getQuest().get_step(40241) == 2)
/* 506 */       str142 = "[已完成]"; 
/* 507 */     if (paramL1PcInstance.getQuest().get_step(40242) == 2)
/* 508 */       str143 = "[已完成]"; 
/* 509 */     if (paramL1PcInstance.getQuest().get_step(40243) == 2)
/* 510 */       str144 = "[已完成]"; 
/* 511 */     if (paramL1PcInstance.getQuest().get_step(40244) == 2)
/* 512 */       str145 = "[已完成]"; 
/* 513 */     if (paramL1PcInstance.getQuest().get_step(40245) == 2)
/* 514 */       str146 = "[已完成]"; 
/* 515 */     if (paramL1PcInstance.getQuest().get_step(40246) == 2)
/* 516 */       str147 = "[已完成]"; 
/* 517 */     if (paramL1PcInstance.getQuest().get_step(40247) == 2)
/* 518 */       str148 = "[已完成]"; 
/* 519 */     if (paramL1PcInstance.getQuest().get_step(40248) == 2)
/* 520 */       str149 = "[已完成]"; 
/* 521 */     if (paramL1PcInstance.getQuest().get_step(40249) == 2)
/* 522 */       str150 = "[已完成]"; 
/* 523 */     if (paramL1PcInstance.getQuest().get_step(40250) == 2)
/* 524 */       str151 = "[已完成]"; 
/* 525 */     if (paramL1PcInstance.getQuest().get_step(40251) == 2)
/* 526 */       str152 = "[已完成]"; 
/* 527 */     if (paramL1PcInstance.getQuest().get_step(40252) == 2)
/* 528 */       str153 = "[已完成]"; 
/* 529 */     if (paramL1PcInstance.getQuest().get_step(40253) == 2)
/* 530 */       str154 = "[已完成]"; 
/* 531 */     if (paramL1PcInstance.getQuest().get_step(40254) == 2)
/* 532 */       str155 = "[已完成]"; 
/* 533 */     if (paramL1PcInstance.getQuest().get_step(40255) == 2)
/* 534 */       str156 = "[已完成]"; 
/* 535 */     if (paramL1PcInstance.getQuest().get_step(40256) == 2)
/* 536 */       str157 = "[已完成]"; 
/* 537 */     if (paramL1PcInstance.getQuest().get_step(40257) == 2)
/* 538 */       str158 = "[已完成]"; 
/* 539 */     if (paramL1PcInstance.getQuest().get_step(40258) == 2)
/* 540 */       str159 = "[已完成]"; 
/* 541 */     if (paramL1PcInstance.getQuest().get_step(40259) == 2)
/* 542 */       str160 = "[已完成]"; 
/* 543 */     if (paramL1PcInstance.getQuest().get_step(40260) == 2)
/* 544 */       str161 = "[已完成]"; 
/* 545 */     if (paramL1PcInstance.getQuest().get_step(40261) == 2)
/* 546 */       str162 = "[已完成]"; 
/* 547 */     if (paramL1PcInstance.getQuest().get_step(40262) == 2)
/* 548 */       str163 = "[已完成]"; 
/* 549 */     if (paramL1PcInstance.getQuest().get_step(40263) == 2)
/* 550 */       str164 = "[已完成]"; 
/* 551 */     if (paramL1PcInstance.getQuest().get_step(40264) == 2)
/* 552 */       str165 = "[已完成]"; 
/* 553 */     if (paramL1PcInstance.getQuest().get_step(40265) == 2)
/* 554 */       str166 = "[已完成]"; 
/* 555 */     if (paramL1PcInstance.getQuest().get_step(40266) == 2)
/* 556 */       str167 = "[已完成]"; 
/* 557 */     if (paramL1PcInstance.getQuest().get_step(40267) == 2)
/* 558 */       str168 = "[已完成]"; 
/* 559 */     if (paramL1PcInstance.getQuest().get_step(40268) == 2)
/* 560 */       str169 = "[已完成]"; 
/* 561 */     if (paramL1PcInstance.getQuest().get_step(40269) == 2)
/* 562 */       str170 = "[已完成]"; 
/* 563 */     if (paramL1PcInstance.getQuest().get_step(40270) == 2)
/* 564 */       str171 = "[已完成]"; 
/* 565 */     if (paramL1PcInstance.getQuest().get_step(40271) == 2)
/* 566 */       str172 = "[已完成]"; 
/* 567 */     if (paramL1PcInstance.getQuest().get_step(40272) == 2)
/* 568 */       str173 = "[已完成]"; 
/* 569 */     if (paramL1PcInstance.getQuest().get_step(40273) == 2)
/* 570 */       str174 = "[已完成]"; 
/* 571 */     if (paramL1PcInstance.getQuest().get_step(40274) == 2)
/* 572 */       str175 = "[已完成]"; 
/* 573 */     if (paramL1PcInstance.getQuest().get_step(40275) == 2)
/* 574 */       str176 = "[已完成]"; 
/* 575 */     if (paramL1PcInstance.getQuest().get_step(40276) == 2)
/* 576 */       str177 = "[已完成]"; 
/* 577 */     if (paramL1PcInstance.getQuest().get_step(40277) == 2)
/* 578 */       str178 = "[已完成]"; 
/* 579 */     if (paramL1PcInstance.getQuest().get_step(40278) == 2)
/* 580 */       str179 = "[已完成]"; 
/* 581 */     if (paramL1PcInstance.getQuest().get_step(40279) == 2)
/* 582 */       str180 = "[已完成]"; 
/* 583 */     if (paramL1PcInstance.getQuest().get_step(40280) == 2)
/* 584 */       str181 = "[已完成]"; 
/* 585 */     if (paramL1PcInstance.getQuest().get_step(40281) == 2)
/* 586 */       str182 = "[已完成]"; 
/* 587 */     if (paramL1PcInstance.getQuest().get_step(40282) == 2)
/* 588 */       str183 = "[已完成]"; 
/* 589 */     if (paramL1PcInstance.getQuest().get_step(40283) == 2)
/* 590 */       str184 = "[已完成]"; 
/* 591 */     if (paramL1PcInstance.getQuest().get_step(40284) == 2)
/* 592 */       str185 = "[已完成]"; 
/* 593 */     if (paramL1PcInstance.getQuest().get_step(40285) == 2)
/* 594 */       str186 = "[已完成]"; 
/* 595 */     if (paramL1PcInstance.getQuest().get_step(40286) == 2)
/* 596 */       str187 = "[已完成]"; 
/* 597 */     if (paramL1PcInstance.getQuest().get_step(40287) == 2)
/* 598 */       str188 = "[已完成]"; 
/* 599 */     if (paramL1PcInstance.getQuest().get_step(40288) == 2)
/* 600 */       str189 = "[已完成]"; 
/* 601 */     if (paramL1PcInstance.getQuest().get_step(40289) == 2)
/* 602 */       str190 = "[已完成]"; 
/* 603 */     if (paramL1PcInstance.getQuest().get_step(40290) == 2)
/* 604 */       str191 = "[已完成]"; 
/* 605 */     if (paramL1PcInstance.getQuest().get_step(40291) == 2)
/* 606 */       str192 = "[已完成]"; 
/* 607 */     if (paramL1PcInstance.getQuest().get_step(40292) == 2)
/* 608 */       str193 = "[已完成]"; 
/* 609 */     if (paramL1PcInstance.getQuest().get_step(40293) == 2)
/* 610 */       str194 = "[已完成]"; 
/* 611 */     if (paramL1PcInstance.getQuest().get_step(40294) == 2)
/* 612 */       str195 = "[已完成]"; 
/* 613 */     if (paramL1PcInstance.getQuest().get_step(40295) == 2)
/* 614 */       str196 = "[已完成]"; 
/* 615 */     if (paramL1PcInstance.getQuest().get_step(40296) == 2)
/* 616 */       str197 = "[已完成]"; 
/* 617 */     if (paramL1PcInstance.getQuest().get_step(40297) == 2)
/* 618 */       str198 = "[已完成]"; 
/* 619 */     if (paramL1PcInstance.getQuest().get_step(40298) == 2)
/* 620 */       str199 = "[已完成]"; 
/* 621 */     if (paramL1PcInstance.getQuest().get_step(40299) == 2)
/* 622 */       str200 = "[已完成]"; 
/* 423 */     if (paramL1PcInstance.getQuest().get_step(40300) == 2)
/* 424 */       str201 = "[已完成]"; 
/* 425 */     if (paramL1PcInstance.getQuest().get_step(40301) == 2)
/* 426 */       str202 = "[已完成]"; 
/* 427 */     if (paramL1PcInstance.getQuest().get_step(40302) == 2)
/* 428 */       str203 = "[已完成]"; 
/* 429 */     if (paramL1PcInstance.getQuest().get_step(40303) == 2)
/* 430 */       str204 = "[已完成]"; 
/* 431 */     if (paramL1PcInstance.getQuest().get_step(40304) == 2)
/* 432 */       str205 = "[已完成]"; 
/* 433 */     if (paramL1PcInstance.getQuest().get_step(40305) == 2)
/* 434 */       str206 = "[已完成]"; 
/* 435 */     if (paramL1PcInstance.getQuest().get_step(40306) == 2)
/* 436 */       str207 = "[已完成]"; 
/* 437 */     if (paramL1PcInstance.getQuest().get_step(40307) == 2)
/* 438 */       str208 = "[已完成]"; 
/* 439 */     if (paramL1PcInstance.getQuest().get_step(40308) == 2)
/* 440 */       str209 = "[已完成]"; 
/* 441 */     if (paramL1PcInstance.getQuest().get_step(40309) == 2)
/* 442 */       str210 = "[已完成]"; 
/* 443 */     if (paramL1PcInstance.getQuest().get_step(40310) == 2)
/* 444 */       str211 = "[已完成]"; 
/* 445 */     if (paramL1PcInstance.getQuest().get_step(40311) == 2)
/* 446 */       str212 = "[已完成]"; 
/* 447 */     if (paramL1PcInstance.getQuest().get_step(40312) == 2)
/* 448 */       str213 = "[已完成]"; 
/* 449 */     if (paramL1PcInstance.getQuest().get_step(40313) == 2)
/* 450 */       str214 = "[已完成]"; 
/* 451 */     if (paramL1PcInstance.getQuest().get_step(40314) == 2)
/* 452 */       str215 = "[已完成]"; 
/* 453 */     if (paramL1PcInstance.getQuest().get_step(40315) == 2)
/* 454 */       str216 = "[已完成]"; 
/* 455 */     if (paramL1PcInstance.getQuest().get_step(40316) == 2)
/* 456 */       str217 = "[已完成]"; 
/* 457 */     if (paramL1PcInstance.getQuest().get_step(40317) == 2)
/* 458 */       str218 = "[已完成]"; 
/* 459 */     if (paramL1PcInstance.getQuest().get_step(40318) == 2)
/* 460 */       str219 = "[已完成]"; 
/* 461 */     if (paramL1PcInstance.getQuest().get_step(40319) == 2)
/* 462 */       str220 = "[已完成]"; 
/* 463 */     if (paramL1PcInstance.getQuest().get_step(40320) == 2)
/* 464 */       str221 = "[已完成]"; 
/* 465 */     if (paramL1PcInstance.getQuest().get_step(40321) == 2)
/* 466 */       str222 = "[已完成]"; 
/* 467 */     if (paramL1PcInstance.getQuest().get_step(40322) == 2)
/* 468 */       str223 = "[已完成]"; 
/* 469 */     if (paramL1PcInstance.getQuest().get_step(40323) == 2)
/* 470 */       str224 = "[已完成]"; 
/* 471 */     if (paramL1PcInstance.getQuest().get_step(40324) == 2)
/* 472 */       str225 = "[已完成]"; 
/* 473 */     if (paramL1PcInstance.getQuest().get_step(40325) == 2)
/* 474 */       str226 = "[已完成]"; 
/* 475 */     if (paramL1PcInstance.getQuest().get_step(40326) == 2)
/* 476 */       str227 = "[已完成]"; 
/* 477 */     if (paramL1PcInstance.getQuest().get_step(40327) == 2)
/* 478 */       str228 = "[已完成]"; 
/* 479 */     if (paramL1PcInstance.getQuest().get_step(40328) == 2)
/* 480 */       str229 = "[已完成]"; 
/* 481 */     if (paramL1PcInstance.getQuest().get_step(40329) == 2)
/* 482 */       str230 = "[已完成]"; 
/* 483 */     if (paramL1PcInstance.getQuest().get_step(40330) == 2)
/* 484 */       str231 = "[已完成]"; 
/* 485 */     if (paramL1PcInstance.getQuest().get_step(40331) == 2)
/* 486 */       str232 = "[已完成]"; 
/* 487 */     if (paramL1PcInstance.getQuest().get_step(40332) == 2)
/* 488 */       str233 = "[已完成]"; 
/* 489 */     if (paramL1PcInstance.getQuest().get_step(40333) == 2)
/* 490 */       str234 = "[已完成]"; 
/* 491 */     if (paramL1PcInstance.getQuest().get_step(40334) == 2)
/* 492 */       str235 = "[已完成]"; 
/* 493 */     if (paramL1PcInstance.getQuest().get_step(40335) == 2)
/* 494 */       str236 = "[已完成]"; 
/* 495 */     if (paramL1PcInstance.getQuest().get_step(40336) == 2)
/* 496 */       str237 = "[已完成]"; 
/* 497 */     if (paramL1PcInstance.getQuest().get_step(40337) == 2)
/* 498 */       str238 = "[已完成]"; 
/* 499 */     if (paramL1PcInstance.getQuest().get_step(40338) == 2)
/* 500 */       str239 = "[已完成]"; 
/* 501 */     if (paramL1PcInstance.getQuest().get_step(40339) == 2)
/* 502 */       str240 = "[已完成]"; 
/* 503 */     if (paramL1PcInstance.getQuest().get_step(40340) == 2)
/* 504 */       str241 = "[已完成]"; 
/* 505 */     if (paramL1PcInstance.getQuest().get_step(40341) == 2)
/* 506 */       str242 = "[已完成]"; 
/* 507 */     if (paramL1PcInstance.getQuest().get_step(40342) == 2)
/* 508 */       str243 = "[已完成]"; 
/* 509 */     if (paramL1PcInstance.getQuest().get_step(40343) == 2)
/* 510 */       str244 = "[已完成]"; 
/* 511 */     if (paramL1PcInstance.getQuest().get_step(40344) == 2)
/* 512 */       str245 = "[已完成]"; 
/* 513 */     if (paramL1PcInstance.getQuest().get_step(40345) == 2)
/* 514 */       str246 = "[已完成]"; 
/* 515 */     if (paramL1PcInstance.getQuest().get_step(40346) == 2)
/* 516 */       str247 = "[已完成]"; 
/* 517 */     if (paramL1PcInstance.getQuest().get_step(40347) == 2)
/* 518 */       str248 = "[已完成]"; 
/* 519 */     if (paramL1PcInstance.getQuest().get_step(40348) == 2)
/* 520 */       str249 = "[已完成]"; 
/* 521 */     if (paramL1PcInstance.getQuest().get_step(40349) == 2)
/* 522 */       str250 = "[已完成]"; 
/* 523 */     if (paramL1PcInstance.getQuest().get_step(40350) == 2)
/* 524 */       str251 = "[已完成]"; 
/* 525 */     if (paramL1PcInstance.getQuest().get_step(40351) == 2)
/* 526 */       str252 = "[已完成]"; 
/* 527 */     if (paramL1PcInstance.getQuest().get_step(40352) == 2)
/* 528 */       str253 = "[已完成]"; 
/* 529 */     if (paramL1PcInstance.getQuest().get_step(40353) == 2)
/* 530 */       str254 = "[已完成]"; 
/* 531 */     if (paramL1PcInstance.getQuest().get_step(40354) == 2)
/* 532 */       str255 = "[已完成]"; 
/* 533 */     if (paramL1PcInstance.getQuest().get_step(40355) == 2)
/* 534 */       str256 = "[已完成]"; 
/* 535 */     if (paramL1PcInstance.getQuest().get_step(40356) == 2)
/* 536 */       str257 = "[已完成]"; 
/* 537 */     if (paramL1PcInstance.getQuest().get_step(40357) == 2)
/* 538 */       str258 = "[已完成]"; 
/* 539 */     if (paramL1PcInstance.getQuest().get_step(40358) == 2)
/* 540 */       str259 = "[已完成]"; 
/* 541 */     if (paramL1PcInstance.getQuest().get_step(40359) == 2)
/* 542 */       str260 = "[已完成]"; 
/* 543 */     if (paramL1PcInstance.getQuest().get_step(40360) == 2)
/* 544 */       str261 = "[已完成]"; 
/* 545 */     if (paramL1PcInstance.getQuest().get_step(40361) == 2)
/* 546 */       str262 = "[已完成]"; 
/* 547 */     if (paramL1PcInstance.getQuest().get_step(40362) == 2)
/* 548 */       str263 = "[已完成]"; 
/* 549 */     if (paramL1PcInstance.getQuest().get_step(40363) == 2)
/* 550 */       str264 = "[已完成]"; 
/* 551 */     if (paramL1PcInstance.getQuest().get_step(40364) == 2)
/* 552 */       str265 = "[已完成]"; 
/* 553 */     if (paramL1PcInstance.getQuest().get_step(40365) == 2)
/* 554 */       str266 = "[已完成]"; 
/* 555 */     if (paramL1PcInstance.getQuest().get_step(40366) == 2)
/* 556 */       str267 = "[已完成]"; 
/* 557 */     if (paramL1PcInstance.getQuest().get_step(40367) == 2)
/* 558 */       str268 = "[已完成]"; 
/* 559 */     if (paramL1PcInstance.getQuest().get_step(40368) == 2)
/* 560 */       str269 = "[已完成]"; 
/* 561 */     if (paramL1PcInstance.getQuest().get_step(40369) == 2)
/* 562 */       str270 = "[已完成]"; 
/* 563 */     if (paramL1PcInstance.getQuest().get_step(40370) == 2)
/* 564 */       str271 = "[已完成]"; 
/* 565 */     if (paramL1PcInstance.getQuest().get_step(40371) == 2)
/* 566 */       str272 = "[已完成]"; 
/* 567 */     if (paramL1PcInstance.getQuest().get_step(40372) == 2)
/* 568 */       str273 = "[已完成]"; 
/* 569 */     if (paramL1PcInstance.getQuest().get_step(40373) == 2)
/* 570 */       str274 = "[已完成]"; 
/* 571 */     if (paramL1PcInstance.getQuest().get_step(40374) == 2)
/* 572 */       str275 = "[已完成]"; 
/* 573 */     if (paramL1PcInstance.getQuest().get_step(40375) == 2)
/* 574 */       str276 = "[已完成]"; 
/* 575 */     if (paramL1PcInstance.getQuest().get_step(40376) == 2)
/* 576 */       str277 = "[已完成]"; 
/* 577 */     if (paramL1PcInstance.getQuest().get_step(40377) == 2)
/* 578 */       str278 = "[已完成]"; 
/* 579 */     if (paramL1PcInstance.getQuest().get_step(40378) == 2)
/* 580 */       str279 = "[已完成]"; 
/* 581 */     if (paramL1PcInstance.getQuest().get_step(40379) == 2)
/* 582 */       str280 = "[已完成]"; 
/* 583 */     if (paramL1PcInstance.getQuest().get_step(40380) == 2)
/* 584 */       str281 = "[已完成]"; 
/* 585 */     if (paramL1PcInstance.getQuest().get_step(40381) == 2)
/* 586 */       str282 = "[已完成]"; 
/* 587 */     if (paramL1PcInstance.getQuest().get_step(40382) == 2)
/* 588 */       str283 = "[已完成]"; 
/* 589 */     if (paramL1PcInstance.getQuest().get_step(40383) == 2)
/* 590 */       str284 = "[已完成]"; 
/* 591 */     if (paramL1PcInstance.getQuest().get_step(40384) == 2)
/* 592 */       str285 = "[已完成]"; 
/* 593 */     if (paramL1PcInstance.getQuest().get_step(40385) == 2)
/* 594 */       str286 = "[已完成]"; 
/* 595 */     if (paramL1PcInstance.getQuest().get_step(40386) == 2)
/* 596 */       str287 = "[已完成]"; 
/* 597 */     if (paramL1PcInstance.getQuest().get_step(40387) == 2)
/* 598 */       str288 = "[已完成]"; 
/* 599 */     if (paramL1PcInstance.getQuest().get_step(40388) == 2)
/* 600 */       str289 = "[已完成]"; 
/* 601 */     if (paramL1PcInstance.getQuest().get_step(40389) == 2)
/* 602 */       str290 = "[已完成]"; 
/* 603 */     if (paramL1PcInstance.getQuest().get_step(40390) == 2)
/* 604 */       str291 = "[已完成]"; 
/* 605 */     if (paramL1PcInstance.getQuest().get_step(40391) == 2)
/* 606 */       str292 = "[已完成]"; 
/* 607 */     if (paramL1PcInstance.getQuest().get_step(40392) == 2)
/* 608 */       str293 = "[已完成]"; 
/* 609 */     if (paramL1PcInstance.getQuest().get_step(40393) == 2)
/* 610 */       str294 = "[已完成]"; 
/* 611 */     if (paramL1PcInstance.getQuest().get_step(40394) == 2)
/* 612 */       str295 = "[已完成]"; 
/* 613 */     if (paramL1PcInstance.getQuest().get_step(40395) == 2)
/* 614 */       str296 = "[已完成]"; 
/* 615 */     if (paramL1PcInstance.getQuest().get_step(40396) == 2)
/* 616 */       str297 = "[已完成]"; 
/* 617 */     if (paramL1PcInstance.getQuest().get_step(40397) == 2)
/* 618 */       str298 = "[已完成]"; 
/* 619 */     if (paramL1PcInstance.getQuest().get_step(40398) == 2)
/* 620 */       str299 = "[已完成]"; 
/* 621 */     if (paramL1PcInstance.getQuest().get_step(40399) == 2)
/* 622 */       str300 = "[已完成]"; 
/* 423 */     if (paramL1PcInstance.getQuest().get_step(40400) == 2)
/* 424 */       str301 = "[已完成]"; 
/* 425 */     if (paramL1PcInstance.getQuest().get_step(40401) == 2)
/* 426 */       str302 = "[已完成]"; 
/* 427 */     if (paramL1PcInstance.getQuest().get_step(40402) == 2)
/* 428 */       str303 = "[已完成]"; 
/* 429 */     if (paramL1PcInstance.getQuest().get_step(40403) == 2)
/* 430 */       str304 = "[已完成]"; 
/* 431 */     if (paramL1PcInstance.getQuest().get_step(40404) == 2)
/* 432 */       str305 = "[已完成]"; 
/* 433 */     if (paramL1PcInstance.getQuest().get_step(40405) == 2)
/* 434 */       str306 = "[已完成]"; 
/* 435 */     if (paramL1PcInstance.getQuest().get_step(40406) == 2)
/* 436 */       str307 = "[已完成]"; 
/* 437 */     if (paramL1PcInstance.getQuest().get_step(40407) == 2)
/* 438 */       str308 = "[已完成]"; 
/* 439 */     if (paramL1PcInstance.getQuest().get_step(40408) == 2)
/* 440 */       str309 = "[已完成]"; 
/* 441 */     if (paramL1PcInstance.getQuest().get_step(40409) == 2)
/* 442 */       str310 = "[已完成]"; 
/* 443 */     if (paramL1PcInstance.getQuest().get_step(40410) == 2)
/* 444 */       str311 = "[已完成]"; 
/* 445 */     if (paramL1PcInstance.getQuest().get_step(40411) == 2)
/* 446 */       str312 = "[已完成]"; 
/* 447 */     if (paramL1PcInstance.getQuest().get_step(40412) == 2)
/* 448 */       str313 = "[已完成]"; 
/* 449 */     if (paramL1PcInstance.getQuest().get_step(40413) == 2)
/* 450 */       str314 = "[已完成]"; 
/* 451 */     if (paramL1PcInstance.getQuest().get_step(40414) == 2)
/* 452 */       str315 = "[已完成]"; 
/* 453 */     if (paramL1PcInstance.getQuest().get_step(40415) == 2)
/* 454 */       str316 = "[已完成]"; 
/* 455 */     if (paramL1PcInstance.getQuest().get_step(40416) == 2)
/* 456 */       str317 = "[已完成]"; 
/* 457 */     if (paramL1PcInstance.getQuest().get_step(40417) == 2)
/* 458 */       str318 = "[已完成]"; 
/* 459 */     if (paramL1PcInstance.getQuest().get_step(40418) == 2)
/* 460 */       str319 = "[已完成]"; 
/* 461 */     if (paramL1PcInstance.getQuest().get_step(40419) == 2)
/* 462 */       str320 = "[已完成]"; 
/* 463 */     if (paramL1PcInstance.getQuest().get_step(40420) == 2)
/* 464 */       str321 = "[已完成]"; 
/* 465 */     if (paramL1PcInstance.getQuest().get_step(40421) == 2)
/* 466 */       str322 = "[已完成]"; 
/* 467 */     if (paramL1PcInstance.getQuest().get_step(40422) == 2)
/* 468 */       str323 = "[已完成]"; 
/* 469 */     if (paramL1PcInstance.getQuest().get_step(40423) == 2)
/* 470 */       str324 = "[已完成]"; 
/* 471 */     if (paramL1PcInstance.getQuest().get_step(40424) == 2)
/* 472 */       str325 = "[已完成]"; 
/* 473 */     if (paramL1PcInstance.getQuest().get_step(40425) == 2)
/* 474 */       str326 = "[已完成]"; 
/* 475 */     if (paramL1PcInstance.getQuest().get_step(40426) == 2)
/* 476 */       str327 = "[已完成]"; 
/* 477 */     if (paramL1PcInstance.getQuest().get_step(40427) == 2)
/* 478 */       str328 = "[已完成]"; 
/* 479 */     if (paramL1PcInstance.getQuest().get_step(40428) == 2)
/* 480 */       str329 = "[已完成]"; 
/* 481 */     if (paramL1PcInstance.getQuest().get_step(40429) == 2)
/* 482 */       str330 = "[已完成]"; 
/* 483 */     if (paramL1PcInstance.getQuest().get_step(40430) == 2)
/* 484 */       str331 = "[已完成]"; 
/* 485 */     if (paramL1PcInstance.getQuest().get_step(40431) == 2)
/* 486 */       str332 = "[已完成]"; 
/* 487 */     if (paramL1PcInstance.getQuest().get_step(40432) == 2)
/* 488 */       str333 = "[已完成]"; 
/* 489 */     if (paramL1PcInstance.getQuest().get_step(40433) == 2)
/* 490 */       str334 = "[已完成]"; 
/* 491 */     if (paramL1PcInstance.getQuest().get_step(40434) == 2)
/* 492 */       str335 = "[已完成]"; 
/* 493 */     if (paramL1PcInstance.getQuest().get_step(40435) == 2)
/* 494 */       str336 = "[已完成]"; 
/* 495 */     if (paramL1PcInstance.getQuest().get_step(40436) == 2)
/* 496 */       str337 = "[已完成]"; 
/* 497 */     if (paramL1PcInstance.getQuest().get_step(40437) == 2)
/* 498 */       str338 = "[已完成]"; 
/* 499 */     if (paramL1PcInstance.getQuest().get_step(40438) == 2)
/* 500 */       str339 = "[已完成]"; 
/* 501 */     if (paramL1PcInstance.getQuest().get_step(40439) == 2)
/* 502 */       str340 = "[已完成]"; 
/* 503 */     if (paramL1PcInstance.getQuest().get_step(40440) == 2)
/* 504 */       str341 = "[已完成]"; 
/* 505 */     if (paramL1PcInstance.getQuest().get_step(40441) == 2)
/* 506 */       str342 = "[已完成]"; 
/* 507 */     if (paramL1PcInstance.getQuest().get_step(40442) == 2)
/* 508 */       str343 = "[已完成]"; 
/* 509 */     if (paramL1PcInstance.getQuest().get_step(40443) == 2)
/* 510 */       str344 = "[已完成]"; 
/* 511 */     if (paramL1PcInstance.getQuest().get_step(40444) == 2)
/* 512 */       str345 = "[已完成]"; 
/* 513 */     if (paramL1PcInstance.getQuest().get_step(40445) == 2)
/* 514 */       str346 = "[已完成]"; 
/* 515 */     if (paramL1PcInstance.getQuest().get_step(40446) == 2)
/* 516 */       str347 = "[已完成]"; 
/* 517 */     if (paramL1PcInstance.getQuest().get_step(40447) == 2)
/* 518 */       str348 = "[已完成]"; 
/* 519 */     if (paramL1PcInstance.getQuest().get_step(40448) == 2)
/* 520 */       str349 = "[已完成]"; 
/* 521 */     if (paramL1PcInstance.getQuest().get_step(40449) == 2)
/* 522 */       str350 = "[已完成]";
/* 623 */     paramL1PcInstance.setpag(0);
/* 624 */     paramL1PcInstance.sendPackets((ServerBasePacket)new S_NPCTalkReturn(paramL1NpcInstance.getId(), "collection", new String[] { str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, str16, str17, str18, str19, str20, str21, str22, str23, str24, str25, str26, str27, str28, str29, str30, str31, str32, str33, str34, str35, str36, str37, str38, str39, str40, str41, str42, str43, str44, str45, str46, str47, str48, str49, str50, str51, str52, str53, str54, str55, str56, str57, str58, str59, str60, str61, str62, str63, str64, str65, str66, str67, str68, str69, str70, str71, str72, str73, str74, str75, str76, str77, str78, str79, str80, str81, str82, str83, str84, str85, str86, str87, str88, str89, str90, str91, str92, str93, str94, str95, str96, str97, str98, str99, str100, str101, str102, str103, str104, str105, str106, str107, str108, str109, str110, str111, str112, str113, str114, str115, str116, str117, str118, str119, str120, str121, str122, str123, str124, str125, str126, str127, str128, str129, str130, str131, str132, str133, str134, str135, str136, str137, str138, str139, str140, str141, str142, str143, str144, str145, str146, str147, str148, str149, str150, str151, str152, str153, str154, str155, str156, str157, str158, str159, str160, str161, str162, str163, str164, str165, str166, str167, str168, str169, str170, str171, str172, str173, str174, str175, str176, str177, str178, str179, str180, str181, str182, str183, str184, str185, str186, str187, str188, str189, str190, str191, str192, str193, str194, str195, str196, str197, str198, str199, str200, str201, str202, str203, str204, str205, str206, str207, str208, str209, str210, str211, str212, str213, str214, str215, str216, str217, str218, str219, str220, str221, str222, str223, str224, str225, str226, str227, str228, str229, str230, str231, str232, str233, str234, str235, str236, str237, str238, str239, str240, str241, str242, str243, str244, str245, str246, str247, str248, str249, str250, str251, str252, str253, str254, str255, str256, str257, str258, str259, str260, str261, str262, str263, str264, str265, str266, str267, str268, str269, str270, str271, str272, str273, str274, str275, str276, str277, str278, str279, str280, str281, str282, str283, str284, str285, str286, str287, str288, str289, str290, str291, str292, str293, str294, str295, str296, str297, str298, str299, str300, str301, str302, str303, str304, str305, str306, str307, str308, str309, str310, str311, str312, str313, str314, str315, str316, str317, str318, str319, str320, str321, str322, str323, str324, str325, str326, str327, str328, str329, str330, str331, str332, str333, str334, str335, str336, str337, str338, str339, str340, str341, str342, str343, str344, str345, str346, str347, str348, str349, str350 }));
/*     */   }
/*     */ }


/* Location:              D:\Desk\381server.jar!\com\lineage\data\npc\event\Npc_GQuest1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */