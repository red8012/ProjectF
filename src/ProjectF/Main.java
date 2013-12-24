package ProjectF;
import Data.DB;
import Data.Fields;
import Parser.ParserRunner;

public class Main {

	public static void main(String[] args) {
//		DB.insertStock("2330");
//		Fields.addColumn("open", "high");
//		DB.get("2330").addRow("2013-12-01");
//		DB.set("2330", 0, "open", 100.0);
////	    DB.set("2330", 0, "high", 200.0);
//		DB.get("2330").print(0);
//		Fields.save();
//		DB.save();

//		Fields.load();
//		DB.load();
//		DB.get("2330").print(0);

//		new ParserRunner("2013-12-02","2013-12-03", true).run();
		DB.load();
		Fields.load();
//		DB.get("2330").set("2013-11-04", "外資買進股數", 7566000.0);
//		DB.get("2330").print("2013-11-04");
	}
}
