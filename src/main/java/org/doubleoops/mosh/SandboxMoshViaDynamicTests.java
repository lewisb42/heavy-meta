package org.doubleoops.mosh;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import health.metatests.heartrate.getheartratezone.MetaTestShouldGetZoneOneAboveAerobicBoundary;
import health.unittests.heartrate.TestGetHeartRateZone;

@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
public class SandboxMoshViaDynamicTests {

	
	
	final List<Method> studentTestMethods;
	
	SandboxMoshViaDynamicTests() throws NoSuchMethodException, SecurityException {
		studentTestMethods = Arrays.asList(
				TestGetHeartRateZone.class.getMethod("testShouldGetZoneAtAerobicBoundary"),
				TestGetHeartRateZone.class.getMethod("testShouldGetZoneOneAboveAerobicBoundary"),
				TestGetHeartRateZone.class.getMethod("testShouldGetZoneOneBelowAerobicBoundary")
		);
	}
	
	
	@TestFactory
	List<DynamicNode> metaTestShouldGetZoneOneAboveAerobicBoundary() {
		var metaTests = new ArrayList<DynamicNode>();
		var tm1 = "testShouldGetZoneOneAboveAerobicBoundary";
		var mt1 = new MetaTestShouldGetZoneOneAboveAerobicBoundary(TestGetHeartRateZone.class, tm1);
		var dc1 = dynamicContainer(tm1, 
				URI.create("method:health.unittests.heartrate.TestGetHeartRateZone#testShouldGetZoneOneAboveAerobicBoundary"), 
				Arrays.asList(
				dynamicTest("shouldHaveArrangeStage", () -> {
					mt1.shouldHaveArrangeStage();
				}),
				dynamicTest("shouldHaveActStage", () -> mt1.shouldHaveActStage()),
				dynamicTest("shouldHaveAssertStage", () -> mt1.shouldHaveAssertStage()),
				dynamicTest("actualValueShoudlComeFromActStageReturnValue", () -> mt1.actualValueShouldComeFromActStageReturnValue()),
				dynamicTest("runStudentUnitTestExpectToPass", () -> mt1.runStudentsTestExpectToPass()),
				dynamicTest("checkForTestAnnotation", () -> mt1.checkForTestAnnotation())
				).stream());
		
		var tm2 = "testShouldGetZoneAtAerobicBoundary";
		var mt2 = new MetaTestShouldGetZoneOneAboveAerobicBoundary(TestGetHeartRateZone.class, tm2);
		var dc2 = dynamicContainer(tm2, Arrays.asList(
				dynamicTest("shouldHaveArrangeStage", () -> mt2.shouldHaveArrangeStage()),
				dynamicTest("shouldHaveActStage", () -> mt2.shouldHaveActStage()),
				dynamicTest("shouldHaveAssertStage", () -> mt2.shouldHaveAssertStage()),
				dynamicTest("actualValueShoudlComeFromActStageReturnValue", () -> mt2.actualValueShouldComeFromActStageReturnValue()),
				dynamicTest("runStudentUnitTestExpectToPass", () -> mt2.runStudentsTestExpectToPass()),
				dynamicTest("checkForTestAnnotation", () -> mt2.checkForTestAnnotation())
				));
		
		var tm3 = "testShouldGetZoneOneBelowAerobicBoundary";
		var mt3 = new MetaTestShouldGetZoneOneAboveAerobicBoundary(TestGetHeartRateZone.class, tm3);
		var dc3 = dynamicContainer(tm3, Arrays.asList(
				dynamicTest("shouldHaveArrangeStage", () -> mt3.shouldHaveArrangeStage()),
				dynamicTest("shouldHaveActStage", () -> mt3.shouldHaveActStage()),
				dynamicTest("shouldHaveAssertStage", () -> mt3.shouldHaveAssertStage()),
				dynamicTest("actualValueShoudlComeFromActStageReturnValue", () -> mt3.actualValueShouldComeFromActStageReturnValue()),
				dynamicTest("runStudentUnitTestExpectToPass", () -> mt3.runStudentsTestExpectToPass()),
				dynamicTest("checkForTestAnnotation", () -> mt3.checkForTestAnnotation())
				));
		
		metaTests.addAll(Arrays.asList(dc1, dc2, dc3));
		
		return metaTests;
	}

}
