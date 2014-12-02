package com.example.nudelvisualization.shared;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

public class TripleHashMapTest {

	@Test
	public void testsetHashMapProduction() {
		HashMap<String, List<String[]>> test = new HashMap<String, List<String[]>>();
		String[] testArray1= {"Armenia", "Afghanistan"};
		String[] testArray2 = {"Switzerland", "Afghanistan"};
		List<String[]> listTest = new ArrayList<String[]>();
		listTest.add(testArray1);
		listTest.add(testArray2);
		test.put("1", listTest);
		
		TripleHashMap tripleHM = new TripleHashMap();
		tripleHM.setHashMapProduction(test);
		assertEquals(test,tripleHM.getHashMapProduction());
		
	}
	
	@Test
	public void testsetHashMapImport() {
		HashMap<String, List<String[]>> test = new HashMap<String, List<String[]>>();
		String[] testArray1= {"Armenia", "Afghanistan"};
		String[] testArray2 = {"Switzerland", "Afghanistan"};
		List<String[]> listTest = new ArrayList<String[]>();
		listTest.add(testArray1);
		listTest.add(testArray2);
		test.put("1", listTest);
		
		TripleHashMap tripleHM = new TripleHashMap();
		tripleHM.setHashMapImport(test);
		assertEquals(test,tripleHM.getHashMapImport());
		
	}
	
	@Test
	public void testsetHashMapExport() {
		HashMap<String, List<String[]>> test = new HashMap<String, List<String[]>>();
		String[] testArray1= {"Armenia", "Afghanistan"};
		String[] testArray2 = {"Switzerland", "Afghanistan"};
		List<String[]> listTest = new ArrayList<String[]>();
		listTest.add(testArray1);
		listTest.add(testArray2);
		test.put("1", listTest);
		
		TripleHashMap tripleHM = new TripleHashMap();
		tripleHM.setHashMapExport(test);
		assertEquals(test,tripleHM.getHashMapExport());
		
	}

}
