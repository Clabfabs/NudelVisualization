package com.example.nudelvisualization.client;

import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapType;
import com.google.maps.gwt.client.MapTypeId;

public class SampleMapVisualization extends Visualization {

	public SampleMapVisualization(Configuration config) {
		super(config);
	}

	@Override
	public void draw() {
		GoogleMap map;
		VerticalPanel mapVp = new VerticalPanel();
		mapVp.setSize("50%", "50%");
		RootPanel.get("sampleMap").add(mapVp, 800, 0);
		// Die Position als Zahlen braucht es leider noch, sonst wird das widget nicht angezeigt.
		// das "sampleMap" (das würde der html-Position <div id="sampleMap"></div> entsprechen!) wird grossartig ignoriert...

		/*VerticalPanel mapVp2 = new VerticalPanel();
		RootPanel.get("sampleMap").add(mapVp2);

		FlexTable flex = new FlexTable();
		flex.setText(0,	0, "Test");
		mapVp2.add(flex);*/
		
		LatLng myLatLng = LatLng.create(80.440099, 20.843498);
		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(3);
		myOptions.setCenter(myLatLng);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		myOptions.setMapTypeControl(true);
		map = GoogleMap.create(mapVp.getElement(), myOptions);
	

		// GoogleMap map = GoogleMap.create(RootPanel.get("sampleMap"));
		/*MapOptions options  = MapOptions.create() ;

	    double lngCenter = 5;
		double latCenter = 5;
		options.setCenter(LatLng.create( latCenter, lngCenter ));   
	    options.setZoom( 0 ) ;
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    SimplePanel widg = new SimplePanel() ;

	    widg.setSize("20%","20%");

	    GoogleMap theMap = GoogleMap.create( widg.getElement(), options ) ;
	    RootPanel.get().add(widg);*/
	}

}
