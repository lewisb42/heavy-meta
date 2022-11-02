package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

/**
 * Non-intercepted versions of (some) assertions one would normally
 * find in <a href="https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html">JUnit5's Assertions class</a>.
 * 
 * Also includes some useful assertions (e.g., safeAssertElementsUnique) to
 * streamline meta-tests.
 * 
 * Use these in meta-tests any time the Assertions class is faked, otherwise
 * the faked versions will be called.
 * 
 * @author Lewis Baumstark
 *
 */
class SafeAssertions {

	/**
	 * Asserts that all elements in the collection are unique (per Object#equals()).
	 * 
	 * This is safe to use with a faked JUnit5 Assertions class.
	 * 
	 * @param <E> the type of element in the collection
	 * @param collection the collection
	 * @param msg the assertion failure message
	 */
	public static <E> void safeAssertElementsUnique(java.util.Collection<E> collection, String msg) {
		int originalSize = collection.size();
		java.util.HashSet<E> set = new java.util.HashSet<E>(collection);
		SafeAssertions.safeAssertEquals(originalSize, set.size(), msg);
	}

	/**
	 * Asserts that the given method in the given class is a test method,
	 * i.e., annotated with Test
	 * <br>
	 * Note that this is automatically run if a meta-test registers the HeavyMeta extension.
	 * 
	 * @param <T> the type name
	 * @param klass the runtime class object
	 * @param methodName the name of the method in question
	 */
	public static <T> void assertIsTestMethod(Class<T> klass, String methodName) {
		try {
			Method method = klass.getMethod(methodName);
			Annotation testAnnotation = method.getAnnotation(Test.class);
			assertNotNull(testAnnotation,
					"method '" + methodName + "' is not annotated with @Test.");
		} catch (NoSuchMethodException e) {
			throw new AssertionFailedError("No test method with name '" + methodName + "' exists on class" + klass.getName());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertFalse(boolean) for those situations.
	 * 
	 * @param cond the condition you are asserting is false
	 */
	public static void safeAssertFalse(boolean cond) {
		if (cond) {
			throw new AssertionFailedError();
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertFalse(boolean, String) for those situations.
	 * 
	 * @param cond the condition you are asserting is false
	 * @param message the message for a failed assertion
	 */
	public static void safeAssertFalse(boolean cond, String message) {
		if (cond) {
			throw new AssertionFailedError(message);
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertEquals(int, int) for those situations.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param message the message for a failed assertion
	 */
	public static void safeAssertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new AssertionFailedError(message);
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertEquals(Object, Object) for those situations.
	 * 
	 * (Also a reminder this covers the assertEquals for Strings -- there is no
	 * separate assertion for that type)
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param message the message for a failed assertion
	 */
	public static void safeAssertEquals(Object expected, Object actual, String message) {
		if (expected == actual) {
			return;
		}
		
		// note the above case handles when both are null
		if (expected == null || actual == null) {
			throw new AssertionFailedError(message);
		}
		
		if (!expected.equals(actual)) {
			throw new AssertionFailedError(message);
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertTrue(boolean, String) for those situations.
	 * 
	 * @param cond the condition you are asserting is true
	 * @param message the message for a failed assertion
	 */
	public static void safeAssertTrue(boolean cond, String message) {
		if (!cond) {
			throw new AssertionFailedError(message);
		}
	}

	/**
	 * If using FakedAssertions, you can't use normal JUnit assertions in your meta-tests.
	 * This is a "safe" version of assertTrue(boolean) for those situations.
	 * 
	 * @param cond the condition you are asserting is true
	 */
	public static void safeAssertTrue(boolean cond) {
		if (!cond) {
			throw new AssertionFailedError();
		}
	}

}
