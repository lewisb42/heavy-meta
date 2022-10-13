package examples.weather.modelclasses;

/**
 * a class-under-test, used for basic constructor tests
 * 
 * Or, perhaps, just show the JavaDoc for the relevant methods.
 * @author lewisb
 *
 */
public class Measurement {

	private String location;
	private int temperatureInCelsius;
	public Measurement(String location, int temperatureInCelsius) {
		if (location == null) {
			throw new IllegalArgumentException("invalid location");
		}
		
		if (location.isEmpty()) {
			throw new IllegalArgumentException("invalid location");
		}
		
		if (temperatureInCelsius < -273) {
			throw new IllegalArgumentException("invalid temperatureInCelsius");
		}
		
		this.location = location;
		this.temperatureInCelsius = temperatureInCelsius;
	}
	public String getLocation() {
		return location;
	}
	public int getTemperatureInCelsius() {
		return temperatureInCelsius;
	}
	
}
