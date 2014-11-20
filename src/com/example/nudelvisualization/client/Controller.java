package com.example.nudelvisualization.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Controller implements EntryPoint {
	
    private HorizontalPanel filterHorizontalPanel = new HorizontalPanel();
    private Grid gridYear = new Grid(3, 2);
    private Button buttonTable = new Button("Table");
    private Button buttonIntensityMap = new Button("Intensity Map");
    private Button buttonColumnChart = new Button("Chart");
    private Button buttonLineChart = new Button("Line Chart");
    private ListBox lbArea = new ListBox(true);
    private ListBox lbYear = new ListBox(true);
    private ListBox lbDataSeries = new ListBox(true);
    private ListBox lbItems = new ListBox(true);
    private Filter filter = null;
    private Configuration config = null; 

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	/*private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";*/
	
	 /**
	 * Updates the Filter. Executed when user clicks the Update Button
	 * @return 
	 */
	private final void updateFilter(ListBox lbArea, ListBox lbYear, ListBox lbDataSeries, ListBox lbItems){
		
		config = new Configuration();
		
		for (int i = 0; i < lbArea.getItemCount(); i++){
			if (lbArea.isItemSelected( i) == true){
				// selectedAreas.add(lbArea.getValue(i));
				config.addArea(lbArea.getValue(i));
			}
		}
		
		for (int j= 0; j<lbYear.getItemCount(); j++){
			if (lbYear.isItemSelected(j)){
				// selectedYears.add(lbYear.getValue(j).replaceAll("\\s", ""));
				config.addYear(lbYear.getValue(j).replaceAll("\\s", ""));
			}
		}
		
		for (int n = 0; n<lbDataSeries.getItemCount(); n++){
			if(lbDataSeries.isItemSelected(n)){
				// selectedDataSeries.add(lbDataSeries.getValue(n));
				config.addDataSeries(lbDataSeries.getValue(n));
			}
		}
		
		for (int m= 0; m< lbItems.getItemCount(); m++){
			if(lbItems.isItemSelected(m)){
				// selectedItems.add(lbItems.getValue(m));
				config.addItem(lbItems.getValue(m));
			}
		}
	}
	
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		filter = new Filter(lbArea, lbItems, lbYear);
		filter.init();
		
		//Listbox Year
		for (int i = 0; i< filter.getYears().size(); i++){
			lbYear.addItem(filter.getYears().get(i).getYear() + "       ");
		}
		lbYear.setVisibleItemCount(10);
		
		//Listbox DataSeries
		for (int i = 0; i<filter.getDataSeries().size(); i++){
			lbDataSeries.addItem(filter.getDataSeries().get(i).getName(), filter.getDataSeries().get(i).getID());
		}
		lbDataSeries.setVisibleItemCount(10);
		
	    // Button to initialize TableVis
	    buttonTable.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		// if (!config.getSelectedAreaList().isEmpty()
	    		// 	&& !config.getSelectedItemsList().isEmpty()
	    		// 	&& !config.getSelectedYearsList().isEmpty()
	    		// 	&& !config.getSelectedDataSeriesList().isEmpty()) {
	    			filter.drawTable(config);
	    			// }
	    	}
	    });
	    
	    // Button to initialize IntensityMap
	    buttonIntensityMap.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		// if (!config.getSelectedAreaList().isEmpty()
	    		// 	&& !config.getSelectedItemsList().isEmpty()
	    		// 	&& !config.getSelectedYearsList().isEmpty()
	    		// 	&& !config.getSelectedDataSeriesList().isEmpty()) {
	    			filter.drawIntensityMap(config);
	    		// }
	    	}
	    });
	    
	    // Button to initialize LineChart
	    buttonLineChart.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		// if (!config.getSelectedAreaList().isEmpty()
	    		// 	&& !config.getSelectedItemsList().isEmpty()
	    		// 	&& !config.getSelectedYearsList().isEmpty()
	    		// 	&& !config.getSelectedDataSeriesList().isEmpty()) {
	    			filter.drawLineChart(config);
	    		// }
	    	}
	    });
	    
	    // Button to initialize ColumnChart
	    buttonColumnChart.addClickHandler(new ClickHandler(){
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems);
	    		// if (!config.getSelectedAreaList().isEmpty()
	    		// 	&& !config.getSelectedItemsList().isEmpty()
	    		// 	&& !config.getSelectedYearsList().isEmpty()
	    		// 	&& !config.getSelectedDataSeriesList().isEmpty()) {
	    			filter.drawColumnChart(config);
	    		// }
	    		
	    	}
	    });
	    
	    VerticalPanel buttons = new VerticalPanel();
	    buttons.add(buttonTable);
	    buttons.add(buttonIntensityMap);
	    buttons.add(buttonColumnChart);
	    buttons.add(buttonLineChart);
	    filterHorizontalPanel.add(gridYear);
	    filterHorizontalPanel.add(lbArea);
	    filterHorizontalPanel.add(lbYear);
	    filterHorizontalPanel.add(lbDataSeries);
	    filterHorizontalPanel.add(lbItems);
	    filterHorizontalPanel.add(buttons);
	    
	    RootPanel.get("filterContainer").add(filterHorizontalPanel);	    	    
	}
}
