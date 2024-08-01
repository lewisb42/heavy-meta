package org.doubleoops.heavymeta;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class StudentTestMethodResolver extends TypeBasedParameterResolver<String> {

	@Override
	public String resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		var annotatedElement = parameterContext.getAnnotatedElement();
		var classAnnotation = annotatedElement.getAnnotation(DefaultString.class);
		if (classAnnotation != null) {
			return classAnnotation.value();
		}
		
		throw new ParameterResolutionException("Unknown annotation type " + annotatedElement.getAnnotations()[0]);
	}

}
