package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

public class MovingAverage extends ModuleFactory {
	final int[] columns;
	final int period;

	public MovingAverage(String name, int period, int... columns) {
		super(name);
		this.period = period;
		this.columns = columns;
	}

	@Override
	public Double calculate(String code, int row) {
		double[] dou = new double[period * columns.length];
		try {
			int x = 0;
			for (int i = row - period + 1; i <= row; i++)
				for (int col : columns)
					dou[x++] = DB.get(code).get(i, col);
		} catch (ArrayIndexOutOfBoundsException ea) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
		return new Double(new Mean().evaluate(dou));
	}
}
