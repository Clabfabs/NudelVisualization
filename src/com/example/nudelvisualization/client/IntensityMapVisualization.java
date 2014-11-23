package com.example.nudelvisualization.client;

import java.util.HashMap;

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
 * 			- Kommentar ohne Kasten. 
 * 			- Methoden testen und kommentieren. 
 * 			
 */
public class IntensityMapVisualization extends Visualization {


	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	String [][] IsoCodes = null;
	String [][] result = null;
	String [][] populationData = null;

	public IntensityMapVisualization(Configuration config) {
		super(config);
		initialize();
	}
	
	private void initialize() {
		dataAccessSocket.getDataForIntensityMap(config, new AsyncCallback<HashMap<String, String[][]>>() {
			public void onFailure(Throwable caught) { System.out.println("Communication with server failed"); }
			public void onSuccess(final HashMap<String, String[][]> data) { 
				IsoCodes = data.get("IsoCode");
				populationData = data.get("population");
				
				/* Idea how you could draw your visualizations per dataseries
				for (String s : config.getSelectedDataSeriesList()) {
					result = data.get(s);
				}
				*/
				
				// Until then, let's just take "production"
				result = data.get("production");
				draw();
			}	
		});	
	}

	@Override
	public void draw() {
		// Shows how the data is given
		System.out.println("IsoCodes:");
		for (int i = 0; i < IsoCodes.length; i++) {
			for (int j = 0; j < IsoCodes[i].length; j++) {
				System.out.print(IsoCodes[i][j] + "\t");
			}
			System.out.println("\n");
		}
		System.out.println("result:");
		for (int i = 0; i < 5 && i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				System.out.print(result[i][j] + "\t");
			}
			System.out.println("\n");
		}
		System.out.println("population:");
		for (int i = 0; i < 5 && i < populationData.length; i++) {
			for (int j = 0; j < populationData[i].length; j++) {
				System.out.print(populationData[i][j] + "\t");
			}
			System.out.println("\n");
		}
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
						for (int i = 0; i< configIsoCodes.length; i++){
							for (int j= 0; j< IsoCodes.length; j++){
								if (config.getSelectedAreaList().get(i).equals(IsoCodes[j][0])){
									configIsoCodes[i] = IsoCodes[j][1];
								}
							}
						}
						for (int i = 0; i<configIsoCodes.length; i++){
							System.out.println(configIsoCodes[i]);
						}
						//iterate through all selected Areas
						double sumAllData = 0;
						int counter = 0;
						for (int j = 0; j<configIsoCodes.length; j++){
							if (!(configIsoCodes[j].equals(".."))){
							//if there is data for the Area add it up:
							for (int i= 1; i< result.length; i++){
								if (result[i][0].equals(config.getSelectedAreaList().get(j))){
									if (!(result[i][3].isEmpty())){ //get rid of exceptions...
										for (int y = 0; y< populationData.length; y++){
											if(populationData[y][1].equals(result[i][1])){
												double ValueAsDouble = Double.valueOf(result[i][3]);
												double PopulationAsDouble = Double.valueOf(populationData[y][2]);
												sumAllData = sumAllData + (ValueAsDouble/PopulationAsDouble);
									}
								}
							}
							}
							}
							
							//add selected Area with sumAllData. If there is no data, sumAllData = 0
							data.addRow();
							data.setValue(counter, 0, configIsoCodes[j]);
							data.setValue(counter, 1, sumAllData);
							sumAllData = 0;
							counter++;
						}
						}
					
						String allSelectedYears = "";
						for (int i = 0; i<config.getSelectedYearsList().size(); i++){
							allSelectedYears = allSelectedYears.concat(config.getSelectedYearsList().get(i)) +" ";	
						}
						String allSelectedItems = result[0][2] +" ";
						for (int i = 1; i<result.length; i++){
							if (result[i][2].equals(result[i-1][2])==false){
							allSelectedItems = (allSelectedItems.concat(result[i][2])) + " ";	
						}
						}
						TextArea text = new TextArea();
						text.removeStyleName("TextArea");//doesn't function yet
						text.addStyleName("TextAreaNew");//doesn't function yet
						text.setReadOnly(true);
						text.setPixelSize(430, 30);
						text.setText("Production in tonnes divided through population" + " of " + allSelectedItems + " in " + allSelectedYears + ".");
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
