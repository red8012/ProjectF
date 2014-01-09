package ProjectF;
import Data.DB;
import Feature.CalculateRunner;

public class Main {

	public static void main(String[] args) throws Exception {
		DB.load();
		new CalculateRunner().run();
		DB.get("0000").print(2466);
		DB.get("0000").print(2467);
		new TextWriter("csv2.csv", "2004-01-01", "2013-12-31", "csv", false, true,
				"oneDayAfter(open)(reg)",
				"oneDayAfter(open)(reg)",
				"oneDay(close-open)(reg)",
				"oneDay(close-close)(reg)",
				"IXICClose-",
				"GSPCClose-",
				"SOXClose-",
				"IXICOpen-",
				"GSPCOpen-",
				"SOXOpen-"
		).run();
	}
}
