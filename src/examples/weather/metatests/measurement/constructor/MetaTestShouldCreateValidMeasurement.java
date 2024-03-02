package weather.metatests.measurement.constructor;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.unittests.measurement.TestConstructor;

public class MetaTestShouldCreateValidMeasurement extends MetaTestBase {
	private static final Class<?> DEFAULT_TEST_CLASS = TestConstructor.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testShouldCreateValidMeasurement";
	
	public MetaTestShouldCreateValidMeasurement() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var expectations = new Expectations() {
			boolean didCreateMeasurement = false;
			String locationParam = null;
			int temperatureParam = Integer.MIN_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(didCreateMeasurement,
						"Did not instantiate a Measurement object in the Arrange stage.");
				expect(locationParam != null && !locationParam.isEmpty(),
						"Use a non-null, non-empty String for the location parameter");
				expect(temperatureParam >= -273,
						"Use a temperature parameter >= -273");
				
			}
		};
		
		new MockUp<Measurement>() {			
			@Mock
			public void $init(Invocation inv, String location, int temperature) {
				expectations.didCreateMeasurement = true;
				expectations.locationParam = location;
				expectations.temperatureParam = temperature;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveAssertStage() {
		var expectations = new Expectations() {
			boolean hasLocationAssertion = false;
			boolean hasTemperatureAssertion = false;
			
			@Override
			protected void establishExpectations() {
				expect(hasLocationAssertion,
						"Did not have an assertEquals to check the Measurement's getLocation()");
				expect(hasTemperatureAssertion,
						"Did not have an assertEquals to check the Measurement's getTemperatureInCelsius()");
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (expected instanceof String && actual instanceof String) {
					expectations.hasLocationAssertion = true;
				}
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertEquals(int expected, int actual) {
				expectations.hasTemperatureAssertion = true;
			}
			
			@Mock
			public void assertEquals(int expected, int actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void locationAssertionActualValueShouldComeFromGetLocation() {
		final String sentinelLocation = "INGUKNIGIUH";
		
		var expectations = new Expectations() {
			String actualValueFromAssertion = null;
			
			@Override
			protected void establishExpectations() {
				expect(actualValueFromAssertion.equals(sentinelLocation),
						"Did not use the return value of getLocation() as the actual value for your location assertion");
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public String getLocation() {
				return sentinelLocation;
			};
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (actual instanceof String) {
					expectations.actualValueFromAssertion = (String) actual;
				}
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void locationAssertionExpectedValueShouldThatInConstructor() {
		
		var expectations = new Expectations() {
			String assertionExpectedValue = null;
			String locationFromConstructor = null;
			
			@Override
			protected void establishExpectations() {
				expect(assertionExpectedValue.equals(locationFromConstructor),
						"Expected value of your location assertion should be the same String as the location you passed to the constructor");
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public void $init(String location, int temp) {
				expectations.locationFromConstructor = location;
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (expected instanceof String) {
					expectations.assertionExpectedValue = (String) expected;
				}
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void temperatureAssertionActualValueShouldComeFromGetTemperatureInCelcius() {
		final int sentinelTemperature = Integer.MIN_VALUE;
		
		var expectations = new Expectations() {
			int actualValueFromAssertion = Integer.MAX_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(actualValueFromAssertion == sentinelTemperature,
						"Did not use the return value of getTemperatureInCelsius() as the actual value for your temperature assertion");
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public int getTemperatureInCelsius() {
				return sentinelTemperature;
			};
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(int expected, int actual) {
				expectations.actualValueFromAssertion = actual;
			}
			
			@Mock
			public void assertEquals(int expected, int actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void temperatureAssertionExpectedValueShouldThatInConstructor() {
		
		var expectations = new Expectations() {
			int assertionExpectedValue = Integer.MAX_VALUE;
			int temperatureFromConstructor = Integer.MIN_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(assertionExpectedValue == temperatureFromConstructor,
						"Expected value of your temperature assertion should be the same int as the temperature you passed to the constructor");
			}
		};
		
		new MockUp<Measurement>() {
			@Mock
			public void $init(Invocation inv, String location, int temp) {
				expectations.temperatureFromConstructor = temp;
				inv.proceed(location, temp);
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(int expected, int actual) {
				expectations.assertionExpectedValue = expected;
			}
			
			@Mock
			public void assertEquals(int expected, int actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}

