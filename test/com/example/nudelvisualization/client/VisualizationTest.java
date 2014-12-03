package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class VisualizationTest {

	@Test
	public void testgetSelectedAreaID() {
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<String> visualizationArraylist = new ArrayList<String>();

		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		test = config.getSelectedAreaList();
		
		Visualization vis = new ColumnChartVisualization(config);
		visualizationArraylist= vis.getAreaIDs();
		
		assertEquals(test,visualizationArraylist);
		
	
	}
	@Test
	public void testgetSelectedItemsID() {
		ArrayList<String> test = new ArrayList<String>();
		ArrayList<String> visualizationArraylist = new ArrayList<String>();

		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		test = config.getSelectedItemsList();
		
		Visualization vis = new ColumnChartVisualization(config);
		visualizationArraylist= vis.getItemIDs();
		
		assertEquals(test,visualizationArraylist);
		
	
	}

}
