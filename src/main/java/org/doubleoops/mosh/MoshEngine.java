package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.reporting.legacy.xml.LegacyXmlReportGeneratingListener;
import org.junit.platform.reporting.open.xml.OpenTestReportGeneratingListener;

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
						metaTestFilter
					)
					.configurationParameter("junit.platform.reporting.open.xml.enabled", "true")
					.configurationParameter("junit.platform.reporting.output.dir", "./open-xml-reports")
					.build();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			session.getLauncher().execute(testPlan);
		}
	}
}
