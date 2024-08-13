package org.doubleoops.mosh;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
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
}
