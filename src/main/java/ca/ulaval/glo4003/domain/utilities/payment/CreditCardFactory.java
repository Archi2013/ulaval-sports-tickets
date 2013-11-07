package ca.ulaval.glo4003.domain.utilities.payment;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;

@Component
public class CreditCardFactory {

	public CreditCard createCreditCard(PaymentViewModel paymentVM) throws InvalidCardException {
		CreditCardType type = paymentVM.getCreditCardType();
		if (type.equals(CreditCardType.MISTERCARD)) {
			return new MisterCard(paymentVM.getCreditCardNumber(), paymentVM.getSecurityCode(),
					paymentVM.getCreditCardUserName(), paymentVM.getExpirationMonth(), paymentVM.getExpirationYear());
		} else if (type.equals(CreditCardType.VASI)) {
			return new VasiCard(paymentVM.getCreditCardNumber(), paymentVM.getSecurityCode(),
					paymentVM.getCreditCardUserName(), paymentVM.getExpirationMonth(), paymentVM.getExpirationYear());
		} else if (type.equals(CreditCardType.AMERICANEXPRESSO)) {
			return new AmericanExpressoCard(paymentVM.getCreditCardNumber(), paymentVM.getSecurityCode(),
					paymentVM.getCreditCardUserName(), paymentVM.getExpirationMonth(), paymentVM.getExpirationYear());
		} else {
			throw new InvalidCardException();
		}
	}

}
