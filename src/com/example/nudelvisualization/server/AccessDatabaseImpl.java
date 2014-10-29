package com.example.nudelvisualization.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.example.nudelvisualization.client.AccessDatabase;
import com.example.nudelvisualization.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class AccessDatabaseImpl extends RemoteServiceServlet implements AccessDatabase {
	
	/**
	   * Client reaches out to Server to get some Data (as an example, here it gets 
	   * the metadata (first row)
	   * 
	   * @param input TO BE CHANGED INTO CONFIGURATION. Or delete, as we don't need it for MetaData
	   * @return the metadata as ArrayList
	   */
	public String[] getMetaData(String input) throws IllegalArgumentException {
		// We don't need this anymore, this is completely implemented in the following method
		return null;
	}
	
	/**
	   * Client reaches out to Server to a certain number of rows
	   * 
	   * @param numberOfRows the number of rows we want
	   * @return a 2-dimensional String Array with the rows
	   */
	public String[][] getSomeRows(int numberOfRows) {

		/*// TODO Verify that the input is valid. Example Code:
		if (!FieldVerifier.isValidName(input)) {
	      // If the input is not valid, throw an IllegalArgumentException back to
	      // the client.
	      throw new IllegalArgumentException(
	          "Name must be at least 4 characters long");
	    }*/
		
		// Stuff we need for csv import
		String csvFile = "data/production1990-2011.csv";
		BufferedReader br = null;
		String cvsSplitBy = ",";

		// The data we want to fill
		String[][] data = null;

		try {
			br = new BufferedReader(new FileReader(csvFile));
			String line = br.readLine();
			
			// workaround to get rid of the first 3 annoying characters -> to be improved! :-)
			String line2 = line.substring(3);

			if (line2 != null) {
				String[] firstLine = line2.split(cvsSplitBy);
				data = new String[numberOfRows][firstLine.length];
				data[0] = firstLine;
					
				int i = 1;
				while (i < numberOfRows) {
					line = br.readLine();
					
					if (line == null) break;
					
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

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
				">", "&gt;");
	}
}
