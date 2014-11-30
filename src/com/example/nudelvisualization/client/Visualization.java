package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;

public abstract class Visualization {	
	
	protected Configuration config = null;
	public Visualization(){
		
	}
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
	
	public void addSource(Panel panel){
		Label source = new Label("source: XXXXXXXXXXXXXXXXXXXX");
		panel.add(source);
		
	}
}