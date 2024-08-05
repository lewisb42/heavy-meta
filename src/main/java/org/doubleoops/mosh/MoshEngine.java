package org.doubleoops.mosh;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.engine.config.JupiterConfiguration;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
/**
 * Executor for the mosh tool.
 */
public class MoshEngine {

	public static final String STUDENT_TEST_METHOD_CONFIG_KEY = "org.doubleoops.mosh.studenttestmethod";
	public static final String STUDENT_TEST_CLASS_CONFIG_KEY = "org.doubleoops.mosh.studenttestclass";

	private List<PackageSelector> metaTestPackages;
	private List<ClassSelector> metaTestClasses;
	private final MoshDocument docRoot = new MoshDocument();
	private final MoshExecutionListener moshExecutionListener;
	
	private List<Class<?>> studentTestClasses;
	
	private MoshEngine() {
		metaTestPackages = new ArrayList<PackageSelector>();
		metaTestClasses = new ArrayList<ClassSelector>();
		studentTestClasses = new ArrayList<Class<?>>();
		moshExecutionListener = new MoshExecutionListener(docRoot);
	}
	
	/**
	 * Use this to build a MoshEngine (instead of instantiating directly).
	 * 
	 * @return a new MoshEngine instance.
	 */
	public static MoshEngine newInstance() {
		return new MoshEngine();
	}
	
	/**
	 * Adds the indicated packages to be searched for MetaTest classes.
	 * 
	 * @param packages the package names
	 * @return this MoshEngine object (to be used in building)
	 */
	public MoshEngine addMetaTestPackages(String... packages) {
		
		for (var p : packages) {
			this.metaTestPackages.add(selectPackage(p));
		}
		
		return this;
	}
	
	/**
	 * Adds the indicated student-submitted classes to be checked for matching
	 * test methods.
	 * 
	 * @param classes the classes to add
	 * @return this engine
	 */
	public MoshEngine addStudentTestClasses(Class<?>... classes) {
		studentTestClasses.addAll(Arrays.asList(classes));
		return this;
	}
	
	/**
	 * Adds the indicated classes. If they are not MetaTests they will be ignored.
	 * 
	 * @param classes the class names
	 * @return this MoshEngine object (to be used in building)
	 */
	public MoshEngine addMetaTestClasses(Class<?>... classes) {
		
		for (var c : classes) {
			this.metaTestClasses.add(selectClass(c));
		}
		
		return this;
	}
	
	public void run() throws Exception {
		for (var klazz : studentTestClasses) {
			Method[] methods = klazz.getDeclaredMethods();
			for (var method : methods) {
				executeMetaTestsFor(klazz, method.getName());
			}
		}
	}
	
	//private static final UniqueId ID = UniqueId.root("mosh","engine");
	/**
	 * Called by main() to set the tool in motion.
	 * @throws Exception 
	 */
	private void executeMetaTestsFor(Class<?> studentTestClass, String studentTestMethod) throws Exception {
		var metaTestFilter = new MetaTestFilter();
		
		LauncherDiscoveryRequest discoveryRequest = 
				LauncherDiscoveryRequestBuilder.request()
					.selectors(metaTestPackages)
					.selectors(metaTestClasses)
					.filters(metaTestFilter)
					.configurationParameter(JupiterConfiguration.PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, "false")
					.configurationParameter(STUDENT_TEST_CLASS_CONFIG_KEY, studentTestClass.getCanonicalName())
					.configurationParameter(STUDENT_TEST_METHOD_CONFIG_KEY, studentTestMethod)
					.build();
		
		LauncherConfig config = LauncherConfig.builder()
				.addTestExecutionListeners(moshExecutionListener)
				.addTestExecutionListeners(new TestExecutionListener() {
					// debugging only
					public void
					executionStarted(TestIdentifier testIdentifier) {
						System.out.println(testIdentifier.getDisplayName());
					}
				})
				.build();
		
		try (LauncherSession session = LauncherFactory.openSession(config)) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			var launcher = session.getLauncher();
			launcher.execute(testPlan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MoshDocument report() {
		return docRoot;
	}
}
