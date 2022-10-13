package examples.health.metatests.patient.constructor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.doubleoops.heavymeta.HeavyMeta;
import static org.doubleoops.heavymeta.SafeAssertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import examples.health.modelclasses.Patient;
import examples.health.studentunittests.patient.TestConstructor;
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
		
		metaTester.runStudentsTestIgnoreFails();
		
		
		assertTrue(fakePatient.wasInstantiated,
				"You must instantiate a Patient object inside the assertThrows");
		assertNull(fakePatient.firstName,
				"The Patient constructor's firstName parameter should be null.");
		assertNotNull(fakePatient.lastName,
				"The Patient constructor's lastName parameter should be valid (i.e., not null or empty");
		assertFalse(fakePatient.lastName.isEmpty(), 
				"The Patient constructor's lastName parameter should be valid (i.e., not null or empty");
		assertTrue(fakePatient.age > 0,
				"The Patient constructor's age should be valid, i.e., greater than zero.");
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
		
		var fakedAssertions = new MockUp<Assertions>() {
			static Executable exe = null;
			
			@Mock
			public static void assertThrows(Class<? extends Object> expectedType, Executable e) {
				exe = e;
			}
			
			@Mock
			public static void assertThrows(Class<? extends Object> expectedType, Executable e, String msg) {
				exe = e;
			}
		};
		
		var fakePatient = new MockUp<Patient>() {
			boolean wasInstantiated = false;
			
			@Mock
			public void $init(String firstName, String lastName, int age) {
				wasInstantiated = true;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		try {
			fakedAssertions.exe.execute();
		} catch (Throwable e1) {
			throw new AssertionFailedError("The code inside the () -> { } could not be executed.");
		}
		
		assertTrue(fakePatient.wasInstantiated,
				"Did not instantiate your Patient inside the assertThrow's () -> { } block.");
	}

}
