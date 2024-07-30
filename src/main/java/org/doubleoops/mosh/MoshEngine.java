package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import org.opentest4j.reporting.tooling.converter.DefaultConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	/*
	 * used as placeholder in generated csv table for when the xml
	 * experienced unexpected things. Doing this as silent-fail-and-move-on
	 * strategy.
	 */
	private static final String UNDEFINED = "UNDEFINED";
	private static final String EVENTS_XML_FILENAME_REGEX = "junit-platform-events.*\\.xml";
	private static final String HIERARCHICAL_XML_FILENAME = "heavy-meta-report.xml";
	private static final String OPEN_XML_REPORT_DIR = "./open-xml-reports";

	private List<PackageSelector> packages;
	private List<MoshRecord> records;
	
	public MoshEngine(String... packages) {
		this.packages = new ArrayList<PackageSelector>();
		for (var p : packages) {
			this.packages.add(selectPackage(p));
		}
		
		records = new ArrayList<MoshRecord>();
	}
	
	
	//private static final UniqueId ID = UniqueId.root("mosh","engine");
	/**
	 * Called by main() to set the tool in motion.
	 * @throws Exception 
	 */
	public void run() throws Exception {

		 var metaTestFilter = new MetaTestFilter();
		
		LauncherDiscoveryRequest discoveryRequest = 
				LauncherDiscoveryRequestBuilder.request()
					.selectors(packages)
					.filters(metaTestFilter)
					.configurationParameter("junit.platform.reporting.open.xml.enabled", "true")
					.configurationParameter("junit.platform.reporting.output.dir", OPEN_XML_REPORT_DIR)
					.build();
		
		cleanReportDirectory();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			session.getLauncher().execute(testPlan);
		}
		
		convertReportToHierachicalForm();
		parseHierarchicalXmlToFlatTable();
		generateGraderReport(System.out);
	}

	private void generateGraderReport(PrintStream out) {
		class Tally {
			int passes = 0;
			int fails = 0;
			double percentage() { return ((double)passes) / total(); }
			double total() { return passes + fails; }
		}
		
		// key is canonical name from MoshRecord
		Map<String, Tally> tallies = new HashMap<String, Tally>();
		
		// create a blank table
		for (var record : records) {
			var key = record.canonicalName();
			if (!tallies.containsKey(key)) {
				tallies.put(key, new Tally());
			}
		}
		
		// tally up SUCCESS vs. FAIL/ERROR
		for (var record : records) {
			var key = record.canonicalName();
			var tally = tallies.get(key);
			if (record.status.equals("SUCCESSFUL")) {
				tally.passes++;
			} else {
				tally.fails++;
			}
		}
		
		// write out the report
		tallies.forEach((canonicalName, tally) -> {
			String result = canonicalName + "{ ";
			result += "pass:" + tally.passes;
			result += " ";
			result += "fail:" + tally.fails;
			result += " ";
			result += String.format("percent:%.1f", 100*tally.percentage());
			result += " }";
			out.println(result);
		});
	}


	/**
	 * The open-xml reporting listener generates an event-based
	 * xml file. For our analysis it needs to be in their hierachical (tree-based)
	 * form. They handily provide tooling for this.
	 * @throws Exception 
	 */
	private void convertReportToHierachicalForm() throws Exception {
		Path reportsDir = Path.of(OPEN_XML_REPORT_DIR);
		Path eventsXmlFile = null;
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(reportsDir, f -> {
			return f.toFile().getName().matches(EVENTS_XML_FILENAME_REGEX);
		})) {
			for (Path file: stream) {
				eventsXmlFile = file;
				break; // just need the first entry
			}
			
			if (eventsXmlFile == null) throw new IllegalStateException();
			
			Path hierarchicalXmlFile = Path.of(OPEN_XML_REPORT_DIR, HIERARCHICAL_XML_FILENAME);
			var converter = new DefaultConverter();
			converter.convert(eventsXmlFile, hierarchicalXmlFile);
		}
	}

	private static void cleanReportDirectory() throws IOException {
			FileUtils.cleanDirectory(new File(OPEN_XML_REPORT_DIR));
	}
	
	private void parseHierarchicalXmlToFlatTable() throws ParserConfigurationException, SAXException, IOException {
		var infile = Path.of(OPEN_XML_REPORT_DIR, HIERARCHICAL_XML_FILENAME).toFile();
		var docBuilder = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder();
		var doc = docBuilder.parse(infile);
		doc.getDocumentElement().normalize();
		
		var metaTestClasses = collectMetaTestClasses(doc);
		
		// debug
		//metaTests.forEach(t -> System.out.println(t.getAttribute("name")));
		
		for (var metaTestClass: metaTestClasses) {
			String metaTestClassName = metaTestClass.getAttribute("name");
			String classUnderTest = retrieveEntryTagText(metaTestClass, "studentUnitTestClass");
			String methodUnderTest = retrieveEntryTagText(metaTestClass, "studentUnitTestName");;
			
			var common = String.join(",", metaTestClassName, classUnderTest, methodUnderTest);
			 
			var metaTestMethods = metaTestClass.getElementsByTagName("h:child");
			for (int i = 0; i < metaTestMethods.getLength(); i++) {
				var metaTestMethod = (Element)metaTestMethods.item(i);
				String metaTestMethodName = findMetaTestMethodName(metaTestMethod);
				String status = findMetaTestStatus(metaTestMethod);
				
				var record = new MoshRecord(
						classUnderTest,
						methodUnderTest,
						metaTestClassName,
						metaTestMethodName,
						status);
				records.add(record);
			}
		}
	}
	
	private static String findMetaTestStatus(Element metaTestMethod) {
		var resultElmts = metaTestMethod.getElementsByTagName("result");
		
		if (resultElmts.getLength() == 0) {
			return UNDEFINED;
		}
		
		var status = ((Element)resultElmts.item(0)).getAttribute("status");
		if (status == null) {
			return UNDEFINED;
		} else {
			return status;
		}
	}

	private static String findMetaTestMethodName(Element metaTestMethod) {
		// TODO Auto-generated method stub
		var name = metaTestMethod.getAttribute("name");
		if (name == null || name.isBlank()) return UNDEFINED;
		return name;
	}

	private static String retrieveEntryTagText(Element metaTest, String key) {
		var entries = metaTest.getElementsByTagName("entry");
		
		for (int i = 0; i < entries.getLength(); i++) {
			var entry = (Element)entries.item(i);
			var keyAttr = entry.getAttribute("key") ;
			if (keyAttr.equals(key)) {
				return entry.getTextContent();
			}
		}
		
		return UNDEFINED;
	}

	private static List<Element> collectMetaTestClasses(Document doc) {
		var hChilds = doc.getElementsByTagName("h:child");
		
		List<Element> metaTests = new ArrayList<Element>();
		
		/*
		 * Yes, I know how to for-each and use streams, etc.
		 * NodeList is NOT iterable or streamable so I have to
		 * fall back to standard for-loop. Don't judge.
		 */
		for (int i = 0; i < hChilds.getLength(); i++) {
			var elmt = (Element)hChilds.item(i);
			if (isMetaTestElement(elmt)) {
				metaTests.add(elmt);
			}
		}
		
		return metaTests;
	}

	/*
	 * Meta-tests show up as containers in the xml, e.g.
	 * 
	 * <h:child ...>
	 * 	<metadata>
	 * 		<junit:type>CONTAINER</junit:type>
	 * 	</metadata>
	 * </h:child ...>
	 * 
	 * Notes:
	 * -- the root container should not be passed to this;
	 * 	precondition (unenforced) is that elmt is h:child
	 */
	private static boolean isMetaTestElement(Element elmt) {
		var junitTypeElements = elmt.getElementsByTagName("junit:type");
		if (junitTypeElements.getLength() == 0) return false;
		var typeElement = junitTypeElements.item(0);
		var type = typeElement.getFirstChild().getNodeValue();
		return type.equals("CONTAINER");
	}

	static class MoshRecord {
		final String classUnderTest;
		final String methodUnderTest; // qualified with its class name, e.g., "MyClass::testStuff"
		final String metaTestClass;
		final String metaTestMethod;
		final String status;
		
		public MoshRecord(String classUnderTest, String methodUnderTest, String metaTestClass, String metaTestMethod,
				String status) {
			super();
			this.classUnderTest = classUnderTest;
			this.methodUnderTest = methodUnderTest;
			this.metaTestClass = metaTestClass;
			this.metaTestMethod = metaTestMethod;
			this.status = status;
		}
		
		public String canonicalName() {
			return "[StudentSubmission: " + classUnderTest + "::" + methodUnderTest + "] MetaTest: " + metaTestClass;
		}
	}
}
