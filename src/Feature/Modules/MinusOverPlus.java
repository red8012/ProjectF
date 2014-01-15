package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;

public class MinusOverPlus extends ModuleFactory {
	final String pos, neg;

	public MinusOverPlus(String pos, String neg) {
		super(pos + "-" + neg);
		this.pos = pos;
		this.neg = neg;
	}

	@Override
	public Double calculate(String code, int row) {
		try {
			Double p = DB.get(code, row, pos), n = DB.get(code, row, neg);
			return (p - n) / (p + n);
		} catch (Exception e) {
		}
		return null;
	}
}
