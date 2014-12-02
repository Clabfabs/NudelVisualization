package com.example.nudelvisualization.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.example.nudelvisualization.client.Configuration;
import com.example.nudelvisualization.client.saveConfig;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class saveConfigImpl extends RemoteServiceServlet implements saveConfig {

	@Override
	public File getConfigAsFile(Configuration config) {
		File output = new File("/savedConfig.txt");
		PrintWriter writer;
		try {
			writer = new PrintWriter("/savedConfig.txt");
			writer.println("BlahBlah");
			writer.println("BibiBoboBubu");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	
}
