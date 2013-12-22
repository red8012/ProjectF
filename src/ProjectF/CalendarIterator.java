package ProjectF;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class CalendarIterator implements Iterable<String> {
	final Calendar start, current, end;
	final boolean outputIsXiYuan;

	public CalendarIterator(boolean outputIsXiYuan, int startY, int startM, int startD, int endY, int endM, int endD) {
		this.outputIsXiYuan = outputIsXiYuan;
		start = new GregorianCalendar(startY, startM - 1, startD, 0, 0, 0);
		current = new GregorianCalendar(startY, startM - 1, startD, 0, 0, 0);
		end = new GregorianCalendar(endY, endM - 1, endD, 0, 0, 0);
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			@Override
			public boolean hasNext() {
				return current.compareTo(end) <= 0;
			}

			@Override
			public String next() {
				return new StringBuilder(outputIsXiYuan ?
						current.get(Calendar.YEAR) : current.get(Calendar.YEAR) - 1911).append("-")
						.append(current.get(Calendar.MONTH) + 1).append("-")
						.append(current.get(Calendar.DAY_OF_MONTH)).toString();
			}

			@Override
			public void remove() {

			}
		};
	}
}
