package health.metatests.patient.constructor;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.doubleoops.heavymeta.SafeAssertions.*;

import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import health.codeundertest.Patient;
import health.unittests.patient.TestConstructor;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestFirstNameShouldNotBeNull {
	
	static String BOGUS_NAME = "678yuhjnkif9u87guyhijlkj,khuy7ty";
	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestConstructor.class, "testFirstNameShouldNotBeNull");

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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void shouldHaveAssertThrows() {
		
		var fakedAssertions = new MockUp<Assertions>() {
			static boolean wasCalled = false;
			static Class<? extends Object> exceptionType = Object.class;
			
			@Mock
			public static void assertThrows(Class<? extends Object> expectedType, Executable e) {
				wasCalled = true;
				exceptionType = expectedType;
			}
			
			@Mock
			public static void assertThrows(Class<? extends Object> expectedType, Executable e, String msg) {
				assertThrows(expectedType, e);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.wasCalled,
				"You need an assertThrows as your assertion.");
		safeAssertEquals(IllegalArgumentException.class, fakedAssertions.exceptionType,
				"The first parameter of assertThrows should be the type of the expected exception; here, this is IllegalArgumentException.class.");
	}
	
	@Test
	public void assertThrowsShouldExecuteTheExceptionThrowingCode() {
		
		var expectations = new Expectations() {
			Executable exe = null;
			boolean patientWasInstantiated = false;
			
			@Override
			protected void establishExpectations() {
				try {
					exe.execute();
				} catch (Throwable e1) {
					throw new AssertionFailedError("The code inside the () -> { } could not be executed.");
				}
				
				assertTrue(patientWasInstantiated,
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();		
		
	}

}
