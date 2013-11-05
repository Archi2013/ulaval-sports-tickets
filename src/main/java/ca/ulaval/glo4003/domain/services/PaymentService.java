package ca.ulaval.glo4003.domain.services;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;

@Service
public class PaymentService {

	public double getCumulatedPrice(ChooseTicketsViewModel chooseTicketsVM) {
		double price = 20.5;
		if (chooseTicketsVM.isGeneralAdmission()) {
			return chooseTicketsVM.getNumberOfTicketsToBuy() * price;
		} else {
			return chooseTicketsVM.getSelectedSeats().size() * price;
		}
	}

	public PaymentViewModel getPaymentViewModel(ChooseTicketsViewModel chooseTicketsVM) {
		// TODO Auto-generated method stub
		return null;
	}

}
