package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TableVisualizationTest {
	
	@Test
	public void testgetRowsAsList() {
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

		String[][] resultTest = new String[2][6];
		resultTest[0][0]= "1";
		resultTest[0][1]= "Armenia";
		resultTest[0][2]= "Apple";
		resultTest[0][3]= "1992";
		resultTest[0][4]= "1000";
		resultTest[0][5]= "tonnes";
		resultTest[1][0]= "2";
		resultTest[1][1]= "Afghanistan";
		resultTest[1][2]= "Apple";
		resultTest[1][3]= "1993";
		resultTest[1][4]= "1200";
		resultTest[1][5]= "tonnes";
		List<String[]> rowsL = new ArrayList<String[]>();
		
		List<String[]> rowsLTest = new ArrayList<String[]>();
		String[] testArray1 = {"1", "Armenia", "Apple", "1992", "1000","tonnes"};
		String[] testArray2 = {"2", "Afghanistan", "Apple", "1993", "1200","tonnes"};
		rowsLTest.add(testArray1);
		rowsLTest.add(testArray2);
		
		TableVisualization table = new TableVisualization(config) ;
		rowsL= table.getRowsAsList(resultTest, rowsL);
		assertEquals(rowsLTest.get(0)[0],(rowsL.get(0)[0]));
		assertEquals(rowsLTest.get(1)[0],(rowsL.get(1)[0]));
		
	}

}
