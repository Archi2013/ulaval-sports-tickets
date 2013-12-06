package ca.ulaval.glo4003.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Service
public class QueryCartService {
	
	@Autowired
	private Cart currentCart;

	public Double getCumulativePrice() throws NoTicketsInCartException {
		return currentCart.getCumulativePrice();
	}
	
	public boolean cartContainsTickets() {
		return currentCart.containTickets();
	}
}
