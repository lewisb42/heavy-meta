package kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.ArrayList;
import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.StandardMetaTestChecks;
import org.doubleoops.heavymeta.MetaTestBase;
import org.doubleoops.heavymeta.MockedUpAssertEqualsForObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import kitchen.codeundertest.MealPlanner;
import kitchen.codeundertest.Recipe;
import kitchen.unittests.mealplanner.TestFindQuickestRecipe;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestWhenMiddleIsQuickest extends MetaTestBase {
	private static final Class<?> DEFAULT_TEST_CLASS = TestFindQuickestRecipe.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testWhenMiddleIsQuickest";
	
	public MetaTestWhenMiddleIsQuickest() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}

	@Test
	public void shouldHaveArrangeStage() {
		
		var expectations = new Expectations() {
			boolean didCreateArrayList = false;
			java.util.ArrayList<Recipe> capturedRecipes =
					new java.util.ArrayList<Recipe>();
			
			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				expect(didCreateArrayList,
						"Did not create an ArrayList of Recipes in your Arrange stage.");
				
				expect(capturedRecipes.size() >= 3,
						"Did not add at least 3 Recipe objects to your list");
						
				expect(elementsAreUnique(capturedRecipes),
						"You added at least one Recipe object to the list twice. Instead, create 3 unique Recipe objects and add each one individually.");
			}
		};
		
		new MockUp<ArrayList<Recipe> >() {
			@Mock
			public void $init() {
				expectations.didCreateArrayList = true;
			}
			
			@Mock
			public boolean add(Object obj) {
				
				if (obj instanceof Recipe) {
					expectations.capturedRecipes.add((Recipe)obj);
				}
				return true;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var expectations = new Expectations() {
			boolean didAct = false;
			
			@Override
			protected void establishExpectations() {
				expect(didAct,
						"Did not call findQuickestRecipe() in your Act stage");
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(Invocation inv, ArrayList<Recipe> recipes) {
				expectations.didAct = true;
				return inv.proceed(recipes);
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void shouldHaveAssertStage() {
		
		var expectations = new Expectations() {
			boolean usedValidAssertion = false;
			
			@Override
			protected void establishExpectations() {
				expect(usedValidAssertion,
						"Invalid (or no) useful assertion found. Try an assertEquals with the Recipe object.");
			}
		};
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if ((expected instanceof Recipe) && (actual instanceof Recipe)) {
					expectations.usedValidAssertion = true;
					return;
				}
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		// improbable object used as sentinel
		final Recipe sentinelRecipe = new Recipe("][po98uyBVFTYHJU", 93426);		
		
		var expectations = new Expectations() {
			Recipe actualValueFromAssertion = null;
			
			@Override
			protected void establishExpectations() {
				expect(actualValueFromAssertion == sentinelRecipe,
						"Did not use the return value of findQuickestRecipe as the actual value (2nd parameter) of your assertion.");
			}
		};
		
		new MockUp<MealPlanner>() {	
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				return sentinelRecipe;
			}
		};
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(actual instanceof Recipe)) {
					return;
				}
				
				expectations.actualValueFromAssertion = (Recipe) actual;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertStageExpectedValueShouldBeAMiddleRecipe() {
		
		var expectations = new Expectations() {
			ArrayList<Recipe> capturedRecipes = new ArrayList<Recipe>();
			Recipe assertStageExpectedValue = null;
			
			@Override
			protected void establishExpectations() {
				expect(isMiddleValue(assertStageExpectedValue, capturedRecipes),
						"You did not add Recipes to your list such that the quickest one is in the interior of the list");
			}

			private boolean isMiddleValue(Recipe recipe, ArrayList<Recipe> list) {
				if (!list.contains(recipe)) return false;
				if (recipe == list.get(0)) return false;
				if (recipe == list.get(list.size()-1)) return false;
				return true;
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(Invocation inv, ArrayList<Recipe> recipes) {
				expectations.capturedRecipes = recipes;
				return inv.proceed(recipes);
			}
		};
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(expected instanceof Recipe)) {
					return;
				}
				
				expectations.assertStageExpectedValue = (Recipe) expected;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
