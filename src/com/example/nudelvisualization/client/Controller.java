package com.example.nudelvisualization.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
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
    private Button buttonGeoMap = new Button("Geo Map");
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
	    		if (isValidInput()) {
	    			filter.drawTable(config);
	    		} else System.out.println("Invalid input");

	    	}
	    });
	    
	    // Button to initialize IntensityMap
	    buttonIntensityMap.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		if (isValidInput()) {
	    			filter.drawIntensityMap(config);
	    		} else System.out.println("Invalid input");

	    	}
	    });
	    
	    // Button to initialize LineChart
	    buttonLineChart.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		if (isValidInput()) {
	    			filter.drawLineChart(config);
	    		} else System.out.println("Invalid input");

	    	} 
	    });
	    
	    buttonGeoMap.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    		if (isValidInput()) {
	    			filter.drawGeoMap(config);
	    		} else System.out.println("Invalid input");

	    	} 
	    });
	    
	    // Button to initialize ColumnChart
	    buttonColumnChart.addClickHandler(new ClickHandler(){
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems);
	    		if (isValidInput()) {
	    			filter.drawColumnChart(config);	    			
	    		} else System.out.println("Invalid input");
	    	}
	    });
	    
	    VerticalPanel buttons = new VerticalPanel();
	    buttons.add(buttonTable);
	    buttons.add(buttonIntensityMap);
	    buttons.add(buttonColumnChart);
	    buttons.add(buttonLineChart);
	    buttons.add(buttonGeoMap);
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
	    filterHorizontalPanel.add(lbArea);
	    filterHorizontalPanel.add(lbYear);
	    filterHorizontalPanel.add(lbDataSeries);
	    filterHorizontalPanel.add(lbItems);
	    filterHorizontalPanel.add(buttons);
	   // filterHorizontalPanel.add(panel);

	    RootPanel.get("filterContainer").add(filterHorizontalPanel);	    	    
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
	 * Updates the Filter. Executed when user clicks the Update Button
	 * @return 
	 */
	private void updateFilter(ListBox lbArea, ListBox lbYear, ListBox lbDataSeries, ListBox lbItems){
		
		config = new Configuration();
		
		for (int i = 0; i < lbArea.getItemCount(); i++){
			if (lbArea.isItemSelected( i) == true){
				// selectedAreas.add(lbArea.getValue(i));
				config.addArea(lbArea.getValue(i));
				config.addAreaName(lbArea.getItemText(i));
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
				config.addItemNames(lbItems.getItemText(m));
			}
		}
	}
	
}
