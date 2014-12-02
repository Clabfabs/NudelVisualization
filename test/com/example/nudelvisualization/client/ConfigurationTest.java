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
		Configuration config = new Configuration();
		config.addTitles("test");
		
		assertEquals(test,config.getSelectedTitles());
		
		
	}

}
