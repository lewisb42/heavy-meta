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
		
		var fakeList = new MockUp<ArrayList<Recipe> >() {
			public static boolean didCreate = false;
			public static boolean didAddItems = false;
			
			@Mock
			public void $init() {
				didCreate = true;
			}
			
			@Mock
			public boolean add(Recipe r) {
				didAddItems = true;
				return true;
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakeList.didCreate,
				"Did not create empty ArrayList<Recipe>.");
		assertFalse(fakeList.didAddItems,
				"You should not add Recipes to the list, since this is the empty list case");
	}
	
	@Test
	public void shouldHaveActStage() {
		
		var fakedMealPlanner = new MockUp<MealPlanner>() {
			public static boolean didAct = false;
			
			@Mock
			public static Recipe findQuickestRecipe(Invocation inv, ArrayList<Recipe> recipes) {
				didAct = true;
				return inv.proceed(recipes);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		assertTrue(fakedMealPlanner.didAct,
				"You did not call findQuickestRecipe() in your Act stage.");
	}
	
	@Test
	public void shouldHaveAssertStage() {
		
		var fakedAssertions = new MockUp<Assertions>() {
			public static boolean usedAssertEquals = false;

			public static boolean usedAssertNull = false;
			
			public static boolean usedValidAssertion() {
				return usedAssertEquals || usedAssertNull;
			}
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				usedAssertEquals = true;
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
			
			@Mock
			public static void assertNull(Object actual) {
				usedAssertNull = true;
			}
			
			@Mock
			public static void assertNull(Object actual, String msg) {
				assertNull(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.usedValidAssertion(),
			"Did not use a valid assertion; assertEquals or assertNull are good choices here.");	
	}
	
	@Test
	public void expectedValueShouldBeNull() {
		var fakedAssertions = new MockUp<Assertions>() {
			public static boolean expectedIsNull = false;
			
			
			@Mock
			public static void assertEquals(Object expected, Object actual) {
				expectedIsNull = (expected == null);
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
			
			@Mock
			public static void assertNull(Object actual) {
				expectedIsNull = true;
			}
			
			@Mock
			public static void assertNull(Object actual, String msg) {
				assertNull(actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.expectedIsNull,
				"Expected value of your assertion should be null.");
	}
	
	@Test
	public void assertStageActualValueShouldComeFromActStage() {
		
		
		var fakedMealPlanner = new MockUp<MealPlanner>() {
			// improbable object used as sentinel
			public static final Recipe sentinelRecipe = new Recipe("}{POI(*&^^TYHJN", 95638);
			
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
			
			@Mock
			public static void assertNull(Object actual) {
				assertEquals(null, actual);
			}
			
			@Mock
			public static void assertNull(Object actual, String msg) {
				assertEquals(null, actual);
			}
		};
		
		metaTester.runStudentsTestIgnoreFails();
		
		safeAssertTrue(fakedAssertions.gotRecipeFromActStage,
				"Did not use the return value of findQuickestRecipe as the actual value (2nd parameter) of your assertion.");
	}
}
