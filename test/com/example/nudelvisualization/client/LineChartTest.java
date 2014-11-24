package com.example.nudelvisualization.client;

import static org.junit.Assert.*;

import org.junit.Test;

public class LineChartTest {
	
	/* JUnittest does not work because all methods are accessing data from the server.
	 * We cannot simulate those circumstances.
	 * In addition: We are using here a DataTable dataTable which is required for our GWT Visualization. 
	 * However, this class consists of native methods that only work when the app is
	 * running, which is not the case in a test. That is the reason why GWT can
	 * not provide us these methods. This leads us to the fact that it's going
	 * to be difficult to test this function...
	 */ 
	
	@Test
	public void test() {
		fail("Does not work.");
	}

}
