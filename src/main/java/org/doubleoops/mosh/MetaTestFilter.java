package org.doubleoops.mosh;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.discovery.ClassNameFilter;

public class MetaTestFilter implements ClassNameFilter {

	@Override
	public FilterResult apply(String className) {
		try {
			var klazz = Class.forName(className);
			if (MetaTestBase.class.isAssignableFrom(klazz)) {
				return FilterResult.included("Child of MetaTestBase");
			}
		} catch (ClassNotFoundException e) {
			//System.err.println(className + " Class not found");
			return FilterResult.excluded("Class not found");
		} catch (ClassCastException e) {
			//System.err.println(className + " Not a child of MetaTestBase");
			return FilterResult.excluded("Not a child of MetaTestBase");
		}
		
		//System.err.println(className + " defaulty exclusions");
		return FilterResult.excluded("Not a child of MetaTestBase");
	}


}
