package com.example.nudelvisualization.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>AccessDatabase</code>.
 */
public interface AccessDatabaseAsync {
  void getMetaData(String input, AsyncCallback<String[]> callback);
  void getSomeRows(int numberOfRows, AsyncCallback<String[][]> callback);
  void getArea(AsyncCallback<String[][]> callback);
  void getItem(AsyncCallback<String[][]> callback);
}
