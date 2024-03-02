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
 * JUnit5 extension that 
 * 
 * <ol>
 * <li>Checks that the student's unit test method is properly annotated with <code>{@literal @}Test</code></li>
 * <li>Runs the student's unit test method (note this and the <code>{@literal @}Test</code> check both happen <i>after</i> all meta-tests have been run)</li>
 * </ol>
 * 
 * @author Lewis Baumstark
 *
 */
public class HeavyMeta implements AfterAllCallback {

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
