package com.example.nudelvisualization.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;




public class Visualization{
	private VerticalPanel visualizationPanel = new VerticalPanel();
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private FlexTable visualizeTable = new FlexTable();
	private String[] areaIDs = null;
	private String[] itemIDs = null;
	private String[] years = null;
	private String[] dataSeries = null;
	
	public Visualization(Configuration config){
		int numberOfAreas = config.getSelectedAreaList().size();
		int numberOfItems = config.getSelectedItemsList().size();
		int numberOfYears = config.getSelectedYearsList().size();
		int numberOfDataSeries = config.getSelectedDataSeriesList().size();
		areaIDs = new String[numberOfAreas];
		itemIDs = new String[numberOfItems];
		years = new String[numberOfYears];
		dataSeries = new String[numberOfDataSeries];
		
		for (int i = 0; i < numberOfAreas; i++) {
			areaIDs[i] = config.getSelectedAreaList().get(i);
		}
		for (int i = 0; i < numberOfItems; i++) {
			itemIDs[i] = config.getSelectedItemsList().get(i);
		}
		for (int i = 0; i < numberOfYears; i++) {
			years[i] = config.getSelectedYearsList().get(i);
		}
		for (int i = 0; i < numberOfDataSeries; i++) {
			dataSeries[i] = config.getSelectedDataSeriesList().get(i);
		}
	}
	
	public void draw() {
		dataAccessSocket.getSelectedRows(areaIDs, itemIDs, years, dataSeries, new CallbackHandler());
	 }

private class CallbackHandler implements AsyncCallback<String[][]>{
	
	
	public void onFailure(Throwable caught) {
		System.out.println("Blah");
	}

	public void onSuccess(String[][] result) {
		
		for (int i = 0; i < result.length; i++) {
			if (result[i][1] != null) {
				for (int j = 0; j < result[i].length; j++) {
					visualizeTable.setText(i + 1, j, result[i][j]);
					}
				}
			}
		visualizeTable.setStyleName("tableVisualization");
		visualizeTable.getRowFormatter().addStyleName(1, "headOfTable");
		visualizationPanel.add(visualizeTable);
		visualizationPanel.setStyleName("visualizationPanel");
		RootPanel.get("visualizationContainer").add(visualizationPanel);
		}
	}
}