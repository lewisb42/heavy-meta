package org.doubleoops.mosh.testdata;

import static org.junit.jupiter.api.Assertions.fail;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import mockit.Mock;
import mockit.MockUp;

@Tag("testdata")
public class MetaTestOneMethodWithMixedPassFail extends MetaTestBase {

	public MetaTestOneMethodWithMixedPassFail(Class<? extends Object> testClass, String testMethodName)  {
		super (testClass, testMethodName);
	}
	
	@Test
	void metaTestMethod1() {
		MutableBoolean shouldPass = new MutableBoolean(true);
		new MockUp<StudentTestsSeveralMethods>() {
			
			@Mock public void test1() { shouldPass.setFalse();; }
			@Mock public void test2() { /* let it pass */ }
			@Mock public void test3() { shouldPass.setFalse();; }
		};
		super.runStudentsTestIgnoreFails();
		if (shouldPass.isFalse()) fail();
	}
}
