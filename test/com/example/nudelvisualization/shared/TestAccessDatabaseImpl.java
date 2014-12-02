package com.example.nudelvisualization.shared;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.nudelvisualization.client.Configuration;
import com.example.nudelvisualization.server.AccessDatabaseImpl;


public class TestAccessDatabaseImpl {

	@Test
	public void testbuildQueryPopulation() {

		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		StringBuilder query = new StringBuilder();
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryPopulation(query, config);
		String queryString = query.toString();
		String test = "SELECT AreaCode, Year, Value FROM nudeldb.population WHERE (AreaCode = ?) AND (Year = ? OR Year = ?)";
		
		assertTrue(test.equals(queryString));
		
	}
	
	@Test
	public void testBuilQueryIsoCodes(){
		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		StringBuilder query = new StringBuilder();
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryISOCodes(query, config);
		String queryString = query.toString();
		String test = "SELECT AreaCode, ISOCode, AreaName FROM nudeldb.countries WHERE (AreaCode = ?)";
		
		assertTrue(test.equals(queryString));

	}
	
	@Test
	public void testBuildQUeryProductionIMData(){
		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		StringBuilder query = new StringBuilder();
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryProductionIMData(query, config);
		String queryString = query.toString();
		String test = "SELECT AreaCode, Year, ItemName, Value FROM nudeldb.production NATURAL JOIN nudeldb.items WHERE (AreaCode = ?)"
				+ " AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		assertTrue(test.equals(queryString));
	
	}
	@Test
	public void testBuildQueryTradeIMData(){
		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		StringBuilder query = new StringBuilder();
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryTradeIMData(query, config);
		String queryString = query.toString();
		String test = "SELECT AreaCode, Year, ItemName, Value FROM nudeldb.trade NATURAL JOIN nudeldb.items"
				+ " WHERE (ElementCode = ?) AND (AreaCode = ?)"
				+ " AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(queryString));

	}
	
	@Test
	public void testBuildQueryProductionLCData(){
		
		Configuration config = new Configuration();
		config.addArea("1");
		config.addAreaName("Armenia");
		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		StringBuilder query = new StringBuilder();
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryProductionLCData(query, config);
		String queryString = query.toString();
		String test = "SELECT ElementName, AreaName, ItemName, Year, Value FROM nudeldb.production NATURAL JOIN nudeldb.elements "
				+ "NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items"
				+ " WHERE (AreaCode = ?)"
				+ " AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(queryString));

	}

}
