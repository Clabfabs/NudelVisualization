package com.example.nudelvisualization.client;

public abstract class Visualization {	
	
	protected String[] areaIDs = null;
	protected String[] itemIDs = null;
	protected String[] years = null;
	protected String[] dataSeries = null;
	
	public Visualization(Configuration config){
		int numberOfAreas = config.getSelectedAreaList().size();
		int numberOfItems = config.getSelectedItemsList().size();
		int numberOfYears = config.getSelectedYearsList().size();
		int numberOfDataSeries = config.getSelectedDataSeriesList().size();
		areaIDs = new String[numberOfAreas];
		itemIDs = new String[numberOfItems];
		years = new String[numberOfYears];
		dataSeries = new String[numberOfDataSeries];
		
		for (int i = 0; i < numberOfAreas; i++) {
			areaIDs[i] = config.getSelectedAreaList().get(i);
		}
		for (int i = 0; i < numberOfItems; i++) {
			itemIDs[i] = config.getSelectedItemsList().get(i);
		}
		for (int i = 0; i < numberOfYears; i++) {
			years[i] = config.getSelectedYearsList().get(i);
		}
		for (int i = 0; i < numberOfDataSeries; i++) {
			dataSeries[i] = config.getSelectedDataSeriesList().get(i);
		}
	}
	
	public abstract void draw();

	public String[] getAreaIDs() {
		return areaIDs;
	}

	public String[] getItemIDs() {
		return itemIDs;
	}
}