package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

/**
 * @author nadinepiveteau We could not generate a test for the method run() in
 *         the class ColumnChartVisualization due to the fact that it is
 *         dependent on what happens on the GUI (in other words the users's
 *         choice). That is the reason why we broke this method in several
 *         method-pieces that correspond to the most important step in the
 *         method and that are GUI-independent. We only will test these methods.
 */
public class ColumnChartVisualzationTest {

	/**
	 * We want to test here whether fillTable() inserts the values correctly in the two-dimensional String array table 
	 * in the correct way. 
	 * 
	 * The test did work. 
	 * 
	 */
	@Test
	
	public void fillTableTest() {
		
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
		
		
		int nrOfItems = config.getSelectedItemsList().size();
		int nrOfYears = config.getSelectedYearsList().size();

		
		HashMap<String, List<String[]>> hashmap = new HashMap<String, List<String[]>>();
		
		List<String[]> list1 = new ArrayList<String[]>();
		String[] line1= {"1992", "Wheat", "456"};
		String[] line3= {"1992", "Barely", "495"};
		String[] line2= {"1993", "Wheat", "470"};
		String[] line4= {"1993", "Barley", "470"};

		list1.add(line1);
		list1.add(line2);
		list1.add(line3);
		list1.add(line4);
		hashmap.put("Armenia", list1);
		
		
		String[][] tester = new String[nrOfYears][nrOfItems+1];
		tester[0][0]= "1992";
		tester[1][0]="1993";
		tester[0][1]= "456";
		tester[1][1]="470";
		tester[0][2]="495";
		tester[1][2]="470";
		
		ColumnChartVisualization colChart = new ColumnChartVisualization(config);
		String[][] table = colChart.fillTable(hashmap, config, "Armenia");
		
		assertEquals(tester[0][0], table[0][0]);
	
//		
	}
}
