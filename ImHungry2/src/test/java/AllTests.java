import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CollageTestCase.class, RecipeTestCase.class , ServletTestCase.class, RestaurantTestCase.class})
public class AllTests {

}
