package org.doubleoops.heavymeta;

import org.doubleoops.mosh.MoshEngine;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class StudentTestClassResolver extends TypeBasedParameterResolver<Class<?>> {

	

	@Override
	public Class<?> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		var testClass = extensionContext
				.getConfigurationParameter(
						MoshEngine.STUDENT_TEST_CLASS_CONFIG_KEY);
		
		if (testClass.isPresent()) {
			try {
				return getClass().getClassLoader().loadClass(testClass.get());
			} catch (ClassNotFoundException e) {
				throw new ParameterResolutionException("Could not load class");
			}
		}
		
		
		var annotatedElement = parameterContext.getAnnotatedElement();
		var classAnnotation = annotatedElement.getAnnotation(DefaultClass.class);
		if (classAnnotation != null) {
			return classAnnotation.value();
		}
		
		throw new ParameterResolutionException("Unknown annotation type");
	}

}
