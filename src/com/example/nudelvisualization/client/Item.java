package com.example.nudelvisualization.client;

public class Item implements FilterItem {
	
	private boolean active = false;
	private String name;
	private int ID;
	
	
	@Override
	public boolean getActive() {
		return this.active;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	

}
