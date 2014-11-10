package com.example.nudelvisualization.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.nudelvisualization.client.AccessDatabase;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AccessDatabaseImpl extends RemoteServiceServlet implements
		AccessDatabase {
	private boolean testphase = false;
	/**
	 * Client reaches out to Server to get rows that pass the filter
	 * 
	 * @param areaIDs the string array with the IDs of the selected areas
	 * @param itemIDs the string array with the IDs of the selected items
	 * @param years the string array with the IDs of the selected years
	 * @param dataSeries the string array with the selected dataseries
	 *
	 * @return a 2-dimensional String Array with the rows
	 */
	public String[][] getSelectedRows(String[] areaIDs, String[] itemIDs, String[] years, String[] dataSeries) {

		/*
		 * // TODO Verify that the input is valid. Example Code: if
		 * (!FieldVerifier.isValidName(input)) { // If the input is not valid,
		 * throw an IllegalArgumentException back to // the client. throw new
		 * IllegalArgumentException( "Name must be at least 4 characters long");
		 * }
		 */

		// Stuff we need for csv import
		String csvFile = "data/production1990-2011.csv";
		BufferedReader br = null;
		String cvsSplitBy = ",";

		// The data we want to fill
		String[] dataRow = null;
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		String[] firstLine = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine();

			// workaround to get rid of the first 3 annoying characters -> to be
			// improved! :-)
			String line2 = line.substring(3);

			if (line2 != null) {
				firstLine = line2.split(cvsSplitBy);
				dataList.add(firstLine);
				
				while (true) {					
					line = br.readLine();
					if (line == null)
						break;

					String[] cells = line.split(cvsSplitBy);

					dataRow = new String[firstLine.length];
					
					// Adds Rows to the ArrayList that have been selected by the User in the Filter
					for(int f=0; f < areaIDs.length; f++){
						if(cells[2].equals(areaIDs[f])){
							for(int g=0; g < itemIDs.length; g++){
								if(cells[6].equals(itemIDs[g])){
									for(int h=0; h < years.length; h++){
										if(cells[8].equals(years[h])){
											dataRow = line.split(cvsSplitBy);
											dataList.add(dataRow);
										}
									}			
								}
							}		
						}
					}
				}
			} else {
				System.out.println("First line failure");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String[][] data = new String[dataList.size()][firstLine.length];

		// converts ArrayList<String[]> into String[][]
		for (int j = 0; j < dataList.size(); j++) {
			for (int k = 0; k < firstLine.length; k++) {
				data[j][k] = dataList.get(j)[k];
			}
		}
		return data;
	}

	public String[][] getArea() {
		
		// Stuff we need for csv import
		String csvFile = "data/production1990-2011.csv";
		BufferedReader br = null;
		String cvsSplitBy = ",";

		// The data we want to fill
		ArrayList<ArrayList<String>> dataAsArrayList = null;
		ArrayList<String> stringList = null;
		String[][] dataAsArray = null;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine(); // ignore first row.
			dataAsArrayList = new ArrayList<ArrayList<String>>();
			int counter = 1;
			line = br.readLine();
			String[] firstLine = line.split(cvsSplitBy);
			stringList = new ArrayList<String>();//add the second row, so we can compare
			stringList.add(firstLine[2]);
			stringList.add(firstLine[3]);
			dataAsArrayList.add(stringList);
				
			/*Fills only the AreaCode-column and the AreaName-column 
				in a multidimensional ArrayList (dataAsArrayList) and takes 
				every Area and ID just once*/
			while (true) {
				line = br.readLine();
				
				if (line == null)
					break;
				String[] firstLine2 = line.split(cvsSplitBy);
				stringList = new ArrayList<String>();
				if (dataAsArrayList.get(counter-1).get(0).compareTo(firstLine2[2]) != 0){
					stringList.add(firstLine2[2]);
					stringList.add(firstLine2[3]);
					dataAsArrayList.add(stringList);
					counter++;
				}else{
	
				}
				if (testphase) {
					if (counter >= 10) {
						break;
					}					
				}
			}
			
			/*converts mulitdimensional ArrayList (dataAsArrayList) 
			 into multidimensional Array (dataAsArray)*/
			String[][] dataArray = new String[dataAsArrayList.size()][];
			List<String> areaList = dataAsArrayList.get(0);
			dataArray[0] = areaList.toArray(new String[areaList.size()]);
			for (int i = 1; i < dataAsArrayList.size(); i++) {
				List<String> areaList2 = dataAsArrayList.get(i);
				dataArray[i] = areaList2.toArray(new String[areaList.size()]);
				}
			
			dataAsArray = dataArray;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataAsArray;
	}
	
	
	public String[][] getItem() {

		// Stuff we need for csv import
		String csvFile = "data/production1990-2011.csv";
		BufferedReader br = null;
		String cvsSplitBy = ",";

		// The data we want to fill
		ArrayList<ArrayList<String>> dataAsArrayList = null;
		ArrayList<String> stringList = null;
		String[][] dataAsArray = null;
		int cnt = 0;
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine(); // ignore first row.
			dataAsArrayList = new ArrayList<ArrayList<String>>();
			int counter = 1;
			line = br.readLine();
			String[] firstLine = line.split(cvsSplitBy);
			stringList = new ArrayList<String>();//add the first row, so we can compare
			stringList.add(firstLine[6]);
			stringList.add(firstLine[7]);
			dataAsArrayList.add(stringList);
				
			/*Fills only the ItemCode-column and the ItemName-column 
				in a multidimensional ArrayList (dataAsArrayList) and takes 
				every Item and ID just once*/
			while (true) {
				cnt = 0;
				line = br.readLine();

				if (line == null)
					break;
				String[] firstLine2 = line.split(cvsSplitBy);
				stringList = new ArrayList<String>();
				for (int i = 0; i < dataAsArrayList.size(); i++) {
					if (dataAsArrayList.get(i).get(0).equals(firstLine2[6])){
						cnt++;
					}
				}
				if (cnt == 0) {
					stringList.add(firstLine2[6]);
					stringList.add(firstLine2[7]);
					dataAsArrayList.add(stringList);
					counter++;		
				}
				if (testphase) {
					if (counter >= 10) {
						break;
					}					
				}
			}
			
			/*converts mulitdimensional ArrayList (dataAsArrayList) 
			 into multidimensional Array (dataAsArray)*/
			String[][] dataArray = new String[dataAsArrayList.size()][];
			List<String> areaList = dataAsArrayList.get(0);
			dataArray[0] = areaList.toArray(new String[areaList.size()]);
			for (int i = 1; i < dataAsArrayList.size(); i++) {
				List<String> areaList2 = dataAsArrayList.get(i);
				dataArray[i] = areaList2.toArray(new String[areaList.size()]);
				}
			
			dataAsArray = dataArray;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataAsArray;
	}
}