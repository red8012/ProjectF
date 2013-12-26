package Data;
import ProjectF.Utility;

import java.io.Serializable;
import java.util.HashMap;

public class DB implements Serializable {
	static HashMap<String, Stock> data = new HashMap<String, Stock>();

	public static void save() {
		Utility.writeObject("Database.sav", data);
		Fields.save();
	}

	public static void load() {
		data = (HashMap<String, Stock>) Utility.readObject("Database.sav", 1)[0];
		Fields.inStockList("2330");
		Fields.load();
	}

	public static void reset(){
		data = new HashMap<String, Stock>();
	}

	public static void insertStock(String code) {
		data.put(code, new Stock(code));
	}

	public static Stock get(String code) {
		return data.get(code);
	}

	public static Double get(String code, int row, String column) {
		return data.get(code).get(row, column);
	}

	public static void set(String code, int row, String column, Double value) {
		data.get(code).set(row, column, value);
	}
}
