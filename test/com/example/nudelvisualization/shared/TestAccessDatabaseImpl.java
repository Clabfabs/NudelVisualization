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

}
