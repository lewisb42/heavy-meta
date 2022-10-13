package examples.weather.studentunittests.weatherservice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import examples.weather.modelclasses.Measurement;
import examples.weather.modelclasses.WeatherService;

public class TestFindMaximumTemperature {

	private WeatherService weatherService;
	@BeforeEach
	public void setup() {
		weatherService = new WeatherService();
	}
	
	@Test
	public void testWhenNoMeasurements() {
		WeatherService ws = new WeatherService();
		assertEquals(Integer.MIN_VALUE, ws.findMaximumTemperature());
	}
	
	@Test
	public void testWhenOneMeasurement() {
		weatherService.add(new Measurement("Atlanta", 75));
		assertEquals(75, weatherService.findMaximumTemperature());
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
