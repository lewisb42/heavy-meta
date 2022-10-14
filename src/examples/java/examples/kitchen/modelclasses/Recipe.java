package examples.kitchen.modelclasses;

/**
 * Simple model of a recipe with a name and cook time.
 * 
 * @author lewisb
 *
 */
public class Recipe {
	private String name;
	private int cookTimeInMinutes;
	
	/**
	 * Creates new recipe with the given name and cook time
	 * 
	 * @precondition  name != null AND !name.isEmpty()
	 * 		AND cookTimeInMinutes >= 1
	 * @postcondition getName()==name AND getCookTimeInMinutes()==cookTimeInMinutes
	 * 
	 * @param name the name of the recipe
	 * @param cookTimeInMiniutes how long it takes to cook, in minutes
	 */
	public Recipe(String name, int cookTimeInMinutes) {
		if (name == null) {
			throw new IllegalArgumentException("name can't be null");
		}
		
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name can't be empty");
		}
		
		if (cookTimeInMinutes < 1) {
			throw new IllegalArgumentException("cookTimeInMinutes must be >= 1");
		}
		
		this.name = name;
		this.cookTimeInMinutes = cookTimeInMinutes;
	}
	
	/**
	 * Gets the cook time (in minutes)
	 * 
	 * @return the cook time (in minutes)
	 */
	public int getCookTimeInMinutes() {
		return this.cookTimeInMinutes;
	}
	
	/**
	 * Gets the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
}
