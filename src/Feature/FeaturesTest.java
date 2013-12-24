package Feature;
import Data.DB;
import junit.framework.TestCase;
import org.junit.Test;

public class FeaturesTest extends TestCase{
	@Test
	public void testRun() throws Exception {
		new CalculateRunner().run();

		DB.get("2330").print("2013-11-08");
	}
}
