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

	private int[] getForecastValues(){

		//calculating linear regression for forecast
		int  betaHat0 = 0, betaHat1  = 0;
		int n = result.length;
		int Exy = 0, Ex = 0, Ey = 0, Ex2 = 0;
		int yAverage = 0, xAverage =0;
		int[] forecastValues = new int[3];
		
		//calculate Ex
		for(int i =1; i<n+1; i++){
			Ex += i;
		}
		System.out.print("Ex: " + Ex);
		
		//calculate Ey
		for(int i=0;i<n;i++){
			Ey += Integer.parseInt(result[i][4]);
		}
		System.out.print("Exy: " + Exy);
		
		//calculate Exy
		for(int i=1;i<n+1;i++){
			Exy += (i + Integer.parseInt(result[i-1][4]));
		}
		System.out.print("Exy: " + Exy);
		
		//calculate Ex2
		for(int i=1;i<n+1;i++){
			Ex2 += (i * i);
		}
		System.out.print(" Ex2: " + Ex2);
		
		//calculate betaHat1
		betaHat1 = ((n*Exy)-(Ex*Ey))/((n*Ex2)-(Ex2*Ex2));
		System.out.print(" betaHat1: " + betaHat1);
		
		//calculate yAverage
		int ySum = 0;
		for(int i=0; i<n;i++){
			ySum += Integer.parseInt(result[i][4]);
		}
		yAverage = ySum / n;
		System.out.print(" yAverage: " + yAverage);
		
		//calculate xAverage
		int xSum=0;
		for(int i=1; i<n+1;i++){
			xSum += i;
		}
		xAverage = xSum / n;
		System.out.print(" xAverage: " + xAverage);
		
		//calculate betaHat0
		betaHat0 = (yAverage - (betaHat1 * xAverage));
		System.out.print(" bethat0: " + betaHat0);
		
		//calculate forecast
		for(int i = 0; i<3;i++){
			forecastValues[i] = betaHat0 + (betaHat1 * (i+ n+ 1));
			System.out.print(" forecastValues: " + i + " " + forecastValues[i]);
		}
		
		return forecastValues;
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
	    						options.setSize(1000, 350);
	    						// item = 2, year = 3, Value = 4
	    						DataTable data = DataTable.create();
	    						
	    						//adding Years to the x-Axis of the LineChart
	    						data.addColumn(ColumnType.STRING, "Years");
	    						
	    						
	    						if(result == null){
	    						//creating Linechart for Production, Import and Export
	    						data.addRows(25);

	    							int f=0;
	    							int j = 0;
	    							int g;
	    							// adding items in different colors to the LineChart
	    							for(g=0; g<result.length; g++){
	    								if(!result[g][2].equals(currentItem)){
	    									data.addColumn(ColumnType.NUMBER, result[g][2]);
	    									currentItem = result[g][2];
	    									j++;
	    									f = 0;
	    								}
	    								//adding Years to x-Axis
	    								data.setValue(f, 0, result[g][3]);
	    								//adding values
	    								data.setCell(f, j, result[g][4], null, null);
	    								f++;	
	    							}
	    							//adding forecast values
	    							if(j==1){
	    								int[] forecastValues;
	    								forecastValues = getForecastValues();
	    								data.addColumn(ColumnType.NUMBER, "Forecast");
	    								j++;
	    								data.setCell(f-1, j, result[g-1][4], null, null);
		    							for(int b=0; b<3;b++){
		    								f++;
		    								int currentYear = (Integer.parseInt(result[g-1][3]) + b + 1);
		    								data.setValue(f, 0, new Integer(currentYear).toString());
		    								data.setCell(f, j, forecastValues[b], null, null);	
		    							}
		    							
	    							}
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



