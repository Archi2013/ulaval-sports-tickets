package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.web.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@RunWith(MockitoJUnitRunner.class)
public class PaymentControllerTest {

	@Mock
	SearchService searchService;
	
	@Mock
	PaymentService paymentService;
	
	@Mock
	private User currentUser;
	
	@InjectMocks
	private PaymentController controller;

	@Before
	public void setUp() {	
	}

	
}
