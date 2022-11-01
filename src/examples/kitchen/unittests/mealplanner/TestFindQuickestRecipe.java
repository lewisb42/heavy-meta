package kitchen.unittests.mealplanner;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import kitchen.codeundertest.MealPlanner;
import kitchen.codeundertest.Recipe;

// make sure this import it here; otherwise students
// will import the java.util.ArrayList instead of our
// fakeable body-double
import org.doubleoops.heavymeta.*;

public class TestFindQuickestRecipe {

	@Test
	public void testWhenListIsEmpty() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		Recipe actual = MealPlanner.findQuickestRecipe(recipes);
		assertNull(actual);
		
		// or:
		// assertEquals(null, actual);
		
		// NOT:
		// assertEquals(new Recipe("muffins", 5), actual);
	}
	
	@Test
	public void testWhenHasSingleElement() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		Recipe recipe0 = new Recipe("muffins", 5);
		recipes.add(recipe0);

		Recipe actual = MealPlanner.findQuickestRecipe(recipes);
		assertEquals(recipe0, actual);
	}
	
	@Test
	public void testWhenFirstIsQuickest() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		Recipe recipe0 = new Recipe("muffins", 5);
		recipes.add(recipe0);
		
		Recipe recipe1 = new Recipe("cake", 15);
		recipes.add(recipe1);
		
		Recipe recipe2 = new Recipe("sourdough", 25);
		recipes.add(recipe2);
		
		Recipe actual = MealPlanner.findQuickestRecipe(recipes);
		//assertEquals(recipe0, actual);
		
		// or:
		assertSame(recipe0, actual);
	}
	
	@Test
	public void testWhenLastIsQuickest() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		Recipe recipe0 = new Recipe("muffins", 25);
		recipes.add(recipe0);
		
		Recipe recipe1 = new Recipe("cake", 15);
		recipes.add(recipe1);
		
		Recipe recipe2 = new Recipe("sourdough", 5);
		recipes.add(recipe2);
		
		Recipe actual = MealPlanner.findQuickestRecipe(recipes);
		//assertEquals(recipe2, actual);
		
		// or:
		assertSame(recipe2, actual);
	}

	@Test
	public void testWhenMiddleIsQuickest() {
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		Recipe recipe0 = new Recipe("muffins", 15);
		recipes.add(recipe0);
		
		Recipe recipe1 = new Recipe("cake", 5);
		recipes.add(recipe1);
		
		Recipe recipe2 = new Recipe("sourdough", 25);
		recipes.add(recipe2);
		
		Recipe actual = MealPlanner.findQuickestRecipe(recipes);
		assertEquals(recipe1, actual);
		
		// or:
		// assertSame(recipe0, actual);
	}
}
