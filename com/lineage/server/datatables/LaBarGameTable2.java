/*     */ package com.lineage.server.datatables;
/*     */ 
/*     */ import com.lineage.DatabaseFactory;
/*     */ import com.lineage.server.model.Instance.L1ItemInstance;
/*     */ import com.lineage.server.model.Instance.L1NpcInstance;
/*     */ import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_CloseList;
/*     */ import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_PacketBoxGree;
import com.lineage.server.serverpackets.S_PacketBoxGree1;
/*     */ import com.lineage.server.serverpackets.S_ServerMessage;
/*     */ import com.lineage.server.serverpackets.S_SystemMessage;
/*     */ import com.lineage.server.serverpackets.ServerBasePacket;
/*     */ import com.lineage.server.templates.L1Item;
/*     */ import com.lineage.server.utils.PerformanceTimer;
/*     */ import com.lineage.server.utils.RandomArrayList;
/*     */ import com.lineage.server.utils.SQLUtil;
/*     */ import com.lineage.server.world.World;

/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
import java.util.Random;
/*     */ import java.util.StringTokenizer;

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
/*     */ public class LaBarGameTable2
/*     */ {
/*  42 */   private static final Log _log = LogFactory.getLog(LaBarGameTable2.class);
/*     */   
/*  44 */   private static ArrayList<ArrayList<Object>> _array = new ArrayList<>();
/*     */   
/*     */   private static final String TOKEN = ",";
/*     */   
/*     */   private static LaBarGameTable2 _instance;
/*     */   
/*     */   public static LaBarGameTable2 get() {
/*  51 */     if (_instance == null) {
/*  52 */       _instance = new LaBarGameTable2();
/*     */     }
/*  54 */     return _instance;
/*     */   }
/*     */   
/*     */   private LaBarGameTable2() {
/*  58 */     PerformanceTimer timer = new PerformanceTimer();
/*  59 */     getData();
/*     */     
/*  61 */     if (_array.size() <= 0) {
/*  62 */       _array.clear();
/*  63 */       _array = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean forLaBarGame(String s, L1PcInstance pc, L1NpcInstance npc, int npcid, int oid) {
/*  68 */     if (_array.size() <= 0) {
/*  69 */       return false;
/*     */     }
/*  71 */     ArrayList<?> aTempData = null;
/*  73 */     for (int i = 0; i < _array.size(); i++) {
/*  74 */       aTempData = _array.get(i);
/*  75 */       if (aTempData.get(0) != null && ((Integer)aTempData.get(0)).intValue() == npcid && (
/*  76 */         (String)aTempData.get(1)).equals(s)) {
/*     */         
/*  78 */         if (pc.hasSkillEffect(123456)) {
/*  79 */           pc.sendPackets((ServerBasePacket)new S_SystemMessage("延遲中請稍後在操作"));
/*  80 */           return false;
/*     */         } 
/*     */         
/*  83 */         boolean isCreate = true;
/*     */         
/*  85 */         if (((Integer)aTempData.get(8)).intValue() != 0 && ((Integer)aTempData.get(9)).intValue() != 0 && 
/*  86 */           !pc.getInventory().checkItem(((Integer)aTempData.get(8)).intValue(), ((Integer)aTempData.get(9)).intValue())) {
/*  87 */           L1Item temp = ItemTable.get().getTemplate(((Integer)aTempData.get(8)).intValue());
/*  88 */           pc.sendPackets((ServerBasePacket)new S_ServerMessage(337, String.valueOf(temp.getName()) + "(" + (((Integer)aTempData.get(9)).intValue() - pc.getInventory().countItems(temp.getItemId())) + ")"));
/*  89 */           isCreate = false;
pc.sendPackets(new S_CloseList(pc.getId())); //關閉視窗
/*     */         } 
/*     */ 
/*     */         
/*  93 */         if (isCreate) {
/*  94 */           pc.getInventory().consumeItem(((Integer)aTempData.get(8)).intValue(), ((Integer)aTempData.get(9)).intValue());
/*     */         }
/*     */         
/*  97 */         if (isCreate) {
/*     */           
/*  99 */           String[] text = (String[])aTempData.get(2);
/* 100 */           int[] 抽卡道具編號1 = (int[])aTempData.get(3);

/*     */           if(抽卡道具編號1 != null){
/* 102 */           for (int j = 0; j < text.length; j++) {
/*     */             
/*     */             try {
/* 105 */               pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), text[j]));
/*     */ 
/*     */               
/* 108 */               pc.setcomtext0(抽卡道具編號1[j]);
/* 109 */               pc.setcomtextc0(text[j]);
/*     */               
/* 111 */               save0(pc);
/*     */               
/* 113 */               Thread.sleep(((Integer)aTempData.get(10)).intValue());
/*     */             
/*     */             }
/* 116 */             catch (InterruptedException e) {
/* 117 */               e.printStackTrace();
/*     */             } 
/*     */           }
				}
/*     */   
           int[] 機率 = (int[])aTempData.get(11);
           int[] 抽卡道具編號 = (int[])aTempData.get(3);
           int[] 累积機率 = new int[機率.length];
           累积機率[0] = 機率[0];

           if(機率 != null) {
           
           for (int i1 = 1; i1 < 機率.length; i1++) {
        	   累积機率[i1] = 累积機率[i1 - 1] + 機率[i1];
           }

           int sum = 累积機率[累积機率.length - 1]; // 機率的總和

           // 確保累積機率的最後一個元素等於機率總和
           累积機率[累积機率.length - 1] = sum;

           int randomNum = (int) (Math.random() * sum) + 1; // 生成隨機數

           int giveItemGet = -1;

           for (int i1 = 0; i1 < 累积機率.length; i1++) {
        	   if (randomNum < 累积機率[i1]) {
        		   giveItemGet = 抽卡道具編號[i1];
        		   break;
        	   }
           }
           
/*     */ 
/*     */           
/* 128 */           L1ItemInstance item = ItemTable.get().createItem(giveItemGet);
/*     */           
/* 130 */           if (item.isStackable()) {
/* 131 */             item.setCount(((Integer)aTempData.get(4)).intValue());
/*     */           } else {
/* 133 */             item.setCount(1L);
/*     */           } 
/*     */           
/* 136 */           if (item != null) {
/* 137 */             if (pc.getInventory().checkAddItem(item, ((Integer)aTempData.get(4)).intValue()) == 0) {
/* 138 */               pc.getInventory().storeItem(item);
/*     */               
/* 140 */               give_text(pc, item);
/* 141 */               text00(pc);
/*     */             } else {
/* 143 */               World.get().getInventory(pc.getX(), pc.getY(), 
/* 144 */                   pc.getMapId()).storeItem(item);
/*     */               
/* 146 */               give_text(pc, item);
/* 147 */               text00(pc);
/*     */             } 
/* 149 */             pc.sendPackets((ServerBasePacket)new S_SystemMessage(String.format((String)aTempData.get(5), new Object[] { item.getLogName() })));
/* 150 */             if (((Integer)aTempData.get(6)).intValue() == 1) {
///* 151 */               World.get().broadcastPacketToAll((ServerBasePacket)new S_SystemMessage(String.format((String)aTempData.get(7), new Object[] { pc.getName(), item.getLogName() })));
/*跑馬燈*/   			World.get().broadcastPacketToAll(new S_PacketBoxGree1(String.format((String)aTempData.get(7), new Object[] { pc.getName(), item.getLogName() })));
/*     */             }
/*     */             
/* 154 */             pc.setSkillEffect(123456, 3000);
/*     */           }
           			}
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     return false;
/*     */   }
/*     */   public static void save0(L1PcInstance pc) {
/* 163 */     if (pc.getcomtext0() > 0 && pc.getcomtext1() == 0) {
/* 164 */       pc.setcomtext1(pc.getcomtext0());
/* 165 */       pc.setcomtextc1(pc.getcomtextc0());
/*     */     }
/* 167 */     else if (pc.getcomtext0() > 0 && pc.getcomtext2() == 0) {
/* 168 */       pc.setcomtext2(pc.getcomtext0());
/* 169 */       pc.setcomtextc2(pc.getcomtextc0());
/*     */     }
/* 171 */     else if (pc.getcomtext0() > 0 && pc.getcomtext3() == 0) {
/* 172 */       pc.setcomtext3(pc.getcomtext0());
/* 173 */       pc.setcomtextc3(pc.getcomtextc0());
/*     */     }
/* 175 */     else if (pc.getcomtext0() > 0 && pc.getcomtext4() == 0) {
/* 176 */       pc.setcomtext4(pc.getcomtext0());
/* 177 */       pc.setcomtextc4(pc.getcomtextc0());
/*     */     }
/* 179 */     else if (pc.getcomtext0() > 0 && pc.getcomtext5() == 0) {
/* 180 */       pc.setcomtext5(pc.getcomtext0());
/* 181 */       pc.setcomtextc5(pc.getcomtextc0());
/*     */     }
/* 183 */     else if (pc.getcomtext0() > 0 && pc.getcomtext6() == 0) {
/* 184 */       pc.setcomtext6(pc.getcomtext0());
/* 185 */       pc.setcomtextc6(pc.getcomtextc0());
/*     */     }
/* 187 */     else if (pc.getcomtext0() > 0 && pc.getcomtext7() == 0) {
/* 188 */       pc.setcomtext7(pc.getcomtext0());
/* 189 */       pc.setcomtextc7(pc.getcomtextc0());
/*     */     }
/* 191 */     else if (pc.getcomtext0() > 0 && pc.getcomtext8() == 0) {
/* 192 */       pc.setcomtext8(pc.getcomtext0());
/* 193 */       pc.setcomtextc8(pc.getcomtextc0());
/*     */     }
/* 195 */     else if (pc.getcomtext0() > 0 && pc.getcomtext9() == 0) {
/* 196 */       pc.setcomtext9(pc.getcomtext0());
/* 197 */       pc.setcomtextc9(pc.getcomtextc0());
/*     */     }
/* 199 */     else if (pc.getcomtext0() > 0 && pc.getcomtext10() == 0) {
/* 200 */       pc.setcomtext10(pc.getcomtext0());
/* 201 */       pc.setcomtextc10(pc.getcomtextc0());
/*     */     }
/* 203 */     else if (pc.getcomtext0() > 0 && pc.getcomtext11() == 0) {
/* 204 */       pc.setcomtext11(pc.getcomtext0());
/* 205 */       pc.setcomtextc11(pc.getcomtextc0());
/*     */     }
/* 207 */     else if (pc.getcomtext0() > 0 && pc.getcomtext12() == 0) {
/* 208 */       pc.setcomtext12(pc.getcomtext0());
/* 209 */       pc.setcomtextc12(pc.getcomtextc0());
/*     */     }
/* 211 */     else if (pc.getcomtext0() > 0 && pc.getcomtext13() == 0) {
/* 212 */       pc.setcomtext13(pc.getcomtext0());
/* 213 */       pc.setcomtextc13(pc.getcomtextc0());
/*     */     }
/* 215 */     else if (pc.getcomtext0() > 0 && pc.getcomtext14() == 0) {
/* 216 */       pc.setcomtext14(pc.getcomtext0());
/* 217 */       pc.setcomtextc14(pc.getcomtextc0());
/*     */     }
/* 219 */     else if (pc.getcomtext0() > 0 && pc.getcomtext15() == 0) {
/* 220 */       pc.setcomtext15(pc.getcomtext0());
/* 221 */       pc.setcomtextc15(pc.getcomtextc0());
/*     */     }
/* 223 */     else if (pc.getcomtext0() > 0 && pc.getcomtext16() == 0) {
/* 224 */       pc.setcomtext16(pc.getcomtext0());
/* 225 */       pc.setcomtextc16(pc.getcomtextc0());
/*     */     }
/* 227 */     else if (pc.getcomtext0() > 0 && pc.getcomtext17() == 0) {
/* 228 */       pc.setcomtext17(pc.getcomtext0());
/* 229 */       pc.setcomtextc17(pc.getcomtextc0());
/*     */     }
/* 231 */     else if (pc.getcomtext0() > 0 && pc.getcomtext18() == 0) {
/* 232 */       pc.setcomtext18(pc.getcomtext0());
/* 233 */       pc.setcomtextc18(pc.getcomtextc0());
/*     */     }
/* 235 */     else if (pc.getcomtext0() > 0 && pc.getcomtext19() == 0) {
/* 236 */       pc.setcomtext19(pc.getcomtext0());
/* 237 */       pc.setcomtextc19(pc.getcomtextc0());
/*     */     }
/* 239 */     else if (pc.getcomtext0() > 0 && pc.getcomtext20() == 0) {
/* 240 */       pc.setcomtext20(pc.getcomtext0());
/* 241 */       pc.setcomtextc20(pc.getcomtextc0());
/*     */     } 
/*     */   }
/*     */   public static void give_text(L1PcInstance pc, L1ItemInstance item) {
/* 245 */     if (pc.getcomtext0() > 0 && pc.getcomtext1() == item.getItemId()) {
/* 246 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc1()));
/*     */     
/*     */     }
/* 249 */     else if (pc.getcomtext2() == item.getItemId()) {
/* 250 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc2()));
/*     */     
/*     */     }
/* 253 */     else if (pc.getcomtext3() == item.getItemId()) {
/* 254 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc3()));
/*     */     }
/* 256 */     else if (pc.getcomtext4() == item.getItemId()) {
/* 257 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc4()));
/*     */     }
/* 259 */     else if (pc.getcomtext5() == item.getItemId()) {
/* 260 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc5()));
/*     */     }
/* 262 */     else if (pc.getcomtext6() == item.getItemId()) {
/* 263 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc6()));
/*     */     }
/* 265 */     else if (pc.getcomtext7() == item.getItemId()) {
/* 266 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc7()));
/*     */     }
/* 268 */     else if (pc.getcomtext8() == item.getItemId()) {
/* 269 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc8()));
/*     */     }
/* 271 */     else if (pc.getcomtext9() == item.getItemId()) {
/* 272 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc9()));
/*     */     }
/* 274 */     else if (pc.getcomtext10() == item.getItemId()) {
/* 275 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc10()));
/*     */     }
/* 277 */     else if (pc.getcomtext11() == item.getItemId()) {
/* 278 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc11()));
/*     */     }
/* 280 */     else if (pc.getcomtext12() == item.getItemId()) {
/* 281 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc12()));
/*     */     }
/* 283 */     else if (pc.getcomtext13() == item.getItemId()) {
/* 284 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc13()));
/*     */     }
/* 286 */     else if (pc.getcomtext14() == item.getItemId()) {
/* 287 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc14()));
/*     */     }
/* 289 */     else if (pc.getcomtext15() == item.getItemId()) {
/* 290 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc15()));
/*     */     }
/* 292 */     else if (pc.getcomtext16() == item.getItemId()) {
/* 293 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc16()));
/*     */     }
/* 295 */     else if (pc.getcomtext17() == item.getItemId()) {
/* 296 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc17()));
/*     */     }
/* 298 */     else if (pc.getcomtext18() == item.getItemId()) {
/* 299 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc18()));
/*     */     }
/* 301 */     else if (pc.getcomtext19() == item.getItemId()) {
/* 302 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc19()));
/*     */     }
/* 304 */     else if (pc.getcomtext20() == item.getItemId()) {
/* 305 */       pc.sendPackets((ServerBasePacket)new S_NPCTalkReturn(pc.getId(), pc.getcomtextc20()));
/*     */     } 
/*     */   }
/*     */   public static void text00(L1PcInstance pc) {
/* 309 */     pc.setcomtext0(0);
/* 310 */     pc.setcomtextc0(null);
/* 311 */     pc.setcomtext1(0);
/* 312 */     pc.setcomtextc1(null);
/* 313 */     pc.setcomtext2(0);
/* 314 */     pc.setcomtextc2(null);
/* 315 */     pc.setcomtext3(0);
/* 316 */     pc.setcomtextc3(null);
/* 317 */     pc.setcomtext4(0);
/* 318 */     pc.setcomtextc4(null);
/* 319 */     pc.setcomtext5(0);
/* 320 */     pc.setcomtextc5(null);
/* 321 */     pc.setcomtext6(0);
/* 322 */     pc.setcomtextc6(null);
/* 323 */     pc.setcomtext7(0);
/* 324 */     pc.setcomtextc7(null);
/* 325 */     pc.setcomtext8(0);
/* 326 */     pc.setcomtextc8(null);
/* 327 */     pc.setcomtext9(0);
/* 328 */     pc.setcomtextc9(null);
/* 329 */     pc.setcomtext10(0);
/* 330 */     pc.setcomtextc10(null);
/* 331 */     pc.setcomtext11(0);
/* 332 */     pc.setcomtextc11(null);
/* 333 */     pc.setcomtext12(0);
/* 334 */     pc.setcomtextc12(null);
/* 335 */     pc.setcomtext13(0);
/* 336 */     pc.setcomtextc13(null);
/* 337 */     pc.setcomtext14(0);
/* 338 */     pc.setcomtextc14(null);
/* 339 */     pc.setcomtext15(0);
/* 340 */     pc.setcomtextc15(null);
/* 341 */     pc.setcomtext16(0);
/* 342 */     pc.setcomtextc16(null);
/* 343 */     pc.setcomtext17(0);
/* 344 */     pc.setcomtextc17(null);
/* 345 */     pc.setcomtext18(0);
/* 346 */     pc.setcomtextc18(null);
/* 347 */     pc.setcomtext19(0);
/* 348 */     pc.setcomtextc19(null);
/* 349 */     pc.setcomtext20(0);
/* 350 */     pc.setcomtextc20(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void getData() {
/* 355 */     Connection cn = null;
/* 356 */     Statement ps = null;
/* 357 */     ResultSet rset = null;
/*     */ 
/*     */     
/* 360 */     String sTemp = null;
/*     */     try {
/* 362 */       cn = DatabaseFactory.get().getConnection();
/* 363 */       ps = cn.createStatement();
/* 364 */       rset = ps.executeQuery("SELECT * FROM w_npc跑圖對話text");
/*     */       
/* 366 */       while (rset.next()) {
/*     */         
/* 368 */         ArrayList<Object> aReturn = new ArrayList();
/*     */         
/* 370 */         aReturn.add(0, new Integer(rset.getInt("npcid")));
/* 371 */         sTemp = rset.getString("action");
/* 372 */         aReturn.add(1, sTemp);
/* 373 */         if (rset.getString("對話檔名") != null && 
/* 374 */           !rset.getString("對話檔名").equals("") && 
/* 375 */           !rset.getString("對話檔名").equals("0")) {
/* 376 */           aReturn.add(2, getArray(rset.getString("對話檔名"), ",", 2));
/*     */         } else {
/* 378 */           aReturn.add(2, null);
/*     */         } 
/* 385 */         if (rset.getString("抽道具編號") != null && !rset.getString("抽道具編號").equals("") && !rset.getString("抽道具編號").equals("0")) {
/* 386 */           aReturn.add(3, getArray(rset.getString("抽道具編號"), ",", 1));
/*     */         } else {
/* 388 */           aReturn.add(3, null);
/*     */         } 
/* 390 */         aReturn.add(4, new Integer(rset.getInt("抽中數量")));
/* 391 */         aReturn.add(5, rset.getString("顯示文字"));
/* 392 */         aReturn.add(6, new Integer(rset.getInt("是否廣播")));
/* 393 */         aReturn.add(7, rset.getString("廣播文字"));
/*     */         
/* 395 */         aReturn.add(8, new Integer(rset.getInt("確認道具")));
/* 396 */         aReturn.add(9, new Integer(rset.getInt("確認道具數量")));
/* 397 */         aReturn.add(10, new Integer(rset.getInt("跑圖延遲毫秒")));
if (rset.getString("機率") != null && !rset.getString("機率").equals("") && !rset.getString("機率").equals("0")) {
           aReturn.add(11, getArray(rset.getString("機率"), ",", 1));
         } else {
           aReturn.add(11, null);
         }
/*     */ 
/*     */         
/* 401 */         _array.add(aReturn);
/*     */       } 
/* 403 */     } catch (SQLException e) {
/* 404 */       _log.error(e.getLocalizedMessage(), e);
/*     */     } finally {
/* 406 */       SQLUtil.close(rset);
/* 407 */       SQLUtil.close(ps);
/* 408 */       SQLUtil.close(cn);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object getArray(String s, String sToken, int iType) {
/* 413 */     StringTokenizer st = new StringTokenizer(s, sToken);
/* 414 */     int iSize = st.countTokens();
/* 415 */     String sTemp = null;
/*     */     
/* 417 */     if (iType == 1) {
/* 418 */       int[] iReturn = new int[iSize];
/* 419 */       for (int i = 0; i < iSize; i++) {
/* 420 */         sTemp = st.nextToken();
/* 421 */         iReturn[i] = Integer.parseInt(sTemp);
/*     */       } 
/* 423 */       return iReturn;
/*     */     } 
/*     */     
/* 426 */     if (iType == 2) {
/* 427 */       String[] sReturn = new String[iSize];
/* 428 */       for (int i = 0; i < iSize; i++) {
/* 429 */         sTemp = st.nextToken();
/* 430 */         sReturn[i] = sTemp;
/*     */       } 
/* 432 */       return sReturn;
/*     */     } 
/*     */     
/* 435 */     if (iType == 3) {
/* 436 */       String sReturn = null;
/* 437 */       for (int i = 0; i < iSize; i++) {
/* 438 */         sTemp = st.nextToken();
/* 439 */         sReturn = sTemp;
/*     */       } 
/* 441 */       return sReturn;
/*     */     } 
/* 443 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ArrayList<ArrayList<Object>> getSetList() {
/* 448 */     return _array;
/*     */   }
/*     */ }