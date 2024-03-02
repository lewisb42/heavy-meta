package health.metatests.patient.constructor;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.MetaTestBase;
import org.doubleoops.heavymeta.MockedUpAssertEqualsForObjects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import health.codeundertest.Patient;
import health.unittests.patient.TestConstructor;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;


public class MetaTestShouldCreateValidPatient extends MetaTestBase {
	private static final Class<TestConstructor> DEFAULT_TEST_CLASS = TestConstructor.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testShouldCreateValidPatient";
	
	public MetaTestShouldCreateValidPatient() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}
	
	@Test
	public void shouldHaveArrangeActStage() {
		var expectations = new Expectations() {

			boolean didCreatePatient  = false;
			String patientFirstName = null;
			String patientLastName = null;
			int patientAge = Integer.MIN_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(didCreatePatient,
						"Did not instantiate a Patient object in your Arrange stage");
				
				expect(patientAge > 0,
						"Patient constructor's age parameter should be > 0, based on the @precondition");
				
				expect(patientFirstName != null,
						"Patient constructor's firstName parameter should not be null, based on the @precondition");
				
				expect(!patientFirstName.isEmpty(),
						"Patient constructor's firstName parameter should not be empty, based on the @precondition");
				
				expect(patientLastName != null,
						"Patient constructor's lastName parameter should not be null, based on the @precondition");
				
				expect(!patientLastName.isEmpty(),
						"Patient constructor's lastName parameter should not be empty, based on the @precondition");
				
			}			
		};
		
		new MockUp<Patient>() {
			
			
			@Mock
			public void $init(Invocation inv, String firstName, String lastName, int age) {
				expectations.didCreatePatient = true;
				
				// capture these to test for validity later
				expectations.patientFirstName = firstName;
				expectations.patientLastName = lastName;
				expectations.patientAge = age;
				inv.proceed(firstName, lastName, age);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveAssertForFirstName() {
		final String bogusString = "ImaBogusString!@#$";
		
		var expectations = new Expectations() {
			String patientFirstName = "";
			boolean hasFirstNameAssertion = false;
			String expectedFirstName = "";
			
			@Override
			protected void establishExpectations() {
				expect(hasFirstNameAssertion,
						"It seems you do not have an assertEquals whose expected value is the same as your constructor's firstName, and whose actual value is returned by getFirstName()");
				
				expect(patientFirstName.equals(expectedFirstName),
						"The expected value of your assertEquals for the first name does not match the firstName you gave to the constructor");
			}
		};
		
		new MockUp<Patient>() {
			@Mock
			public String getFirstName(Invocation inv) {
				// capture for use later
				expectations.patientFirstName = inv.proceed();
				
				// return bogus first name so we can identify this result later
				return bogusString;
			}
		};
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (actual.equals(bogusString)) {
					expectations.hasFirstNameAssertion = true;
					expectations.expectedFirstName = (String)expected;
				}
			}
			
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();	
	}

	@Test
	public void shouldHaveAssertForLastName() {
		final String bogusString = "ImaBogusString!@#$";
		
		var expectations = new Expectations() {
			String patientLastName = "";
			boolean hasLastNameAssertion = false;
			String expectedLastName = "";
			
			@Override
			protected void establishExpectations() {
				expect(hasLastNameAssertion,
						"It seems you do not have an assertEquals whose expected value is the same as your constructor's lastName, and whose actual value is returned by getLastName()");
				
				expect(patientLastName.equals(expectedLastName),
						"The expected value of your assertEquals for the last name does not match the lastName you gave to the constructor");
			}
		};
		
		new MockUp<Patient>() {
			@Mock
			public String getLastName(Invocation inv) {
				// capture for use later
				expectations.patientLastName = inv.proceed();
				
				// return bogus last name so we can identify this result later
				return bogusString;
			}
		};
		
		new MockedUpAssertEqualsForObjects() {			
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (actual.equals(bogusString)) {
					expectations.hasLastNameAssertion = true;
					expectations.expectedLastName = (String)expected;
				}
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void shouldHaveAssertForAge() {
		final int bogusAge = -429818;
		
		var expectations = new Expectations() {
			int patientAge = Integer.MIN_VALUE;
			boolean hasAgeAssertion = false;
			int expectedAge = Integer.MIN_VALUE;
			
			@Override
			protected void establishExpectations() {
				expect(hasAgeAssertion,
						"It seems you do not have an assertEquals whose expected value is the same as your constructor's age, and whose actual value is returned by getAge()");
				
				expect(patientAge == expectedAge,
						"The expected value of your assertEquals for the age does not match the age you gave to the constructor");
			}
		};
		
		new MockUp<Patient>() {
			@Mock
			public int getAge(Invocation inv) {
				// capture for use later
				expectations.patientAge = inv.proceed();
				
				// return bogus age so we can identify this result later
				return bogusAge;
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(int expected, int actual) {
				if (actual == bogusAge) {
					expectations.hasAgeAssertion = true;
					expectations.expectedAge = expected;
				}
			}
			
			@Mock
			public void assertEquals(int expected, int actual, String msg) {
				assertEquals(expected, actual);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

}
