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
}
