package ca.ulaval.glo4003.utilities.time;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeWrapper {

	private String format;
	private DateTime date;
	private Locale locale = Locale.FRENCH;

	public DateTimeWrapper(DateTime date, String format) {
		this.date = date;
		this.format = format;
	}

	public DateTimeWrapper(String date, String format) {
		this.format = format;
		this.date = DateTime.parse(date, DateTimeFormat.forPattern(format).withLocale(locale));
	}

	@Override
	public String toString() {
		DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern(format).withLocale(locale);
		return dateTimeFormat.print(date);

	}

	public DateTime getDateTime() {
		return date;
	}

}
