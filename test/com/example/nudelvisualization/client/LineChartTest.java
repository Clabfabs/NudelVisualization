package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineChartTest {
	
	/* JUnittest does not work because all methods are accessing data from the server.
	 * We cannot simulate those circumstances.
	 * In addition: We are using here a DataTable dataTable which is required for our GWT Visualization. 
	 * However, this class consists of native methods that only work when the app is
	 * running, which is not the case in a test. That is the reason why GWT can
	 * not provide us these methods. This leads us to the fact that it's going
	 * to be difficult to test this function...
	 * We will only test the forecast method. 
	 */ 
	
	@Test
	public void testCalculateEx() {
		//for n = 3 and Ex = 0
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
		
		float test = 3;
		LineChartVisualization lineChart = new LineChartVisualization(config);
		assertEquals(test, lineChart.calculateEx(0, 3), 3);
	}
	
	@Test 
	public void testCalculateEy(){
		//for n = 3 and Ex = 0
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
		
		String[][] result =  new String [3][5];
		result[0][4] = "200";
		result[1][4] = "300";
		result[2][4] = "400";

		
		
		float test = 900;
		LineChartVisualization lineChart = new LineChartVisualization(config);
		assertEquals(test, lineChart.calculateEy(0, 3, 0, result), 3);
		
	}
	@Test 
	public void testCalculateExy(){
		//for n = 3 and Ex = 0
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
		
		String[][] result =  new String [3][5];
		result[0][4] = "200";
		result[1][4] = "300";
		result[2][4] = "400";

		
		
		float test = 2000;
		LineChartVisualization lineChart = new LineChartVisualization(config);
		assertEquals(test, lineChart.calculateExy(result, 0, 3, 0), 3);
		
	}

}
