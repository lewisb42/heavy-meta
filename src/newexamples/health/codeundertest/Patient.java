package health.codeundertest;

/**
 * This class represents a patient of a clinic.
 * 
 * @author	CS1301
 * @version	Spring 2020
 *
 */
public class Patient {

	private String firstName;
	private String lastName;
	private int age;

	/**
	 * Creates a new Patient with the given values, and initializes the Patient's
	 * medical chart.
	 * 
	 * @precondition firstName != null && !firstName.isEmpty() && lastName != null
	 *               && !lastName.isEmpty() && age > 0
	 * @postcondition getFirstName().equals(firstName) &&
	 *                getLastName.equals(lastName) && getAge() == age
	 * @param firstName First name to be assigned to this patient.
	 * @param lastName  Last name to be assigned to this patient.
	 * @param age       Age to be assigned to this patient.
	 */
	public Patient(String firstName, String lastName, int age) {
		if (firstName == null) {
			throw new IllegalArgumentException("first name can not be null");
		}
		
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException("first name can not be empty");
		}
		
		if (lastName == null) {
			throw new IllegalArgumentException("last name can not be null");
		}
		
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException("last name can not be empty");
		}
		
		if (age <= 0) {
			throw new IllegalArgumentException("age must be >0");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;

	}

	/**
	 * Returns this patient's first name.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return firstName This patient's first name.
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets this patient's first name.
	 * 
	 * @precondition firstName != null && !firstName.isEmpty()
	 * @postcondition getFirstName().equals(firstName)
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns this patient's last name.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return lastName This patient's last name.
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets this patient's last name.
	 * 
	 * @precondition lastName != null && !lastName.isEmpty()
	 * @postcondition getLastName().equals(lastName)
	 * @param lastName The last name to set to this patient.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns this patient's age.
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return age This patient's age.
	 */
	public int getAge() {
		return this.age;
	}

	/**
	 * Sets this patient's age.
	 * 
	 * @precondition age > 0
	 * @postcondition getAge() == age
	 * @param age The age to set to this patient.
	 */
	public void setAge(int age) {
		this.age = age;
	}

}
