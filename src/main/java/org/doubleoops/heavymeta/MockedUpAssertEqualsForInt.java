package org.doubleoops.heavymeta;

import org.junit.jupiter.api.Assertions;

import mockit.Mock;
import mockit.MockUp;

/**
 * Convenience class of mocked assertEquals(int, int [, String]).
 * 
 * Implement the abstract assertEquals(int,int) for your
 * desired mocked assertions behavior, and this class will
 * guarantee assertEquals(int, int, String) performs the same
 * way.
 * 
 * @author lewisb
 *
 */
public abstract class MockedUpAssertEqualsForInt 
	extends MockUp<Assertions> {
	
	/**
	 * Override this for a cleaner MockUp<Assertions>
	 * 
	 * @param expected
	 * @param actual
	 */
	public abstract void assertEquals(int expected, int actual);
	
	@Mock
	public void assertEquals(int expected, int actual, String msg) {
		assertEquals(expected, actual);
	}
}
