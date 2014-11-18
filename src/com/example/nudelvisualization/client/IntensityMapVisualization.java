package com.example.nudelvisualization.client;

import java.util.Arrays;
import java.util.Hashtable;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.AnnotatedTimeLine;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.GeoMap;
import com.google.gwt.visualization.client.visualizations.ImageAreaChart;
import com.google.gwt.visualization.client.visualizations.ImageBarChart;
import com.google.gwt.visualization.client.visualizations.ImageChart;
import com.google.gwt.visualization.client.visualizations.ImageLineChart;
import com.google.gwt.visualization.client.visualizations.ImagePieChart;
import com.google.gwt.visualization.client.visualizations.ImageSparklineChart;
import com.google.gwt.visualization.client.visualizations.IntensityMap;
import com.google.gwt.visualization.client.visualizations.MapVisualization;
import com.google.gwt.visualization.client.visualizations.MotionChart;
import com.google.gwt.visualization.client.visualizations.OrgChart;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

/*
 * TO DOS: 	- Zusammenzählen von Daten wenn mehrere Länder und Jahre gewählt sind.
 * 			- Karte grösser.
 * 			- alle Länder IsoCodes.
 * 			- Daten in Verhältnis zu Population
 * 			- exception wenn keine Daten beheben.
 * 			- wenn Land ausgewählt wurde, es aber keine Daten dazu hat, dann 0 einfüllen. (wahrscheinlich nicht wirklich lösbar...)
 * 			- Kommentar, welche Daten angezeigt werden. 
 * 			- Methoden testen und kommentieren. 
 * 			
 */
public class IntensityMapVisualization extends Visualization {


	private final AccessDatabaseAsync dataAccessSocket = GWT.create(AccessDatabase.class);	
	String [][] IsoCodes = new String[96][2];
	String [] years = new String[22];
	
	public IntensityMapVisualization(Configuration config) {
		super(config);
		setIsoCodes();
		setYears();
	}
	private void setYears(){
			int startYear = 1990;
			int endYear = 2011;

			for (int j = 0; j <= endYear - startYear; j++) {
				int x = startYear + j;
				Integer n = new Integer(x);
				years[j] = n.toString();
			}	
	}
	private void setIsoCodes(){
		IsoCodes[0][0] = "1"; IsoCodes[0][1] = "AM"; IsoCodes[1][0] = "2"; IsoCodes[1][1]= "AF";
		IsoCodes[2][0]="3"; IsoCodes[2][1] ="AL"; IsoCodes[3][0] ="4"; IsoCodes[3][1]="DZ";
		IsoCodes[4][0] = "5"; IsoCodes[4][1] = "AS"; IsoCodes[5][0] = "7"; IsoCodes[5][1]= "AO";
		IsoCodes[6][0]="8"; IsoCodes[6][1] ="AG"; IsoCodes[7][0] ="9"; IsoCodes[7][1]="AR";
		IsoCodes[8][0] = "10"; IsoCodes[8][1] = "AU";IsoCodes[9][0] = "11"; IsoCodes[9][1]= "AT";
		IsoCodes[10][0]="12"; IsoCodes[10][1] ="BS";IsoCodes[11][0] ="13"; IsoCodes[11][1]="BH";
		IsoCodes[12][0] = "14"; IsoCodes[12][1] = "BB"; IsoCodes[13][0] = "15"; IsoCodes[13][1]= "BE";
		IsoCodes[14][0]="16"; IsoCodes[14][1] ="BD";IsoCodes[15][0] ="17"; IsoCodes[15][1]="BM";
		IsoCodes[16][0] = "18"; IsoCodes[16][1] = "BT"; IsoCodes[17][0] = "19"; IsoCodes[17][1]= "BO";
		IsoCodes[18][0]="20"; IsoCodes[18][1] ="BW";IsoCodes[19][0] ="21"; IsoCodes[19][1]="BR";
		IsoCodes[20][0] = "23"; IsoCodes[20][1] = "BZ";IsoCodes[21][0] = "25"; IsoCodes[21][1]= "SB";
		IsoCodes[22][0]="26"; IsoCodes[22][1] ="BN";IsoCodes[23][0] ="27"; IsoCodes[23][1]="BG";
		IsoCodes[24][0] = "28"; IsoCodes[24][1] = "MM";IsoCodes[25][0] = "29"; IsoCodes[25][1]= "BI";
		IsoCodes[26][0]="32"; IsoCodes[26][1] ="CM";IsoCodes[27][0] ="33"; IsoCodes[27][1]="CA";
		IsoCodes[28][0] = "35"; IsoCodes[28][1] = "CV";IsoCodes[29][0] = "36"; IsoCodes[29][1]= "KY";
		IsoCodes[30][0]="37"; IsoCodes[30][1] ="CF";IsoCodes[31][0] ="38"; IsoCodes[31][1]="LK";
		IsoCodes[32][0] = "39"; IsoCodes[32][1] = "TD";IsoCodes[33][0] = "40"; IsoCodes[33][1]= "CL";
		IsoCodes[34][0]="351"; IsoCodes[34][1] ="CN";IsoCodes[35][0] ="44"; IsoCodes[35][1]="CO";
		IsoCodes[36][0] = "45"; IsoCodes[36][1] = "KM";IsoCodes[37][0] = "46"; IsoCodes[37][1]= "CD";
		IsoCodes[38][0]="47"; IsoCodes[38][1] ="CK";IsoCodes[39][0] ="48"; IsoCodes[39][1]="CR";
		IsoCodes[40][0] = "49"; IsoCodes[40][1] = "CU";IsoCodes[41][0] = "50"; IsoCodes[41][1]= "CY";
		IsoCodes[42][0]="51"; IsoCodes[42][1] ="CZ";IsoCodes[43][0] ="52"; IsoCodes[43][1]="AZ";
		IsoCodes[44][0] = "53"; IsoCodes[44][1] = "BJ";IsoCodes[45][0] = "54"; IsoCodes[45][1]= "DK";
		IsoCodes[46][0]="55"; IsoCodes[46][1] ="DM";IsoCodes[47][0] ="56"; IsoCodes[47][1]="DO";
		
		IsoCodes[48][0] = "57"; IsoCodes[48][1] = "BY"; IsoCodes[49][0] = "58"; IsoCodes[49][1]= "EC";
		IsoCodes[50][0]="59"; IsoCodes[50][1] ="EG"; IsoCodes[51][0] ="60"; IsoCodes[51][1]="SV";
		IsoCodes[52][0] = "61"; IsoCodes[52][1] = "GQ"; IsoCodes[53][0] = "62"; IsoCodes[53][1]= "ET";
		IsoCodes[54][0]="63"; IsoCodes[54][1] ="EE"; IsoCodes[55][0] ="64"; IsoCodes[55][1]="FO";
		IsoCodes[56][0] = "66"; IsoCodes[56][1] = "FJ";IsoCodes[57][0] = "67"; IsoCodes[57][1]= "FI";
		IsoCodes[58][0]="68"; IsoCodes[58][1] ="FR";IsoCodes[59][0] ="69"; IsoCodes[59][1]="GF";
		IsoCodes[60][0] = "70"; IsoCodes[60][1] = "PF"; IsoCodes[61][0] = "72"; IsoCodes[61][1]= "DJ";
		IsoCodes[62][0]="73"; IsoCodes[62][1] ="GE";IsoCodes[63][0] ="74"; IsoCodes[63][1]="GA";
		IsoCodes[64][0] = "75"; IsoCodes[64][1] = "GM"; IsoCodes[65][0] = "79"; IsoCodes[65][1]= "DE";
		IsoCodes[66][0]="80"; IsoCodes[66][1] ="BA";IsoCodes[67][0] ="81"; IsoCodes[67][1]="GH";
		IsoCodes[68][0] = "83"; IsoCodes[68][1] = "KI";IsoCodes[69][0] = "84"; IsoCodes[69][1]= "GR";
		IsoCodes[70][0]="86"; IsoCodes[70][1] ="GD";IsoCodes[71][0] ="87"; IsoCodes[71][1]="GP";
		IsoCodes[72][0] = "88"; IsoCodes[72][1] = "GU";IsoCodes[73][0] = "89"; IsoCodes[73][1]= "GT";
		IsoCodes[74][0]="90"; IsoCodes[74][1] ="GN";IsoCodes[75][0] ="91"; IsoCodes[75][1]="GY";
		IsoCodes[76][0] = "93"; IsoCodes[76][1] = "HT";IsoCodes[77][0] = "95"; IsoCodes[77][1]= "HN";
		IsoCodes[78][0]="97"; IsoCodes[78][1] ="HU";IsoCodes[79][0] ="98"; IsoCodes[79][1]="HR";
		IsoCodes[80][0] = "99"; IsoCodes[80][1] = "IS";IsoCodes[81][0] = "100"; IsoCodes[81][1]= "IN";
		IsoCodes[82][0]="101"; IsoCodes[82][1] ="ID";IsoCodes[83][0] ="102"; IsoCodes[83][1]="IR";
		IsoCodes[84][0] = "103"; IsoCodes[84][1] = "IQ";IsoCodes[85][0] = "104"; IsoCodes[85][1]= "IE";
		IsoCodes[86][0]="105"; IsoCodes[86][1] ="IL";IsoCodes[87][0] ="106"; IsoCodes[87][1]="IT";
		IsoCodes[88][0] = "107"; IsoCodes[88][1] = "CI";IsoCodes[89][0] = "108"; IsoCodes[89][1]= "KZ";
		IsoCodes[90][0]="109"; IsoCodes[90][1] ="JM";IsoCodes[91][0] ="110"; IsoCodes[91][1]="JP";
		IsoCodes[92][0] = "112"; IsoCodes[92][1] = "JO";IsoCodes[93][0] = "113"; IsoCodes[93][1]= "KG";
		IsoCodes[94][0]="114"; IsoCodes[94][1] ="KE";IsoCodes[95][0] ="115"; IsoCodes[95][1]="KH";
	}
	@Override
	public void draw() {
		dataAccessSocket.getSelectedRows(configToVisualize, new CallbackHandler());
	}

	
	
	private class CallbackHandler implements AsyncCallback<String[][]>{

		public void onFailure(Throwable caught) {
			System.out.println("Communication with server failed");
		}

		public void onSuccess(final String[][] result) {
			

		    VisualizationUtils.loadVisualizationApi(
		    		new Runnable() {
		    			public void run() {
		    						IntensityMap.Options options = IntensityMap.Options.create();
		    						options.setRegion(IntensityMap.Region.WORLD);
		    						options.setSize(440, 220);
		    						// 2= areacode, 3 = land, 5 = dataserie, 7=item, 8=year, 10 =value
		    						DataTable data = DataTable.create();
		    						data.addColumn(ColumnType.STRING, "Country");
		    					
		    						data.addColumn(ColumnType.NUMBER, result[1][8]);
		    					
		    						int counter = 0;
		    						
		    						for (int i = 0; i<result.length; i++){
		    							for (int j= 0; j<IsoCodes.length; j++){
		    							if (result[i][2].equals(IsoCodes[j][0])){
		    								data.addRow();
		    								data.setValue(counter, 0, IsoCodes[j][1]);
		    								data.setValue(counter, 1, result[i][10]);
		    								counter++;
		  
		    								}else{
		    									
		    								}
		    						}
		    						}
		    	
		    						
		    						IntensityMap widget = new IntensityMap(data, options);
		    						RootPanel.get("visualizationContainer").clear();
		    						RootPanel.get("visualizationContainer").add(widget);
		    					}
		    				}, AnnotatedTimeLine.PACKAGE, CoreChart.PACKAGE,
		    				Gauge.PACKAGE, GeoMap.PACKAGE, ImageChart.PACKAGE,
		    				ImageLineChart.PACKAGE, ImageAreaChart.PACKAGE, ImageBarChart.PACKAGE,
		    				ImagePieChart.PACKAGE, IntensityMap.PACKAGE,
		    				MapVisualization.PACKAGE, MotionChart.PACKAGE, OrgChart.PACKAGE,Table.PACKAGE,
		    				ImageSparklineChart.PACKAGE);		
		    		
		    	}
		    }
		    	   
		
	}
		




