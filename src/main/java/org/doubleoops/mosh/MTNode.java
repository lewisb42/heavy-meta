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
	
	public STNode getSTNode(String studentClassName, String studentMethodName) {
		var compositeKey = buildSTNodeKey(studentClassName, studentMethodName);
		return studentTests.get(compositeKey);
	}
	
	public STNode createSTNodeIfAbsent(
			String classUnderTest, 
			String methodUnderTest) {
		
		var key = buildSTNodeKey(classUnderTest, methodUnderTest);
		if (!studentTests.containsKey(key)) {
			var stNode = new STNode(methodUnderTest, classUnderTest);
			studentTests.put(key, stNode);
			return stNode;
		} else {
			return studentTests.get(key);
		}
	}
	
	private static String buildSTNodeKey(String classUnderTest, String methodUnderTest) {
		return String.join("::", classUnderTest, methodUnderTest);
	}
}
