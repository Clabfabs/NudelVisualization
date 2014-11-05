package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.example.nudelvisualization.server.AccessDatabaseImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Filter {

	public ArrayList<Area> area = new ArrayList<Area>();
	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<Year> years = new ArrayList<Year>();
	public ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	private Configuration config;
	// private String[][] table;
	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);

	public Filter() {
		dataSeries = setDataSeries();
		//years = setYears();
		//items = setItems();
		setArea();
	}

	// AccessDatabaseImpl accessDB = new AccessDatabaseImpl();

	// todo: change to definitive method when method available / when database
	// available

	// Gather all Areas in an arraylist
	
	
	
	private void setArea() {
		dataAccessSocket.getArea(new AsyncCallback<String[][]>() {
			public void onFailure(Throwable caught) {
				System.out.println("Blah");
			}

			public void onSuccess(String[][] result) {
				// indices of column "AreaCode" and "AreaName"
				int indexAreaCode = 0;
				int indexAreaName = 1;
				
				 
				
				area.add(new Area(result[0][indexAreaCode], result[0][indexAreaName]));
				System.out.println(area.get(0).getID());
				System.out.println(area.get(0).getName());
				// We fill Area objects with the values of the columns "AreaCode" and "AreaName" and gather them in an arraylist.  
				//result.length stimmt! 
				for (int j = 1; j < result.length; j++) {
					
					
					//if (area.get(j-1).equals(area.get(j)) == false){--> funktioniert nicht, contains funktionert am ehesten.
					//aber auch dann werden immer nur 2 Objekte hinzugefügt.
					
					
					//egal wie. Es stoppt, nachdem area 2 Objekte enthält.
					area.add(new Area(result[j][indexAreaCode], result[j][indexAreaName]));
					System.out.println(area.get(j).getID());
					System.out.println(area.get(j).getName());	
					}
				}
				//}
			});
	}

	// Gather all Items in an arraylist
	/*private ArrayList<Item> setItems() {
		

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

		// We fill Area objects with the values of the columns "AreaCode" and
		// "AreaName" and gather them in an arraylist.
		for (int j = 1; j < table.length; j++) {
			Item itemObject = new Item(table[j][indexItemCode],
					table[j][indexItemName]);
			if (items.contains(itemObject) == false)
				items.add(itemObject);
		}
		return items;
	}*/

	// Gather all Years in an arraylist
	/*private ArrayList<Year> setYears() {

		table = accessDB.getSomeRows(20);

		// indices of column "Year"
		int indexYear = 0;

		// find indices of column "Year"
		for (int i = 0; i < table[0].length; i++) {
			if (table[0][i] == "Year") {
				indexYear = i;
			}
		}

		// We fill Area objects with the values of the columns "AreaCode" and
		// "AreaName" and gather them in an arraylist.
		for (int j = 1; j < table.length; j++) {
			Year yearObject = new Year(Integer.valueOf(table[j][indexYear]));
			if (years.contains(yearObject) == false)
				years.add(yearObject);
		}
		return years;
	}*/

	private ArrayList<DataSeries> setDataSeries() {

		DataSeries exports = new DataSeries(1, "export");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries(2, "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries(3, "production");
		dataSeries.add(production);

		return dataSeries;
	}

	// adds all active Objects of the ArrayLists areas, items, years and
	// DataSeries to the Configuration config.
	public void createConfiguration() {

		for (int i = 0; i < area.size(); i++) {
			if (area.get(i).isActive()) {
				config.addArea(area.get(i));
			}
		}

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isActive()) {
				config.addItem(items.get(i));
			}
		}

		for (int i = 0; i < years.size(); i++) {
			if (years.get(i).isActive()) {
				config.addYear(years.get(i));
			}
		}

		for (int i = 0; i < dataSeries.size(); i++) {
			if (dataSeries.get(i).isActive()) {
				config.addDataSeries(dataSeries.get(i));
			}
		}
	}

	public Configuration getConfig() {
		return this.config;
	}

	// to be done
	public void visualize(Configuration config) {

	}

}
