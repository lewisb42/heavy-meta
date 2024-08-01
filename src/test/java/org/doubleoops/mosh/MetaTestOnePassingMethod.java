package org.doubleoops.mosh;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Test;

public class MetaTestOnePassingMethod extends MetaTestBase {

	public MetaTestOnePassingMethod(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		
	}
}
