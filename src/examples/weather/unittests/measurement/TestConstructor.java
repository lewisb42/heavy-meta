package weather.unittests.measurement;



import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import weather.codeundertest.Measurement;

/**
 * This is a test class that a student might write.
 *  
 * @author lewisb
 *
 */
public class TestConstructor {
	
	private Measurement measurement;
	

	@Test
	public void testShouldNotAllowNullLocation() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Measurement(null, 100);
		});
	}
	
	@Test
	public void testShouldNotAllowEmptyLocation() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Measurement(new String(""), 100);
		});
	}
	
	@Test
	public void testShouldNotAllowTemperatureOneBelowBoundary() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Measurement(new String("Carrollton"), -274);
		});
	}

	@Test
	public void testShouldAllowTemperatureAtBoundary() {
		this.measurement = new Measurement(new String("Carrollton"), -273);
		assertEquals(-273, this.measurement.getTemperatureInCelsius());
	}
	
	@Test
	public void testShouldAllowTemperatureOneAboveBoundary() {
		Measurement m = new Measurement(new String("Carrollton"), -272);
		assertEquals(-272, m.getTemperatureInCelsius());
	}
	
	@Test
	public void testShouldCreateValidMeasurement() {
		Measurement measurement = new Measurement(new String("Carrollton"), 100);
		assertEquals(new String("Carrollton"), measurement.getLocation());
		// Bad: assertEquals(new String("Carrollton"), "Carrollton");
		assertEquals(100, measurement.getTemperatureInCelsius());
	}
}
