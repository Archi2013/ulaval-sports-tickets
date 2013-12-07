package ca.ulaval.glo4003.utilities.time;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UrlDateTimeFactoryTest {
	
	private static final DateTime DATE = DateTime.parse("1998-02-28T16:30:00.000-05:00");
	
	private static final String DATE_STRING = "1998-02-28--16-30-EST";
	
	@InjectMocks
	private UrlDateTimeFactory urlDateTimeFactory;
	
	@Test
	public void create_DateTime_return_the_good_urlDateTime() {
		UrlDateTime urlDateTime = urlDateTimeFactory.create(DATE);
		
		assertEquals(DATE, urlDateTime.getDateTime());
	}

	@Test
	public void create_String_return_the_good_urlDateTime() {
		UrlDateTime urlDateTime = urlDateTimeFactory.create(DATE_STRING);
		
		assertEquals(DATE.toString(), urlDateTime.getDateTime().toString());
	}

}
