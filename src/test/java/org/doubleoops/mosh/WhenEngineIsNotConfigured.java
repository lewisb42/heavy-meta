package org.doubleoops.mosh;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.doubleoops.mosh.MoshEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WhenEngineIsNotConfigured {

	@BeforeEach
	void setup() {
		
	}
	
	@Test
	void whenEngineIsNotConfigured() throws Exception {
		var engine = MoshEngine.newInstance();
		engine.run();
		var report = engine.report();
		assertTrue(report.isEmpty());
	}
}
