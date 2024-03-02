package metatests.extensiontesting;

import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.StandardMetaTestChecks;
import org.doubleoops.heavymeta.MetaTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import weather.unittests.measurement.TestConstructor;

/**
 * @exclude
 * @author lewisb
 *
 */
class WhenHasTestAnnotation extends MetaTestBase {

	public WhenHasTestAnnotation() {
		super(TestConstructor.class, "testShouldCreateValidMeasurement");
	}

	@Test
	void test() {
		assertTrue(true);
	}

}
