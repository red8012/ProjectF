package ProjectF;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class CalendarIterator implements Iterable<String> {
	final Calendar start, current, end;
	final boolean outputIsXiYuan = true;

	public CalendarIterator(int startY, int startM, int startD, int endY, int endM, int endD) {
//		this.outputIsXiYuan = outputIsXiYuan;
		start = new GregorianCalendar(startY, startM - 1, startD, 0, 0, 0);
		current = (GregorianCalendar) start.clone();
		current.add(Calendar.DAY_OF_MONTH, -1);
		end = new GregorianCalendar(endY, endM - 1, endD, 0, 0, 0);
	}

	public CalendarIterator(String startDate, String endDate) {
//		this.outputIsXiYuan = outputIsXiYuan;
		String[] s = startDate.split("-"), e = endDate.split("-");
		int startY = Integer.parseInt(s[0]),
				startM = Integer.parseInt(s[1]),
				startD = Integer.parseInt(s[2]),
				endY = Integer.parseInt(e[0]),
				endM = Integer.parseInt(e[1]),
				endD = Integer.parseInt(e[2]);
		start = new GregorianCalendar(startY, startM - 1, startD, 0, 0, 0);
		current = (GregorianCalendar) start.clone();
		current.add(Calendar.DAY_OF_MONTH, -1);
		end = new GregorianCalendar(endY, endM - 1, endD, 0, 0, 0);
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			@Override
			public boolean hasNext() {
				current.add(Calendar.DAY_OF_MONTH, 1);
				return current.compareTo(end) <= 0;
			}

			@Override
			public String next() {
				String month = String.format("%02d", current.get(Calendar.MONTH) + 1),
						date = String.format("%02d", current.get(Calendar.DAY_OF_MONTH));
				return new StringBuilder().append(current.get(Calendar.YEAR))
						.append("-").append(month)
						.append("-").append(date).toString();
			}

			@Override
			public void remove() {

			}
		};
	}
}
