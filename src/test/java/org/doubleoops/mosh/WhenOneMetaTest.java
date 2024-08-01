package org.doubleoops.mosh;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class WhenOneMetaTest {

	private MoshEngine engine;
	private Class<?> studentsTestOnePassingMethod;
	
	
	@BeforeEach
	void setup() {
		engine = MoshEngine.newInstance();
		studentsTestOnePassingMethod = StudentTestsOneMethod.class;
	}
	
	@Test
	void whenStudentsTestMethodPassesTheMetaTest() throws Exception {
		engine.addMetaTestClasses(MetaTestOnePassingMethod.class);
		engine.addStudentTestClasses(studentsTestOnePassingMethod);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOnePassingMethod");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("test1");
		assertNotNull(stNode);
		assertEquals(1, stNode.passCount());
		assertEquals(0, stNode.failCount());
		assertEquals(100.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().isEmpty());
	}
	
	@Test
	void whenStudentsTestMethodDoesNotPassTheMetaTest() throws Exception {
		engine.addMetaTestClasses(MetaTestOneFailingMethod.class);
		engine.addStudentTestClasses(studentsTestOnePassingMethod);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOneFailingMethod");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("test1");
		assertNotNull(stNode);
		assertEquals(0, stNode.passCount());
		assertEquals(1, stNode.failCount());
		assertEquals(0.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getPassedMethodNames().isEmpty());
	}
	
	@Test
	void whenStudentTestClassIsNull() {
		assertThrows(Exception.class, () -> {
			new MetaTestOnePassingMethod(null, "studentTestMethod1");
		});
	}
	
	@Test
	void whenStudentTestMethodDoesNotExist() {
		assertThrows(AssertionFailedError.class, () -> {
			new MetaTestOnePassingMethod(
					StudentTestsOneMethod.class, 
					"noTestMethodWithThisNameExists");
		});
	}
	
	@Test
	void whenStudentTestMethodIsNull() {
		assertThrows(Exception.class, () -> {
			new MetaTestOnePassingMethod(
					StudentTestsOneMethod.class, 
					null);
		});
	}
}
