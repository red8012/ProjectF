package Simulator;
import Data.DB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Simulator {
	final String label, result;
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<Double> pos = new ArrayList<Double>(), neg = new ArrayList<Double>();
	double asset = 0;
	final double thresholdP, thresholdN, down;

	public Simulator(String label, String result, double thresholdP, double thresholdN, double down) {
		this.label = label;
		this.result = result;
		this.thresholdP = thresholdP;
		this.thresholdN = thresholdN;
		this.down = down;
	}

	public double run() {
		String s;
		double sumDelta = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(label));
			while ((s = reader.readLine()) != null)
				dates.add(s);
			reader.close();
			reader = new BufferedReader(new FileReader(result));
			for (int i = 0; i < 4; i++) reader.readLine();
			while ((s = reader.readLine()) != null) {
				String[] split = s.split(" ");
				pos.add(new Double(split[2]));
				neg.add(new Double(split[3]));
			}
			reader.close();
//			DB.load();
			for (int i = 0; i < dates.size() - 2; i++) {
				double p = pos.get(i), n = neg.get(i);
				double delta = (p - n) / (p + n);
				sumDelta += delta;
				int row = DB.get("0000").getRowNumber(dates.get(i));
				double c0 = DB.get("0000").get(row, "close"),
						o1 = DB.get("0000").get(row + 1, "open"),
						l1 = DB.get("0000").get(row + 1, "low"),
						h1 = DB.get("0000").get(row + 1, "high"),
						c1 = DB.get("0000").get(row + 1, "close"),
						o2 = DB.get("0000").get(row + 2, "open");
				double earn = 0;
				if (delta > thresholdP && !(delta < thresholdN)) {
//					if (l1 < c0)
						earn = o2 - o1;
				} else if (delta < thresholdN && !(delta > thresholdP)) {
//					if (h1 > c0)
						earn = o1 - o2;
				}
				asset += earn;
				System.out.println(dates.get(i + 1) + "," + asset);
			}
			System.out.println("asset: " + asset);
//			System.out.println(sumDelta / (dates.size() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asset;
	}
}
