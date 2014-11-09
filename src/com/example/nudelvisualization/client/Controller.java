package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.List;

import com.example.nudelvisualization.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Composite;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Controller implements EntryPoint {

	static VerticalPanel visualizationPanel = new VerticalPanel();
    private HorizontalPanel filterHorizontalPanel = new HorizontalPanel();
    //private TextBox tbYearStart = new TextBox();
    //private TextBox tbYearEnd = new TextBox();
    private Grid gridYear = new Grid(3, 2);
    private Button buttonUpdateFilter = new Button("OK");
    private ListBox lbArea = new ListBox(true);
    private ListBox lbYear = new ListBox(true);
    private ListBox lbDataSeries = new ListBox(true);
    private ListBox lbItems = new ListBox(true);
    Filter filter = new Filter(lbArea, lbItems);
    private ArrayList<String> selectedAreas = new ArrayList<String>();
    private ArrayList<String> selectedYears = new ArrayList<String>();
    private ArrayList<String> selectedDataSeries = new ArrayList<String>();
    private ArrayList<String> selectedItems = new ArrayList<String>();
    static Configuration config = new Configuration(); 





	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);

	
	 /**
	   * Updates the Filter. Executed when user clicks the Update Button
	 * @return 
	   */
	private final void updateFilter(ListBox lbArea, ListBox lbYear, ListBox lbDataSeries, ListBox lbItems){
		
		for (int i = 0; i < lbArea.getItemCount(); i++){
			if (lbArea.isItemSelected( i) == true){
				selectedAreas.add(lbArea.getValue(i));
				config.addArea(lbArea.getValue(i));
				System.out.println("Land " + i+ lbArea.getValue(i));
			}
		}
		
		for (int j= 0; j<lbYear.getItemCount(); j++){
			if (lbYear.isItemSelected(j)){
				selectedYears.add(lbYear.getValue(j));
				config.addYear(lbYear.getValue(j));
				System.out.println(lbYear.getValue(j));
			}
		}
		
		for (int n = 0; n<lbDataSeries.getItemCount(); n++){
			if(lbDataSeries.isItemSelected(n)){
				selectedDataSeries.add(lbDataSeries.getValue(n));
				config.addDataSeries(lbDataSeries.getValue(n));
				System.out.println(lbDataSeries.getValue(n));
			}
		}
		
		for (int m= 0; m< lbItems.getItemCount(); m++){
			if(lbItems.isItemSelected(m)){
				selectedItems.add(lbItems.getValue(m));
				config.addItem(lbItems.getValue(m));
				System.out.println(lbItems.getValue(m));

			}
		}
		
		
	}
	
	public ArrayList<String> getSelectedAreas(){
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
	}
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		new ListBox(true);
		
		// TODO Put this in a separate Class "Visualization"

	
		//create ListBox for Area
		//for (int i = 0; i < filter.area.size(); i++){
		//	lbArea.addItem(filter.area.get(i).getName());
		//}
	    
	    
//		for (int j = 0; j < filter.getArea().size(); j++) {
//			cb.add(new CheckBox(filter.getArea().get(j).getName()));
//		}
		
//		for (int j = 0; j < cb.size(); j++) {
//			cb.get(j).setValue(false);
//		}
	    

		// Hook up a handler to find out when it's clicked.
//		for (int j = 0; j < cb.size(); j++){
//			cb.get(j).addClickHandler(new ClickHandler() {
//				@Override
//				public void onClick(ClickEvent event) {
//					boolean checked = ((CheckBox) event.getSource()).getValue();
//					Window.alert("It is " + (checked ? "" : "not ") + "checked");
//				}
//			});
//		}
//	    // Add it to the root panel.
//		for (int i = 0; i<cb.size(); i++){
//	    RootPanel.get().add(cb.get(i));
//		}
		
//
	    
		//Listbox Year
		for (int i = 0; i< filter.getYears().size(); i++){
			lbYear.addItem(filter.getYears().get(i).getYear() + "       ");
		}
		
		//Listbox DataSeries
		for (int i = 0; i<filter.getDataSeries().size(); i++){
			lbDataSeries.addItem(filter.getDataSeries().get(i).getName(), filter.getDataSeries().get(i).getID());
		}
		
		
	    // Button to update Filter
	    buttonUpdateFilter.addClickHandler(new ClickHandler() {
	    	
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		updateFilter(lbArea, lbYear, lbDataSeries, lbItems );
	    	}
	    });
	    
	    
	    
	    filterHorizontalPanel.add(gridYear);
	    filterHorizontalPanel.add(lbArea);
	    filterHorizontalPanel.add(lbYear);
	    filterHorizontalPanel.add(lbDataSeries);
	    filterHorizontalPanel.add(lbItems);

	    filterHorizontalPanel.add(buttonUpdateFilter);
	    
	    RootPanel.get("filterContainer").add(filterHorizontalPanel);
		RootPanel.get("visualizationContainer").add(visualizationPanel);

		// -----------------------------------------------------------------------
		// //
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		// sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		// RootPanel.get("nameFieldContainer").add(nameField);
		// RootPanel.get("sendButtonContainer").add(sendButton);
		// RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				// sendButton.setEnabled(true);
				// sendButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				// sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				/*
				 * greetingService.greetServer(textToServer, new
				 * AsyncCallback<String>() { public void onFailure(Throwable
				 * caught) { // Show the RPC error message to the user
				 * dialogBox.setText("Remote Procedure Call - Failure");
				 * serverResponseLabel.addStyleName("serverResponseLabelError");
				 * serverResponseLabel.setHTML(SERVER_ERROR);
				 * dialogBox.center(); closeButton.setFocus(true); }
				 * 
				 * public void onSuccess(String result) {
				 * dialogBox.setText("Remote Procedure Call");
				 * serverResponseLabel
				 * .removeStyleName("serverResponseLabelError");
				 * serverResponseLabel.setHTML(result); dialogBox.center();
				 * closeButton.setFocus(true); } });
				 */
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		// sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
