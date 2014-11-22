package com.example.nudelvisualization.client;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
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

public class ColumnChartVisualization extends Visualization {
	private final AccessDatabaseAsync dataAccessSocket = GWT
			.create(AccessDatabase.class);
	String[][] result = null;

	public ColumnChartVisualization(Configuration config) {
		super(config);
		initialize();
		// TODO Auto-generated constructor stub
	}

	private void initialize() {
		dataAccessSocket.getDataForColumnChart(config,
				new AsyncCallback<String[][]>() {
					public void onFailure(Throwable caught) {
						System.out.println("Communication with server failed");
					}

					public void onSuccess(String[][] data) {
						result = data;
						draw();
					}
				});
	}

	@Override
	public void draw() {
		VisualizationUtils.loadVisualizationApi(
				new Runnable() {
					public void run() {

						int nrOfArea = config.getSelectedAreaList().size();
						int nrOfItems = config.getSelectedItemsList().size();
						int nrOfYears = config.getSelectedYearsList().size();

						// 2= areacode, 3 = land, 5 = dataserie, 7=item, 8=year,
						// 10
						// =value
						// ArrayList<String>lands = new ArrayList<String>();
						// String land = result[1][2];
						// lands.add(land);
						// for(int i = 1; i<result.length;i++){
						// if(land.equals(result[i][2])== false){
						// lands.add(result[i][2]);
						// land = result[]
						// }
						// }
						RootPanel.get("visualizationContainer").clear();

						for (int i = 0; i < nrOfArea; i++) {

							Options options = ColumnChart.createOptions();
							options.setHeight(250);
							options.setWidth(550);
							options.setTitle(result[(i * nrOfItems * nrOfYears)+1][0]);

							DataTable data = DataTable.create();
							data.addColumn(ColumnType.STRING, "Year");
							String columnName = null;
							for (int c = 0; c < nrOfItems; c++) {
								columnName = result[c * nrOfYears + 1][2];
								data.addColumn(ColumnType.NUMBER, columnName);
							}

							data.addRows(nrOfYears);

							for (int j = 0; j < nrOfYears; j++) {
								data.setValue(j, 0, config
										.getSelectedYearsList().get(j));
							}
							int counter = i * nrOfYears * nrOfItems;
							for (int n = 1; n <= nrOfItems; n++) {
								for (int m = 0; m < nrOfYears; m++) {
									//if (result[counter][1].equals(data
									//		.getValueString(m, 0))) {
										data.setValue(m, n, result[counter][3]);
										counter++;
									//} else {
									//	data.setValue(m, n, 0);
									//	counter++;
									//}
								}
							}
							//
							//
							ColumnChart colChart = new ColumnChart(data,
									options);
							RootPanel.get("visualizationContainer").add(
									colChart);
						}

						// data.addColumn(ColumnType.NUMBER, result[2][7]);
						// data.addRow();
						// data.setValue(0,0,result[1][8]);
						// data.setValue(0, 1, result[1][10]);
						// data.setValue(0, 2, result[2][10]);
						// data.addRow();
						// data.setValue(1, 0, result[2][8]);
						// data.setValue(1,1,111111);
						// data.setValue(1,2,111222);

						int counter = 0;

						// for (int i =2; i < result.length; i++) {
						// data.addRow();
						// //data.setValue(0,counter, result[i][3]);
						// data.setValue( 0, counter, result[i][8]);
						//
						// data.setValue( 2, counter,
						// Integer.parseInt(result[i][10]));
						// counter++;
						//
						// }
						//
					}
				}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE, Gauge.PACKAGE,
				GeoMap.PACKAGE, ImageChart.PACKAGE, ImageLineChart.PACKAGE,
				ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
				MapVisualization.PACKAGE, MotionChart.PACKAGE,
				OrgChart.PACKAGE, Table.PACKAGE, ImageSparklineChart.PACKAGE);

	}

}
