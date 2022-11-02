package weather.metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.BeforeEach;
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
}

