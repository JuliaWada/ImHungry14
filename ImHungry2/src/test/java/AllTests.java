import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CollageTestCase.class, RecipeTestCase.class , RestaurantTestCase.class, ServletTestCase.class, ListMgmtTestCases.class})
public class AllTests {
	
}
