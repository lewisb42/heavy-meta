package org.doubleoops.heavymeta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applied to meta-test constructors to indicate the
 * test class and test method the meta-test will analyze.
 * 
 * Used by ParameterResolver's to know what default values
 * to instantiate the meta-test class with, in the absence
 * of something (like the Mosh tool) that injects its own
 * parameter values.
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MetaTestConfig {
	public Class<?> testClass();
	public String testMethodName();
}
