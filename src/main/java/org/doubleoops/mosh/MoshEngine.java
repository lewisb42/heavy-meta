package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	//private static final UniqueId ID = UniqueId.root("mosh","engine");
	/**
	 * Called by main() to set the tool in motion.
	 */
	public void run() {

		 var metaTestFilter = new MetaTestFilter();
		
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
