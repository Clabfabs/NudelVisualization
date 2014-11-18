package com.example.nudelvisualization.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Configuration implements Serializable {

	/**
	 * 
	 */
	private ArrayList<String> selectedArea = new ArrayList<>();
	private ArrayList<String> selectedItems = new ArrayList<String>();
	private ArrayList<String> selectedYears = new ArrayList<String>();
	private ArrayList<String> selectedDataSeries = new ArrayList<String>();
	private ArrayList<String> titles = new ArrayList<String>();

	
	public Configuration(){}

	public Configuration(ArrayList<String> selectedArea,
			ArrayList<String> selectedItems, ArrayList<String> selectedYears,
			ArrayList<String> selectedDataSeries) {
		this.selectedArea = selectedArea;
		this.selectedItems = selectedItems;
		this.selectedYears = selectedYears;
		this.selectedDataSeries = selectedDataSeries;
	}

	public void addTitles(String areaID){
		titles.add(areaID);	
	}

	public void addArea(String area){
		selectedArea.add(area);	
	}

	public void addItem(String itemID){
		selectedItems.add(itemID);	
	}

	public void addYear(String year){
		selectedYears.add(year);
	}

	public void addDataSeries(String dataSerieID){
		selectedDataSeries.add(dataSerieID);
	}

	public ArrayList<String> getSelectedAreaList(){
		return selectedArea;
	}

	public ArrayList<String> getSelectedItemsList(){
		return selectedItems;
	}

	public ArrayList<String> getSelectedYearsList(){
		return selectedYears;
	}

	public ArrayList<String> getSelectedDataSeriesList(){
		return selectedDataSeries;
	}

	public ArrayList<String> getSelectedTitles(){
		if (titles.size() == 0) {
			return null;
		} else {
			return titles;		
		}
	}

	//to be done
	public void loadConfig(){

	}

	//to be done
	public void saveConfig(){

	}
}