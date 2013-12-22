package Data;
import junit.framework.TestCase;
import org.junit.Test;

public class UnitTest extends TestCase {
	@Test
	public void testFields() throws Exception {
//		Fields.addRow("a");
//		Fields.addRow("b");
//		Fields.addRow("c");
		Fields.addColumn("open");
		Fields.addColumn("high");

		Fields.save();
//		Fields.rowMap = null;
		Fields.columnMap = null;
		Fields.columnHeaderList = null;
//		Fields.dateList = null;
		Fields.load();

//		assertTrue(Fields.rowToDate(1).equals("b"));
//		assertTrue(Fields.dateToRow("c") == 2);
		assertTrue(Fields.columnToName(0).equals("open"));
		assertTrue(Fields.nameToColumn("high") == 1);
	}

	@Test
	public void testStock() throws Exception {
		Stock stock = new Stock("2330");
//		stock.set(10,20,30.0);
	}
}
