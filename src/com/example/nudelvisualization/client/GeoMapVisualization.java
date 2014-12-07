package com.example.nudelvisualization.client;


import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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

	String [][] IsoCodes = null;
	String [][] productionresult = null;
	String [][] populationData = null;
	String [][] importresult = null;
	String [][] exportresult = null;
	ArrayList <Image> yearsButton = new ArrayList <Image>();
	
	
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
				
				RootPanel.get("visualizationContainer").clear();
				IsoCodes = data.get("IsoCode");
				boolean noCountryArea = checkIsoCode(IsoCodes); 
				if (noCountryArea){
				Window.alert("You have chosen regions which are not representable. Please chose actual countries.");
					
				}else{
				populationData = data.get("population");
				
				if (config.getSelectedDataSeriesList().contains("1")) {
					productionresult = data.get("production");
				}
				if (config.getSelectedDataSeriesList().contains("2")) {
					importresult = data.get("import");
				}
				if (config.getSelectedDataSeriesList().contains("3")) {
					exportresult = data.get("export");
				}
				
				draw();
				}
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
		options.setSize(990, 495);
		options.setShowLegend(true);
		
		//add data to GeoMap
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, dataSerie);
		data.addColumn(ColumnType.NUMBER, dataSerie+ " per capita");
		
		int sumAllData = 0;
		int counter = 0;
		
		for (int j = 0; j<IsoCodes.length; j++){
			//if the selected Area is a country:
			if (!(IsoCodes[j][1].equals(".."))){
				//gather value of data
				sumAllData = getSumAllData(result, j, config, populationData);
//				for (int i= 0; i< result.length; i++){
//					if (result[i][0].equals(config.getSelectedAreaList().get(j))){
//						if (!(result[i][3].isEmpty())){ //get rid of exceptions
//							//compare it with population
//							for (int y = 0; y< populationData.length; y++){
//								if(populationData[y][1].equals(result[i][1])){
//									//add up all dataValues
//									int valueAsInt = Integer.valueOf(result[i][3]);
//									int populationAsInt = Integer.valueOf(populationData[y][2]);
//									sumAllData = sumAllData + (valueAsInt/populationAsInt);
//								}
//							}
//						}
//					}
//				}
				if (IsoCodes[j][1].equals("CD") || IsoCodes[j][1].equals("CG") || IsoCodes[j][1].equals("CI") || IsoCodes[j][1].equals("SS")){
					//add selected country with value of sumAllData. If there is no data, sumAllData = 0
					data.addRow();
					data.setValue(counter, 0, IsoCodes[j][1]);
					data.setValue(counter, 1 , sumAllData);
					sumAllData = 0;
					counter++;
				}else{
					//add selected country with value of sumAllData. If there is no data, sumAllData = 0
					data.addRow();
					data.setValue(counter, 0, IsoCodes[j][2]);
					data.setValue(counter, 1 , sumAllData);
					sumAllData = 0;
					counter++;
				}
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
		String noCountryAreas = "";
		for (int j = 0; j<IsoCodes.length; j++){
			//if the selected Area is not a country:
			if (IsoCodes[j][1].equals("..")){
				noCountryAreas = " (Some of the regions you have chosen are no actual countries. They are not represented.)";
				break;
		}
		}
		HTML text = new HTML(dataSerie+ " per capita" +
		" of " + allSelectedItems + "in " + allSelectedYears + noCountryAreas+ ":");

		GeoMap widget = new GeoMap(data, options);

		if (config.getSelectedYearsList().size() == 1 && config.getSelectedDataSeriesList().size() == 1) {
			createTimeline();
		}
		
		
		RootPanel.get("visualizationContainer").add(text);
		RootPanel.get("visualizationContainer").add(widget);
		addSource();
	}
	

	public boolean checkIsoCode(String[][]isoCodes){
		boolean noCountryArea = false;
		for (int j = 0; j<isoCodes.length; j++){
			//if the selected Area is not a country:
			if (isoCodes[j][1].equals("..")){
				noCountryArea = true;
			}else{
				noCountryArea = false;
				break;
		}
		}
		return noCountryArea;
	}
	
	public int getSumAllData(String [][] result, int j, Configuration config, String[][] populationData){
		int sumAllData=0;
		for (int i= 0; i< result.length; i++){
			if (result[i][0].equals(config.getSelectedAreaList().get(j))){
				if (!(result[i][3].isEmpty())){ //get rid of exceptions
					//compare it with population
					for (int y = 0; y< populationData.length; y++){
						if(populationData[y][1].equals(result[i][1])){
							//add up all dataValues
							int valueAsInt = Integer.valueOf(result[i][3]);
							int populationAsInt = Integer.valueOf(populationData[y][2]);
							sumAllData = sumAllData + (valueAsInt/populationAsInt);
						}
					}
				}
			}
		}
		return sumAllData;
	}
	
	
	public void createTimeline(){
		int startYear = 1990;
		int endYear = 2011;

		for (int i = startYear; i <= endYear;i++){
			if (config.getSelectedYearsList().size() == 1 && Integer.toString(i).equals(config.getSelectedYearsList().get(0))){
				yearsButton.add(new Image("timelineimages/" + Integer.toString(i)+"r.png"));	
			}else{
				yearsButton.add(new Image("timelineimages/" + Integer.toString(i)+".png"));
			}
		}
		
		for (int j = 0; j<yearsButton.size(); j++){
			yearsButton.get(j).setPixelSize(45, 58);	
		}
	
		yearsButton.get(0).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1990");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(1).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1991");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(2).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1992");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(3).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1993");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(4).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1994");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(5).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1995");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(6).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1996");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(7).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1997");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(8).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1998");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(9).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("1999");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(10).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2000");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(11).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2001");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(12).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2002");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(13).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2003");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(14).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2004");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(15).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2005");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(16).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2006");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(17).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2007");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(18).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2008");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(19).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2009");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(20).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2010");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		yearsButton.get(21).addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				config.getSelectedYearsList().clear();
				config.addYear("2011");
				GeoMapVisualization newMap = new GeoMapVisualization(config);
				newMap.initialize();
			}
		});
		
		HorizontalPanel timeline = new HorizontalPanel();
		for (int y = 0; y<yearsButton.size(); y++){
			timeline.add(yearsButton.get(y));
		}
		timeline.addStyleName("timeline");
		RootPanel.get("visualizationContainer").add(timeline);
	}
}
