package com.example.nudelvisualization.client;

public class Year implements FilterItem {
	
	private boolean active = false;
	private Year year;
	
	@Override
	public boolean getActive() {
		return this.active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

}
