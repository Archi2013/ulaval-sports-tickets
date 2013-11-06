package ca.ulaval.glo4003.web.viewmodels.factories;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForPaymentViewModel;

@Component
public class PaymentViewModelFactory {
	
	@Inject
	SectionForPaymentViewModelFactory sectionForPaymentViewModelFactory;

	public PaymentViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = sectionForPaymentViewModelFactory.createViewModel(chooseTicketsVM,
				gameDto, sectionDto);
		
		Double cumulatedPrice = 0.0;
		
		if (sectionDto.isGeneralAdmission()) {
			cumulatedPrice = chooseTicketsVM.getNumberOfTicketsToBuy() * sectionDto.getPrice();
		} else {
			cumulatedPrice = chooseTicketsVM.getSelectedSeats().size() * sectionDto.getPrice();
		}
		
		String cumulatedPriceFR = cumulatedPrice.toString().replace(".", ",");
		
		PaymentViewModel paymentVM = new PaymentViewModel();
		
		paymentVM.setSectionForPaymentViewModel(sectionForPaymentVM);
		paymentVM.setCumulatedPrice(cumulatedPriceFR);
		
		return paymentVM;
	}

}
