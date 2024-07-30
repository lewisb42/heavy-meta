package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import org.opentest4j.reporting.tooling.converter.DefaultConverter;
/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	private static final String EVENTS_XML_FILENAME_REGEX = "junit-platform-events.*\\.xml";
	private static final String HIERARCHICAL_XML_FILENAME = "heavy-meta-report.xml";
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
		
		convertReportToHierachicalForm();
	}

	/**
	 * The open-xml reporting listener generates an event-based
	 * xml file. For our analysis it needs to be in their hierachical (tree-based)
	 * form. They handily provide tooling for this.
	 */
	private void convertReportToHierachicalForm() {
		Path reportsDir = Path.of(OPEN_XML_REPORT_DIR);
		Path eventsXmlFile = null;
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(reportsDir, f -> {
			return f.toFile().getName().matches(EVENTS_XML_FILENAME_REGEX);
		})) {
			for (Path file: stream) {
				eventsXmlFile = file;
				break; // just need the first entry
			}
			
			if (eventsXmlFile == null) throw new IllegalArgumentException();
			
			Path hierarchicalXmlFile = Path.of(OPEN_XML_REPORT_DIR, HIERARCHICAL_XML_FILENAME);
			var converter = new DefaultConverter();
			try {
				converter.convert(eventsXmlFile, hierarchicalXmlFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
