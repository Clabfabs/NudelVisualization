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
	
	//test for one Area that is a country.
	@Test 
	public void testGetSumAllData(){
		int j = 0;
		Configuration config = new Configuration();
		config.addArea("1");
		
		
		
//		public int getSumAllData(String [][] result, int j){
//			int sumAllData=0;
//			for (int i= 0; i< result.length; i++){
//				if (result[i][0].equals(config.getSelectedAreaList().get(j))){
//					if (!(result[i][3].isEmpty())){ //get rid of exceptions
//						//compare it with population
//						for (int y = 0; y< populationData.length; y++){
//							if(populationData[y][1].equals(result[i][1])){
//								//add up all dataValues
//								int valueAsDouble = Integer.valueOf(result[i][3]);
//								int populationAsDouble = Integer.valueOf(populationData[y][2]);
//								sumAllData = sumAllData + (valueAsDouble/populationAsDouble);
//							}
//						}
//					}
//				}
//			}
//			return sumAllData;
//		}
	}

}
