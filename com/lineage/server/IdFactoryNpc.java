package com.lineage.server;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IdFactoryNpc
{
  private static final Log _log = LogFactory.getLog(IdFactoryNpc.class);
  private static IdFactoryNpc _instance;
  private Object _monitor;
  private AtomicInteger _nextId;

  public static IdFactoryNpc get()
  {
    if (_instance == null) {
      _instance = new IdFactoryNpc();
    }
    return _instance;
  }

  public IdFactoryNpc() {
    try {
      _monitor = new Object();
      _nextId = new AtomicInteger(2000000000);
    }
    catch (Exception e) {
      _log.error(e.getLocalizedMessage(), e);
    }
  }

  public int nextId()
  {
    synchronized (_monitor) {
      return _nextId.getAndIncrement();
    }
  }

  public int maxId()
  {
    synchronized (_monitor) {
      return _nextId.get();
    }
  }
}
