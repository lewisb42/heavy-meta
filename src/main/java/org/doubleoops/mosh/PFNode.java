package org.doubleoops.mosh;

import java.util.ArrayList;
import java.util.List;

public class PFNode {
	final List<String> metaTestMethods = new ArrayList<String>();
	
	public int count() { return metaTestMethods.size(); }
}
