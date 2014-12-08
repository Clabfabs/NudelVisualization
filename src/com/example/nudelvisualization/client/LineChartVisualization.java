package com.example.nudelvisualization.client;

import java.util.HashMap;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
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
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;

public class LineChartVisualization extends Visualization{
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);
	
	private String[][] result = null;
	private int tenYearsForecast;
	
	public LineChartVisualization(Configuration config) {
		super(config);
		initialize();
	}

	private float[] getForecastValues(int length, int start){

		//calculating linear regression for forecast
		float  betaHat0 = 0, betaHat1  = 0;
		int n = length;
		float Exy = 0, Ex = 0, Ey = 0, Ex2 = 0;
		float yAverage = 0, xAverage =0;
		float[] forecastValues = new float[3];
		
		//calculate Ex
		for(int i =1; i<n+1; i++){
			Ex = Ex + i;
		}
		System.out.print("Ex: " + Ex);
		
		//calculate Ey
		for(int i=0;i<n;i++){
			Ey = Ey + Float.parseFloat(result[i + start][4]);
		}
		System.out.print("Ey: " + Ey);
		
		//calculate Exy
		for(int i=1;i<n+1;i++){
			Exy = Exy + (i * Float.parseFloat(result[(i-1) + start][4]));
		}
		System.out.print("Exy: " + Exy);
		
		//calculate Ex2
		for(int i=1;i<n+1;i++){
			Ex2 = Ex2 + (i * i);
		}
		System.out.print(" Ex2: " + Ex2);
		
		//calculate betaHat1
		betaHat1 = ((n*Exy)-(Ex*Ey))/((n*Ex2)-(Ex*Ex));
		System.out.print(" betaHat1: " + betaHat1);
		
		//calculate yAverage
		float ySum = 0;
		for(int i=0; i<n;i++){
			ySum = ySum +  Float.parseFloat(result[i + start][4]);
		}
		yAverage = ySum / n;
		System.out.print(" yAverage: " + yAverage);
		
		//calculate xAverage
		float xSum=0;
		for(int i=1; i<n+1;i++){
			xSum = xSum + i;
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
				if(config.getSelectedDataSeriesList().contains("1")){
				result = data.get("production");
				}
				else if(config.getSelectedDataSeriesList().contains("2")){
					result = data.get("import");
				}
				else if(config.getSelectedDataSeriesList().contains("3")){
					result = data.get("export");
				}
				//to do: window message
				else{
					System.out.println("No dataseries selected. Please select production, import or export.");
				}
				draw();
			}	
		});	
	}
	private void createLineChart(){
		
	}
	
	@Override
	public void draw() {
			VisualizationUtils.loadVisualizationApi(
					new Runnable() {
						public void run() {
							System.out.println("result:");
							for (int i = 0; i < result.length; i++) {
								for (int j = 0; j < result[i].length; j++) {
									System.out.print(result[i][j] + "\t");
								}
								System.out.println("\n");
							}
								RootPanel.get("visualizationContainer").clear();
								
								String currentArea = "";
								int row = 0;
								for(int i=0;i<result.length; i++){
									if(!(currentArea.equals(result[i][1]))){
										currentArea = result[i][1];
								String currentItem = null;	
								
								Options options = LineChart.createOptions();
								options.setHeight(300);
								options.setWidth(1000);
								options.setTitle(result[row][1]);
	    						// item = 2, year = 3, Value = 4
	    						DataTable data = DataTable.create();
	    						data.addColumn(ColumnType.STRING, "Years");
	    						data.addRows(25);
	    						
	    						//TO DO: ADDING ALL YEARS
	    						for(int x=0; x<10; x++){
	    						//data.setValue(x,0,config.getSelectedYearsList.get(x));
	    						data.setValue(0,0,"1990");
	    						data.setValue(1,0,"1991");
	    						data.setValue(2,0,"1992");
	    						data.setValue(3,0,"1993");
	    						data.setValue(4,0,"1994");
	    						data.setValue(5,0,"1995");
	    						data.setValue(6,0,"1996");
	    						data.setValue(7,0,"1997");
	    						data.setValue(8,0,"1998");
	    						data.setValue(9,0,"1999");
	    						data.setValue(10,0,"2000");
	    						data.setValue(11,0,"2001");
	    						data.setValue(12,0,"2002");
	    						data.setValue(13,0,"2003");
	    						data.setValue(14,0,"2004");
	    						data.setValue(15,0,"2005");
	    						data.setValue(16,0,"2006");
	    						data.setValue(17,0,"2007");
	    						data.setValue(18,0,"2008");
	    						data.setValue(19,0,"2009");
	    						data.setValue(20,0,"2010");
	    						data.setValue(21,0,"2011");
	    						}
	    						//adding Years to the x-Axis of the LineChart
	    						
	    						int ROWCOUNTER = 0;
	    						for(int y = 0; y < result.length; y++){
	    							if(result[y][1].equals(currentArea)){
	    								ROWCOUNTER++;
	    							}
	    						}
	    						if(result != null){
	    						//creating Linechart for Production, Import and Export
	    						// data.addRows(YEARCOUNTER);
	    							int start = 0;
	    							int columnCounter=0;
	    							int itemCounter = 0;
	    							// adding items in different colors to the LineChart
	    							for(int r = 0; r<ROWCOUNTER; r++){
	    								if(!result[row][2].equals(currentItem)){
	    									data.addColumn(ColumnType.NUMBER, result[row][2]);
	    									currentItem = result[row][2];
	    									itemCounter++;
	    									columnCounter = 0;
	    									start = row;
	    								}
	    								//calculates the years entrypoint for Values
	    								columnCounter = (Integer.parseInt(result[row][3])-1990);
	    								//adding values
	    								data.setCell(columnCounter, itemCounter, result[row][4], null, null);
	    								columnCounter++;
	    								row++;
	    								if(columnCounter % 22 == 0){
    	    								
    	    								float[] forecastValues;
    	    								int n = ROWCOUNTER;
    	    								forecastValues = getForecastValues((row - start), start);
    		    							for(int b=0; b<3;b++){
    		    								
    		    								//int currentYear = (Integer.parseInt(result[row-1][3]) + b +1);
    		    								data.setValue(columnCounter, 0, "in " + (b+ 1) + "y");
    		    								data.setCell(columnCounter, itemCounter, (int)forecastValues[b], null, null);
    		    								columnCounter++;
    		    							}
    	    							}
	    							}	
	    						LineChart widget = new LineChart(data, options);
	    						RootPanel.get("visualizationContainer").add(widget);
						}
						
						else{
							System.out.println("no avaialable data");
							
						}
									}
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



