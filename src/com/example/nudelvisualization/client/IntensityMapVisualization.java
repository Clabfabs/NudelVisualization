package com.example.nudelvisualization.client;

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
				MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
				ImageSparklineChart.PACKAGE);

	}

	private void draw2() {

		IntensityMap.Options options = IntensityMap.Options.create();
		options.setRegion(IntensityMap.Region.WORLD);

		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "3", "Armenia");
		data.addColumn(ColumnType.NUMBER, "Bla");
		data.addRows(2);
		data.setValue(0, 1, 10);

		data.setValue(1, 1, 0);

		IntensityMap widget = new IntensityMap(data, options);
		RootPanel.get("visualizationContainer").clear();
		RootPanel.get("visualizationContainer").add(widget);
	}	
}



