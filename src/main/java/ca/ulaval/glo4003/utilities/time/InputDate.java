package ca.ulaval.glo4003.utilities.time;

import org.joda.time.DateTime;

public class InputDate extends DateTimeWrapper {

	private static final String format = "yyyy-MM-dd HH:mm";

	public InputDate(DateTime date) {
		super(date, format);
	}

	public InputDate(String date) {
		super(date, format);
	}

}
