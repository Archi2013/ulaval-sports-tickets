package ca.ulaval.glo4003.utilities.time;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeWrapper {

	private String format;
	private DateTime date;

	public DateTimeWrapper(DateTime date, String format) {
		this.date = date;
		this.format = format;
	}

	public DateTimeWrapper(String date, String format) {
		this.format = format;
		this.date = DateTime.parse(date, DateTimeFormat.forPattern(format));
	}

	@Override
	public String toString() {
		return date.toString(format);

	}

	public DateTime getDateTime() {
		return date;
	}

}
