package com.example.nudelvisualization.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

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
	
	public void addSource(){
		Label source1 = new Label("(c) FAO. 2014. FAOSTAT. data.fao.org. (Accessed 1.9.2014)");
		source1.addStyleName("sourceLabel");
		Label source2 = new Label("The FAO's endorsement of views portrayed on this website is not stated or implied in any way.");
		source2.addStyleName("sourceLabel");
		RootPanel.get("visualizationContainer").add(source1);
		RootPanel.get("visualizationContainer").add(source2);
		
	}
}