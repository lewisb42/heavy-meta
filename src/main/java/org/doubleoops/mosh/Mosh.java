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
	
	private PackageNameFilter systemPackagesFilter;
	
	public Mosh() {
		systemPackagesFilter = PackageNameFilter.excludePackageNames(
				"java", "javax", "sun", "org.junit");
		discoverMetaTests();
	}
	
	private void discoverMetaTests() {
		var metaTestFilter = new MetaTestFilter();
		
		//var pkgs = Package.getPackages();
		var pkgs = new String[] {
			"org.doubleoops.heavymeta",
			"org.doubleoops.mosh",
			"health",
			"kitchen"
		};
		var packageSelectors = Arrays.asList(pkgs).stream()
//							.map(x -> x.getName())
//							.filter(x -> !x.startsWith("java"))
//							.filter(x -> !x.startsWith("sun"))
//							.filter(x -> !x.startsWith("org.junit"))
//							.filter(x -> !x.startsWith("org.eclipse"))
//							.filter(x -> !x.startsWith("jdk"))
//							.filter(x -> !x.startsWith("org.apiguardian"))
//							.filter(x -> !x.startsWith("org.opentest4j"))
							.map(DiscoverySelectors::selectPackage)
							.collect(Collectors.toUnmodifiableList());
		
		LauncherDiscoveryRequest discoveryRequest = 
				LauncherDiscoveryRequestBuilder.request()
					.filters(systemPackagesFilter, metaTestFilter)
					.selectors(packageSelectors)
					.build();
		
		try (LauncherSession session = LauncherFactory.openSession()) {
			TestPlan testPlan = session.getLauncher().discover(discoveryRequest);
			
			testPlan.accept(new TestPlan.Visitor() {
				public void visit(TestIdentifier ident) {
					try {
						MethodSource src = (MethodSource)ident.getSource().get();
						Method meth = src.getJavaMethod();
						metaTestMethods.add(meth);
						
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
	
	@Test
	void dummyTest() {
		
	}
}
