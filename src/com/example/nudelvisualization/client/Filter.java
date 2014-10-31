package com.example.nudelvisualization.client;


import java.util.ArrayList;

import com.example.nudelvisualization.server.AccessDatabaseImpl;

public class Filter{

ArrayList <Area> area = new ArrayList<Area>();
ArrayList <Item> items = new ArrayList<Item>();
ArrayList<Year> years = new ArrayList<Year>();
ArrayList<DataSeries> dataSeries = new ArrayList<DataSeries>();
Configuration config;
private String[][] table;

public Filter(){	
dataSeries = setDataSeries();
years = setYears();
items = setItems();
area = setArea();
}

private ArrayList setArea(){
	AccessDatabaseImpl accessDB = new AccessDatabaseImpl();
	//todo: change to definitive method when method available / when database available
	table = accessDB.getSomeRows(20);
	return area;
}
private ArrayList setItems(){
	return items;
}
private ArrayList setYears(){
	return years;
}

private ArrayList setDataSeries(){
	
	DataSeries exports = new DataSeries(1, "export");
	dataSeries.add(exports);
	DataSeries imports = new DataSeries(2, "import");
	dataSeries.add(imports);
	DataSeries production = new DataSeries(3, "production");
	dataSeries.add(production);
	
	return dataSeries;
		
}




public ArrayList getSomeRows(int numberOfRows){
	
	return items;
	
}


}
