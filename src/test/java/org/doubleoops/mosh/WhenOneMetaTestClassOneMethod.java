package org.doubleoops.mosh;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.doubleoops.heavymeta.MetaTestBase;
import org.doubleoops.mosh.testdata.MetaTestOneFailingMethod;
import org.doubleoops.mosh.testdata.MetaTestOneMethodWithMixedPassFail;
import org.doubleoops.mosh.testdata.MetaTestOnePassingMethod;
import org.doubleoops.mosh.testdata.StudentTestsOneMethod;
import org.doubleoops.mosh.testdata.StudentTestsSeveralMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class WhenOneMetaTestClassOneMethod {

	private MoshEngine engine;
	
	
	@BeforeEach
	void setup() {
		engine = MoshEngine.newInstance();
	}
	
	@Test
	void whenStudentsTestMethodPassesTheMetaTest() throws Exception {
		engine.addMetaTestClasses(MetaTestOnePassingMethod.class);
		engine.addStudentTestClasses(StudentTestsOneMethod.class);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOnePassingMethod");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("StudentTestsOneMethod::test1");
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
		engine.addStudentTestClasses(StudentTestsOneMethod.class);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOneFailingMethod");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("StudentTestsOneMethod::test1");
		assertNotNull(stNode);
		assertEquals(0, stNode.passCount());
		assertEquals(1, stNode.failCount());
		assertEquals(0.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getPassedMethodNames().isEmpty());
	}
	
	@Test
	void whenSeveralStudentTestMethodsDoNotPassTheMetaTest() throws Exception {
		engine.addMetaTestClasses(MetaTestOneFailingMethod.class);
		engine.addStudentTestClasses(StudentTestsSeveralMethods.class);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOneFailingMethod");
		assertNotNull(mtNode);
		
		STNode stNode1 = mtNode.getSTNode("StudentTestsSeveralMethods::test1");
		assertNotNull(stNode1);
		assertEquals(0, stNode1.passCount());
		assertEquals(1, stNode1.failCount());
		assertEquals(0.0, stNode1.percentagePasses(), 0.0001);
		assertTrue(stNode1.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode1.getPassedMethodNames().isEmpty());
		
		STNode stNode2 = mtNode.getSTNode("StudentTestsSeveralMethods::test2");
		assertNotNull(stNode2);
		assertEquals(0, stNode2.passCount());
		assertEquals(1, stNode2.failCount());
		assertEquals(0.0, stNode2.percentagePasses(), 0.0001);
		assertTrue(stNode2.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode2.getPassedMethodNames().isEmpty());
		
		STNode stNode3 = mtNode.getSTNode("StudentTestsSeveralMethods::test3");
		assertNotNull(stNode3);
		assertEquals(0, stNode3.passCount());
		assertEquals(1, stNode3.failCount());
		assertEquals(0.0, stNode3.percentagePasses(), 0.0001);
		assertTrue(stNode3.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode3.getPassedMethodNames().isEmpty());
	}
	
	@Test
	void whenSeveralStudentTestMethodsPassTheMetaTest() throws Exception {
		engine.addMetaTestClasses(MetaTestOnePassingMethod.class);
		engine.addStudentTestClasses(StudentTestsSeveralMethods.class);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOnePassingMethod");
		assertNotNull(mtNode);
		
		STNode stNode1 = mtNode.getSTNode("StudentTestsSeveralMethods::test1");
		assertNotNull(stNode1);
		assertEquals(1, stNode1.passCount());
		assertEquals(0, stNode1.failCount());
		assertEquals(100.0, stNode1.percentagePasses(), 0.0001);
		assertTrue(stNode1.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode1.getFailedMethodNames().isEmpty());
		
		STNode stNode2 = mtNode.getSTNode("StudentTestsSeveralMethods::test2");
		assertNotNull(stNode2);
		assertEquals(1, stNode2.passCount());
		assertEquals(0, stNode2.failCount());
		assertEquals(100.0, stNode1.percentagePasses(), 0.0001);
		assertTrue(stNode2.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode2.getFailedMethodNames().isEmpty());
		
		STNode stNode3 = mtNode.getSTNode("StudentTestsSeveralMethods::test3");
		assertNotNull(stNode1);
		assertEquals(1, stNode3.passCount());
		assertEquals(0, stNode3.failCount());
		assertEquals(100.0, stNode3.percentagePasses(), 0.0001);
		assertTrue(stNode3.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode3.getFailedMethodNames().isEmpty());
	}
	
	@Test
	void whenSeveralStudentTestMethodsWhereSomePassTheMetaTestAndSomeFail() throws Exception {
		engine.addMetaTestClasses(MetaTestOneMethodWithMixedPassFail.class);
		engine.addStudentTestClasses(StudentTestsSeveralMethods.class);
		engine.run();
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestOneMethodWithMixedPassFail");
		assertNotNull(mtNode);
		
		STNode stNode1 = mtNode.getSTNode("StudentTestsSeveralMethods::test1");
		assertNotNull(stNode1);
		assertEquals(0, stNode1.passCount());
		assertEquals(1, stNode1.failCount());
		assertEquals(0.0, stNode1.percentagePasses(), 0.0001);
		assertTrue(stNode1.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode1.getPassedMethodNames().isEmpty());
		
		STNode stNode2 = mtNode.getSTNode("StudentTestsSeveralMethods::test2");
		assertNotNull(stNode2);
		assertEquals(1, stNode2.passCount());
		assertEquals(0, stNode2.failCount());
		assertEquals(100.0, stNode2.percentagePasses(), 0.0001);
		assertTrue(stNode2.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode2.getFailedMethodNames().isEmpty());
		
		STNode stNode3 = mtNode.getSTNode("StudentTestsSeveralMethods::test3");
		assertNotNull(stNode3);
		assertEquals(0, stNode3.passCount());
		assertEquals(1, stNode3.failCount());
		assertEquals(0.0, stNode3.percentagePasses(), 0.0001);
		assertTrue(stNode3.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode3.getPassedMethodNames().isEmpty());
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
