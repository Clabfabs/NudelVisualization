package com.example.nudelvisualization.client;

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

public LineChartVisualization(Configuration config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

@Override
public void draw() {
	VisualizationUtils.loadVisualizationApi(
    		new Runnable() {
    			public void run() {
    						ImageLineChart.Options options = ImageLineChart.Options.create();
    						//options.setSize(width, height);
    						// 2= areacode, 3 = land, 5 = dataserie, 7=item, 8=year, 10 =value
    						DataTable data = DataTable.create();
    						
    						
    	
    						
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
