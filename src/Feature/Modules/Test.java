package Feature.Modules;
import Data.DB;
import Feature.ModuleFactory;

public class Test extends ModuleFactory {
	final String type;

	public Test(String type) {
		super(type);
		this.type = type;
	}

	@Override
	public Double calculate(String code, int row) {
		Double o = DB.get(code, row, "open");
		if (o == null || o.isNaN()) return null;

		if (type.equals("(c-o)/o"))
			return (DB.get(code, row, "close") - o) / o * 10;
		else if (type.equals("(h-o)/o"))
			return (DB.get(code, row, "high") - o) / o * 10;
		else if (type.equals("(l-o)/o"))
			return (DB.get(code, row, "low") - o) / o * 10;
		else if (type.equals("(c-l)/(h-l)"))
			return (DB.get(code, row, "close") - DB.get(code, row, "low")) /
					(DB.get(code, row, "high") - DB.get(code, row, "low"));
		else if (type.equals("0000(c-o)/o")) {
			String date = DB.get(code).dateList.get(row);
			int row0000 = DB.get("0000").getRowNumber(date) + 1;
			if (row0000 >= DB.get("0000").getRowCount()) return null;
			Double open0 = DB.get("0000").get(row0000, "open"),
					close0 = DB.get("0000").get(row0000, "close");
			return (close0 - open0) / open0;
		} else System.err.println(("wrong type."));
		return null;
	}
}
