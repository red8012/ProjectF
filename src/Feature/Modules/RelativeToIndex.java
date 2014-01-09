package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class RelativeToIndex extends ModuleFactory {
	final String target;

	public RelativeToIndex(String name, String target) {
		super(name);
		this.target = target;
	}

	@Override
	public Double calculate(String code, int row) {
		double stockResult = 0,
				indexResult = 0;
		try {
			String date = DB.get(code).dateList.get(row);
			int indexRow = DB.get("0000").getRowNumber(date);
			if (indexRow < 20 || row < 20) return null;
			double[] indexData = new double[20],
					stockData = new double[20];
			for (int i = 0; i < 20; i++) {
				indexData[i] = DB.get("0000").get(indexRow - i, target);
				stockData[i] = DB.get(code).get(row - i, target);
			}
			double indexStd = new StandardDeviation(false).evaluate(indexData),
					stockStd = new StandardDeviation(false).evaluate(stockData);
			if (indexStd == Double.NaN || stockStd == Double.NaN) return null;

			indexResult = (indexData[0] - indexData[1]) / indexStd;
			stockResult = (stockData[0] - stockData[1]) / stockStd;
		} catch (Exception e) {
			System.out.println(code + " - " + row);
			e.printStackTrace();
		}

		return stockResult - indexResult;
	}
}
