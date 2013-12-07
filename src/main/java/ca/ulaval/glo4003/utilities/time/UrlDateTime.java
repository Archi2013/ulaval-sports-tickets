package ca.ulaval.glo4003.utilities.time;

import org.joda.time.DateTime;

public class UrlDateTime extends DateTimeWrapper {

	private static final String format = "yyyy-MM-dd--HH-mm-z";

	public UrlDateTime(DateTime date) {
		super(date, format);
	}

	public UrlDateTime(String date) {
		super(date, format);
	}

}
