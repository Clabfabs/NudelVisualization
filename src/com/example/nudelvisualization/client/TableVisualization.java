package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;

// import com.example.nudelvisualization.client.IndexedColumn;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class TableVisualization extends Visualization {

	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private VerticalPanel visualizationPanel = new VerticalPanel();
	private CellTable<String[]> visualizeTable = new CellTable<>();
	private String[][] result = null;
	
	public TableVisualization(Configuration config) {
		super(config); // protected areaIDs, itemIDs, years & dataSeries now ready to be used
		visualizeTable.setPageSize(50);
		visualizeTable.setWidth("100%", true);
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
			    
			    for (int irow = 0; irow < nrows; irow++) {
				    rowsL.add(result[irow]); 
			    }
			    			    
			    TextColumn<String[]> addressColumn = new TextColumn<String[]>() {
			        @Override
			        public String getValue(String[] s) {
			          return s[0];
			        }
			      };
			    
			    // Create table columns
			    IndexedColumn element = new IndexedColumn(0);
			    addressColumn.setSortable(true);
			    visualizeTable.addColumn(addressColumn, new TextHeader("Element Name"));
			    IndexedColumn area = new IndexedColumn(1);
			    area.setSortable(true);
			    visualizeTable.addColumn(area, new TextHeader("Area Name"));
			    IndexedColumn item = new IndexedColumn(2);
			    item.setSortable(true);
			    visualizeTable.addColumn(item, new TextHeader("Item Name"));
			    IndexedColumn year = new IndexedColumn(3);
			    year.setSortable(true);
			    visualizeTable.addColumn(year, new TextHeader("Year"));
			    IndexedColumn value = new IndexedColumn(4);
			    value.setSortable(true);
			    visualizeTable.addColumn(value, new TextHeader("Value"));
			    IndexedColumn unit = new IndexedColumn(5);
			    visualizeTable.addColumn(unit, new TextHeader("Unit"));

			    // Add a ColumnSortEvent.ListHandler to connect sorting to the
			    // java.util.List.
			    ListHandler<String[]> columnSortHandler = new ListHandler<>(rowsL);
			    columnSortHandler.setComparator(addressColumn,
			    		new Comparator<String[]>() {
			    	public int compare(String[] s1, String[] s2) {
			    		if (s1 == s2) {
			    			return 0;
			    		}

			    		// Compare the name columns.
			    		if (s1 != null) {
			    			return (s2 != null) ? s1[0].compareTo(s2[0]) : 1;
			    		}
			    		return -1;
			    	}
			    });
			    visualizeTable.addColumnSortHandler(columnSortHandler);


			    final ListDataProvider<String[]> dataProvider = new ListDataProvider<String[]>(rowsL);
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
	
	private class IndexedColumn extends Column<String[], String> {
	    private final int index;
	    public IndexedColumn(int index) {
	        super(new TextCell());
	        this.index = index;
	    }
		@Override
		public String getValue(String[] object) {
			return object[this.index];
		}
	}
}
