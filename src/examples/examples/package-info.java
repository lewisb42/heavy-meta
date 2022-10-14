/**
 * <h1>Overview</h1>
 * Each sub-package of the examples package is a different example project.
 * 
 * Each example is organized as follows:
 * <ul>
 * <li>examples.projectname.codeundertest : the model classes tested by the students' unit tests
 * <li>examples.projectname.unittests : (correct) implementations of the unit tests students would write
 * <li>examples.projectname.metatests : meta-tests for checking the students' unit tests
 * </ul>
 * 
 * <h1>The unittests sub-package</h1>
 * 
 * The unittests sub-package has the following format:
 * 
 * <pre>
 * examples.projectname.unittests.classname
 * </pre>
 * 
 * and contains unit test classes of the format TestNameOfMethodUnderTest (TestConstructor for
 * constructor tests). For example, the unit tests for the health project's class HeartRate's 
 * getHeartRateZone() method are in the (fully qualified) test class 
 * examples.health.unittests.heartrate.TestGetHeartRateZone.
 * 
 *  <h1>The metatests sub-package</h1>
 *  
 *  The metatests sub-package has the following format:
 *  
 *  <pre>
 *  examples.projectname.metatests.classname.methodname
 *  </pre>
 *  
 *  and contains the meta-test classes of the format MetaTestNameOfUnitTestMethod. For example,
 *  the meta-tests for the shouldGetZoneAtOneAboveAerobicBoundary() unit test method of
 *  examples.health.unittests.heartrate.TestGetHeartRateZone would be:
 *  
 *  <pre>
 *  examples.health.metatests.heartrate.getheartratezone.MetaTestShouldGetZoneAtOneAboveAerobicBoundary
 *  </pre>
 *  
 */
package examples;