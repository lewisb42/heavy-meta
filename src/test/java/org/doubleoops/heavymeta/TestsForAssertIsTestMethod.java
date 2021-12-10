package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.doubleoops.heavymeta.HeavyMeta.*;

class TestsForAssertIsTestMethod {

	class Dummy {
		@Test
		public void annotatedMethod() {}
		
		public void nonAnnotatedMethod() {}
	}
	
	@Test
	void shouldConfirmTestAnnotation() {
		assertIsTestMethod(Dummy.class, "annotatedMethod");
	}

	@Test
	void shouldConfirmNotTestAnnotation() {
		assertThrows(AssertionFailedError.class, () -> {
			assertIsTestMethod(Dummy.class, "nonAnnotatedMethod");
		});
	}
}
