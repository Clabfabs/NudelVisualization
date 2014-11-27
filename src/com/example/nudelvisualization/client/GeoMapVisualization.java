package com.example.nudelvisualization.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
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

public class GeoMapVisualization extends Visualization {

	/*
	 * TO DO's
	 * - Exception wenn man nur Import oder Export anwählt--> Warum?! (result ist dann null)
	 * - Beschriftung --> ev. Production fett und oben, Absätze
	 * - Methode, die Land überprüft und dann den richtigen Namen ausgibt oder Datenbank änderen... 
	 * 
	 * */
	String [][] IsoCodes = null;
	String [][] productionresult = null;
	String [][] populationData = null;
	String [][] importresult = null;
	String [][] exportresult = null;
	
	public GeoMapVisualization(){
		
	}

	public GeoMapVisualization(Configuration config) {
		super(config);
	}
	
	
	
	public void initialize() {
		AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
		dataAccessSocket.getDataForIntensityMap(config, new AsyncCallback<HashMap<String, String[][]>>() {
			public void onFailure(Throwable caught) { System.out.println("Communication with server failed"); }
			public void onSuccess(final HashMap<String, String[][]> data) { 
				IsoCodes = data.get("IsoCode");
				
				populationData = data.get("population");
				
				if (config.getSelectedDataSeriesList().contains("1")) {
					productionresult = data.get("production");
				}
				if (config.getSelectedDataSeriesList().contains("2")) {
					importresult = data.get("production");
				}
				if (config.getSelectedDataSeriesList().contains("3")) {
					exportresult = data.get("production");
				}
				//new config: delete former Map
				RootPanel.get("visualizationContainer").clear();
				draw();
			}	
		});	
	}
	
	
	@Override
	public void draw() {
		VisualizationUtils.loadVisualizationApi(
				new Runnable() {
					public void run() {
					
						if (config.getSelectedDataSeriesList().contains("1")) {
							drawGeoMap("Production", productionresult);
							
						}
						if (config.getSelectedDataSeriesList().contains("2")) {
							drawGeoMap("Import", importresult);
							
						}
						if (config.getSelectedDataSeriesList().contains("3")) {
							drawGeoMap("Export", exportresult);
							
						}		
					}	
				}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
				Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
				ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
				MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
				ImageSparklineChart.PACKAGE);	
	}
	

	private void drawGeoMap(String dataSerie, String result[][]){
		//create GeoMap
		GeoMap.Options options = GeoMap.Options.create();
		options.setRegion("world");
		options.setShowLegend(true);
		options.setSize(1000, 500);
		
		//add data to GeoMap
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, dataSerie);
		data.addColumn(ColumnType.NUMBER, dataSerie+ " per capita");
		
		int sumAllData = 0;
		int counter = 0;
		
		for (int j = 0; j<IsoCodes.length; j++){
			//if the selected Area is a country:
			if (!(IsoCodes[j][2].equals(".."))){
				//gather value of data
				for (int i= 0; i< result.length; i++){
					if (result[i][0].equals(config.getSelectedAreaList().get(j))){
						if (!(result[i][3].isEmpty())){ //get rid of exceptions
							//compare it with population
							for (int y = 0; y< populationData.length; y++){
								if(populationData[y][1].equals(result[i][1])){
									//add up all dataValues
									int valueAsDouble = Integer.valueOf(result[i][3]);
									int populationAsDouble = Integer.valueOf(populationData[y][2]);
									sumAllData = sumAllData + (valueAsDouble/populationAsDouble);
									
								}
							}
						}
					}
				}

				//add selected country with value of sumAllData. If there is no data, sumAllData = 0
				data.addRow();
				data.setValue(counter, 0, IsoCodes[j][2]);
				data.setValue(counter, 1 , sumAllData);
				sumAllData = 0;
				counter++;
				
			}
		}
		
		//gather selected years and items for the comment underneath the visualization
		String allSelectedYears = "";
		for (int i = 0; i<config.getSelectedYearsList().size(); i++){
			allSelectedYears = (allSelectedYears.concat(config.getSelectedYearsList().get(i)));	
			if (i == config.getSelectedYearsList().size() -2){
				allSelectedYears = allSelectedYears + " and ";
			}
			else if (i == config.getSelectedYearsList().size()-1){
				allSelectedYears = allSelectedYears + " ";	
			}else{
				allSelectedYears = allSelectedYears + ", ";
			}
		}
		String allSelectedItems = "";
		for (int i = 0; i< config.getSelectedItemNameList().size(); i++){
			allSelectedItems = (allSelectedItems.concat(config.getSelectedItemNameList().get(i)));	
				if (i == config.getSelectedItemNameList().size() -2){
					allSelectedItems = allSelectedItems + " and ";
				}
				else if (i == config.getSelectedItemNameList().size()-1){
					allSelectedItems = allSelectedItems + " ";	
				}else{
					allSelectedItems = allSelectedItems + ", ";
				}
		}
		//HTML space = new HTML("");
		HTML text = new HTML(dataSerie+ " per capita" +
		" of " + allSelectedItems + "in " + allSelectedYears + ":");

		GeoMap widget = new GeoMap(data, options);
		//RootPanel.get("visualizationContainer").add(space);
		RootPanel.get("visualizationContainer").add(text);
		//RootPanel.get("visualizationContainer").add(space);
		RootPanel.get("visualizationContainer").add(widget);
		
	}
}