package ca.ulaval.glo4003.web.viewmodels;

import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;

public class PaymentViewModel {
	private CreditCardType creditCardType;
	private Long creditCardNumber;
	private Integer securityCode;
	private String creditCardUserName;
	private Integer expirationMonth;
	private Integer expirationYear;

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}

	public Long getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public Integer getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(Integer securityCode) {
		this.securityCode = securityCode;
	}

	public String getCreditCardUserName() {
		return creditCardUserName;
	}

	public void setCreditCardUserName(String creditCardUserName) {
		this.creditCardUserName = creditCardUserName;
	}

	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public Integer getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
}
