package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;


public class Filter {

	private ArrayList<Area> area = new ArrayList<Area>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Year> years = new ArrayList<Year>();
	private ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	// Listbox for GUI which will offer the option to choose one of the Areas in
	// the Arraylist area.
	private ListBox lbAreaFilter = null;
	// Listbox for GUI which will offer the option to choose one of the Items in
	// the Arraylist items.
	private ListBox lbItemsFilter = null;

	/**
	 * Create a remote service proxy to talk to the server-side database
	 * service.
	 */
	private AccessDatabaseAsync dataAccessSocket = null;

	public Filter(ListBox lbArea, ListBox lbItems) {
		lbItemsFilter = lbItems;
		lbAreaFilter = lbArea;
	}

	public void init() {
		dataAccessSocket = GWT.create(AccessDatabase.class);
		setDataSeries();
		setYears();
		setItems();
		setArea();
		//testSql();
	}

	private void testSql() {
		dataAccessSocket.getSQLSelection(null, new AsyncCallback<String[][]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String[][] result) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void setArea() {
		dataAccessSocket.getArea(new AreaCallbackHandler());
	}

	public ArrayList<Area> getArea() {
		return this.area;
	}

	/*
	 * Gather all Items in an arraylist--> hat noch viele Items doppelt, da es
	 * von jedem Land einfach einmal jedes Item nimmt. Viele Länder haben aber
	 * die gleichen Items...Dieses Problem wird mit einer Datenbank gelöst-->
	 * warten auf Sprint 2
	 */
	private void setItems() {
		dataAccessSocket.getItem(new ItemCallbackHandler());

	}

	// Returns the arraylist of all Item Objects
	public ArrayList<Item> getItems() {
		return this.items;
	}

	// Returns the arraylist which consists of all Year Objects
	private void setYears() {
		addYears();
	}

	// Adding all Years in the ArrayList
	public void addYears() {
		int startYear = 1990;
		int endYear = 2011;

		for (int j = 0; j <= endYear - startYear; j++) {
			int x = startYear + j;
			Integer n = new Integer(x);
			years.add(new Year(n.toString()));
		}
	}

	public ArrayList<Year> getYears() {
		return years;
	}

	// Adding all DataSeries-Objects
	public void setDataSeries() {
		DataSeries exports = new DataSeries("1", "export");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries("2", "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries("3", "production");
		dataSeries.add(production);
	}

	public ArrayList<DataSeries> getDataSeries() {
		return dataSeries;
	}

	// adds all active Objects of the ArrayLists areas, items, years and
	// DataSeries to the Configuration config.
	/*
	 * public void createConfiguration() {
	 * 
	 * for (int i = 0; i < area.size(); i++) { if (area.get(i).isActive()) {
	 * config.addArea(area.get(i)); } }
	 * 
	 * for (int i = 0; i < items.size(); i++) { if (items.get(i).isActive()) {
	 * config.addItem(items.get(i)); } }
	 * 
	 * for (int i = 0; i < years.size(); i++) { if (years.get(i).isActive()) {
	 * config.addYear(years.get(i)); } }
	 * 
	 * for (int i = 0; i < dataSeries.size(); i++) { if
	 * (dataSeries.get(i).isActive()) { config.addDataSeries(dataSeries.get(i));
	 * } } }
	 */

	/*
	 * public Configuration getConfig() { return this.config; }
	 */

	private class AreaCallbackHandler implements AsyncCallback<String[][]> {

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(String[][] result) {
			// indices of column "AreaCode" and "AreaName"
			int indexAreaCode = 0;
			int indexAreaName = 1;

			/*
			 * We fill Area objects with the values of the columns "AreaCode"
			 * and "AreaName" and gather them in an arraylist(area).
			 */
			for (int j = 0; j < result.length; j++) {
				area.add(new Area(result[j][indexAreaCode],
						result[j][indexAreaName]));
				// System.out.println(area.get(j).getName());
				// System.out.println(area.get(j).getID());
				// System.out.println(area.get(j).getName());
				lbAreaFilter
						.addItem(area.get(j).getName(), area.get(j).getID());
				lbAreaFilter.setVisibleItemCount(10);
			}
		}

	}

	private class ItemCallbackHandler implements AsyncCallback<String[][]> {
		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(String[][] result) {
			// indices of column "ItemCode" and "ItemName"
			int indexItemCode = 0;
			int indexItemName = 1;

			/*
			 * We fill Item objects with the values of the columns "ItemCode"
			 * and "ItemName" and gather them in an arraylist(items).
			 */
			for (int j = 0; j < result.length; j++) {
				items.add(new Item(result[j][indexItemCode],
						result[j][indexItemName]));
				lbItemsFilter.addItem(items.get(j).getName(), items.get(j)
						.getID());
				lbItemsFilter.setVisibleItemCount(10);

			}
		}
	}

	public void drawTable(Configuration config) {
		Visualization visualization = new TableVisualization(config);
		visualization.draw();
	}

	public void drawSampleMap(Configuration config) {
		Visualization visualization = new SampleMapVisualization(config);
		visualization.draw();
	}

	public void drawIntensityMap(Configuration config) {
		IntensityMapVisualization map = new IntensityMapVisualization(config);
		map.draw();
	}
	
	public void drawLineChart(Configuration config) {
		LineChartVisualization map = new LineChartVisualization(config);
		map.draw();
	}

	public void drawColumnChart(Configuration config) {
		
		// Load the visualization api, passing the onLoadCallback to be called when loading is done.
	    VisualizationUtils.loadVisualizationApi(new ColumnChartRunnable(), ColumnChart.PACKAGE);
	}
	
	private class ColumnChartRunnable implements Runnable{

		@Override
		public void run() {
			DataTable dataTable = DataTable.create();
			dataTable.addColumn(ColumnType.NUMBER, "Aple");
			dataTable.addColumn(ColumnType.NUMBER, "Potatoes");
			dataTable.addColumn(ColumnType.NUMBER, "Banana");
			dataTable.addRow();
			dataTable.setCell(0, 0, 3, null, null);
			dataTable.setCell(0, 1, 5, null, null);
			dataTable.setCell(0, 2, 9, null, null);

			Options options = CoreChart.createOptions();

			ColumnChart colChart = new ColumnChart(dataTable, options);
			colChart.draw(dataTable, options);
			
			RootPanel.get("visualizationContainer").clear();
			RootPanel.get("visualizationContainer").add(colChart);
		}
	}
}
