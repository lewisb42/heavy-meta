package org.doubleoops.mosh;

import org.doubleoops.heavymeta.MetaTestBase;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.PackageNameFilter.includePackageNames;

import org.junit.platform.engine.DiscoveryFilter;
import org.junit.platform.engine.FilterResult;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.launcher.LauncherDiscoveryListener;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import health.metatests.heartrate.getheartratezone.MetaTestShouldGetZoneAtAerobicBoundary;

/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	//private static final UniqueId ID = UniqueId.root("mosh","engine");
	/**
	 * Called by main() to set the tool in motion.
	 */
	public void run() {

		 var metaTestFilter = new ClassNameFilter() {
			 
			@Override
			public FilterResult apply(String className) {
				try {
					var klazz = Class.forName(className);
					if (MetaTestBase.class.isAssignableFrom(klazz)) {
						return FilterResult.included("Child of MetaTestBase");
					}
				} catch (ClassNotFoundException e) {
					//System.err.println(className + " Class not found");
					return FilterResult.excluded("Class not found");
				} catch (ClassCastException e) {
					//System.err.println(className + " Not a child of MetaTestBase");
					return FilterResult.excluded("Not a child of MetaTestBase");
				}
				
				//System.err.println(className + " defaulty exclusions");
				return FilterResult.excluded("Not a child of MetaTestBase");
			}
			 
		 };
		
		LauncherDiscoveryRequest discoveryRequest = 
				LauncherDiscoveryRequestBuilder.request()
					.selectors(
						selectPackage("health.metatests")
					)
					.filters(
						//includeClassNamePatterns("^(.+[.]MetaTest.*)$") // note: gunk at beginning of regex is for fully-qualified w/ package
						metaTestFilter
					)
					.build();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			
			testPlan.accept(new TestPlan.Visitor() {
				public void visit(TestIdentifier testIdentifier) {
					// TODO: may need to "filter" out vanilla @Test's at this stage
					if (isMetaTestClass(testIdentifier)) {
						System.out.println(testIdentifier.getDisplayName());
					}
				}
			});
		}
	}

	protected static boolean isMetaTestClass(TestIdentifier testIdentifier) {
		if (!testIdentifier.isContainer()) return false;
		
		return true;
	}
}
