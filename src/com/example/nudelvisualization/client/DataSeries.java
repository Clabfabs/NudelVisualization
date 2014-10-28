package com.example.nudelvisualization.client;

public class DataSeries implements FilterItem {
	
	private boolean active = false;
	private int ID;
	private String name;

	
	@Override
	public boolean getActive() {
		return this.active;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
