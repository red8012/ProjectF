package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;

public class Delta extends ModuleFactory {
	final int baseline, minus;
	final String column;
	final boolean twoClass;

	public Delta(String name, int baseline, int minus, String column, boolean twoClass) {
		super(name);
		this.baseline = baseline;
		this.minus = minus;
		this.column = column;
		this.twoClass = twoClass;
	}

	@Override
	public Double calculate(String code, int row) {
		try {
			double d1 = DB.get(code).get(row + baseline, column),
					d2 = DB.get(code).get(row + minus, column);
			if (!twoClass) return d1 - d2;
			if (d1 - d2 > 0) return 1.0;
			else return -1.0;
		} catch (IndexOutOfBoundsException ea) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
