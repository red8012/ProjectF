package Parser;
import Data.DB;
import Data.Fields;
import ProjectF.CalendarIterator;
import ProjectF.Utility;

import java.util.LinkedList;

public class ParserRunner implements Runnable {
	final String startDate, endDate,
			priceUrl = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX3_print.php?genpage=genpage/Report[yyyy][mm]/A112[yyyy][mm][dd]ALLBUT0999_1.php&type=csv";
	final boolean rebuild;

	public ParserRunner(String startDate, String endDate, boolean rebuild) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.rebuild = rebuild;
	}

	@Override
	public void run() {
		// parse price
		int[] columns = new int[]{2, 3, 4, 5, 6, 7, 8};
		String[] columnNames = new String[]{"成交股數", "成交筆數", "volume", "open", "high", "low", "close"};
		if (rebuild) {
			Fields.addColumn(columnNames);
			Fields.inStockList("2330"); // load stock list
			for (String code: Fields.stockList) DB.insertStock(code);
		} else {
			DB.load();
			Fields.load();
		}

		LinkedList<Runnable> parsers = new LinkedList<Runnable>();
		for (String date : new CalendarIterator(startDate, endDate)) {
			ParserFactory p = new ParserFactory(priceUrl, date);
			p.skipLines = 130;
			p.columns = columns;
			p.columnNames = columnNames;
			p.shouldAddNewRow = true;
			parsers.add(p);
		}
		Utility.runInThreadPool(parsers);

		DB.save();
		Fields.save();
	}
}
