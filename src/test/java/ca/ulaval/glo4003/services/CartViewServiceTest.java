package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@RunWith(MockitoJUnitRunner.class)
public class CartViewServiceTest {
	
	private static final double CUMULATIVE_PRICE = 88.7;

	@Mock
	private PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Mock
	private Cart currentCart;
	
	@InjectMocks
	private CartViewService cartViewService;
	
	@Test
	public void getPayableItemsViewModel_should_return_the_payableItemsViewModel() throws NoTicketsInCartException {
		Set<SectionForCart> sections = new HashSet<>();
		PayableItemsViewModel payableItemsVM = mock(PayableItemsViewModel.class);
		
		when(currentCart.getSections()).thenReturn(sections);
		when(currentCart.getCumulativePrice()).thenReturn(CUMULATIVE_PRICE);
		when(payableItemsViewModelFactory.createViewModel(sections, CUMULATIVE_PRICE)).thenReturn(payableItemsVM);
		
		PayableItemsViewModel actual = cartViewService.getPayableItemsViewModel();
		
		assertEquals(payableItemsVM, actual);
	}
}
