package health.metatests.heartrate.getheartratezone;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.HeavyMeta;
import static org.doubleoops.heavymeta.SafeAssertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import health.codeundertest.HeartRate;
import health.unittests.heartrate.TestGetHeartRateZone;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestShouldGetZoneOneBelowAerobicBoundary {

	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestGetHeartRateZone.class, "testShouldGetZoneOneBelowAerobicBoundary");
	
	@Test
	public void shouldHaveArrangeStage() {
		
		final int targetBpm = 139;
		
		var fakeHeartRate = new MockUp<HeartRate>() {
			boolean didCreate = false;
			int actualBpm = Integer.MAX_VALUE;
			
			@Mock
			public void $init(int bpm) {
				didCreate = true;
				actualBpm = bpm;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeHeartRate.didCreate,
				"Did not instantiate a HeartRate object in your Arrange stage.");
		assertEquals(targetBpm, fakeHeartRate.actualBpm,
				"Should instantiate the HeartRate object with a bpm one below the boundary of 140.");
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var fakeHeartRate = new MockUp<HeartRate>() {
			boolean didAct = false;
			
			@Mock
			public String getHeartRateZone(Invocation inv) {
				didAct = true;
				return inv.proceed();
			}
		};

		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeHeartRate.didAct,
				"Did not call getHeartRateZone() on your HeartRate object in the Act stage.");
	}
	
	@Test
	public void shouldHaveAssertStage() {
		
		var fakedAssertions = new MockUp<Assertions>() {
			static boolean didAssert = false;
			static boolean hasProperExpectedValue = false;
			
			final String properExpectedValue = "Temperate";
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				checkParameters(expected, actual);
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual) {
				checkParameters(expected, actual);
			}

			private void checkParameters(Object expected, Object actual) {
				if (expected instanceof String && actual instanceof String) {
					didAssert = true;
					
					if (properExpectedValue.equals(expected)) {
						hasProperExpectedValue = true;
					}
				}
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.didAssert,
				"You do not have an assertEquals(String expected, String actual) in your Assert stage.");
		safeAssertTrue(fakedAssertions.hasProperExpectedValue,
				"The expected value (first parameter) of assertEquals should be the string for the temperate zone");
		
	}
	
	@Test
	public void actMethodShouldBeCalledOnArrangedObject() {
		
		var fakeHeartRate = new MockUp<HeartRate>() {
			HeartRate arrangedHeartRate = null;
			HeartRate actedUponHeartRate = null;
			
			@Mock
			public void $init(Invocation inv, int heartRate) {
				arrangedHeartRate = inv.getInvokedInstance();
			}
			
			@Mock
			public String getHeartRateZone(Invocation inv) {
				String realResult = inv.proceed();
				actedUponHeartRate = inv.getInvokedInstance();
				return realResult;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertSame(fakeHeartRate.arrangedHeartRate, fakeHeartRate.actedUponHeartRate,
				"The HeartRate you created in your Arrange stage is not the one you called getHeartRateZone() upon");
	}

	@Test
	public void actualValueShouldComeFromActStageReturnValue() {
		final String bogusReturnValue = "ghu23bnwmg0pd98ypq;3o4";
		
		var fakeHeartRate = new MockUp<HeartRate>() {
		
			@Mock
			public String getHeartRateZone() {
				return bogusReturnValue;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			boolean hasProperActualValue = false;
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				checkActualValue(actual);
			}
			
			private void checkActualValue(Object actual) {
				if (actual instanceof String) {
					String actualString = (String) actual;
					if (bogusReturnValue.equals(actualString)) {
						hasProperActualValue = true;
					}
				}
			}

			@Mock
			public void assertEquals(Object expected, Object actual) {
				checkActualValue(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakedAssertions.hasProperActualValue,
				"The actual value of your assertEquals does not come from the return of getHeartRateZone. Capture the latter in a local variable and use that variable as the actual value of the assertion (second parameter).");
	}
}
