package org.doubleoops.mosh;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Test;

public class MetaTestOneMethod extends MetaTestBase {

	public MetaTestOneMethod(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		
	}
}
