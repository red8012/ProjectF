package Parser;
import Data.DB;
import ProjectF.Utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ThreeBigParser implements Runnable {
	String url = "http://www.twse.com.tw/ch/trading/fund/BFI82U/BFI82U_print.php?begin_date=[yyyy][mm][dd]&end_date=[yyyy][mm][dd]&report_type=day&language=ch&save=csv", date;
	public int skipLines = 2;
	public boolean shouldAddNewRow = false;

	public ThreeBigParser(String date) {
		this.date = date;
		String[] d = date.split("-");
		url = url.replace("[yyyy]", d[0]).replace("[mm]", d[1]).replace("[dd]", d[2]);
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
				System.out.println("   " + date);
				return;
			}
			String[] who = {"自營商", "投信", "外資及陸資", "合計"};
			while ((line = reader.readLine()) != null) {
				String[] split = Utility.removeUrusaiTokens(line).split(",");
				for (String w : who)
					if (split[0].equals(w)) {
						DB.get("0000").set(date, w + "b", new Double(split[1]));
						DB.get("0000").set(date, w + "s", new Double(split[2]));
					} else if (split[0].equals("外資")) {
						DB.get("0000").set(date, "外資及陸資b", new Double(split[1]));
						DB.get("0000").set(date, "外資及陸資s", new Double(split[2]));
					}
			}
			System.out.print("*");
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
