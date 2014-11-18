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
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Controller implements EntryPoint {

	
    private HorizontalPanel filterHorizontalPanel = new HorizontalPanel();
    private Grid gridYear = new Grid(3, 2);
    private Button buttonTable = new Button("Table");
    private Button buttonIntensityMap = new Button("IntensityMap");
    private Button buttonColumnChart = new Button("Chart");
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
		
		// initialize visualization
		// filter.visualizeAsTable(config);
		// filter.drawSampleMap(config);
		// filter.drawIntensityMap(config);	
		
	}
	
	/*public ArrayList<String> getSelectedAreas(){
		return this.selectedAreas;
	}
	
	public ArrayList<String> getSelectedItems(){
		return this.selectedItems;
	}
	
	public ArrayList<String> getSelectedDataSeries(){
		return this.selectedDataSeries;
	}
	
	public ArrayList<String> getSelectedYears(){
		return this.selectedYears;
	}*/
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		filter = new Filter(lbArea, lbItems);
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
	    		filter.drawTable(config);
	    	}
	    });
	    
	    buttonIntensityMap.addClickHandler(new ClickHandler() {
	    	
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		filter.drawIntensityMap(config);
	    	}
	    });
	    
	    buttonColumnChart.addClickHandler(new ClickHandler(){
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		filter.drawColumnChart(config);
	    	}
	    });
	    
	    
	    
	    
	    VerticalPanel buttons = new VerticalPanel();
	    buttons.add(buttonTable);
	    buttons.add(buttonIntensityMap);
	    buttons.add(buttonColumnChart);
	    
	    filterHorizontalPanel.add(gridYear);
	    filterHorizontalPanel.add(lbArea);
	    filterHorizontalPanel.add(lbYear);
	    filterHorizontalPanel.add(lbDataSeries);
	    filterHorizontalPanel.add(lbItems);
	    
	    filterHorizontalPanel.add(buttons);
	    
	    RootPanel.get("filterContainer").add(filterHorizontalPanel);	    	    
	}
}
