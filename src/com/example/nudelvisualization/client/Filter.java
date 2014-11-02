package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.example.nudelvisualization.server.AccessDatabaseImpl;

public class Filter {

	ArrayList<Area> area = new ArrayList<Area>();
	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<Year> years = new ArrayList<Year>();
	ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	Configuration config;
	private String[][] table;

	public Filter() {
		dataSeries = setDataSeries();
		years = setYears();
		items = setItems();
		area = setArea();
	}

	AccessDatabaseImpl accessDB = new AccessDatabaseImpl();

	// todo: change to definitive method when method available / when database
	// available

	// Gather all Areas in an arraylist
	private ArrayList setArea() {
		table = accessDB.getSomeRows(20);

		// indices of column "AreaCode" and "AreaName"
		int indexAreaCode = 0;
		int indexAreaName = 0;

		// find indices of column "AreaCode" and "AreaName"
		for (int i = 0; i < table[0].length; i++) {
			if (table[0][i] == "AreaCode") {
				indexAreaCode = i;
			} else if (table[0][i] == "AreaName") {
				indexAreaName = i;
			}
		}
		// We fill Area objects with the values of the columns "AreaCode" and "AreaName" and gather them in an arraylist.  
		for (int j = 1; j < table.length; j++) {
			Area areaObject = new Area(table[j][indexAreaCode], table[j][indexAreaName]);
			if (area.contains(areaObject)== false)
			area.add(areaObject);
		}
		return area;
	}
	
	// Gather all Items in an arraylist
	private ArrayList setItems() {
		table = accessDB.getSomeRows(20);

		// indices of column "ItemCode" and "ItemName"
		int indexItemCode = 0;
		int indexItemName = 0;

		// find indices of column "ItemCode" and "ItemName"
		for (int i = 0; i < table[0].length; i++) {
			if (table[0][i] == "ItemCode") {
				indexItemCode = i;
			} else if (table[0][i] == "ItemName") {
				indexItemName = i;
			}
		}
		
		// We fill Area objects with the values of the columns "AreaCode" and "AreaName" and gather them in an arraylist.  
		for (int j = 1; j < table.length; j++) {
			Item itemObject = new Item(table[j][indexItemCode], table[j][indexItemName]);
			if (items.contains(itemObject)== false)
			items.add(itemObject);
		}
		return items;
	}
	
	// Gather all Years in an arraylist
	private ArrayList setYears() {
		
		//PROBLEM: Datatype Year.
		table = accessDB.getSomeRows(20);

		// indices of column "Year"
		int indexYear = 0;

		// find indices of column "Year"
		for (int i = 0; i < table[0].length; i++) {
			if (table[0][i] == "Year") {
				indexYear = i;
			}
		}
		
		// We fill Area objects with the values of the columns "AreaCode" and "AreaName" and gather them in an arraylist.  
		for (int j = 1; j < table.length; j++) {
			Year yearObject = new Year(Integer.valueOf(table[j][indexYear]));
			if (years.contains(yearObject)== false)
			years.add(yearObject);
		}
		return years;
	}

	private ArrayList setDataSeries() {

		DataSeries exports = new DataSeries(1, "export");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries(2, "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries(3, "production");
		dataSeries.add(production);

		return dataSeries;

	}

	public ArrayList getSomeRows(int numberOfRows) {

		return items;

	}

}
