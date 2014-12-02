package com.example.nudelvisualization.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class TripleHashMap implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, List<String[]>> hashMapProduction = null;
	private HashMap<String, List<String[]>> hashMapImport = null;
	private HashMap<String, List<String[]>> hashMapExport = null;
	
	

	
	public void setHashMapProduction(HashMap<String, List<String[]>> production){
		this.hashMapProduction = production;
		}
	
	public void setHashMapImport (HashMap<String, List<String[]>> imp){
		this.hashMapImport = imp;
	}
	public void setHashMapExport (HashMap<String, List<String[]>> export){
		this.hashMapExport = export;
	}
	
	public HashMap<String, List<String[]>> getHashMapProduction(){
		return this.hashMapProduction;
	}
	
	public HashMap<String, List<String[]>> getHashMapImport(){
		return this.hashMapImport;
	}

	public HashMap<String, List<String[]>> getHashMapExport(){
		return this.hashMapExport;
	}

}
