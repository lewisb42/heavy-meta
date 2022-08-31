package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

/**
 * <p>
 * HeavyMeta is a JUnit5 extension for simplifying certain aspects of
 * writing meta-tests.
 * </p>
 * 
 * <p><strong>Usage</strong></p>
 * 
 * <p>
 * Turn a vanilla JUnit5 test class into a Meta-Tests class by registering the 
 * HeavyMeta extension as a class variable, e.g.:
 * </p>
 * 
 * <pre>
 * public class MetaTestForSomeStudentsUnitTest {
 * 
 *  {@literal @}RegisterExtension
 *  static HeavyMeta metaTester = new HeavyMeta(NameOfStudentsTestClass.class, "testNameOfStudentsUnitTestMethod");
 *  
 * }
 * </pre>
 * 
 * <p>
 * This will automatically perform the following standard actions when your meta-test suite is run:
 * </p>
 * 
 * <ol>
 * <li>Checks that the student's test class can be instantiated with a 0-parameter constructor</li>
 * <li>Checks that the student's unit test method exists in that class</li>
 * <li>Checks that the student's unit test method is properly annotated with <code>{@literal @}Test</code></li>
 * <li>Runs the student's unit test method (note this and the <code>{@literal @}Test</code> check both happen <i>after</i> all meta-tests have been run)</li>
 * </ol>
 * 
 * <p>
 * Additionally, calling <code>metaTester.runStudentsTestIgnoreFails()</code> is a standard Act 
 * stage for most meta-tests. (<code>runStudentsTestExpectToPass()</code> is public, but rarely used directly,
 * as the framework automatically uses it to check that students' tests pass.)
 * </p>
 * <p>
 * See the project README and examples directory for more examples of use.
 * </p>
 * @author Lewis Baumstark
 *
 */
public class HeavyMeta implements AfterAllCallback {

	private Class<? extends Object> testClass;
	private String testMethodName;
	private Method testMethod;
	private Object testClassInstance;
	
	/**
	 * Configures the extension to run the given test method from the given class.
	 * 
	 * @param testClass the test-class-under-(meta)test.
	 * @param testMethodName the name of the test-method-under-(meta)test
	 * @throws IllegalArgumentException if testClass or testMethodName is null, or if testMethodName is blank
	 * @throws AssertionFailedError if the test method does not exist on the test class, or if the test class cannot be instantiated with a zero-parameter constructor
	 */
	public HeavyMeta(Class<? extends Object> testClass, String testMethodName) {
		if (testClass == null) {
			throw new IllegalArgumentException("testClass can't be null");
		}
		
		if (testMethodName == null) {
			throw new IllegalArgumentException("testMethodName can't be null");
		}
		
		if (testMethodName.isBlank()) {
			throw new IllegalArgumentException("testMethodName can't be blank/empty");
		}
		
		this.testClass = testClass;
		this.testMethodName = testMethodName;
		
		try {
			this.testMethod = testClass.getDeclaredMethod(testMethodName);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionFailedError("could not find test method " + testMethodName + " on class " + testClass.getName());
		} 
		
		try {
			this.testClassInstance = testClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AssertionFailedError("could not instantiate an object of type " + testClass.getName());
		} 
	}


	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		String message = "";
		try {
			assertIsTestMethod(this.testClass, this.testMethodName);
		} catch (AssertionFailedError e) {
			message += e.getMessage();
		}
		
		try {
			runStudentsTestExpectToPass();
		} catch (AssertionFailedError e) {
			message +=  " " + e.getMessage();
		}
		
		if (!message.isEmpty()) {
			throw new AssertionFailedError(message);
		}
	}
	
	/**
	 * Helper to run the student's unit test, with no expectation of passing or failing.
	 * 
	 * Typically this is done in a meta-tests Act stage, after its Arrange stage has
	 * configured fake objects with instrumentation
	 */
	public void runStudentsTestIgnoreFails() {
		HeavyMeta.shouldPassOrFail(() -> {
			HeavyMeta.runBeforeEachMethods(this.testClassInstance);
			this.testMethod.invoke(this.testClassInstance);
		});
	}
	
	/**
	 * Runs the student's test, expecting it to pass (i.e., no exceptions thrown).
	 * 
	 * @throws AssertionFailedError if the student's test does not pass
	 */
	public void runStudentsTestExpectToPass() {
		HeavyMeta.shouldPass(() -> {
			HeavyMeta.runBeforeEachMethods(this.testClassInstance);
			this.testMethod.invoke(this.testClassInstance);
		}, "Your unit test does not pass as-written. Other error messages may have clues as to why this occurred.");
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
		safeAssertEquals(originalSize, set.size(), msg);
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
	 * @throws AssertionFailedError if the unitTestUnderTest fails for any reason (failed assertion or unexpected exception)
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
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 */
	public static void shouldPassOrFail(Executable unitTestUnderTest) {
		try {
			unitTestUnderTest.execute();
		} catch (Throwable e) {
			// don't print any underlying exceptions!
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
	 * @throws AssertionFailedError if the unitTestUnderTest fails for any reason (failed assertion or unexpected exception)
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
	 * @throws AssertionFailedError if the unitTestUnderTest fails for any reason (failed assertion or unexpected exception)
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
			System.err.println("Original exception message: " + e.getMessage());
			e.printStackTrace();
			throw new AssertionFailedError(passMessage);
		}
		
		// if we made it here it means the student test *passed*, 
		// thus *this* test fails
		throw new AssertionFailedError(passMessage);
	}
	
	/**
	 * Run any method on testClassObject that is annotated with BeforeEach.
	 * 
	 * This is automatically performed by the HeavyMeta extension and should not
	 * normally be called directly.
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
