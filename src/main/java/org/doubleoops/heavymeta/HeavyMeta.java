package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.opentest4j.AssertionFailedError;

/**
 * DSL for writing meta-unit tests
 * 
 * @author lewisb
 *
 */
public class HeavyMeta {

	
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
	 * Asserts that the given method in the given class is a test method,
	 * i.e., annotated with Test
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
	 * Asserts the given unit test should pass.
	 * 
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 * @param failureMessage test result message to display if unitTestUnderTest fails
	 * @throws AssertionFailedError if the unitTestUnderTest fails
	 */
	public static void shouldPass(Executable unitTestUnderTest, String failureMessage) throws AssertionFailedError {
		try {
			unitTestUnderTest.execute();
		} catch (Throwable e) {
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			throw new AssertionFailedError(failureMessage);
		}
	}
	
	/**
	 * Asserts the given unit test should pass.
	 * 
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 * 
	 * @throws AssertionFailedError if the unitTestUnderTest fails
	 */
	public static void shouldPass(Executable unitTestUnderTest) throws AssertionFailedError {
		try {
			unitTestUnderTest.execute();
		} catch (Throwable e) {
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			throw new AssertionFailedError("Unit-test-under-test did not pass");
		}
	}
	
	/**
	 * Simply executes the given unit test but doesn't care if it passes or fails.
	 * Effectively ignore any exceptions thrown by a failing test.
	 * 
	 * @param unitTestUnderTest
	 */
	public static void shouldPassOrFail(Executable unitTestUnderTest) {
		try {
			unitTestUnderTest.execute();
		} catch (Throwable e) {
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Asserts the given unit test should fail.
	 * 
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 * 
	 * @param passMessage test result message to display if unitTestUnderTest passes (which it shouldn't)
	 * 
	 * @throws AssertionFailedError if the unitTestUnderTest fails
	 */
	public static void shouldFail(Executable unitTestUnderTest, String passMessage) 
		throws AssertionFailedError {
		try {
			unitTestUnderTest.execute();
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			return;
		} catch (Throwable e) {
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			throw new AssertionFailedError(passMessage);
		}
		
		// if we made it here it means the student test *passed*, 
		// thus *this* test fails
		throw new AssertionFailedError(passMessage);
	}
	
	/**
	 * Asserts the given unit test should fail.
	 * 
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 *  
	 * @throws AssertionFailedError if the unitTestUnderTest fails
	 */
	public static void shouldFail(Executable unitTestUnderTest) 
		throws AssertionFailedError {
		String passMessage = "unit-test-under-test did not fail when it should have";
		try {
			unitTestUnderTest.execute();
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			return;
		} catch (Throwable e) {
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			throw new AssertionFailedError(passMessage);
		}
		
		// if we made it here it means the student test *passed*, 
		// thus *this* test fails
		throw new AssertionFailedError(passMessage);
	}
	
	/**
	 * Run any method on testClassObject that is annotated with BeforeEach
	 * 
	 * @param testClassObject any object. If null the method simply returns.
	 */
	public static void runBeforeEachMethods(Object testClassObject) {
		if (testClassObject == null) return;
		
		Class<? extends Object> klass = testClassObject.getClass();
		Method[] methods = klass.getDeclaredMethods();
		for (Method method: methods) {
			if (isBeforeEachMethod(method)) {
				try {
					method.invoke(testClassObject);
				} catch (IllegalAccessException e) {
					// ignore exceptions
				} catch (IllegalArgumentException e) {
					// ignore exceptions
				} catch (InvocationTargetException e) {
					// ignore exceptions
				}
			}
		}
	}
	
	private static boolean isBeforeEachMethod(Method method) {
		// TODO Auto-generated method stub
		Annotation[] annotations = method.getAnnotations();
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(BeforeEach.class)) {
				return true;
			}
		}
		return false;
	}

}
