package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Service
public class CartViewService {
	
	@Inject
	private PayableItemsViewModelFactory payableItemsViewModelFactory;
	
	@Autowired
	private Cart currentCart;
	
	public PayableItemsViewModel getPayableItemsViewModel() throws NoTicketsInCartException {
		return payableItemsViewModelFactory.createViewModel(currentCart.getSections(),
				currentCart.getCumulativePrice());
	}
}
