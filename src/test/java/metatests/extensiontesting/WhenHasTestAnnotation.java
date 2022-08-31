package metatests.extensiontesting;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import examples.studentunittests.measurement.TestConstructor;

class WhenHasTestAnnotation {

	@RegisterExtension
	private static HeavyMeta extension = new HeavyMeta(TestConstructor.class, "testShouldCreateValidMeasurement");
	
	@Test
	void test() {
		assertTrue(true);
	}

}
