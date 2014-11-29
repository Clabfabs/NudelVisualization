package com.example.nudelvisualization.client;

import java.util.HashMap;
import java.util.List;

import com.example.nudelvisualization.shared.TripleHashMap;
import com.google.gwt.rpc.server.WebModeClientOracle.Triple;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>AccessDatabase</code>.
 */
public interface AccessDatabaseAsync {
	void getInitialData(AsyncCallback<HashMap<String, String[][]>> callback);
	void getTableVisualizationData(Configuration config, AsyncCallback<HashMap<String, String[][]>> asyncCallback);
	void getDataForIntensityMap(Configuration config, AsyncCallback<HashMap<String, String[][]>> callback);
	void getDataForLineChart(Configuration config, AsyncCallback<HashMap<String, String[][]>> asyncCallback);
	void getDataForColumnChart(Configuration config,
			AsyncCallback<TripleHashMap> asyncCallback);
}