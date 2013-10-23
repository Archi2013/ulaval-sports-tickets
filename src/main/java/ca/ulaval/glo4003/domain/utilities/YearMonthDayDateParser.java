package ca.ulaval.glo4003.domain.utilities;

import org.springframework.stereotype.Component;

@Component
public class YearMonthDayDateParser extends DateParser {

	public YearMonthDayDateParser() {
		super("yyyy-MM-dd");
	}

}
