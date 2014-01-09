package ProjectF;
import Data.DB;
import Data.Fields;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class TextWriter implements Runnable {
	final int CSV = 0, SVM = 1, type;
	final boolean isClassification, debug;
	final String fileName, startDate, endDate, answerColumnName;
	final String[] columnNames;
	BufferedWriter writer;
	StringBuffer buffer;
	int positive = 0, negative = 0;

	public TextWriter(String fileName, String startDate, String endDate, String type, boolean isClassification, boolean debug, String answerColumnName, String... columnNames) throws Exception {
		this.fileName = fileName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isClassification = isClassification;
		this.debug = debug;
		if (type.equals("csv")) this.type = CSV;
		else if (type.equals("svm")) this.type = SVM;
		else throw new Exception("wrong type");
		this.answerColumnName = answerColumnName;
		this.columnNames = columnNames;
	}

	@Override
	public void run() {
		try {
			DB.load();
			writer = new BufferedWriter(new FileWriter(fileName));
			if (type == CSV) {
				buffer = new StringBuffer();
				if (debug) buffer.append("date,code,");
				buffer.append(answerColumnName);
				for (String s : columnNames) buffer.append(",").append(s);
				writer.write(buffer.toString());
				writer.newLine();
			}
			for (String date : new CalendarIterator(startDate, endDate, Calendar.DAY_OF_MONTH)) {
				for (String code : Fields.stockList) {
					try {
						Double answer = DB.get(code).get(date, answerColumnName);
						if (answer == null) continue;
						boolean shouldSkip = false;
						if (debug) buffer = new StringBuffer(date + "," + code + ",");
						else buffer = new StringBuffer();
						buffer.append(String.format("%.0f", answer))
								.append(type == CSV ? "," : "\t");
						for (int i = 0; i < columnNames.length; i++) {
							if (type == SVM) buffer.append(i + 1).append(":");
							Double d = DB.get(code).get(date, columnNames[i]);
							if (d == null || d.isNaN()) {
								shouldSkip = true;
								break;
							}
							buffer.append(String.format("%.7f", d)).append(type == CSV ? "," : "\t");
						}
						if (shouldSkip) continue;
						writer.write(buffer.toString());
						writer.newLine();
						if (answer > 0) positive++;
						else negative++;
					} catch (Exception e) {
						System.err.print(".");
					}
				}
			}
			writer.close();
			System.out.println(new StringBuffer("\nPositive: ").append(positive)
					.append("\t Negative: ").append(negative));
			System.out.println(fileName);
			System.out.print((double) positive / (positive + negative) * 100);
			System.out.println(" %");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}