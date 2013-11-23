package ca.ulaval.glo4003.utilities.time;

import org.joda.time.DateTime;

public class DisplayDate extends DateTimeWrapper {

	private static final String format = "d MMMM yyyy à HH'h'mm z";

	public DisplayDate(DateTime date) {
		super(date, format);
	}

	public DisplayDate(String date) {
		super(date, format);
	}

}
