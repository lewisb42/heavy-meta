package kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import kitchen.codeundertest.MealPlanner;
import kitchen.codeundertest.Recipe;
import kitchen.unittests.mealplanner.TestFindQuickestRecipe;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

import static org.doubleoops.heavymeta.SafeAssertions.*;

public class MetaTestWhenLastIsQuickest {

	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestFindQuickestRecipe.class, "testWhenLastIsQuickest");
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var expectations = new Expectations() {
			boolean didCreateArrayList = false;
			public java.util.ArrayList<Recipe> capturedRecipes =
					new java.util.ArrayList<Recipe>();
			
			@Override
			protected void establishExpectations() {
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
		
		metaTester.runStudentsTestIgnoreFails();
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
		
		metaTester.runStudentsTestIgnoreFails();
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		// improbable object used as sentinel
		final Recipe sentinelRecipe = new Recipe("][po98uyhuyhjklhyh", 8347826);
		
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertStageExpectedValueShouldBeLastRecipe() {
		
		var expectations = new Expectations() {
			Recipe lastRecipe = null;
			Recipe expectedValueFromAssertion = null;
			
			@Override
			protected void establishExpectations() {
				expect(expectedValueFromAssertion == lastRecipe,
						"Your assertion's expected value (1st parameter) should be the same recipe that is last in your list.");
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				if (recipes.size() > 0) {
					expectations.lastRecipe = recipes.get(recipes.size() - 1);
				}
				return expectations.lastRecipe;
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
