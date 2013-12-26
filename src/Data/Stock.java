package Data;
import java.io.Serializable;
import java.util.ArrayList;

public class Stock implements Serializable {
	final String code;
	public ArrayList<ArrayList<Double>> content = new ArrayList<ArrayList<Double>>(); // content[row][column]
	public ArrayList<String> dateList = new ArrayList<String>(); // row -> date

	public Stock(String code) {
		this.code = code;
	}

	public void addRow(String date) {
		dateList.add(date);
		content.add(new ArrayList<Double>(Fields.columnHeaderList.size()));
		for (int i = 0; i < Fields.columnHeaderList.size(); i++)
			content.get(content.size() - 1).add(null);
	}

	public void set(int row, int column, Double value) {
		int columnSize = content.get(row).size();
		if (column < Fields.columnHeaderList.size())
			while (columnSize++ <= column)
				content.get(row).add(null);
		content.get(row).set(column, value);
	}

	public void set(String date, String columnName, Double value) {
		set(dateList.indexOf(date), Fields.nameToColumn(columnName), value);
	}

	public void set(int row, String columnName, Double value) {
		set(row, Fields.nameToColumn(columnName), value);
	}

	public Double get(int row, int column) {
		return content.get(row).get(column);
	}

	public Double get(int row, String columnName) {
		return content.get(row).get(Fields.nameToColumn(columnName));
	}

	public Double get(String date, String columnName) {
		return content.get(dateList.indexOf(date)).get(Fields.nameToColumn(columnName));
	}

	public int getRowCount() {
		return content.size();
	}

	public int getRowNumber(String date){
		return dateList.indexOf(date);
	}

	public void print(int row) {
		System.out.println(code + " [ " + String.valueOf(row) + " ]  " + dateList.get(row));
		for (String s : Fields.columnHeaderList)
			System.out.println(s + ":\t" + get(row, s));
		System.out.println();
	}

	public void print(String date) {
		print(dateList.indexOf(date));
	}
}
