package Data;

import ProjectF.Utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Fields implements Serializable {
	static HashMap<String, Integer>
//			rowMap = new HashMap<String, Integer>(),
			columnMap = new HashMap<String, Integer>();
	static ArrayList<String>
//			dateList = new ArrayList<String>(),
			columnHeaderList = new ArrayList<String>();
	public static HashSet<String> stockList;

	public static boolean inStockList(String code) {
		try {
			return stockList.contains(code);
		} catch (NullPointerException e) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader("stockList.txt"));
				stockList = new HashSet<String>();
				String line;
				while ((line = reader.readLine()) != null)
					stockList.add(line.trim());
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return stockList.contains(code);
	}

//	public static String rowToDate(int row) {
//		return dateList.get(row);
//	}
//
//	public static Integer dateToRow(String date) {
//		return rowMap.get(date);
//	}
//
//	static void addRow(String date) {
//		dateList.add(date);
//		rowMap.put(date, dateList.size() - 1);
//	}

	public static Integer nameToColumn(String columnName) {
		return columnMap.get(columnName);
	}

	public static String columnToName(int column) {
		return columnHeaderList.get(column);
	}

	public static void addColumn(String... columnNames) {
		for (String c : columnNames) {
			columnHeaderList.add(c);
			columnMap.put(c, columnHeaderList.size() - 1);
		}
	}

	public static void save() {
		Utility.writeObject("Fields.sav",
				columnMap,
				columnHeaderList);
	}

	public static void load() {
		Object[] o = Utility.readObject("Fields.sav", 2);
		columnMap = (HashMap<String, Integer>) o[0];
		columnHeaderList = (ArrayList<String>) o[1];
	}

	public static void reset() {
		columnMap = new HashMap<String, Integer>();
		columnHeaderList = new ArrayList<String>();
	}
}
