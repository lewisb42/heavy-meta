package metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.assertIsTestMethod;
import static org.doubleoops.heavymeta.HeavyMeta.shouldPass;
import static org.doubleoops.heavymeta.HeavyMeta.when;
import static org.doubleoops.heavymeta.HeavyMeta.whenNotEqual;
import static org.doubleoops.heavymeta.HeavyMeta.whenNotNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import model.Measurement;
import studenttests.measurement.TestConstructor;

class ShouldAllowTemperatureAtBoundaryTests {

	TestConstructor unitTests;
	
	@BeforeEach
	void setup() {
		assertIsTestMethod(TestConstructor.class, "testShouldAllowTemperatureAtBoundary");
		unitTests = new TestConstructor();
	}

	@Test
	void studentsTestShouldHandleNonMutatedMeasurement() {
		// mutate the class-under-test
		new MockUp<Measurement>() {
			@Mock void $init(Invocation invocation, String location, int temperatureInCelsius) {
				whenNotNull(location);
				whenNotEqual("", location);
				when(temperatureInCelsius == -273);
				invocation.proceed(location, temperatureInCelsius);
			}
		};
		
		shouldPass(() -> {
			unitTests.testShouldAllowTemperatureAtBoundary();
		});
	}

}
