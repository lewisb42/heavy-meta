package kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.ArrayList;
import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.HeavyMeta;
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

public class MetaTestWhenHasSingleElement extends MetaTestBase {
	private static final Class<?> DEFAULT_TEST_CLASS = TestFindQuickestRecipe.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testWhenHasSingleElement";
	
	public MetaTestWhenHasSingleElement() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var expectations = new Expectations() {
			boolean didCreateRecipe = false;
			java.util.ArrayList<Recipe> capturedRecipes =
					new java.util.ArrayList<Recipe>();
			
			@Override
			protected void establishExpectations() {
				expect(didCreateRecipe,
						"Did not create an ArrayList of Recipes in your Arrange stage.");
				
				expect(capturedRecipes.size() == 1,
						"Did not add exactly 1 Recipe to the list.");
			}
		};
		
		new MockUp<ArrayList<Recipe> >() {
			@Mock
			public void $init() {
				expectations.didCreateRecipe = true;
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
		final Recipe sentinelRecipe = new Recipe("XERFGVBHUJK", 9376555);
		
		var expectations = new Expectations() {
			Recipe assertStageActualValue = null;
			
			@Override
			protected void establishExpectations() {
				expect(assertStageActualValue == sentinelRecipe,
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
				
				expectations.assertStageActualValue = (Recipe)actual;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertStageExpectedValueShouldBeTheOneAndOnlyRecipe() {
		
		var expectations = new Expectations() {
			Recipe expectedValueFromAssertion = null;
			Recipe recipeAddedToList = null;
			
			@Override
			protected void establishExpectations() {
				expect(expectedValueFromAssertion == recipeAddedToList,
						"Your assertion's expected value (1st parameter) should be the same recipe that you added to the list.");
			}
		};
		
		new MockUp<ArrayList<Recipe>>() {
			@Mock
			public boolean add(Recipe r) {
				expectations.recipeAddedToList = r;
				return true;
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				// return junk Recipe to avoid assertEquals(actual, actual) situation
				return new Recipe("&UHJKUYHJ", 827564);
			}
		};
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(expected instanceof Recipe)) {
					return;
				}
				
				expectations.expectedValueFromAssertion = (Recipe) expected;
			}
		};
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
