package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class IntensityMapVisualizationTest {
	@Test
	public void getIsocodeOfSelectedArea() {
		Configuration config = new Configuration();
		config.addArea("1");
		config.addArea("2");

		config.addAreaName("Armenia");
		config.addAreaName("Afghanistan");

		config.addDataSeries("production");
		config.addItem("1");
		config.addItem("2");
		config.addItemNames("Wheat");
		config.addItemNames("Barley");
		config.addYear("1992");
		config.addYear("1993");
		
		String[][] isoCodesTest = new String[3][2]; 
		isoCodesTest[0][0] = "1";
		isoCodesTest[0][1] = "AM";
		isoCodesTest[1][0] = "2";
		isoCodesTest[1][1] = "AF";
		isoCodesTest[2][0] = "3";
		isoCodesTest[2][1] = "AL";
		
		String[] test = {"AM", "AF"};
		
		String[] isoCodeArray = new String[config.getSelectedAreaList().size()];
		IntensityMapVisualization intmap = new IntensityMapVisualization(config);
		isoCodeArray = intmap.getIsocodeOfSelectedArea(config, isoCodesTest);
		
		assertEquals(test[0], isoCodeArray[0]);
		assertEquals(test[1], isoCodeArray[1]);
		
		
	}

	

}




