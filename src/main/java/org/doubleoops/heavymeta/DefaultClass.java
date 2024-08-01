package org.doubleoops.heavymeta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Allows specifying a default Class object
 * as a parameter.
 */
@Target(ElementType.PARAMETER)
@Inherited
public @interface DefaultClass {
	Class<?> value();
}
