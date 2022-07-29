package metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import model.Measurement;
import studenttests.measurement.TestConstructor;
import studenttests.weatherservice.TestFindMaximumTemperature;

class ShouldCreateValidMeasurementTests {
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
//	void studentsTestShouldHandleNonMutatedMeasurement() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock void $init(Invocation invocation, String location, int temperatureInCelsius) {
//				whenNotNull(location);
//				whenNotEqual("", location);
//				when(temperatureInCelsius > -273);
//				invocation.proceed(location, temperatureInCelsius);
//			}
//		};
//		
//		shouldPass(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
//
//	@Test
//	void studentsTestShouldFailWhenGetLocationReturnsNull() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock String getLocation() {
//				return null;
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
//
//	@Test
//	void studentsTestShouldFailWhenGetLocationReturnsEmptyString() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock String getLocation() {
//				return "";
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
//
//	@Test 
//	void studentsTestShouldFailWhenGetLocationReturnsWrongValue() {
//		
//		new MockUp<Measurement>() {
//			@Mock 
//			public void $init(Invocation inv, String location, int temperatureInCelsius) {
//				when(temperatureInCelsius >= -273);
//				whenNotNull(location);
//				whenNot(location.isEmpty());
//				inv.proceed(location, temperatureInCelsius);
//			}
//			
//			@Mock 
//			public String getLocation() {
//				return "q2w3sedxcftg6y7hunjk";
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
//
//	@Test 
//	void studentsTestShouldFailWhenGetTemperatureInCelsiusReturnsWrongValue() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock 
//			public int getTemperatureInCelsius() {
//				return Integer.MAX_VALUE;
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
//
//	@Test
//	void studentsTestShouldFailWhenGetTemperatureInCelsiusReturnsInvalidValue() {
//		// mutate the class-under-test
//		new MockUp<Measurement>() {
//			@Mock 
//			public int getTemperatureInCelsius() {
//				return -274;
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testShouldCreateValidMeasurement();
//		});
//	}
}

