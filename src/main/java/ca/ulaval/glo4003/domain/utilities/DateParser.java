package ca.ulaval.glo4003.domain.utilities;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateParser {
	DateTimeFormatter format;

	public DateParser() {
		format = DateTimeFormat.forPattern(Constants.LONG_DATE_TIME_FORMAT_FR);
	}

	public DateParser(String formatString) {
		format = DateTimeFormat.forPattern(formatString);
	}

	public DateTime parseDate(String dateToParse) {
		return DateTime.parse(dateToParse, format);
	}
}
