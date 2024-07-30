package org.doubleoops.mosh;

class STNode {
	final String methodName;
	final String className;
	final PFNode passes = new PFNode();
	final PFNode fails = new PFNode();
	
	public STNode(String methodName, String className) {
		super();
		this.methodName = methodName;
		this.className = className;
	}
	
	public double percentagePasses() {
		double p = passes.count();
		double f = fails.count();
		return 100 * p / (p + f);
	}
}
