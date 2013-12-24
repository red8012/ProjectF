package Feature;
import Data.DB;
import Data.Fields;
import Feature.Modules.MovingAverage;
import ProjectF.Utility;

import java.util.LinkedList;

public class CalculateRunner implements Runnable {
	public CalculateRunner() {
	}

	@Override
	public void run() {
		Fields.load();
		Fields.inStockList("2330"); // load stock list
		DB.load();

		final ModuleFactory modules[] = {
				new MovingAverage("MA(c)", 3, Fields.nameToColumn("close")),
				new MovingAverage("MA(all)", 2, Fields.nameToColumn("open"), Fields.nameToColumn("high"),
						Fields.nameToColumn("low"), Fields.nameToColumn("close"))
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
		Utility.runInThreadPool(runnables);
		Fields.save();
		DB.save();
	}
}
