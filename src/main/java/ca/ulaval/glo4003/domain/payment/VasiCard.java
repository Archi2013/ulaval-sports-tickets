package ca.ulaval.glo4003.domain.payment;

public class VasiCard extends CreditCard {

	public VasiCard(Long creditCardNumber, Integer securityCode,
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
