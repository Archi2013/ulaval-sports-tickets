package ca.ulaval.glo4003.domain.payment;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.constants.CreditCardType;

@Component
public class CreditCardFactory {

	public CreditCard createCreditCard(CreditCardType creditCardType,
			Long creditCardNumber, Integer securityCode,
			String creditCardUserName, Integer expirationMonth,
			Integer expirationYear) throws InvalidCreditCardException {
		if (creditCardType.equals(CreditCardType.MISTERCARD)) {
			return new MisterCard(creditCardNumber, securityCode,
					creditCardUserName, expirationMonth, expirationYear);
		} else if (creditCardType.equals(CreditCardType.VASI)) {
			return new VasiCard(creditCardNumber, securityCode,
					creditCardUserName, expirationMonth, expirationYear);
		} else if (creditCardType.equals(CreditCardType.AMERICANEXPRESSO)) {
			return new AmericanExpressoCard(creditCardNumber, securityCode,
					creditCardUserName, expirationMonth, expirationYear);
		} else {
			throw new InvalidCreditCardException();
		}
	}

}
