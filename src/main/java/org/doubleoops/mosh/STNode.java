package org.doubleoops.mosh;

class STNode {
	final String methodName;
	final String className;
	private final PFNode passedMetaTests = new PFNode();
	private final PFNode failedMetaTests = new PFNode();
	
	public STNode(String methodName, String className) {
		super();
		this.methodName = methodName;
		this.className = className;
	}
	
	public double percentagePasses() {
		double p = passedMetaTests.count();
		double f = failedMetaTests.count();
		return 100 * p / (p + f);
	}
	
	public void passed(String metaTestMethod) {
		passedMetaTests.add(metaTestMethod);
	}
	
	public void failed(String metaTestMethod) {
		failedMetaTests.add(metaTestMethod);
	}
}
