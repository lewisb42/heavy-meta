package org.doubleoops.mosh;

import java.util.List;

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
	
	public int passCount() { return passedMetaTests.count(); }
	public int failCount() { return failedMetaTests.count(); }
	
	public List<String> getPassedMethodNames() {
		return passedMetaTests.getMethodNames();
	}
	
	public List<String> getFailedMethodNames() {
		return failedMetaTests.getMethodNames();
	}
	
	public void markAsPassed(String metaTestMethod) {
		passedMetaTests.add(metaTestMethod);
	}
	
	public void markAsFailed(String metaTestMethod) {
		failedMetaTests.add(metaTestMethod);
	}
	
	
}
