package com.example.nudelvisualization.client;

import com.google.gwt.user.client.ui.RootPanel;
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
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

/*
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IntensityMapVisualization extends Visualization{

  // GWT module entry point method.
  

	public IntensityMapVisualization(Configuration config) {
		super(config);
	}

	@Override
	public void draw() {
	    VisualizationUtils.loadVisualizationApi(
		        new Runnable() {
		          public void run() {
		            draw2();
		          }
		        }, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
		        Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
		        ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
		        ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
		        MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,
		        ImageSparklineChart.PACKAGE);

		  }

		  private void draw2() {

		    ImageChart.Options options = ImageChart.Options.create();
		    options.set("chs", "400x400");
		    options.set("cht", "qr");
		    options.set("chl", "Hello+world");

		    DataTable dataTable = DataTable.create();
		    ImageChart widget = new ImageChart(dataTable, options);

		    RootPanel.get("sampleMap").add(widget);

		    System.out.println("finished");
		  }	
		
		
	}
	
	

