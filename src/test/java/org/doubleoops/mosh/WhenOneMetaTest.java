package org.doubleoops.mosh;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class WhenOneMetaTest {

	private MoshEngine engine;
	private Class<?> studentsTestOnePassingMethod;
	
	private Class<?> metaTestOneMethod;
	
	@BeforeEach
	void setup() {
		engine = MoshEngine.newInstance();
		metaTestOneMethod = MetaTestOneMethod.class;
		studentsTestOnePassingMethod = StudentTestsOnePassingMethod.class;
	}
	
	@Test
	void whenOneStudentTestMethodPassesMetaTest() throws Exception {
		engine.addMetaTestClasses(metaTestOneMethod);
		engine.addStudentTestClasses(studentsTestOnePassingMethod);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOneMethod");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("test1");
		assertNotNull(stNode);
		assertEquals(1, stNode.passCount());
		assertEquals(0, stNode.failCount());
		assertEquals(100.0, stNode.percentagePasses(), 0.0001);
	}
	
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
