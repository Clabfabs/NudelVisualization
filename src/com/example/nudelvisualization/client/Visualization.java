package com.example.nudelvisualization.client;

import java.util.ArrayList;

public abstract class Visualization {	
	
	protected Configuration config = null;
	
	public Visualization(Configuration config) {
		this.config = config;
	}
	
	public abstract void draw();

	public ArrayList<String> getAreaIDs() {
		return config.getSelectedAreaList();
	}

	public ArrayList<String> getItemIDs() {
		return config.getSelectedItemsList();
	}
}