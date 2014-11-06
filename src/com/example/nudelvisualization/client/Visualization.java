package com.example.nudelvisualization.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;



public class Visualization implements EntryPoint {

	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private FlexTable sampleTable = new FlexTable();
	private Configuration config = new Configuration();

	public Visualization(Configuration config) {
		this.config = config;
	}

	public void onModuleLoad(){
		dataAccessSocket.getSelectedRows(config, new AsyncCallback<String[][]>() {
			public void onFailure(Throwable caught) {
				System.out.println("Blah");
			}

			public void onSuccess(String[][] result) {
				for (int i = 0; i < result.length; i++) {
					// Assumption: data is complete and a missing value in
					// column 1 means there is no more data to fetch
					if (result[i][1] != null) {
						for (int j = 0; j < result[i].length; j++) {
							sampleTable.setText(i + 1, j, result[i][j]);
							}
						}
					}
				}
		});
	}
}