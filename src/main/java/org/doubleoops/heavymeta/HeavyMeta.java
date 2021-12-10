package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

/**
 * DSL for writing meta-unit tests
 * 
 * @author lewisb
 *
 */
public class HeavyMeta {

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
			throw new AssertionFailedError("Unit-test-under-test did not pass");
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
			throw new AssertionFailedError(passMessage);
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
		} catch (Throwable e) {
			throw new AssertionFailedError(passMessage);
		}
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
			throw new AssertionFailedError(passMessage);
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
		} catch (Throwable e) {
			throw new AssertionFailedError(passMessage);
		}
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the given condition is true.
	 * 
	 * @param condition the condition to test.
	 */
	public static void whenTrue(boolean condition) {
		assertTrue(condition);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument is not null.
	 * 
	 * @param condition the condition to test.
	 */
	public static void whenNotNull(Object obj) {
		assertNotNull(obj);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument is null.
	 * 
	 * @param condition the condition to test.
	 */
	public static void whenNull(Object obj) {
		assertNull(obj);
	}
	
	
}
