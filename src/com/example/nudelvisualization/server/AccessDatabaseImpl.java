package com.example.nudelvisualization.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				// Load the class that provides the new "jdbc:google:mysql://"
				// prefix.
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("couldn't get connection ");
			e.printStackTrace();
		}
	}
	
	@Override
	public HashMap<String, String[][]> getInitialData() {
		HashMap<String, String[][]> data = new HashMap<>();
		data.put("area", getArea());
		data.put("items", getItem());
		data.put("years", getYears());
		return data;
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
	 * User wants an Intensity Map. This method provides all required data for
	 * this task.
	 * 
	 * <pre>
	 * For every config option, at least one item is selected
	 * 
	 * @param config The Configuration object with the IDs of the selected parameters
	 * 
	 * @return a HashMap  with all the required tables as 2-dimensional String arrays
	 */
	public HashMap<String, String[][]> getDataForIntensityMap(Configuration config) {

		HashMap<String, String[][]> data = new HashMap<String, String[][]>();

		if (config.getSelectedDataSeriesList().contains("1")) {
			data.put("production", getProductionIMData(config));
		}
		if (config.getSelectedDataSeriesList().contains("2")) {
			data.put("import", getTradeIMData(config, 5610));
		}
		if (config.getSelectedDataSeriesList().contains("3")) {
			data.put("export", getTradeIMData(config, 5910));
		}

		data.put("IsoCode", getISOCodes(config));
		data.put("population", getPopulation(config));

		return data;
	}

	public HashMap<String, String[][]> getDataForLineChart(Configuration config) {

		HashMap<String, String[][]> data = new HashMap<String, String[][]>();

		if (config.getSelectedDataSeriesList().contains("1")) {
			data.put("production", getProductionLCData(config));
		}
		if (config.getSelectedDataSeriesList().contains("2")) {
			data.put("import", getTradeLCData(config, 5610));
		}
		if (config.getSelectedDataSeriesList().contains("3")) {
			data.put("export", getTradeLCData(config, 5910));
		}

		return data;
	}

	private String[][] getISOCodes(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT AreaCode, ISOCode, AreaName FROM nudeldb.countries WHERE (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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

	private String[][] getProductionIMData(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT AreaCode, Year, ItemName, Value FROM nudeldb.production NATURAL JOIN nudeldb.items");
		query.append(" WHERE (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
			query.append(" ? OR Year =");
		}
		query.append(" ?)");
		query.append(" AND (ItemCode =");
		for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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

	private String[][] getTradeIMData(Configuration config, int code) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT AreaCode, Year, ItemName, Value FROM nudeldb.trade NATURAL JOIN nudeldb.items");
		query.append(" WHERE (ElementCode = ?) AND (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
			query.append(" ? OR Year =");
		}
		query.append(" ?)");
		query.append(" AND (ItemCode =");
		for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
			query.append(" ? OR ItemCode =");
		}
		query.append(" ?)");
		System.out.println(query.toString());
		try {
			PreparedStatement select = conn.prepareStatement(query.toString());
			int count = 1;
			select.setInt(count++, code);
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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

	private String[][] getProductionLCData(Configuration config) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT ElementName, AreaName, ItemName, Year, Value FROM nudeldb.production NATURAL JOIN nudeldb.elements NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items");
		query.append(" WHERE (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
			query.append(" ? OR Year =");
		}
		query.append(" ?)");
		query.append(" AND (ItemCode =");
		for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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

	private String[][] getTradeLCData(Configuration config, int code) {
		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT ElementName, AreaName, ItemName, Year, Value FROM nudeldb.trade NATURAL JOIN nudeldb.elements NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items");
		query.append(" WHERE (ElementCode = ?) AND (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
			query.append(" ? OR Year =");
		}
		query.append(" ?)");
		query.append(" AND (ItemCode =");
		for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
			query.append(" ? OR ItemCode =");
		}
		query.append(" ?)");
		System.out.println(query.toString());
		try {
			PreparedStatement select = conn.prepareStatement(query.toString());
			int count = 1;
			select.setInt(count++, code);
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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
	 * <pre>
	 * For every config option, at least one item is selected
	 * 
	 * @param config The Configuration object with the IDs of the selected parameters
	 *
	 * @return a 2-dimensional String Array with the rows
	 */
	public HashMap<String, String[][]> getTableVisualizationData(Configuration config) {

		ArrayList<String[]> returnValue = new ArrayList<>();
		int nCol = 0;
		System.out.println("trying query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT");
		if (config.getSelectedTitles() == null) {
			query.append(" * ");
		} else {
			int count = 0;
			for (int i = 0; i < config.getSelectedTitles().size() - 1; i++) {
				query.append(" " + config.getSelectedTitles().get(count++)
						+ ",");
			}
			query.append(" " + config.getSelectedTitles().get(count) + " ");
		}
		query.append("FROM");
		// To be uncommented later!
		/*
		 * for (int i = 0; i < config.getSelectedDataSeriesList().size()-1; i++)
		 * { query.append(" nudeldb." +
		 * config.getSelectedDataSeriesList().get(i) + ","); }
		 */
		query.append(" nudeldb.production NATURAL JOIN nudeldb.elements NATURAL JOIN nudeldb.countries NATURAL JOIN nudeldb.items");
		query.append(" WHERE (AreaCode =");
		for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
			query.append(" ? OR AreaCode =");
		}
		query.append(" ?)");
		query.append(" AND (Year =");
		for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
			query.append(" ? OR Year =");
		}
		query.append(" ?)");
		query.append(" AND (ItemCode =");
		for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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
		HashMap<String, String[][]> data = new HashMap<>();
		data.put("Production", returnValuesStrings);
		return data;
	}

	public HashMap<String, List<String[]>> getDataForColumnChart(Configuration config) {
		int nCol = 0;
		HashMap<String, List<String[]>> hashMap = new HashMap<String, List<String[]>>();
		ArrayList<String[]> returnValue = new ArrayList<>();

		StringBuilder query = new StringBuilder();
		query.append("SELECT c.AreaName, p.Year, i.ItemName, p.Value FROM nudeldb.production p join nudeldb.countries c on c.AreaCode = p.AreaCode join nudeldb.items i on i.ItemCode = p.ItemCode where ( c.AreaCode = "
				+ config.getSelectedAreaList().get(0));
		int a = 1;
		while (a < config.getSelectedAreaList().size()) {
			query.append(" or c.AreaCode = "
					+ config.getSelectedAreaList().get(a));
			a++;
		}

		query.append(" ) and ( p.Year = "
				+ config.getSelectedYearsList().get(0));

		int y = 1;
		while (y < config.getSelectedYearsList().size()) {
			query.append(" or p.Year = " + config.getSelectedYearsList().get(y));
			y++;
		}

		query.append(" ) and ( i.ItemCode = "
				+ config.getSelectedItemsList().get(0));

		int it = 1;
		while (it < config.getSelectedItemsList().size()) {
			query.append(" or i.ItemCode = "
					+ config.getSelectedItemsList().get(it));
			it++;
		}

		query.append(" )");

		System.out.println(query);
		try {
			PreparedStatement select = conn.prepareStatement(query.toString());
			ResultSet result = select.executeQuery();
			nCol = result.getMetaData().getColumnCount();

			while (result.next()) {
				String areaName = result.getString("AreaName");
				String year = result.getString("Year");
				String item = result.getString("ItemName");
				String value = result.getString("Value");

				if (!hashMap.containsKey(areaName)) {
					hashMap.put(areaName, new ArrayList<String[]>());
				}

				List<String[]> yearItemList = hashMap.get(areaName);
				yearItemList.add(new String[] { year, item, value });

				// String[] row = new String[nCol];
				// for (int iCol = 1; iCol <= nCol; iCol++) {
				// row[iCol - 1] = result.getString(iCol);
				// }
				// returnValue.add(row);
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
		return hashMap;
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
				for (int iCol = 1; iCol <= nCol; iCol++) {
					row[iCol - 1] = result.getString(iCol);
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