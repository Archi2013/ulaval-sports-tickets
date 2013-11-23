package ca.ulaval.glo4003.utilities.time;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeWrapperTest {
	public static final String A_FORMAT = "d MMMM yyyy à HH'h'mm z";

	private DateTime dateTime;

	private DateTimeWrapper dateTimeWrapper;

	@Before
	public void setUp() {
		dateTime = new DateTime();
		dateTimeWrapper = new DisplayDate(dateTime);
	}

	@Test
	public void toString_return_the_DateTime_string_in_the_correct_format() {
		String stringReturned = dateTimeWrapper.toString();

		Assert.assertEquals(dateTime.toString(A_FORMAT), stringReturned);
	}

	@Test
	public void getDate_returns_the_DateTime() {
		DateTime dateReturned = dateTimeWrapper.getDateTime();

		Assert.assertSame(dateTime, dateReturned);

	}
}