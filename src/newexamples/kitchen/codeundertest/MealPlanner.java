package kitchen.codeundertest;

import org.doubleoops.heavymeta.*;
/**
 * A class of utilities to help a chef plan a meal.
 * 
 * @author lewisb
 *
 */
public class MealPlanner {
	
	/**
	 * Finds the quickest recipe (i.e., with the shortest cook time)
	 * in the given list.
	 * 
	 * @precondition recipes != null
	 * @postcondition none
	 * 
	 * @param recipes a list of recipes
	 * @return the Recipe with the shortest cook time
	 */
	public static Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
		if (recipes == null) {
			throw new IllegalArgumentException("recipes should not be null");
		}
		
		if (recipes.isEmpty()) {
			return null;
		}
		
		Recipe shortest = recipes.get(0);
		
		for (Recipe currentRecipe : recipes) {
			if (shortest.getCookTimeInMinutes() > currentRecipe.getCookTimeInMinutes()) {
				shortest = currentRecipe;
			}
		}
		
		return shortest;
	}
}
