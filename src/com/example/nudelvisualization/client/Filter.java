package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.HashMap;
// import java.io.File;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
    private Button buttonIntensityMap = new Button("Intensity Map");
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
	// Listbox for GUI which will offer the option to choose one of the DataSeries in
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
				items.add(new Item(itemsAsString[j][indexCode],	itemsAsString[j][indexName]));
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

			//Listbox DataSeries
			for (int i = 0; i < dataSeries.size(); i++){
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
				if (isValidInput()) {
					drawTable(config);
				} else System.out.println("Invalid input");
			}
		});

		// Button to initialize IntensityMap
		buttonIntensityMap.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isValidInput()) {
					drawIntensityMap(config);
				} else System.out.println("Invalid input");

			}
		});

		// Button to initialize LineChart
		buttonLineChart.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isValidInput()) {
					drawLineChart(config);
				} else System.out.println("Invalid input");
			} 
		});

		buttonGeoMap.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isValidInput()) {
					drawGeoMap(config);
				} else System.out.println("Invalid input");
			} 
		});

		// Button to initialize ColumnChart
		buttonColumnChart.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isValidInput()) {
					drawColumnChart(config);	    			
				} else System.out.println("Invalid input");
			}
		});

		// Button to initialize ColumnChart
		buttonSaveConfig.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				updateFilter();
				if (isValidInput()) {
					/*saveConfigSocket.getConfigAsFile(config, new AsyncCallback<File>() {
						@Override
						public void onFailure(Throwable caught) {System.out.println("Communication with Server failed.");}

						@Override
						public void onSuccess(File result) {
							
						}
					});
*/				} else System.out.println("Invalid input");
			}
		});
		
		VerticalPanel buttons = new VerticalPanel();
		buttons.add(buttonTable);
		buttons.add(buttonIntensityMap);
		buttons.add(buttonColumnChart);
		buttons.add(buttonLineChart);
		buttons.add(buttonGeoMap);
		buttons.add(buttonSaveConfig);
		buttons.addStyleName("buttonPanel");

		/*	    VerticalPanel upload = new VerticalPanel();
			    // Create a FormPanel and point it at a service.
			    final FormPanel form = new FormPanel();

			    // Because we're going to add a FileUpload widget, we'll need to set the
			    // form to use the POST method, and multipart MIME encoding.
			    form.setEncoding(FormPanel.ENCODING_MULTIPART);
			    form.setMethod(FormPanel.METHOD_POST);

			    // Create a panel to hold all of the form widgets.
			    VerticalPanel panel = new VerticalPanel();
			    form.setWidget(panel);

			    // Create a FileUpload widget.
			    final FileUpload upload = new FileUpload();
			    upload.setName("uploadFormElement");
			    panel.add(upload);

			    // Add a 'submit' button.
			    panel.add(new Button("Submit", new ClickHandler() {
			    	public void onClick(ClickEvent event) {
			    		form.submit();
			    	}
			    }));

		    // Add an event handler to the form.
		    form.addSubmitHandler(new FormPanel.SubmitHandler() {
		      public void onSubmit(SubmitEvent event) {
		        // This event is fired just before the form is submitted. We can take
		        // this opportunity to perform validation.
		    	  System.out.println(upload.getFilename());
		      }
		    });
		    form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
		      public void onSubmitComplete(SubmitCompleteEvent event) {
		    	  System.out.println("Test2");

		        // When the form submission is successfully completed, this event is
		        // fired. Assuming the service returned a response of type text/html,
		        // we can get the result text here (see the FormPanel documentation for
		        // further explanation).
		        Window.alert(event.getResults());
		      }
		    });*/


		filterHorizontalPanel.add(gridYear);
		filterHorizontalPanel.add(lbAreaFilter);
		filterHorizontalPanel.add(lbYearsFilter);
		filterHorizontalPanel.add(lbDataSeriesFilter);
		filterHorizontalPanel.add(lbItemsFilter);
		filterHorizontalPanel.add(buttons);
		// filterHorizontalPanel.add(panel);

		RootPanel.get("filterContainer").add(filterHorizontalPanel);
	}

	public void drawTable(Configuration config) {
		Visualization visualization = new TableVisualization(config);
		visualization.draw();
	}

	public void drawIntensityMap(Configuration config) {
		IntensityMapVisualization map = new IntensityMapVisualization(config);
		map.initialize();

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

	private boolean isValidInput() {
		if (config.getSelectedAreaList().isEmpty()
				|| config.getSelectedDataSeriesList().isEmpty()
				|| config.getSelectedItemsList().isEmpty()
				|| config.getSelectedYearsList().isEmpty()) {
			return false;
		} else
			return true;
	}

	/**
	 * Updates the Filter. Executed when user clicks any Visualization Button
	 * @return 
	 */
	private void updateFilter(){
		
		config = new Configuration();
		
		for (int i = 0; i < lbAreaFilter.getItemCount(); i++){
			if (lbAreaFilter.isItemSelected( i) == true){
				config.addArea(lbAreaFilter.getValue(i));
				config.addAreaName(lbAreaFilter.getItemText(i));
			}
		}
		
		for (int j= 0; j<lbYearsFilter.getItemCount(); j++){
			if (lbYearsFilter.isItemSelected(j)){
				config.addYear(lbYearsFilter.getValue(j));
			}
		}
		
		for (int n = 0; n<lbDataSeriesFilter.getItemCount(); n++){
			if(lbDataSeriesFilter.isItemSelected(n)){
				config.addDataSeries(lbDataSeriesFilter.getValue(n));
				config.addDataSeriesName(lbDataSeriesFilter.getItemText(n));
			}
		}
		
		for (int m= 0; m< lbItemsFilter.getItemCount(); m++){
			if(lbItemsFilter.isItemSelected(m)){
				config.addItem(lbItemsFilter.getValue(m));
				config.addItemNames(lbItemsFilter.getItemText(m));
			}
		}
	}
}
