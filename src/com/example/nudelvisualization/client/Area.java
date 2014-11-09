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


	public String getName() {
		return name;
	}


}
