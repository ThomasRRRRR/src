package com.lineage.server.model;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_PacketBoxParty;
import com.lineage.config.ConfigOther;
import org.apache.commons.logging.LogFactory;
import com.lineage.server.model.Instance.L1PcInstance;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;

public class L1Party
{
  private static final Log _log = LogFactory.getLog(L1Party.class);

  public static final int MAXPT = ConfigOther.partycount;

  private final ConcurrentHashMap<Integer, L1PcInstance> _membersList = 
			new ConcurrentHashMap<Integer, L1PcInstance>();
  
  private L1PcInstance _leader = null;

  private boolean _isLeader = false;

	/**
	 * 加入新的隊伍成員
	 * @param pc
	 */
	public void addMember(final L1PcInstance pc) {
		try {
			int key = 1;// 隊伍成員編號
			if (pc == null) {
				throw new NullPointerException();
			}
			
			if (this._membersList.size() == MAXPT || this._membersList.contains(pc)) {
				return;
			}
			
			boolean hpUpdate = false;
			if (this._membersList.isEmpty()) {
				// 隊伍初始化成立 設置隊長
				this.setLeader(pc);
				
			} else {
				hpUpdate = true;
			}

			while (this._membersList.get(key) != null) {
				key++;
			}
			this._membersList.put(key, pc);
			
			pc.setParty(this);

			for (L1PcInstance member : this._membersList.values()) {
				if (!member.equals(this._leader)) {
					member.sendPackets(new S_PacketBoxParty(member, this));
					
				} else {
					if (_isLeader) {
						member.sendPackets(new S_PacketBoxParty(pc));
					}
				}
				// 隊伍UI更新
				member.sendPackets(new S_PacketBoxParty(this, member));
			}
			
			if (!_isLeader) {
				_isLeader = true;
			}
			if (hpUpdate) {
				this.createMiniHp(pc);
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 移出隊伍成員
	 * @param pc
	 */
	private void removeMember(final L1PcInstance pc) {
		try {
			int removeKey = -1;
			for (final int key : this._membersList.keySet()) {
				L1PcInstance tgpc = this._membersList.get(key);
				if (pc.equals(tgpc)) {
					removeKey = key;
				}
			}
			if (removeKey != -1) {
				this._membersList.remove(removeKey);
				
				pc.setParty(null);
				if (!this._membersList.isEmpty()) {
					this.deleteMiniHp(pc);
				}
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
    
	/**
	 * 該隊伍目前人數(同地圖)
	 * @return
	 */
	public int partyUserInMap(final short mapid) {
		int i = 0;
		if (this._membersList.isEmpty()) {
			return 0;
		}
		if (this._membersList.size() <= 0) {
			return 0;
		}
		
		for (final L1PcInstance tgpc : this._membersList.values()) {
			short tgpcMapid = tgpc.getMapId();
			if (tgpcMapid == mapid) {
				i += 1;
			}
		}

		return i;
	}
    
	public boolean isVacancy()
	  {
	    return _membersList.size() < MAXPT;
	  }

	  public int getVacancy()
	  {
	    return MAXPT - _membersList.size();
	  }
    
	  /**
		 * 是否為隊員
		 * @param pc
		 * @return
		 */
		public boolean isMember(final L1PcInstance pc) {
			for (final L1PcInstance tgpc : this._membersList.values()) {
				if (pc.equals(tgpc)) {
					return true;
				}
			}
			return false;
		}
    
		public void setLeader(L1PcInstance pc)
		  {
		    _leader = pc;
		  }

		  public L1PcInstance getLeader()
		  {
		    return _leader;
		  }

		  public int getLeaderID()
		  {
		    return _leader.getId();
		  }

		  public boolean isLeader(L1PcInstance pc)
		  {
		    return pc.getId() == _leader.getId();
		  }
    
		  /**
			 * 全隊員名稱
			 * @return
			 */
			public String getMembersNameList() {
				StringBuilder stringBuilder = new StringBuilder();
				
				if (this._membersList.isEmpty()) {
					return null;
				}
				if (this._membersList.size() <= 0) {
					return null;
				}
				for (final L1PcInstance pc : this._membersList.values()) {
					stringBuilder.append(pc.getName() + " ");
				}
				return stringBuilder.toString();
			}
    
			/**
			 * 隊員加入時HP顯示的增加
			 * @param pc
			 */
			private void createMiniHp(final L1PcInstance pc) {
				if (this._membersList.isEmpty()) {
					return;
				}
				if (this._membersList.size() <= 0) {
					return;
				}
				for (final L1PcInstance member : this._membersList.values()) {
					member.sendPackets(new S_HPMeter(pc.getId(), 100 * pc.getCurrentHp() / pc.getMaxHp()));
					pc.sendPackets(new S_HPMeter(member.getId(), 100 * member.getCurrentHp() / member.getMaxHp()));
				}
			}
    
			/**
			 * 隊員離開時HP顯示的移除
			 * @param pc
			 */
			private void deleteMiniHp(final L1PcInstance pc) {
				if (this._membersList.isEmpty()) {
					return;
				}
				if (this._membersList.size() <= 0) {
					return;
				}
				for (final L1PcInstance member : this._membersList.values()) {
					pc.sendPackets(new S_HPMeter(member.getId(), 0xff));
					member.sendPackets(new S_HPMeter(pc.getId(), 0xff));
				}
			}
    
			/**
			 * 隊員血條更新
			 * @param pc
			 */
			public void updateMiniHP(final L1PcInstance pc) {
				if (this._membersList.isEmpty()) {
					return;
				}
				if (this._membersList.size() <= 0) {
					return;
				}
				for (final L1PcInstance member : this._membersList.values()) { // 隊員血條更新
					member.sendPackets(new S_HPMeter(pc.getId(), 100 * pc.getCurrentHp() / pc.getMaxHp()));
				}
			}

			/**
			 * 解散隊伍
			 */
			public void breakup() {
				if (this._membersList.isEmpty()) {
					return;
				}
				if (this._membersList.size() <= 0) {
					return;
				}
				for (final L1PcInstance member : this._membersList.values()) {
					this.removeMember(member);
					// 您解散您的隊伍了!!
					member.sendPackets(new S_ServerMessage(418));
				}
			}
    
			public void leaveMember(final L1PcInstance pc) {
				if (this.isLeader(pc)) {
					// パーティーリーダーの場合
					this.breakup();
					
				} else {
					// パーティーリーダーでない場合
					if (this.getNumOfMembers() == 2) {
						// 隊伍成員剩餘2人
						this.removeMember(pc);
						final L1PcInstance leader = this.getLeader();
						this.removeMember(leader);

						this.sendLeftMessage(pc, pc);
						this.sendLeftMessage(leader, pc);
						
						// 您解散您的隊伍了!!
						leader.sendPackets(new S_ServerMessage(418));
						pc.sendPackets(new S_ServerMessage(418));
						
					} else {
						// 隊伍成員餘2人以上
						this.removeMember(pc);
						for (final L1PcInstance member : this._membersList.values()) {
							this.sendLeftMessage(member, pc);
						}
						this.sendLeftMessage(pc, pc);
					}
				}
			}
    
			/**
			 * 驅逐隊員
			 * @param pc
			 */
			public void kickMember(final L1PcInstance pc) {
				if (this.getNumOfMembers() == 2) {
					// 隊伍成員剩餘2人
					this.removeMember(pc);
					final L1PcInstance leader = this.getLeader();
					this.removeMember(leader);

					this.sendLeftMessage(pc, pc);
					this.sendLeftMessage(leader, pc);
					
					// 您解散您的隊伍了!!
					leader.sendPackets(new S_ServerMessage(418));
					pc.sendPackets(new S_ServerMessage(418));
					
				} else {
					// 隊伍成員餘2人以上
					this.removeMember(pc);
					for (final L1PcInstance member : this._membersList.values()) {
						this.sendLeftMessage(member, pc);
					}
					this.sendLeftMessage(pc, pc);
				}
				// 您從隊伍中被驅逐了。
				pc.sendPackets(new S_ServerMessage(419));
			}
    
			public ConcurrentHashMap<Integer, L1PcInstance> partyUsers()
			  {
			    return _membersList;
			  }

			  public int getNumOfMembers()
			  {
			    return _membersList.size();
			  }

			  private void sendLeftMessage(L1PcInstance sendTo, L1PcInstance left)
			  {
			    try
			    {
			      sendTo.sendPackets(new S_ServerMessage(420, left.getName()));
			    }
			    catch (Exception e) {
			      _log.error(e.getLocalizedMessage(), e);
			    }
			  }

			  public final int checkMentor(L1Apprentice apprentice) {
			    int checkType = 0;
			    for (L1PcInstance member : _membersList.values()) {
			      if (apprentice.getMaster().getId() == member.getId())
			        checkType += 4;
			      else if (apprentice.getTotalList().contains(member)) {
			        checkType++;
			      }
			    }
			    return checkType;
			  }
    
			  /**
				 * 傳遞新隊長訊息
				 */
				public void msgToAll() {
					for (final L1PcInstance member : this._membersList.values()) {
						if (!member.equals(this._leader)) {
							// 傳遞新隊長訊息
							member.sendPackets(new S_PacketBoxParty(this._leader.getId(), this._leader.getName()));
						}
					}
				}

				/**
				 * 任意一名隊伍成員
				 * @return
				 */
				public L1PcInstance partyUser() {
					L1PcInstance user = null;
					for (final L1PcInstance pc : _membersList.values()) {
						if (user == null && !_leader.equals(pc)) {
							user = pc;
						}
					}
					return user;
				}

			  public List<String> getPartyMembers() {
			    List<String> partyMembers = new CopyOnWriteArrayList<String>();
			    for (L1PcInstance pc : _membersList.values()) {
			      if (!_leader.equals(pc)) {
			        partyMembers.add(pc.getName());
			      }
			    }
			    return partyMembers;
			  }
			}
