package health.metatests.heartrate.getheartratezone;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import health.codeundertest.HeartRate;
import health.unittests.heartrate.TestGetHeartRateZone;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestShouldGetZoneOneAboveAerobicBoundary extends MetaTestBase {

	private static final Class<TestGetHeartRateZone> DEFAULT_TEST_CLASS = TestGetHeartRateZone.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testShouldGetZoneOneAboveAerobicBoundary";
	
	public MetaTestShouldGetZoneOneAboveAerobicBoundary() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}
	
	
	@Test
	public void shouldHaveArrangeStage() {
		
		final int targetBpm = 141;
		
		var expectations = new Expectations() {
			boolean didCreate = false;
			int actualBpm = Integer.MAX_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(didCreate,
						"Did not instantiate a HeartRate object in your Arrange stage.");
				expect(targetBpm == actualBpm,
						"Should instantiate the HeartRate object with a bpm one above the boundary of 140.");
			}
			
		};
		
		new MockUp<HeartRate>() {	
			
			@Mock
			public void $init(int bpm) {
				expectations.didCreate = true;
				expectations.actualBpm = bpm;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
		
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var expectations = new Expectations() {
			boolean didAct = false;
			
			@Override
			protected void establishExpectations() {
				expect(didAct,
						"Did not call getHeartRateZone() on your HeartRate object in the Act stage.");
			}
			
		};
		
		new MockUp<HeartRate>() {
			
			@Mock
			public String getHeartRateZone(Invocation inv) {
				expectations.didAct = true;
				return inv.proceed();
			}
		};

		runStudentsTestIgnoreFails();
		expectations.assertPassed();
		
	}
	
	@Test
	public void shouldHaveAssertStage() {
		
		final String properExpectedValue = "Aerobic";
		
		var expectations = new Expectations() {
			boolean didAssert = false;
			String expectedValueFromAssertion = "";
			
			@Override
			protected void establishExpectations() {
				expect(didAssert,
						"You do not have an assertEquals(String expected, String actual) in your Assert stage.");
				expect(expectedValueFromAssertion.equals(properExpectedValue),
						"The expected value (first parameter) of assertEquals should be the string for the aerobic zone");
			}
			
		};
		
		new MockUp<Assertions>() {
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				captureParameters(expected, actual);
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual) {
				captureParameters(expected, actual);
			}

			private void captureParameters(Object expected, Object actual) {
				expectations.didAssert = true;
				expectations.expectedValueFromAssertion = (String)expected;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
		
		
	}

	@Test
	public void actualValueShouldComeFromActStageReturnValue() {
		
		final String bogusReturnValue = "97y8gyihuoj878ij3ks;3o4";
		
		var expectations = new Expectations() {
			String actualValueFromAssertion = "";
			
			@Override
			protected void establishExpectations() {
				expect(actualValueFromAssertion.equals(bogusReturnValue),
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
				expectations.actualValueFromAssertion =  (String)actual;
			}

			@Mock
			public void assertEquals(Object expected, Object actual) {
				checkActualValue(actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
