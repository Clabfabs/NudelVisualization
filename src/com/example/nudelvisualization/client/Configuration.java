package com.example.nudelvisualization.client;

import java.util.ArrayList;

public class Configuration {
	
	private ArrayList<Area> selectedArea = new ArrayList<Area>();
	private ArrayList<Item> selectedItems = new ArrayList<Item>();
	private ArrayList<Year> selectedYears = new ArrayList<Year>();
	private ArrayList<DataSeries> selectedDataSeries = new ArrayList<DataSeries>();
	



public void addArea(Area area){
	selectedArea.add(area);	
}

public void addItem(Item item){
	selectedItems.add(item);	
}

public void addYear(Year year){
	selectedYears.add(year);
}

public void addDataSeries(DataSeries dataSerie){
	selectedDataSeries.add(dataSerie);
}

public ArrayList<Area> getSelectedAreaList(){
	return selectedArea;
}

public ArrayList<Item> getSelectedItemsList(){
	return selectedItems;
}

public ArrayList<Year> getSelectedYearsList(){
	return selectedYears;
}

public ArrayList<DataSeries> getSelectedDataSeriesList(){
	return selectedDataSeries;
}

//to be done
public void loadConfig(){
	
}

//to be done
public void saveConfig(){
	
}
}