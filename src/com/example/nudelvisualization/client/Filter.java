package com.example.nudelvisualization.client;

import java.util.ArrayList;
import java.util.List;

import com.example.nudelvisualization.server.AccessDatabaseImpl;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;


public class Filter {

	private ArrayList<Area> area = new ArrayList<Area>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Year> years = new ArrayList<Year>();
	private ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
	private Configuration config;
	private ListBox lbAreaFilter = null;
	private ListBox lbItemsFilter = null;
	//public CheckBoxGroup cbgAreaFilter = null;

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final AccessDatabaseAsync dataAccessSocket = GWT
			.create(AccessDatabase.class);

	public Filter(ListBox lbArea, ListBox lbItems ) {
		setDataSeries();
		setYears();
		setItems();
		lbItemsFilter = lbItems;
		setArea();
		lbAreaFilter = lbArea;
		// System.out.println(area.get(0).getName());
	}

	private void setArea() {
		dataAccessSocket.getArea(new AreaCallbackHandler());
	}

	public ArrayList<Area> getArea() {
		return this.area;
	}

	/*
	 * Gather all Items in an arraylist--> hat noch viele Items doppelt, da es
	 * von jedem Land einfach einmal jedes Item nimmt. Viele Länder haben aber
	 * die gleichen Items...
	 */
	 private void setItems() {
	 dataAccessSocket.getItem(new AsyncCallback<String[][]>() {
	 public void onFailure(Throwable caught) {
	 System.out.println("Blah");
	 }
	
	 public void onSuccess(String[][] result) {
	 // indices of column "AreaCode" and "AreaName"
	 int indexItemCode = 0;
	 int indexItemName = 1;
	
	 /* We fill Area objects with the values of the columns "AreaCode"
	 and "AreaName" and gather them in an arraylist(area).*/
	 for (int j = 0; j < result.length; j++) {
	 items.add(new Item(result[j][indexItemCode], result[j][indexItemName]));
	 //System.out.println(items.get(j).getID());
	 //System.out.println(items.get(j).getName());
		lbItemsFilter.addItem(items.get(j).getName(), items.get(j).getID());

	 }
	 }
	 });
	
	 }

	public ArrayList<Item> getItems() {
		return this.items;
	}

	// Gather all Years in an arraylist
	private void setYears() {
		int startYear = 1990;
		int endYear = 2011;
		
		for (int j = 0; j <= endYear - startYear; j++) {
			years.add(new Year(startYear + j));
			// System.out.println(years.get(j).getYear());
			//lbYearFilter.addItem("hi");
		}
	}

	public ArrayList<Year> getYears() {
		return this.years;
	}

	private void setDataSeries() {
		DataSeries exports = new DataSeries("1", "export");
		dataSeries.add(exports);
		DataSeries imports = new DataSeries("2", "import");
		dataSeries.add(imports);
		DataSeries production = new DataSeries("3", "production");
		dataSeries.add(production);
	}

	public ArrayList<DataSeries> getDataSeries() {
		return this.dataSeries;
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
		Visualization visualization = new Visualization(config);
		// visualization.draw();
	}

	private class AreaCallbackHandler implements AsyncCallback<String[][]> {

		public void onFailure(Throwable caught) {
			System.out.println("Blah");
		}

		public void onSuccess(String[][] result) {
			// indices of column "AreaCode" and "AreaName"
			int indexAreaCode = 0;
			int indexAreaName = 1;

			/*
			 * We fill Area objects with the values of the columns "AreaCode"
			 * and "AreaName" and gather them in an arraylist(area).
			 */
			for (int j = 0; j < result.length; j++) {
				area.add(new Area(result[j][indexAreaCode],
						result[j][indexAreaName]));
				// System.out.println(area.get(j).getName());
				// System.out.println(area.get(j).getID());
				// System.out.println(area.get(j).getName());
					lbAreaFilter.addItem(area.get(j).getName(), area.get(j).getID());
			}
		}

	}

}
