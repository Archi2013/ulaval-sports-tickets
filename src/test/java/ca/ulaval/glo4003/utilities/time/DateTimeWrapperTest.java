package ca.ulaval.glo4003.utilities.time;

import java.util.Locale;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeWrapperTest {
	private static final String A_DATE_ISO8601 = "1998-02-28T16:30:45.618-05:00";
	public static final String A_FORMAT = "d MMMM yyyy à HH'h'mm z";
	public static final String A_DATE = "28 février 1998 à 16h30 EST";

	private DateTime dateTime;

	private DateTimeWrapper dateTimeWrapper;

	@Before
	public void setUp() {
		dateTime = new DateTime(A_DATE_ISO8601);
		dateTimeWrapper = new DateTimeWrapper(dateTime, A_FORMAT);
	}

	@Test
	public void constructor_from_string_create_the_correct_date() {
		dateTimeWrapper = new DateTimeWrapper(A_DATE, A_FORMAT);

		Assert.assertEquals(A_DATE, dateTimeWrapper.toString());
	}

	@Test
	public void toString_return_the_DateTime_string_in_the_correct_format() {
		String stringReturned = dateTimeWrapper.toString();
		
		Assert.assertEquals(A_DATE, stringReturned);
	}

	@Test
	public void getDate_returns_the_DateTime() {
		DateTime dateReturned = dateTimeWrapper.getDateTime();

		Assert.assertSame(dateTime, dateReturned);

	}
}