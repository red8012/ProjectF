package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class BollingerNormalizer extends ModuleFactory {
	final int period;
	final String column;

	public BollingerNormalizer(String name, int period, String column) {
		super(name);
		this.period = period;
		this.column = column;
	}

	@Override
	public Double calculate(String code, int row) {

		double[] data = new double[period];
		try {
			for (int i = 0; i < period; i++) data[i] = DB.get(code).get(row - i, column);
			double end = DB.get(code).get(row, column);
			double std = new StandardDeviation(false).evaluate(data);
			double avg = new Mean().evaluate(data);
			if (std==Double.NaN||avg==Double.NaN)return null;
			return new Double((end - avg) / std);
		} catch (IndexOutOfBoundsException ea) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
