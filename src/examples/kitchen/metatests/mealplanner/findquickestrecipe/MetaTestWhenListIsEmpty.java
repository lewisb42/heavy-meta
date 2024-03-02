package kitchen.metatests.mealplanner.findquickestrecipe;

import org.doubleoops.heavymeta.ArrayList;
import org.doubleoops.heavymeta.Expectations;
import org.doubleoops.heavymeta.MetaTestBase;
import org.doubleoops.heavymeta.MockedUpAssertEqualsForObjects;
import org.junit.jupiter.api.Test;

import kitchen.codeundertest.MealPlanner;
import kitchen.codeundertest.Recipe;
import kitchen.unittests.mealplanner.TestFindQuickestRecipe;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

public class MetaTestWhenListIsEmpty extends MetaTestBase {
	private static final Class<?> DEFAULT_TEST_CLASS = TestFindQuickestRecipe.class;
	private static final String DEFAULT_TEST_METHOD_NAME = "testWhenListIsEmpty";
	
	public MetaTestWhenListIsEmpty() {
		super(DEFAULT_TEST_CLASS, DEFAULT_TEST_METHOD_NAME);
	}

	
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
						"Did not use a valid assertion; assertEquals or assertNull are good choices here.");
			}
		};
		
		new MockedUpAssertEqualsForObjects() {

			@Mock
			public void assertEquals(Object expected, Object actual) {
				expectations.usedValidAssertion = true;
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
		
		runStudentsTestIgnoreFails();
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
		
		new MockedUpAssertEqualsForObjects() {
			@Mock
			public void assertEquals(Object expected, Object actual) {
				expectations.expectedValueFromAssertionIsNull = (expected == null);
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
		
		runStudentsTestIgnoreFails();
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
		
		new MockedUpAssertEqualsForObjects() {
			
			@Mock
			public void assertEquals(Object expected, Object actual) {
				if (!(actual instanceof Recipe)) {
					return;
				}
				
				expectations.actualValueFromAssertion = (Recipe) actual;
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
		
		runStudentsTestIgnoreFails();
		expectations.assertPassed();
	}
}
