package metatests.weatherservice.findmaximumtemperature;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.doubleoops.heavymeta.FakedAssertions;
import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import model.Measurement;
import model.WeatherService;
import studenttests.weatherservice.TestFindMaximumTemperature;

class WhenOnlyOneMeasurementTests {
	
	private TestFindMaximumTemperature unitTests;
	
	@BeforeEach
	public void setup() {
		unitTests = new TestFindMaximumTemperature();
	}
	
	
	@Test
	public void studentsTestShouldBeTestMethod() {
		assertIsTestMethod(TestFindMaximumTemperature.class, "testWhenOneMeasurement");
	}
	
	@Test
	public void shouldHaveProperArrangeStage() {
		
		var fakeWeatherService = new MockUp<WeatherService>() {
			public boolean createdWeatherService = false;
			@Mock
			public void $init(Invocation inv) {
				createdWeatherService = true;
				inv.proceed();
			}
		};
		
		var fakeMeasurement = new MockUp<Measurement>() {
			
			public boolean didCreate = false;
			
			@Mock
			public void $init(Invocation inv, String location, int temperature) {
				didCreate = true;
				inv.proceed(location, temperature);
			}
		};
		
		shouldPassOrFail(() -> {
			runBeforeEachMethods(unitTests);
			unitTests.testWhenOneMeasurement();
		});
		
		assertTrue(fakeWeatherService.createdWeatherService,
				"Did not initialize a WeatherService object in Arrange stage");
		
		assertTrue(fakeMeasurement.didCreate,
				"Did not initialize a Measurement object in Arrange stgge");
	}
	
	@Test
	public void shouldHaveProperActStage() {
		var fakeWeatherService = new MockUp<WeatherService>() {
			public boolean didAct = false;
			
			@Mock
			public int findMaximumTemperature(Invocation inv) {
				didAct = true;
				return inv.proceed();
			}
		};
		
		shouldPassOrFail(() -> {
			runBeforeEachMethods(unitTests);
			unitTests.testWhenOneMeasurement();
		});
		
		assertTrue(fakeWeatherService.didAct,
				"Did not not call findMaximumTemperature() in the Act stage");
	}

	@Test
	public void shouldHaveProperAssertStage() {
		new FakedAssertions();
		
		shouldPassOrFail(() -> {
			runBeforeEachMethods(unitTests);
			unitTests.testWhenOneMeasurement();
		});
		
		safeAssertTrue(FakedAssertions.didAssertEqualsIntInt(), 
				"You did not use an appropriate assertion (e.g., assertEquals) in your Assert stage");
	}
	
	@Test
	public void actualValueOfAssertionShouldComeFromActMethod() {
		var fakeAssertions = new MockUp<Assertions>() {
			public static int actualValue = Integer.MAX_VALUE;
			
			@Mock
			public static void assertEquals(int e, int a) {
				actualValue = a;
			}
		};
		
		var fakeWeatherService = new MockUp<WeatherService>() {
			
			@Mock
			public int findMaximumTemperature() {
				// return something unlikely;
				// we'll check this is what arrives at the assertEquals
				return 82739274; 
			}
		};
		
		shouldPassOrFail(() -> {
			runBeforeEachMethods(unitTests);
			unitTests.testWhenOneMeasurement();
		});
		
		safeAssertEquals(fakeAssertions.actualValue, 82739274,
				"Use the return value of your findMaximumTemperature() as the actual value (2nd parameter) of your assertEquals()");
	}
	
	@Test
	public void expectedValueShouldBeSameAsReturnOfFindMaximumTemperature() {
		var fakeAssertions = new MockUp<Assertions>() {
			public static int expectedValue = Integer.MAX_VALUE;
			
			@Mock
			public static void assertEquals(int e, int a) {
				expectedValue = e;
			}
		};
		
		var fakeWeatherService = new MockUp<WeatherService>() {
			
			public int actualValue = Integer.MAX_VALUE;
			
			@Mock
			public int findMaximumTemperature(Invocation inv) {
				actualValue = inv.proceed();
				return actualValue; 
			}
		};
		
		shouldPassOrFail(() -> {
			runBeforeEachMethods(unitTests);
			unitTests.testWhenOneMeasurement();
		});
		
		safeAssertEquals(fakeAssertions.expectedValue, fakeWeatherService.actualValue,
				"The expected value of the assertion must match the return value of findMaximumTemperature()");
	}
}
