package ca.ulaval.glo4003.domain.utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {
	private DateTimeFormatter format;

	public DateParser(String formatString) {
		format = DateTimeFormat.forPattern(formatString);
	}

	public DateTime parseDate(String dateToParse) {
		return DateTime.parse(dateToParse, format);
	}
}
