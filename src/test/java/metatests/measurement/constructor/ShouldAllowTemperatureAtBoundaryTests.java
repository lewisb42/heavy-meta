package metatests.measurement.constructor;


import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import weather.codeundertest.Measurement;
import weather.unittests.measurement.TestConstructor;

/**
 * @exclude
 * @author lewisb
 *
 */
class ShouldAllowTemperatureAtBoundaryTests {

	@RegisterExtension
	private static HeavyMeta metaTester = new HeavyMeta(TestConstructor.class, "testShouldAllowTemperatureAtBoundary");

	@Test
	public void shouldHaveArrangeActStage() {
		
		var fakeMeasurement = new MockUp<Measurement>() {
		
			public int tempInF = Integer.MAX_VALUE;
			public boolean didArrangeAct = false;
			@Mock
			public void $init(Invocation inv, String loc, int tempF) {
				this.tempInF = tempF;
				didArrangeAct = true;
				inv.proceed(loc, tempF);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeMeasurement.didArrangeAct,
				"Did not instantiate a Measurement object in your Arrange/Act stage");
		
		assertEquals(-273, fakeMeasurement.tempInF,
				"Measurement constructor should use the boundary value of -273 for the temperature");
	}

}
