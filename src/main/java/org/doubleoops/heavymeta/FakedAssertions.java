package org.doubleoops.heavymeta;

import org.junit.jupiter.api.Assertions;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

/**
 * A faked version of JUnit5's Assertions class,
 * used to verify that certain assertions have been
 * called.
 * 
 * @author lewisb
 *
 */
public class FakedAssertions extends MockUp<Assertions> {

	private static boolean didAssertEqualsIntInt = false;
	private static boolean didAssertTrue = false;
	
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
}
