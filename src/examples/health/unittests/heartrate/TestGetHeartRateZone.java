package health.unittests.heartrate;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import health.codeundertest.HeartRate;


public class TestGetHeartRateZone {

	@Test
	public void testShouldGetZoneOneBelowAerobicBoundary() {
		HeartRate rate = new HeartRate(139);
		String actualZone = rate.getHeartRateZone();
		assertEquals("Temperate", actualZone);
	}
	
	@Test
	public void testShouldGetZoneOneAboveAerobicBoundary() {
		HeartRate rate = new HeartRate(141);
		String actualZone = rate.getHeartRateZone();
		assertEquals("Aerobic", actualZone);
	}

	@Test
	public void testShouldGetZoneAtAerobicBoundary() {
		HeartRate rate = new HeartRate(140);
		String actualZone = rate.getHeartRateZone();
		assertEquals("Aerobic", actualZone);
	}
}
