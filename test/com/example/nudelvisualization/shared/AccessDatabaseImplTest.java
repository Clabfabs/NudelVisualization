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
	
	@Test
	public void testbuildQueryProductionIMData() {
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
		query = accessDB.buildQueryProductionIMData(query, config);
		String test = "SELECT AreaCode, Year, ItemName, Value FROM nudeldb.production NATURAL JOIN nudeldb.items"
				+ " WHERE (AreaCode = ? OR AreaCode = ?) AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(query.toString()));
		
	}
	
	@Test
	public void testbuildQueryTradeIMData() {
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
		query = accessDB.buildQueryTradeIMData(query, config);
		String test = "SELECT AreaCode, Year, ItemName, Value FROM nudeldb.trade NATURAL JOIN nudeldb.items"
				+ " WHERE (ElementCode = ?) AND (AreaCode = ? OR AreaCode = ?) AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(query.toString()));
		
	}
	
	@Test
	public void testbuildQueryProductionLCData() {
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
		query = accessDB.buildQueryProductionLCData(query, config);
		String queryString = query.toString();
		String test = "SELECT ElementName, AreaName, ItemName, Year, Value FROM nudeldb.production "
				+ "NATURAL JOIN nudeldb.elements NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items"
				+ " WHERE (AreaCode = ? OR AreaCode = ?) AND (Year = ? OR Year = ?) AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(queryString));
		
	}
	
	@Test
	public void testbuildQueryTradeLCData() {
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
		query = accessDB.buildQueryTradeLCData(query, config);
		String queryString = query.toString();
		String test = "SELECT ElementName, AreaName, ItemName, Year, Value FROM nudeldb.trade "
				+ "NATURAL JOIN nudeldb.elements NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items "
				+ "WHERE (ElementCode = ?) AND (AreaCode = ? OR AreaCode = ?) AND (Year = ? OR Year = ?) "
				+ "AND (ItemCode = ? OR ItemCode = ?)";
		
		assertTrue(test.equals(queryString));
		
	}
	
	@Test
	public void testbuildQueryColumnChart() {
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
		query = accessDB.buildQueryColumnChart(query, config, 0);
		String queryString = query.toString();
		String test = "SELECT c.AreaName, p.Year, i.ItemName, p.Value "
				+ "FROM nudeldb.production p join nudeldb.countries c on c.AreaCode = p.AreaCode "
				+ "join nudeldb.items i on i.ItemCode = p.ItemCode "
				+ "where ( c.AreaCode = 1 or c.AreaCode = 2 ) and ( p.Year = 1992 or p.Year = 1993 ) "
				+ "and ( i.ItemCode = 15 or i.ItemCode = 44 )";
		
		assertTrue(test.equals(queryString));
		
	}
	

	

}
