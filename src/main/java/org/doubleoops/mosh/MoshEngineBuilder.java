package org.doubleoops.mosh;

/**
 * Builder for configuring MoshEngine.
 */
public class MoshEngineBuilder {

	private MoshEngineBuilder() {
		
	}
	
	public static MoshEngineBuilder newBuilder() {
		return new MoshEngineBuilder();
	}
	
	public static MoshEngine build() {
		throw new UnsupportedOperationException();
	}
	
	public static MoshEngineBuilder includeMetaTestClasses(String... classNames) {
		throw new UnsupportedOperationException();
	}
	
	public static MoshEngineBuilder includeMetaTestPackages(String... packageNames) {
		throw new UnsupportedOperationException();
	}
	
	public static MoshEngineBuilder includeStudentUnitTestClasses(String... classNames) {
		throw new UnsupportedOperationException();
	}
	
	public static MoshEngineBuilder includeStudentUnitTestPackages(String... packageNames) {
		throw new UnsupportedOperationException();
	}
}
