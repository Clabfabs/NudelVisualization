package com.example.nudelvisualization.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side for the RPC service.
 */
@RemoteServiceRelativePath("accessDatabase")
public interface AccessDatabase extends RemoteService {
  String[][] getArea();
  String[][] getItem();
  String[][] getSelectedRows(String[] areaIDs, String[] itemIDs, String[] years, String[] dataSeries);
}
