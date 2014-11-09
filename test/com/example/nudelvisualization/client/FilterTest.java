package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author nadinepiveteau
 *
 */
/**
 * @author nadinepiveteau
 *
 */
public class FilterTest {

	/**
	 * Was geändert wurde um diesen Test zu ermöglichen:
	 * 1)getYears() musste auf static und public gesetzt werden
	 * 2)addYear() auf static und public setzen 
	 * 3)Arraylist years auf static setzen.
	 * wieder rückgängig machen wenn Test korrekt. 
	 */
	@Test
	
	public void testAddYears() {
		ArrayList<Year> result = new ArrayList<Year>();
		result.add(new Year("1990"));
		result.add(new Year("1991"));
		result.add(new Year("1992"));
		result.add(new Year("1993"));
		result.add(new Year("1994"));
		result.add(new Year("1995"));
		result.add(new Year("1996"));
		result.add(new Year("1997"));
		result.add(new Year("1998"));
		result.add(new Year("1999"));
		result.add(new Year("2000"));
		result.add(new Year("2001"));
		result.add(new Year("2002"));
		result.add(new Year("2003"));
		result.add(new Year("2004"));
		result.add(new Year("2005"));
		result.add(new Year("2006"));
		result.add(new Year("2007"));
		result.add(new Year("2008"));
		result.add(new Year("2009"));
		result.add(new Year("2010"));
		result.add(new Year("2011"));
		
		Filter filter = new Filter(null, null);
		filter.addYears();
		assertEquals(result, filter.getYears());
	}

	
	/**
	 * Was geändert wurde um diesen Test zu ermöglichen:
	 * 1)getDataSeries() musste auf static  und this.dataSeries auf nur dataSeries gesetzt werden
	 * 2)setDataSeries() auf static und public setzen 
	 * 3)Arraylist dataSeries auf static setzen.
	 * wieder rückgängig machen wenn Test korrekt. 
	 */
	@Test
	public void testSetDataSeries(){
		ArrayList<DataSeries> result = new ArrayList<DataSeries>();
		result.add(new DataSeries ("1", "export"));
		result.add(new DataSeries ("2", "import"));
		result.add(new DataSeries ("3", "production"));
		
		Filter filter = new Filter(null, null);
		filter.setDataSeries();
		assertEquals(result, filter.getDataSeries());
		
	}
	
	
	
	
	

}
