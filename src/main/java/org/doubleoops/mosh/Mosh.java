package org.doubleoops.mosh;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.Filter;
import org.junit.platform.engine.discovery.ClassNameFilter;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.engine.discovery.PackageNameFilter;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;


public class Mosh {
	private final List<Method> metaTestMethods = new ArrayList<Method>();
	private final List<Method> studentTestMethods = new ArrayList<Method>();
	
	
	public Mosh() {
		discoverMetaTests();
		discoverStudentTests();
	}
	
	private void discoverStudentTests() {
		var filter = new StudentTestFilter();
		discoverTests(filter, studentTestMethods);
	}
	
	private void discoverTests(ClassNameFilter filter, List<Method> toList) {

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
			
			testPlan.accept(new TestPlan.Visitor() {
				public void visit(TestIdentifier ident) {
					try {
						MethodSource src = (MethodSource)ident.getSource().get();
						Method meth = src.getJavaMethod();
						toList.add(meth);
						
						// debug
						Class<?> klass = meth.getDeclaringClass();
						String dn = new DisplayNameGenerator.IndicativeSentences().generateDisplayNameForMethod(klass, meth);
						System.out.println(dn);
					} catch (ClassCastException | NoSuchElementException e) {
						
					}
				}
			});
			
		} catch (Exception e) {
			throw new RuntimeException("Error discovering Meta-Tests found in project.");
		}
	}
	
	private void discoverMetaTests() {
		var metaTestFilter = new MetaTestFilter();
		discoverTests(metaTestFilter, metaTestMethods);
	}
	
	@Test
	void dummyTest() {
		
	}
}
