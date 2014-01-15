package ProjectF;
import Data.DB;

public class Main {
	public static void main(String[] args) throws Exception {
		DB.load();
//		double max = 0, pp=0, nn=0;
//		for (double p = -0.1; p < 0.2; p += 0.01)
//			for (double n = 0.1; n > -0.1; n -= 0.01) {
//				double result = new Simulator("label.txt", "pResult", p, n, 0).run();
//				if (result > max) {
//					max = result;
//					pp=p;
//					nn=n;
//				}
//			}
//		System.out.println(max);
//		System.out.println(pp);
//		System.out.println(nn);

//		System.out.println(new Simulator.Simulator("label.txt", "pResult", 0.11, 0.07, 0).run());

//		DB.load();
//		LinkedList<Runnable> runs = new LinkedList<Runnable>();
//		for (String date : new CalendarIterator("2009-01-01", "2011-01-01", Calendar.DAY_OF_MONTH))
//			runs.add(new ThreeBigParser(date));
//		Utility.runInThreadPool(runs, 4);
//		DB.save();

//		new CalculateRunner().run();
//		DB.get("0000").print(2466);
//		DB.get("0000").print(100);
///*
		new TextWriter("oo13.svm", "2004-01-01", "2013-12-31", "svm", true, false, true,
//				"oneDayAfter(open)(reg)",
//				"oneDay(close-open)(reg)",
//				"oneDay(close-close)(reg)",
				"oneDayAfter(open)",
//				"oneDay(close-open)",
//				"oneDay(close-close)",

				"自營商b-自營商s",
				"投信b-投信s",
				"外資及陸資b-外資及陸資s",

//				"tc0-tot",
				"tc0-to0",
				"tc0-to1",
				"tc0-to2",
				"tc0-th0",
				"tc0-th1",
				"tc0-th2",
				"tc0-tl0",
				"tc0-tl1",
				"tc0-tl2",
				"tc0-tc1",
				"tc0-tc1",

				"ic0-io0",
				"ic0-io1",
				"ic0-io2",
				"ic0-ih0",
				"ic0-ih1",
				"ic0-ih2",
				"ic0-il0",
				"ic0-il1",
				"ic0-il2",
				"ic0-ic1",
				"ic0-ic1",

				"gc0-go0",
				"gc0-go1",
				"gc0-go2",
				"gc0-gh0",
				"gc0-gh1",
				"gc0-gh2",
				"gc0-gl0",
				"gc0-gl1",
				"gc0-gl2",
				"gc0-gc1",
				"gc0-gc1",

				"sc0-so0",
				"sc0-so1",
				"sc0-so2",
				"sc0-sh0",
				"sc0-sh1",
				"sc0-sh2",
				"sc0-sl0",
				"sc0-sl1",
				"sc0-sl2",
				"sc0-sc1",
				"sc0-sc1"

		).run();
//*/
	}
}
