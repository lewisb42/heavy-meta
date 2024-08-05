package org.doubleoops.mosh;

import health.unittests.heartrate.TestGetHeartRateZone;

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
				.addStudentTestClasses(TestGetHeartRateZone.class)
				.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
