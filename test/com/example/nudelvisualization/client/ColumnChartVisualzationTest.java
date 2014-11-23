package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;

/**
 * @author nadinepiveteau
 *We could not generate a test for the method run() in the class ColumnChartVisualization
 *due to the fact that it is dependent on what happens on the GUI (in other words the users's 
 *choice). That is the reason why we broke this method in several method-pieces that correspond 
 *to the most important step in the method and that are GUI-independent. 
 *We only will test these methods. 
 */
public class ColumnChartVisualzationTest {

	/**
	 * We want to test here whether inserYearsIntoDataTable() inserts the Years per country in 
	 * the correct way. 
	 * 
	 * The test did fail because of the following reason: We are using here a DataTable dataTable which is required for our GWT Visualization.
	 * However, this class consists of native methods that only work when the app is running, which is not the case in a test. That is the 
	 * reason why GWT can not provide us these methods. This leads us to the fact that it's going to be difficult to test this function...
	 */
	@Test
	
	public void insertYearsIntoDataTableTest() {
		Configuration config = new Configuration();
		config.addArea("1");
		config.addArea("2");
		config.addArea("3");
		config.addAreaName("Armenia");
		config.addAreaName("Afghanistan");
		config.addAreaName("Albania");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItem("3");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addItemNames("Maize");
		config.addYear("1992");
		config.addYear("1993");
		config.addYear("1994");
		config.addYear("1995");
		
		int nrOfArea = config.getSelectedAreaList().size();
		int nrOfItems = config.getSelectedItemsList().size();
		int nrOfYears = config.getSelectedYearsList().size();
		
		DataTable tester = DataTable.create();
		DataTable dataTable2 = DataTable.create();
		
		tester.addColumn(ColumnType.STRING, "Year");
		for (int c = 0; c<nrOfItems; c++){
			tester.addColumn(ColumnType.NUMBER,config.getSelectedItemNameList().get(c));
		}
		tester.addRows(nrOfYears);
		tester.setValue(0, 0, "1992");
		tester.setValue(1,0,"1993");
		tester.setValue(2, 0, "1994");
		tester.setValue(3, 0, "1995");
		
		ColumnChartVisualization colChartViz = new ColumnChartVisualization(config);
		dataTable2 = colChartViz.insertYearsIntoDataTable(dataTable2, config);
		System.out.println(dataTable2.getValueString(0, 0));
		assertEquals(tester.getValueString(0, 0), dataTable2.getValueString(0, 0));
		//assertEquals(tester.getValueString(1, 0), dataTable2.getValueString(1, 0));
		//assertEquals(tester.getValueString(2, 0), dataTable2.getValueString(2, 0));
		//assertEquals(tester.getValueString(3, 0), dataTable2.getValueString(3, 0));
	}

}
