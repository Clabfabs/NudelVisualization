package com.example.nudelvisualization.client;

public class DataSeries implements FilterItem {
	
	private boolean active = false;
	private String ID;
	private String name;

	public DataSeries(String ID, String name){
	this.ID = ID;
	this.name = name;
	}
	@Override
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
