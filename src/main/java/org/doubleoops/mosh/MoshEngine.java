package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.apache.commons.io.FileUtils;
/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	private static final String OPEN_XML_REPORT_DIR = "./open-xml-reports";

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
					.configurationParameter("junit.platform.reporting.output.dir", OPEN_XML_REPORT_DIR)
					.build();
		
		cleanReportDirectory();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			session.getLauncher().execute(testPlan);
		}
	}

	private static void cleanReportDirectory() {
		try {
			FileUtils.cleanDirectory(new File(OPEN_XML_REPORT_DIR));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
