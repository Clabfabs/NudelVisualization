package com.example.nudelvisualization.client;

public class DataSeries implements FilterItem {
	
	private boolean active = false;
	private String ID;
	private String name;

	public DataSeries(String ID, String name){
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataSeries other = (DataSeries) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
