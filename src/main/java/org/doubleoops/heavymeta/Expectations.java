package org.doubleoops.heavymeta;

import org.opentest4j.AssertionFailedError;

/**
 * Sub-class (usually by anonymous inner class) to establish expectations
 * for a meta-test, after which they can be validated by calling assertPassed
 * 
 * @author lewisb
 *
 */
public abstract class Expectations {
	
	/**
	 * Called within the overridden establishExpectations() method
	 * to list out expectations for a meta-test.
	 * 
	 * @param cond the condition expected to be true
	 * @param failureMessage the failure message if not true (will be reported by JUnit)
	 */
	protected void expect(boolean cond, String failureMessage) {
		if (!cond) {
			throw new AssertionFailedError(failureMessage);
		}
	}
	
	/**
	 * Called in the meta-test assertion stage to check that all expectations
	 * were validated.
	 */
	public void assertPassed() {
		establishExpectations();
	}
	
	/**
	 * Overridden at the beginning of a meta-test to set out what should be
	 * true at the end of the test.
	 */
	protected abstract void establishExpectations();
}
