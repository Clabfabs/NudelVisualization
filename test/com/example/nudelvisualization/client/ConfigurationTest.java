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
	public void testaddTitles() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addTitles("test");

		assertEquals(test, config.getSelectedTitles());

	}

	@Test
	public void testaddArea() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addArea("test");

		assertEquals(test, config.getSelectedAreaList());

	}
	
	@Test
	public void testaddAreaName() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addAreaName("test");

		assertEquals(test, config.getSelectedAreaNameList());

	}
	
	@Test
	public void testaddYears(){
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addYear("test");

		assertEquals(test, config.getSelectedYearsList());
	}
	
	@Test
	public void testaddItem() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addItem("test");

		assertEquals(test, config.getSelectedItemsList());

	}
	
	@Test
	public void testaddItemName() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addItemNames("test");

		assertEquals(test, config.getSelectedItemNameList());

	}
	
	@Test
	public void testaddDataSeries() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addDataSeries("test");

		assertEquals(test, config.getSelectedDataSeriesList());

	}
	
	@Test
	public void testaddDataSeriesName() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("test");
		Configuration config = new Configuration();
		config.addDataSeriesName("test");

		assertEquals(test, config.getSelectedDataSeriesNameList());

	}
	
	

}
