package weather.metatests.weatherservice.findmaximumtemperature;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.doubleoops.heavymeta.FakedAssertions;
import org.doubleoops.heavymeta.HeavyMeta;
import org.doubleoops.heavymeta.SafeAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.codeundertest.WeatherService;
import weather.unittests.weatherservice.TestFindMaximumTemperature;

/**
 * <strong>Meta-test example</strong>
 * 
 * <table style="border: solid 1px black;">
 * 	<caption>Relevant Classes and Methods</caption>
 * 	<tr>
 * 		<td><strong>Class being unit-tested</strong></td>
 * 		<td>{@link weather.codeundertest.WeatherService WeatherService}</td>
 * 	</tr>
 * 	<tr>
 * 		<td><strong>Method being unit-tested</strong></td>
 * 		<td>{@link weather.codeundertest.WeatherService#findMaximumTemperature() findMaximumTemperature()}</td>
 * 	</tr>
 * 	<tr>
 * 		<td><strong>Test class being meta-tested</strong></td>
 * 		<td>{@link weather.unittests.weatherservice.TestFindMaximumTemperature TestFindMaximumTemperature}</td>
 * 	</tr>
 * 	<tr>
 * 		<td><strong>Unit test method being meta-tested</strong></td>
 * 		<td>{@link weather.unittests.weatherservice.TestFindMaximumTemperature#testWhenOneMeasurement() testWhenOneMeasurement}</td>
 * 	</tr>
 * </table>
 * 
 * 
 * @author Lewis Baumstark
 *
 */
public class MetaTestWhenOneMeasurement {
	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestFindMaximumTemperature.class, "testWhenOneMeasurement");
	
	/**
	 * Meta-test that ensures the proper Arrange components exist for this test:
	 * <ol>
	 * <li>a WeatherService object has been instantiated</li>
	 * <li>exactly one Measurement object has been instantiated</li>
	 * <li>the Measurement object has been added to the WeatherService</li>
	 * </ol>
	 */
	@Test
	public void shouldHaveArrangeStage() {
		
		var fakeWeatherService = new MockUp<WeatherService>() {
			public boolean createdWeatherService = false;
			public int measurementCount = 0;
			
			@Mock
			public void $init(Invocation inv) {
				createdWeatherService = true;
				inv.proceed();
			}
			
			@Mock
			public void add(Measurement m) {
				measurementCount++;
			}
		};
		
		var fakeMeasurement = new MockUp<Measurement>() {
			public Measurement createdMeasurement = null;
			
			public boolean didCreate = false;
			
			@Mock
			public void $init(Invocation inv, String location, int temperature) {
				didCreate = true;
				inv.proceed(location, temperature);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeWeatherService.createdWeatherService,
				"Did not initialize a WeatherService object in Arrange stage");
		
		assertTrue(fakeMeasurement.didCreate,
				"Did not initialize a Measurement object in Arrange stgge");
		
		assertEquals(1, fakeWeatherService.measurementCount,
				"Did not add exactly 1 Measurement to the WeatherService.");

	}
	
	/**
	 * Meta-test that ensures the student's unit test
	 * called the Act method (here, findMaximumTemperature()).
	 * 
	 * It does not check anything other than that the method
	 * was called.
	 */
	@Test
	public void shouldHaveActStage() {
		var fakeWeatherService = new MockUp<WeatherService>() {
			public boolean didAct = false;
			
			@Mock
			public int findMaximumTemperature(Invocation inv) {
				didAct = true;
				return inv.proceed();
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeWeatherService.didAct,
				"Did not not call findMaximumTemperature() in the Act stage");
	}

	/**
	 * Meta-test to check that an appropriate assertion was
	 * called (here, assertEquals(int, int) or assertEquals(int,int, String))
	 * 
	 * It does not check anything other than that the method
	 * was called.
	 */
	@Test
	public void shouldHaveAssertStage() {
		new FakedAssertions();
		
		metaTester.runStudentsTestIgnoreFails();
		
		SafeAssertions.safeAssertTrue(FakedAssertions.didAssertEqualsIntInt(), 
				"You did not use an appropriate assertion (e.g., assertEquals) in your Assert stage");
	}
	
	@Test
	public void actualValueOfAssertionShouldComeFromActMethod() {
		final int sentinelValue = 82739274;
		
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
				return sentinelValue; 
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		SafeAssertions.safeAssertEquals(fakeAssertions.actualValue, sentinelValue,
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
		
		metaTester.runStudentsTestIgnoreFails();
		
		SafeAssertions.safeAssertEquals(fakeAssertions.expectedValue, fakeWeatherService.actualValue,
				"The expected value of the assertion must match the return value of findMaximumTemperature()");
	}
}
