package ca.ulaval.glo4003.domain.utilities.payment;



public abstract class CreditCard {

	private Long creditCardNumber;
	private Integer securityCode;
	private String creditCardUserName;
	private Integer expirationMonth;
	private Integer expirationYear;
	
	public CreditCard(Long creditCardNumber, Integer securityCode,
			String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) {
		super();
		this.creditCardNumber = creditCardNumber;
		this.securityCode = securityCode;
		this.creditCardUserName = creditCardUserName;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
	}
	
	final public void pay(Double number) throws InvalidCardException {
		if (isValid()) {
			// Paiement
		} else {
			throw new InvalidCardException();
		}
	}
	
	final public Boolean isValid() {
		return isValidExpirationDate() && isValidChecksum() && isValidNumberOfDigit() &&
				isValidPrefix() && isGoodAccount();
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
