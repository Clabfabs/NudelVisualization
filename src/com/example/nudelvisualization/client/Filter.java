package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.example.nudelvisualization.server.AccessDatabaseImpl;
import com.google.gwt.core.client.GWT;

public class Filter {

	public ArrayList<Area> area = new ArrayList<Area>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Year> years = new ArrayList<Year>();
	public ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	private Configuration config;
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
	private ArrayList<Area> setArea() {
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
	private ArrayList<Item> setItems() {
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
	private ArrayList<Year> setYears() {
		
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

	private ArrayList<DataSeries> setDataSeries() {

		DataSeries exports = new DataSeries(1, "export");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries(2, "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries(3, "production");
		dataSeries.add(production);

		return dataSeries;
	}
	
	//adds all active Objects of the ArrayLists areas, items, years and DataSeries to the Configuration config.
	public void createConfiguration(){
		
		for (int i = 0; i< area.size(); i++){
			if (area.get(i).isActive()){
			config.addArea(area.get(i));
			}
		}
		
		for (int i = 0; i< items.size(); i++){
			if (items.get(i).isActive()){
				config.addItem(items.get(i));
			}
		}
		
		for (int i = 0; i< years.size(); i++){
			if (years.get(i).isActive()){
			config.addYear(years.get(i));
			}
		}
		
		for (int i = 0; i< dataSeries.size(); i++){
			if (dataSeries.get(i).isActive()){
			config.addDataSeries(dataSeries.get(i));
			}
		}	
	}
	
	
	public Configuration getConfig(){
		return this.config;
	}
	
	//to be done
	public void visualize(Configuration config){
		
	}
	
}


