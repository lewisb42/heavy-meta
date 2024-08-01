package org.doubleoops.heavymeta;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver;

public class StudentTestClassResolver implements ParameterResolver {

	

	@Override
	public Class<?> resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		var parm = parameterContext.getParameter();
		var classAnnotation = parameterContext.findAnnotation(DefaultClass.class);
		if (classAnnotation.isPresent()) {
			return classAnnotation.get().value();
		}
		
		throw new ParameterResolutionException("Encountered Class-type parameter without @DefaultClass annotation");
	}

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return parameterContext.isAnnotated(DefaultClass.class);
	}

}
