package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.payment.Cart;
import ca.ulaval.glo4003.domain.payment.CreditCard;
import ca.ulaval.glo4003.domain.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;

@Service
public class PaymentService {

	@Inject
	private CreditCardFactory creditCardFactory;

	@Inject
	private CartService cartService;

	@Autowired
	private Cart currentCart;

	public void buyTicketsInCart(PaymentViewModel paymentVM) throws InvalidCreditCardException,
			NoTicketsInCartException {
		if (currentCart.containTickets()) {
			CreditCard creditCard = creditCardFactory.createCreditCard(paymentVM);
			creditCard.pay(currentCart.getCumulativePrice());
			makeTicketsUnavailable();
		} else {
			throw new NoTicketsInCartException();
		}
	}

	private void makeTicketsUnavailable() {
		cartService.makeTicketsUnavailableToOtherPeople();
	}

	public void emptyCart() {
		currentCart.empty();
	}
}
