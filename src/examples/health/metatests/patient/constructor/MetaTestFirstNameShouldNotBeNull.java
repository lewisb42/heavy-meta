package health.metatests.patient.constructor;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import health.codeundertest.Patient;
import health.unittests.heartrate.TestGetHeartRateZone;
import health.unittests.patient.TestConstructor;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestFirstNameShouldNotBeNull extends MetaTestBase {
	
	static String BOGUS_NAME = "678yuhjnkif9u87guyhijlkj,khuy7ty";
	
	private static final Class<TestConstructor> DEFAULT_TEST_CLASS = TestConstructor.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testFirstNameShouldNotBeNull";
	
	public MetaTestFirstNameShouldNotBeNull() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}
	
	
	@Test
	public void shouldHaveArrangeActStage() {
		
		
		
		var fakePatient = new MockUp<Patient>() {
			
			public String firstName = BOGUS_NAME;
			public String lastName = null;
			public int age = -1;
			public boolean wasInstantiated = false;
			
			@Mock
			public void $init(String firstName, String lastName, int age) {
				this.firstName = firstName;
				this.lastName = lastName;
				this.age = age;
				this.wasInstantiated = true;
			}
		};
		
		var expectations = new Expectations() {
			
			@Override
			protected void establishExpectations() {
				expect(fakePatient.wasInstantiated,
						"You must instantiate a Patient object inside the assertThrows");
				expect(fakePatient.firstName == null,
						"The Patient constructor's firstName parameter should be null.");
				expect(fakePatient.lastName != null,
						"The Patient constructor's lastName parameter should be valid (i.e., not null or empty");
				expect(!fakePatient.lastName.isEmpty(), 
						"The Patient constructor's lastName parameter should be valid (i.e., not null or empty");
				expect(fakePatient.age > 0,
						"The Patient constructor's age should be valid, i.e., greater than zero.");
				
			}
			
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void shouldHaveAssertThrows() {
		
		var expectations = new Expectations() {
			boolean assertThrowsWasCalled = false;
			Class<? extends Object> exceptionType = Object.class;
			
			@Override
			protected void establishExpectations() {
				expect(assertThrowsWasCalled,
						"You need an assertThrows as your assertion.");
				expect(IllegalArgumentException.class.equals(exceptionType),
						"The first parameter of assertThrows should be the type of the expected exception; here, this is IllegalArgumentException.class.");
			}
			
		};
		
		new MockUp<Assertions>() {
			
			// NOTE: must make assertThrows non-static here. Otherwise the mocking confuses it.
			@Mock
			public void assertThrows(Class<? extends Object> expectedType, Executable e) {
				expectations.assertThrowsWasCalled = true;
				expectations.exceptionType = expectedType;
			}
			
			@Mock
			public void assertThrows(Class<? extends Object> expectedType, Executable e, String msg) {
				assertThrows(expectedType, e);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertThrowsShouldExecuteTheExceptionThrowingCode() {
		
		var expectations = new Expectations() {
			Executable exe = null;
			boolean patientWasInstantiated = false;
			
			@Override
			protected void establishExpectations() {
				// running the test captures the lambda (i.e., Executable object);
				// we execute it here to see if it called the Patient constructor
				try {
					exe.execute();
				} catch (Throwable e1) {
					throw new AssertionFailedError("The code inside the () -> { } could not be executed.");
				}
				
				expect(patientWasInstantiated,
						"Did not instantiate your Patient inside the assertThrow's () -> { } block.");
			}
			
		};
		
		new MockUp<Assertions>() {
			static Executable exe = null;
			
			@Mock
			public void assertThrows(Class<? extends Object> expectedType, Executable e) {
				expectations.exe = e;
			}
			
			@Mock
			public void assertThrows(Class<? extends Object> expectedType, Executable e, String msg) {
				expectations.exe = e;
			}
		};
		
		new MockUp<Patient>() {
			
			@Mock
			public void $init(String firstName, String lastName, int age) {
				expectations.patientWasInstantiated = true;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();		
		
	}

}
