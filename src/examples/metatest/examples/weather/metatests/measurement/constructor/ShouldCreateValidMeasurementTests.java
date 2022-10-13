package examples.weather.metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import examples.weather.modelclasses.Measurement;
import examples.weather.studentunittests.measurement.TestConstructor;
import examples.weather.studentunittests.weatherservice.TestFindMaximumTemperature;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

public class ShouldCreateValidMeasurementTests {
	TestConstructor unitTests;

	@RegisterExtension
	private static HeavyMeta metaTester = new HeavyMeta(TestConstructor.class, "testShouldCreateValidMeasurement");
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var fakeMeasurement = new MockUp<Measurement>() {
			boolean didCreate = false;
			
			@Mock
			public void $init(String location, int temperature) {
				didCreate = true;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeMeasurement.didCreate,
				"Did not instantiate a Measurement object in the Arrange stage.");
	}
}

