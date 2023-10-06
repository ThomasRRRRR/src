package com.lineage.server.datatables.storage;

import java.util.Map;

public interface EzpayStorage2 {
  Map<Integer, int[]> ezpayInfo(String paramString);
  
  boolean update(String paramString1, int paramInt, String paramString2, String paramString3);
}


/* Location:              D:\Desk\381server.jar!\com\lineage\server\datatables\storage\EzpayStorage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */