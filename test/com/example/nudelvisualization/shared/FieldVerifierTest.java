package com.example.nudelvisualization.shared;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldVerifierTest {

	@Test
	public void test() {
		String name = null; 
		FieldVerifier fv = new FieldVerifier();
		assertFalse(fv.isValidName(name));
	}

}
