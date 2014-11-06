package com.example.nudelvisualization.client;

public class Year implements FilterItem {
	
	private boolean active = false;
	private int year;
	
	public Year(int year) {
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
