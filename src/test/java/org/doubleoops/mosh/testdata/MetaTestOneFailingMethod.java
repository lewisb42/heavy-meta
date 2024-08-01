package org.doubleoops.mosh.testdata;

import static org.junit.jupiter.api.Assertions.fail;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("testdata")
public class MetaTestOneFailingMethod extends MetaTestBase {

	public MetaTestOneFailingMethod(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		fail();
	}
}
