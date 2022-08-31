package org.doubleoops.heavymeta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

/**
 * A faked version of JUnit5's Assertions class,
 * used to verify that certain assertions have been
 * called. It is not exhaustive of all JUnit5's available
 * assertions.
 * 
 * Warning: this may be deprecated in the future. Its
 * usefulness is limited to checking if an appropriate
 * assertion was called, and nothing more. Typical
 * meta-test suites need the ability to validate parameters
 * in ways that are likely impossible to abstract into
 * a reusable class.
 * 
 * @author Lewis Baumstark
 *
 */
public class FakedAssertions extends MockUp<Assertions> {

	private static boolean didAssertEqualsIntInt = false;
	private static boolean didAssertEqualsStringString = false;
	
	/**
	 * Instantiate to activate the faked object, even though
	 * most methods are called statically.
	 */
	public FakedAssertions() {
		didAssertEqualsIntInt = false;
		didAssertTrue = false;
		didAssertFalse = false;
		didAssertThrows = false;
		didAssertEqualsBooleanBoolean = false;
		didAssertEqualsStringString = false;
	}
	 
	/**
	 * Catchall to report that one of assertTrue(), assertFalse(), or
	 * assertEquals(boolean, boolean) was called
	 * 
	 * @return true if one of the above methods was called; false otherwise
	 */
	public static boolean didAssertBoolean() {
		return didAssertTrue || didAssertFalse || didAssertEqualsBooleanBoolean;
	}
	

	@Mock
	public static void assertEquals(Invocation inv, int expected, int actual) {
		didAssertEqualsIntInt = true;
		inv.proceed(expected, actual);
	}
	
	@Mock
	public static void assertEquals(Invocation inv, int expected, int actual, String message) {
		didAssertEqualsIntInt = true;
		inv.proceed(expected, actual, message);
	}
	
	/**
	 * Called on the fake class to indicate that an assertEquals(int,int) was recorded.
	 * 
	 * @return true if assertEquals(int,int) was called
	 */
	public static boolean didAssertEqualsIntInt() {
		return didAssertEqualsIntInt;
	}
	
	/**
	 * Called on the fake class to indicate that an assertEquals(String,String) was recorded.
	 * 
	 * @return true if assertEquals(String,String) was called
	 */
	public static boolean didAssertEqualsStringString() {
		return didAssertEqualsStringString;
	}
	
	private static boolean didAssertEqualsBooleanBoolean = false;
	
	/**
	 * Called on the fake class to indicate that an assertEquals(boolean,boolean) was recorded.
	 * 
	 * @return true if assertEquals(boolean,boolean) was called
	 */
	public static boolean didAssertEqualsBooleanBoolean() {
		return didAssertEqualsBooleanBoolean;
	}
	
	@Mock
	public static void assertEquals(Invocation inv, Object expected, Object actual) {
		if (expected instanceof Boolean && actual instanceof Boolean) {
			didAssertEqualsBooleanBoolean = true;
		}
		
		if (expected instanceof Integer && actual instanceof Integer) {
			didAssertEqualsIntInt = true;
		}
		
		if (expected instanceof String && actual instanceof String) {
			didAssertEqualsStringString = true;
		}
		
		inv.proceed(expected, actual);
	}
	
	@Mock
	public static void assertEquals(Invocation inv, Object expected, Object actual, String message) {
		if (expected instanceof Boolean && actual instanceof Boolean) {
			didAssertEqualsBooleanBoolean = true;
		}
		
		if (expected instanceof Integer && actual instanceof Integer) {
			didAssertEqualsIntInt = true;
		}
		
		if (expected instanceof String && actual instanceof String) {
			didAssertEqualsStringString = true;
		}
		
		inv.proceed(expected, actual, message);
	}
	
	private static boolean didAssertTrue = false;
	
	@Mock
	public static void assertTrue(Invocation inv, boolean condition) {
		didAssertTrue = true;
		inv.proceed(condition);
	}
	
	@Mock
	public static void assertTrue(Invocation inv, boolean condition, String message) {
		didAssertTrue = true;
		inv.proceed(condition, message);
	}
	
	/**
	 * Called on the fake class to indicate that an assertTrue was recorded.
	 * 
	 * @return true if assertTrue was called
	 */
	public static boolean didAssertTrue() {
		return didAssertTrue;
	}
	
	private static boolean didAssertFalse = false;
	
	@Mock
	public static void assertFalse(Invocation inv, boolean condition) {
		didAssertFalse = true;
		inv.proceed(condition);
	}
	
	@Mock
	public static void assertFalse(Invocation inv, boolean condition, String message) {
		didAssertFalse = true;
		inv.proceed(condition, message);
	}
	
	/**
	 * Called on the fake class to indicate that an assertFalse was recorded.
	 * 
	 * @return true if assertFalse was called
	 */
	public static boolean didAssertFalse() {
		return didAssertFalse;
	}
	
	
	
	private static boolean didAssertThrows = false;
	
	/**
	 * Called on the fake class to indicate that an assertThrows was recorded.
	 * 
	 * @return true if assertThrows was called
	 */
	public static boolean didAssertThrows() {
		return didAssertThrows;
	}
	
	@Mock
	public static <T extends Throwable> T assertThrows(
			Invocation inv,
			Class<T> expectedType, 
			Executable executable) {
		didAssertThrows = true;
		return inv.proceed(expectedType, executable);
	}
	
	@Mock
	public static <T extends Throwable> T assertThrows(
			Invocation inv,
			Class<T> expectedType, 
			Executable executable,
			String message) {
		didAssertThrows = true;
		return inv.proceed(expectedType, executable, message);
	}
}
