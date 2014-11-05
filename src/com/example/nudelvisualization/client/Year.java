package com.example.nudelvisualization.client;

public class Year implements FilterItem {
	
	private boolean active = false;
	private String year;
	
	public Year(String year) {
		this.year = year;
	}
	
	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
