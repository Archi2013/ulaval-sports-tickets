package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.CreditCard;
import ca.ulaval.glo4003.domain.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Service
public class PaymentService {

	@Inject
	private CreditCardFactory creditCardFactory;

	@Inject
	private CartService cartService;

	public void buyTicketsInCart(CreditCardType creditCardType,
			String creditCardNumber, Integer securityCode,
			String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) throws InvalidCreditCardException,
			NoTicketsInCartException {
		if (cartService.cartContainsTickets()) {
			CreditCard creditCard = creditCardFactory.createCreditCard(creditCardType,
					creditCardNumber, securityCode,
					creditCardUserName, expirationMonth,
					expirationYear);
			creditCard.pay(cartService.getCumulativePrice());
			makeTicketsUnavailable();
			cartService.emptyCart();
		} else {
			cartService.emptyCart();
			throw new NoTicketsInCartException();
		}
	}

	private void makeTicketsUnavailable() {
		cartService.makeTicketsUnavailableToOtherPeople();
	}
}
