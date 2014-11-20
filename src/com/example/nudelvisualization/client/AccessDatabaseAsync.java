package com.example.nudelvisualization.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>AccessDatabase</code>.
 */
public interface AccessDatabaseAsync {
	void getArea(AsyncCallback<String[][]> callback);
	void getItem(AsyncCallback<String[][]> callback);
	void getYears(AsyncCallback<String[][]> callback);
	void getSelectedRows(Configuration config, AsyncCallback<String[][]> callback);
	void getDataForIntensityMap(Configuration config, AsyncCallback<HashMap<String, String[][]>> callback);
}