package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.function.BooleanSupplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
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
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
			return;
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
		} catch (AssertionFailedError e) {
			// this is where we want to be, so if here, do nothing,
			// meaning shouldFail() was successful
			return;
		} catch (Throwable e) {
			throw new AssertionFailedError(passMessage);
		}
		
		// if we made it here it means the student test *passed*, 
		// thus *this* test fails
		throw new AssertionFailedError(passMessage);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(boolean[] expected, boolean[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(byte[] expected, byte[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(char[] expected, char[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(double[] expected, double[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 * @param delta the error tolerance per element
	 */
	public static void whenArraysEqual(double[] expected, double[] actual, double delta) {
		assertArrayEquals(expected, actual, delta);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(float[] expected, float[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 * @param delta the error tolerance per element
	 */
	public static void whenArraysEqual(float[] expected, float[] actual, float delta) {
		assertArrayEquals(expected, actual, delta);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(int[] expected, int[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(short[] expected, short[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument arrays are equal.
	 * 
	 * @param expected the expected array
	 * @param actual the actual array
	 */
	public static void whenArraysEqual(Object[] expected, Object[] actual) {
		assertArrayEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the code executed does not throw an exception
	 * 
	 * @param executable the code to execute
	 */
	public static void whenDoesNotThrow(Executable executable) {
		assertDoesNotThrow(executable);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the code executed does not throw an exception
	 * 
	 * @param executable the code to execute
	 * @return the supplier's result (if no exception thrown)
	 */
	public static <T> T whenDoesNotThrow(ThrowingSupplier<T> supplier) {
		return assertDoesNotThrow(supplier);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the code throws an exception of the given type or a subclass.
	 * 
	 * @param expectedType the expected type
	 * @param executable the code to execute
	 * @return the thrown exception
	 */
	public static <T extends Throwable> T whenThrows(Class<T> expectedType, Executable executable) {
		return assertThrows(expectedType, executable);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the code throws an exception of exactly the given type.
	 * 
	 * @param expectedType the expected type
	 * @param executable the code to execute
	 * @return the thrown exception
	 */
	public static <T extends Throwable> T whenThrowsExactly(Class<T> expectedType, Executable executable) {
		return assertThrowsExactly(expectedType, executable);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(byte expected, byte actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(byte expected, Byte actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Byte expected, byte actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Byte expected, Byte actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(char expected, char actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(char expected, Character actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Character expected, char actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Character expected, Character actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(double expected, double actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Double expected, double actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Double expected, Double actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(double expected, Double actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param delta the error tolerance
	 */
	public static void whenEqual(double expected, double actual, double delta) {
		assertEquals(expected, actual, delta);
	}
	
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(float expected, float actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(float expected, Float actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Float expected, float actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Float expected, Float actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param delta the error tolerance
	 */
	public static void whenEqual(float expected, float actual, float delta) {
		assertEquals(expected, actual, delta);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(int expected, int actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(int expected, Integer actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Integer expected, int actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Integer expected, Integer actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(long expected, long actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(long expected, Long actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Long expected, long actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Long expected, Long actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(short expected, short actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(short expected, Short actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Short expected, short actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Short expected, Short actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are equal
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenEqual(Object expected, Object actual) {
		assertEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the given condition is false.
	 * 
	 * @param condition the condition to test.
	 */
	public static void whenNot(boolean condition) {
		assertFalse(condition);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when actualValue is an instance of expectedType.
	 * 
	 * @param expectedType the expected type
	 * @param actualValue an object
	 * @return unclear, blame the JUnit 5 docs :(
	 */
	public static <T> T whenInstanceOf(Class<T> expectedType, Object actualValue) {
		return assertInstanceOf(expectedType, actualValue);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the iterable are equal
	 * 
	 * @param expected the expected iterable
	 * @param actual the actual iterable
	 */
	public static void whenIterablesEqual(Iterable<?> expected, Iterable<?> actual) {
		assertIterableEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the lists of lines are equal
	 * 
	 * @param expectedLines the expected list of lines
	 * @param actualLines the actual list of lines
	 */
	public static void whenLinesMatch(List<String> expectedLines, List<String> actualLines) {
		assertLinesMatch(expectedLines, actualLines);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(byte expected, byte actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(byte expected, Byte actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Byte expected, byte actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Byte expected, Byte actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(char expected, char actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(char expected, Character actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Character expected, char actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Character expected, Character actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(double expected, double actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal within the given delta.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param delta the error tolerance
	 */
	public static void whenNotEqual(double expected, double actual, double delta) {
		assertNotEquals(expected, actual, delta);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(double expected, Double actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Double expected, double actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Double expected, Double actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(float expected, float actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal within the given delta.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 * @param delta the error tolerance
	 */
	public static void whenNotEqual(float expected, float actual, float delta) {
		assertNotEquals(expected, actual, delta);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(float expected, Float actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Float expected, float actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Float expected, Float actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(int expected, int actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(int expected, Integer actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Integer expected, int actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Integer expected, Integer actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the given condition is true.
	 * 
	 * @param condition the condition to test.
	 */
	public static void when(boolean condition) {
		assertTrue(condition);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the condition supplied by the given BooleanSupplier is true.
	 * 
	 * @param booleanSupplier the supplier
	 */
	public static void when(BooleanSupplier booleanSupplier) {
		assertTrue(booleanSupplier);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(long expected, long actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(long expected, Long actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Long expected, long actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Long expected, Long actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(short expected, short actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(short expected, Short actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Short expected, short actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Short expected, Short actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments are not equal.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotEqual(Object expected, Object actual) {
		assertNotEquals(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument is not null.
	 * 
	 * @param condition the object to test.
	 */
	public static void whenNotNull(Object obj) {
		assertNotNull(obj);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the argument is null.
	 * 
	 * @param condition the object to test.
	 */
	public static void whenNull(Object obj) {
		assertNull(obj);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments refer to the same object.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenSame(Object expected, Object actual) {
		assertSame(expected, actual);
	}
	
	/**
	 * Matcher to use within a new MockUp<T>() block.
	 * 
	 * Matches when the arguments do not refer to the same object.
	 * 
	 * @param expected the expected value
	 * @param actual the actual value
	 */
	public static void whenNotSame(Object expected, Object actual) {
		assertNotSame(expected, actual);
	}
	
	/**
	 * Used with a new MockUp<T>() block to indicate a student's test
	 * should immediately fail. Typically used in conjunction with shouldFail()
	 * in order to make the HeavyMeta test pass.
	 */
	public static void forceStudentsTestToFail() {
		fail();
	}
}
