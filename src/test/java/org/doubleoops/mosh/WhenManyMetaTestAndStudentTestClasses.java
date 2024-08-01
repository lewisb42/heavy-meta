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
	
	@Disabled
	@Test
	void whenManyMetaTestAndStudentTestClasses() throws Exception {
		engine.addMetaTestClasses(
				MetaTestSeveralMethodsSomePassSomeFail.class,
				MetaTestOneFailingMethod.class,
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
		STNode stNode = mtNode.getSTNode("test1");
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
