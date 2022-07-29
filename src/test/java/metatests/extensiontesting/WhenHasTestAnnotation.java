package metatests.extensiontesting;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.MetaTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import studenttests.measurement.TestConstructor;

class WhenHasTestAnnotation {

	@RegisterExtension
	private static MetaTestExtension extension = new MetaTestExtension(TestConstructor.class, "testShouldCreateValidMeasurement");
	
	@Test
	void test() {
		assertTrue(true);
	}

}
