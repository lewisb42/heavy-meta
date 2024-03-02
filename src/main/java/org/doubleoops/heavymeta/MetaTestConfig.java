package org.doubleoops.heavymeta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applied to meta-test classes to indicate the
 * test class and test method the meta-test will analyze.
 * 
 * These are ignored by tools like Mosh, which inject their
 * own values at runtime.
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MetaTestConfig {
	public Class<?> testClass();
	public String testMethodName();
}
