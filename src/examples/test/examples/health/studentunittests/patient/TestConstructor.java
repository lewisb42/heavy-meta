package examples.health.studentunittests.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import examples.health.modelclasses.Patient;

/**
 * Class to test for correct functionality of the
 * 	Patient constructor
 * 
 * @author	CS1301
 * @version	Spring 2020
 *
 */
public class TestConstructor {
	
	
	@Test
	public void testShouldCreateValidPatient() {
		Patient patient0 = new Patient("Lando", "Calrissian", 50);
		assertEquals("Lando", patient0.getFirstName());
		assertEquals("Calrissian", patient0.getLastName());
		assertEquals(50, patient0.getAge());
	}
	
	@Test
	public void testFirstNameShouldNotBeNull() {
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Patient(null, "Calrissian", 50);
		});
	}
	
	
	
	
}
