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
 * 			- Kommentar optimieren und ohne Kasten. 
 * 			
 */
public class IntensityMapVisualization extends Visualization {


	String [][] IsoCodes = null;
	String [][] result = null;
	String [][] populationData = null;
	
	public IntensityMapVisualization(){
		
	}

	public IntensityMapVisualization(Configuration config) {
		super(config);
	}
	
	public String[] getIsocodeOfSelectedArea(Configuration config, String[][] IsoCodes){
		String [] configIsoCodes = new String[config.getSelectedAreaList().size()];
		for (int i = 0; i< configIsoCodes.length; i++){
			for (int j= 0; j< IsoCodes.length; j++){
				if (config.getSelectedAreaList().get(i).equals(IsoCodes[j][0])){
					configIsoCodes[i] = IsoCodes[j][1];
				}
			}
		}
		
		return configIsoCodes;
	}
	
	
	public void initialize() {
		AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
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
		VisualizationUtils.loadVisualizationApi(
				new Runnable() {
					public void run() {

						//create IntensityMap
						IntensityMap.Options options = IntensityMap.Options.create();
						options.setRegion(IntensityMap.Region.WORLD);
						options.setSize(440, 220);

						//add data to IntensityMap
						DataTable data = DataTable.create();
						data.addColumn(ColumnType.STRING, "Country");
						data.addColumn(ColumnType.NUMBER, "Production");

						//get all isoCodes of the selectedAreas
						String [] configIsoCodes = new String[config.getSelectedAreaList().size()];
						configIsoCodes = getIsocodeOfSelectedArea(config,IsoCodes);
						//iterate through all selected Areas
						double sumAllData = 0;
						int counter = 0;
						for (int j = 0; j<configIsoCodes.length; j++){
							//if the selected Area is a country:
							if (!(configIsoCodes[j].equals(".."))){
							//gather value of data
							for (int i= 1; i< result.length; i++){
								if (result[i][0].equals(config.getSelectedAreaList().get(j))){
								if (!(result[i][3].isEmpty())){ //get rid of exceptions
									//compare it with population
									for (int y = 0; y< populationData.length; y++){
										if(populationData[y][1].equals(result[i][1])){
											//add up all dataValues
											double valueAsDouble = Double.valueOf(result[i][3]);
											double populationAsDouble = Double.valueOf(populationData[y][2]);
											sumAllData = sumAllData + (valueAsDouble/populationAsDouble);
									}
								}
							}
							}
							}
							
							//add selected country with value of sumAllData. If there is no data, sumAllData = 0
							data.addRow();
							data.setValue(counter, 0, configIsoCodes[j]);
							data.setValue(counter, 1, sumAllData);
							sumAllData = 0;
							counter++;
						}
						}
						//gather selected years and items for the comment underneath the visualization
						String allSelectedYears = "";
						for (int i = 0; i<config.getSelectedYearsList().size(); i++){
							if (i==(config.getSelectedYearsList().size()-1)){
								allSelectedYears = allSelectedYears.concat(config.getSelectedYearsList().get(i));		
							}else{
							allSelectedYears = allSelectedYears.concat(config.getSelectedYearsList().get(i)) +", ";	
						}
						}
						String allSelectedItems = result[0][2]+ " ";
						for (int i = 1; i<result.length; i++){
							if (result[i][2].equals(result[i-1][2])==false){
							allSelectedItems = (allSelectedItems.concat(result[i][2])) + ", ";	
						}
						}
						TextArea text = new TextArea();
						text.removeStyleName("TextArea");//doesn't function yet
						text.addStyleName("TextAreaNew");//doesn't function yet
						text.setReadOnly(true);
						text.setPixelSize(430, 30);
						text.setText("Production in tonnes divided through population" + " of " + allSelectedItems + "in " + allSelectedYears + ".");
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
