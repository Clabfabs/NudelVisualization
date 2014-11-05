package com.example.nudelvisualization.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.nudelvisualization.client.AccessDatabase;
import com.example.nudelvisualization.client.Configuration;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AccessDatabaseImpl extends RemoteServiceServlet implements
		AccessDatabase {

	/**
	 * Client reaches out to Server to get some Data (as an example, here it
	 * gets the metadata (first row)
	 * 
	 * @param input
	 *            TO BE CHANGED INTO CONFIGURATION. Or delete, as we don't need
	 *            it for MetaData
	 * @return the metadata as ArrayList
	 */
	public String[] getMetaData(String input) throws IllegalArgumentException {
		// We don't need this anymore, this is completely implemented in the
		// following method
		return null;
	}

	/**
	 * Client reaches out to Server to a certain number of rows
	 * 
	 * @param numberOfRows
	 *            the number of rows we want
	 * @return a 2-dimensional String Array with the rows
	 */
	public String[][] getSomeRows(int numberOfRows) {

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
		String[][] data = null;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine();

			// workaround to get rid of the first 3 annoying characters -> to be
			// improved! :-)
			String line2 = line.substring(3);

			if (line2 != null) {
				String[] firstLine = line2.split(cvsSplitBy);
				data = new String[numberOfRows][firstLine.length];
				data[0] = firstLine;

				int i = 1;
				while (i < numberOfRows) {
					line = br.readLine();

					if (line == null)
						break;

					String[] cells = line.split(cvsSplitBy);
					// First idea how a filter could work
					if (cells[8].equals("1992") && cells[6].equals("15")) {
						data[i] = line.split(cvsSplitBy);
						i++;
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
		return data;
	}

	public String[][] getSelectedRows(Configuration config) {

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

				int i = 1;

				// To do: better while loop.
				while (i < 10000) {
					line = br.readLine();
					if (line == null)
						break;

					String[] cells = line.split(cvsSplitBy);

					dataRow = new String[firstLine.length];
					
					// Adds Rows to the ArrayList that have been selected by the User in the Filter
						for(int f=0; f<config.getSelectedYearsList().size(); f++){
							if(cells[8].equals(config.getSelectedYearsList().get(f).getYear())){
								for(int g=0; g<config.getSelectedAreaList().size(); g++){
									if(cells[2].equals(config.getSelectedAreaList().get(f).getID())){
										for(int h=0; h<config.getSelectedItemsList().size(); h++){
											if(cells[6].equals(config.getSelectedItemsList().get(f).getID())){
												dataRow = line.split(cvsSplitBy);
												dataList.add(dataRow);
												i++;	
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
			stringList = new ArrayList<String>();
			int counter = 1;
			line = br.readLine();
			String[] firstLine = line.split(cvsSplitBy);
			stringList = new ArrayList<String>();
			stringList.add(firstLine[2]);
			stringList.add(firstLine[3]);
			dataAsArrayList.add(stringList);
				
			/*Fills only the AreaCode-column and the AreaName-column 
				in a multidimensional ArrayList (dataAsArrayList)*/
			while (br.readLine() != null) {
				line = br.readLine();

				if (line == null)
					break;
				String[] firstLine2 = line.split(cvsSplitBy);
				stringList = new ArrayList<String>();
				//funktioniert nicht:
				//if (dataAsArrayList.get(counter-1).get(0).equals(firstLine2[2]) == false){
					stringList.add(firstLine2[2]);
					stringList.add(firstLine2[3]);
					dataAsArrayList.add(stringList);
					}
				counter++;
			//}
			
			//converts mulitdimensional ArrayList (dataAsArrayList) into multidimensional Array (dataAsArray)
			String[][] dataArray = new String[dataAsArrayList.size()][];
			List<String> areaList = dataAsArrayList.get(0);
			dataArray[0] = areaList.toArray(new String[areaList.size()]);
			for (int i = 1; i < dataAsArrayList.size(); i++) {
				//if (dataAsArrayList.get(i).get(0).equals(dataAsArrayList.get(i-1).get(0)) == false){
				//--> funktioniert nicht. Ist immer falsch. 
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

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
