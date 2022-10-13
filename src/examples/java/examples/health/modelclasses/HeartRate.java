package examples.health.modelclasses;

import java.time.LocalDate;

/**
 * This class represents the heart rate of a patient, in beats per minute (bpm).
 * 
 * @author	CS1301
 * @version	Spring 2020
 *
 */
public class HeartRate {
	private int heartRate;

	public static final int ABSOLUTE_MAX_HEART_RATE = 250;
	public static final int ABSOLUTE_MIN_HEART_RATE = 25;

	/**
	 * Creates a new heart rate with the given bpm
	 * 
	 * @precondition 25 <= heartRate <= 250
	 * 
	 * @postcondition getHeartRate() == heartRate
	 * @param beatsPerMinute The measured heartRate.
	 */
	public HeartRate(int beatsPerMinute) {
		
		if (beatsPerMinute < HeartRate.ABSOLUTE_MIN_HEART_RATE || beatsPerMinute > HeartRate.ABSOLUTE_MAX_HEART_RATE) {
			throw new IllegalArgumentException("beatsPerMinute must be between 25 and 250, inclusive");
		}
		
		this.heartRate = beatsPerMinute;
	}

	
	/**
	 * Gets a string representing the heart rate zone based on the
	 * following criteria:
	 * 
	 * zone                 heart rate
	 * ----------------------------------
	 * "Lower-intensity"    below 120
	 * "Temperate"			between 120 and 139, inclusive
	 * "Aerobic"			140 or higher
	 * 
	 * @return a string based on the table above
	 */
	public String getHeartRateZone() {
		if (this.heartRate < 120) {
			return "Lower-intensity";
		} else if (this.heartRate >= 120 && this.heartRate < 140) {
			return "Temperate";
		} else {
			return "Aerobic";
		}
	}

	/**
	 * Returns the measured heart rate.
	 * @precondition none
	 * @postcondition none
	 * @return the heartRate
	 */
	public int getHeartRate() {
		return this.heartRate;
	}

}