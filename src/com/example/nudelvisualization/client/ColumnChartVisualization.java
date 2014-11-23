package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ibm.icu.util.BytesTrie.Result;

public class ColumnChartVisualization extends Visualization {
	private final AccessDatabaseAsync dataAccessSocket = GWT
			.create(AccessDatabase.class);
	HashMap<String, List<String[]>> data = null;

	public ColumnChartVisualization(Configuration config) {
		super(config);
		initialize();
		// TODO Auto-generated constructor stub
	}

	private void initialize() {
		dataAccessSocket.getDataForColumnChart(config,
				new AsyncCallback<HashMap<String, List<String[]>>>() {
					public void onFailure(Throwable caught) {
						System.out.println("Communication with server failed");
					}

					@Override
					public void onSuccess(HashMap<String, List<String[]>> result) {
						// TODO Auto-generated method stub
						data = result;
						draw();

					}
				});
	}

	@Override
	public void draw() {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			public void run() {

				int nrOfArea = config.getSelectedAreaList().size();
				int nrOfItems = config.getSelectedItemsList().size();
				int nrOfYears = config.getSelectedYearsList().size();

				RootPanel.get("visualizationContainer").clear();

				for (String areaName : config.getSelectedAreaNameList()) {
					List<String[]> areaYearItemList = data.get(areaName);
					
					Options options = ColumnChart.createOptions();
					options.setHeight(250);
					options.setWidth(600);
					options.setTitle(areaName);

					DataTable dataTable = DataTable.create();
					dataTable.addColumn(ColumnType.STRING, "Year");
					for (int c = 0; c<nrOfItems; c++){
						dataTable.addColumn(ColumnType.NUMBER,config.getSelectedItemNameList().get(c));
					}
					// insert rows
					dataTable.addRows(nrOfYears);
					// insert Years in first column of dataTable
					 for (int j = 0; j < nrOfYears; j++) {
							dataTable.setValue(j, 0, config.getSelectedYearsList().get(j));
							}
					 //insert production amount for Items in dataTable. We check whether a item in a particular country and year is produced.
					
						for (int y = 0; y < nrOfYears; y++){
							String year = config.getSelectedYearsList().get(y);
							
							for (int it = 0; it < nrOfItems; it++){
								String item = config.getSelectedItemNameList().get(it);
								
								dataTable.setValue(y, it+1, 0);
								
								if(data.containsKey(areaName)){
									for (String[] yearItems : areaYearItemList){
										if( yearItems[0].equals(year) && yearItems[1].equals(item) ){
											dataTable.setValue(y, it+1, yearItems[2]);
										}
									}
								}
							}
						}

					ColumnChart colChart = new ColumnChart(dataTable,
							options);
					RootPanel.get("visualizationContainer").add(
							colChart);
				}
				
				 }
			
		}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE, Gauge.PACKAGE,
				GeoMap.PACKAGE, ImageChart.PACKAGE, ImageLineChart.PACKAGE,
				ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
				MapVisualization.PACKAGE, MotionChart.PACKAGE,
				OrgChart.PACKAGE, Table.PACKAGE, ImageSparklineChart.PACKAGE);

	}

}
