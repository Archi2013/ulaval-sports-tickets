package ca.ulaval.glo4003.domain.payment;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.constants.CreditCardType;

public class CreditCardFactoryTest {
	static final Long A_CREDIT_CARD_NUMBER = new Long(14578);
	static final Integer A_SECURITY_CODE = 1478;
	static final String A_USER_NAME = "User";
	static final int EXPIRATION_MONTH = 6;
	static final int EXPIRATION_YEAR = 2015;
	static final String ANOTHER_CREDIT_CARD_NAME = "Anything,really";

	CreditCardFactory factory;

	@Before
	public void SetUp() {
		factory = new CreditCardFactory();
	}

	@Test
	public void createCreditCard_returns_a_MisterCard_if_cardType_is_MisterCard() throws InvalidCreditCardException {
		CreditCard card = factory.createCreditCard(CreditCardType.MISTERCARD, A_CREDIT_CARD_NUMBER, A_SECURITY_CODE,
				A_USER_NAME, EXPIRATION_MONTH, EXPIRATION_YEAR);

		assertSame(MisterCard.class, card.getClass());
	}

	@Test
	public void createCreditCard_returns_a_Vasi_if_cardType_is_Vasi() throws InvalidCreditCardException {
		CreditCard card = factory.createCreditCard(CreditCardType.VASI, A_CREDIT_CARD_NUMBER, A_SECURITY_CODE,
				A_USER_NAME, EXPIRATION_MONTH, EXPIRATION_YEAR);

		assertSame(VasiCard.class, card.getClass());
	}

	@Test
	public void createCreditCard_returns_an_AmericanExpresso_if_cardType_is_AmericanExpresso()
			throws InvalidCreditCardException {
		CreditCard card = factory.createCreditCard(CreditCardType.AMERICANEXPRESSO, A_CREDIT_CARD_NUMBER,
				A_SECURITY_CODE, A_USER_NAME, EXPIRATION_MONTH, EXPIRATION_YEAR);

		assertSame(AmericanExpressoCard.class, card.getClass());
	}

}
