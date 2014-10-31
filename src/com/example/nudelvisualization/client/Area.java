package com.example.nudelvisualization.client;

public class Area implements FilterItem {
	
	private int ID;
	private String name;
	private boolean active = false;
	
	public Area(int ID, String name) {
		this.ID = ID;
		this.name = name;
	}
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
