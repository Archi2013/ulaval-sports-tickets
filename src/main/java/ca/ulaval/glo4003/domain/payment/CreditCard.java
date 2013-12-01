package ca.ulaval.glo4003.domain.payment;

public abstract class CreditCard {

	public CreditCard(Long creditCardNumber, Integer securityCode, String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) {
		super();
	}

	// sans «final» pour les tests
	public void pay(Double number) throws InvalidCreditCardException {
		if (isValid()) {
			// Paiement
		} else {
			throw new InvalidCreditCardException();
		}
	}

	final public Boolean isValid() {
		return isValidExpirationDate() && isValidChecksum() && isValidNumberOfDigit() && isValidPrefix()
				&& isGoodAccount();
	}

	public Boolean isValidExpirationDate() {
		return true;
	}

	public Boolean isValidChecksum() {
		return true;
	}

	abstract public Boolean isValidNumberOfDigit();

	abstract public Boolean isValidPrefix();

	abstract public Boolean isGoodAccount();
}
