package weather.metatests.measurement.constructor;

import static org.junit.jupiter.api.Assertions.*;
import static org.doubleoops.heavymeta.HeavyMeta.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.unittests.measurement.TestConstructor;

class ShouldNotAllowNullLocationTests {
	TestConstructor unitTests;

	@Test
	public void deprecated() {
		fail("These tests need to be rewritten.");
	}
	
//	@BeforeEach
//	void setup() {
//		assertIsTestMethod(TestConstructor.class, "testShouldCreateValidMeasurement");
//		unitTests = new TestConstructor();
//	}
//
//	@Test
//	void studentsTestShouldPassWhenLocationIsNull() {
//		// check the constructor's params
//		new MockUp<Measurement>() {
//			@Mock 
//			public void $init(Invocation invocation, String location, int temperatureInCelsius) {
//				// assertions to make sure the location is null
//				whenNull(location);
//				// assertion that the temperature is valid
//				when(temperatureInCelsius > -273);
//				invocation.proceed(location, temperatureInCelsius);
//			}
//		};
//		
//		shouldPass(() -> unitTests.testShouldNotAllowNullLocation(),
//				"testShouldNotAllowNullLocation should have passed when the location was null");
//	}
//
//	@Test 
//	void studentsTestShouldFailWhenLocationIsNotNull() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock 
//			public void $init(Invocation invocation, String location, int temperatureInCelsius) {
//				// assertions to make sure the location is a valid string
//				whenNotNull(location);
//				whenNotEqual("", location);
//				// assertion that the temperature is valid
//				when(temperatureInCelsius > -273);
//				invocation.proceed(location, temperatureInCelsius);
//			}
//		};
//		
//		shouldFail(() -> unitTests.testShouldNotAllowNullLocation(),
//				"testShouldnotAllowNullLocation should have failed when presented with valid data for location and temperatureInCelsius");
//	}
//
//	@Test
//	void studentsTestShouldFailWhenLocationIsNotNullButTemperatureIsInvalid() {
//		// this test checks to see that the student's test isn't passing for
//		// the wrong reason: invalid temperature
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock 
//			public void $init(Invocation invocation, String location, int temperatureInCelsius) {
//				when(temperatureInCelsius <= -273);
//				whenNotNull(location);
//				invocation.proceed(location, temperatureInCelsius);
//			}
//		};
//		
//		shouldFail(() -> unitTests.testShouldNotAllowNullLocation(),
//				"testShouldnotAllowNullLocation passes, but for the wrong reason: temperatureInCelsius is invalid, not location");
//	}
}

