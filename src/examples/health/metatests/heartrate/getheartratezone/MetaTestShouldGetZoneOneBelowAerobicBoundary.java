package health.metatests.heartrate.getheartratezone;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import static org.doubleoops.heavymeta.SafeAssertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

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
		
		var expectations = new Expectations() {
			boolean didCreate = false;
			int actualBpm = Integer.MAX_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(didCreate, "Did not instantiate a HeartRate object in your Arrange stage.");
				expect(actualBpm == targetBpm, "Should instantiate the HeartRate object with a bpm one below the boundary of 140.");
			}
		};
		
		
		
		new MockUp<HeartRate>() {
			
			@Mock
			public void $init(int bpm) {
				expectations.didCreate = true;
				expectations.actualBpm = bpm;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveActStage() {
		var expectations = new Expectations( ) {
			boolean didAct = false;
			
			@Override
			protected void establishExpectations() {
				this.expect(didAct, "Did not call getHeartRateZone() on your HeartRate object in the Act stage.");
			}
			
		};
		
		new MockUp<HeartRate>() {
			
			@Mock
			public String getHeartRateZone(Invocation inv) {
				expectations.didAct = true;
				return inv.proceed();
			}
		};

		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveAssertStage() {
		
		var expectations = new Expectations() {
			boolean didAssert = false;
			boolean hasProperExpectedValue = false;
			
			@Override
			protected void establishExpectations() {
				expect(didAssert, "You do not have an assertEquals(String expected, String actual) in your Assert stage.");
				expect(hasProperExpectedValue, "The expected value (first parameter) of assertEquals should be the string for the temperate zone");
			}
			
		};
		
		new MockUp<Assertions>() {
			
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
					expectations.didAssert = true;
					
					if (properExpectedValue.equals(expected)) {
						expectations.hasProperExpectedValue = true;
					}
				}
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
		
	}
	
	@Test
	public void actMethodShouldBeCalledOnArrangedObject() {
		
		var expectations = new Expectations() {
			HeartRate arrangedHeartRate = null;
			HeartRate actedUponHeartRate = null;
			
			@Override
			protected void establishExpectations() {
				expect(arrangedHeartRate == actedUponHeartRate, "The HeartRate you created in your Arrange stage is not the one you called getHeartRateZone() upon");
				
			}
			
		};
		
		new MockUp<HeartRate>() {
			
			@Mock
			public void $init(Invocation inv, int heartRate) {
				expectations.arrangedHeartRate = inv.getInvokedInstance();
			}
			
			@Mock
			public String getHeartRateZone(Invocation inv) {
				String realResult = inv.proceed();
				expectations.actedUponHeartRate = inv.getInvokedInstance();
				return realResult;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void actualValueShouldComeFromActStageReturnValue() {
		final String bogusReturnValue = "ghu23bnwmg0pd98ypq;3o4";
		
		var expectations = new Expectations() {
			boolean hasProperActualValue = false;
			
			@Override
			protected void establishExpectations() {
				expect(hasProperActualValue,
						"The actual value of your assertEquals does not come from the return of getHeartRateZone. Capture the latter in a local variable and use that variable as the actual value of the assertion (second parameter).");
			}
			
		};
		
		new MockUp<HeartRate>() {
		
			@Mock
			public String getHeartRateZone() {
				return bogusReturnValue;
			}
		};
		
		new MockUp<Assertions>() {
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				checkActualValue(actual);
			}
			
			private void checkActualValue(Object actual) {
				if (actual instanceof String) {
					String actualString = (String) actual;
					if (bogusReturnValue.equals(actualString)) {
						expectations.hasProperActualValue = true;
					}
				}
			}

			@Mock
			public void assertEquals(Object expected, Object actual) {
				checkActualValue(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
