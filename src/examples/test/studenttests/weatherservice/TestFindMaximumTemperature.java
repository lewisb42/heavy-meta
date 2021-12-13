package studenttests.weatherservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Measurement;
import model.WeatherService;

public class TestFindMaximumTemperature {

	@Test
	public void testWhenNoMeasurements() {
		WeatherService ws = new WeatherService();
		assertEquals(Integer.MIN_VALUE, ws.findMaximumTemperature());
	}
	
	@Test
	public void testWhenOneMeasurement() {
		WeatherService ws = new WeatherService();
		ws.add(new Measurement("Atlanta", 75));
		assertEquals(75, ws.findMaximumTemperature());
	}

	@Test
	public void testWhenMaxIsDuplicated() {
		WeatherService ws = new WeatherService();
		ws.add(new Measurement("Atlanta", 75));
		ws.add(new Measurement("Atlanta", 75));
		assertEquals(75, ws.findMaximumTemperature());
	}
	
	@Test
	public void testWhenMaxIsFirstElement() {
		WeatherService ws = new WeatherService();
		ws.add(new Measurement("Atlanta", 300));
		ws.add(new Measurement("Atlanta", 200));
		ws.add(new Measurement("Atlanta", 100));
		assertEquals(300, ws.findMaximumTemperature());
	}
	
	@Test
	public void testWhenMaxIsLastElement() {
		WeatherService ws = new WeatherService();
		ws.add(new Measurement("Atlanta", 100));
		ws.add(new Measurement("Atlanta", 200));
		ws.add(new Measurement("Atlanta", 300));
		assertEquals(300, ws.findMaximumTemperature());
	}
	
	@Test
	public void testWhenMaxIsMiddleElement() {
		WeatherService ws = new WeatherService();
		ws.add(new Measurement("Atlanta", 200));
		ws.add(new Measurement("Atlanta", 300));
		ws.add(new Measurement("Atlanta", 100));
		assertEquals(300, ws.findMaximumTemperature());
	}
}
