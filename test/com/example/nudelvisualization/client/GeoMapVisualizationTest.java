package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeoMapVisualizationTest {
	String [][] IsoCodes = new String[3][2];

	                                    
	@Test
	public void testCheckIsoCode() {
		IsoCodes[0][0] = "Switzerland";
		IsoCodes[1][0] = "France";
		IsoCodes[2][0] = "Italy";
		IsoCodes[0][1] = "CH";
		IsoCodes[1][1] = "F";
		IsoCodes[2][1] = "I";
		
		GeoMapVisualization geomap = new GeoMapVisualization();
		boolean check = geomap.checkIsoCode(IsoCodes);
		assertFalse(check);
		

	}

}
