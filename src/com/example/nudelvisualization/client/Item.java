package com.example.nudelvisualization.client;

public class Item implements FilterItem {
	
	private boolean active = false;
	private String name;
	private int ID;
	
	public Item(int ID, String name){
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
