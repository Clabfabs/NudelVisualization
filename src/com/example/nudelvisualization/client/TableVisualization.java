package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;


// import com.example.nudelvisualization.client.IndexedColumn;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TableVisualization extends Visualization {

	private VerticalPanel visualizationPanel = new VerticalPanel();
	private CellTable<String[]> visualizeTable = new CellTable<>();
	private String[][] result = null;
	
	public TableVisualization(Configuration config) {
		super(config); // protected areaIDs, itemIDs, years & dataSeries now ready to be used
		
	}
	
	public List<String[]> getRowsAsList(String[][] result,  List<String[]> rowsL){
	    int nrows = result.length;

		  for (int irow = 0; irow < nrows; irow++) {
			    rowsL.add(result[irow]); 
		    }
		  return rowsL;
	}

	public void draw() {
		final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
		visualizeTable.setPageSize(50);
		visualizeTable.setWidth("100%", true);

		config.addTitles("ElementName");
		config.addTitles("AreaName");
		config.addTitles("ItemName");
		config.addTitles("Year");
		config.addTitles("Value");
		config.addTitles("Unit");
		dataAccessSocket.getTableVisualizationData(config, new AsyncCallback<HashMap<String, String[][]>>() {
			public void onFailure(Throwable caught) {
				System.out.println("Communication with server failed");
			}

			public void onSuccess(HashMap<String, String[][]> data) {
				result = data.get("Production");
				for (int i = 0; i < 30 && i < result.length; i++) {
					for (int j = 0; j < result[i].length; j++) {
						System.out.print(result[i][j] + "\t");
					}
					System.out.println("\n");
				}
				// Get the rows as List
			    int nrows = result.length;
			    List<String[]> rowsL = new ArrayList<>(nrows);
			    
			    rowsL = getRowsAsList(result, rowsL);
//			    for (int irow = 0; irow < nrows; irow++) {
//				    rowsL.add(result[irow]); 
//			    }

			    // Create table columns
			    TextColumn<String[]> elementColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[0];
			    	}
			    };
			    visualizeTable.addColumn(elementColumn, new TextHeader("Element Name"));
			    
			    TextColumn<String[]> areaColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[1];
			    	}
			    };
			    visualizeTable.addColumn(areaColumn, new TextHeader("Area Name"));
			    
			    TextColumn<String[]> itemColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[2];
			    	}
			    };
			    visualizeTable.addColumn(itemColumn, new TextHeader("Item Name"));
			    
			    TextColumn<String[]> yearColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[3];
			    	}
			    };
			    visualizeTable.addColumn(yearColumn, new TextHeader("Year"));
			    
			    TextColumn<String[]> valueColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[4];
			    	}
			    };
			    visualizeTable.addColumn(valueColumn, new TextHeader("Value"));
			    
			    TextColumn<String[]> unitColumn = new TextColumn<String[]>() {
			    	@Override
			    	public String getValue(String[] s) {
			    		return s[5];
			    	}
			    };
			    visualizeTable.addColumn(unitColumn, new TextHeader("Unit"));

			    final ListDataProvider<String[]> dataProvider = new ListDataProvider<String[]>(rowsL);
			    dataProvider.addDataDisplay(visualizeTable);
			    
			    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
			    SimplePager pagerTop = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			    pagerTop.setDisplay(visualizeTable);
			    
			    SimplePager pagerBottom = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
			    pagerBottom.setDisplay(visualizeTable);

			    visualizationPanel.add(visualizeTable);
			    RootPanel.get("visualizationContainer").clear();
				RootPanel.get("visualizationContainer").add(pagerTop);
				RootPanel.get("visualizationContainer").add(visualizationPanel);
				RootPanel.get("visualizationContainer").add(pagerBottom);	
				addSource();
			}
		});
	 }
}
