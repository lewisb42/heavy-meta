package org.doubleoops.mosh;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.mosh.testdata.MetaTestSeveralMethodsSomePassSomeFail;
import org.doubleoops.mosh.testdata.StudentTestsOneMethod;
import org.doubleoops.mosh.testdata.StudentTestsSeveralMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WhenOneMetaTestClassSeveralMethods {
	
	private MoshEngine engine;
	
	
	@BeforeEach
	void setup() {
		engine = MoshEngine.newInstance();
	}
	
	@Test
	void whenSeveralMetaTestMethodsVsOneStudentTestMethod() throws Exception {
		engine.addMetaTestClasses(MetaTestSeveralMethodsSomePassSomeFail.class);
		engine.addStudentTestClasses(StudentTestsOneMethod.class);
		engine.run();
		
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestSeveralMethodsSomePassSomeFail");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("StudentTestsOneMethod::test1");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
	}
	
	@Test
	void whenSeveralMetaTestMethodsVsSeveralStudentTestMethods() throws Exception {
		engine.addMetaTestClasses(MetaTestSeveralMethodsSomePassSomeFail.class);
		engine.addStudentTestClasses(StudentTestsSeveralMethods.class);
		engine.run();
		
		var report = engine.report();
		var mtNode = report.getMTNode("MetaTestSeveralMethodsSomePassSomeFail");
		assertNotNull(mtNode);
		STNode stNode = mtNode.getSTNode("StudentTestsSeveralMethods::test1");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsSeveralMethods::test2");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsSeveralMethods::test3");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
	}

}
