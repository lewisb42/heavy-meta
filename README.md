# heavy-meta
A library and approach for writing "meta" unit tests that can examine details of unit tests written by students. 
Designed for use in systems that auto-grade student programming practice exercises.

# Creating the jar file

Right now this is still an Eclipse project (sorry no Maven yet).

1. Open the project in Eclipse.
2. Export the org.doubleoops.heavymeta package to a heavymeta.jar file.
3. Include heavymeta.jar file on the classpath of your own project.

# Create an Eclipse project using Heavy-Meta

## Maven dependencies

Add the following as *compile* (not test) dependencies to your pom.xml:

- [jmockit](https://mvnrepository.com/artifact/org.jmockit/jmockit)
- [junit5](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter)

Setup the project:

- Make sure the heavymeta.jar is added to the project's build path.



# Running
See the [jmockit](https://jmockit.github.io/tutorial/Introduction.html#runningTests) documentation for adding 
the proper -javaagent option to your java command line. (This is not necessary when using the provided launch configurations.)

# Examples

The src/examples directory has several projects including code-under-test, example student-submitted unit tests for the code-under-test, 
and meta-tests for the student-submitted code. Additionally there are Eclipse .launch files pre-configured (with the right java command-line
arguments, etc) to run the meta-tests. In the build path, change the variable M2_REPO so that it points to your maven download directory. 
(Otherwise the provided launch configurations will not work.)
