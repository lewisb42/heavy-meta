package org.doubleoops.mosh;

import static org.junit.jupiter.api.Assertions.fail;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Test;

public class MetaTestOneFailingMethod extends MetaTestBase {

	public MetaTestOneFailingMethod(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		fail();
	}
}
