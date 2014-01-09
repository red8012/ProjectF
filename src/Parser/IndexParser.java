package Parser;
import Data.DB;
import ProjectF.Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IndexParser extends ParserFactory {
	final static String TABLE_STRING = "<table width=590 border=0 align=center cellpadding=0 cellspacing=1 class=board_trad>";

	public IndexParser(String date) {
		super("http://www.twse.com.tw/ch/trading/indices/MI_5MINS_HIST/MI_5MINS_HIST.php?myear=[yy]&mmon=[mm]",
				date);

	}

	@Override
	public void run() {
		try {
			String minGuoYY = String.valueOf(Integer.parseInt(date.split("-")[0]) - 1911);
			URLConnection conn = new URL(url.replace("[yy]", minGuoYY).replace("[mm]", date.split("-")[1])).openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "big5"));
			String line;
			while ((line = reader.readLine()) != null)
				if (line.startsWith(TABLE_STRING)) break;
			line = line.substring(line.indexOf("<tr height=20 bgcolor=#FFFFFF class=gray12>"));
			String[] split = line.split("</tr>");
			for (String s : split)
				if (s.length() > 10) {
					s = s.replaceAll("<[^>]*>", "").trim()
							.replaceAll(",", "").replaceAll(" ", ",").replaceAll("/", "-")
							.replace(minGuoYY + "-", date.substring(0,5));
					String[] result = s.split(",");
					if (shouldAddNewRow && DB.get("0000").getRowNumber(result[0]) != -1) {
						System.err.println("\nWarning: date duplicate -> " + result[0]);
						shouldAddNewRow = false;
					}
					if (shouldAddNewRow)
						DB.get("0000").addRow(result[0]);
					DB.get("0000").set(result[0], "open", Double.parseDouble(result[1]));
					DB.get("0000").set(result[0], "high", Double.parseDouble(result[2]));
					DB.get("0000").set(result[0],"low",Double.parseDouble(result[3]));
					DB.get("0000").set(result[0],"close",Double.parseDouble(result[4]));
				}
			System.out.print("*");
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
