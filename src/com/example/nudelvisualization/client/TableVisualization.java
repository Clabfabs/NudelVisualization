package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;





// import com.example.nudelvisualization.client.IndexedColumn;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TableVisualization extends Visualization {

	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private VerticalPanel visualizationPanel = new VerticalPanel();
	private CellTable visualizeTable = new CellTable<>();
	private String[][] result = null;
	
	public TableVisualization(Configuration config) {
		super(config);
		// protected areaIDs, itemIDs, years & dataSeries now ready to be used
	}

	public void draw() {
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
				visualizeTable.setPageSize(50);
				visualizeTable.setWidth("100%", true);
				result = data.get("Production");
				for (int i = 0; i < 30 && i < result.length; i++) {
					for (int j = 0; j < result[i].length; j++) {
						System.out.print(result[i][j] + "\t");
					}
					System.out.println("\n");
				}
				// Get the rows as List
			    int nrows = result.length;
			    int ncols = result[0].length;
			    ArrayList rowsL = new ArrayList(nrows);
			    
			    //List rowsL = new ArrayList(nrows);
			    for (int irow = 0; irow < nrows; irow++) {
				    List<String> rowL = Arrays.asList(result[irow]); 
				    rowsL.add(rowL); 

			    }
			    
			    // Create table columns
			    for (int icol = 0; icol < ncols; icol++) {
			    	IndexedColumn iColumn = new IndexedColumn(icol);
			    	visualizeTable.addColumn(iColumn, new TextHeader(config.getSelectedTitles().get(icol)));
			    }

			    final ListDataProvider<ArrayList<String>> dataProvider = new ListDataProvider<ArrayList<String>>(rowsL);
			    dataProvider.addDataDisplay(visualizeTable);
			    
			    SimplePager pagerTop = new SimplePager();
			    pagerTop.setDisplay(visualizeTable);
			    SimplePager pagerBottom = new SimplePager();
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
