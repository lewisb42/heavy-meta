package org.doubleoops.mosh;

import java.util.HashMap;
import java.util.Map;

class MoshDocument {
	// key is meta-test class name
	private final Map<String, MTNode> metaTests = new HashMap<String, MTNode>();
	
	public boolean isEmpty() { return metaTests.isEmpty(); }
	
	/**
	 * Creates a new meta-test node in the document if it does
	 * not already exist.
	 * 
	 * @param metaTestClassName the name of the meta-test class
	 */
	public void createMTNodeIfAbsent(String metaTestClassName) {
		if (!metaTests.containsKey(metaTestClassName)) {
			var mtNode = new MTNode(metaTestClassName);
			metaTests.put(metaTestClassName, mtNode);
		}
	}
	
	/**
	 * Retrieves the given node.
	 * 
	 * @param metaTestClassName name of the meta-test class to retrieve
	 * @return the MTNode associated with that class
	 */
	public MTNode getMTNode(String metaTestClassName) {
		validate(metaTests.containsKey(metaTestClassName));
		return metaTests.get(metaTestClassName);
	}

	private static void validate(boolean cond) {
		if (!cond) {
			throw new RuntimeException("Unexpected Error");
		}
	}
}
