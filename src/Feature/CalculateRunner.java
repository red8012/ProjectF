package Feature;
import Data.DB;
import Data.Fields;
import Feature.Modules.Delta;
import Feature.Modules.MinusOverPlus;

import java.util.LinkedList;

public class CalculateRunner implements Runnable {
	public CalculateRunner() {
	}

	@Override
	public void run() {
		DB.load();
		final ModuleFactory modules[] = {
				new Delta("oneDayAfter(open)(reg)", 2, 1, "open", "open", false),
				new Delta("oneDay(close-open)(reg)", 1, 1, "close", "open", false),
				new Delta("oneDay(close-close)(reg)", 1, 0, "close", "close", false),
				new Delta("oneDayAfter(open)", 2, 1, "open", "open",     true),
				new Delta("oneDay(close-open)", 1, 1, "close", "open",   true),
				new Delta("oneDay(close-close)", 1, 0, "close", "close", true),

				// Three big
				new MinusOverPlus("自營商b", "自營商s"),
				new MinusOverPlus("投信b", "投信s"),
				new MinusOverPlus("外資及陸資b", "外資及陸資s"),

				// TW
				new Delta("tc0-tot", 0, 1, "close", "open", false),
				new Delta("tc0-to0", 0, 0, "close", "open", false),
				new Delta("tc0-to1", 0, -1, "close", "open", false),
				new Delta("tc0-to2", 0, -2, "close", "open", false),
				new Delta("tc0-th0", 0, 0, "close", "high", false),
				new Delta("tc0-th1", 0, -1, "close", "high", false),
				new Delta("tc0-th2", 0, -2, "close", "high", false),
				new Delta("tc0-tl0", 0, 0, "close", "low", false),
				new Delta("tc0-tl1", 0, -1, "close", "low", false),
				new Delta("tc0-tl2", 0, -2, "close", "low", false),
				new Delta("tc0-tc1", 0, -1, "close", "close", false),
				new Delta("tc0-tc1", 0, -2, "close", "close", false),
				// TW

				// IXIC
				new Delta("ic0-io0", 0, 0, "IXICClose", "IXICOpen", false),
				new Delta("ic0-io1", 0, -1, "IXICClose", "IXICOpen", false),
				new Delta("ic0-io2", 0, -2, "IXICClose", "IXICOpen", false),
				new Delta("ic0-ih0", 0, 0, "IXICClose", "IXICHigh", false),
				new Delta("ic0-ih1", 0, -1, "IXICClose", "IXICHigh", false),
				new Delta("ic0-ih2", 0, -2, "IXICClose", "IXICHigh", false),
				new Delta("ic0-il0", 0, 0, "IXICClose", "IXICLow", false),
				new Delta("ic0-il1", 0, -1, "IXICClose", "IXICLow", false),
				new Delta("ic0-il2", 0, -2, "IXICClose", "IXICLow", false),
				new Delta("ic0-ic1", 0, -1, "IXICClose", "IXICClose", false),
				new Delta("ic0-ic1", 0, -2, "IXICClose", "IXICClose", false),
				// IXIC

				// GSPC
				new Delta("gc0-go0", 0, 0, "GSPCClose", "GSPCOpen", false),
				new Delta("gc0-go1", 0, -1, "GSPCClose", "GSPCOpen", false),
				new Delta("gc0-go2", 0, -2, "GSPCClose", "GSPCOpen", false),
				new Delta("gc0-gh0", 0, 0, "GSPCClose", "GSPCHigh", false),
				new Delta("gc0-gh1", 0, -1, "GSPCClose", "GSPCHigh", false),
				new Delta("gc0-gh2", 0, -2, "GSPCClose", "GSPCHigh", false),
				new Delta("gc0-gl0", 0, 0, "GSPCClose", "GSPCLow", false),
				new Delta("gc0-gl1", 0, -1, "GSPCClose", "GSPCLow", false),
				new Delta("gc0-gl2", 0, -2, "GSPCClose", "GSPCLow", false),
				new Delta("gc0-gc1", 0, -1, "GSPCClose", "GSPCClose", false),
				new Delta("gc0-gc1", 0, -2, "GSPCClose", "GSPCClose", false),
				// GSPC

				// SOX
				new Delta("sc0-so0", 0, 0, "SOXClose", "SOXOpen", false),
				new Delta("sc0-so1", 0, -1, "SOXClose", "SOXOpen", false),
				new Delta("sc0-so2", 0, -2, "SOXClose", "SOXOpen", false),
				new Delta("sc0-sh0", 0, 0, "SOXClose", "SOXHigh", false),
				new Delta("sc0-sh1", 0, -1, "SOXClose", "SOXHigh", false),
				new Delta("sc0-sh2", 0, -2, "SOXClose", "SOXHigh", false),
				new Delta("sc0-sl0", 0, 0, "SOXClose", "SOXLow", false),
				new Delta("sc0-sl1", 0, -1, "SOXClose", "SOXLow", false),
				new Delta("sc0-sl2", 0, -2, "SOXClose", "SOXLow", false),
				new Delta("sc0-sc1", 0, -1, "SOXClose", "SOXClose", false),
				new Delta("sc0-sc1", 0, -2, "SOXClose", "SOXClose", false)
				// SOX
		};

		for (ModuleFactory m : modules)
			if (Fields.nameToColumn(m.NAME) == null)
				Fields.addColumn(m.NAME);

		LinkedList<Runnable> runnables = new LinkedList<Runnable>();
		for (final String code : Fields.stockList) {
			runnables.add(new Runnable() {
				@Override
				public void run() {
					for (ModuleFactory m : modules) {
						for (int i = 0; i < DB.get(code).getRowCount(); i++)
							DB.get(code).set(i, m.NAME, m.calculate(code, i));
						System.out.print("*");
					}
				}
			});
		}
//		Utility.runInThreadPool(runnables,4);
		for (Runnable r : runnables)
			try {
				r.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		DB.save();
	}
}
