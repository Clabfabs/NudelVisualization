package com.example.nudelvisualization.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

public class LineChartVisualization extends Visualization{
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);
	
	public LineChartVisualization(Configuration config) {
		super(config);
	}

	@Override
	public void draw() {
		//dataAccessSocket.getSelectedRows(config, new CallbackHandler());
		VisualizationUtils.loadVisualizationApi(
				new Runnable() {
					public void run() {
    						ImageLineChart.Options options = ImageLineChart.Options.create();
    						options.setSize(1000, 500);
    						// 2= areacode, 3 = land, 5 = dataserie, 7=item, 8=year, 10 =value
    						DataTable data = DataTable.create();
    						
    						//adding Years to the x-Axis of the LineChart
    						data.addColumn(ColumnType.STRING, "Years");
    						data.addColumn(ColumnType.NUMBER, "Years");
    						data.addColumn(ColumnType.NUMBER, "Years");
    						data.addColumn(ColumnType.NUMBER, "MONGO");
    						data.addColumn(ColumnType.NUMBER, "BILLY");
    						
    						data.addRows(22);
    						data.setValue(0, 0, "1990");
    						data.setValue(1, 0, "1991");
    						data.setValue(2, 0, "1992");
    						data.setValue(3, 0, "1993");
    						data.setValue(4, 0, "1994");
    						data.setValue(5, 0, "1995");
    						data.setValue(6, 0, "1996");
    						data.setValue(7, 0, "1997");
    						data.setValue(8, 0, "1998");
    						data.setValue(9, 0, "1999");
    						data.setValue(10, 0, "2000");
    						data.setValue(11, 0, "2001");
    						data.setValue(12, 0, "2002");
    						data.setValue(13, 0, "2003");
    						data.setValue(14, 0, "2004");
    						data.setValue(15, 0, "2005");
    						data.setValue(16, 0, "2006");
    						data.setValue(17, 0, "2007");
    						data.setValue(18, 0, "2008");
    						data.setValue(19, 0, "2009");
    						data.setValue(20, 0, "2010");
    						data.setValue(21, 0, "2011");
    						
    					//	String[][] result = new String[50][50];
    						
    						//adding items in different colors to the LineChart
    					//	for(int i=0; i<result.length; i++){
    					//		data.addColumn(ColumnType.NUMBER, result[i][7]);
    					//	}
    						
    						//adding values to the LineChart
    						//for(int i=0;i<result.length; i++){
    					//	data.setCell(0, 1, result[i][10], null, null);
    						data.setCell(0, 1, 100, null, null);
    						data.setCell(1, 1, 100, null, null);
    						data.setCell(2, 1, 200, null, null);
    						data.setCell(3, 1, 300, null, null);
    						data.setCell(4, 1, 400, null, null);
    						data.setCell(5, 1, 500, null, null);
    						data.setCell(5, 3, 500, null, null);
    						data.setCell(6, 3, 600, null, null);
    						data.setCell(7, 3, 700, null, null);
    						data.setCell(8, 3, 800, null, null);
    						//}
    						ImageLineChart widget = new ImageLineChart(data, options);
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
/*	
	private class CallbackHandler implements AsyncCallback<String[][]>{

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(final String[][] result) {
		}		
}
*/
