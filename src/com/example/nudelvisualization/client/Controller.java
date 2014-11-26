package com.example.nudelvisualization.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Controller implements EntryPoint {
	
    private Filter filter = null;

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	/*private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";*/
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		filter = new Filter();
		filter.init();	
		
	}
	
	
	
	
	
}
