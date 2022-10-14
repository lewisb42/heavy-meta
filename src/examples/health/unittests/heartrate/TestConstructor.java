package health.unittests.heartrate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import health.codeundertest.HeartRate;



/**
 * Class to test for correct functionality of the
 * 	MedicalRecord constructor
 * 
 * @author	CS1301
 * @version	Spring 2020
 *
 */

public class TestConstructor {


	

	@Test
	public void shouldNotAllowHeartRateOneBelowMin() {
		assertThrows(IllegalArgumentException.class, () -> {
			new HeartRate(24);
		});
	}

	@Test
	public void shouldNotAllowHeartRateSeveralBeatsBelowMin() {
		assertThrows(IllegalArgumentException.class, () -> {
			new HeartRate(20);
		});
	}

	@Test
	public void shouldNotAllowHeartRateOneAboveMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			new HeartRate(251);
		});
	}

	@Test
	public void shouldNotAllowHeartRateSeveralBeatsAboveMax() {
		assertThrows(IllegalArgumentException.class, () -> {
			new HeartRate(255);
		});
	}

	@Test
	public void shouldAllowHeartRateAtMin() {
		HeartRate record = new HeartRate(25);
		assertEquals(25, record.getHeartRate());
	}

	@Test
	public void shouldAllowHeartRateAtMax() {
		HeartRate record = new HeartRate(250);
		assertEquals(250, record.getHeartRate());
	}

	@Test
	public void shouldCreateRecord() {
		HeartRate record = new HeartRate(25);
		assertEquals(25, record.getHeartRate());
	}

}
