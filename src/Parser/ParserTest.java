package Parser;
import Data.DB;
import Data.Fields;
import junit.framework.TestCase;
import org.junit.Test;

public class ParserTest extends TestCase {
	@Test
	public void testRun() throws Exception {
		new ParserRunner("2013-09-20", "2013-11-07", true).run();
		Fields.reset();
		DB.reset();
		Fields.load();
		DB.load();

		assertEquals(DB.get("2330").get("2013-11-04", "成交股數"), 9642456.0);
		assertEquals(DB.get("1101").get("2013-11-04", "volume"), 222392757.0);
		assertEquals(DB.get("9945").get("2013-11-04", "成交筆數"), 1331.0);
		assertEquals(DB.get("2498").get("2013-11-04", "open"), 147.0);
		assertEquals(DB.get("2498").get("2013-11-04", "外資賣出股數"), 1720694.0);
		assertEquals(DB.get("1101").get("2013-11-04", "三大法人買賣超股數"), -963000.0);
		assertEquals(DB.get("9945").get("2013-11-04", "融資買進"), 420.0);
		assertEquals(DB.get("1101").get("2013-11-04", "限額"), 923043.0);

		assertEquals(DB.get("2498").get("2013-09-23", "融券餘額"), 14694.0);
		assertEquals(DB.get("2498").get("2013-10-01", "外資買進股數"), 2632000.0);

		new ParserRunner("2013-11-08", "2013-11-08", false).run();
		assertEquals(DB.get("1101").get("2013-11-08", "三大法人買賣超股數"), 938786.0);
		assertEquals(DB.get("2498").get("2013-11-08", "high"), 155.50);
		assertEquals(DB.get("9945").get("2013-11-08", "融券買進"), 121.0);
	}
}
