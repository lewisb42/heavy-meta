package org.doubleoops.mosh.testdata;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("testdata")
public class MetaTestOnePassingMethod extends MetaTestBase {

	public MetaTestOnePassingMethod(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		
	}
}
