package org.doubleoops.mosh;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MTNode {
	final String name;
	
	// key is the (method) name of the STNode
	final Map<String, STNode> studentTests = new HashMap<String, STNode>();
	
	public MTNode(String name) { this.name = name; }
	
	public STNode getSTNode(String name) {
		return studentTests.get(name);
	}
	
	public void createSTNodeIfAbsent(
			String classUnderTest, 
			String methodUnderTest, 
			String metaTestMethod, 
			String status) {
		if (!studentTests.containsKey(methodUnderTest)) {
			var stNode = new STNode(methodUnderTest, classUnderTest);
			studentTests.put(methodUnderTest, stNode);
			if (status.equals("SUCCESSFUL")) {
				stNode.passed(metaTestMethod);
			} else {
				stNode.failed(metaTestMethod);
			}
		}
	}
}
