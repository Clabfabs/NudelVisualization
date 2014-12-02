package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ConfigurationTest {
	private ArrayList<String> selectedArea = new ArrayList<>();
	private ArrayList<String> selectedAreaName = new ArrayList<>();
	private ArrayList<String> selectedItems = new ArrayList<String>();
	private ArrayList<String> selctedItemNames = new ArrayList<String>();
	private ArrayList<String> selectedYears = new ArrayList<String>();
	private ArrayList<String> selectedDataSeries = new ArrayList<String>();
	private ArrayList<String> selectedDataSeriesName = new ArrayList<String>();
	private ArrayList<String> titles = new ArrayList<String>();

	@Test
	public void testConfiguration() {
		Configuration tester = new Configuration();
		tester.addArea("1");
		assertEquals("1", tester.getSelectedAreaList().get(0));
		tester.addItem("1");
		assertEquals("1", tester.getSelectedItemsList().get(0));
		tester.addDataSeries("1");
		assertEquals("1", tester.getSelectedDataSeriesList().get(0));
		tester.addYear("1990");
		assertEquals("1990", tester.getSelectedYearsList().get(0));
	}
	
	@Test
	public void testaddTitles(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addTitles("test");
		assertEquals(test, tester.getSelectedTitles());
	}
	
	
	@Test
	public void testaddArea(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addArea("test");
		assertEquals(test,tester.getSelectedAreaList());
	}
	
	@Test
	public void testAddAreaName(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addAreaName("test");
		assertEquals(test,tester.getSelectedAreaNameList());
	}
	
	@Test
	public void testAddItems(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addItem("test");
		assertEquals(test,tester.getSelectedItemsList());
	}
	
	@Test
	public void testAddItemNames(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addItemNames("test");
		assertEquals(test,tester.getSelectedItemNameList());
	}
	
	@Test
	public void testAddYears(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addYear("test");
		assertEquals(test,tester.getSelectedYearsList());
	}
	
	@Test
	public void testAddDataSeries(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addDataSeries("test");
		assertEquals(test,tester.getSelectedDataSeriesList());
	}
	
	@Test
	public void testAddDataSeriesNames(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration tester = new Configuration();
		tester.addDataSeriesName("test");
		assertEquals(test,tester.getSelectedDataSeriesNameList());
	}
	

}
