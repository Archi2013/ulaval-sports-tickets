package ca.ulaval.glo4003.controllers;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.data_access.SportDao;

@RunWith(MockitoJUnitRunner.class)
public class SportsControllerTest {

	@Mock
	private SportDao dao;

	@InjectMocks
	private SportController controller;

	@Before
	public void setUp() {

	}

	@Test
	public void getSportsShouldGetSportsFromDao() {
		// Act
		controller.getSports();

		// Assert
		verify(dao).getAll();
	}

}
