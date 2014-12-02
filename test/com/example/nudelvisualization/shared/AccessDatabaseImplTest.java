package com.example.nudelvisualization.shared;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.nudelvisualization.client.Configuration;
import com.example.nudelvisualization.server.AccessDatabaseImpl;

public class AccessDatabaseImplTest {

	@Test
	public void testbuildQueryISOCodes() {
		StringBuilder query = new StringBuilder();
		Configuration config = new Configuration();
		config.addArea("1");
		config.addArea("2");
		config.addAreaName("Armenia");
		config.addAreaName("Afghanistan");
		config.addDataSeries("1");
		config.addDataSeriesName("Production");
		config.addItem("15");
		config.addItem("44");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryISOCodes(query, config);
		String test = "SELECT AreaCode, ISOCode, AreaName FROM nudeldb.countries WHERE (AreaCode = ? OR AreaCode = ?)";
		
		assertTrue(test.equals(query.toString()));
		
	}
	
	@Test
	public void testbuildQueryPopulation() {
		StringBuilder query = new StringBuilder();
		Configuration config = new Configuration();
		config.addArea("1");
		config.addArea("2");
		config.addAreaName("Armenia");
		config.addAreaName("Afghanistan");
		config.addDataSeries("1");
		config.addDataSeriesName("Production");
		config.addItem("15");
		config.addItem("44");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
		query = accessDB.buildQueryPopulation(query, config);
		String test = "SELECT AreaCode, Year, Value "
				+ "FROM nudeldb.population "
				+ "WHERE (AreaCode = ? OR AreaCode = ?) AND (Year = ? OR Year = ?)";
		
		assertTrue(test.equals(query.toString()));
		
	}
	
	

}
