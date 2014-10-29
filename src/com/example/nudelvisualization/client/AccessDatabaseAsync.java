package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AccessDatabaseAsync {
  void getMetaData(String input, AsyncCallback<ArrayList<String>> callback)
      throws IllegalArgumentException;
}
