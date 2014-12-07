package com.example.nudelvisualization.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.example.nudelvisualization.client.Configuration;
import com.example.nudelvisualization.client.saveConfig;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class saveConfigImpl extends RemoteServiceServlet implements saveConfig {

	public void getConfigAsFile(Configuration config) {
		File output = new File("../savedConfig.txt");
		PrintWriter writer;
		try {
			writer = new PrintWriter(output);
			for (int i = 0; i < config.getSelectedAreaList().size() - 1; i++) {
				writer.print(config.getSelectedAreaList().get(i)+",");
			}
			writer.print(config.getSelectedAreaList().get(config.getSelectedAreaList().size())+"\n");
			for (int i = 0; i < config.getSelectedItemsList().size() - 1; i++) {
				writer.print(config.getSelectedItemsList().get(i)+",");
			}
			writer.print(config.getSelectedItemsList().get(config.getSelectedItemsList().size())+"\n");
			for (int i = 0; i < config.getSelectedYearsList().size() - 1; i++) {
				writer.print(config.getSelectedYearsList().get(i)+",");
			}
			writer.print(config.getSelectedYearsList().get(config.getSelectedYearsList().size())+"\n");
			for (int i = 0; i < config.getSelectedDataSeriesList().size() - 1; i++) {
				writer.print(config.getSelectedDataSeriesList().get(i)+",");
			}
			writer.print(config.getSelectedDataSeriesList().get(config.getSelectedDataSeriesList().size())+"\n");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Configuration setConfigFromFile() {
		Configuration config = null;
		ArrayList<String> area = new ArrayList<>();
		area.add("2");
		area.add("3");
		area.add("4");
		ArrayList<String> item = new ArrayList<>();
		item.add("44");
		item.add("71");
		item.add("75");
		ArrayList<String> years = new ArrayList<>();
		years.add("1991");
		years.add("1992");
		years.add("1994");
		ArrayList<String> dataSeries = new ArrayList<>();
		dataSeries.add("2");
		config = new Configuration(area, item, years, dataSeries);
		return config;
	}
}
