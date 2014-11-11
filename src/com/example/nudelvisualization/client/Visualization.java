package com.example.nudelvisualization.client;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.view.client.ListDataProvider;



public class Visualization{
	private VerticalPanel visualizationPanel = new VerticalPanel();
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private CellTable visualizeTable = new CellTable();
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
			System.out.println("Communication with server failed");
		}

		public void onSuccess(String[][] result) {

			// Get the rows as List
		    int nrows = result.length;
		    int ncols = result[0].length;
		    ArrayList rowsL = new ArrayList(nrows);
		    //List rowsL = new ArrayList(nrows);
		    for (int irow = 1; irow < nrows; irow++) {		    	
		        List<String> rowL = Arrays.asList(result[irow]);
		        rowsL.add(rowL);
		    }
		    
		 // Create table columns
		    int p = 0;
		    for (int icol = 0; icol < ncols; icol++) {
		    	//extracts columns we need 
		    	if(icol == 1 || icol == 3 || icol == 5 || icol == 7 || icol == 8 || icol == 9 || icol == 10 ){
		    		p = icol;
		    		//exchanges columns value and unit
		    		if(icol == 9){
		    			p = 10;
		    		}
		    		else if(icol == 10){
		    			p = 9;
		    		//exchanges columns ElementName and Domain
		    		}
		    		else if(icol == 1){
		    			p = 5;
		    		}
		    		else if(icol == 5){
		    			p = 1;
		    		}
		    		//change TextHeader
		    		result[0][5] = "Element Name";
		    		result[0][7] = "Item Name";
		    		result[0][3] = "Area Name";
		    		visualizeTable.addColumn(new IndexedColumn(p), new TextHeader(result[0][p]));
		    	}
		    }
		    final ListDataProvider<ArrayList<String>> dataProvider = new ListDataProvider<ArrayList<String>>(rowsL);
		    dataProvider.addDataDisplay(visualizeTable);
		    
		    visualizationPanel.add(visualizeTable);
		    RootPanel.get().clear();
			RootPanel.get().add(visualizationPanel);
		}
	}
	
	private class IndexedColumn extends Column<List<String>, String> {
	    private final int index;
	    public IndexedColumn(int index) {
	        super(new TextCell());
	        this.index = index;
	    }
	    @Override
	    public String getValue(List<String> object) {
	        return object.get(this.index);
	    }
	}
}