package metatests.measurement.constructor;

import static org.doubleoops.heavymeta.HeavyMeta.assertIsTestMethod;
import static org.doubleoops.heavymeta.HeavyMeta.shouldPass;
import static org.junit.jupiter.api.Assertions.*;

import org.doubleoops.heavymeta.MetaTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import model.Measurement;
import studenttests.measurement.TestConstructor;

class ShouldAllowTemperatureAtBoundaryTests {

	@RegisterExtension
	private static MetaTestExtension metaTester = new MetaTestExtension(TestConstructor.class, "testShouldAllowTemperatureAtBoundary");

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
