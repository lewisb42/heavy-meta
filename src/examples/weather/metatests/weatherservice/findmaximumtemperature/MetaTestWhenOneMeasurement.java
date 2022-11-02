package weather.metatests.weatherservice.findmaximumtemperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.FakedAssertions;
import org.doubleoops.heavymeta.HeavyMeta;
import org.doubleoops.heavymeta.SafeAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.codeundertest.WeatherService;
import weather.unittests.weatherservice.TestFindMaximumTemperature;

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
		
		var expectations = new Expectations() {
			boolean didCreateWeatherService = false;
			boolean didCreateMeasurement = false;
			int numberMeasurementsAdded = 0;
			Measurement addedMeasurement = null;
			
			@Override
			protected void establishExpectations() {
				expect(didCreateWeatherService,
						"Did not initialize a WeatherService object in Arrange stage");
				
				expect(didCreateMeasurement,
						"Did not initialize a Measurement object in Arrange stgge");
				
				expect(numberMeasurementsAdded == 1 && addedMeasurement != null,
						"Did not add exactly 1 Measurement to the WeatherService.");
			}
		};
		
		new MockUp<WeatherService>() {
			@Mock
			public void $init(Invocation inv) {
				expectations.didCreateWeatherService = true;
				inv.proceed();
			}
			
			@Mock
			public void add(Measurement m) {
				expectations.addedMeasurement = m;
				expectations.numberMeasurementsAdded++;
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public void $init(Invocation inv, String location, int temperature) {
				expectations.didCreateMeasurement = true;
				inv.proceed(location, temperature);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
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
		
		var expectations = new Expectations( ) {
			boolean didAct = false;
			
			@Override
			protected void establishExpectations() {
				expect(didAct,
						"Did not not call findMaximumTemperature() in the Act stage");
			}
		};
		
		new MockUp<WeatherService>() {
			@Mock
			public int findMaximumTemperature(Invocation inv) {
				expectations.didAct = true;
				return inv.proceed();
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
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
		
		var expectations = new Expectations( ) {

			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				
			}
		};
		
		new FakedAssertions();
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
		SafeAssertions.safeAssertTrue(FakedAssertions.didAssertEqualsIntInt(), 
				"You did not use an appropriate assertion (e.g., assertEquals) in your Assert stage");
	}
	
	@Test
	public void actualValueOfAssertionShouldComeFromActMethod() {
		final int sentinelValue = 82739274;
		
		var expectations = new Expectations( ) {

			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				
			}
		};
		
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
		expectations.assertPassed();
		SafeAssertions.safeAssertEquals(fakeAssertions.actualValue, sentinelValue,
				"Use the return value of your findMaximumTemperature() as the actual value (2nd parameter) of your assertEquals()");
	}
	
	@Test
	public void expectedValueShouldBeSameAsReturnOfFindMaximumTemperature() {
		
		var expectations = new Expectations( ) {

			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				
			}
		};
		
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
		expectations.assertPassed();
		SafeAssertions.safeAssertEquals(fakeAssertions.expectedValue, fakeWeatherService.actualValue,
				"The expected value of the assertion must match the return value of findMaximumTemperature()");
	}
}
