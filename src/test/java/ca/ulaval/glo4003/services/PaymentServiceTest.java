package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.payment.MisterCard;
import ca.ulaval.glo4003.services.CartService;
import ca.ulaval.glo4003.services.PaymentService;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

	private static final double PRICE = 17.5;

	private static final int EXPIRATION_YEAR = 2016;

	private static final int EXPIRATION_MONTH = 8;

	private static final String CREDIT_CARD_USERNAME = "Personne Inconnu";

	private static final int SECURITY_CODE = 1234;

	private static final long CREDIT_CARD_NUMBER = 123456789012345L;

	@Mock
	private CreditCardFactory creditCardFactory;

	@Mock
	private CartService cartService;

	@InjectMocks
	private PaymentService paymentService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void when_cartContainsTickets_buyTicketsInCart_should_pay_the_tickets_in_cart()
			throws InvalidCreditCardException, NoTicketsInCartException {
		MisterCard creditCard = mock(MisterCard.class);
		CreditCardType creditCardType = CreditCardType.MISTERCARD;

		when(cartService.cartContainsTickets()).thenReturn(true);
		when(creditCardFactory.createCreditCard(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR)).thenReturn(creditCard);
		when(cartService.getCumulativePrice()).thenReturn(PRICE);

		paymentService.buyTicketsInCart(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		verify(creditCard).pay(PRICE);
	}

	@Test
	public void when_cartContainsTickets_buyTicketsInCart_should_makeTicketsUnavailable()
			throws InvalidCreditCardException, NoTicketsInCartException {
		MisterCard creditCard = mock(MisterCard.class);
		CreditCardType creditCardType = CreditCardType.MISTERCARD;

		when(cartService.cartContainsTickets()).thenReturn(true);
		when(creditCardFactory.createCreditCard(creditCardType, CREDIT_CARD_NUMBER,
						SECURITY_CODE, CREDIT_CARD_USERNAME,
						EXPIRATION_MONTH, EXPIRATION_YEAR)).thenReturn(creditCard);
		when(cartService.getCumulativePrice()).thenReturn(PRICE);

		paymentService.buyTicketsInCart(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		verify(cartService).makeTicketsUnavailableToOtherPeople();
	}

	@Test
	public void when_cartContainsTickets_buyTicketsInCart_should_empty_the_cart() throws InvalidCreditCardException,
			NoTicketsInCartException {
		MisterCard creditCard = mock(MisterCard.class);
		CreditCardType creditCardType = CreditCardType.MISTERCARD;

		when(cartService.cartContainsTickets()).thenReturn(true);
		when(creditCardFactory.createCreditCard(creditCardType, CREDIT_CARD_NUMBER,
						SECURITY_CODE, CREDIT_CARD_USERNAME,
						EXPIRATION_MONTH, EXPIRATION_YEAR)).thenReturn(creditCard);
		when(cartService.getCumulativePrice()).thenReturn(PRICE);

		paymentService.buyTicketsInCart(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		verify(cartService).emptyCart();
	}

	@Test(expected = NoTicketsInCartException.class)
	public void when_cartContainsNoTickets_buyTicketsInCart_should_empty_the_cart() throws InvalidCreditCardException,
			NoTicketsInCartException {
		CreditCardType creditCardType = CreditCardType.MISTERCARD;

		when(cartService.cartContainsTickets()).thenReturn(false);

		paymentService.buyTicketsInCart(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		verify(cartService).emptyCart();
	}

	@Test(expected = NoTicketsInCartException.class)
	public void when_cartContainsNoTickets_buyTicketsInCart_should_raise_NoTicketsInCartException()
			throws InvalidCreditCardException, NoTicketsInCartException {
		CreditCardType creditCardType = CreditCardType.MISTERCARD;

		when(cartService.cartContainsTickets()).thenReturn(false);

		paymentService.buyTicketsInCart(creditCardType, CREDIT_CARD_NUMBER,
				SECURITY_CODE, CREDIT_CARD_USERNAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);
	}
}
