package org.doubleoops.mosh;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;


@TestInstance(Lifecycle.PER_CLASS)
public class Mosh {
	private final List<Class<? extends MetaTestBase>> metaTestClasses = new ArrayList<Class<? extends MetaTestBase>>();
	private final List<Method> studentTestMethods = new ArrayList<Method>();
	
	
	public Mosh() {
		discoverMetaTests();
		discoverStudentTests();
	}
	
	private void discoverStudentTests() {
		var filter = new StudentTestFilter();
		discoverTests(filter, new TestPlan.Visitor() {
			public void visit(TestIdentifier ident) {
				try {
					MethodSource src = (MethodSource)ident.getSource().get();
					Method meth = src.getJavaMethod();
					studentTestMethods.add(meth);
					
					// debug
					Class<?> klass = meth.getDeclaringClass();
					String dn = new DisplayNameGenerator.IndicativeSentences().generateDisplayNameForMethod(klass, meth);
					System.out.println(dn);
				} catch (ClassCastException | NoSuchElementException e) {
					
				}
			}
		});
	}
	
	private void discoverTests(ClassNameFilter filter, TestPlan.Visitor visitor) {

		var packageSelectors = Arrays.asList(MoshConfiguration.getSearchPackages()).stream()
							.map(DiscoverySelectors::selectPackage)
							.collect(Collectors.toUnmodifiableList());
		
		LauncherDiscoveryRequest discoveryRequest = 
				LauncherDiscoveryRequestBuilder.request()
					.filters(filter, ClassNameFilter.excludeClassNamePatterns("org.doubleoops.mosh.Mosh"))
					.selectors(packageSelectors)
					.build();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			testPlan.accept(visitor);
			
		} catch (Exception e) {
			throw new RuntimeException("Error discovering Meta-Tests found in project.");
		}
	}
	
	private void discoverMetaTests() {
		var metaTestFilter = new MetaTestFilter();
		discoverTests(metaTestFilter, new TestPlan.Visitor() {
			public void visit(TestIdentifier ident) {
				try {
					ClassSource src = (ClassSource)ident.getSource().get();
					Class<? extends MetaTestBase> klass = (Class<? extends MetaTestBase>)src.getJavaClass();
					metaTestClasses.add(klass);
					
					System.out.println(klass.getCanonicalName());
				} catch (ClassCastException | NoSuchElementException e) {
					
				}
			}
		});
	}
	
	@Test
	void dummyTest() {
		
	}
	
	@TestFactory
	List<DynamicContainer> runAllMetaTestsAgainstAllStudentTests() {
		var resultsByMetaTestClass = new ArrayList<DynamicContainer>();
		
		for (var mtClass : metaTestClasses) {
			var studentTestsForThisMetaTestClass = new ArrayList<DynamicContainer>();
			for (var stMethod : studentTestMethods) {
				try {
					var dynTests = metaTestsForCandidateStudentTest(stMethod.getDeclaringClass(), stMethod.getName(), mtClass);
					studentTestsForThisMetaTestClass.add(dynTests);
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			URI mtUri = uriFor(mtClass);
			var metaTestContainer = dynamicContainer(mtClass.getName(), mtUri, studentTestsForThisMetaTestClass.stream());
			resultsByMetaTestClass.add(metaTestContainer);
		}
		
		return resultsByMetaTestClass;
	}
	
	private static URI uriFor(Class<?> klass) {
		var path = String.join(":", "class", klass.getCanonicalName());
		return URI.create(path);
	}

	private DynamicContainer metaTestsForCandidateStudentTest(
			Class<?> studentTestClass, 
			String studentTestMethodName,
			Class<? extends MetaTestBase> metaTestClass) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		var dynTests = new ArrayList<DynamicTest>();
		var mtConstructor = metaTestClass.getDeclaredConstructor(new Class<?>[] {Class.class, String.class });
		var mtInstance = mtConstructor.newInstance(studentTestClass, studentTestMethodName);
		
		for (var mtMethod : metaTestClass.getMethods()) {
			if (mtMethod.isAnnotationPresent(Test.class)) {
				var displayName = mtMethod.getName();
				URI mtMethodUri = uriFor(metaTestClass, displayName);
				var dynTest = dynamicTest(displayName, mtMethodUri, () -> {
					mtMethod.invoke(mtInstance);
				});
				dynTests.add(dynTest);
			}
		}
		
		URI stUri = uriFor(studentTestClass, studentTestMethodName);
		return dynamicContainer(studentTestMethodName, stUri, dynTests.stream());
	}

	private URI uriFor(Class<?> studentTestClass, String studentTestMethodName) {
		var path = String.join(":", "method", studentTestClass.getCanonicalName());
		path = String.join("#", path, studentTestMethodName);
		return URI.create(path);
	}
}
