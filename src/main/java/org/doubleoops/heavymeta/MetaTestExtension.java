package org.doubleoops.heavymeta;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.opentest4j.AssertionFailedError;
import static org.doubleoops.heavymeta.HeavyMeta.*;

public class MetaTestExtension implements BeforeAllCallback {

	private Class<? extends Object> testClass;
	private String testMethodName;
	private Method testMethod;
	private Object testClassInstance;
	
	/**
	 * Configures the extension to run the given test method from the given class.
	 * 
	 * @param testClass the test-class-under-(meta)test
	 * @param testMethodName the name of the test-method-under-(meta)test
	 */
	public MetaTestExtension(Class<? extends Object> testClass, String testMethodName) {
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
	public void beforeAll(ExtensionContext context) throws Exception {
		System.out.println("called beforeAll");
		assertIsTestMethod(this.testClass, this.testMethodName);
	}
	
	/**
	 * Helper to run the student's unit test, with no expectation of passing or failing.
	 */
	public void runStudentsTestIgnoreFails() {
		HeavyMeta.shouldPassOrFail(() -> {
			HeavyMeta.runBeforeEachMethods(this.testClassInstance);
			this.testMethod.invoke(this.testClassInstance);
		});
	}

}
