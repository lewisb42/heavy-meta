package org.doubleoops.mosh;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.mosh.testdata.MetaTestOneFailingMethod;
import org.doubleoops.mosh.testdata.MetaTestOneMethodWithMixedPassFail;
import org.doubleoops.mosh.testdata.MetaTestOnePassingMethod;
import org.doubleoops.mosh.testdata.MetaTestSeveralMethodsSomePassSomeFail;
import org.doubleoops.mosh.testdata.StudentTestsOneMethod;
import org.doubleoops.mosh.testdata.StudentTestsSeveralMethods;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class WhenManyMetaTestAndStudentTestClasses {

	private MoshEngine engine;

	@BeforeEach
	void setup() {
		engine = MoshEngine.newInstance();
	}
	
	@Test
	void whenManyMetaTestAndStudentTestClasses() throws Exception {
		engine.addMetaTestClasses(
				MetaTestSeveralMethodsSomePassSomeFail.class,
				MetaTestOneMethodWithMixedPassFail.class,
				MetaTestOnePassingMethod.class
			);
		engine.addStudentTestClasses(
				StudentTestsOneMethod.class,
				StudentTestsSeveralMethods.class);
		
		engine.run();
		
		var report = engine.report();
		
		var mtNode = report.getMTNode("MetaTestSeveralMethodsSomePassSomeFail");
		assertNotNull(mtNode);
		
		STNode stNode = mtNode.getSTNode("StudentTestsOneMethod", "test1");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsSeveralMethods", "test1");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsSeveralMethods", "test2");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsSeveralMethods", "test3");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		stNode = mtNode.getSTNode("StudentTestsOneMethod", "test1");
		assertNotNull(stNode);
		assertEquals(2, stNode.passCount());
		assertEquals(3, stNode.failCount());
		assertEquals(40.0, stNode.percentagePasses(), 0.0001);
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod2()"));
		assertTrue(stNode.getPassedMethodNames().contains("metaTestMethod4()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod3()"));
		assertTrue(stNode.getFailedMethodNames().contains("metaTestMethod5()"));
		
		mtNode = report.getMTNode("MetaTestOneMethodWithMixedPassFail");
		assertNotNull(mtNode);
		
		STNode stNode1 = mtNode.getSTNode("StudentTestsSeveralMethods", "test1");
		assertNotNull(stNode1);
		assertEquals(0, stNode1.passCount());
		assertEquals(1, stNode1.failCount());
		assertEquals(0.0, stNode1.percentagePasses(), 0.0001);
		assertTrue(stNode1.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode1.getPassedMethodNames().isEmpty());
		
		STNode stNode2 = mtNode.getSTNode("StudentTestsSeveralMethods", "test2");
		assertNotNull(stNode2);
		assertEquals(1, stNode2.passCount());
		assertEquals(0, stNode2.failCount());
		assertEquals(100.0, stNode2.percentagePasses(), 0.0001);
		assertTrue(stNode2.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode2.getFailedMethodNames().isEmpty());
		
		STNode stNode3 = mtNode.getSTNode("StudentTestsSeveralMethods", "test3");
		assertNotNull(stNode3);
		assertEquals(0, stNode3.passCount());
		assertEquals(1, stNode3.failCount());
		assertEquals(0.0, stNode3.percentagePasses(), 0.0001);
		assertTrue(stNode3.getFailedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode3.getPassedMethodNames().isEmpty());
		
		STNode stNode4 = mtNode.getSTNode("StudentTestsOneMethod", "test1");
		assertNotNull(stNode4);
		assertEquals(1, stNode4.passCount());
		assertEquals(0, stNode4.failCount());
		assertEquals(100.0, stNode4.percentagePasses(), 0.0001);
		assertTrue(stNode4.getPassedMethodNames().contains("metaTestMethod1()"));
		assertTrue(stNode4.getFailedMethodNames().isEmpty());
	}

}
