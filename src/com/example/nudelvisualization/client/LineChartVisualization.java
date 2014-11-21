package com.example.nudelvisualization.client;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
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
import com.google.gwt.user.client.ui.RootPanel;


public class LineChartVisualization extends Visualization{
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);
	
	private String currentItem = null;
	private String currentArea = null;
	private String[][] result = null;
	
	public LineChartVisualization(Configuration config) {
		super(config);
		initialize();
	}

	private void initialize() {
		dataAccessSocket.getDataForLineChart(config, new AsyncCallback<HashMap<String, String[][]>>() {
			public void onFailure(Throwable caught) { System.out.println("Communication with server failed"); }
			public void onSuccess(final HashMap<String, String[][]> data) { 
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
							System.out.println("result:");
							for (int i = 0; i < 5 && i < result.length; i++) {
								for (int j = 0; j < result[i].length; j++) {
									System.out.print(result[i][j] + "\t");
								}
								System.out.println("\n");
							}
							
	    						ImageLineChart.Options options = ImageLineChart.Options.create();
	    						options.setSize(1000, 450);
	    						// 2= areacode, 3 = area, 5 = dataserie, 7=item, 8=year, 10 =value
	    						DataTable data = DataTable.create();
	    						
	    						//adding Years to the x-Axis of the LineChart
	    						data.addColumn(ColumnType.STRING, "Years");
	    						
	    						//calculating linear regression for forecast
	    						int forecastValue, betaHat0, betaHat1  = 0;
	    						int xi, n = 0;
	    						int Exy, Ex, Ey,Ex2, Ey2 = 0;
	    						int y = 1;
	    						
	    						//calculate Ex
	    						for(int i =0; i<n; i++){
	    							
	    						}
	    						
	    						// to do: eventually change 22 it to result.length
	    						if(result.length!=0){
	    						data.addRows(22);
	    						if(result[0][5].equals("Total Population - Both sexes")){
	    							//POPULATION START
	    							int f=0;
	    							int j=0;
	    							// adding Areas in different colors to the LineChart
	    							for(int i=0; i<result.length; i++){
	    								if(!result[i][3].equals(currentArea)){
	    									data.addColumn(ColumnType.NUMBER, result[i][3]);
	    									currentArea = result[i][3];
	    									j++;
	    									f = 0;
	    								}
	    								//adding Years to x-Axis
	    								data.setValue(f, 0, result[i][8]);
	    								//adding values
	    								data.setCell(f, j, result[i][10], null, null);
	    								f++;
	    							}
	    							//POPULATION END
	    						}
	    						
	    						else if(result[0][5].equals("Production") || result[0][5].equals("Export Quantity") || result[0][5].equals("Import Quantity")){
	    							// PRODUCTION / IMPORT / EXPORT START
	    							int f=0;
	    							int j = 0;
	    							// adding items in different colors to the LineChart
	    							for(int i=0; i<result.length; i++){
	    								if(!result[i][7].equals(currentItem)){
	    									data.addColumn(ColumnType.NUMBER, result[i][7]);
	    									currentItem = result[i][7];
	    									j++;
	    									f = 0;
	    								}
	    								data.addColumn(ColumnType.NUMBER, "Vorhersage");
	    						
	    								//adding Years to x-Axis
	    								data.setValue(f, 0, result[i][8]);
	    								//adding values
	    								data.setCell(f, j, result[i][10], null, null);
	    								f++;
	    							}
	    						}
	    						//PRODUCTION / IMPORT / EXPORT END
	    						
	    						ImageLineChart widget = new ImageLineChart(data, options);
	    						RootPanel.get("visualizationContainer").clear();
	    						RootPanel.get("visualizationContainer").add(widget);
	    					}
						
						else{
							System.out.println("no avaialable data");
							
						}
						}
	    				}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
	    				Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
	    				ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
	    				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
	    				MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
	    				ImageSparklineChart.PACKAGE);
					
		}
	}



