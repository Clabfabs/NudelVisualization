package com.example.nudelvisualization.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;




public class Visualization{

	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	private FlexTable visualizeTable = new FlexTable();
	
	public void draw() {
		dataAccessSocket.getSelectedRows(Controller.config, new CallbackHandler());
	 }



private class CallbackHandler implements AsyncCallback<String[][]>{
	
	
	public void onFailure(Throwable caught) {
		System.out.println("Blah");
	}

	public void onSuccess(String[][] result) {
		
		for (int i = 0; i < result.length; i++) {
			if (result[i][1] != null) {
				for (int j = 0; j < result[i].length; j++) {
					visualizeTable.setText(i + 1, j, result[i][j]);
					}
				}
			}
		visualizeTable.setStyleName("tableVisualization");
		visualizeTable.getRowFormatter().addStyleName(1, "headOfTable");
		Controller.visualizationPanel.add(visualizeTable);
		}
	}
}


	
	
	
	
	
	

	


	