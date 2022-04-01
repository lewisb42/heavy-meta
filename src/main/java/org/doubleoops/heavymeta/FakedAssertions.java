package org.doubleoops.heavymeta;

import org.junit.jupiter.api.Assertions;

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
	public static void assertEquals(int expected, int actual) {
		didAssertEqualsIntInt = true;
	}
	
	@Mock
	public static void assertEquals(int expected, int actual, String message) {
		didAssertEqualsIntInt = true;
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
	public static void assertTrue(boolean condition) {
		didAssertTrue = true;
	}
	
	@Mock
	public static void assertTrue(boolean condition, String message) {
		didAssertTrue = true;
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
