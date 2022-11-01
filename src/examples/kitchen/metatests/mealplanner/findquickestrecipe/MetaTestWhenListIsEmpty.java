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

public class MetaTestWhenListIsEmpty {

	
	@RegisterExtension
	static HeavyMeta metaTester = new HeavyMeta(TestFindQuickestRecipe.class, "testWhenListIsEmpty");
	
	@Test
	public void shouldHaveArrangeStage() {
		
		var expectations = new Expectations() {
			boolean didCreateArrayList = false;
			boolean didNotAddItemsToList = true;
			
			@Override
			protected void establishExpectations() {
				expect(didCreateArrayList,
						"Did not create empty ArrayList<Recipe>.");
				expect(didNotAddItemsToList,
						"You should not add Recipes to the list, since this is the empty list case");
			}
		};
		
		new MockUp<ArrayList<Recipe> >() {
			@Mock
			public void $init() {
				expectations.didCreateArrayList = true;
			}
			
			@Mock
			public boolean add(Recipe r) {
				expectations.didNotAddItemsToList = false;
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
						"You did not call findQuickestRecipe() in your Act stage.");
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
						"Did not use a valid assertion; assertEquals or assertNull are good choices here.");
			}
		};
		
		new MockUp<Assertions>() {

			@Mock
			public void assertEquals(Object expected, Object actual) {
				expectations.usedValidAssertion = true;
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
			
			@Mock
			public void assertNull(Object actual) {
				expectations.usedValidAssertion = true;
			}
			
			@Mock
			public void assertNull(Object actual, String msg) {
				assertNull(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();	
	}
	
	@Test
	public void expectedValueShouldBeNull() {
		
		var expectations = new Expectations() {
			boolean expectedValueFromAssertionIsNull = false;
			
			@Override
			protected void establishExpectations() {
				expect(expectedValueFromAssertionIsNull,
						"Expected value of your assertion should be null.");
			}
		};
		
		new MockUp<Assertions>() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				expectations.expectedValueFromAssertionIsNull = (expected == null);
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
			
			@Mock
			public void assertNull(Object actual) {
				expectations.expectedValueFromAssertionIsNull = true;
			}
			
			@Mock
			public void assertNull(Object actual, String msg) {
				assertNull(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
	
	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		// improbable object used as sentinel
		final Recipe sentinelRecipe = new Recipe("}{POI(*&^^TYHJN", 95638);
		
		var expectations = new Expectations() {
			Recipe actualValueFromAssertion = null;
			Recipe returnValueFromAct = sentinelRecipe;
			
			@Override
			protected void establishExpectations() {
				expect(actualValueFromAssertion == returnValueFromAct,
						"Did not use the return value of findQuickestRecipe as the actual value (2nd parameter) of your assertion.");
			}
		};
		
		new MockUp<MealPlanner>() {
			@Mock
			public Recipe findQuickestRecipe(ArrayList<Recipe> recipes) {
				return sentinelRecipe;
			}
		};
		
		new MockUp<Assertions>() {
			
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(actual instanceof Recipe)) {
					return;
				}
				
				expectations.actualValueFromAssertion = (Recipe) actual;
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
			
			@Mock
			public void assertNull(Object actual) {
				assertEquals(null, actual);
			}
			
			@Mock
			public void assertNull(Object actual, String msg) {
				assertEquals(null, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
