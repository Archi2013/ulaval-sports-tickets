package ca.ulaval.glo4003.domain.utilities;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DisplayedPeriodMapperPropertiesFileTest {
	private static final String PERIOD_NAME = "aujourd'hui";
	private static final Integer NUMBER_OF_DAYS = 1;
	private static final String INVALID_PERIOD_NAME = "demi-seconde";
	private static final Integer INVALID_NUMBER_OF_DAYS = 18;
	private static final String OTHER_PERIOD_NAME = "une semaine";
	private static final Integer OTHER_NUMBER_OF_DAYS = 7;

	@Mock
	Properties properties;
	
	@InjectMocks
	DisplayedPeriodMapperPropertiesFile displayedPeriodMapper;
	
	Set<Object> keySet;
	
	@Before
	public void setUp() {
		keySet = new HashSet<>();
		keySet.add(NUMBER_OF_DAYS);
		keySet.add(OTHER_NUMBER_OF_DAYS);
	}
	
	@Test
	public void with_a_existing_period_getNumberOfDays_should_return_the_numberOfDays() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		Integer numberOfDays = displayedPeriodMapper.getNumberOfDays(PERIOD_NAME);
		
		assertEquals(numberOfDays, NUMBER_OF_DAYS);
	}
	
	@Test
	public void with_a_existing_period_getName_should_return_the_name() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.isEmpty()).thenReturn(true);
		
		String sportName = displayedPeriodMapper.getName(NUMBER_OF_DAYS);
		
		assertEquals(sportName, PERIOD_NAME);
	}
	
	@Test(expected = DisplayedPeriodDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_period_name_getNumberOfDays_shoudl_raise_a_DisplayedPeriodDoesntExistInPropertiesFileException() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		displayedPeriodMapper.getNumberOfDays(INVALID_PERIOD_NAME);
	}
	
	@Test(expected = DisplayedPeriodDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_numberOfDays_getName_should_raise_a_DisplayedPeriodDoesntExistInPropertiesFileException() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.getProperty(INVALID_NUMBER_OF_DAYS.toString())).thenReturn(null);
		when(properties.isEmpty()).thenReturn(true);
		
		displayedPeriodMapper.getName(INVALID_NUMBER_OF_DAYS);
	}
	
	@Test
	public void when_second_use_getNumberOfDays_should_return_the_numberOfDays() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(false);
		
		Integer numberOfDays = displayedPeriodMapper.getNumberOfDays(PERIOD_NAME);
		assertEquals(numberOfDays, NUMBER_OF_DAYS);
	}
	
	@Test
	public void when_second_use_getName_should_return_the_name() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.isEmpty()).thenReturn(false);
		
		String name = displayedPeriodMapper.getName(NUMBER_OF_DAYS);
		assertEquals(name, PERIOD_NAME);
	}
	
	@Test(expected = RuntimeException.class)
	public void when_propertie_file_doesnt_exist_getNumberOfDays_shoudl_raise_a_RuntimeException() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());
		
		displayedPeriodMapper.getNumberOfDays(PERIOD_NAME);
	}
	
	@Test(expected = RuntimeException.class)
	public void when_propertie_file_doesnt_exist_getName_should_raise_a_RuntimeException() throws RuntimeException, DisplayedPeriodDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());
		
		displayedPeriodMapper.getName(NUMBER_OF_DAYS);
	}
	
	@Test
	public void getAllNames_should_return_all_the_names() {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		List<String> list = displayedPeriodMapper.getAllNames();
		
		List<String> expecteds = new ArrayList<>();
		expecteds.add(PERIOD_NAME);
		expecteds.add(OTHER_PERIOD_NAME);
		
		assertEquals(expecteds, list);
	}
	
	@Test
	public void getAllNumberOfDays_should_return_all_the_number_of_days() {
		when(properties.getProperty(NUMBER_OF_DAYS.toString())).thenReturn(PERIOD_NAME);
		when(properties.getProperty(OTHER_NUMBER_OF_DAYS.toString())).thenReturn(OTHER_PERIOD_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		List<Integer> list = displayedPeriodMapper.getAllNumberOfDays();
		
		List<Integer> expecteds = new ArrayList<>();
		expecteds.add(NUMBER_OF_DAYS);
		expecteds.add(OTHER_NUMBER_OF_DAYS);
		
		assertEquals(expecteds, list);
	}
}
