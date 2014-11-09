package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class AreaTest {

	@Test
	public void testAreaClass() {
		Area tester = new Area("1", "Armenia");
		assertEquals("1", tester.getID());
		assertEquals("Armenia", tester.getName());
		assertEquals(false, tester.isActive());
		tester.setActive(true);
		assertEquals(true, tester.isActive());
	}
}
