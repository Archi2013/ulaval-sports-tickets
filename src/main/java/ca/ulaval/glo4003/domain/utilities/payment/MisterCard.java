package ca.ulaval.glo4003.domain.utilities.payment;

public class MisterCard extends CreditCard {

	public MisterCard(Long creditCardNumber, Integer securityCode,
			String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) {
		super(creditCardNumber, securityCode, creditCardUserName, expirationMonth,
				expirationYear);
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
