package examples.health.metatests.patient.constructor;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.HeavyMeta;
import static org.doubleoops.heavymeta.SafeAssertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import examples.health.modelclasses.Patient;
import examples.health.studentunittests.patient.TestConstructor;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;


public class MetaTestShouldCreateValidPatient {
	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestConstructor.class, "testShouldCreateValidPatient");
	
	@Test
	public void shouldHaveArrangeActStage() {
		var fakePatient = new MockUp<Patient>() {
			public boolean didCreate  = false;
			public String firstName = null;
			public String lastName = null;
			public int age = Integer.MIN_VALUE;
			
			@Mock
			public void $init(Invocation inv, String firstName, String lastName, int age) {
				didCreate = true;
				
				// capture these to test for validity later
				this.firstName = firstName;
				this.lastName = lastName;
				this.age = age;
				inv.proceed(firstName, lastName, age);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakePatient.didCreate,
				"Did not instantiate a Patient object in your Arrange stage");
		
		assertTrue(fakePatient.age > 0,
				"Patient constructor's age parameter should be > 0, based on the @precondition");
		
		assertNotNull(fakePatient.firstName,
				"Patient constructor's firstName parameter should not be null, based on the @precondition");
		
		assertNotEquals(fakePatient.firstName, "",
				"Patient constructor's firstName parameter should not be empty, based on the @precondition");
		
		assertNotNull(fakePatient.lastName,
				"Patient constructor's lastName parameter should not be null, based on the @precondition");
		
		assertNotEquals(fakePatient.lastName, "",
				"Patient constructor's lastName parameter should not be empty, based on the @precondition");
	}
	
	@Test
	public void shouldHaveAssertForFirstName() {
		final String bogusString = "ImaBogusString!@#$";
		
		var fakePatient = new MockUp<Patient>() {
			
			public String firstName = "";
			@Mock
			public String getFirstName(Invocation inv) {
				// capture for use later
				firstName = inv.proceed();
				
				// return bogus first name so we can identify this result later
				return bogusString;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			public static boolean hasFirstNameAssertion = false;
			public static String expectedFirstName = "";
			public static boolean assertEqualsUsesGetFirstName = false;
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				if (actual.equals(bogusString)) {
					hasFirstNameAssertion = true;
					expectedFirstName = (String)expected;
					assertEqualsUsesGetFirstName = true;
				}
			}
			
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.hasFirstNameAssertion,
				"It seems you do not have an assertEquals whose expected value is the same as your constructor's firstName, and whose actual value is returned by getFirstName()");
		
		safeAssertEquals(fakePatient.firstName, fakedAssertions.expectedFirstName,
				"The expected value of your assertEquals for the first name does not match the firstName you gave to the constructor");
		
	}

	@Test
	public void shouldHaveAssertForLastName() {
		final String bogusString = "ImaBogusString!@#$";
		
		var fakePatient = new MockUp<Patient>() {
			
			public String lastName = "";
			@Mock
			public String getLastName(Invocation inv) {
				// capture for use later
				lastName = inv.proceed();
				
				// return bogus last name so we can identify this result later
				return bogusString;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			public static boolean hasLastNameAssertion = false;
			public static String expectedLastName = "";
			public static boolean assertEqualsUsesGetLastName = false;
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				if (actual.equals(bogusString)) {
					hasLastNameAssertion = true;
					expectedLastName = (String)expected;
					assertEqualsUsesGetLastName = true;
				}
			}
			
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.hasLastNameAssertion,
				"It seems you do not have an assertEquals whose expected value is the same as your constructor's lastName, and whose actual value is returned by getLastName()");
		
		safeAssertEquals(fakePatient.lastName, fakedAssertions.expectedLastName,
				"The expected value of your assertEquals for the last name does not match the lastName you gave to the constructor");
		
	}

	@Test
	public void shouldHaveAssertForAge() {
		final int bogusAge = -429818;
		
		var fakePatient = new MockUp<Patient>() {
			
			public int age = Integer.MIN_VALUE;
			@Mock
			public int getAge(Invocation inv) {
				// capture for use later
				age = inv.proceed();
				
				// return bogus age so we can identify this result later
				return bogusAge;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			public static boolean hasAgeAssertion = false;
			public static int expectedAge = Integer.MIN_VALUE;
			public static boolean assertEqualsUsesGetAge = false;
			
			@Mock
			public static void assertEquals(int expected, int actual) {
				if (actual == bogusAge) {
					hasAgeAssertion = true;
					expectedAge = expected;
					assertEqualsUsesGetAge = true;
				}
			}
			
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.hasAgeAssertion,
				"It seems you do not have an assertEquals whose expected value is the same as your constructor's age, and whose actual value is returned by getAge()");
		
		safeAssertEquals(fakePatient.age, fakedAssertions.expectedAge,
				"The expected value of your assertEquals for the age does not match the age you gave to the constructor");
		
	}

}
