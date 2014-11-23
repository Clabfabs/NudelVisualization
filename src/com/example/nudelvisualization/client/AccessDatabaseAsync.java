package com.example.nudelvisualization.client;

import java.util.HashMap;
import java.util.List;

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
	void getDataForLineChart(Configuration config,
			AsyncCallback<HashMap<String, String[][]>> asyncCallback);
	void getDataForColumnChart(Configuration config,
			AsyncCallback<HashMap<String, List<String[]>>> asyncCallback);
}