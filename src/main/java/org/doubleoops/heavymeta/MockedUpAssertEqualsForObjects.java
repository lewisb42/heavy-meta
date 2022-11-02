package org.doubleoops.heavymeta;

import org.junit.jupiter.api.Assertions;

import mockit.Mock;
import mockit.MockUp;

/**
 * Convenience class of mocked assertEquals(Object, Object [, String]),
 * and assertSame(Object, Object [, String]).
 * 
 * Implement the abstract assertEquals(Object,Object) for your
 * desired mocked assertions behavior, and this class will
 * guarantee assertEquals(Object, Object, String) and
 * assertSame(Object, Object [,String]) perform the same
 * way.
 * 
 * @author lewisb
 *
 */
public abstract class MockedUpAssertEqualsForObjects 
	extends MockUp<Assertions> {
	
	/**
	 * Override this for a cleaner MockUp<Assertions>
	 * 
	 * @param expected
	 * @param actual
	 */
	public abstract void assertEquals(Object expected, Object actual);
	
	@Mock
	public void assertEquals(Object expected, Object actual, String msg) {
		assertEquals(expected, actual);
	}
	
	@Mock
	public void assertSame(Object expected, Object actual) {
		assertEquals(expected, actual);
	}
	
	@Mock
	public void assertSame(Object expected, Object actual, String msg) {
		assertSame(expected, actual);
	}
}
