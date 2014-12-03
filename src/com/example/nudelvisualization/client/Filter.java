package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.HashMap;
// import java.io.File;

import org.apache.xalan.xsltc.compiler.util.ErrorMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Filter {

	private ArrayList<Area> area = new ArrayList<Area>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Year> years = new ArrayList<Year>();
	private String[][] areaAsString = null;
	private String[][] itemsAsString = null;
	private String[][] yearsAsString = null;
	private Configuration config = null;

	private HorizontalPanel filterHorizontalPanel = new HorizontalPanel();
	private Grid gridYear = new Grid(3, 2);
	private Button buttonTable = new Button("Table");
	private Button buttonColumnChart = new Button("Column Chart");
	private Button buttonGeoMap = new Button("Geo Map");
	private Button buttonLineChart = new Button("Line Chart");
	private Button buttonSaveConfig = new Button("Save Configuration");

	private ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	// Listbox for GUI which will offer the option to choose one of the Areas in
	// the Arraylist area.
	private ListBox lbAreaFilter = new ListBox(true);
	// Listbox for GUI which will offer the option to choose one of the Years in
	// the Arraylist items.
	private ListBox lbYearsFilter = new ListBox(true);
	// Listbox for GUI which will offer the option to choose one of the Items in
	// the Arraylist items.
	private ListBox lbItemsFilter = new ListBox(true);
	// Listbox for GUI which will offer the option to choose one of the
	// DataSeries in
	// the Arraylist dataSeries.
	private ListBox lbDataSeriesFilter = new ListBox(true);

	/**
	 * Create a remote service proxy to talk to the server-side database
	 * service.
	 */
	private AccessDatabaseAsync dataAccessSocket = null;
	private saveConfigAsync saveConfigSocket = null;

	public void init() {
		dataAccessSocket = GWT.create(AccessDatabase.class);
		fillListBoxes();
	}

	private void fillListBoxes() {
		dataAccessSocket.getInitialData(new InitialDataCallbackHandler());
	}

	private class InitialDataCallbackHandler implements AsyncCallback<HashMap<String, String[][]>> {

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(HashMap<String, String[][]> result) {
			areaAsString = result.get("area");
			itemsAsString = result.get("items");
			yearsAsString = result.get("years");
			// indices of column "AreaCode" and "AreaName"
			int indexCode = 0;
			int indexName = 1;

			/*
			 * We fill Area objects with the values of the columns "AreaCode"
			 * and "AreaName" and gather them in an arraylist(area).
			 */
			for (int j = 0; j < areaAsString.length; j++) {
				area.add(new Area(areaAsString[j][indexCode], areaAsString[j][indexName]));
				lbAreaFilter.addItem(area.get(j).getName(), area.get(j).getID());
			}
			lbAreaFilter.setVisibleItemCount(10);

			/*
			 * We fill Item objects with the values of the columns "ItemCode"
			 * and "ItemName" and gather them in an arraylist(items).
			 */
			for (int j = 0; j < itemsAsString.length; j++) {
				items.add(new Item(itemsAsString[j][indexCode], itemsAsString[j][indexName]));
				lbItemsFilter.addItem(items.get(j).getName(), items.get(j).getID());
			}
			lbItemsFilter.setVisibleItemCount(10);

			/*
			 * We fill Year object
			 */
			for (int j = 0; j < yearsAsString.length; j++) {
				years.add(new Year(yearsAsString[j][0]));
				lbYearsFilter.addItem(years.get(j).getYear());
			}
			lbYearsFilter.setVisibleItemCount(10);

			// Adding all DataSeries-Objects
			setDataSeries();

			// Listbox DataSeries
			for (int i = 0; i < dataSeries.size(); i++) {
				lbDataSeriesFilter.addItem(dataSeries.get(i).getName(), dataSeries.get(i).getID());
			}
			lbDataSeriesFilter.setVisibleItemCount(10);

			initializeFilter();
		}
	}

	public void setDataSeries() {
		DataSeries exports = new DataSeries("1", "production");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries("2", "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries("3", "export");
		dataSeries.add(production);
	}

	public void initializeFilter() {

		// Button to initialize TableVis
		buttonTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You have forgotten to chose: ";
					if (config.getSelectedAreaList().isEmpty()){
						msg = msg + "the countrie(s) ";
					}else if(config.getSelectedYearsList().isEmpty()){
						msg = msg + "the year(s)";
					}else if (config.getSelectedDataSeriesList().isEmpty()){
						msg = msg + "the data serie(s) ";
					} else if (config.getSelectedItemsList().isEmpty()){
						msg = msg + "the item(s) ";
					}
					Window.alert(msg);
				} else if (constraint2()) {

				}
				else {
					drawTable(config);

				}
				System.out.println("Invalid input");}
		});

		

		// Button to initialize LineChart
		buttonLineChart.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You have forgotten to chose: ";
					if (config.getSelectedAreaList().isEmpty()){
						msg = msg + "the countrie(s) ";
					}else if(config.getSelectedYearsList().isEmpty()){
						msg = msg + "the year(s)";
					}else if (config.getSelectedDataSeriesList().isEmpty()){
						msg = msg + "the data serie(s) ";
					} else if (config.getSelectedItemsList().isEmpty()){
						msg = msg + "the item(s) ";
					}
					Window.alert(msg);

				} else if (config.getSelectedAreaList().size()>10) {
					Window.alert("Only 10 countries are allowed.");

				}
				else if (config.getSelectedItemsList().size()>5) {
					Window.alert("Only 5 items are allowed.");

				}
				else {
					drawLineChart(config);

				}
				System.out.println("Invalid input");}
		});

		buttonTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You have forgotten to chose: ";
					if (config.getSelectedAreaList().isEmpty()){
						msg = msg + "the countrie(s) ";
					}else if(config.getSelectedYearsList().isEmpty()){
						msg = msg + "the year(s)";
					}else if (config.getSelectedDataSeriesList().isEmpty()){
						msg = msg + "the data serie(s) ";
					} else if (config.getSelectedItemsList().isEmpty()){
						msg = msg + "the item(s) ";
					}
					Window.alert(msg);
				} else if (constraint2()) {

				}
				else {
					drawTable(config);

				}
				System.out.println("Invalid input");}
		});

		
		// Button to initialize ColumnChart
		buttonGeoMap.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You have forgotten to chose: ";
					if (config.getSelectedAreaList().isEmpty()){
						msg = msg + "the countrie(s) ";
					}else if(config.getSelectedYearsList().isEmpty()){
						msg = msg + "the year(s)";
					}else if (config.getSelectedDataSeriesList().isEmpty()){
						msg = msg + "the data serie(s) ";
					} else if (config.getSelectedItemsList().isEmpty()){
						msg = msg + "the item(s) ";
					}
					Window.alert(msg);
				} else {
					drawGeoMap(config);

				}
				System.out.println("Invalid input");
			}
		});

		// Button to initialize ColumnChart
		buttonSaveConfig.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) { /// Ufpasse Fabio has gänderet vo isValid unf is(!!!)In(!!)ValidInput(). Just so you know =)
					/*
					 * saveConfigSocket.getConfigAsFile(config, new
					 * AsyncCallback<File>() {
					 * 
					 * @Override public void onFailure(Throwable caught)
					 * {System.
					 * out.println("Communication with Server failed.");}
					 * 
					 * @Override public void onSuccess(File result) {
					 * 
					 * } });
					 */} else
					System.out.println("Invalid input");
			}
		});
		
		VerticalPanel buttons = new VerticalPanel();
		buttons.add(buttonTable);
		buttons.add(buttonColumnChart);
		buttons.add(buttonLineChart);
		buttons.add(buttonGeoMap);
		buttons.add(buttonSaveConfig);
		buttons.addStyleName("buttonPanel");

		filterHorizontalPanel.add(gridYear);
		filterHorizontalPanel.add(lbAreaFilter);
		filterHorizontalPanel.add(lbYearsFilter);
		filterHorizontalPanel.add(lbDataSeriesFilter);
		filterHorizontalPanel.add(lbItemsFilter);
		filterHorizontalPanel.add(buttons);

		RootPanel.get("filterContainer").clear();
		RootPanel.get("filterContainer").add(filterHorizontalPanel);
	}

	public void drawTable(Configuration config) {
		Visualization visualization = new TableVisualization(config);
		visualization.draw();
	}

	
	public void drawLineChart(Configuration config) {
		LineChartVisualization map = new LineChartVisualization(config);
	}

	public void drawColumnChart(Configuration config) {
		ColumnChartVisualization map = new ColumnChartVisualization(config);
		map.initialize();
	}

	public void drawGeoMap(Configuration config) {
		GeoMapVisualization map = new GeoMapVisualization(config);
		map.initialize();

	}

	public ArrayList<Area> getArea() {
		return area;
	}

	public ArrayList<Year> getYears() {
		return years;
	}

	// Returns the arraylist of all Item Objects
	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<DataSeries> getDataSeries() {
		return dataSeries;
	}

	private boolean isInValidInput() {
		if (config.getSelectedAreaList().isEmpty() || config.getSelectedDataSeriesList().isEmpty() || config.getSelectedItemsList().isEmpty()
				|| config.getSelectedYearsList().isEmpty()) {
			return true;
		} else
			return false;
	}

	private boolean constraint2() {
		return false;
	}

	/**
	 * Updates the Filter. Executed when user clicks any Visualization Button
	 * 
	 * @return
	 */
	private void updateFilter() {

		config = new Configuration();

		for (int i = 0; i < lbAreaFilter.getItemCount(); i++) {
			if (lbAreaFilter.isItemSelected(i) == true) {
				config.addArea(lbAreaFilter.getValue(i));
				config.addAreaName(lbAreaFilter.getItemText(i));
			}
		}

		for (int j = 0; j < lbYearsFilter.getItemCount(); j++) {
			if (lbYearsFilter.isItemSelected(j)) {
				config.addYear(lbYearsFilter.getValue(j));
			}
		}

		for (int n = 0; n < lbDataSeriesFilter.getItemCount(); n++) {
			if (lbDataSeriesFilter.isItemSelected(n)) {
				config.addDataSeries(lbDataSeriesFilter.getValue(n));
				config.addDataSeriesName(lbDataSeriesFilter.getItemText(n));
			}
		}

		for (int m = 0; m < lbItemsFilter.getItemCount(); m++) {
			if (lbItemsFilter.isItemSelected(m)) {
				config.addItem(lbItemsFilter.getValue(m));
				config.addItemNames(lbItemsFilter.getItemText(m));
			}
		}
	}
}
