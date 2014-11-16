package com.example.nudelvisualization.client;




import java.util.Arrays;
import java.util.Hashtable;
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
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IntensityMapVisualization extends Visualization {

	// GWT module entry point method.
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	//Hashtable<String, String> IsoCodes = new Hashtable<String, String>();
	public IntensityMapVisualization(Configuration config) {
		super(config);
		//setIsoCodes();
	}
	/*private void setIsoCodes(){
		IsoCodes.put("1", "AM");
		IsoCodes.put("2", "AF");
		IsoCodes.put("3", "AL");
		IsoCodes.put("4", "DZ");
		System.out.println(IsoCodes.get("1"));
	}*/
	@Override
	public void draw() {
		dataAccessSocket.getSelectedRows(configToVisualize, new CallbackHandler());
	}

	
	
	private class CallbackHandler implements AsyncCallback<String[][]>{

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(final String[][] result) {

		    VisualizationUtils.loadVisualizationApi(
		    		new Runnable() {
		    			public void run() {
		    						IntensityMap.Options options = IntensityMap.Options.create();
		    						options.setRegion(IntensityMap.Region.WORLD);
		    						// 2= areacode, 3 = land, 5 = dataserie, 7=item, 8=year, 10 =value
		    						DataTable data = DataTable.create();
		    						data.addColumn(ColumnType.STRING, "Country");
		    						//data.addColumn(ColumnType.NUMBER, "Import");
		    						//data.addColumn(ColumnType.NUMBER, "Export");
		    						data.addColumn(ColumnType.NUMBER, result[1][5]);
		    						data.addColumn(ColumnType.NUMBER, result[1][8]);
		    						data.addRows(5);//Anzahl Länder
		    						data.setValue(0, 0, "GB");//(Land 1 2etc. , 0= Lädercode 1=import 2 = export 3= production, Wert)
		    						data.setValue(0, 1, 0);
		    						data.setValue(0, 2, 200);
		    						//data.setValue(0, 3 ,500);
		    						data.setValue(1, 0 ,"FR");
		    						data.setValue(1, 1, 50);
		    						data.setValue(1, 2, 300);
		    						//data.setValue(1, 3 ,100);
		    						data.setValue(2, 0, "CH");
		    						data.setValue(2, 1, 250);
		    						data.setValue(2, 2, 500);
		    						//data.setValue(2, 3 , 800);
		    						data.setValue(3, 0, "US");
		    						data.setValue(3, 1, 40);
		    						data.setValue(3, 2, 350);
		    						//data.setValue(3, 3 ,100);
		    						data.setValue(4, 0, "RU");
		    						data.setValue(4, 1, 30);
		    						data.setValue(4, 2, 10);
		    						//data.setValue(4, 3 ,500);
		    						
		    						IntensityMap widget = new IntensityMap(data, options);
		    						RootPanel.get("visualizationContainer").clear();
		    						RootPanel.get("visualizationContainer").add(widget);
		    					}
		    				}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
		    				Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
		    				ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
		    				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
		    				MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
		    				ImageSparklineChart.PACKAGE);		
		    		
		    	}
		    }
		    	   
		
	}
		




