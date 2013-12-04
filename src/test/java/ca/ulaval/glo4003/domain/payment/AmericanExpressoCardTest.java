package ca.ulaval.glo4003.domain.payment;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AmericanExpressoCardTest {

	static final Long A_CREDIT_CARD_NUMBER = new Long(14578);
	static final Integer A_SECURITY_CODE = 1478;
	static final String A_USER_NAME = "User";
	static final int EXPIRATION_MONTH = 6;
	static final int EXPIRATION_YEAR = 2015;
	static final double A_COST = 145;

	AmericanExpressoCard card;

	@Before
	public void SetUp() {
		card = new AmericanExpressoCard(A_CREDIT_CARD_NUMBER, A_SECURITY_CODE, A_USER_NAME, EXPIRATION_MONTH,
				EXPIRATION_YEAR);
	}

	@Test
	public void VasiCard_always_has_a_valid_number_of_digits() {
		assertTrue(card.isValidNumberOfDigit());
	}

	@Test
	public void VasiCard_always_has_valid_prefix() {
		assertTrue(card.isValidPrefix());
	}

	@Test
	public void VasiCard_always_has_good_account() {
		assertTrue(card.isGoodAccount());
	}

}
