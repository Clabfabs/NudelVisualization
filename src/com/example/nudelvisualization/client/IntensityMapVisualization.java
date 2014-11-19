package com.example.nudelvisualization.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.ImageAreaChart;
import com.google.gwt.visualization.client.visualizations.ImageBarChart;
import com.google.gwt.visualization.client.visualizations.ImageChart;
import com.google.gwt.visualization.client.visualizations.ImageLineChart;
import com.google.gwt.visualization.client.visualizations.ImagePieChart;
import com.google.gwt.visualization.client.visualizations.ImageSparklineChart;
import com.google.gwt.visualization.client.visualizations.IntensityMap;
import com.google.gwt.visualization.client.visualizations.MapVisualization;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

/*
 * TO DOS: 	
 * 			- Karte grösser.
 * 			- alle Länder IsoCodes.
 * 			- Daten in Verhältnis zu Population --> nötig?
 * 			- Kommentar, welche Daten angezeigt werden optimieren. --> Items nicht als Zahl... 
 * 			- Methoden testen und kommentieren. 
 * 			
 */
public class IntensityMapVisualization extends Visualization {


	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	String [][] IsoCodes = null;

	public IntensityMapVisualization(Configuration config) {
		super(config);
		setIsoCodes();
	}

	private void setIsoCodes(){
		dataAccessSocket.getISOCodes(config, new AsyncCallback<String[][]>() {
			public void onFailure(Throwable caught) {
				System.out.println("Communication with server failed");
			}
			public void onSuccess(String[][] result) {
				IsoCodes = result;			
			}
		});
	}
	@Override
	public void draw() {
		dataAccessSocket.getSelectedRows(config, new CallbackHandler());
	}

	private class CallbackHandler implements AsyncCallback<String[][]>{

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(final String[][] result) {

			VisualizationUtils.loadVisualizationApi(
					new Runnable() {
						public void run() {
							
							
							//create IntensityMap
							IntensityMap.Options options = IntensityMap.Options.create();
							options.setRegion(IntensityMap.Region.WORLD);
							options.setSize(440, 220);

							//add Data to IntensityMap
							DataTable data = DataTable.create();
							data.addColumn(ColumnType.STRING, "Country");
							data.addColumn(ColumnType.NUMBER, "Production");

							//get all isoCodes of the selectedAreas
							String [] configIsoCodes = new String[config.getSelectedAreaList().size()];
							for (int i = 0; i<configIsoCodes.length; i++){
								for (int j= 0; j< IsoCodes.length; j++){
									if (config.getSelectedAreaList().get(i).equals(IsoCodes[j][0])&&((IsoCodes[j][1] == null) ==false)){
										configIsoCodes[i] = IsoCodes[j][1];
									}else{
										configIsoCodes[i] = "null";
									}
								}
							}
							for (int i = 0; i<configIsoCodes.length; i++){
								
								System.out.println(configIsoCodes[i]);
							}
							//iterate through all selected Areas
							double sumAllData = 0;
							for (int j = 0; j<configIsoCodes.length; j++){
								//if there is data for the Area add it up:
								for (int i= 1; i< result.length; i++){
									if (result[i][2].equals(config.getSelectedAreaList().get(j))){
										String dataValue = result[i][10];
										if (dataValue.isEmpty()){ //get rid of exceptions...
											sumAllData = 0 + sumAllData;
										}else{
											double dataAsDouble = Double.valueOf(dataValue);
											sumAllData = sumAllData + dataAsDouble;
										}
									}
								}
								//add selected Area with sumAllData. If there is no data, sumAllData = 0.
								if (configIsoCodes[j].equals("null") == false){
								data.addRow();
								data.setValue(j, 0, configIsoCodes[j]);
								data.setValue(j, 1, sumAllData);
								sumAllData = 0;
								}
							}

							String allSelectedYears = "";
							for (int i = 0; i<config.getSelectedYearsList().size(); i++){
								allSelectedYears = allSelectedYears.concat(config.getSelectedYearsList().get(i)) +" ";	
							}
							String allSelectedItems = "";
							for (int i = 0; i<config.getSelectedItemsList().size(); i++){
								allSelectedItems = allSelectedItems.concat(config.getSelectedItemsList().get(i)) +" ";	
							}

							TextArea text = new TextArea();
							text.removeStyleName("TextArea");//doesn't function yet
							text.addStyleName("TextAreaNew");//doesn't function yet
							text.setReadOnly(true);
							text.setPixelSize(430, 30);
							text.setText("Production" + " in tonnes of " + allSelectedItems + " in " + allSelectedYears + ".");
							IntensityMap widget = new IntensityMap(data, options);
							RootPanel.get("visualizationContainer").clear();
							RootPanel.get("visualizationContainer").add(widget);
							RootPanel.get("visualizationContainer").add(text);
						}
					}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
					Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
					ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
					ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
					MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
					ImageSparklineChart.PACKAGE);		
		}
	}
}

