package ca.ulaval.glo4003.utilities.time;

import org.springframework.stereotype.Component;

@Component
public class YearMonthDayHourMinuteDateParser extends DateParser {

	public YearMonthDayHourMinuteDateParser() {
		super("yyyy-MM-dd HH:mm");
	}

}
