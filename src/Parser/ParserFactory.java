package Parser;
import Data.DB;
import Data.Fields;
import ProjectF.Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ParserFactory implements Runnable {
	final String url, date;
	public int skipLines = 0;
	public boolean shouldAddNewRow = false;
	public int[] columns = {};
	public String[] columnNames = {};

	public ParserFactory(String url, String date) {
		this.date = date;
		String[] d = date.split("-");
		url = url.replace("[yyyy]", d[0]).replace("[mm]", d[1]).replace("[dd]", d[2]);
		this.url = url;
		if (columns.length != columnNames.length) System.err.println("arranger column not matched!");
	}

	@Override
	public void run() {
		try {
			URLConnection conn = new URL(url).openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "big5"));
			String line = null;
			for (int i = 0; i < skipLines; i++) line = reader.readLine();
			if (line == null) {
				reader.close();
				System.out.print("   " + date);
				return;
			}
			while ((line = reader.readLine()) != null) {
				String[] split = line.split(",");
//				if (split[0].equals("2330") && date.equals("2013-11-04")) System.out.println("\n" + line);// debug
				try {
					if (split[0].length() != 4) continue;
					if (!Fields.inStockList(split[0])) continue;
					split = Utility.removeUrusaiTokens(line).split(",");
					Integer.parseInt(split[0]); // check legal 4 digit code
				} catch (NumberFormatException e) {
					continue;
				}
				if (shouldAddNewRow && DB.get(split[0]).getRowNumber(date) != -1) {
					System.err.println("\nWarning: date duplicate -> " + date);
					shouldAddNewRow = false;
				}
				if (shouldAddNewRow) {
					DB.get(split[0]).addRow(date);
					for (int i = 0; i < columns.length; i++)
						DB.set(split[0], DB.get(split[0]).getRowCount() - 1,
								columnNames[i], Utility.stringToDouble(split[columns[i]]));
				} else {
					for (int i = 0; i < columns.length; i++)
						DB.get(split[0]).set(date, columnNames[i], Utility.stringToDouble(split[columns[i]]));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
