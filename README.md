# heavy-meta
A library and approach for writing "meta" unit tests that test conventional JUnit unit tests. Designed for use in systems that auto-grade student programming practice exercises.

# Setup

Add the [HeavyMeta.java](https://github.com/lewisb42/heavy-meta/blob/main/src/main/java/org/doubleoops/heavymeta/HeavyMeta.java) file to your project (sorry no Maven repository yet -- at least its).

Note that HeavyMeta.java is just a class of static convenience methods. The meta-testing process here can be done without them; they just make things more readable.

## Maven dependencies

Add the following as *compile* (not test) dependencies to your pom.xml:

- [jmockit](https://mvnrepository.com/artifact/org.jmockit/jmockit)
- [junit5](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter)

# Running
See the [jmockit](https://jmockit.github.io/tutorial/Introduction.html#runningTests) documentation for adding the proper -javaagent option to your java command line.

# Writing Meta-tests

You are strongly encouraged to read up on [JMockit's fake objects](https://jmockit.github.io/tutorial/Faking.html)
before proceeding!

## Scope and limitations

What this is not: a general framework for evaluating the quality of student-written unit tests. This is a Hard Problem. I know, because I spent several years banging my head against it, to no avail. So if you came here looking for that, sorry not sorry.

Instead, this is intended specifically for automated grading systems (e.g., zyBooks) that present students with small, focused, heavily-scaffolded programming exercises, the kind of exercises that give them ample opportunities for practice and are likely low-value in terms of the impact on the student's grade. It is not intended for high-point-value assignments that are major assessments for the course.

In our case, the scaffolding includes:

- A Java class with one-or-more methods whose role is the code-under-test for the student test. This class will typically be the *correct* code for passing the test the student writes.
- A test class with a test method stub for the unit test the student will write. It is important that the scaffolded code include the class name and the name of the student test method, since our meta-tests will depend on those.

In these exercises it is expected that the student's task is to complete the unit test body, then submit their code to the autograder.

## Conceptual Overview

### Student tests vs. Meta-tests

First, some terminology. We distinguish between vanilla unit tests and the unit tests that test those vanilla unit tests. Specifically we refer to them as:
- **student tests**: vanilla unit tests, written by students attempting a practice exercise
- **meta-tests**: specially-crafted unit tests, written by the maintainers (e.g., course instructors) of the practice exercises, that test the student tests

### How in the heck do you unit test a unit test???

Consider a regular (non-meta) unit test that tests a Java method: conceptually, the method-under-test has:

- input(s): parameter values and/or object state carefully constructed for this specific test case
- output(s): return values, thrown exceptions, or modified object state

Assertions in the unit test check that the actual outputs match the output expected for the given inputs. If the actual and expected match, the unit test passes, otherwise the unit test fails.

Now extend that to a unit test (the meta-test) whose method-under-test is a JUnit test method (i.e., one annotated with @Test). Once again, we have input and output. Here, the input is the @Test method's code-under-test (more on this in a minute), and the output is whether that unit-test-method-under-test passes or fails. In JUnit5, the latter is indicated by whether it throws AssertionFailedError or not, so we can simply use an assertThrows to check for failure (and lack thereof for passing).

As previously mentioned, the student test's code-under-test is the "input" to the meta-test. What this means is that in order to write a suite of meta-tests to check a single student test, we have to be able to pass different versions of that code-under-test to the student's test. In other words, we're deliberately injecting errors into the code-under-test. Naively we could simply write multiple versions and execute the meta-tests using each one. A better way, however, is to start with correct code-under-test and modify it at runtime. (*If this is starting to sound like mutation testing, it's no accident -- while mutation testing is technically something different, our approach is very much inspired by it.*) This is where JMockit comes in, specifically its [*fake object*](https://jmockit.github.io/tutorial/Faking.html) functionality. In JMockit, a fake object is one that can intercept constructor and method calls to the "real" object and change the behavior of those calls. We will use fakes to mutate the class-under-test that our student's unit test is testing. By carefully and intelligently crafting a fake, we create code that is expected to pass or fail, as appropriate, the student-written test. Then, if the meta-test fails we can provide guidance to the student about what they got wrong.

## We all need a simple and contrived example so here it is

Let's start with the (from the student's point-of-view) code-under-test, a simple data class in Java:

```
public class Measurement {
	private String location;
	private int temperatureInCelsius;
	public Measurement(String location, int temperatureInCelsius) {
		if (location == null) {
			throw new IllegalArgumentException("invalid location");
		}
		
		if (location.isEmpty()) {
			throw new IllegalArgumentException("invalid location");
		}
		
		if (temperatureInCelsius < -273) {
			throw new IllegalArgumentException("invalid temperatureInCelsius");
		}
		
		this.location = location;
		this.temperatureInCelsius = temperatureInCelsius;
	}
  
	public String getLocation() {
		return location;
	}
  
	public int getTemperatureInCelsius() {
		return temperatureInCelsius;
	}
}
```
While not a particularly interesting class from a testing perspective, its simplicity makes it a good place to start teaching students about unit test structure, even if we might not bother testing it In Real Life.

Now we have an autograded programming exercise: *Complete the unit test below by instantiating a `Measurement` object with valid parameters and writing the appropriate assertions to check the constructor properly initialized its state*

With the following scaffolded test class:

```
public class TestConstructor {
	
  public void testShouldCreateValidMeasurement() {

  }
}
```

(Notice we left out the @Test! HeavyMeta can check for its absence as a way to remind the student to include it in their tests. Also note that the test method **must** be public. This isn't required by JUnit, but is required by the HeavyMeta approach.)

An acceptable implementation of this unit test would look something like:

```
@Test
public void testShouldCreateValidMeasurement() {
  Measurement measurement = new Measurement(new String("Carrollton"), 100);
  assertEquals(new String("Carrollton"), measurement.getLocation());
  assertEquals(100, measurement.getTemperatureInCelsius());
}
```

Now, on the back end, we as the instructor have created a series of meta-tests to use to autograde the student's work. 
A reasonably complete suite of meta-tests for `testShouldCreateValidMeasurement()` might be:

1. student's test should pass if fed the non-mutated `Measurement`
1. student's test should fail if `Measurement`'s constructor's `location` parameter is `null`
1. student's test should fail if `Measurement`'s constructor's `location` parameter is the empty string
1. student's test should fail if `Measurement`'s constructor's `temperatureInCelsius` parameter is less-than -273
1. student's test should fail if `getLocation()` returns a different value than that specified in the constructor
1. student's test should fail if `getTemperatureInCelsius()` returns a different value than that specified in the constructor

Here is the test class and meta-test for case #5 above:

```
class ShouldCreateValidMeasurementTests {
	TestConstructor unitTests;

	@BeforeEach
	void setup() {
		assertIsTestMethod(TestConstructor.class, "testShouldCreateValidMeasurement");
		unitTests = new TestConstructor();
	}
	
	@Test 
	void studentsTestShouldFailWhenGetLocationReturnsWrongValue() {
		
		new MockUp<Measurement>() {
			@Mock 
			public void $init(Invocation inv, String location, int temperatureInCelsius) {
				when(temperatureInCelsius >= -273);
				whenNotNull(location);
				whenNot(location.isEmpty());
				inv.proceed(location, temperatureInCelsius);
			}
			
			@Mock 
			public String getLocation() {
				return "q2w3sedxcftg6y7hunjk";
			}
		};
		
		shouldFail(() -> {
			unitTests.testShouldCreateValidMeasurement();
		});
	}
}
```

Explanation of all the bits and pieces of the test:

- The `@BeforeEach` first checks that the student-test-under-test method is annotated with `@Test` and then creates an instance of the student's test class (here `TestConstructor`). Obviously this doesn't have to happen in an @BeforeEach, but this is code that will common to most tests.
- In the meta-test itself (`studentsTestShouldFailWhenGetLocationReturnsWrongValue()`):
	1. We create a `MockUp<Measurement>` object and pass it an initialization block. (`MockUp` is JMockit's fake object class.) This `MockUp` object will intercept calls to methods of `Measurement`.
	1. We `@Mock` `Measurement`'s `getLocation()` method and constructor, the latter via the `$init()` method.
	1. `$init()` ensures the constructor has been called with a valid value for `temperatureInCelsius` as we don't want the student's test to fail for the wrong reasons.
	1. Similarly, `$init()` ensures the `location` valid (neither `null` nor the empty string).
	1. `$init()` then calls the *real* constructor via `Invocation#proceed`, passing in the parameters.
	1. The `@Mock` for `getLocation()` returns an ugly location value that is unlikely to be what was originally sent to the constructor. 

We have now created a modified `Measurement` object, at run-time, 
that effectively has a bug in it, namely that the location passed-in to the constructor is 
not being returned by `getLocation()`. The student's test should should show failure in this situation.

The last bit of our meta-test is a call to `shouldFail()`, which takes as parameter a lambda running the student's 
unit test. This means our meta-test passes if the student's test (properly) fails, and that the meta-test fails if 
the student's test (improperly) passes. Keeping those straight can be a chore so proceed carefully!
