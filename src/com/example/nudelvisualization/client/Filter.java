package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
// import java.io.File;




import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

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
	private Button buttonLoadConfig = new Button("Load Configuration");

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
			lbAreaFilter.setWidth("100%");

			/*
			 * We fill Item objects with the values of the columns "ItemCode"
			 * and "ItemName" and gather them in an arraylist(items).
			 */
			for (int j = 0; j < itemsAsString.length; j++) {
				items.add(new Item(itemsAsString[j][indexCode], itemsAsString[j][indexName]));
				lbItemsFilter.addItem(items.get(j).getName(), items.get(j).getID());
			}
			lbItemsFilter.setVisibleItemCount(10);
			lbItemsFilter.setWidth("100%");


			/*
			 * We fill Year object
			 */
			for (int j = 0; j < yearsAsString.length; j++) {
				years.add(new Year(yearsAsString[j][0]));
				lbYearsFilter.addItem(years.get(j).getYear());
			}
			lbYearsFilter.setVisibleItemCount(10);
			lbYearsFilter.setWidth("100%");

			// Adding all DataSeries-Objects
			setDataSeries();

			// Listbox DataSeries
			for (int i = 0; i < dataSeries.size(); i++) {
				lbDataSeriesFilter.addItem(dataSeries.get(i).getName(), dataSeries.get(i).getID());
			}
			lbDataSeriesFilter.setVisibleItemCount(10);
			lbDataSeriesFilter.setWidth("100%");


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

		HorizontalPanel configButtons = new HorizontalPanel();
		
		final FormPanel uploadForm = new FormPanel();

		uploadForm.setAction("/nudelvisualization/config");
		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
	    uploadForm.setMethod(FormPanel.METHOD_POST);
	    
	    FileUpload upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    uploadForm.add(upload);
	    
	    // Add an event handler to the form.
	    uploadForm.addSubmitHandler(new FormPanel.SubmitHandler() {
	      public void onSubmit(SubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	      }
	    });
	    uploadForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	      public void onSubmitComplete(SubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
	    	String result = event.getResults();
	    	String[] results = result.split("\n");

	    	if (!results[0].equals("")) {
	    		ArrayList<String> areas = new ArrayList<String>(Arrays.asList(results[0].split(","))); 
	    		ArrayList<String> items = new ArrayList<String>(Arrays.asList(results[1].split(","))); 
	    		ArrayList<String> years = new ArrayList<String>(Arrays.asList(results[2].split(","))); 
	    		ArrayList<String> dataSeries = new ArrayList<String>(Arrays.asList(results[3].split(","))); 

	    		for (int i = 0; i < lbAreaFilter.getItemCount(); i++) {
	    			if (areas.contains(lbAreaFilter.getValue(i))) {
	    				lbAreaFilter.setItemSelected(i, true);
	    			} else {
	    				lbAreaFilter.setItemSelected(i, false);
	    			}
	    		}
	    		for (int i = 0; i < lbItemsFilter.getItemCount(); i++) {
	    			if (items.contains(lbItemsFilter.getValue(i))) {
	    				lbItemsFilter.setItemSelected(i, true);
	    			} else {
	    				lbItemsFilter.setItemSelected(i, false);
	    			}
	    		}
	    		for (int i = 0; i < lbYearsFilter.getItemCount(); i++) {
	    			if (years.contains(lbYearsFilter.getValue(i))) {
	    				lbYearsFilter.setItemSelected(i, true);
	    			} else {
	    				lbYearsFilter.setItemSelected(i, false);
	    			}
	    		}
	    		for (int i = 0; i < lbDataSeriesFilter.getItemCount(); i++) {
	    			if (dataSeries.contains(lbDataSeriesFilter.getValue(i))) {
	    				lbDataSeriesFilter.setItemSelected(i, true);
	    			} else {
	    				lbDataSeriesFilter.setItemSelected(i, false);
	    			}
	    		}
	    	} else {
	    		Window.alert("No file selected.");
	    	}
	      }
	    });
	    
		buttonLoadConfig.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				uploadForm.submit();
			}
		});
		
		// Buttons to initialize
		buttonTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You forgot to choose: ";
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
				} else if (config.getSelectedAreaList().size() * config.getSelectedItemsList().size() * config.getSelectedYearsList().size() * config.getSelectedDataSeriesList().size() > 35000) {
					Window.alert("You chose too many options, this would probably cause the database connection to time out.");
				} else {
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
					String msg = "You forgot to choose: ";
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
		
		//Button to initialize ColumnChart
		buttonColumnChart.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You forgot to choose: ";
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
				}  else if (config.getSelectedAreaList().size()>10) {
					Window.alert("Only 10 countries are allowed.");
				}
				else if (config.getSelectedItemsList().size()>5) {
					Window.alert("Only 5 items are allowed.");
				}
				else {
					drawColumnChart(config);

				}
				System.out.println("Invalid input");}
		});

		
		// Button to initialize GeoMap
		buttonGeoMap.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isInValidInput()) {
					String msg = "You forgot to choose: ";
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
				}
				else if (config.getSelectedItemsList().size()>5) {
					Window.alert("Only 5 items are allowed.");

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
				if (isInValidInput()) {
					System.out.println("Invalid input");
				} else {
					StringBuilder areas = new StringBuilder();
					for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
						areas.append(config.getSelectedAreaList().get(i)+",");
					}
					areas.append(config.getSelectedAreaList().get(config.getSelectedAreaList().size()-1));
					StringBuilder items = new StringBuilder();
					for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
						items.append(config.getSelectedItemsList().get(i)+",");
					}
					items.append(config.getSelectedItemsList().get(config.getSelectedItemsList().size()-1));
					StringBuilder years = new StringBuilder();
					for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
						years.append(config.getSelectedYearsList().get(i)+",");
					}
					years.append(config.getSelectedYearsList().get(config.getSelectedYearsList().size()-1));
					StringBuilder dataSeries = new StringBuilder();
					for (int i = 0; i < config.getSelectedDataSeriesList().size() - 1; i++) {
						dataSeries.append(config.getSelectedDataSeriesList().get(i)+",");
					}
					dataSeries.append(config.getSelectedDataSeriesList().get(config.getSelectedDataSeriesList().size()-1));
					
					Window.open("/nudelvisualization/config?areas="+URL.encode(areas.toString())+"&items="+URL.encode(items.toString())+"&years="+URL.encode(years.toString())+"&dataseries="+URL.encode(dataSeries.toString()), "_blank", "");
				}
			}
		});
		
		configButtons.add(buttonSaveConfig);
		configButtons.add(uploadForm);
		configButtons.add(buttonLoadConfig);

		configButtons.setWidth("100%");
		configButtons.setCellHorizontalAlignment(buttonSaveConfig, HasHorizontalAlignment.ALIGN_CENTER);
		configButtons.setCellHorizontalAlignment(uploadForm, HasHorizontalAlignment.ALIGN_RIGHT);
		configButtons.setCellHorizontalAlignment(buttonLoadConfig, HasHorizontalAlignment.ALIGN_RIGHT);
		configButtons.setCellVerticalAlignment(buttonSaveConfig, HasVerticalAlignment.ALIGN_MIDDLE);
		configButtons.setCellVerticalAlignment(uploadForm, HasVerticalAlignment.ALIGN_MIDDLE);
		configButtons.setCellVerticalAlignment(buttonLoadConfig, HasVerticalAlignment.ALIGN_MIDDLE);
		configButtons.addStyleName("buttonPanel");

		
		HorizontalPanel visualizationButtons = new HorizontalPanel();
		visualizationButtons.add(buttonTable);
		visualizationButtons.add(buttonColumnChart);
		visualizationButtons.add(buttonLineChart);
		visualizationButtons.add(buttonGeoMap);
		visualizationButtons.setCellWidth(buttonTable, "25%");
		visualizationButtons.setCellWidth(buttonColumnChart, "25%");
		visualizationButtons.setCellWidth(buttonLineChart, "25%");
		visualizationButtons.setCellWidth(buttonGeoMap, "25%");
		visualizationButtons.setCellHorizontalAlignment(buttonTable, HasHorizontalAlignment.ALIGN_CENTER);
		visualizationButtons.setCellHorizontalAlignment(buttonColumnChart, HasHorizontalAlignment.ALIGN_CENTER);
		visualizationButtons.setCellHorizontalAlignment(buttonLineChart, HasHorizontalAlignment.ALIGN_CENTER);
		visualizationButtons.setCellHorizontalAlignment(buttonGeoMap, HasHorizontalAlignment.ALIGN_CENTER);
		visualizationButtons.addStyleName("buttonPanel");
		visualizationButtons.setWidth("100%");

		filterHorizontalPanel.add(gridYear);
		filterHorizontalPanel.add(lbAreaFilter);
		filterHorizontalPanel.add(lbYearsFilter);
		filterHorizontalPanel.add(lbDataSeriesFilter);
		filterHorizontalPanel.add(lbItemsFilter);
		filterHorizontalPanel.setWidth("100%");

		RootPanel.get("filterContainer").clear();
		RootPanel.get("filterContainer").add(configButtons);
		RootPanel.get("filterContainer").add(filterHorizontalPanel);
		RootPanel.get("filterContainer").add(visualizationButtons);

	}

	public void drawTable(Configuration config) {
		Visualization visualization = new TableVisualization(config);
		visualization.draw();
	}

	
	public void drawLineChart(Configuration config) {
		LineChartVisualization map = new LineChartVisualization(config);
		map.initialize();
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
