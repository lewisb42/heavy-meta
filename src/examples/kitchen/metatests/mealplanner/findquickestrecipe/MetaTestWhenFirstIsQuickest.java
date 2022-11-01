package kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;

import org.doubleoops.heavymeta.HeavyMeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import kitchen.codeundertest.MealPlanner;
import kitchen.codeundertest.Recipe;
import kitchen.unittests.mealplanner.TestFindQuickestRecipe;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

import static org.doubleoops.heavymeta.SafeAssertions.*;

public class MetaTestWhenFirstIsQuickest {

	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestFindQuickestRecipe.class, "testWhenFirstIsQuickest");
	
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
				
				expect(capturedRecipes.size() >= 3,
						"Did not add at least 3 Recipe objects to your list");
						
				expect(elementsAreUnique(capturedRecipes),
						"You added at least one Recipe object to the list twice. Instead, create 3 unique Recipe objects and add each one individually.");
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
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var expectations = new Expectations() {
			boolean didAct = false;
			@Override
			protected void establishExpectations() {
				assertTrue(didAct,
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
			boolean usedAssertEquals = false;
			boolean usedAssertSame = false;
			
			@Override
			protected void establishExpectations() {
				// TODO Auto-generated method stub
				expect(usedAssertEquals || usedAssertSame,
						"Invalid (or no) useful assertion found. Try an assertEquals with the Recipe object.");
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if ((expected instanceof Recipe) && (actual instanceof Recipe)) {
					expectations.usedAssertEquals = true;
					return;
				}
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}

	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		// improbable object used as sentinel
		final Recipe sentinelRecipe = new Recipe("nt6789okiuyhjklhyh", 34567826);
		
		var expectations = new Expectations() {
			boolean gotRecipeFromActStage = false;
			
			@Override
			protected void establishExpectations() {
				expect(gotRecipeFromActStage,
						"Did not use the return value of findQuickestRecipe as the actual value (2nd parameter) of your assertion.");
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				return sentinelRecipe;
			}
		};
		
		var fakedAssertions = new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(actual instanceof Recipe)) {
					return;
				}
				
				Recipe actualRecipe = (Recipe) actual;
				expectations.gotRecipeFromActStage = (actualRecipe == sentinelRecipe);
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertStageExpectedValueShouldBeFirstRecipe() {
		
		var expectations = new Expectations() {
			Recipe returnedFirstRecipe = null;
			Recipe expectedValueFirstRecipe = null;
			@Override
			protected void establishExpectations() {
				expect(returnedFirstRecipe == expectedValueFirstRecipe,
						"Your assertion's expected value (1st parameter) should be the same recipe that is first in your list.");
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				if (recipes.size() > 0) {
					expectations.returnedFirstRecipe = recipes.get(0);
				}
				return expectations.returnedFirstRecipe;
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(expected instanceof Recipe)) {
					return;
				}
				
				expectations.expectedValueFirstRecipe = (Recipe) expected;
			}
			
			@Mock
			public void assertEquals(Object expected, Object actual, String msg) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual) {
				assertEquals(expected, actual);
			}
			
			@Mock
			public void assertSame(Object expected, Object actual, String msg) {
				assertSame(expected, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
