package com.example.nudelvisualization.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side for the RPC service.
 */
@RemoteServiceRelativePath("accessDatabase")
public interface AccessDatabase extends RemoteService {
	String[][] getArea();
	String[][] getItem();
	String[][] getYears();
	String[][] getSelectedRows(Configuration config);
	HashMap<String, String[][]> getDataForIntensityMap(Configuration config);
	HashMap<String, String[][]> getDataForLineChart(Configuration config);
}
