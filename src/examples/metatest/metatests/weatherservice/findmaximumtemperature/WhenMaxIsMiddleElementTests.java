package metatests.weatherservice.findmaximumtemperature;

import static org.doubleoops.heavymeta.HeavyMeta.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import model.Measurement;
import model.WeatherService;
import studenttests.weatherservice.TestFindMaximumTemperature;

class WhenMaxIsMiddleElementTests {
	
	@Test
	public void deprecated() {
		fail("These tests need to be rewritten.");
	}
	
//	private TestFindMaximumTemperature unitTests;
//	
//	@BeforeEach
//	public void setup() {
//		assertIsTestMethod(TestFindMaximumTemperature.class, "testWhenMaxIsMiddleElement");
//		this.unitTests = new TestFindMaximumTemperature();
//	}
//	
//	@Test
//	void studentsTestShouldPassWhenMaxIsMiddleElement() {
//		new MockUp<WeatherService>() {
//			
//		};
//		
//		shouldPass(() -> {
//			unitTests.testWhenMaxIsMiddleElement();
//		});
//	}
//	
//	@Test
//	void studentsTestShouldFailWhenNothingAdded() {
//		new MockUp<WeatherService>() {
//			@Mock
//			public void add(Measurement measurement) {
//				// if we've made it this far that means the student did
//				// indeed add() something to the WeatherService, in which
//				// case this meta-test automatically passes
//				forceStudentsTestToFail();
//			}
//		};
//		
//		shouldFail(() -> {
//			unitTests.testWhenMaxIsMiddleElement();
//		});
//	}
//	
//	@Test
//	void studentsTestShouldFailWhenMaxIsNotInMiddle() {
//		MockUp<WeatherService> mock = new MockUp<WeatherService>() {
//			@Mock
//			public int findMaximumTemperature(Invocation invocation) {
//				// determine if the list is such that the max is at
//				// either the beginning or end, in which case we proceed()
//				// and hope the student's test fails. If it does not, something
//				// is wrong with the assertions or setup.
//				WeatherService ws = invocation.getInvokedInstance();
//				List<Measurement> data = ws.getMeasurements();
//				int lastIndex = data.size() - 1;
//				int max = invocation.proceed();
//				when(	
//						max == data.get(0).getTemperatureInCelsius() ||
//						max == data.get(lastIndex).getTemperatureInCelsius()
//					);
//				return max;
//			}
//		};
//		
//		shouldFail(() -> unitTests.testWhenMaxIsMiddleElement(),
//				"Your test passes when the max is either the first or last element. " +
//				"This could be for several reasons: (1) you added measurements in the wrong order for this test. " +
//				"(2) you had the max in the middle, but duplicated it at the first or last element. " +
//				"(3) you have an incorrect value in your assertion.");
//	}

}
