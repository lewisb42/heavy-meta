package org.doubleoops.mosh;

/**
 * Values to tailor Mosh to your particular grading setup.
 */
final class MoshConfiguration {
	/**
	 * Most of the time, this should be the only value you change;
	 * if all classes in your project are in this package or a sub-
	 * package then they will be searched properly.
	 */
	public static final String TOP_LEVEL_PACKAGE = "org.doubleoops.mosh";
	
	/**
	 * Gets a list of package names to include in the search for tests.
	 * 
	 * This should, at the least, included TOP_LEVEL_PACKAGE, but in special
	 * cases may also need others searched.
	 * 
	 * The packages will be searched recursively, so if you specify "edu.myuni",
	 * it will also search "edu.myuni.cs1" (if such exists), "edu.myuni.cs1.project5",
	 * etc.
	 * 
	 * @return a list of package names as described above.
	 */
	public static String[] getSearchPackages() {
		return new String[] { 
				TOP_LEVEL_PACKAGE,
				"kitchen",
				"health",
				"weather"
		};
	}
}
