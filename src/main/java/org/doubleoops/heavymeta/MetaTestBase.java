package org.doubleoops.heavymeta;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

/**
 * Base class of meta-tests.
 */
@ExtendWith(StandardMetaTestChecks.class)
public abstract class MetaTestBase {

	private Class<? extends Object> testClass;
	private String testMethodName;
	private Method testMethod;
	private Object testClassInstance;
	
	/**
	 * Configures the meta test to run the given test method from the given class.
	 * 
	 * @param testClass the test-class-under-(meta)test.
	 * @param testMethodName the name of the test-method-under-(meta)test
	 * @throws IllegalArgumentException if testClass or testMethodName is null, or if testMethodName is blank
	 * @throws AssertionFailedError if the test method does not exist on the test class, or if the test class cannot be instantiated with a zero-parameter constructor
	 */
	public MetaTestBase(Class<? extends Object> testClass, String testMethodName) {
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


	/**
	 * Ensures a student's submitted test is annotated with \@Test
	 */
	public void checkForTestAnnotation() {
		SafeAssertions.assertIsTestMethod(this.testClass, this.testMethodName);
	}
	
	
	
	
	/**
	 * Helper to run the student's unit test, with no expectation of passing or failing.
	 * 
	 * Typically this is done in a meta-tests Act stage, after its Arrange stage has
	 * configured fake objects with instrumentation
	 */
	public void runStudentsTestIgnoreFails() {
		MetaTestBase.shouldPassOrFail(() -> {
			MetaTestBase.runBeforeEachMethods(this.testClassInstance);
			this.testMethod.invoke(this.testClassInstance);
		});
	}
	
	/**
	 * Runs the student's test, expecting it to pass (i.e., no exceptions thrown).
	 * 
	 * @throws AssertionFailedError if the student's test does not pass
	 */
	public void runStudentsTestExpectToPass() {
		MetaTestBase.shouldPass(() -> {
			MetaTestBase.runBeforeEachMethods(this.testClassInstance);
			this.testMethod.invoke(this.testClassInstance);
		}, "Your unit test does not pass as-written. Other error messages may have clues as to why this occurred.");
	}
	
	/**
	 * Asserts the given unit test should pass.
	 * 
	 * @param unitTestUnderTest no-arg lambda containing the unit test code, typically as a method
	 * 	call to the test method in a separate test class.
	 * @param failureMessage test result message to display if unitTestUnderTest fails
	 * @throws AssertionFailedError if the unitTestUnderTest fails
	 */
	private static void shouldPass(Executable unitTestUnderTest, String failureMessage) throws AssertionFailedError {
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
	 * Run any method on testClassObject that is annotated with BeforeEach.
	 * 
	 * This is automatically performed by the HeavyMeta extension and should not
	 * normally be called directly.
	 * 
	 * @param testClassObject any object. If null the method simply returns.
	 */
	private static void runBeforeEachMethods(Object testClassObject) {
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
