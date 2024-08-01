package org.doubleoops.mosh.testdata;

import static org.junit.jupiter.api.Assertions.fail;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("testdata")
public class MetaTestSeveralMethodsSomePassSomeFail extends MetaTestBase {

	public MetaTestSeveralMethodsSomePassSomeFail(Class<? extends Object> testClass, String testMethodName) {
		super(testClass, testMethodName);
	}

	@Test
	void metaTestMethod1() {
		fail();
	}
	
	@Test
	void metaTestMethod2() {
		
	}
	
	@Test
	void metaTestMethod3() {
		fail();
	}
	
	@Test
	void metaTestMethod4() {
		
	}
	
	@Test
	void metaTestMethod5() {
		fail();
	}

}
