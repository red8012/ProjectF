package Parser;
import Data.DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class YahooIndexParser implements Runnable {
	String YAHOO = "http://ichart.yahoo.com/table.csv?s=[target]" +
			"&a=[MM-1]&b=[DD]&c=[YYYY]&d=[mm-1]&e=[dd]&f=[yyyy]&g=d&ignore=.csv",
			NASDAQ = "%5EIXIC", SNP = "%5EGSPC", PHLX = "%5ESOX";

	public YahooIndexParser(String fromDate, String toDate) {
		String[] from = fromDate.split("-"), to = toDate.split("-");
		YAHOO = YAHOO.replace("[MM-1]", String.valueOf(Integer.parseInt(from[1]) - 1))
				.replace("[DD]", from[2]).replace("[YYYY]", from[0])
				.replace("[mm-1]", String.valueOf(Integer.parseInt(to[1]) - 1))
				.replace("[dd]", to[2]).replace("[yyyy]", to[0]);
	}

	@Override
	public void run() {
		final String[] TARGETS = {NASDAQ, SNP, PHLX};
		try {
			for (String target : TARGETS) {
				URLConnection conn = new URL(YAHOO.replace("[target]", target)).openConnection();
				BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = reader.readLine();
				while ((line = reader.readLine()) != null) {
					String[] split = line.split(",");
					String date = split[0];
					Double open = new Double(split[1]), high = new Double(split[2]),
							low = new Double(split[3]), close = new Double(split[4]);
					if (DB.get("0000").getRowNumber(date) >= 0) {
						DB.get("0000").set(date, target.substring(3) + "Open", open);
						DB.get("0000").set(date, target.substring(3) + "High", high);
						DB.get("0000").set(date, target.substring(3) + "Low", low);
						DB.get("0000").set(date, target.substring(3) + "Close", close);
					}
				}
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
