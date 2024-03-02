package org.doubleoops.heavymeta;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
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

	
	/*
	 * TODO: re-design the extension so that:
	 * -- this extension is declarative (i.e., via annotation not field)
	 * -- afterAll gets the class and method info from its ExecutionContext parameter
	 * -- ExecutionContext searches annotated object for specific class and method fields
	 * 		-- hopefully in the inherited MetaTest class
	 * -- speaking of the MetaTest class, just about everything not related to the 
	 * 		afterAll() should be moved into that. It should have a constructor that sets
	 * 		the class and method fields
	 * -- don't forget that ExtendWith is inherited, so we should be able to tag it onto
	 *    MetaTestBase and not have to do so with classes that extend MetaTestBase
	 */
	
	

	
	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		Class<?> metaTestClass = context.getRequiredTestClass();
		Object metaTestClassInstance = metaTestClass.getDeclaredConstructor().newInstance();
		assertAll(
				() -> {
					Method method = metaTestClass.getMethod("checkForTestAnnotation");
					method.invoke(metaTestClassInstance);
				},
				() -> {
					Method method = metaTestClass.getMethod("runStudentsTestExpectToPass");
					method.invoke(metaTestClassInstance);
				}
			);
	}
	
	

}
