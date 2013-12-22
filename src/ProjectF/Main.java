package ProjectF;
import Data.DB;
import Data.Fields;

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

		Fields.load();
		DB.load();
		DB.get("2330").print(0);
	}
}
