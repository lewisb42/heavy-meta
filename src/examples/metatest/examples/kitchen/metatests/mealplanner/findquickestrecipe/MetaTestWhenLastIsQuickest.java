package examples.kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import examples.kitchen.modelclasses.MealPlanner;
import examples.kitchen.modelclasses.Recipe;
import examples.kitchen.studentunittests.mealplanner.TestFindQuickestRecipe;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

import static org.doubleoops.heavymeta.SafeAssertions.*;

public class MetaTestWhenLastIsQuickest {

	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestFindQuickestRecipe.class, "testWhenLastIsQuickest");
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var fakeList = new MockUp<ArrayList<Recipe> >() {
			public boolean didCreate = false;
			public java.util.ArrayList<Recipe> capturedRecipes =
					new java.util.ArrayList<Recipe>();
			
			@Mock
			public void $init() {
				didCreate = true;
			}
			
			@Mock
			public boolean add(Object obj) {
				
				if (obj instanceof Recipe) {
					capturedRecipes.add((Recipe)obj);
				}
				return true;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeList.didCreate,
				"Did not create an ArrayList of Recipes in your Arrange stage.");
		
		assertTrue(fakeList.capturedRecipes.size() >= 3,
				"Did not add at least 3 Recipe objects to your list");
				
		safeAssertElementsUnique(fakeList.capturedRecipes,
				"You added at least one Recipe object to the list twice. Instead, create 3 unique Recipe objects and add each one individually.");
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var fakeList = new MockUp<MealPlanner>() {
			public static boolean didAct = false;
			
			@Mock
			public static Recipe findQuickestRecipe(Invocation inv, ArrayList<Recipe> recipes) {
				didAct = true;
				return inv.proceed(recipes);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeList.didAct,
				"Did not call findQuickestRecipe() in your Act stage");
	}

	@Test
	public void shouldHaveAssertStage() {
		
		var fakedAssertions = new MockUp<Assertions>() {
			public static boolean usedAssertEquals = false;
			public static boolean usedAssertSame = false;
			
			public static boolean usedValidAssertion() {
				return usedAssertEquals || usedAssertSame;
			}
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				if ((expected instanceof Recipe) && (actual instanceof Recipe)) {
					usedAssertEquals = true;
					return;
				}
			}
			
			@Mock
			public static void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.usedValidAssertion(),
				"Invalid (or no) useful assertion found. Try an assertEquals with the Recipe object.");
	}

	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		
		
		var fakedMealPlanner = new MockUp<MealPlanner>() {
			// improbable object used as sentinel
			public static final Recipe sentinelRecipe = new Recipe("][po98uyhuyhjklhyh", 8347826);
			
			@Mock
			public static Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				return sentinelRecipe;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			public static boolean gotRecipeFromActStage = false;
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				if (!(actual instanceof Recipe)) {
					return;
				}
				
				Recipe actualRecipe = (Recipe) actual;
				gotRecipeFromActStage = (actualRecipe == fakedMealPlanner.sentinelRecipe);
			}
			
			@Mock
			public static void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.gotRecipeFromActStage,
				"Did not use the return value of findQuickestRecipe as the actual value (2nd parameter) of your assertion.");
	}
	
	@Test
	public void assertStageExpectedValueShouldBeLastRecipe() {
		
		var fakedMealPlanner = new MockUp<MealPlanner>() {
			
			public static Recipe lastRecipe = null;
			
			@Mock
			public static Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				if (recipes.size() > 0) {
					lastRecipe = recipes.get(recipes.size() - 1);
				}
				return lastRecipe;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			
			public static boolean expectedIsFirstRecipe = false;
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				if (!(expected instanceof Recipe)) {
					return;
				}
				
				Recipe expectedRecipe = (Recipe) expected;
				expectedIsFirstRecipe = (expectedRecipe == fakedMealPlanner.lastRecipe);
			}
			
			@Mock
			public static void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public static void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.expectedIsFirstRecipe,
				"Your assertion's expected value (1st parameter) should be the same recipe that is first in your list.");
	}
}
