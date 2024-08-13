package org.doubleoops.heavymeta;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class StudentTestClassResolver extends TypeBasedParameterResolver<Class<?>> {

	

	@Override
	public Class<?> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		var annotatedElement = parameterContext.getAnnotatedElement();
		var classAnnotation = annotatedElement.getAnnotation(DefaultClass.class);
		if (classAnnotation != null) {
			return classAnnotation.value();
		}
		
		throw new ParameterResolutionException("Unknown annotation type");
	}

}
