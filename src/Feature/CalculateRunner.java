package Feature;
import Data.DB;
import Data.Fields;
import Feature.Modules.Delta;

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
				new Delta("IXICClose-", 0, -1, "IXICClose", "IXICClose", false),
				new Delta("GSPCClose-", 0, -1, "GSPCClose", "GSPCClose", false),
				new Delta("SOXClose-", 0,  -1, "SOXClose", "SOXClose", false),

				new Delta("IXICOpen-", 1, 0, "IXICOpen", "IXICOpen", false),
				new Delta("GSPCOpen-", 1, 0, "GSPCOpen", "GSPCOpen", false),
				new Delta("SOXOpen-",  1, 0, "SOXOpen", "SOXOpen", false),
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
