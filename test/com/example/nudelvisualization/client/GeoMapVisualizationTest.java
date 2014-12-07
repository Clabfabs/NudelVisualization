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
		Configuration config = new Configuration();
		config.addArea("1");
		config.addArea("2");
		String [][] populationData = new String[2][3];
		populationData[0][1] = "Armenia";
		populationData[1][1] = "Afghanistan"; 
		populationData[0][2] = "100";
		populationData[1][2] = "100"; 
		
		String[][] testResult = new String[2][4];
		testResult[0][0] = "1";
		testResult[0][1] = "Armenia";
		testResult[0][3] = "200";
		testResult[1][0] = "2";
		testResult[1][1] = "Afghanistan";
		testResult[1][3] = "500";
		
		//for j = 0
		GeoMapVisualization geomap = new GeoMapVisualization();
		assertEquals(2, geomap.getSumAllData(testResult, 0, config, populationData));
		
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
