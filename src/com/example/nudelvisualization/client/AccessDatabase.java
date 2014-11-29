package com.example.nudelvisualization.client;

import java.util.HashMap;
import java.util.List;

import com.example.nudelvisualization.shared.TripleHashMap;
import com.google.gwt.rpc.server.WebModeClientOracle.Triple;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side for the RPC service.
 */
@RemoteServiceRelativePath("accessDatabase")
public interface AccessDatabase extends RemoteService {
	HashMap<String, String[][]> getInitialData();
	HashMap<String, String[][]> getTableVisualizationData(Configuration config);
	HashMap<String, String[][]> getDataForIntensityMap(Configuration config);
	HashMap<String, String[][]> getDataForLineChart(Configuration config);
	TripleHashMap getDataForColumnChart(Configuration config);
}
