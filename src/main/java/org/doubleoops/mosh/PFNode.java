package org.doubleoops.mosh;

import java.util.ArrayList;
import java.util.List;

public class PFNode {
	private final List<String> metaTestMethods = new ArrayList<String>();
	
	public int count() { return metaTestMethods.size(); }
	
	public void add(String metaTestMethod) {
		metaTestMethods.add(metaTestMethod);
	}
}
