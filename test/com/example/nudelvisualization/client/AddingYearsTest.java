package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddingYearsTest {

	@Test
	public void testAddYears() {
		
	Filter.addYears();
	int y = 2011 - 1990 + 1;
	assertEquals(y, Filter.years.size());
	String firstYear = "1990";
	assertEquals(firstYear, Filter.years.get(0).getYear());
		
	}

}
