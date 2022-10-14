package weather.codeundertest;

import java.util.ArrayList;

public class WeatherService {

	private ArrayList<Measurement> measurements;
	
	public WeatherService() {
		this.measurements = new ArrayList<Measurement>();
	}
	
	public void add(Measurement measurement) {
		if (measurement == null) {
			throw new IllegalArgumentException("measurement can't be null");
		}
		
		this.measurements.add(measurement);
	}
	
	public ArrayList<Measurement> getMeasurements() {
		return this.measurements;
	}
	
	public int findMaximumTemperature() {
		int max = Integer.MIN_VALUE;
		for (Measurement current: this.measurements) {
			if (current.getTemperatureInCelsius() > max) {
				max = current.getTemperatureInCelsius();
			}
		}
		return max;
	}
}
