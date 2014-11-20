package com.example.nudelvisualization.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.nudelvisualization.client.AccessDatabase;
import com.example.nudelvisualization.client.Configuration;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AccessDatabaseImpl extends RemoteServiceServlet implements
		AccessDatabase {
	private Connection conn = null;
	
	public AccessDatabaseImpl() {
		String url = null;
		String user = null;
		String password = null;
		try {
			if (SystemProperty.environment.value() ==
					SystemProperty.Environment.Value.Production) {
				// Load the class that provides the new "jdbc:google:mysql://" prefix.
				Class.forName("com.mysql.jdbc.GoogleDriver");
				url = "jdbc:google:mysql://norse-voice-758:nudeldatabase";
				user = "root";
				password = "";
			} else {
				// Local MySQL instance to use during development.
				Class.forName("com.mysql.jdbc.Driver");
				url = "jdbc:mysql://173.194.242.3:3306";
				user = "root";
				password = "welovenoodles";
						
			} 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    try {
    		conn = DriverManager.getConnection(url, user, password);	    		
	    } catch (SQLException e) {
	      System.out.println("couldn't get connection ");
	      e.printStackTrace();
	    }
	}
	
	public String[][] getArea() {
	    String sql = "SELECT AreaCode, AreaName FROM nudeldb.countries";
	    return simpleQuery(sql);
	}
	
	public String[][] getItem() {
		String sql = "SELECT ItemCode, ItemName FROM nudeldb.items";
	    return simpleQuery(sql);
	}
	
	public String[][] getYears() {
		String sql = "SELECT * FROM nudeldb.years ORDER BY year";
	    return simpleQuery(sql);
	}
	
	/**
	 * User wants an Intensity Map. This method provides all required data for this task.
	 * 
	 * <pre> For every config option, at least one item is selected
	 * 
	 * @param config The Configuration object with the IDs of the selected parameters
	 * 
	 * @return a HashMap  with all the required tables as 2-dimensional String arrays
	 */
	public HashMap<String, String[][]> getDataForIntensityMap(Configuration config) {

		HashMap<String, String[][]> data = new HashMap<String, String[][]>();
		
		// Fill data
		data.put("data", getReducedData(config));
		data.put("IsoCode", getISOCodes(config));
		data.put("population", getPopulation(config));

		return data;
	}
	
	private String[][] getISOCodes(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
	    System.out.println("trying query");
	    StringBuilder query = new StringBuilder();
	    query.append("SELECT AreaCode, ISOCode FROM nudeldb.countries WHERE (AreaCode =");
	    for (int i = 0; i < config.getSelectedAreaList().size()-1; i++) {
	    	query.append(" ? OR AreaCode =");
	    }
	    query.append(" ?)");
	    System.out.println(query.toString());
	    try {
	      PreparedStatement select = conn.prepareStatement(query.toString());
	      int count = 1;
	      for (String s : config.getSelectedAreaList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      System.out.println(select.toString());
	      ResultSet result = select.executeQuery();
	      nCol = result.getMetaData().getColumnCount();	      
	      while (result.next()) {
	    	  String[] row = new String[nCol];
	    	  for(int iCol = 1; iCol <= nCol; iCol++ ){
	    	        row[iCol-1] = result.getString(iCol);
	    	    }
	    	    returnValue.add(row);
	      }
	      result.close();
	      result = null;
	      select.close();
	      select = null;
	    } catch (SQLException e) {
	      System.err.println("Error: queryColumns(): " + query.toString());
	      e.printStackTrace();
	    }
	    
	    String[][] returnValuesStrings = new String[returnValue.size()][nCol];
	    for (int i = 0; i < returnValue.size(); i++) {
	    	returnValuesStrings[i] = returnValue.get(i);
	    }
		return returnValuesStrings;
	}
	
	private String[][] getPopulation(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
	    System.out.println("trying query");
	    StringBuilder query = new StringBuilder();
	    query.append("SELECT AreaCode, Year, Value FROM nudeldb.population WHERE (AreaCode =");
	    for (int i = 0; i < config.getSelectedAreaList().size()-1; i++) {
	    	query.append(" ? OR AreaCode =");
	    }
	    query.append(" ?)");
	    query.append(" AND (Year =");
	    for (int i = 0; i < config.getSelectedYearsList().size()-1; i++) {
	    	query.append(" ? OR Year =");
	    }
	    query.append(" ?)");
	    System.out.println(query.toString());
	    try {
	      PreparedStatement select = conn.prepareStatement(query.toString());
	      int count = 1;
	      for (String s : config.getSelectedAreaList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      for (String s : config.getSelectedYearsList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      System.out.println(select.toString());
	      ResultSet result = select.executeQuery();
	      nCol = result.getMetaData().getColumnCount();	      
	      while (result.next()) {
	    	  String[] row = new String[nCol];
	    	  for(int iCol = 1; iCol <= nCol; iCol++ ){
	    	        row[iCol-1] = result.getString(iCol);
	    	    }
	    	    returnValue.add(row);
	      }
	      result.close();
	      result = null;
	      select.close();
	      select = null;
	    } catch (SQLException e) {
	      System.err.println("Error: queryColumns(): " + query.toString());
	      e.printStackTrace();
	    }
	    
	    String[][] returnValuesStrings = new String[returnValue.size()][nCol];
	    for (int i = 0; i < returnValue.size(); i++) {
	    	returnValuesStrings[i] = returnValue.get(i);
	    }
		return returnValuesStrings;
	}
	
	private String[][] getReducedData(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
	    System.out.println("trying query");
	    StringBuilder query = new StringBuilder();
	    query.append("SELECT AreaCode, Year, ItemName, Value FROM");
	    // To be uncommented later!
	    /*for (int i = 0; i < config.getSelectedDataSeriesList().size()-1; i++) {
	    	query.append(" nudeldb." + config.getSelectedDataSeriesList().get(i) + ",");
	    }*/
	    query.append(" nudeldb.production");
	    query.append(" WHERE (AreaCode =");
	    for (int i = 0; i < config.getSelectedAreaList().size()-1; i++) {
	    	query.append(" ? OR AreaCode =");
	    }
	    query.append(" ?)");
	    query.append(" AND (Year =");
	    for (int i = 0; i < config.getSelectedYearsList().size()-1; i++) {
	    	query.append(" ? OR Year =");
	    }
	    query.append(" ?)");
	    query.append(" AND (ItemCode =");
	    for (int i = 0; i < config.getSelectedItemsList().size()-1; i++) {
	    	query.append(" ? OR ItemCode =");
	    }
	    query.append(" ?)");
	    System.out.println(query.toString());
	    try {
	      PreparedStatement select = conn.prepareStatement(query.toString());
	      int count = 1;
	      for (String s : config.getSelectedAreaList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      for (String s : config.getSelectedYearsList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      for (String s : config.getSelectedItemsList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      System.out.println(select.toString());
	      ResultSet result = select.executeQuery();
	      nCol = result.getMetaData().getColumnCount();	      
	      while (result.next()) {
	    	  String[] row = new String[nCol];
	    	  for(int iCol = 1; iCol <= nCol; iCol++ ){
	    	        row[iCol-1] = result.getString(iCol);
	    	    }
	    	    returnValue.add(row);
	      }
	      result.close();
	      result = null;
	      select.close();
	      select = null;
	    } catch (SQLException e) {
	      System.err.println("Error: queryColumns(): " + query.toString());
	      e.printStackTrace();
	    }
	    
	    String[][] returnValuesStrings = new String[returnValue.size()][nCol];
	    for (int i = 0; i < returnValue.size(); i++) {
	    	returnValuesStrings[i] = returnValue.get(i);
	    }
		return returnValuesStrings;
	}
	
	/**
	 * Client reaches out to Server to get rows that pass the filter
	 * 
	 * <pre> For every config option, at least one item is selected
	 * 
	 * @param config The Configuration object with the IDs of the selected parameters
	 *
	 * @return a 2-dimensional String Array with the rows
	 */
	public String[][] getSelectedRows(Configuration config) {

		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
	    System.out.println("trying query");
	    StringBuilder query = new StringBuilder();
	    query.append("SELECT");
	    if (config.getSelectedTitles() == null) {
	    	query.append(" * ");
	    } else {
	    	int count = 0;
	    	for (int i = 0; i < config.getSelectedTitles().size()-1; i++) {
		    	query.append(" " + config.getSelectedTitles().get(count++) + ",");
		    }
		    query.append(" " +  config.getSelectedTitles().get(count) + " ");
	    }
	    query.append("FROM");
	    // To be uncommented later!
	    /*for (int i = 0; i < config.getSelectedDataSeriesList().size()-1; i++) {
	    	query.append(" nudeldb." + config.getSelectedDataSeriesList().get(i) + ",");
	    }*/
	    query.append(" nudeldb.production");
	    query.append(" WHERE (AreaCode =");
	    for (int i = 0; i < config.getSelectedAreaList().size()-1; i++) {
	    	query.append(" ? OR AreaCode =");
	    }
	    query.append(" ?)");
	    query.append(" AND (Year =");
	    for (int i = 0; i < config.getSelectedYearsList().size()-1; i++) {
	    	query.append(" ? OR Year =");
	    }
	    query.append(" ?)");
	    query.append(" AND (ItemCode =");
	    for (int i = 0; i < config.getSelectedItemsList().size()-1; i++) {
	    	query.append(" ? OR ItemCode =");
	    }
	    query.append(" ?)");
	    System.out.println(query.toString());
	    try {
	      PreparedStatement select = conn.prepareStatement(query.toString());
	      int count = 1;
	      for (String s : config.getSelectedAreaList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      for (String s : config.getSelectedYearsList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      for (String s : config.getSelectedItemsList()) {
	    	  select.setInt(count++, Integer.parseInt(s));
	      }
	      System.out.println(select.toString());
	      ResultSet result = select.executeQuery();
	      nCol = result.getMetaData().getColumnCount();	      
	      while (result.next()) {
	    	  String[] row = new String[nCol];
	    	  for(int iCol = 1; iCol <= nCol; iCol++ ){
	    	        row[iCol-1] = result.getString(iCol);
	    	    }
	    	    returnValue.add(row);
	      }
	      result.close();
	      result = null;
	      select.close();
	      select = null;
	    } catch (SQLException e) {
	      System.err.println("Error: queryColumns(): " + query.toString());
	      e.printStackTrace();
	    }
	    
	    String[][] returnValuesStrings = new String[returnValue.size()][nCol];
	    for (int i = 0; i < returnValue.size(); i++) {
	    	returnValuesStrings[i] = returnValue.get(i);
	    }
		return returnValuesStrings;
	}
	
	public String[][] simpleQuery(String sql) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
	    System.out.println("trying query");
	    try {
	      Statement select = conn.createStatement();
	      ResultSet result = select.executeQuery(sql);
	      nCol = result.getMetaData().getColumnCount();	      
	      while (result.next()) {
	    	  String[] row = new String[nCol];
	    	  for( int iCol = 1; iCol <= nCol; iCol++ ){
	    	        row[iCol-1] = result.getString(iCol);
	    	    }
	    	    returnValue.add(row);
	      }
	      result.close();
	      result = null;
	      select.close();
	      select = null;
	    } catch (SQLException e) {
	      System.err.println("Error: queryColumns(): " + sql);
	      e.printStackTrace();
	    }
	    
	    String[][] returnValuesStrings = new String[returnValue.size()][nCol];
	    for (int i = 0; i < returnValue.size(); i++) {
	    	returnValuesStrings[i] = returnValue.get(i);
	    }
		return returnValuesStrings;
	}
}