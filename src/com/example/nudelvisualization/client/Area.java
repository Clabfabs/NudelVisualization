package com.example.nudelvisualization.client;

public class Area implements FilterItem {
	
	private String ID;
	private String name;
	private boolean active = false;
	
	public Area(String ID, String name) {
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
