package Parser;
import Data.DB;
import Data.Fields;
import ProjectF.CalendarIterator;
import ProjectF.Utility;

import java.util.LinkedList;

public class ParserRunner implements Runnable {
	final String startDate, endDate,
			priceUrl = "http://www.twse.com.tw/ch/trading/exchange/MI_INDEX/MI_INDEX3_print.php?genpage=genpage/Report[yyyy][mm]/A112[yyyy][mm][dd]ALLBUT0999_1.php&type=csv",
			threeBigUrl = "http://www.twse.com.tw/ch/trading/fund/T86/print.php?edition=ch&filename=genpage/[yyyy][mm]/[yyyy][mm][dd]_2by_stkno.dat&type=csv&select2=ALLBUT0999",
			borrowUrl = "http://www.twse.com.tw/ch/trading/exchange/MI_MARGN/MI_MARGN3_2.php?select2=&input_date=[yyyy]/[mm]/[dd]&type=csv";
	final String[] priceColumnNames = {"成交股數", "成交筆數", "volume", "open", "high", "low", "close"},
			threeBigColumnNames = {"外資買進股數", "外資賣出股數", "三大法人買賣超股數"},
			borrowColumnNames = {"融資買進", "融資賣出", "融資餘額", "融券買進", "融券賣出", "融券餘額", "限額"};
	final int[] priceColumns = {2, 3, 4, 5, 6, 7, 8},
			threeBigColumns = {2, 3, 8},
			borrowColumns = {2, 3, 6, 8, 9, 12, 7};

	final boolean rebuild;

	public ParserRunner(String startDate, String endDate, boolean rebuild) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.rebuild = rebuild;
	}

	@Override
	public void run() {
		if (rebuild) {
			Fields.reset();
			DB.reset();
			Fields.addColumn(priceColumnNames);
			Fields.addColumn(threeBigColumnNames);
			Fields.addColumn(borrowColumnNames);
			Fields.inStockList("2330"); // load stock list
			for (String code : Fields.stockList) DB.insertStock(code);
		} else {
			DB.load();
			Fields.load();
		}
		System.out.println("\nParsing price...");
		parsePrice();
		System.out.println("\nParsing three big...");
		parseThreeBig();
		System.out.println("\nParsing borrow...");
		parseBorrow();
		DB.save();
		Fields.save();
	}

	public void parsePrice() {
		LinkedList<Runnable> parsers = new LinkedList<Runnable>();
		for (String date : new CalendarIterator(startDate, endDate)) {
			ParserFactory p = new ParserFactory(priceUrl, date);
			p.skipLines = 120;
			p.columns = priceColumns;
			p.columnNames = priceColumnNames;
			p.shouldAddNewRow = true;
			parsers.add(p);
		}
		Utility.runInThreadPool(parsers);
	}

	public void parseThreeBig() {
		LinkedList<Runnable> parsers = new LinkedList<Runnable>();
		for (String date : new CalendarIterator(startDate, endDate)) {
			ParserFactory p = new ParserFactory(threeBigUrl, date);
			p.skipLines = 10;
			p.columns = threeBigColumns;
			p.columnNames = threeBigColumnNames;
			p.shouldAddNewRow = false;
			parsers.add(p);
		}
		Utility.runInThreadPool(parsers);
	}

	public void parseBorrow() {
		LinkedList<Runnable> parsers = new LinkedList<Runnable>();
		for (String date : new CalendarIterator(startDate, endDate)) {
			ParserFactory p = new ParserFactory(borrowUrl, date);
			p.skipLines = 9;
			p.columns = borrowColumns;
			p.columnNames = borrowColumnNames;
			p.shouldAddNewRow = false;
			parsers.add(p);
		}
		Utility.runInThreadPool(parsers);
	}
}
