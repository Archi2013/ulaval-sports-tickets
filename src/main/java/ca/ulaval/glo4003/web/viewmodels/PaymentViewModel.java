package ca.ulaval.glo4003.web.viewmodels;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;

public class PaymentViewModel {
	
	@NotNull
	private CreditCardType creditCardType;
	
	@NotNull @Min(1000000000000L) @Max(9999999999999L) // doit être 13 ou 16 chiffres
	private Long creditCardNumber;
	
	@NotNull @Min(100) @Max(9999)
	private Integer securityCode;
	
	@NotEmpty
	private String creditCardUserName;
	
	@NotNull @Min(1) @Max(12)
	private Integer expirationMonth;
	
	@NotNull @Min(2013)
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
