package weather.metatests.weatherservice.findmaximumtemperature;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.MetaTestBase;
import org.doubleoops.heavymeta.MetaTestConfig;
import org.doubleoops.heavymeta.MockedUpAssertEqualsForInt;
import org.junit.jupiter.api.Test;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.codeundertest.WeatherService;
import weather.unittests.weatherservice.TestFindMaximumTemperature;

@MetaTestConfig
(
	testClass=TestFindMaximumTemperature.class,
	testMethodName="testWhenOneMeasurement"
)
public class MetaTestWhenOneMeasurement extends MetaTestBase {
	
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
		
		runStudentsTestIgnoreFails();
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
		
		runStudentsTestIgnoreFails();
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
			boolean usedValidAssertion = false;
			
			@Override
			protected void establishExpectations() {
				expect(usedValidAssertion, 
						"You did not use an appropriate assertion (e.g., assertEquals) in your Assert stage");
			}
		};
		
		new MockedUpAssertEqualsForInt() {
			@Mock
			public void assertEquals(int expected, int actual) {
				expectations.usedValidAssertion = true;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void actualValueOfAssertionShouldComeFromActMethod() {
		final int sentinelValue = 82739274;
		
		var expectations = new Expectations( ) {
			int assertionActualValue = Integer.MIN_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(assertionActualValue == sentinelValue,
						"Use the return value of your findMaximumTemperature() as the actual value (2nd parameter) of your assertEquals()");
			}
		};
		
		new MockedUpAssertEqualsForInt() {		
			@Mock
			public void assertEquals(int e, int a) {
				expectations.assertionActualValue = a;
			}
		};
		
		new MockUp<WeatherService>() {
			@Mock
			public int findMaximumTemperature() {
				// return something unlikely;
				// we'll check this is what arrives at the assertEquals
				return sentinelValue; 
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void expectedValueShouldEqualMeasurementsTemperature() {
		
		var expectations = new Expectations( ) {
			int assertionsExpectedValue = Integer.MIN_VALUE;
			int measurementsTemperature = Integer.MAX_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(assertionsExpectedValue == measurementsTemperature,
						"Your assertion's expected value should equal the temperature you passed to the constructor");
			}
		};
		
		new MockedUpAssertEqualsForInt() {
			@Mock
			public void assertEquals(int e, int a) {
				expectations.assertionsExpectedValue = e;
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public void $init(Invocation inv, String location, int temperature) {
				expectations.measurementsTemperature = temperature;
				inv.proceed(location, temperature);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
