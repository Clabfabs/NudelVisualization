package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ConfigurationTest {

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
	
	

}
