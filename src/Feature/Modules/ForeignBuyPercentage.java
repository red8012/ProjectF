package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;

public class ForeignBuyPercentage extends ModuleFactory {
	public ForeignBuyPercentage() {
		super("foreignBuyPercentage");
	}

	@Override
	public Double calculate(String code, int row) {
		try {
			double buy = DB.get(code, row, "外資買進股數"),
					sell = DB.get(code, row, "外資賣出股數");
			return (buy - sell) / (buy + sell);
		} catch (IndexOutOfBoundsException ea) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}
