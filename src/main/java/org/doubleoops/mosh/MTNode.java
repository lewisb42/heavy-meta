package org.doubleoops.mosh;

import java.util.ArrayList;
import java.util.List;

class MTNode {
	final String name;
	final List<STNode> studentTests = new ArrayList<STNode>();
	
	public MTNode(String name) { this.name = name; }
}
