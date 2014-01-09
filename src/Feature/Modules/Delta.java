package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;

public class Delta extends ModuleFactory {
	final int baseline, minus;
	final String columnOfBaseline, columnOfMinus;
	final boolean twoClass;

	public Delta(String name, int baseline, int minus, String columnOfBaseline, String columnOfMinus, boolean twoClass) {
		super(name);
		this.baseline = baseline;
		this.minus = minus;
		this.columnOfBaseline = columnOfBaseline;
		this.columnOfMinus = columnOfMinus;
		this.twoClass = twoClass;
	}

	@Override
	public Double calculate(String code, int row) {
		try {
			double d1 = DB.get(code).get(row + baseline, columnOfBaseline),
					d2 = DB.get(code).get(row + minus, columnOfMinus);
			if (!twoClass) return (d1 - d2) / d1;
			if (d1 - d2 > 0) return 1.0;
			else return -1.0;
		} catch (IndexOutOfBoundsException ea) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
