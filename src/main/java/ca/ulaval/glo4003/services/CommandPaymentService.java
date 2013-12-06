package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.CreditCard;
import ca.ulaval.glo4003.domain.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Service
public class CommandPaymentService {

	@Inject
	private CreditCardFactory creditCardFactory;

	@Inject
	private CommandCartService commandCartService;
	
	@Inject
	private QueryCartService queryCartService;

	public void buyTicketsInCart(CreditCardType creditCardType,
			String creditCardNumber, Integer securityCode,
			String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) throws InvalidCreditCardException,
			NoTicketsInCartException {
		if (queryCartService.cartContainsTickets()) {
			CreditCard creditCard = creditCardFactory.createCreditCard(creditCardType,
					creditCardNumber, securityCode,
					creditCardUserName, expirationMonth,
					expirationYear);
			creditCard.pay(queryCartService.getCumulativePrice());
			commandCartService.makeTicketsUnavailableToOtherPeople();
			commandCartService.emptyCart();
		} else {
			commandCartService.emptyCart();
			throw new NoTicketsInCartException();
		}
	}
}
