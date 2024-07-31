package org.doubleoops.mosh;

/**
 * Main class for the mosh project.
 */
public class Main {

	/**
	 * Entry point for the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MoshEngine.newInstance()
				.addMetaTestPackages("kitchen.metatests", "health.metatests")
				.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
