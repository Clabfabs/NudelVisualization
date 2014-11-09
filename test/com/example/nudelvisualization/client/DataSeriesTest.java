package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataSeriesTest {

	@Test
	public void testDataSeriesClass() {
		DataSeries dataSeriesTester = new DataSeries("1", "production");
		assertEquals("1", dataSeriesTester.getID());
		assertEquals("production", dataSeriesTester.getName());
		assertEquals(false, dataSeriesTester.isActive());
		dataSeriesTester.setActive(true);
		assertEquals(true, dataSeriesTester.isActive());
	}
}
