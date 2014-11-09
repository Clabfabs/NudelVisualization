package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class YearTest {

	@Test
	public void testYearClass() {
		
			Year yeartester = new Year("1992");
			assertEquals("1992", yeartester.getYear());
			assertEquals(false, yeartester.isActive());
			yeartester.setActive(true);
			assertEquals(true, yeartester.isActive());
	}
}
