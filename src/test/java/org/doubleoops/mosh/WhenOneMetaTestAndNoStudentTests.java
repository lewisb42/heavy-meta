package org.doubleoops.mosh;



import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class WhenOneMetaTestAndNoStudentTests {

	private MoshEngine engine;
	
	@Test
	void whenStudentTestClassIsNull() {
		assertThrows(Exception.class, () -> {
			new MetaTestOneMethod(null, "studentTestMethod1");
		});
	}
	
	@Test
	void whenStudentTestMethodDoesNotExist() {
		assertThrows(AssertionFailedError.class, () -> {
			new MetaTestOneMethod(
					StudentTestsOnePassingMethod.class, 
					"noTestMethodWithThisNameExists");
		});
	}
	
	@Test
	void whenStudentTestMethodIsNull() {
		assertThrows(Exception.class, () -> {
			new MetaTestOneMethod(
					StudentTestsOnePassingMethod.class, 
					null);
		});
	}
}
