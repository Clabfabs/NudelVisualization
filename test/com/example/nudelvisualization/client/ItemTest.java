package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class ItemTest {

	@Test
	public void testItemClass() {
		Item itemTester = new Item("1", "Crops");
		assertEquals("1", itemTester.getID());
		assertEquals("Crops", itemTester.getName());
		assertEquals(false, itemTester.isActive());
		itemTester.setActive(true);
		assertEquals(true, itemTester.isActive());
	}
}
