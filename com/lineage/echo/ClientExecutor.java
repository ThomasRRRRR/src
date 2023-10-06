package com.lineage.echo;

import com.lineage.commons.system.IpAttackCheck;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.config.Config;
import com.lineage.config.ConfigIpCheck;
import com.lineage.echo.encryptions.Cipher;
import com.lineage.echo.encryptions.Encryption;
import com.lineage.list.OnlineUser;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1Account;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.StreamUtil;
import com.lineage.server.utils.SystemUtil;

import java.net.Socket;
import java.net.SocketException;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;

public class ClientExecutor extends OpcodesClient implements Runnable {
  private static final Log _log;
  
  private Socket _csocket;
  
  private L1Account _account = null;// 連線帳戶資料

  private L1PcInstance _activeChar = null;// 登入人物資料

  private StringBuilder _ip = null;// 連線IP資料

  private StringBuilder _mac = null;// MAC資料

  private int _kick = 0;

  private boolean _isStrat = true;

  private EncryptExecutor _encrypt;// 封包加密管理

  private DecryptExecutor _decrypt;// 封包解密管理

  private PacketHandlerExecutor _handler;// 資料處理者

  private Encryption _keys;

  private int _error = -1;// 錯誤次數

  private static final int M = 3; // 移動最大封包處理量

  private static final int O = 2;// 人物其他動作最大封包處理量
  
  private int _saveInventory;
  
  private int _savePc;
  
  public int _xorByte;
  
  public long _authdata;
  
  private PacketHc o;
  
  private PacketHc m;
  
  private byte _loginStatus;
  
  private static final Timer observerTimer = new Timer();
  
  private final IoSession _session;
  
  private final Cipher le;
  
  private final Lock synLock = new ReentrantLock(true);
  
  public static final String CLIENT_KEY = "CLIENT";
  
  private boolean _isfirst = true;
  
  ClientThreadObserver observer = new ClientThreadObserver(0);
  
  private long _lastSavedTime = System.currentTimeMillis();
  
  private long _lastSavedTime_inventory = System.currentTimeMillis();
  
  private PacketHandler packetHandler;
  
  private HcPacket hPacket;
  
  private HcPacket movePacket;
  
  static {
    _log = LogFactory.getLog(ClientExecutor.class);
  }
  
  public ClientExecutor(IoSession session, int key) {
    this._session = session;
    this.le = new Cipher(key);
  }
  
  public void start() {}
  
  public void run() {
    this.o = new PacketHc(this, O);
    GeneralThreadPool.get().schedule(this.o, 0L);
    
	// 加入人物自動保存時間軸
	set_savePc(Config.AUTOSAVE_INTERVAL);
	// 加入背包物品自動保存時間軸
	set_saveInventory(Config.AUTOSAVE_INTERVAL_INVENTORY);
    
    try {
		_encrypt.satrt();// 開始處理封包輸出
		_encrypt.outStart();// 把第一個封包送出去
		
		boolean isEcho = false;// 完成要求接收伺服器版本(防止惡意封包發送)
      while (this._isStrat) {
        byte[] decrypt = null;
        try {
          decrypt = readPacket();
        } catch (Exception e) {
          break;
        } 
        if (decrypt.length > 1440) {
          _log.warn("客戶端送出長度異常封包:" + this._ip.toString() + " 帳號:" + ((this._account != null) ? this._account.get_login() : "未登入"));
          LanSecurityManager.BANIPMAP.put(this._ip.toString(), Integer.valueOf(100));
          break;
        } 
        if (this._account != null) {
          if (!OnlineUser.get().isLan(this._account.get_login()))
            break; 
          if (!this._account.is_isLoad())
            break; 
        }
		/** 占領偵測 (斷現時間)*/
		if(LanSecurityManager.Loginer.get(_ip.toString()) == 3){
			_csocket.setSoTimeout(0);
			_log.info(new String()+_ip.toString());
			LanSecurityManager.Loginer.put(_ip.toString(), 4);
		}
        int opcode = decrypt[0] & 0xFF;
        if (this._activeChar == null) {
        	
        	/** 占領偵測 (斷現時間)*/
			if(LanSecurityManager.Loginer.get(_ip.toString()) == 1 && _account == null){
				_csocket.setSoTimeout(ConfigIpCheck.timeOutSocket * 1000);// 用戶端60秒無反應中斷
				_log.info(new String("UTF-8")+_ip.toString());
			}
        	
          if (opcode == C_OPCODE_CLIENTVERSION) {// 要求接收伺服器版本
        	  if (ConfigIpCheck.IPCHECK) {/**防導航儀CC*/
					_csocket.setSoTimeout(0);
				}
            isEcho = true;
          } else if (opcode == C_OPCODE_LOGINPACKET) {
            this.m = new PacketHc(this, M);
            GeneralThreadPool.get().schedule(this.m, 0L);
            set_savePc(Config.AUTOSAVE_INTERVAL);
            set_saveInventory(Config.AUTOSAVE_INTERVAL_INVENTORY);
          } 
          if (!isEcho)
            continue; 
          this._handler.handlePacket(decrypt);
          continue;
        } 
        if (!isEcho)
          continue; 
        if (this.m == null)
          continue; 
        switch (opcode) {
          case C_OPCODE_QUITGAME:// 要求離開遊戲
            this._isStrat = false;
            continue;
          case C_OPCODE_CHANGECHAR: // 要求切換角色
          case C_OPCODE_DROPITEM: // 要求丟棄物品(丟棄置地面)
          case C_OPCODE_DELETEINVENTORYITEM: // 要求刪除物品
            this._handler.handlePacket(decrypt);
            continue;
          case C_OPCODE_MOVECHAR: // 人物移動封包處理
            this.m.requestWork(decrypt);
            continue;
        } 
        // 其他封包處理
        this.o.requestWork(decrypt);
      } 
    } catch (Exception e) {
		//_log.error(e.getLocalizedMessage(), e);
		
	//} catch (Throwable e) {
		//_log.error(e.getLocalizedMessage(), e);*/
		
	} finally {
		if (ConfigIpCheck.IPCHECK) {
			IpAttackCheck.SOCKETLIST.remove(this);
		}
		// 移出人物自動保存時間軸
		set_savePc(-1);
		// 移出背包物品自動保存時間軸
		set_saveInventory(-1);

		// 關閉IO
		close();
	}
	return;
}
  
  /**
	 * 關閉連線線程
	 * @throws IOException
	 */
  public void close() {
    try {
		String mac = null;
		if (_mac != null) {
			mac = _mac.toString();
		}
		
		if (_csocket == null) {
			return;
		}
      
		if (_activeChar != null) {
			if (!_activeChar.IsDisconnect()) {
				Thread.sleep(15 * 1000);
			} else {
				_activeChar.setDisconnect(false);
			}
		}
      
      this._kick = 0;
      if (_account != null) {
			OnlineUser.get().remove(_account.get_login());
		}
		
		if (_activeChar != null) {
			quitGame();
		}
      String ipAddr = this._ip.toString();
      String account = null;
      if (_kick < 1) {
			if (_account != null) {
				account = _account.get_login();
			}
		}
      this._decrypt.stop();
      this._encrypt.stop();
      StreamUtil.close(this._csocket);
      
      if (ConfigIpCheck.ISONEIP) {
			LanSecurityManager.ONEIPMAP.remove(ipAddr);
		}
      
      	_handler = null;
		_mac = null;// MAC資料
		_ip = null;// 連線IP資料
		_activeChar = null;// 登入人物資料
		_account = null;// 連線帳戶資料

		_decrypt = null;
		_encrypt = null;
		_csocket = null;
		_keys = null;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("\n--------------------------------------------------");
      stringBuilder.append("\n       客戶端 離線: (");
      if (account != null)
        stringBuilder.append(String.valueOf(String.valueOf(account)) + " "); 
      if (mac != null)
        stringBuilder.append(" " + mac + " / "); 
      stringBuilder.append(String.valueOf(String.valueOf(ipAddr)) + ") 完成連線中斷!!");
      stringBuilder.append("\n--------------------------------------------------");
      _log.info(stringBuilder.toString());
      SystemUtil.printMemoryUsage(_log);
    } catch (Exception exception) {}
  }
  
  /**
	 * 傳回帳號暫存資料
	 * @return
	 */
  public L1Account getAccount() {
    return this._account;
  }
  
  public boolean isClosed() {
    if (this._session.isClosing())
      return true; 
    return false;
  }
  
  /**
	 * 設置登入帳號
	 * @param account
	 */
  public void setAccount(L1Account account) {
    this._account = account;
  }
  
  public void sendPacket(ServerBasePacket bp) {
    if (bp == null || this._session.isClosing())
      return; 
    try {
      this.synLock.lock();
      this._session.write(bp);
    } catch (Exception exception) {
    
    } finally {
      this.synLock.unlock();
    } 
  }
  
  /**
	 * 傳回登入帳號
	 * @return
	 */
  public String getAccountName() {
    if (this._account == null)
      return null; 
    return this._account.get_login();
  }
  
  public boolean isConnected() {
    return this._session.isConnected();
  }
  
  /**
	 * 傳回此帳號目前登入人物
	 * @return
	 */
  public L1PcInstance getActiveChar() {
    return this._activeChar;
  }
  
  public IoSession getsSession() {
    return this._session;
  }
  
  /**
	 * 設置此帳號目前登入人物
	 * @param pc
	 */
  public void setActiveChar(L1PcInstance pc) {
    this._activeChar = pc;
  }
  
  public StringBuilder getIp() {
    return this._ip;
  }
  
  /**
	 * 傳回IP位置
	 * @return
	 */
  public void setIp(StringBuilder ip) {
    this._ip = ip;
  }
  
  /**
	 * 傳回MAC位置
	 * @return
	 */
  public StringBuilder getMac() {
    return this._mac;
  }
  
  /**
	 * 設置MAC位置
	 * @param mac
	 * @return true:允許登入 false:禁止登入
	 */
  public boolean setMac(StringBuilder mac) {
    this._mac = mac;
    return true;
  }
  
  /**
	 * 傳回 Socket
	 * @return
	 */
  public Socket get_socket() {
    return this._csocket;
  }
  
  /**
	 * 中斷連線
	 * @return 
	 */
  public boolean kick1() {
    try {
      this._encrypt.encrypt((ServerBasePacket)new S_Disconnect());
    } catch (Exception exception) {}
    quitGame();
    this._kick = 1;
    this._isStrat = false;
    close();
    return true;
  }
  
  public void kick() {
    try {
      this._session.close(true);
    } catch (Exception exception) {}
  }
  
  /**
	 * 人物離開遊戲的處理
	 * @param pc
	 */
	public void quitGame() {
		try {
			if (_activeChar == null) {
				//System.out.println("登入角色為空");
				return;
			}
			synchronized (_activeChar) {
				QuitGame.quitGame(_activeChar);
				_activeChar = null;
			}

		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
	}
  
	/**
	 * 封包解密
	 * @return
	 * @throws Exception
	 */
	private byte[] readPacket() {
		try {
			byte[] data = null;
			data = _decrypt.decrypt();
			return data;

		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
		return null;
	}
  
  public void clientclose() {
    try {
      if (this._activeChar != null) {
        if (this._activeChar.is_bounce_attack() && 
          !this._activeChar.getMap().isSafetyZone(this._activeChar.getX(), this._activeChar.getY()))
          Thread.sleep(12000L); 
        quitGame();
        synchronized (this._activeChar) {
          this._activeChar.logout();
          setActiveChar(null);
        } 
      } 
    } catch (Exception exception) {
      try {
        OnlineUser.get().remove(this._account.get_login());
        stopObsever();
      } catch (Exception exception1) {}
    } finally {
      try {
        OnlineUser.get().remove(this._account.get_login());
        stopObsever();
      } catch (Exception exception) {}
    } 
  }
  
  public void stopObsever() {
    this.observer.cancel();
  }
  
  public EncryptExecutor out() {
    return this._encrypt;
  }
  
  public void PacketHandler(byte[] data) throws Exception {
    int opcode = data[0] & 0xFF;
    if (this._isfirst)
      if (opcode == 14) {
        this._isfirst = false;
      } else {
        this._session.close(true);
        return;
      }  
    if (opcode != 95)
      this.observer.packetReceived(); 
    if (this._activeChar == null) {
      this.packetHandler.handlePacket(data);
    } else if (opcode == 29 || opcode == 122 || opcode == 7 || opcode == 25 || opcode == 138) {
      this.movePacket.requestWork(data);
    } else {
      this.hPacket.requestWork(data);
    } 
    doAutoSave();
  }
  
  private void doAutoSave() throws Exception {
    if (this._activeChar == null)
      return; 
    try {
      if (Config.AUTOSAVE_INTERVAL * 1000L < System.currentTimeMillis() - this._lastSavedTime) {
        this._activeChar.save();
        this._lastSavedTime = System.currentTimeMillis();
      } 
      if (Config.AUTOSAVE_INTERVAL_INVENTORY * 1000L < System.currentTimeMillis() - this._lastSavedTime_inventory) {
        this._activeChar.saveInventory();
        this._lastSavedTime_inventory = System.currentTimeMillis();
      } 
    } catch (Exception e) {
      _log.info("Client autosave failure.");
      _log.error(e.getLocalizedMessage(), e);
      throw e;
    } 
  }
  
  public void set_keys(Encryption keys) {
    this._keys = keys;
  }
  
  public Encryption get_keys() {
    return this._keys;
  }
  
  public int get_error() {
    return this._error;
  }
  
  public void set_error(int error) {
    this._error = error;
    if (this._error >= 2)
      kick(); 
  }
  
  public void set_saveInventory(int saveInventory) {
    this._saveInventory = saveInventory;
  }
  
  public int get_saveInventory() {
    return this._saveInventory;
  }
  
  public void set_savePc(int savePc) {
    this._savePc = savePc;
  }
  
  public int get_savePc() {
    return this._savePc;
  }
  
  public void setLoginStatus(byte i) {
    this._loginStatus = i;
  }
  
  public byte getLoginStatus() {
    return this._loginStatus;
  }
  
  public void Initialization() {
    this.packetHandler = new PacketHandler(this._session);
    this.hPacket = new HcPacket();
    GeneralThreadPool.get().execute(this.hPacket);
    this.movePacket = new HcPacket();
    GeneralThreadPool.get().execute(this.movePacket);
  }
  
  public void RemoveSocket() {
    try {
      this._csocket.setSoTimeout(0);
    } catch (SocketException e) {
      e.printStackTrace();
    } 
  }
  
  public void SetSocket(int time) {
    try {
      this._csocket.setSoTimeout(time * 1000);
    } catch (SocketException e) {
      e.printStackTrace();
    } 
  }
  
  class HcPacket implements Runnable {
    private final Queue<byte[]> _queue = (Queue)new ConcurrentLinkedQueue<>();
    
    private PacketHandler _handler = new PacketHandler(ClientExecutor.this._session);
    
    public void requestWork(byte[] data) {
      this._queue.offer(data);
    }
    
    public void run() {
      while (ClientExecutor.this._session != null && !ClientExecutor.this._session.isClosing()) {
        byte[] data = this._queue.poll();
        if (data != null) {
          try {
            this._handler.handlePacket(data);
          } catch (Exception exception) {}
          continue;
        } 
        try {
          Thread.sleep(10L);
        } catch (Exception exception) {}
      } 
    }
  }
  
  class ClientThreadObserver extends TimerTask {
    private final int _disconnectTimeMillis;
    
    private int _checkct = 1;
    
    public ClientThreadObserver(int disconnectTimeMillis) {
      this._disconnectTimeMillis = disconnectTimeMillis;
    }
    
    public void start() {
      ClientExecutor.observerTimer.scheduleAtFixedRate(this, 0L, this._disconnectTimeMillis);
    }
    
    public void run() {
      try {
        if (ClientExecutor.this._session.isClosing()) {
          cancel();
          return;
        } 
        if (this._checkct > 0) {
          this._checkct = 0;
          return;
        } 
        if (ClientExecutor.this._activeChar == null || (
          ClientExecutor.this._activeChar != null && !ClientExecutor.this._activeChar.isPrivateShop())) {
          ClientExecutor.this.kick();
          ClientExecutor._log.info("太長時間沒動作(" + ClientExecutor.this._account + ")切斷路線.");
          cancel();
          return;
        } 
      } catch (Exception e) {
        ClientExecutor._log.error(e.getLocalizedMessage(), e);
        cancel();
      } 
    }
    
    public void packetReceived() {
      this._checkct++;
    }
  }
  
  public Cipher getCipher() {
    return this.le;
  }
}