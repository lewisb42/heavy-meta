package weather.metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.unittests.measurement.TestConstructor;
import weather.unittests.weatherservice.TestFindMaximumTemperature;

public class MetaTestShouldCreateValidMeasurement {
	TestConstructor unitTests;

	@RegisterExtension
	private static HeavyMeta metaTester = new HeavyMeta(TestConstructor.class, "testShouldCreateValidMeasurement");
	
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
		
		metaTester.runStudentsTestIgnoreFails();
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
		
		metaTester.runStudentsTestIgnoreFails();
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Disabled("template only")
	@Test
	public void template() {
		var expectations = new Expectations() {

			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}

