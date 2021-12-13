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

As previously mentioned, the student test's code-under-test is the "input" to the meta-test. What this means is that in order to write a suite of meta-tests to check a single student test, we have to be able to pass different versions of that code-under-test to the student's test. Naively we could simply write multiple versions and execute the meta-tests using each one. A better way, however, is to start with correct code-under-test and modify it at runtime. (*If this is starting to sound like mutation testing, it's no accident -- while mutation testing is technically something different, our approach is very much inspired by it.*) This is where JMockit comes in, specifically its [*fake object*](https://jmockit.github.io/tutorial/Faking.html) functionality. In JMockit, a fake object is one that can intercept constructor and method calls to the "real" object and change the behavior of those calls. We will use fakes to mutate the class-under-test that our student's unit test is testing. By carefully and intelligently crafting a fake, we create code that is expected to pass or fail, as appropriate, the student-written test. Then, if the meta-test fails we can provide guidance to the student about what they got wrong.

## examples
