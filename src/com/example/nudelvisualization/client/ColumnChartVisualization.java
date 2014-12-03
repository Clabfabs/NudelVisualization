package com.example.nudelvisualization.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.example.nudelvisualization.shared.TripleHashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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
	TripleHashMap dataTriple = null;

	public ColumnChartVisualization(Configuration config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	public String[][] fillTable(HashMap<String, List<String[]>> hashmap, Configuration config, String areaName) {
		int column = config.getSelectedItemsList().size();
		int row = config.getSelectedYearsList().size();
		String[][] result = new String[row][column + 1];
		List<String[]> areaYearItemList = hashmap.get(areaName);

		for (int y = 0; y < row; y++) {
			String year = config.getSelectedYearsList().get(y);

			for (int it = 0; it < column; it++) {
				String item = config.getSelectedItemNameList().get(it);

				if (hashmap.containsKey(areaName)) {
					for (String[] yearItems : areaYearItemList) {
						if (yearItems[0].equals(year) && yearItems[1].equals(item)) {
							result[y][0] = config.getSelectedYearsList().get(y);
							result[y][it + 1] = yearItems[2];
						}
					}
				}
			}
		}
		return result;
	}

	public void initialize() {
		AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);
		dataAccessSocket.getDataForColumnChart(config, new AsyncCallback<TripleHashMap>() {
			public void onFailure(Throwable caught) {
				System.out.println("Communication with server failed");
			}

			@Override
			public void onSuccess(TripleHashMap result) {
				// TODO Auto-generated method stub
				dataTriple = result;
				draw();

			}
		});
	}

	@Override
	public void draw() {
		VisualizationUtils.loadVisualizationApi(new Runnable() {
			public void run() {
				List<HashMap<String, List<String[]>>> hashMapArray = new ArrayList<HashMap<String, List<String[]>>>();
				hashMapArray.add(dataTriple.getHashMapProduction());
				hashMapArray.add(dataTriple.getHashMapImport());
				hashMapArray.add(dataTriple.getHashMapExport());
				int counter = 0;
				HorizontalPanel framePanel = new HorizontalPanel();
				RootPanel.get("visualizationContainer").clear();
				for (HashMap<String, List<String[]>> hashMap : hashMapArray) {
					if (hashMap == null){
						System.out.println("Hashmap null.");
						counter++;
					}else{

					int nrOfArea = config.getSelectedAreaList().size();
					int nrOfItems = config.getSelectedItemsList().size();
					int nrOfYears = config.getSelectedYearsList().size();

			
					
					VerticalPanel columnChartPanel = new VerticalPanel();

					for (String areaName : config.getSelectedAreaNameList()) {
						List<String[]> areaYearItemList = hashMap.get(areaName);

						Options options = ColumnChart.createOptions();
						options.setHeight(250);
						options.setWidth(600);
						options.setTitle(areaName);

						DataTable dataTable = DataTable.create();
						dataTable.addColumn(ColumnType.STRING, "Year");
						for (int c = 0; c < nrOfItems; c++) {
							dataTable.addColumn(ColumnType.NUMBER, config.getSelectedItemNameList().get(c));
						}
						// insert rows
						dataTable.addRows(nrOfYears);

						String[][] table = new String[nrOfYears][nrOfItems + 1];
						table = fillTable(hashMap, config, areaName);
						// fill dataTable based on table
						for (int i = 0; i < nrOfYears; i++) {
							for (int j = 0; j <= nrOfItems; j++) {
								dataTable.setValue(i, j, table[i][j]);
							}
						}
						ColumnChart colChart = new ColumnChart(dataTable, options);
						
						columnChartPanel.add(colChart);
					}
					Label title = null;
					if(counter==0){
						title = new Label("Production");
						title.addStyleDependentName("titleColumnChartPanel");
						title.addStyleName("gwt-Label");
					}else if(counter == 1){
						title = new Label("Import");
					}else if (counter == 2 ){
						title = new Label("Export");
					}
					counter++;
					framePanel.add(title);
					framePanel.add(columnChartPanel);

				}
				RootPanel.get("visualizationContainer").add(framePanel);
			}
				addSource();
			}
		}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE, Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE, ImageLineChart.PACKAGE,
				ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE, ImagePieChart.PACKAGE, IntensityMap.PACKAGE, MapVisualization.PACKAGE,
				MotionChart.PACKAGE, OrgChart.PACKAGE, Table.PACKAGE, ImageSparklineChart.PACKAGE);

	}
	
	
}
