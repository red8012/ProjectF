package ProjectF;
import Feature.CalculateRunner;
import Parser.ParserRunner;

public class Main {

	public static void main(String[] args) throws Exception {
//		new ParserRunner("2013-01-01","2013-12-25",true).run();
//		new CalculateRunner().run();
		new TextWriter("fw.svm", "2013-03-01", "2013-12-24", "svm", true, false,
				"oneWeekAfter(open)", "centerBPercentage", "foreignBuyPercentage",
				"threeBigBPercentage").run();
	}
}
