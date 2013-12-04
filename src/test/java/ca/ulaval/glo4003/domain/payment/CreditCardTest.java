package ca.ulaval.glo4003.domain.payment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardTest {
	static final Long A_CREDIT_CARD_NUMBER = new Long(14578);
	static final Integer A_SECURITY_CODE = 1478;
	static final String A_USER_NAME = "User";
	static final int EXPIRATION_MONTH = 6;
	static final int EXPIRATION_YEAR = 2015;
	static final double A_COST = 145;

	@Test
	public void if_data_is_valid_then_pay_does_nothing() throws InvalidCreditCardException {
		CreditCard creditCard = new ValidCreditCard(A_CREDIT_CARD_NUMBER, A_SECURITY_CODE, A_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		creditCard.pay(A_COST);
	}

	@Test(expected = InvalidCreditCardException.class)
	public void if_data_is_invalid_then_pay_throws_exception() throws InvalidCreditCardException {
		CreditCard creditCard = new InvalidCreditCard(A_CREDIT_CARD_NUMBER, A_SECURITY_CODE, A_USER_NAME,
				EXPIRATION_MONTH, EXPIRATION_YEAR);

		creditCard.pay(A_COST);
	}

	private class ValidCreditCard extends CreditCard {

		public ValidCreditCard(Long creditCardNumber, Integer securityCode, String creditCardUserName,
				Integer expirationMonth, Integer expirationYear) {
			super(creditCardNumber, securityCode, creditCardUserName, expirationMonth, expirationYear);
		}

		@Override
		public Boolean isValidNumberOfDigit() {
			return true;
		}

		@Override
		public Boolean isValidPrefix() {
			return true;
		}

		@Override
		public Boolean isGoodAccount() {
			return true;
		}

	}

	private class InvalidCreditCard extends CreditCard {

		public InvalidCreditCard(Long creditCardNumber, Integer securityCode, String creditCardUserName,
				Integer expirationMonth, Integer expirationYear) {
			super(creditCardNumber, securityCode, creditCardUserName, expirationMonth, expirationYear);
		}

		@Override
		public Boolean isValidNumberOfDigit() {
			return false;
		}

		@Override
		public Boolean isValidPrefix() {
			return false;
		}

		@Override
		public Boolean isGoodAccount() {
			return false;
		}

	}
}
